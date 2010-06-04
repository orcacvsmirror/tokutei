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
	class::name	JSimpleTableCellRendererData

	class::expl	�e�[�u���J�X�^�������_������N���X
*/
// ----------------------------------------------------------------------------
public class JSimpleTableCellRendererData
{
	private Color m_color;

	private JSimpleTableCellPosition m_cell;

	/**
	 *  �R���X�g���N�^
	 *
	 *    @param  �Z���F
	 *    @param  �Z���ʒu
	 *
	 *    @return null
	 */
	public JSimpleTableCellRendererData( Color color, JSimpleTableCellPosition cell )
	{
		m_color = color;

		m_cell  = cell;
	}

	/**
	 *  �F�擾
	 *
	 *    @param  none
	 *
	 *    @return Color
	 */
	public Color getColor()
	{
		return m_color;
	}

	/**
	 *  �ʒu�擾
	 *
	 *    @param  none
	 *
	 *    @return JSimpleTableCellPosition
	 */
	public JSimpleTableCellPosition getCellPosition()
	{
		return m_cell;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

