//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.TreeMap;
//
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.AddMedical;
//
//import org.apache.log4j.Logger;
//
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.pdf.AcroFields;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfStamper;
//
///**
// * ����茒�f���ڂ̃y�[�W�쐬
// * @author DevMaster
// *
// */
//public class PrintHitokutei_ShokenVer2 extends JTKenshinPrint {
//
//	public static final String WORK_PDF_HITOKUTEI_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"hitokutei.pdf";
//
//	// add s.inoue 2014/02/21
//	public static final String WORK_SHOKEN_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"shokenList.pdf";
//	
//	// ���{�敪
//	private static final String KEKKA_OUTPUT_JISI_KBN_0 = "�����{";
//	private static final String KEKKA_OUTPUT_JISI_KBN_2 = "����s�\";
//	private static final String BASEVALUPPLIMIT = "baseValUppLimit";
//	private static final String BASEVALDWNLIMIT = "baseValDwnLimit";
//
//	AddMedical addMedical = new AddMedical();
//
//	List pdfPathList = new ArrayList();
//
//	String kihonPdf = "";
//
//	PrintKihonChkList pKChkList = new PrintKihonChkList();
//
//	// add s.inoue 2014/02/18
//	PrintShokenList pShokenList = new PrintShokenList();
//	
//	//all�}�b�v���ڂ��l�߂ē����
//	Hashtable<String, Hashtable<String, String>> allMap =
//		new Hashtable<String, Hashtable<String, String>> ();
//
//	public final static String PATH_PRINT_PROPERTIES = "print.properties";
//	
//	private static org.apache.log4j.Logger logger = Logger.getLogger(PrintHitokutei_ShokenVer2.class);
//	/**
//	 * ����茒�f���ڂ̃y�[�W�쐬
//	 * @param kensaNenList
//	 * @param kojinData
//	 * @return pdfPathList
//	 */
//	public List startAddMedical(List kensaNenList, Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData, String ishiName,int printSelect ){
//
//		String kensaNengappi = "";
//		String zKensaNengappi = "";
//		String zzKensaNengappi = "";
//		String tmpPath = WORK_PDF_HITOKUTEI_PDF;
//		// edit ver2 s.inoue 2009/06/22
//		TreeMap<String, String> resultItem = new TreeMap<String, String>();
//		TreeMap<String, String> zResultItem = new TreeMap<String, String>();
//		TreeMap<String, String> zzResultItem = new TreeMap<String, String>();
//
//		//����̒l�̂��鍀�ڃR�[�h�̃��X�g
//		ArrayList<String> itemCodes = new ArrayList<String> ();
//		
//		// add s.inoue 2014/02/24
//		//����̒l�̂��鍀�ڃR�[�h�̃��X�g
//		TreeMap<String, ArrayList<String>> mapKonkai = new TreeMap<String, ArrayList<String>>();
//		TreeMap<String, ArrayList<String>> mapZenkai = new TreeMap<String, ArrayList<String>>();
//		TreeMap<String, ArrayList<String>> mapZenzenkai = new TreeMap<String, ArrayList<String>>();
//		
//		ArrayList<String> itemShokenCodes = new ArrayList<String> ();
//		
//		//���ڗp
//		ArrayList<String> koumokuCd = new ArrayList<String> ();
//
//		//����ptable
//		Hashtable<String, String>  koumokuName = new Hashtable<String, String>();
//		Hashtable<String, String>  taniList = new Hashtable<String, String>();
//		Hashtable<String, String>  baseVal = new Hashtable<String, String>();
//		Hashtable<String, String>  baseValUppLimit = new Hashtable<String, String>();
//		Hashtable<String, String>  baseValDwnLimit = new Hashtable<String, String>();
//		Hashtable<String, String>  HL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  valsesList = new Hashtable<String, String>();
//		Hashtable<String, String>  DATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  JISI_KBN = new Hashtable<String, String>();
//
//		//�O��ptable
//		Hashtable<String, String> zHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> zValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> zDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  zJISI_KBN = new Hashtable<String, String>();
//
//		//�O�X��ptable
//		Hashtable<String, String> zzHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> zzValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> zzDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  zzJISI_KBN = new Hashtable<String, String>();
//
//		kensaNengappi = replaseNenGaPii(kensaNenList,0);
//
//		//�ǉ��̌��f����tmp(�f�[�^�擾�j
//		// edit ver2 s.inoue 2009/06/22
//		List<TreeMap<String, String>> tmpTuikaList = addMedical.tuika(kojinData,kensaNengappi,0,koumokuCd);
//		//�ǉ��̌��f����
//		List<TreeMap<String, String>> tuikaList = new ArrayList<TreeMap<String, String>>() ;
//		//�ǉ��̌��f�O��
//		List<TreeMap<String, String>> zTuikaList = new ArrayList<TreeMap<String, String>>() ;
//		//�ǉ��̌��f�O�X��
//		List<TreeMap<String, String>> zzTuikaList = new ArrayList<TreeMap<String, String>>() ;
//		//������{�`�F�b�N���X�g
//		List<TreeMap<String, String>> seikatuKihonList = new ArrayList<TreeMap<String, String>>() ;
//		//�����L���p���X�g
//		List<String> syokenUmuList = new ArrayList<String>();
//		//�����p���X�g
//		List<TreeMap<String, String>> syokenList = new ArrayList<TreeMap<String, String>>() ;
//
//		//�����A������{�`�F�b�N���X�g�͑ޔ����Ă���
//		for(int i = 0; i<tmpTuikaList.size();i++){
//			resultItem = tmpTuikaList.get(i);
//			
//			// System.out.println(resultItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//			
//			String val = resultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//			String code = resultItem.get(PrintDefine.CODE_KOUMOKU);
//			// add ver2 s.inoue 2009/07/15
//			String kensaNengapi = resultItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//			if(!val.isEmpty()){
//// edit s.inoue 2014/02/26
//// �L����list�Ɋ܂߂�
////				if(PrintDefine.syoken.contains(code)){
////					//�����̗L��
////					syokenUmuList.add(code);
////				}
//				if(PrintDefine.syoken.contains(code)){
//					//����
//					// edit s.inoue 2014/03/07
//					// �ߋ����捞
//					// if(kensaNengapi.equals(kensaNengappi)){
//						syokenList.add(tmpTuikaList.get(i));
//						// add s.inoue 2014/02/24
//						itemShokenCodes.add(resultItem.get(PrintDefine.CODE_KOUMOKU));
//					// }
//				}else if(PrintDefine.seikatuKihon.contains(code)){
//					//������{�`�F�b�N���X�g
//					seikatuKihonList.add(tmpTuikaList.get(i));
//				}else{
//					//�ǉ��̌��f
//					tuikaList.add(tmpTuikaList.get(i));
//					itemCodes.add(resultItem.get(PrintDefine.CODE_KOUMOKU));
//				}
//			}
//		}
//
//		// ���ʕ\�o�͏���
//		// �����\��
//		// ����Ɣ�r���Ĉ�v����΁A����ɕ\���B��v���Ȃ���ΐV���ɍs��ǉ�
//		int keyCnt = 0;
//		List<String> itemMergedCodes = new ArrayList<String> ();
//
//		for (String keyItem :itemCodes){
//			keyCnt = 0;
//			for (String item :itemCodes){
//				if (keyItem.equals(item)) {
//					if (keyCnt == 0 && !itemMergedCodes.contains(keyItem)){
//						itemMergedCodes.add(item);
//					}
//					keyCnt ++;
//				}
//			}
//		}
//
//		// add s.inoue 2014/02/24
//		// �����̗�������
//		// ���ڃR�[�h��5����Group(�����̗L��)
//		// ��t�̎����͕ʃt�B�[���h
//		// ���ڏ��ɂ��ă}�[�W����̂����₷���x�X�g
//		int keyShokenCnt = 0;
//		// del s.inoue 2014/03/17 �}�[�W����Ƃ����g��Ȃ�
//		List<String> itemShokenMergedCodes = new ArrayList<String> ();
//		for (String keyItem :itemShokenCodes){
//			keyShokenCnt = 0;
//			for (String item :itemShokenCodes){
//				if (keyItem.equals(item)) {
//					if (keyShokenCnt == 0 && !itemShokenMergedCodes.contains(keyItem)){
//						itemShokenMergedCodes.add(item);
//					}
//					keyShokenCnt ++;
//				}
//			}
//		}
//		
//		//�y�[�W�����J�E���g
//		int templistNum =itemMergedCodes.size();//���ڂ̐�
//		// edit s.inoue 2014/03/19
//		// int div = templistNum/20;//���ڂŊ������y�[�W����
//		// int surPlus = templistNum%20;//���ڂŊ������y�[�W�����̗]��
//		int div = templistNum/30;//���ڂŊ������y�[�W����
//		int surPlus = templistNum%30;//���ڂŊ������y�[�W�����̗]��
//		
//		int startNum = 0;//�P�y�[�W�Ɉ󎚂��閇���̍ő吔��
//		int cnt = 0;//�y�[�W�̃J�E���^�[
//		int pageNum = 0; //�y�[�W�󎚂̕��q
//		int koumokuMaxPageNum = div;
//		if (surPlus > 0) {
//			koumokuMaxPageNum++;
//		}
//		
//// del s.inoue 2014/03/18
////		//����
//		int sNum = syokenList.size();//�����̐�
//////		int sDiv = sNum/4;//MAX���S�Ƃ��ĂS�Ŋ�����������
//////		int sSurPlus = sNum%4;//MAX���S�Ƃ��ĂS�Ŋ������������̗]��
////		int sDiv = sNum/3;//MAX���R�Ƃ��ĂR�Ŋ�����������
////		int sSurPlus = sNum%3;//MAX���R�Ƃ��ĂR�Ŋ������������̗]��
////		int sStrNum = 0;//�P�y�[�W�Ɉ󎚂��閇���̍ő吔��
////
////		int syokenMaxPageNum = sDiv;
////		if (sSurPlus > 0) {
////			syokenMaxPageNum++;
////		}
//
//		int commonMaxPageNum = 0;
//// del s.inoue 2014/03/18		
////		if (syokenMaxPageNum>koumokuMaxPageNum)
////		{
////			commonMaxPageNum=syokenMaxPageNum;
////		}else{
//			commonMaxPageNum=koumokuMaxPageNum;
////		}
//
//		// add s.inoue 2014/03/18
//		int pNum_page = itemShokenCodes.size();
//		int pDiv_page = pNum_page/15;	 //MAX��15�Ƃ���15�Ŋ�����������
//		
////		int pSurPlus_page = pNum_page%15;//MAX��15�Ƃ���15�Ŋ������������̗]��
//		// 1page��
//		if (pNum_page > 0){
//			if (pDiv_page == 0) {
//				++pDiv_page;
//			}
//		}
//		koumokuMaxPageNum+=pDiv_page;
//		commonMaxPageNum +=pDiv_page;
//			
//// del s.inoue 2014/03/18			
//		//������{�̍��ڂ��ЂƂł�����΁A��{�`�F�b�N���X�g�쐬�B
////		if (div > sDiv) {
//			if(seikatuKihonList.size()>0){
//				kihonPdf = pKChkList.createChkList(kensaNenList, kojinData, kikanData, ishiName ,commonMaxPageNum++,printSelect);
////				syokenMaxPageNum++;
//				// add ver2 s.inoue 2009/06/25
//				koumokuMaxPageNum++;
//			}
////		}else {
//			if(seikatuKihonList.size()>0){
//				kihonPdf = pKChkList.createChkList(kensaNenList, kojinData, kikanData, ishiName ,commonMaxPageNum++,printSelect);
//				// edit ver2 s.inoue 2009/07/28 �����y�[�W���J�E���g
////				syokenMaxPageNum++;
//				koumokuMaxPageNum++;
//			}
////		}
//
//		//�ǉ��̌��f�Ə����������ꍇ�́A�߂�
//		if(tuikaList.size()==0 && sNum == 0){
//			//������{�̃y�[�W���쐬����Ă���΍Ō�ɒǉ�
//			if(isVal(kihonPdf)){
//				pdfPathList.add(kihonPdf);
//			}
//			return pdfPathList;
//		}
//
////		//�O��A�O�X��擾
////		int zenkaiFlg = 0;
////		String tmpKensaNenGappi = "";
////		zKensaNengappi = replaseNenGaPii(kensaNenList,1);
////		zzKensaNengappi = replaseNenGaPii(kensaNenList,2);
////
////		if(!zKensaNengappi.equals("") ){
////			tmpKensaNenGappi = zKensaNengappi;
////			zTuikaList = addMedical.tuika(kojinData,tmpKensaNenGappi,1,itemCodes);
////			zenkaiFlg = 1;
////		}
////		if(!zzKensaNengappi.equals("") ){
////			tmpKensaNenGappi = zzKensaNengappi;
////			zzTuikaList = addMedical.tuika(kojinData,tmpKensaNenGappi,1,itemCodes);
////			zenkaiFlg = 2;
////		}
//
//		boolean isMale = false;
//		// edit s.inoue 2009/11/11
//		/* ���� */
//		if (kojinData.get("SEX").equals("�j��")) {
//			isMale = true;
//		}
//
//		/* ��l���,���� */
//		String kenshinDate ="";
//		String prekenshinDate ="";
//		int historyCnt = 0;
//
//		// �J�E���g�p-���f�N����
////		for (int kenCnt=0;kenCnt < kensaNenList.size(); kenCnt++){
////			kensaNengappi = replaseNenGaPii(kensaNenList,historyCnt);
//
//			for(int i = 0; i<tuikaList.size();i++){
//				resultItem = tuikaList.get(i);
//				// System.out.println(resultItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//				if (resultItem == null)
//					break;
//
//				// �ǉ����f���ڃ��X�g
//				kenshinDate =resultItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//
//				// ���f���{�����ɗ���\������
//				if (!kenshinDate.equals(prekenshinDate)){
//					// �L�[���
//					prekenshinDate=resultItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//					// edit ver2 s.inoue 2009/06/22
//					// �J�E���g�p-���f�N����
//					for (int hisCnt=0;hisCnt< kensaNenList.size(); hisCnt++){
//						kensaNengappi = replaseNenGaPii(kensaNenList,historyCnt);
//						
////						// add s.inoue 2014/06/11
////						historyCnt++;
//// del s.inoue 2014/05/26							
//						if (kensaNengappi.equals(kenshinDate)){
//							historyCnt++;break;
//						}else{
//							historyCnt++;
//						}
//					}
//				}
//
//				// ����l
//				// �Œ��� ���ږ��A�P�ʁA��l
//				String cd = resultItem.get(PrintDefine.CODE_KOUMOKU);
//				koumokuCd.add(cd);
//				String name = resultItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME);
//				// delete ver2 s.inoue 2009/06/26
//				//resultItem.get(PrintDefine.COLUMN_NAME_KENSA_HOUHOU);
//				koumokuName.put(cd, name);
//
//				String tani = resultItem.get(PrintDefine.COLUMN_NAME_TANI);
//				taniList.put(cd, tani);
//
//// edit s.inoue 2009/10/28
//// �C���R��
////				if (isMale) {
////					kagen = resultItem.get(PrintDefine.COLUMN_NAME_JS_KAGEN);
////					jyogen = resultItem.get(PrintDefine.COLUMN_NAME_JS_JYOUGEN);
////				}else{
////					kagen = resultItem.get(PrintDefine.COLUMN_NAME_DS_KAGEN);
////					jyogen = resultItem.get(PrintDefine.COLUMN_NAME_DS_JYOUGEN);
////				}
//				// edit s.inoue 2009/10/28
//				// �f�[�^�^�C�v�����l�̂�
//				String kagen = "";
//				String jyogen = "";
//				if ("1".equals(resultItem.get(PrintDefine.DATA_TYPE))) {
//					// edit s.inoue 2009/10/28
//					// ��l�t�H�[�}�b�g�Ή�
//					String kekkaFormat = resultItem.get(PrintDefine.COLUMN_MAX_BYTE_LENGTH);
//					kekkaFormat = kekkaFormat != null ? kekkaFormat: "";
//
//					if (isMale){
//						kagen = resultItem.get(PrintDefine.COLUMN_NAME_DS_KAGEN);
//						kagen = JValidate.validateKensaKekkaDecimal(kagen,kekkaFormat);
//						kagen = kagen != null ? kagen:"";
//						jyogen = resultItem.get(PrintDefine.COLUMN_NAME_DS_JYOUGEN);
//						jyogen = JValidate.validateKensaKekkaDecimal(jyogen,kekkaFormat);
//						jyogen = jyogen != null ? jyogen:"";
//					}else{
//						kagen = resultItem.get(PrintDefine.COLUMN_NAME_JS_KAGEN);
//						kagen = JValidate.validateKensaKekkaDecimal(kagen,kekkaFormat);
//						kagen = kagen != null ? kagen:"";
//						jyogen = resultItem.get(PrintDefine.COLUMN_NAME_JS_JYOUGEN);
//						jyogen = JValidate.validateKensaKekkaDecimal(jyogen,kekkaFormat);
//						jyogen = jyogen != null ? jyogen:"";
//					}
//				}
//
//				if(isVal(kagen)||isVal(jyogen)){
//					baseVal.put(cd,kagen +"�`" +jyogen);
//					baseValUppLimit.put(cd, jyogen);
//					baseValDwnLimit.put(cd, kagen);
//				}else{
//					baseVal.put(cd,"");
//					baseValUppLimit.put(cd,"");
//					baseValDwnLimit.put(cd,"");
//				}
//
//				// �������ʒl���Ɋi�[����
//				String HL = resultItem.get(PrintDefine.STR_H_L);
//				// HL ���𖈂Ɋi�[
//				if (historyCnt == 1){
//					HL_LIST.put(cd,HL);
//				}else if (historyCnt == 2){
//					zHL_LIST.put(cd,HL);
//				}else if (historyCnt == 3){
//					zzHL_LIST.put(cd,HL);
//				}
//
//				String vales = resultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//				// ���ʒl ���𖈂Ɋi�[
//				if (historyCnt == 1){
//					valsesList.put(cd,vales);
//				}else if (historyCnt == 2){
//					zValsesList.put(cd,vales);
//				}else if (historyCnt == 3){
//					zzValsesList.put(cd,vales);
//				}
//
//				String dataType = resultItem.get(PrintDefine.DATA_TYPE);
//				// �f�[�^�^ ���𖈂Ɋi�[
//				if (historyCnt == 1){
//					DATATYPE_LIST.put(cd,dataType);
//				}else if (historyCnt == 2){
//					zDATATYPE_LIST.put(cd,dataType);
//				}else if (historyCnt == 3){
//					zzDATATYPE_LIST.put(cd,dataType);
//				}
//
//				String jisiKbn = resultItem.get(PrintDefine.COLUMN_NAME_JISI_KBN);
//				// �f�[�^�^ ���𖈂Ɋi�[
//				if (historyCnt == 1){
//					JISI_KBN.put(cd,jisiKbn);
//				}else if (historyCnt == 2){
//					zJISI_KBN.put(cd,jisiKbn);
//				}else if (historyCnt == 3){
//					zzJISI_KBN.put(cd,jisiKbn);
//				}
//			}
////		}
//
////		//�O��A�O�X��̒l�̂��錒�f���ڂ𔲂��o��
////		if(!zTuikaList.isEmpty()){
////			for(int i = 0; i<zTuikaList.size();i++){
////				zResultItem = (Hashtable<String, String>)zTuikaList.get(i);
////				String cd = zResultItem.get(PrintDefine.CODE_KOUMOKU);
////
////				String HL = zResultItem.get(PrintDefine.STR_H_L);
////				zHL_LIST.put(cd,HL);
////
////				String vales = zResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
////				zValsesList.put(cd,vales);
////
////				String dataType = zResultItem.get("DATA_TYPE");
////				zDATATYPE_LIST.put(cd,dataType);
////
////				String zjisiKbn = resultItem.get("JISI_KBN");
////				zJISI_KBN.put(cd,zjisiKbn);
////			}
////		}
////		//�O�X��擾
////		if(!zzTuikaList.isEmpty()){
////			for(int i = 0; i<zzTuikaList.size();i++){
////				zzResultItem = (Hashtable<String, String>)zzTuikaList.get(i);
////				String cd = zzResultItem.get(PrintDefine.CODE_KOUMOKU);
////
////				String HL = zzResultItem.get(PrintDefine.STR_H_L);
////				zzHL_LIST.put(cd,HL);
////
////				String vales = zzResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
////				zzValsesList.put(cd,vales);
////
////				String dataType = zzResultItem.get("DATA_TYPE");
////				zzDATATYPE_LIST.put(cd,dataType);
////
////				// add s.inoue 20080909
////				String zzjisiKbn = resultItem.get("JISI_KBN");
////				zzJISI_KBN.put(cd,zzjisiKbn);
////
////			}
////		}
//
//		//�Œ�
//		allMap.put("koumokuName", koumokuName);
//		allMap.put("taniList", taniList);
//		allMap.put("baseVal", baseVal);
//		allMap.put("baseValUppLimit", baseValUppLimit);
//		allMap.put("baseValDwnLimit", baseValDwnLimit);
//		//����
//		allMap.put("HL_LIST", HL_LIST);
//		allMap.put("valsesList", valsesList);
//		allMap.put("DATATYPE_LIST", DATATYPE_LIST);
//		allMap.put("JISI_KBN",JISI_KBN);
//		//�O��
//		allMap.put("zHL_LIST", zHL_LIST);
//		allMap.put("zValsesList", zValsesList);
//		allMap.put("zDATATYPE_LIST", zDATATYPE_LIST);
//		allMap.put("zJISI_KBN",zJISI_KBN);
//		//�O�X��
//		allMap.put("zzHL_LIST", zzHL_LIST);
//		allMap.put("zzValsesList", zzValsesList);
//		allMap.put("zzDATATYPE_LIST", zzDATATYPE_LIST);
//		allMap.put("zzJISI_KBN",zzJISI_KBN);
//
//		// add s.inoue 2014/02/26
//		// �����p����
//		// edit ver2 s.inoue 2009/06/22
//		//����ptable
//		//���ڗp
//		ArrayList<String> sKoumokuCd = new ArrayList<String> ();
//		Hashtable<String, String>  sKoumokuName = new Hashtable<String, String>();
////		Hashtable<String, String>  sTaniList = new Hashtable<String, String>();
////		Hashtable<String, String>  sBaseVal = new Hashtable<String, String>();
////		Hashtable<String, String>  sBaseValUppLimit = new Hashtable<String, String>();
////		Hashtable<String, String>  sBaseValDwnLimit = new Hashtable<String, String>();
////		Hashtable<String, String>  sHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  sValsesList = new Hashtable<String, String>();
//		Hashtable<String, String>  sDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  sJISI_KBN = new Hashtable<String, String>();
//
//		//�O��ptable
////		Hashtable<String, String> szHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> szValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> szDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  szJISI_KBN = new Hashtable<String, String>();
//
//		//�O�X��ptable
////		Hashtable<String, String> szzHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> szzValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> szzDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  szzJISI_KBN = new Hashtable<String, String>();
//		
//		TreeMap<String, String> resultShokenItem = new TreeMap<String, String>();
////		TreeMap<String, String> zResultShokenItem = new TreeMap<String, String>();
////		TreeMap<String, String> zzResultShokenItem = new TreeMap<String, String>();
//		
//		//all�}�b�v���ڂ��l�߂ē����
//		Hashtable<String, Hashtable<String, String>> allMapShoken =
//			new Hashtable<String, Hashtable<String, String>> ();
//		
//		String shokenkenshinDate ="";
//		String preShokenkenshinDate ="";
//		int historyShokenCnt = 0;
//		
//		for(int i = 0; i<syokenList.size();i++){
//			resultShokenItem = syokenList.get(i);
//
//			if (resultShokenItem == null)
//				break;
//
//			shokenkenshinDate =resultShokenItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//
//			// ���f���{�����ɗ���\������
//			if (!shokenkenshinDate.equals(preShokenkenshinDate)){
//				// �L�[���
//				preShokenkenshinDate=resultShokenItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//				// �J�E���g�p-���f�N����
//				for (int hisCnt=0;hisCnt< kensaNenList.size(); hisCnt++){
//					kensaNengappi = replaseNenGaPii(kensaNenList,historyShokenCnt);
//					
//					// add s.inoue 2014/06/11
////					historyShokenCnt++;
//// del s.inoue 2014/05/26						
//					if (kensaNengappi.equals(shokenkenshinDate)){
//						historyShokenCnt++;break;
//					}else{
//						historyShokenCnt++;
//					}
//				}
//			}
//
//			// ����l
//			// �Œ��� ���ږ��A�P�ʁA��l
//			String cd = resultShokenItem.get(PrintDefine.CODE_KOUMOKU);
//			sKoumokuCd.add(cd);
//			String name = resultShokenItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME);
//			sKoumokuName.put(cd, name);
//			
//			String vales = resultShokenItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//			// ���ʒl ���𖈂Ɋi�[
//			if (vales.equals("")){
//				System.out.println("");
//			}
//			if (historyShokenCnt == 1){
//				sValsesList.put(cd,vales);
//			}else if (historyShokenCnt == 2){
//				szValsesList.put(cd,vales);
//			}else if (historyShokenCnt == 3){
//				szzValsesList.put(cd,vales);
//			}
//			
//			String dataType = resultShokenItem.get(PrintDefine.DATA_TYPE);
//			// �f�[�^�^ ���𖈂Ɋi�[
//			if (historyShokenCnt == 1){
//				sDATATYPE_LIST.put(cd,dataType);
//			}else if (historyShokenCnt == 2){
//				szDATATYPE_LIST.put(cd,dataType);
//			}else if (historyShokenCnt == 3){
//				szzDATATYPE_LIST.put(cd,dataType);
//			}
//			
//			String jisiKbn = resultShokenItem.get(PrintDefine.COLUMN_NAME_JISI_KBN);
//			// �f�[�^�^ ���𖈂Ɋi�[
//			if (historyShokenCnt == 1){
//				sJISI_KBN.put(cd,jisiKbn);
//			}else if (historyShokenCnt == 2){
//				szJISI_KBN.put(cd,jisiKbn);
//			}else if (historyShokenCnt == 3){
//				szzJISI_KBN.put(cd,jisiKbn);
//			}
//
//		}
//		
//		//�Œ�
//		allMapShoken.put("koumokuName", sKoumokuName);
////		allMapShoken.put("taniList", taniList);
////		allMapShoken.put("baseVal", baseVal);
////		allMapShoken.put("baseValUppLimit", baseValUppLimit);
////		allMapShoken.put("baseValDwnLimit", baseValDwnLimit);
//		//����
////		allMapShoken.put("HL_LIST", HL_LIST);
//		allMapShoken.put("valsesList", sValsesList);
//		allMapShoken.put("DATATYPE_LIST", sDATATYPE_LIST);
//		allMapShoken.put("JISI_KBN",sJISI_KBN);
//		//�O��
////		allMapShoken.put("zHL_LIST", zHL_LIST);
//		allMapShoken.put("zValsesList", szValsesList);
//		allMapShoken.put("zDATATYPE_LIST", szDATATYPE_LIST);
//		allMapShoken.put("zJISI_KBN",szJISI_KBN);
//		//�O�X��
////		allMapShoken.put("zzHL_LIST", zzHL_LIST);
//		allMapShoken.put("zzValsesList", szzValsesList);
//		allMapShoken.put("zzDATATYPE_LIST", szzDATATYPE_LIST);
//		allMapShoken.put("zzJISI_KBN",szzJISI_KBN);
//		// add s.inoue 2014/02/26 ��������������
//		
//		int kFlg = 0;
//// del s.inoue 2014/03/18		
////		int syokenFlg = 0;
//		if(surPlus>0){
//			kFlg = 1;
//		}
//		
//// del s.inoue 2014/03/18		
////		if(sSurPlus>0){
////			syokenFlg = 1;
////		}
//
//		// �����x�[�X�̃��[�v����������B���ڂ��쓮�ɂ���
////		if(sDiv+syokenFlg<=div+kFlg){
//
//		// add s.inoue 2014/03/18
//		int sStrNum = 0;
//		
//			for(int i = 0;i<div;i++){
//				pageNum++;
//				cnt++;
//				// edit s.inoue 2014/03/19
//				// startNum = 20*cnt;
//				startNum = 30*cnt;
//
////				System.out.println("startNum:" + startNum
////						+"pageNum" + pageNum
////						+"koumokuMaxPageNum" + koumokuMaxPageNum
////						+"sStrNum" + sStrNum
////						);
//
//				// edit s.inoue 2014/03/19
//				// 20��30
//				createPdf(kensaNenList, tmpPath, allMap,startNum,
//						i,surPlus,kojinData,kikanData,ishiName,pageNum,koumokuMaxPageNum,0,syokenList,sStrNum,itemMergedCodes,printSelect,30);
//
//				sStrNum = 3*cnt;
//			}
//			
//			if(surPlus>0){
//				pageNum++;
//				if(div==0){
//					div = 1;
//				}
//
//				// add ver2 s.inoue 2009/07/27
////				if (cnt >= 4){
////					sStrNum ++;
////				}
//
//				// edit s.inoue 2014/03/19
//				// int sabunNum = itemMergedCodes.size() - 20*cnt;
//				int sabunNum = itemMergedCodes.size() - 30*cnt;
//				startNum = itemMergedCodes.size();
//
////				System.out.println("startNum:" + startNum
////						+"pageNum" + pageNum
////						+"koumokuMaxPageNum" + koumokuMaxPageNum
////						+"sStrNum" + sStrNum
////						);
//
//				if (cnt >= 3){
//					sStrNum ++;
//				}
//				createPdf(kensaNenList, tmpPath, allMap,startNum,
//						cnt,surPlus,kojinData,kikanData,ishiName,pageNum,koumokuMaxPageNum,1,syokenList,sStrNum,itemMergedCodes,printSelect,sabunNum);
//			}
//			
//// del s.inoue 2014/03/18
////		//�������쓮�ɂ���
////		}else {
////			if (sSurPlus > 0) {
////				++sDiv;
////			}
////
////			for(int i = 0;i < sDiv;i++){
////				pageNum++;
////				startNum = 20 * cnt++;
////
////				if (templistNum > startNum) {
////					if (templistNum - startNum == surPlus) {
////						startNum += surPlus;
////					}
////				}
////				else {
////					startNum = -20;
////				}
//////				sStrNum = 4 * cnt;
////				sStrNum = 3 * cnt;
////				createPdfBasedSyokenRoop(kensaNenList, tmpPath, allMap,startNum + 20,
////						i,surPlus,kojinData,kikanData,ishiName,pageNum,syokenMaxPageNum,0,syokenList,sStrNum,itemMergedCodes,printSelect);
////			}
////		}
//		
//		// add s.inoue 2014/02/21
//		// �����y�[�W�ŕʏ������s��
//		// edit s.inoue 2014/03/11 itemShokenMergedCodes�}�[�W�͂��Ȃ�x
//		// int pTemplistNum =itemShokenCodes.size();//���ڂ̐�
//		int pTemplistNum =itemShokenMergedCodes.size();//���ڂ̐�
//		
//		int pStartNum = 0;//�P�y�[�W�Ɉ󎚂��閇���̍ő吔��
//		// int pCnt = 0;//�y�[�W�̃J�E���^�[
//		int pNum = itemShokenCodes.size();
//		
//		int pDiv = pNum/15;//MAX��15�Ƃ���15�Ŋ�����������
//		int pSurPlus = pNum%15;//MAX��15�Ƃ���15�Ŋ������������̗]��
//		int pSubun = 0; //�}�[�W���ڂł̃J�E���g�i�y�[�W�܂���
//		
//		// add s.inoue 2014/03/18
//		int syokenMaxPageNum = commonMaxPageNum;
//		
//		if (pNum > 0){
//			
//			if (pSurPlus > 0) {
//				++pDiv;
//			}
//
//			for(int i = 0;i < pDiv;i++){
//				pageNum++;
//				
//				pStartNum = pSubun;
//				
//				pSubun = createPdfPageSyokenRoop(kensaNenList, tmpPath, allMapShoken,pStartNum,
//						i,pSurPlus,kojinData,kikanData,ishiName,pageNum,syokenMaxPageNum,0,syokenUmuList,syokenList,itemShokenMergedCodes,printSelect);
//			}
//		}
//
//		//������{�̃y�[�W���쐬����Ă���΍Ō�ɒǉ�
//		if(isVal(kihonPdf)){
//			pdfPathList.add(kihonPdf);
//		}
//		return pdfPathList;
//	}
//
//	/**
//	 * �O�񌋉ʎ擾
//	 * @param zResultItem
//	 * @param zHL_LIST
//	 * @param zValsesList
//	 * @param zDATATYPE_LIST
//	 * @param zzHL_LIST
//	 * @param zzValsesList
//	 * @param zzDATATYPE_LIST
//	 */
//	private void getZenkaiKekka(Hashtable<String, String> zResultItem,
//			ArrayList<String> zHL_LIST, ArrayList<String> zValsesList,
//			ArrayList<String> zDATATYPE_LIST) {
//
//		String zhl = zResultItem.get(PrintDefine.STR_H_L);
//		zHL_LIST.add(zhl);
//
//		String zVal = zResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//		zValsesList.add(zVal);
//
//		String zData = zResultItem.get(PrintDefine.DATA_TYPE);
//		zDATATYPE_LIST.add(zData);
//
//	}
//
//	/**
//	 * �O�X�񌋉ʎ擾
//	 * @param zResultItem
//	 * @param zHL_LIST
//	 * @param zValsesList
//	 * @param zDATATYPE_LIST
//	 * @param zzHL_LIST
//	 * @param zzValsesList
//	 * @param zzDATATYPE_LIST
//	 */
//	private void getZZenkaiKekka(Hashtable<String, String> zzResultItem,
//			ArrayList<String> zzHL_LIST,ArrayList<String> zzValsesList,
//			ArrayList<String> zzDATATYPE_LIST) {
//
//		String zzhl = zzResultItem.get(PrintDefine.STR_H_L);
//		zzHL_LIST.add(zzhl);
//
//		String zzVal = zzResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//		zzValsesList.add(zzVal);
//
//		String zzData = zzResultItem.get("DATA_TYPE");
//		zzDATATYPE_LIST.add(zzData);
//	}
//
//	/**
//	 * PDF�����ۂɈ󎚂���B
//	 *
//	 * @param kensaNenList�@
//	 * @param tmpPath�@�t�@�C������
//	 * @param allMap
//	 * @param startNum�@�o�͍��ڐ�
//	 * @param cnt�@PDF�̖���
//	 * @param surplus�@�]�萔
//	 * @param kojinData�@�l�f�[�^
//	 * @param kikanData�@�@�փf�[�^
//	 * @param ishiName�@��t�̖��O
//	 * @param pageNum�@�y�[�W�J�E���g
//	 * @param div�@�]��ȊO�̃y�[�W��
//	 * @param isflg �Q���ڈȍ~�̗]��𔻒f
//	 * @param tmpSyoken�@�����f�[�^
//	 * @param itemCodes�@�擾���鍡��̍��ڃR�[�h�����������X�g
//	 */
//	private void createPdf(List kensaNenList, String tmpPath, Hashtable allMap,
//			int startNum,int cnt,int surplus,Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData,String ishiName,int pageNum,
//			int div,int isflg, List<TreeMap<String, String>> tmpSyoken, int sStrNum, List<String> itemCodes ,int printSelect,int sabunNum) {
//
//		try {
//			PdfReader reader = new PdfReader(WORK_PDF_HITOKUTEI_PDF);
//
//			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tmpPath+Integer.toString(cnt)));
//			AcroFields form = stamp.getAcroFields();
//
//			//�w�b�_�[���ځA�y�[�W���Ȃǈ�
//			makeHaeder(kensaNenList, kojinData, kikanData, ishiName, pageNum, div, form, printSelect);
//			// edit s.inoue 2010/04/20
//			int num = sabunNum;
//			// int num = 20;
//
//			// edit s.inoue 2010/04/20
//			// startNum = startNum - 20;
//			startNum = startNum - sabunNum;
//			if (startNum < sabunNum){
//				startNum = 0;
//			}else{
//				if(startNum<=0&&isflg==1){
//					num = surplus;
//					// edit s.inoue 2014/03/19
//					// startNum = startNum + 20;
//					startNum = startNum + 30;
//
//				}else if(startNum<0){
//					num = surplus;
//					startNum = 0;
//				}
//			}
//
//			// edit ver2 s.inoue 2009/06/11
//			// sort�p
//			/*
//			 * ���ڃ��X�g�����ԍ��ɏ]���ă\�[�g����B
//			 */
////			ArrayList<String> mergedCodes = new ArrayList<String> ();
////			Object objCodes[] =itemMergedCodes.toArray();
////			Arrays.sort(objCodes);
////
////			for (int itmCnt=0;itmCnt<objCodes.length;itmCnt++){
////				mergedCodes.add(objCodes[itmCnt].toString());
////			}
//
//			for(int kekkaCnt=0;kekkaCnt<num;kekkaCnt++){
//
//				// edit s.inoue 2014/03/19
//				if(itemCodes.size() == startNum)break;
//				
//				// �S�̂�KeyMap���珇�ɍ��ڃR�[�h���擾
//				String koumokuCd = itemCodes.get(startNum);
//
//				// ���񕪂��擾
//				Hashtable fstMap = (Hashtable)allMap.get("DATATYPE_LIST");
//				// �O�񕪂��擾
//				Hashtable secMap = (Hashtable)allMap.get("zDATATYPE_LIST");
//				// �O�X�񕪂��擾
//				Hashtable thrdMap = (Hashtable)allMap.get("zzDATATYPE_LIST");
//
//				/*** �Œ��� ***/
//				//���ږ� ��
//				Hashtable koumokuMeiMap = (Hashtable)allMap.get("koumokuName");
//				form.setField("KOUMOKU_" + Integer.toString(kekkaCnt+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//				//�P�� ��
//				Hashtable taniMap = (Hashtable)allMap.get("taniList");
//				form.setField("TANI_" + Integer.toString(kekkaCnt+1),(String)taniMap.get(itemCodes.get(startNum)));
//				//��l ��
//				Hashtable kijyunMap = (Hashtable)allMap.get("baseVal");
//				form.setField("KIJYUN_" + Integer.toString(kekkaCnt+1),(String)kijyunMap.get(itemCodes.get(startNum)));
//
//				/*** ���񕪏�� ***/
//				// �S�̂�KeyMap�ɍ���p��Key���܂܂�Ă��邩�ǂ���
//				// System.out.println(koumokuCd);
//				if (fstMap.containsKey(koumokuCd)){
//					//���� ��
//					String strKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"DATATYPE_LIST","valsesList","KEKA_","JISI_KBN",itemCodes);
//					if (strKekka.equals(""))
//						continue;
//					//H_L ��
//					hlOut(allMap, startNum, form, kekkaCnt, strKekka,"DATATYPE_LIST","HL_LIST","HL_","JISI_KBN",itemCodes);
//
//					// �����ڂ��O��Ɋ܂ޏꍇ
//					if (secMap.containsKey(koumokuCd)){
//						//����(�O��j
//						String strZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zDATATYPE_LIST","zValsesList","zKEKA_","zJISI_KBN",itemCodes);
//						if (strZKekka.equals(""))
//							continue;
//						//H_L(�O��j
//						hlOut(allMap, startNum, form, kekkaCnt, strZKekka,"zDATATYPE_LIST","zHL_LIST","zHL_","zJISI_KBN",itemCodes);
//					}
//
//					// �����ڂ��O��Ɋ܂ޏꍇ
//					if (thrdMap.containsKey(koumokuCd)){
//						//���ʁi�O�X��j
//						String strZZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zzDATATYPE_LIST","zzValsesList","zzKEKA_","zzJISI_KBN",itemCodes);
//						if (strZZKekka.equals(""))
//							continue;
//						//H_L�i�O�X��j
//						hlOut(allMap, startNum, form, kekkaCnt, strZZKekka,"zzDATATYPE_LIST","zzHL_LIST","zzHL_","zzJISI_KBN",itemCodes);
//					}
//				}else{
//
//					/*** �O�񕪏�� ***/
//					if (secMap.containsKey(koumokuCd)){
//						//���ʁi�O��j
//						String strZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zDATATYPE_LIST","zValsesList","zKEKA_","zJISI_KBN",itemCodes);
//						if (strZKekka.equals(""))
//							continue;
//						//H_L�i�O��j
//						hlOut(allMap, startNum, form, kekkaCnt, strZKekka,"zDATATYPE_LIST","zHL_LIST","zHL_","zJISI_KBN",itemCodes);
//					}
//
//					/*** �O�X�񕪏�� ***/
//					if (thrdMap.containsKey(koumokuCd)){
//						//���ʁi�O�X��j
//						String strZZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zzDATATYPE_LIST","zzValsesList","zzKEKA_","zzJISI_KBN",itemCodes);
//						if (strZZKekka.equals(""))
//							continue;
//						//H_L�i�O�X��j
//						hlOut(allMap, startNum, form, kekkaCnt, strZZKekka,"zzDATATYPE_LIST","zzHL_LIST","zzHL_","zzJISI_KBN",itemCodes);
//					}
//
//				}
//				startNum++;
//			}
//// del s.inoue 2014/03/18
////			String strSyoken = "";
////			strSyoken = strSyoken + cntSyoken(tmpSyoken,sStrNum);
////			if(strSyoken.equals("null")){
////				strSyoken = "";
////			}
////			form.setField("SYHOKEN", strSyoken);
//
//			stamp.setFormFlattening(true);
//
//			stamp.close();
//			reader.close();
//
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//
//		pdfPathList.add(tmpPath+Integer.toString(cnt));
//	}
//
//
//
//	private void createPdfBasedSyokenRoop(List kensaNenList, String tmpPath, Hashtable allMap,
//			int startNum,int cnt,int surplus,Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData,String ishiName,int pageNum,
//			int div,int isflg, List tmpSyoken, int sStrNum, List<String> itemCodes,int printSelect ) {
//
//		try {
//			PdfReader reader = new PdfReader(WORK_PDF_HITOKUTEI_PDF);
//
//			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tmpPath+Integer.toString(cnt)));
//			AcroFields form = stamp.getAcroFields();
//
//			//�w�b�_�[���ځA�y�[�W���Ȃǈ�
//			makeHaeder(kensaNenList, kojinData, kikanData, ishiName, pageNum, div, form, printSelect);
//
//			// edit s.inoue 2014/03/19
//			// int num = 20;
//			int num = 30;
//			int itemCodesSize = itemCodes.size();
//
//			if (itemCodesSize != 0) {
//				if (startNum > 0) {
//					// edit s.inoue 2014/03/19
//					// if (startNum % 20 != 0) {
//					if (startNum % 30 != 0) {
//						num = surplus;
//					}
//				}
//				else {
//					num = 0;
//				}
//			}
//			else {
//				num = 0;
//			}
//			
//			// edit s.inoue 2014/03/19
//			// startNum = (pageNum - 1) * 20;
//			startNum = (pageNum - 1) * 30;
//
//			for(int i=0;i<num;i++){
//
//				//���� ��
//				String strKekka = kekkaOut(allMap, startNum, form, i,"DATATYPE_LIST","valsesList","KEKA_","JISI_KBN",itemCodes);
//				// edit s.inoue 2009/11/30
//				if (strKekka.equals("")){
//					startNum++;continue;
//				}
//
//				// move s.inoue 2009/11/30
//				//���ږ� ��
//				Hashtable koumokuMeiMap = (Hashtable)allMap.get("koumokuName");
//				form.setField("KOUMOKU_" + Integer.toString(i+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//
//				//�P�� ��
//				Hashtable taniMap = (Hashtable)allMap.get("taniList");
//				form.setField("TANI_" + Integer.toString(i+1),(String)taniMap.get(itemCodes.get(startNum)));
//
//				//H_L ��
//				hlOut(allMap, startNum, form, i, strKekka,"DATATYPE_LIST","HL_LIST","HL_","JISI_KBN",itemCodes);
//
//				//��l ��
//				Hashtable kijyunMap = (Hashtable)allMap.get("baseVal");
//				form.setField("KIJYUN_" + Integer.toString(i+1),(String)kijyunMap.get(itemCodes.get(startNum)));
//
//				//�O��
//				Hashtable zTmpKekkaMap = (Hashtable)allMap.get("zValsesList");
//				if(!zTmpKekkaMap.isEmpty()){
//					//����(�O��j
//					String strZKekka = kekkaOut(allMap, startNum, form, i,"zDATATYPE_LIST","zValsesList","zKEKA_","zJISI_KBN",itemCodes);
//
//					// edit s.inoue 2009/11/30
//					if (strZKekka.equals("")){
//						startNum++;continue;
//					}
//					//H_L(�O��j
//					hlOut(allMap, startNum, form, i, strZKekka,"zDATATYPE_LIST","zHL_LIST","zHL_","zJISI_KBN",itemCodes);
//				}
//
//				//�O�X��
//				Hashtable zzTmpKekkaMap = (Hashtable)allMap.get("zzValsesList");
//				if(!zzTmpKekkaMap.isEmpty()){
//					//���ʁi�O�X��j
//					String strZZKekka = kekkaOut(allMap, startNum, form, i,"zzDATATYPE_LIST","zzValsesList","zzKEKA_","zzJISI_KBN",itemCodes);
//
//					// edit s.inoue 2009/11/30
//					if (strZZKekka.equals("")){
//						startNum++;continue;
//					}
//					//H_L�i�O�X��j
//					hlOut(allMap, startNum, form, i, strZZKekka,"zzDATATYPE_LIST","zzHL_LIST","zzHL_","zzJISI_KBN",itemCodes);
//				}
//
//				startNum++;
//			}
//
//			// edit h.yoshitama 2009/09/15
//			String strSyoken = "";
////			strSyoken = strSyoken + cntSyoken(tmpSyoken,sStrNum - 4);
//			strSyoken = strSyoken + cntSyoken(tmpSyoken,sStrNum - 3);
//			if(strSyoken.equals("null")){
//				strSyoken = "";
//			}
//			form.setField("SYHOKEN", strSyoken);
//
//			stamp.setFormFlattening(true);
//
//			stamp.close();
//			reader.close();
//
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//
//		pdfPathList.add(tmpPath+Integer.toString(cnt));
//	}
//
//	// add s.inoue 2014/03/18
//	private int createPdfPageSyokenRoop(List kensaNenList, String tmpPath, Hashtable allMapShoken,
//			int startNum,int cnt,int surplus,Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData,String ishiName,int pageNum,
//			int div,int isflg,List<String> tmpSyokenUmu, List<TreeMap<String, String>> tmpSyoken,List<String> itemCodes,int printSelect ) {
//
//		try {
//			PdfReader reader = new PdfReader(WORK_SHOKEN_PDF);
//
//			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(WORK_SHOKEN_PDF+Integer.toString(cnt)));
//			AcroFields form = stamp.getAcroFields();
//
//			//�w�b�_�[���ځA�y�[�W���Ȃǈ�
//			makeHaeder(kensaNenList, kojinData, kikanData, ishiName, pageNum, div, form, printSelect);
//
//			int cntKoumoku = 0;
//			
//			// 1page0-14�Ń��[�v�i���ڃJ�E���g�͗����ł����jstartNum������(�}�[�W)
//			for(int i=0;i<15;i++){
//				
//				if (cntKoumoku == 14)break;
//				if (itemCodes.size()==startNum)break;
//				
//				//����
//				Hashtable TmpKekkaMap = (Hashtable)allMapShoken.get("valsesList");
//				Hashtable kekkaMap = (Hashtable)allMapShoken.get("valsesList");
//				String strtmpKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//				Hashtable zkekkaMap = (Hashtable)allMapShoken.get("zValsesList");
//				String strztmpKekka = (String)zkekkaMap.get(itemCodes.get(startNum));
//				Hashtable zzkekkaMap = (Hashtable)allMapShoken.get("zzValsesList");
//				String strtzzmpKekka = (String)zzkekkaMap.get(itemCodes.get(startNum));
//				//����
//				if(!TmpKekkaMap.isEmpty()){
//					// edit s.inoue 2014/04/25
//					if (strtmpKekka != null){
//					// if (!strtmpKekka.equals("")){
//						if( i>0 )
//						cntKoumoku++;
//						//���� ��
//						String strKekka = kekkaShokenOut(allMapShoken, startNum, form, cntKoumoku,"DATATYPE_LIST","valsesList","KEKA_","JISI_KBN",itemCodes);
//						//���ږ� ��
//						Hashtable koumokuMeiMap = (Hashtable)allMapShoken.get("koumokuName");
//						form.setField("KOUMOKU_" + Integer.toString(cntKoumoku+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//						//���ږ� ��
//						form.setField("STR_" + Integer.toString(cntKoumoku+1),"�����f");
//						//���ږ� ��
//						String sDate = kensaNenList.get(0).toString();
//						form.setField("DATE_" + Integer.toString(cntKoumoku+1),sDate);
//					}
//				}
//				
//				//�O��
//				Hashtable zTmpKekkaMap = (Hashtable)allMapShoken.get("zValsesList");
//				if(!zTmpKekkaMap.isEmpty()){
//					if (strztmpKekka!= null){
//						
//						// add s.inoue 2014/05/26
//						if (kensaNenList.size() > 1){
//						
//							if( i>=0 && strtmpKekka!= null)
//							cntKoumoku++;
//							String strZKekka = kekkaShokenOut(allMapShoken, startNum, form, cntKoumoku,"zDATATYPE_LIST","zValsesList","KEKA_","zJISI_KBN",itemCodes);
//							//���ږ� ��
//							Hashtable koumokuMeiMap = (Hashtable)allMapShoken.get("koumokuName");
//							form.setField("KOUMOKU_" + Integer.toString(cntKoumoku+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//							form.setField("STR_" + Integer.toString(cntKoumoku+1),"�O���f");
//							String sDate = kensaNenList.get(1).toString();
//							form.setField("DATE_" + Integer.toString(cntKoumoku+1),sDate);
//						}
//					}
//				}
//
//				//�O�X��
//				Hashtable zzTmpKekkaMap = (Hashtable)allMapShoken.get("zzValsesList");
//				if(!zzTmpKekkaMap.isEmpty()){
//					if (strtzzmpKekka!= null){
//						
//						// add s.inoue 2014/05/26
//						if (kensaNenList.size() > 2){
//							
//							if( i>=0 && strztmpKekka!= null)
//							cntKoumoku++;
//							String strZZKekka = kekkaShokenOut(allMapShoken, startNum, form, cntKoumoku,"zzDATATYPE_LIST","zzValsesList","KEKA_","zzJISI_KBN",itemCodes);
//							//���ږ� ��
//							Hashtable koumokuMeiMap = (Hashtable)allMapShoken.get("koumokuName");
//							form.setField("KOUMOKU_" + Integer.toString(cntKoumoku+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//							form.setField("STR_" + Integer.toString(cntKoumoku+1),"�O�X���f");
//							String sDate = kensaNenList.get(2).toString();
//							form.setField("DATE_" + Integer.toString(cntKoumoku+1),sDate);
//						}
//					}
//				}
//				
//				startNum++;
//			}
//
//			stamp.setFormFlattening(true);
//
//			stamp.close();
//			reader.close();
//
//		}
//		catch (Exception e){
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		pdfPathList.add(WORK_SHOKEN_PDF+Integer.toString(cnt));
//		return startNum;
//	}
//
//	/* ���͏���l�A�����l
//	 * ���ʒl����������������f���AHL���肷��
//	 *    @param �p�^�[���i���l�̂݁j
//	 *    @param ��l���
//	 *    @param ��l����
//	 *    @param ����
//	 *    @return H:��l��荂,L:��l����,NULL:���̑�
//	 */
//	private static String HLHantei(
//			String inputRangeLowValue,
//			String inputRangeHighValue,
//			String result)
//	{
//		boolean inInputRange = true;
//
//		String hlHanteiFlg = null;
//
//
//			double resultValue = 0;
//
//			try {
//				resultValue = Double.parseDouble(result);
//
//				/* ���͏���l�A�����l */
//				/* ���ʒl�����͉����l�����������ꍇ */
//				if (inputRangeLowValue != null && ! inputRangeLowValue.isEmpty()) {
//					try {
//						double inputLowLimit = Double.parseDouble(inputRangeLowValue);
//						// edit s.inoue 20081125 ���E�܂܂�
//						//if (resultValue <= inputLowLimit ) {
//						if (resultValue < inputLowLimit ) {
//							inInputRange = false;
//							hlHanteiFlg = "L";
//						}
//
//					} catch (NumberFormatException e) {
//						/* ���l�ɕϊ��ł��Ȃ������ꍇ */
//						e.printStackTrace();
//					}
//				}
//
//				if (inInputRange) {
//					/* ���͏���l�͈͊O */
//					if (inputRangeHighValue != null && ! inputRangeHighValue.isEmpty()) {
//						try {
//							double inputHighLimit = Double.parseDouble(inputRangeHighValue);
//							// edit s.inoue 20081125 ���E�܂܂�
//							//if (resultValue >= inputHighLimit ) {
//							if (resultValue > inputHighLimit ) {
//								inInputRange = false;
//								hlHanteiFlg = "H";
//							}
//						} catch (NumberFormatException e) {
//							/* ���l�ɕϊ��ł��Ȃ������ꍇ */
//							e.printStackTrace();
//						}
//					}
//				}
//
//			} catch (NumberFormatException e) {
//				/* ���l�ɕϊ��ł��Ȃ������ꍇ */
//				e.printStackTrace();
//			}
//
//		return hlHanteiFlg;
//	}
//
//	/**
//	 * ����茒�f�̃w�b�_�[
//	 * �y�[�W�A�N�����Ȃǂ���
//	 *
//	 * @param kensaNenList
//	 * @param kojinData
//	 * @param kikanData
//	 * @param ishiName
//	 * @param pageNum
//	 * @param div
//	 * @param form
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private void makeHaeder(List kensaNenList, Hashtable<String, String> kojinData, Hashtable<String, String>
//	kikanData, String ishiName, int pageNum, int div, AcroFields form, int printSelect)
//	throws IOException, DocumentException {
//		form.setField("KENSHIN" , kikanData.get("KIKAN_NAME"));
//		form.setField("KANA_NAME",kojinData.get("KANANAME"));
//		form.setField("ISI_NAME", ishiName);
//		form.setField("NENGAPPI", isVal(kensaNenList,0));
//		form.setField("kNENGAPPI",isVal(kensaNenList,0));
//		form.setField("zNENGAPPI",isVal(kensaNenList,1));
//		form.setField("zzNENGAPPI", isVal(kensaNenList,2));
//
//		// edit s.inoue 2010/01/05
//		// �y�[�W�ݒ�i��������ύX�Ή��j
//		int sabunPageNum = 1;
//		int sabunDivNum = 1;
//		if (printSelect == 2) {
//			sabunPageNum++;
//			sabunDivNum++;
//		}
//		form.setField("PAGE_KO",  Integer.toString(pageNum+sabunPageNum));
//		form.setField("PAGE_OYA", Integer.toString(div+sabunDivNum));
//	}
//
//	/**
//	 * ���������s����ꍀ�ږ��A�l�Ő��^����B
//	 *
//	 *
//	 * @param syokenList
//	 */
//	private String cntSyoken(List<TreeMap<String, String>> syokenList,int sStrNum) {
//
//		TreeMap<String, String> tmpSyoken = new TreeMap<String, String>();
//		StringBuffer buffer = new StringBuffer();
//		// edit h.yoshitama 2009/09/15
//		// add ver2 s.inoue 2009/07/15
//		//sStrNum=sStrNum-1;
////		for(int i = 0;i<4;i++){
//		for(int i = 0;i<3;i++){
//
//			try {
//				tmpSyoken = syokenList.get(sStrNum);
//				buffer.append(tmpSyoken.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//				buffer.append(JApplication.LINE_SEPARATOR);
//				buffer.append(tmpSyoken.get(PrintDefine.COLUMN_NAME_KEKA_TI));
//				buffer.append(JApplication.LINE_SEPARATOR);
//				buffer.append(JApplication.LINE_SEPARATOR);
//				tmpSyoken.put(Integer.toString(i+1), buffer.toString());
//			} catch (IndexOutOfBoundsException e) {
//				// TODO �����������ꂽ catch �u���b�N
//				buffer.append("");
//			}
//			sStrNum++;
//		}
//		return buffer.toString();
//	}
//
//	/**
//	 * ���ʒl���o�͂���B
//	 *
//	 * @param allMap
//	 * @param startNum
//	 * @param form
//	 * @param i
//	 * @param dataType
//	 * @param valList
//	 * @param setForm
//	 * @param itemCodes
//	 * @return
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private String kekkaOut(Hashtable allMap, int startNum, AcroFields form,
//			int i, String dataType, String valList, String setForm, String jisiKbn, List itemCodes)
//	throws IOException, DocumentException {
//
//		//�f�[�^�^�C�v�擾
//		Hashtable taniMap = (Hashtable)allMap.get(dataType);
//		String strDataType = (String)taniMap.get(itemCodes.get(startNum));
//
//		// ���{�敪�擾
//		Hashtable HsjisiKbn = (Hashtable)allMap.get(jisiKbn);
//		String strjisiKbn = (String)HsjisiKbn.get(itemCodes.get(startNum));
//
//		// add s.inoue 2009/11/27
//		if (strjisiKbn == null || strDataType == null)
//			return "";
//
//		if (strjisiKbn.equals(""))
//			return "";
//
//		String strKekka = "";
//
//		//	���ʎ擾
//		switch (Integer.valueOf(strjisiKbn)) {
//			case 0:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_0;
//				break;
//			case 1:
//				Hashtable kekkaMap = (Hashtable)allMap.get(valList);
//				strKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//				break;
//			case 2:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_2;
//				break;
//		}
//
//		//���ڃR�[�h�擾
//		String strCd =  (String)itemCodes.get(startNum);
//
//		if(strKekka==null||strKekka.equals("")){
//			return "";
//		}
//
//		// edit h.yoshitama 2009/09/11
//		/*
//		 * ���ʒl�����l���R�[�h���𔻕ʂ��A�K�؂Ȍ��ʒl��\������
//		 */
//		if (Integer.valueOf(strjisiKbn) == 1 ){
//
//			double kekatiDouble = 0;
//			/*
//			 * ���� 1=>����, 2=>�R�[�h, 3=>������
//			 */
//
//			if (strDataType.equals("1") || strDataType.equals("2")) {
//
//				// Hashtable kekkaMap = (Hashtable)allMap.get(valList);
//				// strKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//
//				kekatiDouble = Double.parseDouble(strKekka);
//			}
//
//			if (strDataType.equals("2")) {
//				String code_name = addMedical.getCodeName(strCd, (int)kekatiDouble);
//
//				code_name = code_name.replaceFirst("\\d+:", "");
//
////				if (code_name != null && code_name.length() >= 5) {
////					code_name = code_name.substring(0, 5);
////				}
//
//				form.setField(setForm + Integer.toString(i+1), code_name);
//			} else {
////				if (strDataType.equals("3")) {
////					if (strKekka != null && strKekka.length() >= 5) {
////						strKekka = strKekka.substring(0, 5);
////					}
////				}
//
//				form.setField(setForm + Integer.toString(i+1),strKekka);
//			}
//		}else{
//
//			form.setField(setForm + Integer.toString(i+1),strKekka);
//		}
//
//		return strKekka;
//
//	}
//
//	// add s.inoue 2014/02/26
//	private String kekkaShokenOut(Hashtable allMap, int startNum, AcroFields form,
//			int i, String dataType, String valList, String setForm, String jisiKbn, List itemCodes)
//	throws IOException, DocumentException {
//		
//		//�f�[�^�^�C�v�擾
//		Hashtable taniMap = (Hashtable)allMap.get(dataType);
//		String strDataType = (String)taniMap.get(itemCodes.get(startNum));
//
//		// ���{�敪�擾
//		Hashtable HsjisiKbn = (Hashtable)allMap.get(jisiKbn);
//		String strjisiKbn = (String)HsjisiKbn.get(itemCodes.get(startNum));
//
//		if (strjisiKbn == null || strDataType == null)
//			return "";
//		if (strjisiKbn.equals(""))
//			return "";
//
//		String strKekka = "";
//
//		//	���ʎ擾
//		switch (Integer.valueOf(strjisiKbn)) {
//			case 0:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_0;
//				break;
//			case 1:
//				Hashtable kekkaMap = (Hashtable)allMap.get(valList);
//				strKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//				break;
//			case 2:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_2;
//				break;
//		}
//
//		//���ڃR�[�h�擾
//		String strCd =  (String)itemCodes.get(startNum);
//
//		if(strKekka==null||strKekka.equals("")){
//			return "";
//		}
//
//		/*
//		 * ���ʒl�����l���R�[�h���𔻕ʂ��A�K�؂Ȍ��ʒl��\������
//		 */
//		if (Integer.valueOf(strjisiKbn) == 1 ){
//
//			double kekatiDouble = 0;
//			/*
//			 * ���� 1=>����, 2=>�R�[�h, 3=>������
//			 */
//
//			if (strDataType.equals("1") || strDataType.equals("2")) {
//				kekatiDouble = Double.parseDouble(strKekka);
//			}
//
//			if (strDataType.equals("2")) {
//				String code_name = addMedical.getCodeName(strCd, (int)kekatiDouble);
//
//				code_name = code_name.replaceFirst("\\d+:", "");
//
////				if (code_name != null && code_name.length() >= 5) {
////					code_name = code_name.substring(0, 5);
////				}
//
//				form.setField(setForm + Integer.toString(i+1), code_name);
//			} else {
////				if (strDataType.equals("3")) {
////					if (strKekka != null && strKekka.length() >= 5) {
////						strKekka = strKekka.substring(0, 5);
////					}
////				}
//
//				form.setField(setForm + Integer.toString(i+1),strKekka);
//			}
//		}else{
//
//			form.setField(setForm + Integer.toString(i+1),strKekka);
//		}
//
//		return strKekka;
//
//	}
//	
//	/**
//	 * HL���f�[�^�^�C�v�Ŕ��肵�Ĉ�
//	 *
//	 * @param allMap
//	 * @param startNum
//	 * @param form
//	 * @param i
//	 * @param strKekka
//	 * @param strDataTypeList
//	 * @param strHlList
//	 * @param strHl
//	 * @param itemCodes
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private void hlOut(Hashtable allMap, int startNum, AcroFields form,
//			int i, String strKekka, String strDataTypeList, String strHlList, String strHl, String strJisiKbn,List itemCodes)
//	throws IOException, DocumentException {
//		try {
//
//			//�f�[�^�^�C�v�擾
//			Hashtable taniMap = (Hashtable)allMap.get(strDataTypeList);
//			String strDataType = (String)taniMap.get(itemCodes.get(startNum));
//
//			// ��l����A�����擾
//			Hashtable uppLimit = (Hashtable)allMap.get(BASEVALUPPLIMIT);
//			String struppLimit = (String)uppLimit.get(itemCodes.get(startNum));
//
//			Hashtable dwnLimt = (Hashtable)allMap.get(BASEVALDWNLIMIT);
//			String strdwnLimit = (String)dwnLimt.get(itemCodes.get(startNum));
//
//			int dataType = Integer.parseInt(strDataType);
//
//			// ���{�敪�擾
//			Hashtable HsjisiKbn = (Hashtable)allMap.get(strJisiKbn);
//			String strjisiKbn = (String)HsjisiKbn.get(itemCodes.get(startNum));
//
//			Integer jisiKbn= Integer.valueOf(strjisiKbn);
//
//			if(!strKekka.isEmpty()){
//				/*
//				 * ���� 1=>���l, 2=>�R�[�h, 3=>������
//				 */
//				/* �R�[�h�l */
//				if (dataType == 2) {
//					/* ���l�܂��͕����� */
//				} else {
//					/* ���l */
//					// ���{�̂�(���ʒl�������)
//					if (dataType == 1 && jisiKbn == 1) {
//						if (strKekka.startsWith(".")) {
//							strKekka = "0" + strKekka;
//						}
//
//						/* HL���� */
//						String hl = "";
//						Hashtable hlMap = (Hashtable)allMap.get(strHlList);
//						String hlCode = (String)hlMap.get(itemCodes.get(startNum));
//						hl = HLHantei(strdwnLimit,struppLimit,strKekka);
//
//						form.setField(strHl + Integer.toString(i + 1), hl);
//					}
//				}
//			}
//		} catch (NumberFormatException ee) {
//		}
//	}
//
//	private String replaseNenGaPii(List kensaNenList,int i ) {
//		String kensaNengappi;
//		try {
//			kensaNengappi = (String)kensaNenList.get(i);
//			kensaNengappi = kensaNengappi.replaceAll("�N", "");
//			kensaNengappi = kensaNengappi.replaceAll("��", "");
//			kensaNengappi = kensaNengappi.replaceAll("��", "");
//		} catch (RuntimeException e) {
//			return "";
//		}
//		return kensaNengappi;
//	}
//
//	private String isVal(List valList,int i ) {
//		String strVal;
//		try {
//			strVal = (String)valList.get(i);
//		} catch (RuntimeException e) {
//			return "";
//		}
//		return strVal;
//	}
//
//	private boolean isVal(String val ) {
//		try {
//			if(val.equals("")){
//				return false;
//			}
//			if(val==null){
//				return false;
//			}
//		} catch (RuntimeException e) {
//			return false;
//		}
//		return true;
//	}
//}
