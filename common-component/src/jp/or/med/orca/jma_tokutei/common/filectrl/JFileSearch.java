package jp.or.med.orca.jma_tokutei.common.filectrl;

import java.io  .*;
import java.util.*;

// ----------------------------------------------------------------------------
/**
	class::name	JFileSearch

	class::expl	�t�@�C������
*/
// ----------------------------------------------------------------------------
public class JFileSearch
{
	private Vector<String> m_vectFiles;

	private Vector<String> m_vectDirectory;

	/*
	 *  �R���X�g���N�^
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JFileSearch()
	{
		m_vectFiles     = new Vector<String>();

		m_vectDirectory = new Vector<String>();
	}
	
	/*
	 *  �f�B���N�g������������
	 *  
	 *  	@param  none
	 *  
	 *  	@return none
	 *  
	 *  	@ToDo   �������������s����O�ɂ��̃��\�b�h���Ăяo����.
	 *  
	 */
	public void initializer()
	{
		m_vectFiles    .clear();
		m_vectDirectory.clear();
	}

	/*
	 *  �f�B���N�g������
	 *
	 *		@param  �����f�B���N�g��
	 *
	 *		@return none
	 *
	 */
	public void searchDirectory( String dir )
	{
		File dirList = new File( dir );

		// ����
		File[] fileList = dirList.listFiles();

		if( fileList != null )
		{
			for( int i=0; i<fileList.length; ++i )
			{
				if( fileList[ i ].isFile() )
				{
					m_vectFiles.add( fileList[ i ].getAbsolutePath() );
				}
				else if( fileList[ i ].isDirectory() )
				{
					// �ċA����
					searchDirectory    ( fileList[ i ].getAbsolutePath() );
	
					m_vectDirectory.add( fileList[ i ].getAbsolutePath() );
				}
			}
		}
	}
	
	/*
	 *  �f�B���N�g������
	 *
	 *		@param  �����f�B���N�g��
	 *		@param  �����}�b�`
	 *
	 *		@return none
	 *
	 */
	public void searchDirectory( String dir, String match )
	{
		File dirList = new File( dir );

		// ����
		File[] fileList = dirList.listFiles();

		if( fileList != null )
		{
			for( int i=0; i<fileList.length; ++i )
			{
				if( fileList[ i ].isFile() )
				{
					String path = fileList[ i ].getName();
					
					if( path.matches( match ) )
					{
						m_vectFiles.add( fileList[ i ].getAbsolutePath() );
					}
				}
				else if( fileList[ i ].isDirectory() )
				{
					// �ċA����
					searchDirectory    ( fileList[ i ].getAbsolutePath(), match );
	
					m_vectDirectory.add( fileList[ i ].getAbsolutePath() );
				}
			}
		}
	}

	/*
	 *  �t�@�C�����X�g�擾
	 *
	 *		@param  none
	 *
	 *		@return Vector<String>
	 *
	 */
	public Vector<String> getFileList()
	{
		return m_vectFiles;
	}

	/*
	 *  �f�B���N�g�����X�g�擾
	 *
	 *		@param  none
	 *
	 *		@return Vector<String>
	 *
	 */
	public Vector<String> getDirectoryList()
	{
		return m_vectDirectory;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

