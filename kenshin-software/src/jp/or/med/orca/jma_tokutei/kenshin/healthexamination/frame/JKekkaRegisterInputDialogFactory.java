package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import javax.swing.JFrame;

public class JKekkaRegisterInputDialogFactory {

	private static final int KEKKA_MOJIRETSU_INDEX = 5;
	private static final int SOUGOU_COMMENT__INDEX = 12;
	
	/**
	 * �_�C�A���O����
	 * @param parent �e�E�C���h�E
	 * @param index �e�[�u���Z���C���f�b�N�X
	 * @return ���͗p�_�C�A���O
	 */
	public static IKekkaRegisterInputDialog createDialog(
										JFrame parent, int index) {
		// �����R�����g���͗p�_�C�A���O����
		if (index == KEKKA_MOJIRETSU_INDEX) {
			return new JKekkaRegisterKekkaMojiretsuInputDialog(parent);
		}
		else if (index == SOUGOU_COMMENT__INDEX) {
			return new JKekkaRegisterCommentInputDialog(parent);
		}
		return null;
	}
}
