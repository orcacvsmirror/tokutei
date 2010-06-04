package jp.or.med.orca.jma_tokutei.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.OutputStream;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.util.XMLDocumentUtil;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

//import com.lowagie.text.Document;
import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;

public class XMLDocumentUtil {
	private static final String ELEMENT_PROPERTIES = "properties";
	private static final String ELEMENT_PROPERTY = "property";
	private static final String ATTRIBUTE_ID = "id";
	
	private Element m_elementRoot = null; // ���[�g�v�f
	private String m_strXmlFileName = ""; // XML�t�@�C����

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
			throw err;
		}

		m_strXmlFileName = strPath;
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
	
	/** add s.inoue 20081205
	 *  �w��m�[�h�ǉ��ݒ�
	 */
	public void addNodeValue(XMLDocumentUtil xmlDoc,String properities, String properity)
			throws Exception {
		
		 try {
		        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilder�C���X�^���X
		        Document document =(Document)docbuilder.newDocument();		 // Document�̐���
		        
		        document= docbuilder.parse(PropertyUtil.getProperty("property.filename"));
		        
		         /*
		          * �m�[�h�𐶐����Aroot�̃m�[�h�̍ŏ��̎q�m�[�h�Ƃ��Ēǉ�
		          */
		         Element contents = document.createElement("property");
		         contents.setAttribute("id", "AbsolutePath");
		         
		         //org.w3c.dom.Text contents2 = document.createTextNode("\t"+ "\t");
		         
		         NodeList list = document.getElementsByTagName(ELEMENT_PROPERTIES);
		 		 Node n;
		 		 for (int i = 0; i < list.getLength(); i++) {
		 			n = list.item(i);
		 			NamedNodeMap map = n.getAttributes();
		 			
		 			if ("DBConfig".equals(map.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
		 				NodeList list2 = ((Element) n).getElementsByTagName(ELEMENT_PROPERTY);
		 				for (int j = 0; j < list2.getLength(); j++) {
		 					Node node = list2.item(j);
		 					NamedNodeMap map2 = node.getAttributes();
		 					if ("LoginTimeOut".equals(map2.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
		 						Node pnode= node.getFirstChild();
		 						
		 						Node rootNode =  pnode.getParentNode().getParentNode();
		 						rootNode.insertBefore(contents, node);
		 						break;
		 					}
		 				}
		 			}
		 		 }
		        
		         /*
		          * DOM�I�u�W�F�N�g��property�t�@�C���֏o��
		          */
		         TransformerFactory tfactory = TransformerFactory.newInstance(); 
		         Transformer transformer = tfactory.newTransformer(); 
		         
		         transformer.setOutputProperty(OutputKeys.INDENT , "yes");
		         transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT,"2");

		         String outfile = PropertyUtil.getProperty("property.filename");

		         DOMSource source = new DOMSource(document);
		 		 File Xml = new File(outfile);
		 		 FileOutputStream stream = new FileOutputStream(Xml);
		 		 StreamResult result = new StreamResult(stream);
		 		 transformer.transform(source, result);

		 		 stream.close();
		 				         
		      } catch (Exception err) {
		    	  throw err;
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
