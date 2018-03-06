package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * Ctl���
 * @author s.inoue
 * @version 2.0
 */
public class JKikanLogListFrameCtl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = -2988778262514672086L;	// edit n.ohkubo 2014/10/01�@�ǉ�
	
	private JKikanLogListFrame grid = null;
//	private JKikanLogListFrame frame;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
//	private Connection conn = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

//	private static Vector<Vector<String>> CSVItems = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

	private static Logger logger = Logger.getLogger(JKikanLogListFrameCtl.class);
	
	private boolean firstViewFlg = true;	// edit n.ohkubo 2014/10/01�@�ǉ�

	public JKikanLogListFrameCtl() {
		// this.conn = conn;
		grid = new JKikanLogListFrame(this);
	}

	public JKikanLogListFrame getGridControl(){
		return grid;
	}

//	/**
//	* @param attributeName attribute name that identify a grid column
//	* @return tooltip text to show in the column header; this text will be automatically translated according to internationalization settings
//	*/
//	public String getHeaderTooltip(String attributeName) {
//	    return attributeName;
//	}

//    /**
//	* @param row row index in the grid
//	* @param attributeName attribute name that identify a grid column
//	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
//	*/
//	public String getCellTooltip(int row,String attributeName) {
//
//		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
////		return attributeName+" at row "+row;
//	}

//	/**
//	 * INSERT mode�̎��A�ۑ��O�ɌĂ΂�� Callback�֐�(save button�������ꂽ��).
//	 *
//	 * @return <code>true</code> �ۑ�����, <code>false</code> �ۑ����f
//	 */
//	public boolean beforeInsertGrid(GridControl grid) {
//		// new JShokenMasterMaintenanceDetailCtl(this.grid,null, null, conn);
//		return false;
//	}

//	/**
//	 * grid��double clicked���ꂽ���ACallback�֐�
//	 * @param rowNumber selected row index
//	 * @param persistentObject v.o. related to the selected row
//	 */
//	public void doubleClick(int rowNumber, ValueObject persistentObject) {
//		JKikanLogListFrameData vo = (JKikanLogListFrameData)persistentObject;
//
//		// openswing s.inoue 2011/02/09
//		// new JShokenMasterMaintenanceDetailCtl(grid, vo.getSYOKEN_TYPE(),vo.getSYOKEN_NO(), conn);
////		JScene.ChangeScene(grid,new JShokenMasterMaintenanceDetailCtl(
////				vo.getSYOKEN_NO()),new WindowRefreshEvent());
//		JScene.ChangeScene(grid,new JShokenMasterMaintenanceEditFrameCtrl(
//				vo.getSYOKEN_TYPE(), vo.getSYOKEN_NO()),new WindowRefreshEvent());
//	}

    /**
     * Callback���\�b�h
     * export�_�C�A���O��\������O�ɌĂяo��
     * grid�ɒ�`���ꂽformat���Ē�`���ČĂяo���܂�
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 @Override
	public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
	 }

// del s.inoue 2013/03/14
//	 // add s.inoue 2011/04/05
//	 /**
//	   * Callback���\�b�h
//	   * grid����export�����{�����Ƃ��ɌĂяo��
//	   * @param exportOptions options used to export data; these options can be programmatically changed, in order to customize exporting result
//	   */
//	 public void exportGrid(ExportOptions exportOptions) {
//		 GridExportOptions empsOpts = (GridExportOptions)exportOptions.getComponentsExportOptions().get(1);
//
//		 final GridExportOptions wdOpts = frame.getGrid().getDefaultGridExportOptions();
//		 GridExportCallbacks callbacks = new GridExportCallbacks() {
//		     /**
//		     * @param vo value object related to current exported row
//		     * @param row row index just exported
//		     * @return method invoked after adding a row to export document; if not null, then specified ComponentExportOptions will be added after current row
//		     */
//		    public ComponentExportOptions getComponentPerRow(ValueObject vo,int row) {
//		    	JShokenMasterMaintenanceListData empVO = (JShokenMasterMaintenanceListData)vo;
//		        wdOpts.getOtherGridParams().put("empCode",empVO.getSYOKEN_NO());
//		        return wdOpts;
//		    }
//
//	    };
//	    empsOpts.setCallbacks(callbacks);
//	 }

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
//			ArrayList vals = new ArrayList();
//			Map attribute2dbField = new HashMap();
//			attribute2dbField.put("SYOKEN_TYPE","SYOKEN_TYPE");
//			attribute2dbField.put("SYOKEN_TYPE_NAME","SYOKEN_TYPE_NAME");
//			attribute2dbField.put("SYOKEN_NO","SYOKEN_NO");
//			attribute2dbField.put("SYOKEN_NAME","SYOKEN_NAME");
//			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
//
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//
////			// add s.inoue 2013/02/25
////			try {
////				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
////			} catch (SQLException ex) {
////				logger.warn(ex.getMessage());
////			}
//
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME,");
//			sql.append(" SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP");
//			sql.append(" FROM T_SYOKENMASTER ");
//			// add s.inoue 2012/05/07
//			if (currentSortedVersusColumns.size() == 0)
//				sql.append(" ORDER BY SYOKEN_TYPE,SYOKEN_NO ");
//
//			return QueryUtil
//					.getQuery(
//							conn,
//							new UserSessionParameters(),
//							sql.toString(),
//							vals,
//							attribute2dbField,
//							JKikanLogListFrameData.class,
//							"Y", "N",
//							null, gridParams, JApplication.gridViewMasterCount, true);


			// add s.inoue 2013/11/08
			// CSV�Ǎ�����
			reader = new JCSVReaderStream();
			// edit s.inoue 2014/05/20
			// String filePath = "C:\\NITTOKU\\Log\\JKenshinSoftware.log";
			String filePath = JPath.CURRENT_DIR_PATH + File.separator+"Log"+ File.separator+ "JKenshinSoftware.log";

