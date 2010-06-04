package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import java.util.*;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

public class RecordTarget {
	public Element getElement(Document doc)
	{
		// �ݒ�̃`�F�b�N
		if(Check() == false)
		{
			return null;
		}

		Element element = doc.createElement("recordTarget");

		Element element_role = doc.createElement("patientRole");

		// add s.inoue 2009/12/29
		element_role.appendChild(doc.createComment("�ی��Ҕԍ�"));
		Element element_id = doc.createElement("id");
		element_id.setAttribute("extension", HokenjyaNumberExtension);
		element_id.setAttribute("root", "1.2.392.200119.6.101");
		element_role.appendChild(element_id);

		if(HokensyoCodeExtension != null && ! HokensyoCodeExtension.isEmpty())
		{
			// add s.inoue 2009/12/29
			element_role.appendChild(doc.createComment("��ی��ҏؓ��L��"));
			Element element_id2 = doc.createElement("id");
			element_id2.setAttribute("extension", HokensyoCodeExtension);
			element_id2.setAttribute("root", "1.2.392.200119.6.204");
			element_role.appendChild(element_id2);
		}

		// add s.inoue 2009/12/29
		element_role.appendChild(doc.createComment("��ی��ҏؓ��ԍ�"));
		Element element_id3 = doc.createElement("id");
		element_id3.setAttribute("extension", HokensyoNumberExtension);
		element_id3.setAttribute("root", "1.2.392.200119.6.205");
		element_role.appendChild(element_id3);

		Element element_addr = doc.createElement("addr");

		/* �󔒕������폜����B */
		// add s.inoue 2009/12/29
		element_role.appendChild(doc.createComment("�Z���A�X�֔ԍ�"));
		String address = Address.replaceAll("[ �@\\t]", "");
		element_addr.setTextContent(address);

		Element element_addr_postalCode = doc.createElement("postalCode");
		element_addr_postalCode.setTextContent(PostalCode);
		element_addr.appendChild(element_addr_postalCode);
		element_role.appendChild(element_addr);


		Element element_patient = doc.createElement("patient");
		Element element_patient_name = doc.createElement("name");

		/* �󔒕������폜����B */
		// add s.inoue 2009/12/29
		element_patient.appendChild(doc.createComment("����"));
		String name = Name.replaceAll("[ �@\\t]", "");
		element_patient_name.setTextContent(name);
		element_patient.appendChild(element_patient_name);

		// add s.inoue 2009/12/29
		element_patient.appendChild(doc.createComment("����"));
		Element element_patient_gender = doc.createElement("administrativeGenderCode");
		element_patient_gender.setAttribute("code", GenderCode);
		element_patient_gender.setAttribute("codeSystem", "1.2.392.200119.6.1104");
		element_patient.appendChild(element_patient_gender);

		// add s.inoue 2009/12/29
		element_patient.appendChild(doc.createComment("���N����"));
		Element element_patient_birthTime = doc.createElement("birthTime");
		element_patient_birthTime.setAttribute("value", BirthTime);
		element_patient.appendChild(element_patient_birthTime);

		element_role.appendChild(element_patient);

		element.appendChild(element_role);

		return element;
	}

	/**
	 * @return ���ׂĂ̍��ڂ��ݒ肳��Ă��邩�`�F�b�N
	 */
	public boolean Check()
	{
		if(HokenjyaNumberExtension == null)
		{
			return false;
		}

		if(HokensyoNumberExtension == null)
		{
			return false;
		}

		if(Address == null)
		{
			return false;
		}

		if(PostalCode == null)
		{
			return false;
		}

		if(Name == null)
		{
			return false;
		}

		if(GenderCode == null)
		{
			return false;
		}

		if(BirthTime == null)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value �ی��Ҕԍ�
	 */
	public void setHokenjyaNumber(long Value)
	{
		HokenjyaNumberExtension = Utility.FillZero(Value, 8);
	}
	public void setHokenjyaNumber(String Value)
	{
		HokenjyaNumberExtension = Value;
	}
	private String HokenjyaNumberExtension = null;

	/**
	 * @param Value ��ی��ҏؓ��L��
	 */
	public void setHokensyoCode(String Value)
	{
		HokensyoCodeExtension = Value;
	}
	private String HokensyoCodeExtension = null;

	/**
	 * @param Value ��ی��ҏؓ��ԍ�
	 */
	public void setHokensyoNumber(String Value)
	{
		HokensyoNumberExtension = Value;
	}
	private String HokensyoNumberExtension = null;

	/**
	 * @param Value ��f�ҏZ��
	 */
	public void setAddress(String Value)
	{
		Address = Value;
	}
	private String Address = null;

	/**
	 * @param Value ��f�җX�֔ԍ�
	 */
	public void setPostalCode(String Value)
	{
		PostalCode = Utility.ConvertPostalCode(Value);
	}
	private String PostalCode = null;


	/**
	 * @param Value ��f�҂̎���
	 */
	public void setName(String Value)
	{
		Name = Value;
	}
	private String Name = null;

	/**
	 * @param Value ��f�Ґ��ʂ̃R�[�h
	 */
	public void setGender(String Value)
	{
		GenderCode = Value;
	}
	private String GenderCode = null;

	/**
	 * @param Value ��f�҂̐��N�����iCalendar�N���X�̌�������1���������́A�����ŏC���j
	 */
	public void setBirthTime(Calendar Value)
	{
		BirthTime = Utility.GetTimeString(Value);
	}
	public void setBirthTime(String Value)
	{
		BirthTime = Value;
	}
	private String BirthTime = null;
}
