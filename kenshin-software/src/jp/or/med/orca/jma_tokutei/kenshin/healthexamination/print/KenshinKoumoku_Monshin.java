package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import java.util.*;

import jp.or.med.orca.jma_tokutei.common.printer.*;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.*;

/**
 * ���茒�f���ړ��̓V�[�g�i��f�j
 */
public class KenshinKoumoku_Monshin extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new KenshinKoumoku_Monshin();
	}
	
	public KenshinKoumoku_Monshin(){}
	
	/**
	 * ���f���ځ@��f���
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{
		if( pageIndex >= 2 )
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
			//int base = 24;
			int base = 20;
			
			/*
			 * �l��񕔕��̍���
			 */
			int height_info = 82;
			
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
//				g2d.drawRect( 10, base_y, (x_w-20), 19*base);
				g2d.drawRect( 10, base_y, (x_w-20), 30*base);
				g2d.drawLine(10, base_y + (1)*base, 441, base_y + (1)*base);			
				g2d.drawLine(10, base_y + (2)*base, 441, base_y + (2)*base);			
				g2d.drawLine(10, base_y + (3)*base, 441, base_y + (3)*base);			
				g2d.drawLine(10, base_y + (4)*base, 441, base_y + (4)*base);			
				g2d.drawLine(10, base_y + (5)*base, 441, base_y + (5)*base);			
				g2d.drawLine(10, base_y + (6)*base, 441, base_y + (6)*base);			
				g2d.drawLine(10, base_y + (7)*base, 441, base_y + (7)*base);			
				g2d.drawLine(10, base_y + (8)*base, 441, base_y + (8)*base);			
				g2d.drawLine(10, base_y + (10)*base, 441, base_y + (10)*base);			
				g2d.drawLine(10, base_y + (11)*base, 441, base_y + (11)*base);			
				g2d.drawLine(10, base_y + (12)*base, 441, base_y + (12)*base);			
				g2d.drawLine(10, base_y + (13)*base, 441, base_y + (13)*base);			
				g2d.drawLine(10, base_y + (14)*base, 441, base_y + (14)*base);			
				g2d.drawLine(10, base_y + (15)*base, 441, base_y + (15)*base);			
				g2d.drawLine(10, base_y + (16)*base, 441, base_y + (16)*base);			
				g2d.drawLine(10, base_y + (17)*base, 441, base_y + (17)*base);			
				g2d.drawLine(10, base_y + (18)*base, 441, base_y + (18)*base);			
				g2d.drawLine(10, base_y + (19)*base, 441, base_y + (19)*base);			
				g2d.drawLine(10, base_y + (21)*base, 441, base_y + (21)*base);			
				//g2d.drawLine(10, base_y + (22)*base, 441, base_y + (22)*base);			
				g2d.drawLine(10, base_y + (23)*base, 441, base_y + (23)*base);			
				g2d.drawLine(10, base_y + (24)*base, 441, base_y + (24)*base);			

				g2d.drawLine(10, base_y + (29)*base, 441, base_y + (29)*base);			
				g2d.drawLine(10, base_y + (30)*base, 441, base_y + (30)*base);			

				//������
