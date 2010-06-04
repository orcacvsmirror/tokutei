package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

public interface THokenjyaDao extends Dao {

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param hknjanum
	 * @return kojinCnt
	 */
	int selectByCountUniqueKey(String hknjanum)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param hknjanum,historyId
	 * @return kojinCnt
	 */
	int selectByCountUniqueKey(String hknjanum,String historyId,String itakuKubun)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * ���R�[�h�폜
	 * @param
	 * @throws SQLException
	 */
	void delete()throws SQLException;

	/**
	 * ���R�[�h�폜
	 * @param hknjanum
	 * @throws SQLException
	 */
	void delete(String hknjanum,String historyId,String itakuKubun) throws SQLException;

	/**
	 * ����ID�̔�
	 * @return ����ID
	 * @throws SQLException
	 */
	long selectNewHistoryId(String hknjanum) throws SQLException;

}
