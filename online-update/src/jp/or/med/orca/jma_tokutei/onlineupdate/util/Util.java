package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.io.File;

/**
 *  Update Utility
 *  
 *    ���[�e�B���e�B�[�N���X
 */
public class Util
{
	/**
	 * �@String �k���`�F�b�N
	 * 
	 *    @param  �]��������
	 *    
	 *    @return boolean 
	 */
	public static boolean isNull( String str )
	{
		return (str == null) || ("".equals(trim(str)));
	}
	
	/**
	 *�@ ������̐�[�I�[�����O�������폜
	 *
	 *    @param  ����������
	 *    
	 *    @return String
	 */
	public static String trim( String str )
	{
		int start = -1;
		int end   = -1;
		
		if( str == null )
		{
			return "";
		}
		
		String comp = " " + "�@" + "\n";
		
		char[] temp = str.toCharArray();
		
		for( int i = 0; i<temp.length; ++i )
		{
			if( comp.indexOf( temp[i] ) == -1 )
			{
				start = i;
				
				break;
			}
		}
		
		if( start == -1 )
		{
			return "";
		}
		
		for( int i = temp.length - 1; i >= 0; --i )
		{
			if( comp.indexOf( temp[i] ) == -1 )
			{
				end = i;
				
				break;
			}
		}
		
		return str.substring( start, end + 1 );
	}
	
	/**
	 *  �f�B���N�g���쐬
	 *  
	 *    @param  �쐬�f�B���N�g����
	 *    
	 *    @return none
	 */
	public static void makeDir( String fileName )
	{
		if( isNull( fileName ) )
		{
			return;
		}
		
	    File file = new File( fileName );
	    
	    if( file.getParent() != null )
	    {
	    	File dir = new File( file.getParent() );
	    	
	    	if( !dir.exists() )
	    	{
		    	dir.mkdirs();
	    	}
	    }
	}
}
