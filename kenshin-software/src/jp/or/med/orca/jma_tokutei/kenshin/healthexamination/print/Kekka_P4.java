package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KenshinKekka;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

public class Kekka_P4 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P4();
	}
	
	public Kekka_P4(){}
	
	/**
	 * ���茒�f���ʒʒm�\�@�y�[�W4
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin, KenshinKekka
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{
		if( pageIndex >= 4 )
		{
			return Printable.NO_SUCH_PAGE;
		}

		g.translate( (int)(pf.getImageableX()), (int)(pf.getImageableY()) );

		((Graphics2D)g).transform( java.awt.geom.AffineTransform.getScaleInstance( 1, 1 ) );

		// ���������
		Graphics2D g2d = (Graphics2D)g;
		
		/*
		 * �����ݒ�
		 * A4�̘g��`��(72dpi 595px*842px)
		 * width : 451	�]��25.4mm�l��
		 * height: 695	�]��25.4mm�l��	
		 */
			final int x_w = 451;
			final int y_h = 695;

			/*
			 * �e���ڂ̕�
			 */
			int base = 24;
			
			/*
			 * �l��񕔕��̍���
			 */
			int height_info = 20;
			
			/*
			 * �`�悷����̑���
			 */
			BasicStroke stroke;
			stroke = new BasicStroke(0.2f);
			g2d.setStroke(stroke);
			
			/*
			 * �����̐ݒ�
			 */
			int fontsize = 7;
			Font f = new Font("Dialog" , Font.PLAIN, fontsize);
			g2d.setFont(f);

			/*
			 * y���W��_
			 */
			int base_y = 0;
			
			/*
			 * �h���t�g
			 */	
				fontsize = 64;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				Color c = g2d.getColor();
				g2d.setColor(Color.gray);
				g2d.drawString("�c�q�`�e�s", 80, 300);
				g2d.setColor(c);
				fontsize = 7;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);

		/*
		 * �g�̕`��
		 */
			//�O�g
				g2d.drawRect( 0, 0, x_w, y_h);
			
			/*
			 * ��f����
			 */
				base_y = height_info;
				//�g
				/* yabu 2008/03/12*/
//				g2d.drawRect( 10, base_y, (x_w-20), 19*base);
				g2d.drawRect( 10, base_y, (x_w-20), 26*base);
				/* yabu 2008/03/12*/

				//������
				/* yabu 2008/03/12*/
				for (int i = 0;i < 26; i++){
				//for (int i = 0;i < 18; i++){
					if (i==4||i==6||i==8){
					}else{
						g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
					}
				}
				/* yabu 2008/03/12*/
				
				//�c
				/* yabu 2008/03/12*/
