package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TextFieldWithComboBoxDocumentListener;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JYearAge;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;
import jp.or.med.orca.jma_tokutei.common.orca.JOrcaInfoSearchCtl;
import jp.or.med.orca.jma_tokutei.common.qr.JQRReader;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceEditFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameData.T_KOJIN_COLUMN;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectKojinFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectKojinFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceEditFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintNyuryoku;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import org.apache.log4j.Logger;


/**
 * �l�o�^�t���[��
 */
public class JKojinRegisterFrameCtrl extends JKojinRegisterFrame {
	
	private static final long serialVersionUID = 920280230214173874L;	// edit n.ohkubo 2014/10/01�@�ǉ�
	
	private static final String STRING_UNIT_PERCENT = "��";
	private static final String STRING_UNIT_YEN = "�~";
	private static final int COMBOBOX_NUMBER_START_INDEX = 1;
	private static final String TEXT_NO_NAME = "�i���̂��ݒ肳��Ă��܂���j";
	final static int ADD_MODE = 1;
	final static int CHANGE_MODE = 2;
	private int mode = ADD_MODE;

	private static final String COMBOBOX_ITEM_ADD_NEWITEM = "���V�K�ǉ���";
	private JKojinRegisterFrameData validatedData = new JKojinRegisterFrameData();  //  @jve:decl-index=0:
	private boolean isFromKekkaRegster = false;
	private static final String FLAME_TITLE_NAME_MESSAGE = "���������̎�f�҂����݂��܂��B";
	private static final String FLAME_TITLE_JYUSINNO_MESSAGE = "��f�������ԍ�������̎�f�҂����݂��܂��B";

	/** �ی��Ҕԍ��A�x����s�@�֑I�𗓂́A�ԍ��Ɩ��̂̋�؂蕶���� */
	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";
	/** �ی��Ҕԍ��̐��K�\�� */
//	private static final String REGEX_HOKENJA_NUMBER = "\\d{1,8}";	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	/** �x����s�@�֔ԍ��̐��K�\�� */
//	private static final String REGEX_DAIKOU_NUMBER = "\\d{1,8}";	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

//	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private TKojinDao tKojinDao;
	private TNayoseDao tNayoseDao;
	private static Logger logger = Logger.getLogger(JKojinRegisterFrameCtrl.class);
	private String[] itakuKubunStr = { "1:��", "2:�W�c" };

	private boolean keiyakuCode_flg = false;
	private boolean futanCode_flg = false;
	private boolean nyuryokuCode_flg = false;
	// eidt s.inoue 2011/08/01
	private boolean yearOld_flg = false;

	// edit s.inoue 2009/10/04
	private IDialog dateSelectDialog;
	// add s.inoue 2010/01/15
	private static final String TANKA_HANTEI_KIHON = "1";
	private static final String TANKA_HANTEI_DOC = "2";

	// eidt s.inoue 2013/07/01
	private String kensanengapi = "";

	/**
	 * �o�^�{�^�����������ۂ̕K�{���ڂȂǂ�����
	 */
	protected boolean validateAsRegisterPushed(JKojinRegisterFrameData data) {
		if (data.getHokenjyaNumber().equals(""))
		{
			JErrorMessage.show("M4352", null);
			return false;
		}
		if (data.getNameKana().equals("")) {
			JErrorMessage.show("M4355", null);
			return false;
		}
		if (data.getBirthday().equals("")) {
			JErrorMessage.show("M4356", null);
			return false;
		}
		if (data.getSex().equals("")) {
			JErrorMessage.show("M4357", null);
			return false;
		}
		if (data.getZipcode().equals("")) {
			JErrorMessage.show("M4370", null);
			return false;
		}
		if (data.getAddress().equals("")) {
			JErrorMessage.show("M4371", null);
			return false;
		}
		
		// edit n.ohkubo 2014/10/01�@�ǉ��@start�@�ی��ҏ��DB�֓o�^����Ă��邩�m�F����
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			sb.append("HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL, KIGO, HON_GAIKYURATE, HON_NYUKYURATE, KZK_GAIKYURATE, KZK_NYUKYURATE, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, TANKA_NINGENDOC, TANKA_HANTEI, HKNJYA_HISTORY_NO, HKNJYA_LIMITDATE_START, HKNJYA_LIMITDATE_END, YUKOU_FLG FROM T_HOKENJYA ");
			sb.append("WHERE ");
			sb.append("HKNJANUM = ? AND YUKOU_FLG = '1'");
			String[] params = {data.getHokenjyaNumber()};
			ArrayList<Hashtable<String, String>> result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString(), params);
			
			//DB���o�^���̓G���[
			if (result.size() == 0) {
				JErrorMessage.show("M3140", null);
				return false;
			}
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error(sqlEx.getMessage());
		}
		// edit n.ohkubo 2014/10/01�@�ǉ��@end�@�ی��ҏ��DB�֓o�^����Ă��邩�m�F����

		data.setValidationAsDataSet();
		return true;
	}

	/**
	 * �ی��҃����e�i���X��ʂɑJ�ڂ���ۂ̕K�{���ڂȂǂ�����
	 */
	protected boolean validateAsToHokenjaAddFrame(JKojinRegisterFrameData data) {
		if (data.getHokenjyaNumber().equals("")) {
			JErrorMessage.show("M4352", null);
			return false;
		}

		data.setValidationAsDataSet();
		return true;
	}

	/**
	 * ORCA�Ăяo���̍ۂ̕K�{���ڂ̌���
	 */
	public boolean validateAsPushedORCAButton(JKojinRegisterFrameData data) {
		if (validatedData.getORCAID().isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * �x����s�@�փ}�X�^�����e�i���X�֑J�ڂ���ۂ̕K�{���ڂȂǂ�����
	 */
	protected boolean validateAsToJShiharaiMasterMaintenanceEditFrame(
			JKojinRegisterFrameData data) {
		if (data.getDaikouNumber().equals("")) {
			JErrorMessage.show("M4358", null);
			return false;
		}
		data.setValidationAsDataSet();
		return true;
	}

	/**
	 * �t���[���̏���������
	 */
	public JKojinRegisterFrameCtrl() {
		super();

		// �S�̑������S�֘A�R���{�{�b�N�X�̏�����
		for (int i = 0; i < JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS.length; i++) {
			String syubetsuItem = JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS[i][1];

			jComboBox_MadoguchiKihonSyubetu.addItem(syubetsuItem);
			jComboBox_MadoguchiShousaiSyubetu.addItem(syubetsuItem);
			jComboBox_MadoguchiTsuikaSyubetu.addItem(syubetsuItem);
			jComboBox_MadoguchiDockSyubetu.addItem(syubetsuItem);
		}

		validatedData.setMadoguchiDockSyubetu(jComboBox_MadoguchiDockSyubetu.getSelectedIndex());
		validatedData.setMadoguchiKihonSyubetu(jComboBox_MadoguchiKihonSyubetu.getSelectedIndex());
		validatedData.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiShousaiSyubetu.getSelectedIndex());
		validatedData.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu.getSelectedIndex());

		/* �ی��ґI�𗓂�����������B */
		this.initializeHokenjaNumComboBox();
		jComboBox_ItakuKubun.addItem(itakuKubunStr[0]);
		jComboBox_ItakuKubun.addItem(itakuKubunStr[1]);

		/* �x����s�@�֑I�𗓂�����������B */
		this.initializeDaikouNumberComboBox();

		/* �R���|�[�l���g�̔񊈐������s�� */
		this.initializeDisableItem();

		/* �R���|�[�l���g�̔w�i�F������������B */
		this.initializeComponentBgColor();

		// edit s.inoue 2009/12/20
		initilazeFunctionSetting();

// del s.inoue 2011/12/13
//		/* �t�H�[�J�X�̑J�ڏ����w�肷��B */
//		this.initializeFocusTraversal();
//		// add s.inoue 2009/11/09
//		/* F�L�[�p�C�x���g�ݒ� */
//		functionListner();

		/* ���ʂ̑I�����N���A */
		this.groupSex.clearSelection();

		/* ORCA�A�g�̐ݒ�ɉ����āuORCA�A�g�v�{�^���̗L���E������؂�ւ���B */
		if (!JApplication.useORCA) {
			jButton_ORCA.setEnabled(false);
		}

		/* T_KOJIN Dao ���쐬����B */
		try {
			tKojinDao = (TKojinDao) DaoFactory.createDao(
					JApplication.kikanDatabase.getMConnection(), new TKojin());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		/* �ی��Ҕԍ��A�x����s�@�֔ԍ����͗��� DocumentLister ��ǉ�����B */
//		DocumentListener listener = this.getComboBoxDocumentListener(
//				this.jComboBox_HokenjyaNumber);
		TextFieldWithComboBoxDocumentListener hokenjaListener =
			new TextFieldWithComboBoxDocumentListener(
				this.jComboBox_HokenjyaNumber, 0);
		// edit s.inoue 2011/12/13
		this.jTextField_hokenjaNumber.getDocument().addDocumentListener(hokenjaListener);

		TextFieldWithComboBoxDocumentListener daikouListener =
			new TextFieldWithComboBoxDocumentListener(this.jComboBox_DaikouNumber, 1);
		this.jTextField_daikouNumber.getDocument().addDocumentListener(daikouListener);

		/* ����ID���͗��Ƀt�H�[�J�X���ړ�����B */
		this.jTextField_ORCAID.grabFocus();

// del s.inoue 2012/06/27
//		jTextField_Birthday.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				String selectedItem = JValidate.validateSendSeinengapi(jTextField_Birthday.getDateText());
//				if ( selectedItem == null) {
//					jTextField_YearOld.setText("");
//					return;
//				}
//				jTextField_YearOld.setText(String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem)));
//			}
//		});

		// add s.inoue 2012/07/09
		jButton_ORCA.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				logger.info(jButton_ORCA.getText());
				// eidt s.inoue 2012/07/09
			if (KeyEvent.VK_ENTER != e.getKeyCode()) return;
				int mod = e.getModifiersEx();
				  if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
				    jTextField_ORCAID.grabFocus();
				  }else{
					  pushedORCAButton();
				  }
			}
		});
	}

	/* �ʐݒ�p */
	private void initilazeFunctionSetting(){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
			sb.append(" FROM T_SCREENFUNCTION ");
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert("001"));

			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> item = result.get(i);

			String functionCd = item.get("FUNCTION_CD");
			if (JApplication.func_yearOldCode.equals(functionCd)){
				yearOld_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}else if (JApplication.func_keiyakuCode.equals(functionCd)){
				keiyakuCode_flg = item.get("FUNCTION_FLG").equals("1")?true:false;
			}else if (JApplication.func_futanCode.equals(functionCd)){
				futanCode_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}else if (JApplication.func_nyuryokuCode.equals(functionCd)){
					nyuryokuCode_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}
		}

	}

	/**
	 * �R���|�[�l���g�̔񊈐������s��
	 */
	private void initializeDisableItem(){
		// del s.inoue 2009/10/15
//		jRadioButton_Kihon.setEnabled(false);
//		jRadioButton_Doc.setEnabled(false);
		jComboBox_ItakuKubun.setEnabled(false);
		// eidt s.inoue 2011/12/13
//		jTextField_KihonTanka.setEditable(false);
//		jTextField_HinketsuTanka.setEditable(false);
//		jTextField_SindenzuTanka.setEditable(false);
//		jTextField_GanteiTanka.setEditable(false);
//		jTextField_NingenDocTanka.setEditable(false);
		jTextField_KihonTanka.setEnabled(false);
		jTextField_HinketsuTanka.setEnabled(false);
		jTextField_SindenzuTanka.setEnabled(false);
		jTextField_GanteiTanka.setEnabled(false);
		jTextField_NingenDocTanka.setEnabled(false);

//		this.jButton_QR.setEnabled(false);
	}
	/**
	 * �R���|�[�l���g�̔w�i�F��ݒ肷��B
	 */
	private void initializeComponentBgColor() {
// del s.inoue 2012/05/10
//		Color requiedItemColor = ViewSettings.getRequiedItemBgColor();
//		Color importantItemColor = ViewSettings.getImportantItemBgColor();
//		Color disableItemColor = ViewSettings.getDisableItemBgColor();

//		jTextField_JyushinkenID.setBackground(importantItemColor);
//		jTextField_hokenjaNumber.setBackground(requiedItemColor);
//
//		jComboBox_HokenjyaNumber.setBackground(requiedItemColor);
//		jTextField_HihokenjyaCode.setBackground(importantItemColor);
//		jTextField_HihokenjyaNumber.setBackground(importantItemColor);

		// add s.inoue 2009/10/12
//		jRadioButton_Doc.setBackground(disableItemColor);
//		jTextField_NingenDocTanka.setBackground(disableItemColor);
//		jRadioButton_Kihon.setBackground(disableItemColor);
//		jTextField_KihonTanka.setBackground(disableItemColor);
//		jTextField_HinketsuTanka.setBackground(disableItemColor);
//		jTextField_SindenzuTanka.setBackground(disableItemColor);
//		jTextField_GanteiTanka.setBackground(disableItemColor);

//		jTextField_NameKana.setBackground(requiedItemColor);
//		jTextField_Birthday.setBackground(requiedItemColor);
//		jRadioButton_Male.setBackground(requiedItemColor);
//		jPanel1.setBackground(requiedItemColor);
//		jRadioButton_Female.setBackground(requiedItemColor);
//		jTextField_Address.setBackground(requiedItemColor);
//		jTextField_ZipCode.setBackground(requiedItemColor);
//		jTextField_sex.setBackground(requiedItemColor);
//		jLabel_requiredExample.setBackground(requiedItemColor);
//		jLabel_importantExample.setBackground(importantItemColor);
	}

