package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Font;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * ��ʃ^�C�g���p���x��
 */
public class GuidanceLabel extends ExtendedLabel {
    /**
     * �R���X�g���N�^
     */
    public GuidanceLabel(String key) {
	Font font = ViewSettings.getGuidanceLabelFont();
	this.setFont(font);
	this.setText(ViewSettings.getUsingValueString(key));
    }
}
