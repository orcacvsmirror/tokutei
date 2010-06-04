package jp.or.med.orca.jma_tokutei.common.frame;

import java.awt.Paint;
import java.awt.event.ComponentListener;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

public class JSplashFrameCtrl extends JSplashFrame implements Runnable
{
	/*
	 *  �R���X�g���N�^
	 *
	 * 		@param  �o�[�W����
	 * 		@param  �X�v���b�V��
	 *
	 * 		@return none
	 *
	 */
	public JSplashFrameCtrl( String strVersion, String strModuleVersion,String strSchemaVersion,String strDBDataVersion, String strImagePath ,boolean flgReload)
	{
		try
		{
			createSplash( strVersion,strModuleVersion, strSchemaVersion,strDBDataVersion,strImagePath );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			JErrorMessage.show("M9500", null);

			return;
		}

		// add s.inoue 20090113 reload����
		if (flgReload){
			return;
		}
		// �Ǎ�������ʃX���b�h�ŏ���
		Thread loadThread = new Thread( this );

		showSplash();

		loadThread.run();

		try
		{
			/* Modified 2008/03/11 �ጎ  */
			/* --------------------------------------------------- */
//			Thread.sleep( 3500 );
			/* --------------------------------------------------- */
			Thread.sleep( ViewSettings.getTokuteiSplashDisplayTime() );
			/* --------------------------------------------------- */
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		hideSplash();

		m_splashScreen.dispose();
	}

	/*
	 *  ���[�h����
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	public void loadingProc()
	{
		// You have to write loading process after override.
	}

	/*
	 *  �����X���b�h
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	public void run()
	{
		loadingProc();
	}

	/* Added 2008/03/11 �ጎ  */
	/* --------------------------------------------------- */
	public void showSplashWindow(){

		m_splashScreen.setVisible(true);
	}

	public void hideSplashWindow(){
		m_splashScreen.setVisible(false);
	}

	public void addComponentListener(ComponentListener listener){
		m_splashScreen.addComponentListener(listener);
	}
	/* --------------------------------------------------- */
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

