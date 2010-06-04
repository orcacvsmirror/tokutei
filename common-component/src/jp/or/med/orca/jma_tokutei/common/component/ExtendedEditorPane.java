package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JTextField �̓Ǝ��g��
 *
 *  Modified 2008/03/12 �ጎ
 *  IME ����@�\��ExtendedEditorPane �N���X�Ƌ��L���邽�߁AImeController �N���X�Ɉړ��B
 */
public class ExtendedEditorPane extends JEditorPane {

	private ImeController imeController = null;

	/**
	 * �R���X�g���N�^
	 */
	public ExtendedEditorPane(String text, int n, ImeMode mode, boolean handleEnterKey) {
		super();
		setFont(ViewSettings.getCommonUserInputFont());

		LengthLimitableDocument doc = new LengthLimitableDocument();
		if (n > 0) {
			doc.setLimit(n);
		}
		this.setDocument(doc);

		this.setText(text);

		initActions(handleEnterKey);

		this.imeController = new ImeController();
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedEditorPane() {
		this("", -1, ImeMode.IME_NO_CONTROLL, true);
	}

	public ExtendedEditorPane(boolean handleEnterKey) {
		this("", -1, ImeMode.IME_NO_CONTROLL, handleEnterKey);
	}

	public ExtendedEditorPane(String text, int n) {
		this(text, n, ImeMode.IME_NO_CONTROLL, true);
	}

	public ExtendedEditorPane(String text, int n, boolean handleEnterKey) {
		this(text, n, ImeMode.IME_NO_CONTROLL, handleEnterKey);
	}

	public ExtendedEditorPane(String text, int n, ImeMode mode) {
		this(text, n, mode, true);
	}

	public ExtendedEditorPane(ImeMode mode) {
		this("", -1, mode, true);
	}

	private void initActions(boolean handleEnterKey) {
		ActionMap actions = getActionMap();
		InputMap inputs = getInputMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext");

		actions.put("focusOutPrevious", new FocusOutPreviousAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious");

		if (handleEnterKey) {
			inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");
		}
	}

	/**
	 * �G�f�B�^�̃v���_�E�����j���[�ݒ�
	 */
	public void showPopup(JComponent c, int x, int y) {
		JPopupMenu menu = new JPopupMenu();

		ActionMap am = c.getActionMap();

		Action cut = am.get(DefaultEditorKit.cutAction);
		addPopupMenu(menu, "�؂���(X)", cut, 'X', KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

		Action copy = am.get(DefaultEditorKit.copyAction);
		addPopupMenu(menu, "�R�s�[(C)", copy, 'C', KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

		Action paste = am.get(DefaultEditorKit.pasteAction);
		addPopupMenu(menu, "�\��t��(V)", paste, 'V', KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

		Action all = am.get(DefaultEditorKit.selectAllAction);
		addPopupMenu(menu, "���ׂđI��(A)", all, 'A', KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

		menu.show(c, x, y);
	}

	// add s.inoue 2009/12/07
	// �v���_�E�����j���[���ڂ�ǉ�
	protected void addPopupMenu(JPopupMenu pmenu, String text, Action action, int mnemonic, KeyStroke ks) {
		if (action != null) {
			JMenuItem mi = pmenu.add(action);
			if (text != null) {
				mi.setText(text);
			}
			if (mnemonic != 0) {
				mi.setMnemonic(mnemonic);
			}
			if (ks != null) {
				mi.setAccelerator(ks);
			}
		}
	}
	public void setLimit(int limit) {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		doc.setLimit(limit);
	}

	public int getLimit() {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		return doc.getLimit();
	}
}
// edit 2010/05/21 �C���O�s��Ή�
//package jp.or.med.orca.jma_tokutei.common.component;
//
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//
//import javax.swing.Action;
//import javax.swing.ActionMap;
//import javax.swing.JComponent;
//import javax.swing.JEditorPane;
//import javax.swing.JMenuItem;
//import javax.swing.JPopupMenu;
//import javax.swing.KeyStroke;
//import javax.swing.text.DefaultEditorKit;
//import javax.swing.text.Document;
//
//import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//
///**
// * JEditorPane �̓Ǝ��g��
// */
//public class ExtendedEditorPane extends JEditorPane {
//
//	private ImeController imeController = null;
//
//	public ExtendedEditorPane() {
//		super();
//		setDocument(new LengthLimitableDocument());
//		setFont(ViewSettings.getCommonUserInputFont());
//
//		this.imeController = new ImeController();
//	}
//
//	// add s.inoue 2009/11/11
//	/**
//	 * �G�f�B�^�̃v���_�E�����j���[�ݒ�
//	 */
//	public void showPopup(JComponent c, int x, int y) {
//		JPopupMenu menu = new JPopupMenu();
//
//		ActionMap am = c.getActionMap();
//
//		Action cut = am.get(DefaultEditorKit.cutAction);
//		addPopupMenu(menu, "�؂���(X)", cut, 'X', KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
//
//		Action copy = am.get(DefaultEditorKit.copyAction);
//		addPopupMenu(menu, "�R�s�[(C)", copy, 'C', KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
//
//		Action paste = am.get(DefaultEditorKit.pasteAction);
//		addPopupMenu(menu, "�\��t��(V)", paste, 'V', KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
//
//		Action all = am.get(DefaultEditorKit.selectAllAction);
//		addPopupMenu(menu, "���ׂđI��(A)", all, 'A', KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
//
//		menu.show(c, x, y);
//	}
//
//	// add s.inoue 2009/11/11
//	// �v���_�E�����j���[���ڂ�ǉ�
//	protected void addPopupMenu(JPopupMenu pmenu, String text, Action action, int mnemonic, KeyStroke ks) {
//		if (action != null) {
//			JMenuItem mi = pmenu.add(action);
//			if (text != null) {
//				mi.setText(text);
//			}
//			if (mnemonic != 0) {
//				mi.setMnemonic(mnemonic);
//			}
//			if (ks != null) {
//				mi.setAccelerator(ks);
//			}
//		}
//	}
//
//	// �}�E�X���X�i�̃��\�b�h���`
//	public void mouseClicked(MouseEvent e){
//	}
//
//	// MouseListener�ɐ錾����Ă���e�탁�\�b�h���`�i�����ł͏ȗ��j
//	public ExtendedEditorPane(String text, int n) {
//		this();
//
//		Document doc = this.getDocument();
//		if (doc instanceof LengthLimitableDocument) {
//			((LengthLimitableDocument)doc).setLimit(n);
//		}
//
//		setText(text);
//	}
//
//	public ExtendedEditorPane(String text, int n, ImeMode mode) {
//		this(text, n);
//		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
//	}
//
//	public ExtendedEditorPane(ImeMode mode) {
//		this();
//		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
//	}
//
//	public void setLimit(int limit) {
//		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
//		doc.setLimit(limit);
//	}
//
//	public int getLimit() {
//		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
//		return doc.getLimit();
//	}
//}
