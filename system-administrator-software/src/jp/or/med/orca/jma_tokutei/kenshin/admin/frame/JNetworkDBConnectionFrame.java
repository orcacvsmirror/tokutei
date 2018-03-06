package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

import org.apache.log4j.Logger;

/**
 * DB���ڑ���ʂ�View�N���X
 */
public class JNetworkDBConnectionFrame extends JFrame {
	private static final long serialVersionUID = 8106105722854070685L;
	private static Logger logger = Logger.getLogger(JNetworkDBConnectionFrame.class);

	//GridBagConstraints�ɐݒ肷��]��
	private static final Insets INSETS_BLANK_BOTTOM = new Insets(0, 0, 30, 10);	//���Ƀu�����N�@�{�@�E�Ƀu�����N1������
	private static final Insets INSETS_BLANK_BOTTOM_AND_LEFT = new Insets(0, 20, 30, 10);	//���Ƀu�����N�@�{�@�E�Ƀu�����N1�������@�{�@���Ƀu�����N2������
	private static final Insets INSETS_BLANK_NOTHING = new Insets(0, 0, 0, 0);	//�u�����N����
	
	//�e�L�X�g�t�B�[���h�̃T�C�Y
	private static final Dimension DEFAULT_SIZE = new Dimension(200, 20);
	
	//�r�W�l�X���W�b�N
	private JNetworkDBConnectionFrameCtrl networkDBConnectionFrameCtrl = null;
	//��ʏ��i�[Bean
	private JNetworkDBConnectionFrameData bean = null;
	
	
	//�߂�{�^��
	private ExtendedButton jButton_End = null;
	//�o�^�{�^��
	private ExtendedButton jButton_Register = null;
	//�ڑ��e�X�g�{�^��
	private ExtendedButton jButton_ConnectTestFDB = null;
	//�Q�ƃ{�^��
	private ExtendedButton jButton_Browse = null;


	//��ʑS��
	private JPanel jPanel_Content = null;
	//�{�^���\���G���A
	private JPanel jPanel_ButtonArea = null;
	//�ݒ��ʂ̕\���G���A
	private JPanel jPanel_InfoArea = null;
	//FDB�̕\���G���A
	private JPanel jPanel_info_fdb = null;

	//��ʂ̃^�C�g��
	private JPanel jPanel_TitleArea = null;
	
	//��ʂ̍��ځi�ڑ���DB���j
	private ExtendedLabel jLabel_fdbFolderPath = null;	//FDB�t�H���_�̃p�X���x��
	private ExtendedOpenTextControl jTextField_fdbFolderPath = null;	//FDB�t�H���_�̃p�X�e�L�X�g�{�b�N�X

	private ExtendedLabel jLabel_fdbIPAddress = null;	//FDB�̃z�X�g�� or IP�A�h���X���x��
	private ExtendedOpenTextControl jTextField_fdbIPAddress = null;	//FDB�̃z�X�g�� or IP�A�h���X�e�L�X�g�{�b�N�X
	private ExtendedLabel jLabel_fdbIPAddressInputExplanation = null;	//FDB�̃z�X�g�� or IP�A�h���X�e�L�X�g�{�b�N�X���͒l����

	private ExtendedLabel jLabel_fdbPortNumber = null;	//FDB�̃|�[�g�ԍ����x��
	private ExtendedOpenTextControl jTextField_fdbPortNumber = null;	//FDB�̃|�[�g�ԍ��e�L�X�g�{�b�N�X
	private ExtendedLabel jLabel_fdbPortNumberInputExplanation = null;	//FDB�̃|�[�g�ԍ��e�L�X�g�{�b�N�X���͒l����

	private ExtendedLabel jLabel_fdbUserName = null;	//FDB�̃��[�U�����x��
	private ExtendedOpenTextControl jTextField_fdbUserName = null;	//FDB�̃��[�U���e�L�X�g�{�b�N�X
	private ExtendedLabel jLabel_fdbUserNameInputExplanation = null;	//FDB�̃��[�U���e�L�X�g�{�b�N�X���͒l����

