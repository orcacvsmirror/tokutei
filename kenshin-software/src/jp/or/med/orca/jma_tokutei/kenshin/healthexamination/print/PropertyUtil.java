package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.io  .*;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

/**
 *  PropertyUtil
 *
 *    �ݒ�t�@�C������N���X
 *    
 *    Modified 2008/07/25 �ጎ
 *    "file.properties" �ȊO�̃t�@�C���ł��ǂݍ��߂�悤�A�d�l��ύX�B
 */
public class PropertyUtil{
	/* Modified 2008/07/25 �ጎ  */
	/* --------------------------------------------------- */
//	private final static String m_strPropertyFileName = "file.properties";
	/* --------------------------------------------------- */
//	public final static String PATH_FILE_PROPERTIES = "file.properties";
//	public final static String PATH_PRINT_PROPERTIES = "print.properties";
	/* --------------------------------------------------- */
	
	private String filePath = null;
	
	public PropertyUtil(String filePath) {
		this.setFilePath(filePath);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 *  �ݒ�l�擾
	 *
	 *    @param  �L�[
	 *
	 *    @return VALUE
	 */
	/* Modified 2008/07/25 �ጎ  */
	/* --------------------------------------------------- */
//	public static String getProperty( String strKey )
//	{
//		Properties prop = new Properties();
//		
//		try
//		{
//			prop.load( new FileInputStream(m_strPropertyFileName) );
//		}
//		catch( IOException err )
//		{
//			err.printStackTrace();
//			
//        	LastError.setErrorMessage("�ݒ�l \"" + strKey + "\" �̐ݒ�Ɏ��s���܂���");
//			
//			return "";
//		}
//		
//		return prop.getProperty( strKey );
//	}
	/* --------------------------------------------------- */
	public String getProperty( String strKey )
	{
		if (this.filePath == null || this.filePath.isEmpty()) {
			/* �G���[���b�Z�[�W�FM0003=�G���[,�t�@�C�������ݒ肳��Ă��܂���B,0,0 */
			JErrorMessage.show("M0003", null);
			return null;
		}
		
		String retValue = "";
		
		Properties prop = new Properties();
		
		FileInputStream fileInputStream = null;
		try
		{
			fileInputStream = new FileInputStream(this.filePath);
			prop.load( fileInputStream );
			retValue = prop.getProperty( strKey );
		}
		catch( IOException err ){
			err.printStackTrace();
        	LastError.setErrorMessage("�ݒ�l \"" + strKey + "\" �̐ݒ�Ɏ��s���܂���");
		}
		finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return retValue;
	}
	/* --------------------------------------------------- */
	
	/**
	 *  �ݒ�l�擾
	 *
	 *    @param  �L�[
	 *    @param  �l
	 *
	 *    @return none
	 */
	/* Modified 2008/07/25 �ጎ  */
	/* --------------------------------------------------- */
//	public static void setProperty( String strKey, String strValue )
//	{
//		Properties prop = new Properties();
//		
//		try
//		{
//			prop.load ( new FileInputStream (m_strPropertyFileName) );
//			
//			prop.setProperty( strKey, strValue );
//			
//			prop.store( new FileOutputStream(m_strPropertyFileName), m_strPropertyFileName );
//		}
//		catch( IOException err )
//		{                                                            
//			err.printStackTrace();
//			
//        	LastError.setErrorMessage("�ݒ�l \"" + strKey + "\" �̎擾�Ɏ��s���܂���");
//		}
//	}
	/* --------------------------------------------------- */
	public void setProperty( String strKey, String strValue )
	{
		if (this.filePath == null || this.filePath.isEmpty()) {
			/* �G���[���b�Z�[�W�FM0003=�G���[,�t�@�C�������ݒ肳��Ă��܂���B,0,0 */
			JErrorMessage.show("M0003", null);
			return;
		}

		Properties prop = new Properties();
		
		/* Modified 2008/07/25 �ጎ  */
		/* --------------------------------------------------- */
//		try
//		{
//			prop.load ( new FileInputStream (this.filePath) );
//			
//			prop.setProperty( strKey, strValue );
//			
//			prop.store( new FileOutputStream(this.filePath), this.filePath );
//		}
//		catch( IOException err )
//		{                                                            
//			err.printStackTrace();
//			
//        	LastError.setErrorMessage("�ݒ�l \"" + strKey + "\" �̎擾�Ɏ��s���܂���");
//		}
		/* --------------------------------------------------- */
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		try
		{
			fileInputStream = new FileInputStream (this.filePath);
			prop.load ( fileInputStream );
			
			prop.setProperty( strKey, strValue );
			
			fileOutputStream = new FileOutputStream(this.filePath);
			prop.store( fileOutputStream, this.filePath );
		}
		catch( IOException err )
		{                                                            
			err.printStackTrace();
			
        	LastError.setErrorMessage("�ݒ�l \"" + strKey + "\" �̎擾�Ɏ��s���܂���");
		}
		finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* --------------------------------------------------- */
	}
	/* --------------------------------------------------- */
}
