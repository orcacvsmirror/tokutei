package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

/**
 * ���f���ڍ��ړ��̓V�[�g�i�������ʁj
 */
public class KenshinKoumoku_Kensa extends JTKenshinPrint
{

	public static void main( String[] argv )
	{
		new KenshinKoumoku_Kensa();
	}
	
	  public KenshinKoumoku_Kensa(){}
	
	/**
	 * ���f���ځ@����
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin
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
			KenshinKoumoku_Monshin Monshin = new KenshinKoumoku_Monshin();
			Monshin.setQueryResult(printData);
			Monshin.print(g, pf, pageIndex);
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
		 * width : 451	�]��25.4mm�l��
		 * height: 695	�]��25.4mm�l��	
		 */
			final int x_w = 451;
			final int y_h = 695;
			
			/*
			 * �e���ڂ̕�
			 */
			int base = 15;
			
			/*
			 * �l��񕔕��̍���
			 */
			int height_info = 82;
			
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
			int fontsize = 7;
			Font f = new Font("Dialog" , Font.PLAIN, fontsize);
			g2d.setFont(f);
			
			/*
			 * �ǉ����f���ڍ��ڐ�
			 * 10�s���x�ɂ���
			 */
			int num_rows = (y_h - height_info - 31*base - height_middle - 10)/base;
			
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
			 * ���f����
			 */
				base_y = height_info;
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), 31*base);

				//���ږ�����
				g2d.drawLine( 10, base_y + 1*base,  75, base_y + 1*base);
				g2d.drawLine( 10, base_y + 5*base,  75, base_y + 5*base);
				g2d.drawLine( 10, base_y + 8*base,  75, base_y + 8*base);
				g2d.drawLine( 10, base_y + 11*base,  75, base_y + 11*base);
				g2d.drawLine( 10, base_y + 14*base,  75, base_y + 14*base);
				g2d.drawLine( 10, base_y + 16*base,  75, base_y + 16*base);
				g2d.drawLine( 10, base_y + 18*base,  75, base_y + 18*base);
				g2d.drawLine( 10, base_y + 22*base,  75, base_y + 22*base);
				g2d.drawLine( 10, base_y + 25*base,  75, base_y + 25*base);
				
				//������
				for (int i = 0;i < 30; i++){
					g2d.drawLine(75, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//�c
				g2d.drawLine( 75, base_y,  75, base_y + 31*base);
				g2d.drawLine(170, base_y, 170, base_y + 31*base);
				g2d.drawLine(250, base_y, 250, base_y + 31*base);
				g2d.drawLine(280, base_y, 280, base_y + 31*base);
				g2d.drawLine(320, base_y, 320, base_y + 31*base);
				g2d.drawLine(360, base_y, 360, base_y + 31*base);
				g2d.drawLine(400, base_y, 400, base_y + 31*base);
				
			/*
			 * �C�Ӓǉ�����
			 */
				base_y = height_info + 31*base + height_middle;
				//�g
				g2d.drawRect( 10, base_y, (x_w-20), num_rows * base);
				
				//��
				for (int i = 0;i < num_rows; i++){
					g2d.drawLine(10, base_y + i*base, 441, base_y + i*base);			
				}
				//�c
				g2d.drawLine( 75, base_y,  75, base_y + num_rows*base);
				g2d.drawLine(170, base_y, 170, base_y + num_rows*base);
				g2d.drawLine(250, base_y, 250, base_y + num_rows*base);
				g2d.drawLine(280, base_y, 280, base_y + num_rows*base);
				g2d.drawLine(320, base_y, 320, base_y + num_rows*base);
				g2d.drawLine(360, base_y, 360, base_y + num_rows*base);
				g2d.drawLine(400, base_y, 400, base_y + num_rows*base);
		/*
		 * �t�H�[�}�b�g
		 */
			/*
			 * �\��
			 */
				fontsize = 14;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				
				g2d.drawString("���茒�N�f���p���͕[", 10 + (410-10)/2 - 8*fontsize/2, 27);
				
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
			 * ���f����
			 */
				//1�s��
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("�敪"	, 10+(75-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("���ږ�"	, 75+(170-75)/2 - 3*fontsize/2, base_y);
				g2d.drawString("���ʒl"	, 170+(250-170)/2 - 3*fontsize/2, base_y);
				g2d.drawString("�P��"	, 250+(280-250)/2 - 2*fontsize/2, base_y);
				g2d.drawString("��l"	, 280+(320-280)/2 - 3*fontsize/2, base_y);
				g2d.drawString("����"	, 320+(360-320)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O��"	, 360+(400-360)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O�X��"	, 400+(441-400)/2 - 3*fontsize/2, base_y);
				
				//1��ځ@�敪
				base_y = height_info + fontsize/2;
				g2d.drawString("�g�̑���"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 5*base/2 );	base_y += 5*base;
				g2d.drawString("����"		, 10+(75-10)/2 - 2*fontsize/2, base_y + 3*base/2 );	base_y += 3*base;
				g2d.drawString("������������"	, 10+(75-10)/2 - 6*fontsize/2, base_y + 3*base/2 ); base_y += 3*base;
				g2d.drawString("�̋@�\����"	, 10+(75-10)/2 - 5*fontsize/2, base_y + 3*base/2 ); base_y += 3*base;
				g2d.drawString("��������"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 2*base/2 ); base_y += 2*base;
				g2d.drawString("�A����"		, 10+(75-10)/2 - 3*fontsize/2, base_y + 2*base/2 ); base_y += 2*base;
				g2d.drawString("�n������"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 4*base/2 ); base_y += 4*base;
				g2d.drawString("�S�d�}"		, 10+(75-10)/2 - 3*fontsize/2, base_y + 3*base/2 ); base_y += 3*base;
				g2d.drawString("��ꌟ��"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 6*base/2 );
				
				//2��ځ@���ږ�
				String[] koumoku_name = 
						{
							"�g��","�̏d","����","BMI","���k������",
							"�g��������","�̌�����(�H��)","�������b","HDL-�ڽ�۰�-","LDL-�ڽ�۰�-"
							,"GOT","GTP","��-GTP","�󕠎�����","�Ӹ�����A��c"
							,"��","�`��","�w�}�g�N���b�g�l","���F�f��[�Ӹ����ݒl]","�Ԍ�����"
							,"�n������(���{���R)","�S�d�}","�S�d�}(����)","�S�d�}(���{���R)","��ꌟ��(���ܸ�Ű����)"
							,"��ꌟ��(��������:H)","��ꌟ��(��������:S)","��ꌟ��(SCOTT����)","��ꌟ��(���̑��̏���)","��ꌟ��(���{���R)"
						};
				base_y = height_info + base + (base + fontsize)/2;
				for(int i = 0;i < 30;i++){
					g2d.drawString(koumoku_name[i], 77, base_y);
					base_y += base;
				}
				
				//4��ځ@�P��
				base_y = height_info + fontsize/2;
				g2d.drawString("cm"		, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("Kg"		, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("cm"		, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("Kg/�u"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mmHg"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mmHg"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString(" "	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mmHg"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mg/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mg/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("U/l"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("U/l"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("U/l"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mg/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("g/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString(" "	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString(" "	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("%"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("g/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("��/mm3"	, 252, base_y + 3*base/2 ); base_y += base;

			/*
			 * �C�Ӓǉ�����
			 */
				//1�s��
				base_y = height_info + 31*base + height_middle + (base + fontsize)/2;
				g2d.drawString("�C�Ӓǉ�����"	, 10+(75-10)/2 - 6*fontsize/2, base_y - base);
				g2d.drawString("�敪"		, 10+(75-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("���ږ�"		, 75+(170-75)/2 - 3*fontsize/2, base_y);
				g2d.drawString("���ʒl"		, 170+(250-170)/2 - 3*fontsize/2, base_y);
				g2d.drawString("�P��"		, 250+(280-250)/2 - 2*fontsize/2, base_y);
				g2d.drawString("��l"		, 280+(320-280)/2 - 3*fontsize/2, base_y);
				g2d.drawString("����"		, 320+(360-320)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O��"		, 360+(400-360)/2 - 2*fontsize/2, base_y);
				g2d.drawString("�O�X��"		, 400+(441-400)/2 - 3*fontsize/2, base_y);
		
		/*
		 * �f�[�^�̈ڑ��ƕ\��
		 */
			try{
			/*
			 * �l���
			 * ��f���ԍ��A�����A��f���A�ی��Ҕԍ��A��ی��ҏؓ��L���A��ی��ҏؓ��ԍ�
			 * ��f���́A���̗p��������������t���w�肷��B
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

