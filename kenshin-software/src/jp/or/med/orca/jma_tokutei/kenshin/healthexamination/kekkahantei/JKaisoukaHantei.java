package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei;

import java.util.ArrayList;

/**
 * �K�w�����菈��
 */
public class JKaisoukaHantei {
	private static final String CODE_KITSUEN = "9N736000000000011";
	private static final String CODE_HDL_1 = "3F070000002327101";
	private static final String CODE_CYUSEI_SHIBOU_1 = "3F015000002327101";
	private static final String CODE_KAKUCHOKI_KETSUATSU_1 = "9A761000000000001";
	private static final String CODE_SYUSYUKUKI_KETSUATSU_1 = "9A751000000000001";
	private static final String CODE_HBA1C = "3D045000001906202";
	private static final String CODE_KUHUKUZI_KETO_1 = "3D010000001926101";
	private static final String CODE_BMI = "9N011000000000001";
	private static final String CODE_HUKUI_ZISOKU = "9N016160100000001";
	private static final String CODE_NAIZOU_SHIBOU_MENSEKI = "9N021000000000001";
	private static final String CODE_HUKUYAKU_3 = "9N711000000000011";
	private static final String CODE_HUKUYAKU_2 = "9N706000000000011";
	private static final String CODE_HUKUYAKU_1 = "9N701000000000011";
	public final static int SEKKYOKUTEKI_SHIEN = 1;
	public final static int DOUKIDUKE_SHIEN = 2;
	public final static int JYOUHOU_TEIKYOU = 3;
	public final static int HANTEI_HUNOU = 4;

