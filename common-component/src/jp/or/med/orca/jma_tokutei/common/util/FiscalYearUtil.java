package jp.or.med.orca.jma_tokutei.common.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;

import org.apache.log4j.Logger;

public class FiscalYearUtil {

	private static final String LATEST_YEAR = "0331";
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final String DATE_JUN = "01";
	private static final String DATE_FEB = "02";
	private static final String DATE_MER = "03";

	private static org.apache.log4j.Logger logger = Logger.getLogger(FiscalYearUtil.class);
	/**
	 *  ���N�x�擾
	 *    @param  �L�[
	 *    @return VALUE
	 */
	public static int getSystemYear()
	{
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);
		return thisYear;
	}

	/**
	* �V�X�e�����t��Ԃ��܂��B
	* @param format �߂�l�ŕԂ����t�̏������w�肵�܂��Byyyymmdd�܂���yyyy/mm/dd�ł��B
	* @return �����Ŏw�肳�ꂽformat�̏����ŃV�X�e�����t��Ԃ��܂��B
	*/
	public static String getSystemDate(String format) {
	  String sysDate = "";


	  Calendar cal = Calendar.getInstance();
	  int year = cal.get(Calendar.YEAR);
	  int month = cal.get(Calendar.MONTH) + 1;
	  int day = cal.get(Calendar.DATE);


	  if (format.equals("yyyy/mm/dd")) {
	    sysDate= new DecimalFormat("0000").format(year) + "/"
	       + new DecimalFormat("00").format(month) + "/"
	       + new DecimalFormat("00").format(day);
	  } else {
	    sysDate = new DecimalFormat("0000").format(year)
	        + new DecimalFormat("00").format(month)
	        + new DecimalFormat("00").format(day);
	  }

	  return sysDate;
	}


	/**
	 *  �N�x���N�x�擾
	 *    @return �N�x���N�x
	 */
	public static int getThisYear()
	{
		int thisYear =0;
		int tmpNenDo = 0;
		int tmpGetu = 0;

		DateFormat format = new SimpleDateFormat(DATE_FORMAT);

		try {

			tmpNenDo = Integer.parseInt(format.format(new Date()).substring(0,4));
			tmpGetu = Integer.parseInt(format.format(new Date()).substring(4,6));
			// 1�`3���� -1
			if (tmpGetu >=1 && tmpGetu <=3){
				tmpNenDo--;
			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}
		thisYear = tmpNenDo;

		return thisYear;
	}

	/**
	 *  �N�x���N�x�擾
	 *  @param ���f���{�� or ���N������
	 *  @return �N�x
	 */
	public static int getThisYear(String nendoMatuDate)
	{
		int thisYear =0;
		int tmpNenDo = 0;
		int tmpGetu = 0;

		try {
			String splitDate =nendoMatuDate.substring(0, 4) +
			"/" + nendoMatuDate.substring(4, 6) +
			"/" +nendoMatuDate.substring(6, 8);

			DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		    Date kensinDt = DateFormat.getDateInstance().parse(splitDate);

			tmpNenDo = Integer.parseInt(format.format(kensinDt).substring(0,4));
			tmpGetu = Integer.parseInt(format.format(kensinDt).substring(4,6));
			// 1�`3���� -1
			if (tmpGetu >=1 && tmpGetu <=3){
				tmpNenDo--;
			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}
		thisYear = tmpNenDo;

		return thisYear;
	}

	/**
	 *  �w����t�����N�x���ǂ������f
	 *    @return �N�x
	 */
	public static boolean getJugeThisYear( String dateValue )
	{
		int thisYear =0;
		int thisMonth =0;
		int tmpNenDo = 0;
		int tmpGetu = 0;
		boolean blnJugethisYear = false;

		try {

			DateFormat format = new SimpleDateFormat(DATE_FORMAT);

			if (dateValue != null){

				tmpNenDo = Integer.parseInt(dateValue.substring(0,4));
				tmpGetu = Integer.parseInt(dateValue.substring(4,6));
				thisYear = Integer.parseInt(format.format(new Date()).substring(0,4));
				// edit ver2 s.inoue 2009/08/19
				thisMonth = Integer.parseInt(format.format(new Date()).substring(4,6));

				// 1�`3���� -1
				if (tmpGetu >=1 && tmpGetu <=3){
					tmpNenDo--;
				}

				// edit ver2 s.inoue 2009/08/19
				// 1�`3���� -1
				if (thisMonth >=1 && thisMonth <=3){
					thisYear--;
				}

				if (thisYear == tmpNenDo){
					blnJugethisYear = true;
				}

			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}

		return blnJugethisYear;
	}

	// add ver2 s.inoue 2009/08/19
	/**
	 *  �۔�1118001��75�΂̒a�����܂łɌ��f���{������ꍇ
	 *  ���@�t���̔���ƂȂ�
	 *    @param  ���N����
	 *    @param  ���f���{��
	 *    @return ����Ώۂ��ǂ���
	 */
	public static boolean getJugeDate(String birthday ,String kensinJisiDate){

		boolean retTarget = false;

		// ���N�����ƌ��f���{�����r
		int intBirthday = Integer.parseInt(birthday.substring(4));
		int intJishiDate = Integer.parseInt(kensinJisiDate.substring(4));

		/* ���f���{�� < ���N���� �� ����Ώ�
		   ���f���{�� >= ���N���� �� ����ΏۊO */
		if ( intJishiDate < intBirthday ){
			retTarget = true;
		}

		return retTarget;
	}

	/**
	 *  �N�x���N��擾
	 *    @param  ���N����
	 *    @param  ���f���{��
	 *    @return �N�x���N��
	 */
	public static int getFiscalYear( String birthday ,String kensinJisiDate)
	{
		int Age =0;
		int birthDayInt = Integer.valueOf(birthday);
		int intYear = 10000;

		try {

			if (birthday == "")
				return 0;

			DateFormat format = new SimpleDateFormat(DATE_FORMAT);

		    // edit ver2 s.inoue 2009/06/24
			// �O�̃��W�b�N
//		    String tmpGetu = format.format(new Date()).substring(4,6);
//		    if (tmpGetu.equals(DATE_JUN) || tmpGetu.equals(DATE_FEB) || tmpGetu.equals(DATE_MER)) {
//		    	intYear = 0;
//			}
//		    String tmpJuge = format.format(new Date()).substring(0,4) + LATEST_YEAR;
//		    Age = ((Integer.valueOf(tmpJuge)+10000) - birthDayInt)/10000;

		    // �����܂�
		    String splitDate =kensinJisiDate.substring(0, 4) + "/" + kensinJisiDate.substring(4, 6) + "/" +kensinJisiDate.substring(6, 8);

		    Date kensinDt = DateFormat.getDateInstance().parse(splitDate);
		    String tmpGetu = kensinJisiDate.substring(4, 6);

		    if (tmpGetu.equals(DATE_JUN) || tmpGetu.equals(DATE_FEB) || tmpGetu.equals(DATE_MER)) {
		    	intYear = 0;
			}

		    String tmpJuge = format.format(kensinDt).substring(0,4) + LATEST_YEAR;

		    Age = ((Integer.valueOf(tmpJuge)+intYear) - birthDayInt)/10000;

		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

	    return Age;
	}

	/**
	 *  �N�x���N��擾
	 *
	 *    @param  �L�[
	 *
	 *    @return VALUE
	 */
	public static int getFiscalYear( String birthday )
	{
		int Age =0;

		try {

			if (birthday == "")
				return 0;

			//	String birthday = searchResult.get(i).get("BIRTHDAY");
			int birthDayInt = Integer.valueOf(birthday);
			int intYear = 10000;

		    DateFormat format = new SimpleDateFormat("yyyyMMdd");

		    String tmpGetu = format.format(new Date()).substring(4,6);

		    if (tmpGetu.equals("01") ||
		    		tmpGetu.equals("02") ||
		    			tmpGetu.equals("03")) {
		    	intYear = 0;
			}

		    String tmpJuge = format.format(new Date()).substring(0,4) + "0331";

			//int Age = ((Integer.valueOf(tmpJuge)+10000) - birthDayInt)/10000;
		    Age = ((Integer.valueOf(tmpJuge)+intYear) - birthDayInt)/10000;

		} catch (NumberFormatException e) {
			logger.warn(e.getMessage());
		}

	    return Age;
	}

}