//			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET,' ');
//			} catch (IOException e) {
//				JErrorMessage.show("M9913",this);
//				logger.error(e.getMessage());
//			}

				// edit n.ohkubo 2014/10/01�@start�@�i���݂ƃ\�[�g�������s���Ă��Ȃ��̂ŁA�R�����g�A�E�g���ĐV�����L�� 
//			CSVItems = reader.readAllTable();

//			ArrayList vos = new ArrayList();
//
//			// �������ʃf�[�^�捞����
//			for (int i = 1; i < CSVItems.size(); i++) {
//
//				Vector<String> insertRow = new Vector<String>();
//
//				insertRow =CSVItems.get(i);
//
//				JKikanLogListFrameData vo = new JKikanLogListFrameData();
//				System.out.println(insertRow.size());
//				if (insertRow.size()!=7)continue;
//				
//				// vo.setLOG_DATE(insertRow.get(0));
//				vo.setUPDATE_TIMESTAMP(insertRow.get(0) +" "+ insertRow.get(1));
//				vo.setLOG_MESSAGE_LEVEL(insertRow.get(3));
//				vo.setLOG_ERROR_PLACE(insertRow.get(4));
//
//				String var6 ="";
//				if (insertRow.get(6).indexOf("?s")>0)
//					var6= insertRow.get(6).substring(insertRow.get(6).indexOf("?s")+2);
//				if (insertRow.get(6).indexOf("jp.or.med.orca.jma_tokutei")>0)continue;
//				
//				vo.setLOG_MESSAGE(var6);
//				vos.add(vo);
//
//			}
//
//	        return new VOListResponse(vos,false,vos.size());
				
			//�߂�l�p���X�g
			List<JKikanLogListFrameData> vos = new ArrayList<JKikanLogListFrameData>();

			//�t�B���^�[���ݒ肳��Ă��邩�̔���i���[�v�̊O�Ŏ擾�j
			boolean isFilterSetting = (filteredColumns.size() != 0) ? true : false;
			
			//���O�t�@�C����ǂݍ��ށi�����͕s�v�Ȃ̂œK���ȕ������w��
			reader.openCSV(filePath, JApplication.CSV_CHARSET, '\n');
			
			//���O�t�@�C���̑S�s���擾
			Vector<Vector<String>> vecLines = reader.readAllTable();
			
			//���O�t�@�C���̓��e�𐸍�
			for (int i = 0; i < vecLines.size(); i++) {
				
				//1�s���̕\���f�[�^�i�[�p
				JKikanLogListFrameData bean = new JKikanLogListFrameData();
				
				//���O�t�@�C����1�s�i�[�p
				String line = vecLines.get(i).get(0);

				//30���������͒ʏ�̃��O�ł͂Ȃ��̂ŃX�L�b�v
				if (line.length() < 30) {
					continue;
				}
				
				//" - "��30��������ɖ����ꍇ���ʏ�̃��O�ł͂Ȃ��̂ŃX�L�b�v
				int index = line.indexOf(" - ");
				if (index < 30) {
					continue;
				}
				
				//�X�V����
				String timeStamp = line.substring(0, 23);
				bean.setUPDATE_TIMESTAMP(timeStamp);
				bean.setUPDATE_TIMESTAMP2(timeStamp);
				
				//���b�Z�[�W���
				String logLevelCode = line.substring(23, 30).trim();
				bean.setLOG_MESSAGE_LEVEL(logLevelCode);
				
				//���
				String placeCode = line.substring(30, line.indexOf(" - "));
				bean.setLOG_ERROR_PLACE(placeCode);
				
				//���e
				String msg = line.substring((line.indexOf("?s") + 2), line.length());
				bean.setLOG_MESSAGE(msg);
				
				//�i���݂̔���
				if (isFilterSetting && !isFilter(filteredColumns, bean)) {
					continue;
				}
				vos.add(bean);
			}
			
			//�\�[�g
			int currentSortedColumnsCount = currentSortedColumns.size();
			int currentSortedVersusColumnsCount = currentSortedVersusColumns.size();
			if ((currentSortedColumnsCount > 0) && (currentSortedColumnsCount == currentSortedVersusColumnsCount)) {
				Collections.sort(vos, new LogListSort(currentSortedColumns, currentSortedVersusColumns));
			}
			
			//�����E�\�[�g�����̐ݒ�p���ʃN���X
			FilterSetting filterSetting = new FilterSetting();
			
			//������ʂփ\�[�g�����𔽉f����i������A������ʂ���čēx�J�����Ƃ��ɁA�l�����f����Ă��Ȃ����ۂ̑Ή��j
			ColumnContainer columnContainer = grid.getColumnContainer();
			filterSetting.setSortColumnsAfter(columnContainer, currentSortedColumns, currentSortedVersusColumns);
			
			Response result = new VOListResponse(vos, false, vos.size());
			
			//0���̏ꍇ�A���b�Z�[�W�̕\���i��ʃI�[�v������ȊO�j
			if (!firstViewFlg) {
				if ((result != null) && (!result.isError() && (result instanceof VOListResponse))){
					if (((VOListResponse)result).getRows().size() == 0) {
						JErrorMessage.show("M3550", getGridControl());
					}
				}
			}

			firstViewFlg = false;			

			return result;
			// edit n.ohkubo 2014/10/01�@�ǉ��@end
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}


	// edit n.ohkubo 2014/10/01�@�ǉ��@start
	/**
	 * �i���ݔ���
	 * 
	 * @param filteredColumns
	 * @param bean
	 * @return	�i���ݏ����Ɉ�v�i�\���Ώہj�̏ꍇ�Ftrue�A�i���ݏ����ɕs��v�i�\���ΏۊO�j�̏ꍇ�Ffalse
	 */
	private boolean isFilter(Map<String, FilterWhereClause[]> filteredColumns, JKikanLogListFrameData bean) {
		
		//�߂�l
		boolean isResult = true;
		
		//�w�肳��Ă���͉̂������ׂ�
		Set<Entry<String, FilterWhereClause[]>> set = filteredColumns.entrySet();
		Iterator<Entry<String, FilterWhereClause[]>> iterator = set.iterator();
		
		//�w�肳��Ă���S���𒲍�
		while(iterator.hasNext()) {
			Entry<String, FilterWhereClause[]> entry = iterator.next();
			FilterWhereClause[] values = entry.getValue();
			
			//�w�肳��Ă��鍀��
			String key = entry.getKey();
			
			//�w�肳�ꂽ�i���ݏ���
			String operator = values[0].getOperator();
			
			//���͂��ꂽ�l
			String inputValue;
			if (values[0].getValue() != null) {
				inputValue = values[0].getValue().toString();
			} else {
				inputValue = "";
			}
			
			String getValue = "";
			
			//���
			if ("LOG_ERROR_PLACE".equals(key)) {
//					System.out.println("LOG_ERROR_PLACE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
				
				getValue = bean.getLOG_ERROR_PLACE();
				
			//���b�Z�[�W���
			} else if ("LOG_MESSAGE_LEVEL".equals(key)) {
//					System.out.println("LOG_MESSAGE_LEVEL Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
				
				getValue = bean.getLOG_MESSAGE_LEVEL();
				
			//���e
			} else if ("LOG_MESSAGE".equals(key)) {
//					System.out.println("LOG_MESSAGE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
				
				getValue = bean.getLOG_MESSAGE();
				
			//�X�V����
			} else if ("UPDATE_TIMESTAMP".equals(key)) {
//				System.out.println("UPDATE_TIMESTAMP Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
			
				getValue = bean.getUPDATE_TIMESTAMP();

				//�͈͎w��Ō��������Ƃ��̑Ή�
				if (inputValue.length() > 0) {
					inputValue = convertTimeStamp(inputValue, operator);
				}
				
			//�X�V����2
			} else if ("UPDATE_TIMESTAMP2".equals(key)) {
//				System.out.println("UPDATE_TIMESTAMP2 Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
			
				getValue = bean.getUPDATE_TIMESTAMP2();
				
				//�͈͎w��Ō��������Ƃ��̑Ή�
				if (inputValue.length() > 0) {
					inputValue = convertTimeStamp(inputValue, operator);
				}
			
			} else {
//				throw new RuntimeException("�z��O�̍���:[" + key + "]");
				logger.warn("�z��O�̍���:[" + key + "]");
				continue;
			}

			boolean isOK = isTarget(operator, inputValue, getValue);
			if (!isOK) {
				isResult = false;
				break;
			}
		}
			
		return isResult;
	}
	
	/**
	 * �X�V�����̃~�j�}���ƃ}�b�N�X��ݒ肷��i�͈͎w��Ō��������ꍇ��z��j
	 * ��j
	 * ���͒l���u2014�v�A�������u�ȏ�v�̏ꍇ�A�u2014-01-01 00:00:00,000�v�ɂȂ�
	 * ���͒l���u2013-02�v�A�������u�����v�̏ꍇ�A�u2013-02-31 23:59:59,999�v�ɂȂ�
	 * �i31���܂Ŗ����������邪�A�������r�Ȃ̂Ŗ�薳���j
	 * 
	 * @param inputValue	���͒l
	 * @param operator		����
	 * @return
	 */
	private String convertTimeStamp(String inputValue, String operator) {
		
		String result;
		final String over = "0000-01-01 00:00:00,000";
		final String under = "9999-12-31 23:59:59,999";
		
		//�ȏ� or ���߂̏ꍇ
		if (">=".equals(operator) || ">".equals(operator)) {
			
			result = inputValue + over.substring(inputValue.length(), over.length());
			
		//�ȉ� or ����
		} else if ("<=".equals(operator) || "<".equals(operator)) {
			
			result = inputValue + under.substring(inputValue.length(), under.length());
			
		} else {
			result = inputValue;
		}
		
		return result;
	}
	
	/**
	 * �t�B���^�[�����Ƀ}�b�`���邩�̔���
	 * 
	 * @param operator
	 * @param inputValue
	 * @param getValue
	 * 
	 * @return �t�B���^�[�����Ɉ�v�i�\���Ώہj�̏ꍇ�Ftrue�A�t�B���^�[�����ɕs��v�i�\���ΏۊO�j�̏ꍇ�Ffalse
	 */
	private boolean isTarget(String operator, String inputValue, String getValue) {
		
		if (operator == null || inputValue == null || getValue == null) {
			return false;
		}

		//�߂�l
		boolean isResult = false;
		
		//������
		if ("=".equals(operator)) {
			isResult = inputValue.equals(getValue) ? true : false;
			
		//�������Ȃ�
		} else if ("<>".equals(operator)) {
			isResult = inputValue.equals(getValue) ? false : true;
			
		//���܂�
		} else if ("like".equals(operator)) {
			isResult = (getValue.indexOf(inputValue) < 0) ? false : true;
			
		//��łȂ�
		} else if ("is not null".equals(operator)) {
//			isResult = (("".equals(getValue.trim()) ? false : true) || (((Domain)JApplication.domains.get("LOG_PLACE")).getDomainPair(getValue) != null));
			isResult = "".equals(getValue.trim()) ? false : true;
			
		//���
		} else if (("is null".equals(operator))){
//			isResult = (("".equals(getValue.trim()) ? true : false) || (((Domain)JApplication.domains.get("LOG_PLACE")).getDomainPair(getValue) == null));
			isResult = "".equals(getValue.trim()) ? true : false;
			
		//�ȏ�
		} else if (">=".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) >= 0) ? true : false;
			
		//����
		} else if (">".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) > 0) ? true : false;
			
		//�ȉ�
		} else if ("<=".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) <= 0) ? true : false;
			
		//����
		} else if ("<".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) < 0) ? true : false;
		}
		
		return isResult;
	}
	// edit n.ohkubo 2014/10/01�@�ǉ��@end

