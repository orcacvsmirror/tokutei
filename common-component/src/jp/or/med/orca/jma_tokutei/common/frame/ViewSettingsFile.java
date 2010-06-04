package jp.or.med.orca.jma_tokutei.common.frame;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

/**
 * ��ʗp�ݒ�t�@�C��
 */
public class ViewSettingsFile {

	private static final String PROPERTY_FILE_NAME = "view.properties";
	private static Properties properties;

//	static {
//		initialize();
//	}

	/**
	 * ������
	 */
	private static boolean initialize(){
		boolean success = false;

		try	{
			FileInputStream fs = new FileInputStream(PROPERTY_FILE_NAME);
			InputStreamReader in = new InputStreamReader(fs, "Shift_JIS");
			properties.load(in);

			/* ���b�Z�[�W�t�@�C���̃o�[�W���������؂���B */
			String fileVersionNumber = getProperty("VersionNumber");

			if (fileVersionNumber.equals(JApplication.versionNumber)) {
				success = true;
			}
			else {
				JErrorMessage.show("M0002", null);
			}
		}
		catch(Exception e)	{
			success = false;
		}

		if (success) {
			success = syntaxCheck();
		}

		return success;
	}

	/**
	 * properties ���擾����B
	 */
	private static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			initialize();
		}

		return properties;
	}

	/**
	 * �v���p�e�B�t�@�C���̕��@�`�F�b�N���s��
	 */
	private static boolean syntaxCheck()	{
		boolean success = false;

		if (properties != null){
			Enumeration<Object> items = properties.keys();

			success = true;
			while(items.hasMoreElements()){

				String keyName = (String)items.nextElement();
				String right = properties.getProperty(keyName);
				String[] values = right.split(",");

				// �z��̗v�f�O�Q�ƃG���[���N����\������
				if(values[0].length() <= 0 ){
//					new JErrorMessageDialogFrameCtrl(null,
//							"�G���[","��ʗp�ݒ�t�@�C���̓ǂݍ��݂Ɏ��s���܂����B�t�@�C�����m�F���Ă��������B" +
//									"�G���[����������ID�́u" + keyName + "�v�ł��B" ,0,0,"M0103");

					success = false;
					break;
				}
			}
		}

		return success;
	}

	/**
	 * <p>��ʗp�ݒ�l���擾����B</p>
	 * @return String ��ʗp�ݒ�l
	 */
	public static String getProperty(String id) {

		String value = (String)getProperties().get(id);

		if(value == null){
			value = "";
		}

		return value;
	}
}
