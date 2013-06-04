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

import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.RecordModel;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;

/**
 * T_KOJIN�f�[�^�A�N�Z�X�I�u�W�F�N�g
 *
 * @author nishiyama
 */
public class TKojinDaoImpl extends DaoImpl implements TKojinDao {

    private static final String DELETE_SQL = "DELETE FROM T_KOJIN WHERE UKETUKE_ID=?";

    private static final String INSERT_SQL = "INSERT INTO T_KOJIN "
	    + "VALUES (" + "PTNUM = ?,  " + "JYUSHIN_SEIRI_NO = ?,  "
	    + "YUKOU_KIGEN = ?,  " + "HKNJANUM = ?,  "
	    + "HIHOKENJYASYO_KIGOU = ?,  " + "HIHOKENJYASYO_NO = ?,  "
	    + "NAME = ?,  " + "KANANAME = ?,  " + "NICKNAME = ?,  "
	    + "BIRTHDAY = ?,  " + "SEX = ?,  " + "HOME_POST = ?,  "
	    + "HOME_ADRS = ?,  " + "HOME_BANTI = ?,  " + "HOME_TEL1 = ?,  "
	    + "KEITAI_TEL = ?,  " + "FAX = ?,  " + "EMAIL = ?,  "
	    + "KEITAI_EMAIL = ?,  " + "KEIYAKU_TORIMATOME = ?,  "
	    + "KOUHUBI = ?,  " + "SIHARAI_DAIKOU_BANGO = ?,  "
	    + "MADO_FUTAN_K_SYUBETU = ?,  " + "MADO_FUTAN_KIHON = ?,  "
	    + "MADO_FUTAN_S_SYUBETU = ?,  " + "MADO_FUTAN_SYOUSAI = ?,  "
	    + "MADO_FUTAN_T_SYUBETU = ?,  " + "MADO_FUTAN_TSUIKA = ?,  "
	    + "MADO_FUTAN_D_SYUBETU = ?,  " + "MADO_FUTAN_DOC = ?,  "
	    + "NENDO = ?, " + "UKETUKE_ID = ?" + ")";

    private static final String UPDATE_SQL = "UPDATE T_KOJIN " + "SET "
	    + "PTNUM = ?,  " + "JYUSHIN_SEIRI_NO = ?,  " + "YUKOU_KIGEN = ?,  "
	    + "HKNJANUM = ?,  " + "HIHOKENJYASYO_KIGOU = ?,  "
	    + "HIHOKENJYASYO_NO = ?,  " + "NAME = ?,  " + "KANANAME = ?,  "
	    + "NICKNAME = ?,  " + "BIRTHDAY = ?,  " + "SEX = ?,  "
	    + "HOME_POST = ?,  " + "HOME_ADRS = ?,  " + "HOME_BANTI = ?,  "
	    + "HOME_TEL1 = ?,  " + "KEITAI_TEL = ?,  " + "FAX = ?,  "
	    + "EMAIL = ?,  " + "KEITAI_EMAIL = ?,  "
	    + "KEIYAKU_TORIMATOME = ?,  " + "KOUHUBI = ?,  "
	    + "SIHARAI_DAIKOU_BANGO = ?,  " + "MADO_FUTAN_K_SYUBETU = ?,  "
	    + "MADO_FUTAN_KIHON = ?,  " + "MADO_FUTAN_S_SYUBETU = ?,  "
	    + "MADO_FUTAN_SYOUSAI = ?,  " + "MADO_FUTAN_T_SYUBETU = ?,  "
	    + "MADO_FUTAN_TSUIKA = ?,  " + "MADO_FUTAN_D_SYUBETU = ?,  "
	    + "MADO_FUTAN_DOC = ?,  " + "NENDO = ?, " + "UKETUKE_ID = ? "
	    + "WHERE " + "UKETUKE_ID = ?";

    private static final String SELECT_ALL_SQL = "SELECT * FROM T_KOJIN";

    private static final String SELECT_BY_PRIMARYKEY_SQL = "SELECT * FROM T_KOJIN WHERE UKETUKE_ID=?";

    private static final String SELECT_NEWUKETUKE_ID_SQL = "SELECT MAX(UKETUKE_ID) FROM T_KOJIN WHERE UKETUKE_ID > ?";

    private static final String UKETUKE_ID_FORMAT = "%d%02d%02d";

    // add ver2 s.inoue 2009/05/01
    private static final String SELECT_BY_COUNT_UNIQUEKEY_SQL = "SELECT COUNT(UKETUKE_ID) FROM T_KOJIN WHERE JYUSHIN_SEIRI_NO=?";

