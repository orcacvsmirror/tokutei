package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * ���f�p�^�[�������e�i���X�i�����j
 */
public class JKenshinPatternMaintenanceCopyFrameCtrl extends
		JKenshinPatternMaintenanceCopyFrame {
	private int patternNumber;

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private JKenshinPatternMaintenanceCopyFrameData validatedData =
		new JKenshinPatternMaintenanceCopyFrameData();

	private static Logger logger = Logger.getLogger(JKenshinPatternMaintenanceCopyFrameCtrl.class);

	// OK�{�^�����������ۂ̕K�{���ڂ����؂���
	protected boolean validateAsOKPushed(
			JKenshinPatternMaintenanceCopyFrameData data) {
		if (validatedData.getPatternNumber().equals("")) {
			JErrorMessage.show("M3901", this);
			return false;
		}

		if (validatedData.getNewPatternName().equals("")) {
			JErrorMessage.show("M3902", this);
			return false;
		}

		if (validatedData.getSourcePatternNumber().equals("")) {
			JErrorMessage.show("M3903", this);
			return false;
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * @param nextNumber
	 *            �R�s�[���̃p�^�[���ԍ�
	 */
	public JKenshinPatternMaintenanceCopyFrameCtrl(
			int nextNumber,
			String currentNo
		) {

		// focus����
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jTextField_Name);
		this.focusTraversalPolicy.addComponent(this.jTextField_Name);
		this.focusTraversalPolicy.addComponent(this.jTextField_Note);
		this.focusTraversalPolicy.addComponent(this.jButton_OK);
		this.focusTraversalPolicy.addComponent(jButton_End);
		this.focusTraversalPolicy.addComponent(this.jComboBox_Target);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		/* �p�^�[���I�𗓂�����������B */
		this.initializePatternComboBox(nextNumber, currentNo);

		this.patternNumber = nextNumber;

		}

	/**
	 * �p�^�[���I�𗓂�����������B
	 */
	private boolean initializePatternComboBox(
			int patternNumber,
			String currentNo) {

		boolean success = false;

		ArrayList<Hashtable<String, String>> result = null;

		try {
			/* Modified 2008/07/11 �ጎ  */
			/* --------------------------------------------------- */
//			String query = new String("SELECT * FROM T_KENSHIN_P_M");
			/* --------------------------------------------------- */
			String query = "SELECT * FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ";
			/* --------------------------------------------------- */

			result = JApplication.kikanDatabase.sendExecuteQuery(query);
			success = true;

		} catch (SQLException e) {
			e.printStackTrace();

			JErrorMessage.show("M3920", this);
			dispose();
		}

		if (result != null) {
			int selectedIndex = 0;

			for (int i = 0; i < result.size(); i++) {
				Hashtable<String, String> resultItem = result.get(i);

//				String no = String.valueOf(resultItem.get("K_P_NO"));
				String no = resultItem.get("K_P_NO");

				if (no.equals(String.valueOf(currentNo))) {
					selectedIndex = i;
				}

				String item = JValidate.fillZero(no, 3)
						+ ":" + String.valueOf(resultItem.get("K_P_NAME"));

				this.jComboBox_Target.addItem(item);
			}

			this.jComboBox_Target.setSelectedIndex(selectedIndex);
		}

		// del s.inoue 2009/12/03
		// this.jComboBox_Target.transferFocus();

		return success;
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * OK�{�^��
	 */
	public void pushedOKButton(ActionEvent e) {
		if (validatedData.setSourcePatternNumber(((String) jComboBox_Target
				.getSelectedItem()).substring(0, 3))
				&& validatedData.setNewPatternName(jTextField_Name.getText())
				&& validatedData
						.setPatternNumber(String.valueOf(patternNumber))
				&& validatedData.setNote(jTextField_Note.getText())) {
			if (validateAsOKPushed(validatedData)) {
				if (validatedData.isValidateAsDataSet()) {
					try {

						// add s.inoue 20081217
						// ���f�p�^�[�����̂̏d���`�F�b�N
						try
						{
							ArrayList kikanItem =  JApplication.kikanDatabase.sendExecuteQuery(
									"SELECT * FROM T_KENSHIN_P_M WHERE K_P_NAME =" +
									JQueryConvert.queryConvert(validatedData.getNewPatternName()));

							if (kikanItem.size() > 0){
								JErrorMessage.show("M9637", this);
								return;
							}

						}catch(Exception err){
							err.printStackTrace();
							JErrorMessage.show("M9601", this);
							return;
						}

						JApplication.kikanDatabase.Transaction();

						// ���f�p�^�[���}�X�^�ɐV�K�p�^�[����o�^����
						String Query = new String(
								"INSERT INTO T_KENSHIN_P_M (K_P_NO,K_P_NAME,BIKOU) VALUES ("
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getPatternNumber())
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getNewPatternName())
										+ JQueryConvert
												.queryConvert(validatedData
														.getNote()) + ")");

						JApplication.kikanDatabase.sendNoResultQuery(Query);

						ArrayList<Hashtable<String, String>> Result = new ArrayList<Hashtable<String, String>>();
						Hashtable<String, String> ResultItem = new Hashtable<String, String>();

						// ���f�p�^�[���ڍׂ��畡�����̃p�^�[��No���t���Ă�����̂����𒊏o����
						Query = new String(
								"SELECT * FROM T_KENSHIN_P_S WHERE K_P_NO = "
										+ JQueryConvert
												.queryConvert(validatedData
														.getSourcePatternNumber()));

						Result = JApplication.kikanDatabase
								.sendExecuteQuery(Query);

						// 2008/03/06 �M �C��
						// ���f�p�^�[��No�̍ő�l���擾���C���N�������g��V�K�p�^�[���̓o�^�Ɏg��
						ArrayList<Hashtable<String, String>> maxNo = new ArrayList<Hashtable<String, String>>();
						Hashtable<String, String> maxNoItem = new Hashtable<String, String>();
						Query = new String(
								"SELECT MAX(K_P_NO) FROM T_KENSHIN_P_S");

						maxNo = JApplication.kikanDatabase
								.sendExecuteQuery(Query);
						maxNoItem = maxNo.get(0);

						// edit s.inoue 20090122
//						int max = Integer.valueOf(maxNoItem.get("MAX"))
//								.intValue();
//						max++;
						int max = this.patternNumber;

						// 2008/03/06 �M �C��

						// �������̌��f�p�^�[���ڍׂ̍s�̌��f�p�^�[��No��V�K�p�^�[��No�ɏ��������}�����s��
						for (int i = 0; i < Result.size(); i++) {
							ResultItem = Result.get(i);
							Query = new String(
									"INSERT INTO T_KENSHIN_P_S (K_P_NO,KOUMOKU_CD,LOW_ID) VALUES ("
											+
											// 2008/03/06 �M �C��
											// JQueryConvert.queryConvertAppendComma(validatedData.getPatternNumber())
											// +
											Integer.toString(max)
											+ ","
											+
											// 2008/03/06 �M �C��
											JQueryConvert
													.queryConvertAppendComma(ResultItem
															.get("KOUMOKU_CD"))
											+ JQueryConvert
													.queryConvert(ResultItem
															.get("LOW_ID"))
											+ ")");
							JApplication.kikanDatabase.sendNoResultQuery(Query);
						}

						JApplication.kikanDatabase.Commit();
						dispose();

					} catch (SQLException f) {
						f.printStackTrace();
						JErrorMessage.show("M3921", this);
						try {
							JApplication.kikanDatabase.rollback();
						} catch (SQLException g) {
							g.printStackTrace();

							JErrorMessage.show("M0000", this);

							System.exit(1);
						}

						return;
					}

				}
			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		} else if (e.getSource() == jButton_OK) {
			logger.info(jButton_OK.getText());
			pushedOKButton(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_OK.getText());
			pushedOKButton(null);break;
		}
	}

}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

