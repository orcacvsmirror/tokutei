package jp.or.med.orca.jma_tokutei.common.scene;
import javax.swing.*;
import java.awt.event.WindowListener;;

/**
 * 遷移の管理やダイアログの表示を行うクラス。
 */
public class JScene  {
	/**
	 * フレームの遷移を行う。
	 * @param NextFrame 遷移先のフレーム
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
	 * モーダルダイアログを作成する。
	 * @param ParentFrame 作成元のダイアログ。
	 * @param NewFrame 新しいダイアログ。
	 */
	public static void CreateDialog(JFrame ParentFrame,JFrame NewFrame)
	{
		ParentFrame.setEnabled(false);
		NewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NewFrame.setVisible(true);
		
		NewFrame.addWindowListener(new JModalDialogWindowAdapter(ParentFrame));
	}
	
	/**
	 * モーダルダイアログを作成する。
	 * @param ParentFrame 作成元のダイアログ。
	 * @param NewFrame 新しいダイアログ。
	 * @param Window モーダルダイアログの状態を通知するWindow。
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

