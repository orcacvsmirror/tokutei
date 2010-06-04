package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;

public interface TKojinDao extends Dao {

	/**
	 * �w���tID�̃��R�[�h��1���擾
	 * @param uketukeId
	 * @return
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	TKojin selectByPrimaryKey(String uketukeId)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	/**
	 * ���j�[�N�L�[�w��Ń��R�[�h�����擾
	 * @param jyushinSeiriNo ��f�������ԍ�
	 * @return kojinCnt �Y�����R�[�h����
	 * @throws SQLException
	 */
	Long selectByCountUniqueKey(String jyushinSeiriNo)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * ���R�[�h�S���擾
	 * @return
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	List<TKojin> selectAll()
		throws SQLException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException;
	/**
	 * ���R�[�h�}��
	 * @param tKojin
	 * @throws SQLException
	 */
	void insert(TKojin tKojin) throws SQLException;

	/**
	 * ���R�[�h�X�V
	 * @param tKojin
	 * @throws SQLException
	 */
	void update(TKojin tKojin) throws SQLException;

	/**
	 * ���R�[�h�폜
	 * @param uketukeId
	 * @throws SQLException
	 */
	void delete(String uketukeId) throws SQLException;

	/**
	 * ��tID�̔�
	 * @return ��tID
	 * @throws SQLException
	 */
	long selectNewUketukeId() throws SQLException;
}