// del s.inoue 2011/12/13
//	/**
//	 * �t�H�[�J�X�̑J�ڏ����w�肷��B
//	 */
//	private void initializeFocusTraversal() {
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_ORCAID);
//
//		this.focusTraversalPolicy.addComponent(jTextField_ORCAID);
//		this.focusTraversalPolicy.addComponent(jTextField_JyushinkenID);
//		this.focusTraversalPolicy.addComponent(jTextField_NameKana);
//		this.focusTraversalPolicy.addComponent(jTextField_IssueDate);
//		this.focusTraversalPolicy.addComponent(jTextField_LimitDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_hokenjaNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_HokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(this.jTextField_daikouNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_DaikouNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_TorimatomeName);
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiKihonSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiKihon);
//		this.focusTraversalPolicy.addComponent(jTextField_KihonJougen);
//
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiShousaiSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiShousai);
//		this.focusTraversalPolicy.addComponent(jTextField_ShousaiJougen);
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiTsuikaSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiTsuika);
//		this.focusTraversalPolicy.addComponent(jTextField_TsuikaJyougen);
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiDockSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiDock);
//		this.focusTraversalPolicy.addComponent(jTextField_DockJougen);
//		this.focusTraversalPolicy.addComponent(jTextField_sonotaHutangaku);
//
//		// add s.inoue 2009/10/11
////		this.focusTraversalPolicy.addComponent(jTextField_TankaHantei);
////		this.focusTraversalPolicy.addComponent(jComboBox_ItakuKubun);
////		this.focusTraversalPolicy.addComponent(jTextField_KihonTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_HinketsuTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_SindenzuTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_GanteiTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_NingenDocTanka);
//
//		this.focusTraversalPolicy.addComponent(jTextField_ZipCode);
//		this.focusTraversalPolicy.addComponent(jTextField_Address);
//		this.focusTraversalPolicy.addComponent(jTextField_HomePhone);
//		this.focusTraversalPolicy.addComponent(jTextField_FAXNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_CellPhone);
//		this.focusTraversalPolicy.addComponent(jTextField_EMail);
//		this.focusTraversalPolicy.addComponent(jTextField_MobileMail);
//
//		this.focusTraversalPolicy.addComponent(jTextField_HihokenjyaCode);
//		this.focusTraversalPolicy.addComponent(jTextField_HihokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_NameKanji);
//		this.focusTraversalPolicy.addComponent(jTextField_NameTsushou);
//		this.focusTraversalPolicy.addComponent(jTextField_Birthday);
//		this.focusTraversalPolicy.addComponent(jTextField_sex);
//
//		this.focusTraversalPolicy.addComponent(jButton_Register);
//		this.focusTraversalPolicy.addComponent(jButton_Print);
//		this.focusTraversalPolicy.addComponent(jButton_Call);
////		this.focusTraversalPolicy.addComponent(jButton_QR);
//		this.focusTraversalPolicy.addComponent(jButton_Clear);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//	}
//
//	// add s.inoue 2009/11/09
//	private void functionListner(){
//
//		// add s.inoue 2009/12/02
//		Component comp =null;
//		HashMap<Integer, Component> compHm = new HashMap<Integer, Component>();
//
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//			// System.out.println(comp.getName());
//
//			if (!compHm.containsValue(comp)){
//				comp.addKeyListener(this);
//				compHm.put(i, comp);
//			}
//		}
//
////		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
////			Component comp = focusTraversalPolicy.getComponent(i);
////			comp.addKeyListener(this);
////		}
//	}
	/**
	 * �ی��Ҕԍ��A���̂̈ꗗ���擾����B
	 */
	private ArrayList<Hashtable<String, String>> getHokenjaNumAndNames() {
		ArrayList<Hashtable<String, String>> result = null;
		// add s.inoue 2010/01/15
		String query = new String(
				"SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA WHERE YUKOU_FLG = '1' ORDER BY HKNJANUM");
		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * �ی��Ҕԍ��R���{�{�b�N�X�̏�����
	 */
	private ArrayList<String> hokenjaNumbers = new ArrayList<String>();

	private void initializeHokenjaNumComboBox() {
		ArrayList<Hashtable<String, String>> result = this
				.getHokenjaNumAndNames();

		jComboBox_HokenjyaNumber.removeAllItems();
		hokenjaNumbers.clear();

		jComboBox_HokenjyaNumber.addItem(COMBOBOX_ITEM_ADD_NEWITEM);
		hokenjaNumbers.add("");

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> resultItem = result.get(i);
			String num = resultItem.get("HKNJANUM").trim();
			String name = resultItem.get("HKNJANAME").trim();

			StringBuffer buffer = new StringBuffer();
			buffer.append(num);
			buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);

			if (name != null && !name.isEmpty()) {
				buffer.append(name);
			} else {
				buffer.append(TEXT_NO_NAME);
			}

			hokenjaNumbers.add(num);
			jComboBox_HokenjyaNumber.addItem(buffer.toString());
		}
	}

	/**
	 * �x����s�@�փR���{�{�b�N�X������������
	 */
	private ArrayList<String> daikouNumbers = new ArrayList<String>();

	private void initializeDaikouNumberComboBox() {
		ArrayList<Hashtable<String, String>> result = getDaikoNumAndNames();

		jComboBox_DaikouNumber.removeAllItems();
		daikouNumbers.clear();

		jComboBox_DaikouNumber.addItem("");
		daikouNumbers.add("");

		jComboBox_DaikouNumber.addItem(COMBOBOX_ITEM_ADD_NEWITEM);
		daikouNumbers.add("");

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> ResultItem = result.get(i);
			String num = ResultItem.get("SHIHARAI_DAIKO_NO").trim();
			String name = ResultItem.get("SHIHARAI_DAIKO_NAME").trim();

			StringBuffer buffer = new StringBuffer();
			buffer.append(num);
			buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);

			if (name != null && !num.isEmpty()) {
				buffer.append(name);
			} else {
				buffer.append(TEXT_NO_NAME);
			}

			daikouNumbers.add(num);
			jComboBox_DaikouNumber.addItem(buffer.toString());
		}
	}

	private ArrayList<Hashtable<String, String>> getDaikoNumAndNames() {
		ArrayList<Hashtable<String, String>> result = null;

		String query = new String(
				"SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");
		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * �t���[���̏���������
	 */
	public JKojinRegisterFrameCtrl(JKenshinKekkaSearchListFrameData data) {
		this();
		isFromKekkaRegster = true;

		String uketukeId = data.getUKETUKE_ID();
		// eidt s.inoue 2013/07/01
		kensanengapi = data.getKENSA_NENGAPI();

		setKojinDataFromDB(uketukeId,kensanengapi);
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		RETURN_VALUE Ret = JErrorMessage.show("M4397", this);
		if (Ret == RETURN_VALUE.YES) {
			if (register()) {
				dispose();
			}
		}
	}

	/**
	 * �L�����Z���{�^��
	 */
	public void pushedCancelButton() {
		RETURN_VALUE Ret = JErrorMessage.show("M4396", this);
		if (Ret == RETURN_VALUE.YES) {
			dispose();
		}
	}

	/**
	 * �o�^�������s��
	 *
	 * Modified 2008/04/20 �ጎ
	 *   �o�^���������\�b�h�������B
	 */
	public void pushedRegisterButton(ActionEvent e) {
		this.register();

	}

	/**
	 * �o�^�������s�Ȃ��B
	 */
	private boolean register() {
		boolean success = false;

		/* ���͒l�����؂���B */
		if (! validateInputData()) {
			return false;
		}

		if (! validateAsRegisterPushed(validatedData)) {
			return false;
		}

		if (validatedData.isValidationAsDataSet()) {

			// add s.inoue 2011/07/21
			try {
				JApplication.kikanDatabase.Transaction();
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
			}

			// eidt s.inoue 2011/07/19
			// �o�^�O����
			if (!beforeRegister())return false;

			if(nyuryokuCode_flg){
				pushedPrintButton(null);
			}

			String query = this.createRegisterSql();
			if (query != null) {
				// System.out.println("@@ " + query);

				try {
					JApplication.kikanDatabase.sendNoResultQuery(query);
					this.pushedClearButton();

					success = true;

				} catch (SQLException e) {
					logger.error(e.getMessage());

					try {
						JApplication.kikanDatabase.rollback();
					} catch (SQLException g) {
						logger.error(g.getMessage());
					}
				}
			}

			// add s.inoue 2011/07/21
			try {
				JApplication.kikanDatabase.Commit();
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
			}

		}
		return success;
	}

	// eidt s.inoue 2011/07/19
	/**
	 * ��f�����o�^�O���������{
	 */
	private boolean beforeRegister(){

		boolean registered = true;

		try {
			// edit s.inoue 2009/09/30
			if (mode == ADD_MODE ||
					((mode == CHANGE_MODE) &&
				    (!validatedData.getJyushinkenID().equals(validatedData.getpreJyushinkenID())))){
						Long resultCnt = tKojinDao.selectByCountUniqueKey(validatedData.getJyushinkenID());
						if (resultCnt > 0) {
							JErrorMessage.show("M4348", this);
						}
			}
		}catch (Exception ex){
			registered = false;
			JErrorMessage.show("M4349", this);
			logger.warn(ex.getMessage());
		}

		if (mode == ADD_MODE) {

			try {

				long uketukeId = -1L;

				uketukeId = tKojinDao.selectNewUketukeId(1);

				// ��tID�̕ۑ�
				if (!validatedData.getUketukeID().equals("")){
					validatedData.setpreUketukeID(validatedData.getUketukeID());

					registerNayose(uketukeId);
				}

				// �V�K�o�^�p�Ɏ�tID�ɃZ�b�g����
				validatedData.setUketukeID(new Long(uketukeId).toString());

			} catch (Exception e) {
				registered = false;
				JErrorMessage.show("M4349", this);
				logger.error(e.getMessage());
			}
		}
		return registered;
	}

//	/**
//	 * �����e�[�u���֓o�^���������{����B
//	 */
//	private void registerKojinHistory(Long newUketukeId){
//		// �����e�[�u���o�^����(���f�[�^�́A�l�e�[�u�����擾)
//		historyQuery = new StringBuilder();
//		historyQuery.append("INSERT INTO T_KOJIN_HISTORY ( NAYOSE_NO, PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU,");
//		historyQuery.append("HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX,HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, ");
//		historyQuery.append("FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON,");
//		historyQuery.append("MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC,");
//		historyQuery.append("NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC)");
//		historyQuery.append(" SELECT ");
//		historyQuery.append(JQueryConvert.queryConvert(String.valueOf(nayoseNo)) + " NAYOSE_NO,");
//		historyQuery.append(" PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME,");
//		historyQuery.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME,");
//		historyQuery.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU,");
//		historyQuery.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");
//		historyQuery.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
//		historyQuery.append(JQueryConvert.queryConvert(validatedData.getUketukeID()));
//
//		try {
//			JApplication.kikanDatabase.sendNoResultQuery(historyQuery.toString());
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//
//			try {
//				JApplication.kikanDatabase.rollback();
//			} catch (SQLException g) {
//				logger.error(g.getMessage());
//			}
//		}
//	}

	/**
	 * ���񂹃e�[�u���֓o�^���������{����B
	 */
	private void registerNayose(Long newUketukeId){

		StringBuilder nayoseQuery = null;
		StringBuilder newNayoseQuery = null;

		/* INSERT ���𐶐�����B */
		if (mode == ADD_MODE) {

			// ����NO�̔�
			long nayoseNo = -1L;
			long retTNayoseNo = 0L;

			// ���������擾
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			try {
				/* T_NAYOSE Dao ���쐬����B */
				tNayoseDao = (TNayoseDao) DaoFactory.createDao(
						JApplication.kikanDatabase.getMConnection(), new TNayose());

				retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedData.getUketukeID()));
				// ���ɗ���������ꍇ�A���̖���No���g�p����
				if (retTNayoseNo > 0) {
					nayoseNo = retTNayoseNo;
				}else{
					nayoseNo = tNayoseDao.selectNewNayoseNo();

					// ���񂹃e�[�u���o�^����
					// ��tID�̕R�t������:1�A2���Z�b�g
					// 1.��f�����Ŏ������ȂŕR�t������tID��o�^
					// 2.��f���o�^��ɐV������t�ԍ���o�^

					// 1.����:�������Ȃ��ꍇ
					nayoseQuery = new StringBuilder();
					nayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
					nayoseQuery.append("VALUES (");
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketukeID()));
					nayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
					nayoseQuery.append(") ");

					try {
						JApplication.kikanDatabase.sendNoResultQuery(nayoseQuery.toString());
					} catch (SQLException e) {
						logger.error(e.getMessage());

						try {
							// eidt s.inoue 2013/02/20
							// JApplication.kikanDatabase.rollback();
							JApplication.kikanDatabase.getMConnection().rollback();
						} catch (SQLException g) {
							logger.error(g.getMessage());
						}
					}

				}

				// 2.����:�̔Ԃ����ǉ��p���R�[�h���ʏ���
				newNayoseQuery = new StringBuilder();
				newNayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
				newNayoseQuery.append("VALUES (");
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(newUketukeId.toString()));
				newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
				newNayoseQuery.append(") ");

				try {
					JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
				} catch (SQLException e) {
					logger.error(e.getMessage());
					try {
						// eidt s.inoue 2013/02/20
						// JApplication.kikanDatabase.rollback();
						JApplication.kikanDatabase.getMConnection().rollback();
						logger.warn(e.getMessage());
					} catch (SQLException g) {
						logger.error(g.getMessage());
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}
	}

	/**
	 * ��f�����o�^�p SQL ���쐬����B
	 */
	private String createRegisterSql() {

		StringBuffer query = null;
		/* INSERT ���𐶐�����B */
		if (mode == ADD_MODE) {

			query = new StringBuffer();
			query.append(" INSERT INTO T_KOJIN (");

			// edit s.inoue 20110328
			// T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();
			T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();

			int length = columns.length;

			for (int i = 0; i < length; i++) {
				String columnName = JKojinRegisterFrameData.getColumnName(columns[i]);
				query.append(columnName);

				if (i != length - 1) {
					query.append(",");
				}
			}

			query.append(" )");
			query.append(" VALUES ( ");

			String value = null;
			for (int i = 0; i < length; i++) {
				value = validatedData.get(columns[i]);

				if (i != length - 1) {
					query.append(getAppendCommaString(value));
				}
				else {
					query.append(JQueryConvert.queryConvert(value));
				}
			}

			query.append(" ) ");

		} else if (mode == CHANGE_MODE) {

			/* UPDATE���𐶐�����B */
			StringBuffer buffer = new StringBuffer();

			buffer.append(" WHERE ");
			buffer.append(" UKETUKE_ID = ");
			// edit ver2 s.inoue 2009/05/29
			//buffer.append(JQueryConvert.queryConvert(validatedData.getpreUketukeID()));
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukeID()));

			String condition = buffer.toString();

			query = new StringBuffer();
			query.append("UPDATE T_KOJIN SET ");

			T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();
			int length = columns.length;

			String value = null;
			for (int i = 0; i < length; i++) {

				String columnName = JKojinRegisterFrameData.getColumnName(columns[i]);
				query.append(columnName);
				query.append(" = ");

				value = validatedData.get(columns[i]);
				if (i != length - 1) {
					query.append(getAppendCommaString(value));
				}
				else {
					query.append(JQueryConvert.queryConvert(value));
				}
			}

			query.append(condition);
		}

		return query.toString();
	}

	private String getAppendCommaString(String text) {
		return JQueryConvert.queryConvertAppendComma(text);
	}

	/**
	 * ���̓f�[�^�����؂���B
	 *
	 * Modified 2008/06/02 �ጎ
	 * �R���{�{�b�N�X�A���W�I�{�^���̕�����ɂ�錟�؂�p�~����B
	 */
	private boolean validateInputData() {

		String hokenjaNumber = this.jTextField_hokenjaNumber.getText();
		String daikouNumber = this.jTextField_daikouNumber.getText();

		String address = jTextField_Address.getText();
		if (!address.equals(""))
			address = JValidate.encodeUNICODEtoConvert(address);
		boolean isValid =
				/* �Z�� */
				// edit s.inoue 2009/10/21
				// validatedData.setAddress(jTextField_Address.getText())
				validatedData.setAddress(address,false)

				/* ���N���� */
				// eidt s.inoue 2012/06/27
				&& validatedData.setBirthday(jTextField_Birthday.getText(),false)
				// && validatedData.setBirthday(jTextField_Birthday.getDateText(),false)

				/* �g�ѓd�b�ԍ� */
				&& validatedData.setCellPhone(jTextField_CellPhone.getText())
				/* �x����s�@�֔ԍ� */
				&& validatedData.setDaikouNumber(daikouNumber)
				/* E-Mail */
				&& validatedData.setEMail(jTextField_EMail.getText())
				/* FAX */
				&& validatedData.setFaxNumber(jTextField_FAXNumber.getText())
				/* ��ی��ҏؓ��L�� */
				&& validatedData.setHihokenjyaCode(jTextField_HihokenjyaCode
						.getText(),false)
				/* ��ی��ҏؓ��ԍ� */
				&& validatedData.setHihokenjyaNumber(jTextField_HihokenjyaNumber
								.getText(),false)
				/* �ی��Ҕԍ� */
				&& validatedData.setHokenjyaNumber(hokenjaNumber,false)
				/* �d�b�ԍ� */
				&& validatedData.setHomePhone(jTextField_HomePhone.getText())
				/* ��t�� */
				&& validatedData.setIssueDate(jTextField_IssueDate.getDateText())
				/* ��f�������ԍ� */
				&& validatedData.setJyushinkenID(jTextField_JyushinkenID.getText(),false)
				/* �L������ */
				&& validatedData.setLimitDate(jTextField_LimitDate.getDateText(),false)
				/* �g�ѓd�b�ԍ� */
				&& validatedData.setMobileMail(jTextField_MobileMail.getText())
				/* �����i�J�i�j */
				&& validatedData.setNameKana(jTextField_NameKana.getText(),false)
				/* ���� */
				&& validatedData.setNameKanji(jTextField_NameKanji.getText())
				/* �����i�ʏ́j */
				&& validatedData.setNameTsusyou(jTextField_NameTsushou.getText())
				/* ���Ҕԍ� */
				&& validatedData.setORCAID(jTextField_ORCAID.getText())
				/* ���� */
				&& validatedData.setSex(jTextField_sex.getText(),false)
				/* �_����܂Ƃߋ@�֖� */
				&& validatedData.setTorimatomeName(jTextField_TorimatomeName.getText())
				/* �X�֔ԍ� */
				&& validatedData.setZipcode(jTextField_ZipCode.getText(),false)

				/* �������S�i��{�I�Ȍ��f�j */
				&& validatedData.setMadoguchiKihonSyubetu(jComboBox_MadoguchiKihonSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiKihon(jTextField_MadoguchiKihon.getText(),false,false)

				/* �������S�i�ڍׂȌ��f�j */
				&& validatedData.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiShousaiSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiSyousai(jTextField_MadoguchiShousai.getText(),false,false)

				/* �������S�i�ǉ��̌��f�j */
				&& validatedData.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiTsuika(jTextField_MadoguchiTsuika.getText(),false,false)

				/* �������S�i�l�ԃh�b�N�j */
				&& validatedData.setMadoguchiDockSyubetu(jComboBox_MadoguchiDockSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiDock(jTextField_MadoguchiDock.getText(),false,false)

				/* �ی��ҕ��S�z��� */
				&& validatedData.setHokenjyaJougenKihon(jTextField_KihonJougen.getText(),false)
				&& validatedData.setHokenjyaJougenSyousai(jTextField_ShousaiJougen.getText(),false)
				&& validatedData.setHokenjyaJougenTsuika(jTextField_TsuikaJyougen.getText(),false)
				&& validatedData.setHokenjyaJougenDoc(jTextField_DockJougen.getText(),false)

				/* ���̑��̌��f�̕��S�z */
				&& validatedData.setMadoguchiSonota(jTextField_sonotaHutangaku.getText(),false)

				/* ��tID */
				&& validatedData.setUketukeID(jTextField_Uketsukeid.getText())

				/* �N�x */
				&& validatedData.setNendo(jTextField_Nendo.getText());

		return isValid;
	}

	public boolean validateAsCallPushed(JKojinRegisterFrameData data) {
		if (data.getJyushinkenID().equals("")) {
			return false;
		}

		return true;
	}

	public void pushedCallButton(ActionEvent e) {
		// edit ver2 s.inoue 2009/08/18
		// �������ʉ��ׁ̈A�ړ�
		setKojinDataFromSeiriNo();
	}

	/**
	 * ��f�ҏ��(�X�V�������̉�ʐ���)
	 */
	private void presetKojinData(Hashtable<String, String> resultItem, String kensanengapi) {
		// del ver2 s.inoue 2009/05/07 �X�V�����̎�
		// jTextField_JyushinkenID.setEditable(false);
		jTextField_JyushinkenID.setText(resultItem.get("JYUSHIN_SEIRI_NO"));
		mode = CHANGE_MODE;

		setKojinData(resultItem,kensanengapi);
	}

	/**
	 * ��f�ҏ�����ʏ�ɕ\������B
	 */
	private void setKojinData(Hashtable<String, String> resultItem,String kensanengapi) {

		jTextField_Uketsukeid.setText(resultItem.get("UKETUKE_ID"));
		jTextField_Nendo.setText(resultItem.get("NENDO"));
		jTextField_HihokenjyaCode.setText(resultItem.get("HIHOKENJYASYO_KIGOU"));
		jTextField_HihokenjyaNumber.setText(resultItem.get("HIHOKENJYASYO_NO"));
		jTextField_ORCAID.setText(resultItem.get("PTNUM").trim());

		// add s.inoue 2012/03/05
		// jTextField_IssueDate.setText(resultItem.get("KOUHUBI"));
		try {
			Date dt = jTextField_IssueDate.getTextToDate(resultItem.get("KOUHUBI"));
			jTextField_IssueDate.setDate(dt);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

		// add s.inoue 2012/03/05
		// jTextField_LimitDate.setText(resultItem.get("YUKOU_KIGEN"));
		try {
			Date dt = jTextField_LimitDate.getTextToDate(resultItem.get("YUKOU_KIGEN"));
			jTextField_LimitDate.setDate(dt);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}


		String hokenjaNumber = resultItem.get("HKNJANUM");
		if (hokenjaNumber != null) {
			hokenjaNumber = hokenjaNumber.trim();
			int hokenjaNumberIndex = this.hokenjaNumbers.indexOf(hokenjaNumber);

			if (hokenjaNumberIndex >= 0) {
				jComboBox_HokenjyaNumber.setSelectedIndex(hokenjaNumberIndex);
				jTextField_hokenjaNumber.setText(hokenjaNumber);
			}
		}

		String daikouBongo = resultItem.get("SIHARAI_DAIKOU_BANGO");
		if (daikouBongo != null) {
			daikouBongo = daikouBongo.trim();
			int daikouIndex = this.daikouNumbers.indexOf(daikouBongo);
			if (daikouIndex >= 0) {
				this.jComboBox_DaikouNumber.setSelectedIndex(daikouIndex);
				jTextField_daikouNumber.setText(daikouBongo);
			}
		}

		// add s.inoue 2009/10/11
		String itakuKbn = resultItem.get("ITAKU_KBN");
		String tankaKihon = resultItem.get("TANKA_KIHON");
		String tankaHinketu = resultItem.get("TANKA_HINKETU");
		String tankaSindenzu = resultItem.get("TANKA_SINDENZU");
		String tankaGantei = resultItem.get("TANKA_GANTEI");
		String tankaDoc = resultItem.get("TANKA_NINGENDOC");
		String hantei = resultItem.get("TANKA_HANTEI");

		// add s.inoue 2009/10/12
		if (itakuKbn != null) {
			jComboBox_ItakuKubun.setSelectedIndex(Integer.valueOf(itakuKbn) - 1);
		}

		// add s.inoue 2009/10/12
		if (hantei != null) {
			if (hantei.equals(TANKA_HANTEI_DOC)) {
// del s.inoue 2009/10/15
//				jRadioButton_Doc.setSelected(true);
				jTextField_NingenDocTanka.setText(tankaDoc);
			}else{
// del s.inoue 2009/10/15
//				jRadioButton_Kihon.setSelected(true);
				jTextField_KihonTanka.setText(tankaKihon);
				jTextField_HinketsuTanka.setText(tankaHinketu);
				jTextField_SindenzuTanka.setText(tankaSindenzu);
				jTextField_GanteiTanka.setText(tankaGantei);
			}
		}

		jTextField_TorimatomeName.setText(resultItem.get("KEIYAKU_TORIMATOME"));
		jTextField_NameKana.setText(resultItem.get("KANANAME"));
		jTextField_NameKanji.setText(resultItem.get("NAME"));
		jTextField_NameTsushou.setText(resultItem.get("NICKNAME"));

		// eidt s.inoue 2012/06/27
		jTextField_Birthday.setText(resultItem.get("BIRTHDAY"));
//		try {
//			Date dt = jTextField_Birthday.getTextToDate(resultItem.get("BIRTHDAY"));
//			jTextField_Birthday.setDate(dt);
//		} catch (ParseException e) {
//			logger.error(e.getMessage());
//		}

		// eidt s.inoue 2012/03/06
		// jTextField_ZipCode.setText(resultItem.get("HOME_POST"));
		jTextField_ZipCode.setPostCodeFormatText(resultItem.get("HOME_POST"));

		jTextField_Address.setText(resultItem.get("HOME_ADRS"));
		jTextField_HomePhone.setText(resultItem.get("HOME_TEL1"));
		jTextField_CellPhone.setText(resultItem.get("KEITAI_TEL"));
		jTextField_FAXNumber.setText(resultItem.get("FAX"));

		// eidt s.inoue 2012/06/27
		String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getText());
		// String selectedItem = JValidate.validateSendSeinengapi(resultItem.get("BIRTHDAY"));

		if ( selectedItem == null) {
			jTextField_YearOld.setText("");
		}
		// move s.inoue 2011/08/03
//		if (yearOld_flg){
//			selectedItem = String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem));
//		}else{
//			selectedItem = JYearAge.getAge(selectedItem);
//		}
		int age =0;
		if (yearOld_flg){
			if (!kensanengapi.equals("")){
				age = FiscalYearUtil.getFiscalYear(selectedItem,kensanengapi);
			}else{
				age = FiscalYearUtil.getFiscalYear(selectedItem);
			}
		}else{
			if (!kensanengapi.equals("")){
				age = Integer.parseInt(JYearAge.getAge(selectedItem,kensanengapi));
			}else{
				age = Integer.parseInt(JYearAge.getAge(selectedItem));
			}
		}
		System.out.println("age:" + age);

		// eidt s.inoue 2011/08/03
		jTextField_YearOld.setText(String.valueOf(age));

		this.initMadoFutan(
				jComboBox_MadoguchiKihonSyubetu,
				jTextField_MadoguchiKihon,
				resultItem.get("MADO_FUTAN_K_SYUBETU"),
				resultItem.get("MADO_FUTAN_KIHON"));

		this.initMadoFutan(
				jComboBox_MadoguchiShousaiSyubetu,
				jTextField_MadoguchiShousai,
				resultItem.get("MADO_FUTAN_S_SYUBETU"),
				resultItem.get("MADO_FUTAN_SYOUSAI"));

		this.initMadoFutan(
				jComboBox_MadoguchiTsuikaSyubetu,
				jTextField_MadoguchiTsuika,
				resultItem.get("MADO_FUTAN_T_SYUBETU"),
				resultItem.get("MADO_FUTAN_TSUIKA"));

		this.initMadoFutan(
				jComboBox_MadoguchiDockSyubetu,
				jTextField_MadoguchiDock,
				resultItem.get("MADO_FUTAN_D_SYUBETU"),
				resultItem.get("MADO_FUTAN_DOC"));

		this.jTextField_sonotaHutangaku.setText(resultItem.get("MADO_FUTAN_SONOTA"));

		jTextField_EMail.setText(resultItem.get("EMAIL"));
		jTextField_MobileMail.setText(resultItem.get("KEITAI_EMAIL"));

		if (resultItem.get("SEX").equals("1")) {
			jRadioButton_Male.setSelected(true);
		} else {
			jRadioButton_Female.setSelected(true);
		}

		String hokenjyaFutanKihon = resultItem.get("HOKENJYA_FUTAN_KIHON");
		String hokenjyaFutanSyousai = resultItem.get("HOKENJYA_FUTAN_SYOUSAI");
		String hokenjyaFutanTsuika = resultItem.get("HOKENJYA_FUTAN_TSUIKA");
		String hokenjyaFutanDoc = resultItem.get("HOKENJYA_FUTAN_DOC");

		if (hokenjyaFutanKihon != null && ! hokenjyaFutanKihon.isEmpty()) {
			hokenjyaFutanKihon = hokenjyaFutanKihon.replaceAll("^0+", "");
			jTextField_KihonJougen.setText(hokenjyaFutanKihon);
		}
		if (hokenjyaFutanSyousai != null && ! hokenjyaFutanSyousai.isEmpty()) {
			hokenjyaFutanSyousai = hokenjyaFutanSyousai.replaceAll("^0+", "");
			jTextField_ShousaiJougen.setText(hokenjyaFutanSyousai);
		}
		if (hokenjyaFutanTsuika != null && ! hokenjyaFutanTsuika.isEmpty()) {
			hokenjyaFutanTsuika = hokenjyaFutanTsuika.replaceAll("^0+", "");
			jTextField_TsuikaJyougen.setText(hokenjyaFutanTsuika);
		}
		if (hokenjyaFutanDoc != null && ! hokenjyaFutanDoc.isEmpty()) {
			hokenjyaFutanDoc = hokenjyaFutanDoc.replaceAll("^0+", "");
			jTextField_DockJougen.setText(hokenjyaFutanDoc);
		}

		// edit s.inoue 2009/09/30
		validatedData.setpreJyushinkenID(resultItem.get("JYUSHIN_SEIRI_NO"),false);
	}

	private void initMadoFutan(
			ExtendedComboBox comboBox,
			ExtendedOpenTextControl textField,
			String madoFutanSyubetsuValue,
			String madoFutanValueValue) {

		int index = 0;
		if (! madoFutanSyubetsuValue.equals("")) {
			index = Integer.valueOf(madoFutanSyubetsuValue);

			comboBox.setSelectedIndex(index);
			if (index == 3) {
				String percentage = JValidate.to3DigitCode(madoFutanValueValue);

				textField.setText(percentage.replaceAll("^0+", ""));
			} else {
				textField.setText(madoFutanValueValue.replaceAll("^0+", ""));
			}
		}
	}

	/**
	 * �f�[�^�x�[�X����ǂݍ��񂾎�f�ҏ�����ʏ�ɕ\������B
	 *
	 * @param uketukeID
	 *            ��tID
	 */
	public void setKojinDataFromDB(String uketukeId,String kensanengapi) {

		if (!validatedData.setJyushinkenID(jTextField_JyushinkenID.getText(),false))
			return;

		ArrayList<Hashtable<String, String>> result = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT ");
		sb.append("KOJIN.PTNUM, KOJIN.JYUSHIN_SEIRI_NO, KOJIN.YUKOU_KIGEN, KOJIN.HKNJANUM, KOJIN.HIHOKENJYASYO_KIGOU, KOJIN.HIHOKENJYASYO_NO,");
		sb.append("KOJIN.NAME, KOJIN.KANANAME, KOJIN.NICKNAME, KOJIN.BIRTHDAY, KOJIN.SEX, KOJIN.HOME_POST, KOJIN.HOME_ADRS, KOJIN.HOME_BANTI,");
		sb.append("KOJIN.HOME_TEL1, KOJIN.KEITAI_TEL, KOJIN.FAX, KOJIN.EMAIL, KOJIN.KEITAI_EMAIL, KOJIN.KEIYAKU_TORIMATOME, KOJIN.KOUHUBI,");
		sb.append("KOJIN.SIHARAI_DAIKOU_BANGO, KOJIN.MADO_FUTAN_K_SYUBETU, KOJIN.MADO_FUTAN_KIHON, KOJIN.MADO_FUTAN_S_SYUBETU,");
		sb.append("KOJIN.MADO_FUTAN_SYOUSAI, KOJIN.MADO_FUTAN_T_SYUBETU, KOJIN.MADO_FUTAN_TSUIKA, KOJIN.MADO_FUTAN_D_SYUBETU,");
		sb.append("KOJIN.MADO_FUTAN_DOC, KOJIN.NENDO, KOJIN.UKETUKE_ID, KOJIN.MADO_FUTAN_SONOTA, KOJIN.HOKENJYA_FUTAN_KIHON,");
		sb.append("KOJIN.HOKENJYA_FUTAN_SYOUSAI, KOJIN.HOKENJYA_FUTAN_TSUIKA, KOJIN.HOKENJYA_FUTAN_DOC,");

		sb.append("HOKENJYA.ITAKU_KBN, HOKENJYA.TANKA_KIHON, HOKENJYA.HINKETU_CD, HOKENJYA.TANKA_HINKETU, HOKENJYA.SINDENZU_CD,");
		sb.append("HOKENJYA.TANKA_SINDENZU, HOKENJYA.GANTEI_CD, HOKENJYA.TANKA_GANTEI, HOKENJYA.TANKA_NINGENDOC, HOKENJYA.TANKA_HANTEI");
		sb.append(" FROM T_KOJIN AS KOJIN,T_HOKENJYA HOKENJYA ");
		sb.append(" WHERE KOJIN.HKNJANUM = HOKENJYA.HKNJANUM ");
		// add s.inoue 2010/01/15
		sb.append(" AND HOKENJYA.YUKOU_FLG = '1' ");
		sb.append(" AND UKETUKE_ID = ");
		sb.append(JQueryConvert.queryConvert(uketukeId));

		// edit s.inoue 2009/10/11
//		String query = "SELECT * FROM T_KOJIN WHERE UKETUKE_ID = " +
//			JQueryConvert.queryConvert(uketukeId);

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		if (result.isEmpty()) {
			JErrorMessage.show("M4383", this);
		} else {
			/* ��f�ҏ�����ʏ�ɕ\������B */
			this.presetKojinData(result.get(0),kensanengapi);
		}
	}

	/**
	 * �f�[�^�x�[�X���玁���J�i���L�[��������
	 * ���ʂ������̏ꍇ�˃��X�g�����ĕ\��
	 * ���ʂ��P���̏ꍇ�ˎ�f�����ɑΏۂ̃f�[�^���Z�b�g����
	 * @param kanaName �J�i����
	 */
	public void setKojinDataFromShimeiKana(String kanaName) {

		if (!validatedData.setNameKana(kanaName,false))
			return;
//		// ��1���̂� ���S��v
//		// �������� �B������
//		ArrayList<Hashtable<String, String>> resultKanaName = null;
//
//		// edit ver2 s.inoue 2009/08/21
//		String Query = "SELECT PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, " +
//			"HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI," +
//			" HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO," +
//			" MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU," +
//			" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA," +
//			" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC" +
//			" FROM T_KOJIN WHERE KANANAME CONTAINING " + JQueryConvert.queryConvert(kanaName);
//
//		try {
//			resultKanaName = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//		// �������ʉ��ׁ̈A�ړ�
//		kojinDataList(resultKanaName,FLAME_TITLE_NAME_MESSAGE);

		// edit s.inoue 2009/09/29
		// �����擾:�B��������
		StringBuilder kanaQuery = new StringBuilder();
		StringBuilder kanaWhereQuery = new StringBuilder();
		StringBuilder kanaWhereElseQuery = new StringBuilder();

		ArrayList<Hashtable<String, String>> resultKanaName = null;
		ArrayList<Hashtable<String, String>> resultKanaNameElse = null;

		kanaQuery.append("SELECT NAYOSE_NO,PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, ");
		kanaQuery.append("HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI,");
		kanaQuery.append(" HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO,");
		kanaQuery.append(" MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU,");
		kanaQuery.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, T_KOJIN.UKETUKE_ID, MADO_FUTAN_SONOTA,");
		kanaQuery.append(" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");
		// edit s.inoue 2010/11/25
		kanaQuery.append(" FROM T_KOJIN LEFT JOIN T_NAYOSE ON  ");
		kanaQuery.append(" T_KOJIN.UKETUKE_ID = T_NAYOSE.UKETUKE_ID");
		kanaQuery.append(" WHERE KANANAME ");
		kanaWhereQuery.append(" = ");
		kanaWhereQuery.append(JQueryConvert.queryConvert(kanaName));

		try {
			resultKanaName = JApplication.kikanDatabase.sendExecuteQuery(
				kanaQuery.toString() + kanaWhereQuery.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		if (resultKanaName.size() > 0){
			// ���S��v�ŁA1���ȏ゠��ꍇ
			RETURN_VALUE Ret = JErrorMessage.show("M4399", this);

			if(Ret == RETURN_VALUE.YES){
				kojinDataList(resultKanaName,FLAME_TITLE_NAME_MESSAGE);
			}
		}else{
			// ���S��v�ŁA1���������ꍇ�A�B������
			// edit s.inoue 2009/10/27
			// kanaWhereElseQuery.append(" CONTAINING ");
			kanaWhereElseQuery.append(" STARTING WITH ");
			kanaWhereElseQuery.append("'" + kanaName+ "'");

			try {
				resultKanaNameElse = JApplication.kikanDatabase.sendExecuteQuery(
						kanaQuery.toString() + kanaWhereElseQuery.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

			if (resultKanaNameElse.size() > 0){
				RETURN_VALUE Ret = JErrorMessage.show("M4400", this);
				if(Ret == RETURN_VALUE.YES){
					kojinDataList(resultKanaNameElse,FLAME_TITLE_NAME_MESSAGE);
				}
			}
		}
	}

	/**
	 * �f�[�^�x�[�X�����f�������ԍ����L�[��������
	 * ���ʂ������̏ꍇ�˃��X�g�����ĕ\��
	 * ���ʂ��P���̏ꍇ�ˎ�f�����ɑΏۂ̃f�[�^���Z�b�g����
	 * @param kanaName �J�i����
	 */
	public void setKojinDataFromSeiriNo() {

		if (validatedData.setJyushinkenID(jTextField_JyushinkenID.getText(),false)) {
			if (validateAsCallPushed(validatedData)) {
				ArrayList<Hashtable<String, String>> resultKanaName = null;

				String Query = "SELECT PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, " +
					"HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI," +
					" HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO," +
					" MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU," +
					" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA," +
					" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC"
					+" FROM T_KOJIN WHERE JYUSHIN_SEIRI_NO = " + JQueryConvert.queryConvert(validatedData.getJyushinkenID());

				try {
					resultKanaName = JApplication.kikanDatabase.sendExecuteQuery(Query);
				} catch (SQLException ex) {
					logger.error(ex.getMessage());
				}

				if (resultKanaName.isEmpty()) {
					JErrorMessage.show("M4383", this);
				} else {
					// edit ver2 s.inoue 2009/08/18
					// �������ʉ��ׁ̈A�ړ�
					kojinDataList(resultKanaName,FLAME_TITLE_JYUSINNO_MESSAGE);
					/* ��f�ҏ�����ʏ�ɕ\������B */
					// this.presetKojinData(resultKanaName.get(0));
				}
			}
		}
	}

	// add ver2 s.inoue 2009/08/18
	/**
	 * �l���X�g�\������
	 */
	public void kojinDataList( ArrayList<Hashtable<String, String>> resultKanaName,String title){

		Hashtable<String, String> targetItem = null;

		// DB�擾�f�[�^���烊�X�g�\���p�A�C�e���֊i�[
		Vector<JSelectKojinFrameData> sameKojinData
			= new Vector<JSelectKojinFrameData>();

		for(int j = 0 ; j < resultKanaName.size() ; j++)
		{
			Hashtable<String,String> DatabaseItem = resultKanaName.get(j);

			//���p�J�^�J�i�A�󔒂�����������ԂŔ�r
			// del s.inoue 2009/08/18 �C���ς݂̎c�[ ���O�͂��̂܂ܕ\��
			// String Name = JEraseSpace.EraceSpace(DatabaseItem.get("KANANAME"));
			JSelectKojinFrameData DataSame = new JSelectKojinFrameData();

			DataSame.uketukeNo = DatabaseItem.get("UKETUKE_ID");
			// edit s.inoue 2010/11/25
			DataSame.nayoseNo = DatabaseItem.get("NAYOSE_NO");
			DataSame.jusinSeiNo = DatabaseItem.get("JYUSHIN_SEIRI_NO");
			DataSame.name = DatabaseItem.get("NAME");
			DataSame.kanaShimei = DatabaseItem.get("KANANAME");
			DataSame.seiNenGapi = DatabaseItem.get("BIRTHDAY");
			DataSame.seiBetu = DatabaseItem.get("SEX");

			// edit ver2 s.inoue 2009/08/19
			DataSame.jyusho= DatabaseItem.get("HOME_ADRS");
			DataSame.hihokenshaKigo = DatabaseItem.get("HIHOKENJYASYO_KIGOU");
			DataSame.hihokenshaNo = DatabaseItem.get("HIHOKENJYASYO_NO");

			DataSame.DatabaseItem = DatabaseItem;
			sameKojinData.add(DataSame);
			targetItem = DatabaseItem;
		}

		// edit s.inoue 2009/09/29
		// 2���ȏと1�����܂߂�
		// ���������E���ꐶ�N�����̎�f�҂����������ꍇ�̏���
		if(sameKojinData.size() > 0)
		{
			JSelectKojinFrameCtrl SelectKojinFrame
				= new JSelectKojinFrameCtrl(sameKojinData,this,title);
			JSelectKojinFrameData selectedData = SelectKojinFrame.GetSelectedData();

			if(selectedData == null){
				targetItem=null;
			}else{
				targetItem = selectedData.DatabaseItem;
			}
		}
		// edit ver2 s.inoue 2009/06/19
		if (targetItem != null) {
			/* ��f�ҏ�����ʏ�ɕ\������B */
			this.setKojinData(targetItem,"");
		}
	}

	// add ver2 s.inoue 2009/08/18
	/*
	 * �{�^���A�N�V����
	 */
	public void pushedClearButton() {
		jTextField_Address.setText("");
		// eidt s.inoue 2012/06/27
		jTextField_Birthday.setText("");
		// jTextField_Birthday.setDate(null);

		jTextField_CellPhone.setText("");
		jTextField_EMail.setText("");
		jTextField_FAXNumber.setText("");
		jTextField_HihokenjyaCode.setText("");
		jTextField_HihokenjyaNumber.setText("");

		// add s.inoue 2009/12/20
		// �_����
		if (!keiyakuCode_flg){
			jTextField_hokenjaNumber.setText("");
			jComboBox_HokenjyaNumber.setSelectedIndex(0);
			jTextField_daikouNumber.setText("");
			jComboBox_DaikouNumber.setSelectedItem("");
			jComboBox_DaikouNumber.setSelectedIndex(0);
			jTextField_TorimatomeName.setText("");
		}

		// ���S���
		if (!futanCode_flg){
			jTextField_MadoguchiDock.setText("");
			jTextField_MadoguchiKihon.setText("");
			jTextField_MadoguchiShousai.setText("");
			jTextField_MadoguchiTsuika.setText("");
			this.jComboBox_MadoguchiKihonSyubetu.setSelectedIndex(0);
			this.jComboBox_MadoguchiShousaiSyubetu.setSelectedIndex(0);
			this.jComboBox_MadoguchiTsuikaSyubetu.setSelectedIndex(0);
			this.jComboBox_MadoguchiDockSyubetu.setSelectedIndex(0);
			this.jTextField_KihonSyubetsuNum.setText("");
			this.jTextField_SyousaiSyubetsuNum.setText("");
			this.jTextField_TsuikaSyubetsuNum.setText("");
			this.jTextField_DockSyubetsuNum.setText("");
			this.jTextField_KihonJougen.setText("");
			this.jTextField_ShousaiJougen.setText("");
			this.jTextField_TsuikaJyougen.setText("");
			this.jTextField_DockJougen.setText("");
			this.jTextField_sonotaHutangaku.setText("");
		}

		jTextField_HomePhone.setText("");
		// eidt s.inoue 2012/03/05
		// jTextField_IssueDate.setText("");
		jTextField_IssueDate.setDate(null);
		// jTextField_LimitDate.setText("");
		jTextField_LimitDate.setDate(null);

		jTextField_JyushinkenID.setText("");
		jTextField_MobileMail.setText("");
		jTextField_NameKana.setText("");
		jTextField_NameKanji.setText("");
		jTextField_NameTsushou.setText("");
		jTextField_ORCAID.setText("");
		jTextField_ZipCode.setText("");
		// eidt s.inoue 2011/12/12
		// jTextField_JyushinkenID.setEditable(true);
		jTextField_JyushinkenID.setEnabled(true);

		this.jTextField_sex.setText("");
		this.groupSex.clearSelection();


		this.jTextField_ORCAID.grabFocus();

		// add ver2 s.inoue 2009/08/13
		this.jTextField_Uketsukeid.setText("");
		this.jTextField_ZipCode.setText("");

		// add h.yoshitama 2009/09/30
		this.jTextField_YearOld.setText("");

		// edit s.inoue 2009/10/15
		jComboBox_ItakuKubun.setSelectedIndex(-1);
		jTextField_KihonTanka.setText("");
		jTextField_HinketsuTanka.setText("");
		jTextField_SindenzuTanka.setText("");
		jTextField_GanteiTanka.setText("");
		jTextField_NingenDocTanka.setText("");

		mode = ADD_MODE;
	}
// del s.inoue 2012/02/28 �VAPI
//	/**
//	 * ORCA�{�^��
//	 */
//	public void pushedORCAButton() {
//		/* �����Z�A�g���L���łȂ��ꍇ�́A�������Ȃ��B */
//		if (! JApplication.useORCA) {
//			this.jTextField_JyushinkenID.grabFocus();
//			return;
//		}
//
//		/*
//		 * ���͏������؂���B
//		 */
//		String inputOrcaId = jTextField_ORCAID.getText();
//
//		if (inputOrcaId == null || inputOrcaId.isEmpty()) {
//			this.jTextField_JyushinkenID.grabFocus();
//			return;
//		}
//
//		/* ORCA ID �� 0 ���߂��s�Ȃ��B */
//		if ( JApplication.orcaSetting.isUseOrcaIdDigit() ) {
//			int orcaId = Integer.parseInt(inputOrcaId);
//
//			String digit = JApplication.orcaSetting.getOrcaIdDigit();
//			if (digit != null && ! digit.isEmpty()) {
//
//				String format = "%0" + digit + "d";
//
//				inputOrcaId = String.format(format, orcaId);
//			}
//		}
//
//		boolean validated = validatedData.setORCAID(inputOrcaId);
//		if (validated) {
//			if (validateAsPushedORCAButton(validatedData)) {
//				validated = true;
//			}
//		}
//
//		if (validated) {
//			/* ��ʂ��N���A����B */
//			this.pushedClearButton();
//
//			/* ORCA DB�p�R�l�N�V�������쐬����B */
//			OrcaConnection orcaCon = new OrcaConnection();
//			JConnection con = orcaCon.getOrcaDbConnectionFromConfigFile();
//
//			try {
//				if (con == null) {
//					JErrorMessage.show("M4394", this);
//					return;
//				}
//			}
//			finally {
//				jTextField_ORCAID.setText(inputOrcaId);
//			}
//
//			try{
//				String version = orcaCon.getOrcaVersion(con);
//
//				/* �o�[�W���������؂���B */
//				boolean validVersion = orcaCon.validateOrcaVersion(version);
//
//				/* ���ҏ����擾����B */
//				if (validVersion) {
//					ArrayList<Hashtable<String, String>> result =
//						orcaCon.getOrcaData(validatedData.getORCAID());
//
//					if (result == null) {
//						return;
//					}
//
//					/* �ی��̏I���������ő�̃��R�[�h��I������B */
//					if (result.size() != 0) {
//						Hashtable<String, String> lastHokenOrcaData =
//							new Hashtable<String, String>();
//						String maxTekEdYmd = null;
//
//						for (Iterator<Hashtable<String, String>>
//							iter = result.iterator();
//							iter.hasNext();) {
//
//							Hashtable<String, String> item = iter.next();
//							String itemTekEdYmd = item.get("tekedymd");
//
//							if (maxTekEdYmd == null ||
//									itemTekEdYmd.compareTo(maxTekEdYmd) > 0 ) {
//
//								maxTekEdYmd = itemTekEdYmd;
//								lastHokenOrcaData = item;
//							}
//						}
//
//						this.inputOrcaDataToComponent(lastHokenOrcaData);
//						this.jTextField_JyushinkenID.grabFocus();
//
//					} else {
//						JErrorMessage.show("M4380", this);
//					}
//				}
//				else {
//					JErrorMessage.show("M9629", this);
//				}
//			}finally{
//				try {
//					con.Shutdown();
//
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//					JErrorMessage.show("M4392", this);
//				}
//			}
//		}
//	}

	// add s.inoue 2012/03/01
	/**
	 * ORCA�{�^��
	 */
	public void pushedORCAButton() {
		/* �����Z�A�g���L���łȂ��ꍇ�́A�������Ȃ��B */
		if (! JApplication.useORCA) {
			this.jTextField_JyushinkenID.grabFocus();
			return;
		}

		/*
		 * ���͏������؂���B
		 */
		String inputOrcaId = jTextField_ORCAID.getText();

		if (inputOrcaId == null || inputOrcaId.isEmpty()) {
			this.jTextField_JyushinkenID.grabFocus();
			return;
		}

		/* ORCA ID �� 0 ���߂��s�Ȃ��B */
		if ( JApplication.orcaSetting.isUseOrcaIdDigit() ) {
			int orcaId = Integer.parseInt(inputOrcaId);

			String digit = JApplication.orcaSetting.getOrcaIdDigit();
			if (digit != null && ! digit.isEmpty()) {

				String format = "%0" + digit + "d";

				inputOrcaId = String.format(format, orcaId);
			}
		}

		boolean validated = validatedData.setORCAID(inputOrcaId);
		if (validated) {
			if (validateAsPushedORCAButton(validatedData)) {
				validated = true;
			}
		}

		if (validated) {
			/* ��ʂ��N���A����B */
			this.pushedClearButton();
// eidt s.inoue 2012/03/01 �VAPI�Ή�
////			/* ORCA DB�p�R�l�N�V�������쐬����B */
//			OrcaConnection orcaCon = new OrcaConnection();
//			JConnection con = orcaCon.getOrcaDbConnectionFromConfigFile();
//			try {
//				if (con == null) {
//					JErrorMessage.show("M4394", this);
//					return;
//				}
//			}
//			finally {
//				jTextField_ORCAID.setText(inputOrcaId);
//			}

			String host = JApplication.orcaSetting.getOrcaIpAddress();
			String port = JApplication.orcaSetting.getOrcaPort();
//			String database = JApplication.orcaSetting.getOrcaDatabase();	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
			String user = JApplication.orcaSetting.getNichireseUser();
			String password = JApplication.orcaSetting.getNichiresePassword();

			JOrcaInfoSearchCtl orcaData = new JOrcaInfoSearchCtl();
			try {
				orcaData.setPatientId(inputOrcaId);		  // id
				orcaData.setPort(port);					  // 8000
				orcaData.setHost(host); // trial.orca.med.or.jp
				orcaData.setUser(user);	// trial
				orcaData.setPass(password); // ""
				orcaData.setURL();
			} catch (Exception e) {
				logger.error(e.getMessage());
				JErrorMessage.show("M4392", this);
				return;
			}

			// �擾�����Z�b�g
			inputOrcaDataToComponent(orcaData);
			// eidt s.inoue 2012/03/06
			jTextField_JyushinkenID.grabFocus();

//			try{
//				String version = orcaCon.getOrcaVersion(con);
//
//				/* �o�[�W���������؂���B */
//				boolean validVersion = orcaCon.validateOrcaVersion(version);
//
//				/* ���ҏ����擾����B */
//				if (validVersion) {
//					ArrayList<Hashtable<String, String>> result =
//						orcaCon.getOrcaData(validatedData.getORCAID());
//
//					if (result == null) {
//						return;
//					}
//
//					/* �ی��̏I���������ő�̃��R�[�h��I������B */
//					if (result.size() != 0) {
//						Hashtable<String, String> lastHokenOrcaData =
//							new Hashtable<String, String>();
//						String maxTekEdYmd = null;
//
//						for (Iterator<Hashtable<String, String>>
//							iter = result.iterator();
//							iter.hasNext();) {
//
//							Hashtable<String, String> item = iter.next();
//							String itemTekEdYmd = item.get("tekedymd");
//
//							if (maxTekEdYmd == null ||
//									itemTekEdYmd.compareTo(maxTekEdYmd) > 0 ) {
//
//								maxTekEdYmd = itemTekEdYmd;
//								lastHokenOrcaData = item;
//							}
//						}
//
//						this.inputOrcaDataToComponent(lastHokenOrcaData);
//						this.jTextField_JyushinkenID.grabFocus();
//
//					} else {
//						JErrorMessage.show("M4380", this);
//					}
//				}
//				else {
//					JErrorMessage.show("M9629", this);
//				}
//			}finally{
//				try {
////					con.Shutdown();
//
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//					JErrorMessage.show("M4392", this);
//				}
//			}
		}
	}

// del s.inoue 2012/03/01
//	private void inputOrcaDataToComponent(Hashtable<String, String> orcaData) {
//			/* ����ID */
//			jTextField_ORCAID.setText(orcaData.get("ptnum").trim());
//			/* �����i�J�i�j */
//			jTextField_NameKana.setText(orcaData.get("kananame"));
//			/* �����i�����j */
//			jTextField_NameKanji.setText(orcaData.get("name"));
//			/* �����i�ʏ́j */
//			jTextField_NameTsushou.setText(orcaData.get("nickname"));
//			/* ���N���� */
//			// eidt s.inoue 2011/12/12
//			// jTextField_Birthday.setText(orcaData.get("birthday"));
//			try {
//				Date dt = jTextField_Birthday.getTextToDate(orcaData.get("birthday"));
//				jTextField_Birthday.setDate(dt);
//			} catch (ParseException e) {
//				logger.error(e.getMessage());
//			}
//
//			/* ����X�֔ԍ� */
//			jTextField_ZipCode.setText(orcaData.get("home_post"));
//
//			/* ����Z�� */
//			/* �O��ɃX�y�[�X���܂ޏꍇ�͏��O����B */
//			StringBuffer buffer = new StringBuffer();
//
//			String homeAddress = orcaData.get("home_adrs");
//			String homeBanti = orcaData.get("home_banti");
//			if (homeAddress != null && ! homeAddress.isEmpty()) {
//				buffer.append(homeAddress);
//			}
//			if (homeBanti != null && ! homeBanti.isEmpty()) {
//				buffer.append(homeBanti);
//			}
//			String address = buffer.toString();
//			if (address != null && ! address.isEmpty()) {
//				address = address.trim();
//
//				address = address.replaceAll("^�@+", "");
//				address = address.replaceAll("�@+$", "");
//
//				jTextField_Address.setText(address);
//			}
//
//			/* ����d�b�ԍ� */
//			jTextField_HomePhone.setText(orcaData.get("home_tel1"));
//			/* �g�ѓd�b�ԍ� */
//			jTextField_CellPhone.setText(orcaData.get("keitai_tel"));
//			/* FAX */
//			jTextField_FAXNumber.setText(orcaData.get("fax"));
//			/* E-mail */
//			jTextField_EMail.setText(orcaData.get("email"));
//
//			/* ����n�ԕ��� */
////			validatedData.setChiban(resultItem.get("home_banti"));
//
//			/* ��ی��ҏؓ��L�� */
//			jTextField_HihokenjyaCode.setText(orcaData.get("kigo"));
//
//			/* ��ی��ҏؓ��ԍ� */
//			jTextField_HihokenjyaNumber.setText(orcaData.get("num"));
//
//			/* ���� */
//			if (orcaData.get("sex").equals("1")) {
//				jRadioButton_Male.setSelected(true);
//			} else if (orcaData.get("sex").equals("2")) {
//				jRadioButton_Female.setSelected(true);
//			}
//	}


	// add s.inoue 2012/03/01
	private void inputOrcaDataToComponent(JOrcaInfoSearchCtl orcaData) {

		// ����ID
		jTextField_ORCAID.setText(orcaData.getPatientId().trim());
		// �����i�J�i�j
		jTextField_NameKana.setText(orcaData.getSimeiKana());
		// �����i�����j
		jTextField_NameKanji.setText(orcaData.getSimeiKanji());
		// �����i�ʏ́j���p�~

		// ���N����
		// eidt s.inoue 2012/06/27
		jTextField_Birthday.setText(orcaData.getBirthDay());
//		try {
//			Date dt = jTextField_Birthday.getTextToDate(orcaData.getBirthDay());
//			jTextField_Birthday.setDate(dt);
//		} catch (ParseException e) {
//			logger.error(e.getMessage());
//		}

		// ����X�֔ԍ�
		// eidt s.inoue 2013/03/25
		// jTextField_ZipCode.setText(orcaData.getPostCode());
		jTextField_ZipCode.setPostCodeFormatText(orcaData.getPostCode());

		// ����Z��
		// �O��ɃX�y�[�X���܂ޏꍇ�͏��O����B
		StringBuffer buffer = new StringBuffer();

		// String homeAddress = orcaData.get("home_adrs");
		// String homeBanti = orcaData.get("home_banti");
		String homeAddress = orcaData.getAddress1();
		String homeBanti = orcaData.getAddress2();

		if (homeAddress != null && ! homeAddress.isEmpty()) {
			buffer.append(homeAddress);
		}
		if (homeBanti != null && ! homeBanti.isEmpty()) {
			buffer.append(homeBanti);
		}
		String address = buffer.toString();
		if (address != null && ! address.isEmpty()) {
			address = address.trim();

			address = address.replaceAll("^�@+", "");
			address = address.replaceAll("�@+$", "");

			jTextField_Address.setText(address);
		}

		// ����d�b�ԍ�
		// jTextField_HomePhone.setText(orcaData.get("home_tel1"));
		// eidt s.inoue 2013/03/25
		jTextField_HomePhone.setText(orcaData.getPhoneNumber1().replaceAll("-", ""));

		// �g�ѓd�b�ԍ� �p�~
		// jTextField_CellPhone.setText(orcaData.get("keitai_tel"));
		// FAX �p�~
		// jTextField_FAXNumber.setText(orcaData.get("fax"));
		// E-mail �p�~
		// jTextField_EMail.setText(orcaData.get("email"));
		// ����n�ԕ���
		// validatedData.setChiban(resultItem.get("home_banti"));

		// ��ی��ҏؓ��L��
		jTextField_HihokenjyaCode.setText(orcaData.getPersonSymbol());
		// ��ی��ҏؓ��ԍ�
		jTextField_HihokenjyaNumber.setText(orcaData.getPersonNumber());

		// ����
		if (orcaData.getSex().equals("1")) {
			jRadioButton_Male.setSelected(true);
		} else if (orcaData.getSex().equals("2")) {
			jRadioButton_Female.setSelected(true);
		}
	}

	/**
	 * QR�{�^��
	 */
	public void pushedQRButton(ActionEvent e) {
		new JQRReader();
	}

	/**
	 * ����@�\
	 *
	 * 1�y�[�W ���f���ړ��̓V�[�g�i�������ʁj �K�{�f�[�^�F��f�������ԍ��A�����A��f���i���������j�A�ی��Ҕԍ��A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
	 * import Print.KenshinKoumoku_Kensa class KenshinKoumoku_Kensa
	 *
	 * 2�y�[�W ���f���ړ��̓V�[�g�i��f�j �K�{�f�[�^�F��f�������ԍ��A�����A��f���i���������j�A�ی��Ҕԍ��A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
	 * import Print.KenshinKoumoku_Monshin class KenshinKoumoku_Monshin
	 */
	public void pushedPrintButton(ActionEvent e) {

		// add s.inoue 2009/10/19
		try {
			dateSelectDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
			dateSelectDialog.setShowCancelButton(false);
			// ���f���{�����̓_�C�A���O��\��
			dateSelectDialog.setMessageTitle("���f���{�����͉��");
			dateSelectDialog.setVisible(true);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}

		/*
		 * �l���f�[�^�쐬
		 */
		ProgressWindow progressWindow = new ProgressWindow(this, false, true);
		progressWindow.setGuidanceByKey("tokutei.jushinken.frame.progress.guidance.print1");
		progressWindow.setVisible(true);

		try {
			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();

			// edit s.inoue 2010/04/15
			// ���f���{��
			String selectKenshinDate = dateSelectDialog.getKenshinDate();
			String kenshinDate = dateSelectDialog.getKenshinDate();
			String printKenshinDate = "";
			if (!selectKenshinDate.equals("")){
				printKenshinDate = selectKenshinDate;
			}else{
				printKenshinDate = kenshinDate;
			}
			tmpKojin.put("KENSA_NENGAPI", printKenshinDate);

			// ��f�������ԍ�
			String jyushinkenID = jTextField_JyushinkenID.getText();
			if (jyushinkenID == null) {
				jyushinkenID = "";
			}
			tmpKojin.put("JYUSHIN_SEIRI_NO", jyushinkenID);

			// ��f�Ҏ���
			String nameKana = jTextField_NameKana.getText();
			if (nameKana == null) {
				nameKana = "";
			}
			tmpKojin.put("KANANAME", nameKana);

			// �ی��Ҕԍ�
			int hokenjaIndex = this.jComboBox_HokenjyaNumber.getSelectedIndex();
			String hokenjyaNumber = this.hokenjaNumbers.get(hokenjaIndex);
			if (hokenjyaNumber == null) {
				hokenjyaNumber = "";
			}
			tmpKojin.put("HKNJANUM", hokenjyaNumber);

			// ��ی��ҏؓ��L��
			String hihokenjyaCode = jTextField_HihokenjyaCode.getText();
			if (hihokenjyaCode == null) {
				hihokenjyaCode = "";
			}
			tmpKojin.put("HIHOKENJYASYO_KIGOU", hihokenjyaCode);

			// ��ی��ҏؓ��ԍ�
			String hihokenjyaNumber = jTextField_HihokenjyaNumber.getText();
			if (hihokenjyaNumber == null) {
				hihokenjyaNumber = "";
			}
			tmpKojin.put("HIHOKENJYASYO_NO", hihokenjyaNumber);
			// ���O����
			String name = jTextField_NameKanji.getText();
			if (name == null) {
				name = "";
			}
			tmpKojin.put("NAME", name);
			// ���N����
			// eidt s.inoue 2012/06/27
			String Birthday = jTextField_Birthday.getText();
			// String Birthday = jTextField_Birthday.getDateText();
			if (Birthday == null) {
				Birthday = "";
			}
			tmpKojin.put("Birthday", Birthday);
			// ����
			String SEX = jTextField_sex.getText();
			if (SEX == null) {
				SEX = "";
			}
			tmpKojin.put("SEX", SEX);
			// ��tID
			String uketukeID = jTextField_Uketsukeid.getText();
			if (uketukeID == null) {
				uketukeID = "";
			}
			tmpKojin.put("UKETUKE_ID", uketukeID);

			// edit s.inoue 2009/10/19
//			// �N�x
//			String Nendo = jTextField_Nendo.getText();
//			if (Nendo == null) {
//				Nendo = "";
//			}
//			tmpKojin.put("KENSA_NENGAPI", Nendo);

			Kojin KojinData = new Kojin();

			if (isFromKekkaRegster) {
				KojinData.setPrintKojinDataSQL(tmpKojin);
			// edit s.inoue 2009/10/31
			} else {
				if (!KojinData.setPrintKojinData(tmpKojin,this.mode)) {
					// �f�[�^�ڑ����s
					System.out.println("����Ɏ��s���܂����B�i�f�[�^�ڑ����s�@�l�j");
				}
			}

			/*
			 * ����f�[�^���� �l�f�[�^���iy�[����
			 */
			Hashtable<String, Object> PrintData = new Hashtable<String, Object>();
			PrintData.put("Kojin", KojinData);

			/*
			 * ��� 1�y�[�W�ڂ��������ƁA�����I�ɍŏI�y�[�W�܂ŏo�͂����
			 */
			// KenshinKoumoku_Kensa Kensa = new KenshinKoumoku_Kensa();
			PrintNyuryoku Kensa = new PrintNyuryoku();
			Kensa.setQueryResult(PrintData);
			progressWindow.setVisible(false);

			try {
				Kensa.beginPrint();

			} catch (PrinterException ex) {
				logger.error(ex.getMessage());
				JErrorMessage.show("M4393", this);
			}
		} finally {
			progressWindow.setVisible(false);
		}
	}

	/**
	 * �ی��Ҕԍ����͌�Enter�������ꂽ�ꍇ �ی��Җ��̂Ɠd�b�ԍ��̕\��
	 */
	protected void enterKeyPushedHokenjyaNumber(boolean isSkipCreateDialog) {

		/* �������͂��Ă��Ȃ��ꍇ�́A���̃R���|�[�l���g�Ƀt�H�[�J�X���ړ�����B */
		String inputNumber = this.jTextField_hokenjaNumber.getText();
		if (inputNumber.isEmpty()) {
			this.jTextField_hokenjaNumber.transferFocus();
			return;
		}

		int itemIndex = 2;
		for (itemIndex = 2; itemIndex < hokenjaNumbers.size(); itemIndex++) {
			String number = this.hokenjaNumbers.get(itemIndex);
			if (number.equals(inputNumber)) {
				break;
			}
		}
	}

	/**
	 * �x����s�@�֔ԍ����͌�Enter�������ꂽ�ꍇ �x����s�@�֖��̂Ɠd�b�ԍ��̕\��
	 */
	protected void enterKeyPushedDaikouNumber(boolean isSkipCreateDialog) {
		/* �������͂��Ă��Ȃ��ꍇ�́A���̃R���|�[�l���g�Ƀt�H�[�J�X���ړ�����B */
		String inputNumber = this.jTextField_daikouNumber.getText();
		if (inputNumber.isEmpty()) {
			this.jTextField_daikouNumber.transferFocus();
			return;
		}

//		boolean existsNumber = false;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

		int itemIndex = 2;
		for (itemIndex = 2; itemIndex < daikouNumbers.size(); itemIndex++) {
			String number = this.daikouNumbers.get(itemIndex);
			if (number.equals(inputNumber)) {
//				existsNumber = true;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
				break;
			}
		}
	}

	/**
	 * �ی��ҏ��̐V�K�ǉ��_�C�A���O��\������
	 */
	private void showAddHokenjaNumberDialog(String inputNumber) {
//		JHokenjyaMasterMaintenanceEditFrameCtrl ctrl = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
		if (inputNumber == null || inputNumber.isEmpty()) {
			// eidt s.inoue 2012/12/21
			// ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl();
			JScene.CreateDialog(
			this,
			new JHokenjyaMasterMaintenanceEditFrameCtrl(),
			new HokenjyaWindowEvent()
			);
		}
		else {
			validatedData.setHokenjyaNumber(inputNumber,false);
			// eidt s.inoue 2012/12/21
			// ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl(validatedData);
			JScene.CreateDialog(
					this,
					new JHokenjyaMasterMaintenanceEditFrameCtrl(validatedData),
					new HokenjyaWindowEvent()
			);
		}
		// move s.inoue 2012/12/21
//		JScene.CreateDialog(
//				this,
//				ctrl,
//				new HokenjyaWindowEvent()
//			);
	}

	/**
	 * �x����s�@�֏��̐V�K�ǉ��_�C�A���O��\������B
	 */
	private void showAddDaikouNumberDialog(String inputNumber) {

		JShiharaiMasterMaintenanceEditFrameCtrl ctrl = null;

		if (inputNumber == null || inputNumber.isEmpty()) {
			ctrl = new JShiharaiMasterMaintenanceEditFrameCtrl();
		}
		else {
			validatedData.setDaikouNumber(inputNumber);
			ctrl = new JShiharaiMasterMaintenanceEditFrameCtrl(validatedData);
		}

		JScene.CreateDialog(
				this,
				ctrl,
				new DaikouWindowEvent()
			);
	}

	/**
	 * �j���̃��W�I�{�^���������ꂽ�ꍇ
	 */
	public void changedMaleState(ItemEvent e) {
		if (ItemEvent.SELECTED == e.getStateChange()) {
			jTextField_sex.setText("1");
		} else {
			jTextField_sex.setText("");
		}
	}

	/**
	 * �����̃��W�I�{�^���������ꂽ�ꍇ
	 */
	public void changedFemaleState(ItemEvent e) {
		if (ItemEvent.SELECTED == e.getStateChange()) {
			jTextField_sex.setText("2");
		} else {
			jTextField_sex.setText("");
		}
	}

	/**
	 * �A�N�V�������X�i�[
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		/* �I���{�^�� */
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			dispose();
		}
		/* �o�^�{�^�� */
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);

		/* DB�Ǎ��{�^�� */
		} else if (source == jButton_Call) {
			logger.info(jButton_Call.getText());
			pushedCallButton(e);
		}
		/* �N���A�{�^�� */
		else if (source == jButton_Clear) {
			logger.info(jButton_Clear.getText());
			pushedClearButton();
		}
		/* �����Z�Ǎ��{�^�� */
		else if (source == jButton_ORCA) {
			logger.info(jButton_ORCA.getText());
			pushedORCAButton();
		}
//		/* QR�{�^�� */
//		else if (source == jButton_QR) {
//			logger.info(jButton_QR.getText());
//			pushedQRButton(e);
//		}
		/* ���͕[����{�^�� */
		else if (source == jButton_Print) {
			logger.info(jButton_Print.getText());
			pushedPrintButton(e);
		}

		/*
		 * ��Ë@�֔ԍ��y�юx����s�@�֔ԍ��t�B�[���h��Enter���������ۂɖ��̂Ɠd�b�ԍ���ǂݍ���
		 */
		else if (source == jTextField_hokenjaNumber) {
			logger.info(jTextField_hokenjaNumber.getText());
			enterKeyPushedHokenjyaNumber(false);

		}
		/* �x����s�@�֓��͗� */
		else if (source == jTextField_daikouNumber) {
			logger.info(jTextField_daikouNumber.getText());
			enterKeyPushedDaikouNumber(false);
		}

		/* ����ID���͗� */
		else if (source == jTextField_ORCAID) {
			logger.info(jTextField_ORCAID.getText());
			this.pushedORCAButton();
		}

		/* �ی��Ҍďo�{�^�� */
		else if (source == jButton_CallHokenjya) {
			logger.info(jButton_CallHokenjya.getText());
			String number = this.jTextField_hokenjaNumber.getText();
			number = JValidate.fillZero(number, 8);

			if (!this.hokenjaNumbers.contains(number)) {
				return;
			}

			JHokenjyaMasterMaintenanceEditFrameCtrl ctrl = null;

			if (number == null || number.isEmpty()) {
				ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl();
			} else {
				ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl(number);
			}

			JScene.CreateDialog(this, ctrl, new HokenjyaWindowEvent());
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		Object source = e.getSource();
		if (source == jRadioButton_Male) {
			changedMaleState(e);
		}

		else if (source == jRadioButton_Female) {
			changedFemaleState(e);
		}

		/*
		 * �΂ƂȂ�e�L�X�g�t�B�[���h��setter���Ă񂾎��_�Ŏ�ʂ���ɃZ�b�g����Ă��邱�Ƃ�ۏ؂���
		 */
		else if (source == jComboBox_MadoguchiKihonSyubetu) {

			this.showUnitAndSyubetsuNumToTextField(
					jTextField_KihonSyubetsuNum,
					jTextField_MadoguchiKihon,
					jComboBox_MadoguchiKihonSyubetu,
					jLabel_madoguchikihon_unit,
					jTextField_KihonJougen
				);
		}
		else if (source == jComboBox_MadoguchiShousaiSyubetu) {
			this.showUnitAndSyubetsuNumToTextField(
					jTextField_SyousaiSyubetsuNum,
					jTextField_MadoguchiShousai,
					jComboBox_MadoguchiShousaiSyubetu,
					jLabel_madoguchishosai_unit,
					jTextField_ShousaiJougen
				);
		}

		else if (source == jComboBox_MadoguchiTsuikaSyubetu) {
			this.showUnitAndSyubetsuNumToTextField(
					jTextField_TsuikaSyubetsuNum,
					jTextField_MadoguchiTsuika,
					jComboBox_MadoguchiTsuikaSyubetu,
					jLabel_madoguchitsuika_unit,
					jTextField_TsuikaJyougen
				);
		}
		else if (source == jComboBox_MadoguchiDockSyubetu) {
			this.showUnitAndSyubetsuNumToTextField(
					jTextField_DockSyubetsuNum,
					jTextField_MadoguchiDock,
					jComboBox_MadoguchiDockSyubetu,
					jLabel_madoguchidock_unit,
					jTextField_DockJougen
				);
		}
		/* �ی��ҏ��I�� */
		else if (source == jComboBox_HokenjyaNumber) {
			if (e.getStateChange() == ItemEvent.SELECTED
					&& this.jComboBox_HokenjyaNumber.hasFocus()
					&& ! this.jComboBox_HokenjyaNumber.isPopupVisible()
			) {
				this.showHokenjyaNumberToTextField();
			}
		}
		/* �x����s�@�֏��I�� */
		else if (source == jComboBox_DaikouNumber) {
			if (e.getStateChange() == ItemEvent.SELECTED
					&& this.jTextField_daikouNumber.hasFocus()
					&& ! this.jComboBox_DaikouNumber.isPopupVisible()
			) {
				this.showDaikouNumberToTextField();
			}
		}
	}

	private void showDaikouNumberToTextField() {
		int selectedIndex = jComboBox_DaikouNumber.getSelectedIndex();
		int size = this.daikouNumbers.size();

		if (size > selectedIndex && selectedIndex >= 0) {
			String number = this.daikouNumbers.get(selectedIndex);

			if (selectedIndex >= COMBOBOX_NUMBER_START_INDEX) {

				this.jTextField_daikouNumber.setText(number);
			}
		}
	}

	private void showHokenjyaNumberToTextField() {
		int selectedIndex = jComboBox_HokenjyaNumber.getSelectedIndex();
		int size = this.hokenjaNumbers.size();

		if (size > selectedIndex && selectedIndex >= 0) {
			String number = this.hokenjaNumbers.get(selectedIndex);

			if (selectedIndex >= COMBOBOX_NUMBER_START_INDEX ) {

				this.jTextField_hokenjaNumber.setText(number);
				// add s.inoue 2009/10/15
				this.setHokenjyaTanka(number);
			}
		}
	}

	// add s.inoue 2009/10/15
	private void setHokenjyaTanka(String hokenjyaNumber){

		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL, KIGO, HON_GAIKYURATE, HON_NYUKYURATE, KZK_GAIKYURATE, KZK_NYUKYURATE, ITAKU_KBN, TANKA_KIHON, ");
		sb.append("HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, TANKA_NINGENDOC, TANKA_HANTEI");
		sb.append(" FROM T_HOKENJYA WHERE HKNJANUM = ");
		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
		// add s.inoue 2010/01/15
		sb.append(" AND YUKOU_FLG = '1' ");

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		resultItem = result.get(0);

		String hani = resultItem.get("TANKA_HANTEI");
		hani = (hani == null)?TANKA_HANTEI_KIHON: hani;

		if (hani.equals(TANKA_HANTEI_DOC)) {
//			jRadioButton_Doc.setSelected(true);
			String tankaDoc = resultItem.get("TANKA_NINGENDOC");
			jTextField_NingenDocTanka.setText(tankaDoc.equals("")?"0":tankaDoc);
		}else{
			// ��{���f�̂ݐݒ�
//			jRadioButton_Kihon.setSelected(true);
			String tankaKihon = resultItem.get("TANKA_KIHON");
			String tankaHinketu = resultItem.get("TANKA_HINKETU");
			String tankaSindenzu = resultItem.get("TANKA_SINDENZU");
			String tankaGantei = resultItem.get("TANKA_GANTEI");
			jTextField_KihonTanka.setText(tankaKihon.equals("")?"0":tankaKihon);
			jTextField_HinketsuTanka.setText(tankaHinketu.equals("")?"0":tankaHinketu);
			jTextField_SindenzuTanka.setText(tankaSindenzu.equals("")?"0":tankaSindenzu);
			jTextField_GanteiTanka.setText(tankaGantei.equals("")?"0":tankaGantei);
		}
	}

	/**
	 * �P�ʂƎ�ʃR�[�h��\������B
	 */
	private void showUnitAndSyubetsuNumToTextField(
			ExtendedOpenTextControl jTextField_SyubetsuNum,
			ExtendedOpenTextControl jTextField_Madoguchi,
			ExtendedComboBox jComboBox_MadoguchiSyubetu,
			ExtendedLabel jLabel_madoguchi_unit,
			ExtendedOpenTextControl jTextField_Jougen
			) {

		/*
		 * �u��f�҂͕��S�����v���I�����ꂽ�ꍇ�́A
		 * ���S�z�E���S�������󗓂ɂ��A������Ԃɂ���B
		 */
		jTextField_Madoguchi.setText("");

		int selectedIndex = jComboBox_MadoguchiSyubetu.getSelectedIndex();
		if (selectedIndex == 0 || selectedIndex == 1) {
			jTextField_Madoguchi.setEnabled(false);
			jTextField_Madoguchi.setOpaque(false);

			jLabel_madoguchi_unit.setText("");
		} else {
			jTextField_Madoguchi.setEnabled(true);
			jTextField_Madoguchi.setOpaque(true);

			if (selectedIndex == 2) {
				jLabel_madoguchi_unit.setText(STRING_UNIT_YEN);
			} else if(selectedIndex == 3){
				jLabel_madoguchi_unit.setText(STRING_UNIT_PERCENT);
			} else {
				jLabel_madoguchi_unit.setText("");
			}
		}
		// eidt s.inoue 2011/12/13
		if (selectedIndex == 0) {
			jTextField_SyubetsuNum.setText("");
			// jTextField_Jougen.setEditable(false);
			jTextField_Jougen.setEnabled(false);
		} else if (selectedIndex == 1){
			jTextField_SyubetsuNum.setText("1");
			// jTextField_Jougen.setEditable(true);
			jTextField_Jougen.setEnabled(true);
		} else if (selectedIndex == 2){
			jTextField_SyubetsuNum.setText("2");
			// jTextField_SyubetsuNum.setEditable(false);
//			jTextField_SyubetsuNum.setEnabled(false);	// edit n.ohkubo 2014/10/01�@�폜
			jTextField_Jougen.setEnabled(false);	// edit n.ohkubo 2014/10/01�@�ǉ�
		} else if (selectedIndex == 3){
			jTextField_SyubetsuNum.setText("3");
			// jTextField_Jougen.setEditable(false);
			jTextField_Jougen.setEnabled(false);
		} else {
			jTextField_SyubetsuNum.setText("");
			// jTextField_Jougen.setEditable(false);
			jTextField_Jougen.setEnabled(false);
		}
	}

	/**
	 * �ی��ҏ��o�^��ʂ̃E�B���h�E���X�i�[
	 */
	public class HokenjyaWindowEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (JHokenjyaMasterMaintenanceEditFrameCtrl.isRegistered()) {
				JKojinRegisterFrameCtrl.this.initializeHokenjaNumComboBox();
				String hokenjaNumber = JHokenjyaMasterMaintenanceEditFrameCtrl
						.getLastAddedNumber();

				int hokenjaIndex = JKojinRegisterFrameCtrl.this.hokenjaNumbers
						.indexOf(hokenjaNumber);
				JKojinRegisterFrameCtrl.this.jComboBox_HokenjyaNumber
						.setSelectedIndex(hokenjaIndex);
				JKojinRegisterFrameCtrl.this.jTextField_hokenjaNumber.setText(
						JHokenjyaMasterMaintenanceEditFrameCtrl.getLastAddedNumber());
			}
			else {
				JKojinRegisterFrameCtrl.this.jComboBox_HokenjyaNumber.setSelectedIndex(0);
			}
		}
	}

	/**
	 * �x����s�@�֓o�^��ʂ̃E�B���h�E���X�i�[
	 */
	public class DaikouWindowEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (JShiharaiMasterMaintenanceEditFrameCtrl.isRegistered()) {
				JKojinRegisterFrameCtrl.this.initializeDaikouNumberComboBox();
				String number = JShiharaiMasterMaintenanceEditFrameCtrl
						.getLastAddedNumber();

				int index = JKojinRegisterFrameCtrl.this.daikouNumbers
						.indexOf(number);
				JKojinRegisterFrameCtrl.this.jComboBox_DaikouNumber
						.setSelectedIndex(index);
				JKojinRegisterFrameCtrl.this.jTextField_daikouNumber.setText(
						JShiharaiMasterMaintenanceEditFrameCtrl.getLastAddedNumber());
			}
			else {
				JKojinRegisterFrameCtrl.this.jComboBox_DaikouNumber.setSelectedIndex(0);
			}
		}
	}

	/**
	 * keyTyped �C�x���g�n���h��
	 */
	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {

		Object source = e.getSource();
		char keyChar = e.getKeyChar();

		/* �j�����W�I�{�^�� */
		if (source == this.jRadioButton_Male) {
			if (keyChar == KeyEvent.VK_SPACE) {
				this.jRadioButton_Male.setSelected(true);
			}
		/* �������W�I�{�^�� */
		} else if (source == this.jRadioButton_Female) {
			if (keyChar == KeyEvent.VK_SPACE) {
				this.jRadioButton_Female.setSelected(true);
			}
		/* ���ʓ��͗� */
		} else if (source == jTextField_sex) {

			if (keyChar == KeyEvent.VK_1) {
				this.jTextField_sex.setText("1");
				this.jRadioButton_Male.setSelected(true);
			} else if (keyChar == KeyEvent.VK_2) {
				this.jTextField_sex.setText("2");
				this.jRadioButton_Female.setSelected(true);
			} else {
				String currentValue = this.jTextField_sex.getText();
				if (!currentValue.equals("1") && !currentValue.equals("2")) {
					this.groupSex.clearSelection();
				}
			}
		} else if (source == jTextField_KihonSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_KihonSyubetsuNum,
					jComboBox_MadoguchiKihonSyubetu
				);

		} else if (source == jTextField_SyousaiSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_SyousaiSyubetsuNum,
					jComboBox_MadoguchiShousaiSyubetu
				);

		} else if (source == jTextField_TsuikaSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_TsuikaSyubetsuNum,
					jComboBox_MadoguchiTsuikaSyubetu
				);

		} else if (source == jTextField_DockSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_DockSyubetsuNum,
					jComboBox_MadoguchiDockSyubetu
				);
		}
		/* �ی��Ҕԍ� */
		else if (source == this.jComboBox_HokenjyaNumber) {
			if (keyChar == KeyEvent.VK_ENTER) {
				this.typedEnterKeyOnHokenjyaNumberTextField();
			}
		}
		else if (source == this.jComboBox_DaikouNumber) {
			if (keyChar == KeyEvent.VK_ENTER) {
				String selectedItem = (String)
					this.jComboBox_DaikouNumber.getSelectedItem();

				if (selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM)) {
				}
				else {
					this.showDaikouNumberToTextField();
				}
			}
		}
		// del s.inoue 2009/10/15
