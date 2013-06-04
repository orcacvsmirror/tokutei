package jp.or.med.orca.jma_tokutei.common.scene;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * ���[�_���_�C�A���O�̏I���������s�����߂̃N���X�B
 */
public class JModalDialogWindowAdapter extends WindowAdapter
{
	public JModalDialogWindowAdapter(JFrame ParentDialog)
	{
		mParentFrame = ParentDialog;
	}

	private JFrame mParentFrame;

	public void windowClosed(WindowEvent evt)
	{
		mParentFrame.setEnabled(true);

		// �O�ʂɕ\�����邽�߂ɁA��x�펞�O�ʂɏo����Ԃɂ���B
		mParentFrame.setAlwaysOnTop(true);
		mParentFrame.setAlwaysOnTop(false);
    }
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

