package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.*;

import org.apache.log4j.Logger;


import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.JChangeKikanInformationFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;


/**
 * �}�X�^�����e�i���X�̃��j���[�t���[���̃R���g���[��
 */
public class JMasterMaintenanceFrameCtrl extends JMasterMaintenanceFrame
{
	private static Logger logger = Logger.getLogger(JMasterMaintenanceFrameCtrl.class);
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	public JMasterMaintenanceFrameCtrl()
	{
		functionListner();
	}

	private void functionListner(){
		Component comp = null;
		this.focusTraversalPolicy = new JFocusTraversalPolicy();

		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton( ActionEvent e )
	{
		dispose();
	}

	/**
	 * �@�֏�񃁃��e�i���X�{�^��
	 */
	public void pushedKikanButton( ActionEvent e )
	{
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JChangeKikanInformationFrameCtrl(
				JApplication.systemDatabase,JApplication.kikanNumber));
	}

	/**
	 * ���f�p�^�[�������e�i���X�{�^��
	 */
	public void pushedPatternButton( ActionEvent e )
	{
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JKenshinPatternMaintenanceListFrameCtrl());
	}

	/**
	 * �������ڃ}�X�^�����e�i���X�{�^��
	 */
	public void pushedKoumokuButton( ActionEvent e )
	{
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JKenshinMasterMaintenanceFrameCtrl());
	}

	/**
	 * �ی��ҏ�񃁃��e�i���X�{�^��
	 */
	public void pushedHokenjyaButton( ActionEvent e )
	{
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JHokenjyaMasterMaintenanceListFrameCtrl());
	}

	/**
	 * �����Z���^�[���ڃR�[�h�����e�i���X�{�^��
	 */
	public void pushedCenterButton( ActionEvent e )
	{
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JKensaKikanCodeMasterMaintenanceListFrameCtrl());
	}

	/**
	 * �x����s�����e�i���X�{�^��
	 */
	public void pushedShiharaiButton( ActionEvent e )
	{
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JShiharaiMasterMaintenanceListFrameCtrl());
	}

	/**
	 * ���񂹃����e�i���X�{�^��
	 */
	public void pushedNayoseButton( ActionEvent e )
	{
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JNayoseMaintenanceListFrameCtl());
	}

	// add s.inoue 20090317
	/**
	 * ��^�������e�i���X�{�^��
	 */
	public void pushedTeikeiButton( ActionEvent e )
	{
		//jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JKenshinTeikeiMaintenanceFrameCtrl());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JKekkaTeikeiMaintenanceListFrameCtrl());
	}

	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}
		if( e.getSource() == jButton_KikanMaintenance )
		{
			logger.info(jButton_KikanMaintenance.getText());
			pushedKikanButton( e );
		}
		if( e.getSource() == jButton_KenshinPatternMaintenance )
		{
			logger.info(jButton_KenshinPatternMaintenance.getText());
			pushedPatternButton( e );
		}
		if( e.getSource() == jButton_KenshinKoumokuMaintenance )
		{
			logger.info(jButton_KenshinKoumokuMaintenance.getText());
			pushedKoumokuButton( e );
		}
		if( e.getSource() == jButton_HokenjyaMaintenance )
		{
			logger.info(jButton_HokenjyaMaintenance.getText());
			pushedHokenjyaButton( e );
		}
		if( e.getSource() == jButton_KensaCenterCodeMaintenance )
		{
			logger.info(jButton_KensaCenterCodeMaintenance.getText());
			pushedCenterButton( e );
		}
		if( e.getSource() == jButton_ShiharaiMaintenance )
		{
			logger.info(jButton_ShiharaiMaintenance.getText());
			pushedShiharaiButton( e );
		}
		if( e.getSource() == jButton_NayoseMaintenance )
		{
			logger.info(jButton_NayoseMaintenance.getText());
			pushedNayoseButton( e );
		}
		if( e.getSource() == jButton_TeikeiMaintenance )
		{
			logger.info(jButton_TeikeiMaintenance.getText());
			pushedTeikeiButton( e );
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		int mod = keyEvent.getModifiersEx();
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KenshinKoumokuMaintenance.getText());
				pushedKoumokuButton(null);
				break;
			}
			logger.info(jButton_End.getText());
			pushedEndButton(null);
			break;
// edit s.inoue 2010/04/01
//		case KeyEvent.VK_F2:
//			pushedKoumokuButton(null);break;
		case KeyEvent.VK_F2:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedHokenjyaButton(null);
			}
			break;
		case KeyEvent.VK_F3:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KenshinPatternMaintenance.getText());
				pushedPatternButton(null);
			}
			break;
		case KeyEvent.VK_F4:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_ShiharaiMaintenance.getText());
				pushedShiharaiButton(null);
			}
			break;
		case KeyEvent.VK_F5:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedKikanButton(null);
			}
			break;
		case KeyEvent.VK_F6:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_NayoseMaintenance.getText());
				pushedNayoseButton(null);
			}
			break;
		case KeyEvent.VK_F7:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_TeikeiMaintenance.getText());
				pushedTeikeiButton(null);
			}
			break;
		}
	}

}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

