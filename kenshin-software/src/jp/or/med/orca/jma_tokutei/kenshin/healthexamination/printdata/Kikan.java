package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;



/**
 * ����ɗp���錒�f�@�փf�[�^���󂯓n���N���X
 */
public class Kikan {
	// ���f�@�փf�[�^
	private Hashtable<String, String> KikanData = new Hashtable<String, String>();
	
	/**
	 * ���f�@�փf�[�^ set
	 * DB����擾����
	 * @param	String TKIKAN_NO
	 * @return	boolean
	 */
	public boolean setPrintKikanDataSQL() {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;
		String Query = null;
		
		try{
			Query = "SELECT TKIKAN_NO, KIKAN_NAME, POST, TEL, ADR, TIBAN FROM T_KIKAN";
		} catch (NullPointerException e){
			return false;
		}
			
		try{
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
			ResultItem = Result.get(0);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		//���f�@�֔ԍ�
		this.KikanData.put("TKIKAN_NO", ResultItem.get("TKIKAN_NO"));	
		//���f�@�֖�
		this.KikanData.put("KIKAN_NAME", ResultItem.get("KIKAN_NAME"));	
		//�X�֔ԍ�
		this.KikanData.put("POST", ResultItem.get("POST"));	
		//�d�b�ԍ�
		this.KikanData.put("TEL", ResultItem.get("TEL"));
		//�Z��
		this.KikanData.put("ADR", ResultItem.get("ADR"));
		//�n�ԕ���
		this.KikanData.put("TIBAN", ResultItem.get("TIBAN"));
		
		return true;
	}
	
	/**
	 * ���f�@�փf�[�^ get
	 * @return	Hashtable<String, String> KikanData
	 */
	public Hashtable<String, String> getPrintKikanData() {
		return KikanData;		
	}

}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

