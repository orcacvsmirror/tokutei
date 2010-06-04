package jp.or.med.orca.jma_tokutei.db;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JEraseSpace;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

public class DBYearAdjuster {

    private static org.apache.log4j.Logger logger = Logger.getLogger(DBYearAdjuster.class);
    // �ڑ��J�n
	private static JConnection kikanDatabase = null;
	private TNayoseDao tNayoseDao = null;
	private TKojinDao tKojinDao = null;
	private TKensakekaTokuteiDao tTokuteiDao = null;
	private TKensakekaSonotaDao tSonotaDao = null;

	public static void main(String[] args) {
		new DBYearAdjuster();
	}

    public DBYearAdjuster(){
    	JApplication.load();

		// �e�X�g�p���ۂ̓��O�C���ς�
//		try {
//			kikanDatabase = JConnection.ConnectKikanDatabase("4614310052");
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//    	callfixedNayose("4614310052");
    }

    public void call(String kikanNumber){

    	logger.info("�o�N�����ς݂��ǂ����`�F�b�N");
    	try {

    		// edit ver2 s.inoue 2009/07/24
    		kikanDatabase = JConnection.ConnectKikanDatabase(kikanNumber);

    		initialize(kikanNumber);
    		if (checkNayoseShori()){
    			kikanDatabase.Shutdown();return;
    		}
    	} catch (Exception ex) {
    		try {
				kikanDatabase.Shutdown();return;
			} catch (SQLException ex2) {
				logger.error(ex2.getMessage());
			}
			logger.error(ex.getMessage());
		}

    	logger.info("�o�N�����n��");

    	try{
    		registerNayose();
    		kikanDatabase.Shutdown();
    	} catch (Exception ex){
    		logger.error(ex.getMessage());
    	}
    	logger.info("�o�N�����I��");
    }

    // ver1.1.8�ȑO�Ōo�N�菇�𓥂܂��Ɍo�N�f�[�^���쐬�����ꍇ�̏���
    public void callfixedNayose(String kikanNumber){

    	logger.info("ver1.2.0�o�N�����ς݂��ǂ����`�F�b�N �J�n");

    	try {
       		// edit ver2 s.inoue 2009/07/24
    		kikanDatabase = JConnection.ConnectKikanDatabase(kikanNumber);

    		initialize(kikanNumber);
    		if (checkNayoseShori()){
    			logger.info("ver1.2.0�o�N���� ���{�ς�" );
    		}else{
    			logger.info("ver1.2.0�o�N���� �����{" );
    		}
    	} catch (Exception ex) {
			logger.error(ex.getMessage());
			try {
				kikanDatabase.Shutdown();return;
			} catch (SQLException ex2) {
				logger.error(ex2.getMessage());
			}
		}

    	logger.info("ver1.2.0�o�N�����ς݂��ǂ����`�F�b�N �I��");


    	logger.info("ver1.2.1�o�N�����␳ �J�n");

    	try{
    		registerfixedNayose();
    		kikanDatabase.Shutdown();
    	} catch (Exception ex){
    		logger.error(ex.getMessage());
    	}

    	logger.info("ver1.2.0�o�N�����␳ �I��");

    }

    // ��x�ł����񂹏��������{�������ǂ����`�F�b�N
    private boolean checkNayoseShori(){

    	boolean existsNumber = false;
    	String sql = "select COUNT(NAYOSE_NO) ROWCOUNT from T_NAYOSE";

		ArrayList<Hashtable<String, String>> result = null;
		try{
			result = kikanDatabase.sendExecuteQuery(sql);
		}catch(SQLException e){
			logger.error(e.getMessage());
		}

		if (result != null && result.size() > 0) {
			int rowCount = 0;
			try {
				rowCount = Integer.valueOf(Integer.parseInt(result.get(0).get("ROWCOUNT")));
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
			}

			if (rowCount > 0) {
				existsNumber = true;
			}
		}
		return existsNumber;
    }

	private void initialize(String kikanNumber) throws Exception{

		/* T_KOJIN Dao ���쐬����B */
		tKojinDao = (TKojinDao) DaoFactory.createDao(kikanDatabase.getMConnection(), new TKojin());

		/* T_NAYOSE Dao ���쐬����B */
		tNayoseDao = (TNayoseDao) DaoFactory.createDao(kikanDatabase.getMConnection(), new TNayose());

		/* T_KENSAKEKA_TOKUTEI Dao ���쐬����B */
		tTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(kikanDatabase.getMConnection(),new TKensakekaTokutei());

		/* T_KENSAKEKA_SONOTA Dao ���쐬����B */
		tSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(kikanDatabase.getMConnection(),new TKensakekaSonota());
	}

