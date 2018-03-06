package jp.or.med.orca.jma_tokutei.common.app;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessageDialogFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.orca.JORCASetting;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.common.util.XMLDocumentUtil;

import org.apache.log4j.PropertyConfigurator;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.util.client.ClientSettings;

/**
 * �O���[�o�����̕ێ����s��
 */
public class JApplication {

	/**
	 * �C���X�^���X�𐶐��ł��Ȃ��悤�ɂ���
	 */
	protected JApplication() {
	}

	// �\�t�g�E�F�A�S�̂ɌW�����
	public static int authority;
	public final static int authorityAdmin = 1;
	public final static int authorityUser = 2;
	public static String systemDBUserName;
	public static String systemDBPassword;
	public static String systemDBCharSet;
	public static String systemDBPath;
	public static String systemDBLoginTimeOut;
	public static String systemDBServer;
	public static String systemDBMaxPoolSize;
	public static String systemDBPort;
	public static String userID;
	public static String password;
	public static String kikanNumber;
	public static int debugCode = 0;
	public static String kikanName;

	// �f�[�^�x�[�X
	public static JConnection systemDatabase = null;
	public static JConnection kikanDatabase = null;
	public static JConnection hokenjyaDatabase = null;

	// �f�[�^�x�[�X����̐ݒ�
	public static boolean useORCA = false;
	public static JORCASetting orcaSetting = null;
	public static String versionNumber;
	public static String Moduleversion;
	public static String SchemaVersion;
	public static String DBDataVersion;

	// t_version rowid
	public static HashMap<String, String>kikanSchemaVersionRowIds;
	public static String kikanSchemaVersionRowId= null;
	public static String kikanDataVersionRowId= null;
	public static String systemSchemaVersionRowId= null;
	public static String systemDataVersionRowId= null;
	public static String moduleDataVersionRowId= null;
	// ����flg
	public static boolean kikanVersionFlg= false;
	public static boolean MultiKikanVersionFlg= false;
	public static boolean systemVersionFlg= false;
	public static boolean moduleVersionFlg= false;

	public static String SCHEMA_VERSION = "SCHEMA_VERSION";
	public static String DB_VERSION = "DB_VERSION";
	public static String MODULE_VERSION = "MODULE_VERSION";

	// �����t���O
	public static boolean initTVersionInsFlg =true;

	// �萔����
	public static String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	public static String KAISOUHANTEI_CSV_PATH = "." + FILE_SEPARATOR + "CSV" + FILE_SEPARATOR + "KAISOU_HANTEI.csv";
	public static String CHECK_CSV_PATH = "." + FILE_SEPARATOR + "CSV" + FILE_SEPARATOR + "CHECK.csv";

