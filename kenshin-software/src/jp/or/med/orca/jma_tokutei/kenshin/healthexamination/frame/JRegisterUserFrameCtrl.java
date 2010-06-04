package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

public class JRegisterUserFrameCtrl extends JRegisterUserFrame {
	static final int ADD_MODE = 1;
	static final int CHANGE_MODE = 2;

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JRegisterUserFrameCtrl.class);

	/**
	 * ���s���Ă���t���[���̃��[�h �P�F���[�U�̒ǉ� �Q�F���[�U�̕ύX
	 */
	int mode;

	JRegisterUserFrameData validatedData = new JRegisterUserFrameData();

	/**
	 * �o�^�{�^�����������ۂ̕K�{���ڂ�����
	 */
	public boolean validateAsRegisterPushed(JRegisterUserFrameData data) {
		if (data.getKengen().equals("")) {
			JErrorMessage.show("M4801", this);
			return false;
		}

		if (data.getPassword().equals("")) {
			JErrorMessage.show("M4802", this);
			return false;
		}

		if (data.getUserName().equals("")) {
			JErrorMessage.show("M4803", this);
			return false;
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * �V�K���[�U�ǉ����̃R���X�g���N�^
	 */
	public JRegisterUserFrameCtrl() {
		super();
		mode = ADD_MODE;
		init();
	}

	/**
	 * ���[�U���ύX���̃R���X�g���N�^
	 */
	public JRegisterUserFrameCtrl(String userName) {
		mode = CHANGE_MODE;
		init();

		jTextField_UserName.setText(userName);
		jTextField_UserName.setEditable(false);

		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from t_user where user_name = ?");
		String query = buffer.toString();

		String[] params = { userName };

		ArrayList<Hashtable<String, String>> result = null;
		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query, params);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (result != null && ! result.isEmpty()) {
			Hashtable<String, String> item = result.get(0);
			String dbPassword = item.get("PASS");
			String dbKengen = item.get("KENGEN");

			/* ������ݒ肷��B */
			String kengenItem = null;
			if (dbKengen.equals("1")) {
				kengenItem = JValidate.USER_KENGEN_KANRISYA;
			}
			else {
				kengenItem = JValidate.USER_KENGEN_IPANUSER;
			}

			/* �p�X���[�h��ݒ肷��B */
			this.jPasswordField_Password.setText(dbPassword);
			this.jComboBox_Grant.setSelectedItem(kengenItem);
		}
		// add s.inoue 2009/12/07
		// focus�����ݒ�
		this.addComponentListener(new ComponentAdapter()
		{
		    public void componentShown(ComponentEvent e)
		    {
		        // ensure password text field has focus initially
		    	jPasswordField_Password.requestFocusInWindow();
		    }
		});

	}

	/**
	 * ������
	 */
	public void init() {
		jButton_Register.setEnabled(false);

		jComboBox_Grant.addItem("��ʃ��[�U");
		jComboBox_Grant.addItem("�Ǘ���");

		// ���O�C���{�^���̏�Ԃ��Ǘ�
		jTextField_UserName.getDocument().addDocumentListener(new LoginButtonStatus());
		jPasswordField_Password.getDocument().addDocumentListener(new LoginButtonStatus());
		// edit s.inoue 2010/03/24
		functionListner();
	}

	private void functionListner(){

		// focus����
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		// edit s.inoue 2010/03/24
		if (mode == ADD_MODE) {
			this.focusTraversalPolicy.setDefaultComponent(this.jTextField_UserName);
			this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
			this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		}else if (mode == CHANGE_MODE){
			this.focusTraversalPolicy.setDefaultComponent(this.jPasswordField_Password);
			this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		}
		// del s.inoue 2010/03/24
		// this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
		// this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		this.focusTraversalPolicy.addComponent(this.jComboBox_Grant);
		this.focusTraversalPolicy.addComponent(this.jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * �o�^�{�^��������
	 */
	public void pushedRegisterButton(ActionEvent e) {
		if (validatedData.setUserName(jTextField_UserName.getText())
				&& validatedData.setPassword(new String(jPasswordField_Password
						.getPassword()))
				&& validatedData.setKengen((String) jComboBox_Grant
						.getSelectedItem())) {
			if (validateAsRegisterPushed(validatedData)) {

				if (validatedData.isValidateAsDataSet()) {

					String Query = null;

					// �V�K���[�U�ǉ��̏ꍇ�̃N�G��
					if (mode == ADD_MODE) {
						// add s.inoue 20081217
						// ���[�U���̂̏d���`�F�b�N
						try
						{
							ArrayList kikanItem =  JApplication.kikanDatabase.sendExecuteQuery(
									"SELECT * FROM T_USER WHERE USER_NAME =" +
									JQueryConvert.queryConvert(validatedData.getUserName()));

							if (kikanItem.size() > 0){
								JErrorMessage.show("M9638", this);
								return;
							}

						}catch(Exception err){
							err.printStackTrace();
							JErrorMessage.show("M9601", this);
							return;
						}

						Query = new String(
								"INSERT INTO T_USER (USER_NAME,PASS,KENGEN) VALUES ( "
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getUserName())
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getPassword())
										+ JQueryConvert
												.queryConvert(validatedData
														.getKengen()) + ")");
					}

					// �������[�U���ύX�̏ꍇ�̃N�G��
					if (mode == CHANGE_MODE) {
						Query = new String("UPDATE T_USER SET USER_NAME = "
								+ JQueryConvert
										.queryConvertAppendComma(validatedData
												.getUserName())
								+ "PASS = "
								+ JQueryConvert
										.queryConvertAppendComma(validatedData
												.getPassword())
								+ "KENGEN = "
								+ JQueryConvert.queryConvert(validatedData
										.getKengen())
								+ "WHERE USER_NAME = "
								+ JQueryConvert.queryConvert(validatedData
										.getUserName()));
					}

					try {
						JApplication.kikanDatabase.sendNoResultQuery(Query);
						dispose();

					} catch (SQLException f) {
						f.printStackTrace();
					}
				}
			}
		}
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
		// �G���^�[�L�[�ł̈ړ�
		else if (source == jTextField_UserName) {
			jTextField_UserName.transferFocus();
		}
		else if (source == jPasswordField_Password) {
			jPasswordField_Password.transferFocus();
		}
// del s.inoue 2009/12/04
//		else if (source == jComboBox_Grant) {
//			// �o�^�{�^�����L���ł������珈�����s��
//			if (jButton_Register.isEnabled()) {
//				pushedRegisterButton(null);
//			}
//		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			if (jButton_Register.isEnabled()){
				logger.info(jButton_Register.getText());
				pushedRegisterButton(null);break;
			}
		}
	}

	/**
	 * �o�^�{�^���̃X�e�[�^�X���e�L�X�g�t�B�[���h�̓��e�ɂ���� �ύX���邽�߂̃C�x���g���X�i
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
	 * �o�^�{�^���̃X�e�[�^�X�`�F�b�N���s���B LoginButtonStatus�C�x���g����Ăяo�����߂̊֐��B
	 */
	public void setLoginButtonStatus(DocumentEvent e) {
		// ���O�C���{�^���̗L����
		if (e.getDocument() == jTextField_UserName.getDocument()
				|| e.getDocument() == jPasswordField_Password.getDocument()) {
			if (jTextField_UserName.getText().length() > 0
					&& (new String(jPasswordField_Password.getPassword()))
							.length() > 0) {
				jButton_Register.setEnabled(true);
			} else {
				jButton_Register.setEnabled(false);
			}
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

