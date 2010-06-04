package jp.or.med.orca.jma_tokutei.common.frame;

import javax.swing.JPanel;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;

public abstract class JSelectAbstractInputDialog extends JDialog
	implements ActionListener, KeyListener, ISelectInputDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanelNaviArea = null;
	private JPanel jPanelMainArea = null;
	private JPanel jPanelButtonArea = null;
	private Font guidanceFont;
	private Font defaultFont;
	private JPanel jPanelLabel = null;
	private JLabel jLabelTitle = null;
	private JLabel jLabelGuidance = null;
	protected JScrollPane jScrollPane = null;
	// add ver2 s.inoue 2009/08/17
	protected JCheckBox jCheckBox_BirthDay = null;
	protected JCheckBox jCheckBox_Sex = null;
	protected JCheckBox jCheckBox_Name = null;
	protected JCheckBox jCheckBox_SeiriNo = null;
	protected JCheckBox jCheckBox_HihokenjyaKigo = null;
	protected JCheckBox jCheckBox_HihokenjyaNo = null;
	protected JCheckBox jCheckBox_KenshinNengapi = null;
	protected JCheckBox jCheckBox_HokenjaNo = null;
	protected JCheckBox jCheckBox_HL7 = null;
	protected JCheckBox jCheckBox_ShiharaiNo = null;
	protected JCheckBox jCheckBox_KanaName = null;
	protected JCheckBox jCheckBox_Nendo = null;

	protected JCheckBox jCheckBox_Tanka = null;
	protected JCheckBox jCheckBox_Madoguti = null;
	protected JCheckBox jCheckBox_Seikyu = null;

	protected JButton jButtonOK = null;
	protected JButton jButtonCancel = null;
	protected RETURN_VALUE returnValue;

	/**
	 * @param owner
	 */
	public JSelectAbstractInputDialog(Frame owner) {
		super(owner);
		defaultFont = ViewSettings.getCommonUserInputFont();
		guidanceFont = new Font("Dialog", Font.PLAIN, 14);
	}

//	protected void initialize(String title, String guidance, Component component) {
//		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
//		this.setSize(420, 420);
//		this.setContentPane(getJContentPane());
//		this.setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
//
//		jButtonOK.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
//		jButtonCancel.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
//		jLabelTitle.setText(title);
//		jLabelGuidance.setText(guidance);
//		jScrollPane.setViewportView(component);
//		jButtonOK.setSelected(true);
//		setModal(true);
//	}
	protected void initialize(String title, String guidance) {
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(420, 420);
		this.setContentPane(getJContentPane());
		this.setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		// add ver2 s.inoue 2009/08/17
		this.setLocationRelativeTo(null);            //�t���[������ʂ̒����ɕ\��

		jButtonOK.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		jButtonCancel.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		jLabelTitle.setText(title);
		jLabelGuidance.setText(guidance);

		jButtonOK.setSelected(true);
		setModal(true);
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
		if (jPanelMainArea == null) {

			/*�N�x,��f�������ԍ�,�����i�J�i�j,�����i�����j,����
			���N����,���f���{��,HL7�o�͓�,�ی��Ҕԍ�,��ی��ҏؓ��L��
			��ی��ҏؓ��ԍ�,�P�����v,�������S���v,�������z���v*/

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			jCheckBox_Nendo = new JCheckBox("�N�x");

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			jCheckBox_SeiriNo = new JCheckBox("��f�������ԍ�");

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;
			jCheckBox_KanaName = new JCheckBox("�����i�J�i�j");

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 3;
			jCheckBox_Name = new JCheckBox("�����i�����j");

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 4;
			jCheckBox_Sex = new JCheckBox("����");

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 5;
			jCheckBox_BirthDay = new JCheckBox("���N����");

			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 6;
			jCheckBox_KenshinNengapi = new JCheckBox("���f���{��");

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.BOTH;
			gridBagConstraints8.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 0;
			jCheckBox_HL7 = new JCheckBox("HL7�o�͓�");

			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 1;
			jCheckBox_HokenjaNo = new JCheckBox("�ی��Ҕԍ�");

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.BOTH;
			gridBagConstraints10.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 2;
			jCheckBox_HihokenjyaKigo = new JCheckBox("��ی��ҏؓ��L��");

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 3;
			jCheckBox_HihokenjyaNo = new JCheckBox("��ی��ҏؓ��ԍ�");

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 4;
			jCheckBox_Tanka = new JCheckBox("�P�����v");

			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.gridy = 5;
			jCheckBox_Madoguti = new JCheckBox("�������S���v");


			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.gridy = 6;
			jCheckBox_Seikyu = new JCheckBox("�������z���v");

			jPanelMainArea = new JPanel();
			jPanelMainArea.setLayout(new GridBagLayout());

			jPanelMainArea.add(jCheckBox_Nendo,gridBagConstraints1);
			jPanelMainArea.add(jCheckBox_SeiriNo,gridBagConstraints2);
			jPanelMainArea.add(jCheckBox_KanaName,gridBagConstraints3);
			jPanelMainArea.add(jCheckBox_Name,gridBagConstraints4);
			jPanelMainArea.add(jCheckBox_Sex,gridBagConstraints5);
			jPanelMainArea.add(jCheckBox_BirthDay,gridBagConstraints6);
			jPanelMainArea.add(jCheckBox_KenshinNengapi,gridBagConstraints7);
			jPanelMainArea.add(jCheckBox_HL7,gridBagConstraints8);
			jPanelMainArea.add(jCheckBox_HokenjaNo,gridBagConstraints9);
			jPanelMainArea.add(jCheckBox_HihokenjyaKigo,gridBagConstraints10);
			jPanelMainArea.add(jCheckBox_HihokenjyaNo,gridBagConstraints11);
			jPanelMainArea.add(jCheckBox_Tanka,gridBagConstraints12);
			jPanelMainArea.add(jCheckBox_Madoguti,gridBagConstraints13);
			jPanelMainArea.add(jCheckBox_Seikyu,gridBagConstraints14);

			// default�`�F�b�N����
			jCheckBox_Nendo.setSelected(true);
			jCheckBox_SeiriNo.setSelected(true);
			jCheckBox_KanaName.setSelected(true);
			jCheckBox_Name.setSelected(true);
			jCheckBox_Sex.setSelected(true);
			jCheckBox_BirthDay.setSelected(true);
			jCheckBox_KenshinNengapi.setSelected(true);
		}
		return jPanelMainArea;
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
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.gridy = 0;
			jPanelButtonArea = new JPanel();
			jPanelButtonArea.setLayout(new GridBagLayout());
			jPanelButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanelButtonArea.add(getJButtonOK(), gridBagConstraints1);
			jPanelButtonArea.add(getJButtonCancel(), gridBagConstraints2);
		}
		return jPanelButtonArea;
	}

