package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.onlineupdate.task.*;

// if you use java 5, there package don't use.
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;

/**
 *  XMLDocumentUtil
 *
 *    XML�h�L�������g�t�@�C������N���X
 *    
 *  Modified 2008/04/08 �ጎ
 *  �������萔��
 *  ���t�@�N�^�����O
 */
public class XMLDocumentUtil {
	
	private static final String ELEMENT_PROPERTIES = "properties";
	private static final String ELEMENT_PROPERTY = "property";
	private static final String ATTRIBUTE_ID = "id";
	private static final String ELEMENT_DATA_KIKAN_VERSION = "data_kikan_version";
	private static final String ELEMENT_DATA_URL = "data_url";
	private static final String ELEMENT_PARAM_QUERY = "param_query";
	private static final String ELEMENT_PRE_QUERY = "pre_query";
	// s.inoue 20080625 tag�ύX
	//private static final String ELEMENT_DATA_VERSION = "data_version";
	private static final String ELEMENT_DATA_VERSION = "data_system_version";
	private static final String ELEMENT_SCHEMA_KIKAN_VERSION = "schema_kikan_version";
	private static final String ELEMENT_QUERY = "query";
	private static final String ELEMENT_SCHEMA_SYSTEM_VERSION = "schema_system_version";
	private static final String ELEMENT_COPY_DIR = "copy_dir";
	private static final String ELEMENT_MODULE_URL = "module_url";
	private static final String ELEMENT_TASK = "task";
	private static final String ATTRIBUTE_NO = "no";
	private static final String ELEMENT_SYSTEM_VERSION = "system_version";	
	
	private Element m_elementRoot = null; // ���[�g�v�f
	private String m_strXmlFileName = ""; // XML�t�@�C����
	
	private static org.apache.log4j.Logger logger =
    	Logger.getLogger(XMLDocumentUtil.class);
	
	/**
	 *  �R���X�g���N�^
	 *  
	 *    @param  �t�@�C���p�X
	 *  
	 *    @return none
	 */
	public XMLDocumentUtil(String strPath) throws Exception {
		try {
			// �t�@�N�g������
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// �r���_�[����
			DocumentBuilder builder = factory.newDocumentBuilder();

			// ���[�g�v�f�擾
			m_elementRoot = builder.parse(strPath).getDocumentElement();
		} catch (Exception err) {
			LastError.setErrorMessage("\"" + strPath + "\"" + " �̓Ǎ��Ɏ��s���܂���");
			logger.error("\"" + strPath + "\"" + " �̓Ǎ��Ɏ��s���܂���");
			throw err;
		}

		m_strXmlFileName = strPath;
	}
	public String getXmlFileName(){
		return m_strXmlFileName;
	}
	
	
	/**
	 *  �A�b�v�f�[�g�^�X�N�����p�[�X
	 * 
	 *    @param  none
	 * 
	 *    @return �^�X�N���X�g
	 */
	public ArrayList<AbstractTask> parseModuleTask() throws Exception {
		ArrayList<AbstractTask> taskList = new ArrayList<AbstractTask>();

		try {
			
			/*
			 * �t�@�C���̍X�V���s�Ȃ��B 
			 */
			NodeList systemVersionElementList = m_elementRoot.getElementsByTagName(ELEMENT_SYSTEM_VERSION);

			for (int i = 0; i < systemVersionElementList.getLength(); i++) {
				ModuleCopyTask copyTask = new ModuleCopyTask();

				Element systemVersionElement = (Element) systemVersionElementList.item(i);

				copyTask.setVersion(systemVersionElement.getAttribute(ATTRIBUTE_NO));
				NodeList taskElementList = systemVersionElement.getElementsByTagName(ELEMENT_TASK);
				
				for (int j = 0; j < taskElementList.getLength(); j++) {
					Node taskElement = taskElementList.item(j);
					
					String url = this.getNodeValue(taskElement, ELEMENT_MODULE_URL);
					String copyDir = this.getNodeValue(taskElement, ELEMENT_COPY_DIR);
					
					copyTask.putTask(url, copyDir);					
				}

				taskList.add(copyTask);
			}
			
		} catch (Exception err) {
			throw err;
		}

		return taskList;
	}

