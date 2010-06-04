package jp.or.med.orca.jma_tokutei.common.frame;

import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * ���ʃ_�C�A���O�N���X
 *
 */
public class JSelectCommentInputDialog extends JSelectAbstractInputDialog implements ItemListener {

	private static final long serialVersionUID = 1L;
	/**
	 * �_�C�A���O�^�C�g��������擾�p�L�[
	 */
	private final String TITLE_TEXT_KEY = "tokutei.kekkainput.frame.commentinput.dialog.title";
	/**
	 * ���̓K�C�_���X������擾�p�L�[
	 */
	private final String GUIDANCE_TEXT_KEY = "tokutei.kekkainput.frame.commentinput.dialog.guidance";
	// edit s.inoue 2009/10/28
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	// �ȉ��b��I���u
	// �R�[�h�}�X�^�ɒ�`���ׂ�
	private final String[] SOUGOU_COMMENT_TEXT =
	{
		"�����͐���ł�",
		"�����Ɉُ킪�����܂�",
		"�A�����͐���ł�",
		"�A�����Ɉُ킪�F�߂��܂�",
		"���������͐���ł�",
		"���������Ɉُ킪�F�߂��܂�",
		"���������͐���ł�",
		"���������Ɉُ킪�F�߂��܂�",
		"�̋@�\�����͐���ł�",
		"�̋@�\�����Ɉُ킪�F�߂��܂�",
		"���񌒐f�̈�t�̔��f�́u�ُ�Ȃ��v�ł�",
		"���񌒐f�̈�t�̔��f�́u�v�ώ@�v�ł�",
		"���񌒐f�̈�t�̔��f�́u�v�w���v�ł�",
		"���񌒐f�̈�t�̔��f�́u���Ò��v�ł�",
		"���񌒐f�̈�t�̔��f�́u�v��Áv�ł�"
	};

	private JList jListComment = null;
	/**
	 * �R���X�g���N�^
	 * @param owner
	 */
	public JSelectCommentInputDialog(Frame owner) {
		super(owner);
		String title = ViewSettings.getUsingValueString(TITLE_TEXT_KEY);
		String guidance = ViewSettings.getUsingValueString(GUIDANCE_TEXT_KEY);

		// edit ver2 s.inoue 2009/08/14
		// JList listBox = getJListComment();
		// initialize(title, guidance, listBox);
		// listBox.grabFocus();

//		Checkbox ch[] = new Checkbox[2];
//		ch[0] = (Checkbox)add(new Checkbox("��f�������ԍ�"));
//		ch[1] = (Checkbox)add(new Checkbox("�����J�i"));
//		ch[0].addItemListener(this);
//		ch[1].addItemListener(this);
		initialize(title,guidance);

		// edit s.inoue 2009/10/28
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);

		this.focusTraversalPolicy.setDefaultComponent(this.jListComment);
		this.focusTraversalPolicy.addComponent(this.jListComment);
		this.focusTraversalPolicy.addComponent(this.jButtonOK);
		this.focusTraversalPolicy.addComponent(this.jButtonCancel);

