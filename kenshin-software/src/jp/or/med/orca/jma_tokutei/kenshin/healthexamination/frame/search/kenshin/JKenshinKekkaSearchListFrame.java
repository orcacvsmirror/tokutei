package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Serche;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenNavigatorBar;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendendGridControl;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintIraisho;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintNyuryoku;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Iraisho;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.table.columns.client.CheckBoxColumn;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;
import org.openswing.swing.util.java.Consts;

/**
 * �ꗗList���
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinKekkaSearchListFrame extends JFrame implements KeyListener,ActionListener {

	protected Connection conn = null;
	// eidt s.inoue 2012/02/27
	protected GridControl grid = new GridControl();
//	protected ExtendendGridControl grid = new ExtendendGridControl(new DefaultTableCellRenderer());

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();  //  @jve:decl-index=0:
	protected FlowLayout flowLayoutPanel = new FlowLayout();  //  @jve:decl-index=0:

	/* button ���� */
//	protected InsertButton insertButton = new InsertButton();
//	protected EditButton editButton = new EditButton();
//	protected SaveButton saveButton = new SaveButton();
//	protected DeleteButton deleteButton = new DeleteButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	// protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	// del s.inoue 2012/11/14
//	protected ExportButton exportButton = new ExportButton();
	protected ExtendedOpenNavigatorBar navigatorBar = new ExtendedOpenNavigatorBar();
	protected TextControl countText = new TextControl();

	// button�R���g���[��
	protected ExtendedOpenGenericButton buttonCheck = null;
	protected ExtendedOpenGenericButton buttonClose = null;
	protected ExtendedOpenGenericButton buttonDeleteKekka = null;
	protected ExtendedOpenGenericButton buttonDeleteKojin = null;
	protected ExtendedOpenGenericButton buttonJusinkenAdd = null;
	protected ExtendedOpenGenericButton buttonNyuryokuPrint = null;
	protected ExtendedOpenGenericButton buttonIraiPrint = null;
	protected ExtendedOpenGenericButton buttonKekkaCopy = null;
	protected ExtendedOpenGenericButton buttonJusinkenCall = null;
	protected ExtendedOpenGenericButton buttonKekkaInputCall = null;
// edit s.inoue 2011/02/16 �ۗ�
//	protected CheckBoxControl checkBoxSelect = null;

	/* control */
	protected TextColumn textColumn_Name = new TextColumn();
	protected TextColumn textColumn_HokensyoCode = new TextColumn();
	protected TextColumn textColumn_HokensyoNumber = new TextColumn();
	protected TextColumn dateColumn_KenshinDate = new TextColumn();
	protected TextColumn dateColumn_KenshinDateTo = new TextColumn();

	// eidt s.inoue 2012/10/23
	// protected TextColumn textColumn_sex = new TextColumn();
	protected ComboColumn textColumn_sex = new ComboColumn();
	protected ComboColumn textColumn_inputFlg = new ComboColumn();
	protected ComboColumn textColumn_hokenjaNo = new ComboColumn();

	protected TextColumn textColumn_birthdayFrom = new TextColumn();
	protected TextColumn textColumn_birthdayTo = new TextColumn();

	protected TextColumn textColumn_jyushinSeiriNo = new TextColumn();
	protected TextColumn textColumn_shiharaiDaikouNo = new TextColumn();
	protected TextColumn textColumn_kanaName = new TextColumn();
	protected TextColumn textColumn_hanteiNengapi = new TextColumn();
	protected TextColumn textColumn_tutiNengapi = new TextColumn();
	protected TextColumn textColumn_nendo = new TextColumn();
	protected CheckBoxColumn checkColumn_checkBox = new CheckBoxColumn();

	private IDialog dateSelectDialog;

	protected int currentRow = 0;
	protected int currentPage = 0;
	protected int currentTotalRows = 0;

	private static final String SQL_DELETE_KOJIN ="delete from T_KOJIN where UKETUKE_ID = ? ";
	private static final String SQL_DELETE_NAYOSE ="delete from T_NAYOSE where UKETUKE_ID = ? ";
	private static final String SQL_SELECT_TOKUTEI ="select count(1) as num from T_KENSAKEKA_TOKUTEI where UKETUKE_ID = ? ";  //  @jve:decl-index=0:
	private static final String SQL_DELETE_TOKUTEI ="delete from T_KENSAKEKA_TOKUTEI where UKETUKE_ID = ? and KENSA_NENGAPI = ? ";
	private static final String SQL_DELETE_SONOTA ="delete from T_KENSAKEKA_SONOTA where UKETUKE_ID = ? and KENSA_NENGAPI = ? ";

	protected final static String CONST_VALUE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameData";

	private static Logger logger = Logger.getLogger(JKenshinKekkaSearchListFrame.class);  //  @jve:decl-index=0:
	private boolean chkFlg = false;

	/* �R���X�g���N�^ */
	public JKenshinKekkaSearchListFrame(Connection conn,
			JKenshinKekkaSearchListFrameCtl controller) {
		setControl(conn,controller);

		addKeyListener(this);
	}

	/* �����[�h */
	public void reloadData() {
		grid.reloadData();
	}

	/* �O���b�hgetter */
	public GridControl getGrid() {
		return grid;
	}

	private JKenshinKekkaSearchListFrameCtl controller = null;  //  @jve:decl-index=0:

	/* ���䃁�\�b�h */
	public void setControl(Connection conn,
			JKenshinKekkaSearchListFrameCtl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

			this.controller = controller;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ���������� */
	/**
	 * @throws Exception
	 */
	public void jbInit() throws Exception {
		/* control ��ClientApplication�ƈ�v*/
		textColumn_Name.setColumnFilterable(true);
		textColumn_Name.setColumnName("NAME");
		textColumn_Name.setColumnSortable(true);
		textColumn_Name.setPreferredWidth(120);
		// eidt s.inoue 2012/10/24
		textColumn_Name.setColumnFilterable(false);
		textColumn_Name.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_Name.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.NAME));
		// textColumn_Name.setColumnVisible(false);
		
		textColumn_HokensyoCode.setColumnFilterable(true);
		textColumn_HokensyoCode.setColumnName("HIHOKENJYASYO_KIGOU");
		textColumn_HokensyoCode.setColumnSortable(true);
		textColumn_HokensyoCode.setPreferredWidth(110);
		// eidt s.inoue 2012/10/24
		textColumn_HokensyoCode.setColumnFilterable(false);
		textColumn_HokensyoCode.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_HokensyoCode.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));

		textColumn_HokensyoNumber.setColumnFilterable(true);
		textColumn_HokensyoNumber.setColumnName("HIHOKENJYASYO_NO");
		textColumn_HokensyoNumber.setColumnSortable(true);
		textColumn_HokensyoNumber.setPreferredWidth(110);
		// eidt s.inoue 2012/10/24
		textColumn_HokensyoNumber.setColumnFilterable(false);
		textColumn_HokensyoNumber.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_HokensyoNumber.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_NO));

		dateColumn_KenshinDate.setColumnFilterable(true);
		dateColumn_KenshinDate.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDate.setColumnSortable(true);
		dateColumn_KenshinDate.setPreferredWidth(80);

		// edit s.inoue 2013/11/12
		dateColumn_KenshinDate.setColumnFilterable(true);
		dateColumn_KenshinDate.setColumnSortable(false);
		dateColumn_KenshinDate.setColumnSelectable(false);
		dateColumn_KenshinDate.setColumnVisible(false);
		
		// add s.inoue 2012/10/23
		dateColumn_KenshinDateTo.setColumnFilterable(true);
		dateColumn_KenshinDateTo.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDateTo.setColumnSortable(true);
		dateColumn_KenshinDateTo.setColumnSelectable(true);//eidt tanaka 2013/11/08

		//add tanaka 2013/11/06
		dateColumn_KenshinDateTo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.KENSA_NENGAPI));

