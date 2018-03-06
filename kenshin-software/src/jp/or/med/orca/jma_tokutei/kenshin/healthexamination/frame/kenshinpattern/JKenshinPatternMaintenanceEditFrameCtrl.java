package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.origine.JKenshinPatternMaintenanceEditFrameData;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleColorTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;

import org.apache.log4j.Logger;

/**
 * ���f�p�^�[�������e�i���X�i�ҏW�j
 */


//class StripeTableRenderer extends DefaultTableCellRenderer {
//	  private static final Color evenColor = new Color(240, 240, 255);
//	  @Override public Component getTableCellRendererComponent(JTable table, Object value,
//	                           boolean isSelected, boolean hasFocus,
//	                           int row, int column) {
//	    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//	    if(isSelected) {
//	      setForeground(table.getSelectionForeground());
//	      setBackground(table.getSelectionBackground());
//	    }else{
//	      setForeground(table.getForeground());
//	      setBackground((row%2==0)?evenColor:table.getBackground());
//	    }
//	    setHorizontalAlignment((value instanceof Number)?RIGHT:LEFT);
//	    return this;
//	  }
//	}

public class JKenshinPatternMaintenanceEditFrameCtrl extends JKenshinPatternMaintenanceEditFrame
{

	private static final long serialVersionUID = -5261660570076737239L;	// edit n.ohkubo 2014/10/01�@�ǉ�
	
	// add h.yoshitama 2009/11/19
	private static final String CODE_METABO_HANTEI = "9N501000000000011";
	private static final String CODE_HOKEN_SHIDOU = "9N506000000000011";

	private String patternNumber = null;
	// ������p�^�[��
	private String cmbPatternNumber = null;

	private JSimpleColorTable leftTable = new JSimpleColorTable();
	private JSimpleColorTable rightTable = new JSimpleColorTable();
//	private JSimpleTable leftTable = new JSimpleTable();
//	private JSimpleTable rightTable = new JSimpleTable();

	/* �t�H�[�J�X�ړ����� */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private static Logger logger = Logger.getLogger(JKenshinPatternMaintenanceEditFrameCtrl.class);

	// add s.inoue 2011/09/01
	private IDialog patternSelectDialog;
//	private static Integer kenshinPatternNumver = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	
	// edit n.ohkubo 2014/10/01�@�ǉ�
	TableRowSorter<TableModel> tableRowSorterLeft;
	TableRowSorter<TableModel> tableRowSorterRight;
	
	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
//	// add s.inoue 2010/01/15
//	/* �ꗗ�p�f�[�^�擾 */
//	private Object[][] resultRefresh(String PatternNum)
//	{
//		ArrayList<Hashtable<String, String>> result = null;
//		try {
//			String sb = new String("SELECT K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO = " +
//					JQueryConvert.queryConvert(this.patternNumber));
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		Object[][] rowcolumn = new Object[result.size()][16];
//		int counti = 0;
//		List<String> fields = new ArrayList<String>();
//
//		for (Hashtable<String, String> record : result) {
//
////			model.addData(fields.toArray(new String[0]));
////
////			String[] col = fields.toArray(new String[0]);
////			for (int i = 0; i < col.length; i++) {
////
////			}
//		}
//		return rowcolumn;
//	}

	/**
	 * @param PatternNum �ҏW���錒�f�p�^�[���̔ԍ�
	 */
	public JKenshinPatternMaintenanceEditFrameCtrl(String PatternNum)
	{
		ArrayList<Hashtable<String,String>> Result = new ArrayList<Hashtable<String,String>>();
		Hashtable<String,String> ResultItem = new Hashtable<String,String>();
		String Query = null;

		//�󂯓n���ꂽ���f�p�^�[���ԍ���\������
		this.patternNumber = PatternNum;

		// eidt s.inoue 2011/12/20
		try{
			Query = new String("SELECT K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO = " +
					JQueryConvert.queryConvert(this.patternNumber));
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
			ResultItem = Result.get(0);
		}catch(SQLException e){
			e.printStackTrace();
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3920",this);
			dispose();
			return;
		}

		jTextField_PatternName.setText(ResultItem.get("K_P_NAME"));
		jTextField_PatternName.setToolTipText(ResultItem.get("K_P_NAME"));	// edit n.ohkubo 2014/10/01�@�ǉ�
		jTextField_PatternName.setEditable(false);

		//�e�[�u���̏�����
		// eidt s.inoue 2011/12/19
		JSimpleTableScrollPanel leftPanel = new JSimpleTableScrollPanel(leftTable);
		JSimpleTableScrollPanel rightPanel = new JSimpleTableScrollPanel(rightTable);

		jPanel_Left.add(leftPanel);
		jPanel_Right.add(rightPanel);
		leftTable.addHeader("���ڃR�[�h");
		leftTable.addHeader("���ږ�");
		leftTable.addHeader("�������@");
		leftTable.addHeader("���");
		leftTable.addHeader("SEQ");
		rightTable.addHeader("���ڃR�[�h");
		rightTable.addHeader("���ږ�");
		rightTable.addHeader("�������@");
		rightTable.addHeader("���");
		rightTable.addHeader("SEQ");

		JSimpleTableCellPosition iSetColumnList[] = {
				new JSimpleTableCellPosition(-1,-1)
				};

		// add s.inoue 2011/09/07
		leftTable.setPreferedColumnWidths(new int[] {110,120,120,32, 35});
		rightTable.setPreferedColumnWidths(new int[] {110,120,120,32, 35});

		leftTable.setCellEditForbid(iSetColumnList);
		rightTable.setCellEditForbid(iSetColumnList);
		// edit s.inoue 2009/09/14
		leftTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		rightTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// add s.inoue 2011/12/22
//		leftTable.setSelectionBackground(Color.BLUE);
//		leftTable.setSelectionForeground(Color.BLACK);
//		rightTable.setSelectionBackground(Color.BLUE);
//		rightTable.setSelectionForeground(Color.BLACK);

		refreshTable(false);
		updateButtonsState();

// del s.inoue 2012/07/10
//		// focus����
//		// edit s.inoue 2009/10/07
//		// edit h.yoshitama 2009/11/20
//		if((patternNumber.equals("1"))||(patternNumber.equals("2"))){
//			this.focusTraversalPolicy = new JFocusTraversalPolicy();
//			this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//			this.focusTraversalPolicy.setDefaultComponent(jButton_End);
//			this.focusTraversalPolicy.addComponent(jButton_End);
//		}else{
//			this.focusTraversalPolicy = new JFocusTraversalPolicy();
//			this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//			this.focusTraversalPolicy.setDefaultComponent(jButton_Sort);
//			this.focusTraversalPolicy.addComponent(jButton_Sort);
//			this.focusTraversalPolicy.addComponent(jButton_ToTop);
//			this.focusTraversalPolicy.addComponent(jButton_ToLeft);
//			this.focusTraversalPolicy.addComponent(jButton_ToRight);
//			this.focusTraversalPolicy.addComponent(jButton_ToDown);
//			this.focusTraversalPolicy.addComponent(jButton_Register);
//			this.focusTraversalPolicy.addComponent(jButton_Clear);
//			this.focusTraversalPolicy.addComponent(jButton_End);
//		}
//
//		// add s.inoue 2009/12/02
//		Component comp =null;
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//		leftTable.addKeyListener(this);
//		rightTable.addKeyListener(this);
	}

