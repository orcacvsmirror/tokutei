package jp.or.med.orca.jma_tokutei.common.execlocker;

import java .io   .*;
import java .awt  .*;
import java .util .*;
import java.util.regex.Pattern;
import java .lang .*;
import javax.swing.*;
import java .awt  .event.*;
import javax.swing.table.*;
import javax.swing.event.*;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

// ----------------------------------------------------------------------------
/**
	class::name	JExecUnlocker

	class::expl	�N�����b�N�����N���X
*/
// ----------------------------------------------------------------------------
public class JExecUnlocker extends JFrame implements ActionListener
{
	private JLabel  m_guiLabel  = null;

	private JButton m_guiButton = null;

	/* Added 2008/03/17 �ጎ  */
	/* --------------------------------------------------- */
	/* �V�X�e���Ǘ��җp�\�t�g�E�F�A�p���b�N�t�@�C���̑��݂������t���O */
	private static boolean existsAdminLockFile = false;
	/* ���茒�f�\�t�g�E�F�A�p���b�N�t�@�C���̑��݂������t���O */
	private static boolean existsKikanLockFile = false;
	/* --------------------------------------------------- */

	/*
	 *  ���C���֐�
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public static void main( String[] args ){
		/* Modified 2008/03/15 �ጎ  */
		/* --------------------------------------------------- */
//		File lockFile = new File( JExecLockerConfig.LOCKER_FILENAME );
//
//		if( !lockFile.exists() )
//		{
//			JOptionPane.showMessageDialog( null, "��d�N�����b�N����Ă��܂���B", "�m�F", JOptionPane.INFORMATION_MESSAGE );
//
//			System.exit( -1 );
//		}
//
//		new JExecUnlocker( "��d�N�����b�N�����\�t�g�E�F�A" );
		/* --------------------------------------------------- */
		existsAdminLockFile = false;
		existsKikanLockFile = false;
		// add s.inoue 2012/06/06
		boolean existsflg = false;

		File file = new File(".");
		File[] files = file.listFiles();

		if(files != null){
			File adminLockFile = new File( JExecLockerConfig.ADMIN_LOCK_FILENAME );

			/* �V�X�e���Ǘ��җp�\�t�g�E�F�A */
			if (! adminLockFile.exists()) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(ViewSettings.getUsingValueString("admin.application.name"));
				buffer.append("�͓�d�N�����b�N����Ă��܂���B");
// eidt s.inoue 2012/06/06
//				JOptionPane.showMessageDialog(
//						null,
//						buffer.toString(),
//						"�m�F",
//						JOptionPane.INFORMATION_MESSAGE
//					);
			}
			else {
				existsAdminLockFile = true;
				existsflg = true;
			}

			/* ���茒�f�V�X�e���\�t�g�E�F�A�p���b�N�t�@�C������������ */
			for(int i = 0 ; files.length > i ; i++)	{
				String filename = files[i].getName();

				if(Pattern.matches(JExecLockerConfig.KIKAN_LOCK_FILENAME_PATTERN, filename)){
					existsKikanLockFile = true;
				}
			}

