package jp.or.med.orca.jma_tokutei.common.validate;

import java.io.*;
import java.math.BigDecimal;

import java.text.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;

/**
 * �����񌟏؁A�ϊ��p�N���X
 * �N���X�R�����g�ǉ�
 */
public class JValidate {
	public static final String USER_KENGEN_IPANUSER = "��ʃ��[�U";
	public static final String USER_KENGEN_KANRISYA = "�Ǘ���";

	private static org.apache.log4j.Logger logger = Logger.getLogger(JValidate.class);

	/* unicodeTO */
	public static String encodeUNICODEtoConvert(String str){
		 StringBuffer ret = new StringBuffer();
		 char c = 0x0000;
		 for(int i=0; i < str.length(); i++){
		  c = str.charAt(i);
		  switch(c){
		  case 0xff5e:    // �g��������
		   c = 0x30Fc;
		   break;
		  case 0xff0d:    // �n�C�t��
		   c = 0x30Fc;
		   break;
		  }
		  ret.append(c);
		 }
		 return ret.toString();
		}

	/**
	 * �w�肳�ꂽ�����P������������
	 * @param str		�ΏۂƂȂ镶����
	 * @param target	���������������P���̔z��
	 * @return			�w�蕶��������̕�����
	 */
	public static String eliminateCharacter(String str,char[] target)
	{
		String ret = "";
		boolean isEliminate = false;

		for(int i = 0;i < str.length(); i++ )
		{
			for(int j = 0;j < target.length;j++ )
			{
				if( str.charAt(i) == target[j] )
					isEliminate = true;
			}

			if( isEliminate )
			{
				isEliminate = false;
			}else{
				ret += str.charAt(i);
			}
		}

		return ret;
	}


	/**
	 * ������̐擪�ɂO��ǉ����Ďw�茅���ɂ���
	 * @param str   �O���ߑΏە�����
	 * @param digit �O�Ŗ��߂���̌���
	 * @return
	 */
	public static String fillZero(String str,int digit)
	{
		StringBuffer ret = new StringBuffer();
		ret.append(str);

		if( digit < str.length())
		{
			return null;
		}

		for( int i = str.length() ; i < digit ; i++ )
		{
			ret.insert(0,"0");
		}

		return ret.toString();
	}

