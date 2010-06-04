package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class JKekkaRegisterKekkaMojiretsuInputDialog extends JKekkaRegisterAbstractInputDialog implements MouseListener{

	private static final long serialVersionUID = 1L;
	/**
	 * �_�C�A���O�^�C�g��������擾�p�L�[
	 */
	private final String TITLE_TEXT_KEY = "tokutei.kekkainput.frame.kekkainput.dialog.title";
	/**
	 * ���̓K�C�_���X������擾�p�L�[
	 */
	private final String GUIDANCE_TEXT_KEY = "tokutei.kekkainput.frame.kekkainput.dialog.guidance";

	private  ExtendedEditorPane jKekkaMojiretsuTextArea = null;

	private JFocusTraversalPolicy focusTraversalPolicy = null;

	/**
	 * �R���X�g���N�^
	 * @param owner ���f���ʃf�[�^���͉��
	 */
	public JKekkaRegisterKekkaMojiretsuInputDialog(Frame owner) {
		super(owner);
		String title = ViewSettings.getUsingValueString(TITLE_TEXT_KEY);
		String guidance = ViewSettings.getUsingValueString(GUIDANCE_TEXT_KEY);
		ExtendedEditorPane textArea = getJKekkaMojiretsuTextArea();
		initialize(title, guidance, textArea);
		textArea.grabFocus();

		// edit s.inoue 2009/10/23
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);

		this.focusTraversalPolicy.setDefaultComponent(this.jKekkaMojiretsuTextArea);
		this.focusTraversalPolicy.addComponent(this.jKekkaMojiretsuTextArea);
		this.focusTraversalPolicy.addComponent(this.jButtonOK);
		this.focusTraversalPolicy.addComponent(this.jButtonCancel);

		/* Added 2008/03/14 �ጎ
		 * ����ԂɂȂ����Ƃ��ɁA�G�f�B�^�Ƀt�H�[�J�X���ړ�����B
		 * �����ԂɂȂ����Ƃ��AIME �� OFF �ɂ���B */
		/* --------------------------------------------------- */
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentShown(ComponentEvent arg0) {
				JKekkaRegisterKekkaMojiretsuInputDialog.this.jKekkaMojiretsuTextArea.grabFocus();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				JKekkaRegisterKekkaMojiretsuInputDialog.this.jKekkaMojiretsuTextArea
					.getInputContext().setCharacterSubsets(null);
			}
		});
		/* --------------------------------------------------- */
	}

	/**
	 * This method initializes jKekkaMojiretsuTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private ExtendedEditorPane getJKekkaMojiretsuTextArea() {
		if (jKekkaMojiretsuTextArea == null) {
			jKekkaMojiretsuTextArea = new ExtendedEditorPane(ImeMode.IME_ZENKAKU);
			jKekkaMojiretsuTextArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			// edit s.inoue 2009/11/11
			jKekkaMojiretsuTextArea.addMouseListener(this); // �}�E�X���X�i��o�^

		}
		return jKekkaMojiretsuTextArea;
	}

	/**
	 * �e�L�X�g�G���A�Ɍ���(������)���Z�b�g����
	 * @param kekkaMojiretsu ����(������)
	 */
	public void setText(String text) {
		jKekkaMojiretsuTextArea.setText(text);
	}

	/**
	 * �e�L�X�g�G���A������͂��ꂽ����(������)���擾����
	 * @return �e�L�X�g�G���A���͍� ����(������)
	 */
	public String getText() {
		return jKekkaMojiretsuTextArea.getText();
	}

	// add s.inoue 2009/11/11
	@Override
	public void mousePressed(MouseEvent e) {
		mousePopup(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}
	// add s.inoue 2009/11/11
	private void mousePopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			// �|�b�v�A�b�v���j���[��\������
			JComponent c = (JComponent)e.getSource();
			jKekkaMojiretsuTextArea.showPopup(c, e.getX(), e.getY());
			e.consume();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}
//@jve:decl-index=0:
//  @jve:decl-index=0:visual-constraint="10,10"