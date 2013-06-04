package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja;

import java.util.*;

import org.apache.log4j.Logger;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.message.receive.java.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceEditFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceListData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceListFrameCtrl.WindowRefreshEvent;

import org.openswing.swing.table.java.GridDataLocator;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.export.java.ExportOptions;

/**
 * �ꗗCtl���
 * @author s.inoue
 * @version 2.0
 */
public class JHokenjyaMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator,IHokenjyaMasterMaintenanceDetailFrame {

	private JHokenjyaMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
	private JHokenjyaMasterMaintenanceListFrame frame;
	private static Logger logger = Logger.getLogger(JHokenjyaMasterMaintenanceListFrameCtrl.class);

	public JHokenjyaMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JHokenjyaMasterMaintenanceListFrame(conn, this);
	}

	public JHokenjyaMasterMaintenanceListFrame getGridControl(){
		return grid;
	}
	/**
	 * INSERT mode�̎��A�ۑ��O�ɌĂ΂�� Callback�֐�(save button�������ꂽ��).
	 *
	 * @return <code>true</code> �ۑ�����, <code>false</code> �ۑ����f
	 */
	public boolean beforeInsertGrid(GridControl grid) {
//		new xJHokenjyaMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
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

		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
//		return attributeName+" at row "+row;
	}
	/**
	 * grid��double clicked���ꂽ���ACallback�֐�
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
		JHokenjyaMasterMaintenanceListData vo = (JHokenjyaMasterMaintenanceListData)persistentObject;
		// openswing s.inoue 2011/01/26
		// new xJHokenjyaMasterMaintenanceDetailCtl(grid, vo.getHKNJANUM(), conn);
		// �Ăяo��
		JScene.ChangeScene(grid,new JHokenjyaMasterMaintenanceEditFrameCtrl(
				vo.getHKNJANUM_M()),new WindowRefreshEvent());
//		JScene.CreateDialog(getJFrame(), new JHokenjyaMasterMaintenanceEditFrameCtrl(
//				vo.getHKNJANUM()));
	}

	/**
     * Callback���\�b�h
     * export�_�C�A���O��\������O�ɌĂяo��
     * grid�ɒ�`���ꂽformat���Ē�`���ČĂяo���܂�
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
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
			// HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL
			// eidt s.inoue 2012/10/29
			attribute2dbField.put("HKNJANUM_M","GET_HKNJANUM.hknjanum");
			attribute2dbField.put("HKNJANAME","HKNJANAME");
			attribute2dbField.put("POST","POST");
			attribute2dbField.put("ADRS","ADRS");
			attribute2dbField.put("TEL","TEL");

			GridParams gridParams = new GridParams(action, startIndex,
					filteredColumns, currentSortedColumns,
					currentSortedVersusColumns, otherGridParams);

			// �����l ���� �����܂������̐ݒ�
		    if (filteredColumns.values().iterator().hasNext()){
				Iterator it_dt = filteredColumns.values().iterator();
			    FilterWhereClause[] filterClauses = null;

			    while(it_dt.hasNext()) {
			      filterClauses = (FilterWhereClause[])it_dt.next();
			      // comment
//			      System.out.println(filterClauses[0].getAttributeName());
//			      System.out.println(filterClauses[0].getValue());
//			      System.out.println(filterClauses[0].getOperator());

			      if (filterClauses[0].getOperator().equals("like")){
			    	  filterClauses[0].setValue("%" + filterClauses[0].getValue() + "%");

						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
								new FilterWhereClause[] { filterClauses[0], null });
			      }
			    }
		    }else{
//				FilterWhereClause clauseDesign = new FilterWhereClause();
//				clauseDesign.setAttributeName("NENDO");
//				clauseDesign.setOperator("=");
//				clauseDesign.setValue(2012);
//
//				gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
//						new FilterWhereClause[] { clauseDesign, null });
		    }

			// eidt s.inoue 2011/04/08
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			// eidt s.inoue 2012/10/29
			// String sql = "SELECT DISTINCT(HKNJANUM) HKNJANUMBER, HKNJANAME, POST, ADRS, TEL FROM T_HOKENJYA ORDER BY HKNJANUM DESC";
			String sql = "SELECT DISTINCT GET_HKNJANUM.hknjanum,HKNJANAME, POST, ADRS, TEL FROM T_HOKENJYA LEFT JOIN (select DISTINCT(hknjanum) from T_HOKENJYA) as GET_HKNJANUM ON GET_HKNJANUM.hknjanum = T_HOKENJYA.HKNJANUM";
			return QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sql,
							vals,
							attribute2dbField,
							JHokenjyaMasterMaintenanceListData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewMasterCount, true);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}

	// add s.inoue 2013/02/27
    public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }

	// eidt s.inoue 2011/06/07
	/**
	 * �폜�{�^���C�x���g ��grid��READONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("delete from T_HOKENJYA where HKNJANUM=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JHokenjyaMasterMaintenanceListData vo = (JHokenjyaMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getHKNJANUM_M());
				stmt.execute();
			}

			stmt = conn.prepareStatement("delete from T_KENSHINMASTER where HKNJANUM=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JHokenjyaMasterMaintenanceListData vo = (JHokenjyaMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getHKNJANUM_M());
				stmt.execute();
			}
			// eidt s.inoue 2011/06/07
			grid.reloadButton.doClick();

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

	/* frameInterface */
	@Override
	public GridControl getGrid() {
		return frame.getGrid();
	}

	@Override
	public void jbInit() {
		try {
			frame.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reloadData() {
		frame.reloadData();
	}

	@Override
	public void setControl(Connection conn,
			JHokenjyaMasterMaintenanceListFrameCtrl controller) {
		frame.setControl(conn, controller);

	}

	@Override
	public void setJFrameeDitable() {
		frame.setJFrameeDitable();
	}

	/**
	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/05/12
			// grid.reloadData();
			grid.reloadButton.doClick();
		}
	}

}