	/**
	 * �S�p���ǂ����𒲂ׂ�
	 */
	public static boolean isAllZenkaku(String str)
	{
		byte[] bytes = null;

		// ���������΍�ɁA�ŏ��Ɂu?�v���`�F�b�N����
		for(int i = 0 ; i < str.length() ; i++)
		{
			if(str.charAt(i) == '?')
			{
				return false;
			}
		}

		try {
			bytes = str.getBytes("EUC-JP");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		for(int i = 0 ; i < bytes.length ; i++ )
		{
			// �ꕔ�̕�����EUC-JP�ɑ��݂��Ȃ��̂ŁA���̎��͏������΂�
			if(bytes[i] == 63)
			{
				continue;
			}

			if(bytes[i] >= 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * ���p���ǂ����𒲂ׂ�B
	 * �Ώە������ UTF-8 �ŃG���R�[�h����Ă��邱�Ƃ�O��Ƃ���B
	 * UTF-8 �ȊO�̏ꍇ�́AisAllHankaku(String str, String charSet) ��
	 * �g�p����B
	 */
	public static boolean isAllHankaku(String str)
	{
		return isAllHankaku(str, "UTF-8");
	}

	/* isAllHankaku(String str) �𕶎��R�[�h�ɂ��Ĕėp���������\�b�h�B */
	/**
	 * ���p���ǂ����𒲂ׂ�
	 */
	public static boolean isAllHankaku(String str, String charSet)
	{
		byte[] bytes = null;
		try {
			bytes = str.getBytes(charSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		// �z��̗v�f���ƈ����̕�������������������A�S�����p�ƂȂ�
		if(str.length() == bytes.length)
		{
			return true;
		}else{
			return false;
		}
	}

	/**
	 * �������ǂ����𒲂ׂ�
	 */
	public static boolean isNumber(String str)
	{
		for(int i = 0 ; i < str.length() ; i++)
		{
			char c = str.charAt(i);

			if(c < '0' || '9' < c)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * �������ǂ����𒲂ׂ�
	 */
	public static boolean isSyousu(String str)
	{
		boolean hasPoint = false;
		for(int i = 0 ; i < str.length() ; i++)
		{
			char c = str.charAt(i);
			if( c == '.' )
			{
				if( hasPoint )
				{
					return false;
				}else{
					hasPoint = true;
				}
			}

			if(!(('0' <= c && c <= '9') || c == '.' || c == '-' || c == '+' ))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * �o�C�g���`�F�b�N
	 * @param ���蕶����
	 * @param �o�C�g��
	 * @return boolean
	 */
    public static boolean checkByte(String strbt,int byt)
    {
        byte[] bytes = strbt.getBytes();
        int len = bytes.length;

        if (len != byt)
        {
            return false;
        }
        return true;
    }

	/**
	 * �ő�l�`�F�b�N
	 * @param ���蕶����
	 * @param �o�C�g��
	 * @return boolean
	 */
	public static boolean checkMaxArea(String strbt,int max)
    {
        byte[] bytes = strbt.getBytes();
        int len = bytes.length;

        if (len > max)
        {
            return false;
        }
        return true;
    }

	/**
	 * �ő�l�`�F�b�N
	 * @param ���蕶����
	 * @param �o�C�g��
	 * @return boolean
	 */
	public static boolean checkMaxLimit(String strbt,int max)
    {
        byte[] bytes = strbt.getBytes();
        int len = bytes.length;

        if (len > max)
        {
            return false;
        }
        return true;
    }

	/**
	 * ���l�f�[�^�̌����`�F�b�N
	 * @param ���茅��
	 * @return boolean
	 */
	public static boolean checkDigitNumber( String str, int iPlace )
	{
		int iCount = 0;

		for( int i=0; i<str.length(); ++i )
		{
			char ch = str.charAt( i );

			if( ch < '0' || ch > '9' )
			{
				return false;
			}
			else
			{
				iCount ++;
			}
		}

		if( iCount != iPlace )
		{
			return false;
		}

		return true;
	}

	/**
	 * �U���p�[�Z���g�\������R���p�[�Z���g�\���ւƕϊ�����
	 */
	public static String to3DigitCode(String str)
	{
		String ret = "";

		int length = str.length();
		if( length <= 3 )
		{
			return "";
		}

		ret += str.substring(0, length - 3);
		ret += ".";
		ret += str.substring(length - 3, length - 1);

		return ret;
	}

	/*
	 * [������]int�ɃL���X�g����.�ȉ��S���ȏ��h��
	 */
	public static String toSixDigitPercent(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		double value;
		String ret;

		if( !isSyousu(str) )
			return null;

		value = Double.valueOf(str);
		value *= 1000;

		ret = String.valueOf(value);
		ret = ret.substring(0, ret.length() - 2);

		//�O���߂���O�̌������`�F�b�N
		if( ret.length() > 6 )
			return null;

		//�O�Ŗ��߂ĂU�P�^�ɂ���
		ret = JValidate.fillZero(ret,6);

		if( ret == null )
			return  null;

		return ret;
	}

	/*
	 * ���t�t�H�[�}�b�g�̌���
	 */
	public static String validateDate(String str,boolean startflg,boolean perfectflg)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		String temp;
		// edit s.inoue 2009/10/27
		temp = JCalendarConvert.ADvalidate(str,startflg,perfectflg);

		if( temp == null ){
			return null;
		}else{
			return temp;
		}
	}
	/*
	 * ���t�t�H�[�}�b�g�̌���(�a��A����Ή�)
	 */
	public static String validateDate(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		String temp;
		// edit s.inoue 2009/10/27
		temp = JCalendarConvert.JCtoAD(str);

		if( temp == null ){
			return null;
		}else{
			return temp;
		}
	}
	/**
	 * ������t�̑Ó����`�F�b�N���s���܂��B
	 * �w�肵�����t������iyyyy/MM/dd or yyyy-MM-dd�j��
	 * �J�����_�[�ɑ��݂��邩�ǂ�����Ԃ��܂��B
	 * @param strDate �`�F�b�N�Ώۂ̕�����
	 * @return ���݂�����t�̏ꍇtrue
	 */
	public static boolean validateCDate(String strDate) {
	    // ���l�݂̂ɕϊ�
		strDate = strDate.replace('-', '/');
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    // ���t/������͂������ɍs�����ǂ�����ݒ肷��B
	    format.setLenient(false);
	    try {
	        format.parse(strDate);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}

	/*
	 * �d�b�ԍ��t�H�[�}�b�g�̌��؂��s��
	 * @return str �������d�b�ԍ��Ƃ��Đ�������l
	 * @return null �������d�b�ԍ��Ƃ��ĕs����null
	 */
	public static String validatePhoneNumber(String str)
	{
		char[] target = {'-'};

		//��l��������
		if ( str.isEmpty() )
			return str;

		//�n�C�t������������
		str = JValidate.eliminateCharacter(str, target);

		//���l�݂̂��ǂ����̃`�F�b�N
		if( !JValidate.isNumber(str) )
			return null;

		if( str.length() > 11)
		{
			return null;
		}

		return str;
	}


	/*
	 * ORCAID�̌��؂��s��
	 */
	public static String validateORCAID(String str)
	{
		String retValue = null;

		//��l��������
		if ( str.isEmpty() ){
			return str;
		}

		if ( str != null && ! str.isEmpty() ){
			if (str.getBytes().length <= 20) {
				retValue = str;
			}
		}

		return retValue;
	}

	/*
	 * ��f�������ԍ��̌��؂��s��
	 */
	public static String validateJyushinkenID(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		// edit ver2 s.inoue 2009/06/24
		if( !JValidate.isNumber(str))
		{
			return null;
		}

		if( str.length() != 11)
		{
			return null;
		}

		return str;
	}

	/*
	 * ����ԍ��̌��؂��s��
	 */
	public static String validateNayoseNo(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isNumber(str))
		{
			return null;
		}

		if( str.length() != 12)
		{
			return null;
		}

		return str;
	}

	/*
	 * ��f�������ԍ��̌��؂��s��
	 */
	public static String validateJyushinkenIDforSearch(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 11)
		{
			return null;
		}

		return str;
	}

	/*
	 * �ی��Ҕԍ��̌��؂��s��
	 */
	public static String validateHokenjyaNumber(String str)
	{
		String ret;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 8 )
		{
			return null;
		}

		if( !JValidate.isNumber(str) )
		{
			return null;
		}

		ret = fillZero(str, 8);

		return ret;
	}

	/*
	 * �ی��Җ��̂̌��؂��s��
	 */
	public static String validateHokenjyaName(String str)
	{
		String retValue = null;

		if( str.getBytes().length <= 200 )
		{
			retValue = str;
		}

		return retValue;
	}

	// add s.inoue 2010/01/12
	/*
	 * �ی��җ���ԍ��̌��؂��s��
	 */
	public static String validateHokenjyaHistoryNumber(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return null;

		if( !JValidate.isNumber(str) )
		{
			return null;
		}

		return str;
	}
	/*
	 * ��ی��ҏؓ��L���̌��؂��s��
	 */
	public static String validateHihokenjyaKigou(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		//�S�p�ȊO�������Ȃ�
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}

		if( str.getBytes().length > 40)
		{
			return null;
		}

		return str;
	}

	/*
	 * ��ی��ҏؓ��ԍ��̌��؂��s��
	 */
	public static String validateHihokenjyaNumber(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		//�S�p�ȊO�������Ȃ�
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}

		if( str.getBytes().length > 40)
		{
			return null;
		}

		return str;
	}


	/*
	 * �����i�����j�̌��؂��s��
	 */
	public static String validateNameKanji(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !isAllZenkaku(str) )
			return null;
		if( str.getBytes().length > 100)
		{
			return null;
		}

		return str;
	}

	/*
	 * �����i�J�i�j�̌��؂��s��
	 */
	public static String validateNameKana(String str)
	{
		// del s.inoue 2009/09/30
		// char target[] = {' ','�@'};

		//��l��������
		if ( str.isEmpty() )
			return str;

		//�X�y�[�X����������
		// del s.inoue 2009/09/30
		// str = JValidate.eliminateCharacter(str,target);

		if( !isAllZenkaku(str) )
			return null;

		if( str.getBytes().length > 100 )
		{
			return null;
		}

		return str;
	}

	/*
	 * �����i�ʏ́j�̌��؂��s��
	 */
	public static String validateNameTsusyou(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 100)
		{
			return null;
		}

		return str;
	}

	/*
	 * ���ʂ̌��؂��s��
	 */
	public static String validateSex(String str)
	{
		String temp = null;

		if ( str.isEmpty() )
			return str;

		if( str.equals("1") )
		{
			return str;
		}

		if(str.equals("2") )
		{
			return str;
		}
		return temp;
	}

	/*
	 * ���N�����̌���
	 */
	public static String validateSendSeinengapi(String str)
	{
		boolean JorAD = false;

		try {
			//��l��������
			if ( str.isEmpty() )
				return null;

			String temp = "";

			JorAD = JCalendarConvert.JCorAD(str);

			// �ĕϊ�����(�a�����)
			if (JorAD){
				str = JCalendarConvert.JCtoAD(str);
				if( str == null ){
					return null;
				}
			}

			if( !isNumber(str) )
				return null;

			if ( str.length() != 8 )
				return null;

			if (!validateCDate(str))
				return null;
		}catch(Exception ex){
			return null;
		}
		return str;
	}

	/*
	 * �X�֔ԍ��̌��؂��s��
	 */
	public static String validateZipcode(String str)
	{
		String ret;
		char[] target = { '-' };

		//��l��������
		if ( str.isEmpty() )
			return str;

		//�n�C�t������������
		ret = JValidate.eliminateCharacter(str, target);

		//�����݂̂��ǂ����`�F�b�N����
		if( !JValidate.isNumber(ret) )
		{
			return null;
		}

		if ( ret.length() != 7 )
		{
			return null;
		}

		return ret;
	}

	/*
	 * �Z���̌��؂��s��
	 */
	public static String validateAddress(String str)
	{
		char target[] = {' ','�@'};
		//��l��������
		if ( str.isEmpty() )
			return str;

		//�X�y�[�X����������
		str = JValidate.eliminateCharacter(str,target);

		//�S�p�݂̂��ǂ����`�F�b�N���s��
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}
		if( str.getBytes().length > 200 )
		{
			return null;
		}

		return str;
	}

	/*
	 * �n�ԕ����̌��؂��s��
	 */
	public static String validateChiban(String str)
	{
		char target[] = {' ','�@'};
		//��l��������
		if ( str.isEmpty() )
			return str;

		//�X�y�[�X����������
		str = JValidate.eliminateCharacter(str,target);

		//�S�p�݂̂��ǂ����`�F�b�N���s��
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}

		if( str.getBytes().length > 200 )
		{
			return null;
		}

		return str;
	}

	/*
	 * ���f�@�֏Z���̌��؂��s��
	 */
	public static String validateKikanAddress(String str){

		//��l��������
		if ( str.isEmpty() )
			return str;

		/* �S�p�X�y�[�X���폜����B */
		str = str.replaceAll("�@", "");

		// edit s.inoue 2009/09/24
		//�S�p�݂̂��ǂ����`�F�b�N���s��
		if( !JValidate.isAllZenkaku(str) )	{
			return null;
		}

		return str;
	}

	/*
	 * ���f�@�֒n�ԕ����̌��؂��s��
	 */
	public static String validateKikanChiban(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		/* �S�p�X�y�[�X���폜����B */
		str = str.replaceAll("�@", "");

		if( !isAllZenkaku(str) )
			return null;

		return str;
	}

	/*
	 * ���f�@�֏Z���ƒn�ԕ����̌��؂��s��
	 */
	public static boolean validateKikanAddressAndChiban(String address, String chiban)
	{
		boolean retValue = false;

		String addressForValidate = null;
		String chibanForValidate = null;

		/*
		 *  null �̏ꍇ�́A�󕶎��ɒu��������B
		 * �S�p�X�y�[�X���폜����B
		 */
		if (address == null) {
			addressForValidate = "";
		}
		else {
			addressForValidate = address.replaceAll("�@", "");
		}

		if (chiban == null) {
			chibanForValidate = "";
		}
		else {
			chibanForValidate = chiban.replaceAll("�@", "");
		}

		/* �o�C�g���� 80 �ȓ��Ȃ� OK�i�j */
		int byteSize = addressForValidate.getBytes().length + chibanForValidate.getBytes().length;
		if (byteSize <= 80) {
			retValue = true;
		}

		return retValue;
	}

	/*
	 * EMAIL�̌��؂��s��
	 */
	public static String validateEMAIL(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 100 )
		{
			return null;
		}

		boolean hasDot = false;
		boolean hasAt = false;

		for(int i = 0 ; i < str.length() ; i++)
		{
			char c = str.charAt(i);

			if(!(('0' <= c && c <= '9') || ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') ||
					c == '@' || c == '.' || c == '-' || c == '_'))
			{
				return null;
			}

			if(c == '@')
			{
				hasAt = true;
			}
			if(hasAt == true && c =='.')
			{
				hasDot = true;
			}
		};

		if(hasAt == true && hasDot == true)
		{
			return str;
		}
		return null;
	}

	/*
	 * �_����܂Ƃߋ@�֖��̌��؂��s��
	 */
	public static String validateTorimatomeName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 100 )
		{
			return null;
		}

