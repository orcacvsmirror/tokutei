package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;

/**
 * ��^���}�X�^�����e�i���X
 */
public class JNayoseMaintenanceListFrameCtl extends JNayoseMaintenanceListFrame
{
	private static final int COLUMN_INDEX_NAYOSE_NO = 0;
	private static final int COLUMN_INDEX_JYUSHIN_SEIRI_NO = 1;
	private static final int COLUMN_INDEX_KANANAME = 2;
	private static final int COLUMN_INDEX_NAME = 3;
	private static final int COLUMN_INDEX_BIRTHDAY = 4;
	private static final int COLUMN_INDEX_SEX = 5;
	private static final int COLUMN_INDEX_HOME_ADRS = 6;
	private static final int COLUMN_INDEX_HIHOKENJYASYO_KIGOU = 7;
	private static final int COLUMN_INDEX_HIHOKENJYASYO_NO = 8;
	private static final int COLUMN_INDEX_UPDATETIME = 9;

	private HashMap<Integer,String> hm = null;
	// edit s.inoue 2009/11/14
	// private JSimpleTable model = new JSimpleTable();
	private DefaultTableModel dmodel = null;
	private TableRowSorter<TableModel> sorter =null;

	private JSimpleTable table=null;
	private JSimpleTable modelfixed= null;
	Object[][] rowcolumn = null;

	// edit s.inoue 2009/11/14
	private String[] header = {"��f�ҕR�t��ID", "��f�������ԍ�", "�����i�J�i�j","�����i�����j", "���N����", "����", "�Z��", "��ی��ҏؓ��L��", "��ی��ҏؓ��ԍ�", "�X�V����"};
	// private String[] solidheader = {"��f�ҕR�t��ID", "��f�������ԍ�", "�����i�J�i�j"};
	private ArrayList<Hashtable<String, String>> result;