	/**
	 * �e�[�u���̃��t���b�V�����s��
	 */
	public void refreshTable(boolean combin)
	{
		ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
		Hashtable<String,String> resultItem = new Hashtable<String,String>();

		StringBuilder query = new StringBuilder();
		StringBuilder queryDokuji = new StringBuilder();

		String[] row = new String[5];

		leftTable.clearAllRow();
		rightTable.clearAllRow();
		leftTable.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_ALL_COLUMNS);
		rightTable.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_ALL_COLUMNS);

		try{
			//���̃e�[�u���̏����ݒ�
			// add s.inoue 2012/05/09
			query.append("SELECT distinct case MS.HISU_FLG when 1 then '��{' when 2 then '�ڍ�' else '�ǉ�' end as HISU_FLG,");
			query.append("MS.KOUMOKU_CD,MS.KOUMOKU_NAME,MS.KENSA_HOUHOU,XMLITEM_SEQNO ");
			query.append("FROM T_KENSHINMASTER MS ");
			query.append("INNER JOIN T_KENSHIN_P_S PS ");
			query.append("ON MS.KOUMOKU_CD = PS.KOUMOKU_CD ");

			query.append(" WHERE PS.K_P_NO = ");
			query.append(JQueryConvert.queryConvert(this.patternNumber));
			query.append(" AND MS.HKNJANUM = ");
			query.append(JQueryConvert.queryConvert("99999999"));

			if (combin){
				query.append(" OR PS.K_P_NO = ");
				query.append(JQueryConvert.queryConvert(this.cmbPatternNumber));
				// eidt s.inoue 2011/09/02
				// query.append(" ORDER BY K_P_NO, LOW_ID ");
				query.append(" ORDER BY XMLITEM_SEQNO ");
			}else{
				// del s.inoue 2012/06/29
				// ���Ԃœo�^�ł��Ȃ��Ȃ��
				// query.append(" ORDER BY K_P_NO, LOW_ID");
				query.append(" ORDER BY PS.LOW_ID");
			}

// eidt s.inoue 2011/09/02
//			query =
//				"SELECT master.KOUMOKU_CD,master.KOUMOKU_NAME,master.KENSA_HOUHOU " +
//									"FROM T_KENSHINMASTER master " +
//									"INNER JOIN " +
//									"T_KENSHIN_P_S syousai " +
//									"ON master.KOUMOKU_CD = syousai.KOUMOKU_CD " +
//									"WHERE syousai.K_P_NO = " + JQueryConvert.queryConvert(this.patternNumber) +
//									"AND " +
//									"master.HKNJANUM = " + JQueryConvert.queryConvert("99999999") + " " +
//									"ORDER BY K_P_NO, LOW_ID";

			result = JApplication.kikanDatabase.sendExecuteQuery(query.toString());

			// add s.inoue 2010/03/18
			JKenshinPatternMaintenanceEditFrameData tuikaCode =
				new JKenshinPatternMaintenanceEditFrameData();

			// add s.inoue 2010/03/19
			boolean dokujiFlg= getFunctionSetting("04");
			// edit s.inoue 2010/04/15
			HashSet<String> dokujiKoumokuCD = tuikaCode.getDokujiTuikaCD();

			for( int i = 0;i < result.size();i++ )
			{
				resultItem = result.get(i);

				String koumokuCD = resultItem.get("KOUMOKU_CD");
				// edit n.ohkubo 2015/08/01�@�폜�@start�@���Őݒ�
//				if(!dokujiKoumokuCD.contains(koumokuCD)){
//					row[0] = koumokuCD;
//					row[1] = resultItem.get("KOUMOKU_NAME");
//					row[2] = resultItem.get("KENSA_HOUHOU");
//					row[3] = resultItem.get("HISU_FLG");
//					row[4] = resultItem.get("XMLITEM_SEQNO");
//					leftTable.addData(row);
//				}
				// edit n.ohkubo 2015/08/01�@�폜�@end�@���Őݒ�
				
				// edit n.ohkubo 2015/08/01�@�ǉ��@start�@�Ǝ��ǉ����ڂ��Ō�ɒǉ��͂�߂āA�o�^���ʂ�ɕ\������
				if (dokujiFlg) {
					row[0] = koumokuCD;
					row[1] = resultItem.get("KOUMOKU_NAME");
					row[2] = resultItem.get("KENSA_HOUHOU");
					row[3] = resultItem.get("HISU_FLG");
					row[4] = resultItem.get("XMLITEM_SEQNO");
					leftTable.addData(row);
				} else {
					if (!dokujiKoumokuCD.contains(koumokuCD)) {
						row[0] = koumokuCD;
						row[1] = resultItem.get("KOUMOKU_NAME");
						row[2] = resultItem.get("KENSA_HOUHOU");
						row[3] = resultItem.get("HISU_FLG");
						row[4] = resultItem.get("XMLITEM_SEQNO");
						leftTable.addData(row);
					}
				}
				// edit n.ohkubo 2015/08/01�@�ǉ��@end�@�Ǝ��ǉ����ڂ��Ō�ɒǉ��͂�߂āA�o�^���ʂ�ɕ\������
			}

			// edit n.ohkubo 2015/08/01�@�폜�@start�@�Ǝ��ǉ����ڂ��Ō�ɒǉ��͂�߂āA�o�^���ʂ�ɕ\������
			// �Ǝ��ǉ����f
//			if (dokujiFlg){
//				for( int i = 0;i < result.size();i++ )
//				{
//					resultItem = result.get(i);
//					String koumokuCD = resultItem.get("KOUMOKU_CD");
//					if(dokujiKoumokuCD.contains(koumokuCD)){
////						System.out.println(koumokuCD + resultItem.get("KOUMOKU_NAME"));
//						row[0] = koumokuCD;
//						row[1] = resultItem.get("KOUMOKU_NAME");
//						row[2] = resultItem.get("KENSA_HOUHOU");
//						// add s.inoue 2011/09/02
//						row[3] = resultItem.get("HISU_FLG");
//						row[4] = resultItem.get("XMLITEM_SEQNO");
//
//						leftTable.addData(row);
//					}
//				}
//			}
			// edit n.ohkubo 2015/08/01�@�폜�@end�@�Ǝ��ǉ����ڂ��Ō�ɒǉ��͂�߂āA�o�^���ʂ�ɕ\������

			// eidt s.inoue 2012/05/09
			queryDokuji.append("SELECT distinct case HISU_FLG when 1 then '��{' when 2 then '�ڍ�' else '�ǉ�' end as HISU_FLG,");
			queryDokuji.append("KOUMOKU_CD,KOUMOKU_NAME,KENSA_HOUHOU,XMLITEM_SEQNO ");
			queryDokuji.append(" FROM T_KENSHINMASTER ");
			queryDokuji.append(" WHERE KOUMOKU_CD NOT IN ( ");
				queryDokuji.append(" SELECT DISTINCT KOUMOKU_CD FROM T_KENSHIN_P_S ");
				queryDokuji.append(" WHERE K_P_NO = ");
				queryDokuji.append(JQueryConvert.queryConvert(this.patternNumber));
				if (combin){
					queryDokuji.append(" OR K_P_NO = ");
					queryDokuji.append(JQueryConvert.queryConvert(this.cmbPatternNumber));
				}
			queryDokuji.append(" ) AND HKNJANUM = ");
			queryDokuji.append(JQueryConvert.queryConvert("99999999"));

			// add s.inoue 2011/09/02
			if (combin)
				queryDokuji.append(" ORDER BY XMLITEM_SEQNO ");

//			// �Ǝ��ǉ����f����
//			query = new String("SELECT KOUMOKU_CD,KOUMOKU_NAME,KENSA_HOUHOU " +
//							"FROM T_KENSHINMASTER " +
//					"WHERE KOUMOKU_CD " +
//					"NOT IN " +
//					"( " +
//					"SELECT KOUMOKU_CD " +
//					"FROM T_KENSHIN_P_S " +
//					"WHERE K_P_NO = " + JQueryConvert.queryConvert(this.patternNumber) +
//					")" +
//					" AND " +
//					"HKNJANUM = " + JQueryConvert.queryConvert("99999999")
//					);

			result = JApplication.kikanDatabase.sendExecuteQuery(queryDokuji.toString());

			// add s.inoue 2013/06/07
			boolean flg = false;
			if (leftTable.getRowCount() ==0)
				flg= true;

			for( int i = 0;i < result.size();i++ )
			{
				resultItem = result.get(i);
				String koumokuCD = resultItem.get("KOUMOKU_CD");

				if(!dokujiKoumokuCD.contains(koumokuCD)){
					row[0] = koumokuCD;
					row[1] = resultItem.get("KOUMOKU_NAME");
					row[2] = resultItem.get("KENSA_HOUHOU");
					// add s.inoue 2011/09/02
					row[3] = resultItem.get("HISU_FLG");
					row[4] = resultItem.get("XMLITEM_SEQNO");

					// eidt s.inoue 2013/06/17 �ďC��
					if(flg && (row[0].equals(CODE_METABO_HANTEI)||(row[0].equals(CODE_HOKEN_SHIDOU)))){
						leftTable.addData(row);
					}else{
						rightTable.addData(row);
					}
				}
			}

			// �Ǝ��ǉ����f����
			if (dokujiFlg){
				for( int i = 0;i < result.size();i++ )
				{
					resultItem = result.get(i);
					String koumokuCD = resultItem.get("KOUMOKU_CD");
					if(dokujiKoumokuCD.contains(koumokuCD)){
						row[0] = koumokuCD;
						row[1] = resultItem.get("KOUMOKU_NAME");
						row[2] = resultItem.get("KENSA_HOUHOU");
						// add s.inoue 2011/09/02
						row[3] = resultItem.get("HISU_FLG");
						row[4] = resultItem.get("XMLITEM_SEQNO");
						rightTable.addData(row);
					}
				}
			}

		}catch(SQLException e)
		{
			JErrorMessage.show("M3920",this);
			dispose();
			return;
		}
		
		// edit n.ohkubo 2014/10/01�@�ǉ� start�@�i�荞�ݏ�����TableRowSorter��ݒ�
		tableRowSorterLeft = new TableRowSorter<TableModel>(leftTable.getModel());
		tableRowSorterRight = new TableRowSorter<TableModel>(rightTable.getModel());
		
		//�񖼉����ł̃\�[�g�͍s��Ȃ�
		for (int i = 0; i < leftTable.getColumnCount(); i++) {
			tableRowSorterLeft.setSortable(i, false);
			tableRowSorterRight.setSortable(i, false);
		}
		
		//Sorter��ݒ�
		leftTable.setRowSorter(tableRowSorterLeft);
		rightTable.setRowSorter(tableRowSorterRight);
		
		//�������i�u�������v�������Ƀ{�^���̃O���[�A�E�g����������ׁj
		jText_filter.setText("");
		pushedfilterButton();
		
		// edit n.ohkubo 2014/10/01�@�ǉ� end

		// add ver2 s.inoue 2009/05/08
		TableColumnModel lcolumns = leftTable.getColumnModel();
        for(int i=0;i<lcolumns.getColumnCount();i++) {

        	lcolumns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)leftTable.getDefaultRenderer(leftTable.getColumnClass(i))));
        }
        // �����I��
		if (leftTable.getRowCount() > 0) {
			leftTable.setRowSelectionInterval(0, 0);
		}

		TableColumnModel rcolumns = rightTable.getColumnModel();
        for(int i=0;i<rcolumns.getColumnCount();i++) {

        	rcolumns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)rightTable.getDefaultRenderer(rightTable.getColumnClass(i))));
        }
        // �����I��
		if (rightTable.getRowCount() > 0) {
			rightTable.setRowSelectionInterval(0, 0);
		}
	}


	/**
	 * �o�^�������s��
	 */
	public void register()
	{
		// edit n.ohkubo 2014/10/01�@�ǉ��@��\�������o�^�ΏۂȂ̂ŁA�i���݂������i�S���\���j����
		tableRowSorterLeft.setRowFilter(null);
		tableRowSorterRight.setRowFilter(null);

// del s.inoue 2012/06/26
//		// eidt s.inoue 2011/12/06 �o�^���ɂ��ǉ�
//		// leftTable
//		String leftColCode [] = new String [leftTable.getRowCount()-1] ;
//		int jj = 0;
//		for (jj = 0; jj < leftTable.getRowCount()-1; jj++) {
//			leftColCode[jj] = (String)leftTable.getData(jj,0);
//		}
//		String retMessage = JConstantString.checkDoubleKensaHouhouMap(leftColCode);
//		if(!retMessage.isEmpty()){
//			String[] params = {retMessage};
//			JErrorMessage.show("M5523", this, params);
//		}

// del s.inoue 2011/12/08
//		HashMap<String,ArrayList<String>> hm = null;
//		for(int i = 0;i < leftTable.getRowCount();i++)
//		{
//			String koumokuCD = (String)leftTable.getData(i, 0);
//			hm = JConstantString.getKensaHouhouMap(koumokuCD);
//
//			// Object[] objKey=hm.keySet().toArray();
//		   	Object[] objKey=hm.keySet().toArray();
//	    	String keyStr = (String)objKey[0];
//	    	ArrayList<String> arr = hm.get(keyStr);
//
//			if (arr != null){
//				for (int j = 0; j < objKey.length; j++) {
//					System.out.println(objKey[i]);
//				}
//			}
//		}

		// add s.inoue 2013/07/23
		// leftTable
		int jj = 0;
		boolean flgh = false;
		boolean flgm = false;

		for (jj = 0; jj < leftTable.getRowCount(); jj++) {
			String code = (String)leftTable.getData(jj,0);
			if (code.equals(CODE_METABO_HANTEI)){
				flgh = true;
			}else if(code.equals(CODE_HOKEN_SHIDOU)) {
				flgm = true;
			}
		}

		if(!flgh || !flgm){
			JErrorMessage.show("M5524", this);
			return;
		}

		String Query = null;
		try{
			//���ڕ��я��̔������Ȃ������߂܂����ׂč폜����
			JApplication.kikanDatabase.Transaction();

			Query = new String("DELETE FROM T_KENSHIN_P_S WHERE K_P_NO = " + JQueryConvert.queryConvert(this.patternNumber));
			JApplication.kikanDatabase.sendNoResultQuery(Query);

			for(int i = 0;i < leftTable.getRowCount();i++)
			{
				Query = new String("INSERT INTO T_KENSHIN_P_S (K_P_NO,KOUMOKU_CD,LOW_ID) VALUES (" +
						JQueryConvert.queryConvertAppendComma(this.patternNumber) +
						JQueryConvert.queryConvertAppendComma((String)leftTable.getData(i, 0)) +
						JQueryConvert.queryConvert(String.valueOf(i + 1)) +
						")");

				JApplication.kikanDatabase.sendNoResultQuery(Query);
			}
			JApplication.kikanDatabase.Commit();

			dispose();
		}catch(SQLException f)
		{
			JErrorMessage.show("M3923",this);
			f.printStackTrace();
			try{
				JApplication.kikanDatabase.rollback();
			}catch(SQLException g)
			{
				JErrorMessage.show("M0000",this);
				g.printStackTrace();
				System.exit(1);
			}

			dispose();
			return;
		}
	}

	/**
	 * ���̃e�[�u���ŏ�Ɉړ�
	 */
	public void pushedToTopButton( ActionEvent e )
	{
		if(leftTable.getSelectedRowCount() >= 1 )
		{
			int[] leftselectedRows = leftTable.getSelectedRows();
			int rowCount = leftTable.getSelectedRowCount();
			String[][] row = new String[rowCount][5];

			if (leftselectedRows[0]-1 < 0){
				return;
			}

			for(int i = 0;i < rowCount;i++ )
			{
				row[i][0] = new String((String)leftTable.getData(leftselectedRows[i], 0));
				row[i][1] = new String((String)leftTable.getData(leftselectedRows[i], 1));
				row[i][2] = new String((String)leftTable.getData(leftselectedRows[i], 2));
				row[i][3] = new String((String)leftTable.getData(leftselectedRows[i], 3));
				row[i][4] = new String((String)leftTable.getData(leftselectedRows[i], 4));
			}
			for(int i = 0;i < rowCount;i++ )
			{
				leftTable.insertData(row[i], leftselectedRows[i]-1);
			}

			//�O����폜�����index���ς���Ă��܂����ߌ�납��폜
			for(int i = rowCount ;i > 0;)
			{
				i--;
				leftTable.clearRow(leftselectedRows[i]+rowCount);
			}

			if (leftselectedRows[0]-1 >= 0){
				if (leftselectedRows.length == 1){
					leftTable.setRowSelectionInterval(leftselectedRows[0]-1, leftselectedRows[0]-1);
				}else{
					leftTable.setRowSelectionInterval(leftselectedRows[0]-1, leftselectedRows[0]+rowCount-2);
				}
			}else{
				leftTable.setRowSelectionInterval(0, 0);
			}
		}
	}

	/**
	 * ���̃e�[�u���ŉ��Ɉړ�
	 */
	public void pushedToDownButton( ActionEvent e )
	{
		if(leftTable.getSelectedRowCount() >= 1 )
		{
			int[] leftselectedRows = leftTable.getSelectedRows();
			int rowCount = leftTable.getSelectedRowCount();
			String[][] row = new String[rowCount][5];

			if (leftselectedRows[0]+rowCount+1 > leftTable.getRowCount()){
				return;
			}

			for(int i = 0;i < rowCount;i++ )
			{
				row[i][0] = new String((String)leftTable.getData(leftselectedRows[i], 0));
				row[i][1] = new String((String)leftTable.getData(leftselectedRows[i], 1));
				row[i][2] = new String((String)leftTable.getData(leftselectedRows[i], 2));
				row[i][3] = new String((String)leftTable.getData(leftselectedRows[i], 3));
				row[i][4] = new String((String)leftTable.getData(leftselectedRows[i], 4));
			}

			for(int i = 0;i < rowCount;i++ )
			{
				leftTable.insertData(row[i], leftselectedRows[i]+rowCount+1);
			}

			//�O����폜�����index���ς���Ă��܂����ߌ�납��폜
			for(int i = rowCount ;i > 0;)
			{
				i--;
				leftTable.clearRow(leftselectedRows[i]);
			}

			if (leftselectedRows[0]+1 < leftTable.getRowCount()){
				if (leftselectedRows.length == 1){
					leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+1);
				}else{
					leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+rowCount);
				}
			}else{
				leftTable.setRowSelectionInterval(0, 0);
			}
		}
	}

	/**
	 * �E�̃e�[�u�����獶�̃e�[�u���Ɉړ�
	 */
	public void pushedToLeftButton( ActionEvent e )
	{
		if(rightTable.getSelectedRowCount() >= 1 )
		{
			String[] row = new String[5];
//			int[] rightselectedRows = rightTable.getSelectedRows();		// edit n.ohkubo 2014/10/01�@�폜
//			int[] leftselectedRows = leftTable.getSelectedRows();		// edit n.ohkubo 2014/10/01�@�폜
			int[] leftselectedRows = leftTable.getSelectedRowsConvertRowIndexToModel();		// edit n.ohkubo 2014/10/01�@�ǉ�
			int[] rightselectedRows = rightTable.getSelectedRowsConvertRowIndexToModel();	// edit n.ohkubo 2014/10/01�@�ǉ�
			int rowCount = rightTable.getSelectedRowCount();

// del s.inoue 2012/06/26 �d���`�F�b�N�p�~
//			// �����������@�̈ړ�����
//			String retMessage = "";
//
//			// rightTable
//			String rightColCode [] = new String [rightselectedRows.length] ;
//			for (int i = 0; i < rightselectedRows.length; i++) {
//				rightColCode[i] = (String)rightTable.getData(rightselectedRows[i],0);
//			}
//
//			retMessage = JConstantString.checkDoubleKensaHouhouMap(rightColCode);
//			if(!retMessage.isEmpty()){
//				String[] params = {retMessage};
//				JErrorMessage.show("M5522", this, params);
//				return;
//			}
//			// leftTable
//			String leftColCode [] = new String [leftTable.getRowCount()-1 + rightColCode.length] ;
//			int j = 0;
//			for (j = 0; j < leftTable.getRowCount()-1; j++) {
//				leftColCode[j] = (String)leftTable.getData(j,0);
//			}
//			for (int ij = 0; ij < rightColCode.length; ij++) {
//				leftColCode[j+ij] = rightColCode[ij];
//			}
//
//			retMessage = JConstantString.checkDoubleKensaHouhouMap(leftColCode);
//			if(!retMessage.isEmpty()){
//				String[] params = {retMessage};
//				JErrorMessage.show("M5522", this, params);
//				return;
//			}

			for(int jj = rowCount-1;jj >= 0;jj-- )
			{
				row[0] = new String((String)rightTable.getData(rightselectedRows[jj], 0));
				row[1] = new String((String)rightTable.getData(rightselectedRows[jj], 1));
				row[2] = new String((String)rightTable.getData(rightselectedRows[jj], 2));
				row[3] = new String((String)rightTable.getData(rightselectedRows[jj], 3));
				row[4] = new String((String)rightTable.getData(rightselectedRows[jj], 4));
				if (leftselectedRows.length > 0){
					leftTable.insertData(row, leftselectedRows[0]+1);
				}else{
					leftTable.insertData(row, 0);
				}
			}

			//�O����폜�����index���ς���Ă��܂����ߌ�납��폜
			for(int i = rowCount ;i > 0;)
			{
				i--;
				rightTable.clearRow(rightselectedRows[i]);
			}

			// �I������
//			if (rightTable.getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�폜
			if (rightTable.getModel().getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�ǉ�
				if(rightselectedRows.length == 0){
					leftTable.setRowSelectionInterval(0,0);
				}else if (rightselectedRows[0] > 0){
					// ���̎��́A���ɉ������Ă���
					rightTable.setRowSelectionInterval(rightselectedRows[0]-1, rightselectedRows[0]-1);
				}else{
					rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]);
				}
			}

