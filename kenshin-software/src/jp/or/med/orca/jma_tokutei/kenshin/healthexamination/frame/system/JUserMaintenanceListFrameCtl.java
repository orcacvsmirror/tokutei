package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * Ctl���
 * @author s.inoue
 * @version 2.0
 */
public class JUserMaintenanceListFrameCtl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = -6952764477694983014L;	// edit n.ohkubo 2015/03/01�@�ǉ�
	
	private JUserMaintenanceListFrame grid = null;
//	private JUserMaintenanceListFrame frame;	// edit n.ohkubo 2015/03/01�@���g�p�Ȃ̂ō폜
	private Connection conn = null;

	private static Logger logger = Logger.getLogger(JUserMaintenanceListFrameCtl.class);

	public JUserMaintenanceListFrameCtl(Connection conn) {
		this.conn = conn;
		grid = new JUserMaintenanceListFrame(conn, this);
	}

	public JUserMaintenanceListFrame getGridControl(){
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

//	/**
//	 * INSERT mode�̎��A�ۑ��O�ɌĂ΂�� Callback�֐�(save button�������ꂽ��).
//	 *
//	 * @return <code>true</code> �ۑ�����, <code>false</code> �ۑ����f
//	 */
//	public boolean beforeInsertGrid(GridControl grid) {
////		new JUserMaintenanceDetailFrameCtrl(this.grid,null, null, conn);
////		return false;
//		return true;
//	}

	/**
	 * grid��double clicked���ꂽ���ACallback�֐�
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	@Override
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
//		JUserMaintenanceListFrameData vo = (JUserMaintenanceListFrameData)persistentObject;
//		new JUserMaintenanceDetailFrameCtrl(grid, vo.getUserName(), conn);
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
	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {

		try {
			ArrayList vals = new ArrayList();
			Map attribute2dbField = new HashMap();
			attribute2dbField.put("USER_NAME","USER_NAME");
			attribute2dbField.put("PASS","PASS");
			attribute2dbField.put("KENGEN","KENGEN");

			GridParams gridParams = new GridParams(action, startIndex,
					filteredColumns, currentSortedColumns,
					currentSortedVersusColumns, otherGridParams);

// del s.inoue 2013/02/28
//			// add s.inoue 2013/02/25
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT USER_NAME,PASS,KENGEN");
			sql.append(" FROM T_USER ");

			return org.openswing.swing.server.QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sql.toString(),
							vals,
							attribute2dbField,
							JUserMaintenanceListFrameData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewMasterCount, true);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
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

			StringBuilder sb = new StringBuilder();
			sb.append(" DELETE from T_USER WHERE USER_NAME = ?");
			stmt = conn.prepareStatement(sb.toString());

			for (int i = 0; i < persistentObjects.size(); i++) {
				JUserMaintenanceListFrameData vo = (JUserMaintenanceListFrameData)persistentObjects.get(i);
				stmt.setString(1, vo.getUSER_NAME());
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
	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
	   * @param rowNumbers row indexes related to the new rows to save
	   * @param newValueObjects list of new value objects to save
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  @Override
	public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {

		PreparedStatement stmt = null;

		System.out.println("row" + rowNumbers[0] + "value" + newValueObjects);

		try {
	    	  StringBuffer sb = new StringBuffer();
			  sb.append("INSERT INTO T_USER (USER_NAME, PASS, KENGEN)");
			  sb.append("VALUES (?,?,?)");

			  stmt = conn.prepareStatement(sb.toString());

		      // �s���ǉ�����
		      JUserMaintenanceListFrameData vo = null;

		      for(int i=0;i<newValueObjects.size();i++) {
		        vo = (JUserMaintenanceListFrameData)newValueObjects.get(i);
		        stmt.setString(1,vo.getUSER_NAME());
		        stmt.setString(2,vo.getPASS());
		        stmt.setString(3,vo.getKENGEN());
		        stmt.execute();
		      }
      return new VOListResponse(newValueObjects,false,newValueObjects.size());
		}catch (SQLException ex) {
			logger.error(ex.getMessage());
	      return new ErrorResponse(JErrorMessage.getMessageValue("M7300"));
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

	    RETURN_VALUE retValue = JErrorMessage.show("M4814", getGridControl());
		if (retValue == RETURN_VALUE.NO)
			return new ErrorResponse(JErrorMessage.getMessageValue("M4815"));

	    try {

	    	StringBuilder sb = new StringBuilder();
	    	sb = new StringBuilder();
	    	sb.append("UPDATE T_USER SET USER_NAME = ?,PASS = ?,KENGEN = ? ");
	    	sb.append(" WHERE USER_NAME =  ? ");

	    	stmt = conn.prepareStatement(sb.toString());

	    	JUserMaintenanceListFrameData vo = null;
	    	JUserMaintenanceListFrameData voOld = null;

	      for(int i=0;i<persistentObjects.size();i++) {
	        vo = (JUserMaintenanceListFrameData)persistentObjects.get(i);
	        voOld = (JUserMaintenanceListFrameData)oldPersistentObjects.get(i);

	        if (vo == null || voOld == null)
	        	break;

	        stmt.setString(1,vo.getUSER_NAME());
	        stmt.setString(2,vo.getPASS());
	        stmt.setString(3,vo.getKENGEN());
	        stmt.setString(4,voOld.getUSER_NAME());
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

	// edit n.ohkubo 2015/03/01�@�ǉ��@start
	/**
	 * �폜�{�^���̏����O�ɌĂ΂�鏈��
	 */
	@Override
	public boolean beforeDeleteGrid(GridControl grid) {
		
		//�I������Ă��郆�[�UID
		String selectUserID = (String)grid.getTable().getGrid().getModel().getValueAt(grid.getTable().getSelectedRow(), 0);
//		System.out.println("�I������Ă��郆�[�UID:[" + selectUserID + "] ���O�C�����[�UID:[" + JApplication.userID + "]");
		
		//�����i���O�C�����[�U�j�͍폜�s��
		if (JApplication.userID.equals(selectUserID)) {
			JErrorMessage.show("M4816", this.grid);
			return false;
		} else {
			return super.beforeDeleteGrid(grid);
		}
	}
	// edit n.ohkubo 2015/03/01�@�ǉ��@end
}
