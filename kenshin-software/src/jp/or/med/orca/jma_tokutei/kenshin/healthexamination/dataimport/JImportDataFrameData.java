package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.ArrayList;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JImportDataFrameData {
	String importFormat = new String("");	//1:���J�ȏ����A2:�Ǝ�
	String kensaCenterNumber = new String("");
	String kensaJissidate = new String("");
	String filePath = new String("");
	boolean isValidateAsDataSet = false;

	// edit s.inoue 2010/02/10
	/* ����[ */
	protected static final String CODE_FUKUYAKU_1 ="9N701000000000011";		// ����1(����)
	// protected static final String CODE_FUKUYAKU_1 ="9N701167000000049";	// ����1(����)(��ܖ�)
	// protected static final String CODE_FUKUYAKU_1 ="9N701167100000049";	// ����1(����)(���{���R)
	protected static final String CODE_FUKUYAKU_2 ="9N706000000000011";		// ����2(����)
	// protected static final String CODE_FUKUYAKU_2 ="9N706167000000049";	// ����2(����)(��ܖ�)
	// protected static final String CODE_FUKUYAKU_2 ="9N706167100000049";	// ����2(����)(���{���R)
	protected static final String CODE_FUKUYAKU_3 ="9N711000000000011";		// ����3(����)
	// protected static final String CODE_FUKUYAKU_3 ="9N711167000000049";	// ����3(����)(��ܖ�)
	// protected static final String CODE_FUKUYAKU_3 ="9N711167100000049";	// ����3(����)(���{���R)
	protected static final String CODE_KIOUREKI_1 ="9N716000000000011";		// ������1(�]����)
	protected static final String CODE_KIOUREKI_2 ="9N721000000000011";		// ������2(�S����)
	protected static final String CODE_KIOUREKI_3 ="9N726000000000011";		// ������3(�t�s�S��l�H����)
	protected static final String CODE_HINKETU	 ="9N731000000000011";		// �n��
	protected static final String CODE_KITUEN 	 ="9N736000000000011";		// �i��
	protected static final String CODE_INSHU	 ="9N786000000000011";		// ����
	// protected static final String CODE_INSHU_RYO ="9N791000000000011";	// �����
	protected static final String CODE_HOKENSIDO ="9N806000000000011";		// �ی��w���̊�]

	/* �g�̌v�� */
	protected static final String CODE_SINCHO ="9N001000000000001";			// �g��
	protected static final String CODE_TAIJYU ="9N006000000000001";			// �̏d
	protected static final String CODE_BMI ="9N011000000000001";			// BMI���v�v�Z
	// protected static final String CODE_NAIZO ="9N021000000000001";		// �������b�ʐ�
	protected static final String CODE_FUKUI_JISOKU ="9N016160100000001";	// ����(�����j
	protected static final String CODE_FUKUI_HANTEI ="9N016160200000001";	// ����(���Ȕ���j
	protected static final String CODE_HIGHT_SINKOKU ="9N016160300000001";	// ����(���Ȑ\���j
	// protected static final String CODE_HIMANDO ="9N026000000000002";		// �얞�x
	// protected static final String CODE_GYOMUREKI ="9N051000000000049";	// �Ɩ���

	/* �f�@ */
	protected static final String CODE_KIOUREKI ="9N056000000000011";		// ������
	protected static final String CODE_KIOUREKI_GUTAI ="9N056160400000049";	// ��̓I�Ȋ������i�R���ڂ������j
	protected static final String CODE_JIKAKU ="9N061000000000011";			// ���o�Ǐ�
	protected static final String CODE_JIKAKU_SYOKEN ="9N061160800000049";	// ���o�Ǐ󏊌��i�R���ڂ������j
	protected static final String CODE_TAKAKU ="9N066000000000011";			// ���o�Ǐ�
	protected static final String CODE_TAKAKU_SYOKEN ="9N066160800000049";	// ���o�����i�R���ڂ������j

	// protected static final String CODE_HIGHT ="9N071000000000049";		// ���̑�(�Ƒ���)
	// protected static final String CODE_HIGHT ="9N076000000000049";		// ���f(���o���܂�)
	// protected static final String CODE_HIGHT ="9N081000000000049";		// �Œ��f
	// protected static final String CODE_HIGHT ="9N086000000000049";		// �G�f(�֐߉���܂�)
	// protected static final String CODE_HIGHT ="9N091000000000001";		// �������t�����e�X�g

	/* ���� */
	protected static final String CODE_SYUSYUKU_SONOTA ="9A755000000000001";	// ���k������(���̑�)
	protected static final String CODE_SYUSYUKU_NIKAI ="9A752000000000001";		// ���k������(2���)
	protected static final String CODE_SYUSYUKU_IKAI ="9A751000000000001";		// ���k������(1���)
	protected static final String CODE_KAKUCYO_SONOTA ="9A765000000000001";		// �g��������(���̑�)
	protected static final String CODE_KAKUCYO_NIKAI ="9A762000000000001";		// �g��������(2���)
	protected static final String CODE_KAKUCYO_IKAI ="9A761000000000001";		// �g��������(1���)

	/* �A */
	protected static final String CODE_NYOTOU ="1A020000000191111";		// �A������Ԑ擪���g�p
	// protected static final String CODE_HIGHT ="1A020000000190111";	// �A��
	protected static final String CODE_NYOTANPAKU ="1A010000000191111";	// �A�`��
	// protected static final String CODE_HIGHT ="1A010000000190111";	// �A�`��

	/* ���� */
	protected static final String CODE_KUFUKUJIKETOU ="3D010000001926101";	// �󕠎���������Ԑ擪���g�p
	// protected static final String CODE_HIGHT ="3D010000002227101";		// �󕠎�����
	// protected static final String CODE_HIGHT ="3D010000001927201";		// �󕠎�����
	// protected static final String CODE_HIGHT ="3D010000001999901";		// �󕠎�����
	protected static final String CODE_SAIKETU ="9N141000000000011";		// �̌�����(�H��)
	protected static final String CODE_HBA1C ="3D045000001906202";			// HbA1c����Ԑ擪���g�p
	// protected static final String CODE_HIGHT ="3D045000001920402";	// HbA1c
	// protected static final String CODE_HIGHT ="3D045000001927102";	// HbA1c
	// protected static final String CODE_HIGHT ="3D045000001999902";	// HbA1c

	/* �������� */
	protected static final String CODE_CYUSEISIBOU ="3F015000002327101";	// �������b�i�g���O���Z���h�j����Ԑ擪���g�p
	// protected static final String CODE_HIGHT ="3F015000002327201";		// �������b�i�g���O���Z���h�j
	// protected static final String CODE_HIGHT ="3F015000002399901";		// �������b�i�g���O���Z���h�j
	protected static final String CODE_HDL ="3F070000002327101";			// HDL�R���X�e���[������Ԑ擪���g�p
	// protected static final String CODE_HIGHT ="3F070000002327201";		// HDL�R���X�e���[��
	// protected static final String CODE_HIGHT ="3F070000002399901";		// HDL�R���X�e���[��
	protected static final String CODE_LDL ="3F077000002327101";			// LDL�R���X�e���[������Ԑ擪���g�p
	// protected static final String CODE_HIGHT ="3F077000002327201";		// LDL�R���X�e���[��
	// protected static final String CODE_HIGHT ="3F077000002399901";		// LDL�R���X�e���[��

	/* �̋@�\ */
	protected static final String CODE_GOT ="3B035000002327201";		// GOT(AST)
	// protected static final String CODE_HIGHT ="3B035000002399901";	// GOT(AST)
	protected static final String CODE_GPT ="3B045000002327201";		// GPT(ALT)
	// protected static final String CODE_HIGHT ="3B045000002399901";	// GPT(ALT)
	protected static final String CODE_GANMA ="3B090000002327101";		// ��-GT(��-GTP)
	// protected static final String CODE_HIGHT ="3B090000002399901";	// ��-GT(��-GTP)

	/* ��t���f */
	protected static final String CODE_METABO ="9N501000000000011";			// ���^�{���b�N�V���h���[������
	// protected static final String CODE_HIGHT ="9N506000000000011";		// �ی��w�����x��
	protected static final String CODE_ISINO_HANDAN ="9N511000000000049";	// ��t�̐f�f(����)
	protected static final String CODE_ISINO_SIMEI ="9N516000000000049";	// ���N�f�f�����{������t�̎���
	// protected static final String CODE_HIGHT ="9N521000000000049";	// ��t�̈ӌ�
	// protected static final String CODE_HIGHT ="9N526000000000049";	// �ӌ����q�ׂ���t�̎���
	// protected static final String CODE_HIGHT ="9N531000000000049";	// ���Ȉ�t�ɂ�錒�N�f�f
	// protected static final String CODE_HIGHT ="9N536000000000049";	// ���N�f�f�����{�������Ȉ�t�̎���
	// protected static final String CODE_HIGHT ="9N541000000000049";	// ���Ȉ�t�̈ӌ�
	// protected static final String CODE_HIGHT ="9N546000000000049";	// �ӌ����q�ׂ����Ȉ�t�̎���
	// protected static final String CODE_HIGHT ="9N551000000000049";	// ���l

	/* �n�� */
	protected static final String CODE_HINKETU_JISIRIYU ="2A020161001930149";	// �n���������{���R
	protected static final String CODE_SEKESYU ="2A020000001930101";			// �Ԍ�����
	protected static final String CODE_HEMATORIC ="2A040000001930102";			// �w�}�g�N���b�g�l
	protected static final String CODE_KESYOKUSORYOU ="2A030000001930101";		// ���F�f��(�w���O���r���l)

	/* �S�d�} */
	protected static final String CODE_SINDENZU_JISIRIYU ="9A110161000000049";	// �S�d�}���{���R
	protected static final String CODE_SINDENZU ="9A110160700000011";			// �S�d�}(�����̗L��)
	protected static final String CODE_SINDENZU_SYOKEN ="9A110160800000049";	// �S�d�}����

	/* ��� */
	protected static final String CODE_GANTEI_KENSA ="9E100166000000011";	// ��ꌟ��(�L�[�X���O�i�[����)
	protected static final String CODE_GANTEI_H ="9E100166100000011";		// ��ꌟ��(�V�F�C�G����:H)
	protected static final String CODE_GANTEI_S ="9E100166200000011";		// ��ꌟ��(�V�F�C�G����:S)
	protected static final String CODE_GANTEI_SCOTT ="9E100166300000011";	// ��ꌟ��(SCOTT����)
	protected static final String CODE_GANTEI_SONOTA ="9E100160900000049";	// ��ꌟ��(���̑��̏���)
	protected static final String CODE_GANTEI_JISIRIYU ="9E100161000000049";// ��ꌟ�����{���R
	// ��Davos���ނ��������߁A�u���̑��̏����ɋL�ځv

	/* ����茒�f */
	protected static final String CODE_NYOSENKETU ="1A100000000191111";	// �A����
	// protected static final String CODE_HIGHT ="1A100000000190111";	// �A����
	protected static final String CODE_KESEI_C ="3C015000002327101";	// �����N���A�`�j���������_�ȉ��ɂ�蔻�f����
	// protected static final String CODE_HIGHT ="3C015000002399901";	// �����N���A�`�j��
	protected static final String CODE_KESEI_N ="3C020000002327101";	// �����A�_
	// protected static final String CODE_HIGHT ="3C020000002399901";	// �����A�_
	protected static final String CODE_KORESTEROL ="3F050000002327101";	// ���R���X�e���[��
	// protected static final String CODE_HIGHT ="3F050000002327201";	// ���R���X�e���[��
	// protected static final String CODE_HIGHT ="3F050000002399901";	// ���R���X�e���[��
	protected static final String CODE_BIRIRUBIN ="3J010000002327101";	// ���r�����r��
	// protected static final String CODE_HIGHT ="3J010000002399901";	// ���r�����r��
	protected static final String CODE_ALP ="3B070000002327101";		// ALP
	// protected static final String CODE_HIGHT ="3B070000002399901";	// ALP
	protected static final String CODE_SOUTANPAKU ="3A010000002327101";	// ���`��
	// protected static final String CODE_HIGHT ="3A010000002399901";	// ���`��
	protected static final String CODE_FERITIRIN ="5C095000002302301";	// �����t�F���`��
	// protected static final String CODE_HIGHT ="5C095000002399901";	// �����t�F���`��
	protected static final String CODE_ARUBUMIN ="3A015000002327101";	// �A���u�~��
	// protected static final String CODE_HIGHT ="3A015000002399901";	// �A���u�~��

	// add s.inoue 2010/02/10
	public static final String[] koumokuCD_Situmonhyo = {
		"9N701000000000011","9N706000000000011","9N711000000000011",
		"9N716000000000011","9N721000000000011","9N726000000000011",
		"9N731000000000011","9N736000000000011","9N786000000000011",
		"9N806000000000011"
	};
	// add s.inoue 2010/02/10
	public static final String[] koumokuCD_Situmonhyo_Order = {
		"7","8","9","10","11","12","13","14","15","16"
	};

	/**
	 * @return the importFormat
	 */
	public String getImportFormat() {
		return importFormat;
	}
	/**
	 * @return the kensaCenterNumber
	 */
	public String getKensaCenterNumber() {
		return kensaCenterNumber;
	}
	/**
	 * @return the kensaJissidate
	 */
	public String getKensaJissidate() {
		return kensaJissidate;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @param importFormat the importFormat to set
	 */
	public boolean setImportFormat(String importFormat) {
		this.isValidateAsDataSet = false;
		this.importFormat = JValidate.validateImportFormat(importFormat);

		if( this.importFormat == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3310", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaCenterNumber the kensaCenterNumber to set
	 */
	public boolean setKensaCenterNumber(String kensaCenterNumber) {
		this.isValidateAsDataSet = false;
		this.kensaCenterNumber = JValidate.validateKensaCenterCode(kensaCenterNumber);

		if( this.kensaCenterNumber == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3311", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaJissidate the kensaJissidate to set
	 */
	public boolean setKensaJissidate(String kensaJissidate) {
		this.isValidateAsDataSet = false;
		this.kensaJissidate = JValidate.validateDate(kensaJissidate);

		if( this.kensaJissidate == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3312", null);
			return false;
		}

		return true;
	}

	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = true;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public boolean setFilePath(String filePath) {
		this.isValidateAsDataSet = false;
		this.filePath = JValidate.validateFilePath(filePath);

		if( this.filePath == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3313", null);
			return false;
		}

		return true;
	}
}