//			if (leftTable.getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�폜
			if (leftTable.getModel().getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�ǉ�
				if(leftselectedRows.length == 0){
					leftTable.setRowSelectionInterval(0,0);
				}else if (leftselectedRows[0] >= 0){
					// ���̎��́A���ɉ������Ă���
					if (rightselectedRows.length > 1){
						leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+rightselectedRows.length);
					}else{
						leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+1);
					}
				}else if (leftselectedRows[0] == 0){
					leftTable.setRowSelectionInterval(leftselectedRows[0], leftselectedRows[0]);
				}else{
					leftTable.setRowSelectionInterval(leftselectedRows[0], leftselectedRows[0]+ rowCount);
				}
			}
		}
	}

	/**
	 * ���̃e�[�u������E�̃e�[�u���ֈړ�
	 */
	public void pushedToRightButton( ActionEvent e )
	{
		if(leftTable.getSelectedRowCount() >= 1 )
		{
			String[] row = new String[5];
//			int[] rightselectedRows = rightTable.getSelectedRows();		// edit n.ohkubo 2014/10/01�@�폜
//			int[] leftselectedRows = leftTable.getSelectedRows();		// edit n.ohkubo 2014/10/01�@�폜
			int[] leftselectedRows = leftTable.getSelectedRowsConvertRowIndexToModel();		// edit n.ohkubo 2014/10/01�@�ǉ�
			int[] rightselectedRows = rightTable.getSelectedRowsConvertRowIndexToModel();	// edit n.ohkubo 2014/10/01�@�ǉ�
			int rowCount = leftTable.getSelectedRowCount();
			String retMessage = "";
			String kekkaMessage="";
			for(int koumokuCD : leftselectedRows){
				String koumokuCode = (String)leftTable.getData(koumokuCD).get(0);
				String koumokuName =  (String)leftTable.getData(koumokuCD).get(1);
				if((koumokuCode.equals(CODE_METABO_HANTEI))||(koumokuCode.equals(CODE_HOKEN_SHIDOU))){
					kekkaMessage ="���ڃR�[�h�F" + koumokuCode+ "�A���ږ��F" + koumokuName;

					if(!retMessage.isEmpty()){
						retMessage = retMessage + "[���s]"+ kekkaMessage;
					}else{
						retMessage = kekkaMessage;
					}
				}
			}
			if(!retMessage.isEmpty()){
				String[] params = {retMessage};
				JErrorMessage.show("M5521", this, params);
				return;
			}

			// edit s.inoue 2009/09/14 �\�[�g�����C��
			for(int i = rowCount-1;i >= 0;i-- )
			{
				row[0] = new String((String)leftTable.getData(leftselectedRows[i], 0));
				row[1] = new String((String)leftTable.getData(leftselectedRows[i], 1));
				row[2] = new String((String)leftTable.getData(leftselectedRows[i], 2));
				row[3] = new String((String)leftTable.getData(leftselectedRows[i], 3));
				row[4] = new String((String)leftTable.getData(leftselectedRows[i], 4));
				// edit ver2 s.inoue 2009/09/07
				if (rightselectedRows.length > 0){
					rightTable.insertData(row, rightselectedRows[0]);
				}else{
					rightTable.insertData(row, 0);
				}
			}

			//�O����폜�����index���ς���Ă��܂����ߌ�납��폜
			for(int i = rowCount ;i > 0;)
			{
				i--;
				leftTable.clearRow(leftselectedRows[i]);
			}

			// �I������
//			if (rightTable.getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�폜
			if (rightTable.getModel().getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�ǉ�
				if(rightselectedRows.length == 0){
					rightTable.setRowSelectionInterval(0,0);
				}else if (rightselectedRows[0] >= 0){
					if (leftselectedRows.length > 1){
						rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]+leftselectedRows.length-1);
					}else {
						rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]);
					}
				}else if (rightselectedRows[0] == 0){
						rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]);
				}else{
					rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]+ rowCount);
				}
			}

			// edit s.inoue 2009/09/14