	/* ���s���� */
	public static String LINE_SEPARATOR = System.getProperties().getProperty("line.separator");
	public static int PROGRESS_CNT= 0;
	// DB�J����(�������ڏ��)
	public static final String COLUMN_NAME_UKETUKE_ID = "UKETUKE_ID";
	public static final String COLUMN_NAME_KOUMOKUCD = "KOUMOKU_CD";
	public static final String COLUMN_NAME_KOUMOKUNAME = "KOUMOKU_NAME";
	public static final String COLUMN_NAME_KENSA_NENGAPI = "KENSA_NENGAPI";
	//public static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
	//public static final String COLUMN_NAME_IJYOTI_KBN = "IJYOTI_KBN";
	public static final String COLUMN_NAME_IJYOTI_KBN = "IJYO_TI";
	public static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
	public static final String COLUMN_NAME_TANI = "TANI";
	public static final String COLUMN_NAME_HYOUJI_KIJYUNTI = "HYOUJI_KIJYUNTI";
	public static final String COLUMN_NAME_KANANAME = "KANANAME";
	public static final String COLUMN_NAME_BIRTHDAY = "BIRTHDAY";
	public static final String COLUMN_NAME_SEX = "SEX";
	// add s.inoue 2010/01/26
	// ����t�H�[�}�b�g
	public static final Integer CSV_NITII_ZOKUSEI_JISIKIKAN_NO = 0;
	public static final Integer CSV_NITII_ZOKUSEI_JISI_DATE = 2;
	public static final Integer CSV_NITII_ZOKUSEI_JUSIN_SEIRI_NO = 125;
	public static final Integer CSV_NITII_ZOKUSEI_SHIMEI = 5;
	public static final Integer CSV_NITII_ZOKUSEI_SEINENGAPI = 3;
	public static final Integer CSV_NITII_ZOKUSEI_SEIBETU = 4;
	// CSV�t�H�[�}�b�g
	public static final Integer CSV_ZOKUSEI_KENSA_KIKAN = 0;
	public static final Integer CSV_ZOKUSEI_JISIKIKAN_NO = 1;
	public static final Integer CSV_ZOKUSEI_JISIKIKAN = 2;
	public static final Integer CSV_ZOKUSEI_KENSA_UKETUKE_DATE = 3;
	public static final Integer CSV_ZOKUSEI_KENSA_UKETUKE_NO = 4;
	public static final Integer CSV_ZOKUSEI_JISI_DATE = 5;
	public static final Integer CSV_ZOKUSEI_JUSIN_UKETUKE_NO = 6;
	public static final Integer CSV_ZOKUSEI_JUSIN_SEIRI_NO = 7;
	public static final Integer CSV_ZOKUSEI_SHIMEI = 8;
	public static final Integer CSV_ZOKUSEI_SEINENGAPI = 9;
	public static final Integer CSV_ZOKUSEI_SEIBETU = 10;
	public static final Integer CSV_ZOKUSEI_NYUBI_YOUKETU = 11;
	public static final Integer CSV_ZOKUSEI_SHOKUZEN_SHOKUGO = 12;
	public static final Integer CSV_KENSA_KOUMOKU_CD = 13;
	public static final Integer CSV_KENSA_KOUMOKU = 14;
	public static final Integer CSV_KENSA_JISI_KBN = 15;
	public static final Integer CSV_KENSA_IJYO_KBN = 16;
	public static final Integer CSV_KENSA_KEKATI_KEITAI = 17;
	public static final Integer CSV_KENSA_KEKATI = 18;
	public static final Integer CSV_KENSA_TANI = 19;
	public static final Integer CSV_KENSA_KIJUN = 20;
	// �������ڏ��:1�`22,�������ڌ�:8
	// eidt s.inoue 2013/02/26
	// public static final Integer CSV_KENSA_LOOP = 22;
	public static final Integer CSV_KENSA_LOOP = 280;
	public static final Integer CSV_KENSA_KOUMOKU_LOOP = 8;

	public static final Integer CSV_ZOKUSEI_COUNT = 12;

	// �G���[field
	public static final String ZOKUSEI_FIELD_JISI_DATE = "���f���{�N����";
	public static final String ZOKUSEI_FIELD_JISI_KIKAN_NO = "���f���{�@�֔ԍ�";
	public static final String ZOKUSEI_FIELD_JUSIN_SEIRI_NO = "��f�������ԍ�";
	public static final String ZOKUSEI_FIELD_SHIMEI = "����";
	public static final String ZOKUSEI_FIELD_SEINENGAPI = "���N����";
	public static final String ZOKUSEI_FIELD_SEIBETU = "����";
	public static final String ZOKUSEI_FIELD_YOUKETU = "���сE�n��";
	public static final String ZOKUSEI_FIELD_SHOKUZENGO = "�H�O/�H��";

	public static final String KENSA_FIELD_KOUMOKU_CD = "�������ڃR�[�h";
	public static final String KENSA_FIELD_JISI_KBN = "���{�敪";
	//public static final String KENSA_FIELD_IJYO_KBN = "�ُ�l�敪";
	//public static final String KENSA_FIELD_KEKATI_KEITAI = "���ʒl�`��";
	public static final String KENSA_FIELD_KEKATI = "��������";
	// �G���[���
	public static final Integer ERR_KIND_NUMBER = 0;
	public static final Integer ERR_KIND_DATE = 1;
	public static final Integer ERR_KIND_DIGIT = 2;
	public static final Integer ERR_KIND_NAME = 3;
	public static final Integer ERR_KIND_BIRTHDAY = 4;
	public static final Integer ERR_KIND_SEX = 5;
	public static final Integer ERR_KIND_NOT_EXIST = 6;
	public static final Integer ERR_KIND_KEY_EXIST = 7;
	public static final Integer ERR_KIND_KEY_DOUBLE = 8;
	public static final Integer ERR_KIND_HALF = 9;
	public static final Integer ERR_KIND_EMPTY = 10;
	public static final Integer ERR_KIND_TYPE = 11;
	public static final Integer ERR_KIND_DATA = 12;
	public static final String CSV_CHARSET ="SHIFT-JIS";

