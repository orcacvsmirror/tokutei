package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

public class LastError
{
	static private String m_strErrorMessage = null;
	
	/**
	 *  �G���[���b�Z�[�W�ݒ�
	 *  
	 *    @param  �G���[���b�Z�[�W
	 *    
	 *    @return none
	 */
	static public void setErrorMessage( String strError )
	{
		m_strErrorMessage = strError;
	}
	
	/**
	 *  �G���[���b�Z�[�W�擾
	 * 
	 *    @param  none
	 *    
	 *    @return �G���[���b�Z�[�W
	 */
	static public String getErrorMessage()
	{
		return m_strErrorMessage;
	}
}
