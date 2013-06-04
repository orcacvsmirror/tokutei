package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JComponent;

import org.apache.log4j.Logger;
import org.openswing.swing.client.ReloadButton;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JNayoseMaintenanceListFrameCtl.WindowRefreshEvent;

/**
 * ��^���}�X�^�̕ҏW�̃R���g���[��
 */
public class JShokenMasterMaintenanceEditFrameCtrl extends
		JShokenMasterMaintenanceEditFrame {
	private static final int ADD_MODE = 1;
	private static final int CHANGE_MODE = 2;

	private String selectpreCombo ="";
	// edit s.inoue 2010/05/19
	private String preInputSyokenNo = "";

	private JShokenMasterMaintenanceEditFrameData validatedData = new JShokenMasterMaintenanceEditFrameData();
	private int mode;

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	/* Enter �L�[�����Ńt�H�[�J�X���ړ�����R���|�[�l���g */
	private List<Component> enterkeyFocusComponents = null;

	private static org.apache.log4j.Logger logger = Logger.getLogger(JShokenMasterMaintenanceEditFrameCtrl.class);

	/**
	 * �ی��ҏ��ǉ��̏ꍇ�̃R���X�g���N�^�i�J�ڌ��F�}�X�^�����e�i���X�j
	 */
	public JShokenMasterMaintenanceEditFrameCtrl(String maxShubetuNo) {
		super();
		mode = ADD_MODE;
		init(true);

		// add s.inoue 2013/03/01
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}

		// edit s.inoue 2010/05/18
		jTextField_ShubetuNumber.setText(maxShubetuNo);
		jTextField_TeikeibunNo.setText("01");
//		jCombobox_TeikeibunType.setVisible(false);
	}

	/**
	 * ��^���ύX�̏ꍇ�̃R���X�g���N�^�i�J�ڌ��F�}�X�^�����e�i���X�j
	 */
	public JShokenMasterMaintenanceEditFrameCtrl(String shubetuNo,String syokenNo) {
		super();
		mode = CHANGE_MODE;
		init(false);

		// add s.inoue 2013/03/01
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}

		// �J�ڌ��t���[�����瓾����^No��������̃f�[�^���擾
		jTextArea_Teikeibun.setText(syokenNo);
		// edit s.inoue 2010/05/19
		preInputSyokenNo = syokenNo;

		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();

		StringBuilder buffer = new StringBuilder();
		buffer.append("SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME,");
		buffer.append(" SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP");
		buffer.append(" FROM T_SYOKENMASTER WHERE SYOKEN_TYPE = ");
		buffer.append(JQueryConvert.queryConvert(shubetuNo));
		buffer.append(" AND SYOKEN_NO = ");
		buffer.append(JQueryConvert.queryConvert(syokenNo));

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		resultItem = result.get(0);

		selectpreCombo = resultItem.get("SYOKEN_TYPE").toString();
// del s.inoue 2010/05/18
//		setSyokenCombobox(resultItem.get("SYOKEN_TYPE_STR").toString());
		jTextField_ShubetuNumber.setText(shubetuNo);
		jTextField_TeikeibunNo.setText(resultItem.get("SYOKEN_NO").trim());
		jTextArea_Teikeibun.setText(resultItem.get("SYOKEN_NAME"));
		// edit s.inoue 2010/05/18
		jTextField_TeikeibunType.setText(resultItem.get("SYOKEN_TYPE_NAME"));
//		jCombobox_TeikeibunType.setVisible(false);

		// move s.inoue 2010/05/18
//		initializeComboBox();

		// del s.inoue 2009/12/15
		// jTextField_TeikeibunNo.setEditable(false);
		// jCombobox_TeikeibunType.setEnabled(false);
	}

