package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Font;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * 画面タイトル用ラベル
 */
public class GuidanceLabel extends ExtendedLabel {
    /**
     * コンストラクタ
     */
    public GuidanceLabel(String key) {
	Font font = ViewSettings.getGuidanceLabelFont();
	this.setFont(font);
	this.setText(ViewSettings.getUsingValueString(key));
    }
}
