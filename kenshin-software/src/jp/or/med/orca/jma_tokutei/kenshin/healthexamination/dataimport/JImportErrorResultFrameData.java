package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportErrorResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;
	// ����p
	public String uketukeNo;
	public String jisiKikanNo;
	public String jusinSeiriNo;
	public String jishiDate;
	public String kanaShimei;
	public String seiNenGapi;
	public String seiBetu;
	public String nyuBi;
	public String shokuZenGo;
	public String jisiKbn;
	public String koumokuCd;
	public String ijyoKbn;
	public String kekkaTiKeitai;
	public String kekkaTi;
	
	// del s.inoue
	// public String HiHokenjyasyoKigo;
	// public String HiHokenjyasyoNumber;
	
	/**
	 * �f�[�^�x�[�X��̃f�[�^��ێ��B�ŏI�I�ɑI�����ꂽ��f�҂𔻒肷��ۂɎg�p�B
	 */
	public Hashtable<String, String> DatabaseItem;
}
