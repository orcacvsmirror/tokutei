package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class ExtendedRadioButton extends JRadioButton{
	public ExtendedRadioButton() {
		super();

		Font font = ViewSettings.getCommonUserInputFont();
		this.setFont(font);

		this.initActions();
	}

	private void initActions() {
		ActionMap actions = getActionMap();
		actions.put("focusOutNext", new FocusOutNextAction()); //$NON-NLS-1$
		actions.put("focusOutPrevious", new FocusOutPreviousAction()); //$NON-NLS-1$

		InputMap inputs = getInputMap(JComponent.WHEN_FOCUSED);

		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed"); //$NON-NLS-1$
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released"); //$NON-NLS-1$
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext"); //$NON-NLS-1$
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious"); //$NON-NLS-1$
	}

	public void setSize(Dimension d) {
		if (d.width == 0) {
			d.width = d.height;
		}
		super.setSize(d);
	}

}
