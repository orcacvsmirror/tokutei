package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKekkaListFrameCtrl;

/**
 * ����ɗp���銳�Ҍl�f�[�^���󂯓n���N���X
 */
public class Kojin {
	//���Ҍl�f�[�^
	private Hashtable<String, String> kojinData = new Hashtable<String, String>();
	private static Logger logger = Logger.getLogger(Kojin.class);

	/**
	 * ���Ҍl�f�[�^ set
	 * ��f�����͉�ʂ���擾
	 * DB�ɓo�^����O�Ȃ̂Œl�͒��ړn��
	 * @param	HashTable<String, String>
	 * 			Keys => JYUSHIN_SEIRI_NO, KANANAME, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO
	 * @return	boolean
	 */
	public boolean setPrintKojinData(Hashtable<String, String> data,int regMode) {
		try {

			//��f��
			// edit s.inoue 2009/10/19
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy'�N'MM'��'dd'��'");

			// edit s.inoue 2010/04/20
			if (regMode == 1){
			// if (data.get("UKETUKE_ID").equals("")){
				String dates = data.get("KENSA_NENGAPI").substring(0, 4)+ "�N"
				+ data.get("KENSA_NENGAPI").substring(4,6)+ "��"
				+ data.get("KENSA_NENGAPI").substring(6,8)+ "��";

				this.kojinData.put("KENSA_NENGAPI", dates);
				// del s.inoue 2010/04/15
				// return true;
			}else{
				this.kojinData.put("KENSA_NENGAPI", sdf.format(data.get("KENSA_NENGAPI")));
			}

			//��f�������ԍ�
			this.kojinData.put("JYUSHIN_SEIRI_NO", data.get("JYUSHIN_SEIRI_NO"));
			//����
			this.kojinData.put("KANANAME", data.get("KANANAME"));

			//�ی��Ҕԍ�
			this.kojinData.put("HKNJANUM", data.get("HKNJANUM"));
			//��ی��ҏؓ��L��
			this.kojinData.put("HIHOKENJYASYO_KIGOU", data.get("HIHOKENJYASYO_KIGOU"));
			//��ی��ҏؓ��ԍ�
			this.kojinData.put("HIHOKENJYASYO_NO", data.get("HIHOKENJYASYO_NO"));

			/* Modified 2008/03/25 �M ��ʂ���̈�����ڒǉ�       */
			/* --------------------------------------------------- */
			String nameKanji = data.get("NAME");
			//���O����
			this.kojinData.put("NAME", nameKanji);

			String birthday = data.get("Birthday");

			if (birthday != null) {
				if (birthday.length() == 8) {

					String yearString = birthday.substring(0, 4);
					String monthString = birthday.substring(4, 6);
					String dayString = birthday.substring(6, 8);

					//���N����
					this.kojinData.put("BIRTHDAY", yearString + "�N" + monthString + "��" + dayString + "��");

					/* Modified 2008/05/26 �ጎ  */
					/* --------------------------------------------------- */
//					/*
//					 * �N��
//					 * DB�ɂȂ��̂Ōv�Z����
//					 */
//					Calendar calendar1 = Calendar.getInstance();
//					Calendar calendar2 = Calendar.getInstance();
//
//					int y = Integer.valueOf(birthdayYear);
//					int m = Integer.valueOf(birthdayMonth);
//					int d = Integer.valueOf(birthdayDate);
//
//					calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
//					calendar2.set(y, m-1 ,d);
//
//					long millis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
//
//					long age = (long)millis/1000/60/60/24/365;
//
//					String year = new String();
//					year = Long.toString(age);
//
//					this.kojinData.put("AGE", year + "��");

					/* --------------------------------------------------- */
					/*
					 * �N��
					 * DB�ɂȂ��̂Ōv�Z����
					 */
// edit s.inoue 2010/04/15
//					Calendar calendar1 = Calendar.getInstance();
//
//					int birthdayYear = Integer.valueOf(yearString);
//					int birthdayMonth = Integer.valueOf(monthString);
//					int birthdayDay = Integer.valueOf(dayString);
//
//					int todayYear = calendar1.get(Calendar.YEAR);
//					int todayMonth = calendar1.get(Calendar.MONTH) + 1;
//					int todayDay = calendar1.get(Calendar.DATE);
//
//					int age = todayYear - birthdayYear;
//
//					if (todayMonth < birthdayMonth) {
//						--age;
//					}
//					else if(todayMonth == birthdayMonth){
//						if (todayDay < birthdayDay) {
//							--age;
//						}
//					}

					this.kojinData.put("AGE", FiscalYearUtil.getFiscalYear(birthday) + "��");
					/* --------------------------------------------------- */
				}
			}
			/* --------------------------------------------------- */
			//����
			String sexString = data.get("SEX");

			if (sexString != null && ! sexString.isEmpty()) {
				if(Integer.valueOf(sexString) == 1){
					this.kojinData.put("SEX", "�j��");
				} else {
					this.kojinData.put("SEX", "����");
				}
			}
			/* --------------------------------------------------- */


		} catch (NullPointerException e){
			return false;
		}

		return true;
	}

