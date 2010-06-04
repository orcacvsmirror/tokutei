package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Cursor;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextArea;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;

import javax.swing.BorderFactory;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 * 定型文マスタメンテナンス画面
 */
public class JKekkaTeikeiMaintenanceEditFrame extends JFrame implements ActionListener,KeyListener,MouseListener {

	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JPanel jPanel_NaviArea = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JPanel jPanel_Teikei = null;
	protected ExtendedComboBox jCombobox_TeikeibunType = null;
	protected ExtendedTextField jTextField_TeikeibunNo = null;
	protected ExtendedEditorPane jTextArea_Teikeibun = null;
	protected ExtendedButton jButton_Register = null;
	protected ExtendedButton jButton_End = null;
	protected ExtendedLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected ExtendedLabel jLabel1TeikeiType = null;
	protected ExtendedLabel jLabel1TeikeiNo = null;
	protected ExtendedLabel jLabel1Teikei = null;
	protected ExtendedLabel jLabelBikou = null;
	protected JScrollPane jScrollPane1 = null;
	protected ExtendedButton jButton_Cancel = null;
	/**
	 * This is the default constructor
	 */
	public JKekkaTeikeiMaintenanceEditFrame() {
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
		this.setLocationRelativeTo( null );
		this.setVisible(true);
		this.addKeyListener(this);
	}

	/**
	 * This method initializes jPanel_Content
	 *
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJPanel_Content() {
		if (jPanel_Content == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);
			jPanel_Content = new JPanel();
			jPanel_Content.setLayout(borderLayout);
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
			jPanel_Content.add(getJPanel_MainArea(), BorderLayout.CENTER);
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
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.gridy = 0;
			gridBagConstraints56.gridx = 0;
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.gridy = 0;
			gridBagConstraints55.gridx = 2;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridy = 0;
			gridBagConstraints54.anchor = GridBagConstraints.EAST;
			gridBagConstraints54.weightx = 1.0D;
			gridBagConstraints54.gridx = 2;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints54);
			jPanel_ButtonArea.add(getJButton_Cancel(), gridBagConstraints55);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints56);
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
			jButton_End.setActionCommand("終了");
			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_End.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("登録画面（コメントフィールド）のポップアップ画面で使用する定型文を作成します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_TEIKEI_MASTERMAINTENANCE_EDIT_FRAME_TITLE);

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
			jPanel_TitleArea.add(getJPanel_ExplArea2(), null);
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
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.gridx = 0;
			gridBagConstraints60.weightx = 1.0D;
			gridBagConstraints60.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints60.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints60.gridy = 1;
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.gridx = 0;
			gridBagConstraints59.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints59.weightx = 1.0D;
			gridBagConstraints59.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints59.gridy = 0;
			jLabal_SubExpl = new ExtendedLabel();
			jLabal_SubExpl.setText("各項目を入力後、「登録」ボタンを押して入力内容を登録します。");
			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jPanel_ExplArea2 = new JPanel();
			jPanel_ExplArea2.setName("jPanel4");
			jPanel_ExplArea2.setLayout(new GridBagLayout());
			jPanel_ExplArea2.add(jLabel_MainExpl, gridBagConstraints59);
			jPanel_ExplArea2.add(jLabal_SubExpl, gridBagConstraints60);
		}
		return jPanel_ExplArea2;
	}

	/**
	 * This method initializes jPanel_MainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_MainArea() {
		if (jPanel_MainArea == null) {

			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new BorderLayout());
			jPanel_MainArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

			jPanel_MainArea.add(getJPanel_DrawArea(), BorderLayout.NORTH);
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel_DrawArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_DrawArea() {
		if (jPanel_DrawArea == null) {
			GridBagConstraints gridBagConstraints310 = new GridBagConstraints();
			gridBagConstraints310.gridx = 0;
			gridBagConstraints310.gridy = 0;
			gridBagConstraints310.anchor = GridBagConstraints.WEST;
			gridBagConstraints310.weightx = 1.0D;

			jPanel_DrawArea = new JPanel();
			jPanel_DrawArea.setLayout(new GridBagLayout());
			jPanel_DrawArea.setName("jPanel_DrawArea");
			jPanel_DrawArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//			jPanel_DrawArea.setPreferredSize(new Dimension(300, 150));
			jPanel_DrawArea.add(getJPaneTeikei(), gridBagConstraints310);
		}
		return jPanel_DrawArea;
	}

	/**
	 * This method initializes jTextField_TeikeibunNo
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedComboBox getJTextField_TeikeibunType() {
		if (jCombobox_TeikeibunType == null) {
			jCombobox_TeikeibunType = new ExtendedComboBox(ImeMode.IME_OFF);
			jCombobox_TeikeibunType.setPreferredSize(new Dimension(200, 20));
		}
		return jCombobox_TeikeibunType;
	}

	/**
	 * This method initializes jTextField_TeikeibunNo
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_TeikeibunNo() {
		if (jTextField_TeikeibunNo == null) {
			jTextField_TeikeibunNo = new ExtendedTextField(ImeMode.IME_OFF);
			jTextField_TeikeibunNo.setPreferredSize(new Dimension(50, 20));
		}
		return jTextField_TeikeibunNo;
	}

	/**
	 * This method initializes jScrollPane1
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new Dimension(300, 150));
			jScrollPane1.setViewportView(getJTextField_Teikeibun());
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jTextArea_Teikeibun
	 *
	 * @return javax.swing.JEditorPane
	 */
	private ExtendedEditorPane getJTextField_Teikeibun() {
		if (jTextArea_Teikeibun == null) {
			jTextArea_Teikeibun = new ExtendedEditorPane(ImeMode.IME_ZENKAKU);
			jTextArea_Teikeibun.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jTextArea_Teikeibun.addMouseListener(this); // マウスリスナを登録
		}
		return jTextArea_Teikeibun;
	}
	public void actionPerformed(ActionEvent e)
	{
	}

