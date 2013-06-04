package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * ����ɗp���錒�f���ʃf�[�^���󂯓n���N���X
 */
public class KenshinKekka {

	private static Logger logger = Logger.getLogger(KenshinKekka.class);

	//���f���ʃf�[�^
	private ArrayList<Hashtable<String, Hashtable<String, String>>> KenshinKekkaData
	= new ArrayList<Hashtable<String, Hashtable<String, String>>>();
	//���f�N����
	private int KensaNengapiNum = 0;
	private String[] uketukeID = new String[3];
	private String[] KensaNengapi = new String[3];
	//����ς݌������ڃR�[�h�ꗗ
	private ArrayList<String>PrintedCD = new ArrayList<String>();
	//����ς݌������ڃR�[�h���Z�b�g����Ă��邩�̃t���O
	private boolean flg = false;
	private String comment = null;

	// ���{�敪
	private static final String KEKKA_OUTPUT_JISI_KBN_0 = "�����{";
	private static final String KEKKA_OUTPUT_JISI_KBN_2 = "����s�\";

	/**
	 * �R���X�g���N�^
	 * ����ς݌������ڃR�[�h�ꗗ�𐶐�����
	 * @see	setKenshinCD()
	 */
	public KenshinKekka() {
		this.setKenshinCD();
		this.flg = true;
	}

	/**
	 * ���f���ʃf�[�^ set
	 * DB����擾
	 * @param	Hashtable<String, String>
	 * 			Keys => HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI, HKNJANUM
	 * @return	boolean
	 */
	public boolean setPrintKenshinKekkaSQL(Hashtable<String, String> data){
		ArrayList<Hashtable<String,String>> resultCount = null;
		ArrayList<Hashtable<String,String>> resultList = null;
		ArrayList<Hashtable<String,String>> resultHistoryList = null;

		Hashtable<String,String> resultItem = null;
		Hashtable<String,String> tmp1 = new Hashtable<String,String>();
		Hashtable<String, Hashtable<String,String>> tmp2 = new Hashtable<String, Hashtable<String,String>>();

		/*
		 * ���f�N�������擾
		 * ����A�O��A�O�X��
		 */
//		try{
// edit ver2 s.inoue 2009/06/03
//			Query =
//			"SELECT DISTINCT KENSA_NENGAPI FROM T_KENSAKEKA_SONOTA WHERE UKETUKE_ID = "
//			+ JQueryConvert.queryConvert(data.get("UKETUKE_ID")) +
//			" ORDER BY KENSA_NENGAPI DESC";

// edit s.inoue 2010/04/06
			// �w�背�R�[�h���ߋ��R�����\��
			// ���N�x���ǂ���
//			String kensaNenGappi = data.get("KENSA_NENGAPI");
//			boolean blnYear = FiscalYearUtil.getJugeThisYear(kensaNenGappi);

//			// 1���f�[�^���ǂ���
//			if (blnYear) {
//				// 1.��tID����R�t��ID���擾
//				sbNengapi.append(" SELECT KENSA_NENGAPI,GET_NAYOSE.UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI TS, ");
//				sbNengapi.append(" (SELECT UKETUKE_ID FROM T_NAYOSE TN ");
//				sbNengapi.append(" WHERE TN.NAYOSE_NO = ");
//				sbNengapi.append(" (SELECT NAYOSE_NO FROM T_NAYOSE TN WHERE TN.UKETUKE_ID = ");
//				sbNengapi.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
//				sbNengapi.append("  )) GET_NAYOSE WHERE TS.UKETUKE_ID = GET_NAYOSE.UKETUKE_ID ");
//				sbNengapi.append("  ORDER BY KENSA_NENGAPI DESC");
//			}else{
//				sbNengapi.append("SELECT DISTINCT KENSA_NENGAPI,UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID = ");
//				sbNengapi.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
//				sbNengapi.append(" ORDER BY KENSA_NENGAPI DESC");
//			}
//		} catch (Exception ex){
//			logger.warn(ex.getMessage());
//			return false;
//		}

		// edit s.inoue 2010/04/13
		int cntNayose = 0;

		try {
			StringBuilder sbCount = new StringBuilder();
			sbCount.append("SELECT COUNT(UKETUKE_ID) N_COUNT FROM T_NAYOSE ");
			sbCount.append(" WHERE UKETUKE_ID = ");
			sbCount.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));

			resultCount = JApplication.kikanDatabase.sendExecuteQuery(sbCount.toString());

			Hashtable<String, String> Ncount = resultCount.get(0);
			cntNayose = Integer.parseInt(Ncount.get("N_COUNT").toString());

		}catch(Exception ex){
			logger.warn(ex.getMessage());
			return false;
		}

		// edit s.inoue 2010/04/13
		if (cntNayose == 0){
			// edit s.inoue 2010/04/06
			// ���ʒʒm�\-�I�������������ߋ��R��
			try {
				StringBuilder sbHistoryKey = new StringBuilder();

				// 1.��tID����擾
				sbHistoryKey.append(" SELECT  TS.KENSA_NENGAPI, TS.UKETUKE_ID ");
				sbHistoryKey.append(" FROM T_KENSAKEKA_TOKUTEI TS");
				sbHistoryKey.append(" WHERE TS.UKETUKE_ID = ");
				sbHistoryKey.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
				sbHistoryKey.append("  ORDER BY KENSA_NENGAPI DESC");

				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbHistoryKey.toString());

			}catch (Exception ex){
				logger.warn(ex.getMessage());
				return false;
			}
		}else{
			// edit s.inoue 2010/04/06
			// ���ʒʒm�\-�I�������������ߋ��R��
			try {
				StringBuilder sbHistoryKey = new StringBuilder();

				// 1.��tID����R�t��ID���擾
				sbHistoryKey.append(" SELECT KENSA_NENGAPI,GET_NAYOSE.UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI TS, ");
				sbHistoryKey.append(" (SELECT UKETUKE_ID FROM T_NAYOSE TN ");
				sbHistoryKey.append(" WHERE TN.NAYOSE_NO = ");
				sbHistoryKey.append(" (SELECT NAYOSE_NO FROM T_NAYOSE TN WHERE TN.UKETUKE_ID = ");
				sbHistoryKey.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
				sbHistoryKey.append("  )) GET_NAYOSE WHERE TS.UKETUKE_ID = GET_NAYOSE.UKETUKE_ID ");
				sbHistoryKey.append("  ORDER BY KENSA_NENGAPI DESC");

				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbHistoryKey.toString());

			}catch (Exception ex){
				logger.warn(ex.getMessage());
				return false;
			}
		}

		// 2.�擾�����f�[�^����w�背�R�[�h�̌��f���{�����
		// �ߋ��R���̃L�[������o��Where��𐶐�
		int resultSize = 0;
		StringBuilder sbWhere = new StringBuilder();

		String kenshinDate = data.get("KENSA_NENGAPI");
		try {
			for (int i = 0; i < resultList.size(); i++) {
				Hashtable<String, String> history_rec = resultList.get(i);
				if (Integer.parseInt(history_rec.get("KENSA_NENGAPI"))
						<= Integer.parseInt(kenshinDate)){
					resultSize++;
					if ( i == resultList.size() -1){
						sbWhere.append(JQueryConvert.queryConvert(history_rec.get("UKETUKE_ID")));
					}else{
						sbWhere.append(JQueryConvert.queryConvertAppendComma(history_rec.get("UKETUKE_ID")));
					}
				}
			}
		}catch (Exception ex){
			logger.warn(ex.getMessage());
			return false;
		}

		// 3.�擾�L�[�̎�tID���w�肵�ăf�[�^���擾����
		try {
			StringBuilder sbHistoryData = new StringBuilder();

			sbHistoryData.append("SELECT DISTINCT KENSA_NENGAPI,UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI ");
			sbHistoryData.append(" WHERE UKETUKE_ID IN ( ");
			sbHistoryData.append(sbWhere.toString());
			sbHistoryData.append(" ) ");
			sbHistoryData.append(" ORDER BY KENSA_NENGAPI DESC");
			resultList = JApplication.kikanDatabase.sendExecuteQuery(sbHistoryData.toString());

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

// del s.inoue 2010/04/06
//			if(resultSize == 0){
//				sbNengapi.delete(0, sbNengapi.length());
//				sbNengapi.append("SELECT DISTINCT KENSA_NENGAPI,UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID = ");
//				sbNengapi.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
//				sbNengapi.append(" ORDER BY KENSA_NENGAPI DESC");
//				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbNengapi.toString());
//				resultSize = resultList.size();
//			}

		try{

			int j = 0;
			resultSize = resultList.size();

			for (int i =0; i < resultSize; i++){
				resultItem = resultList.get(i);
				/*
				 * ���f�N������ێ�
				 * �����œn���ꂽ���f�N�������Â���������i�[
				 */
				this.uketukeID[j] = resultItem.get("UKETUKE_ID");
				this.KensaNengapi[j] = resultItem.get("KENSA_NENGAPI");
				j++;
				this.KensaNengapiNum = j;
				if(j == 3){
					break;
				}
			}
		} catch (Exception ex){
			logger.warn(ex.getMessage());
		}

		/*
		 * ����̌��f�N�����̌������ʂ��擾
		 * �擾����̂͌������ʒl�̂�
		 */
		StringBuilder sbKekka = new StringBuilder();
		StringBuilder sbTokutei = new StringBuilder();

		for(int i=0; i < this.KensaNengapiNum; i++){
			try{

				/*
				 * ���ڃR�[�h�A���ږ��A���ʒl�A��l�͈́A�f�[�^�^�C�v�A�P�ʁA�f�[�^�^�C�v�R�[�h��
				 */
				if (sbKekka.length() > 0){
					sbKekka.delete(0, sbKekka.length()-1);
				}
				sbKekka.append("SELECT ");
				sbKekka.append(" T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, T_KENSAKEKA_SONOTA.KEKA_TI AS KEKA_TI, T_KENSAKEKA_SONOTA.JISI_KBN AS JISI_KBN,");
				sbKekka.append(" T_KENSAKEKA_SONOTA.H_L AS H_L , T_KENSHINMASTER.BIKOU AS BIKOU, ");
				sbKekka.append(" T_KENSHINMASTER.DS_JYOUGEN AS DS_JYOUGEN, T_KENSHINMASTER.DS_KAGEN AS DS_KAGEN, ");
				sbKekka.append(" T_KENSHINMASTER.JS_JYOUGEN AS JS_JYOUGEN, T_KENSHINMASTER.JS_KAGEN AS JS_KAGEN, ");
				// edit ver2 s.inoue 2009/09/17
				sbKekka.append(" T_KENSHINMASTER.MAX_BYTE_LENGTH, ");
				sbKekka.append(" T_KENSHINMASTER.KOUMOKU_NAME AS KOUMOKU_NAME, T_KENSHINMASTER.KIJYUNTI_HANI AS KIJYUNTI_HANI, T_KENSHINMASTER.DATA_TYPE AS DATA_TYPE,");
				sbKekka.append(" T_KENSHINMASTER.TANI AS TANI ");
				sbKekka.append(" From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD) ");
				sbKekka.append(" WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = ");
				// edit ver2 s.inoue 2009/06/03
				//sbKekka.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
				sbKekka.append(JQueryConvert.queryConvert(uketukeID[i]));
				sbKekka.append(" AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI = " );
				// add ver2 s.inoue 2009/07/15
				sbKekka.append(JQueryConvert.queryConvert(KensaNengapi[i]));
				sbKekka.append(" AND T_KENSHINMASTER.HKNJANUM = ");
				sbKekka.append(JQueryConvert.queryConvert(data.get("HKNJANUM")));

			} catch (NullPointerException e){
				logger.warn(e.getMessage());
				return false;
			}

			try{
				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbKekka.toString());
			}catch(SQLException e){
				logger.warn(e.getMessage());
			}

			/*
			 * ���ڃR�[�h, ���ʒl
			 */
			resultSize = resultList.size();
			for (int j =0; j < resultSize; j++){
				// System.out.println( j + "�s��");
				try{
					resultItem = resultList.get(j);
					tmp1.clear();
					tmp1.put("KOUMOKU_CD", resultItem.get("KOUMOKU_CD"));
					tmp1.put("KOUMOKU_NAME", resultItem.get("KOUMOKU_NAME"));

					// ���{�敪 0:�����{,1:���{,2:����s�\
					tmp1.put("KEKA_TI", resultItem.get("KEKA_TI"));
					tmp1.put("JISI_KBN", resultItem.get("JISI_KBN"));
					tmp1.put("KIJYUNTI_HANI", resultItem.get("KIJYUNTI_HANI"));
					tmp1.put("DATA_TYPE", resultItem.get("DATA_TYPE"));
					tmp1.put("TANI", resultItem.get("TANI"));
					tmp1.put("BIKOU", resultItem.get("BIKOU"));
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}

				// edit ver2 s.inoue 2009/09/17
				int data_type = 0;

				try{
					data_type = Integer.valueOf(resultItem.get("DATA_TYPE")).intValue();
					if (data_type == 2){
						String code_name = this.getCodeName(resultItem.get("KOUMOKU_CD"), Integer.valueOf(resultItem.get("KEKA_TI")));
						tmp1.put("CODE_NAME", code_name);
					} else {
						tmp1.put("CODE_NAME", "");
					}

				} catch(NumberFormatException e) {
					tmp1.put("CODE_NAME", "");
				}

				try{
					int H_L = Integer.valueOf(resultItem.get("H_L")).intValue();
					if (H_L == 1){
						tmp1.put("H_L", "L");
					} else if (H_L == 2){
						tmp1.put("H_L", "H");
					}

				} catch(NumberFormatException e) {
					tmp1.put("H_L", "");
				}

				// edit ver2 s.inoue 2009/09/17
				try{
					if (data_type == 1){
						String koumokuFormat = resultItem.get("MAX_BYTE_LENGTH").toString();
						tmp1.put("MAX_BYTE_LENGTH",koumokuFormat);
					}else{
						tmp1.put("MAX_BYTE_LENGTH", "");
					}
				} catch(NumberFormatException e){
					tmp1.put("MAX_BYTE_LENGTH", "");
				}

				try{//�j������l
					double DS_JYOUGEN = Double.valueOf(resultItem.get("DS_JYOUGEN")).doubleValue();
					String ds_jyougen = Double.toString(DS_JYOUGEN);
					tmp1.put("DS_JYOUGEN",ds_jyougen.toString());
				} catch(NumberFormatException e) {
					tmp1.put("DS_JYOUGEN", "");
				}

				try{//�j�������l
					double DS_KAGEN = Double.valueOf(resultItem.get("DS_KAGEN")).doubleValue();
					String ds_kagen = Double.toString(DS_KAGEN);
					tmp1.put("DS_KAGEN", ds_kagen);
				} catch(NumberFormatException e) {
					tmp1.put("DS_KAGEN", "");
				}

				try{//��������l
					double JS_JYOUGEN = Double.valueOf(resultItem.get("JS_JYOUGEN")).doubleValue();
					String js_jyougen = Double.toString(JS_JYOUGEN);
					tmp1.put("JS_JYOUGEN", js_jyougen);
				} catch(NumberFormatException e) {
					tmp1.put("JS_JYOUGEN", "");
				}

				try{//���������l
					double JS_KAGEN = Double.valueOf(resultItem.get("JS_KAGEN")).doubleValue();
					String js_kagen = Double.toString(JS_KAGEN);
					tmp1.put("JS_KAGEN", js_kagen);
				} catch(NumberFormatException e) {
					tmp1.put("JS_KAGEN", "");
				}

				/*
				 * tmp2�ɑ��
				 */
				tmp2.put(resultItem.get("KOUMOKU_CD"), (Hashtable<String, String>) tmp1.clone());
			}

			// i 0:����,1:�O��,2:�O�X��
			this.KenshinKekkaData.add(i, (Hashtable<String, Hashtable<String,String>>)tmp2.clone());
			// add ver2 s.inoue 2009/07/15
			tmp2.clear();
			if (sbTokutei.length() > 0){
				sbTokutei.delete(0, sbTokutei.length() -1);
			}
			sbTokutei.append(" select ");
			sbTokutei.append(" HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI, K_P_NO, HANTEI_NENGAPI, TUTI_NENGAPI, KENSA_CENTER_CD, ");
			sbTokutei.append(" KEKA_INPUT_FLG, SEIKYU_KBN, KOMENTO, NENDO, UKETUKE_ID, NYUBI_YOUKETU, SYOKUZEN_SYOKUGO, HENKAN_NITIJI ");
			sbTokutei.append(" from T_KENSAKEKA_TOKUTEI as tokutei");
			sbTokutei.append(" where  tokutei.UKETUKE_ID = ");
			// edit ver2 s.inoue 2009/06/03
			//buffer.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
			sbTokutei.append(JQueryConvert.queryConvert(uketukeID[i]));
			sbTokutei.append(" AND tokutei.KENSA_NENGAPI = ");
			sbTokutei.append(JQueryConvert.queryConvert(KensaNengapi[i]));

			String query = sbTokutei.toString();
			try{
				resultList = JApplication.kikanDatabase.sendExecuteQuery(query);
			}catch(SQLException e){
				logger.warn(e.getMessage());
			}

			// edit ver2 s.inoue 2009/07/16
			if (i == 0 &&
				resultList != null && ! resultList.isEmpty()) {
				this.comment = resultList.get(0).get("KOMENTO");
			}
		}
		try {
			this.KensaNengapi[0] = this.KensaNengapi[0].substring(0, 4) + "�N" + this.KensaNengapi[0].substring(4, 6) + "��" + this.KensaNengapi[0].substring(6, 8) + "��";
			// edit s.inoue 2010/05/07
			if (this.KensaNengapi[1] != null)
			this.KensaNengapi[1] = this.KensaNengapi[1].substring(0, 4) + "�N" + this.KensaNengapi[1].substring(4, 6) + "��" + this.KensaNengapi[1].substring(6, 8) + "��";

			if (this.KensaNengapi[2] != null)
			this.KensaNengapi[2] = this.KensaNengapi[2].substring(0, 4) + "�N" + this.KensaNengapi[2].substring(4, 6) + "��" + this.KensaNengapi[2].substring(6, 8) + "��";

		} catch (Exception e){
			logger.warn(e.getMessage());
		}
		return true;
	}

	/**
	 * ���ڃR�[�h�����擾
	 * @param KOUMOKU_CD, CODE_NUM(KEKA_TI)
	 *
	 * @return CODE_NAME
	 */
	public String getCodeName(String koumoku_cd, int code_num) {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;
		StringBuilder sb = null;
		String code_name = new String();

		try{
			sb = new StringBuilder();
			sb.append("SELECT CODE_NAME ");
			sb.append(" FROM T_DATA_TYPE_CODE ");
			sb.append(" WHERE KOUMOKU_CD = ");
			sb.append(JQueryConvert.queryConvert(koumoku_cd));
			sb.append(" AND CODE_NUM = ");
			sb.append( JQueryConvert.queryConvert(String.valueOf(code_num)));
		} catch (NullPointerException e){
			return "";
		}
		try{
			Result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
			ResultItem = Result.get(0);
			/*
			 * ���f�N������ێ�
			 */
			code_name = ResultItem.get("CODE_NAME");
		} catch (SQLException e){
			logger.warn(e.getMessage());
		}

		return code_name;
	}

	/**
	 * ���f���ʃf�[�^ get
	 * @return	ArrayList<Hashtable<String, Hashtable<String, String>>> KenshinKekkaData
	 * 			<KENSANENGAPI, <KOUMOKU_CD, <KEKATI, String>>
	 */
	public ArrayList<Hashtable<String, Hashtable<String, String>>> getPrintKenshinKekkaData() {
		return this.KenshinKekkaData;
	}
	/**
	 * ���f�N�������擾
	 * @return KensaNengapi[]
	 */
	public String[] getKensaNengapi(){
		return this.KensaNengapi;
	}

	/**
	 * ����ςݍ��ځ@add
	 * @param void
	 */
	public void setKenshinCD() {
		/*
		 * ��y�[�W��
		 */
		//������
		this.PrintedCD.add("9N056000000000011");
		//���o�Ǐ�
		this.PrintedCD.add("9N061000000000011");
		//���o�Ǐ�
		this.PrintedCD.add("9N066000000000011");
		//�i����
		this.PrintedCD.add("9N736000000000011");
		//�g��
		this.PrintedCD.add("9N001000000000001");
		//�̏d
		this.PrintedCD.add("9N006000000000001");
		/*
		 * ���́i�����j		9N016160100000001
		 * ���́i���Ȕ���j	9N016160200000001
		 * ���́i���Ȑ\���j	9N016160300000001
		 */
		this.PrintedCD.add("9N016160100000001");
		this.PrintedCD.add("9N016160200000001");
		this.PrintedCD.add("9N016160300000001");
		//BMI
		this.PrintedCD.add("9N011000000000001");
		/*
		 * ���k�������i1��ځj	9A751000000000001
		 * ���k�������i2��ځj	9A752000000000001
		 * ���k�������i���̑��j	9A755000000000001
		 */
		this.PrintedCD.add("9A751000000000001");
		this.PrintedCD.add("9A752000000000001");
		this.PrintedCD.add("9A755000000000001");
		/*
		 * �g���������i���ځj	9A761000000000001
		 * �g���������i���ځj	9A762000000000001
		 * �g���������i���̑��j	9A765000000000001
		 */
		this.PrintedCD.add("9A761000000000001");
		this.PrintedCD.add("9A762000000000001");
		this.PrintedCD.add("9A765000000000001");
		/*
		 * �������b�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327101
		 * �������b�i���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327201
		 * �������b�i���̑��j								3F015000002399901
		 */
		this.PrintedCD.add("3F015000002327101");
		this.PrintedCD.add("3F015000002327201");
		this.PrintedCD.add("3F015000002399901");
		/*
		 * HDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327101
		 * HDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327201
		 * HDL-�R���X�e���[��-�i���̑��j						3F070000002399901
		 */
		this.PrintedCD.add("3F070000002327101");
		this.PrintedCD.add("3F070000002327201");
		this.PrintedCD.add("3F070000002399901");
		/*
		 * LDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327101
		 * LDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327201
		 * LDL-�R���X�e���[��-�i���̑��j						3F077000002399901
		 */
		this.PrintedCD.add("3F077000002327101");
		this.PrintedCD.add("3F077000002327201");
		this.PrintedCD.add("3F077000002399901");
		/*
		 * GOT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B035000002327201
		 * GOT�i���̑��j									3B035000002399901
		 */
		this.PrintedCD.add("3B035000002327201");
		this.PrintedCD.add("3B035000002399901");
		/*
		 * GPT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B045000002327201
		 * GPT�i���̑��j									3B045000002399901
		 */
		this.PrintedCD.add("3B045000002327201");
		this.PrintedCD.add("3B045000002399901");
		/*
		 * y-GTP�i���z�����x�@�iJSCC�W�����Ή��@�j�j			3B090000002327101
		 * y-GTP�i���̑��j									3B090000002399901
		 */
		this.PrintedCD.add("3B090000002327101");
		this.PrintedCD.add("3B090000002399901");
		/*
		 * �󕠎������i�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j�j			3D010000001926101
		 * �󕠎������i���z�����x�@�i�u�h�E���_���y�f�@�j�j			3D010000002227101
		 * �󕠎������i���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j�j	3D010000001927201
		 * �󕠎������i���̑��j								3D010000001999901
		 */
		this.PrintedCD.add("3D010000001926101");
		this.PrintedCD.add("3D010000002227101");
		this.PrintedCD.add("3D010000001927201");
		this.PrintedCD.add("3D010000001999901");
		/*
		 * �Ӹ�����A1c	�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j	3D045000001906202
		 * �Ӹ�����A1c	HPLC(�s���蕪�揜��HPLC�@�j			3D045000001920402
		 * �Ӹ�����A1c	�y�f�@							3D045000001927102
		 * �Ӹ�����A1c	���̑�							3D045000001999902
		 */
		this.PrintedCD.add("3D045000001906202");
		this.PrintedCD.add("3D045000001920402");
		this.PrintedCD.add("3D045000001927102");
		this.PrintedCD.add("3D045000001999902");
		/*
		 * ��	�������@�i�@�B�ǂݎ��j						1A020000000191111
		 * ��	�������@�i�ڎ��@�j							1A020000000190111
		 */
		this.PrintedCD.add("1A020000000191111");
		this.PrintedCD.add("1A020000000190111");
		/*
		 * �`��	�������@�i�@�B�ǂݎ��j						1A010000000191111
		 * �`��	�������@�i�ڎ��@�j							1A010000000190111
		 */
		this.PrintedCD.add("1A010000000191111");
		this.PrintedCD.add("1A010000000190111");

		/*
		 * 2�y�[�W��
		 */
		//�Ԍ�����
		this.PrintedCD.add("2A020000001930101");
		//���F�f��
		this.PrintedCD.add("2A030000001930101");
		//�w�}�g�N���b�g�l
		this.PrintedCD.add("2A040000001930102");
		//�S�d�}����
		this.PrintedCD.add("9A110160800000049");
		//��ꌟ������
		this.PrintedCD.add("9E100160900000049");
		//���^�{���b�N�V���h���[������
		this.PrintedCD.add("9N501000000000011");
		//��t�̔��f
		this.PrintedCD.add("9N511000000000049");
		//���f������t��
		this.PrintedCD.add("9N516000000000049");

		/* 7.�n���A19.����ʁA21.�����K�����P�A22.�ی��w����ǉ�*/
		/* ��L��4���ڒǉ��ɂ�荀�ڔԍ��C��*/
		/*
		 * 4�y�[�W��
		 */
		//1-1 �������������𕞗p���Ă���
		this.PrintedCD.add("9N701000000000011");
		//1-2 �C���X�������˖��͌��������������g�p���Ă���
		this.PrintedCD.add("9N706000000000011");
		//1-3 �R���X�e���[�����������𕞗p���Ă���
		this.PrintedCD.add("9N711000000000011");
		//4 ��t����A�]�����i�]�o���A�]�[�Ǔ��j�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
		this.PrintedCD.add("9N716000000000011");
		//5 ��t����A�S���a�i���S�ǁA�S�؍[�ǁj�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
		this.PrintedCD.add("9N721000000000011");
		//6 ��t����A�����̐t�s�S�ɂ������Ă���Ƃ���ꂽ��A���Ái�l�H���́j���󂯂����Ƃ�����
		this.PrintedCD.add("9N726000000000011");
		//7 ��t����n���Ƃ���ꂽ���Ƃ�����
		this.PrintedCD.add("9N731000000000011");
		//8 ���݁A���΂����K���I�ɋz���Ă���
		this.PrintedCD.add("9N736000000000011");
		//9 �Q�O�΂̎��̑̏d����P�O�L���ȏ㑝�����Ă���
		this.PrintedCD.add("9N741000000000011");
		//10 �P��R�O���ȏ�̌y�����������^�����T�Q���ȏ�A�P�N�ȏ���{���Ă���
		this.PrintedCD.add("9N746000000000011");
		//11 ���퐶���ɂ����ĕ��s���͓����̐g�̊������P���P���Ԉȏ���{���Ă���
		this.PrintedCD.add("9N751000000000011");
		//12 ������̓����Ɣ�r���ĕ������x������
		this.PrintedCD.add("9N756000000000011");
		//13 ���̂P�N�Ԃő̏d�̑������}�Rkg�ȏ゠��
		this.PrintedCD.add("9N761000000000011");
		//14 ���H���E�h�J�H���E�Ȃ���H��������
		this.PrintedCD.add("9N766000000000011");
		//15 �A�Q�O�̂Q���Ԉȓ��ɗ[�H���Ƃ邱�Ƃ��T�ɂR��ȏ゠��
		this.PrintedCD.add("9N771000000000011");
		//16 ��H��ԐH������
		this.PrintedCD.add("9N776000000000011");
		//17 ���H�𔲂����Ƃ�����
		this.PrintedCD.add("9N781000000000011");
		//18 �قږ����A���R�[������������
		this.PrintedCD.add("9N786000000000011");
		//19 �������1��������̈����
		this.PrintedCD.add("9N791000000000011");
		//20 �����ŋx�{�������Ă���
		this.PrintedCD.add("9N796000000000011");
		//21 �^���␶�������̐����K�������P���Ă݂悤�Ǝv���܂���
		this.PrintedCD.add("9N801000000000011");
		//21 �����K���a�̉��P�ɂ��ĕی��w�����󂯂�@�����΁A���p���܂���
		this.PrintedCD.add("9N806000000000011");
	}


	/**
	 * ����ς݃R�[�h�@add
	 * @param KenshinKoumoku_CD
	 * @return boolean
	 */
	public boolean addKenshinCD(String CD) {
		this.PrintedCD.add(CD);
		return true;
	}

	/**
	 * ����ς݃R�[�h�@check
	 * 3�y�[�W�ڂɂ́A1�y�[�W�E2�y�[�W�E4�y�[�W�̍��ڃR�[�h�����������̂��������
	 * ���̂��߁A���錟�����ڃR�[�h�����̃y�[�W�Ɋ܂܂�Ă��邩�m�F����
	 * @param KenshinKoumoku_CD
	 * @return	boolean already printed is true
	 */
	public boolean checkKenshinCD(String CD){
		/*
		 * flg���m�F
		 */
		if (!this.flg) {
			this.setKenshinCD();
			this.flg = true;
		}
		/*
		 * �R�[�h���܂܂�Ă��邩���m�F
		 */
		if (this.PrintedCD.contains(CD)){
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ���ʒl�A���{�敪�A�����A����l���擾����
	 * @param CD:�������ڃR�[�h,KenshinDateNum:���f���{��,isMale:�j,��
	 * @return KEKA_TI,JISI_KBN,XX_KAGEN,XX_JYOUGEN
	 */
	public Hashtable<String,String> getKekkData(ArrayList<String> CD, int KenshinDateNum, boolean isMale) {

		boolean jisiExists = false;
		boolean sokuExists = false;
		boolean miExists = false;
		String jisiKbn ="";
		String kekati ="";
		String jisikekati ="";
		String Kagen ="";
		String Jogen ="";

		String jisiKagen ="";
		String jisiJogen ="";
		String sonotaKagen ="";
		String sonotaJogen ="";

		String sokuJogen ="";
		String miJogen="";
		String sokuKagen="";
		String miKagen="";

		Hashtable<String,String> kekkaTbl = new Hashtable<String, String>();

		if (this.KenshinKekkaData.size() > KenshinDateNum) {

			for(int i=0;i < CD.size(); i++){

				Hashtable<String, Hashtable<String, String>> kekkaData =
					this.KenshinKekkaData.get(KenshinDateNum);

				/*
				 * �������ڃR�[�h���܂܂�Ă��邩���`�F�b�N����
				 */
				if(kekkaData.containsKey(CD.get(i))){

					jisiKbn = kekkaData.get(CD.get(i)).get("JISI_KBN");
					kekati = kekkaData.get(CD.get(i)).get("KEKA_TI");

					// edit ver2 s.inoue 2009/09/17
					// ��l�t�H�[�}�b�g�Ή�
					String kekkaFormat = kekkaData.get(CD.get(i)).get("MAX_BYTE_LENGTH");
					kekkaFormat = kekkaFormat != null ? kekkaFormat: "";

					if (isMale){
						Kagen = kekkaData.get(CD.get(i)).get("DS_KAGEN");
						Kagen = JValidate.validateKensaKekkaDecimal(Kagen,kekkaFormat);
						Kagen = Kagen != null ? Kagen:"";
						Jogen = kekkaData.get(CD.get(i)).get("DS_JYOUGEN");
						Jogen = JValidate.validateKensaKekkaDecimal(Jogen,kekkaFormat);
						Jogen = Jogen != null ? Jogen:"";
					}else{
						Kagen = kekkaData.get(CD.get(i)).get("JS_KAGEN");
						Kagen = JValidate.validateKensaKekkaDecimal(Kagen,kekkaFormat);
						Kagen = Kagen != null ? Kagen:"";
						Jogen = kekkaData.get(CD.get(i)).get("JS_JYOUGEN");
						Jogen = JValidate.validateKensaKekkaDecimal(Jogen,kekkaFormat);
						Jogen = Jogen != null ? Jogen:"";
					}

					if (!kekati.equals("") && jisiKbn.equals("1")){
						jisiExists = true;
						jisikekati = kekati;
						jisiKagen = Kagen;
						jisiJogen = Jogen;

					}else if (kekati.equals("") && jisiKbn.equals("1")){
						sonotaKagen = Kagen;sonotaJogen = Jogen;
						continue;
					}else if (jisiKbn.equals("2")){
						sokuKagen = Kagen;sokuJogen = Jogen;
						sokuExists = true;
					}else if (jisiKbn.equals("0")){
						miKagen = Kagen;miJogen = Jogen;
						miExists = true;
					}
				}
			}

			if (jisiExists){
				kekkaTbl.put("KEKA_TI", jisikekati);
				kekkaTbl.put("JISI_KBN", "1");
				kekkaTbl.put("KAGEN", jisiKagen);
				kekkaTbl.put("JYOGEN", jisiJogen);
			}else if (sokuExists) {
				kekkaTbl.put("KEKA_TI", KEKKA_OUTPUT_JISI_KBN_2);
				kekkaTbl.put("JISI_KBN", "2");
				kekkaTbl.put("KAGEN", sokuKagen);
				kekkaTbl.put("JYOGEN", sokuJogen);
			}else if (miExists) {
				kekkaTbl.put("KEKA_TI", KEKKA_OUTPUT_JISI_KBN_0);
				kekkaTbl.put("JISI_KBN", "0");
				kekkaTbl.put("KAGEN", miKagen);
				kekkaTbl.put("JYOGEN", miJogen);
			}else{
				kekkaTbl.put("KEKA_TI", "");
				kekkaTbl.put("JISI_KBN", "1");
				kekkaTbl.put("KAGEN", sonotaKagen);
				kekkaTbl.put("JYOGEN", sonotaJogen);
			}

		}

		return kekkaTbl;
	}

	/**
	 * ������JLA10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̌��ʒl��Ԃ�
	 * ���ʒl���A�����񂩃R�[�h���𔻕ʂ��āA���ʒl���f�[�^�^�C�v�R�[�h����Ԃ�
	 * @param	�������ڃR�[�h, ���f�N�����ԍ��i����=>0�A�O��=>1�A�O�X��=>2�j
	 */
	public String getKekati(ArrayList<String> CD, int KenshinDateNum) {

		String ret = "";

		if (this.KenshinKekkaData.size() > KenshinDateNum) {

			for(int i=0;i < CD.size(); i++){

				Hashtable<String, Hashtable<String, String>> kekkaData =
					this.KenshinKekkaData.get(KenshinDateNum);

				/*
				 * �������ڃR�[�h���܂܂�Ă��邩���`�F�b�N����
				 */
				if(kekkaData.containsKey(CD.get(i))){
					/*
					 * �܂܂�Ă���
					 * ���ʒl�������񂩁A�R�[�h�������ʂ���
					 */
					String kekati = kekkaData.get(CD.get(i)).get("KEKA_TI");
					if(! kekati.isEmpty()){
						ret = kekati;
						break;
					}
				}
			}
		}

		return ret;
	}

	public String getJisiKbn(ArrayList<String> CD, int KenshinDateNum) {

	String ret = "";

	if (this.KenshinKekkaData.size() > KenshinDateNum) {

		for(int i=0;i < CD.size(); i++){

			Hashtable<String, Hashtable<String, String>> kekkaData =
				this.KenshinKekkaData.get(KenshinDateNum);

			/*
			 * �������ڃR�[�h���܂܂�Ă��邩���`�F�b�N����
			 */
			if(kekkaData.containsKey(CD.get(i))){
				if(kekkaData.get(CD.get(i)).get("KOUMOKU_CD").equals(CD.get(i))){
					/*
					 * �܂܂�Ă���
					 * ���ʒl�������񂩁A�R�[�h�������ʂ���
					 * ���{�敪(0:�����{,1:���{,2:����s�\)
					 */
					String jisiKbn = kekkaData.get(CD.get(i)).get("JISI_KBN");
					if(! jisiKbn.isEmpty()){
						ret = jisiKbn;
						break;
					}
				}
			}
		}
	}

	return ret;
	}

	public ArrayList<String> getArrJisiKbn(ArrayList<String> CD, int KenshinDateNum) {

		ArrayList<String> retArrLst= null;

		if (this.KenshinKekkaData.size() > KenshinDateNum) {

			for(int i=0;i < CD.size(); i++){

				Hashtable<String, Hashtable<String, String>> kekkaData =
					this.KenshinKekkaData.get(KenshinDateNum);

				/*
				 * �������ڃR�[�h���܂܂�Ă��邩���`�F�b�N����
				 */
				if(kekkaData.containsKey(CD.get(i))){
						/*
						 * �܂܂�Ă���
						 * ���ʒl�������񂩁A�R�[�h�������ʂ���
						 * ���{�敪(0:�����{,1:���{,2:����s�\)
						 */
						String jisiKbn = kekkaData.get(CD.get(i)).get("JISI_KBN");
						if(! jisiKbn.isEmpty()){
							retArrLst.add(jisiKbn);
						}
				}
			}
		}

		return retArrLst;
	}

	/**
	 * ������JLAC10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̒j���̊�l������Ԃ�
	 * @param	�������ڃR�[�h
	 */
	public String getDsKagen(ArrayList<String> CD, int KenshinDateNum) {

		String key = "DS_KAGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);

		return item;
	}

	/**
	 * ���ʊ֘A�̍��ڒl���擾����B
	 */
	private String getKekkaItem(ArrayList<String> CD, int KenshinDateNum, String key) {
		String value = "";

		for(int i=0;i < CD.size(); i++){
			/*
			 * �������ڃR�[�h���܂܂�Ă��邩���`�F�b�N����
			 */
//			if (this.KenshinKekkaData.contains(KenshinDateNum)) {
//				Hashtable<String, Hashtable<String, String>> dateNum = this.KenshinKekkaData.get(KenshinDateNum);
//				String cdItem = CD.get(i);
//
//				if(dateNum.containsKey(cdItem)){
//					//�܂܂�Ă���
//					value = KenshinKekkaData.get(KenshinDateNum).get(cdItem).get(key);
//					break;
//				}
//			}

			if (this.KenshinKekkaData != null && this.KenshinKekkaData.size() > KenshinDateNum) {

				Hashtable<String, Hashtable<String, String>> kekkaData = this.KenshinKekkaData.get(KenshinDateNum);

				if(kekkaData.containsKey(CD.get(i))){
					value = KenshinKekkaData.get(KenshinDateNum).get(CD.get(i)).get(key);
					break;
				}
			}
		}

		return value;
	}

	/**
	 * ������JLAC10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̒j���̊�l�����Ԃ�
	 * @param	�������ڃR�[�h
	 */
	public String getDsJyogen(ArrayList<String> CD, int KenshinDateNum) {

		String key = "DS_JYOUGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}

	/**
	 * ������JLAC10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̏����̊�l������Ԃ�
	 * @param	�������ڃR�[�h
	 */
	public String getJsKagen(ArrayList<String> CD, int KenshinDateNum) {
		String key = "JS_KAGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}

	/**
	 * ������JLAC10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̏����̊�l�����Ԃ�
	 * @param	�������ڃR�[�h
	 */
	public String getJsJyogen(ArrayList<String> CD, int KenshinDateNum) {
		String key = "JS_JYOUGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}
	/**
	 * ������JLAC10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̔��l��Ԃ�
	 * @param	�������ڃR�[�h
	 */
	public String getBikou(ArrayList<String> CD, int KenshinDateNum) {
		String key = "BIKOU";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}
	/**
	 * ������JLAC10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̔��l��Ԃ�
	 * @param	�������ڃR�[�h
	 */
	public String getHL(ArrayList<String> CD, int KenshinDateNum) {
		String key = "H_L";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}
	/**
	 * ������JLAC10�R�[�h������ꍇ�ɁA���ʒl���܂܂�鍀�ڂ̔��l��Ԃ�
	 * @param	�������ڃR�[�h
	 */
	public String getKekkati(ArrayList<String> CD, int KenshinDateNum) {
		String key = "KEKA_TI";
		String item = getKekkaItem(CD, KenshinDateNum, key);

		return item;
	}
	/**
	 * ��f�o�͂̈׍쐬
	 * @param	�������ڃR�[�h
	 */
	public String getMonshin(int i, String code) {
		String codeName = "";

		/*
		 * ���ڃR�[�h���܂܂�Ă��邩���`�F�b�N����
		 */
		Hashtable<String, String> item = KenshinKekkaData.get(i).get(code);
		if (item != null) {
			codeName = item.get("CODE_NAME");
		}

		return codeName;
	}
	/**
	 * ���ږ��擾����B
	 * @param	�������ڃR�[�h�A���ʒl
	 */
	public String getKomokuMeisho(String kensaCd, String kekkati ) {
		int numKekka = 0;
		if (!kekkati.equals("")){
			numKekka = Integer.parseInt(kekkati);
		}else{
			return "";
		}
		return this.getCodeName(kensaCd.toString(), numKekka);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


