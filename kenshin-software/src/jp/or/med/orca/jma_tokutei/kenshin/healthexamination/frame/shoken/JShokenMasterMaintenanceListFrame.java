package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.openswing.swing.client.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import org.openswing.swing.table.columns.client.*;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenDeleteButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenReloadButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorTeikeiResultFrameData;

/**
 * �ꗗList���
 * @author s.inoue
 * @version 2.0
 */
public class JShokenMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

	/* �ڑ� */
	protected Connection conn = null;

	protected GridControl grid = new GridControl();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	/* button */
//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
//	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

//	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
//	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
//	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected GenericButton buttonExport = null;
	protected GenericButton buttonImport = null;
	protected GenericButton buttonClose = null;

	/* control */
	protected TextColumn textColumn_ShokenTypeNo = new TextColumn();
	protected TextColumn textColumn_ShokenType = new TextColumn();
	protected TextColumn textColumn_ShokenNo = new TextColumn();
	protected TextColumn textColumn_Shoken = new TextColumn();
	protected TextColumn textColumn_TimeStamp = new TextColumn();

	private JShokenMasterMaintenanceEditFrameData validatedData = new JShokenMasterMaintenanceEditFrameData();
	private IDialog filePathDialog;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;

	private static Vector<Vector<String>> CSVItems = null;
	private static String saveTitle= "csv�t�@�C���ۑ���I��";
	private static String selectTitle= "csv�t�@�C���I��";
	private static String savaDialogTitle= "csv�t�@�C���̕ۑ����I���A�t�@�C�������w�肵�Ă�������";
	private static String selectDialogTitle= "csv�t�@�C����I�����Ă�������";

	// �����{�^����������SQL�Ŏg�p
	private static final String[] TABLE_COLUMNS = {
		"SYOKEN_TYPE","SYOKEN_TYPE_NAME","SYOKEN_NO","SYOKEN_NAME","UPDATE_TIMESTAMP" };

	private static Logger logger = Logger.getLogger(JShokenMasterMaintenanceListFrame.class);

	/* �R���X�g���N�^ */
	public JShokenMasterMaintenanceListFrame(Connection conn,
			JShokenMasterMaintenanceListFrameCtrl controller) {
		setControl(conn,controller);
	}

	/* �����[�h */
	public void reloadData() {
		grid.reloadData();
	}

	/* �O���b�hgetter */
	public GridControl getGrid() {
		return grid;
	}

	/* ���䃁�\�b�h */
	public void setControl(Connection conn,
			JShokenMasterMaintenanceListFrameCtrl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

			grid.addKeyListener(new KeyAdapter() {
			      public void keyPressed(KeyEvent e) {
			        if (e.getKeyCode()==e.VK_CANCEL || e.getKeyCode()==e.VK_BACK_SPACE || e.getKeyCode()==e.VK_DELETE)
			          System.out.println("����");
			      }
			    });

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ���������� */
	public void jbInit() throws Exception {
		/* control ��ClientApplication�ƈ�v*/
		textColumn_ShokenTypeNo.setColumnFilterable(true);
		textColumn_ShokenTypeNo.setColumnName("SYOKEN_TYPE");
		textColumn_ShokenTypeNo.setColumnSortable(true);
		textColumn_ShokenTypeNo.setPreferredWidth(80);

		textColumn_ShokenType.setColumnFilterable(true);
		textColumn_ShokenType.setColumnName("SYOKEN_TYPE_NAME");
		textColumn_ShokenType.setColumnSortable(true);
		textColumn_ShokenType.setPreferredWidth(150);

//		textColumn_ShokenNo.setColumnFilterable(true);
		textColumn_ShokenNo.setColumnName("SYOKEN_NO");
//		textColumn_ShokenNo.setColumnSortable(true);
		textColumn_ShokenNo.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_ShokenNo.setColumnVisible(true);
		textColumn_ShokenNo.setColumnFilterable(false);
		textColumn_ShokenNo.setColumnSortable(false);

//		textColumn_Shoken.setColumnFilterable(true);
		textColumn_Shoken.setColumnName("SYOKEN_NAME");
//		textColumn_Shoken.setColumnSortable(true);
		textColumn_Shoken.setPreferredWidth(500);
		// eidt s.inoue 2012/10/25
		textColumn_Shoken.setColumnVisible(true);
		textColumn_Shoken.setColumnFilterable(false);
		textColumn_Shoken.setColumnSortable(false);

//		textColumn_TimeStamp.setColumnFilterable(true);
		textColumn_TimeStamp.setColumnName("UPDATE_TIMESTAMP");
//		textColumn_TimeStamp.setColumnSortable(true);
		textColumn_TimeStamp.setPreferredWidth(120);
		// eidt s.inoue 2012/10/25
		textColumn_TimeStamp.setColumnVisible(true);
		textColumn_TimeStamp.setColumnFilterable(false);
		textColumn_TimeStamp.setColumnSortable(false);

		/* button */
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
//		buttonOriginePanel.add(insertButton, null);
//		buttonOriginePanel.add(editButton, null);
		buttonOriginePanel.add(filterButton, null);
		buttonOriginePanel.add(reloadButton, null);
		buttonOriginePanel.add(deleteButton, null);
		buttonOriginePanel.add(exportButton, null);
//		buttonOriginePanel.add(importButton, null);
		buttonOriginePanel.add(navigatorBar, null);
//		buttonOriginePanel.add(saveButton, null);

		// button����
		setJButtons();

	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
	    buttonPanel.add(buttonExport,null);
	    buttonPanel.add(buttonImport,null);

		// action�ݒ�
		buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonExport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonImport.addActionListener(new ListFrame_button_actionAdapter(this));

		Box origineBox = new Box(BoxLayout.X_AXIS);
		origineBox.add(buttonOriginePanel);
		origineBox.add(Box.createVerticalStrut(2));

		// box2�s��
		Box recordBox = new Box(BoxLayout.X_AXIS);
		recordBox.add(buttonPanel);

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(origineBox);
		buttonBox.add(new JSeparator());
		buttonBox.add(recordBox);

		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
		grid.setDeleteButton(deleteButton);
//		grid.setEditButton(editButton);
		grid.setExportButton(exportButton);
//		grid.setImportButton(importButton);
		grid.setFilterButton(filterButton);
//		grid.setSaveButton(saveButton);

//		grid.setInsertButton(insertButton);
		// add s.inoue 2012/10/25 ���ڍi��
		grid.setMaxSortedColumns(2);

		grid.setNavBar(navigatorBar);
		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName("jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken.JShokenMasterMaintenanceListData");
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(textColumn_ShokenTypeNo, null);
		grid.getColumnContainer().add(textColumn_ShokenType, null);
		grid.getColumnContainer().add(textColumn_ShokenNo, null);
		grid.getColumnContainer().add(textColumn_Shoken, null);
		grid.getColumnContainer().add(textColumn_TimeStamp, null);

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.teikei-mastermaintenance.frame.title","tokutei.teikei-mastermaintenance.frame.guidance");
		jLabel_Title.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
	}

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	protected JPanel jPanel_TitleArea = null;
	protected ExtendedLabel jLabel_Title = null;

	private JPanel getJPanel_TitleArea(Box buttonBox) {
		if (jPanel_TitleArea == null) {
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridy = 1;
			gridBagConstraints26.gridx = 0;

			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
//			gridBagConstraints25.ipady = 10;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			// gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.gridx = 0;

			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
			 jPanel_TitleArea.add(buttonBox, gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}

	/* �{�^���Q */
	/**
	 * This method initializes jButton_Close
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private void setJButtons() {
		if (buttonClose == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","�߂�(R)","�߂�(ALT+R)",KeyEvent.VK_R,icon);
			buttonClose.addActionListener(this);
		}
		if (buttonExport == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Export);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonExport= new ExtendedOpenGenericButton(
					"Exort","���o(O)","���o(ALT+O)",KeyEvent.VK_O,icon);
			buttonExport.addActionListener(this);
		}
		if (buttonImport == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Import);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonImport= new ExtendedOpenGenericButton(
					"Import","�捞(I)","�捞(ALT+I)",KeyEvent.VK_I,icon);
			buttonImport.addActionListener(this);
		}
	}

	// add s.inoue 2010/03/12
	/**ImportStart����������**********************************************************/
	/**
	 * �捞�{�^��
	 */
	public void pushedImportButton( ActionEvent e )
	{
		try {
			filePathDialog = DialogFactory.getInstance().createDialog(this,null);

			filePathDialog.setMessageTitle(selectTitle);
			filePathDialog.setDialogTitle(selectDialogTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(true);
			filePathDialog.setVisible(true);

			// eidt s.inoue 2012/07/06
			if (filePathDialog.getStatus() == null)return;
			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;

			String filePath = filePathDialog.getFilePath();
			// add s.inoue 2010/03/05
			File fileImport = new File(filePath);
			if(!fileImport.exists())
				return;
			importCsvData(filePath);
			// del s.inoue 2011/04/07
			// initTable();
		} catch (Exception ex) {
			JErrorMessage.show("M9912",this);
			logger.error(ex.getMessage());
		}
	}

	// edit s.inoue 2010/03/04
	/* �捞���� */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M9916", this);

		// cancel��
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();

			// CSV�Ǎ�����
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
			} catch (IOException e) {
				JErrorMessage.show("M9913",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M9914",this);
					return;
				}

				// ��^���}�X�^�S�폜
				teikeiMasterDelete();

				// �f�[�^�捞����
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// �������擾 CSV�f�[�^�����[�J���ϐ��ɃZ�b�g(�u"�v��������������)
					setCSVItemsToDB(data,insertRow);

					// validate����
					if (!validateData(data))
						return;

					// ��^���o�^
					teikeiMasterRegister(data);
				}

				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();
				reloadButton.doClick();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M9915",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M9913",this);
				logger.error(e.getMessage());
			}
		}
	}

	// edit s.inoue 2010/03/04
	/* csv�f�[�^�捞 */
	private void setCSVItemsToDB(JImportMasterErrorTeikeiResultFrameData data,
			Vector<String> insertRow){

		data.CSV_COLUMN_SYOKEN_TYPE = reader.rmQuart(insertRow.get(0));
		data.CSV_COLUMN_SYOKEN_TYPE_NAME = reader.rmQuart(insertRow.get(1));
		data.CSV_COLUMN_SYOKEN_NO = reader.rmQuart(insertRow.get(2));
		data.CSV_COLUMN_SYOKEN_NAME = reader.rmQuart(insertRow.get(3));
	}

	// edit s.inoue 2010/03/04
	/* validate���� */
	private boolean validateData(JImportMasterErrorTeikeiResultFrameData data) {

		boolean rettanka = false;
		// edit s.inoue 2010/05/19
		rettanka= validatedData.setTeikeiType(data.CSV_COLUMN_SYOKEN_TYPE)
			&& validatedData.setTeikeiTypeName(data.CSV_COLUMN_SYOKEN_TYPE_NAME)
			&& validatedData.setTeikeiNumber(data.CSV_COLUMN_SYOKEN_NO)
			&& validatedData.setTeikeibun(data.CSV_COLUMN_SYOKEN_NAME);
		return rettanka;
	}

	/**
	 * ������^���}�X�^�폜
	 * @throws SQLException
	 */
	private void teikeiMasterDelete(){
		try {

			String query = new String("DELETE FROM T_SYOKENMASTER ");
			JApplication.kikanDatabase.sendNoResultQuery(query);
		} catch (SQLException e) {
			JErrorMessage.show("M9908",this);
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * CSV�f�[�^�o�^
	 * @throws SQLException
	 */
	private void teikeiMasterRegister(JImportMasterErrorTeikeiResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		// timestamp�擾
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		buffer.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP)");
		buffer.append("VALUES (");
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiType()));
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiTypeName()));
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiNumber()));
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun()));
		buffer.append(JQueryConvert.queryConvert(stringTimeStamp));
		buffer.append(")");

		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());

	}
	/**ImportEnd����������**********************************************************/

	/**ExportStart����������**********************************************************/
	// add s.inoue 2010/02/25
	/**
	 * ���o�{�^��
	 */
	public void pushedExportButton( ActionEvent e )
	{
		try {
			String saveFileName = JPath.createDirectoryUniqueName("SyokenMaster");

			String defaltPath = JPath.getDesktopPath() +
			File.separator +
			saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
			filePathDialog.setMessageTitle(saveTitle);
			filePathDialog.setDialogTitle(savaDialogTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(true);
			filePathDialog.setVisible(true);

			// eidt s.inoue 2012/07/06
			if (filePathDialog.getStatus() == null)return;
			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;

			String filePath = filePathDialog.getFilePath();
			if (filePath.equals(""))
				return;
			exportCsvData(filePath);

		} catch (Exception ex) {
			JErrorMessage.show("M9909", this);
			logger.error(ex.getMessage());
		}
	}

	/* export���� */
	private void exportCsvData(String filePath){
		JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();

		// CSV�Ǎ�����
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M9909", this);
			logger.error(e.getMessage());
		}
	}

	/* DB���f�[�^�擾*/
	private Vector<Vector<String>> getExportData(){

		Vector<Vector<String>> newTable = new Vector<Vector<String>>();

		ArrayList<Hashtable<String, String>> result
			= new ArrayList<Hashtable<String, String>>();

		// add s.inoue 2010/03/04
		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP ");
		sb.append(" FROM T_SYOKENMASTER ");
		sb.append(" ORDER BY SYOKEN_TYPE,SYOKEN_NO ");

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		Hashtable<String, String> resultItem = new Hashtable<String, String>();
		for( int i=0; i<result.size(); ++i )
		{
			resultItem = result.get(i);

			Vector<String> data = new Vector<String>();

			for (int j=0; j<resultItem.size(); ++j){
				data.add(resultItem.get(TABLE_COLUMNS[j]).trim());
			}

			// header�ݒ�
			if (i==0){
				Vector colmn = new Vector<String>();
				List l = java.util.Arrays.asList(TABLE_COLUMNS);
				for (Iterator item = l.iterator(); item.hasNext();) {
					colmn.add((String)item.next());
				}
				newTable.add(colmn);
			}
			newTable.add(data);
		}
		return newTable;
	}
	/**ExportEnd����������**********************************************************/


	/* �{�^���A�N�V�����p�����N���X */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
	  JShokenMasterMaintenanceListFrame adaptee;

		  ListFrame_button_actionAdapter(JShokenMasterMaintenanceListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  public void actionPerformed(ActionEvent e) {
			  Object source = e.getSource();
			  if (source == buttonClose){
				  logger.info(buttonClose.getText());
				  adaptee.closeButtton_actionPerformed(e);
			  }else if(source == buttonExport){
				  logger.info(buttonExport.getText());
				  pushedExportButton(e);
			  }else if(source == buttonImport){
				  logger.info(buttonImport.getText());
				  pushedImportButton(e);
			  }
		  }
		@Override
		public void keyPressed(KeyEvent e) {
			adaptee.closeButtton_keyPerformed(e);

		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u

		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u

		}
	}

	/* �{�^���A�N�V���� */
	public void closeButtton_actionPerformed(ActionEvent e) {
		dispose();
	}
	/* �{�^���A�N�V���� */
	public void closeButtton_keyPerformed(KeyEvent e) {
		dispose();
	}

	// �C�x���g����
	public void actionPerformed(ActionEvent ae) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
}