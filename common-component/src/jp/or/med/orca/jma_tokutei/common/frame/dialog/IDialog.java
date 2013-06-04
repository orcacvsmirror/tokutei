package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

/**
 * messageDialogInterface
 */
public interface IDialog {

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
	 * �t�@�C���p�X�擾
	 * @return �t�@�C���p�X
	 */
	String getFilePath();

	/**
	 * �w�茒�f���擾
	 * @return ���f��
	 */
	String getKenshinDate();

	/**
	 * ������@�擾 ����(1 or 2)
	 * @return ������@
	 */
	Integer getPrintSelect();

	/*
	 * ���f�p�^�[���ԍ�
	 */
	String getK_PNo();

	/*
	 * ���f�p�^�[����
	 */
	String getK_PName();

	/*
	 * ���f�p�^�[��:���l
	 */
	String getK_PNote();

	/**
	 * �_�C�A���O�̃e�L�X�g�G���A/���X�g�{�b�N�X��
	 * �I�𒆃Z���̃e�L�X�g��ݒ肷��
	 * @param text �I�𒆃Z���e�L�X�g
	 */
	void setText(String text);

	/**
	 * �^�C�g�������ɕ\�����镶�����ݒ�
	 * @param title �^�C�g��������
	 */
	void setMessageTitle(String title);

	/**
	 * �������񊈐��ݒ�
	 * @param title �^�C�g��������
	 */
	void setEnabled(boolean enabled);

	/**
	 * �������񊈐��ݒ�
	 * @param title �^�C�g��������
	 */
	void setDialogSelect(boolean enabled);

	/**
	 * �^�C�g�������ɕ\�����镶�����ݒ�
	 * @param title �^�C�g��������
	 */
	void setDialogTitle(String title);

	/**
	 * �^�C�g�������ɕ\�����镶�����ݒ�
	 * @param title �^�C�g��������
	 */
	void setSaveFileName(String title);

	/**
	 * �L�����Z���{�^���\��/��\���ݒ�
	 * @param isShowCancel
	 */
	void setShowCancelButton(boolean isShowCancel);

	// add s.inoue 2012/07/04
	Vector getDataSelect();
}