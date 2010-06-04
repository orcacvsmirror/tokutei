package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import javax.swing.JFrame;
import java.awt.event.*;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;

/**
 * edit s.inoue �A���}�b�`�G���[�ꗗ��� 
 * �������ʃf�[�^��荞�ݎ��ɁA��f�҂��������������Ƃ��ɑI�������邽�߂̃t���[���B
 */
public class JImportErrorResultFrameCtrl extends JImportErrorResultFrame
{
	
	private JSimpleTable model = new JSimpleTable();
	private Vector<JImportErrorResultFrameData> KojinData;
	private JImportErrorResultFrameData SelectedData = null;  //  @jve:decl-index=0:
	
	public JImportErrorResultFrameCtrl(Vector<JImportErrorResultFrameData> Data,JFrame ParentFrame)
	{
		super();
		
		/* �񕝂̐ݒ� */
		// edit s.inoue 20080924
		//model.setPreferedColumnWidths(new int[]{55, 120, 120, 120, 120, 120, 120, 120, 55, 110, 110, 130, 55, 110 ,120, 160});
		model.setPreferedColumnWidths(new int[]{55, 120, 120, 120, 120, 120, 120, 120, 55, 110, 110, 130, 55,160});
		
		// �e�[�u���̃w�b�_�̒ǉ�
		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_Table.add(scroll);
		// oms s.inoue
		// model.addHeader("");
		model.addHeader("�s�ԍ�");
		model.addHeader("�G���[���ږ�");
		model.addHeader("�G���[���");
		model.addHeader("���f���{�@�֔ԍ�");
		model.addHeader("��f�������ԍ�");
		model.addHeader("�������{�N����");
		model.addHeader("����");
		model.addHeader("���N����");
		model.addHeader("����");
		model.addHeader("���сE�n��");
		model.addHeader("�H�O/�H��");
		model.addHeader("�������ڃR�[�h");
		model.addHeader("���{�敪");
		// edit s.inoue 20080924
		//model.addHeader("�ُ�敪");
		//model.addHeader("���ʒl�`��");
		model.addHeader("���ʒl");
		
		String prerowNo ="";
		String errDigit ="";
		
		for(int i = 0 ; i < Data.size() ; i++)
		{
			JImportErrorResultFrameData ErrorResultFrameData = Data.get(i);
			
			switch(ErrorResultFrameData.errCase){
				case 0: errDigit ="�^�s��";break;
				case 1: errDigit ="���t�s��";break;
				case 2: errDigit ="�����s��";break;
				case 3: errDigit ="�����s��v";break;
				case 4: errDigit ="�a�����s��v";break;
				case 5: errDigit ="���ʕs��v";break;			
				case 6: errDigit ="������";break;
				case 7: errDigit ="�L�[��";break;
				case 8: errDigit ="�L�[�d��";break;
				case 9: errDigit ="�f�[�^�����s��";break;
				case 10: errDigit ="�K�{���ڕs��";break;
				case 11: errDigit ="���̑�";break;
				case 12: errDigit ="�f�[�^�l�s��";break;
				default:
			}
			
			// �s�Ń}�[�W����
			if (String.valueOf(ErrorResultFrameData.rowNo).equals(prerowNo)){
				//���s�̃G���[�̏ꍇ�A�\�����Ȃ�
				//int rowCnt = model.getRowCount();
				//String errCase=model.getData(rowCnt, 3).toString() + "," + errDigit;
				//model.setData(errCase, rowCnt, 3);
				
			}else{
				
				String[] AddItem = {String.valueOf(ErrorResultFrameData.rowNo),
						ErrorResultFrameData.errField,errDigit,
						ErrorResultFrameData.jisiKikanNo,ErrorResultFrameData.jusinSeiriNo,
						ErrorResultFrameData.jishiDate,ErrorResultFrameData.kanaShimei,
						ErrorResultFrameData.seiNenGapi,ErrorResultFrameData.seiBetu,
						ErrorResultFrameData.nyuBi,ErrorResultFrameData.shokuZenGo,
						ErrorResultFrameData.koumokuCd,ErrorResultFrameData.jisiKbn,
						// edit s.inoue 20080924
						//ErrorResultFrameData.ijyoKbn,ErrorResultFrameData.kekkaTiKeitai,
						ErrorResultFrameData.kekkaTi};
	
				model.addData(AddItem);
				prerowNo = String.valueOf(ErrorResultFrameData.rowNo);
			}
		}
		
		// oms s.inoue
		//		JSimpleTableCellPosition[] forbitCells = {
		//				new JSimpleTableCellPosition(-1,1),
		//				new JSimpleTableCellPosition(-1,2),
		//				new JSimpleTableCellPosition(-1,3),
		//				new JSimpleTableCellPosition(-1,4),
		//				new JSimpleTableCellPosition(-1,5),
		//				new JSimpleTableCellPosition(-1,6),
		//				new JSimpleTableCellPosition(-1,7),
		//				new JSimpleTableCellPosition(-1,8),
		//				new JSimpleTableCellPosition(-1,9),
		//				new JSimpleTableCellPosition(-1,10),
		//				new JSimpleTableCellPosition(-1,11),
		//				new JSimpleTableCellPosition(-1,12),				
		//				};
		// model.setCellEditForbid(forbitCells);
		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {new JSimpleTableCellPosition(-1,-1)};
		model.setCellEditForbid(iSetColumnList);
		
		// oms s.inoue
		// model.setCellCheckBoxEditor(new JCheckBox(), 0);
		
		KojinData = Data;
		
		// �G���^�[�L�[�̏���
		jButton_End.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// del s.inoue
		//jButton_Select.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		model.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this,jButton_Select));
		
		// �_�u���N���b�N�̏���
		// del s.inoue
		//model.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JDoubleClickEvent(this,jButton_Select));
		
		setVisible(true);
	}
	
	/**
	 * �I���{�^��
	 */
	public void pushedEndButton( ActionEvent e )
	{
		SelectedData = null;
		dispose();
	}
	
	/**
	 * ed s.inoue
	 * �I���{�^���������捞�{�^��
	 */
	public void pushedSelectButton( ActionEvent e )
	{
		if(model.getSelectedRow() < 0)
		{
			return;
		}
		
		SelectedData = KojinData.get(model.getSelectedRow());
		dispose();
	}
	
	/**
	 * @return null�������ꍇ�A�����I�΂Ȃ������B
	 */
	public JImportErrorResultFrameData GetSelectedData()
	{
		return SelectedData;
	}

	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == jButton_End )
		{
			pushedEndButton( e );
		}
		
		if( e.getSource() == jButton_Select )
		{
			pushedSelectButton( e );
		}
	}
}  //  @jve:decl-index=0:visual-constraint="9,12"


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


