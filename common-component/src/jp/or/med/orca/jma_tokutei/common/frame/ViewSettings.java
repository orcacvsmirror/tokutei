package jp.or.med.orca.jma_tokutei.common.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.util.Enumeration;
//import java.util.Properties;
//
//import javax.swing.JFrame;
//
//import jp.or.med.orca.jma_tokutei.errormessage.JErrorMessageDialogFrameCtrl;

/**
 * ��ʃN���X�ݒ�
 *
 * New 2008/03/07 �ጎ
 * �@���݁A�e���\�b�h�ŌŒ�l��Ԃ��Ă��邪�A�����I�ɂ̓v���p�e�B�t�@�C���܂���
 * �@�f�[�^�x�[�X����l��ǂݍ��ނ悤�ɂ���\��B
 */
public class ViewSettings {

//	private static final String PROPERTY_FILE_NAME = "view.properties";
//	private static Properties properties;

	private static String commonTitle = null;

	/* Added 2008/05/15 �ጎ  */
	/* --------------------------------------------------- */
	private static String adminCommonTitleWithVersion = null;
	/* --------------------------------------------------- */

	private static String commonTitleWithKikanInfomation = null;

	/**
	 * �L�[���w�肵�āA�ݒ�t�@�C�������ʗp�ݒ�l���擾����B
	 * �ݒ�p�t�@�C����ǂݍ��߂Ȃ��ꍇ�́A�f�t�H���g�l�N���X����f�t�H���g�l��
	 * �擾����B
     */
	private static String getUsingValue(String key) {

		String value = ViewSettingsFile.getProperty(key);

		if (value == null || value.isEmpty()) {
			value = ViewSettingsDefault.getPorperty(key);
		}

		return value;
	}

	/**
	 * <p>�V�X�e���Ǘ��җp�\�t�g�E�G�A�� Frame �̋��ʃ^�C�g�����擾����</p>
	 * @eturn String ���ʃ^�C�g��
	 */
	public static String getAdminFrameCommonTitle() {
		String value = commonTitle;

		if (commonTitle == null || commonTitle.isEmpty()) {
			value = getUsingValue("admin.frame.title.text");
		}

		return value;
	}

	public static void setAdminFrameTitle(String title){
		commonTitle = title;
	}

	public static void setAdminFrameTitleWithVersion(String title){
		adminCommonTitleWithVersion = title;
	}

	/**
	 * <p>���茒�f�\�t�g�E�G�A�� Frame �̋��ʃ^�C�g�����擾����</p>
	 * @eturn String ���ʃ^�C�g��
	 */
	public static String getTokuteFrameTitle() {
		String value = commonTitle;

		/* Modified 2008/05/14 �ጎ  */
		/* --------------------------------------------------- */
//		if (commonTitle == null || commonTitle.isEmpty()) {
//			value = getUsingValue("tokutei.frame.title.text");
//		}
		/* --------------------------------------------------- */
		value = getUsingValue("tokutei.frame.title.text");
		/* --------------------------------------------------- */

		return value;
	}

	public static void setTokuteFrameTitleWithKikanInfomation(String title){
		commonTitleWithKikanInfomation = title;
	}
	public static String getTokuteFrameTitleWithKikanInfomation(){
		return commonTitleWithKikanInfomation;
	}

	/**
	 * <p>Frame �̋��ʃT�C�Y���擾����</p>
	 * @eturn String ���ʃT�C�Y
	 */
	public static Dimension getFrameCommonSize() {
		int width = getUsingValueInt("common.frame.width");
		int height = getUsingValueInt("common.frame.height");

		return new Dimension(width, height);
	}

	/**
	 * <p>�@�֏���ʂ̋��ʃT�C�Y���擾����</p>
	 * @eturn String ���ʃ^�C�g��
	 */
	public static Dimension getKikanInformationFrameSize() {
		int width = getUsingValueInt("tokutei.kikaninformation.frame.width");
		int height = getUsingValueInt("tokutei.kikaninformation.frame.height");

		return new Dimension(width, height);
	}

