package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.form.client.FormController;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * �ꗗCtl���
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinKekkaSearchListFrameCtl
		extends GridController
		implements GridDataLocator,IKenshinKekkaSearchListFrame {

	private JKenshinKekkaSearchListFrame grid = null;
	private Connection conn = null;
	private JKenshinKekkaSearchListFrame frame;
	private static Logger logger = Logger.getLogger(JKenshinKekkaSearchListFrameCtl.class);

//	// �����{�^����������SQL�Ŏg�p
//	private static final String[] SQL_SELECT_PART = {
//			"'' as CHECKBOX_COLUMN,",
//			"kojin.UKETUKE_ID,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.BIRTHDAY,",
//			"kojin.SEX,",
//			"case kojin.SEX when 1 then '�j��' when 2 then '����' end as SEX_NAME,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.JYUSHIN_SEIRI_NO,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.HKNJANUM,",
//			// edit s.inoue 2009/10/31
//			"kojin.SIHARAI_DAIKOU_BANGO,",
//			// edit s.inoue 2009/10/31
//			"kojin.NAME,",
//			"kojin.KANANAME,",
//			"tokutei.KENSA_NENGAPI,",
//			"tokutei.HANTEI_NENGAPI,",
//			"tokutei.TUTI_NENGAPI,",
//			// add ver2 s.inoue 2009/05/22
//			"GET_NENDO.NENDO, ",
//			"case tokutei.KEKA_INPUT_FLG when 1 then '��' "
//					+ "when 2 then '��' else '��' end as KEKA_INPUT_FLG " };


//	/**
//	 * ���ʃf�[�^���̓{�^��
//	 */
//	public void showKekkaRegisterFrame(boolean isCopy) {
//		// edit s.inoue 2010/04/27
//	//	ArrayList<Integer> selectedRow = new ArrayList<Integer>();
//	//
//	//	// ���݃`�F�b�N����Ă���s�̃��X�g�𒊏o
//	//	for (int i = 0; i < table.getRowCount(); i++) {
//	//		if (table.getData(i, 0) == Boolean.TRUE) {
//	//			selectedRow.add(i);
//	//		}
//	//	}
//		setSelectedRows();
//
//		// ��I������Ă���ꍇ�̂݉�ʑJ�ڂ��s��
//		if (selectedRows.size() == 1) {
//			resultItem = result.get(selectedRows.get(0));
//
//			// edit ver2 s.inoue 2009/06/01
//			if (isCopy){
//				/* T_KOJIN Dao ���쐬����B */
//				try {
//					tKojinDao = (TKojinDao) DaoFactory.createDao(
//							JApplication.kikanDatabase.getMConnection(), new TKojin());
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}
//
//				// �����R�s�[���A��tID�̔�
//				long uketukeId = -1L;
//				try {
//					uketukeId = tKojinDao.selectNewUketukeId();
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}
//
//				// add ver2 s.inoue 2009/06/01
//				validatedData.setUketukePre_id(resultItem.get("UKETUKE_ID"));
//				validatedData.setUketuke_id(new Long(uketukeId).toString());
//
//			}else{
//				validatedData.setUketuke_id(resultItem.get("UKETUKE_ID"));
//			}
//
//			validatedData.setHihokenjyaCode(resultItem.get("HIHOKENJYASYO_KIGOU"));
//			validatedData.setHihokenjyaNumber(resultItem.get("HIHOKENJYASYO_NO"));
//			validatedData.setName(resultItem.get("KANANAME"));
//			validatedData.setHokenjyaNumber(resultItem.get("HKNJANUM"));
//			validatedData.setSex(resultItem.get("SEX"));
//			validatedData.setBirthDay(resultItem.get("BIRTHDAY"));
//
//			String kensaJissiDate = resultItem.get("KENSA_NENGAPI");
//
//			if (kensaJissiDate == null) {
//				kensaJissiDate = new String("");
//			}
//
//			JScene.CreateDialog(
//					this,
//					new JKekkaRegisterFrameCtrl(validatedData, kensaJissiDate, isCopy),
//					new WindowRefreshEvent());
//		} else {
//			JErrorMessage.show(
//					"M3520", this);
//		}
//	}

	// add s.inoue 2012/11/16
	private boolean firstViewFlg =true;

	public JKenshinKekkaSearchListFrameCtl(Connection conn) {
		this.conn = conn;
		JApplication.selectedHistoryRows = new ArrayList<Integer>();
		grid = new JKenshinKekkaSearchListFrame(conn, this);
	}

	public JKenshinKekkaSearchListFrame getGridControl(){
		return grid;
	}

	/**
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the column header; this text will be automatically translated according to internationalization settings
	*/
	public String getHeaderTooltip(String attributeName) {
	    return attributeName;
	}

    /**
	* @param row row index in the grid
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
	*/
	public String getCellTooltip(int row,String attributeName) {
		String item ="";
		try {
			item = (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
		}catch (Exception e) {}
		return item;
//		return attributeName+" at row "+row;
	}

	/**
	 * INSERT mode�̎��A�ۑ��O�ɌĂ΂�� Callback�֐�(save button�������ꂽ��).
	 *
	 * @return <code>true</code> �ۑ�����, <code>false</code> �ۑ����f
	 */
	public boolean beforeInsertGrid(GridControl grid) {
// del
//		new JShiharaiMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
	}

	/**
     * Callback���\�b�h
     * export�_�C�A���O��\������O�ɌĂяo��
     * grid�ɒ�`���ꂽformat���Ē�`���ČĂяo���܂�
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.CSV_FORMAT1,ExportOptions.CSV_FORMAT2,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
	 }

	 // add s.inoue 2011/04/26
	/**
	 * Callback method called when the data loading is completed.
	 * @param error <code>true</code> if an error occours during data loading, <code>false</code> if data loading is successfully completed
	 */
	 public void loadDataCompleted(boolean error) {
//	    frame.getControlCurrency().setCurrencySymbol("$");
//	    frame.getControlCurrency().setDecimals(2);
//	    frame.getControlCurrency().setDecimalSymbol('.');
//	    frame.getControlCurrency().setGroupingSymbol(',');
//		JKenshinKekkaSearchListFrameData vo = (JKenshinKekkaSearchListFrameData)grid.getMainPanel().getVOModel().getValueObject();
//	    frame.getGrid().getOtherGridParams().put("empCode",vo.getEmpCode());
//	    frame.getGrid().reloadData();

		// add s.inoue 2012/11/07
		setSelectedRow(JApplication.selectedHistoryRows);

		// eidt s.inoue 2011/05/10
		// ViewSettings.getGridPageSize()
		int rowCount = ((grid.currentPage - 1)*JApplication.gridViewSearchCount) + grid.currentRow + 1;
		// add s.inoue 2012/12/05
		if (grid.getGrid().getTable().getstartIndex() != 0)
			rowCount = grid.getGrid().getTable().getstartIndex() + grid.currentRow + 1;

		grid.currentTotalRows = rowCount;
		grid.countText.setText(rowCount + "����");
	 }

	// �`�F�b�N�{�b�N�X�ݒ�
	private void setSelectedRow(ArrayList<Integer> selectedRows){
		// add s.inoue 2013/02/26
		if (selectedRows == null)return;

		// edit s.inoue 2013/10/24
		// if (selectedRows.size() <= 1)return;
		if (grid.getGrid().getVOListTableModel().getRowCount() == 0)return;

		for (int i = 0; i < selectedRows.size(); i++) {
			grid.getGrid().getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
			grid.getGrid().getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
		}
	}

	/**
	 * grid�̃f�[�^�����[�h���ꂽ����Callback�֐�
	 * @param PREVIOUS_BLOCK_ACTION:�O�s�ֈړ�, NEXT_BLOCK_ACTION:���s�ֈړ� or LAST_BLOCK_ACTION:�ŏI�s�ֈړ�
	 * @param startPos start position of data fetching in result set
	 * @param filteredColumns filtered columns
	 * @param currentSortedColumns sorted columns
	 * @param currentSortedVersusColumns ordering versus of sorted columns
	 * @param valueObjectType v.o. type
	 * @param otherGridParams other grid parameters
	 * @return response from the server: an object of type VOListResponse
	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
	 */
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {
		// PreparedStatement stmt = null;
		try {
			ArrayList vals = new ArrayList();
			Map attribute2dbField = new HashMap();
			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");
			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
			attribute2dbField.put("NAME","NAME");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU","TK.HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO","TK.HIHOKENJYASYO_NO");
			attribute2dbField.put("KENSA_NENGAPI","TT.KENSA_NENGAPI");
			attribute2dbField.put("SEX","SEX");
			// eidt s.inoue 2013/03/25
			// attribute2dbField.put("KEKA_INPUT_FLG","KEKA_INPUT_FLG");
			attribute2dbField.put("KEKA_INPUT_FLG","GET_INPUT.INPUT_FLG");
			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
			attribute2dbField.put("HKNJANUM","HKNJANUM");
			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","SIHARAI_DAIKOU_BANGO");
			attribute2dbField.put("KANANAME","KANANAME");
			attribute2dbField.put("HANTEI_NENGAPI","TT.HANTEI_NENGAPI");
			attribute2dbField.put("TUTI_NENGAPI","TT.TUTI_NENGAPI");
			attribute2dbField.put("NENDO","GET_NENDO.NENDOS");


			// add s.inoue 2012/10/12
//			Map pfilteredColumns = new HashMap<String,String>();

			// this.filteredColumns = new ArrayList();
//		    Iterator it = filteredColumns.values().iterator();

//		    if (!it.hasNext()){

//		    	FilterWhereClause[] filterClauses = null;
//		    	filterClauses[0] = new FilterWhereClause();
//		    	filterClauses[0].setAttributeName("NENDO");
//		    	filterClauses[0].setValue("2012");

//		    	filteredColumns.put(
//		    			"status",
//		    			new FilterWhereClause[]{new FilterWhereClause("NENDO",Consts.EQ,"2012"),
//		    			null}
//	    		      );
//
//		    	System.out.println("done");

//		    	filteredColumns.put("status",new FilterWhereClause[]{
//		    		        new FilterWhereClause("status",Consts.EQ,"E"),
//		    		        null
//		    		      });

//		    	GridParams gridParams = (GridParams) command.getInputParam();
//		    	FilterWhereClause clauseDesign = new FilterWhereClause();
//                 clauseDesign.setAttributeName("NENDO");
//                 clauseDesign.setOperator("=");
//                 clauseDesign.setValue("2012");
//
//                 filteredColumns.add(filterClauses[0]);


//                 gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
//                                 new FilterWhereClause[] { clauseDesign, null });
//		    }

			GridParams gridParams = new GridParams(action, startIndex,
					filteredColumns, currentSortedColumns,
					currentSortedVersusColumns, otherGridParams);

			// add s.inoue 2013/11/06
			// �����l ���� �����܂������̐ݒ�
		    if (currentSortedColumns.iterator().hasNext()){
				Iterator it_dt = currentSortedColumns.iterator();
				Object sortClauses = null;

			    // add s.inoue 2013/11/06
			    JApplication.currentSortedColumns = new ArrayList();

			    while(it_dt.hasNext()) {
			      sortClauses = (Object)it_dt.next();
			      // add s.inoue 2013/11/06
			      JApplication.currentSortedColumns.add(sortClauses);
			    }
		    }

		    // add s.inoue 2013/11/19
		    JApplication.currentSorted = new HashMap<Integer, String>();		    
		    int keymap = 0;
		    
		    if (currentSortedVersusColumns.iterator().hasNext()){
				Iterator it_dt = currentSortedVersusColumns.iterator();
				Object sortClauses = null;

			    // add s.inoue 2013/11/06
				JApplication.currentSortedVersusColumns = new ArrayList();
			    
			    while(it_dt.hasNext()) {
			      sortClauses = (Object)it_dt.next();
			      // add s.inoue 2013/11/06
			      JApplication.currentSortedVersusColumns.add(sortClauses);
			      // add s.inoue 2013/11/19
			      JApplication.currentSorted.put(keymap, (String)sortClauses);
			      keymap++;
			    }
		    }

			// �����l ���� �����܂������̐ݒ�
		    if (filteredColumns.values().iterator().hasNext()){
				Iterator it_dt = filteredColumns.values().iterator();
			    FilterWhereClause[] filterClauses = null;

			    // add s.inoue 2013/11/06
			    int ii = 0;
			    JApplication.clauseDesign = new FilterWhereClause[filteredColumns.size()];

			    // add s.inoue 2013/03/25
			    while(it_dt.hasNext()) {
			      filterClauses = (FilterWhereClause[])it_dt.next();

			      if (filterClauses[0].getOperator().equals("like")){
			    	  // add s.inoue 2014/03/18
			    	  String filterval = filterClauses[0].getValue().toString();
			    	  if(!filterval.startsWith("%"))
			    		  filterval = "%"+filterval+"%";
			    	  filterClauses[0].setValue(filterval);

						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
								new FilterWhereClause[] { filterClauses[0], null });
// eidt s.inoue 2013/03/25
//			      }else if (filterClauses[0].getOperator().equals("like")){
//			    	  System.out.println("");
// del s.inoue 2013/03/26
//			      }else{
//			    	  filterClauses[0].setValue(filterClauses[0].getValue());
//					  gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
//								new FilterWhereClause[] { filterClauses[0], null });

			      }

			      // add s.inoue 2013/11/06
			      JApplication.clauseDesign[ii] = new FilterWhereClause();
				  JApplication.clauseDesign[ii].setAttributeName(filterClauses[0].getAttributeName());
				  JApplication.clauseDesign[ii].setOperator(filterClauses[0].getOperator());
				  // edit s.inoue 2014/03/18
		    	  String filterval = filterClauses[0].getValue().toString();
		    	  // JApplication.clauseDesign[ii].setValue(filterClauses[0].getValue());
		    	  if(filterval.startsWith("%"))
		    		  filterval = filterval.replaceAll("%", "");
		    	  JApplication.clauseDesign[ii].setValue(filterval);
				  ii++;
			    }
		    }else{
		    	// eidt s.inoue 2012/11/16
		    	if (firstViewFlg){

		    		// add s.inoue 2013/11/06
		    		if(JApplication.clauseDesign != null){
		    			for (int i = 0; i < JApplication.clauseDesign.length; i++) {
			    			gridParams.getFilteredColumns().put(JApplication.clauseDesign[i].getAttributeName(),
									new FilterWhereClause[] { JApplication.clauseDesign[i], null });
						}
		    		}else{
						DateFormat format = new SimpleDateFormat("yyyy");
						// eidt s.inoue 2013/01/21
						// ���N���{�N�x
						// String cYear = format.format(new Date());
						String cYear = String.valueOf(FiscalYearUtil.getThisYear());

				    	// �N�x�͌��f���{������̏ꍇ������̂ŏȗ�
						FilterWhereClause clauseDesign = new FilterWhereClause();
						clauseDesign.setAttributeName("NENDO");
						clauseDesign.setOperator("=");
						clauseDesign.setValue(cYear);

						gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
								new FilterWhereClause[] { clauseDesign, null });
		    		}
		    		
// del s.inoue 2013/11/19
//		    		// add s.inoue 2013/11/06
//		    		if(JApplication.currentSortedColumns != null){
//		    			for (int i = 0; i < JApplication.currentSortedColumns.size(); i++) {
//			    			gridParams.getCurrentSortedColumns().add(JApplication.currentSortedColumns.get(i));
//						}
//		    		}
//
//		    		if(JApplication.currentSortedVersusColumns != null){
//		    			for (int i = 0; i < JApplication.currentSortedVersusColumns.size(); i++) {
//			    			gridParams.getCurrentSortedVersusColumns().add(JApplication.currentSortedVersusColumns.get(i));
//						}
//		    		}

		    	}
		    }

			// ViewSettings.getGridPageSize()
			int curpage = startIndex/JApplication.gridViewSearchCount + action;

			grid.currentPage = curpage;
			// System.out.println("���݃y�[�W:" + grid.currentPage);

			// eidt s.inoue 2011/04/08
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}

