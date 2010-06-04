package jp.or.med.orca.jma_tokutei.common.component;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * �R���{�{�b�N�X�ƘA�������e�L�X�g�t�B�[���h�ւ̓��͂�ʒm���� DocumentListner
 */
public class TextFieldWithComboBoxDocumentListener 
	implements DocumentListener {

	private JComboBox comboBox = null;
	private int newItemIndex = 0;
	
	/**
	 * �R���X�g���N�^
	 */
	public TextFieldWithComboBoxDocumentListener(
			JComboBox comboBox,
			int newItemIndex){
		
		this.comboBox = comboBox;
		this.newItemIndex = newItemIndex;
	}
	
	public void changedUpdate(DocumentEvent e) {
		
	}

	public void insertUpdate(DocumentEvent e) {
		if (! comboBox.hasFocus()) {
			changeSelectedItem(e);
		}
	}

	public void removeUpdate(DocumentEvent e) {
		if (! comboBox.hasFocus()) {
			changeSelectedItem(e);
		}
	}
	
	/**
	 * ���͂����e�L�X�g�ɍ��킹�āA�R���{�{�b�N�X�̍��ڂ��������A
	 * ���������ŏ��̍��ڂ�I������B 
	 */
//	private void changeSelectedItem(final JComboBox comboBox, DocumentEvent e) {
	private void changeSelectedItem(DocumentEvent e) {
		String inputText = this.getInputText(e);
		
		if (inputText == null || inputText.isEmpty()) {
			comboBox.setSelectedIndex(0);
		}
		else {
			int findIndex = this.searchComoboBoxItem(inputText, true);
			
			if (findIndex < 0) {
				comboBox.setSelectedIndex(this.newItemIndex);
			}
			else {
				comboBox.setSelectedIndex(findIndex);
			}
		}
	}

	/**
	 * ���͍ς݂̃e�L�X�g���擾����B
	 */
	private String getInputText(DocumentEvent e){
		String text = null;
		
		int length = e.getDocument().getLength();
		
		try {
			text = e.getDocument().getText(0, length);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		return text;
	}
	
	/**
	 * �w�肵���e�L�X�g�ɍ��킹�āA�R���{�{�b�N�X���̍��ڂ��������A
	 * ���������ŏ��̍��ڂ̃C���f�b�N�X���擾����B
	 * 
	 * @param boolean trimBeforeZero �擪�̘A������ 0 ���폜����B
	 */
//	private int searchComoboBoxItem(String text){
//		int retValue = this.searchComoboBoxItem(text, false);
//		return retValue;
//	}

	private int searchComoboBoxItem(String text, boolean trimBeforeZero){
		int findIndex = -1;
		int count = comboBox.getItemCount();
		
		for (int i = 0; i < count; i++) {
			String itemText = (String)comboBox.getItemAt(i);
			
			/* �w�肵���e�L�X�g�� 0 �ŊJ�n���Ă��Ȃ��ꍇ */
			if (
					text != null 
					&& ! text.isEmpty()
//					&& String.valueOf(text.charAt(0)).startsWith("0")) {
					&& ! text.startsWith("0")) {
				
				/* �R���{�{�b�N�X�̍��ڂ̐擪�� 0 ���폜���Ĕ�r����ꍇ */
				if (trimBeforeZero) {
					itemText = itemText.replaceFirst("^0+", "");
				}
			}
			
			if (itemText.indexOf(text) == 0) {
				findIndex = i;
				break;
			}
		}
		
		return findIndex;
	}
}
