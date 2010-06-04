package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLockerConfig;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.kenshin.admin.JAdminSoftware;

/**
 * ログインフレーム
 */
public class JLoginFrameCtrl extends JLoginFrame {

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JLoginFrameCtrl.class);

	public static void main(String[] args) {
		new JLoginFrameCtrl();
	}

	public JLoginFrameCtrl() {
		jButton_Login.setEnabled(false);

		// ログインボタンの状態を管理
		jTextField_UserName.getDocument().addDocumentListener(
				new LoginButtonStatus());
		jPasswordField_Password.getDocument().addDocumentListener(
				new LoginButtonStatus());

		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.jTextField_UserName);
		this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
		this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		this.focusTraversalPolicy.addComponent(this.jButton_Login);
		// del y.okano 2010/05/24
		//this.focusTraversalPolicy.addComponent(this.jButton_Version);
		this.focusTraversalPolicy.addComponent(jButton_End);
		// jTextField_UserName.requestFocus();

		// add s.inoue 2009/12/04
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
		// del y.okano 2010/05/24
		//JAdminSoftware.getSplashFrame().addComponentListener(
		//		new ComponentAdapter() {

		//			@Override
		//			public void componentHidden(ComponentEvent arg0) {
		//				JLoginFrameCtrl.this.jButton_Version.setSelected(false);
		//			}
		//		});

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JAdminSoftware.getSplashFrame().hideSplashWindow();
			}
		});

		StringBuffer buffer = new StringBuffer();
		buffer.append(ViewSettings.getAdminFrameCommonTitle());
		buffer.append(" (Version ");
		buffer.append(JApplication.versionNumber);
		buffer.append(")");

		String title = buffer.toString();
		this.setTitle(title);

		ViewSettings.setAdminCommonTitleWithVersion(title);
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * バージョンボタン
	 */
	// del y.okano 2010/05/24
	//public void pushedVersionButton(ActionEvent e) {
	//	if (jButton_Version.isSelected()) {
	//		JAdminSoftware.getSplashFrame().showSplashWindow();
	//	} else {
	//		JAdminSoftware.getSplashFrame().hideSplashWindow();
	//	}
	//}

	/**
	 * ログインボタン
	 */
	public void pushedLoginButton() {

		String username = jTextField_UserName.getText();
		String password = new String(jPasswordField_Password.getPassword());

		// 入力文字列の長さのチェック
		if (username.length() <= 0) {
			JErrorMessage.show("M7100", this);
			return;
		}
		if (password.length() <= 0) {
			JErrorMessage.show("M7101", this);
			return;
		}

		// データベースから該当するユーザを取得
		ArrayList<Hashtable<String, String>> result;

		try {
			String sql = "SELECT * FROM T_SYS_USER WHERE USER_NAME = "
					+ JQueryConvert.queryConvert(username);

			result = JApplication.systemDatabase.sendExecuteQuery(sql);

		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M7102", this);
			return;
		}

		if (result.size() <= 0) {
			JErrorMessage.show("M7102", this);
			return;
		}

		Hashtable<String, String> ResultItem = result.get(0);
		String Password = ResultItem.get("PASS");

		if (!Password.equals(password)) {
			// edit h.yoshitama 2009/11/10
			JErrorMessage.show("M7102", this);
			return;
		}

		try {
			/* 二重起動防止 */
			JExecLocker.getLockWithPattern(
					JExecLockerConfig.ADMIN_LOCK_FILENAME,
					JExecLockerConfig.KIKAN_LOCK_FILENAME_PATTERN);
		} catch (Exception e) {
			JErrorMessage.show("M1002", null);
			return;
		}

		JApplication.userID = ResultItem.get("USER_NAME");
		JApplication.password = ResultItem.get("PASS");

		JScene.ChangeScene(new JMenuFrameCtrl());
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		} else if (source == jButton_Login) {
			logger.info(jButton_Login.getText());
			pushedLoginButton();
		}

		// エンターキーでの移動
		else if (source == jTextField_UserName) {
			jTextField_UserName.transferFocus();
		} else if (source == jPasswordField_Password) {
			// 登録ボタンが有効な時だけEnterキーでログインを行う

			if (jButton_Login.isEnabled()) {
				pushedLoginButton();
			} else {
				jButton_Login.transferFocus();
			}
		}

		// del y.okano 2010/05/24
		//else if (source == jButton_Version) {
		//	logger.info(jButton_Version.getText());
		//	pushedVersionButton(e);
		//}
	}

	// add s.inoue 2009/12/04
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			if (jButton_Login.isEnabled()){
				logger.info(jButton_Login.getText());
				pushedLoginButton();
			}
			break;
		// del y.okano 2010/05/24
		//case KeyEvent.VK_F9:
			// edit s.inoue 2009/12/05
		//	logger.info(jButton_Version.getText());
		//	if (jButton_Version.isSelected()){
		//		jButton_Version.setSelected(false);
		//	}else{
		//		jButton_Version.setSelected(true);
		//	}
		//	pushedVersionButton(null);break;
		}
	}

	/**
	 * ログインボタンのステータスをテキストフィールドの内容によって
	 * 変更するためのイベントリスナ
	 */
	public class LoginButtonStatus implements DocumentListener {
		public void changedUpdate(DocumentEvent arg0) {
		}

		public void insertUpdate(DocumentEvent e) {
			setLoginButtonStatus(e);
		}

		public void removeUpdate(DocumentEvent e) {
			setLoginButtonStatus(e);
		}
	}

	/**
	 * ログインボタンのステータスチェックを行う。
	 * LoginButtonStatusイベントから呼び出すための関数。
	 */
	public void setLoginButtonStatus(DocumentEvent e) {
		// ログインボタンの有効化
		if (e.getDocument() == jTextField_UserName.getDocument()
				|| e.getDocument() == jPasswordField_Password.getDocument()) {
			if (jTextField_UserName.getText().length() > 0
					&& (new String(jPasswordField_Password.getPassword()))
							.length() > 0) {
				jButton_Login.setEnabled(true);
			} else {
				jButton_Login.setEnabled(false);
			}
		}
	}
}

//Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

