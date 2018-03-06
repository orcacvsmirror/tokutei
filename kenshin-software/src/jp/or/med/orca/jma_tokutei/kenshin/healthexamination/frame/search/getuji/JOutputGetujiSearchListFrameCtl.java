package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Getuji;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JOutputGetujiSearchListFrameCtl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = -13396357757642984L;	// edit n.ohkubo 2014/10/01　追加
	
	private JOutputGetujiSearchListFrame grid = null;
	private Connection conn = null;
//	private JOutputGetujiSearchListFrame frame;	// edit n.ohkubo 2014/10/01　未使用なので削除
	// add s.inoue 2012/11/16
	private boolean firstViewFlg =true;

	private static Logger logger = Logger.getLogger(JOutputGetujiSearchListFrameCtl.class);

//	// 検索ボタン押下時のSQLで使用
//	private static final String[] SQL_SELECT_PART = {
//			"'' as CHECKBOX_COLUMN,",
//			"kojin.UKETUKE_ID,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.BIRTHDAY,",
//			"kojin.SEX,",
//			"case kojin.SEX when 1 then '男性' when 2 then '女性' end as SEX_NAME,",
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
//			"case tokutei.KEKA_INPUT_FLG when 1 then '未' "
//					+ "when 2 then '済' else '未' end as KEKA_INPUT_FLG " };

	public JOutputGetujiSearchListFrameCtl(Connection conn) {
		this.conn = conn;
		grid = new JOutputGetujiSearchListFrame(conn, this);
	}

	public JOutputGetujiSearchListFrame getGridControl(){
		return grid;
	}

	/**
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the column header; this text will be automatically translated according to internationalization settings
	*/
	@Override
	public String getHeaderTooltip(String attributeName) {
	    return attributeName;
	}

    /**
	* @param row row index in the grid
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
	*/
	@Override
	public String getCellTooltip(int row,String attributeName) {

		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
//		return attributeName+" at row "+row;
	}
	/**
	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
	 *
	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
	 */
	@Override
	public boolean beforeInsertGrid(GridControl grid) {
// del
//		new JShiharaiMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
	}

	/**
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	@Override
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
// del
//		JShiharaiMasterMaintenanceListData vo = (JShiharaiMasterMaintenanceListData)persistentObject;
//		new JShiharaiMasterMaintenanceDetailCtl(grid, vo.getUketuke_id(), conn);
	}
	/**
     * Callbackメソッド
     * exportダイアログを表示する前に呼び出す
     * gridに定義されたformatを再定義して呼び出せます
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 @Override
	public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.CSV_FORMAT1,ExportOptions.CSV_FORMAT2,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
	 }

	 // add s.inoue 2011/04/26
	/**
	 * Callback method called when the data loading is completed.
	 * @param error <code>true</code> if an error occours during data loading, <code>false</code> if data loading is successfully completed
	 */
	 @Override
	public void loadDataCompleted(boolean error) {
//		    frame.getControlCurrency().setCurrencySymbol("$");
//		    frame.getControlCurrency().setDecimals(2);
//		    frame.getControlCurrency().setDecimalSymbol('.');
//		    frame.getControlCurrency().setGroupingSymbol(',');
//			JKenshinKekkaSearchListFrameData vo = (JKenshinKekkaSearchListFrameData)grid.getMainPanel().getVOModel().getValueObject();
//		    frame.getGrid().getOtherGridParams().put("empCode",vo.getEmpCode());
//		    frame.getGrid().reloadData();

		 // del s.inoue 2013/04/05
		 setSelectedRow(JApplication.selectedHistoryRows);

		// eidt s.inoue 2011/05/10
		// ViewSettings.getGridPageSize()
		int rowCount = ((grid.currentPage - 1)*JApplication.gridViewSearchCount) + grid.currentRow + 1;
		// add s.inoue 2012/12/05
		if (grid.getGrid().getTable().getstartIndex() != 0)
			rowCount = grid.getGrid().getTable().getstartIndex() + grid.currentRow + 1;

		grid.currentTotalRows = rowCount;
		grid.countText.setText(rowCount + "件目");
	 }

	 // add s.inoue 2012/11/20
	// チェックボックス設定
	 private void setSelectedRow(ArrayList<Integer> selectedRows){
			if (selectedRows == null)return;
			// add s.inoue 2013/04/05
			if (grid.getGrid().getVOListTableModel().getRowCount() == 0)return;

			for (int i = 0; i < selectedRows.size(); i++) {
				grid.getGrid().getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
				grid.getGrid().getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
			}
	 }

	 /**
	 * @param grid grid
	 * @param row selected row index
	 * @param attributeName attribute name that identifies the selected grid column
	 * @return <code>true</code> if the selected cell is editable, <code>false</code> otherwise
	 */
	 @Override
	public boolean isCellEditable(GridControl grid,int row,String attributeName) {
		  JOutputGetujiSearchListFrameData vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(row);
	    if ("CHECKBOX_COLUMN".equals(attributeName)){
	    	// eidt s.inoue 2011/04/28
	    	// add s.inoue 2012/11/02
	    	if (vo == null) return false;
	    	if (vo.getCHECKBOX_COLUMN()==null) return false;
	    	if (vo.getCHECKBOX_COLUMN().equals("N")){
	    		vo.setCHECKBOX_COLUMN("Y");
	    		return false;
	    	}else if(vo.getCHECKBOX_COLUMN().equals("Y")){
	    		vo.setCHECKBOX_COLUMN("N");
	    		return false;
	    	}else{
	    		vo.setCHECKBOX_COLUMN("Y");
	    		// eidt s.inoue 2012/11/22
	    		// return true;
	    		return false;
	    	}
//	      return vo.getCheckValue()!=null && vo.getCheckValue().booleanValue();
	    }
	    return grid.isFieldEditable(row,attributeName);
	 }

	 /**
	 * Callback method invoked each time a cell is edited: this method define if the new value is valid.
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
	  @Override
	public boolean validateCell(int rowNumber,String attributeName,Object oldValue,Object newValue) {
		  // this method has been overrrided to listen for numeric/currency cell changes:
		    // in this case it will be invoked getTotals method to refresh bottom grid content...
		    if (attributeName.equals("currencyValue") || attributeName.equals("numericValue")) {
		      grid.getGrid().getBottomTable().reload();
		    }
		    if (attributeName.equals("intValue") && new Integer(0).equals(newValue)) {
		      // zero value not allowed...
		      return false;
		    }
		    return true;
	  }

	  // eidt s.inoue 2012/11/20
	  /**
	   * Callback method invoked when the user has selected another row.
	   * @param rowNumber selected row index
	   */
	  @Override
	public void rowChanged(int rowNumber) {

		  // add s.inoue 2012/12/05
		  // int rowCount = ((grid.currentPage -1)*JApplication.gridViewSearchCount) + rowNumber + 1;
		  int rowCount = rowNumber + 1;

		  if (grid.currentPage -1 > 0){
			  // rowCount = ((grid.currentPage -1)*JApplication.gridViewSearchCount) + rowNumber + 1;
			  rowCount = grid.getGrid().getTable().getstartIndex() + rowNumber + 1;
		  }
		  grid.currentRow = rowNumber;
		  grid.currentTotalRows = rowCount;
		  grid.countText.setText(rowCount + "件目");
			// System.out.println("現在行" + rowCount);
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

	// edit n.ohkubo 2014/10/01　start　検索条件の保持等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）  
//	/**
//	 * gridのデータがロードされた時のCallback関数
//	 * @param PREVIOUS_BLOCK_ACTION:前行へ移動, NEXT_BLOCK_ACTION:次行へ移動 or LAST_BLOCK_ACTION:最終行へ移動
//	 * @param startPos start position of data fetching in result set
//	 * @param filteredColumns filtered columns
//	 * @param currentSortedColumns sorted columns
//	 * @param currentSortedVersusColumns ordering versus of sorted columns
//	 * @param valueObjectType v.o. type
//	 * @param otherGridParams other grid parameters
//	 * @return response from the server: an object of type VOListResponse
//	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
//	 */
//	@Override
//	public Response loadData(int action, int startIndex, Map filteredColumns,
//			ArrayList currentSortedColumns,
//			ArrayList currentSortedVersusColumns, Class valueObjectType,
//			Map otherGridParams) {
//		// PreparedStatement stmt = null;
//		try {
//			ArrayList vals = new ArrayList();
//			Map attribute2dbField = new HashMap();
//			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");
//			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
//			attribute2dbField.put("NAME","NAME");
//			attribute2dbField.put("HIHOKENJYASYO_KIGOU","KOJIN.HIHOKENJYASYO_KIGOU");
//			attribute2dbField.put("HIHOKENJYASYO_NO","KOJIN.HIHOKENJYASYO_NO");
//			attribute2dbField.put("KENSA_NENGAPI","TOKUTEI.KENSA_NENGAPI");
//			attribute2dbField.put("SEX","SEX");
//			attribute2dbField.put("KEKA_INPUT_FLG","KEKA_INPUT_FLG");
//			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
//			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
//			attribute2dbField.put("HKNJANUM","KOJIN.HKNJANUM");
//			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","KOJIN.SIHARAI_DAIKOU_BANGO");
//			attribute2dbField.put("KANANAME","KANANAME");
//
//			attribute2dbField.put("HKNJYA_LIMITDATE_START","HKNJYA_LIMITDATE_START");
//			attribute2dbField.put("HKNJYA_LIMITDATE_END","HKNJYA_LIMITDATE_END");
//
//			attribute2dbField.put("HANTEI_NENGAPI","HANTEI_NENGAPI");
//			attribute2dbField.put("TUTI_NENGAPI","TUTI_NENGAPI");
//			attribute2dbField.put("NENDO","GET_NENDO.NENDO");
//
//			attribute2dbField.put("TANKA_GOUKEI","TANKA_GOUKEI");
//			attribute2dbField.put("MADO_FUTAN_GOUKEI","MADO_FUTAN_GOUKEI");
//			attribute2dbField.put("SEIKYU_KINGAKU","SEIKYU_KINGAKU");
//			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
//			// 月次
//			attribute2dbField.put("JISI_KBN","GET_JISIKBN.JISI_KBN_WK");
//			// eidt s.inoue 2013/07/30
//			// attribute2dbField.put("HENKAN_NITIJI","TOKUTEI.HENKAN_NITIJI");
//			// attribute2dbField.put("OUTPUT_HL7","OUTPUT_HL7");
//			attribute2dbField.put("HENKAN_NITIJI","HENKAN_NITIJI");
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//
//			
//			// add s.inoue 2013/11/06
//						// 初期値 又は あいまい検索の設定
//					    if (currentSortedColumns.iterator().hasNext()){
//							Iterator it_dt = currentSortedColumns.iterator();
//							String sortClauses = null;
//
//						    // add s.inoue 2013/11/06
//						    JApplication.currentSortedColumns = new ArrayList();
//
//						    while(it_dt.hasNext()) {
//						      sortClauses = (String)it_dt.next();
//						      // add s.inoue 2013/11/06
//						      JApplication.currentSortedColumns.add(sortClauses);
//						    }
//					    }
//
//					    if (currentSortedVersusColumns.iterator().hasNext()){
//							Iterator it_dt = currentSortedVersusColumns.iterator();
//							String sortClauses = null;
//
//						    // add s.inoue 2013/11/06
//						    JApplication.currentSortedVersusColumns = new ArrayList();
//
//						    while(it_dt.hasNext()) {
//						      sortClauses = (String)it_dt.next();
//						      // add s.inoue 2013/11/06
//						      JApplication.currentSortedVersusColumns.add(sortClauses);
//						    }
//					    }
//
//						
//						// 初期値 又は あいまい検索の設定
//					    if (filteredColumns.values().iterator().hasNext()){
//							Iterator it_dt = filteredColumns.values().iterator();
//						    FilterWhereClause[] filterClauses = null;
//
//						    // add s.inoue 2013/11/06
//						    int ii = 0;
//						    JApplication.clauseDesign = new FilterWhereClause[filteredColumns.size()];
//						    
//						    // add s.inoue 2013/03/25
//						    while(it_dt.hasNext()) {
//						      filterClauses = (FilterWhereClause[])it_dt.next();
//
//						      if (filterClauses[0].getOperator().equals("like")){
//					 			  // add s.inoue 2014/03/18
//						    	  String filterval = filterClauses[0].getValue().toString();
//						    	  if(!filterval.startsWith("%"))
//						    		  filterval = "%"+filterval+"%";
//						    	  filterClauses[0].setValue(filterval);
//
//									gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
//											new FilterWhereClause[] { filterClauses[0], null });
//			// eidt s.inoue 2013/03/25
////						      }else if (filterClauses[0].getOperator().equals("like")){
////						    	  System.out.println("");
//			// del s.inoue 2013/03/26
////						      }else{
////						    	  filterClauses[0].setValue(filterClauses[0].getValue());
////						    	  JApplication.gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
////											new FilterWhereClause[] { filterClauses[0], null });
//
////							      // add s.inoue 2013/11/05
////							      JApplication.filterClauses = (FilterWhereClause[])it_dt.next();
////							      JApplication.filterClauses[filter_i] = filterClauses[0];
////							      filter_i++;
//						      }
//
//						      // add s.inoue 2013/11/06
//						      JApplication.clauseDesign[ii] = new FilterWhereClause();
//							  JApplication.clauseDesign[ii].setAttributeName(filterClauses[0].getAttributeName());
//							  JApplication.clauseDesign[ii].setOperator(filterClauses[0].getOperator());
//							  
//							// edit n.ohkubo 2014/08/21
////							  // edit s.inoue 2014/03/18
////					    	  String filterval = filterClauses[0].getValue().toString();
//							  String filterval;
//							  Object obj = filterClauses[0].getValue();
//							  if (obj == null) {
//								  filterval = "";
//							  } else {
//								  filterval = obj.toString();
//							  }
//							  
//					    	  // JApplication.clauseDesign[ii].setValue(filterClauses[0].getValue());
//					    	  if(filterval.startsWith("%"))
//					    		  filterval = filterval.replaceAll("%", "");
//					    	  JApplication.clauseDesign[ii].setValue(filterval);
//							  ii++;
//						      
//						    }
//					    }else{
//					    	
//					    	// edit s.inoue 2014/06/23
//					    	if (firstViewFlg){
//				    		// add s.inoue 2013/11/06
//				    		if(JApplication.clauseDesign != null){
//				    			for (int i = 0; i < JApplication.clauseDesign.length; i++) {
//					    			gridParams.getFilteredColumns().put(JApplication.clauseDesign[i].getAttributeName(),
//											new FilterWhereClause[] { JApplication.clauseDesign[i], null });
//								}
//				    		}else{
//								DateFormat format = new SimpleDateFormat("yyyy");
//								// eidt s.inoue 2013/01/21
//								// 今年→本年度
//								// String cYear = format.format(new Date());
//								String cYear = String.valueOf(FiscalYearUtil.getThisYear());
//
//						    	// 年度は健診実施日が空の場合があるので省略
//								FilterWhereClause clauseDesign = new FilterWhereClause();
//								clauseDesign.setAttributeName("NENDO");
//								clauseDesign.setOperator("=");
//								clauseDesign.setValue(cYear);
//
//								gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
//										new FilterWhereClause[] { clauseDesign, null });
//				    		}
//// del s.inoue 2014/06/23					    	
////				    		// add s.inoue 2013/11/06
////				    		if(JApplication.currentSortedColumns != null){
////				    			for (int i = 0; i < JApplication.currentSortedColumns.size(); i++) {
////					    			gridParams.getCurrentSortedColumns().add(JApplication.currentSortedColumns.get(i));
////								}
////				    		}
////
////				    		if(JApplication.currentSortedVersusColumns != null){
////				    			for (int i = 0; i < JApplication.currentSortedVersusColumns.size(); i++) {
////					    			gridParams.getCurrentSortedVersusColumns().add(JApplication.currentSortedVersusColumns.get(i));
////								}
////				    		}
//					    	}
//					    }
//			
//// del s.inoue 2014/06/23			
////			// 初期値 又は あいまい検索の設定
////		    if (filteredColumns.values().iterator().hasNext()){
////				Iterator it_dt = filteredColumns.values().iterator();
////			    FilterWhereClause[] filterClauses = null;
////
////			    while(it_dt.hasNext()) {
////			      filterClauses = (FilterWhereClause[])it_dt.next();
////			      // comment
//////			      System.out.println(filterClauses[0].getAttributeName());
//////			      System.out.println(filterClauses[0].getValue());
//////			      System.out.println(filterClauses[0].getOperator());
////
////			      if (filterClauses[0].getOperator().equals("like")){
////		 			  // add s.inoue 2014/03/18
////			    	  String filterval = filterClauses[0].getValue().toString();
////			    	  if(!filterval.startsWith("%"))
////			    		  filterval = "%"+filterval+"%";
////			    	  filterClauses[0].setValue(filterval);
////
////						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
////								new FilterWhereClause[] { filterClauses[0], null });
////			      }
////			    }
////		    }else{
////		    	// eidt s.inoue 2012/11/16
////		    	DateFormat format = new SimpleDateFormat("yyyy");
////		    	// eidt s.inoue 2013/01/21
////		    	// String cYear = format.format(new Date());
////		    	String cYear = String.valueOf(FiscalYearUtil.getThisYear());
////
////		    	// eidt s.inoue 2012/11/16
////		    	if (firstViewFlg){
////					FilterWhereClause clauseDesign = new FilterWhereClause();
////					clauseDesign.setAttributeName("NENDO");
////					clauseDesign.setOperator("=");
////					clauseDesign.setValue(cYear);
////
////					gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
////							new FilterWhereClause[] { clauseDesign, null });
////
////					// eidt s.inoue 2012/10/29
////					FilterWhereClause clauseDesign2 = new FilterWhereClause();
////					clauseDesign2.setAttributeName("JISI_KBN");
////					clauseDesign2.setOperator("=");
////					clauseDesign2.setValue("1");
////
////					gridParams.getFilteredColumns().put(clauseDesign2.getAttributeName(),
////							new FilterWhereClause[] { clauseDesign2, null });
////		    	}
////		    }
//
//			// eidt s.inoue 2011/04/28
//			int curpage = startIndex/JApplication.gridViewSearchCount + action;
//			grid.currentPage = curpage;
//			System.out.println("現在ページ:" + grid.currentPage);
//
//			// eidt s.inoue 2011/04/08
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}
//
//			// openswing s.inoue 2011/01/14
//			// String sql = buildSQL();
//			StringBuilder sb = new StringBuilder();
//
////			sb.append("SELECT '' as CHECKBOX_COLUMN,TT.NENDO NENDO,TK.UKETUKE_ID UKETUKE_ID,TK.NAME NAME,TK.HIHOKENJYASYO_KIGOU HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO HIHOKENJYASYO_NO,");
////			sb.append("TK.SEX SEX,TK.BIRTHDAY BIRTHDAY,");
////			sb.append("TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.HKNJANUM HKNJANUM,TK.SIHARAI_DAIKOU_BANGO SIHARAI_DAIKOU_BANGO,");
////			sb.append("TK.KANANAME KANANAME,TT.HANTEI_NENGAPI HANTEI_NENGAPI,TT.TUTI_NENGAPI TUTI_NENGAPI,");
////			sb.append("TT.KENSA_NENGAPI KENSA_NENGAPI,TT.KEKA_INPUT_FLG KEKA_INPUT_FLG");
////			sb.append(" FROM T_KOJIN TK");
////			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
////			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");
//			sb.append(createSearchQuery(currentSortedColumns,currentSortedVersusColumns));
//
//			// add s.inoue 2012/11/16
//			firstViewFlg = false;
//
//			return QueryUtil
//					.getQuery(
//							conn,
//							new UserSessionParameters(),
//							sb.toString(),
//							vals,
//							attribute2dbField,
//							JOutputGetujiSearchListFrameData.class,
//							"Y", "N",
//							null, gridParams,JApplication.gridViewSearchCount, true);
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return new ErrorResponse(ex.getMessage());
//		}
//	}
	/**
	 * gridのデータがロードされた時のCallback関数
	 * @param PREVIOUS_BLOCK_ACTION:前行へ移動, NEXT_BLOCK_ACTION:次行へ移動 or LAST_BLOCK_ACTION:最終行へ移動
	 * @param startPos start position of data fetching in result set
	 * @param filteredColumns filtered columns
	 * @param currentSortedColumns sorted columns
	 * @param currentSortedVersusColumns ordering versus of sorted columns
	 * @param valueObjectType v.o. type
	 * @param otherGridParams other grid parameters
	 * @return response from the server: an object of type VOListResponse
	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
	 */
	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns, ArrayList currentSortedColumns, ArrayList currentSortedVersusColumns, Class valueObjectType, Map otherGridParams) {
		try {
			Map<String, String> attribute2dbField = new HashMap<String, String>();
			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");
			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
			attribute2dbField.put("NAME","NAME");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU","KOJIN.HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO","KOJIN.HIHOKENJYASYO_NO");
			attribute2dbField.put("KENSA_NENGAPI","TOKUTEI.KENSA_NENGAPI");
			attribute2dbField.put("KENSA_NENGAPI2","TOKUTEI.KENSA_NENGAPI");
			attribute2dbField.put("SEX","SEX");
			attribute2dbField.put("KEKA_INPUT_FLG","KEKA_INPUT_FLG");
			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
			attribute2dbField.put("HKNJANUM","KOJIN.HKNJANUM");
			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","KOJIN.SIHARAI_DAIKOU_BANGO");
			attribute2dbField.put("KANANAME","KANANAME");
			attribute2dbField.put("HKNJYA_LIMITDATE_START","HKNJYA_LIMITDATE_START");
			attribute2dbField.put("HKNJYA_LIMITDATE_END","HKNJYA_LIMITDATE_END");
			attribute2dbField.put("HANTEI_NENGAPI","HANTEI_NENGAPI");
			attribute2dbField.put("TUTI_NENGAPI","TUTI_NENGAPI");
			attribute2dbField.put("NENDO","GET_NENDO.NENDO");
			attribute2dbField.put("TANKA_GOUKEI","TANKA_GOUKEI");
			attribute2dbField.put("MADO_FUTAN_GOUKEI","MADO_FUTAN_GOUKEI");
			attribute2dbField.put("SEIKYU_KINGAKU","SEIKYU_KINGAKU");
			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
			attribute2dbField.put("JISI_KBN","GET_JISIKBN.JISI_KBN_WK");
			attribute2dbField.put("HENKAN_NITIJI","HENKAN_NITIJI");
			attribute2dbField.put("HENKAN_NITIJI2","HENKAN_NITIJI");

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);
			
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			
			//検索・ソート条件の設定
			FilterSetting filterSetting = new FilterSetting(JApplication.SCREEN_GETUJI_CODE, grid.getColumnContainer());
			filterSetting.setFilterSort(firstViewFlg, grid.getSavedJCheckBox(), gridParams, filteredColumns, currentSortedColumns, currentSortedVersusColumns, new String[]{"NENDO", "="});

			//ページの設定
			int curpage = startIndex/JApplication.gridViewSearchCount + action;
			grid.currentPage = curpage;
//			System.out.println("現在ページ:" + grid.currentPage);


			StringBuilder sb = new StringBuilder();
			sb.append(createSearchQuery(currentSortedColumns,currentSortedVersusColumns));

			//データの取得実行
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sb.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JOutputGetujiSearchListFrameData.class,
											"Y",
											"N",
											null,
											gridParams,
											JApplication.gridViewSearchCount,
											true
			);
			
			//「を含む」検索は「like %入力値%」で検索するため、入力値に"%"を付加しているので、付加した"%"を削除する
			filterSetting.setLikeColumns(filteredColumns, false);
			
			//検索画面へソート条件を反映する（「条件を保持しない」の場合、検索後、検索画面を閉じて再度開いたときに、値が反映されていない現象の対応）
			if (!grid.getSavedJCheckBox().isSelected()) {
				ColumnContainer columnContainer = grid.getColumnContainer();
				filterSetting.setSortColumnsAfter(columnContainer, currentSortedColumns, currentSortedVersusColumns);
			}
			
			//0件の場合、メッセージの表示（画面オープン初回以外）
			if (!firstViewFlg) {
				if ((result != null) && (!result.isError() && (result instanceof VOListResponse))){
					if (((VOListResponse)result).getRows().size() == 0) {
						JErrorMessage.show("M3550", getGridControl());
					}
				}
			}

			firstViewFlg = false;
			
			return result;

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}
	// edit n.ohkubo 2014/10/01　end　検索条件の保持等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）

	// eidt s.inoue 2012/10/15
	// LeftJoinからRightJoinへ
	/**
	 * 検索用の SQL を作成する。
	 */
	private StringBuffer createSearchQuery(ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns
			) {

		StringBuffer sb = new StringBuffer();
		// TOKUTEI.HENKAN_NITIJI HENKAN_NITIJI,
		// eidt s.inoue 2013/07/30
		// sb.append("SELECT DISTINCT TOKUTEI.HENKAN_NITIJI OUTPUT_HL7,");
		sb.append("SELECT DISTINCT TOKUTEI.HENKAN_NITIJI HENKAN_NITIJI,");

		// edit s.inoue 2012/10/26
		// sb.append("case when KESAI_WK.JISI_KBN is not null then '済' else '未' end JISI_KBN,");
		// sb.append("case when KESAI_WK.JISI_KBN is not null then '1' else '2' end JISI_KBN_WK,");
		sb.append("GET_JISIKBN.JISI_KBN_WK,");

		sb.append("KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.UKETUKE_ID UKETUKE_ID,KOJIN.BIRTHDAY BIRTHDAY,");
		sb.append("KOJIN.SEX SEX,KOJIN.NAME NAME,KOJIN.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,KOJIN.HIHOKENJYASYO_KIGOU,");
		sb.append("KOJIN.HIHOKENJYASYO_NO,TOKUTEI.KENSA_NENGAPI,");
		sb.append("KOJIN.HKNJANUM,KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.KANANAME KANANAME,GET_NENDO.NENDO,");
		sb.append("HOKENJYA.HKNJYA_LIMITDATE_START HKNJYA_LIMITDATE_START,HOKENJYA.HKNJYA_LIMITDATE_END HKNJYA_LIMITDATE_END, ");
	 	sb.append(" case when HOKENJYA.TANKA_HANTEI = '2' then ");
	 	// eidt s.inoue 2011/04/27 合計値判断（一時にあれば表示）
	 	sb.append(" case when KESAI_WK.TANKA_NINGENDOC is not null then KESAI_WK.TANKA_NINGENDOC else KESAI.TANKA_NINGENDOC end ");
	 	sb.append(" else ");
	 	sb.append(" case when KESAI_WK.TANKA_GOUKEI is not null then KESAI_WK.TANKA_GOUKEI else KESAI.TANKA_GOUKEI end ");
	 	sb.append(" end TANKA_GOUKEI, ");

		// sb.append("KESAI_WK.MADO_FUTAN_GOUKEI MADO_FUTAN_GOUKEI,");
	 	sb.append(" case when KESAI_WK.MADO_FUTAN_GOUKEI is not null then KESAI_WK.MADO_FUTAN_GOUKEI else KESAI.MADO_FUTAN_GOUKEI end MADO_FUTAN_GOUKEI, ");
		// sb.append("KESAI_WK.SEIKYU_KINGAKU SEIKYU_KINGAKU,");
	 	sb.append(" case when KESAI_WK.SEIKYU_KINGAKU is not null then KESAI_WK.SEIKYU_KINGAKU else KESAI.SEIKYU_KINGAKU end SEIKYU_KINGAKU, ");
	 	// sb.append("KESAI_WK.UPDATE_TIMESTAMP UPDATE_TIMESTAMP ");
	 	sb.append(" case when KESAI_WK.UPDATE_TIMESTAMP is not null then KESAI_WK.UPDATE_TIMESTAMP else KESAI.UPDATE_TIMESTAMP end UPDATE_TIMESTAMP ");

		sb.append(" FROM T_KOJIN AS KOJIN ");
		// move s.inoue 2012/10/15
		sb.append("LEFT JOIN T_KESAI_WK AS KESAI_WK ON KOJIN.UKETUKE_ID = KESAI_WK.UKETUKE_ID ");
		sb.append("LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
		sb.append("LEFT JOIN T_KESAI AS KESAI ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID ");
		sb.append("LEFT JOIN T_HOKENJYA HOKENJYA ON KOJIN.HKNJANUM = HOKENJYA.HKNJANUM ");
		sb.append(" AND HOKENJYA.YUKOU_FLG = '1' ");

		sb.append("LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
		sb.append(" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
		sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
		sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
		sb.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
		sb.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
		sb.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
		sb.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
		sb.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");

		// add s.inoue 2012/10/29
		sb.append("LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,");
		sb.append(" case when T_KESAI_WK.JISI_KBN is not null then '1' else '2' end JISI_KBN_WK");
		sb.append(" from T_KESAI_WK ) as GET_JISIKBN");
		sb.append(" ON GET_JISIKBN.UKETUKE_ID = tokutei.UKETUKE_ID");
		sb.append(" AND GET_JISIKBN.KENSA_NENGAPI = tokutei.KENSA_NENGAPI");

		// eidt s.inoue 2012/11/21
		// add s.inoue 2012/11/21
		if (currentSortedColumns.size()==0  &&
				currentSortedVersusColumns.size()==0){
			sb.append(" ORDER BY GET_NENDO.NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC ");
		}

		return sb;
	}

// eidt s.inoue 2012/10/15
// 元のソース
//	/**
//	 * 検索用の SQL を作成する。
//	 */
//	private StringBuffer createSearchQuery() {
//
//		StringBuffer sb = new StringBuffer();
//		// TOKUTEI.HENKAN_NITIJI HENKAN_NITIJI,
//		sb.append("SELECT DISTINCT TOKUTEI.HENKAN_NITIJI OUTPUT_HL7,");
//
//		// edit s.inoue 2012/10/15
//		sb.append("case when KESAI_WK.JISI_KBN is not null then '済' else '未' end JISI_KBN,");
//		// sb.append("KESAI_WK.JISI_KBN,");
//
//		sb.append("KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.UKETUKE_ID UKETUKE_ID,KOJIN.BIRTHDAY BIRTHDAY,");
//		sb.append("KOJIN.SEX SEX,KOJIN.NAME NAME,KOJIN.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,KOJIN.HIHOKENJYASYO_KIGOU,");
//		sb.append("KOJIN.HIHOKENJYASYO_NO,TOKUTEI.KENSA_NENGAPI,");
//		sb.append("KOJIN.HKNJANUM,KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.KANANAME KANANAME,GET_NENDO.NENDO,");
//		sb.append("HOKENJYA.HKNJYA_LIMITDATE_START HKNJYA_LIMITDATE_START,HOKENJYA.HKNJYA_LIMITDATE_END HKNJYA_LIMITDATE_END, ");
//	 	sb.append(" case when HOKENJYA.TANKA_HANTEI = '2' then ");
//	 	// eidt s.inoue 2011/04/27 合計値判断（一時にあれば表示）
//	 	sb.append(" case when KESAI_WK.TANKA_NINGENDOC is not null then KESAI_WK.TANKA_NINGENDOC else KESAI.TANKA_NINGENDOC end ");
//	 	sb.append(" else ");
//	 	sb.append(" case when KESAI_WK.TANKA_GOUKEI is not null then KESAI_WK.TANKA_GOUKEI else KESAI.TANKA_GOUKEI end ");
//	 	sb.append(" end TANKA_GOUKEI, ");
//
//		// sb.append("KESAI_WK.MADO_FUTAN_GOUKEI MADO_FUTAN_GOUKEI,");
//	 	sb.append(" case when KESAI_WK.MADO_FUTAN_GOUKEI is not null then KESAI_WK.MADO_FUTAN_GOUKEI else KESAI.MADO_FUTAN_GOUKEI end MADO_FUTAN_GOUKEI, ");
//		// sb.append("KESAI_WK.SEIKYU_KINGAKU SEIKYU_KINGAKU,");
//	 	sb.append(" case when KESAI_WK.SEIKYU_KINGAKU is not null then KESAI_WK.SEIKYU_KINGAKU else KESAI.SEIKYU_KINGAKU end SEIKYU_KINGAKU, ");
//	 	// sb.append("KESAI_WK.UPDATE_TIMESTAMP UPDATE_TIMESTAMP ");
//	 	sb.append(" case when KESAI_WK.UPDATE_TIMESTAMP is not null then KESAI_WK.UPDATE_TIMESTAMP else KESAI.UPDATE_TIMESTAMP end UPDATE_TIMESTAMP ");
//
//		sb.append(" FROM T_KOJIN AS KOJIN ");
//		sb.append("LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
//		sb.append("LEFT JOIN T_KESAI_WK AS KESAI_WK ON KOJIN.UKETUKE_ID = KESAI_WK.UKETUKE_ID ");
//
//		// add s.inoue 2012/10/15
////		sb.append(" AND KESAI_WK.JISI_KBN is not null ");
//
//		// eidt s.inoue 2011/04/27
//		sb.append("LEFT JOIN T_KESAI AS KESAI ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID ");
//
//		sb.append("LEFT JOIN T_HOKENJYA HOKENJYA ON KOJIN.HKNJANUM = HOKENJYA.HKNJANUM ");
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
//		// eidt s.inoue 2011/04/12
//		sb.append(" ORDER BY GET_NENDO.NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC ");
//
//		return sb;
//	}

	// add s.inoue 2013/02/27
    @Override
	public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }

	/**
	 * 削除ボタンイベント ※gridがREADONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	@Override
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("delete from T_SHIHARAI where SHIHARAI_DAIKO_NO=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JOutputGetujiSearchListFrameData vo = (JOutputGetujiSearchListFrameData)persistentObjects.get(i);
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

	// edit n.ohkubo 2014/10/01　追加 start　「戻る」ボタンの処理（テーブルの表示項目の「表示 or 非表示」を登録）
	/**
	 * 項目の表示 or 非表示の状態を、DBへ登録する
	 */
	protected void preserveColumnStatus() {
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

			StringBuilder sbwhere_getuji = new StringBuilder();
			
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.JYUSHIN_SEIRI_NO)){
				// 非表示
				sbwhere_getuji.append(" JYUSHIN_SEIRI_NO = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" JYUSHIN_SEIRI_NO = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.NENDO)){
				// 非表示
				sbwhere_getuji.append(" ,NENDO = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,NENDO = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU)){
				// 非表示
				sbwhere_getuji.append(" ,HIHOKENJYASYO_KIGOU = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,HIHOKENJYASYO_KIGOU = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_NO)){
				// 非表示
				sbwhere_getuji.append(" ,HIHOKENJYASYO_NO = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,HIHOKENJYASYO_NO = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KENSA_NENGAPI)){
				// 非表示
				sbwhere_getuji.append(" ,KENSA_NENGAPI = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,KENSA_NENGAPI = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEX)){
				// 非表示
				sbwhere_getuji.append(" ,SEX = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,SEX = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.BIRTHDAY)){
				// 非表示
				sbwhere_getuji.append(" ,BIRTHDAY = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,BIRTHDAY = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KEKA_INPUT_FLG)){
				// 非表示
				sbwhere_getuji.append(" ,KEKA_INPUT_FLG = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,KEKA_INPUT_FLG = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HKNJANUM)){
				// 非表示
				sbwhere_getuji.append(" ,HKNJANUM = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,HKNJANUM = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO)){
				// 非表示
				sbwhere_getuji.append(" ,SIHARAI_DAIKOU_BANGO = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,SIHARAI_DAIKOU_BANGO = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KANANAME)){
				// 非表示
				sbwhere_getuji.append(" ,KANANAME = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,KANANAME = '1'");
			}
			if(JApplication.flag_Getuji.contains(FlagEnum_Getuji.HANTEI_NENGAPI)){
				sbwhere_getuji.append(" ,HANTEI_NENGAPI = '0'");
			}else{
				sbwhere_getuji.append(" ,HANTEI_NENGAPI = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.TUTI_NENGAPI)){
				// 非表示
				sbwhere_getuji.append(" ,TUTI_NENGAPI = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,TUTI_NENGAPI = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.CHECKBOX_COLUMN)){
				// 非表示
				sbwhere_getuji.append(" ,CHECKBOX_COLUMN = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,CHECKBOX_COLUMN = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.TANKA_GOUKEI)){
				// 非表示
				sbwhere_getuji.append(" ,TANKA_GOUKEI = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,TANKA_GOUKEI = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.MADO_FUTAN_GOUKEI)){
				// 非表示
				sbwhere_getuji.append(" ,MADO_FUTAN_GOUKEI = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,MADO_FUTAN_GOUKEI = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEIKYU_KINGAKU)){
				// 非表示
				sbwhere_getuji.append(" ,SEIKYU_KINGAKU = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,SEIKYU_KINGAKU = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.UPDATE_TIMESTAMP)){
				// 非表示
				sbwhere_getuji.append(" ,UPDATE_TIMESTAMP = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,UPDATE_TIMESTAMP = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.JISI_KBN)){
				// 非表示
				sbwhere_getuji.append(" ,JISI_KBN = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,JISI_KBN = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HENKAN_NITIJI)){
				// 非表示
				sbwhere_getuji.append(" ,HENKAN_NITIJI = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,HENKAN_NITIJI = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.METABO)){
				// 非表示
				sbwhere_getuji.append(" ,METABO = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,METABO = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HOKENSIDO_LEVEL)){
				// 非表示
				sbwhere_getuji.append(" ,HOKENSIDO_LEVEL = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,HOKENSIDO_LEVEL = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KOMENTO)){
				// 非表示
				sbwhere_getuji.append(" ,KOMENTO = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,KOMENTO = '1'");
			}
			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.NAME)){
				// 非表示
				sbwhere_getuji.append(" ,NAME = '0'");
			}else{
				// 表示
				sbwhere_getuji.append(" ,NAME = '1'");
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE T_SCREENCOLUMNS SET ");
			sb.append(sbwhere_getuji.toString());
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_GETUJI_CODE));
			
			//SQL実行
			JApplication.kikanDatabase.sendNoResultQuery(sb.toString());

			//コミット
			JApplication.kikanDatabase.getMConnection().commit();
			
		} catch (SQLException e) {
			try {
				JApplication.kikanDatabase.getMConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
		}
	}
	// edit n.ohkubo 2014/10/01　追加 end　「戻る」ボタンの処理（テーブルの表示項目の「表示 or 非表示」を登録）
}
