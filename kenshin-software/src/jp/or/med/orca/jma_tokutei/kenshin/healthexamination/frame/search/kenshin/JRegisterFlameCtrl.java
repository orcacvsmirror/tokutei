package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.openswing.swing.client.CheckBoxControl;
import org.openswing.swing.domains.java.DomainPair;
import org.openswing.swing.util.client.ClientSettings;

import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextArea;
import jp.or.med.orca.jma_tokutei.common.component.IDialog;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JYearAge;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IKekkaRegisterInputDialog;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.JKekkaRegisterInputDialogFactory;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenComboboxControl;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFormattedFloatControl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintDefine;
//import jp.or.med.orca.jma_tokutei.common.app.ClientSettings;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JConstantString;

/**
 * ���茒�f�\�t�g�E�G�A�̃��O�C����ʂ̃t���[���̃R���g���[��
 */
public class JRegisterFlameCtrl extends JRegisterFlame {

//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//	private JKekkaRegisterFrameData validatedData = new JKekkaRegisterFrameData();
	private static org.apache.log4j.Logger logger = Logger.getLogger(JRegisterFlameCtrl.class);
	private ArrayList<Hashtable<String, String>> tableResult = null;
	private ArrayList<Hashtable<String, String>> tableMyTabResult = null;

	// top + texthight = 20�Ńt�H�[�J�X�ړ�(20���ړ�)�������\
	private int cntrolTop = 0;
	private int controlLeft = 2;
	private int controlRight = 2;
	private int controlTextHeight = 20;
	private int controlLabelHeight = 20;
	private int controlButtonHeight = 20;
	private int koumoku_nm_Width = 200;
	private int kensahouhou_Width = 50;
	private int kekka_Width = 70;
	private int tani_Width = 64;
	private int zenkai_Width = 50;
	private int hl_Width = 20;
	private int hantei_Width = 60;
//	private int controlButtonWidth = 40;
	// 17 ��
	private int forcus_range = 20;
	private int forcus_foward = 100;
	private int forcus_back = 60;

	private IDialog patternSelectDialog;
	private ExtendedCheckBox[] jCheckBox_1 = null;
// del s.inoue 2012/07/13
//	private ExtendedButton[] jButton_1 = null;
	// private ExtendedOpenFormattedFloatControl[] jTextField_1 = null;
	private ExtendedOpenFormattedFloatControl[] jTextField_1 = null;
// edit s.inoue 2012/02/23 �p�~
//	private ExtendedOpenComboboxControl[] jComboBox_1 = null;
	private ExtendedComboBox[] jComboBox_1 = null;
	private ExtendedTextArea[] jTextAreaField_1 = null;
	// add s.inoue 2013/02/14
	private JScrollPane[] jTextScrollPane_1 = null;

	private ExtendedCheckBox[] jCheckBox_2 = null;
// del s.inoue 2012/07/13
//	private ExtendedButton[] jButton_2 = null;
	private ExtendedOpenFormattedFloatControl[] jTextField_2 = null;
// edit s.inoue 2012/02/23 �p�~
//	private ExtendedOpenComboboxControl[] jComboBox_2 = null;
	private ExtendedComboBox[] jComboBox_2 = null;
	private ExtendedTextArea[] jTextAreaField_2 = null;
	// add s.inoue 2013/02/14
	private JScrollPane[] jTextScrollPane_2 = null;

	private static final String NUMBER_FIELD = "1";
	private static final String CODE_FIELD = "2";
	private static final String TEXT_FIELD = "3";

	private static final String strHighMessageValue = "����l";
	private static final String strLowMessageValue = "�����l";

	private IKekkaRegisterInputDialog dialogs;
	private JRegisterFlameData validatedData = new JRegisterFlameData();
	private TKensakekaTokuteiDao kensakekaTokuteiDao = null;
	private TKensakekaSonotaDao kensakekaSonotaDao = null;
	private TKensakekaTokutei kensakekaTokutei = null;
	private TKensakekaSonota kensakekaSonota = null;

	private int kenshinPatternNumber = -1;
	private HashMap<String, String> kensaHouhouCodeList = null;
	private boolean isCopy = false;
	private boolean isPrev = false;

	// ���^�{�A�ی��w���ɂ��t�B�[���h��������鎖�ɂ�钲���l
	protected int posHokensido = 0;
	protected int posMetabo = 0;

	private boolean fnc_yearOld_flg = false;
//	private boolean fnc_print_flg = false;

	private JScrollBar jScrollbarKihon = null;
	private JScrollBar jScrollbarSyosai = null;
	private JScrollBar jScrollbarTuika = null;
	private JScrollBar jScrollbarMonshin = null;
	private JScrollBar jScrollbarMyTab = null;

	private static final String CODE_BMI = "9N011000000000001";
	private static final String CODE_HIGHT = "9N001000000000001";
	private static final String CODE_WEIGHT = "9N006000000000001";
	private String BMIFormat = "";
	private String height = "";
	private String weight ="";
	private String kensaKekka ="";
//	private boolean noChangeflg = false;

//	private static final String CODE_HBA1C_4_JDS = "3D045000001999902";
//	private static final String CODE_HBA1C_3_JDS = "3D045000001927102";
//	private static final String CODE_HBA1C_2_JDS = "3D045000001920402";
//	private static final String CODE_HBA1C_1_JDS = "3D045000001906202";

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	public JRegisterFlameCtrl(String patternNo){
		JRegisterFrameTabSetting(null,true);
	}

	/**
	 * �R���X�g���N�^
	 * @param srcData : ���n���f�[�^
	 */
	public JRegisterFlameCtrl(
			JKenshinKekkaSearchListFrameData srcData,
			boolean isCopy
			) {

		this.isCopy = isCopy;
		JRegisterFrameTabSetting(srcData,true);
	}

	/*
	 * Dao�ݒ�
	 */
	private void setInitilizeDBSetting(){
		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();
			kensakekaTokutei = new TKensakekaTokutei();
			kensakekaSonota = new TKensakekaSonota();
			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(connection,kensakekaTokutei);
			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(connection,kensakekaSonota);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/************************* �p�����[�^�ݒ� start *************************/

//	/*
//	 * �e�X�g�p�R�[�h
//	 */
//	private JKenshinKekkaSearchListFrameData srcData =null;
//	private void testCodeSetting(JKenshinKekkaSearchListFrameData srcData){
//
//		validatedData.setUketuke_id("201109130001");
//		validatedData.setKensaJissiDate("20100909",false);
//		validatedData.setHokenjyaNumber("11111111");

//		srcData = new JKenshinKekkaSearchListFrameData();
//		srcData.setNAME("����������");
//		srcData.setSEX("�j");
//		srcData.setBIRTHDAY(19760404);
//		srcData.setHIHOKENJYASYO_KIGOU("����");
//		srcData.setHIHOKENJYASYO_NO("�P�Q�R�S�T");
//	}

	private void initilazeFunctionSetting(){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
			sb.append(" FROM T_SCREENFUNCTION ");
			// eidt s.inoue 2012/01/19
			sb.append(" WHERE SCREEN_CD IN (");
			sb.append(JQueryConvert.queryConvertAppendComma("001"));
			sb.append(JQueryConvert.queryConvert("002"));
			sb.append(" )");

			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> item = result.get(i);

			String functionCd = item.get("FUNCTION_CD");
			if (JApplication.func_yearOldCode.equals(functionCd)){
				fnc_yearOld_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
//			// eidt s.inoue 2012/01/19
//			// �̎����t���O�ǉ�
//			}else if (JApplication.func_printCode.equals(functionCd)){
//				if (item.get("FUNCTION_FLG").equals("1"))jButton_PrintRyoshu.setVisible(true);
			}
		}

	}
	/************************* �p�����[�^�ݒ� end *************************/

	/************************* �S�̃p�l���ݒu start *************************/
	/*
	 * srcData ���n���f�[�^
	 * eventFlg �C�x���g����
	 */