//	/**
//	 * This method initializes jScrollPane
//	 *
//	 * @return javax.swing.JScrollPane
//	 */
//	private JScrollPane getJScrollPane() {
//		if (jScrollPane == null) {
//			jScrollPane = new JScrollPane();
//		}
//		return jScrollPane;
//	}

	/**
	 * This method initializes jScrollPane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JCheckBox getJCheckBox_BirthDay() {
		if (jCheckBox_BirthDay == null) {
			jCheckBox_BirthDay = new JCheckBox("���N����");
		}
		return jCheckBox_BirthDay;
	}
	/**
	 * This method initializes jButtonOK
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new ExtendedButton();
			jButtonOK.setText("���");
			jButtonOK.setFont(defaultFont);
			jButtonOK.setActionCommand("�I��");
			jButtonOK.addActionListener(this);
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new ExtendedButton();
			jButtonCancel.setFont(defaultFont);
			jButtonCancel.setText("�L�����Z��");
			jButtonCancel.addActionListener(this);
		}
		return jButtonCancel;
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * �߂�l���擾����
	 * @return �߂�l
	 */
	public RETURN_VALUE getStatus() {
		return returnValue;
	}

	/**
	 * �߂�l���i�[
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {

			StringBuilder sb = new StringBuilder();

			// ReturnValue = RETURN_VALUE.OK;
			if (this.jCheckBox_BirthDay.isSelected())
			{
				sb.append("BIRTHDAY");
			}

		}
		else if(e.getSource() == jButtonCancel) {
			returnValue = RETURN_VALUE.CANCEL;
		}
		// ���[�_���_�C�A���O�̐�������B
		setVisible(false);
	}

	/**
	 * �I�𒆃Z���̃e�L�X�g���e�L�X�g�G���A/���X�g�{�b�N�X�ɃZ�b�g����
	 * @param text �I�𒆃Z���e�L�X�g
	 */
	public abstract void setText(String text);

	/**
	 * �_�C�A���O�������/�I�����ꂽ�e�L�X�g���擾����
	 * @return ����/�I���e�L�X�g
	 */
	public abstract String getText();

	/**
	 * This method initializes jPanelLabel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabel() {
		if (jPanelLabel == null) {
			jLabelGuidance = new JLabel();
			jLabelGuidance.setFont(guidanceFont);
			jLabelGuidance.setText("");
			jLabelTitle = new TitleLabel("");
			jLabelTitle.setText("JLabel");
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

}
