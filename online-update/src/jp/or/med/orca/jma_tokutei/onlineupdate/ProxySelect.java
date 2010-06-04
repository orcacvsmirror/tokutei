package jp.or.med.orca.jma_tokutei.onlineupdate;
import javax.swing.*;
import java .awt  .*;
import java .awt  .event.*;

import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;
import org.apache.log4j.Logger;

/**
 *  ProxySelect
 * 
 *   プロキシサーバ設定画面
 */
public class ProxySelect  extends JDialog implements ActionListener {
	
    private static final long serialVersionUID = 1L;
	
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(ProxySelect.class);
    
	JTextField m_guiServer = new JTextField( 25 );
	JTextField m_guiPort   = new JTextField( 5  );
	
	/**
	 *  コンストラクタ
	 *
	 *    @param  none
	 *
	 *    @return none
	 * @throws Exception 
	 */
	public ProxySelect( JFrame frame ) throws Exception
	{
		super( frame, true );
		
		try{
			
			setTitle("プロキシサーバ設定");
	
			// 実行ボタン
			JButton commit = new JButton("適用");
			commit.setActionCommand ("commit");
			commit.addActionListener(this);
	
			// 作成ボタン
			JButton cancel = new JButton("キャンセル");
			cancel.setActionCommand ("cancel");
			cancel.addActionListener(this);
			
			// フォント設定
			commit.setFont( new Font("Dialog", Font.PLAIN, 12) );
			cancel.setFont( new Font("Dialog", Font.PLAIN, 12) );
			
			// フレームパネル作成
			JPanel panelText1  = new JPanel( new FlowLayout(FlowLayout.LEFT ) );
			JPanel panelText2  = new JPanel( new FlowLayout(FlowLayout.LEFT ) );
			JPanel panelButton = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
			
			JLabel label1 = new JLabel("設定アドレス");
			
			panelText1.add( label1 );
			panelText1.add( m_guiServer );
			
			JLabel label2 = new JLabel("設定ポート　");
			
			panelText2.add( label2 );
			panelText2.add( m_guiPort   );
			
			label1.setFont( new Font("Dialog", Font.PLAIN, 12) );
			label2.setFont( new Font("Dialog", Font.PLAIN, 12) );
			
			panelButton.add( commit );
			panelButton.add( cancel );
			
			getContentPane().setLayout( new GridLayout(3, 1) );
			
			getContentPane().add( panelText1  );
			getContentPane().add( panelText2  );
			getContentPane().add( panelButton );
			
			m_guiServer.setText( PropertyUtil.getProperty("http.proxyHost") );
			m_guiPort  .setText( PropertyUtil.getProperty("http.proxyPort") );
			
			// フレーム作成
			setSize( new Dimension( 380, 150 ) );
			
			setLocationRelativeTo( null );
			
			setVisible           ( true );
		}catch(Exception err){
			logger.error("プロキシサーバ設定初期処理に失敗しました");
			throw err;
		}
	}
	
	/**
	 *  アクションイベント
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void actionPerformed( ActionEvent e )
	{
		if( "commit".equals( e.getActionCommand() ) )
		{
			PropertyUtil.setProperty( "http.proxyHost", m_guiServer.getText().trim() );
			PropertyUtil.setProperty( "http.proxyPort", m_guiPort  .getText().trim() );
			
			HttpConnecter.setProxyHost( m_guiServer.getText().trim() );
			HttpConnecter.setProxyPort( m_guiPort  .getText().trim() );
		}

		dispose();
	}
}