	// s.inoue 20080807
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JNayoseMaintenanceListFrameCtl.class);

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	public JNayoseMaintenanceListFrameCtl() {
		initializeSetting();
	}

	// add s.inoue 2009/11/14
	private void initializeSetting(){

		//this.initializeTable();

		dmodel = new DefaultTableModel(resultRefresh(),header){
	      public boolean isCellEditable(int row, int column) {
	    	boolean retflg = false;
	    	if (column >9){
	    		retflg = true;
	      	}
	        return retflg;
	      }
	    };

		sorter = new TableRowSorter<TableModel>(dmodel);
		table = new JSimpleTable(dmodel);
		modelfixed = new JSimpleTable(dmodel);

		modelfixed.setPreferedColumnWidths(new int[] { 100, 100, 175 });
		table.setPreferedColumnWidths(new int[] { 150, 80, 50, 120, 120, 120, 180 });
		// add s.inoue 2009/10/23
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
		// add s.inoue 2009/12/02
		functionListner();

		// add s.inoue 2009/10/26
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

        jPanel.add(scroll);

		dmodel.setDataVector(resultRefresh(),header);

		// �G���^�[�L�[�̏���
		table.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this, jButton_Change));
		// edit s.inoue 2009/12/12
		// �_�u���N���b�N�̏���
		table.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Change));
		modelfixed.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Change));
		// table.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Change));

		// 4��ȉ��͌Œ�A�ȍ~�͕ϓ�
		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<3) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                modelfixed.getColumnModel().getColumn(i).setResizable(false);
            }else{
                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
            }
        }

	   TableColumnModel columnsfix = modelfixed.getColumnModel();
	   for(int i=0;i<columnsfix.getColumnCount();i++) {

		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
	   }

	   TableColumnModel columns = table.getColumnModel();
	   for(int i=0;i<columns.getColumnCount();i++) {

		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
	   }

	   // add s.inoue 2009/11/12
	   table.addHeader(this.header);
	   table.refreshTable(); modelfixed.refreshTable();

	   // edit s.inoue 2009/11/09
	   // �����I��
	   if (table.getRowCount() > 0) {
		   table.setRowSelectionInterval(0, 0);
	   }
	}

	/* focus������ */
	private void initilizeFocus(){
		// focus����
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(table);
		this.focusTraversalPolicy.addComponent(this.table);
		this.focusTraversalPolicy.addComponent(this.jButton_Nayose);
		this.focusTraversalPolicy.addComponent(this.jButton_Change);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(this.jButton_End);
	}

	// add s.inoue 2009/12/02
	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	// add s.inoue 2010/04/21
	/*
	 * �������t���b�V������
	 */
	private void searchRefresh(){

		modelfixed.setPreferedColumnWidths(new int[] { 100, 100, 175 });
		table.setPreferedColumnWidths(new int[] { 150, 80, 50, 120, 120, 120, 180 });
		// add s.inoue 2009/10/23
		modelfixed.setSelectionModel(table.getSelectionModel());

		    // validationCheck
		    Object [][] resultref=resultRefresh();
		    if (resultref == null)
		    	return;

		    // edit s.inoue 2010/02/16
		    if (resultref.length == 0){
		    	JErrorMessage.show("M3550", this);
		    }

		    // add s.inoue 2009/10/26
		    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
	            if(i<5) {
	                table.removeColumn(table.getColumnModel().getColumn(i));
	            }
	        }

		    dmodel.setDataVector(resultref,header);

			// 4��ȉ��͌Œ�A�ȍ~�͕ϓ�
			for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
	            if(i<3) {
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

	  	// add s.inoue 2009/11/12
		   table.addHeader(this.header);
		   table.refreshTable(); modelfixed.refreshTable();

		   // edit s.inoue 2009/11/09
		   // �����I��
		   if (table.getRowCount() > 0) {
			   table.setRowSelectionInterval(0, 0);
		   }
	}

	// edit s.inoue 2009/10/26
	// �ꗗ�p�f�[�^�擾
	private Object[][] resultRefresh()
	{
		hm= new HashMap<Integer,String>();

		String sql = buildSQL();

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		rowcolumn = new Object[result.size()][10];

		Hashtable<String, String> ResultItem;

		for (int i = 0; i < result.size(); i++) {
			ResultItem = result.get(i);

			String updtime = ResultItem.get("UPDATE_TIMESTAMP").replaceAll("-", "");

			rowcolumn[i][COLUMN_INDEX_NAYOSE_NO] =ResultItem.get("NAYOSE_NO");
			rowcolumn[i][COLUMN_INDEX_JYUSHIN_SEIRI_NO] = ResultItem.get("JYUSHIN_SEIRI_NO");
			rowcolumn[i][COLUMN_INDEX_KANANAME] = ResultItem.get("KANANAME");
			rowcolumn[i][COLUMN_INDEX_NAME] =ResultItem.get("NAME");
			rowcolumn[i][COLUMN_INDEX_BIRTHDAY] =  ResultItem.get("BIRTHDAY");
			rowcolumn[i][COLUMN_INDEX_SEX] = ResultItem.get("SEX").equals("1") ? "�j��" : "����";
			rowcolumn[i][COLUMN_INDEX_HOME_ADRS] = ResultItem.get("HOME_ADRS");
			rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_KIGOU] = ResultItem.get("HIHOKENJYASYO_KIGOU");
			rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_NO] = ResultItem.get("HIHOKENJYASYO_NO");
			rowcolumn[i][COLUMN_INDEX_UPDATETIME] = updtime;

			hm.put(i, ResultItem.get("UKETUKE_ID"));
		}

		return rowcolumn;
	}
	/**
	 * SQL����g�ݗ��Ă�
	 *
	 * @return SQL��
	 */
	private String buildSQL() {
		StringBuilder sb = new StringBuilder();
		// edit ver2 s.inoue 2009/07/28
		sb.append("SELECT TN.NAYOSE_NO, TK.JYUSHIN_SEIRI_NO, TK.NAME, TK.KANANAME, TK.BIRTHDAY, TK.SEX, TK.HOME_ADRS,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO, TN.UPDATE_TIMESTAMP, TN.UKETUKE_ID");
		sb.append(" FROM T_NAYOSE TN ");
		sb.append(" LEFT JOIN T_KOJIN TK ON TN.UKETUKE_ID = TK.UKETUKE_ID ");
		sb.append(" order by NAYOSE_NO,UPDATE_TIMESTAMP ");
		return sb.toString();
	}

