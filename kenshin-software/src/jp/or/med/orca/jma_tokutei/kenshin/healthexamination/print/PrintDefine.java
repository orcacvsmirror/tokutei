package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jp.or.med.orca.jma_tokutei.common.app.JConstantString;

public class PrintDefine {

	public static final String PAGE_ITEM_1 = "1/1";
	public static final String PAGE_ITEM_LEFT = "[";
	public static final String PAGE_ITEM_RIGHT = "�y�[�W��] �j";
	public static final String GENERAL_COMMENT = "��t�̔��f�F";
	public static final String VALUES_NASHI = "�Ȃ�";
	public static final String VALUES_ARI = "����";
	public static final String ANSWER_YES = "�͂�";
	public static final String ANSWER_NO = "������";
	public static final String ANEMIA = "�n���ɂ��Ċ���������";
	public static final String LIVER_FAILURE = "�t�s�S�E�l�H���͂ɂ��Ċ���������";
	public static final String CARDIOVASCULAR_SYSTEM = "�S���ǂɂ��Ċ���������";
	public static final String CEREBRAL_BLOOD_VESSEL = "�]���ǂɂ��Ċ���������";
	// add h.yoshitama 2009/11/24
	public static final String NO_ANEMIA = "�n���ɂ��Ċ������Ȃ�";
	public static final String NO_LIVER_FAILURE = "�t�s�S�E�l�H���͂ɂ��Ċ������Ȃ�";
	public static final String NO_CARDIOVASCULAR_SYSTEM = "�S���ǂɂ��Ċ������Ȃ�";
	public static final String NO_CEREBRAL_BLOOD_VESSEL = "�]���ǂɂ��Ċ������Ȃ�";
	public static final String NO_TOKKIJIKO = "���L���ׂ����ƂȂ�";
	public static final String NO_HUKUYAKU = "����Ȃ�";

	public static final String WORK_PDF_INKEKKA2PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inkekka2page.pdf";
	public static final String WORK_PDF_INKEKKA3PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inkekka3page.pdf";
	public static final String WORK_PDF_INKEKKA4PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inkekka4page.pdf";
	public static final String WORK_PDF_TMP_DUMMY_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"dummy.pdf";
	// edit s.inoue 2010/05/10
	// public static final String WORK_PDF_DUMMY2_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inKekkaTemplate_2.pdf";
	public static final String WORK_PDF_DUMMY2_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inKekkaTemplate_2.pdf";

	public static final String WORK_PDF_TMP_INKEKKA2PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka2page.pdf";
	public static final String WORK_PDF_TMP_INKEKKA3PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka3page.pdf";
	public static final String WORK_PDF_TMP_INKEKKA4PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka4page.pdf";
	// edit s.inoue 2009/09/28
	public static final String WORK_GEKEI_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"gekei.pdf";
	public static final String WORK_SHUKEI_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"shukeihyo.pdf";
	public static final String WORK_RYOSYU_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"ryosyusyo.pdf";

	public static final String OUT_GEKEI_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_getkei.pdf";
	public static final String OUT_SHUKEI_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_shukeihyo.pdf";
	public static final String OUT_RYOSYUSYO_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_ryosyusyo.pdf";

	public static final String CODE_GANTEI_SONOTA_SYOKEN = "9E100160900000049";
	public static final String CODE_SINDENZU_SYOKEN_UMU = "9A110160700000011";
	public static final String CODE_SINDENZU_SYOKEN = "9A110160800000049";
	public static final String CODE_NYOTANPAKU_MOKUSI = "1A010000000190111";
	public static final String CODE_NYOTANPAKU_KIKAI = "1A010000000191111";
	public static final String CODE_WEIGHT = "9N006000000000001";
	public static final String CODE_HIGHT = "9N001000000000001";
	public static final String CODE_HEMATCLIT = "2A040000001930102";
	public static final String CODE_KESIKISOSU = "2A030000001930101";
	public static final String CODE_SEKEKYUSU = "2A020000001930101";

	// edit s.inoue 2012/09/04
	public static final String CODE_HBA1C_SONOTA_JDS = "3D045000001999902";
	public static final String CODE_HBA1C_COUSOHO_JDS = "3D045000001927102";
	public static final String CODE_HBA1C_HPLC_JDS = "3D045000001920402";
	public static final String CODE_HBA1C_RATEX_JDS = "3D045000001906202";
	public static String CODE_HBA1C_SONOTA = CODE_HBA1C_SONOTA_JDS;
	public static String CODE_HBA1C_COUSOHO = CODE_HBA1C_COUSOHO_JDS;
	public static String CODE_HBA1C_HPLC = CODE_HBA1C_HPLC_JDS;
	public static String CODE_HBA1C_RATEX = CODE_HBA1C_RATEX_JDS;
	public static final String CODE_HBA1C_SONOTA_NGSP = "3D046000001999902";
	public static final String CODE_HBA1C_COUSOHO_NGSP = "3D046000001927102";
	public static final String CODE_HBA1C_HPLC_NGSP = "3D046000001920402";
	public static final String CODE_HBA1C_RATEX_NGSP = "3D046000001906202";

