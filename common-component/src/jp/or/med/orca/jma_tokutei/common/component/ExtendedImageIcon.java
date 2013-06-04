package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * ImageIcon�g���N���X
 * @author s.inoue
 * @version 2.0
 */
public class ExtendedImageIcon extends ImageIcon {
	private ImageIcon icon = null;

	public ExtendedImageIcon(String path){
		icon = new ImageIcon(path);
	}

	public ImageIcon setStrechIcon(JFrame frame,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(frame);		//original image��width�����߂�
		imageHeight= image.getHeight(frame);	//original image��hight�����߂�

		//scale���悸�邱�Ƃɂ��k���E�g���size��ݒ�
		image=icon.getImage().getScaledInstance(
				(int)(imageWidth*scale),
				(int)(imageHeight*scale),
				Image.SCALE_FAST);

		return new ImageIcon(image);				//�g��E�k�������C���[�W��ImageIcon�ɐݒ�
	}

	public ImageIcon setStrechIcon(JButton btn,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(btn);		//original image��width�����߂�
		imageHeight= image.getHeight(btn);	//original image��hight�����߂�

		//scale���悸�邱�Ƃɂ��k���E�g���size��ݒ�
		image=icon.getImage().getScaledInstance(
				(int)(imageWidth*scale),
				(int)(imageHeight*scale),
				Image.SCALE_FAST);

		return new ImageIcon(image);				//�g��E�k�������C���[�W��ImageIcon�ɐݒ�
	}

	public ImageIcon setStrechIcon(JDialog frame,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(frame);		//original image��width�����߂�
		imageHeight= image.getHeight(frame);	//original image��hight�����߂�

		//scale���悸�邱�Ƃɂ��k���E�g���size��ݒ�
		image=icon.getImage().getScaledInstance((int)(imageWidth*scale), (int)(imageHeight*scale),Image.SCALE_FAST);
		return new ImageIcon(image);				//�g��E�k�������C���[�W��ImageIcon�ɐݒ�
	}
}
