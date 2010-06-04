package jp.or.med.orca.jma_tokutei.common.frame;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;


import java.awt.Cursor;
import java.awt.Dimension;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.GuidanceLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import java.awt.Insets;
import java.awt.event.KeyEvent;


/**
 * 機関情報追加画面
 */
public class JAddKikanInformationFrame extends JFrame
	implements ActionListener, ItemListener, KeyListener ,FocusListener{

	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected GuidanceLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected GuidanceLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel = null;
	protected ExtendedButton jButton_Register = null;
	protected JPanel jPanel_Body = null;
	protected JPanel jPanel_Table = null;
	protected JPanel jPanel_ORCA = null;
	protected ExtendedRadioButton jRadioButton_Yes = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedRadioButton jRadioButton_No = null;
	protected JPanel jPanel_kikan = null;
	protected ExtendedLabel jLabel_kikanNumber = null;
	protected JPanel jPanel_orca = null;
	protected JPanel jPanel_space = null;
	protected ExtendedLabel jLabel_ip = null;
	protected ExtendedLabel jLabel_souhumotoNumber = null;
	protected ExtendedLabel jLabel_name = null;
	protected ExtendedLabel jLabel_address = null;
	protected ExtendedLabel jLabel_zip = null;
	protected ExtendedLabel jLabel_address2 = null;
	protected ExtendedLabel jLabel_tel = null;
	protected ExtendedTextField jTextField_kikanNumber = null;
	protected ExtendedTextField jTextField_souhumotoNumber = null;
	protected ExtendedTextField jTextField_kikanName = null;
	protected ExtendedTextField jTextField_zip = null;
	protected ExtendedTextField jTextField_address = null;
	protected ExtendedTextField jTextField_address2 = null;
	protected ExtendedTextField jTextField_tel = null;
	protected ExtendedTextField jTextField_ip = null;
	protected ExtendedLabel jLabel_port = null;
	protected ExtendedLabel jLabel_databaseName = null;
	protected ExtendedLabel jLabel_protocol = null;
	protected ExtendedLabel jLabel_dbUserId = null;
	protected ExtendedLabel jLabel_dbPassword = null;
	protected ExtendedLabel jLabel_encoding = null;
	protected ExtendedLabel jLabel_nichiReseUserId = null;
	protected ExtendedLabel jLabel_nichiResePassword = null;
	protected ExtendedTextField jTextField_portNumber = null;
	protected ExtendedTextField jTextField_databaseName = null;
	protected ExtendedTextField jTextField_protocol = null;
	protected ExtendedTextField jTextField_dbUserId = null;
	protected ExtendedTextField jTextField_dbPassword = null;
	protected ExtendedTextField jTextField_nichireseUserId = null;
	protected ExtendedTextField jTextField_nichiresePassword = null;
	protected ExtendedTextField jTextField_encoding = null;
	protected JPanel jPanel_hanrei = null;
	protected ExtendedLabel jLabel_requiredExample = null;
	protected ExtendedLabel jLabel151 = null;
	protected ExtendedLabel jLabel1512 = null;
//	private ExtendedLabel jLabel_kikanTitle = null;
	protected ExtendedLabel jLabel_orcaTitle = null;
	protected ExtendedLabel jLabel_kikanInfo = null;
	protected ExtendedButton jButton_ConnectionTest = null;
	protected ExtendedLabel jLabel_kikanno_format = null;
	protected ExtendedLabel jLabel_souhumotono_format1 = null;
	protected ExtendedLabel jLabel_kikanname_format = null;
	protected ExtendedLabel jLabel_zip_format = null;
	protected ExtendedLabel jLabel_address_format = null;
	protected ExtendedLabel jLabel_chiban_format = null;
	protected ExtendedLabel jLabel_tel_format = null;
	protected ExtendedLabel jLabel_ip_format1 = null;
	protected ExtendedLabel jLabel_port_format1 = null;
	protected ExtendedLabel jLabel_dbname_format1 = null;
	protected ExtendedLabel jLabel_protocol_format1 = null;
	protected ExtendedLabel jLabel_dbuserid_format = null;
	protected ExtendedLabel jLabel_dbuserpassword_format11 = null;
	protected ExtendedLabel jLabel_ncuserid_format = null;
	protected ExtendedLabel jLabel_ncpassword_format = null;
	protected ExtendedLabel jLabel_encording_format = null;
	protected ExtendedLabel jLabel_orcaidFormat = null;
	protected JPanel jPanel1 = null;
	protected ExtendedLabel jLabel_0surpress = null;
	protected ExtendedLabel jLabel_orcaIdDegit = null;
	protected ExtendedRadioButton jRadioButton_Yes1 = null;
	protected ExtendedRadioButton jRadioButton_No1 = null;
	protected ExtendedTextField jTextField_orcaIdDigit = null;
	protected ExtendedLabel jLabel_digitText = null;

	protected ButtonGroup bgUseOrca = new ButtonGroup();
	protected ButtonGroup bgSurpressOrcaID = new ButtonGroup();

	// add h.yoshitama 2009/09/30
	protected ExtendedLabel jLabelhuka = null;
	protected ExtendedLabel jLabel_disableExample = null;

	/**
	 * This is the default constructor
	 */
	public JAddKikanInformationFrame() {
//		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		/* Modified 2008/04/20 若月  */
		/* --------------------------------------------------- */
//		this.setSize(800, 600);
//		this.setContentPane(getJPanel_Content());
//		this.setTitle("特定健診システム");
//		setLocationRelativeTo( null );
//		this.setVisible(true);
		/* --------------------------------------------------- */
		this.setContentPane(getJPanel_Content());


		String title = ViewSettings.getTokuteFrameTitleWithKikanInfomation();
		if (title == null || title.isEmpty()) {
			title  = ViewSettings.getAdminCommonTitleWithVersion();
		}
		this.setTitle(title);

		/* Modified 2008/07/02 若月  */
		/* --------------------------------------------------- */
//		this.setSize(new Dimension(900, 600));
//		this.setPreferredSize(new Dimension(900, 600));
//		this.setMinimumSize(new Dimension(900, 600));
		/* --------------------------------------------------- */
		// edit s.inoue 2009/09/28
		// Dimension size = ViewSettings.getKikanInformationFrameSize();
		Dimension size = ViewSettings.getFrameCommonSize();

		this.setSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		/* --------------------------------------------------- */

//		this.setMinimumSize(ViewSettings.getFrameCommonSize());
		this.setLocationRelativeTo( null );
		this.setVisible(true);
		/* --------------------------------------------------- */

		/* Added 2008/07/22 若月  */
		/* --------------------------------------------------- */
		bgUseOrca.add(jRadioButton_Yes);
		bgUseOrca.add(jRadioButton_No);

		bgSurpressOrcaID.add(jRadioButton_Yes1);
		bgSurpressOrcaID.add(jRadioButton_No1);
		/* --------------------------------------------------- */
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
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.gridy = 0;
			gridBagConstraints45.gridx = 0;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.gridy = 0;
			gridBagConstraints44.weightx = 1.0D;
			gridBagConstraints44.anchor = GridBagConstraints.EAST;
			gridBagConstraints44.gridx = 1;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints44);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints45);
		}
		return jPanel_ButtonArea;
	}

	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			jButton_End = new ExtendedButton();
			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
			jButton_End.setActionCommand("終了");
			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_End.setText("戻る(F1)");
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
			CardLayout cardLayout2 = new CardLayout();
			cardLayout2.setHgap(10);
			cardLayout2.setVgap(10);
			jLabel_MainExpl = new GuidanceLabel("tokutei.kikaninformation.frame.guidance.main");
