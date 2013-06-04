// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.openswing.swing.client.ComboBoxControl;

public class ExtendedOpenComboboxControl extends ComboBoxControl
    implements FocusListener
{

    public ExtendedOpenComboboxControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        imeController = null;
        setFont(ViewSettings.getCommonUserInputFont());
        addEnterPolicy(this);
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
        addFocusListener(this);
    }

	// enter�L�[����
	private void addEnterPolicy(JComponent comp) {
		  //���ւ̃t�H�[�J�X�ݒ�
		  Set<AWTKeyStroke> keystrokes = new HashSet<AWTKeyStroke>();
		  Set<AWTKeyStroke> oldKeyStrokes = comp
		          .getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
		  if (oldKeyStrokes != null) {
		      //���ɓo�^����Ă���KeySet��������΃R�s�[����B
		  //�W���ł����TabKey�Ȃǂ������Ă���͂�
		      for (AWTKeyStroke akw : oldKeyStrokes) {
		          keystrokes.add(akw);
		      }
		  }

		  //ENTER��ǉ�
		  keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
		  comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keystrokes);

		  //�O�ւ̃t�H�[�J�X�ݒ�
		  keystrokes = new HashSet<AWTKeyStroke>();
		  oldKeyStrokes = comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
		  if (oldKeyStrokes != null) {
		      //���ɓo�^����Ă���KeySet��������΃R�s�[����B
		  //�W���ł����Shft+TabKey�Ȃǂ������Ă���͂�
		      for (AWTKeyStroke akw : oldKeyStrokes) {
		          keystrokes.add(akw);
		      }
		  }

		  // Shift+Enter��ǉ�
		  keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK));
		  comp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, keystrokes);
	}

    public void focusGained(FocusEvent focusevent)
    {
    }

    public void focusLost(FocusEvent focusevent)
    {
    }

    private ImeController imeController;
}
