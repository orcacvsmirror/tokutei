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
import java.text.ParseException;
import java.util.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import org.openswing.swing.client.FormattedTextControl;

public class ExtendedOpenFormattedZIPControl extends FormattedTextControl
    implements FocusListener
{

    public ExtendedOpenFormattedZIPControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode, boolean flag)
    {
        imeController = null;
        setText(s);
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
        MaskFormatter maskformatter = null;
        try
        {
            maskformatter = new MaskFormatter("###-####");
        }
        catch(ParseException parseexception)
        {
            parseexception.printStackTrace();
        }
        maskformatter.setValidCharacters("0123456789");
        setFormatter(maskformatter);
        addEnterPolicy(this);
    }

    public ExtendedOpenFormattedZIPControl()
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
    }

    public ExtendedOpenFormattedZIPControl(boolean flag)
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedOpenFormattedZIPControl(String s, int i)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
    }

    public ExtendedOpenFormattedZIPControl(String s, int i, boolean flag)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedOpenFormattedZIPControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this(s, i, imemode, true);
    }

    public ExtendedOpenFormattedZIPControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this("", -1, imemode, true);
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

    public void focusLost(FocusEvent focusevent)
    {
    }

    public void focusGained(FocusEvent focusevent)
    {
    }

    private static final String FORMAT_TEXT_STRING = "###-####";
    private static final String FORMAT_TEXT_ARROWED = "0123456789";
    private ImeController imeController;
}
