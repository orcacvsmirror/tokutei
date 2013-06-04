//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Component;
//import java.awt.event.*;
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.util.*;
//
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JComboBox;
//import javax.swing.JComponent;
//import javax.swing.JEditorPane;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.JViewport;
//import javax.swing.ListSelectionModel;
//
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKekkaRegisterFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintKekka;
//import jp.or.med.orca.jma_tokutei.common.scene.JScene;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.JShowResultFrameDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TDataTypeCodeDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
//import jp.or.med.orca.jma_tokutei.common.sql.model.JShowResultFrameModel;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TDataTypeCode;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
//
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableColumnModel;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
//import org.apache.log4j.Logger;
//import org.openswing.swing.client.GridControl;
//
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//
////add h.yoshitama 2009/09/30
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
//
///**
// * ���f���ʕ\����ʐ���@
// */
//public class JShowResultFrameCtrl extends JShowResultFrame {
//
//	private static final int COLUMN_INDEX_KOUMOKU_NAME = 0;
//	private static final int COLUMN_INDEX_KENSA_HOUHOU = 1;
//	private static final int COLUMN_INDEX_KENSA_KEKA_NUM = 2;
//	private static final int COLUMN_INDEX_KENSA_KEKA_CODE = 3;
//	private static final int COLUMN_INDEX_KENSA_KEKA_STR = 4;
//	private static final int COLUMN_INDEX_KENSA_JISIKBN = 5;
//	private static final int COLUMN_INDEX_KIJUN_DS_KAGEN = 6;
//	private static final int COLUMN_INDEX_KIJUN_DS_JYOGEN = 7;
//	private static final int COLUMN_INDEX_KIJUN_JS_KAGEN = 8;
//	private static final int COLUMN_INDEX_KIJUN_JS_JYOGEN = 9;
//	private static final int COLUMN_INDEX_HL = 10;
//	private static final int COLUMN_INDEX_HANTEI = 11;
//	private static final int COLUMN_INDEX_COMENT = 12;
//
//	// �Œ荀�ڕύX
//	private static final String[] header = { "���ږ�","�������@","���ʁi���l�j","���ʁi�R�[�h�j","���ʁi������j" ,
//		"���{�敪","��l�����i�j���j", "��l����i�j���j", "��l�����i�����j",
//		"��l����i�����j", "H/L", "����", "�R�����g"};
//
//	private static final String[] fieldKeys = { "KOUMOKU_NAME", "KENSA_HOUHOU",
//			"KEKA_TI_DECIMAL", "KEKA_TI_CODE", "KEKA_TI_STRING", "JISI_KBN","DS_KAGEN",
//			"DS_JYOUGEN", "JS_KAGEN", "JS_JYOUGEN", "H_L", "HANTEI", "KOMENT"};
//
//	private static final String[] hokenShidouLevelTable = { "������", "�ϋɓI�x��",
//			"���@�Â��x��",
////			"����",
//			"�Ȃ��i���񋟁j",
//			"����s�\" };
//
//	private static final String[] metaboHanteiTable = { "������", "��Y��",
//			"�\���Q�Y��",
//			"��Y��", "����s�\" };
//
//	/**
//	 * ���^�{����̍��ڃR�[�h
//	 */
//	private static final String METABO_HANTEI_KOUMOKU_CD = "9N501000000000011";
//
//	/**
//	 * �ی��w�����x���̍��ڃR�[�h
//	 */
//	private static final String HOKEN_LEVEL_KOUMOKU_CD = "9N506000000000011";
//	private TreeMap<String, JComponent> fieldComponentMap;
//	private List<TDataTypeCode> dataTypeCodes;
//	// private List<TreeMap<String, String>> kojinData;
//	private GridControl grid = null;
//
//	private int curIndex;
//	Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
//	private JShowResultFrameDao jShowResultFrameDao = null;
//	private TKensakekaSonotaDao kensakekaSonotaDao = null;
//	private TKensakekaTokuteiDao kensakekaTokuteiDao = null;
//	private TDataTypeCodeDao dataTypeCodeDao = null;
//	/** ���f�p�^�[���ԍ� */
//	private int kenshinPatternNumber = 0;
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	private static org.apache.log4j.Logger logger = Logger.getLogger(JSearchResultListFrameCtrl.class);
//
//	private DefaultTableModel dmodel = null;
//	private TableRowSorter<TableModel> sorter =null;
//
//	private JSimpleTable table=null;
//	private JSimpleTable modelfixed= null;
//	Object[][] rowcolumn = null;
//	// add s.inoue 2009/12/24
//	private IDialog pageSelectDialog;
//	private JHanteiSearchResultListFrameData vo = null;
//	private int rowCount = 0;
//
//	/**
//	 * �R���X�g���N�^
//	 * @param recordList �������ʃ��R�[�h���X�g
//	 * @param curIndex   �O��ʂőI�����ꂽ���R�[�h�̃C���f�b�N�X
//	 */
//	public JShowResultFrameCtrl(
//			// eidt s.inoue 2011/03/30
//			// List<TreeMap<String, String>> recordList,
//			GridControl p_grid,
//			int curIndex) {
//		// eidt s.inoue 2011/03/30
//		// kojinData = recordList;
//		this.grid = p_grid;
//		this.curIndex = curIndex;
//
//		rowCount = grid.getVOListTableModel().getRowCount();
//		vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(curIndex);
//
//		// �R���|�[�l���g�̃Z�b�g�A�b�v
//		setUpComponent();
//		// DAO�Z�b�g�A�b�v
//		setUpDao();
//		// �\��
//		refresh();
//		// �e�[�u��������
//		initializeSetting();
//
//		// edit s.inoue 2010/04/29
//		if (curIndex > 0) {
//			jButton_Prev.setEnabled(true);
//		}else if (curIndex == 0){
//			jButton_Prev.setEnabled(false);
//		}
//		// eidt s.inoue 2011/03/30
//		// if (curIndex < kojinData.size() - 1) {
//		if (curIndex < rowCount) {
//			jButton_Next.setEnabled(true);
//		// }else if (curIndex == kojinData.size() - 1){
//		}else if (curIndex == rowCount){
//			jButton_Next.setEnabled(false);
//		}
//	}
//
//	/**
//	 * �I���{�^���������R�[���o�b�N
//	 * @param e �A�N�V�����C�x���g
//	 */
//	public void pushedEndButton(ActionEvent e) {
//		dispose();
//	}
//
////	/*
////	 * ����@�\
////	 * ���茒�f���ʒʒm
////	 * import Print.*;
////	 * import PrintData.*;
////	 *
////	 * 1�y�[�W
////	 * �\��
////	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
////	 * import	Print.Kekka_P1
////	 * class	Kekka_P1
////	 *
////	 * 2�y�[�W
////	 * ���f���ʁi�ڍׁj
////	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
////	 * import	Print.Kekka_P2
////	 * class	Kekka_P2
////	 *
////	 * 3�y�[�W
////	 * ���f���ʁi�ꗗ�j
////	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
////	 * import	Print.Kekka_P3
////	 * class	Kekka_P3
////	 *
////	 *
////	 * 4�y�[�W
////	 * ���f���ʁi��f�j
////	 * �K�{�f�[�^�F��f���A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
////	 * import	Print.Kekka_P4
////	 * class	Kekka_P4
////	 *
////	 */
////	public void pushedPrintButton(ActionEvent e) {
////		TreeMap<String, String> kojinDataItem = kojinData.get(curIndex);
////		String uketukeId = kojinDataItem.get("UKETUKE_ID");
////		String hihokenjyasyoKigou = kojinDataItem.get("HIHOKENJYASYO_KIGOU");
////		String hihokenjyasyoNo = kojinDataItem.get("HIHOKENJYASYO_NO");
////		String kensaNengapi = kojinDataItem.get("KENSA_NENGAPI");
////		String hokenjyaNo = kojinDataItem.get("HKNJANUM");
////
////		// add s.inoue 2009/12/24
////		// A4-1��,2���I��
////		pageSelectDialog = DialogFactory.getInstance().createDialog(this);
////		// ������@�I���_�C�A���O��\��
////		pageSelectDialog.setMessageTitle("������@�I�����");
////		pageSelectDialog.setVisible(true);
////
////		int pageSelect = (pageSelectDialog.getPrintSelect()==2) ?2:1;
////
////		/* ���f���ʒʒm�\���������B */
////		PrintKekka kekka = new PrintKekka();
////		kekka.printResultCard(kensaNengapi, uketukeId, hihokenjyasyoKigou,
////				hihokenjyasyoNo, hokenjyaNo, this, pageSelect);
////	}
////
////	/**
////	 * �C���{�^���������R�[���o�b�N
////	 * @param �A�N�V�����C�x���g
////	 */
////	public void pushedFixButton(ActionEvent e) {
////		// edit s.inoue 20110325
////		// JKekkaListFrameData srcData = new JKekkaListFrameData();
////		JKenshinKekkaSearchListFrameData srcData = new JKenshinKekkaSearchListFrameData();
////
////		TreeMap<String, String> map = kojinData.get(curIndex);
////
////		String uketukeId = map.get("UKETUKE_ID");
////		String kigou = map.get("HIHOKENJYASYO_KIGOU");
////		String no = map.get("HIHOKENJYASYO_NO");
////		String kanaName = map.get("KANANAME");
////		String hokenjaNumber = map.get("HKNJANUM");
////
////		String sex = map.get("SEX");
////		String birthDay = map.get("BIRTHDAY");
////		String age = map.get("");
////
////// edit s.inoue 20110325
//////		srcData.setUketuke_id(uketukeId);
//////		srcData.setHihokenjyaCode(kigou);
//////		srcData.setHihokenjyaNumber(no);
//////		srcData.setName(kanaName);
//////		srcData.setHokenjyaNumber(hokenjaNumber);
//////		srcData.setSex(sex);
//////		srcData.setBirthDay(birthDay);
////		srcData.setUKETUKE_ID(uketukeId);
////		srcData.setHIHOKENJYASYO_KIGOU(kigou);
////		srcData.setHIHOKENJYASYO_NO(no);
////		srcData.setNAME(kanaName);
////		srcData.setHIHOKENJYASYO_NO(hokenjaNumber);
////		srcData.setSEX(sex);
////		srcData.setBIRTHDAY(birthDay);
////
////		JScene.CreateDialog(
////				this,
////				new JKekkaRegisterFrameCtrl(
////						srcData,
////						// edit ver2 s.inoue 2009/08/26
////						this.jTextField_Date.getText(),
////						// map.get("KENSA_NENGAPI"),
////						false),
////				new WindowRefreshEvent());
////	}
//
//	/**
//	 * ���{�^���������R�[���o�b�N
//	 * @param e �A�N�V�����C�x���g
//	 */
//	public void pushedNextButton(ActionEvent e) {
//		// eidt s.inoue 2011/03/30
//		// if (curIndex < kojinData.size() - 1) {
//		if (curIndex < rowCount) {
//			// edit s.inoue 2009/12/07
//			jButton_Next.setEnabled(true);
//			jButton_Prev.setEnabled(true);
//
//			curIndex++;
//			// �R���|�[�l���g�̃Z�b�g�A�b�v
//			setUpComponent();
//			// �\��
//			refresh();
//			// �e�[�u��������
//			searchRefresh();
//		// edit s.inoue 2009/12/07
//		// }else if (curIndex == kojinData.size() - 1){
//		}else if (curIndex == rowCount){
//			jButton_Next.setEnabled(false);
//		}
//	}
//
//	/**
//	 * �O�{�^���������R�[���o�b�N
//	 * @param e �A�N�V�����C�x���g
//	 */
//	public void pushedPrevButton(ActionEvent e) {
//		if (curIndex > 0) {
//			// edit s.inoue 2009/12/07
//			jButton_Next.setEnabled(true);
//			jButton_Prev.setEnabled(true);
//
//			curIndex--;
//			// edit s.inoue 2009/11/18
//			// �R���|�[�l���g�̃Z�b�g�A�b�v
//			setUpComponent();
//			// �\��
//			refresh();
//			// �e�[�u��������
//			searchRefresh();
//		// edit s.inoue 2009/12/07
//		}else if (curIndex == 0){
//			jButton_Prev.setEnabled(false);
//		}
//
//	}
//
//	/**
//	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
//	 */
//	public class WindowRefreshEvent extends WindowAdapter {
//		public void windowClosed(WindowEvent e) {
//			// del s.inoue 2009/11/17
//			//�e�[�u���̍ēǂݍ��݂��s��
//			// edit s.inoue 2009/11/18
//			// �R���|�[�l���g�̃Z�b�g�A�b�v
//			setUpComponent();
//			// �\��
//			refresh();
//			// �e�[�u��������
//			searchRefresh();
//		}
//	}
//
//	/**
//	 * �A�N�V�����C�x���g�R�[���o�b�N
//	 * @param e �A�N�V�����C�x���g
//	 */
//	public void actionPerformed(ActionEvent e) {
//		Object soruce = (e.getSource());
//		if (soruce == jButton_End) {
//			logger.info(jButton_End.getText());
//			pushedEndButton(e);
//		}
//		else if (soruce == jButton_Print) {
//			logger.info(jButton_Print.getText());
////			pushedPrintButton(e);
//		}
//		else if (soruce == jButton_Fix) {
//			logger.info(jButton_Fix.getText());
////			pushedFixButton(e);
//		}
//		else if (soruce == jButton_Next) {
//			logger.info(jButton_Next.getText());
//			pushedNextButton(e);
//		}
//		else if (soruce == jButton_Prev) {
//			logger.info(jButton_Prev.getText());
//			pushedPrevButton(e);
//		}
//	}
//
//	/**
//	 * ��ʂ̊e�R���|�[�l���g�̃v���p�e�B��ݒ肵�A
//	 * �R���|�[�l���g���E�C���h�E��ɔz�u����
//	 */
//	private void setUpComponent() {
//
//		//�ی��w�����x���R���{�{�b�N�X�̏����ݒ�
//		for (String item : hokenShidouLevelTable) {
//			jComboBox_HokenSidouLevel.addItem(item);
//		}
//		//���^�{���b�N�V���h���[������R���{�{�b�N�X�̏����ݒ�
//		for (String item : metaboHanteiTable) {
//			jComboBox_MetaboHantei.addItem(item);
//		}
//
//		// TextField,EditorPane��ҏW�s�ɐݒ肷��
//		jTextField_HihokenjyaCode.setEditable(false);
//		jTextField_HihokenjyaNumber.setEditable(false);
//		jTextField_Name.setEditable(false);
//		jTextField_PatternName.setEditable(false);
//		jTextField_KensaCenterName.setEditable(false);
//		jTextField_Date.setEditable(false);
//		jEditorPane_Comment.setEditable(false);
//
//		// ���R�[�h�t�B�[���h���ƃf�[�^���Z�b�g����R���|�[�l���g��
//		// �֘A�t����}�b�v�𐶐�����
//		fieldComponentMap = new TreeMap<String, JComponent>();
//		fieldComponentMap.put("HIHOKENJYASYO_KIGOU", jTextField_HihokenjyaCode);
//		fieldComponentMap.put("HIHOKENJYASYO_NO", jTextField_HihokenjyaNumber);
//		fieldComponentMap.put("KANANAME", jTextField_Name);
//		fieldComponentMap.put("K_P_NO", jTextField_PatternName);
//		fieldComponentMap.put("KENSA_CENTER_CD", jTextField_KensaCenterName);
//		fieldComponentMap.put("KENSA_NENGAPI", jTextField_Date);
//
//		initilizeFocus();
//
//	}
//
//	private void initializeSetting(){
//		dmodel = new DefaultTableModel(setUpRecordTable(),header){
//	      public boolean isCellEditable(int row, int column) {
//	    	return false;}
//	    };
//
//		sorter = new TableRowSorter<TableModel>(dmodel);
//		table = new JSimpleTable(dmodel);
//		modelfixed = new JSimpleTable(dmodel);
//
//		modelfixed.setPreferedColumnWidths(new int[]{150, 150});
//		table.setPreferedColumnWidths(new int[] { 80, 80, 80, 80, 80, 80, 80, 80, 50, 120, 120 });
//
//		modelfixed.setSelectionModel(table.getSelectionModel());
//
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	    table.setRowSorter(sorter);
//	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    modelfixed.setRowSorter(sorter);
//	    modelfixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    modelfixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    initilizeFocus();
//		// add s.inoue 2009/12/01
//		functionListner();
//
//        final JScrollPane scroll = new JScrollPane(table);
//        JViewport viewport = new JViewport();
//        viewport.setView(modelfixed);
//        viewport.setPreferredSize(modelfixed.getPreferredSize());
//        scroll.setRowHeader(viewport);
//
//        modelfixed.setPreferredScrollableViewportSize(modelfixed.getPreferredSize());
//        scroll.setRowHeaderView(modelfixed);
//        scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, modelfixed.getTableHeader());
//
//        scroll.getRowHeader().addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                JViewport viewport = (JViewport) e.getSource();
//                scroll.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
//            }
//        });
//
//        jPanel_TableArea.add(scroll);
//
//		dmodel.setDataVector(setUpRecordTable(),header);
//
//		// 2��ȉ��͌Œ�A�ȍ~�͕ϓ�
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<2) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	   TableColumnModel columnsfix = modelfixed.getColumnModel();
//	   for(int i=0;i<columnsfix.getColumnCount();i++) {
//
//		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
//	   }
//
//	   TableColumnModel columns = table.getColumnModel();
//	   for(int i=0;i<columns.getColumnCount();i++) {
//
//		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
//	   }
//
//		// �����I��
//		if (table.getRowCount() > 0) {
//			table.setRowSelectionInterval(0, 0);
//		}
//
//		// add s.inoue 2009/11/12
//		table.refreshTable(); modelfixed.refreshTable();
//
//	}
//
//	/*
//	 * �������t���b�V������
//	 */
//	private void searchRefresh(){
//
//		modelfixed.setPreferedColumnWidths(new int[]{150, 150});
//		table.setPreferedColumnWidths(new int[] { 80, 80, 80, 80, 80, 80, 80, 80, 50, 120, 120 });
//
//		// add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//	    // validationCheck
//	    Object [][] resultref=setUpRecordTable();
//	    if (resultref == null)
//	    	return;
//
//	    // add s.inoue 2009/10/26
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<2) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//            }
//        }
//
//	    dmodel.setDataVector(resultref,header);
//
//		// add s.inoue 2009/10/26
//		// 4��ȉ��͌Œ�A�ȍ~�͕ϓ�
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<2) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	   // add s.inoue 2009/10/26
//	   TableColumnModel columnsfix = modelfixed.getColumnModel();
//	   for(int i=0;i<columnsfix.getColumnCount();i++) {
//
//		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
//	   }
//
//	   TableColumnModel columns = table.getColumnModel();
//	   for(int i=0;i<columns.getColumnCount();i++) {
//
//		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
//	   }
//
//	    // add s.inoue 2009/11/12
//	    table.addHeader(this.header);
//	    modelfixed.refreshTable();table.refreshTable();
//
//	    // edit s.inoue 2009/11/09
//		// �����I��
//		if (table.getRowCount() > 0) {
//			table.setRowSelectionInterval(0, 0);
//			} else {
//				jTextField_Name.requestFocus();
//		}
//	}
//	/* focus������ */
//	private void initilizeFocus(){
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//
//		this.focusTraversalPolicy.setDefaultComponent(table);
//		this.focusTraversalPolicy.addComponent(table);
//		this.focusTraversalPolicy.addComponent(jButton_Prev);
//		this.focusTraversalPolicy.addComponent(jButton_Next);
//		this.focusTraversalPolicy.addComponent(jButton_Print);
//		this.focusTraversalPolicy.addComponent(jButton_Fix);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//	}
//
//	// add s.inoue 2009/12/01
//	private void functionListner(){
//		Component comp = null;
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//		// edit s.inoue 2010/05/19
//		jTextField_HihokenjyaCode.addKeyListener(this);
//		jTextField_HihokenjyaNumber.addKeyListener(this);
//		jTextField_PatternName.addKeyListener(this);
//		jTextField_Name.addKeyListener(this);
//		jTextField_Date.addKeyListener(this);
//		jEditorPane_Comment.addKeyListener(this);
//
//		modelfixed.addKeyListener(this);
//	}
//
//	// add s.inoue 2009/12/01
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			dispose();break;
//		case KeyEvent.VK_F7:
//			logger.info(jButton_Prev.getText());
//			pushedPrevButton(null);break;
//		case KeyEvent.VK_F9:
//			logger.info(jButton_Next.getText());
//			pushedNextButton(null);break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_Print.getText());
////			pushedPrintButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Fix.getText());
////			pushedFixButton(null);break;
//		}
//	}
//
//	/**
//	 * DAO�̃Z�b�g�A�b�v
//	 */
//	private void setUpDao() {
//
//		Connection con = null;
//		try {
//			con = JApplication.kikanDatabase.getMConnection();
//			jShowResultFrameDao = (JShowResultFrameDao) DaoFactory.createDao(
//					con, new JShowResultFrameModel());
//			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
//					con, new TKensakekaSonota());
//			dataTypeCodeDao = (TDataTypeCodeDao) DaoFactory.createDao(con,
//					new TDataTypeCode());
//
//			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
//					con, new TKensakekaTokutei());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * �f�[�^�\��
//	 */
//	private void refresh() {
//
//		// eidt s.inoue 2011/03/30
//		// TreeMap<String, String> recordMap = kojinData.get(curIndex);
//		// if (recordMap == null)
//		//	return;
//
//		// eidt s.inoue 2011/03/30
//		// String uketukeId = recordMap.get("UKETUKE_ID");
//		String uketukeId = vo.getUKETUKE_ID();
//
//		// del s.inoue 2011/03/30
//		// �������背�R�[�h�֘A�̃R���|�[�l���g�Ƀf�[�^���Z�b�g
//		// setUpKensaTokuteiComponents(recordMap);
//
//		// ���f�p�^�[���E�����Z���^�[�e�L�X�g�t�B�[���h�Ƀf�[�^���Z�b�g
//		setUpRecordKpNameCenterName(uketukeId);
//		// ���^�{����R���{�{�b�N�X�Ƀf�[�^���Z�b�g
//		setUpRecordMetaboHokenShidou(uketukeId,METABO_HANTEI_KOUMOKU_CD, jComboBox_MetaboHantei);
//		// �ی��w�����x���R���{�{�b�N�X�Ƀf�[�^���Z�b�g
//		setUpRecordMetaboHokenShidou(uketukeId,HOKEN_LEVEL_KOUMOKU_CD, jComboBox_HokenSidouLevel);
//		// �R�����g���ݒ�
//		this.setUpKoment(uketukeId, this.jEditorPane_Comment);
//	}
//
//	/**
//	 * �������背�R�[�h�֘A�R���|�[�l���g�Ƀf�[�^���Z�b�g
//	 * @param recordMap ���R�[�h�}�b�v(�L�[ : �t�B�[���h�� �l : �t�B�[���h�l)
//	 */
//	private void setUpKensaTokuteiComponents(TreeMap<String, String> recordMap) {
//		for (String fieldName : recordMap.keySet()) {
//			JComponent component = fieldComponentMap.get(fieldName);
//			String value = recordMap.get(fieldName);
//
//			if (component instanceof JTextField) {
//				JTextField text = (JTextField) component;
//				text.setText(value);
//			} else if (component instanceof JEditorPane) {
//				JEditorPane pane = (JEditorPane) component;
//				pane.setText(value);
//			}
//		}
//	}
//
//	// edit ver2 s.inoue 2009/08/26
//	// ���f���{��������
//	/**
//	 * ���f�p�^�[���E�����Z���^���Ƀf�[�^���Z�b�g
//	 * @param uketukeId ��tID
//	 * @param kensaNengapi �������{�N����
//	 */
//	private void setUpRecordKpNameCenterName(String uketukeId) {
//
//		Long uketukeIdValue = null;
//
//		try {
//			uketukeIdValue = new Long(uketukeId);
//		} catch (NumberFormatException e) {
//		}
//
//		JShowResultFrameModel recordModel = null;
//		try {
//			recordModel = jShowResultFrameDao.selectKpNameCenterName(uketukeIdValue);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		if (recordModel == null) {
//			return;
//		}
//
//		jTextField_PatternName.setText(recordModel.getK_P_NAME());
//		jTextField_KensaCenterName.setText(recordModel.getCENTER_NAME());
//
//		kenshinPatternNumber = recordModel.getK_P_NO();
//	}
//
//	/**
//	 * ���^�{����E�ی��w�����x���R���{�{�b�N�X�Ƀf�[�^���Z�b�g
//	 * @param uketukeId ��tID
//	 * @param kensaNengapi �������{�N����
//	 * @param koumokuCd ���^�{����E�ی��w�����x�����ڃR�[�h
//	 * @param cmb ���^�{����E�ی��w�����x���R���{�{�b�N�X
//	 */
//	private void setUpRecordMetaboHokenShidou(String uketukeId,
//			String koumokuCd, JComboBox cmb) {
//
//		Long uketukeIdValue = null;
//
//		try {
//			uketukeIdValue = new Long(uketukeId);
//		} catch (NumberFormatException e) {
//		}
//
//		TKensakekaSonota recordModel = null;
//		try {
//			recordModel = kensakekaSonotaDao.selectByPrimaryKey(uketukeIdValue,koumokuCd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (recordModel == null)
//			return;
//
//		String keka_ti = recordModel.getKEKA_TI();
//		Integer value = new Integer(0);
//		try {
//			value = new Integer(keka_ti);
//		} catch (NumberFormatException e) {
//		}
//		cmb.setSelectedIndex(value);
//	}
//
//	// edit ver2 s.inoue 2009/08/26
//	private void setUpKoment(String uketukeId,JEditorPane komentPane){
//
//		Long uketukeIdValue = null;
//
//		try {
//			uketukeIdValue = new Long(uketukeId);
//		} catch (NumberFormatException e) {
//		}
//
//		TKensakekaTokutei recordModel = null;
//		try {
//			// edit ver2 s.inoue 2009/08/26
//			recordModel = kensakekaTokuteiDao.selectByPrimaryKey(uketukeIdValue);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (recordModel == null)
//			return;
//
//		String komento = recordModel.getKOMENTO();
//		komentPane.setText(komento);
//	}
//
//	/**
//	 * �e�[�u���Ƀ��R�[�h���Z�b�g
//	 * @param recordMap ���݂̕\���Ώۃ��R�[�h�̃}�b�v
//	 */
//	private Object[][] setUpRecordTable() {
//
//		// eidt s.inoue 2011/03/30
//		// TreeMap<String, String> recordMap = kojinData.get(curIndex);
//
//		// String hknjaNum = recordMap.get("HKNJANUM");
//		String hknjaNum = vo.getHKNJANUM();
//
//		Long uketukeId = null;
//		Integer kensaNengapi = null;
//		Integer kenshinPatternNumber = null;
//		try {
//			// eidt s.inoue 2011/03/30
//			// uketukeId = new Long(recordMap.get("UKETUKE_ID"));
//			uketukeId = new Long(vo.getUKETUKE_ID());
//			kenshinPatternNumber = new Integer(this.kenshinPatternNumber);
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//
//		TKensakekaTokutei recordModel = null;
//		try {
//			recordModel = kensakekaTokuteiDao.selectByPrimaryKey(uketukeId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (recordModel == null)
//			return null;
//
//		this.jTextField_Date.setText(String.valueOf(recordModel.getKENSA_NENGAPI()));
//
//		// ���f���ڃ}�X�^�E�������̑��e�[�u�����R�[�h�𒊏o
//		List<TreeMap<String, String>> recList = null;
//		try {
//			// edit s.inoue 2009/11/18
//			recList = jShowResultFrameDao.selectTableRecord(
//			uketukeId,
//			kenshinPatternNumber,
//			hknjaNum
//			);
//
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//		if (recList == null)
//			return null;
//
//		// �f�[�^�^�C�v�R�[�h��ǂݍ���
//		try {
//			dataTypeCodes = dataTypeCodeDao.selectByAll();
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//			return null;
//		}
//
//		//��s���ƂɃf�[�^��}�����Ă���
//		boolean kekkaDecimal = false;
//		int i = 0;
//		rowcolumn = new Object[recList.size()][13];
//
//		for (TreeMap<String, String> map : recList) {
//			kekkaDecimal = false;
//
//			List<String> tableData = new ArrayList<String>();
//			setKekaTiValue(map);
//
//				rowcolumn[i][COLUMN_INDEX_KOUMOKU_NAME] = map.get("KOUMOKU_NAME");
//				rowcolumn[i][COLUMN_INDEX_KENSA_HOUHOU] = map.get("KENSA_HOUHOU");
//				rowcolumn[i][COLUMN_INDEX_KENSA_KEKA_NUM] = map.get("KEKA_TI_DECIMAL");
//				rowcolumn[i][COLUMN_INDEX_KENSA_KEKA_CODE] = map.get("KEKA_TI_CODE");
//				rowcolumn[i][COLUMN_INDEX_KENSA_KEKA_STR] = map.get("KEKA_TI_STRING");
//				rowcolumn[i][COLUMN_INDEX_KENSA_JISIKBN] = map.get("JISI_KBN");
//				rowcolumn[i][COLUMN_INDEX_KIJUN_DS_KAGEN] = map.get("DS_KAGEN");
//				rowcolumn[i][COLUMN_INDEX_KIJUN_DS_JYOGEN] = map.get("DS_KAGEN");
//				rowcolumn[i][COLUMN_INDEX_KIJUN_JS_KAGEN] = map.get("JS_KAGEN");
//				rowcolumn[i][COLUMN_INDEX_KIJUN_JS_JYOGEN] = map.get("JS_JYOUGEN");
//				rowcolumn[i][COLUMN_INDEX_HL] = map.get("HL");
//				rowcolumn[i][COLUMN_INDEX_HANTEI] = map.get("HANTEI");
//				rowcolumn[i][COLUMN_INDEX_COMENT] = map.get("COMMENT");
//				i++;
//		}
//		return rowcolumn;
//	}
//
//	/**
//	 * ���f���ڃ}�X�^�̃f�[�^�^�C�v�ɂ�茟�����ʂ��̑����R�[�h��
//	 * ���ʒl���Z�b�g����
//	 * @param recMap ���ݕ\���Ώۂ̃��R�[�h�}�b�v
//	 */
//	private void setKekaTiValue(TreeMap<String, String> recMap) {
//
//		if (recMap == null)
//			return;
//
//		String dataType = recMap.get("DATA_TYPE");
//		String kekaTi = recMap.get("KEKA_TI_DECIMAL");
//		String maxByteLength = recMap.get("MAX_BYTE_LENGTH");
//
//		recMap.put("KEKA_TI_DECIMAL", "");
//		recMap.put("KEKA_TI_CODE", "");
//		recMap.put("KEKA_TI_STRING", "");
//
//		// edit s.inoue 2009/09/14
//		String koumokuCd = recMap.get("KOUMOKU_CD");
//		// "1" ���l�^
//		// "2" �R�[�h�^
//		// "3" ������^
//
//		if ("1".equals(dataType)) {
//			// edit s.inoue 2009/09/14
//			String limitValeDsKagen = recMap.get("DS_KAGEN") == null ? "" : recMap.get("DS_KAGEN");;
//			String limitValeDsJyougen = recMap.get("DS_JYOUGEN") == null ? "" : recMap.get("DS_JYOUGEN");;
//			String limitValeJsKagen = recMap.get("JS_KAGEN") == null ? "" : recMap.get("JS_KAGEN");;
//			String limitValeJsJyougen = recMap.get("JS_JYOUGEN") == null ? "" : recMap.get("JS_JYOUGEN");;
//			String maxValeKagen = recMap.get("KAGEN") == null ? "" : recMap.get("KAGEN");;
//			String maxValeJyougen = recMap.get("JYOUGEN") == null ? "" : recMap.get("JYOUGEN");
//
//			// edit s.inoue 2009/09/28
//			// ���ʊ֐����Ăяo���悤�ɏC��
//			if (koumokuCd.equals("2A040000001930102")){
//				recMap.put("JYOUGEN", "99.9");
//			}else{
//				recMap.put("JYOUGEN", JValidate.validateKensaKekkaDecimal(maxValeJyougen, maxByteLength));
//			}
//
//			recMap.put("KAGEN", JValidate.validateKensaKekkaDecimal(maxValeKagen, maxByteLength));
//			recMap.put("DS_JYOUGEN", JValidate.validateKensaKekkaDecimal(limitValeDsJyougen, maxByteLength));
//			recMap.put("DS_KAGEN", JValidate.validateKensaKekkaDecimal(limitValeDsKagen, maxByteLength));
//			recMap.put("JS_JYOUGEN", JValidate.validateKensaKekkaDecimal(limitValeJsJyougen, maxByteLength));
//			recMap.put("JS_KAGEN", JValidate.validateKensaKekkaDecimal(limitValeJsKagen, maxByteLength));
//			// add s.inoue 2009/10/26
//			// ��������l�ɂ��C���ŏ����Ă����G�G
//			recMap.put("KEKA_TI_DECIMAL", JValidate.validateKensaKekkaDecimal(
//					kekaTi, maxByteLength));
//
//		} else if ("2".equals(dataType)) {
//			// �R�[�h�^�̏ꍇ�A�f�[�^�^�C�v�R�[�h�ڍ׃��R�[�h�̍��ڃR�[�h�ƈ�v����
//			// �f�[�^�^�C�v�R�[�h���ږ������ʒl�ɃZ�b�g
//
//			for (TDataTypeCode code : dataTypeCodes) {
//				if (code.getKOUMOKU_CD().equals(koumokuCd)) {
//					if (code.getCODE_NUM().toString().equals(kekaTi)) {
//						recMap.put("KEKA_TI_CODE", code.getCODE_NAME());
//						return;
//					}
//				}
//			}
//			// edit s.inoue 2009/09/28
//			recMap.put("JYOUGEN", "");
//			recMap.put("KAGEN", "");
//			recMap.put("DS_JYOUGEN", "");
//			recMap.put("DS_KAGEN", "");
//			recMap.put("JS_JYOUGEN", "");
//			recMap.put("JS_KAGEN", "");
//		} else if ("3".equals(dataType)) {
//			recMap.put("KEKA_TI_STRING", kekaTi);
//			// edit s.inoue 2009/09/28
//			recMap.put("JYOUGEN", "");
//			recMap.put("KAGEN", "");
//			recMap.put("DS_JYOUGEN", "");
//			recMap.put("DS_KAGEN", "");
//			recMap.put("JS_JYOUGEN", "");
//			recMap.put("JS_KAGEN", "");
//		}
//	}
//}
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

