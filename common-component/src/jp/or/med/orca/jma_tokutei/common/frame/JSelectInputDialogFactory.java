package jp.or.med.orca.jma_tokutei.common.frame;

import javax.swing.JFrame;

public class JSelectInputDialogFactory {

	/**
	 * �_�C�A���O����
	 * @param parent �e�E�C���h�E
	 * @return ���͗p�_�C�A���O
	 */
	public static ISelectInputDialog createDialog(JFrame parent) {
		// �����R�����g���͗p�_�C�A���O����
		return new JSelectCommentInputDialog(parent);
	}
}