//	private void setSyokenCombobox(String syokencmb){
//		switch (Integer.valueOf(syokencmb)) {
//		case 1:
//			jCombobox_TeikeibunType.setSelectedIndex(0);
//			break;
//		case 2:
//			jCombobox_TeikeibunType.setSelectedIndex(1);
//			break;
//		case 3:
//			jCombobox_TeikeibunType.setSelectedIndex(2);
//			break;
//		case 4:
//			jCombobox_TeikeibunType.setSelectedIndex(3);
//			break;
//		}
//	}

// edit s.inoue 2010/05/18
//	private void initializeComboBox() {
//		// ������ʐݒ�
//		jCombobox_TeikeibunType.addItem("���̑��̊�����");
//		jCombobox_TeikeibunType.addItem("���o�Ǐ󏊌�");
//		jCombobox_TeikeibunType.addItem("���o�Ǐ󏊌�");
//		jCombobox_TeikeibunType.addItem("�S�d�}����");
//	}

//	private void initializeComboBox() {
//
//		ArrayList<Hashtable<String, String>> result
//			= new ArrayList<Hashtable<String, String>>();
//		Hashtable<String, String> resultItem = new Hashtable<String, String>();
//
//		StringBuilder buffer = new StringBuilder();
//
//		buffer.append(" SELECT distinct SYOKEN_TYPE, SYOKEN_TYPE_NAME");
//		buffer.append(" FROM T_SYOKENMASTER");
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		for (int i = 0; i < result.size(); i++) {
//			resultItem = result.get(i);
//			String selectType = resultItem.get("SYOKEN_TYPE").toString() +
//			":" + resultItem.get("SYOKEN_TYPE_NAME").toString();
//
//			this.jCombobox_TeikeibunType.addItem(selectType);
//		}
//
//	}

	/**
	 * ������̕K�{���ڂ��`�F�b�N����
	 */
	public boolean validateAsPushedORCAButton(
			JShokenMasterMaintenanceEditFrameData data) {
		if (data.getTeikeiNumber().isEmpty()) {
			return false;
		}
		return true;
	}

	// �}�E�X���X�i�̃��\�b�h���`
	@Override
	public void mousePressed(MouseEvent e) {
		mousePopup(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}
	private void mousePopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			// �|�b�v�A�b�v���j���[��\������
			JComponent c = (JComponent)e.getSource();
			jTextArea_Teikeibun.showPopup(c, e.getX(), e.getY());
			e.consume();
		}
	}

	/**
	 * ������
	 */
	private void init(boolean initFlg) {

		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);

		// edit s.inoue 2010/05/18
		if (initFlg){
			this.focusTraversalPolicy.setDefaultComponent(jTextField_TeikeibunType);
			this.focusTraversalPolicy.addComponent(jTextField_TeikeibunType);
//			jTextField_TeikeibunType.setBackground(ViewSettings.getRequiedItemBgColor());
//			jTextField_ShubetuNumber.setBackground(ViewSettings.getRequiedItemBgColor());
			jButton_Add.setVisible(false);
			jTextField_TeikeibunNo.setEditable(false);
			jTextField_ShubetuNumber.setEditable(false);
		}else{
			this.focusTraversalPolicy.setDefaultComponent(jTextField_TeikeibunNo);
			this.focusTraversalPolicy.addComponent(jTextField_TeikeibunNo);
			jTextField_ShubetuNumber.setEditable(false);
			jTextField_TeikeibunType.setEditable(false);
			// del s.inoue 2012/07/05
			// jTextField_TeikeibunNo.setBackground(ViewSettings.getRequiedItemBgColor());
		}

		this.focusTraversalPolicy.addComponent(jTextArea_Teikeibun);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		// del s.inoue 2010/05/19
//		jCombobox_TeikeibunType.setBackground(ViewSettings.getRequiedItemBgColor());
//		jTextField_TeikeibunNo.setBackground(ViewSettings.getRequiedItemBgColor());
	}