	public static final String CODE_KUHUKUZI_KETO_SONOTA = "3D010000001999901";
	public static final String CODE_KUHUKUZI_KETO_SIGAI = "3D010000001927201";
	public static final String CODE_KUHUKUZI_KETO_KASI = "3D010000002227101";
	public static final String CODE_KUHUKUZI_KETO_DENNI = "3D010000001926101";
	public static final String CODE_GAMMA_GTP_SONOTA = "3B090000002399901";
	public static final String CODE_GAMMA_GTP_KASI = "3B090000002327101";
	public static final String CODE_GPT_SONOTA = "3B045000002399901";
	public static final String CODE_GPT_SIGAI = "3B045000002327201";
	public static final String CODE_GOT_SONOTA = "3B035000002399901";
	public static final String CODE_GOT_SIGAI = "3B035000002327201";
	public static final String CODE_LDL_SONOTA = "3F077000002399901";
	public static final String CODE_LDL_SIGAI = "3F077000002327201";
	public static final String CODE_LDL_KASI = "3F077000002327101";
	public static final String CODE_HDL_SONOTA = "3F070000002399901";
	public static final String CODE_HDL_SIGAI = "3F070000002327201";
	public static final String CODE_HDL_KASI = "3F070000002327101";
	public static final String CODE_CHUSEISIBOU_SONOTA = "3F015000002399901";
	public static final String CODE_CHUSEISIBOU_SIGAI = "3F015000002327201";
	public static final String CODE_CHUSEISIBOU_KASI = "3F015000002327101";
	public static final String CODE_KAKUCHOKI_KETUATU_SONOTA = "9A765000000000001";
	public static final String CODE_KAKUCHOKI_KETUATU_2 = "9A762000000000001";
	public static final String CODE_KAKUCHOKI_KETUATU_1 = "9A761000000000001";
	public static final String CODE_SYUSYUKUKI_KETUATU_SONOTA = "9A755000000000001";
	public static final String CODE_SYUSYUKUKI_KETUATU_2 = "9A752000000000001";
	public static final String CODE_SYUSYUKUKI_KETUATU_1 = "9A751000000000001";
	public static final String CODE_BMI = "9N011000000000001";
	public static final String CODE_INSYU = "9N791000000000011";
	public static final String CODE_KITUENREKI = "9N736000000000011";
	public static final String CODE_TAKAKUSYOJOU = "9N066160800000049";
	public static final String CODE_ZIKAKUSYOJOU = "9N061160800000049";
	public static final String CODE_GUTAITEKINA_KIOUREKI = "9N056160400000049";
	public static final String CODE_KIOREKI = "9N056000000000011";
	public static final String CODE_HINKETU = "9N731000000000011";
	public static final String CODE_KIOUREKI3_ZINHUZEN_ZINKOTOSEKI = "9N726000000000011";
	public static final String CODE_KIOUREKI2_SINKEKAN = "9N721000000000011";
	public static final String CODE_KIOUREKI1_NOKEKAN = "9N716000000000011";
	// add h.yoshitama 2009/11/24
	public static final String CODE_TAKAKU_UMU = "9N066000000000011";
	public static final String CODE_ZIKAKU_UMU = "9N061000000000011";

	/* �e�[�u������ */
	public static final String COLUMN_NAME_KOUMOKU_NAME = "KOUMOKU_NAME";
	public static final String COLUMN_NAME_KENSA_HOUHOU = "KENSA_HOUHOU";
	public static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
	public static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
	public static final String COLUMN_NAME_DS_JYOUGEN = "DS_JYOUGEN";
	public static final String COLUMN_NAME_DS_KAGEN = "DS_KAGEN";
	public static final String COLUMN_NAME_JS_JYOUGEN = "JS_JYOUGEN";
	public static final String COLUMN_NAME_JS_KAGEN = "JS_KAGEN";
	public static final String COLUMN_NAME_TANI = "TANI";
	public static final String COLUMN_NAME_KENSA_NENGAPI = "KENSA_NENGAPI";
	public static final String CODE_KOUMOKU = "KOUMOKU_CD";
	public static final String COLUMN_MAX_BYTE_LENGTH ="MAX_BYTE_LENGTH";
	public static final String STR_H_L = "H_L";
	public static final String DATA_TYPE = "DATA_TYPE";

