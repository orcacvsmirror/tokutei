package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 * �O���������ʃf�[�^��荞�ݎ��Ɍ��ʂ�\������t���[���̃R���g���[��
 */
public class JImportResultFrameCtrl extends JImportResultFrame
{
	/**
	 * @param Result ����
	 * @param Parent �Ăяo�����Ƃ̃t���[��
	 */
	public JImportResultFrameCtrl(String Result,JFrame Parent)
	{
		super(Parent);
		
		jTextArea_Message.setText(Result);
		
		// �G���^�[�L�[�̏���
		jButton_End.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		
		setVisible(true);
	}
	
	/**
	 * �I���{�^��
	 */
	public void pushedEndButton( ActionEvent e )
	{
		dispose();
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == jButton_End )
		{
			pushedEndButton( e );
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

