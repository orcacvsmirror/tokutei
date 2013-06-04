//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JViewport;
//import javax.swing.ListSelectionModel;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.event.RowSorterEvent;
//import javax.swing.event.RowSorterListener;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.ListIterator;
//import java.util.TreeMap;
//import java.util.Vector;
//import java.awt.Component;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.InputEvent;
//import java.awt.event.ItemEvent;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.print.PrinterException;
//import java.sql.SQLException;
//import org.apache.log4j.Logger;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
//import jp.or.med.orca.jma_tokutei.common.scene.JScene;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintSeikyuNitiji;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Nikeihyo;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JInputKessaiDataFrameCtrl;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellEditor;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
///**
// * �����EHL7�o�͉�ʐ���
// *
// * ���������G�Ȃ��߁A�S�̓I�Ƀ��t�@�N�^�����O�Ə����̌��������s�Ȃ����B
// */
//public class JOutputNitijiFrameCtrl extends JOutputNitijiFrame
//{
//	private static Logger logger = Logger.getLogger(JOutputNitijiFrameCtrl.class);
//
//	// edit s.inoue 2009/10/26
//	// �ꗗ�f�[�^�񍀖�
//	private static final int COLUMN_INDEX_CHECK = 0;
//	private static final int COLUMN_INDEX_NENDO = 1;
//	private static final int COLUMN_INDEX_JYUSHIN_SEIRI_NO = 2;
//	private static final int COLUMN_INDEX_KANANAME = 3;
//	private static final int COLUMN_INDEX_NAME = 4;
//	private static final int COLUMN_INDEX_SEX = 5;
//	private static final int COLUMN_INDEX_BIRTHDAY = 6;
//	private static final int COLUMN_INDEX_KENSA_NENGAPI = 7;
//	private static final int COLUMN_INDEX_HKNJANUM = 8;
//	private static final int COLUMN_INDEX_SIHARAI_DAIKOU_BANGO = 9;
//	private static final int COLUMN_INDEX_HIHOKENJYASYO_KIGOU = 10;
//	private static final int COLUMN_INDEX_HIHOKENJYASYO_NO = 11;
//	private static final int COLUMN_INDEX_TANKA_GOUKEI = 12;
//	private static final int COLUMN_INDEX_MADO_FUTAN_GOUKEI = 13;
//	private static final int COLUMN_INDEX_SEIKYU_KINGAKU = 14;
//	private static final int COLUMN_INDEX_UPDATE_TIMESTAMP = 15;
//
//	/** �ی��Ҕԍ��A�x����s�@�֑I�𗓂́A�ԍ��Ɩ��̂̋�؂蕶���� */
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";
//	private static final String JISSIKUBUN_TOKUTEIKENSHIN = "1";
//	private static final String SYUBETSU_CODE_6_DISPLAY_NAME = "���茒�f�@�֖��͓���ی��w���@�ւ���ی���";
//	private static final String SYUBETSU_CODE_1_DISPLAY_NAME = "���茒�f�@�֖��͓���ی��w���@�ւ����s�@��";
//	// edit s.inoue 2009/10/26
//	private static final String[] header = {"", "�N�x", "��f�������ԍ�", "�����i�J�i�j","�����i�����j", "����",
//			"���N����", "���f���{��","�ی��Ҕԍ�","��s�@�֔ԍ�","��ی��ҏؓ��L��", "��ی��ҏؓ��ԍ�",
//			"�P�����v","�������S���v", "�������z���v", "�X�V���t"};
//	// edit s.inoue 2009/10/26
//	private DefaultTableModel dmodel =null;
//	// edit s.inoue 2009/10/26
//    private TableRowSorter<TableModel> sorter = null;
//
//    // edit s.inoue 2009/10/23
//	// private JSimpleTable model = new JSimpleTable();
//	// private JSimpleTable modelfixed = new JSimpleTable();
//    private JSimpleTable table =null;
//	private JSimpleTable modelfixed =null;
//
//	// �t���[���̏�Ԃ��Ǘ�����
//	public enum JOutputHL7FrameState {
//		/* ������� */
//		STATUS_INITIALIZED,
//		/* ������ */
//		STATUS_AFTER_SEARCH,
//		/* ������ */
//		STATUS_AFTER_SEIKYU,
//		/* HL7�ϊ��� */
//		STATUS_AFTER_HL7
//	}
//
//	// �S�I���{�^���̏��
//	boolean isAllSelect = true;
//
//	/** �������ʁi��ʏ�̃f�[�^�j */
//	private ArrayList<Hashtable<String, String>> result = null;
//
//	private JOutputHL7FrameState state = JOutputHL7FrameState.STATUS_INITIALIZED;
////	private Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
//	private Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
//
//	private IDialog messageDialog;
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	/**
//	 * �t���[���̏�Ԃɂ���ĉ�����{�^���Ȃǂ𐧌䂷��
//	 */
//	public void buttonCtrl()
//	{
//		switch(state)
//		{
//		case STATUS_INITIALIZED:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
////			forbitCells.clear();
////			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_SEARCH:
//			jButton_Seikyu.setEnabled(true);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
////			forbitCells.clear();
//			break;
//
//		case STATUS_AFTER_SEIKYU:
//			jButton_Seikyu.setEnabled(true);
//			jButton_SeikyuEdit.setEnabled(true);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
////			forbitCells.clear();
////			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_HL7:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
////			forbitCells.clear();
////			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//		}
//	}
//
//	// edit s.inoue 2009/10/26
//	/*
//	 * �������t���b�V������
//	 */
//	private void searchRefresh(){
//
//		/* �񕝂̐ݒ� */
//		// edit s.inoue 2010/07/07
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
//		table.setPreferedColumnWidths(new int[] {100, 40, 70, 70, 70,80, 110, 110, 80, 80, 80, 140});
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//	    dmodel.setDataVector(resultRefresh(),header);
//
//	    // edit s.inoue 2010/02/16
//	    if (resultRefresh().length == 0){
//	    	JErrorMessage.show("M4757", this);
//	    }
//
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i < 4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                // edit s.inoue 2010/07/07
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//		    }else{
//	            modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//	        }
//        }
//
//	   // add s.inoue 2009/10/26
//	   TableColumnModel columnsfix = modelfixed.getColumnModel();
//	   for(int i=1;i<columnsfix.getColumnCount();i++) {
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
//	    // edit s.inoue 2009/12/07
//		// �Œ�,�ϓ����t���b�V��
//	    modelfixed.setCellCheckBoxEditor(new ExtendedCheckBox(), 0);
////		table.setCellEditForbid(forbitCells);
////		modelfixed.setCellEditForbid(forbitFixedCells);
//	    initilizeSeikyu();
//	    table.refreshTable();
//
//	    // edit s.inoue 2010/02/24
//		// �����I��
//	    int count = 0;
//		if (table.getRowCount() > 0) {
//			table.setRowSelectionInterval(0, 0);
//			count = table.getRowCount();
//			} else {
//				jTextField_Name.requestFocus();
//		}
//		jLabel_count.setText(count + " ��");
//
//	}
//
//	// add s.inoue 2010/01/19
//	private void checkKigenSetting(String kenshinDate,String limitDataStart,String limitDataEnd){
//		if (!kenshinDate.equals("") &&
//			!limitDataStart.equals("") &&
//				!limitDataEnd.equals("")){
//			if(!(Integer.parseInt(limitDataStart) <= Integer.parseInt(kenshinDate) &&
//					Integer.parseInt(kenshinDate) <= Integer.parseInt(limitDataEnd))){
//				JErrorMessage.show("M4756", this);
//			}
//		}
//	}
//
//	/**
//	 * �e�[�u���ɕ\��������e���X�V����B
//	 */
//	public Object[][] resultRefresh()
//	{
//		// edit s.inoue 2009/10/26
//		Hashtable<String, String> resultItem;
//		Object[][] rowcolumn = null;
//
//		try {
//			JOutputNitijiFrameData validatedData = new JOutputNitijiFrameData();
//
//			/* �R���{�{�b�N�X�őI������Ă���ی��Ҕԍ��A���̂��擾����B */
//			String hokenjaNumberAndNameString = (String)jComboBox_HokenjyaNumber.getSelectedItem();
//			String hokenjaNumber = "";
//			String hokenjaName = "";
//			if (hokenjaNumberAndNameString != null && ! hokenjaNumberAndNameString.isEmpty()) {
//				String[] hokenjaNumberAndName = hokenjaNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				hokenjaNumber = hokenjaNumberAndName[0];
//
//				if (hokenjaNumberAndName.length > 1) {
//					hokenjaName = hokenjaNumberAndName[1];
//				}
//			}
//
//			/* �R���{�{�b�N�X�őI������Ă���x����s�@�֔ԍ��A���̂��擾����B */
//			String daikoNumberAndNameString = (String)jComboBox_SeikyuKikanNumber.getSelectedItem();
//			String daikoNumber = "";
//			String daikoName = "";
//			if (daikoNumberAndNameString != null && ! daikoNumberAndNameString.isEmpty()) {
//				String[] daikoNumberAndName = daikoNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				daikoNumber = daikoNumberAndName[0];
//
//				if (daikoNumberAndName.length > 1) {
//					daikoName = daikoNumberAndName[1];
//				}
//			}
//
//			if(
//					/* ��ی��ҏؓ��L�� */
//					validatedData.setHihokenjyasyo_kigou(jTextField_Hihokenjyasyo_kigou.getText()) &&
//					/* ��ی��ҏؓ��ԍ� */
//					validatedData.setHihokenjyasyo_Number(jTextField_Hihokenjyasyo_Number.getText()) &&
//					/* ���{���i�J�n�j */
//					validatedData.setJissiBeginDate(jTextField_JissiBeginDate.getText()) &&
//					/* ���{���i�I���j */
//					validatedData.setJissiEndDate(jTextField_JissiEndDate.getText()) &&
//					/* �ی��ؔԍ� */
//					validatedData.setHokenjyaNumber(hokenjaNumber) &&
//					/* �ی��Җ��� */
//					validatedData.setHokenjyaName(hokenjaName) &&
//					/* ������@�֔ԍ� */
//					validatedData.setSeikyuKikanNumber(daikoNumber) &&
//					/* ������@�֖��� */
//					validatedData.setSeikyuKikanName(daikoName) &&
//					/* ���� */
//					validatedData.setName(jTextField_Name.getText())
//			){
//				/* Where ���������SQL���쐬����B */
//				StringBuffer queryBuffer = this.createSearchQueryWithoutCondition();
//
//				/* ��������t������B */
//				this.appendCondition(queryBuffer, validatedData);
//
//				/* SQL�����s����B */
//				result = JApplication.kikanDatabase.sendExecuteQuery(queryBuffer.toString());
//
//				// edit s.inoue 2009/10/26
//				/* �������ʂ��e�[�u���ɒǉ�����B */
//				// retData = addRowToTable();
//				rowcolumn = new Object[result.size()][16];
//				// edit s.inoue 2009/11/14
//				NumberFormat kingakuFormat = NumberFormat.getNumberInstance();
//
//				for(int i = 0;i < result.size();i++ )
//				{
//					// edit s.inoue 2009/10/26
//					// Vector<String> rowcolumn = new Vector<String>();
//					// Vector<String> rowfixed = new Vector<String>();
//
//					resultItem = result.get(i);
//
//					// edit s.inoue 2009/10/26
//					/* �`�F�b�N�{�b�N�X */
//					// rowcolumn.add(null);
//					rowcolumn[i][COLUMN_INDEX_CHECK] = null;
//					/* �N�x */
//					// rowcolumn.add(resultItem.get("NENDO"));
//					rowcolumn[i][COLUMN_INDEX_NENDO] = resultItem.get("NENDO");
//					/* ��f�������ԍ� */
//					// rowcolumn.add(resultItem.get("JYUSHIN_SEIRI_NO"));
//					rowcolumn[i][COLUMN_INDEX_JYUSHIN_SEIRI_NO] = resultItem.get("JYUSHIN_SEIRI_NO");
//					/* �����i�J�i�j */
//					// rowcolumn.add(resultItem.get("KANANAME"));
//					rowcolumn[i][COLUMN_INDEX_KANANAME] = resultItem.get("KANANAME");
//					/* �����i���O�j */
//					// rowcolumn.add(resultItem.get("NAME"));
//					rowcolumn[i][COLUMN_INDEX_NAME] = resultItem.get("NAME");
//
//					/* ���� */
//					// edit s.inoue 2009/10/26
//					// String sexCode = resultItem.get("SEX");
//					// String sexText = "";
//					// if (sexCode.equals("1")) {
//					//	sexText = "�j��";
//					// }
//					// else if(sexCode.equals("2")){
//					// 	sexText = "����";
//					// }
//					// rowcolumn.add(sexText);
//					rowcolumn[i][COLUMN_INDEX_SEX] = resultItem.get("SEX").equals("1") ? "�j��" : "����";
//					/* ���N���� */
//					// rowcolumn.add(resultItem.get("BIRTHDAY"));
//					rowcolumn[i][COLUMN_INDEX_BIRTHDAY] = resultItem.get("BIRTHDAY");
//					/* ���f���{�� */
//					// rowcolumn.add(resultItem.get("KENSA_NENGAPI"));
//					rowcolumn[i][COLUMN_INDEX_KENSA_NENGAPI] = resultItem.get("KENSA_NENGAPI");
//					/* �ی��Ҕԍ� */
//					// rowcolumn.add(resultItem.get("HKNJANUM"));
//					rowcolumn[i][COLUMN_INDEX_HKNJANUM] = resultItem.get("HKNJANUM");
//					/* �x����s�@�֔ԍ� */
//					// rowcolumn.add(resultItem.get("SIHARAI_DAIKOU_BANGO"));
//					rowcolumn[i][COLUMN_INDEX_SIHARAI_DAIKOU_BANGO] = resultItem.get("SIHARAI_DAIKOU_BANGO");
//					/* ��ی��ҏؓ��L�� */
//					// rowcolumn.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
//					rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_KIGOU] = resultItem.get("HIHOKENJYASYO_KIGOU");
//					/* ��ی��ҏؓ��ԍ� */
//					// rowcolumn.add(resultItem.get("HIHOKENJYASYO_NO"));
//					rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_NO] = resultItem.get("HIHOKENJYASYO_NO");
//					/* �P�����v */
//					// rowcolumn.add(resultItem.get("TANKA_GOUKEI"));
//					String tankaGoukei = resultItem.get("TANKA_GOUKEI");
//					tankaGoukei = (tankaGoukei.equals("")) ? "0": tankaGoukei;
//					rowcolumn[i][COLUMN_INDEX_TANKA_GOUKEI] =kingakuFormat.format(Long.parseLong(tankaGoukei));
//					/* �������S���v */
//					// rowcolumn.add(resultItem.get("MADO_FUTAN_GOUKEI"));
//					String madogutiGoukei = resultItem.get("MADO_FUTAN_GOUKEI");
//					madogutiGoukei = (madogutiGoukei.equals("")) ? "0": madogutiGoukei;
//					rowcolumn[i][COLUMN_INDEX_MADO_FUTAN_GOUKEI] = kingakuFormat.format(Long.parseLong(madogutiGoukei));
//					/* �������z���v */
//					// rowcolumn.add(resultItem.get("SEIKYU_KINGAKU"));
//					String seikyuGoukei = resultItem.get("SEIKYU_KINGAKU");
//					seikyuGoukei = (seikyuGoukei.equals("")) ? "0": seikyuGoukei;
//					rowcolumn[i][COLUMN_INDEX_SEIKYU_KINGAKU] =kingakuFormat.format(Long.parseLong(seikyuGoukei));
//					/* �X�V���� */
//					// rowcolumn.add(resultItem.get("UPDATE_TIMESTAMP").replaceAll("-",""));
//					rowcolumn[i][COLUMN_INDEX_UPDATE_TIMESTAMP] = resultItem.get("UPDATE_TIMESTAMP").replaceAll("-","");
//					// edit s.inoue 2009/10/26
//					// model.addData(rowcolumn);
//					// modelfixed.addData(rowfixed);
//				}
//			}else{
//				return null;
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//		}
//
//		return rowcolumn;
//	}
//
//	/**
//	 * ��������t������B
//	 */
//	private void appendCondition(StringBuffer query, JOutputNitijiFrameData validatedData) {
//		/* ����������t������B */
//		ArrayList<String> conditions = new ArrayList<String>();
//
//		/* ��l�̃e�L�X�g�t�B�[���h�͏������珜�O���� */
//
//		String jyushinkenId = jTextField_JyushinKenID.getText();
//		if (jyushinkenId != null && ! jyushinkenId.isEmpty()) {
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.JYUSHIN_SEIRI_NO CONTAINING " + JQueryConvert.queryConvert(jyushinkenId));
//			conditions.add(" KOJIN.JYUSHIN_SEIRI_NO STARTING WITH "
//					+ JQueryConvert.queryConvert(jyushinkenId));
//		}
//
//		String hihokenjyasyo_kigou = validatedData.getHihokenjyasyo_kigou();
//		if( !hihokenjyasyo_kigou.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.HIHOKENJYASYO_KIGOU CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_kigou));
//			conditions.add(" KOJIN.HIHOKENJYASYO_KIGOU STARTING WITH "
//					+ JQueryConvert.queryConvert(hihokenjyasyo_kigou));
//		}
//		String hihokenjyasyo_Number = validatedData.getHihokenjyasyo_Number();
//		if( !hihokenjyasyo_Number.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.HIHOKENJYASYO_NO CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_Number));
//			conditions.add(" KOJIN.HIHOKENJYASYO_NO STARTING WITH "
//					+ JQueryConvert.queryConvert(hihokenjyasyo_Number));
//		}
//		String hokenjyaNumber = validatedData.getHokenjyaNumber();
//		if( !hokenjyaNumber.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.HKNJANUM CONTAINING " + JQueryConvert.queryConvert(hokenjyaNumber));
//			conditions.add(" KOJIN.HKNJANUM STARTING WITH "
//					+ JQueryConvert.queryConvert(hokenjyaNumber));
//		}
//		String seikyuKikanNumber = validatedData.getSeikyuKikanNumber();
//		if( !seikyuKikanNumber.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.SIHARAI_DAIKOU_BANGO CONTAINING " + JQueryConvert.queryConvert(seikyuKikanNumber));
//			conditions.add(" KOJIN.SIHARAI_DAIKOU_BANGO STARTING WITH "
//					+ JQueryConvert.queryConvert(seikyuKikanNumber));
//		}
//		String name2 = validatedData.getName();
//		if( !name2.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.KANANAME CONTAINING " + JQueryConvert.queryConvert(name2));
//			conditions.add(" KOJIN.KANANAME STARTING WITH "
//					+ JQueryConvert.queryConvert(name2));
//		}
//
//		String jissiBeginDate = validatedData.getJissiBeginDate();
//		if( !jissiBeginDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI >= " + JQueryConvert.queryConvert(jissiBeginDate));
//		}
//		String jissiEndDate = validatedData.getJissiEndDate();
//		if( !jissiEndDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI <= " + JQueryConvert.queryConvert(jissiEndDate));
//		}
//
//		/* ���N�x */
//		if (jCheckBox_JisiYear.isSelected()) {
//			conditions.add(" GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())));
//		}
//
//		// ���ʓo�^�u���A�ρv�́u���v�̃f�[�^�͕\�����Ȃ�
//		conditions.add(" TOKUTEI.KEKA_INPUT_FLG = '2' ");
//
//		/* ����������t������ */
//		if (conditions.size() > 0) {
//			query.append(" WHERE ");
//
//			for (ListIterator<String> iter = conditions.listIterator(); iter.hasNext();) {
//				String condition = iter.next();
//				query.append(condition);
//
//				if (iter.hasNext()) {
//					query.append(" AND ");
//				}
//			}
//			// �\����
//			query.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//		}
//	}
//
//	/**
//	 * �����p�� SQL ���쐬����B
//	 */
//	private StringBuffer createSearchQueryWithoutCondition() {
//
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT DISTINCT ");
//		sb.append("KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.UKETUKE_ID,KOJIN.BIRTHDAY,KOJIN.SEX,KOJIN.NAME,KOJIN.JYUSHIN_SEIRI_NO,KOJIN.HIHOKENJYASYO_KIGOU,KOJIN.HIHOKENJYASYO_NO,");
//		sb.append("TOKUTEI.KENSA_NENGAPI,TOKUTEI.HENKAN_NITIJI,KOJIN.HKNJANUM,KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.KANANAME,GET_NENDO.NENDO,");
//		// add s.inoue 2010/01/19
//		sb.append("HOKENJYA.HKNJYA_LIMITDATE_START,HOKENJYA.HKNJYA_LIMITDATE_END, ");
//		// edit s.inoue 2009/10/28
//		// sb.append(" case when TANKA_NINGENDOC is not null then TANKA_NINGENDOC else KESAI.TANKA_GOUKEI end TANKA_GOUKEI,");
//	 	sb.append(" case when HOKENJYA.TANKA_HANTEI = '2' then KESAI.TANKA_NINGENDOC else KESAI.TANKA_GOUKEI end TANKA_GOUKEI,");
//		sb.append("KESAI.MADO_FUTAN_GOUKEI,KESAI.SEIKYU_KINGAKU,KESAI.UPDATE_TIMESTAMP ");
//		sb.append(" FROM T_KOJIN AS KOJIN ");
//		sb.append("LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
//		sb.append("LEFT JOIN T_KESAI_WK AS KESAI ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID ");
//		// edit s.inoue 2009/10/28
//		sb.append("LEFT JOIN T_HOKENJYA HOKENJYA ON KOJIN.HKNJANUM = HOKENJYA.HKNJANUM ");
//		// add s.inoue 2010/01/15
//		sb.append(" AND HOKENJYA.YUKOU_FLG = '1' ");
//
//		sb.append("LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
//		sb.append(" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//		sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//		sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//		sb.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
//		sb.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
//		sb.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
//		sb.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		sb.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
//
//		return sb;
//	}
//
//	/**
//	 * �R���X�g���N�^
//	 */
//	public JOutputNitijiFrameCtrl()
//	{
//		this.initializeCtrl();
//	}
//
//	/**
//	 * ����N���X�̏�����
//	 */
//	private void initializeCtrl() {
//
//		// edit s.inoue 2009/10/27
//		initializeTable();
//
//		// edit s.inoue 2009/10/27
//		dmodel = new DefaultTableModel(resultRefresh(),header){
//	      public boolean isCellEditable(int row, int column) {
//	    	boolean retflg = false;
//	    	if (column == 0 || column >12){
//	    		retflg = true;
//	      	}else{
//	      		retflg = false;
//	      	}
//	        return retflg;
//	      }
//	    };
//
//		sorter = new TableRowSorter<TableModel>(dmodel);
//		table = new JSimpleTable(dmodel);
//		modelfixed= new JSimpleTable(dmodel);
//
//		// edit s.inoue 2009/10/26
//		// state = JOutputHL7FrameState.STATUS_INITIALIZED;
//		/* �{�^���̏�Ԃ�ύX����B */
//		// this.buttonCtrl();
//
//		/* �񕝂̐ݒ� */
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
//		table.setPreferedColumnWidths(new int[] {100, 40, 70, 70, 70,80, 110, 110, 80, 80, 80, 140});
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//	    	// edit s.inoue 2010/07/07
//	    	if(i < 4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            // edit s.inoue 2010/07/07
//            }else if(i != 4) {
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	    // add s.inoue 2009/10/23
//	    table.setRowSorter(sorter);
//	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setRowSorter(sorter);
//	    modelfixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    modelfixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        // edit s.inoue 2009/10/26
//		// modelfixed.setCellCheckBoxEditor(new JCheckBox(), 0);
//		/* �J�����w�b�_�̃N���b�N�ɂ����ёւ����\�ɂ���B */
//		// model.setAutoCreateRowSorter(true);
//
//		/* ���N�x */
//		jCheckBox_JisiYear.setSelected(true);
//	    // edit s.inoue 2009/12/12
//		/* �����N���b�N�̏��� */
//		// table.setAutoCreateRowSorter(true);
//
//		// edit s.inoue 2010/07/06
//		table.addMouseListener(new JSingleDoubleClickEvent(this, jButton_SeikyuEdit,modelfixed));
//		modelfixed.addMouseListener(new JSingleDoubleClickEvent(this, jButton_SeikyuEdit,modelfixed));
//
////		// �\�[�g���ꂽ�Ƃ��ɔ�������C�x���g����
////		sorter.addRowSorterListener(new RowSorterListener() {
////		    public void sorterChanged(RowSorterEvent event) {
////		        System.out.println("aaaaType: " + event.getType());
////		        if(event.getType().equals(RowSorterEvent.Type.SORTED)){
////		        	sorter.sort()
////		        }
////		    }
////		});
////		modelfixed.setSelectionModel(table.getSelectionModel());
////		modelfixed.getColumnModel().getColumn(0).setResizable(false);
////		modelfixed.getColumnModel().getColumn(1).setResizable(false);
////		modelfixed.getColumnModel().getColumn(2).setResizable(false);
////		modelfixed.getColumnModel().getColumn(3).setResizable(false);
//
//		this.initilizeSeikyu();
//		this.initilizeFocus();
//		// add s.inoue 2009/12/01
//		this.functionListner();
//
//		// add s.inoue 2009/10/26
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
//		jPanel_TableArea.add(scroll);
//
//		/* �ی��Ҕԍ��R���{�{�b�N�X�̐ݒ� */
//		this.initializeHokenjaNumComboBox();
//
//		/* �����@�֔ԍ��R���{�{�b�N�X������������B */
//		this.initializeSeikyukikanNumberComboBox();
//
//		// ��ʃR�[�h�R���{�{�b�N�X�̐ݒ�
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_1_DISPLAY_NAME);
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_6_DISPLAY_NAME);
//
//		/* ���b�Z�[�W�_�C�A���O������������B�� */
//		try {
//			messageDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
//			messageDialog.setShowCancelButton(false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		/* �{�^���̏�Ԃ�ύX����B */
//		this.buttonCtrl();
//
//		dmodel.setDataVector(resultRefresh(),header);
//
//		// add s.inoue 2009/10/26
//		// 4��ȉ��͌Œ�A�ȍ~�͕ϓ�
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//            	// edit s.inoue 2010/07/07
//            	// modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            	modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	   // add s.inoue 2009/10/26
//	   TableColumnModel columnsfix = modelfixed.getColumnModel();
//	   for(int i=1;i<columnsfix.getColumnCount();i++) {
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
//	   modelfixed.setCellCheckBoxEditor(new ExtendedCheckBox(), 0);
//
//	   // add s.inoue 2009/12/07
//	   table.refreshTable();
//	   modelfixed.refreshTable();
//
//		 // edit s.inoue 2009/11/09
//		 // �����I��
//	     int count = 0;
//		 if (table.getRowCount() > 0) {
//			 table.setRowSelectionInterval(0, 0);
//			 count = table.getRowCount();
//		 } else {
//			 jTextField_JyushinKenID.requestFocus();
//		 }
//		 jLabel_count.setText(count + " ��");
//
//		  	// add s.inoue 2009/12/12
//		   // selectedrow�̏�����(simpletable�Ƃ̘A�g)
//		   table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			   public void valueChanged(ListSelectionEvent e) {
//			     if(e.getValueIsAdjusting()) return;
//
//			     int i = table.getSelectedRow();
//			     if (i <= 0) return;
//
//			     if(modelfixed.getValueAt(i, 0) == null){
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }else if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
//			    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
//			     }else{
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }
//			   }
//			 });
//		   modelfixed.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			   public void valueChanged(ListSelectionEvent e) {
//			     if(e.getValueIsAdjusting()) return;
//
//			     int i = table.getSelectedRow();
//			     if (i <= 0) return;
//
//			     if(modelfixed.getValueAt(i, 0) == null){
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }else if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
//			    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
//			     }else{
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }
//			   }
//			 });
//
//	}
//
//	/**
//	 * �t�H�[�J�X������������B
//	 */
//	private void initilizeFocus(){
//		// edit s.inoue 2009/10/07
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_Name);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Name);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JyushinKenID);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiBeginDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiEndDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_kigou);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_Number);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_HokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_SeikyuKikanNumber);
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_JisiYear);
//		this.focusTraversalPolicy.addComponent(this.jButton_AllSelect);
//		this.focusTraversalPolicy.addComponent(this.jButton_Search);
//		this.focusTraversalPolicy.addComponent(this.table);
//		this.focusTraversalPolicy.addComponent(this.jButton_Seikyu);
//		this.focusTraversalPolicy.addComponent(this.jButton_SeikyuListPrint);
//		this.focusTraversalPolicy.addComponent(this.jButton_SeikyuEdit);
//		this.focusTraversalPolicy.addComponent(this.jButton_End);
//	}
//	// add s.inoue 2009/12/01
//	/* focus������ */
//	private void functionListner(){
//		Component comp = null;
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//		modelfixed.addKeyListener(this);
//	}
//
//	/**
//	 * �e�[�u��������������B
//	 */
//	private void initializeTable() {
//
//        searchCondition(true);
//	}
//
//	private void initilizeSeikyu(){
//	      table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//				@Override
//				public void valueChanged(ListSelectionEvent e) {
//
//					if(e.getValueIsAdjusting()) return;
//		       	    int intTanka = 0;
//		       	    int intMadoguti = 0;
//		       	    int intSeikyu = 0;
//
//		       	    for(int i =0;i < table.getRowCount();i++){
//		       	    	if((Boolean)modelfixed.getData(i,0) == Boolean.TRUE)
//		    			{
//		       	    		// �P���A�������S�A�����z
//		       	    		String sumTanka="";
//		       	    		String sumMadoguti="";
//		       	    		String sumSeikyu="";
//
//		       	    		// edit s.inoue 2009/11/14
//		       	    		if(table.getData(i,COLUMN_INDEX_TANKA_GOUKEI) != null){
//		       	    			sumTanka = String.valueOf(table.getData(i,COLUMN_INDEX_TANKA_GOUKEI)).replaceAll(",", "");
//		       	    		}
//		       	    		if(table.getData(i,COLUMN_INDEX_MADO_FUTAN_GOUKEI) != null){
//		       	    			sumMadoguti = String.valueOf(table.getData(i,COLUMN_INDEX_MADO_FUTAN_GOUKEI)).replaceAll(",", "");
//		       	    		}
//		       	    		if(table.getData(i,COLUMN_INDEX_SEIKYU_KINGAKU) != null){
//		       	    			sumSeikyu = String.valueOf(table.getData(i,COLUMN_INDEX_SEIKYU_KINGAKU)).replaceAll(",", "");
//		       	    		}
//
//		       	    		// �I���s�̍��v����
//		       	    		if ( !sumTanka.equals("") ){
//		       	    			intTanka += Integer.parseInt(sumTanka);
//		       	    		}
//		       	    		if ( !sumMadoguti.equals("") ){
//		       	    			intMadoguti += Integer.parseInt(sumMadoguti);
//		       	    		}
//		       	    		if ( !sumSeikyu.equals("") ){
//		       	    			intSeikyu += Integer.parseInt(sumSeikyu);
//		       	    		}
//		    			}
//		       	    }
//		       	    // del s.inoue 2009/08/28
////		       	    // �P���A�������S�A�����z
////		       	    if (intTanka > 0){
////		       	    	jTextField_TankaGoukei.setText(String.valueOf(intTanka));
////		       	    }
////		       	    if (intMadoguti > 0){
////		       	    	jTextField_FutanGoukei.setText(String.valueOf(intMadoguti));
////		       	    }
////		       	    if (intSeikyu > 0){
////		       	    	jTextField_SeikyuGoukei.setText(String.valueOf(intSeikyu));
////		       	    }
////	        	    System.out.println(model.getSelectedRow() +"�s�I���C�x���g����");
//				}
//	        	});
//	}
//	/**
//	 * �x����s�@�փR���{�{�b�N�X������������
//	 */
//	private void initializeSeikyukikanNumberComboBox() {
//		ArrayList<Hashtable<String, String>> result = null;
//
//		String query = new String("SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//
//		jComboBox_SeikyuKikanNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> ResultItem = result.get(i);
//			String num = ResultItem.get("SHIHARAI_DAIKO_NO");
//			String name = ResultItem.get("SHIHARAI_DAIKO_NAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_SeikyuKikanNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * �ی��Ҕԍ��A���̂̈ꗗ���擾����B
//	 */
//	private ArrayList<Hashtable<String, String>> getHokenjaNumAndNames() {
//		ArrayList<Hashtable<String, String>> result = null;
//		// add s.inoue 2010/01/15
//		String query = new String("SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA WHERE YUKOU_FLG = '1' ORDER BY HKNJANUM");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//		return result;
//	}
//
//	/**
//	 * �ی��Ҕԍ��R���{�{�b�N�X�̏�����
//	 */
//	private void initializeHokenjaNumComboBox() {
//		ArrayList<Hashtable<String, String>> result = this.getHokenjaNumAndNames();
//
//		jComboBox_HokenjyaNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> resultItem = result.get(i);
//			String num = resultItem.get("HKNJANUM");
//			String name = resultItem.get("HKNJANAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_HokenjyaNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * �I���{�^���������ꂽ�ꍇ �T
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
//		dispose();
//	}
//
//	/**
//	 * ���������{�^���������ꂽ�ꍇ
//	 *
//	 * Modified 2008/04/01 �ጎ �ǐ����Ⴍ�A��Q�̒���������Ȃ��߁A�S�ʓI�ɏ��������B
//	 */
//	public void pushedSeikyuButton( ActionEvent e )
//	{
//		// �I����Ԃ�ێ�����
//		ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);
//
//		// �����{�^����������
//
//		// switch(state)
//		// {
//		// case STATUS_AFTER_SEARCH:
//			int count = 0;
//
//			boolean notExtMessage = false;
//
//			datas = new Vector<JKessaiProcessData>();
//
//			for( int i = 0;i < result.size();i++ )
//			{
//				if( Boolean.TRUE == (Boolean)modelfixed.getData(i,0)  )
//				{
//					count++;
//
//					Hashtable<String, String> item = result.get(i);
//
//					JKessaiProcessData dataItem = new JKessaiProcessData();
//
//					// add s.inoue 2010/01/19
//					checkKigenSetting(
//							item.get("KENSA_NENGAPI"),
//							item.get("HKNJYA_LIMITDATE_START"),
//							item.get("HKNJYA_LIMITDATE_END"));
//
//					/* ��tID */
//					dataItem.uketukeId = item.get("UKETUKE_ID");
//					/* ��ی��ҏؓ��L�� */
//					dataItem.hiHokenjyaKigo = item.get("HIHOKENJYASYO_KIGOU");
//					/* ��ی��ҏؓ��ԍ� */
//					dataItem.hiHokenjyaNumber = item.get("HIHOKENJYASYO_NO");
//					/* �ی��Ҕԍ��i�l�j */
//					dataItem.hokenjyaNumber = item.get("HKNJANUM");
//					/* �������{�� */
//					dataItem.KensaDate = item.get("KENSA_NENGAPI");
//					/* �x����s�@�֔ԍ� */
//					dataItem.daikouKikanNumber = item.get("SIHARAI_DAIKOU_BANGO");
//
//					/* �J�i���� */
//					dataItem.kanaName = item.get("KANANAME");
//					/* ���� */
//					dataItem.sex = item.get("SEX");
//					/* ���N���� */
//					dataItem.birthday = item.get("BIRTHDAY");
//
//					if (dataItem.hiHokenjyaNumber == null || dataItem.hiHokenjyaNumber.isEmpty()) {
//						/* �����̓G���[,��ی��ҏؓ��ԍ������͂���Ă��܂���B�ꗗ�Ŕ�ی��ҏؓ��ԍ����m�F���Ă��������B
//						 * [���s]�@�����i�J�i�j[%s]�A����[%s]�A���N����[%s] */
//						String sexName = dataItem.sex.equals("1") ? "�j��" : "����";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4751", this, params);
//						return;
//					}
//
//					if (dataItem.KensaDate == null || dataItem.KensaDate.isEmpty()) {
//						/* ���̓G���[,���f���ʃf�[�^�����݂��܂���B[���s]�@�����i�J�i�j[%s]�A����[%s]�A���N����[%s] */
//						String sexName = dataItem.sex.equals("1") ? "�j��" : "����";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4753", this, params);
//						return;
//					}
//
//					/*
//					 * ��ʃR�[�h���i�[����
//					 */
//					if( dataItem.daikouKikanNumber != null && ! dataItem.daikouKikanNumber.isEmpty() )
//					{
//						dataItem.syubetuCode = "1";
//					}else{
//						dataItem.syubetuCode = "6";
//					}
//
//					// ���{�敪���i�[����i���茒�f�� 1 �Œ�j
//					dataItem.jissiKubun = JISSIKUBUN_TOKUTEIKENSHIN;
//
//					/* �����敪���i�[����B */
//					String seikyuKubun = getSeikyuKubun(i);
//
//					if (seikyuKubun == null || seikyuKubun.isEmpty()) {
//						/* M4732�F�G���[,�����敪�̎擾�Ɏ��s���܂����B,0,0 */
//						JErrorMessage.show("M4733", this);
//						return;
//					}
//
//					dataItem.seikyuKubun = seikyuKubun;
//
//					String[] registKensa= JInputKessaiDataFrameCtrl.isNotExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (registKensa != null){
//						// edit s.inoue 20081119
//						if (!notExtMessage){
//							JErrorMessage.show("M3635", this);
//							notExtMessage = true;
//						}
//					}
//
//					String[] existKensa= JInputKessaiDataFrameCtrl.isExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (existKensa != null){
//						if (!notExtMessage){
//							JErrorMessage.show("M3636", this );
//							notExtMessage = true;
//						}
//					}
//
//					datas.add(dataItem);
//				}
//			}
//
//			if( count != 0 )
//			{
//				try{
//					// ���ρA�W�v��tran
//					JApplication.kikanDatabase.Transaction();
//
//					JOutputNitijiFrameData validatedData = new JOutputNitijiFrameData();
//
//					if( validatedData.setSyubetuCode((String)jComboBox_SyubetuCode.getSelectedItem()))
//					{
//						try {
//							/* ���Ϗ��� */
//							JKessaiProcess.runProcess(datas, JApplication.kikanNumber);
//
//						} catch (Exception e1) {
//							logger.error(e1.getMessage());
//							JApplication.kikanDatabase.rollback();
//							JErrorMessage.show("M4730", this);
//							return;
//						}
//
//						/* �W�v���� */
//						try{
//							if(JSyuukeiProcess.RunProcess(datas) == false)
//							{
//								JApplication.kikanDatabase.rollback();
//								JErrorMessage.show("M4731", this);
//								return;
//							}
//						} catch (Exception e2){
//							JApplication.kikanDatabase.rollback();
//							logger.error(e2.getMessage());
//							JErrorMessage.show("M4731", this);
//							return;
//						}
//
//						state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//
//						messageDialog.setMessageTitle("��������");
//						messageDialog.setText("�����������I�����܂���");
//						messageDialog.setVisible(true);
//					}
//					// ���ρA�W�v��Commit
//					JApplication.kikanDatabase.Commit();
//					// edit s.inoue 2009/10/26
//					searchRefresh();
//					table.setselectedRows(modelfixed,selectedRows);
//
//				}catch (Exception ex){
//					try{
//						JApplication.kikanDatabase.rollback();
//					}catch(SQLException exx){
//					}
//					ex.printStackTrace();
//					logger.error(ex.getMessage());
//				}
//			}
//		buttonCtrl();
//	}
//
//	private String getSeikyuKubun(int i) {
//		// �����敪���i�[����
//		String query = "SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE " +
//				"UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//				"KENSA_NENGAPI = " + JQueryConvert.queryConvert(result.get(i).get("KENSA_NENGAPI"));
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException ex){
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//		}
//
//		String seikyuKubun = null;
//		if (tbl != null && tbl.size() == 1) {
//			seikyuKubun = tbl.get(0).get("SEIKYU_KBN");
//		}
//
//		return seikyuKubun;
//	}
//
//	/**
//	 * �x����s�@�ւ��ݒ肳��Ă��邩�𒲂ׂ�B
//	 */
//	private boolean existsShiharaiDaikoKikan(int i) {
//
//		/* �x����s�@�֔ԍ��̗L���𒲂ׂ� */
//		String Query = "select * from T_KOJIN " +
//									" where " +
//									" UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//									" SIHARAI_DAIKOU_BANGO IS NULL";
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
//
//		if( ! tbl.isEmpty() ){
//			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * �����f�[�^�ҏW�����{�^���������ꂽ�ꍇ 2
//	 */
//	public void pushedSeikyuEditButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEIKYU:
//			// ������I������Ă���ꍇ�̂�
//			if( table.getSelectedRowCount() == 1 )
//			{
//				int[] selection = table.getSelectedRows();
//				int modelRow=0;
//	            for (int i = 0; i < selection.length; i++) {
//
//	                // �e�[�u�����f���̍s���ɕϊ�
//	                modelRow = table.convertRowIndexToModel(selection[i]);
//	                System.out.println("View Row: " + selection[i] + " Model Row: " + modelRow);
//	            }
//
//	            Hashtable<String, String> resultItem = result.get(modelRow);
//
//				String uketukeId = resultItem.get("UKETUKE_ID");
//				String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
//				String hihokenjyasyoNo = resultItem.get("HIHOKENJYASYO_NO");
//				String kensaNengapi = resultItem.get("KENSA_NENGAPI");
//
//				JInputKessaiDataFrameCtrl ctrl = new JInputKessaiDataFrameCtrl(
//						uketukeId,
//						hihokenjyasyoKigou,
//						hihokenjyasyoNo,
//						kensaNengapi,
//						datas);
//
//				// edit s.inoue 2009/11/25
//				JScene.CreateDialog(this,ctrl,new WindowRefreshEvent());
//
//				state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//				break;
//			}
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * �S�đI���{�^���������ꂽ�ꍇ 7
//	 */
//	public void pushedAllSelectButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEARCH:
//			// edit h.yoshitama 2009/09/03
//			for(int i = 0;i < modelfixed.getRowCount();i++ )
//			{
//				if( isAllSelect )
//					modelfixed.setData(true, i, 0);
//				else
//					modelfixed.setData(false, i, 0);
//			}
//
//			if( isAllSelect )
//			{
//				isAllSelect = false;
//			}else{
//				isAllSelect = true;
//			}
//
//			break;
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * �����{�^���������ꂽ�ꍇ �U
//	 */
//	public void pushedSearchButton( ActionEvent e )
//	{
//		// add s.inoue 2009/10/27
//		if(!searchCondition(false))
//			searchRefresh();
//	}
//
//	// add s.inoue 2010/02/03
//	/**
//	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
//	 */
//	public class WindowRefreshEvent extends WindowAdapter {
//		public void windowClosed(WindowEvent e) {
//			// �I����Ԃ�ێ�����
//			ArrayList<Integer> selectedRows = null;
//			selectedRows = table.getselectedRows(modelfixed,table);
//
//			// edit s.inoue 2010/02/03
//			state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//
//			// edit s.inoue 2010/02/03
//			// if(!searchCondition(false))
//			searchRefresh();
//			buttonCtrl();
//
//			table.setselectedRows(modelfixed,selectedRows);
//		}
//	}
//
//	// edit s.inoue 2009/10/27
//	// �����s�������ǉ�
//	private boolean searchCondition(boolean initialize){
//		boolean retflg = false;
//
//		buttonCtrl();
//
//		switch(state)
//		{
//		/* FALLTHROUGH */
//		case STATUS_INITIALIZED:
//		case STATUS_AFTER_SEARCH:
//		case STATUS_AFTER_SEIKYU:
//		case STATUS_AFTER_HL7:
//			// edit s.inoue 2009/10/26
//			// rowCount = tableRefresh();
//			// if( rowCount == 0 )
//			// {
//			// edit s.inoue 2009/10/27
//			// resultRefresh();
//			// state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
//			if (resultRefresh() == null){
//				// del s.inoue 2009/10/28
//				// state = JOutputHL7FrameState.STATUS_INITIALIZED;
//				// edit s.inoue 2009/10/27
//				retflg= true;
//			}else{
//				state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
//			}
//			break;
//		}
//		buttonCtrl();
//
//		return retflg;
//	}
//
//// del s.inoue 2009/09/14
////	/**
////	 * ���̓_�C�A���O
////	 */
////	private ISelectInputDialog dialog;
//
//	public void pushedSeikyuPrintButton( ActionEvent e )
//	{
//
//// del s.inoue 2009/09/14
//// ����I������̔p�~
////		dialog = JSelectInputDialogFactory.createDialog(this);
////		dialog.setVisible(true);
////		String printQuery = dialog.getPrintQuery();
////		System.out.println( dialog.getStatus() +  "");
////		if (dialog.getStatus() != RETURN_VALUE.OK){
////			break;
////		}
//
//		// edit ver2 s.inoue 2009/09/01
//		// �I���s�𒊏o����B
//		int rowCount = table.getRowCount();
//		// ����p
//		TreeMap<String, Object> printDataNitiji = new TreeMap<String, Object>();
//		// �X�e�[�^�X���
//		ProgressWindow progressWindow = new ProgressWindow(this, false,true);
//
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		// edit s.inoue 2009/11/02
//		ArrayList<String> keyList =  new ArrayList<String>();
//
//		// �I���`�F�b�N
//		// edit h.yoshitama 2009/09/03
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
//
//		// �I���s�Ȃ�
//		int selectedCount = selectedRows.size();
//		if (selectedCount == 0) {
//			JErrorMessage.show("M3548", this);return;
//		}
//
//		// edit ver2 s.inoue 2009/09/08
//		Hashtable<String, String> tmpKojin = new Hashtable<String, String>();
//
//		// ���v�f�[�^�쐬
//		for (int j = 0; j < selectedCount; j++) {
//
//			int k = selectedRows.get(j);
//
//			// del s.inoue 2009/09/01
//			progressWindow.setGuidanceByKey("common.frame.progress.guidance.main");
//			progressWindow.setVisible(true);
//
//			// ��tID
//			String uketukeId = result.get(k).get("UKETUKE_ID");
//			tmpKojin.put("UKETUKE_ID", uketukeId);
//
//			// edit ver2 s.inoue 2009/09/07
//			// ���f�N����
//			// String kensaNengapi = result.get(k).get("KENSA_NENGAPI");
//			// tmpKojin.put("KENSA_NENGAPI",kensaNengapi );
//
//			Nikeihyo nikeihyo = new Nikeihyo();
//
//			if (!nikeihyo.setPrintSeikyuNitijiDataSQL(tmpKojin)){
//			 	// �f�[�^�ڑ����s
//				logger.warn("�����f�[�^�쐬���s");
//			 	JErrorMessage.show("M3530", this);
//			}
//
//			// edit ver2 s.inoue 2009/09/01
//			// Iraisho IraiData = new Iraisho();
//			// if (!IraiData.setPrintKojinIraiDataSQL(tmpKojin)) {
//			// 	// �f�[�^�ڑ����s
//			// 	JErrorMessage.show("M3530", this);
//			// }
//			// PrintSeikyuNitiji seikyu = new PrintSeikyuNitiji();
//			// if(!seikyu.setPrintSeikyuDataSQL(printQuery)){
//			//
//			// }
//
//			// ��� ��tID:����f�[�^
//			printDataNitiji.put(String.valueOf(uketukeId), nikeihyo);
//			keyList.add(String.valueOf(uketukeId));
//		}
//
//		/*
//		 * Kikan�쐬
//		 */
//		Kikan kikanData = new Kikan();
//		if (!kikanData.setPrintKikanDataSQL()) {
//			// �f�[�^�ڑ����s
//			JErrorMessage.show("M4941", this);
//		}
//
//		Hashtable<String, Object> printData = new Hashtable<String, Object>();
//		printData.put("Kikan", kikanData);
//
//		// ������s
//		// edit ver2 s.inoue 2009/09/03
//		PrintSeikyuNitiji nitiji = new PrintSeikyuNitiji();
//		nitiji.setQueryNitijiResult(printDataNitiji);
//		nitiji.setQueryResult(printData);
//		nitiji.setKeys(keyList);
//
//		// PrintSeikyuNitiji seikyu = new PrintSeikyuNitiji();
//		progressWindow.setVisible(false);
//
//		try {
//			nitiji.beginPrint();
//		} catch (PrinterException err) {
//			err.printStackTrace();
//			JErrorMessage.show("M3531", this);
//		} finally {
//			// progressWindow.setVisible(false);
//		}
//	}
//
//	public void itemStateChanged( ItemEvent e )
//	{
//	}
//
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		int mod = keyEvent.getModifiersEx();
//
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//		case KeyEvent.VK_F9:
//			logger.info(jButton_Seikyu.getText());
//			pushedSeikyuButton(null);break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_SeikyuListPrint.getText());
//			pushedSeikyuPrintButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_SeikyuEdit.getText());
//			pushedSeikyuEditButton(null);break;
//
////		case KeyEvent.VK_S:
////			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
////				logger.info(jButton_Search.getText());
////				pushedSearchButton( null );
////			}
////			break;
//		}
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		Object source = e.getSource();
//
//		if( source == jButton_End			)
//		{
//			logger.info(jButton_End.getText());
//			pushedEndButton( e );
//		}
//		// ���������{�^��
//		else if( source == jButton_Seikyu		)
//		{
//			logger.info(jButton_Seikyu.getText());
//			pushedSeikyuButton( e );
//		}
//		else if( source == jButton_SeikyuEdit		)
//		{
//			logger.info(jButton_SeikyuEdit.getText());
//			pushedSeikyuEditButton( e );
//		}
//		else if( source == jButton_SeikyuListPrint		)
//		{
//			logger.info(jButton_SeikyuListPrint.getText());
//			pushedSeikyuPrintButton( e );
//		}
//		else if( source == jButton_AllSelect	)
//		{
//			logger.info(jButton_AllSelect.getText());
//			pushedAllSelectButton( e );
//		}
//		else if( source == jButton_Search		)
//		{
//			logger.info(jButton_Search.getText());
//			pushedSearchButton( e );
//		}
//
//	}
//// edit s.inoue 2009/10/26
////	/**
////	 * �������ʂ��e�[�u���ɒǉ�����B
////	 */
////	private Object[][] addRowToTable() {
////		// del s.inoue 2009/10/26
////		// model.clearAllRow();
////		// modelfixed.clearAllRow();
////
////		// edit s.inoue 2009/10/26
////		Hashtable<String, String> resultItem;
////		Object[][] rowcolumn = new Object[result.size()][16];
////
////		for(int i = 0;i < result.size();i++ )
////		{
////			// edit s.inoue 2009/10/26
////			// Vector<String> rowcolumn = new Vector<String>();
////			// Vector<String> rowfixed = new Vector<String>();
////
////			resultItem = result.get(i);
////
////			// edit s.inoue 2009/10/26
////			/* �`�F�b�N�{�b�N�X */
////			// rowcolumn.add(null);
////			rowcolumn[i][COLUMN_INDEX_CHECK] = null;
////			/* �N�x */
////			// rowcolumn.add(resultItem.get("NENDO"));
////			rowcolumn[i][COLUMN_INDEX_NENDO] = resultItem.get("NENDO");
////			/* ��f�������ԍ� */
////			// rowcolumn.add(resultItem.get("JYUSHIN_SEIRI_NO"));
////			rowcolumn[i][COLUMN_INDEX_JYUSHIN_SEIRI_NO] = resultItem.get("JYUSHIN_SEIRI_NO");
////			/* �����i�J�i�j */
////			// rowcolumn.add(resultItem.get("KANANAME"));
////			rowcolumn[i][COLUMN_INDEX_KANANAME] = resultItem.get("KANANAME");
////			/* �����i���O�j */
////			// rowcolumn.add(resultItem.get("NAME"));
////			rowcolumn[i][COLUMN_INDEX_NAME] = resultItem.get("NAME");
////
////			/* ���� */
////			// edit s.inoue 2009/10/26
////			// String sexCode = resultItem.get("SEX");
////			// String sexText = "";
////			// if (sexCode.equals("1")) {
////			//	sexText = "�j��";
////			// }
////			// else if(sexCode.equals("2")){
////			// 	sexText = "����";
////			// }
////			// rowcolumn.add(sexText);
////			rowcolumn[i][COLUMN_INDEX_SEX] = resultItem.get("SEX").equals("1") ? "�j��" : "����";
////			/* ���N���� */
////			// rowcolumn.add(resultItem.get("BIRTHDAY"));
////			rowcolumn[i][COLUMN_INDEX_BIRTHDAY] = resultItem.get("BIRTHDAY");
////			/* ���f���{�� */
////			// rowcolumn.add(resultItem.get("KENSA_NENGAPI"));
////			rowcolumn[i][COLUMN_INDEX_KENSA_NENGAPI] = resultItem.get("KENSA_NENGAPI");
////			/* �ی��Ҕԍ� */
////			// rowcolumn.add(resultItem.get("HKNJANUM"));
////			rowcolumn[i][COLUMN_INDEX_HKNJANUM] = resultItem.get("HKNJANUM");
////			/* �x����s�@�֔ԍ� */
////			// rowcolumn.add(resultItem.get("SIHARAI_DAIKOU_BANGO"));
////			rowcolumn[i][COLUMN_INDEX_SIHARAI_DAIKOU_BANGO] = resultItem.get("SIHARAI_DAIKOU_BANGO");
////			/* ��ی��ҏؓ��L�� */
////			// rowcolumn.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
////			rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_KIGOU] = resultItem.get("HIHOKENJYASYO_KIGOU");
////			/* ��ی��ҏؓ��ԍ� */
////			// rowcolumn.add(resultItem.get("HIHOKENJYASYO_NO"));
////			rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_NO] = resultItem.get("HIHOKENJYASYO_NO");
////			/* �P�����v */
////			// rowcolumn.add(resultItem.get("TANKA_GOUKEI"));
////			rowcolumn[i][COLUMN_INDEX_TANKA_GOUKEI] = resultItem.get("TANKA_GOUKEI");
////			/* �������S���v */
////			// rowcolumn.add(resultItem.get("MADO_FUTAN_GOUKEI"));
////			rowcolumn[i][COLUMN_INDEX_MADO_FUTAN_GOUKEI] = resultItem.get("MADO_FUTAN_GOUKEI");
////			/* �������z���v */
////			// rowcolumn.add(resultItem.get("SEIKYU_KINGAKU"));
////			rowcolumn[i][COLUMN_INDEX_SEIKYU_KINGAKU] = resultItem.get("SEIKYU_KINGAKU");
////			/* �X�V���� */
////			// rowcolumn.add(resultItem.get("UPDATE_TIMESTAMP").replaceAll("-",""));
////			rowcolumn[i][COLUMN_INDEX_UPDATE_TIMESTAMP] = resultItem.get("UPDATE_TIMESTAMP").replaceAll("-","");
////			// edit s.inoue 2009/10/26
////			// model.addData(rowcolumn);
////			// modelfixed.addData(rowfixed);
////		}
////		return rowcolumn;
////	}
//	// del s.inoue 2009/10/26
////	/**
////	 * �g�k�V�O���o�̓{�^���������ꂽ�ꍇ �S
////	 */
////	public void pushedHL7CopyButton( ActionEvent e )
////	{
////		JDirChooser dirSelect = new JDirChooser();
////
////		switch(state)
////		{
////		case STATUS_AFTER_HL7:
////			JFileChooser Chooser = new JFileChooser(JPath.ZIP_OUTPUT_DIRECTORY_PATH);
////
////			// �t�@�C���I���_�C�A���O�̕\��
////			// TODO �o�͂���t�@�C����I�����Ă��������B
////			if( Chooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
////			{
////				// TODO �o�͐�̃t�H���_��I�����Ă��������B
////				if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION )
////				{
////					try {
////						if( JFileCopy.copyFile(
////								Chooser.getSelectedFile().getAbsolutePath(),			// �R�s�[��
////								dirSelect.getSelectedFile().getPath() + File.separator +
////								Chooser.getSelectedFile().getName() ) != true )
////																						// �R�s�[��
////						{
////							JErrorMessage.show("M4720", this);
////						}else{
////							JErrorMessage.show("M4721", this);
////						}
////					} catch (IOException e1) {
////						e1.printStackTrace();
////						JErrorMessage.show("M4720", this);
////					}
////				}
////			}
////
////			state = JOutputHL7FrameState.STATUS_AFTER_HL7;
////		}
////
////		buttonCtrl();
////	}
//
//// del s.inoue 2009/09/18
////	/**
////	 * �g�k�V�o�̓{�^���������ꂽ�ꍇ �R
////	 */
////	public void pushedHL7OutputButton( ActionEvent e )
////	{
////		switch(state)
////		{
////			case STATUS_AFTER_SEIKYU:
////				/* datas �́AHL7 �o�͎��ɕK�v�ȏ�� */
////				if( JOutputHL7.RunProcess(datas) )
////				{
////					// ����ɏI�������ꍇ
////					state = JOutputHL7FrameState.STATUS_AFTER_HL7;
////					tableRefresh();
////				}
////
////				break;
////		}
////		buttonCtrl();
////
////		// HL7�o�͏����I�����Ƀ��b�Z�[�W�{�b�N�X��\��
////		messageDialog.setMessageTitle("�g�k�V�o��");
////
////		String message = "";
////		if (state == JOutputHL7FrameState.STATUS_AFTER_HL7) {
////			message = "HL7�o�͏������I�����܂���";
////		}
////		else {
////			message = "HL7�o�͏����Ɏ��s���܂���";
////		}
////		messageDialog.setText(message);
////		messageDialog.setVisible(true);
////	}
//
//// del s.inoue 2009/09/14
////	/**
////	 * ����p�� SQL ���쐬����B
////	 */
////	private String createPrintQuery() {
////		// �������ڂ𔲂����N�G��
////		StringBuilder strsb = new StringBuilder(
////				"SELECT DISTINCT " +
////
////				/* �I��������������Ńf�[�^���擾����B���̌セ�̂܂܈�������ցB
////				 * ��tID,���N����,����,����,��f�������ԍ�,��ی��ҏؓ��L��,��ی��ҏؓ��ԍ�,�����N����,
////				 * �ϊ�����,�ی��Ҕԍ��i�l�j,�x����s�@�֔ԍ�,�J�i����,�N�x,�P�����v,�������S���v,�������z���v */
////				dialog.getPrintQuery() +
////
////				"FROM T_KOJIN AS KOJIN " +
////				"LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI " +
////				"ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID " +
////				"LEFT JOIN T_KESAI_WK AS KESAI " +
////				" ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID " +
////				" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO " +
////				" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR " +
////				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR " +
////				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' " +
////				" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 " +
////				" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO " +
////				" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO " +
////				" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID " +
////				" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI "
////		);
////		return strsb.toString();
////	}
//}