	// add s.inoue 2009/09/22
	// �W�v�e�[�u������(T_SYUKEI)
	public static final String ROW_ID = "ROW_ID";
	public static final String HKNJANUM = "HKNJANUM";
	public static final String KENSA_NENGAPI = "KENSA_NENGAPI";
	public static final String KENSA_JISI_KUBUN = "KENSA_JISI_KUBUN";
	public static final String KENSA_JISI_SOUSU = "KENSA_JISI_SOUSU";
	public static final String KENSA_TANKA_SOUKEI = "KENSA_TANKA_SOUKEI";
	public static final String KENSA_MADO_SOUKEI = "KENSA_MADO_SOUKEI";
	public static final String KENSA_SEIKYU_SOUKEI = "KENSA_SEIKYU_SOUKEI";
	public static final String KENSA_SONOTA_SOUKEI = "KENSA_SONOTA_SOUKEI";
	public static final String UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

	public static final String CODE_KENKOUSINDAN_WO_JISISITA_ISI_NO_SHIMEI = "9N516000000000049";
	public static final String CODE_ISHINO_HANDAN = "9N511000000000049";
	public static final String CODE_KEITH_WAGNER = "9E100166000000011";//��ꌟ���i�L�[�X���O�i�[���ށj
	public static final String CODE_SHEIE_H = "9E100166100000011";//��ꌟ���i�V�F�C�G���ށF�g�j
	public static final String CODE_SHEIE_S = "9E100166200000011";//��ꌟ���i�V�F�C�G���ށF�r�j
	public static final String CODE_SCOTT = "9E100166300000011";//��ꌟ���iSCOTT���ށj
	public static final String CODE_COLLECTING_BLOOD_TIME = "9N141000000000011";//�̌����ԁi�H��j
	public static final String COLESTEROLL_DOWN = "9N711000000000011";
	public static final String INSURIN = "9N706000000000011";
	public static final String FUKUTO_BLODD = "9N701000000000011";
	public static final String TOU_MOKUSHI = "1A020000000190111";
	public static final String TOU_KIKAI = "1A020000000191111";
	public static final String VALUE_KEITH_WAGNER = "�L�[�X���O�i�[���ށF";//��ꌟ���i�L�[�X���O�i�[���ށj
	public static final String VALUE_SHEIE_H = "�V�F�C�G���ނg�F";//��ꌟ���i�V�F�C�G���ށF�g�j
	public static final String VALUE_SHEIE_S = "�V�F�C�G���ނr�F";//��ꌟ���i�V�F�C�G���ށF�r�j
	public static final String VALUE_SCOTT = "SCOTT���ށF";//��ꌟ���iSCOTT���ށj
	public static final String  asuta = "*";
	public static final String  ONE = "1"; //1
	public static final String  TWO = "2"; //2
	public static final String NAME_SHISHITU = "����";
	public static final String NAME_KETTOU = "����";
	public static final String NAME_KETUATU = "����";

	/*      ��������ʔ���                                                  **/
	public static final String KETUATU_SEIJYO = "�����͐���ł��B";
	public static final String KETUATU_IJYO = "�����Ɉُ킪�F�߂��܂��B";
	public static final String KETTYU_SEIJYO = "�������������͐���ł��B";
	public static final String KETTYU_IJYO = "�������������Ɉُ킪�F�߂��܂��B";
	public static final String KANKINOU_SEIJYO = "�̋@�\�����͐���ł��B";
	public static final String KANKINOU_IJYO = "�̋@�\�����Ɉُ킪�F�߂��܂��B";
	public static final String KETTOU_SEIJYO = "���������͐���ł��B";
	public static final String KETTOU_IJYO = "���������Ɉُ킪�F�߂��܂��B";
	public static final String NYOU_SEIJYO = "�A�����͐���ł��B";
	public static final String NYOU_IJYO_YOSEI = "�A�����Ɉُ킪�F�߂��܂��B�A�����z���ł��B";
	public static final String NYOU_IJYO_TANPAKU = "�A�����Ɉُ킪�F�߂��܂��B�A�`�����z���ł��B";
	public static final String NYOU_IJYO = "�A�����Ɉُ킪�F�߂��܂��B";
	public static final String SYOKEN_NASHI = "�����Ȃ�";
	public static final String IJYO_NASHI = "�ُ�Ȃ�";

