package jp.or.med.orca.jma_tokutei.common.sql;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Pattern;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;

/**
 * �f�[�^�x�[�X�̃R�l�N�V�������Ǘ����邽�߂̃N���X
 */
public class JConnection {
	private static final String JDBC_DRIVER_NAME_FIREBIRD = "org.firebirdsql.jdbc.FBDriver";
	private Connection mConnection;

	private PreparedStatement m_PreparedState = null;

	/**
	 * �R���X�g���N�^
	 * @param driver JDBC�h���C�o��
	 * @param url �ڑ���URL
	 * @param user
	 * @param password
	 * @throws SQLException
	 */
	public JConnection(String driver, String url, String user, String password)
			throws SQLException {

		/* Added 2008/07/29 �ጎ  */
		/* --------------------------------------------------- */
		StringBuffer buffer = new StringBuffer();
		buffer.append("driver:");
		buffer.append(driver);
		buffer.append(JApplication.LINE_SEPARATOR);

		buffer.append("url:");
		buffer.append(url);
		buffer.append(JApplication.LINE_SEPARATOR);

		buffer.append("user:");
		buffer.append(user);
		buffer.append(JApplication.LINE_SEPARATOR);

		buffer.append("password:");
		buffer.append(password);
		buffer.append(JApplication.LINE_SEPARATOR);

		System.out.println(buffer.toString());
		/* --------------------------------------------------- */

		// SQL������
		try {
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mConnection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * �R�l�N�V���������
	 * @throws SQLException
	 */
	public void Shutdown() throws SQLException {
		// 2008/3/17 ���R �C��
		// �N���[�Y�݂̂ł̓R�l�N�V�����ւ̎Q�Ƃ��ێ����ꑱ��
		// GC�̑ΏۂɂȂ�Ȃ�(���������[�N�����̌����ƂȂ�)
		// �N���[�Y��A�����I��null����
		// ----------------------------------------------------
		//		mConnection.close();
		try {
			mConnection.close();
		} finally {
			mConnection = null;
		}
		// ----------------------------------------------------
	}

	/**
	 * Select�Ȃǂ̌��ʂ��Ԃ��Ă���N�G�������s����B
	 * @param query �N�G��������
	 * @return �J���������L�[�Ƃ����n�b�V���e�[�u���̃��X�g
	 * @throws SQLException
	 */
	public ArrayList<Hashtable<String, String>> sendExecuteQuery(String query)
		throws SQLException{

		return this.sendExecuteQuery(query, null);
	}


	/**
	 * Select�Ȃǂ̌��ʂ��Ԃ��Ă���N�G�������s����B
	 * @param query �N�G��������
	 * @return �J���������L�[�Ƃ����n�b�V���e�[�u���̃��X�g
	 * @throws SQLException
	 */
	public ArrayList<Hashtable<String, String>> sendExecuteQuery(String query, String[] params)
			throws SQLException {
		// �N�G���̕\��
		System.out.println(query);

		/* Added 2008/07/16 �ጎ  */
		/* --------------------------------------------------- */
		if (params != null) {
			System.out.print("params: ");
			for (int i = 0; i < params.length; i++) {

				if (i != params.length - 1) {
					System.out.print(params[i]);
					System.out.print(", ");
				}
				else {
					System.out.println(params[i]);
				}
			}
		}
		/* --------------------------------------------------- */

		ArrayList<Hashtable<String, String>> returnList
			= new ArrayList<Hashtable<String, String>>();
		PreparedStatement ps = mConnection.prepareStatement(query);

		if (params != null ){
			if ( params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setString(i + 1, params[i]);
				}
			}
		}

		ResultSet result = ps.executeQuery();

		//�f�[�^�擾
		ResultSetMetaData resultData = result.getMetaData();
		int count = resultData.getColumnCount();

		while (result.next()) {
			Hashtable<String, String> rowData = new Hashtable<String, String>();

			for (int i = 1; i <= count; i++) {
				// null�̃`�F�b�N
				if (result.getObject(i) == null) {
					rowData.put(resultData.getColumnName(i), "");
				} else {
					rowData.put(resultData.getColumnName(i),
							result.getObject(i).toString());
				}
			}

			returnList.add(rowData);
		}

		result.close();
		ps.close();

		return returnList;
	}

	// add ver2 s.inoue 2009/07/07
	/**
	 * Sort�����l������Select�Ȃǂ̌��ʂ��Ԃ��Ă���N�G�������s����B
	 * @param query �N�G��������
	 * @return �J���������L�[�Ƃ����n�b�V���e�[�u���̃��X�g
	 * @throws SQLException
	 */
	public ArrayList<TreeMap<String, String>> sendExecuteSortedQuery(String query, String[] params)
			throws SQLException {
		// �N�G���̕\��
		System.out.println(query);

		if (params != null) {
			System.out.print("params: ");
			for (int i = 0; i < params.length; i++) {

				if (i != params.length - 1) {
					System.out.print(params[i]);
					System.out.print(", ");
				}
				else {
					System.out.println(params[i]);
				}
			}
		}

		ArrayList<TreeMap<String, String>> returnList
			= new ArrayList<TreeMap<String, String>>();
		PreparedStatement ps = mConnection.prepareStatement(query);

		if (params != null ){
			if ( params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setString(i + 1, params[i]);
				}
			}
		}

		ResultSet result = ps.executeQuery();

		//�f�[�^�擾
		ResultSetMetaData resultData = result.getMetaData();
		int count = resultData.getColumnCount();

		while (result.next()) {
			TreeMap<String, String> rowData = new TreeMap<String, String>();

			for (int i = 1; i <= count; i++) {
				// null�̃`�F�b�N
				if (result.getObject(i) == null) {
					rowData.put(resultData.getColumnName(i), "");
				} else {
					rowData.put(resultData.getColumnName(i),
							result.getObject(i).toString());
				}
			}
			returnList.add(rowData);
		}

		result.close();
		ps.close();

		return returnList;
	}

