package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * ���f�@�փR�[�h�}�X�^�����e�i���X
 */
public class JKensaKikanCodeMasterMaintenanceListFrameCtrl extends
		JKensaKikanCodeMasterMaintenanceListFrame {
	private JSimpleTable model = new JSimpleTable();

	public JKensaKikanCodeMasterMaintenanceListFrameCtrl() {
		/* Added 2008/03/10 �ጎ */
		/* --------------------------------------------------- */
		model.setPreferedColumnWidths(new int[] { 200, 200 });
		/* --------------------------------------------------- */

		model.addHeader("�����Z���^�[�R�[�h");
		model.addHeader("�����Z���^�[����");

		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_MainArea.add(scroll);

		refreshTable();

		// �G���^�[�L�[�̐ݒ�
		// jButton_End.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_Add.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_Edit.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_Delete.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		model
				.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(
						this, jButton_Edit));

		// �_�u���N���b�N�̏���
		model
				.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent(
						this, jButton_Edit));

		// �����I��
		if (model.getRowCount() > 0) {
			model.setRowSelectionInterval(0, 0);
		} else {
			jButton_Add.requestFocus();
		}
	}

	/**
	 * �e�[�u���̃��t���b�V�����s��
	 */
	public void refreshTable() {
		ArrayList<Hashtable<String, String>> Result = null;
		Hashtable<String, String> ResultItem = null;
		String Query = new String(
				"SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME FROM T_KENSACENTER_MASTER");

		// �e�[�u���̍폜
		model.clearAllRow();

		try {
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] row = new String[2];
		for (int i = 0; i < Result.size(); i++) {
			ResultItem = Result.get(i);
			row[0] = ResultItem.get("KENSA_CENTER_CD");
			row[1] = ResultItem.get("CENTER_NAME");
			model.addData(row);
		}
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * �����Z���^�[�̍폜
	 */
	public void pushedDeleteButton(ActionEvent e) {
		String insertQuery = null;
		int selectedRowCount = model.getSelectedRowCount();

		if (selectedRowCount > 0) {
			RETURN_VALUE Ret = jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
					.show("M5601", this);

			if (Ret == RETURN_VALUE.YES) {
				for (int i = 0; i < selectedRowCount; i++) {
					String DeleteItem = (String) model.getData(model
							.getSelectedRows()[i], 0);
					insertQuery = new String(
							"DELETE FROM T_KENSACENTER_MASTER WHERE KENSA_CENTER_CD = "
									+ JQueryConvert.queryConvert(DeleteItem));

					try {
						String confirmQuery = new String(
								"SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE KENSA_CENTER_CD = "
										+ JQueryConvert
												.queryConvert(DeleteItem));

						// ����e�[�u���ɂ����ĊY���̌����Z���^�[�R�[�h���g�p����Ă��Ȃ�������폜���s��
						if (!JConnection.IsExistDatabaseItem(
								JApplication.kikanDatabase, confirmQuery)) {
							JApplication.kikanDatabase
									.sendNoResultQuery(insertQuery);
						} else {
							jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
									.show("M5603", this);
						}
					} catch (SQLException f) {
						f.printStackTrace();

						jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
								.show("M5602", this);

						return;
					}
				}

				refreshTable();
			}
		}
	}

	/**
	 * �ǉ��{�^��
	 */
	public void pushedAddButton(ActionEvent e) {
		int maxCodeNumber = GetKensaCenterNumber();

		String centerCode = String.valueOf(maxCodeNumber);
		String centerName = "�`�����ɐV�K�o�^���錟���Z���^�[�̖��̂���͂��Ă��������`";

		jp.or.med.orca.jma_tokutei.common.scene.JScene
				.CreateDialog(
						this,
						new JKensaKikanCodeMasterMaintenanceEditFrameCtrl(
								centerCode,
								centerName,
								JKensaKikanCodeMasterMaintenanceEditFrameCtrl.ADD_MODE),
						new WindowRefreshEvent());
	}

	/**
	 * �ύX�{�^��
	 */
	public void pushedEditButton(ActionEvent e) {
		if (model.getSelectedRowCount() > 0) {
			String centerCode = (String) model.getData(model.getSelectedRow(),
					0);
			String centerName = (String) model.getData(model.getSelectedRow(),
					1);

			jp.or.med.orca.jma_tokutei.common.scene.JScene
					.CreateDialog(
							this,
							new JKensaKikanCodeMasterMaintenanceEditFrameCtrl(
									centerCode,
									centerName,
									JKensaKikanCodeMasterMaintenanceEditFrameCtrl.CHANGE_MODE),
							new WindowRefreshEvent());
		}
	}

	/**
	 * �����Z���^�[�R�[�h�̋󂫔ԍ��̎擾
	 */
	private int GetKensaCenterNumber() {
		ArrayList<Hashtable<String, String>> Items;
		try {
			Items = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT DISTINCT KENSA_CENTER_CD FROM T_KENSACENTER_MASTER");
		} catch (SQLException e) {
			e.printStackTrace();
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show(
					"M5604", this);
			return -1;
		}

		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			boolean FindFlag = false;

			for (int j = 0; j < Items.size(); j++) {
				if (Items.get(j).get("KENSA_CENTER_CD").equals(
						String.valueOf(i))) {
					FindFlag = true;
				}
			}

			// true�łȂ���΋󂫂�����Ƃ���B
			if (FindFlag == false) {
				return i;
			}
		}

		return -1;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			pushedEndButton(e);
		}

		else if (source == jButton_Delete) {
			pushedDeleteButton(e);
		}

		else if (source == jButton_Add) {
			pushedAddButton(e);
		}

		else if (source == jButton_Edit) {
			pushedEditButton(e);
		}
	}

	/**
	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			// �e�[�u���̍ēǂݍ��݂��s��
			refreshTable();
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

