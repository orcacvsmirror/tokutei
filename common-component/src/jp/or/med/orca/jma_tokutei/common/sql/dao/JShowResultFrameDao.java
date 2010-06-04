package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.or.med.orca.jma_tokutei.common.sql.model.JShowResultFrameModel;

public interface JShowResultFrameDao extends Dao {

	/**
	 * �������ږ��ƌ��f�p�^�[�������擾����
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param centerKoumokuCd �����Z���^�[���ڃR�[�h
	 * @return JShowResultFrameModel
	 */
	JShowResultFrameModel selectKpNameCenterName(
			Long uketukeId,
			Integer kensaNengapi,
			Integer centerKoumokuCd) throws SQLException;

	// edit ver2 s.inoue 2009/08/26
	/**
	 * �������ږ��ƌ��f�p�^�[�������擾����
	 * @param uketukeId ��tID
	 * @param kensaNengapi �������{�N����
	 * @param centerKoumokuCd �����Z���^�[���ڃR�[�h
	 * @return JShowResultFrameModel
	 */
	JShowResultFrameModel selectKpNameCenterName(
			Long uketukeId
			) throws SQLException;


	List<TreeMap<String, String>>
	selectTableRecord(Long uketukeId, Integer kensaNengapi, String hknjanum, Integer kenshinPatternNumber)
	throws SQLException;

	// edit s.inoue 2009/11/18
	List<TreeMap<String, String>>
	selectTableRecord(Long uketukeId, Integer kenshinPatternNumber, String hknjanum)
	throws SQLException;
}