//			jLabel_MainExpl.setText("機関情報の追加を行います。値を編集後、登録ボタンを押して下さい。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
			//jLabel_Title = new TitleLabel("tokutei.kikaninformation.frame.title");
			// edit s.inoue 2009/10/15
			//jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_ADD_FRAME_TITLE);
			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
//			jLabel_Title.setText("機関情報の追加");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			jPanel_NaviArea = new JPanel();
			jPanel_NaviArea.setLayout(cardLayout2);
			jPanel_NaviArea.add(getJPanel_TitleArea(), getJPanel_TitleArea().getName());
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
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			gridLayout.setHgap(0);
			gridLayout.setColumns(0);
			gridLayout.setVgap(10);
			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(gridLayout);
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, null);
			jPanel_TitleArea.add(getJPanel_ExplArea1(), null);
		}
		return jPanel_TitleArea;
	}

	/**
	 * This method initializes jPanel_ExplArea2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ExplArea2() {
		if (jPanel_ExplArea2 == null) {
			jLabal_SubExpl = new GuidanceLabel("tokutei.kikaninformation.frame.guidance.sub");

			/* Modified 2008/03/07 若月  */
			/* --------------------------------------------------- */
//			jLabal_SubExpl.setText("健診機関と送付元機関が違う場合は送付元機関番号を修正して下さい。");
			/* --------------------------------------------------- */
//			jLabal_SubExpl.setText("医師会等でとりまとめて送付される場合以外は、送付元機関番号には、健診機関番号と同じ番号を入れてください。");
			/* --------------------------------------------------- */

