package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.csvcheck;


import java.util.*;
import java.io  .*;

import jp.or.med.orca.jma_tokutei.common.csv.*;

//----------------------------------------------------------------------------
/**
	class::name	JCSVInterface

	class::expl	this is csv interface class.
*/
// ----------------------------------------------------------------------------
public abstract class JCsvCheck
{
	private static JCSVReaderStream m_csvReader = null;

	/*
	 *  CHECK.CSV�̓Ǎ�
	 *
	 *		@param  none
	 *
	 *		@return none
	 */
	private static void loadCSV() throws IOException
	{
		if( m_csvReader == null )
		{
			m_csvReader = new JCSVReaderStream( "./CSV/CHECK.csv" );
		}
	}

	/*
	 *  ���͍��ڔ���
	 *
	 *		@param  ���ږ�
	 *		@param	�l
	 *
	 *		@return true  : ����l
	 *		@return false : �ȏ�l
	 *
	 *		@throws IOExcepiton : CSV�����݂��Ȃ������ꍇ
	 */
	public static boolean checkItem( String stItem, int iValue ) throws IOException
	{
		loadCSV();

		for( int i=1; i<m_csvReader.readAllTable().size(); ++i )
		{
			Vector<String> lineData = m_csvReader.readLineTable( i );

			// ���ڌ���
			if( lineData.get( 1 ).toString().equals( stItem ) )
			{
				int iMin = Integer.parseInt(lineData.get(2));
				int iMax = Integer.parseInt(lineData.get(3));

				if( iValue < iMin || iValue > iMax )
				{
					return false;
				}

				return true;
			}
		}

		return false;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

