package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorSiharaiResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	/* �x����s�}�X�^ */
	public String CSV_COLUMN_SHIHARAI_DAIKO_NO;
	public String CSV_COLUMN_SHIHARAI_DAIKO_NAME;
	public String CSV_COLUMN_SHIHARAI_DAIKO_ZIPCD;
	public String CSV_COLUMN_SHIHARAI_DAIKO_ADR;
	public String CSV_COLUMN_SHIHARAI_DAIKO_TEL;

	/**
	 * �f�[�^�x�[�X��̃f�[�^��ێ��B�ŏI�I�ɑI�����ꂽ��f�҂𔻒肷��ۂɎg�p�B
	 */
	public Hashtable<String, String> DatabaseItem;
}
