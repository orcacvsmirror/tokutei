package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;

/**
 *  DateUpdateTask
 * 
 *    �f�[�^�X�V�^�X�N
 */
public class SystemDataUpdateTask extends AbstractTask {
	// s.inoue 20080620
    private static org.apache.log4j.Logger logger =
    	Logger.getLogger(SystemDataUpdateTask.class);
    
	private ArrayList<UpdateTask> m_aryTaskList = new ArrayList<UpdateTask>();

	private static final String DB_FILENAME_SYSTEM_FDB = "System";

	/**
	 *  �^�X�N�ǉ�
	 *  
	 *    @param �f�[�^�X�V�O�Ɏ��s����N�G��
	 *    @param �f�[�^�X�V���Ɏ��s����N�G��
	 *    @param �f�[�^�X�V���Ɏg�p����f�[�^URL
	 *    
	 *    @return none
	 */
	public void putTask(String strPreQuery, String strQuery, String strDataURL) {
		System.out.printf("DataUpdateTask#putTask: %s, %s, %s", strPreQuery, strQuery, strDataURL);
		System.out.println();
		
		m_aryTaskList.add(new UpdateTask(strPreQuery, strQuery, strDataURL));
	}

	/**
	 *  �^�X�N���s
	 *    @param none
	 *    @param none
	 */
	public boolean runTask() throws Exception {
		System.out.println("DataUpdateTask#runTask()");
		
		XMLDocumentUtil doc = new XMLDocumentUtil( PropertyUtil.getProperty("property.filename") );
        String strVersion = doc.getNodeValue( "DBDataVersion", "no" );
		
        if (strVersion.compareTo(getVersion()) >= 0) {
			// ���ɃA�b�v�f�[�g�ς�
			return false;
		}
		
		// s.inoue 20080730
		File f = new File(".");
		String getRef = f.getCanonicalPath();
		String dbPath =JApplication.systemDBPath;
		dbPath = dbPath.replace(getRef+JApplication.FILE_SEPARATOR, "");
		File directory = new File(dbPath);

		File[] dbList = directory.listFiles();

		if (dbList == null) {
			LastError.setErrorMessage("�f�[�^�x�[�X�t�@�C����������܂���ł���");

			throw new Exception();
		}

		for (int i = 0; i < dbList.length; ++i) {
			File dbFile = dbList[i];
			String fileName = dbFile.getName();
			
			
			if (dbFile.isFile())
			{
				// system.fdb�̍X�V����
				if (fileName.equals(DB_FILENAME_SYSTEM_FDB + JPath.DATA_BASE_EXTENSION))
				{
					JConnection con = JConnection.ConnectSystemDatabase();
					
					try {
						
						con.Transaction();

						// �o�^�@���ۂ̃R�s�[���� 
						for (int j = 0; j < m_aryTaskList.size(); ++j) {
							UpdateTask task = (UpdateTask) m_aryTaskList.get(j);

							task.runTask(con);
						}
						
						con.Commit();
						
						} catch (Exception err) {
							
							LastError.setErrorMessage("�V�X�e���f�[�^�x�[�X�̃f�[�^�X�V�Ɏ��s���܂���");
							logger.warn("�V�X�e���f�[�^�x�[�X�̃f�[�^�̃f�[�^�X�V�Ɏ��s���܂��� " + JApplication.LINE_SEPARATOR + err.getMessage());
							throw err;
						} finally {
							if (con != null)
							con.Shutdown();						
						}
				}
			}
		}

		logger.info("�V�X�e���f�[�^�x�[�X�̃f�[�^�X�V���������܂���");
		
		return true;
	}
}
