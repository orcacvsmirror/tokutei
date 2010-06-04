package jp.or.med.orca.jma_tokutei.common.filectrl;

import java.io  .*;
import java.util.*;
import java.nio .channels.*;

import org.apache.log4j.Logger;

// ----------------------------------------------------------------------------
/**
	class::name	JFileCopy

	class::expl	�t�@�C���R�s�[
*/
// ----------------------------------------------------------------------------
public abstract class JFileCopy
{
	// s.inoue 20080807
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JFileCopy.class);

	/*
	 *  �t�@�C���R�s�[
	 *
	 *		@param  �R�s�[��
	 *		@param  �R�s�[��
	 *
	 *		@return none
	 */
	public static boolean copyFile( String strIn, String strOut ) throws IOException
	{

        FileChannel in  = null;
        FileChannel out = null;

        /* Added 2008/06/13 �ጎ  */
		/* --------------------------------------------------- */
		/* �R�s�[��̃t�H���_���쐬����B */
        /* edit 2008/08/08 s.inoue �t�H���_�łȂ��ꍇ���*/
        if (strOut.indexOf("\\") > 0){
        	/* Modified 2008/09/11 ��  */
        	/* --------------------------------------------------- */
//	        String outDirPath = strOut.substring(0, strOut.lastIndexOf("\\"));
	        String outDirPath = strOut.substring(0, strOut.lastIndexOf(File.separator));
        	/* --------------------------------------------------- */
	        File outDir = new File(outDirPath);
	        if (! outDir.exists()) {
	        	if (outDir.mkdirs()) {
	            	return false;
				}
			}
        }
        /* --------------------------------------------------- */

		try
		{
			// logger.info( "�R�s�[��:" + strIn + " " + "�R�s�[��:" + strOut + "�փR�s�[�������J�n���܂����B");
			in  = new FileInputStream ( strIn  ).getChannel();
			out = new FileOutputStream( strOut ).getChannel();

			out.transferFrom( in, 0, in.size() );
			// logger.info( "�R�s�[��:" + strIn + " " + "�R�s�[��:" + strOut + "�փR�s�[�������s���܂����B");
		}
		catch( IOException e )
		{
			e.printStackTrace();
			logger.error("�o�b�N�A�b�v�����y�у��[���o�b�N�����̃t�@�C���R�s�[���A�G���[�ƂȂ�܂����B");

			return false;
		}
		finally
		{
			try
			{
				in .close();
				out.close();
			}
			catch( IOException e )
			{
				e.printStackTrace();
				logger.error("�o�b�N�A�b�v�����y�у��[���o�b�N�����̃t�@�C���R�s�[���A�G���[�ƂȂ�܂����B");
				return false;
			}
		}

		return true;
	}

	/*
	 *  �t�@�C���G�b�N�X�R�s�[
	 *
	 *		@param  �R�s�[�� �f�B���N�g���i���� / ��΁j
	 *		@param	�R�s�[�� �f�B���N�g���i���� / ��΁j
	 *
	 *		@return 0x00000000 : ����
	 *		@return 0x00000001 : ���[�g�f�B���N�g�������݂��Ȃ�.
	 *		@return 0x00000010 : �R�s�[�f�B���N�g���̍쐬�Ɏ��s.
	 *		@return 0x00000100 : �t�@�C�����X�g�̍쐬�Ɏ��s.
	 *		@return 0x00001000 : �t�@�C���R�s�[�̎��s�Ɏ��s.
	 *		@return 0x11111111 : �v���I�G���[.
	 *
	 *		@note : �t�@�C���R�s�[�ɓr���Ŏ��s���Ă������𑱍s���܂�.
	 */
	public static int xcopyFile( String inDir, String outDir )
	{
		int iResult = 0x00000000;

		try
		{
			Vector<String> aryDirList  = new Vector<String>();
			Vector<String> arySrcList  = new Vector<String>();
			Vector<String> aryDestList = new Vector<String>();

			// ���X�g�쐬
			createDirectoryList( inDir, outDir, aryDirList, arySrcList, aryDestList );

			// �f�B���N�g���`�F�b�N
			File copyDir = new File( outDir );

			if( !copyDir.exists() )
			{
				if( !copyDir.mkdirs() )
				{
					iResult |= 0x00000001;
				}
			}

			// �f�B���N�g���\���쐬
			for( int i=0; i<aryDirList.size(); ++i )
			{
				File createDir = new File( aryDirList.get( i ) );

				if( !createDir.exists() )
				{
					if( !createDir.mkdirs() )
					{
						iResult |= 0x00000010;
					}
				}
			}

			// �t�@�C���R�s�[
			if( arySrcList.size() == aryDestList.size() )
			{
				for( int i=0; i<aryDestList.size(); ++i )
				{
					try
					{
						if( !copyFile( arySrcList.get( i ), aryDestList.get( i ) ) )
						{
							iResult |= 0x00001000;
						}
					}
					catch( IOException e )
					{
						e.printStackTrace();
						logger.error("�o�b�N�A�b�v�����y�у��[���o�b�N�����̃t�@�C���R�s�[���A�G���[�ƂȂ�܂����B");
						iResult |= 0x00001000;
					}
				}
			}
			else
			{
				iResult |= 0x00000100;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("�o�b�N�A�b�v�����y�у��[���o�b�N�����̃t�@�C���R�s�[���A�G���[�ƂȂ�܂����B");
			return 0x11111111;
		}

		return iResult;
	}

	/*
	 *  �t�@�C�����X�g�쐬
	 *
	 *		@param  ...
	 *
	 *		@return ...
	 */
	private static void createDirectoryList( String strIn, String strOut, Vector<String> aryDirList, Vector<String> arySrcList, Vector<String> aryDestList )
	{
		File dirList = new File( strIn );

		File[] fileList = dirList.listFiles();

		for( int i=0; i<fileList.length; ++ i )
		{
			if( fileList[ i ].isFile() )
			{
				// �R�s�[���t�@�C��
				arySrcList .add( fileList[ i ].getAbsolutePath() );

				// �R�s�[��t�@�C��
				aryDestList.add( strOut + System.getProperty("file.separator") + fileList[ i ].getName() );
			}
			else if( fileList[ i ].isDirectory() )
			{
				String strTemp = strOut + System.getProperty("file.separator") + fileList[ i ].getName();

				// �R�s�[��f�B���N�g��
				aryDirList .add( strTemp );

				// �ċA����
				createDirectoryList( fileList[ i ].getAbsolutePath(), strTemp, aryDirList, arySrcList, aryDestList );
			}
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