//				for (int i = 0;i < 18; i++){
//					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
//				}
				
				//�c
				g2d.drawLine( 25, base_y + base,  25, base_y + 30*base);
				g2d.drawLine(340, base_y, 340, base_y + 30*base);
		
		/*
		 * �t�H�[�}�b�g
		 */	
			/*
			 * �\��
			 */
				fontsize = 14;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				
				g2d.drawString("���茒�N�f������[", 10 + (410-10)/2 - 7*fontsize/2, 27);
				
				fontsize = 7;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
			/*
			 * �l���
			 */
				//����
				g2d.drawString("��f���ԍ�", 10, 50);
				g2d.drawLine(10, 52, 110, 52);
				g2d.drawString("����", 120, 50);
				g2d.drawLine(120, 52, 280, 52);
				g2d.drawString("��f��", 290, 50);
				g2d.drawLine(290, 52, 441, 52);
				
				//����
				g2d.drawString("�ی��Ҕԍ�", 10, 68);
				g2d.drawLine(10, 70, 110, 70);
				g2d.drawString("��ی��ҏؓ��L��", 120, 68);
				g2d.drawLine(120, 70, 280, 70);
				g2d.drawString("��ی��ҏؓ��ԍ�", 290, 68);
				g2d.drawLine(290, 70, 441, 70);
				
			/*
			 * ��f����
			 */
				//1�s��
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("��f���e"	, 10+(340-10)/2 - 4*fontsize/2, base_y);
				g2d.drawString("�I����"	, 340+(441-340)/2 - 3*fontsize/2, base_y);
				
				
				//2���
				base_y = height_info + base + (base + fontsize)/2;
				String[][] koumoku_name = {
							 {"1-1"	, "�������������𕞗p���Ă���B"}
							,{"1-2"	, "�C���X�������˖��͌��������������g�p���Ă���B"}
							,{"1-3"	, "�R���X�e���[�����������𕞗p���Ă���B"}
							,{"4"	, "��t����A�]�����i�]�o���A�]�[�Ǔ��j�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����B"}
							,{"5"	, "��t����A�S���a�i���S�ǁA�S�؍[�ǁj�ɂ������Ă���Ƃ���ꂽ��A���Â��󂯂����Ƃ�����B"}
							,{"6"	, "��t����A�����̐t�s�S�ɂ������Ă���Ƃ���ꂽ��A���Ái�l�H���́j���󂯂����Ƃ�����B"}
							,{"7"	, "��t����A�n���Ƃ���ꂽ���Ƃ�����B"}
							,{"8"	, "���݁A���΂����K���I�ɋz���Ă���B"}
							,{"9"	, "�Q�O�΂̎��̑̏d����P�O�L���ȏ㑝�����Ă���B"}
							,{"10"	, "�P��R�O���ȏ�̌y�����������^�����T�Q���ȏ�A�P�N�ȏ���{���Ă���B"}
							,{"11"	, "���퐶���ɂ����ĕ��s���͓����̐g�̊������P���P���Ԉȏ���{���Ă���B"}
							,{"12"	, "�قړ�����̓����Ɣ�r���ĕ������x�������B"}
							,{"13"	, "���̂P�N�Ԃő̏d�̑������}�Rkg�ȏ゠��B"}
							,{"14"	, "�l�Ɣ�r���ĐH�ׂ鑬�x�������B"}
							,{"15"	, "�A�Q�O�̂Q���Ԉȓ��ɗ[�H���Ƃ邱�Ƃ��T�ɂR��ȏ゠��B"}
							,{"16"	, "�[�H��ɊԐH�i�R�H�ȊO�̖�H�j���Ƃ邱�Ƃ��T�ɂR��ȏ゠��B"}
							,{"17"	, "���H�𔲂����Ƃ��T�ɂR��ȏ゠��B"}
							,{"18"	, "�����i�����A�Ē��A�r�[���A�m���Ȃǁj�����ޕp�x�B"}
							,{"19"	, "������̂P��������̈���ʁB"}
							,{"20"	, "�����ŋx�{�������Ă���B"}
							,{"21"	, "�^����H�������̐����K�������P���Ă݂悤�Ǝv���܂����B"}
							,{"22"	, "�����K���̉��P�ɂ��ĕی��w�����󂯂�@�����Η��p���܂����B"}
						};
				int base_k = height_info + base + (base + fontsize)/4;
				String[][] koumoku_komoji = {
						{""	, "(�� �u���݁A�K���I�ɋi�����Ă���ҁv�Ƃ́u���v100�{�ȏ�A���͂U�����ȏ�z���Ă���ҁv"}
						,{""	, "�ł���A�ŋ߂P�������z���Ă���ҁj"}
						,{""	, "�����ꍇ�i180ml�j�̖ڈ��F�r�[�����r�P�{�i��500ml�j�A�Ē�35�x�i80ml�j�A"}
						,{""	, "�E�C�X�L�[�_�u����t�i60ml�j�A���C��2�t�i240ml�j"}
						,{""	, ""} //�u�����N�p
				};
				g2d.drawString(koumoku_name[0][0], 10+ 2 , base_y);
				g2d.drawString(koumoku_name[0][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[1][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[1][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[2][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[2][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[3][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[3][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[4][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[4][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[5][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[5][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[6][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[6][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[7][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[7][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_komoji[0][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[0][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_komoji[1][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[1][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_name[8][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[8][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[9][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[9][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[10][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[10][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[11][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[11][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[12][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[12][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[13][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[13][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[14][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[14][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[15][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[15][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[16][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[16][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[17][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[17][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_komoji[4][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_komoji[4][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_name[18][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[18][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_komoji[2][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[2][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_komoji[3][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[3][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_name[19][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[19][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[20][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[20][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[21][0], 10+ 2 , base_y+=base*5);
				g2d.drawString(koumoku_name[21][1], 25+ 2 , base_y);
//				for(int i = 0;i < 19;i++){
//					g2d.drawString(koumoku_name[i][0], 10+ 2 , base_y);
//					g2d.drawString(koumoku_name[i][1], 25+ 2 , base_y);
//					base_y += base;
//				}
				
				//3���
//				base_y = height_info + base + (base + fontsize)/2;
//				String[][] koumoku_select = {
//						 	 {"1-1"	, "1:���򂠂�@�@2:����Ȃ�"}
//							,{"1-2"	, "1:���򂠂�@�@2:����Ȃ�"}
//							,{"1-3"	, "1:���򂠂�@�@2:����Ȃ�"}
//							,{"4"	, "1:�͂��@�@2:������"}
//							,{"5"	, "1:�͂��@�@2:������"}
//							,{"6"	, "1:�͂��@�@2:������"}
//							,{"7"	, "1:�͂��@�@2:������"}
//							,{"8"	, "1:�͂��@�@2:������"}
//							,{"9"	, "1:�͂��@�@2:������"}
//							,{"10"	, "1:�͂��@�@2:������"}
//							,{"11"	, "1:�͂��@�@2:������"}
//							,{"12"	, "1:�͂��@�@2:������"}
//							,{"13"	, "1:�����@2:�ӂ��@3:�x��"}
//							,{"14"	, "1:�͂��@�@2:������"}
//							,{"15"	, "1:�͂��@�@2:������"}
//							,{"16"	, "1:�͂��@�@2:������"}
//							,{"17"	, "1:�͂��@�@2:������"}
//							,{"18"	, "1:�͂��@�@2:������"}
//						};				
				base_y = height_info + base + (base + fontsize)/2;
				String[][] koumoku_select_1 = {
						 	 {"1-1"	, "1:���򂠂�@�@2:����Ȃ�"}
							,{"1-2"	, "1:���򂠂�@�@2:����Ȃ�"}
							,{"1-3"	, "1:���򂠂�@�@2:����Ȃ�"}
							,{"4"	, "1:�͂��@�@2:������"}
							,{"5"	, "1:�͂��@�@2:������"}
							,{"6"	, "1:�͂��@�@2:������"}
							,{"7"	, "1:�͂��@�@2:������"}
							,{"8"	, "1:�͂��@�@2:������"}
						};				
				base_y = height_info + base + (base + fontsize)/2;
				String[][] koumoku_select_2 = {
							{"9"	, "1:�͂��@�@2:������"}
							,{"10"	, "1:�͂��@�@2:������"}
							,{"11"	, "1:�͂��@�@2:������"}
							,{"12"	, "1:�����@2:�ӂ��@3:�x��"}
							,{"13"	, "1:�͂��@�@2:������"}
							,{"14"	, "1:�͂��@�@2:������"}
							,{"15"	, "1:�͂��@�@2:������"}
							,{"16"	, "1:�͂��@�@2:������"}
							,{"17"	, "1:�͂��@�@2:������"}
							,{"18"	, "1:�͂��@�@2:������"}
						};				
				int base_3 = 0;//�O�ڂ̈ʒu
				base_3 = height_info + base + (base + fontsize);
				String[][] koumoku_select_3 = {
							{"19"	, "1:�����@�@2:���X"}
							,{""	, "3:�قƂ�ǈ��܂Ȃ�"}
							,{"20"	, "1:1������   2:1�`2������"}
							,{""	, "3:2�`3������ �@4:3���ȏ�"}
							,{"22"	, "1:�͂��@�@2:������"}
							,{"23"	, "1:���P�������͂Ȃ�"}
							,{""	, "2:���P�������ł���"}
							,{""	, " �i�T�˂U�����ȓ��j"}
							,{""	, "3:�߂�������(�T�˂P����"}
							,{""	, " �ȓ�)���P�������ł���"}
							,{""	, " �������n�߂Ă���"}
							,{""	, "4:���ɉ��P�Ɏ��g��ł���"}
							,{""	, "�@�i�U���������j"}
							,{""	, "5:���ɉ��P�Ɏ��g��ł���"}
							,{""	, "�@�i�U�����ȏ�j"}
							,{""	, " "}//�u�����N�p(15)
					};				
//				for(int i = 0;i < 18;i++){
//				g2d.drawString(koumoku_select[i][1], 347 , base_y);
//				base_y += base;
//			}
				int base_2 = 0;//��ڂ̈ʒu
				for(int i = 0;i < 8;i++){
					g2d.drawString(koumoku_select_1[i][1], 347 , base_y);
					base_y += base;
					base_2 = base;
				}
				base_2 = base_2 + base_y;
				for(int i = 0;i < 9;i++){
					g2d.drawString(koumoku_select_2[i][1], 347 , base_2);
					base_2 += base;
					base_3 = base_2;
				}
				g2d.drawString(koumoku_select_3[0][1], 347 , base_3);
				g2d.drawString(koumoku_select_3[1][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base);//�u�����N
				g2d.drawString(koumoku_select_3[2][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[3][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base);//�u�����N
				g2d.drawString(koumoku_select_3[4][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base/2);//�u�����N
				g2d.drawString(koumoku_select_3[5][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[6][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[7][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[8][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[9][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[11][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[12][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[13][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[14][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base/2);//�u�����N
				g2d.drawString(koumoku_select_3[4][1], 347 , base_3+=base/2);
					
		/*
		 * �f�[�^�̈ڑ��ƕ\��
		 */	
			try{
			/*
			 * �l���
			 * ��f���ԍ��@�@�����@�@���f���{���@�@�ی��Ҕԍ��@�@��ی��ҏؓ��L���@�@��ی��ҏؓ��ԍ�
			 * ��f���͂��̗p��������������t���w�肷��B
			 */
				/*
				 * PrintData����l���𒊏o
				 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
				//����
				//��f���ԍ�
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 50, 50);
				//����
				g2d.drawString(KojinData.get("KANANAME"), 180, 50);
				//��f��
				g2d.drawString(KojinData.get("KENSA_NENGAPI"), 360, 50);
					
				//����
				//�ی��Ҕԍ�
				g2d.drawString(KojinData.get("HKNJANUM"), 50, 68);
				//��ی��ҏؓ��L��
				g2d.drawString(KojinData.get("HIHOKENJYASYO_KIGOU"), 180, 68);
				//��ی��ҏؓ��ԍ�
				g2d.drawString(KojinData.get("HIHOKENJYASYO_NO"), 360, 68);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

