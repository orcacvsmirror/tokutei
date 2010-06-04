package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

public interface TNayoseDao extends Dao {

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param jyushinSeiriNo ��f�������ԍ�
	 * @return kojinCnt �Y�����R�[�h����
	 * @throws SQLException
	 */
	Long selectByCountUniqueKey(String uketukeId)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * ���R�[�h�폜
	 * @param uketukeId
	 * @throws SQLException
	 */
	void delete(String uketukeId) throws SQLException;

	/**
	 * ����ID�̔�
	 * @return ��tID
	 * @throws SQLException
	 */
	long selectNewNayoseNo() throws SQLException;

	/**
	 * ����No�擾
	 * @return ����No
	 * @throws SQLException
	 */
	long selectOldNayoseNo(long uketukeId) throws SQLException;

//	/**
//	 * ���R�[�h�S���擾
//	 * @return
//	 * @throws SQLException
//	 * @throws NoSuchMethodException
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 */
//	List<TKojin> selectAll()
//		throws SQLException, NoSuchMethodException,
//			IllegalAccessException, InvocationTargetException;
//	/**
//	 * ���R�[�h�}��
//	 * @param tKojin
//	 * @throws SQLException
//	 */
//	void insert(TKojin tKojin) throws SQLException;
//
//	/**
//	 * ���R�[�h�X�V
//	 * @param tKojin
//	 * @throws SQLException
//	 */
//	void update(TKojin tKojin) throws SQLException;
//
//	/**
//	 * �w���tID�̃��R�[�h��1���擾
//	 * @param uketukeId
//	 * @return
//	 * @throws SQLException
//	 * @throws NoSuchMethodException
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 */
//	TKojin selectByPrimaryKey(String uketukeId)
//		throws SQLException, NoSuchMethodException,
//				IllegalAccessException, InvocationTargetException;

}
