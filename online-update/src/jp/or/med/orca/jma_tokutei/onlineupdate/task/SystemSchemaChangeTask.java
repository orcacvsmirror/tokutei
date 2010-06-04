package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.File;
import java.util.Vector;
import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.DBConnect;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.LastError;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.Util;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.XMLDocumentUtil;

/**
 *  SchemaChangeTask
 * 
 *    �X�L�[�}�ύX�^�X�N
 */
public class SystemSchemaChangeTask extends AbstractTask {
	// s.inoue 20080620
    private static org.apache.log4j.Logger logger =
    	Logger.getLogger(SystemSchemaChangeTask.class);

	private Vector<String> m_vectTaskList = new Vector<String>();
	private static final String DB_FILENAME_SYSTEM_FDB = "System";
	
	/**
	 *  �^�X�N�ǉ�
	 *    @param ���s�N�G��
	 *    @return none
	 */
	public void putTask(String strURL) {
		System.out.printf("Schema	ChangeTask#putTask: %s", strURL);
		System.out.println();

		m_vectTaskList.add(strURL);
	}

	/**
	 *  �^�X�N���s
	 *    @param none
	 *    @param none
	 */
	public boolean runTask() throws Exception {
		System.out.println("SchemaChangeTask#runTask()");
        
		XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
        String strVersion = doc.getNodeValue("SchemaVersion", "no");
        
        if( strVersion.compareTo(getVersion()) >= 0 )
        {
        	// ���ɃA�b�v�f�[�g�ς�
        	return false;
        }
        
		// s.inoue 20080730
		File f = new File(".");
		String getRef = f.getCanonicalPath();
		String dbPath =JApplication.systemDBPath;
		dbPath = dbPath.replace(getRef+JApplication.FILE_SEPARATOR, "");
		
		File directory = new File(dbPath);
		File[] DBlist = directory.listFiles();

		if (DBlist == null) {
			LastError.setErrorMessage("�f�[�^�x�[�X�t�@�C����������܂���ł���");

			throw new Exception();
		}
		
		//SYSTEM.FDB�̏ꍇ
		for (int i = 0; i < DBlist.length; ++i) {
			File dbFile = DBlist[i];
			
			String fileName = dbFile.getName();

			if (dbFile.isFile())
			{
				// system.fdb�̍X�V����
				if (fileName.equals(DB_FILENAME_SYSTEM_FDB + JPath.DATA_BASE_EXTENSION))
				{
					JConnection consk = JConnection.ConnectSystemDatabase();
					
					try {
						
						consk.Transaction();
						
						// �o�^ ���ۂ̃R�s�[���� 
						/* Task ���Ƃ̏��� */
						for (int j = 0; j < m_vectTaskList.size(); ++j) {
							String queryString = (String) m_vectTaskList.get(j);

							String[] querys = queryString.split(";");
							for (int k = 0; k < querys.length; k++) {
								String query = querys[k];
								if (!Util.isNull(query)) {
									consk.exec(query);
								}
							}
						}
						
						consk.Commit();
						
						} catch (Exception err) {
							
							LastError.setErrorMessage("�V�X�e���f�[�^�x�[�X�̃X�L�[�}�X�V�Ɏ��s���܂���");
							logger.warn("�V�X�e���f�[�^�x�[�X�̃X�L�[�}�X�V�Ɏ��s���܂���" + JApplication.LINE_SEPARATOR + err.getMessage());
							throw err;
						} finally {
							if (consk != null)
							consk.Shutdown();						
						}
				}
				
			}
		}				

		doc.setNodeValue("SchemaVersion", "no", getVersion());
		doc.store();
 
		logger.info("�V�X�e���f�[�^�x�[�X�̃X�L�[�}�X�V���������܂���");
		return true;
	}
}
