package jp.or.med.orca.jma_tokutei.common.scene;
import javax.swing.*;
import java.awt.event.WindowListener;;

/**
 * �J�ڂ̊Ǘ���_�C�A���O�̕\�����s���N���X�B
 */
public class JScene  {
	/**
	 * �t���[���̑J�ڂ��s���B
	 * @param NextFrame �J�ڐ�̃t���[��
	 */
	public static void ChangeScene(JFrame NextFrame)
	{
		if(mCurrentForm != null)
		{
			mCurrentForm.setVisible(false);
			mCurrentForm.dispose();
		}

		NextFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NextFrame.setVisible(true);
		mCurrentForm = NextFrame;
	}
	
	/**
	 * ���[�_���_�C�A���O���쐬����B
	 * @param ParentFrame �쐬���̃_�C�A���O�B
	 * @param NewFrame �V�����_�C�A���O�B
	 */
	public static void CreateDialog(JFrame ParentFrame,JFrame NewFrame)
	{
		ParentFrame.setEnabled(false);
		NewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NewFrame.setVisible(true);
		
		NewFrame.addWindowListener(new JModalDialogWindowAdapter(ParentFrame));
	}
	
	/**
	 * ���[�_���_�C�A���O���쐬����B
	 * @param ParentFrame �쐬���̃_�C�A���O�B
	 * @param NewFrame �V�����_�C�A���O�B
	 * @param Window ���[�_���_�C�A���O�̏�Ԃ�ʒm����Window�B
	 */
	public static void CreateDialog(JFrame ParentFrame,JFrame NewFrame,WindowListener Window)
	{
		ParentFrame.setEnabled(false);
		NewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NewFrame.setVisible(true);
		
		NewFrame.addWindowListener(new JModalDialogWindowAdapter(ParentFrame));
		NewFrame.addWindowListener(Window);
	}
	
	private static JFrame mCurrentForm = null;
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

