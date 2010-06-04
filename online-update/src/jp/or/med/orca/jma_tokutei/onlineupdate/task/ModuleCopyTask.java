package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.File;
import java.util.*;
import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;

/**
 *  ModuleCopyTask
 *   
 *    ���W���[���X�V�^�X�N
 */
public class ModuleCopyTask extends AbstractTask {
	// s.inoue 20080620 log����
    private static org.apache.log4j.Logger logger =
    	Logger.getLogger(ModuleCopyTask.class);
    
	private ArrayList<CopyTask> m_aryTaskList = new ArrayList<CopyTask>();
	
	/**
	 *  �^�X�N�ǉ�
	 *    @param  �擾��A�h���X
	 *    @param  �ۑ���f�B���N�g��
	 *    @return none
	 */
	public void putTask(String strURL, String strCopyDir) {
		System.out.printf("ModuleCopyTask#putTask: %s, %s", strURL, strCopyDir);
		System.out.println();
		
		m_aryTaskList.add(new CopyTask(strURL, strCopyDir));
	}
	
	/**
	 *  �^�X�N���s
	 *    @param  none
	 *    @return none
	 */
	public boolean runTask() throws Exception {
		System.out.println("ModuleCopyTask#runTask()");

		try {
			 
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			String strVersion = doc.getNodeValue("ModuleVersion", "no");
			
			if (strVersion.compareTo(getVersion()) >= 0) {
				// ���ɃA�b�v�f�[�g�ς�
				return false;
			}
			
			// ���ۂ̃R�s�[���� 
			for (int i = 0; i < m_aryTaskList.size(); ++i) {
				CopyTask task = (CopyTask) m_aryTaskList.get(i);
	
				task.runTask();
			}
			
			doc.setNodeValue("ModuleVersion", "no", getVersion());
			doc.store();
			logger.info("�t�@�C��(.xml,.jar��)�X�V������Ɋ������܂���");
			
		}catch(Exception err){
			
			LastError.setErrorMessage("�t�@�C��(.xml,.jar��)�X�V�Ɏ��s���܂���");
			logger.error("�t�@�C��(.xml,.jar��)�X�V�Ɏ��s���܂���" + JApplication.LINE_SEPARATOR + err.getMessage());
			throw err;
		}

		return true;
	}
}