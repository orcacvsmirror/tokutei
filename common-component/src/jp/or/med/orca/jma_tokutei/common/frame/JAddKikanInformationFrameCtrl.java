package jp.or.med.orca.jma_tokutei.common.frame;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.orca.JORCASetting;
import jp.or.med.orca.jma_tokutei.common.sql.*;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/**
 * �@�֏��̒ǉ��t���[���̃R���g���[��
 */
public class JAddKikanInformationFrameCtrl extends JAddKikanInformationFrame {
	protected JConnection systemDatabase = null;
	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JAddKikanInformationFrameCtrl.class);

	/* �K�{���ڂ̃R���|�[�l���g */
	private JComponent[] requiredComponents = new JComponent[]{
			jTextField_kikanNumber,
			jTextField_kikanName,
			jTextField_souhumotoNumber,
			jTextField_zip,
			jTextField_address,
//			jTextField_address2,
			jTextField_tel,

//			jTextField_ip,
//			jTextField_portNumber,
//			jTextField_databaseName,
//			jTextField_protocol,
//			jTextField_dbUserId,
//			jTextField_dbPassword,
//			jTextField_nichireseUserId,
//			jTextField_nichiresePassword,
//			jTextField_encoding,
	};

	/* �����Z�̃R���|�[�l���g */
	private JComponent[] nichireseComponents = new JComponent[]{
			jTextField_ip,
			jTextField_portNumber,
			jTextField_databaseName,
			jTextField_protocol,
			jTextField_dbUserId,
			jTextField_dbPassword,
			jTextField_nichireseUserId,
			jTextField_nichiresePassword,
			jTextField_encoding,
			this.jRadioButton_Yes1,
			this.jRadioButton_No1,
			this.jTextField_orcaIdDigit,
	};

	/* �����Z�̃R���|�[�l���g�ŕK�{�̂��� */
	private JComponent[] nichireseRequiredComponents = new JComponent[]{
			jTextField_ip,
			jTextField_portNumber,
			jTextField_databaseName,
			jTextField_protocol,
			jTextField_dbUserId,
//			jTextField_dbPassword,
			jTextField_nichireseUserId,
//			jTextField_nichiresePassword,
			jTextField_encoding,
	};

	protected JAddKikanInformationFrameData validatedData = new JAddKikanInformationFrameData();
	private ArrayList<JTextField> inputFields = null;

	/**
	 * @param SystemDatabase
	 *            �ڑ�����V�X�e���f�[�^�x�[�X
	 */
	// edit s.inoue 2009/10/15
	public JAddKikanInformationFrameCtrl(JConnection SystemDatabase,boolean addFlg) {
		super();
		this.systemDatabase = SystemDatabase;

		this.inputFields = new ArrayList<JTextField>();
		inputFields.add(this.jTextField_kikanNumber);
		inputFields.add(this.jTextField_souhumotoNumber);
		inputFields.add(this.jTextField_kikanName);
		inputFields.add(this.jTextField_zip);
		inputFields.add(this.jTextField_address);
		inputFields.add(this.jTextField_address2);
		inputFields.add(this.jTextField_tel);
		inputFields.add(this.jTextField_ip);
		inputFields.add(this.jTextField_portNumber);
		inputFields.add(this.jTextField_databaseName);
		inputFields.add(this.jTextField_protocol);
		inputFields.add(this.jTextField_dbUserId);
		inputFields.add(this.jTextField_dbPassword);
		inputFields.add(this.jTextField_nichireseUserId);
		inputFields.add(this.jTextField_nichiresePassword);
		inputFields.add(this.jTextField_encoding);

		jRadioButton_No.setSelected(true);
		jRadioButton_No1.setSelected(true);

		/* �@�֔ԍ����͗����t�H�[�J�X���������Ƃ��A
		 * ���͒l�𑗕t���@�֔ԍ��ɃR�s�[����B */
		this.jTextField_kikanNumber.addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent arg0) {
				/* �t�H�[�J�X���������Ƃ��A���͒l�𑗕t���@�֔ԍ��ɃR�s�[����B */
				// edit s.inoue 20081216 �V�K���݂̂ɒl���Z�b�g����
				if (jTextField_souhumotoNumber.getText().equals("")){
					String kikanNumber = JAddKikanInformationFrameCtrl.this.jTextField_kikanNumber.getText();
					JAddKikanInformationFrameCtrl.this.jTextField_souhumotoNumber.setText(kikanNumber);
				}
			}
		});

		// edit s.inoue 2009/09/30
		// �t�H�[�J�X����
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		// add or edit
		if (addFlg) {
			this.focusTraversalPolicy.setDefaultComponent(jTextField_kikanNumber);
		}else{
			this.focusTraversalPolicy.setDefaultComponent(jTextField_souhumotoNumber);
		}

		this.focusTraversalPolicy.addComponent(jTextField_kikanNumber);
		this.focusTraversalPolicy.addComponent(jTextField_souhumotoNumber);
		this.focusTraversalPolicy.addComponent(jTextField_kikanName);
		this.focusTraversalPolicy.addComponent(jTextField_zip);
		this.focusTraversalPolicy.addComponent(jTextField_address);
		this.focusTraversalPolicy.addComponent(jTextField_address2);
		this.focusTraversalPolicy.addComponent(jTextField_tel);
		this.focusTraversalPolicy.addComponent(jRadioButton_Yes);
		this.focusTraversalPolicy.addComponent(jRadioButton_No);
		this.focusTraversalPolicy.addComponent(jTextField_ip);
		this.focusTraversalPolicy.addComponent(jTextField_portNumber);
		this.focusTraversalPolicy.addComponent(jTextField_databaseName);
		this.focusTraversalPolicy.addComponent(jTextField_protocol);
		this.focusTraversalPolicy.addComponent(jTextField_dbUserId);
		this.focusTraversalPolicy.addComponent(jTextField_dbPassword);
		this.focusTraversalPolicy.addComponent(jTextField_nichireseUserId);
		this.focusTraversalPolicy.addComponent(jTextField_nichiresePassword );
		this.focusTraversalPolicy.addComponent(jTextField_encoding);
		this.focusTraversalPolicy.addComponent(jRadioButton_Yes1);
		this.focusTraversalPolicy.addComponent(jRadioButton_No1);
		this.focusTraversalPolicy.addComponent(jTextField_orcaIdDigit);
		this.focusTraversalPolicy.addComponent(jButton_ConnectionTest);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		Color requiedItemColor = ViewSettings.getRequiedItemBgColor();
		jLabel_requiredExample.setBackground(requiedItemColor);

		for (int i = 0; i < requiredComponents.length; i++) {
			requiredComponents[i].setBackground(requiedItemColor);
		}
	}

	/**
	 * �o�^�{�^�����������ۂ̕K�{���ڂ̌���
	 */
	protected boolean validateAsRegisterPushed(
			JAddKikanInformationFrameData data) {
		boolean useORCA = false;
		if (jRadioButton_Yes.isSelected() == true) {
			useORCA = true;
		}

		if (data.getKikanNumber().isEmpty() || data.getKikanName().isEmpty()
				|| data.getZipcode().isEmpty() || data.getAddress().isEmpty()
				|| data.getTel().isEmpty() || data.getORCA().isEmpty()) {
			JErrorMessage.show(
					"M9603", this);
			return false;
		}

		// ORCA���g�p����Ƃ��̍��ڃ`�F�b�N
		if (useORCA == true) {
			if (data.getORCADatabase().isEmpty()
					|| data.getORCAEncode().isEmpty()
					|| data.getORCAIpAddress().isEmpty()
					||

					data.getORCAPort().isEmpty()
					|| data.getORCAProtocol().isEmpty()
					|| data.getORCAUser().isEmpty()

					|| ( data.isUseOrcaIdDigit() && (
							data.getOrcaIdDigit() == null || data.getOrcaIdDigit().isEmpty()) )

			) {
				JErrorMessage
						.show("M9604", this);
				return false;
			}
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		logger.info(jButton_End.getText());
		dispose();
	}

	private boolean checkDegit(String JyushinkenID) {

		int i1 = Integer.parseInt(JyushinkenID.substring(0, 1)) * 2;
		int i2 = Integer.parseInt(JyushinkenID.substring(1, 2)) * 1;
		int i3 = Integer.parseInt(JyushinkenID.substring(2, 3)) * 2;
		int i4 = Integer.parseInt(JyushinkenID.substring(3, 4)) * 1;
		int i5 = Integer.parseInt(JyushinkenID.substring(4, 5)) * 2;
		int i6 = Integer.parseInt(JyushinkenID.substring(5, 6)) * 1;
		int i7 = Integer.parseInt(JyushinkenID.substring(6, 7)) * 2;
		int i8 = Integer.parseInt(JyushinkenID.substring(7, 8)) * 1;
		int i9 = Integer.parseInt(JyushinkenID.substring(8, 9)) * 2;

		i1 = this.checkKeta(i1);
		i2 = this.checkKeta(i2);
		i3 = this.checkKeta(i3);
		i4 = this.checkKeta(i4);
		i5 = this.checkKeta(i5);
		i6 = this.checkKeta(i6);
		i7 = this.checkKeta(i7);
		i8 = this.checkKeta(i8);
		i9 = this.checkKeta(i9);

		int yy = i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;
		int zz = yy % 10;
		int xx = Integer.parseInt(JyushinkenID.substring(9, 10));
		zz = 10 - zz;

		if (zz == xx) {
			return true;
		}

		return false;
	}

	// 2���̏ꍇ1���ڂ�2���ڂ𑫂�
	private int checkKeta(int a) {

		String s = String.valueOf(a);
		if (s.length() > 1) {
			a = Integer.parseInt(s.substring(0, 1))
					+ Integer.parseInt(s.substring(1, 2));
		}
		return a;
	}

	/**
	 * �o�^�{�^��
	 */
	public void pushedRegisterButton(ActionEvent e) {
		boolean useORCA = false;
		if (jRadioButton_Yes.isSelected() == true) {
			useORCA = true;
		}

		// �e���͍��ڂ��������ǂ���
		if (
				this.validateInputKikanData(useORCA)
				) {

			// ORCA�A�g�̕��������ʂɕ]������
			if (useORCA) {
				if (! validateInputOrcaData()) {
					return;
				}
			}

			// �K�{���ڂ����݂��邩
			if (validateAsRegisterPushed(validatedData)) {
				if (validatedData.isValidateAsDataSet()) {
					String kikanNumber = validatedData.getKikanNumber();
					String kikanName = validatedData.getKikanName();

					// �@��DB�̏d���`�F�b�N
					File checkFile = new File(JPath
							.GetKikanDatabaseFilePath(kikanNumber));

					if (checkFile.exists()) {
						JErrorMessage.show("M9636", this);
						return;
					}

					// ���f�@�֖��̂̏d���`�F�b�N
					try
					{
						ArrayList kikanItem =  systemDatabase.sendExecuteQuery(
								"SELECT * FROM T_F_KIKAN WHERE KIKAN_NAME =" +
								JQueryConvert.queryConvert(kikanName));

						if (kikanItem.size() > 0){
							JErrorMessage.show("M9635", this);
							return;
						}

					}catch(Exception err){
						err.printStackTrace();
						JErrorMessage.show("M9605", this);
						return;
					}

					// �@��DB�̍쐬
					try {
						JFile.Copy(
								JPath.BaseKikanDatabaseFilePath, JPath
										.GetKikanDatabaseFilePath(kikanNumber));
					} catch (IOException e2) {
						e2.printStackTrace();
						JErrorMessage.show("M9600", this);
						return;
					}

					// �ڑ��J�n
					JConnection kikanDatabase = null;
					try {
						kikanDatabase = JConnection.ConnectKikanDatabase(kikanNumber);
					} catch (SQLException e1) {
						e1.printStackTrace();
						JErrorMessage.show("M9601", this);
						rollback();
						return;
					}

					try {
						// �V�X�e��DB�̕��̓g�����U�N�V������������K�v������
						systemDatabase.Transaction();

						systemDatabase.sendNoResultQuery(
								"INSERT INTO T_F_KIKAN (TKIKAN_NO, KIKAN_NAME, NITI_RESE)"
										+ "VALUES ("
										+ JQueryConvert
												.queryConvertAppendComma(kikanNumber)
										+ JQueryConvert
												.queryConvertAppendComma(kikanName)
										+ JQueryConvert
												.queryConvert(validatedData
														.getORCA()) + ")");

						// �@��DB�ɋ@�֏���o�^
						// �@��DB�̕��́A���s������DB���ƍ폜����邽�߁A�g�����U�N�V������������K�v�͂Ȃ�

						kikanDatabase
								.sendNoResultQuery("INSERT INTO T_KIKAN (TKIKAN_NO, SMOTO_KIKAN, SAKI_KIKAN, KIKAN_NAME, POST, ADR, TIBAN, TEL) "
										+ "VALUES ("
										+ JQueryConvert
												.queryConvertAppendComma(kikanNumber)
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getSendSourceKikan())
										+ JQueryConvert
												.queryConvertAppendComma("")
										+ JQueryConvert
												.queryConvertAppendComma(kikanName)
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getZipcode())
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getAddress())
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getChiban())
										+ JQueryConvert
												.queryConvert(validatedData
														.getTel()) + ")");

						systemDatabase.Commit();
					} catch (Exception e2) {
						e2.printStackTrace();
						JErrorMessage
								.show("M9602", this);

						try {
							systemDatabase.rollback();
							kikanDatabase.Shutdown();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JErrorMessage
									.show("M0000", this);
							System.exit(1);
						}

						rollback();
						return;
					}

					try {
						kikanDatabase.Shutdown();

					} catch (SQLException e1) {
						e1.printStackTrace();
						JErrorMessage.show("M0000", this);

						System.exit(1);
					}

					// ORCA�A�g�p��XML�t�@�C�����쐬
					JORCASetting orcaSettings = new JORCASetting(kikanNumber);
					orcaSettings.setOrcaDatabase(validatedData.getORCADatabase());
					orcaSettings.setOrcaEncode(validatedData.getORCAEncode());
					orcaSettings.setOrcaIpAddress(validatedData.getORCAIpAddress());
					orcaSettings.setOrcaPassword(validatedData.getORCAPassword());
					orcaSettings.setOrcaPort(validatedData.getORCAPort());
					orcaSettings.setOrcaProtocol(validatedData.getORCAProtocol());
					orcaSettings.setOrcaUser(validatedData.getORCAUser());

					orcaSettings.setNichireseUser(validatedData.getORCANichireseUser());
					orcaSettings.setNichiresePassword(validatedData.getORCANichiresePassword());
					orcaSettings.setUseOrcaIdDigit(validatedData.isUseOrcaIdDigit());
					orcaSettings.setOrcaIdDigit(validatedData.getOrcaIdDigit());

					orcaSettings.Save();

					JApplication.kikanNumber = kikanNumber;
					JApplication.loadORCAData();
					dispose();
				}
			}
		}
	}

	private void clearInputData() {
		for (Iterator<JTextField> iter = inputFields.iterator(); iter.hasNext();) {
			JTextField field = iter.next();
			field.setText("");
		}

		this.jRadioButton_No.setSelected(true);
	}

	/**
	 * ORCA�A�g���̓��͒l�����؂���B
	 */
	protected boolean validateInputOrcaData() {

		return
				validatedData.setORCAIpAddress(this.jTextField_ip.getText())
				&& validatedData.setORCAPort(this.jTextField_portNumber.getText())
				&& validatedData.setORCADatabase(this.jTextField_databaseName.getText())
				&& validatedData.setORCAProtocol(this.jTextField_protocol.getText())

				&& validatedData.setORCAUser(this.jTextField_dbUserId.getText())
				&& validatedData.setORCAPassword(this.jTextField_dbPassword.getText())

				&& validatedData.setORCANichireseUser(this.jTextField_nichireseUserId.getText())
				&& validatedData.setORCANichiresePassword(this.jTextField_nichiresePassword.getText())
				&& validatedData.setORCAEncode(this.jTextField_encoding.getText());
	}

	/**
	 * �@�֏��̓��͒l�����؂���B
	 */
	protected boolean validateInputKikanData(boolean useORCA) {

		boolean useOrcaIdDigiti = false;
		if (this.jRadioButton_Yes1.isSelected()) {
			useOrcaIdDigiti = true;
		}

		// edit s.inoue 2009/10/27
		String address = this.jTextField_address.getText();
		if (!address.equals(""))
			address = JValidate.encodeUNICODEtoConvert(address);

		String tiban = this.jTextField_address2.getText();
		if (!tiban.equals(""))
			tiban = JValidate.encodeUNICODEtoConvert(tiban);

		return validatedData.setKikanNumber(this.jTextField_kikanNumber.getText())
		&& validatedData.setSendSourceKikan(this.jTextField_souhumotoNumber.getText())
		&& validatedData.setKikanName(this.jTextField_kikanName.getText())
		&& validatedData.setZipcode(this.jTextField_zip.getText())
		// edit s.inoue 2009/10/27
		// && validatedData.setAddressAndChiban(this.jTextField_address.getText(), this.jTextField_address2.getText())
		&& validatedData.setAddressAndChiban(address,tiban)
		&& validatedData.setTel(this.jTextField_tel.getText())
		&& validatedData.setORCA(useORCA)
		&& validatedData.setUseOrcaIdDigit(useOrcaIdDigiti)
		&& validatedData.setOrcaIdDigit(this.jTextField_orcaIdDigit.getText());
	}

	/**
	 * �����S�̂̂̃��[���o�b�N�������s��
	 */
	private void rollback() {
		try {
			JFile.Delete(JPath.GetKikanDatabaseFilePath(validatedData.getKikanNumber()));
		} catch (Exception e) {
			e.printStackTrace();
			JErrorMessage.show(
					"M0000", this);
			System.exit(1);
		}
	}

	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == jButton_End) {
			pushedEndButton(e);

		} else if (source == jButton_Register) {
			pushedRegisterButton(e);

		/* �����Z�A�g�u�͂��v */
		} else if (source == jRadioButton_Yes) {

			this.changeComponentEnableOnUsingOrca(true);

		/* �����Z�A�g�u�������v */
		} else if (source == jRadioButton_No) {

			this.changeComponentEnableOnUsingOrca(false);
		}
		/* �ڑ��e�X�g�{�^�� */
		else if(source == jButton_ConnectionTest){
			pushedConnectionTestButton();
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		Object source = event.getSource();

		// add s.inoue 2010/03/29
		if(source.equals(this.jTextField_zip)){
			if (jTextField_zip.getText().equals(""))
				return;

			String zipText = setZipCodeAddress(this.jTextField_zip.getText());
			if (zipText.equals(""))
				return;

			jTextField_address.setText(zipText);
		}
	}

	// add s.inoue 2010/04/02
	private String setZipCodeAddress(String zipCode){

		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT SUB_POST.POSTCD,ADDRESS FROM T_POST,");
		sb.append(" (SELECT SUBSTRING(POST_CD FROM 1 FOR 3) || SUBSTRING(POST_CD FROM 5 FOR 4) POSTCD,");
		sb.append(" POST_CD SP_POSTCD FROM T_POST) SUB_POST");
		sb.append(" where SUB_POST.POSTCD = ");
		sb.append(JQueryConvert.queryConvert(zipCode));
		sb.append(" and SUB_POST.SP_POSTCD = T_POST.POST_CD ");

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
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Register.getText());
			pushedRegisterButton(null);break;
		}
	}

	private void pushedConnectionTestButton() {

		validatedData.initialize();
		if (! validateInputOrcaData()) {
			return;
		}

		/* ORCA DB�p�R�l�N�V�������쐬����B */
		OrcaConnection orcaCon = new OrcaConnection();

		JConnection con = orcaCon.getOrcaDbTestConnection(
				validatedData.getORCAIpAddress(),
				validatedData.getORCAPort(),
				validatedData.getORCADatabase(),
				validatedData.getORCAUser(),
				validatedData.getORCAPassword()
				);

		if (con == null) {
			JErrorMessage.show("M4394", this);
		} else {
			JErrorMessage.show("M9631", this);

			try {
				/* �o�[�W���������؂���B */
				String version = orcaCon.getOrcaVersion(con);
				boolean validVersion = orcaCon.validateOrcaVersion(version);
				if (! validVersion) {
					JErrorMessage.show("M9632", this);
					return;
				}

				String userId = validatedData.getORCANichireseUser();
				boolean existsUserId = orcaCon.existsUserId(con, userId);
				if (! existsUserId) {
					JErrorMessage.show("M9633", this);
					return;
				}

			} finally {
				try {
					con.Shutdown();

				} catch (SQLException ex) {
					ex.printStackTrace();
//					JErrorMessage.show("M4392", this);
				}
			}
		}
	}

	/**
	 * ORCA�A�g�̗L�����w�肵�āA�R���|�[�l���g�̗L���A������؂�ւ���B
	 */
	private void changeComponentEnableOnUsingOrca(boolean useOrca) {

		for (int i = 0; i < nichireseComponents.length; i++) {
			nichireseComponents[i].setEnabled(useOrca);
//			((ExtendedTextField)nichireseComponents[i]).setEditable(useOrca);
		}

		for (int i = 0; i < nichireseComponents.length; i++) {
			nichireseComponents[i].setBackground(Color.WHITE);
		}

		if (useOrca) {
			for (int i = 0; i < nichireseRequiredComponents.length; i++) {
				nichireseRequiredComponents[i].setBackground(
						ViewSettings.getRequiedItemBgColor());
			}

			/*
			 * ���� ID �� 0 ���ߌ����ɂ��ẮA0 ���߂��s�Ȃ����Ƃ�
			 * �I������Ă���ꍇ�̂݁A�L�������A�w�i�F��ύX����B
			 */
			if (this.jRadioButton_Yes1.isSelected()) {
				this.jTextField_orcaIdDigit.setEnabled(true);
				this.jTextField_orcaIdDigit.setBackground(
						ViewSettings.getRequiedItemBgColor());
			}
			else {
				this.jTextField_orcaIdDigit.setEnabled(false);
				this.jTextField_orcaIdDigit.setBackground(Color.WHITE);
			}
		}

		this.jButton_ConnectionTest.setEnabled(useOrca);
	}

	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();

		if (source == jRadioButton_Yes) {
			this.changeComponentEnableOnUsingOrca(true);
		}
		else if(source == jRadioButton_No) {
			this.changeComponentEnableOnUsingOrca(false);
		}
		else if(source == jRadioButton_Yes1) {
			if (this.jRadioButton_Yes.isSelected()) {
				this.jTextField_orcaIdDigit.setEnabled(true);
				this.jTextField_orcaIdDigit.setBackground(ViewSettings.getRequiedItemBgColor());
			}
		}
		else if(source == jRadioButton_No1) {
			this.jTextField_orcaIdDigit.setEnabled(false);
			this.jTextField_orcaIdDigit.setBackground(Color.WHITE);
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

