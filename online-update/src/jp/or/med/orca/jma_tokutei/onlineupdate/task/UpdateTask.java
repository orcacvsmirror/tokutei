package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 *  UpdateTask
 *  
 *    �f�[�^�X�V�^�X�N
 */
class UpdateTask {
	private static org.apache.log4j.Logger logger =
	    	Logger.getLogger(UpdateTask.class);
	
	private static final String TEMP_DIR_PATH = "work";
	//private static final String TEMP_CSV_PATH = "work/temp.csv";
	private static final String TEMP_CSV_PATH = "work" + JApplication.FILE_SEPARATOR + "temp.csv";

	private String m_strDataURL = "";
	private String m_strPreQuery = "";
	private String m_strParamQuery = "";

	/**
	 *  �R���X�g���N�^
	 *  
	 *    @param �f�[�^�X�V�O�Ɏ��s����N�G��
	 *    @param �f�[�^�X�V���Ɏ��s����N�G��
	 *    @param �f�[�^�X�V���Ɏg�p����f�[�^URL
	 *    
	 *    @return none
	 */
	public UpdateTask(String strPreQuery, String strParamQuery,
			String strDataURL) {
		m_strDataURL = strDataURL;
		m_strPreQuery = strPreQuery;
		m_strParamQuery = strParamQuery;
	}

	/**
	 *  �^�X�N���s
	 * 
	 *    @param  DB�A�N�Z�X�N���X
	 *    
	 *    @return none
	 */
	// s.inoue 20080806
	//public void runTask(DBConnect con) throws Exception {
	public void runTask(JConnection con) throws Exception {
		System.out.println("UpdateTask#runTask()");
		
		try{
			
			String strLine;
	
			if (Util.isNull(m_strParamQuery)) {
				return;
			}
	
			if (!Util.isNull(m_strDataURL)) {
				File tmpDir = new File(TEMP_DIR_PATH);
	
				if (!tmpDir.exists()) {
					tmpDir.mkdirs();
				}
	
				HttpConnecter.getFile(m_strDataURL, TEMP_CSV_PATH);
			}
	
			if (!Util.isNull(m_strPreQuery)) {
				// s.inoue 20080806
				con.exec(m_strPreQuery);
			}
	
			// �X�V�p�N�G��
			System.out.printf("con.setPrepareQuery()[%s]", m_strParamQuery);
			
			con.setPrepareQuery(m_strParamQuery);
			
			// �X�V�p�f�[�^
			BufferedReader reader = new BufferedReader(new FileReader(
					TEMP_CSV_PATH));
	
			while ((strLine = reader.readLine()) != null) {
				String[] strTmp = strLine.split("\",\"");
	
				ArrayList<String> aryList = new ArrayList<String>();
	
				for (int i = 0; i < strTmp.length; ++i) {
					if (0 == i) {
						if ((strTmp[0] != null) && (strTmp.length > 0)) {
							strTmp[0] = strTmp[0].substring(1);
						}
					}
	
					if (i == strTmp.length - 1) {
						if ((strTmp[i] != null) && (strTmp.length > 0)) {
							strTmp[i] = strTmp[i].substring(0,
									strTmp[i].length() - 1);
						}
					}
	
					String value = strTmp[i];
					aryList.add(value);
					
				}
				
				con.execPrepareQuery(aryList);
			}
	
			reader.close();
	
			// �_�E�����[�h�X�V�t�@�C���폜
			File deleteFile = new File(TEMP_CSV_PATH);
	
			if (deleteFile.exists()) {
				deleteFile.delete();
			}
		
		} catch (Exception err) {
			con.rollback();
			con.Shutdown();
			con=null;
			LastError.setErrorMessage("�V�X�e���f�[�^�x�[�X�̃f�[�^�X�V�Ɏ��s���܂���");
			logger.error("�V�X�e���f�[�^�x�[�X�̃f�[�^�X�V�Ɏ��s���܂��� " + JApplication.LINE_SEPARATOR + err.getMessage());
			
			throw err;
		}
	}
}