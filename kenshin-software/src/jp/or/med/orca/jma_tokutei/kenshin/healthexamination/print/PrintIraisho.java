package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Iraisho;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PrintIraisho extends JTKenshinPrint {

	/* Modified 2008/09/16 ��  */
	/* --------------------------------------------------- */
//	private static final String OUT_IRAISYO_PDF = ".\\Data\\PDF\\out_iraisyo.pdf";
	private static final String OUT_IRAISYO_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_iraisyo.pdf";	
	/* Modified 2008/08/07 �ጎ  */
	/* --------------------------------------------------- */
	/* Modified 2008/09/16 ��  */
//	private static final String TEMPLATE_IRAISYO_PDF = ".\\Work\\PDF\\iraisyo.pdf";
	/* --------------------------------------------------- */
	private static final String TEMPLATE_IRAISYO_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"iraisyo.pdf";
	/* --------------------------------------------------- */
	private Hashtable<String, String> kikanData = new Hashtable<String, String>();

	
	// �˗����f�[�^
	//private Iraisho iraisho = new Iraisho();

	PrinterJob job = PrinterJob.getPrinterJob();

	File printFile = null;

	
	/* MOD 2008/07/30 yabu */
//	private TreeSet<String> saiketubiList = new TreeSet<String>(); 
	private ArrayList<String> saiketubiList = new ArrayList<String>(); 
	
	private int pageBunshi = 1; 

	/* ADD 2008/07/30 yabu */
	//PDF���̌����ʂŁA���ڕ��󎚂���
	private List fileList = new ArrayList();

	
	/* �����˗����� 1 �y�[�W������̍ő匏�� */
	private final int MAX_KENSU_PER_PAGE = 20;
//	private final int MAX_KENSU_PER_PAGE = 10;

	/**
	 * �˗��[�̍��ڂ��쐬����
	 */
	public File printKekka() {

		File file = null;

		File outfile = new File(".\\Data\\PDF");
		outfile.mkdirs();

		//�@�֏��o��
		Kikan tmpKikan = (Kikan) printData.get("Kikan");
		kikanData = tmpKikan.getPrintKikanData();

		/* MOD 2008/07/30 yabu */
		//�̌������ς�鐔���J�E���g
//		for(int i=0; i<keyList.size();i++){
//			saiketubiList.add(keyList.get(i).substring(0,8));
//		}

		Iterator ite = keyList.iterator();
		while(ite.hasNext()){
			saiketubiList.add((String)ite.next());
			
		}

//		int saiketuCnt =0;
//		for(Iterator  ite = saiketubiList.iterator() ; ite.hasNext();){
//		String saiketuBi = (String)ite.next();
//		for( saiketuCnt=saiketuCnt; saiketuCnt<keyList.size();saiketuCnt++){
//		if(keyList.get(saiketuCnt).substring(0,8).contains(saiketuBi)){
//		iraiDateCounter.put(saiketuBi, saiketuCnt);
//		}
//		//saiketubiList.add(keyList.get(i).substring(0,8));
//		}

//		}

//		for(int i = 0; i<keyList.size();i++){
//		iraisho = (Iraisho)printIraiData.get(keyList.get(i));
//		Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

//		// �̌���
//		iraiData.get("KENSA_NENGAPI");

////		// ��f�������ԍ�
////		form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));
////		// �J�i����
////		form.setField("KANA_MEI_"+Integer.toString(i+1),iraiData.get("KANANAME"));
////		// ���N����
////		form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
////		// ����
////		form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
////		// ���l
////		form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));

//		}

		/* �����N�������L�[�A���̓��̈˗����f�[�^�̃��X�g��l�Ɏ��� Map */
		HashMap<String, List> nengapiIraishoMap = new HashMap<String, List>();

		/* �˗����f�[�^�̃��X�g */
		List<Iraisho> iraishoList = null;
		/* �������̌����N���� */
		String currentNengapi = null;

		/* ���y�[�W�� */
		int allPageCount = 0;
		/* 1 �y�[�W���Ƃ̍��ڂ̃C���f�b�N�X */
//		int indexPerPage = 0;
		int indexPerPage = 1;

//		iraishoList = new ArrayList<Iraisho>();

		/* MOD 2008/07/30 yabu */
//		for(int i = 0; i<keyList.size();i++){
		for(int i = 0; i<saiketubiList.size();i++){
			String saiketubiAndUketsukeId = saiketubiList.get(i);
//			String saiketubiAndUketsukeId = keyList.get(i);
			Iraisho iraisho = (Iraisho)printIraiData.get(saiketubiAndUketsukeId);
			Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

			// �̌���
			String nengapi = iraiData.get("KENSA_NENGAPI");
			if (currentNengapi == null) {
				currentNengapi = nengapi;

				/* 
				 * �ŏ��͈�x�V����
				 * �V���� List �� ��������B
				 */
//				iraishoList.add(iraisho);
//				nengapiIraishoMap.put(nengapi, iraishoList);

				iraishoList = new ArrayList<Iraisho>();
				nengapiIraishoMap.put(nengapi, iraishoList);
			}

			/* 
			 * ���݂̓��t���A����܂ł̓��t�ƈقȂ�ꍇ�́A
			 * �V���� List �� ��������B
			 */
			if (! currentNengapi.equals(nengapi)) {
				iraishoList = new ArrayList<Iraisho>();
				nengapiIraishoMap.put(nengapi, iraishoList);

				/* ���y�[�W�����C���N�������g����B */
				++allPageCount;
				indexPerPage = 0;

				/* ADD 2008/07/30 yabu */
				currentNengapi = nengapi;
			}
			/* ADD 2008/07/30 yabu */
			indexPerPage++;

			iraishoList.add(iraisho);

			/* 1 �y�[�W���Ƃ̌������A�ő�l�𒴂����ꍇ�́A���y�[�W����
			 * �C���N�������g����B */
			if (indexPerPage > MAX_KENSU_PER_PAGE - 1) {
				indexPerPage = 0;
				++allPageCount;
			}
		}

		/* MOD 2008/07/30 yabu */
		//PDF���̌����ʂŁA���ڕ��󎚂���
		//List fileList = new ArrayList();

		//�̌������ЂƎ�ނ����Ȃ��ꍇ
//		if(allPageCount==0){
//		int pdfCnt = 0;
//		if(keyList.size()>0){
//		if(keyList.size()/20>0){
//		max =keyList.size()/20;
//		amari =keyList.size()%20;
//		pdfCnt++;
//		fileList=createPdf(max,amari,pdfCnt,jyushinsuu);
//		}else{
//		max =keyList.size()/20;
//		amari =keyList.size()%20;
//		pdfCnt++;
//		fileList=createPdf(max,amari,pdfCnt,jyushinsuu);
//		}
//		}else{
//		//	fileList=createPdf(max,amari,pdfCnt,jyushinsuu);
//		}
//		}else{
////		for (int i = 0; i < allPageCount; i++) {
////		for (int i = 0; i < nengapiIraishoMap.size(); i++) {
//		int i = 0;
//		for (Iterator<String> ite = nengapiSet.iterator();ite.hasNext();){
////		fileList=createPdfDeffDay(ite.next());

//		String nengapi = ite.next();

//		fileList=createPdfDeffDay(nengapiIraishoMap.get(nengapi),i);
//		i++;
//		}

		Set<String> nengapiSet = nengapiIraishoMap.keySet();
//		Iterator<String> ite = nengapiSet.iterator();
		int i = 0;
//		while (ite.hasNext()) {
//		String nengapi = ite.next();

//		fileList=createPdfDeffDay(nengapiIraishoMap.get(nengapi),i);
//		i++;				
//		}

//		for (String nengapi : nengapiSet) {
//		fileList.add(createPdfDeffDay(nengapiIraishoMap.get(nengapi),i));
//		i++;				
//		}
//		}
		int cnt = 0;
		for (String nengapi : nengapiSet) {
			/* MOD 2008/07/30 yabu */
//			fileList.add(createPdfDeffDay(nengapiIraishoMap.get(nengapi),cnt,allPageCount));
			createPdfDeffDay(nengapiIraishoMap.get(nengapi),cnt,allPageCount);
			cnt++;				
			pageBunshi++;
		}

		printFile = new File(OUT_IRAISYO_PDF);

		//PDF����
		unitPdf(fileList);

		return printFile;

	}


	/**
	 * PDF�쐬���\�b�h(���t���Ⴄ�Ƃ��j
	 */
	private void createPdfDeffDay(Object iraisyo, int cnt,int allPageCount) {
//		ArrayList<String> filePathList = new ArrayList<String>();
//		String filePath = "";
		PdfReader reader;
		PdfStamper stamp = null;
		List iraisyoList =  (List)iraisyo;
		int koumokuSuu =  iraisyoList.size();
		int pageSuu = koumokuSuu/20;
		int pageAmari = koumokuSuu%20;

		try {

			if(pageSuu>0){
				/* MOD 2008/07/30 yabu */
				for(int i = 0;i<pageSuu;i++){
//				for(int i = 0;i<=pageSuu;i++){
					reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
					/* MOD 2008/07/30 yabu */
//					stamp = new PdfStamper(reader, new FileOutputStream(
//							PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i)));
					stamp = new PdfStamper(reader, new FileOutputStream(
							PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi)));
					AcroFields form = stamp.getAcroFields();
					//�t�H�[���Z�b�g
					setFormDiffDay(iraisyoList,form, pageSuu, pageAmari,allPageCount,koumokuSuu);

					stamp.setFormFlattening(true);
					stamp.close();
					/* MOD 2008/07/30 yabu */
//					filePath=PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i);
					fileList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi));
					pageBunshi++;
				}
				/* MOD 2008/07/30 yabu */
				if(pageAmari>0){
					reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
					stamp = new PdfStamper(reader, new FileOutputStream(
							PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi)));
					AcroFields form = stamp.getAcroFields();
					//�t�H�[���Z�b�g
					setFormDiffDay(iraisyoList,form, -1, pageAmari,allPageCount,koumokuSuu);

					stamp.setFormFlattening(true);
					stamp.close();
					fileList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi));
					pageBunshi++;
				}
				
			}else{
				reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
				/* MOD 2008/07/30 yabu */
//				stamp = new PdfStamper(reader, new FileOutputStream(
//						PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(cnt)));
				stamp = new PdfStamper(reader, new FileOutputStream(
						PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi)));
				AcroFields form = stamp.getAcroFields();
				//�t�H�[���Z�b�g
				setFormDiffDay(iraisyoList,form, pageSuu, pageAmari,allPageCount,-1);

				stamp.setFormFlattening(true);
				stamp.close();
				/* MOD 2008/07/30 yabu */
