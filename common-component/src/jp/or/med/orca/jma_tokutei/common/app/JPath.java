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
 * ファイルパスの管理クラス
 * 各ソフトウェアで利用する共通パスはここで管理する。
 * 共通で利用しないパスに関してはJApplicationで保持する。
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
	 *  フォルダの区切り文字
	 */
//	final public static String Separator =
//		System.getProperties().getProperty("file.separator");

	/**
	 *  データベース拡張子
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
	 *  スプラッシュイメージ
	 */
	final public static String SplashPath =
		"SplashImage.png";

	/**
	 *  データベースフォルダ
	 */
	final public static String DatabaseFolder =
		"DB" + JApplication.FILE_SEPARATOR;

	/**
	 *  機関情報データベース
	 */
	final public static String BaseKikanDatabaseFilePath =
		DatabaseFolder + "Kikan" + DATA_BASE_EXTENSION;

	/**
	 *  システムデータベース
	 */
	final public static String SystemDatabaseFilePath =
		DatabaseFolder + "System" + DATA_BASE_EXTENSION;

	/**
	 *  データベースバックアップフォルダ
	 */
	final public static String BackupDatabaseFolder =
		"Backup" + JApplication.FILE_SEPARATOR;

	/**
	 *  システムデータベースバックアップフォルダ
	 */
	final public static String BackupSystemDatabaseFolder =
		BackupDatabaseFolder + "System" + JApplication.FILE_SEPARATOR;

	/**
	 *  機関データベースファイルパス
	 */
	final public static String BackupKikanDataBaseFolder =
		BackupDatabaseFolder + "Kikan" + JApplication.FILE_SEPARATOR;

	/* Modified 2008/08/07 若月  */
	/* --------------------------------------------------- */
//	/**
//	 *  一時ディレクトリ
//	 */
//	final public static String TEMP_SYSTEM_DATABASEFILE_PATH =
//		"Work" + JApplication.FILE_SEPARATOR + "Temp" + JApplication.FILE_SEPARATOR;
//
//	/**
//	 * 一時的にHL7を出力するフォルダ
//	 */
//	public final static String TEMP_OUTPUT_HL7_DIRECOTRY_PATH =
//		"Work" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */
	/**
	 *  一時ディレクトリ
	 */
	final public static String TEMP_SYSTEM_DATABASEFILE_PATH =
		"work" + JApplication.FILE_SEPARATOR + "Temp" + JApplication.FILE_SEPARATOR;

	/**
	 * 一時的にHL7を出力するフォルダ
	 */
	public final static String TEMP_OUTPUT_HL7_DIRECOTRY_PATH =
		"work" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */

	/**
	 * HL7のZipファイルの出力フォルダ
	 */
	public final static String ZIP_OUTPUT_DIRECTORY_PATH =
		"Data" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */

	/**
	 * オンラインアップデート用出力フォルダ
	 */
	public static final String MODULE_TEMP = "Module";

	/**
	 *  機関データベース
	 *
	 *    @param 機関番号
	 */
	public static String GetKikanDatabaseFilePath( String number )
	{
		return DatabaseFolder + number + DATA_BASE_EXTENSION;
	}

	/**
	 *  JSql システムデータベースのフルパスを取得する
	 */
	public static String GetSystemDatabaseFilePathToInitJSql()
	{
		/* Modified 2008/07/29 若月  */
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
	 *  JSql 機関情報データベースのフルパスを取得する
	 *
	 *    @param 機関番号
	 */
	public static String GetKikanDatabaseFilePathToInitJSql( String number )
	{
		/* Modified 2008/07/29 若月  */
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
	 *  JSql レジストリより、デスクトップを取得する
	 *
	 *    @param デスクトップパス
	 */
	public static String getDesktopPath() {
	    String ret = "";
	  	try{
	  		// デスクトップ
		    File fs= new File(System.getProperty("user.home"), "Desktop");
			ret = fs.getAbsolutePath();

			if(!fs.exists()){
		  		// デスクトップ
			    File ffs= new File(System.getProperty("user.home"), "デスクトップ");
				ret = ffs.getAbsolutePath();
		  		logger.info("use vistaデスクトップ");

		  		// ユーザホーム
		  		if(!ffs.exists()){
			  		File fffs= new File(System.getProperty("user.home"));
			  		ret = fffs.getAbsolutePath();
			  		logger.info("use user.home");
		  		}
			}
	  	} catch (Exception e) {
	  		// ユーザホーム
	  		File fs= new File(System.getProperty("user.home"));
	  		ret = fs.getAbsolutePath();
	  		logger.info("use user.home");
	  	}

//	    try {
//	      String osname = System.getProperty("os.name");
//	      // "Linux","Mac"
//	      if(osname.indexOf("Windows")>=0){
//	    	  // Windowsであったときの処理
//	    	  File fs= new File(System.getProperty("user.home"), "Desktop");
//	    	  ret = fs.getAbsolutePath();
//
////	    	} else if(osname.indexOf("Linux")>=0){
////	    	  // Linuxであったときの処理
////	    	  System.getProperty(“user.home”)
////	    	} else if(osname.indexOf("Mac")>=0){
////	    	  // MacOSであったときの処理
//	    	} else {
//	    		// その他の環境だったときの処理
//	    		ret = System.getProperty("user.home");
//	    	}
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }

	    return ret;
	}

	// add s.inoue 2010/03/09
	/**
	 * 出力用のフォルダ名を生成する(_1-10)
	 * @return エラーコード
	 * ret = 0 -> 正常終了
	 * ret = 1 -> フォルダの作成に失敗
	 * ret = 2 -> フォルダ作成の上限に達した
	 * ret = 4 -> スキーマのファイルコピーに失敗
	 */
	public static String createDirectoryUniqueName(String uniqueName)
	{
		String retFileName = "";

		// 変換日時
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

