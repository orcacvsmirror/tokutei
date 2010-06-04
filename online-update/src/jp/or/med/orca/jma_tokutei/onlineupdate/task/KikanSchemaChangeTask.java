package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.DBConnect;

/**
 *  SchemaChangeTask
 * 
 *    �X�L�[�}�ύX�^�X�N
 */
public class KikanSchemaChangeTask extends AbstractTask {
	// s.inoue 20080620 log����
    private static org.apache.log4j.Logger logger =
    	Logger.getLogger(KikanSchemaChangeTask.class);
    
	private Vector<String> m_vectTaskList = new Vector<String>();
	
	/**
	 *  �^�X�N�ǉ�
	 *    @param ���s�N�G��
	 *    @return none
	 */
	public void putTask(String strURL) {
		System.out.printf("KikanSchemaChangeTask#putTask: %s", strURL);
		System.out.println();
		
		m_vectTaskList.add(strURL);
	}

	/**
	 *  �^�X�N���s
	 *    @param none
	 *    @param none
	 */
	public boolean runTask() throws Exception {
		System.out.println("KikanSchemaChangeTask#runTask()");
		XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
        
        String strVersion = doc.getNodeValue("KikanSchemaVersion", "no");
        
		if (strVersion.compareTo(getVersion()) >= 0) {
			// ���ɃA�b�v�f�[�g�ς�
			return false;
		}
		
		// �p�X�擾
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

		// �f�[�^�x�[�X�ڑ�
		JConnection con = JConnection.ConnectKikanDatabase(JApplication.kikanNumber);		
		for (int n = 0; n < DBlist.length; ++n) {
			
			if( DBlist[n].isFile() && (DBlist[n].getName().equals(JApplication.kikanNumber)) )
			{
				// �X�L�[�}�X�V����
				con.Transaction();

	        	for( int p=0; p<m_vectTaskList.size(); ++p )
	        	{
	        		String strQuery = (String)m_vectTaskList.get(p );
	        		
	        		if( !Util.isNull(strQuery) )
	        		{
	        			con.exec( strQuery );
	        		}
	        	}
	        	
	        	con.Commit();
			}
		}
		
		doc.setNodeValue( "KikanSchemaVersion", "no", getVersion() );
        doc.store();
        
		logger.info("�@�փf�[�^�x�[�X�̃X�L�[�}�X�V���������܂���");

		return true;
	}
	
	/**
	 *  �Ώۃt�@�C���ݒ��ǉ�
	 *    @param  ���W���[���R�s�[��f�B���N�g��
	 *    @param  ���W���[���R�s�[��ꎞ�e�t�H���_(true:̧�ٌn,false:DB�n)
	 *    @return none
	 * @throws Exception 
	 */
	public void queryExcuteTask(JConnection consk,File dbFile)
		throws Exception {
		
		try{
		for (int a = 0; a < m_vectTaskList.size(); ++a) {
			String queryString = (String) m_vectTaskList.get(a);

			int execCount = 0;
			
			String[] querys = queryString.split(";");
			for (; execCount < querys.length; execCount++) {
				String query = querys[execCount];
				
				query = query.replaceFirst("^\\s*", "");
				query = query.replaceFirst("\\s*$", "");
				
				if (! Util.isNull(query)) {
					consk.sendNoResultQuery(query);
				}
			}
		}
		
		} catch (Exception err) {
			consk.rollback();
			err.printStackTrace();
			logger.error("�f�[�^�x�[�X�̃X�L�[�}�X�V�Ɏ��s���܂���" + JApplication.LINE_SEPARATOR + err.getMessage());
			try {
				consk.Shutdown();
			} catch (SQLException er) {
				er.printStackTrace();
				System.exit(1);
			}
		}
	}
}