	// add ver2 s.inoue 2009/07/25 replace all
	private void registerfixedNayose(){

		ArrayList<Hashtable<String, String>> resultKanaName = null;
		ArrayList<Hashtable<String, String>> resultUketukeID = null;

		Hashtable<String, String> targetItem = null;

		try {
			kikanDatabase.Transaction();

			StringBuilder query = new StringBuilder();

			query.append("select kojin.uketuke_id,kojin.kananame,kojin.birthday,kojin.sex from T_KOJIN kojin,");
			query.append("(select count (kananame) cnt,kananame,birthday,sex from T_KOJIN group by kananame,birthday,sex having count (kananame)  > 1) cnttbl ");
			query.append(" where kojin.kananame = cnttbl.kananame ");
			query.append(" and kojin.birthday = cntTbl.birthday ");
			query.append(" and kojin.sex = cntTbl.sex ");
			query.append(" and kojin.uketuke_id not in (select uketuke_id from T_NAYOSE) ");
			query.append(" order by kojin.kananame ");

			try {
				resultKanaName = kikanDatabase.sendExecuteQuery(query.toString());
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

			// ���������擾
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String curtimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			String preKananame = "";
			long newNayoseNo = 0;

			// ���񂹕␳�o�^����
			for(int i = 0 ; i < resultKanaName.size() ; i++)
			{
				Hashtable<String,String> DatabaseItem = resultKanaName.get(i);

				String curKananame =DatabaseItem.get("KANANAME");

				Long curuketukeID =Long.parseLong(DatabaseItem.get("UKETUKE_ID"));

				if (!curKananame.equals(preKananame)){

					newNayoseNo = createNayoseNo();
				}

				createNayoseRecord(newNayoseNo,curuketukeID,curtimeStamp);
				preKananame = curKananame;
			}

			kikanDatabase.Commit();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	private void registerNayose(){
		// �R�[�h�̏ꍇ
		ArrayList<Hashtable<String, String>> countResult = null;
		ArrayList<Hashtable<String, String>> kensaDateResult = null;
		Hashtable<String, String> codeResultItem;
		Hashtable<String, String> kensaDateResultItem;


		// 1.���ʃf�[�^(����e�[�u��)�̎�tID�̃J�E���g�擾
		// 2.�J�E���g���Ƀ��[�v�������s���A2���ȏ㑶�݂���΁A�����Ώ�
		// 3.��tID������T_KENSAKEKA_TOKUTEI�����f���{�����Â����Ɏ擾�i��ԌÂ����ʂ����Ɍo�N���������{�j
		// 4.���[�v�K1�����l���o�^(T_KOJIN�ˎ�tID��N�x���ɍ̔Ԃ��ĐV�K�o�^����)
		// 5.���[�v�K1�������񂹏��o�^(T_NAYOSE�˖��񂹔ԍ���N�x���ɍ̔Ԃ��ĐV�K�o�^����)
		// 6.���[�v�K2����(T_KENSAKEKA_TOKUTEI�˓���)
		// 7.���[�v�K2����(T_KENSAKEKA_SONOTA�˓���)
		try{
			kikanDatabase.Transaction();

			// ���������擾
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String curtimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			// ����.1
			String queryCount = new String("select uketuke_ID UID,COUNT(uketuke_ID) UIDCNT from T_KENSAKEKA_TOKUTEI GROUP BY UKETUKE_ID ORDER BY COUNT(uketuke_id) DESC ");
			try {
				countResult = kikanDatabase.sendExecuteQuery(queryCount);
			} catch (SQLException e) {
				kikanDatabase.rollback();
				logger.warn(e.getMessage());
			}

			// ����.2
			for (int counti = 0; counti < countResult.size(); counti++) {
				codeResultItem = countResult.get(counti);

				Integer uIDCnt = Integer.parseInt(codeResultItem.get("UIDCNT"));

				if ( uIDCnt > 1) {

					Long curuketukeID = Long.parseLong(codeResultItem.get("UID"));
					logger.info("�o�N�����Ώۃ��R�[�h:" +curuketukeID);
					String queryTokutei = new String("select KENSA_NENGAPI KDATE from T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID ="
							+JQueryConvert.queryConvert(String.valueOf(curuketukeID)) + "ORDER BY KENSA_NENGAPI");

					try {
						kensaDateResult= kikanDatabase.sendExecuteQuery(queryTokutei);
					} catch (SQLException e) {
						kikanDatabase.rollback();
						logger.warn(e.getMessage());
					}

					long newNayoseNo = 0;
					newNayoseNo = createNayoseNo();

					// ����.3
					for (int kDatei = 0; kDatei < kensaDateResult.size(); kDatei++) {
						kensaDateResultItem = kensaDateResult.get(kDatei);

						Integer kDate = Integer.parseInt(kensaDateResultItem.get("KDATE"));
						// ���񃋁[�v�͏���
						logger.info("�o�N�����Ώۃ��R�[�h-���f��:" +kDate);
						long newUketukeId = 0;

						if (kDatei == 0){
							// ����.5
							createNayoseRecord(newNayoseNo,curuketukeID,curtimeStamp);
						}else{
							newUketukeId = createUketukeNo();
							// ����.4
							createKojinRecord(curuketukeID,newUketukeId);

							// ����.5
							createNayoseRecord(newNayoseNo,newUketukeId,curtimeStamp);

							// ����.6
							updateTokuteiUketukeIDRecord(newUketukeId,curuketukeID,kDate);

							// ����.7
							updateSonotaUketukeIDRecord(newUketukeId,curuketukeID,kDate);
						}
					}
				}
			}
			kikanDatabase.Commit();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}


	// T_KENSA_TOKUTEI(�L�[�X�V)����
	private void updateTokuteiUketukeIDRecord(long newUketukeID,long curuketukeID,int kDate){
		try{
			logger.info("T_KENSA_TOKUTEI�֎�tID�X�V " +"��tID(�V):" + newUketukeID+"��tID:" +curuketukeID + "���f���{��:"+kDate );
			tTokuteiDao.updatekey(newUketukeID,curuketukeID,kDate);
			logger.info("T_KENSA_TOKUTEI�֓o�^���� ");
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
	}

	// T_KENSA_SONOTA(�L�[�X�V)����
	private void updateSonotaUketukeIDRecord(long newUketukeID,long curuketukeID,int kDate){
		try{
			logger.info("T_KENSA_SONOTA�֎�tID�X�V " +"��tID(�V):" + newUketukeID+"��tID:" +curuketukeID + "���f���{��:"+kDate );
			tSonotaDao.updatekey(newUketukeID,curuketukeID,kDate);
			logger.info("T_KENSA_SONOTA�֓o�^���� ");
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
	}

	// ����(T_NAYOSE)�o�^����
	private void createNayoseRecord(long nayoseNo,long curuketukeID,String curtimeStamp){

			/* 3.2���񂹏��o�^ */
            StringBuilder queryNayose = new StringBuilder();
            queryNayose.append("INSERT INTO T_NAYOSE (NAYOSE_NO, UKETUKE_ID, UPDATE_TIMESTAMP)");
            queryNayose.append(" VALUES ( " + JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
            queryNayose.append( JQueryConvert.queryConvertAppendComma(String.valueOf(curuketukeID)) );
            queryNayose.append( JQueryConvert.queryConvert(String.valueOf(curtimeStamp)) );
            queryNayose.append(")");

            try {
    			logger.info("T_NAYOSE�֓o�^ " +"����NO(�V):" + nayoseNo+"��tID:" +curuketukeID + "�X�V��:"+curtimeStamp );
				kikanDatabase.sendNoResultQuery(queryNayose.toString());
				logger.info("T_NAYOSE�֓o�^���� ");
			} catch (SQLException e) {
				logger.error(e.getMessage());
				try{
					kikanDatabase.rollback();
				} catch (SQLException ex) {
					logger.error(ex.getMessage());
				}
			}
	}

	// ���������̌l���o�^
	private void createKojinRecord(long curuketukeID,long newuketukeID){

			StringBuilder queryKojin = new StringBuilder();
			queryKojin.append("INSERT INTO T_KOJIN ( PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU,");
			queryKojin.append(" HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX,HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, ");
			queryKojin.append(" FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, ");
			queryKojin.append(" MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, ");
			queryKojin.append(" NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ) ");
			queryKojin.append(" SELECT ");
			queryKojin.append(" PTNUM, JYUSHIN_SEIRI_NO ,YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME,");
			queryKojin.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, ");
			queryKojin.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, ");
			queryKojin.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, ");
			queryKojin.append(JQueryConvert.queryConvert(String.valueOf(newuketukeID)));
			queryKojin.append(" ,MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ");
			queryKojin.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
			queryKojin.append(JQueryConvert.queryConvert(String.valueOf(curuketukeID)));

			try {
    			logger.info("T_KOJIN�֓o�^ " +"��tID(�V):" + newuketukeID+"��tID:" +curuketukeID );
				kikanDatabase.sendNoResultQuery(queryKojin.toString());
				logger.info("T_KOJIN�֓o�^���� ");
			} catch (SQLException e) {
				try {
					logger.error(e.getMessage());
					kikanDatabase.rollback();
				} catch(SQLException ex){
					logger.error(ex.getMessage());
				}
			}
	}
	// ���񂹔ԍ��̔�
	private long createNayoseNo(){

		// �����R�s�[���A���񂹔ԍ��̔�
		long nayoseNo = -1L;
		try {
			nayoseNo = tNayoseDao.selectNewNayoseNo();
			logger.info("���񂹔ԍ��̔�:" +nayoseNo);
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return nayoseNo;
	}

	// ��t�ԍ��̔�
	private long createUketukeNo(){

		// �����R�s�[���A��tID�̔�
		long uketukeId = -1L;
		try {
			uketukeId = tKojinDao.selectNewUketukeId();
			logger.info("���񂹔ԍ��̔�:" +uketukeId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return uketukeId;
	}

	// del ver2 s.inoue 2009/07/26
//	/**
//	 * �����܂������ɂ�閼�񂹏���
//	 * @param kanaName �J�i����
//	 */
//	public void setKojinDataFromShimeiKana(String kanaName) {
//
//		ArrayList<Hashtable<String, String>> resultKanaName = null;
//		Hashtable<String, String> targetItem = null;
//
//		String Query = "SELECT PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, " +
//			"HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI," +
//			" HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO," +
//			" MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU," +
//			" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA," +
//			" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC"
//			+" FROM T_KOJIN WHERE KANANAME CONTAINING " + JQueryConvert.queryConvert(kanaName);
//
//		try {
//			resultKanaName = kikanDatabase.sendExecuteQuery(Query);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//
//		// DB�擾�f�[�^���烊�X�g�\���p�A�C�e���֊i�[
//		Vector<JSelectKeinenDataFrameData> sameKojinData
//			= new Vector<JSelectKeinenDataFrameData>();
//
//		for(int j = 0 ; j < resultKanaName.size() ; j++)
//		{
//			Hashtable<String,String> DatabaseItem = resultKanaName.get(j);
//
//			//���p�J�^�J�i�A�󔒂�����������ԂŔ�r
//			String Name = JEraseSpace.EraceSpace(DatabaseItem.get("KANANAME"));
//			JSelectKeinenDataFrameData DataSame = new JSelectKeinenDataFrameData();
//
//			DataSame.uketukeNo = DatabaseItem.get("UKETUKE_ID");
//			DataSame.jusinSeiNo = DatabaseItem.get("JYUSHIN_SEIRI_NO");
//			DataSame.name = DatabaseItem.get("NAME");
//			DataSame.kanaShimei = DatabaseItem.get("KANANAME");
//			DataSame.seiNenGapi = DatabaseItem.get("BIRTHDAY");
//			DataSame.seiBetu = DatabaseItem.get("SEX");
//			DataSame.DatabaseItem = DatabaseItem;
//			sameKojinData.add(DataSame);
//			targetItem = DatabaseItem;
//		}
//
//
//		//���������E���ꐶ�N�����̎�f�҂����������ꍇ�̏���
//		if(sameKojinData.size() > 1)
//		{
//			JSelectKeinenDataFrameCtrl SelectKojinFrame
//				= new JSelectKeinenDataFrameCtrl(sameKojinData,this);
//			JSelectKeinenDataFrameData selectedData = SelectKojinFrame.GetSelectedData();
//
//			if(selectedData == null)
//			{
//				targetItem=null;
//			}else{
//				targetItem = selectedData.DatabaseItem;
//			}
//		}
//
//	}

}
