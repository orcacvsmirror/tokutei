package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Getuji;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filectrl.JDirChooser;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.filter.SpecialFilterPanel;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenEditButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenInsertButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji.JInputKessaiDataFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JOutputHL7;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JOutputHL7Directory;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintSeikyuGetuji;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Gekeihyo;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.table.client.Grid;
import org.openswing.swing.table.columns.client.CheckBoxColumn;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;

/**
 * �ꗗList���
 * @author s.inoue
 * @version 2.0
 */
public class JOutputGetujiSearchListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = 9011769886413314314L;	// edit n.ohkubo 2014/10/01�@�ǉ�
	
	protected Connection conn = null;
	protected GridControl grid = new GridControl();
	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	/* button */
//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
//	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	// del s.inoue 2012/11/14
//	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	protected ExtendedOpenGenericButton buttonClose = null;
	protected ExtendedOpenGenericButton buttonSeikyu = null;
	protected ExtendedOpenGenericButton buttonHL7 = null;
	protected ExtendedOpenGenericButton buttonSeikyuPrint = null;

	/* control */
	protected TextColumn dateColumn_KenshinDateFrom = new TextColumn();
	protected TextColumn dateColumn_KenshinDateTo = new TextColumn();

	protected ComboColumn textColumn_hokenjaNo = new ComboColumn();
	protected ComboColumn textColumn_shiharaiDaikouNo = new ComboColumn();

	protected TextColumn textColumn_Name = new TextColumn();
	protected TextColumn textColumn_HokensyoCode = new TextColumn();
	protected TextColumn textColumn_HokensyoNumber = new TextColumn();
	protected TextColumn textColumn_sex = new TextColumn();
	protected TextColumn textColumn_birthday = new TextColumn();
//	protected TextColumn textColumn_inputFlg = new TextColumn();
	protected TextColumn textColumn_jyushinSeiriNo = new TextColumn();
	protected TextColumn textColumn_kanaName = new TextColumn();
//	protected TextColumn textColumn_hanteiNengapi = new TextColumn();
//	protected TextColumn textColumn_tutiNengapi = new TextColumn();
	protected TextColumn textColumn_nendo = new TextColumn();

	protected TextColumn textColumn_tankaGoukei = new TextColumn();
	protected TextColumn textColumn_madoGoukei = new TextColumn();
	protected TextColumn textColumn_seikyuGoukei = new TextColumn();
	protected TextColumn textColumn_updateTimeStamp = new TextColumn();

	protected CheckBoxColumn checkColumn_checkBox = new CheckBoxColumn();

	protected ComboColumn textColumn_nitijiFlg = new ComboColumn();
	protected TextColumn textColumn_outputHl7From = new TextColumn();
	protected TextColumn textColumn_outputHl7To = new TextColumn();

	protected int currentRow = 0;
	protected int currentPage = 0;
	protected int currentTotalRows = 0;

	protected final static String CONST_VALUE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji.JOutputGetujiSearchListFrameData";
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	private static final String JISSIKUBUN_TOKUTEIKENSHIN = "1";  //  @jve:decl-index=0:
	private static final String SYUBETSU_CODE_6_DISPLAY_NAME = "���茒�f�@�֖��͓���ی��w���@�ւ���ی���";  //  @jve:decl-index=0:
	private static final String SYUBETSU_CODE_1_DISPLAY_NAME = "���茒�f�@�֖��͓���ی��w���@�ւ����s�@��";  //  @jve:decl-index=0:

	private static Logger logger = Logger.getLogger(JOutputGetujiSearchListFrame.class);
	private JOutputHL7FrameState state = JOutputHL7FrameState.STATUS_INITIALIZED;
	private Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();

	protected ExtendedComboBox jComboBox_SyubetuCode = new ExtendedComboBox();
    protected ExtendedComboBox jComboBox_SeikyuKikanNumber = new ExtendedComboBox();

	private IDialog messageDialog;
//	private ArrayList<Integer> selectedData = new ArrayList<Integer>();  //  @jve:decl-index=0:	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
	// button�R���g���[��
	protected ExtendedOpenGenericButton buttonCheck = null;
	protected TextControl countText = new TextControl();
	protected boolean chkFlg = false;
	
	// edit n.ohkubo 2014/10/01�@�ǉ� start
	private ColumnContainer columnContainer;
	public ColumnContainer getColumnContainer() {
		return this.columnContainer;
	}
	private JCheckBox savedJCheckBox = new JCheckBox();
	public JCheckBox getSavedJCheckBox() {
		return this.savedJCheckBox;
	}
	// edit n.ohkubo 2014/10/01�@�ǉ� end

	// edit n.ohkubo 2015/03/01�@�ǉ� start
	private boolean isKeyPressed = false;
	public boolean isKeyPressed() {
		return this.isKeyPressed;
	}
	// edit n.ohkubo 2015/03/01�@�ǉ� end

	// �t���[���̏�Ԃ��Ǘ�����
	public enum JOutputHL7FrameState {
		/* ������� */
		STATUS_INITIALIZED,
		/* ������ */
		STATUS_AFTER_SEARCH,
		/* ������ */
		STATUS_AFTER_SEIKYU,
		/* HL7�ϊ��� */
		STATUS_AFTER_HL7
	}

	/* �R���X�g���N�^ */
	public JOutputGetujiSearchListFrame(Connection conn,
			JOutputGetujiSearchListFrameCtl controller) {
		setControl(conn,controller);
	}

	/* �����[�h */
	public void reloadData() {
		grid.reloadData();
	}

	/* �O���b�hgetter */
	public GridControl getGrid() {
		return grid;
	}

	/* ���䃁�\�b�h */
	public void setControl(Connection conn,
			JOutputGetujiSearchListFrameCtl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);
			
			// edit n.ohkubo 2014/10/01�@�ǉ� start�@������ʂɃ`�F�b�N�{�b�N�X��ǉ�
			//�t�B���^�[�p�l���p��WindowListener
			SpecialFilterPanel specialFilterPanel = new SpecialFilterPanel(savedJCheckBox, grid.getParent().getComponents());
			
			//���̃t���[���i�ꗗ��ʁj���A�N�e�B�u���i��ʉE�̌����E�B���h�E�j�@or�@��A�N�e�B�u���i�����E�B���h�E���|�b�v�A�b�v�ŊJ����ꍇ�j���ꂽ�Ƃ��ɓ��삷��悤�ɁAListener��ݒ�iFilterPanel�ւ̃`�F�b�N�{�b�N�X�ǉ���Listener���ōs���j
			this.addWindowListener(specialFilterPanel);
			// edit n.ohkubo 2014/10/01�@�ǉ� end�@������ʂɃ`�F�b�N�{�b�N�X��ǉ�

			// edit n.ohkubo 2015/03/01�@�ǉ��@start�@�uAlt+E�v��������ɓ��삵�Ȃ��Ή��i�L�[�����Ń`�F�b�N�{�b�N�X�̒l�����]����j
			Grid table = grid.getTable().getGrid();
			table.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}
				@Override
				public void keyReleased(KeyEvent e) {
					isKeyPressed = false;
				}
				@Override
				public void keyPressed(KeyEvent e) {
					isKeyPressed = true;
				}
			});
			// edit n.ohkubo 2015/03/01�@�ǉ��@end�@�uAlt+E�v��������ɓ��삵�Ȃ��Ή��i�L�[�����Ń`�F�b�N�{�b�N�X�̒l�����]����j

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ���������� */
	public void jbInit() throws Exception {
		/* control ��ClientApplication�ƈ�v*/
		textColumn_Name.setColumnFilterable(true);
		textColumn_Name.setColumnName("NAME");
		textColumn_Name.setColumnSortable(true);
		textColumn_Name.setPreferredWidth(90);
		// eidt s.inoue 2012/10/25
		textColumn_Name.setColumnFilterable(false);
		textColumn_Name.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_Name.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.NAME));

		textColumn_HokensyoCode.setColumnFilterable(true);
		textColumn_HokensyoCode.setColumnName("HIHOKENJYASYO_KIGOU");
		textColumn_HokensyoCode.setColumnSortable(true);
		textColumn_HokensyoCode.setPreferredWidth(110);
		// eidt s.inoue 2012/10/25
		textColumn_HokensyoCode.setColumnFilterable(false);
		textColumn_HokensyoCode.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_HokensyoCode.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU));

		textColumn_HokensyoNumber.setColumnFilterable(true);
		textColumn_HokensyoNumber.setColumnName("HIHOKENJYASYO_NO");
		textColumn_HokensyoNumber.setColumnSortable(true);
		textColumn_HokensyoNumber.setPreferredWidth(110);
		// eidt s.inoue 2012/10/25
		textColumn_HokensyoNumber.setColumnFilterable(false);
		textColumn_HokensyoNumber.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_HokensyoNumber.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_NO));

