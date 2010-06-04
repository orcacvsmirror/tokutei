package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.Insets;
import javax.swing.BorderFactory;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.GuidanceLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.Color;

// add ver2 s.inoue 2009/08/20 �N���X�S��
/**
 * ��������(����)
 */
public class JOutputGetujiFrame extends JFrame implements ActionListener,KeyListener,
	ItemListener {
    protected static final long serialVersionUID = 1L;
    protected JPanel jPanel_Content = null;
    protected JPanel jPanel_ButtonArea = null;
    protected ExtendedButton jButton_End = null;
    protected JPanel jPanel_NaviArea = null;
    protected TitleLabel jLabel_Title = null;
    protected GuidanceLabel jLabel_MainExpl = null;
    protected JPanel jPanel_TitleArea = null;
    protected JPanel jPanel_ExplArea2 = null;
    protected ExtendedLabel jLabal_SubExpl = null;
    protected JPanel jPanel_ExplArea1 = null;
    // del s.inoue 2009/09/18
    // protected ExtendedButton jButton_HL7Copy = null;
    protected ExtendedButton jButton_HL7Output = null;
    protected ExtendedButton jButton_Seikyu = null;
    protected ExtendedButton jButton_SeikyuListPrint = null;
    protected JPanel jPanel = null;
    protected ExtendedLabel jLabel3 = null;
    protected ExtendedButton jButton_AllSelect = null;
    protected ExtendedTextField jTextField_JyushinKenID = null;
    protected ExtendedTextField jTextField_Hihokenjyasyo_kigou = null;
    protected ExtendedTextField jTextField_JissiBeginDate = null;
    protected ExtendedComboBox jComboBox_HokenjyaNumber = null;
    protected ExtendedTextField jTextField_Name = null;
    protected ExtendedTextField jTextField_Hihokenjyasyo_Number = null;
    protected ExtendedTextField jTextField_JissiEndDate = null;
    protected ExtendedButton jButton_Search = null;
    protected JPanel jPanel1 = null;
    protected ExtendedLabel jLabel = null;
    protected ExtendedLabel jLabel1 = null;
    protected ExtendedLabel jLabel32 = null;
    protected ExtendedLabel jLabel34 = null;
    protected ExtendedLabel jLabel35 = null;
    protected ExtendedLabel jLabel2 = null;
    protected ExtendedLabel jLabel33 = null;

    protected ExtendedComboBox jComboBox_SyubetuCode = null;
    protected ExtendedComboBox jComboBox_SeikyuKikanNumber = null;
    protected ExtendedLabel jLabel39 = null;
    protected ExtendedTextField jTextField_HL7BeginDate = null;
    protected ExtendedLabel jLabel310 = null;
    protected ExtendedTextField jTextField_HL7EndDate = null;
    protected ExtendedLabel jLabel31 = null;

    protected JPanel jPanel2 = null;
    protected JPanel jPanel_TableArea = null;
    private JPanel jPanel3 = null;
    private JPanel jPanel31 = null;
    private JPanel jPanel32 = null;
    private JPanel jPanel33 = null;

    protected JPanel jPanelKingaku = null;
	protected JLabel jLabelTanka = null;
	protected JLabel jLabelFutan = null;
	protected JLabel jLabelSeikyu = null;
	protected ExtendedTextField jTextField_TankaGoukei = null;
	protected ExtendedTextField jTextField_FutanGoukei = null;
	protected ExtendedTextField jTextField_SeikyuGoukei = null;

    protected ExtendedCheckBox jCheckBox_JisiYear = null;
    // add s.inoue 2009/09/14
    protected ExtendedCheckBox jCheckBox_IsPermitHL7 = null;
	protected ExtendedLabel jLabel_count = null;

    /**
         * This is the default constructor
         */
    public JOutputGetujiFrame() {
	super();
	initialize();
    }

    /**
         * This method initializes this
         *
         * @return void
         */
    private void initialize() {
		this.setContentPane(getJPanel_Content());
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(ViewSettings.getFrameCommonSize());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
    }

    /**
         * This method initializes jPanel_Content
         *
         * @return javax.swing.JPanel
         */
    private JPanel getJPanel_Content() {
	if (jPanel_Content == null) {
	    BorderLayout borderLayout = new BorderLayout();
	    borderLayout.setVgap(2);
	    jPanel_Content = new JPanel();
	    jPanel_Content.setLayout(borderLayout);
	    jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
	    jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
	    jPanel_Content.add(getJPanel(), BorderLayout.CENTER);
	}
	return jPanel_Content;
    }

    /**
         * This method initializes jPanel_ButtonArea
         *
         * @return javax.swing.JPanel
         */
    private JPanel getJPanel_ButtonArea() {
	if (jPanel_ButtonArea == null) {
	    GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
	    gridBagConstraints31.gridy = 0;
	    gridBagConstraints31.gridx = 0;

	    // add ver2 s.inoue 2009/08/03
	    GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
	    gridBagConstraints32.gridy = 0;
	    gridBagConstraints32.gridx = 3;
	    gridBagConstraints32.insets = new Insets(0, 5, 0, 0);

	    GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
	    gridBagConstraints30.gridy = 0;
	    gridBagConstraints30.gridx = 4;
	    gridBagConstraints30.insets = new Insets(0, 5, 0, 0);

	    GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
	    gridBagConstraints29.gridy = 0;
	    gridBagConstraints29.gridx = 2;

	    // del ver2 s.inoue 2009/08/03
//	    GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
//	    gridBagConstraints27.gridy = 0;
//	    gridBagConstraints27.insets = new Insets(0, 5, 0, 0);
//	    gridBagConstraints27.gridx = 3;
	    GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
	    gridBagConstraints23.weightx = 1.0D;
	    gridBagConstraints23.anchor = GridBagConstraints.EAST;
	    gridBagConstraints23.gridy = 0;
	    gridBagConstraints23.gridx = 1;
	    gridBagConstraints23.insets = new Insets(0, 0, 0, 5);

	    jPanel_ButtonArea = new JPanel();
	    jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    jPanel_ButtonArea.setLayout(new GridBagLayout());
	    jPanel_ButtonArea.add(getJButton_Seikyu(), gridBagConstraints23);
	    // del ver2 s.inoue 2009/08/03
	    // jPanel_ButtonArea.add(getJButton_Kessai(), gridBagConstraints27);
	    jPanel_ButtonArea.add(getJButton_HL7Output(), gridBagConstraints29);
	    // del s.inoue 2009/09/18
	    // jPanel_ButtonArea.add(getJButton_HL7Copy(), gridBagConstraints30);
	    jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints31);
	    jPanel_ButtonArea.add(getJButton_SeikyuListPrint(), gridBagConstraints32);

	}
	return jPanel_ButtonArea;
    }

    /**
         * This method initializes jButton_End
         *
         * @return javax.swing.ExtendedButton
         */
    private ExtendedButton getJButton_End() {
	if (jButton_End == null) {
	    jButton_End = new ExtendedButton();
	    jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
	    jButton_End.setActionCommand("�I��");
	    jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
	    jButton_End.setText("�߂�(F1)");
	    jButton_End.addActionListener(this);
	}
	return jButton_End;
    }

    /**
         * This method initializes jPanel_NaviArea
         *
         * @return javax.swing.JPanel
         */
    private JPanel getJPanel_NaviArea() {
	if (jPanel_NaviArea == null) {
	    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
	    gridBagConstraints5.insets = new Insets(10, 10, 0, 10);
	    gridBagConstraints5.gridy = 0;
	    gridBagConstraints5.weightx = 1.0D;
	    gridBagConstraints5.weighty = 1.0D;
	    gridBagConstraints5.fill = GridBagConstraints.BOTH;
	    gridBagConstraints5.gridx = 0;
	    jLabel_Title = new TitleLabel("tokutei.getuji.frame.title");
	    jPanel_NaviArea = new JPanel();
	    jPanel_NaviArea.setLayout(new GridBagLayout());
	    jPanel_NaviArea.add(getJPanel_TitleArea(), gridBagConstraints5);
	}
	return jPanel_NaviArea;
    }

    /**
         * This method initializes jPanel_TitleArea
         *
         * @return javax.swing.JPanel
         */
    private JPanel getJPanel_TitleArea() {
	if (jPanel_TitleArea == null) {
	    GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
	    gridBagConstraints6.gridx = 0;
	    gridBagConstraints6.weightx = 1.0D;
	    gridBagConstraints6.fill = GridBagConstraints.BOTH;
	    gridBagConstraints6.insets = new Insets(0, 10, 0, 0);
	    gridBagConstraints6.gridy = 1;
	    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
	    gridBagConstraints4.insets = new Insets(5, 0, 0, 0);
	    gridBagConstraints4.gridy = 1;
	    gridBagConstraints4.gridx = 0;
	    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
	    gridBagConstraints3.insets = new Insets(0, 0, 5, 0);
	    gridBagConstraints3.gridy = 0;
	    gridBagConstraints3.anchor = GridBagConstraints.WEST;
	    gridBagConstraints3.fill = GridBagConstraints.BOTH;
	    gridBagConstraints3.weightx = 1.0D;
	    gridBagConstraints3.ipady = 15;
	    gridBagConstraints3.gridx = 0;

	    jLabel_MainExpl = new GuidanceLabel("tokutei.hl7.frame.guidance.main");
	    // edit s.inoue 2010/02/25
		jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 13));
		jLabel_MainExpl.setName("jLabel1");

	    jPanel_TitleArea = new JPanel();
	    jPanel_TitleArea.setLayout(new GridBagLayout());
	    jPanel_TitleArea.setName("jPanel2");
	    jPanel_TitleArea.add(jLabel_Title, gridBagConstraints3);
	    jPanel_TitleArea.add(jLabel_MainExpl, gridBagConstraints6);
	}
	return jPanel_TitleArea;
    }