	/*      ����茟�f                                                  **/
	public static final String NYOTINSA_UMU = "1A105160700166211";	//�A���ԁi�����̗L���j
	public static final String NYOTINSA_VAL = "1A105160800166249";	//�A���ԁi�����j
	public static final String SHINDENZU_UMU = "9A110160700000011";	//�S�d�}�i�����̗L���j
	public static final String SHINDENZU_VAL = "9A110160800000049";	//�S�d�}�i�����j
	public static final String KYUBU_X_TYUKU_UMU = "9N206160700000011";	//�����G�b�N�X�������i��ʁF���ڎB�e�j�i�����̗L���j
	public static final String KYUBU_X_TYUKU_VAL = "9N206160800000049";	//�����G�b�N�X�������i��ʁF���ڎB�e�j�i�����j
	public static final String KYUBU_X_KAN_UMU = "9N221160700000011";	//�����G�b�N�X�������i��ʁF�ԐڎB�e�j�i�����̗L���j
	public static final String KYUBU_X_KAN_VAL = "9N221160800000049";	//�����G�b�N�X�������i��ʁF�ԐڎB�e�j�i�����j
	public static final String KAKUTAN_UMU = "6A010160706170411";	//�\ႌ��� �i�h�������@��ʍ׋ہj�i�����̗L���j
	public static final String KAKUTAN_VAL = "6A010160806170449";	//�\ႌ��� �i�h�������@��ʍ׋ہj�i�����j
	public static final String KYUBU_CT_UMU = "9N251160700000011";	//�����b�s�����i�����̗L���j
	public static final String KYUBU_CT_VAL = "9N251160800000049";	//�����b�s�����i�����j
	public static final String JYOUBU_X_TYUKU_UMU = "9N256160700000011";	//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�����̗L���j
	public static final String JYOUBU__X_TYUKU_VAL = "9N256160800000049";	//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�����j
	public static final String JYOUBU_SYOUKAKAN_X_UMU = "9N261160700000011";	//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�����̗L���j
	public static final String JYOUBU_SYOUKAKAN_X_VAL = "9N261160800000049";	//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�����j
	public static final String JYOUBU_KANNAISIKYOU_UMU = "9N266160700000011";	//�㕔�����Ǔ����������i�����̗L���j
	public static final String JYOUBU_KANNAISIKYOU_VAL = "9N266160800000049";	//�㕔�����Ǔ����������i�����j
	public static final String FUKUBU_ONPA_UMU = "9F130160700000011";	//���������g�i�����̗L���j
	public static final String FUKUBU_ONPA_VAL = "9F130160800000049";	//���������g�i�����j
	public static final String FIJINKA_UMU = "9N271160700000011";	//�w�l�Ȑf�@�i�����̗L���j
	public static final String FIJINKA_VAL = "9N271160800000049";	//�w�l�Ȑf�@�i�����j
	public static final String NYUBOU_UMU = "9N276160700000011";	//���[���G�f�i�����̗L���j
	public static final String NYUBOU_VAL = "9N276160800000049";	//���[���G�f�i�����j
	public static final String NYUBOU_GAZOU_UMU = "9N281160700000011";	//���[�摜�f�f�i�}�����O���t�B�[�j�i�����̗L���j
	public static final String NYUBOU_GAZOU_VAL = "9N281160800000049";	//���[�摜�f�f�i�}�����O���t�B�[�j�i�����j
	public static final String NYUBOU_ONPA_UMU = "9F140160700000011";	//���[�����g�����i�����̗L���j
	public static final String NYUBOU_ONPA_VAL = "9F140160800000049";	//���[�����g�����i�����j
	public static final String SIKYU_KEIBU_UMU = "9N291160700000011";	//�q�{�z�����f�i�����̗L���j
	public static final String SIKYU_KEIBU_VAL = "9N291160800000049";	//�q�{�z�����f�i�����j
	public static final String SIKYU_NAISHIN_UMU = "9N296160700000011";	//�q�{���f�i�����̗L���j
	public static final String SIKYU_NAISHIN_VAL = "9N296160800000049";	//�q�{���f(�����j
	public static final String TYOKU_KOUMON2_UMU = "9Z771160700000011";	//�������@�\�i2���ڈȏ�j�i�����̗L���j
	public static final String TYOKU_KOUMON2_VAL = "9Z771160800000049";	//�������@�\�i2���ڈȏ�j�i�����j
	public static final String TYOKU_KOUMON1_UMU = "9Z770160700000011";	//�������@�\�i1���ځj�i�����̗L���j
	public static final String TYOKU_KOUMON1_VAL = "9Z770160800000049";	//�������@�\�i1���ځj�i�����j
	//add h.yoshitama 2009.09.15
	public static final String GYOMUREKI_VAL = "9N051000000000049";	//�Ɩ���
	public static final String SONOTA_KAZOKUREKI_VAL = "9N071000000000049";	//���̑�(�Ƒ���)
	public static final String SHISHIN_KOUKU_VAL = "9N076000000000049";	//���f(���o���܂�)
	public static final String DATYOSHIN_VAL = "9N081000000000049";	//�Œ��f
	public static final String SHOKUSHIN_KANSETSUKADO_VAL = "9N086000000000049";	//�G�f(�֐߉���܂�)
	public static final String TYORYOKU_SONOTA_VAL = "9D100160900000049";	//���́i���̑��̏����j
	public static final String CODE_CGATAKANEN_VAL = "9N401000000000011";	//C�^�̉��E�B���X���f�̔���
	public static final String SONOTA_TOKUSYUKENSHIN_VAL = "9N406000000000049";	//���̑��̖@����ꌒ�N�f�f
	public static final String SONOTA_HOUTEIKENSA_VAL = "9N411000000000049";	//���̑��̖@�쌟��
	public static final String SONOTA_KENSA_VAL = "9N416000000000049";	//���̑��̌���
	public static final String ISHI_IKEN_VAL = "9N521000000000049";	//��t�̈ӌ�
	public static final String ISHI_NAME_IKEN_VAL = "9N526000000000049";	//�ӌ����q�ׂ���t�̎���
	public static final String KENSHIN_SHIKAI_VAL = "9N531000000000049";	//���Ȉ�t�ɂ�錒�N�f�f
	public static final String SHIKAI_NAME_KENSHIN_VAL = "9N536000000000049";	//���N�f�f�����{�������Ȉ�t�̎���
	public static final String SHIKAI_IKEN_VAL = "9N541000000000049";	//���Ȉ�t�̈ӌ�
	public static final String SHIKAI_NAME_IKEN_VAL = "9N546000000000049";	//�ӌ����q�ׂ����Ȉ�t�̎���
	public static final String BIKOU_VAL = "9N551000000000049";	//���l
	public static final String ISHI_NAME_SHINDAN_VAL = "9N576000000000049";	//�f�f��������t�̎���
	public static final String JIYUUKISAI_HAIGAN_VAL = "9N581161400000049";	//��t�̐f�f(�x���񌟐f)(���R�L��)
	public static final String ISHI_NAME_HAIGAN_VAL = "9N586000000000049";	//�f�f��������t�̎���(�x���񌟐f)
	public static final String JIYUUKISAI_IGAN_VAL = "9N591161400000049";	//��t�̐f�f(�݂��񌟐f)(���R�L��)
	public static final String ISHI_NAME_IGAN_VAL = "9N596000000000049";	//�f�f��������t�̎���(�݂��񌟐f)
	public static final String JIYUUKISAI_NYUGAN_VAL = "9N601161400000049";	//��t�̐f�f(�����񌟐f)(���R�L��)
	public static final String ISHI_NAME_NYUGAN_VAL = "9N606000000000049";	//�f�f��������t�̎���(�����񌟐f)
	public static final String JIYUUKISAI_SHIKYUGAN_VAL = "9N611161400000049";	//��t�̐f�f(�q�{���񌟐f)(���R�L��)
	public static final String ISHI_NAME_SHIKYUGAN_VAL = "9N616000000000049";	//�f�f��������t�̎���(�q�{���񌟐f)
	public static final String JIYUUKISAI_DAITYOGAN_VAL = "9N621161400000049";	//��t�̐f�f(�咰���񌟐f)(���R�L��)
	public static final String ISHI_NAME_DAITYOGAN_VAL = "9N626000000000049";	//�f�f��������t�̎���(�咰���񌟐f)
	public static final String JIYUUKISAI_ZENRITSUSENGAN_VAL = "9N631161400000049";	//��t�̐f�f(�O���B���񌟐f)(���R�L��)
	public static final String ISHI_NAME_ZENRITSUSENGAN_VAL = "9N636000000000049";	//�f�f��������t�̎���(�O���B���񌟐f)
	public static final String JIYUUKISAI_SONOTA_VAL = "9N641000000000049";	//��t�̐f�f(���̑�)
	public static final String ISHI_NAME_SONOTA_VAL = "9N646000000000049";	//�f�f��������t�̎���(���̑�)

