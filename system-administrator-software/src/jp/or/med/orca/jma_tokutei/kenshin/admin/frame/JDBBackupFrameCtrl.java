package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.dbfix.ShcDBAdjust;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;

/**
 * �f�[�^�x�[�X�o�b�N�A�b�v
 */
public class JDBBackupFrameCtrl extends JDBBackupFrame {
	private JSimpleTable m_model = new JSimpleTable();

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	// edit h.yoshitama 2009/11/13
	// private Vector<File> m_dirList = new Vector<File>();
	private SortedMap<String,File> m_fileList = null;

	// s.inoue 20080807
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JDBBackupFrameCtrl.class);

	/**
	 *  �R���X�g���N�^
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JDBBackupFrameCtrl() {
		m_model.addHeader("�o�b�N�A�b�v���t");
		m_model.addHeader("�o�b�N�A�b�v�T�C�Y");

		// edit h.yoshitama 2009/11/13
		m_fileList = new TreeMap<String,File>();

		// �e�[�u���p�l������
		JSimpleTableScrollPanel tablePanel = new JSimpleTableScrollPanel(m_model);

		// �e�[�u���p�l���ݒ�
		JSimpleTableCellPosition[] forbidList = { new JSimpleTableCellPosition(-1, -1) };

		m_model.setCellEditForbid(forbidList);
		m_model.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jPanel_Table.add(tablePanel, BorderLayout.CENTER);

		reloadDBFileList();

		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());

		// focus����
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.m_model);
		this.focusTraversalPolicy.addComponent(this.m_model);
		this.focusTraversalPolicy.addComponent(this.jButton_Reload);
		this.focusTraversalPolicy.addComponent(this.jButton_Backup);
		this.focusTraversalPolicy.addComponent(this.jButton_Restore);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		// add ver2 s.inoue 2009/05/08
		TableColumnModel columns = m_model.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)m_model.getDefaultRenderer(m_model.getColumnClass(i))));
        }
	}

	/**
	 *  �f�[�^�x�[�X�ڑ�
	 *     @param  none
	 *     @return none
	 */
	private void connectDataBase() throws SQLException {
		try {
			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();
		} catch (SQLException err) {
			err.printStackTrace();

			JErrorMessage.show(
					"M7501", this);

			throw new SQLException();
		}
	}

	/**
	 *  �f�[�^�x�[�X�ؒf
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	private void shutdownDataBase() throws SQLException {
		try {
			JApplication.systemDatabase.Shutdown();
		} catch (SQLException err) {
			err.printStackTrace();

			JErrorMessage.show(
					"M7500", this);

			throw new SQLException();
		}
	}

	/**
	 *  �f�B���N�g���T�C�Y�v�Z
	 *
	 *     @param  �Ώۃf�B���N�g��
	 *
	 *     @return long
	 */
	private long calcDirectorySize(File dir) {
		long lTotalSize = 0;

		File[] fileList = dir.listFiles();

		if (fileList != null) {
			for (int i = 0; i < fileList.length; ++i) {
				lTotalSize += fileList[i].length();
			}
		}

		return lTotalSize;
	}

	/**
	 *  �o�b�N�A�b�v���X�g�Ǎ�
	 *
	 *     @param  none
	 *
	 *     @return none
	 */
	private void reloadDBFileList() {
		m_model.clearAllRow();
		m_fileList.clear();

		File dirRoot = new File(JPath.BackupSystemDatabaseFolder);

		// �f�B���N�g���`�F�b�N
		if (!dirRoot.exists()) {
			return;
		}

		// �f�B���N�g���ꗗ�쐬
		File[] dirList = dirRoot.listFiles();

		// edit s.inoue 2009/10/31
		for( int i = dirList.length - 1; i >= 0; --i )
		{
			if (dirList[i].isDirectory()) {
				// edit h.yoshitama 2009/11/13
				// m_fileList.add(dirList[i]);
				m_fileList.put(String.valueOf(dirList.length-i-1),dirList[i]);
			}
		}

		// �f�B���N�g���ꗗ����
		// del h.yoshitama 2009/11/13
//		for (int i = 0; i < m_fileList.size(); ++i) {
//			for (int j = i + 1; j < m_fileList.size(); ++j) {
//				if (m_fileList.get(i).lastModified() > m_fileList.get(j)
//						.lastModified()) {
//					File fileTmp1 = m_fileList.get(i);
//					File fileTmp2 = m_fileList.get(j);
//
//					m_fileList.set(i, fileTmp2);
//					m_fileList.set(j, fileTmp1);
//				}
//			}
//		}

		// �t�@�C����
		// for( int i = fileList.length - 1; i >= 0; --i ){
		for (int i = 0; i < m_fileList.size(); ++i) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			long lSize = calcDirectorySize(m_fileList.get(String.valueOf(i))) / 1000;

			String[] aryData = {
					dateFormat
							.format(new Date(m_fileList.get(String.valueOf(i)).lastModified())), // �t�@�C�����t
					Long.toString(lSize) + " KB" // �t�@�C���e��
			};

			m_model.addData(aryData);
		}

		// add s.inoue 2009/11/16
		m_model.setAutoCreateRowSorter(false);
		m_model.refreshTable();

		// �ŏ��ɑI�����鍀�ڂ̐ݒ�
		if (m_model.getRowCount() > 0) {
			m_model.setRowSelectionInterval(0, 0);
		} else {
			jButton_Reload.requestFocus();
		}
	}

	/**
	 *  �o�b�N�A�b�v����
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void beginBackup() throws Exception {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

			String path = JPath.BackupSystemDatabaseFolder
								+ JApplication.FILE_SEPARATOR + JApplication.versionNumber + "_"
								+ dateFormat.format(Calendar.getInstance().getTime());

			File directory = new File(path);

			if (!directory.mkdirs()) {
				throw new Exception();
			}

			if (JFileCopy.xcopyFile(JPath.DatabaseFolder, directory
					.getAbsolutePath()) > 0) {
				throw new Exception();
			}
		} catch (Exception err) {
			err.printStackTrace();
			logger.error("�o�b�N�A�b�v�����y�у��[���o�b�N�����̃t�@�C���R�s�[���A�G���[�ƂȂ�܂����B");
			throw new Exception();
		}
	}

	/**
	 *  �o�b�N�A�b�v�I��
	 *
	 *    @param  �C�x���g
	 *
	 *    @return none
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 *  �o�b�N�A�b�v�\��
	 *
	 *    @param  �C�x���g
	 *
	 *    @return none
	 */
	public void pushedReloadButton(ActionEvent e) {
		reloadDBFileList();
	}

	/**
	 *  �o�b�N�A�b�v����
	 *
	 *    @param  �C�x���g
	 *
	 *    @return none
	 */
	public void pushedBackupButton(ActionEvent e) {
		try {
			// �f�[�^�x�[�X�ؒf
			shutdownDataBase();

			// �o�b�N�A�b�v�m�F
			RETURN_VALUE iResult = JErrorMessage
					.show("M7502", this);

			if (iResult == RETURN_VALUE.YES) {
				beginBackup();

				reloadDBFileList();

				JErrorMessage
						.show("M7504", this);
			}
		} catch (Exception err) {
			err.printStackTrace();

			JErrorMessage.show(
					"M7503", this);
		} finally {
			try {
				// �f�[�^�x�[�X�Đڑ�
				connectDataBase();
			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage
						.show("M0000", this);

				System.exit(1);
			}
		}
	}

	/**
	 *  �o�b�N�A�b�v����
	 *
	 *    @param  �C�x���g
	 *
	 *    @return none
	 */
	public void pushedRestoreButton(ActionEvent e) {
		int iSelect = m_model.getSelectedRow();

		try {
			// �f�[�^�x�[�X�ؒf
			shutdownDataBase();

			if (iSelect != -1) {
				// �o�b�N�A�b�v�m�F
				RETURN_VALUE iResult = JErrorMessage
						.show("M7505", this);

				if (iResult == RETURN_VALUE.YES) {
					try {
						beginBackup();

						reloadDBFileList();

						JErrorMessage
								.show("M7504", this);
					} catch (Exception err) {
						err.printStackTrace();

						JErrorMessage
								.show("M7503", this);

						return;
					}
				}

				// �o�b�N�A�b�v�����m�F
				iResult = JErrorMessage
						.show("M7506", this);

				if (iResult == RETURN_VALUE.YES) {
					// �ꎞ�t�@�C���쐬
					if (JFileCopy.xcopyFile(JPath.DatabaseFolder,
							JPath.TEMP_SYSTEM_DATABASEFILE_PATH) > 0) {
						JErrorMessage.show("M7509", this);

						return;
					}

					// �t�@�C���폜
					if (!JFile.deleteDirectory(new File(JPath.DatabaseFolder),
							false)) {
						JErrorMessage.show("M7509", this);

						return;
					}

					// �t�@�C������
					if (JFileCopy.xcopyFile(m_fileList.get(String.valueOf(iSelect))
							.getAbsolutePath(), JPath.DatabaseFolder) > 0) {
						JErrorMessage.show("M7509", this);

						return;
					}

					// ���������I��
					if (!JFile.deleteDirectory(new File(
							JPath.TEMP_SYSTEM_DATABASEFILE_PATH), false)) {
						JErrorMessage.show("M7509", this);

						return;
					}

					// yoshida 20090105 s.inoue
					ShcDBAdjust adjuster = new ShcDBAdjust();
					adjuster.call("System");    //System.fdb��Ώ�
					adjuster.call("Kikan");     //Kikan.fdb��Ώ�

					JErrorMessage.show("M7510", this);

					reloadDBFileList();
				}
			}
		} catch (Exception err1) {
			err1.printStackTrace();

			JErrorMessage.show("M7508", this);

			try {
				// ��������
				if (JFileCopy.xcopyFile(JPath.TEMP_SYSTEM_DATABASEFILE_PATH,
						JPath.DatabaseFolder) > 0) {
					JErrorMessage
							.show("M0000", this);

					System.exit(1);
				}

				// ���������I��
				if (!JFile.deleteDirectory(new File(
						JPath.TEMP_SYSTEM_DATABASEFILE_PATH), false)) {
					JErrorMessage
							.show("M0000", this);

					return;
				}
			} catch (Exception err2) {
				err2.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(1);
			}
		} finally {
			try {
				// �f�[�^�x�[�X�Đڑ�
				connectDataBase();

			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(1);
			}
		}
	}

	/**
	 *  �o�b�N�A�b�v�폜
	 *
	 *    @param  �C�x���g
	 *
	 *    @return none
	 */
	public void pushedDeleteButton(ActionEvent e) {
		int iSelect[] = m_model.getSelectedRows();

		try {
			// �f�[�^�x�[�X�ؒf
			shutdownDataBase();

			if (iSelect.length > 0) {
				RETURN_VALUE iResult = JErrorMessage
						.show("M7511", this);

				if (iResult == RETURN_VALUE.YES) {
					for (int i = 0; i < iSelect.length; ++i) {
						if (!JFile.deleteDirectory(m_fileList.get(String.valueOf(iSelect[i]))
								.getAbsoluteFile(), true)) {
							JErrorMessage.show("M7512", this);

							return;
						}
					}

					reloadDBFileList();

					JErrorMessage.show("M7513", this);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();

			JErrorMessage.show("M7512", this);
		} finally {
			try {
				// �f�[�^�x�[�X�Đڑ�
				connectDataBase();
			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(1);
			}
		}
	}

	/**
	 *  �A�N�V�����C�x���g
	 *
	 *    @param  �C�x���g
	 *
	 *    @return none
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		/* �I���{�^�� */
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		/* �ĕ\���{�^�� */
		else if (source == jButton_Reload) {
			logger.info(jButton_Reload.getText());
			pushedReloadButton(e);
		}
		/* �o�b�N�A�b�v�{�^�� */
		else if (source == jButton_Backup) {
			logger.info(jButton_Backup.getText());
			pushedBackupButton(e);
		}
		/* �����{�^�� */
		else if (source == jButton_Restore) {
			logger.info(jButton_Restore.getText());
			pushedRestoreButton(e);
		}
		/* �폜�{�^�� */
		else if (source == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
	}

	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F7:
			logger.info(jButton_Reload.getText());
			pushedReloadButton(null);break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Backup.getText());
			pushedBackupButton(null);break;
		case KeyEvent.VK_F11:
			logger.info(jButton_Restore.getText());
			pushedRestoreButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

