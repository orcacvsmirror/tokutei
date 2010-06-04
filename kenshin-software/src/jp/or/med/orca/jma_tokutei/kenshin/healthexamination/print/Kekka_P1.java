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
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

/**
 * ���茒�f���ʒʒm�\�@1�y�[�W
 */
public class Kekka_P1 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P1();
	}
	
	public Kekka_P1(){}

	/**
	 * ���茒�f���ʒʒm�\�@�y�[�W1
	 * @param Graphics g, PageFormat pf, int pageIndex, Hashtable<String, Object>PrintData 
	 * Printdate Keys => Kojin, Kikan, KenshinKekka
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{

		/*
		 * ����y�[�W�U�蕪��
		 */
		if( pageIndex == 0){
			/*
			 * 1�y�[�W�ڂ����
			 */
		} else if( pageIndex == 1){
			/*
			 * 2�y�[�W�ڂ����
			 */
			Kekka_P2 Kekka = new Kekka_P2();
			Kekka.setQueryResult(printData);
			Kekka.print(g, pf, pageIndex);
			return Printable.PAGE_EXISTS;
		} else if( pageIndex == 2){
			/*
			 * 3�y�[�W�ڂ����
			 */
			Kekka_P3 Kekka = new Kekka_P3();
			Kekka.setQueryResult(printData);
			Kekka.print(g, pf, pageIndex);
			return Printable.PAGE_EXISTS;
		} else
		if( pageIndex == 3){
			/*
			 * 4�y�[�W�ڂ����
			 */
			Kekka_P4 Kekka = new Kekka_P4();
			Kekka.setQueryResult(printData);
			Kekka.print(g, pf, pageIndex);
			return Printable.PAGE_EXISTS;
		} else {
			/*
			 * �I��
			 */
			return Printable.NO_SUCH_PAGE;
		}

		g.translate( (int)(pf.getImageableX()), (int)(pf.getImageableY()) );

		((Graphics2D)g).transform( java.awt.geom.AffineTransform.getScaleInstance( 1, 1 ) );

		// ���������
		Graphics2D g2d = (Graphics2D)g;
		
		
		/*
		 * �����ݒ�
		 * A4�̘g��`��(72dpi 595px*842px)
		 * width : 451px	�]��25.4mm�l��
		 * height: 695px	�]��25.4mm�l��	
		 */
			final int x_w = 451;
			final int y_h = 695;
			
			/*
			 * �e���ڂ̕�
			 */
			int base = 14;
			
			/*
			 * �l��񕔕��̍���
			 */
			int height_info = 112;
			
			/*
			 * �g�Ƙg�̕�
			 */
			int height_middle = 20;
			
			/*
			 * �`�悷����̑���
			 */
			BasicStroke stroke;
			stroke = new BasicStroke(0.2f);
			g2d.setStroke(stroke);
			
			/*
			 * �����̐ݒ�
			 */
			int fontsize = 8;
			Font f = new Font("Dialog" , Font.PLAIN, fontsize);
			g2d.setFont(f);
			
			/*
			 * y���W��_
			 */
			int base_y = 0;
			
			/*
			 * �s����_
			 */
			int base_row = 0;
		
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
					fontsize = 8;

			
		/*
		 * �g�̕`��
		 */
			//�O�g
				g2d.drawRect( 0, 0, x_w, y_h);
			
			/*
			 * �l���
			 */
				base_y = height_info;
				base_row = 2;
				
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);

				//��
				for (int i = 0;i < base_row; i++){
					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//�c
				g2d.drawLine( 70, base_y,  70, base_y + base_row*base);
				g2d.drawLine(170, base_y, 170, base_y + base_row*base);
				g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(310, base_y, 310, base_y + base_row*base);
				g2d.drawLine(370, base_y, 370, base_y + base_row*base);

			/*
			 * ������
			 */
				base_y += base_row*base + height_middle;
				base_row = 4;
				
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);

				//��
				for (int i = 0;i < base_row; i++){
					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//�c
				g2d.drawLine( 70, base_y,  70, base_y + base_row*base);
				g2d.drawLine(310, base_y + 1*base, 310, base_y + 2*base);
				g2d.drawLine(370, base_y + 1*base, 370, base_y + 2*base);
			
				
			
			/*
			 * ���茋�ʊȈ�
			 */
				base_y += base_row*base + height_middle;
				base_row = 18;
				
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), base_row * base);

				base_row = 2;
				
				//��
				g2d.drawLine(275, base_y + base, 441, base_y + base);
				
				//�c
				/*add yabu 2008/03/12*/
				//g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(240, base_y, 240, base_y + base_row*base);
				g2d.drawLine(170, base_y, 170, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(275, base_y, 275, base_y + base_row*base);
				g2d.drawLine(330, base_y, 330, base_y + base_row*base);
				g2d.drawLine(385, base_y, 385, base_y + base_row*base);
				
				base_y += 2*base;
				base_row = 16;
				
				//��
				g2d.drawLine(10, base_y, 70, base_y);
				g2d.drawLine(10, base_y + 4*base, 70, base_y + 4*base);
				g2d.drawLine(10, base_y + 6*base, 70, base_y + 6*base);
				g2d.drawLine(10, base_y + 9*base, 70, base_y + 9*base);
				g2d.drawLine(10, base_y + 12*base, 70, base_y + 12*base);
				g2d.drawLine(10, base_y + 14*base, 70, base_y + 14*base);
				g2d.drawLine(10, base_y + 16*base, 70, base_y + 16*base);
				for (int i = 0;i < base_row; i++){
					g2d.drawLine(70, base_y + i*base, 441, base_y + i*base);			
				}
				
				//�c
				g2d.drawLine( 70, base_y,  70, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(120, base_y, 120, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(170, base_y, 170, base_y + base_row*base);
				/* yabu 2008/03/12*/
				//g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(240, base_y, 240, base_y + base_row*base);
				/* yabu 2008/03/12*/
				g2d.drawLine(275, base_y, 275, base_y + base_row*base);
				g2d.drawLine(330, base_y, 330, base_y + base_row*base);
				g2d.drawLine(385, base_y, 385, base_y + base_row*base);

				
				
		/*
		 * �t�H�[�}�b�g
		 */	
			/*
			 * ���f�@�֏��
			 */
				/* yabu 2008/03/12 */
				//g2d.drawString("���茒�f���ʒʒm�\", 441/2 - 9*fontsize/2, 20);
				fontsize = 12;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
    			g2d.drawString("���茒�f���ʒʒm�\", 441/2 - 9*fontsize, 20);
				fontsize = 8;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				/* yabu 2008/03/12 */
				
				//�����̐ݒ�
				fontsize = 7;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				
				g2d.drawString("���f�@�֖�"	, 250, 40);
				g2d.drawString("��"			, 250, 50);
				g2d.drawString("�d�b�ԍ�"		, 250, 60);
				g2d.drawString("�Z��"		, 250, 80);
		
			/*
			 * �l���
			 */
				//1�s��
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("�t���K�i"		, 10+(70-10)/2 - 4*fontsize/2, base_y);
				g2d.drawString("���N����"		, 170+(220-170)/2 - 4*fontsize/2, base_y);
				g2d.drawString("��f��"		, 310+(370-310)/2 - 3*fontsize/2, base_y);
				
				//2�s��
				base_y += base;
				g2d.drawString("����"			, 10+(70-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("���ʁE�N��"		, 170+(220-170)/2 - 5*fontsize/2, base_y);
				g2d.drawString("��f�������ԍ�"	, 310+(370-310)/2 - 7*fontsize/2, base_y);

			/*
			 * ������
			 */
				//1���
				base_y += height_middle + base;
				g2d.drawString("������"		, 10+(70-10)/2 - 3*fontsize/2, base_y); base_y += base;
				g2d.drawString("������"		, 10+(70-10)/2 - 3*fontsize/2, base_y);
				g2d.drawString("�i����"		, 310+(370-310)/2 - 3*fontsize/2, base_y); base_y += base;
				g2d.drawString("���o�Ǐ�"		, 10+(70-10)/2 - 4*fontsize/2, base_y); base_y += base;
				g2d.drawString("���o�Ǐ�"		, 10+(70-10)/2 - 4*fontsize/2, base_y);
			
			/*
			 * ���茋�ʊȈ�
			 */
				//1�s��
				base_y += height_middle + base;
				/* yabu 2008/03/12 */
				//g2d.drawString("����"		, 70+(170-70)/2 - 2*fontsize/2, base_y + base/2);
				//g2d.drawString("��l"		, 220+(275-220)/2 - 3*fontsize/2, base_y + base/2);
				g2d.drawString("����"		, 84, base_y + base/2);
				g2d.drawString("��l"		, 194, base_y + base/2);
				g2d.drawString("H/L"		, 254, base_y + base/2);
				/* yabu 2008/03/12 */
				g2d.drawString("����"		, 275+(330-275)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O��"		, 330+(385-330)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O�X��"		, 385+(441-385)/2 - 3*fontsize/2, base_y);
				
				//1���
				base_y += base + base/2;
				g2d.drawString("�g�̌v��"		, 10+(70-10)/2 - 4*fontsize/2, base_y + 4*base/2); base_y += 4*base;
				g2d.drawString("����"		, 10+(70-10)/2 - 2*fontsize/2, base_y + 2*base/2); base_y += 2*base;
				g2d.drawString("������������"	, 10+(70-10)/2 - 6*fontsize/2, base_y + 3*base/2); base_y += 3*base;
				g2d.drawString("�̋@�\����"	, 10+(70-10)/2 - 5*fontsize/2, base_y + 3*base/2); base_y += 3*base;
				g2d.drawString("��������"		, 10+(70-10)/2 - 4*fontsize/2, base_y + 2*base/2); base_y += 2*base;
				g2d.drawString("�A����"		, 10+(70-10)/2 - 3*fontsize/2, base_y + 2*base/2);
				
				//2���
				base_y -= 13*base + base/2;
				String[][] koumoku_name = {
							 {"�g��", "cm"},{"�̏d", "kg"},{"����","cm"},{"BMI", ""}
							,{"���k������", "mmHg"},{"�g��������", "mmHg"}
							,{"�������b", "mg/dl"},{"HDL-�ڽ�۰�-", "mg/dl"},{"LDL-�ڽ�۰�-", "mg/dl"}
							,{"GOT", "U/I"},{"GPT", "U/I"},{"��-GTP", "U/I"}
							,{"�󕠎�����", "mg/dl"},{"�Ӹ�����A��c", "%"}
							,{"��", ""},{"�`��", ""}
						};
				for(int i = 0;i < 16;i++){
					g2d.drawString(koumoku_name[i][0], 72, base_y + i*base);
					//** yabu 2008/03/12**//
					//g2d.drawString(koumoku_name[i][1], 182, base_y + i*base);
					g2d.drawString(koumoku_name[i][1], 132, base_y + i*base);
					//** yabu 2008/03/12**//
				}
		
		/*
		 * �f�[�^�̈ڑ��ƕ\��
		 */
			try{
			/*
			 * ���f�@�֏��
			 * PrintData���猒�f�@�֏��𒊏o
			 */
				Kikan tmpKikan = (Kikan)printData.get("Kikan");
				Hashtable<String, String> KikanData = tmpKikan.getPrintKikanData();
				
				//���f�@�֖�
				g2d.drawString(KikanData.get("KIKAN_NAME"), 300, 40);
				//�X�֔ԍ�
				g2d.drawString(KikanData.get("POST"), 300, 50);
				//�d�b�ԍ�
				g2d.drawString(KikanData.get("TEL"), 300, 60);
				//�Z��
				g2d.drawString(KikanData.get("ADR"), 300, 80);
				//�n�ԕ���
				g2d.drawString(KikanData.get("TIBAN"), 300, 80 + fontsize);
				
			/*
			 * �l���
			 * PrintData����l���𒊏o
			 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();

				//����
				base_y = height_info + (base + fontsize)/2;
				//�t���K�i
				g2d.drawString(KojinData.get("KANANAME"), 72, base_y);
				//���N����
				g2d.drawString(KojinData.get("BIRTHDAY"), 222, base_y);
				//���f�N����
				g2d.drawString(KojinData.get("KENSA_NENGAPI"), 372, base_y);
				
				//����
				base_y = height_info + (base + fontsize)/2 + base;
				//����
				g2d.drawString(KojinData.get("NAME"), 72, base_y);
				//���ʁE�N��
				g2d.drawString(KojinData.get("SEX")+ "  " +KojinData.get("AGE"), 222, base_y);
				//��f�������ԍ�
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 372, base_y);
			
			/*
			 * ���f���ʏ��
			 * PrintData���猒�f���ʏ��𒊏o
			 */
				ArrayList<String> tmp = new ArrayList<String>();
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
				 * �������A�����ǁA���o�Ǐ�A���o�Ǐ�A�i����
				 */
					base_y = height_info + (base + fontsize)/2 + 2*base + height_middle;
					if(KenshinKekkaData.size()>0){
						//������
						g2d.drawString(KenshinKekkaData.get(0).get("9N056000000000011").get("CODE_NAME"), 72, base_y + 0*base);
						//�����ǁ@�d�l����`�̂��ߖ�����
						//g2d.drawString("", 72, base_y + 0*base);
						//���o�Ǐ�
						g2d.drawString(KenshinKekkaData.get(0).get("9N061000000000011").get("CODE_NAME"), 72, base_y + 2*base);
						//���o�Ǐ�
						g2d.drawString(KenshinKekkaData.get(0).get("9N066000000000011").get("CODE_NAME"), 72, base_y + 3*base);
						//�i����
						g2d.drawString(KenshinKekkaData.get(0).get("9N736000000000011").get("CODE_NAME"), 372, base_y + 1*base);
					}
				
				/*
				 * ���f�N�����Z�b�g
				 * ����A�O��A�O�X��
				 */
					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 1*base;
					
					try {
						//����
						g2d.drawString(KensaNengapi[0], 277, base_y);
						//�O��
						g2d.drawString(KensaNengapi[1], 332, base_y);
						//�O�X��
						g2d.drawString(KensaNengapi[2], 387, base_y);
					} catch (NullPointerException e) {
					}
				/*
				 * ��l�Z�b�g
				 * �g�̑���A�����A�������������A�̋@�\�����A���������A�A����
				 * �g���A�̏d�A���͂͂���Ȃ��Ƃ̂���
				 */
					/*2008/02/12yabu �ǉ�
					 * ��l
					 * �j���A����
					 */
					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
					if (KojinData.get("SEX").equals("�j��")){
						//�g��
//						g2d.drawString(KenshinKekkaData.get(0).get("9N001000000000001").get("DS_KAGEN")+"�`"
//								+ KenshinKekkaData.get(0).get("9N001000000000001").get("DS_JYOUGEN") , 172, base_y + 0*base);
//						//�̏d
//						g2d.drawString(KenshinKekkaData.get(0).get("9N006000000000001").get("DS_KAGEN")+"�`"
//								+ KenshinKekkaData.get(0).get("9N006000000000001").get("DS_JYOUGEN") , 172, base_y + 1*base);
//						// ���́i�����j		9N016160100000001
//						g2d.drawString(KenshinKekkaData.get(0).get("9N016160100000001").get("DS_KAGEN")+"�`"
//								+ KenshinKekkaData.get(0).get("9N016160100000001").get("DS_JYOUGEN") , 172, base_y + 2*base);
//						//BMI
						tmp.clear(); tmp.add("9N011000000000001"); tmp.add("9N011000000000001");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0) , 172, base_y + 3*base);
						}
						/*
						 * ���k�������i1��ځj	9A751000000000001
						 * ���k�������i2��ځj	9A752000000000001
						 * ���k�������i���̑��j	9A755000000000001
						 */
						tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 4*base);
						}
						/*
						 * �g���������i���ځj	9A761000000000001
						 * �g���������i���ځj	9A762000000000001
						 * �g���������i���̑��j	9A765000000000001
						 */
						tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 5*base);
						}
						/*
						 * �������b�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327101
						 * �������b�i���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327201
						 * �������b�i���̑��j								3F015000002399901
						 */
						tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 6*base);
						}
						/*
						 * HDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327101
						 * HDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327201
						 * HDL-�R���X�e���[��-�i���̑��j						3F070000002399901
						 */
						tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 7*base);
						}
						/*
						 * LDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327101
						 * LDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327201
						 * LDL-�R���X�e���[��-�i���̑��j						3F077000002399901
						 */
						tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 8*base);
						}
						/*
						 * GOT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B035000002327201
						 * GOT�i���̑��j									3B035000002399901
						 */
						tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 9*base);
						}
						/*
						 * GPT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B045000002327201
						 * GPT�i���̑��j									3B045000002399901
						 */
						tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 10*base);
						}
						/*
						/*
						 * y-GTP�i���z�����x�@�iJSCC�W�����Ή��@�j�j			3B090000002327101
						 * y-GTP�i���̑��j									3B090000002399901
						 */
						tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 11*base);
						}
						/*
						 * �󕠎������i�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j�j			3D010000001926101
						 * �󕠎������i���z�����x�@�i�u�h�E���_���y�f�@�j�j			3D010000001727101
						 * �󕠎������i���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j�j	3D010000001927201
						 * �󕠎������i���̑��j								3D010000001999901
						 */
						tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000001727101");
						tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 12*base);
						}
						/*
						 * �Ӹ�����A1c	�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j	3D045000001906202
						 * �Ӹ�����A1c	HPLC(�s���蕪�揜��HPLC�@�j			3D045000001920402
						 * �Ӹ�����A1c	�y�f�@							3D045000001927102
						 * �Ӹ�����A1c	���̑�							3D045000001999902
						 */
						tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402");
						tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 13*base);
						}