	/**
	 * int �^�̐ݒ�l���擾����B
     */
	public static int getUsingValueInt(String key) {
		int value = 0;

		try {
			value = Integer.parseInt(getUsingValue(key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * �K�{���ڂ̔w�i�F���擾����B
	 * @return Color
	 */
	public static Color getRequiedItemBgColor() {
		Color color = getUsingValueColor("common.frame.requireditem.bgcolor");

		return color;
	}

	/**
	 * �d�v���ڂ̔w�i�F���擾����B
	 * @return Color
	 */
	public static Color getImportantItemBgColor() {
		Color color = getUsingValueColor("common.frame.importantitem.bgcolor");

		return color;
	}

	/**
	 * ���͕s���ڂ̔w�i�F���擾����B
	 * @return Color
	 */
	public static Color getDisableItemBgColor() {
		Color color = getUsingValueColor("common.frame.disableitem.bgcolor");

		return color;
	}

	// add h.yoshitama 2009/09/18
	/**
	 * ���͉\���ڂ̔w�i�F���擾����B
	 * @return Color
	 */
	public static Color getAbleItemBgColor() {
		Color color = getUsingValueColor("common.frame.ableitem.bgcolor");

		return color;
	}

	/**
	 * String �^�̐ݒ�l���擾����B
     */
	public static String getUsingValueString(String key) {
		String value = getUsingValue(key);

		if (value == null) {
			value = "";
		}

		return value;
	}

	/**
	 * Color �^�̐ݒ�l���擾����B
     */
	public static Color getUsingValueColor(String key) {
		String value = getUsingValue(key).trim();

		int r = 0;
		int g = 0;
		int b = 0;

		Color color = Color.BLACK;

		if (value != "") {
			String[] values = value.split(",");

			if (values != null && values.length == 3) {

				try {
					r = Integer.parseInt(values[0]);
					g = Integer.parseInt(values[1]);
					b = Integer.parseInt(values[2]);

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				if (r >= 0 && g >= 0 && b >= 0
						&& r <= 255 && g <= 255 && b <= 255) {

					color = new Color(r,g,b);
				}
			}
		}

		return color;
	}

	/**
	 * ���ʂ� Frame �̕����擾����B
     */
	public static int getFrameCommonWidth() {
		int width = getUsingValueInt("common.frame.width");
		return width;
	}

	/**
	 * ���ʂ� Frame �̍������擾����B
     */
	public static int getFrameCommonHeight() {
		int height = getUsingValueInt("common.frame.height");
		return height;
	}

	/**
	 * �V�X�e���Ǘ��җp�\�t�g�E�G�A�̃��O�C����ʂ� Frame �̃T�C�Y���擾����B
     */
	public static Dimension getAdminLoginFrameSize() {
		int width = getAdminLoginFrameWidth();
		int height = getAdminLoginFrameHeight();

		Dimension size = new Dimension(width, height);
		return size;
	}

	/**
	 * �V�X�e���Ǘ��җp�\�t�g�E�G�A�̃��O�C����ʂ� Frame �̍������擾����B
     */
	public static int getAdminLoginFrameWidth() {
		int width = getUsingValueInt("admin.login.frame.width");
		return width;
	}

	/**
	 * �V�X�e���Ǘ��җp�\�t�g�E�G�A�̃��O�C����ʂ� Frame �̍������擾����B
     */
	public static int getAdminLoginFrameHeight() {
		int height = getUsingValueInt("admin.login.frame.height");
		return height;
	}

	/**
	 * �V�X�e���Ǘ��җp�\�t�g�E�G�A�̃X�v���b�V����ʂ̕\�����Ԃ��擾����B
     */
	public static int getAdminSplashDisplayTime() {
		int height = getUsingValueInt("admin.splash.display-time");
		return height;
	}

	/**
	 * ���茒�f�\�t�g�E�G�A�̃X�v���b�V����ʂ̕\�����Ԃ��擾����B
     */
	public static int getTokuteiSplashDisplayTime() {
		int height = getUsingValueInt("tokutei.splash.display-time");
		return height;
	}

	/**
	 * ���茒�f�\�t�g�E�G�A�̃��O�C����ʂ� Frame �̕����擾����B
     */
	public static int getTokuteiLoginFrameWidth() {
		int width = getUsingValueInt("tokutei.login.frame.width");
		return width;
	}

	/**
	 * ���茒�f�\�t�g�E�G�A�̃��O�C����ʂ� Frame �̍������擾����B
     */
	public static int getTokuteiLoginFrameHeight() {
		int height = getUsingValueInt("tokutei.login.frame.height");
		return height;
	}

	/**
	 * ���茒�f�\�t�g�E�G�A�̃��O�C����ʂ� Frame �̃T�C�Y���擾����B
     */
	public static Dimension getTokuteiLoginFrameSize() {
		Dimension d = new Dimension(getTokuteiLoginFrameWidth(), getTokuteiLoginFrameHeight());
		return d;
	}

//	/**
//	 * ���ʃK�C�_���X�p�t�H���g���擾����B
//	 * @return Font
//	 */
//	public static Font getGuidanceLableFont() {
//		return getFontFromId("common.frame.guidance-lable.font");
//	}

	/**
	 * ���ʓ��͗p�t�H���g���擾����B
	 * @return Font
	 */
	public static Font getCommonUserInputFont() {
		return getFontFromId("common.frame.user-input.font");
	}

	/**
	 * ���ʓ��͗p�t�H���g���擾����B
	 * @return Font
	 */
	public static Font getCommonUserInputFont(int style) {
		return getFontFromId("common.frame.user-input.font", style);
	}

	/**
	 * �^�C�g�����x���p���ʃt�H���g���擾����B
	 * @return Font
	 */
	public static Font getCommonTitleLabelFont() {
		return getFontFromId("common.frame.title-label.font");
	}

	/**
	 * �K�C�_���X���x���p���ʃt�H���g���擾����B
	 * @return Font
	 */
	public static Font getGuidanceLabelFont() {
		return getFontFromId("common.frame.guidance-label.font");
	}

//	/**
//	 * ��ʗp�ݒ�t�@�C���� ID ���w�肵�ăt�H���g���擾����B
//	 */
//	private static Font getFontFromId(String fontId) {
//		Font font = null;
//
//		String[] properties = getUsingValue(fontId).split(",");
//
//		String name = "";
//		int style = 0;
//		int size = 0;
//
//		if (properties != null &&  properties.length == 3) {
//			/* �t�H���g�� */
//			name = properties[0].trim();
//
//			/* �t�H���g�X�^�C�� */
//			try {
//				style = Font.class.getDeclaredField(properties[1].trim()).getInt(null);
//
//			} catch (Exception e) {
//				/* �f�t�H���g�l���g�p����B */
//			}
//
//			/* �t�H���g�T�C�Y */
//			try {
//				size = Integer.parseInt(properties[2].trim());
//			} catch (NumberFormatException e) {
//				/* �f�t�H���g�l���g�p����B */
//			}
//
//			font = new Font(name, style, size);
//		}
//
//		return font;
//	}

	/**
	 * ��ʗp�ݒ�t�@�C���� ID �ƃX�^�C�����w�肵�ăt�H���g���擾����B
	 */
	private static Font getFontFromId(String fontId) {
		return getFontFromId(fontId, -1);

//		Font font = null;
//
//		String name = "";
//		int size = 0;
//		int style = 0;
//
//		String[] properties = getUsingValue(fontId).split(",");
//
//		if (properties != null &&  properties.length == 3) {
//			/* �t�H���g�� */
//			name = properties[0].trim();
//
//			/* �t�H���g�X�^�C�� */
//			try {
//				style = Font.class.getDeclaredField(properties[1].trim()).getInt(null);
//
//			} catch (Exception e) {
//				/* �f�t�H���g�l���g�p����B */
//			}
//
//			/* �t�H���g�T�C�Y */
//			try {
//				size = Integer.parseInt(properties[2].trim());
//			} catch (NumberFormatException e) {
//				/* �f�t�H���g�l���g�p����B */
//			}
//
//			font = new Font(name, style, size);
//		}
//
//		return font;
	}

	/**
	 * ��ʗp�ݒ�t�@�C���� ID �ƃX�^�C�����w�肵�ăt�H���g���擾����B
	 */
	private static Font getFontFromId(String fontId, int style) {
		Font font = null;

		String name = "";
		int size = 0;
		int useStyle = style;

		String[] properties = getUsingValue(fontId).split(",");

		if (properties != null &&  properties.length == 3) {
			/* �t�H���g�� */
			name = properties[0].trim();

			if (style == -1) {
				/* �t�H���g�X�^�C�� */
				try {
					String settingStyle = properties[1].trim();
					useStyle = Font.class.getDeclaredField(settingStyle).getInt(null);

				} catch (Exception e) {
					/* �f�t�H���g�l���g�p����B */
					useStyle = Font.PLAIN;
				}
			}

			/* �t�H���g�T�C�Y */
			try {
				size = Integer.parseInt(properties[2].trim());
			} catch (NumberFormatException e) {
				/* �f�t�H���g�l���g�p����B */
			}

			font = new Font(name, useStyle, size);
		}

		return font;
	}

	/* Added 2008/05/15 �ጎ  */
	/* --------------------------------------------------- */
	public static String getAdminCommonTitleWithVersion() {
		return adminCommonTitleWithVersion;
	}

	public static void setAdminCommonTitleWithVersion(String title) {
		ViewSettings.adminCommonTitleWithVersion = title;
	}
	/* --------------------------------------------------- */

//	/**
//	 * ��ʗp�ݒ�t�@�C���� ID ���w�肵�ăt�H���g���擾����B
//	 */
//	private static Font getFontFromId(String fontId, int style) {
//		Font font = null;
//
//		String name = "";
//		int size = 0;
//
//		String[] properties = getUsingValue(fontId).split(",");
//
//		if (properties != null &&  properties.length == 3) {
//			/* �t�H���g�� */
//			name = properties[0].trim();
//
//			font = new Font(name, style, size);
//		}
//
//		return font;
//	}

//	/**
//	 * ������
//	 */
//	public static boolean initialize(){
//		boolean success = false;
//
//		properties = new Properties();
//		try	{
//			FileInputStream fs = new FileInputStream(PROPERTY_FILE_NAME);
//			InputStreamReader in = new InputStreamReader(fs, "Shift_JIS");
//			properties.load(in);
//			success = true;
//		}
//		catch(Exception e)	{
//			success = false;
//		}
//
//		if (success) {
//			syntaxCheck();
//		}
//
//		return success;
//	}
//
//	/**
//	 * �v���p�e�B�t�@�C���̕��@�`�F�b�N���s��
//	 */
//	private static boolean syntaxCheck()	{
//		boolean success = false;
//
//		if (properties != null){
//			Enumeration<Object> items = properties.keys();
//
//			while(items.hasMoreElements()){
//
//				String keyName = (String)items.nextElement();
//				String right = properties.getProperty(keyName);
//				String[] values = right.split(",");
//
//				// �z��̗v�f�O�Q�ƃG���[���N����\������
//				if(values[0].length() <= 0 ){
//					new JErrorMessageDialogFrameCtrl(null,
//							"�G���[","��ʗp�ݒ�t�@�C���̓ǂݍ��݂Ɏ��s���܂����B�t�@�C�����m�F���Ă��������B" +
//									"�G���[����������ID�́u" + keyName + "�v�ł��B" ,0,0,"M0103");
//					break;
//				}
//			}
//
//			success = true;
//		}
//
//		return success;
//	}

//	/**
//	 * <p>��ʗp�ݒ�l���擾����B</p>
//	 * @return String ��ʗp�ݒ�l
//	 */
//	public static String getProperty(String id, JFrame parent) {
//
//		String value = (String)properties.get(id);
//
//		if(value == null){
//			new JErrorMessageDialogFrameCtrl(
//					parent,
//					"�G���[","���b�Z�[�W�̉�͂Ɏ��s���܂����B",0,0,"M0102");
//
//			value = "";
//		}
//
//		return value;
//	}
//
//	public static String getProperty(String id) {
//		String value = ViewSettings.getProperty(id, null);
//		return value;
//	}
}