// edit s.inoue 2009/12/14
// �����ԍ��̓R�[�h�����ׁ̈A�d���`�F�b�N�݂̂Ƃ���
//	/**
//	 * ��^���ԍ��̋󂫔ԍ��̎擾
//	 */
//	private int getNextNumber() {
//		ArrayList<Hashtable<String, String>> Items;
//		try {
//			Items = JApplication.kikanDatabase
//					.sendExecuteQuery("SELECT SYOKEN_NO FROM T_SYOKENMASTER");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JErrorMessage.show("M9904", this);
//			return -1;
//		}
//
//		for (int i = 1; i < Integer.MAX_VALUE; i++) {
//			boolean FindFlag = false;
//
//			for (int j = 0; j < Items.size(); j++) {
//				if (Items.get(j).get("SYOKEN_NO").equals(String.valueOf(i))) {
//					FindFlag = true;
//				}
//			}
//
//			// true�łȂ���΋󂫂�����Ƃ���B
//			if (FindFlag == false) {
//				return i;
//			}
//		}
//
//		return -1;
//	}

	private static String lastAddedNumber = null;

	public static String getLastAddedNumber() {
		return lastAddedNumber;
	}

	private static boolean registered = false;
	public static boolean isRegistered() {
		return registered;
	}

	/* ���͒l���� */
	private boolean validateData() {
		// edit s.inoue 2010/05/19
//		String syokenIdx = String.valueOf(jCombobox_TeikeibunType.getSelectedIndex() + 1);
		return validatedData.setTeikeiType(jTextField_ShubetuNumber.getText())
				&& validatedData.setTeikeiTypeName(jTextField_TeikeibunType.getText())
				&& validatedData.setTeikeiNumber(jTextField_TeikeibunNo.getText())
				&& validatedData.setTeikeibun(jTextArea_Teikeibun.getText());
	}

	/**
	 * �o�^�������s��
	 */
	public void register() {
		if (!this.validateData()) {
			return;
		}

		// �e�t�B�[���h�ɂ��Č��؍ς�
		if (!validateAsRegisterPushed(validatedData)) {
			return;
		}

		// �K�{���ڂɂ��Ă��������Ȃ�
		if (!validatedData.isValidateAsDataSet()) {
			return;
		}
		String inputTeikeiShubetu = validatedData.getTeikeiType();
		String inputTeikeiNumber = validatedData.getTeikeiNumber();

		String sql = "select count(SYOKEN_NO) as ROWCOUNT from T_SYOKENMASTER where SYOKEN_TYPE = ? and SYOKEN_NO = ?";
		String[] params = { inputTeikeiShubetu,inputTeikeiNumber };

		ArrayList<Hashtable<String, String>> result = null;
		try{
			result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
		}catch(SQLException e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		boolean existsNumber = false;
		if (result != null && result.size() > 0) {
			String countString = result.get(0).get("ROWCOUNT");

			int count = 0;
			try {
				count = Integer.valueOf(countString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			// edit s.inoue 2010/05/19
			if (count == 0){
				mode = ADD_MODE;
			}else if (count > 0) {
				// edit s.inoue 2010/05/19
				if (!preInputSyokenNo.equals(jTextField_TeikeibunNo.getText())){
					RETURN_VALUE retValue = JErrorMessage.show("M9922", this);
					if (retValue != RETURN_VALUE.YES) {
						return;
					}
				}
					existsNumber = true;
			}
		}

		if (mode == ADD_MODE && existsNumber){
			JErrorMessage.show("M9905", this);
			return;
		}

		StringBuffer buffer = new StringBuffer();
		// ���������擾
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String curtimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		if (mode == ADD_MODE) {
			buffer.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE, SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP) ");

			buffer.append("VALUES ( "
							+ JQueryConvert.queryConvertAppendComma(inputTeikeiShubetu)
							+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiTypeName())
							+ JQueryConvert.queryConvertAppendComma(inputTeikeiNumber)
							+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun())
							+ JQueryConvert.queryConvert(curtimeStamp)
							+ ")");
		}

		if (mode == CHANGE_MODE) {
			buffer.append("UPDATE T_SYOKENMASTER SET ");
			// del s.inoue 2010/05/19
			// buffer.append(" SYOKEN_TYPE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiType()));
			buffer.append(" SYOKEN_NO = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiNumber()));
			buffer.append(" SYOKEN_NAME = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun()));
			buffer.append(" UPDATE_TIMESTAMP = " + JQueryConvert.queryConvert(curtimeStamp));
			buffer.append(" WHERE SYOKEN_TYPE = "+ JQueryConvert.queryConvert(selectpreCombo));
			buffer.append(" AND SYOKEN_NO = "+ JQueryConvert.queryConvert(validatedData.getTeikeiNumber()));
		}

		try {
			// eidt s.inoue 2013/03/01
//			JApplication.kikanDatabase.Transaction();
//			JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//			JApplication.kikanDatabase.Commit();

			JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
			JApplication.kikanDatabase.getMConnection().commit();

			registered = true;
			// del s.inoue 2009/12/15
			// lastAddedNumber = validatedData.getTeikeiNumber();
			dispose();
		} catch (SQLException f) {
			f.printStackTrace();
			JErrorMessage.show("M9910", this);
			try {
				// eidt s.inoue 2013/03/01
				// JApplication.kikanDatabase.rollback();
				JApplication.kikanDatabase.getMConnection().rollback();
				return;
			} catch (SQLException g) {
				g.printStackTrace();
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
	}

	/**
	 * ����No�̋󂫔ԍ��̎擾
	 */
	private String getNextSyokenNumber(String shubetuNo) {
		ArrayList<Hashtable<String, String>> Items;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT SYOKEN_NO FROM T_SYOKENMASTER ");
			sb.append("WHERE SYOKEN_TYPE = ");
			sb.append(JQueryConvert.queryConvert(shubetuNo));
			Items = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M9919", this);
			return null;
		}

		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			boolean FindFlag = false;

			for (int j = 0; j < Items.size(); j++) {
				String strVal = String.valueOf(i);
				strVal = (strVal.length() == 1)? "0" + strVal: strVal;
				if (Items.get(j).get("SYOKEN_NO").equals(strVal)) {
					FindFlag = true;
				}
			}

			// true�łȂ���΋󂫂�����Ƃ���B
			if (FindFlag == false) {
				// edit s.inoue 2010/05/19
				String ret = String.valueOf(i);
				ret = (ret.length() == 1)?"0" + ret:ret;
				return ret;
			}
		}

		return null;
	}

	/**
	 * �o�^�{�^�����������ۂ̕K�{���ڂ̌���
	 */
	protected boolean validateAsRegisterPushed(
			JShokenMasterMaintenanceEditFrameData data) {
		if (data.getTeikeiNumber().equals("")) {
			JErrorMessage.show("M9902", null);
			return false;
		}

		validatedData.setValidateAsDataSet();
		return true;
	}

	/**
	 * �o�^�{�^��
	 */
	public void pushedRegisterButton(ActionEvent e) {
		register();
	}

	/**
	 * �ǉ��{�^��
	 */
	public void pushedAddButton(ActionEvent e) {
		jTextField_TeikeibunNo.setText(getNextSyokenNumber(this.jTextField_ShubetuNumber.getText()));
		jTextArea_Teikeibun.setText("");
		jTextArea_Teikeibun.grabFocus();
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
	 */
	public class WindowRefreshEvent extends WindowAdapter
	{
		public void windowClosed(WindowEvent e)
		{
			//�e�[�u���̍ēǂݍ��݂��s��
			init(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		// edit s.inoue 2010/05/18
		else if (source == jButton_Add) {
			logger.info(jButton_Add.getText());
			pushedAddButton(e);
		}
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);
		}
	}
	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Add.getText());
			pushedAddButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Register.getText());
			pushedRegisterButton(null);break;
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