//						/*
//						 * ��	�������@�i�@�B�ǂݎ��j						1A020000000191111
//						 * ��	�������@�i�ڎ��@�j							1A020000000190111
//						 */
//						tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "�`"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 14*base);
//						/*
//						 * �`��	�������@�i�@�B�ǂݎ��j						1A010000000191111
//						 * �`��	�������@�i�ڎ��@�j							1A010000000190111
//						 */
//						tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "�`"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 15*base);

						//�����̏ꍇ
					}else if(KojinData.get("SEX").equals("����")){
//						//BMI
						tmp.clear(); tmp.add("9N011000000000001"); tmp.add("9N011000000000001");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0)+"�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0) , 172, base_y + 3*base);
						}
						/*
						 * ���k�������i1��ځj	9A751000000000001
						 * ���k�������i2��ځj	9A752000000000001
						 * ���k�������i���̑��j	9A755000000000001
						 */
						tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 4*base);
						}
						/*
						 * �g���������i���ځj	9A761000000000001
						 * �g���������i���ځj	9A762000000000001
						 * �g���������i���̑��j	9A765000000000001
						 */
						tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 5*base);
						}
						/*
						 * �������b�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327101
						 * �������b�i���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327201
						 * �������b�i���̑��j								3F015000002399901
						 */
						tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 6*base);
						}
						/*
						 * HDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327101
						 * HDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327201
						 * HDL-�R���X�e���[��-�i���̑��j						3F070000002399901
						 */
						tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 7*base);
						}
						/*
						 * LDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327101
						 * LDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327201
						 * LDL-�R���X�e���[��-�i���̑��j						3F077000002399901
						 */
						tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 8*base);
						}
						/*
						 * GOT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B035000002327201
						 * GOT�i���̑��j									3B035000002399901
						 */
						tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 9*base);
						}
						/*
						 * GPT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B045000002327201
						 * GPT�i���̑��j									3B045000002399901
						 */
						tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 10*base);
						}
						/*
						/*
						 * y-GTP�i���z�����x�@�iJSCC�W�����Ή��@�j�j			3B090000002327101
						 * y-GTP�i���̑��j									3B090000002399901
						 */
						tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 11*base);
						}
						/*
						 * �󕠎������i�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j�j			3D010000001926101
						 * �󕠎������i���z�����x�@�i�u�h�E���_���y�f�@�j�j			3D010000001727101
						 * �󕠎������i���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j�j	3D010000001927201
						 * �󕠎������i���̑��j								3D010000001999901
						 */
						tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000001727101");
						tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 12*base);
						}
						/*
						 * �Ӹ�����A1c	�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j	3D045000001906202
						 * �Ӹ�����A1c	HPLC(�s���蕪�揜��HPLC�@�j			3D045000001920402
						 * �Ӹ�����A1c	�y�f�@							3D045000001927102
						 * �Ӹ�����A1c	���̑�							3D045000001999902
						 */
						tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402");
						tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 13*base);
						}