//				filePath=PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(cnt);
				fileList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				stamp.close();
			} catch (DocumentException e) {
				//�X���[����K�v�Ȃ�
			} catch (IOException e) {
				//�X���[����K�v�Ȃ�
			}
		}
//		return filePathList;
	}

	/**
	 * FormText�ɒl���Z�b�g
	 * 
	 * @param iraisyoList
	 * @param form
	 * @param pageSuu
	 * @param amari ���ڂ̗]��
	 * @param allPageCount ���y�[�W��
	 * @param koumokuSuu ������ŕ����y�[�W����ꍇ�̍��ڐ�
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void setFormDiffDay(List iraisyoList, AcroFields form,
			int pageSuu, int amari, int allPageCount,int koumokuSuu) 
	throws IOException, DocumentException {

		Iraisho iraisho;
		/*
		 * ���ڈ󎚃��[�v�̍ŏ��̈ʒu
		 */
		int roopi = koumokuSuu;

		/*
		 * ���̓��̑��y�[�W���� 1 �y�[�W�̏ꍇ�A���ڐ��� 0 �܂��� amari�A
		 * ���̓��̑��y�[�W���� 1 �y�[�W�ȏ�̏ꍇ�A���ڐ��� 20*�y�[�W�� 20���ږ����ꍇ�͑����ڐ��A
		 */
		int roopSize = 0;
		if(pageSuu>0){
			roopSize = 20*pageSuu;
			roopi = 0;
		}else if(amari>0&&pageSuu<0) {
			roopSize = amari;
			roopi = roopi -1 ;
		}else if(amari>0) {
			roopSize = amari;
			koumokuSuu = 0;
			roopi = 0;
		}

		
		// �����@�֖�
