package jp.or.med.orca.jma_tokutei.common.hl7.common;

public interface JBaseXmlWriter {
	/**
	 * Xml�t�@�C�����쐬����
	 * @param FileName �쐬����t�@�C����
	 * @throws Exception XML�������̗�O
	 */
	public void createXml(String FileName) throws Exception;
	
	/**
	 * �K�v���ڂ̃`�F�b�N���s��
	 * XML�t�@�C���𐶐�����O�Ɏ��s���Ă�������
	 * @return �K�v���ڂ����܂��Ă���΁utrue�v���Ԃ��Ă���
	 */
	public boolean check();
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

