package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JLong;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRendererData;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessai;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiDataInput;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiDataOutput;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;

import org.apache.log4j.Logger;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintRyosyusyo;

/**
 * �����f�[�^�ҏW��ʐ���
 */
public class JInputKessaiDataFrameCtrl extends JInputKessaiDataFrame {

	private static final long serialVersionUID = -5839937082793048226L;	// edit n.ohkubo 2015/03/01�@�ǉ�

	private static org.apache.log4j.Logger logger =
		Logger.getLogger(JInputKessaiDataFrameCtrl.class);

	private static String SELECT_TUIKA_SQL = createSelectTuikaSql();

	private static String createSelectTuikaSql(){
		String query = "SELECT SYOUSAI.TUIKA_KENSIN_CD,"
		+ "  MASTER.KOUMOKU_NAME, SYOUSAI.TANKA_TUIKA_KENSIN, "
		+ "  SONOTA.KEKA_TI, SONOTA.KOMENTO " +
		// edit ver2 s.inoue 2009/08/06
		// �ꎞ�e�[�u���֓o�^
		"FROM T_KESAI_SYOUSAI_WK AS SYOUSAI " +
		"LEFT JOIN T_KOJIN AS KOJIN " + "ON "
		+ "  SYOUSAI.UKETUKE_ID = KOJIN.UKETUKE_ID " +
		"LEFT JOIN T_KENSHINMASTER AS MASTER " + "ON "
		+ "  SYOUSAI.TUIKA_KENSIN_CD = MASTER.KOUMOKU_CD " + "AND "
		+ "  MASTER.HKNJANUM = KOJIN.HKNJANUM " +
		"LEFT JOIN T_KENSAKEKA_SONOTA AS SONOTA " + "ON "
		+ "  SYOUSAI.TUIKA_KENSIN_CD = SONOTA.KOUMOKU_CD " + "AND "
		+ "  SYOUSAI.UKETUKE_ID = SONOTA.UKETUKE_ID " + "AND "
		+ "  SYOUSAI.KENSA_NENGAPI = SONOTA.KENSA_NENGAPI " +
		" WHERE SYOUSAI.UKETUKE_ID = ? "
		+ " AND SYOUSAI.KENSA_NENGAPI = ? "
		+ " AND SYOUSAI.TKIKAN_NO = ? ";

		return query;
	}

	private static final int COLUMN_INDEX_KOUMOKU_CD = 0;
	private static final int COLUMN_INDEX_KOUMOKU_NAME = 1;
	private static final int COLUMN_INDEX_TANKA = 2;
	private static final String GROUP_CODE_SHINDENZU = "9A110161000000049";
	private static final String GROUP_CODE_GANTEI = "9E100161000000049";
	private static final String GROUP_CODE_HINKETSU = "2A020161001930149";
	private JInputKessaiDataFrameData validatedData = new JInputKessaiDataFrameData();

	private DefaultTableModel dmodel = null;
	private JSimpleTable table=null;
	Object[][] rowcolumn = null;
	private TableRowSorter<TableModel> sorter =null;
//	private ArrayList<Hashtable<String, String>> result;	// edit n.ohkubo 2015/03/01�@���g�p�Ȃ̂ō폜

	private Vector<JSimpleTableCellRendererData> cellColors = new Vector<JSimpleTableCellRendererData>();
 	private final Color COLOR_ABLE = ViewSettings.getAbleItemBgColor();

	private String[] header = { "���ڔԍ�", "���ږ�", "�P���i�~�j[����]" };
	private JKessaiDataOutput output = null;
	private Vector<JKessaiProcessData> data;

	private static final String CODE_SEIKATU_KAIZEN = "9N801000000000011";
	private static final String CODE_HOKEN_SHIDOU = "9N806000000000011";
	private static final String CODE_SAIKETSU_ZIKAN = "9N141000000000011";
	private static final String CODE_ISHINOHANDAN_1 = "9N501000000000011";
	private static final String CODE_ISHINOHANDAN_2 = "9N506000000000011";
	private static final String CODE_ISHINOHANDAN_3 = "9N511000000000049";
	private static final String CODE_ISHINOHANDAN_4 = "9N516000000000049";
	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	// add s.inoue 2010/01/15
	private static final String TANKA_HANTEI_KIHON = "1";
	private static final String TANKA_HANTEI_DOC = "2";
	// del s.inoue 2012/02/03
	// private boolean print_flg = false;

	// edit s.inoue 2010/02/16
	boolean existsDoc = false;

	/**
	 * �R���X�g���N�^
	 */
	private int selectedDataIndex = -1;

	// add s.inoue 2012/01/20
	public JInputKessaiDataFrameCtrl(){

	}

	public JInputKessaiDataFrameCtrl(String uketukeId,
			String hihokenjyasyoKigou, String hihokenjyasyoNo,
			String kensaDate, Vector<JKessaiProcessData> Data) {
		initializeSetting(uketukeId,
				hihokenjyasyoKigou, hihokenjyasyoNo,
				kensaDate,Data);
	}

	// add s.inoue 2009/11/17
	private void initializeSetting(String uketukeId,
			String hihokenjyasyoKigou, String hihokenjyasyoNo,
			String kensaDate, Vector<JKessaiProcessData> Data){
		this.data = Data;

		for (int i = 0; i < Data.size()	; i++) {
			JKessaiProcessData processData = Data.get(i);

			if (processData.uketukeId.equals(uketukeId) &&
					processData.KensaDate.equals(kensaDate)	) {

				this.selectedDataIndex = i;
				break;
			}
		}

		validatedData.setUketuke_id(uketukeId);
		validatedData.setHihokenjyasyo_Kigou(hihokenjyasyoKigou);
		validatedData.setHihokenjyasyo_No(hihokenjyasyoNo);
		validatedData.setKensaDate(kensaDate);

		/* �ی��Ҕԍ����擾����B */
		String hokenjaNum = getHokenjaNumber();
		validatedData.setHokenjyaNum(hokenjaNum);

		/* ���σf�[�^���擾����B */
		ArrayList<Hashtable<String, String>> results = getKesaiData(kensaDate);

		if (results.size() <= 0){
			JErrorMessage.show("M4754", this);
			return;
		}

		Hashtable<String, String> resultItem = results.get(0);

		/* �l�f�[�^�\��������������B */
		this.initializeKozinDataDisplay(resultItem);

		/* ���σf�[�^�̕\��������������B */
		this.initializeKessaiDataDispay(kensaDate, resultItem);

		/* �ǉ����f���ڃe�[�u���f�[�^�擾 */
		this.initializeTsuikaTable(kensaDate, results);

		/* �ǉ��e�[�u�������� */
		this.initilizeTableSetting(kensaDate,results);

		/* �����񊈐� */
		this.initilizeControl(resultItem);

		/* �t�H�[�J�X������ */
		this.focusInitialize();
		// add s.inoue 2009/12/03
		functionListner();

		/* �Čv�Z */
		this.pushedReCalcButton();

//		// add s.inoue 2012/01/26	// edit n.ohkubo 2015/03/01�@���g�p�Ȃ̂ō폜
//		initilazeFunctionSetting();	// edit n.ohkubo 2015/03/01�@���g�p�Ȃ̂ō폜
//		if (print_flg)
//			jButton_PrintRyosyu.setVisible(true);

		// add s.inoue 2009/11/12
		table.addHeader(this.header);
		table.refreshTable();
	}

	// edit n.ohkubo 2015/03/01�@���g�p�Ȃ̂ō폜
//	// add s.inoue 2012/01/26
//	/* �ʐݒ�p */
//	private void initilazeFunctionSetting(){
//		ArrayList<Hashtable<String, String>> result = null;
//
//		try{
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
//			sb.append(" FROM T_SCREENFUNCTION ");
//			sb.append(" WHERE SCREEN_CD = ");
//			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_SEIKYU_EDIT_CODE));
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//		}
//
//// del s.inoue 2012/02/03
////		for (int i = 0; i < result.size(); i++) {
////			Hashtable<String, String> item = result.get(i);
////
////			String functionCd = item.get("FUNCTION_CD");
////			if (JApplication.func_printCode.equals(functionCd)){
////				print_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
////			}
////		}
//
//	}

	// �e�[�u��������
	private void initilizeTableSetting(String kensaDate,ArrayList<Hashtable<String, String>> results){

		   Object[][] tuika = this.initializeTsuikaTable(kensaDate, results);
		   dmodel = new DefaultTableModel(tuika,header){
			private static final long serialVersionUID = -2022097518500759862L;	// edit n.ohkubo 2015/03/01�@�ǉ�

		@Override
		public boolean isCellEditable(int row, int column) {
		   boolean retflg = false;
		   if ( column >1 ){
			   	retflg = true;
		     	}
		        return retflg;
		     }
		   };
		   sorter = new TableRowSorter<TableModel>(dmodel);
		   table = new JSimpleTable(dmodel);
//		   table.setPreferedColumnWidths(new int[] { 150, 230, 100});	// edit n.ohkubo 2015/03/01�@�폜
		   table.setPreferedColumnWidths(new int[] { 150, 300, 110});	// edit n.ohkubo 2015/03/01�@�ǉ�

		   table.setRowSorter(sorter);
		   table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		   table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		   final JScrollPane scroll = new JScrollPane(table);
		   JViewport viewport = new JViewport();
		   scroll.setRowHeader(viewport);

		   jPanel_TableArea.add(scroll);
	       dmodel.setDataVector(tuika,header);
// del s.inoue 2012/01/18
		   TableColumnModel columns = table.getColumnModel();
		   for(int i=0;i<columns.getColumnCount();i++) {
			   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
		   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
		   }
		   // �����I��
		   if (table.getRowCount() > 0) {
			   table.setRowSelectionInterval(0, 0);
		   }

		   table.addHeader(this.header);
		   table.refreshTable();

		   /* �Z���̐F���X�V����B */
		   this.refreshTableCellColor();
	}

	// control����
	private void initilizeControl(Hashtable<String, String> resultItem){
		// �ҏW�s�̐ݒ�
		jTextField_Jyusinken_ID.setEditable(false);
		jTextField_Name.setEditable(false);
		jTextField_Birthday.setEditable(false);
		jTextField_Sex.setEditable(false);
		jTextField_KensaDate.setEditable(false);
		// del s.inoue 2009/08/27
		// jTextField_ToHL7Date.setEditable(false);
		jTextField_AllTanka.setEditable(false);
		jTextField_AllMadoguchi.setEditable(false);
		jTextField_TotalFee.setEditable(false);

		// add s.inoue 2009/10/08
		// ��{
		String tankaHantei = resultItem.get("TANKA_HANTEI");
		tankaHantei = (tankaHantei == null) ? "1": tankaHantei;
		if (tankaHantei.equals("2")) {
			jTextField_KihonTanka.setEditable(false);
			// edit s.inoue 2009/10/13
			jComboBox_MadoguchiKihonSyubetu.setEnabled(false);
			jTextField_MadoguchiKihonKinInput.setEditable(false);
			jTextField_hokenjyaJougenKihon.setEditable(false);
			jTextField_MadoguchiKihonKin.setEditable(false);

			jTextField_HinketuTanka.setEditable(false);
			// edit s.inoue 2009/10/13
			jComboBox_MadoguchiSyousaiSyubetu.setEnabled(false);
			jTextField_MadoguchiSyousaiKinInput.setEditable(false);
			jTextField_hokenjyaJougenSyosai.setEditable(false);
			jTextField_MadoguchiSyousaiKin.setEditable(false);

			jTextField_SindenzuTanka.setEditable(false);
			jTextField_GanteiTanka.setEditable(false);

			jTextField_TsuikaTankaSum.setEditable(false);
			// edit s.inoue 2009/10/13
			jComboBox_MadoguchiTsuikaSyubetu.setEnabled(false);
			jTextField_MadoguchiTsuikaKinInput.setEditable(false);
			jTextField_hokenjyaJougenTsuika.setEditable(false);
			jTextField_MadoguchiTsuikaKin.setEditable(false);
			/* �K�{���ڂɔw�i�F��ݒ肷��B */
			// eidt s.inoue 2012/05/15
			// jTextField_DocTanka.setBackground(ViewSettings.getRequiedItemBgColor());
			jLabel_ningenDoc.setForeground(ViewSettings.getRequiedItemFrColor());
		}else{
			jTextField_DocTanka.setEditable(false);
			jComboBox_MadoguchiDocSyubetu.setEnabled(false);
			jTextField_MadoguchiDocKinInput.setEditable(false);
			jTextField_hokenjyaJougenDoc.setEditable(false);
			jTextField_MadoguchiNingenDocKin.setEditable(false);
			/* �K�{���ڂɔw�i�F��ݒ肷��B */
			// eidt s.inoue 2012/05/15
			// jTextField_KihonTanka.setBackground(ViewSettings.getRequiedItemBgColor());
			jLabel_kihon.setForeground(ViewSettings.getRequiedItemFrColor());
		}

		jTextField_MadoguchiKihonKin.setEditable(false);
		jTextField_MadoguchiSyousaiKin.setEditable(false);
		jTextField_MadoguchiTsuikaKin.setEditable(false);
		jTextField_MadoguchiNingenDocKin.setEditable(false);

		/* �d�v���ڂɔw�i�F��ݒ肷��B */
		jTextField_hihokenjasyotouBangou.setBackground(ViewSettings.getRequiedItemBgColor());
		jTextField_hihokenjasyotouKigou.setBackground(ViewSettings.getImportantItemBgColor());

		/*�}��̔w�i�F��ݒ肷�� */
		// jLabel_ableExample.setBackground(ViewSettings.getAbleItemBgColor());
		jLabel119.setBackground(ViewSettings.getRequiedItemBgColor());
	}


