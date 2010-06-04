package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei;

import java.sql.SQLException;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * Modified 2008/03/30
 * �\�[�X�R�[�h�̎��F�����Ⴍ�A�o�O�y�я������������������߁A�S�ʓI�ɏ����������B
 */
public class JIppanHantei {

	public static boolean Hantei(Vector<JIppanHanteiStartData> dataSet)
	{
		ArrayList<Hashtable<String,String>> result = null;
		Hashtable<String,String> resultItem = null;
		JIppanHanteiStartData data;
		String sex = null;
		String Query = null;

		for(int j = 0;j < dataSet.size();j++)
		{
			data = dataSet.get(j);

			/* �܂�����Ώۂ��j�����������𔻕ʂ��� */
			Query =
			"SELECT SEX FROM T_KOJIN WHERE " +
			"UKETUKE_ID = " + JQueryConvert.queryConvert(data.getUketukeId());

			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(Query);
				resultItem = result.get(0);
			}catch(SQLException e){
				e.printStackTrace();
			}
			sex = resultItem.get("SEX");

			StringBuffer buffer = new StringBuffer();

			/* ���ڃR�[�h���ƂɁA���ʒl�A����l�A�����l�AHL������擾���Ă���悤���B
			 * HL����͂��ꂩ��s�Ȃ��͂��Ȃ̂ɁA���̎��_�Ŏ擾���Ă��闝�R�͕s���B */
			buffer.append("SELECT ");
//			buffer.append("KOJIN.HIHOKENJYASYO_KIGOU,");
//			buffer.append("KOJIN.HIHOKENJYASYO_NO, ");
			buffer.append("MASTER.DS_JYOUGEN,");
			buffer.append("MASTER.DS_KAGEN, ");

			/* Added 2008/05/08 �ጎ  */
			/* --------------------------------------------------- */
			buffer.append("MASTER.JS_JYOUGEN,");
			buffer.append("MASTER.JS_KAGEN, ");
			/* --------------------------------------------------- */

			buffer.append("SONOTA.KEKA_TI,");
			buffer.append("SONOTA.H_L,SONOTA.HANTEI,");
			buffer.append("SONOTA.KOUMOKU_CD, ");
			// edit 20080918 s.inoue
			buffer.append("SONOTA.JISI_KBN, ");
			buffer.append("TOKUTEI.KENSA_NENGAPI ");
			buffer.append("FROM T_KOJIN AS KOJIN ");
			buffer.append("INNER JOIN T_KENSAKEKA_SONOTA AS SONOTA ");
			buffer.append("ON ");
			buffer.append(" ( ");
			buffer.append("    KOJIN.UKETUKE_ID = SONOTA.UKETUKE_ID ");
			buffer.append(" ) ");
			buffer.append("INNER JOIN T_KENSHINMASTER MASTER ");
			buffer.append("ON ");
			buffer.append("( ");
			buffer.append("    KOJIN.HKNJANUM = MASTER.HKNJANUM ");
			buffer.append("    AND ");
			buffer.append("    SONOTA.KOUMOKU_CD = MASTER.KOUMOKU_CD ");
			buffer.append(") ");

			buffer.append("INNER JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ");
			buffer.append("ON  ");
			buffer.append("( ");
			buffer.append("    KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
			buffer.append(") ");
			buffer.append("WHERE ");
//			buffer.append("( ");
			buffer.append("    KOJIN.UKETUKE_ID = ");
			buffer.append(JQueryConvert.queryConvert(data.getUketukeId()));
			buffer.append("    AND ");
			buffer.append("    TOKUTEI.KENSA_NENGAPI = ");
			buffer.append(JQueryConvert.queryConvert(data.getKensaJissiDate()));
			buffer.append("AND ");
			buffer.append("    SONOTA.KEKA_TI IS NOT NULL ");

						// edit s.inoue 20081126 ��l���Ȃ����ʃf�[�^���擾�ł��Ȃ��Ȃ�ׁA���̕����͕s�v
						/* Deleted 2008/03/30 �ጎ ����l�A�����l�́A�Е������ł��L���Ȃ̂ŁA
						 * �I���W�i���̃\�[�X�ɗL�邱�̕����̏����͕s�v�B */
						/* --------------------------------------------------- */
//						buffer.append("    AND  ");
//						/* �j���̏ꍇ */
//						if( sex.equals("1") ){
//							buffer.append("    MASTER.DS_JYOUGEN IS NOT NULL ");
//							buffer.append("    AND ");
//							buffer.append("    MASTER.DS_KAGEN IS NOT NULL ");
//						/* �����̏ꍇ */
//						}else if( sex.equals("2") ){
//							buffer.append("    MASTER.JS_JYOUGEN IS NOT NULL ");
//							buffer.append("    AND ");
//							buffer.append("    MASTER.JS_KAGEN IS NOT NULL ");
//						}
						/* --------------------------------------------------- */

//			buffer.append(")");

			/*
			 *
			 */
			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
			}catch(SQLException e){
				e.printStackTrace();
			}

			try{
				JApplication.kikanDatabase.Transaction();

				for( int i = 0;i < result.size();i++ )
				{
					/* ��r���ʂ𔻒菈���N���X�̃I�u�W�F�N�g�Ƀf�t�H���g�l��ݒ肷��B */
					JIppanHanteiProcessData updateData = new JIppanHanteiProcessData();
					try {
						updateData.setH_l("");
						updateData.setHantei("�ُ�Ȃ�");

						/* ���ʒl�𐔒l�ɕϊ����� */
						resultItem = result.get(i);
						// add 20080917 s.inoue
						updateData.setJisi_KBN(resultItem.get("JISI_KBN"));

						String itemKekati = resultItem.get("KEKA_TI");

						if (itemKekati == null || itemKekati.equals("")) {
							continue;
						}

						Double kekati = 0d;
						try {
							kekati = Double.valueOf(itemKekati);

						} catch (NumberFormatException e) {
							/* �ϊ��Ɏ��s�����ꍇ�́A���̍��ڂ̏����𒆎~����B */
							System.out.println("continue to next roop.");
							continue;
						}

						/* ���l�̔�r�ɂ�锻����s�Ȃ��B */
						resultItem = result.get(i);

						String itemJyougen = null;
						String itemKagen = null;
						/* �j���̏ꍇ */
						if( sex.equals("1") ){
							itemJyougen = resultItem.get("DS_JYOUGEN");
							itemKagen = resultItem.get("DS_KAGEN");

						/* �����̏ꍇ */
						}else if( sex.equals("2") ){
							itemJyougen = resultItem.get("JS_JYOUGEN");
							itemKagen = resultItem.get("JS_KAGEN");
						}

						/* Modified 2008/03/31 �ጎ null �l�`�F�b�N�ǉ� */
						/* --------------------------------------------------- */
//						/* ����l�𐔒l�ɕϊ�����B */
//						boolean jyougenExists = false;
//						Double jyougen = 0d;
//						try {
//							jyougen = Double.valueOf(itemJyougen);
//							jyougenExists = true;
//
//						} catch (NumberFormatException e) {
//							/* �ϊ��Ɏ��s�����ꍇ�́A���̍��ڂ̏����𒆎~����B */
//							e.printStackTrace();
//							System.out.println("do nothing.");
//						}
//
//						/* �����l�𐔒l�ɕϊ�����B */
//						boolean kagenExists = false;
//						Double kagen = 0d;
//						try {
//							kagen = Double.valueOf(itemKagen);
//							kagenExists = true;
//
//						} catch (NumberFormatException e) {
//							/* �ϊ��Ɏ��s�����ꍇ�́A���̍��ڂ̏����𒆎~����B */
//							e.printStackTrace();
//							System.out.println("do nothing.");
//						}
						/* --------------------------------------------------- */
						/* ����l�𐔒l�ɕϊ�����B */
						boolean jyougenExists = false;
						Double jyougen = 0d;

						if (itemJyougen != null && ! itemJyougen.isEmpty()) {
							try {
								jyougen = Double.valueOf(itemJyougen);
								jyougenExists = true;

							} catch (NumberFormatException e) {
								/* �ϊ��Ɏ��s�����ꍇ�́A���̍��ڂ̏����𒆎~����B */
								e.printStackTrace();
								System.out.println("do nothing.");
							}
						}

						/* �����l�𐔒l�ɕϊ�����B */
						boolean kagenExists = false;
						Double kagen = 0d;

						if (itemKagen != null && ! itemKagen.isEmpty()) {
							try {
								kagen = Double.valueOf(itemKagen);
								kagenExists = true;

							} catch (NumberFormatException e) {
								/* �ϊ��Ɏ��s�����ꍇ�́A���̍��ڂ̏����𒆎~����B */
								e.printStackTrace();
								System.out.println("do nothing.");
							}
						}
						/* --------------------------------------------------- */

						/* ����l�����݂���ꍇ */
						if (jyougenExists) {
							/* ����l�Ɖ����l�����݂���ꍇ */
							if (kagenExists) {
								/* ���ʒl������l���傫���ꍇ */
								if( kekati > jyougen ){
									updateData.setH_l("H");
									updateData.setHantei("�ُ�");
								}
								/* ���ʒl�������l��菬�����ꍇ */
								else if(kekati < kagen){
									updateData.setH_l("L");
									updateData.setHantei("�ُ�");
								}
								else {
									/* ����l�i�f�t�H���g�j */
								}
							}
							/* ����l�݂̂����݂���ꍇ */
							else {
								/* ���ʒl������l���傫���ꍇ */
								if( kekati > jyougen ){
									updateData.setH_l("H");
									updateData.setHantei("�ُ�");
								}
								else {
									/* ����l�i�f�t�H���g�j */
								}
							}
						}
						/* ����l�����݂��Ȃ��ꍇ */
						else {
							/* �����l�݂̂����݂���ꍇ */
							if (kagenExists) {
								/* ���ʒl�������l��菬�����ꍇ */
								if(kekati < kagen){
									updateData.setH_l("L");
									updateData.setHantei("�ُ�");
								}
								else {
									/* ����l�i�f�t�H���g�j */
								}
							}
							/* ����l�������l�����݂��Ȃ��ꍇ */
							else {
								/* ����l�i�f�t�H���g�j */
							}
						}
					}
					finally {

						/* Modified 2008/05/08 �ጎ  */
						/* --------------------------------------------------- */
//						String updateQuery =
//							"UPDATE T_KENSAKEKA_SONOTA " +
//							"SET " +
//							"H_L = " + JQueryConvert.queryConvertAppendComma(updateData.getH_l()) +
//							"HANTEI = " + JQueryConvert.queryConvert(updateData.getHantei()) +
//							"WHERE " +
//							"UKETUKE_ID = " + JQueryConvert.queryConvert(data.getUketukeId()) + " " +
//							"AND " +
//							"KENSA_NENGAPI = " + JQueryConvert.queryConvert(data.getKensaJissiDate()) +
//							"AND " +
//							"KOUMOKU_CD = " + JQueryConvert.queryConvert(resultItem.get("KOUMOKU_CD"));
						/* --------------------------------------------------- */
						StringBuffer buffer2 = new StringBuffer();

						buffer2.append("UPDATE T_KENSAKEKA_SONOTA ");
						buffer2.append("SET ");
						buffer2.append("H_L = ");
						buffer2.append(JQueryConvert.queryConvertAppendComma(updateData.getH_l()));
						buffer2.append("HANTEI = ");
						buffer2.append(JQueryConvert.queryConvertAppendComma(updateData.getHantei()));
						// add s.inoeu 20080901
						// ���{�敪��null�l���͋�̏ꍇ�́u���{:1�v��ݒ肷��B
						buffer2.append("JISI_KBN = ");
						buffer2.append(JQueryConvert.queryConvert(updateData.getJISI_KBN()));
						// add s.inoeu 20080901
						buffer2.append("WHERE ");
						buffer2.append("UKETUKE_ID = ");
						buffer2.append(JQueryConvert.queryConvert(data.getUketukeId()));
						buffer2.append(" AND ");
						buffer2.append("KENSA_NENGAPI = ");
						buffer2.append(JQueryConvert.queryConvert(data.getKensaJissiDate()));
						buffer2.append("AND ");
						buffer2.append("KOUMOKU_CD = ");
						buffer2.append(JQueryConvert.queryConvert(resultItem.get("KOUMOKU_CD")));


						String updateQuery = buffer2.toString();
						/* --------------------------------------------------- */

						JApplication.kikanDatabase.sendNoResultQuery(updateQuery);
					}
				}

				JApplication.kikanDatabase.Commit();

			}catch(SQLException e){
				e.printStackTrace();
				try{
					JApplication.kikanDatabase.rollback();
					return false;
				}catch(SQLException f){
					f.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
}