	public void JRegisterFrameTabSetting(JKenshinKekkaSearchListFrameData srcData,boolean isEvent){

		int tabCount = jPanelRegisterCenter.getTabCount();
		// combo reflesh
		if (tabCount >= 5){
			for (int i = 0; i < tabCount; i++) {
				jPanelRegisterCenter.remove(0);
			}
		}

		if (srcData != null){
			validatedData.setUketuke_id(srcData.getUKETUKE_ID());
			validatedData.setUketukePre_id(srcData.getUKETUKEPRE_ID());

			validatedData.setKensaJissiDate(srcData.getKENSA_NENGAPI(),false);
			this.initialDate = srcData.getKENSA_NENGAPI();
			validatedData.setHokenjyaNumber(srcData.getHKNJANUM());
			// eidt s.inoue 2011/12/08
			validatedData.setNameKana(srcData.getNAME());
			// testCodeSetting(srcData);
		}

		setInitilizeDBSetting();
		initilazeFunctionSetting();
		setLeftTableDataSetting(srcData,isEvent);

		try {
			// �ʏ�Tab
			tableResult = JApplication.kikanDatabase.sendExecuteQuery(createCellDataSql());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		jCheckBox_1 = new ExtendedCheckBox[tableResult.size()];
//		jButton_1 = new ExtendedButton[tableResult.size()];
		jTextField_1 = new ExtendedOpenFormattedFloatControl[tableResult.size()];
		jComboBox_1 = new ExtendedComboBox[tableResult.size()];
		jTextAreaField_1= new ExtendedTextArea[tableResult.size()];
		// add s.inoue 2013/02/14
		jTextScrollPane_1 = new JScrollPane[tableResult.size()];

		settingTabData(false);

		try {
			// MyTab
			tableMyTabResult = JApplication.kikanDatabase.sendExecuteQuery(createCellDataMyTabSql());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		jCheckBox_2 = new ExtendedCheckBox[tableMyTabResult.size()];
//		jButton_2 = new ExtendedButton[tableMyTabResult.size()];
		jTextField_2 = new ExtendedOpenFormattedFloatControl[tableMyTabResult.size()];
		jComboBox_2 = new ExtendedComboBox[tableMyTabResult.size()];

		jTextAreaField_2= new ExtendedTextArea[tableMyTabResult.size()];
		// add s.inoue 2013/02/14
		jTextScrollPane_2 = new JScrollPane[tableMyTabResult.size()];

		kensaHouhouCodeList = JConstantString.getKensahouhou();

		settingTabData(true);

		// add s.inoue 2012/02/13
//		initializeFocusTraversal();

//		if (flg){
////			kenshinPatternItemChange();
//		   	jComboBox_Pattern.addItemListener(new ItemListener() {
//	    	      public void itemStateChanged(ItemEvent e) {
//
//	    	    	  System.out.println(kenshinPatternNumber + " ����" + jComboBox_Pattern.getSelectedIndex());
//		    	    if (e.getStateChange() == e.SELECTED && !isInit) {
//						stateChangedKenshinPatternNumber(
//								validatedData.getUketuke_id(),
//								validatedData.getKensaJissiDate(),
//								false,
//								isNewKekkaData);
//						// stateChangedKenshinPatternNumber("201108250001","20110913",false,isNewKekkaData);
//					}
//	    	      }
//	    	});
//		}
		if (isEvent)
			jComboBox_Pattern.addActionListener(this);

		if (jPanelRegisterCenter.getComponentCount()==0){
			// eidt s.inoue 2011/12/09
			String gridMasterCnt = PropertyUtil.getProperty( "register.myTabPos");
			if (Integer.parseInt(gridMasterCnt) == 1){
				jPanelRegisterCenter.addTab("ϲ�����", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("��{���f", jInnerPanelKenshin);
			if (Integer.parseInt(gridMasterCnt) == 2){
				jPanelRegisterCenter.addTab("ϲ�����", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("�ڍ׌��f", jInnerPanelSyosai);
			if (Integer.parseInt(gridMasterCnt) == 3){
				jPanelRegisterCenter.addTab("ϲ�����", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("�ǉ����f", jInnerPanelTuika);
			if (Integer.parseInt(gridMasterCnt) == 4){
				jPanelRegisterCenter.addTab("ϲ�����", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("��f��", jInnerPanelMonshin);
			if (Integer.parseInt(gridMasterCnt) == 5){
				jPanelRegisterCenter.addTab("ϲ�����", jInnerPanelMyTab);
			}

		}

//		/* �@�֑I�𗓂�����������B */
//		this.initializeKikanComboBox();

		/* ���O�C����ʂ��A�N�e�B�u�Ŗ����Ȃ����Ƃ��A�X�v���b�V����ʂ��\���ɂ���B */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JSoftware.getSplashFrame().hideSplashWindow();
			}
		});

		this.setVisible(true);
		if (isCopy){
			this.jLabel_SeiriNO.setVisible(true);
			this.jTextField_SeiriNo.setVisible(true);
		}
		if (isEvent){
			// eidt s.inoue 2012/03/07
//			this.jTextField_KenshinJisiDay.grabFocus();
			jTextField_1[0].grabFocus();
//			this.jTextField_KenshinJisiDay.grabFocus();
			this.jComboBox_Pattern.grabFocus();
		}
	}

	/************************* �S�̃p�l���ݒu end *************************/

	/************************* ���p�l��-�^�u�ݒu start *************************/

	// ���p�l���ݒ菈��
	private void setLeftTableDataSetting(JKenshinKekkaSearchListFrameData srcData,boolean isEvent){

		TKensakekaTokutei kensaTokutei = null;

		try {
			String uketuke_id = "";
			if (isCopy && !isPrev){
				uketuke_id = validatedData.getUketukePre_id();
			}else{
				uketuke_id = validatedData.getUketuke_id();
			}

			kensaTokutei = kensakekaTokuteiDao.selectKekkaTokuteiByPrimaryKey(new Long(uketuke_id),new Integer(validatedData.getKensaJissiDate()));

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		if (kensaTokutei != null){

			// add s.inoue 2013/05/10
			jTextArea_SougouComment.addFocusListener(new FocusAdapter(){
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					Object source = e.getSource();

					if (source instanceof JTextArea){
						String areaText = jTextArea_SougouComment.getText().replaceAll("��", System.getProperty("line.separator"));
						System.out.println(areaText);
						jTextArea_SougouComment.setText(areaText);
					}
				}
			});

			jTextArea_SougouComment.setText(kensaTokutei.getKOMENTO());
			String kensaJissiDate = validatedData.getKensaJissiDate();

			if (srcData != null){
				jLabel_Name.setText(srcData.getNAME());
				jLabel_Name.setToolTipText(srcData.getNAME());
				jLabel_Field_Sex.setText(srcData.getSEX());
				jLable_Field_BirthDate.setText(String.valueOf(srcData.getBIRTHDAY()));

				jLabel_HiHokenjyaCode.setText(srcData.getHIHOKENJYASYO_KIGOU());
				jLabel_HiHokenjyaNumber.setText(srcData.getHIHOKENJYASYO_NO());
				jLabel_Field_JusinSeiriNo.setText(srcData.getJYUSHIN_SEIRI_NO());
	//			jTextField_KenshinJisiDay.setText(String.valueOf(kensaTokutei.getKENSA_NENGAPI()));

				String selectedItem = String.valueOf(srcData.getBIRTHDAY());

				int age =0;
				if (fnc_yearOld_flg){
					if (!kensaJissiDate.equals("")){
						age = FiscalYearUtil.getFiscalYear(selectedItem,kensaJissiDate);
					}else{
						age = FiscalYearUtil.getFiscalYear(selectedItem);
					}
				}else{
					if (!kensaJissiDate.equals("")){
						age = Integer.parseInt(JYearAge.getAge(selectedItem,kensaJissiDate));
					}else{
						age = Integer.parseInt(JYearAge.getAge(selectedItem));
					}
				}
				System.out.println("age:" + age);
				jLabel_Field_Nenrei.setText(String.valueOf(age));
			}

			String jisibi = String.valueOf(kensaTokutei.getKENSA_NENGAPI());

			if (isCopy) {
				validatedData.setKensaJissiDate(kensaJissiDate,false);
				// add s.inoue 2012/03/22
				try {
					Date dt = jTextField_KenshinJisiDay.getTextToDate(jisibi);
					jTextField_KenshinJisiDay.setDate(dt);
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}

				this.isNewKekkaData = true;
			}else {
				try {
					if (jisibi.equals("")) {
						this.isNewKekkaData = true;

						/* �V�X�e����������͂���B */
						Calendar cal = Calendar.getInstance();

						String year = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
						String month = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
						String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

						String jissiDateString = year + month + date;

	//					jTextField_Date.setText(jissiDateString);
						Date dt = jTextField_KenshinJisiDay.getTextToDate(jisibi);
						jTextField_KenshinJisiDay.setDate(dt);

						this.validatedData.setKensaJissiDate(jissiDateString,false);

					/* ���f���{�����w�肳��Ă���ꍇ */
					} else {
						Date dt = jTextField_KenshinJisiDay.getTextToDate(jisibi);
						jTextField_KenshinJisiDay.setDate(dt);
					}
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
			}

			/* �����敪�ݒ� */
			Integer seikyuKBN = kensaTokutei.getSEIKYU_KBN();

			// ���^�{����A�ی��w�����x���͕ʏ���
			if (seikyuKBN == null){
				jComboBox_SeikyuKubun.setSelectedIndex(0);
			}else{
				// 1: ��{�I�Ȍ��f 2: ��{�I�Ȍ��f�{�ڍׂȌ��f�̏ꍇ 3: ��{�I�Ȍ��f�{�ǉ����f���ڌ��f 4: ��{�I�Ȍ��f�{�ڍׂȌ��f+�ǉ����f���� 5: �l�ԃh�b�N
				switch (seikyuKBN) {
				case 1:jComboBox_SeikyuKubun.setSelectedIndex(0);break;
				case 2:jComboBox_SeikyuKubun.setSelectedIndex(1);break;
				case 3:jComboBox_SeikyuKubun.setSelectedIndex(2);break;
				case 4:jComboBox_SeikyuKubun.setSelectedIndex(3);break;
				case 5:jComboBox_SeikyuKubun.setSelectedIndex(4);break;
				}
			}

			/* ���f�p�^�[���ݒ� */
			// eidt s.inoue 2011/12/09
//			if (isEvent && !isInit){
			if (isEvent){
				Integer kenshinPattern = kensaTokutei.getK_P_NO();
				if (kenshinPattern == null){
					// eidt s.inoue 2012/03/22 0��1
					jComboBox_Pattern.setSelectedIndex(0);
				}else{
					// eidt s.inoue 2012/02/21
					if (!kenshinPattern.equals(kenshinPatternNumber)){

						// eidt s.inoue 2013/04/10
						// jComboBox_Pattern.setSelectedIndex(kenshinPattern - 1);
						// �󂫔ԍ�������ꍇ�̃o�O�̌���
						DomainPair[] pairs = jComboBox_Pattern.getDomain().getDomainPairList();
						int selectedIDX = -1;
					    for (int i = 0; i < pairs.length; ++i){
					    	if (pairs[i] != null)
					    	if (pairs[i].getCode().toString().equals(String.valueOf(kenshinPattern))){
					    		selectedIDX = i;
					    		System.out.println(jComboBox_Pattern.getSelectedIndex());
					    		break;
					    	}
					    }
					    jComboBox_Pattern.setSelectedIndex(selectedIDX);

						kenshinPatternNumber =kenshinPattern;
					}
				}
			// eidt s.inoue 2013/07/09
			}else{
				posHokensido = 0;
				posMetabo = 0;
			}
		} else {
			// add s.inoue 2012/04/24
			this.isNewKekkaData = true;

			// eidt s.inoue 2012/05/29
			if (kenshinPatternNumber < 0)
				kenshinPatternNumber = 1;

			// eidt s.inoue 2013/04/10
			// �󂫔ԍ�������ꍇ�̃o�O�̌���
			DomainPair[] pairs = jComboBox_Pattern.getDomain().getDomainPairList();
			int selectedIDX = -1;
		    for (int i = 0; i < pairs.length; ++i){
		    	if (pairs[i] != null)
		    	if (pairs[i].getCode().toString().equals(String.valueOf(kenshinPatternNumber))){
		    		selectedIDX = i;
		    		System.out.println(jComboBox_Pattern.getSelectedIndex());
		    		break;
		    	}
		    }

		    // eidt s.inoue 2013/04/10
			// jComboBox_Pattern.setSelectedIndex(kenshinPatternNumber -1);
		    jComboBox_Pattern.setSelectedIndex(selectedIDX);

			/**** ���������� add s.inoue 2012/03/22 */
			// tokutei�Ƀf�[�^�������p�^�[��
			jComboBox_SeikyuKubun.setSelectedIndex(0);
			// eidt s.inoue 2013/07/23 1 �� 0 �H�Ȃ��
			jComboBox_MetaboHantei.setSelectedIndex(0);
			jComboBox_HokenSidouLevel.setSelectedIndex(0);

			if (srcData != null){
				jLabel_Name.setText(srcData.getNAME());
				jLabel_Name.setToolTipText(srcData.getNAME());
				jLabel_Field_Sex.setText(srcData.getSEX());
				jLable_Field_BirthDate.setText(String.valueOf(srcData.getBIRTHDAY()));

				jLabel_HiHokenjyaCode.setText(srcData.getHIHOKENJYASYO_KIGOU());
				jLabel_HiHokenjyaNumber.setText(srcData.getHIHOKENJYASYO_NO());
				jLabel_Field_JusinSeiriNo.setText(srcData.getJYUSHIN_SEIRI_NO());
//				jTextField_KenshinJisiDay.setText(String.valueOf(kensaTokutei.getKENSA_NENGAPI()));

				// add s.inoue 2012/03/22
				String kensaJissiDate = srcData.getKENSA_NENGAPI();

				try {
					Date dt = jTextField_KenshinJisiDay.getTextToDate(kensaJissiDate);
					jTextField_KenshinJisiDay.setDate(dt);
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}

				String selectedItem = String.valueOf(srcData.getBIRTHDAY());

				int age =0;
				if (fnc_yearOld_flg){
					if (!kensaJissiDate.equals("")){
						age = FiscalYearUtil.getFiscalYear(selectedItem,kensaJissiDate);
					}else{
						age = FiscalYearUtil.getFiscalYear(selectedItem);
					}
				}else{
					if (!kensaJissiDate.equals("")){
						age = Integer.parseInt(JYearAge.getAge(selectedItem,kensaJissiDate));
					}else{
						age = Integer.parseInt(JYearAge.getAge(selectedItem));
					}
				}
				System.out.println("age:" + age);
				jLabel_Field_Nenrei.setText(String.valueOf(age));
			}
			/**** ������������ add s.inoue 2012/03/22 */
		}
	}

	/************************* ���p�l��-�^�u�ݒu end *************************/

	/************************* �E�p�l��-�^�u�ݒu start *************************/

	private void settingTabData(boolean myTab){

		if (myTab){
			// MyTab
			jInnerPanelMyTab = new JPanel();
			jInnerPanelMyTab.setLayout(new GridBagLayout());
			jInnerPanelMyTab.setName("jInnerPanelMyTab");
			jInnerTranPanelMyTab = new JPanel();
			jInnerTranPanelMyTab.setLayout(new GridBagLayout());
			jInnerTranPanelMyTab.setName("jInnerTranPanelMyTab");
		}else{
			// ��{����
			jInnerPanelKenshin = new JPanel();
			jInnerPanelKenshin.setLayout(new GridBagLayout());
			jInnerPanelKenshin.setName("jInnerPanelKenshin");
			// del s.inoue 2011/11/18
//			jInnerPanelKenshin.setFocusable(true);

			jInnerTranPanelKenshin = new JPanel();
			jInnerTranPanelKenshin.setLayout(new GridBagLayout());
			jInnerTranPanelKenshin.setName("jInnerTranPanelKenshin");
			// del s.inoue 2011/11/18
//			jInnerTranPanelKenshin.setFocusable(true);

			// �ڍ׍���
			jInnerPanelSyosai = new JPanel();
			jInnerPanelSyosai.setLayout(new GridBagLayout());
			jInnerPanelSyosai.setName("jInnerPanelSyosai");
			jInnerTranPanelSyosai = new JPanel();
			jInnerTranPanelSyosai.setLayout(new GridBagLayout());
			jInnerTranPanelSyosai.setName("jInnerTranPanelSyosai");

			// �ǉ�����
			jInnerPanelTuika = new JPanel();
			jInnerPanelTuika.setLayout(new GridBagLayout());
			jInnerPanelTuika.setName("jInnerPanelTuika");
			jInnerTranPanelTuika = new JPanel();
			jInnerTranPanelTuika.setLayout(new GridBagLayout());
			jInnerTranPanelTuika.setName("jInnerTranPanelTuika");

			// ��f
			jInnerPanelMonshin = new JPanel();
			jInnerPanelMonshin.setLayout(new GridBagLayout());
			jInnerPanelMonshin.setName("jInnerPanelMonshin");
			jInnerTranPanelMonshin = new JPanel();
			jInnerTranPanelMonshin.setLayout(new GridBagLayout());
			jInnerTranPanelMonshin.setName("jInnerTranPanelMonshin");
		}

		int irowAll = 0;
		int preType = 0;
		int irow = 0;

		boolean lastflg = false;
		Hashtable<String, String> databaseItem = null;

		int recordCount = 0;
		if (myTab){
			recordCount = tableMyTabResult.size();
		}else{
			recordCount = tableResult.size();
		}

		// add s.inoue 2013/07/24
//		boolean flgm = false;
//		boolean flgh = false;

		// �ʏ�Tab
		for (int i = 0; i < recordCount; i++) {

			if (myTab){
				databaseItem  = tableMyTabResult.get(i);
			}else{
				databaseItem  = tableResult.get(i);
			}

			String koumokuCD = databaseItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
			String kekkaTi = databaseItem.get(JConstantString.COLUMN_NAME_KEKA_TI);

			// add s.inoue 2012/06/08
			System.out.println(koumokuCD + " " + kekkaTi );

			// ���^�{����A�ی��w�����x���͕ʏ���
			if (koumokuCD.equals(JConstantString.COMBO_METABO_CODE)){
				// eidt s.inoue 2013/07/24
//				flgm = true;
					posMetabo = i;
				if (kekkaTi.equals("")){
					jComboBox_MetaboHantei.setSelectedIndex(0);
				}else{
					// ��Y��:1 �\���R�Y��:2 ��Y��:3 ����s�\:4
					jComboBox_MetaboHantei.setSelectedIndex(Integer.valueOf(kekkaTi));
				}
			}else if (koumokuCD.equals(JConstantString.COMBO_HOKENSIDO_CODE)){
				// eidt s.inoue 2013/07/24
//				flgh = true;
					posHokensido = i;
				if (kekkaTi.equals("")){
					jComboBox_HokenSidouLevel.setSelectedIndex(0);
				}else{
					// �ϋɓI�x��:1 ���@�Â��x��:2 �Ȃ�:3 ����s�\:4
					jComboBox_HokenSidouLevel.setSelectedIndex(Integer.valueOf(kekkaTi));
				}
			}else{
				// �����̃f�[�^�̓ǂݍ��݂��s��
				// edit s.inoue 2012/05/30
				String typeStr=databaseItem.get(JConstantString.COLUMN_NAME_HISU_FLG);

				// add s.inoue 2012/05/30
				String myTabVal = "";
				if (typeStr.length() == 5){
					myTabVal = typeStr.substring(4, 5);
					typeStr = typeStr.substring(0, 4);
				}

				int type = Integer.valueOf(typeStr);
				if(type != preType )
					irow = 0;

				if (recordCount - 1 == i)
					lastflg = true;

				// edit s.inoue 2012/05/30
				// ���׏���
				if (!myTabVal.equals("")){
					setPanelDataGrid(Integer.valueOf(myTabVal),irow,irowAll,databaseItem,myTab,lastflg);
				}else{
					setPanelDataGrid(type,irow,irowAll,databaseItem,myTab,lastflg);
				}
				preType = type;
				irow++;
				irowAll++;
			}
		}

//		// add s.inoue 2013/07/24
//		if (!myTab && (!flgh || !flgm)){
//			posHokensido = 0;
//			posMetabo = 0;
//		}
		// �^�u���̃p�l���ݒ菈��
		setInnerTranPanel(myTab);
	}

	/*
	 * ���p�l���ւ̔z�u����
	 */
	private void setInnerTranPanel(boolean myTab){

		GridBagConstraints gridCtlTabs = new GridBagConstraints();
		gridCtlTabs.gridx = 0;
		gridCtlTabs.gridy = 1;
		gridCtlTabs.insets = new Insets(cntrolTop, controlLeft, 0, controlRight);
		gridCtlTabs.gridheight = 1;
		gridCtlTabs.weightx = 1.0d;
		gridCtlTabs.weighty = 1.0d;
		gridCtlTabs.fill = GridBagConstraints.BOTH;
		gridCtlTabs.anchor = GridBagConstraints.NORTHWEST;

		if (myTab){
			// MyTabF
			JPanel ajustmentMyTab = new JPanel();
			ajustmentMyTab.setLayout(new BorderLayout());
			ajustmentMyTab.add(jInnerTranPanelMyTab,BorderLayout.NORTH);
			JPanel ajustmentMyTab_left = new JPanel();
			ajustmentMyTab_left.setLayout(new BorderLayout());
			ajustmentMyTab_left.add(ajustmentMyTab,BorderLayout.WEST);

			JScrollPane scrollMyTab = new JScrollPane(ajustmentMyTab_left);
			scrollMyTab.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollMyTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMyTab.setPreferredSize(new Dimension(550,450));
			jScrollbarMyTab = scrollMyTab.getVerticalScrollBar();
			jScrollbarMyTab.setValue(jScrollbarMyTab.getMinimum());
			jInnerPanelMyTab.add(scrollMyTab, gridCtlTabs);
		}else{
			// ��{���f
			JPanel ajustmentKihon = new JPanel();
			ajustmentKihon.setLayout(new BorderLayout());
			ajustmentKihon.add(jInnerTranPanelKenshin,BorderLayout.NORTH);
			JPanel ajustmentKihon_left = new JPanel();
			ajustmentKihon_left.setLayout(new BorderLayout());
			ajustmentKihon_left.add(ajustmentKihon,BorderLayout.WEST);

			JScrollPane scrollKihon = new JScrollPane(ajustmentKihon_left);
			scrollKihon.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollKihon.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollKihon.setPreferredSize(new Dimension(550,450));
			jScrollbarKihon = scrollKihon.getVerticalScrollBar();
			jScrollbarKihon.setValue(jScrollbarKihon.getMinimum());
			jInnerPanelKenshin.add(scrollKihon, gridCtlTabs);

			// �ڍ׌��f
			JPanel ajustmentSyosai = new JPanel();
			ajustmentSyosai.setLayout(new BorderLayout());
			ajustmentSyosai.add(jInnerTranPanelSyosai,BorderLayout.NORTH);
			JPanel ajustmentSyosai_left = new JPanel();
			ajustmentSyosai_left.setLayout(new BorderLayout());
			ajustmentSyosai_left.add(ajustmentSyosai,BorderLayout.WEST);

			JScrollPane scrollSyosai = new JScrollPane(ajustmentSyosai_left);
			scrollSyosai.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollSyosai.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollSyosai.setPreferredSize(new Dimension(550,450));
			jScrollbarSyosai = scrollSyosai.getVerticalScrollBar();
			jScrollbarSyosai.setValue(jScrollbarSyosai.getMinimum());
			jInnerPanelSyosai.add(scrollSyosai, gridCtlTabs);

			// �ǉ����f
			JPanel ajustmentTuika = new JPanel();
			ajustmentTuika.setLayout(new BorderLayout());
			ajustmentTuika.add(jInnerTranPanelTuika,BorderLayout.NORTH);
			JPanel ajustmentTuika_left = new JPanel();
			ajustmentTuika_left.setLayout(new BorderLayout());
			ajustmentTuika_left.add(ajustmentTuika,BorderLayout.WEST);

			JScrollPane scrollTuika = new JScrollPane(ajustmentTuika_left);
			scrollTuika.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollTuika.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollTuika.setPreferredSize(new Dimension(550,450));
			jScrollbarTuika = scrollTuika.getVerticalScrollBar();
			jScrollbarTuika.setValue(jScrollbarTuika.getMinimum());
			jInnerPanelTuika.add(scrollTuika, gridCtlTabs);

			// ��f
			JPanel ajustmentMonshin = new JPanel();
			ajustmentMonshin.setLayout(new BorderLayout());
			ajustmentMonshin.add(jInnerTranPanelMonshin,BorderLayout.NORTH);
			JPanel ajustmentMonshin_left = new JPanel();
			ajustmentMonshin_left.setLayout(new BorderLayout());
			ajustmentMonshin_left.add(ajustmentMonshin,BorderLayout.WEST);

			JScrollPane scrollMonshin = new JScrollPane(ajustmentMonshin_left);
			scrollMonshin.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollMonshin.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMonshin.setPreferredSize(new Dimension(550,450));
			jScrollbarMonshin = scrollMonshin.getVerticalScrollBar();
			jScrollbarMonshin.setValue(jScrollbarMonshin.getMinimum());
			jInnerPanelMonshin.add(scrollMonshin, gridCtlTabs);
		}
	}

	/************************* �E�p�l��-�^�u�ݒu end *************************/

	/************************* ���̓t�B�[���h�ݒu start *************************/

	private void setPanelDataGrid(int type,final int irow,int irowAll,Hashtable<String, String> dbItem,boolean myTab,boolean lastFlg){

		GridBagConstraints[] gridCtl = null;

		gridCtl = new GridBagConstraints[8];

		for (int i = 0; i < 8; i++) {
			gridCtl[i] = new GridBagConstraints();
			gridCtl[i].gridx = i;
			gridCtl[i].gridy = irow;
			gridCtl[i].anchor = GridBagConstraints.NORTHWEST;
			gridCtl[i].insets = new Insets(cntrolTop, controlLeft, 0, 0);
		}

		// �w�b�_�����ړ�
		/* ���׍��� */
		JLabel jLabelKoumokuNm = new JLabel();
		// add s.inoue 2013/01/25
		// hba1c���
		String tmpCd = dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
		String tmpAdd = "";
		if (tmpCd.equals("3D045000001906202") || tmpCd.equals("3D045000001920402") || tmpCd.equals("3D045000001927102") || tmpCd.equals("3D045000001999902")){
			tmpAdd = "(JDS)";
		}else if (tmpCd.equals("3D046000001906202") || tmpCd.equals("3D046000001920402") || tmpCd.equals("3D046000001927102") || tmpCd.equals("3D046000001999902")){
			tmpAdd = "(NGSP)";
		}

		jLabelKoumokuNm.setText(dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME) + tmpAdd);
		jLabelKoumokuNm.setToolTipText(dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME));
		jLabelKoumokuNm.setPreferredSize(new Dimension(koumoku_nm_Width, controlLabelHeight));
		jLabelKoumokuNm.setFocusable(false);

		// add s.inoue 2012/05/30
		String hisu_flg = dbItem.get(JConstantString.COLUMN_NAME_HISU_FLG);
		Color koumoku_color = null;

		// add s.inoue 2012/05/30
		if (hisu_flg.length() == 5)
			hisu_flg = hisu_flg.substring(4, 5);
		if (hisu_flg.equals(JApplication.HISU_FLG_BASE)){
			koumoku_color = ViewSettings.getRequiedItemFrColor();
		}else if (hisu_flg.equals(JApplication.HISU_FLG_SYOSAI)){
			koumoku_color = ViewSettings.getSyosaiItemFrColor();
		}else if (hisu_flg.equals(JApplication.HISU_FLG_TUIKA) || hisu_flg.equals(JApplication.HISU_FLG_MONSHIN)){
			koumoku_color = ViewSettings.getTuikaItemFrColor();
		}
//		jLabelKoumokuNm.setBackground(koumoku_color);
		jLabelKoumokuNm.setForeground(koumoku_color);

		JLabel jLabelTani = new JLabel();
		jLabelTani.setPreferredSize(new Dimension(tani_Width, controlTextHeight));
		jLabelTani.setText(dbItem.get(JConstantString.COLUMN_NAME_TANI));
		jLabelTani.setFocusable(false);

		// 1:���� 2:�R�[�h 3:������
		String dataType = dbItem.get(JConstantString.COLUMN_NAME_DATA_TYPE);
		String koumokuCD = dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
		String koumokuNM = dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME);
		String kensaHouhou = dbItem.get(JConstantString.COLUMN_NAME_KENSA_HOUHOU);
		String kekaTi = dbItem.get(JConstantString.COLUMN_NAME_KEKA_TI);
		String hisTi = dbItem.get(JConstantString.COLUMN_NAME_HIS_TI);
		String hl = dbItem.get(JConstantString.COLUMN_NAME_H_L);
		String hantei = dbItem.get(JConstantString.COLUMN_NAME_HANTEI);
		String jisiKbn = dbItem.get(JConstantString.COLUMN_NAME_JISI_KBN);
		String maxLength = dbItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);

// del s.inoue 2012/07/13
//		boolean houhouflg = false;
//    	HashMap<String,ArrayList<String>> hm = JConstantString.getKensaHouhouMap(koumokuCD);
//    	String keyStr =(String)hm.keySet().toArray()[0];
//    	ArrayList<String> arr = hm.get(keyStr);
//    	if (arr != null)
//    		houhouflg = true;

		if (myTab){
			jCheckBox_2[irowAll] = new ExtendedCheckBox();
			jCheckBox_2[irowAll].setFocusable(false);
			jCheckBox_2[irowAll].setName(koumokuCD);
			// add s.inoue 2012/03/07
			jCheckBox_2[irowAll].setToolTipText("<html>�`�F�b�N�Ȃ��F���{<br>�`�F�b�N�F����s�\</html>");

			jCheckBox_2[irowAll].addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					Object source = e.getSource();

					if (source instanceof JCheckBox){
						JCheckBox chk = (JCheckBox)source;
//						ExtendedOpenCheckboxControl eChk = (ExtendedOpenCheckboxControl)chk.getParent();
//						if ((eChk == null))
//							return;

						// �^�u�ւ̘A������(defTab
						String koumokuCD = chk.getName();
						for (int i = 0; i < jCheckBox_1.length; i++) {
							if (jCheckBox_1[i] != null)
							if (jCheckBox_1[i].getName().equals(koumokuCD)){
								if (chk.isSelected()){
									jCheckBox_1[i].setSelected(true);
								}else{
									jCheckBox_1[i].setSelected(false);
								}
								break;
							}
						}
					}
				}
			});

//			// mytab����
//			jButton_2[irowAll] = new ExtendedButton();
//			jButton_2[irowAll].setText("���@");
//			jButton_2[irowAll].setToolTipText(kensaHouhou);
//			jButton_2[irowAll].setPreferredSize(new Dimension(kensahouhou_Width, controlButtonHeight));
//			jButton_2[irowAll].addActionListener(this);
//			jButton_2[irowAll].addKeyListener(this);
//			jButton_2[irowAll].setFocusable(false);
//			jButton_2[irowAll].setName(koumokuCD);

			// �^�C�v���̃t�B�[���h����
			if (dataType.equals(NUMBER_FIELD)){
				jTextField_2[irowAll] = new ExtendedOpenFormattedFloatControl(ImeMode.IME_OFF);
				jTextField_2[irowAll].setPreferredSize(new Dimension(kekka_Width, controlTextHeight));
				// eidt s.inoue 2012/07/13
				// jTextField_2[irowAll].setToolTipText(koumokuNM);
				jTextField_2[irowAll].setToolTipText(kensaHouhou);
				jTextField_2[irowAll].setName(koumokuCD);
				if (koumokuCD.equals(CODE_BMI)){
					jTextField_2[irowAll].setEnabled(false);
					BMIFormat =  maxLength;
				}
				jTextField_2[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {
					}
					@Override
					public void keyReleased(KeyEvent e) {
					}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
								jScrollbarMyTab.setValue(irow*forcus_range - forcus_back);
							}else{
								jScrollbarMyTab.setValue(irow*forcus_range - forcus_foward);
							}
						}
					}
				});
				jTextField_2[irowAll].addFocusListener(new FocusAdapter(){
					public void focusGained(FocusEvent e) {
						JTextField txt = (JTextField)e.getSource();

						for (int i = 0; i < jTextField_2.length; i++) {
							if ( jTextField_2[i]!= null){
								// eidt s.inoue 2012/02/24
								// if (txt.getParent().getName().equals(jTextField_2[i].getName())){
								if (txt.getName().equals(jTextField_2[i].getName())){
									String houhou =kensaHouhouCodeList.get(jTextField_2[i].getName());
									houhou = (houhou==null)?"":houhou;
									jLabel_KensaHouhou_Value.setText(houhou);
									((JTextField)e.getSource()).setBackground(JApplication.backColor_Focus);
								}
							}
						}
					}
					public void focusLost(FocusEvent e) {
						Object source = e.getSource();

						if (source instanceof JTextField){
							JTextField txt = (JTextField)source;
							// eidt s.inoue 2012/02/24
							// ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt.getParent();
							ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt;

							// add s.inoue 2012/07/13
							jLabel_KensaHouhou_Value.setText("");

							if ((eTxt == null))
								return;
							// add s.inoue 2012/02/02
							((JTextField)e.getSource()).setBackground(JApplication.backColor_UnFocus);

							// �����_����(myTab
							for (int i = 0; i < jTextField_2.length; i++) {
								if (jTextField_2[i]==null)continue;
								if (eTxt.getName().equals(jTextField_2[i].getName())){
									editingMyTabStopped(i,eTxt.getText());
								}
								// BMI �����v�Z
								if (eTxt.getName().equals(CODE_HIGHT)){
									height = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}
								}
								if (eTxt.getName().equals(CODE_WEIGHT)){
									weight = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}
								}
								// eidt s.inoue 2013/04/01
//								if (jTextField_2[i].getName().equals(CODE_BMI) &&
//										!kensaKekka.equals(""))
//									jTextField_2[i].setText(kensaKekka);
								if (jTextField_2[i].getName().equals(CODE_BMI) &&
										!kensaKekka.equals("")){
									jTextField_2[i].setText(kensaKekka);

									// bmi �A�g���� ver2.0.3
									for (int ii = 0; i < jTextField_1.length; ii++) {
										if (jTextField_1[ii] != null){
										if (jTextField_1[ii].getName().equals(CODE_BMI)){
											jTextField_1[ii].setText(kensaKekka);
											break;
										}
										}
									}
								}

							}

							// add s.inoue 2013/02/27
							if (height.equals("") || weight.equals("")){
								for (int i = 0; i < jTextField_2.length; i++) {
									if (jTextField_2[i] != null){
										if (jTextField_2[i].getName().equals(CODE_BMI)){
											jTextField_2[i].setText("");
											break;
										}
									}
								}
							}

							// �^�u�ւ̘A������(defTab
							String koumokuCD = eTxt.getName();

							// eidt s.inoue 2013/04/11
							if (!eTxt.getText().equals(""))
							for (int i = 0; i < jTextField_1.length; i++) {
								if (jTextField_1[i] != null)
								if (jTextField_1[i].getName().equals(koumokuCD))
									jTextField_1[i].setText(eTxt.getText());
							}
						}
					}

