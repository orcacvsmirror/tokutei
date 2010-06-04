package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.*;

import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;

/**
 * �������ʃf�[�^���̑��f�[�^�A�N�Z�X�I�u�W�F�N�g
 * @author nishiyama
 *
 */
public class TKensakekaSonotaDaoImpl extends DaoImpl implements TKensakekaSonotaDao {

	private static final String SELECT_BY_ALL_SQL =
		"SELECT * FROM T_KENSAKEKA_SONOTA";
	// add 20080725 s.inoue
	private static final String SELECT_BY_COUNT_SQL =
		"SELECT COUNT(UKETUKE_ID) CNT FROM T_KENSAKEKA_SONOTA";

	private static final String SELECT_BY_COUNT_PRIMARYKEY_SQL =
		SELECT_BY_COUNT_SQL +
		" WHERE " +
		"UKETUKE_ID=? AND KENSA_NENGAPI=? AND KOUMOKU_CD=?";

	private static final String SELECT_BY_PRIMARYKEY_SQL =
		SELECT_BY_ALL_SQL +
		" WHERE " +
		"UKETUKE_ID=? AND KENSA_NENGAPI=? AND KOUMOKU_CD=?";

	// edit ver2 s.inoue 2009/08/26
	private static final String SELECT_BY_UKETUKEID_SQL =
		SELECT_BY_ALL_SQL +
		" WHERE " +
		"UKETUKE_ID=? AND KOUMOKU_CD=?";

	private static final String SELECT_BY_UKETUKEID_KENSANENGAPI_SQL =
		SELECT_BY_ALL_SQL +
		" WHERE " +
		"UKETUKE_ID=? AND KENSA_NENGAPI=?";

	private static final String INSERT_SQL =
		"INSERT INTO T_KENSAKEKA_SONOTA VALUES " +
		"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	/* Edit 2008/07/23 ��� */
	/* --------------------------------------------------- */
	private static final String UPDATE_SQL =
		"UPDATE T_KENSAKEKA_SONOTA SET " +
		//"HIHOKENJYASYO_KIGOU = ?," +
		//"HIHOKENJYASYO_NO = ?," +
		"KEKA_TI = ?," +
		//"KOMENTO = ?," +
		"H_L = ?," +
		//"HANTEI = ?," +
		//"NENDO = ?," +
		"JISI_KBN = ?," +
		"KEKA_TI_KEITAI = ? " +
		"WHERE " +
		"UKETUKE_ID = ? " +
		"AND " +
		"KENSA_NENGAPI = ? " +
		"AND " +
		"KOUMOKU_CD = ?";
	/* --------------------------------------------------- */

	// edit s.inoue 2009/10/08
	private static final String UPDATE_KENSANENGAPI_SQL =
		"UPDATE T_KENSAKEKA_SONOTA SET " +
		" KENSA_NENGAPI = ? " +
		" WHERE " +
		"UKETUKE_ID = ? " +
		"AND " +
		"KENSA_NENGAPI = ? ";

	// add ver2 s.inoue 2009/07/07
	private static final String UPDATE_KEY_SQL
		= "UPDATE T_KENSAKEKA_SONOTA SET " +
		"UKETUKE_ID = ? " +
		"WHERE " +
		"UKETUKE_ID = ? " +
		"AND " +
		"KENSA_NENGAPI = ? ";

	private static final String DELETE_SQL =
		"DELETE FROM T_KENSAKEKA_SONOTA " +
		"WHERE " +
		"UKETUKE_ID=? " +
		"AND " +
		"KENSA_NENGAPI=? " +
		"AND " +
		"KOUMOKU_CD=?";

	private static final String DELETE_BY_UKETUKEID_KENSANENGAPI_SQL =
		"DELETE FROM T_KENSAKEKA_SONOTA " +
		"WHERE " +
		"UKETUKE_ID=? " +
		"AND " +
		"KENSA_NENGAPI=? ";

	/**
	 * �R���X�g���N�^
	 * @param con �R�l�N�V����
	 */
	public TKensakekaSonotaDaoImpl(Connection con) {
		super(con);
	}

