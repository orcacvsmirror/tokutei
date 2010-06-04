package jp.or.med.orca.jma_tokutei.common.orca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ORCA 設定
 *
 * Modified 2008/04/20 若月
 *   コメント追加。
 *   定数抽出。
 *   日レセユーザ、パスワード追加。
 */
public class JORCASetting {
	/* Added 2008/04/20 若月  */
	/* --------------------------------------------------- */
	private static final String KEY_ENCODE = "Encode";
	private static final String KEY_NICHIRESE_PASSWORD = "NichiresePassword";
	private static final String KEY_NICHIRESE_USER = "NichireseUser";
	private static final String KEY_PASSWORD = "Password";
	private static final String KEY_USER = "User";
	private static final String KEY_PROTOCOL = "Protocol";
	private static final String KEY_DATABASE = "Database";
	private static final String KEY_PORT = "Port";
	private static final String KEY_IP_ADDRESS = "IpAddress";
	private static final String FILE_EXTENSION = ".properties";
	private static final String FILE_PREFIX = "orca_";
	/* --------------------------------------------------- */

	/* Added 2008/07/22 若月  */
	/* --------------------------------------------------- */
	private static final String KEY_USE_ORCAID_DIGIT = "UseOrcaIdDigit";
	private static final String KEY_ORCAID_DIGIT = "OrcaIdDigit";
	/* --------------------------------------------------- */

	public JORCASetting(String KikanNumber)
	{
		this.orcaKikanNumber = KikanNumber;
	}
	private String orcaKikanNumber = null;
	private String orcaIpAddress = null;
	private String orcaPort = null;
	private String orcaDatabase = null;
	private String orcaProtocol = null;
	private String orcaUser = null;
	private String orcaPassword = null;
	private String orcaEncode = null;

	/* Added 2008/04/20 若月  */
	/* --------------------------------------------------- */
	private String nichireseUser = null;
	private String nichiresePassword = null;
	/* --------------------------------------------------- */

	/* Added 2008/07/22 若月  */
	/* --------------------------------------------------- */
	private boolean useOrcaIdDigit = false;

	private String orcaIdDigit = null;
//	private int orcaIdDigit = 0;
	/* --------------------------------------------------- */

	public String getOrcaIpAddress() {
		return orcaIpAddress;
	}
	public void setOrcaIpAddress(String ipAddress) {
		orcaIpAddress = ipAddress;
	}
	public String getOrcaPort() {
		return orcaPort;
	}
	public void setOrcaPort(String port) {
		orcaPort = port;
	}
	public String getOrcaDatabase() {
		return orcaDatabase;
	}
	public void setOrcaDatabase(String database) {
		orcaDatabase = database;
	}
	public String getOrcaProtocol() {
		return orcaProtocol;
	}
	public void setOrcaProtocol(String proctocol) {
		orcaProtocol = proctocol;
	}
	public String getOrcaUser() {
		return orcaUser;
	}
	public void setOrcaUser(String user) {
		orcaUser = user;
	}
	public String getOrcaPassword() {
		return orcaPassword;
	}
	public void setOrcaPassword(String password) {
		orcaPassword = password;
	}
	public String getOrcaEncode() {
		return orcaEncode;
	}
	public void setOrcaEncode(String encode) {
		orcaEncode = encode;
	}

