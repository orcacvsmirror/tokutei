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
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.internationalization.java.XMLResourcesFactory;
import org.openswing.swing.lookup.client.LookupController;
import org.openswing.swing.permissions.java.ButtonsAuthorizations;
import org.openswing.swing.table.profiles.client.FileGridProfileManager;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.java.Consts;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLockerConfig;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.kenshin.admin.JAdminSoftware;

/**
 * ���O�C���t���[��
 */
public class JLoginFrameCtrl extends JLoginFrame {

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JLoginFrameCtrl.class);

	public static void main(String[] args) {
		new JLoginFrameCtrl();
	}

	public JLoginFrameCtrl() {
		jButton_Login.setEnabled(false);

		// ���O�C���{�^���̏�Ԃ��Ǘ�
		jTextField_UserName.getDocument().addDocumentListener(
				new LoginButtonStatus());
		jPasswordField_Password.getDocument().addDocumentListener(
				new LoginButtonStatus());

		// focus����
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
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * ���O�C���{�^��
	 */
	public void pushedLoginButton() {

		String username = jTextField_UserName.getText();
		String password = new String(jPasswordField_Password.getPassword());

		// ���͕�����̒����̃`�F�b�N
		if (username.length() <= 0) {
			JErrorMessage.show("M7100", this);
			return;
		}
		if (password.length() <= 0) {
			JErrorMessage.show("M7101", this);
			return;
		}

		// �f�[�^�x�[�X����Y�����郆�[�U���擾
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
		// add s.inoue 2011/04/11
		setClientSetting();
// del s.inoue 2011/04/11
		try {
			/* ��d�N���h�~ */
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

		// �G���^�[�L�[�ł̈ړ�
		else if (source == jTextField_UserName) {
			jTextField_UserName.transferFocus();
		} else if (source == jPasswordField_Password) {
			// �o�^�{�^�����L���Ȏ�����Enter�L�[�Ń��O�C�����s��

			if (jButton_Login.isEnabled()) {
				pushedLoginButton();
			} else {
				jButton_Login.transferFocus();
			}
		}
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

		// eidt s.inoue 2011/04/15
		case KeyEvent.VK_ENTER:
			if (keyEvent.getComponent()== jPasswordField_Password){
				logger.info(jButton_Login.getText());
				// ���O�C���{�^�����L���ȂƂ������������s��
				if (jButton_Login.isEnabled()) {
					pushedLoginButton();
				} else {
					jPasswordField_Password.transferFocus();
				}
			}
		}
	}

	/*
	 * ClientSetting�ݒ�
	 */
	private static void setClientSetting(){

		Hashtable domains = new Hashtable();
		Properties props = new Properties();

		// �{�^���̐���i�����\���j
		ButtonsAuthorizations auth = new ButtonsAuthorizations();
		auth.addButtonAuthorization("F1",true,true,true);

		// add s.inoue 2011/04/15
	    Domain sexDomain = new Domain("KENGEN");
	    sexDomain.addDomainPair("1","Administrator");
	    sexDomain.addDomainPair("2","User");
	    domains.put(
	      sexDomain.getDomainId(),
	      sexDomain
	    );

		// ���{����g�p
	    Hashtable xmlFiles = new Hashtable();
	    xmlFiles.put("JP","resources_jp.xml");
	    ClientSettings clientSettings = new ClientSettings(
	        new XMLResourcesFactory(xmlFiles,true),
	        domains,
	        auth
 	    );

	    // �K�{�}�[�N
		ClientSettings.VIEW_MANDATORY_SYMBOL = true;
		// ������
		ClientSettings.FILTER_PANEL_ON_GRID = true;
		// �����b�N(0:closeOnExit,1:useClose,2:pressed,3unPressed)
		ClientSettings.FILTER_PANEL_ON_GRID_POLICY = Consts.FILTER_PANEL_ON_GRID_USE_PADLOCK_UNPRESSED;

		// �Z���F
		ClientSettings.VIEW_BACKGROUND_SEL_COLOR = true;
		// buttonText
		ClientSettings.BUTTON_BEHAVIOR = Consts.BUTTON_IMAGE_AND_TEXT;
		// �\�[�g�\�L
		ClientSettings.SHOW_SORTING_ORDER = true;
		ClientSettings.SHOW_FOCUS_BORDER_ON_FORM = true;
		// �R���g���[���Q�փt�H�[�J�X�o���邩�ǂ���
		ClientSettings.DISABLED_INPUT_CONTROLS_FOCUSABLE = false;

	    ClientSettings.ALLOW_OR_OPERATOR = false;
	    ClientSettings.INCLUDE_IN_OPERATOR = false;

		ClientSettings.GRID_PROFILE_MANAGER = new FileGridProfileManager();
	    ClientSettings.SELECT_DATA_IN_EDITABLE_GRID = true;

	    // filter�@�\
	    ClientSettings.GRID_PROFILE_MANAGER = new FileGridProfileManager();
	    ClientSettings.LOOKUP_FRAME_CONTENT = LookupController.GRID_AND_FILTER_FRAME;

	    // �y�[�W�i�ݐ�
	    ClientSettings.GRID_SCROLL_BLOCK_INCREMENT = Consts.GRID_SCROLL_BLOCK_INCREMENT_PAGE;

		// ����
		ClientSettings.getInstance().setLanguage("JP");

		ClientSettings.LOOK_AND_FEEL_CLASS_NAME =PropertyUtil.getProperty( "setting.lookAndFeel");

		// eidt s.inoue 2013/03/12
		try{
//		  String osname = System.getProperty("os.name");
//	      if(osname.indexOf("Windows")>=0){
	    	  Class.forName(ClientSettings.LOOK_AND_FEEL_CLASS_NAME).getMethod(
			      "setCurrentTheme", new Class[] {Properties.class}).invoke(null,
			      new Object[] {props});
	    	  UIManager.setLookAndFeel(ClientSettings.LOOK_AND_FEEL_CLASS_NAME);
//	      }
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * ���O�C���{�^���̃X�e�[�^�X���e�L�X�g�t�B�[���h�̓��e�ɂ����
	 * �ύX���邽�߂̃C�x���g���X�i
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
	 * ���O�C���{�^���̃X�e�[�^�X�`�F�b�N���s���B
	 * LoginButtonStatus�C�x���g����Ăяo�����߂̊֐��B
	 */
	public void setLoginButtonStatus(DocumentEvent e) {
		// ���O�C���{�^���̗L����
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