//	/**
//	 * �e�[�u���̏�����
//	 */
//	public void initializeTable() {
//		//�e�[�u���̍ēǂݍ��݂��s��
//		model.clearAllColumn();
//		model.clearAllRow();
//
//		// edit ver2 s.inoue 2009/09/07
//		modelfixed.clearAllColumn();
//		modelfixed.clearAllRow();
//
//		// edit h.yoshitama 20090101
//		//model.setPreferedColumnWidths(new int[] { 100, 100, 150, 150, 80, 50, 120, 120, 120, 180 });
//		modelfixed.setPreferedColumnWidths(new int[] { 100, 100, 150 });
//		model.setPreferedColumnWidths(new int[] { 150, 80, 50, 120, 120, 120, 180 });
//
//		//�e�[�u���̏����ݒ�
//		// edit h.yoshitama 2009/09/01
//		//model.addHeader("��f�ҕR�t��ID");
//		//model.addHeader("��f�������ԍ�");
//		//model.addHeader("�����i�����j");
//		//model.addHeader("�����i�J�i�j");
//		//model.addHeader("���N����");
//		//model.addHeader("����");
//		//model.addHeader("�Z��");
//		//model.addHeader("��ی��ҏؓ��L��");
//		//model.addHeader("��ی��ҏؓ��ԍ�");
//		//model.addHeader("�X�V����");
//
//		modelfixed.addHeader(solidheader);
//		model.addHeader(header);
//
//		hm= new HashMap<Integer,String>();
//
//		//DB�ɃA�N�Z�X���e�[�u���̗v�f���擾����
//		try {
//			StringBuilder sb = new StringBuilder();
//			// edit ver2 s.inoue 2009/07/28
//			sb.append("SELECT TN.NAYOSE_NO, TK.JYUSHIN_SEIRI_NO, TK.NAME, TK.KANANAME, TK.BIRTHDAY, TK.SEX, TK.HOME_ADRS,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO, TN.UPDATE_TIMESTAMP, TN.UKETUKE_ID");
//			sb.append(" FROM T_NAYOSE TN ");
//			sb.append(" LEFT JOIN T_KOJIN TK ON TN.UKETUKE_ID = TK.UKETUKE_ID ");
//			sb.append(" order by NAYOSE_NO,UPDATE_TIMESTAMP ");
//
//			// edit h.yoshitama 2009/09/01
//			//String[] row = new String[10];
//			String[] rowfixed = new String[3];
//			String[] row = new String[7];
//
//			ArrayList<Hashtable<String, String>> Result;
//			Hashtable<String, String> ResultItem;
//
//			Result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//
//			for (int i = 0; i < Result.size(); i++) {
//				ResultItem = Result.get(i);
//
//				String updtime = ResultItem.get("UPDATE_TIMESTAMP").replaceAll("-", "");
//
//				// edit h.yoshitama 2009/09/01
//				//row[0] = ResultItem.get("NAYOSE_NO");
//				//row[1] = ResultItem.get("JYUSHIN_SEIRI_NO");
//				//row[2] = ResultItem.get("NAME");
//				//row[3] = ResultItem.get("KANANAME");
//				//row[4] = ResultItem.get("BIRTHDAY");
//				//row[5] = ResultItem.get("SEX").equals("1") ? "�j��" : "����";
//				// edit ver2 s.inoue 2009/07/28
//				//row[6] = ResultItem.get("HOME_ADRS");
//				//row[7] = ResultItem.get("HIHOKENJYASYO_KIGOU");
//				//row[8] = ResultItem.get("HIHOKENJYASYO_NO");
//				//row[9] = updtime;
//				rowfixed[0] = ResultItem.get("NAYOSE_NO");
//				rowfixed[1] = ResultItem.get("JYUSHIN_SEIRI_NO");
//				rowfixed[2] = ResultItem.get("KANANAME");
//				row[0] = ResultItem.get("NAME");
//				row[1] = ResultItem.get("BIRTHDAY");
//				row[2] = ResultItem.get("SEX").equals("1") ? "�j��" : "����";
//				row[3] = ResultItem.get("HOME_ADRS");
//				row[4] = ResultItem.get("HIHOKENJYASYO_KIGOU");
//				row[5] = ResultItem.get("HIHOKENJYASYO_NO");
//				row[6] = updtime;
//
//				hm.put(i, ResultItem.get("UKETUKE_ID"));
//
//				// edit h.yoshitama 2009/09/01
//				modelfixed.addData(rowfixed);
//				model.addData(row);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JErrorMessage.show("M4001", this);
//			return;
//		}
//		// add ver2 s.inoue 2009/07/14
//		modelfixed.setSelectionModel(table.getSelectionModel());
//
//		Vector<JSimpleTableCellPosition> forbitList = new Vector<JSimpleTableCellPosition>();
//		forbitList.add(new JSimpleTableCellPosition(-1,-1));
//		// edit s.inoue 2009/09/25
//		modelfixed.setCellEditForbid(forbitList);
//		model.setCellEditForbid(forbitList);
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel columns = model.getColumnModel();
//        for(int i=0;i<columns.getColumnCount();i++) {
//
//            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//        }
//
//        // add 2009/09/01
//        TableColumnModel columnsfix = modelfixed.getColumnModel();
//        for(int i=0;i<columnsfix.getColumnCount();i++) {
//
//        	columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//        			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
//        }
//
//		// edit s.inoue 2009/10/20
//		// �����I��
//		if (model.getRowCount() > 0) {
//			model.setRowSelectionInterval(0, 0);
//		} else {
//			jButton_Nayose.requestFocus();
//		}
//	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	// add ver2 s.inoue 2009/07/24
	/**
	 * ���񂹃{�^��
	 */
	public void pushedNayoseButton(ActionEvent e) {

		// add ver2 s.inoue 2009/07/06
		DBYearAdjuster yajuster = new DBYearAdjuster();
		// �f�[�^�x�[�X�ؒf
		try {
			JApplication.kikanDatabase.Shutdown();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}

		yajuster.callfixedNayose(JApplication.kikanNumber);

		try {
			JApplication.kikanDatabase = JConnection
				.ConnectKikanDatabase(JApplication.kikanNumber);
			// edit s.inoue 2009/11/14
			// initializeTable();
			resultRefresh();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * �ύX�{�^��
	 */
	public void pushedChangeButton(ActionEvent e) {
		if (table.getSelectedRowCount() == 1) {
			// edit s.inoue 2009/12/04 ����␳
			// �R�t��ID,�����ԍ�,��������,�����J�i,���N����,����,�X�V����
			String himotukeID = (String) modelfixed.getData(table.getSelectedRow()).get(0);
			String name = (String) modelfixed.getData(table.getSelectedRow()).get(3);
			String nameKana = (String) table.getData(table.getSelectedRow()).get(2);
			String birthDay = (String) table.getData(table.getSelectedRow()).get(4);
			String sex = (String) table.getData(table.getSelectedRow()).get(5);
			String home = (String) table.getData(table.getSelectedRow()).get(6);
			String hiKigou = (String) table.getData(table.getSelectedRow()).get(7);
			String hiBangou = (String) table.getData(table.getSelectedRow()).get(8);
			String updatetime = (String) table.getData(table.getSelectedRow()).get(9);
			String uketukeID =hm.get(table.getSelectedRow());

			JScene.CreateDialog(this,
					new JNayoseMaintenanceEditFrameCtl(himotukeID,uketukeID,name,nameKana,birthDay,sex,home,hiKigou,hiBangou),
					new WindowRefreshEvent());

			// edit s.inoue 2009/12/04
			resultRefresh();
		}
	}

	/**
	 * �폜�{�^��
	 */
	public void pushedDeleteButton(ActionEvent e) {
		if (table.getSelectedRowCount() == 0) {
			return;
		}

		RETURN_VALUE Ret = JErrorMessage.show("M4002", this);

		if (Ret == RETURN_VALUE.YES) {

			try {

				JApplication.kikanDatabase.Transaction();

				int[] selectedRowIndeces = table.getSelectedRows();

				for (int i = selectedRowIndeces.length - 1; i >= 0; i--) {

					int selectedIndex = selectedRowIndeces[i];
					// edit ver2 s.inoue 2009/06/23
					//String Key = (String) model.getData(selectedIndex, 0);
					String Key =hm.get(selectedIndex);

					String Query = new String(
							"DELETE FROM T_NAYOSE WHERE UKETUKE_ID = "
									+ JQueryConvert.queryConvert(Key));

						JApplication.kikanDatabase.sendNoResultQuery(Query);
				}
				// edit ver2 s.inoue 2009/07/08
				JApplication.kikanDatabase.Commit();
				// edit s.inoue 2009/11/14
				// initializeTable();
				// edit s.inoue 2010/04/23
				// resultRefresh();
				searchRefresh();
			} catch (SQLException e1) {
				e1.printStackTrace();
				JErrorMessage.show("M4003", this);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		if (e.getSource() == jButton_Nayose) {
			logger.info(jButton_Nayose.getText());
			pushedNayoseButton(e);
		}
		if (e.getSource() == jButton_Change) {
			logger.info(jButton_Change.getText());
			pushedChangeButton(e);
		}
		if (e.getSource() == jButton_Delete) {
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
			logger.info(jButton_Nayose.getText());
			pushedNayoseButton(null);break;
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
			// edit s.inoue 2009/11/14
			// initializeTable();
//			resultRefresh();
//			table.clearAllColumn();
//			modelfixed.clearAllColumn();
//			initializeSetting();
			// edit s.inoue 2010/04/23
			searchRefresh();
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