//			if (leftTable.getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�폜
			if (leftTable.getModel().getRowCount() > 0){	// edit n.ohkubo 2014/10/01�@�ǉ�
				if(leftselectedRows.length == 0){
					leftTable.setRowSelectionInterval(0,0);
				}else if (leftselectedRows[0] > 0){
					// ���̎��́A��ɏオ���Ă���
					leftTable.setRowSelectionInterval(leftselectedRows[0]-1, leftselectedRows[0]-1);
				}else{
					leftTable.setRowSelectionInterval(leftselectedRows[0], leftselectedRows[0]);
				}
			}

		}

	}

	// add s.inoue 2011/09/05
	/**
	 * ���̃e�[�u���o�u���\�[�g����
	 */
	public void pushedToTopButton(int curRow ,int moveUpper)
	{
		int rowCount = 1;

		String[][] row = new String[rowCount][5];

		for(int i = 0;i < rowCount;i++ )
		{
			row[i][0] = new String((String)leftTable.getData(moveUpper, 0));
			row[i][1] = new String((String)leftTable.getData(moveUpper, 1));
			row[i][2] = new String((String)leftTable.getData(moveUpper, 2));
			row[i][3] = new String((String)leftTable.getData(moveUpper, 3));
			row[i][4] = new String((String)leftTable.getData(moveUpper, 4));
		}

		leftTable.clearRow(moveUpper);

		for(int i = 0;i < rowCount;i++ )
		{
			leftTable.insertData(row[i], curRow);
		}

		leftTable.setRowSelectionInterval(0, 0);
	}

	// add s.inoue 2010/03/18
	private boolean getFunctionSetting(String functionCode){

		ArrayList<Hashtable<String, String>> result = null;
		boolean retflg = false;

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
			sb.append(" FROM T_SCREENFUNCTION ");
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_MASTER_PATTERN_CODE));
			sb.append(" AND FUNCTION_CD = ");
			sb.append(JQueryConvert.queryConvert(functionCode));

			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		Hashtable<String, String> item = result.get(0);
		retflg = item.get("FUNCTION_FLG").equals("1")?true:false;

		return retflg;
	}

	// add s.inoue 2011/09/01
    /*
     * ���f�p�^�[�������_�C�A���O
     */
    public boolean pushedCombinationButton(){

    	boolean cancelFlg = false;
    	// �����p���b�Z�[�W����
    	String messageTaisyosya = "";
    	// messageTaisyosya = "����:" + Data.kanaShimei + " ���f���{��:" + Data.jishiDate;
		try {
			patternSelectDialog = DialogFactory.getInstance().createDialog(this,messageTaisyosya);

			// ���f���{�����̓_�C�A���O��\��
			patternSelectDialog.setMessageTitle("���f�p�^�[���I�����");
			patternSelectDialog.setVisible(true);
			// �ߒl�i�[
			if(patternSelectDialog.getStatus().equals(RETURN_VALUE.YES)){
				cmbPatternNumber = patternSelectDialog.getPrintSelect().toString();
			}else if (patternSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL)){
				cancelFlg = true;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			JErrorMessage.show("M3948", null);
		}
		return cancelFlg;
    }

    // add s.inoue 2011/09/01
    public void combinationPattern(){
    	
		// edit n.ohkubo 2014/10/01�@�ǉ��@clearAllRow����ArrayIndexOutOfBoundsException�ŗ�����Ή�
		leftTable.setRowSorter(null);
		rightTable.setRowSorter(null);
    	
    	// ���f�p�^�[�� kenshinPatternNumver
    	System.out.println("�p�^�[������:" + patternNumber + "��"+ cmbPatternNumber);
    	refreshTable(true);
    }

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton( ActionEvent e )
	{
		dispose();
	}

	/*
	 * �����{�^��
	 */
	public void pushedCombinationButton( ActionEvent e )
	{
		if (!pushedCombinationButton())
			combinationPattern();
	}

	/**
	 * �L�����Z���{�^��
	 */
	public void pushedCancelButton( ActionEvent e )
	{
		dispose();
	}

	/**
	 * �o�^�{�^��
	 */
	public void pushedRegisterButton( ActionEvent e )
	{
		register();
	}

	/**
	 * �������{�^��
	 */
	public void pushedClearButton( ActionEvent e )
	{
		// edit n.ohkubo 2014/10/01�@�ǉ��@clearAllRow����ArrayIndexOutOfBoundsException�ŗ�����Ή�
		leftTable.setRowSorter(null);
		rightTable.setRowSorter(null);
				
		refreshTable(false);
	}

	// add s.inoue 2011/09/05
	/**
	 * �\�[�g�{�^��
	 */
	public void pushedSortButton( ActionEvent e )
	{
		// �\�[�g(�I��@)����
		int firstMinValue = 0;
		int firstMinKey = 0;

		// ���ڕ����[�v
		for (int i = 0; i < leftTable.getRowCount(); i++) {

			// �ŏ��l�ړ����[�v
			int itemValue = 0;
			for (int k = 0+i; k < leftTable.getRowCount(); k++) {
				Vector<String> vec = leftTable.getData(k);

				itemValue = Integer.parseInt(vec.get(4));
				System.out.println(itemValue);

				if (itemValue < firstMinValue ||
						(k == i)){
					firstMinValue = itemValue;
					firstMinKey = k;
				}
			}

			System.out.println("�ʒu:" +firstMinKey+ " �l:" + firstMinValue);

			// ���ۂ̈ړ�����
			pushedToTopButton(i,firstMinKey);
		}
	}

	@Override
	public void actionPerformed( ActionEvent e )
	{
		Object source = e.getSource();
		if( source == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}
		else if( source == jButton_Cancel )
		{
			logger.info(jButton_Cancel.getText());
			pushedCancelButton( e );
		}
		else if( source == jButton_Register )
		{
			logger.info(jButton_Register.getText());
			pushedRegisterButton( e );
		}
		else if( source == jButton_Clear )
		{
			logger.info(jButton_Clear.getText());
			pushedClearButton( e );
		}
		// add s.inoue 2011/09/05
		else if( source == jButton_Sort )
		{
			logger.info(jButton_Sort.getText());
			pushedSortButton( e );
		}
		else if( source == jButton_ToTop )
		{
			logger.info(jButton_ToTop.getText());
			pushedToTopButton( e );
		}
		else if( source == jButton_ToDown )
		{
			logger.info(jButton_ToDown.getText());
			pushedToDownButton( e );
		}
		else if( source == jButton_ToLeft )
		{
			logger.info(jButton_ToLeft.getText());
			pushedToLeftButton( e );
		}
		else if( source == jButton_ToRight )
		{
			logger.info(jButton_ToRight.getText());
			pushedToRightButton( e );
		}
		// add s.inoue 2011/09/01
		else if( source == jButton_Combination)
		{
			logger.info(jButton_Combination.getText());
			pushedCombinationButton( e );
		}
		// edit n.ohkubo 2014/10/01�@�ǉ� start
		else if( source == jButton_filter)
		{
			logger.info(jButton_filter.getText());
			pushedfilterButton();
		}
		// edit n.ohkubo 2014/10/01�@�ǉ� end
	}
	
	// edit n.ohkubo 2014/10/01�@�ǉ� start
	/**
	 * �i���ݏ���
	 * �i���E�����̃e�[�u�����i�荞�ށj
	 */
	private void pushedfilterButton() {

		//�I����S����
		rightTable.clearSelection();
		leftTable.clearSelection();
		
		//�������Ƃ��đS���\��
		tableRowSorterRight.setRowFilter(null);
		tableRowSorterLeft.setRowFilter(null);
		
		String text = jText_filter.getText();
		if (text.length() == 0) {
			
			//�t�B���^�[�����i�S���\���j�̏ꍇ�A�g�p�s�ɂ����{�^����߂�
			jButton_Sort.setEnabled(true);
			jButton_ToTop.setEnabled(true);
			jButton_ToDown.setEnabled(true);
			
		} else {
			tableRowSorterRight.setRowFilter(RowFilter.regexFilter(text));
			tableRowSorterLeft.setRowFilter(RowFilter.regexFilter(text));
			
			//�t�B���^�[���������ꍇ�A�u����v�Ə㉺�ړ��̃{�^���͎g�p�s�ɂ���
			jButton_Sort.setEnabled(false);
			jButton_ToTop.setEnabled(false);
			jButton_ToDown.setEnabled(false);
		}
	}
	// edit n.ohkubo 2014/10/01�@�ǉ� end

