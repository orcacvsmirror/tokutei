package jp.or.med.orca.jma_tokutei.common.frame;

import java.util.HashMap;

/**
 * ��ʗp�ݒ�̃f�t�H���g�l�B
 *
 * ��ʐݒ�p�̊O���t�@�C����ǂݍ��߂Ȃ������ꍇ�A���̃N���X���񋟂���l���g�p����B
 * map �̃L�[���́A��ʐݒ�p�̊O���t�@�C���Ɠ���ɂ���B
 */
public class ViewSettingsDefault implements ViewSettingsKey{

	private static final String VALUE_DIALOG_FONT_PLAIN_14 = "Dialog,Font.PLAIN,14";

	private static HashMap<String, String> map = null;

	private static void initialize(){

		/* --------------------------------------------------
		 *  ��ʋ���
		 * -------------------------------------------------- */
		getMap().put(KEY_COMMON_FRAME_GUIDANCE_LABEL_FONT, VALUE_DIALOG_FONT_PLAIN_14);
		getMap().put(KEY_COMMON_FRAME_TITLE_LABEL_FONT, "Dialog,Font.PLAIN,18");
		getMap().put(KEY_COMMON_FRAME_USER_INPUT_FONT, "Dialog,Font.PLAIN,12");
		getMap().put(KEY_COMMON_FRAME_WIDTH, "850");
		getMap().put(KEY_COMMON_FRAME_HEIGHT, "600");
		getMap().put(KEY_COMMON_FRAME_TITLE_LABEL_FGCOLOR, "51,51,51");
		getMap().put(KEY_COMMON_FRAME_TITLE_LABEL_BGCOLOR, "153,204,255");
		getMap().put(KEY_COMMON_FRAME_REQUIREDITEM_BGCOLOR, "255,255,128");
		getMap().put(KEY_COMMON_FRAME_IMPORTANTITEM_BGCOLOR, "128,255,128");
		getMap().put(KEY_COMMON_FRAME_DISABLEITEM_BGCOLOR, "205,205,205");
		getMap().put(KEY_COMMON_FRAME_PROGRESS_TITLE, "�i����");
		getMap().put(KEY_COMMON_FRAME_PROGRESS_GUIDANCE_MAIN, "���������s���ł��B���΂炭���҂����������B");

		/* --------------------------------------------------
		 *  �V�X�e���Ǘ��җp�\�t�g�E�G�A
		 * -------------------------------------------------- */
		getMap().put(KEY_ADMIN_APPLICATION_NAME, "������茒�N�f���V�X�e���Ǘ��җp�\�t�g�E�F�A");
		getMap().put(KEY_ADMIN_FRAME_TITLE_TEXT, "������茒�N�f���V�X�e���Ǘ�");
		getMap().put(KEY_ADMIN_SPLASH_DISPLAY_TIME,"3500");

		getMap().put(KEY_ADMIN_LOGIN_FRAME_TITLE, "�V�X�e�������e�i���X���O�C��");
		getMap().put(KEY_ADMIN_LOGIN_FRAME_GUIDANCE_MAIN,
				"���[�U���A�p�X���[�h����͌�A���O�C���{�^���������Ă��������B");
		getMap().put(KEY_ADMIN_LOGIN_FRAME_WIDTH, "650");
		getMap().put(KEY_ADMIN_LOGIN_FRAME_HEIGHT, "280");

		/* --------------------------------------------------
		 *  ���茒�f�\�t�g�E�G�A
		 * -------------------------------------------------- */
		/* ���� */
		getMap().put(KEY_TOKUTEI_APPLICATION_NAME, "������茒�N�f���V�X�e��");
		getMap().put(KEY_TOKUTEI_FRAME_TITLE_TEXT, "������茒�N�f���V�X�e��");

		// add s.inoue 2009/12/21
		getMap().put(KEY_TOKUTEI_SYOKEN_DIALOG_TITLE, "�����I��");
		/* �X�v���b�V����� */
		getMap().put(KEY_TOKUTEI_SPLASH_DISPLAY_TIME,"3500");

		/* ���O�C����� */
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_GUIDANCE_MAIN,
				"���f�@�֔ԍ��A���[�U���A�p�X���[�h����͌�A���O�C���{�^���������Ă��������B");
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_WIDTH, "650");
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_HEIGHT, "280");
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_TITLE, "������茒�N�f���V�X�e�����O�C��");

		/* ���C�����j���[ */
		getMap().put(KEY_TOKUTEI_MAINMENU_FRAME_TITLE, "���C�����j���[");

		/* ��f�����͉�� */
		getMap().put(KEY_TOKUTEI_JUSHINKEN_FRAME_TITLE, "��f�����́i�l���o�^�j");
		getMap().put(KEY_TOKUTEI_JUSHINKEN_FRAME_PROGRESS_GUIDANCE_PRINT1,"���͕[�f�[�^���쐬���Ă��܂��B");

		/* ���f���ʃf�[�^�ꗗ��� */
		getMap().put(KEY_TOKUTEI_KEKKALIST_FRAME_TITLE, "���f�E��f���ʃf�[�^�ꗗ");

		/* ���f���ʃf�[�^���͉�� */
		getMap().put(KEY_TOKUTEI_KEKKAINPUT_FRAME_TITLE, "���f�E��f���ʃf�[�^����");

		/* �O���������ʃf�[�^�捞�݉�� */
		getMap().put(KEY_TOKUTEI_IMPORTDATA_FRAME_TITLE, "�O���������ʃf�[�^��荞��");

		/* ���f���ʕ\���E��������@������� */
		getMap().put(KEY_TOKUTEI_SEARCHRESULT_FRAME_TITLE, "���^�{���b�N�V���h���[������E�K�w��");
		getMap().put(KEY_TOKUTEI_SEARCHRESULT_FRAME_GUIDANCE_MAIN,
			"������������͂��A�����{�^���������Č��f�f�[�^��\�����܂��B<br>���f�f�[�^��I�����A��ʉ����̃{�^���������Ċe�������J�n���܂��B");

		/* ���f���ʕ\���E���������� */
		getMap().put(KEY_TOKUTEI_SHOWRESULT_FRAME_TITLE,"���f�E��f���ʃf�[�^�\��");
		getMap().put(KEY_TOKUTEI_SHOWRESULT_FRAME_PROGRESS_GUIDANCE_PRINT1,"���f�E��f���ʃf�[�^���쐬���Ă��܂��B");

		/* ���茒�f�����˗������ */
		getMap().put(KEY_TOKUTEI_IRAISHO_FRAME_TITLE,"�����˗����f�[�^�\��");
		getMap().put(KEY_TOKUTEI_IRAISHO_FRAME_GUIDANCE_MAIN,"�����˗����f�[�^���쐬���Ă��܂��B");

		/* �}�X�^�����e�i���X���j���[��� */
		getMap().put(KEY_TOKUTEI_MASTERMAINTENANCE_MENU_FRAME_TITLE, "�}�X�^�����e�i���X");

		/* �V�X�e�������e�i���X���j���[��� */
		getMap().put(KEY_TOKUTEI_SYSTEMMAINTENANCE_MENU_FRAME_TITLE, "�V�X�e�������e�i���X");

		/* �����f�[�^�ҏW��� */
		// edit ver2 s.inoue 2009/08/06
		getMap().put(KEY_TOKUTEI_INPUTKESSAI_FRAME_TITLE, "��������(�����ҏW)");

		/* �����EHL7�o�͉�� */