	/**
	 * ���Ҍl�f�[�^ set
	 * DB����擾����
	 * @param	HashTable<String, String>
	 * 			Keys => HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO,KENSA_NENGAPI,
	 * @return	boolean
	 */
	public boolean setPrintKojinDataSQL(Hashtable<String, String> data) {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;
		String query = null;

		try{
			query =
				// edit s.inoue 2009/10/05
				"SELECT HKNJANUM, JYUSHIN_SEIRI_NO, NAME, KANANAME, BIRTHDAY, SEX FROM T_KOJIN WHERE " +
				"UKETUKE_ID = " + JQueryConvert.queryConvert(data.get("UKETUKE_ID"));
				// "SELECT TT.KEKA_INPUT_FLG,TT.KENSA_NENGAPI, TK.HKNJANUM, TK.JYUSHIN_SEIRI_NO, TK.NAME, TK.KANANAME," +
				// " TK.BIRTHDAY, TK.SEX FROM T_KOJIN TK,T_KENSAKEKA_TOKUTEI TT WHERE " +
				// " TK.UKETUKE_ID = " + JQueryConvert.queryConvert(data.get("UKETUKE_ID")) +
				// " AND TK.UKETUKE_ID = TT.UKETUKE_ID ";

		} catch (NullPointerException e){
			logger.error(e.getMessage());
			return false;
		}

		try{
			Result = JApplication.kikanDatabase.sendExecuteQuery(query);
			if (Result.size() > 0) {
				ResultItem = Result.get(0);
			}
		}catch(SQLException e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		// edit s.inoue 2009/10/16
		// data.put("KENSA_NENGAPI", ResultItem.get("KENSA_NENGAPI"));

		// del s.inoue 2009/10/15
		// ���ʓ��͍ς݂��ǂ����̃t���O�擾
		// this.kojinData.put("KEKA_INPUT_FLG", ResultItem.get("KEKA_INPUT_FLG"));

		//�ی��Ҕԍ�
		this.kojinData.put("HKNJANUM", ResultItem.get("HKNJANUM"));
		//��f�������ԍ�
		this.kojinData.put("JYUSHIN_SEIRI_NO", ResultItem.get("JYUSHIN_SEIRI_NO"));
		//����
		this.kojinData.put("NAME", ResultItem.get("NAME"));
		//�t���K�i
		this.kojinData.put("KANANAME", ResultItem.get("KANANAME"));

		/* Modified 2008/05/08 �ጎ  */
		/* --------------------------------------------------- */
//		//���N����
//		this.KojinData.put("BIRTHDAY", ResultItem.get("BIRTHDAY").substring(0, 4) + "�N" + ResultItem.get("BIRTHDAY").substring(4, 6) + "��" + ResultItem.get("BIRTHDAY").substring(6, 8) + "��");
		/* --------------------------------------------------- */
		//���N����
//		String birthday =
//			ResultItem.get("BIRTHDAY").substring(0, 4)
//			+ "�N"
//			+ ResultItem.get("BIRTHDAY").substring(4, 6)
//			+ "��"
//			+ ResultItem.get("BIRTHDAY").substring(6, 8)
//			+ "��";

		String yearString = ResultItem.get("BIRTHDAY").substring(0, 4);
		String monthString = ResultItem.get("BIRTHDAY").substring(4, 6);
		String dayString = ResultItem.get("BIRTHDAY").substring(6, 8);

		int year = Integer.parseInt(yearString);
		int month = Integer.parseInt(monthString);
		int day = Integer.parseInt(dayString);

		Calendar target = Calendar.getInstance();
		target.set(year, month, day, 0, 0, 0);

		Calendar meijiStart = Calendar.getInstance();
		meijiStart.set(1868, 9, 8, 0, 0, 0);

		Calendar taisyoStart = Calendar.getInstance();
		taisyoStart.set(1912, 7, 30, 0, 0, 0);

		Calendar syowaStart = Calendar.getInstance();
		syowaStart.set(1926, 12, 25, 0, 0, 0);

		Calendar heiseiStart = Calendar.getInstance();
		heiseiStart.set(1989, 1, 8, 0, 0, 0);

		String gengoName = null;
		int gengoYear = -1;
		/* �����ȍ~ */
		if (target.equals(meijiStart) || target.after(meijiStart)) {
			/* �����i�吳���O�j */
			if (target.before(taisyoStart)) {
				gengoName = "����";
				gengoYear = year - 1867;
			}
			/* �吳�ȍ~ */
			else {
				/* �吳�i���a���O�j */
				if (target.before(syowaStart)) {
					gengoName = "�吳";
					gengoYear = year - 1911;
				}
				/* ���a�ȍ~ */
				else {
					/* ���a�i�������O�j */
					if (target.before(heiseiStart)) {
						gengoName = "���a";
						gengoYear = year - 1925;
					}
					/* �����ȍ~ */
					else {
						gengoName = "����";
						gengoYear = year - 1988;
					}
				}
			}
		}

		if (gengoYear < 0) {
			gengoName = "";
			gengoYear = year;
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(gengoName);
		buffer.append(String.valueOf(gengoYear));
		buffer.append("�N");
		buffer.append(String.valueOf(month));
		buffer.append("��");
		buffer.append(String.valueOf(day));
		buffer.append("��");

		String birthday = buffer.toString();
		this.kojinData.put("BIRTHDAY", birthday);
		/* --------------------------------------------------- */

		//����
		if(Integer.valueOf(ResultItem.get("SEX")) == 1){
			this.kojinData.put("SEX", "�j��");
		} else {
			this.kojinData.put("SEX", "����");
		}

		/* Modified 2008/05/26 �ጎ  */
//		/* --------------------------------------------------- */
//		/*
//		 * �N��
//		 * DB�ɂȂ��̂Ōv�Z����
//		 */
//		Calendar calendar1 = Calendar.getInstance();
//		Calendar calendar2 = Calendar.getInstance();
//
//		int y = Integer.valueOf(ResultItem.get("BIRTHDAY").substring(0, 4));
//		int m = Integer.valueOf(ResultItem.get("BIRTHDAY").substring(4, 6));
//		int d = Integer.valueOf(ResultItem.get("BIRTHDAY").substring(6, 8));
//
//		calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
//		calendar2.set(y, m-1 ,d);
//
//		long millis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
//
//		long age = (long)millis/1000/60/60/24/365;
//
//		String ageYear = Long.toString(age);
//
//		this.kojinData.put("AGE", ageYear + "��");

		/* --------------------------------------------------- */
		/*
		 * �N��
		 * DB�ɂȂ��̂Ōv�Z����
		 */
// edit s.inoue 20081113
		// ���ʉ���getFiscalYear

// edit s.inoue 2010/04/15
//		Calendar calendar1 = Calendar.getInstance();
//
//		int birthdayYear = Integer.valueOf(yearString);
//		int birthdayMonth = Integer.valueOf(monthString);
//		int birthdayDay = Integer.valueOf(dayString);
//
//		int todayYear = calendar1.get(Calendar.YEAR);
//		int todayMonth = calendar1.get(Calendar.MONTH) + 1;
//		int todayDay = calendar1.get(Calendar.DATE);
//
//		int age = todayYear - birthdayYear;
//
//		if (todayMonth < birthdayMonth) {
//			--age;
//		}
//		else if(todayMonth == birthdayMonth){
//			if (todayDay < birthdayDay) {
//				--age;
//			}
//		}
		// edit s.inoue 20081113�@�N�x���N��d�l�̏ꍇ
		//int age =FiscalYearUtil.getFiscalYear(JCalendarConvert.JCtoAD(birthday));

		// edit s.inoue 2010/04/15
		// this.kojinData.put("AGE", age + "��");
		String selectedItem
		= JValidate.validateSendSeinengapi(birthday);

		this.kojinData.put("AGE", FiscalYearUtil.getFiscalYear(selectedItem) + "��");

		/* --------------------------------------------------- */

//		2008/3/25 �M �C��
//		T_KOJIN,T_KENSAKEKA_TOKUTEI��UKETUKE_ID�ǉ��Ή�
//		-------------------------------------------------------------------------------------------------------
		this.kojinData.put("UKETUKE_ID",data.get("UKETUKE_ID") );
		/*
		 * ��f��
		 * DB�ɂȂ��̂ň����Ŏ󂯎��
		 * ��������l�Ȃ猻�݂̎���������
		 */
		if(!data.get("KENSA_NENGAPI").isEmpty()){
			this.kojinData.put("KENSA_NENGAPI", data.get("KENSA_NENGAPI").substring(0, 4) + "�N" + data.get("KENSA_NENGAPI").substring(4, 6) + "��" + data.get("KENSA_NENGAPI").substring(6, 8) + "��");
//			2008/3/25 �M ��t�N�����@�ǉ�
			this.kojinData.put("UKETUKE_NENGAPI", data.get("KENSA_NENGAPI"));
		} else {
			Calendar cal = Calendar.getInstance();
			String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4) + JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2) + JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);
			this.kojinData.put("KENSA_NENGAPI", date.substring(0, 4) + "�N" + date.substring(4, 6) + "��" + date.substring(6, 8) + "��");
//			2008/3/25 �M ��t�N�����@�ǉ�
			this.kojinData.put("UKETUKE_NENGAPI", date);
			}

		//��ی��ҏؓ��L��
		this.kojinData.put("HIHOKENJYASYO_KIGOU", data.get("HIHOKENJYASYO_KIGOU"));
		//��ی��ҏؓ��ԍ�
		this.kojinData.put("HIHOKENJYASYO_NO", data.get("HIHOKENJYASYO_NO"));

		return true;
	}

	/**
	 * ���Ҍl�f�[�^ get
	 * @return	Hashtable<String, String> KojinData
	 */
	public Hashtable<String, String> getPrintKojinData(){
		return this.kojinData;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

