package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/*
 * ��ʔ���̍ۂɑΏۂƂȂ�l����肷�邽�߂̃f�[�^���󂯓n���N���X
 */
public class JIppanHanteiStartData {

//	2008/3/19 ���R �C��
//	��tID�ǉ�
//	-------------------------------------------------------------------------------------------------------
	private String uketukeId = "";
//	-------------------------------------------------------------------------------------------------------

	/* Deleted 2008/03/30 �ጎ  */
	/* --------------------------------------------------- */
//	private String hokenjyaNumber= new String("");
//	private String hihokenjyaCode= new String("");
//	private String hihokenjyaNumber= new String("");

	/* --------------------------------------------------- */
	private String kensaJissiDate = new String("");
	private boolean isValidateAsDataSet = false;

//	2008/3/19 ���R �C��
//	��tIDsetter/getter�ǉ�
//	-------------------------------------------------------------------------------------------------------
	public String getUketukeId() {
		return uketukeId;
	}
	public void setUketukeId(String uketukeId) {
		this.uketukeId = uketukeId;
	}
//	-------------------------------------------------------------------------------------------------------

	/* Deleted 2008/03/30 �ጎ  */
	/* --------------------------------------------------- */
//	/**
//	 * @return the hokenjyaNumber
//	 */
//	public String getHokenjyaNumber() {
//		return hokenjyaNumber;
//	}
//	/**
//	 * @return the hihokenjyaCode
//	 */
//	public String getHihokenjyaCode() {
//		return hihokenjyaCode;
//	}
//	/**
//	 * @return the hihokenjyaNumber
//	 */
//	public String getHihokenjyaNumber() {
//		return hihokenjyaNumber;
//	}
	/* --------------------------------------------------- */
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/* Deleted 2008/03/30 �ጎ  */
	/* --------------------------------------------------- */
//	/**
//	 * @param hokenjyaNumber the hokenjyaNumber to set
//	 */
//	public boolean setHokenjyaNumber(String hokenjyaNumber) {
//		this.isValidateAsDataSet = false;
//		this.hokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);
//
//		if( this.hokenjyaNumber == null )
//			return false;
//
//		return true;
//	}
//
//	/**
//	 * @param hihokenjyaCode the hihokenjyaCode to set
//	 */
//	public boolean setHihokenjyaCode(String hihokenjyaCode) {
//		this.isValidateAsDataSet = false;
//		this.hihokenjyaCode = JValidate.validateHihokenjyaKigou(hihokenjyaCode);
//
//		if( this.hihokenjyaCode == null )
//			return false;
//
//		return true;
//	}
//
//	/**
//	 * @param hihokenjyaNumber the hihokenjyaNumber to set
//	 */
//	public boolean setHihokenjyaNumber(String hihokenjyaNumber) {
//		this.isValidateAsDataSet = false;
//		this.hihokenjyaNumber = JValidate.validateHihokenjyaNumber(hihokenjyaNumber);
//
//		if( this.hihokenjyaNumber == null )
//			return false;
//
//		return true;
//	}
	/* --------------------------------------------------- */

	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = false;
	}
	/**
	 * @return the kensaJissiDate
	 */
	public String getKensaJissiDate() {
		return kensaJissiDate;
	}

	/**
	 * @param kensaJissiDate the kensaJissiDate to set
	 */
	public boolean setKensaJissiDate(String kensaJissiDate) {
		this.isValidateAsDataSet = false;
		this.kensaJissiDate = JValidate.validateDate(kensaJissiDate,true,true);

		if( this.kensaJissiDate == null )
			return false;

		return true;
	}


}