	public static final String DB_SCHEMA_VERSION = "SCHEMA_VERSION";
	public static final String DB_DATA_VERSION = "DB_VERSION";

	public static final String[] jishiKBNTable = { "0:�����{", "1:���{","2:����s�\" };

	// ��{�I�Ȍ��f,�ڍׂȌ��f,�ǉ����f����,�l�ԃh�b�N
	public static final String SEIKYU_KBN_BASE = "1";
	public static final String SEIKYU_KBN_SYOSAI = "2";
	public static final String SEIKYU_KBN_TUIKA = "3";
	public static final String SEIKYU_KBN_SYOSAI_TUIKA = "4";
	public static final String SEIKYU_KBN_DOC = "5";
	public static final String HISU_FLG_BASE = "1";
	public static final String HISU_FLG_SYOSAI = "2";
	public static final String HISU_FLG_TUIKA = "3";
	public static final String HISU_FLG_MONSHIN = "4";
	//	private static String VERSION_FILE = "MainVersion.properties";
	private static final String DB_FILENAME_SYSTEM_FDB = "System.fdb";

	// add s.inoue 2010/03/18
	// ��ʃ��X�g�Ή��R�[�h�ꗗ
	public static final String SCREEN_JYUSINKEN_CODE = "001";     // ��f�����͉��
	public static final String SCREEN_KEKKATOUROKU_CODE = "002";  // ���f���ʃf�[�^����
	public static final String SCREEN_KEKKATORIKOMI_CODE = "003"; // �����f�[�^��荞��
	public static final String SCREEN_HANTEI_CODE = "004"; // ���^�{����E�K�w��
	public static final String SCREEN_SHOSAI_CODE = "005"; // �ڍו\��
	public static final String SCREEN_NITIJI_CODE = "006"; // ��������(����)
	public static final String SCREEN_GETUJI_CODE = "007"; // ��������(�����m��/HL7�o��)

	public static final String SCREEN_SEIKYU_EDIT_CODE = "008";    // �����f�[�^�ҏW
	public static final String SCREEN_KIKAN_DBBACKUP_CODE = "009"; // �@�ւc�a�o�b�N�A�b�v
	public static final String SCREEN_LOGIN_CODE = "010"; 		  // ���O�C��
	public static final String SCREEN_NYURYOKUHYO_CODE = "011";   // ���͕[
	public static final String SCREEN_KEKKATUTIHYO_CODE = "012";  // ���ʒʒm�\
	public static final String SCREEN_ONLINE_UPDATE_CODE = "013"; // �I�����C���A�b�v�f�[�g

	public static final String SCREEN_MASTER_KIKAN_CODE = "101"; // �@�֏�񃁃��e�i���X
	public static final String SCREEN_MASTER_KENSHIN_CODE = "102"; // ���f���ڃ}�X�^�����e�i���X
	public static final String SCREEN_MASTER_PATTERN_CODE = "103"; // ���f�p�^�[�������e�i���X
	public static final String SCREEN_MASTER_SHIHARAI_CODE = "104"; // �x����s�@�փ}�X�^�����e
	public static final String SCREEN_MASTER_HOKENJYA_CODE = "105"; // �ی��ҏ��}�X�^�����e�i���X
	public static final String SCREEN_MASTER_USER_CODE = "106"; // �V�X�e���Ǘ����[�U��񃁃��e�i���X
	public static final String SCREEN_MASTER_SYSTEM_CODE = "107"; // �V�X�e�����p�҃����e�i���X
	public static final String SCREEN_MASTER_KEINEN_CODE = "108"; // �o�N�}�X�^�����e�i���X
	public static final String SCREEN_MASTER_USEBILITY_CODE = "109"; // ���[�U�r���e�B�����e�i���X

	// add s.inoue 2011/05/10
	// �����n�ꗗ�J�E���g
	public static int gridViewSearchCount = 0;
	public static int gridViewMasterCount = 0;

    public static String func_keiyakuCode = "01";
    public static String func_futanCode = "02";
    public static String func_nyuryokuCode = "03";
    public static String func_yearOldCode = "05";
    public static String func_printCode = "06";
    public static String func_orijinCode = "04";
    public static Color backColor_Focus = new Color(195, 229, 254);
    public static Color backColor_UnFocus = new Color(255, 255, 255);
    // eidt s.inoue 2012/11/16
    public static ClientSettings clientSettings = null;
    public static Domain patternDomain = null;
    public static Domain graphDomain = null;
    // add s.inoue 2012/10/24
    public static Domain hokenjaDomain = null;
    public static Domain shiharaiDomain = null;

    public static Hashtable domains = null;

//     add s.inoue 2013/03/12
//    public static boolean blnLookFeelMetal = false;

    // add s.inoue 2013/03/12
    public static Font FONT_COMMON_BUTTON = null;
    public static Font FONT_COMMON_MENU_BUTTON = null;

    // eidt s.inoue 2012/11/28
//    public static ArrayList<Integer> selectedHistoryRows = null;
    public static ArrayList<Integer> selectedHistoryRows = new ArrayList<Integer>();

    // add s.inoue 2012/11/29
    public static ArrayList<Integer> selectedPreservRows = new ArrayList<Integer>();
    public static int selectedMaxRow = 0;

    public static boolean callValidateCancelFlg = false;
    public static boolean firstCheckedFlg =false;

    //add s.inous 2013/11/06
    public enum FlagEnum_Serche {
    	JYUSHIN_SEIRI_NO,
    	NAME, 
    	HIHOKENJYASYO_KIGOU, 
    	HIHOKENJYASYO_NO,
    	KENSA_NENGAPI, 
    	SEX, 
    	BIRTHDAY,
    	KEKA_INPUT_FLG, 
    	HKNJANUM, 
    	SIHARAI_DAIKOU_BANGO,
    	KANANAME,
    	HANTEI_NENGAPI, 
    	TUTI_NENGAPI,
    	NENDO, 
    	CHECKBOX_COLUMN,
    	//add tanaka 2013/11/07
    	TANKA_GOUKEI, 
    	MADO_FUTAN_GOUKEI,
    	SEIKYU_KINGAKU,
    	UPDATE_TIMESTAMP,
    	JISI_KBN,
    	HENKAN_NITIJI,
    	METABO,
    	HOKENSIDO_LEVEL,
    	KOMENTO
    }
    
    public enum FlagEnum_Nitiji {
    	JYUSHIN_SEIRI_NO, NAME, 
    	HIHOKENJYASYO_KIGOU, 
    	HIHOKENJYASYO_NO,
    	KENSA_NENGAPI, 
    	SEX, 
    	BIRTHDAY,
    	KEKA_INPUT_FLG, 
    	HKNJANUM, 
    	SIHARAI_DAIKOU_BANGO,
    	KANANAME,
    	HANTEI_NENGAPI, 
    	TUTI_NENGAPI,
    	NENDO, 
    	CHECKBOX_COLUMN,
    	//add tanaka 2013/11/07
    	TANKA_GOUKEI, 
    	MADO_FUTAN_GOUKEI,
    	SEIKYU_KINGAKU,
    	UPDATE_TIMESTAMP,
    	JISI_KBN, HENKAN_NITIJI,
    	METABO,
    	HOKENSIDO_LEVEL,
    	KOMENTO
    }
    
    public enum FlagEnum_Hantei {
    	JYUSHIN_SEIRI_NO, NAME, 
    	HIHOKENJYASYO_KIGOU, 
    	HIHOKENJYASYO_NO,
    	KENSA_NENGAPI, 
    	SEX, 
    	BIRTHDAY,
    	KEKA_INPUT_FLG, 
    	HKNJANUM, 
    	SIHARAI_DAIKOU_BANGO,
    	KANANAME,
    	HANTEI_NENGAPI, 
    	TUTI_NENGAPI,
    	NENDO, 
    	CHECKBOX_COLUMN,
    	//add tanaka 2013/11/07
    	TANKA_GOUKEI, 
    	MADO_FUTAN_GOUKEI,
    	SEIKYU_KINGAKU,
    	UPDATE_TIMESTAMP,
    	JISI_KBN, HENKAN_NITIJI,
    	METABO,
    	HOKENSIDO_LEVEL,
    	KOMENTO
    }
    
    public enum FlagEnum_Getuji {
    	JYUSHIN_SEIRI_NO, NAME, 
    	HIHOKENJYASYO_KIGOU, 
    	HIHOKENJYASYO_NO,
    	KENSA_NENGAPI, 
    	SEX, 
    	BIRTHDAY,
    	KEKA_INPUT_FLG, 
    	HKNJANUM, 
    	T_HOKENJYA, 
    	SIHARAI_DAIKOU_BANGO,
    	KANANAME,
    	HANTEI_NENGAPI, 
    	TUTI_NENGAPI,
    	NENDO, 
    	CHECKBOX_COLUMN,
    	//add tanaka 2013/11/07
    	TANKA_GOUKEI, 
    	MADO_FUTAN_GOUKEI,
    	SEIKYU_KINGAKU,
    	UPDATE_TIMESTAMP,
    	JISI_KBN, HENKAN_NITIJI,
    	METABO,
    	HOKENSIDO_LEVEL,
    	KOMENTO
    }
    
    /*add tanaka 2013/11/15*/
    public enum FlagEnum_Master {
    	HKNJANUM,
    	KOUMOKU_CD,
    	KOUMOKU_NAME,
    	KENSA_HOUHOU,
    	HISU_FLG,
    	DS_KAGEN,
    	DS_JYOUGEN,
    	JS_KAGEN,
    	JS_JYOUGEN,
    	TANI,
    	KAGEN,
    	JYOUGEN,
    	KIJYUNTI_HANI,
    	TANKA_KENSIN,
    	BIKOU,
    }
    
    // add s.inoue 2014/02/17
    public enum FlagEnum_KouteiMaster {
    	HKNJANUM,
    	JYUSHIN_SEIRI_NO,
    	HIHOKENJYASYO_KIGOU,
    	HIHOKENJYASYO_NO,
    	KENSA_NENGAPI,
    	BIRTHDAY,
    	SEX,
    	KANANAME,
    	KEKA_FLG,
    	HANTEI_FLG,
    	NITIJI_FLG,
    	GETUJI_FLG,
    }
    
    public static Set flag = EnumSet.noneOf(FlagEnum_Serche.class);
    public static Set flag_Nitiji = EnumSet.noneOf(FlagEnum_Nitiji.class);
    public static Set flag_Hantei = EnumSet.noneOf(FlagEnum_Hantei.class);
    public static Set flag_Getuji = EnumSet.noneOf(FlagEnum_Getuji.class);
    public static Set flag_Master = EnumSet.noneOf(FlagEnum_Master.class);
    public static Set flag_KouteiMaster = EnumSet.noneOf(FlagEnum_KouteiMaster.class);
    public static GridParams gridParams = null;
    public static ArrayList vals = null;
    public static FilterWhereClause[] clauseDesign = null;
    public static ArrayList currentSortedColumns = null;
    public static ArrayList currentSortedVersusColumns = null;
    
    // add s.inoue 2013/11/19
    public static Map<Integer,String> currentSorted = null;
	/**
	 * �ݒ�t�@�C���Ȃǂ�ǂݍ���a�B
	 */
	public static void load() {

		/* property.xml ���� DB �ݒ���擾����B */
		try {

			// eidt s.inoue 2013/03/12
//			FONT_COMMON_BUTTON = new Font("�l�r �S�V�b�N", 0, blnLookFeelMetal?10:12);
//			FONT_COMMON_MENU_BUTTON = new Font("�l�r �S�V�b�N", 0, blnLookFeelMetal?11:13);

			// add ver2 s.inoue 2009/05/19
			PropertyConfigurator.configure("log4j.properties");

			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));

			systemDBUserName = doc.getNodeValue("DBConfig", "UserName");
			systemDBPassword = doc.getNodeValue("DBConfig", "Password");
			systemDBCharSet = doc.getNodeValue("DBConfig", "CharSet");

			/*
			 * AbsolutePath �����΃p�X���擾����B
			 * �擾�ł��Ȃ���΁APath ���瑊�΃p�X���擾����B
			 */
			String path = doc.getNodeValue("DBConfig", "AbsolutePath");

			if (path == null ||  path.isEmpty()) {

				path = doc.getNodeValue("DBConfig", "Path");

				/* �ݒ肪�Ȃ��ꍇ�́A�f�t�H���g�̏ꏊ�i�J�����g�f�B���N�g��/DB�j���g�p����B */
				if (path == null || path.isEmpty()) {

					StringBuffer buffer = new StringBuffer();
					buffer.append(JPath.CURRENT_DIR_PATH);

					if (! buffer.toString().endsWith( JApplication.FILE_SEPARATOR)) {
						buffer.append(JApplication.FILE_SEPARATOR);
					}
					buffer.append("DB");
					buffer.append(JApplication.FILE_SEPARATOR);

					path = buffer.toString();
				}
				/* �ݒ肪����ꍇ�́A�J�����g�f�B���N�g������̑��΃p�X�Ƃ���B */
				else {
					File relativePath = new File(path);

					StringBuffer buffer = new StringBuffer();
					String canonicalPath = relativePath.getCanonicalPath();
					buffer.append(canonicalPath);

					if (! canonicalPath.endsWith(JApplication.FILE_SEPARATOR)) {
						buffer.append(JApplication.FILE_SEPARATOR);
					}

					path = buffer.toString();
				}
			}

			systemDBPath = path;
			systemDBLoginTimeOut = doc.getNodeValue("DBConfig", "LoginTimeOut");
			systemDBServer = doc.getNodeValue("DBConfig", "Server");
			systemDBMaxPoolSize = doc.getNodeValue("DBConfig", "MaxPoolSize");
			systemDBPort = doc.getNodeValue("DBConfig", "Port");

		} catch (Exception e1) {
			e1.printStackTrace();
			JErrorMessage.show("M0104", null);
			System.exit(1);
		}

