package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.sql.Types.*;

import jp.or.med.orca.jma_tokutei.common.sql.dao.JShowResultFrameDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.JShowResultFrameModel;

public class JShowResultFrameDaoImpl extends DaoImpl implements
		JShowResultFrameDao {

	// edit ver2 s.inoue 2009/08/27
	// �����Z���^�[���̂���肷��ɂ͌����Z���^�[�R�[�h�ƌ����Z���^�[���ڃR�[�h��
	// �w�肷��K�v������B
	// �������ʓ���e�[�u���ɂ͌����Z���^�[���ڃR�[�h�񂪑��݂��Ȃ����߁A
	// �����Z���^�[���̂�1�ɓ���ł��Ȃ��B
	// ���̂��߁A�����Z���^�[���̂͒��o���Ȃ��B
	private static final String SELECT_KPNAME_CENTERNAME_BY_UKETUKEID_SQL
	= "select b.k_p_name, b.k_p_no  " +
		" from " +
		" t_kensakeka_tokutei as a " +
		" left join " +
		" t_kenshin_p_m as b " +
		" on " +
		" a.k_p_no = b.k_p_no " +
		" where " +
		" a.uketuke_id = ? ";
//	private static final String SELECT_KPNAME_CENTERNAME_SQL
//	= "select b.k_p_name, b.k_p_no  " +
//		" from " +
//		" t_kensakeka_tokutei as a " +
//		" left join " +
//		" t_kenshin_p_m as b " +
//		" on " +
//		" a.k_p_no = b.k_p_no " +
//		" where " +
//		" a.uketuke_id = ? " +
//		" and " +
//		" a.kensa_nengapi = ? ";

	// edit ver2 s.inoue 2009/08/26
	private static final String SELECT_TABLERECORD_SQL = createSelectTableRecordSql();
	private static final String SELECT_TABLERECORD_BY_UKETUKEID_SQL = createSelectTableRecordByUketukeIDSql();

	private static String createSelectTableRecordSql(){
		StringBuffer buffer = new StringBuffer();

		buffer.append(" SELECT master.KOUMOKU_CD,");

		// edit ver2 s.inoue 2009/07/23
		//buffer.append(" master.KOUMOKU_NAME,");
		buffer.append("  case when master.KOUMOKU_NAME ='�����@�\��f1' then '����1-1' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f2' then '����1-2' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f3' then '����1-3' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f4' then '����4' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f5' then '����5' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f6' then '����6' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f7' then '����7' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f8' then '����8' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f9' then '����9' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f10' then '����10' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f11' then '����11' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f12' then '����12' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f13' then '����13' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f14' then '����14' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f15' then '����15' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f16' then '����16' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f17' then '����17' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f18' then '����18' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f19' then '����19' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f20' then '����20' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f21' then '����21' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f22' then '����22' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f23' then '����23' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f24' then '����24' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f25' then '����25' ");
		buffer.append("  else master.KOUMOKU_NAME end KOUMOKU_NAME, ");

		buffer.append(" master.KENSA_HOUHOU,");
		buffer.append(" sonota.KEKA_TI AS KEKA_TI_DECIMAL,");
		buffer.append(" sonota.KEKA_TI AS KEKA_TI_CODE,");
		buffer.append(" sonota.KEKA_TI AS KEKA_TI_STRING,");
		// add s.inoue 20080911
		buffer.append(" case sonota.JISI_KBN ");
		buffer.append(" when 0 then TRIM('0:�����{') ");
		buffer.append(" when 1 then TRIM('1:���{') ");
		buffer.append(" when 2 then TRIM('2:����s�\') ");
		buffer.append(" else TRIM('1:���{') end as JISI_KBN, ");

		buffer.append(" master.DS_KAGEN,");
		buffer.append(" master.DS_JYOUGEN,");
		buffer.append(" master.JS_KAGEN,");
		buffer.append(" master.JS_JYOUGEN,");
		buffer.append(" master.KAGEN,");
		buffer.append(" master.JYOUGEN,");
		buffer.append(" master.DATA_TYPE,");
		buffer.append(" master.MAX_BYTE_LENGTH,");
		buffer.append(" sonota.KOMENTO,");
		buffer.append(" case sonota.H_L when 1 then 'L' ");
		buffer.append(" when 2 then 'H' ");
		buffer.append(" else '' end as H_L,");
		buffer.append(" case sonota.HANTEI ");
		buffer.append(" when 1 then '�ُ�Ȃ�' ");
		buffer.append(" when 2 then '�y�x�ُ�' ");
		buffer.append(" when 3 then '�v�o�ߊώ@' ");
		buffer.append(" when 4 then '�ُ�' ");
		buffer.append(" when 5 then '�v����' ");
		buffer.append(" else '������' end ");
		buffer.append(" as HANTEI ");

		buffer.append(" FROM ");
		buffer.append(" T_KENSAKEKA_SONOTA as sonota ");

		buffer.append(" LEFT JOIN T_KENSHINMASTER as master ");
		buffer.append(" ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
		// buffer.append(" AND ");
		// buffer.append(" sonota.KENSA_NENGAPI = ? ");
		buffer.append(" AND ");
		buffer.append(" sonota.UKETUKE_ID = ? ");
		buffer.append(" LEFT JOIN T_KENSHIN_P_S ps ");
		buffer.append(" ON ps.KOUMOKU_CD = master.KOUMOKU_CD   ");
		buffer.append(" AND ps.K_P_NO = ? ");
		buffer.append(" WHERE master.KOUMOKU_CD NOT IN ('9N501000000000011', '9N506000000000011') ");
		buffer.append(" AND ");
		buffer.append(" master.HKNJANUM = ? ");
		buffer.append(" ORDER BY ps.LOW_ID ");

		return buffer.toString();
	}


	// add ver2 s.inoue 2009/08/26
	private static String createSelectTableRecordByUketukeIDSql(){
		StringBuffer buffer = new StringBuffer();

		buffer.append(" SELECT master.KOUMOKU_CD,");

		// edit ver2 s.inoue 2009/07/23
		//buffer.append(" master.KOUMOKU_NAME,");
		buffer.append("  case when master.KOUMOKU_NAME ='�����@�\��f1' then '����1-1' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f2' then '����1-2' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f3' then '����1-3' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f4' then '����4' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f5' then '����5' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f6' then '����6' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f7' then '����7' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f8' then '����8' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f9' then '����9' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f10' then '����10' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f11' then '����11' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f12' then '����12' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f13' then '����13' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f14' then '����14' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f15' then '����15' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f16' then '����16' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f17' then '����17' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f18' then '����18' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f19' then '����19' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f20' then '����20' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f21' then '����21' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f22' then '����22' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f23' then '����23' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f24' then '����24' ");
		buffer.append("  when master.KOUMOKU_NAME ='�����@�\��f25' then '����25' ");
		buffer.append("  else master.KOUMOKU_NAME end KOUMOKU_NAME, ");

		buffer.append(" master.KENSA_HOUHOU,");
		buffer.append(" sonota.KEKA_TI AS KEKA_TI_DECIMAL,");
		buffer.append(" sonota.KEKA_TI AS KEKA_TI_CODE,");
		buffer.append(" sonota.KEKA_TI AS KEKA_TI_STRING,");
		buffer.append(" case sonota.JISI_KBN ");
		buffer.append(" when 0 then TRIM('0:�����{') ");
		buffer.append(" when 1 then TRIM('1:���{') ");
		buffer.append(" when 2 then TRIM('2:����s�\') ");
		buffer.append(" else TRIM('1:���{') end as JISI_KBN, ");
		buffer.append(" master.DS_KAGEN,");
		buffer.append(" master.DS_JYOUGEN,");
		buffer.append(" master.JS_KAGEN,");
		buffer.append(" master.JS_JYOUGEN,");
		buffer.append(" master.KAGEN,");
		buffer.append(" master.JYOUGEN,");
		buffer.append(" master.DATA_TYPE,");
		buffer.append(" master.MAX_BYTE_LENGTH,");
		// edit s.inoue 2009/11/18
		buffer.append(" sonota.KOMENTO AS COMMENT,");
		buffer.append(" case sonota.H_L when 1 then 'L' ");
		buffer.append(" when 2 then 'H' ");
		buffer.append(" else '' end as HL,");
		buffer.append(" case sonota.HANTEI ");
		buffer.append(" when 1 then '�ُ�Ȃ�' ");
		buffer.append(" when 2 then '�y�x�ُ�' ");
		buffer.append(" when 3 then '�v�o�ߊώ@' ");
		buffer.append(" when 4 then '�ُ�' ");
		buffer.append(" when 5 then '�v����' ");
		buffer.append(" else '������' end ");
		buffer.append(" as HANTEI ");
		buffer.append(" FROM ");
		buffer.append(" T_KENSAKEKA_SONOTA as sonota ");
		buffer.append(" LEFT JOIN T_KENSHINMASTER as master ");
		buffer.append(" ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
		buffer.append(" AND ");
		buffer.append(" sonota.UKETUKE_ID = ? ");
		buffer.append(" LEFT JOIN T_KENSHIN_P_S ps ");
		buffer.append(" ON ps.KOUMOKU_CD = master.KOUMOKU_CD   ");
		buffer.append(" AND ps.K_P_NO = ? ");
		buffer.append(" WHERE master.KOUMOKU_CD NOT IN ('9N501000000000011', '9N506000000000011') ");
		buffer.append(" AND ");
		buffer.append(" master.HKNJANUM = ? ");
		// edit s.inoue 2009/10/28
		// �R�����g�߂� �\�[�g������
		// del ver2 s.inoue 2009/08/26
		buffer.append(" ORDER BY ps.LOW_ID ");

		return buffer.toString();
	}

	public JShowResultFrameDaoImpl(Connection con) {
		super(con);
	}

	// edit s.inoue 2009/11/18
	/**
	 * �������ږ��ƌ��f�p�^�[�������擾����
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param centerKoumokuCd �����Z���^�[���ڃR�[�h
	 * @return JShowResultFrameModel
	 */
	public JShowResultFrameModel selectKpNameCenterName(Long uketukeId) throws SQLException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_KPNAME_CENTERNAME_BY_UKETUKEID_SQL);
		setNumber(stmt, 1, uketukeId, BIGINT);

		ResultSet rs = stmt.executeQuery();
		if (!rs.next()) return null;

		JShowResultFrameModel recModel = null;
		try {
			recModel = setValueForRecordModel(new JShowResultFrameModel(), rs);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return recModel;
	}
	// edit ver2 s.inoue 2009/08/26
//	public JShowResultFrameModel selectKpNameCenterName(Long uketukeId,
//			Integer kensaNengapi, Integer centerKoumokuCd) throws SQLException {
//
//		Connection con = getConnection();
//		if (con == null) return null;
//
//		PreparedStatement stmt = con.prepareStatement(SELECT_KPNAME_CENTERNAME_SQL);
//		setNumber(stmt, 1, uketukeId, BIGINT);
//		setNumber(stmt, 2, kensaNengapi, INTEGER);
//
//		ResultSet rs = stmt.executeQuery();
//		if (!rs.next()) return null;
//
//		JShowResultFrameModel recModel = null;
//		try {
//			recModel = setValueForRecordModel(new JShowResultFrameModel(), rs);
//		} catch (Exception e) {
//			throw new SQLException(e);
//		}
//		return recModel;
//	}

	// edit ver2 s.inoue 2009/08/26 orverload
	public List<TreeMap<String, String>>selectTableRecord(Long uketukeId, Integer kenshinPatternNumber, String hknjanum)
	throws SQLException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_TABLERECORD_BY_UKETUKEID_SQL);

		int index = 1;
		setNumber(stmt, index++, uketukeId, BIGINT);
		setNumber(stmt, index++, kenshinPatternNumber, INTEGER);
		stmt.setString(index++, hknjanum);

		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int colCnt = metaData.getColumnCount();
		List<TreeMap<String, String>> recList = new ArrayList<TreeMap<String,String>>();
		while (rs.next()) {
			TreeMap<String, String> recMap = new TreeMap<String, String>();
			for (int i = 1 ; i <= colCnt ; i++) {
				String key = metaData.getColumnName(i);
				String value = rs.getString(i);
				recMap.put(key, value);
			}
			if (!recMap.isEmpty()) recList.add(recMap);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return ((!recList.isEmpty()) ? recList : null);
	}

//	public List<Map<String, String>>
//	selectTableRecord(Long uketukeId, Integer kensaNengapi, String hknjanum, Integer kenshinPatternNumber)
//	throws SQLException {
//
//		Connection con = getConnection();
//		if (con == null) return null;
//
//		PreparedStatement stmt = con.prepareStatement(SELECT_TABLERECORD_SQL);
//
//		int index = 1;
//		setNumber(stmt, index++, kensaNengapi, INTEGER);
//		setNumber(stmt, index++, uketukeId, BIGINT);
//		setNumber(stmt, index++, kenshinPatternNumber, INTEGER);
//		stmt.setString(index++, hknjanum);
//
//		ResultSet rs = stmt.executeQuery();
//		ResultSetMetaData metaData = rs.getMetaData();
//		int colCnt = metaData.getColumnCount();
//		List<Map<String, String>> recList = new ArrayList<Map<String,String>>();
//
//		while (rs.next()) {
//			Map<String, String> recMap = new HashMap<String, String>();
//			for (int i = 1 ; i <= colCnt ; i++) {
//				String key = metaData.getColumnName(i);
//				String value = rs.getString(i);
//				recMap.put(key, value);
//			}
//			if (!recMap.isEmpty()) recList.add(recMap);
//		}
//		rs.close();
//		stmt.close();
//		rs = null;
//		stmt = null;
//		return ((!recList.isEmpty()) ? recList : null);
//	}

	// edit ver2 s.inoue 2009/08/26
	@Override
	public JShowResultFrameModel selectKpNameCenterName(Long uketukeId,
			Integer kensaNengapi, Integer centerKoumokuCd) throws SQLException {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public List<TreeMap<String, String>> selectTableRecord(Long uketukeId,
			Integer kensaNengapi, String hknjanum, Integer kenshinPatternNumber)
			throws SQLException {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
}