//		/* ��{���W�I�{�^�� */
//		else if (source == this.jRadioButton_Kihon) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Kihon.setSelected(true);
//			}
//		/* Doc���W�I�{�^�� */
//		} else if (source == this.jRadioButton_Doc) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Doc.setSelected(true);
//			}
//		else if (source == this.jTextField_TankaHantei) {
//			if (keyChar == KeyEvent.VK_1) {
//				this.jTextField_TankaHantei.setText("1");
//				this.jRadioButton_Kihon.setSelected(true);
//			} else if (keyChar == KeyEvent.VK_2) {
//				this.jRadioButton_Doc.setText("2");
//				this.jRadioButton_Doc.setSelected(true);
//			} else {
//				String currentValue = this.jTextField_TankaHantei.getText();
//				if (!currentValue.equals("1") && !currentValue.equals("2")) {
//					this.groupHantei.clearSelection();
//				}
//			}
//		}
	}

	private void typedEnterKeyOnHokenjyaNumberTextField() {
		String selectedItem = (String)
			this.jComboBox_HokenjyaNumber.getSelectedItem();

		if (selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM)) {
		}
		else {
			this.showHokenjyaNumberToTextField();
		}
	}

	private void setSyubetsuNumText(
			char keyChar,
			ExtendedOpenTextControl textField,
			ExtendedComboBox comboBox) {

		switch (keyChar) {
		case KeyEvent.VK_1:
			textField.setText("1");
			comboBox.setSelectedIndex(1);
			break;

		case KeyEvent.VK_2:
			textField.setText("2");
			comboBox.setSelectedIndex(2);
			break;

		case KeyEvent.VK_3:
			textField.setText("3");
			comboBox.setSelectedIndex(3);
			break;

		case KeyEvent.VK_ENTER:
			textField.transferFocus();
			break;

		default:
			textField.setText("");
			comboBox.setSelectedIndex(0);

			break;
		}
	}
