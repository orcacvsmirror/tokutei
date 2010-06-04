package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.KeyEvent;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;

public class ExtendedPasswordField extends JPasswordField {
	public ExtendedPasswordField() {
		super();
		setDocument(new LengthLimitableDocument());
		initActions();
	}

	public ExtendedPasswordField(String text, int n) {
		super();
		LengthLimitableDocument doc = new LengthLimitableDocument();
		doc.setLimit(n);
		setDocument(doc);
		setText(text);
		setColumns(n);
		initActions();
	}

	private void initActions() {
		ActionMap actions = getActionMap();
		InputMap inputs = getInputMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext");

		actions.put("focusOutPrevious", new FocusOutPreviousAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious");

		// edit s.inoue 2009/12/06
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");
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
