package jp.or.med.orca.jma_tokutei.common.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;

/**
	class::name JSimpleTable
	class::expl �e�[�u������N���X
*/
public class JSimpleTable extends JTable implements TableModel
{
	protected DefaultTableModel m_objTableModel;
	protected TableRowSorter<TableModel> sorter;

	// �����ݒ�
	protected final static int DEFAULT_COLUMEN_WIDTH  = 100;
	protected final static int DEFAULT_ROW_HEIGHT =  20;

	private int[] columnWidths = null;
	private int[] rowHeights = null;
	// add s.inoue 2009/11/12
	 String[] modelHeader = null;

	// �Z������
	protected Vector<JSimpleTableCellPosition>     m_vectCellEdit  = null;
	protected Vector<JSimpleTableCellRendererData> m_vectCellColor = null;

	protected Vector<JSimpleTableCellEditorData<ExtendedCheckBox>> m_vectCheckBox = null;
	protected Vector<JSimpleTableCellEditorData<JComboCellEditor>> m_vectComboBox = null;
	protected Vector<JSimpleTableCellEditorData<ExtendedRadioButton>> m_vectRadioButton = null;

	// add s.inoue 2010/01/13
	JRadioButton[] buttons;

	// add s.inoue 2009/10/22
	// �c�[���`�b�v���e | �����I�[�o�[�̎�
	public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {

		Component c = super.prepareRenderer(tcr, row, column);
		// edit s.inoue 2010/01/29
	    if (c instanceof JCheckBox || c instanceof JRadioButton ||
	    		c instanceof ExtendedCheckBox || c instanceof ExtendedRadioButton)
	    	return c;

	    if (c instanceof JCheckBox || c instanceof JRadioButton)
      		return c;
	    if(c instanceof JComponent) {
	       JComponent l = (JComponent)c;
	       Object o = getValueAt(row, column);
	       if (o == null)
	    	   return c;

	       Insets i = l.getInsets();
	       Rectangle rect = getCellRect(row, column, false);
	       rect.width -= i.left + i.right;
	       FontMetrics fm = l.getFontMetrics(l.getFont());
	       String str = o.toString();
	       int cellTextWidth = fm.stringWidth(str);
	       l.setToolTipText(cellTextWidth>rect.width?splitToopTipString(str):null);
	    }
	    return c;
	}

	// add s.inoue 2009/10/23
	private String splitToopTipString(String tooltip){
		String substr ="";
		String strTip ="";
		// edit s.inoue 2009/11/27
		final int split = 26;
		int start = 0;
		int end = split;

		if (tooltip.length() <= end)
			return tooltip;

		// edit s.inoue 2009/11/27
		// for��do 25��26 �]�菈���ǉ�
		// for (int i = 0; i < tooltip.length(); i++) {
		while(tooltip.length() > split ){
			// if (tooltip.length() < split) {
			//	strTip = strTip + tooltip;break;
			//}
			substr = tooltip.substring(0, split-1);
			strTip = strTip + substr + "<br>";
			tooltip = tooltip.substring(split-1);
			start= end;
			end = start + end;
		}
		if (tooltip.length() <= split -1){
			strTip += tooltip;
		}
		return "<html>" + strTip + "</html>";
	}

	/**
	 *  �J�X�^���G�f�B�^�Ǘ��N���X
	 */
	protected class JSimpleTableCellEditorData< T >
	{
		private T m_cellEditor;
		private Integer m_iColumn;

		/**
		 *  �R���X�g���N�^
		 *
		 *    @param  �ǉ��G�f�B�^
		 *    @param  �ǉ��ʒu
		 *
		 *    @return none
		 */
		public JSimpleTableCellEditorData( T cellEditor, int iColumn )
		{
			  m_iColumn = iColumn;
			  m_cellEditor = cellEditor;
		}

		/**
		 *  ��擾
		 *
		 *    @param  none
		 *    @return int
		 */
		public int getColumn()
		{
			return m_iColumn;
		}

		/**
		 *  �J�X�^���G�f�B�^
		 *
		 *    @param  none
		 *    @return T
		 */
		public T getCellEditor()
		{
			return m_cellEditor;
		}
	}

	/**
	 *  �J�X�^���Z�������_���N���X : DefaultCellRenderer
	 */
	protected class JSimpleTableCellRenderer extends DefaultTableCellRenderer
	{
		private Vector<JSimpleTableCellRendererData> m_vectCellList;

		/**
		 *  �R���X�g���N�^
		 *
		 *    @param  �ݒ胊�X�g
		 *    @return none
		 */
		public JSimpleTableCellRenderer( Vector<JSimpleTableCellRendererData> list )
		{
			super();

			setOpaque( true );
			setBorder( BorderFactory.createEmptyBorder( 0, 5, 0, 5 ) );

			m_vectCellList = list;
		}

		private DecimalFormat formater = new DecimalFormat("###,###.###");