	public static int Hantei(JKaisoukaHanteiData data)
	{
		/*
		 * �ȉ��̊K�w�����tokuteikenshin.jp��http://tokuteikenshin.jp/update/2-1/latest/systemdoc-appe20080205.pdf��
		 * ���������ɂ��āA������s���Ă���B
		 * �R�����g�̑����́A���̎����̒��̊K�w����̍����Q�Ƃ��Ă���A���̃\�[�X�R�[�h�Ɏ��������
		 * �K�v������ꍇ�A���̎����ƌ���ׂȂ����Ƃ����Ă������������B
		 * �����l���ڂ̏����ɂ��Ă��A��L�̎��������Ƃɍ쐬���Ă���B
		 */

		// ���茋��
		int Code = 0;

		/* ���� */
		String gender = data.getGender();

		double ketsuatsuHukuyaku = data.getKetsuatsuHukuyaku();
		double kettoHukuyaku = data.getKettoHukuyaku();
		double shishitsuHukuyaku = data.getShishitsuHukuyaku();
		// ����̃`�F�b�N
		// ����[����̂����ꂩ���u���򂠂�v�ł���΁A���񋟃��x���Ƃ��āB����I��
		// ����Ɋւ��ẮA��Ԑ�Ƀ`�F�b�N���s���B
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,	kettoHukuyaku) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
		)
		{
			Code = JYOUHOU_TEIKYOU;
			return Code;
		}

		// �X�e�b�v1
		// �Y���Ȃ� = 0
		int Step1 = 0;
		boolean Step1Kesson = false;

		double naizouShibou = data.getNaizouShibou();
		double hukui = data.getHukui();
		// �������b�ʐ�
		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_NAIZOU_SHIBOU_MENSEKI, gender,naizouShibou) == JKaisoukaHanteiItemCheck.TRUE)
		{
			Step1 = 1;
		}else{
			// �������b�ʐς������ꍇ
			if(JKaisoukaHanteiItemCheck.CheckItem(CODE_NAIZOU_SHIBOU_MENSEKI, gender,naizouShibou) == JKaisoukaHanteiItemCheck.KESSON)
			{
				// ����
				if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUI_ZISOKU, gender,hukui) == JKaisoukaHanteiItemCheck.TRUE)
				{
					Step1 = 1;
				}
				else if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUI_ZISOKU, gender,hukui) == JKaisoukaHanteiItemCheck.KESSON){
					Step1Kesson = true;
				}
			}
		}

		double bmi = data.getBMI();
		// �p�^�[��1�ɊY�����Ȃ��ꍇ
		if(Step1 == 0)
		{
			// BMI
			if(JKaisoukaHanteiItemCheck.CheckItem(CODE_BMI, gender,	bmi) == JKaisoukaHanteiItemCheck.TRUE)
			{
				Step1 = 2;
			}
		}

		if(Step1 == 0)
		{
			return 3;
		}

		// �X�e�b�v2

		// �J�e�S��1
		boolean Category1 = false;
		boolean Category1Kesson = false;

		double kuhukujiKetto = data.getKuhukujiKetto();
		int kuhukuziKeto1Check = JKaisoukaHanteiItemCheck.CheckItem(CODE_KUHUKUZI_KETO_1, gender, kuhukujiKetto);

		// �󕠎�����
		if(kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.TRUE)
		{
			Category1 = true;
		}else{
			if(kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.KESSON)
			{

				/* �󕠎����������݂��Ȃ��ꍇ�̂݁A�J�e�S���[�P�������Ƃ��Ă������߁A�C�� */
				// HbA1c
				int hba1cCheck = JKaisoukaHanteiItemCheck.CheckItem(CODE_HBA1C, gender, data.getHbA1c());
				if(hba1cCheck == JKaisoukaHanteiItemCheck.TRUE)
				{
					Category1 = true;
				}

				// �����`�F�b�N
				// NO�̏ꍇ�́A����s�\�ƂȂ�ꍇ������̂ŁA�����Ń`�F�b�N����B�ŏI�I�Ɏg�p����̂́A��ԍŌ�̌����`�F�b�N
				if( kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.KESSON
						&& hba1cCheck == JKaisoukaHanteiItemCheck.KESSON )
				{
					Category1Kesson = true;
				}
			}

			if(kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.FALSE)
			{
				Category1 = false;
			}
		}

		// ����[����2�i�����j
		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,
				kettoHukuyaku) == JKaisoukaHanteiItemCheck.TRUE && Category1 == false)
		{
			Category1 = true;
		}else{
			// �����`�F�b�N
			if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,kettoHukuyaku) == JKaisoukaHanteiItemCheck.KESSON && Category1 == false)
			{
				Category1Kesson = true;
			}
		}

		// �J�e�S��2
		boolean Category2 = false;
		boolean Category2Kesson = false;
		double shushukuKetsuatsu = data.getShushukuKetsuatsu();
		double kakutyoKetsuatsu = data.getKakutyoKetsuatsu();
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_SYUSYUKUKI_KETSUATSU_1, gender,	shushukuKetsuatsu) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_KAKUCHOKI_KETSUATSU_1, gender,kakutyoKetsuatsu) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
		)
		{
			Category2 = true;
		}else{
			if(
					JKaisoukaHanteiItemCheck.CheckItem(CODE_SYUSYUKUKI_KETSUATSU_1, gender,	shushukuKetsuatsu) == JKaisoukaHanteiItemCheck.KESSON ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_KAKUCHOKI_KETSUATSU_1, gender, kakutyoKetsuatsu) == JKaisoukaHanteiItemCheck.KESSON ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.KESSON
			)
			{
				Category2Kesson = true;
			}
		}

		// �J�e�S��3
		boolean Category3 = false;
		boolean Category3Kesson = false;
		double chuseiShibou = data.getChuseiShibou();
		double cholesterol = data.getHDLCholesterol();
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_CYUSEI_SHIBOU_1, gender,chuseiShibou) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HDL_1, gender,cholesterol) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
		)
		{
			Category3 = true;
		}else{
			if(
					JKaisoukaHanteiItemCheck.CheckItem(CODE_CYUSEI_SHIBOU_1, gender,chuseiShibou) == JKaisoukaHanteiItemCheck.TRUE ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_HDL_1, gender,cholesterol) == JKaisoukaHanteiItemCheck.TRUE ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
			)
			{
				Category3Kesson = true;
			}
		}

		// �J�e�S��4
		boolean Category4 = false;
		boolean Category4Kesson = false;
		double kitsuen = data.getKitsuen();

		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.TRUE)
		{
			Category4 = true;
		}
		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.KESSON)
		{
			Category4Kesson = true;
		}

		// �X�e�b�v3

		// �R�[�h�̔���

		if(Step1 == 0)
		{
			Code = JYOUHOU_TEIKYOU;
		}

		// �X�e�b�v2�̊Y�����鐔���v�Z
		int Step2Count = 0;

		if(Category1 == true)
		{
			Step2Count++;
		}
		if(Category2 == true)
		{
			Step2Count++;
		}
		if(Category3 == true)
		{
			Step2Count++;
		}

		// ���ʂ̔���

		if(Step1 == 1)
		{
			if(Step2Count == 0)
			{
				Code = JYOUHOU_TEIKYOU;
			}
			if(Step2Count == 1)
			{
				if(Category4 == true)
				{
					Code = SEKKYOKUTEKI_SHIEN;
				}else{
					Code = DOUKIDUKE_SHIEN;
				}

				// �����̔���
				if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.KESSON)
				{
					Code = HANTEI_HUNOU;
					return Code;
				}
			}

			if(Step2Count >= 2)
			{
				Code = SEKKYOKUTEKI_SHIEN;
			}
		}

		if(Step1 == 2)
		{
			if(Step2Count == 0)
			{
				Code = JYOUHOU_TEIKYOU;
			}

			if(Step2Count == 1)
			{
				Code = DOUKIDUKE_SHIEN;
			}

			if(Step2Count == 2)
			{
				if(Category4 == true)
				{
					Code = SEKKYOKUTEKI_SHIEN;
				}else{
					Code = DOUKIDUKE_SHIEN;
				}

				// �����̔���
				if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.KESSON)
				{
					Code = HANTEI_HUNOU;
					return Code;
				}
			}

			if(Step2Count >= 3)
			{
				Code = SEKKYOKUTEKI_SHIEN;
			}
		}

		// �X�e�b�v4
		if(Code == SEKKYOKUTEKI_SHIEN || Code == DOUKIDUKE_SHIEN)
		{
			// edit ver2 s.inoue 2009/08/19 75�Ώ����ǉ�
			if((65 <= data.getAge() && data.getAge() <= 74) ||
					data.getTargetAge())
			{
				Code = DOUKIDUKE_SHIEN;
				return Code;
			}

			return Code;
		}

		// �����l�`�F�b�N1

		// 1�A�������b�ʐρA���͂Ɋւ�����̂ǂ�����܂��������݂��Ȃ��ꍇ�ɂ́u����s�\�v�Ƃ��Ċ���
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_NAIZOU_SHIBOU_MENSEKI, gender,naizouShibou) == JKaisoukaHanteiItemCheck.KESSON &&
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUI_ZISOKU, gender,hukui) == JKaisoukaHanteiItemCheck.KESSON
		)
		{
			Code = HANTEI_HUNOU;
			return Code;
		}

		double shintyou = data.getShintyou();
		double taijyu = data.getTaijyu();
		// 2�ASTEP1�Ńp�^�[��1�ɊY�����Ȃ��ꍇ�ŁABMI���������Ă���A�g���A�̏d�̂ǂ��炩�����݂��Ȃ��ꍇ
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_BMI, gender,bmi) == JKaisoukaHanteiItemCheck.KESSON && (shintyou == -1 || taijyu == -1)
		)
		{
			Code = HANTEI_HUNOU;
			return Code;
		}

		// 3�A����Ɏg���������A����ю���[�̕���֌W��3��A�i���ɂ����Č����l���Ȃ��ꍇ
		if(JKaisoukaHanteiItemCheck.IsKesson() == false)
		{
			return Code;
		}

		// 4�ASTEP2�̃J�e�S���[4�������l���ڂł��邽�߂ɓ��肪�ł��Ȃ��󋵂���������ꍇ
		// �J�e�S���[4���g���Ĕ�����s�������ŁA�����̃`�F�b�N���s���Ă���B

		// ����̌���
		boolean HukuyakuKesson = false;

		if(
			JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,	kettoHukuyaku) == JKaisoukaHanteiItemCheck.KESSON ||
			JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.KESSON ||
			JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.KESSON
		)
		{
			HukuyakuKesson = true;
		}

		// ���茋�ʂ��u���񋟃��x���v
		if(Code == JYOUHOU_TEIKYOU)
		{
			// 5�A�����l������Ɋւ�����̂����ł����āA����ȊO�̍��ڂɂ͌����l���Ȃ��ꍇ
			if(
					HukuyakuKesson == true &&
					(Step1Kesson == false && Category1Kesson == false && Category2Kesson == false &&
					Category3Kesson == false && Category4Kesson == false)
			)
			{
				return Code;
			}

			// 6�A����ȊO�̍��ڂɌ����l������ꍇ
			if(
					(Step1Kesson == true || Category1Kesson == true || Category2Kesson == true ||
					Category3Kesson == true || Category4Kesson == true)
			)
			{
				Code = HANTEI_HUNOU;
				return Code;
			}
		}

		// 7�A���茋�ʂ��u���񋟃��x���v�ȊO�ł����āA���A���򂪈�ł��������Ă���ꍇ
		if(Code != JYOUHOU_TEIKYOU && HukuyakuKesson == true)
		{
			Code = HANTEI_HUNOU;
			return Code;
		}

		// 9�A65�`74�΂ł����āA���@�Â��x�����x���ł���΁A�����l���ڂ̗L���ɂ�����炸����
		// edit ver2 s.inoue 2009/08/19 75�Ώ����܂�
		// if(65 <= data.getAge() && data.getAge() <= 74 && ( Code == DOUKIDUKE_SHIEN || Code == SEKKYOKUTEKI_SHIEN ) )
		// {
		if( ((65 <= data.getAge() && data.getAge() <= 74) || data.getTargetAge())
				 && ( Code == DOUKIDUKE_SHIEN || Code == SEKKYOKUTEKI_SHIEN ))
		{
			Code = DOUKIDUKE_SHIEN;
			return Code;
		}

		// 8�A�ϋɓI�x�����x���ł������ꍇ�A�����l���ڂ̗L���ɂ�����炸����
		if(Code == SEKKYOKUTEKI_SHIEN)
		{
			return Code;
		}

		// �����`�F�b�N2
		// �����l���ڂ�1�ȏ゠��A���ʂ��u��Y���v�܂��́u���@�Â��x�����x���v�ł���΃`�F�b�N
		if(JKaisoukaHanteiItemCheck.IsKesson() == true && (Code == JYOUHOU_TEIKYOU || Code == DOUKIDUKE_SHIEN))
		{
			if(Step1 == 1)
			{
				// �����l���ڂ̑�����J�e�S���[�̂ЂƂł�STEP2��NO�ł���ꍇ
				if(
						(Category1 == false && Category1Kesson == true) ||
						(Category2 == false && Category2Kesson == true) ||
						(Category3 == false && Category3Kesson == true)
				)
				{
					Code = HANTEI_HUNOU;
					return Code;
				}

				// ����ȊO�͕ύX�Ȃ�
				return Code;
			}

			if(Step1 == 2)
			{
				if(Step2Count == 0)
				{
					return Code;
				}

				if(Step2Count == 1)
				{
					// Step2��No�ƂȂ��Ă���2�J�e�S���̗����Ɍ����l������
					if(
							(Category1 == true && Category2Kesson == true && Category3Kesson == true) ||
							(Category2 == true && Category1Kesson == true && Category3Kesson == true) ||
							(Category3 == true && Category2Kesson == true && Category1Kesson == true)
					)
					{
						Code = HANTEI_HUNOU;
						return Code;
					}

					// Step2��No�ƂȂ��Ă���2�J�e�S���̂���1�J�e�S�������Ɍ����l������
					if(
							(Category1 == true && (Category2Kesson == true || Category3Kesson == true)) ||
							(Category2 == true && (Category1Kesson == true || Category3Kesson == true)) ||
							(Category3 == true && (Category2Kesson == true || Category1Kesson == true))
					)
					{
						if(Category4 == false)
						{
							return Code;
						}else{
							Code = HANTEI_HUNOU;
							return Code;
						}
					}

					return Code;
				}

				if(Step2Count == 2)
				{
					if(
							(Category1 == false && Category1Kesson == true) ||
							(Category2 == false && Category2Kesson == true) ||
							(Category3 == false && Category3Kesson == true)
					)
					{
						Code = HANTEI_HUNOU;
						return Code;
					}

					return Code;
				}
			}

		}

		return Code;
	}
}