//						/*
//						 * ��	�������@�i�@�B�ǂݎ��j						1A020000000191111
//						 * ��	�������@�i�ڎ��@�j							1A020000000190111
//						 */
//						tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "�`"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 14*base);
//						/*
//						 * �`��	�������@�i�@�B�ǂݎ��j						1A010000000191111
//						 * �`��	�������@�i�ڎ��@�j							1A010000000190111
//						 */
//						tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "�`"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 15*base);

					}

//					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
//					//�g��
//					g2d.drawString(KenshinKekkaData.get(0).get("9N001000000000001").get("KIJYUNTI_HANI") , 222, base_y + 0*base);
//					//�̏d
//					g2d.drawString(KenshinKekkaData.get(0).get("9N006000000000001").get("KIJYUNTI_HANI") , 222, base_y + 1*base);
//					/*
//					 * ���́i�����j		9N016160100000001
//					 * ���́i���Ȕ���j	9N016160200000001
//					 * ���́i���Ȑ\���j	9N016160300000001
//					 */
//					tmp.clear(); tmp.add("9N016160100000001"); tmp.add("9N016160200000001"); tmp.add("9N016160300000001");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0), 222, base_y + 2*base);
//					//BMI
//					g2d.drawString(KenshinKekkaData.get(0).get("9N011000000000001").get("KIJYUNTI_HANI") , 222, base_y + 3*base);
//					/*
//					 * ���k�������i1��ځj	9A751000000000001
//					 * ���k�������i2��ځj	9A752000000000001
//					 * ���k�������i���̑��j	9A755000000000001
//					 */
//					tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0) , 222, base_y + 4*base);
//					/*
//					 * �g���������i���ځj	9A761000000000001
//					 * �g���������i���ځj	9A762000000000001
//					 * �g���������i���̑��j	9A765000000000001
//					 */
//					tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 5*base);
//					/*
//					 * �������b�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327101
//					 * �������b�i���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327201
//					 * �������b�i���̑��j								3F015000002399901
//					 */
//					tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 6*base);
//					/*
//					 * HDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327101
//					 * HDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327201
//					 * HDL-�R���X�e���[��-�i���̑��j						3F070000002399901
//					 */
//					tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 7*base);
//					/*
//					 * LDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327101
//					 * LDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327201
//					 * LDL-�R���X�e���[��-�i���̑��j						3F077000002399901
//					 */
//					tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 8*base);
//					/*
//					 * GOT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B035000002327201
//					 * GOT�i���̑��j									3B035000002399901
//					 */
//					tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 9*base);
//					/*
//					 * GPT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B045000002327201
//					 * GPT�i���̑��j									3B045000002399901
//					 */
//					tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 10*base);
//					/*
//					 * y-GTP�i���z�����x�@�iJSCC�W�����Ή��@�j�j			3B090000002327101
//					 * y-GTP�i���̑��j									3B090000002399901
//					 */
//					tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 11*base);
//					/*
//					 * �󕠎������i�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j�j			3D010000001926101
//					 * �󕠎������i���z�����x�@�i�u�h�E���_���y�f�@�j�j			3D010000002227101
//					 * �󕠎������i���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j�j	3D010000001927201
//					 * �󕠎������i���̑��j								3D010000001999901
//					 */
//					tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000002227101"); tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 12*base);
//					/*
//					 * �Ӹ�����A1c	�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j	3D045000001906202
//					 * �Ӹ�����A1c	HPLC(�s���蕪�揜��HPLC�@�j			3D045000001920402
//					 * �Ӹ�����A1c	�y�f�@							3D045000001927102
//					 * �Ӹ�����A1c	���̑�							3D045000001999902
//					 */
//					tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402"); tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 13*base);
//					/*
//					 * ��	�������@�i�@�B�ǂݎ��j						1A020000000191111
//					 * ��	�������@�i�ڎ��@�j							1A020000000190111
//					 */
//					tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 14*base);
//					/*
//					 * �`��	�������@�i�@�B�ǂݎ��j						1A010000000191111
//					 * �`��	�������@�i�ڎ��@�j							1A010000000190111
//					 */
//					tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 15*base);
				/*2008/02/12yabu �ǉ�*/
				
				/*�@
				 * �g/�k���� 
				 * �g���A�̏d�A���͂͂���Ȃ�
				 */
				base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