	/*
	 * FrameSize Control
	 */
	public void validate()
	{
		Rectangle rect = getBounds();

		super.validate();

		if( ViewSettings.getFrameCommonWidth() > rect.width  ||
				ViewSettings.getFrameCommonHeight() > rect.height )
		{
			setBounds( rect.x,
					   rect.y,
					   ViewSettings.getFrameCommonWidth(),
					   ViewSettings.getFrameCommonHeight() );
		}
	}

	/**
	 * This method initializes jPanel8
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPaneTeikei() {
		if (jPanel_Teikei == null) {

			jLabel1TeikeiType = new ExtendedLabel();
			jLabel1TeikeiType.setText("所見種別");
			jLabel1TeikeiType.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel1TeikeiNo = new ExtendedLabel();
			jLabel1TeikeiNo.setText("所見No");
			jLabel1TeikeiNo.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel1Teikei = new ExtendedLabel();
			jLabel1Teikei.setText("所見");
			jLabel1Teikei.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabelBikou = new ExtendedLabel();
			jLabelBikou.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelBikou.setText("（全角128文字以内、半角可）");
			jLabelBikou.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.gridy = 1;
			gridBagConstraints63.gridx = 5;

			// edit s.inoue 2009/12/14
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.anchor = GridBagConstraints.NORTH;
			gridBagConstraints33.gridy = 0;
			gridBagConstraints33.gridx = 1;
			gridBagConstraints33.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.anchor = GridBagConstraints.NORTH;
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.gridx = 0;

			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.anchor = GridBagConstraints.NORTH;
			gridBagConstraints32.gridy = 0;
			gridBagConstraints32.gridx = 2;
			gridBagConstraints32.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.anchor = GridBagConstraints.NORTH;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.gridx = 3;
			gridBagConstraints11.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.anchor = GridBagConstraints.NORTH;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.gridx = 4;
			gridBagConstraints12.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridx = 5;
			gridBagConstraints10.insets = new Insets(0, 10, 0, 0);

			jPanel_Teikei = new JPanel();
			jPanel_Teikei.setLayout(new GridBagLayout());
			jPanel_Teikei.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel_Teikei.add(jLabel1TeikeiType,gridBagConstraints13);
			jPanel_Teikei.add(getJTextField_TeikeibunType(),gridBagConstraints33);
			jPanel_Teikei.add(jLabel1TeikeiNo, gridBagConstraints32);
			jPanel_Teikei.add(getJScrollPane1(), gridBagConstraints10);
			jPanel_Teikei.add(getJTextField_TeikeibunNo(), gridBagConstraints11);
			jPanel_Teikei.add(jLabel1Teikei, gridBagConstraints12);

			jPanel_Teikei.add(jLabelBikou, gridBagConstraints63);
		}
		return jPanel_Teikei;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			jButton_Register = new ExtendedButton();
			jButton_Register.setText("登録(F12)");
			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	/**
	 * This method initializes jButton_Cancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			jButton_Cancel = new ExtendedButton();
			jButton_Cancel.setText("キャンセル");
			jButton_Cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Cancel.setVisible(false);
			jButton_Cancel.addActionListener(this);
		}
		return jButton_Cancel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
