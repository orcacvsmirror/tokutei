package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//import jp.or.med.orca.jma_tokutei.common.app.ClientSettings;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Getuji;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Hantei;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_KouteiMaster;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Master;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Nitiji;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Serche;
import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLockerConfig;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DngPreviewHtml;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.SettingDialog;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;
import jp.or.med.orca.jma_tokutei.dbfix.ShcDBAdjust;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;

import org.apache.log4j.Logger;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.internationalization.java.XMLResourcesFactory;
import org.openswing.swing.lookup.client.LookupController;
import org.openswing.swing.permissions.java.ButtonsAuthorizations;
import org.openswing.swing.table.profiles.client.FileGridProfileManager;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.java.Consts;

/**
 * ���茒�f�\�t�g�E�G�A�̃��O�C����ʂ̃t���[���̃R���g���[��
 *
 * Modified 2008/04/01 �������萔�ɕύX�B Modified 2008/04/06 ���F������̂��߁A�s�v�ȃR�����g���폜���AJava
 * �̋K��Ɋ�Â��� �t�H�[�}�b�g���s�Ȃ��B
 *
 * Modified 2008/05/14 ���j���[��ʂ���߂�@�\�ւ̑Ή�
 */
public class JLoginFrameCtrl extends JLoginFrame {
	private static final String TEXT_NO_NAME = "�i���̂��ݒ肳��Ă��܂���j";

	/** ���f�@�֔ԍ��̐��K�\�� */
	private static final String REGEX_KENSHINKIKAN_NO = "^\\d{10}.(fdb|FDB)";
	private static int lastSelectedKikanInfomationIndex = 0;
	private static Pattern kikanInfomationPattern = Pattern.compile("^.* - (\\d{10})$");
	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	// edit s.inoue 2009/12/12
	private static org.apache.log4j.Logger logger = Logger.getLogger(JLoginFrameCtrl.class);
	// edit s.inoue 2009/12/18
	private IDialog settingDialog;

	/**
	 * �R���X�g���N�^
	 */
	public JLoginFrameCtrl() {

		jButton_Login.setEnabled(false);

		jTextField_UserName.getDocument().addDocumentListener(
				new LoginButtonStatus());
		jPasswordField_Password.getDocument().addDocumentListener(
				new LoginButtonStatus());

		// /* �@�֔ԍ��̈ꗗ��ݒ肷��B */
		// File file = new File(JPath.DatabaseFolder);
		// File[] files = file.listFiles();
		//
		// if (files != null) {
		// for (int i = 0; files.length > i; i++) {
		// String filename = files[i].getName();
		// if (Pattern.matches(REGEX_KENSHINKIKAN_NO, filename)) {
		// /* �@��DB�Ȃ�A�@�֔ԍ����R���{�{�b�N�X�ɐݒ肷��B */
		//
		// String kikanNumber = filename.substring(
		// 0, filename.length() - 4);
		// jComboBox_kikanNumber.addItem(kikanNumber);
		// }
		// }
		// }

		/* �@�֑I�𗓂�����������B */
		this.initializeKikanComboBox();

		/* �^�C�g���o�[������������B */
		this.initializeFrameTitle();

		/* �X�v���b�V����ʂ���\���ɂȂ����ꍇ�A�o�[�W�����{�^����L���ɂ���B */
		// del y.okano 2010/05/24
		//JSoftware.getSplashFrame().addComponentListener(new ComponentAdapter() {
		//	@Override
		//	public void componentHidden(ComponentEvent arg0) {
		//		JLoginFrameCtrl.this.jButton_Version.setSelected(false);
		//	}
		//});

		/* ���O�C����ʂ��A�N�e�B�u�Ŗ����Ȃ����Ƃ��A�X�v���b�V����ʂ��\���ɂ���B */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JSoftware.getSplashFrame().hideSplashWindow();
			}
		});

		this.setVisible(true);

