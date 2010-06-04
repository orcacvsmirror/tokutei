package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Font;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * ��ʃ^�C�g���p���x�� 
 */
public class TitleLabel extends ExtendedLabel {
	/**
	 * �R���X�g���N�^
	 */
	/* Added 2008/03/22 �ጎ  */
	/* --------------------------------------------------- */
	public TitleLabel() {
		initializeTitleLabel();
	}
	/* --------------------------------------------------- */
	public TitleLabel(String key) {
		this();

		if (key != null && ! key.isEmpty()) {
			this.setText(ViewSettings.getUsingValueString(key));
		}
	}
	
	private void initializeTitleLabel() {
		Font font = ViewSettings.getCommonTitleLabelFont();
		this.setFont(font);
		
		this.setOpaque(true);

		this.setBackground(ViewSettings.getUsingValueColor("common.frame.title-label.bgcolor"));
		this.setForeground(ViewSettings.getUsingValueColor("common.frame.title-label.fgcolor"));
	}
}
