package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Font;

import javax.swing.JLabel;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JLabel �Ǝ��g���N���X
 *
 */
public class ExtendedLabel extends JLabel {

	/**
	 * �R���X�g���N�^
	 */
	public ExtendedLabel() {
//		Font font = ViewSettings.getCommonUserInputFont();
//		this.setFont(font);
		this.setFontStyle(-1);
	}

	/**
	 * �R���X�g���N�^
	 */
	public ExtendedLabel(String key, int style) {
		this(key);
		this.setFontStyle(style);
	}

	/**
	 * �R���X�g���N�^
	 */
	public ExtendedLabel(int style) {
		this.setFontStyle(style);
	}

	private void setFontStyle(int style) {
		Font font = ViewSettings.getCommonUserInputFont(style);
		this.setFont(font);
	}

	/**
	 * @param id ��ʐݒ�ID
	 */
	public ExtendedLabel(String key) {
		this();

		/* Modified 2008/03/15 �ጎ  */
		/* --------------------------------------------------- */
//		String text = ViewSettings.getUsingValueString(key);
//		this.setHtmlText(text);
		/* --------------------------------------------------- */
		if (key != null && ! key.isEmpty()) {
		    this.setTextFromKey(key);
		}
		/* --------------------------------------------------- */
	}

	public void setHtmlText(String text) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<html>");
		buffer.append(text);
		buffer.append("</html>");

		super.setText(buffer.toString());
	}

	/* Added 2008/03/10 �ጎ  */
	/* --------------------------------------------------- */
	@Override
	public void setText(String text) {
//		super.setText(text);
		this.setHtmlText(text);
	}
	/* --------------------------------------------------- */

	/* Added 2008/03/15 �ጎ �@�\�ǉ� */
	/* --------------------------------------------------- */
	public void setTextFromKey(String key){
		String text = ViewSettings.getUsingValueString(key);
		if (text == null) {
			text = "";
		}
		this.setHtmlText(text);
	}
	/* --------------------------------------------------- */

//	/**
//	 * @param arg0
//	 */
//	public ExtendedLabel(Icon arg0) {
//		super(arg0);
//	}
//
//	/**
//	 * @param arg0
//	 * @param arg1
//	 */
//	public ExtendedLabel(String arg0, int arg1) {
//		super(arg0, arg1);
//	}
//
//	/**
//	 * @param arg0
//	 * @param arg1
//	 */
//	public ExtendedLabel(Icon arg0, int arg1) {
//		super(arg0, arg1);
//	}
//
//	/**
//	 * @param arg0
//	 * @param arg1
//	 * @param arg2
//	 */
//	public ExtendedLabel(String arg0, Icon arg1, int arg2) {
//		super(arg0, arg1, arg2);
//	}
}