					/**
					 * BMI�l���v�Z����
					 */
					public String calcBMI(String height, String weight) {
						// �ǂ��炩����l�̏ꍇ�͋�l��Ԃ�
						if (height.isEmpty() || weight.isEmpty()) {
							return "";
						}

						// �O���Z�G���[��h��
						if (Double.valueOf(height) == 0) {
							return "";
						}

						BigDecimal bmi = new BigDecimal(String.valueOf((Double.valueOf(weight) /((Double.valueOf(height) / 100) * (Double.valueOf(height) / 100)))));
						return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
					}
				});

			}else if (dataType.equals(CODE_FIELD)){
				jComboBox_2[irowAll] = new ExtendedComboBox(ImeMode.IME_OFF);
// eidt s.inoue 2012/02/23 �p�~
//				jComboBox_2[irowAll].setAttributeName(koumokuNM);
//				jComboBox_2[irowAll].setDomainId(koumokuCD);
//				jComboBox_2[irowAll].setCanCopy(true);
//				jComboBox_2[irowAll].setRequired(true);
				jComboBox_2[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight));
				jComboBox_2[irowAll].setName(koumokuCD);
				jComboBox_2[irowAll].setSize(new Dimension(kekka_Width*20, controlTextHeight));

				// �������������O�\�[�X
				// �R�[�h�̏ꍇ
				ArrayList<Hashtable<String, String>> codeResult = null;
				Hashtable<String, String> codeResultItem;
				String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
						+ JQueryConvert.queryConvert(koumokuCD));
				try {
					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				// �����͂��I�ׂ�悤�ɂ���
				jComboBox_2[irowAll].addItem("");
				for (int j = 0; j < codeResult.size(); j++) {
					codeResultItem = codeResult.get(j);
					jComboBox_2[irowAll].addItem(codeResultItem.get("CODE_NAME"));
				}

				// �������������O�\�[�X
				jComboBox_2[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
								jScrollbarMyTab.setValue(irow*forcus_range - forcus_back);
							}else{
								jScrollbarMyTab.setValue(irow*forcus_range - forcus_foward);
							}
						}
					}
				});

				// �X�e�[�^�X�\������
				jComboBox_2[irowAll].addFocusListener(new FocusAdapter(){
					public void focusGained(FocusEvent e) {
						JComboBox cmb = (JComboBox)e.getSource();
							for (int i = 0; i < jComboBox_2.length; i++) {
								if ( jComboBox_2[i]!= null){
									if (cmb.getParent().getName().equals(jComboBox_2[i].getName())){
										String houhou = kensaHouhouCodeList.get(jComboBox_2[i].getName());
										houhou = (houhou==null)?"":houhou;
										jLabel_KensaHouhou_Value.setText(houhou);
//										jScrollbarMyTab.setValue(irow*forcus_Range);
									}
								}
							}
					}
					public void focusLost(FocusEvent e) {
						Object source = e.getSource();

						if (source instanceof JComboBox){
							// JComboBox txt = (JComboBox)source;
							// eidt s.inoue 2012/02/24
							// ExtendedOpenComboboxControl eCmb = (ExtendedOpenComboboxControl)txt.getParent();
							JComboBox eCmb = (JComboBox)source;
						if ((eCmb == null))
							return;

						// eidt s.inoue 2013/04/11
						if (eCmb.getSelectedIndex() > 0)
						for (int i = 0; i < jComboBox_1.length; i++) {
							if (jComboBox_1[i] != null)
							if (jComboBox_1[i].getName().equals(eCmb.getName()))
								jComboBox_1[i].setSelectedIndex(eCmb.getSelectedIndex());
						}
						}

					}
				});
			}else if (dataType.equals(TEXT_FIELD)){
				jTextAreaField_2[irowAll] = new ExtendedTextArea(ImeMode.IME_ZENKAKU);
				// del s.inoue 2013/02/18
				// jTextAreaField_2[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));
				jTextAreaField_2[irowAll].setName(koumokuCD);
				jTextAreaField_2[irowAll].addKeyListener(this);

				// add s.inoue 2013/02/14
				jTextScrollPane_2[irowAll] = new JScrollPane();
				jTextScrollPane_2[irowAll].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jTextScrollPane_2[irowAll].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				jTextScrollPane_2[irowAll].setViewportView(jTextAreaField_2[irowAll]);
				jTextScrollPane_2[irowAll].setAutoscrolls(true);
				// jTextScrollPane_2[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));

				jTextAreaField_2[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
								jScrollbarMyTab.setValue(irow*forcus_range - forcus_back);
							}else{
								jScrollbarMyTab.setValue(irow*forcus_range - forcus_foward);
							}
						}
					}
				});
				jTextAreaField_2[irowAll].addFocusListener(new FocusListener() {
					@Override
					public void focusGained(FocusEvent e) {
						((JTextArea)e.getSource()).setBackground(JApplication.backColor_Focus);
					}
					@Override
					public void focusLost(FocusEvent e) {
						((JTextArea)e.getSource()).setBackground(JApplication.backColor_UnFocus);
						JTextArea txt = (JTextArea)e.getSource();

						// eidt s.inoue 2013/04/11
						if (!txt.getText().equals(""))
						for (int i = 0; i < jTextAreaField_1.length; i++) {
							if (jTextAreaField_1[i] != null)
							if (jTextAreaField_1[i].getName().equals(txt.getName()))
								jTextAreaField_1[i].setText(((String)txt.getText()));
						}

					}
				});

			}
		}else{
			// defTab
			jCheckBox_1[irowAll] = new ExtendedCheckBox();
			jCheckBox_1[irowAll].setFocusable(false);
			jCheckBox_1[irowAll].setName(koumokuCD);
			// add s.inoue 2012/03/07
			jCheckBox_1[irowAll].setToolTipText("<html>�`�F�b�N�Ȃ��F���{<br>�`�F�b�N�F����s�\</html>");
			// 0:�����{,1:���{,2:����s�\
			if (jisiKbn.equals("2"))
				jCheckBox_1[irowAll].setSelected(true);

//			jButton_1[irowAll] = new ExtendedButton();
//			jButton_1[irowAll].setText("���@");
//			jButton_1[irowAll].setToolTipText(kensaHouhou);
//			jButton_1[irowAll].setPreferredSize(new Dimension(kensahouhou_Width, controlButtonHeight));
//			jButton_1[irowAll].addActionListener(this);
//			// jButton_1[irowAll].addKeyListener(this);
//			jButton_1[irowAll].setFocusable(false);
//			jButton_1[irowAll].setName(koumokuCD);
//			jButton_1[irowAll].setActionCommand(String.valueOf(irowAll));

			// �^�C�v���̃t�B�[���h����
			if (dataType.equals(NUMBER_FIELD)){
				jTextField_1[irowAll] = new ExtendedOpenFormattedFloatControl(ImeMode.IME_OFF);

				jTextField_1[irowAll].setPreferredSize(new Dimension(kekka_Width, controlTextHeight));
				// eidt s.inoue 2012/07/13
				// jTextField_1[irowAll].setToolTipText(koumokuNM);
				jTextField_1[irowAll].setToolTipText(kensaHouhou);
				jTextField_1[irowAll].setName(koumokuCD);
				jTextField_1[irowAll].setText(kekaTi);
				// jTextField_1[irowAll].addKeyListener(this);
				if (koumokuCD.equals(CODE_BMI)){
					jTextField_1[irowAll].setEnabled(false);
					BMIFormat =  maxLength;
				// add s.inoue 2013/01/29
				} else if(koumokuCD.equals(CODE_HIGHT)){
					height =kekaTi;
				} else if(koumokuCD.equals(CODE_WEIGHT)){
					weight =kekaTi;
				}

				jTextField_1[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {
					}
					@Override
					public void keyReleased(KeyEvent e) {
					}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();

							String title = jPanelRegisterCenter.getTitleAt(jPanelRegisterCenter.getSelectedIndex());
							int diff = 0;
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
								diff = forcus_back;
							}else{
								diff = forcus_foward;
							}

							if (title.equals("��{���f")){
								jScrollbarKihon.setValue(irow*forcus_range - diff);
							}else if (title.equals("�ڍ׌��f")){
								jScrollbarSyosai.setValue(irow*forcus_range - diff);
							}else if (title.equals("�ǉ����f")){
								jScrollbarTuika.setValue(irow*forcus_range - diff);
							}else if (title.equals("��f��")){
								jScrollbarMonshin.setValue(irow*forcus_range - diff);
							}

						}
					}
				});

				jTextField_1[irowAll].addFocusListener(new FocusAdapter(){
					public void focusGained(FocusEvent e) {
						JTextField txt = (JTextField)e.getSource();

						for (int i = 0; i < jTextField_1.length; i++) {
							if ( jTextField_1[i]!= null){
								// eidt s.inoue 2012/02/24
								// if (txt.getParent().getName().equals(jTextField_1[i].getName())){
								if (txt.getName().equals(jTextField_1[i].getName())){
									String houhou =kensaHouhouCodeList.get(jTextField_1[i].getName());
									houhou = (houhou==null)?"":houhou;
									jLabel_KensaHouhou_Value.setText(houhou);
									((JTextField)e.getSource()).setBackground(JApplication.backColor_Focus);
								}
							}
						}
					}
					public void focusLost(FocusEvent e) {
						// eidt s.inoue 2012/02/14
						JFormattedTextField txt = (JFormattedTextField)e.getSource();
						// JTextField txt = (JTextField)e.getSource();
						// ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt.getParent();
						ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt;

						// add s.inoue 2012/07/13
						jLabel_KensaHouhou_Value.setText("");

						if ((eTxt == null))
							return;
						// edit s.inoue 2012/02/02
						// ((JTextArea)e.getSource()).setBackground(JApplication.backColor_UnFocus);
						((JTextField)e.getSource()).setBackground(JApplication.backColor_UnFocus);

						for (int i = 0; i < jTextField_1.length; i++) {
							if ( jTextField_1[i]!= null){

								if (eTxt.getName().equals(jTextField_1[i].getName())){
									editingStopped(i,eTxt.getText());
								}
								// BMI �����v�Z
								if (eTxt.getName().equals(CODE_HIGHT)){
									height = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}

								}
								if (eTxt.getName().equals(CODE_WEIGHT)){
									weight = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}
								}
								// eidt s.inoue 2011/12/09
								if (jTextField_1[i].getName().equals(CODE_BMI) &&
										!kensaKekka.equals("")){
									jTextField_1[i].setText(kensaKekka);
									break;
								}
							}
						}
						// add s.inoue 2013/02/27
						if (height.equals("") || weight.equals("")){
							for (int i = 0; i < jTextField_1.length; i++) {
								if (jTextField_1[i] != null){
									if (jTextField_1[i].getName().equals(CODE_BMI)){
										jTextField_1[i].setText("");
										break;
									}
								}
							}
						}
					}

					/**
					 * BMI�l���v�Z����
					 */
					public String calcBMI(String height, String weight) {
						// �ǂ��炩����l�̏ꍇ�͋�l��Ԃ�
						if (height.isEmpty() || weight.isEmpty()) {
							return "";
						}

						// �O���Z�G���[��h��
						if (Double.valueOf(height) == 0) {
							return "";
						}

						BigDecimal bmi = new BigDecimal(String.valueOf((Double.valueOf(weight) /((Double.valueOf(height) / 100) * (Double.valueOf(height) / 100)))));
						return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
					}

				});
			/* �R�[�h�t�B�[���h�ݒ� */
			}else if (dataType.equals(CODE_FIELD)){
				jComboBox_1[irowAll] = new ExtendedComboBox(ImeMode.IME_OFF);
// del s.inoue 2012/02/17 1��
//				jComboBox_1[irowAll].setAttributeName(koumokuNM);
//				jComboBox_1[irowAll].setDomainId(koumokuCD);
//				jComboBox_1[irowAll].setCanCopy(true);
				// jComboBox_1[irowAll].setRequired(true);
				jComboBox_1[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight));
				jComboBox_1[irowAll].setName(koumokuCD);
				jComboBox_1[irowAll].setSize(new Dimension(kekka_Width*20, controlTextHeight));
				// del s.inoue 2012/02/17 1��
