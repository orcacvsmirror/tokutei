package jp.or.med.orca.jma_tokutei.onlineupdate.task;

/**
 *  AbstractTask
 * 
 *    ���ۃ^�X�N
 */
public abstract class AbstractTask {
	private String m_strVersion = "";

	/**
	 *  ���s����
	 *  
	 *    @param  none
	 *  
	 *    @return �^�X�N���s���
	 */
	public boolean runTask() throws Exception {
		System.out.println("AbstractTask#runTask()");
		return true;
	}

	/**
	 *  �o�[�W�����ݒ�
	 *  
	 *    @param  �o�[�W�������
	 *    
	 *    @return none
	 */
	public void setVersion(String strVersion) {
		m_strVersion = strVersion;
	}

	/**
	 * �o�[�W�����擾
	 * 
	 *   @param  none
	 * 
	 *   @return �o�[�W�������
	 */
	public String getVersion() {
		return m_strVersion;
	}

}