		// �V�X�e���f�[�^�x�[�X�֐ڑ�
		try {
			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			JErrorMessage.show("M1000", null);
			System.exit(1);
		}
	}

	/**
	 * �f�o�b�O�p�����^�C���X�C�b�` (������e�̕ύX) �J�����p
	 */
	public static void runtimeTest(String debugString) {
		final String debugData = "NagcmWT_`Z```R_]Ppbf_oh`\\bUZ`XVg`V^T[jd\\qd`uZ["; // ����t���O
		int i, code = 0;
		String dst = "";
		for (i = 0; i < debugData.length(); i++)
			dst += (char) (debugData.charAt(i) + debugString.charAt(i) - 'a' + 1);
		for (i = 0; i < dst.length(); i++)
			code += (int) (dst.charAt(i) * dst.charAt(i) * (i + 1) + dst
					.charAt(i));
		debugCode = code;
		if (code == 0xC29F36) {
			new JErrorMessageDialogFrameCtrl(null, "TokuteiKenshin", dst
					.toUpperCase()
					+ "  ", 0, 0, "");
		}
	}

	/**
	 * ORCA�̃f�[�^�����[�h����B ���O�C����Ƀ��[�h���邱�ƁB
	 */
	public static void loadORCAData() {
		String sUseORCA = null;
		try {
			String query = "SELECT NITI_RESE FROM T_F_KIKAN WHERE TKIKAN_NO = "
										+ JQueryConvert.queryConvert(kikanNumber);

			ArrayList<Hashtable<String, String>> result = systemDatabase.sendExecuteQuery(query);
			if (result != null && result.size() > 0) {
				Hashtable<String, String> resultItem = result.get(0);

				if (resultItem != null && resultItem.size() > 0) {
					sUseORCA = resultItem.get("NITI_RESE");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			useORCA = false;
			return;
		}

		if (sUseORCA != null) {
			if (sUseORCA.equals("1")) {
				useORCA = true;
			} else {
				useORCA = false;
			}
		}
		else {
			useORCA = false;
		}

		// ORCA�̃f�[�^��ǂݍ���
		if (useORCA == true) {
			JORCASetting orca = new JORCASetting(kikanNumber);
			if (orca.Load() == true) {
				orcaSetting = orca;
			} else {
				// ORCA�̓Ǎ��݂Ɏ��s������AORCA���g�p�ł��Ȃ��悤�ɂ���B
				useORCA = false;
			}
		}
	}

	/**
	 * �o�[�W��������ǂݍ��ށB ���O�C���O�Ƀ��[�h���邱�ƁB
	 */
	public static void loadVersionFile(String versionFile) {
		// �o�[�W�����t�@�C��
		Properties PropVersion = new Properties();
		try {
			PropVersion.load(new FileInputStream(versionFile));

			versionNumber = PropVersion.getProperty("VersionNumber");
			Moduleversion = PropVersion.getProperty("ModuleVersion");
			SchemaVersion = "";
			DBDataVersion = "";

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * �o�[�W��������ǂݍ��ށB ���O�C���O�Ƀ��[�h���邱�ƁB
	 */
	public static void checkVersionFile(String versionFile) {
		// �o�[�W�����t�@�C��
		Properties PropVersion = new Properties();
		try {
			PropVersion.load(new FileInputStream(versionFile));
			Moduleversion = PropVersion.getProperty("ModuleVersion");


		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * ���f�\�t�g�A�Ǘ��p�\�t�g�N�����A�o�[�W���������������݁B
	 */
	public static void setVersionFile(String versionFile,String updTskSchemaVersion,String updTskDBDataVersion) {
		// �o�[�W�����t�@�C��
		Properties PropVersion = new Properties();
		try {

			PropVersion.load(new FileInputStream(versionFile));

			if (updTskSchemaVersion != null){
				PropVersion.setProperty("SchemaVersion", updTskSchemaVersion);
			}
			if (updTskDBDataVersion != null){
				PropVersion.setProperty("DBDataVersion", updTskDBDataVersion);
			}
			PropVersion.store( new FileOutputStream(versionFile), null );

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @return versionNumber
	 */
	public static String getVersionNumber() {
		return versionNumber;
	}

	/* edit 2009/01/13 ���  */
	/**
	 * SCHEMA_VERSION,DB_VERSION��T_VERSION���擾����
	 * @throws SQLException
	 * @return ROW_ID
	 * @throws Exception
	 */
	public static boolean loadDBVersions(JConnection dbcon) throws Exception {
		String verScv = null;
		String verDbv = null;
		String tVersionRowId = null;

		boolean flgRead = false;

		try {

			// t_version����
			String exQuery = "SELECT RDB$RELATION_NAME AS TableName FROM RDB$RELATIONS WHERE RDB$RELATION_NAME ='T_VERSION' " +
			   				 " ORDER BY RDB$RELATION_NAME ";
			try{
				ArrayList<Hashtable<String, String>> result =
					dbcon.sendExecuteQuery(exQuery);

				if (result != null && result.size() > 0) {
					flgRead = true;
				}

			}catch(Exception err){
				throw err;
			}

			if (flgRead){
				// t_version�Ǎ�
				StringBuffer buffer = new StringBuffer();

				buffer.append("select ROW_ID,SCHEMA_VERSION,DB_VERSION ");
				buffer.append(" from T_VERSION ");
				buffer.append(" where ROW_ID = (select MAX(ROW_ID) from T_VERSION) ");

				try{
					ArrayList<Hashtable<String, String>> result =
						dbcon.sendExecuteQuery(buffer.toString());

					if (result != null && result.size() > 0) {
						Hashtable<String, String> resultItem = result.get(0);

						if (resultItem != null && resultItem.size() > 0) {
							verScv = resultItem.get("SCHEMA_VERSION");
							verDbv = resultItem.get("DB_VERSION");
							if (resultItem.get("ROW_ID") != null)
							{
								tVersionRowId = resultItem.get("ROW_ID");
							}

							JApplication.SchemaVersion=verScv;
							JApplication.DBDataVersion=verDbv;
						}
					}
				}catch(SQLException err){
					throw err;
				}
			}
		} catch (Exception err) {
			throw err;
		}

		return true;
	}


	/**
	 * �@��DB(T_F_KIKAN)�����X�g������
	 * @param Database �ڑ���̃f�[�^�x�[�X
	 * @param Query �N�G��
	 * @return ���݂����true
	 * @throws Exception
	 */
	public static ArrayList<Hashtable<String, String>> KikanDatabaseItem(JConnection con)
		throws Exception {

		ArrayList<Hashtable<String, String>> arrTK =
			 new  ArrayList<Hashtable<String, String>>();

		// �f�B���N�g������
		File directory = new File(JApplication.systemDBPath);
		File[] DBlist = directory.listFiles();

		try{
			for (int n = 0; n < DBlist.length; ++n) {

				File dbFile = DBlist[n];
				String fileName = dbFile.getName();

				if (dbFile.isFile() && fileName.equals(DB_FILENAME_SYSTEM_FDB)) {
					arrTK = con.sendExecuteQuery("select TKIKAN_NO from T_F_KIKAN ");
				}
			}
		}catch( Exception err ){
			throw err;
		}finally{
			con.Shutdown();
			con = null;
		}
		return arrTK;
	}

	/**
	 * �@��DB(T_F_KIKAN)��T_Version�Ƀf�[�^�o�^
	 * @param Database �ڑ���̃f�[�^�x�[�X
	 * @return ���݂����true
	 * @throws Exception
	 */
	public static boolean tVersionRegister(JConnection con,
			String strVersionRowID,
			String strColumnver,
			String strVersion)

		throws Exception {

		StringBuffer buffer = new StringBuffer();

		// version�擾
		String verScv = JApplication.SchemaVersion;
		String verDbv = JApplication.DBDataVersion;

		try
		{
			con.Transaction();
			// timestamp�擾
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			if (strVersionRowID.equals("0"))
			{
				buffer.append("INSERT INTO T_VERSION (ROW_ID");
				buffer.append(",SCHEMA_VERSION");
				buffer.append(",DB_VERSION");
				buffer.append(",UPDATE_TIMESTAMP ");
				buffer.append(") ");
				buffer.append("SELECT case when max(row_id) is null then 1 else MAX(ROW_ID)+1 end, ");

				if (strColumnver.equals(JApplication.SCHEMA_VERSION)){
					buffer.append(JQueryConvert.queryConvertAppendComma(strVersion));
				}else{
					buffer.append(JQueryConvert.queryConvertAppendComma(verScv));
				}
				if (strColumnver.equals(JApplication.DB_DATA_VERSION)){
					buffer.append(JQueryConvert.queryConvertAppendComma(strVersion));
				}else{
					buffer.append(JQueryConvert.queryConvertAppendComma(verDbv));
				}
				buffer.append(JQueryConvert.queryConvert(stringTimeStamp));

				buffer.append(" FROM T_VERSION");

			}else{

				buffer.append("UPDATE T_VERSION ");
				buffer.append("SET ");
				if (strColumnver.equals(JApplication.SCHEMA_VERSION)){
					buffer.append("SCHEMA_VERSION = ");
					buffer.append(JQueryConvert.queryConvert(strVersion));
				}
				if (strColumnver.equals(JApplication.DB_DATA_VERSION)){
					buffer.append("DB_VERSION = ");
					buffer.append(JQueryConvert.queryConvert(strVersion));
				}

				buffer.append(" WHERE ROW_ID = ");
				buffer.append(strVersionRowID);

			}
			con.sendNoResultQuery(buffer.toString());

			con.Commit();
			System.out.println("T_Version regist" + "Sc:" + verScv + " "+ "Data:" + verDbv);

		} catch (Exception err) {
			con.rollback();
			err.printStackTrace();
			JErrorMessage.show("M9602", null);

			try {
				con.Shutdown();
			} catch (SQLException er) {
				er.printStackTrace();
				JErrorMessage.show("M0000", null);
				System.exit(1);
			}
		}
		return true;
	}

	public static void exit(int returnValue){
		System.out.println("�A�v���P�[�V�����I��");
		System.out.printf("�I���R�[�h[%d]", returnValue);
		System.exit(returnValue);
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

