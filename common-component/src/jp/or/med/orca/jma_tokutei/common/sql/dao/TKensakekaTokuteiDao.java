package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;

public interface TKensakekaTokuteiDao extends Dao {

	/**
	 * �w���tID�A�����N����(YYYYMMDD)���R�[�h��1���擾
	 * @param uketukeId	��tID
	 * @param kensaNengapi �����N����
	 * @return TKensakekaTokutei
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	TKensakekaTokutei selectByPrimaryKey(Long uketukeId, Integer kensaNengapi)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	// edit ver2 s.inoue 2009/08/26
	/**
	 * �w���tID�A�����N����(YYYYMMDD)���R�[�h��1���擾
	 * @param uketukeId	��tID
	 * @param kensaNengapi �����N����
	 * @return TKensakekaTokutei
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	TKensakekaTokutei selectByPrimaryKey(Long uketukeId)
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
	List<TKensakekaTokutei> selectAll()
		throws SQLException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException;

	 /**
	 * �v���C�}���[�L�[�w��Ń��R�[�h�����擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 Long selectByCountPrimaryKey(
			Long uketukeId,
			Integer kensaNengapi)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;
	 // add s.inoue 2012/07/04
	 public abstract TKensakekaTokutei selectKekkaTokuteiByPrimaryKey(Long long1, Integer integer)
	 throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	 // edit ver2 s.inoue 2009/08/26
	 /**
	 * �v���C�}���[�L�[�w��Ń��R�[�h�����擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 Long selectByCountPrimaryKey(
			Long uketukeId)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	/**
	 * ���R�[�h�}��
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void insert(TKensakekaTokutei tKensakekaTokutei) throws SQLException;

	/**
	 * ���R�[�h�X�V
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void update(TKensakekaTokutei tKensakekaTokutei,boolean flgInput) throws SQLException;

	// add ver2 s.inoue 2009/07/07
	/**
	 * ���R�[�h�X�V
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updatekey(Long newUketukeId,Long curUketukeID,Integer kensaNengapi) throws SQLException;

	// edit s.inoue 2009/10/04
	/**
	 * ���R�[�h�X�V
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updateKenshinDate(Integer InputKenshinDate,Long curUketukeID,Integer kensaNengapi) throws SQLException;

	/**
	 * ���R�[�h�폜
	 * @param uketukeId
	 * @throws SQLException
	 */
	void delete(String uketukeId) throws SQLException;

	/**
	 * ���R�[�h�폜
	 * @param uketukeId
	 * @throws SQLException
	 */
	void delete(String uketukeId, String jissibi) throws SQLException;
}
