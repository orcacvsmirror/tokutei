package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Section {

	/* Added 2008/06/10 �ጎ  */
	/* --------------------------------------------------- */
	private static HashMap<String, String> doctorIkenNameMap =
		createDoctorIkenNameMap();

	private static HashMap<String, String> createDoctorIkenNameMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("9N511000000000049", "9N516000000000049");
		map.put("9N521000000000049", "9N526000000000049");
		map.put("9N531000000000049", "9N536000000000049");
		map.put("9N541000000000049", "9N546000000000049");
		map.put("9N571000000000049", "9N576000000000049");
		map.put("9N581161400000049", "9N586000000000049");
		map.put("9N591161400000049", "9N596000000000049");
		map.put("9N601161400000049", "9N606000000000049");
		map.put("9N611161400000049", "9N616000000000049");
		map.put("9N621161400000049", "9N626000000000049");
		map.put("9N631161400000049", "9N636000000000049");
		map.put("9N641000000000049", "9N646000000000049");

		return map;
	}
	/* --------------------------------------------------- */

	/**
	 * @param observationList
	 * @param Purpose �񍐂̖ړI�i0 = ���茒�f , 1 = ���̑��j
	 */
	public static Element getElement(Document doc, List<Observation> observationList, int Purpose)
	{
		if(Purpose < 0 || 1 < Purpose)
		{
			throw new RuntimeException();
		}

		Element element = doc.createElement("section");
		Element element_code = doc.createElement("code");

		// add s.inoue 2009/12/29
		String displayTitle = "";
		if(Purpose == 0)
		{
			// edit s.inoue 2009/12/29
			displayTitle = "�����E��f���ʃZ�N�V����";
			element_code.setAttribute("displayName", displayTitle);
			element_code.setAttribute("code", "01010");
			element_code.setAttribute("codeSystem", "1.2.392.200119.6.1010");
		}else{
			// edit s.inoue 2009/12/29
			displayTitle = "�C�Ӓǉ�����";
			element_code.setAttribute("displayName", displayTitle);
			element_code.setAttribute("code", "01990");
			element_code.setAttribute("codeSystem", "1.2.392.200119.6.1010");
		}
		element.appendChild(element_code);
		element.appendChild(doc.createElement("text"));

		/*
		 * ���ڃ��X�g�����ԍ��ɏ]���ă\�[�g����B
		 */
		Collections.sort(observationList, new Comparator<Observation>(){
			public int compare(Observation o1, Observation o2) {
				int no1 = o1.getXmlItemSeqNo();
				int no2 = o2.getXmlItemSeqNo();

				return no1 - no2;
			}
		});

		HashMap<String, Observation> doctorNameObservationMap = new HashMap<String, Observation>();

		/* ��t���̍��ځi�������ڂƂ��Ĉ���Ȃ��j����菜���AHashMap �ɓo�^����B */
		for(int i = 0 ; i < observationList.size() ; i++){
			Observation observationItem = observationList.get(i);
			String code = observationItem.getCode();

			if (doctorIkenNameMap.containsValue(code)) {
				/* ��t���̍��ڂ̏ꍇ */
				observationList.remove(observationItem);
				doctorNameObservationMap.put(code, observationItem);
			}
		}

		/* ��t����ݒ�\�ȍ��ڂŁA�Ή������t�������݂���ꍇ�́A��t����
		 * �ݒ肷��B */
		for(int i = 0 ; i < observationList.size() ; i++){
			Observation ikenItem = observationList.get(i);
			String code = ikenItem.getCode();

			if (doctorIkenNameMap.containsKey(code)) {
				String doctorNameCode = doctorIkenNameMap.get(code);
				Observation nameItem = doctorNameObservationMap.get(doctorNameCode);

				if (nameItem == null ) {
					continue;
				}

				String name = nameItem.getResult();
				ikenItem.setAuthor(name);
			}
		}

		/* ��A�����O���[�v�֌W�R�[�h�ƁA���̗v�f�̃}�b�v */
		HashMap<String, Element> ichirenKensaMap = new HashMap<String, Element>();

		for(int i = 0 ; i < observationList.size() ; i++){
			Observation observationItem = observationList.get(i);

			/* ��A�����R�[�h�̗L���ŏ����𕪊򂷂�B */
			String entryType = observationItem.getEntryType();

			Element entryElement = doc.createElement("entry");
			Element observationElement = observationItem.getElement(doc);

			if (observationElement == null) {
				continue;
			}

			/* ��A�����O���[�v�ɑ����Ȃ��ꍇ */
			if (entryType == null || entryType.isEmpty()) {
				if (Purpose == 1)
				{
					// add s.inoue 2009/12/29
					entryElement.appendChild(doc.createComment(observationItem.getCodeDisplayName()));
					entryElement.appendChild(observationElement);
					element.appendChild(entryElement);
				}else{
					// add s.inoue 2013/01/29
					String tmpCd = observationItem.getCode();
					String tmpAdd = "";
					if (tmpCd.equals("3D045000001906202") || tmpCd.equals("3D045000001920402") || tmpCd.equals("3D045000001927102") || tmpCd.equals("3D045000001999902")){
						tmpAdd = "(JDS)";
					}else if (tmpCd.equals("3D046000001906202") || tmpCd.equals("3D046000001920402") || tmpCd.equals("3D046000001927102") || tmpCd.equals("3D046000001999902")){
						tmpAdd = "(NGSP)";
					}
					entryElement.appendChild(doc.createComment(observationItem.getCodeDisplayName() +tmpAdd));
					entryElement.appendChild(observationElement);
					element.appendChild(entryElement);
				}
			}
			/* ��A�����O���[�v�ɑ�����ꍇ */
			else {
				Element elementRelationship = doc.createElement("entryRelationship");
				elementRelationship.setAttribute("typeCode", observationItem.getEntryCode());
				elementRelationship.appendChild(observationElement);

				/* �܂��o�^����Ă��Ȃ��O���[�v�̏ꍇ�́A�V�K�ɃO���[�v���쐬���Ēǉ�����B */
				Element groupObservationElement = ichirenKensaMap.get(entryType);
				if (groupObservationElement == null) {

					groupObservationElement = doc.createElement("observation");
					groupObservationElement.setAttribute("classCode", "OBS");
					groupObservationElement.setAttribute("moodCode", "EVN");

					Element codeElement = doc.createElement("code");
					codeElement.setAttribute("nullFlavor", "NA");
					groupObservationElement.appendChild(codeElement);

					// add s.inoue 2009/12/29
					entryElement.appendChild(doc.createComment(observationItem.getCodeDisplayName()));
					entryElement.appendChild(groupObservationElement);
					element.appendChild(entryElement);

					ichirenKensaMap.put(entryType, groupObservationElement);
				}
				groupObservationElement.appendChild(elementRelationship);
			}
		}

		return element;
	}

}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

