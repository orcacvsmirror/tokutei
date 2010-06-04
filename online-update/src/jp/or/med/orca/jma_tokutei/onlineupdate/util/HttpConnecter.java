package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

/**
 *  HttpConnecter
 *  
 *    HTTP�R�l�N�V�����N���X
 */
public class HttpConnecter
{
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(HttpConnecter.class);
	/**
	 *  �v���L�V�T�[�o�ݒ�
	 *  
	 *    @param  �v���L�V�T�[�o�[��
	 *    
	 *    @return none
	 */
	public static void setProxyHost( String host )
	{
		System.setProperty( "http.proxyHost", PropertyUtil.getProperty("http.proxyHost") );
	}
	
	/**
	 *�@�@�v���L�V�|�[�g�ݒ�
	 *
	 *    @param  �|�[�g�ԍ�
	 *    
	 *    @return none
	 */
	public static void setProxyPort( String port )
	{
		System.setProperty( "http.proxyPort", PropertyUtil.getProperty("http.proxyPort") );
	}
	
	/**
	 *  �v���L�V���O�T�[�o�ݒ�
	 *
	 *    @param  �v���L�V���O�T�[�o�ݒ�
	 *    
	 *    @return none
	 */
	public static void setNonProxyHosts( String host )
	{
		System.setProperty( "http.nonProxyHosts", PropertyUtil.getProperty("http.nonProxyHosts") );
	}
	
	/**
	 *  �t�@�C���_�E�����[�h
	 *  
	 *    @param  �_�E�����[�hURL
	 *    @param  �_�E�����[�h�f�B���N�g��
	 *    
	 *    @return none
	 */
	public static void getFile( String strURL, String strDest ) throws Exception
	{
		int i;
		
	    InputStream  in  = null;
	    OutputStream out = null;
	    
	    if( Util.isNull(strURL) )
	    {
	    	LastError.setErrorMessage("Unable to found download URL !!");
	    	
	    	return;
	    }
	    
	    if( Util.isNull(strDest) )
	    {
	    	LastError.setErrorMessage("Unable to found save file name !!");
	    	
	    	return;
	    }
	    
	    // ��ƃf�B���N�g������
	    Util.makeDir( strDest );
	    
	    try
	    {
	    	byte[] byteBuf = new byte[ 0x2000 ];
	    	
	    	// �_�E�����[�h��擾
	    	URL url = new URL( strURL );
	    	
	    	in  = url.openStream();
	    	out = new FileOutputStream( strDest );
	    	
	    	while( (i = in.read( byteBuf )) != -1 )
	    	{
	    		out.write( byteBuf, 0, i );
	    	}
	    }
	    catch( FileNotFoundException err )
	    {
        	LastError.setErrorMessage("\""+ strDest + "\"" + " �̃_�E�����[�h�Ɏ��s���܂���");
        	logger.error("\""+ strDest + "\"" + " �̃_�E�����[�h�Ɏ��s���܂���");
	    	throw err;
	    }
	    catch( IOException err )
	    {
        	LastError.setErrorMessage("\""+ strDest + "\"" + " �̃_�E�����[�h�Ɏ��s���܂���");
        	logger.error("\""+ strDest + "\"" + " �̃_�E�����[�h�Ɏ��s���܂���");
        	
	    	throw err;
	    }
	    finally
	    {
	        try
	        {
	            if( in  != null )
	            {
	                in.close();
	            }
	            
	            if( out != null )
	            {
	                out.flush();
	                out.close();
	            }
	        }
	        catch( Exception err )
	        {
	        	err.printStackTrace();
	        }
	    }
	}
}
