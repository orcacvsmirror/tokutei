package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JTable;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.table.*;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;

/**
 * ���[�U�����e�i���X�p�t���[���̃R���g���[��
 */
public class JUserMaintenanceFrameCtrl extends JUserMaintenanceFrame {
	public JSimpleTable model = new JSimpleTable();

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private static Logger logger = Logger.getLogger(JUserMaintenanceFrameCtrl.class);

	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public JUserMaintenanceFrameCtrl() {
		jPanel_MainArea.add(new JSimpleTableScrollPanel(model));

		init();

		model.addKeyListener(new JEnterEvent(this, jButton_Change));

		// �_�u���N���b�N�̏���
		model.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Change));

		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.model);
		this.focusTraversalPolicy.addComponent(this.model);
		this.focusTraversalPolicy.addComponent(this.jButton_Add);
		this.focusTraversalPolicy.addComponent(this.jButton_Change);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);
		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		model.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	}

	/**
	 * �e�[�u���̏��������s��
	 */
	public void init() {
		model.clearAllColumn();
		model.clearAllRow();

		// �w�b�_�̐ݒ�
		model.addHeader("���[�U��");
		model.addHeader("����");

		JSimpleTableCellPosition[] forbitCells = { new JSimpleTableCellPosition(
				-1, -1) };
		model.setCellEditForbid(forbitCells);
		// edit s.inoue 2009/10/31
		model.setAutoCreateRowSorter(true);
		model.refreshTable();

		// add ver2 s.inoue 2009/05/08
		TableColumnModel columns = model.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
        }

		ArrayList<Hashtable<String, String>> Result;
		try {
			Result = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT USER_NAME, PASS, KENGEN FROM T_USER ORDER BY USER_NAME");

			for (int i = 0; i < Result.size(); i++) {
				Hashtable<String, String> item = Result.get(i);

				Vector<String> row = new Vector<String>();
				row.add(item.get("USER_NAME"));
				String dbKengen = item.get("KENGEN");

				String kengen = null;
				if (dbKengen.equals("1")) {
					kengen = JValidate.USER_KENGEN_KANRISYA;
				}
				else {
					kengen = JValidate.USER_KENGEN_IPANUSER;
				}

				row.add(kengen);

				model.addData(row);
			}
			// �����I��
			if (model.getRowCount() > 0) {
				model.setRowSelectionInterval(0, 0);
			} else {
				jButton_Add.requestFocus();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show(
					"M5401", this);
			dispose();
			return;
		}
	}

	/**
	 * �I���{�^������
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * �ǉ��{�^������
	 */
	public void pushedAddButton(ActionEvent e) {
		JScene.CreateDialog(
				this,
				new JRegisterUserFrameCtrl(),
				new WindowRefreshEvent()
			);
	}

	/**
	 * �ύX�{�^������
	 */
	public void pushedChangeButton(ActionEvent e) {
		if (model.getSelectedRow() == -1) {
			return;
		}

		String userName = (String) model.getData(model.getSelectedRow(), 0);

		JScene.CreateDialog(
				this,
				new JRegisterUserFrameCtrl(userName),
				new WindowRefreshEvent()
			);
	}

	/**
	 * �폜�{�^������
	 */
	public void pushedDeleteButton(ActionEvent e) {
		if (model.getSelectedRow() == -1) {
			return;
		}

		if (model.getRowCount() == model.getSelectedRowCount()) {
			JErrorMessage.show(
					"M5402", this);
			return;
		}

		RETURN_VALUE Ret = JErrorMessage.show("M5403", this);

		if (Ret == RETURN_VALUE.YES) {
			int[] rows = model.getSelectedRows();

			for (int i = 0; i < model.getSelectedRowCount(); i++) {
				String Query = new String(
						"DELETE FROM T_USER WHERE USER_NAME ="
								+ JQueryConvert.queryConvert((String) model
										.getData(rows[i], 0)));

				try {
					JApplication.kikanDatabase.sendNoResultQuery(Query);
				} catch (SQLException f) {
					f.printStackTrace();
					JErrorMessage.show("M5404", this);
				}
			}

			init();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object souce = e.getSource();
		/* �I���{�^�� */
		if (souce == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		/* �ǉ��{�^�� */
		else if (souce == jButton_Add) {
			logger.info(jButton_Add.getText());
			pushedAddButton(e);
		}
		/* �ύX�{�^�� */
		else if (souce == jButton_Change) {
			logger.info(jButton_Change.getText());
			pushedChangeButton(e);
		}
		/* �폜�{�^�� */
		else if (souce == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
	}
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Add.getText());
			pushedAddButton(null);break;
		case KeyEvent.VK_F11:
			logger.info(jButton_Change.getText());
			pushedChangeButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(null);break;
		}
	}
	/**
	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			//�e�[�u���̍ēǂݍ��݂��s��
			init();
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

