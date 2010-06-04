package jp.or.med.orca.jma_tokutei.common.qr;

import java .awt  .*;
import javax.swing.*;
import java .awt  .event.*;

/**
 *  JQRreader
 *
 *    �p�q�R�[�h������Ǎ��N���X
 */
public class JQRReader extends JFrame implements ActionListener
{
	/**
	 *  �ő區�ڐ�
	 */
	final static int MAX_ITEMS = 42;

	/**
	 *  �R���X�g���N�^
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JQRReader()
	{
		super("QRCode Reader");

		JTextArea textarea = new JTextArea("");
		JButton   button   = new JButton  ("�捞");

		// ���̐ݒ�
		textarea.setFont( new Font("Dialog", Font.PLAIN, 12) );
		button  .setFont( new Font("Dialog", Font.PLAIN, 12) );

		// UI �ݒ�
		textarea.setLineWrap      ( true );
		button  .addActionListener( this );
		
		// UI �ǉ�
		getContentPane().setLayout( new BorderLayout() );

		add( textarea, BorderLayout.CENTER );
		add( button,   BorderLayout.SOUTH  );

		/* Modified 2008/04/21 �ጎ  */
		/* --------------------------------------------------- */
//		// �t���[���쐬
//		setSize( 384, 256 );
//
//		setVisible( true );
//
//		setLocationRelativeTo( null );
//
//		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		/* --------------------------------------------------- */
		// �t���[���쐬
		setSize( 384, 256 );
		setLocationRelativeTo( null );
		setLocationRelativeTo( null );
		setVisible( true );
		/* --------------------------------------------------- */
	}

	/**
	 *  �A�N�V�����C�x���g
	 *
	 *    @param  �C�x���g
	 *
	 *    @return none
	 */
	public void actionPerformed( ActionEvent event )
	{
		dispose();
	}

	/**
	 *  ��f�����[���ڎ擾
	 *
	 *    @param  ������
	 *
	 *    @return ���ڔz��<br>���ڔz��ɂ͎�f���p�q�R�[�h���^���ڂ��擪���珇�ɓ����Ă���.
	 */
	public static String[] getList( String strInput ) throws Exception
	{
		int iBufAddress = 0;

		// ���ڃf�[�^��
		int[] aryItemsLen = { 9, 20, 1, 7, 8, 11, 8, 8, 1, 1, 1, 6, 1, 6, 1, 6, 1, 6, 6, 1, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8 };

		// ���ڃf�[�^�z��
		String[] aryItems = new String[ MAX_ITEMS ];

		// �Œ蒷�E�ϒ��ŕ���
		String[] arySplit = strInput.split(",");

		if( arySplit == null && arySplit.length != 7 )
		{
			throw new Exception();
		}

		/**
		 *  ���� 01 �` ���� 36�@��f�ҏ��E�ی��ҏ��E��f�����
		 */
		for( int i=0; i<aryItemsLen.length; ++i )
		{
			StringBuffer strbuf = new StringBuffer();

			for( int j=0; j<aryItemsLen[ i ]; ++j )
			{
				strbuf.append( arySplit[ 0 ].charAt( iBufAddress ++ ) );
			}

			aryItems[ i ] = strbuf.toString();
		}

		/**
		 *  ���� 37 �` ���� 39�@�ی��ҏ��
		 */
		aryItems[ 36 ] = arySplit[ 1 ];
		aryItems[ 37 ] = arySplit[ 2 ];
		aryItems[ 38 ] = arySplit[ 3 ];

		/**
		 *  ���� 40 �` ���� 42�@��f�����
		 */
		aryItems[ 39 ] = arySplit[ 4 ];
		aryItems[ 40 ] = arySplit[ 5 ];
		aryItems[ 41 ] = arySplit[ 6 ];

		return aryItems;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

