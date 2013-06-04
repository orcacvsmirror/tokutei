package jp.or.med.orca.jma_tokutei.common.errormessage;

import java.util.*;
import java.io.*;
import javax.swing.JFrame;

public class JErrorMessage {

	private static final String KEY_VERSION_NUMBER = "VersionNumber";

	private JErrorMessage() {
	}

	private static Properties mProperties;

	private static String LoadFile = "ErrorMessage.properties";

	/**
	 * ���b�Z�[�W�t�@�C����ǂݍ���
	 */
	public static String load() {
		Properties prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(LoadFile);
			InputStreamReader in = new InputStreamReader(fs, "SJIS");
			prop.load(in);
			mProperties = prop;

		} catch (Exception e) {
			new JErrorMessageDialogFrameCtrl(null, "�G���[",
					"�G���[���b�Z�[�W��`�t�@�C���̃��[�h�Ɏ��s���܂����B�t�@�C�����m�F���Ă��������B", 0, 0, "M0100");
			System.exit(1);
		}

		/* �e�s�̃V���^�b�N�X�����؂���B */
		boolean success = syntaxCheck();

		/* �o�[�W�����ԍ� */
		String versionNumuber = null;
		if (success) {
			/* ���b�Z�[�W�t�@�C���̃o�[�W�������擾��B */
			versionNumuber = prop.getProperty(KEY_VERSION_NUMBER);
		}

		return versionNumuber;
	}

	/**
	 * ���b�Z�[�W��`�t�@�C���̕��@�`�F�b�N���s��
	 */
	private static boolean syntaxCheck() {
		boolean validated = true;

		Enumeration<Object> items = mProperties.keys();

		while (items.hasMoreElements() == true) {
			String keyName = (String) items.nextElement();
			String right = mProperties.getProperty(keyName);

			if (keyName.equals(KEY_VERSION_NUMBER)) {
				continue;
			}

			String[] values = right.split(",");

			try {
				// �z��̗v�f�O�Q�ƃG���[���N����\������
				if (values[0].length() <= 0 || values[1].length() <= 0
						|| values[2].length() <= 0 || values[3].length() <= 0) {
					throw new RuntimeException();
				}

				// �v�f�̒l�`�F�b�N
				// edit s.inoue 2010/02/09
				// �v�f���ǉ� 3��4
				if (Integer.valueOf(values[2]) < 0
						|| 4 < Integer.valueOf(values[2])) {
					throw new RuntimeException();
				}
			} catch (Exception e) {
				validated = false;
				new JErrorMessageDialogFrameCtrl(null, "�G���[",
						"���b�Z�[�W�̉�͂Ɏ��s���܂����B" + "�G���[�����������ӏ��̃��b�Z�[�WID�́u" + keyName
								+ "�v�ł��B", 0, 0, "M0101");

				e.printStackTrace();
				break;
				// System.exit(1);
			}
		}

		return validated;
	}

	// add s.inoue 2010/10/27
	public static String getMessageValue(String ID){
		String retErrMessage = "";
		try{
			String right = (String) mProperties.get(ID);
			if (right == null) {
				throw new RuntimeException();
			}
			String[] values = right.split(",");
			retErrMessage = values[1];
		}catch(Exception ex){
			new JErrorMessageDialogFrameCtrl(null, "�G���[",
					"���b�Z�[�W�̉�͂Ɏ��s���܂����B" + "�G���[�����������ӏ��̃��b�Z�[�WID�́u" + ID
							+ "�v�ł��B", 0, 0, "M0101");

			ex.printStackTrace();
		}
		return retErrMessage;
	}
	/**
	 * �_�C�A���O�̕\��
	 *
	 * @param ID
	 *            �G���[ID
	 * @param ParentFrame
	 *            �e�t���[��
	 * @return �������{�^���̒l
	 */
	public static RETURN_VALUE show(String ID, JFrame ParentFrame) {
		String right = (String) mProperties.get(ID);
		if (right == null) {
			new JErrorMessageDialogFrameCtrl(ParentFrame, "�G���[",
					"���b�Z�[�W�̉�͂Ɏ��s���܂����B", 0, 0, "M0102");
			throw new RuntimeException();
		}

		String[] values = right.split(",");

		// �e�����֕���
		String ErrorTitle = values[0];
		String ErrorMessage = values[1];
		int buttonType = Integer.valueOf(values[2]);
		int defaultButton = Integer.valueOf(values[3]);

		JErrorMessageDialogFrameCtrl Dialog = new JErrorMessageDialogFrameCtrl(
				ParentFrame, ErrorTitle, ErrorMessage, buttonType,
				defaultButton, ID);

		return Dialog.getStatus();
	}

	/**
	 * �_�C�A���O�̕\��
	 *
	 * @param ID
	 *            �G���[ID
	 * @param ParentFrame
	 *            �e�t���[��
	 * @return �������{�^���̒l
	 */
	public static RETURN_VALUE show(
			String ID, JFrame ParentFrame, Object[] params) {

		String right = (String) mProperties.get(ID);
		if (right == null) {
			new JErrorMessageDialogFrameCtrl(ParentFrame, "�G���[",
					"���b�Z�[�W�̉�͂Ɏ��s���܂����B", 0, 0, "M0102");
			throw new RuntimeException();
		}

		String[] values = right.split(",");

		// �e�����֕���
		String ErrorTitle = values[0];
		String ErrorMessage = values[1];

		int buttonType = Integer.valueOf(values[2]);
		int defaultButton = Integer.valueOf(values[3]);

//		JErrorMessageDialogFrameCtrl Dialog = new JErrorMessageDialogFrameCtrl(
//				ParentFrame,
//				ErrorTitle,
//				ErrorMessage,
//				buttonType,
//				defaultButton,
//				ID);

		JErrorMessageDialogFrameCtrl Dialog = new JErrorMessageDialogFrameCtrl(
				ParentFrame,
				ErrorTitle,
				ErrorMessage,
				params,
				buttonType,
				defaultButton,
				ID);

		return Dialog.getStatus();
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