		/* Added 2008/03/14 �ጎ ����ԂɂȂ����Ƃ��ɁA�G�f�B�^��
		 * �t�H�[�J�X���ړ�����悤�ɕύX�B */
		/* --------------------------------------------------- */
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentShown(ComponentEvent arg0) {
				// del s.inoue 2009/08/17
				// JSelectCommentInputDialog.this.jListComment.grabFocus();
			}
		});
		/* --------------------------------------------------- */
	}

	/**
	 * ���X�g�{�b�N�X����I�����ꂽ�e�L�X�g���擾����
	 */
	@Override
	public String getText() {
		return (String)jListComment.getSelectedValue();
	}

	/**
	 * �I�𒆃Z���̃e�L�X�g�ƈ�v���郊�X�g�{�b�N�X�̍��ڂ�
	 * �I����Ԃɐݒ肷��
	 * @param �I�𒆃Z���e�L�X�g
	 */
	@Override
	public void setText(String text) {
		if (text == null) return;

		for (int i = 0 ; i < SOUGOU_COMMENT_TEXT.length ; i++) {
			if (SOUGOU_COMMENT_TEXT[i].equals(text)) {
				jListComment.setSelectedIndex(i);
				return;
			}
		}
	}

	/**
	 * This method initializes jListComment
	 *
	 * @return javax.swing.JList
	 */
	private JList getJListComment() {
		if (jListComment == null) {
			jListComment = new JList(SOUGOU_COMMENT_TEXT);
			jListComment.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jListComment.setFont(ViewSettings.getCommonUserInputFont());
			jListComment.addKeyListener(this);
		}
		return jListComment;
	}

	/**
	 * ���X�g�{�b�N�X�L�[���X�i�R�[���o�b�N
	 * Enter�L�[��������OK�{�^�������Ɠ������������s����
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {

		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			ActionEvent event = new ActionEvent(jButtonOK, 0, "exit");
			actionPerformed(event);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public String getPrintQuery() {

		ArrayList<String> conditions = new ArrayList<String>();

		/* �N�x */
		if (jCheckBox_Nendo.isSelected()){
			conditions.add("GET_NENDO.NENDO ");
		}
		/* ��f�������ԍ� */
		if (jCheckBox_SeiriNo.isSelected()){
			conditions.add("KOJIN.JYUSHIN_SEIRI_NO");
		}
		/* �J�i���� */
		if (jCheckBox_KanaName.isSelected()){
			conditions.add("KOJIN.KANANAME");
		}
		/* ����(����) */
		if (jCheckBox_Name.isSelected()){
			conditions.add("KOJIN.NAME");
		}
		/* ���� */
		if (jCheckBox_Sex.isSelected()){
			conditions.add("KOJIN.SEX");
		}
		/* ���N���� */
		if (jCheckBox_BirthDay.isSelected()){
			conditions.add("KOJIN.BIRTHDAY");
		}
		/* �����N���� */
		if (jCheckBox_KenshinNengapi.isSelected()){
			conditions.add("TOKUTEI.KENSA_NENGAPI");
		}
		/* �ϊ����� */
		if (jCheckBox_HL7.isSelected()){
			conditions.add("TOKUTEI.HENKAN_NITIJI");
		}
		/* �ی��Ҕԍ��i�l�j */
		if (jCheckBox_HokenjaNo.isSelected()){
			conditions.add("KOJIN.HKNJANUM");
		}
		/* ��ی��ҏؓ��L�� */
		if (jCheckBox_HihokenjyaKigo.isSelected()){
			conditions.add("KOJIN.HIHOKENJYASYO_KIGOU");
		}
		/* ��ی��ҏؓ��ԍ� */
		if (jCheckBox_HihokenjyaNo.isSelected()){
			conditions.add("KOJIN.HIHOKENJYASYO_NO");
		}

		/* �P�����v */
		if (jCheckBox_Tanka.isSelected()){
			conditions.add("KESAI.TANKA_GOUKEI");
		}
		/* �������S���v */
		if (jCheckBox_Madoguti.isSelected()){
			conditions.add("KESAI.MADO_FUTAN_GOUKEI");
		}
		/* �������z���v */
		if (jCheckBox_Seikyu.isSelected()){
			conditions.add("KESAI.SEIKYU_KINGAKU");
		}

		String retValue = "";
		if (! conditions.isEmpty()) {
			StringBuffer buffer = new StringBuffer();

			for (Iterator<String> iter = conditions.iterator(); iter.hasNext();) {
				String condition = iter.next();
				buffer.append(condition);

				if (iter.hasNext()) {
					buffer.append(" , ");
				}
			}

			retValue = buffer.toString();
		}

		return retValue;
	}
}