// eidt s.inoue 2012/10/23
//		textColumn_sex.setColumnFilterable(true);
//		textColumn_sex.setColumnName("SEX");
//		textColumn_sex.setColumnSortable(true);
//		textColumn_sex.setPreferredWidth(40);
		textColumn_sex.setColumnFilterable(true);
		textColumn_sex.setColumnName("SEX");
		textColumn_sex.setColumnSortable(true);
		textColumn_sex.setPreferredWidth(40);
	    textColumn_sex.setEditableOnEdit(true);
	    textColumn_sex.setEditableOnInsert(true);
	    textColumn_sex.setColumnRequired(false);
	    textColumn_sex.setDomainId("SEX");
	    //add tanaka 2013/11/06
	    textColumn_sex.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.SEX));

		textColumn_birthdayFrom.setColumnFilterable(true);
		textColumn_birthdayFrom.setColumnName("BIRTHDAY");
		textColumn_birthdayFrom.setColumnSortable(true);
		textColumn_birthdayFrom.setPreferredWidth(80);

		// edit s.inoue 2013/11/12
		textColumn_birthdayFrom.setColumnVisible(false);
		textColumn_birthdayFrom.setColumnFilterable(true);
		textColumn_birthdayFrom.setColumnSortable(false);
		textColumn_birthdayFrom.setColumnSelectable(false);
		
		// eidt s.inoue 2012/10/24
		textColumn_birthdayTo.setColumnName("BIRTHDAY");
		textColumn_birthdayTo.setPreferredWidth(80);

		//add tanaka 2013/11/06
		textColumn_birthdayTo.setColumnFilterable(true);
		textColumn_birthdayTo.setColumnSortable(true);
		textColumn_birthdayTo.setColumnSelectable(true);//eidt tanaka 2013/11/08
		textColumn_birthdayTo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.BIRTHDAY));

		// eidt s.inoue 2012/10/23
//		textColumn_inputFlg.setColumnFilterable(true);
//		textColumn_inputFlg.setColumnName("KEKA_INPUT_FLG");
//		textColumn_inputFlg.setColumnSortable(true);
//		textColumn_inputFlg.setPreferredWidth(40);
		textColumn_inputFlg.setColumnFilterable(true);
		textColumn_inputFlg.setColumnName("KEKA_INPUT_FLG");
		textColumn_inputFlg.setColumnSortable(true);
		
		textColumn_inputFlg.setPreferredWidth(40);
	    textColumn_inputFlg.setEditableOnEdit(true);
	    textColumn_inputFlg.setEditableOnInsert(true);
	    textColumn_inputFlg.setColumnRequired(false);
	    textColumn_inputFlg.setDomainId("KEKA_INPUT_FLG");
	    //add tanaka2013/11/06
	    textColumn_inputFlg.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.KEKA_INPUT_FLG));

		textColumn_jyushinSeiriNo.setColumnFilterable(true);
		textColumn_jyushinSeiriNo.setColumnName("JYUSHIN_SEIRI_NO");
		textColumn_jyushinSeiriNo.setColumnSortable(true);
		textColumn_jyushinSeiriNo.setPreferredWidth(100);

		// add s.inoue 2013/10/29
		textColumn_jyushinSeiriNo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO));

		textColumn_hokenjaNo.setColumnFilterable(true);
		textColumn_hokenjaNo.setColumnName("HKNJANUM");
		textColumn_hokenjaNo.setColumnSortable(true);
		textColumn_hokenjaNo.setPreferredWidth(250);
		textColumn_hokenjaNo.setDomainId("T_HOKENJYA");
		//add tanaka 2013/11/06
		textColumn_hokenjaNo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HKNJANUM));

		textColumn_shiharaiDaikouNo.setColumnFilterable(true);
		textColumn_shiharaiDaikouNo.setColumnName("SIHARAI_DAIKOU_BANGO");
		textColumn_shiharaiDaikouNo.setColumnSortable(true);
		textColumn_shiharaiDaikouNo.setPreferredWidth(150);
		// eidt s.inoue 2012/10/24
		textColumn_shiharaiDaikouNo.setColumnVisible(false);
		textColumn_shiharaiDaikouNo.setColumnFilterable(false);
		textColumn_shiharaiDaikouNo.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_shiharaiDaikouNo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));

		textColumn_kanaName.setColumnFilterable(true);
		textColumn_kanaName.setColumnName("KANANAME");
		textColumn_kanaName.setColumnSortable(true);
		textColumn_kanaName.setPreferredWidth(175);
		//add tanaka 2013/11/06
		textColumn_kanaName.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.KANANAME));
		
		textColumn_hanteiNengapi.setColumnFilterable(true);
		textColumn_hanteiNengapi.setColumnName("HANTEI_NENGAPI");
		textColumn_hanteiNengapi.setColumnSortable(true);
		textColumn_hanteiNengapi.setPreferredWidth(80);
		// eidt s.inoue 2012/10/24
		textColumn_hanteiNengapi.setColumnFilterable(false);
		textColumn_hanteiNengapi.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_hanteiNengapi.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HANTEI_NENGAPI));

		textColumn_tutiNengapi.setColumnFilterable(true);
		textColumn_tutiNengapi.setColumnName("TUTI_NENGAPI");
		textColumn_tutiNengapi.setColumnSortable(true);
		textColumn_tutiNengapi.setPreferredWidth(80);
		// eidt s.inoue 2012/10/24
		textColumn_tutiNengapi.setColumnFilterable(false);
		textColumn_tutiNengapi.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_tutiNengapi.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.TUTI_NENGAPI));

		textColumn_nendo.setColumnFilterable(true);
		textColumn_nendo.setColumnName("NENDO");
		textColumn_nendo.setColumnSortable(true);
		textColumn_nendo.setPreferredWidth(40);
		// add s.inoue 2012/10/24
		textColumn_nendo.setColumnSortable(true);
		textColumn_nendo.setColumnSelectable(true);

		textColumn_nendo.setEditableOnEdit(true);
		textColumn_nendo.setEditableOnInsert(true);
		textColumn_nendo.setColumnDuplicable(true);
		textColumn_nendo.setColumnRequired(false);
		textColumn_nendo.setSelectDataOnEdit(true);
		//add tanaka 2013/11/06
		textColumn_nendo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.NENDO));

// del s.inoue 2012/11/28
		checkColumn_checkBox.setColumnFilterable(false);
		checkColumn_checkBox.setColumnSortable(false);
		checkColumn_checkBox.setColumnName("CHECKBOX_COLUMN");
		checkColumn_checkBox.setPreferredWidth(50);
		//add tanaka 2013/11/06
		checkColumn_checkBox.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.CHECKBOX_COLUMN));
		// eidt s.inoue 2012/11/28
		checkColumn_checkBox.setEditableOnEdit(true);
		checkColumn_checkBox.setEditableOnInsert(true);
		checkColumn_checkBox.setPositiveValue("Y");
		checkColumn_checkBox.setNegativeValue("N");
		// checkColumn_checkBox.setListFilter(null);

		// add s.inoue 2012/11/29
		checkColumn_checkBox.setFocusable(false);
		checkColumn_checkBox.setEnabled(false);

		// add
		checkColumn_checkBox.setColumnDuplicable(true);
		checkColumn_checkBox.setColumnRequired(false);
		// eidt s.inoue 2012/11/29
		checkColumn_checkBox.setEnableInReadOnlyMode(true);
		checkColumn_checkBox.setAllowNullValue(false);

		// add s.inoue 2013/11/19
		if(JApplication.currentSortedColumns != null){
			
			for (int i = 0; i < JApplication.currentSortedColumns.size(); i++) {
    			String sortclumn = (String)JApplication.currentSortedColumns.get(i);
    			if(sortclumn.equals("KANANAME")){
    				textColumn_kanaName.setSortingOrder(i+1);
    				
    				String sort = JApplication.currentSorted.get(i);
    	  			if(sort.equals("ASC")){
        				textColumn_kanaName.setSortVersus(Consts.ASC_SORTED);
        			}else if (sort.equals("DESC")){
        				textColumn_kanaName.setSortVersus(Consts.DESC_SORTED);
        			}
    			}
    			
    			if(sortclumn.equals("JYUSHIN_SEIRI_NO")){
    				textColumn_kanaName.setSortingOrder(i+1);
    				
    				String sort = JApplication.currentSorted.get(i);
    	  			if(sort.equals("ASC")){
        				textColumn_jyushinSeiriNo.setSortVersus(Consts.ASC_SORTED);
        			}else if (sort.equals("DESC")){
        				textColumn_jyushinSeiriNo.setSortVersus(Consts.DESC_SORTED);
        			}
    			}
			}
		}

		// openswing s.inoue 2011/01/25
		/* button */
		// close�{�^��
		setJButtons();

		// openswing s.inoue 2011/02/03
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
//	    buttonsPanel.add(insertButton, null);
//	    buttonOriginePanel.add(editButton, null);
//	    buttonOriginePanel.add(saveButton, null);
//	    buttonOriginePanel.add(deleteButton, null);
	    buttonOriginePanel.add(filterButton, null);
	    buttonOriginePanel.add(reloadButton, null);
