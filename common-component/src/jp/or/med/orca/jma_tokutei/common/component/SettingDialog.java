package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;

/**
 * ���ݒ�_�C�A���O���
 */
public class SettingDialog extends JDialog
	implements ActionListener, KeyListener,ItemListener,IDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelNaviArea = null;
	private JPanel jPanel_MainArea = null;
	private JPanel jPanelButtonArea = null;
	private JPanel jPanel_LookFeel = null;
	protected ExtendedButton jButtonOK = null;
	protected ExtendedButton jButtonCancel = null;
	protected RETURN_VALUE ReturnValue;  //  @jve:decl-index=0:
	private Font guidanceFont;
	private Font defaultFont;
	private JPanel jPanelLabel = null;
	private JLabel jLabelTitle = null;
	private JLabel jLabelGuidance = null;
	private ExtendedLabel jLabelLookFeel = null;
	// add s.inoue 2009/11/25
	protected ExtendedComboBox jCombobox_LookFeel = null;

	// Possible Look & Feels
	private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();  //  @jve:decl-index=0:

	// private static final String motif   = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private static final String mac     = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
    private static final String metal   = "javax.swing.plaf.metal.MetalLookAndFeel";
    private static final String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";  //  @jve:decl-index=0:
    private static final String gtk     = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";  //  @jve:decl-index=0:
    // del s.inoue 2010/05/21
    // private static final String nimbus  = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";  //  @jve:decl-index=0:
	private UIManager.LookAndFeelInfo looksinfo = null;
	private static org.apache.log4j.Logger logger = Logger.getLogger(SettingDialog.class);  //  @jve:decl-index=0:
	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	public SettingDialog(){
	}
	/**
	 * @param owner
	 */
	public SettingDialog(Frame owner) {
		super(owner);
		initialize();
		initializecomboSetting();
	}

	// String title, String guidance, Component component
	protected void initialize() {
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(420, 420);
		this.setContentPane(getJContentPane());
		// this.setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setLocationRelativeTo( null );

		jButtonOK.addKeyListener(new JEnterEvent(this));
		jButtonCancel.addKeyListener(new JEnterEvent(this));
//		jLabelTitle.setText(title);
//		jLabelGuidance.setText(guidance);
//		jButtonOK.setSelected(true);
		setModal(true);
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jCombobox_LookFeel);
		this.focusTraversalPolicy.addComponent(jButtonOK);
		this.focusTraversalPolicy.addComponent(jButtonCancel);
	}

	/* combo�ݒ� */
	private void initializecomboSetting(){

		if (isAvailableLookAndFeel(mac))
			jCombobox_LookFeel.addItem("Mac");
		if (isAvailableLookAndFeel(metal))
			jCombobox_LookFeel.addItem("Metal");
		// edit s.inoue 2009/12/16
		// if (isAvailableLookAndFeel(motif))
		//	jCombobox_LookFeel.addItem("motif");
		if (isAvailableLookAndFeel(windows))
			jCombobox_LookFeel.addItem("Windows");
		if (isAvailableLookAndFeel(gtk))
			jCombobox_LookFeel.addItem("GTK+");
		// del s.inoue 2010/05/21
//		if (isAvailableLookAndFeel(nimbus))
//			jCombobox_LookFeel.addItem("Nimbus");

		String lookAndFeel = getAppSettings();

		if (!lookAndFeel.equals("")){
			jCombobox_LookFeel.setSelectedItem(lookAndFeel);
		}
	}
	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelNaviArea(), BorderLayout.NORTH);
			jContentPane.add(getJPanelMainArea(), BorderLayout.CENTER);
			jContentPane.add(getJPanelButtonArea(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelNaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNaviArea() {
		if (jPanelNaviArea == null) {
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(5);
			cardLayout.setVgap(5);
			jPanelNaviArea = new JPanel();
			jPanelNaviArea.setLayout(cardLayout);
			jPanelNaviArea.add(getJPanelLabel(), getJPanelLabel().getName());
		}
		return jPanelNaviArea;
	}

	/**
	 * This method initializes jPanelMainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelMainArea() {
		if (jPanel_MainArea == null) {
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new BorderLayout());
			jPanel_MainArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			jPanel_MainArea.setOpaque(false);

			jPanel_MainArea.add(getJPanelLookFeel(),BorderLayout.NORTH);

		}
		return jPanel_MainArea;
	}
	/**
	 * This method initializes jPanel9
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLookFeel() {
		if (jPanel_LookFeel == null) {
			jLabelLookFeel = new ExtendedLabel();
			jLabelLookFeel.setText("");

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.insets = new Insets(0,5,10,0);
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints1.insets = new Insets(0,5,0,0);
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0D;

			jPanel_LookFeel = new JPanel();
			jPanel_LookFeel.setLayout(new GridBagLayout());
			// edit s.inoue 2009/12/18
			jPanel_LookFeel.add(jLabelLookFeel,gridBagConstraints);
			jPanel_LookFeel.add(getJComboBox_LookFeel(), gridBagConstraints1);
		}
		return jPanel_LookFeel;
	}

	/**
	 * This method initializes jPanelButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtonArea() {
		if (jPanelButtonArea == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;

			// edit s.inoue 2009/12/15
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;

			jPanelButtonArea = new JPanel();
			jPanelButtonArea.setLayout(new GridBagLayout());
			jPanelButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanelButtonArea.add(getJButtonCancel(), gridBagConstraints2);
			jPanelButtonArea.add(getJButtonOK(), gridBagConstraints3);
		}
		return jPanelButtonArea;
	}

	// add s.inoue 2009/11/25
	/**
	 * This method initializes jCombobox_LookFeel
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_LookFeel() {
		if (jCombobox_LookFeel == null) {
			jCombobox_LookFeel = new ExtendedComboBox();
			jCombobox_LookFeel.setPreferredSize(new Dimension(260, 20));
			jCombobox_LookFeel.addItemListener(this);
			jCombobox_LookFeel.addKeyListener(this);
		}
		return jCombobox_LookFeel;
	}

	/**
	 * This method initializes jButtonOK
	 *
	 * @return javax.swing.JButton
	 */
	private ExtendedButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new ExtendedButton();
			jButtonOK.setText("�m��(F12)");
			jButtonOK.setActionCommand("�I��");
			jButtonOK.addActionListener(this);
			// edit s.inoue 2009/12/11
			jButtonOK.addKeyListener(this);
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.JButton
	 */
	private ExtendedButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new ExtendedButton();
			jButtonCancel.setText("�L�����Z��(F1)");
			jButtonCancel.addActionListener(this);
			// edit s.inoue 2009/12/11
			jButtonCancel.addKeyListener(this);
		}
		return jButtonCancel;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButtonCancel.getText());
			changeTheLookAndFeel(getAppSettings());dispose();break;
		case KeyEvent.VK_F12:
			logger.info(jButtonOK.getText());
			registerSettings();dispose();break;
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * �߂�l���擾����
	 * @return �߂�l
	 */
	public RETURN_VALUE getStatus() {
		return ReturnValue;
	}

	/**
	 * This method initializes jPanelLabel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabel() {
		if (jPanelLabel == null) {
			jLabelGuidance = new JLabel();
//			jLabelGuidance.setFont(guidanceFont);
			jLabelGuidance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelGuidance.setText("���ڂ̃��X�g���I�����A�V�X�e���̊O�ς�ύX���܂�");
			jLabelTitle = new TitleLabel("");
			jLabelTitle = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_APPLICATION_ENVIRO_TITLE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			gridLayout.setVgap(5);
			jPanelLabel = new JPanel();
			jPanelLabel.setLayout(gridLayout);
			jPanelLabel.setName("jPanelLabel");
			jPanelLabel.add(jLabelTitle, null);
			jPanelLabel.add(jLabelGuidance, null);
		}
		return jPanelLabel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {
			logger.info(jButtonOK.getText());
			registerSettings();
		}else if(e.getSource() == jButtonCancel) {
			logger.info(jButtonCancel.getText());
			changeTheLookAndFeel(getAppSettings());
		}
		// ���[�_���_�C�A���O�̐�������B
		setVisible(false);
	}

	/* Look & Feel �ݒ�*/
	protected boolean isAvailableLookAndFeel(String laf) {
		try{
		    Class lnfClass = Class.forName(laf);
		    LookAndFeel newLAF = (LookAndFeel)(lnfClass.newInstance());

		    return newLAF.isSupportedLookAndFeel();
		}catch(Exception e) {
		    return false;
		}
	}

    /* combo�C�x���g */
	public void itemStateChanged(ItemEvent e) {
		// edit s.inoue 2009/12/16
		// int intItem = jCombobox_LookFeel.getSelectedIndex();
		// �Ō�ɕt����Listener���O���B
//		int lisnersCount = jCombobox_LookFeel.getItemListeners().length;
//		jCombobox_LookFeel.removeItemListener
//		(jCombobox_LookFeel.getItemListeners()[0]);
//		 (jCombobox_LookFeel.getItemListeners()[lisnersCount - 1]);

		try {
			if (e.getStateChange()==ItemEvent.SELECTED){
				String selectItem = jCombobox_LookFeel.getSelectedItem().toString();
				changeTheLookAndFeel(selectItem);
			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}
	}

	/* combo�C�x���g */
	public void changeTheLookAndFeel(String value) {
	    try {

	    	for (int i = 0; i < looks.length; i++) {
	    		// System.out.println(looks[i].getName());
				if (looks[i].getName().equals(value)){
					looksinfo = looks[i];
					// System.out.println("value:" + value);
					break;
				}
			}
	    	// UIManager.setLookAndFeel(looks[value].getClassName());
	    	UIManager.setLookAndFeel(looksinfo.getClassName());

	    	SwingUtilities.updateComponentTreeUI(this);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}

	// add s.inoue 2009/12/18
	private void applyAppSettings(){
		PropertyUtil.setProperty( "setting.lookAndFeel", looksinfo.getName() );
	}

	// add s.inoue 2009/12/18
	private String getAppSettings(){
		return PropertyUtil.getProperty( "setting.lookAndFeel");
	}

	/* �o�^ */
	private void registerSettings(){
		// application�֏���
		logger.info(jButtonOK.getText());
		applyAppSettings();
	}
	@Override
	public String getKenshinDate() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
	@Override
	public void setMessageTitle(String title) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
	@Override
	public void setShowCancelButton(boolean isShowCancel) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
	@Override
	public void setText(String text) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
	@Override
	public Integer getPrintSelect() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
	@Override
	public String getFilePath() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
	@Override
	public void setDialogTitle(String title) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
	@Override
	public void setDialogSelect(boolean enabled) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
	@Override
	public void setSaveFileName(String title) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