//				g2d.drawLine( 25, base_y + base,  25, base_y + 19*base);
//				g2d.drawLine(351, base_y, 351, base_y + 19*base);
//				g2d.drawLine(381, base_y, 381, base_y + 19*base);
//				g2d.drawLine(411, base_y, 411, base_y + 19*base);
				g2d.drawLine( 25, base_y + base,  25, base_y + 26*base);
				g2d.drawLine(266, base_y, 266, base_y + 26*base);
				g2d.drawLine(326, base_y, 326, base_y + 26*base);
				g2d.drawLine(381, base_y, 381, base_y + 26*base);
				/* yabu 2008/03/12*/

		/*
		 * �t�H�[�}�b�g
		 */
			/*
			 * ��f����
			 */
				//1�s��
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("��f���e"	, 10+(266-10)/2 - 4*fontsize/2, base_y);
				g2d.drawString("����"	, 266+(326-266)/2  - 2*fontsize/2, base_y);
				g2d.drawString("�O��"	, 326+(381-326)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O�X��"	, 381+(441-381)/2 - 3*fontsize/2, base_y);
				
//				g2d.drawString("��f���e"	, 10+(351-10)/2 - 4*fontsize/2, base_y);
//				g2d.drawString("����"	, 351+(381-351)/2 - 2*fontsize/2, base_y);
//				g2d.drawString("�O��"	, 381+(411-381)/2 - 2*fontsize/2, base_y);
//				g2d.drawString("�O�X��"	, 411+(441-411)/2 - 3*fontsize/2, base_y);
				
				
				/* yabu 2008/03/12*/
				/* 7.�n���A19.����ʁA21.�����K�����P�A22.�ی��w��������*/
				/* ��L��4���ڒǉ��ɂ�荀�ڔԍ��C��*/
				//2���
				String[][] koumoku_name = {
							 {"1-1"	, "�������������𕞗p���Ă���B"}
							,{"1-2"	, "�C���X�������˖��͌��������������g�p���Ă���B"}
							,{"1-3"	, "�R���X�e���[�����������𕞗p���Ă���B"}
							,{"4"	, "��t����A�]�����i�]�o���A�]�[�Ǔ��j�ɂ������Ă���Ƃ���ꂽ��A"}
							,{""	, "���Â��󂯂����Ƃ�����B"}
							,{"5"	, "��t����A�S���a�i���S�ǁA�S�؍[�ǁj�ɂ������Ă���Ƃ���ꂽ��A"}
							,{""	, "���Â��󂯂����Ƃ�����B"}
							,{"6"	, "��t����A�����̐t�s�S�ɂ������Ă���Ƃ���ꂽ��A"}
							,{""	, "���Ái�l�H���́j���󂯂����Ƃ�����B"}
							,{"7"	, "��t����n���Ƃ���ꂽ���Ƃ�����B"}
							,{"8"	, "���݁A���΂����K���I�ɋz���Ă���B"}
							,{"9"	, "�Q�O�΂̎��̑̏d����P�O�L���ȏ㑝�����Ă���B"}
							,{"10"	, "�P��R�O���ȏ�̌y�����������^�����T�Q���ȏ�A�P�N�ȏ���{���Ă���B"}
							,{"11"	, "���퐶���ɂ����ĕ��s���͓����̐g�̊������P���P���Ԉȏ���{���Ă���B"}
							,{"12"	, "������̓����Ɣ�r���ĕ������x�������B"}
							,{"13"	, "���̂P�N�Ԃő̏d�̑������}�Rkg�ȏ゠��B"}
							,{"14"	, "���H���E�h�J�H���E�Ȃ���H���������B"}
							,{"15"	, "�A�Q�O�̂Q���Ԉȓ��ɗ[�H���Ƃ邱�Ƃ��T�ɂR��ȏ゠��B"}
							,{"16"	, "��H��ԐH�������B"}
							,{"17"	, "���H�𔲂����Ƃ������B"}
							,{"18"	, "�قږ����A���R�[�����������ށB"}
							,{"19"	, "�����������1��������̈���ʁB"}
							,{"20"	, "�����ŋx�{�������Ă���B"}
							,{"21"	, "�^����H�������̐����K�������P���Ă݂悤���Ǝv���܂����B"}
							,{"22"	, "�����K���̉��P�ɂ��ĕی��w�����󂯂�@�����΁A���p���܂����B"}
						};
				/* yabu 2008/03/12*/
				
				base_y = height_info + base + (base + fontsize)/2;
				
				/* yabu 2008/03/12*/
				//for(int i = 0;i < 18;i++){
				for(int i = 0;i < 25;i++){
				/* yabu 2008/03/12*/
					g2d.drawString(koumoku_name[i][0], 10+ 2 , base_y);
					g2d.drawString(koumoku_name[i][1], 25+ 2 , base_y);
					base_y += base;
				}
		
		/*
		 * �f�[�^�̈ڑ��ƕ\��
		 */
			try{
			/*
			 * ��f���ʈꗗ���������
			 * PrintData���猒�f���ʏ��𒊏o
			 */
				KenshinKekka tmpKenshinKekka = (KenshinKekka)printData.get("KenshinKekka");
				
				/*
				 * ���f�N�������Ƃ̃f�[�^���擾
				 * 0=>����
				 * 1=>�O��
				 * 2=>�O�X��
				 */
				ArrayList<Hashtable<String, Hashtable<String, String>>> KenshinKekkaData = tmpKenshinKekka.getPrintKenshinKekkaData();

				/*
				 * ���f�N�������擾
				 * 0=>����
				 * 1=>�O��
				 * 2=>�O�X��
				 */
				String[] KensaNengapi = tmpKenshinKekka.getKensaNengapi();

				/*
				 * ���f�N�������Ƃɏo��
				 */
				/* yabu 2008/03/12*/
				/* 7.�n���A19.����ʁA21.�����K�����P�A22.�ی��w����ǉ�*/
				/* ��L��4���ڒǉ��ɂ�荀�ڔԍ��C��*/
				base_y = height_info + base + (base + fontsize)/2;
				for (int i=0;i < KensaNengapi.length; i++){
					int j=0;
					try {
						//1-1 �������������𕞗p���Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N701000000000011"), 268 + i*30, base_y + j*base); j++;
						//1-2 �C���X�������˖��͌��������������g�p���Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N706000000000011"), 268 + i*30, base_y + j*base); j++;
						//1-3 �R���X�e���[�����������𕞗p���Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N711000000000011"), 268 + i*30, base_y + j*base); j++;
						//4 ��t����A�]�����i�]�o���A�]�[�Ǔ��j�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N716000000000011"), 268 + i*30, base_y + j*base); j++;
						//5 ��t����A�S���a�i���S�ǁA�S�؍[�ǁj�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N721000000000011"), 268 + i*30, base_y +base+ j*base); j++;
						j++;
						//6 ��t����A�����̐t�s�S�ɂ������Ă���Ƃ���ꂽ��A���Ái�l�H���́j���󂯂����Ƃ�����
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N726000000000011"), 268 + i*30, base_y +base+ j*base); j++;
						j++;
						//7 ��t����n���Ƃ���ꂽ���Ƃ�����
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N731000000000011"), 268 + i*30, base_y + j*base); j++;
						j++;
						//8 ���݁A���΂����K���I�ɋz���Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N736000000000011"), 268 + i*30, base_y + j*base); j++;
						//9 �Q�O�΂̎��̑̏d����P�O�L���ȏ㑝�����Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N741000000000011"), 268 + i*30, base_y + j*base); j++;
						//10 �P��R�O���ȏ�̌y�����������^�����T�Q���ȏ�A�P�N�ȏ���{���Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N746000000000011"), 268 + i*30, base_y + j*base); j++;
						//11 ���퐶���ɂ����ĕ��s���͓����̐g�̊������P���P���Ԉȏ���{���Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N751000000000011"), 268 + i*30, base_y + j*base); j++;
						//12 ������̓����Ɣ�r���ĕ������x������
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N756000000000011"), 268 + i*30, base_y + j*base); j++;
						//13 ���̂P�N�Ԃő̏d�̑������}�Rkg�ȏ゠��
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N761000000000011"), 268 + i*30, base_y + j*base); j++;
						//14 ���H���E�h�J�H���E�Ȃ���H��������
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N766000000000011"), 268 + i*30, base_y + j*base); j++;
						//15 �A�Q�O�̂Q���Ԉȓ��ɗ[�H���Ƃ邱�Ƃ��T�ɂR��ȏ゠��
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N771000000000011"), 268 + i*30, base_y + j*base); j++;
						//16 ��H��ԐH������
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N776000000000011"), 268 + i*30, base_y + j*base); j++;
						//17 ���H�𔲂����Ƃ�����
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N781000000000011"), 268 + i*30, base_y + j*base); j++;
						//18 �قږ����A���R�[������������
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N786000000000011"), 268 + i*30, base_y + j*base); j++;
						//19 �������1��������̈����
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N791000000000011"), 268 + i*30, base_y + j*base); j++;
						//20 �����ŋx�{�������Ă���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N796000000000011"), 268 + i*30, base_y + j*base); j++;
						//21 �^���␶�������̐����K�������P���Ă݂悤�Ǝv���܂���
						//g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N801000000000011"), 268 + i*30, base_y + j*base); j++;
		    			if (tmpKenshinKekka.getMonshin(i,"9N801000000000011").length() >= 9) {
			    			int tmp = fontsize;
		    				fontsize = 6;
			    			f = new Font("Dialog" , Font.PLAIN, fontsize);
			    			g2d.setFont(f);
				    		g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N801000000000011"), 268 + i*30, base_y + j*base); j++;
			    			fontsize = tmp;
			    			f = new Font("Dialog" , Font.PLAIN, fontsize);
			    			g2d.setFont(f);
		    			} else {
				    		g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N801000000000011"), 268 + i*30, base_y + j*base); j++;
		    			}
						//22 �����K���a�̉��P�ɂ��ĕی��w�����󂯂�@�����΁A���p���܂���
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N806000000000011"), 268 + i*30, base_y + j*base); j++;

//						//1-1 �������������𕞗p���Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N701000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//1-2 �C���X�������˖��͌��������������g�p���Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N706000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//1-3 �R���X�e���[�����������𕞗p���Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N711000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//4 ��t����A�]�����i�]�o���A�]�[�Ǔ��j�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N716000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//5 ��t����A�S���a�i���S�ǁA�S�؍[�ǁj�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N721000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//6 ��t����A�����̐t�s�S�ɂ������Ă���Ƃ���ꂽ��A���Ái�l�H���́j���󂯂����Ƃ�����
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N726000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//7 ��t����n���Ƃ���ꂽ���Ƃ�����
//						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N731000000000011"), 352 + i*30, base_y + j*base); j++;
//						//8 ���݁A���΂����K���I�ɋz���Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N736000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//9 �Q�O�΂̎��̑̏d����P�O�L���ȏ㑝�����Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N741000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//10 �P��R�O���ȏ�̌y�����������^�����T�Q���ȏ�A�P�N�ȏ���{���Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N746000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//11 ���퐶���ɂ����ĕ��s���͓����̐g�̊������P���P���Ԉȏ���{���Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N751000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//12 ������̓����Ɣ�r���ĕ������x������
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N756000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//13 ���̂P�N�Ԃő̏d�̑������}�Rkg�ȏ゠��
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N761000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//14 ���H���E�h�J�H���E�Ȃ���H��������
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N766000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//15 �A�Q�O�̂Q���Ԉȓ��ɗ[�H���Ƃ邱�Ƃ��T�ɂR��ȏ゠��
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N771000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//16 ��H��ԐH������
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N776000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//17 ���H�𔲂����Ƃ�����
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N781000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//18 �قږ����A���R�[������������
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N786000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//19 �������1��������̈����
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N791000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//20 �����ŋx�{�������Ă���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N796000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//21 �^���␶�������̐����K�������P���Ă݂悤�Ǝv���܂���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N801000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//22 �����K���a�̉��P�ɂ��ĕی��w�����󂯂�@�����΁A���p���܂���
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N806000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;

					} catch (IndexOutOfBoundsException e) {
						
					}
				}
				/* yabu 2008/03/12*/

			/*
			 * �l���
			 * PrintData����l���𒊏o
			 */	
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
				/*
				 * ��f�������ԍ��ƃy�[�W�ԍ�
				 */
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 380, 670);
				g2d.drawString("Page.4", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