	public static final ArrayList<String> syokenUmu = new ArrayList<String>();
	public static final ArrayList<String> syoken = new ArrayList<String>();

	private static final void initializeSyokenUmu(){

		syokenUmu.add(NYOTINSA_UMU);   //1A105160700166211";	//�A���ԁi�����̗L���j
//		syokenUmu.add(SHINDENZU_UMU);   //9A110160700000011";	//�S�d�}�i�����̗L���j
		syokenUmu.add(KYUBU_X_TYUKU_UMU);   //9N206160700000011";	//�����G�b�N�X�������i��ʁF���ڎB�e�j�i�����̗L���j
		syokenUmu.add(KYUBU_X_KAN_UMU);   //9N221160700000011";	//�����G�b�N�X�������i��ʁF�ԐڎB�e�j�i�����̗L���j
		syokenUmu.add(KAKUTAN_UMU);   //6A010160706170411";	//�\ႌ��� �i�h�������@��ʍ׋ہj�i�����̗L���j
		syokenUmu.add(KYUBU_CT_UMU);   //9N251160700000011";	//�����b�s�����i�����̗L���j
		syokenUmu.add(JYOUBU_X_TYUKU_UMU);   //9N256160700000011";	//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�����̗L���j
		syokenUmu.add(JYOUBU_SYOUKAKAN_X_UMU);   //9N261160700000011";	//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�����̗L���j
		syokenUmu.add(JYOUBU_KANNAISIKYOU_UMU);   //9N266160700000011";	//�㕔�����Ǔ����������i�����̗L���j
		syokenUmu.add(FUKUBU_ONPA_UMU);   //9F130160700000011";	//���������g�i�����̗L���j
		syokenUmu.add(FIJINKA_UMU);   //9N271160700000011";	//�w�l�Ȑf�@�i�����̗L���j
		syokenUmu.add(NYUBOU_UMU);   //9N276160700000011";	//���[���G�f�i�����̗L���j
		syokenUmu.add(NYUBOU_GAZOU_UMU);   //9N281160700000011";	//���[�摜�f�f�i�}�����O���t�B�[�j�i�����̗L���j
		syokenUmu.add(NYUBOU_ONPA_UMU);   //9F140160700000011";	//���[�����g�����i�����̗L���j
		syokenUmu.add(SIKYU_KEIBU_UMU);   //9N291160700000011";	//�q�{�z�����f�i�����̗L���j
		syokenUmu.add(SIKYU_NAISHIN_UMU);   //9N296160700000011";	//�q�{���f�i�����̗L���j
		syokenUmu.add(TYOKU_KOUMON2_UMU);   //9Z771160700000011";	//�������@�\�i2���ڈȏ�j�i�����̗L���j
		syokenUmu.add(TYOKU_KOUMON1_UMU);   //9Z770160700000011";	//�������@�\�i1���ځj�i�����̗L���j
	}