//			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			jPanel_ExplArea2 = new JPanel();
			jPanel_ExplArea2.setName("jPanel4");
			jPanel_ExplArea2.setLayout(gridLayout1);
			jPanel_ExplArea2.add(jLabel_MainExpl, null);
			jPanel_ExplArea2.add(jLabal_SubExpl, null);
		}
		return jPanel_ExplArea2;
	}

	/**
	 * This method initializes jPanel_ExplArea1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ExplArea1() {
		if (jPanel_ExplArea1 == null) {
			CardLayout cardLayout1 = new CardLayout();
			cardLayout1.setHgap(20);
			jPanel_ExplArea1 = new JPanel();
			jPanel_ExplArea1.setLayout(cardLayout1);
			jPanel_ExplArea1.add(getJPanel_ExplArea2(), getJPanel_ExplArea2().getName());
		}
		return jPanel_ExplArea1;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			CardLayout cardLayout = new CardLayout();

			jPanel = new JPanel();
			jPanel.setLayout(cardLayout);
			jPanel.add(getJPanel_Body(), getJPanel_Body().getName());
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			jButton_Register = new ExtendedButton();
			jButton_Register.setText("登録(F12)");
//			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	/**
	 * This method initializes jPanel_Body
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Body() {
		if (jPanel_Body == null) {
			jPanel_Body = new JPanel();
			jPanel_Body.setLayout(new BorderLayout());
			jPanel_Body.setName("jPanel_Table");
			jPanel_Body.add(getJPanel_Table(), BorderLayout.CENTER);
		}
		return jPanel_Body;
	}

	/**
	 * This method initializes jPanel_Table
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Table() {
		if (jPanel_Table == null) {
			jLabel_orcaTitle = new ExtendedLabel();
			jLabel_orcaTitle.setOpaque(true);
			jLabel_orcaTitle.setText("日レセ連携情報");

			Font currentFont = jLabel_orcaTitle.getFont();
			Font boldFont = new Font(
								currentFont.getFontName(),
								Font.BOLD,
								currentFont.getSize()
								);
			jLabel_orcaTitle.setFont(boldFont);

			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridx = 0;
			gridBagConstraints35.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints35.insets = new Insets(10, 10, 0, 0);
			gridBagConstraints35.gridy = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.ipady = 10;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.gridy = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.insets = new Insets(10, 10, 0, 0);
			gridBagConstraints1.gridheight = 1;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.insets = new Insets(10, 10, 0, 0);
			gridBagConstraints.gridy = 1;
			jPanel_Table = new JPanel();
			jPanel_Table.setLayout(new GridBagLayout());
			jPanel_Table.add(getJPanel_kikan(), gridBagConstraints);
			jPanel_Table.add(getJPanel_orca(), gridBagConstraints1);
			jPanel_Table.add(getJPanel_space(), gridBagConstraints2);
			jPanel_Table.add(getJPanel_hanrei(), gridBagConstraints35);
		}
		return jPanel_Table;
	}

	/**
	 * This method initializes jPanel_ORCA
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ORCA() {
		if (jPanel_ORCA == null) {
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.gridy = 0;
			gridBagConstraints42.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints42.gridx = 1;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridy = 0;
			gridBagConstraints41.gridx = 0;
			jPanel_ORCA = new JPanel();
			jPanel_ORCA.setLayout(new GridBagLayout());
			jPanel_ORCA.add(getJRadioButton_Yes(), gridBagConstraints41);
			jPanel_ORCA.add(getJRadioButton_No(), gridBagConstraints42);
		}
		return jPanel_ORCA;
	}

	/**
	 * This method initializes jRadioButton_Yes
	 *
	 * @return javax.swing.EventHandleRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Yes() {
		if (jRadioButton_Yes == null) {
			jRadioButton_Yes = new ExtendedRadioButton();
			jRadioButton_Yes.setText("はい");
			jRadioButton_Yes.setFont(new Font("Dialog", Font.PLAIN, 12));
			jRadioButton_Yes.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jRadioButton_Yes.addActionListener(this);
			jRadioButton_Yes.addItemListener(this);
		}
		return jRadioButton_Yes;
	}

	/**
	 * This method initializes jRadioButton_No
	 *
	 * @return javax.swing.EventHandleRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_No() {
		if (jRadioButton_No == null) {
			jRadioButton_No = new ExtendedRadioButton();
			jRadioButton_No.setText("いいえ");
			jRadioButton_No.setFont(new Font("Dialog", Font.PLAIN, 12));
			jRadioButton_No.addActionListener(this);
			jRadioButton_No.addItemListener(this);
		}
		return jRadioButton_No;
	}

	/**
	 * This method initializes jPanel_kikan
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_kikan() {
		if (jPanel_kikan == null) {
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridx = 2;
			gridBagConstraints52.anchor = GridBagConstraints.WEST;
			gridBagConstraints52.gridy = 13;
			jLabel_tel_format = new ExtendedLabel();
			jLabel_tel_format.setText("（半角数字11桁以下）");
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 0;
			gridBagConstraints51.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints51.gridwidth = 3;
			gridBagConstraints51.anchor = GridBagConstraints.EAST;
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.gridy = 12;
			jLabel_chiban_format = new ExtendedLabel();
//			jLabel_chiban_format.setText("（全角のみ200文字以内）");
			jLabel_chiban_format.setText("（所在地と地番方書を合わせて、全角40文字以内）");
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.gridx = 2;
			gridBagConstraints50.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints50.gridy = 10;
			jLabel_address_format = new ExtendedLabel();
			jLabel_address_format.setText("（全角のみ200文字以内）");
			jLabel_address_format.setVisible(false);
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.gridx = 2;
			gridBagConstraints49.anchor = GridBagConstraints.WEST;
			gridBagConstraints49.gridy = 8;
			jLabel_zip_format = new ExtendedLabel();
			jLabel_zip_format.setText("（半角数字7桁）");
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.gridx = 2;
			gridBagConstraints48.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints48.gridy = 7;
			jLabel_kikanname_format = new ExtendedLabel();
			jLabel_kikanname_format.setText("（全角20文字以内）");
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.gridx = 2;
			gridBagConstraints47.anchor = GridBagConstraints.WEST;
			gridBagConstraints47.gridy = 5;
			jLabel_souhumotono_format1 = new ExtendedLabel();
			jLabel_souhumotono_format1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_souhumotono_format1.setText("（半角数字10桁以下）");
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.gridx = 2;
			gridBagConstraints46.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints46.anchor = GridBagConstraints.WEST;
			gridBagConstraints46.gridy = 4;
			jLabel_kikanno_format = new ExtendedLabel();
			jLabel_kikanno_format.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_kikanno_format.setText("（半角数字10桁）");
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.gridx = 0;
			gridBagConstraints37.anchor = GridBagConstraints.WEST;
			gridBagConstraints37.gridy = 0;
			jLabel_kikanInfo = new ExtendedLabel();
			jLabel_kikanInfo.setText("機関情報");

			Font currentFont = jLabel_kikanInfo.getFont();
			Font boldFont = new Font(
								currentFont.getFontName(),
								Font.BOLD,
								currentFont.getSize()
								);

			jLabel_kikanInfo.setFont(boldFont);

			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.anchor = GridBagConstraints.WEST;
			gridBagConstraints36.gridy = 0;
			gridBagConstraints36.gridx = 0;
//			jLabel_kikanTitle = new ExtendedLabel();
//			jLabel_kikanTitle.setText("機関情報");
//			jLabel_kikanTitle.setOpaque(true);
//			jLabel_kikanTitle.setPreferredSize(new Dimension(180, 18));

			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints17.gridy = 13;
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridx = 1;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridy = 11;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.gridwidth = 2;
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.gridx = 1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridy = 9;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.gridwidth = 2;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints14.gridy = 8;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.gridy = 6;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.gridwidth = 2;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridy = 5;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridy = 4;
			gridBagConstraints11.insets = new Insets(10, 0, 0, 10);
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridy = 4;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridy = 13;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 11;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 9;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridy = 8;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 6;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints3.gridy = 5;
			jLabel_souhumotoNumber = new ExtendedLabel();
			jLabel_souhumotoNumber.setText("送付元機関番号<br>（請求事務代行含む）");
			jLabel_kikanNumber = new ExtendedLabel();
			jLabel_kikanNumber.setText("特定健診機関番号");
			jLabel_tel = new ExtendedLabel();
			jLabel_tel.setText("電話番号");
			jLabel_address2 = new ExtendedLabel();
			jLabel_address2.setText("地番方書");
			jLabel_zip = new ExtendedLabel();
			jLabel_zip.setText("郵便番号");
			jLabel_address = new ExtendedLabel();
			jLabel_address.setText("所在地");
			jLabel_name = new ExtendedLabel();
			jLabel_name.setText("名称");

			jPanel_kikan = new JPanel();
			jPanel_kikan.setLayout(new GridBagLayout());
			jPanel_kikan.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel_kikan.add(jLabel_kikanNumber, gridBagConstraints11);
			jPanel_kikan.add(jLabel_souhumotoNumber, gridBagConstraints3);
			jPanel_kikan.add(jLabel_name, gridBagConstraints4);
			jPanel_kikan.add(jLabel_zip, gridBagConstraints7);
			jPanel_kikan.add(jLabel_address, gridBagConstraints6);
			jPanel_kikan.add(jLabel_address2, gridBagConstraints8);
			jPanel_kikan.add(jLabel_tel, gridBagConstraints9);
			jPanel_kikan.add(getJTextField_number(), gridBagConstraints10);
			jPanel_kikan.add(getJTextField_souhumotoNumber(), gridBagConstraints12);
			jPanel_kikan.add(getJTextField_name(), gridBagConstraints13);
			jPanel_kikan.add(getJTextField_zip(), gridBagConstraints14);
			jPanel_kikan.add(getJTextField_address(), gridBagConstraints15);
			jPanel_kikan.add(getJTextField_address2(), gridBagConstraints16);
			jPanel_kikan.add(getJTextField_tel(), gridBagConstraints17);
			jPanel_kikan.add(jLabel_kikanInfo, gridBagConstraints37);
			jPanel_kikan.add(jLabel_kikanno_format, gridBagConstraints46);
			jPanel_kikan.add(jLabel_souhumotono_format1, gridBagConstraints47);
			jPanel_kikan.add(jLabel_kikanname_format, gridBagConstraints48);
			jPanel_kikan.add(jLabel_zip_format, gridBagConstraints49);
			jPanel_kikan.add(jLabel_address_format, gridBagConstraints50);
			jPanel_kikan.add(jLabel_chiban_format, gridBagConstraints51);
			jPanel_kikan.add(jLabel_tel_format, gridBagConstraints52);
		}
		return jPanel_kikan;
	}

	/**
	 * This method initializes jPanel_orca
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_orca() {
		if (jPanel_orca == null) {
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.gridx = 1;
			gridBagConstraints63.gridwidth = 2;
			gridBagConstraints63.fill = GridBagConstraints.BOTH;
			gridBagConstraints63.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints63.gridy = 11;
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridx = 0;
			gridBagConstraints62.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints62.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints62.gridy = 11;
			jLabel_orcaidFormat = new ExtendedLabel();
			jLabel_orcaidFormat.setText("患者IDのフォーマット");
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 2;
			gridBagConstraints61.anchor = GridBagConstraints.WEST;
			gridBagConstraints61.gridy = 10;
			jLabel_encording_format = new ExtendedLabel();
			jLabel_encording_format.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_encording_format.setText("（半角英数字のみ）");
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.gridx = 2;
			gridBagConstraints60.anchor = GridBagConstraints.WEST;
			gridBagConstraints60.gridy = 9;
			jLabel_ncpassword_format = new ExtendedLabel();
			jLabel_ncpassword_format.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_ncpassword_format.setText("（半角英数字のみ）");
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.gridx = 2;
			gridBagConstraints59.anchor = GridBagConstraints.WEST;
			gridBagConstraints59.gridy = 8;
			jLabel_ncuserid_format = new ExtendedLabel();
			jLabel_ncuserid_format.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_ncuserid_format.setText("（半角英数字のみ）");
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.gridx = 2;
			gridBagConstraints58.anchor = GridBagConstraints.WEST;
			gridBagConstraints58.gridy = 7;
			jLabel_dbuserpassword_format11 = new ExtendedLabel();
			jLabel_dbuserpassword_format11.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_dbuserpassword_format11.setText("（半角英数字のみ）");
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.gridx = 2;
			gridBagConstraints57.anchor = GridBagConstraints.WEST;
			gridBagConstraints57.gridy = 6;
			jLabel_dbuserid_format = new ExtendedLabel();
			jLabel_dbuserid_format.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_dbuserid_format.setText("（半角英数字のみ）");
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.gridx = 2;
			gridBagConstraints56.anchor = GridBagConstraints.WEST;
			gridBagConstraints56.gridy = 5;
			jLabel_protocol_format1 = new ExtendedLabel();
			jLabel_protocol_format1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_protocol_format1.setText("（半角数字のみ）");
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.gridx = 2;
			gridBagConstraints55.anchor = GridBagConstraints.WEST;
			gridBagConstraints55.gridy = 4;
			jLabel_dbname_format1 = new ExtendedLabel();
			jLabel_dbname_format1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_dbname_format1.setText("（半角英数字のみ）");
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridx = 2;
			gridBagConstraints54.anchor = GridBagConstraints.WEST;
			gridBagConstraints54.gridy = 3;
			jLabel_port_format1 = new ExtendedLabel();
			jLabel_port_format1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_port_format1.setText("（半角数字6桁以下）");
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridx = 2;
			gridBagConstraints53.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints53.anchor = GridBagConstraints.WEST;
			gridBagConstraints53.gridy = 2;
			jLabel_ip_format1 = new ExtendedLabel();
			jLabel_ip_format1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel_ip_format1.setText("（IPv4形式）");
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.gridx = 2;
			gridBagConstraints43.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints43.anchor = GridBagConstraints.SOUTHEAST;
			gridBagConstraints43.gridy = 12;
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = GridBagConstraints.WEST;
			gridBagConstraints40.gridx = 1;
			gridBagConstraints40.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints40.gridy = 1;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.gridx = 0;
			gridBagConstraints39.insets = new Insets(10, 0, 0, 5);
			gridBagConstraints39.anchor = GridBagConstraints.WEST;
			gridBagConstraints39.gridy = 1;
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.anchor = GridBagConstraints.WEST;
			gridBagConstraints38.gridx = 0;
			gridBagConstraints38.gridy = 0;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridy = 10;
			gridBagConstraints34.weightx = 1.0;
			gridBagConstraints34.gridx = 1;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridy = 9;
			gridBagConstraints33.weightx = 1.0;
			gridBagConstraints33.gridx = 1;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridy = 8;
			gridBagConstraints32.weightx = 1.0;
			gridBagConstraints32.gridx = 1;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridy = 7;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.gridx = 1;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints30.gridy = 6;
			gridBagConstraints30.weightx = 1.0;
			gridBagConstraints30.gridx = 1;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.gridy = 5;
			gridBagConstraints29.weightx = 1.0;
			gridBagConstraints29.gridx = 1;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridy = 4;
			gridBagConstraints28.weightx = 1.0;
			gridBagConstraints28.gridx = 1;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridy = 3;
			gridBagConstraints27.weightx = 1.0;
			gridBagConstraints27.gridx = 1;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridx = 0;
			gridBagConstraints26.anchor = GridBagConstraints.WEST;
			gridBagConstraints26.gridy = 9;
			jLabel_nichiResePassword = new ExtendedLabel();
			jLabel_nichiResePassword.setText("日レセのパスワード");
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridx = 0;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			gridBagConstraints25.gridy = 8;
			jLabel_nichiReseUserId = new ExtendedLabel();
			jLabel_nichiReseUserId.setText("日レセのユーザID");
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 0;
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.gridy = 10;
			jLabel_encoding = new ExtendedLabel();
			jLabel_encoding.setText("文字列のエンコーディング");
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 0;
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.insets = new Insets(5, 0, 5, 10);
			gridBagConstraints23.gridy = 7;
			jLabel_dbPassword = new ExtendedLabel();
			jLabel_dbPassword.setText("データベースユーザの<br>パスワード");
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridx = 0;
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridy = 6;
			jLabel_dbUserId = new ExtendedLabel();
			jLabel_dbUserId.setText("データベースのユーザID");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 5;
			jLabel_protocol = new ExtendedLabel();
			jLabel_protocol.setText("プロトコル");
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 4;
			jLabel_databaseName = new ExtendedLabel();
			jLabel_databaseName.setText("データベース名");
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridy = 3;
			jLabel_port = new ExtendedLabel();
			jLabel_port.setText("ポート番号");
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridy = 2;
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints18.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints5.gridx = 0;
			jLabel = new ExtendedLabel();

			/* Modified 2008/03/07 若月  */
			/* --------------------------------------------------- */
