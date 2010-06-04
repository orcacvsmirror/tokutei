package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;

/**
 * �������ʃf�[�^���̑��f�[�^�A�N�Z�X�I�u�W�F�N�g
 * �C���^�t�F�[�X
 * @author nishiyama
 *
 */
public interface TKensakekaSonotaDao extends Dao {

	/**
	 * �v���C�}���[�L�[�w��Ń��R�[�h1���擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 TKensakekaSonota selectByPrimaryKey(
			Long uketukeId,
			Integer kensaNengapi,
			String koumokuCd)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	 // edit ver2 s.inoue 2009/08/26
	/**
	 * �v���C�}���[�L�[�w��Ń��R�[�h1���擾
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param koumokuCd ���ڃR�[�h
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 TKensakekaSonota selectByPrimaryKey(
			Long uketukeId,
			String koumokuCd)
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
			Integer kensaNengapi,
			String koumokuCd)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;


		/**
		 * ��tID,�������{�N�����w��Ń��R�[�h�擾
		 * @param uketukeId ��tID
		 * @param kensaNengapi �������{�N����
		 * @return TKensakekaSonota
		 * @throws SQLException
		 */
		 List<TKensakekaSonota> selectByUketukeIdKensaNengapi(
				Long uketukeId,
				Integer kensaNengapi)
			throws SQLException, NoSuchMethodException,
					IllegalAccessException, InvocationTargetException;

	/**
	 * �S�����R�[�h�擾
	 * @return �������ʃf�[�^���背�R�[�h�S��
	 */
	List<TKensakekaSonota> selectAll() throws SQLException,
		NoSuchMethodException, IllegalAccessException,
		InvocationTargetException;

	/**
	 * �������ʃf�[�^���̑����R�[�h1���}��
	 * @param kensakekaSonota �������ʃf�[�^���̑����R�[�h
	 * @throws SQLException
	 */
	void insert(TKensakekaSonota kensakekaSonota) throws SQLException;

	/**
	 * �������ʃf�[�^���̑����R�[�h�X�V
	 * @param kensakekaSonota �X�V�Ώی������ʃf�[�^���̑����R�[�h
	 * @throws SQLException
	 */
	void update(TKensakekaSonota kensakekaSonota) throws SQLException;

	// add ver2 s.inoue 2009/07/07
	/**
	 * ���R�[�h�X�V
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updatekey(Long newUketukeId,Long curUketukeID,Integer kensaNengapi) throws SQLException;

	// add s.inoue 2009/10/08
	/**
	 * ���R�[�h�X�V
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updatekey(Integer newKensaNengapi,Long curUketukeID,Integer kensaNengapi) throws SQLException;

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
			String koumokuCd) throws SQLException;

	/**
	 * �w���tID,�������{�N�����̃��R�[�h���폜����
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @throws SQLException
	 */
	public void deleteByUketukeIdKensaNengapi(
			Long uketukeId, Integer kensaNengapi) throws SQLException;
}
