package jp.or.med.orca.jma_tokutei.common.hl7.common;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class Utility {
	/**
	 * �X�֔ԍ����u###-####�v�Ƃ����`���ɏo�͂���
	 * @param Value �K���Ȍ`���̐���
	 */
	public static String ConvertPostalCode(String Value)
	{
		String Ret = "";
		
		// 4�����ڂɃn�C�t���������Ă��邩�ŏ�����U�蕪��
		if(Value.charAt(3) == '-')
		{
			Ret = Value;
		}else{
			String Left = Value.substring(0, 3);
			String Right = Value.substring(3, 7);
			
			Ret = Left + "-" + Right;
		}
		
		return Ret;
	}
	
	/**
	 * �J�����_�[�N���X����A�uyyyyMMdd�v�`���̓��t���o�͂���
	 * @param Value �J�����_�[�N���X
	 * @return ���t�̕�����
	 */
	public static String GetTimeString(Calendar Value)
	{
		// 1���������Ȃ�����ۂ߂�
		Value.set(Calendar.MONTH, Value.get(Calendar.MONTH) - 1);
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String Temp = format.format(Value.getTime());
		
		return Temp;
	}
	
	/**
	 * ���͂����l�ɑ΂��āA�K�v������������0�Ŗ��߂�
	 * @param Value �l
	 * @param Figure ����
	 * @return 0�Ŗ��߂�������
	 */
	public static String FillZero(long Value,int Figure)
	{
		DecimalFormat df = new DecimalFormat();
		String FigurePattern = "";
		
		// �K�v�����̃p�^�[���𐶐�
		for(int i = 0 ; i < Figure ; i++)
		{
			FigurePattern += "0";
		}
		
		df.applyLocalizedPattern(FigurePattern);
		return df.format(Value);
	}
	
	/* Added 2008/04/04 �ጎ  */
	/* --------------------------------------------------- */
	public static String FillZero(String Value, int digit) {
		
		String valueString = "";
		String formattedValue = null;
		
		if (Value != null && ! Value.isEmpty()) {
			valueString = Value.trim();
			
			long valueLong = 0;
			if (Value != null && ! Value.isEmpty()) {
				valueLong = Long.parseLong(valueString);
			}

			formattedValue = Utility.FillZero(valueLong, digit);
		}
		else {
			for (int i = 0; i < digit; i++) {
				formattedValue += "0";
			}
		}
		
		return formattedValue;
	}
	/* --------------------------------------------------- */
	
	/**
	 * @return ���݂̎��Ԃ�HL7�Ŏg�p����t�H�[�}�b�g�Ŏ擾����
	 */
	public static String NowDate()
	{
		// ���t�̎擾
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
	}
	
	/**
	 * ������S�p�ɕϊ�����
	 * @param str �������܂񂾕�����
	 * @return ������
	 */
	public static String NumberUpper(String str)
	{
		StringBuffer Ret = new StringBuffer(str);
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			if ('0' <= c && c <= '9')
			{

				Ret.setCharAt(i, (char)(c - '0' + '�O'));
			}
		}
		return Ret.toString();
	}
	
	/**
	 * �����𔼊p�ɕϊ�����
	 * @param str �������܂񂾕�����
	 * @return ������
	 */
	public static String NumberLower(String str)
	{
		StringBuffer Ret = new StringBuffer(str);
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			if ('�O' <= c && c <= '�X')
			{

				Ret.setCharAt(i, (char)(c - '�O' + '0'));
			}
		}
		return Ret.toString();
	}
	
	/**
	 * @param Value ###.### �`���̐���
	 * @return ###### �`���̐���
	 * �p�[�Z���g�̕\�L��ύX����
	 */
	public static String getPercentFormat(double Value)
	{
		Value = Value * 1000;
		return Utility.FillZero((long)Value, 6);
	}
	
	/**
	 * �X�L�[�}������XML�t�@�C�������؂���
	 * @param XmlFileName XML�t�@�C��
	 * @param SchemaFileName �X�L�[�}�̃t�@�C��
	 */
	public static void checkXmlSchema(String XmlFileName,String SchemaFileName)
	{
		// XML�X�L�[�}�̌���
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
	        factory.setValidating(true);
	        factory.setNamespaceAware(true);
	        factory.setAttribute( 
	        		"http://java.sun.com/xml/jaxp/properties/schemaLanguage", 
	        		"http://www.w3.org/2001/XMLSchema"); 
	        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
	        		SchemaFileName); 
	        factory.setAttribute("http://apache.org/xml/features/validation/schema",true);
	        
            DocumentBuilder builder = factory.newDocumentBuilder();
	        builder.setErrorHandler(new MyHandler(XmlFileName));
	        
	        builder.parse(XmlFileName);
		}catch(Exception e){
			throw new RuntimeException();
		}
	}
}

/**
 * �X�L�[�}���؎��̃G���[�n���h��
 */
class MyHandler implements ErrorHandler
{
	public MyHandler(String FileName)
	{
		this.FileName = FileName;
	}
	
	private String FileName = null;
	
    public void warning(SAXParseException e)
    {
        System.out.println("�x�� : " + FileName + " " + e.getLineNumber() +"�s�� " + e.getColumnNumber());
        System.out.println(e.getLocalizedMessage());
       // e.printStackTrace();
    }
    public void error(SAXParseException e)
    {
        System.out.println("�G���[ : " + FileName + " " + e.getLineNumber() +"�s�� " + e.getColumnNumber());
        System.out.println(e.getLocalizedMessage());
       // e.printStackTrace();
    }
    public void fatalError(SAXParseException e)
    {
        System.out.println("�[���ȃG���[ : " + FileName + " " + e.getLineNumber() +"�s�� " + e.getColumnNumber());
        System.out.println(e.getLocalizedMessage());
       // e.printStackTrace();
    }
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