	/* addd 2008/08/06 ���  */
	/* --------------------------------------------------- */
	/**
	 *  prepareStatement �ݒ�
	 *    @param  SQL��
	 *    @return none
	 */
	public void setPrepareQuery(String sql) throws Exception {
		try {
			m_PreparedState = mConnection.prepareStatement(sql);
		} catch (Exception err) {
			throw err;
		}
	}

	/**
	 *  prepareStatement ���s
	 *    @param  �ݒ胊�X�g
	 *    @return none
	 */
	public void execPrepareQuery(ArrayList<String> aryList) throws Exception {
		// System.out.printf("con.execPrepareQuery()[]");
		if (m_PreparedState == null) {
			return;
		}

		if (aryList == null) {
			return;
		}

		try {
			for (int i = 0; i < aryList.size(); ++i) {
				m_PreparedState.setString(i + 1, aryList.get(i).toString());
			}

			m_PreparedState.execute();
		} catch (Exception err) {
			throw err;
		}
	}

	/**
	 *  SQL���s
	 *    @param  SQL��
	 *    @return none
	 */
	public boolean exec(String sql) throws Exception {
		boolean retValue = false;

		Statement stmt = null;
		try {
			stmt = mConnection.createStatement();
			if (stmt != null) {
				System.out.println("exec: " + sql);
			}

			stmt.executeUpdate(sql);

			stmt.close();
			retValue = true;

		} catch (Exception err) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			try {
				this.mConnection.close();
			} catch (SQLException e) {
			}

			throw err;
		}

		return retValue;
	}

	/* --------------------------------------------------- */

	/* Modified 2008/06/26 �ጎ  */
	/* --------------------------------------------------- */
//	/**
//	 * Insert�Ȃǂ̌��ʂ��Ԃ��Ă��Ȃ��N�G���𑗐M����B
//	 * @param query �N�G��������
//	 * @throws SQLException
//	 */
//	public void sendNoResultQuery(String query) throws SQLException {
//		// �N�G���̕\��
//		System.out.println(query);
//
//		Statement st = mConnection.createStatement();
//		st.executeUpdate(query);
//
//		st.close();
//	}
	/* --------------------------------------------------- */
	/**
	 * Insert�Ȃǂ̌��ʂ��Ԃ��Ă��Ȃ��N�G���𑗐M����B
	 * @param query �N�G��������
	 * @throws SQLException
	 */
	public int sendNoResultQuery(String query, String[] params)
		throws SQLException {

		// �N�G���̕\��
		System.out.println(query);

		/* Added 2008/07/16 �ጎ  */
		/* --------------------------------------------------- */
		if (params != null) {
			System.out.print("params: ");
			for (int i = 0; i < params.length; i++) {

				if (i != params.length - 1) {
					System.out.print(params[i]);
					System.out.print(", ");
				}
				else {
					System.out.println(params[i]);
				}
			}
		}
		/* --------------------------------------------------- */

		PreparedStatement ps = mConnection.prepareStatement(query);

		if (params != null ){
			if ( params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setString(i + 1, params[i]);
				}
			}
		}

		int count = ps.executeUpdate();
		ps.close();

		return count;
	}

	/**
	 * Insert�Ȃǂ̌��ʂ��Ԃ��Ă��Ȃ��N�G���𑗐M����B
	 * @throws SQLException
	 */
	public int sendNoResultQuery(String query) throws SQLException {
		int count = this.sendNoResultQuery(query, null);
		return count;
	}
	/* --------------------------------------------------- */

	// �g�����U�N�V�����֘A

	/**
	 * �g�����U�N�V�������s���Ă���Œ����ǂ����B
	 */
	private boolean IsTransaction = false;

	/**
	 * �g�����U�N�V�������J�n����B
	 * @throws SQLException
	 */
	public void Transaction() throws SQLException {
		if (IsTransaction == true) {
			throw new RuntimeException();
		}

		mConnection.setAutoCommit(false);
		IsTransaction = true;
	}

	/**
	 * ���[���o�b�N���s���B
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		if (IsTransaction == false) {
			throw new RuntimeException();
		}

		mConnection.rollback();
		mConnection.setAutoCommit(true);

		IsTransaction = false;
	}

	/**
	 * �R�~�b�g���s���B
	 * @throws SQLException
	 */
	public void Commit() throws SQLException {
		if (IsTransaction == false) {
			throw new RuntimeException();
		}

		mConnection.commit();
		mConnection.setAutoCommit(true);

		IsTransaction = false;
	}

	/**
	 * �@�փf�[�^�x�[�X�ւ̐ڑ�
	 * @param KikanNumber �@�֔ԍ�
	 */
	/* Modified 2008/07/28 �ጎ  */
	/* --------------------------------------------------- */
