package jp.or.med.orca.jma_tokutei.common.convert;

/*
 * SQL�Ƀt�B�[���h�̒l�𓊂���ۂɕK�v�ƂȂ镶����̕ϊ����s��
 */
public class JQueryConvert {
	private JQueryConvert()
	{
		
	}
	
	/*
	 *
	 */
	public static String queryConvert(String query)
	{
		String ret;
		
		if(query == null)
		{
			ret = new String("null ");
			return ret;
		}
		
		
		
		if(query.isEmpty())
		{
			ret = new String("null ");
			return ret;
		}
		
		ret = new String("'" + query + "' ");
		return ret;
	}

	public static String queryLikeConvert(String query)
	{
		String ret;
		
		if(query == null)
		{
			ret = new String("null ");
			return ret;
		}
		
		
		
		if(query.isEmpty())
		{
			ret = new String("null ");
			return ret;
		}
		
		ret = new String("'%" + query + "%' ");
		return ret;
	}
	
	/**
	 * �󕶎��ƃJ���}��ǉ�����B 
	 */
	public static String queryConvertAppendBlankAndComma(String query)
	{
		String ret;

		if(query == null)
		{
			ret = new String("'',");
			return ret;
		}
		
		if(query.isEmpty())
		{
			ret = new String("'',");
			return ret;
		}
		
		ret = new String("'" + query + "',");
		return ret;
	}
	public static String queryConvertAppendComma(String query)
	{
		String ret;

		if(query == null)
		{
			ret = new String("null,");
			return ret;
		}
		
		if(query.isEmpty())
		{
			ret = new String("null,");
			return ret;
		}
		
		ret = new String("'" + query + "',");
		return ret;
	}
}




//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