// del s.inoue 2013/03/12
//		try{
//		  String osname = System.getProperty("os.name");
//	      if(osname.indexOf("Linux")>=0){
//	    	  this.jButton_Setting.setEnabled(false);
//	      }
//		}catch (Exception e) {
//			logger.error(e.getMessage());
//		}

		/* ���[�U�����͗��Ƀt�H�[�J�X���ړ�����B */
		// focus����
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
		this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		this.focusTraversalPolicy.addComponent(this.jButton_Login);
		// del y.okano 2010/05/24
		//this.focusTraversalPolicy.addComponent(this.jButton_Version);
		this.focusTraversalPolicy.addComponent(jButton_End);
		jTextField_UserName.requestFocus();
		this.jComboBox_kikanNumber.transferFocus();

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * �@�֑I�𗓂�����������B
	 */
	private boolean initializeKikanComboBox() {
		boolean retValue = false;

		ArrayList<Hashtable<String, String>> items = null;
		try {
			items = JApplication.systemDatabase
					.sendExecuteQuery("SELECT * FROM T_F_KIKAN");
			retValue = true;

		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M7200", this);
		}

		if (retValue && items != null) {
			for (int i = items.size() - 1; i >= 0; i--) {
				Hashtable<String, String> item = items.get(i);

				String kikanNumber = item.get("TKIKAN_NO");
				String kikanName = item.get("KIKAN_NAME");

				StringBuffer buffer = new StringBuffer();
				if (kikanName != null && !kikanName.isEmpty()) {
					buffer.append(kikanName);
				} else {
					buffer.append(TEXT_NO_NAME);
				}
				buffer.append(" - ");
				buffer.append(kikanNumber);

				this.jComboBox_kikanNumber.addItem(buffer.toString());
			}
		}

		this.jComboBox_kikanNumber.setSelectedIndex(lastSelectedKikanInfomationIndex);

		return retValue;
	}

	private void initializeFrameTitle() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(ViewSettings.getTokuteFrameTitle());
		buffer.append(" (Version ");
		buffer.append(JApplication.versionNumber);
		buffer.append(")");

		String title = buffer.toString();
		this.setTitle(title);
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * �o�[�W�����{�^��
	 */
	// del y.okano 2010/05/24
	//public void pushedVersionButton(ActionEvent e) {
	//	if (jButton_Version.isSelected()) {
	//		JSoftware.getSplashFrame().showSplashWindow();
	//	} else {
	//		JSoftware.getSplashFrame().hideSplashWindow();
	//	}
	//}

	// add s.inoue 2009/12/18
	/**
	 * ���ݒ�{�^��
	 */
	public void pushedSettingButton(ActionEvent e) {
		try {
			settingDialog
				= DialogFactory.getInstance().createDialog(this, e, null);

			settingDialog.setMessageTitle("���ݒ���");

			settingDialog.setVisible(true);
			// del s.inoue 2010/04/07
			// if (settingDialog.getStatus().equals(RETURN_VALUE.CANCEL))
			// 	return;

			// add s.inoue 2009/12/18
			reloginSystem();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	/* �ă��O�C�� */
	private void reloginSystem(){
		try{
			// window��S�ĕ���LookAndFeel
			java.awt.Window[] win =this.getWindows();
			for (int i = 0; i < win.length; i++) {
				win[i].dispose();
			}

			JScene.ChangeScene(new JLoginFrameCtrl());

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
	/**
	 * ���O�C���{�^��
	 */
	public void pushedLoginButton() {
		/* ���[�U�� */
		String userName = jTextField_UserName.getText();
		// ���͕�����̒������`�F�b�N
		if (userName.length() <= 0) {
			JErrorMessage.show("M1100", this);
			return;
		}

		/* �p�X���[�h */
		String password = new String(this.jPasswordField_Password.getPassword());
		if (password.length() <= 0) {
			JErrorMessage.show("M1101", this);
			return;
		}

		/* �@�֔ԍ� */
		String kikan = (String) jComboBox_kikanNumber.getSelectedItem();

		Matcher m = kikanInfomationPattern.matcher(kikan);
		String kikanNumber = null;
		if (m.matches() && m.groupCount() > 0) {
			kikanNumber = m.group(1);
		}

		if (kikanNumber == null || kikanNumber.length() < 10) {
			JErrorMessage.show("M1102", this);
			return;
		}


		// �f�[�^�x�[�X�ɐڑ�
		try {
			JApplication.kikanDatabase = JConnection
					.ConnectKikanDatabase(kikanNumber);
		} catch (SQLException e1) {
			JErrorMessage.show("M1103", this);
			return;
		}

		// openswing s.inoue 2010/12/21
		setClientSetting();

		// ���O�C��������
		ArrayList<Hashtable<String, String>> result;
		try {
			result = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT * FROM T_USER WHERE USER_NAME = "
							+ JQueryConvert.queryConvert(userName));
		} catch (SQLException e1) {
			JErrorMessage.show("M1104", this);
			return;
		}

		if (result.size() <= 0) {
			JErrorMessage.show("M1104", this);
			return;
		}

		Hashtable<String, String> resultItem = result.get(0);
		String dbPassword = resultItem.get("PASS");

		if (!dbPassword.equals(password)) {
			// edit h.yoshitama 2009/11/10
			JErrorMessage.show("M1104", this);
			return;
		}

		JApplication.kikanNumber = kikanNumber;
		JApplication.userID = resultItem.get("USER_NAME");
		JApplication.password = resultItem.get("PASS");
		JApplication.authority = JInteger.valueOf(resultItem.get("KENGEN"));

		JApplication.loadORCAData();


		// add s.inoue 2011/05/09
		String gridSearchCnt = PropertyUtil.getProperty( "grid.search.viewCount");
		JApplication.gridViewSearchCount =Integer.parseInt(gridSearchCnt);

		String gridMasterCnt = PropertyUtil.getProperty( "grid.master.viewCount");
		JApplication.gridViewMasterCount =Integer.parseInt(gridMasterCnt);

		try {
			result = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT KIKAN_NAME FROM T_KIKAN");
		} catch (SQLException e1) {
			JErrorMessage.show("M1104", this);
			return;
		}

		if (result.size() == 1) {
			JApplication.kikanName = result.get(0).get("KIKAN_NAME");
		}

		// yoshida 20090105 s.inoue
		// �@�֔ԍ�.fdb��Ώ� kikanNumber �� String
		// ���ׂ������Ȃ��ׁADB����U��~
		try{
			JApplication.kikanDatabase.Shutdown();
		} catch (SQLException ex) {
			JErrorMessage.show("M1103", this);
			return;
		}

		// yoshida 20090105 s.inoue
		ShcDBAdjust adjuster = new ShcDBAdjust();
        adjuster.call(JApplication.kikanNumber);

		// add ver2 s.inoue 2009/07/06
		DBYearAdjuster yajuster = new DBYearAdjuster();
		yajuster.call(JApplication.kikanNumber);

        // ���ׂ������Ȃ��ׁADB����U�ċN��
        try{
        	JApplication.kikanDatabase = JConnection.ConnectKikanDatabase(kikanNumber);

		} catch (SQLException ex) {
			JErrorMessage.show("M1103", this);
			return;
		}
        
        // add s.inoue 2014/04/30
        setScreenColumns();

        // �o�[�W����(�X�L�[�}�A�f�[�^)��T_Version���擾
        try {
			JApplication.loadDBVersions(JApplication.kikanDatabase);
		} catch (Exception ex) {
			JErrorMessage.show("M1106", this);
			return;
		}

// edit s.inoue 2009/09/29
// �J�����̓R�����g�A�E�g����
		/* ��d�N������ */
		try {
			String lockfileName = JExecLockerConfig.LOCK_FILENAME_BASE + "."
					+ kikanNumber;
			JExecLocker.getLockWithName(lockfileName);
		} catch (Exception e) {
			JErrorMessage.show("M1002", null);
			return;
		}

		lastSelectedKikanInfomationIndex = this.jComboBox_kikanNumber.getSelectedIndex();

		logger.info("�@�֔ԍ�:" + kikanNumber + "�փ��O�C��");

		// openswing s.inoue 2011/02/15
		String lookAndFeel = PropertyUtil.getProperty( "setting.lookAndFeel");
		if (!lookAndFeel.equals("")){
			SettingDialog sd = new SettingDialog();
			sd.changeTheLookAndFeel(lookAndFeel,true);
		}

		JScene.ChangeScene(new JMenuFrameCtrl());
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_Login) {
			logger.info(jButton_Login.getText());
			pushedLoginButton();
		}
		else if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		else if (source == jButton_UpdateInfo){
			logger.info(jButton_UpdateInfo.getText());
    		// edit s.inoue 2010/08/02
//			DngPreviewHtml dng = new DngPreviewHtml();
//    		dng.setLayout("http://www.orca.med.or.jp/ikensyo/info/");
			try {
				settingDialog
					= DialogFactory.getInstance().createDialog(this, e, null);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		// add s.inoue 2009/12/18
		else if (source == jButton_Setting) {
			logger.info(jButton_Setting.getText());
			pushedSettingButton(e);
		}
		else if (source == jTextField_UserName) {
			logger.info(jTextField_UserName.getText());
			jTextField_UserName.transferFocus();
		}
		else if (source == jPasswordField_Password) {
			// ���O�C���{�^�����L���ȂƂ������������s��
			if (jButton_Login.isEnabled()) {
				pushedLoginButton();
			} else {
				jPasswordField_Password.transferFocus();
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		Object source = e.getSource();
		char keyChar = e.getKeyChar();

		if (source == this.jComboBox_kikanNumber) {
			if (keyChar == KeyEvent.VK_ENTER) {
				this.jComboBox_kikanNumber.transferFocus();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;

		// edit s.inoue 2010/08/02
		case KeyEvent.VK_F7:
			logger.info(jButton_UpdateInfo.getText());
    		DngPreviewHtml dng = new DngPreviewHtml(this);
    		// dng.setLayout("http://www.orca.med.or.jp/ikensyo/info/");

		case KeyEvent.VK_F8:
			logger.info(jButton_Setting.getText());
			pushedSettingButton(null);break;
		case KeyEvent.VK_F12:
			if(jButton_Login.isEnabled()){
				logger.info(jButton_Login.getText());
				pushedLoginButton();
			}
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

	/**
	 * ���O�C���{�^���̃X�e�[�^�X���e�L�X�g�t�B�[���h�̓��e�ɂ���� �ύX���邽�߂̃C�x���g���X�i
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
	 * ���O�C���{�^���̃X�e�[�^�X�`�F�b�N���s���B LoginButtonStatus�C�x���g����Ăяo�����߂̊֐��B
	 */
	public void setLoginButtonStatus(DocumentEvent e) {
		// ���O�C���{�^���̗L����
		if (e.getDocument() == jTextField_UserName.getDocument()
				|| e.getDocument() == jPasswordField_Password.getDocument()) {
			if (jTextField_UserName.getText().length() > 0 &&

			/* �@�֔ԍ����R���{�{�b�N�X����I���ł���悤�ɕύX */
			(new String(jPasswordField_Password.getPassword())).length() > 0) {
				jButton_Login.setEnabled(true);
			} else {
				jButton_Login.setEnabled(false);
			}
		}
	}

	// openswing s.inoue 2010/11/29
	/*
	 * ClientSetting�ݒ�
	 */
	private static void setClientSetting(){

		JApplication.domains = new Hashtable();
		Properties props = new Properties();

		// add s.inoue 2012/10/26
	    Domain jisiDomain = new Domain("JISI_KBN");
	    jisiDomain.addDomainPair("2","��");
	    jisiDomain.addDomainPair("1","��");
	    JApplication.domains.put(
	    	jisiDomain.getDomainId(),
	    	jisiDomain
	    );

	    Domain getujiDomain = new Domain("JISI_KBN_WK");
	    getujiDomain.addDomainPair("2","��");
	    getujiDomain.addDomainPair("1","��");
	    JApplication.domains.put(
	    		getujiDomain.getDomainId(),
	    		getujiDomain
	    );

		// add s.inoue 2012/10/23
	    Domain inputDomain = new Domain("KEKA_INPUT_FLG");
	    inputDomain.addDomainPair("1","��");
	    inputDomain.addDomainPair("2","��");
	    JApplication.domains.put(
    		inputDomain.getDomainId(),
    		inputDomain
	    );

		// add s.inoue 2012/10/23
	    Domain sexDomain = new Domain("SEX");
	    sexDomain.addDomainPair("1","�j");
	    sexDomain.addDomainPair("2","��");
	    JApplication.domains.put(
	      sexDomain.getDomainId(),
	      sexDomain
	    );

		// add s.inoue 2011/04/15
	    Domain kengenDomain = new Domain("KENGEN");
	    kengenDomain.addDomainPair("1","Administrator");
	    kengenDomain.addDomainPair("2","User");
	    JApplication.domains.put(
	      kengenDomain.getDomainId(),
	      kengenDomain
	    );

		// add s.inoue 2011/04/15
	    Domain hisuDomain = new Domain("HISU_FLG");
	    hisuDomain.addDomainPair("1","Basic");
	    hisuDomain.addDomainPair("2","Detail");
	    hisuDomain.addDomainPair("3","Additional");
	    JApplication.domains.put(
    		hisuDomain.getDomainId(),
    		hisuDomain
	    );

	    // add s.inoue 2011/10/13
	    Domain hokensidoDomain = new Domain("HOKENSIDO_LEVEL");
	    hokensidoDomain.addDomainPair("1","HokenSido_Mihantei");
	    hokensidoDomain.addDomainPair("2","HokenSido_SekyokuSien");
	    hokensidoDomain.addDomainPair("3","HokenSido_DoukiSien");
	    hokensidoDomain.addDomainPair("4","HokenSido_JyohoTeikyo");
	    hokensidoDomain.addDomainPair("5","HokenSido_HanteiFunou");
	    JApplication.domains.put(
    		hokensidoDomain.getDomainId(),
    		hokensidoDomain
	    );

	    // add s.inoue 2011/10/13
	    Domain metaboDomain = new Domain("METABOHANTEI_LEVEL");
	    metaboDomain.addDomainPair("1","Metabo_Mihantei");
	    metaboDomain.addDomainPair("2","Metabo_KijyunGaito");
	    metaboDomain.addDomainPair("3","Metabo_YobigunGaito");
	    metaboDomain.addDomainPair("4","Metabo_Higaito");
	    metaboDomain.addDomainPair("5","Metabo_HanteiFunou");
	    JApplication.domains.put(
    		metaboDomain.getDomainId(),
    		metaboDomain
	    );

	    // add s.inoue 2011/10/13
	    Domain seikyuDomain = new Domain("SEIKYUKBN_LEVEL");
	    seikyuDomain.addDomainPair("1","Seikyu_Kihon");
	    seikyuDomain.addDomainPair("2","Seikyu_KihonSyosai");
	    seikyuDomain.addDomainPair("3","Seikyu_KihonTuika");
	    seikyuDomain.addDomainPair("4","Seikyu_KihonSyosaiTuika");
	    seikyuDomain.addDomainPair("5","Seikyu_NingenDoc");
	    JApplication.domains.put(
    		seikyuDomain.getDomainId(),
    		seikyuDomain
	    );

	    // add s.inoue 2012/11/15
	    Domain itakuDomain = new Domain("ITAKU_KBN");
	    itakuDomain.addDomainPair("1","Itaku_Kobetu");
	    itakuDomain.addDomainPair("2","Itaku_Syudan");
	    JApplication.domains.put(
	    	itakuDomain.getDomainId(),
	    	itakuDomain
	    );

	    // add s.inoue 2013/11/08
	    Domain logLevelDomain = new Domain("LOG_LEVEL");
	    logLevelDomain.addDomainPair("INFO","����");
	    logLevelDomain.addDomainPair("WARN","�x��");
	    logLevelDomain.addDomainPair("ERROR","�ُ�");
	    JApplication.domains.put(
    		logLevelDomain.getDomainId(),
    		logLevelDomain
	    );

	    // add s.inoue 2013/11/08
	    Domain logPlaceDomain = new Domain("LOG_PLACE");
	    logPlaceDomain.addDomainPair("JPath","�p�X");
	    logPlaceDomain.addDomainPair("JKekkaRegisterCommentInputDialog","�R�����g�idialog�j");
	    logPlaceDomain.addDomainPair("JKekkaRegisterKekkaMojiretsuInputDialog","������idialog�j");
	    logPlaceDomain.addDomainPair("JSelectHokenjyaOrcaDialogCtrl","orca�I��");
	    logPlaceDomain.addDomainPair("JAddKikanInformationFrameCtrl","�@�֏��ǉ�");
	    logPlaceDomain.addDomainPair("DBYearAdjuster","�o�N�����i�N�x�����j");
	    logPlaceDomain.addDomainPair("JHokenjyaMasterMaintenanceEditFrameCtrl","�ی��ҏ��i�ҏW�j");
	    logPlaceDomain.addDomainPair("JHokenjyaMasterMaintenanceListFrame","�ی��ҏ��i�ҏW�j");
	    logPlaceDomain.addDomainPair("JImportDataFrameCtrl","���f���ʃf�[�^��荞��");
	    logPlaceDomain.addDomainPair("JKeinenMasterMaintenanceListFrame","�o�N�}�X�^�����e�i���X");
	    logPlaceDomain.addDomainPair("JKenshinMasterMaintenanceListFrame","���f���ڃ}�X�^�����e�i���X");
	    logPlaceDomain.addDomainPair("JKenshinpatternMasterMaintenanceListFrame","���f�p�^�[�������e�i���X");
	    logPlaceDomain.addDomainPair("JKenshinPatternMaintenanceEditFrameCtrl","���f�p�^�[�������e�i���X�i�ҏW�j");
	    logPlaceDomain.addDomainPair("JLoginFrameCtrl","���O�C��");
	    logPlaceDomain.addDomainPair("JMasterMaintenanceFrameCtrl","�}�X�^�����e�i���X���j���[");
	    logPlaceDomain.addDomainPair("JMenuFrameCtrl","���C�����j���[");
	    logPlaceDomain.addDomainPair("JSystemMaintenanceFrameCtrl","�V�X�e�������e�i���X���j���[");
	    logPlaceDomain.addDomainPair("JOutputGetujiSearchListFrame","��������");
	    logPlaceDomain.addDomainPair("JHanteiSearchResultListFrame","���^�{���b�N�V���h���[������E�K�w��");
	    logPlaceDomain.addDomainPair("JShowResultFrameCtrl","���f���ʕ\���E��������");
	    logPlaceDomain.addDomainPair("JKekkaRegisterFrameCtrl","���f���ʃf�[�^����");
	    logPlaceDomain.addDomainPair("JKenshinKekkaSearchListFrame","���f���ʃf�[�^�ꗗ");
	    logPlaceDomain.addDomainPair("JKojinRegisterFrameCtrl","��f�����͉��");
	    logPlaceDomain.addDomainPair("JRegisterFlameCtrl","���f���ʃf�[�^����");
	    logPlaceDomain.addDomainPair("JInputKessaiDataFrameCtrl","�����f�[�^�ҏW");
	    logPlaceDomain.addDomainPair("JOutputNitijiSearchListFrame","��������");
	    logPlaceDomain.addDomainPair("JSelectHokenjyaFrameCtrl","�ی��ҏ��idialog�j");
	    logPlaceDomain.addDomainPair("JSelectKojinFrameCtrl","��f�ҁidialog�j");
	    logPlaceDomain.addDomainPair("JSelectShiharaiFrameCtrl","�x����s�@�ցidialog�j");
	    logPlaceDomain.addDomainPair("JShiharaiMasterMaintenanceEditFrameCtrl","�x����s��񃁃��e�i���X�i�ҏW�j");
	    logPlaceDomain.addDomainPair("JShiharaiMasterMaintenanceListFrame","�x����s��񃁃��e�i���X");
	    logPlaceDomain.addDomainPair("JShokenMasterMaintenanceEditFrameCtrl","�����}�X�^�����e�i���X�i�ҏW�j");
	    logPlaceDomain.addDomainPair("JShokenMasterMaintenanceListFrame","�����}�X�^�����e�i���X");
	    logPlaceDomain.addDomainPair("JKikanDBBackupFrameCtrl","�@��DB�o�b�N�A�b�v������");
	    logPlaceDomain.addDomainPair("JKikanLogListFrame","���O�t�@�C���Ǘ�");
	    logPlaceDomain.addDomainPair("JUsabilityFrameCtrl","���[�U�r���e�B�����e�i���X");
	    logPlaceDomain.addDomainPair("JKekkaTeikeiMaintenanceEditFrameCtrl","�����}�X�^�����e�i���X�i�ҏW�j");
	    logPlaceDomain.addDomainPair("JKekkaTeikeiMaintenanceListFrameCtrl","�����}�X�^�����e�i���X");
	    logPlaceDomain.addDomainPair("JKenshinMasterMaintenanceFrameCtrl","���f���ڃ}�X�^�����e�i���X");
	    logPlaceDomain.addDomainPair("JKenshinPatternMaintenanceAddFrameCtrl","�@�֏�񃁃��e�i���X�ҏW�i�ǉ��j");
	    logPlaceDomain.addDomainPair("JKenshinPatternMaintenanceCopyFrameCtrl","�@�֏�񃁃��e�i���X�ҏW�i�����j");
	    logPlaceDomain.addDomainPair("JKikanDBBackupFrameCtrl","���f���ʃf�[�^��荞��");
	    logPlaceDomain.addDomainPair("JNayoseMaintenanceEditFrameCtl","�o�N�}�X�^�����e�i���X�i�ҏW�j");
	    logPlaceDomain.addDomainPair("JNayoseMaintenanceListFrameCtl","�o�N�}�X�^�����e�i���X");
	    logPlaceDomain.addDomainPair("JRegisterUserFrameCtrl","���[�U�}�X�^�����e�i���X");
	    logPlaceDomain.addDomainPair("JSystemMaintenanceFrameCtrl","���f���ʃf�[�^��荞��");
	    logPlaceDomain.addDomainPair("JUserMaintenanceFrameCtrl","���f���ʃf�[�^��荞��");
	    // onlineupdate
	    logPlaceDomain.addDomainPair("KikanSchemaChangeTask","�@�փX�L�[�}����/onlineupdate");
	    logPlaceDomain.addDomainPair("ModuleCopyTask","�t�@�C������/onlineupdate");
	    logPlaceDomain.addDomainPair("SystemDataUpdateTask","�V�X�e���f�[�^����/onlineupdate");
	    logPlaceDomain.addDomainPair("SystemSchemaChangeTask","�V�X�e���X�L�[�}����/onlineupdate");
	    logPlaceDomain.addDomainPair("JMainFrame","���C�����/onlineupdate");
	    // admin
	    logPlaceDomain.addDomainPair("JDBBackupFrameCtrl","�V�X�e���o�b�N�A�b�v������/�Ǘ�");
	    logPlaceDomain.addDomainPair("JKikanMaintenanceFrameCtrl","�@�֏�񃁃��e�i���X/�Ǘ�");
	    logPlaceDomain.addDomainPair("JLoginFrameCtrl","���O�C��/�Ǘ�");
	    logPlaceDomain.addDomainPair("JMenuFrameCtrl","���C�����j���[/�Ǘ�");
	    logPlaceDomain.addDomainPair("JRegisterUserFrameCtrl","���f���ʃf�[�^��荞��/�Ǘ�");
	    
	    JApplication.domains.put(
    		logPlaceDomain.getDomainId(),
    		logPlaceDomain
	    );

// del s.inoue 2012/02/20
//	    // eidt s.inoue 2011/10/18
//		// �������ڂ̑I�����擾
//		ArrayList<Hashtable<String, String>> codeResult = null;
//		Hashtable<String, String> codeResultItem;
//
//		String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE order by KOUMOKU_CD desc,CODE_NUM");
//		try {
//			codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//
//		String preKoumokuCD = "";
//		String nextKoumokuCD = "";
//		Domain koumokuDomain = null;
//		Domain patternDomain = null;
//
//		for (int j = 0; j < codeResult.size(); j++) {
//			codeResultItem = codeResult.get(j);
//
//			String koumokuCD = codeResultItem.get("KOUMOKU_CD");
//			String codeNUM = codeResultItem.get("CODE_NUM");
//			String codeNAME = codeResultItem.get("CODE_NAME");
//
//			if (!koumokuCD.equals(preKoumokuCD))
//				koumokuDomain = new Domain(koumokuCD);
//
//			koumokuDomain.addDomainPair(codeNUM,codeNAME);
//			System.out.println(koumokuCD + " "+codeNUM + " " + codeNAME);
//
//			if (j+1 < codeResult.size())
//				nextKoumokuCD = codeResult.get(j + 1).get("KOUMOKU_CD");
//
//			// eidt s.inoue 2012/02/09
//			// �ŏI���ڂ�������̂�h��
//			if (!koumokuCD.equals(nextKoumokuCD) ||
//					(j == codeResult.size() - 1))
//				domains.put(koumokuDomain.getDomainId(),koumokuDomain);
//
//			preKoumokuCD = koumokuCD;
//		}


//	 // ��������������������������������������������������
//	 // add s.inoue 2012/06/11
//
//		// �Ǝ��ǉ����ڃt���O�擾
//	    ArrayList<Hashtable<String, String>> results = null;
//	    boolean origin_flg = false;
//
//		try{
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
//			sb.append(" FROM T_SCREENFUNCTION ");
//			sb.append(" WHERE SCREEN_CD = ");
//			sb.append(JQueryConvert.queryConvert("004"));
//
//			results = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//		}
//
//		for (int i = 0; i < results.size(); i++) {
//			Hashtable<String, String> item = results.get(i);
//
//			String functionCd = item.get("FUNCTION_CD");
//
//			if (JApplication.func_orijinCode.equals(functionCd)){
//				origin_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
//			}
//		}
//
//		// �O���t�p���f���ڃ��X�g
//		ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
//		Hashtable<String,String> resultItem = new Hashtable<String,String>();
//
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("SELECT distinct case MS.HISU_FLG when 1 then '��{' when 2 then '�ڍ�' else '�ǉ�' end as HISU_FLG,");
//		sb.append("MS.KOUMOKU_CD,MS.KOUMOKU_NAME,MS.KENSA_HOUHOU,XMLITEM_SEQNO ");
//		sb.append("FROM T_KENSHINMASTER MS ");
//		sb.append("INNER JOIN T_KENSHIN_P_S PS ");
//		sb.append("ON MS.KOUMOKU_CD = PS.KOUMOKU_CD ");
//		sb.append(" AND MS.HKNJANUM = ");
//		sb.append(JQueryConvert.queryConvert("99999999"));
//		sb.append(" ORDER BY K_P_NO, LOW_ID ");
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//
//		JKenshinPatternMaintenanceEditFrameData tuikaCode =
//			new JKenshinPatternMaintenanceEditFrameData();
//
//		HashSet<String> dokujiKoumokuCD = tuikaCode.getDokujiTuikaCD();
//
//		JApplication.graphDomain = new Domain("T_KENSHINMASTER");
//
//		for( int i = 0;i < result.size();i++ )
//		{
//			resultItem = result.get(i);
//
//			String koumokuCD = resultItem.get("KOUMOKU_CD");
//			String combItem = "";
//			if(dokujiKoumokuCD.contains(koumokuCD)){
//				if (origin_flg)
//				combItem = koumokuCD +":" + resultItem.get("KOUMOKU_NAME") +":"+ resultItem.get("KENSA_HOUHOU");
//			}else{
//				combItem = koumokuCD +":" + resultItem.get("KOUMOKU_NAME") +":"+ resultItem.get("KENSA_HOUHOU");
//			}
//			if (!koumokuCD.equals("") && !combItem.equals(""))
//			JApplication.graphDomain.addDomainPair(koumokuCD,combItem);
//		}
//
//		JApplication.domains.put(
//				JApplication.graphDomain.getDomainId(),JApplication.graphDomain);
//
//	 // ��������������������������������������������������
	    
	    // move s.inoue 2014/04/30
	    // ���O�C�����������ƃ^�C�~���O����
	    
	    
	    // add s.inoue 2012/10/25
		// �x����s
		ArrayList<Hashtable<String, String>> siharaiItems = null;
		String shiharai = "SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO DESC";
		try {
			siharaiItems = JApplication.kikanDatabase.sendExecuteQuery(shiharai);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		JApplication.shiharaiDomain = new Domain("T_SHIHARAI");

		for (int i = 0; i < siharaiItems.size(); i++) {
			Hashtable<String, String> Item;
			Item = siharaiItems.get(i);

			String siharaiNO = Item.get("SHIHARAI_DAIKO_NO");
			String shiharaiNAME = Item.get("SHIHARAI_DAIKO_NAME");

			if (shiharaiNAME.length() >10)
				shiharaiNAME = shiharaiNAME.substring(0, 10);

			JApplication.shiharaiDomain.addDomainPair(siharaiNO,siharaiNO +":"+ shiharaiNAME);
		}
		JApplication.domains.put(
				JApplication.shiharaiDomain.getDomainId(),JApplication.shiharaiDomain);

	    // add s.inoue 2012/10/24
		// �ی���
		ArrayList<Hashtable<String, String>> hokenjaItems = null;
		String hokenja = "SELECT DISTINCT(HKNJANUM) HKNJANUM, HKNJANAME, POST, ADRS, TEL FROM T_HOKENJYA ORDER BY HKNJANUM DESC";
		try {
			hokenjaItems = JApplication.kikanDatabase.sendExecuteQuery(hokenja);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		JApplication.hokenjaDomain = new Domain("T_HOKENJYA");

		for (int i = 0; i < hokenjaItems.size(); i++) {
			Hashtable<String, String> Item;
			Item = hokenjaItems.get(i);

			String hokenjaNO = Item.get("HKNJANUM");
			String kpNAME = Item.get("HKNJANAME");

			if (kpNAME.length() >10)
				kpNAME = kpNAME.substring(0, 10);

			JApplication.hokenjaDomain.addDomainPair(hokenjaNO,hokenjaNO +":"+ kpNAME);
		}
		JApplication.domains.put(
				JApplication.hokenjaDomain.getDomainId(),JApplication.hokenjaDomain);
		// ��������������������������


		// ���f�p�^�[�����X�g
		ArrayList<Hashtable<String, String>> items = null;
		String pattern = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO";
		try {
			items = JApplication.kikanDatabase.sendExecuteQuery(pattern);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		// patternDomain = new Domain("T_KENSHIN_P_M");
		JApplication.patternDomain = new Domain("T_KENSHIN_P_M");

		for (int i = 0; i < items.size(); i++) {
			Hashtable<String, String> Item;
			Item = items.get(i);

			String kpNO = Item.get("K_P_NO");
			String kpNAME = Item.get("K_P_NAME");
			// add s.inoue 2011/11/28
			if(!kpNO.equals("9999")){
				JApplication.patternDomain.addDomainPair(kpNO,kpNAME);
			}
		}
		JApplication.domains.put(
				JApplication.patternDomain.getDomainId(),JApplication.patternDomain);

		// add s.inoue 2013/11/14
		// getFunctionSetting();
		
		// �{�^���̐���i�����\���j
		ButtonsAuthorizations auth = new ButtonsAuthorizations();
		auth.addButtonAuthorization("F1",true,true,true);
		// ���{����g�p
	    Hashtable xmlFiles = new Hashtable();
	    xmlFiles.put("JP","resources_jp.xml");

	    JApplication.clientSettings = new ClientSettings(
	        new XMLResourcesFactory(xmlFiles,true),
	        JApplication.domains,
	        auth
 	    );

// combobox
//	    Domain orderStateDomain = new Domain("ORDERSTATE");
//	    orderStateDomain.addDomainPair("O","opened");
//	    orderStateDomain.addDomainPair("S","suspended");
//	    orderStateDomain.addDomainPair("D","delivered");
//	    orderStateDomain.addDomainPair("ABC","closed");
//	    domains.put(
//	      orderStateDomain.getDomainId(),
//	      orderStateDomain
//	    );

	    // add s.inoue 2012/10/11
	    // �K�{�}�[�N
		ClientSettings.VIEW_MANDATORY_SYMBOL = true;
		// ������
		ClientSettings.FILTER_PANEL_ON_GRID = true;
		// �����b�N(0:closeOnExit,1:useClose,2:pressed,3unPressed)
		ClientSettings.FILTER_PANEL_ON_GRID_POLICY = Consts.FILTER_PANEL_ON_GRID_USE_PADLOCK_UNPRESSED;

		// add s.inoue 2013/10/28

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

        ClientSettings.GRID_SCROLL_BLOCK_INCREMENT = Consts.GRID_SCROLL_BLOCK_INCREMENT_PAGE;

	    // filter�@�\
	    ClientSettings.GRID_PROFILE_MANAGER = new FileGridProfileManager();
	    ClientSettings.LOOKUP_FRAME_CONTENT = LookupController.GRID_AND_FILTER_FRAME;
	    // add s.inoue 2013/10/28
	    ClientSettings.STORE_INTERNAL_FRAME_PROFILE = true;


//	    // add s.inoue 2012/11/14
//	    /** flag used to add a filter panel on top of the exported grid, in order to show filtering conditions;
//	     this pane is visibile only whether there is at least one filtering condition applied; default value: <code>false</code> */
//	    ClientSettings.SHOW_FILTERING_CONDITIONS_IN_EXPORT = true;
//
//	    /** flag used to show the title of the frame that contains the grid component currently exported;
//	     title is showed on top of the exported grid, in order to show filtering conditions; default value: <code>false</code> */
//	    ClientSettings.SHOW_FRAME_TITLE_IN_EXPORT = false;

		// ����
		ClientSettings.getInstance().setLanguage("JP");

		// �f�t�H���g�̐ݒ�
	    // ClientSettings.GRID_CELL_BACKGROUND = new Color(220,220,220);
	    // ClientSettings.GRID_CELL_FOREGROUND = Color.BLACK;
	    // ClientSettings.GRID_EDITABLE_CELL_BACKGROUND = Color.WHITE;
	    // ClientSettings.GRID_NOT_EDITABLE_CELL_BACKGROUND = new Color(220,220,220);
		ClientSettings.GRID_REQUIRED_CELL_BORDER = Color.red;
		ClientSettings.GRID_NO_FOCUS_BORDER = Color.lightGray;
		ClientSettings.SHOW_PAGE_NUMBER_IN_GRID = true;
		ClientSettings.VIEW_BACKGROUND_SEL_COLOR = true;
		ClientSettings.BACKGROUND_SEL_COLOR = new Color(220,220,220);
		ClientSettings.SHOW_FOCUS_BORDER_ON_FORM = false;
		ClientSettings.ASK_BEFORE_CLOSE = true;
		
// eidt s.inoue 2013/03/12
		try{
//		  String osname = System.getProperty("os.name");
//	      if(osname.indexOf("Windows")>=0){
	    	  ClientSettings.LOOK_AND_FEEL_CLASS_NAME =PropertyUtil.getProperty( "setting.lookAndFeel");
	    	  Class.forName(ClientSettings.LOOK_AND_FEEL_CLASS_NAME).getMethod(
			      "setCurrentTheme", new Class[] {Properties.class}).invoke(null,
			      new Object[] {props});
			UIManager.setLookAndFeel(ClientSettings.LOOK_AND_FEEL_CLASS_NAME);
//	      }
		} catch (Exception e) {
			// eidt n.ohkubo 2014/08/22
			//Ver2.1.1�ɍ��킹�邽�߂ɃR�����g�A�E�g
//			logger.error(e.getMessage());
		}
	}
	
	// add s.inoue 2014/04/30
	private static void setScreenColumns(){
		
	    // add s.inoue 2013/11/22
		ArrayList<Hashtable<String, String>> searchColumnItems = null;
		String sql005 ="SELECT SCREEN_CD, NAME,JYUSHIN_SEIRI_NO,NENDO, HIHOKENJYASYO_NO,HIHOKENJYASYO_KIGOU, KENSA_NENGAPI, SEX, BIRTHDAY, "
				+ "KEKA_INPUT_FLG, HKNJANUM, SIHARAI_DAIKOU_BANGO, KANANAME, HANTEI_NENGAPI, TUTI_NENGAPI,JISI_KBN, CHECKBOX_COLUMN, "
				+ "TANKA_GOUKEI, MADO_FUTAN_GOUKEI, SEIKYU_KINGAKU, UPDATE_TIMESTAMP, HENKAN_NITIJI, METABO, HOKENSIDO_LEVEL, KOMENTO, "
				+ "KOUMOKU_CD, KOUMOKU_NAME, KENSA_HOUHOU, HISU_FLG, DS_KAGEN, DS_JYOUGEN, JS_KAGEN, JS_JYOUGEN, TANI, KAGEN, JYOUGEN, KIJYUNTI_HANI, TANKA_KENSIN, BIKOU, "
				// add s.inoue 2014/02/17
				+ "NITIJI_FLG,GETUJI_FLG "
				+ "FROM T_SCREENCOLUMNS "
				+ "WHERE SCREEN_CD ='005' or SCREEN_CD = '004' or SCREEN_CD = '006' or SCREEN_CD = '007' or SCREEN_CD = '102' or SCREEN_CD = '110'";

		try {
			searchColumnItems = JApplication.kikanDatabase.sendExecuteQuery(sql005);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < searchColumnItems.size(); i++) {
			Hashtable<String, String> Items;
			Items = searchColumnItems.get(i);
			// ���ʈꗗ
			if (Items.get("SCREEN_CD").equals("005")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KOMENTO));
			}else if (Items.get("SCREEN_CD").equals("004")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KOMENTO));
			}else if (Items.get("SCREEN_CD").equals("006")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KOMENTO));
				
			}else if (Items.get("SCREEN_CD").equals("007")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KOMENTO));
			// add s.inoue 2013/12/19	
			}else if (Items.get("SCREEN_CD").equals("102")){
				if(Items.get("KOUMOKU_CD").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_CD));
				if(Items.get("KOUMOKU_NAME").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_NAME));
				if(Items.get("KENSA_HOUHOU").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KENSA_HOUHOU));
				if(Items.get("HISU_FLG").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.HISU_FLG));
				if(Items.get("DS_KAGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_KAGEN));
				if(Items.get("DS_JYOUGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_JYOUGEN));
				if(Items.get("JS_KAGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_KAGEN));
				if(Items.get("JS_JYOUGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_JYOUGEN));
				if(Items.get("TANI").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANI));
				if(Items.get("KAGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KAGEN));
				if(Items.get("JYOUGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JYOUGEN));
				if(Items.get("KIJYUNTI_HANI").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KIJYUNTI_HANI));
				if(Items.get("TANKA_KENSIN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANKA_KENSIN));
				if(Items.get("BIKOU").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.BIKOU));
		    // add s.inoue 2014/02/17
			}else if (Items.get("SCREEN_CD").equals("110")){
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HKNJANUM));
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.JYUSHIN_SEIRI_NO));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.BIRTHDAY));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.SEX));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KENSA_NENGAPI));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KANANAME));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KEKA_FLG));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HANTEI_FLG));
				if(Items.get("NITIJI_FLG").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.NITIJI_FLG));
				if(Items.get("GETUJI_FLG").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.GETUJI_FLG));
			}
		}
	}
	
	// add s.inoue 2013/11/14