//				if (!kekaTi.equals(""))
//					jComboBox_1[irowAll].setSelectedIndex(Integer.parseInt(kekaTi)-1);

				// �������������O�\�[�X
				// �R�[�h�̏ꍇ
				ArrayList<Hashtable<String, String>> codeResult = null;
				Hashtable<String, String> codeResultItem;
				String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
						+ JQueryConvert.queryConvert(koumokuCD));
				try {
					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				// �����͂��I�ׂ�悤�ɂ���
				jComboBox_1[irowAll].addItem("");
				for (int j = 0; j < codeResult.size(); j++) {
					codeResultItem = codeResult.get(j);
					jComboBox_1[irowAll].addItem(codeResultItem.get("CODE_NAME"));
				}

				// eidt s.inoue 2012/02/21
				if (!kekaTi.equals("")){
					// eidt s.inoue 2012/12/27
					// index���Ƃ����(�͂� 0 or 1������)
					for (int i = 0; i < jComboBox_1[irowAll].getItemCount(); i++) {
						String item = jComboBox_1[irowAll].getItemAt(i).toString();
						if (!item.equals(""))
							item = item.substring(0, 1);
						if (kekaTi.equals(item))
							jComboBox_1[irowAll].setSelectedIndex(i);
					}
					// jComboBox_1[irowAll].setSelectedIndex(Integer.parseInt(kekaTi));
				}

				// �������������O�\�[�X

				// add s.inoue 2012/02/14
				jComboBox_1[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();

							String title = jPanelRegisterCenter.getTitleAt(jPanelRegisterCenter.getSelectedIndex());
							int diff = 0;
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
								diff = forcus_back;
							}else{
								diff = forcus_foward;
							}

							if (title.equals("��{���f")){
								jScrollbarKihon.setValue(irow*forcus_range - diff);
							}else if (title.equals("�ڍ׌��f")){
								jScrollbarSyosai.setValue(irow*forcus_range - diff);
							}else if (title.equals("�ǉ����f")){
								jScrollbarTuika.setValue(irow*forcus_range - diff);
							}else if (title.equals("��f��")){
								jScrollbarMonshin.setValue(irow*forcus_range - diff);
							}
						}
					}
				});

				// �X�e�[�^�X�\������
				jComboBox_1[irowAll].addFocusListener(new FocusAdapter(){
					public void focusGained(FocusEvent e) {
						JComboBox cmb = (JComboBox)e.getSource();
							for (int i = 0; i < jComboBox_1.length; i++) {
								if ( jComboBox_1[i]!= null){
									if (cmb.getParent().getName().equals(jComboBox_1[i].getName())){
										String houhou = kensaHouhouCodeList.get(jComboBox_1[i].getName());
										houhou = (houhou==null)?"":houhou;
										jLabel_KensaHouhou_Value.setText(houhou);
									}
								}
							}
					}
					public void focusLost(FocusEvent e) {
					}
				});
			}else if (dataType.equals(TEXT_FIELD)){
				jTextAreaField_1[irowAll] = new ExtendedTextArea(ImeMode.IME_ZENKAKU);
				// eidt s.inoue 2012/03/08
				// del s.inoue 2013/02/18
				// jTextAreaField_1[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));
				jTextAreaField_1[irowAll].setName(koumokuCD);
				jTextAreaField_1[irowAll].addKeyListener(this);
				jTextAreaField_1[irowAll].setText(kekaTi);

				// add s.inoue 2013/02/14
				jTextScrollPane_1[irowAll] = new JScrollPane();
				jTextScrollPane_1[irowAll].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jTextScrollPane_1[irowAll].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				jTextScrollPane_1[irowAll].setViewportView(jTextAreaField_1[irowAll]);
				jTextScrollPane_1[irowAll].setAutoscrolls(true);

				// eidt s.inoue 2013/02/15
				// jTextScrollPane_1[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));

				// add s.inoue 2012/02/14
				jTextAreaField_1[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
//							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								switch (jPanelRegisterCenter.getSelectedIndex()) {
//								case 1:
//									jScrollbarKihon.setValue(irow*forcus_range - forcus_back);break;
//								case 2:
//									jScrollbarSyosai.setValue(irow*forcus_range - forcus_back);break;
//								case 3:
//									jScrollbarTuika.setValue(irow*forcus_range - forcus_back);break;
//								case 4:
//									jScrollbarMonshin.setValue(irow*forcus_range - forcus_back);break;
//								}
//							}else{
//								switch (jPanelRegisterCenter.getSelectedIndex()) {
//								case 1:
//									jScrollbarKihon.setValue(irow*forcus_range - forcus_foward);break;
//								case 2:
//									jScrollbarSyosai.setValue(irow*forcus_range - forcus_foward);break;
//								case 3:
//									jScrollbarTuika.setValue(irow*forcus_range - forcus_foward);break;
//								case 4:
//									jScrollbarMonshin.setValue(irow*forcus_range - forcus_foward);break;
//								}
//							}

							String title = jPanelRegisterCenter.getTitleAt(jPanelRegisterCenter.getSelectedIndex());
							int diff = 0;
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
								diff = forcus_back;
							}else{
								diff = forcus_foward;
							}

							if (title.equals("��{���f")){
								jScrollbarKihon.setValue(irow*forcus_range - diff);
							}else if (title.equals("�ڍ׌��f")){
								jScrollbarSyosai.setValue(irow*forcus_range - diff);
							}else if (title.equals("�ǉ����f")){
								jScrollbarTuika.setValue(irow*forcus_range - diff);
							}else if (title.equals("��f��")){
								jScrollbarMonshin.setValue(irow*forcus_range - diff);
							}
						}
					}
				});

				jTextAreaField_1[irowAll].addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent e) {
						((JTextArea)e.getSource()).setBackground(JApplication.backColor_UnFocus);
					}
					@Override
					public void focusGained(FocusEvent e) {
				    	((JTextArea)e.getSource()).setBackground(JApplication.backColor_Focus);
					}
				});

			}
		}

		// ���ʒl�t�B�[���h
		JLabel jLabelZenkai = new JLabel();
		jLabelZenkai.setPreferredSize(new Dimension(zenkai_Width, controlTextHeight));
		jLabelZenkai.setText(hisTi);
		jLabelZenkai.setToolTipText("�O��l:" + hisTi);
		jLabelZenkai.setFocusable(false);

		JLabel jLabelHL = new JLabel();
		jLabelHL.setPreferredSize(new Dimension(hl_Width, controlTextHeight));
		jLabelHL.setFocusable(false);
		jLabelHL.setText(hl);
		jLabelHL.setToolTipText("HL����:" + hl);

		JLabel jLabelHantei = new JLabel();
		jLabelHantei.setPreferredSize(new Dimension(hantei_Width, controlTextHeight));
		jLabelHantei.setFocusable(false);
		jLabelHantei.setText(hantei);
		jLabelHantei.setToolTipText("����:" + hantei);

		// defTab
		if (!myTab){
			// ����s�\,���ږ�,�������@,����(���l�A�R�[�h�A����),����(�R�����g)
			// type == �K�{�t���O
			switch (type) {
			case 1:
				jInnerTranPanelKenshin.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelKenshin.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelKenshin.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelKenshin.add(jTextField_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelKenshin.add(jComboBox_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelKenshin.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelKenshin.add(jTextScrollPane_1[irowAll], gridCtl[3]);
				}
				jInnerTranPanelKenshin.add(jLabelZenkai, gridCtl[4]);
//				// �������@����
//				if(houhouflg)
//					jInnerTranPanelKenshin.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelKenshin.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelKenshin.add(jLabelHantei, gridCtl[7]);
				break;
			case 2:
				jInnerTranPanelSyosai.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelSyosai.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelSyosai.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelSyosai.add(jTextField_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelSyosai.add(jComboBox_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelSyosai.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelSyosai.add(jTextScrollPane_1[irowAll], gridCtl[3]);
				}
				jInnerTranPanelSyosai.add(jLabelZenkai, gridCtl[4]);
//				// �������@����
//				if(houhouflg)
//					jInnerTranPanelSyosai.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelSyosai.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelSyosai.add(jLabelHantei, gridCtl[7]);
				break;
			case 3:
				jInnerTranPanelTuika.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelTuika.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelTuika.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelTuika.add(jTextField_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelTuika.add(jComboBox_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelTuika.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelTuika.add(jTextScrollPane_1[irowAll], gridCtl[3]);
				}
				jInnerTranPanelTuika.add(jLabelZenkai, gridCtl[4]);
//				// �������@����
//				if(houhouflg)
//					jInnerTranPanelTuika.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelTuika.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelTuika.add(jLabelHantei, gridCtl[7]);

				break;
			case 4 :
				jInnerTranPanelMonshin.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelMonshin.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelMonshin.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelMonshin.add(jTextField_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelMonshin.add(jComboBox_1[irowAll], gridCtl[3]);
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelMonshin.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelMonshin.add(jTextScrollPane_1[irowAll], gridCtl[3]);
				}
				jInnerTranPanelMonshin.add(jLabelZenkai, gridCtl[4]);
//				// �������@����
//				if(houhouflg)
//					jInnerTranPanelMonshin.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelMonshin.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelMonshin.add(jLabelHantei, gridCtl[7]);
				break;
			}
		}else{
			jInnerTranPanelMyTab.add(jCheckBox_2[irowAll],gridCtl[0]);
			jInnerTranPanelMyTab.add(jLabelKoumokuNm, gridCtl[1]);
			jInnerTranPanelMyTab.add(jLabelTani, gridCtl[2]);
			if (dataType.equals(NUMBER_FIELD)){
				jInnerTranPanelMyTab.add(jTextField_2[irowAll], gridCtl[3]);
			}else if (dataType.equals(CODE_FIELD)){
				jInnerTranPanelMyTab.add(jComboBox_2[irowAll], gridCtl[3]);
			}else if (dataType.equals(TEXT_FIELD)){
				// eidt s.inoue 2013/02/15
				// jInnerTranPanelMyTab.add(jTextAreaField_2[irowAll], gridCtl[3]);
				jInnerTranPanelMyTab.add(jTextScrollPane_2[irowAll], gridCtl[3]);
			}
			jInnerTranPanelMyTab.add(jLabelZenkai, gridCtl[4]);
//			// �������@����
//			if(houhouflg)
//				jInnerTranPanelMyTab.add(jButton_2[irowAll], gridCtl[5]);
			jInnerTranPanelMyTab.add(jLabelHL, gridCtl[6]);
			jInnerTranPanelMyTab.add(jLabelHantei, gridCtl[7]);
			// clearButton
			if (lastFlg){
				GridBagConstraints gridButton = new GridBagConstraints();
				gridButton.gridx = 1;
				gridButton.gridy = irow+1;
				gridButton.gridwidth = 1;
				gridButton.gridheight = 1;
				gridButton.anchor = GridBagConstraints.NORTHWEST;
				gridButton.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// del s.inoue 2012/02/09
//				jButton_mytabClear = new ExtendedButton();
//				jButton_mytabClear.setText("�N���A");
//				jButton_mytabClear.setToolTipText("�N���A");
//				jButton_mytabClear.setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight*2));
//				jButton_mytabClear.addActionListener(this);
//				jInnerTranPanelMyTab.add(jButton_mytabClear, gridButton);
			}
		}
	}
	/************************* ���̓t�B�[���h�ݒu end *************************/

	/************************* ���̓t�B�[���h���� start *************************/
	/*
	 * �ҏW�����C�x���g�R�[���o�b�N
	 */
	public void editingStopped(int irow,String txt) {

		// ���Ӂj���W�b�N�s���A�ʒu�����p
		// eidt s.inoue 2013/07/10
		int choseiRow = irow;
		if (irow > posHokensido){
			choseiRow += 1;
		}
		if (irow > posMetabo){
			choseiRow += 1;
		}
//		if ((irow > posHokensido) &&
//			(irow > posMetabo))
//				choseiRow += 2;

		// �t�H�[�}�b�g��������擾����
		Map<String, String> recordMap = tableResult.get(choseiRow);
		String format = recordMap.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);
		// add s.inoue 2013/02/12
		String roundUpValue = textTabFormat(recordMap,irow,format,txt);
		// �l�̌ܓ��l���Z���ɃZ�b�g
		jTextField_1[irow].setText(roundUpValue);
	}

	/*
	 * �ҏW�����C�x���g�R�[���o�b�N(Mytab
	 */
	public void editingMyTabStopped(int irow,String txt) {

		// �t�H�[�}�b�g��������擾����
		Map<String, String> recordMap = tableMyTabResult.get(irow);
		String format = recordMap.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);
		// add s.inoue 2013/02/12
		String roundUpValue = textTabFormat(recordMap,irow,format,txt);
		// �l�̌ܓ��l���Z���ɃZ�b�g
		jTextField_2[irow].setText(roundUpValue);
	}

	/*
	 * �t�H�[�}�b�g����
	 */
	private String textTabFormat(Map<String, String> recordMap,int irow,String format,String txt){
		// �t�H�[�}�b�g������̑Ó���������
		if (!isValidFormat(format))
			return "";

		// add s.inoue 2012/07/02
		// �Ή���������
		// �B�e�N����
		boolean dayformat = false;
		if (recordMap.get("KOUMOKUCD").equals("9N211161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N226161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N251161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N256161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N261161100000049") ||
				recordMap.get("KOUMOKUCD").equals("Z9N21161100000049") ||
				recordMap.get("KOUMOKUCD").equals("Z9N22161100000049") ||
				recordMap.get("KOUMOKUCD").equals("Z9N26161100000049") ||
				recordMap.get("KOUMOKUCD").equals("ZI210161100000049"))
				dayformat = true;

		// �t�H�[�}�b�g������̍쐬
		int width = getWidth(format);
		int scale = getScale(format);
		String formatStr = "%." + scale + "f";

		String roundUpValue = "";
		try {

// eidt s.inoue 2013/02/12
//			double value = new Double(txt)
//					.doubleValue();
//			// �����_��2�ʈȉ����l�̌ܓ��������_��1�ʂ܂ł�\��
//			roundUpValue = String.format(formatStr, new BigDecimal(
//					value).setScale(scale + 1,
//					BigDecimal.ROUND_HALF_UP).floatValue());

			// �����_��2�ʈȉ����l�̌ܓ��������_��1�ʂ܂ł�\��
			if (dayformat){
				String value = new String(txt);
				roundUpValue = String.format(formatStr, new BigDecimal(
						value));
			}else{
				double value = new Double(txt).doubleValue();
				roundUpValue = String.format(formatStr, new BigDecimal(
						value).setScale(scale + 1,
								BigDecimal.ROUND_HALF_UP).floatValue());
			}

			// �������E���������w�茅�����I�[�o�[���Ă���ꍇ�̓N���A
			if ((getWidth(roundUpValue) > width)
					|| (getScale(roundUpValue) > scale))
				roundUpValue = "";
		} catch (NumberFormatException e) {
		}

		return roundUpValue;
	}

	private final String DIGIT_FORMAT_REGEXP = "[#\\.0]+";
	/*
	 * �Ó��ȃt�H�[�}�b�g�����񂩌��؂���
	 */
	public boolean isValidFormat(String format) {

		if ((format == null) || (format.length() < 1))
			return false;

		Pattern pattern = Pattern.compile(DIGIT_FORMAT_REGEXP);
		if (!pattern.matcher(format).matches())
			return false;
		int pos = format.indexOf(".");
		if (pos == (format.length() - 1))
			return false;

		// �����_�̎w�肪�����ꍇ
		if (pos < 0) {
			pattern = Pattern.compile("#+");
			return pattern.matcher(format).matches();
		}

		pos++;
		pattern = Pattern.compile("0+");
		return pattern.matcher(
				format.substring(pos, format.length())).matches();
	}
	/*
	 * �������̌������擾����
	 */
	public int getWidth(String format) {
		if ((format == null) || (format.length() == 0))
			return -1;
		int pos = format.indexOf(".");
		if (pos < 0)
			return format.length();
		if ((pos == 0) || (pos == (format.length() - 1)))
			return -1;
		return format.substring(0, pos).length();
	}

	/*
	 * �������̌������擾����
	 */
	public int getScale(String format) {
		int pos = format.indexOf(".");
		if (pos < 0)
			return 0;
		if ((pos == 0) || (pos == (format.length() - 1)))
			return -1;
		pos++;
		return format.substring(pos, format.length()).length();
	}

	// add s.inoue 2012/04/24
	// �ėL����
	@Override
	public void keyPressed(KeyEvent keyEvent) {

    	Object obj = keyEvent.getSource();

    	if (obj instanceof ExtendedTextArea ){
			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
				// keyEvent.getKeyText(keyCode)
				// Shift�L�[���ꏏ�ɉ����ꂽ��?
				int modfiersKey = keyEvent.getModifiersEx();

				if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0){
					return;
				}else{

					ExtendedTextArea txtArea = (ExtendedTextArea)obj;
					String kekkaMojiretsu = txtArea.getText();

					try {
						if (dialogs == null){
							dialogs = JKekkaRegisterInputDialogFactory.createDialogComment(this,kekkaMojiretsu);
						}else{
							// "����(������)"���̓_�C�A���O�̃e�L�X�g�G���A�ɑI���Z���f�[�^���Z�b�g
							dialogs.setCommentText(kekkaMojiretsu);
						}
						dialogs.setVisible(true);
						if (dialogs.getStatus() == RETURN_VALUE.YES){
							// "����(������)"���̓_�C�A���O������͂��ꂽ�������I���Z���ɃZ�b�g
							txtArea.setText(dialogs.getText());
							txtArea.setToolTipText(dialogs.getText());
						}
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}
			}
		}
	}

    /************************* ���̓t�B�[���h���� end *************************/

	/************************* SQL���� start *************************/

	/*
	 * ���f���(1:��{���f,2:�ڍ׌��f,3:�ǉ����f,4��f)���Ǝ擾
	 */
	private String createCellDataSql(){

		List kinouCd = Arrays.asList(JConstantString.codesSeikatuKinou);
		List monshinCd = Arrays.asList(JConstantString.codesSeikatuMonshin);

		// del s.inoue 2012/11/07
		// List monshinHisuCd = Arrays.asList(JConstantString.codesSeikatuMonshinHisu);

		String strKoumokuCd = "";
		StringBuilder strsubSQL = new StringBuilder();
//		StringBuilder strSeikatu = new StringBuilder();
//		StringBuilder strWhere = new StringBuilder();

//		strsubSQL = " '9999' AS HISU_FLG ";
		strsubSQL.append("case ");
		// �����@�\�]��
		for (int i = 0 ;i<kinouCd.size();i++){
			strKoumokuCd = (String)kinouCd.get(i);
			strsubSQL.append(" when master.KOUMOKU_CD ='" + strKoumokuCd +"' then '4' ");
		}
		// ��f����
		for (int i = 0 ;i<monshinCd.size();i++){
			strKoumokuCd = (String)monshinCd.get(i);
			strsubSQL.append(" when master.KOUMOKU_CD ='" + strKoumokuCd +"' then '4' ");
		}

		strsubSQL.append(" else master.HISU_FLG end as HISU_FLG ");

		// �����̌������@�}�[�W����
//		strWhere.append(" AND master.KOUMOKU_CD <> '9N016160200000001' AND master.KOUMOKU_CD <> '9N016160300000001'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '9A752000000000001' AND master.KOUMOKU_CD <> '9A755000000000001'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '9A762000000000001' AND master.KOUMOKU_CD <> '9A765000000000001'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F050000002327201' AND master.KOUMOKU_CD <> '3F050000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F015000002327201' AND master.KOUMOKU_CD <> '3F015000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F070000002327201' AND master.KOUMOKU_CD <> '3F070000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F077000002327201' AND master.KOUMOKU_CD <> '3F077000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3J010000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3B035000002399901' AND master.KOUMOKU_CD <> '3B045000002327201'  AND master.KOUMOKU_CD <> '3B045000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3B090000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3B070000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3C015000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3C020000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3A010000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3A015000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5C095000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3D010000002227101' AND master.KOUMOKU_CD <> '3D010000001927201' AND master.KOUMOKU_CD <> '3D010000001999901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3D010129902227101' AND master.KOUMOKU_CD <> '3D010129901927201' AND master.KOUMOKU_CD <> '3D010129901999901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3D045000001920402' AND master.KOUMOKU_CD <> '3D045000001927102' AND master.KOUMOKU_CD <> '3D045000001999902'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '1A020000000190111'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '1A010000000190111' AND master.KOUMOKU_CD <> '1A100000000191111' AND master.KOUMOKU_CD <> '1A100000000190111'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '1A030000000199901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '9N221160700000011' AND master.KOUMOKU_CD <> '9N221160800000049' AND master.KOUMOKU_CD <> '9N226161100000049' AND master.KOUMOKU_CD <> '9N226161200000049'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5C070000002306301' AND master.KOUMOKU_CD <> '5C070000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5H010000001999911'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5H020000001999911'");

//		strSeikatu = new StringBuilder();
//
//		strSeikatu.append(" SELECT master.XMLITEM_SEQNO,master.KOUMOKU_CD AS KOUMOKUCD,");
//		strSeikatu.append(" case ");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f1'  then '����1-1'  when master.KOUMOKU_NAME ='�����@�\��f2'  then '����1-2'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f3'  then '����1-3'  when master.KOUMOKU_NAME ='�����@�\��f4'  then '����4'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f5'  then '����5'    when master.KOUMOKU_NAME ='�����@�\��f6'  then '����6'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f7'  then '����7'    when master.KOUMOKU_NAME ='�����@�\��f8'  then '����8'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f9'  then '����9'    when master.KOUMOKU_NAME ='�����@�\��f10' then '����10'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f11' then '����11'   when master.KOUMOKU_NAME ='�����@�\��f12' then '����12'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f13' then '����13'   when master.KOUMOKU_NAME ='�����@�\��f14' then '����14'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f15' then '����15'   when master.KOUMOKU_NAME ='�����@�\��f16' then '����16'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f17' then '����17'   when master.KOUMOKU_NAME ='�����@�\��f18' then '����18'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f19' then '����19'   when master.KOUMOKU_NAME ='�����@�\��f20' then '����20'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f21' then '����21'   when master.KOUMOKU_NAME ='�����@�\��f22' then '����22'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f23' then '����23'   when master.KOUMOKU_NAME ='�����@�\��f24' then '����24'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f25' then '����25'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='����(�����j' then '����'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '���k������(���̑�)' then '���k������'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '�g��������(���̑�)' then '�g��������'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(����:���ڎB�e)' then '�����w������(����)'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���:���ڎB�e)(�����̗L��)' then '�����w������(���)(�����̗L��)'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���:���ڎB�e)(����)' then '�����w������(���)(����)' ");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���ڎB�e)(�B�e�N����)' then '�����w������(�B�e�N����)' ");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���ڎB�e)(�t�B�����ԍ�)' then '�����w������(�t�B�����ԍ�)' ");
//		strSeikatu.append(" else master.KOUMOKU_NAME end KOUMOKUNAME,");
//		strSeikatu.append(" master.KENSA_HOUHOU AS KENSA_HOUHOU,sonota.JISI_KBN,sonota.KEKA_TI,master.DS_KAGEN,master.DS_JYOUGEN,master.JS_KAGEN,master.JS_JYOUGEN,master.KAGEN,");
//		strSeikatu.append("  master.JYOUGEN,master.DATA_TYPE,master.MAX_BYTE_LENGTH,sonota.KOMENTO,sonota.H_L,sonota.HANTEI,master.TANI,");

		StringBuilder sql = new StringBuilder();

		/* ���f����ʂ̃^�u */
//		sql.append(" SELECT * FROM ( ");
		sql.append(getCellDataSeikatuSql() + strsubSQL.toString());
		sql.append(" FROM ( T_KENSHINMASTER master LEFT JOIN T_KENSACENTER_MASTER kensa ON kensa.KOUMOKU_CD = master.KOUMOKU_CD  AND kensa.KENSA_CENTER_CD = null )");
		sql.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ON sonota.KOUMOKU_CD = master.KOUMOKU_CD");
		// add s.inoue 2012/04/24
		if (!validatedData.getKensaJissiDate().equals(""))
			sql.append(" AND sonota.KENSA_NENGAPI = '" + validatedData.getKensaJissiDate() + "'");

		sql.append(" AND sonota.UKETUKE_ID = ");

		// eidt s.inoue 2011/12/07
		// sql.append(validatedData.getUketuke_id());
		if (isCopy && !isPrev){
			sql.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
		}else{
			sql.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		}

		sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '" + kenshinPatternNumber  + "'");

		// add s.inoue 2011/11/08
		sql.append(" LEFT JOIN (select KOUMOKU_CD,KEKA_TI from T_KENSAKEKA_SONOTA where UKETUKE_ID = ");
		sql.append(" (select MAX(UKETUKE_ID) FROM T_NAYOSE ns,");
		sql.append(" (select NAYOSE_NO FROM T_NAYOSE WHERE UKETUKE_ID = '" + validatedData.getUketuke_id()  + "') nst ");
		sql.append(" where ns.NAYOSE_NO = nst.NAYOSE_NO and ns.UKETUKE_ID <> '" + validatedData.getUketuke_id()  + "')) his ON ps.KOUMOKU_CD = his.KOUMOKU_CD ");

		// pattern 5 �� pattern 1
		sql.append(" WHERE master.KOUMOKU_CD IN ( SELECT KOUMOKU_CD FROM T_KENSHIN_P_S WHERE K_P_NO = '" + kenshinPatternNumber  + "' )");
		// �����Ɋ܂߂�
		// AND KOUMOKU_CD NOT IN ( '9N501000000000011','9N506000000000011' ) )");
		sql.append(" AND master.HKNJANUM = '" + validatedData.getHokenjyaNumber() + "'");

		// �������@�����݂���ꍇ�A�Œ��1�ɂ��郁�\�b�h
		// ���p�~
//		sql.append(getCellDataWhereSql());

//		sql.append(" UNION ");
//		/* �}�C�^�u�p�� UNION SQL */
//		sql.append(strSeikatu.toString() + " '9999' AS HISU_FLG ");
//		sql.append(" FROM ( T_KENSHINMASTER master LEFT JOIN T_KENSACENTER_MASTER kensa ON kensa.KOUMOKU_CD = master.KOUMOKU_CD  AND kensa.KENSA_CENTER_CD = null )");
//		sql.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ON sonota.KOUMOKU_CD = master.KOUMOKU_CD  AND sonota.KENSA_NENGAPI = '20110913'  AND sonota.UKETUKE_ID = '201108250001'");
//		sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '" + patternNo  + "'");
//		sql.append(" WHERE master.KOUMOKU_CD IN ( SELECT KOUMOKU_CD FROM T_KENSHIN_P_S WHERE K_P_NO = '9999'  AND KOUMOKU_CD NOT IN ( '9N501000000000011','9N506000000000011' ) )");
//		sql.append(" AND master.HKNJANUM = '11111111'  ");
//		sql.append(strWhere);
//		sql.append("  ) ");

		// no eidt s.inoue 2013/04/10
		// ������ς���ƕ\�������̏d���̃o�O�ƂȂ�
		// eidt s.inoue 2013/11/07
		// �ēx�C���A�p�^�[�����ɂ�����
		// sql.append(" ORDER BY HISU_FLG,XMLITEM_SEQNO ");
		sql.append(" ORDER BY HISU_FLG,LOW_ID ");

		System.out.println(sql.toString());

		return sql.toString();
	}

	/*
	 * ���f���(MyTab)���Ǝ擾
	 */
	private String createCellDataMyTabSql(){

		StringBuilder sql = new StringBuilder();
		/* �}�C�^�u�p�� UNION SQL */
		// eidt s.inoue 2012/05/30
		sql.append(getCellDataSeikatuSql() + " '9999'  || HISU_FLG AS HISU_FLG ");
		sql.append(" FROM ( T_KENSHINMASTER master LEFT JOIN T_KENSACENTER_MASTER kensa ON kensa.KOUMOKU_CD = master.KOUMOKU_CD  AND kensa.KENSA_CENTER_CD = null )");
		sql.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");

		// add s.inoue 2012/04/24
		if (!validatedData.getKensaJissiDate().equals(""))
			sql.append(" AND sonota.KENSA_NENGAPI = '" + validatedData.getKensaJissiDate() + "'");

		sql.append(" AND sonota.UKETUKE_ID = ");

		// eidt s.inoue 2011/12/07
		// sql.append(validatedData.getUketuke_id());
		if (isCopy && !isPrev){
			sql.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
		}else{
			sql.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		}

		// eidt s.inoue 2013/04/10
		// sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '" + kenshinPatternNumber  + "'");
		sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '9999'");

		// add s.inoue 2011/11/08
		sql.append(" LEFT JOIN (select KOUMOKU_CD,KEKA_TI from T_KENSAKEKA_SONOTA where UKETUKE_ID = ");
		sql.append(" (select MAX(UKETUKE_ID) FROM T_NAYOSE ns,");
		sql.append(" (select NAYOSE_NO FROM T_NAYOSE WHERE UKETUKE_ID = '" + validatedData.getUketuke_id()  + "') nst ");
		sql.append(" where ns.NAYOSE_NO = nst.NAYOSE_NO and ns.UKETUKE_ID <> '" + validatedData.getUketuke_id()  + "')) his ON ps.KOUMOKU_CD = his.KOUMOKU_CD ");

		sql.append(" WHERE master.KOUMOKU_CD IN ( SELECT KOUMOKU_CD FROM T_KENSHIN_P_S WHERE K_P_NO = '9999' AND KOUMOKU_CD NOT IN ( '9N501000000000011','9N506000000000011' ) )");
		sql.append(" AND master.HKNJANUM = '" + validatedData.getHokenjyaNumber() + "'");

		// �������@�����݂���ꍇ�A�Œ��1�ɂ��郁�\�b�h
		// ���p�~
//		sql.append(getCellDataWhereSql());

		// eidt s.inoue 2013/04/10
//		sql.append(" ORDER BY HISU_FLG,XMLITEM_SEQNO ");
		sql.append(" ORDER BY ps.LOW_ID ");

		System.out.println(sql.toString());
		return sql.toString();
	}

