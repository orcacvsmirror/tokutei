package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * �Ǝ��t�H�[�}�b�g�̃f�[�^��ǂݍ��ރN���X
 */
public class JOriginalFormatDataReader {
	
	/**
	 * @param FileName �ǂݍ��ރt�@�C��
	 * @return ��f�҂��Ƃ̃��X�g
	 */
	public static Vector<JOriginalFormatData> Read(String FileName) throws Exception
	{
		// ���p�����񂵂����邱�Ƃ������Ɖ��肵�Ă��܂��B
		FileReader input = new FileReader(FileName);
		
		Vector<JOriginalFormatData> ReturnItems = new Vector<JOriginalFormatData>();
		JOriginalFormatData Item = null;

		while(true)
		{
			// Buffer�̒��g�͍��ڂ����O�ɏ���������悤�ɂ���B
			char[] Buffer = new char[256];
			if(input.read(Buffer,0,2) <= 0)
			{
				break;
			}
			
			String Type = new String(Buffer).trim();
			if(Type.equals("A1"))
			{
				if(Item != null)
				{
					ReturnItems.add(Item);
				}
				Item = new JOriginalFormatData();
				
				Buffer = new char[256];
				input.skip(154);
				input.read(Buffer,0,20);
				Item.Name = new String(Buffer).trim();
				
				// �����̕ϊ��Ō��̃f�[�^�ɔN�̍��ڂ�2�������Ȃ����߁A
				// �s����N�����\������B
				Buffer = new char[256];
				input.skip(6);
				input.read(Buffer,0,6);
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				Date date = df.parse(new String(Buffer).trim());
				
				SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
				Item.BirthDay = df2.format(date);
				
				input.skip(68);
			}
			
			if(Type.equals("A2"))
			{
				JOriginalFormatDataBody BodyData = new JOriginalFormatDataBody();
				
				// �����Z���^�[�R�[�h
				Buffer = new char[256];
				input.read(Buffer,0,5);
				BodyData.KensaCenterCode = new String(Buffer).trim();

				// ���ʒl
				Buffer = new char[256];
				input.read(Buffer,0,8);
				BodyData.Kekka = new String(Buffer).trim();
				
				Item.Body.add(BodyData);

				// �{�f�B���̌㔼���ɂ��Ă͑��݂��Ȃ��\��������̂ŁA����ɑ΂��鏈���B
				Buffer = new char[256];
				input.skip(114);
				input.read(Buffer,0,5);
				
				if(!(new String(Buffer)).trim().equals(""))
				{
					JOriginalFormatDataBody BodyData2 = new JOriginalFormatDataBody();
					
					BodyData2.KensaCenterCode = new String(Buffer).trim();
	
					Buffer = new char[256];
					input.read(Buffer,0,8);
					BodyData2.Kekka = new String(Buffer).trim();
					
					Item.Body.add(BodyData2);
					input.skip(114);
				}else{
					input.skip(122);
				}
			}
		}

		ReturnItems.add(Item);
		
		return ReturnItems;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

