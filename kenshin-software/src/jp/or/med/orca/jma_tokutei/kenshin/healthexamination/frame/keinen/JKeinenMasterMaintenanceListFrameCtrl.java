package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.keinen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
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
 * �ꗗCtl���
 * @author s.inoue
 * @version 2.0
 */
public class JKeinenMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = -8442816488600129926L;	// edit n.ohkubo 2014/10/01�@�ǉ�
	
	private JKeinenMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
//	private JKeinenMasterMaintenanceListFrame frame;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	// add s.inoue 2012/11/21
	private boolean firstViewFlg =true;

    private static org.apache.log4j.Logger logger
    	= Logger.getLogger(JKeinenMasterMaintenanceListFrameCtrl.class);

	public JKeinenMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JKeinenMasterMaintenanceListFrame(conn, this);
	}

	public JKeinenMasterMaintenanceListFrame getGridControl(){
		return grid;
	}
	/**
	 * INSERT mode�̎��A�ۑ��O�ɌĂ΂�� Callback�֐�(save button�������ꂽ��).
	 *
	 * @return <code>true</code> �ۑ�����, <code>false</code> �ۑ����f
	 */
	@Override
	public boolean beforeInsertGrid(GridControl grid) {
		// new JKeinenMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
	}

	/**
	 * grid��double clicked���ꂽ���ACallback�֐�
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	@Override
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
		// JKeinenMasterMaintenanceListData vo = (JKeinenMasterMaintenanceListData)persistentObject;
		// new JKeinenMasterMaintenanceDetailCtl(grid, vo.getUKETUKE_ID(), conn);
	}

	/**
    * Callback���\�b�h
    * export�_�C�A���O��\������O�ɌĂяo��
    * grid�ɒ�`���ꂽformat���Ē�`���ČĂяo���܂�
    * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
    */
	@Override
	public String[] getExportingFormats() {
		return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.CSV_FORMAT1,ExportOptions.CSV_FORMAT2,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
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
	
	// edit n.ohkubo 2014/10/01�@start�@�\�[�g�ݒ�̕s���0�����b�Z�[�W���̏C�����s���̂ŁA���\�b�h��V�K�ō쐬�i�����̃��W�b�N�͑S�R�����g�j
//	/**
//	 * grid�̃f�[�^�����[�h���ꂽ����Callback�֐�
//	 * @param PREVIOUS_BLOCK_ACTION:�O�s�ֈړ�, NEXT_BLOCK_ACTION:���s�ֈړ� or LAST_BLOCK_ACTION:�ŏI�s�ֈړ�
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
//		try {
//			ArrayList vals = new ArrayList();
//		    Map attribute2dbField = new HashMap();
//			attribute2dbField.put("NAYOSE_NO","NAYOSE_NO");
//			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
//			attribute2dbField.put("NAME","NAME");
//			attribute2dbField.put("KANANAME","KANANAME");
//			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
//			attribute2dbField.put("SEX","SEX");
//
//			attribute2dbField.put("HOME_ADRS","HOME_ADRS");
//			attribute2dbField.put("HIHOKENJYASYO_KIGOU","HIHOKENJYASYO_KIGOU");
//			attribute2dbField.put("HIHOKENJYASYO_NO","HIHOKENJYASYO_NO");
//			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
//			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//
//			// �����l ���� �����܂������̐ݒ�
//		    if (filteredColumns.values().iterator().hasNext()){
//				Iterator it_dt = filteredColumns.values().iterator();
//			    FilterWhereClause[] filterClauses = null;
//
//			    while(it_dt.hasNext()) {
//			      filterClauses = (FilterWhereClause[])it_dt.next();
//			      // comment
////			      System.out.println(filterClauses[0].getAttributeName());
////			      System.out.println(filterClauses[0].getValue());
////			      System.out.println(filterClauses[0].getOperator());
//
//			      if (filterClauses[0].getOperator().equals("like")){
//						// add s.inoue 2014/03/18
//				    	String filterval = filterClauses[0].getValue().toString();
//				    	if(!filterval.startsWith("%"))
//				    	  filterval = "%"+filterval+"%";
//				    	  filterClauses[0].setValue(filterval);
//
//						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
//								new FilterWhereClause[] { filterClauses[0], null });
//			      }
//			    }
//		    }else{
////				FilterWhereClause clauseDesign = new FilterWhereClause();
////				clauseDesign.setAttributeName("NENDO");
////				clauseDesign.setOperator("=");
////				clauseDesign.setValue(2012);
////
////				gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
////						new FilterWhereClause[] { clauseDesign, null });
//		    }
//
//			// add s.inoue 2013/02/25
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}
//
//			// add s.inoue 2013/02/25
//			conn = grid.getConnection();
//
//			// ���񂹃e�[�u���ǂݍ���
//			// String sql = "SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL FROM T_SHIHARAI";
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT TN.NAYOSE_NO NAYOSE_NO, TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO, TK.NAME NAME, TK.KANANAME KANANAME,");
//			sb.append(" TK.BIRTHDAY BIRTHDAY, TK.HOME_ADRS HOME_ADRS,TK.HIHOKENJYASYO_KIGOU HIHOKENJYASYO_KIGOU,");
//			sb.append(" TK.HIHOKENJYASYO_NO HIHOKENJYASYO_NO, TN.UPDATE_TIMESTAMP UPDATE_TIMESTAMP, TN.UKETUKE_ID UKETUKE_ID, ");
//			sb.append(" case TK.SEX when 1 then '�j' when 2 then '��' end as SEX");
//			sb.append(" FROM T_NAYOSE TN ");
//			sb.append(" LEFT JOIN T_KOJIN TK ON TN.UKETUKE_ID = TK.UKETUKE_ID ");
//
//	    	// eidt s.inoue 2012/11/16
//	    	if (firstViewFlg)
//			sb.append(" ORDER BY NAYOSE_NO,UPDATE_TIMESTAMP ");
//			// openswing s.inoue 2010/12/17
////			sb.append("SELECT NAYOSE_NO ");
//			// , UKETUKE_ID, UPDATE_TIMESTAMP");
////			sb.append(" FROM T_NAYOSE ");
//
//	    	// add s.inoue 2012/11/16
//			firstViewFlg = false;
//
//			return QueryUtil
//					.getQuery(
//					conn,
//					new UserSessionParameters(),
//					sb.toString(),
//					vals,
//					attribute2dbField,
//					JKeinenMasterMaintenanceListData.class,
//					"Y", "N",
//					null, gridParams, JApplication.gridViewMasterCount, true);
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return new ErrorResponse(ex.getMessage());
//		}
//	}
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
	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns, ArrayList currentSortedColumns, ArrayList currentSortedVersusColumns, Class valueObjectType, Map otherGridParams) {
		
		try {
		    Map<String, String> attribute2dbField = new HashMap<String, String>();
			attribute2dbField.put("NAYOSE_NO","NAYOSE_NO");
			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
			attribute2dbField.put("NAME","NAME");
			attribute2dbField.put("KANANAME","KANANAME");
			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
			attribute2dbField.put("SEX","SEX");
			attribute2dbField.put("HOME_ADRS","HOME_ADRS");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU","HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO","HIHOKENJYASYO_NO");
			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);

			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			
			//�����E�\�[�g�����̐ݒ�p���ʃN���X
			FilterSetting filterSetting = new FilterSetting();
			
			//�u���܂ށv�����́ulike %���͒l%�v�Ō������邽�߁A���͒l��"%"��t������
			filterSetting.setLikeColumns(filteredColumns, true);

			//���񂹃e�[�u���ǂݍ���
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT TN.NAYOSE_NO NAYOSE_NO, TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO, TK.NAME NAME, TK.KANANAME KANANAME,");
			sb.append(" TK.BIRTHDAY BIRTHDAY, TK.HOME_ADRS HOME_ADRS,TK.HIHOKENJYASYO_KIGOU HIHOKENJYASYO_KIGOU,");
			sb.append(" TK.HIHOKENJYASYO_NO HIHOKENJYASYO_NO, TN.UPDATE_TIMESTAMP UPDATE_TIMESTAMP, TN.UKETUKE_ID UKETUKE_ID, ");
			sb.append(" TK.SEX as SEX");
			sb.append(" FROM T_NAYOSE TN ");
			sb.append(" LEFT JOIN T_KOJIN TK ON TN.UKETUKE_ID = TK.UKETUKE_ID ");
	    	if (firstViewFlg) {
	    		sb.append(" ORDER BY NAYOSE_NO,UPDATE_TIMESTAMP ");
	    	}
	    	
	    	//�f�[�^�̎擾���s
	    	conn = grid.getConnection();
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sb.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JKeinenMasterMaintenanceListData.class,
											"Y",
											"N",
											null,
											gridParams,
											JApplication.gridViewMasterCount,
											true
			);

			//�u���܂ށv�����́ulike %���͒l%�v�Ō������邽�߁A���͒l��"%"��t�����Ă���̂ŁA�t������"%"���폜����
			filterSetting.setLikeColumns(filteredColumns, false);

			//������ʂփ\�[�g�����𔽉f����i������A������ʂ���čēx�J�����Ƃ��ɁA�l�����f����Ă��Ȃ����ۂ̑Ή��j
			ColumnContainer columnContainer = grid.getColumnContainer();
			filterSetting.setSortColumnsAfter(columnContainer, currentSortedColumns, currentSortedVersusColumns);
			
			//0���̏ꍇ�A���b�Z�[�W�̕\���i��ʃI�[�v������ȊO�j
			if (!firstViewFlg) {
				if ((result != null) && (!result.isError() && (result instanceof VOListResponse))){
					if (((VOListResponse)result).getRows().size() == 0) {
//						JErrorMessage.show("M3550", getGridControl());	// edit n.ohkubo 2015/03/01�@�폜
						JErrorMessage.show("M9804", getGridControl());	// edit n.ohkubo 2015/03/01�@�ǉ��@���b�Z�[�W�̕ύX
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
	// edit n.ohkubo 2014/10/01�@end�@�\�[�g�ݒ�̕s���0�����b�Z�[�W���̏C�����s���̂ŁA���\�b�h��V�K�ō쐬�i�����̃��W�b�N�͑S�R�����g�j

	 /**
	   * Method invoked when the user has clicked on save button and the grid is in EDIT mode.
	   * @param rowNumbers row indexes related to the changed rows
	   * @param oldPersistentObjects old value objects, previous the changes
	   * @param persistentObjects value objects relatied to the changed rows
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  @Override
	public Response updateRecords(int[] rowNumbers,ArrayList oldPersistentObjects,ArrayList persistentObjects) throws Exception {
	    PreparedStatement stmt = null;

	    // add s.inoue 2012/05/08
	    RETURN_VALUE retValue = JErrorMessage.show("M9802", getGridControl());
		if (retValue == RETURN_VALUE.NO)
			return new ErrorResponse(JErrorMessage.getMessageValue("M9803"));

	    try {

	    	StringBuilder sb = new StringBuilder();
	    	sb = new StringBuilder();
	    	sb.append("UPDATE T_NAYOSE SET NAYOSE_NO = ? ");
	    	sb.append("WHERE UKETUKE_ID =  ? ");

	    	stmt = conn.prepareStatement(sb.toString());

	      JKeinenMasterMaintenanceListData vo = null;
	      for(int i=0;i<persistentObjects.size();i++) {
	        vo = (JKeinenMasterMaintenanceListData)persistentObjects.get(i);
	        if (vo == null)
	        	break;
	        // ���ڏ�
	        stmt.setString(1,vo.getNAYOSE_NO());
	        stmt.setString(2,vo.getUKETUKE_ID());

	        stmt.execute();
	      }
	      return new VOListResponse(persistentObjects,false,persistentObjects.size());
	    }
	    catch (SQLException ex) {
	      ex.printStackTrace();
	      return new ErrorResponse(ex.getMessage());
	    }
	    finally {
	      try {
	        stmt.close();
	        conn.commit();
	      }
	      catch (SQLException ex1) {
	      }
	    }
	  }

	// add s.inoue 2013/02/27
    @Override
	public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }
	/**
	 * �폜�{�^���C�x���g ��grid��READONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	@Override
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("delete from T_NAYOSE WHERE UKETUKE_ID=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JKeinenMasterMaintenanceListData vo = (JKeinenMasterMaintenanceListData)persistentObjects.get(i);
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
}