		return str;
	}

	/*
	 * �x����s�@�֔ԍ��̌��؂��s��
	 */
	public static String validateDaikouNumber(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() != 8)
		{
			return null;
		}

		if( !JValidate.isNumber(str) )
		{
			return null;
		}

		return str;
	}

	/*
	 * �x����s�@�֖��̂̌��؂��s��
	 */
	public static String validateDaikouName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
		{
			return null;
		}

		return str;
	}

	/*
	 * �������S���z
	 */
	public static String validateMadoguchiAmount(String str)
	{
		String ret;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if( str.length() > 6 )
		{
			return null;
		}

		ret = JValidate.fillZero(str, 6);

		return ret;
	}

	/*
	 * �������S�����̌���
	 */
	public static String validateMadoguchiPercent(String str)
	{
		return toSixDigitPercent(str);
	}

	/*
	 * �������S���v���z�̌���
	 */
	public static String validateMadoguchiAmountTotal(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if ( str.length() > 9 )
		{
			return null;
		}

		return str;
	}

	/*
	 * ���̑��̌��f�̕��S�z�̌���
	 */
	public static String validateMadoguchiSonota(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if ( str.length() > 9 )
		{
			return null;
		}

		return str;
	}

	/*
	 * ���[�U�[���̌���
	 */
	public static String validateUserName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 20 )
			return null;

		return str;
	}

	/*
	 * �p�X���[�h�̌���
	 */
	public static String validatePassword(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if ( !isAllHankaku(str) ){
			return null;
		}

		if( str.length() > 20 )
			return null;

		return str;
	}

	/*
	 * ���f�@�֔ԍ��̌���
	 */
	public static String validateKikanNumber(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() != 10 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * ���t���@�ւ̌���
	 * ���l�A�P�O�P�^�ȓ��Ƃ��Ď����i�O�l�߂Ȃ��j
	 */
	public static String validateSendSourceKikan(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return null;

		if( str.getBytes().length > 10 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * ��Ë@�֖��̂̌���
	 */
	public static String validateKikanName(String str)
	{
		String retValue = null;

		if ( ! str.isEmpty() ){
			String checkStr = str.replaceAll("�@", "");

			/* �S�p�݂̂��ǂ����`�F�b�N���s�� */
			if( JValidate.isAllZenkaku(checkStr) && checkStr.length() <= 20)	{
				retValue = str;
			}
		}

		return retValue;
	}

	/*
	 * ���[�U�[�����̌���
	 */
	public static String validateUserKengen(String str)
	{
		String ret = "";

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals(USER_KENGEN_KANRISYA))
		{
			ret = "1";
			return ret;
		}

		if( str.equals(USER_KENGEN_IPANUSER))
		{
			ret = "2";
			return ret;
		}

		return null;
	}

	/*
	 * ��Õی��ҋL���̋L��
	 */
	public static String validateHokenjyaKigou(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 80 )
		{
			return null;
		}

		return str;
	}

	/*
	 * ���t�����i�{�l or �Ƒ��E�O�� or ���@�j�̌���
	 */
	public static String validateKyuuhuPercent(String str)
	{
		return toSixDigitPercent(str);
	}

	/*
	 * �ϑ����P���敪�̌���
	 */
	public static String validateItakuKubun(String str)
	{
		String ret = null;

		// add s.inoue 2009/10/01
		if (str.indexOf(":", 0) > 0) {
			str  = str.substring(str.indexOf(":", 0)+1);
		}

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("��") )
		{
			ret = "1";
		}
		if( str.equals("�W�c") )
		{
			ret = "2";
		}

		return ret;
	}

	// add s.inoue 2010/03/03
	/*
	 * �ϑ����P���敪�̌���
	 */
	public static String validateItakuKubunCode(String str)
	{
		String ret = "";

		if( str.isEmpty() || str.equals("1") || str.equals("2") )
			ret = str;

		return ret;
	}
	/*
	 * �P������
	 * �P(��{,�ڍ�,�ǉ�) or �Q(�l�ԃh�b�N) �̂�
	 */
	public static String validateTankaHantei(String str)
	{
		if( str.equals("1") || str.equals("2") )
			return str;

		return null;
	}
	/*
	 * �e�����̒P���̌���
	 */
	public static String validateKensaTanka(String str)
	{
		// edit s.inoue 2009/11/12
		// final String KTanka = "0";

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 9 )
		{
			return null;
		}

		if( !isNumber(str) )
		{
			return null;
		}

		return str;
	}

	/*
	 * �e�����P���̍��v�̌���
	 */
	public static String validateKensaTankaTotal(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 9 )
		{
			return null;
		}

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * �n���A���A�S�d�}�����R�[�h�̌���
	 */
	public static String validateKensaCode(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() != 1 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * �p�^�[��No�̌���
	 */
	public static String validatePatternNumber(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 3 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * �p�^�[�����̂̌���
	 */
	public static String validatePatternName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * ���l�̌���
	 */
	public static String validateNote(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}


	/*
	 * ���ڃR�[�h�̌���
	 * ���p�̂�
	 */
	public static String validateKensaKoumokuCode(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() != 17 )
			return null;

		if( !isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ���ږ��̂̌���
	 * �S�Ă̕���
	 */
	public static String validateKensaKoumokuName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * ���茒�f�K�{�t���O�̌���
	 * �P or �Q or 3 �̂�
	 */
	public static String validateKenshinHisuFlag(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("1") || str.equals("2") || str.equals("3") )
			return str;

		return null;
	}

	/*
	 * ���茒�f��sex����
	 * �P or �Q or 3 �̂�
	 */
	public static boolean validateSexFlag(String str)
	{
		boolean sexFlag = false;

		if( str.isEmpty() || str.equals("1") || str.equals("2") )
			sexFlag = true;

		return sexFlag;
	}
	/*
	 * ���ڏ㉺���A��l�͈́i�j���j�i�����j�i���ʁj�l�̌���
	 * �����֌W������
	 */
	public static String validateKensaResultLimit(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 10 )
			return null;

		if( !isSyousu(str) )
			return null;

		return str;
	}

	/*
	 * �����l�P�ʂ̌���
	 */
	public static String validateKensaUnit(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 10 )
			return null;

		return str;
	}

	/*
	 * �����l��l�͈͂̌���
	 */
	public static String validateKensaKijyunHanni(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 20 )
			return null;

		return str;
	}

	/*
	 * XML�������@�R�[�h�̌���
	 */
	public static String validateKensaCd(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 10 )
			return null;

		return str;
	}

	/*
	 * �������@�̌���
	 */
	public static String validateKensaHouhou(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * �����Z���^�[�R�[�h�̌���
	 */
	public static String validateKensaCenterCode(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 20 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * �����Z���^�[���̂̌���
	 */
	public static String validateKensaCenterName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * �����Z���^�[�Ǝ����ڃR�[�h�̌���
	 */
	public static String validateKensaCenterKoumokuCode(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 5 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	// add s.inoue 2009/09/28
	public static String DecimalFormatValue(String limitVale,String format){
		//�����_�ȉ��̐����̐�

		int lowNum = format.length() - format.indexOf(".");

		BigDecimal bd = new BigDecimal(limitVale);
		bd.setScale(lowNum, BigDecimal.ROUND_HALF_UP);

		DecimalFormat decimal = new DecimalFormat(format);
		String result = decimal.format(bd.doubleValue());

		if (result.startsWith(".")) {
			result =  "0" + result;
		}
		return result;
	}

	/*
	 * DecimalFormat�ɑΉ����������̂ݎ󂯕t��
	 */
	public static String validateKensaKekkaDecimal(String value,String format)
	{
		char[] target = {'.'};
		char ch;
		boolean pointFlag = false;
		int lowNum = 0;
		String result = null;

		//�����l�̋�l�͋�����
		if ( value.isEmpty() )
			return value;

		//�t�H�[�}�b�g�w��q�̋�l�͋����Ȃ�
		if ( format.isEmpty() )
			return null;

		//�����l��.�Ɛ��l�݂̂��ǂ����`�F�b�N����
		String tmp = JValidate.eliminateCharacter(value, target);
		if( !JValidate.isNumber(tmp) )
		{
			return null;
		}


		//�t�H�[�}�b�g�w��q��.��0��#�݂̂��ǂ����̃`�F�b�N
		for( int i = 0; i < format.length() ;i++ )
		{
			ch = format.charAt(i);

			//.�����ȏ�o�����Ȃ����ǂ���
			if( ch == '.' )
			{
				if( pointFlag == true)
				{
					return null;
				}
				pointFlag = true;
			}

			if( ch != '0' && ch != '.' && ch != '#' )
			{
				return null;
			}
		}

		//�����_�ȉ��̐����̐�
		lowNum = format.length() - format.indexOf(".");

		BigDecimal bd = new BigDecimal(value);
		bd.setScale(lowNum, BigDecimal.ROUND_HALF_UP);

		DecimalFormat decimal = new DecimalFormat(format);
		result = decimal.format(bd.doubleValue());

		if (result.startsWith(".")) {
			result =  "0" + result;
		}

		return result;
	}

	/*
	 * ������ŕ\�킳��錟�����ʂ̌���
	 */
	public static String validateKensaKekkaText(String value,String format)
	{
		// edit s.inoue 2009/10/15
		if (value == null)
			return "";

		//��l��������
		if ( value.isEmpty() )
			return value;

		//�t�H�[�}�b�g�w��q�̋�l�͋����Ȃ�
		if ( format.isEmpty() )
			return null;

		if( value.getBytes().length > Integer.valueOf(format) )
			return null;

		// edit s.inoue 2009/10/14
		if (!isAllZenkaku(value))
			return null;

		return value;
	}

	/*
	 * �R�[�h�l�ŕ\�킳��錟�����ʂ̌���
	 */
	public static String validateKensaKekkaCode(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isNumber(str) )
			return null;

		return str;
	}

	/*
	 * �R�����g�̌���
	 */
	public static String validateComment(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;
		if( str.getBytes().length > 256 )
			return null;

		return str;
	}

	/*
	 * �������ʂ�H/L����
	 * ���āFH or L
	 * H or L �ꌅ
	 */
	public static String validateHLKubun(String str)
	{
		String temp = null;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("L") )
			temp = "1";

		if( str.equals("H") )
			temp = "2";

		if( str.equals("!") )
			temp = "3";

		return temp;
	}

	/*
	 * ���{�敪�̌���
	 * ���āF0 or 1 or 2
	 * 0 or 1 or 2 �ꌅ
	 */
	public static String validateJisiKubun(String str)
	{
		// ��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("0") || str.equals("1") || str.equals("2") )
			return str;

		return null;
	}
	/* ���{�敪 */
	public static String validateJisiKubunText(String str)
	{

		if( str.equals(JApplication.jishiKBNTable[0]) ||
				str.equals(JApplication.jishiKBNTable[1]) ||
					str.equals(JApplication.jishiKBNTable[2]) )
			// 1�����ڂ�ԋp
			return str.substring(0, 1);

		return null;
	}

	/*
	 * �������ʂ�H/L����
	 * ���āFH or L
	 * H or L �ꌅ
	 */
	public static String validateKekkaTiKeitai(String str)
	{
		String temp = null;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("E") )
			temp = "1";

		if( str.equals("L") )
			temp = "2";

		if( str.equals("U") )
			temp = "3";

		return temp;
	}

	/*
	 * ���ʒl�̌���
	 * ���āF
	 */
	public static boolean validateKekkaTi(String str)
	{
		boolean flgKekkaTi = false;

		if ( str.isEmpty() )
			flgKekkaTi=true;

		if( str.length() > 14 )
		{
			flgKekkaTi = true;
		}
		if( !JValidate.isAllHankaku(str,JApplication.CSV_CHARSET) )
		{
			flgKekkaTi = true;
		}
		return flgKekkaTi;

	}

	/*
	 * ���茋�ʋ敪�̌���
	 */
	public static String validatehanteiKekka(String str)
	{
		String ret = null;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("������"))
		{
			ret = "";
		}
		if( str.equals("�ُ�Ȃ�"))
		{
			ret = "1";
		}
		if( str.equals("�y�x�ُ�"))
		{
			ret = "2";
		}
		if( str.equals("�v�o�ߊώ@"))
		{
			ret = "3";
		}
		if( str.equals("�ُ�"))
		{
			ret = "4";
		}
		if( str.equals("�v����"))
		{
			ret = "5";
		}

		return ret;
	}

	/*
	 * ���^�{���茋�ʋ敪�̌���
	 */
	public static String validateMetaboHanteiKubun(String str)
	{
		String ret = null;

		//��l��������
		if ( str.isEmpty() )
			return str;
		if( str.equals("������") )
			ret = "";
		if( str.equals("��Y��") )
			ret = "1";
		if( str.equals("�\���Q�Y��") )
			ret = "2";
		if( str.equals("��Y��") )
			ret = "3";
		if( str.equals("����s�\") )
			ret = "4";

		return ret;
	}

	/*
	 * �ی��w�����x���̌���
	 * ������A�ϋɓI�x���A���@�Â��x���A�Ȃ��A����s�\�̂S���
	 * ���ꂼ���l�A�P�A�Q�A�R�A�S�ƃR�[�h�l���o��
	 */
	public static String validateHokenSidouLevel(String str)
	{
		String ret = null;

		//��l��������
		if ( str.isEmpty() || str.equals("�w�薳��"))
			return "0";
		if( str.equals("������") )
			ret = "";
		if( str.equals("�ϋɓI�x��") )
			ret = "1";
		if( str.equals("���@�Â��x��") )
			ret = "2";
		if( str.equals("�Ȃ��i���񋟁j") )
			ret = "3";
		if( str.equals("����s�\") )
			ret = "4";

		return ret;
	}

	/*
	 * �����敪�̌���
	 * �O�`�T�̐��l�݈̂ꌅ
	 */
	public static String validateSeikyuKubun(String str)
	{
		String ret = null;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("��{�I�Ȍ��f"))
			ret = "1";
		if( str.equals("��{�I�Ȍ��f�{�ڍׂȌ��f"))
			ret = "2";
		if( str.equals("��{�I�Ȍ��f�{�ǉ����f����"))
			ret = "3";
		if( str.equals("��{�I�Ȍ��f�{�ڍׂȌ��f�{�ǉ����f����"))
			ret = "4";
		if( str.equals("�l�ԃh�b�N"))
			ret = "5";

		return ret;
	}

	/*
	 * �������z�̌���
	 */
	public static String validateSeikyuKingaku(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if( str.length() > 9 )
			return null;

		return str;
	}

	/*
	 * ���茒�f���{��ʂ̌���
	 */
	public static String validateTokuteiKenshinSyubetuKubun(String str)
	{
		String ret = null;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("���茒�f�@�֖��͓���ی��w���@�ւ����s�@��") )
			ret = "1";
		if( str.equals("��s�@�ւ�����茒�f�@�֖��͓���ی��w���@��") )
			ret = "2";
		if( str.equals("��s�@�ւ���ی���") )
			ret = "3";
		if( str.equals("�ی��҂����s�@�ցi�����σf�[�^�̏ꍇ�j") )
			ret = "4";
		if( str.equals("�ی��҂����s�@�ցi���ύς݃f�[�^�̏ꍇ�j") )
			ret = "5";
		if( str.equals("���茒�f�@�֖��͓���ی��w���@�ւ���ی���") )
			ret = "6";
		if( str.equals("�ی��҂�����茒�f�@�֖��͓���ی��w���@��") )
			ret = "7";
		if( str.equals("�ی��҂���ی���"))
			ret = "8";
		if( str.equals("���̑�") )
			ret = "9";
		if( str.equals("�ی��҂��獑�i�x������j") )
			ret = "10";
		if( str.equals("��s�@�ւ���ی��҂֊m�F�˗�") )
			ret = "11";
		if( str.equals("�\��") )
			ret = "12";
		if( str.equals("�\��") )
			ret = "13";

		return ret;
	}

	/*
	 * ���茒�f���{�敪�̌���
	 */
	public static String validateTokuteiKenshinJissiKubun(String str)
	{
		String ret = null;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("���茒�f���") )
			ret = "1";
		if( str.equals("����ی��w�����") )
			ret = "2";
		if( str.equals("���i�x������j�ւ̎��{���ʕ�") )
			ret = "3";
		if( str.equals("���̌��f���ʎ�̕�") )
			ret = "4";

		return ret;
	}


	/*
	 * ��荞�݃t�H�[�}�b�g�̌���
	 * ���āF���J�ȏ����t�H�[�}�b�g or �Ǝ��t�H�[�}�b�g
	 */
	public static String validateImportFormat(String str)
	{
		String ret;

		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.equals("���J�ȏ����t�H�[�}�b�g") )
		{
			ret = "1";
			return ret;
		}
		if( str.equals("�Ǝ��t�H�[�}�b�g") )
		{
			ret = "2";
			return ret;
		}

		return null;
	}

	/*
	 * �t�@�C���p�X�̌���
	 */
	public static String validateFilePath(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		return str;
	}

	/*
	 * ORCA�t���O�̌���
	 */
	public static String validateORCAFlag(boolean flag)
	{
		String ret;

		if(flag)
		{
			ret = "1";
		}else{
			ret = "0";
		}

		return ret;
	}

	/*
	 * ORCA�ڑ��pIP�A�h���X�̌���
	 */
	public static String validateIPAddress(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		return str;
	}

	/*
	 * 	�|�[�g�ԍ��̌���
	 */
	public static String validatePortNumber(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isNumber(str) )
			return null;

		if( Integer.valueOf(str) > 65535 || Integer.valueOf(str) <  0 )
			return null;

		return str;
	}

	/*
	 * ORCA�f�[�^�x�[�X���̌���
	 */
	public static String validateORCADBName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA�ڑ��p�v���g�R���̌���
	 */
	public static String validateORCAProtocol(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA�ڑ��p���[�U�[���̌���
	 */
	public static String validateORCADBUserName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA�ڑ��p�p�X���[�h�̌���
	 */
	public static String validateORCAPassword(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/* Added 2008/04/20 �ጎ �����Z���[�U�E�p�X���[�h */
	/* --------------------------------------------------- */
	/*
	 * ORCA�����Z���[�U�[���̌���
	 */
	public static String validateORCANichireseUserName(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA�����Z�p�X���[�h�̌���
	 */
	public static String validateORCANichiresePassword(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA�ڑ��p�����G���R�[�h�̌���
	 */
	public static String validateORCAEncord(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA�o�[�W�����̌��؂��s��
	 */
	public static String validateORCAVersion(String str){
		return str;
	}

	/*
	 * �N��t�H�[�}�b�g�̌���
	 */
	public static String validateAge(String str)
	{
		String retValue = null;

		if ( str != null && ( str.isEmpty() || JValidate.isNumber(str)) ){
			retValue = str;
		}

		return retValue;
	}

	public static String validateOrcaIdDigit(String str){
		String retValue = null;

		if( isNumber(str) ){
			retValue = str;
		}

		return retValue;
	}

	public static int validateOrcaIdDigit(int digit)
	{
		return digit;
	}

	/*
	 * ��^����ʂ̌��؂��s��
	 */
	public static String validateTeikeiType(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		if( str.length() > 2 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * ��^��No�̌��؂��s��
	 */
	public static String validateTeikeiNumber(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return str;

		// edit s.inoue 2010/05/06
		if( str.length() > 2 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * ��^����ʓ��e�̌���
	 */
	public static String validateTeikeibunShubetu(String str)
	{
		//��l��������
		if ( str.isEmpty() )
			return null;
		if( str.getBytes().length > 256 )
			return null;

		return str;
	}

	/*
	 * ��^�����e�̌���
	 */
	public static String validateTeikeibun(String str)
	{
		// edit s.inoue 2010/03/12 null��str
		//��l��������
		if ( str.isEmpty() )
			return str;
		if( str.getBytes().length > 256 )
			return null;

		return str;
	}

	/*
	 * �L�������t���O(0:���� or 1:�L��)�̌��؂��s��
	 */
	public static String validateYukouFlg(String str)
	{
		String temp = null;

		if ( str.isEmpty() )
			return str;

		if( str.equals("0") )
		{
			return str;
		}

		if(str.equals("1") )
		{
			return str;
		}
		return temp;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

