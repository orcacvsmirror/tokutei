package jp.or.med.orca.jma_tokutei.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
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
			
		}
	}
}
