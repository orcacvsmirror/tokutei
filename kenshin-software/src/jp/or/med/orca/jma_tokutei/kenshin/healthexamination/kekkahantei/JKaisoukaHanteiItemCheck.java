package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei;

import java.io.IOException;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * �K�w�����荀�ڃ`�F�b�N
 */
public class JKaisoukaHanteiItemCheck {
	private static void Init()
	{
		reader = new JCSVReaderStream();
		try {
			reader.openCSV(JApplication.KAISOUHANTEI_CSV_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		csvItems = reader.readAllTable();
	}

	// Proxy�Ƃ��ĊǗ�����
	private static JCSVReaderStream reader = null;
	private static Vector<Vector<String>> csvItems = null;

	public final static int KESSON = -1;
	public final static int FALSE = 0;
	public final static int TRUE = 1;

	private static boolean KessonFlag = false;

	public static boolean IsKesson()
	{
		return KessonFlag;
	}

	public static int CheckItem(String koumokuCode,String gender,double value)
	{
		if(reader == null)
		{
			Init();
		}

		if(value == -1)
		{
			KessonFlag = true;
			return KESSON;
		}

		/* �P�ʂ͑��݂��Ȃ��ꍇ������B
		 * ����āA�����ł͒P�ʂ̗L���ɂ�錟�؂��s��Ȃ��B */
		// �Y�����鍀�ڂ�T��
		double kijun = -1;
		String tanni = null;
		boolean success = false;

		if (csvItems != null) {
			success = true;
		}

		if (success) {
			for(int i = 0 ; i < csvItems.size() ; i++)
			{
				Vector<String> csvItem = csvItems.get(i);
				if (csvItem != null && csvItem.size() == 4) {

					String csvCode = csvItem.get(0);
					String csvGender = csvItem.get(1);

					if(csvCode.equals(koumokuCode) && csvGender.equals(gender))
					{
						try
						{
							String kijunchi = csvItem.get(2);
							kijun = Double.valueOf(kijunchi);
							tanni = csvItem.get(3);

							if (tanni == null) {
								tanni = "";
							}

						}catch(Exception e)
						{
							throw new RuntimeException();
						}
					}
				}
			}

			if(tanni == null || kijun == -1)
			{
				throw new RuntimeException();
			}

			if(tanni.equals("�ȏ�"))
			{
				if(kijun <= value)
				{
					return TRUE;
				}else
				{
					return FALSE;
				}
			}
			if(tanni.equals("����"))
			{
				if(kijun > value)
				{
					return TRUE;
				}else
				{
					return FALSE;
				}
			}
			if(tanni.equals("�ȉ�"))
			{
				if(kijun >= value)
				{
					return TRUE;
				}else
				{
					return FALSE;
				}
			}
			if(tanni.equals(""))
			{
				if(kijun == value)
				{
					return TRUE;
				}else
				{
					return FALSE;
				}
			}
		}

		throw new RuntimeException();
	}
}
