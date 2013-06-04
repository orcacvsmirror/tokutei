package jp.or.med.orca.jma_tokutei.common.scene;
import javax.swing.*;

import org.openswing.swing.table.client.GridController;

import java.awt.event.WindowListener;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.SettingDialog;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;

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
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NextFrame.setIconImage(image);

		mCurrentForm = NextFrame;
	}

	// openswing s.inoue 2011/02/09
	/**
	 * �t���[���̑J�ڂ��s���B(openswing�p�J�X�^�}�C�Y�J��)
	 * @param NextFrame �J�ڐ�̃t���[��
	 */
	public static void ChangeScene(JFrame grid,JFrame NextFrame,WindowListener Window)
	{
		if(mCurrentForm != null)
		{
			mCurrentForm.setVisible(false);
//			mCurrentForm.dispose();
		}

		NextFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NextFrame.setVisible(true);
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NextFrame.setIconImage(image);

		// �Ăяo�������ɏ�
        // del s.inoue 2013/02/13
		// NextFrame.setAlwaysOnTop(true);

		// �Ăяo������񊈐�
		grid.setEnabled(false);

		NextFrame.addWindowListener(Window);
		mCurrentForm = NextFrame;
	}

	/**
	 * �t���[���̑J�ڂ��s���B(openswing�p�J�X�^�}�C�Y�J��)
	 * @param NextFrame �J�ڐ�̃t���[��
	 */
	public static void ChangeScene(JFrame grid,JFrame NextFrame)
	{
		if(mCurrentForm != null)
		{
			mCurrentForm.setVisible(false);
//			mCurrentForm.dispose();
		}

		NextFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NextFrame.setVisible(true);
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NextFrame.setIconImage(image);

		// �Ăяo�������ɏ�
		NextFrame.setAlwaysOnTop(true);

		// �Ăяo������񊈐�
		grid.setEnabled(false);

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
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NewFrame.setIconImage(image);

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
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NewFrame.setIconImage(image);

		NewFrame.addWindowListener(new JModalDialogWindowAdapter(ParentFrame));
		NewFrame.addWindowListener(Window);
	}

	private static JFrame mCurrentForm = null;
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

