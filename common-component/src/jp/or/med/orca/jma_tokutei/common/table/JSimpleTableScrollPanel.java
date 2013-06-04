package jp.or.med.orca.jma_tokutei.common.table;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import jp.or.med.orca.jma_tokutei.common.tree.JSimpleTree;

/**
 * �X�N���[������e�[�u����z�u�����p�l��
 *
 *�@�\�[�X�R�[�h�̎��F������̂��߁A���s�ʒu�Ȃǂ��C���B
 *�@�������e�̕ύX�͖����B �@
 */
public class JSimpleTableScrollPanel extends JPanel
{
	/**
	 *  �R���X�g���N�^
	 *
	 *    @param  �ݒ�e�[�u��
	 *
	 *    @return none
	 */
	public JSimpleTableScrollPanel( JSimpleTable table )
	{
		this.setLayout( new BorderLayout() );

		// �p�l������
		JScrollPane scrollPane = new JScrollPane(
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
				);

		// �p�l���ݒ�
		this.add( scrollPane, BorderLayout.CENTER );

		// �`��I�u�W�F�N�g�ݒ�
		scrollPane.setViewportView( table );
	}

	// add s.inoue 2012/07/04
    public JSimpleTableScrollPanel(JSimpleColorTable jsimplecolortable)
    {
        setLayout(new BorderLayout());
        JScrollPane jscrollpane = new JScrollPane(20, 30);
        add(jscrollpane, "Center");
        jscrollpane.setViewportView(jsimplecolortable);
    }
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

