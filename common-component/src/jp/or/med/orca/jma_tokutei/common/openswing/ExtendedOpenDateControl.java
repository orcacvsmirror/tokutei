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
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.openswing.swing.client.DateControl;

public class ExtendedOpenDateControl extends DateControl
    implements FocusListener
{

    public ExtendedOpenDateControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode, boolean flag)
    {
        imeController = null;
        setFont(ViewSettings.getCommonUserInputFont());
        setFocusable(true);
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
        addFocusListener(this);
        addEnterPolicy(this);
    }

    public ExtendedOpenDateControl()
    {
        imeController = null;
        setCanCopy(true);
        setRequired(false);
        setAttributeName("hireDate");
    }

    public ExtendedOpenDateControl(boolean flag)
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
        addFocusListener(this);
    }

    public ExtendedOpenDateControl(String s, int i)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
        addFocusListener(this);
    }

    public ExtendedOpenDateControl(String s, int i, boolean flag)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
        addFocusListener(this);
    }

    public ExtendedOpenDateControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this(s, i, imemode, true);
        addFocusListener(this);
    }

    public ExtendedOpenDateControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this("", -1, imemode, true);
        addFocusListener(this);
    }

    public Date getTextToDate(String s)
        throws ParseException
    {
        if(s.indexOf("/") > 0)
            s = s.replaceAll("/", "");
        int i = Integer.parseInt(s.substring(0, 4));
        int j = Integer.parseInt(s.substring(5, 6));
        int k = Integer.parseInt(s.substring(7, 8));
        Calendar calendar = Calendar.getInstance();
        Calendar _tmp = calendar;
        calendar.set(2, k);
        Calendar _tmp1 = calendar;
        calendar.set(5, j);
        Calendar _tmp2 = calendar;
        calendar.set(1, i);
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        simpledateformat.setLenient(true);
        simpledateformat.applyPattern("yyyyMMdd");
        Date date = null;
        date = simpledateformat.parse(s);
        return date;
    }

    public String getDateText()
    {
        String s = null;
        try
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            Date date = getTextToDate(getDateField().getText());
            s = simpledateformat.format(date);
            System.out.println(s);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return s;
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

    private ImeController imeController;
}