    public TKojinDaoImpl(Connection con) {
	super(con);
    }

    /**
     * T_KOJIN���R�[�h�폜
     *
     * @param uketukeId
     *                ��tID
     */
    public void delete(String uketukeId) throws SQLException {
	Connection con = getConnection();
	if (con == null)
	    throw new SQLException("Not connected.");
	PreparedStatement stmt = con.prepareStatement(DELETE_SQL);
	stmt.setLong(0, new Long(uketukeId));
	stmt.executeUpdate();
	stmt.close();
	stmt = null;
    }

    /**
     * T_KOJIN���R�[�h�}��
     *
     * @param tKojin
     *                �V�K���R�[�h���Z�b�g����TKojin
     */
    public void insert(TKojin tKojin) throws SQLException {
	Connection con = getConnection();
	if (con == null)
	    throw new SQLException("Not connected.");
	PreparedStatement stmt = con.prepareStatement(INSERT_SQL);
	stmt.setString(1, tKojin.getPTNUM());
	stmt.setString(2, tKojin.getJYUSHIN_SEIRI_NO());
	stmt.setInt(3, tKojin.getYUKOU_KIGEN());
	stmt.setString(4, tKojin.getHKNJANUM());
	stmt.setString(5, tKojin.getHIHOKENJYASYO_KIGOU());
	stmt.setString(6, tKojin.getHIHOKENJYASYO_NO());
	stmt.setString(7, tKojin.getNAME());
	stmt.setString(8, tKojin.getKANANAME());
	stmt.setString(9, tKojin.getNICKNAME());
	stmt.setInt(10, tKojin.getBIRTHDAY());
	stmt.setInt(11, tKojin.getSEX());
	stmt.setString(12, tKojin.getHOME_POST());
	stmt.setString(13, tKojin.getHOME_ADRS());
	stmt.setString(14, tKojin.getHOME_BANTI());
	stmt.setString(15, tKojin.getHOME_TEL1());
	stmt.setString(16, tKojin.getKEITAI_TEL());
	stmt.setString(17, tKojin.getFAX());
	stmt.setString(18, tKojin.getEMAIL());
	stmt.setString(19, tKojin.getKEITAI_EMAIL());
	stmt.setString(20, tKojin.getKEIYAKU_TORIMATOME());
	stmt.setInt(21, tKojin.getKOUHUBI());
	stmt.setString(22, tKojin.getSIHARAI_DAIKOU_BANGO());
	stmt.setInt(23, tKojin.getMADO_FUTAN_K_SYUBETU());
	stmt.setString(24, tKojin.getMADO_FUTAN_KIHON());
	stmt.setInt(25, tKojin.getMADO_FUTAN_S_SYUBETU());
	stmt.setString(26, tKojin.getMADO_FUTAN_SYOUSAI());
	stmt.setInt(27, tKojin.getMADO_FUTAN_T_SYUBETU());
	stmt.setString(28, tKojin.getMADO_FUTAN_TSUIKA());
	stmt.setInt(29, tKojin.getMADO_FUTAN_D_SYUBETU());
	stmt.setString(30, tKojin.getMADO_FUTAN_DOC());
	stmt.setInt(31, tKojin.getNENDO());
	stmt.setLong(32, tKojin.getUKETUKE_ID());
	stmt.executeUpdate();
	stmt.close();
	stmt = null;
    }

    // add ver2 s.inoue 2009/05/01
	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param jyushinSeiriNo ��f�������ԍ�
	 * @return kojinCnt �Y�����R�[�h����
	 * @throws SQLException
	 */
	public Long selectByCountUniqueKey(
			String jyushinSeiriNo)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_UNIQUEKEY_SQL);
		stmt.setString(1, jyushinSeiriNo);
		ResultSet rs = stmt.executeQuery();

		Long kojinCnt = 0L;
		if (rs.next()) {
			kojinCnt = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return kojinCnt;
	}


