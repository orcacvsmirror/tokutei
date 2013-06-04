package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei;

import java.io .*;
import java.net.*;

// ----------------------------------------------------------------------------
/**
	class::name	JMTHantei

	class::expl	�l�s����N���X
*/
// ----------------------------------------------------------------------------
public abstract class JMTHantei
{
	final private static int GENDER_MAN   = 1;  // �j��

	final private static int GENDER_WOMAN = 2;  // ����

	final public static int  RESULT_STANDARD_CORRESPOND = 1;  // ��Y��

	final public static int  RESULT_RESERVE_CORRESPOND  = 2;  // �\���Y��

	final public static int  RESULT_NOT_CORRESPOND      = 3;  // ��Y��

	final public static int  RESULT_INABILLITY          = 4;  // ����s�\

	/**
	 *  �l�s����
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	// eidt s.inoue 2012/09/14
	public static int checkMT( JMTHanteiData data,boolean newHbA1c)
	{
		boolean isFirstStep = false;

		int iCategory1Lost = 0;
		boolean isCategory1 = false;

		int iCategory2Lost = 0;
		boolean isCategory2 = false;

		int iCategory3Lost = 0;
		boolean isCategory3 = false;

		/**
		 *  �X�e�b�v�P
		 */
		if( data.getInternalFatArea() != null )
		{
			// �������b�ʐ� 100 cm �ȏ�
			if( data.getInternalFatArea().intValue() >= 100 )
			{
				isFirstStep = true;
			}
		}

		if( !isFirstStep )
		{
			if( data.getGender() == null )
			{
				// ����s�\
				return RESULT_INABILLITY;
			}

			switch( (int)data.getGender() )
			{
				/**
				 *  �j��
				 */
				case GENDER_MAN:
					{
						Float iStomach[] = data.getStomach();

						if( iStomach[ 0 ] == null && iStomach[ 1 ] == null && iStomach[ 2 ] == null )
						{
							// ����s�\
							return RESULT_INABILLITY;
						}
						else
						{
							for( int i=0; i<iStomach.length; ++i )
							{
								// �����l����
								if( iStomach[ i ] != null )
								{
									// ���� 85 cm �ȏ�
									if( iStomach[ i ].intValue() >= 85 )
									{
										isFirstStep = true;
									}

									break;
								}
							}
						}
					}
					break;

				/**
				 *  ����
				 */
				case GENDER_WOMAN:
					{
						Float iStomach[] = data.getStomach();

						if( iStomach[ 0 ] == null && iStomach[ 1 ] == null && iStomach[ 2 ] == null )
						{
							// ����s�\
							return RESULT_INABILLITY;
						}
						else
						{
							for( int i=0; i<iStomach.length; ++i )
							{
								// �����l����
								if( iStomach[ i ] != null )
								{
									// ���� 90 cm �ȏ�
									if( iStomach[ i ].intValue() >= 90 )
									{
										isFirstStep = true;
									}

									break;
								}
							}
						}
					}
					break;

				default:
					{
						// ����s�\
					}
					return RESULT_INABILLITY;
			}
		}

		/**
		 *  �X�e�b�v�Q
		 */
		if( isFirstStep )
		{
			/**
			 *  �J�e�S���P
			 */
			if( data.getQuestion2() != null )
			{
				// ��������L��
				if( data.getQuestion2() )
				{
					isCategory1 = true;
				}
			}

			if( data.getBloodSugar() != null )
			{
				// �󕠎������l 110 �ȏ�
				if( data.getBloodSugar().intValue() >= 110 )
				{
					isCategory1 = true;
				}
			}
			else if( data.getHbA1c() != null )
			{
				// HbA1c 5.5 �ȏ�
				// eidt s.inoue 2012/09/14
				double dblHbA1c = 0;
				if (newHbA1c)
					dblHbA1c = 0.5;

				if( data.getHbA1c().floatValue() >= (5.5 + dblHbA1c))
				{
					isCategory1 = true;
				}
			}

			if( data.getQuestion2() == null || data.getBloodSugar() == null && data.getHbA1c() == null )
			{
				// �������ڑ���
				iCategory1Lost ++;
			}

			/**
			 *  �J�e�S���Q
			 */
			if( data.getQuestion1() != null )
			{
				// ��������L��
				if( data.getQuestion1() )
				{
					isCategory2 = true;
				}
			}

			Float iMaxBlood[] = data.getMaxBloodPressre();
			Float iMinBlood[] = data.getMinBloodPressre();

			for( int i=0; i<iMinBlood.length; ++i )
			{
				// �����l����
				if( iMinBlood[ i ] != null )
				{
					// ���k���� 130 �ȏ�
					if( iMinBlood[ i ].intValue() >= 130 )
					{
						isCategory2 = true;
					}

					break;
				}
			}

			for( int i=0; i<iMaxBlood.length; ++i )
			{
				// �����l����
				if( iMaxBlood[ i ] != null )
				{
					// �g������ 85 �ȏ�
					if( iMaxBlood[ i ].intValue() >= 85 )
					{
						isCategory2 = true;
					}

					break;
				}
			}

			if( data.getQuestion1() == null ||
				(iMinBlood[ 0 ]     == null && iMinBlood[ 1 ] == null && iMinBlood[ 2 ] == null) ||
				(iMaxBlood[ 0 ]     == null && iMaxBlood[ 1 ] == null && iMaxBlood[ 2 ] == null) )
			{
				// �������ڑ���
				iCategory2Lost ++;
			}

			/**
			 *  �J�e�S���R
			 */
			if( data.getQuestion3() != null )
			{
				// ��������L��
				if( data.getQuestion3() )
				{
					isCategory3 = true;
				}
			}

			if( data.getNaturalFat() != null )
			{
				// �������b 150 �ȏ�
				if( data.getNaturalFat().intValue() >= 150 )
				{
					isCategory3 = true;
				}
			}

			if( data.getHDL() != null )
			{
				// HDL �R���X�e���[�� 40 ����
				if( data.getHDL().intValue() < 40 )
				{
					isCategory3 = true;
				}
			}

			if( data.getQuestion3() == null || data.getNaturalFat() == null || data.getHDL() == null )
			{
				// �������ڑ���
				iCategory3Lost ++;
			}
		}

		/**
		 *  ���菈��
		 */
		int iResult    = 0;
		int iRiskPoint = 0;

		iRiskPoint += isCategory1 ? 1 : 0;
		iRiskPoint += isCategory2 ? 1 : 0;
		iRiskPoint += isCategory3 ? 1 : 0;

		if( !isFirstStep )
		{
			// ��Y��
			iResult = RESULT_NOT_CORRESPOND;
		}
		else
		{
			if( iRiskPoint == 0 )
			{
				// ��Y��
				iResult = RESULT_NOT_CORRESPOND;
			}
			else if( iRiskPoint == 1 )
			{
				// �\���Q
				iResult = RESULT_RESERVE_CORRESPOND;
			}
			else if( iRiskPoint >= 2 )
			{
				// �Y��
				iResult = RESULT_STANDARD_CORRESPOND;
			}
		}

		// �����l���l����������
		if( iResult >= RESULT_RESERVE_CORRESPOND && (iCategory1Lost > 0 || iCategory2Lost > 0 || iCategory3Lost > 0) )
		{
			// ����s�\
			iResult =  RESULT_INABILLITY;
		}

		return iResult;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