//		getMap().put(KEY_TOKUTEI_HL7_FRAME_TITLE, "�����EHL7�o��");
//		getMap().put(KEY_TOKUTEI_HL7_FRAME_GUIDANCE_MAIN,
//			"������������͂��A�����{�^���������Č��f�f�[�^��\�����܂��B<br>���f�f�[�^��I�����A��ʉ����̃{�^���������Ċe�������J�n���܂��B");
		// edit ver2 s.inoue 2009/08/03
		/* ��������(����) */
		getMap().put(KEY_TOKUTEI_NITIJI_FRAME_TITLE, "��������(����)");
		getMap().put(KEY_TOKUTEI_NITIJI_FRAME_GUIDANCE_MAIN,
		"������������͂��A�����{�^���������Č��f�f�[�^��\�����܂��B<br>���f�f�[�^��I�����A��ʉ����̃{�^���������ē�������(����)���J�n���܂��B");

		/* ��������(����) */
		getMap().put(KEY_TOKUTEI_GETUJI_FRAME_TITLE, "��������(�����m��/HL7�o��)");
		getMap().put(KEY_TOKUTEI_GETUJI_FRAME_GUIDANCE_MAIN,
		"������������͂��A�����{�^���������Č��f�f�[�^��\�����܂��B<br>���f�f�[�^��I�����A��ʉ����̃{�^���������Č�������(HL7�o��)���J�n���܂��B");

		/* �@�֏��ҏW��� */
		getMap().put(KEY_TOKUTEI_KIKANINFORMATION_FRAME_WIDTH, "900");
		getMap().put(KEY_TOKUTEI_KIKANINFORMATION_FRAME_HEIGHT, "620");
		getMap().put("tokutei.kikaninformation.frame.title", "�@�֏��|�ǉ�");
		getMap().put("tokutei.kikaninformation.frame.guidance.main", "�@�֏��̒ǉ����s���܂��B�l��ҏW��A�o�^�{�^���������ĉ������B");
		getMap().put("tokutei.kikaninformation.frame.guidance.sub", "��t��łƂ�܂Ƃ߂đ��t�����ꍇ�ȊO�́A���t���@�֔ԍ��ɂ́A���f�@�֔ԍ��Ɠ����ԍ������Ă��������B");

		/* --------------------------------------------------
		 *  �}�X�^�����e�i���X
		 * -------------------------------------------------- */

		/* �����Z���^�[�������ڃR�[�h�}�X�^�����e�i���X */
		getMap().put(KEY_TOKUTEI_KENSACENTER_KENSAITEM_CODE_MASTERMAINTENANCE_FRAME_TITLE, "�����Z���^�[�������ڃR�[�h�}�X�^�����e�i���X");

		/* ���f���ڃ}�X�^�����e�i���X��� */
		getMap().put(KEY_TOKUTEI_KENSHIN_ITEM_MASTERMAINTENANCE_FRAME_TITLE, "���f���ڃ����e�i���X");

		/* �������ڃR�[�h�}�X�^�����e�i���X */
		getMap().put(KEY_TOKUTEI_KENSAITEM_CODE_MASTERMAINTENANCE_FRAME_TITLE, "�������ڃR�[�h�}�X�^�����e�i���X");

		/* �ی��ҏ��}�X�^�����e�i���X */
		getMap().put(KEY_TOKUTEI_HOKENJA_MASTERMAINTENANCE_FRAME_TITLE, "�ی��ҏ�񃁃��e�i���X");

		/* ���f�p�^�[�������e�i���X */
		getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_FRAME_TITLE, "���f�p�^�[�������e�i���X");

		/* �x����s���}�X�^�����e�i���X */
		getMap().put(KEY_TOKUTEI_SHIHARAI_MASTERMAINTENANCE_FRAME_TITLE, "�x����s�����e�i���X");

		/* �ی��ҏ��}�X�^�����e�i���X */
		//getMap().put(KEY_TOKUTEI_HOKENJA_MASTERMAINTENANCE_ADD_FRAME_TITLE, "�ی��ҏ�񃁃��e�i���X|�ǉ�");
		/* �ی��ҏ��}�X�^�����e�i���X */
		//getMap().put(KEY_TOKUTEI_HOKENJA_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "�ی��ҏ�񃁃��e�i���X|�o�^/�X�V/�폜");
		/* ���f�p�^�[�������e�i���X�ҏW */
		//getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "���f�p�^�[�������e�i���X|�ҏW");
		/* ���f�p�^�[�������e�i���X�ǉ� */
		//getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_ADD_FRAME_TITLE, "���f�p�^�[�������e�i���X|�ǉ�");
		/* ���f�p�^�[�������e�i���X���� */
		//getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_COPY_FRAME_TITLE, "���f�p�^�[�������e�i���X|����");
		/* �@�֏�񃁃��e�i���X�ǉ� */
		//getMap().put(KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_ADD_FRAME_TITLE, "�@�֏�񃁃��e�i���X|�ǉ�");
		/* �@�֏�񃁃��e�i���X */
		//getMap().put(KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_FRAME_TITLE, "�@�֏�񃁃��e�i���X");
		/* �@�֏�񃁃��e�i���X�ҏW */
		//getMap().put(KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "�@�֏�񃁃��e�i���X|�ҏW");
		/* �x����s���}�X�^�����e�i���X */
		//getMap().put(KEY_TOKUTEI_SHIHARAI_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "�x����s�����e�i���X|�ҏW");
	}

	public static String getPorperty(String key){
		String value = getMap().get(key);
		return value;
	}

	private static HashMap<String, String> getMap() {
		if (map == null) {
			map = new HashMap<String, String>();
			initialize();
		}

		return map;
	}
}


