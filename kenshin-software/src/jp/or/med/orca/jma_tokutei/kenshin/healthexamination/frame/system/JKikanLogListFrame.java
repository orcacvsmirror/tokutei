package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filter.SpecialFilterPanel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GenericButton;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;

/**
 * �ꗗList���
 * @author s.inoue
 * @version 2.0
 */
public class JKikanLogListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = -5541152768937027572L;	// edit n.ohkubo 2014/10/01�@�ǉ�

	/* �ڑ� */
//	protected Connection conn = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

	protected GridControl grid = new GridControl();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	/* button */
//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
//	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
//	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

//	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
//	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
//	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
//	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected GenericButton buttonExport = null;
	protected GenericButton buttonImport = null;
	protected GenericButton buttonClose = null;

	/* control */
	protected ComboColumn textColumn_MessageLevel = new ComboColumn();
	protected TextColumn textColumn_Message = new TextColumn();
	protected ComboColumn textColumn_ErrorPlace = new ComboColumn();
//	protected TextColumn textColumn_LogDate = new TextColumn();
	protected TextColumn textColumn_TimeStamp = new TextColumn();
	protected TextColumn textColumn_TimeStamp2 = new TextColumn();	// edit n.ohkubo 2014/10/01�@�ǉ�

//	private JKikanLogListFrameData validatedData = new JKikanLogListFrameData();	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	private IDialog filePathDialog;
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;