	//edit h.yoshitama 2009.09.15
	private static final void initializeSyoken(){
		syoken.add(GYOMUREKI_VAL);   //9N051000000000049;	//�Ɩ���
		syoken.add(SONOTA_KAZOKUREKI_VAL);   //9N071000000000049;	//���̑�(�Ƒ���)
		syoken.add(SHISHIN_KOUKU_VAL);   //9N076000000000049;	//���f(���o���܂�)
		syoken.add(DATYOSHIN_VAL);   //9N081000000000049";	//�Œ��f
		syoken.add(SHOKUSHIN_KANSETSUKADO_VAL);   //9N086000000000049;	//�G�f(�֐߉���܂�)
		syoken.add(NYOTINSA_VAL);   //1A105160800166249";	//�A���ԁi�����j
//		syoken.add(SHINDENZU_VAL);   //9A110160800000049";	//�S�d�}�i�����j
		syoken.add(KYUBU_X_TYUKU_VAL);   //9N206160800000049";	//�����G�b�N�X�������i��ʁF���ڎB�e�j�i�����j
		syoken.add(KYUBU_X_KAN_VAL);   //9N221160800000049";	//�����G�b�N�X�������i��ʁF�ԐڎB�e�j�i�����j
		syoken.add(KAKUTAN_VAL);   //6A010160806170449";	//�\ႌ��� �i�h�������@��ʍ׋ہj�i�����j
		syoken.add(KYUBU_CT_VAL);   //9N251160800000049";	//�����b�s�����i�����j
		syoken.add(JYOUBU__X_TYUKU_VAL);   //9N256160800000049";	//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�����j
		syoken.add(JYOUBU_SYOUKAKAN_X_VAL);   //9N261160800000049";	//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�����j
		syoken.add(JYOUBU_KANNAISIKYOU_VAL);   //9N266160800000049";	//�㕔�����Ǔ����������i�����j
		syoken.add(FUKUBU_ONPA_VAL);   //9F130160800000049";	//���������g�i�����j
		syoken.add(FIJINKA_VAL);   //9N271160800000049";	//�w�l�Ȑf�@�i�����j
		syoken.add(NYUBOU_VAL);   //9N276160800000049";	//���[���G�f�i�����j
		syoken.add(NYUBOU_GAZOU_VAL);   //9N281160800000049";	//���[�摜�f�f�i�}�����O���t�B�[�j�i�����j
		syoken.add(NYUBOU_ONPA_VAL);   //9F140160800000049";	//���[�����g�����i�����j
		syoken.add(SIKYU_KEIBU_VAL);   //9N291160800000049";	//�q�{�z�����f�i�����j
		syoken.add(SIKYU_NAISHIN_VAL);   //9N296160800000049";	//�q�{���f(�����j
		syoken.add(TYOKU_KOUMON2_VAL);   //9Z771160800000049";	//�������@�\�i2���ڈȏ�j�i�����j
		syoken.add(TYOKU_KOUMON1_VAL);   //9Z770160800000049";	//�������@�\�i1���ځj�i�����j

		syoken.add(TYORYOKU_SONOTA_VAL);   //9D100160900000049;	//���́i���̑��̏����j
		syoken.add(CODE_CGATAKANEN_VAL);   //9N401000000000011;	//C�^�̉��E�B���X���f�̔���
		syoken.add(SONOTA_TOKUSYUKENSHIN_VAL);   //9N406000000000049;	//���̑��̖@����ꌒ�N�f�f
		syoken.add(SONOTA_HOUTEIKENSA_VAL);   //9N411000000000049;	//���̑��̖@�쌟��
		syoken.add(SONOTA_KENSA_VAL);   //9N416000000000049";	//���̑��̌���
		syoken.add(ISHI_IKEN_VAL);   //9N521000000000049;	//��t�̈ӌ�
		syoken.add(ISHI_NAME_IKEN_VAL);   //9N526000000000049";	//�ӌ����q�ׂ���t�̎���
		syoken.add(KENSHIN_SHIKAI_VAL);   //9N531000000000049;	//���Ȉ�t�ɂ�錒�N�f�f
		syoken.add(SHIKAI_NAME_KENSHIN_VAL);   //9N536000000000049;	//���N�f�f�����{�������Ȉ�t�̎���
		syoken.add(SHIKAI_IKEN_VAL);   //9N541000000000049;	//���Ȉ�t�̈ӌ�
		syoken.add(SHIKAI_NAME_IKEN_VAL);   //9N546000000000049;	//�ӌ����q�ׂ����Ȉ�t�̎���

		syoken.add(BIKOU_VAL);   //9N551000000000049;	//���l
		syoken.add(ISHI_NAME_SHINDAN_VAL);   //9N576000000000049;	//�f�f��������t�̎���
		syoken.add(JIYUUKISAI_HAIGAN_VAL);   //9N581161400000049";	//��t�̐f�f(�x���񌟐f)(���R�L��)
		syoken.add(ISHI_NAME_HAIGAN_VAL);   //9N586000000000049";	//�f�f��������t�̎���(�x���񌟐f)
		syoken.add(JIYUUKISAI_IGAN_VAL);   //9N591161400000049";	//��t�̐f�f(�݂��񌟐f)(���R�L��)
		syoken.add(ISHI_NAME_IGAN_VAL);   //9N596000000000049;	//�f�f��������t�̎���(�݂��񌟐f)
		syoken.add(JIYUUKISAI_NYUGAN_VAL);   //9N601161400000049;	//��t�̐f�f(�����񌟐f)(���R�L��)
		syoken.add(ISHI_NAME_NYUGAN_VAL);   //9N606000000000049;	//�f�f��������t�̎���(�����񌟐f)
		syoken.add(JIYUUKISAI_SHIKYUGAN_VAL);   //9N611161400000049;	//��t�̐f�f(�q�{���񌟐f)(���R�L��)
		syoken.add(ISHI_NAME_SHIKYUGAN_VAL);   //9N616000000000049";	//�f�f��������t�̎���(�q�{���񌟐f)
		syoken.add(JIYUUKISAI_DAITYOGAN_VAL);   //9N621161400000049;	//��t�̐f�f(�咰���񌟐f)(���R�L��)

		syoken.add(ISHI_NAME_DAITYOGAN_VAL);   //9N626000000000049;	//�f�f��������t�̎���(�咰���񌟐f)
		syoken.add(JIYUUKISAI_ZENRITSUSENGAN_VAL);   //9N631161400000049;	//��t�̐f�f(�O���B���񌟐f)(���R�L��)
		syoken.add(ISHI_NAME_ZENRITSUSENGAN_VAL);   //9N636000000000049;	//�f�f��������t�̎���(�O���B���񌟐f)
		syoken.add(JIYUUKISAI_SONOTA_VAL);   //9N641000000000049";	//��t�̐f�f(���̑�)
		syoken.add(ISHI_NAME_SONOTA_VAL);   //9N646000000000049";	//�f�f��������t�̎���(���̑�)
	}