//	    buttonOriginePanel.add(exportButton, null);
	    buttonOriginePanel.add(buttonCheck,null);
	    buttonOriginePanel.add(navigatorBar, null);
	    buttonOriginePanel.add(countText, null);

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);

	    // eidt s.inoue 2011/04/21
//	    buttonPanel.add(buttonCheck,null);

	    buttonPanel.add(buttonClose, null);
//	    buttonPanel.add(checkBoxSelect,null);
	    buttonPanel.add(buttonJusinkenCall, null);
	    buttonPanel.add(buttonJusinkenAdd, null);
	    buttonPanel.add(buttonDeleteKojin, null);
	    buttonPanel.add(buttonKekkaInputCall, null);
	    buttonPanel.add(buttonKekkaCopy, null);
	    buttonPanel.add(buttonDeleteKekka, null);
	    buttonPanel.add(buttonNyuryokuPrint, null);
	    buttonPanel.add(buttonIraiPrint, null);

//	    checkBoxSelect.addActionListener(new ListFrame_closeButton_actionAdapter(this));
	    buttonCheck.addActionListener(new ListFrame_button_actionAdapter(this));
	    buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonKekkaInputCall.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonJusinkenCall.addActionListener(new ListFrame_button_actionAdapter(this));

		buttonJusinkenAdd.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonDeleteKekka.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonDeleteKojin.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonKekkaCopy.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonNyuryokuPrint.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonIraiPrint.addActionListener(new ListFrame_button_actionAdapter(this));

		// add s.inoue 2012/11/21
		navigatorBar.addAfterActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if (JApplication.selectedHistoryRows == null)return;
	    		for (int i = 0; i < JApplication.selectedHistoryRows.size(); i++) {
	    			grid.getVOListTableModel().setValueAt("N", JApplication.selectedHistoryRows.get(i), 0);
	    			// eidt s.inoue 2013/11/07
	    			// JApplication.selectedHistoryRows.remove(i);
	    		}
	    		JApplication.selectedHistoryRows.removeAll(JApplication.selectedHistoryRows);
	          }
	    });

		Box origineBox = new Box(BoxLayout.X_AXIS);
		origineBox.add(buttonOriginePanel);
		origineBox.add(Box.createVerticalStrut(2));

		// box2�s��
		Box recordBox = new Box(BoxLayout.X_AXIS);
		recordBox.add(buttonPanel);
		recordBox.add(Box.createVerticalStrut(2));

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(origineBox);
		buttonBox.add(new JSeparator());
		buttonBox.add(recordBox);

		// openswing s.inoue 2011/01/25
//		buttonsPanel.add(closeButton,null);

		/* list */
		// eidt s.inoue 2012/10/11
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
		// grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.EQUALS);

//		grid.setInsertButton(insertButton);
//		grid.setEditButton(editButton);
//		grid.setDeleteButton(deleteButton);
		grid.setFilterButton(filterButton);

		grid.setReloadButton(reloadButton);
//		grid.setExportButton(exportButton);
		grid.setNavBar(navigatorBar);

		/* list */
//		grid.setInsertButton(insertButton);
		grid.setMaxSortedColumns(5);
		grid.setNavBar(navigatorBar);
//		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName(CONST_VALUE);
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(checkColumn_checkBox, null);
		grid.getColumnContainer().add(textColumn_nendo, null);
		grid.getColumnContainer().add(textColumn_jyushinSeiriNo, null);
		grid.getColumnContainer().add(textColumn_kanaName, null);
		grid.getColumnContainer().add(textColumn_Name, null);
		grid.getColumnContainer().add(textColumn_birthdayFrom, null);
		grid.getColumnContainer().add(textColumn_birthdayTo, null);

		grid.getColumnContainer().add(textColumn_sex, null);
		grid.getColumnContainer().add(textColumn_inputFlg, null);
		grid.getColumnContainer().add(textColumn_HokensyoCode, null);
		grid.getColumnContainer().add(textColumn_HokensyoNumber, null);
		grid.getColumnContainer().add(dateColumn_KenshinDate, null);
		// eidt s.inoue 2012/10/23
		grid.getColumnContainer().add(dateColumn_KenshinDateTo, null);

		grid.getColumnContainer().add(textColumn_hanteiNengapi, null);
		grid.getColumnContainer().add(textColumn_tutiNengapi, null);
		grid.getColumnContainer().add(textColumn_hokenjaNo, null);
		grid.getColumnContainer().add(textColumn_shiharaiDaikouNo, null);

