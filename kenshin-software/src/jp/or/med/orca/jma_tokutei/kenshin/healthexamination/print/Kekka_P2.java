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

/**
 * ���茒�f���ʒʒm�\�@2�y�[�W
 */
public class Kekka_P2 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P2();
	}
	
	public Kekka_P2(){}

	/**
	 * ���茒�f���ʒʒm�\�@�y�[�W2
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * Printdate Keys => Kojin, Kikan, KenshinKekka
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
			int base = 14;
			
			/*
			 * �l��񕔕��̍���
			 */
			int height_info = 20;
			
			/*
			 * �g�Ƙg�̕�
			 */
			int height_middle = 10;
			
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
					fontsize = 7;
					f = new Font("Dialog" , Font.PLAIN, fontsize);
					g2d.setFont(f);

		/*
		 * �g�̕`��
		 */
			//�O�g
				g2d.drawRect( 0, 0, x_w, y_h);
			
			/*
			 * ���f����
			 */
				base_y = height_info;
				base_row = 5;
				
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), base_row * base);

				//��
				g2d.drawLine(275, base_y + 1*base, 441, base_y + base);
				g2d.drawLine(10, base_y + 2*base, 441, base_y + 2*base);
				g2d.drawLine(70, base_y + 3*base, 441, base_y + 3*base);
				g2d.drawLine(70, base_y + 4*base, 441, base_y + 4*base);
				
				//�c
				g2d.drawLine( 70, base_y + 2*base,  70, base_y + base_row*base);
				/* yabu 2008/03/12*/
				//g2d.drawLine(165, base_y, 165, base_y + base_row*base);
				g2d.drawLine(140, base_y, 140, base_y + base_row*base);
				/* yabu 2008/03/12*/
				g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(275, base_y, 275, base_y + base_row*base);
				g2d.drawLine(330, base_y, 330, base_y + base_row*base);
				g2d.drawLine(385, base_y, 385, base_y + base_row*base);
			/*
			 * �S�d�}�A��ꌟ���@����
			 */
				base_y += base_row * base + height_middle;
				base_row = 19;
				
				//�g
				g2d.drawRect(10, base_y, (x_w-20), base_row * base);
				
				//��
				g2d.drawLine(10, base_y + 1*base, 441, base_y + 1*base);
				g2d.drawLine(10, base_y + 10*base, 441, base_y + 10*base);
				
				//�c
				g2d.drawLine(100, base_y, 100, base_y + base_row*base);
				g2d.drawLine(213, base_y, 213, base_y + base_row*base);
				g2d.drawLine(326, base_y, 326, base_y + base_row*base);

			/*
			 * ���^�{���b�N�V���h���[������
			 */
				base_y += base_row*base + height_middle;
				base_row = 1;
				
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);

				//�c
				g2d.drawLine(275, base_y,  275, base_y + base_row*base);
				g2d.drawLine(330, base_y,  330, base_y + base_row*base);
				g2d.drawLine(385, base_y,  385, base_y + base_row*base);
			
			/*
			 * ��t�̔��f
			 */
				base_y += base_row*base + height_middle;
				base_row = 17;
				
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), base_row * base);

				//��
				g2d.drawLine(10, base_y + 15*base, 441, base_y + 15*base);
				
				//�c
				g2d.drawLine(100, base_y, 100, base_y + base_row*base);
				
		/*
		 * �t�H�[�}�b�g
		 */	
			/*
			 * ���f����
			 */
				//1�s��
				base_y = (base + fontsize)/2 + height_info;
				base_row = 5;
				
				g2d.drawString("����"		, 10+(165-10)/2 - 2*fontsize/2, base_y + base/2);
				/* yabu 2008/03/12*/
				//g2d.drawString("��l"		, 165+(220-165)/2 - 3*fontsize/2, base_y + base/2);
				g2d.drawString("��l"		, 140+(220-140)/2 - 3*fontsize/2, base_y + base/2);
				/* yabu 2008/03/12*/
				g2d.drawString("�P��"		, 220+(275-220)/2 - 2*fontsize/2, base_y + base/2);
				
				g2d.drawString("����"		, 275+(330-275)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O��"		, 330+(385-330)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O�X��"		, 385+(441-385)/2 - 3*fontsize/2, base_y);
				
				//1���
				g2d.drawString("�n������"		, 10+(70-10)/2 - 4*fontsize/2, base_y + 2*base);
				
				//2���
				g2d.drawString("�Ԍ�����"			, 72, base_y + 2*base);
				g2d.drawString("���F�f��"			, 72, base_y + 3*base);
				g2d.drawString("�w�}�g�N���b�g�l"		, 72, base_y + 4*base);
				
				//4��ځ@�P��
				g2d.drawString("��/�u", 232, base_y + 2*base);
				g2d.drawString("g/dl", 232, base_y + 3*base);
				g2d.drawString("%", 232, base_y + 4*base);
				

			/*
			 * �S�d�}�A��ꌟ���@����
			 */
				base_y += base_row * base + height_middle;
				base_row = 19;

				//1�s��
				g2d.drawString("����", 100+(213-100)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O��", 213+(326-213)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O�X��", 326+(441-326)/2 - 2*fontsize/2, base_y);
				
				//1���
				g2d.drawString("�S�d�}�����@����"	, 10+(100-10)/2 - 8*fontsize/2, base_y + 5*base);
				g2d.drawString("��ꌟ���@����"		, 10+(100-10)/2 - 7*fontsize/2, base_y + 14*base);
				
				
			/*
			 * ���^�{���b�N�V���h���[������
			 */
				base_y += base_row * base + height_middle;
				base_row = 1;
				
				g2d.drawString("���^�{���b�N�V���h���[������", 10+(275-10)/2 - 13*fontsize/2, base_y);
			
			/*
			 * ��t�̔��f
			 */
				//1�s��
				base_y += base_row*base + height_middle;
				base_row = 17;
				
				//1���
				g2d.drawString("��t�̔��f"		, 10+(100-10)/2 - 5*fontsize/2, base_y + 7*base);
				g2d.drawString("���f������t��"		, 10+(100-10)/2 - 7*fontsize/2, base_y + 15*base + base/2);
		
		/*
		 * �f�[�^�̈ڑ��ƕ\��
		 */
			try{
			/*
			 * �l���
			 * PrintData����l���𒊏o
			 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
			/*
			 * ���f����
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
				 * �n������
				 * �Ԍ������A���F�f���A�w�}�g�N���b�g�l
				 */
				base_y = (base + fontsize)/2 + height_info;
				base_row = 5;
				
					/*
					 * ���f�N�����Z�b�g
					 * ����A�O��A�O�X��
					 */
					try {
						//����
						g2d.drawString(KensaNengapi[0], 277, base_y + base);
						//�O��
						g2d.drawString(KensaNengapi[1], 332, base_y + base);
						//�O�X��
						g2d.drawString(KensaNengapi[2], 387, base_y + base);
					} catch (NullPointerException e) {
					}
					/*
					 * ��l���Z�b�g
					 */
					try {
						ArrayList<String> tmp = new ArrayList<String>();
						if (KojinData.get("SEX").equals("�j��")){
							//�Ԍ�����
							tmp.clear(); tmp.add("2A020000001930101"); tmp.add("2A020000001930101");
							if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
										+ tmpKenshinKekka.getDsJyogen(tmp, 0), 142, base_y + 2*base);
							}
							//���F�f��
							tmp.clear(); tmp.add("2A030000001930101"); tmp.add("2A030000001930101");
							if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
										+ tmpKenshinKekka.getDsJyogen(tmp, 0), 142, base_y + 3*base);
							}
							//�w�}�g�N���b�g�l
							tmp.clear(); tmp.add("2A040000001930102"); tmp.add("2A040000001930102");
							if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "�`"
										+ tmpKenshinKekka.getDsJyogen(tmp, 0), 142, base_y + 4*base);
							}
						}else if (KojinData.get("SEX").equals("����")){
							//�Ԍ�����
							tmp.clear(); tmp.add("2A020000001930101"); tmp.add("2A020000001930101");
							if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
										+ tmpKenshinKekka.getJsJyogen(tmp, 0), 142, base_y + 2*base);
							}
							//���F�f��
							tmp.clear(); tmp.add("2A030000001930101"); tmp.add("2A030000001930101");
							if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
										+ tmpKenshinKekka.getJsJyogen(tmp, 0), 142, base_y + 3*base);
							}
							//�w�}�g�N���b�g�l
							tmp.clear(); tmp.add("2A040000001930102"); tmp.add("2A040000001930102");
							if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
							}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "�`"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 142, base_y + 4*base);
							}
						}