    /**
     * �w���tID�̃��R�[�h��1���擾
     *
     * @param uketukeId
     * @return �w���tID������TKojin���R�[�h���f��
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public TKojin selectByPrimaryKey(String uketukeId) throws SQLException,
	    NoSuchMethodException, IllegalAccessException,
	    InvocationTargetException {

	Connection con = getConnection();
	if (con == null)
	    throw new SQLException("Not connected.");
	PreparedStatement stmt = con.prepareStatement(SELECT_BY_PRIMARYKEY_SQL);
	stmt.setLong(1, new Long(uketukeId));
	ResultSet rs = stmt.executeQuery();
	if (!rs.next())
	    return null;
	rs.close();
	stmt.close();
	rs = null;
	stmt = null;
	return setValueForRecordModel(new TKojin(), rs);
    }

    /**
     * ���R�[�h�S���擾
     *
     * @return TKojin���R�[�h���f���̃��X�g
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public List<TKojin> selectAll() throws SQLException, NoSuchMethodException,
	    IllegalAccessException, InvocationTargetException {

	Connection con = getConnection();
	if (con == null)
	    throw new SQLException("Not connected.");
	PreparedStatement stmt = con.prepareStatement(SELECT_ALL_SQL);
	ResultSet rs = stmt.executeQuery();

	List<TKojin> resultList = new ArrayList<TKojin>();
	while (rs.next()) {
	    resultList.add(setValueForRecordModel(new TKojin(), rs));
	}
	rs.close();
	stmt.close();
	rs = null;
	stmt = null;
	return ((!resultList.isEmpty()) ? resultList : null);
    }

    /**
     * ���R�[�h�X�V
     *
     * @param tKojin
     * @throws SQLException
     */
    public void update(TKojin tKojin) throws SQLException {
	Connection con = getConnection();
	PreparedStatement stmt = con.prepareStatement(UPDATE_SQL);
	stmt.setString(1, tKojin.getPTNUM());
	stmt.setString(2, tKojin.getJYUSHIN_SEIRI_NO());
	stmt.setInt(3, tKojin.getYUKOU_KIGEN());
	stmt.setString(4, tKojin.getHKNJANUM());
	stmt.setString(5, tKojin.getHIHOKENJYASYO_KIGOU());
	stmt.setString(6, tKojin.getHIHOKENJYASYO_NO());
	stmt.setString(7, tKojin.getNAME());
	stmt.setString(8, tKojin.getKANANAME());
	stmt.setString(9, tKojin.getNICKNAME());
	stmt.setInt(10, tKojin.getBIRTHDAY());
	stmt.setInt(11, tKojin.getSEX());
	stmt.setString(12, tKojin.getHOME_POST());
	stmt.setString(13, tKojin.getHOME_ADRS());
	stmt.setString(14, tKojin.getHOME_BANTI());
	stmt.setString(15, tKojin.getHOME_TEL1());
	stmt.setString(16, tKojin.getKEITAI_TEL());
	stmt.setString(17, tKojin.getFAX());
	stmt.setString(18, tKojin.getEMAIL());
	stmt.setString(19, tKojin.getKEITAI_EMAIL());
	stmt.setString(20, tKojin.getKEIYAKU_TORIMATOME());
	stmt.setInt(21, tKojin.getKOUHUBI());
	stmt.setString(22, tKojin.getSIHARAI_DAIKOU_BANGO());
	stmt.setInt(23, tKojin.getMADO_FUTAN_K_SYUBETU());
	stmt.setString(24, tKojin.getMADO_FUTAN_KIHON());
	stmt.setInt(25, tKojin.getMADO_FUTAN_S_SYUBETU());
	stmt.setString(26, tKojin.getMADO_FUTAN_SYOUSAI());
	stmt.setInt(27, tKojin.getMADO_FUTAN_T_SYUBETU());
	stmt.setString(28, tKojin.getMADO_FUTAN_TSUIKA());
	stmt.setInt(29, tKojin.getMADO_FUTAN_D_SYUBETU());
	stmt.setString(30, tKojin.getMADO_FUTAN_DOC());
	stmt.setInt(31, tKojin.getNENDO());
	stmt.setLong(32, tKojin.getUKETUKE_ID());
	stmt.setLong(33, tKojin.getUKETUKE_ID());
	stmt.executeUpdate();
	stmt.close();
	stmt = null;
    }

    // edit s.inoue 2010/12/01
    public long selectNewUketukeId() throws SQLException {
    	return selectNewUketukeId(1);
    }
    /**
     * ��tID�̍̔�
     * @return ��tID
     */
    public long selectNewUketukeId(int diff) throws SQLException {
	Connection con = getConnection();
	if (con == null)
	    throw new SQLException("Not connected.");
	PreparedStatement stmt = con.prepareStatement(SELECT_NEWUKETUKE_ID_SQL);

	Calendar calendar = Calendar.getInstance();
	long idValue = new Long(String.format(UKETUKE_ID_FORMAT, calendar
		.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + diff, calendar
		.get(Calendar.DAY_OF_MONTH))).longValue() * 10000;
	stmt.setLong(1, idValue);
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
