package jp.or.med.orca.jma_tokutei.common.table;

import java .io   .*;
import java .awt  .*;
import java .util .*;
import java .lang .*;
import javax.swing.*;
import java .awt  .event.*;
import javax.swing.table.*;
import javax.swing.event.*;

// ----------------------------------------------------------------------------
/**
	class::name	JSimpleTableCellPosition

	class::expl	�Z���|�W�V����
*/
// ----------------------------------------------------------------------------
public class JSimpleTableCellPosition
{
	private int m_iRow;
	private int m_iColumn;

	/**
	 *  �R���X�g���N�^
	 *
	 *    @param  �����s
	 *    @param  ������
	 *
	 *    @return none
	 */
	public JSimpleTableCellPosition( int iRow, int iColumn )
	{
		m_iRow    = iRow;
		m_iColumn = iColumn;
	}

	/**
	 *  �s���擾
	 *
	 *    @param  none
	 *
	 *    @return �s��
	 */
	public int getRow()
	{
		return m_iRow;
	}

	/**
	 *  �񐔎擾
	 *
	 *    @param  none
	 *
	 *    @return ��
	 */
	public int getColumn()
	{
		return m_iColumn;
	}

	/**
	 *  �s���ݒ�
	 *
	 *    @param  �s��
	 *
	 *    @return none
	 */
	public void setRow( int row )
	{
		m_iRow = row;
	}

	/**
	 *  �񐔐ݒ�
	 *
	 *    @param  ��
	 *
	 *    @return none
	 */
	public void setColumn( int column )
	{
		m_iColumn = column;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


