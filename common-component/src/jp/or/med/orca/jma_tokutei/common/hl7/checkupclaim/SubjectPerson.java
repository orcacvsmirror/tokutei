package jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SubjectPerson {
	public Element GetElement(Document doc)
	{
		Element element = doc.createElement("subjectPerson");

		Element element_performer = doc.createElement("performerOrganization");
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("���{�@�֔ԍ�"));

		Element element_id = doc.createElement("id");
		element_id.setAttribute("root", "1.2.392.200119.6.102");
		element_id.setAttribute("extension", OrganizationId);
		element_performer.appendChild(element_id);

		element.appendChild(element_performer);

		Element element_card = doc.createElement("insuranceCard");
		// add s.inoue 2010/01/18
		element_card.appendChild(doc.createComment("�ی��Ҕԍ�"));
		Element element_insure = doc.createElement("insurerNumber");
		element_insure.setAttribute("root", "1.2.392.200119.6.101");
		element_insure.setAttribute("extension", InsureNumber);
		element_card.appendChild(element_insure);

		// ��ی��ҏؓ��L�����K�{�ł͂Ȃ��Ȃ����̂ŁA����̑Ή��B
		/* Modified 2008/03/31 �ጎ  */
		/* --------------------------------------------------- */
//		if(InsurerCardSymbol != null)
//		{
//			Element element_symbol = doc.createElement("symbol");
//			element_symbol.setAttribute("root", "1.2.392.200119.6.204");
//			element_symbol.setAttribute("extension", InsurerCardSymbol);
//			element_card.appendChild(element_symbol);
//		}
		/* --------------------------------------------------- */
		if(InsurerCardSymbol != null && ! InsurerCardSymbol.isEmpty())
		{
			// add s.inoue 2010/01/18
			element_card.appendChild(doc.createComment("��ی��ҏؓ��L��"));
			Element element_symbol = doc.createElement("symbol");
			element_symbol.setAttribute("root", "1.2.392.200119.6.204");
			element_symbol.setAttribute("extension", InsurerCardSymbol);
			element_card.appendChild(element_symbol);
		}
		/* --------------------------------------------------- */

		/* Modified 2008/03/31 �ጎ  */
		/* --------------------------------------------------- */
//		Element element_number = doc.createElement("number");
//		element_number.setAttribute("root", "1.2.392.200119.6.205");
//		element_number.setAttribute("extension", InsurerCardNumber);
//		element_card.appendChild(element_number);
		/* --------------------------------------------------- */
		if (InsurerCardNumber != null && ! InsurerCardNumber.isEmpty()) {
			// add s.inoue 2010/01/18
			element_card.appendChild(doc.createComment("��ی��ҏؓ��ԍ�"));
			Element element_number = doc.createElement("number");
			element_number.setAttribute("root", "1.2.392.200119.6.205");
			element_number.setAttribute("extension", InsurerCardNumber);
			element_card.appendChild(element_number);
		}
		/* --------------------------------------------------- */

		element.appendChild(element_card);

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("����"));
		Element element_name = doc.createElement("name");

		/* Modified 2008/06/08 �ጎ  */
		/* --------------------------------------------------- */
//		element_name.setTextContent(Name);
		/* --------------------------------------------------- */
		/* �󔒕������폜����B */
		String name = Name.replaceAll("[ �@\\t]", "");
		element_name.setTextContent(name);
		/* --------------------------------------------------- */

		element.appendChild(element_name);
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("�Z���ƗX�֔ԍ�"));
		Element element_addr = doc.createElement("addr");

		/* Modified 2008/06/08 �ጎ  */
		/* --------------------------------------------------- */
//		element_addr.setTextContent(Address);
		/* --------------------------------------------------- */
		/* �󔒕������폜����B */
		String address = Address.replaceAll("[ �@\\t]", "");
		element_addr.setTextContent(address);
		/* --------------------------------------------------- */

		Element element_postal = doc.createElement("postalCode");
		element_postal.setTextContent(PostalCode);
		element_addr.appendChild(element_postal);
		element.appendChild(element_addr);

		Element element_birth = doc.createElement("birthTime");
		element_birth.setAttribute("value", BirthTime);
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("���N����"));
		element.appendChild(element_birth);

		Element element_gender = doc.createElement("administrativeGender");
		element_gender.setAttribute("code", Gender);
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("����"));
		element.appendChild(element_gender);

		return element;
	}

	public boolean Check()
	{
		if(
				OrganizationId == null ||
				InsureNumber == null ||
				InsurerCardNumber == null ||
				Name == null ||
				Address == null ||
				PostalCode == null ||
				BirthTime == null ||
				Gender == null
		)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value �@�֔ԍ�
	 */
	public void setOrganizationId(long Value)
	{
		OrganizationId = Utility.FillZero(Value, 10);
	}
	public void setOrganizationId(String Value)
	{
		OrganizationId = Value;
	}
	private String OrganizationId = null;

	/**
	 * @param Value �ی��Ҕԍ�
	 */
	public void setInsureNumber(long Value)
	{
		InsureNumber = Utility.FillZero(Value, 8);
	}
	public void setInsureNumber(String Value)
	{
		InsureNumber = Value;
	}
	private String InsureNumber = null;

	/**
	 * @param Value ��ی��ҏؓ��L��
	 */
	public void setInsurerCardSymbol(String Value)
	{
		InsurerCardSymbol = Value;
	}
	private String InsurerCardSymbol = null;

	/**
	 * @param Value ��ی��ҏؓ��ԍ�
	 */
	public void setInsurerCardNumber(String Value)
	{
		InsurerCardNumber = Value;
	}
	private String InsurerCardNumber = null;

	/**
	 * @param Value ���O
	 */
	public void setName(String Value)
	{
		Name = Value;
	}
	private String Name = null;

	/**
	 * @param Value �Z��
	 */
	public void setAddress(String Value)
	{
		Address = Value;
	}
	private String Address = null;

	/**
	 * @param Value �X�֔ԍ�
	 */
	public void setPostalCode(String Value)
	{
		PostalCode = Utility.ConvertPostalCode(Value);
	}
	private String PostalCode = null;

	/**
	 * @param Value ���N����
	 */
	public void setBirthTime(String Value)
	{
		BirthTime = Value;
	}
	private String BirthTime = null;

	/**
	 * @param Value ����
	 */
	public void setGender(String Value)
	{
		Gender = Value;
	}
	private String Gender = null;
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