	public boolean Load()
	{
		boolean retValue = false;
		Properties prop = new Properties();

		String fileName = FILE_PREFIX + orcaKikanNumber + FILE_EXTENSION;
		File configFile = new File(fileName);
		if (configFile.exists()) {
			try {
				prop.load(new FileInputStream(
						FILE_PREFIX
						+ orcaKikanNumber
						+ FILE_EXTENSION));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

			/* Modified 2008/08/04 若月  */
			/* --------------------------------------------------- */
//			this.orcaIpAddress = prop.getProperty(KEY_IP_ADDRESS);
//			this.orcaPort = prop.getProperty(KEY_PORT);
//			this.orcaDatabase = prop.getProperty(KEY_DATABASE);
//			this.orcaProtocol = prop.getProperty(KEY_PROTOCOL);
//			this.orcaUser = prop.getProperty(KEY_USER);
//			this.orcaPassword = prop.getProperty(KEY_PASSWORD);
//			this.nichireseUser = prop.getProperty(KEY_NICHIRESE_USER);
//			this.nichiresePassword = prop.getProperty(KEY_NICHIRESE_PASSWORD);
//
//			this.orcaEncode = prop.getProperty(KEY_ENCODE);
//
//			this.useOrcaIdDigit = Boolean.parseBoolean(prop.getProperty(KEY_USE_ORCAID_DIGIT));
//
////			this.orcaIdDigit = Integer.parseInt(prop.getProperty(KEY_ORCAID_DIGIT));
//			this.orcaIdDigit = prop.getProperty(KEY_ORCAID_DIGIT);
			/* --------------------------------------------------- */

			this.orcaIpAddress = prop.getProperty(KEY_IP_ADDRESS);
			this.orcaPort = prop.getProperty(KEY_PORT);
			this.orcaDatabase = prop.getProperty(KEY_DATABASE);
			this.orcaProtocol = prop.getProperty(KEY_PROTOCOL);
			this.orcaUser = prop.getProperty(KEY_USER);
			this.orcaPassword = prop.getProperty(KEY_PASSWORD);
			this.nichireseUser = prop.getProperty(KEY_NICHIRESE_USER);
			this.nichiresePassword = prop.getProperty(KEY_NICHIRESE_PASSWORD);
			this.orcaEncode = prop.getProperty(KEY_ENCODE);
			this.useOrcaIdDigit = Boolean.parseBoolean(prop.getProperty(KEY_USE_ORCAID_DIGIT));
			this.orcaIdDigit = prop.getProperty(KEY_ORCAID_DIGIT);
			/* --------------------------------------------------- */

			retValue = true;
		}

		return retValue;
	}

	public boolean Save()
	{
		Properties prop = new Properties();

		prop.put(KEY_IP_ADDRESS, this.orcaIpAddress);
		prop.put(KEY_PORT, this.orcaPort);
		prop.put(KEY_DATABASE, this.orcaDatabase);
		prop.put(KEY_PROTOCOL, this.orcaProtocol);
		prop.put(KEY_USER, this.orcaUser);
		prop.put(KEY_PASSWORD, this.orcaPassword);

		/* Added 2008/04/20 若月  */
		/* --------------------------------------------------- */
		prop.put(KEY_NICHIRESE_USER, this.nichireseUser);
		prop.put(KEY_NICHIRESE_PASSWORD, this.nichiresePassword);
		/* --------------------------------------------------- */

		prop.put(KEY_ENCODE, this.orcaEncode);

		/* Added 2008/07/22 若月 */
		/* --------------------------------------------------- */
		prop.put(KEY_USE_ORCAID_DIGIT, String.valueOf(this.useOrcaIdDigit));

//		if (this.orcaIdDigit != null) {
//			prop.put(KEY_ORCAID_DIGIT, this.orcaIdDigit);
//		}

		prop.put(KEY_ORCAID_DIGIT, String.valueOf(this.orcaIdDigit));
		/* --------------------------------------------------- */

		try {
			prop.store(new FileOutputStream(FILE_PREFIX + orcaKikanNumber + FILE_EXTENSION),"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/* Added 2008/04/20 若月  */
	/* --------------------------------------------------- */
	public String getNichiresePassword() {
		return nichiresePassword;
	}
	public void setNichiresePassword(String nichiresePassword) {
		this.nichiresePassword = nichiresePassword;
	}
	public String getNichireseUser() {
		return nichireseUser;
	}
	public void setNichireseUser(String nichireseUser) {
		this.nichireseUser = nichireseUser;
	}
	/* --------------------------------------------------- */

	/* Added 2008/07/22 若月  */
	/* --------------------------------------------------- */
	public String getOrcaIdDigit() {
		return orcaIdDigit;
	}
	public void setOrcaIdDigit(String orcaIdDigit) {
		this.orcaIdDigit = orcaIdDigit;
	}
//	public int getOrcaIdDigit() {
//		return orcaIdDigit;
//	}
//	public void setOrcaIdDigit(int orcaIdDigit) {
//		this.orcaIdDigit = orcaIdDigit;
//	}

	public boolean isUseOrcaIdDigit() {
		return useOrcaIdDigit;
	}
	public void setUseOrcaIdDigit(boolean useOrcaIdDigit) {
		this.useOrcaIdDigit = useOrcaIdDigit;
	}
	/* --------------------------------------------------- */
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