//		// openswing s.inoue 2011/01/26
//		grid.addMouseListener(new JSingleDoubleClickEvent(this, button,modelfixed));

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.kekkalist.frame.title","tokutei.kekkalist.frame.guidance");
		jLabel_Title.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		// openswing s.inoue 2011/01/25
		// 2�i�\���̃{�^���\��
		// this.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);

		// this.setTitle(ClientSettings.getInstance().getResources().getResource("UKETUKE_ID"));
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

	/* �{�^���A�N�V�����p�����N���X */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener {
	  JKenshinKekkaSearchListFrame adaptee;

		  ListFrame_button_actionAdapter(JKenshinKekkaSearchListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  // button�A�N�V����
		  public void actionPerformed(ActionEvent e) {
		   	Object source = e.getSource();

		   	// del s.inoue 2011/05/09
		   	// selectedData = new ArrayList<Integer>();
		   	// eidt s.inoue 2011/04/21
		   	if (source == buttonCheck){
				logger.info(buttonCheck.getText());
				setCheckBoxValue();
		   	}else if (source == buttonClose){
				logger.info(buttonClose.getText());
				// add s.inoue 2013/10/29
				preservColumnStatus();
				adaptee.closeButtton_actionPerformed(e);
			/* ���ʃf�[�^���̓{�^�� */
			}else if (source == buttonKekkaInputCall){
				logger.info(buttonKekkaInputCall.getText());
				if (!getCheckBoxCount())
					showKekkaRegisterFrame(false);
			/* ��f���ďo */
			}else if (source == buttonJusinkenCall){
				logger.info(buttonJusinkenCall.getText());
				// add s.inoue 2012/11/30
				if (JApplication.selectedPreservRows.size() == 0)
					setSelectedRow(JApplication.selectedPreservRows);
				if (!getCheckBoxCount())
					pushedKojinButton(e);
			/* ��f���ǉ� */
			}else if (source == buttonJusinkenAdd) {
				logger.info(buttonJusinkenAdd.getText());
				pushedKojinAddButton(e);
			}
			/* ���f�f�[�^�폜 */
			else if (source == buttonDeleteKekka) {
				logger.info(buttonDeleteKekka.getText());
				pushedDeleteKekkaButton();
				// edit s.inoue 2012/11/21
				// grid.reloadData();
				reloadButton.doClick();
				/* ��f���폜 */
			} else if (source == buttonDeleteKojin) {
				logger.info(buttonDeleteKojin.getText());
				pushedDeleteKojinButton();
				// edit s.inoue 2012/11/21
				// grid.reloadData();
				reloadButton.doClick();
				/* ���ʃf�[�^�����{�^�� */
			} else if (source == buttonKekkaCopy) {
				logger.info(buttonKekkaCopy.getText());
				showKekkaRegisterFrame(true);
		   	/* ���͕[����{�^�� */
			} else if (source == buttonNyuryokuPrint) {
				logger.info(buttonNyuryokuPrint.getText());
				pushedPrintButton(e);
				// eidt s.inoue 2011/05/09
				chkFlg = true;
			/* �˗����{�^�� */
			} else if (source == buttonIraiPrint) {
				logger.info(buttonIraiPrint.getText());
				pushedPrintIraiButton(e);
				// eidt s.inoue 2011/05/09
				chkFlg = true;
			}

// del s.inoue 2012/11/05
//			if (source != buttonDeleteKojin) {
//				// eidt s.inoue 2011/04/28
//				// if (!chkFlg)
//				if (chkFlg)
//				selectedData = getSelectedRow();
//			}
//				reloadButton.doClick();
//
//				// ���������[�h����X���b�h
//				Thread reload = new ActionAutoReloadThread();
//				reload.start();

		  }
//		@Override
//		public void keyPressed(KeyEvent e) {
//			adaptee.closeButtton_keyPerformed(e);
//		}
//		@Override
//		public void keyReleased(KeyEvent e) {
//		}
//		@Override
//		public void keyTyped(KeyEvent e) {
//		}
	}

	// add s.inoue 2012/11/28
	// �`�F�b�N�{�b�N�X�ݒ�
	private void setSelectedRow(ArrayList<Integer> selectedRows){
		if (selectedRows == null)return;
		int ival = selectedRows.size();
		for (int i = 0; i < ival; i++) {
			grid.getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
		}
	}

	// eidt s.inoue 2011/04/21
	private void setCheckBoxValue(){
		JKenshinKekkaSearchListFrameData chk = null;
		// eidt s.inoue 2011/04/27
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (chk.getCHECKBOX_COLUMN().equals("Y")){
//				if (selectedData.size() == 0)break;
//				selectedData.remove(i);
//			}else{
//				selectedData.add(i);
//			}
//		}
		if (chkFlg){
			for (int i = grid.getVOListTableModel().getRowCount()-1; i >= 0; i--) {
				// eidt s.inoue 2012/11/05
				// chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (chk.getCHECKBOX_COLUMN().equals("Y"))
					// eidt s.inoue 2012/11/05
					// selectedData.remove(i);
					grid.getVOListTableModel().setValueAt("N", i, 0);
			}
			chkFlg = false;
		}else{
			// eidt s.inoue 2012/11/05
			for (int i = grid.getVOListTableModel().getRowCount()-1; i >= 0; i--) {
				grid.getVOListTableModel().setValueAt("N", i, 0);
			}

			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				// eidt s.inoue 2012/11/05
				// chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (!chk.getCHECKBOX_COLUMN().equals("Y"))
					// eidt s.inoue 2012/11/05
					grid.getVOListTableModel().setValueAt("Y", i, 0);
					// selectedData.add(i);
			}
			chkFlg = true;
		}
	}

	// �`�F�b�N��Ԃ��擾
	private boolean getCheckBoxCount(){
		boolean ret = false;
		JKenshinKekkaSearchListFrameData chk = null;
		int jcnt = 0;

		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y")){
				jcnt++;
			}
		}
		// eidt s.inoue 2012/11/28
		if (jcnt != 1 ){
			JErrorMessage.show("M3520", this);
			ret = true;
			// add s.inoue 2012/11/28
//			JApplication.callValidateCancelFlg = false;
		}
		return ret;
	}

	/* �{�^���Q */
	/**
	 * This method initializes jButton_Close
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private void setJButtons() {

		// �W��buttonSize
		filterButton.setPreferredSize(new Dimension(100,50));
		reloadButton.setPreferredSize(new Dimension(100,50));
//		exportButton.setPreferredSize(new Dimension(100,50));
		// eidt s.inoue 2012/11/20
		countText.setPreferredSize(new Dimension(80,50));
		countText.setEnabled(false);

// del s.inoue 2012/06/21
//		filterButton.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 10));
//		reloadButton.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 10));
//		exportButton.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 10));
//		countText.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 12));

		// �`�F�b�N�{�b�N�X
//		if (checkBoxSelect == null) {
//			checkBoxSelect= new CheckBoxControl();
//			checkBoxSelect.setText("�S��");
//			checkBoxSelect.setBorderPainted(false);
//			checkBoxSelect.setVerticalAlignment(SwingConstants.BOTTOM);
//			checkBoxSelect.setHorizontalTextPosition(JLabel.CENTER);
//			checkBoxSelect.setVerticalTextPosition(SwingConstants.BOTTOM);
//		}

		// �`�F�b�Nbutton
		if (buttonCheck == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Check);
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			buttonCheck= new ExtendedOpenGenericButton(
					"Check","","�S�`�F�b�N(ALT+C)",KeyEvent.VK_C,icon,new Dimension(30,30));
			buttonCheck.addActionListener(this);
		}

		// ����button
		if (buttonClose == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","�߂�(R)","�߂�(ALT+R)",KeyEvent.VK_R,icon);
			buttonClose.addActionListener(this);
			buttonClose.addKeyListener(this);
		}
		// ���ʍ폜
		if (buttonDeleteKekka == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_Delete);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonDeleteKekka= new ExtendedOpenGenericButton(
					"KekkaDelete","���ʍ폜(D)","���ʍ폜(ALT+D)",KeyEvent.VK_D,icon);
			buttonDeleteKekka.addActionListener(this);
		}
		// ��f���ǉ�
		if (buttonJusinkenAdd == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_AddJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonJusinkenAdd= new ExtendedOpenGenericButton(
					"JusinkenAdd","��f���ǉ�(A)","��f���ǉ�(ALT+A)",KeyEvent.VK_A,icon);
			buttonJusinkenAdd.addActionListener(this);
		}
		// ��f���폜
		if (buttonDeleteKojin == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_DeleteJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonDeleteKojin= new ExtendedOpenGenericButton(
					"JusinkenDelete","��f���폜(J)","��f���폜(ALT+J)",KeyEvent.VK_J,icon);
			buttonDeleteKojin.addActionListener(this);
		}
		// ���͕[���
		if (buttonNyuryokuPrint == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonNyuryokuPrint= new ExtendedOpenGenericButton(
					"NyuryokuhyoPrint","���͕[���(P)","���͕[���(ALT+P)",KeyEvent.VK_P,icon);
			buttonNyuryokuPrint.addActionListener(this);
		}
		// �˗������
		if (buttonIraiPrint == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print2);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonIraiPrint= new ExtendedOpenGenericButton(
					"IraiPrint","�˗������(Q)","�˗������(ALT+Q)",KeyEvent.VK_Q,icon);
			buttonIraiPrint.addActionListener(this);
		}
		// ���ʓ��͌ďo
		if (buttonKekkaInputCall == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Detail);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonKekkaInputCall= new ExtendedOpenGenericButton(
					"KekkaEdit","���ʓ���(E)","���ʓ���(ALT+E)",KeyEvent.VK_E,icon);
			buttonKekkaInputCall.addActionListener(this);
		}
		// ���ʕ���
		if (buttonKekkaCopy == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_Copy);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonKekkaCopy= new ExtendedOpenGenericButton(
					"KekkaCopy","���ʕ���(K)","���ʕ���(ALT+K)",KeyEvent.VK_K,icon);
			buttonKekkaCopy.addActionListener(this);
		}
		// ��f���ďo
		if (buttonJusinkenCall == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_InputJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonJusinkenCall= new ExtendedOpenGenericButton(
					"JusinkenCall","��f���ďo(W)","��f���ďo(ALT+W)",KeyEvent.VK_W,icon);
			// del s.inoue 2012/12/03
			// buttonJusinkenCall.addActionListener(this);
		}
	}

	private int[] selectedRows = null;
	// edit s.inoue 2011/07/19
	private TKojinDao tKojinDao;
	private ArrayList<Hashtable<String, String>> result;
	private Hashtable<String, String> resultItem;  //  @jve:decl-index=0:
	private JKenshinKekkaSearchListFrameData validatedData = new JKenshinKekkaSearchListFrameData();  //  @jve:decl-index=0:

	/**
	 * ���ʃf�[�^���̓{�^��
	 */
	public void showKekkaRegisterFrame(boolean isCopy) {


		// �I���s�擾
		// JKenshinKekkaSearchListFrameData srcData = null;

		// selectedRows = this.grid.getSelectedRows();

		// eidt s.inoue 2012/04/27
		// selectedRows = this.grid.getSelectedRows();
		// �I����Ԃ�ێ�����
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		// eidt s.inoue 2011/04/05
		// selectedRows = table.getselectedRows(modelfixed,table);
		// srcData = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows[0]);
		JKenshinKekkaSearchListFrameData srcData = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			srcData = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (srcData.getCHECKBOX_COLUMN().equals("Y")){
				selectedRows.add(i);break;
			}
		}

		if (isCopy){
			srcData.setUKETUKEPRE_ID(srcData.getUKETUKE_ID());
		}else{
			srcData.setUKETUKE_ID(srcData.getUKETUKE_ID());
		}