//		dateColumn_KenshinDateFrom.setColumnName("KENSA_NENGAPI");	// edit n.ohkubo 2014/10/01�@�폜
		dateColumn_KenshinDateFrom.setColumnName("KENSA_NENGAPI2");	// edit n.ohkubo 2014/10/01�@�ǉ�
		dateColumn_KenshinDateFrom.setPreferredWidth(80);
		// edit s.inoue 2013/11/12
		dateColumn_KenshinDateFrom.setColumnFilterable(true);
		dateColumn_KenshinDateFrom.setColumnSortable(false);
		dateColumn_KenshinDateFrom.setColumnSelectable(false);
		dateColumn_KenshinDateFrom.setColumnVisible(false);
		dateColumn_KenshinDateFrom.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01�@�ǉ�

		// add s.inoue 2012/10/23
		dateColumn_KenshinDateTo.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDateTo.setColumnFilterable(true);
		dateColumn_KenshinDateTo.setColumnSortable(true);
		dateColumn_KenshinDateTo.setColumnSelectable(true);
		
		dateColumn_KenshinDateTo.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.KENSA_NENGAPI));
		dateColumn_KenshinDateTo.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01�@�ǉ�
		
		textColumn_sex.setColumnFilterable(true);
		textColumn_sex.setColumnName("SEX");
		textColumn_sex.setColumnSortable(true);
		textColumn_sex.setPreferredWidth(40);
		// add s.inoue 2012/10/25
		textColumn_sex.setColumnFilterable(false);
		textColumn_sex.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_sex.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEX));

		textColumn_birthday.setColumnFilterable(true);
		textColumn_birthday.setColumnName("BIRTHDAY");
		textColumn_birthday.setColumnSortable(true);
		textColumn_birthday.setPreferredWidth(80);
		// add s.inoue 2012/10/25
		textColumn_birthday.setColumnFilterable(false);
		textColumn_birthday.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_birthday.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.BIRTHDAY));

//		textColumn_inputFlg.setColumnFilterable(true);
//		textColumn_inputFlg.setColumnName("KEKA_INPUT_FLG");
//		textColumn_inputFlg.setColumnSortable(true);
//		textColumn_inputFlg.setPreferredWidth(40);

		textColumn_jyushinSeiriNo.setColumnFilterable(true);
		textColumn_jyushinSeiriNo.setColumnName("JYUSHIN_SEIRI_NO");
		textColumn_jyushinSeiriNo.setColumnSortable(true);
		textColumn_jyushinSeiriNo.setPreferredWidth(100);
		//add tanaka 2013/11/07
		textColumn_jyushinSeiriNo.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.JYUSHIN_SEIRI_NO));
		textColumn_jyushinSeiriNo.setMaxCharacters(11);	// edit n.ohkubo 2014/10/01�@�ǉ�

		textColumn_hokenjaNo.setColumnFilterable(true);
		textColumn_hokenjaNo.setColumnName("HKNJANUM");
		textColumn_hokenjaNo.setColumnSortable(true);
		textColumn_hokenjaNo.setPreferredWidth(200);
		// add s.inoue 2012/10/25
		textColumn_hokenjaNo.setDomainId("T_HOKENJYA");
		//add tanaka 2013/11/07
		textColumn_hokenjaNo.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HKNJANUM));

		textColumn_shiharaiDaikouNo.setColumnFilterable(true);
		textColumn_shiharaiDaikouNo.setColumnName("SIHARAI_DAIKOU_BANGO");
		textColumn_shiharaiDaikouNo.setColumnSortable(true);
		textColumn_shiharaiDaikouNo.setPreferredWidth(200);
		// add s.inoue 2012/10/25
		textColumn_shiharaiDaikouNo.setDomainId("T_SHIHARAI");
		//add tanaka 2013/11/07
		textColumn_shiharaiDaikouNo.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO));

		textColumn_kanaName.setColumnFilterable(true);
		textColumn_kanaName.setColumnName("KANANAME");
		textColumn_kanaName.setColumnSortable(true);
		textColumn_kanaName.setPreferredWidth(165);
		//add tanaka 2013/11/07
		textColumn_kanaName.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.KANANAME));
		textColumn_kanaName.setMaxCharacters(50);	// edit n.ohkubo 2014/10/01�@�ǉ�

//		textColumn_hanteiNengapi.setColumnFilterable(true);
//		textColumn_hanteiNengapi.setColumnName("HANTEI_NENGAPI");
//		textColumn_hanteiNengapi.setColumnSortable(true);
//		textColumn_hanteiNengapi.setPreferredWidth(80);

