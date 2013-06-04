package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

public interface TShiharaiDao extends Dao {

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param shiharainum
	 * @return kojinCnt
	 */
	int selectByCountUniqueKey(String shiharainum)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * ���R�[�h�폜
	 * @param
	 * @throws SQLException
	 */
	void delete(String shiharainum)throws SQLException;
}