//		form.setField("K_KIKAN_NAME",iraiData.get(""));
		// ���f���{�@�֖�
		form.setField("K_J_KIKAN_NAME",kikanData.get("KIKAN_NAME"));

		// �̌���
		iraisho = (Iraisho)iraisyoList.get(0);
		Hashtable<String, String> irai = iraisho.getPrintIraiData();
		form.setField("SAIKETUBI",irai.get("KENSA_NENGAPI"));

		//irai����擾����ʒu�p
		int roop = roopi;
		/*
		 *  ���ڐ������[�v
		 */
		for(int i = 0; i < roopSize ;i++){
			
			iraisho = (Iraisho)iraisyoList.get(roop);
			Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

			/*
			 * printIraiData����˗����𒊏o
			 */
			// ��f�������ԍ�
			form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));

			// �J�i����
			form.setField("KANA_MEI_"+Integer.toString(i+1),JZenkakuKatakanaToHankakuKatakana.Convert(iraiData.get("KANANAME")));
			// ���N����
			form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
			// ����
			form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
			// ���l
			form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));
			
			roop++;

		}
		
		//�y�[�W
		form.setField("PAGE", Integer.toString(pageBunshi)+"�^"+Integer.toString(allPageCount+1));

	}
//	/**
//	* PDF�쐬���\�b�h(���t�����Ȃ��Ƃ��A�\���j
//	*/
//	private List createPdf(int max, int amari, int pdfCnt,int jyushinsuu ) {
//	ArrayList<String> filePathList = new ArrayList<String>();
//	PdfReader reader;
//	PdfStamper stamp = null; 
//	try {

