package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * ��^���}�X�^�̕ҏW�̃R���g���[��
 */
public class JKekkaTeikeiMaintenanceEditFrameCtrl extends
		JKekkaTeikeiMaintenanceEditFrame {
	private static final int ADD_MODE = 1;
	private static final int CHANGE_MODE = 2;

	private String selectpreCombo ="";

	private JKekkaTeikeiMaintenanceEditFrameData validatedData = new JKekkaTeikeiMaintenanceEditFrameData();
	private int mode;

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	/* Enter �L�[�����Ńt�H�[�J�X���ړ�����R���|�[�l���g */
	private List<Component> enterkeyFocusComponents = null;

	private static org.apache.log4j.Logger logger = Logger.getLogger(JKekkaTeikeiMaintenanceEditFrameCtrl.class);

	/**
	 * �ی��ҏ��ǉ��̏ꍇ�̃R���X�g���N�^�i�J�ڌ��F�}�X�^�����e�i���X�j
	 */
	public JKekkaTeikeiMaintenanceEditFrameCtrl() {
		super();
		mode = ADD_MODE;
		// del s.inoue 2009/12/14
		// this.jTextField_TeikeibunNo.setText(String.valueOf(getNextNumber()));
		init();
	}

	/**
	 * ��^���ύX�̏ꍇ�̃R���X�g���N�^�i�J�ڌ��F�}�X�^�����e�i���X�j
	 */
	public JKekkaTeikeiMaintenanceEditFrameCtrl(String shubetuNo,String syokenNo) {
		super();
		mode = CHANGE_MODE;
		init();

		// �J�ڌ��t���[�����瓾����^No��������̃f�[�^���擾
		jTextArea_Teikeibun.setText(syokenNo);

		ArrayList<Hashtable<String, String>> Result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();

		StringBuilder buffer = new StringBuilder();
		buffer.append("SELECT SYOKEN_TYPE,");
// edit s.inoue 2010/04/26
//		buffer.append(" case SYOKEN_TYPE ");
//		buffer.append(" when 1 then '���̑��̊�����' ");
//		buffer.append(" when 2 then '���o�Ǐ󏊌�' ");
//		buffer.append(" when 3 then '���o�Ǐ󏊌�' ");
//		buffer.append(" when 4 then '�S�d�}����' ");
//		buffer.append(" end SYOKEN_TYPE_STR, ");
		buffer.append(" SYOKEN_NO, SYOKEN, UPDATE_TIMESTAMP");
		buffer.append(" FROM T_SYOKENMASTER WHERE SYOKEN_TYPE = ");
		buffer.append(JQueryConvert.queryConvert(shubetuNo));
		buffer.append(" AND SYOKEN_NO = ");
		buffer.append(JQueryConvert.queryConvert(syokenNo));

		try {
			Result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultItem = Result.get(0);

		// edit s.inoue 2010/04/26
		selectpreCombo = resultItem.get("SYOKEN_TYPE").toString();
		setSyokenCombobox(resultItem.get("SYOKEN_TYPE").toString());
//		setSyokenCombobox(resultItem.get("SYOKEN_TYPE_STR").toString());

		jTextField_TeikeibunNo.setText(resultItem.get("SYOKEN_NO").trim());
		jTextArea_Teikeibun.setText(resultItem.get("SYOKEN"));

		// del s.inoue 2009/12/15
		// jTextField_TeikeibunNo.setEditable(false);
		// jCombobox_TeikeibunType.setEnabled(false);
	}

	private void setSyokenCombobox(String syokencmb){
		switch (Integer.valueOf(syokencmb)) {
		case 1:
			jCombobox_TeikeibunType.setSelectedIndex(0);
			break;
		case 2:
			jCombobox_TeikeibunType.setSelectedIndex(1);
			break;
		case 3:
			jCombobox_TeikeibunType.setSelectedIndex(2);
			break;
		case 4:
			jCombobox_TeikeibunType.setSelectedIndex(3);
			break;
		}
	}

	private void initializeComboBox() {
		// ������ʐݒ�
		jCombobox_TeikeibunType.addItem("���̑��̊�����");
		jCombobox_TeikeibunType.addItem("���o�Ǐ󏊌�");
		jCombobox_TeikeibunType.addItem("���o�Ǐ󏊌�");
		jCombobox_TeikeibunType.addItem("�S�d�}����");
	}

	/**
	 * ������̕K�{���ڂ��`�F�b�N����
	 */
	public boolean validateAsPushedORCAButton(
			JHokenjyaMasterMaintenanceEditFrameData data) {
		if (data.getHokenjyaNumber().isEmpty()) {
			return false;
		}

		return true;
	}

	// edit s.inoue 2009/12/15
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
	private void init() {
		// edit s.inoue 2009/12/14
		initializeComboBox();

		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);

		this.focusTraversalPolicy.setDefaultComponent(jCombobox_TeikeibunType);
		this.focusTraversalPolicy.addComponent(jCombobox_TeikeibunType);
		this.focusTraversalPolicy.addComponent(jTextField_TeikeibunNo);
		this.focusTraversalPolicy.addComponent(jTextArea_Teikeibun);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		jTextArea_Teikeibun.setBackground(ViewSettings.getRequiedItemBgColor());

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
		String syokenIdx = String.valueOf(jCombobox_TeikeibunType.getSelectedIndex() + 1);

		return validatedData.setTeikeiType(syokenIdx)
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

			if (count > 0) {
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
			buffer.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE, SYOKEN_NO, SYOKEN, UPDATE_TIMESTAMP) ");

			buffer.append("VALUES ( "
							+ JQueryConvert.queryConvertAppendComma(inputTeikeiShubetu)
							+ JQueryConvert.queryConvertAppendComma(inputTeikeiNumber)
							+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun())
							+ JQueryConvert.queryConvert(curtimeStamp)
							+ ")");
		}

		if (mode == CHANGE_MODE) {
			buffer.append("UPDATE T_SYOKENMASTER SET "
					+ " SYOKEN_TYPE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiType())
					+ " SYOKEN_NO = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiNumber())
					+ " SYOKEN = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun())
					+ " UPDATE_TIMESTAMP = " + JQueryConvert.queryConvert(curtimeStamp)
					// edit s.inoue 2010/04/26
					// validatedData.getTeikeiType()
					+ " WHERE SYOKEN_TYPE = "+ JQueryConvert.queryConvert(selectpreCombo)
					+ " AND SYOKEN_NO = "+ JQueryConvert.queryConvert(validatedData.getTeikeiNumber()));
		}

		try {
			JApplication.kikanDatabase.Transaction();
			JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
			JApplication.kikanDatabase.Commit();
			registered = true;
			// del s.inoue 2009/12/15
			// lastAddedNumber = validatedData.getTeikeiNumber();
			dispose();
		} catch (SQLException f) {
			f.printStackTrace();
			JErrorMessage.show("M9910", this);
			try {
				JApplication.kikanDatabase.rollback();
				return;
			} catch (SQLException g) {
				g.printStackTrace();
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
	}

	/**
	 * �o�^�{�^�����������ۂ̕K�{���ڂ̌���
	 */
	protected boolean validateAsRegisterPushed(
			JKekkaTeikeiMaintenanceEditFrameData data) {
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
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
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
		case KeyEvent.VK_F12:
			logger.info(jButton_Register.getText());
			pushedRegisterButton(null);break;
		}
	}


}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

