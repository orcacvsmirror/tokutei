package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.io.File;
import java.sql.SQLException;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.common.util.XMLDocumentUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

import org.apache.log4j.Logger;

/**
 * DB���ڑ���ʂ̃r�W�l�X���W�b�N�N���X
 */
public class JNetworkDBConnectionFrameCtrl {
	private static Logger logger = Logger.getLogger(JNetworkDBConnectionFrameCtrl.class);
	
	/**
	 * �R���X�g���N�^
	 */
	public JNetworkDBConnectionFrameCtrl() {
	}

	
	/**
	 * ��ʕ\���p�̃f�[�^���擾����
	 * 
	 * @return
	 */
	public JNetworkDBConnectionFrameData getFrameData() {
		
		JNetworkDBConnectionFrameData bean = new JNetworkDBConnectionFrameData();
		
		//property.xml�̓Ǎ�
		getPropertyData(bean);
		
		return bean;
	}
	
	/**
	 * property.xml�������ǂݍ��݁ABean�֐ݒ肷��
	 * 
	 * @param bean
	 */
	private void getPropertyData(JNetworkDBConnectionFrameData bean) {
		try {
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			
			String userName = doc.getNodeValue("DBConfig", "UserName");
			String password = doc.getNodeValue("DBConfig", "Password");
			String absolutePath = doc.getNodeValue("DBConfig", "AbsolutePath");
			String server = doc.getNodeValue("DBConfig", "Server");
			String port = doc.getNodeValue("DBConfig", "Port");
//			System.out.println("property.xml���@UserName:[" + userName + "] Password:[" + password + "] AbsolutePath:[" + absolutePath + "] Server:[" + server + "] Port:[" + port + "]");
			
			bean.setFdbUserName(userName);
			bean.setFdbPass(password);
			bean.setFdbFolderPath(isEmpty(absolutePath) ? "C:/NITTOKU/DB/" : absolutePath);	//���ݒ�̏ꍇ�͏����l��ݒ�
			bean.setFdbIPAddress(server);
			bean.setFdbPortNumber(port);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("property.xml�̓Ǎ��Ɏ��s");
			JErrorMessage.show("M10103", null);
			throw new RuntimeException("property.xml�̓Ǎ��Ɏ��s", ex);
		}
	}

	
	/**
	 * �o�^�{�^����������
	 * 
	 * @param bean	��ʏ��
	 * @return	�o�^�����Ftrue�A�o�^�������i���̓`�F�b�N�ŃG���[�Ƃ��j�Ffalse
	 */
	public boolean register(JNetworkDBConnectionFrameData bean) {
//		System.out.println("register:[" + bean.toString() + "]");
		
		//�߂�l�p
		boolean isResutl;
		
		try {
			//���̓`�F�b�N�iFDB�֘A�j
			isResutl = validateFDB(bean);
			
			//���̓`�F�b�N��OK�̏ꍇ
			if (isResutl) {
				
				//�w�肳�ꂽFDB�֐ڑ��ł��邩�m�F
				boolean isConnectOK = connectTestFDB(bean.getFdbUserName(), bean.getFdbPass(), bean.getFdbFolderPath(), bean.getFdbIPAddress(), bean.getFdbPortNumber());
				
				//�ڑ�OK
				if (isConnectOK) {
					
					//FDB�̐ڑ����iproperty.xml�j���X�V
					updatePropertyFile(bean);
					
					//�V����FDB���ŁA�V�X�e���f�[�^�x�[�X���Đڑ�����
					reConnectSystemDatabase();
					
				//�ڑ�NG
				} else {
					isResutl = false;
					JErrorMessage.show("M10102", null);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			isResutl = false;
		}
		return isResutl;
	}

	/**
	 * �ڑ���DB��񍀖ڂ̓��̓`�F�b�N
	 * 
	 * @param bean	��ʏ��
	 * @return	�S���ړ��̓`�F�b�NOK�Ftrue�A��ł����̓`�F�b�NNG�L��Ffalse
	 */
	private boolean validateFDB(JNetworkDBConnectionFrameData bean) {

		//�߂�l�p
		boolean isResutl = true;
		
		//�t�H���_�p�X
		String folderPath = getFolderPath(bean.getFdbFolderPath());
		if (isEmpty(folderPath) || isEmpty(JValidate.validateFilePath(folderPath))) {
			JErrorMessage.show("M10108", null);
			isResutl = false;
		}
		if (isResutl) {
			//�z�X�g�� or IP�A�h���X
			String fdbIPAddress = bean.getFdbIPAddress();
			if (isEmpty(fdbIPAddress) || isEmpty(JValidate.validateIPAddress(fdbIPAddress))) {
				JErrorMessage.show("M10109", null);
				isResutl = false;
			}
		}
		if (isResutl) {
			//�|�[�g�ԍ�
			String portNumber = bean.getFdbPortNumber();
			if (isEmpty(portNumber) || isEmpty(JValidate.validatePortNumber(portNumber))) {
				JErrorMessage.show("M10110", null);
				isResutl = false;
			}
		}
		if (isResutl) {
			//���[�UID
			String userName = bean.getFdbUserName();
			if (isEmpty(userName) || isEmpty(JValidate.validateORCANichireseUserName(userName))) {
				JErrorMessage.show("M10111", null);
				isResutl = false;
			}
		}
		if (isResutl) {
			//�p�X���[�h
			String pass = bean.getFdbPass();
			if (isEmpty(pass) || isEmpty(JValidate.validateORCANichiresePassword(pass))) {
				JErrorMessage.show("M10112", null);
				isResutl = false;
			}
		}
		return isResutl;
	}
	

	/**
	 * Bean�������ǂݍ��݁Aproperty.xml�t�@�C���iFDB�̐ڑ����j���X�V����
	 * 
	 * @param bean
	 */
	private void updatePropertyFile(JNetworkDBConnectionFrameData bean) {
		
		try {
			String userName = bean.getFdbUserName();
			String password = bean.getFdbPass();
			String absolutePath = getFolderPath(bean.getFdbFolderPath());
			String server = bean.getFdbIPAddress();
			String port = bean.getFdbPortNumber();
//			System.out.println("Bean���@UserName:[" + userName + "] Password:[" + password + "] AbsolutePath:[" + absolutePath + "] Server:[" + server + "] Port:[" + port + "]");

			//�l��ݒ�
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			doc.setNodeValueEmptyTag("DBConfig", "UserName", userName);
			doc.setNodeValueEmptyTag("DBConfig", "Password", password);
			doc.setNodeValueEmptyTag("DBConfig", "AbsolutePath", absolutePath);
			doc.setNodeValueEmptyTag("DBConfig", "Server", server);
			doc.setNodeValueEmptyTag("DBConfig", "Port", port);
			
			//�t�@�C���ۑ�
			doc.store();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("property.xml�̏������݂Ɏ��s");
			JErrorMessage.show("M10104", null);
			throw new RuntimeException("property.xml�̏������݂Ɏ��s", ex);
		}
	}
	
	/**
	 * property.xml�̓��e�ŁA�V�X�e���f�[�^�x�[�X���Đڑ�����
	 * ��JApplication.java��load���\�b�h�̈ꕔ���R�s�[���C��
	 */
	private void reConnectSystemDatabase() {
		
		XMLDocumentUtil doc;
		try {
			doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			JApplication.systemDBUserName = doc.getNodeValue("DBConfig", "UserName");
			JApplication.systemDBPassword = doc.getNodeValue("DBConfig", "Password");

			//AbsolutePath�����΃p�X���擾
			String path = doc.getNodeValue("DBConfig", "AbsolutePath");
			
			//��΃p�X���ݒ肳��Ă��Ȃ��ꍇ
			if (path == null ||  path.isEmpty()) {
			
				//�f�t�H���g�̃p�X���擾
				path = doc.getNodeValue("DBConfig", "Path");
			
				//�ݒ肪�Ȃ��ꍇ�́A�f�t�H���g�̏ꏊ�i�J�����g�f�B���N�g��/DB�j���g�p����
				if (path == null || path.isEmpty()) {
			
					StringBuilder sb = new StringBuilder();
					sb.append(getFolderPath(JPath.CURRENT_DIR_PATH));
					sb.append(getFolderPath("DB"));
					path = sb.toString();
					
				//�ݒ肪����ꍇ�́A�J�����g�f�B���N�g������̑��΃p�X�Ƃ���
				} else {
					File relativePath = new File(path);
					path = getFolderPath(relativePath.getCanonicalPath());
				}
			}
			
			JApplication.systemDBPath = path;
			JApplication.systemDBServer = doc.getNodeValue("DBConfig", "Server");
			JApplication.systemDBPort = doc.getNodeValue("DBConfig", "Port");
			
			//�V�X�e���f�[�^�x�[�X���Đڑ�
			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error(sqlEx.getMessage(), sqlEx);
			JErrorMessage.show("M10105", null);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			JErrorMessage.show("M10105", null);
		}
	}
	
	
	/**
	 * �ڑ���DB�Ɛڑ��e�X�g���s��
	 * 
	 * @param fdbUserName
	 * @param fdbUserPass
	 * @param fdbFolderPath
	 * @param fdbIPAddress
	 * @param fdbPortNumber
	 * @return	�ڑ�OK�Ftrue�A�ڑ�NG�Ffalse
	 */
	public boolean connectTestFDB(String fdbUserName, String fdbUserPass, String fdbFolderPath, String fdbIPAddress, String fdbPortNumber) {
		
		//�߂�l�p
		boolean isResutl = true;
		
		try {
			StringBuilder url = new StringBuilder();
			url.append("jdbc:firebirdsql:");
			url.append(fdbIPAddress);
			url.append("/");
			url.append(fdbPortNumber);
			url.append(":");
			url.append(getFolderPath(fdbFolderPath));
			url.append("System");
			url.append(JPath.DATA_BASE_EXTENSION);
			
			//�ڑ��ł��邩�m�F
			JConnection connection = new JConnection("org.firebirdsql.jdbc.FBDriver", url.toString(), fdbUserName, fdbUserPass);
			connection.Transaction();
			connection.rollback();
			connection.Shutdown();
			
		} catch(SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error(sqlEx.getMessage());
			isResutl = false;
		}
		return isResutl;
	}
	
	/**
	 * �p�X������̍Ōオ�t�H���_��؂蕶���Ŗ����ꍇ�A��؂蕶����t������
	 * ���p�X�����񂪃u�����N�̏ꍇ�́A���̂܂܂̕������ԋp����
	 * ���t�������؂蕶����́u/�v�iUnix�n�̋�؂蕶���񂾂��AWindows�ł����v�̂͂��j�ŁAFile.separator�Ƃ��ɂ���ƁA�T�[�o��Unix�n�����[�J����Windows���ƃG���[�ɂȂ�
	 * 
	 * @param path	�p�X������
	 * @return	��؂蕶����t�������p�X������
	 */
	private String getFolderPath(String path) {
//		System.out.println("getFolderPath path:[" + path + "]");
		
		String result = path;
		if (!isEmpty(path)) {
			if (!path.endsWith("\\") && !path.endsWith("/")) {
				result = path.concat("/");
//				System.out.println("�t��  result:[" + result + "]");
			}
		}
		return result;
	}
	
	/**
	 * �󕶎���null�`�F�b�N
	 * 
	 * @param str
	 * @return		�󕶎� or Null�̏ꍇ�Ftrue�A��������l���L��ꍇ�Ffalse
	 */
	private boolean isEmpty(String str) {
		return (str != null) ? str.isEmpty() : true;
	}
}