//		textColumn_tutiNengapi.setColumnFilterable(true);
//		textColumn_tutiNengapi.setColumnName("TUTI_NENGAPI");
//		textColumn_tutiNengapi.setColumnSortable(true);
//		textColumn_tutiNengapi.setPreferredWidth(80);

		textColumn_nendo.setColumnFilterable(true);
		textColumn_nendo.setColumnName("NENDO");
		textColumn_nendo.setColumnSortable(true);
		textColumn_nendo.setPreferredWidth(40);
		//add tanaka 2013/11/07
		textColumn_nendo.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.NENDO));
		textColumn_nendo.setMaxCharacters(4);	// edit n.ohkubo 2014/10/01�@�ǉ�

		textColumn_tankaGoukei.setColumnFilterable(true);
		textColumn_tankaGoukei.setColumnName("TANKA_GOUKEI");
		textColumn_tankaGoukei.setColumnSortable(true);
		textColumn_tankaGoukei.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_tankaGoukei.setColumnFilterable(false);
		textColumn_tankaGoukei.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_tankaGoukei.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.TANKA_GOUKEI));

		textColumn_madoGoukei.setColumnFilterable(true);
		textColumn_madoGoukei.setColumnName("MADO_FUTAN_GOUKEI");
		textColumn_madoGoukei.setColumnSortable(true);
		textColumn_madoGoukei.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_madoGoukei.setColumnFilterable(false);
		textColumn_madoGoukei.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_madoGoukei.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.MADO_FUTAN_GOUKEI));

		textColumn_seikyuGoukei.setColumnFilterable(true);
		textColumn_seikyuGoukei.setColumnName("SEIKYU_KINGAKU");
		textColumn_seikyuGoukei.setColumnSortable(true);
		textColumn_seikyuGoukei.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_seikyuGoukei.setColumnFilterable(false);
		textColumn_seikyuGoukei.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_seikyuGoukei.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEIKYU_KINGAKU));

		textColumn_updateTimeStamp.setColumnFilterable(false);
		textColumn_updateTimeStamp.setColumnName("UPDATE_TIMESTAMP");
		textColumn_updateTimeStamp.setColumnSortable(true);
		textColumn_updateTimeStamp.setPreferredWidth(140);
		// eidt s.inoue 2012/10/25
		textColumn_updateTimeStamp.setColumnFilterable(false);
		textColumn_updateTimeStamp.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_updateTimeStamp.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.UPDATE_TIMESTAMP));

		textColumn_nitijiFlg.setColumnFilterable(true);
		textColumn_nitijiFlg.setColumnName("JISI_KBN");
		textColumn_nitijiFlg.setColumnSortable(true);
		textColumn_nitijiFlg.setPreferredWidth(40);
		// add s.inoue 2012/10/25
		textColumn_nitijiFlg.setDomainId("JISI_KBN_WK");
		//add tanaka 2013/11/07
		textColumn_nitijiFlg.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.JISI_KBN));

		// edit s.inoue 2013/11/12
//		textColumn_outputHl7From.setColumnName("HENKAN_NITIJI");	// edit n.ohkubo 2014/10/01�@�폜
		textColumn_outputHl7From.setColumnName("HENKAN_NITIJI2");	// edit n.ohkubo 2014/10/01�@�ǉ�
		textColumn_outputHl7From.setPreferredWidth(80);
		// edit s.inoue 2013/11/12
		textColumn_outputHl7From.setColumnFilterable(true);
		textColumn_outputHl7From.setColumnSortable(false);
		textColumn_outputHl7From.setColumnSelectable(false);
		textColumn_outputHl7From.setColumnVisible(false);
		textColumn_outputHl7From.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01�@�ǉ�

		// add s.inoue 2012/10/23
		textColumn_outputHl7To.setColumnName("HENKAN_NITIJI");
		textColumn_outputHl7To.setColumnFilterable(true);
		textColumn_outputHl7To.setColumnSortable(true);
		textColumn_outputHl7To.setColumnSelectable(true);
		
		textColumn_outputHl7To.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HENKAN_NITIJI));
		textColumn_outputHl7To.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01�@�ǉ�
		
		// eidt s.inoue 2011/04/20
		checkColumn_checkBox.setColumnFilterable(false);
		checkColumn_checkBox.setColumnName("CHECKBOX_COLUMN");
		checkColumn_checkBox.setColumnSortable(false);
		checkColumn_checkBox.setPreferredWidth(50);
		checkColumn_checkBox.setEditableOnEdit(true);
		checkColumn_checkBox.setEditableOnInsert(true);
		checkColumn_checkBox.setPositiveValue("Y");
		checkColumn_checkBox.setNegativeValue("N");
		checkColumn_checkBox.setColumnDuplicable(true);
		checkColumn_checkBox.setColumnRequired(false);
		checkColumn_checkBox.setEnableInReadOnlyMode(true);
		checkColumn_checkBox.setAllowNullValue(false);
		//add tanaka 2013/11/07
		checkColumn_checkBox.setColumnVisible(true);
		checkColumn_checkBox.setColumnVisible(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.CHECKBOX_COLUMN));

		/* button */
		setJButtons();

		// openswing s.inoue 2011/02/03
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
//	    buttonsPanel.add(insertButton, null);
//	    buttonOriginePanel.add(editButton, null);
//	    buttonOriginePanel.add(saveButton, null);
//	    buttonOriginePanel.add(deleteButton, null);
	    buttonOriginePanel.add(filterButton, null);
	    buttonOriginePanel.add(reloadButton, null);
//	    buttonOriginePanel.add(exportButton, null);
		buttonOriginePanel.add(buttonCheck,null);
	    buttonOriginePanel.add(navigatorBar, null);
		buttonOriginePanel.add(countText, null);

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
	    buttonPanel.add(buttonSeikyu, null);
	    buttonPanel.add(buttonHL7, null);
	    buttonPanel.add(buttonSeikyuPrint, null);

		// action�ݒ�
	    buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
	    buttonSeikyu.addActionListener(new ListFrame_button_actionAdapter(this));
	    buttonHL7.addActionListener(new ListFrame_button_actionAdapter(this));
	    buttonSeikyuPrint.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonCheck.addActionListener(new ListFrame_button_actionAdapter(this));

		// add s.inoue 2012/11/21
		navigatorBar.addAfterActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	if (JApplication.selectedHistoryRows == null)return;
	    		for (int i = 0; i < JApplication.selectedHistoryRows.size(); i++) {
	    			grid.getVOListTableModel().setValueAt("N", JApplication.selectedHistoryRows.get(i), 0);
	    			// edit s.inoue 2013/11/07
	    			// JApplication.selectedHistoryRows.remove(i);
	    		}
	    		JApplication.selectedHistoryRows.removeAll(JApplication.selectedHistoryRows);
	          }
	    });

		Box origineBox = new Box(BoxLayout.X_AXIS);
		origineBox.add(buttonOriginePanel);
		origineBox.add(Box.createVerticalStrut(2));

		// box2�s��
		Box recordBox = new Box(BoxLayout.X_AXIS);
		recordBox.add(buttonPanel);
		recordBox.add(Box.createVerticalStrut(2));

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(origineBox);
		buttonBox.add(new JSeparator());
		buttonBox.add(recordBox);

		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
		grid.setInsertButton(insertButton);
		grid.setEditButton(editButton);
		grid.setReloadButton(reloadButton);
		grid.setDeleteButton(deleteButton);