//	/*
//	 * SQLWhere��
//	 */
//	private String getCellDataWhereSql(){
//		StringBuilder strSqlWhere = new StringBuilder();
//
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9N016160200000001' AND master.KOUMOKU_CD <> '9N016160300000001'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9A752000000000001' AND master.KOUMOKU_CD <> '9A755000000000001'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9A762000000000001' AND master.KOUMOKU_CD <> '9A765000000000001'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F050000002327201' AND master.KOUMOKU_CD <> '3F050000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F015000002327201' AND master.KOUMOKU_CD <> '3F015000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F070000002327201' AND master.KOUMOKU_CD <> '3F070000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F077000002327201' AND master.KOUMOKU_CD <> '3F077000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3J010000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3B035000002399901' AND master.KOUMOKU_CD <> '3B045000002327201'  AND master.KOUMOKU_CD <> '3B045000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3B090000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3B070000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3C015000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3C020000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3A010000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3A015000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5C095000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3D010000002227101' AND master.KOUMOKU_CD <> '3D010000001927201' AND master.KOUMOKU_CD <> '3D010000001999901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3D010129902227101' AND master.KOUMOKU_CD <> '3D010129901927201' AND master.KOUMOKU_CD <> '3D010129901999901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3D045000001920402' AND master.KOUMOKU_CD <> '3D045000001927102' AND master.KOUMOKU_CD <> '3D045000001999902'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '1A020000000190111'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '1A010000000190111' AND master.KOUMOKU_CD <> '1A100000000191111' AND master.KOUMOKU_CD <> '1A100000000190111'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '1A030000000199901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9N221160700000011' AND master.KOUMOKU_CD <> '9N221160800000049' AND master.KOUMOKU_CD <> '9N226161100000049' AND master.KOUMOKU_CD <> '9N226161200000049'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5C070000002306301' AND master.KOUMOKU_CD <> '5C070000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5H010000001999911'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5H020000001999911'");
//		return strSqlWhere.toString();
//	}

	/*
	 * SQLWhere��
	 */
	private String getCellDataSeikatuSql(){
		StringBuilder strSeikatu = new StringBuilder();

		strSeikatu.append(" SELECT master.XMLITEM_SEQNO,master.KOUMOKU_CD AS KOUMOKUCD,");
		strSeikatu.append(" case ");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f1'  then '����1-1'  when master.KOUMOKU_NAME ='�����@�\��f2'  then '����1-2'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f3'  then '����1-3'  when master.KOUMOKU_NAME ='�����@�\��f4'  then '����4'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f5'  then '����5'    when master.KOUMOKU_NAME ='�����@�\��f6'  then '����6'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f7'  then '����7'    when master.KOUMOKU_NAME ='�����@�\��f8'  then '����8'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f9'  then '����9'    when master.KOUMOKU_NAME ='�����@�\��f10' then '����10'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f11' then '����11'   when master.KOUMOKU_NAME ='�����@�\��f12' then '����12'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f13' then '����13'   when master.KOUMOKU_NAME ='�����@�\��f14' then '����14'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f15' then '����15'   when master.KOUMOKU_NAME ='�����@�\��f16' then '����16'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f17' then '����17'   when master.KOUMOKU_NAME ='�����@�\��f18' then '����18'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f19' then '����19'   when master.KOUMOKU_NAME ='�����@�\��f20' then '����20'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f21' then '����21'   when master.KOUMOKU_NAME ='�����@�\��f22' then '����22'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f23' then '����23'   when master.KOUMOKU_NAME ='�����@�\��f24' then '����24'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='�����@�\��f25' then '����25'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='����(�����j' then '����'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '���k������(���̑�)' then '���k������'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '�g��������(���̑�)' then '�g��������'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(����:���ڎB�e)' then '�����w������(����)'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���:���ڎB�e)(�����̗L��)' then '�����w������(���)(�����̗L��)'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���:���ڎB�e)(����)' then '�����w������(���)(����)' ");
		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���ڎB�e)(�B�e�N����)' then '�����w������(�B�e�N����)' ");
		strSeikatu.append(" when master.KOUMOKU_NAME = '�����w������(���ڎB�e)(�t�B�����ԍ�)' then '�����w������(�t�B�����ԍ�)' ");
		strSeikatu.append(" else master.KOUMOKU_NAME end KOUMOKUNAME,");
		strSeikatu.append(" master.KENSA_HOUHOU AS KENSA_HOUHOU,sonota.JISI_KBN,sonota.KEKA_TI,master.DS_KAGEN,master.DS_JYOUGEN,master.JS_KAGEN,master.JS_JYOUGEN,master.KAGEN,");
		strSeikatu.append("  master.JYOUGEN,master.DATA_TYPE,master.MAX_BYTE_LENGTH,sonota.KOMENTO,master.TANI,his.KEKA_TI HIS_TI,");

		strSeikatu.append(" case when sonota.H_L = '1' then 'L' when sonota.H_L = '2' then 'H' else '' end H_L,");
		strSeikatu.append(" case when sonota.HANTEI = '0' then '������' when sonota.HANTEI = '1' then '�ُ�Ȃ�' when sonota.HANTEI = '2' then '�y�x�ُ�' ");
		strSeikatu.append(" when sonota.HANTEI = '3' then '�v�o�ߊώ@' when sonota.HANTEI = '4' then '�ُ�' when sonota.HANTEI = '5' then '�v����' else '������' end HANTEI, ");

		return strSeikatu.toString();
	}

	/*
	 * ���������̌l���o�^
	 */
	private String registerKojinCopy(){

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO T_KOJIN ( PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU,");
		query.append(" HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX,HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, ");
		query.append(" FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, ");
		query.append(" MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, ");
		query.append(" NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ) ");
		query.append(" SELECT ");
		query.append(" PTNUM, ");
		//JYUSHIN_SEIRI_NO���w��
		query.append(JQueryConvert.queryConvert(validatedData.getcopyJyushinkenID()));
		query.append(" ,YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME, ");

		query.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, ");
		query.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, ");
		query.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, ");

		query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		query.append(" ,MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ");
		query.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
		query.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));

		return query.toString();

	}

	/*
	 * ����f�[�^�o�^
	 */
	private String getUpdateTokutei(String kensaJissiDate){

		StringBuilder query = new StringBuilder();

		query.append("UPDATE T_KENSAKEKA_TOKUTEI SET KENSA_CENTER_CD = ");
		query.append(JQueryConvert.queryConvertAppendComma(validatedData.getKensaCenterCode()));
		query.append("K_P_NO = ");
		query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(kenshinPatternNumber)));
		query.append("KOMENTO = ");
		query.append(JQueryConvert.queryConvertAppendComma(validatedData.getSougouComment()));
		query.append("NENDO = ");
		query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(kensakekaTokutei.getNENDO())));
		query.append("SEIKYU_KBN = ");
		query.append(JQueryConvert.queryConvert(validatedData.getSeikyuKubunCode()));
		query.append(" WHERE UKETUKE_ID = ");
		query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		query.append(" AND KENSA_NENGAPI = ");
		query.append(JQueryConvert.queryConvert(kensaJissiDate));
		return query.toString();
	}

	/*
	 * ����f�[�^�o�^
	 */
	private String getRecordCount_Sonota(String kensaJissiDate){
		StringBuilder query = new StringBuilder();

		// �������ʂ������͂̃��R�[�h���������ꍇ
		query.append(" SELECT KEKA_TI FROM T_KENSAKEKA_SONOTA sonota,T_KENSHINMASTER master WHERE sonota.UKETUKE_ID = ");

		// eidt s.inoue 2011/12/07
		// sql.append(validatedData.getUketuke_id());
		if (isCopy && !isPrev){
			query.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
		}else{
			query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		}

		query.append(" AND sonota.KENSA_NENGAPI =" );
		query.append(JQueryConvert.queryConvert(kensaJissiDate));
		query.append(" AND sonota.KOUMOKU_CD = master.KOUMOKU_CD " );
		query.append(" AND sonota.KEKA_TI is null ");
		query.append(" AND master.HISU_FLG = 1 ");

//		query.append(" AND KOUMOKU_CD NOT IN ( ");
//		query.append(JQueryConvert.queryConvertAppendComma("2A020000001930101"));
//		query.append(JQueryConvert.queryConvertAppendComma("2A030000001930101"));
//		query.append(JQueryConvert.queryConvertAppendComma("2A040000001930102"));
//		query.append(JQueryConvert.queryConvertAppendComma("9A110160700000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9A110160800000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9A110161000000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166000000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166100000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166200000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166300000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100160900000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100161000000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9N501000000000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9N506000000000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9N511000000000049"));
//		query.append(JQueryConvert.queryConvert("9N516000000000049") + " )");

		return query.toString();
	}


	/************************* SQL���� end *************************/

	/************************* �A�N�V�������� start *************************/

	/*
	 * ActionListner
	 */
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		ExtendedButton btnCalled = null;
		JComboBox cmbCalled = null;

		boolean dialogCallFlg = false;
		boolean ptnCallFlg = false;

		if (source instanceof ExtendedButton){
			btnCalled = (ExtendedButton)source;
// del s.inoue 2012/07/13
//			if (btnCalled.getName() != null){
//				for (int i = 0; i < jButton_1.length; i++) {
//					if (jButton_1[i]!= null){
//					if (btnCalled.getName().equals(jButton_1[i].getName()))
//						System.out.println(jButton_1[i].getActionCommand());
//						dialogCallFlg = true;break;
//					}
//				}
//			}
		}else if ( source instanceof JComboBox){
			cmbCalled = (JComboBox)source;
			if (cmbCalled.getParent().getName()!=null){
			if (cmbCalled.getParent().getName().equals("jComboBox_Pattern"))
				ptnCallFlg = true;
			}
		}

		/* �I���{�^�� */
		if (btnCalled == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		/* �o�^�{�^�� */
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());

			String jissiDate = jTextField_KenshinJisiDay.getDateText();
			if (jissiDate == null || jissiDate.isEmpty()) {
				JErrorMessage.show("M3633", this);
				this.jTextField_KenshinJisiDay.grabFocus();
				return;
			}
			if (! validatedData.setKensaJissiDate(jissiDate,false)) {
				JErrorMessage.show("M3605", this);
				this.jTextField_KenshinJisiDay.grabFocus();
				return;
			}

			// ������
			if (isCopy){
				TKojinDao tKojinDao = null;
				try {
					tKojinDao = (TKojinDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), new TKojin());
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}

				long uketukeId = -1L;
				try {
					uketukeId = tKojinDao.selectNewUketukeId();
				} catch (Exception exx) {
					logger.error(exx.getMessage());
				}
				validatedData.setUketuke_id(new Long(uketukeId).toString());

				String copySeiriNo = jTextField_SeiriNo.getText();
				if (! validatedData.setcopyJyushinkenID(copySeiriNo)) {
					JErrorMessage.show("M3606", this);
					this.jTextField_SeiriNo.grabFocus();
					return;
				}
			}

			RETURN_VALUE Ret = JErrorMessage.show("M3622", this);
			if (Ret == RETURN_VALUE.YES) {
				if(registerTabInfomation())
				dispose();
				// add s.inoue 2012/04/24
				myTabClosingPreserve();
			}

			// add s.inoue 2012/11/07
			jButton_End.setFocusable(true);
			jButton_Clear.setFocusable(true);
		}
