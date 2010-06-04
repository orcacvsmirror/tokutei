package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import org.apache.log4j.Logger;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputNitijiFrameData;

public class Nikeihyo {

	// ���v�\�f�[�^
	private Hashtable<String, String> SeikyuData = new Hashtable<String, String>();

	private static Logger logger = Logger.getLogger(Gekeihyo.class);

	/**
	 * �����f�[�^(���v�\) set
	 * DB����擾����
	 * @param	HashTable<String, String>
	 * 			Keys => HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO�AKENSA_NENGAPI
	 * @return	boolean
	 */
	public boolean setPrintSeikyuNitijiDataSQL(Hashtable<String, String> data) {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;

		// �l�f�[�^�擾
		try{

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT DISTINCT ");
			sb.append(" KOJIN.UKETUKE_ID,KOJIN.BIRTHDAY,KOJIN.SEX,KOJIN.NAME,KOJIN.JYUSHIN_SEIRI_NO,KOJIN.HIHOKENJYASYO_KIGOU,KOJIN.HIHOKENJYASYO_NO,");
			sb.append(" TOKUTEI.KENSA_NENGAPI,TOKUTEI.HENKAN_NITIJI,KOJIN.HKNJANUM,KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.KANANAME,GET_NENDO.NENDO,KESAI.TANKA_GOUKEI,");
			// edit ver2 s.inoue 2009/09/07
			sb.append(" KESAI.MADO_FUTAN_GOUKEI,KESAI.SEIKYU_KINGAKU,KESAI.UPDATE_TIMESTAMP,KOJIN.HKNJANUM");
			sb.append(" FROM T_KOJIN AS KOJIN ");
			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
			sb.append(" LEFT JOIN T_KESAI_WK AS KESAI ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID ");
			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
			sb.append(" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
			sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
			sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
			sb.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
			sb.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
			sb.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
			sb.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
			sb.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
			sb.append(" WHERE ");
			sb.append(" KOJIN.UKETUKE_ID = ");
			sb.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));

			Result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
			ResultItem = Result.get(0);

		} catch (Exception ex){
			logger.error(ex.getMessage());
		}

		// ���f���{�� �����w�b�_��񁫁�
		// edit ver2 s.inoue 2009/09/07
		// ��������擾�����f�[�^�ɕύX
		if(ResultItem.get("KENSA_NENGAPI") != null){
			this.SeikyuData.put("KENSA_NENGAPI",
					ResultItem.get("KENSA_NENGAPI").substring(0, 4) + "�N"
					+ ResultItem.get("KENSA_NENGAPI").substring(4, 6) + "��"
					+ ResultItem.get("KENSA_NENGAPI").substring(6, 8) + "��");

			this.SeikyuData.put("UKETUKE_ID", ResultItem.get("UKETUKE_ID"));
		} else {
			Calendar cal = Calendar.getInstance();
			String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4) +
			JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2) +
			JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

			this.SeikyuData.put("KENSA_NENGAPI",
					date.substring(0, 4) + "�N"
					+ date.substring(4, 6) + "��"
					+ date.substring(6, 8) + "��");
			this.SeikyuData.put("KENSA_NENGAPI", date);
		}

		// �������׏�񁫁�

		// ��f�������ԍ�
		this.SeikyuData.put("JYUSHIN_SEIRI_NO", ResultItem.get("JYUSHIN_SEIRI_NO"));
		// ����
		this.SeikyuData.put("NAME", ResultItem.get("NAME"));
		// �t���K�i
		this.SeikyuData.put("KANANAME", ResultItem.get("KANANAME"));

		// ���N����
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
		this.SeikyuData.put("BIRTHDAY", birthday);

		// ����
		if(Integer.valueOf(ResultItem.get("SEX")) == 1){
			this.SeikyuData.put("SEX", "�j��");
		} else {
			this.SeikyuData.put("SEX", "����");
		}

		// add ver2 s.inoue 2009/09/07
		// �ی��Ҕԍ�
		this.SeikyuData.put("HKNJANUM", ResultItem.get("HKNJANUM"));

		// add s.inoue 2009/10/20
		// �x����s�@�֔ԍ�
		this.SeikyuData.put("SIHARAI_DAIKOU_BANGO", ResultItem.get("SIHARAI_DAIKOU_BANGO"));

		int tanka = 0;
		int madoguti = 0;
		int seikyu = 0;

		if (!ResultItem.get("TANKA_GOUKEI").equals("")){
			tanka = Integer.parseInt(ResultItem.get("TANKA_GOUKEI"));
		}
		if (!ResultItem.get("MADO_FUTAN_GOUKEI").equals("")){
			madoguti = Integer.parseInt(ResultItem.get("MADO_FUTAN_GOUKEI"));
		}
		if (!ResultItem.get("SEIKYU_KINGAKU").equals("")){
			seikyu = Integer.parseInt(ResultItem.get("SEIKYU_KINGAKU"));
		}

		// �P�����v
		this.SeikyuData.put("TANKA_GOUKEI", String.valueOf(tanka));
		// �������S���v
		this.SeikyuData.put("MADO_FUTAN_GOUKEI", String.valueOf(madoguti));
		// �������z���v
		this.SeikyuData.put("SEIKYU_KINGAKU", String.valueOf(seikyu));

		return true;
	}

	/**
	 * ����(����)�f�[�^ get
	 * @return	Hashtable<String, String> KojinData
	 */
	public Hashtable<String, String> getPrintNikeiData(){
		return this.SeikyuData;
	}
}
