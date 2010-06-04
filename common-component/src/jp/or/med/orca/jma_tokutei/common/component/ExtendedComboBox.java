package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ActionMap;
import javax.swing.ComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;

/**
 * JComboBox の独自拡張
 */
public class ExtendedComboBox extends JComboBox {

	private ImeController imeController = null;

	public ExtendedComboBox() {
		super();
		this.initActions();

		this.setFont(ViewSettings.getCommonUserInputFont());
	}

	// add s.inoue 2009/10/01
	/**
	 * コンストラクタ
	 */
	public ExtendedComboBox(String text, int n, ImeMode mode, boolean handleEnterKey) {
		super();
		setFont(ViewSettings.getCommonUserInputFont());

		initActions(handleEnterKey);

		this.imeController = new ImeController();
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedComboBox(ComboBoxModel model) {
		super(model);
	}

	public ExtendedComboBox(Object[] objects) {
		super(objects);
	}

	public ExtendedComboBox(Vector<?> vector) {
		super(vector);
	}

	// add s.inoue 2009/10/01
	public ExtendedComboBox(ImeMode mode) {
		this("", -1, mode, true);
	}

	private void initActions() {
		ActionMap actions = getActionMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		actions.put("focusOutPrevious", new FocusOutPreviousAction());

		InputMap inputs = this.getInputMap(JComponent.WHEN_FOCUSED);
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");

	}

	// add s.inoue 2009/10/01
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
}
