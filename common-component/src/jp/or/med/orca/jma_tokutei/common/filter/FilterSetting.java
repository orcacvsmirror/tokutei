package jp.or.med.orca.jma_tokutei.common.filter;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JCheckBox;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;

import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.table.columns.client.Column;
import org.openswing.swing.util.java.Consts;


/**
 * ���������ƕ��я��̕ۑ��ݒ�𔻒肵�A�K�؂ȏ�����ݒ肷�鋤�ʃN���X
 */
public class FilterSetting {

	/**
	 * �L���t���O�i1�F�L���@0�F�����j
	 */
	private static final String YUKOU_FLG_YUKO = "1";
	private static final String YUKOU_FLG_MUKO = "0";
	
	/**
	 * ���CD
	 */
	private String screenCode;
	
	/**
	 * org.openswing.swing.client.GridControl.ColumnContainer
	 */
	private ColumnContainer columnContainer;
	
	
	/**
	 * �R���X�g���N�^�iDB�g�p���j
	 */
	public FilterSetting(String screenCode, ColumnContainer columnContainer) {
		this.screenCode = screenCode;
		this.columnContainer = columnContainer;
	}
	
	/**
	 * �R���X�g���N�^�iDB���g�p���j
	 */
	public FilterSetting() {
	}
	

	/**
	 * �����E�\�[�g������ݒ肷��
	 * 
	 * @param firstViewFlg
	 * @param jCheckBox
	 * @param gridParams
	 * @param filteredColumns
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 * @param firstFilter	���������̏����l�i[0]��AttributeName�A[1]��Operator���w�肵�A���������������ꍇ�́unull�v���w�肷��j
	 * @throws SQLException
	 */
	public void setFilterSort(
			boolean firstViewFlg, 
			JCheckBox jCheckBox, 
			GridParams gridParams, 
			Map<String, FilterWhereClause[]> filteredColumns, 
			List<String> currentSortedColumns, 
			ArrayList<String> currentSortedVersusColumns,
			String[] firstFilter) throws SQLException {

		//�����E�\�[�g������ێ����邩�̔���p
		boolean isColumnsSave;
		
		//��ʃI�[�v������
		if (firstViewFlg) {
			
			//�}�X�^��������ێ��𔻒�
			isColumnsSave = isColumnsSaveMaster();
			
			//�`�F�b�N�{�b�N�X�֐ݒ�
			jCheckBox.setSelected(isColumnsSave);
			
			//�}�X�^�̒l���u������ێ����Ȃ��v �ior�}�X�^�ɖ��o�^�j�̏ꍇ
			if (!isColumnsSave) {
				
				//���������̏����l��ݒ�
				if (firstFilter != null) {
					
					//�u�N�x�v���u=�v�̏ꍇ
					if (firstFilter.length == 2 && "NENDO".equals(firstFilter[0]) && "=".equals(firstFilter[1])) {
						String cYear = String.valueOf(FiscalYearUtil.getThisYear());
						FilterWhereClause clauseDesign = new FilterWhereClause();
						clauseDesign.setAttributeName(firstFilter[0]);
						clauseDesign.setOperator(firstFilter[1]);
						clauseDesign.setValue(cYear);
						gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(), new FilterWhereClause[]{clauseDesign, null});
					}
				}
			}
			
		//����ȍ~�i��ʃI�[�v����̌������Ȃǂ̎���ʑJ�ڎ��j
		} else {
			
			//�����ۑ��`�F�b�N�{�b�N�X�̐ݒ�
			isColumnsSave = jCheckBox.isSelected();
			registerFilterMaster(isColumnsSave);
			
			//������ێ�����ꍇ
			if (isColumnsSave) {
				
				//�\�[�g�֌W�̏���������
				sortInit(gridParams);
				
				//����������ۑ�
				saveFilter(filteredColumns);
				
				//�\�[�g������ۑ�
				saveSort(currentSortedColumns, currentSortedVersusColumns);
			}
			//�R�~�b�g
			JApplication.kikanDatabase.getMConnection().commit();
		}
		
		//������ێ�����ꍇ
		if (isColumnsSave) {
			
			//���������̐ݒ�
			setFilteredColumns(gridParams);
			
			//�\�[�g�����̐ݒ�
			setSortColumns(gridParams, currentSortedVersusColumns, currentSortedVersusColumns);
		}
		
