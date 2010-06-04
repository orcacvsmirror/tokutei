package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class RepresentedOrganization {
	public Element getElement(Document doc)
	{
		// �ݒ�̃`�F�b�N
		if(Check() == false)
		{
			return null;
		}

		Element element_representedOrg = doc.createElement("representedOrganization");

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("�t�@�C���쐬�@�֔ԍ�"));
		Element element_id2 = doc.createElement("id");
		element_id2.setAttribute("extension", NumberExtension);
		element_id2.setAttribute("root", "1.2.392.200119.6.102");
		element_representedOrg.appendChild(element_id2);

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("�t�@�C���쐬�@�֖���"));
		Element element_name = doc.createElement("name");
		element_name.setTextContent(Name);
		element_representedOrg.appendChild(element_name);

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("�t�@�C���쐬�@�֓d�b�ԍ�"));
		Element element_telecom = doc.createElement("telecom");
		element_telecom.setAttribute("value", Telecom);
		element_representedOrg.appendChild(element_telecom);

		Element element_addr = doc.createElement("addr");

		/* �󔒕������폜����B */
		String address = Address.replaceAll("[ �@\\t]", "");
		element_addr.setTextContent(address);

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("�t�@�C���쐬�@�֏Z���A�X�֔ԍ�"));
		Element element_postalCode = doc.createElement("postalCode");
		element_postalCode.setTextContent(PostalCode);
		element_addr.appendChild(element_postalCode);

		element_representedOrg.appendChild(element_addr);

		return element_representedOrg;
	}

	public boolean Check()
	{
		if(
				NumberExtension == null ||
				Name == null ||
				Telecom == null ||
				Address == null ||
				PostalCode == null
		)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value �@�ւ̔ԍ�
	 */
	public void setNumber(long Value)
	{
		NumberExtension = Utility.FillZero(Value, 10);
	}
	public void setNumber(String Value)
	{
		NumberExtension = Value;
	}
	private String NumberExtension = null;

	/**
	 * @param Value �@�ւ̖���
	 */
	public void setName(String Value)
	{
		Name = Value;
	}
	private String Name = null;

	/**
	 * @param Value �@�ւ̘A������
	 */
	public void setTel(String Value)
	{
		Telecom = "tel:" + Value;
	}
	private String Telecom = null;

	/**
	 * @param Value �@�ւ̏��ݒn���
	 */
	public void setAddress(String Value)
	{
		Address = Value;
	}
	private String Address = null;

	/**
	 * @param Value �@�ւ̗X�֔ԍ����
	 */
	public void setPostalCode(String Value)
	{
		Value = Utility.ConvertPostalCode(Value);

		PostalCode = Value;
	}
	private String PostalCode = null;
}