//		grid.setSaveButton(saveButton);
//		grid.setImportButton(importButton);
//		grid.setExportButton(exportButton);
		grid.setFilterButton(filterButton);
		grid.setNavBar(navigatorBar);

		grid.setMaxSortedColumns(5);
		grid.setNavBar(navigatorBar);

		grid.setValueObjectClassName(CONST_VALUE);
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(checkColumn_checkBox, null);
		grid.getColumnContainer().add(textColumn_nendo, null);
		grid.getColumnContainer().add(textColumn_nitijiFlg, null);
		grid.getColumnContainer().add(textColumn_jyushinSeiriNo, null);
		grid.getColumnContainer().add(textColumn_kanaName, null);
		grid.getColumnContainer().add(textColumn_Name, null);
		grid.getColumnContainer().add(textColumn_sex, null);
		grid.getColumnContainer().add(textColumn_birthday, null);
//		grid.getColumnContainer().add(textColumn_inputFlg, null);
		grid.getColumnContainer().add(dateColumn_KenshinDateFrom, null);
		grid.getColumnContainer().add(dateColumn_KenshinDateTo, null);
		grid.getColumnContainer().add(textColumn_outputHl7From, null);
		grid.getColumnContainer().add(textColumn_outputHl7To, null);
		grid.getColumnContainer().add(textColumn_hokenjaNo, null);
		grid.getColumnContainer().add(textColumn_shiharaiDaikouNo, null);
		grid.getColumnContainer().add(textColumn_HokensyoCode, null);
		grid.getColumnContainer().add(textColumn_HokensyoNumber, null);
