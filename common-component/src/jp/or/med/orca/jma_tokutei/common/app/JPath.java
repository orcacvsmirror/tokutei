package jp.or.med.orca.jma_tokutei.common.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

/**
 * �t�@�C���p�X�̊Ǘ��N���X
 * �e�\�t�g�E�F�A�ŗ��p���鋤�ʃp�X�͂����ŊǗ�����B
 * ���ʂŗ��p���Ȃ��p�X�Ɋւ��Ă�JApplication�ŕێ�����B
 */
/**
 * @author asc
 *
 */
/**
 * @author asc
 *
 */
public class JPath {
	/**
	 *  �t�H���_�̋�؂蕶��
	 */
//	final public static String Separator =
//		System.getProperties().getProperty("file.separator");

	/**
	 *  �f�[�^�x�[�X�g���q
	 */
	final public static String DATA_BASE_EXTENSION =
		".fdb";

	public static final String PATH_FILE_PROPERTIES = "file.properties";


	public static final String CURRENT_DIR_PATH = getCurrnetPath();
	private static org.apache.log4j.Logger logger = Logger.getLogger(JPath.class);

	private static String getCurrnetPath(){
		String path = null;

		File currentDir = new File(".");
		try {
			path = currentDir.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			JErrorMessage.show("M0003", null);
		}

		return path;
	}

	/**
	 *  �X�v���b�V���C���[�W
	 */
	final public static String SplashPath =
		"SplashImage.png";

	/**
	 *  �f�[�^�x�[�X�t�H���_
	 */
	final public static String DatabaseFolder =
		"DB" + JApplication.FILE_SEPARATOR;

	/**
	 *  �@�֏��f�[�^�x�[�X
	 */
	final public static String BaseKikanDatabaseFilePath =
		DatabaseFolder + "Kikan" + DATA_BASE_EXTENSION;

	/**
	 *  �V�X�e���f�[�^�x�[�X
	 */
	final public static String SystemDatabaseFilePath =
		DatabaseFolder + "System" + DATA_BASE_EXTENSION;

	/**
	 *  �f�[�^�x�[�X�o�b�N�A�b�v�t�H���_
	 */
	final public static String BackupDatabaseFolder =
		"Backup" + JApplication.FILE_SEPARATOR;

	/**
	 *  �V�X�e���f�[�^�x�[�X�o�b�N�A�b�v�t�H���_
	 */
	final public static String BackupSystemDatabaseFolder =
		BackupDatabaseFolder + "System" + JApplication.FILE_SEPARATOR;

	/**
	 *  �@�փf�[�^�x�[�X�t�@�C���p�X
	 */
	final public static String BackupKikanDataBaseFolder =
		BackupDatabaseFolder + "Kikan" + JApplication.FILE_SEPARATOR;

	/* Modified 2008/08/07 �ጎ  */
	/* --------------------------------------------------- */
//	/**
//	 *  �ꎞ�f�B���N�g��
//	 */
//	final public static String TEMP_SYSTEM_DATABASEFILE_PATH =
//		"Work" + JApplication.FILE_SEPARATOR + "Temp" + JApplication.FILE_SEPARATOR;
//
//	/**
//	 * �ꎞ�I��HL7���o�͂���t�H���_
//	 */
//	public final static String TEMP_OUTPUT_HL7_DIRECOTRY_PATH =
//		"Work" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */
	/**
	 *  �ꎞ�f�B���N�g��
	 */
	final public static String TEMP_SYSTEM_DATABASEFILE_PATH =
		"work" + JApplication.FILE_SEPARATOR + "Temp" + JApplication.FILE_SEPARATOR;

	/**
	 * �ꎞ�I��HL7���o�͂���t�H���_
	 */
	public final static String TEMP_OUTPUT_HL7_DIRECOTRY_PATH =
		"work" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */

	/**
	 * HL7��Zip�t�@�C���̏o�̓t�H���_
	 */
	public final static String ZIP_OUTPUT_DIRECTORY_PATH =
		"Data" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */

	/**
	 * �I�����C���A�b�v�f�[�g�p�o�̓t�H���_
	 */
	public static final String MODULE_TEMP = "Module";

	/**
	 *  �@�փf�[�^�x�[�X
	 *
	 *    @param �@�֔ԍ�
	 */
	public static String GetKikanDatabaseFilePath( String number )
	{
		return DatabaseFolder + number + DATA_BASE_EXTENSION;
	}