//	for(int i = 0;i<pdfCnt;i++){
//	reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
//	stamp = new PdfStamper(reader, new FileOutputStream(
//	PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i)));
//	AcroFields form = stamp.getAcroFields();
//	//�t�H�[���Z�b�g
//	setForm(form, max, amari,jyushinsuu);

//	stamp.setFormFlattening(true);
//	stamp.close();
//	filePathList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i));
//	}

//	} catch (Exception e) {
//	e.printStackTrace();
//	} finally{
//	try {
//	stamp.close();
//	} catch (DocumentException e) {
//	//�X���[����K�v�Ȃ�
//	} catch (IOException e) {
//	//�X���[����K�v�Ȃ�
//	}
//	}
//	return filePathList;
//	}


//	/**
//	* @param form
//	* @throws IOException
//	* @throws DocumentException
//	*/
//	private void setForm(AcroFields form,int max, int amari, int jyushinsuu) 
//	throws IOException, DocumentException {

//	Iraisho iraisho;
//	int size = 0;
//	if(max>0){
//	size = 20;
//	}else if(amari>0) {
//	size = amari;
//	}else if(jyushinsuu>0){
//	size = jyushinsuu;
//	}

//	for(int i = 0; i<size;i++){
//	iraisho = (Iraisho)printIraiData.get(keyList.get(i));
//	Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

//	/*
//	* printIraiData����˗����𒊏o
//	*/
//	// �̌���
//	form.setField("SAIKETUBI"+Integer.toString(i+1),iraiData.get("KENSA_NENGAPI"));

//	// ��f�������ԍ�
//	form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));
//	// �J�i����
//	form.setField("KANA_MEI_"+Integer.toString(i+1),iraiData.get("KANANAME"));
//	// ���N����
//	form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
//	// ����
//	form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
//	// ���l
//	form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));

//	}
//	}


	/**
	 * �����ɂ��o�c�e���������邩���f���Č�������B
	 */
	private void unitPdf(List filePathList) {
		int fileAddCnt = filePathList.size();

		try {
			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(printFile));
			for(int i = 0;i<fileAddCnt;i++){
				PdfReader reader = new PdfReader((String)filePathList.get(i));
				copy.addDocument(reader);
				new File((String)filePathList.get(i)).deleteOnExit();
				new File((String)filePathList.get(i)).deleteOnExit();
				reader.close();
			}

			copy.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
