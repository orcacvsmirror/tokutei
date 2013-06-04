package jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class CheckupCard {
	public Element GetElement(Document doc)
	{
		Element element = doc.createElement("checkupCard");

		if (Id != null && ! Id.isEmpty()) {
			Element element_id = doc.createElement("id");
			element_id.setAttribute("root","1.2.392.200119.6.209");
			element_id.setAttribute("extension",Id);
			// add s.inoue 2010/01/18
			element.appendChild(doc.createComment("��f�������ԍ�"));
			element.appendChild(element_id);
		}

		/* Modified 2008/03/31 �ጎ �L�����������݂��Ȃ��ꍇ */
		if (LimitTime != null && ! LimitTime.isEmpty()) {
			Element element_time = doc.createElement("effectiveTime");
			Element element_high = doc.createElement("high");
			element_high.setAttribute("value", LimitTime);
			// add s.inoue 2010/01/18
			element_time.appendChild(doc.createComment("��f���L������"));
			element_time.appendChild(element_high);
			element.appendChild(element_time);
		}

		if(1 <= ClaimType && ClaimType <= 4)
		{
			// add s.inoue 2010/01/18
			element.appendChild(doc.createComment("�������S�i��{�I�Ȍ��f�j"));
			Element element_basic = doc.createElement("chargeTypeBasic");
			element_basic.setAttribute("code", BasicCode);

			if(BasicCode.equals("2") || BasicCode.equals("4"))
			{
				Element element_amount = doc.createElement("amount");
				element_amount.setAttribute("value", BasicAmount);
				element_amount.setAttribute("currency", "JPY");
				element_basic.appendChild(element_amount);
			}
			if(BasicCode.equals("3"))
			{
				Element element_rate = doc.createElement("rate");
				element_rate.setAttribute("value", BasicRate);
				element_rate.setAttribute("unit", "%");
				element_basic.appendChild(element_rate);
			}
			element.appendChild(element_basic);
		}

		if(ClaimType == 2 || ClaimType == 4)
		{
			// add s.inoue 2010/01/18
			element.appendChild(doc.createComment("�������S�i�ڍׁj"));
			Element element_detail = doc.createElement("chargeTypeDetail");
			element_detail.setAttribute("code", DetailCode);

			if(DetailCode.equals("2") || DetailCode.equals("4"))
			{
				Element element_amount = doc.createElement("amount");
				element_amount.setAttribute("value", DetailAmount);
				element_amount.setAttribute("currency", "JPY");
				element_detail.appendChild(element_amount);
			}

			if(DetailCode.equals("3"))
			{
				Element element_rate = doc.createElement("rate");
				element_rate.setAttribute("value", DetailRate);
				element_rate.setAttribute("unit", "%");
				element_detail.appendChild(element_rate);
			}
			element.appendChild(element_detail);
		}

		if(ClaimType == 3 || ClaimType == 4)
		{
			// add s.inoue 2010/01/18
			element.appendChild(doc.createComment("�������S�i�ǉ����f�j"));
			Element element_other = doc.createElement("chargeTypeOther");
			element_other.setAttribute("code", OtherCode);

			if(OtherCode.equals("2") || OtherCode.equals("4"))
			{
				Element element_amount = doc.createElement("amount");
				element_amount.setAttribute("value", OtherAmount);
				element_amount.setAttribute("currency", "JPY");
				element_other.appendChild(element_amount);
			}

			if(OtherCode.equals("3"))
			{
				Element element_rate = doc.createElement("rate");
				element_rate.setAttribute("value", OtherRate);
				element_rate.setAttribute("unit", "%");
				element_other.appendChild(element_rate);
			}
			element.appendChild(element_other);
		}

		if(ClaimType == 5)
		{
			// add s.inoue 2010/01/18
			element.appendChild(doc.createComment("�������S�i�l�ԃh�b�N�j"));
			Element element_dock = doc.createElement("chargeTypeHumanDryDock");

			// ��̏���������ȗ������邽�߂�null�����Ă���
			if(MaxInsurance != null)
			{
				if(MaxInsurance.equals(""))
				{
					MaxInsurance = null;
				}
			}

// move s.inoue 2010/02/03
// �����C�� 4�ȊO�̎�
//			Element element_copyment = doc.createElement("copayment");
//
//			element_copyment.setAttribute("code", OtherCode);
//
//			if(DockCode.equals("2"))
//			{
//				Element element_amount = doc.createElement("amount");
//				element_amount.setAttribute("value", DockAmount);
//				element_amount.setAttribute("currency", "JPY");
//				element_copyment.appendChild(element_amount);
//			}
//
//			if(DockCode.equals("3"))
//			{
//				Element element_rate = doc.createElement("rate");
//				element_rate.setAttribute("value", DockRate);
//				element_rate.setAttribute("unit", "%");
//				element_copyment.appendChild(element_rate);
//			}
//
//			element_dock.appendChild(element_copyment);
//
//			if(MaxInsurance != null && DockCode.equals("3"))
//			{
//				Element element_max = doc.createElement("maxInsuranceLimit");
//				element_max.setAttribute("code", "4");
//
//				Element element_amount = doc.createElement("amount");
//				element_amount.setAttribute("value", MaxInsurance);
//				element_amount.setAttribute("currency", "JPY");
//				element_max.appendChild(element_amount);
//
//				element_dock.appendChild(element_max);
//			}
//			element.appendChild(element_dock);
//		}
			// edit s.inoue 2010/02/03
			// maxInsuranceLimit��4�̂ݕ��S�敪��3����4��
			// if(MaxInsurance != null && DockCode.equals("3"))
			if(MaxInsurance != null && DockCode.equals("4"))
			{
				Element element_max = doc.createElement("maxInsuranceLimit");
				element_max.setAttribute("code", "4");

				Element element_amount = doc.createElement("amount");
				element_amount.setAttribute("value", MaxInsurance);
				// edit s.inoue 2010/08/11
				element_amount.setAttribute("currency", "JPY");
				element_max.appendChild(element_amount);

				element_dock.appendChild(element_max);
			}else{

				// move s.inoue 2010/02/03
				Element element_copyment = doc.createElement("copayment");

				element_copyment.setAttribute("code", OtherCode);

				if(DockCode.equals("2"))
				{
					Element element_amount = doc.createElement("amount");
					element_amount.setAttribute("value", DockAmount);
					element_amount.setAttribute("currency", "JPY");
					element_copyment.appendChild(element_amount);
				}

				if(DockCode.equals("3"))
				{
					Element element_rate = doc.createElement("rate");
					element_rate.setAttribute("value", DockRate);
					element_rate.setAttribute("unit", "%");
					element_copyment.appendChild(element_rate);
				}

				element_dock.appendChild(element_copyment);
			}

			element.appendChild(element_dock);
		}

		return element;
	}

	public boolean Check()
	{
		if(Id == null || LimitTime == null || ClaimType < 0)
		{
			return false;
		}

		if(1 <= ClaimType && ClaimType <= 4)
		{
			if(BasicCode == null)
			{
				return false;
			}

			if(BasicCode.equals("2") || BasicCode.equals("4"))
			{
				if(BasicAmount == null)
				{
					return false;
				}
			}

			if(BasicCode.equals("3"))
			{
				if(BasicRate == null)
				{
					return false;
				}
			}
		}

		if(ClaimType == 2 || ClaimType == 4)
		{
			if(DetailCode == null)
			{
				return false;
			}

			if(DetailCode.equals("2") || DetailCode.equals("4"))
			{
				if(DetailAmount == null)
				{
					return false;
				}
			}

			if(DetailCode.equals("3"))
			{
				if(DetailRate == null)
				{
					return false;
				}
			}
		}

		if(ClaimType == 2 || ClaimType == 4)
		{
			if(OtherCode == null)
			{
				return false;
			}

			if(OtherCode.equals("2") || OtherCode.equals("4"))
			{
				if(OtherAmount == null)
				{
					return false;
				}
			}

			if(OtherCode.equals("3"))
			{
				if(OtherRate == null)
				{
					return false;
				}
			}
		}

		if(ClaimType == 5)
		{
			if(OtherCode == null)
			{
				return false;
			}

			if(DockCode.equals("2"))
			{
				if(DockAmount == null)
				{
					return false;
				}
			}

			if(DockCode.equals("3"))
			{
				if(DockRate == null)
				{
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * @param Value ��f�������ԍ�
	 */
	public void setId(String Value)
	{
		Id = Value;
	}
	private String Id = null;

	/**
	 * @param Value ��f���̗L������
	 */
	public void setLimitTime(String Value)
	{
		LimitTime = Value;
	}
	private String LimitTime = null;

	/**
	 * @param Value �����敪
	 */
	public void setClaimType(int Value)
	{
		if(1 <= Value && Value <= 5)
		{
			ClaimType = Value;
		}
	}
	private int ClaimType = -1;

	/**
	 * @param Value ��{�I�Ȍ��f�̑������S�R�[�h
	 */
	public void setBasicCode(String Value)
	{
		BasicCode = Value.trim();
	}
	private String BasicCode = null;

	/**
	 * @param Value ��{�I�Ȍ��f�̋��z
	 */
	public void setBasicAmount(long Value)
	{
		BasicAmount = Utility.FillZero(Value, 6);
	}
	public void setBasicAmount(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */

		BasicAmount = Value;
	}
	private String BasicAmount = null;

	/**
	 * @param Value ��{�I�Ȍ��f�̕��S��
	 */
	public void setBasicRate(double Value)
	{
		BasicRate = Utility.getPercentFormat(Value);
	}
	public void setBasicRate(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */

		BasicRate = Value;
	}
	private String BasicRate = null;

	/**
	 * @param Value �ڍׂȌ��f�̑������S�R�[�h
	 */
	public void setDetailCode(String Value)
	{
		DetailCode = Value.trim();
	}
	private String DetailCode = null;

	/**
	 * @param Value �ڍׂȌ��f�̋��z
	 */
	public void setDetailAmount(long Value)
	{
		DetailAmount = Utility.FillZero(Value, 6);
	}
	public void setDetailAmount(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */
		DetailAmount = Value;
	}
	private String DetailAmount = null;

	/**
	 * @param Value �ڍׂȌ��f�̕��S��
	 */
	public void setDetailRate(double Value)
	{
		DetailRate = Utility.getPercentFormat(Value);
	}
	public void setDetailRate(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */
		DetailRate = Value;
	}
	private String DetailRate = null;

	/**
	 * @param Value �ǉ����f�̑������S�R�[�h
	 */
	public void setOtherCode(String Value)
	{
		OtherCode = Value.trim();
	}
	private String OtherCode = null;

	/**
	 * @param Value �ǉ����f�̋��z
	 */
	public void setOtherAmount(long Value)
	{
		OtherAmount = Utility.FillZero(Value, 6);
	}
	public void setOtherAmount(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */
		OtherAmount = Value;
	}
	private String OtherAmount = null;

	/**
	 * @param Value �ǉ����f�̕��S��
	 */
	public void setOtherRate(double Value)
	{
		OtherRate = Utility.getPercentFormat(Value);
	}
	public void setOtherRate(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */
		OtherRate = Value;
	}
	private String OtherRate = null;

	/**
	 * @param Value �l�ԃh�b�N�̑������S�R�[�h
	 */
	public void setDockCode(String Value)
	{
		DockCode = Value.trim();
	}
	private String DockCode = null;

	/**
	 * @param Value �l�ԃh�b�N�̋��z
	 */
	public void setDockAmount(long Value)
	{
		DockAmount = Utility.FillZero(Value, 6);
	}
	public void setDockAmount(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */
		DockAmount = Value;
	}
	private String DockAmount = null;

	/**
	 * @param Value �l�ԃh�b�N�̕��S��
	 */
	public void setDockRate(double Value)
	{
		DockRate = Utility.getPercentFormat(Value);
	}
	public void setDockRate(String Value)
	{
		/* Added 2008/04/03 �ጎ  */
		/* --------------------------------------------------- */
		if (Value == null || Value.isEmpty()) {
			Value = "000000";
		}
		/* --------------------------------------------------- */
		DockRate = Value;
	}
	private String DockRate = null;

	/**
	 * @param Value �l�ԃh�b�N�̕ی��ҕ��S���
	 */
	public void setDockMaxInsurance(long Value)
	{
		MaxInsurance = Utility.FillZero(Value, 6);
	}
	public void setDockMaxInsurance(String Value)
	{
		MaxInsurance = Value;
	}
	public String MaxInsurance = null;

	/* Added 2008/06/02 �ጎ  */
	/* --------------------------------------------------- */
//	public long getHokenjyaJyogenKihon() {
//		return hokenjyaJyogenKihon;
//	}
//
//	public void setHokenjyaJyogenKihon(long hokenjyaJyogenKihon) {
//		this.hokenjyaJyogenKihon = hokenjyaJyogenKihon;
//	}
//
//	public long getHokenjyaJyogenSyousai() {
//		return hokenjyaJyogenSyousai;
//	}
//
//	public void setHokenjyaJyogenSyousai(long hokenjyaJyogenSyousai) {
//		this.hokenjyaJyogenSyousai = hokenjyaJyogenSyousai;
//	}
//
//	public long getHokenjyaJyogenTsuika() {
//		return hokenjyaJyogenTsuika;
//	}
//
//	public void setHokenjyaJyogenTsuika(long hokenjyaJyogenTsuika) {
//		this.hokenjyaJyogenTsuika = hokenjyaJyogenTsuika;
//	}
	/* --------------------------------------------------- */
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