//		grid.getColumnContainer().add(textColumn_hanteiNengapi, null);
//		grid.getColumnContainer().add(textColumn_tutiNengapi, null);
		grid.getColumnContainer().add(textColumn_tankaGoukei, null);
		grid.getColumnContainer().add(textColumn_madoGoukei, null);
		grid.getColumnContainer().add(textColumn_seikyuGoukei, null);
		grid.getColumnContainer().add(textColumn_updateTimeStamp, null);
		
		columnContainer = grid.getColumnContainer();// edit n.ohkubo 2014/10/01�@�ǉ�

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.getuji.frame.title","tokutei.getuji.frame.guidance");
		jLabel_Title.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

		// ��ʃR�[�h�R���{�{�b�N�X�̐ݒ�
		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_1_DISPLAY_NAME);
		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_6_DISPLAY_NAME);
		/* ���b�Z�[�W�_�C�A���O������������B�� */
		try {
			messageDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
			// messageDialog.setShowCancelButton(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	protected JPanel jPanel_TitleArea = null;
	protected ExtendedLabel jLabel_Title = null;

	private JPanel getJPanel_TitleArea(Box buttonBox) {
		if (jPanel_TitleArea == null) {
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridy = 1;
			gridBagConstraints26.gridx = 0;

			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
//			gridBagConstraints25.ipady = 10;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			// gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.gridx = 0;

			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
			 jPanel_TitleArea.add(buttonBox, gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}

	/**
	 * �g�k�V�o�̓{�^���������ꂽ�ꍇ �R
	 */
	// eidt s.inoue 2013/11/07
	public boolean pushedHL7OutputButton( ActionEvent e )
	{
		boolean outputCansel = false;
		// �I����Ԃ�ێ�����
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
		switch(state)
		{
			case STATUS_AFTER_SEIKYU:
			case STATUS_AFTER_HL7:

				// eidt s.inoue 2011/04/05
//				JOutputGetujiSearchListFrameData vo = null;
//				for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//					vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//					if (vo.getCHECKBOX_COLUMN().equals("Y"))
//						selectedRows.add(i);
//				}

				// add s.inoue 2013/11/07
				RETURN_VALUE Ret_p = JErrorMessage.show("M4759", this);
				if (Ret_p == RETURN_VALUE.NO) {
					return false;
				}

//				// add s.inoue 2012/11/20
//				JApplication.selectedHistoryRows = new ArrayList<Integer>();
//				JOutputGetujiSearchListFrameData chk = null;
//				for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//					chk = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//					if (chk.getCHECKBOX_COLUMN().equals("Y"))
//						JApplication.selectedHistoryRows.add(i);
//				}

				JOutputGetujiSearchListFrameData jOutData = new JOutputGetujiSearchListFrameData();

				/* datas �́AHL7 �o�͎��ɕK�v�ȏ�� */
				if( JOutputHL7.RunProcess(datas,jOutData) ){
					// ����ɏI�������ꍇ
					state = JOutputHL7FrameState.STATUS_AFTER_HL7;
					outputCansel = HL7OutputFiles(jOutData);
				}else{
					logger.error("HL7�o�͂Ɏ��s���܂����B");
					return false;
				}
				break;
		}
		buttonCtrl();

		// HL7�o�͏����I�����Ƀ��b�Z�[�W�{�b�N�X��\��
		messageDialog.setMessageTitle("�g�k�V�o��");

		// edit s.inoue 2009/09/18
		// String message = "";
		if(outputCansel){
			JErrorMessage.show("M4722", this);
		}else if (state == JOutputHL7FrameState.STATUS_AFTER_HL7) {
			// message = "HL7�o�͏������I�����܂���";
			JErrorMessage.show("M4721", this);

			// del s.inoue 2011/04/04
			// ���������Ăяo��
			// searchRefresh();
			// table.setselectedRows(modelfixed,selectedRows);
		}

		return true;
	}

	// add s.inoue 2009/09/18
	private boolean HL7OutputFiles(JOutputGetujiSearchListFrameData JOutputData){

//		boolean retCansel = false;
//
//		try {
//			// eidt s.inoue 2012/01/13
//			// �f�B���N�g����I���o����悤�ɕύX
//			JDirectoryChooser dirSelect = new JDirectoryChooser(JPath.getDesktopPath());
//			dirSelect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//			dirSelect.setApproveButtonText("�ۑ�");
//			UIManager.put("FileChooser.readOnly", Boolean.FALSE);
//
//			// �t�@�C���I���_�C�A���O�̕\��
//			// �o�͂���t�@�C����I�����Ă��������B
//			ArrayList<JOutputHL7Directory> outputHL7 = JOutputData.getJOutputDir();
//
//			// �o�͐�̃t�H���_��I�����Ă��������B
//			if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION ){
//					// HL7�o�͏���
//					for (int i = 0; i < outputHL7.size(); i++) {
//						JOutputHL7Directory outputHL7List = outputHL7.get(i);
//
//						// Data/HL7�̎��̓R�s�[��R�s�[�����������
//						if ((JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
//								+ outputHL7List.GetUniqueName() + ".zip").equals(dirSelect.getSelectedFile().getPath() + File.separator
//										+ outputHL7List.GetUniqueName() + ".zip"))break;
//
//						if( JFileCopy.copyFile(
//								// �R�s�[��
//								JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
//								+ outputHL7List.GetUniqueName() + ".zip",
//
//								// �R�s�[��
//								dirSelect.getSelectedFile().getPath() + File.separator
//								+ outputHL7List.GetUniqueName() + ".zip"
//								) != true ){
//							JErrorMessage.show("M4720", this);
//						}
//					}
//			}else{
//				retCansel = true;
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//			JErrorMessage.show("M4720", this);
//		}
//
//		return retCansel;

// eidt s.inoue 2012/01/13
		boolean retCansel = false;

		try {

		// edit s.inoue 2010/05/10 ���̃\�[�X�𗘗p
		JDirChooser dirSelect = new JDirChooser(JPath.getDesktopPath());
		// eidt s.inoue 2011/12/19
		dirSelect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirSelect.setApproveButtonText("�ۑ�");
		UIManager.put("FileChooser.readOnly", Boolean.FALSE);

		// �t�@�C���I���_�C�A���O�̕\��
		// �o�͂���t�@�C����I�����Ă��������B
		ArrayList<JOutputHL7Directory> outputHL7
			= JOutputData.getJOutputDir();

			// �o�͐�̃t�H���_��I�����Ă��������B
			if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION ){
					// HL7�o�͏���
					for (int i = 0; i < outputHL7.size(); i++) {
						// edit s.inoue 2009/09/25
						JOutputHL7Directory outputHL7List = outputHL7.get(i);

						// edit s.inoue 2010/01/05
						// Data/HL7�̎��̓R�s�[��R�s�[�����������
						if ((JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
								+ outputHL7List.GetUniqueName() + ".zip").equals(dirSelect.getSelectedFile().getPath() + File.separator
										+ outputHL7List.GetUniqueName() + ".zip"))break;

						if( JFileCopy.copyFile(
								// �R�s�[��
								JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
								// edit s.inoue 2009/10/18
								// + outputHL7List.GetUniqueName(i) + ".zip",
								+ outputHL7List.GetUniqueName() + ".zip",

								// �R�s�[��
								dirSelect.getSelectedFile().getPath() + File.separator
								// edit s.inoue 2009/10/18
								// + outputHL7List.GetUniqueName(i) + ".zip"
								+ outputHL7List.GetUniqueName() + ".zip"
								) != true ){
							JErrorMessage.show("M4720", this);
						}
					}
			}else{
				retCansel = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			JErrorMessage.show("M4720", this);
		}
		return retCansel;
	}

	/**
	 * �t���[���̏�Ԃɂ���ĉ�����{�^���Ȃǂ𐧌䂷��
	 */
	public void buttonCtrl()
	{
		switch(state)
		{
		case STATUS_INITIALIZED:
		case STATUS_AFTER_SEARCH:
			buttonSeikyu.setEnabled(true);
			buttonClose.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
//			forbitCells.clear();
			break;

		case STATUS_AFTER_SEIKYU:
			buttonSeikyu.setEnabled(false);
			buttonClose.setEnabled(true);
//			jButton_Search.setEnabled(true);
			buttonSeikyu.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
			break;

		case STATUS_AFTER_HL7:
			buttonClose.setEnabled(true);
			// eidt s.inoue 2012/11/20
			// buttonSeikyu.setEnabled(true);
			buttonSeikyu.setEnabled(false);
			buttonClose.setEnabled(true);

//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
			break;
		}
	}

	/* �{�^���Q */
	/**
	 * This method initializes jButton_Close
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private void setJButtons() {
		// �W��buttonSize
		filterButton.setPreferredSize(new Dimension(100,50));
		reloadButton.setPreferredSize(new Dimension(100,50));
		// eidt s.inoue 2012/11/20
		countText.setPreferredSize(new Dimension(80,50));
		countText.setEnabled(false);
		countText.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 12));

		// �`�F�b�Nbutton
		if (buttonCheck == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Check);
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			buttonCheck= new ExtendedOpenGenericButton(
					"Check","","�S�`�F�b�N(ALT+C)",KeyEvent.VK_C,icon,new Dimension(30,30));
			buttonCheck.addActionListener(this);
		}
		// ����button
		if (buttonClose == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonClose= new ExtendedOpenGenericButton(
					"Close","�߂�(R)","�߂�(ALT+R)",KeyEvent.VK_R,icon);
		}
		// �����m��
		if (buttonSeikyu == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Decide);

			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonSeikyu= new ExtendedOpenGenericButton(
					"getujiSeikyu","�����m��(D)","�����m��(ALT+D)",KeyEvent.VK_D,icon);
		}
		// HL7�o��
		if (buttonHL7 == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_HL7Export);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonHL7= new ExtendedOpenGenericButton(
					"getujiHL7","HL7�o��(E)","HL7�o��(ALT+E)",KeyEvent.VK_E,icon);
		}
		// �������
		if (buttonSeikyuPrint == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonSeikyuPrint= new ExtendedOpenGenericButton(
					"getujiSeikyuPrint","�������(P)","�������(ALT+P)",KeyEvent.VK_P,icon);
		}
	}

	/* �{�^���A�N�V�����p�����N���X */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
	  JOutputGetujiSearchListFrame adaptee;

		  ListFrame_button_actionAdapter(JOutputGetujiSearchListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  @Override
		public void actionPerformed(ActionEvent e) {
			   	Object btn = e.getSource();

			   	// eidt s.inoue 2011/05/09
//			   	selectedData = new ArrayList<Integer>();	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

			   	// eidt s.inoue 2011/04/21
			   	if (btn == buttonCheck){
					logger.info(buttonCheck.getText());
					setCheckBoxValue();
		  		}else if (btn == buttonClose){
					logger.info(buttonClose.getText());
					//add tanaka 2013/11/07
					presevColumnStatus();
					adaptee.closeButtton_actionPerformed(e);
				/* ���� */
				}else if (btn == buttonSeikyu){
					logger.info(buttonSeikyu.getText());
					pushedSeikyuButton( e );
					// eidt s.inoue 2011/05/09
//					chkFlg = true;
				/* HL7 */
				}else if (btn == buttonHL7){
					logger.info(buttonHL7.getText());

					// eidt s.inoue 2013/11/07
					if(pushedHL7OutputButton(e))
						reloadButton.doClick();

					// eidt s.inoue 2011/05/09
//					chkFlg = true;
				/* ������� */
				}else if (btn == buttonSeikyuPrint){
					logger.info(buttonSeikyuPrint.getText());
					pushedSeikyuPrintButton( e );
					// eidt s.inoue 2011/05/09
//					chkFlg = true;
				}

// del s.inoue 2012/11/05
//			   	if (chkFlg)
//				selectedData = getSelectedRow();
//
//				// grid.reloadData();
//				reloadButton.doClick();
//
//				// ���������[�h����X���b�h
//				Thread reload = new ActionAutoReloadThread();
//				reload.start();
		  }
		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	//add tanaka 2013/11/07
	private void presevColumnStatus() {
		
		try{
		if(textColumn_Name.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.NAME));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.NAME))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.NAME));
		}
		
		if(textColumn_HokensyoCode.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU));
		}
		
		if(textColumn_HokensyoNumber.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_NO));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_NO))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_NO));
		}
		
