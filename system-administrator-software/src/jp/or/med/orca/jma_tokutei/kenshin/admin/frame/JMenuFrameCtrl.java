package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLockerConfig;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.kenshin.admin.JAdminSoftware;
import jp.or.med.orca.jma_tokutei.kenshin.admin.frame.JMenuFrameCtrl;

/**
 * ���j���[
 */
public class JMenuFrameCtrl extends JMenuFrame
{
	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private static Logger logger = Logger.getLogger(JMenuFrameCtrl.class);

	public JMenuFrameCtrl()
	{
		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());

		JAdminSoftware.getSplashFrame().addComponentListener(new ComponentAdapter(){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				JMenuFrameCtrl.this.jButton_Version.setSelected(false);
			}
		});

		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JAdminSoftware.getSplashFrame().hideSplashWindow();
			}
		});

		// focus����
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.addComponent(this.jButton_KikanMaintenance);
		this.focusTraversalPolicy.addComponent(this.jButton_KanriUserMaintenance);
		this.focusTraversalPolicy.addComponent(this.jButton_DBBackup);
		this.focusTraversalPolicy.addComponent(this.jButton_Version);
		this.focusTraversalPolicy.addComponent(this.jButton_BackLogin);
		this.focusTraversalPolicy.addComponent(jButton_End);
		this.jButton_KikanMaintenance.requestFocus();
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton( ActionEvent e )
	{
		System.exit(0);
	}

	/**
	 * �����@�փ����e�i���X�{�^��
	 */
	public void pushedHukusuuButton( ActionEvent e )
	{
		JScene.CreateDialog(this, new JKikanMaintenanceFrameCtrl());
	}

	/**
	 * ���[�U�����e�i���X�{�^��
	 */
	public void pushedKanriButton( ActionEvent e )
	{
//		JScene.CreateDialog(this, new JUserMaintenanceListFrameCtrl());
	    try {
			JApplication.systemDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
		JUserMaintenanceListFrameCtrl ctl = new JUserMaintenanceListFrameCtrl(
				JApplication.systemDatabase.getMConnection());
		JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	/**
	 * �f�[�^�x�[�X�o�b�N�A�b�v�{�^��
	 */
	public void pushedDBButton( ActionEvent e )
	{
		JScene.CreateDialog(this, new JDBBackupFrameCtrl());
	}

	/**
	 * �o�[�W�����{�^��
	 */
	public void pushedVersionButton( ActionEvent e )
	{
		if (jButton_Version.isSelected()) {
			JAdminSoftware.getSplashFrame().showSplashWindow();
		}
		else {
			JAdminSoftware.getSplashFrame().hideSplashWindow();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		// add s.inoue 2009/12/17
		Object source = e.getSource();
		if(source == jButton_End)
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}
		else if(source == jButton_KikanMaintenance)
		{
			logger.info(jButton_KikanMaintenance.getText());
			pushedHukusuuButton( e );
		}
		else if(source == jButton_KanriUserMaintenance)
		{
			logger.info(jButton_KanriUserMaintenance.getText());
			pushedKanriButton( e );
		}
		else if(source == jButton_DBBackup)
		{
			logger.info(jButton_DBBackup.getText());
			pushedDBButton( e );
		}
		else if(source == jButton_Version)
		{
			logger.info(jButton_Version.getText());
			pushedVersionButton( e );
		}
		else if(source == jButton_BackLogin)
		{
			logger.info(jButton_BackLogin.getText());
			backLogin();
		}
	}

	/* ���O�C����ʂ� */
	private void backLogin(){
		JApplication.userID = null;
		JApplication.password = null;

		/* ���b�N����������B */
		File lockFile = JExecLocker.getLockFile();
		if (lockFile != null ) {
			lockFile.delete();
		}

		JScene.ChangeScene(new JLoginFrameCtrl());
	}

	// add s.inoue 2009/12/04
	@Override
	public void keyPressed(KeyEvent keyEvent) {

		int mod = keyEvent.getModifiersEx();

		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedHukusuuButton(null);
				break;
			}
			logger.info(jButton_End.getText());
			pushedEndButton(null);
			break;
		case KeyEvent.VK_F2:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedKanriButton(null);
				break;
			}
			logger.info(jButton_BackLogin.getText());
			backLogin();
			break;
//		case KeyEvent.VK_F3:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_DBBackup.getText());
//				pushedDBButton(null);
//			}
//			break;


// edit s.inoue 2010/04/01
//		case KeyEvent.VK_F3:
//			pushedHukusuuButton(null);break;
//		case KeyEvent.VK_F4:
//			pushedKanriButton(null);break;
//		case KeyEvent.VK_F5:
//			pushedDBButton(null);break;
//		case KeyEvent.VK_F2:
//			backLogin();break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Version.getText());

			if (jButton_Version.isSelected()){
				jButton_Version.setSelected(false);
			}else{
				jButton_Version.setSelected(true);
			}
			pushedVersionButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

