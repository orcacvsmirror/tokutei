package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KihonKensaKoumoku;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * ���͕[�̍��ڂ��쐬����
 */
public class PrintNyuryoku extends JTKenshinPrint {

	private String templatePath = ""; // �f�B���N�g���p�X

	private String outputPath = ""; // �f�B���N�g���p�X

	private String infilename = "inNyuryokuTemplate.pdf";// ���̓e���v���[�g�t�@�C��

	private String outfilename = "outNyuryokuTemplate.pdf";// �o�̓t�@�C��

	// ���f�N����
	private int KensaNengapiNum = 0;

	private String[] KensaNengapi = new String[3];

	// ����ς݌������ڃR�[�h�ꗗ
	// private ArrayList<String>PrintedCD = new ArrayList<String>();

	public static void main(String[] argv) {
		new PrintNyuryoku();
	}

	public PrintNyuryoku() {
	}

	PrinterJob job = PrinterJob.getPrinterJob();

	/**
	 * ���͕[�̍��ڂ��쐬����
	 */
	public File printKekka() {
		/* Modified 2008/07/25 �ጎ  */
		/* --------------------------------------------------- */
//		templatePath = PropertyUtil.getProperty("filePath");
//		outputPath = PropertyUtil.getProperty("filePath");
//		templatePath = templatePath + infilename;
//		outputPath = ".\\Data\\PDF\\" + outfilename;
		/* --------------------------------------------------- */
		PropertyUtil util = new PropertyUtil(JPath.PATH_FILE_PROPERTIES);

		templatePath = util.getProperty("filePath");
		outputPath = templatePath;
		templatePath = templatePath + infilename;
		/* --------------------------------------------------- */
		/* Modified 2008/09/11 ��  */
		/* --------------------------------------------------- */
//		outputPath = ".\\Data\\PDF\\" + outfilename;
		outputPath = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename;
		/* --------------------------------------------------- */

		/* --------------------------------------------------- */
		/* Modified 2008/09/11 ��  */
		/* --------------------------------------------------- */
//		File outfile = new File(".\\Data\\PDF");
		File outfile = new File("."+File.separator+"Data"+File.separator+"PDF");
		/* --------------------------------------------------- */
		outfile.mkdirs();

		// File n = new File(".\\Data\\PDF\\outNyuryokuTemplate.pdf");
		File file = null;
		try {
			/*
			 * �l��� PrintData����l���𒊏o
			 */
			Kojin tmpKojin = (Kojin) printData.get("Kojin");
			Hashtable<String, String> kojinData = tmpKojin.getPrintKojinData();

			/* --------------------------------------------------- */
			/* Added 2008/09/11 ��  */
			/* --------------------------------------------------- */
			String strKensaYmd = "";
			if(kojinData.get("KENSA_NENGAPI")==null){
				// edit stop s.inoue 2009/10/15
				// strKensaYmd = String.valueOf(FiscalYearUtil.getSystemDate());
				// edit s.inoue 2009/10/16
				// strKensaYmd = null;
				strKensaYmd = getKenshinDate();
			}else{
				strKensaYmd = replaseNenGaPii(kojinData.get("KENSA_NENGAPI"));
//				strKensaYmd = kojinData.get("KENSA_NENGAPI").replaceAll("[^0-9]+","");
			}

			// �t�@�C���쐬
			PdfReader reader = new PdfReader(templatePath);
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
					outputPath + kojinData.get("UKETUKE_ID")
			/* --------------------------------------------------- */
			/* Modified 2008/09/11 ��  */
			/* --------------------------------------------------- */
//							+ kojinData.get("KENSA_NENGAPI")));
							+ strKensaYmd));
			/* --------------------------------------------------- */
			AcroFields form = stamp.getAcroFields();

			/* --------------------------------------------------- */
			/* Modified 2008/09/11 ��  */
			/* --------------------------------------------------- */
//			file = new File(".\\Data\\PDF\\" + outfilename

			file = new File("."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename
					+ kojinData.get("UKETUKE_ID")
//					+ kojinData.get("KENSA_NENGAPI"));
					+ strKensaYmd);
			/* --------------------------------------------------- */

			// ���f�N����
			form.setField("T1", kojinData.get("KENSA_NENGAPI"));
			form.setField("T19", kojinData.get("KENSA_NENGAPI"));
			// ��f���ԍ�
			form.setField("T2", kojinData.get("JYUSHIN_SEIRI_NO"));
			form.setField("T20", kojinData.get("JYUSHIN_SEIRI_NO"));
			// �ی��Ҕԍ�
			form.setField("T3", kojinData.get("HKNJANUM"));
			form.setField("T21", kojinData.get("HKNJANUM"));
			// ��ی��ҏؓ��L��
			form.setField("T4", kojinData.get("HIHOKENJYASYO_KIGOU"));
			form.setField("T22", kojinData.get("HIHOKENJYASYO_KIGOU"));
			// ��ی��ҏؓ��ԍ�
			form.setField("T5", kojinData.get("HIHOKENJYASYO_NO"));
			form.setField("T23", kojinData.get("HIHOKENJYASYO_NO"));
			// �t���K�i
			form.setField("T6", kojinData.get("KANANAME"));
			form.setField("T24", kojinData.get("KANANAME"));
			// ����
			form.setField("T7", kojinData.get("NAME"));
			form.setField("T26", kojinData.get("NAME"));

			// ���N����
			form.setField("T8", kojinData.get("BIRTHDAY"));
			form.setField("T25", kojinData.get("BIRTHDAY"));

			// ����
			form.setField("T9", kojinData.get("SEX"));
			form.setField("T27", kojinData.get("SEX"));

			// �N��
			form.setField("T10", kojinData.get("AGE"));
			form.setField("T28", kojinData.get("AGE"));

// del s.inoue 2009/11/16
//			if (kojinData.get("UKETUKE_ID") != null) {
//				Hashtable<String, String> ResultItem = null;
//				// �ǉ��̍���
//				// ���ږ�
//				int setCnt = 11;
//				List tuika = this.tuika(kojinData);
//				int tuikaSize = tuika.size();
//
//				if (tuikaSize > 0) {
//					for (int i = 0; i < tuikaSize; i++) {
//						ResultItem = (Hashtable<String, String>) tuika.get(i);
//						form.setField("S" + Integer.toString(setCnt),
//								ResultItem.get("KOUMOKU_NAME"));
//						setCnt++;
//					}
//				}
//
//				// ���ږ�
//				int setTani = 19;
//				if (tuikaSize > 0) {
//					for (int i = 0; i < tuikaSize; i++) {
//						ResultItem = (Hashtable<String, String>) tuika.get(i);
//						form.setField("S" + Integer.toString(setTani),
//								ResultItem.get("TANI"));
//						setTani++;
//					}
//				}
//
//			}

			stamp.setFormFlattening(true);
			stamp.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * ���͕[�̒ǉ��̌��f���ڂ��쐬����B
	 */
	private List tuika(Hashtable<String, String> KojinData) {

		ArrayList<Hashtable<String, String>> Result = null;
		Hashtable<String, String> ResultItem = null;
		String Query = null;

		ArrayList<Hashtable<String, String>> rtnList = new ArrayList<Hashtable<String, String>>();

		/* Modified 2008/07/15 �ጎ  */
		/* --------------------------------------------------- */
//		Query = "SELECT"
//			+ " T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, "
//			+ " T_KENSHINMASTER.KOUMOKU_NAME AS KOUMOKU_NAME, "
//			+ " T_KENSHINMASTER.TANI AS TANI"
//			+ " From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD)"
//			+ " WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = "
//			+ KojinData.get("UKETUKE_ID")
//			+ " AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI = "
//			+ KojinData.get("UKETUKE_NENGAPI")
//			+ " AND T_KENSHINMASTER.HKNJANUM = "
//			+ KojinData.get("HKNJANUM");
		/* --------------------------------------------------- */
		Query = "SELECT"
			+ " T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, "
			+ " T_KENSHINMASTER.KOUMOKU_NAME AS KOUMOKU_NAME, "
			+ " T_KENSHINMASTER.TANI AS TANI"
			+ " From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD)"
			+ " WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = '"
			+ KojinData.get("UKETUKE_ID")
			// edit s.inoue 2009/11/13
			// + "' AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI = '"
			// + KojinData.get("UKETUKE_NENGAPI")
			+ "' AND T_KENSHINMASTER.HKNJANUM = '"
			+ KojinData.get("HKNJANUM")
			+ "'";
		/* --------------------------------------------------- */

		try {
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		KihonKensaKoumoku kihonKensaKoumoku = new KihonKensaKoumoku();
		kihonKensaKoumoku.setKenshinCD();
		for (int i = 0; i < Result.size(); i++) {
			ResultItem = Result.get(i);
			if (! kihonKensaKoumoku.TSUIKA_CODES.contains(ResultItem
					.get("KOUMOKU_CD"))) {
			} else {
				rtnList.add(Result.get(i));
			}
		}
		return rtnList;
	}

	private String replaseNenGaPii(String kensaNengappi ) {
		try {
			kensaNengappi = kensaNengappi.replaceAll("�N", "");
			kensaNengappi = kensaNengappi.replaceAll("��", "");
			kensaNengappi = kensaNengappi.replaceAll("��", "");
		} catch (RuntimeException e) {
			return "";
		}
		return kensaNengappi;
	}


	// /**
	// * ����ςݍ��� add
	// * @param void
	// */
	// public void setKenshinCD() {
	// //������
	// this.PrintedCD.add("9N056000000000011");
	// //���o�Ǐ�
	// this.PrintedCD.add("9N061000000000011");
	// //���o�Ǐ�
	// this.PrintedCD.add("9N066000000000011");
	// //�i����
	// this.PrintedCD.add("9N736000000000011");
	// //�g��
	// this.PrintedCD.add("9N001000000000001");
	// //�̏d
	// this.PrintedCD.add("9N006000000000001");
	// /*
	// * ���́i�����j 9N016160100000001
	// * ���́i���Ȕ���j 9N016160200000001
	// * ���́i���Ȑ\���j 9N016160300000001
	// */
	// this.PrintedCD.add("9N016160100000001");
	// this.PrintedCD.add("9N016160200000001");
	// this.PrintedCD.add("9N016160300000001");
	// //BMI
	// this.PrintedCD.add("9N011000000000001");
	// /*
	// * ���k�������i1��ځj 9A751000000000001
	// * ���k�������i2��ځj 9A752000000000001
	// * ���k�������i���̑��j 9A755000000000001
	// */
	// this.PrintedCD.add("9A751000000000001");
	// this.PrintedCD.add("9A752000000000001");
	// this.PrintedCD.add("9A755000000000001");
	// /*
	// * �g���������i���ځj 9A761000000000001
	// * �g���������i���ځj 9A762000000000001
	// * �g���������i���̑��j 9A765000000000001
	// */
	// this.PrintedCD.add("9A761000000000001");
	// this.PrintedCD.add("9A762000000000001");
	// this.PrintedCD.add("9A765000000000001");
	// /*
	// * �������b�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j 3F015000002327101
	// * �������b�i���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j 3F015000002327201
	// * �������b�i���̑��j 3F015000002399901
	// */
	// this.PrintedCD.add("3F015000002327101");
	// this.PrintedCD.add("3F015000002327201");
	// this.PrintedCD.add("3F015000002399901");
	// /*
	// * HDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j�j 3F070000002327101
	// * HDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j�j 3F070000002327201
	// * HDL-�R���X�e���[��-�i���̑��j 3F070000002399901
	// */
	// this.PrintedCD.add("3F070000002327101");
	// this.PrintedCD.add("3F070000002327201");
	// this.PrintedCD.add("3F070000002399901");
	// /*
	// * LDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j 3F077000002327101
	// * LDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j 3F077000002327201
	// * LDL-�R���X�e���[��-�i���̑��j 3F077000002399901
	// */
	// this.PrintedCD.add("3F077000002327101");
	// this.PrintedCD.add("3F077000002327201");
	// this.PrintedCD.add("3F077000002399901");
	// /*
	// * GOT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j 3B035000002327201
	// * GOT�i���̑��j 3B035000002399901
	// */
	// this.PrintedCD.add("3B035000002327201");
	// this.PrintedCD.add("3B035000002399901");
	// /*
	// * GPT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j 3B045000002327201
	// * GPT�i���̑��j 3B045000002399901
	// */
	// this.PrintedCD.add("3B045000002327201");
	// this.PrintedCD.add("3B045000002399901");
	// /*
	// * y-GTP�i���z�����x�@�iJSCC�W�����Ή��@�j�j 3B090000002327101
	// * y-GTP�i���̑��j 3B090000002399901
	// */
	// this.PrintedCD.add("3B090000002327101");
	// this.PrintedCD.add("3B090000002399901");
	// /*
	// * �󕠎������i�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j�j 3D010000001926101
	// * �󕠎������i���z�����x�@�i�u�h�E���_���y�f�@�j�j 3D010000002227101
	// * �󕠎������i���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j�j 3D010000001927201
	// * �󕠎������i���̑��j 3D010000001999901
	// */
	// this.PrintedCD.add("3D010000001926101");
	// this.PrintedCD.add("3D010000002227101");
	// this.PrintedCD.add("3D010000001927201");
	// this.PrintedCD.add("3D010000001999901");
	// /*
	// * �Ӹ�����A1c �Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j 3D045000001906202
	// * �Ӹ�����A1c HPLC(�s���蕪�揜��HPLC�@�j 3D045000001920402
	// * �Ӹ�����A1c �y�f�@ 3D045000001927102
	// * �Ӹ�����A1c ���̑� 3D045000001999902
	// */
	// this.PrintedCD.add("3D045000001906202");
	// this.PrintedCD.add("3D045000001920402");
	// this.PrintedCD.add("3D045000001927102");
	// this.PrintedCD.add("3D045000001999902");
	// /*
	// * �� �������@�i�@�B�ǂݎ��j 1A020000000191111
	// * �� �������@�i�ڎ��@�j 1A020000000190111
	// */
	// this.PrintedCD.add("1A020000000191111");
	// this.PrintedCD.add("1A020000000190111");
	// /*
	// * �`�� �������@�i�@�B�ǂݎ��j 1A010000000191111
	// * �`�� �������@�i�ڎ��@�j 1A010000000190111
	// */
	// this.PrintedCD.add("1A010000000191111");
	// this.PrintedCD.add("1A010000000190111");
	//
	// //�Ԍ�����
	// this.PrintedCD.add("2A020000001930101");
	// //���F�f��
	// this.PrintedCD.add("2A030000001930101");
	// //�w�}�g�N���b�g�l
	// this.PrintedCD.add("2A040000001930102");
	// //�S�d�}����
	// this.PrintedCD.add("9A110160800000049");
	// //��ꌟ������
	// this.PrintedCD.add("9E100160900000049");
	// //���^�{���b�N�V���h���[������
	// this.PrintedCD.add("9N501000000000011");
	// //��t�̔��f
	// this.PrintedCD.add("9N511000000000049");
	// //���f������t��
	// this.PrintedCD.add("9N516000000000049");
	//
	// //1-1 �������������𕞗p���Ă���
	// this.PrintedCD.add("9N701000000000011");
	// //1-2 �C���X�������˖��͌��������������g�p���Ă���
	// this.PrintedCD.add("9N706000000000011");
	// //1-3 �R���X�e���[�����������𕞗p���Ă���
	// this.PrintedCD.add("9N711000000000011");
	// //4 ��t����A�]�����i�]�o���A�]�[�Ǔ��j�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
	// this.PrintedCD.add("9N716000000000011");
	// //5 ��t����A�S���a�i���S�ǁA�S�؍[�ǁj�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
	// this.PrintedCD.add("9N721000000000011");
	// //6 ��t����A�����̐t�s�S�ɂ������Ă���Ƃ���ꂽ��A���Ái�l�H���́j���󂯂����Ƃ�����
	// this.PrintedCD.add("9N726000000000011");
	// //7 ��t����n���Ƃ���ꂽ���Ƃ�����
	// this.PrintedCD.add("9N731000000000011");
	// //8 ���݁A���΂����K���I�ɋz���Ă���
	// this.PrintedCD.add("9N736000000000011");
	// //9 �Q�O�΂̎��̑̏d����P�O�L���ȏ㑝�����Ă���
	// this.PrintedCD.add("9N741000000000011");
	// //10 �P��R�O���ȏ�̌y�����������^�����T�Q���ȏ�A�P�N�ȏ���{���Ă���
	// this.PrintedCD.add("9N746000000000011");
	// //11 ���퐶���ɂ����ĕ��s���͓����̐g�̊������P���P���Ԉȏ���{���Ă���
	// this.PrintedCD.add("9N751000000000011");
	// //12 ������̓����Ɣ�r���ĕ������x������
	// this.PrintedCD.add("9N756000000000011");
	// //13 ���̂P�N�Ԃő̏d�̑������}�Rkg�ȏ゠��
	// this.PrintedCD.add("9N761000000000011");
	// //14 ���H���E�h�J�H���E�Ȃ���H��������
	// this.PrintedCD.add("9N766000000000011");
	// //15 �A�Q�O�̂Q���Ԉȓ��ɗ[�H���Ƃ邱�Ƃ��T�ɂR��ȏ゠��
	// this.PrintedCD.add("9N771000000000011");
	// //16 ��H��ԐH������
	// this.PrintedCD.add("9N776000000000011");
	// //17 ���H�𔲂����Ƃ�����
	// this.PrintedCD.add("9N781000000000011");
	//	//18 �قږ����A���R�[������������
	//	this.PrintedCD.add("9N786000000000011");
	//	//19 �������1��������̈����
	//	this.PrintedCD.add("9N791000000000011");
	//	//20 �����ŋx�{�������Ă���
	//	this.PrintedCD.add("9N796000000000011");
	//	//21 �^���␶�������̐����K�������P���Ă݂悤�Ǝv���܂���
	//	this.PrintedCD.add("9N801000000000011");
	//	//21 �����K���a�̉��P�ɂ��ĕی��w�����󂯂�@�����΁A���p���܂���
	//	this.PrintedCD.add("9N806000000000011");
	//}

}