	/**
	 *  �A�b�v�f�[�g�^�X�N�����p�[�X
	 *  �{��(���茒�N�f���\�t�g�E�F�A�A�Ǘ��p�\�t�g�E�F�A)����Ăяo���ꂽ�ꍇ
	 *    @param  none
	 *    @return �^�X�N���X�g
	 */
	public ArrayList<AbstractTask> parseDatabaseTask() throws Exception {
		ArrayList<AbstractTask> taskList = new ArrayList<AbstractTask>();

		try {
			
			/*
			 * System.fdb �ɑ΂��āASQL �����s����B
			 * 1 �^�X�N���Ƃ� commit ����B
			 */
			// Schema Task SYSTEM.FDB�p
			NodeList schemaSystemVersionElementList = m_elementRoot.getElementsByTagName(ELEMENT_SCHEMA_SYSTEM_VERSION);
			//			System.out.println(list.getLength());
			for (int i = 0; i < schemaSystemVersionElementList.getLength(); i++) {
				
				SystemSchemaChangeTask schemaChangeTask = new SystemSchemaChangeTask();

				Element systemVersionElement = (Element) schemaSystemVersionElementList.item(i);

				schemaChangeTask.setVersion(systemVersionElement.getAttribute(ATTRIBUTE_NO));
				NodeList taskElementList = systemVersionElement.getElementsByTagName(ELEMENT_TASK);
				
				for (int j = 0; j < taskElementList.getLength(); j++) {
					Node taskElement = taskElementList.item(j);
					String sql = this.getNodeValue(taskElement, ELEMENT_QUERY);
					
					schemaChangeTask.putTask(sql);
				}

				taskList.add(schemaChangeTask);
			}

			/*
			 * System.fdb �ȊO�� FDB �ɑ΂��āASQL �����s����B
			 * 1 �^�X�N���Ƃ� commit ����B
			 */
			/* 2007/03/09 yabu �X�L�[�}��FDB�ʂɔF�������邽�߂ɕύX */
			/* Schema Task�@KIKAN.FDB �܂��� �g�����U�N�V�����p.FDB */
			NodeList schemaKikanVersionElementList = m_elementRoot.getElementsByTagName(ELEMENT_SCHEMA_KIKAN_VERSION);
			//			System.out.println(list);
			for (int i = 0; i < schemaKikanVersionElementList.getLength(); i++) {
				KikanSchemaChangeTask schemaTask = new KikanSchemaChangeTask();

				Element kikanVersionElement = (Element) schemaKikanVersionElementList.item(i);

				schemaTask.setVersion(kikanVersionElement.getAttribute(ATTRIBUTE_NO));

				NodeList nodelist = kikanVersionElement.getElementsByTagName(ELEMENT_TASK);
				for (int b = 0; b < nodelist.getLength(); b++) {
					Node n = nodelist.item(b);
					String sql = getNodeValue(n, ELEMENT_QUERY);
					schemaTask.putTask(sql);					
				}

				taskList.add(schemaTask);
			}

			/*
			 * System.fdb �̃f�[�^��ύX����B 
			 */
			NodeList dataVersionElementList = m_elementRoot.getElementsByTagName(ELEMENT_DATA_VERSION);
			for (int i = 0; i < dataVersionElementList.getLength(); i++) {
				SystemDataUpdateTask dataTask = new SystemDataUpdateTask();

				Element el = (Element) dataVersionElementList.item(i);

				dataTask.setVersion(el.getAttribute(ATTRIBUTE_NO));

				NodeList nodelist = el.getElementsByTagName(ELEMENT_TASK);
				for (int j = 0; j < nodelist.getLength(); j++) {
					Node n = nodelist.item(j);
					
					String preQuery = getNodeValue(n, ELEMENT_PRE_QUERY);
					String query = getNodeValue(n, ELEMENT_PARAM_QUERY);
					String dataUrl = getNodeValue(n, ELEMENT_DATA_URL);
					
					dataTask.putTask(preQuery, query, dataUrl);					
				}

				taskList.add(dataTask);
			}

			/*
			 * System.fdb �ȊO��FDB �̃f�[�^��ύX����B 
			 */
			NodeList dataKikanVersionElementList = m_elementRoot.getElementsByTagName(ELEMENT_DATA_KIKAN_VERSION);
			//System.out.println(list.getLength());			
			for (int i = 0; i < dataKikanVersionElementList.getLength(); i++) {
				KikanDataUpdateTask dataTask = new KikanDataUpdateTask();

				Element el = (Element) dataKikanVersionElementList.item(i);

				dataTask.setVersion(el.getAttribute(ATTRIBUTE_NO));

				NodeList nodelist = el.getElementsByTagName(ELEMENT_TASK);
				for (int j = 0; j < nodelist.getLength(); j++) {
					Node n = nodelist.item(j);
					
					String preQuery = getNodeValue(n, ELEMENT_PRE_QUERY);
					String query = getNodeValue(n, ELEMENT_PARAM_QUERY);
					String dataUrl = getNodeValue(n, ELEMENT_DATA_URL);
					
					dataTask.putTask(preQuery, query, dataUrl);					
				}

				taskList.add(dataTask);
			}

		} catch (Exception err) {
			throw err;
		}

		return taskList;
	}	
	
