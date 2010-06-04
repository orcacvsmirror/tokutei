package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei;

import java.io .*;
import java.net.*;

// ----------------------------------------------------------------------------
/***
	class::name	JMTHanteiData

	class::expl	�l�s����f�[�^�i�[�N���X
*/
// ----------------------------------------------------------------------------
public class JMTHanteiData
{
	private Integer m_iGender            = null;					// ���� (�j���A����)
	
	private Float   m_fStomach[]         = { null, null, null };	// ���́i���ۑ���A���ȑ���A���Ȑ\���j

	private Float   m_fNaturalFat        = null;					// �������b

	private Float   m_fInternalFatArea   = null;					// �������b�ʐ�

	private Float   m_fHDL               = null;					// �g�c�k

	private Float   m_fMaxBloodPressre[] = { null, null, null };	// �����i�g�����j(���̑��A�Q��ځA�P���)

	private Float   m_fMinBloodPressre[] = { null, null, null };	// �����i���k���j(���̑��A�Q��ځA�P���)
	
	private Float   m_fDrawBloodTime     = null;					// �̌�����

	private Float   m_fBloodSugar        = null;					// �����l
	
	private Float   m_fHbA1c             = null;					// HbA1c
	
	private Boolean m_isQuestion1        = null;					// ���򎿖�P�i�����j
	
	private Boolean m_isQuestion2        = null;					// ���򎿖�Q�i�����j
	
	private Boolean m_isQuestion3        = null;					// ���򎿖�R�i�����j

	/***
	 *  ���ʐݒ�
	 *
	 *    @param  ����
	 *
	 *    @return none
	 */
	public void setGender( String stGender )
	{
		m_iGender = stGender.isEmpty() ? null : Integer.valueOf( stGender );
	}

	/***
	 *  ���ʎ擾
	 *
	 *    @param  none
	 *
	 *    @return Integer
	 */
	public Integer getGender()
	{
		return m_iGender;
	}

	/***
	 *  ���͐ݒ�
	 *
	 *    @param  ���́i�����l�j
	 *    @param  ���́i����l�j
	 *    @param  ���́i�\���l�j
	 *
	 *    @return none
	 */
	public void setStomach( String stActual, String stMeasure, String stReport )
	{
		m_fStomach[ 0 ] = stActual .isEmpty() ? null : Float.valueOf( stActual  );
		
		m_fStomach[ 1 ] = stMeasure.isEmpty() ? null : Float.valueOf( stMeasure );
		
		m_fStomach[ 2 ] = stReport .isEmpty() ? null : Float.valueOf( stReport  );
	}

	/***
	 *  ���͎擾
	 *
	 *    @param  none
	 *
	 *    @return Float[]
	 */
	public Float[] getStomach()
	{
		return m_fStomach;
	}

	/**
	 *  �������b
	 *
	 *    @param  �������b�l
	 *
	 *    @return none
	 */
	public void setNaturalFat( String stNaturalFat )
	{
		m_fNaturalFat = stNaturalFat.isEmpty() ? null : Float.valueOf( stNaturalFat );
	}

	/**
	 *  �������b�擾
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getNaturalFat()
	{
		return m_fNaturalFat;
	}
	
	/**
	 *  �������b�ʐ�
	 *
	 *    @param  �������b�ʐϒl
	 *
	 *    @return none
	 */
	public void setInternalFatArea( String stInternalFatArea )
	{
		m_fInternalFatArea = stInternalFatArea.isEmpty() ? null : Float.valueOf( stInternalFatArea );
	}

	/**
	 *  �������b�ʐώ擾
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getInternalFatArea()
	{
		return m_fInternalFatArea;
	}

	/**
	 *  �g�c�k�ݒ�
	 *
	 *    @param  �g�c�k
	 *
	 *    @return none
	 */
	public void setHDL( String stHDL )
	{
		m_fHDL = stHDL.isEmpty() ? null : Float.valueOf( stHDL );
	}

	/**
	 *  �g�c�k�擾
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getHDL()
	{
		return m_fHDL;
	}

	/**
	 *  �����i�g�����j�ݒ�
	 *
	 *    @param  �����i���̑��j
	 *    @param  �����i�Q��ځj
	 *    @param  �����i�P��ځj
	 *
	 *    @return none
	 */
	public void setMaxBloodPressre( String stOther, String stSecond, String stFirst )
	{
		m_fMaxBloodPressre[ 0 ] = stOther .isEmpty() ? null : Float.valueOf( stOther  );
			
		m_fMaxBloodPressre[ 1 ] = stSecond.isEmpty() ? null : Float.valueOf( stSecond );

		m_fMaxBloodPressre[ 2 ] = stFirst .isEmpty() ? null : Float.valueOf( stFirst  );
	}