//			Set<String> keys = filteredColumns.keySet();
//			System.out.println(keys.toArray()[0]);
//			String keyColumn = (String) keys.toArray()[0];
//			boolean bln = filteredColumns.containsKey(keyColumn);
//			boolean bln2 = filteredColumns.containsValue("'00000000001'");
//			System.out.println(bln + "   " + bln2);
//			HashMap<Object, Object> hm = filteredColumns.get(keyColumn);
//			System.out.println(hm.get(keyColumn));
//			String[] obj = (String[]) keys.toArray();
//			System.out.println(obj[0]);

			// openswing s.inoue 2011/01/14
			// String sql = buildSQL();
			StringBuilder sb = new StringBuilder();

			sb.append("SELECT '' as CHECKBOX_COLUMN, GET_NENDO.NENDOS,TK.UKETUKE_ID UKETUKE_ID,TK.NAME NAME,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO,");
			sb.append("TK.SEX SEX,TK.BIRTHDAY BIRTHDAY,");
			sb.append("TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.HKNJANUM HKNJANUM,TK.SIHARAI_DAIKOU_BANGO SIHARAI_DAIKOU_BANGO,");
			sb.append("TK.KANANAME KANANAME,TT.HANTEI_NENGAPI,TT.TUTI_NENGAPI,");
			sb.append("TT.KENSA_NENGAPI,");
			// eidt s.inoue 2013/03/26
			sb.append("GET_INPUT.INPUT_FLG");
			// eidt s.inoue 2012/10/24
			// sb.append("case TT.KEKA_INPUT_FLG when 1 then '��' when 2 then '��' else '��' end as KEKA_INPUT_FLG ");
			// eidt s.inoue 2013/03/26
			// sb.append("case TT.KEKA_INPUT_FLG when 1 then '1' when 2 then '2' else '1' end as KEKA_INPUT_FLG ");
			sb.append(" FROM T_KOJIN TK");
			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");

			// eidt s.inoue 2012/11/05

			// �N�x���k���̏ꍇ�ɔN�x�𓖂č���
			DateFormat format = new SimpleDateFormat("yyyy");
			// eidt s.inoue 2013/01/24
			// String cYear = format.format(new Date());
			String cYear = String.valueOf(FiscalYearUtil.getThisYear());

			// sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when TT.NENDO is not null then TT.NENDO ");
			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,");
			// eidt s.inoue 2012/11/16
			// sb.append(" case when TT.NENDO is null then '"+ cYear+ "' ");
			sb.append(" case when TT.KENSA_NENGAPI is null then '"+ cYear+ "' ");

			sb.append(" when substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
			sb.append(" then CAST(substring(TT.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1  else substring(TT.KENSA_NENGAPI FROM 1 FOR 4) end as NENDOS ");
			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_NENDO  ON GET_NENDO.UKETUKE_ID = TT.UKETUKE_ID ");
			sb.append(" AND GET_NENDO.KENSA_NENGAPI = TT.KENSA_NENGAPI ");

			// eidt s.inoue 2013/03/26
			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,");
			sb.append(" case TT.KEKA_INPUT_FLG when 1 then '1' when 2 then '2' else '1' end as INPUT_FLG ");
			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_INPUT ");
			sb.append(" ON GET_INPUT.UKETUKE_ID = TT.UKETUKE_ID  AND GET_INPUT.KENSA_NENGAPI = TT.KENSA_NENGAPI ");

			// eidt s.inoue 2011/07/28
			// add s.inoue 2012/11/21
			if (currentSortedColumns.size()==0  &&
					currentSortedVersusColumns.size()==0){
				sb.append(" ORDER BY GET_NENDO.NENDOS DESC,KANANAME,BIRTHDAY,TT.KENSA_NENGAPI DESC");
			}

			// add s.inoue 2012/12/05
			firstViewFlg = false;

			cnterrflg = false;
			// eidt s.inoue 2011/05/10
			// ViewSettings.getGridPageSize()
			return QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sb.toString(),
							vals,
							attribute2dbField,
							JKenshinKekkaSearchListFrameData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewSearchCount, true);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}