//	/**
//	 * ��{�̃��W�I�{�^���������ꂽ�ꍇ
//	 */
//	public void changedKihonState(ItemEvent e) {
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//			jTextField_TankaHantei.setText("1");
//		} else {
//			// add s.inoue 2009/10/02
//			clearJTextfield_Tanka(false);
//		}
//	}

//	/**
//	 * �l�ԃh�b�N�̃��W�I�{�^���������ꂽ�ꍇ
//	 */
//	public void changedDocState(ItemEvent e) {
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//			jTextField_TankaHantei.setText("2");
//		} else {
//			// add s.inoue 2009/10/02
//			clearJTextfield_Tanka(true);
//		}
//	}

//	// add s.inoue 2009/10/02
//	private void clearJTextfield_Tanka(boolean hantei){
//		jTextField_TankaHantei.setText("");
//		if (hantei) {
//			this.jTextField_KihonTanka.setText("");
//			this.jTextField_SindenzuTanka.setText("");
//			this.jTextField_GanteiTanka.setText("");
//			this.jTextField_HinketsuTanka.setText("");
//		}else{
//			this.jTextField_NingenDocTanka.setText("");
//		}
//	}
	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
		Object source = event.getSource();

		if (source == this.jComboBox_HokenjyaNumber) {
			this.showHokenjyaNumberToTextField();
			this.jComboBox_HokenjyaNumber.transferFocus();
		}
		else if(source == this.jComboBox_DaikouNumber){
			this.showDaikouNumberToTextField();
			this.jComboBox_DaikouNumber.transferFocus();
		}
	}