// del s.inoue 2011/12/07
//		String kensaJissiDate = srcData.getKENSA_NENGAPI();
//		if (kensaJissiDate == null) {
//			kensaJissiDate = new String("");
//		}

		WindowRefreshEvent win = new WindowRefreshEvent();
		// win.setselectedData(selectedRows.get(0));

		// eidt s.inoue 2012/03/29 copy�̎��֕ύX
		if (isCopy) {
			/* ���f���{������̏ꍇ */
			if (srcData.getKENSA_NENGAPI().equals("")) {

				/* �V�X�e����������͂���B */
				Calendar cal = Calendar.getInstance();

				String year = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
				String month = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
				String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

				String jissiDateString = year + month + date;
				srcData.setKENSA_NENGAPI(jissiDateString);
//			/* ���f���{�����w�肳��Ă���ꍇ */
//			} else {
//				validatedData.setKensaJissiDate(kensaJissiDate,false);
//				jTextField_Date.setText(validatedData.getKensaJissiDate());
			}
		}
//		/* ���ʃf�[�^�����̏ꍇ */
//		else {
//			validatedData.setKensaJissiDate(kensaJissiDate,false);
//		}

		// eidt s.inoue 2011/12/06
		JScene.CreateDialog(
				this,
				new JRegisterFlameCtrl(srcData, isCopy),
				win);
	}

	/**
	 * ��f�����{�^��
	 */
	public void pushedKojinButton(ActionEvent e) {

		// eidt s.inoue 2012/04/27
		// selectedRows = this.grid.getSelectedRows();
		// �I����Ԃ�ێ�����
		// eidt s.inoue 2012/12/27
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		// eidt s.inoue 2011/04/05
		// selectedRows = table.getselectedRows(modelfixed,table);
		JKenshinKekkaSearchListFrameData testVO = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			testVO = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			// eidt s.inoue 2012/12/27
			if (testVO.getCHECKBOX_COLUMN().equals("Y")){
				break;
				// selectedRows.add(i);break;
			}
		}

		// ��I������Ă���ꍇ�̂݉�ʑJ�ڂ��s��
//		if (selectedRows.length == 1) {
// del s.inoue 2012/04/27
//			JKenshinKekkaSearchListFrameData testVO = null;
//			testVO = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows[0]);
			// resultItem = result.get(selectedRows.get(0));

			// edit s.inoue 20110328
			// this.validatedData.setUketuke_id(resultItem.get("UKETUKE_ID"));
			// this.validatedData.setHihokenjyaCode(resultItem.get("HIHOKENJYASYO_KIGOU"));
			// this.validatedData.setHihokenjyaNumber(resultItem.get("HIHOKENJYASYO_NO"));
			// this.validatedData.setName(resultItem.get("KANANAME"));
			// this.validatedData.setHokenjyaNumber(resultItem.get("HKNJANUM"));
			this.validatedData.setUKETUKE_ID(testVO.getUKETUKE_ID());
			this.validatedData.setHIHOKENJYASYO_KIGOU(testVO.getHIHOKENJYASYO_KIGOU());
			this.validatedData.setHIHOKENJYASYO_NO(testVO.getHIHOKENJYASYO_NO());
			this.validatedData.setNAME(testVO.getNAME());
			this.validatedData.setHKNJANUM(testVO.getHKNJANUM());

			// edit s.inoue 20110328 ��f��
			// String kensaJissiDate = resultItem.get("KENSA_NENGAPI");
			String kensaJissiDate = testVO.getKENSA_NENGAPI();

			if (kensaJissiDate == null) {
				kensaJissiDate = new String("");
			}

			// add s.inoue 2011/08/03�@
			this.validatedData.setKENSA_NENGAPI(kensaJissiDate);

			WindowRefreshEvent win = new WindowRefreshEvent();
			// eidt s.inoue 2012/12/27
//			win.setselectedData(selectedRows.get(0));

			JScene.CreateDialog(this, new JKojinRegisterFrameCtrl(
					this.validatedData), win);