//	/**
//	* SQL����g�ݗ��Ă�
//	*
//	* @return SQL��
//	*/
//	private String buildSQL() {
//		StringBuilder query = new StringBuilder("select ");
//		for (String element : SQL_SELECT_PART) {
//			query.append(element);
//		}
//		query.append(" from T_KOJIN AS kojin ");
//		query.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI AS tokutei ");
//		query.append(" ON kojin.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		query.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
//		query.append(" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//		query.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//		query.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//		query.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
//		query.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
//		query.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
//		query.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		query.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
//		query.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//		return query.toString();
//	}
	// add s.inoue 2013/02/27
    public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }
	/**
	 * �폜�{�^���C�x���g ��grid��READONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("delete from T_SHIHARAI where SHIHARAI_DAIKO_NO=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JKenshinKekkaSearchListFrameData vo = (JKenshinKekkaSearchListFrameData)persistentObjects.get(i);
				stmt.setString(1, vo.getUKETUKE_ID());
				stmt.execute();
			}
			return new VOResponse(new Boolean(true));
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		} finally {
			try {
				stmt.close();
				conn.commit();
			} catch (SQLException ex1) {
			}
		}
	}

	 /**
	   * Callback method invoked each time a cell is edited: this method define if the new value if valid.
	   * This method is invoked ONLY if:
	   * - the edited value is not equals to the old value OR it has exmplicitely called setCellAt or setValueAt
	   * - the cell is editable
	   * Default behaviour: cell value is valid.
	   * @param rowNumber selected row index
	   * @param attributeName attribute name related to the column currently selected
	   * @param oldValue old cell value (before cell editing)
	   * @param newValue new cell value (just edited)
	   * @return <code>true</code> if cell value is valid, <code>false</code> otherwise
	   */
	  public boolean validateCell(int rowNumber,String attributeName,Object oldValue,Object newValue) {
	    if (attributeName.equals("currencyValue") || attributeName.equals("numericValue")) {
	      grid.getGrid().getBottomTable().reload();
	    }
	    if ((attributeName.equals("intValue") && new Integer(0).equals(newValue))) {
	      // zero value not allowed...
	      return false;
	    }

	    return true;
	  }

	  // eidt s.inoue 2011/04/28
	  /**
	   * Callback method invoked when the user has selected another row.
	   * @param rowNumber selected row index
	   */
	  public void rowChanged(int rowNumber) {
		   // int curpg = grid.currentPage*(grid.currentSepalatePage-1);
		   // eidt s.inoue 2011/05/10
		   // ViewSettings.getGridPageSize()
		  // add s.inoue 2012/12/05
		  // int rowCount = ((grid.currentPage -1)*JApplication.gridViewSearchCount) + rowNumber + 1;
		  int rowCount = rowNumber + 1;

		  if (grid.currentPage -1 > 0){
			  // rowCount = ((grid.currentPage -1)*JApplication.gridViewSearchCount) + rowNumber + 1;
			  rowCount = grid.getGrid().getTable().getstartIndex() + rowNumber + 1;
		  }

		   grid.currentRow = rowNumber;
		   grid.currentTotalRows = rowCount;
		   grid.countText.setText(rowCount + "����");
		   // add s.inoue 2012/11/21
//		   for (int i=0;i < JApplication.selectedHistoryRows.size(); i++) {
//			   JApplication.selectedHistoryRows.remove(i);
//		   }
	  }

	  private boolean cnterrflg = false;
	  /**
	   * @param grid grid
	   * @param row selected row index
	   * @param attributeName attribute name that identifies the selected grid column
	   * @return <code>true</code> if the selected cell is editable, <code>false</code> otherwise
	   */
	  public boolean isCellEditable(GridControl grid,int row,String attributeName) {

		  int jcnt = 0;
		  JApplication.selectedPreservRows = new ArrayList<Integer>();

		  JKenshinKekkaSearchListFrameData vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(row);
		    if ("CHECKBOX_COLUMN".equals(attributeName)){

		    	if(cnterrflg){
		    		cnterrflg = false;return false;
		    	}

		    	// add s.inoue 2013/04/05
		    	if (vo == null) return false;

		    	if (vo.getCHECKBOX_COLUMN() == null)
		    		return false;
		    	if (vo.getCHECKBOX_COLUMN().equals("N")){
		    	  vo.setCHECKBOX_COLUMN("Y");
		    	  /* 1���ׂ̈̏��� */
				  for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
					  JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y")){
							jcnt++;
							if (!JApplication.selectedPreservRows.contains(i))
							JApplication.selectedPreservRows.add(i);
						}
				  }

		    	  if (jcnt==1)
		    			JApplication.firstCheckedFlg=true;
		    		return false;
		    	}else if(vo.getCHECKBOX_COLUMN().equals("Y")){
		    		/* 1���ׂ̈̏��� */
					for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
						JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y")){
							jcnt++;
							if (!JApplication.selectedPreservRows.contains(i))
							JApplication.selectedPreservRows.add(i);
						}
					}

					int kcnt=0;
					for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
						JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y"))
							kcnt++;
					}
					if(kcnt == 2){
						cnterrflg = true;
					}

					vo.setCHECKBOX_COLUMN("N");
		    		return false;
		    	}else{
		    	  vo.setCHECKBOX_COLUMN("Y");
				  for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
					  JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y")){
							jcnt++;
							if (!JApplication.selectedPreservRows.contains(i))
							JApplication.selectedPreservRows.add(i);
						}
				  }
			      if (jcnt==1)
			    		JApplication.firstCheckedFlg=true;
		    		return false;
		    	}
		    }
		    return grid.isFieldEditable(row,attributeName);
	  }

	  /**
	   * Callback method invoked each time an input control is edited: this method define if the new value is valid.
	   * Default behaviour: input control value is valid.
	   * @param attributeName attribute name related to the input control currently edited
	   * @param oldValue old input control value (before editing)
	   * @param newValue new input control value (just edited)
	   * @return <code>true</code> if input control value is valid, <code>false</code> otherwise
	   */
	  public boolean validateControl(String attributeName,Object oldValue,Object newValue) {
	    if (attributeName.equals("numericValue") &&
	        newValue!=null &&
	        ((BigDecimal)newValue).doubleValue()==0) {
	      // zero value not allowed...
	      return false;
	    }
	    return true;
	  }

	  public void selectedCell(int i, int j, String s, ValueObject valueobject)
	  {
	  }

	@Override
	public FormController getGrid() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public void jbInit() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void reloadData() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void setControl(Connection conn,
			JKenshinKekkaSearchListFrameCtl controller) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void setJFrameeDitable() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}
