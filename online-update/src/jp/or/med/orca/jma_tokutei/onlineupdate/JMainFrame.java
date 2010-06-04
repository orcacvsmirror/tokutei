package jp.or.med.orca.jma_tokutei.onlineupdate;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import jp.or.med.orca.jma_tokutei.onlineupdate.task.*;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.XMLDocumentUtil;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.HttpConnecter;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.LastError;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsFile;

/**
 *  JMainFrame
 *
 *    �I�����C���A�b�v�f�[�g��{���
 *    Modified 2008/03/09 ���
 */
public class JMainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JLabel m_guiStatus = null;
	private static final String FILENAME_UPDATE_TASK = "updatetask.xml";
	private JProgressBar m_guiProgress = null;

	// logger�� object�����B
	// s.inoue 20080620
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JMainFrame.class);

	/**
	 *  ���C���֐�
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public static void main(String[] args) {
		/* Modified 2008/06/29 �ጎ  */
		/* --------------------------------------------------- */
//		new JMainFrame("���茒�f�A�b�v�f�[�g�c�[��");
		/* --------------------------------------------------- */
		new JMainFrame("�A�b�v�f�[�g�\�t�g�E�F�A");
		/* --------------------------------------------------- */
	}

	/**
	 *  �R���X�g���N�^
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JMainFrame(String strTitle) {
		super(strTitle);

		HttpConnecter.setProxyHost(PropertyUtil.getProperty("http.proxyHost"));
		HttpConnecter.setProxyPort(PropertyUtil.getProperty("http.proxyPort"));
		HttpConnecter.setNonProxyHosts(PropertyUtil
				.getProperty("http.nonProxyHosts"));
		//s.inoue 20080620
		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		// ���s�{�^��
		JButton update = new JButton("�A�b�v�f�[�g���s");
		update.setActionCommand("update");
		update.addActionListener(this);

		// �ݒ�{�^��
		JButton proxy = new JButton("�v���L�V�ݒ�");
		proxy.setActionCommand("proxy");
		proxy.addActionListener(this);


		// �I���{�^��
		JButton cancel = new JButton("�I��");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);

		// �i�s��
		m_guiStatus = new JLabel();
		m_guiProgress = new JProgressBar(0, 10);

		m_guiProgress.setStringPainted(true);
		m_guiProgress.setIndeterminate(false);

		// �����ݒ�
		update.setFont(new Font("Dialog", Font.PLAIN, 12));
		proxy.setFont(new Font("Dialog", Font.PLAIN, 12));
		cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		m_guiStatus.setFont(new Font("Dialog", Font.PLAIN, 12));

		// �p�l���쐬
		getContentPane().setLayout(new CardLayout(5, 5));
		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		panelButton.add(update);
		panelButton.add(proxy);
		panelButton.add(cancel);

		JPanel panelScreen = new JPanel(new GridLayout(3, 1));

		panelScreen.add(m_guiStatus);
		panelScreen.add(m_guiProgress);
		panelScreen.add(panelButton);

		getContentPane().add("ScreenGap", panelScreen);

		// �t���[���쐬
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(new Dimension(600, 150));

		setLocationRelativeTo(null);

		setVisible(true);

		// �N������̃G���[�擾
		if (LastError.getErrorMessage() != null) {
			m_guiStatus.setText("Exception : " + LastError.getErrorMessage());
		}
	}

	/**
	 *  �A�N�V�����C�x���g
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void actionPerformed(ActionEvent e) {
		try {
				if ("update".equals(e.getActionCommand())) {
					executeTaskXML();
				} else if ("proxy".equals(e.getActionCommand())) {
					new ProxySelect(this);
				} else if ("cancel".equals(e.getActionCommand())) {
					System.exit(0);
				}
			} catch (Exception err) {
				// s.inoue 20080620
				logger.warn(err.getMessage());
			}
	}

	/**
	 *  �A�b�v�f�[�g���s
	 *    @param  none
	 *    @return none
	 */
	public void executeTaskXML() {
		System.out.println("executeTaskXML():start");

		String strErrorMessage = null;
		XMLDocumentUtil xmlDoc =null;
		ArrayList<AbstractTask> tasks =null;

		try {

			// add start s.inoue 20090119 �o�b�N�A�b�v
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			JApplication.versionNumber = doc.getNodeValue("ModuleVersion", "no");
			JErrorMessage.load();

			RETURN_VALUE Ret = JErrorMessage.show("M10003", this);
			if (Ret == RETURN_VALUE.NO) {
				System.exit(0);
			}
			// add end s.inoue 20090119 �o�b�N�A�b�v

			boolean isUpdate = false;

			// add s.inoue 20080730
			JApplication.load();

			// FDB�ւ̐ڑ������K�v�B���ʂŎg�p���Ă���ׁA
			// �I�����C���A�b�v�f�[�g�����ł̓N���[�Y���ď������s���B
			// del 20090108
			// JApplication.systemDatabase.Shutdown();

			// �X�V��
			xmlDoc = new XMLDocumentUtil(
					PropertyUtil.getProperty("update.url"));

			/////////////////////////////////////////////////
			// updatetask.xml�̃R�s�[�������s��
			/////////////////////////////////////////////////
			String strURL = xmlDoc.getXmlFileName();
			HttpConnecter.getFile(strURL, FILENAME_UPDATE_TASK);

			// �X�V��
			m_guiProgress.setValue(0);

			tasks = xmlDoc.parseModuleTask();

			int taskCount = tasks.size();
			m_guiProgress.setMaximum(taskCount);

			for (int i = 0; i < taskCount; ++i) {
				AbstractTask task = tasks.get(i);

				if (task.runTask()) {
					// �A�b�v�f�[�g����
					isUpdate = true;
				}

				m_guiProgress.setValue(JApplication.PROGRESS_CNT + 1);JApplication.PROGRESS_CNT++;
				m_guiProgress.paintImmediately(m_guiProgress.getVisibleRect());
			}

			// FDB�ւ̐ڑ������K�v�B���ʂŎg�p���Ă���ׁA
			// �I�����C���A�b�v�f�[�g�����I����I�[�v������B
			// del 20090108
			// JApplication.systemDatabase = JConnection.ConnectSystemDatabase();

			m_guiStatus.setText(isUpdate ? "�A�b�v�f�[�g����" : "���ɍŐV�o�[�W�����ł�");

		} catch (Exception err) {
			err.printStackTrace();

			// �ŏI�G���[�擾
			strErrorMessage = LastError.getErrorMessage();

			logger.error(strErrorMessage);
			m_guiStatus.setText((strErrorMessage == null ? "�A�b�v�f�[�g�Ɏ��s���܂����B���O�t�@�C�����m�F���ĉ������B" : strErrorMessage));
		}

		System.out.println("executeTaskXML():end");
	}
}
