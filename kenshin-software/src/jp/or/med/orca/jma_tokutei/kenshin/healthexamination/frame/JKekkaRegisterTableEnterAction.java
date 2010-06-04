package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 * ���ʓ��͉�ʂ̃e�[�u���ɂ�
 * Enter�L�[�������Ƀt�H�[�J�X�}�b�v�Ŏw�肳�ꂽ
 * �Z���Ƀt�H�[�J�X�ړ����郊�X�i�N���X
 * @author nishiyama
 *
 */
public class JKekkaRegisterTableEnterAction {

	/**
	 * �t�H�[�J�X�}�b�v
	 * int[2]�̃��X�g
	 * int[0] - ��C���f�b�N�X
	 * int[1] - �s�C���f�b�N�X
	 */
	private List<int[]> focusMapList;
	private JTable table;
	/**
	 * �O��ړ������Z���̃|�W�V����
	 * int[0] - ��C���f�b�N�X
	 * int[1] - �s�C���f�b�N�X
	 */
	private int[] lastPosition;
	
	/**
	 * �R���X�g���N�^
	 * @param focusMapList �t�H�[�J�X�}�b�v
	 * @param table ���X�i�ݒ�Ώۃe�[�u��
	 */
	public JKekkaRegisterTableEnterAction(List<int[]> focusMapList, JTable table) {
		
		this.table = table;
		lastPosition = new int[2];
		setFocusMapList(focusMapList);
	}
	
	/**
	 * �C���v�b�g�}�b�v�I�u�W�F�N�g���擾����
	 * @return
	 */
	public Object getInputMapObject() {
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        return im.get(enter);
	}
	
	/**
	 * Enter�L�[�������A�N�V�����C�x���g�n���h��
	 * @return Action
	 */
	public Action getEnterAction() {
		
        final Action oldEnterAction = table.getActionMap().get(getInputMapObject());
        Action enterAction = new AbstractAction() {
        	
        	public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                int x = table.getSelectedColumn();
                int y = table.getSelectedRow();
                int nextIndex = -1;
                nextIndex = getNextIndex(x, y);
                if (nextIndex == 0) {
                	nextIndex = getNextIndex(lastPosition[0], lastPosition[1]);
                	if (nextIndex > 0) nextIndex--;
                }
                int[] nextPosition = focusMapList.get(nextIndex);
                lastPosition[0] = nextPosition[0];
                lastPosition[1] = nextPosition[1];
                table.changeSelection(nextPosition[1], nextPosition[0], false, false);
            }
        };
        return enterAction;
	}
	
	/**
	 * ������
	 * @param focusMapList �t�H�[�J�X�}�b�v
	 */
	public void setFocusMapList(List<int[]> focusMapList) {
		this.focusMapList = focusMapList;
//		lastPosition[0] = -1;
//		lastPosition[1] = -1;
		lastPosition[0] = 0;
		lastPosition[1] = 0;
	}
	
	/**
	 * �t�H�[�J�X�}�b�v����
	 * ���Ɉړ�����Z�����W���i�[����Ă���C���f�b�N�X���擾����
	 * @param x ���ݑI�����Ă����C���f�b�N�X
	 * @param y ���ݑI�����Ă���s�C���f�b�N�X
	 * @return ���ړ��ΏۃZ�����W���i�[����Ă���}�b�v���̃C���f�b�N�X
	 */
	private int getNextIndex(int x, int y) {
		int size = focusMapList.size();
		int nextIndex = -1;
		for (int i = 0 ; i < size ; i++) {
			int[] position = focusMapList.get(i);
			if ((position[0] == x) && (position[1] == y)) {
				nextIndex = i + 1;
				break;
			}
		}
        if ((nextIndex < 0) || (nextIndex == size)) {
        	nextIndex = 0;
        }
		return nextIndex;
	}
}