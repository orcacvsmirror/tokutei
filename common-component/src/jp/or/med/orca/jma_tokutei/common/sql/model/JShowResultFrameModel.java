package jp.or.med.orca.jma_tokutei.common.sql.model;

public class JShowResultFrameModel implements RecordModel {

	/**
	 * �����Z���^�[����
	 */
	private String CENTER_NAME;
	
	/**
	 * ���f�p�^�[������
	 */
	private String K_P_NAME;

	/* Added 2008/08/06 �ጎ  */
	/* --------------------------------------------------- */
	/**
	 * ���f�p�^�[���ԍ� 
	 */
	private int K_P_NO;
	
	public int getK_P_NO() {
		return K_P_NO;
	}

	public void setK_P_NO(Integer k_p_no) {
		K_P_NO = k_p_no;
	}
	/* --------------------------------------------------- */
	
	public String getCENTER_NAME() {
		return CENTER_NAME;
	}

	public void setCENTER_NAME(String center_name) {
		CENTER_NAME = center_name;
	}

	public String getK_P_NAME() {
		return K_P_NAME;
	}

	public void setK_P_NAME(String k_p_name) {
		K_P_NAME = k_p_name;
	}
	
	
}
