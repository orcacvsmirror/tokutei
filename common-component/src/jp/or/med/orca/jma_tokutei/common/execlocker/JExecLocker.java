package jp.or.med.orca.jma_tokutei.common.execlocker;

import java.io  .*;
import java.util.regex.Pattern;

import jp.or.med.orca.jma_tokutei.common.app.JPath;

// ----------------------------------------------------------------------------
/**
	class::name	JExecLocker

	class::expl	�N�������N���X
	
	Modified 2008/03/09 �ጎ
	�@�R���X�g���N�^�ɂ�郍�b�N�����p�~�B
	�@�t�@�C�������w�肵�ă��b�N���s�Ȃ��ÓI���\�b�h��ǉ��B
	�@�t�@�C�����p�^�[���i���K�\���j���w�肵�ă��b�N���s�Ȃ��ÓI���\�b�h��ǉ��B
*/
// ----------------------------------------------------------------------------
public class JExecLocker {
	
	/* Added 2008/05/14 �ጎ  */
	/* --------------------------------------------------- */
	private static File lockFile = null;
	/* --------------------------------------------------- */
	
	private JExecLocker(){
		/* �C���X�^���X�������֎~����B */
	}
	
	/**
	 * �t�@�C�������w�肵�ă��b�N���擾����B 
     */
	public static void getLockWithName(String filepath) throws Exception {
		/* Deleted 2008/05/14 �ጎ  */
		/* --------------------------------------------------- */
//		File lockFile = null;
		/* --------------------------------------------------- */
		
		FileWriter fileWriter = null;
		
		try	{
			lockFile = new File( filepath );
			
			// ��d�N���`�F�b�N
			if( lockFile.exists() )
			{
				throw new Exception();
			}
			
			// ���b�N�t�@�C���쐬
			fileWriter = new FileWriter( lockFile );
			
			fileWriter.write( 0x10 );
		}
		catch( Exception e )
		{
			throw new Exception();
		}
		finally{
			try	{
				if( fileWriter != null )
				{
					fileWriter.close();
					
					// �A�v���P�[�V�����I�����폜
					lockFile.deleteOnExit();
				}
			}
			catch( Exception e ){
				throw new Exception ();
			}
		}
	}
	
	/**
	 * �t�@�C�����̃p�^�[���i���K�\���j���w�肵�ă��b�N���擾����B 
     */
	public static void getLockWithPattern(String filepath, String pattern) throws Exception {
		
		File file = new File(".");
		File[] files = file.listFiles();
		
		if(files == null){
			throw new Exception();
		}
		
		for(int i = 0 ; files.length > i ; i++)	{
			String filename = files[i].getName();
			
			if(Pattern.matches(pattern, filename)){
				throw new Exception();
			}
		}
		
		getLockWithName(filepath);
	}

	/* Added 2008/05/14 �ጎ  */
	/* --------------------------------------------------- */
	public static File getLockFile() {
		return lockFile;
	}
	/* --------------------------------------------------- */
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