// del s.inoue 2012/01/24
//		/* ����{�^���������ҏW */
//		else if (btnCalled == jButton_PrintRyoshu) {
//			String uketukeId = validatedData.getUketuke_id();
//			String hihokenjyasyoKigou = validatedData.getHihokenjyaCode();
//			String hihokenjyasyoNo = validatedData.getHihokenjyaNumber();
//			String kensaNengapi = validatedData.getKensaJissiDate();
//// eidt s.inoue 2012/01/20
//// 1.��������
//			// �����{�^����������
//			int count = 0;
//
//			Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
//
//			JOutputNitijiSearchListFrameData vo = new JOutputNitijiSearchListFrameData();
//
//			JInputKessaiDataFrameCtrl ctl = new JInputKessaiDataFrameCtrl();
//			JKessaiProcessData dataItem = ctl.getKesaiItemData(vo);
//			if (dataItem == null)
//				return;
//			datas.add(dataItem);
//
//				if( count != 0 )
//				{
//					try{
//						registerSeikyuData();
//					}catch (Exception ex){
//						try{
//							JApplication.kikanDatabase.rollback();
//						}catch(SQLException exx){
//						}
//						ex.printStackTrace();
//						logger.error(ex.getMessage());
//					}
//				}
//// 2.�����ҏW�Ăяo��
////			Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
////			datas.add(e)
////
////
////			JInputKessaiDataFrameCtrl ctrl = new JInputKessaiDataFrameCtrl(
////					uketukeId,
////					hihokenjyasyoKigou,
////					hihokenjyasyoNo,
////					kensaNengapi,
////					datas);
////
////			WindowRefreshEvent win = new WindowRefreshEvent();
////
////			win.setselectedData(selectedRows.get(0));
////			JScene.CreateDialog(this,ctrl,win);
//		}
		/* �N���A�{�^�� */
		else if (btnCalled == jButton_Clear) {
			logger.info(jButton_Clear.getText());
			pushedClearButton(e);
// del s.inoue 2012/02/09
//		}
//		/* ���f�{�^�� */
//		else if (source == jButton_mytabClear) {
//			for (int i = 0; i < jTextField_2.length; i++) {
//				if (jTextField_2[i] != null)
//					jTextField_2[i].setText("");
//			}
//			for (int i = 0; i < jComboBox_2.length; i++) {
//				if (jComboBox_2[i] != null)
//					jComboBox_2[i].setSelectedIndex(-1);
//			}
//			for (int i = 0; i < jTextAreaField_2.length; i++) {
//				if (jTextAreaField_2[i] != null)
//					jTextAreaField_2[i].setText("");
//			}
		}else if (dialogCallFlg){
			serchKenshinDetailDialog(btnCalled.getName());
		}else if (ptnCallFlg){
//    	    if (e.getStateChange() == e.SELECTED && !isInit) {

//	    	if (kenshinPatternNumber == jComboBox_Pattern.getSelectedIndex() && isInit){
//	    		isInit=false;return;
//	    	}

	    	stateChangedKenshinPatternNumber(
						validatedData.getUketuke_id(),
						validatedData.getKensaJissiDate(),
						false,
						isNewKekkaData);
				// stateChangedKenshinPatternNumber("201108250001","20110913",false,isNewKekkaData);
		}
	}

    /*
     * ���f�p�^�[���R���{�{�b�N�X����
     */

