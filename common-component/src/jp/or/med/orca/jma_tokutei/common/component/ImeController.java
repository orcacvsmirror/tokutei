package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.im.InputSubset;
import java.lang.Character.Subset;
import java.lang.Character.UnicodeBlock;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import jp.or.med.orca.jma_tokutei.common.table.JComboCellEditor;

/**
 * IME ����N���X
 */
public class ImeController {

	private Subset[] subsets = null;
	private boolean characterSubsetsManaged = false;

	public enum ImeMode {
		/* IME �𐧌䂵�Ȃ� */
		IME_NO_CONTROLL,

		/* �S�p */
		IME_ZENKAKU,

		/* ���p�J�i */
		IME_HANKAKU_KANA,

		/* �S�p�J�i */
		IME_ZENKAKU_KANA,

		/* �S�p�p���� */
		IME_ZENKAKU_ASCII,

		/* IME �� OFF �ɂ��� */
		IME_OFF
	}

	// add s.inoue 2009/10/01
	/**
	 * IME �^������(�O���b�h�A�R���{�{�b�N�X�p)�̂��߂� FocusListener ��ǉ�����B
	 */
	public void addFocusListenerForCharcterSubsets(final Component c, ImeMode mode) {

		switch(mode){
		case IME_NO_CONTROLL:
			break;

		case IME_ZENKAKU:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.KANJI };
			break;

		case IME_HANKAKU_KANA:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.HALFWIDTH_KATAKANA };
			break;

		case IME_ZENKAKU_KANA:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ UnicodeBlock.KATAKANA };
			break;

		case IME_ZENKAKU_ASCII:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.FULLWIDTH_LATIN };
			break;

		case IME_OFF:
			this.characterSubsetsManaged = true;
			this.subsets = null;
			break;

		default:
		}

		c.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				if (ImeController.this.characterSubsetsManaged) {

					c.getInputContext().setCharacterSubsets(
							ImeController.this.subsets);
				}
			}

			public void focusLost(FocusEvent arg0) {
			}
		});
	}

	// add s.inoue 2009/10/01
	/**
	 * IME �^������(�O���b�h�A�R���{�{�b�N�X�p)�̂��߂� FocusListener ��ǉ�����B
	 */
	public void addFocusListenerForCharcterSubsets(final JTextField c, ImeMode mode) {

		switch(mode){
		case IME_NO_CONTROLL:
			break;

		case IME_ZENKAKU:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.KANJI };
			break;

		case IME_HANKAKU_KANA:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.HALFWIDTH_KATAKANA };
			break;

		case IME_ZENKAKU_KANA:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ UnicodeBlock.KATAKANA };
			break;

		case IME_ZENKAKU_ASCII:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.FULLWIDTH_LATIN };
			break;

		case IME_OFF:
			this.characterSubsetsManaged = true;
			this.subsets = null;
			break;

		default:
		}

		c.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				if (ImeController.this.characterSubsetsManaged) {

					c.getInputContext().setCharacterSubsets(
							ImeController.this.subsets);
				}
			}

			public void focusLost(FocusEvent arg0) {
			}
		});
	}

	/**
	 * IME �^������̂��߂� FocusListener ��ǉ�����B
	 */
	public void addFocusListenerForCharcterSubsets(final JTextComponent c, ImeMode mode) {

		switch(mode){
		case IME_NO_CONTROLL:
			break;

		case IME_ZENKAKU:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.KANJI };
			break;

		case IME_HANKAKU_KANA:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.HALFWIDTH_KATAKANA };
			break;

		case IME_ZENKAKU_KANA:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ UnicodeBlock.KATAKANA };
			break;

		case IME_ZENKAKU_ASCII:
			this.characterSubsetsManaged = true;
			this.subsets = new Subset[]{ InputSubset.FULLWIDTH_LATIN };
			break;

		case IME_OFF:
			this.characterSubsetsManaged = true;
			this.subsets = null;
			break;

		default:
		}

		c.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				if (ImeController.this.characterSubsetsManaged) {

					c.getInputContext().setCharacterSubsets(
							ImeController.this.subsets);
				}
			}

			public void focusLost(FocusEvent arg0) {
			}
		});
	}
}
