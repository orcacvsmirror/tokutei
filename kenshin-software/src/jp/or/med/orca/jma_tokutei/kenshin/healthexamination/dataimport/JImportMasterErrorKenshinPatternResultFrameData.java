package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorKenshinPatternResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	/* ���f�p�^�[���}�X�^ */
	public String CSV_COLUMN_KENSHIN_PM_K_P_NO;
	public String CSV_COLUMN_KENSHIN_K_P_NAME;
	public String CSV_COLUMN_KENSHIN_BIKOU;

	public String[] CSV_COLUMN_KENSHIN_LOW_ID;
	public String[] CSV_COLUMN_KENSHIN_KOUMOKU_CD;

	/**
	 * �f�[�^�x�[�X��̃f�[�^��ێ��B�ŏI�I�ɑI�����ꂽ��f�҂𔻒肷��ۂɎg�p�B
	 */
	public Hashtable<String, String> DatabaseItem;
}