	/**
	 *  JSql �V�X�e���f�[�^�x�[�X�̃t���p�X���擾����
	 */
	public static String GetSystemDatabaseFilePathToInitJSql()
	{
		/* Modified 2008/07/29 �ጎ  */
		/* --------------------------------------------------- */
//		return JFile.GetCurrentDirectory() + "DB" + JApplication.FILE_SEPARATOR + "System" + DATA_BASE_EXTENSION;
		/* --------------------------------------------------- */
		String dbDirectory = JApplication.systemDBPath;

		StringBuffer buffer = new StringBuffer();
		buffer.append(dbDirectory);
//		if (! dbDirectory.endsWith(JApplication.FILE_SEPARATOR) ) {
//			buffer.append(JApplication.FILE_SEPARATOR);
//		}
		buffer.append("System");
		buffer.append(DATA_BASE_EXTENSION);

		String path = buffer.toString();

		return path;
		/* --------------------------------------------------- */
	}

	/**
	 *  JSql �@�֏��f�[�^�x�[�X�̃t���p�X���擾����
	 *
	 *    @param �@�֔ԍ�
	 */
	public static String GetKikanDatabaseFilePathToInitJSql( String number )
	{
		/* Modified 2008/07/29 �ጎ  */
		/* --------------------------------------------------- */
//		return JFile.GetCurrentDirectory() + "DB" + JApplication.FILE_SEPARATOR + number + DATA_BASE_EXTENSION;
		/* --------------------------------------------------- */
		String dbDirectory = JApplication.systemDBPath;

		StringBuffer buffer = new StringBuffer();
		buffer.append(dbDirectory);
//		if (! dbDirectory.endsWith(JApplication.FILE_SEPARATOR) ) {
//			buffer.append(JApplication.FILE_SEPARATOR);
//		}
		buffer.append(number);
		buffer.append(DATA_BASE_EXTENSION);

		String path = buffer.toString();

		return path;
	}
	/**
	 *  JSql ���W�X�g�����A�f�X�N�g�b�v���擾����
	 *
	 *    @param �f�X�N�g�b�v�p�X
	 */
	public static String getDesktopPath() {
	    String ret = "";
	  	try{
	  		// �f�X�N�g�b�v
		    File fs= new File(System.getProperty("user.home"), "Desktop");
			ret = fs.getAbsolutePath();

			if(!fs.exists()){
		  		// �f�X�N�g�b�v
			    File ffs= new File(System.getProperty("user.home"), "�f�X�N�g�b�v");
				ret = ffs.getAbsolutePath();
		  		logger.info("use vista�f�X�N�g�b�v");

		  		// ���[�U�z�[��
		  		if(!ffs.exists()){
			  		File fffs= new File(System.getProperty("user.home"));
			  		ret = fffs.getAbsolutePath();
			  		logger.info("use user.home");
		  		}
			}
	  	} catch (Exception e) {
	  		// ���[�U�z�[��
	  		File fs= new File(System.getProperty("user.home"));
	  		ret = fs.getAbsolutePath();
	  		logger.info("use user.home");
	  	}

//	    try {
//	      String osname = System.getProperty("os.name");
//	      // "Linux","Mac"
//	      if(osname.indexOf("Windows")>=0){
//	    	  // Windows�ł������Ƃ��̏���
//	    	  File fs= new File(System.getProperty("user.home"), "Desktop");
//	    	  ret = fs.getAbsolutePath();
//
////	    	} else if(osname.indexOf("Linux")>=0){
////	    	  // Linux�ł������Ƃ��̏���
////	    	  System.getProperty(�guser.home�h)
////	    	} else if(osname.indexOf("Mac")>=0){
////	    	  // MacOS�ł������Ƃ��̏���
//	    	} else {
//	    		// ���̑��̊��������Ƃ��̏���
//	    		ret = System.getProperty("user.home");
//	    	}
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }

	    return ret;
	}

	// add s.inoue 2010/03/09
	/**
	 * �o�͗p�̃t�H���_���𐶐�����(_1-10)
	 * @return �G���[�R�[�h
	 * ret = 0 -> ����I��
	 * ret = 1 -> �t�H���_�̍쐬�Ɏ��s
	 * ret = 2 -> �t�H���_�쐬�̏���ɒB����
	 * ret = 4 -> �X�L�[�}�̃t�@�C���R�s�[�Ɏ��s
	 */
	public static String createDirectoryUniqueName(String uniqueName)
	{
		String retFileName = "";

		// �ϊ�����
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String CreateDate = format.format(new Date());

		int count;

		for(count = 1 ; count < 10000 ; count++)
		{
			retFileName = uniqueName + CreateDate + "_" + String.valueOf(count) + ".csv";

			File newFile = new File(getDesktopPath()+ File.separator + retFileName);

			if(newFile.exists() == false)
				break;
		}

		return retFileName;
	}
//	      String cmdline = "";
//	      osname = "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v Desktop";
//
//	      String line = "";
//
//	      Process pc = Runtime.getRuntime().exec(cmdline);
//	      BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream()));
//
//	      while ((line = br.readLine()) != null) {
//	        if (line.indexOf("Desktop") != -1) {
//	          ret = line.substring(line.indexOf("C"), line.length());
//	        }
//	      }
//	      br.close();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
//	    return ret;
//	  }
}




//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

