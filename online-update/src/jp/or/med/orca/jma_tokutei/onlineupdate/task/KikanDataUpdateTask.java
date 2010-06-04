package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.*;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;

import org.apache.log4j.Logger;

/**
 *  2008/03/10 yabu �ǉ�
 *  �g�����U�N�V�����AKIKAN�@MDB�p�̃f�[�^�X�V�N���X
 *  KikanDataUpdateTask
 *
 *    �f�[�^�X�V�^�X�N
 */

/**
 *  KikanDataUpdateTask
 *
 *    �f�[�^�X�V�^�X�N
 */
public class KikanDataUpdateTask extends AbstractTask {

	// s.inoue 20080620 log����
	// private static org.apache.log4j.Logger logger = Logger.getLogger(KikanDataUpdateTask.class);
	private ArrayList<UpdateTask> m_aryTaskList = new ArrayList<UpdateTask>();

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
		System.out.printf("KikanDataUpdateTask#putTask: %s, %s, %s", strPreQuery, strQuery, strDataURL);
		System.out.println();

		m_aryTaskList.add(new UpdateTask(strPreQuery, strQuery, strDataURL));
	}


	/**
	 *  �^�X�N���s
	 *
	 *    @param none
	 *
	 *    @param none
	 */
	public boolean runTask() throws Exception
	{

		System.out.println("KikanDataUpdateTask#runTask()");
		// 1.property.xml�Ǎ�
	    XMLDocumentUtil doc = new XMLDocumentUtil( PropertyUtil.getProperty("property.filename") );
	    String strVersion = doc.getNodeValue( "DBDataKikanVersion", "no" );

        System.out.println(m_aryTaskList);

        if( strVersion.compareTo(getVersion()) >= 0 )
        {
        	// ���ɃA�b�v�f�[�g�ς�
        	return false;
        }

        // 2.DB�t�H���_�Ǎ�
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

		// 3.���O�C���@�ւ̎擾
		for( int i=0; i<DBlist.length; ++i )
		{
			if( DBlist[ i ].isFile() && (DBlist[ i ].getName().equals(JApplication.kikanNumber)) )
			{
				String s = DBlist[ i ].getName();

				// �f�[�^�x�[�X�ڑ�
				JConnection con = JConnection.ConnectKikanDatabase(JApplication.kikanNumber);

			   try
		       {
		        	// �f�[�^�X�V����
		        	con.Transaction();

		        	for( int j=0; j<m_aryTaskList.size(); ++j )
		        	{
						UpdateTask task = (UpdateTask)m_aryTaskList.get( j );
						task.runTask( con );
		        	}

		        	con.Commit();
		        }
		        catch( Exception err )
		        {
		        	err.printStackTrace();
		        	con.rollback();
		        	// logger.warn("�f�[�^�x�[�X�̃f�[�^�X�V�Ɏ��s���܂���");
		        	LastError.setErrorMessage("�f�[�^�x�[�X�̃f�[�^�X�V�Ɏ��s���܂���");
		        	throw err;
		        }
		        finally
		        {
		        	con = null;
		        }
			}
		}

        doc.setNodeValue( "DBDataKikanVersion", "no", getVersion() );

        doc.store();

		return true;
	}

}