//    private void kenshinPatternItemChange(){
////  		if (isInit) {
//    	jComboBox_Pattern.addItemListener(new ItemListener() {
//    	      public void itemStateChanged(ItemEvent e) {
//	    	    if (e.getStateChange()==e.SELECTED && !isInit) {
//					stateChangedKenshinPatternNumber(
//							validatedData.getUketuke_id(),
//							validatedData.getKensaJissiDate(),
//							false,
//							isNewKekkaData);
//					// stateChangedKenshinPatternNumber("201108250001","20110913",false,isNewKekkaData);
//				}
//    	      }
//    	});
////  		}
//    }

	/**
	 * ���f�p�^�[���̃R���{�{�b�N�X���ύX���ꂽ�ꍇ�ɌĂ΂��
	 */
	public boolean stateChangedKenshinPatternNumber(String uketukeId,String jissiDate,boolean blnKensaRegist,boolean blnKekka) {

		boolean retPattan = false;

//		String query = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ";
//		String kenshinPatternName = null;
//		boolean blnNoEditPattarn = false;

//		ArrayList<Hashtable<String, String>> result = null;
//		Hashtable<String, String> ResultItem;

//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}

//		Object selectedItem = jComboBox_Pattern.getSelectedItem();
//		if (selectedItem != null)
//			kenshinPatternName = selectedItem.toString();

//		int selectedIdx = jComboBox_Pattern.getSelectedIndex();
		String patternValue = (String) jComboBox_Pattern.getValue();

//		// �o�^���A�ύX�O�̌��f�p�^�[�����g�p���A�R���{�{�b�N�X�̃p�^�[���ύX�����f�p�^�[���̃o�b�N�A�b�v����
//		if (blnKensaRegist){
//			if (prekenshinPatternNumber == -1 || blnKekka){
//				blnNoEditPattarn = true;
//			}
//			kenshinPatternNumber = prekenshinPatternNumber;
//		}else{
//			prekenshinPatternNumber = kenshinPatternNumber;
//		}

//		for (int i = 0; i < result.size(); i++) {
//			ResultItem = result.get(i);
//			if (ResultItem.get("K_P_NAME").equals(kenshinPatternName)) {
//
//				// edit 20081201 s.inoue �f�t�H���g�l�̏ꍇ�������p�^�[���폜�s��Ȃ�
//				if (kenshinPatternNumber == Integer.valueOf(ResultItem.get("K_P_NO"))) {
//					this.refreshTable(jissiDate, true,false,false);
//					break;
//				} else {
//
//					// add s.inoue 20081125 �V�K�쐬�̂Ƃ��̓`�F�b�N�����͍s��Ȃ�
//					Long resultCnt = 0L;
//					try {
//						resultCnt = kensakekaTokuteiDao.selectByCountPrimaryKey(Long.valueOf(
//								uketukeId),Integer.valueOf(jissiDate));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					if (resultCnt <= 0) {
//						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//						this.refreshTable(jissiDate, true,false,false);
//						break;
//					}
//
					// edit s.inoue 20081128 �R���{�I�����A�o�^���Ń��b�Z�[�W��ύX����
					RETURN_VALUE retValue = null;
//
//					if (blnKensaRegist){ //�o�^���s��
//						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//						// edit ver2 s.inoue 2009/08/25
//						// �폜���s
//						if (!blnNoEditPattarn){
//							if(pattarnKensaKoumoku()){
//								this.refreshTable(jissiDate, true,false,true);
//								retPattan= true;
//							}else{
//								// edit ver2 s.inoue 2009/08/25
//								this.refreshTable(jissiDate, true,false,false);
//								retPattan= false;
//							}
//						}
//					}else{ //�R���{�I����
						retValue = JErrorMessage.show("M3638", this);

						if (retValue == RETURN_VALUE.YES) {
//							kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//							this.refreshTable(jissiDate, true,false,true);
							kenshinPatternNumber=Integer.parseInt(patternValue);
							JRegisterFrameTabSetting(null,false);
//							isInit = true;
							retPattan= true;
						}else if(retValue == RETURN_VALUE.NO) {
//							isInit = false;

							// eidt s.inoue 2013/04/10
							// jComboBox_Pattern.setSelectedIndex(kenshinPatternNumber - 1);
							DomainPair[] pairs = jComboBox_Pattern.getDomain().getDomainPairList();
							int selectedIDX = -1;
						    for (int i = 0; i < pairs.length; ++i){
						    	if (pairs[i] != null)
						    	if (pairs[i].getCode().toString().equals(String.valueOf(kenshinPatternNumber))){
						    		selectedIDX = i;
						    		System.out.println(jComboBox_Pattern.getSelectedIndex());
						    		break;
						    	}
						    }
						    jComboBox_Pattern.setSelectedIndex(selectedIDX);
						}
//					}
//
//					break;
//				}
//			}
//		}
		return retPattan;
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {

		myTabClosingPreserve();
		dispose();
	}

	// Tab�ێ�
	private void myTabClosingPreserve(){
		// eidt s.inoue 2011/12/09
		try {
			int idxVal = 0;
			for (int i = 0; i < jPanelRegisterCenter.getTabCount(); i++) {
				String title = jPanelRegisterCenter.getTitleAt(i);
				if (title.equals("ϲ�����")){
					idxVal = i+1;break;
				}
			}
			PropertyUtil.setProperty( "register.myTabPos", String.valueOf(idxVal) );
		}catch( Exception ex ){
			logger.error(ex.getMessage());
			JErrorMessage.show("M3551", this);
			return;
		}
	}
	/**
	 * �N���A�{�^��
	 */
	public void pushedClearButton(ActionEvent e) {
		RETURN_VALUE Ret = JErrorMessage.show("M3642", this);
		if (Ret == RETURN_VALUE.YES) {
			// �ʏ�tab
			for (int i = 0; i < jTextField_1.length; i++){
				if(jTextField_1[i] != null)
					jTextField_1[i].setText("");
			}
			for (int i = 0; i < jComboBox_1.length; i++){
				if(jComboBox_1[i] != null)
					jComboBox_1[i].setSelectedIndex(-1);
			}
			for (int i = 0; i < jTextAreaField_1.length; i++){
				if(jTextAreaField_1[i] != null)
					jTextAreaField_1[i].setText("");
			}

			// add s.inoue 2012/02/09
			// my�p�^�[��
			for (int i = 0; i < jTextField_2.length; i++) {
			if (jTextField_2[i] != null)
				jTextField_2[i].setText("");
			}
			for (int i = 0; i < jComboBox_2.length; i++) {
			if (jComboBox_2[i] != null)
				jComboBox_2[i].setSelectedIndex(-1);
			}

			for (int i = 0; i < jTextAreaField_2.length; i++) {
				if (jTextAreaField_2[i] != null)
					jTextAreaField_2[i].setText("");
			}

			// add s.inoue 2013/02/27
			kensaKekka ="";
			height="";
			weight="";
		}
	}

	/*
	 * �o�^����
	 */
	private int mode;
	private String initialDate = "";
//	private boolean isInit = false;
	private boolean isNewKekkaData = false;

	public final static int INSERT_MODE = 1;
	public final static int UPDATE_MODE = 2;

	private boolean registerTabInfomation(){

		// ���f���{��
		// initialDate = validatedData.getKensaJissiDate();
		String jissiDate = jTextField_KenshinJisiDay.getDateText();

		/*
		 *  case1.�����\���̓��t����ύX���������ꍇ�́A�x����\������B
		 */
		boolean hasJissiDateChanged = false;
		if (! jissiDate.equals(initialDate))
			hasJissiDateChanged = true;

		if (hasJissiDateChanged && ! this.isNewKekkaData) {
			/* ���f���{���ύX�����ꍇ */
			String[] messageParams = {initialDate, jissiDate};
			RETURN_VALUE retValue = JErrorMessage.show("M3543", this, messageParams);
			if (retValue == RETURN_VALUE.NO)
				return false;
		}

		/*
		 * case2.���Ɍ��f���ʃf�[�^�����݂��A�������\���̓��t����ύX���������ꍇ�́A
		 *       �x�����b�Z�[�W��\������B
		 */
		boolean existsKensaKekkaData = existsKensaKekkaData(jissiDate);
		if (existsKensaKekkaData) {
			if (hasJissiDateChanged) {
				/* M3544=�m�F,[%s]�ɂ́A���łɌ��ʃf�[�^�����݂��܂��B�o�^�����ꍇ�A
				 * �ȑO�ɓ��͂������ʃf�[�^�͏����Ă��܂��܂��B�o�^���Ă�낵���ł����H,1,0 */
				String[] messageParams = {jissiDate};
				RETURN_VALUE retValue = JErrorMessage.show("M3544", this, messageParams);
				if (retValue != RETURN_VALUE.YES) {
					return false;
				}
			}

			mode = UPDATE_MODE;
		}
		else {
			mode = INSERT_MODE;
		}
		// eidt s.inoue 2012/04/24 �ԈႢ
		// ��Y��:1 �\���R�Y��:2 ��Y��:3 ����s�\:4
		String hantei = "";
		switch (jComboBox_MetaboHantei.getSelectedIndex()) {
		case 0:hantei = "0";break;case 1:hantei = "1";break;	case 2:hantei = "2";break;	case 3:hantei = "3";break;	case 4:hantei = "4";break;
		}
		// �ϋɓI�x��:1 ���@�Â��x��:2 �Ȃ�:3 ����s�\:4
		String sido = "";
		switch (jComboBox_HokenSidouLevel.getSelectedIndex()) {
		case 0:sido = "0";break;case 1:sido = "1";break;	case 2:sido = "2";break;	case 3:sido = "3";break;case 4:sido = "4";break;
		}
		// 1: ��{�I�Ȍ��f 2: ��{�I�Ȍ��f�{�ڍׂȌ��f�̏ꍇ 3: ��{�I�Ȍ��f�{�ǉ����f���ڌ��f 4: ��{�I�Ȍ��f�{�ڍׂȌ��f+�ǉ����f���� 5: �l�ԃh�b�N
		String seikyu = "";
		switch (jComboBox_SeikyuKubun.getSelectedIndex()) {
		case 0:seikyu = "1";break;	case 1:seikyu = "2";break;	case 2:seikyu = "3";break;	case 3:seikyu = "4";break;	case 4:seikyu = "5";break;
		}

		// �G���[�`�F�b�N
		if (!(validatedData.setSeikyuKubunCode(seikyu,false)
				&& validatedData.setHihokenjyaCode(jLabel_HihokenjyaKigo.getText(),false)
				&& validatedData.setHihokenjyaNumber(jLabel_HihokenjyaNo.getText(),false)
				&& validatedData.setNameKana(jLabel_Name.getText())
				&& validatedData.setKensaJissiDate(jissiDate,false)
				&& validatedData.setSougouComment(jTextArea_SougouComment.getText())
				&& validatedData.setMetaboHantei(hantei)
				&& validatedData.setHokenSidouLevel(sido)
				&& kenshinPatternNumber != -1))return false;

			TKojinDao tKojinDao = null;

			try {

				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				/*
				 * case3.�����������̏���
				 */
				if (isCopy){
					try {
						tKojinDao = (TKojinDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), new TKojin());
						// �����ԍ��d���`�F�b�N����
						Long resultCnt = tKojinDao.selectByCountUniqueKey(validatedData.getcopyJyushinkenID());
						if (resultCnt > 0)
							JErrorMessage.show("M3607", this);
					}catch (Exception ex) {
						logger.error(ex.getMessage());
					}

					// ���񂹓o�^
					registerHistory();
					try {
						// T_KOJIN�o�^����
						JApplication.kikanDatabase.sendNoResultQuery(registerKojinCopy());
					} catch (SQLException e) {
						logger.error(e.getMessage());
						// JApplication.kikanDatabase.rollback();
						// eidt s.inoue 2013/02/20
						JApplication.kikanDatabase.getMConnection().rollback();
						return false;
					}
				}

				/*
				 *  case4.���̑��e�[�u���o�^����
				 */
				// eidt s.inoue 2013/01/29
				// �߂�l�ɂ���ĕς���
				boolean ret = registerKensaKekkaSonota(jissiDate,hasJissiDateChanged);
				if (!ret)return ret;

//					boolean isOtherValidated = false;
//					boolean isKekkaValidated = false;
//					HashMap<Integer, String> kekkaValidated = new HashMap<Integer, String>();
//
//					boolean isCheckCSV = false;
//					String errMessage = "";
//					int i = 0;

			} catch (SQLException f) {
				logger.error(f.getMessage());
				try {
					// JApplication.kikanDatabase.rollback();
					// eidt s.inoue 2013/02/20
					JApplication.kikanDatabase.getMConnection().rollback();
					return false;
				} catch (SQLException g) {
					JErrorMessage.show("M0000", this);
					System.exit(1);
				}
			}
		return true;
	}

	/*
	 * case4.���̑��e�[�u���ւ̓o�^����
	 */
	private boolean registerKensaKekkaSonota(String jissiDate, boolean hasJissiDateChanged){

		boolean validateKensa = false;
		// del s.inoue 2012/07/12
		// boolean hisuInput = false;
		boolean hanteiBase = false;
		boolean hanteiSyosai = false;
		boolean hanteiTuika = false;
		boolean notExtMessage = false;
		boolean validateInput = false;

		int intBaseCnt = 0;
		int intSyosaiCnt = 0;
		int intTuikaCnt = 0;
		int intAllCnt = 0;

		boolean isOtherValidated = false;
		boolean isKekkaValidated = false;
		boolean isCheckCSV = false;

		HashMap<Integer, String> kekkaValidated = new HashMap<Integer, String>();

		// 4.1.���ʒl�Ɋւ��錟��
		Hashtable<String, String> resultItem = null;
		String errMessage = "";
		String errhisuMessage = "";


		// move s.inoue 2013/08/13
		// ���^�{�A�ی��w���Ȃ�����
		ArrayList<Hashtable<String, String>> codeResultm = null;
		// eidt s.inoue 2013/08/12
		String querym = new String("SELECT K_P_NO FROM T_KENSHIN_P_S WHERE (KOUMOKU_CD = "
		+ JQueryConvert.queryConvert("9N501000000000011") +" OR KOUMOKU_CD = "+ JQueryConvert.queryConvert("9N506000000000011")
		+ ") AND K_P_NO = " +kenshinPatternNumber
		+ " AND K_P_NO <> '9999' AND K_P_NO <> '-1'" );

		try {
			codeResultm = JApplication.kikanDatabase.sendExecuteQuery(querym);

			if (codeResultm.size() < 2){
				JErrorMessage.show("M3645", this);
				JApplication.kikanDatabase.getMConnection().rollback();
				return false;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			JErrorMessage.show("M3645", this);
			try {
				JApplication.kikanDatabase.getMConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}


		for (int irow = 0; irow < tableResult.size(); irow++) {

			resultItem = tableResult.get(irow);

			String koumokuCode = resultItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
			String koumokuName = resultItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME);
			String maxByte = resultItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);
			String hl = resultItem.get(JConstantString.COLUMN_NAME_H_L).trim();
			String hantei = resultItem.get(JConstantString.COLUMN_NAME_HANTEI).trim();

			// ���ʒl�ȊO�Ɋւ��Ă̌���
//			switch (jComboBox_SeikyuKubun.getSelectedIndex()) {
//			case 0:seikyu = "1";break;	case 1:seikyu = "2";break;	case 2:seikyu = "3";break;	case 3:seikyu = "4";break;	case 4:seikyu = "5";break;
//			}
			// eidt s.inoue 2012/06/27 ����X�V
			isOtherValidated = (validatedData.setHihokenjyaCode(jLabel_HiHokenjyaCode.getText(),false)
					&& validatedData.setHihokenjyaNumber(jLabel_HiHokenjyaNumber.getText(),false)
					&& validatedData.setKensaJissiDate(jissiDate,false)
					&& validatedData.setKensaKoumokuCode(koumokuCode)
					&& validatedData.setHL(hl)
					&& validatedData.setHantei(hantei));

//			&& validatedData.setComment((String) this.model.getData(i, COLUMN_IDX_KOMENTO))
//			&& validatedData.setChkText((String) this.model.getData(i, COLUMN_IDX_JISIKBN))

			// ���Ӂj���W�b�N�s���A�ʒu�����p
			int choseiRow = irow;

			// eidt s.inoue 2013/07/09
			if (irow > posHokensido){
				choseiRow -= 1;
			}
			if (irow > posMetabo){
				choseiRow -= 1;
			}
//			if ((irow > posHokensido){
//				(irow > posMetabo))
//					choseiRow -= 2;

// del s.inoue 2013/08/13
//			// eidt s.inoue 2013/08/12
//			// ���^�{�A�ی��w���Ȃ�����
//			ArrayList<Hashtable<String, String>> codeResultm = null;
//			// eidt s.inoue 2013/08/12
//			String querym = new String("SELECT K_P_NO FROM T_KENSHIN_P_S WHERE (KOUMOKU_CD = "
//			+ JQueryConvert.queryConvert("9N501000000000011") +" OR KOUMOKU_CD = "+ JQueryConvert.queryConvert("9N506000000000011")
//			+ " AND K_P_NO = " +kenshinPatternNumber
//			+ ") AND K_P_NO <> '9999' AND K_P_NO <> '-1'" );
//
//			try {
//				codeResultm = JApplication.kikanDatabase.sendExecuteQuery(querym);
//			} catch (SQLException e) {
//				logger.warn(e.getMessage());
//			}
//
//			// eidt s.inoue 2013/05/23
//			if (codeResultm.size() == 0){
//				JErrorMessage.show("M3645", this);
//				try {
//					JApplication.kikanDatabase.getMConnection().rollback();
//					return false;
//				} catch (SQLException e) {
//					logger.warn(e.getMessage());
//				}
//				return false;
//			}

			// 4.2.�^�C�v�ʏ���
			// add s.inoue 2012/06/27
			// �O��l�c��ׁA����
			validatedData.setKekkaCode("");
			validatedData.setKekkaDecimal("",maxByte);
			validatedData.setKekkaText("", resultItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH));

			switch (Integer.valueOf(resultItem.get(JConstantString.COLUMN_NAME_DATA_TYPE))) {
			case 1:
				// ���l�̏ꍇ
				if (jTextField_1[choseiRow] == null)continue;
				String kekka = (jTextField_1[choseiRow].getText()== null)?"":(String)jTextField_1[choseiRow].getText();

				isKekkaValidated = validatedData.setKekkaDecimal(kekka,maxByte);

				if (isKekkaValidated) {
					isCheckCSV = true;
					String retMessage = getHighLawMessage(koumokuCode,koumokuName,validatedData.getHokenjyaNumber(),validatedData.getKekka());

					if (!retMessage.isEmpty()){
						validateInput = true;
						errMessage +=retMessage;
					}
				}
				break;

			case 2:
				// �R�[�h�̏ꍇ
				if (jComboBox_1[choseiRow] == null)continue;
// del s.inoue 2012/02/17 1��
// eidt s.inoue 2012/11/19 �ďC��
//				String kekkaCode = (jComboBox_1[choseiRow].getValue()== null)?"":(String)jComboBox_1[choseiRow].getValue();
				// eidt s.inoue 2012/12/26 �R���{���C��index������Ă܂��I�I�I
				// String kekkaCode = String.valueOf(jComboBox_1[choseiRow].getSelectedIndex() == 0?"":jComboBox_1[choseiRow].getSelectedIndex());
				// String kekkaCode = String.valueOf(jComboBox_1[choseiRow].getSelectedIndex());
				// eidt s.inoue 2013/02/27 null���
				String kekkaCode = "";
				if (jComboBox_1[choseiRow].getSelectedItem() != null )
					kekkaCode = String.valueOf(jComboBox_1[choseiRow].getSelectedItem().equals("")?"":jComboBox_1[choseiRow].getSelectedItem().toString().substring(0, 1));

				ArrayList<Hashtable<String, String>> codeResult = null;
				Hashtable<String, String> codeResultItem;
				String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "+ JQueryConvert.queryConvert(koumokuCode));
				try {
					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
				} catch (SQLException e) {
					logger.warn(e.getMessage());
				}

				if (kekkaCode.equals("")) {
					isKekkaValidated = validatedData.setKekkaCode("");
					isCheckCSV = true;
					break;
				}

				for (int j = 0; j < codeResult.size(); j++) {
					codeResultItem = codeResult.get(j);

					if (kekkaCode.equals(codeResultItem.get(JConstantString.COLUMN_NAME_CODE_NUM))) {
						isCheckCSV = CheckHighLawItem(kekkaCode,validatedData.getHokenjyaNumber(),codeResultItem.get(JConstantString.COLUMN_NAME_CODE_NUM));
						isKekkaValidated = validatedData.setKekkaCode(codeResultItem.get(JConstantString.COLUMN_NAME_CODE_NUM));
						break;
					}
				}
				break;

			case 3:
				// ������̏ꍇ
				if (jTextAreaField_1[choseiRow] == null)continue;

				String kekkaArea = (jTextAreaField_1[choseiRow].getText()== null)?"":(String)jTextAreaField_1[choseiRow].getText();
				isCheckCSV = true;
				isKekkaValidated= validatedData.setKekkaText(kekkaArea, resultItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH));
				break;
			}

			// ���{�敪
			// String jisi = jCheckBox_1[choseiRow].getValue();
			if (jCheckBox_1[choseiRow].isSelected()){
				validatedData.setJisiKbn("2");
			}else{
			// String kekka = validatedData.getKekka();
				validatedData.setJisiKbn("1");
			}

			if (!isKekkaValidated)
				kekkaValidated.put(choseiRow,"���ڃR�[�h:" + koumokuCode + "  ���ږ�:" + koumokuName);

			if (!(isKekkaValidated && isOtherValidated && isCheckCSV)) {
				if (!isKekkaValidated) {
					String[] params = { kekkaValidated.get(choseiRow) };
					JErrorMessage.show("M3601", this, params);
				} else if (!isCheckCSV) {
					String[] params = { koumokuCode, koumokuName };
					JErrorMessage.show("M3602", this, params);
				} else if (!isOtherValidated){
					String[] params = { koumokuCode, koumokuName };
					JErrorMessage.show("M3608", this, params);
				}
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
				// eidt s.inoue 2013/02/20
				try {
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e) {
					logger.warn(e.getMessage());
				}
				return false;
			}

			try {
				// 4.3.���̑�obj�ւ̊i�[����
				// ��tID,�N�x�̒ǉ��Ή� �������ʓ���A���̑��e�[�u���֐V�K���R�[�h�}��
				kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
				kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
				kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
				try {
					kensakekaSonota.setKENSA_NENGAPI(new Integer(validatedData.getKensaJissiDate()));
					kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(validatedData.getKensaJissiDate()));
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
				}
				kensakekaSonota.setKOUMOKU_CD(validatedData.getKensaKoumokuCode());
				kensakekaSonota.setKEKA_TI(validatedData.getKekka());
				kensakekaSonota.setKOMENTO(validatedData.getComment());

				// ���{�敪
				Integer jisiInteger = null;
				String strJisi = validatedData.getJisi_KBN();
				if (strJisi != null && !strJisi.isEmpty())
					jisiInteger = new Integer(strJisi);

				kensakekaSonota.setJISI_KBN(jisiInteger);
				// HL
				Integer hlInteger = null;
				String hlString = validatedData.getHL();
				if (hlString != null && !hlString.isEmpty())
					hlInteger = new Integer(hlString);

				kensakekaSonota.setH_L(hlInteger);
				// ����
				kensakekaSonota.setHANTEI(new Integer(validatedData.getHantei()));
			} catch (NumberFormatException e) {
				/* �������Ȃ� */
			}

// del s.inoue 2011/11/16
//			// ���ʒl(�����{,����s�\)����
//			if ((validatedData.getJisi_KBN().equals("0") ||
//					validatedData.getJisi_KBN().equals("2")) &&
//					!validatedData.getKekka().equals("")){
//				String[] params = { koumokuCode, koumokuName };
//				JErrorMessage.show("M3634", this, params);
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//				}
//				return false;
//			}
			try{
				// add s.inoue 2013/01/28
				if (!validatedData.getKensaJissiDate().equals("")){
					if (koumokuCode.equals(PrintDefine.CODE_HBA1C_SONOTA_JDS)||
						koumokuCode.equals(PrintDefine.CODE_HBA1C_COUSOHO_JDS)||
						koumokuCode.equals(PrintDefine.CODE_HBA1C_HPLC_JDS)||
						koumokuCode.equals(PrintDefine.CODE_HBA1C_RATEX_JDS)){

						int tDate = 20130401;
						// ���f���{����'130401�ȍ~
						if (tDate <= Integer.parseInt(validatedData.getKensaJissiDate())){
							if (!validatedData.getKekka().equals("")){
								JErrorMessage.show("M3643", this);
								// eidt s.inoue 2013/05/23
								try {
									JApplication.kikanDatabase.getMConnection().rollback();
								} catch (SQLException e) {
									logger.warn(e.getMessage());
								}
								return false;
							}
						}
					// add s.inoue 2013/01/29
					}else if(koumokuCode.equals( PrintDefine.CODE_HBA1C_SONOTA_NGSP)||
							koumokuCode.equals( PrintDefine.CODE_HBA1C_COUSOHO_NGSP)||
							koumokuCode.equals( PrintDefine.CODE_HBA1C_HPLC_NGSP)||
							koumokuCode.equals( PrintDefine.CODE_HBA1C_RATEX_NGSP)
							){
						int tDate = 20130401;
						// ���f���{����'130401�ȍ~
						if (tDate > Integer.parseInt(validatedData.getKensaJissiDate())){
							if (!validatedData.getKekka().equals("")){
								JErrorMessage.show("M3644", this);
								// eidt s.inoue 2013/05/23
								try {
									JApplication.kikanDatabase.getMConnection().rollback();
								} catch (SQLException e) {
									logger.warn(e.getMessage());
								}
								return false;
							}
						}
					}
				}
			}catch (Exception e) {
				logger.error(e.getMessage());
				System.out.println(e.getMessage());
			}

			// �o�^�Ώۂ̃t���O�`�F�b�N
			// ��O�̍���(����[�Ɋւ�鍀��)
			String KoumokuHd = koumokuCode.substring(0, 3);
			if(!KoumokuHd.equals(JConstantString.CODE_MONSHIN_HEADER)&&
				!koumokuCode.equals(JConstantString.CODE_HOKEN_SHIDOU) &&
				!koumokuCode.equals(JConstantString.CODE_SEIKATU_KAIZEN) &&
				!koumokuCode.equals(JConstantString.CODE_SAIKETSU_ZIKAN) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_1) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_2) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_3) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_4)){

				String hisuFlg = resultItem.get(JConstantString.COLUMN_NAME_HISU_FLG);

				// add s.inoue 2012/03/07
				// if (!hisuInput)
//				if (!kekkaMessage.isEmpty())

					// eidt s.inoue 2012/03/08
					// errhisuMessage = errhisuMessage +"[���s]���ڃR�[�h[" + koumokuCode + "]"+ "���ږ�[" + koumokuName +"]";
					errhisuMessage = errhisuMessage +"[���s]     " + koumokuName +"  ";

// del s.inoue 2012/07/12
//					if (hisuFlg.equals(JApplication.SEIKYU_KBN_BASE)
//							&& validatedData.getKekka().equals("")){
//						hisuInput = true;
//					}

				if ((validatedData.getJisi_KBN().equals("1") && !validatedData.getKekka().equals("")) ||
					((validatedData.getJisi_KBN().equals("2") || validatedData.getJisi_KBN().equals("0"))
							&& validatedData.getKekka().equals(""))){

					if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_BASE)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;intBaseCnt +=1;
						}
					}else if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_SYOSAI)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
							hanteiSyosai = true;
						}
						if (hanteiBase && hanteiSyosai){
							intSyosaiCnt +=1;
						}
					}else if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_TUIKA)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
							hanteiTuika = true;
						}
						if (hanteiBase && hanteiTuika){
							intTuikaCnt +=1;
						}
					}else if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_SYOSAI_TUIKA) ||
							validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_DOC)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
							hanteiSyosai = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
							hanteiTuika = true;
						}
						if (hanteiBase && hanteiSyosai && hanteiTuika){
							intAllCnt +=1;
						}
					}
				}
			}
			// �������ڐ������`�F�b�N
			validateKensa= isNotExistKensaKoumoku(
					    validatedData.getHokenjyaNumber(),
						validatedData.getUketuke_id(),
						validatedData.getKensaJissiDate(),
						validatedData.getSeikyuKubunCode(),
						validatedData.getKensaKoumokuCode(),
						validatedData.getKekka(),
						validatedData.getJisi_KBN());

			if (validateKensa){

				if (!notExtMessage){
					JErrorMessage.show("M3635", this);
					notExtMessage = true;
				}
			}

		try{
			if (! this.isCopy && ! this.isNewKekkaData) {
					kensakekaSonotaDao.delete(
							new Long(validatedData.getUketuke_id()),
							new Integer(this.initialDate),
							new String(validatedData.getKensaKoumokuCode()));

			}
			kensakekaSonotaDao.insert(kensakekaSonota);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		// for close
		}

