package jp.or.med.orca.jma_tokutei.common.filter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.openswing.swing.client.GenericButton;
import org.openswing.swing.table.filter.client.FilterDialog;
import org.openswing.swing.table.filter.client.FilterPanel;

/**
 * �R���X�g���N�^�Ŏw�肳�ꂽ�R���|�[�l���g���A�t�B���^�[�p�l���֒ǉ�����
 * ���킹�āA�t�B���^�[�p�l���́u�����v�Ɓu�Œ�v�{�^���̑傫�����ύX����
 */
public class SpecialFilterPanel extends WindowAdapter {
	
	/**
	 * �ǉ�����R���|�[�l���g
	 */
	private JComponent jComponent;
	
	/**
	 * �\�����Ă����ʂ̃R���|�[�l���g�Q�iGridControl#getParent().getComponents()�j
	 */
	private Component[] componentArgs;
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param jComponent		�t�B���^�[�p�l���ɒǉ�����R���|�[�l���g�i�ǉ����Ȃ��i�{�^���̑傫���̂ݕύX����j�ꍇ��null���w��j
	 * @param componentArgs		�\�����Ă����ʁiGridControl#getParent().getComponents()�j
	 */
	public SpecialFilterPanel(JComponent jComponent, Component[] componentArgs) {
		this.jComponent = jComponent;
		this.componentArgs = componentArgs;
	}
	
	
	/**
	 * �E�B���h�E���A�N�e�B�u�������ƌĂяo����܂��B 
	 * ����ʃI�[�v������A�|�b�v�A�b�v�̌����E�B���h�E���N���[�Y�����Ƃ��ɌĂяo�����
	 * 
	 * @param e
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		
		//��ʂ̉E�ɕ\�����Ă���uFilterPanel�v���擾
		FilterPanel filterPanel = getFilterPanel(componentArgs, null);
		if (filterPanel != null) {
			setSpecialFilterPanel(filterPanel);
		}
	}

	/**
	 * �E�B���h�E����A�N�e�B�u�������ƌĂяo����܂��B 
	 * ���|�b�v�A�b�v�̌����E�B���h�E���I�[�v�������Ƃ��ɌĂяo�����
	 * 
	 * @param e
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		
		//�J���Ă���uFilterDialog�v���擾
		List<FilterDialog> filterDialogList = getFilterDialog();
		if (filterDialogList.size() != 0) {
			
			//�擾�����S�uFilterDialog�v�ɑ΂��Đݒ肷��
			for (int i = 0; i < filterDialogList.size(); i++) {
				
				FilterPanel filterPanel = getFilterPanel(filterDialogList.get(i).getComponents(), null);
				if (filterPanel != null) {
					
					setSpecialFilterPanel(filterPanel);
				}
			}
		}
	}
	
	/**
	 * �J���Ă���E�B���h�E�ɁuFilterDialog�v���������ꍇ�����ԋp����
	 * 
	 * @return
	 */
	private List<FilterDialog> getFilterDialog() {
		
		//�J���Ă���S�E�B���h�E���擾
		Window[] windowArgs = Window.getWindows();
		
		//������ʂ���Ă�Window���c���Ă���ꍇ������̂ŁA��肠�����S�Ă�FilterDialog���擾����
		List<FilterDialog> resultList = new ArrayList<FilterDialog>();
		if (windowArgs != null) {
			for (int i = 0; i < windowArgs.length; i++) {
				if (windowArgs[i] instanceof FilterDialog) {
					resultList.add((FilterDialog)windowArgs[i]);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * Component[]����FilterPanel���擾����
	 * 
	 * @param componentArgs
	 * @param filterPanel
	 * @return
	 */
	private FilterPanel getFilterPanel(Component[] componentArgs, FilterPanel filterPanel) {
		
		if (filterPanel != null) {
			return filterPanel;
		}
		
		FilterPanel result = null;
		for (int i = 0; i < componentArgs.length; i++) {
			Container container = (Container)componentArgs[i];
			if (container instanceof FilterPanel) {
				result = (FilterPanel)container;
				break;
			} else if (container.getComponents().length != 0) {
				result = getFilterPanel(container.getComponents(), result);
			}
		}
		return result;
	}

	/**
	 * �t�B���^�[�p�l���ցA�R���|�[�l���g��ǉ�����
	 * ���{�^���̑傫�����ύX����
	 * 
	 * @param filterPanel
	 */
	private void setSpecialFilterPanel(FilterPanel filterPanel) {
		
		//FilterPanel�́umainPanel�v���擾
		JPanel mainPanel = (JPanel)filterPanel.getComponent(0);

		//mainPanel����utopPanel�v���擾
		JPanel topPanel = (JPanel)mainPanel.getComponent(1);
		
		//�utopPanel�v�̃R���|�[�l���g�Q���擾
		Component[] topPanelComponents = topPanel.getComponents();

		//�R���|�[�l���g�����ǉ��̏ꍇ
		if (jComponent != null && !jComponent.equals(topPanelComponents[0])) {
			
			//�擪�ɃR���|�[�l���g��ǉ�
			topPanel.add(jComponent, 0);
			
			//�t�H�[�J�X�͖���
			jComponent.setFocusable(false);
			
			//�`�F�b�N�{�b�N�X�̏ꍇ�A�e�L�X�g��ݒ肷��
			if (jComponent instanceof JCheckBox) {
				
				//���ݒ�̏ꍇ�̂ݐݒ�
				if ("".equals(((JCheckBox)jComponent).getText())) {
					((JCheckBox)jComponent).setText("������ۑ�����");
				}
			}
		}

		//�{�^���̑傫����ύX
		for (int i = 0; i < topPanelComponents.length; i++) {
			if (topPanelComponents[i] instanceof GenericButton) {
				GenericButton button = (GenericButton)topPanelComponents[i];
				button.setPreferredSize(new Dimension(35, button.getFontMetrics(button.getFont()).getHeight() + button.getMargin().top + button.getMargin().bottom + 16));
			}
		}
		
		//�ύX�𔽉f
		topPanel.validate();
		mainPanel.validate();
	}
}
