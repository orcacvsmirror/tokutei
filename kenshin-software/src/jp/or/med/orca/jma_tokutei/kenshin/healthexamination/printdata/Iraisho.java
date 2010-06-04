package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class Iraisho {
	
	// �˗����f�[�^
	private Hashtable<String, String> IraiData = new Hashtable<String, String>();
	
	/**
	 * �l�f�[�^(�˗���) set
	 * DB����擾����
	 * @param	HashTable<String, String>
	 * 			Keys => HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO�AKENSA_NENGAPI
	 * @return	boolean
	 */
	public boolean setPrintKojinIraiDataSQL(Hashtable<String, String> data) {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;
		String query = null;

		// �l�f�[�^�擾
		try{
			query = " SELECT KOJIN.UKETUKE_ID,KOJIN.JYUSHIN_SEIRI_NO,KOJIN.NAME,KOJIN.KANANAME,KOJIN.BIRTHDAY,KOJIN.SEX," +
			" TOKUTEI.NYUBI_YOUKETU,TOKUTEI.SYOKUZEN_SYOKUGO " +
			" FROM T_KOJIN AS KOJIN " +
			" LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI " +
			" ON(TOKUTEI.UKETUKE_ID = KOJIN.UKETUKE_ID) " +
			" WHERE " +
			" KOJIN.UKETUKE_ID = " + JQueryConvert.queryConvert(data.get("UKETUKE_ID")) +
			" AND TOKUTEI.KENSA_NENGAPI = " + JQueryConvert.queryConvert(data.get("KENSA_NENGAPI"));
			
			Result = JApplication.kikanDatabase.sendExecuteQuery(query);
			ResultItem = Result.get(0);
			
		} catch (Exception ex){
			return false;
		}
		
		/***** �w�b�_��� *****/
		// �����@�֖�
		// ���f���{���Ԗ�
		
		// �̌���
		if(!data.get("KENSA_NENGAPI").isEmpty()){
			this.IraiData.put("KENSA_NENGAPI",
					data.get("KENSA_NENGAPI").substring(0, 4) + "�N" 
					+ data.get("KENSA_NENGAPI").substring(4, 6) + "��"
					+ data.get("KENSA_NENGAPI").substring(6, 8) + "��");
			
			this.IraiData.put("UKETUKE_NENGAPI", data.get("KENSA_NENGAPI"));
		} else {
			Calendar cal = Calendar.getInstance();
			String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4) + 
			JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2) +
			JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);
			
			this.IraiData.put("KENSA_NENGAPI",
					date.substring(0, 4) + "�N" 
					+ date.substring(4, 6) + "��"
					+ date.substring(6, 8) + "��");
			this.IraiData.put("UKETUKE_NENGAPI", date);
		}
		
		/***** ���׏�� *****/		
		// ��f�������ԍ�
		this.IraiData.put("JYUSHIN_SEIRI_NO", ResultItem.get("JYUSHIN_SEIRI_NO"));
		// ����
		this.IraiData.put("NAME", ResultItem.get("NAME"));
		// �t���K�i
		this.IraiData.put("KANANAME", ResultItem.get("KANANAME"));
		
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
		this.IraiData.put("BIRTHDAY", birthday);
		
		// ����
		if(Integer.valueOf(ResultItem.get("SEX")) == 1){
			this.IraiData.put("SEX", "�j��");
		} else {
			this.IraiData.put("SEX", "����");
		}
		
		// ���l(���r�A�n��)
		this.IraiData.put("BIKOU", ResultItem.get("NYUBI_YOUKETU") + JApplication.LINE_SEPARATOR + ResultItem.get("SYOKUZEN_SYOKUGO"));


		return true;
	}

	/**
	 * �˗��f�[�^ get
	 * @return	Hashtable<String, String> KojinData
	 */
	public Hashtable<String, String> getPrintIraiData(){
		return this.IraiData;
	}

}