//    /**
//         * This method initializes jButton_HL7Copy
//         *
//         * @return javax.swing.ExtendedButton
//         */
//    private ExtendedButton getJButton_HL7Copy() {
//	if (jButton_HL7Copy == null) {
//	    jButton_HL7Copy = new ExtendedButton();
//	    jButton_HL7Copy.setText("HL7�O���R�s�[");
//	    jButton_HL7Copy.setPreferredSize(new Dimension(100, 25));
//	    jButton_HL7Copy.addActionListener(this);
//	}
//	return jButton_HL7Copy;
//    }

    /**
         * This method initializes jButton_HL7Output
         *
         * @return javax.swing.ExtendedButton
         */
    private ExtendedButton getJButton_HL7Output() {
	if (jButton_HL7Output == null) {
	    jButton_HL7Output = new ExtendedButton();
	    jButton_HL7Output.setText("HL7�o��(F9)");
	    jButton_HL7Output.addActionListener(this);
	}
	return jButton_HL7Output;
    }

    /**
         * This method initializes jButton_Seikyu
         *
         * @return javax.swing.ExtendedButton
         */
    private ExtendedButton getJButton_Seikyu() {
	if (jButton_Seikyu == null) {
	    jButton_Seikyu = new ExtendedButton();
	    jButton_Seikyu.setText("�����m��(F7)");
	    jButton_Seikyu.addActionListener(this);
	}
	return jButton_Seikyu;
    }


    /**
     * This method initializes jButton_Seikyu
     *
     * @return javax.swing.ExtendedButton
     */
	private ExtendedButton getJButton_SeikyuListPrint() {
	if (jButton_SeikyuListPrint == null) {
		jButton_SeikyuListPrint = new ExtendedButton();
		jButton_SeikyuListPrint.setText("�������X�g���(F11)");
		jButton_SeikyuListPrint.setPreferredSize(new Dimension(150, 25));
		jButton_SeikyuListPrint.addActionListener(this);
	}
	return jButton_SeikyuListPrint;
	}



    /**
         * This method initializes jPanel
         *
         * @return javax.swing.JPanel
         */
    private JPanel getJPanel() {
	if (jPanel == null) {
	    jPanel = new JPanel();
	    jPanel.setLayout(new BorderLayout());
	    jPanel.add(getJPanel2(), BorderLayout.NORTH);
		// del s.inoue 2009/09/29
		// �d�l����O���i����K�v��������R�����g�A�E�g���O���j
	    // jPanel.add(getJPanel4(), BorderLayout.SOUTH);
	    jPanel.add(getJPanel_TableArea(), BorderLayout.CENTER);
	}
	return jPanel;
    }

    /**
         * This method initializes jButton_AllSelect
         *
         * @return javax.swing.ExtendedButton
         */
    private ExtendedButton getJButton_AllSelect() {
	if (jButton_AllSelect == null) {
	    jButton_AllSelect = new ExtendedButton();
	    jButton_AllSelect.setText("�S�đI��(A)");
	    jButton_AllSelect.setMinimumSize(new Dimension(82, 20));
	    jButton_AllSelect.setFont(new Font("Dialog", Font.PLAIN, 12));
	    jButton_AllSelect.addActionListener(this);
	    jButton_AllSelect.setMnemonic(KeyEvent.VK_A);
	}
	return jButton_AllSelect;
    }

    /**
         * This method initializes jTextField_JyushinKenID
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_JyushinKenID() {
	if (jTextField_JyushinKenID == null) {
	    jTextField_JyushinKenID = new ExtendedTextField("", 11,ImeMode.IME_OFF);
	    jTextField_JyushinKenID.setPreferredSize(new Dimension(150, 20));
	}
	return jTextField_JyushinKenID;
    }

    /**
         * This method initializes jTextField_HokensyoCode
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_HokensyoCode() {
	if (jTextField_Hihokenjyasyo_kigou == null) {
	    jTextField_Hihokenjyasyo_kigou = new ExtendedTextField(ImeMode.IME_ZENKAKU);
	    jTextField_Hihokenjyasyo_kigou.setPreferredSize(new Dimension(150, 20));
	}
	return jTextField_Hihokenjyasyo_kigou;
    }

    /**
         * This method initializes jTextField_BeginDate
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_BeginDate() {
	if (jTextField_JissiBeginDate == null) {
	    jTextField_JissiBeginDate = new ExtendedTextField("", 8,ImeMode.IME_OFF);
	    jTextField_JissiBeginDate.setPreferredSize(new Dimension(100, 20));
	}
	return jTextField_JissiBeginDate;
    }

    /**
         * This method initializes jComboBox_HokenjyaNumber
         *
         * @return javax.swing.ExtendedComboBox
         */
    private ExtendedComboBox getJComboBox_HokenjyaNumber() {
	if (jComboBox_HokenjyaNumber == null) {
	    jComboBox_HokenjyaNumber = new ExtendedComboBox();
	    jComboBox_HokenjyaNumber.setMinimumSize(new Dimension(31, 20));
	    jComboBox_HokenjyaNumber.setPreferredSize(new Dimension(31, 20));
	    jComboBox_HokenjyaNumber.addItemListener(this);
	}
	return jComboBox_HokenjyaNumber;
    }

    /**
         * This method initializes jTextField_Name
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_Name() {
	if (jTextField_Name == null) {
	    jTextField_Name = new ExtendedTextField(ImeMode.IME_ZENKAKU_KANA);
	    jTextField_Name.setPreferredSize(new Dimension(100, 20));
	}
	return jTextField_Name;
    }

    /**
         * This method initializes jTextField_HokensyoNumber
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_HokensyoNumber() {
	if (jTextField_Hihokenjyasyo_Number == null) {
	    jTextField_Hihokenjyasyo_Number = new ExtendedTextField(ImeMode.IME_ZENKAKU);
	    jTextField_Hihokenjyasyo_Number.setPreferredSize(new Dimension(150, 20));
	}
	return jTextField_Hihokenjyasyo_Number;
    }

    /**
         * This method initializes jTextField_EndDate
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_EndDate() {
	if (jTextField_JissiEndDate == null) {
	    jTextField_JissiEndDate = new ExtendedTextField("", 8,ImeMode.IME_OFF);
	    jTextField_JissiEndDate.setPreferredSize(new Dimension(100, 20));
	}
	return jTextField_JissiEndDate;
    }

    /**
         * This method initializes jButton_Search
         *
         * @return javax.swing.ExtendedButton
         */
    private ExtendedButton getJButton_Search() {
	if (jButton_Search == null) {
	    jButton_Search = new ExtendedButton();
	    jButton_Search.setText("����(S)");
	    jButton_Search.setMinimumSize(new Dimension(58, 20));
	    jButton_Search.setFont(new Font("Dialog", Font.PLAIN, 12));
	    jButton_Search.addActionListener(this);
	    jButton_Search.setMnemonic(KeyEvent.VK_S);
	}
	return jButton_Search;
    }

    public void actionPerformed(ActionEvent e) {}
    public void itemStateChanged(ItemEvent e) {}

    /*
         * FrameSize Control
         */
    public void validate() {
		Rectangle rect = getBounds();

		super.validate();

		if (ViewSettings.getFrameCommonWidth() > rect.width
			|| ViewSettings.getFrameCommonHeight() > rect.height) {
		    setBounds(rect.x, rect.y, ViewSettings.getFrameCommonWidth(),
			    ViewSettings.getFrameCommonHeight());
		}
    }

