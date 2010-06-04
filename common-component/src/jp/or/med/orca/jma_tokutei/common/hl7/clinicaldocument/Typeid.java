package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Typeid {
	private Typeid()
	{
		// �Œ�l�݂̂Ȃ̂ŁA�C���X�^���X�����s�v�B
	}
	
	public static Element getElement(Document doc)
	{
		Element element = doc.createElement("typeId");
		
		element.setAttribute("root", "2.16.840.1.113883.1.3");
		element.setAttribute("extension", "POCD_HD000040");
		
		return element;
	}
}
