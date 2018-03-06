package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JGraphicPrinter;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * �ǉ����f���ڕ\���쐬����
 * ���������ڂ͑S�ău�����N�ō쐬����i��f�҂̏��̂ݐݒ�j
 * 
 */
public class PrintTsuikaKenshin extends JGraphicPrinter {

	private static final String OUTPUT_PATH = "." + File.separator + "Data" + File.separator + "PDF" + File.separator; 		//�o�͐�̃p�X�i�f�B���N�g���j
	private static final String TEMPLATE_PATH = "." + File.separator + "work" + File.separator + "PDF" + File.separator;	//�e���v���[�g�t�@�C���̃f�B���N�g���p�X
	private static final String IN_FILE_NAME = "inNyuryokuTemplate_Add.pdf";	//���̓e���v���[�g�t�@�C����
	private static final String OUT_FILE_NAME = "outNyuryokuTemplate_Add.pdf";	//�o�̓t�@�C����

	//���́i�t�H�[���j�f�[�^
	private Hashtable<String, Object> printData = null;
	
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param printData	�t�H�[���f�[�^
	 */
	public PrintTsuikaKenshin(Hashtable<String, Object> printData) {
		this.printData = printData;
	}

	/**
	 * PDF�t�@�C���i�ǉ����f���ڕ\�j�̍쐬
	 * ���������ڂ͑S�ău�����N�ō쐬����i��f�҂̏��̂ݐݒ�j
	 */
	@Override
	public File printKekka() {

		//�o�͐�f�B���N�g���̍쐬
		File outfile = new File(OUTPUT_PATH);
		outfile.mkdirs();

		//�t�@�C���Q
		PdfReader reader = null;
		FileOutputStream fileOutputStream = null;
		PdfStamper stamp = null;
		File file = null;
		
		try {
			//�l��� PrintData����l���𒊏o
			Kojin tmpKojin = (Kojin) printData.get("Kojin");
			Hashtable<String, String> kojinData = tmpKojin.getPrintKojinData();

			//�t�@�C�����쐬���A�t�H�[���֖��ߍ���
			file = new File(OUTPUT_PATH, OUT_FILE_NAME + kojinData.get("UKETUKE_ID") + replaseNenGaPii(kojinData.get("KENSA_NENGAPI")));
			reader = new PdfReader(TEMPLATE_PATH + IN_FILE_NAME);
			fileOutputStream = new FileOutputStream(file);
			stamp = new PdfStamper(reader, fileOutputStream);
			AcroFields form = stamp.getAcroFields();
			// T31:���{��			
			form.setField("T31" , kojinData.get("KENSA_NENGAPI"));
			// T32:��f���ԍ�
			form.setField("T32" , kojinData.get("JYUSHIN_SEIRI_NO"));
			// T33:�ی��Ҕԍ�
			form.setField("T33" , kojinData.get("HKNJANUM"));
			// T34:��ی��ҏ؋L��
			form.setField("T34" , kojinData.get("HIHOKENJYASYO_KIGOU"));
			// T35:�ԍ�
			form.setField("T35" , kojinData.get("HIHOKENJYASYO_NO"));
			// T36:�ӂ肪��
			form.setField("T36" , kojinData.get("KANANAME"));
			// T37:����
			form.setField("T37" , kojinData.get("NAME"));
			// T38:���N����
			form.setField("T38" , kojinData.get("BIRTHDAY"));
			// T39:����
			form.setField("T39" , kojinData.get("SEX"));
			// T40:�N��
			form.setField("T40" , kojinData.get("AGE"));
			
			//���ߍ��݌�Ƀt���b�g���i�ύX�s�j����
			stamp.setFormFlattening(true);

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (stamp != null) {
				try {
					stamp.close();
				} catch (DocumentException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return file;
	}
	
	/**
	 * �S�p��"�N����"���폜
	 * @param kensaNengappi
	 * @return
	 */
	private String replaseNenGaPii(String kensaNengappi) {
		String result;
		if ((kensaNengappi != null) && (kensaNengappi.trim().length() != 0)) {
			result = kensaNengappi.replaceAll("�N", "").replaceAll("��", "").replaceAll("��", "");
		} else {
			result = "";
		}
		return result;
	}
}