// del s.inoue 2012/07/12
//		if (hisuInput){
//			String[] params = { errhisuMessage };
//			JErrorMessage.show("M3641", this, params);
//		}

		if (validateInput){
			String[] params = { errMessage };
			JErrorMessage.show("M3602", this, params);
// del s.inoue 2013/02/20 �p��
//			try {
//				JApplication.kikanDatabase.getMConnection().rollback();
//			} catch (SQLException e) {
//				logger.warn(e.getMessage());
//			}
//			// return true;
//			return false;
		}

		if ((intBaseCnt == 0) &&(intSyosaiCnt == 0) &&(intTuikaCnt == 0) && (intAllCnt == 0)){
			// ���b�Z�[�W�͈�񂫂�ɂ���
			if (!notExtMessage && isCheckCSV){
				JErrorMessage.show("M3636", this);
				notExtMessage = true;
			}
		}

//			// �e�[�u���̍Ō�̍s��insert����O��break���ꂽ�ꍇ
//			if (choseiRow != tableResult.size()) {
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//				}
//				return false;
//			}

		// ���Ƀ��^�{���b�N�V���h���[������ƕی��w�����x���Ɋւ��Ă̏������s��
		try {
			// �������ʃf�[�^���̑����R�[�h�폜�E�}��
			kensakekaSonota = new TKensakekaSonota();
			kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
			kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
			kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
			kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(jissiDate));
			kensakekaSonota.setKENSA_NENGAPI(new Integer(jissiDate));

			// �܂����^�{���b�N�V���h���[���Ɋւ��鏈�����s��
			kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
					this.initialDate.equals("")?new Integer(jissiDate):new Integer(this.initialDate),"9N501000000000011");

			// add s.inoue 2012/04/26
			JApplication.kikanDatabase.getMConnection().commit();

			kensakekaSonota.setKOUMOKU_CD("9N501000000000011");
			kensakekaSonota.setKEKA_TI(validatedData.getMetaboHantei());
			kensakekaSonotaDao.insert(kensakekaSonota);

			// �ی��w�����x���Ɋւ��鏈�����s��
			kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
					this.initialDate.equals("")?new Integer(jissiDate):new Integer(this.initialDate),"9N506000000000011");
			// add s.inoue 2012/04/26
			JApplication.kikanDatabase.getMConnection().commit();

			kensakekaSonota.setKOUMOKU_CD("9N506000000000011");
			kensakekaSonota.setKEKA_TI(validatedData.getHokenSidouLevel());
			kensakekaSonotaDao.insert(kensakekaSonota);

			// ����e�[�u��(update,insert)����
			if (mode == UPDATE_MODE) {
				JApplication.kikanDatabase.sendNoResultQuery(getUpdateTokutei(jissiDate));
			} else if (mode == INSERT_MODE) {
				kensakekaTokutei = new TKensakekaTokutei();
				kensakekaTokutei.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
				kensakekaTokutei.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
				kensakekaTokutei.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
				kensakekaTokutei.setKENSA_CENTER_CD(validatedData.getKensaCenterCode());
				kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumber));
				kensakekaTokutei.setKOMENTO(validatedData.getSougouComment());
				kensakekaTokutei.setKENSA_NENGAPI(new Integer(jissiDate));
				kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(jissiDate));

				// ���̂���UPDATE���s�����߈ꎞ�I�Ƀf�[�^������
				kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(1));
				kensakekaTokutei.setSEIKYU_KBN(new Integer(validatedData.getSeikyuKubunCode()));
				kensakekaTokuteiDao.insert(kensakekaTokutei);
			}

			/* �����ł͂Ȃ��A�����t�ύX�ł���ꍇ�́A���̃f�[�^���폜����B */
			if (! this.isCopy && hasJissiDateChanged && ! this.isNewKekkaData) {
				kensakekaTokuteiDao.delete(validatedData.getUketuke_id(),this.initialDate);
			}

			ArrayList<Hashtable<String, String>> rs = JApplication.kikanDatabase.sendExecuteQuery(getRecordCount_Sonota(jissiDate));

			/* ���͐� */
			int nullCount = rs.size();

			// �S�Ă̍��ڂɂ��ē��͂��Ȃ���Ă����ꍇ
			String kensakekaInputFlg = (nullCount == 0) ? "2": "1";
			String query = String.format("UPDATE T_KENSAKEKA_TOKUTEI SET KEKA_INPUT_FLG = '%s' WHERE UKETUKE_ID = '%s' AND KENSA_NENGAPI = '%s'",
										kensakekaInputFlg, validatedData.getUketuke_id(),jissiDate);
			System.out.println(query);

			JApplication.kikanDatabase.sendNoResultQuery(query);

			// ���ʂ����邩�ǂ����o�^�������ɁA�R�~�b�g�O�̏�Ԃ̃t���O��n��
			// JApplication.kikanDatabase.Commit();
			JApplication.kikanDatabase.getMConnection().commit();

			// stateChangedKenshinPatternNumber(validatedData.getUketuke_id(),jissiDate,true,isNewKekkaData);

			this.initialDate = this.validatedData.getKensaJissiDate();
			this.isNewKekkaData = false;

		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				// JApplication.kikanDatabase.rollback();
				// eidt s.inoue 2013/02/20
				JApplication.kikanDatabase.getMConnection().rollback();
				return false;
			} catch (SQLException ex) {
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
		if (mode == INSERT_MODE) {
			mode = UPDATE_MODE;
			// jTextField_SeiriNo.setVisible(false);
			// jLabelSeirino.setVisible(false);
			isCopy = false;
		}
		isPrev = true;
		return true;
	}

	/*
	 * ����l�����l�`�F�b�N�ɂ�郁�b�Z�[�W�擾���s��
	 */
	public String getHighLawMessage(String koumokuCode,String koumokuName,String hknjaNm,String value){

		ArrayList<Hashtable<String, String>> result = null;
		String retMessage = "";
		String kekkaMessage ="";

		try{
			String[] params = { hknjaNm,koumokuCode };

			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";

			// ��l�̏ꍇ�̓`�F�b�N���s��Ȃ�
			if (!value.isEmpty() || !hknjaNm.isEmpty()) {
				String strKagen = "";
				String strJyogen = "";

				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);

				for (int i = 0; i < result.size(); i++) {

					strKagen = result.get(i).get("KAGEN");
					strJyogen = result.get(i).get("JYOUGEN");

					// ���ڃR�[�h�̌��ʔ���̕����������Ĕ�r���s���͈͓��̏ꍇ true
					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
						if (value.isEmpty())
							break;
						if (strKagen.isEmpty() && strJyogen.isEmpty()){
							break;
						}else if (strKagen.isEmpty()){
							if (Double.valueOf(strJyogen) < Double.valueOf(value)){
								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"�𒴂��Ă��܂��B";
							}
						}else if(strJyogen.isEmpty()){
							if (Double.valueOf(strKagen) > Double.valueOf(value)){
								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"��������Ă��܂��B";
							}
						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
							if (Double.valueOf(strKagen) > Double.valueOf(value)){
								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"��������Ă��܂��B";
							}else if(Double.valueOf(strJyogen) < Double.valueOf(value)){
								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"�𒴂��Ă��܂��B";
							}
						}
					}
				}
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		if (!kekkaMessage.isEmpty())
			retMessage = "[���s]���ڃR�[�h[" + koumokuCode + "] ���ږ�[" + koumokuName + "][���s]�̌��ʒl[" + validatedData.getKekka() + "]��" + kekkaMessage;

		return retMessage;
	}

	/*
	 * ����l�����l�̍��ڂ��`�F�b�N���s��
	 */
	public static boolean CheckHighLawItem(String koumokuCode,String hknjaNm,String value){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			String[] params = { hknjaNm,koumokuCode };
			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";

			// ��l�̏ꍇ�̓`�F�b�N���s��Ȃ�
			if (!value.isEmpty() || !hknjaNm.isEmpty()) {
				String strKagen = "";
				String strJyogen = "";

				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);

				for (int i = 0; i < result.size(); i++) {

					strKagen = result.get(i).get("KAGEN");
					strJyogen = result.get(i).get("JYOUGEN");

					// ���ڃR�[�h�̌��ʔ���̕����������Ĕ�r���s��
					// �͈͓��̏ꍇ true
					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
						if (value.isEmpty())break;
						if (strKagen.isEmpty() && strJyogen.isEmpty()){
							break;
						}else if (strKagen.isEmpty()){
							return Double.valueOf(strJyogen) >= Double.valueOf(value);
						}else if(strJyogen.isEmpty()){
							return Double.valueOf(strKagen) <= Double.valueOf(value);
						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
							return (Double.valueOf(strKagen) <= Double.valueOf(value)
									&& Double.valueOf(value) <= Double.valueOf(strJyogen));
						}
					}
				}
			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}
		return true;
	}

	/**
	 * �����敪�ɂ�錟�����ڃ`�F�b�N
	 */
	public static boolean isNotExistKensaKoumoku(
			String hokenjyaNumber,
			String uketukeId,
			String kensaDate,
			String seikyuKbn,
			String koumokuCd,
			String kekkati,
			String jisiKbn
			)
	{
			boolean retChk = false;
			boolean hanteiHisu = false;

			// ��O�̍���(����[�Ɋւ�鍀��)
			String KoumokuHd = koumokuCd.substring(0, 3);

			if(KoumokuHd.equals(JConstantString.CODE_MONSHIN_HEADER) ||
					!koumokuCd.equals(JConstantString.CODE_HOKEN_SHIDOU)  ||
					!koumokuCd.equals(JConstantString.CODE_SEIKATU_KAIZEN)  ||
					!koumokuCd.equals(JConstantString.CODE_SAIKETSU_ZIKAN)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_1)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_2)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_3)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_4)){
				return false;
			}

			// ��{�I�Ȍ��f,�ڍׂȌ��f,�ǉ����f����,�l�ԃh�b�N
			StringBuffer buffer = new StringBuffer();
			String[] params =null;

			// �V�K�o�^���̃`�F�b�N�������Ȃ���
			buffer.append(" select master.HISU_FLG ");
			buffer.append(" from T_KENSHINMASTER master ");
			buffer.append(" where master.HKNJANUM = ?");
			buffer.append(" and KOUMOKU_CD = ?");

			params = new String []{ hokenjyaNumber,koumokuCd  };

			String query = buffer.toString();

			ArrayList<Hashtable<String, String>> result = null;
			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);
			}catch(SQLException ex){
				ex.printStackTrace();
			}

			for (Hashtable<String, String> item : result) {
				String hisuFlg = item.get("HISU_FLG");
				//String tanka = item.get("TANKA_KENSIN");
				// �o�^�Ώۂ̃t���O�`�F�b�N
				if (jisiKbn.equals("1")){
					if (!kekkati.equals("")){
						hanteiHisu = true;
					}
				}else if (jisiKbn.equals("2") ||
						jisiKbn.equals("0")){
					if (kekkati.equals("")){
						hanteiHisu = true;
					}
				}

				if (hanteiHisu){
					// �������m�F�p
					if (seikyuKbn.equals(JApplication.SEIKYU_KBN_BASE)){
						if (!hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							retChk = true;break;
						}
					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_SYOSAI)){
						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
								hisuFlg.equals(JApplication.HISU_FLG_SYOSAI))){
							retChk = true;break;
						}
					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_TUIKA)){
						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
								hisuFlg.equals(JApplication.HISU_FLG_TUIKA))){
							retChk = true;break;
						}
					}
				}

			}
		return retChk;
	}

	/*
	 * �������ʃf�[�^
	 */
	private boolean existsKensaKekkaData(String kensaJissiDate) {
		Integer kensanengapi = null;
		try {
			kensanengapi = new Integer(kensaJissiDate);
		} catch (NumberFormatException e) {
		}

		List<TKensakekaSonota> resultList = null;
		try {
			String uketuke_id = validatedData.getUketuke_id();
			resultList = kensakekaSonotaDao.selectByUketukeIdKensaNengapi(
					new Long(uketuke_id),
					kensanengapi);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean exsistsKensaKekkaData = false;
		if (resultList != null &&  resultList.size() > 0) {
			exsistsKensaKekkaData = true;
		}

		return exsistsKensaKekkaData;
	}

	/*
	 * ����,���񂹃e�[�u���֓o�^���������{����B
	 */
	private void registerHistory(){

		TNayoseDao tNayoseDao = null;
		StringBuilder nayoseQuery = null;
		StringBuilder newNayoseQuery = null;

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

				retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedData.getUketukePre_id()));
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
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketukePre_id()));
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
							logger.warn(e.getMessage());
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
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketuke_id()));
				newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
				newNayoseQuery.append(") ");

				try {
					JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
				} catch (SQLException e) {
					logger.error(e.getMessage());
					try {
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


    /*
     * ���f�p�^�[�������_�C�A���O
     */
    private boolean serchKenshinDetailDialog(String koumokuCD){

    	boolean cancelFlg = false;


		// HashMap<String,ArrayList<String>> hm = JConstantString.getKensaHouhouMap();
    	HashMap<String,ArrayList<String>> hm = JConstantString.getKensaHouhouMap(koumokuCD);

    	// hm �� arraylist
    	Object[] objKey=hm.keySet().toArray();

    	String keyStr = (String)objKey[0];
    	// keyStr = keyStr.substring(0,keyStr.indexOf(","));

    	ArrayList<String> arr = hm.get(keyStr);

//    	for (int i = 0; i < arr.size(); i++) {
//			if (arr.get(i))
//		}

		try {
			patternSelectDialog = DialogFactory.getInstance().createDialog(this,arr);

			// ���f���{�����̓_�C�A���O��\��
			patternSelectDialog.setMessageTitle("�������@�I��");
			patternSelectDialog.setVisible(true);
			// �ߒl�i�[
			// eidt s.inoue 2012/07/06
			if (patternSelectDialog.getStatus() == null)return false;
			if(patternSelectDialog.getStatus().equals(RETURN_VALUE.YES)){
				System.out.println(patternSelectDialog.getTextValue());
				// jLabel_KensaKoumoku.setText(arr.get(0));
				jLabel_KensaHouhou_Value.setText(arr.get(0)+ " " + kensaHouhouCodeList.get(koumokuCD));

//				kenshinPatternNumver = patternSelectDialog.getPrintSelect();
			}else if (patternSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL)){
				cancelFlg = true;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			// eidt s.inoue 2012/07/06
			JErrorMessage.show("M3552", null);
		}

//		System.out.println("�I�����f�p�^�[��:" + kenshinPatternNumver);
		return cancelFlg;
    }

    /************************* �A�N�V�������� end *************************/
}

/************************* ���i *************************/
//jTextField_1[irowAll].addKeyListener(new KeyAdapter() {
//public void keyPressed(KeyEvent e) {
//	int keycode = e.getKeyCode();
//    int mod = e.getModifiersEx();
//    int rangeVal = 0;
//
//    if ((keycode == KeyEvent.VK_ENTER) &&
//    			  (mod & InputEvent.SHIFT_DOWN_MASK) != 0){
//    	rangeVal = irow*12;
//	}else if (keycode == KeyEvent.VK_ENTER){
//		rangeVal = irow*20;
//	}
//		jScrollbarKihon.setValue(rangeVal);jScrollbarSyosai.setValue(rangeVal);
//    	jScrollbarTuika.setValue(rangeVal);jScrollbarMonshin.setValue(rangeVal);
//    }
//});
/************************* ���i *************************/
