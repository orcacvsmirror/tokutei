package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JTextField �̓Ǝ��g��
 *
 *  IME ����@�\��ExtendedEditorPane �N���X�Ƌ��L���邽�߁AImeController �N���X�Ɉړ��B
 */
public class ExtendedTextField extends JTextField implements FocusListener{

	private ImeController imeController = null;

	/**
	 * �R���X�g���N�^
	 */
	public ExtendedTextField(String text, int n, ImeMode mode, boolean handleEnterKey) {
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
		this.addFocusListener(this);
	}

	// add s.inoue 2010/01/14
	public void focusGained(FocusEvent e) {
		this.selectAll();
	}

	public ExtendedTextField() {
		this("", -1, ImeMode.IME_NO_CONTROLL, true);
		this.addFocusListener(this);
	}

	public ExtendedTextField(boolean handleEnterKey) {
		this("", -1, ImeMode.IME_NO_CONTROLL, handleEnterKey);
		this.addFocusListener(this);
	}

	public ExtendedTextField(String text, int n) {
		this(text, n, ImeMode.IME_NO_CONTROLL, true);
		this.addFocusListener(this);
	}

	public ExtendedTextField(String text, int n, boolean handleEnterKey) {
		this(text, n, ImeMode.IME_NO_CONTROLL, handleEnterKey);
		this.addFocusListener(this);
	}

	public ExtendedTextField(String text, int n, ImeMode mode) {
		this(text, n, mode, true);
		this.addFocusListener(this);
	}

	public ExtendedTextField(ImeMode mode) {
		this("", -1, mode, true);
		this.addFocusListener(this);
	}


	/* Enter�L�[�ɂ��t�H�[�J�X�����ON/OFF��
	 * �؂�ւ�����悤�ɂ���*/
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

	public void setLimit(int limit) {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		doc.setLimit(limit);
	}

	public int getLimit() {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		return doc.getLimit();
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}
