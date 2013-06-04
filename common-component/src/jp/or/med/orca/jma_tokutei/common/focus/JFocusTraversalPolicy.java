package jp.or.med.orca.jma_tokutei.common.focus;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.JTextComponent;

import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

/**
 * �t�H�[�J�X�ړ�����
 */
public class JFocusTraversalPolicy extends FocusTraversalPolicy {

	private List<Component> componentList = null;

	private Component defaultComponent = null;

	/**
	 * �R���X�g���N�^
	 */
	public JFocusTraversalPolicy() {
		super();
		this.initialize();
	}

	/**
	 * ������
	 */
	private void initialize(){
		boolean ret = false;

		this.componentList = new ArrayList<Component>();
	}

	/**
	 * ����Ώۂ̃R���|�[�l���g��ǉ�����B
	 */
	public void addComponent(Component c){
		this.componentList.add(c);
	}

	/* Added 2008/03/12 �ጎ  */
	/* --------------------------------------------------- */
	/**
	 * �w�肵���ʒu�ɁA����Ώۂ̃R���|�[�l���g��ǉ�����B
	 */
	public void addComponent(Component c, int index){
		this.componentList.add(index, c);
	}
	/* --------------------------------------------------- */

	/**
	 * �R���|�[�l���g�̎擾
	 */
	public Component getComponent(int index){
		return this.componentList.get(index);
	}

	/**
	 * �R���|�[�l���g�̃T�C�Y
	 */
	public int getComponentSize(){
		return this.componentList.size();
	}

	/**
	 * ����ΏۃN���X����w�肵���R���|�[�l���g���폜����B
	 */
	public void removeComponent(Component c){
		for (Iterator<Component> iter = this.componentList.iterator(); iter.hasNext();) {

			Component listComponent = iter.next();
			if (listComponent == c) {
				this.componentList.remove(listComponent);
				return;
			}
		}
	}

	/**
	 * ���̃R���|�[�l���g���擾����B
	 */
	@Override
	public Component getComponentAfter(Container container, Component component) {

		Component ret = null;

		/* ���X�g��A���̃R���|�[�l���g���擾����B */
//		Component c = this.getTargetComponent(component, 1);

		/* �L���ȃR���|�[�l���g�݂̂�Ԃ��B */
		/* �S�ẴR���|�[�l���g�������ł���ꍇ���l�����A1 ���[�v�����L����Ԃ̒��������Ȃ��B */
		int count = this.componentList.size();

		Component c = component;
		for (int i = 0; i < count; i++) {
			c = this.getTargetComponent(c, 1);

			if (c.isEnabled()) {
				if (c instanceof JTextComponent) {
					if (((JTextComponent)c).isEditable()) {
						ret = c;
						break;
					}
				}else if (c.getClass() == jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl.class){
					if (((ExtendedOpenTextControl)c).isEnabled()) {
						ret = c;
						break;
					}
				}
				else {
					ret = c;
					break;
				}
			}
		}

		return ret;
	}

	/**
	 * �O�̃R���|�[�l���g���擾����B
	 */
	@Override
	public Component getComponentBefore(Container container, Component component) {

		Component ret = null;

		/* ���X�g��A���̃R���|�[�l���g���擾����B */
//		Component c = this.getTargetComponent(component, -1);

		/* �L���ȃR���|�[�l���g�݂̂�Ԃ��B */
		/* �S�ẴR���|�[�l���g�������ł���ꍇ���l�����A1 ���[�v�����L����Ԃ̒��������Ȃ��B */
		int count = this.componentList.size();

		Component c = component;

		try {
				for (int i = 0; i < count; i++) {
					c = this.getTargetComponent(c, -1);

					/* Modified 2008/05/01 �ጎ  */
					/* --------------------------------------------------- */
		//			if (c.isEnabled()) {
		//				ret = c;
		//				break;
		//			}
					/* --------------------------------------------------- */
					if (c.isEnabled()) {
						if (c instanceof JTextComponent) {
						if (((JTextComponent)c).isEditable()) {
							ret = c;
							break;
						}
					}
				}else if (c.getClass() == jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl.class){
					if (((ExtendedOpenTextControl)c).isEnabled()) {
						ret = c;
						break;
					}
					else {
						ret = c;
						break;
					}
				}
				/* --------------------------------------------------- */
			}
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		return ret;

//		int index = ( this.componentList.indexOf(component) - 1 + this.componentList.size() ) % this.componentList.size();
//
//		return this.componentList.get(index);
	}

	/**
	 * ���X�g��A���Ώ������w�肵�ăR���|�[�l���g���擾����B
	 */
	private Component getTargetComponent(Component component, int count) {
		int size = this.componentList.size();

		int index = this.componentList.indexOf(component) + count;
		index %= this.componentList.size();

		while(index < 0){
			index += size;
		}

		Component c = this.componentList.get(index);
		return c;
	}

	@Override
	public Component getDefaultComponent(Container container) {
		return this.defaultComponent;
	}

	@Override
	public Component getFirstComponent(Container container) {
		return this.componentList.get(0);
	}

	@Override
	public Component getLastComponent(Container container) {
		return this.componentList.get(this.componentList.size() - 1);
	}

	public void setDefaultComponent(Component defaultComponent) {
		this.defaultComponent = defaultComponent;
	}
}
