package jp.or.med.orca.jma_tokutei.common.csv;

import java.io  .*;
import java.util.*;

// ----------------------------------------------------------------------------
/**
	class::name	JCSVReaderStream

	class::expl	this is csv reader stream class.
*/
// ----------------------------------------------------------------------------
public class JCSVReaderStream extends JCSVReader
{
	/*
	 *  �R���X�g���N�^
	 *
	 *		@param  none
	 *
	 *		@return none
	 */
	public JCSVReaderStream()
	{
	}

	/*
	 *  �R���X�g���N�^
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JCSVReaderStream( String path ) throws IOException
	{
		openCSV( path );
	}

	/*
	 *  �b�r�u���J��
	 *
	 *		@param  �t�@�C���p�X
	 *
	 *		@return none
	 */
	public void openCSV( String path ) throws IOException
	{
//		BufferedReader reader = null;
//
//		try
//		{
//			String line;
//
//			reader = new BufferedReader( new InputStreamReader( new FileInputStream( path ), "UTF-8" ) );
//
//			do
//			{
//				line = reader.readLine();
//
//				if( line != null )
//				{
//					Vector<String> data = new Vector<String>();
//
//					int iIndex        = 0;
//					int iElementCount = 1;
//
//					for( int i=0; i<line.length(); ++i )
//					{
//						if( line.charAt( i ) == ',' )
//						{
//							iElementCount ++;
//						}
//					}
//
//					char elementBuf[] = new char[ line.length() ];
//
//					for( int i=0; i<iElementCount; ++i )
//					{
//						int iCopyPos = 0;
//
//						while( iIndex < line.length() && line.charAt( iIndex ) != ',' )
//						{
//							elementBuf[ iCopyPos ++ ] = line.charAt( iIndex ++ );
//						}
//
//						iIndex ++;
//
//						if( iCopyPos == 0 )
//						{
//							// ��v�f
//							data.add( new String() );
//						}
//						else
//						{
//							// ���v�f
//							data.add( String.copyValueOf( elementBuf, 0, iCopyPos ) );
//						}
//					}
//
//					m_vectTable.add( data );
//				}
//
//			}
//			while( line != null );
//		}
//		catch( ArrayIndexOutOfBoundsException e )
//		{
//			e.printStackTrace();
//
//			throw new IOException();
//		}
//		catch( FileNotFoundException e )
//		{
//			e.printStackTrace();
//
//			throw new IOException();
//		}
//		finally
//		{
//			try
//			{
//				if( reader != null )
//				{
//					reader.close();
//				}
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//
//				throw new IOException();
//			}
//		}
		openCSV(path,"UTF-8",',');
	}

	// eidt s.inoue 2013/11/08
	public void openCSV( String path ,String charSet,char sep) throws IOException
	{
		BufferedReader reader = null;

		try
		{
			String line;

			reader = new BufferedReader( new InputStreamReader( new FileInputStream( path ), charSet ) );

			do
			{
				line = reader.readLine();

				if( line != null )
				{
					if (line.length()<=0)
						continue;

					Vector<String> data = new Vector<String>();

					int iIndex        = 0;
					int iElementCount = 1;

					for( int i=0; i<line.length(); ++i )
					{
						// eidt s.inoue 2013/11/08
						// if( line.charAt( i ) == ',' )
						if( line.charAt( i ) == sep )
						{
							iElementCount ++;
						}
					}

					char elementBuf[] = new char[ line.length() ];

					for( int i=0; i<iElementCount; ++i )
					{
						int iCopyPos = 0;

						// eidt s.inoue 2013/11/08
						// while( iIndex < line.length() && line.charAt( iIndex ) != ',' )
						while( iIndex < line.length() && line.charAt( iIndex ) != sep )
						{
							elementBuf[ iCopyPos ++ ] = line.charAt( iIndex ++ );
						}

						iIndex ++;

						if( iCopyPos == 0 )
						{
							// ��v�f
							data.add( new String() );
						}
						else
						{
							// ���v�f
							data.add( String.copyValueOf( elementBuf, 0, iCopyPos ) );
						}
					}

					m_vectTable.add( data );
				}

			}
			while( line != null );
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			e.printStackTrace();

			throw new IOException();
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();

			throw new IOException();
		}
		finally
		{
			try
			{
				if( reader != null )
				{
					reader.close();
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();

				throw new IOException();
			}
		}
	}

	/* Modified 2008/07/23 ���  */
	/* --------------------------------------------------- */
	/*
     * ������̒u�����s��
     * @param input �����̑Ώۂ̕�����
     * @param pattern �u���O�̕�����
     * @oaram replacement �u����̕�����
     * @return �u��������̕�����
     */
    public String substitute(String input, String pattern, String replacement) {
        // �u���Ώە����񂪑��݂���ꏊ���擾
        int index = input.indexOf(pattern);

        // �u���Ώە����񂪑��݂��Ȃ���ΏI��
        if(index == -1) {
            return input;
        }

        // �������s�����߂� StringBuffer
        StringBuffer buffer = new StringBuffer();

        buffer.append(input.substring(0, index) + replacement);

        if(index + pattern.length() < input.length()) {
            // �c��̕�������ċA�I�ɒu��
            String rest = input.substring(index + pattern.length(), input.length());
            buffer.append(substitute(rest, pattern, replacement));
        }
        return buffer.toString();
    }
    /* --------------------------------------------------- */

    /* Modified 2008/07/23 ���  */
    /* --------------------------------------------------- */
    /*
	 * CSV�t�@�C��importformat�t�B�[���h�p�ϊ�
	 *		@param  quart
	 *		@return none
	 */
	public String rmQuart(String strColmn)
	{
		if (strColmn == null) {
			strColmn ="";
		}

		if (strColmn.indexOf("\"")!=-1)
        {
			strColmn = substitute(strColmn, "\"", "");
        }

		return strColmn;
	}
	/* --------------------------------------------------- */

}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