//	// add s.inoue 2012/02/02
//	@Override
//	public void focusGained(FocusEvent event) {
//		Object source = event.getSource();
//
//		if (source instanceof JTextField){
//			JTextField txt = (JTextField)source;
//			txt.setBackground(JApplication.backColor_Focus);
//		}else if (source instanceof JComboBox){
//			JComboBox cmb = (JComboBox)source;
//			cmb.setBackground(JApplication.backColor_Focus);
//		}
//	}

	@Override
	public void focusLost(FocusEvent event) {
		Object source = event.getSource();

		if (source instanceof JTextField){

			JTextField txt = (JTextField)source;
			if(txt.getParent().equals(this.jTextField_NameKana)){
				// �V�K�A�o�N�����̏ꍇ�̂ݎ������ȂŌ������s��
				if (this.mode == ADD_MODE){
					String selectedItem = this.jTextField_NameKana.getText();

					if (selectedItem == null || selectedItem.isEmpty()) {
						return;
					}
					setKojinDataFromShimeiKana(selectedItem);
				}
			}else if(txt.getParent().equals(this.jTextField_Birthday)){
				// eidt s.inoue 2012/06/27
				String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getText());
				// String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getDateText());
				if ( selectedItem == null) {
					jTextField_YearOld.setText("");
					return;
				}

				// eidt s.inoue 2013/07/01
//				String age ="";
//				if (yearOld_flg){
//					age = String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem));
//				}else{
//					age = JYearAge.getAge(selectedItem);
//				}
				int age =0;
				if (yearOld_flg){
					if (!kensanengapi.equals("")){
						age = FiscalYearUtil.getFiscalYear(selectedItem,kensanengapi);
					}else{
						age = FiscalYearUtil.getFiscalYear(selectedItem);
					}
				}else{
					if (!kensanengapi.equals("")){
						age = Integer.parseInt(JYearAge.getAge(selectedItem,kensanengapi));
					}else{
						age = Integer.parseInt(JYearAge.getAge(selectedItem));
					}
				}
				jTextField_YearOld.setText(String.valueOf(age));

			}else if(txt.getParent().equals(this.jTextField_ZipCode)){
					if (jTextField_ZipCode.getTextTrim().equals(""))
						return;
					// eidt s.inoue 2012/04/24 gettext
					String zipText = setZipCodeAddress(this.jTextField_ZipCode.getText());
					if (zipText.equals(""))
						return;
					jTextField_Address.setText(zipText);
			}
		}else if (source instanceof JComboBox){

			JComboBox cmb = (JComboBox)source;
			if (cmb.equals(this.jComboBox_HokenjyaNumber)) {
				String selectedItem = (String)
					this.jComboBox_HokenjyaNumber.getSelectedItem();

				if (selectedItem == null || selectedItem.isEmpty()) {
					return;
				}

				String inputText = this.jTextField_hokenjaNumber.getText();

				if(selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM) ){
					this.showAddHokenjaNumberDialog(inputText);
				}
			} else if(cmb.equals(this.jComboBox_DaikouNumber)){
				String selectedItem = (String)
					this.jComboBox_DaikouNumber.getSelectedItem();

				if (selectedItem == null || selectedItem.isEmpty()) {
					return;
				}

				String inputText = this.jTextField_daikouNumber.getText();

				if(selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM) ){
					this.showAddDaikouNumberDialog(inputText);
				}
			}
		}
		// eidt s.inoue 2011/12/13 ��ɒu������
//		else if(source.equals(this.jTextField_NameKana)){
//			// �V�K�A�o�N�����̏ꍇ�̂ݎ������ȂŌ������s��
//			if (this.mode == ADD_MODE){
//				String selectedItem = this.jTextField_NameKana.getText();
//
//				if (selectedItem == null || selectedItem.isEmpty()) {
//					return;
//				}
//				setKojinDataFromShimeiKana(selectedItem);
//			}
//		}
//		else if(source.equals(this.jTextField_Birthday)){
//			// eidt s.inoue 2011/12/12
//			// String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getText());
//			String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getDateText());
//			if ( selectedItem == null) {
//				jTextField_YearOld.setText("");
//				return;
//			}
//			jTextField_YearOld.setText(String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem)));
//		}
//		// add s.inoue 2010/03/29
//		else if(source.equals(this.jTextField_ZipCode)){
//			if (jTextField_ZipCode.getText().equals(""))
//				return;
//
//			String zipText = setZipCodeAddress(this.jTextField_ZipCode.getText());
//			if (zipText.equals(""))
//				return;
//
//			jTextField_Address.setText(zipText);
//		}
	}

	// add s.inoue 2010/04/02
	private String setZipCodeAddress(String zipCode){

			ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
			Hashtable<String, String> resultItem = new Hashtable<String, String>();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT ADDRESS,POST_CD FROM T_POST ");
			// eidt s.inoue 2012/04/24
//			sb.append("(SELECT SUBSTRING(POST_CD FROM 1 FOR 3) || SUBSTRING(POST_CD FROM 5 FOR 4) POSTCD,");
//			sb.append(" POST_CD SP_POSTCD FROM T_POST) SUB_POST");
//			sb.append(" where SUB_POST.POSTCD = ");
			sb.append(" where POST_CD = ");
			sb.append(JQueryConvert.queryConvert(zipCode));
//			sb.append(" and SUB_POST.SP_POSTCD = T_POST.POST_CD ");

			try {
				result = JApplication.systemDatabase.sendExecuteQuery(sb.toString());
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			if (result.size() <= 0)
				return "";

			resultItem = result.get(0);
			return resultItem.get("ADDRESS");

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
			case KeyEvent.VK_F1:
				logger.info(jButton_End.getText());
				dispose();break;
			case KeyEvent.VK_F2:
				logger.info(jButton_Clear.getText());
				pushedClearButton();break;
			case KeyEvent.VK_F5:
				logger.info(jButton_Print.getText());
				pushedPrintButton(null);break;
			case KeyEvent.VK_F6:
				logger.info(jButton_Call.getText());
				pushedCallButton(null);break;
			case KeyEvent.VK_F12:
				logger.info(jButton_Register.getText());
				pushedRegisterButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

