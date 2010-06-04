package jp.or.med.orca.jma_tokutei.common.csv;

import java.io  .*;
import java.util.*;

// ----------------------------------------------------------------------------
/**
	class::name	JCSVReader

	class::expl	this is csv reader class.
*/
// ----------------------------------------------------------------------------
abstract class JCSVReader extends JCSVInterface
{
	/*
	 *	�Ǘ��e�[�u�����擾
	 *
	 *		@param	none
	 *
	 *		@return	Vector
	 */
	public Vector<Vector<String>> readAllTable()
	{
		return m_vectTable;
	}

	/*
	 *	�v�f�e�[�u�����擾
	 *
	 *		@param	none
	 *
	 *		@return	Vector �擾����
	 *		@return null   �擾���s : out of array index.
	 */
	public Vector<String> readLineTable( int iLine )
	{
		if( iLine < 0 || iLine >= m_vectTable.size() )
		{
			return null;
		}

		return m_vectTable.get( iLine );
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

