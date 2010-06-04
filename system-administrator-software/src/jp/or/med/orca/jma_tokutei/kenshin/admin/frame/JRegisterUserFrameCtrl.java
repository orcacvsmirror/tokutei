package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * ���[�U���̓o�^�A�ύX���s�����߂̃N���X�B �R���X�g���N�^�֓n�������ɂ���ē��삪�ύX�����B �ڂ����́A�e�R���X�g���N�^�̐������Q�Ƃ��Ă��������B
 */
public class JRegisterUserFrameCtrl extends JRegisterUserFrame {
	JRegisterUserFrameData validatedData = new JRegisterUserFrameData();

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private static Logger logger = Logger.getLogger(JRegisterUserFrameCtrl.class);

	/**
	 * ���̃t���[���̎��s����Ă��郂�[�h 1 : ���[�U�ǉ� 2 : ���[�U���̕ύX
	 */
	int mode = 0;
	int ADD_MODE = 1;
	int CHANGE_MODE = 2;

	boolean validateAsRegisterPushed(JRegisterUserFrameData data) {
		if (data.getPassword().isEmpty() || data.getUserName().isEmpty()) {
			JErrorMessage.show("M7310", this);
			return false;
		}

		data.isValidateAsDataset();
		initializefocus();
		// del y.okano 2010/05/24
		// functionListner();
		return true;
	}

	private void initializefocus(){
		// focus����
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		// edit s.inoue 2009/12/03
		if (mode == ADD_MODE){
			this.focusTraversalPolicy.setDefaultComponent(this.jTextField_UserName);
		}else if (mode == CHANGE_MODE){
			this.focusTraversalPolicy.setDefaultComponent(this.jPasswordField_Password);
		}
		this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
		this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		this.focusTraversalPolicy.addComponent(this.jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);
	}

// edit y.okano 2010/05/24
	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			System.out.println(comp.getName());
			comp.addKeyListener(this);
		}
//		Component comp =null;
//		HashMap<String, Component> compHm = new HashMap<String, Component>();
//
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//
//			if (!compHm.containsKey(comp.getName())){
//				comp.addKeyListener(this);
//				compHm.put(comp.getName(), comp);
//			}
//		}
	}

	/**
	 * ���[�U�̒ǉ����s���Ƃ��Ɏg�p�B
	 */
	public JRegisterUserFrameCtrl() {
		// edit s.inoue 2009/12/03
		mode = ADD_MODE;
		jPasswordField_Password.getDocument().addDocumentListener(new RegisterButtonStatus());
		jTextField_UserName.getDocument().addDocumentListener(new RegisterButtonStatus());
		jButton_Register.setEnabled(false);

		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());
		initializefocus();
		// add s.inoue 2009/12/03
		functionListner();
	}

	/**
	 * ���[�U�̕ύX���s���Ƃ��Ɏg�p�B
	 */
	public JRegisterUserFrameCtrl(String name) {
		super();
		mode = CHANGE_MODE;

		jPasswordField_Password.addKeyListener(this);
		validatedData.setUserName(name);

		// ���O�C���{�^���̏�Ԃ��Ǘ�
		jTextField_UserName.getDocument().addDocumentListener(new RegisterButtonStatus());
		jPasswordField_Password.getDocument().addDocumentListener(new RegisterButtonStatus());

		jTextField_UserName.setText(validatedData.getUserName());
		jTextField_UserName.setEditable(false);
		jButton_Register.setEnabled(false);

		// �G���^�[�L�[�̏���
		// del s.inoue 2009/12/04
//		jButton_End.addKeyListener(new JEnterEvent(this, jButton_End));
//		jButton_Register.addKeyListener(new JEnterEvent(this, jButton_Register));
		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());
		initializefocus();
		functionListner();

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
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * �o�^�{�^��
	 */
	public void pushedRegisterButton(ActionEvent e) {
		if (validatedData.setPassword(new String(jPasswordField_Password
				.getPassword()))
				&& validatedData.setUserName(jTextField_UserName.getText())) {
			if (validateAsRegisterPushed(validatedData)) {
				try {
					// �V�K�ǉ��̏ꍇ
					if (mode == ADD_MODE) {

						// add s.inoue 20081217
						// ���[�U���̂̏d���`�F�b�N
						try
						{
							ArrayList kikanItem =  JApplication.systemDatabase.sendExecuteQuery(
									"SELECT * FROM T_SYS_USER WHERE USER_NAME =" +
									JQueryConvert.queryConvert(validatedData.getUserName()));

							if (kikanItem.size() > 0){
								JErrorMessage.show("M7303", this);
								return;
							}

						}catch(Exception err){
							err.printStackTrace();
							JErrorMessage.show("M9601", this);
							return;
						}

						JApplication.systemDatabase
								.sendNoResultQuery("INSERT INTO T_SYS_USER VALUES ("
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getUserName())
										+ JQueryConvert
												.queryConvert(validatedData
														.getPassword()) + ")");
						dispose();
					}

					// �ύX�̏ꍇ
					if (mode == CHANGE_MODE) {
						JApplication.systemDatabase
								.sendNoResultQuery("UPDATE T_SYS_USER SET PASS = "
										+ JQueryConvert
												.queryConvert(validatedData
														.getPassword())
										+ " WHERE USER_NAME = "
										+ JQueryConvert
												.queryConvert(validatedData
														.getUserName()));
						dispose();
					}

				} catch (Exception e2) {
					e2.printStackTrace();
					jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
							.show("M7300", this);
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
		// del s.inoue 2009/12/06
		//			jTextField_UserName.transferFocus();
		} else if (source == jPasswordField_Password) {
			// �o�^�{�^�����L���Ȏ�����Enter�L�[�Ń��O�C�����s��
			if (jButton_Register.isEnabled())
				pushedRegisterButton(null);
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
			if (jButton_Register.isEnabled()){
				logger.info(jButton_Register.getText());
				pushedRegisterButton(null);break;
			}
		}
	}
	/**
	 * �o�^�{�^���̃X�e�[�^�X���e�L�X�g�t�B�[���h�̓��e�ɂ���� �ύX���邽�߂̃C�x���g���X�i
	 */
	public class RegisterButtonStatus implements DocumentListener {
		public void changedUpdate(DocumentEvent arg0) {
		}

		public void insertUpdate(DocumentEvent e) {
			setRegisterButtonStatus(e);
		}

		public void removeUpdate(DocumentEvent e) {
			setRegisterButtonStatus(e);
		}
	}

	/**
	 * �o�^�{�^���̃X�e�[�^�X�`�F�b�N���s���B�C�x���g����Ăяo�����߂̊֐��B
	 */
	public void setRegisterButtonStatus(DocumentEvent e) {
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

// Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}
