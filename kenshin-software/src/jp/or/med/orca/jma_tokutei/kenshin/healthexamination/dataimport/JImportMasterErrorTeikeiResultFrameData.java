package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorTeikeiResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	public String CSV_COLUMN_SYOKEN_TYPE;
	public String CSV_COLUMN_SYOKEN_NO;
	public String CSV_COLUMN_SYOKEN;

	/**
	 * �f�[�^�x�[�X��̃f�[�^��ێ��B�ŏI�I�ɑI�����ꂽ��f�҂𔻒肷��ۂɎg�p�B
	 */
	public Hashtable<String, String> DatabaseItem;
}
