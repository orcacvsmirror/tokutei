package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

public class JKekkaRegisterFrameData {

	private String uketuke_id = "";
	private String uketukePre_id ="";
	private String hokenjyaNumber = new String("");
	private String hihokenjyaCode = new String("");
	private String hihokenjyaNumber = new String("");
	private String nameKana = new String("");
	private String kensaJissiDate = new String("");
	private String kensaKoumokuCode = new String("");
	private String kekka = new String("");
	private String sougouComment = new String("");
	private String jisiKubun = new String("");
	private String HL = new String("");
	private String hantei = new String("");
	private String comment = new String("");

	private String metaboHantei = new String("");
	private String hokenSidouLevel = new String("");
	private String seikyuKubun = new String ("");  //  @jve:decl-index=0:

	private String kensaCenterCode = new String("");
	private boolean isValidateAsDataSet = false;
	// add ver2 s.inoue 2009/06/29
	private String copyJyushinkenID= new String("");

	/**
	 * 受付ID取得
	 * @return 受付ID
	 */
	public String getUketuke_id() {
		return uketuke_id;
	}
	/**
	 * 受付ID設定
	 * @param uketuke_id 受付ID
	 */
	public void setUketuke_id(String uketuke_id) {
		this.uketuke_id = uketuke_id;
	}

	/**
	 * 受付ID取得
	 * @return 受付ID
	 */
	public String getUketukePre_id() {
		return uketukePre_id;
	}
	/**
	 * 受付ID設定
	 * @param uketuke_id 受付ID
	 */
	public void setUketukePre_id(String uketukePre_id) {
		this.uketukePre_id = uketukePre_id;
	}

	/**
	 * @return the jyushinkenID
	 */
	public String getcopyJyushinkenID() {
		return copyJyushinkenID;
	}