//						//�Ԍ�����
//						g2d.drawString(KenshinKekkaData.get(0).get("2A020000001930101").get("KIJYUNTI_HANI") , 222, base_y + 2*base);
//						//���F�f��
//						g2d.drawString(KenshinKekkaData.get(0).get("2A030000001930101").get("KIJYUNTI_HANI") , 222, base_y + 3*base);
//						//�w�}�g�N���b�g�l
//						g2d.drawString(KenshinKekkaData.get(0).get("2A040000001930102").get("KIJYUNTI_HANI") , 222, base_y + 4*base);
					} catch (NullPointerException e) {
					}
					/*
					 * ���ʒl���Z�b�g
					 */
					try {
						for(int i=0;i < KensaNengapi.length; i++){
							//�Ԍ�����
							g2d.drawString(KenshinKekkaData.get(i).get("2A020000001930101").get("KEKA_TI") , 277 + i*55, base_y + 2*base);
							//�w�}�g�N���b�g�l
							g2d.drawString(KenshinKekkaData.get(i).get("2A030000001930101").get("KEKA_TI") , 277 + i*55, base_y + 3*base);
							//���F�f��
							g2d.drawString(KenshinKekkaData.get(i).get("2A040000001930102").get("KEKA_TI") , 277 + i*55, base_y + 4*base);
//							//�Ԍ�����
//							g2d.drawString(KenshinKekkaData.get(i).get("2A020000001930101").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
//							//�w�}�g�N���b�g�l
//							g2d.drawString(KenshinKekkaData.get(i).get("2A030000001930101").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
//							//���F�f��
//							g2d.drawString(KenshinKekkaData.get(i).get("2A040000001930102").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
						}
					} catch (NullPointerException e) {
					}
				/*
				 * �S�d�}�A��ꌟ���@����
				 */
				base_y += base_row * base + height_middle;
				base_row = 19;
				
					/*
					 * ���ʒl���Z�b�g
					 * ����
					 */
					for(int i=0;i < KensaNengapi.length; i++){
						/*
						 * ���s����
						 * 15����x17�s
						 */
						//�S�d�}
						try{
							if (!KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").isEmpty()){
								for(int j=0;j < 17;j++){
									/*
									 * ������̎擾
									 */
									try {
										KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").substring(j*15, 15+j*15);
									} catch (IndexOutOfBoundsException e) {
										g2d.drawString(KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").substring(j*15), 102 + j*113, base_y + fontsize + fontsize/2 + 2 +  j*fontsize);
										break;
									} catch (NullPointerException e){
										break;
									}
									g2d.drawString(KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").substring(j*15, 15+j*15), 102 + j*113, base_y + fontsize + fontsize/2 + 2 +  j*fontsize);
								}
							}
						} catch	(Exception e){
						}
						//��ꌟ��
						try{
							if (!KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").isEmpty()){
								for(int j=0;j < 17;j++){
									/*
									 * ������̎擾
									 */
									try {
										KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").substring(j*15, 15+j*15);
									} catch (java.lang.StringIndexOutOfBoundsException e) {
										g2d.drawString(KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").substring(j*15), 102 + j*113, base_y  + 9*base + fontsize + fontsize/2 + 2 +  j*fontsize);
										break;
									} catch (NullPointerException e){
										break;
									}
									g2d.drawString(KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").substring(j*15, 15+j*15), 102 + j*113, base_y  + 9*base + fontsize + fontsize/2 + 2 +  j*fontsize);
								}
							}
						} catch	(Exception e){
						}
					}
				/*
				 * ���^�{���b�N�V���h���[������
				 */
					base_y += base_row * base + height_middle;
					base_row = 1;
					/*
					 * ���ʒl���Z�b�g
					 */
					try {
						for(int i=0;i < KensaNengapi.length; i++){
							g2d.drawString(KenshinKekkaData.get(i).get("9N501000000000011").get("CODE_NAME"), 277 + i*55, base_y);
						}
					}catch (IndexOutOfBoundsException e){
						
					}
					
				/*
				 * ��t�̔��f
				 * 44����*41�s�܂�
				 * value_bottom[2]
				 */
					base_y += base_row*base + height_middle;
					base_row = 17;
					
					/*
					 * ��t�̔��f�i����̌��f�̂݁j
					 * ���s����
					 * 44����x40�s
					 */
					for(int i = 0;i < 40;i++){
						try {
							KenshinKekkaData.get(0).get("9N511000000000049").get("KEKA_TI").substring(i*44, 44+i*44);
						} catch (java.lang.StringIndexOutOfBoundsException e) {
							g2d.drawString(KenshinKekkaData.get(0).get("9N511000000000049").get("KEKA_TI").substring(i*44), 112, base_y + i*fontsize + 7 - base/2);
							break;
						}
						g2d.drawString(KenshinKekkaData.get(0).get("9N511000000000049").get("KEKA_TI").substring(i*44, 44+i*44), 112, base_y + i*fontsize + 7 - base/2);
					}
					
					//���f������t��
					g2d.drawString(KenshinKekkaData.get(0).get("9N516000000000049").get("KEKA_TI"), 112, base_y + 15*base + base/2);

//			/*
//			 * �l���
//			 * PrintData����l���𒊏o
//			 */
//				Kojin tmpKojin = (Kojin)PrintData.get("Kojin");
//				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
				/*
				 * ��f�������ԍ��ƃy�[�W�ԍ�
				 */
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 380, 670);
				g2d.drawString("Page.2", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

