package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;

import org.apache.log4j.helpers.DateTimeDateFormat;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.IDialog;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKekkaListFrameCtrl.WindowRefreshEvent;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei.JIppanHantei;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei.JIppanHanteiStartData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei.JKaisoukaHantei;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei.JKaisoukaHanteiData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei.JMTHantei;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei.JMTHanteiData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintKekka;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;

import com.lowagie.tools.Executable;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import org.apache.log4j.Logger;

/**
 * ���f���ʕ\���E��������@������ʐ���
 *
 * Modified 2008/03/29 �������ڃR�[�h��萔�ɒu������
 * Modified 2008/03/30 �s�v�ȃR�����g���폜
 */
public class JSearchResultListFrameCtrl extends JSearchResultListFrame
{
	private static org.apache.log4j.Logger logger = Logger.getLogger(JSearchResultListFrameCtrl.class);

	private static final String CODE_BMI = "9N011000000000001";
	private static final int COLUMN_INDEX_CHECK = 0;
	private static final int COLUMN_INDEX_NENDO = 1;
	private static final int COLUMN_INDEX_JYUSHIN_SEIRI_NO = 2;
	private static final int COLUMN_INDEX_KANANAME = 3;
	private static final int COLUMN_INDEX_NAME = 4;
	private static final int COLUMN_INDEX_BIRTHDAY = 5;
	private static final int COLUMN_INDEX_SEX = 6;
	private static final int COLUMN_INDEX_INPUT_FLAG = 7;
	private static final int COLUMN_INDEX_METBO_HANTEI = 8;
	private static final int COLUMN_INDEX_HOKENSHIDOU_LEVEL = 9;
	private static final int COLUMN_INDEX_KENSA_NENGAPI = 10;
	private static final int COLUMN_INDEX_HANTEI_NENGAPI = 11;
	private static final int COLUMN_INDEX_TUTI_NENGAPI = 12;
	private static final int COLUMN_INDEX_HIHOKENSHA_KIGO = 13;
	private static final int COLUMN_INDEX_HIHOKENSHA_NO = 14;
	private static final int COLUMN_INDEX_HKNJANUM = 15;
	private static final int COLUMN_INDEX_DAIKOU = 16;
	private static final int COLUMN_INDEX_KOMENTO = 17;
	private static final int COLUMN_INDEX_UKETUKE_ID = 18;

	private static final String CODE_METABO_HANTEI = "9N501000000000011";  //  @jve:decl-index=0:
	private static final String CODE_HOKENSHIDOU_LEVEL = "9N506000000000011";
	private static final String CODE_TAIJU = "9N006000000000001";
	private static final String CODE_SHINCHOU = "9N001000000000001";
	private static final String CODE_HUKUYAKU_3 = "9N711000000000011";
	private static final String CODE_HUKUYAKU_2 = "9N706000000000011";
	private static final String CODE_HUKUYAKU_1 = "9N701000000000011";
	private static final String CODE_HBA1C_4 = "3D045000001999902";
	private static final String CODE_HBA1C_3 = "3D045000001927102";
	private static final String CODE_HBA1C_2 = "3D045000001920402";
	private static final String CODE_HBA1C_1 = "3D045000001906202";
	private static final String CODE_SAIKETSU_ZIKAN = "9N141000000000011";
	private static final String CODE_SHUSHUKUKI_KETSUATSU_1 = "9A751000000000001";
	private static final String CODE_SHUSHUKUKI_KETSUATSU_2 = "9A752000000000001";
	private static final String CODE_SHUSHUKUKI_KETSUATSU_3 = "9A755000000000001";
	private static final String CODE_KAKUCHOKI_KETSUATSU_1 = "9A761000000000001";
	private static final String CODE_KAKUCHOKI_KETSUATSU_2 = "9A762000000000001";
	private static final String CODE_KAKUCHOKI_KETSUATSU_3 = "9A765000000000001";  //  @jve:decl-index=0:
	private static final String CODE_HDL_3 = "3F070000002399901";
	private static final String CODE_HDL_2 = "3F070000002327201";
	private static final String CODE_HDL_1 = "3F070000002327101";
	private static final String CODE_NAIZO_SHIBOU_MENSEKI = "9N021000000000001";
	private static final String CODE_KUHUKUJI_KETTO_4 = "3D010000001999901";
	private static final String CODE_KUHUKUJI_KETTO_3 = "3D010000001927201";
	private static final String CODE_KUHUKUJI_KETTO_2 = "3D010000002227101";
	private static final String CODE_KUHUKUJI_KETTO_1 = "3D010000001926101";
	private static final String CODE_CYUSEISHIBOU_3 = "3F015000002399901";
	private static final String CODE_CYUSEISHIBOU_2 = "3F015000002327201";
	private static final String CODE_CYUSEISHIBOU_1 = "3F015000002327101";
	private static final String CODE_HUKUI_ZIKOSHINKOKU = "9N016160300000001";
	private static final String CODE_HUKUI_ZIKO_HANTEI = "9N016160200000001";
	private static final String CODE_HUKUI_ZISSOKU = "9N016160100000001";
	private static final String WORK_PDF_TMP_13KEKKA_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"13_kekkaura.pdf";

	private JSearchResultListFrameData validatedData = new JSearchResultListFrameData();  //  @jve:decl-index=0:

	private ArrayList<Hashtable<String,String>> searchResult = new ArrayList<Hashtable<String,String>>();
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	// add s.inoue 2010/02/23
	/** �ی��Ҕԍ��A�x����s�@�֑I�𗓂́A�ԍ��Ɩ��̂̋�؂蕶���� */
	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";

	private static final String[] header = {"","�N�x","��f�������ԍ�","�����i�J�i�j","�����i�����j","���N����","����","����","���茋��","�ی��w�����x��","���f���{��","�����","���ʒʒm��","��ی��ҏؓ��L��","��ی��ҏؓ��ԍ�","�ی��Ҕԍ�","��s�@�֔ԍ�","�����R�����g",""};
	private DefaultTableModel dmodel = null;
	private TableRowSorter<TableModel> sorter =null;
	private JSimpleTable table=null;
	private JSimpleTable modelfixed= null;
	// add s.inoue 2009/12/24
	private IDialog pageSelectDialog;

	// edit s.inoue 2010/04/27
	private ArrayList<Integer> selectedRows = null;

	// add h.yoshitama 2009/11/26
	// �S�I���{�^���̏��
	boolean isAllSelect = true;

	/*
	 * �R���X�g���N�^
	 */
	public JSearchResultListFrameCtrl()
	{
		initializeSetting();
	}