//	  /**
//	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
//	   * @param rowNumbers row indexes related to the new rows to save
//	   * @param newValueObjects list of new value objects to save
//	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
//	   */
//	  public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {
//
//		PreparedStatement stmt = null;
//
//		System.out.println("row" + rowNumbers[0] + "value" + newValueObjects);
//
//		if (rowNumbers[0] <= 0)
//			return new VOListResponse(newValueObjects,false,newValueObjects.size());
//
//		try {
//	    	  StringBuffer sb = new StringBuffer();
//			  sb.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP)");
//			  sb.append("VALUES (?,?,?,?,?)");
//
//			  stmt = conn.prepareStatement(sb.toString());
//
//		      // �s���ǉ�����
//		      JKikanLogListFrameData vo = null;
//
//		      for(int i=0;i<newValueObjects.size();i++) {
//		        vo = (JKikanLogListFrameData)newValueObjects.get(i);
//		        stmt.setString(1,vo.getSYOKEN_TYPE());
//		        stmt.setString(2,vo.getSYOKEN_TYPE_NAME());
//		        stmt.setString(3,vo.getSYOKEN_NO());
//		        stmt.setString(4,vo.getSYOKEN_NAME());
//		        stmt.setString(5,vo.getUPDATE_TIMESTAMP());
//		        stmt.execute();
//		      }
//        return new VOListResponse(newValueObjects,false,newValueObjects.size());
//		}catch (SQLException ex) {
//			logger.error(ex.getMessage());
//	      return new ErrorResponse(JErrorMessage.getMessageValue("M9912"));
//	    }
//	    finally {
//	      try {
//	        stmt.close();
//	        conn.commit();
//	      }
//	      catch (SQLException ex1) {
//	      }
//	    }
//	  }
//	 /**
//	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
//	   * @param rowNumbers row indexes related to the new rows to save
//	   * @param newValueObjects list of new value objects to save
//	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
//	   */
//	  public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {
//	    try {
//	      Map attribute2dbField = new HashMap();
//	      attribute2dbField.put("SYOKEN_TYPE","SYOKEN_TYPE");
//	      attribute2dbField.put("SYOKEN_TYPE_NAME","SYOKEN_TYPE_NAME");
//	      attribute2dbField.put("SYOKEN_NO","SYOKEN_NO");
//	      attribute2dbField.put("SYOKEN_NAME","SYOKEN_NAME");
//	      attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
//
//	      return QueryUtil.insertTable(
//	        conn,
//	        new UserSessionParameters(),
//	        (ValueObject)newValueObjects.get(0),
//	        "T_SYOKENMASTER",
//	        attribute2dbField,
//	        "Y",
//	        "N",
//	        null,
//	        true
//	      );
//	    }
//	    catch (Exception ex) {
//	      ex.printStackTrace();
//	      return new ErrorResponse(ex.getMessage());
//	    }
//	    finally {
//	      try {
//	        conn.commit();
//	      }
//	      catch (SQLException ex1) {
//	      }
//	    }
//	  }

	// add s.inoue 2013/02/27
    @Override
	public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }

