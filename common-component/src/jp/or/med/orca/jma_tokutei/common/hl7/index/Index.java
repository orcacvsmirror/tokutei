package jp.or.med.orca.jma_tokutei.common.hl7.index;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Index {
	public Element GetElement(Document doc,Element element)
	{
		final String iStr_4F8_5C2F4EE5847_B3BB7A9ADC5AC59_E3EF66F79A1 = "yyyyMMdd";
		Element element_interaction = doc.createElement("interactionType");
		element_interaction.setAttribute("code", String.valueOf(InteractionType));
        element.appendChild(element_interaction);
		
		Element element_creationTime = doc.createElement("creationTime");
		
        DateFormat format = new SimpleDateFormat(iStr_4F8_5C2F4EE5847_B3BB7A9ADC5AC59_E3EF66F79A1);
        String NowDate = format.format(new Date());
        
        element_creationTime.setAttribute("value", NowDate);
        element.appendChild(element_creationTime);
		
        // ���M���@�ւ̐ݒ�
        Element element_sender = doc.createElement("sender");
        Element element_senderID = doc.createElement("id");
        
        if(InteractionType == 1 || InteractionType == 6)
        {
        	element_senderID.setAttribute("root", "1.2.392.200119.6.102");
        	element_senderID.setAttribute("extension", Utility.FillZero(SenderExtension, 10));
            element_sender.appendChild(element_senderID);
            element.appendChild(element_sender);
        }else if(InteractionType == 2 || InteractionType == 3 || InteractionType == 11)
        {
        	element_senderID.setAttribute("root", "1.2.392.200119.6.103");
        	element_senderID.setAttribute("extension", Utility.FillZero(SenderExtension, 8));
            element_sender.appendChild(element_senderID);
            element.appendChild(element_sender);
        }else if(InteractionType == 4 || InteractionType == 5 || InteractionType == 7 ||
        		InteractionType == 8 || InteractionType == 10)
        {
        	element_senderID.setAttribute("root", "1.2.392.200119.6.101");
        	element_senderID.setAttribute("extension", String.valueOf(SenderExtension));
            element_sender.appendChild(element_senderID);
            element.appendChild(element_sender);
        }
        
        // ��M���@�ւ̐ݒ�
        Element element_receiver = doc.createElement("receiver");
        Element element_receiverID = doc.createElement("id");
        
        if(InteractionType == 2 || InteractionType == 7)
        {
        	element_receiverID.setAttribute("root", "1.2.392.200119.6.102");
        	element_receiverID.setAttribute("extension", Utility.FillZero(ReceiverExtension, 10));
            element_receiver.appendChild(element_receiverID);
            element.appendChild(element_receiver);
            
        }else if(InteractionType == 1 || InteractionType == 4 || InteractionType == 5)
        {
        	element_receiverID.setAttribute("root", "1.2.392.200119.6.103");
        	element_receiverID.setAttribute("extension", Utility.FillZero(ReceiverExtension, 8));
            element_receiver.appendChild(element_receiverID);
            element.appendChild(element_receiver);
            
        }else if(InteractionType == 3 || InteractionType == 6 || InteractionType == 8 ||
        		InteractionType == 11)
        {
        	element_receiverID.setAttribute("root", "1.2.392.200119.6.101");
        	
        	/* Modified 2008/06/08 �ጎ  */
			/* --------------------------------------------------- */
//        	element_receiverID.setAttribute("extension", String.valueOf(ReceiverExtension));
			/* --------------------------------------------------- */
        	/* 8 ���ɖ����Ȃ��ꍇ�ɂ́A�擪�� 0 �Ŗ��߂� */
        	String extension = String.valueOf(ReceiverExtension);
        	extension = JValidate.fillZero(extension, 8);
        	
			element_receiverID.setAttribute("extension", extension);
			/* --------------------------------------------------- */
        	
            element_receiver.appendChild(element_receiverID);
            element.appendChild(element_receiver);
        }
        
        Element element_serviceEventType = doc.createElement("serviceEventType");
        element_serviceEventType.setAttribute("code", ServiceEventType);
        element.appendChild(element_serviceEventType);
        
        Element element_total = doc.createElement("totalRecordCount");
        element_total.setAttribute("value", TotalRecordCount);
        element.appendChild(element_total);
        
        return element;
	}
	
	public boolean Check()
	{
		if(
				InteractionType < 0 ||
				SenderExtension < 0 ||
				ReceiverExtension < 0 ||
				ServiceEventType == null ||
				TotalRecordCount == null
		)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param Value ���
	 */
	public void setInteractionType(int Value)
	{
		if(1 <= Value && Value <= 13)
		{
			InteractionType = Value;
		}
	}
	private int InteractionType = -1;
	
	/**
	 * @param Value ���M���@��
	 */
	public void setSenderExtension(long Value)
	{
		SenderExtension = Value;
	}
	private long SenderExtension = -1;
	
	/**
	 * @param Value ���M��@��
	 */
	public void setReceiverExtension(long Value)
	{
		ReceiverExtension = Value;
	}
	private long ReceiverExtension = -1;
	
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
	private String ServiceEventType = null;
	
	/**
	 * @param Value ���t�@�C����
	 */
	public void setTotalRecordCount(int Value)
	{
		TotalRecordCount = String.valueOf(Value);
	}
	public void setTotalRecordCount(String Value)
	{
		TotalRecordCount = Value;
	}
	private String TotalRecordCount = null;
}