// del s.inoue 2012/07/10
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//
////		case KeyEvent.VK_F5:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToTop.getText());
////				pushedToTopButton(null);break;
////			}
////		case KeyEvent.VK_F6:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToLeft.getText());
////				pushedToLeftButton(null);break;
////			}
////		case KeyEvent.VK_F7:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToRight.getText());
////				pushedToRightButton(null);break;
////			}
////		case KeyEvent.VK_F8:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToDown.getText());
////				pushedToDownButton(null);break;
////			}
//		case KeyEvent.VK_F11:
//			logger.info(jButton_Clear.getText());
//			pushedClearButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Register.getText());
//			pushedRegisterButton(null);break;
//		}
//	}

	/**
	 * �{�^���̏�Ԃ��X�V����B
	 */
	private void updateButtonsState() {

		boolean enableButton = false;

		/* �p�^�[���ԍ��� 1�łȂ�����2�łȂ��ꍇ */
		if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
			enableButton = true;
		}
		// eidt s.inoue 2012/07/09
		jButton_Sort.setEnabled(enableButton);
		jButton_ToTop.setEnabled(enableButton);
		jButton_ToLeft.setEnabled(enableButton);
		jButton_ToRight.setEnabled(enableButton);
		jButton_ToDown.setEnabled(enableButton);
		jButton_Register.setEnabled(enableButton);
		jButton_Combination.setEnabled(enableButton);
		jButton_Clear.setEnabled(enableButton);
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