	private ExtendedLabel jLabel_fdbUserPass = null;	//FDB�̃p�X���[�h���x��
	private ExtendedOpenTextControl jTextField_fdbUserPass = null;	//FDB�̃p�X���[�h�e�L�X�g�{�b�N�X
	private ExtendedLabel jLabel_fdbUserPassInputExplanation = null;	//FDB�̃p�X���[�h�e�L�X�g�{�b�N�X���͒l����

	/**
	 * �R���X�g���N�^
	 */
	public JNetworkDBConnectionFrame() {
		
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
		jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
		
		//���ݒ�G���A��ݒ�
		jPanel_Content.add(getJPanel_InfoArea(), BorderLayout.CENTER);
		
		//�����t�H�[�J�X�̐ݒ�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e){
				jTextField_fdbFolderPath.requestFocus();
			}
		});
		
		//�r�W�l�X���W�b�N
		networkDBConnectionFrameCtrl = new JNetworkDBConnectionFrameCtrl();
		//�\�����̎擾
		bean = networkDBConnectionFrameCtrl.getFrameData();
		
		//�\���G���A�̃f�[�^��ݒ�
		setData();
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
			
			jPanel_ButtonArea = new JPanel();
			
			//���C�A�E�g
			GridBagLayout layout = new GridBagLayout();
			jPanel_ButtonArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//�\���ʒu
			int gridX = 0;	//x���W
			int gridY = 0;	//y���W
			
			//�^�C�g��
			jPanel_TitleArea = getJPanel_TitleArea();
			gridBagConstraints.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints.gridwidth = 2;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jPanel_TitleArea);
			jPanel_ButtonArea.add(jPanel_TitleArea);

			gridX = 0;	//�����W��������
			gridY++;	//�c���W������
			gridBagConstraints.gridwidth = 1;	//�߂�
			
			//�߂�{�^��
			jButton_End = getJButton_End();
			gridBagConstraints.insets = new Insets(0, 5, 0, 5);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_End);
			jPanel_ButtonArea.add(jButton_End);

			//�o�^�{�^��
			jButton_Register = getJButton_Register();
			gridBagConstraints.weightx = 1.0d;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_Register);
			jPanel_ButtonArea.add(jButton_Register);
		}
		return jPanel_ButtonArea;
	}
	
	/**
	 * �o�^�{�^��
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Register= new ExtendedButton("Save", "�o�^(S)", "�o�^(ALT+S)", KeyEvent.VK_S, icon);
			jButton_Register.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_Register.getText());
					
					//�m�F���b�Z�[�W
					RETURN_VALUE retVal = JErrorMessage.show("M10106", JNetworkDBConnectionFrame.this);
					if(retVal == RETURN_VALUE.YES) {
						
						//��ʏ���Bean�֐ݒ�
						setBean();
						
						//�o�^����
						boolean isRegisterOK = networkDBConnectionFrameCtrl.register(bean);
						if (isRegisterOK) {
							JErrorMessage.show("M10107", JNetworkDBConnectionFrame.this);
							dispose();
						}
					}
				}
			});
		}
		return jButton_Register;
	}
	
	/**
	 * �߂�{�^��
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton("Return", "�߂�(R)", "�߂�(ALT+R)", KeyEvent.VK_R, icon);
			jButton_End.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_End.getText());
					dispose();
				}
			});
		}
		return jButton_End;
	}
	
	/**
	 * �ڑ��e�X�g�{�^��
	 */
	private ExtendedButton getJButton_ConnectTestFDB() {
		if (jButton_ConnectTestFDB == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_ConnectTestFDB= new ExtendedButton("Test","�ڑ��e�X�g(T)", "�ڑ��e�X�g(ALT+T)", KeyEvent.VK_T, icon);
			jButton_ConnectTestFDB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_ConnectTestFDB.getText());
					
					//�ڑ���DB�̐ڑ��e�X�g���s
					boolean isConnectOK = networkDBConnectionFrameCtrl.connectTestFDB(
														jTextField_fdbUserName.getText(),
														jTextField_fdbUserPass.getText(),
														jTextField_fdbFolderPath.getText(),
														jTextField_fdbIPAddress.getText(),
														jTextField_fdbPortNumber.getText()
					);
					if (isConnectOK) {
						JErrorMessage.show("M10101", JNetworkDBConnectionFrame.this);
					} else {
						JErrorMessage.show("M10102", JNetworkDBConnectionFrame.this);
					}
				}
			});
		}
		return jButton_ConnectTestFDB;
	}
	
	/**
	 * �Q�ƃ{�^��
	 */
	private ExtendedButton getJButton_Browse() {
		if (jButton_Browse == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_InputJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Browse= new ExtendedButton("Reference","�Q��(O)", "�Q��(ALT+O)", KeyEvent.VK_O, icon);
			jButton_Browse.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_Browse.getText());

					//���݂̉�ʏ���Bean�֐ݒ�
					setBean();

					JFileChooser fileChooser = new JFileChooser(bean.getFdbFolderPath());
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.setDialogTitle("DB�t�H���_���w�肵�Ă�������");
					
					int selected = fileChooser.showOpenDialog(JNetworkDBConnectionFrame.this);
					if (selected == JFileChooser.APPROVE_OPTION) {
						
						//�I�������l�Ńt�H���_�p�X���㏑��
						bean.setFdbFolderPath(fileChooser.getSelectedFile().getAbsolutePath());
						
						//��ʂ̍ĕ\��
						setData();
					}
				}
			});
		}
		return jButton_Browse;
	}
	
	
	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_TitleArea() {
		if (jPanel_TitleArea == null) {
			
			jPanel_TitleArea = new JPanel();
			
			//���C�A�E�g
			GridBagLayout layout = new GridBagLayout();
			jPanel_TitleArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//�\���ʒu
			int gridX = 0;	//x���W
			int gridY = 0;	//y���W
			
			//�^�C�g��
			TitleLabel jLabel_Title = new TitleLabel("admin.network-db-connection-edit.frame.title", "admin.network-db-connection-edit.frame.guidance");
			jLabel_Title.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 14));
			gridBagConstraints.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints.weightx = 1.0d;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_Title);
			jPanel_TitleArea.add(jLabel_Title);
		}
		return jPanel_TitleArea;
	}
	
	/**
	 * ���ݒ���
	 * 
	 * @return
	 */
	private JPanel getJPanel_InfoArea() {
		if (jPanel_InfoArea == null) {
		
			jPanel_InfoArea = new JPanel();

			GridBagLayout layout = new GridBagLayout();
			jPanel_InfoArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;	//�\���ʒu�͍���
			gridBagConstraints.weighty = 1.0d;	//��ς߂ŕ\��
			gridBagConstraints.weightx = 1.0d;	//���ς߂ŕ\��
			
			//�\���ʒu
			int gridX = 0;	//x���W
			int gridY = 0;	//y���W
			
			jPanel_info_fdb = getJPanel_Info_fdb();
			gridBagConstraints.insets = new Insets(5, 10, 0, 0);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jPanel_info_fdb);
			jPanel_InfoArea.add(jPanel_info_fdb);
		}
		return jPanel_InfoArea;
	}
	
	private JPanel getJPanel_Info_fdb() {
		if (jPanel_info_fdb == null) {
			
			jPanel_info_fdb = new JPanel();

			//�ݒ��ʓ��̈�ԊO�̘g��
			jPanel_info_fdb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			
			//���C�A�E�g
			GridBagLayout layout = new GridBagLayout();
			jPanel_info_fdb.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//�\���ʒu
			int gridX = 0;	//x���W
			int gridY = 0;	//y���W
			
			//�^�C�g��
			ExtendedLabel jLabel_fdbTitle = new ExtendedLabel();
			jLabel_fdbTitle.setOpaque(true);
			jLabel_fdbTitle.setText("�ڑ���DB���");
			Font font = jLabel_fdbTitle.getFont();
			Font font1 = new Font(font.getFontName(), 1, font.getSize());
			jLabel_fdbTitle.setFont(font1);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbTitle);
			jPanel_info_fdb.add(jLabel_fdbTitle);

			gridX = 0;	//�����W��������
			gridY++;	//�c���W������
			
			//FDB�̃C���t�H���[�V�����̍s
			ExtendedLabel jLabel_fdbInfo = new ExtendedLabel();
			jLabel_fdbInfo.setText("���S�Ă̌��f�@�ւœ����l���g�p���܂�");
			gridBagConstraints.gridwidth = 3;	//��3�Z�����g�p
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//���u�����N
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbInfo);
			jPanel_info_fdb.add(jLabel_fdbInfo);

			gridX = 0;	//�����W��������
			gridY++;	//�c���W������
			gridBagConstraints.gridwidth = 1;	//���ɖ߂�
			
			//FDB�t�H���_�̃p�X�̍s
			jLabel_fdbFolderPath = new ExtendedLabel();
			jLabel_fdbFolderPath.setText("FDB�t�H���_�̃p�X");
			jLabel_fdbFolderPath.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2�������C���f���g
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbFolderPath);
			jPanel_info_fdb.add(jLabel_fdbFolderPath);
			
			jTextField_fdbFolderPath = new ExtendedOpenTextControl();
			jTextField_fdbFolderPath.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//�߂�
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbFolderPath);
			jPanel_info_fdb.add(jTextField_fdbFolderPath);

			jButton_Browse = getJButton_Browse();
			gridBagConstraints.insets = new Insets(-30, 0, 0, 0);	//�Q�ƃ{�^����^�񒆂ɕ\��
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_Browse);
			jPanel_info_fdb.add(jButton_Browse);

			gridX = 0;	//�����W��������
			gridY++;	//�c���W������

			//�z�X�g�� or IP�A�h���X�̍s
			jLabel_fdbIPAddress = new ExtendedLabel();
			jLabel_fdbIPAddress.setText("FDB�̃z�X�g�� or IP�A�h���X");
			jLabel_fdbIPAddress.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2�������C���f���g
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbIPAddress);
			jPanel_info_fdb.add(jLabel_fdbIPAddress);
			
			jTextField_fdbIPAddress = new ExtendedOpenTextControl();
			jTextField_fdbIPAddress.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//�߂�
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbIPAddress);
			jPanel_info_fdb.add(jTextField_fdbIPAddress);

			jLabel_fdbIPAddressInputExplanation = new ExtendedLabel();
			jLabel_fdbIPAddressInputExplanation.setText("�iIP�̏ꍇIPv4�`���j");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbIPAddressInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbIPAddressInputExplanation);

			gridX = 0;	//�����W��������
			gridY++;	//�c���W������

			//FDB�̃|�[�g�ԍ��̍s
			jLabel_fdbPortNumber = new ExtendedLabel();
			jLabel_fdbPortNumber.setText("FDB�̃|�[�g�ԍ�");
			jLabel_fdbPortNumber.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2�������C���f���g
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbPortNumber);
			jPanel_info_fdb.add(jLabel_fdbPortNumber);
			
			jTextField_fdbPortNumber = new ExtendedOpenTextControl();
			jTextField_fdbPortNumber.setPreferredSize(DEFAULT_SIZE);
			jTextField_fdbPortNumber.setMaxCharacters(5);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//�߂�
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbPortNumber);
			jPanel_info_fdb.add(jTextField_fdbPortNumber);

			jLabel_fdbPortNumberInputExplanation = new ExtendedLabel();
			jLabel_fdbPortNumberInputExplanation.setText("�i���p����5���ȉ��j");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbPortNumberInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbPortNumberInputExplanation);

			gridX = 0;	//�����W��������
			gridY++;	//�c���W������
			
			//FDB�̃��[�U���̍s
			jLabel_fdbUserName = new ExtendedLabel();
			jLabel_fdbUserName.setText("FDB�̃��[�U��");
			jLabel_fdbUserName.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2�������C���f���g
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserName);
			jPanel_info_fdb.add(jLabel_fdbUserName);
			
			jTextField_fdbUserName = new ExtendedOpenTextControl();
			jTextField_fdbUserName.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//�߂�
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbUserName);
			jPanel_info_fdb.add(jTextField_fdbUserName);
			
			jLabel_fdbUserNameInputExplanation = new ExtendedLabel();
			jLabel_fdbUserNameInputExplanation.setText("�i���p�p�����̂݁j");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserNameInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbUserNameInputExplanation);
			
			gridX = 0;	//�����W��������
			gridY++;	//�c���W������

			//FDB�̃p�X���[�h�̍s
			jLabel_fdbUserPass = new ExtendedLabel();
			jLabel_fdbUserPass.setText("FDB�̃p�X���[�h");
			jLabel_fdbUserPass.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2�������C���f���g
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserPass);
			jPanel_info_fdb.add(jLabel_fdbUserPass);
			
			jTextField_fdbUserPass = new ExtendedOpenTextControl();
			jTextField_fdbUserPass.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//�߂�
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbUserPass);
			jPanel_info_fdb.add(jTextField_fdbUserPass);

			jLabel_fdbUserPassInputExplanation = new ExtendedLabel();
			jLabel_fdbUserPassInputExplanation.setText("�i���p�p�����̂݁j");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserPassInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbUserPassInputExplanation);
			
			gridX = 0;	//�����W��������
			gridY++;	//�c���W������
			gridBagConstraints.insets = INSETS_BLANK_NOTHING;	//�u�����N�̉���

			//�ڑ��e�X�g�{�^���̍s
			gridX++;
			gridX++;
			jButton_ConnectTestFDB = getJButton_ConnectTestFDB();
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_ConnectTestFDB);
			jPanel_info_fdb.add(jButton_ConnectTestFDB);
		}
		return jPanel_info_fdb;
	}
	
	
	/**
	 * GridBagLayout�̊ȈՐݒ�ix���W��y���W��++���ēn���΁A��ʏ�̕\���ʂ�ɐݒ�ł���j
	 * 
	 * @param layout
	 * @param gridBagConstraints
	 * @param x
	 * @param y
	 * @param component
	 */
	private void setGridBagConstraints(GridBagLayout layout, GridBagConstraints gridBagConstraints, int x, int y, Component component) {
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		layout.setConstraints(component, gridBagConstraints);
	}
	

	/**
	 * ��ʂɐݒ肷������擾���A�l��ݒ肷��
	 */
	private void setData() {
		
		jTextField_fdbUserName.setText(bean.getFdbUserName());
		jTextField_fdbUserPass.setText(bean.getFdbPass());
		jTextField_fdbFolderPath.setText(bean.getFdbFolderPath());
		jTextField_fdbIPAddress.setText(bean.getFdbIPAddress());
		jTextField_fdbPortNumber.setText(bean.getFdbPortNumber());
	}

	/**
	 * ��ʏ���Bean�֐ݒ肷��
	 */
	private void setBean() {
		
		bean.setFdbUserName(jTextField_fdbUserName.getText());
		bean.setFdbPass(jTextField_fdbUserPass.getText());
		bean.setFdbFolderPath(jTextField_fdbFolderPath.getText());
		bean.setFdbIPAddress(jTextField_fdbIPAddress.getText());
		bean.setFdbPortNumber(jTextField_fdbPortNumber.getText());
	}
}
