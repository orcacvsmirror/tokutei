package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.KeyEvent;
import java.text.Format;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.text.Document;

import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JFormattedTextField �̓Ǝ��g��
 */
public class ExtendedFormattedTextField extends JFormattedTextField {
	
	private ImeController imeController = null;
	
	public ExtendedFormattedTextField() {
		super();
		this.initialize();
	}

	/**
	 * ���������� 
	 */
	private void initialize() {
		setDocument(new LengthLimitableDocument());
		setFont(ViewSettings.getCommonUserInputFont());
		initActions();
		this.imeController = new ImeController();
	}

	public ExtendedFormattedTextField(String text, int n) {
		this();
		
//		Document doc = this.getDocument();
//		if (doc instanceof LengthLimitableDocument) {
//			((LengthLimitableDocument)doc).setLimit(n);	
//		}
		this.setLimit(n);
		
		setText(text);
	}

	/*
	 * IME ���[�h���w�肷��R���X�g���N�^ 
	 */
	public ExtendedFormattedTextField(ImeMode mode) {
		this();
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedFormattedTextField(String text, int n, ImeMode mode) {
		this(text, n);
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}
	
	/*
	 * Formatter ���p�����[�^�Ɏ��R���X�g���N�^ 
	 */
	public ExtendedFormattedTextField(AbstractFormatter formater) {
		super(formater);
		this.initialize();
	}

	public ExtendedFormattedTextField(AbstractFormatterFactory factory, Object object) {
		super(factory, object);
		this.initialize();
	}

	public ExtendedFormattedTextField(AbstractFormatterFactory factory) {
		super(factory);
		this.initialize();
	}

	public ExtendedFormattedTextField(Format formatter) {
		super(formatter);
		this.initialize();
	}

	public ExtendedFormattedTextField(Object object) {
		super(object);
		this.initialize();
	}

	public ExtendedFormattedTextField(ImeMode mode, Format formatter) {
		super(formatter);
		this.initialize();
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}
	
	public ExtendedFormattedTextField(int n, ImeMode mode, Format formatter) {
		super(formatter);
		this.initialize();
		this.setLimit(n);		
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedFormattedTextField(String text, int n, ImeMode mode, Format formatter) {
		super(formatter);
		this.initialize();
		this.setText(text);
		this.setLimit(n);		
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedFormattedTextField(ImeMode mode, AbstractFormatter formatter) {
		super(formatter);
		this.initialize();
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}
	
	public ExtendedFormattedTextField(int n, ImeMode mode, AbstractFormatter formatter) {
		super(formatter);
		this.initialize();
		this.setLimit(n);		
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedFormattedTextField(String text, int n, ImeMode mode, AbstractFormatter formatter) {
		super(formatter);
		this.initialize();
		this.setText(text);
		this.setLimit(n);		
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}
	
	/*
	 * private ���\�b�h 
	 */
	private void initActions() {
		ActionMap actions = getActionMap();
		InputMap inputs = getInputMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext");

		actions.put("focusOutPrevious", new FocusOutPreviousAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious");
		
		/* Added 2008/03/18 �ጎ  */
		/* --------------------------------------------------- */
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");
//		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "focusOutNext");
		/* --------------------------------------------------- */
	}

	/*
	 * public ���\�b�h 
	 */
	public void setLimit(int limit) {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		doc.setLimit(limit);
	}

	public int getLimit() {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		return doc.getLimit();
	}
}