	// edit s.inoue 2009/10/26
	private void initializeSetting(){
		dmodel = new DefaultTableModel(resultRefresh(),header){
	      public boolean isCellEditable(int row, int column) {
	    	boolean retflg = false;
	    	if (column == 0 || column >12){
	    		retflg = true;
	      	}else{
	      		retflg = false;
	      	}
	        return retflg;
	      }
	    };

		sorter = new TableRowSorter<TableModel>(dmodel);
		table = new JSimpleTable(dmodel);
		modelfixed = new JSimpleTable(dmodel);

		//�R���{�{�b�N�X�̏����ݒ�
		jComboBox_HanteiResult.addItem("�w�薳��");
		jComboBox_HanteiResult.addItem("������");
		jComboBox_HanteiResult.addItem("�ϋɓI�x��");
		jComboBox_HanteiResult.addItem("���@�Â��x��");
		jComboBox_HanteiResult.addItem("�Ȃ��i���񋟁j");
		jComboBox_HanteiResult.addItem("����s�\");

		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
		table.setPreferedColumnWidths(new int[] {120, 80, 40, 40, 100, 100, 80, 80, 80, 100, 100,80,80, 200, 0});
	    modelfixed.setSelectionModel(table.getSelectionModel());

	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<5) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                modelfixed.getColumnModel().getColumn(i).setResizable(false);
            }else{
                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
            }
        }

	    table.setRowSorter(sorter);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    modelfixed.setRowSorter(sorter);
	    modelfixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    modelfixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    initilizeFocus();
	    // add s.inoue 2009/12/01
	    functionListner();
	   	// add s.inoue 2010/02/23
		initializeHokenjaNumComboBox();

        final JScrollPane scroll = new JScrollPane(table);
        JViewport viewport = new JViewport();
        viewport.setView(modelfixed);
        viewport.setPreferredSize(modelfixed.getPreferredSize());
        scroll.setRowHeader(viewport);

        modelfixed.setPreferredScrollableViewportSize(modelfixed.getPreferredSize());
        scroll.setRowHeaderView(modelfixed);
        scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, modelfixed.getTableHeader());

        scroll.getRowHeader().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JViewport viewport = (JViewport) e.getSource();
                scroll.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
            }
        });

		jPanel_Table.add(scroll);

		jCheckBox_IsKekkaInput.setSelected(true);
		jCheckBox_JisiYear.setSelected(true);

		/* Enter �L�[�����̏��� */
		table.addKeyListener(new JEnterEvent(this,jButton_OK));

		/* �_�u���N���b�N�̏��� */
		table.addMouseListener(new JSingleDoubleClickEvent(this,jButton_OK,modelfixed));
		modelfixed.addMouseListener(new JSingleDoubleClickEvent(this, jButton_OK,modelfixed));

		dmodel.setDataVector(resultRefresh(),header);

		// edit s.inoue 2010/02/16
	    if (resultRefresh().length == 0){
	    	JErrorMessage.show("M4955", this);
	    }

		// add s.inoue 2009/10/26
		// 4��ȉ��͌Œ�A�ȍ~�͕ϓ�
		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<4) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                modelfixed.getColumnModel().getColumn(i).setResizable(false);
            }else{
                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
            }
        }

	   // add s.inoue 2009/10/26
	   TableColumnModel columnsfix = modelfixed.getColumnModel();
	   for(int i=1;i<columnsfix.getColumnCount();i++) {

		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
	   }

	   TableColumnModel columns = table.getColumnModel();
	   for(int i=0;i<columns.getColumnCount();i++) {

		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
	   }

	   this.initializeColumnWidth();

		modelfixed.setSelectionModel(table.getSelectionModel());
		modelfixed.getColumnModel().getColumn(0).setResizable(false);
		modelfixed.getColumnModel().getColumn(1).setResizable(false);
		modelfixed.getColumnModel().getColumn(2).setResizable(false);
		modelfixed.getColumnModel().getColumn(3).setResizable(false);

		hanteiSetting();
		// �Œ�,�ϓ����t���b�V��
	    // edit s.inoue 2009/12/07
		modelfixed.setCellCheckBoxEditor(new ExtendedCheckBox(), 0);
	    table.refreshTable();

	     // edit s.inoue 2010/02/24
	     int count = 0;
	     // �����I��
		 if (table.getRowCount() > 0) {
			 table.setRowSelectionInterval(0, 0);
			 count = table.getRowCount();
		 } else {
			 jTextField_Jyusinken_ID.requestFocus();
		 }
		 jLabel_count.setText(count + "��");

	   // add s.inoue 2009/12/12
	   table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		   public void valueChanged(ListSelectionEvent e) {
		     if(e.getValueIsAdjusting()) return;

		     int i = table.getSelectedRow();
		     if (i <= 0) return;

		     if(modelfixed.getValueAt(i, 0) == null){
		    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);return;
		     }
		     // selectedrow�̏�����(simpletable�Ƃ̘A�g)
		     if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
		    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
		     }else{
		    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
		     }
		   }
		 });

	   modelfixed.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		   public void valueChanged(ListSelectionEvent e) {
		     if(e.getValueIsAdjusting()) return;

		     int i = table.getSelectedRow();
		     if (i <= 0) return;

		     if(modelfixed.getValueAt(i, 0) == null){
		    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
		     }else if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
		    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
		     }else{
		    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
		     }
		   }
		 });

	}

	/* focus������ */
	private void initilizeFocus(){
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jTextField_Name);
		this.focusTraversalPolicy.addComponent(jTextField_Name);
		this.focusTraversalPolicy.addComponent(jTextField_Jyusinken_ID);
		this.focusTraversalPolicy.addComponent(jComboBox_HokenjyaNumber);
		this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
		this.focusTraversalPolicy.addComponent(jTextField_HokensyoCode);
		this.focusTraversalPolicy.addComponent(jComboBox_HanteiResult);
		this.focusTraversalPolicy.addComponent(jTextField_KensaBeginDate);
		this.focusTraversalPolicy.addComponent(jTextField_KensaEndDate);
		this.focusTraversalPolicy.addComponent(jTextField_HanteiBeginDate);
		this.focusTraversalPolicy.addComponent(jTextField_HanteiEndDate);
		this.focusTraversalPolicy.addComponent(jTextField_TsuuchiBeginDate);
		this.focusTraversalPolicy.addComponent(jTextField_TsuuchiEndDate);
		this.focusTraversalPolicy.addComponent(jCheckBox_IsKekkaInput);
		this.focusTraversalPolicy.addComponent(jCheckBox_JisiYear);
		this.focusTraversalPolicy.addComponent(jButton_AllSelect);
		this.focusTraversalPolicy.addComponent(jButton_Serach);
		this.focusTraversalPolicy.addComponent(table);
		this.focusTraversalPolicy.addComponent(jButton_Metabo);
		this.focusTraversalPolicy.addComponent(jButton_Print);
		this.focusTraversalPolicy.addComponent(jButton_PrintSetsumei);
		this.focusTraversalPolicy.addComponent(jButton_OK);
		this.focusTraversalPolicy.addComponent(jButton_End);

	}

	// add s.inoue 2009/12/01
	private void functionListner(){
		Component comp = null;
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
		modelfixed.addKeyListener(this);
	}

	// add s.inoue 2010/02/23
	/**
	 * �ی��Ҕԍ��A���̂̈ꗗ���擾����B
	 */
	private ArrayList<Hashtable<String, String>> getHokenjaNumAndNames() {
		ArrayList<Hashtable<String, String>> result = null;
		// add s.inoue 2010/01/15
		String query = new String("SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA WHERE YUKOU_FLG = '1' ORDER BY HKNJANUM");
		try{
			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		}catch(SQLException e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * �ی��Ҕԍ��R���{�{�b�N�X�̏�����
	 */
	private void initializeHokenjaNumComboBox() {
		ArrayList<Hashtable<String, String>> result = this.getHokenjaNumAndNames();

		jComboBox_HokenjyaNumber.addItem("");

		for(int i = 0;i < result.size();i++ )
		{
			Hashtable<String,String> resultItem = result.get(i);
			String num = resultItem.get("HKNJANUM");
			String name = resultItem.get("HKNJANAME");

			StringBuffer buffer = new StringBuffer();
			buffer.append(num);
			if (name != null && ! num.isEmpty()) {
				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
				buffer.append(name);
			}

			jComboBox_HokenjyaNumber.addItem(buffer.toString());
		}
	}

	// edit s.inoue 2009/10/26
	/**
	 * ��T�C�Y������������B
	 */
	private void initializeColumnWidth() {
		this.table.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_OFF);

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.table.getColumnModel();
		// edit s.inoue 2009/10/31
		columnModel.getColumn(COLUMN_INDEX_UKETUKE_ID - modelfixed.getColumnCount()).setMinWidth(0);
		columnModel.getColumn(COLUMN_INDEX_UKETUKE_ID- modelfixed.getColumnCount()).setPreferredWidth(0);
		columnModel.getColumn(COLUMN_INDEX_UKETUKE_ID- modelfixed.getColumnCount()).setMaxWidth(0);
		columnModel.getColumn(COLUMN_INDEX_UKETUKE_ID- modelfixed.getColumnCount()).setWidth(0);
	}

	/**
	 * �������ɒl����ł��w�肳��Ă��邩���`�F�b�N����
	 * @return true:�����̏������w�肳��Ă��� false:�܂������w�肳��Ă��Ȃ�
	 */
	public boolean isNeedSearch()
	{
		if(
				validatedData.getJyushinkenID().isEmpty() &&
				validatedData.getHihokenjyaCode().isEmpty() &&
				validatedData.getHihokenjyaNumber().isEmpty() &&
				validatedData.getHokenjyaNumber().isEmpty() &&
				validatedData.getName().isEmpty() &&
				validatedData.getKensaBeginDate().isEmpty() &&
				validatedData.getKensaEndDate().isEmpty() &&
				validatedData.getHanteiBeginDate().isEmpty() &&
				validatedData.getHanteiEndDate().isEmpty() &&
				validatedData.getTsuuchiBeginDate().isEmpty() &&
				validatedData.getTsuuchiEndDate().isEmpty() &&
				validatedData.getHanteiKekka().isEmpty() &&
				validatedData.getHanteiKekka().equals("0") &&
				!jCheckBox_IsKekkaInput.isSelected()
				)
		{
			return false;
		}
		return true;
	}

	/*
	 *
	 */
	public void pushedEndButton( ActionEvent e )
	{
		dispose();
	}

	/*
	 * ���������C���A�@�\�ǉ��̂��߁A�啝�ɏ����������B
	 */
	public void pushedSearchButton( ActionEvent e )
	{
		searchRefresh();
	}

	// add s.inoue 2009/11/18
	/*
	 * �������t���b�V������
	 */
	private void searchRefresh(){

		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
		table.setPreferedColumnWidths(new int[] {120, 80, 40, 40, 100, 100, 80, 80, 80, 100, 100,80,80, 200, 0});
	    modelfixed.setSelectionModel(table.getSelectionModel());

	    Object [][] resultref=resultRefresh();
	    if (resultref == null)
	    	return;

	    // add h.yoshitama 2009/11/16
	    if (resultref.length == 0){
	    	JErrorMessage.show("M4955", this);
	    }

	    // add s.inoue 2009/10/26
	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<5) {
                table.removeColumn(table.getColumnModel().getColumn(i));
            }
        }

	    dmodel.setDataVector(resultref,header);

		// add s.inoue 2009/10/26
		// 4��ȉ��͌Œ�A�ȍ~�͕ϓ�
		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<4) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                modelfixed.getColumnModel().getColumn(i).setResizable(false);
            }else{
                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
            }
        }

	   // add s.inoue 2009/10/26
	   TableColumnModel columnsfix = modelfixed.getColumnModel();
	   for(int i=1;i<columnsfix.getColumnCount();i++) {

		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
	   }

	   TableColumnModel columns = table.getColumnModel();
	   for(int i=0;i<columns.getColumnCount();i++) {

		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
	   }

		this.initializeColumnWidth();
	    // add s.inoue 2009/10/26
		hanteiSetting();

		// edit s.inoue 2009/12/07
		// �Œ�,�ϓ����t���b�V��
	    modelfixed.setCellCheckBoxEditor(new ExtendedCheckBox(), 0);
	    table.refreshTable();

	    // edit s.inoue 2009/11/09
		// �����I��
	    int count = 0;
		if (table.getRowCount() > 0) {

			count = table.getRowCount();
			table.setRowSelectionInterval(0, 0);
			} else {
				jTextField_Jyusinken_ID.requestFocus();
		}
		jLabel_count.setText(count + " ��");
	}

	/**
	 * �ی��w�����x���擾�p��SQL���擾����B
	 */
	private String getHokenshidowLevelSql() {

		StringBuffer queryBuffer = new StringBuffer();

		queryBuffer.append(" select ");
		/* ��tID */
		queryBuffer.append(" kojin.UKETUKE_ID,");
		/* ���f�N���� */
		queryBuffer.append(" tokutei.KENSA_NENGAPI,");
		/* ���ʒl */
		queryBuffer.append(" sonota.KEKA_TI ");
		queryBuffer.append(" from T_KOJIN AS kojin ");
		queryBuffer.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI AS tokutei ");
		queryBuffer.append(" ON kojin.UKETUKE_ID = tokutei.UKETUKE_ID ");
		queryBuffer.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
		queryBuffer.append(" ON sonota.UKETUKE_ID = tokutei.UKETUKE_ID ");
		queryBuffer.append(" AND sonota.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
		queryBuffer.append(" AND sonota.KOUMOKU_CD = ");
		queryBuffer.append(JQueryConvert.queryConvert(CODE_HOKENSHIDOU_LEVEL));

		if( isNeedSearch() ){
			/* �����p�̏��������擾����B */
			String conditionString = getSearchConditionString();
			queryBuffer.append(conditionString);
		}

		return queryBuffer.toString();
	}

	/**
	 * ���^�{���b�N�V���h���[�����茋�ʎ擾�p��SQL���擾����B
	 */
	private String getMetaboHanteiSql() {

		StringBuffer queryBuffer = new StringBuffer();

		queryBuffer.append(" select ");
		/* ��tID */
		queryBuffer.append(" kojin.UKETUKE_ID,");
		/* ���f�N���� */
		queryBuffer.append(" tokutei.KENSA_NENGAPI,");
		/* ���ʒl */
		queryBuffer.append(" sonota.KEKA_TI ");
		queryBuffer.append(" from T_KOJIN AS kojin ");
		queryBuffer.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI AS tokutei ");
		queryBuffer.append(" ON kojin.UKETUKE_ID = tokutei.UKETUKE_ID ");
		queryBuffer.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
		queryBuffer.append(" ON sonota.UKETUKE_ID = tokutei.UKETUKE_ID ");
		queryBuffer.append(" AND sonota.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
		queryBuffer.append(" AND sonota.KOUMOKU_CD = ");
		queryBuffer.append(JQueryConvert.queryConvert(CODE_METABO_HANTEI));

		return queryBuffer.toString();
	}

	/**
	 * �����p��SQL���擾����B
	 */
	private String getSearchSql() {
		StringBuffer queryBuffer = new StringBuffer();

		queryBuffer.append("select ");
		/* ��tID */
		queryBuffer.append("kojin.UKETUKE_ID,");
		/* ���ʒl */
		queryBuffer.append("sonota.KEKA_TI,");
		/* �ی��Ҕԍ� */
		queryBuffer.append("kojin.HKNJANUM,");
		// edit s.inoue 2009/11/02
		queryBuffer.append("kojin.SIHARAI_DAIKOU_BANGO,");

		/* ��ی��ҏؓ��L�� */
		queryBuffer.append("kojin.HIHOKENJYASYO_KIGOU,");
		/* ��ی��ҏؓ��ԍ� */
		queryBuffer.append("kojin.HIHOKENJYASYO_NO,");
		/* ��f�������ԍ� */
		queryBuffer.append("kojin.JYUSHIN_SEIRI_NO,");
		/* �����i�����j */
		queryBuffer.append("kojin.NAME,");
		/* �����i�J�i�j */
		queryBuffer.append("kojin.KANANAME,");
		/* �Z�� */
		queryBuffer.append("kojin.HOME_ADRS,");
		/* ���N���� */
		queryBuffer.append("kojin.BIRTHDAY,");
		/* ���� */
		queryBuffer.append("kojin.SEX,");
		/* ���f�N���� */
		queryBuffer.append("tokutei.KENSA_NENGAPI,");
		/* ����N���� */
		queryBuffer.append("tokutei.HANTEI_NENGAPI,");
		/* �ʒm�N���� */
		queryBuffer.append("tokutei.TUTI_NENGAPI,");
		/* �R�����g */
		queryBuffer.append("tokutei.KOMENTO, ");

		/* ���ʓ��̓t���O */
		queryBuffer.append("tokutei.KEKA_INPUT_FLG, ");
		/* ����ID */
		queryBuffer.append("sonota.KOUMOKU_CD, ");
		/* �N�x */
		queryBuffer.append("GET_NENDO.NENDO ");
		queryBuffer.append("from T_KOJIN AS kojin ");
		queryBuffer.append("LEFT JOIN T_KENSAKEKA_TOKUTEI AS tokutei ");
		queryBuffer.append("ON kojin.UKETUKE_ID = tokutei.UKETUKE_ID ");
		queryBuffer.append("LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
		queryBuffer.append("ON sonota.UKETUKE_ID = kojin.UKETUKE_ID ");
		queryBuffer.append("AND sonota.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
		queryBuffer.append("AND sonota.KOUMOKU_CD = ");

		/* �ی��w�����x���̌������� */
		queryBuffer.append(JQueryConvert.queryConvert(CODE_HOKENSHIDOU_LEVEL));

		queryBuffer.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
		queryBuffer.append(" when substring(tokutei.KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
		queryBuffer.append(" substring(tokutei.KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
		queryBuffer.append(" substring(tokutei.KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
		queryBuffer.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
		queryBuffer.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");

		queryBuffer.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
		queryBuffer.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
		queryBuffer.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");

		if( isNeedSearch() ){
			/* �����p�̏��������擾����B */
			String conditionString = this.getSearchConditionString();
			queryBuffer.append(conditionString);
		}
		queryBuffer.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");

		return queryBuffer.toString();
	}

	/**
	 * �����p�̏��������擾����B
	 */
	private String getSearchConditionString() {
		List<String> conditions = new ArrayList<String>();

		String jyushinkenId = jTextField_Jyusinken_ID.getText();
		if (jyushinkenId != null && ! jyushinkenId.isEmpty()) {
			// edit s.inoue 2009/10/27
			// conditions.add(" kojin.JYUSHIN_SEIRI_NO CONTAINING " + JQueryConvert.queryConvert(jyushinkenId));
			conditions.add(" kojin.JYUSHIN_SEIRI_NO STARTING WITH "
					+ JQueryConvert.queryConvert(jyushinkenId));
		}

		if( !validatedData.getHihokenjyaCode().isEmpty() )
		{
			// edit s.inoue 2009/10/27
			// conditions.add(" kojin.HIHOKENJYASYO_KIGOU CONTAINING " + JQueryConvert.queryConvert(validatedData.getHihokenjyaCode()));
			conditions.add(" kojin.HIHOKENJYASYO_KIGOU STARTING WITH "
					+ JQueryConvert.queryConvert(validatedData.getHihokenjyaCode()));
		}
		if( !validatedData.getHihokenjyaNumber().isEmpty() )
		{
			// edit s.inoue 2009/10/27
			// conditions.add(" kojin.HIHOKENJYASYO_NO CONTAINING " + JQueryConvert.queryConvert(validatedData.getHihokenjyaNumber()));
			conditions.add(" kojin.HIHOKENJYASYO_NO STARTING WITH "
					+ JQueryConvert.queryConvert(validatedData.getHihokenjyaNumber()));
		}
		if( !validatedData.getHokenjyaNumber().isEmpty() )
		{
			// edit s.inoue 2009/10/27
			// conditions.add(" kojin.HKNJANUM CONTAINING " + JQueryConvert.queryConvert(validatedData.getHokenjyaNumber()));
			// edit s.inoue 2009/11/02
			conditions.add(" kojin.HKNJANUM STARTING WITH "
					+ JQueryConvert.queryConvert(JValidate.fillZero(validatedData
							.getHokenjyaNumber(), 8)));
		}
		if( !validatedData.getName().isEmpty() )
		{
			// edit s.inoue 2009/10/27
			// conditions.add(" kojin.KANANAME CONTAINING " + JQueryConvert.queryConvert(validatedData.getName()));
			conditions.add(" kojin.KANANAME STARTING WITH "
					+ JQueryConvert.queryConvert(validatedData.getName()));
		}
		if( !validatedData.getKensaBeginDate().isEmpty() )
		{
			conditions.add(" tokutei.KENSA_NENGAPI >= " + JQueryConvert.queryConvert(validatedData.getKensaBeginDate()));
		}
		if( !validatedData.getKensaEndDate().isEmpty() )
		{
			conditions.add(" tokutei.KENSA_NENGAPI <= " + JQueryConvert.queryConvert(validatedData.getKensaEndDate()));
		}
		if( !validatedData.getHanteiBeginDate().isEmpty() )
		{
			conditions.add(" tokutei.HANTEI_NENGAPI >= " + JQueryConvert.queryConvert(validatedData.getHanteiBeginDate()));
		}
		if( !validatedData.getHanteiEndDate().isEmpty() )
		{
			conditions.add(" tokutei.HANTEI_NENGAPI <= " + JQueryConvert.queryConvert(validatedData.getHanteiEndDate()));
		}
		if( !validatedData.getTsuuchiBeginDate().isEmpty() )
		{
			conditions.add(" tokutei.TUTI_NENGAPI >= " + JQueryConvert.queryConvert(validatedData.getTsuuchiBeginDate()));
		}
		if( !validatedData.getTsuuchiEndDate().isEmpty() )
		{
			conditions.add(" tokutei.TUTI_NENGAPI <= " + JQueryConvert.queryConvert(validatedData.getTsuuchiEndDate()));
		}

		String hanteiKekka = validatedData.getHanteiKekka();
		if( !hanteiKekka.equals("0") ){
			if (hanteiKekka != null && hanteiKekka.isEmpty()) {
				conditions.add(" sonota.KEKA_TI = ''");
			}
			else {
				conditions.add(" sonota.KEKA_TI = " + JQueryConvert.queryConvert(hanteiKekka));
			}
		}
		// edit ver2 s.inoue 2009/07/27
		/* ���ʓ��̗͂L�� */
		if( jCheckBox_IsKekkaInput.isSelected() )
		{
			// edit s.inoue 2009/09/16
			// �ďC�� �t���O'2'��������null��������
			// �o�^�������́A���f���{����������
			// conditions.add(" tokutei.KEKA_INPUT_FLG = '2' ");
			// ���N�x��\��
			// edit s.inoue 2009/10/27
			if (jCheckBox_JisiYear.isSelected()) {
				conditions.add(" (GET_NENDO.NENDO is null or GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())) + ")");
			}
		}else{
			// ���N�x��\��
			if (jCheckBox_JisiYear.isSelected()) {
				// edit s.inoue 2009/09/16
				conditions.add(" (( tokutei.KEKA_INPUT_FLG = '1' or tokutei.KEKA_INPUT_FLG is NULL) and (GET_NENDO.NENDO is null or  GET_NENDO.NENDO = "
				+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())) + "))");
				// conditions.add(" ( tokutei.KEKA_INPUT_FLG = '1' or tokutei.KEKA_INPUT_FLG is NULL or GET_NENDO.NENDO = "
				//		+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())) + ")");
			}else{
				conditions.add(" ( tokutei.KEKA_INPUT_FLG = '1' or tokutei.KEKA_INPUT_FLG is NULL ) ");
			}
		}

		StringBuffer conditionBuffer = new StringBuffer();
		int count = conditions.size();
		if (count > 0) {
			conditionBuffer.append(" WHERE ");

			for (int i = 0; i < count - 1; i++) {
				String condition = conditions.get(i);
				conditionBuffer.append(condition);
				conditionBuffer.append(" AND ");
			}

			conditionBuffer.append(conditions.get(count - 1));
		}

		String conditionString = conditionBuffer.toString();
		return conditionString;
	}

	/*
	 * ����@�\
	 * ���茒�f���ʒʒm
	 * import Print.*;
	 * import PrintData.*;
	 *
	 * 1�y�[�W
	 * �\��
	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
	 * import	Print.Kekka_P1
	 * class	Kekka_P1
	 *
	 * 2�y�[�W
	 * ���f���ʁi�ڍׁj
	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
	 * import	Print.Kekka_P2
	 * class	Kekka_P2
	 *
	 * 3�y�[�W
	 * ���f���ʁi�ꗗ�j
	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
	 * import	Print.Kekka_P3
	 * class	Kekka_P3
	 *
	 *
	 * 4�y�[�W
	 * ���f���ʁi��f�j
	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
	 * import	Print.Kekka_P4
	 * class	Kekka_P4
	 *
	 */
	public void pushedPrintButton( ActionEvent e )
	{
		// �I����Ԃ�ێ�����
		ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);


		// add s.inoue 2009/12/24
		// A4-1��,2���I��
		pageSelectDialog = DialogFactory.getInstance().createDialog(this);

		// ������@�I���_�C�A���O��\��
		pageSelectDialog.setMessageTitle("������@�I�����");
		pageSelectDialog.setVisible(true);

		int pageSelect = (pageSelectDialog.getPrintSelect()==2) ?2:1;
		// cancel��break
		if(pageSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL)){
			return;
		}

		/*
		 * �l���f�[�^�쐬
		 */
		// edit ver2 s.inoue 2009/09/02
		for(int i = 0;i < table.getRowCount();i++ )
		{
			if( (Boolean)modelfixed.getData(i, 0) == Boolean.TRUE )
			{
				Hashtable<String, String> searchResultItem = searchResult.get(i);

				String kensaNengapi = (String)table.getData(i, COLUMN_INDEX_KENSA_NENGAPI);

				String itemJyushinSeiriNo = searchResultItem.get("JYUSHIN_SEIRI_NO");
				String itemKanaName = searchResultItem.get("KANANAME");
				String itemHokenjaNumber = searchResultItem.get("HKNJANUM");
				String itemHihokenjyasyoKigou = searchResultItem.get("HIHOKENJYASYO_KIGOU");
				String itemHihokenjyasyoNumber = searchResultItem.get("HIHOKENJYASYO_NO");
				String itemUketukeId = searchResultItem.get("UKETUKE_ID");

					/* �e�l�����؂��� */
					validatedData.setJyushinkenID(itemJyushinSeiriNo);
					validatedData.setName(itemKanaName);
					validatedData.setHokenjyaNumber(itemHokenjaNumber);
					validatedData.setHihokenjyaCode(itemHihokenjyasyoKigou);
					validatedData.setHihokenjyaNumber(itemHihokenjyasyoNumber);
					validatedData.setUketukeId(itemUketukeId);

					/* ���ؗp�I�u�W�F�N�g����e�l���Ď擾����B */
					String uketukeId = validatedData.getUketukeId();
					String hihokenjyaCode = validatedData.getHihokenjyaCode();
					String hihokenjyaNumber = validatedData.getHihokenjyaNumber();
					String hokenjyaNumber = validatedData.getHokenjyaNumber();

					/* ���f���ʒʒm�\���������B */
					PrintKekka kekka = new PrintKekka();
					kekka.printResultCard(
							kensaNengapi,
							uketukeId,
							hihokenjyaCode,
							hihokenjyaNumber,
							hokenjyaNumber,
							this,
							pageSelect);
			}
		}
		// add 20081224 s.inoue�`�F�b�N��Ԃ�ێ����ď������Ԃ�߂�
		resultRefresh();
		table.setselectedRows(modelfixed,selectedRows);
	}

	public void pushedOKButton( ActionEvent e ) {
		int selectedRowCount = table.getSelectedRowCount();
		if (selectedRowCount != 1) return;
		int selectedIndex = -1;

		// edit s.inoue 2010/04/27
		setSelectedRows();
//		ArrayList<Integer> selectedRow = new ArrayList<Integer>();
//
//		//���݃`�F�b�N����Ă���s�̃��X�g�𒊏o
//		// edit ver2 s.inoue 2009/09/02
//		for( int i = 0;i < table.getRowCount();i++)
//		{
//			if( modelfixed.getData(i, 0) == Boolean.TRUE )
//			{
//				selectedRow.add(i);
//			}
//		}
//		if( selectedRow.size() != 1 ){
//			JErrorMessage.show("M3520", this);
//			return;
//		}

		selectedIndex = selectedRows.get(0).intValue();

		// Hashtable�͏����p�~�\��̂��߁AList<Map<String,String>>�ɋl�ߑւ���
		List<TreeMap<String, String>> recordList = new ArrayList<TreeMap<String,String>>();
		for (Hashtable<String, String> table : searchResult) {
			// edit s.inoue 2009/11/17
			TreeMap<String, String> recMap = new TreeMap<String, String>(table);

			// edit s.inoue 2009/11/17
			recordList.add(recMap);
		}

		JScene.CreateDialog(this, new JShowResultFrameCtrl(recordList, selectedIndex), new WindowRefreshEvent());
	}

	// add s.inoue 2010/04/27
	private void setSelectedRows(){
		selectedRows =  new ArrayList<Integer>();

		// ���݃`�F�b�N����Ă���s�̃��X�g�𒊏o
		for (int i = 0; i < this.modelfixed.getRowCount(); i++) {
			if (this.modelfixed.getData(i, 0) == Boolean.TRUE) {
				selectedRows.add(i);
			}
		}
	}

	/**
	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ�Ƀe�[�u���̍ĕ`����s��
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			// del s.inoue 2009/10/26
			// table.clearAllRow();
			// modelfixed.clearAllRow();
			pushedSearchButton(null);
			// edit s.inoue 2010/04/27
			if (selectedRows != null)
				modelfixed.setselectedRows(modelfixed,selectedRows);
		}
	}

	/**
	 * ���^�{���b�N�V���h���[������A�K�w��
	 *
	 * Modified 2008/04/01 �ጎ
	 * ���F������̂��߁A�s�v�ȃR�����g���폜�����B
	 * �܂��A�����̃��\�b�h���Ȃǂ̂�t�@�N�^�����O���s�Ȃ����B
	 */
	public void pushedMetaboButton( ActionEvent e )
	{
		Vector<JIppanHanteiStartData> ipDataSet = new Vector<JIppanHanteiStartData>();

		boolean existsSuccessResult = false;

		// edit s.inoue 2009/09/25
		// �I����Ԃ�ێ�����
		ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);

		/* ��ʔ��� */
		boolean success = this.ipanHantei(ipDataSet);
		if (! success) {
			JErrorMessage.show("M4953", this);
			return;
		}

		try
		{
			/* ���^�{���� */
			this.metaboHantei();

		}catch(Exception e1)
		{
			e1.printStackTrace();
			JErrorMessage.show("M4950", this);
			return;
		}

		try
		{
			/* �K�w�� */
			this.kaisouka(existsSuccessResult);

		}catch(Exception e2)
		{
			e2.printStackTrace();
			JErrorMessage.show("M4951", this);
			return;
		}
		// edit s.inoue 2009/10/26
		// �`�F�b�N��Ԃ�ێ�
		searchRefresh();
		// edit s.inoue 2009/09/25
		table.setselectedRows(modelfixed,selectedRows);
	}

	/**
	 * �K�w��
	 */
	private void kaisouka(boolean existsSuccessResult) throws SQLException {
		// edit ver2 s.inoue 2009/09/02
		for(int i = 0 ; i < table.getRowCount() ; i++)
		{
			if((Boolean)modelfixed.getData(i, 0) == Boolean.TRUE)
			{
				String uketukeId = searchResult.get(i).get("UKETUKE_ID");

				String KensaDate = searchResult.get(i).get("KENSA_NENGAPI");
				int hokenLevelResult = 0;

				//�K�w��������s��
				JKaisoukaHanteiData kaisouData = new JKaisoukaHanteiData();

				// �N��

				/* Modified 2008/06/03 �ጎ
				 * ���N���N�x�N��ɕύX */
				/* --------------------------------------------------- */
//				int BirthDay = Integer.valueOf(searchResult.get(i).get("BIRTHDAY"));
//		        DateFormat format = new SimpleDateFormat("yyyyMMdd");
//				int Age = (int)(Integer.valueOf(format.format(new Date())) - BirthDay/10000);
//				kaisouData.setAge(Age);
				/* --------------------------------------------------- */

				String birthday = searchResult.get(i).get("BIRTHDAY");
				// edit s.inoue 20081113
				//�������狤�ʉ�getFiscalYear
				//int Age = FiscalYearUtil.getFiscalYear(birthday);
				int Age = FiscalYearUtil.getFiscalYear(birthday,KensaDate);

//				int birthDayInt = Integer.valueOf(birthday);
//				int intYear = 10000;
//
//		        DateFormat format = new SimpleDateFormat("yyyyMMdd");
//
//		        String tmpGetu = format.format(new Date()).substring(4,6);
//
//		        if (tmpGetu.equals("01") ||
//		        		tmpGetu.equals("02") ||
//		        			tmpGetu.equals("03")) {
//		        	intYear = 0;
//				}
//
//		        String tmpJuge = format.format(new Date()).substring(0,4) + "0331";
//
//				//int Age = ((Integer.valueOf(tmpJuge)+10000) - birthDayInt)/10000;
//		        int Age = ((Integer.valueOf(tmpJuge)+intYear) - birthDayInt)/10000;
				//�����܂�

				/*if (birthdayMMdd.compareTo("0101") >= 0 &&
		        		birthdayMMdd.compareTo("0401") < 0) {
					++Age;
				}
		        int Age = ((Integer.valueOf(format.format(new Date())) +10000) - birthDayInt)/10000;
				if (birthdayMMdd.compareTo("0101") >= 0 &&
						birthdayMMdd.compareTo("0401") < 0) {
					--Age;
				}*/

				kaisouData.setAge(Age);

				// add ver2 s.inoue 2009/08/19
				if (Age == 75){
					kaisouData.setTargetAge(FiscalYearUtil.getJugeDate(birthday,KensaDate));
				}

				// BMI
				String BMI = GetResultValue(uketukeId, KensaDate, CODE_BMI);
				if(!BMI.equals(""))
				{
					kaisouData.setBMI(Double.valueOf(BMI));
				}

				// �������b
				String chuseiShibou = GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_1);
				if(chuseiShibou.equals(""))
				{
					String cyuseishibou2 = GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_2);
					if(! cyuseishibou2.equals("") )
					{
						chuseiShibou = cyuseishibou2;
					}else{
						String cyuseishibou3 = GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_3);
						if(!cyuseishibou3.equals("")){
							chuseiShibou = cyuseishibou3;
						}
					}
				}

				if(!chuseiShibou.equals("")){
					kaisouData.setChuseiShibou(Double.valueOf(chuseiShibou));
				}

				// ����
				kaisouData.setGender(searchResult.get(i).get("SEX"));

				// HbA1c
				String hba1c = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_1);
				if(hba1c.equals(""))
				{
					String hba1c_2 = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_2);
					if(hba1c_2.equals(""))
					{
						String hba1c_3 = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_3);
						if(hba1c_3.equals(""))
						{
							String hba1c_4 = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_4);
							if(hba1c_4.equals(""))
							{
								// �Y�����ڂȂ�
							}else{
								hba1c = hba1c_4;
							}
						}else{
							hba1c = hba1c_3;
						}
					}else{
						hba1c = hba1c_2;
					}
				}

				if(!hba1c.equals(""))
				{
					kaisouData.setHbA1c(Double.valueOf(hba1c));
				}

				// HDL�R���X�e���[��
				String HDLCholesterol = "";
				if(GetResultValue(uketukeId, KensaDate, CODE_HDL_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_HDL_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_HDL_3).equals(""))
						{
							// �Y�����ڂȂ�
						}else{
							HDLCholesterol = GetResultValue(uketukeId, KensaDate, CODE_HDL_3);
						}
					}else{
						HDLCholesterol = GetResultValue(uketukeId, KensaDate, CODE_HDL_2);
					}
				}else{
					HDLCholesterol = GetResultValue(uketukeId, KensaDate, CODE_HDL_1);
				}
				if(!HDLCholesterol.equals(""))
				{
					kaisouData.setHDLCholesterol(Double.valueOf(HDLCholesterol));
				}

				// ���́i���ȑ���j
				String Hukui_JikoSokutei = GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKO_HANTEI);
				if(!Hukui_JikoSokutei.equals(""))
				{
					kaisouData.setHukui_JikoSokutei(Double.valueOf(Hukui_JikoSokutei));
				}

				// ���́i���Ȑ\���j
				String Hukui_JikoShinkoku = GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKOSHINKOKU);
				if(!Hukui_JikoShinkoku.equals(""))
				{
					kaisouData.setHukui_JikoShinkoku(Double.valueOf(Hukui_JikoShinkoku));
				}

				// ���́i�����j
				String Hukui_Jissoku = GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZISSOKU);
				if(!Hukui_Jissoku.equals(""))
				{
					kaisouData.setHukui_Jissoku(Double.valueOf(Hukui_Jissoku));
				}

				// �g���������i1��ځj
				String KakutyoKetsuatsuNo1 = GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_1);
				if(!KakutyoKetsuatsuNo1.equals(""))
				{
					kaisouData.setKakutyoKetsuatsuNo1(Double.valueOf(KakutyoKetsuatsuNo1));
				}

				// �g���������i2��ځj
				String KakutyoKetsuatsuNo2 = GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_2);
				if(!KakutyoKetsuatsuNo2.equals(""))
				{
					kaisouData.setKakutyoKetsuatsuNo2(Double.valueOf(KakutyoKetsuatsuNo2));
				}

				// �g���������i���̑��j
				String KakutyoKetsuatsuOther = GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_3);
				if(!KakutyoKetsuatsuOther.equals(""))
				{
					kaisouData.setKakutyoKetsuatsuOther(Double.valueOf(KakutyoKetsuatsuOther));
				}

				// ����i�����j
				String KetsuatsuHukuyaku = GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_1);
				if(!KetsuatsuHukuyaku.equals(""))
				{
					kaisouData.setKetsuatsuHukuyaku(Double.valueOf(KetsuatsuHukuyaku));
				}

				// ����i�����j
				String KettoHukuyaku = GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_2);
				if(!KettoHukuyaku.equals(""))
				{
					kaisouData.setKettoHukuyaku(Double.valueOf(KettoHukuyaku));
				}

				// �i��
				String Kitsuen = GetResultValue(uketukeId, KensaDate, "9N736000000000011");
				if(!Kitsuen.equals(""))
				{
					kaisouData.setKitsuen(Double.valueOf(Kitsuen));
				}

				// �󕠎�����
				String kuhukujiKeto = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_1);
				if(kuhukujiKeto.equals(""))
				{
					String kuhukujiKeto2 = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_2);
					if(kuhukujiKeto2.equals(""))
					{
						String kuhukujiKeto3 = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_3);
						if(kuhukujiKeto3.equals(""))
						{
							String kuhukujiKeto4 = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_4);
							if(! kuhukujiKeto4.equals("")){
								kuhukujiKeto = kuhukujiKeto4;
							}
						}else{
							kuhukujiKeto = kuhukujiKeto3;
						}
					}else{
						kuhukujiKeto = kuhukujiKeto2;
					}
				}

				if(!kuhukujiKeto.equals(""))
				{
					kaisouData.setKuhukujiKetto(Double.valueOf(kuhukujiKeto));
				}

				// �������b�ʐ�
				String NaizouShibou = GetResultValue(uketukeId, KensaDate, CODE_NAIZO_SHIBOU_MENSEKI);
				if(!NaizouShibou.equals(""))
				{
					kaisouData.setNaizouShibou(Double.valueOf(NaizouShibou));
				}

				// �g��
				String Shintyou = GetResultValue(uketukeId, KensaDate, CODE_SHINCHOU);
				if(!Shintyou.equals(""))
				{
					kaisouData.setShintyou(Double.valueOf(Shintyou));
				}

				// ����i�����j
				String ShishitsuHukuyaku = GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_3);
				if(!ShishitsuHukuyaku.equals(""))
				{
					kaisouData.setShishitsuHukuyaku(Double.valueOf(ShishitsuHukuyaku));
				}

				// ���k�������i1��ځj
				String ShushukuKetsuatsuNo1 = GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_1);
				if(!ShushukuKetsuatsuNo1.equals(""))
				{
					kaisouData.setShushukuKetsuatsuNo1(Double.valueOf(ShushukuKetsuatsuNo1));
				}

				// ���k�������i2��ځj
				String ShushukuKetsuatsuNo2 = GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_2);
				if(!ShushukuKetsuatsuNo2.equals(""))
				{
					kaisouData.setShushukuKetsuatsuNo2(Double.valueOf(ShushukuKetsuatsuNo2));
				}

				// ���k�������i���̑��j
				String ShushukuKetsuatsuOther = GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_3);
				if(!ShushukuKetsuatsuOther.equals(""))
				{
					kaisouData.setShushukuKetsuatsuOther(Double.valueOf(ShushukuKetsuatsuOther));
				}

				// �̏d
				String Taijyu = GetResultValue(uketukeId, KensaDate, CODE_TAIJU);
				if(!Taijyu.equals(""))
				{
					kaisouData.setTaijyu(Double.valueOf(Taijyu));
				}

				/* ���菈�� */
				hokenLevelResult = JKaisoukaHantei.Hantei(kaisouData);

				String Query = "UPDATE T_KENSAKEKA_SONOTA SET "
						+ "KEKA_TI = "
						+ JQueryConvert.queryConvert(String
								.valueOf(hokenLevelResult))
						+ " WHERE "
						+ "UKETUKE_ID = "
						+ JQueryConvert.queryConvert(uketukeId)
						+ " AND "
						+ "KENSA_NENGAPI = "
						+ JQueryConvert.queryConvert(KensaDate)
						+ " AND "
						+ "KOUMOKU_CD = "
						+ JQueryConvert
								.queryConvert(CODE_HOKENSHIDOU_LEVEL);


				JApplication.kikanDatabase.sendNoResultQuery(Query);

				/* ��������X�V���� */
				Calendar cal = Calendar.getInstance();
				String yearText = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
				String monthText = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
				String dayText = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

				String hanteibiText = yearText + monthText + dayText;

				Query =
					"UPDATE T_KENSAKEKA_TOKUTEI SET " +
					"HANTEI_NENGAPI = " + JQueryConvert.queryConvert(hanteibiText) +
					" WHERE " +
					"UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId) + " AND " +
					"KENSA_NENGAPI = " + JQueryConvert.queryConvert(KensaDate);
				JApplication.kikanDatabase.sendNoResultQuery(Query);

				existsSuccessResult = true;
			}
		}

		if (existsSuccessResult) {
			JErrorMessage.show("M4952", this);
		}
	}

	/**
	 * ���^�{������s�Ȃ��B
	 */
	private void metaboHantei() throws SQLException {

		// edit ver2 s.inoue 2009/09/02
		for( int i = 0;i < table.getRowCount();i++ )
		{
			if((Boolean)modelfixed.getData(i, 0) == Boolean.TRUE)
			{
				String uketukeId = searchResult.get(i).get("UKETUKE_ID");

				String KensaDate = searchResult.get(i).get("KENSA_NENGAPI");
				int metaboResult;

				JMTHanteiData mtData = new JMTHanteiData();

				//���ʂɊւ��ăf�[�^��}��
				mtData.setGender(searchResult.get(i).get("SEX"));

				// ����
				mtData.setStomach(
						GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZISSOKU),
						GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKO_HANTEI),
						GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKOSHINKOKU)
				);

				// �������b
				if(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_3).equals(""))
						{
							// �Y�����ڂȂ�
						}else{
							mtData.setNaturalFat(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_3));
						}
					}else{
						mtData.setNaturalFat(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_2));
					}
				}else{
					mtData.setNaturalFat(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_1));
				}

				// �����l
				if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_3).equals(""))
						{
							if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_4).equals(""))
							{
								// �Y�����ڂȂ�
							}else{
								mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_4));
							}
						}else{
							mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_3));
						}
					}else{
						mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_2));
					}
				}else{
					mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_1));
				}

				// �������b�ʐ�
				mtData.setInternalFatArea(GetResultValue(uketukeId, KensaDate, CODE_NAIZO_SHIBOU_MENSEKI));

				// HDL
				if(GetResultValue(uketukeId, KensaDate, CODE_HDL_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_HDL_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_HDL_3).equals(""))
						{
							// �Y�����ڂȂ�
						}else{
							mtData.setHDL(GetResultValue(uketukeId, KensaDate, CODE_HDL_3));
						}
					}else{
						mtData.setHDL(GetResultValue(uketukeId, KensaDate, CODE_HDL_2));
					}
				}else{
					mtData.setHDL(GetResultValue(uketukeId, KensaDate, CODE_HDL_1));
				}

				// �����i�g�����j
				mtData.setMaxBloodPressre(
						GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_3),
						GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_2),
						GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_1)
				);

				// �����i���k���j
				mtData.setMinBloodPressre(
						GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_3),
						GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_2),
						GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_1)
				);

				// �̌�����
				mtData.setDrawBloodTime(GetResultValue(uketukeId, KensaDate, CODE_SAIKETSU_ZIKAN));

				// HbA1c
				if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_3).equals(""))
						{
							if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_4).equals(""))
							{
								// �Y�����ڂȂ�
							}else{
								mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_4));
							}
						}else{
							mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_3));
						}
					}else{
						mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_2));
					}
				}else{
					mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_1));
				}

				// ���򎿖�1�i�����j
				mtData.setQuestion1(GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_1));

				// ���򎿖�2�i�����j
				mtData.setQuestion2(GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_2));

				// ���򎿖�3�i�����j
				mtData.setQuestion3(GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_3));
				metaboResult = JMTHantei.checkMT(mtData);

				String query =
					"UPDATE T_KENSAKEKA_SONOTA SET " +
					"KEKA_TI = " + JQueryConvert.queryConvert(String.valueOf(metaboResult)) +
					" WHERE " +
					"UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId) + " AND " +
					"KENSA_NENGAPI = " + JQueryConvert.queryConvert(KensaDate) + " AND " +
					"KOUMOKU_CD = " + JQueryConvert.queryConvert(CODE_METABO_HANTEI);

				JApplication.kikanDatabase.sendNoResultQuery(query);
			}
		}
	}

	private boolean ipanHantei(Vector<JIppanHanteiStartData> ipDataSet) {
		/*
		 * ��ʔ���
		 */
		// edit ver2 s.inoue 2009/09/02
		for(int i = 0;i < table.getRowCount();i++ )
		{
			if((Boolean)modelfixed.getData(i,0) == Boolean.TRUE)
			{
				JIppanHanteiStartData ipData = new JIppanHanteiStartData();
				ipData.setUketukeId(searchResult.get(i).get("UKETUKE_ID"));
				ipData.setKensaJissiDate(searchResult.get(i).get("KENSA_NENGAPI"));
				ipDataSet.add(ipData);
			}
		}

		boolean success = JIppanHantei.Hantei(ipDataSet);

		return success;
	}

	/**
	 * ���ʂ̒l�̎擾
	 * @param uketukeId ��tID
	 * @param KensaDate ��������
	 * @param KoumokuCode ���ڃR�[�h
	 * @return ��������(KEKA_TI)
	 */
	private String GetResultValue(String uketukeId, String kensaDate, String koumokuCode) {

		TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
		TKensakekaSonotaDao dao = null;
		String returnValue = null;
		try {
			dao = (TKensakekaSonotaDao)DaoFactory.createDao(
					JApplication.kikanDatabase.getMConnection(), kensakekaSonota);
			kensakekaSonota = dao.selectByPrimaryKey(new Long(uketukeId), new Integer(kensaDate), koumokuCode);
			returnValue = kensakekaSonota.getKEKA_TI();
		} catch (Exception e) {
		}
		return ((returnValue != null) ? returnValue : "");
	}

	public void actionPerformed( ActionEvent e )
	{
		Object source = e.getSource();
		if( source == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}
		else if( source == jButton_Serach )
		{
			logger.info(jButton_Serach.getText());
			pushedSearchButton( e );
		}
		else if( source == jButton_Print )
		{
			logger.info(jButton_Print.getText());
			pushedPrintButton( e );
		}
		else if( source == jButton_OK )
		{
			logger.info(jButton_OK.getText());
			pushedOKButton( e );
		}
		else if( source == jButton_Metabo )
		{
			logger.info(jButton_Metabo.getText());
			pushedMetaboButton( e );
		}
		else if( source == jButton_PrintSetsumei )
		{
			logger.info(jButton_PrintSetsumei.getText());
			pushePrintSetsumei( e );
		}
		// add h.yoshitama 2009/11/26
		else if( source == jButton_AllSelect	)
		{
			logger.info(jButton_AllSelect.getText());
			pushedAllSelectButton( e );
		}
	}

	/**
	 * �����p���[�i�u��f�҂̕��ցv�j���������
	 * @return
	 */
	public void pushePrintSetsumei( ActionEvent e ) {
		File stumeiPdf = new File(WORK_PDF_TMP_13KEKKA_PDF);
		JFrame ParentFrame = null;

		if(!stumeiPdf.exists()){
			JErrorMessage.show("M5204", this);
		}else{
			try {
				// Linux�Ή� 20081017
				//Process process = Executable.openDocument(stumeiPdf, true);
				Executable pdfexe = new Executable();
				Process process=null;
				if (!pdfexe.isWindows() && !pdfexe.isMac()) {
					System.out.println(stumeiPdf.getPath());

					Process process0=  Runtime.getRuntime().exec("which acroread xpdf");
					InputStream is = process0.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String answer;
					String cmd=null;
					while ( (answer = br.readLine()) !=null) {
						cmd = answer;
					}
					if (cmd != null)  {
						pdfexe.acroread = cmd;

						//process = pdfexe.openDocument(rtn, true);  //could not execute why?
						process = Runtime.getRuntime().exec(cmd + " "+ stumeiPdf.getPath());
// del s.inoue 2009/09/17
//						try {
//							process.waitFor();
//						} catch(InterruptedException ie) {
//						}
//						is = process.getErrorStream();
//						isr = new InputStreamReader(is);
//						br = new BufferedReader(isr);
//						while ( (answer = br.readLine()) !=null) {
//							System.out.println(answer);
//						}
					}
				}
				else process = pdfexe.openDocument(stumeiPdf, true);

				if (process==null || process.exitValue() == 1) {
//		     		if (process.exitValue() == 1) {
						JErrorMessage.show("M9727", ParentFrame);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
				JErrorMessage.show("M5203", this);
			}
		}
	}

	// add h.yoshitama 2009/11/26
	/**
	 * �S�đI���{�^���������ꂽ�ꍇ
	 */
	public void pushedAllSelectButton( ActionEvent e )
	{
		for(int i = 0;i < modelfixed.getRowCount();i++ )
		{
			if( isAllSelect )
				modelfixed.setData(true, i, 0);
			else
				modelfixed.setData(false, i, 0);
		}

		if( isAllSelect )
		{
			isAllSelect = false;
		}else{
			isAllSelect = true;
		}
	}

	// add h.yoshitama 2009/11/11
	public void itemStateChanged(ItemEvent e) {
		if ((e.getSource()) == jCheckBox_IsKekkaInput) {
			if( !jCheckBox_IsKekkaInput.isSelected() )
			{
				/* ���f���{�� */
				jTextField_KensaBeginDate.setEditable(false);
				jTextField_KensaBeginDate.setEnabled(false);
				jTextField_KensaBeginDate.setText("");
				jTextField_KensaEndDate.setEditable(false);
				jTextField_KensaEndDate.setEnabled(false);
				jTextField_KensaEndDate.setText("");
				/* ����� */
				jTextField_HanteiBeginDate.setEditable(false);
				jTextField_HanteiBeginDate.setEnabled(false);
				jTextField_HanteiBeginDate.setText("");
				jTextField_HanteiEndDate.setEditable(false);
				jTextField_HanteiEndDate.setEnabled(false);
				jTextField_HanteiEndDate.setText("");
				/* ���ʒʒm�� */
				jTextField_TsuuchiBeginDate.setEditable(false);
				jTextField_TsuuchiBeginDate.setEnabled(false);
				jTextField_TsuuchiBeginDate.setText("");
				jTextField_TsuuchiEndDate.setEditable(false);
				jTextField_TsuuchiEndDate.setEnabled(false);
				jTextField_TsuuchiEndDate.setText("");
			} else {
				/* ���f���{�� */
				jTextField_KensaBeginDate.setEditable(true);
				jTextField_KensaBeginDate.setEnabled(true);
				jTextField_KensaEndDate.setEditable(true);
				jTextField_KensaEndDate.setEnabled(true);
				/* ����� */
				jTextField_HanteiBeginDate.setEditable(true);
				jTextField_HanteiBeginDate.setEnabled(true);
				jTextField_HanteiEndDate.setEditable(true);
				jTextField_HanteiEndDate.setEnabled(true);
				/* ���ʒʒm�� */
				jTextField_TsuuchiBeginDate.setEditable(true);
				jTextField_TsuuchiBeginDate.setEnabled(true);
				jTextField_TsuuchiEndDate.setEditable(true);
				jTextField_TsuuchiEndDate.setEnabled(true);

			}
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		int mod = keyEvent.getModifiersEx();

		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F7:
			logger.info(jButton_Serach.getText());
			pushedMetaboButton(null);break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Print.getText());
			pushedPrintButton(null);break;
		case KeyEvent.VK_F11:
			logger.info(jButton_PrintSetsumei.getText());
			pushePrintSetsumei(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_OK.getText());
			pushedOKButton(null);break;
//		case KeyEvent.VK_S:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_Serach.getText());
//				pushedSearchButton( null );
//			}
//			break;
		}

	}

	// edit s.inoue 2009/10/26
	// �ꗗ�p�f�[�^�擾
	private Object[][] resultRefresh()
	{
		// del s.inoue 2009/10/23
		// model.clearAllRow();
		// modelfixed.clearAllRow();
		// add s.inoue 2009/10/23
		/* �����p��SQL���擾����B */
		boolean validateflg = true;
		Object[][] rowcolumn = null;
		String calSelect = (jComboBox_HanteiResult.getSelectedItem() == null) ? "":(String)jComboBox_HanteiResult.getSelectedItem();

		// edit s.inoue 2010/02/23
		/* �R���{�{�b�N�X�őI������Ă���ی��Ҕԍ��A���̂��擾����B */
		String hokenjaNumberAndNameString = (String)jComboBox_HokenjyaNumber.getSelectedItem();
		String hokenjaNumber = "";
		String hokenjaName = "";
		if (hokenjaNumberAndNameString != null && ! hokenjaNumberAndNameString.isEmpty()) {
			String[] hokenjaNumberAndName = hokenjaNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
			hokenjaNumber = hokenjaNumberAndName[0];

			if (hokenjaNumberAndName.length > 1) {
				hokenjaName = hokenjaNumberAndName[1];
			}
		}

		// edit s.inoue 2009/10/27
		// �G���[���̏����ǉ�
			validateflg = validatedData.setJyushinkenID(jTextField_Jyusinken_ID.getText()) &&
			validatedData.setHihokenjyaCode(  jTextField_HokensyoCode.getText()) &&
			validatedData.setHihokenjyaNumber(hokenjaNumber) &&
			validatedData.setHokenjyaNumber(  jTextField_HokenjyaNumber.getText()) &&
			validatedData.setName(jTextField_Name.getText()) &&
			validatedData.setKensaBeginDate(jTextField_KensaBeginDate.getText()) &&
			validatedData.setKensaEndDate(jTextField_KensaEndDate.getText()) &&
			validatedData.setHanteiBeginDate(jTextField_HanteiBeginDate.getText()) &&
			validatedData.setHanteiEndDate(jTextField_HanteiEndDate.getText()) &&
			validatedData.setTsuuchiBeginDate(jTextField_TsuuchiBeginDate.getText()) &&
			validatedData.setTsuuchiEndDate(jTextField_TsuuchiEndDate.getText()) &&
			validatedData.setHokenSidouLevel( calSelect);
			// edit s.inoue 2009/10/28
			if(!validateflg)
				return null;

			//�R���{�{�b�N�X�̏����ݒ�
			jComboBox_HanteiResult.addItem("�w�薳��");
			jComboBox_HanteiResult.addItem("������");
			jComboBox_HanteiResult.addItem("�ϋɓI�x��");
			jComboBox_HanteiResult.addItem("���@�Â��x��");
			jComboBox_HanteiResult.addItem("�Ȃ��i���񋟁j");
			jComboBox_HanteiResult.addItem("����s�\");

			String query = getSearchSql();
			try{
				searchResult = JApplication.kikanDatabase.sendExecuteQuery(query);
			}catch(SQLException ex){
				ex.printStackTrace();
			}

			rowcolumn = new Object[searchResult.size()][19];

				try{

					for( int i = 0;i < searchResult.size();i++ )
					{
						Hashtable<String,String> ResultItem = searchResult.get(i);

						/* �e�[�u���ɒǉ��ς݂̎�tID�̏ꍇ�́A�l������ݒ肷��B */
						String uketukeId = ResultItem.get("UKETUKE_ID");
						String koumokuCode = ResultItem.get("KOUMOKU_CD");
						String kekkati = ResultItem.get("KEKA_TI");

						/* ���f���{�� */
						String kensaNengapi = ResultItem.get("KENSA_NENGAPI");
						// del s.inoue 2009/10/23
						// String[] rowfixed = new String[4];
						// String[] row = new String[12];

						// edit s.inoue 2009/10/23
						// Arrays.fill(row, "");
						// Arrays.fill(rowcolumn, "");

							// edit s.inoue 2009/10/23
							// rowfixed[COLUMN_INDEX_CHECK] = null;
							rowcolumn[i][COLUMN_INDEX_CHECK] = null;
							/* �N�x */
							// rowfixed[COLUMN_INDEX_NENDO] = ResultItem.get("NENDO");
							rowcolumn[i][COLUMN_INDEX_NENDO] = ResultItem.get("NENDO");
							/* ��f�������ԍ� */
							// rowfixed[COLUMN_INDEX_JYUSHIN_SEIRI_NO] = ResultItem.get("JYUSHIN_SEIRI_NO");
							rowcolumn[i][COLUMN_INDEX_JYUSHIN_SEIRI_NO] = ResultItem.get("JYUSHIN_SEIRI_NO");
							/* �J�i���� */
							// rowfixed[COLUMN_INDEX_KANANAME] = ResultItem.get("KANANAME");
							rowcolumn[i][COLUMN_INDEX_KANANAME] = ResultItem.get("KANANAME");
							/* ���� */
							// row[COLUMN_INDEX_NAME] = ResultItem.get("NAME");
							rowcolumn[i][COLUMN_INDEX_NAME] = ResultItem.get("NAME");
							// del s.inoue 2009/10/31
//							/* �Z�� */
//							// row[COLUMN_INDEX_HOME_ADRS] = ResultItem.get("HOME_ADRS");
//							rowcolumn[i][COLUMN_INDEX_HOME_ADRS] = ResultItem.get("HOME_ADRS");
							/* �a���� */
							// row[COLUMN_INDEX_BIRTHDAY] = ResultItem.get("BIRTHDAY");
							rowcolumn[i][COLUMN_INDEX_BIRTHDAY] = ResultItem.get("BIRTHDAY");
							/* ���� */
							// row[COLUMN_INDEX_SEX] = ResultItem.get("SEX").equals("1") ? "�j��" : "����";
							rowcolumn[i][COLUMN_INDEX_SEX] = ResultItem.get("SEX").equals("1") ? "�j��" : "����";
							/* ���� */
							String inputFlag = ResultItem.get("KEKA_INPUT_FLG");

							/* �ی��w�����x���̏ꍇ */
							if (koumokuCode.equals(CODE_HOKENSHIDOU_LEVEL)) {
								// edit s.inoue 2009/10/23
								rowcolumn[i][COLUMN_INDEX_HOKENSHIDOU_LEVEL] = JKekkaListFrameData.getHokenshidowLebelDisplayName(kekkati);
								// row[COLUMN_INDEX_HOKENSHIDOU_LEVEL] = JKekkaListFrameData.getHokenshidowLebelDisplayName(kekkati);
							}
							/* ���^�{���b�N�V���h���[������̏ꍇ */
							else if(koumokuCode.equals(CODE_METABO_HANTEI)){
								// edit s.inoue 2009/10/23
								rowcolumn[i][COLUMN_INDEX_METBO_HANTEI] = JKekkaListFrameData.getMetaboHanteiDisplayName(kekkati);
								// row[COLUMN_INDEX_METBO_HANTEI] = JKekkaListFrameData.getMetaboHanteiDisplayName(kekkati);
							}

							/* ���f���{�� */
							// row[COLUMN_INDEX_KENSA_NENGAPI] = kensaNengapi;
							rowcolumn[i][COLUMN_INDEX_KENSA_NENGAPI] = kensaNengapi;
							// row[COLUMN_INDEX_INPUT_FLAG] = inputFlag.equals("2") ?  "��" : "��";
							rowcolumn[i][COLUMN_INDEX_INPUT_FLAG] = inputFlag.equals("2") ?  "��" : "��";
							/* ����N���� */
							// row[COLUMN_INDEX_HANTEI_NENGAPI] = ResultItem.get("HANTEI_NENGAPI");
							rowcolumn[i][COLUMN_INDEX_HANTEI_NENGAPI] = ResultItem.get("HANTEI_NENGAPI");
							/* �ʒm�N���� */
							// row[COLUMN_INDEX_TUTI_NENGAPI] = new String(ResultItem.get("TUTI_NENGAPI"));
							rowcolumn[i][COLUMN_INDEX_TUTI_NENGAPI] = new String(ResultItem.get("TUTI_NENGAPI"));

							// add s.inoue 2009/10/31
							/* ��ی��ҏؓ��L�� */
							rowcolumn[i][COLUMN_INDEX_HIHOKENSHA_KIGO] = new String(ResultItem.get("HIHOKENJYASYO_KIGOU"));
							/* ��ی��ҏؓ��ԍ� */
							rowcolumn[i][COLUMN_INDEX_HIHOKENSHA_NO] = new String(ResultItem.get("HIHOKENJYASYO_NO"));

							// edit s.inoue 2009/11/02
							rowcolumn[i][COLUMN_INDEX_HKNJANUM] = new String(ResultItem.get("HKNJANUM"));
							rowcolumn[i][COLUMN_INDEX_DAIKOU] = new String(ResultItem.get("SIHARAI_DAIKOU_BANGO"));

							/* �����R�����g */
							// row[COLUMN_INDEX_KOMENTO] = new String(ResultItem.get("KOMENTO"));
							rowcolumn[i][COLUMN_INDEX_KOMENTO] = new String(ResultItem.get("KOMENTO"));
							/* ��tID */
							// row[COLUMN_INDEX_UKETUKE_ID] = uketukeId;
							rowcolumn[i][COLUMN_INDEX_UKETUKE_ID] = uketukeId;
					}
	// move s.inoue 2009/10/26
	//				/* ���^�{���b�N�V���h���[�����茋�ʎ擾�pSQL�𔭍s����B */
	//				ArrayList<Hashtable<String,String>> shidouSearchResult =
	//					JApplication.kikanDatabase.sendExecuteQuery(getMetaboHanteiSql());
	//
	//				for( int i = 0;i < shidouSearchResult.size();i++ ){
	//					Hashtable<String, String> shidowItem = shidouSearchResult.get(i);
	//
	//					String uketukeId = shidowItem.get("UKETUKE_ID");
	//					String kensaNengapi = shidowItem.get("KENSA_NENGAPI");
	//					String kekkati = shidowItem.get("KEKA_TI");
	//					/* �e�[�u������A�Y������s���������A���^�{���b�N�V���h���[�����茋�ʂ�ݒ肷��B */
	//					for (int j = 0; j < this.table.getRowCount(); j++) {
	//						String tableUketukeId = (String)this.table.getData(j, COLUMN_INDEX_UKETUKE_ID);
	//						String tableKensaNengapi = (String)this.table.getData(j, COLUMN_INDEX_KENSA_NENGAPI);
	//
	//						if (uketukeId.equals(tableUketukeId) && kensaNengapi.equals(tableKensaNengapi)) {
	//							String metaboHanteiText = JKekkaListFrameData.getMetaboHanteiDisplayName(kekkati);
	//							this.table.setData(metaboHanteiText, j, COLUMN_INDEX_METBO_HANTEI);
	//							break;
	//						}
	//					}
	//				}
			}catch(Exception f){
				f.printStackTrace();
				JErrorMessage.show("M4930", this);
			}

		return rowcolumn;
	}

	/*
	 * ���^�{���茋�ʐݒ�
	 */
	private void hanteiSetting(){

		try{
			/* ���^�{���b�N�V���h���[�����茋�ʎ擾�pSQL�𔭍s����B */
			ArrayList<Hashtable<String,String>> shidouSearchResult =
				JApplication.kikanDatabase.sendExecuteQuery(getMetaboHanteiSql());

			for( int i = 0;i < shidouSearchResult.size();i++ ){
				Hashtable<String, String> shidowItem = shidouSearchResult.get(i);

				String uketukeId = shidowItem.get("UKETUKE_ID");
				String kensaNengapi = shidowItem.get("KENSA_NENGAPI");
				String kekkati = shidowItem.get("KEKA_TI");
				/* �e�[�u������A�Y������s���������A���^�{���b�N�V���h���[�����茋�ʂ�ݒ肷��B */
				for (int j = 0; j < this.table.getRowCount(); j++) {
					String tableUketukeId = (String)this.table.getData(j, COLUMN_INDEX_UKETUKE_ID);
					String tableKensaNengapi = (String)this.table.getData(j, COLUMN_INDEX_KENSA_NENGAPI);

					if (uketukeId.equals(tableUketukeId) && kensaNengapi.equals(tableKensaNengapi)) {
						String metaboHanteiText = JKekkaListFrameData.getMetaboHanteiDisplayName(kekkati);
						this.table.setData(metaboHanteiText, j, COLUMN_INDEX_METBO_HANTEI);
						break;
					}
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}


// del s.inoue 2009/10/26
// ����������������������������������������������������

//	public void resultRefresh()
//	{
//		model.clearAllRow();
//		modelfixed.clearAllRow();
//
//		if(
//				validatedData.setJyushinkenID(jTextField_Jyusinken_ID.getText()) &&
//				validatedData.setHihokenjyaCode(  jTextField_HokensyoCode.getText()) &&
//				validatedData.setHihokenjyaNumber(jTextField_HokensyoNumber.getText()) &&
//				validatedData.setHokenjyaNumber(  jTextField_HokenjyaNumber.getText()) &&
//				validatedData.setName(jTextField_Name.getText()) &&
//				validatedData.setKensaBeginDate(jTextField_KensaBeginDate.getText()) &&
//				validatedData.setKensaEndDate(jTextField_KensaEndDate.getText()) &&
//				validatedData.setHanteiBeginDate(jTextField_HanteiBeginDate.getText()) &&
//				validatedData.setHanteiEndDate(jTextField_HanteiEndDate.getText()) &&
//				validatedData.setTsuuchiBeginDate(jTextField_TsuuchiBeginDate.getText()) &&
//				validatedData.setTsuuchiEndDate(jTextField_TsuuchiEndDate.getText()) &&
//				validatedData.setHokenSidouLevel((String)jComboBox_HanteiResult.getSelectedItem())
//		){
//			/* �����p��SQL���擾����B */
//			String query = getSearchSql();
//			try{
//				searchResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//
//				for( int i = 0;i < searchResult.size();i++ )
//				{
//					Hashtable<String,String> ResultItem = searchResult.get(i);
//
//					/* �e�[�u���ɒǉ��ς݂̎�tID�̏ꍇ�́A�l������ݒ肷��B */
//					String uketukeId = ResultItem.get("UKETUKE_ID");
//					String koumokuCode = ResultItem.get("KOUMOKU_CD");
//					String kekkati = ResultItem.get("KEKA_TI");
//
//					/* ���f���{�� */
//					String kensaNengapi = ResultItem.get("KENSA_NENGAPI");
//					String[] rowfixed = new String[4];
//					String[] row = new String[12];
//
//					Arrays.fill(row, "");
//
//						/* �ی��w�����x���̏ꍇ */
//						if (koumokuCode.equals(CODE_HOKENSHIDOU_LEVEL)) {
//							row[COLUMN_INDEX_HOKENSHIDOU_LEVEL] = JKekkaListFrameData.getHokenshidowLebelDisplayName(kekkati);
//						}
//						/* ���^�{���b�N�V���h���[������̏ꍇ */
//						else if(koumokuCode.equals(CODE_METABO_HANTEI)){
//							row[COLUMN_INDEX_METBO_HANTEI] = JKekkaListFrameData.getMetaboHanteiDisplayName(kekkati);
//						}
//
//						rowfixed[COLUMN_INDEX_CHECK] = null;
//						/* �N�x */
//						rowfixed[COLUMN_INDEX_NENDO] = ResultItem.get("NENDO");
//						/* ��f�������ԍ� */
//						rowfixed[COLUMN_INDEX_JYUSHIN_SEIRI_NO] = ResultItem.get("JYUSHIN_SEIRI_NO");
//						/* �J�i���� */
//						rowfixed[COLUMN_INDEX_KANANAME] = ResultItem.get("KANANAME");
//						/* ���� */
//						row[COLUMN_INDEX_NAME] = ResultItem.get("NAME");
//						/* �Z�� */
//						row[COLUMN_INDEX_HOME_ADRS] = ResultItem.get("HOME_ADRS");
//						/* �a���� */
//						row[COLUMN_INDEX_BIRTHDAY] = ResultItem.get("BIRTHDAY");
//						/* ���� */
//						row[COLUMN_INDEX_SEX] = ResultItem.get("SEX").equals("1") ? "�j��" : "����";
//						/* ���f���{�� */
//						row[COLUMN_INDEX_KENSA_NENGAPI] = kensaNengapi;
//						/* ���� */
//						String inputFlag = ResultItem.get("KEKA_INPUT_FLG");
//						row[COLUMN_INDEX_INPUT_FLAG] = inputFlag.equals("2") ?  "��" : "��";
//						/* ����N���� */
//						row[COLUMN_INDEX_HANTEI_NENGAPI] = ResultItem.get("HANTEI_NENGAPI");
//						/* �ʒm�N���� */
//						row[COLUMN_INDEX_TUTI_NENGAPI] = new String(ResultItem.get("TUTI_NENGAPI"));
//						/* �����R�����g */
//						row[COLUMN_INDEX_KOMENTO] = new String(ResultItem.get("KOMENTO"));
//						/* ��tID */
//						row[COLUMN_INDEX_UKETUKE_ID] = uketukeId;
//
//						model.addData(row);
//						modelfixed.addData(rowfixed);
//				}
//
//				/* ���^�{���b�N�V���h���[�����茋�ʎ擾�pSQL�𔭍s����B */
//				ArrayList<Hashtable<String,String>> shidouSearchResult =
//					JApplication.kikanDatabase.sendExecuteQuery(getMetaboHanteiSql());
//
//				for( int i = 0;i < shidouSearchResult.size();i++ ){
//					Hashtable<String, String> shidowItem = shidouSearchResult.get(i);
//
//					String uketukeId = shidowItem.get("UKETUKE_ID");
//					String kensaNengapi = shidowItem.get("KENSA_NENGAPI");
//					String kekkati = shidowItem.get("KEKA_TI");
//
//					/* �e�[�u������A�Y������s���������A���^�{���b�N�V���h���[�����茋�ʂ�ݒ肷��B */
//					for (int j = 0; j < this.model.getRowCount(); j++) {
//						String tableUketukeId = (String)this.model.getData(j, COLUMN_INDEX_UKETUKE_ID);
//						String tableKensaNengapi = (String)this.model.getData(j, COLUMN_INDEX_KENSA_NENGAPI);
//
//						if (uketukeId.equals(tableUketukeId) && kensaNengapi.equals(tableKensaNengapi)) {
//							String metaboHanteiText = JKekkaListFrameData.getMetaboHanteiDisplayName(kekkati);
//							this.model.setData(metaboHanteiText, j, COLUMN_INDEX_METBO_HANTEI);
//
//							break;
//						}
//					}
//				}
//			}catch(SQLException f){
//				f.printStackTrace();
//				JErrorMessage.show("M4930", this);
//			}
//		}
//	}

}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


