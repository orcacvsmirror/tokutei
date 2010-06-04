package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import sun.management.counter.Units;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.zip.JZip;

/**
 * HL7�p�̃f�B���N�g�����Ǘ�����
 */
public class JOutputHL7Directory {
	private String Souhumoto;
	private String Souhusaki;
	private String uniqueName = null;
	private String getUniqueName(int count){
			this.uniqueName = Souhumoto + "_" + Souhusaki + "_" +
			CreateDate + String.valueOf(count) + "_" + "1";

		return this.uniqueName;
	}

	/**
	 * �ϊ����������̂����ł̕ϊ��񐔂�ێ�
	 */
	private String AllocDirectoryNumber;

	/**
	 * �ϊ������f�[�^���o�͂����f�B���N�g��
	 */
	private String RootDirectory;

	/**
	 * �ϊ�����
	 */
	private String CreateDate;

	/**
	 * Zip�t�@�C���̏o�͐�B
	 */
	private String zipOutputFilePath;

	private int ClinicalDocumentCount = 0;
	private int CheckupClaimCount = 0;

	public final static String SEPARATOR = System.getProperty("file.separator");
	public final static String XSDFolder = "XSD" + SEPARATOR;

	/**
	 * @param Souhumoto ���t���@�ւ̔ԍ�
	 * @param Souhusaki ���t��@�ւ̔ԍ�
	 */
	public JOutputHL7Directory(String Souhumoto,String Souhusaki) {
		this.Souhumoto = Souhumoto;
		this.Souhusaki = Souhusaki;
	}
	// add s.inoue 2009/09/24
	public String GetRootDirectoryPath(){
		return this.RootDirectory;
	}

	// add s.inoue 2009/09/24
	public String GetUniqueName(int count){
		this.uniqueName = Souhumoto + "_" + Souhusaki + "_" +
		CreateDate + String.valueOf(count) + "_" + "1";
	return this.uniqueName;
	}
	// add s.inoue 2009/10/18
	public String GetUniqueName(){
		this.uniqueName = Souhumoto + "_" + Souhusaki + "_" +
		CreateDate + String.valueOf(this.AllocDirectoryNumber) + "_" + "1";
	return this.uniqueName;
	}

	public String GetIndexPath()
	{
		return RootDirectory + "ix08_V08.xml";
	}

	public String GetSummaryPath()
	{
		return RootDirectory + "su08_V08.xml";
	}

	public String GetClinicalDocumentPath()
	{
		String Ret =  RootDirectory + "DATA" + SEPARATOR + "h" + Souhumoto + CreateDate + AllocDirectoryNumber +
					"1" + Utility.FillZero(ClinicalDocumentCount, 6) + ".xml";
		ClinicalDocumentCount++;
		return Ret;
	}

	public String GetCheckupClaimPath()
	{
		String Ret =  RootDirectory + "CLAIMS" + SEPARATOR + "c" + Souhumoto + CreateDate + AllocDirectoryNumber +
					"1" +  Utility.FillZero(CheckupClaimCount, 6) + ".xml";
		CheckupClaimCount++;
		return Ret;
	}

	/**
	 * �o�͗p�̃t�H���_���m�ۂ���B
	 * @return �G���[�R�[�h
	 * ret = 0 -> ����I��
	 * ret = 1 -> �t�H���_�̍쐬�Ɏ��s
	 * ret = 2 -> �t�H���_�쐬�̏���ɒB����
	 * ret = 4 -> �X�L�[�}�̃t�@�C���R�s�[�Ɏ��s
	 */
	public int AllocOutputDirectory()
	{
		// �ϊ�����
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		CreateDate = format.format(new Date());

		// ��ԍŏ��Ƀx�[�X�̃f�B���N�g��������Ă���̂����m�F
		File f = new File(JPath.TEMP_OUTPUT_HL7_DIRECOTRY_PATH);

		if(! f.exists())
		{
			f.mkdirs();
		}

		int count;

		// �ꎞ�I�ɏo�͂��s���t�H���_
		File tempOutputFolder = null;

		for(count = 0 ; count < 10 ; count++)
		{
			String uniqueName = this.getUniqueName(count);
			String zipFilePath = JPath.ZIP_OUTPUT_DIRECTORY_PATH
				+ uniqueName
				+ ".zip";

			File zipFile = new File(zipFilePath);

			/* �����̃t�@�C�������݂���ꍇ�́A�J�E���^���C���N�������g����B */
			if(zipFile.exists() == false)
			{
				try{
					zipOutputFilePath = zipFile.getPath();

					// �Ō��1�͎��{�敪
					String tempFolderPath = JPath.TEMP_OUTPUT_HL7_DIRECOTRY_PATH + uniqueName + SEPARATOR;
					tempOutputFolder = new File(tempFolderPath);

					/* �t�H���_�����݂���ꍇ�́A�폜����B */
					if (tempOutputFolder.exists()) {
						JFile.deleteDirectory(tempOutputFolder, true);
					}

					if(tempOutputFolder.mkdirs() == false)
					{
						return 1;
					}
					Thread.sleep( 1000 );
					// ���̃f�B���N�g�����쐬
					File dataFolder = new File(tempOutputFolder.getPath() + SEPARATOR + "DATA");
					if(dataFolder.mkdirs() == false)
					{
						return 1;
					}

					File claimsFolder = new File(tempOutputFolder.getPath() + SEPARATOR + "CLAIMS");
					if(claimsFolder.mkdirs() == false)
					{
						return 1;
					}

					break;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}

		// �󂫂̃t�H���_��������Ȃ������Ƃ�
		// count == �� => �ɏC��
		if(count >= 10)
		{
			// edit s.inoue 2009/10/02
			// ���(10���ȏ�)�ɒB������t�H���_�S�̂��폜����
			// return 2;
			try{
				String zipFolderPath = JPath.ZIP_OUTPUT_DIRECTORY_PATH;
				tempOutputFolder = new File(zipFolderPath);

				/* �t�H���_�����݂���ꍇ�́A�폜����B */
				if (tempOutputFolder.exists()) {
					JFile.deleteDirectory(tempOutputFolder, true);
				}
				File tempDir = new File(JPath.ZIP_OUTPUT_DIRECTORY_PATH);
				Thread.sleep( 1000 );
				if(! tempDir.exists())
					tempDir.mkdir();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return 2;
		}

		AllocDirectoryNumber = String.valueOf(count);

		RootDirectory = tempOutputFolder.getPath() + SEPARATOR;

		// XSD�t�@�C�����R�s�[����
		try {
			CopyXSDFiles();
		} catch (Exception e) {
			e.printStackTrace();
			return 4;
		}

		return 0;
	}

	/**
	 * �t�@�C�������k����
	 */
	public void CompressFile() throws IOException
	{
		File f1 = new File(RootDirectory);
		File f2 = new File(zipOutputFilePath);

		JZip.Zip(f1, f2);
	}

	/**
	 * �ꎞ�t�H���_���폜����
	 */
	public void DeleteFolder()
	{
		File f1 = new File(RootDirectory);
		JFile.deleteDirectory(f1, true);
	}

	/**
	 * XSD�t�@�C���̃R�s�[
	 */
	private void CopyXSDFiles() throws Exception
	{
		JFileCopy.xcopyFile(XSDFolder, RootDirectory + XSDFolder);
	}
}