// edit ver2 s.inoue 2009/08/27
//	/**
//	 * This method initializes jPanel4
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel4() {
//		if (jPanelKingaku == null) {
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.fill = GridBagConstraints.WEST;
//			gridBagConstraints1.gridx = 0;
//			gridBagConstraints1.gridy = 0;
//
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.fill = GridBagConstraints.WEST;
//			gridBagConstraints2.gridx = 2;
//			gridBagConstraints2.gridy = 0;
//
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.fill = GridBagConstraints.WEST;
//			gridBagConstraints3.gridx = 1;
//			gridBagConstraints3.gridy = 0;
//
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.fill = GridBagConstraints.WEST;
//			gridBagConstraints4.gridx = 3;
//			gridBagConstraints4.gridy = 0;
//
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.anchor = GridBagConstraints.WEST;
//			gridBagConstraints5.gridx = 4;
//			gridBagConstraints5.gridy = 0;
//
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.anchor = GridBagConstraints.WEST;
//			gridBagConstraints6.gridx = 5;
//			gridBagConstraints6.gridy = 0;
//			gridBagConstraints6.insets = new Insets(0, 0, 0, 430);
//
//			jLabelTanka = new ExtendedLabel();
//			jLabelTanka.setText("�P�����v");
//			jLabelTanka.setPreferredSize(new Dimension(50, 20));
//			jLabelFutan = new ExtendedLabel();
//			jLabelFutan.setText("���S���v");
//			jLabelFutan.setPreferredSize(new Dimension(50, 20));
//			jLabelSeikyu = new ExtendedLabel();
//			jLabelSeikyu.setText("�������v");
//			jLabelSeikyu.setPreferredSize(new Dimension(50, 20));
//
//			jPanelKingaku = new JPanel();
//			jPanelKingaku.setLayout(new GridBagLayout());
//			jPanelKingaku.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanelKingaku.add(jLabelTanka,gridBagConstraints1);
//			jPanelKingaku.add(jLabelFutan,gridBagConstraints2);
//			jPanelKingaku.add(jLabelSeikyu,gridBagConstraints5);
//			jPanelKingaku.add(getJTextField_TankaGoukei(), gridBagConstraints3);
//			jPanelKingaku.add(getJTextField_FutanGoukei(), gridBagConstraints4);
//			jPanelKingaku.add(getJTextField_SeikyuGoukei(), gridBagConstraints6);
//		}
//		return jPanelKingaku;
//	}

    /**
         * This method initializes jPanel1
         *
         * @return javax.swing.JPanel
         */
    private JPanel getJPanel1() {
	if (jPanel1 == null) {
	    GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
	    gridBagConstraints26.gridx = 5;
	    gridBagConstraints26.fill = GridBagConstraints.BOTH;
	    gridBagConstraints26.gridwidth = 3;
	    gridBagConstraints26.anchor = GridBagConstraints.WEST;
	    gridBagConstraints26.gridheight = 2;
	    gridBagConstraints26.insets = new Insets(0, 10, 0, 0);
	    gridBagConstraints26.gridy = 0;
	    GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
	    gridBagConstraints12.gridx = 6;
	    // del s.inoue 2010/03/23
//	    gridBagConstraints12.ipadx = 10;
//	    gridBagConstraints12.ipady = 10;
	    gridBagConstraints12.weightx = 1.0D;
	    gridBagConstraints12.gridy = 3;
	    gridBagConstraints12.anchor = GridBagConstraints.EAST;

	    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
	    gridBagConstraints1.gridx = 1;
	    gridBagConstraints1.gridwidth = 9;
	    gridBagConstraints1.ipady = 10;
	    gridBagConstraints1.gridy = 5;
	    GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
	    gridBagConstraints58.gridx = 0;
	    gridBagConstraints58.anchor = GridBagConstraints.WEST;
	    gridBagConstraints58.gridy = 1;
	    jLabel31 = new ExtendedLabel();
	    jLabel31.setText("��ی��ҏؓ��L��");
	    jLabel310 = new ExtendedLabel();
	    jLabel310.setText("�`");
	    jLabel39 = new ExtendedLabel();
	    jLabel39.setText("�`");
	    GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
	    gridBagConstraints53.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints53.gridy = 3;
	    gridBagConstraints53.gridwidth = 2;
	    gridBagConstraints53.insets = new Insets(0, 5, 0, 0);
	    gridBagConstraints53.gridx = 1;
	    GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
	    gridBagConstraints50.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints50.gridx = 6;
	    gridBagConstraints50.gridy = 5;
	    gridBagConstraints50.gridwidth = 4;
	    gridBagConstraints50.weightx = 1.0;
	    GridBagConstraints gridBagConstraints = new GridBagConstraints();
	    gridBagConstraints.gridx = 5;
	    gridBagConstraints.anchor = GridBagConstraints.WEST;
	    gridBagConstraints.gridy = 5;
	    GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
	    gridBagConstraints48.gridx = 0;
	    gridBagConstraints48.anchor = GridBagConstraints.WEST;
	    gridBagConstraints48.gridy = 3;
	    jLabel33 = new ExtendedLabel();
	    jLabel33.setText("�x����s�@�֔ԍ�");
	    GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
	    gridBagConstraints47.gridx = 0;
	    gridBagConstraints47.gridy = 2;
	    gridBagConstraints47.gridy = 2;
	    gridBagConstraints47.anchor = GridBagConstraints.WEST;
	    jLabel2 = new ExtendedLabel();
	    jLabel2.setText("�ی��Ҕԍ�");
	    jLabel35 = new ExtendedLabel();
	    jLabel35.setText("HL7�o�͓�");
	    jLabel34 = new ExtendedLabel();
	    jLabel34.setText("���f���{��");
	    GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
	    gridBagConstraints43.gridx = 2;
	    gridBagConstraints43.anchor = GridBagConstraints.WEST;
	    gridBagConstraints43.insets = new Insets(0, 10, 0, 0);
	    gridBagConstraints43.gridy = 1;
	    jLabel32 = new ExtendedLabel();
	    jLabel32.setText("��ی��ҏؓ��ԍ�");
	    GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
	    gridBagConstraints41.gridx = 2;
	    gridBagConstraints41.gridy = 1;
	    GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
	    gridBagConstraints40.gridx = 2;
	    gridBagConstraints40.anchor = GridBagConstraints.WEST;
	    gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
	    gridBagConstraints40.gridy = 0;
	    jLabel1 = new ExtendedLabel();
	    jLabel1.setText("�����i�J�i�j");
	    GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
	    gridBagConstraints39.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints39.gridx = 1;
	    gridBagConstraints39.insets = new Insets(0, 5, 0, 0);
	    gridBagConstraints39.gridy = 0;
	    GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
	    gridBagConstraints38.gridx = 0;
	    gridBagConstraints38.anchor = GridBagConstraints.WEST;
	    gridBagConstraints38.gridy = 0;
	    jLabel = new ExtendedLabel();
	    jLabel.setText("��f�������ԍ�");
	    GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
	    gridBagConstraints28.gridx = 0;
	    gridBagConstraints28.anchor = GridBagConstraints.WEST;
	    gridBagConstraints28.insets = new Insets(0, 0, 0, 0);
	    gridBagConstraints28.gridy = 5;
	    GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
	    gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints25.gridy = 1;
	    gridBagConstraints25.insets = new Insets(0, 5, 0, 0);
	    gridBagConstraints25.gridx = 3;
	    GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
	    gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints24.gridy = 1;
	    gridBagConstraints24.insets = new Insets(0, 5, 0, 0);
	    gridBagConstraints24.gridx = 1;
	    GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
	    gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints22.gridy = 3;
	    gridBagConstraints22.weightx = 1.0;
	    gridBagConstraints22.gridwidth = 2;
	    gridBagConstraints22.insets = new Insets(0, 10, 0, 0);
	    gridBagConstraints22.gridx = 5;
	    GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
	    gridBagConstraints20.gridy = 2;
	    gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints20.gridwidth = 2;
	    gridBagConstraints20.insets = new Insets(0, 5, 0, 0);
	    gridBagConstraints20.gridx = 1;

	    // edit ver2 s.inoue 2009/08/28
	    GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
	    gridBagConstraints19.gridx = 6;
	    gridBagConstraints19.anchor = GridBagConstraints.NORTHEAST;
	    gridBagConstraints19.insets = new Insets(0, 0, 0, 0);
	    gridBagConstraints19.gridy = 5;
	    GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
	    gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints17.gridy = 0;
	    gridBagConstraints17.insets = new Insets(0, 5, 0, 0);
	    gridBagConstraints17.gridx = 3;
	    GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
	    gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints16.gridy = 4;
	    gridBagConstraints16.weightx = 1.0;
	    gridBagConstraints16.gridwidth = 2;
	    gridBagConstraints16.insets = new Insets(0, 10, 0, 0);
	    gridBagConstraints16.gridx = 5;

	    // edit ver2 s.inoue 2009/08/28
		GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
		gridBagConstraints59.gridx = 5;
//		gridBagConstraints59.anchor = GridBagConstraints.WEST;
//		gridBagConstraints59.fill = GridBagConstraints.BOTH;
		gridBagConstraints59.insets = new Insets(0, 195, 0, 0);
		gridBagConstraints59.anchor = GridBagConstraints.EAST;
		gridBagConstraints59.gridwidth = 1;
		gridBagConstraints59.gridy = 5;

		// add s.inoue 2009/09/14
	    GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
	    gridBagConstraints11.gridx = 5;
	    // gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraints11.gridwidth = 1;
		gridBagConstraints11.anchor = GridBagConstraints.EAST;
	    gridBagConstraints11.insets = new Insets(0, 0, 0, 85);
	    gridBagConstraints11.gridy = 5;

	    // edit s.inoue 2010/02/24
	    GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
	    gridBagConstraints60.gridx = 6;
	    gridBagConstraints60.anchor = GridBagConstraints.NORTHEAST;
	    gridBagConstraints60.insets = new Insets(0, 0, 0, 0);
	    gridBagConstraints60.gridy = 4;

		// edit s.inoue 2010/02/24
		jLabel_count = new ExtendedLabel();
		jLabel_count.setPreferredSize(new Dimension(100, 15));
		jLabel_count.setFont(new Font("Dialog", Font.PLAIN, 12));

	    jPanel1 = new JPanel();
	    jPanel1.setLayout(new GridBagLayout());
	    jPanel1.setName("jPanel1");
	    jPanel1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	    jPanel1.add(jLabel, gridBagConstraints40);
	    jPanel1.add(getJTextField_JyushinKenID(), gridBagConstraints17);
	    jPanel1.add(getJTextField_HokensyoCode(), gridBagConstraints24);
	    jPanel1.add(jLabel32, gridBagConstraints43);
	    jPanel1.add(getJComboBox_HokenjyaNumber(), gridBagConstraints20);
	    jPanel1.add(jLabel2, gridBagConstraints47);
	    jPanel1.add(getJTextField_HokensyoNumber(), gridBagConstraints25);
	    jPanel1.add(jLabel33, gridBagConstraints48);
	    jPanel1.add(getJComboBox_SeikyuKikanNumber(), gridBagConstraints53);
	    jPanel1.add(jLabel1, gridBagConstraints38);
	    jPanel1.add(getJTextField_Name(), gridBagConstraints39);
	    jPanel1.add(getJComboBox_SyubetuCode(), gridBagConstraints50);
	    // del s.inoue 2010/03/23
	    // jPanel1.add(jLabel_count, gridBagConstraints60);
	    jPanel1.add(getJButton_Search(), gridBagConstraints19);

	    jPanel1.add(getJButton_AllSelect(), gridBagConstraints28);
	    jPanel1.add(jLabel31, gridBagConstraints58);
	    // edit s.inoue 2010/03/23
	    // jPanel1.add(getJPanel31(), gridBagConstraints12);
	    jPanel1.add(jLabel_count, gridBagConstraints12);

	    jPanel1.add(getJPanel32(), gridBagConstraints26);
	    // add ver2 s.inoue 2009/06/23
		jPanel1.add(getJCheckBox_JisiYear(), gridBagConstraints59);

		// edit s.inoue 2009/09/14
	    jPanel1.add(getjCheckBox_IsPermitHL7(), gridBagConstraints11);
	}
	return jPanel1;
    }

    // edit s.inoue 2009/09/14
    /**
     * This method initializes jCheckBox_IsPermitHL7
     *
     * @return javax.swing.ExtendedCheckBox
     */
    private ExtendedCheckBox getjCheckBox_IsPermitHL7() {
		if (jCheckBox_IsPermitHL7 == null) {
		    jCheckBox_IsPermitHL7 = new ExtendedCheckBox();
		    jCheckBox_IsPermitHL7.setText("HL7�o�͍�");
		    // add h.yoshitama 2009/11/11
		    jCheckBox_IsPermitHL7.addItemListener(this);
		}
		return jCheckBox_IsPermitHL7;
    }

    /**
         * This method initializes jComboBox_SyubetuCode
         *
         * @return javax.swing.ExtendedComboBox
         */
    private ExtendedComboBox getJComboBox_SyubetuCode() {
	if (jComboBox_SyubetuCode == null) {
	    jComboBox_SyubetuCode = new ExtendedComboBox();
	    jComboBox_SyubetuCode.setPreferredSize(new Dimension(31, 20));
	    jComboBox_SyubetuCode.setMinimumSize(new Dimension(31, 20));
	    jComboBox_SyubetuCode.setVisible(false);
	}
	return jComboBox_SyubetuCode;
    }

    /**
         * This method initializes jComboBox_SeikyuKikanNumber
         *
         * @return javax.swing.ExtendedComboBox
         */
    private ExtendedComboBox getJComboBox_SeikyuKikanNumber() {
	if (jComboBox_SeikyuKikanNumber == null) {
	    jComboBox_SeikyuKikanNumber = new ExtendedComboBox();
	    jComboBox_SeikyuKikanNumber.setPreferredSize(new Dimension(150, 20));
	    jComboBox_SeikyuKikanNumber.setMinimumSize(new Dimension(31, 20));
	    jComboBox_SeikyuKikanNumber.addItemListener(this);
	}
	return jComboBox_SeikyuKikanNumber;
    }

    /**
         * This method initializes jTextField_HL7BeginDate
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_HL7BeginDate() {
	if (jTextField_HL7BeginDate == null) {
	    jTextField_HL7BeginDate = new ExtendedTextField("", 8,ImeMode.IME_OFF);
	    jTextField_HL7BeginDate.setPreferredSize(new Dimension(100, 20));
	}
	return jTextField_HL7BeginDate;
    }

    /**
         * This method initializes jTextField_HL7EndDate
         *
         * @return javax.swing.ExtendedTextField
         */
    private ExtendedTextField getJTextField_HL7EndDate() {
	if (jTextField_HL7EndDate == null) {
	    jTextField_HL7EndDate = new ExtendedTextField("", 8,ImeMode.IME_OFF);
	    jTextField_HL7EndDate.setPreferredSize(new Dimension(100, 20));
	}
	return jTextField_HL7EndDate;
    }

    /**
     * This method initializes jPanel2
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel2() {
	if (jPanel2 == null) {
	    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
	    gridBagConstraints2.insets = new Insets(5, 10, 0, 10);
	    gridBagConstraints2.gridy = 0;
	    gridBagConstraints2.weightx = 1.0D;
	    gridBagConstraints2.weighty = 1.0D;
	    gridBagConstraints2.fill = GridBagConstraints.BOTH;
	    gridBagConstraints2.ipadx = 10;
	    gridBagConstraints2.gridx = 0;
	    jPanel2 = new JPanel();
	    jPanel2.setLayout(new GridBagLayout());
	    jPanel2.add(getJPanel1(), gridBagConstraints2);
	}
	return jPanel2;
    }

    /**
     * This method initializes jPanel_TableArea
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel_TableArea() {
	if (jPanel_TableArea == null) {
	    jPanel_TableArea = new JPanel();
	    jPanel_TableArea.setLayout(new BorderLayout());
	    jPanel_TableArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
	}
	return jPanel_TableArea;
    }

    // edit ver2 s.inoue 2009/06/23
	/**
	 * This method initializes jCheckBox_InputDone
	 *
	 * @return javax.swing.ExtendedCheckBox
	 */
	private ExtendedCheckBox getJCheckBox_JisiYear() {
		if (jCheckBox_JisiYear == null) {
			jCheckBox_JisiYear = new ExtendedCheckBox();
			jCheckBox_JisiYear.setText("���N�x");
			jCheckBox_JisiYear.setPreferredSize(new Dimension(84, 20));
		}
		return jCheckBox_JisiYear;
	}
    /**
     * This method initializes jPanel31
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel31() {
        if (jPanel31 == null) {
    	jPanel31 = new JPanel();
    	jPanel31.setLayout(new GridBagLayout());
        }
        return jPanel31;
    }

    /**
     * This method initializes jPanel32
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel32() {
        if (jPanel32 == null) {
    	GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
    	gridBagConstraints21.gridx = 4;
    	gridBagConstraints21.ipadx = 10;
    	gridBagConstraints21.weightx = 1.0D;
    	gridBagConstraints21.gridy = 0;
    	GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
    	gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
    	gridBagConstraints18.gridy = 1;
    	gridBagConstraints18.gridx = 3;
    	GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
    	gridBagConstraints15.gridx = 2;
    	gridBagConstraints15.gridy = 1;
    	GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
    	gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
    	gridBagConstraints14.gridy = 1;
    	gridBagConstraints14.insets = new Insets(0, 5, 0, 0);
    	gridBagConstraints14.gridx = 1;
    	GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
    	gridBagConstraints13.anchor = GridBagConstraints.WEST;
    	gridBagConstraints13.gridy = 1;
    	gridBagConstraints13.gridx = 0;
    	GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
    	gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
    	gridBagConstraints10.gridy = 0;
    	gridBagConstraints10.gridx = 3;
    	GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
    	gridBagConstraints9.gridx = 2;
    	gridBagConstraints9.insets = new Insets(0, 5, 0, 5);
    	gridBagConstraints9.gridy = 0;
    	GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
    	gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
    	gridBagConstraints8.gridy = 0;
    	gridBagConstraints8.insets = new Insets(0, 5, 0, 0);
    	gridBagConstraints8.gridx = 1;
    	GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
    	gridBagConstraints7.anchor = GridBagConstraints.WEST;
    	gridBagConstraints7.gridy = 0;
    	gridBagConstraints7.gridx = 0;
    	jPanel32 = new JPanel();
    	jPanel32.setLayout(new GridBagLayout());
    	jPanel32.add(jLabel34, gridBagConstraints7);
    	jPanel32.add(getJTextField_BeginDate(), gridBagConstraints8);
    	jPanel32.add(jLabel39, gridBagConstraints9);
    	jPanel32.add(getJTextField_EndDate(), gridBagConstraints10);
    	jPanel32.add(jLabel35, gridBagConstraints13);
    	jPanel32.add(getJTextField_HL7BeginDate(), gridBagConstraints14);
    	jPanel32.add(jLabel310, gridBagConstraints15);
    	jPanel32.add(getJTextField_HL7EndDate(), gridBagConstraints18);
    	jPanel32.add(getJPanel33(), gridBagConstraints21);
        }
        return jPanel32;
    }

    /**
     * This method initializes jPanel33
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel33() {
        if (jPanel33 == null) {
    	jPanel33 = new JPanel();
    	jPanel33.setLayout(new GridBagLayout());
        }
        return jPanel33;
    }


// del s.inoue 2009/12/02
//	/**
//	 * This method initializes jTextField_DocTanka1
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_TankaGoukei() {
//		if (jTextField_TankaGoukei == null) {
//			jTextField_TankaGoukei = new ExtendedTextField();
//			jTextField_TankaGoukei.setPreferredSize(new Dimension(80, 20));
//			jTextField_TankaGoukei.setHorizontalAlignment(JTextField.RIGHT);
//			jTextField_TankaGoukei.setEditable(false);
//		}
//		return jTextField_TankaGoukei;
//	}


// del s.inoue 2009/12/02
//	/**
//	 * This method initializes jTextField_FutanGoukei
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_FutanGoukei() {
//		if (jTextField_FutanGoukei == null) {
//			jTextField_FutanGoukei = new ExtendedTextField();
//			jTextField_FutanGoukei.setPreferredSize(new Dimension(80, 20));
//			jTextField_FutanGoukei.setHorizontalAlignment(JTextField.RIGHT);
//			jTextField_FutanGoukei.setEditable(false);
//		}
//		return jTextField_FutanGoukei;
//	}

//	/**
//	 * This method initializes jTextField_SeikyuGoukei
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_SeikyuGoukei() {
//		if (jTextField_SeikyuGoukei == null) {
//			jTextField_SeikyuGoukei = new ExtendedTextField();
//			jTextField_SeikyuGoukei.setPreferredSize(new Dimension(80, 20));
//			jTextField_SeikyuGoukei.setHorizontalAlignment(JTextField.RIGHT);
//			jTextField_SeikyuGoukei.setEditable(false);
//		}
//		return jTextField_SeikyuGoukei;
//	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}