	// add ver2 s.inoue 2009/08/26
	/**
	 * �v���C�}���[�L�[�w��Ń��R�[�h1���擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	public TKensakekaSonota selectByPrimaryKey(
			Long uketukeId,
			String koumokuCd)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_UKETUKEID_SQL);
		stmt.setLong(1, uketukeId);
		stmt.setString(2, koumokuCd);
		ResultSet rs = stmt.executeQuery();
		TKensakekaSonota kensakekaSonota = null;
		if (rs.next()) {
			kensakekaSonota = setValueForRecordModel(new TKensakekaSonota(), rs);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return kensakekaSonota;
	}

	/**
	 * �v���C�}���[�L�[�w��Ń��R�[�h1���擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	public TKensakekaSonota selectByPrimaryKey(
			Long uketukeId,
			Integer kensaNengapi,
			String koumokuCd)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_PRIMARYKEY_SQL);
		stmt.setLong(1, uketukeId);
		stmt.setInt(2, kensaNengapi);
		stmt.setString(3, koumokuCd);
		ResultSet rs = stmt.executeQuery();
		TKensakekaSonota kensakekaSonota = null;
		if (rs.next()) {
			kensakekaSonota = setValueForRecordModel(new TKensakekaSonota(), rs);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return kensakekaSonota;
	}

	/** add s.inoue
	 * �v���C�}���[�L�[�w��Ń��R�[�h�����擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	public Long selectByCountPrimaryKey(
			Long uketukeId,
			Integer kensaNengapi,
			String koumokuCd)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_PRIMARYKEY_SQL);
		stmt.setLong(1, uketukeId);
		stmt.setInt(2, kensaNengapi);
		stmt.setString(3, koumokuCd);
		ResultSet rs = stmt.executeQuery();

		Long sonotaCnt = 0L;
		if (rs.next()) {
			sonotaCnt = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return sonotaCnt;

	}

	/**
	 * ��tID,�������{�N�����w��Ń��R�[�h�擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	public List<TKensakekaSonota> selectByUketukeIdKensaNengapi(
			Long uketukeId,
			Integer kensaNengapi)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_UKETUKEID_KENSANENGAPI_SQL);
		stmt.setLong(1, uketukeId);
		setNumber(stmt, 2, kensaNengapi, INTEGER);
		ResultSet rs = stmt.executeQuery();
		List<TKensakekaSonota> resultList = new ArrayList<TKensakekaSonota>();
		while (rs.next()) {
			resultList.add(
				setValueForRecordModel(new TKensakekaSonota(), rs));
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return ((!resultList.isEmpty()) ? resultList : null);
	}

	/**
	 * �S�����R�[�h�擾
	 * @return �������ʃf�[�^���背�R�[�h�S��
	 */
	public List<TKensakekaSonota> selectAll() throws SQLException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_ALL_SQL);
		ResultSet rs = stmt.executeQuery();
		List<TKensakekaSonota> resultList = new ArrayList<TKensakekaSonota>();
		while (rs.next()) {
			resultList.add(
				setValueForRecordModel(new TKensakekaSonota(), rs));
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return ((!resultList.isEmpty()) ? resultList : null);
	}

	/**
	 * �������ʃf�[�^���̑����R�[�h1���}��
	 * @param kensakekaSonota �������ʃf�[�^���̑����R�[�h
	 * @throws SQLException
	 */
	public void insert(TKensakekaSonota kensakekaSonota) throws SQLException {

		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt = con.prepareStatement(INSERT_SQL);
		stmt.setString(1, kensakekaSonota.getHIHOKENJYASYO_KIGOU());
		stmt.setString(2, kensakekaSonota.getHIHOKENJYASYO_NO());

		setNumber(stmt, 3, kensakekaSonota.getKENSA_NENGAPI(), INTEGER);

		stmt.setString(4, kensakekaSonota.getKOUMOKU_CD());
		stmt.setString(5, kensakekaSonota.getKEKA_TI());
		stmt.setString(6, kensakekaSonota.getKOMENTO());

		setNumber(stmt, 7, kensakekaSonota.getH_L(), INTEGER);
		setNumber(stmt, 8, kensakekaSonota.getHANTEI(), INTEGER);
		setNumber(stmt, 9, kensakekaSonota.getNENDO(), INTEGER);
		stmt.setLong(10, kensakekaSonota.getUKETUKE_ID());
		/* Edit 2008/07/23 ��� */
		/* --------------------------------------------------- */
		setNumber(stmt, 11, kensakekaSonota.getKensaJISI_KBN(), INTEGER);
		setNumber(stmt, 12, kensakekaSonota.getKEKA_TI_KEITAI(), INTEGER);
		/* --------------------------------------------------- */
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/* Edit 2008/07/23 ��� */
	/* --------------------------------------------------- */
	/**
	 * �������ʃf�[�^���̑����R�[�h�X�V
	 * @param kensakekaSonota �X�V�Ώی������ʃf�[�^���̑����R�[�h
	 * @throws SQLException
	 */
	public void update(TKensakekaSonota kensakekaSonota) throws SQLException {

		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt = con.prepareStatement(UPDATE_SQL);
		// stmt.setString(1, kensakekaSonota.getHIHOKENJYASYO_KIGOU());
		// stmt.setString(2, kensakekaSonota.getHIHOKENJYASYO_NO());
		stmt.setString(1, kensakekaSonota.getKEKA_TI());
		// stmt.setString(4, kensakekaSonota.getKOMENTO());
		setNumber(stmt, 2, kensakekaSonota.getH_L(), INTEGER);
		// setNumber(stmt, 6, kensakekaSonota.getHANTEI(), INTEGER);
		// setNumber(stmt, 7, kensakekaSonota.getNENDO(), INTEGER);
		setNumber(stmt, 3, kensakekaSonota.getKensaJISI_KBN(), INTEGER);
		setNumber(stmt, 4, kensakekaSonota.getKEKA_TI_KEITAI(), INTEGER);
		setNumber(stmt, 5, kensakekaSonota.getUKETUKE_ID(), BIGINT);
		setNumber(stmt, 6, kensakekaSonota.getKENSA_NENGAPI(), INTEGER);
		stmt.setString(7, kensakekaSonota.getKOUMOKU_CD());
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}
	/* --------------------------------------------------- */

	// add s.inoue 2009/10/08
	/**
	 * �������ʃf�[�^���̑����R�[�h�X�V
	 * @param kensakekaSonota �X�V�Ώی������ʃf�[�^���̑����R�[�h
	 * @throws SQLException
	 */
	public void updatekey(Integer KensaNengapi , Long curUketukeID,
			Integer kensaNengapi) throws SQLException {

		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt = con.prepareStatement(UPDATE_KENSANENGAPI_SQL);
		setNumber(stmt, 1, KensaNengapi, INTEGER);
		setNumber(stmt, 2, curUketukeID, BIGINT);
		setNumber(stmt, 3, kensaNengapi, INTEGER);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}
	/**
	 * �L�[�w��Ń��R�[�h1���폜
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @throws SQLException
	 */
	public void delete(
			Long uketukeId,
			Integer kensaNengapi,
			String koumokuCd) throws SQLException {

		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt = con.prepareStatement(DELETE_SQL);
		setNumber(stmt, 1, uketukeId, BIGINT);
		setNumber(stmt, 2, kensaNengapi, INTEGER);
		stmt.setString(3, koumokuCd);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * �w���tID,�������{�N�����̃��R�[�h���폜����
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @throws SQLException
	 */
	public void deleteByUketukeIdKensaNengapi(
			Long uketukeId,
			Integer kensaNengapi) throws SQLException {

		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt =
			con.prepareStatement(DELETE_BY_UKETUKEID_KENSANENGAPI_SQL);
		setNumber(stmt, 1, uketukeId, BIGINT);
		setNumber(stmt, 2, kensaNengapi, INTEGER);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	// add ver2 s.inoue 2009/07/07
	@Override
	public void updatekey(Long newUketukeId, Long curUketukeID,
			Integer kensaNengapi) throws SQLException {
		Connection con = getConnection();
		if (con == null) return;

		String updateSqlTmp ="";
		updateSqlTmp = UPDATE_KEY_SQL;

		PreparedStatement stmt = con.prepareStatement(updateSqlTmp);

		setNumber(stmt, 1, newUketukeId, BIGINT);
		setNumber(stmt, 2, curUketukeID, BIGINT);
		setNumber(stmt, 3, kensaNengapi, INTEGER);

		stmt.executeUpdate();
		stmt.close();
		stmt = null;

	}

}