	/**
	 * ������
	 */
	// edit h.yoshitama 2009/10/01
	private void focusInitialize() {
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jRadioButton_ItakuTankaKobetu);
		this.focusTraversalPolicy.addComponent(jRadioButton_ItakuTankaKobetu);
		this.focusTraversalPolicy.addComponent(jRadioButton_ItakuTankaSyudan);
		this.focusTraversalPolicy.addComponent(jComboBox_SeikyuKubun);
		this.focusTraversalPolicy.addComponent(jTextField_KihonTanka);
		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiKihonSyubetu);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiKihonKinInput);
		this.focusTraversalPolicy.addComponent(jTextField_hokenjyaJougenKihon);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiSonota);
		this.focusTraversalPolicy.addComponent(jTextField_HinketuTanka);
		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiSyousaiSyubetu);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiSyousaiKinInput);
		this.focusTraversalPolicy.addComponent(jTextField_hokenjyaJougenSyosai);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiSyousaiKin);
		this.focusTraversalPolicy.addComponent(jTextField_SindenzuTanka);
		this.focusTraversalPolicy.addComponent(jTextField_GanteiTanka);
		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiTsuikaSyubetu);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiTsuikaKinInput);
		this.focusTraversalPolicy.addComponent(jTextField_hokenjyaJougenTsuika);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiTsuikaKin);
		// edit s.inoue 2009/11/09
		this.focusTraversalPolicy.addComponent(jTextField_DocTanka);
		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiDocSyubetu);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiDocKinInput);
		this.focusTraversalPolicy.addComponent(jTextField_hokenjyaJougenDoc);
		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiNingenDocKin);
		this.focusTraversalPolicy.addComponent(this.table);
		this.focusTraversalPolicy.addComponent(jButton_ReCalc);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);
	}

	// add s.inoue 2009/12/03
	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * �ǉ����f���ڃe�[�u��������������B
	 * @return
	 */
	private Object[][] initializeTsuikaTable(String kensaDate,
			ArrayList<Hashtable<String, String>> results) {

		String[] params = {
					validatedData.getUketuke_id(),
					kensaDate,
					JApplication.kikanNumber
				};
		try {
			results = JApplication.kikanDatabase.sendExecuteQuery(
					SELECT_TUIKA_SQL,
					params);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		rowcolumn = new Object[results.size()][3];

		for (int i = 0; i < results.size(); i++) {

			Hashtable<String, String> result = results.get(i);
			String kekati = result.get("KEKA_TI");

			if (kekati != null && !kekati.isEmpty()) {
				rowcolumn[i][COLUMN_INDEX_KOUMOKU_CD] = result.get("TUIKA_KENSIN_CD");
				rowcolumn[i][COLUMN_INDEX_KOUMOKU_NAME] = result.get("KOUMOKU_NAME");

				String tankaTsuikaKenshin = result.get("TANKA_TUIKA_KENSIN");

				try {
					if (tankaTsuikaKenshin.isEmpty()) {
						rowcolumn[i][COLUMN_INDEX_TANKA] = new Integer("0");
					} else {
						rowcolumn[i][COLUMN_INDEX_TANKA] = new Integer(tankaTsuikaKenshin);
					}
				} catch (NumberFormatException e) {
					/* �������Ȃ� */
				}
			}
		}

	    return rowcolumn;
	}

	/**
	 * ���σf�[�^�̕\��������������B
	 */
	private void initializeKessaiDataDispay(String kensaDate,
			Hashtable<String, String> resultItem) {

		// edit s.inoue 2010/02/10
		String tankaHantei = resultItem.get("TANKA_HANTEI");
		tankaHantei = (tankaHantei == null) ? "1": tankaHantei;

		// add s.inoue 20081008 �����敪�R���{�{�b�N�X�̏����ݒ�
		jComboBox_SeikyuKubun.addItem("��{�I�Ȍ��f");
		jComboBox_SeikyuKubun.addItem("��{�I�Ȍ��f�{�ڍׂȌ��f");
		jComboBox_SeikyuKubun.addItem("��{�I�Ȍ��f�{�ǉ����f����");
		jComboBox_SeikyuKubun.addItem("��{�I�Ȍ��f�{�ڍׂȌ��f�{�ǉ����f����");
		jComboBox_SeikyuKubun.addItem("�l�ԃh�b�N");

		// eidt s.inoue 2012/05/15
		// this.jComboBox_SeikyuKubun.setBackground(ViewSettings.getRequiedItemBgColor());
		jLabel33.setForeground(ViewSettings.getRequiedItemFrColor());

		Integer seikyuKbn =
			Integer.valueOf(resultItem.get("SEIKYU_KBN")) -1;
		jComboBox_SeikyuKubun.setSelectedIndex(seikyuKbn);

		/* �ڍׂȌ��f�̃R�[�h��ݒ肷��B */
		jTextField_HinketsuCode.setText(resultItem.get("HINKETU_CD"));
		jTextField_SindenzuCode.setText(resultItem.get("SINDENZU_CD"));
		jTextField_GanteiCode.setText(resultItem.get("GANTEI_CD"));

		// edit s.inoue 2010/02/10
		if (tankaHantei.equals(TANKA_HANTEI_KIHON)){
			/* �P���i��{�I�Ȍ��f�j */
			String tankaKihon = resultItem.get("TANKA_KIHON");
			if (tankaKihon != null && !tankaKihon.isEmpty()) {
				jTextField_KihonTanka.setText(tankaKihon.replaceFirst("^0+", ""));
			}

			String hokenjyaNum = validatedData.getHokenjyaNum();
			String uketuke_id = validatedData.getUketuke_id();

			/* �P���i�ڍׂȌ��f�A�n�������j */
			if (JKessaiProcess.isExistSyousaiKensaKoumoku(hokenjyaNum, uketuke_id,
					kensaDate, GROUP_CODE_HINKETSU)) {
				String tankaHinketsu = resultItem.get("TANKA_HINKETU");
				if (tankaHinketsu != null && !tankaHinketsu.isEmpty()) {
					jTextField_HinketuTanka.setText(tankaHinketsu.replaceFirst("^0+", ""));
				}
			}

			/* �P���i�ڍׂȌ��f�A��ꌟ���j */
			if (JKessaiProcess.isExistSyousaiKensaKoumoku(hokenjyaNum, uketuke_id,
					kensaDate, GROUP_CODE_GANTEI)) {
				String tankaGantei = resultItem.get("TANKA_GANTEI");
				if (tankaGantei != null && !tankaGantei.isEmpty()) {
					jTextField_GanteiTanka.setText(tankaGantei.replaceFirst("^0+",""));
				}
			}

			/* �P���i�ڍׂȌ��f�A�S�d�}�j */
			if (JKessaiProcess.isExistSyousaiKensaKoumoku(hokenjyaNum, uketuke_id,
					kensaDate, GROUP_CODE_SHINDENZU)) {
				String tankaShindenzu = resultItem.get("TANKA_SINDENZU");
				if (tankaShindenzu != null && !tankaShindenzu.isEmpty()) {
					jTextField_SindenzuTanka.setText(tankaShindenzu.replaceFirst("^0+", ""));
				}
			}
		// edit s.inoue 2010/02/10
		}else if(tankaHantei.equals(TANKA_HANTEI_DOC)){
			// add ver2 s.inoue 2009/06/17
			/* �P���i�l�ԃh�b�N�j */
			String tankaNingenDoc = resultItem.get("TANKA_NINGENDOC");
			if (tankaNingenDoc != null && !tankaNingenDoc.isEmpty()) {
				jTextField_DocTanka.setText(tankaNingenDoc.replaceFirst("^0+", ""));
			}
			// edit s.inoue 2010/02/16
			this.existsDoc = true;
		}

		// move s.inoue 2010/02/10
		/* �P���i���v�j */
		String tankaGoukei = resultItem.get("TANKA_GOUKEI");
		if (tankaGoukei != null && !tankaGoukei.isEmpty()) {
			jTextField_AllTanka.setText(tankaGoukei.replaceFirst("^0+", ""));
		}
		/*
		 * �������S���
		 */
		for (int i = 0; i < JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS.length; i++) {
			// edit s.inoue 2010/02/10
			if (tankaHantei.equals(TANKA_HANTEI_KIHON)){
				jComboBox_MadoguchiKihonSyubetu.addItem(JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS[i][1]);
				jComboBox_MadoguchiSyousaiSyubetu.addItem(JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS[i][1]);
				jComboBox_MadoguchiTsuikaSyubetu.addItem(JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS[i][1]);
				// edit s.inoue 2010/02/10
			}else if (tankaHantei.equals(TANKA_HANTEI_DOC)){
				jComboBox_MadoguchiDocSyubetu.addItem(JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS[i][1]);
			}
		}

		if (tankaHantei.equals(TANKA_HANTEI_KIHON)){

			Integer madoguchiFutanKihonSyubetu =Integer.valueOf(resultItem.get("MADO_FUTAN_K_SYUBETU"));
			Integer madoguchiFutanSyousaiSyubetu =Integer.valueOf(resultItem.get("MADO_FUTAN_S_SYUBETU"));
			Integer madoguchiFutanTsuikaSyubetu =Integer.valueOf(resultItem.get("MADO_FUTAN_T_SYUBETU"));

			jComboBox_MadoguchiKihonSyubetu.setSelectedIndex(madoguchiFutanKihonSyubetu);
			jComboBox_MadoguchiSyousaiSyubetu.setSelectedIndex(madoguchiFutanSyousaiSyubetu);
			jComboBox_MadoguchiTsuikaSyubetu.setSelectedIndex(madoguchiFutanTsuikaSyubetu);

			validatedData.setMadoguchiKihonSyubetu(jComboBox_MadoguchiKihonSyubetu.getSelectedIndex());
			validatedData.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiSyousaiSyubetu.getSelectedIndex());
			validatedData.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu.getSelectedIndex());

		}else if (tankaHantei.equals(TANKA_HANTEI_DOC)){
			Integer madoguchiFutanDocSyubetu =
				Integer.valueOf(resultItem.get("MADO_FUTAN_D_SYUBETU"));
			jComboBox_MadoguchiDocSyubetu.setSelectedIndex(
					madoguchiFutanDocSyubetu);
			validatedData.setMadoguchiDocSyubetu(
					jComboBox_MadoguchiDocSyubetu.getSelectedIndex());
		}

		/*
		 * �������S�i��f�����j
		 */
		// edit s.inoue 2010/02/10
		if (tankaHantei.equals(TANKA_HANTEI_KIHON)){
			/* �������S�i��{�I�Ȍ��f�j */
			String madoHutanKihonInput = resultItem.get("MADO_FUTAN_KIHON");
			if (madoHutanKihonInput != null && !madoHutanKihonInput.isEmpty()) {
				jTextField_MadoguchiKihonKinInput.setText(madoHutanKihonInput
						.replaceFirst("^0+", ""));
			}

			/* �������S�i�ڍׂȌ��f�j */
			String madoHutanSyousaiInput = resultItem.get("MADO_FUTAN_SYOUSAI");
			if (madoHutanSyousaiInput != null && !madoHutanSyousaiInput.isEmpty()) {

				jTextField_MadoguchiSyousaiKinInput.setText(madoHutanSyousaiInput
						.replaceFirst("^0+", ""));
			}

			/* �������S�i�ǉ��̌��f�j */
			String madoHutanTsuikaInput = resultItem.get("MADO_FUTAN_TSUIKA");
			if (madoHutanTsuikaInput != null && !madoHutanTsuikaInput.isEmpty()) {
				jTextField_MadoguchiTsuikaKinInput.setText(madoHutanTsuikaInput
						.replaceFirst("^0+", ""));
			}
			// edit s.inoue 2010/02/10
		}else if (tankaHantei.equals(TANKA_HANTEI_DOC)){
			// add ver2 s.inoue 2009/06/18
			/* �������S�i�l�ԃh�b�N�j */
			String madoHutanNingenDocInput = resultItem.get("MADO_FUTAN_DOC");
			if (madoHutanNingenDocInput != null && !madoHutanNingenDocInput.isEmpty()) {
				jTextField_MadoguchiDocKinInput.setText(madoHutanNingenDocInput
						.replaceFirst("^0+", ""));
			}
		}

		/* ���̑��̌��f�ɂ�镉�S�z */
		String madoHutanSonota = resultItem.get("MADO_FUTAN_SONOTA");
		if (madoHutanSonota != null && !madoHutanSonota.isEmpty()) {
			jTextField_MadoguchiSonota.setText(madoHutanSonota.replaceFirst(
					"^0+", ""));
		}

		/*
		 * �ی��ҕ��S�z���
		 */
		// edit s.inoue 2010/02/10
		if (tankaHantei.equals(TANKA_HANTEI_KIHON)){
			/* ��{�I�Ȍ��f */
			String jyougenKihon = resultItem.get("HOKENJYA_FUTAN_KIHON");
			if (jyougenKihon != null && !jyougenKihon.isEmpty()) {
				this.jTextField_hokenjyaJougenKihon.setText(jyougenKihon
						.replaceFirst("^0+", ""));
			}

			/* �ڍׂȌ��f */
			String jyougenSyousai = resultItem.get("HOKENJYA_FUTAN_SYOUSAI");
			if (jyougenSyousai != null && !jyougenSyousai.isEmpty()) {
				this.jTextField_hokenjyaJougenSyosai.setText(jyougenSyousai
						.replaceFirst("^0+", ""));
			}

			/* �ǉ��̌��f */
			String jyougenTsuika = resultItem.get("HOKENJYA_FUTAN_TSUIKA");
			if (jyougenTsuika != null && !jyougenTsuika.isEmpty()) {
				this.jTextField_hokenjyaJougenTsuika.setText(jyougenTsuika
						.replaceFirst("^0+", ""));
			}
			// edit s.inoue 2010/02/10
		}else if (tankaHantei.equals(TANKA_HANTEI_DOC)){
			// edit ver2 s.inoue 2009/07/21
			/* �l�ԃh�b�N�̌��f */
			String jyougenNingenDoc = resultItem.get("HOKENJYA_FUTAN_DOC");
			if (jyougenNingenDoc != null && !jyougenNingenDoc.isEmpty()) {
				this.jTextField_hokenjyaJougenDoc.setText(jyougenNingenDoc
						.replaceFirst("^0+", ""));
			}
		}

		/*
		 * �������S�z�i���ۂ̕��S�z�j
		 */
		// edit s.inoue 2010/02/10
		if (tankaHantei.equals(TANKA_HANTEI_KIHON)){

			/* �������S�i��{�I�Ȍ��f�j */
			String madoHutanKihon = resultItem.get("MADO_FUTAN_KIHON_OUT");
			if (madoHutanKihon != null && !madoHutanKihon.isEmpty()) {
				jTextField_MadoguchiKihonKin.setText(madoHutanKihon.replaceFirst(
						"^0+", ""));
			}

			/* �������S�i�ڍׂȌ��f�j */
			String madoHutanSyousai = resultItem.get("MADO_FUTAN_SYOUSAI_OUT");
			if (madoHutanSyousai != null && !madoHutanSyousai.isEmpty()) {
				jTextField_MadoguchiSyousaiKin.setText(madoHutanSyousai
						.replaceFirst("^0+", ""));
			}

			/* �������S�i�ǉ��̌��f�j */
			String madoHutanTsuika = resultItem.get("MADO_FUTAN_TSUIKA_OUT");
			if (madoHutanTsuika != null && !madoHutanTsuika.isEmpty()) {
				jTextField_MadoguchiTsuikaKin.setText(madoHutanTsuika.replaceFirst(
						"^0+", ""));
			}
			// edit s.inoue 2010/02/10
		}else if (tankaHantei.equals(TANKA_HANTEI_DOC)){
			// add ver2 s.inoue 2009/06/18
			/* �������S�i�l�ԃh�b�N�j */
			String madoHutanNingenDoc = resultItem.get("MADO_FUTAN_DOC_OUT");
			if (madoHutanNingenDoc != null && !madoHutanNingenDoc.isEmpty()) {
				jTextField_MadoguchiNingenDocKin.setText(madoHutanNingenDoc.replaceFirst(
						"^0+", ""));
			}
		}

		/* �������S�z�i���v�j */
		String madoHutanGoukei = resultItem.get("MADO_FUTAN_GOUKEI");
		if (madoHutanGoukei != null && !madoHutanGoukei.isEmpty()) {
			jTextField_AllMadoguchi.setText(madoHutanGoukei.replaceFirst("^0+",
					""));
		}

		/* �������z */
		String seikyuKingaku = resultItem.get("SEIKYU_KINGAKU");
		if (seikyuKingaku != null && !seikyuKingaku.isEmpty()) {
			jTextField_TotalFee.setText(seikyuKingaku.replaceFirst("^0+", ""));
		}

		/*
		 * �������S�����̏ꍇ�A���\���ɏC������B
		 */
		// edit s.inoue 2010/02/10
		if (tankaHantei.equals(TANKA_HANTEI_KIHON)){
			if (validatedData.getMadoguchiKihonSyubetu().equals("3")) {
				jTextField_MadoguchiKihonKinInput.setText(JValidate.to3DigitCode(jTextField_MadoguchiKihonKinInput.getText()));
			}
			if (validatedData.getMadoguchiSyousaiSyubetu().equals("3")) {
				jTextField_MadoguchiSyousaiKinInput.setText(JValidate.to3DigitCode(jTextField_MadoguchiSyousaiKinInput.getText()));
			}
			if (validatedData.getMadoguchiTsuikaSyubetu().equals("3")) {
				jTextField_MadoguchiTsuikaKinInput.setText(JValidate.to3DigitCode(jTextField_MadoguchiTsuikaKinInput.getText()));
			}
			// edit s.inoue 2010/02/10
		}else if (tankaHantei.equals(TANKA_HANTEI_DOC)){
			if (validatedData.getMadoguchiDocSyubetu().equals("3")) {
				jTextField_MadoguchiDocKinInput.setText(JValidate.to3DigitCode(jTextField_MadoguchiDocKinInput.getText()));
			}
		}

		/*
		 * ���W�I�{�^���Ɋւ��ď����ݒ���s��
		 */
		ButtonGroup bt = new ButtonGroup();
		bt.add(jRadioButton_ItakuTankaKobetu);
		bt.add(jRadioButton_ItakuTankaSyudan);
		if (resultItem.get("ITAKU_KBN").equals("1")) {
			jRadioButton_ItakuTankaKobetu.setSelected(true);
		} else if (resultItem.get("ITAKU_KBN").equals("2")) {
			jRadioButton_ItakuTankaSyudan.setSelected(true);
		}
	}

	/**
	 * �l����\������B
	 */
	private void initializeKozinDataDisplay(Hashtable<String, String> resultItem) {
		/*
		 * �㕔�p�l���Ƀf�[�^��\��������
		 */
		jTextField_Jyusinken_ID.setText(resultItem.get("JYUSHIN_SEIRI_NO"));
		jTextField_Name.setText(resultItem.get("KANANAME"));
		jTextField_Birthday.setText(resultItem.get("BIRTHDAY"));
		jTextField_KensaDate.setText(resultItem.get("KENSA_NENGAPI"));
		// del s.inoue 2009/08/27
		// jTextField_ToHL7Date.setText(resultItem.get("HENKAN_NITIJI"));

		jTextField_hihokenjasyotouKigou.setText(validatedData
				.getHihokenjyasyo_Kigou());
		jTextField_hihokenjasyotouBangou.setText(validatedData
				.getHihokenjyasyo_No());

		// ���ʃR�[�h�l�𖼏̂ɕύX����
		if (resultItem.get("SEX").equals("1")) {// �j��
			jTextField_Sex.setText("�j��");

		} else if (resultItem.get("SEX").equals("2")) {// ����
			jTextField_Sex.setText("����");
		}
	}

	/**
	 * ���σf�[�^���擾����B
	 */
	private ArrayList<Hashtable<String, String>> getKesaiData(String kensaDate) {
		StringBuffer buffer = new StringBuffer();
		// edit s.inoue 2009/10/08
		// �l�ԃh�b�N������
		buffer.append("SELECT ");
		buffer.append("HOKENJYA.TANKA_HANTEI, ");
		buffer.append("KESAI.TKIKAN_NO, ");
		buffer.append("KESAI.KENSA_NENGAPI, ");
		buffer.append("KESAI.SEIKYU_KBN, ");
		buffer.append("KESAI.SYUBETU_CD, ");
		buffer.append("KESAI.HKNJANUM, ");
		buffer.append("KESAI.HIHOKENJYASYO_KIGOU, ");
		buffer.append("KESAI.HIHOKENJYASYO_NO, ");
		buffer.append("KESAI.ITAKU_KBN, ");
		buffer.append("KESAI.TANKA_KIHON, ");
		buffer.append("KESAI.HINKETU_CD, ");
		buffer.append("KESAI.TANKA_HINKETU, ");
		buffer.append("KESAI.SINDENZU_CD, ");
		buffer.append("KESAI.TANKA_SINDENZU, ");
		buffer.append("KESAI.GANTEI_CD, ");
		buffer.append("KESAI.TANKA_GANTEI, ");
		buffer.append("KESAI.TANKA_NINGENDOC, ");
		buffer.append("KESAI.MADO_FUTAN_K_SYUBETU, ");
		buffer.append("KESAI.MADO_FUTAN_KIHON, ");
		buffer.append("KESAI.MADO_FUTAN_KIHON_OUT, ");
		buffer.append("KESAI.MADO_FUTAN_S_SYUBETU, ");
		buffer.append("KESAI.MADO_FUTAN_SYOUSAI, ");
		buffer.append("KESAI.MADO_FUTAN_SYOUSAI_OUT, ");
		buffer.append("KESAI.MADO_FUTAN_T_SYUBETU, ");
		buffer.append("KESAI.MADO_FUTAN_TSUIKA, ");
		buffer.append("KESAI.MADO_FUTAN_TSUIKA_OUT, ");
		buffer.append("KESAI.MADO_FUTAN_D_SYUBETU, ");
		buffer.append("KESAI.MADO_FUTAN_DOC, ");
		buffer.append("KESAI.MADO_FUTAN_DOC_OUT, ");
		buffer.append("KESAI.TANKA_GOUKEI, ");
		buffer.append("KESAI.MADO_FUTAN_GOUKEI, ");
		buffer.append("KESAI.SEIKYU_KINGAKU, ");
		buffer.append("KESAI.UPDATE_TIMESTAMP, ");
		buffer.append("KESAI.SIHARAI_DAIKOU_BANGO, ");
		buffer.append("KESAI.NENDO, ");
		buffer.append("KESAI.UKETUKE_ID, ");
		buffer.append("KESAI.MADO_FUTAN_SONOTA, ");
		buffer.append("KESAI.HOKENJYA_FUTAN_KIHON, ");
		buffer.append("KESAI.HOKENJYA_FUTAN_SYOUSAI, ");
		buffer.append("KESAI.HOKENJYA_FUTAN_TSUIKA, ");
		buffer.append("KESAI.HOKENJYA_FUTAN_DOC, ");
		buffer.append("KOJIN.PTNUM, ");
		buffer.append("KOJIN.JYUSHIN_SEIRI_NO, ");
		buffer.append("KOJIN.YUKOU_KIGEN, ");
		buffer.append("KOJIN.HKNJANUM, ");
		buffer.append("KOJIN.HIHOKENJYASYO_KIGOU, ");
		buffer.append("KOJIN.HIHOKENJYASYO_NO, ");
		buffer.append("KOJIN.NAME, ");
		buffer.append("KOJIN.KANANAME, ");
		buffer.append("KOJIN.NICKNAME, ");
		buffer.append("KOJIN.BIRTHDAY, ");
		buffer.append("KOJIN.SEX, ");
		buffer.append("KOJIN.HOME_POST, ");
		buffer.append("KOJIN.HOME_ADRS, ");
		buffer.append("KOJIN.HOME_BANTI, ");
		buffer.append("KOJIN.HOME_TEL1, ");
		buffer.append("KOJIN.KEITAI_TEL, ");
		buffer.append("KOJIN.FAX, ");
		buffer.append("KOJIN.EMAIL, ");
		buffer.append("KOJIN.KEITAI_EMAIL, ");
		buffer.append("KOJIN.KEIYAKU_TORIMATOME, ");
		buffer.append("KOJIN.KOUHUBI, ");
		buffer.append("KOJIN.SIHARAI_DAIKOU_BANGO ");
		buffer.append("FROM T_KESAI_WK AS KESAI, ");
		buffer.append("T_KOJIN AS KOJIN, ");
		buffer.append("T_HOKENJYA AS HOKENJYA ");
		buffer.append(" WHERE ");
		buffer.append(" KESAI.UKETUKE_ID = KOJIN.UKETUKE_ID ");
		buffer.append(" AND HOKENJYA.HKNJANUM = KESAI.HKNJANUM");
		// add s.inoue 2010/01/15
		buffer.append(" AND HOKENJYA.YUKOU_FLG = '1' ");
		buffer.append(" AND TKIKAN_NO = ");
		buffer.append(JQueryConvert.queryConvert(JApplication.kikanNumber));
		buffer.append(" AND KOJIN.UKETUKE_ID = ");
		buffer.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		buffer.append(" AND KESAI.KENSA_NENGAPI = ");
		buffer.append(JQueryConvert.queryConvert(kensaDate));

		String query = buffer.toString();

		ArrayList<Hashtable<String, String>> results = null;
		try {
			results = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * �ی��Ҕԍ����擾����B
	 */
	private String getHokenjaNumber() {
		// ��ی��ҏؓ��L���Ɣԍ�����ی��҂�����
		String query = "SELECT HKNJANUM " + "FROM T_KOJIN " + "WHERE "
				+ "UKETUKE_ID = "
				+ JQueryConvert.queryConvert(validatedData.getUketuke_id());

		ArrayList<Hashtable<String, String>> results = null;
		try {
			results = JApplication.kikanDatabase.sendExecuteQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		String hokenjaNum = results.get(0).get("HKNJANUM");
		return hokenjaNum;
	}

	public boolean validateAsRegisterPushed(JInputKessaiDataFrameData data) {
		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * �����敪�ɂ�錟�����ڃ`�F�b�N[�������`�F�b�N]
	 */
	public static String[] isNotExistKensaKoumoku(
			String hokenjyaNumber,
			String uketukeId,
			String kensaDate,
			String seikyuKbn
			)
	{
			String[] retMess = null;

			boolean hanteiHisu = false;

			// ��{�I�Ȍ��f,�ڍׂȌ��f,�ǉ����f����,�l�ԃh�b�N
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select master.KOUMOKU_CD,master.KOUMOKU_NAME,master.HISU_FLG,sonota.KEKA_TI,master.TANKA_KENSIN,sonota.JISI_KBN ");
			buffer.append(" from T_KENSAKEKA_SONOTA sonota ");
			buffer.append(" left join T_KENSHINMASTER master ");
			buffer.append(" on sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
			buffer.append(" where master.HKNJANUM = ?");
			buffer.append(" and sonota.UKETUKE_ID = ?");
			buffer.append(" and sonota.KENSA_NENGAPI = ?");

			String query = buffer.toString();
			String[] params = { hokenjyaNumber,uketukeId,kensaDate };

			ArrayList<Hashtable<String, String>> result = null;
			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);
			}catch(SQLException ex){
				ex.printStackTrace();
			}

			for (int ii=0;ii < result.size(); ii++) {

				Hashtable<String, String> item =
					new Hashtable<String, String>();

				item  = result.get(ii);

				String hisuFlg = item.get("HISU_FLG");
				String jisiKbn = item.get("JISI_KBN");
				String kekaTi = item.get("KEKA_TI");

				String koumokuCd = item.get("KOUMOKU_CD");

				hanteiHisu = false;

				// ��O�̍���(����[�Ɋւ�鍀��)
				String KoumokuHd = koumokuCd.substring(0, 3);
				if(!KoumokuHd.equals("9N7")&&
						!koumokuCd.equals(CODE_SEIKATU_KAIZEN) &&
						!koumokuCd.equals(CODE_HOKEN_SHIDOU) &&
						!koumokuCd.equals(CODE_SAIKETSU_ZIKAN) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_1) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_2) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_3) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_4)){


				//String tanka = item.get("TANKA_KENSIN");
				// �o�^�Ώۂ̃t���O�`�F�b�N
				if (jisiKbn.equals("1")){
					if (!kekaTi.equals("")){
						hanteiHisu = true;
					}
				}else if (jisiKbn.equals("2") ||
						jisiKbn.equals("0")){
					if (kekaTi.equals("")){
						hanteiHisu = true;
					}
				}

					if (hanteiHisu){
						// �������m�F�p
						if (seikyuKbn.equals(JApplication.SEIKYU_KBN_BASE)){
							if (!hisuFlg.equals(JApplication.HISU_FLG_BASE)){
								retMess = new String[2];
								retMess[0] = item.get("KOUMOKU_CD");
								retMess[1] = item.get("KOUMOKU_NAME");
								break;
							}
						}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_SYOSAI)){
							if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
								retMess = new String[2];
								retMess[0] = item.get("KOUMOKU_CD");
								retMess[1] = item.get("KOUMOKU_NAME");
								break;
							}
						}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_TUIKA)){
							if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
								retMess = new String[2];
								retMess[0] = item.get("KOUMOKU_CD");
								retMess[1] = item.get("KOUMOKU_NAME");
								break;
							}
						}
					}
				}
			}

		return retMess;
	}

	/**
	 * �����敪�ɑΉ����錟�����ڃ`�F�b�N[���݃`�F�b�N]
	 */
	public static String[] isExistKensaKoumoku(
			String hokenjyaNumber,
			String uketukeId,
			String kensaDate,
			String seikyuKbn
			)
	{
			String[] retMess = null;

			boolean hanteiBase = false;
			boolean hanteiSyosai = false;
			boolean hanteiTuika = false;
			int intBaseCnt = 0;
			int intSyosaiCnt = 0;
			int intTuikaCnt = 0;
			int intAllCnt = 0;

			// ��{�I�Ȍ��f,�ڍׂȌ��f,�ǉ����f����,�l�ԃh�b�N
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select master.KOUMOKU_CD,master.KOUMOKU_NAME,master.HISU_FLG,sonota.KEKA_TI,master.TANKA_KENSIN,sonota.JISI_KBN ");
			buffer.append(" from T_KENSAKEKA_SONOTA sonota ");
			buffer.append(" left join T_KENSHINMASTER master ");
			buffer.append(" on sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
			buffer.append(" where master.HKNJANUM = ?");
			buffer.append(" and sonota.UKETUKE_ID = ?");
			buffer.append(" and sonota.KENSA_NENGAPI = ?");

			String query = buffer.toString();
			String[] params = { hokenjyaNumber,uketukeId,kensaDate };

			ArrayList<Hashtable<String, String>> result = null;
			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);
			}catch(SQLException ex){
				ex.printStackTrace();
			}

			for (int ii=0;ii < result.size(); ii++) {

				Hashtable<String, String> item =
					new Hashtable<String, String>();

				item  = result.get(ii);

				String hisuFlg = item.get("HISU_FLG");
				String jisiKbn = item.get("JISI_KBN");
				String kekaTi = item.get("KEKA_TI");

				String koumokuCd = item.get("KOUMOKU_CD");

				// ��O�̍���(����[�Ɋւ�鍀��)
				String KoumokuHd = koumokuCd.substring(0, 3);
				if(!KoumokuHd.equals("9N7")&&
						!koumokuCd.equals(CODE_HOKEN_SHIDOU) &&
						!koumokuCd.equals(CODE_SEIKATU_KAIZEN) &&
						!koumokuCd.equals(CODE_SAIKETSU_ZIKAN) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_1) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_2) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_3) &&
						!koumokuCd.equals(CODE_ISHINOHANDAN_4)){


				if ((jisiKbn.equals("1") && !kekaTi.equals("")) ||
						((jisiKbn.equals("2") || jisiKbn.equals("0"))
								&& kekaTi.equals(""))){

						if (seikyuKbn.equals(JApplication.SEIKYU_KBN_BASE)){
							if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
								hanteiBase = true;intBaseCnt +=1;
							}
						}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_SYOSAI)){
							if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
								hanteiBase = true;
							}else
							if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
								hanteiSyosai = true;
							}
							if (hanteiBase && hanteiSyosai){
								intSyosaiCnt +=1;
							}
						}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_TUIKA)){
							if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
								hanteiBase = true;
							}else
							if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
								hanteiTuika = true;
							}
							if (hanteiBase && hanteiTuika){
								intTuikaCnt +=1;
							}
						}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_SYOSAI_TUIKA) ||
								seikyuKbn.equals(JApplication.SEIKYU_KBN_DOC)){
							if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
								hanteiBase = true;
							}else
							if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
								hanteiSyosai = true;
							}else
							if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
								hanteiTuika = true;
							}
							if (hanteiBase && hanteiSyosai && hanteiTuika){
								intAllCnt +=1;
							}
						}
					}
				}
			}
			if ((intBaseCnt == 0) &&
					(intSyosaiCnt == 0) &&
						(intTuikaCnt == 0) &&
						 (intAllCnt == 0)){
				retMess = new String[2];
			}
		return retMess;
	}
	public void register() {
		String sex = jTextField_Sex.getText();
		String sexCode = "";
		boolean exMessageFlg = false;
		if (sex.equals("�j��")) {
			sexCode = "1";
		} else if (sex.equals("����")) {
			sexCode = "2";
		}

		String hihokenjyasyoNumber = validatedData.getHihokenjyasyo_No();
		if (hihokenjyasyoNumber == null || hihokenjyasyoNumber.isEmpty()) {
			JErrorMessage.show("M3401", this);

		}

		// edit s.inoue 2010/02/16 doc or kihon�I��
		boolean kihoninfo =
			validatedData.setJyusinken_ID(jTextField_Jyusinken_ID.getText())
			&& validatedData.setName(jTextField_Name.getText())
			&& validatedData.setBirthday(jTextField_Birthday.getText())
			&& validatedData.setSex(sexCode)
			&& validatedData.setKensaDate(jTextField_KensaDate.getText())
			&& validatedData.setMadoguchiSonota(this.jTextField_MadoguchiSonota.getText())
			&& validatedData.setSeikyuKubun(this.jComboBox_SeikyuKubun.getSelectedItem().toString())
			&& validatedData.setAllTanka(jTextField_AllTanka.getText())
			&& validatedData.setAllMadoguchi(jTextField_AllMadoguchi.getText())
			&& validatedData.setTotalFee(jTextField_TotalFee.getText());

		boolean tankaSelect = false;
		if (existsDoc){
			tankaSelect =
				validatedData.setMadoguchiDocKin(jTextField_MadoguchiDocKinInput.getText())
				&& validatedData.setHokenjyaJougenDoc(this.jTextField_hokenjyaJougenDoc.getText())
				&& validatedData.setDocTanka(jTextField_DocTanka.getText());
		}else{
			tankaSelect =
				validatedData.setKihonTanka(jTextField_KihonTanka.getText())
				&& validatedData.setHinketuTanka(jTextField_HinketuTanka.getText())
				&& validatedData.setSindenzuTanka(jTextField_SindenzuTanka.getText())
				&& validatedData.setGanteiTanka(jTextField_GanteiTanka.getText())
				&& validatedData.setMadoguchiKihonKin(jTextField_MadoguchiKihonKinInput.getText())
				&& validatedData.setMadoguchiShousaiKin(jTextField_MadoguchiSyousaiKinInput.getText())
				&& validatedData.setMadoguchiTuikaKin(jTextField_MadoguchiTsuikaKinInput.getText())
				&& validatedData.setHokenjyaJougenKihon(this.jTextField_hokenjyaJougenKihon.getText())
				&& validatedData.setHokenjyaJougenSyousai(this.jTextField_hokenjyaJougenSyosai.getText())
				&& validatedData.setHokenjyaJougenTsuika(this.jTextField_hokenjyaJougenTsuika.getText())
				&& validatedData.setHinketsuCode(jTextField_HinketsuCode.getText())
				&& validatedData.setSindenzuCode(jTextField_SindenzuCode.getText())
				&& validatedData.setGanteiCode(jTextField_GanteiCode.getText());
		}

// edit s.inoue 2010/02/16
//		if (validatedData.setJyusinken_ID(jTextField_Jyusinken_ID.getText())
//				&& validatedData.setName(jTextField_Name.getText())
//				&& validatedData.setBirthday(jTextField_Birthday.getText())
//				&& validatedData.setSex(sexCode)
//				&& validatedData.setKensaDate(jTextField_KensaDate.getText())
//				// del s.inoue 2009/08/27
//				// && validatedData.setToHL7Date(jTextField_ToHL7Date.getText())
//				&& validatedData.setHinketsuCode(jTextField_HinketsuCode.getText())
//				&& validatedData.setSindenzuCode(jTextField_SindenzuCode.getText())
//				&& validatedData.setGanteiCode(jTextField_GanteiCode.getText())
//				&& validatedData.setKihonTanka(jTextField_KihonTanka.getText())
//				&& validatedData.setHinketuTanka(jTextField_HinketuTanka.getText())
//				&& validatedData.setSindenzuTanka(jTextField_SindenzuTanka.getText())
//				&& validatedData.setGanteiTanka(jTextField_GanteiTanka.getText())
//				&& validatedData.setMadoguchiKihonKin(jTextField_MadoguchiKihonKinInput.getText())
//				&& validatedData.setMadoguchiShousaiKin(jTextField_MadoguchiSyousaiKinInput.getText())
//				&& validatedData.setMadoguchiTuikaKin(jTextField_MadoguchiTsuikaKinInput.getText())
//				&& validatedData.setMadoguchiDocKin(jTextField_MadoguchiDocKinInput.getText())
//				&& validatedData.setMadoguchiSonota(this.jTextField_MadoguchiSonota.getText())
//				&& validatedData.setHokenjyaJougenKihon(this.jTextField_hokenjyaJougenKihon.getText())
//				&& validatedData.setHokenjyaJougenSyousai(this.jTextField_hokenjyaJougenSyosai.getText())
//				&& validatedData.setHokenjyaJougenTsuika(this.jTextField_hokenjyaJougenTsuika.getText())
//				&& validatedData.setHokenjyaJougenDoc(this.jTextField_hokenjyaJougenDoc.getText())
//				&& validatedData.setSeikyuKubun(this.jComboBox_SeikyuKubun.getSelectedItem().toString())
//
//				&& validatedData.setAllTanka(jTextField_AllTanka.getText())
//				&& validatedData.setDocTanka(jTextField_DocTanka.getText())
//				&& validatedData.setAllMadoguchi(jTextField_AllMadoguchi.getText())
//				&& validatedData.setTotalFee(jTextField_TotalFee.getText())) {
		if (tankaSelect && kihoninfo) {
			String[] registKensa= isNotExistKensaKoumoku(
					validatedData.getHokenjyaNum(),
					validatedData.getUketuke_id(),
					validatedData.getKensaDate(),
					validatedData.getSeikyuKubun()
					);

			if (registKensa != null){
				JErrorMessage.show("M3635", this);
				exMessageFlg = true;
			}

			String[] existKensa= JInputKessaiDataFrameCtrl.isExistKensaKoumoku(
					validatedData.getHokenjyaNum(),
					validatedData.getUketuke_id(),
					validatedData.getKensaDate(),
					validatedData.getSeikyuKubun());

			if (!exMessageFlg &&
					existKensa != null){
				JErrorMessage.show("M3636", this);
			}

			// edit ver2 s.inoue 2009/08/24
			// timestamp�擾
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			if (validateAsRegisterPushed(validatedData)) {
				try {
					JApplication.kikanDatabase.Transaction();
				} catch (SQLException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}

				// edit ver2 s.inoue 2009/08/06
				// T_KESAI�e�[�u���ɑ΂��X�V���s��
				StringBuilder query = new StringBuilder();
				query.append("UPDATE T_KESAI_WK SET SEIKYU_KBN = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getSeikyuKubun()));
				query.append("ITAKU_KBN = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getItakuTankaKubun()));
				query.append("HINKETU_CD = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuCode()));
				query.append("SINDENZU_CD = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuCode()));
				query.append("GANTEI_CD = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getGanteiCode()));
				query.append("TANKA_KIHON = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getKihonTanka()));
				query.append("TANKA_HINKETU = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getHinketuTanka()));
				query.append("TANKA_SINDENZU = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuTanka()));
				query.append("TANKA_GANTEI = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getGanteiTanka()));
				query.append("TANKA_NINGENDOC = ");
				query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(validatedData.getNingenDocTanka())));
				query.append("MADO_FUTAN_KIHON = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiKihonKin()));
				query.append("MADO_FUTAN_SYOUSAI = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiSyousaiKin()));
				query.append("MADO_FUTAN_SONOTA = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiSonota()));
				query.append("HOKENJYA_FUTAN_KIHON = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaJougenKihon()));
				query.append("HOKENJYA_FUTAN_SYOUSAI = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaJougenSyousai()));
				query.append("HOKENJYA_FUTAN_TSUIKA = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaJougenTsuika()));
				query.append("HOKENJYA_FUTAN_DOC = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaJougenDoc()));
				query.append("MADO_FUTAN_TSUIKA = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiTuikaKin()));
				query.append("MADO_FUTAN_DOC = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiDocKin()));
				query.append("MADO_FUTAN_K_SYUBETU = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiKihonSyubetu()));
				query.append("MADO_FUTAN_S_SYUBETU = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiSyousaiSyubetu()));
				query.append("MADO_FUTAN_T_SYUBETU = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiTsuikaSyubetu()));
				query.append("MADO_FUTAN_D_SYUBETU = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getMadoguchiDocSyubetu()));
				query.append("TANKA_GOUKEI = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getAllTanka()));
				query.append("MADO_FUTAN_GOUKEI = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getAllMadoguchi()));
				query.append("SEIKYU_KINGAKU = ");
				query.append(JQueryConvert.queryConvertAppendComma(validatedData.getTotalFee()));
				query.append("MADO_FUTAN_KIHON_OUT = ");
				query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(output.getMadoFutanKihon())));
				query.append("MADO_FUTAN_SYOUSAI_OUT = ");
				query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(output.getMadoFutanSyousai())));
				query.append("MADO_FUTAN_TSUIKA_OUT = ");
				query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(output.getMadoFutanTsuika())));
				query.append("MADO_FUTAN_DOC_OUT = ");
				query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(output.getMadoFutanDoc())));
				query.append("UPDATE_TIMESTAMP = ");
				query.append(JQueryConvert.queryConvert(stringTimeStamp));
				query.append(" WHERE TKIKAN_NO = ");
				query.append(JQueryConvert.queryConvert(JApplication.kikanNumber));
				query.append(" AND UKETUKE_ID = ");
				query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
				query.append(" AND KENSA_NENGAPI = ");
				query.append(JQueryConvert.queryConvert(validatedData.getKensaDate()));

				try {
					JApplication.kikanDatabase.sendNoResultQuery(query.toString());
				} catch (SQLException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}

				// T_KESAI_SYOUSAI�e�[�u���ɑ΂��X�V���s��
				for (int i = 0; i < table.getRowCount(); i++) {
					// edit s.inoue 2009/11/17
					// Object tanka = table.getData(i, 2);
					Object tanka = table.getData(i, COLUMN_INDEX_TANKA);
					String tankaString = null;
					if (tanka instanceof Integer) {
						tankaString = ((Integer) tanka).toString();
					} else if (tanka instanceof String) {
						tankaString = (String) tanka;
					}

					// edit s.inoue 2009/11/17
					// String code = (String) table.getData(i, 0);
					String code = (String) table.getData(i, COLUMN_INDEX_KOUMOKU_CD);

					// �ꎞ�e�[�u���֓o�^ �L�[���s���Ȉ׏C��
					StringBuilder querySyousai = new StringBuilder();

					querySyousai.append("UPDATE T_KESAI_SYOUSAI_WK ");
					querySyousai.append(" SET TANKA_TUIKA_KENSIN = ");
					querySyousai.append(JQueryConvert.queryConvertAppendComma(tankaString));
					querySyousai.append(" UPDATE_TIMESTAMP = ");
					querySyousai.append(JQueryConvert.queryConvert(stringTimeStamp));

					querySyousai.append(" WHERE TKIKAN_NO = ");
					querySyousai.append(JQueryConvert.queryConvert(JApplication.kikanNumber));
					querySyousai.append(" AND UKETUKE_ID = ");
					querySyousai.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
					querySyousai.append(" AND KENSA_NENGAPI = ");
					querySyousai.append(JQueryConvert.queryConvert(validatedData.getKensaDate()));
					querySyousai.append(" AND TUIKA_KENSIN_CD = ");
					querySyousai.append(JQueryConvert.queryConvert(code));

					try {
						JApplication.kikanDatabase.sendNoResultQuery(querySyousai.toString());
					} catch (SQLException f) {
						f.printStackTrace();
						logger.error(f.getMessage());
					}
				}
				String queryTokutei ="";

				// �������ʓ���e�[�u���ɑ΂��ēo�^����
				queryTokutei = String.format("UPDATE "
						+ "T_KENSAKEKA_TOKUTEI " + "SET "
						+ "SEIKYU_KBN = '%s' "
						+ "WHERE " + "UKETUKE_ID = %s "
						+ "AND " + "KENSA_NENGAPI = %s",
						validatedData.getSeikyuKubun(),
						validatedData.getUketuke_id(),
						validatedData.getKensaDate());
				try{
					JApplication.kikanDatabase.sendNoResultQuery(queryTokutei);
				} catch (SQLException f) {
					f.printStackTrace();
					logger.error(f.getMessage());
				}

				// �W�v�������s��
				if (JSyuukeiProcess.RunProcess(data) == false) {
					JErrorMessage.show("M3401", this);
				}

				// edit s.inoue 2009/09/16
				// �R�~�b�g�����i�ی��ҏ�����j�ړ�
				try {
					JApplication.kikanDatabase.Commit();
					// edit s.inoue 2009/12/11
					dispose();
				} catch (SQLException f) {
					f.printStackTrace();
					logger.error(f.getMessage());
					try {
						JApplication.kikanDatabase.rollback();
					} catch (SQLException g) {
						g.printStackTrace();
						logger.error(f.getMessage());
					}
				}
			}
		}
	}

	public void pushedRegisterButton(ActionEvent e) {
		register();
	}

	public void pushedCancelButton(ActionEvent e) {
		dispose();
	}

	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * �u�Čv�Z�v�{�^�������C�x���g�n���h��
	 *
	 * Modified 2008/04/01 �ጎ �v�Z�����s������̂��߁A�R�����g�폜�ƃ��t�@�N�^�����O���s�Ȃ��B
	 */
	public void pushedReCalcButton() {
		// /* �������S���z�i���ۂ̕��S�z�j���Čv�Z���A�\������B */

		if (validateInputData()) {
			if (validateAsRegisterPushed(validatedData)) {
				long syousaiSum = 0;

				// �ڍ׌��f�̒��Ō������s�������̂Ɋւ��Ă������Z����
				String hokenjyaNum = validatedData.getHokenjyaNum();
				String uketuke_id = validatedData.getUketuke_id();
				String kensaDate = validatedData.getKensaDate();

				/* �n�������Ɍ��ʒl������ꍇ�́A���Z����B */
				boolean existHinketsuKekkati = JKessaiProcess
						.isExistSyousaiKensaKoumoku(hokenjyaNum, uketuke_id,
								kensaDate, GROUP_CODE_HINKETSU);
				if (existHinketsuKekkati) {
					String hinketuTanka = validatedData.getHinketuTanka();
					try {
						syousaiSum += Integer.valueOf(hinketuTanka);

					} catch (NumberFormatException e) {
						/* �������Ȃ� */
					}
				} else {
				}

				boolean existGanteiKekkati = JKessaiProcess
						.isExistSyousaiKensaKoumoku(hokenjyaNum, uketuke_id,
								kensaDate, GROUP_CODE_GANTEI);
				/* ��ꌟ���Ɍ��ʒl������ꍇ�́A���Z����B */
				if (existGanteiKekkati) {
					String ganteiTanka = validatedData.getGanteiTanka();
					try {
						syousaiSum += Integer.valueOf(ganteiTanka);

					} catch (NumberFormatException e) {
						/* �������Ȃ� */
					}
				} else {
				}

				boolean existShindenzuKekkati = JKessaiProcess
						.isExistSyousaiKensaKoumoku(hokenjyaNum, uketuke_id,
								kensaDate, GROUP_CODE_SHINDENZU);
				/* �S�d�}�Ɍ��ʒl������ꍇ�́A���Z����B */
				if (existShindenzuKekkati) {
					String sindenzuTanka = validatedData.getSindenzuTanka();
					try {
						syousaiSum += Integer.valueOf(sindenzuTanka);

					} catch (NumberFormatException e) {
						/* �������Ȃ� */
					}
				} else {
				}

				boolean existsSyousai = false;
				if (existHinketsuKekkati || existGanteiKekkati
						|| existShindenzuKekkati) {
					existsSyousai = true;
				} else {
				}

				/* �ǉ����f�̋��z�����Z���� */
				long tsuikaSum = 0;
				boolean existsTsuika = false;
				// del s.inoue 2010/02/16
				// boolean existsDoc = false;
				int rowCount = table.getRowCount();

				// add ver2 s.inoue 2009/06/18 �l�ԃh�b�N
				long ningenDocSetPrice = 0;
				String ningenDocTanka = validatedData.getNingenDocTanka();
				try {
					ningenDocSetPrice= Integer.valueOf(ningenDocTanka);
				} catch (NumberFormatException e) {
					/* �������Ȃ� */
				}

				// edit s.inoue 2010/01/15
				// �l�ԃh�b�N���ǂ����̔��f
				String hantei = "";
				Hashtable<String, String> itemHantei = new Hashtable<String, String>();
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT TANKA_HANTEI FROM T_HOKENJYA");
				sb.append(" WHERE HKNJANUM = ");
				sb.append(JQueryConvert.queryConvert(hokenjyaNum));
				sb.append(" AND YUKOU_FLG = '1' ");
				// String Query = new String("SELECT COUNT(TANKA_NINGENDOC) DOC_CNT
				// FROM T_HOKENJYA WHERE TANKA_NINGENDOC > 0 AND HKNJANUM = "
				//		+ JQueryConvert.queryConvert(hokenjyaNum));

				try {
					itemHantei = JApplication.kikanDatabase.sendExecuteQuery(sb.toString()).get(0);
					hantei =itemHantei.get("TANKA_HANTEI");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				// edit s.inoue 2010/01/15
				if (hantei.equals(TANKA_HANTEI_DOC)){
					jTextField_MadoguchiTsuikaKinInput.setEditable(false);
					jComboBox_MadoguchiTsuikaSyubetu.setEnabled(false);
					// edit ver2 s.inoue 2009/07/21
					jTextField_MadoguchiDocKinInput.setEditable(true);
					jComboBox_MadoguchiDocSyubetu.setEnabled(true);

					existsDoc = true;
				}else{
					if (rowCount > 0) {
						existsTsuika = true;

						for (int i = 0; i < rowCount; i++) {
							try {
								// edit s.inoue 2009/11/17
								// Object tsuikaTankaObject = table.getData(i, 2);
								Object tsuikaTankaObject = table.getData(i, COLUMN_INDEX_TANKA);

								try {
									if (tsuikaTankaObject instanceof Integer) {
										tsuikaSum += ((Integer) tsuikaTankaObject).intValue();
									} else if (tsuikaTankaObject instanceof Long) {
										tsuikaSum += ((Long) tsuikaTankaObject).longValue();
									} else if (tsuikaTankaObject instanceof String) {
										tsuikaSum += Long.valueOf((String) tsuikaTankaObject);
									}
								} catch (NumberFormatException e) {
									/* �������Ȃ� */
								}
							} catch (RuntimeException e) {
								e.printStackTrace();
								JErrorMessage.show("M4752", this);
								return;
							}
						}

						jTextField_MadoguchiTsuikaKinInput.setEditable(true);
						jComboBox_MadoguchiTsuikaSyubetu.setEnabled(true);
					} else {
						// del s.inoue 2010/02/16
						// jTextField_MadoguchiTsuikaKinInput.setEditable(false);
						// jComboBox_MadoguchiTsuikaSyubetu.setEnabled(false);
					}

					// edit ver2 s.inoue 2009/08/28
					// �l�ԃh�b�N�͎g�p���Ȃ�
					jTextField_DocTanka.setEditable(false);
					jTextField_MadoguchiDocKinInput.setEditable(false);
					jComboBox_MadoguchiDocSyubetu.setEnabled(false);
				}

				/* ���͌��σf�[�^���쐬���擾����B */
				JKessaiDataInput data = this.getInputData(syousaiSum, tsuikaSum, ningenDocSetPrice);
				data.setExistsSyousai(existsSyousai);
				data.setExistsTsuika(existsTsuika);
				// add ver2 s.inoue 2009/06/18
				data.setExistsDoc(existsDoc);

				/*
				 * ���Ϗ���
				 */
				output = JKessai.Kessai(data, this.data.get(this.selectedDataIndex));


				// edit ver2 s.inoue 2009/07/21
				if (!existsDoc){
					jTextField_MadoguchiKihonKin.setText(String.valueOf(output.getMadoFutanKihon()));
				}

				long tsuikaTankaGoukei = output.getTsuikaTankaGoukei();
				if (existsTsuika) {
					jTextField_TsuikaTankaSum.setText(String.valueOf(tsuikaTankaGoukei));
				}

				/* �ڍׂȌ��f */
				if (existsSyousai) {
					jTextField_MadoguchiSyousaiKin.setText(String.valueOf(output.getMadoFutanSyousai()));
				} else {
					jTextField_MadoguchiSyousaiKin.setText("");
					jTextField_MadoguchiSyousaiKinInput.setText("");
					jLabel_UnitMadoSyosai.setText("");
				}

				/* �ǉ��̌��f */
				if (existsTsuika) {
					jTextField_MadoguchiTsuikaKin.setText(String.valueOf(output.getMadoFutanTsuika()));
					// edit ver2 s.inoue 2009/07/21
					jTextField_TsuikaTankaSum.setText(String.valueOf(tsuikaSum));
				} else {
					jTextField_MadoguchiTsuikaKin.setText("");
					jTextField_MadoguchiTsuikaKinInput.setText("");
					jLabel_UnitMadoTsuika.setText("");
				}

				// add ver2 s.inoue 2009/07/09
				if (existsDoc){
					jTextField_MadoguchiNingenDocKin.setText(String.valueOf(output.getMadoFutanDoc()));
					jTextField_DocTanka.setText(String.valueOf(ningenDocTanka));

					jTextField_AllTanka.setText(String.valueOf(output.getDocTankaGoukei()));
					jTextField_AllMadoguchi.setText(String.valueOf(output.getMadoFutanDoc()));
				}else{
					// edit ver2 s.inoue 2009/08/28
					jTextField_MadoguchiNingenDocKin.setText("");
					jTextField_MadoguchiDocKinInput.setText("");
					jLabel_UnitMadoNingenDoc.setText("");

					jTextField_AllTanka.setText(String.valueOf(output.getTankaGoukei()));
					jTextField_AllMadoguchi.setText(String.valueOf(output.getMadoFutanGoukei()));
				}
				jTextField_TotalFee.setText(String.valueOf(output.getSeikyuKingaku()));
			}
		}
	}

	private boolean validateInputData() {
		String sex = jTextField_Sex.getText();
		String sexCode = "";
		if (sex.equals("�j��")) {
			sexCode = "1";
		} else if (sex.equals("����")) {
			sexCode = "2";
		}

		 /* �l��� */
		boolean kihoninfo = validatedData.setJyusinken_ID(jTextField_Jyusinken_ID.getText())
				&& validatedData.setName(jTextField_Name.getText())
				&& validatedData.setBirthday(jTextField_Birthday.getText())
				&& validatedData.setSex(sexCode)
				&& validatedData.setKensaDate(jTextField_KensaDate.getText())
				// del s.inoue 2009/08/27
				// && validatedData.setToHL7Date(jTextField_ToHL7Date.getText())
				&& validatedData.setHihokenjyasyo_Kigou(jTextField_hihokenjasyotouKigou.getText())
				&& validatedData.setHihokenjyasyo_No(jTextField_hihokenjasyotouBangou.getText())

				/* ���Ϗ�� */
				&& validatedData.setHinketsuCode(jTextField_HinketsuCode.getText())
				&& validatedData.setSindenzuCode(jTextField_SindenzuCode.getText())
				&& validatedData.setGanteiCode(jTextField_GanteiCode.getText())
				&& validatedData.setKihonTanka(jTextField_KihonTanka.getText())
				&& validatedData.setHinketuTanka(jTextField_HinketuTanka.getText())
				&& validatedData.setSindenzuTanka(jTextField_SindenzuTanka.getText())
				&& validatedData.setGanteiTanka(jTextField_GanteiTanka.getText())
				&& validatedData.setAllTanka(jTextField_AllTanka.getText())
				&& validatedData.setAllMadoguchi(jTextField_AllMadoguchi.getText())
				&& validatedData.setTotalFee(jTextField_TotalFee.getText());

		// edit s.inoue 2010/02/16
		// �h�b�N���
		if (existsDoc){
			boolean docTankaHantei = validatedData.setDocTanka(jTextField_DocTanka.getText())
					&& validatedData.setMadoguchiDocSyubetu(jComboBox_MadoguchiDocSyubetu.getSelectedIndex())
					&& validatedData.setMadoguchiDocKin(jTextField_MadoguchiDocKinInput.getText())
					&& validatedData.setHokenjyaJougenDoc(this.jTextField_hokenjyaJougenDoc.getText());

			return kihoninfo && docTankaHantei;
		}else{
			// edit s.inoue 2010/02/16
			// ��{���
			boolean kihonTankaHantei = validatedData.setMadoguchiKihonSyubetu(jComboBox_MadoguchiKihonSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiSyousaiSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu.getSelectedIndex())
				&& validatedData.setSeikyuKubun(jComboBox_SeikyuKubun.getSelectedItem().toString())
				&& validatedData.setMadoguchiKihonKin(jTextField_MadoguchiKihonKinInput.getText())
				&& validatedData.setMadoguchiShousaiKin(jTextField_MadoguchiSyousaiKinInput.getText())
				&& validatedData.setMadoguchiTuikaKin(jTextField_MadoguchiTsuikaKinInput.getText())
				&& validatedData.setHokenjyaJougenKihon(this.jTextField_hokenjyaJougenKihon.getText())
				&& validatedData.setHokenjyaJougenSyousai(this.jTextField_hokenjyaJougenSyosai.getText())
				&& validatedData.setHokenjyaJougenTsuika(this.jTextField_hokenjyaJougenTsuika.getText())
				&& validatedData.setMadoguchiSonota(jTextField_MadoguchiSonota.getText());

			return kihoninfo && kihonTankaHantei;
		}
// edit s.inoue 2010/02/16
//		return /* �l��� */
//		validatedData.setJyusinken_ID(jTextField_Jyusinken_ID.getText())
//				&& validatedData.setName(jTextField_Name.getText())
//				&& validatedData.setBirthday(jTextField_Birthday.getText())
//				&& validatedData.setSex(sexCode)
//				&& validatedData.setKensaDate(jTextField_KensaDate.getText())
//				// del s.inoue 2009/08/27
//				// && validatedData.setToHL7Date(jTextField_ToHL7Date.getText())
//				&& validatedData.setHihokenjyasyo_Kigou(jTextField_hihokenjasyotouKigou.getText())
//				&& validatedData.setHihokenjyasyo_No(jTextField_hihokenjasyotouBangou.getText())
//				&&
//
//				/* ���Ϗ�� */
//				validatedData.setHinketsuCode(jTextField_HinketsuCode.getText())
//				&& validatedData.setSindenzuCode(jTextField_SindenzuCode.getText())
//				&& validatedData.setGanteiCode(jTextField_GanteiCode.getText())
//				&& validatedData.setKihonTanka(jTextField_KihonTanka.getText())
//				&& validatedData.setHinketuTanka(jTextField_HinketuTanka.getText())
//				&& validatedData.setSindenzuTanka(jTextField_SindenzuTanka.getText())
//				&& validatedData.setGanteiTanka(jTextField_GanteiTanka.getText())
//				&& validatedData.setDocTanka(jTextField_DocTanka.getText())
//				&& validatedData.setMadoguchiKihonSyubetu(jComboBox_MadoguchiKihonSyubetu.getSelectedIndex())
//				&& validatedData.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiSyousaiSyubetu.getSelectedIndex())
//				&& validatedData.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu.getSelectedIndex())
//				&& validatedData.setMadoguchiDocSyubetu(jComboBox_MadoguchiDocSyubetu.getSelectedIndex())
//				&& validatedData.setSeikyuKubun(jComboBox_SeikyuKubun.getSelectedItem().toString())
//				&& validatedData.setMadoguchiKihonKin(jTextField_MadoguchiKihonKinInput.getText())
//				&& validatedData.setMadoguchiShousaiKin(jTextField_MadoguchiSyousaiKinInput.getText())
//				&& validatedData.setMadoguchiTuikaKin(jTextField_MadoguchiTsuikaKinInput.getText())
//				&& validatedData.setMadoguchiDocKin(jTextField_MadoguchiDocKinInput.getText())
//				&& validatedData.setHokenjyaJougenKihon(this.jTextField_hokenjyaJougenKihon.getText())
//				&& validatedData.setHokenjyaJougenSyousai(this.jTextField_hokenjyaJougenSyosai.getText())
//				&& validatedData.setHokenjyaJougenTsuika(this.jTextField_hokenjyaJougenTsuika.getText())
//				&& validatedData.setHokenjyaJougenDoc(this.jTextField_hokenjyaJougenDoc.getText())
//				&& validatedData.setMadoguchiSonota(jTextField_MadoguchiSonota.getText())
//				&& validatedData.setAllTanka(jTextField_AllTanka.getText())
//				&& validatedData.setAllMadoguchi(jTextField_AllMadoguchi.getText())
//				&& validatedData.setTotalFee(jTextField_TotalFee.getText());
	}

	/**
	 * ���͌��σf�[�^���쐬���擾����B
	 */
	private JKessaiDataInput getInputData(long syousaiSum, long tsuikaSum, long ningenDocPrice) {
		JKessaiDataInput data = new JKessaiDataInput();

		// add ver2 s.inoue 2009/06/18
		//if (ningenDocPrice > 0) {

			/* �P���i�l�ԃh�b�N�j */
			try {
				data.setDockTanka(Integer.valueOf(validatedData.getNingenDocTanka()));
			} catch (NumberFormatException e) {
				/* �������Ȃ� */
			}

			/* �������S��ʁi�l�ԃh�b�N�j */
			try {
				Integer madoguchiNingenDocSkyubetsu = Integer.valueOf(validatedData.getMadoguchiDocSyubetu());
				data.setDockMadoFutanSyubetu(madoguchiNingenDocSkyubetsu);
			} catch (NumberFormatException e) {
				/* �������Ȃ� */
			}

			/* �������S���z�i�l�ԃh�b�N�j */
			try {
				String madoguchiDocKingakuString = validatedData.getMadoguchiDocKin();
				if (madoguchiDocKingakuString == null
						|| madoguchiDocKingakuString.isEmpty()) {
					data.setDocMadoFutan(0);
				} else {
					Integer madoguchiDockaKingaku = Integer.valueOf(madoguchiDocKingakuString);
					data.setDocMadoFutan(madoguchiDockaKingaku);
				}
			} catch (NumberFormatException e) {
				data.setDocMadoFutan(0);
			}

			/* �ی��ҕ��S����z */
			data.setDockHokenjyaJyougen(JLong.valueOf(validatedData.getHokenjyaJougenDoc()));

		//}else{


			/* �P���i��{�I�Ȍ��f�j */
			try {
				data.setKihonTanka(Integer.valueOf(validatedData.getKihonTanka()));
			} catch (NumberFormatException e) {
				/* �������Ȃ� */
			}

			/* �������S��ʁi��{�I�Ȍ��f�j */
			try {
				Integer madoguchiKihonSyubetu = Integer.valueOf(validatedData.getMadoguchiKihonSyubetu());
				data.setKihonMadoFutanSyubetu(madoguchiKihonSyubetu);
			} catch (NumberFormatException e) {
				/* �������Ȃ� */
			}

			/* �������S���z�i��{�I�Ȍ��f�j */
			try {
				String madoguchiKihonKingakuString = validatedData
						.getMadoguchiKihonKin();
				if (madoguchiKihonKingakuString == null
						|| madoguchiKihonKingakuString.isEmpty()) {
					data.setKihonMadoFutan(0);
				} else {
					Integer madoguchiKihonKingaku = Integer.valueOf(madoguchiKihonKingakuString);
					data.setKihonMadoFutan(madoguchiKihonKingaku);
				}

			} catch (NumberFormatException e) {
				data.setKihonMadoFutan(0);
			}

			/* �P�����v�i�ڍׂȌ��f�j */
			data.setSyousaiTanka(syousaiSum);

			/* �������S��ʁi�ڍׂȌ��f�j */
			try {
				Integer madoguchiSyousaiSyubetu = Integer.valueOf(validatedData.getMadoguchiSyousaiSyubetu());
				data.setSyousaiMadoFutanSyubetu(madoguchiSyousaiSyubetu);

			} catch (NumberFormatException e) {
				/* �������Ȃ� */
			}

			/* �������S���z�i�ڍׂȌ��f�j */
			try {
				// String madoguchiShousaiKingakuString =
				// validatedData.getMadoguchiSyousaiKin();
				String madoguchiShousaiKingakuString = validatedData
						.getMadoguchiSyousaiKin();
				if (madoguchiShousaiKingakuString == null
						|| madoguchiShousaiKingakuString.isEmpty()) {
					data.setSyousaiMadoFutan(0);
				} else {
					Integer madoguchiSyousaiKingaku = Integer
							.valueOf(madoguchiShousaiKingakuString);
					data.setSyousaiMadoFutan(madoguchiSyousaiKingaku);
				}

			} catch (NumberFormatException e) {
				data.setSyousaiMadoFutan(0);
			}

			/* �P�����v�i�ǉ����f�j */
			data.setTsuikaTanka(tsuikaSum);

			/* �������S��ʁi�ǉ����f�j */
			try {
				Integer madoguchiTsuikaSkyubetsu = Integer.valueOf(validatedData
						.getMadoguchiTsuikaSyubetu());
				data.setTsuikaMadoFutanSyubetu(madoguchiTsuikaSkyubetsu);

			} catch (NumberFormatException e) {
				/* �������Ȃ� */
			}

			/* �������S���z�i�ǉ����f�j */
			try {
				String madoguchiTuikaKingakuString = validatedData
						.getMadoguchiTuikaKin();
				if (madoguchiTuikaKingakuString == null
						|| madoguchiTuikaKingakuString.isEmpty()) {
					data.setTsuikaMadoFutan(0);
				} else {
					Integer madoguchiTsuikaKingaku = Integer
							.valueOf(madoguchiTuikaKingakuString);
					data.setTsuikaMadoFutan(madoguchiTsuikaKingaku);
				}
			} catch (NumberFormatException e) {
				data.setTsuikaMadoFutan(0);
			}

			/* ���̑��̌��f�ɂ�镉�S�z */
			try {
				String madoguchiSonotaString = validatedData.getMadoguchiSonota();
				if (madoguchiSonotaString == null
						|| madoguchiSonotaString.isEmpty()) {
					data.setMadoFutanSonota(0);
				} else {
					Integer madoguchiSonota = Integer
							.valueOf(madoguchiSonotaString);
					data.setMadoFutanSonota(madoguchiSonota);
				}
			} catch (NumberFormatException e) {
				data.setMadoFutanSonota(0);
			}
			/* �ی��ҕ��S����z */
			data.setKihonHokenjyaJyougen(JLong.valueOf(validatedData.getHokenjyaJougenKihon()));
			data.setSyousaiHokenjyaJyougen(JLong.valueOf(validatedData.getHokenjyaJougenSyousai()));
			data.setTsuikaHokenjyaJyougen(JLong.valueOf(validatedData.getHokenjyaJougenTsuika()));
		//}
		return data;
	}

// del s.inoue 2012/07/04
//	// �̎����������
//	public void pushedPrintRyosyu() {
//
//		// �I���s�𒊏o����B
////		TreeMap<String, Object> printDataIrai = new TreeMap<String, Object>();
//		ProgressWindow progressWindow = new ProgressWindow(this, false,true);
////
////		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
////		ArrayList<String> kyeList =  new ArrayList<String>();
////
////		JKenshinKekkaSearchListFrameData vo = null;
////		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
////			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
////			if (vo.getCHECKBOX_COLUMN().equals("Y")){
////				if (vo.getKEKA_INPUT_FLG().equals("��")){
////					JErrorMessage.show("M3547", this);
////					return;
////				}
////				selectedRows.add(i);
////			}
////		}
////
////		// �I���s�Ȃ�
////		int selectedCount = selectedRows.size();
////		if (selectedCount == 0) {
////			JErrorMessage.show("M3548", this);
////			return;
////		}
//
//		// �˗����f�[�^�쐬
////		for (int j = 0; j < selectedCount; j++) {
////
////			int k = selectedRows.get(j);
////			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(k);
//
//		progressWindow.setGuidanceByKey("tokutei.iraisho.frame.progress.guidance");
//		progressWindow.setVisible(true);
//
////			validatedData.setUKETUKE_ID(vo.getUKETUKE_ID());
//		Hashtable<String, String> tmpRyosyusyo = new Hashtable<String, String>();
//
//		// ���s��
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�NMM��dd��");
//		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());
//		tmpRyosyusyo.put("HAKOUBI_1", stringTimeStamp);
//		tmpRyosyusyo.put("HAKOUBI_2", stringTimeStamp);
//
//		// ���� _2:�T��
//		String simei = validatedData.getName();
//		tmpRyosyusyo.put("SHIMEI_1", simei);
//		tmpRyosyusyo.put("SHIMEI_2", simei);
//		// ���f���{��
//		// eidt s.inoue 2012/01/30
//		String kensaNengapi = validatedData.getKensaDate();
//
//		String dates = kensaNengapi.substring(0, 4)+ "�N"+ kensaNengapi.substring(4,6)+ "��"+ kensaNengapi.substring(6,8)+ "��";
//
//		tmpRyosyusyo.put("JISHIBI_1",dates );
//		tmpRyosyusyo.put("JISHIBI_2",dates );
//
//		// ��f�������ԍ�
//		String jyusinkenID = validatedData.getJyusinken_ID();
//		tmpRyosyusyo.put("SEIRINO_1",jyusinkenID );
//		tmpRyosyusyo.put("SEIRINO_2",jyusinkenID );
//
//		DecimalFormat formater = new DecimalFormat("#,###");
//		// �P�����v
//		String tankaSum = validatedData.getAllTanka();
//		Integer inttankaSum = tankaSum.equals("")?0:Integer.parseInt(tankaSum);
//
//		tmpRyosyusyo.put("TANKA_SUM_1",formater.format(inttankaSum));
//		tmpRyosyusyo.put("TANKA_SUM_2",formater.format(inttankaSum));
//		// �������v
//		String madogutiSum = validatedData.getAllMadoguchi();
//		Integer intmadogutiSum = madogutiSum.equals("")?0:Integer.parseInt(madogutiSum);
//
//		tmpRyosyusyo.put("MADOGUTI_SUM_1",formater.format(intmadogutiSum));
//		tmpRyosyusyo.put("MADOGUTI_SUM_2",formater.format(intmadogutiSum));
//		// �������v
//		String totalFee = validatedData.getTotalFee();
//		Integer inttotalFee = totalFee.equals("")?0:Integer.parseInt(totalFee);
//
//		tmpRyosyusyo.put("SEIKYU_SUM_1",formater.format(inttotalFee));
//		tmpRyosyusyo.put("SEIKYU_SUM_2",formater.format(inttotalFee));
//		// ���̑����v
//		String totalSonota = validatedData.getMadoguchiSonota();
//		Integer inttotalSonota = totalSonota.equals("")?0:Integer.parseInt(totalSonota);
//
//		tmpRyosyusyo.put("SONOTA_SUM_1",formater.format(inttotalSonota));
//		tmpRyosyusyo.put("SONOTA_SUM_2",formater.format(inttotalSonota));
//
////		String uketukeId = validatedData.getUketuke_id();
////		tmpRyosyusyo.put("UKETUKE_ID", uketukeId);
//
//// del s.inoue 2012/01/27
////		Ryosyusyo Ryosyusyo = new Ryosyusyo();
////
////		if (!Ryosyusyo.setPrintRyosyusyoDataSQL(tmpRyosyusyo)) {
////			progressWindow.setVisible(false);
////			JErrorMessage.show("M3530", this);
////			return;
////		}
////
////		// ���obj key:�����N����+��tID value:����f�[�^
////		printDataIrai.put(String.valueOf(kensaNengapi + uketukeId), Ryosyusyo);
////		kyeList.add(String.valueOf(kensaNengapi + uketukeId));
////		System.out.println(String.valueOf(kensaNengapi + uketukeId));
////		}
//
//		Kikan kikanData = new Kikan();
//		if (!kikanData.setPrintKikanDataSQL()) {
//			JErrorMessage.show("M4941", this);
//		}
//		Hashtable<String, Object> printData = new Hashtable<String, Object>();
//		printData.put("Kikan", kikanData);
//
//		PrintRyosyusyo print = new PrintRyosyusyo();
////		print.setQueryRyosyusyoResult(printDataIrai);
//		print.setQueryRyosyusyoResult(tmpRyosyusyo);
//		print.setQueryResult(printData);
////		print.setKeys(kyeList);
//
//		progressWindow.setVisible(false);
//
//		try {
//			print.beginPrint();
//		} catch (PrinterException err) {
//			JErrorMessage.show("M3531", this);
//		} finally {
//			progressWindow.setVisible(false);
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = (e.getSource());
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);
		}
		else if (source == jButton_ReCalc) {
			logger.info(jButton_ReCalc.getText());
			pushedReCalcButton();
		}
		// del s.inoue 2012/07/04
		else if (source == jButton_PrintRyosyu) {
			logger.info(jButton_PrintRyosyu.getText());
			// pushedPrintRyosyu();
		}
	}

// del s.inoue 2012/01/26
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_ReCalc.getText());
//			pushedReCalcButton();break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Register.getText());
//			pushedRegisterButton(null);break;
//		}
//	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		/*
		 * �ϑ����P���敪�̃��W�I�{�^��
		 */
		if ((e.getSource()) == jRadioButton_ItakuTankaKobetu) {
			if (jRadioButton_ItakuTankaKobetu.isSelected()) {
				validatedData.setItakuTankaKubun(jRadioButton_ItakuTankaKobetu
						.getText());
			}
		}
		if ((e.getSource()) == jRadioButton_ItakuTankaSyudan) {
			if (jRadioButton_ItakuTankaSyudan.isSelected()) {
				validatedData.setItakuTankaKubun(jRadioButton_ItakuTankaSyudan
						.getText());
			}
		}

		/*
		 * �΂ƂȂ�e�L�X�g�t�B�[���h��setter���Ă񂾎��_�Ŏ�ʂ���ɃZ�b�g����Ă��邱�Ƃ�ۏ؂���
		 */
		if ((e.getSource()) == jComboBox_MadoguchiKihonSyubetu) {
			validatedData.setMadoguchiKihonSyubetu(
					jComboBox_MadoguchiKihonSyubetu.getSelectedIndex() );

			/* ���S�z�E���S�������󗓂ɂ��A������Ԃɂ���B */
			jTextField_MadoguchiKihonKinInput.setText("");

			int selectedIndex = jComboBox_MadoguchiKihonSyubetu
					.getSelectedIndex();
			if (selectedIndex == 0 || selectedIndex == 1) {
				jTextField_MadoguchiKihonKinInput.setEnabled(false);
				jTextField_MadoguchiKihonKinInput.setOpaque(false);

				jLabel_UnitMadoKihon.setText("");

				if (selectedIndex == 0) {
					jTextField_hokenjyaJougenKihon.setEditable(false);
				} else {
					jTextField_hokenjyaJougenKihon.setEditable(true);
				}
			} else {
				jTextField_MadoguchiKihonKinInput.setEnabled(true);
				jTextField_MadoguchiKihonKinInput.setOpaque(true);

				if (selectedIndex == 2) {
					jLabel_UnitMadoKihon.setText("�~");
				} else if (selectedIndex == 3) {
					jLabel_UnitMadoKihon.setText("��");
				} else {
					jLabel_UnitMadoKihon.setText("");
				}

				jTextField_hokenjyaJougenKihon.setEditable(true);
			}
		}

		if ((e.getSource()) == jComboBox_MadoguchiSyousaiSyubetu) {
			validatedData
			.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiSyousaiSyubetu
					.getSelectedIndex());

			/* ���S�z�E���S�������󗓂ɂ��A������Ԃɂ���B */
			jTextField_MadoguchiSyousaiKinInput.setText("");

			int selectedIndex = jComboBox_MadoguchiSyousaiSyubetu
					.getSelectedIndex();
			if (selectedIndex == 0 || selectedIndex == 1) {
				jTextField_MadoguchiSyousaiKinInput.setEnabled(false);
				jTextField_MadoguchiSyousaiKinInput.setOpaque(false);

				jLabel_UnitMadoSyosai.setText("");

				if (selectedIndex == 0) {
					jTextField_hokenjyaJougenSyosai.setEditable(false);
				} else {
					jTextField_hokenjyaJougenSyosai.setEditable(true);
				}
			} else {
				jTextField_MadoguchiSyousaiKinInput.setEnabled(true);
				jTextField_MadoguchiSyousaiKinInput.setOpaque(true);

				if (selectedIndex == 2) {
					jLabel_UnitMadoSyosai.setText("�~");
				} else if (selectedIndex == 3) {
					jLabel_UnitMadoSyosai.setText("��");
				} else {
					jLabel_UnitMadoSyosai.setText("");
				}

				jTextField_hokenjyaJougenSyosai.setEditable(true);
			}
		}

		if ((e.getSource()) == jComboBox_MadoguchiTsuikaSyubetu) {
			validatedData
					.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu
							.getSelectedIndex());

			/* ���S�z�E���S�������󗓂ɂ��A������Ԃɂ���B */
			jTextField_MadoguchiTsuikaKinInput.setText("");

			int selectedIndex = jComboBox_MadoguchiTsuikaSyubetu
					.getSelectedIndex();
			if (selectedIndex == 0 || selectedIndex == 1) {
				jTextField_MadoguchiTsuikaKinInput.setEnabled(false);
				jTextField_MadoguchiTsuikaKinInput.setOpaque(false);

				jLabel_UnitMadoTsuika.setText("");

				if (selectedIndex == 0) {
					jTextField_hokenjyaJougenTsuika.setEditable(false);
				} else {
					jTextField_hokenjyaJougenTsuika.setEditable(true);
				}
			} else {
				jTextField_MadoguchiTsuikaKinInput.setEnabled(true);
				jTextField_MadoguchiTsuikaKinInput.setOpaque(true);

				if (selectedIndex == 2) {
					jLabel_UnitMadoTsuika.setText("�~");
				} else if (selectedIndex == 3) {
					jLabel_UnitMadoTsuika.setText("��");
				} else {
					jLabel_UnitMadoTsuika.setText("");
				}

				jTextField_hokenjyaJougenTsuika.setEditable(true);
			}
		}

		// add ver2 s.inoue 2009/07/09
		if ((e.getSource()) == jComboBox_MadoguchiDocSyubetu) {
			validatedData
					.setMadoguchiDocSyubetu(jComboBox_MadoguchiDocSyubetu
							.getSelectedIndex());

			/* ���S�z�E���S�������󗓂ɂ��A������Ԃɂ���B */
			jTextField_MadoguchiDocKinInput.setText("");

			int selectedIndex = jComboBox_MadoguchiDocSyubetu
					.getSelectedIndex();
			if (selectedIndex == 0 || selectedIndex == 1) {
				jTextField_MadoguchiDocKinInput.setEnabled(false);
				jTextField_MadoguchiDocKinInput.setOpaque(false);

				jLabel_UnitMadoNingenDoc.setText("");

				if (selectedIndex == 0) {
					jTextField_hokenjyaJougenDoc.setEditable(false);
				} else {
					jTextField_hokenjyaJougenDoc.setEditable(true);
				}
			} else {
				jTextField_MadoguchiDocKinInput.setEnabled(true);
				jTextField_MadoguchiDocKinInput.setOpaque(true);

				if (selectedIndex == 2) {
					jLabel_UnitMadoNingenDoc.setText("�~");
				} else if (selectedIndex == 3) {
					jLabel_UnitMadoNingenDoc.setText("��");
				} else {
					jLabel_UnitMadoNingenDoc.setText("");
				}

				jTextField_hokenjyaJougenDoc.setEditable(true);
			}
		}

	}
	// add h.yoshitama 2009/09/18
	/**
	 * �Z���̐F�ɍŐV�̏�Ԃ𔽉f����B
	 */
	private void refreshTableCellColor() {

		cellColors.clear();
		for (int i = 0; i < table.getRowCount(); i++) {
			cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, 2)));
		}
	}
}

