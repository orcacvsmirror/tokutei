package jp.or.med.orca.jma_tokutei.common.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

/**
 * �o�^�A�ꗗ��ʂŎg�p���鋤�ʃ_�C�A���O�̃C���^�t�F�[�X
 *
 */
public interface ISelectInputDialog {

	/**
	 * �_�C�A���O�\��/��\��
	 * @param isVisible �\��/��\���t���O
	 */
	void setVisible(boolean isVisible);

	/**
	 * �{�^��������Ԏ擾
	 * @return �{�^���������
	 */
	RETURN_VALUE getStatus();

	/**
	 * �_�C�A���O�̃e�L�X�g�G���A/���X�g�{�b�N�X��
	 * �I�𒆃Z���̃e�L�X�g��ݒ肷��
	 * @param text �I�𒆃Z���e�L�X�g
	 */
	void setText(String text);

	/**
	 * �_�C�A���O�������/�I�����ꂽ�e�L�X�g��
	 * �擾����
	 * @return ����/�I���e�L�X�g
	 */
	String getText();

	/**
	 * ����p�̃N�G��
	 */
	String getPrintQuery();
}
