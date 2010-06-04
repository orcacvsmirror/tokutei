package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Font;

import javax.swing.JLabel;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JLabel 独自拡張クラス
 *
 */
public class ExtendedLabel extends JLabel {

	/**
	 * コンストラクタ
	 */
	public ExtendedLabel() {
//		Font font = ViewSettings.getCommonUserInputFont();
//		this.setFont(font);
		this.setFontStyle(-1);
	}

	/**
	 * コンストラクタ
	 */
	public ExtendedLabel(String key, int style) {
		this(key);
		this.setFontStyle(style);
	}

	/**
	 * コンストラクタ
	 */
	public ExtendedLabel(int style) {
		this.setFontStyle(style);
	}

	private void setFontStyle(int style) {
		Font font = ViewSettings.getCommonUserInputFont(style);
		this.setFont(font);
	}

	/**
	 * @param id 画面設定ID
	 */
	public ExtendedLabel(String key) {
		this();

		/* Modified 2008/03/15 若月  */
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

	/* Added 2008/03/10 若月  */
	/* --------------------------------------------------- */
	@Override
	public void setText(String text) {
//		super.setText(text);
		this.setHtmlText(text);
	}
	/* --------------------------------------------------- */

	/* Added 2008/03/15 若月 機能追加 */
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