//	private static boolean getFunctionSetting(){
//
//		ArrayList<Hashtable<String, String>> result = null;
//		boolean retflg = false;
//
//		try{
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT CHECKBOX_COLUMN, NENDO, JYUSHIN_SEIRI_NO, NAME, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO,");
//			sb.append("KENSA_NENGAPI, SEX, BIRTHDAY, KEKA_INPUT_FLG, HKNJANUM, SIHARAI_DAIKOU_BANGO, KANANAME, HANTEI_NENGAPI,");
//			sb.append("TUTI_NENGAPI, TANKA_GOUKEI, MADO_FUTAN_GOUKEI, SEIKYU_KINGAKU, JISI_KBN, HENKAN_NITIJI, METABO,");
//			sb.append("HOKENSIDO_LEVEL, KOMENTO, UPDATE_TIMESTAMP");
//			sb.append(" FROM T_SCREENCOLUMNS ");
//			sb.append(" WHERE SCREEN_CD = ");
//			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_SHOSAI_CODE));
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//			
//			Hashtable<String, String> item = result.get(0);
//			retflg = item.get("CHECKBOX_COLUMN").equals("1")?true:false;
//			
//			if (item.get("FUNCTION_FLG").equals("1")){
//				JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
//			}else{
//				if (!JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO))
//					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
//			}
//			
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//		}
//
//		Hashtable<String, String> item = result.get(0);
//		retflg = item.get("FUNCTION_FLG").equals("1")?true:false;
//
//		return retflg;
//	}
}
