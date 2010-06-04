package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import static java.sql.Types.*;

import jp.or.med.orca.jma_tokutei.common.sql.dao.Dao;
import jp.or.med.orca.jma_tokutei.common.sql.model.RecordModel;

/**
 * �f�[�^�A�N�Z�X�I�u�W�F�N�g
 * �X�[�p�[�N���X
 * @author nishiyama
 *
 */
public abstract class DaoImpl implements Dao {

	private static final String SELECT_NEW_UKETUKE_ID_SQL
	= "SELECT UKETUKE_ID FROM T_KOJIN WHERE HIHOKENJYASYO_KIGOU=? AND HIHOKENJYASYO_NO=?";

	private Connection con;
	
	/**
	 * �R���X�g���N�^
	 * @param con �ڑ��σR�l�N�V����
	 */
	protected DaoImpl(Connection con) {
		this.con = con;
	}

	/**
	 * �R�l�N�V�����擾
	 * @return �ڑ��σR�l�N�V����
	 */
	public Connection getConnection() {
		return con;
	}
	
	/**
	 * T_KOJIN�����tID�擾
	 * @param hihokenjyasyo_kigou ��ی��ҏؓ��L��
	 * @param hihokenjyasyo_no ��ی��ҏؓ��ԍ�
	 * @return ��tID
	 * @throws SQLException
	 */
	public Long selectNewUketukeId(String hihokenjyasyo_kigou,
			String hihokenjyasyo_no) throws SQLException {
		
		Connection con = getConnection();
		if (con == null) return null;
		
		PreparedStatement stmt = con.prepareStatement(SELECT_NEW_UKETUKE_ID_SQL);
		stmt.setString(1, hihokenjyasyo_kigou);
		stmt.setString(2, hihokenjyasyo_no);
		ResultSet rs = stmt.executeQuery();
		Long uketukeId = null;
		if (rs.next()) {
			uketukeId = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return uketukeId;
	}

	/**
	 * ���R�[�h���f���̊e�t�B�[���h�Ƀf�[�^���Z�b�g����
	 * @param rs ResultSet
	 * @param metaData ResultSetMetaData
	 * @return �f�[�^���Z�b�g����TKojin���R�[�h���f��
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected <T extends RecordModel> T setValueForRecordModel (
			T model,
			ResultSet rs)
		throws SQLException, 
				NoSuchMethodException, 
				IllegalAccessException, 
				InvocationTargetException {
		
		if ((rs == null) || (model == null)) return null;
		
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		// ���R�[�h����e�J�����l���擾���A�w�肳�ꂽ���R�[�h���f����
		// �e�t�B�[���h�Ɋi�[����
		for (int i = 1 ; i <= columnCount ; i++) {
			Object obj = rs.getObject(i);
			String columnName = metaData.getColumnName(i);
			// "setXXXXX(XXXXX�̓e�[�u���̃J������)���\�b�h���Ăяo��
			String setterMethodName = "set" + columnName;
			if (obj != null) {
				Method method = 
					model.getClass().getMethod(setterMethodName,obj.getClass());
				method.invoke(model, rs.getObject(i));
			}
		}
		return model;
	}
	
	/**
	 * ���l�^���o�C���h�ϐ��Ƀo�C���h���邽�߂̃��\�b�h
	 * ���l�^���null���ݒ肳�ꂽ�ꍇ��setNull()�����s����
	 * @param stmt     �o�C���h�Ώ�PreparedStatement
	 * @param colIndex �o�C���h�ϐ��C���f�b�N�X
	 * @param value    ���l
	 * @param sqlType  �o�C���h�w�肷��SQL�̌^
	 * @throws SQLException
	 */
	protected void setNumber(
			PreparedStatement stmt, 
			int colIndex, Number value, int sqlType) throws SQLException {
		
		if ((stmt == null) || (colIndex < 1)) return;
		
		if (value == null) {
			stmt.setNull(colIndex, sqlType);
			return;
		}
		
		// �w�肳�ꂽSQL�̌^�ɂ����PreparedStatement
		// ��setXXXX���\�b�h�����s����
		if (sqlType == INTEGER) {
			stmt.setInt(colIndex, (Integer)value);
		} else if (sqlType == BIGINT) {
			stmt.setLong(colIndex,(Long)value);
		} else if (sqlType == FLOAT) {
			stmt.setFloat(colIndex, (Float)value);
		} else if (sqlType == DOUBLE) {
			stmt.setDouble(colIndex, (Double)value);
		}
		// ���̌^�͕K�v�ɉ����Ē�`����
	}
}