		/**
		 *  @Override getTableCellRendererComponent
		 */
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col )
		{
			String displayText = null;
			if (value instanceof Number) {
				Number n = (Number) value;

				try {
					displayText = formater.format(n);
				} catch (Exception e) {
					// value��null�̏ꍇ�ANullPointerException�ŗ�����
					displayText = (value != null) ? value.toString() : "";
				}
			}
			if (displayText == null || displayText.isEmpty()) {
				// value��null�̏ꍇ�ANullPointerException�ŗ�����
				displayText = (value != null) ? value.toString() : "";
			}

			super.getTableCellRendererComponent( table, displayText, isSelected, hasFocus, row, col );

			/* �w�i�F���ݒ肳��Ă���ꍇ�A�I�����ɂ��A�w�肳�ꂽ�F��
			 * �g�p����悤�ɕύX�B */
				boolean isFound = false;

				for( int i=0; i<m_vectCellList.size(); ++i )
				{
					setForeground( table.getForeground() );

					JSimpleTableCellRendererData rendererData = m_vectCellList.get( i );
					Color color = rendererData.getColor();

					/*
					 *  �C�Ӎs��
					 */
					if( rendererData.getCellPosition().getRow() == row && rendererData.getCellPosition().getColumn() == col )
					{
						setBackground( color );
						isFound = true;
						break;
					}

					/*
					 *  �C�ӗ�
					 */
					if( rendererData.getCellPosition().getRow() == -1 && rendererData.getCellPosition().getColumn() == col )
					{
						setBackground( color );
						isFound = true;
						break;
					}

					/*
					 *  �C�Ӎs
					 */
					if( rendererData.getCellPosition().getRow() == row && rendererData.getCellPosition().getColumn() == -1  )
					{
						setBackground( color );
						isFound = true;
						break;
					}

					/*
					 *  �S�̍s��
					 */
					if( rendererData.getCellPosition().getRow() == -1  && rendererData.getCellPosition().getColumn() == -1  )
					{
						setBackground( color );
						isFound = true;
						break;
					}
				}

				if( !isFound )
				{
					setBackground( table.getBackground() );

					if (isSelected) {
						setForeground( table.getSelectionForeground() );
						setBackground( table.getSelectionBackground() );
					}
				}
			// edit s.inoue 2009/09/28
			setHorizontalAlignment(LEFT);
			// setHorizontalAlignment( (value instanceof Number)?RIGHT:LEFT );

			return this;
		}
	}

	/**
	 *  �J�X�^���Z�������_���N���X : ComboBoxCellRenderer
	 */
	/* JComboBox ���g���N���X�ɒu������ */
	protected class JComboBoxCellRenderer extends ExtendedComboBox implements TableCellRenderer
	{
		private final JTextField m_editor;

		/**
		 *  �R���X�g���N�^
		 *
		 *    @param  none
		 *    @return none
		 */
		public JComboBoxCellRenderer()
		{
			super();

			m_editor = (JTextField)( getEditor().getEditorComponent() );

			setEditable       ( true );
			setBorder         ( BorderFactory.createEmptyBorder() );

			m_editor.setOpaque( true );
			m_editor.setBorder( BorderFactory.createEmptyBorder() );
		}

		/**
		 *  @override : getTableCellRendererComponent
		 */
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
		{
			removeAllItems();

			if( isSelected )
			{
				m_editor.setForeground( table.getSelectionForeground() );
				m_editor.setBackground( table.getSelectionBackground() );
			}
			else
			{
				m_editor.setForeground( table.getForeground() );
				m_editor.setBackground( table.getBackground() );
			}

			addItem( (value == null) ? "" : value.toString() );

			return this;
		}
	}

	// add s.inoue 2010/01/11
	/**
	 *  �J�X�^���Z�������_���N���X : RadioButtonCellRenderer
	 */
	protected class JRadioButtonRenderer extends JRadioButton implements TableCellRenderer{

		/**
		 *  �R���X�g���N�^
		 *
		 *    @param  none
		 *    @return none
		 */
		public JRadioButtonRenderer()
		{
			super();
		}

		/**
		 *  @override : getTableCellRendererComponent
		 */
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
		{
			setHorizontalAlignment( SwingConstants.CENTER );

//			if( isSelected )
//			{
//				setForeground( table.getSelectionForeground() );
//				setBackground( table.getSelectionBackground() );
//			}
//			else
//			{
//				setForeground( table.getForeground() );
//				setBackground( table.getBackground() );
//			}
//
//			setSelected( false );

//			if( value instanceof Boolean )
//			{
//				 setSelected( Boolean.valueOf((Boolean)value) );
//			}
//			else
//			{
//				 setSelected( Boolean.valueOf((String )value) );
//			}

			// edit s.inoue 2010/01/13
			if( value != null )
			{
				setSelected(false);

				if( isSelected ){
					setSelected(true);
					setForeground( table.getSelectionForeground() );
					setBackground( table.getSelectionBackground() );
				}else{
					setSelected(false);
					setForeground( table.getForeground() );
					setBackground( table.getBackground() );
				}
			}
			return this;
		}
	}

	/**
	 *  �J�X�^���Z�������_���N���X : CheckBoxCellRenderer
	 */
	protected class JCheckBoxCellRenderer extends JCheckBox implements TableCellRenderer
	{
		/**
		 *  �R���X�g���N�^
		 *
		 *    @param  none
		 *    @return none
		 */
		public JCheckBoxCellRenderer()
		{
			super();
		}

		/**
		 *  @override : getTableCellRendererComponent
		 */
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
		{
			setHorizontalAlignment( SwingConstants.CENTER );

			if( isSelected )
			{
				setForeground( table.getSelectionForeground() );
				setBackground( table.getSelectionBackground() );
			}
			else
			{
				setForeground( table.getForeground() );
				setBackground( table.getBackground() );
			}

			setSelected( false );

			if( value != null )
			{
				if( value instanceof Boolean )
				{
					setSelected( Boolean.valueOf((Boolean)value) );
				}
				else
				{
					setSelected( Boolean.valueOf((String )value) );
				}
			}

			return this;
		}
	}

	/**
	 * tablemodel�p�R���X�g���N�^
	 * @param tmodel
	 */
	public JSimpleTable(DefaultTableModel dm ){

		m_objTableModel = dm;

		// �e�[�u��������
		cellInitialize();
	}

	/**
	 *  �R���X�g���N�^
	 *    @param  none
	 *    @return none
	 */
	public JSimpleTable()
	{
		m_objTableModel = new DefaultTableModel();

		// �e�[�u��������
		cellInitialize();
	}

	/**
	 * �Z���̏�����
	 */
	private void cellInitialize(){
		// edit s.inoue 2009/12/07
		m_vectCheckBox  = new Vector<JSimpleTableCellEditorData<ExtendedCheckBox>>       ();
		m_vectComboBox  = new Vector<JSimpleTableCellEditorData<JComboCellEditor>>();

		// ���f���ݒ�
		setModel( m_objTableModel );

		// �T�C�Y�ҏW
		setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

		setSurrendersFocusOnKeystroke        ( true  );

		getTableHeader().setReorderingAllowed( false );

		/**
		 *  @Override addFocusListener::focusLost
		 */
		DefaultCellEditor cellEdit = (DefaultCellEditor)getDefaultEditor( Object.class );

		cellEdit.getComponent().addFocusListener( new FocusAdapter()
		{
			public void focusLost( FocusEvent e )
			{
				if( isEditing() )
				{
					getCellEditor().stopCellEditing();
				}
			}
		} );

		/**
		 *  @Override addMouseListener::mousePressed
		 */
		getTableHeader().addMouseListener( new MouseAdapter()
		{
			public void mousePressed( MouseEvent e )
			{
				if( isEditing() )
				{
					getCellEditor().stopCellEditing();
				}

// del s.inoue 2009/12/15
//				// add s.inoue 2009/12/15
//				int[] selection = getSelectedRows();
//					for (int i = 0; i < selection.length; i++) {
//						// �e�[�u���E���f���̍s���ɕϊ�
//						int modelRow = convertRowIndexToModel(selection[i]);
//						System.out.println("View Row: " + selection
//						+ " Model Row: " + modelRow);
//					}
			}
		} );

		/* �t�H�[�J�X���擾�������A�s���� 0
		 * �Ȃ�A�t�H�[�J�X�����Ɉړ�����B */
		this.addFocusListener(new FocusAdapter(){
		    @Override
		    public void focusGained(FocusEvent e) {
			super.focusGained(e);
			JSimpleTable table = JSimpleTable.this;
			if (table.getRowCount() == 0) {
			    table.transferFocus();
			}
		    }
		    @Override
		    public void focusLost(FocusEvent arg0) {
			}
		});
	}

	// add s.inoue 2009/12/15
	public int[] getSelectedRows() {
		int[] rows = super.getSelectedRows();
//		if (rows.length == 0){
//			return new int[0];
//		}
		for (int i = 0, n = rows.length; i < n; i++) {
		    rows[i] = convertRowIndexToModel(rows[i]);
		}
		return rows;
	}

	// add s.inoue 2009/12/15
	public int getSelectedRow() {
		int row = super.getSelectedRow();
		if(row <= 0) return 0;
		return convertRowIndexToModel(row);
	}

	// add s.inoue 2009/12/15
	public int convertRowIndexToModel(int viewRowIndex){
		return super.convertRowIndexToModel(viewRowIndex);
	}

	/**
	 *  @Override columnMarginChanged
	 */
	public void columnMarginChanged( ChangeEvent e )
	{
		if( isEditing() )
		{
			getCellEditor().stopCellEditing();
		}

		super.columnMarginChanged( e );
	}