			if (! existsKikanLockFile) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(ViewSettings.getUsingValueString("tokutei.application.name"));
				buffer.append("�͓�d�N�����b�N����Ă��܂���B");
// eidt s.inoue 2012/06/06
//				JOptionPane.showMessageDialog(
//						null,
//						buffer.toString(),
//						"�m�F",
//						JOptionPane.INFORMATION_MESSAGE
//					);
			}else{
				existsflg = true;
			}
			// add s.inoue 2012/06/06
			if (!existsflg)
					JOptionPane.showMessageDialog(
						null,
						"��d�N������Ă���A�v���P�[�V�����͂���܂���B\n���͂���܂���B",
						"�m�F",
						JOptionPane.INFORMATION_MESSAGE
					);

		}

		/* ���b�N�t�@�C�����S�����݂��Ȃ��ꍇ�́A�I������B */
		if( ! existsAdminLockFile && ! existsKikanLockFile ){
			System.exit( -1 );
		}

		new JExecUnlocker( "��d�N�����b�N�����\�t�g�E�F�A" );
		/* --------------------------------------------------- */
	}

	/*
	 *  �R���X�g���N�^
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JExecUnlocker( String strTitle )
	{
		super( strTitle );

		// �t�h����
		m_guiLabel  = new JLabel ( "�{�^���������ƃ��b�N�������J�n����܂��B" );

		m_guiButton = new JButton( "���b�N�����J�n" );

		m_guiLabel .setFont( new Font("Dialog", Font.PLAIN, 12) );
		m_guiButton.setFont( new Font("Dialog", Font.PLAIN, 12) );

		// �t�h�ݒ�
		getContentPane().setLayout     ( new CardLayout( 5, 5 ) );
		JPanel panelScreen = new JPanel( new GridLayout( 2, 1 ) );

		panelScreen.add( m_guiLabel  );
		panelScreen.add( m_guiButton );

		getContentPane().add( "ScreenGap", panelScreen );

		m_guiButton.addActionListener( this );

		// �t���[������
		setLocationRelativeTo( null );

		setSize( 384, 256 );

		setVisible( true );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	/*
	 *  ���̓C�x���g
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public void actionPerformed( ActionEvent event )
	{
		JButton button = (JButton)(event.getSource());

		if( button == m_guiButton )
		{
			/* Modified 2008/03/17 �ጎ  */
			/* --------------------------------------------------- */
//			File lockFile = new File( JExecLockerConfig.LOCKER_FILENAME );
//
//			if( !lockFile.delete() )
//			{
//				JOptionPane.showMessageDialog( this, "��d�N�����b�N�̉����Ɏ��s���܂����B", "�m�F", JOptionPane.INFORMATION_MESSAGE );
//
//				System.exit( -1 );
//			}
//
//			JOptionPane.showMessageDialog( this, "��d�N�����b�N�̉����ɐ������܂����B", "�m�F", JOptionPane.INFORMATION_MESSAGE );
//
//			System.exit( -1 );
			/* --------------------------------------------------- */

			boolean successDeleteAdminLockFile = false;
			boolean successDeleteKikanLockFile = false;

			/* �V�X�e���Ǘ��җp�\�t�g�E�F�A�p���b�N�t�@�C�����폜����B */
			if (existsAdminLockFile) {
				File adminLockFile = new File( JExecLockerConfig.ADMIN_LOCK_FILENAME );

				if (! adminLockFile.delete()) {

					StringBuffer buffer = new StringBuffer();
					buffer.append(ViewSettings.getUsingValueString("admin.application.name"));
					buffer.append("�̓�d�N�����b�N�̉����Ɏ��s���܂����B");

					JOptionPane.showMessageDialog(
							null,
							buffer.toString(),
							"�m�F",
							JOptionPane.INFORMATION_MESSAGE
						);

					System.exit(-1);
				}
				else {
					successDeleteAdminLockFile = true;
				}
			}

			/* ���茒�f�\�t�g�E�F�A�p���b�N�t�@�C�����폜����B */
			if (existsKikanLockFile) {
				File file = new File(".");
				File[] files = file.listFiles();

				successDeleteKikanLockFile = true;
				for(int i = 0 ; files.length > i ; i++)	{

					String filename = files[i].getName();

					if(Pattern.matches(JExecLockerConfig.KIKAN_LOCK_FILENAME_PATTERN, filename)){
						if (! files[i].delete()) {

							StringBuffer buffer = new StringBuffer();
							buffer.append(ViewSettings.getUsingValueString("tokutei.application.name"));
							buffer.append("�̓�d�N�����b�N�̉����Ɏ��s���܂����B");

							successDeleteKikanLockFile = false;

							JOptionPane.showMessageDialog(
									null,
									buffer.toString(),
									"�m�F",
									JOptionPane.INFORMATION_MESSAGE
								);

							break;
						}
					}
				}

				if (! successDeleteKikanLockFile) {
					System.exit(-1);
				}
			}

			if (successDeleteAdminLockFile || successDeleteKikanLockFile) {
				JOptionPane.showMessageDialog( this, "��d�N�����b�N�̉����ɐ������܂����B", "�m�F", JOptionPane.INFORMATION_MESSAGE );

				System.exit( 0 );
			}
			/* --------------------------------------------------- */
		}
	}
}