	/**
	 *  �捞�h�L�������g�ɑ΂��� XPath ���s
	 * 
	 *    @param  ���s�p�X
	 *    
	 *    @return �ԋp�l
	 */
	public String getNodeValue(String xpath) throws Exception {
		return getNodeValue(m_elementRoot, xpath);
	}

	/**
	 *  �w��m�[�h�l�̎擾
	 *
	 *    @param  ���s�m�[�h
	 *    @param  ���s�p�X
	 *
	 *    @return �ԋp�l
	 */
	private String getNodeValue(Node n, String xpath) throws Exception {
		String result = "";

		int index = xpath.indexOf("/");

		if (index != -1) {
			NodeList nodelist = ((Element) n).getElementsByTagName(xpath.substring(0, index));
			return getNodeValue(nodelist.item(0), xpath.substring(index + 1));
		}

		NodeList nodelist = ((Element) n).getElementsByTagName(xpath);
		Node node;

		for (int i = 0; i < nodelist.getLength(); i++) {
			node = nodelist.item(i);
			if (node.getFirstChild() != null) {
				result = node.getFirstChild().getNodeValue();
			}
		}

		return result;
	}

	/**
	 *  �w��m�[�h�l�擾
	 */
	public String getNodeValue(String properities, String properity)
			throws Exception {
		String result = "";
		Node node = getNode(properities, properity);

		if (node != null) {
			result = node.getNodeValue();
		}

		return result;
	}

	/**
	 *  �w��m�[�h�l�ݒ�
	 */
	public void setNodeValue(String properities, String properity, String value)
			throws Exception {
		Node node = getNode(properities, properity);

		if (node != null) {
			node.setNodeValue(value);
		}
	}

	/**
	 *  �ݒ�t�@�C�����m�[�h�l���擾
	 */
	private Node getNode(String properties, String property) {
		NodeList list = m_elementRoot.getElementsByTagName(ELEMENT_PROPERTIES);
		Node n;
		for (int i = 0; i < list.getLength(); i++) {
			n = list.item(i);
			NamedNodeMap map = n.getAttributes();
			if (properties.equals(map.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
				NodeList list2 = ((Element) n).getElementsByTagName(ELEMENT_PROPERTY);
				for (int j = 0; j < list2.getLength(); j++) {
					Node node = list2.item(j);
					NamedNodeMap map2 = node.getAttributes();
					if (property.equals(map2.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
						return node.getFirstChild();
					}
				}
			}
		}
		return null;
	}

	/**
	 *  �t�@�C���ۑ�
	 */
	public void store() throws Exception {
		store(m_strXmlFileName);
	}

	/**
	 *  �t�@�C���ۑ�
	 */
	public void store(String fileName) throws Exception {
		File newXML = new File(fileName);
		Transformer tm;
		try {
			tm = TransformerFactory.newInstance().newTransformer();
			tm.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

			/* Added 2008/04/08 �ጎ  */
			/* --------------------------------------------------- */
			/* �C���f���g�̕��������w�肷��B */
			tm.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");		
			/* --------------------------------------------------- */
			
			tm.transform(new DOMSource(m_elementRoot), new StreamResult(
					new FileOutputStream(newXML)));
		} catch (Exception err) {
			throw err;
		}

		newXML = null;
	}
}
