package jp.or.med.orca.jma_tokutei.common.hl7;
import org.w3c.dom.*;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;


import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

import jp.or.med.orca.jma_tokutei.common.hl7.common.JBaseXmlWriter;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.hl7.index.Index;

public class IndexWriter implements JBaseXmlWriter {
	public void createXml(String FileName) throws Exception
	{
		if(check() == false)
		{
			throw new Exception();
		}
		DOMImplementation impl;
		Transformer transformer;
		
		// ����������
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
			impl = builder.getDOMImplementation();
		}
		catch(Exception e)
		{
			throw new RuntimeException();
		}
		
		// XML�쐬
		Document document = impl.createDocument("http://tokuteikenshin.jp/checkup/2007","index",null);
		Element root = document.getDocumentElement();
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation", "http://tokuteikenshin.jp/checkup/2007 ./XSD/ix08_V08.xsd");
		
		// �v�f�̒ǉ�
		index.GetElement(document,root);
		
		// XML�o��
		TransformerFactory TransFactory = TransformerFactory.newInstance();
		try
		{
			transformer = TransFactory.newTransformer();
		}
		catch(Exception e)
		{
			throw new RuntimeException();
		}
		transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		
		/* Added 2008/04/06 �ጎ  */
		/* --------------------------------------------------- */
		/* �C���f���g�̕��������w�肷��B */
		transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");		
		/* --------------------------------------------------- */
		
		DOMSource source = new DOMSource(document);
		File Xml = new File(FileName);
		FileOutputStream stream = new FileOutputStream(Xml);
		StreamResult result = new StreamResult(stream);
		transformer.transform(source, result);

		stream.close();
		
		// XML�X�L�[�}�̌���
		Utility.checkXmlSchema(FileName,"./XSD/ix08_V08.xsd"); 
	}
	
	public boolean check()
	{
		if(index == null)
		{
			return false;
		}
		
		if(index.Check() == false)
		{
			return false;
		}
		
		return true;
	}
	
	public void setIndex(Index Value)
	{
		index = Value;
	}
	private jp.or.med.orca.jma_tokutei.common.hl7.index.Index index = null;
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