//		if(dateColumn_KenshinDateFrom.isVisible()) {
//			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.KENSA_NENGAPI));
//		} else {
//			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.KENSA_NENGAPI))
//				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KENSA_NENGAPI));
//		}
		
		if(dateColumn_KenshinDateTo.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.KENSA_NENGAPI));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.KENSA_NENGAPI))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KENSA_NENGAPI));
		}
		
		if(textColumn_sex.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.SEX));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEX))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SEX));
		}
		
		if(textColumn_birthday.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.BIRTHDAY));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.BIRTHDAY))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.BIRTHDAY));
		}
		
		if(textColumn_jyushinSeiriNo.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.JYUSHIN_SEIRI_NO));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.JYUSHIN_SEIRI_NO))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.JYUSHIN_SEIRI_NO));
		}
		
		if(textColumn_hokenjaNo.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.HKNJANUM));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HKNJANUM))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HKNJANUM));
		}
		
		if(textColumn_shiharaiDaikouNo.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO));
		}
		
		if(textColumn_kanaName.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.KANANAME));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.KANANAME))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KANANAME));
		}
		
		if(textColumn_nendo.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.NENDO));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.NENDO))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.NENDO));
		}
		
		if(textColumn_tankaGoukei.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.TANKA_GOUKEI));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.TANKA_GOUKEI))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.TANKA_GOUKEI));
		}
		
		if(textColumn_madoGoukei.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.MADO_FUTAN_GOUKEI));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.MADO_FUTAN_GOUKEI))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.MADO_FUTAN_GOUKEI));
		}
		
		if(textColumn_seikyuGoukei.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.SEIKYU_KINGAKU));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEIKYU_KINGAKU))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SEIKYU_KINGAKU));
		}
		
		if(textColumn_updateTimeStamp.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.UPDATE_TIMESTAMP));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.UPDATE_TIMESTAMP))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.UPDATE_TIMESTAMP));
		}
		
		if(textColumn_nitijiFlg.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.JISI_KBN));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.JISI_KBN))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.JISI_KBN));
		}
		
//		if(textColumn_outputHl7From.isVisible()) {
//			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.HENKAN_NITIJI));
//		} else {
//			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HENKAN_NITIJI))
//				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HENKAN_NITIJI));
//		}
		
		if(textColumn_outputHl7To.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.HENKAN_NITIJI));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.HENKAN_NITIJI))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HENKAN_NITIJI));
		}
		
		if(checkColumn_checkBox.isVisible()) {
			JApplication.flag_Getuji.removeAll(EnumSet.of(FlagEnum_Getuji.CHECKBOX_COLUMN));
		} else {
			if(!JApplication.flag_Getuji.contains(FlagEnum_Getuji.CHECKBOX_COLUMN))
				JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.CHECKBOX_COLUMN));
		}
		
		// edit n.ohkubo 2014/10/01�@�ǉ� start
		//�u�\�� or ��\���v��DB�֓o�^����
		((JOutputGetujiSearchListFrameCtl)grid.getController()).preserveColumnStatus();
		// edit n.ohkubo 2014/10/01�@�ǉ� end
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// eidt s.inoue 2011/04/21
	private void setCheckBoxValue(){
		JOutputGetujiSearchListFrameData chk = null;
		// eidt s.inoue 2011/04/27
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (chk.getCHECKBOX_COLUMN().equals("Y")){
//				if (selectedData.size() == 0)break;
//				selectedData.remove(i);
//			}else{
//				selectedData.add(i);
//			}
//		}
		if (chkFlg){
			for (int i = grid.getVOListTableModel().getRowCount()-1; i >= 0; i--) {
				// chk = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (chk.getCHECKBOX_COLUMN().equals("Y"))
				//	selectedData.remove(i);
				grid.getVOListTableModel().setValueAt("N", i, 0);
			}
			chkFlg = false;
		}else{
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				grid.getVOListTableModel().setValueAt("N", i, 0);
			}

			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				// chk = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (!chk.getCHECKBOX_COLUMN().equals("Y"))
				// selectedData.add(i);
				grid.getVOListTableModel().setValueAt("Y", i, 0);
			}
			chkFlg = true;
		}
	}
	// add s.inoue 2010/01/19
	private void checkKigenSetting(String kenshinDate,String limitDataStart,String limitDataEnd){
		if (!kenshinDate.equals("") &&
			!limitDataStart.equals("") &&
				!limitDataEnd.equals("")){
			if(!(Integer.parseInt(limitDataStart) <= Integer.parseInt(kenshinDate) &&
					Integer.parseInt(kenshinDate) <= Integer.parseInt(limitDataEnd))){
				JErrorMessage.show("M4756", this);
			}
		}
	}

	// edit ver2 s.inoue 2009/08/31
	// �m����s���Awork�t�H���_������e�[�u���փf�[�^���ڍs����
	/**
	 * �����m�菈���{�^���������ꂽ�ꍇ
	 */
	public void pushedSeikyuButton( ActionEvent e )
	{
		// �I����Ԃ�ێ�����
		// ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		JOutputGetujiSearchListFrameData vo = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (vo.getCHECKBOX_COLUMN().equals("Y"))
				selectedRows.add(i);
		}

// eidt s.inoue 2011/04/05
//		switch(state)
//		{
//		case STATUS_AFTER_SEARCH:
			int count = 0;
			boolean notExtMessage = false;

			datas = new Vector<JKessaiProcessData>();

			RETURN_VALUE Ret = JErrorMessage.show("M4755", this);
			if (Ret == RETURN_VALUE.NO) {
				return;
			}