	/**
	 *  �����i�g�����j�擾
	 *
	 *    @param  none
	 *
	 *    @return Float[]
	 */
	public Float[] getMaxBloodPressre()
	{
		return m_fMaxBloodPressre;
	}

	/**
	 *  �����i�k�����j�ݒ�
	 *
	 *    @param  �����i���̑��j
	 *    @param  �����i�Q��ځj
	 *    @param  �����i�P��ځj
	 *
	 *    @return none
	 */
	public void setMinBloodPressre( String stOther, String stSecond, String stFirst )
	{
		m_fMinBloodPressre[ 0 ] = stOther .isEmpty() ? null : Float.valueOf( stOther  );
			
		m_fMinBloodPressre[ 1 ] = stSecond.isEmpty() ? null : Float.valueOf( stSecond );

		m_fMinBloodPressre[ 2 ] = stFirst .isEmpty() ? null : Float.valueOf( stFirst  );
	}

	/**
	 *  �����i�k�����j�擾
	 *
	 *    @param  none
	 *
	 *    @return Float[]
	 */
	public Float[] getMinBloodPressre()
	{
		return m_fMinBloodPressre;
	}

	/**
	 *�@�̌�����
	 *
	 *    @param  �̌�����
	 *
	 *    @return none
	 */
	public void setDrawBloodTime( String stDrawBloodTime )
	{
		m_fDrawBloodTime = stDrawBloodTime.isEmpty() ? null : Float.valueOf( stDrawBloodTime );
	}
	
	/**
	 *�@�̌����Ԏ擾
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	 public Float getDrawBloodTime()
	 {
	 	 return m_fDrawBloodTime;
	 }

	/**
	 *  �����l�ݒ�
	 *
	 *    @param  �����l
	 *
	 *    @return none
	 */
	public void setBloodSugar( String stBloodSugar )
	{
		m_fBloodSugar = stBloodSugar.isEmpty() ? null : Float.valueOf( stBloodSugar );
	}

	/**
	 *  �����l�擾
	 *
	 *    @param  �����l
	 *
	 *    @return Float
	 */
	public Float getBloodSugar()
	{
		return m_fBloodSugar;
	}
	
	/**
	 *  �g���`�P��
	 *
	 *    @param  �g���`�P��
	 *
	 *    @return none
	 */
	public void setHbA1c( String stHbA1c )
	{
		m_fHbA1c = stHbA1c.isEmpty() ? null : Float.valueOf( stHbA1c );
	}
	
	/**
	 *  �g���`�P���擾
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getHbA1c()
	{
		return m_fHbA1c;
	}
	
	/**
	 *  ���⎖���P�i�����j
	 *
	 *    @param  ���⎖���P�i�����j
	 *
	 *    @return none
	 */
	public void setQuestion1( String stAnswer )
	{
		if     ( stAnswer.equals("1") )
		{
			m_isQuestion1 = true;
		}
		else if( stAnswer.equals("2") )
		{
			m_isQuestion1 = false;
		}
	}
	
	/**
	 *  ���⎖���P�i�����j�擾
	 *
	 *    @param  none
	 *
	 *    @return Boolean
	 */
	public Boolean getQuestion1()
	{
		return m_isQuestion1;
	}
	
	/**
	 *  ���⎖���Q�i�����j
	 *
	 *    @param  ���⎖���Q�i�����j
	 *
	 *    @return none
	 */
	public void setQuestion2( String stAnswer )
	{
		if     ( stAnswer.equals("1") )
		{
			m_isQuestion2 = true;
		}
		else if( stAnswer.equals("2") )
		{
			m_isQuestion2 = false;
		}
	}
	
	/**
	 *  ���⎖���Q�i�����j�擾
	 *
	 *    @param  none
	 *
	 *    @return Boolean
	 */
	public Boolean getQuestion2()
	{
		return m_isQuestion2;
	}
	
	/**
	 *  ���⎖���R�i�����j
	 *
	 *    @param  ���⎖���i�����j
	 *
	 *    @return none
	 */
	public void setQuestion3( String stAnswer )
	{
		if     ( stAnswer.equals("1") )
		{
			m_isQuestion3 = true;
		}
		else if( stAnswer.equals("2") )
		{
			m_isQuestion3 = false;
		}
	}
	
	/**
	 *  ���⎖���R�i�����j�擾
	 *
	 *    @param  none
	 *
	 *    @return Boolean
	 */
	public Boolean getQuestion3()
	{
		return m_isQuestion3;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

