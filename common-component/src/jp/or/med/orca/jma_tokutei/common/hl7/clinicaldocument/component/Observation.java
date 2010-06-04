package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Observation {
	private static final String DEFAULT_VALUE_CODE_SYSTEM = "1.2.392.200119.6.1005";

	public Element getElement(Document doc)
	{
		// move s.inoue 20080912
		Element element_code = null;

		Element element = doc.createElement("observation");
		element.setAttribute("classCode", "OBS");
		element.setAttribute("moodCode", "EVN");

		boolean existsResult = false;
		if(result != null && ! result.isEmpty()){
			existsResult = true;
		}

		// ���f���{

		// �w�b�_
//		String negationIndValue = existsResult ? "false" : "true";

		/* Modified 2008/06/23 �ጎ  */
		/* --------------------------------------------------- */
//		/* ���ʒl�����݂���ꍇ�A�T���v���ɏ]�� negationInd �����̕t�����ȗ�����B */
//		if (! existsResult) {
//			element.setAttribute("negationInd", "true");
//		}

		// del s.inoue 20080910
		/* --------------------------------------------------- */
		/* ���ʒl�����݂��Ȃ��ꍇ�A�v�f���o�������Ȃ��B */
//		if (! existsResult) {
//			return null;
//		}
		/* --------------------------------------------------- */

		// add s.inoue 20080910 �����{
		/* ���{�敪���u0:�����{�v�̎��AnegationInd �����̕t�����ȗ�����B */
		/*code.equals("9A755000000000001") ||code.equals("9A752000000000001") ||code.equals("9A751000000000001") ||*/
		if (this.JISI_KBN == null ||
				(this.JISI_KBN.equals("1") && !existsResult)){
			if (code.equals("9A765000000000001") ||
				code.equals("9A762000000000001") ||
				code.equals("9A761000000000001")){
				this.JISI_KBN = "0";
			}else{
				return null;
			}
		}

		// add s.inoue 20081002 ����s�\�͏ȗ���
		if (this.JISI_KBN.equals("0")) {
			element.setAttribute("negationInd", "true");
		}

		// edit s.inoue 20080912
		// ���{�y�ё���s�\�̂ݗv�f�쐬
		element_code = doc.createElement("code");
		element_code.setAttribute("code", code);

		/* Modified 2008/04/23 �ጎ  */
		/* --------------------------------------------------- */
//		if (codeSystem != DEFAULT_VALUE_CODE_SYSTEM) {
		/* --------------------------------------------------- */
		if (! codeSystem.equals(DEFAULT_VALUE_CODE_SYSTEM)) {
		/* --------------------------------------------------- */
			element_code.setAttribute("codeSystem", codeSystem);
		}
		element.appendChild(element_code);

		/* Added 2008/04/06 �ጎ */
		/* --------------------------------------------------- */
		if (this.codeDisplayName != null && !this.codeDisplayName.isEmpty()) {
			element_code.setAttribute("displayName", this.codeDisplayName);
		}
		/* --------------------------------------------------- */

		// ���ԏ��
		if (effectiveTime != null && ! effectiveTime.isEmpty()) {
			Element element_time = doc.createElement("effectiveTime");
			Element element_width = doc.createElement("width");
			element_width.setAttribute("value", effectiveTime);
			element_width.setAttribute("unit", "min");
			element_time.appendChild(element_width);
			element.appendChild(element_time);
		}

		/* Modified 2008/04/06 �ጎ */
		/* --------------------------------------------------- */
		// // ����
		// Element element_value = doc.createElement("value");
		// element_value.setAttribute("xsi:type", DataType);
		// if(Pattern == 1)
		// {
		// // �p�^�[��1
		// element_value.setAttribute("value", Result);
		// element_value.setAttribute("unit", Unit);
		// }
		// if(Pattern == 2)
		// {
		// if(DataType.equals("CD") || DataType.equals("CO"))
		// {
		// // �p�^�[��2 �R�[�h
		// element_value.setAttribute("code", Result);
		// element_value.setAttribute("codeSystem", ResultCode);
		// element_value.setAttribute("displayName", DisplayName);
		// }
		//
		// if(DataType.equals("ST"))
		// {
		// // �p�^�[��2 ������
		// element_value.setTextContent(Result);
		// }
		// }
		// element.appendChild(element_value);
		/* --------------------------------------------------- */

		// edit s.inoue 20080910 �����{ �����C��
//		if (existsResult) {

		if (this.JISI_KBN.equals("1") ||
				this.JISI_KBN.equals("2")) {

			// ����
			/* value �v�f */
			Element element_value = doc.createElement("value");

			/* xsi:type ���� */
			element_value.setAttribute("xsi:type", dataType);

			boolean inInputRange = true;

			// add s.inoue 20080910 ����s�\�@�����C��
			if (this.JISI_KBN.equals("2")) {
				element_value.setAttribute("nullFlavor", "NI");

				/* value �v�f */
				element.appendChild(element_value);
			}else if (this.JISI_KBN.equals("1") && existsResult){

				if (pattern == 1) {
					/* ���l�^ */

					double resultValue = 0;

					/* Added 2008/05/10 �ጎ  */
					/* --------------------------------------------------- */
					/* ���͏���A�����l�͈͓� */
	//				boolean inInputRange = true;
					try {
						resultValue = Double.parseDouble(result);

						/* ���͏���l�A�����l */
						/* ���ʒl�����͉����l�����������ꍇ */
	//					boolean underLowValue = false;
						if (inputRangeLowValue != null && ! inputRangeLowValue.isEmpty()) {
							try {
								double inputLowLimit = Double.parseDouble(inputRangeLowValue);
								if (resultValue <= inputLowLimit ) {

	//								underLowValue = true;
									inInputRange = false;

									element_value.setAttribute("code", "L");
									element_value.setAttribute("codeSystem", "2.16.840.1.113883.5.83");
									element_value.setAttribute("displayName", "�ȉ�");

									/* Added 2008/06/23 �ጎ  */
									/* --------------------------------------------------- */
									element_value.setAttribute("xsi:type", "CD");
									/* --------------------------------------------------- */
								}

							} catch (NumberFormatException e) {
								/* ���l�ɕϊ��ł��Ȃ������ꍇ */
								e.printStackTrace();
							}
						}

						if (inInputRange) {
							/* ���͏���l�͈͊O */
	//						boolean overHighValue = false;

							if (inputRangeHighValue != null && ! inputRangeHighValue.isEmpty()) {
								try {
									double inputHighLimit = Double.parseDouble(inputRangeHighValue);
									if (resultValue >= inputHighLimit ) {
										inInputRange = false;

										element_value.setAttribute("code", "H");
										element_value.setAttribute("codeSystem", "2.16.840.1.113883.5.83");
										element_value.setAttribute("displayName", "�ȏ�");

										/* Added 2008/06/23 �ጎ  */
										/* --------------------------------------------------- */
										element_value.setAttribute("xsi:type", "CD");
										/* --------------------------------------------------- */
									}
								} catch (NumberFormatException e) {
									/* ���l�ɕϊ��ł��Ȃ������ꍇ */
									e.printStackTrace();
								}
							}

							if (inInputRange) {
								this.outputValueAndUnit(element, element_value);
							}
						}

					} catch (NumberFormatException e) {
						/* ���l�ɕϊ��ł��Ȃ������ꍇ */
						e.printStackTrace();
					}

					/* ���͏���l�͈͓� */
					if (inInputRange) {
						if (
								observationRangeHighValue != null
								&& ! observationRangeHighValue.isEmpty()
								&& observationRangeLowValue != null
								&& ! observationRangeLowValue.isEmpty()
								) {
							/* ��l����l�A�����l�̗��������݂���ꍇ */

							/* HL������ */
							String hl = null;

							/* ���l�ɕϊ����Ĕ�r����B */
							try {
								double highValue = Double.parseDouble(observationRangeHighValue);
								double lowValue = Double.parseDouble(observationRangeLowValue);
	//							double resultValue = Double.parseDouble(result);

								/* ���ʒl����l���������������iL�j */
								if ( resultValue < lowValue ) {
									hl = "L";
								}
								/* ���ʒl����l���������傫�� */
								else {
									/* ���ʒl����l����ȉ��iN�j */
									if( resultValue <= highValue ) {
										hl = "N";
									}
									/* ���ʒl����l��������傫���iH�j */
									else {
										hl = "H";
									}
								}

							} catch (NumberFormatException e) {
								/* ���l�ɕϊ��ł��Ȃ������ꍇ */
								e.printStackTrace();
							}

							/* value �v�f */
							element.appendChild(element_value);

							/* ��l����l�A�����l�����݂���ꍇ */
							Element element_interpret = doc.createElement("interpretationCode");
							element_interpret.setAttribute("code", hl);

							element.appendChild(element_interpret);
						}
						else {
							/* ���͏���l�͈͓��̏ꍇ */
							/* ���ʒl�ƒP�ʂ��o�͂���B */
	//						element_value.setAttribute("value", result);
	//						element_value.setAttribute("unit", unit);
							this.outputValueAndUnit(element, element_value);
						}

					}
					/* Added 2008/06/20 �ጎ  */
					/* --------------------------------------------------- */
					else {
						/* value �v�f */
						element.appendChild(element_value);
					}
					/* --------------------------------------------------- */
					/* --------------------------------------------------- */
				}
				else if (pattern == 2) {
					if (dataType.equals("CD") || dataType.equals("CO")) {
						// �p�^�[��2 �R�[�h
						element_value.setAttribute("code", result);
						element_value.setAttribute("codeSystem", resultCode);

						/* Deleted 2008/04/24 �ጎ  */
						/* --------------------------------------------------- */
	//					element_value.setAttribute("displayName", itemCodeDisplayName);
						/* --------------------------------------------------- */
					}
					else if (dataType.equals("ST")) {
						// �p�^�[��2 ������
						element_value.setTextContent(result);
					}

					/* value �v�f */
					element.appendChild(element_value);
				}
			}
			/* ���ʉ��߃R�[�h */
			/* Modified 2008/05/10 �ጎ  */
			/* --------------------------------------------------- */
//			if (pattern == 1 && ! interpretationCode.equals("")) {
			/* --------------------------------------------------- */
//			if (pattern == 1) {
//				Element element_interpret = doc.createElement("interpretationCode");
//
//				/* Modified 2008/05/09 �ጎ  */
//				/* --------------------------------------------------- */
////				element_interpret.setAttribute("code", interpretationCode);
//				/* --------------------------------------------------- */
//
//				/* ��l����l�A�����l */
//				/* ��l����l�A�����l�����݂���ꍇ */
//				if (
//						observationRangeHighValue != null
//						&& ! observationRangeHighValue.isEmpty()
//						&& observationRangeLowValue != null
//						&& ! observationRangeLowValue.isEmpty()
//						) {
//
//					String hl = null;
//
//					/* ���l�ɕϊ����Ĕ�r����B */
//					try {
//						double highValue = Double.parseDouble(observationRangeHighValue);
//						double lowValue = Double.parseDouble(observationRangeLowValue);
////						double resultValue = Double.parseDouble(result);
//
//						/* ���ʒl����l�������������� */
//						if ( resultValue < lowValue ) {
//						}
//						/* ���ʒl����l���������傫�� */
//						else {
//							/* ���ʒl����l����ȉ� */
//							if( resultValue <= highValue ) {
//
//							}
//							/* ���ʒl����l��������傫�� */
//							else {
//
//							}
//						}
//
//					} catch (NumberFormatException e) {
//						/* ���l�ɕϊ��ł��Ȃ������ꍇ */
//						e.printStackTrace();
//					}
//				}
//
//				String hl = null;
//				if (interpretationCode.equals("1")) {
//					hl = "L";
//				}
//				else if(interpretationCode.equals("2")){
//					hl = "H";
//				}
//				else {
//					hl = "N";
//				}
//
//				element_interpret.setAttribute("code", hl);
//
//				element.appendChild(element_interpret);
//			}

			/* �������@ */
			if (!methodCode.equals("")) {
				Element element_method = doc.createElement("methodCode");
				element_method.setAttribute("code", methodCode);
				element.appendChild(element_method);
			}

			/* ��l���o�͂���B */
			/* Modified 2008/06/25 �ጎ  */
			/* --------------------------------------------------- */
//			if (pattern == 1 &&
//					!observationRangeHighValue.isEmpty() && !observationRangeLowValue.isEmpty()){
			/* --------------------------------------------------- */
			if (pattern == 1 &&
					!observationRangeHighValue.isEmpty() && !observationRangeLowValue.isEmpty()
					&& inInputRange){
			/* --------------------------------------------------- */

				Element element_ref = doc.createElement("referenceRange");
				Element element_observRange = doc.createElement("observationRange");
				element_observRange.setAttribute("classCode", "OBS");
				element_observRange.setAttribute("moodCode", "EVN.CRT");

				Element element_value2 = doc.createElement("value");
				element_value2.setAttribute("xsi:type", "IVL_PQ");

				/* �����l */
				Element element_low = doc.createElement("low");
				element_low.setAttribute("value", observationRangeLowValue);
				if(!observationRangeLowUnit.isEmpty()) {
					element_low.setAttribute("unit", observationRangeLowUnit);
				}
				element_value2.appendChild(element_low);

				/* ����l */
				Element element_high = doc.createElement("high");
				element_high.setAttribute("value", observationRangeHighValue);
				if(	!observationRangeHighUnit.isEmpty()){
					element_high.setAttribute("unit", observationRangeHighUnit);
				}
				element_value2.appendChild(element_high);

				element_observRange.appendChild(element_value2);
				element_ref.appendChild(element_observRange);
				element.appendChild(element_ref);
			}
		}
		/* --------------------------------------------------- */

		// �L�^�ҏ��
		if (author != null && ! author.isEmpty()) {
			/* Modified 2008/06/08 �ጎ  */
			/* --------------------------------------------------- */
//			Element element_author = doc.createElement("author");
//			element_author.setAttribute("nullFlavor", "NI");
//			Element element_assighend = doc.createElement("assignedAuthor");
//			element_assighend.setAttribute("nullFlavor", "NI");
//			Element element_person = doc.createElement("assignedPerson");
//
//			Element element_name = doc.createElement("name");
//			element_name.setTextContent(author);
//			element_person.appendChild(element_person);
//
//			element_assighend.appendChild(element_person);
//			element_author.appendChild(element_assighend);
//			element.appendChild(element_author);
			/* --------------------------------------------------- */
			/* author �v�f */
			Element element_author = doc.createElement("author");
			element.appendChild(element_author);

			/* time �v�f */
			Element element_time = doc.createElement("time");
			element_time.setAttribute("nullFlavor", "NI");
			element_author.appendChild(element_time);

			/* assignedAuthor �v�f */
			Element element_assighendAuthor = doc.createElement("assignedAuthor");
			element_author.appendChild(element_assighendAuthor);

			/* id �v�f */
			Element element_id = doc.createElement("id");
			element_id.setAttribute("nullFlavor", "NI");
			element_assighendAuthor.appendChild(element_id);

			/* assignedPerson �v�f */
			Element element_person = doc.createElement("assignedPerson");
			element_assighendAuthor.appendChild(element_person);

			/* name �v�f */
			Element element_name = doc.createElement("name");
			element_name.setTextContent(author);
			element_person.appendChild(element_name);
			/* --------------------------------------------------- */
		}

		// }else{
		// ���f�����{
		// element.setAttribute("negationInd", "true");
		//
		// Element element_code = doc.createElement("code");
		// element_code.setAttribute("code", Code);
		//
		// element.appendChild(element_code);
		// }

		return element;
	}

	private void outputValueAndUnit(Element element, Element element_value) {
		/* ���͏���l�͈͓��̏ꍇ */
		/* ���ʒl�ƒP�ʂ��o�͂���B */

		/* Modified 2008/06/10 �ጎ  */
		/* --------------------------------------------------- */
//		element_value.setAttribute("value", result);
		/* --------------------------------------------------- */
		String resultOutput = null;

		if (this.result.startsWith(".")) {
			resultOutput =  "0" + this.result;
		}
		else {
			resultOutput =  this.result;
		}

		element_value.setAttribute("value", resultOutput);
		/* --------------------------------------------------- */


		if (this.unit != null && ! this.unit.isEmpty()) {
			element_value.setAttribute("unit", unit);
		}

		/* value �v�f */
		element.appendChild(element_value);
	}

	public boolean check()
	{
		if(result == null)
		{
			return false;
		}

		if(!result.equals(""))
		{
			// ���f���{

			if(
					code == null ||
					codeSystem == null ||
					dataType == null ||
					pattern < 0
			)
			{
				return false;
			}

			if(pattern == 1)
			{
				// �p�^�[��1

				if(
						result == null ||
						unit == null
				)
				{
					return false;
				}
			}
			if(pattern == 2)
			{
				if(dataType == null)
				{
					return false;
				}

				if(dataType.equals("CD") || dataType.equals("CO"))
				{
					// �p�^�[��2 �R�[�h
					if(
							result == null ||
							resultCode == null ||
							itemCodeDisplayName == null
					)
					{
						return false;
					}
				}

				if(dataType.equals("ST"))
				{
					// �p�^�[��2 ������
					if(result == null)
					{
						return false;
					}
				}
			}

			// ���ʉ��߃R�[�h

			if(interpretationCode == null)
			{
				return false;
			}

			if(pattern == 1 && !interpretationCode.isEmpty())
			{
				if(interpretationCode == null)
				{
					return false;
				}
			}

			// �������@
			if(methodCode == null)
			{
				return false;
			}

			if(!methodCode.isEmpty())
			{
				if(methodCode == null)
				{
					return false;
				}
			}

			if(observationRangeHighValue == null || observationRangeLowValue == null)
			{
				return false;
			}

			// ��l
			if(
					!observationRangeHighValue.isEmpty() && !observationRangeLowValue.isEmpty() &&
					!observationRangeHighUnit.isEmpty() && !observationRangeLowUnit.isEmpty()
					)
			{
				if(
						observationRangeLowValue == null ||
						observationRangeLowUnit == null ||
						observationRangeHighValue == null ||
						observationRangeHighUnit == null
				)
				{
					return false;
				}
			}
		}else{
			// ���f�����{
			if(code == null)
			{
				return false;
			}
		}

		return true;
	}


	/**
	 * @param Value ���ڃR�[�h
	 */
	public void setCode(String Value)
	{
		code = Value;
	}
	private String code = null;

	/**
	 * @param Value ���ڃR�[�hOID
	 */
	public void setCodeSystem(String Value)
	{
		codeSystem = Value;
	}
	private String codeSystem = null;


	/* Added 2008/04/06 �ጎ  */
	/* --------------------------------------------------- */
	/**
	 * �������ڃR�[�h�\����
	 */
	private String codeDisplayName = null;
	public String getCodeDisplayName() {
		return codeDisplayName;
	}

	public void setCodeDisplayName(String name) {
		codeDisplayName = name;
	}
	/* --------------------------------------------------- */

	/* Added 2008/04/06 �ጎ  */
	/* --------------------------------------------------- */
	private int xmlItemSeqNo = -1;

	public int getXmlItemSeqNo() {
		return xmlItemSeqNo;
	}

	public void setXmlItemSeqNo(int xmlItemSeqNo) {
		this.xmlItemSeqNo = xmlItemSeqNo;
	}
	/* --------------------------------------------------- */

	/**
	 * @param Value �����p�^�[��
	 */
	public void setPattern(String Value)
	{
		if(Value.trim().equals("1"))
		{
			pattern = 1;
		}
		if(Value.trim().equals("2"))
		{
			pattern = 2;
		}
	}
	private int pattern = -1;

	/**
	 * @param value ���ԏ��i���P�ʁj
	 * ���͂���Ă���ꍇ�͎g�p
	 */
	public void setEffectiveTime(String value)
	{
		effectiveTime = value;
	}
	private String effectiveTime = null;

	/**
	 * @param value �������ʂ̃f�[�^�^
	 */
	public void setDataType(String value)
	{
		dataType = value.trim();
	}
	private String dataType;

	/**
	 * @param value ���ʂ̒l
	 * �S�Ă̌��ʂ͂����ɓ���Ă�������
	 */
	public void setResult(String value)
	{
		result = value;
	}
	private String result = null;

	/**
	 * @param value �P�ʃR�[�h
	 * �p�^�[��1�̏ꍇ�Ɏg�p
	 */
	public void setResultUnit(String value)
	{
		unit = value.trim();
	}
	private String unit = null;

	/**
	 * @param Value �������ʃR�[�hOID
	 * �p�^�[��2�Ń^�C�v��CD�̏ꍇ�Ɏg�p
	 *
	 */
	public void setResultCode(String Value)
	{
		resultCode = Value;
	}
	private String resultCode = null;

	/**
	 * @param Value ���ڃR�[�h�\����
	 * �p�^�[��2�Ń^�C�v��CD�̏ꍇ�Ɏg�p
	 */
	public void setItemCodeDisplayName(String Value)
	{
		itemCodeDisplayName = Value;
	}
	private String itemCodeDisplayName = null;

	/**
	 * @param Value ���ʂ̉��߃R�[�h
	 * ��l���ݒ肳��Ă���ꍇ�Ɏg�p
	 */
	public void setInterpretationCode(String Value)
	{
		interpretationCode = Value;
	}
	private String interpretationCode = null;

	/**
	 * @param Value �������@�R�[�h
	 * ���͂���Ă���ꍇ�͎g�p
	 */
	public void setMethodCode(String Value)
	{
		methodCode = Value;
	}
	public String methodCode = null;

	/**
	 * @param Value ���ʂ��L�^������t�̖��O
	 * ���͂���Ă���ꍇ�͎g�p
	 */
	public void setAuthor(String Value)
	{
		author = Value;
	}
	private String author = null;

	/**
	 * @param Value ��l�����l臒l
	 */
	public void setObservationRangeLowValue(String Value)
	{
		observationRangeLowValue = Value;
	}
	private String observationRangeLowValue = null;

	/**
	 * @param Value ��l�����l�P��
	 */
	public void setObservationRangeLowUnit(String Value)
	{
		observationRangeLowUnit = Value;
	}
	private String observationRangeLowUnit = null;

	/**
	 * @param Value ��l����l臒l
	 */
	public void setObservationRangeHighValue(String Value)
	{
		observationRangeHighValue = Value;
	}
	private String observationRangeHighValue = null;

	/**
	 * @param Value ��l����l�P��
	 */
	public void setObservationRangeHighUnit(String Value)
	{
		observationRangeHighUnit = Value;
	}
	private String observationRangeHighUnit = null;

// ��A�����O���[�v�Ɋւ��鍀��
	/**
	 * @param Value ��A�����O���[�v�̃R�[�h�iRSON�ACOMP�Ȃǁj
	 */
	public void setEntryCode(String Value)
	{
		EntryCode = Value;
	}
	public String getEntryCode()
	{
		return EntryCode;
	}
	private String EntryCode = null;

	/**
	 * @param value ��A�����O���[�v�̃^�C�v
	 */
	public void setEntryType(String value)
	{
		entryType = value;
	}
	public String getEntryType()
	{
		return entryType;
	}
	private String entryType = null;

// �����̎�ނ̎w��
	public void setKensaType(String value)
	{
		kensaType = value;
	}
	public String getKensaType()
	{
		return kensaType;
	}
	public String kensaType = null;

	// add start s.inoue 20080909
	//	 ���{�敪
	public void setJISI_KBN(String value)
	{
		JISI_KBN = value;
	}
	public String getJISI_KBN()
	{
		return JISI_KBN;
	}
	public String JISI_KBN = null;
	// add end s.inoue 20080909

	/* Added 2008/05/10 �ጎ  */
	/* --------------------------------------------------- */
	/**
	 * @param Value ���͉����l臒l
	 */
	public void setInputRangeLowValue(String Value)
	{
		inputRangeLowValue = Value;
	}
	private String inputRangeLowValue = null;

	/**
	 * @param Value ���͏���l臒l
	 */
	public void setInputRangeHighValue(String Value)
	{
		inputRangeHighValue = Value;
	}
	private String inputRangeHighValue = null;
	/* --------------------------------------------------- */

	/* Added 2008/06/08 �ጎ  */
	/* --------------------------------------------------- */
	public String getCode() {
		return code;
	}
	public String getResult() {
		return result;
	}
	/* --------------------------------------------------- */
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


