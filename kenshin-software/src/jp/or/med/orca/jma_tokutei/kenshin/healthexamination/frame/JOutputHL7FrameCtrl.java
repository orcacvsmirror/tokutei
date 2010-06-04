//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.*;
//import javax.swing.table.JTableHeader;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//import java.awt.event.*;
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//
//import jp.or.med.orca.jma_tokutei.common.app.JPath;
//import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.component.IDialog;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.filectrl.JDirChooser;
//import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JOutputHL7;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JInputKessaiDataFrameCtrl;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableColumnModel;
//
//import org.apache.log4j.Logger;
//
///**
// * �����EHL7�o�͉�ʐ���
// *
// * ���������G�Ȃ��߁A�S�̓I�Ƀ��t�@�N�^�����O�Ə����̌��������s�Ȃ����B
// */
//public class JOutputHL7FrameCtrl extends JOutputHL7Frame
//{
//	/** �ی��Ҕԍ��A�x����s�@�֑I�𗓂́A�ԍ��Ɩ��̂̋�؂蕶���� */
//
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";  //  @jve:decl-index=0:
//	private static final String JISSIKUBUN_TOKUTEIKENSHIN = "1";  //  @jve:decl-index=0:
//	private static final String SYUBETSU_CODE_6_DISPLAY_NAME = "���茒�f�@�֖��͓���ی��w���@�ւ���ی���";
//	private static final String SYUBETSU_CODE_1_DISPLAY_NAME = "���茒�f�@�֖��͓���ی��w���@�ւ����s�@��";
//
//	private static org.apache.log4j.Logger logger =
//		Logger.getLogger(JOutputHL7FrameCtrl.class);
//
//	// �t���[���̏�Ԃ��Ǘ�����
//	public enum JOutputHL7FrameState {
//		/* ������� */
//		STATUS_INITIALIZED,
//		/* ������ */
//		STATUS_AFTER_SEARCH,
//		/* ������ */
//		STATUS_AFTER_SEIKYU,
//		/* HL7�ϊ��� */
//		STATUS_AFTER_HL7
//	}
//
//	// �S�I���{�^���̏��
//	boolean isAllSelect = true;
//
//	/** �������ʁi��ʏ�̃f�[�^�j */
//	private ArrayList<Hashtable<String, String>> result = null;  //  @jve:decl-index=0:
//
//	private JOutputHL7FrameState state = JOutputHL7FrameState.STATUS_INITIALIZED;  //  @jve:decl-index=0:
//	private Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
//	private Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
//	JSimpleTable model = new JSimpleTable();
//	private IDialog messageDialog;
//	private JFocusTraversalPolicy focusTraversalPolicy = null;  //  @jve:decl-index=0:
//
//	/**
//	 * �t���[���̏�Ԃɂ���ĉ�����{�^���Ȃǂ𐧌䂷��
//	 */
//	public void buttonCtrl()
//	{
//		switch(state)
//		{
//		case STATUS_INITIALIZED:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_HL7Output.setEnabled(false);
//			jButton_HL7Copy.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			// del ver2 s.inoue 2009/07/27
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_SEARCH:
//			jButton_Seikyu.setEnabled(true);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_HL7Output.setEnabled(false);
//			jButton_HL7Copy.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
//			forbitCells.clear();
//			break;
//
//		case STATUS_AFTER_SEIKYU:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(true);
//			jButton_HL7Output.setEnabled(true);
//			jButton_HL7Copy.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_HL7:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_HL7Output.setEnabled(false);
//			jButton_HL7Copy.setEnabled(true);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//		}
//	}
//
//	/**
//	 * �e�[�u���ɕ\��������e���X�V����B
//	 */
//	public int tableRefresh()
//	{
//		JOutputHL7FrameData validatedData = new JOutputHL7FrameData();
//
//		/* �R���{�{�b�N�X�őI������Ă���ی��Ҕԍ��A���̂��擾����B */
//		String hokenjaNumberAndNameString = (String)jComboBox_HokenjyaNumber.getSelectedItem();
//		String hokenjaNumber = "";
//		String hokenjaName = "";
//		if (hokenjaNumberAndNameString != null && ! hokenjaNumberAndNameString.isEmpty()) {
//			String[] hokenjaNumberAndName = hokenjaNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//			hokenjaNumber = hokenjaNumberAndName[0];
//
//			if (hokenjaNumberAndName.length > 1) {
//				hokenjaName = hokenjaNumberAndName[1];
//			}
//		}
//
//		/* �R���{�{�b�N�X�őI������Ă���x����s�@�֔ԍ��A���̂��擾����B */
//		String daikoNumberAndNameString = (String)jComboBox_SeikyuKikanNumber.getSelectedItem();
//		String daikoNumber = "";
//		String daikoName = "";
//		if (daikoNumberAndNameString != null && ! daikoNumberAndNameString.isEmpty()) {
//			String[] daikoNumberAndName = daikoNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//			daikoNumber = daikoNumberAndName[0];
//
//			if (daikoNumberAndName.length > 1) {
//				daikoName = daikoNumberAndName[1];
//			}
//		}
//
//		if(
//				/* ��ی��ҏؓ��L�� */
//				validatedData.setHihokenjyasyo_kigou(jTextField_Hihokenjyasyo_kigou.getText()) &&
//				/* ��ی��ҏؓ��ԍ� */
//				validatedData.setHihokenjyasyo_Number(jTextField_Hihokenjyasyo_Number.getText()) &&
//				/* ���{���i�J�n�j */
//				validatedData.setJissiBeginDate(jTextField_JissiBeginDate.getText()) &&
//				/* ���{���i�I���j */
//				validatedData.setJissiEndDate(jTextField_JissiEndDate.getText()) &&
//				/* �ϊ����i�J�n�j */
//				validatedData.setHL7BeginDate(jTextField_HL7BeginDate.getText()) &&
//				/* �ϊ����i�I���j */
//				validatedData.setHL7EndDate(jTextField_HL7EndDate.getText()) &&
//				/* �ی��ؔԍ� */
//				validatedData.setHokenjyaNumber(hokenjaNumber) &&
//				/* �ی��Җ��� */
//				validatedData.setHokenjyaName(hokenjaName) &&
//				/* ������@�֔ԍ� */
//				validatedData.setSeikyuKikanNumber(daikoNumber) &&
//				/* ������@�֖��� */
//				validatedData.setSeikyuKikanName(daikoName) &&
//				/* ���� */
//				validatedData.setName(jTextField_Name.getText())
//		)
//		{
//			/* Where ���������SQL���쐬����B */
//			StringBuffer queryBuffer = this.createSearchQueryWithoutCondition();
//
//			/* ��������t������B */
//			this.appendCondition(queryBuffer, validatedData);
//
//			/* SQL�����s����B */
//			try{
//				result = JApplication.kikanDatabase.sendExecuteQuery(queryBuffer.toString());
//			}catch(SQLException f){
//				f.printStackTrace();
//			}
//
//			/* �������ʂ��e�[�u���ɒǉ�����B */
//			this.addRowToTable();
//
//			return result.size();
//		}
//		// �o���f�[�^�ň������������ꍇ
//		return 0;
//	}
//
//	/**
//	 * �������ʂ��e�[�u���ɒǉ�����B
//	 */
//	private void addRowToTable() {
//		Hashtable<String, String> resultItem;
//		model.clearAllRow();
//		for(int i = 0;i < result.size();i++ )
//		{
//			Vector<String> row = new Vector<String>();
//			resultItem = result.get(i);
//
//			row.add(null);
//			// add ver2 s.inoue 2009/06/23
//			/* �N�x */
//			row.add(resultItem.get("NENDO"));
//
//			/* ��f�������ԍ� */
//			row.add(resultItem.get("JYUSHIN_SEIRI_NO"));
//			/* �����i�J�i�j */
//			row.add(resultItem.get("KANANAME"));
//			/* �����i���O�j */
//			row.add(resultItem.get("NAME"));
//
//			/* ���� */
//			String sexCode = resultItem.get("SEX");
//			String sexText = "";
//			if (sexCode.equals("1")) {
//				sexText = "�j��";
//			}
//			else if(sexCode.equals("2")){
//				sexText = "����";
//			}
//			row.add(sexText);
//
//			/* ���N���� */
//			row.add(resultItem.get("BIRTHDAY"));
//			/* ���f���{�� */
//			row.add(resultItem.get("KENSA_NENGAPI"));
//
//			/* �ϊ����{�� */
//			// edit ver2 s.inoue 2009/04/02
//			//row.add(resultItem.get("TUTI_NENGAPI"));
//			row.add(resultItem.get("HENKAN_NITIJI"));
//
//			/* �ی��Ҕԍ� */
//			row.add(resultItem.get("HKNJANUM"));
//			/* ��ی��ҏؓ��L�� */
//			row.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
//			/* ��ی��ҏؓ��ԍ� */
//			row.add(resultItem.get("HIHOKENJYASYO_NO"));
//
//			model.addData(row);
//		}
//	}
//
//	/**
//	 * ��������t������B
//	 */
//	private void appendCondition(StringBuffer Query, JOutputHL7FrameData validatedData) {
//		/* ����������t������B */
//		ArrayList<String> conditions = new ArrayList<String>();
//
//		/* ��l�̃e�L�X�g�t�B�[���h�͏������珜�O���� */
//
//		String jyushinkenId = jTextField_JyushinKenID.getText();
//		if (jyushinkenId != null && ! jyushinkenId.isEmpty()) {
//			conditions.add(" KOJIN.JYUSHIN_SEIRI_NO CONTAINING " + JQueryConvert.queryConvert(jyushinkenId));
//		}
//
//		String hihokenjyasyo_kigou = validatedData.getHihokenjyasyo_kigou();
//		if( !hihokenjyasyo_kigou.isEmpty() )
//		{
//			conditions.add(" KOJIN.HIHOKENJYASYO_KIGOU CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_kigou));
//		}
//		String hihokenjyasyo_Number = validatedData.getHihokenjyasyo_Number();
//		if( !hihokenjyasyo_Number.isEmpty() )
//		{
//			conditions.add(" KOJIN.HIHOKENJYASYO_NO CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_Number));
//		}
//		String hokenjyaNumber = validatedData.getHokenjyaNumber();
//		if( !hokenjyaNumber.isEmpty() )
//		{
//			conditions.add(" KOJIN.HKNJANUM CONTAINING " + JQueryConvert.queryConvert(hokenjyaNumber));
//		}
//		String seikyuKikanNumber = validatedData.getSeikyuKikanNumber();
//		if( !seikyuKikanNumber.isEmpty() )
//		{
//			conditions.add(" KOJIN.SIHARAI_DAIKOU_BANGO CONTAINING " + JQueryConvert.queryConvert(seikyuKikanNumber));
//		}
//		String name2 = validatedData.getName();
//		if( !name2.isEmpty() )
//		{
//			conditions.add(" KOJIN.KANANAME CONTAINING " + JQueryConvert.queryConvert(name2));
//		}
//
//		String jissiBeginDate = validatedData.getJissiBeginDate();
//		if( !jissiBeginDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI >= " + JQueryConvert.queryConvert(jissiBeginDate));
//		}
//		String jissiEndDate = validatedData.getJissiEndDate();
//		if( !jissiEndDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI <= " + JQueryConvert.queryConvert(jissiEndDate));
//		}
//
//		String beginDate = validatedData.getHL7BeginDate();
//		if( !beginDate.isEmpty() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI >= " + JQueryConvert.queryConvert(beginDate));
//			conditions.add(" TOKUTEI.HENKAN_NITIJI >= " + JQueryConvert.queryConvert(beginDate));
//		}
//		String endDate = validatedData.getHL7EndDate();
//		if( !endDate.isEmpty() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI <= " + JQueryConvert.queryConvert(endDate));
//			conditions.add(" TOKUTEI.HENKAN_NITIJI <= " + JQueryConvert.queryConvert(endDate));
//		}
//		if( jCheckBox_IsPermitHL7.isSelected() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI IS NULL ");
//			conditions.add(" TOKUTEI.HENKAN_NITIJI IS not NULL ");
//		}else{
//			conditions.add(" TOKUTEI.HENKAN_NITIJI IS NULL ");
//		}
//
//		/* ���N�x */
//		// add ver2 s.inoue 2009/06/23
//		// ���N�x��\��
//		if (jCheckBox_JisiYear.isSelected()) {
//			conditions.add(" GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())));
//		}
//
//		// ���ʓo�^�u���A�ρv�́u���v�̃f�[�^�͕\�����Ȃ�
//		conditions.add(" TOKUTEI.KEKA_INPUT_FLG = '2' ");
//
//		/* ����������t������ */
//		if (conditions.size() > 0) {
//			Query.append(" WHERE ");
//
//			for (ListIterator<String> iter = conditions.listIterator(); iter.hasNext();) {
//				String condition = iter.next();
//				Query.append(condition);
//
//				if (iter.hasNext()) {
//					Query.append(" AND ");
//				}
//			}
//			// add ver2 s.inoue 2009/06/12
//			// �\����
//			Query.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//		}
//	}
//
//	/**
//	 * �����p�� SQL ���쐬����B
//	 */
//	private StringBuffer createSearchQueryWithoutCondition() {
//		// �������ڂ𔲂����N�G��
//		StringBuffer Query = new StringBuffer(
//				"SELECT DISTINCT " +
//					/* ��tID */
//					"KOJIN.UKETUKE_ID," +
//					/* ���N���� */
//					"KOJIN.BIRTHDAY," +
//					/* ���� */
//					"KOJIN.SEX," +
//					/* ���� */
//					"KOJIN.NAME," +
//					/* ��f�������ԍ� */
//					"KOJIN.JYUSHIN_SEIRI_NO," +
//					/* ��ی��ҏؓ��L�� */
//					"KOJIN.HIHOKENJYASYO_KIGOU," +
//					/* ��ی��ҏؓ��ԍ� */
//					"KOJIN.HIHOKENJYASYO_NO," +
//					/* �����N���� */
//					"TOKUTEI.KENSA_NENGAPI," +
//
//					/* �ϊ����� */
//					// edit ver2 s.inoue 20090401
//					// "TOKUTEI.TUTI_NENGAPI," +
//					"TOKUTEI.HENKAN_NITIJI," +
//
//					/* �ی��Ҕԍ��i�l�j */
//					"KOJIN.HKNJANUM," +
//					/* �x����s�@�֔ԍ� */
//					"KOJIN.SIHARAI_DAIKOU_BANGO," +
//					/* �J�i���� */
//					"KOJIN.KANANAME, " +
//					/* �N�x */
//					// add ver2 s.inoue 2009/06/23
//					"GET_NENDO.NENDO " +
//
//				"FROM" +
//					" T_KOJIN AS KOJIN " +
//
//				"LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI " +
//				"ON " +
//				"KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID " +
//				"LEFT JOIN T_KESAI_WK AS KESAI " +
//				"ON " +
//				"KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID " +
//				// add ver2 s.inoue 2009/06/23
//				" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO " +
//				// edit ver2 s.inoue 2009/07/13
//				// " else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO " +
//				" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR " +
//				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR " +
//				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' " +
//				" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 " +
//				" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO " +
//
//				" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO " +
//				" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID " +
//				" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI "
//		);
//		return Query;
//	}
//
//	/**
//	 * �R���X�g���N�^
//	 */
//	public JOutputHL7FrameCtrl()
//	{
//		this.initializeCtrl();
//	}
//
//	/**
//	 * ����N���X�̏�����
//	 */
//	private void initializeCtrl() {
//
//		state = JOutputHL7FrameState.STATUS_INITIALIZED;
//
//		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
//		jPanel_TableArea.add(scroll);
//
//		/* �e�[�u��������������B */
//		this.initializeTable();
//
//		// edit ver2 s.inoue 2009/06/23
//		jCheckBox_IsPermitHL7.setSelected(true);
//		jCheckBox_JisiYear.setSelected(true);
//
//		/* �ی��Ҕԍ��R���{�{�b�N�X�̐ݒ� */
//		this.initializeHokenjaNumComboBox();
//
//		/* �����@�֔ԍ��R���{�{�b�N�X������������B */
//		this.initializeSeikyukikanNumberComboBox();
//
//		// ��ʃR�[�h�R���{�{�b�N�X�̐ݒ�
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_1_DISPLAY_NAME);
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_6_DISPLAY_NAME);
//
//		/* �{�^���̏�Ԃ�ύX����B */
//		this.buttonCtrl();
//
//		/* ���b�Z�[�W�_�C�A���O������������B�� */
//		try {
//			messageDialog = DialogFactory.getInstance().createDialog(this, new JButton());
//			messageDialog.setShowCancelButton(false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_JyushinKenID);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JyushinKenID);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Name);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_kigou);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_Number);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_HokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_SeikyuKikanNumber);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiBeginDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiEndDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_HL7BeginDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_HL7EndDate);
//		this.focusTraversalPolicy.addComponent(this.model);
//		this.focusTraversalPolicy.addComponent(this.jButton_AllSelect);
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_IsPermitHL7);
//		this.focusTraversalPolicy.addComponent(this.jButton_Search);
//		this.focusTraversalPolicy.addComponent(this.jButton_Seikyu);
//		this.focusTraversalPolicy.addComponent(this.jButton_SeikyuEdit);
//		this.focusTraversalPolicy.addComponent(this.jButton_HL7Output);
//		this.focusTraversalPolicy.addComponent(this.jButton_HL7Copy);
//		this.focusTraversalPolicy.addComponent(this.jButton_End);
//
//	}
//
//	/**
//	 * �e�[�u��������������B
//	 */
//	private void initializeTable() {
//		/* �񕝂̐ݒ� */
//		// edit ver2 s.inoue 2009/06/23
//		model.setPreferedColumnWidths(new int[]{50, 50, 120, 150, 150, 50, 100, 100, 100, 100, 100, 100});
//
//		// edit ver2 s.inoue 2009/06/23
//		/* �Z���̕ҏW�ۂ�ݒ肷��B */
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
//		this.model.setCellEditForbid(forbitCells);
//
//		model.setCellEditForbid(forbitCells);
//		model.addHeader("");
//		model.addHeader("�N�x");
//		model.addHeader("��f�������ԍ�");
//
//		model.addHeader("�����i�J�i�j");
//		model.addHeader("�����i�����j");
//		model.addHeader("����");
//		model.addHeader("���N����");
//		model.addHeader("���f���{��");
//		model.addHeader("HL7�o�͓�");
//		model.addHeader("�ی��Ҕԍ�");
//		model.addHeader("��ی��ҏؓ��L��");
//		model.addHeader("��ی��ҏؓ��ԍ�");
//		model.setCellCheckBoxEditor(new JCheckBox(), 0);
//
//		/* �J�����w�b�_�̃N���b�N�ɂ����ёւ����\�ɂ���B */
//		model.setAutoCreateRowSorter(true);
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel columns = model.getColumnModel();
//        for(int i=1;i<columns.getColumnCount();i++) {
//
//            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//        }
//
//        searchCondition(true);
//	}
//
//	/**
//	 * �x����s�@�փR���{�{�b�N�X������������
//	 */
//	private void initializeSeikyukikanNumberComboBox() {
//		ArrayList<Hashtable<String, String>> result = null;
//
//		String query = new String("SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
//
//		jComboBox_SeikyuKikanNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> ResultItem = result.get(i);
//			String num = ResultItem.get("SHIHARAI_DAIKO_NO");
//			String name = ResultItem.get("SHIHARAI_DAIKO_NAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_SeikyuKikanNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * �ی��Ҕԍ��A���̂̈ꗗ���擾����B
//	 */
//	private ArrayList<Hashtable<String, String>> getHokenjaNumAndNames() {
//		ArrayList<Hashtable<String, String>> result = null;
//		String query = new String("SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA ORDER BY HKNJANUM");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * �ی��Ҕԍ��R���{�{�b�N�X�̏�����
//	 */
//	private void initializeHokenjaNumComboBox() {
//		ArrayList<Hashtable<String, String>> result = this.getHokenjaNumAndNames();
//
//		jComboBox_HokenjyaNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> resultItem = result.get(i);
//			String num = resultItem.get("HKNJANUM");
//			String name = resultItem.get("HKNJANAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_HokenjyaNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * �I���{�^���������ꂽ�ꍇ �T
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
//		dispose();
//	}
//
//	/**
//	 * �g�k�V�O���o�̓{�^���������ꂽ�ꍇ �S
//	 */
//	public void pushedHL7CopyButton( ActionEvent e )
//	{
//		JDirChooser dirSelect = new JDirChooser();
//
//		switch(state)
//		{
//		case STATUS_AFTER_HL7:
//			JFileChooser Chooser = new JFileChooser(JPath.ZIP_OUTPUT_DIRECTORY_PATH);
//
//			// �t�@�C���I���_�C�A���O�̕\��
//			// TODO �o�͂���t�@�C����I�����Ă��������B
//			if( Chooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
//			{
//				// TODO �o�͐�̃t�H���_��I�����Ă��������B
//				if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION )
//				{
//					try {
//						if( JFileCopy.copyFile(
//								Chooser.getSelectedFile().getAbsolutePath(),			// �R�s�[��
//								dirSelect.getSelectedFile().getPath() + File.separator +
//								Chooser.getSelectedFile().getName() ) != true )
//																						// �R�s�[��
//						{
//							JErrorMessage.show("M4720", this);
//						}else{
//							JErrorMessage.show("M4721", this);
//						}
//					} catch (IOException e1) {
//						e1.printStackTrace();
//						JErrorMessage.show("M4720", this);
//					}
//				}
//			}
//
//			state = JOutputHL7FrameState.STATUS_AFTER_HL7;
//		}
//
//		buttonCtrl();
//	}
//
//	/**
//	 * �g�k�V�o�̓{�^���������ꂽ�ꍇ �R
//	 */
//	public void pushedHL7OutputButton( ActionEvent e )
//	{
//		switch(state)
//		{
//			case STATUS_AFTER_SEIKYU:
//				/* datas �́AHL7 �o�͎��ɕK�v�ȏ�� */
//				if( JOutputHL7.RunProcess(datas) )
//				{
//					// ����ɏI�������ꍇ
//					state = JOutputHL7FrameState.STATUS_AFTER_HL7;
//					tableRefresh();
//				}
//
//				break;
//		}
//		buttonCtrl();
//
//		// HL7�o�͏����I�����Ƀ��b�Z�[�W�{�b�N�X��\��
//		messageDialog.setMessageTitle("�g�k�V�o��");
//
//		String message = "";
//		if (state == JOutputHL7FrameState.STATUS_AFTER_HL7) {
//			message = "HL7�o�͏������I�����܂���";
//		}
//		else {
//			message = "HL7�o�͏����Ɏ��s���܂���";
//		}
//		messageDialog.setText(message);
//		messageDialog.setVisible(true);
//	}
//
//	/**
//	 * ���������{�^���������ꂽ�ꍇ
//	 *
//	 * Modified 2008/04/01 �ጎ �ǐ����Ⴍ�A��Q�̒���������Ȃ��߁A�S�ʓI�ɏ��������B
//	 */
//	public void pushedSeikyuButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEARCH:
//			int count = 0;
//
//			boolean notExtMessage = false;
//
//			datas = new Vector<JKessaiProcessData>();
//
//			for( int i = 0;i < result.size();i++ )
//			{
//				if( Boolean.TRUE == (Boolean)model.getData(i,0)  )
//				{
//					count++;
//
//					Hashtable<String, String> item = result.get(i);
//
//					JKessaiProcessData dataItem = new JKessaiProcessData();
//					/* ��tID */
//					dataItem.uketukeId = item.get("UKETUKE_ID");
//					/* ��ی��ҏؓ��L�� */
//					dataItem.hiHokenjyaKigo = item.get("HIHOKENJYASYO_KIGOU");
//					/* ��ی��ҏؓ��ԍ� */
//					dataItem.hiHokenjyaNumber = item.get("HIHOKENJYASYO_NO");
//					/* �ی��Ҕԍ��i�l�j */
//					dataItem.hokenjyaNumber = item.get("HKNJANUM");
//					/* �������{�� */
//					dataItem.KensaDate = item.get("KENSA_NENGAPI");
//					/* �x����s�@�֔ԍ� */
//					dataItem.daikouKikanNumber = item.get("SIHARAI_DAIKOU_BANGO");
//
//					/* �J�i���� */
//					dataItem.kanaName = item.get("KANANAME");
//					/* ���� */
//					dataItem.sex = item.get("SEX");
//					/* ���N���� */
//					dataItem.birthday = item.get("BIRTHDAY");
//
//					if (dataItem.hiHokenjyaNumber == null || dataItem.hiHokenjyaNumber.isEmpty()) {
//						/* �����̓G���[,��ی��ҏؓ��ԍ������͂���Ă��܂���B�ꗗ�Ŕ�ی��ҏؓ��ԍ����m�F���Ă��������B
//						 * [���s]�@�����i�J�i�j[%s]�A����[%s]�A���N����[%s] */
//						String sexName = dataItem.sex.equals("1") ? "�j��" : "����";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4751", this, params);
//						return;
//					}
//
//					if (dataItem.KensaDate == null || dataItem.KensaDate.isEmpty()) {
//						/* ���̓G���[,���f���ʃf�[�^�����݂��܂���B[���s]�@�����i�J�i�j[%s]�A����[%s]�A���N����[%s] */
//						String sexName = dataItem.sex.equals("1") ? "�j��" : "����";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4753", this, params);
//						return;
//					}
//
//					/*
//					 * ��ʃR�[�h���i�[����
//					 */
//					if( dataItem.daikouKikanNumber != null && ! dataItem.daikouKikanNumber.isEmpty() )
//					{
//						dataItem.syubetuCode = "1";
//					}else{
//						dataItem.syubetuCode = "6";
//					}
//
//					// ���{�敪���i�[����i���茒�f�� 1 �Œ�j
//					dataItem.jissiKubun = JISSIKUBUN_TOKUTEIKENSHIN;
//
//					/* �����敪���i�[����B */
//					String seikyuKubun = getSeikyuKubun(i);
//
//					if (seikyuKubun == null || seikyuKubun.isEmpty()) {
//						/* M4732�F�G���[,�����敪�̎擾�Ɏ��s���܂����B,0,0 */
//						JErrorMessage.show("M4733", this);
//						return;
//					}
//
//					dataItem.seikyuKubun = seikyuKubun;
//
//					String[] registKensa= JInputKessaiDataFrameCtrl.isNotExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (registKensa != null){
//						// edit s.inoue 20081119
//						if (!notExtMessage){
//							JErrorMessage.show("M3635", this);
//							notExtMessage = true;
//						}
//					}
//
//					String[] existKensa= JInputKessaiDataFrameCtrl.isExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (existKensa != null){
//						if (!notExtMessage){
//							JErrorMessage.show("M3636", this );
//							notExtMessage = true;
//						}
//					}
//
//					datas.add(dataItem);
//				}
//			}
//
//			if( count != 0 )
//			{
//				try{
//
//					// ���ρA�W�v��tran�J�n
//					JApplication.kikanDatabase.Transaction();
//
//					JOutputHL7FrameData validatedData = new JOutputHL7FrameData();
//
//					if( validatedData.setSyubetuCode((String)jComboBox_SyubetuCode.getSelectedItem()))
//					{
//						try {
//							/* ���Ϗ��� */
//							JKessaiProcess.runProcess(datas, JApplication.kikanNumber);
//
//						} catch (Exception e1) {
//							e1.printStackTrace();
//							JErrorMessage.show("M4730", this);
//							break;
//						}
//
//						/* �W�v���� */
//						if(JSyuukeiProcess.RunProcess(datas) == false)
//						{
//							JErrorMessage.show("M4731", this);
//							break;
//						}
//
//						state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//
//						messageDialog.setMessageTitle("��������");
//						messageDialog.setText("�����������I�����܂���");
//						messageDialog.setVisible(true);
//
//						// ���ρA�W�vtran�I��
//						JApplication.kikanDatabase.Commit();
//					}
//				}catch(Exception ex){
//					logger.error(ex.getMessage());
//				}
//			}
//		}
//		buttonCtrl();
//	}
//
//	private String getSeikyuKubun(int i) {
//		// �����敪���i�[����
//		String query = "SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE " +
//				"UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//				"KENSA_NENGAPI = " + JQueryConvert.queryConvert(result.get(i).get("KENSA_NENGAPI"));
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
//
//		String seikyuKubun = null;
//		if (tbl != null && tbl.size() == 1) {
//			seikyuKubun = tbl.get(0).get("SEIKYU_KBN");
//		}
//
//		return seikyuKubun;
//	}
//
//	/**
//	 * �x����s�@�ւ��ݒ肳��Ă��邩�𒲂ׂ�B
//	 */
//	private boolean existsShiharaiDaikoKikan(int i) {
//
//		/* �x����s�@�֔ԍ��̗L���𒲂ׂ� */
//		String Query = "select * from T_KOJIN " +
//									" where " +
//									" UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//									" SIHARAI_DAIKOU_BANGO IS NULL";
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
//
//		if( ! tbl.isEmpty() ){
//			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * �����f�[�^�ҏW�����{�^���������ꂽ�ꍇ 2
//	 */
//	public void pushedSeikyuEditButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEIKYU:
//			// ������I������Ă���ꍇ�̂�
//			if( model.getSelectedRowCount() == 1 )
//			{
//				int[] selection = model.getSelectedRows();
//				int modelRow=0;
//	            for (int i = 0; i < selection.length; i++) {
//
//	                // �e�[�u�����f���̍s���ɕϊ�
//	                modelRow = model.convertRowIndexToModel(selection[i]);
//	                System.out.println("View Row: " + selection[i] + " Model Row: " + modelRow);
//	            }
//
//	            Hashtable<String, String> resultItem = result.get(modelRow);
//
//				String uketukeId = resultItem.get("UKETUKE_ID");
//				String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
//				String hihokenjyasyoNo = resultItem.get("HIHOKENJYASYO_NO");
//				String kensaNengapi = resultItem.get("KENSA_NENGAPI");
//
//				JInputKessaiDataFrameCtrl ctrl = new JInputKessaiDataFrameCtrl(
//						uketukeId,
//						hihokenjyasyoKigou,
//						hihokenjyasyoNo,
//						kensaNengapi,
//						datas);
//
//				jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,ctrl);
//
//				state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//				break;
//			}
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * �S�đI���{�^���������ꂽ�ꍇ 7
//	 */
//	public void pushedAllSelectButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEARCH:
//			for(int i = 0;i < model.getRowCount();i++ )
//			{
//				if( isAllSelect )
//					model.setData(true, i, 0);
//				else
//					model.setData(false, i, 0);
//			}
//
//			if( isAllSelect )
//			{
//				isAllSelect = false;
//			}else{
//				isAllSelect = true;
//			}
//
//			break;
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * �����{�^���������ꂽ�ꍇ �U
//	 */
//	public void pushedSearchButton( ActionEvent e )
//	{
//		searchCondition(false);
//	}
//
//	private void searchCondition(boolean initialize){
//		int rowCount;
//
//		buttonCtrl();
//
//		switch(state)
//		{
//		/* FALLTHROUGH */
//		case STATUS_INITIALIZED:
//		case STATUS_AFTER_SEARCH:
//		case STATUS_AFTER_SEIKYU:
//		case STATUS_AFTER_HL7:
//			rowCount = tableRefresh();
//			if( rowCount == 0 )
//			{
//				state = JOutputHL7FrameState.STATUS_INITIALIZED;
//			}else{
//				// add ver2 s.inoue 2009/06/24
//				if (!initialize){
//					// �������ʂɂg�k�V�ϊ��ς݂̎�f�҂����邩�ǂ����`�F�b�N����
//					for(int i = 0; i < model.getRowCount();i++ )
//					{
//						// edit ver2 s.inoue 2009/06/23
//						//Object tutiNengapi = model.getData(i, 7);
//						Object tutiNengapi = model.getData(i, 8);
//						if(tutiNengapi != null && ! tutiNengapi.equals("") )
//						{
//							RETURN_VALUE Ret = JErrorMessage.show("M4740", this);
//							if(Ret == RETURN_VALUE.NO)
//							{
//								state = JOutputHL7FrameState.STATUS_INITIALIZED;
//								buttonCtrl();
//								model.clearAllRow();
//								return;
//							}else{
//								break;
//							}
//						}
//					}
//				}
//				state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
//			}
//			break;
//		}
//		buttonCtrl();
//	}
//
//	public void itemStateChanged( ItemEvent e )
//	{
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		Object source = e.getSource();
//		// 5
//		if( source == jButton_End			)
//		{
//			pushedEndButton( e );
//		}
//
//		// 3
//		else if( source == jButton_HL7Copy		)
//		{
//			pushedHL7CopyButton( e );
//		}
//
//		// HL7 �o�̓{�^��
//		else if( source == jButton_HL7Output	)
//		{
//			pushedHL7OutputButton( e );
//		}
//
//		// ���������{�^��
//		else if( source == jButton_Seikyu		)
//		{
//			pushedSeikyuButton( e );
//		}
//
//		// 2
//		else if( source == jButton_SeikyuEdit		)
//		{
//			pushedSeikyuEditButton( e );
//		}
//
//		// 7
//		else if( source == jButton_AllSelect	)
//		{
//			pushedAllSelectButton( e );
//		}
//
//		// 6
//		else if( source == jButton_Search		)
//		{
//			pushedSearchButton( e );
//		}
//	}
//}