//	private static Vector<Vector<String>> CSVItems = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	private static String saveTitle= "csv�t�@�C���ۑ���I��";
	private static String selectTitle= "csv�t�@�C���I��";
	private static String savaDialogTitle= "csv�t�@�C���̕ۑ����I���A�t�@�C�������w�肵�Ă�������";
	private static String selectDialogTitle= "csv�t�@�C����I�����Ă�������";

	// �����{�^����������SQL�Ŏg�p
	private static final String[] TABLE_COLUMNS = {
		"SYOKEN_TYPE","SYOKEN_TYPE_NAME","SYOKEN_NO","SYOKEN_NAME","UPDATE_TIMESTAMP" };

	private static Logger logger = Logger.getLogger(JKikanLogListFrame.class);

	// edit n.ohkubo 2014/10/01�@�ǉ� start
	private ColumnContainer columnContainer;
	public ColumnContainer getColumnContainer() {
		return this.columnContainer;
	}
	// edit n.ohkubo 2014/10/01�@�ǉ� end

	/* �R���X�g���N�^ */
	public JKikanLogListFrame(
			JKikanLogListFrameCtl controller) {
		setControl(controller);
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
	public void setControl(
			JKikanLogListFrameCtl controller){
//		this.conn = conn;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

			grid.addKeyListener(new KeyAdapter() {
			      @Override
				public void keyPressed(KeyEvent e) {
			        if (e.getKeyCode()==e.VK_CANCEL || e.getKeyCode()==e.VK_BACK_SPACE || e.getKeyCode()==e.VK_DELETE)
			          System.out.println("����");
			      }
			    });
			
			// edit n.ohkubo 2014/10/01�@�ǉ� start�@������ʂ̃{�^���̑傫����ύX
			//�t�B���^�[�p�l���p��WindowListener
			SpecialFilterPanel specialFilterPanel = new SpecialFilterPanel(null, grid.getParent().getComponents());
			
			//���̃t���[���i�ꗗ��ʁj���A�N�e�B�u���i��ʉE�̌����E�B���h�E�j�@or�@��A�N�e�B�u���i�����E�B���h�E���|�b�v�A�b�v�ŊJ����ꍇ�j���ꂽ�Ƃ��ɓ��삷��悤�ɁAListener��ݒ�
			this.addWindowListener(specialFilterPanel);
			// edit n.ohkubo 2014/10/01�@�ǉ� end�@������ʂ̃{�^���̑傫����ύX

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ���������� */
	public void jbInit() throws Exception {

		textColumn_ErrorPlace.setColumnFilterable(true);
		textColumn_ErrorPlace.setColumnName("LOG_ERROR_PLACE");
		textColumn_ErrorPlace.setColumnSortable(true);
		textColumn_ErrorPlace.setPreferredWidth(250);
		textColumn_ErrorPlace.setDomainId("LOG_PLACE");

//		textColumn_MessageLevel.setColumnFilterable(true);
		textColumn_MessageLevel.setColumnName("LOG_MESSAGE_LEVEL");
//		textColumn_MessageLevel.setColumnSortable(true);
		textColumn_MessageLevel.setPreferredWidth(100);
		textColumn_MessageLevel.setColumnVisible(true);
		textColumn_MessageLevel.setColumnFilterable(true);
		textColumn_MessageLevel.setColumnSortable(true);
		textColumn_MessageLevel.setDomainId("LOG_LEVEL");

//		textColumn_Message.setColumnFilterable(true);
		textColumn_Message.setColumnName("LOG_MESSAGE");
//		textColumn_Message.setColumnSortable(true);
		textColumn_Message.setPreferredWidth(400);
		textColumn_Message.setColumnVisible(true);
		textColumn_Message.setColumnFilterable(true);
		textColumn_Message.setColumnSortable(true);
		textColumn_Message.setMaxCharacters(256);	// edit n.ohkubo 2014/10/01�@�ǉ�

		/* control ��ClientApplication�ƈ�v*/
//		textColumn_LogDate.setColumnFilterable(true);
//		textColumn_LogDate.setColumnName("LOG_DATE");
//		textColumn_LogDate.setColumnSortable(true);
//		textColumn_LogDate.setPreferredWidth(80);

//		textColumn_TimeStamp.setColumnFilterable(true);
		textColumn_TimeStamp.setColumnName("UPDATE_TIMESTAMP");
//		textColumn_TimeStamp.setColumnSortable(true);
		textColumn_TimeStamp.setPreferredWidth(200);
		textColumn_TimeStamp.setColumnVisible(true);
//		textColumn_TimeStamp.setColumnFilterable(false);	// edit n.ohkubo 2014/10/01�@�폜
		textColumn_TimeStamp.setColumnFilterable(true);	// edit n.ohkubo 2014/10/01�@�ǉ�
		textColumn_TimeStamp.setColumnSortable(true);
		textColumn_TimeStamp.setMaxCharacters(23);	// edit n.ohkubo 2014/10/01�@�ǉ�
		
		// edit n.ohkubo 2014/10/01�@�ǉ� start
		textColumn_TimeStamp2.setColumnName("UPDATE_TIMESTAMP2");
		textColumn_TimeStamp2.setColumnVisible(false);
		textColumn_TimeStamp2.setColumnFilterable(true);
		textColumn_TimeStamp2.setColumnSortable(false);
		textColumn_TimeStamp2.setMaxCharacters(23);
		// edit n.ohkubo 2014/10/01�@�ǉ� end

		/* button */
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
//		buttonOriginePanel.add(insertButton, null);
//		buttonOriginePanel.add(editButton, null);
		buttonOriginePanel.add(filterButton, null);	// edit n.ohkubo 2014/10/01�@�ǉ�
		buttonOriginePanel.add(reloadButton, null);
//		buttonOriginePanel.add(deleteButton, null);
//		buttonOriginePanel.add(exportButton, null);
//		buttonOriginePanel.add(importButton, null);
		buttonOriginePanel.add(navigatorBar, null);
//		buttonOriginePanel.add(saveButton, null);

		// button����
		setJButtons();

	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
//	    buttonPanel.add(buttonExport,null);
//	    buttonPanel.add(buttonImport,null);

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
//		grid.setDeleteButton(deleteButton);
//		grid.setEditButton(editButton);
//		grid.setExportButton(exportButton);
//		grid.setImportButton(importButton);
		grid.setFilterButton(filterButton);
//		grid.setSaveButton(saveButton);

//		grid.setInsertButton(insertButton);
		// add s.inoue 2012/10/25 ���ڍi��
		grid.setMaxSortedColumns(2);

		grid.setNavBar(navigatorBar);
		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName("jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system.JKikanLogListFrameData");
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(textColumn_MessageLevel, null);
		grid.getColumnContainer().add(textColumn_Message, null);
		grid.getColumnContainer().add(textColumn_ErrorPlace, null);
//		grid.getColumnContainer().add(textColumn_LogDate, null);
		grid.getColumnContainer().add(textColumn_TimeStamp, null);
		grid.getColumnContainer().add(textColumn_TimeStamp2, null);	// edit n.ohkubo 2014/10/01�@�ǉ�

		columnContainer = grid.getColumnContainer();// edit n.ohkubo 2014/10/01�@�ǉ�

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.logfile-mastermaintenance.frame.title","tokutei.logfile-mastermaintenance.frame.guidance");
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
//			importCsvData(filePath);
			// del s.inoue 2011/04/07
			// initTable();
		} catch (Exception ex) {
			JErrorMessage.show("M9912",this);
			logger.error(ex.getMessage());
		}
	}

//	// edit s.inoue 2010/03/04
//	/* �捞���� */
//	private void importCsvData(String filePath){
//
//		RETURN_VALUE retValue = JErrorMessage.show("M9916", this);
//
//		// cancel��
//		if (retValue == RETURN_VALUE.NO){
//			return;
//		}else if (retValue == RETURN_VALUE.YES){
//
//			JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();
//
//			// CSV�Ǎ�����
//			reader = new JCSVReaderStream();
//
//			try {
//				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
//			} catch (IOException e) {
//				JErrorMessage.show("M9913",this);
//				logger.error(e.getMessage());
//			}
//
//			CSVItems = reader.readAllTable();
//
//			try {
//				// eidt s.inoue 2011/06/07
//				// JApplication.kikanDatabase.Transaction();
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//
//				int csvCount = CSVItems.size();
//
//				if(csvCount == 0){
//					JErrorMessage.show("M9914",this);
//					return;
//				}
//
//				// ��^���}�X�^�S�폜
//				teikeiMasterDelete();
//
//				// �f�[�^�捞����
//				for (int i = 1; i < csvCount; i++) {
//
//					Vector<String> insertRow = new Vector<String>();
//
//					insertRow =CSVItems.get(i);
//
//					// �������擾 CSV�f�[�^�����[�J���ϐ��ɃZ�b�g(�u"�v��������������)
////					setCSVItemsToDB(data,insertRow);
//
////					// validate����
////					if (!validateData(data))
////						return;
//
////					// ��^���o�^
////					teikeiMasterRegister(data);
//				}
//
//				// eidt s.inoue 2011/06/07
//				// JApplication.kikanDatabase.Commit();
//				JApplication.kikanDatabase.getMConnection().commit();
//				reloadButton.doClick();
//
//				String[] messageParams = {String.valueOf(csvCount-1)};
//				JErrorMessage.show("M9915",this,messageParams);
//			} catch (SQLException e) {
//				try {
//					// eidt s.inoue 2011/06/07
//					// JApplication.kikanDatabase.rollback();
//					JApplication.kikanDatabase.getMConnection().rollback();
//				} catch (SQLException e1) {}
//				JErrorMessage.show("M9913",this);
//				logger.error(e.getMessage());
//			}
//		}
//	}

//	// edit s.inoue 2010/03/04
//	/* csv�f�[�^�捞 */
//	private void setCSVItemsToDB(JImportMasterErrorTeikeiResultFrameData data,
//			Vector<String> insertRow){
//
//		data.CSV_COLUMN_SYOKEN_TYPE = reader.rmQuart(insertRow.get(0));
//		data.CSV_COLUMN_SYOKEN_TYPE_NAME = reader.rmQuart(insertRow.get(1));
//		data.CSV_COLUMN_SYOKEN_NO = reader.rmQuart(insertRow.get(2));
//		data.CSV_COLUMN_SYOKEN_NAME = reader.rmQuart(insertRow.get(3));
//	}

//	// edit s.inoue 2010/03/04
//	/* validate���� */
//	private boolean validateData(JImportMasterErrorTeikeiResultFrameData data) {
//
//		boolean rettanka = false;
//		// edit s.inoue 2010/05/19
//		rettanka= validatedData.setTeikeiType(data.CSV_COLUMN_SYOKEN_TYPE)
//			&& validatedData.setTeikeiTypeName(data.CSV_COLUMN_SYOKEN_TYPE_NAME)
//			&& validatedData.setTeikeiNumber(data.CSV_COLUMN_SYOKEN_NO)
//			&& validatedData.setTeikeibun(data.CSV_COLUMN_SYOKEN_NAME);
//		return rettanka;
//	}

//	/**
//	 * ������^���}�X�^�폜
//	 * @throws SQLException
//	 */
//	private void teikeiMasterDelete(){
//		try {
//
//			String query = new String("DELETE FROM T_SYOKENMASTER ");
//			JApplication.kikanDatabase.sendNoResultQuery(query);
//		} catch (SQLException e) {
//			JErrorMessage.show("M9908",this);
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//	}

//	/**
//	 * CSV�f�[�^�o�^
//	 * @throws SQLException
//	 */
//	private void teikeiMasterRegister(JImportMasterErrorTeikeiResultFrameData data)
//		throws SQLException
//	{
//		StringBuffer buffer = new StringBuffer();
//
//		// timestamp�擾
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());
//
//		buffer.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP)");
//		buffer.append("VALUES (");
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiType()));
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiTypeName()));
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiNumber()));
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun()));
//		buffer.append(JQueryConvert.queryConvert(stringTimeStamp));
//		buffer.append(")");
//
//		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//
//	}
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
//		JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

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
	  JKikanLogListFrame adaptee;

		  ListFrame_button_actionAdapter(JKikanLogListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  @Override
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

		}
		@Override
		public void keyTyped(KeyEvent e) {

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
	@Override
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