//	public static JConnection ConnectKikanDatabase(String KikanNumber)
//	throws SQLException {
//System.out.println("org.firebirdsql.jdbc.FBDriver : "
//		+ "jdbc:firebirdsql:localhost/3050:"
//		+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber) + ","
//		+ "SYSDBA ," + "masterkey");
//return new JConnection(
//		"org.firebirdsql.jdbc.FBDriver",
//		"jdbc:firebirdsql:localhost/3050:"
//				+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber),
//		"SYSDBA", "masterkey");
//}
	/* --------------------------------------------------- */
	public static JConnection ConnectKikanDatabase(String KikanNumber)
	throws SQLException {

//		String url = "jdbc:firebirdsql:localhost/3050:"
//				+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber);
//
//		String user = "SYSDBA";
//		String password = "masterkey";
//		System.out.println("org.firebirdsql.jdbc.FBDriver : "
//		+ url + ","	+ user + "," + password);

		String driver = JDBC_DRIVER_NAME_FIREBIRD;
		String url =
			"jdbc:firebirdsql:" +
			JApplication.systemDBServer +
			"/" +
			JApplication.systemDBPort +
			":"
			+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber);

		String user = JApplication.systemDBUserName;
		String password = JApplication.systemDBPassword;

		JConnection connection = new JConnection(
				driver,
				url,
				user,
				password
				);

		return connection;
}
	/* --------------------------------------------------- */

	/**
	 * �V�X�e���f�[�^�x�[�X�ւ̐ڑ�
	 */
	/* Modified 2008/07/28 �ጎ  */
	/* --------------------------------------------------- */
//	public static JConnection ConnectSystemDatabase() throws SQLException {
//		return new JConnection("org.firebirdsql.jdbc.FBDriver",
//				"jdbc:firebirdsql:localhost/3050:"
//						+ JPath.GetSystemDatabaseFilePathToInitJSql(),
//				"SYSDBA", "masterkey");
//	}
	/* --------------------------------------------------- */
	public static JConnection ConnectSystemDatabase() throws SQLException {

		String driver = JDBC_DRIVER_NAME_FIREBIRD;

		String url =
			"jdbc:firebirdsql:" +
			JApplication.systemDBServer +
			"/" +
			JApplication.systemDBPort +
			":"
			+ JPath.GetSystemDatabaseFilePathToInitJSql();

		String user = JApplication.systemDBUserName;
		String password = JApplication.systemDBPassword;

		JConnection connection = new JConnection(
						driver,
						url,
						user,
						password);

		return connection;
	}
	/* --------------------------------------------------- */

	// add s.inoue 2012/07/04
	public static JConnection ConnectHokenjyaDatabase() throws SQLException {

		String driver = JDBC_DRIVER_NAME_FIREBIRD;

		String url =
			"jdbc:firebirdsql:" +
			JApplication.systemDBServer +
			"/" +
			JApplication.systemDBPort +
			":"
			+ JPath.GetHokenjyaDatabaseFilePathToInitJSql();

		String user = JApplication.systemDBUserName;
		String password = JApplication.systemDBPassword;

		JConnection connection = new JConnection(
						driver,
						url,
						user,
						password);

		return connection;
	}
	/**
	 * �ڑ����邱�Ƃ��ł���@�փf�[�^�x�[�X�����݂��邩�m�F����B
	 */
	public static boolean IsExistKikanDatabase() {
		File file = new File(JPath.DatabaseFolder);
		File[] files = file.listFiles();

		if (files == null) {
			return false;
		}

		if (files.length <= 1) {
			return false;
		}

		for (int i = 0; files.length > i; i++) {
			if (Pattern.matches("^\\d{10}.(fdb|FDB)", files[i].getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * �f�[�^�x�[�X��Ƀf�[�^�����݂��邩�ǂ����𒲂ׂ�
	 * @param Database �ڑ���̃f�[�^�x�[�X
	 * @param Query �N�G��
	 * @return ���݂����true
	 */
	public static boolean IsExistDatabaseItem(JConnection Database, String Query) {
		ArrayList<Hashtable<String, String>> Item;
		try {
			Item = Database.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		if (Item.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 2008/03/17 ���R�ǉ�
	// Connection�̎擾
	// -------------------------------------------------------------
	public Connection getMConnection() {
		return mConnection;
	}
	// -------------------------------------------------------------

}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

