package jp.or.med.orca.jma_tokutei.kenshin.healthexamination;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.frame.JSplashFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.SettingDialog;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu.JLoginFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu.JLoginFrameCtrl;

/**
 * ���茒�f�\�t�g�E�G�A
 *
 * Modified 2008/03/09 �ጎ
 * �@��d�N���h�~�@�\�ύX�Ή��B
 *
 * �@�E�P��̒[���œ������f�@�ւ̌��f�@�֗p�V�X�e�����N������̂�h�~����B
 * �@�E�P��̒[���ŃV�X�e���Ǘ��җp�V�X�e���ƌ��f�@�֗p�V�X�e�����N������
 * �@�@�̂�h�~����B
 * �@�E�P��̒[���ŃV�X�e���Ǘ��җp�V�X�e���𕡐��N������̂�h�~����B
 * �@�E�P��̒[���ŕʁX�̌��f�@�֗p�V�X�e�����N������̂�������B
 *
 * �@�֔ԍ��̓��[�U�̓��͒l����擾���邽�߁A������ JApplication �N���X�ֈړ������B
 */
public class JSoftware{
//	static {
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Throwable tw) { }
//	}

	private  static JSplashFrameCtrl splashFrame = null;

	public static JSplashFrameCtrl getSplashFrame(){
		// add s.inoue 20090113 reload����
		return splashFrame;
	}

	public static void main(String[] args) {
		initialize(args);
	}

	public static void reloadSplashFlame(){
		// add s.inoue 20090113 reload����
		loadVersions(true);
	}

	private static void initialize(String[] args){

		try {
			/* �G���[���b�Z�[�W�Ǎ� */
			String messageVersion = JErrorMessage.load();
			if (messageVersion == null || messageVersion.isEmpty()) {
				System.exit(1);
			}

			if (args.length >= 1) {
				// rds test switch
				JApplication.runtimeTest(args[0]);
			}

			// �o�[�W�������̓ǂݍ���
			JApplication.loadVersionFile("MainVersion.properties");

			// ���b�Z�[�W�t�@�C���̃o�[�W���������؂���B
			if (! messageVersion.equals(JApplication.versionNumber)) {
				JErrorMessage.show("M0001", null);
			}

			// move s.inoue 2013/03/12 ��ɍs��
			// add s.inoue 2009/12/18
			String lookAndFeel = getAppSettings();
			if (!lookAndFeel.equals("")){
				SettingDialog sd = new SettingDialog();
				sd.changeTheLookAndFeel(lookAndFeel,true);
			}

			// �ݒ�t�@�C�����[�h
			JApplication.load();

			// �A�b�v�f�[�g�\�t�g�E�F�A�ďo
// 20081111 add s.inoue
//
//			// s.inoue 20080821
//			// FDB�ւ̐ڑ������K�v�B���ʂŎg�p���Ă���ׁA
//			// �I�����C���A�b�v�f�[�g�����ł̓N���[�Y���ď������s���B
//			JApplication.systemDatabase.Shutdown();
//
//			JMainFrame.executeApplicationTaskXML();
//
//			// FDB�ւ̐ڑ������K�v�B���ʂŎg�p���Ă���ׁA
//			// �I�����C���A�b�v�f�[�g�����I����I�[�v������B
//			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();
//			// �X�V������ɍă��[�h
//			JApplication.loadVersionFile("MainVersion.properties");

			// add s.inoue 20090113
			loadVersions(false);
		} catch (Exception e) {
			System.exit(1);
		}

		// �@�փf�[�^�x�[�X���o�^����Ă��邩�m�F
		if (JConnection.IsExistKikanDatabase() == false) {
			JErrorMessage.show("M1001", null);
			System.exit(1);
		}

		JScene.ChangeScene(new JLoginFrameCtrl());
	}

	// add s.inoue 2009/12/18
	private static String getAppSettings(){
		return PropertyUtil.getProperty( "setting.lookAndFeel");
	}

	public static void loadVersions(boolean flgReload){

		// edit s.inoue 20090113
		String strSchema = "";
		String strDBData = "";

		if (!JApplication.SchemaVersion.equals(""))
		{
			strSchema = "      Schema " + JApplication.SchemaVersion;
		}
		if (!JApplication.DBDataVersion.equals(""))
		{
			strDBData = "      DBData " + JApplication.DBDataVersion;
		}
		// 20090113 s.inoue reload�p
		if (flgReload){
			splashFrame = null;
		}
		// �X�v���b�V����ʕ\��
		splashFrame = new JSplashFrameCtrl(
				"MainVersion " + JApplication.versionNumber,
				"        Module " + JApplication.Moduleversion,
				strSchema,
				strDBData,
				//"      Schema " + JApplication.SchemaVersion,
				//"       DBData " + JApplication.DBDataVersion,
				JPath.SplashPath,flgReload) {

			public void loadingProc() {
//				// �ݒ�t�@�C�����[�h
//				JApplication.load();
			}
		};
	}
}



// Source Code Version Tag System v1.00 -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