	static{
		initializeSyokenUmu();
		initializeSyoken();
	}
// move s.inoue 2011/09/30
//	//������{�`�F�b�N���X�g�̍���
//
//	public static final String SEIKATU_HYOKA_1 = "9N556000000000011";	//�����@�\�]���̌��ʂP
//	public static final String SEIKATU_HYOKA_2 = "9N561000000000011";	//�����@�\�]���̌��ʂQ
//	public static final String SEIKATU_HYOKA_3 = "9N566000000000049";	//�����@�\�]���̌��ʂR
//	public static final String ISHINO_HANDAN = "9N571000000000049";	//��t�̐f�f�i����j�i�����@�\�]���j
//
//	public static final String KINOU_1 = "9N811000000000011";	//�P�D�o�X��d�Ԃ�1�l�ŊO�o���Ă��܂���
//	public static final String KINOU_2 = "9N816000000000011";	//�Q�D���p�i�̔��������Ă��܂���
//	public static final String KINOU_3 = "9N821000000000011";	//�R�D�a�����̏o����������Ă��܂���
//	public static final String KINOU_4 = "9N826000000000011";	//�S�D�F�l�̉Ƃ�K�˂Ă��܂���
//	public static final String KINOU_5 = "9N831000000000011";	//�T�D�Ƒ���F�l�̑��k�ɂ̂��Ă��܂���
//	public static final String KINOU_6 = "9N836000000000011";	//�U�D�K�i���肷���ǂ�����炸�ɏ����Ă��܂���
//	public static final String KINOU_7 = "9N841000000000011";	//�V�D�֎q�ɍ�������Ԃ��牽�����܂炸�ɗ����オ���Ă��܂���
//	public static final String KINOU_8 = "9N846000000000011";	//�W�D15���ʑ����ĕ����Ă��܂���
//	public static final String KINOU_9 = "9N851000000000011";	//�X�D����1�N�Ԃɓ]�񂾂��Ƃ�����܂���
//	public static final String KINOU_10 = "9N856000000000011";	//�P�O�D�]�|�ɑ΂���s���͑傫���ł���
//	public static final String KINOU_11 = "9N861000000000011";	//�P�P�D6�����Ԃ�2�`3kg�ȏ�̑̏d����������܂�����
//	public static final String KINOU_12 = "9N866000000000001";	//�P�Q�D�g���@�@�@�@�@�����@�@�@�̏d�@�@�@�@�@�����@�i�a�l�h���@�@�@�@�j
//	public static final String KINOU_13 = "9N871000000000011";	//�P�R�D���N�O�ɔ�ׂČł����̂��H�ׂɂ����Ȃ�܂�����
//	public static final String KINOU_14 = "9N876000000000011";	//�P�S�D������`�����łނ��邱�Ƃ�����܂���
//	public static final String KINOU_15 = "9N881000000000011";	//�P�T�D���̊������C�ɂȂ�܂���
//	public static final String KINOU_16 = "9N886000000000011";	//�P�U�D�T�ɂP��ȏ�͊O�o���Ă��܂���
//	public static final String KINOU_17 = "9N891000000000011";	//�P�V�D��N�Ɣ�ׂĊO�o�̉񐔂������Ă��܂���
//	public static final String KINOU_18 = "9N896000000000011";	//�P�W�D����̐l����u�����������𕷂��v�Ȃǂ̕��Y�ꂪ����ƌ����܂���
//	public static final String KINOU_19 = "9N901000000000011";	//�P�X�D�����œd�b�ԍ��𒲂ׂāA�d�b�������邱�Ƃ����Ă��܂���
//	public static final String KINOU_20 = "9N906000000000011";	//�Q�O�D�����������������킩��Ȃ���������܂���
//	public static final String KINOU_21 = "9N911000000000011";	//�Q�P�D�i����2�T�ԁj�����̐����ɏ[�������Ȃ�
//	public static final String KINOU_22 = "9N916000000000011";	//�Q�Q�D�i����2�T�ԁj����܂Ŋy����ł��Ă������Ƃ��y���߂Ȃ��Ȃ���
//	public static final String KINOU_23 = "9N921000000000011";	//�Q�R�D�i����2�T�ԁj�ȑO�͊y�ɂł��Ă������Ƃ����ł͂��������Ɋ�������
//	public static final String KINOU_24 = "9N926000000000011";	//�Q�S�D�i����2�T�ԁj���������ɗ��l�Ԃ��Ǝv���Ȃ�
//	public static final String KINOU_25 = "9N931000000000011";	//�Q�T�D�i����2�T�ԁj�킯���Ȃ���ꂽ�悤�Ȋ���������

	public static final ArrayList<String> seikatuKihon = new ArrayList<String>();

// move s.inoue 2011/09/30
//	public static final String[] codesSeikatuKihon = {
//		"9N811000000000011","9N816000000000011","9N821000000000011","9N826000000000011",
//		"9N831000000000011","9N836000000000011","9N841000000000011","9N846000000000011",
//		"9N851000000000011","9N856000000000011","9N861000000000011","9N866000000000001",
//		"9N871000000000011","9N876000000000011","9N881000000000011","9N886000000000011",
//		"9N891000000000011","9N896000000000011","9N901000000000011","9N906000000000011",
//		"9N911000000000011","9N916000000000011","9N921000000000011","9N926000000000011",
//		"9N931000000000011",
//		"9N556000000000011","9N561000000000011","9N566000000000049","9N571000000000049"
//	};

	private static final void initializeSeikatuKihon(){
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_1);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_2);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_3);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_4);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_5);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_6);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_7);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_8);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_9);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_10);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_11);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_12);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_13);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_14);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_15);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_16);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_17);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_18);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_19);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_20);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_21);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_22);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_23);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_24);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_25);
		seikatuKihon.add(JConstantString.SEIKATU_HYOKA_1);
		seikatuKihon.add(JConstantString.SEIKATU_HYOKA_2);
		seikatuKihon.add(JConstantString.SEIKATU_HYOKA_3);
		seikatuKihon.add(JConstantString.ISHINO_HANDAN);
	}

	static {
		initializeSeikatuKihon();
	}

}
