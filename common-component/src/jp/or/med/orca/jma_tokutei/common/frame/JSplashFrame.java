package jp.or.med.orca.jma_tokutei.common.frame;

import java .io     .*;
import java .awt    .*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing  .*;
import javax.imageio.*;

import jp.or.med.orca.jma_tokutei.common.printer.JGraphicPrinter;

/**
 * �X�v���b�V�����
 */
public class JSplashFrame
{
	/* Modified 2008/03/11 �ጎ  */
	/* --------------------------------------------------- */
	protected JWindow m_splashScreen = null;
	/* --------------------------------------------------- */
//	protected static JWindow m_splashScreen = null;
	/* --------------------------------------------------- */

	/*
	 *  �C���[�W�`��J�X�^���p�l���N���X
	 */
	private class JImagePanel extends JPanel
	{
		private Image m_imgImage = null;

		private String m_strVersion = null;

//		Add E.O 2008/03/30 START
		private String m_strModuleVersion = null;
		private String m_strSchemaVersion = null;
		private String m_strDBDataVersion = null;
//		Add E.O 2008/03/30 END

		/*
		 *  �R���X�g���N�^
		 *
		 *  	@param  none
		 *
		 *  	@return none
		 */
		public JImagePanel( String strVersion, String strModuleVersion,String strSchemaVersion,String strDBDataVersion,String strImagePath ) throws Exception
		{
			m_strVersion = strVersion;
//			Add E.O 2008/03/30 START
			m_strModuleVersion = strModuleVersion;
			m_strSchemaVersion = strSchemaVersion;
			m_strDBDataVersion = strDBDataVersion;
//			Add E.O 2008/03/30 END

			m_imgImage   = ImageIO.read( new FileInputStream( strImagePath ) );

			/* Added 2008/03/11 �ጎ  */
			/* --------------------------------------------------- */
			this.addMouseListener(new MouseAdapter(){

				@Override
				public void mouseClicked(MouseEvent e) {
					JSplashFrame.this.hideSplash();
				}
			});
			/* --------------------------------------------------- */
		}

		/*
		 *  �摜�����擾
		 *
		 * 		@param  none
		 *
		 * 		@return ����
		 *
		 */
		public int getWidth()
		{
			return m_imgImage.getWidth( this );
		}

		/*
		 *  �摜�c���擾
		 *
		 *  	@param  none
		 *
		 *  	@return �c��
		 *
		 */
		public int getHeight()
		{
			return m_imgImage.getHeight( this );
		}

		/*
		 *  @override
		 *
		 *  	@param  ...
		 *
		 *  	@return ...
		 */
		public void paint( Graphics g )
		{
			g.drawImage ( m_imgImage, 0, 0, getWidth(), getHeight(), this );

			g.setColor  ( Color.BLACK );

			/* Modified 2008/03/11 �ጎ  */
			/* --------------------------------------------------- */
//			g.drawString( m_strVersion, getWidth() - 100, 20 );
			/* --------------------------------------------------- */
			g.drawString( m_strVersion, getWidth() - 110, 20 );
			/* --------------------------------------------------- */
//			Add E.O 2008/03/30 START
			g.drawString( m_strModuleVersion, getWidth() - 110, 40 );
			g.drawString( m_strSchemaVersion, getWidth() - 110, 60 );
			g.drawString( m_strDBDataVersion, getWidth() - 110, 80 );
//			Add E.O 2008/03/30 END
		}
	}

	/*
	 *  �X�v���b�V������
	 *
	 * 		@param  �o�[�W����
	 *
	 * 		@return none
	 *
	 */
	protected void createSplash( String strVersion,String strModuleVersion,String strSchemaVersion,String strDBDataVersion, String strImagePath ) throws Exception
	{
		JImagePanel image = new JImagePanel( strVersion,strModuleVersion,strSchemaVersion,strDBDataVersion,strImagePath );

		m_splashScreen = new JWindow( new JFrame() );

		/* Added 2008/03/11 �ጎ  */
		/* --------------------------------------------------- */
//		m_splashScreen.addWindowListener(new WindowAdapter(){
//			@Override
//			public void windowDeactivated(WindowEvent arg0) {
//				m_splashScreen.setVisible(false);
//			}
//		});
		/* --------------------------------------------------- */


		// �p�l���ݒ�
		m_splashScreen.setLayout           ( new BorderLayout() );
		m_splashScreen.getContentPane().add( image, BorderLayout.CENTER );

		// �T�C�Y�ݒ�
		m_splashScreen.getContentPane().setBackground( Color.white );
		m_splashScreen.setBounds                     ( 0, 0, image.getWidth(), image.getHeight() );

		// �����\��
		m_splashScreen.setLocationRelativeTo( null );
	}

	/*
	 *  �X�v���b�V���\��
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	protected void showSplash()
	{
		m_splashScreen.setVisible( true );
	}

	/*
	 *  �X�v���b�V����\��
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	protected void hideSplash()
	{
		m_splashScreen.setVisible( false );
	}
}
