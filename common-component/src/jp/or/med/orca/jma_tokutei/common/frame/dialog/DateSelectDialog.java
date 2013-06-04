package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

// add s.inoue 2009/09/29
/**
 * ���t�I���_�C�A���O���
 */
public class DateSelectDialog extends JDialog
	implements ActionListener, KeyListener, IDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanelNaviArea = null;
	private JPanel jPanelButtonArea = null;

	protected ExtendedButton jButtonOK = null;
	protected ExtendedButton jButtonCancel = null;
	private ExtendedLabel jLabelTitle = null;
	protected RETURN_VALUE ReturnValue;  //  @jve:decl-index=0:
	protected ArrayList<Hashtable<String, String>> selectedRow;
	// add s.inoue 2009/10/04
	private static JConnection kikanDatabase = null;
	protected String ReturnKenshinDate;
	private TKensakekaTokuteiDao tTokuteiDao = null;  //  @jve:decl-index=0:
	private TKensakekaSonotaDao tSonotaDao = null;  //  @jve:decl-index=0:
	private boolean isShowCancel;
	private JPanel jPanel1 = null;
	private Font defaultFont;

	// del s.inoue 2009/10/04
	// private JPanel jPanel = null;

	// edit s.inoue 2009/10/04
	// private ExtendedLabel jLabelMessage = null;
	private ExtendedTextField jTextField_KenshinDate = null;
	private ExtendedLabel jLabel_KenshinDate = null;

    private static org.apache.log4j.Logger logger = Logger.getLogger(DateSelectDialog.class);
	/**
	 * @param owner
	 */
	public DateSelectDialog(Frame owner,ArrayList<Hashtable<String, String>> selectedRow) {
		super(owner);
		defaultFont = ViewSettings.getCommonUserInputFont();
		initialize();
		this.setLocationRelativeTo(null);
		this.selectedRow = selectedRow;
	}

	private void initialize() {
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(376, 193);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		// del s.inoue 2009/10/04
		// this.setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.jButtonOK.grabFocus();

		// edit s.inoue 2010/05/19
		//�ȉ��ͳ���޳�E��̏I��[X]���݂��������ꍇ�̏���
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ReturnValue = RETURN_VALUE.CANCEL;
				setVisible(false);
			}
		});

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
			jContentPane.add(getJPanelButtonArea(), BorderLayout.SOUTH);
			// edit s.inoue 2009/10/04
			jContentPane.add(getJPanel1(),BorderLayout.CENTER);
			// jContentPane.add(getJPanel(), BorderLayout.CENTER);
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
			jLabelTitle = new TitleLabel("");
			jLabelTitle.setText("");
			jLabelTitle.setName("jLabelTitle");
			jPanelNaviArea.add(jLabelTitle, jLabelTitle.getName());
		}
		return jPanelNaviArea;
	}

	/**
	 * This method initializes jPanelButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtonArea() {
		if (jPanelButtonArea == null) {
			GridBagConstraints gridBagConstraintsOkBtn = new GridBagConstraints();
			gridBagConstraintsOkBtn.gridx = (isShowCancel ? 1 : 2);
			gridBagConstraintsOkBtn.gridy = 0;

			GridBagConstraints gridBagConstraintsCancelBtn = new GridBagConstraints();
			gridBagConstraintsCancelBtn.gridx = 3;
			gridBagConstraintsCancelBtn.gridy = 0;

			jButtonCancel = getJButtonCancel();
			jButtonCancel.setVisible(isShowCancel);

			jPanelButtonArea = new JPanel();
			jPanelButtonArea.setLayout(new GridBagLayout());
			jPanelButtonArea.add(getJButtonOK(), gridBagConstraintsOkBtn);
			jPanelButtonArea.add(jButtonCancel, gridBagConstraintsCancelBtn);
		}
		return jPanelButtonArea;
	}

	/**
	 * This method initializes jButtonOK
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new ExtendedButton();
			jButtonOK.setText("OK(Y)");
			jButtonOK.setActionCommand("�I��");
			jButtonOK.addActionListener(this);
			jButtonOK.setMnemonic(KeyEvent.VK_Y);
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new ExtendedButton();
			jButtonCancel.setText("�L�����Z��[C]");
			jButtonCancel.addActionListener(this);
			jButtonCancel.setMnemonic(KeyEvent.VK_C);
		}
		return jButtonCancel;
	}

	// add s.inoue 2009/12/24
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_C:
//			ReturnValue = RETURN_VALUE.CANCEL;
//			// ���[�_���_�C�A���O�̐�������B
//			setVisible(false);
//			break;
		case KeyEvent.VK_Y:
			if (setKensaJissiDate(jTextField_KenshinDate.getText())) {
				ReturnKenshinDate = jTextField_KenshinDate.getText();
			}else{
				ReturnKenshinDate = String.valueOf(FiscalYearUtil.getSystemDate(""));
			}
			ReturnValue = RETURN_VALUE.YES;
			setVisible(false);
			break;
		}
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
		return ReturnValue;
	}

	/**
	 * �߂�l���擾����
	 * @return �߂�l
	 */
	public String getKenshinDate() {
		return ReturnKenshinDate;
	}

	/**
	 * �߂�l���i�[
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {
			// add s.inoue 2009/10/04
			// 1.���{��������ȏꍇ�A���̂܂ܓ��͕[�ֈ�
			// 2.���{�����s���ȏꍇ�A�G���[���b�Z�[�W��\��
			// 3.���{�����w��Ȃ��̏ꍇ�AT_TOKUTEI(���f���{��)��\��
			if (setKensaJissiDate(jTextField_KenshinDate.getText())) {
				// del s.inoue 2009/10/16
				// TOKUTEI-���f���{���X�V
				// updateTokutesiKenshinDate();
				// SONOTA-���f���{���X�V
				// updateSonotaKenshinDate();
				ReturnKenshinDate = jTextField_KenshinDate.getText();
			}else{
				ReturnKenshinDate = String.valueOf(FiscalYearUtil.getSystemDate(""));
			}

			// edit s.inoue 2009/12/06
			// ReturnValue = RETURN_VALUE.OK;
			ReturnValue = RETURN_VALUE.YES;
		}
		else if(e.getSource() == jButtonCancel) {
			ReturnValue = RETURN_VALUE.CANCEL;
		}
		// ���[�_���_�C�A���O�̐�������B
		setVisible(false);
	}

	// del s.inoue 2009/10/19
//	// ���f���{���X�V
//	private void updateTokutesiKenshinDate(){
//		try{
//			int inKenshinDate = 0;
//			Long curuketukeID = 0L;
//			int kenshinDate = 0;
//
//			if (!jTextField_KenshinDate.getText().equals("")) {
//				inKenshinDate = Integer.parseInt(jTextField_KenshinDate.getText());
//
//				/* T_KENSAKEKA_TOKUTEI Dao ���쐬����B */
//				JApplication.kikanDatabase.Transaction();
//
//				tTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new TKensakekaTokutei());
//				tSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new TKensakekaSonota());
//
//				for (int i = 0; i < selectedRow.size(); i++) {
//					Hashtable<String, String> resultItem = selectedRow.get(i);
//
//					curuketukeID = Long.parseLong(resultItem.get("UKETUKE_ID"));
//					kenshinDate = Integer.parseInt(resultItem.get("KENSA_NENGAPI"));
//
//					tTokuteiDao.updateKenshinDate(inKenshinDate,curuketukeID,kenshinDate);
//
//					/* T_KENSAKEKA_SONOTA Dao ���쐬����B */
//					tSonotaDao.updatekey(inKenshinDate, curuketukeID, kenshinDate);
//				}
//
//				JApplication.kikanDatabase.Commit();
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//		}
//	}
	// add s.inoue 2009/10/04
	// ���f���{���X�V
	private void updateSonotaKenshinDate(){
		int inputkenshinDate = 0;
		Long curuketukeID = 0L;
		int kenshinDate = 0;

		try{
//			if (this.params != null) {
//				inputkenshinDate = Integer.parseInt(this.jTextField_KenshinDate.getText());
//				curuketukeID = Long.parseLong(this.params[0]);
//				kenshinDate = Integer.parseInt(this.params[1]);
//			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
	}

	// add s.inoue 2009/10/04
	/**
	 * @param kensaJissiDate the kensaJissiDate to set
	 */
	public boolean setKensaJissiDate(String kensaJissiDate) {
		boolean retflg = false;
		String kenshindate= JValidate.validateDate(kensaJissiDate,true,true);
		if( kenshindate != null ){
			if(kenshindate.equals("")){
				retflg = false;
			}else{
				this.jTextField_KenshinDate.setText(kenshindate);
				retflg = true;
			}
		}
		return retflg;
	}

	/**
	 * �I�𒆃Z���̃e�L�X�g���e�L�X�g�G���A/���X�g�{�b�N�X�ɃZ�b�g����
	 * @param text �I�𒆃Z���e�L�X�g
	 */
	public void setText(String text) {
		// edit s.inoue 2009/10/04
		// jLabelMessage.setText(text);
		jTextField_KenshinDate.setText(text);
	}


	public void setMessageTitle(String messageTitle) {
		jLabelTitle.setText(messageTitle);
	}

	/**
	 * �L�����Z���{�^���\��/��\���̐ݒ�
	 * @param isShowCancel
	 */
	public void setShowCancelButton(boolean isShowCancel) {
		this.isShowCancel = isShowCancel;
	}

// del s.inoue 2009/10/04
//	/**
//	 * This method initializes jPanel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel() {
//		if (jPanel == null) {
//			CardLayout cardLayout1 = new CardLayout();
//			cardLayout1.setHgap(5);
//			jPanel = new JPanel();
//			jPanel.setLayout(cardLayout1);
//			jPanel.add(getJPanel1(), getJPanel1().getName());
//		}
//		return jPanel;
//	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
// edit s.inoue 2009/10/04
//			jLabelMessage = new ExtendedLabel();
//			jLabelMessage.setText("�e�X�g���b�Z�[�W");
//			jLabelMessage.setHorizontalAlignment(SwingConstants.CENTER);
//			jLabelMessage.setName("jLabelMessage");
//			jLabelMessage.setFont(defaultFont);
//			CardLayout cardLayout2 = new CardLayout();
//			cardLayout2.setHgap(5);
//			jPanel1 = new JPanel();
//			jPanel1.setBackground(Color.white);
//			jPanel1.setLayout(cardLayout2);
//			jPanel1.setName("jPanel1");
//			jPanel1.add(jLabelMessage, jLabelMessage.getName());
//			jTextField_KenshinDate = new ExtendedTextField();
//			jTextField_KenshinDate.setText("");
//			jTextField_KenshinDate.setHorizontalAlignment(SwingConstants.CENTER);
//			jTextField_KenshinDate.setName("jTextFieldMessage");
//			jTextField_KenshinDate.setFont(defaultFont);

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridx = 0;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 0;


			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setPreferredSize(new Dimension(300,80));
			jPanel1.add(getJTextField_KenshinSelectDate(), gridBagConstraints1);
			jPanel1.add(getJLabel_KenshinSelectDate(), gridBagConstraints2);


// card�̏ꍇ
//			CardLayout cardLayout2 = new CardLayout();
//			cardLayout2.setHgap(5);
//			jPanel1 = new JPanel();
//			jPanel1.setBackground(Color.white);
//			jPanel1.setLayout(cardLayout2);
//			jPanel1.setName("jPanel1");
//			jPanel1.add(jTextField_KenshinDate, jTextField_KenshinDate.getName());

		}
		return jPanel1;
	}

	// add s.inoue 2009/10/04
	private ExtendedTextField getJTextField_KenshinSelectDate() {
		if (jTextField_KenshinDate == null) {
			jTextField_KenshinDate = new ExtendedTextField("",	8,	ImeMode.IME_OFF);
			jTextField_KenshinDate.setPreferredSize(new Dimension(130, 20));
			jTextField_KenshinDate.addActionListener(this);
			jTextField_KenshinDate.addKeyListener(this);
		}
		return jTextField_KenshinDate;
	}

	// add s.inoue 2009/10/06
	private ExtendedLabel getJLabel_KenshinSelectDate() {
		if (jLabel_KenshinDate == null) {
			jLabel_KenshinDate = new ExtendedLabel();
			// eidt s.inoue 2012/07/05
			jLabel_KenshinDate.setHtmlText("���f���{������͂��Ă��������B<br>�����͂������f���{���͓��͕[�ɔ��f����܂��B <br>�@���͂��Ȃ��ꍇ�A�����̌��f���{����\�����܂��B");
			jLabel_KenshinDate.setPreferredSize(new Dimension(300, 60));
		}
		return jLabel_KenshinDate;
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

	@Override
	public String getK_PNo() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public String getK_PName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public String getK_PNote() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	@Override
	public Vector getDataSelect() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
