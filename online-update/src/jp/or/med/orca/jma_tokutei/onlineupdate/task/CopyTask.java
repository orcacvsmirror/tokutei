package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;

/**
 *  CopyTask
 * 
 *    ���W���[���R�s�[�^�X�N
 */
class CopyTask
{
	private String m_strModuleURL     = "";
	private String m_strCopyDirectory = "";

	/**
	 *  �R���X�g���N�^
	 *  
	 *    @param  ���W���[���R�s�[��URL
	 *    @param  ���W���[���R�s�[��f�B���N�g��
	 *    
	 *    @return none
	 */
	public CopyTask( String strURL, String strDirectory )
	{
		m_strModuleURL = strURL;
		m_strCopyDirectory = strDirectory;
	}
	
	/**
	 *  �^�X�N���s
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void runTask() throws Exception
	{
		HttpConnecter.getFile( m_strModuleURL, m_strCopyDirectory );
	}
}