//	/**
//	 * �폜�{�^���C�x���g ��grid��READONLY
//	 * @param persistentObjects value objects to delete (related to the currently selected rows)
//	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
//	 */
//	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
//		PreparedStatement stmt = null;
//
//		try {
//
//			StringBuilder sb = new StringBuilder();
//			sb.append(" delete from T_SYOKENMASTER");
//			sb.append(" WHERE SYOKEN_TYPE = ?");
//			sb.append(" AND SYOKEN_NO = ?");
//			stmt = conn.prepareStatement(sb.toString());
//
//			for (int i = 0; i < persistentObjects.size(); i++) {
//				JKikanLogListFrameData vo = (JKikanLogListFrameData)persistentObjects.get(i);
//				stmt.setString(1, vo.getSYOKEN_TYPE());
//				stmt.setString(2, vo.getSYOKEN_NO());
//				stmt.execute();
//			}
//			return new VOResponse(new Boolean(true));
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//			return new ErrorResponse(ex.getMessage());
//		} finally {
//			try {
//				stmt.close();
//				conn.commit();
//			} catch (SQLException ex1) {
//			}
//		}
//	}

	/**
	 * �J�ڐ�̉�ʂ���߂��Ă����ꍇ
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/04/08
			grid.reloadButton.doClick();
		}
	}

	
	// edit n.ohkubo 2014/10/01�@�ǉ� start�@�\�[�g�p�N���X
	private class LogListSort implements Comparator<JKikanLogListFrameData> {
		
		List<String> sortedColumnsList;
		List<String> sortedVersusList;
		
		public LogListSort(List<String> sortedColumnsList, List<String> sortedVersusList) {
			this.sortedColumnsList = sortedColumnsList;
			this.sortedVersusList = sortedVersusList;
		}

		@Override
		public int compare(JKikanLogListFrameData o1, JKikanLogListFrameData o2) {
			
			//�߂�l
			int result = 0;
			
			//��r�Ώۂ̒l
			String o1Value = "";
			String o2Value = "";
			
			//�w�肳�ꂽ�\�[�g�̌�
			int sortedSize = sortedColumnsList.size();
			
			for (int i = 0; i < sortedSize; i++) {
				
				//�\�[�g�Ώۂ̃J����
				String sortedColumn = sortedColumnsList.get(i);
				
				//�\�[�g�̏���
				String sortedVersus = sortedVersusList.get(i);

				/*�w�肳�ꂽ�\�[�g���ڂ̒l���擾*/
				//���
				if ("LOG_ERROR_PLACE".equals(sortedColumn)) {
//					System.out.println("LOG_ERROR_PLACE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
					
					o1Value = o1.getLOG_ERROR_PLACE();
					o2Value = o2.getLOG_ERROR_PLACE();
					
				//���b�Z�[�W���
				} else if ("LOG_MESSAGE_LEVEL".equals(sortedColumn)) {
//					System.out.println("LOG_MESSAGE_LEVEL Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
					
					o1Value = o1.getLOG_MESSAGE_LEVEL();
					o2Value = o2.getLOG_MESSAGE_LEVEL();
					
				//���e
				} else if ("LOG_MESSAGE".equals(sortedColumn)) {
//					System.out.println("LOG_MESSAGE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
					
					o1Value = o1.getLOG_MESSAGE();
					o2Value = o2.getLOG_MESSAGE();
					
				//�X�V����
				} else if ("UPDATE_TIMESTAMP".equals(sortedColumn)) {
					
					o1Value = o1.getUPDATE_TIMESTAMP();
					o2Value = o2.getUPDATE_TIMESTAMP();
					
				} else {
//					throw new RuntimeException("�z��O�̃\�[�g����:[" + sortedColumn + "]");
				}
				
				/*�w�肳�ꂽ�\�[�g�̏����Ō��ʂ��擾*/
				//����
				if ("ASC".equals(sortedVersus)) {
					result = o1Value.compareTo(o2Value);
					
				//�~��
				} else if ("DESC".equals(sortedVersus)) {
					result = (o1Value.compareTo(o2Value) * -1);
					
				} else {
//					throw new RuntimeException("�z��O�̃\�[�g�̏���:[" + sortedVersus + "]");
				}
				
				//��r���ʂ������@���@���̃\�[�g���ڂ�����ꍇ�A���̍��ڂŃ\�[�g
				if ((result == 0) && (i <= sortedSize)){
					continue;
					
				//�\�[�g���ʂ��ł��̂ŁA���[�v�𔲂���
				} else {
					break;
				}
			}
			return result;
		}
	}
	// edit n.ohkubo 2014/10/01�@�ǉ��@end
}