//		} else {
//			JErrorMessage.show("M3520", this);
//		}
	}


	public void pushedPrintButton(ActionEvent e) {

		// �J�����_�͒��~�ŕ��ʂ̃_�C�A���O��\��
		// calendarDialog();
		try {
			// eidt s.inoue 2011/03/29
			// int rowCount = table.getRowCount();
			int rowCount = grid.getVOListTableModel().getRowCount();

			ArrayList<Integer> selectedRows = new ArrayList<Integer>();
			ArrayList<Hashtable<String, String>> selectResult = new ArrayList<Hashtable<String,String>>();

			// �`�F�b�N�{�b�N�X����
//			for (int i = 0; i < rowCount; i++) {
//				// ���݃`�F�b�N����Ă���s�̃��X�g�𒊏o
//				if ((Boolean) table.getData(i, 0) == Boolean.TRUE) {
//					selectedRows.add(i);
//					selectResult.add(result.get(i));
//				}
//			}
//			JKenshinKekkaSearchListFrameData chk = null;
//			int jcnt = 0;
//			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//				chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//				if (chk.getCHECKBOX_COLUMN().equals("Y"))
//					selectResult.add(chk);
//			}

			dateSelectDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
			// eidt s.inoue 2011/12/16
//			dateSelectDialog.setShowCancelButton(true);

			// ���f���{�����̓_�C�A���O��\��
			dateSelectDialog.setMessageTitle("���f���{�����͉��");
			dateSelectDialog.setVisible(true);

			// eidt s.inoue 2011/12/16
			if (dateSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;
			String kenshinDate = dateSelectDialog.getKenshinDate();
			// �������
			printInputData(kenshinDate);
			// del s.inoue 2009/10/16
			// ���t���b�V�� �X�V���K�v�Ȃ��̂ŁA�K�v�Ȃ�
			// pushedSearchButton(true);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}

	/**
	 * ����@�\
	 * �y�[�W�P�� �����˗���
	 */
	public void pushedPrintIraiButton(ActionEvent e) {

		// �I���s�𒊏o����B
		// eidt s.inoue 2011/03/29
		// int rowCount = table.getRowCount();
		int rowCount = grid.getVOListTableModel().getRowCount();

		// ����p
		TreeMap<String, Object> printDataIrai = new TreeMap<String, Object>();
		// �X�e�[�^�X���
		ProgressWindow progressWindow = new ProgressWindow(this, false,true);

		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		// edit s.inoue 2009/11/02
		ArrayList<String> kyeList =  new ArrayList<String>();

		// �I���`�F�b�N
		// eidt s.inoue 2011/03/29
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				// ���ԍ��u��v,���ʓ��́u���v�̏ꍇ�G���[
//				if (table.getData(i, 7).equals("��")){
//					JErrorMessage.show("M3547", this);return;
//				}
//					selectedRows.add(i);
//			}
//		}
		JKenshinKekkaSearchListFrameData vo = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (vo.getCHECKBOX_COLUMN().equals("Y")){
				if (vo.getKEKA_INPUT_FLG().equals("��")){
					JErrorMessage.show("M3547", this);
					return;
				}
				selectedRows.add(i);
			}
		}

		// �I���s�Ȃ�
		int selectedCount = selectedRows.size();
		if (selectedCount == 0) {
			JErrorMessage.show("M3548", this);
			return;
		}

		// �˗����f�[�^�쐬
		for (int j = 0; j < selectedCount; j++) {

			int k = selectedRows.get(j);
			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(k);

			progressWindow.setGuidanceByKey("tokutei.iraisho.frame.progress.guidance");
			progressWindow.setVisible(true);

			// eidt s.inoue 2011/03/29
			// validatedData.setUketuke_id(result.get(k).get("UKETUKE_ID"));
			validatedData.setUKETUKE_ID(vo.getUKETUKE_ID());

			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();

			// eidt s.inoue 2011/03/29
			// ��tID
			// String uketukeId = validatedData.getUketuke_id();
			String uketukeId = validatedData.getUKETUKE_ID();

			System.out.println(j + "�s��" + uketukeId);

			tmpKojin.put("UKETUKE_ID", uketukeId);
			// ���f�N����
			// eidt s.inoue 2011/03/29
			// String kensaNengapi = result.get(k).get("KENSA_NENGAPI");
			String kensaNengapi = vo.getKENSA_NENGAPI();
			tmpKojin.put("KENSA_NENGAPI",kensaNengapi );

			Iraisho IraiData = new Iraisho();

			if (!IraiData.setPrintKojinIraiDataSQL(tmpKojin)) {
				// �f�[�^�ڑ����s
				// edit s.inoue 2009/10/16
				progressWindow.setVisible(false);
				JErrorMessage.show("M3530", this);
				return;
			}

			// ���obj key:�����N����+��tID value:����f�[�^
			printDataIrai.put(String.valueOf(kensaNengapi + uketukeId), IraiData);
			kyeList.add(String.valueOf(kensaNengapi + uketukeId));
			System.out.println(String.valueOf(kensaNengapi + uketukeId));
		}

		/*
		 * Kikan�쐬
		 */
		Kikan kikanData = new Kikan();
		if (!kikanData.setPrintKikanDataSQL()) {
			// �f�[�^�ڑ����s
			JErrorMessage.show("M4941", this);
		}
		Hashtable<String, Object> printData = new Hashtable<String, Object>();
		printData.put("Kikan", kikanData);

		// ������s
		PrintIraisho kensa = new PrintIraisho();
		kensa.setQueryIraiResult(printDataIrai);
		kensa.setQueryResult(printData);
		kensa.setKeys(kyeList);

		progressWindow.setVisible(false);

		try {
			kensa.beginPrint();
		} catch (PrinterException err) {
			err.printStackTrace();
			JErrorMessage.show("M3531", this);
		} finally {
			progressWindow.setVisible(false);
		}

	}

	/**
	 * ����@�\
	 *
	 * 1�y�[�W ���f���ړ��̓V�[�g�i�������ʁj �K�{�f�[�^�F�ی��Ҕԍ��A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ��A�����N���� import
	 * Print.KenshinKoumoku_Kensa class KenshinKoumoku_Kensa
	 *
	 * 2�y�[�W ���f���ړ��̓V�[�g�i��f�j �K�{�f�[�^�F�ی��Ҕԍ��A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ��A���f�N���� import
	 * Print.KenshinKoumoku_Monshin class KenshinKoumoku_Monshin
	 */
	private void printInputData(String kenshinDate){
		/*
		 * �I���s�𒊏o����B
		 */
//		int rowCount = table.getRowCount();
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) table.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
		// �`�F�b�N�{�b�N�X����
		JKenshinKekkaSearchListFrameData selectDt = null;
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		int jcnt = 0;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (selectDt.getCHECKBOX_COLUMN().equals("Y"))
				selectedRows.add(i);
		}

		int selectedCount = selectedRows.size();
		// int selectedCount = grid.getVOListTableModel().getRowCount();

		if (selectedCount == 0) {

			ProgressWindow progressWindow = new ProgressWindow(this, false, true);
			progressWindow.setGuidanceByKey("tokutei.showresult.frame.progress.guidance.print1");
			progressWindow.setVisible(true);

			Kojin KojinData = new Kojin();
			Hashtable<String, Object> printData = new Hashtable<String, Object>();
			printData.put("Kojin", KojinData);

			PrintNyuryoku kensa = new PrintNyuryoku();

			kensa.setQueryResult(printData);
			// edit s.inoue 2009/10/16
			kensa.setKenshinDate(kenshinDate);

			progressWindow.setVisible(false);

			try {
				kensa.beginPrint();

			} catch (PrinterException err) {
				err.printStackTrace();
				JErrorMessage
						.show("M3531", this);
			} finally {
				progressWindow.setVisible(false);
			}

			return;
		}

		for (int j = 0; j < selectedCount; j++) {
			int k = selectedRows.get(j);

			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(k);

			/*
			 * �l���f�[�^�쐬
			 */
			ProgressWindow progressWindow = new ProgressWindow(this, false,true);
			progressWindow.setGuidanceByKey("tokutei.showresult.frame.progress.guidance.print1");
			progressWindow.setVisible(true);

			try {
			// eidt s.inoue 2011/03/29
			// validatedData.setUketuke_id(result.get(k).get("UKETUKE_ID"));
			// validatedData.setHihokenjyaCode(result.get(k).get("HIHOKENJYASYO_KIGOU"));
			// validatedData.setHihokenjyaNumber(result.get(k).get("HIHOKENJYASYO_NO"));
			validatedData.setUKETUKE_ID(selectDt.getUKETUKE_ID());
			validatedData.setHIHOKENJYASYO_KIGOU(selectDt.getHIHOKENJYASYO_KIGOU());
			validatedData.setHIHOKENJYASYO_NO(selectDt.getHIHOKENJYASYO_NO());

			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();
			// tmpKojin.put("UKETUKE_ID", validatedData.getUketuke_id());
			tmpKojin.put("UKETUKE_ID",selectDt.getUKETUKE_ID());
			// ��ی��ҏؓ��L��
			// tmpKojin.put("HIHOKENJYASYO_KIGOU", validatedData.getHihokenjyaCode());
			tmpKojin.put("HIHOKENJYASYO_KIGOU", selectDt.getHIHOKENJYASYO_KIGOU());
			// ��ی��ҏؓ��ԍ�
			// tmpKojin.put("HIHOKENJYASYO_NO", validatedData.getHihokenjyaNumber());
			tmpKojin.put("HIHOKENJYASYO_NO", selectDt.getHIHOKENJYASYO_NO());

			// ���f�N����
			// edit ver2 s.inoue 2009/05/29
			//tmpKojin.put("KENSA_NENGAPI", (String) model.getData(k, 5));
			// edit ver2 s.inoue 2009/07/14
			// tmpKojin.put("KENSA_NENGAPI", kensaNengapi);
			if (!kenshinDate.equals("")) {
				tmpKojin.put("KENSA_NENGAPI", kenshinDate);
			}else{
				// tmpKojin.put("KENSA_NENGAPI", result.get(k).get("KENSA_NENGAPI"));
				tmpKojin.put("KENSA_NENGAPI", selectDt.getKENSA_NENGAPI());
			}

			Kojin KojinData = new Kojin();
			if (!KojinData.setPrintKojinDataSQL(tmpKojin)) {
				// �f�[�^�ڑ����s
				JErrorMessage.show("M3530", this);
			}

			// add s.inoue 2009/10/15
			// ���ʓo�^�ς݂Ŗ����ꍇ�I���@���o�^�ł��o��
//			if (KojinData.getPrintKojinData().size() < 1){
//				progressWindow.setVisible(false);
//				JErrorMessage.show("M3549", this);
//				return;
//			}

			// add s.inoue 2009/10/15�@����
//			String kekaInputFlg = KojinData.getPrintKojinData().get("KEKA_INPUT_FLG");
//			if (!kekaInputFlg.equals("2")){
//				return;
//			}

			/*
			 * ����f�[�^���� �l�f�[�^���i�[����
			 */
			Hashtable<String, Object> printData = new Hashtable<String, Object>();
			printData.put("Kojin", KojinData);

			/*
			 * ��� 1�y�[�W�ڂ��������ƁA�����I�ɍŏI�y�[�W�܂ŏo�͂����
			 */
			PrintNyuryoku kensa = new PrintNyuryoku();
			kensa.setQueryResult(printData);
			// edit s.inoue 2009/10/16
			// kensa.setKenshinDate(kenshinDate);
			progressWindow.setVisible(false);


				kensa.beginPrint();
			} catch (PrinterException err) {
				err.printStackTrace();
				JErrorMessage.show("M3531", this);
			} finally {
				progressWindow.setVisible(false);
			}
		}
	}

	/**
	 * ��f�����ǉ��{�^��
	 */
	public void pushedKojinAddButton(ActionEvent e) {
		JScene.CreateDialog(this, new JKojinRegisterFrameCtrl(),
				new WindowRefreshEvent());
	}

	/**
	 * ���f�f�[�^�폜�{�^��
	 */
	public void pushedDeleteKekkaButton() {
		this.deleteKekka();
	}

	/**
	 * ��f���f�[�^�폜�{�^��
	 */
	public void pushedDeleteKojinButton() {
		this.deleteKojin();
	}

	/**
	 * ���f���ʂ��폜����B
	 */
	private void deleteKekka() {
		/*
		 * �I���s�𒊏o����B
		 */
		// int rowCount = modelfixed.getRowCount();
		int rowCount = grid.getVOListTableModel().getRowCount();

		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		JKenshinKekkaSearchListFrameData chk = null;
		int jcnt = 0;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y"))
				selectedRows.add(i);
		}
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}


		/* ���ʃf�[�^���폜������ */
		boolean deletedData = false;

		/* �I�����ꂽ�s�� */
		int selectedCount = selectedRows.size();

		JKenshinKekkaSearchListFrameData selectDt = null;

		for (int j = 0; j < selectedCount; j++) {
			/* �I�����ꂽ�s�̍s�C���f�b�N�X */
			int selectedRowIndex = selectedRows.get(j);


			// eidt s.inoue 2011/03/29
			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRowIndex);
			// Hashtable<String, String> resultItem = result.get(selectedRowIndex);

			String uketukeId = selectDt.getUKETUKE_ID();
			String hihokenjyasyoKigou = selectDt.getHIHOKENJYASYO_KIGOU();
			String hihokenjasyoNo = selectDt.getHIHOKENJYASYO_NO();
			String namekana = selectDt.getKANANAME();
			String kesaNagapi = selectDt.getKENSA_NENGAPI();
			// String uketukeId = resultItem.get("UKETUKE_ID");
			// String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
			// String hihokenjasyoNo = resultItem.get("HIHOKENJYASYO_NO");
			// String namekana = resultItem.get("KANANAME");
			// String kesaNagapi = resultItem.get("KENSA_NENGAPI");
			// String hokenjyaNumber = resultItem.get("HKNJANUM");
			// String sex = resultItem.get("SEX");
			// String birthday = resultItem.get("BIRTHDAY");

			String[] params = { uketukeId, kesaNagapi };
			// String[] messageParams = { namekana, kesaNagapi };

			String displayKigou = null;
			if (hihokenjyasyoKigou == null || hihokenjyasyoKigou.isEmpty()) {
				displayKigou = "(����)";
			}
			else {
				displayKigou = hihokenjyasyoKigou;
			}

			String displayNumber = null;
			if (hihokenjasyoNo == null || hihokenjasyoNo.isEmpty()) {
				displayNumber = "(����)";
			}
			else {
				displayNumber = hihokenjasyoNo;
			}

			String[] messageParams = { namekana, displayKigou, displayNumber, kesaNagapi };


			/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�ɂ͌��f�f�[�^������܂��� */
			if (kesaNagapi == null || kesaNagapi.isEmpty()) {
				JErrorMessage.show("M3537", this, messageParams);
				continue;
			}

			/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�i%s�j�̌��f�f�[�^���폜���Ă���낵���ł����H */
			RETURN_VALUE retValue = JErrorMessage.show("M3542", this, messageParams);
			if (retValue != RETURN_VALUE.YES) {
				continue;
			}

			int count = 0;
			boolean successDeleteKekka = false;
			try {
				JApplication.kikanDatabase.Transaction();

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_SONOTA, params);

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_TOKUTEI, params);

				if (count == 1) {
					successDeleteKekka = true;

					if (! deletedData) {
						deletedData = true;
					}

					/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�i%s�j�̌��f�f�[�^���폜���܂����B */
					JErrorMessage.show("M3535", this, messageParams);
				}

				JApplication.kikanDatabase.Commit();

			} catch (SQLException e) {
				e.printStackTrace();
				try {
					JApplication.kikanDatabase.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			finally {
				if (! successDeleteKekka) {
					/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�i%s�j�̌��f�f�[�^�̍폜�Ɏ��s���܂����B */
					JErrorMessage.show("M3536", this, messageParams);
					break;
				}
			}
		}

		/* 1 ���ȏ�̌��ʂ܂��͎�f�������폜���Ă����ꍇ */
		if (deletedData) {
			/* �e�[�u���̍ēǂݍ��݂��s�Ȃ� */
//			model.clearAllRow();
			// del s.inoue 2011/03/29
//			pushedSearchButton(false);
		}
	}

	/**
	 * ��f�������폜����B
	 */
	private void deleteKojin() {
		/*
		 * �I���s�𒊏o����B
		 */

		// eidt s.inoue 2011/03/29

		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		JKenshinKekkaSearchListFrameData chk = null;
		int jcnt = 0;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y"))
				selectedRows.add(i);
		}
//		int rowCount = modelfixed.getRowCount();
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++) {
//
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}

		/* �I�����ꂽ�s�� */
		int selectedCount = selectedRows.size();
		JKenshinKekkaSearchListFrameData selectDt = null;

		/* �����ς݂̎�f�����̎�tID */
		ArrayList<String> chekedKojinIds = new ArrayList<String>();

		/* �f�[�^���폜������ */
		boolean deletedData = false;

		for (int j = 0; j < selectedCount; j++) {
			/* �I�����ꂽ�s�̍s�C���f�b�N�X */
			int selectedRowIndex = selectedRows.get(j);

			// eidt s.inoue 2011/03/29
			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRowIndex);
			// Hashtable<String, String> resultItem = result.get(selectedRowIndex);

			String uketukeId = selectDt.getUKETUKE_ID();
			String hihokenjyasyoKigou = selectDt.getHIHOKENJYASYO_KIGOU();
			String hihokenjasyoNo = selectDt.getHIHOKENJYASYO_NO();
			String namekana = selectDt.getKANANAME();
			String kesaNagapi = selectDt.getKENSA_NENGAPI();
			// String uketukeId = resultItem.get("UKETUKE_ID");
			// String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
			// String hihokenjasyoNo = resultItem.get("HIHOKENJYASYO_NO");
			// String namekana = resultItem.get("KANANAME");
			// String kesaNagapi = resultItem.get("KENSA_NENGAPI");
			// String hokenjyaNumber = resultItem.get("HKNJANUM");
			// String sex = resultItem.get("SEX");
			// String birthday = resultItem.get("BIRTHDAY");

			String[] paramsDeleteKojin = { uketukeId };
			String[] paramsDeleteKekka = { uketukeId, kesaNagapi };

			String displayKigou = null;
			if (hihokenjyasyoKigou == null || hihokenjyasyoKigou.isEmpty()) {
				displayKigou = "(����)";
			}
			else {
				displayKigou = hihokenjyasyoKigou;
			}

			String displayNumber = null;
			if (hihokenjasyoNo == null || hihokenjasyoNo.isEmpty()) {
				displayNumber = "(����)";
			}
			else {
				displayNumber = hihokenjasyoNo;
			}

			String[] messageParams = { namekana, displayKigou, displayNumber, "" };

			/* �����ς݂̎�tID�̏ꍇ�́A�������Ȃ� */
			if (chekedKojinIds.contains(uketukeId)) {
				continue;
			}

			/* ��f�҂Ɍ��f�f�[�^�����邩���ׂ�B */
			ArrayList<Hashtable<String, String>> selectTokuteiResult =
				new ArrayList<Hashtable<String, String>>();
			try {
				selectTokuteiResult = JApplication.kikanDatabase.sendExecuteQuery(
						SQL_SELECT_TOKUTEI, paramsDeleteKojin);

			} catch (SQLException e) {
				e.printStackTrace();

				/* �����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�̎�f�f�[�^�̗L���̎擾�Ɏ��s���܂����B */
				JErrorMessage.show("M3538", this, messageParams);

				return;
			}

			Hashtable<String, String> item = selectTokuteiResult.get(0);
			String kekkaCount = item.get("NUM");

			boolean needDeleteKekkaData = false;
			try {
				if (kekkaCount.equals("0")) {
					/* ���ʃf�[�^�����݂��Ȃ��ꍇ */

					/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j��f���f�[�^���폜���Ă�낵���ł����H */
					RETURN_VALUE retValue = JErrorMessage.show("M3534", this, messageParams);
					if (retValue != RETURN_VALUE.YES) {
						continue;
					}
				}
				else {
					/* ���ʃf�[�^�����݂���ꍇ */

					/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�ɂ́A
					 * ���f���ʃf�[�^��[%s]������܂��B��f���f�[�^���폜���Ă�낵���ł����H */
					messageParams[3] = kekkaCount;

					RETURN_VALUE retValue = JErrorMessage.show("M3539", this, messageParams);
					if (retValue == RETURN_VALUE.YES) {
						needDeleteKekkaData = true;
					}
					else {
						continue;
					}
				}
			} finally {
				/* �����ς݂Ƃ��ēo�^����B */
				chekedKojinIds.add(uketukeId);
			}

			/*
			 * ���ʃf�[�^���폜����B
			 */
			if (needDeleteKekkaData) {
				int count = 0;
				boolean successDeleteKekka = false;
				try {
					JApplication.kikanDatabase.Transaction();

					count = JApplication.kikanDatabase.sendNoResultQuery(
							SQL_DELETE_SONOTA, paramsDeleteKekka);

					count = JApplication.kikanDatabase.sendNoResultQuery(
							SQL_DELETE_TOKUTEI, paramsDeleteKekka);

					if (count == 1) {
						successDeleteKekka = true;

						if (! deletedData) {
							deletedData = true;
						}
					}

					JApplication.kikanDatabase.Commit();

				} catch (SQLException e) {
					logger.error(e.getMessage());
					try {
						JApplication.kikanDatabase.rollback();
					} catch (SQLException ex) {
						logger.error(ex.getMessage());
					}
				}
				finally {
					if (! successDeleteKekka) {
						/* �m�F,�����i�J�i�j[%s]����i%s�j�̌��f�f�[�^�̍폜�Ɏ��s���܂����B */
						JErrorMessage.show("M3536", this, messageParams);
						return;
					}
				}
			}

			/*
			 * ��f���f�[�^���폜����B
			 */
			boolean successDeleteData = false;
			int count = 0;

			try {

				// add ver2 s.inoue 2009/06/02
				JApplication.kikanDatabase.Transaction();

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_NAYOSE, paramsDeleteKojin);

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_KOJIN, paramsDeleteKojin);

				if (count == 1) {
					successDeleteData = true;
					if (! deletedData) {
						deletedData = true;
					}

					/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�̎�f���f�[�^���폜���܂����B */
					JErrorMessage.show("M3541", this, messageParams);
				}

				// add ver2 s.inoue 2009/06/02
				JApplication.kikanDatabase.Commit();

			} catch (SQLException e) {
				logger.error(e.getMessage());
				try {
					JApplication.kikanDatabase.rollback();
				} catch (SQLException ex) {
					logger.error(ex.getMessage());
				}
			}
			finally {
				if (! successDeleteData) {
					/* �m�F,�����i�J�i�j[%s]����i��ی��ҏؓ��L��[%s]�ԍ�[%s]�j�̎�f���f�[�^�̍폜�Ɏ��s���܂����B */
					JErrorMessage.show("M3540", this, messageParams);
					return;
				}
			}
		}

		/* 1 ���ȏ�̌��ʂ܂��͎�f�������폜���Ă����ꍇ */
		if (deletedData) {
			/* �e�[�u���̍ēǂݍ��݂��s�Ȃ� */
//			model.clearAllRow();
			// del s.inoue 2011/03/29
//			pushedSearchButton(false);
		}
	}

	/**
	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
	 */

	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/05/09
			// selectedData = getselectedData();
			// selectedData.add(0, getselectedData());

			// ArrayList<Integer> selectedHistoryRows = new ArrayList<Integer>();
			// eidt s.inoue 2012/11/28
			JApplication.selectedHistoryRows = new ArrayList<Integer>();
			JKenshinKekkaSearchListFrameData chk = null;
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if (chk.getCHECKBOX_COLUMN().equals("Y"))
					JApplication.selectedHistoryRows.add(i);
			}

			// eidt s.inoue 2011/05/10
			// ViewSettings.getGridPageSize()�����
		   	int rowCount = ((currentPage -1)*JApplication.gridViewSearchCount) + currentRow + 1;
			currentTotalRows = rowCount;

