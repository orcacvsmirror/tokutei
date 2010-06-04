package jp.or.med.orca.jma_tokutei.common.hl7.summary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Summary {
	public Element getElement(Document doc,Element element)
	{
		Element element_service = doc.createElement("serviceEventType");
		element_service.setAttribute("code", ServiceEventType);
		element.appendChild(element_service);

		Element element_subject = doc.createElement("totalSubjectCount");
		element_subject.setAttribute("value", TotalSubjectCount);
		element.appendChild(element_subject);
		
		Element element_cost = doc.createElement("totalCostAmount");
		element_cost.setAttribute("value", TotalCostAmount);
		element.appendChild(element_cost);
		
		Element element_payment = doc.createElement("totalPaymentAmount");
		element_payment.setAttribute("value", TotalPaymentAmount);
		element.appendChild(element_payment);
		
		Element element_other_payment = doc.createElement("totalPaymentByOtherProgram");
		element_other_payment.setAttribute("value", TotalPaymentByOtherProgram);
		element.appendChild(element_other_payment);
		
		Element element_claim = doc.createElement("totalClaimAmount");
		element_claim.setAttribute("value", TotalClaimAmount);
		element.appendChild(element_claim);
		
		return element;
	}
	
	public boolean Check()
	{
		if(
				ServiceEventType == null ||
				TotalSubjectCount == null ||
				TotalCostAmount == null ||
				TotalPaymentAmount == null ||
				TotalPaymentByOtherProgram == null ||
				TotalClaimAmount == null
		)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * @param Value ���{�敪
	 */
	public void setServiceEventType(int Value)
	{
		if(1 <= Value && Value <= 4)
		{
			ServiceEventType = String.valueOf(Value);
		}
	}
	public void setServiceEventType(String Value)
	{
		ServiceEventType = Value;
	}
	private String ServiceEventType = null;
	
	/**
	 * @param Value �A�[�J�C�u�Ɋ܂܂���f�ҁE���p�҂̑���
	 */
	public void setTotalSubjectCount(long Value)
	{
		TotalSubjectCount = String.valueOf(Value);
	}
	public void setTotalSubjectCount(String Value)
	{
		TotalSubjectCount = Value;
	}
	private String TotalSubjectCount = null;
	
	/**
	 * @param Value
	 * ���茒�f�F�A�[�J�C�u�Ɋ܂܂��P���̋��z���v
	 * �ی��w���F�A�[�J�C�u�Ɋ܂܂��Z����z�̑��v
	 */
	public void setTotalCostAmount(long Value)
	{
		TotalCostAmount = String.valueOf(Value);
	}
	public void setTotalCostAmount(String Value)
	{
		TotalCostAmount = Value;
	}
	private String TotalCostAmount = null;
	
	/**
	 * @param Value �A�[�J�C�u�Ɋ܂܂�鑋���x�����z�̏W�v
	 */
	public void setTotalPaymentAmount(long Value)
	{
		TotalPaymentAmount = String.valueOf(Value);
	}
	public void setTotalPaymentAmount(String Value)
	{
		TotalPaymentAmount = Value;
	}
	private String TotalPaymentAmount = null;
	
	/**
	 * @param Value �������{�������̌��f���ŕ��S������z�̏W�v
	 */
	public void setTotalPaymentByOtherProgram(long Value)
	{
		TotalPaymentByOtherProgram = String.valueOf(Value);
	}
	public void setTotalPaymentByOtherProgram(String Value)
	{
		TotalPaymentByOtherProgram = Value;
	}
	private String TotalPaymentByOtherProgram = null;
	
	/**
	 * @param Value �A�[�J�C�u�Ɋ܂܂��ی��҂ւ̐������z�̏W�v
	 */
	public void setTotalClaimAmount(long Value)
	{
		TotalClaimAmount = String.valueOf(Value);
	}
	public void setTotalClaimAmount(String Value)
	{
		TotalClaimAmount = Value;
	}
	private String TotalClaimAmount = null;
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