	/**
	 * @return the hihokenjyaCode
	 */
	public String getHihokenjyaCode() {
		return hihokenjyaCode;
	}
	/**
	 * @return the hihokenjyaNumber
	 */
	public String getHihokenjyaNumber() {
		return hihokenjyaNumber;
	}
	/**
	 * @return the kensaJissiDate
	 */
	public String getKensaJissiDate() {
		return kensaJissiDate;
	}
	/**
	 * @return the kensaKoumokuCode
	 */
	public String getKensaKoumokuCode() {
		return kensaKoumokuCode;
	}
	/**
	 * @return the kekka
	 */
	public String getKekka() {
		return kekka;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	//	 add s.inoue 20080903
	/**
	 * @return the Jisi_KBN
	 */
	public String getJisi_KBN() {
		return jisiKubun;
	}
	/**
	 * @return the hL
	 */
	public String getHL() {
		return HL;
	}
	/**
	 * @return the hantei
	 */
	public String getHantei() {
		return hantei;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @param hihokenjyaCode the hihokenjyaCode to set
	 */
	public boolean setHihokenjyaCode(String hihokenjyaCode) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaCode = JValidate.validateHihokenjyaKigou(hihokenjyaCode);

		if( this.hihokenjyaCode == null )
			return false;

		return true;
	}

	/**
	 * @param hihokenjyaNumber the hihokenjyaNumber to set
	 */
	public boolean setHihokenjyaNumber(String hihokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaNumber = JValidate.validateHihokenjyaNumber(hihokenjyaNumber);

		if( this.hihokenjyaNumber == null )
			return false;

		return true;
	}

	/**
	 * @param kensaJissiDate the kensaJissiDate to set
	 */
	public boolean setKensaJissiDate(String kensaJissiDate) {
		this.isValidateAsDataSet = false;
		this.kensaJissiDate = JValidate.validateDate(kensaJissiDate,false,true);

		if( this.kensaJissiDate == null )
			return false;

		return true;
	}

	// add ver2 s.inoue 2009/06/29
	/**
	 * @param jyushinkenID the jyushinkenID to set
	 */
	public boolean setcopyJyushinkenID(String jyushinkenID) {
		this.isValidateAsDataSet = false;
		this.copyJyushinkenID = JValidate.validateJyushinkenID(jyushinkenID);

		if( this.copyJyushinkenID == null ) {
			return false;
		}
		return true;
	}

	/**
	 * @param kensaKoumokuCode the kensaKoumokuCode to set
	 */
	public boolean setKensaKoumokuCode(String kensaKoumokuCode) {
		this.isValidateAsDataSet = false;
		this.kensaKoumokuCode = JValidate.validateKensaKoumokuCode(kensaKoumokuCode);

		if( this.kensaKoumokuCode == null )
			return false;

		return true;
	}

	public boolean setKekkaDecimal(String kekka,String format) {
		this.isValidateAsDataSet = false;
		this.kekka = JValidate.validateKensaKekkaDecimal(kekka, format);

		if( this.kekka == null )
			return false;

		return true;
	}

	public boolean setKekkaText(String kekka,String format) {
		this.isValidateAsDataSet = false;
		this.kekka = JValidate.validateKensaKekkaText(kekka, format);

		if( this.kekka == null )
			return false;

		return true;
	}

	public boolean setKekkaCode(String kekka) {
		this.isValidateAsDataSet = false;
		this.kekka = JValidate.validateKensaKekkaCode(kekka);

		if( this.kekka == null )
			return false;

		return true;
	}

	/**
	 * @param comment the comment to set
	 */
	public boolean setComment(String comment) {
		this.isValidateAsDataSet = false;
		this.comment = JValidate.validateComment(comment);

		if( this.comment == null ){
			// edit 20081216 s.inoue
			// return false;
			JErrorMessage.show("M3603", null);
			return false;
		}
		return true;
	}
	// add s.inoue 20080903
	/**
	 * @param Jisi_Kbn the Jisi_Kbn to set
	 */
	public boolean setJisiKbn(String jisi_Kbn) {
		this.isValidateAsDataSet = false;
		this.jisiKubun = JValidate.validateJisiKubun(jisi_Kbn);

		if( this.jisiKubun == null )
			return false;

		return true;
	}

	/**
	 * @param Jisi_Kbn the Jisi_Kbn to set
	 */
	public boolean setChkText(String jisi_Kbntxt) {
		this.isValidateAsDataSet = false;
		this.jisiKubun = JValidate.validateJisiKubunText(jisi_Kbntxt);

		if( this.jisiKubun == null )
			return false;

		return true;
	}

	/**
	 * @param hl the hL to set
	 */
	public boolean setHL(String hl) {
		this.isValidateAsDataSet = false;
		this.HL = JValidate.validateHLKubun(hl);

		if( this.HL == null )
			return false;

		return true;
	}

	/**
	 * @param hantei the hantei to set
	 */
	public boolean setHantei(String hantei) {
		this.isValidateAsDataSet = false;
		this.hantei = JValidate.validatehanteiKekka(hantei);

		if( this.hantei == null )
			return false;

		return true;
	}


	/**
	 * @return the hokenjyaNumber
	 */
	public String getHokenjyaNumber() {
		return hokenjyaNumber;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setHokenjyaNumber(String hokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.hokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);

		if( this.hokenjyaNumber == null )
			return false;

		return true;
	}



	/**
	 * @return the seikyuKubun
	 */
	public String getSeikyuKubun() {
		return seikyuKubun;
	}

	/**
	 * @param seikyuKubun the seikyuKubun to set
	 */
	public boolean setSeikyuKubun(String seikyuKubun) {
		this.isValidateAsDataSet = false;
		this.seikyuKubun = JValidate.validateSeikyuKubun(seikyuKubun);

		if( this.seikyuKubun == null )
			return false;

		return true;
	}



	/**
	 * @return the metaboHantei
	 */
	public String getMetaboHantei() {
		return metaboHantei;
	}

	/**
	 * @param metaboHantei the metaboHantei to set
	 */
	public  boolean setMetaboHantei(String metaboHantei) {
		this.isValidateAsDataSet = false;
		this.metaboHantei = JValidate.validateMetaboHanteiKubun(metaboHantei);

		if( this.metaboHantei == null )
			return false;

		return true;
	}



	/**
	 * @return the nameKana
	 */
	public String getNameKana() {
		return nameKana;
	}

	/**
	 * @param nameKana the nameKana to set
	 */
	public boolean setNameKana(String nameKana) {
		this.isValidateAsDataSet = false;
		this.nameKana = JValidate.validateNameKana(nameKana);

		if( this.nameKana ==  null )
			return false;

		return true;
	}



	/**
	 * @return the sougouComment
	 */
	public String getSougouComment() {
		return sougouComment;
	}

	/**
	 * @param sougouComment the sougouComment to set
	 */
	public boolean setSougouComment(String sougouComment) {
		this.isValidateAsDataSet = false;
		this.sougouComment = JValidate.validateComment(sougouComment);

		if( this.sougouComment == null ){
			// edit 20081216 s.inoue
			// return false;
			JErrorMessage.show("M3604", null);
			return false;
		}

		return true;
	}



	/**
	 * @return the hokenSidouLevel
	 */
	public String getHokenSidouLevel() {
		return hokenSidouLevel;
	}

	/**
	 * @param hokenSidouLevel the hokenSidouLevel to set
	 */
	public boolean setHokenSidouLevel(String hokenSidouLevel) {
		this.isValidateAsDataSet = false;
		this.hokenSidouLevel = JValidate.validateHokenSidouLevel(hokenSidouLevel);

		if( this.hokenSidouLevel == null )
			return false;

		return true;
	}



	/**
	 * @return the kensaCenterCode
	 */
	public String getKensaCenterCode() {
		return kensaCenterCode;
	}

	/**
	 * @param kensaCenterCode the kensaCenterCode to set
	 */
	public boolean setKensaCenterCode(String kensaCenterCode) {
		this.isValidateAsDataSet = false;
		this.kensaCenterCode = JValidate.validateKensaCenterCode(kensaCenterCode);

		if( this.kensaCenterCode == null )
			return false;

		return true;
	}

	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = true;
	}
}