//			String cntTxt = countText.getText();
//			countText.setText(rowCount + "����");
//			reloadButton.doClick();
			countText.setText(currentTotalRows + "����");

			// eidt s.inoue 2012/11/21
			reloadButton.doClick();
			// grid.reloadData();

// del s.inoue 2012/11/05
//			Thread reload = new ActionAutoReloadThread();
//			reload.start();

// del s.inoue 2012/11/07
//			setSelectedRow(selectedRows);
		}

		// eidt s.inoue 2012/12/27
//		private int sel = 0;
//		public void setselectedData(Integer selIdx){
//			sel = selIdx;
//		}
//		public int getselectedData(){
//			return sel;
//		}
	}
	
	// add s.inoue 2013/10/29
	private void preservColumnStatus(){
		// JApplication.flag = EnumSet.noneOf(FlagEnum.class);
		if (textColumn_jyushinSeiriNo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
		}
		
		//add tanaka 2013/11/06
		if (textColumn_HokensyoCode.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_KIGOU))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));
		}
		
		if (textColumn_HokensyoNumber.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_NO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_NO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_NO));
		}
		
		if (dateColumn_KenshinDateTo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.KENSA_NENGAPI));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.KENSA_NENGAPI))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KENSA_NENGAPI));
		}
		
		if (textColumn_sex.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.SEX));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.SEX))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SEX));
		}
		
		if (textColumn_birthdayTo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.BIRTHDAY));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.BIRTHDAY))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.BIRTHDAY));
		}
		
		if (textColumn_inputFlg.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.KEKA_INPUT_FLG));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.KEKA_INPUT_FLG))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KEKA_INPUT_FLG));
		}
		
		if (textColumn_hokenjaNo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HKNJANUM));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HKNJANUM))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HKNJANUM));
		}
		
		if (textColumn_shiharaiDaikouNo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));
		}
		
		if (textColumn_Name.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.NAME));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.NAME))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NAME));
		}

		if (textColumn_kanaName.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.KANANAME));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.KANANAME))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KANANAME));
		}

		if (textColumn_hanteiNengapi.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HANTEI_NENGAPI));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HANTEI_NENGAPI))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HANTEI_NENGAPI));
		}
		
		if (textColumn_tutiNengapi.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.TUTI_NENGAPI));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.TUTI_NENGAPI))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.TUTI_NENGAPI));
		}
		
		if (textColumn_nendo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.NENDO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.NENDO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NENDO));
		}
		
		if (checkColumn_checkBox.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.CHECKBOX_COLUMN));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.CHECKBOX_COLUMN))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.CHECKBOX_COLUMN));
		}
	}

	/* �{�^���A�N�V���� */
	public void closeButtton_actionPerformed(ActionEvent e) {
		dispose();

		// add s.inoue 2013/04/05
		if (JApplication.selectedHistoryRows.size() == 0)return;
		// eidt s.inoue 2013/11/07
//		for (int i=0;i < grid.getVOListTableModel().getRowCount(); i++) {
//			JApplication.selectedHistoryRows.remove(i);
//		}
		JApplication.selectedHistoryRows.removeAll(JApplication.selectedHistoryRows);
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

// del s.inoue 2012/11/05
//	// �`�F�b�N�{�b�N�X�ێ�
//	private ArrayList<Integer> getSelectedRow(){
//		JKenshinKekkaSearchListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y"))
//				selectedData.add(i);
//		}
//		return selectedData;
//	}
//
//	// �`�F�b�N�{�b�N�X�ݒ�
//	private void setSelectedRow(ArrayList<Integer> selectedRows){
//		// JKenshinKekkaSearchListFrameData vo = null;
//		for (int i = 0; i < selectedRows.size(); i++) {
//			// eidt s.inoue 2012/11/02
//			// vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(i));
//			// eidt s.inoue 2012/11/07
//			grid.getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
//			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
//		}
//	}
//
//	// add s.inoue 2011/04/11
//	private class ActionAutoReloadThread extends Thread {
//	    public ActionAutoReloadThread () {
//	    }
//	    public void run() {
//            try {
//                Thread.sleep(100);
//                setSelectedRow(selectedData);
//            } catch (InterruptedException e) {
//                 // Interrupted���ꂽ�ꍇ�́A���ɉ��������ɏI������
//            }
//	    }
//	}
}