		//�u���܂ށv�����́ulike %���͒l%�v�ɂȂ�̂ŁA���͒l��"%"��t������
		setLikeColumns(filteredColumns, true);
	}


	/**
	 * �����E�\�[�g������ۑ����邩�̔���
	 * �}�X�^����t���O���擾���A�ݒ肪"�L��"���𔻒肷��
	 * 
	 * @return	�ۑ�����ꍇ�Ftrue�A�ۑ����Ȃ��ꍇ�Ffalse
	 * @throws SQLException 
	 */
	private boolean isColumnsSaveMaster() throws SQLException {
		
		boolean isResult;
		
		//�����E�\�[�g�����}�X�^�̒l���擾���A�����̕ێ����m�F����
		String sql = "SELECT YUKOU_FLG FROM T_SCREEN_FILTER_MASTER WHERE SCREEN_CD = ? AND USER_NAME = ?";
		String[] params = {screenCode, JApplication.userID};
		List<Hashtable<String, String>> result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
		
		//�}�X�^�ɓo�^�L��
		if (result.size() == 1) {
			
			//�擾���ʂ̔���
			Hashtable<String, String> hashtable = result.get(0);
			String yukouFlg = hashtable.get("YUKOU_FLG");
			isResult = YUKOU_FLG_YUKO.equals(yukouFlg) ? true : false;
			
		//�}�X�^�ɓo�^����
		} else {
			isResult = false;
		}

		return isResult;
	}
	
	/**
	 * �uT_SCREEN_FILTER_MASTER�v�e�[�u���ցA�o�^����
	 * 
	 * @param yukou_flg
	 * @throws SQLException
	 */
	private void registerFilterMaster(boolean isYuko) throws SQLException {
		
		//�ŏ��ɍ폜
		JApplication.kikanDatabase.sendNoResultQuery(
				"DELETE FROM T_SCREEN_FILTER_MASTER WHERE SCREEN_CD = ? AND USER_NAME = ?", 
				new String[] {screenCode, JApplication.userID}
		);
		
		//���ɓo�^
		String insSql = "INSERT INTO T_SCREEN_FILTER_MASTER VALUES (?, ?, ?)";
		//�L���t���O��ϊ�
		String yukou_flg = (isYuko) ? YUKOU_FLG_YUKO : YUKOU_FLG_MUKO;
		String[] params = {screenCode, JApplication.userID, yukou_flg};
		JApplication.kikanDatabase.sendNoResultQuery(insSql, params);
	}
	
	/**
	 * �\�[�g�֌W�̏��������s��
	 * �� �F�X�Ə��������Ȃ��ƁA�\�[�g�̕\�������������Ȃ�
	 * 
	 * @param gridParams
	 */
	private void sortInit(GridParams gridParams) {
		
		//���ڂɕ\������\�[�g���ʂ��������i������̏��ʂ����������Ȃ�Ή��j
		gridParams.setCurrentSortedColumns(new ArrayList<String>());
		gridParams.setCurrentSortedVersusColumns(new ArrayList<String>());
		
		//�\�[�g�̗L�����������i������A������ʂ���čēx�J�����Ƃ��ɁA�l�����f����Ă��Ȃ����ۂ̑Ή��j
		Component[] componentArgs = columnContainer.getComponents();
		if (componentArgs != null) {
			for (int i = 0; i < componentArgs.length; i++) {
				
				//���ڂ��\�[�g�Ώۂ��̔���p
				boolean isColumnSortable;
				
				Component component = componentArgs[i];
				if (component instanceof Column) {
					
					//�L���X�g���ʓ|�Ȃ̂ŁA�N���X�֑��
					Column column = (Column)component;
					
					//���ڂ��\�[�g�Ώۂ��`�F�b�N
					isColumnSortable = column.isColumnSortable();
					if (isColumnSortable) {
						
						//"�\�[�g���Ȃ�"�ŏ������i�\�[�g����ꍇ�A���̌��DB����\�[�g�������擾���Đݒ肷��j
						column.setSortVersus(Consts.NO_SORTED);
						column.setSortingOrder(0);
					}
				}
			}
		}
	}
	/**
	 * ���������̓o�^���s��
	 * ����x�폜���Ă���o�^����
	 * 
	 * @param filteredColumns
	 * @throws SQLException 
	 */
	private void saveFilter(Map<String, FilterWhereClause[]> filteredColumns) throws SQLException {
		
		//�폜���Ă���o�^����
		JApplication.kikanDatabase.sendNoResultQuery(
				"DELETE FROM T_SCREEN_FILTER_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ?", 
				new String[] {screenCode, JApplication.userID}
		);
		
		//�o�^�pSQL
		final String sql = "INSERT INTO T_SCREEN_FILTER_COLUMNS VALUES (?, ?, ?, ?, ?)";
		
		//��ʂŎw�肳�ꂽ�����������擾
		Set<Entry<String, FilterWhereClause[]>> set = filteredColumns.entrySet();
		Iterator<Entry<String, FilterWhereClause[]>> iterator = set.iterator();
		
		//�w�肳��Ă���S����o�^
		while(iterator.hasNext()) {
			Entry<String, FilterWhereClause[]> entry = iterator.next();
			
			//�w�肳��Ă��鍀��
			String key = entry.getKey();
			//�I�y���[�V�����ƒl
			FilterWhereClause[] values = entry.getValue();

			for (int i = 0; i < values.length; i++) {
				
				//�ʏ��[0]�ɒl������A[1]��null�ɂȂ�i���N�������������ڂ�2����ꍇ�A[1]�ɓ�ڂ̏����������Ă��邪�A�����attribute2dbField�̃L�[��ʂɂ��Ă���̂ŁA[1]�ɒl�͓���Ȃ��j
				if (values[i] != null) {
					
					//�w�肳�ꂽ�i���ݏ���
					String operator = values[i].getOperator();
					
					//���͂��ꂽ�l
					String inputValue;
					if (values[0].getValue() != null) {
						inputValue = values[i].getValue().toString();
					} else {
						inputValue = "";
					}
					
					//�o�^�̎��s
					String[] params = {screenCode, JApplication.userID, key, operator, inputValue};
					JApplication.kikanDatabase.sendNoResultQuery(sql, params);
					
				}
			}
		}
	}
	
	/**
	 * �\�[�g�����̓o�^���s��
	 * 
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 * @throws SQLException 
	 */
	private void saveSort(List<String> currentSortedColumns, List<String> currentSortedVersusColumns) throws SQLException {
		
		//�폜���Ă���o�^����
		JApplication.kikanDatabase.sendNoResultQuery(
				"DELETE FROM T_SCREEN_SORT_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ?", 
				new String[]{screenCode, JApplication.userID}
		);
		
		//�o�^�pSQL
		final String sql = "INSERT INTO T_SCREEN_SORT_COLUMNS VALUES (?, ?, ?, ?, ?)";

		//�w�肳��Ă���S����o�^
		for (int i = 0; i < currentSortedColumns.size(); i++) {
			
			String[] params = {screenCode, JApplication.userID, String.valueOf(currentSortedColumns.get(i)), String.valueOf(currentSortedVersusColumns.get(i)), String.valueOf((i + 1))};
			JApplication.kikanDatabase.sendNoResultQuery(sql, params);
		}
	}
	
	/**
	 * ���������̎擾�Ɛݒ���s��
	 * 
	 * @param gridParams
	 * @throws SQLException 
	 */
	private void setFilteredColumns(GridParams gridParams) throws SQLException {
		
		//���������̎擾
		String sql = "SELECT FILTER_COLUMN_ITEM, FILTER_COLUMN_OPERATOR, FILTER_COLUMN_VALUE FROM T_SCREEN_FILTER_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ? ORDER BY FILTER_COLUMN_ITEM";
		String[] params = {screenCode, JApplication.userID};
		List<Hashtable<String, String>> filterResult = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
		
		Map<String, FilterWhereClause[]> filterMap = new HashMap<String, FilterWhereClause[]>();
		for (int i = 0; i < filterResult.size(); i++) {
			FilterWhereClause[] filterArgs = new FilterWhereClause[2];

			filterArgs[0] = new FilterWhereClause();
			String filterColumnItem = filterResult.get(i).get("FILTER_COLUMN_ITEM");
			filterArgs[0].setOperator(filterResult.get(i).get("FILTER_COLUMN_OPERATOR"));
			filterArgs[0].setValue(filterResult.get(i).get("FILTER_COLUMN_VALUE"));
			filterArgs[0].setAttributeName(filterColumnItem);
			
			//���Ɏw�肳�ꂽ���ڂ�����@���@���̍��ڂ����̍��ڂƓ����ꍇ�i���������ɓ������ڂ�2�w�肵���ꍇ�j
			if (((i + 1) < filterResult.size()) && filterColumnItem.equals(filterResult.get(i+1).get("FILTER_COLUMN_ITEM"))) {
				filterArgs[1] = new FilterWhereClause();
				filterArgs[1].setOperator(filterResult.get(i+1).get("FILTER_COLUMN_OPERATOR"));
				filterArgs[1].setValue(filterResult.get(i+1).get("FILTER_COLUMN_VALUE"));
				filterArgs[1].setAttributeName(filterResult.get(i+1).get("FILTER_COLUMN_ITEM"));
				i++;
			}
			filterMap.put(filterColumnItem, filterArgs);
			
			//����������ݒ�
			gridParams.getFilteredColumns().put(filterColumnItem, filterArgs);
		}
	}
	
	/**
	 * �\�[�g�����̎擾�Ɛݒ���s��
	 * 
	 * @param gridParams
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 * @throws SQLException 
	 */
	private void setSortColumns(GridParams gridParams, ArrayList<String> currentSortedColumns, ArrayList<String> currentSortedVersusColumns) throws SQLException {
		
			//�\�[�g�����̎擾
			String sql = "SELECT SORT_COLUMN_ITEM, SORT_COLUMN_OPERATOR FROM T_SCREEN_SORT_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ? ORDER BY SORT_COLUMN_SEQNO";
			String[] params = {screenCode, JApplication.userID};
			List<Hashtable<String, String>> sortResult = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
			
			ArrayList<String> sortedColumnsList = new ArrayList<String>();
			ArrayList<String> sortedVersusList = new ArrayList<String>();
			for (int i = 0; i < sortResult.size(); i++) {
				
				String sortColumnItem = sortResult.get(i).get("SORT_COLUMN_ITEM");
				String sortColumnOperator = sortResult.get(i).get("SORT_COLUMN_OPERATOR");
				
				//�\�[�g������ݒ�
				sortedColumnsList.add(sortColumnItem);
				sortedVersusList.add(sortColumnOperator);
				gridParams.getCurrentSortedColumns().add(sortColumnItem);
				gridParams.getCurrentSortedVersusColumns().add(sortColumnOperator);
			}
			currentSortedColumns = sortedColumnsList;
			currentSortedVersusColumns = sortedVersusList;
			gridParams.setCurrentSortedColumns(currentSortedColumns);
			gridParams.setCurrentSortedVersusColumns(currentSortedVersusColumns);
			
			//������ʂփ\�[�g�����𔽉f����
			Component[] componentArgs = columnContainer.getComponents();
			if (componentArgs != null) {
				for (int i = 0; i < componentArgs.length; i++) {
					
					Component component = componentArgs[i];
					if (component instanceof Column) {
						
						//�L���X�g���ʓ|�Ȃ̂ŁA�N���X�֑��
						Column column = (Column)component;
						
						//���ږ��̎擾
						String columnName = column.getColumnName();
						
						//���ڂ��\�[�g�����Ɏg�p����Ă��邩����
						int columnNameIndex = sortedColumnsList.indexOf(columnName);
						if(columnNameIndex > -1) {
							
							//���ڂƃ\�[�g������ݒ�
							column.setSortVersus(sortedVersusList.get(columnNameIndex));
							column.setSortingOrder((columnNameIndex + 1));
						}
					}
				}
			}
	}
	
	/**
	 * like�����̓��͒l���Z�b�g����
	 * �i%�̕t���ƍ폜�j
	 * 
	 * @param filteredColumns
	 * @param isAdd				"%"��t������Ƃ��Ftrue�A"%"���폜����Ƃ��Ffalse
	 */
	public void setLikeColumns(Map<String, FilterWhereClause[]> filteredColumns, boolean isAdd) {
		
		//�����������擾
		Set<Entry<String, FilterWhereClause[]>> set = filteredColumns.entrySet();
		Iterator<Entry<String, FilterWhereClause[]>> iterator = set.iterator();
		
		//�w�肳��Ă���S����o�^
		while(iterator.hasNext()) {
			Entry<String, FilterWhereClause[]> entry = iterator.next();
			
			//�I�y���[�V�����ƒl
			FilterWhereClause[] values = entry.getValue();

			for (int i = 0; i < values.length; i++) {
				if (values[i] != null) {
					if ("like".equals(values[i].getOperator())) {
						
						//�u%�v��t����
						if (isAdd) {
							values[i].setValue("%" + values[i].getValue() + "%");
							
						//�u%�v������
						} else {
							values[i].setValue(((String)values[i].getValue()).replaceAll("%", ""));
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * ������ʂփ\�[�g�����𔽉f����i�u������ێ����Ȃ��v�̏ꍇ�A������A������ʂ���čēx�J�����Ƃ��ɁA�l�����f����Ă��Ȃ����ۂ̑Ή��j
	 * 
	 * @param columnContainer
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 */
	public void setSortColumnsAfter(ColumnContainer columnContainer, ArrayList<String> currentSortedColumns, ArrayList<String> currentSortedVersusColumns) {
		
		Component[] componentArgs = columnContainer.getComponents();
		if (componentArgs != null) {
			for (int i = 0; i < componentArgs.length; i++) {
				
				Component component = componentArgs[i];
				if (component instanceof Column) {
					
					//�L���X�g���ʓ|�Ȃ̂ŁA�N���X�֑��
					Column column = (Column)component;
					
					//���ږ��̎擾
					String columnName = column.getColumnName();
					
					//���ڂ��\�[�g�����Ɏg�p����Ă��邩����
					int columnNameIndex = currentSortedColumns.indexOf(columnName);
					if(columnNameIndex > -1) {
						
						//���ڂƃ\�[�g������ݒ�
						column.setSortVersus((String)currentSortedVersusColumns.get(columnNameIndex));
						column.setSortingOrder((columnNameIndex + 1));
					}
				}
			}
		}
	}
}
