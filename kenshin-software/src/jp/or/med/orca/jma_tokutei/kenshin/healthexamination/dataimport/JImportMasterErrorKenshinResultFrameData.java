package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorKenshinResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	public String CSV_COLUMN_HKNJANUM;
	public String CSV_COLUMN_KOUMOKU_CD;
	public String CSV_COLUMN_HISU_FLG;
	public String CSV_COLUMN_KAGEN;
	public String CSV_COLUMN_JYOUGEN;
	public String CSV_COLUMN_DS_JYOUGEN;
	public String CSV_COLUMN_DS_KAGEN;
	public String CSV_COLUMN_JS_JYOUGEN;
	public String CSV_COLUMN_JS_KAGEN;
	public String CSV_COLUMN_TANI;
	public String CSV_TANKA_KENSIN;
	public String CSV_COLUMN_BIKOU;

	/**
	 * �f�[�^�x�[�X��̃f�[�^��ێ��B�ŏI�I�ɑI�����ꂽ��f�҂𔻒肷��ۂɎg�p�B
	 */
	public Hashtable<String, String> DatabaseItem;
}
