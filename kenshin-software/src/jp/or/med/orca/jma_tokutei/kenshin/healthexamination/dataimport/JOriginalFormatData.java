package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Vector;

/**
 * �Ǝ��t�H�[�}�b�g�f�[�^�̊e��M�҂��Ƃ̃w�b�_���B
 */
public class JOriginalFormatData {
	/**
	 * ���Ҏ���
	 */
	public String Name;
	/**
	 * ���Ғa����
	 */
	public String BirthDay;
	
	/**
	 * �{�f�B���Ɋւ�����
	 */
	public Vector<JOriginalFormatDataBody> Body = new Vector<JOriginalFormatDataBody>();
}
