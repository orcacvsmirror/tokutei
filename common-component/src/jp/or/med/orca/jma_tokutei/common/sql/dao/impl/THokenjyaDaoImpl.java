package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.RecordModel;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

/**
 * T_HOKENJYA�f�[�^�A�N�Z�X�I�u�W�F�N�g
 */
public class THokenjyaDaoImpl extends DaoImpl implements THokenjyaDao {

    private static final String DELETE_BY_HKNJYANUM_SQL = "DELETE FROM T_HOKENJYA ";
	private static final String DELETE_SQL = "DELETE FROM T_HOKENJYA WHERE HKNJANUM=? AND HKNJYA_HISTORY_NO=? AND ITAKU_KBN=?";
    private static final String SELECT_BY_COUNT_UNIQUEKEY_SQL = "SELECT COUNT(HKNJYA_HISTORY_NO) FROM T_HOKENJYA WHERE HKNJANUM=? AND HKNJYA_HISTORY_NO=? AND ITAKU_KBN=?";
    private static final String SELECT_BY_COUNT_KEY_SQL = "SELECT COUNT(HKNJANUM) FROM T_HOKENJYA WHERE HKNJANUM=?";
    private static final String SELECT_NEW_HISTORY_ID_SQL = "SELECT MAX(HKNJYA_HISTORY_NO) FROM T_HOKENJYA WHERE HKNJYA_HISTORY_NO > ? AND HKNJANUM = ?";

    public THokenjyaDaoImpl(Connection con) {
    	super(con);
    }

    /**
     * T_HOKENJYA���R�[�h�폜
     * @param hokenjanum
     * �ی��Ҕԍ�
     */
	public void delete() throws SQLException {
		Connection con = getConnection();
		if (con == null)
		    throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(DELETE_BY_HKNJYANUM_SQL);
		// stmt.setString(1, hknjanum);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

    /**
     * T_HOKENJYA���R�[�h�폜
     *
     * @param uketukeId,historyId
     * �ی��Ҕԍ�,����id
     */
    public void delete(String hknjanum,String historyId,String itakuKubun) throws SQLException {
		Connection con = getConnection();
		if (con == null)
		    throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(DELETE_SQL);
		stmt.setString(1, hknjanum);
		stmt.setLong(2, new Long(historyId));
		stmt.setString(3, itakuKubun);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
    }

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param �ی���No
	 * @return �Y�����R�[�h
	 * @throws SQLException
	 */
	public int selectByCountUniqueKey(String hknjanum)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return 0;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_KEY_SQL);
		stmt.setString(1, hknjanum);
		ResultSet rs = stmt.executeQuery();

		int hokenjyaCnt = 0;
		if (rs.next()) {
			hokenjyaCnt = rs.getInt(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return hokenjyaCnt;
	}

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param uketukeId �ی���No,����No
	 * @return �Y�����R�[�h
	 * @throws SQLException
	 */
	public int selectByCountUniqueKey(String hknjanum,String historyId,String itakuKubun)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return 0;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_UNIQUEKEY_SQL);
		stmt.setString(1, hknjanum);
		stmt.setLong(2, new Long(historyId));
		stmt.setString(3, itakuKubun);
		ResultSet rs = stmt.executeQuery();

		int hokenjyaCnt = 0;
		if (rs.next()) {
			hokenjyaCnt = rs.getInt(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return hokenjyaCnt;
	}

    /**
     * ����ID�̍̔�
     *
     * @return ����ID(1�`)
     */
    public long selectNewHistoryId(String hknjanum) throws SQLException {
		Connection con = getConnection();
		if (con == null)
		    throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(SELECT_NEW_HISTORY_ID_SQL);

		// Calendar calendar = Calendar.getInstance();
		long idValue = 0;
		/*
			new Long(String.format(
			UKETUKE_ID_FORMAT,
			calendar.get(Calendar.YEAR),
			calendar.get(Calendar.MONTH) + 1,
			calendar.get(Calendar.DAY_OF_MONTH))).longValue() * 10000;
		*/
		stmt.setLong(1, idValue);
		stmt.setString(2, hknjanum);

		ResultSet rs = stmt.executeQuery();

		long resultValue = 0L;
		if (rs.next()) {
		    resultValue = rs.getLong(1);
		}

		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		idValue = (resultValue > 0) ? resultValue : idValue;
		idValue++;
		return idValue;
    }

}