// del s.inoue 2009/10/25
//	/**
//	 *  �s���擾
//	 *
//	 *    @param  none
//	 *    @return int
//	 *
//	 */
//	public int getRowCount()
//	{
//		return m_objTableModel.getRowCount();
//	}
//
//	/**
//	 *  �񐔎擾
//	 *
//	 *    @param  none
//	 *    @return int
//	 */
//	public int getColumnCount()
//	{
//		return m_objTableModel.getColumnCount();
//	}

	/**
	 *  �񕝎擾
	 *
	 *    @param  ��
	 *    @return int
	 */
	private int getColumnWidth( int iColumn )
	{
		TableColumn column = getColumnModel().getColumn( iColumn );

		return column.getPreferredWidth();
	}

	/**
	 *  �񕝐ݒ�
	 *
	 *    @param  ����
	 *    @param  ��
	 *
	 *    @return none
	 */
	private void setColumnWidth( int iWidth, int iColumn )
	{
		TableColumn column = getColumnModel().getColumn( iColumn );

		column.setPreferredWidth( iWidth );
	}

	// add s.inoue 2009/12/07
//	public void valueChanged(ListSelectionEvent e) {
//
//		if (e.getValueIsAdjusting()) {
//
//		int index = e.getFirstIndex();
//		this.setValueAt(Boolean.TRUE, index, 0);
//		}
//	}

	/**
	 *  �J�X�^���Z���F�Z���ɑ΂��ĕҏW������������i ������^����Ɛݒ�s��S�Ă��Ώ� �j
	 *
	 *    @param  �ݒ�z��
	 *
	 *    @return none
	 */
	public void setCellEditForbid( final JSimpleTableCellPosition[] aryList )
	{
		Vector<JSimpleTableCellPosition> list = new Vector<JSimpleTableCellPosition>();

		try
		{
			for( int i=0; i<aryList.length; ++i )
			{
				list.add( aryList[ i ] );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		setCellEditForbid( list );
	}

	// edit s.inoue 2009/10/27
	/**
	 *  �J�X�^���Z���F�Z���ɑ΂��ĕҏW������������i ������^����Ɛݒ�s��S�Ă��Ώ� �j
	 *    @param  �ݒ胊�X�g
	 *    @return none
	 */
	public void setCellEditCustumForbid( DefaultTableModel model,final JSimpleTableCellPosition[] aryList )
	{
		Vector<JSimpleTableCellPosition> list = new Vector<JSimpleTableCellPosition>();

		try
		{
			for( int i=0; i<aryList.length; ++i )
			{
				list.add( aryList[ i ] );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		setCellEditCustumForbid( model,list );
	}
	/**
	 *  �J�X�^���Z���F�Z���ɑ΂��ĕҏW������������i ������^����Ɛݒ�s��S�Ă��Ώ� �j
	 *
	 *    @param  �ݒ胊�X�g
	 *
	 *    @return none
	 */
	public void setCellEditCustumForbid( DefaultTableModel model, final Vector<JSimpleTableCellPosition> list )
	{
		if( list != null )
		{
//			try
//			{
//				DefaultTableModel model = new DefaultTableModel()
//				{
//					/**
//					 *  @override isCellEditable
//					 */
//					public boolean isCellEditable( int row, int col )
//					{
//						for( int i=0; i<list.size(); ++i )
//						{
//							if( list.get(i).getRow() == row && list.get(i).getColumn() == col )
//							{
//								return false;
//							}
//
//							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() == col )
//							{
//								return false;
//							}
//
//							if( list.get(i).getRow() == row && list.get(i).getColumn() <= -1  )
//							{
//								return false;
//							}
//
//							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() <= -1  )
//							{
//								return false;
//							}
//						}
//						return true;
//					}
//				};
//
//				// �e�[�u������
//				this.copyTableModel( model );
//				this.setModel( model );
//
//				// �e�[�u���X�V
//				refreshTable();
//
//				m_objTableModel = model;
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//
//				return;
//			}

			// �ݒ���ۑ�
			m_vectCellEdit = list;
		}
	}

	/**
	 *  �J�X�^���Z���F�Z���ɑ΂��ĕҏW������������i ������^����Ɛݒ�s��S�Ă��Ώ� �j
	 *
	 *    @param  �ݒ胊�X�g
	 *
	 *    @return none
	 */
	public void setCellEditForbid( final Vector<JSimpleTableCellPosition> list )
	{
		if( list != null )
		{
			try
			{
				DefaultTableModel model = new DefaultTableModel()
				{
					/**
					 *  @override isCellEditable
					 */
					public boolean isCellEditable( int row, int col )
					{
						for( int i=0; i<list.size(); ++i )
						{
							if( list.get(i).getRow() == row && list.get(i).getColumn() == col )
							{
								return false;
							}

							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() == col )
							{
								return false;
							}

							if( list.get(i).getRow() == row && list.get(i).getColumn() <= -1  )
							{
								return false;
							}

							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() <= -1  )
							{
								return false;
							}
						}
						return true;
					}
				};

				// �e�[�u������
				this.copyTableModel( model );
				this.setModel( model );

				// �e�[�u���X�V
				refreshTable();

				m_objTableModel = model;
			}
			catch( Exception e )
			{
				e.printStackTrace();

				return;
			}

			// �ݒ���ۑ�
			m_vectCellEdit = list;
		}
	}

	/**
	 *  �J�X�^���Z���F�Z���J���[��ݒ肵�܂��i ������^����Ɛݒ�s��S�Ă��Ώ� �j
	 *
	 *    @param  �ݒ�z��
	 *    @return none
	 */
	public void setCellColor( JSimpleTableCellRendererData[] aryList )
	{
		Vector<JSimpleTableCellRendererData> list = new Vector<JSimpleTableCellRendererData>();

		try
		{
			for( int i=0; i<aryList.length; ++i )
			{
				list.add( aryList[ i ] );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		setCellColor( list );
	}

	/**
	 *  �J�X�^���Z���F�Z���J���[��ݒ肵�܂��i ������^����Ɛݒ�s��S�Ă��Ώ� �j
	 *
	 *    @param  �ݒ胊�X�g
	 *
	 *    @return none
	 */
	public void setCellColor( Vector<JSimpleTableCellRendererData> list )
	{
		if( list != null )
		{
			try
			{
				JSimpleTableCellRenderer renderer = new JSimpleTableCellRenderer( list );

				setDefaultRenderer( Object .class, renderer );
				setDefaultRenderer( Integer.class, renderer );
				setDefaultRenderer( String .class, renderer );
			}
			catch( Exception e )
			{
				e.printStackTrace();

				return;
			}

			refreshTable();

			// �ݒ���ێ�
			m_vectCellColor = list;
		}
	}

	// add s.inoue 2009/09/28
	/**
	 *  �J�X�^���Z���F�Z���J���[��ݒ肵�܂��i ������^����Ɛݒ�s��S�Ă��Ώ� �j
	 *    @param  �ݒ胊�X�g
	 *    @return none
	 */
	public void setCellColor( Vector<JSimpleTableCellRendererData> list ,JSimpleTableCellRowRenderer renderer)
	{
		if( list != null )
		{
			try
			{
				setDefaultRenderer( Object .class, renderer );
				setDefaultRenderer( Integer.class, renderer );
				setDefaultRenderer( String .class, renderer );
			}
			catch( Exception e )
			{
				e.printStackTrace();

				return;
			}
			refreshTable();
			// �ݒ���ێ�
			m_vectCellColor = list;
		}
	}

	// add s.inoue 2009/11/15
	public TableCellRenderer getCellRenderer(int row, int column) {

		 return super.getCellRenderer(row, column);
	}
	/**
	 *  �J�X�^���G�f�B�^�F�`�F�b�N�{�b�N�X���Z���ɐݒ肵�܂�.
	 *
	 *    @param  �ǉ��`�F�b�N�{�b�N�X
	 *    @param  �ǉ���
	 *
	 *    @return none
	 */
	public void setCellCheckBoxEditor( ExtendedCheckBox checkEditor, int iColumn )
	{
		TableColumn column;

		column = getColumnModel().getColumn( iColumn );

		// �J�X�^�������_��
		column.setCellRenderer( new JCheckBoxCellRenderer() );

		// �J�X�^���G�f�B�^
		column.setCellEditor( new DefaultCellEditor( checkEditor ) );

		// �����`��
		checkEditor.setHorizontalAlignment( SwingConstants.CENTER );

		// �ݒ�ێ�
		boolean isFound = false;

		for( int i=0; i<m_vectCheckBox.size(); ++i )
		{
			if( iColumn == m_vectCheckBox.get(i).getColumn() )
			{
				m_vectCheckBox.set( i, new JSimpleTableCellEditorData<ExtendedCheckBox>( checkEditor, iColumn ) );
				isFound = true;
			}
		}

		if( !isFound )
		{
			// edit s.inoue 2009/12/07
			m_vectCheckBox.add( new JSimpleTableCellEditorData<ExtendedCheckBox>( checkEditor, iColumn ) );
		}

		refreshTable();
	}

	/**
	 *  �J�X�^���G�f�B�^�F�`�F�b�N�{�b�N�X���Z���ɐݒ肵�܂�.
	 *
	 *    @param  �ǉ��`�F�b�N�{�b�N�X
	 *    @param  �ǉ���
	 *
	 *    @return none
	 */
	public void setCellRadioButtonEditor( ExtendedCheckBox radioEditor, int iColumn )
	{
		TableColumn column;

		column = getColumnModel().getColumn( iColumn );

		// �J�X�^�������_��
		column.setCellRenderer( new JRadioButtonRenderer() );

		// �J�X�^���G�f�B�^
		column.setCellEditor( new RadioButtonEditor( radioEditor ) );

		// �����`��
		radioEditor.setHorizontalAlignment( SwingConstants.CENTER );

		// �ݒ�ێ�
		boolean isFound = false;

		for( int i=0; i<m_vectCheckBox.size(); ++i )
		{
			if( iColumn == m_vectCheckBox.get(i).getColumn() )
			{
				m_vectCheckBox.set( i, new JSimpleTableCellEditorData<ExtendedCheckBox>( radioEditor, iColumn ) );
				isFound = true;
			}
		}

		if( !isFound )
		{
			m_vectCheckBox.add( new JSimpleTableCellEditorData<ExtendedCheckBox>( radioEditor, iColumn ) );
		}

		refreshTable();
	}
	 //�e�X�g�p�ɒǉ���
///////////////////////////////////////////
//	// add s.inoue 2010/01/12
//	private JRadioButton[] getHistoryNumber(){
//		JRadioButton[] rbs= new JRadioButton[getRowCount()];
//		String ret ="";
//		for (int i = 0; i < getRowCount(); i++) {
//			// Object[] rd = (Object[]) dmodel.getValueAt(i, 0);
//			// JRadioButton rb = (JRadioButton) rd[0];
//			// rbs[i] = new JRadioButton();
//			rbs[i] = (JRadioButton)getValueAt(i, 0);
////			if(rb.isSelected()){
////				ret = (String) getValueAt(i, 3);
////			}
//		}
//		return rbs;
//	}
//	// edit s.inoue 2010/01/13
//	protected class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
//
//	public RadioButtonEditor(JCheckBox checkBox) {
//		super(checkBox);
//		ButtonGroup buttonGroup = new ButtonGroup();
//		JRadioButton[] buttons  = getHistoryNumber();
//		for (int i=0; i<buttons.length; i++) {
//		buttonGroup.add(buttons[i]);
//		buttons[i].addItemListener(this);
//		}
//
//		for (int i = 0; i < buttons.length; i++) {
//			System.out.println("���񂽂��G"+buttons[i].isSelected());
//		}
//	}
//
//	@Override
//	public void itemStateChanged(ItemEvent e) {
//		// TODO �����������ꂽ���\�b�h�E�X�^�u
//
//	}
//}
	 //�e�X�g�p�ɒǉ�
///////////////////////////////////////////

	protected class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
		private JRadioButton button;

		public RadioButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
			if (value==null) return null;
			button = (JRadioButton)value;
			button.addItemListener(this);
			return (Component)value;
		}

		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}
	// ����(�R�[�h)����DefaultCellEditor�N���X��ǉ�
	private class InputSelectComboCellEditor extends DefaultCellEditor {
		private ImeController imeController = null;

		public InputSelectComboCellEditor(JComboBox comboBox) {
			super(comboBox);
			// add s.inoue 2009/10/14
			imeController = new ImeController();
			imeController.addFocusListenerForCharcterSubsets(comboBox, ImeMode.IME_OFF);
		}
	}

	/**
	 *  �J�X�^���G�f�B�^�F�R���{�{�b�N�X���Z���ɐݒ肵�܂�.
	 *
	 *    @param  �R���{�{�b�N�X�G�f�B�^
	 *    @param  �ǉ���
	 *
	 *    @return none
	 */
	public void setCellComboBoxEditor( JComboCellEditor comboEditor, int iColumn )
	{
		TableColumn column;

		column = getColumnModel().getColumn( iColumn );

		// �J�X�^���G�f�B�^
		column.setCellEditor( comboEditor );

		JComboBoxCellRenderer renderer = new JComboBoxCellRenderer(){
			Component editorComponent = getEditor().getEditorComponent();

			public Component getTableCellRendererComponent(
					JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col ){

				/* Modified 2008/03/22 �ጎ �w�i�F���ݒ肳��Ă���Z���́A
				 * �I�����Ă��w�i�F��I��F�ɂ��Ȃ��悤�ύX�B */
				Component returnComponent = table.getDefaultRenderer(Object.class)
					.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

				return returnComponent;
			}
		};

		column.setCellRenderer( renderer );

		// �ݒ�ێ�
		boolean isFound = false;

		for( int i=0; i<m_vectComboBox.size(); ++i )
		{
			if( iColumn == m_vectComboBox.get(i).getColumn() )
			{
				m_vectComboBox.set( i, new JSimpleTableCellEditorData<JComboCellEditor>( comboEditor, iColumn ) );

				isFound = true;
			}
		}

		if( !isFound )
		{
			m_vectComboBox.add( new JSimpleTableCellEditorData<JComboCellEditor>( comboEditor, iColumn ) );
		}

		refreshTable();
	}

	/**
	 *  �S�s�폜
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void clearAllRow()
	{
		m_objTableModel.setRowCount( 0 );
	}

	/**
	 *  �S��폜
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void clearAllColumn()
	{
		m_objTableModel.setColumnCount( 0 );
	}

	/**
	 *  �P�s�폜
	 *
	 *    @param  �폜�s
	 *    @return none
	 */
	public void clearRow( int iRow )
	{
		try
		{
			m_objTableModel.removeRow( iRow );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}
	}

	/**
	 *  �w�b�_�ǉ��F�e�[�u���I�[��Ƀw�b�_��ǉ����܂�.
	 *
	 *    @param  �ǉ��w�b�_
	 *
	 *    @return none
	 */
	public void addHeader( String header )
	{
		m_objTableModel.addColumn( header );

		refreshTable();
	}

	/**
	 *  �w�b�_�ǉ��F�e�[�u���I�[��Ƀw�b�_��ǉ����܂�.
	 *
	 *    @param  �ǉ��w�b�_
	 *
	 *    @return none
	 */
	public void addHeader( String[] headers )
	{
		// add s.inoue 2009/11/12
		this.modelHeader = headers;

		// del s.inoue 2009/11/12
//		for( int i=0; i<headers.length; ++i )
//		{
//			m_objTableModel.addColumn( headers[ i ] );
//		}
//		refreshTable();
	}

	/**
	 *  �f�[�^�ǉ��F�e�[�u���I�[�s�Ƀf�[�^��ǉ����܂�.
	 *
	 *    @param  �ǉ��f�[�^
	 *
	 *    @return none
	 */
	public void addData( String[] data )
	{
		try
		{
			m_objTableModel.addRow( data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	// add s.inoue 2010/01/08
	/**
	 *  �f�[�^�ǉ��F�e�[�u���I�[�s�Ƀf�[�^��ǉ����܂�.
	 *
	 *    @param  �ǉ��f�[�^
	 *
	 *    @return none
	 */
	public void addData( Object[] data )
	{
		try
		{
			m_objTableModel.addRow( data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}
	// add s.inoue 2009/10/22
	/**
	 *  �f�[�^�ǉ��F�e�[�u���I�[�s�Ƀf�[�^��ǉ����܂�.
	 *
	 *    @param  �ǉ��f�[�^
	 *    @return none
	 */
	public void addDataVector( Object[][] data, Object[] columnHeader)
	{
		try
		{
			m_objTableModel.setDataVector(data, columnHeader);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return;
		}
		// edit s.inoue 2009/10/22 �ꎞ
		// refreshTable();
	}

	/**
	 *  �f�[�^�ǉ��F�e�[�u���I�[�s�Ƀf�[�^��ǉ����܂�.
	 *
	 *    @param  �ǉ��f�[�^
	 *
	 *    @return none
	 */
	public void addData( Vector data )
	{
		try
		{
			m_objTableModel.addRow( data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  �f�[�^�}���F�s�P�ʂ̃f�[�^��}�����܂�.
	 *
	 *    @param  �}���f�[�^
	 *    @param  �}���ʒu
	 *
	 *    @return none
	 */
	public void insertData( String[] data, int iRow )
	{
		try
		{
			m_objTableModel.insertRow( iRow, data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  �f�[�^�}���F�s�ɑ΂��čs�P�ʂ̃f�[�^��}�����܂�.
	 *
	 *    @param  �}���f�[�^
	 *    @param  �}���ʒu
	 *
	 *    @return none
	 */
	public void insertData( Vector data, int iRow )
	{
		try
		{
			m_objTableModel.insertRow( iRow, data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  �f�[�^�ݒ�F���ɑ��݂���Z���ɑ΂��ăf�[�^��ݒ肵�܂�.
	 *
	 *    @param  �ݒ�f�[�^ (Object)
	 *    @param  �ݒ�s
	 *    @param  �ݒ��
	 *
	 *    @return none
	 */
	public void setData( Object data, int iRow, int iColumn )
	{
		try
		{
			m_objTableModel.setValueAt( data, iRow, iColumn );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  �f�[�^�ݒ�F���ɑ��݂���Z���ɑ΂��ăf�[�^��ݒ肵�܂�.
	 *
	 *    @param  �ݒ�f�[�^ (String)
	 *    @param  �ݒ�s
	 *    @param  �ݒ��
	 *
	 *    @return none
	 */
	public void setData( String data, int iRow, int iColumn )
	{
		try
		{
			m_objTableModel.setValueAt( data, iRow, iColumn );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  �f�[�^�擾�F�e�[�u���S�̂̃f�[�^���擾���܂�.
	 *
	 *    @param  none
	 *
	 *    @return Vector
	 *    @return null   : error of inside method.
	 */
	public Vector getData()
	{
		try
		{
			for( int iRow=0; iRow<m_objTableModel.getDataVector().size(); ++ iRow )
			{
				Vector rowData = (Vector)(m_objTableModel.getDataVector().get( iRow ));

				for( int iColumn=0; iColumn<rowData.size(); ++ iColumn )
				{
					if( rowData.get( iColumn ) == null )
					{
						m_objTableModel.setValueAt( "", iRow, iColumn );
					}
				}
			}

			return m_objTableModel.getDataVector();
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 *  �f�[�^�擾�F�s�P�ʂ̃f�[�^���擾���܂�.
	 *
	 *    @param  �s
	 *
	 *    @return Vector
	 *    @return null   : don't exist data.
	 */
	public Vector getData( int iRow )
	{
		try
		{
			Vector rowData = (Vector)(m_objTableModel.getDataVector().get( iRow ));

			for( int iColumn=0; iColumn<rowData.size(); ++iColumn )
			{
				if( rowData.get( iColumn ) == null )
				{
					m_objTableModel.setValueAt( "", iRow, iColumn );
				}
			}

			return rowData;
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 *  �f�[�^�擾�F�Z���P�ʂ̃f�[�^���擾���܂�.
	 *
	 *    @param  �s
	 *    @param  ��
	 *
	 *    @return Object
	 *    @return null   : data doesn't exist.
	 */
	public Object getData( int iRow, int iColmun )
	{
		try
		{
			return m_objTableModel.getValueAt( iRow, iColmun );
		}
		catch( Exception e )
		{
			return null;
		}
	}

	/**
	 *  �e�[�u���X�V
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
//	protected void refreshTable()
	public void refreshTable()
	{
		// �c���ݒ�
		int rowCount = this.getRowCount();
		for (int i = 0; i < rowCount; i++) {

			int rowHeight = 0;
			if (this.rowHeights != null &&
					this.rowHeights.length > i &&
					rowHeight > 0) {

				rowHeight = this.rowHeights[i];
			}
			else {
				rowHeight = DEFAULT_ROW_HEIGHT;
			}
			this.setRowHeight(i, rowHeight);
		}

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.getColumnModel();

		if (this.columnWidths != null) {

			int columnCount = columnModel.getColumnCount();
			for (int i = 0; i < columnCount; i++) {

				int columnWidth = 0;
				if (this.columnWidths.length > i ) {
						columnWidth = this.columnWidths[i];
				}else {
					columnWidth = DEFAULT_COLUMEN_WIDTH;
				}
				columnModel.getColumn(i).setPreferredWidth(columnWidth);
				columnModel.getColumn(i).setWidth(columnWidth);
			}
		}

		this.resizeAndRepaint();
	}

	/**
	 *  JSimpleTable�̃f�[�^��DefaultTalbeModel�^�ŕ������܂�.
	 *
	 *    @param  �������e�[�u��
	 *    @return none
	 */
	protected void copyTableModel( DefaultTableModel destTableModel )
	{
		Vector vecData = m_objTableModel.getDataVector();

		for( int i=0; i<getColumnCount(); ++i )
		{
			destTableModel.addColumn( m_objTableModel.getColumnName( i ) );
		}

		for( int i=0; i<vecData.size();   ++i )
		{
			destTableModel.addRow( (Vector)(vecData.get( i )) );
		}
	}

	public int[] getRowHeights() {
		return rowHeights;
	}

	public void setRowHeights(int[] rowHeights) {
		this.rowHeights = rowHeights;
	}

	public int[] getColumnWidths() {
		return columnWidths;
	}

	public void setPreferedColumnWidths(int[] columnWidths) {
		this.columnWidths = columnWidths;
	}

	// add s.inoue 2009/09/25
	/*
	 * �w�b�_�[�Œ�e�[�u��:�I���s�𒊏o����B
	 */
	public ArrayList<Integer> getselectedRows(JSimpleTable modelfixed,JSimpleTable model){
		int rowCount = model.getRowCount();
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		for (int i = 0; i < rowCount; i++) {
			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}

	// add s.inoue 2009/09/25
	/*
	 * �Œ�e�[�u��:�I���s�𒊏o����B
	 */
	public ArrayList<Integer> getselectedRows(JSimpleTable model){
		int rowCount = model.getRowCount();
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		for (int i = 0; i < rowCount; i++) {
			if ((Boolean) model.getData(i, 0) == Boolean.TRUE) {
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}

	// add s.inoue 2009/09/25
	/*
	 * ���ʁF�I���s��I����Ԃɂ���(�擪�`�F�b�N�{�b�N�X�p)
	 */
	public void setselectedRows(JSimpleTable modelfixed ,ArrayList<Integer> selectedRows){

		int selCnt = selectedRows.size();
		for (int i = 0; i < selCnt; i++) {
			int selectIdx = selectedRows.get(i);
			modelfixed.setData(true, selectIdx, 0);
			modelfixed.setRowSelectionInterval(selectIdx, selectIdx);
		}
	}

	// add s.inoue 2009/10/13
	@Override
	public boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {

		boolean retValue = super.processKeyBinding(ks, e, condition, pressed);
        //if(!check.isSelected()) return retValue;
        // if(KeyStroke.getKeyStroke('\t').equals(ks) || KeyStroke.getKeyStroke('\n').equals(ks)) {
		if(KeyStroke.getKeyStroke('\t').equals(ks)){
			// System.out.println("tab or enter typed");
            return retValue;
        }

        // if(getInputContext().isCompositionEnabled() && !isEditing() && !pressed && !ks.isOnKeyRelease()) {
		if(!isEditing() && !ks.isOnKeyRelease() && !pressed) {
            int selectedRow = getSelectedRow();
            int selectedColumn = getSelectedColumn();
            if(selectedRow!=-1 && selectedColumn!=-1) {
                boolean dummy = editCellAt(selectedRow, selectedColumn);
                // System.out.println("editCellAt: "+dummy);
            }
        }
        return retValue;

//		return super.processKeyBinding(ks, e, condition, pressed);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