//				�g��
//				g2d.drawString(KenshinKekkaData.get(0).get("9N001000000000001").get("H_L"), 250, base_y + 0*base);
//				�̏d
//				g2d.drawString(KenshinKekkaData.get(0).get("9N006000000000001").get("H_L"), 250, base_y + 1*base);
//				 ���́i�����j		9N016160100000001
//				g2d.drawString(KenshinKekkaData.get(0).get("9N016160100000001").get("H_L"), 250, base_y + 2*base);
				// BMI
				tmp.clear(); tmp.add("9N011000000000001");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 3*base);
				/*
				 * ���k�������i1��ځj	9A751000000000001
				 * ���k�������i2��ځj	9A752000000000001
				 * ���k�������i���̑��j	9A755000000000001
				 */
				tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 4*base);
				/*
				 * �g���������i���ځj	9A761000000000001
				 * �g���������i���ځj	9A762000000000001
				 * �g���������i���̑��j	9A765000000000001
				 */
				tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 5*base);
				/*
				 * �������b�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327101
				 * �������b�i���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327201
				 * �������b�i���̑��j								3F015000002399901
				 */
				tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 6*base);
				/*
				 * HDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327101
				 * HDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327201
				 * HDL-�R���X�e���[��-�i���̑��j						3F070000002399901
				 */
				tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 7*base);
				/*
				 * LDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327101
				 * LDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327201
				 * LDL-�R���X�e���[��-�i���̑��j						3F077000002399901
				 */
				tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 8*base);
				/*
				 * GOT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B035000002327201
				 * GOT�i���̑��j									3B035000002399901
				 */
				tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 9*base);
				/*
				 * GPT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B045000002327201
				 * GPT�i���̑��j									3B045000002399901
				 */
				tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 10*base);
				/*
				/*
				 * y-GTP�i���z�����x�@�iJSCC�W�����Ή��@�j�j			3B090000002327101
				 * y-GTP�i���̑��j									3B090000002399901
				 */
				tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 11*base);
				/*
				 * �󕠎������i�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j�j			3D010000001926101
				 * �󕠎������i���z�����x�@�i�u�h�E���_���y�f�@�j�j			3D010000001727101
				 * �󕠎������i���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j�j	3D010000001927201
				 * �󕠎������i���̑��j								3D010000001999901
				 */
				tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000001727101");
				tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 12*base);
				/*
				 * �Ӹ�����A1c	�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j	3D045000001906202
				 * �Ӹ�����A1c	HPLC(�s���蕪�揜��HPLC�@�j			3D045000001920402
				 * �Ӹ�����A1c	�y�f�@							3D045000001927102
				 * �Ӹ�����A1c	���̑�							3D045000001999902
				 */
				tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402");
				tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 13*base);
				/*
				 * ��	�������@�i�@�B�ǂݎ��j						1A020000000191111
				 * ��	�������@�i�ڎ��@�j							1A020000000190111
				 */
				tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 14*base);
				/*
				 * �`��	�������@�i�@�B�ǂݎ��j						1A010000000191111
				 * �`��	�������@�i�ڎ��@�j							1A010000000190111
				 */
				tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 15*base);

				/*
				 * �g�̑���A�����A�������������A�̋@�\�����A���������A�A����
				 * ����A�O��A�O�X��
				 */
					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
					for(int i=0;i < KensaNengapi.length; i++){
						try {
							//�g��
							g2d.drawString(KenshinKekkaData.get(i).get("9N001000000000001").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
							//�̏d
							g2d.drawString(KenshinKekkaData.get(i).get("9N006000000000001").get("KEKA_TI") , 277 + i*55, base_y + 1*base);
							/*
							 * ���́i�����j		9N016160100000001
							 * ���́i���Ȕ���j	9N016160200000001
							 * ���́i���Ȑ\���j	9N016160300000001
							 */
							tmp.clear(); tmp.add("9N016160100000001"); tmp.add("9N016160200000001"); tmp.add("9N016160300000001");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 2*base);
							//BMI
							g2d.drawString(KenshinKekkaData.get(i).get("9N011000000000001").get("KEKA_TI") , 277 + i*55, base_y + 3*base);
							/*
							 * ���k�������i1��ځj	9A751000000000001
							 * ���k�������i2��ځj	9A752000000000001
							 * ���k�������i���̑��j	9A755000000000001
							 */
							tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 4*base);
							/*
							 * �g���������i���ځj	9A761000000000001
							 * �g���������i���ځj	9A762000000000001
							 * �g���������i���̑��j	9A765000000000001
							 */
							tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 5*base);
							/*
							 * �������b�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327101
							 * �������b�i���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j�j	3F015000002327201
							 * �������b�i���̑��j								3F015000002399901
							 */
							tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 6*base);
							//HDL-�R���X�e���[��-
							/*
							 * HDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327101
							 * HDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j�j	3F070000002327201
							 * HDL-�R���X�e���[��-�i���̑��j						3F070000002399901
							 */
							tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 7*base);
							/*
							 * LDL-�R���X�e���[��-�i���z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327101
							 * LDL-�R���X�e���[��-�i���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j	3F077000002327201
							 * LDL-�R���X�e���[��-�i���̑��j						3F077000002399901
							 */
							tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 8*base);
							/*
							 * GOT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B035000002327201
							 * GOT�i���̑��j									3B035000002399901
							 */
							tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 9*base);
							/*
							 * GPT�i���O�z�����x�@�iJSCC�W�����Ή��@�j�j				3B045000002327201
							 * GPT�i���̑��j									3B045000002399901
							 */
							tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 10*base);
							/*
							 * y-GTP�i���z�����x�@�iJSCC�W�����Ή��@�j�j			3B090000002327101
							 * y-GTP�i���̑��j									3B090000002399901
							 */
							tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 11*base);
							/*
							 * �󕠎������i�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j�j			3D010000001926101
							 * �󕠎������i���z�����x�@�i�u�h�E���_���y�f�@�j�j			3D010000002227101
							 * �󕠎������i���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j�j	3D010000001927201
							 * �󕠎������i���̑��j								3D010000001999901
							 */
							tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000002227101"); tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 12*base);
							/*
							 * �Ӹ�����A1c	�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j	3D045000001906202
							 * �Ӹ�����A1c	HPLC(�s���蕪�揜��HPLC�@�j			3D045000001920402
							 * �Ӹ�����A1c	�y�f�@							3D045000001927102
							 * �Ӹ�����A1c	���̑�							3D045000001999902
							 */
							tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402"); tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 13*base);

							
							/*
							 * ��	�������@�i�@�B�ǂݎ��j						1A020000000191111
							 * ��	�������@�i�ڎ��@�j							1A020000000190111
							 */
							tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
							//g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 14*base);
			    			if (tmpKenshinKekka.getKekati(tmp, i).length() >= 9) {
				    			int fn = fontsize;
			    				fontsize = 4;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 14*base);
				    			fontsize = fn;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
			    			} else {
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 14*base);
			    			}
							/*
							 * �`��	�������@�i�@�B�ǂݎ��j						1A010000000191111
							 * �`��	�������@�i�ڎ��@�j							1A010000000190111
							 */
							tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
							//g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 15*base);
			    			if (tmpKenshinKekka.getKekati(tmp, i).length() >= 9) {
				    			int fn = fontsize;
			    				fontsize = 4;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 15*base);
				    			fontsize = fn;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
			    			} else {
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 15*base);
			    			}

						} catch (IndexOutOfBoundsException e) {
						}
					}

				/*
				 * ��f�������ԍ��ƃy�[�W�ԍ�
				 */
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 380, 670);
				g2d.drawString("Page.1", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}

}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