//			jLabel.setText("日医標準レセプトソフトと連帯する");
			/* --------------------------------------------------- */
			jLabel.setText("日医標準レセプトソフトと<br>連携する");
			/* --------------------------------------------------- */

//			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_ip = new ExtendedLabel();
			jLabel_ip.setText("IPアドレス");
			jPanel_orca = new JPanel();
			jPanel_orca.setLayout(new GridBagLayout());
			jPanel_orca.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel_orca.add(jLabel_ip, gridBagConstraints5);
			jPanel_orca.add(getJTextField_ip(), gridBagConstraints18);
			jPanel_orca.add(jLabel_port, gridBagConstraints19);
			jPanel_orca.add(jLabel_databaseName, gridBagConstraints20);
			jPanel_orca.add(jLabel_protocol, gridBagConstraints21);
			jPanel_orca.add(jLabel_dbUserId, gridBagConstraints22);
			jPanel_orca.add(jLabel_dbPassword, gridBagConstraints23);
			jPanel_orca.add(jLabel_encoding, gridBagConstraints24);
			jPanel_orca.add(jLabel_nichiReseUserId, gridBagConstraints25);
			jPanel_orca.add(jLabel_nichiResePassword, gridBagConstraints26);
			jPanel_orca.add(getJTextField_portNumber(), gridBagConstraints27);
			jPanel_orca.add(getJTextField_databaseName(), gridBagConstraints28);
			jPanel_orca.add(getJTextField_protocol(), gridBagConstraints29);
			jPanel_orca.add(getJTextField_userId(), gridBagConstraints30);
			jPanel_orca.add(getJTextField_dbPassword(), gridBagConstraints31);
			jPanel_orca.add(getJTextField_nichireseUserId(), gridBagConstraints32);
			jPanel_orca.add(getJTextField_nichiresePassword(), gridBagConstraints33);
			jPanel_orca.add(getJTextField_encoding(), gridBagConstraints34);
			jPanel_orca.add(jLabel_orcaTitle, gridBagConstraints38);
			jPanel_orca.add(jLabel, gridBagConstraints39);
			jPanel_orca.add(getJPanel_ORCA(), gridBagConstraints40);
			jPanel_orca.add(getJButton_ConnectionTest(), gridBagConstraints43);
			jPanel_orca.add(jLabel_ip_format1, gridBagConstraints53);
			jPanel_orca.add(jLabel_port_format1, gridBagConstraints54);
			jPanel_orca.add(jLabel_dbname_format1, gridBagConstraints55);
			jPanel_orca.add(jLabel_protocol_format1, gridBagConstraints56);
			jPanel_orca.add(jLabel_dbuserid_format, gridBagConstraints57);
			jPanel_orca.add(jLabel_dbuserpassword_format11, gridBagConstraints58);
			jPanel_orca.add(jLabel_ncuserid_format, gridBagConstraints59);
			jPanel_orca.add(jLabel_ncpassword_format, gridBagConstraints60);
			jPanel_orca.add(jLabel_encording_format, gridBagConstraints61);
			jPanel_orca.add(jLabel_orcaidFormat, gridBagConstraints62);
			jPanel_orca.add(getJPanel1(), gridBagConstraints63);
		}
		return jPanel_orca;
	}

	/**
	 * This method initializes jPanel_space
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_space() {
		if (jPanel_space == null) {
			jPanel_space = new JPanel();
			jPanel_space.setLayout(new GridBagLayout());
		}
		return jPanel_space;
	}

	/**
	 * This method initializes jTextField_number
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_number() {
		if (jTextField_kikanNumber == null) {
			jTextField_kikanNumber = new ExtendedTextField("", 10, ImeMode.IME_OFF);
			jTextField_kikanNumber.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_kikanNumber;
	}

	/**
	 * This method initializes jTextField_souhumotoNumber
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_souhumotoNumber() {
		if (jTextField_souhumotoNumber == null) {
			// edit s.inoue 20081216 11桁⇒10桁
			//jTextField_souhumotoNumber = new ExtendedTextField("", 11, ImeMode.IME_OFF);
			jTextField_souhumotoNumber = new ExtendedTextField("", 10, ImeMode.IME_OFF);
			jTextField_souhumotoNumber.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_souhumotoNumber;
	}

	/**
	 * This method initializes jTextField_name
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_name() {
		if (jTextField_kikanName == null) {
			/* Modified 2008/08/07 若月  */
			/* --------------------------------------------------- */
