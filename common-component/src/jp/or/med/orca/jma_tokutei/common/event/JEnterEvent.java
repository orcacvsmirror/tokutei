package jp.or.med.orca.jma_tokutei.common.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;

/**
 * �G���^�[�{�^�����������Ƃ���ActionListener�ɃC�x���g��ʒm���邽�߂̃C�x���g
 * �z�肵�Ă���g�p���@�Ƃ��ẮA�{�^���̃L�[���X�i�[�ɒǉ����āA�G���^�[�L�[��
 * �����ꂽ���Ƃ����m����B
 */
public class JEnterEvent extends KeyAdapter {
	private ActionListener ActionFrame;
	private JComponent TargetComponent;
	
	/**
	 * @param ActionFrame ActionListener���o�^����Ă���t���[��
	 * @param TargetComponent �G���^�[�L�[�������ꂽ�ƒʒm����{�^��
	 */
	public JEnterEvent(ActionListener ActionFrame, JComponent TargetComponent)
	{
		this.ActionFrame = ActionFrame;
		this.TargetComponent = TargetComponent;
	}
	
	/**
	 * �G���^�[�L�[�������ꂽ�ƒʒm����{�^���ɂ��āA�S�Ă̂��̂�ΏۂƂ��Ēʒm����B
	 * �z�肵�Ă���g�p���@�Ƃ��ẮA�{�^���̃L�[���X�i�[�ɒǉ����āA�G���^�[�L�[��
	 * �����ꂽ���Ƃ����m����B
	 * �������A���̃R���X�g���N�^���Ăяo�����ꍇ�AActionEvent�̃\�[�X�I�u�W�F�N�g�Ƃ���
	 * �C�x���g�̃\�[�X�I�u�W�F�N�g�����̂܂ܗ����悤�ɂ���B
	 * ���̂��߁A�{�^���̃L�[���X�i�[�ɒǉ����ꂽ�悪���̂܂܌Ăяo�����B
	 * @param ActionFrame ActionListener���o�^����Ă���t���[��
	 */
	public JEnterEvent(ActionListener ActionFrame)
	{
		this.ActionFrame = ActionFrame;
		TargetComponent = null;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(TargetComponent == null)
			{
				ActionFrame.actionPerformed(new ActionEvent(e.getSource(),0,""));
			}else
			{
				ActionFrame.actionPerformed(new ActionEvent(TargetComponent,0,""));
			}
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