// eidt s.inoue 2011/04/05
//			for( int i = 0;i < result.size();i++ )
//			{
			for( int i = 0;i < selectedRows.size();i++){
//				if( Boolean.TRUE == (Boolean)modelfixed.getData(i,0)  ){
				vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(i));
				if (vo.getCHECKBOX_COLUMN().equals("Y")){
					count++;

//					Hashtable<String, String> item = result.get(i);
//					checkKigenSetting(
//							item.get("KENSA_NENGAPI"),
//							item.get("HKNJYA_LIMITDATE_START"),
//							item.get("HKNJYA_LIMITDATE_END"));
					checkKigenSetting(
							vo.getKENSA_NENGAPI(),
							vo.getHKNJYA_LIMITDATE_START(),
							vo.getHKNJYA_LIMITDATE_END());

					JKessaiProcessData dataItem = new JKessaiProcessData();
					/* ��tID */
					// dataItem.uketukeId = item.get("UKETUKE_ID");
					dataItem.uketukeId = vo.getUKETUKE_ID();
					/* ��ی��ҏؓ��L�� */
					// dataItem.hiHokenjyaKigo = item.get("HIHOKENJYASYO_KIGOU");
					dataItem.hiHokenjyaKigo = vo.getHIHOKENJYASYO_KIGOU();
					/* ��ی��ҏؓ��ԍ� */
					// dataItem.hiHokenjyaNumber = item.get("HIHOKENJYASYO_NO");
					dataItem.hiHokenjyaNumber = vo.getHIHOKENJYASYO_NO();
					/* �ی��Ҕԍ��i�l�j */
					// dataItem.hokenjyaNumber = item.get("HKNJANUM");
					dataItem.hokenjyaNumber = vo.getHKNJANUM();
					/* �������{�� */
					// dataItem.KensaDate = item.get("KENSA_NENGAPI");
					dataItem.KensaDate = vo.getKENSA_NENGAPI();
					/* �x����s�@�֔ԍ� */
					// dataItem.daikouKikanNumber = item.get("SIHARAI_DAIKOU_BANGO");
					dataItem.daikouKikanNumber = vo.getSIHARAI_DAIKOU_BANGO();
					/* �J�i���� */
					// dataItem.kanaName = item.get("KANANAME");
					dataItem.kanaName = vo.getKANANAME();
					/* ���� */
					// dataItem.sex = item.get("SEX");
					dataItem.sex = vo.getSEX();
					/* ���N���� */
					// dataItem.birthday = item.get("BIRTHDAY");
					dataItem.birthday = vo.getBIRTHDAY();

					if (dataItem.hiHokenjyaNumber == null || dataItem.hiHokenjyaNumber.isEmpty()) {
						/* �����̓G���[,��ی��ҏؓ��ԍ������͂���Ă��܂���B�ꗗ�Ŕ�ی��ҏؓ��ԍ����m�F���Ă��������B
						 * [���s]�@�����i�J�i�j[%s]�A����[%s]�A���N����[%s] */
						String sexName = dataItem.sex.equals("1") ? "�j��" : "����";
						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };

						JErrorMessage.show("M4751", this, params);
						return;
					}

					if (dataItem.KensaDate == null || dataItem.KensaDate.isEmpty()) {
						/* ���̓G���[,���f���ʃf�[�^�����݂��܂���B[���s]�@�����i�J�i�j[%s]�A����[%s]�A���N����[%s] */
						String sexName = dataItem.sex.equals("1") ? "�j��" : "����";
						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };

						JErrorMessage.show("M4753", this, params);
						return;
					}

					/*
					 * ��ʃR�[�h���i�[����
					 */
					if( dataItem.daikouKikanNumber != null && ! dataItem.daikouKikanNumber.isEmpty() )
					{
						dataItem.syubetuCode = "1";
					}else{
						dataItem.syubetuCode = "6";
					}

					// ���{�敪���i�[����i���茒�f�� 1 �Œ�j
					dataItem.jissiKubun = JISSIKUBUN_TOKUTEIKENSHIN;

					/* �����敪���i�[����B */
					String seikyuKubun = getSeikyuKubun(vo);

					if (seikyuKubun == null || seikyuKubun.isEmpty()) {
						/* M4732�F�G���[,�����敪�̎擾�Ɏ��s���܂����B,0,0 */
						JErrorMessage.show("M4733", this);
						return;
					}

					dataItem.seikyuKubun = seikyuKubun;

					String[] registKensa= JInputKessaiDataFrameCtrl.isNotExistKensaKoumoku(
							dataItem.hokenjyaNumber,
							dataItem.uketukeId,
							dataItem.KensaDate,
							dataItem.seikyuKubun);

					if (registKensa != null){
						if (!notExtMessage){
							JErrorMessage.show("M3635", this);
							notExtMessage = true;
						}
					}
					String[] existKensa= JInputKessaiDataFrameCtrl.isExistKensaKoumoku(
							dataItem.hokenjyaNumber,
							dataItem.uketukeId,
							dataItem.KensaDate,
							dataItem.seikyuKubun);
					if (existKensa != null){
						if (!notExtMessage){
							JErrorMessage.show("M3636", this );
							notExtMessage = true;
						}
					}

					datas.add(dataItem);
				}
			}

			if( count != 0 )
			{
				try{

					// ���ρA�W�v��tran
					JApplication.kikanDatabase.Transaction();

					JOutputGetujiSearchListFrameData validatedData = new JOutputGetujiSearchListFrameData();

					// ��ʃR�[�h�ݒ�
					validatedData.setSHUBETU_CODE(String.valueOf(jComboBox_SyubetuCode.getSelectedItem()));

					// if( �����ύX ){
					try {
						/* work��T_KESSAI���� */
						// edit ver2 s.inoue 2009/09/09
						JKessaiProcess.runWorkToKessaiProcess(datas, JApplication.kikanNumber);

					} catch (Exception e1) {
						logger.error(e1.getMessage());
						JApplication.kikanDatabase.rollback();
						JErrorMessage.show("M4730", this);
						return;
					}

					try {
						/* �W�v���� */
						if(JSyuukeiProcess.runWorkToSyukeiProcess(datas) == false)
						{
							JApplication.kikanDatabase.rollback();
							JErrorMessage.show("M4731", this);
							return;
						}
					} catch (Exception e2){
						JApplication.kikanDatabase.rollback();
						logger.error(e2.getMessage());
						JErrorMessage.show("M4731", this);
						return;
					}

					state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;

					messageDialog.setMessageTitle("�����m�菈��");
					messageDialog.setText("�����m�菈�����I�����܂���");
					messageDialog.setVisible(true);
					// }

					// ���ρA�W�v��Commit
					JApplication.kikanDatabase.Commit();

				}catch (Exception ex){
					try{
						JApplication.kikanDatabase.rollback();
					}catch(SQLException exx){
					}
					ex.printStackTrace();
					logger.error(ex.getMessage());
				}
			}
		buttonCtrl();
		// eidt s.inoue 2011/04/05
		// searchRefresh();
		// table.setselectedRows(modelfixed,selectedRows);
	}

	private String getSeikyuKubun(JOutputGetujiSearchListFrameData vo) {
		// �����敪���i�[����
		// eidt s.inoue 2011/04/05
		String query = "SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE " +
				"UKETUKE_ID = " + JQueryConvert.queryConvert(vo.getUKETUKE_ID()) + " AND " +
				"KENSA_NENGAPI = " + JQueryConvert.queryConvert(vo.getKENSA_NENGAPI());
				// "UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
				// "KENSA_NENGAPI = " + JQueryConvert.queryConvert(result.get(i).get("KENSA_NENGAPI"));

		ArrayList<Hashtable<String, String>> tbl = null;
		try{
			tbl = JApplication.kikanDatabase.sendExecuteQuery(query);
		}catch(SQLException f){
			f.printStackTrace();
		}

		String seikyuKubun = null;
		if (tbl != null && tbl.size() == 1) {
			seikyuKubun = tbl.get(0).get("SEIKYU_KBN");
		}

		return seikyuKubun;
	}

	public void pushedSeikyuPrintButton( ActionEvent e )
	{
		// eidt s.inoue 2011/04/05
		// �I���s�𒊏o����B
		// int rowCount = table.getRowCount();
		int rowCount = grid.getVOListTableModel().getRowCount();
		// ����p
		TreeMap<String, Object> printDataGetuji = new TreeMap<String, Object>();
		// �X�e�[�^�X���
		ProgressWindow progressWindow = new ProgressWindow(this, false,true);

		try{
			ArrayList<Integer> selectedRows = new ArrayList<Integer>();
			// edit s.inoue 2009/11/02
			ArrayList<String> keyList =  new ArrayList<String>();

			// eidt s.inoue 2011/04/05
//			// �I���`�F�b�N
//			for (int i = 0; i < rowCount; i++) {
//				if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//					selectedRows.add(i);
//				}
//			}
			JOutputGetujiSearchListFrameData vo = null;
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if (vo.getCHECKBOX_COLUMN().equals("Y"))
					selectedRows.add(i);
			}

			// �I���s�Ȃ�
			int selectedCount = selectedRows.size();
			if (selectedCount == 0) {
				JErrorMessage.show("M3548", this);return;
			}

			progressWindow.setGuidanceByKey("common.frame.progress.guidance.main");
			progressWindow.setVisible(true);

			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();

			// �����f�[�^�쐬
			for (int j = 0; j < selectedCount; j++) {

				// eidt s.inoue 2011/04/12
//				int k = selectedRows.get(j);
				vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(j));

				// ��tID
				// String uketukeId = result.get(k).get("UKETUKE_ID");
				String uketukeId = vo.getUKETUKE_ID();

				tmpKojin.put("UKETUKE_ID", uketukeId);

				Gekeihyo gekeihyo = new Gekeihyo();

				if (!gekeihyo.setPrintSeikyuGetujiDataSQL(tmpKojin)){
				 	// �f�[�^�ڑ����s
					logger.warn("�����f�[�^�쐬���s");
					progressWindow.setVisible(false);
				 	JErrorMessage.show("M3530", this);
				}

				// ��� ��tID:����f�[�^
				printDataGetuji.put(String.valueOf(uketukeId), gekeihyo);
				keyList.add(String.valueOf(uketukeId));
			}

			/*
			 * Kikan�쐬
			 */
			Kikan kikanData = new Kikan();
			if (!kikanData.setPrintKikanDataSQL()) {
				// �f�[�^�ڑ����s
				JErrorMessage.show("M4941", this);
			}

			Hashtable<String, Object> printData = new Hashtable<String, Object>();
			printData.put("Kikan", kikanData);

			// ������s
			PrintSeikyuGetuji getuji = new PrintSeikyuGetuji();

			// add s.inoue 2009/10/15
			// �W�v�\�p�L�[
			ArrayList<String> selectColumn = new ArrayList<String>();
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				// if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
				vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if (vo.getCHECKBOX_COLUMN().equals("Y")){

					// edit s.inoue 2009/10/27
					// String hokenjya = table.getData(i, COLUMN_INDEX_HKNJANUM).toString();
					// String daikoukikan = table.getData(i, COLUMN_INDEX_SIHARAI_DAIKOU_BANGO).toString();
					String hokenjya = vo.getHKNJANUM();
					String daikoukikan = vo.getSIHARAI_DAIKOU_BANGO();

					if (!daikoukikan.equals("")){
						selectColumn.add(daikoukikan);
					}else if(!hokenjya.equals("")){
						selectColumn.add(hokenjya);
					}
				}
			}

			getuji.setPrintShukeiKey(selectColumn);

			getuji.setQueryGetujiResult(printDataGetuji);
			getuji.setQueryResult(printData);
			getuji.setKeys(keyList);

			// edit s.inoue 2009/10/29
			getuji.beginPrint();
		} catch (Exception err) {
			err.printStackTrace();
			progressWindow.setVisible(false);
			JErrorMessage.show("M4734", this);
		} finally {
			progressWindow.setVisible(false);
		}
	}

	/* �{�^���A�N�V���� */
	public void closeButtton_actionPerformed(ActionEvent e) {
		// add s.inoue 2012/11/20
		// _WK�e�[�u���̍폜
		for (int j = 0; j < datas.size(); j++) {
			deleteWork(datas.get(j).uketukeId);
		}

		dispose();

		// add s.inoue 2013/04/05
		if (JApplication.selectedHistoryRows.size() == 0)return;

		// eidt s.inoue 2013/11/07
//		for (int i=0;i < grid.getVOListTableModel().getRowCount(); i++) {
//			JApplication.selectedHistoryRows.remove(i);
//		}
		JApplication.selectedHistoryRows.removeAll(JApplication.selectedHistoryRows);

	}

	// add s.inoue 2012/11/20
	/**
	 * �폜�������s��
	 */
	public static void deleteWork(String uketukeId)
	{
		// T_KESAI�f�[�^�폜
		try {
			JApplication.kikanDatabase.Transaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		StringBuilder workKesaiQuery = new StringBuilder();
		try{

			workKesaiQuery.append("DELETE FROM T_KESAI_WK");
			workKesaiQuery.append(" WHERE UKETUKE_ID = ");
			workKesaiQuery.append(JQueryConvert.queryConvert(uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(workKesaiQuery.toString());

		}catch(SQLException err)
		{
			logger.error(err.getMessage());
		}

		// T_KESAI_SYOUSAI�f�[�^�폜
		StringBuilder workSyousaiQuery = new StringBuilder();
		try{

			workSyousaiQuery.append("DELETE FROM T_KESAI_SYOUSAI_WK");
			workSyousaiQuery.append(" WHERE UKETUKE_ID = ");
			workSyousaiQuery.append(JQueryConvert.queryConvert(uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(workSyousaiQuery.toString());

		}catch(SQLException err)
		{
			logger.error(err.getMessage());
		}

		// eidt s.inoue 2012/11/20
		try {
			JApplication.kikanDatabase.Commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/* �{�^���A�N�V���� */
	public void closeButtton_keyPerformed(KeyEvent e) {
		dispose();
	}

	// �C�x���g����
	@Override
	public void actionPerformed(ActionEvent ae) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

// del s.inoue 2012/11/08
//	// �`�F�b�N�{�b�N�X�ێ�
//	private ArrayList<Integer> getSelectedRow(){
//		JOutputGetujiSearchListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y"))
//				selectedData.add(i);
//		}
//		return selectedData;
//	}
//
//	// �`�F�b�N�{�b�N�X�ݒ�
//	private void setSelectedRow(ArrayList<Integer> selectedRows){
//		// del s.inoue 2012/11/02
//		// JOutputGetujiSearchListFrameData vo = null;
//		for (int i = 0; i < selectedRows.size(); i++) {
//			// vo = (JOutputGetujiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(i));
//			if (grid.getVOListTableModel().getRowCount()>0)
//			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
//		}
//	}
//
//	// add s.inoue 2011/04/11
//	private class ActionAutoReloadThread extends Thread {
//	    public ActionAutoReloadThread () {
//	    }
//	    public void run() {
//            try {
//                Thread.sleep(100);
//                setSelectedRow(selectedData);
//            } catch (InterruptedException e) {
//                 // Interrupted���ꂽ�ꍇ�́A���ɉ��������ɏI������
//            }
//	    }
//	}
}
