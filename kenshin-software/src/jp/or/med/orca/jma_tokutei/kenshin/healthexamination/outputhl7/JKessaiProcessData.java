package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

/**
 * ���Ϗ����̂Ƃ��Ɏg�p����f�[�^
 * �����p���āA���Ϗ����̎�M�҃��X�g���쐬����B
 */
public class JKessaiProcessData {
	public String uketukeId;
	public String hiHokenjyaKigo;
	public String hiHokenjyaNumber;
	public String hokenjyaNumber;
	public String KensaDate;
	public String daikouKikanNumber;
	public String syubetuCode;
	public String seikyuKubun;
	public String jissiKubun;

	public int outputFutanSyubetsuKihon = 1;
	public int outputFutanSyubetsuSyousai = 1;
	public int outputFutanSyubetsuTsuika = 1;
	public int outputFutanSyubetsuDoc = 1;
	public String kanaName;
	public String sex;
	public String birthday;
}
