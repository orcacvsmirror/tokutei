package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KenshinKekka;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

/**
 * ���茒�f���ʒʒm�\�@3�y�[�W
 */
public class Kekka_P3 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P3();
	}
	
	public Kekka_P3(){}
	
	/**
	 * ���茒�f���ʒʒm�\�@�y�[�W3
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin, KenshinKekka
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{
		if( pageIndex >= 3 )
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
				base_row = 42;
				
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);
				
				//��
				/*add yabu 2008/03/12*/
				//g2d.drawLine(10, base_y + base, 150, base_y + base);
				g2d.drawLine(10, base_y + base, 100, base_y + base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(212, base_y + base, 441, base_y + base);
				for (int i = 1;i < base_row; i++){
					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//�c
				/*add yabu 2008/03/12*/
				g2d.drawLine(100, base_y, 100, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(150, base_y, 150, base_y + base_row*base);
				g2d.drawLine(180, base_y, 180, base_y + base_row*base);
				g2d.drawLine(212, base_y, 212, base_y + base_row*base);
				g2d.drawLine(288, base_y, 288, base_y + base_row*base);
				g2d.drawLine(364, base_y, 364, base_y + base_row*base);

		/*
		 * �t�H�[�}�b�g
		 */
			/*
			 * ���f����
			 */
				//1�s��
				base_y = height_info + (base + fontsize)/2;
				/*add yabu 2008/03/12*/
				//g2d.drawString("����"		, 10+(110-10)/2 - 2*fontsize/2, base_y);
				//g2d.drawString("��l"	, 150+(180-150)/2 - 3*fontsize/2, base_y + base/2);
				//g2d.drawString("�P��"		, 180+(212-180)/2 - 2*fontsize/2, base_y + base/2);
				g2d.drawString("����"		, 10+(100-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("��l"		, 110+(180-150)/2 - 3*fontsize/2, base_y + base/2);
				g2d.drawString("�P��"		, 150+(212-180)/2 - 2*fontsize/2, base_y + base/2);
				g2d.drawString("H/L"		, 180+(212-180)/2 - 2*fontsize/2, base_y + base/2);
				/*add yabu 2008/03/12*/
				g2d.drawString("����"		, 212+(288-212)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O��"		, 288+(364-288)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O�X��"		, 364+(441-364)/2 - 3*fontsize/2, base_y);
				
				//2�s��
				base_y += base;
				/*add yabu 2008/03/12*/
				//g2d.drawString("���ږ�"			, 10+(110-10)/2 - 3*fontsize/2, base_y);
				g2d.drawString("���ږ�"			, 10+(100-10)/2 - 3*fontsize/2, base_y);
				/*add yabu 2008/03/12*/

		/*
		 * �f�[�^�̈ڑ��ƕ\��
		 */
			try{
			/*
			 * ���O�Ɉ������Ă��Ȃ����f���ڃR�[�h���������
			 * PrintData���猒�f���ʏ��𒊏o
			 */
				KenshinKekka tmpKenshinKekka = (KenshinKekka)printData.get("KenshinKekka");
			/*
			 * �l���
			 * PrintData����l���𒊏o
			 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
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
				 * ���f�N�����Z�b�g
				 * ����A�O��A�O�X��
				 */
				try {
					base_y = height_info + (base + fontsize)/2;
					//����
					g2d.drawString(KensaNengapi[0], 214+(290-214)/2 - 8*fontsize/2, base_y + base);
					//�O��
					g2d.drawString(KensaNengapi[1], 290+(366-290)/2 - 8*fontsize/2, base_y + base);
					//�O�X��
					g2d.drawString(KensaNengapi[2], 366+(441-366)/2 - 8*fontsize/2, base_y + base);
				} catch (NullPointerException e) {
				}
				
				/*
				 * ���ږ��A��l�A�P�ʁA���ʒl
				 */
				base_y = height_info + base + base + (base + fontsize)/2;
				for(int i=0;i < KensaNengapi.length; i++){
					/*
					 * ���ڃR�[�h���o
					 */
			    	try {
			    		/*
			    		 * ���f���ڃR�[�h�ꗗ���擾
			    		 */
			    		Enumeration<String> e = KenshinKekkaData.get(i).keys();

				    	int j=0;
				    	while (e.hasMoreElements()){
					    	/*
					    	 * ���f���ڃR�[�h���擾
					    	 */
					    	String KenshinCD = e.nextElement();
	
					    	/*
					    	 * ���ڃR�[�h������ς݂��𒲂ׂ�
					    	 */
					    	boolean b =tmpKenshinKekka.checkKenshinCD(KenshinCD) && !KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI").isEmpty();
					    	if(b) {
						    	/*
						    	 * �������Ă��Ȃ��̂ň������
						    	 * 1�T�ڂ̂ݍ��ږ��A��l�A�P�ʂ����
						    	 */
					    		if(i == 0){
									/*
									 * ���ږ�
									 * ���ږ�����������i�P�Q�����ȏ�j�̂Ƃ��̓t�H���g�T�C�Y��������
									 * ��Ԗ��㍀�ږ��i�Q�S�����j�ɑΉ�
									 * �q�{�򕔍זE�f (�זE�f�w�l�ȍޗ�)(�x�Z�X�_����)
									 */
					    			if (KenshinKekkaData.get(i).get(KenshinCD).get("KOUMOKU_NAME").length() >= 12) {
						    			int tmp = fontsize;
					    				fontsize = 6;
						    			f = new Font("Dialog" , Font.PLAIN, fontsize);
						    			g2d.setFont(f);
							    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KOUMOKU_NAME"), 12, base_y + j*base);
						    			fontsize = tmp;
						    			f = new Font("Dialog" , Font.PLAIN, fontsize);
						    			g2d.setFont(f);
					    			} else {
							    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KOUMOKU_NAME"), 12, base_y + j*base);
					    			}
									/*add yabu 2008/03/13*/
//									//��l
//									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KIJYUNTI_HANI"), 152, base_y + j*base);
//									//�P��
//									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("TANI"), 182, base_y + j*base);
									//��l
									if (KojinData.get("SEX").equals("�j��")){
										if(KenshinKekkaData.get(i).get(KenshinCD).get("DS_KAGEN").isEmpty()){
										}else{
											g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("DS_KAGEN")  + "�`" +
													KenshinKekkaData.get(i).get(KenshinCD).get("DS_JYOUGEN"), 102, base_y + j*base);
										}
									}else if (KojinData.get("SEX").equals("����")){
										g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("JS_KAGEN")  + "�`" +
												KenshinKekkaData.get(i).get(KenshinCD).get("JS_JYOUGEN"), 102, base_y + j*base);
									}
									//�P��
									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("TANI"), 152, base_y + j*base);
									//H/L
									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("H_L"), 182, base_y + j*base);
									/*add yabu 2008/03/13*/
						    	}
								
						    	/*
						    	 * ���ʒl�����l���R�[�h���𔻕ʂ��A�K�؂Ȍ��ʒl��\������
						    	 */
						    	try {
						    		/*
						    		 * ����
						    		 * 1=>����, 2=>�R�[�h, 3=>������
						    		 */
						    		if (Integer.parseInt(KenshinKekkaData.get(i).get(KenshinCD).get("DATA_TYPE")) == 2) {
						    			/*
						    			 * �R�[�h
						    			 * ��������Ƃ��́i�X�����ȏ�j�t�H���g�T�C�Y��������
						    			 */
						    			if (KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME").length() >= 9) {
							    			int tmp = fontsize;
						    				fontsize = 6;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME") , 214 + i*76, base_y + j*base);
							    			fontsize = tmp;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
						    			} else {
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME") , 214 + i*76, base_y + j*base);
						    			}
							    	} else {
							    		/*
							    		 * ���� or ������
							    		 * ��������Ƃ��́i�X�����ȏ�j�t�H���g�T�C�Y��������
							    		 */
						    			if (KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME").length() >= 9) {
							    			int tmp = fontsize;
						    				fontsize = 6;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI") , 214 + i*76, base_y + j*base);
							    			fontsize = tmp;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
						    			} else {
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI") , 214 + i*76, base_y + j*base);
						    			}
							    	}
						    	} catch (NumberFormatException ee){
						    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI") , 214 + i*76, base_y + j*base);
						    	}
						    	j++;
					    	}
					    }
			    	} catch (IndexOutOfBoundsException err){
			    		break;
			    	}
				}
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
				g2d.drawString("Page.3", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}

}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

