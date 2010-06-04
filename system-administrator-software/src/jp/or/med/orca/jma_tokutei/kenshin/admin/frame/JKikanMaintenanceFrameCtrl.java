package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

//import java.awt.event.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.JAddKikanInformationFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.frame.JChangeKikanInformationFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;

/**
 * �@�֏�񃁃��e�i���X
 *
 * �@�ϐ�����Java�R�[�f�B���O�K��ɑ��������̂ɕύX�B
 */
public class JKikanMaintenanceFrameCtrl extends JKikanMaintenanceFrame {

	private JSimpleTable table = null;
	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JKikanMaintenanceFrameCtrl.class);

	public JKikanMaintenanceFrameCtrl() {
		this.table = new JSimpleTable();

		// �e�[�u���̍쐬
		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(this.table);
		jPanel_Table.add(scroll);

		this.table.addHeader("���f�@�֔ԍ�");
		this.table.addHeader("���f�@�֖���");
		this.table.addHeader("����W�����Z�v�g�\�t�g�A�g");

		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {
				new JSimpleTableCellPosition(-1, 0),
				new JSimpleTableCellPosition(-1, 1),
				new JSimpleTableCellPosition(-1, 2) };

		this.initializeColumnWidth();
		this.table.setCellEditForbid(iSetColumnList);

		//�\�����ڂ̏��������s�Ȃ��B
		this.initialize();

		// �G���^�[�L�[�̏���
		this.table
				.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(
						this, jButton_Change));

		// �_�u���N���b�N�̏���
		this.table
				.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent(
						this, jButton_Change));

		// focus����
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.table);
		this.focusTraversalPolicy.addComponent(this.table);
		this.focusTraversalPolicy.addComponent(this.jButton_Add);
		this.focusTraversalPolicy.addComponent(this.jButton_Change);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		// �����I��
		if (this.table.getRowCount() > 0) {
			this.table.setRowSelectionInterval(0, 0);
		} else {
			jButton_Add.requestFocus();
		}

		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());

		// add ver2 s.inoue 2009/05/08
		TableColumnModel columns = table.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
        }

	}

	/**
	 * ��T�C�Y������������B
	 */
	private void initializeColumnWidth() {
		table.changeSelection(0, 1, false, false);
		table.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * �폜�{�^��
	 */
	public void pushedDeleteButton(ActionEvent e) {
		int[] Row = this.table.getSelectedRows();

		if (Row.length <= 0) {
			return;
		}

		RETURN_VALUE Ret = JErrorMessage.show("M7202", this);

		if (Ret == RETURN_VALUE.YES) {
			for (int i = 0; i < Row.length; i++) {
				String KikanNumber = (String) this.table.getData(Row[i], 0);
				DeleteDatabase(KikanNumber);
			}

			initialize();
		}
	}

	/**
	 * �ύX�{�^��
	 */
	public void pushedChangeButton(ActionEvent e) {

// edit s.inoue 2010/05/26
//		int Row = this.table.getSelectedRow();
//		if (Row <= 0) {
//			return;
//		}
		int[] Row = this.table.getSelectedRows();

		if (Row.length <= 0) {
			return;
		}

		String KikanNumber = (String) this.table.getData(Row[0], 0);
		JScene.CreateDialog(this, new JChangeKikanInformationFrameCtrl(
				JApplication.systemDatabase, KikanNumber), new RefreshEvent());
	}

	/**
	 * �ǉ��{�^��
	 */
	public void pushedAddButton(ActionEvent e) {
		// add s.inoue 2009/10/15
		// flg�ǉ�
		JScene.CreateDialog(this, new JAddKikanInformationFrameCtrl(
				JApplication.systemDatabase,true), new RefreshEvent());
		this.initialize();
	}

	/**
	 * �\�����ڂ̏��������s��
	 */
	public void initialize() {
		this.table.clearAllRow();

		ArrayList<Hashtable<String, String>> Items = null;

		try {
			Items = JApplication.systemDatabase
					.sendExecuteQuery("SELECT * FROM T_F_KIKAN");
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M7200", this);
			return;
		}

		// �e�[�u���Ƀf�[�^��}��
		for (int i = 0; i < Items.size(); i++) {
			Hashtable<String, String> Item = Items.get(i);

			// �����Z�A�g�̕\�����u�͂��v�A�u�������v�ɕύX����
			String NitiRese = "";

			if (Item.get("NITI_RESE").trim().equals("1")) {
				NitiRese = "�͂�";
			} else {
				NitiRese = "������";
			}

			String ItemArray[] = { Item.get("TKIKAN_NO"),
					Item.get("KIKAN_NAME"), NitiRese };
			this.table.addData(ItemArray);
		}
	      // �����I��
		if (table.getRowCount() > 0) {
			table.setRowSelectionInterval(0, 0);
		} else {
			jButton_Add.requestFocus();
		}

	}

	/**
	 * �f�[�^�x�[�X�t�@�C���̍폜���s��
	 * @param KikanNumber �@�֔ԍ�
	 */
	public void DeleteDatabase(String KikanNumber) {
		try {
			JApplication.systemDatabase
					.sendNoResultQuery("DELETE FROM T_F_KIKAN WHERE TKIKAN_NO = "
							+ JQueryConvert.queryConvert(KikanNumber));
		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M7201", this);
			return;
		}
		if (jp.or.med.orca.jma_tokutei.common.filectrl.JFile.Delete(JPath
				.GetKikanDatabaseFilePath(KikanNumber)) == false) {
			JErrorMessage.show("M7201", this);
			return;
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		else if (source == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
		else if (source == jButton_Change) {
			logger.info(jButton_Change.getText());
			pushedChangeButton(e);
		}
		else if (source == jButton_Add) {
			logger.info(jButton_Add.getText());
			pushedAddButton(e);
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
		case KeyEvent.VK_F11:
			logger.info(jButton_Change.getText());
			pushedChangeButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(null);break;
		}
	}
	/**
	 * �@�֏��ǉ����ȂǂɁA�ǉ���ʂ������Ƃ��ɌĂяo�����C�x���g
	 */
	public class RefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			initialize();
		}
	}
}

//Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

