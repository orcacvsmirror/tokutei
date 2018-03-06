package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;


/**
 * ���f�@�ւ̈ꗗ�\���N���X
 * ��DB�ڑ���񃁃��e�i���X�ŁA�����̌��f�@�ւ������Z�A�g���Ă���ꍇ�A���̉�ʂňꗗ��\�����I��������
 */
public class JKikanListDialog extends JDialog {
	private static final long serialVersionUID = -550558357451615057L;

	//�e�[�u���̗�
	private static final String[] COLUMN_NAMES = {"���f�@�֔ԍ�", "���f�@�֖���", "����W�����Z�v�g�\�t�g�A�g"};

	//�I���{�^��
	private ExtendedButton jButton_select;

	//��ʑS��
	private JPanel jPanel_content = null;
	//�{�^���\���G���A
	private JPanel jPanel_buttonArea = null;
	//�ꗗ�\���G���A
	private JPanel jPanel_listArea = null;
	
	//�ꗗ�\���p�e�[�u��
	private JTable table = null;
	
	//�ꗗ�\���f�[�^
	private String[][] listData = null;
	
	//�I���������f�@�֔ԍ���ԋp����
	private String[] resultValues = null;
	public String[] getResultVal() {
		return resultValues;
	}
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param listData	�ꗗ�ɕ\�����錒�f�@�ւ̃f�[�^�i"���f�@�֔ԍ�","���f�@�֖���","����W�����Z�v�g�\�t�g�A�g"��2�����z��j
	 */
	public JKikanListDialog(String[][] listData) {
		
		//�ꉞ�`�F�b�N
		if ((listData == null) || (listData.length == 0)){
			this.listData = new String[0][0];
		} else {
			this.listData = listData;
		}
		
		//��ʏ����\������
		initialize();
	}
	

	/**
	 * ��ʂ̏����\��
	 */
	private void initialize() {
		
		//��ʃT�C�Y�̐ݒ�
		Dimension size = ViewSettings.getFrameCommonSize();
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setModal(true);
		
		//�\���ʒu�̐ݒ�
		this.setLocationRelativeTo(null);

		//�E�B���h�E�̘g�ɕ\������l�i�o�[�W������񓙁j��ݒ�
		String title = ViewSettings.getTokuteFrameTitleWithKikanInfomation();
		if (title == null || title.isEmpty()) {
			title  = ViewSettings.getAdminCommonTitleWithVersion();
		}
		this.setTitle(title);
		
		//��ʂ̃x�[�X��ݒ�
		setContentPane(getJPanel_Content());
		
		//�{�^���G���A��ݒ�
		jPanel_content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
		
		//�ꗗ�G���A��ݒ�
		jPanel_content.add(getJPanel_listArea(), BorderLayout.CENTER);
		
		
		//�\��
		this.setVisible(true);
	}
	
	/**
	 * This method initializes jPanel_Content
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Content() {
		if (jPanel_content == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);

			jPanel_content = new JPanel();
			jPanel_content.setLayout(borderLayout);
		}
		return jPanel_content;
	}
	
	/**
	 * This method initializes jPanel_ButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ButtonArea() {
		
		if (jPanel_buttonArea == null) {
			
			jPanel_buttonArea = new JPanel();
			
			//���C�A�E�g
			GridBagLayout layout = new GridBagLayout();
			jPanel_buttonArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//�\���ʒu
			int gridX = 0;	//x���W
			int gridY = 0;	//y���W
			
			//�I���{�^��
			jButton_select = getJButton_select();
			gridBagConstraints.weightx = 1.0d;
			gridBagConstraints.insets = new Insets(10, 10, 0, 0);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_select);
			jPanel_buttonArea.add(jButton_select);
			
		}
		return jPanel_buttonArea;
	}
	
	
	private ExtendedButton getJButton_select() {
		if (jButton_select == null) {
			ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Select);
			javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
			jButton_select = new ExtendedButton("Select", "�I��(S)", "�I��(ALT+S)", KeyEvent.VK_S, imageicon);
			
			//���X�g�̓��e�������ꍇ�A�����s�ɂ���
			if (listData.length == 0) {
				jButton_select.setEnabled(false);
			}
			
			jButton_select.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pushedBtnSelect();
				}
			});
		}
		return jButton_select;
	}
	
	/**
	 * �I���{�^����������
	 */
	private void pushedBtnSelect() {
//		System.out.println("�I���f�[�^:[" + listData[table.getSelectedRow()][0] + "][" + listData[table.getSelectedRow()][1] + "][" + listData[table.getSelectedRow()][2] + "]");
		resultValues = new String[3];
		resultValues[0] = listData[table.getSelectedRow()][0];
		resultValues[1] = listData[table.getSelectedRow()][1];
		resultValues[2] = listData[table.getSelectedRow()][2];
		dispose();
	}

	/**
	 * ���ݒ���
	 * 
	 * @return
	 */
	private JPanel getJPanel_listArea() {
		if (jPanel_listArea == null) {
		
			jPanel_listArea = new JPanel();
			
			//�\������ꗗ
			table = new JTable(listData, COLUMN_NAMES) {
				private static final long serialVersionUID = 5014173363615825539L;
				@Override
				public boolean isCellEditable(int row, int column) {
					//�Z�����̕ҏW�͕s��
					return false;
				}
			};
			
			//�_�u���N���b�N�ł�"�I��"�{�^���Ɠ��l�̓���
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if (e.getClickCount() == 2) {
//						System.out.println("�_�u���N���b�N");
						pushedBtnSelect();
					}
				}
			});
			
			//�����s�̑I���͕s��
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			//��ԏ�̍s��I����Ԃɂ���
			table.changeSelection(0, 0, false, false);

			//�s�̍���
			table.setRowHeight(20);
			
			//��̕�
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(280);	//���f�@�֔ԍ�
			columnModel.getColumn(1).setPreferredWidth(500);	//���f�@�֖���
			columnModel.getColumn(2).setPreferredWidth(190);	//����W�����Z�v�g�\�t�g�A�g
			
			//�񕝂̕ύX�͕s��
			table.getTableHeader().setResizingAllowed(false);
			
			//��̏��ԕύX�͕s��
			table.getTableHeader().setReorderingAllowed(false);
			
			//�X�N���[���L��
			JScrollPane jScrollPane = new JScrollPane(table);
			jScrollPane.setPreferredSize(new Dimension(970, 550));
			
			//���C�A�E�g
			GridBagLayout layout = new GridBagLayout();
			jPanel_listArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			
			//�\���ʒu
			int gridX = 0;	//x���W
			int gridY = 0;	//y���W
			
			gridBagConstraints.insets = new Insets(-5, 0, 0, 0);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jScrollPane);
			jPanel_listArea.add(jScrollPane);
		}
		return jPanel_listArea;
	}
	
	/**
	 * GridBagLayout�̊ȈՐݒ�ix���W��y���W��++���ēn���΁A��ʏ�̕\���ʂ�ɐݒ�ł���j
	 * 
	 * @param layout
	 * @param gridBagConstraints
	 * @param x
	 * @param y
	 * @param insets
	 */
	private void setGridBagConstraints(GridBagLayout layout, GridBagConstraints gridBagConstraints, int x, int y, Component component) {
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		layout.setConstraints(component, gridBagConstraints);
	}
}