//			jTextField_kikanName = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			/* --------------------------------------------------- */
			jTextField_kikanName = new ExtendedTextField("", 20, ImeMode.IME_ZENKAKU);
			/* --------------------------------------------------- */
			jTextField_kikanName.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_kikanName;
	}

	/**
	 * This method initializes jTextField_zip
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_zip() {
		if (jTextField_zip == null) {
			jTextField_zip = new ExtendedTextField("", 7, ImeMode.IME_OFF);
			jTextField_zip.setPreferredSize(new Dimension(150, 20));
			jTextField_zip.addFocusListener(this);
		}
		return jTextField_zip;
	}

	/**
	 * This method initializes jTextField_address
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_address() {
		if (jTextField_address == null) {
			/* Modified 2008/08/07 若月  */
			/* --------------------------------------------------- */
//			jTextField_address = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			/* --------------------------------------------------- */
			jTextField_address = new ExtendedTextField("", 40, ImeMode.IME_ZENKAKU);
			/* --------------------------------------------------- */
			jTextField_address.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_address;
	}

	/**
	 * This method initializes jTextField_address2
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_address2() {
		if (jTextField_address2 == null) {
			/* Modified 2008/08/07 若月  */
			/* --------------------------------------------------- */
//			jTextField_address2 = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			/* --------------------------------------------------- */
			jTextField_address2 = new ExtendedTextField("", 40, ImeMode.IME_ZENKAKU);
			/* --------------------------------------------------- */
			jTextField_address2.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_address2;
	}

	/**
	 * This method initializes jTextField_tel
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_tel() {
		if (jTextField_tel == null) {
			jTextField_tel = new ExtendedTextField("", 11, ImeMode.IME_OFF);
			jTextField_tel.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_tel;
	}

	/**
	 * This method initializes jTextField_ip
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_ip() {
		if (jTextField_ip == null) {
			jTextField_ip = new ExtendedTextField(ImeMode.IME_OFF);
			jTextField_ip.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_ip;
	}

	/**
	 * This method initializes jTextField_portNumber
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_portNumber() {
		if (jTextField_portNumber == null) {
			jTextField_portNumber = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_portNumber.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_portNumber;
	}

	/**
	 * This method initializes jTextField_databaseName
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_databaseName() {
		if (jTextField_databaseName == null) {
			jTextField_databaseName = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_databaseName.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_databaseName;
	}

	/**
	 * This method initializes jTextField_protocol
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_protocol() {
		if (jTextField_protocol == null) {
			jTextField_protocol = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_protocol.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_protocol;
	}

	/**
	 * This method initializes jTextField_userId
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_userId() {
		if (jTextField_dbUserId == null) {
			jTextField_dbUserId = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_dbUserId.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_dbUserId;
	}

	/**
	 * This method initializes jTextField_dbPassword
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_dbPassword() {
		if (jTextField_dbPassword == null) {
			jTextField_dbPassword = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_dbPassword.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_dbPassword;
	}

	/**
	 * This method initializes jTextField_nichireseUserId
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_nichireseUserId() {
		if (jTextField_nichireseUserId == null) {
			jTextField_nichireseUserId = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_nichireseUserId.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_nichireseUserId;
	}

	/**
	 * This method initializes jTextField_nichiresePassword
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_nichiresePassword() {
		if (jTextField_nichiresePassword == null) {
			jTextField_nichiresePassword = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_nichiresePassword.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_nichiresePassword;
	}

	/**
	 * This method initializes jTextField_encoding
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_encoding() {
		if (jTextField_encoding == null) {
			jTextField_encoding = new ExtendedTextField(
					ImeMode.IME_OFF);
			jTextField_encoding.setPreferredSize(new Dimension(150, 20));
		}
		return jTextField_encoding;
	}

	/**
	 * This method initializes jPanel_hanrei
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_hanrei() {
		if (jPanel_hanrei == null) {
			GridBagConstraints gridBagConstraints88 = new GridBagConstraints();
			gridBagConstraints88.gridx = 0;
			gridBagConstraints88.gridy = 0;
			jLabel1512 = new ExtendedLabel();
			// edit h.yoshitama 2009/09/25
			jLabel1512.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel1512.setText("凡例");
			jLabel1512.setPreferredSize(new Dimension(100, 16));
			GridBagConstraints gridBagConstraints86 = new GridBagConstraints();
			gridBagConstraints86.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints86.gridy = 0;
			gridBagConstraints86.gridx = 2;
			jLabel151 = new ExtendedLabel();
			jLabel151.setFont(new Font("Dialog", Font.PLAIN, 12));
			// edit h.yoshitama 2009/09/25
			jLabel151.setText("入力必須項目");
			GridBagConstraints gridBagConstraints84 = new GridBagConstraints();
			gridBagConstraints84.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints84.gridy = 0;
			gridBagConstraints84.gridx = 1;

			// add h.yoshitama 2009/09/30
			GridBagConstraints gridBagConstraints89 = new GridBagConstraints();
			gridBagConstraints89.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints89.gridy = 0;
			gridBagConstraints89.gridx = 4;
			jLabelhuka = new ExtendedLabel();
			jLabelhuka.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelhuka.setText("入力不可能項目");
			GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
			gridBagConstraints90.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints90.gridy = 0;
			gridBagConstraints90.gridx = 3;

			jLabel_requiredExample = new ExtendedLabel();
			jLabel_requiredExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_requiredExample.setPreferredSize(new Dimension(50, 20));
			jLabel_requiredExample.setOpaque(true);
			jPanel_hanrei = new JPanel();
			jPanel_hanrei.setLayout(new GridBagLayout());
			jPanel_hanrei.setOpaque(false);
			jPanel_hanrei.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel_hanrei.add(jLabel_requiredExample, gridBagConstraints84);
			jPanel_hanrei.add(jLabel151, gridBagConstraints86);
			jPanel_hanrei.add(jLabel1512, gridBagConstraints88);

			// add h.yoshitama 2009/09/30
			jLabel_disableExample = new ExtendedLabel();
			jLabel_disableExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_disableExample.setPreferredSize(new Dimension(50, 20));
			jLabel_disableExample.setOpaque(true);

			jPanel_hanrei.add(jLabel_disableExample, gridBagConstraints90);
			jPanel_hanrei.add(jLabelhuka, gridBagConstraints89);

		}
		return jPanel_hanrei;
	}

	public void itemStateChanged(ItemEvent arg0) {
	}

	/**
	 * This method initializes jButton_ConnectionTest
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_ConnectionTest() {
		if (jButton_ConnectionTest == null) {
			jButton_ConnectionTest = new ExtendedButton();
			jButton_ConnectionTest.setText("接続テスト(C)");
			jButton_ConnectionTest.addActionListener(this);
			jButton_ConnectionTest.setMnemonic(KeyEvent.VK_C);
		}
		return jButton_ConnectionTest;
	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.gridx = 2;
			gridBagConstraints69.anchor = GridBagConstraints.WEST;
			gridBagConstraints69.gridy = 1;
			jLabel_digitText = new ExtendedLabel();
			jLabel_digitText.setText("桁");
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.gridy = 1;
			gridBagConstraints68.gridx = 1;
			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
			gridBagConstraints67.gridx = 2;
			gridBagConstraints67.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints67.weightx = 1.0D;
			gridBagConstraints67.anchor = GridBagConstraints.WEST;
			gridBagConstraints67.gridy = 0;
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.gridx = 1;
			gridBagConstraints66.gridy = 0;
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.gridx = 0;
			gridBagConstraints65.anchor = GridBagConstraints.WEST;
			gridBagConstraints65.gridy = 1;
			jLabel_orcaIdDegit = new ExtendedLabel();
			jLabel_orcaIdDegit.setText("患者IDの桁数");
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.gridx = 0;
			gridBagConstraints64.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints64.gridy = 0;
			jLabel_0surpress = new ExtendedLabel();
			jLabel_0surpress.setText("先頭 0 埋めをする");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(jLabel_0surpress, gridBagConstraints64);
			jPanel1.add(jLabel_orcaIdDegit, gridBagConstraints65);
			jPanel1.add(getJRadioButton_Yes1(), gridBagConstraints66);
			jPanel1.add(getJRadioButton_No1(), gridBagConstraints67);
			jPanel1.add(getJTextField_orcaIdDigit(), gridBagConstraints68);
			jPanel1.add(jLabel_digitText, gridBagConstraints69);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton_Yes1
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Yes1() {
		if (jRadioButton_Yes1 == null) {
			jRadioButton_Yes1 = new ExtendedRadioButton();
			jRadioButton_Yes1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jRadioButton_Yes1.setText("\u306f\u3044");
			jRadioButton_Yes1.setOpaque(false);
			jRadioButton_Yes1.setFont(new Font("Dialog", Font.PLAIN, 12));

			jRadioButton_Yes1.addActionListener(this);
			jRadioButton_Yes1.addItemListener(this);
		}
		return jRadioButton_Yes1;
	}

	/**
	 * This method initializes jRadioButton_No1
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_No1() {
		if (jRadioButton_No1 == null) {
			jRadioButton_No1 = new ExtendedRadioButton();
			jRadioButton_No1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jRadioButton_No1.setOpaque(false);
			jRadioButton_No1.setText("\u3044\u3044\u3048");

			jRadioButton_No1.addActionListener(this);
			jRadioButton_No1.addItemListener(this);
		}
		return jRadioButton_No1;
	}

	/**
	 * This method initializes jTextField_orcaIdDigit
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_orcaIdDigit() {
		if (jTextField_orcaIdDigit == null) {
			jTextField_orcaIdDigit = new ExtendedTextField(ImeMode.IME_OFF);
			jTextField_orcaIdDigit.setPreferredSize(new Dimension(50, 20));
		}
		return jTextField_orcaIdDigit;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
