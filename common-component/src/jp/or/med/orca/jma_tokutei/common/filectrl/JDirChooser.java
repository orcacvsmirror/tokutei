package jp.or.med.orca.jma_tokutei.common.filectrl;

import java .awt  .*;
import javax.swing.*;
import java .awt  .event.*;

// ----------------------------------------------------------------------------
/**
	class::name	JDirChooser

	class::expl	�f�B���N�g���I���_�C�A���O
*/
// ----------------------------------------------------------------------------
public class JDirChooser extends JFileChooser
{
	/*
	 *  �R���X�g���N�^
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JDirChooser()
	{
		setFileSelectionMode( DIRECTORIES_ONLY );
	}

	/*
	 *  �R���X�g���N�^
	 *
	 *		@param  �����f�B���N�g��
	 *
	 *		@return none
	 *
	 */
	public JDirChooser( String strDefaultPath )
	{
		super( strDefaultPath );

		setFileSelectionMode( DIRECTORIES_ONLY );
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

