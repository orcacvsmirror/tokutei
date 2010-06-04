package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.io  .*;
import java.util.*;

/**
 *  PropertyUtil
 *
 *    �ݒ�t�@�C������N���X
 */
public class PropertyUtil
{
	private final static String m_strPropertyFileName = "application.properties";
	
	/**
	 *  �ݒ�l�擾
	 *
	 *    @param  �L�[
	 *
	 *    @return VALUE
	 */
	public static String getProperty( String strKey )
	{
		Properties prop = new Properties();
		
		try
		{
			prop.load( new FileInputStream(m_strPropertyFileName) );
		}
		catch( IOException err )
		{
			err.printStackTrace();
			
        	LastError.setErrorMessage("�ݒ�l \"" + strKey + "\" �̐ݒ�Ɏ��s���܂���");
			
			return "";
		}
		
		return prop.getProperty( strKey );
	}
	
	/**
	 *  �ݒ�l�擾
	 *
	 *    @param  �L�[
	 *    @param  �l
	 *
	 *    @return none
	 */
	public static void setProperty( String strKey, String strValue )
	{
		Properties prop = new Properties();
		
		try
		{
			prop.load ( new FileInputStream (m_strPropertyFileName) );
			
			prop.setProperty( strKey, strValue );
			
			prop.store( new FileOutputStream(m_strPropertyFileName), m_strPropertyFileName );
		}
		catch( IOException err )
		{                                                            
			err.printStackTrace();
			
        	LastError.setErrorMessage("�ݒ�l \"" + strKey + "\" �̎擾�Ɏ��s���܂���");
		}
	}
}
