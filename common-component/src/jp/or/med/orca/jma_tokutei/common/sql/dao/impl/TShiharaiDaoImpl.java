package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TShiharaiDao;

/**
 * T_SHIHARAI�f�[�^�A�N�Z�X�I�u�W�F�N�g
 */
public class TShiharaiDaoImpl extends DaoImpl implements TShiharaiDao {

    private static final String DELETE_BY_SHIHARAINUM_SQL = "DELETE FROM T_SHIHARAI ";
    private static final String SELECT_BY_COUNT_KEY_SQL = "SELECT COUNT(SHIHARAI_DAIKO_NO) FROM T_SHIHARAI WHERE SHIHARAI_DAIKO_NO=?";

    public TShiharaiDaoImpl(Connection con) {
    	super(con);
    }

    /**
     * T_SHIHARAI���R�[�h�폜
     * @param hokenjanum
     * �x��No
     */
	public void delete(String shiharaiNum) throws SQLException {
		Connection con = getConnection();
		if (con == null)
		    throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(DELETE_BY_SHIHARAINUM_SQL);
		stmt.setString(1, shiharaiNum);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param �x��No
	 * @return �Y�����R�[�h
	 * @throws SQLException
	 */
	public int selectByCountUniqueKey(String shiharaiNum)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return 0;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_KEY_SQL);
		stmt.setString(1, shiharaiNum);
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
}
