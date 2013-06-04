//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Component;
//import java.awt.FileDialog;
//import java.awt.event.ActionEvent;
//import java.awt.event.ItemEvent;
//import java.awt.event.KeyEvent;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.lang.reflect.InvocationTargetException;
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;
//import java.util.TreeMap;
//import java.util.Vector;
//
//import javax.swing.filechooser.FileFilter;
//// add s.inoue 20081001
//import javax.swing.ButtonGroup;
//import javax.swing.JButton;
//import javax.swing.JProgressBar;
//
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.app.JPath;
//import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.component.IDialog;
//import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
//import jp.or.med.orca.jma_tokutei.common.convert.JEraseSpace;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
//import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessageDialogFrameCtrl;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TShiharaiDao;
//import jp.or.med.orca.jma_tokutei.common.sql.model.THokenjya;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TShiharai;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.common.zip.JZipDecompresser;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportErrorResultFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportErrorResultFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportResultFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JOriginalFormatData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectHokenjyaFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectHokenjyaFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectKojinFrame;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectKojinFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectKojinFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectShiharaiFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectShiharaiFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintDefine;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
//
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KihonKensaKoumoku;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKojinRegisterFrameData.T_KOJIN_COLUMN;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKekkaRegisterFrameData;
//import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
//
//import org.w3c.dom.*;
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.DocumentBuilder;
//
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
//import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;
//
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
//
///**
// * �O�����f���ʃf�[�^��荞�݂̃t���[���̃R���g���[��
// */
//public class JImportDataFrameCtrl extends JImportDataFrame
//{
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream Reader = null;
//	private static Vector<Vector<String>> CSVItems = null;
//
//	private static TKojin kojin = new TKojin();
//	private static TKensakekaTokutei kensakekaTokutei = new TKensakekaTokutei();
//	private static TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
//	private static TKensakekaTokuteiDao kensakekaTokuteiDao = null;
//	private static TKensakekaSonotaDao kensakekaSonotaDao = null;
//	private static TKojinDao kojinDao = null;
//	private static THokenjyaDao hokenjyaDao = null;
//	private static TShiharaiDao shiharaiDao = null;
//	private static TNayoseDao tNayoseDao = null;
//
//	private static String ResultMessage = "";
//	private Long preuketukeId = 0L;
//	private Integer prekensaDate = 0;
//	private static final String FLAME_KOJIN_TITLE_MESSAGE = "���������E���ꐶ�N�����̎�f�҂����݂��܂��B";
//	private String BMIFormat = "##.0";
//
//	/* �t�H�[�J�X�ړ����� */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//	private ButtonGroup radioButtonGroup = new ButtonGroup();
//
//	// logger�� object�����B
//    private static org.apache.log4j.Logger logger = Logger.getLogger(JImportDataFrameCtrl.class);
//	private static ArrayList<String>importKenshinItem = new ArrayList<String>();
//	private static HashMap<String, String> koumokuCodeMap = null;
//	// �����{�A����s�\�͎��{�敪���i�[
//	private static HashMap<String, String> koumokuCodeElseMap = null;
//
//	private static JKekkaRegisterFrameData validatedKenshinData = new JKekkaRegisterFrameData();
//	private static JKojinRegisterFrameData validatedKojinData  = null;
//
//	private static JImportErrorResultFrameData Data =null;
//	private static String kikanNumber = null;
//	private static Integer kenshinPatternNumver = null;
//
//	private IDialog patternSelectDialog;
//
//	JImportDataFrameData validateDate = new JImportDataFrameData();
//
//	public JImportDataFrameCtrl()
//	{
//		// add s.inoue 2009/10/14
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//// del s.inoue 2010/04/02
////		this.focusTraversalPolicy.setDefaultComponent(jRadio_SelectDokuji);
////		this.focusTraversalPolicy.addComponent(jRadio_SelectDokuji);
////		this.focusTraversalPolicy.addComponent(jRadio_SelectNitii);
//		this.focusTraversalPolicy.setDefaultComponent(jButton_OpenFile);
//		this.focusTraversalPolicy.addComponent(jButton_OpenFile);
//		this.focusTraversalPolicy.addComponent(jButton_Import);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//
//		// add s.inoue 2009/12/03
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//
//		//���f�Z���^�[���̃R���{�{�b�N�X�̏����ݒ�
//		ArrayList<Hashtable<String,String>> Result = null;
//		Hashtable<String,String> ResultItem;
//		String Query = new String(	"SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME " +
//									"FROM T_KENSACENTER_MASTER " +
//									"ORDER BY KENSA_CENTER_CD ASC");
//		try{
//			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		}catch(SQLException e){
//			e.printStackTrace();
//			JErrorMessage.show("M3320",this);
//		}
//
//		for(int i = 0;i < Result.size();i++ )
//		{
//			ResultItem = Result.get(i);
//			jComboBox_KensaCenterName.addItem(ResultItem.get("CENTER_NAME"));
//		}
//		// del s.inoue 2010/04/02
////		radioButtonGroup.add(jRadio_SelectDokuji);
////		radioButtonGroup.add(jRadio_SelectNitii);
////		jRadio_SelectDokuji.setSelected(true);
//	}
//
//	public boolean validateAsImportPushed(JImportDataFrameData data)
//	{
//		if(validateDate.getFilePath().equals(""))
//		{
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3301",this);
//			return false;
//		}
//		validateDate.setValidateAsDataSet();
//		return true;
//	}
//
//	/* Add 2008/07/23 ��� */
//	/* --------------------------------------------------- */
//	public boolean validateImportJusinSeiriNo(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//
//		// �G���[�̏ꍇ�A��ʂ��Z�b�g
//		if (!data.jusinSeiriNo.toString().equals("")){
//			// ���l�`�F�b�N
//			//if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
//			//	data.errCase=JApplication.ERR_KIND_NUMBER;
//			//	errFlg=true;
//			//}else
//
//			// �K�{�`�F�b�N
//			if (data.jusinSeiriNo.toString().equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else
//			// ���p�`�F�b�N
//			if (JValidate.isAllHankaku(data.jusinSeiriNo.toString()) == false){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			}else
//			// �����`�F�b�N
//			if (JValidate.checkMaxLimit(data.jusinSeiriNo.toString(),11) == false){
//				data.errCase=JApplication.ERR_KIND_DIGIT;
//				errFlg=true;
//			}
//		}
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_JUSIN_SEIRI_NO;
//
//		return errFlg;
//	}
//
//	public boolean validateImportJisiKikanNo(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//
//		try {
//			// �K�{�`�F�b�N
//			if (data.jisiKikanNo.toString().equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else
//			// ���p�`�F�b�N
//			if (JValidate.isAllHankaku(data.jisiKikanNo.toString()) == false){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			}else
//			// �����`�F�b�N
//			if (JValidate.checkMaxLimit(data.jisiKikanNo.toString(),10) == false){
//				data.errCase=JApplication.ERR_KIND_DIGIT;
//				errFlg=true;
//			}else
//			// ��v�`�F�b�N
//			if (!JApplication.kikanNumber.equals(data.jisiKikanNo.toString())){
//				data.errCase=JApplication.ERR_KIND_NOT_EXIST;
//				errFlg=true;
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_JISI_KIKAN_NO;
//
//		return errFlg;
//	}
//
//	public boolean validateImportJisiDate(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//
//		try{
//			String temp = JCalendarConvert.JCtoAD(data.jishiDate);
//			// ���p�`�F�b�N
//			if (JValidate.isAllHankaku(data.jishiDate.toString()) == false){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			}else
//			// ���{���Ó����`�F�b�N
//			if( temp == null )
//			{
//				data.errCase=JApplication.ERR_KIND_DATE;
//				errFlg = true;
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_JISI_DATE;
//
//		return errFlg;
//	}
//
//	public boolean validateImportkanaShimei(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//
//		try{
//			// �K�{�`�F�b�N
//			if (data.kanaShimei.toString().equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else
//
//			// ���p�`�F�b�N
//			if ( !JValidate.isAllHankaku(data.kanaShimei,JApplication.CSV_CHARSET)){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			}else
//
//			// �����`�F�b�N
//			if (JValidate.checkMaxLimit(data.kanaShimei,17) == false){
//				data.errCase=JApplication.ERR_KIND_DIGIT;
//				errFlg=true;
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_SHIMEI;
//
//		return errFlg;
//	}
//
//	// add s.inoue 2010/02/03
//	public boolean validateImportNitiiFormatkanaShimei(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//
//		try{
//			// �K�{�`�F�b�N
//			if (data.kanaShimei.toString().equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else
//
//			// ���p�`�F�b�N
//			if ( !JValidate.isAllZenkaku(data.kanaShimei)){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			}else
//
//			// �����`�F�b�N
//			if (JValidate.checkMaxLimit(data.kanaShimei,40) == false){
//				data.errCase=JApplication.ERR_KIND_DIGIT;
//				errFlg=true;
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_SHIMEI;
//
//		return errFlg;
//	}
//
//	public boolean validateImportseiNenGapi(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		String temp ="";
//
//		try{
//			// ���N�������t�Ó����`�F�b�N
//			if (data.seiNenGapi.toString().equals("") &&
//					data.jusinSeiriNo.toString().equals("")){
//					// keyNotExist
//					data.errCase=JApplication.ERR_KIND_DATE;
//					errFlg = true;
//			// edit ver2 s.inoue 2009/06/24
//			}else if(!data.seiNenGapi.toString().equals("")){
//
//				temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
//				if( temp == null ){
//					data.errCase=JApplication.ERR_KIND_DATE;
//					errFlg = true;
//				}
//
//				// �ĕϊ�����(�a�����)
//				if (!JValidate.checkByte(data.seiNenGapi,8) &&
//					!JValidate.isNumber(data.seiNenGapi.toString())){
//
//					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
//					if( temp == null ){
//						data.errCase=JApplication.ERR_KIND_DATE;
//						errFlg = true;
//					}
//				}
//
//				// ���l�`�F�b�N
//				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
//					data.errCase=JApplication.ERR_KIND_NUMBER;
//					errFlg=true;
//				}
//				// ���p�`�F�b�N
//				if ( !JValidate.isAllHankaku(data.seiNenGapi,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_SEINENGAPI;
//		return errFlg;
//	}
//
//	// add s.inoue 2010/02/03
//	public boolean validateImportNitiiFormatseiNenGapi(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		String temp ="";
//
//		try{
//			// ���N�������t�Ó����`�F�b�N
//			// �K�{�`�F�b�N
//			if (data.seiNenGapi.toString().equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			// edit s.inoue 2010/02/03
//			}else{
//				temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
//				if( temp == null ){
//					data.errCase=JApplication.ERR_KIND_DATE;
//					errFlg = true;
//				}
//
//				// �ĕϊ�����(�a�����)
//				if (!JValidate.checkByte(data.seiNenGapi,8) &&
//					!JValidate.isNumber(data.seiNenGapi.toString())){
//
//					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
//					if( temp == null ){
//						data.errCase=JApplication.ERR_KIND_DATE;
//						errFlg = true;
//					}
//				}
//
//				// ���l�`�F�b�N
//				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
//					data.errCase=JApplication.ERR_KIND_NUMBER;
//					errFlg=true;
//				}
//				// ���p�`�F�b�N
//				if ( !JValidate.isAllHankaku(data.seiNenGapi,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_SEINENGAPI;
//		return errFlg;
//	}
//
//	public boolean validateImportSex(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// ���ʃ`�F�b�N
//		try{
//			if (!data.seiBetu.toString().equals("")){
//				// ���p�`�F�b�N
//				if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}else if( !JValidate.validateSexFlag(data.seiBetu) )
//				{
//					// ���ʑÓ���
//					data.errCase=JApplication.ERR_KIND_SEX;
//					errFlg = true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_SEIBETU;
//		return errFlg;
//	}
//
//	// add s.inoue 2010/02/03
//	public boolean validateImportNitiiFormatSex(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// ���ʃ`�F�b�N
//		try{
//			// ���N�������t�Ó����`�F�b�N
//			// �K�{�`�F�b�N
//			if (data.seiBetu.toString().equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//
//			// ���p�`�F�b�N
//			}else if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			}else if( !JValidate.validateSexFlag(data.seiBetu) )
//			{
//				// ���ʑÓ���
//				data.errCase=JApplication.ERR_KIND_SEX;
//				errFlg = true;
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_SEIBETU;
//		return errFlg;
//	}
//
//	public boolean validateImportNyuBi(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// ���r/�n���`�F�b�N
//		try{
//			if (!data.nyuBi.toString().equals("")){
//				// �����`�F�b�N
//				if (JValidate.checkMaxLimit(data.nyuBi,10) == false){
//					data.errCase=JApplication.ERR_KIND_DIGIT;
//					errFlg=true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_YOUKETU;
//		return errFlg;
//	}
//
//	public boolean validateImportShokuZenGo(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// �H�O/�H��`�F�b�N
//		try{
//			if (!data.shokuZenGo.toString().equals("")){
//				// �����`�F�b�N
//				if (JValidate.checkMaxLimit(data.shokuZenGo,10) == false){
//					data.errCase=JApplication.ERR_KIND_DIGIT;
//					errFlg=true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.ZOKUSEI_FIELD_SHOKUZENGO;
//		return errFlg;
//	}
//
//
//	/*** �ڍ׏�� ***/
//	public boolean validateKensaKoumokuCode(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// �������ڃR�[�h�`�F�b�N
//		try{
//			// �K�{�`�F�b�N
//			if (data.koumokuCd.equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else
//			// ���p�`�F�b�N
//			if ( !JValidate.isAllHankaku(data.koumokuCd,JApplication.CSV_CHARSET)){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			}else
//			// �����`�F�b�N 17���݂̂ɕύX s.inoue 20081114
//			//if (JValidate.checkMaxLimit(data.koumokuCd,17) == false){
//			if (JValidate.checkByte(data.koumokuCd,17) == false){
//				data.errCase=JApplication.ERR_KIND_DIGIT;
//				errFlg=true;
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.KENSA_FIELD_KOUMOKU_CD;
//		return errFlg;
//	}
//
//	public boolean validateImportJisiKbn(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//
//		// ���{�敪�`�F�b�N
//		try{
//			String temp = JValidate.validateJisiKubun(data.jisiKbn);
//
//			// add s.inoue 20081118(�󕠎�����,�g���`�P��)
//			//boolean kensaJisi = checkTokuteiKensaJisi(data);
//
//			// �K�{�`�F�b�N
//			if (data.jisiKbn.toString().equals("")){
//					data.errCase=JApplication.ERR_KIND_EMPTY;
//					errFlg=true;
//			}
//			// ���p�`�F�b�N
//			else if ( !JValidate.isAllHankaku(data.jisiKbn,JApplication.CSV_CHARSET)){
//				data.errCase=JApplication.ERR_KIND_HALF;
//				errFlg=true;
//			// �l�`�F�b�N
//			}
//			else if( temp == null )
//			{
//				data.errCase=JApplication.ERR_KIND_NOT_EXIST;
//				errFlg = true;
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.KENSA_FIELD_JISI_KBN;
//		return errFlg;
//	}
//
//	private boolean checkTokuteiKensaJisi(JImportErrorResultFrameData data)
//	{
//		boolean hantei = false;
//		// �󕠎�����,�g���`�P���̏ꍇ
//		if (KihonKensaKoumoku.KUFUKU_CODES.contains(data.koumokuCd) ||
//				KihonKensaKoumoku.HBA1C_CODES.contains(data.koumokuCd)){
//			// ���ʒl����
//			if (data.kekkaTi.equals("")){
//				hantei=true;
//			}
//		}
//
//		return hantei;
//	}
////	 del 20080918 s.inoue
////	public boolean validateImportHLKbn(JImportErrorResultFrameData data)
////	{
////		boolean errFlg = false;
////		// �ُ�l�敪�`�F�b�N
////		try{
////			if (!data.ijyoKbn.toString().equals("")){
////				// �l�`�F�b�N
////				String temp = JValidate.validateHLKubun(data.ijyoKbn);
////
////				// ���p�`�F�b�N
////				if ( !JValidate.isAllHankaku(data.ijyoKbn,JApplication.CSV_CHARSET)){
////					data.errCase=JApplication.ERR_KIND_HALF;
////					errFlg=true;
////				}
////				else if( temp == null )
////				{
////					data.errCase=JApplication.ERR_KIND_NOT_EXIST;
////					errFlg = true;
////				}
////			}
////		}catch(Exception ex){
////			data.errCase=JApplication.ERR_KIND_TYPE;
////			errFlg=true;
////		}
////
////		if (errFlg)
////			data.errField=JApplication.KENSA_FIELD_IJYO_KBN;
////
////		return errFlg;
////	}
//
//// del 20080918 s.inoue
////	public boolean validateImportKekkaTiKeitai(JImportErrorResultFrameData data)
////	{
////		boolean errFlg = false;
////		// ���ʒl�`�ԃ`�F�b�N
////		try{
////			if (!data.kekkaTiKeitai.toString().equals("")){
////				String temp = JValidate.validateKekkaTiKeitai(data.kekkaTiKeitai);
////
////				// ���p�`�F�b�N
////				if ( !JValidate.isAllHankaku(data.kekkaTiKeitai,JApplication.CSV_CHARSET)){
////					data.errCase=JApplication.ERR_KIND_HALF;
////					errFlg=true;
////				}
////				else if( temp == null )
////				{
////					data.errCase=JApplication.ERR_KIND_NOT_EXIST;
////					errFlg = true;
////				}
////			}
////		}catch(Exception ex){
////			data.errCase=JApplication.ERR_KIND_TYPE;
////			errFlg=true;
////		}
////
////		if (errFlg)
////			data.errField=JApplication.KENSA_FIELD_KEKATI_KEITAI;
////
////		return errFlg;
////	}
//
//	public boolean validateImportKekkaTi(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//
//		try{
//			// ���ʒl�`�F�b�N
//			// �K�{�`�F�b�N
//// del s.inoue 20080916
////			if (data.kekkaTi.equals("")){
////				data.errCase=JApplication.ERR_KIND_EMPTY;
////				errFlg=true;
////			}else
//
//			// edit s.inoue 20080916
//			// ���{�敪��1�̎�
//			if (data.jisiKbn.equals("1"))
//			{
//				// ���p�`�F�b�N
//				if ( !JValidate.isAllHankaku(data.kekkaTi,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}else
//				// �����`�F�b�N
//				if (JValidate.checkMaxLimit(data.kekkaTi,14) == false){
//					data.errCase=JApplication.ERR_KIND_DIGIT;
//					errFlg=true;
//				}
//
//				// add 20080916 s.inoue
//				// ���ʒl�Ȃ� �����{
//				if (data.kekkaTi.equals("")){
//					data.jisiKbn = "0";
//				}
//			}
//
//			// add 20080916 s.inoue
//			// ���ʒl���� �����{���͑���s�\
//			else if( !data.kekkaTi.equals("") &&
//					(data.jisiKbn.equals("0") ||
//					(data.jisiKbn.equals("2"))))
//			{
//				data.errCase=JApplication.ERR_KIND_DATA;
//				errFlg = true;
//			}
//
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.KENSA_FIELD_KEKATI;
//
//		return errFlg;
//	}
//
//	// CSV�t�@�C��format�`�F�b�N
//	// flg|true:���� false:����
//	private boolean inputCsvFileCheck(String FilePath,boolean flg){
//		boolean retCheck = false;
//
//// move s.inoue 2010/10/20 �ړ�
////		// 1.txt�`�����ǂ���
////		if (!FilePath.endsWith(".txt") &&
////				!FilePath.endsWith(".TXT") &&
////					!FilePath.endsWith(".csv") &&
////						!FilePath.endsWith(".CSV"))
////			retCheck = true;
//
//		Vector<String> insertRow = new Vector<String>();
//
//		// �������ʃf�[�^�捞����
//		if (flg){
//			for (int i = 0; i < CSVItems.size(); i++) {
//				insertRow =CSVItems.get(i);
//				int csvCnt = insertRow.size() - 1;
//
//				// 2.������� ��13�ȏ゠�邩
//				if (csvCnt <= 13)
//					retCheck = true;
//
//				// 3.������� - �������ڏ��8�Ŋ���؂��
//				csvCnt = csvCnt - JApplication.CSV_ZOKUSEI_COUNT;
//				int csvKensa = csvCnt % 8;
//				if (csvKensa != 0)
//					retCheck = true;
//			}
//		}
//		return retCheck;
//	}
//
//	/*--------------------------------------nitii-----------------------------------------------
//	/**
//	 * ����t�H�[�}�b�g�f�[�^�̎�荞��
//	 * @param
//	 */
//	public void nitiImport(
//			JImportErrorResultFrameData Data,
//			ArrayList<Hashtable<String, String>> kojinData,
//			ArrayList<Hashtable<String, String>> selectKojinData,
//			Vector<JImportErrorResultFrameData> ErrorResultData)
//	{
//
//		// �o�^�����ϐ�
//		int intregistCnt = 0;
//		boolean retblnALL = false;
//		ResultMessage="";
//
//		// �������ʃf�[�^�捞����
//		for (int i = 0; i < CSVItems.size(); i++) {
//
//			Vector<String> insertRow = new Vector<String>();
//
//			insertRow =CSVItems.get(i);
//
//			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "��");
//
//			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JUSIN_SEIRI_NO));
//			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
//			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SHIMEI));
//			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
//			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SEIBETU));
//			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISIKIKAN_NO));
//
//			// ����������
//			Data.uketukeNo =null;
//			kojinData = null;
//			preuketukeId = 0L;
//			prekensaDate =0;
//
//			// ���ʎ捞�f�[�^(�L�[���)�`�F�b�N����
//			if (checkKensaKekkaKeyNitiiFormatData(Data,ErrorResultData,i,false)){
//				i++;continue;
//			}
//
//			// �l���ʃf�[�^:(�����ԍ�,���f���P�ʂŎ擾)
//			try {
//				if (!Data.jusinSeiriNo.toString().equals(""))
//					kojinData = getKojinData(Data);
//
//				} catch (Exception err) {
//					ResultMessage += GetErrorMessage("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
//					logger.error("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
//					break;
//				}
//
//			// �Y������f�[�^���Ȃ��ꍇ�A�l(����,���N����,����)�Ō���
//			if (kojinData == null ||
//					kojinData.size() == 0){
//				try{
//
//					selectKojinData = getSimeiData(Data);
//
//					if (selectKojinData == null ||
//							selectKojinData.size() == 0){
//						break;
//					}
//
//					// �Y������l�����������ꍇ�A���X�g��\������
//					Hashtable<String, String>selectedKojinData
//						= searchKojinData(selectKojinData,Data,false);
//
//					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);
//
//					kojinData = getSelectKojinData(Data);
//					if (kojinData == null ||
//							kojinData.size() == 0){
//						break;
//					}
//
//				} catch (Exception err) {
//					ResultMessage += GetErrorMessage("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
//					logger.error("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
//					break;
//				}
//			}
//
//			// add s.inoue 2010/02/10
//			// �z�񂩂�\���̂֕ϊ��i Arrays.asList�j
//			HashMap<String, String> csvKoumokuCD = this.registerKoumokuCd(validateDate);
//
//			// ���ʎ捞CSV�t�@�C����DB�̒l�̔�r���s���B
//			// ���̑��e�[�u���̍��ڃR�[�h�P�ʂŏ������s���B
//			for (int j = 0; j < kojinData.size(); j++) {
//
//					Hashtable<String,String> DatabaseItem = kojinData.get(j);
//
//					// del s.inoue 2010/02/17
//					// if (Data.uketukeNo == null){
//					Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
//					//}
//
//					// ��tID���r����i��v�F�o�^�ΏہA�s��v�F�G���[�Ώہj
//					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
//					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
//					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);
//
//// del s.inoue 2010/02/17 1���݂̂̑Ώۂɂ��폜
//					// �f�[�^�x�[�X�ɓo�^(T_KENSAKEKKA_TOKUTEI)
//					// �L�[���(:��tID,�������{��)���ς������o�^�������s��
////					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
////							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
//						try{
//
//							// ���ʎ捞�f�[�^(�������)�`�F�b�N����
//							if (checkKensaKekkaData(Data,ErrorResultData,i)){
//								j++;continue;
//							}
//
//							// ���Ɍ��f���ʃf�[�^�����݂����ꍇ�A�x�����b�Z�[�W��\������B
//							// ���f���{��,�����J�i�� ���ʓo�^����ƃf�[�^�����݂���ׁA�X�V�����̂�
//							// edit s.inoue 2010/02/10
//							String strCsvKoumokuOrder = (String) csvKoumokuCD.get(koumokuCdDB);
//							if (strCsvKoumokuOrder == null){
//								j++;continue;
//							}
//							System.out.println("csv:" + strCsvKoumokuOrder);
//							// ���ڃR�[�h��CSV���Ԃ�n���Ď擾
//							// edit s.inoue 2010/02/17
//							// Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
//							Data.koumokuCd = koumokuCdDB;
//							Data.kekkaTi = Reader.rmQuart(insertRow.get(Integer.parseInt(strCsvKoumokuOrder)));
//
//							// ���ڃR�[�h�`�F�b�N
//							if (checkKoumokuCD(Data,ErrorResultData,i)){
//								j++;continue;
//							}
//
//							boolean existsKensaKekkaData = existsKensaKekkaData(Data);
//
//							if (existsKensaKekkaData) {
//
//								if (!retblnALL){
//									// M3328=�m�F,[%s]�ɂ́A���łɌ��ʃf�[�^�����݂��܂��B�o�^�����ꍇ�A
//									// �ȑO�ɓ��͂������ʃf�[�^�͏����Ă��܂��܂��B�o�^���Ă�낵���ł����H
//									String[] messageParams = {"��t�ԍ�:"+ Data.uketukeNo + "/" + "���f���{��:" + Data.jishiDate + "/" + "����:"+ Data.kanaShimei};
//									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
//									// cancel��
//									if (retValue == RETURN_VALUE.CANCEL) {
//										try {
//										JApplication.kikanDatabase.rollback();
//										}catch (SQLException err) {}
//										return;
//									}else if (retValue == RETURN_VALUE.NO){
//										preuketukeId=Long.valueOf(uketukeIdDB);
//										prekensaDate=Integer.valueOf(kensaDateDB);
//										j++;continue;
//									// add s.inoue 2010/02/17
//									}else if (retValue == RETURN_VALUE.YES){
//										updatekensakekaSonota(Data,insertRow);
//									}else if (retValue == RETURN_VALUE.ALL){
//										retblnALL = true;
//									}
//								}
//							}else{
//								updatekensakekaSonota(Data,insertRow);
//							}
//							intregistCnt++;
//
//							// add s.inoue 2010/03/16
//							// BMI�����v�Z roop�Ō�ɏ���
//							String height = "";
//							String weight = "";
//							if(Data.koumokuCd.equals("9N001000000000001")){
//								height = Data.kekkaTi;
//							}else if (Data.koumokuCd.equals("9N006000000000001")){
//								weight = Data.kekkaTi;
//							}
//							if (j == kojinData.size()-1){
//								String bmi = calcBMI(height, weight);
//								if (bmi.equals(""))break;
//
//								String kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
//								Data.koumokuCd = "9N011000000000001"; //BMI
//								Data.kekkaTi = kensaKekka;
//								updatekensakekaSonota(Data,insertRow);
//							}
//
//						}catch(Exception ex){
//							try {
//								JApplication.kikanDatabase.rollback();
//							} catch (SQLException err) {}
//							ResultMessage += GetErrorMessage("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B",Data.kanaShimei);
//
//							logger.error("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B");
//							break;
//						}
//					}
//
//// del s.inoue 2010/02/03
////					// �L�[��񂪈�v�����猟�����ʓo�^���Ă��萳��
////					// �u���̑�:1�`22�̍���(���I�ɕω�����)�v�̓o�^
////					Integer kensaKoumokuLoop = JApplication.CSV_KENSA_KOUMOKU_LOOP;
////					int cntKoumoku =0;
////
////					while (cntKoumoku < JApplication.CSV_KENSA_LOOP * JApplication.CSV_KENSA_KOUMOKU_LOOP) {
////
////						if (insertRow.size() <= JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku )
////						{
////							cntKoumoku += kensaKoumokuLoop;break;
////						}
////						Data.koumokuCd = Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku));
////
////						if (Data.koumokuCd.equals("")){
////							checkKoumokuCD(Data,ErrorResultData,i);
////							cntKoumoku += kensaKoumokuLoop;continue;
////						}
////
////						try {
////							Long resultCnt = kensakekaSonotaDao.selectByCountPrimaryKey(
////									Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
////							if (resultCnt < 0) {
////								checkKoumokuCD(Data,ErrorResultData,i);
////								cntKoumoku += kensaKoumokuLoop;
////								continue;
////							}
////
////						} catch (Exception e) {
////							logger.error("[�������ʎ捞����]�������ʃf�[�^���̑��f�[�^�擾���A�G���[�ƂȂ�܂����B");
////							break;
////						}
////
////						if(JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(JEraseSpace.EraceSpace(uketukeIdDB)) &&
////							JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(JEraseSpace.EraceSpace(kensaDateDB)) &&
////							 JEraseSpace.EraceSpace(Data.koumokuCd.toString()).equals(JEraseSpace.EraceSpace(koumokuCdDB)))
////						{
////							Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN + cntKoumoku));
////							Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI + cntKoumoku));
////
////							// ���ʎ捞�f�[�^(�������ڏ��)
////							if (checkKensaKekkaDataDetail(Data,ErrorResultData,i)){
////								cntKoumoku += kensaKoumokuLoop;break;
////							}
////
////							// ���f���ʂ��̑��o�^����
////							try{
////								kensakekaSonotaregister(Data);
////
////							}catch(Exception ex){
////								try {
////									JApplication.kikanDatabase.rollback();
////
////								} catch (SQLException err) {
////								}
////								logger.error("[�������ʎ捞����]�������ڏ��o�^���A�G���[�ƂȂ�܂����B");
////								ResultMessage += GetErrorMessage("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B",Data.kanaShimei);
////								break;
////							}
////						}
////
////						cntKoumoku += kensaKoumokuLoop;
////					}
//// del s.inoue 2010/02/17
////			}
//		}
//	}
//
//	// sonota�e�[�u���X�V
//	private void updatekensakekaSonota(JImportErrorResultFrameData Data,Vector<String> insertRow){
//		// Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN ));
//		Data.jisiKbn ="1"; // �t�B�[���h�Ȃ��Œ�
//		// Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI));
//		try {
//			kensakekaSonotaregister(Data);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//	}
//
//	// add s.inoue 2010/02/10
//	// ���ڃR�[�h�o�^
//	private HashMap<String, String> registerKoumokuCd(JImportDataFrameData data){
//
//		HashMap<String, String> hm = new HashMap<String, String>();
//		List arrKoumokuCD = new ArrayList<String>();
//		List arrKoumokuOrder = new ArrayList<String>();
//
//		arrKoumokuCD = Arrays.asList(data.koumokuCD_Situmonhyo);
//		arrKoumokuOrder = Arrays.asList(data.koumokuCD_Situmonhyo_Order);
//
//		for (int i = 0; i < arrKoumokuCD.size(); i++) {
//			hm.put((String)arrKoumokuCD.get(i), (String)arrKoumokuOrder.get(i));
//		}
//
//		return hm;
//	}
//
//	// add s.inoue 2010/01/26
//	/**
//	 * ��荞�ݑO����
//	 * @param FilePath �ǂݍ��ރt�@�C���̃p�X
//	 */
//	private void nitiImportCheck(String FilePath)
//	{
//		String logfile = PropertyUtil.getProperty("log.filename");
//		PropertyConfigurator.configure(logfile);
//
//		ArrayList<Hashtable<String, String>> kojinData=null;
//		ArrayList<Hashtable<String, String>> selectKojinData=null;
//
//		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();
//
//		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();
//
//		// CSV�Ǎ�����
//		Reader = new JCSVReaderStream();
//
//		try {
//
//			Reader.openCSV(FilePath,JApplication.CSV_CHARSET);
//
//			CSVItems = Reader.readAllTable();
//
//			if (inputCsvFileCheck(FilePath,false)){
//				JErrorMessage.show("M3327",this);
//				return;
//			}
//
//		} catch (Exception e) {
//			logger.error("[����t�H�[�}�b�g�捞����]CSV�Ǎ������Ɏ��s���܂����B");
//			JErrorMessage.show("M3329",this);
//			return;
//		}
//
//		// �@��(����,���̑�)�ڑ�����
//		try {
//			Connection connection = JApplication.kikanDatabase.getMConnection();
//
//			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
//					connection,
//					kensakekaTokutei);
//
//			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
//					connection,
//					kensakekaSonota);
//
//			JApplication.kikanDatabase.Transaction();
//
//		} catch (Exception e) {
//			logger.error("[�������ʎ捞����]�@��FDB�ւ̐ڑ������Ɏ��s���܂����B");
//			JErrorMessage.show("M3326",this);
//			return;
//		}
//
//		// �捞��
//		nitiImport(Data,kojinData,selectKojinData,ErrorResultData);
//
//		// add s.inoue 2010/02/17
//		try {
//			JApplication.kikanDatabase.Commit();
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//	}
//	/*--------------------------------------nitii-----------------------------------------------*/
//
//	/*-------------------------------------orgine-----------------------------------------------*/
//	/**
//	 * �f�[�^�̎�荞��
//	 * @param FilePath �ǂݍ��ރt�@�C���̃p�X
//	 */
//	public void kekkaImport(String FilePath)
//	{
//
//		String logfile = PropertyUtil.getProperty("log.filename");
//		PropertyConfigurator.configure(logfile);
//
//		ArrayList<Hashtable<String, String>> kojinData=null;
//		ArrayList<Hashtable<String, String>> selectKojinData=null;
//
//		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();
//
//		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();
//
//		// �o�^�����ϐ�
//		int intregistCnt = 0;
//		boolean retblnALL = false;
//		ResultMessage="";
//
//		// CSV�Ǎ�����
//		Reader = new JCSVReaderStream();
//
//		try {
//
//			Reader.openCSV(FilePath,JApplication.CSV_CHARSET);
//
//			CSVItems = Reader.readAllTable();
//
//			// add s.inoue 20081001
//			if (inputCsvFileCheck(FilePath,true)){
//				JErrorMessage.show("M3327",this);
//				return;
//			}
//
//		} catch (Exception e) {
//			logger.error("[�������ʎ捞����]CSV�Ǎ������Ɏ��s���܂����B");
//			JErrorMessage.show("M3326",this);
//			return;
//		}
//
//		// �@��(����,���̑�)�ڑ�����
//		try {
//			Connection connection = JApplication.kikanDatabase.getMConnection();
//
//			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
//					connection,
//					kensakekaTokutei);
//
//			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
//					connection,
//					kensakekaSonota);
//
//			JApplication.kikanDatabase.Transaction();
//
//		} catch (Exception e) {
//			logger.error("[�������ʎ捞����]�@��FDB�ւ̐ڑ������Ɏ��s���܂����B");
//			JErrorMessage.show("M3326",this);
//			return;
//		}
//
//		// �������ʃf�[�^�捞����
//		for (int i = 0; i < CSVItems.size(); i++) {
//
//			Vector<String> insertRow = new Vector<String>();
//
//			insertRow =CSVItems.get(i);
//
//			// add s.inoue 20081001
//			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "��");
//
//			// �������擾 CSV�f�[�^�����[�J���ϐ��ɃZ�b�g(�u"�v��������������)
//			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JUSIN_SEIRI_NO));
//			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISI_DATE));
//			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHIMEI));
//			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEINENGAPI));
//			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEIBETU));
//			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISIKIKAN_NO));
//			Data.nyuBi =Reader.rmQuart((insertRow.get(JApplication.CSV_ZOKUSEI_NYUBI_YOUKETU)));
//			Data.shokuZenGo =Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHOKUZEN_SHOKUGO));
//
//			// ����������
//			Data.uketukeNo =null;
//			kojinData = null;
//			preuketukeId = 0L;
//			prekensaDate =0;
//
//			// ���ʎ捞�f�[�^(�L�[���)�`�F�b�N����
//			if (checkKensaKekkaKeyData(Data,ErrorResultData,i,false)){
//				i++;continue;
//			}
//
//			// �l���ʃf�[�^:(�����ԍ�,���f���P�ʂŎ擾)
//			try {
//				if (!Data.jusinSeiriNo.toString().equals(""))
//					kojinData = getKojinData(Data);
//
//				} catch (Exception err) {
//					ResultMessage += GetErrorMessage("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
//					logger.error("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
//					break;
//				}
//
//			// �Y������f�[�^���Ȃ��ꍇ�A�l(����,���N����,����)�Ō���
//			if (kojinData == null ||
//					kojinData.size() == 0){
//				try{
//
//					selectKojinData = getSimeiData(Data);
//
//					if (selectKojinData == null ||
//							selectKojinData.size() == 0){
//						break;
//					}
//
//					// �Y������l�����������ꍇ�A���X�g��\������
//					// edit s.inoue 2010/02/03
//					Hashtable<String, String>selectedKojinData
//						= searchKojinData(selectKojinData,Data,true);
//
//					// del s.inoue 20081003
//					//if (selectedKojinData == null) {
//					//	Data.errCase = JApplication.ERR_KIND_NOT_EXIST;
//					//	setImportErrDigit(ErrorResultData,Data,i);
//					//	ResultMessage += GetErrorMessage("[�������ʎ捞����]�Y���҂����܂���ł����B�����𒆒f���܂��B","�������ʃf�[�^:" + Data.kanaShimei);
//					//	break;
//					//}
//					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);
//
//					kojinData = getSelectKojinData(Data);
//					if (kojinData == null ||
//							kojinData.size() == 0){
//						break;
//					}
//
//				} catch (Exception err) {
//					ResultMessage += GetErrorMessage("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
//					logger.error("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
//					break;
//				}
//			}
//
//			// ���ʎ捞CSV�t�@�C����DB�̒l�̔�r���s���B
//			// ���̑��e�[�u���̍��ڃR�[�h�P�ʂŏ������s���B
//			// add s.inoue 2010/04/21
//			String height = "";
//			String weight = "";
//			for (int j = 0; j < kojinData.size(); j++) {
//
//					Hashtable<String,String> DatabaseItem = kojinData.get(j);
//
//					if (Data.uketukeNo == null){
//						Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
//					}
//					// ��tID���r����i��v�F�o�^�ΏہA�s��v�F�G���[�Ώہj
//					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
//					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
//					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);
//
//					// �f�[�^�x�[�X�ɓo�^(T_KENSAKEKKA_TOKUTEI)
//					// �L�[���(:��tID,�������{��)���ς������o�^�������s��
//					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
//							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
//						try{
//
//							// ���ʎ捞�f�[�^(�������)�`�F�b�N����
//							if (checkKensaKekkaData(Data,ErrorResultData,i)){
//								j++;continue;
//							}
//
//							// ���Ɍ��f���ʃf�[�^�����݂����ꍇ�A�x�����b�Z�[�W��\������B
//							// ���f���{��,�����J�i�� ���ʓo�^����ƃf�[�^�����݂���ׁA�X�V�����̂�
//							Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
//							// ���ڃR�[�h�`�F�b�N
//							if (checkKoumokuCD(Data,ErrorResultData,i)){
//								j++;continue;
//							}
//
//							boolean existsKensaKekkaData = existsKensaKekkaData(Data);
//
//							if (existsKensaKekkaData) {
//
//								if (!retblnALL){
//									// edit ver2 s.inoue 2009/05/22
//									// M3328=�m�F,[%s]�ɂ́A���łɌ��ʃf�[�^�����݂��܂��B�o�^�����ꍇ�A
//									// �ȑO�ɓ��͂������ʃf�[�^�͏����Ă��܂��܂��B�o�^���Ă�낵���ł����H
//									String[] messageParams = {"��t�ԍ�:"+ Data.uketukeNo + "/" + "���f���{��:" + Data.jishiDate + "/" + "����:"+ Data.kanaShimei};
//									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
//									// cancel��
//									if (retValue == RETURN_VALUE.CANCEL) {
//										try {
//										JApplication.kikanDatabase.rollback();
//										}catch (SQLException err) {}
//										return;
//									}else if (retValue == RETURN_VALUE.NO){
//										preuketukeId=Long.valueOf(uketukeIdDB);
//										prekensaDate=Integer.valueOf(kensaDateDB);
//										j++;continue;
//									}else if (retValue == RETURN_VALUE.ALL){
//										retblnALL = true;
//									}
//								}
//							}
//							intregistCnt++;
//
//							kensakekaTokuteiregister(Data);
//							preuketukeId=Long.valueOf(uketukeIdDB);
//							prekensaDate=Integer.valueOf(kensaDateDB);
//
//						}catch(Exception ex){
//							try {
//								JApplication.kikanDatabase.rollback();
//							} catch (SQLException err) {}
//							ResultMessage += GetErrorMessage("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B",Data.kanaShimei);
//
//							logger.error("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B");
//							break;
//						}
//					}
//
//					// �L�[��񂪈�v�����猟�����ʓo�^���Ă��萳��
//					// �u���̑�:1�`22�̍���(���I�ɕω�����)�v�̓o�^
//					Integer kensaKoumokuLoop = JApplication.CSV_KENSA_KOUMOKU_LOOP;
//					int cntKoumoku =0;
//
//					while (cntKoumoku < JApplication.CSV_KENSA_LOOP * JApplication.CSV_KENSA_KOUMOKU_LOOP) {
//
//						if (insertRow.size() <= JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku )
//						{
//							cntKoumoku += kensaKoumokuLoop;break;
//						}
//						Data.koumokuCd = Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku));
//
//						if (Data.koumokuCd.equals("")){
//							checkKoumokuCD(Data,ErrorResultData,i);
//							cntKoumoku += kensaKoumokuLoop;continue;
//						}
//
//						try {
//							Long resultCnt = kensakekaSonotaDao.selectByCountPrimaryKey(
//									Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
//							if (resultCnt < 0) {
//								checkKoumokuCD(Data,ErrorResultData,i);
//								cntKoumoku += kensaKoumokuLoop;
//								continue;
//							}
//
//						} catch (Exception e) {
//							logger.error("[�������ʎ捞����]�������ʃf�[�^���̑��f�[�^�擾���A�G���[�ƂȂ�܂����B");
//							break;
//						}
//
//						if(JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(JEraseSpace.EraceSpace(uketukeIdDB)) &&
//							JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(JEraseSpace.EraceSpace(kensaDateDB)) &&
//							 JEraseSpace.EraceSpace(Data.koumokuCd.toString()).equals(JEraseSpace.EraceSpace(koumokuCdDB)))
//						{
//							Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN + cntKoumoku));
//							Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI + cntKoumoku));
//							// del 20080918 s.inoue
//							//Data.ijyoKbn=Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_IJYO_KBN + cntKoumoku));
//							//Data.kekkaTiKeitai =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI_KEITAI + cntKoumoku));
//
//							// ���ʎ捞�f�[�^(�������ڏ��)
//							if (checkKensaKekkaDataDetail(Data,ErrorResultData,i)){
//								cntKoumoku += kensaKoumokuLoop;break;
//							}
//
//							// ���f���ʂ��̑��o�^����
//							try{
//								kensakekaSonotaregister(Data);
//
//								// add s.inoue 2010/04/21
//								// BMI�����v�Z roop�Ō�ɏ���
//								if(Data.koumokuCd.equals("9N001000000000001")){
//									height = Data.kekkaTi;
//								}else if (Data.koumokuCd.equals("9N006000000000001")){
//									weight = Data.kekkaTi;
//								}
//							}catch(Exception ex){
//								try {
//									JApplication.kikanDatabase.rollback();
//
//								} catch (SQLException err) {
//								}
//								logger.error("[�������ʎ捞����]�������ڏ��o�^���A�G���[�ƂȂ�܂����B");
//								ResultMessage += GetErrorMessage("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B",Data.kanaShimei);
//								break;
//							}
//						}
//
//						cntKoumoku += kensaKoumokuLoop;
//					}
//			}
//
//			// add s.inoue 2010/04/21
//			String bmi = calcBMI(height, weight);
//
//			// edit s.inoue 2010/07/12
//			if (!bmi.equals("")){
//				String kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
//				Data.koumokuCd = "9N011000000000001"; //BMI
//				Data.kekkaTi = kensaKekka;
//				updatekensakekaSonota(Data,insertRow);
//			}
//		}
//
//		try {
//			// commit
//			JApplication.kikanDatabase.Commit();
//
//		} catch (SQLException err) {
//			logger.error("[�������ʎ捞����]�������ڏ��o�^���A�G���[�ƂȂ�܂����B" + JApplication.LINE_SEPARATOR + err.getMessage());
//		}
//
//		// �A���}�b�`�G���[�ꗗ�\��
//		if(ErrorResultData.size() >= 1){
//			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
//					this,new JImportErrorResultFrameCtrl(ErrorResultData,this));
//
//		}else if(intregistCnt == 0){
//			JErrorMessage.show("M3325",this);
//
//		// �������f�̃G���[���b�Z�[�W��\��
//		}else if(ResultMessage.equals("")){
//			JErrorMessage.show("M3324",this);
//
//		// �������f�̃G���[���b�Z�[�W��\��
//		}else{
//			try
//			{
//				BufferedWriter file = new BufferedWriter(new FileWriter("Error.txt"));
//				file.write(ResultMessage);
//				file.close();
//
//			}catch(Exception err){
//				logger.error("[�������ʎ捞����]�������ڏ��o�^���A�G���[�ƂȂ�܂����B" + JApplication.LINE_SEPARATOR + err.getMessage());
//				JErrorMessage.show("M3323",this);
//			}
//			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
//					this,new JImportResultFrameCtrl(ResultMessage,this));
//		}
//
//		// add s.inoue 20081001
//		this.m_guiStatus.setText("");
//
//	}
//	/*-------------------------------------orgine-----------------------------------------------*/
//
//	/*-------------------------------------hl7�捞-----------------------------------------------*/
//	/**
//	 * xml(HL7)�̎�荞��
//	 * ����
//	 * �v���O������zip�𓀂��Ă���ׁA�z��O�̃G���[�Ɋւ��Ă͑S��
//	 * �G���[���:error����
//	 * @param FilePath �ǂݍ��ރt�@�C���̃p�X
//	 */
//	public void hl7Import(String filePath)
//	{
//		String zipAbsolutePath = "";
//		String zipCommonSubPath = "";
//		String zipSubPath = "";
//		String dataSubPath = "";
//		String dataDirPath = "";
//		String zipPath ="";
//		String[] zipfileArray =null;
//
//		try {
//
//			File zipFile = null;
//			boolean zipProcess = false;
//			Connection connection = JApplication.kikanDatabase.getMConnection();
//
//			kojinDao = (TKojinDao) DaoFactory.createDao(connection,kojin);
//			hokenjyaDao = (THokenjyaDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new THokenjya());
//			// add s.inoue 2010/11/30
//			shiharaiDao = (TShiharaiDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new TShiharai());
//			tNayoseDao = (TNayoseDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new TNayose());
//			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(connection,kensakekaTokutei);
//			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(connection,kensakekaSonota);
//
//			// edit s.inoue 2010/11/01
//			validatedKojinData = new JKojinRegisterFrameData();
//
//			// zip�t�H���_�̏ꍇ�������t�@�C������
//			// ������Aunzip�����t�H���_�͍폜����
//			if (filePath.endsWith(".zip") ||
//					filePath.endsWith(".ZIP")){
//				zipProcess = true;
//			}
//
//			// �����͂���
//			JApplication.kikanDatabase.Transaction();
//
//			if (zipProcess){
//				JZipDecompresser za = new JZipDecompresser();
//				zipFile = new File(filePath);
//
//				zipPath = za.unzip(zipFile);
//				System.out.println(zipPath);
//				int fistStr = filePath.lastIndexOf(File.separator)+1;
//				// zip��
//				zipAbsolutePath = filePath.substring(0,fistStr-1);
//
//				// data�t�H���_
//				zipCommonSubPath = filePath.substring(fistStr,filePath.length() -4);
//				zipSubPath = zipPath +File.separator+zipCommonSubPath +File.separator+ "DATA";
//
//				// claims�t�H���_
//				dataSubPath =zipPath +File.separator+zipCommonSubPath +File.separator+ "CLAIMS";
//
//				// edit s.inoue 2010/11/30 ix�t�@�C��
//				dataDirPath = zipPath + File.separator+zipCommonSubPath;
//
//				// data�t�H���_������
//				String fpath = zipAbsolutePath + File.separator + zipSubPath;
//				File zipFiles = new File(fpath);
//				zipfileArray = zipFiles.list();
//
//				// claims�t�H���_������
//				String cpath = zipAbsolutePath + File.separator + dataSubPath;
//				File cFiles = new File(cpath);
//				String[] cfileArray = cFiles.list();
//				String claimsfileName = "";
//
//				// �t�@�C�������K��
//				// RootDirectory+ "DATA"+ SEPARATOR
//				// ["h" :h or c][Souhumoto :���t���@�֔ԍ�][CreateDate :�쐬��][AllocDirectoryNumber :�ϊ��������̕ϊ���]
//				// ["1" :�Œ�][Utility.FillZero(ClinicalDocumentCount, 6)][".xml"]
//				// Data�t�H���_�Ώ�
//	            for(int i = 0; i < zipfileArray.length; i++) {
//
//	                // �������ɂ�蓯���̏ꍇ���������{
//		            for (int j = 0; j < cfileArray.length; j++) {
//						String cfile = cfileArray[j].substring(1);
//						if (cfile.equals(zipfileArray[i].substring(1))){
//							System.out.println("�����敪�擾�p" + cfileArray[i]);
//							claimsfileName = cfileArray[i];break;
//						}
//					}
//
//					// ������������Data���쐬
//					Data = new JImportErrorResultFrameData();
//
//		            // data,claims
//		            String datafileName = fpath + File.separator + zipfileArray[i];
//		            claimsfileName = cpath + File.separator + claimsfileName;
//
//		            // add s.inoue 2010/11/30
//					// ix�t�@�C���ǂݍ���(�̂�)
//					String ixPath = zipAbsolutePath + File.separator +dataDirPath + File.separator + "ix08_V08.xml";
//
//	            	hl7filesRead(datafileName,claimsfileName,ixPath);
//	                System.out.println(i+1 + "���� " + zipfileArray[i] + "�t�@�C������");
//	            }
//
//// move s.inoue 2010/12/01
////				// �𓀒����t�H���_�폜
////				File zipFolder = new File(zipAbsolutePath +File.separator + zipPath);
////
////				// deletefolder
////				JFile.deleteDirectory(zipFolder,true);
////				System.out.println("�ꎞzip�t�H���_:" + zipFolder);
//			}else{
//				// �ʏ�v���Z�X
//				// ������������Data���쐬
//				Data = new JImportErrorResultFrameData();
//
//				hl7filesRead(filePath,null,null);
//			}
//
//			// add s.inoue 2010/10/27
//			// �A���}�b�`�G���[�ꗗ�\��
//			if(validatedKojinData.getErrorMessageCount() == 0){
//				JApplication.kikanDatabase.Commit();
//
//				// add s.inoue 2010/12/02
//				DBYearAdjuster yAjuster = new DBYearAdjuster();
//				// �f�[�^�x�[�X�ؒf
//				try {
//					JApplication.kikanDatabase.Shutdown();
//				} catch (SQLException ex) {
//					JErrorMessage.show("M3340",this);
//					logger.error(ex.getMessage());
//				}
//
//				yAjuster.callfixedNayose(JApplication.kikanNumber);
//
//				try {
//					JApplication.kikanDatabase = JConnection
//						.ConnectKikanDatabase(JApplication.kikanNumber);
//				} catch (SQLException ex) {
//					JErrorMessage.show("M3340",this);
//					logger.error(ex.getMessage());
//				}
//
//			}else if(validatedKojinData.getErrorMessageCount() >= 1){
//                // �G���[����
//            	String errMessage ="";
//            	String zipfileName ="";
//
//            	HashMap<String, String> errorMessage = validatedKojinData.getErrorMessage();
//            	java.util.Set stKey = errorMessage.entrySet();
//            	java.util.Iterator ite = stKey.iterator();
//            	int iCount = 0;
//            	while (ite.hasNext()) {
//            		Object o = ite.next();
//
//            	    java.util.Map.Entry ent = (java.util.Map.Entry)o;
//            	    String key = (String)ent.getKey();
//            	    String val = (String)ent.getValue();
//
//            	    if (zipProcess){
//            	    	if (!zipfileName.equals(zipfileArray[iCount])
//            	    			&& (!key.equals("")))
//            	    		errMessage = "�t�@�C����:" + zipfileArray[iCount] + "[���s]";
//            	    	zipfileName = zipfileArray[iCount];
//            	    }else if (iCount == 0){
//            	    	errMessage = "���t�@�C����:" + filePath.substring(filePath.lastIndexOf("\\")+2) + "[���s]";
//            	    }
//
//            	    errMessage+="�E" + val + "[���s]";
//            	    iCount++;
//				}
//
//            	String[] params = { errMessage };
//				JErrorMessage.show("M3332", this, params);
//
//		       	try{
//	        		JApplication.kikanDatabase.rollback();
//	        	}catch(Exception ex){}
//			}
//		} catch (Exception e) {
//        	// ���s��rollback
//        	try{
//        		JApplication.kikanDatabase.rollback();
//        	}catch(Exception ex){}
//
//			logger.error("[HL7�捞����]�@��FDB�ւ̐ڑ������Ɏ��s���܂����B");
//			JErrorMessage.show("M3331",this);
//		} finally {
//			// move s.inoue 2010/12/01 �G���[�������l��
//			// �𓀒����t�H���_�폜
//			File zipFolder = new File(zipAbsolutePath +File.separator + zipPath);
//			// deletefolder
//			JFile.deleteDirectory(zipFolder,true);
//			 validatedKojinData.clearColumnData();
//		}
//	}
//
//	/*
//	 * hl7(data)�t�@�C���Q�Ǎ�
//	 */
//	private void hl7filesRead(String datafilePath,String claimsfilePath,String ixPath){
//
//		try{
//
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			// edit s.inoue 2010/11/30
//			// Document doc = builder.parse(datafilePath);			// XML�������p�[�X
//			Document doc = builder.parse(new File(datafilePath));			// XML�������p�[�X
//
//			Document claimsdoc = null;
//			if (claimsfilePath != null)
//				claimsdoc = builder.parse(new File(claimsfilePath)); // claims�p�����ǂݍ���
//
//			// ix�t�@�C���p����
//			Document ixdoc = null;
//			if (ixPath != null)
//				ixdoc = builder.parse(new File(ixPath)); 			// claims�p�����ǂݍ���
//
//			// �O����
//			koumokuCodeMap = new HashMap<String, String>();
//			koumokuCodeElseMap = new HashMap<String, String>();
//
//			setImportKenshinItemList();						// koumokuCD���Z�b�g
//
//	        // �q�v�f���擾
//	        readXMLsearchNode(doc,claimsdoc,ixdoc);
//
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//			System.out.println("error:" + ex.getMessage());
//		}
//	}
//
//	/*
//	 * ix�t�@�C�����f�[�^(��s�@�֔ԍ�)�擾
//	 */
//	private boolean getixXMLsearch(Document ixDoc) {
//
//		boolean retflg = true;
//		Element root = ixDoc.getDocumentElement();
//		System.out.println("���[�g�F" + root.getTagName());
//		Node child=root.getFirstChild();
//
//		while (child!=null) {
//			String nodeNm = child.getNodeName();
//
//			// ���{�@�֔ԍ�
//			if (nodeNm.equals("sender")){
//				Node claimeChild=child.getFirstChild().getNextSibling();
//				String claimeChildnodeNm = claimeChild.getNodeName();
//				if (claimeChildnodeNm.equals("id")){
//					String extensionVal = getItemAttribute(claimeChild,"extension");
//					// �@�֔ԍ���r
//					if (!JApplication.kikanNumber.equals(extensionVal)){
//						retflg =false;break;
//					}
//				}
//			// ��s�@�֔ԍ�
//			}else if (nodeNm.equals("receiver")){
//				Node claimeChild=child.getFirstChild().getNextSibling();
//				String claimeChildnodeNm = claimeChild.getNodeName();
//				if (claimeChildnodeNm.equals("id")){
//					String extensionVal = getItemAttribute(claimeChild,"extension");
//					validatedKojinData.setDaikouNumber(extensionVal);
//					break;
//				}
//			}
//			child = child.getNextSibling();
//		}
//		return retflg;
//	}
//
//	/*
//	 * claims�t�@�C�����f�[�^�擾
//	 */
//	private void getClaimsXMLsearch(Document claimsDoc) {
//		Element root = claimsDoc.getDocumentElement();
//		System.out.println("���[�g�F" + root.getTagName());
//		Node child=root.getFirstChild();
//
//// del s.inoue 2010/11/17
//		/* �������S�i��{�I�Ȍ��f�j */
//		validatedKojinData.setMadoguchiKihonSyubetu(0);
//		validatedKojinData.setMadoguchiKihon("000000",true,true);
//		/* �������S�i�ڍׂȌ��f�j */
//		validatedKojinData.setMadoguchiSyousaiSyubetu(0);
//		validatedKojinData.setMadoguchiSyousai("000000",true,true);
//		/* �������S�i�ǉ��̌��f�j */
//		validatedKojinData.setMadoguchiTsuikaSyubetu(0);
//		validatedKojinData.setMadoguchiTsuika("000000",true,true);
//		/* �������S�i�l�ԃh�b�N�j */
//		validatedKojinData.setMadoguchiDockSyubetu(0);
//		validatedKojinData.setMadoguchiDock("000000",true,true);
//		// �ی��ҏ���͐ߐ����Ȃ��ėǂ�
//
//		while (child!=null) {
//
//			String nodeNm = child.getNodeName();
//
//			if (nodeNm.equals("checkupCard")){
//				// ���茒�f��f�����
//				Node claimeChild=child.getFirstChild();
//				while (claimeChild!=null) {
//					// �����敪�擾
//					String claimeNodeNm = claimeChild.getNodeName();
//
//					// node�łȂ���Ύ��̗v�f�ֈړ�
//					if (claimeChild.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE){
//						claimeChild = claimeChild.getNextSibling();
//						continue;
//					}
//					// �����������������S�ʏ�������������
//					// �m�[�h�擾����
//					Node attrCode = null;
//					NamedNodeMap attrsCode = null;
//					if (claimeNodeNm.equals("chargeTypeBasic") ||
//							claimeNodeNm.equals("chargeTypeDetail") ||
//								claimeNodeNm.equals("chargeTypeOther")){
//						attrsCode = claimeChild.getAttributes();
//			        	attrCode = attrsCode.getNamedItem("code");
//			        	if (attrCode == null) return;
//					}else if (claimeNodeNm.equals("chargeTypeHumanDryDock")){
//						// add s.inoue 2010/11/25
//						claimeChild = claimeChild.getFirstChild().getNextSibling();
//						attrsCode = claimeChild.getAttributes();
//			        	attrCode = attrsCode.getNamedItem("code");
//					}
//
//					// �������S���(��{�I�Ȍ��f)
//					if (claimeNodeNm.equals("chargeTypeBasic")){
//						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());
//
//						// edit s.inoue 2010/11/24
//						String syubetuCode = attrCode.getNodeValue();
//						if (syubetuCode.equals("1")){
//							// edit s.inoue 2010/12/08
//							validatedKojinData.setMadoguchiKihonSyubetu(1);
//							claimeChild = claimeChild.getNextSibling();
//							continue;
//						}
//						validatedKojinData.setMadoguchiKihonSyubetu(madogutiSyubetu);
//
//						// add s.inoue 2010/11/17
//						claimeChild = claimeChild.getFirstChild().getNextSibling();
//						if (claimeChild.getNodeName().equals("amount") ||
//								claimeChild.getNodeName().equals("rate")){
//							attrsCode = claimeChild.getAttributes();
//				        	attrCode = attrsCode.getNamedItem("value");
//						}
//				        	if (attrCode == null) return;
//
//				        	if (syubetuCode.equals("3")){
//				        		validatedKojinData.setMadoguchiKihon(attrCode.getNodeValue(),false,true);
//				        	}else if (syubetuCode.equals("4")){
//				        		validatedKojinData.setHokenjyaJougenKihon(attrCode.getNodeValue(),false);
//				        		validatedKojinData.setMadoguchiKihonSyubetu(1);
//				        	}else{
//				        		validatedKojinData.setMadoguchiKihon(attrCode.getNodeValue(), false,false);
//				        	}
//
//							// edit s.inoue 2010/11/24
//							claimeChild = claimeChild.getParentNode();
//					// �������S���(�ڍׂȌ��f)
//					}else if (claimeNodeNm.equals("chargeTypeDetail")){
//						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());
//
//						// edit s.inoue 2010/11/24
//						String syubetuCode = attrCode.getNodeValue();
//						if (syubetuCode.equals("1")){
//							// edit s.inoue 2010/12/08
//							validatedKojinData.setMadoguchiSyousaiSyubetu(1);
//							claimeChild = claimeChild.getNextSibling();
//							continue;
//						}
//						validatedKojinData.setMadoguchiSyousaiSyubetu(madogutiSyubetu);
//
//						// add s.inoue 2010/11/17
//						claimeChild = claimeChild.getFirstChild().getNextSibling();
//						if (claimeChild.getNodeName().equals("amount") ||
//								claimeChild.getNodeName().equals("rate")){
//							attrsCode = claimeChild.getAttributes();
//				        	attrCode = attrsCode.getNamedItem("value");
//						}
//				        	if (attrCode == null) return;
//				        	if (syubetuCode.equals("3")){
//				        		validatedKojinData.setMadoguchiSyousai(attrCode.getNodeValue(),false,true);
//				        	}else if (syubetuCode.equals("4")){
//				        		validatedKojinData.setHokenjyaJougenSyousai(attrCode.getNodeValue(),false);
//				        		validatedKojinData.setMadoguchiSyousaiSyubetu(1);
//				        	}else{
//				        		validatedKojinData.setMadoguchiSyousai(attrCode.getNodeValue(), false,false);
//				        	}
//
//				        	// edit s.inoue 2010/11/24
//							claimeChild = claimeChild.getParentNode();
//
//					// �������S���(�ǉ����f)
//					}else if (claimeNodeNm.equals("chargeTypeOther")){
//						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());
//						// edit s.inoue 2010/11/24
//						String syubetuCode = attrCode.getNodeValue();
//						if (syubetuCode.equals("1")){
//							// edit s.inoue 2010/12/08
//							validatedKojinData.setMadoguchiTsuikaSyubetu(1);
//							claimeChild = claimeChild.getNextSibling();
//							continue;
//						}
//						validatedKojinData.setMadoguchiTsuikaSyubetu(madogutiSyubetu);
//						// add s.inoue 2010/11/17
//						claimeChild = claimeChild.getFirstChild().getNextSibling();
//						if (claimeChild.getNodeName().equals("amount") ||
//								claimeChild.getNodeName().equals("rate")){
//							attrsCode = claimeChild.getAttributes();
//				        	attrCode = attrsCode.getNamedItem("value");
//						}else if (claimeChild.getNodeName().equals("copayment")){
//							// add s.inoue 2010/11/25
//							attrsCode = claimeChild.getAttributes();
//				        	attrCode = attrsCode.getNamedItem("code");
//						}
//				        	if (attrCode == null) return;
//				        	if (syubetuCode.equals("3")){
//				        		validatedKojinData.setMadoguchiTsuika(attrCode.getNodeValue(),false,true);
//				        	}else if (syubetuCode.equals("4")){
//				        		validatedKojinData.setHokenjyaJougenTsuika(attrCode.getNodeValue(),false);
//				        		validatedKojinData.setMadoguchiTsuikaSyubetu(1);
//				        	}else{
//				        		validatedKojinData.setMadoguchiTsuika(attrCode.getNodeValue(), false,false);
//				        	}
//
//							// edit s.inoue 2010/11/24
//							claimeChild = claimeChild.getParentNode();
//
//					// �������S���(�l�ԃh�b�N)
//					}else if (claimeNodeNm.equals("chargeTypeHumanDryDock")){
//						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());
//						// edit s.inoue 2010/11/24
//						String syubetuCode = attrCode.getNodeValue();
//						if (syubetuCode.equals("1")){
//							// edit s.inoue 2010/12/08
//							validatedKojinData.setMadoguchiDockSyubetu(1);
//							claimeChild = claimeChild.getNextSibling();
//							continue;
//						}
//						validatedKojinData.setMadoguchiDockSyubetu(madogutiSyubetu);
//						// add s.inoue 2010/11/17
//						claimeChild = claimeChild.getFirstChild().getNextSibling();
//						if (claimeChild.getNodeName().equals("amount") ||
//								claimeChild.getNodeName().equals("rate")){
//							attrsCode = claimeChild.getAttributes();
//				        	attrCode = attrsCode.getNamedItem("value");
//						}
//				        	if (attrCode == null) return;
//
//				        	if (syubetuCode.equals("3")){
//				        		validatedKojinData.setMadoguchiDock(attrCode.getNodeValue(),false,true);
//				        	}else if (syubetuCode.equals("4")){
//				        		validatedKojinData.setHokenjyaJougenDoc(attrCode.getNodeValue(),false);
//				        		validatedKojinData.setMadoguchiDockSyubetu(1);
//				        	}else{
//				        		validatedKojinData.setMadoguchiDock(attrCode.getNodeValue(), false,false);
//				        	}
//
//							// edit s.inoue 2010/11/24
//							claimeChild = claimeChild.getParentNode();
//					}
//					claimeChild = claimeChild.getNextSibling();
//				}
//
//			}else if (nodeNm.equals("settlement")){
//				Node claimeChild=child.getFirstChild();
//
//				// settlement�z���T��
//				while (claimeChild!=null) {
//					// �����敪�擾
//					String claimeNodeNm = claimeChild.getNodeName();
//					Node attrCode = null;
//
//					// node�łȂ���Ύ��̗v�f�ֈړ�
//					if (claimeChild.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE){
//						claimeChild = claimeChild.getNextSibling();
//						continue;
//					}
//
//		        	// ���Ϗ��
//					NamedNodeMap attrsCode = claimeChild.getAttributes();
//
//					if (claimeNodeNm.equals("unitPriceBasic") ||
//							claimeNodeNm.equals("unitPriceDetail") ||
//								claimeNodeNm.equals("unitPriceOther") ||
//						claimeNodeNm.equals("paymentForBasic") ||
//							claimeNodeNm.equals("paymentForDetail") ||
//								claimeNodeNm.equals("paymentForOther")){
//
//						Node claimePriceChild = claimeChild.getFirstChild();
//						while(claimePriceChild != null){
//							if (claimePriceChild.getNodeName().equals("amount"))
//								break;
//							claimePriceChild = claimePriceChild.getNextSibling();
//						}
//						NamedNodeMap attrsClaimePriceCode = claimePriceChild.getAttributes();
//			        	attrCode = attrsClaimePriceCode.getNamedItem("value");
//			        	if (attrCode == null) return;
//					}
//
//					if (claimeNodeNm.equals("claimType")){
//			        	// ���Ϗ��
//						// NamedNodeMap attrsCode = claimeChild.getAttributes();
//						attrCode = attrsCode.getNamedItem("code");
//			        	if (attrCode == null) return;
//			        	validatedKenshinData.setSeikyuKubunCode(attrCode.getNodeValue(),true);
//			        	System.out.println("attrCodeNode:" + attrCode.getNodeValue());
//					}
//
//					// add s.inoue 2010/11/25
//					if (claimeNodeNm.equals("paymentByOtherProgram")){
//						attrCode = attrsCode.getNamedItem("value");
//						validatedKojinData.setMadoguchiSonota(attrCode.getNodeValue(),true);
//					}
//// del s.inoue 2010/11/24
////				    // �P���i��{�I�Ȍ��f�j
////					}else if (claimeNodeNm.equals("unitPriceBasic")){
////						validatedKojinData.setHokenjyaJougenKihon(attrCode.getNodeValue(),true);
////			    	// �P���i�ڍׂȌ��f�j
////			    	}else if (claimeNodeNm.equals("unitPriceDetail")){
////			    		validatedKojinData.setHokenjyaJougenSyousai(attrCode.getNodeValue(),true);
////			    	// �P���i�ǉ����f�j
////			    	}else if (claimeNodeNm.equals("unitPriceOther")){
////			    		validatedKojinData.setHokenjyaJougenTsuika(attrCode.getNodeValue(),true);
////				    // �P���i�l�ԃh�b�N�j
////			    	}else if (claimeNodeNm.equals("unitPriceOther")){
////			    		validatedKojinData.setHokenjyaJougenDoc(attrCode.getNodeValue(),true);
////			    	// �������S���z(��{�I�Ȍ��f)
////			    	}else if (claimeNodeNm.equals("paymentForBasic")){
////			    		validatedKojinData.setMadoguchiKihon(attrCode.getNodeValue(),true);
////			    	// �������S���z�i�ڍׂȌ��f�j
////			    	}else if (claimeNodeNm.equals("paymentForDetail")){
////			    		validatedKojinData.setMadoguchiSyousai(attrCode.getNodeValue(),true);
////			    	// �������S���z�i�ǉ����f���͐l�ԃh�b�N�j
////			    	}else if (claimeNodeNm.equals("paymentForOther")){
////			    		validatedKojinData.setMadoguchiTsuika(attrCode.getNodeValue(),true);
////			    	}
//
//					claimeChild = claimeChild.getNextSibling();
//				}
//			}
//			child = child.getNextSibling();
//		}
//	}
//
//	/*
//	 *  XMLReader(jaxParser)���C���ǂݍ��ݏ���
//	 */
//    private void readXMLsearchNode(Document doc,Document claimsDoc,Document ixDoc){
//		try{
//
//			// add s.inoue 2010/11/30
//			if (ixDoc != null){
//				if (!getixXMLsearch(ixDoc)){
//					JErrorMessage.show("M3339", null);
//					return;
//				}
//			}
//
//			// claimsDoc���ɓǍ�
//			if (claimsDoc != null)
//				getClaimsXMLsearch(claimsDoc);
//
//			// ���[�g���̃��C���m�[�h�Ń��[�v
//			Element root = doc.getDocumentElement();
//			System.out.println("���[�g�F" + root.getTagName());
//			// �q�m�[�h���擾
//			Node child=root.getFirstChild();
//
//			// ���ڃm�[�h�Ń��[�v
//			while (child!=null) {
//				System.out.println("���ځF" + child.getNodeName());
//				// ���ڃm�[�h���ŏ����i���s�͎��j
//				if (child.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
//					traceAllChildNodes(child);
//				// Validation
//				if (!getElementValueByTags(child)){
//					JErrorMessage.show("M3337", null);
//					return;
//				}
//				// nextChild
//				child = child.getNextSibling();
//			}
//
//			// DB�֓o�^
//			registerXmlData();
//
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//			System.out.println(ex.getMessage());
//		}
//    }
//
//////�m�[�h�ŏ��P�ʒT����/////////////////////////////////////////////
//    /* child�m�[�h�ǐ� */
//    private static void traceAllChildNodes(Node node) {
//
//        Node child=node.getFirstChild();	 // �q�m�[�h���擾
//
//        while (child!=null) {
//
//        	// edit s.inoue 2010/11/01
////        	if (child.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE){
////        		child = child.getNextSibling();
////        		continue;
////        	}
//
//            traceAllChildNodes(child);       // ����Ɏq�m�[�h�����ǂ�
//            child = child.getNextSibling();  // �Z��m�[�h���擾
//
//            /* �e���ږ��Ƀ��[�v���s��				 */
//            /* ���ډ��ɂ͏d������^�O������ׁA���ӂ��� */
//            if (child != null){
//        		String nodeval ="";
//
//        		// ���ʍ��ڏ����Ή�
//        		setNodeItemAttribute(child);
//       			nodeval = getAttributeItemValue(child);
//        		System.out.println("���O:" + child.getNodeName() + " �l:" + nodeval);
//            }
//        }
//    }
//
//    /* ���ʒl�擾-�e�^�O�ʂɏ����𕪊򂳂���
//     * code�^�O��17���̏ꍇ�A���f���ڂƌ��Ȃ�
//     */
//    private static void setNodeItemAttribute(Node node){
//
//    	String nodeNm = node.getNodeName();
//
//    	/* ���f���ڃR�[�h�Ó����`�F�b�N */
//    	if (nodeNm.equals("code")){
//    		// ���f���ڃ��X�g�擾
//    		List kenshinItem = new ArrayList<String>();
//        	kenshinItem = Arrays.asList(importKenshinItem);
//
//        	// ���X�g�˂����킹 code�����擾
//        	NamedNodeMap attrsCode = node.getAttributes();
//        	if (attrsCode==null) return;
//        	Node attrCode = attrsCode.getNamedItem("code");
//        	if (attrCode == null) return;
//
//        	// 17�����l�������Ȃ�code�^�O���
//        	if (attrCode.getNodeValue().toString().length() == 17){
//	        	// code���������value�^�O���擾���Anext�ɃZ�b�g����
//	        	String itemValue = "";
//				// pnode
//				Node pnode =node.getParentNode();
//
//	        	// node��null�̏ꍇ�A�����{�̉\��
//				while (!node.getNodeName().equals("value")) {
//					node = node.getNextSibling();
//					if (node == null){
//						node = pnode;
//						break;
//					}
//				}
//
//	        	NamedNodeMap attrs = node.getAttributes();
//
//	        	// �����ʂɌ��ʒl���擾����
//	        	// edit s.inoue 2010/10/22
//	        	// Node attrValue = attrs.getNamedItem("code");
//	        	// if (attrValue != null)
//	        	// 	itemValue = attrValue.getNodeValue();
//
//				//���{�敪�F �u����s�\�v��nullFlavor
//	        	boolean jishiSokuteifunou = false;
//				String nullFlg = getItemAttribute(node, "nullFlavor");
//				if (nullFlg != null){
//					if(nullFlg.equals("NI")){
//						jishiSokuteifunou = true;
//					}
//				}
//
//				// ���{�敪�F�u�����{�v �� value�������o�����Ȃ�
//				else if (pnode.getNodeName().equals("entry") ||
//						pnode.getNodeName().equals("observation")){
//					if (pnode.getNodeName().equals("observation")){
//						String negationIndFlg = getItemAttribute(pnode, "negationInd");
//						if (negationIndFlg != null){
//							if(negationIndFlg.equals("true")){
//								 // node = node.getParentNode().getNextSibling();
//					      		// �q�^�Oid�ֈړ�
//								while (!node.getNodeName().equals("code")) {
//									if (node.getFirstChild()!=null){
//										node = node.getFirstChild().getNextSibling();
//									}else{
//										node = node.getNextSibling();
//									}
//								}
//								String koumokuCode = getItemAttribute(node, "code");
//								koumokuCodeElseMap.put(koumokuCode,"0");
//								System.out.println("�����{code:" + koumokuCode);
//								return;
//							}
//						}
//					}
//				    node = node.getFirstChild();
//				}
//
//	        	// type�ʏ���
//	        	Node attrValue = null;
//	        	Node type = attrs.getNamedItem("xsi:type");
//				if(type.getNodeValue().equals("CO") ||
//						type.getNodeValue().equals("CD")){
//					attrValue = attrs.getNamedItem("code");
//				}else if(type.getNodeValue().equals("PQ")){
//					// type.getNodeValue().equals("ST")
//					attrValue = attrs.getNamedItem("value");
//				// edit s.inoue 2010/10/22
//				}else if(type.getNodeValue().equals("ST")){
//					itemValue = node.getTextContent();
//				}
//
//	        	if (attrValue != null)
//	        	 	itemValue = attrValue.getNodeValue();
//
//	        	// �S���f���ڂƂ̓˂����킹
//	        	if (importKenshinItem.contains(attrCode.getTextContent())){
//	        		if (jishiSokuteifunou){
//	        			koumokuCodeElseMap.put(attrCode.getNodeValue(), "2");
//	        		}else{
//	        			koumokuCodeMap.put(attrCode.getNodeValue(),itemValue);
//	        			System.out.println("code:" + attrCode + "�l:" +itemValue);
//	        		}
//	        		// ��O)��t�̎���
//	        		if (attrCode.getTextContent().equals("9N511000000000049")){
//	        			node = node.getParentNode().getNextSibling();
//	        			while (!node.getNodeName().equals("assignedPerson")) {
//	        				if (node.getFirstChild()!=null){
//	        					node = node.getFirstChild();
//	        				}else{
//	        					node = node.getNextSibling();
//	        				}
//	        				if (node == null)break;
//	        			}
//	        			if (node != null){
//		        			node = node.getFirstChild().getNextSibling();
//		        			if (node.getNodeName().equals("name")){
//		        				koumokuCodeMap.put("9N516000000000049",node.getTextContent());
//		        			}
//	        			}
//	        		}
//	        	}
//        	}
//    	}
//    }
//
//    /* �e�m�[�h���tag��F�������� */
//    private static boolean getElementValueByTags(Node node){
//    	Node parentNode = node.getParentNode();
//
//    	String HL7_TAGS_RECORDTARGET = "recordTarget";
//    	String HL7_TAGS_AUTHOR = "author";
//    	String HL7_TAGS_CUSTODIAN = "custodian";
//    	String HL7_TAGS_PARTICIPANT = "participant";
//    	String HL7_TAGS_DOCUMENTATIONOF = "documentationOf";
//    	String HL7_TAGS_COMPONENT = "component";
//    	String HL7_COLUMN_KENSINDATE ="effectiveTime";
//    	String HL7_COLUMN_KENSINKOUMOKU ="code";
//
//    	// OID�Ή�
//    	String OID_HOKENNUM = "1.2.392.200119.6.101";
//    	String OID_HIHOKENSHA_KIGO = "1.2.392.200119.6.204";
//    	String OID_HIHOKENSHA_NUM = "1.2.392.200119.6.205";
//    	String OID_SEX = "1.2.392.200119.6.1104";
//    	String OID_JYUSINKEN_NUM = "1.2.392.200119.6.209";
//    	String OID_JISIKIKAN_NUM = "1.2.392.200119.6.102";
//
//		String nodeVal = node.getNodeName();
//
//    	/* recordTarget�^�O[�l���] id�^�O��root���� */
//    	if (node.getNodeName().equals(HL7_TAGS_RECORDTARGET)){
//
//    		// �q�^�Oid�ֈړ�
//			while (!node.getNodeName().equals("id")) {
//				if (node.getFirstChild()!=null){
//					node = node.getFirstChild().getNextSibling();
//				}else{
//					node = node.getNextSibling();
//				}
//				if (node == null)return false;
//			}
//
//			// id,addr,postalCode,name,birthTime�^�O����
//			while (node!=null) {
//
//				System.out.println("�� " + node.getNodeName());
//	    		if (node.getNodeName().equals("id")){
//	    			String attrNm = getItemAttribute(node,"root");
//	    			// attr���OID�擾
//	    			String extensionVal = getItemAttribute(node,"extension");
//	    			if (attrNm.equals(OID_HOKENNUM)){
//	    				validatedKojinData.setHokenjyaNumber(extensionVal,true);
//	    			}else if (attrNm.equals(OID_HIHOKENSHA_KIGO)){
//	    				validatedKojinData.setHihokenjyaCode(extensionVal,true);
//	    				validatedKenshinData.setHihokenjyaCode(extensionVal,true);
//	    			}else if (attrNm.equals(OID_HIHOKENSHA_NUM)){
//	    				validatedKojinData.setHihokenjyaNumber(extensionVal,true);
//	    				validatedKenshinData.setHihokenjyaNumber(extensionVal,true);
//	    			}
//	    		}else if(node.getNodeName().equals("addr")){
//	    			Node fnode = node.getFirstChild();
//	    			validatedKojinData.setAddress(fnode.getNodeValue(),true);
//	    			fnode = fnode.getNextSibling();
//	    			if (fnode.getNodeName().equals("postalCode"))
//	    				validatedKojinData.setZipcode(fnode.getTextContent(),true);
//	    		}else if (node.getNodeName().equals("patient")){
//
//	    			Node fnode = node.getFirstChild();
//
//	    			while (!fnode.getNodeName().equals("name")){
//	    				fnode = fnode.getNextSibling();
//	    				if (fnode == null)return false;
//	    			}
//	    			if (fnode.getNodeName().equals("name"))
//	    				validatedKojinData.setNameKana(fnode.getTextContent(),true);
//
//	    			while (!fnode.getNodeName().equals("administrativeGenderCode")) {
//	    				fnode = fnode.getNextSibling();
//	    				if (fnode == null)return false;
//	    			}
//	    			if (fnode.getNodeName().equals("administrativeGenderCode")){
//	    				validatedKojinData.setSex(getItemAttribute(fnode, "code"),true);
//	    				if (fnode == null)return false;
//	    			}
//	    			while (!fnode.getNodeName().equals("birthTime")) {
//	    				fnode = fnode.getNextSibling();
//	    				if (fnode == null)return false;
//	    			}
//	    			if(fnode.getNodeName().equals("birthTime"))
//	    				validatedKojinData.setBirthday(getItemAttribute(fnode, "value"),true);
//	    		}
//				node = node.getNextSibling();
//			}
//    	}
//
//    	/* author�^�O �� �ȗ� */
//    	/* custodian�^�O �� �ȗ� */
//    	/* participant�^�O [�l���(��f�����)] */
//    	else if(node.getNodeName().equals(HL7_TAGS_PARTICIPANT)){
//
//    		System.out.println("���� " + node.getNodeName());
//    		Node childTime = node.cloneNode(true);
//
//    		// time�^�O{1}�����d�x{}
//    		// high�^�O{0 or 1}�����d�x{}
//			while (!childTime.getNodeName().equals("time")) {
//				if (childTime.getFirstChild()!=null){
//					childTime = childTime.getFirstChild().getNextSibling();
//				}else{
//					childTime = childTime.getNextSibling();
//				}
//				if (childTime == null)return false;
//			}
//			// high�^�O����{0 or 1}
//    		if(childTime.getNodeName().equals("time")){
//    			// high�^�O�̒��o
//    			Node timeValue = childTime.cloneNode(true);
//    			while (!timeValue.getNodeName().equals("high")) {
//    				if (timeValue.getFirstChild()!=null){
//    					timeValue = timeValue.getFirstChild();
//    				}else{
//    					timeValue = timeValue.getNextSibling();
//    				}
//    				if (timeValue == null)break;
//    			}
//
//    			if (timeValue != null){
//    				validatedKojinData.setLimitDate(getItemAttribute(timeValue,"value"),true);
//    			}else{
//    				// null�̏ꍇ�A{0}�̃P�[�X�Ŏ��̗v�f��
//    				childTime = childTime.getNextSibling();
//    			}
//    		}
//
//    		/* associatedEntity�̎q{id}�擾 */
//    		// edit s.inoue 2010/11/04
//    		// childTime.getParentNode().getNextSibling();
//			Node childEntity = childTime.getNextSibling();
//
//       		// associatedEntity{1}�^�O�̎qid{1}
//			while (!childEntity.getNodeName().equals("associatedEntity")) {
//				childEntity = childEntity.getNextSibling();
//				if (childEntity == null)return false;
//			}
//			// id
//			while (!childEntity.getNodeName().equals("id")) {
//				if (childEntity.getFirstChild()!=null){
//					childEntity = childEntity.getFirstChild().getNextSibling();
//				}else{
//					childEntity = childEntity.getNextSibling();
//				}
//				if (childEntity == null)return false;
//			}
//
//    		if(childEntity.getNodeName().equals("id")){
//    			String attrNm = getItemAttribute(childEntity,"root");
//    			// edit s.inoue 2010/12/01
//    			attrNm = attrNm.substring(0,attrNm.lastIndexOf("."));
//    			if (attrNm.equals(OID_JYUSINKEN_NUM)){
//    				validatedKojinData.setJyushinkenID(getItemAttribute(childEntity,"extension"),true);
//    			}
//    		}
//    	}
//
//    	/* documentationOf�^�O[���f���{��] */
//    	else if (node.getNodeName().equals(HL7_TAGS_DOCUMENTATIONOF)){
//
//       		// �q�^�Oid�ֈړ�
//			while (!node.getNodeName().equals("effectiveTime")) {
//				if (node.getFirstChild()!=null){
//					node = node.getFirstChild().getNextSibling();
//				}else{
//					node = node.getNextSibling();
//				}
//				if (node == null)return false;
//			}
//
//    		System.out.println("���� " + node.getNodeName());
//    		validatedKenshinData.setKensaJissiDate(getItemAttribute(node, "value"),true);
//
//    		// edit s.inoue 2010/08/30
//       		// �q�^�Oid�ֈړ�
//			while (!node.getNodeName().equals("id")) {
//				if (node.getFirstChild()!=null){
//					node = node.getFirstChild().getNextSibling();
//				}else{
//					node = node.getNextSibling();
//				}
//				if (node == null)return false;
//			}
//
//    		if(nodeVal.equals("id")){
//    			String attrNm = getItemAttribute(node,"root");
//    			if (attrNm.equals(OID_JISIKIKAN_NUM)){
//    				kikanNumber = getItemAttribute(node,"extension");
//    			}
//    		}
//    	}
//
//    	/* component�^�O [��������] */
//    	// �����f���ڃR�[�h�ɂ�蔻��
//    	else if (node.getNodeName().equals(HL7_TAGS_COMPONENT)){
//    		System.out.println("�������� " + node.getNodeName());
//
//       		// �q�^�Oid�ֈړ�
//			while (!node.getNodeName().equals("code")) {
//				if (node.getFirstChild()!=null){
//					node = node.getFirstChild().getNextSibling();
//				}else{
//					node = node.getNextSibling();
//				}
//				if (node == null)break;
//			}
//
//			/* ���f����<code>�^�O�����݂���ԃ��[�v���� */
//			Node fnode = node;
//			while(fnode != null){
//				System.out.println("node��:" + fnode.getNodeName());
//
////				// edit s.inoue 2010/11/02
////				// �q�m�[�h�^�O�̏ꍇ�A�ʏ���
////				if (fnode.getNodeName().equals("entry") ||
////					fnode.getNodeName().equals("observation")){
////					// ���{�敪�F�u�����{:0�v �� value�������o�����Ȃ�
////					if (fnode.getNodeName().equals("observation")){
////						String negationIndFlg = getItemAttribute(fnode, "negationInd");
////						if (negationIndFlg != null){
////							if(negationIndFlg.equals("true")){
////								koumokuCodeElseMap.put("", "0");
////							}
////						}
////					}
////					node = node.getFirstChild();
////				}
//
//				// ���ڃR�[�h
//				String koumokuCode = getItemAttribute(fnode, "code");
//
//	    		// ���f���ڃR�[�h=code�^�O�̏ꍇ�������s��
//	    		if (fnode.getNodeName().equals(HL7_COLUMN_KENSINKOUMOKU)){
//					// ���f����=17���̂��̂�ΏۂƂ���
//		    		if (koumokuCode.toString().length() == 17){
//	    	        	// code���������value�^�O���擾���Anext�ɃZ�b�g����
//	    				while (!fnode.getNodeName().equals("value")) {
//	    					fnode = fnode.getNextSibling();
//	    					if (fnode == null)return false;
//	    				}
//
//	       				// ���f���ڎ��
//	    				String itemVal ="";
//	    				String type = getItemAttribute(fnode,"xsi:type");
//	    				if(type.equals("CO") ||
//	    						type.equals("CD")){
//	    					itemVal =getItemAttribute(fnode,"code");
//	    				}else if(type.equals("PQ")){
//	    					// type.equals("ST")
//	    					itemVal =getItemAttribute(fnode,"value");
//
//	    				// edit s.inoue 2010/10/22
//			    		}else if(type.equals("ST")){
//			    			itemVal = node.getTextContent();
//			    		}
//
//	    				System.out.println("type:" + type + " value:" + itemVal);
//
//	    				// edit s.inoue 2010/08/26
//	    				// ���f���ڕʐݒ�
//	    				// validatedKenshinData.setKensaJissiDate(itemVal);
//		    		}
//				}
//	    		fnode = fnode.getNextSibling();
//    		}
//    	}
//    	return true;
//    }
//
//    /* xmlAttribute���[�g�����擾 -  */
//    private static String getAttributeItemValue(Node node){
//
//    	String attributeItemText = "";
//		NamedNodeMap attrs = node.getAttributes();  						 		// NamedNodeMap�̎擾
//
//		if (attrs!=null) {
//			// value����
//			Node attr = attrs.getNamedItem("value");  						 		// value�����m�[�h
//			if (attr != null)
//				attributeItemText = attr.getNodeValue();				 			// value�����̒l
//
//			// code����
//			Node attrCode = attrs.getNamedItem("code");  							// code�����m�[�h
//			if (attrCode != null)
//				attributeItemText = attrCode.getNodeValue();				 		// code�����̒l
//
//			// extension����
//			Node attrExtension = attrs.getNamedItem("extension");  					 // Extension�����m�[�h
//			if (attrExtension != null)
//				attributeItemText = attrExtension.getNodeValue();					// Extension�����̒l
//
//			// �����̃e�L�X�g
//			if (attributeItemText.equals("") &&
//					node.hasChildNodes()){
//				attributeItemText = node.getFirstChild().getTextContent().toString().trim();
//			}
//		}
//		return attributeItemText;
//    }
//
//    /* NodeAttribute��ԋp */
//    private static String getItemAttribute(Node node,String attrNm){
//    	String itemAttr = null;
//		NamedNodeMap attrs = node.getAttributes();  			// NamedNodeMap�̎擾
//
//		if (attrs!=null) {
//			// root����(key�l)
//			Node attrRoot = attrs.getNamedItem(attrNm);  		// root�����m�[�h
//			if (attrRoot != null)
//				itemAttr = attrRoot.getNodeValue();
//		}
//		return itemAttr;
//    }
//
//////hl�捞-�m�[�h�T����/////////////////////////////////////////////
//
//////hl�捞-�o�^������/////////////////////////////////////////////
//    /*
//     * xmlDataDB�o�^��
//     */
//    private boolean registerXmlData(){
//
//    	try {
//// 			JApplication.kikanDatabase.Transaction();
//
//	    	// �l���̓���(�����ԍ� or (���� and ���� and ���N����))
//	    	boolean existKojin = serchKojin();
//
//	    	// �ی���(�����ɖ����ꍇ�A���X�g���I��������)
//	    	if (!serchHokenjya()) return false;
//
//	    	// add s.inoue 2010/12/01
//	    	// ��s�@��(�����ɖ����ꍇ�A���X�g���I��������)
//	    	if (!serchDaikou()) return false;
//
//	    	// ���f�p�^�[��[�K�{](���X�g���I��������)
//	    	if (serchKenshinPattern()) return false;
//
//	    	// �l�o�^(Kojin) �f�[�^�������ꍇ�ǉ�����
//	    	if (validatedKojinData.getErrorMessageCount() > 0)
//	    		return false;
//
//// edit s.inoue 2010/12/01
////	    	if (validatedKojinData.getUketukeID().equals("")
////	    			&& !existKojin)
//
//// �l�f�[�^������ꍇ�A�����f�[�^���X�V�B�Ȃ��ꍇ�A�V���Ɏ�tID�̔�
//	    	registerKojin(existKojin);
//
//	    	// ���肵���Ώێ҂̌��ʃt�@�C��������(�l + ���f���{����)
//	    	// T_KENSAKEKA_TOKUTEI (delete �� insert)
//	    	if (Data.uketukeNo != null)
//	    		deleteKekkaTokutei();
//
//	    	// T_KENSAKEKA_TOKUTEI �o�^
//	    	registerKekkaTokutei();
//
//	    	// T_KENSAKEKA_SONOTA (delete �� insert)
//	    	if (Data.uketukeNo != null)
//	    		deleteKekkaSonota();
//
//	    	// T_KENSAKEKA_SONOTA �o�^
//	    	registerKekkaSonota();
//
//	    	// del s.inoue 2010/12/01
//	    	// T_NAYOSE �o�^
//	    	// registerNayose();
//
////	    	JApplication.kikanDatabase.Commit();
//	    	String msch = "����:"+ Data.kanaShimei + " ���f���{��:" + Data.jishiDate;
//	    	String[] messageParams = {msch};
//	    	JErrorMessage.show("M3330", this, messageParams);
//		} catch (Exception ex) {
//			try {
//				logger.error(ex.getMessage());
//				JApplication.kikanDatabase.rollback();
//			} catch (SQLException e) {}
//			System.out.println(ex.getMessage());
//		}
//		return true;
//    }
//
//    /*
//     * ���f�p�^�[�������_�C�A���O
//     */
//    private boolean serchKenshinPattern(){
//
//    	boolean cancelFlg = false;
//    	// �����p���b�Z�[�W����
//    	String messageTaisyosya = "";
//    	messageTaisyosya = "����:" + Data.kanaShimei + " ���f���{��:" + Data.jishiDate;
//
//		try {
//			patternSelectDialog = DialogFactory.getInstance().createDialog(this,messageTaisyosya);
//
//			// ���f���{�����̓_�C�A���O��\��
//			patternSelectDialog.setMessageTitle("���f�p�^�[���I�����");
//			patternSelectDialog.setVisible(true);
//			// �ߒl�i�[
//			if(patternSelectDialog.getStatus().equals(RETURN_VALUE.YES)){
//				kenshinPatternNumver = patternSelectDialog.getPrintSelect();
//			}else if (patternSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL)){
//				cancelFlg = true;
//			}
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//			JErrorMessage.show("M3336", null);
//		}
//
//		System.out.println("�I�����f�p�^�[��:" + kenshinPatternNumver);
//		return cancelFlg;
//    }
//
//    /*
//     * ��s�@��(�����ɖ����ꍇ�A���X�g���I��������)
//     */
//    private static boolean serchDaikou(){
//		// �l���ʃf�[�^:(�����ԍ�,���f���P�ʂŎ擾)
//    	String daikouNum = validatedKojinData.getDaikouNumber();
//		ArrayList<Hashtable<String, String>> hokenjyaData = null;
//		ArrayList<Hashtable<String, String>> selectShiharaiData;
//
//    	try {
//    		int historyCnt = 0;
//    		historyCnt = shiharaiDao.selectByCountUniqueKey(daikouNum);
//
//    		// ���R�[�h���Ȃ��ꍇ�A�����f�[�^�����X�g�����ĕ\��
//    		if (historyCnt == 0){
//    			selectShiharaiData = getShiharaiData();
//				if (selectShiharaiData == null ||
//						selectShiharaiData.size() == 0){
//					JErrorMessage.show("M3338", null);
//					return false;
//				}
//
//				Hashtable<String, String>selectedShiharaiData
//					= searchDaikouData(getShiharaiData(),Data,true);
//
//				if (selectedShiharaiData == null)return false;
//
//				String daikouNumber = selectedShiharaiData.get("SHIHARAI_DAIKO_NO");
//
//				validatedKojinData.setDaikouNumber(daikouNumber);
//
//				System.out.println("�x����s�@��" +selectedShiharaiData);
//    		}
//    	}catch(Exception ex){
//    		logger.error(ex.getMessage());
//    		JErrorMessage.show("M3338", null);
//    		return false;
//    	}
//    	return true;
//    }
//
//    /*
//     * �ی���(�����ɖ����ꍇ�A���X�g���I��������)
//     */
//    private static boolean serchHokenjya(){
//		// �l���ʃf�[�^:(�����ԍ�,���f���P�ʂŎ擾)
//    	String hokenjyaNum = validatedKojinData.getHokenjyaNumber();
//		ArrayList<Hashtable<String, String>> hokenjyaData = null;
//		ArrayList<Hashtable<String, String>> selectHokenjyaData;
//
//    	try {
//    		int historyCnt = 0;
//    		historyCnt = hokenjyaDao.selectByCountUniqueKey(hokenjyaNum);
//
//    		// ���R�[�h���Ȃ��ꍇ�A�����f�[�^�����X�g�����ĕ\��
//    		if (historyCnt == 0){
//
//    			selectHokenjyaData = getHokenjyaData();
//				if (selectHokenjyaData == null ||
//						selectHokenjyaData.size() == 0){
//					JErrorMessage.show("M3333", null);
//					return false;
//				}
//
//				// �Y������l�����������ꍇ�A���X�g��\������
//				Hashtable<String, String>selectedHokenjyaData
//					= searchHokenjyaData(getHokenjyaData(),Data,true);
//
//				if (selectedHokenjyaData == null)return false;
//
//				String hokenjyaNumber = selectedHokenjyaData.get("HKNJANUM");
//
//				validatedKojinData.setHokenjyaNumber(hokenjyaNumber,true);
//
//				System.out.println("�ی���" +selectedHokenjyaData);
//    		}
//    	}catch(Exception ex){
//    		logger.error(ex.getMessage());
//    		JErrorMessage.show("M3334", null);
//    	}
//    	return true;
//    }
//
//    // add s.inoue 2010/11/30
//	/**
//	 * ��s�@�֔ԍ��̑��݃`�F�b�N
//	 * ���݂��Ȃ���΃��X�g�\�����A�I��������
//	 */
//	private static Hashtable<String,String> searchDaikouData(
//			ArrayList<Hashtable<String,String>> selectedDaikouData,
//			JImportErrorResultFrameData Data,
//			boolean convertFlg){
//
//		// �Y������l������
//		String[] row = new String[5];
//
//		Vector<JSelectShiharaiFrameData> shiharaiData = new Vector<JSelectShiharaiFrameData>();
//		Hashtable<String,String> targetItem = null;
//
//		String daikouNum = "";
//		String predaikouNum = "";
//
//		for(int j = 0 ; j < selectedDaikouData.size() ; j++)
//		{
//			Hashtable<String,String> resultItem = selectedDaikouData.get(j);
//			daikouNum = resultItem.get("SHIHARAI_DAIKO_NO");
//			if (!daikouNum.equals(predaikouNum)){
//				JSelectShiharaiFrameData DataSame = new JSelectShiharaiFrameData();
//				DataSame.shiharai_daiko_no = (String) daikouNum;
//				DataSame.shiharai_daiko_name = new String(resultItem.get("SHIHARAI_DAIKO_NAME"));
//				DataSame.shiharai_daiko_zipcd = new String(resultItem.get("SHIHARAI_DAIKO_ZIPCD"));
//				DataSame.shiharai_daiko_adr = new String(resultItem.get("SHIHARAI_DAIKO_ADR"));
//				DataSame.shiharai_daiko_tel= new String(resultItem.get("SHIHARAI_DAIKO_TEL"));
//
//				DataSame.DatabaseItem = resultItem;
//				System.out.println("�x��no" + DataSame.shiharai_daiko_no+ "�x��Nm"+  DataSame.shiharai_daiko_name);
//				shiharaiData.add(DataSame);
//				targetItem = resultItem;
//			}
//
//			predaikouNum = daikouNum;
//		}
//
//		if(shiharaiData.size() >= 1)
//		{
//			JSelectShiharaiFrameCtrl SelectShiharaiFrame = new JSelectShiharaiFrameCtrl(shiharaiData,"");
//			JSelectShiharaiFrameData SelectedData = SelectShiharaiFrame.GetSelectedData();
//
//			if(SelectedData == null)
//			{
//				String message = "����:" + Data.kanaShimei + "���f���{��:" + Data.jishiDate;
//				ResultMessage += GetErrorMessage("�x����s�@�ւ��I������܂���ł����B�������X�L�b�v���܂��B",message);
//				targetItem=null;
//			}else{
//				// targetItem = SelectedData.DatabaseItem;
//				targetItem = SelectedData.DatabaseItem;
//			}
//		}
//		return targetItem;
//	}
//
//	/**
//	 * �ی��Ҕԍ��̑��݃`�F�b�N
//	 * ���݂��Ȃ���΃��X�g�\�����A�I��������
//	 */
//	private static Hashtable<String,String> searchHokenjyaData(
//			ArrayList<Hashtable<String,String>> selectedHokenjyaData,
//			JImportErrorResultFrameData Data,
//			boolean convertFlg){
//
//		// �Y������l������
//		String[] row = new String[5];
//
//		Vector<JSelectHokenjyaFrameData> hokenjyaData = new Vector<JSelectHokenjyaFrameData>();
//		Hashtable<String,String> targetItem = null;
//
//		// �ی��҃f�[�^�\��
//		String hknjyaNum = "";
//		String prehknjyaNum = "";
//
//		for(int j = 0 ; j < selectedHokenjyaData.size() ; j++)
//		{
//			Hashtable<String,String> resultItem = selectedHokenjyaData.get(j);
//			hknjyaNum = resultItem.get("HKNJANUM");
//			if (!hknjyaNum.equals(prehknjyaNum)){
//
//				JSelectHokenjyaFrameData DataSame = new JSelectHokenjyaFrameData();
//				DataSame.hknjanum = (String) hknjyaNum;
//				DataSame.hknjaname = new String(resultItem.get("HKNJANAME"));
//				DataSame.post = new String(resultItem.get("POST"));
//				DataSame.adress = new String(resultItem.get("ADRS"));
//				DataSame.tel= new String(resultItem.get("TEL"));
//
//				DataSame.DatabaseItem = resultItem;
//				System.out.println("�ی���no" + DataSame.hknjanum+ "�ی���Nm"+  DataSame.hknjaname);
//				hokenjyaData.add(DataSame);
//				targetItem = resultItem;
//			}
//
//			prehknjyaNum = hknjyaNum;
//		}
//
//		if(hokenjyaData.size() >= 1)
//		{
//			JSelectHokenjyaFrameCtrl SelectHokenjyaFrame = new JSelectHokenjyaFrameCtrl(hokenjyaData,"");
//			JSelectHokenjyaFrameData SelectedData = SelectHokenjyaFrame.GetSelectedData();
//
//			if(SelectedData == null)
//			{
//				String message = "����:" + Data.kanaShimei + "���f���{��:" + Data.jishiDate;
//				ResultMessage += GetErrorMessage("�ی��҂��I������܂���ł����B�������X�L�b�v���܂��B",message);
//				targetItem=null;
//			}else{
//				// targetItem = SelectedData.DatabaseItem;
//				targetItem = SelectedData.DatabaseItem;
//			}
//		}
//		return targetItem;
//
//
////		//���������E���ꐶ�N�����̎�f�҂����������ꍇ�̏���
////		if(SameKojinData.size() > 1)
////		{
////			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,FLAME_TITLE_MESSAGE);
////			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();
////
////			if(SelectedData == null)
////			{
////				ResultMessage += GetErrorMessage("��f�҂��I������܂���ł����B�������X�L�b�v���܂��B",Data.kanaShimei);
////				TargetItem=null;
////			}else{
////				TargetItem = SelectedData.DatabaseItem;
////			}
////		}
////		return TargetItem;
//
//
////		for(int j = 0 ; j < selectedHokenjyaData.size() ; j++)
////		{
////			Hashtable<String,String> DatabaseItem = selectedHokenjyaData.get(j);
////
////			String Name = DatabaseItem.get("KANANAME");
////
////			if(JEraseSpace.EraceSpace(Name).equals(JEraseSpace.EraceSpace(Data.kanaShimei)))
////			{
////				JSelectKojinFrameData DataSame = new JSelectKojinFrameData();
////
////				DataSame.jusinSeiNo = DatabaseItem.get("JYUSHIN_SEIRI_NO");
////				DataSame.name = DatabaseItem.get("NAME");
////				DataSame.kanaShimei = DatabaseItem.get("KANANAME");
////				DataSame.seiNenGapi = DatabaseItem.get("BIRTHDAY");
////				DataSame.seiBetu = DatabaseItem.get("SEX");
////				// edit ver2 s.inoue 2009/08/19
////				DataSame.jyusho= DatabaseItem.get("HOME_ADRS");
////				DataSame.hihokenshaKigo = DatabaseItem.get("HIHOKENJYASYO_KIGOU");
////				DataSame.hihokenshaNo = DatabaseItem.get("HIHOKENJYASYO_NO");
////				DataSame.DatabaseItem = DatabaseItem;
////
////				SameKojinData.add(DataSame);
////				TargetItem = DatabaseItem;
////			}
////		}
////
////		//���������E���ꐶ�N�����̎�f�҂����������ꍇ�̏���
////		if(SameKojinData.size() > 1)
////		{
////			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,FLAME_TITLE_MESSAGE);
////			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();
////
////			if(SelectedData == null)
////			{
////				ResultMessage += GetErrorMessage("��f�҂��I������܂���ł����B�������X�L�b�v���܂��B",Data.kanaShimei);
////				TargetItem=null;
////			}else{
////				TargetItem = SelectedData.DatabaseItem;
////			}
////		}
////		return TargetItem;
//	}
//
//	// add s.inoue 2010/11/30
//	/**
//	 * �x���f�[�^�擾(����:�����A�N��A����)
//	 */
//	private static ArrayList<Hashtable<String, String>>
//		getShiharaiData() throws Exception{
//
//		ArrayList<Hashtable<String, String>> shiharaiData = null;
//
//		try{
//
//			String query= new String(
//				"SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL" +
//				" FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO DESC");
//			shiharaiData = JApplication.kikanDatabase.sendExecuteQuery(query);
//
//			} catch (Exception err) {
//				throw err;
//			}
//		return shiharaiData;
//	}
//
//	/**
//	 * �ی��҃f�[�^�擾(����:�����A�N��A����)
//	 */
//	private static ArrayList<Hashtable<String, String>>
//		getHokenjyaData() throws Exception{
//
//		ArrayList<Hashtable<String, String>> kenshinData = null;
//
//		try{
//
//			String query= new String(
//				"SELECT DISTINCT HKNJANUM,HKNJANAME,POST,ADRS,BANTI,TEL FROM T_HOKENJYA ORDER BY HKNJANUM DESC");
//			kenshinData = JApplication.kikanDatabase.sendExecuteQuery(query);
//
//			} catch (Exception err) {
//				throw err;
//			}
//		return kenshinData;
//	}
//    /*
//	 *  �l���茟�� ���X�g�����ĉ�ʂ�\�����đI��������
//	 *  (��f�����ԍ�) or (�J�i���� + ���N���� + ����)
//	 */
//    private boolean serchKojin(){
//
//    	boolean retExistKojin = false;
//
//		// �l���ʃf�[�^:(�����ԍ�,���f���P�ʂŎ擾)
//		String jusinkenID = validatedKojinData.getJyushinkenID();
//
//		ArrayList<Hashtable<String, String>> kojinData = null;
//		ArrayList<Hashtable<String, String>> selectKojinData;
//
//    	try {
//	    		System.out.println("ID" + jusinkenID);
//				Data.jusinSeiriNo = jusinkenID;
//				Data.jishiDate = validatedKenshinData.getKensaJissiDate();
//				Data.kanaShimei = validatedKojinData.getNameKana();
//				Data.seiNenGapi = validatedKojinData.getBirthday();
//				Data.seiBetu = validatedKojinData.getSex();
//				Data.jisiKikanNo = kikanNumber;
//
//				if (!jusinkenID.equals("")){
//					kojinData = getKojinData(Data);
//					if (kojinData != null){
//						if (kojinData.size()>0)
//							Data.uketukeNo =kojinData.get(0).get("UKETUKE_ID");
//					}
//				}
//			} catch (Exception ex) {
//				logger.error(ex.getMessage());
//				JErrorMessage.show("M3335", null);
//			}
//
//		// �Y������f�[�^���Ȃ��ꍇ�A�l(����,���N����,����)�Ō���
//		if (kojinData == null ||
//				kojinData.size() == 0){
//			try{
//
//				selectKojinData = getSimeiData(Data);
//
//				if (selectKojinData == null ||
//						selectKojinData.size() == 0){
//				}
//				// �Y������l�����������ꍇ�A���X�g��\������
//				// edit s.inoue 2010/02/03
//				Hashtable<String, String>selectedKojinData
//					= searchKojinData(selectKojinData,Data,false);
//
//				// �f�[�^�����݂��Ȃ��ꍇ�A������
//				if(selectedKojinData != null){
//					String uketukeId = selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);
//					validatedKenshinData.setUketuke_id(uketukeId);
//					Data.uketukeNo = uketukeId;
//
//					kojinData = getSelectKojinData(Data);
//					if (kojinData == null ||
//							kojinData.size() == 0){
//					}
//				}
//			} catch (Exception ex) {
//				logger.error(ex.getMessage());
//				JErrorMessage.show("M3335", null);
//			}
//		}
//		if (kojinData != null)
//			retExistKojin = true;
//
//		return retExistKojin;
//    }
//
//    /*
//	 *  �l�o�^(T_KOJIN) �ɂ��Ƃ����Ɍl�f�[�^�������ꍇ�A�쐬����
//	 */
//    private static void registerKojin(boolean existflg)throws Exception{
//
//    	String uketukeNewID = "";
//
//    	// edit s.inoue 2010/12/02
//    	// �l�f�[�^������΁A���̎�tID���g�p
//    	if (existflg){
//    		Data.uketukeNo = validatedKojinData.getUketukeID();
//    	}else{
//    		uketukeNewID = String.valueOf(kojinDao.selectNewUketukeId());
//        	// ��tID�̔�
//        	validatedKojinData.setUketukeID(uketukeNewID);
//        	validatedKenshinData.setUketuke_id(uketukeNewID);
//        	Data.uketukeNo = uketukeNewID;
//    	}
//    	System.out.println("��tID:" + uketukeNewID);
//
//    	// �l���o�^
//    	JApplication.kikanDatabase.sendNoResultQuery(createRegisterKojinSql());
//
//    	System.out.println("�o�͌��� " +
//    			" hokennm:" + validatedKojinData.getHokenjyaNumber() +
//    			" hokenkigo:" + validatedKojinData.getHihokenjyaNumber() +
//    			" birthTime:" + validatedKojinData.getBirthday()
//    			);
//    }
//
//    /**
//	 * ��f�����o�^�p SQL ���쐬����B
//	 */
//	private static String createRegisterKojinSql() {
//
//		StringBuffer query = null;
//			query = new StringBuffer();
//			query.append(" INSERT INTO T_KOJIN (");
//
//			T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();
//			int length = columns.length;
//
//			for (int i = 0; i < length; i++) {
//				String columnName = JKojinRegisterFrameData.getColumnName(columns[i]);
//				query.append(columnName);
//
//				if (i != length - 1) {
//					query.append(",");
//				}
//			}
//			query.append(" )VALUES ( ");
//
//			String value = null;
//			for (int i = 0; i < length; i++) {
//				value = validatedKojinData.get(columns[i]);
//
//				if (i != length - 1) {
//					query.append(JQueryConvert.queryConvertAppendComma((value)));
//				}else {
//					query.append(JQueryConvert.queryConvert(value));
//				}
//			}
//			query.append(" ) ");
//
//		return query.toString();
//	}
//
//	/*
//	 *  ���ʃf�[�^�o�^(T_KENSAKEKA_TOKUTEI)
//	 */
//    private static void registerKekkaTokutei()throws Exception{
//
//       	// ��tID,�N�x�̒ǉ��Ή�
//		// �������ʃf�[�^���背�R�[�h�}��
//		kensakekaTokutei = new TKensakekaTokutei();
//
//		kensakekaTokutei.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
//		kensakekaTokutei.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());
//
//		String kenshinJisidate = validatedKenshinData.getKensaJissiDate();
//
//		kensakekaTokutei.setKENSA_NENGAPI(new Integer(kenshinJisidate));
//		kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(kenshinJisidate));
//
//		// ���ʒl����荞�� �� �o�^�ς�(InputFlg:2)
//		kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(2));
//
//		// uketukeID(�擾������ or �Ȃ�)
//		String uketukeId = validatedKenshinData.getUketuke_id();
//		if ( !uketukeId.equals("")){
//			kensakekaTokutei.setUKETUKE_ID(Long.parseLong(uketukeId));
//		}else if (!Data.uketukeNo.equals("")){
//			kensakekaTokutei.setUKETUKE_ID(Long.parseLong(Data.uketukeNo));
//		}
//
//		// �����敪(claims���Ǎ�)
//		// xml�t�@�C���P�̂̏ꍇ�A�����敪���擾�ł��Ȃ�����
//		if (!validatedKenshinData.getSeikyuKubunCode().equals("")){
//			kensakekaTokutei.setSEIKYU_KBN(new Integer(validatedKenshinData.getSeikyuKubunCode()));
//		}else{
//			kensakekaTokutei.setSEIKYU_KBN(1);
//		}
//
//		// ���f�p�^�[��No
//		kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumver));
//
//		kensakekaTokuteiDao.insert(kensakekaTokutei);
//
//		// HANTEI_NENGAPI�ȍ~�̍��ڂ͑ΏۊO�`
//		// �ۗ����� �������炭�捞�܂Ȃ�����
//		// kensakekaTokutei.setKENSA_CENTER_CD(validatedKenshinData.getKensaCenterCode());
//		// kensakekaTokutei.setUKETUKE_ID(new Long(validatedKenshinData.getUketuke_id()));
//		// kensakekaTokutei.setKOMENTO(validatedKenshinData.getSougouComment());
//    }
//
//	/*
//	 *  ���ʂ��̑��o�^(T_KENSAKEKA_SONOTA)
//	 */
//    private static void registerKekkaSonota()throws Exception{
//       	// Map�I�u�W�F�N�g�́u�L�[�v�̈ꗗ���擾
//    	// �o�^�O����:���f�R�[�h�����X�g��
//    	// �o���f�[�V�����Ŗ��o�^���f���ڃ`�F�b�N or �o�^�������������΂�
//        for (Iterator i = koumokuCodeMap.keySet ().iterator (); i.hasNext (); ) {
//        	Object koumokuItem = i.next ();
//        	Object koumokuValue = koumokuCodeMap.get (koumokuItem);
//
//        	if(importKenshinItem.contains(koumokuItem)){
//
//        		System.out.println("key:" + koumokuItem + " value:" + koumokuValue);
//
//				// �������ʃf�[�^���̑����R�[�h�폜�E�}��
//				kensakekaSonota = new TKensakekaSonota();
//
//				// ���f���{��,�N�x
//				kensakekaSonota.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
//
//				String kenshinJisidate = validatedKenshinData.getKensaJissiDate();
//				kensakekaSonota.setKENSA_NENGAPI(new Integer(kenshinJisidate));
//				kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(kenshinJisidate));
//				kensakekaSonota.setKOUMOKU_CD(koumokuItem.toString());
//				kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
//				kensakekaSonota.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());
//
//				// ���{�敪�F���{
//				kensakekaSonota.setJISI_KBN(1);
//				kensakekaSonota.setKEKA_TI(koumokuValue.toString());
//
//				kensakekaSonotaDao.insert(kensakekaSonota);
//
//				// �l��������
//				// kensakekaSonota.setUKETUKE_ID(new Long(validatedKenshinData.getUketuke_id()));
//				// kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
//				// kensakekaSonota.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());
//        	}
//        }
//
//        // �����{�A����s�\{0,2}���̓o�^
//        for (Iterator i = koumokuCodeElseMap.keySet ().iterator (); i.hasNext (); ) {
//        	Object koumokuItem = i.next ();
//        	Object koumokuValue = koumokuCodeElseMap.get (koumokuItem);
//			// �������ʃf�[�^���̑����R�[�h�폜�E�}��
//			kensakekaSonota = new TKensakekaSonota();
//
//			kensakekaSonota.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
//			String kenshinJisidate = validatedKenshinData.getKensaJissiDate();
//			kensakekaSonota.setKENSA_NENGAPI(new Integer(kenshinJisidate));
//			kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(kenshinJisidate));
//			kensakekaSonota.setKOUMOKU_CD(koumokuItem.toString());
//			kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
//			kensakekaSonota.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());
//			kensakekaSonota.setJISI_KBN(Integer.parseInt((String)koumokuValue));
//			kensakekaSonota.setKEKA_TI(koumokuValue.toString());
//
//			kensakekaSonotaDao.insert(kensakekaSonota);
//        }
//
//    }
//
////    // add s.inoue 2010/12/01
////	/**
////	 * ���񂹃e�[�u���֓o�^���������{����B
////	 */
////	private void registerNayose(){
////
////		StringBuilder nayoseQuery = null;
////		StringBuilder newNayoseQuery = null;
////		// ����NO�̔�
////		long nayoseNo = -1L;
////		long retTNayoseNo = 0L;
////
////		// ���������擾
////		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
////		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());
////
////		try {
////			// ��tID
////			// edit s.inoue 2010/12/01
//////			String newUketukeId ="";
//////	    	if (!validatedKojinData.getpreUketukeID().equals("")){
//////	    		if (validatedKojinData.getUketukeID().equals(validatedKojinData.getpreUketukeID())){
//////	    			int diff = validatedKojinData.getdiff();
//////	    			diff++;
//////	    			newUketukeId = String.valueOf(kojinDao.selectNewUketukeId(diff));
//////	    		}else{
//////	    			newUketukeId = validatedKojinData.getUketukeID();
//////	    		}
//////	    	}else{
//////	    		newUketukeId = validatedKojinData.getUketukeID();
//////	    	}
//////			validatedKojinData.setpreUketukeID(newUketukeId);
////
////			String newUketukeId =validatedKojinData.getUketukeID();
////
////			/* T_NAYOSE Dao ���쐬����B */
////			tNayoseDao = (TNayoseDao) DaoFactory.createDao(
////					JApplication.kikanDatabase.getMConnection(), new TNayose());
////
////			retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedKojinData.getUketukeID()));
////			// ���ɗ���������ꍇ�A���̖���No���g�p����
////			if (retTNayoseNo > 0) {
////				nayoseNo = retTNayoseNo;
////			}else{
////				nayoseNo = tNayoseDao.selectNewNayoseNo();
////
////				// ���񂹃e�[�u���o�^����
////				// ��tID�̕R�t������:1�A2���Z�b�g
////				// 1.��f�����Ŏ������ȂŕR�t������tID��o�^
////				// 2.��f���o�^��ɐV������t�ԍ���o�^
////
////				// 1.����:�������Ȃ��ꍇ
////				nayoseQuery = new StringBuilder();
////				nayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
////				nayoseQuery.append("VALUES (");
////				nayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
////				nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedKojinData.getUketukeID()));
////				nayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
////				nayoseQuery.append(") ");
////
////				try {
////					JApplication.kikanDatabase.sendNoResultQuery(nayoseQuery.toString());
////				} catch (SQLException e) {
////					logger.error(e.getMessage());
////
////					try {
////						JApplication.kikanDatabase.rollback();
////					} catch (SQLException g) {
////						logger.error(g.getMessage());
////					}
////				}
////
////			}
////
////			// 2.����:�̔Ԃ����ǉ��p���R�[�h���ʏ���
////			newNayoseQuery = new StringBuilder();
////			newNayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
////			newNayoseQuery.append("VALUES (");
////			newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
////			newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(newUketukeId.toString()));
////			newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
////			newNayoseQuery.append(") ");
////
////			try {
////				JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
////			} catch (SQLException e) {
////				logger.error(e.getMessage());
////				try {
////					JApplication.kikanDatabase.rollback();
////				} catch (SQLException g) {
////					logger.error(g.getMessage());
////				}
////			}
////		} catch (Exception e) {
////			logger.error(e.getMessage());
////		}
////	}
//
//    /*
//     * ���ʍ폜(T_KENSAKEKA_TOKUTEI)
//     */
//    private static void deleteKekkaTokutei() throws Exception{
//		kensakekaTokuteiDao.delete(
//				Data.uketukeNo,
//				Data.jishiDate);
//    }
//
//    /*
//     * ���ʍ폜(T_KENSAKEKA_SONOTA)
//     */
//    private static void deleteKekkaSonota() throws Exception{
//		kensakekaSonotaDao.deleteByUketukeIdKensaNengapi(
//				Long.parseLong(Data.uketukeNo),
//				Integer.parseInt(Data.jishiDate));
//    }
//    /*-------------------------------------hl7-----------------------------------------------*/
//
//	/*-------------------------------------����-----------------------------------------------*/
//	/**
//	 * �����ԍ��Ō����ł��Ȃ��ꍇ�̍i���ݏ���
//	 * convertFlg:���Ȏ����𔼊p���S�p true,�S�p�����p false
//	 */
//	private Hashtable<String,String> searchKojinData(
//			ArrayList<Hashtable<String,String>> KojinDatabaseData,
//			JImportErrorResultFrameData Data,
//			boolean convertFlg){
//
//		// �Y������l������
//		Hashtable<String,String> TargetItem = null;
//
//		Vector<JSelectKojinFrameData> SameKojinData = new Vector<JSelectKojinFrameData>();
//
//		for(int j = 0 ; j < KojinDatabaseData.size() ; j++)
//		{
//			Hashtable<String,String> DatabaseItem = KojinDatabaseData.get(j);
//
//			// edit s.inoue 2010/02/03
//			//���p�J�^�J�i�A�󔒂�����������ԂŔ�r
//			String Name = "";
//			if (convertFlg){
//				Name = JZenkakuKatakanaToHankakuKatakana.Convert(DatabaseItem.get("KANANAME"));
//			}else{
//				Name = DatabaseItem.get("KANANAME");
//			}
//
//			if(JEraseSpace.EraceSpace(Name).equals(JEraseSpace.EraceSpace(Data.kanaShimei)))
//			{
//				JSelectKojinFrameData DataSame = new JSelectKojinFrameData();
//
//				DataSame.jusinSeiNo = DatabaseItem.get("JYUSHIN_SEIRI_NO");
//				DataSame.name = DatabaseItem.get("NAME");
//				DataSame.kanaShimei = DatabaseItem.get("KANANAME");
//				DataSame.seiNenGapi = DatabaseItem.get("BIRTHDAY");
//				DataSame.seiBetu = DatabaseItem.get("SEX");
//				// edit ver2 s.inoue 2009/08/19
//				DataSame.jyusho= DatabaseItem.get("HOME_ADRS");
//				DataSame.hihokenshaKigo = DatabaseItem.get("HIHOKENJYASYO_KIGOU");
//				DataSame.hihokenshaNo = DatabaseItem.get("HIHOKENJYASYO_NO");
//				DataSame.DatabaseItem = DatabaseItem;
//
//				SameKojinData.add(DataSame);
//				TargetItem = DatabaseItem;
//			}
//		}
//
//		//���̒i�K��null�������Ă�����Y������l�����Ȃ��̂ŁA�G���[���o���Ȃǂ̏������s���B
//		//if(TargetItem == null)
//		//{
//		//	ResultMessage += GetErrorMessage("�Y�������f�҂����܂���B",shimeiKana);
//		//}
//
//		//���������E���ꐶ�N�����̎�f�҂����������ꍇ�̏���
//		if(SameKojinData.size() > 1)
//		{
//			//ResultMessage += GetErrorMessage("���������E���ꐶ�N�����̎�f�҂����܂��B�I���_�C�A���O���o���܂��B",shimeiKana);
//
////			JSelectKojinFrameData SelectedData =  new JSelectKojinFrameData();
////			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JSelectKojinFrameCtrl(SameKojinData,this));
////			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this);
////			SelectedData =SelectKojinFrame.GetSelectedData();
//
//			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this,FLAME_KOJIN_TITLE_MESSAGE);
//			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();
//
//			if(SelectedData == null)
//			{
//				ResultMessage += GetErrorMessage("��f�҂��I������܂���ł����B�������X�L�b�v���܂��B",Data.kanaShimei);
//				TargetItem=null;
//			}else{
//				TargetItem = SelectedData.DatabaseItem;
//			}
//		}
//		return TargetItem;
//	}
//
//	/**
//	 * �G���[��ʐݒ菈��
//	 */
//	private void setImportErrDigit(
//			//Hashtable<String,String> DatabaseItem,
//			Vector<JImportErrorResultFrameData> ErrorResultData,
//			JImportErrorResultFrameData Data,
//			Integer i){
//
//		// add ver2 s.inoue 2009/07/27
//		// DB����
//		// Data.rowNo =i + 1;
//		// ErrorResultData.add(Data);
//
//		JImportErrorResultFrameData DataKeys = new JImportErrorResultFrameData();
//
//		DataKeys.errCase = Data.errCase;
//		DataKeys.errField = Data.errField;
//		DataKeys.jusinSeiriNo =Data.jusinSeiriNo;
//		DataKeys.jishiDate = Data.jishiDate;
//		DataKeys.kanaShimei = Data.kanaShimei;
//		DataKeys.seiNenGapi = Data.seiNenGapi;
//		DataKeys.seiBetu = Data.seiBetu;
//		DataKeys.jisiKikanNo = Data.jisiKikanNo;
//		DataKeys.nyuBi = Data.nyuBi;
//		DataKeys.shokuZenGo = Data.shokuZenGo;
//
//		DataKeys.koumokuCd = Data.koumokuCd;
//		DataKeys.jisiKbn = Data.jisiKbn;
//		DataKeys.kekkaTi = Data.kekkaTi;
//		DataKeys.rowNo =i + 1;
//
//		// DB����
//		//Data.rowNo =i + 1;
//		ErrorResultData.add(DataKeys);
//	}
//
//	/**
//	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{)
//	 */
//	private boolean checkKensaKekkaKeyData(
//				JImportErrorResultFrameData Data,
//				Vector<JImportErrorResultFrameData> ErrorResultData,
//				Integer i,
//				boolean flg)
//	{
//		boolean checkKensaKekkaFlg=false;
//
//		if (!Data.jusinSeiriNo.toString().equals("")){
//			// ��f�������ԍ�
//			if (validateImportJusinSeiriNo(Data))
//			{
//				setImportErrDigit(ErrorResultData,Data,i);
//				checkKensaKekkaFlg =true;
//
//			}
//		}
//		// �J�i������
//		if (validateImportkanaShimei(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// ���N����
//		if (validateImportseiNenGapi(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// ����
//		if (validateImportSex(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// ����/�n��
//		if (validateImportNyuBi(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// �H�O/�H��
//		if (validateImportShokuZenGo(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// ���f���{�N������
//		if (validateImportJisiDate(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//
//		return checkKensaKekkaFlg;
//	}
//
//	/**
//	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{�A������t�H�[�}�b�g�p�K�{)
//	 */
//	private boolean checkKensaKekkaKeyNitiiFormatData(
//				JImportErrorResultFrameData Data,
//				Vector<JImportErrorResultFrameData> ErrorResultData,
//				Integer i,
//				boolean flg)
//	{
//		boolean checkKensaKekkaFlg=false;
//
//		if (!Data.jusinSeiriNo.toString().equals("")){
//			// ��f�������ԍ�
//			if (validateImportJusinSeiriNo(Data))
//			{
//				setImportErrDigit(ErrorResultData,Data,i);
//				checkKensaKekkaFlg =true;
//
//			}
//		}
//		// �J�i������
//		if (validateImportNitiiFormatkanaShimei(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// ���N������
//		if (validateImportNitiiFormatseiNenGapi(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// ���ʁ�
//		if (validateImportNitiiFormatSex(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//		// ���f���{�N������
//		if (validateImportJisiDate(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//
//		return checkKensaKekkaFlg;
//
//	}
//
//	/**
//	 * �������ʃf�[�^�`�F�b�N����(�������)��format����
//	 */
//	private boolean checkKensaKekkaData(
//				JImportErrorResultFrameData Data,
//				Vector<JImportErrorResultFrameData> ErrorResultData,
//				Integer i) {
//
//		boolean checkKensaKekkaFlg=false;
//
//		// ���f���{�@�֔ԍ�
//		if (validateImportJisiKikanNo(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg =true;
//		}
//
//		return checkKensaKekkaFlg;
//	}
//
//	/**
//	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{)
//	 */
//	private boolean checkKoumokuCD(
//				JImportErrorResultFrameData Data,
//				Vector<JImportErrorResultFrameData> ErrorResultData,
//				Integer i)
//	{
//		boolean checkKensaKekkaFlg=false;
//		// ���ڃR�[�h��
//		if (validateKensaKoumokuCode(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
//		return checkKensaKekkaFlg;
//	}
//
//
//	/**
//	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{)
//	 */
//	private boolean checkKensaKekkaDataDetail(
//				JImportErrorResultFrameData Data,
//				Vector<JImportErrorResultFrameData> ErrorResultData,
//				Integer i)
//	{
//		boolean checkKensaKekkaFlg=false;
//
//		// ���{�敪��
//		if (validateImportJisiKbn(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
//
//// del 20080918 s.inoue
////		// �ُ�l�敪
////		if (validateImportHLKbn(Data))
////		{
////			setImportErrDigit(ErrorResultData,Data,i);
////			checkKensaKekkaFlg = true;
////		}
////		// ���ʒl�`��
////		if (validateImportKekkaTiKeitai(Data))
////		{
////			setImportErrDigit(ErrorResultData,Data,i);
////			checkKensaKekkaFlg = true;
////		}
//		// �������ʁ�
//		if (validateImportKekkaTi(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
//		return checkKensaKekkaFlg;
//	}
//
//	/* EDIT 2008/07/23 ��� */
//	/* --------------------------------------------------- */
//	/**
//	 * �������ʓ���o�^
//	 * @throws SQLException
//	 */
//	private void kensakekaTokuteiregister(JImportErrorResultFrameData Data)
//		throws SQLException
//	{
//
//		kensakekaTokutei = new TKensakekaTokutei();
//		// ����
//		kensakekaTokutei.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
//		kensakekaTokutei.setKENSA_NENGAPI(Integer.valueOf(Data.jishiDate));
//		kensakekaTokutei.setNYUBI_YOUKETU(Data.nyuBi);
//		kensakekaTokutei.setSYOKUZEN_SYOKUGO(Data.shokuZenGo);
//		// pending s.inoue
//		//if (mode == INSERT_MODE) {
//		//	kensakekaTokutei.setHIHOKENJYASYO_KIGOU("");
//		//	kensakekaTokutei.setHIHOKENJYASYO_NO("");
//		//	kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumber));
//		//	kensakekaTokutei.setHANTEI_NENGAPI(null);
//		//	kensakekaTokutei.setTUTI_NENGAPI(null);
//		//	kensakekaTokutei.setKENSA_CENTER_CD("");
//			// ���̂���UPDATE���s�����߈ꎞ�I�Ƀf�[�^������
//		//	kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(1));
//		//	kensakekaTokutei.setSEIKYU_KBN(new Integer(1));
//		//	kensakekaTokutei.setKOMENTO("");
//		//	kensakekaTokutei.setNENDO(null);
//		//	kensakekaTokuteiDao.insert(kensakekaTokutei);
//
//		//}else if (mode == UPDATE_MODE) {
//		// s.inoue 20080924
//		kensakekaTokuteiDao.update(kensakekaTokutei,false);
//		//}
//
//		/*String query = "UPDATE T_KENSAKEKA_TOKUTEI SET "
//			+ "NYUBI_YOUKETU = "
//			+ JQueryConvert.queryConvertAppendComma(this.nyubiYoketu)
//
//			+ "SYOKUZEN_SYOKUGO = "
//			+ JQueryConvert.queryConvert(this.shokuzenShokugo)
//
//			//+ "KENSA_CENTER_CD = "
//				//+ JQueryConvert.queryConvertAppendComma("")
//			//+ "K_P_NO = "
//				//+ JQueryConvert.queryConvertAppendComma("")
//			//+ "KOMENTO = "
//				//+ JQueryConvert.queryConvertAppendComma("")
//			//+ "SEIKYU_KBN = "
//				//+ JQueryConvert.queryConvert("")
//			+ " WHERE "
//			+ "UKETUKE_ID = "
//				+ JQueryConvert.queryConvert(this.uketukeId.toString())
//			+ " AND "
//			+ "	KENSA_NENGAPI = "
//				+ JQueryConvert.queryConvert(this.kensaDate.toString());
//			JApplication.kikanDatabase.sendNoResultQuery(query);*/
//	}
//	/* --------------------------------------------------- */
//
//	/* EDIT 2008/07/23 ��� */
//	/* --------------------------------------------------- */
//	/**
//	 * �������ʓo�^
//	 * @throws SQLException
//	 */
//	private void kensakekaSonotaregister(JImportErrorResultFrameData Data)
//		throws SQLException
//	{
//			// move4. �������ʓo�^����
//			// ad s.inoue �f�[�^�x�[�X�ɓo�^(T_KENSAKEKKA_SONOTA)
////			2008/3/19 ���R �C��
////			T_KOJIN,T_KENSAKEKA_TOKUTEI��UKETUKE_ID,NENDO�ǉ��Ή�
////			-------------------------------------------------------------------------------------------------------
//			TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
//			// ed s.inoue
//			// Map<String, String> recMap = KojinDatabaseData.get(i);
//			// ed s.inoue kensakekaSonota.setUKETUKE_ID(new Long(recMap.get("UKETUKE_ID")));
//			// kensakekaSonota.setHIHOKENJYASYO_KIGOU(TargetItem.get("HIHOKENJYASYO_KIGOU"));
//			// kensakekaSonota.setHIHOKENJYASYO_NO(TargetItem.get("HIHOKENJYASYO_NO"));
//			// kensakekaSonota.setKENSA_NENGAPI(new Integer(KensaDate));
//			// kensakekaSonota.setKOUMOKU_CD(KoumokuCode);
//			// kensakekaSonota.setKEKA_TI(Body.Kekka);
//
//			kensakekaSonota.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
//			kensakekaSonota.setKENSA_NENGAPI(Integer.valueOf(Data.jishiDate));
//			kensakekaSonota.setKOUMOKU_CD(Data.koumokuCd);
//			// notnull�̃t�B�[���h�l
//			kensakekaSonota.setHIHOKENJYASYO_KIGOU("");
//			kensakekaSonota.setHIHOKENJYASYO_NO("");
//
//			kensakekaSonota.setJISI_KBN(Integer.valueOf(Data.jisiKbn));
//			// del 20080918 s.inoue
//			//kensakekaSonota.setH_L(Integer.valueOf(JValidate.validateHLKubun(Data.ijyoKbn)));
//			//kensakekaSonota.setKEKA_TI_KEITAI(Integer.valueOf(JValidate.validateKekkaTiKeitai(Data.kekkaTiKeitai)));
//
//			kensakekaSonota.setKEKA_TI(Data.kekkaTi);
//
//			// pending s.inoue
//			// if (mode == INSERT_MODE) {
//			//	kensakekaSonotaDao.insert(kensakekaSonota);
//			// }else if (mode == UPDATE_MODE) {
//			kensakekaSonotaDao.update(kensakekaSonota);
//			// }
//
//			// del s.inoue
//			//try {
//			//	TKensakekaSonotaDao kensakekaSonotaDao =
//			//	(TKensakekaSonotaDao)DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), kensakekaSonota);
//			//	kensakekaSonotaDao.insert(kensakekaSonota);
//			//} catch (Exception e) {
//			//	e.printStackTrace();
//			//	ed s.inoue ImportItem��shimeiKana
//			//	ResultMessage += GetErrorMessage("�f�[�^�x�[�X�ւ̓o�^�Ɏ��s���܂����B����̃f�[�^�����݂���\��������܂��B",shimeiKana);
//			//	 del s.inoue
//			//	 continue;
//			//}
////		 -------------------------------------------------------------------------------------------------------
////						 del s.inoue }
//
//	}
//	/* --------------------------------------------------- */
//
//	/**
//	 * �������ʗL������
//	 */
//	private boolean existsKensaKekkaData(JImportErrorResultFrameData Data) {
//
//		TKensakekaSonota resultList = null;
//
//		try {
//			resultList = kensakekaSonotaDao.selectByPrimaryKey(
//					Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		boolean exsistsKensaKekkaData = false;
//		if (resultList != null) {
//
//			if (resultList.getKEKA_TI() !=null)
//			{
//				if (!resultList.getKEKA_TI().equals("")){
//					exsistsKensaKekkaData = true;
//				}
//			}
//		}
//
//		return exsistsKensaKekkaData;
//	}
//
//	/**
//	 * �l�f�[�^�擾(����:�����A�N��A����)
//	 */
//	private static ArrayList<Hashtable<String, String>>
//		getSimeiData(JImportErrorResultFrameData Data) throws Exception{
//
//		ArrayList<Hashtable<String, String>> kenshinData = null;
//
//		try{
//
//			String[] params = new String[] { JCalendarConvert.JCtoAD(Data.seiNenGapi),
//					Data.seiBetu,
//					};
//
//			kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
//				getSimeiDataSQL(), params);
//
//			} catch (Exception ex) {
//				logger.error(ex.getMessage());
//			}
//		return kenshinData;
//	}
//
//	/**
//	 * �l�f�[�^�擾
//	 */
//	private static ArrayList<Hashtable<String, String>>
//		getKojinData(JImportErrorResultFrameData Data) throws Exception{
//
//		ArrayList<Hashtable<String, String>> kenshinData = null;
//		try{
//
//				// omschange s.inoue
//				//String[] params = new String[] { Data.uketukeNo,
//				String[] params = new String[] {
//						Data.jusinSeiriNo.toString(),
//						Data.jishiDate,
//						Data.jishiDate,
//						};
//
//				kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
//					getKensaDataSQL(false), params);
//				if (kenshinData == null ||
//						kenshinData.size() <= 0) {
//					return null;
//				}
//			} catch (Exception err) {
//				throw err;
//			}
//		return kenshinData;
//	}
//
//	/**
//	 * �l�f�[�^�擾(��t�ԍ�)
//	 */
//	private static ArrayList<Hashtable<String, String>>
//		getSelectKojinData(JImportErrorResultFrameData Data) throws Exception{
//
//		ArrayList<Hashtable<String, String>> kenshinData = null;
//
//		try{
//				String[] params = new String[] {
//						Data.uketukeNo,
//						Data.jishiDate,
//						Data.jishiDate,
//						};
//
//				kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
//					getKensaDataSQL(true), params);
//				if (kenshinData == null ||
//						kenshinData.size() <= 0) {
//					return null;
//				}
//			} catch (Exception err) {
//				throw err;
//			}
//		return kenshinData;
//	}
//
//	// add s.inoue 2010/03/16
//	/**
//	 * BMI�l���v�Z����
//	 */
//	public String calcBMI(String height, String weight) {
//		// �ǂ��炩����l�̏ꍇ�͋�l��Ԃ�
//		if (height.isEmpty() || weight.isEmpty()) {
//			return "";
//		}
//
//		// �O���Z�G���[��h��
//		if (Double.valueOf(height) == 0) {
//			return "";
//		}
//
//		BigDecimal bmi = new BigDecimal(
//				String
//						.valueOf((Double.valueOf(weight) / ((Double
//								.valueOf(height) / 100) * (Double
//								.valueOf(height) / 100)))));
//		return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP)
//				.doubleValue());
//	}
//
//	/**
//	 * �������ʂ��̑��擾(����)
//	 */
//	private static String getFromSQL()
//	{
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(" T_KENSAKEKA_TOKUTEI AS TOKUTEI");
//		buffer.append(" LEFT JOIN T_KENSAKEKA_SONOTA AS SONOTA ");
//		buffer.append(" ON");
//		buffer.append(" (");
//		buffer.append("  TOKUTEI.UKETUKE_ID = SONOTA.UKETUKE_ID");
//		buffer.append(" )");
//		buffer.append(" LEFT JOIN T_KOJIN AS KOJIN ");
//		buffer.append(" ON ");
//		buffer.append(" ( ");
//		buffer.append(" SONOTA.UKETUKE_ID = KOJIN.UKETUKE_ID ");
//		buffer.append(" ) ");
//		return buffer.toString();
//	}
//
//	/**
//	 * �������ʂ��̑��擾
//	 * flgKeys true:��t�ԍ�, false:�����ԍ�
//	 */
//	private static String getKensaDataSQL(boolean flgKeys)
//	{
//		// add 20081002 s.inoue SONOTA��MASTER.KOUMOKU_CD�֕ύX
//		StringBuffer buffer = new StringBuffer();
//		//buffer.append(" SELECT DISTINCT KOJIN.UKETUKE_ID,TOKUTEI.KENSA_NENGAPI,MASTER.KOUMOKU_CD ");
//		buffer.append(" SELECT KOJIN.UKETUKE_ID,TOKUTEI.KENSA_NENGAPI,SONOTA.KOUMOKU_CD ");
//		buffer.append(" FROM ");
//		buffer.append(getFromSQL());
//
//		buffer.append(" WHERE");
//
//		if (flgKeys){
//			buffer.append(" TOKUTEI.UKETUKE_ID = ? ");
//		}else{
//			buffer.append(" KOJIN.JYUSHIN_SEIRI_NO = ? ");
//		}
//
//		buffer.append(" AND");
//		buffer.append(" TOKUTEI.KENSA_NENGAPI = ? ");
//		buffer.append(" AND");
//		buffer.append(" SONOTA.KENSA_NENGAPI = ? ");
//		String sql = buffer.toString();
//		return sql;
//	}
//
//	/**
//	 * �������ʂ��̑��擾(������)
//	 */
//	private static String getSimeiDataSQL()
//	{
//
//		StringBuffer buffer = new StringBuffer();
//		// edit ver2 s.inoue 2009/08/19
//		buffer.append("SELECT ");
//		buffer.append(" PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME,");
//		buffer.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL,");
//		buffer.append(" KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU,");
//		buffer.append(" MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO,");
//		buffer.append(" UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");
//
//		buffer.append(" FROM T_KOJIN ");
//		buffer.append(" WHERE ");
//		buffer.append(" BIRTHDAY = ? ");
//		buffer.append(" AND SEX = ? ");
//
//		return buffer.toString();
//
//	}
//
//	/**
//	 * ���s�R�[�h
//	 */
//	private static String LineSeparator = System.getProperty("line.separator");
//
//	/**
//	 * �G���[���b�Z�[�W�̌`���Ń��b�Z�[�W���擾����B
//	 * @param Message ���b�Z�[�W
//	 * @param Data �ǂݍ��ݒ��̃f�[�^
//	 * @return �t�H�[�}�b�g�ς݂̃e�L�X�g
//	 */
//	private String GetErrorMessage(String Message,JOriginalFormatData Data)
//	{
//		return "(" + Data.Name + ")" + Message + LineSeparator;
//	}
//
//	/**
//	 * �G���[���b�Z�[�W�̌`���Ń��b�Z�[�W���擾����B
//	 * @param Message ���b�Z�[�W
//	 * @param Name ���O
//	 * @return �t�H�[�}�b�g�ς݂̃e�L�X�g
//	 */
//	private static String GetErrorMessage(String Message,String Name)
//	{
//		return "(" + Name + ")" + Message + LineSeparator;
//	}
//
//	/**
//	 * �C���|�[�g�{�^��
//	 */
//	public void pushedImportButton(ActionEvent e)
//	{
//
//		String filePath = jTextField_FileName.getText();
//
//		try{
//			if( validateDate.setFilePath(filePath))
//			{
//				if( validateAsImportPushed(validateDate) )
//				{
//					if( validateDate.isValidateAsDataSet )
//					{
//						// log�t�@�C��
//						String logfile = PropertyUtil.getProperty("log.filename");
//						PropertyConfigurator.configure(logfile);
//
//	// edit s.inoue 2010/04/02
//	//					if(jRadio_SelectDokuji.isSelected()){
//							// ���ʎ�荞��
//							if (!checkImportOriginalFormatFile(filePath))
//								kekkaImport(filePath);
//
//							// add s.inoue 2010/10/20
//							if (!checkImportHL7FormatFile(filePath))
//								hl7Import(filePath);
//	// edit s.inoue 2010/04/02
//	//					}else{
//	//						// ����t�H�[�}�b�g
//	//						nitiImportCheck(validateDate.getFilePath());
//	//					}
//					}
//				}
//			}
//		} catch (Exception ex) {
//			logger.error("[HL7�捞����]�@��FDB�ւ̐ڑ������Ɏ��s���܂����B");
//		}
//	}
//
//	// add s.inoue 2010/10/20
//	/*
//	 * ���ʎ捞 csv�t�@�C��
//	 */
//	private boolean checkImportOriginalFormatFile(String FilePath){
//		boolean retCheck = false;
//
//		// 1.txt�`�����ǂ���
//		if (!FilePath.endsWith(".txt") &&
//				!FilePath.endsWith(".TXT") &&
//					!FilePath.endsWith(".csv") &&
//						!FilePath.endsWith(".CSV"))
//			retCheck = true;
//
//		return retCheck;
//	}
//
//	/*
//	 * ���ʎ捞 xml(HL7)�t�@�C��
//	 */
//	private boolean checkImportHL7FormatFile(String FilePath){
//		boolean retCheck = false;
//
//		// xml or zip�`�����ǂ���
//		if (!FilePath.endsWith(".xml") &&
//				!FilePath.endsWith(".XML") &&
//					!FilePath.endsWith(".zip") &&
//						!FilePath.endsWith(".ZIP"))
//			retCheck = true;
//
//		return retCheck;
//	}
//
//	/**
//	 * �I���{�^��
//	 */
//	public void pushedEndButton(ActionEvent e)
//	{
//		dispose();
//	}
//
//	class KnsaFileFilter extends FileFilter
//	{
//		public boolean accept(File f)
//		{
//			if(f.getName().equals("kakka.txt"))
//			{
//				return true;
//			}else{
//				return false;
//			}
//		}
//
//		public String getDescription() { return ""; }
//	}
//
//	/**
//	 * �Q�ƃ{�^��
//	 */
//	public void pushedOpenFileButton(ActionEvent e)
//	{
//		FileDialog fd = new FileDialog(this,"�������ʃf�[�^���w�肵�Ă�������",FileDialog.LOAD);
//		fd.setVisible(true);
//
//		if(fd.getDirectory() != null && fd.getFile() != null)
//		{
//			jTextField_FileName.setText(fd.getDirectory() + fd.getFile());
//		}
//	}
//
//	public void actionPerformed(ActionEvent e)
//	{
//		if( e.getSource() == jButton_End )
//		{
//			logger.info(jButton_End.getText());
//			pushedEndButton( e );
//		}
//
//		if( e.getSource() == jButton_Import )
//		{
//			logger.info(jButton_Import.getText());
//			pushedImportButton( e );
//		}
//
//		if( e.getSource() == jButton_OpenFile )
//		{
//			logger.info(jButton_OpenFile.getText());
//			pushedOpenFileButton( e );
//		}
//	}
//
//	// add s.inoue 2009/12/03
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Import.getText());
//			pushedImportButton(null);break;
//		}
//	}
//
//////hl7�O������/////////////////////////////////////////////
//
//	   /* OID���֘A�t�� */
//    private static HashMap<String, String> getOIDAssociation(){
//    	HashMap<String, String> hmOID = new HashMap<String, String>();
//    	hmOID.put("1.2.392.200119.6.101", "HKNJANUM");
//    	hmOID.put("1.2.392.200119.6.204", "HIHOKENJYASYO_KIGOU");
//    	hmOID.put("1.2.392.200119.6.205", "HIHOKENJYASYO_NO");
//    	hmOID.put("1.2.392.200119.6.209.100012120", "JYUSHIN_SEIRI_NO");
//    	hmOID.put("1.2.392.200119.6.102", "TKIKAN_NO");
//    	return hmOID;
//    }
//
//    /*
//     *  �S�Ă̌��f���ڂ��Z�b�g
//     */
//	private static void setImportKenshinItemList(){
//		// add s.inoue 2010/08/06
//		importKenshinItem.add("9N001000000000001");//�g��
//		importKenshinItem.add("9N006000000000001");//�̏d
//		importKenshinItem.add("9N011000000000001");//�a�l�h
//		importKenshinItem.add("9N021000000000001");//�������b�ʐ�
//		importKenshinItem.add("9N016160100000001");//����(�����j
//		importKenshinItem.add("9N016160200000001");//����(���Ȕ���j
//		importKenshinItem.add("9N016160300000001");//����(���Ȑ\���j
//		importKenshinItem.add("9N026000000000002");//�얞�x
//		importKenshinItem.add("9N051000000000049");//�Ɩ���
//		importKenshinItem.add("9N056000000000011");//������
//		importKenshinItem.add("9N056160400000049");//�i��̓I�Ȋ������j
//		importKenshinItem.add("9N061000000000011");//���o�Ǐ�
//		importKenshinItem.add("9N061160800000049");//�i�����j
//		importKenshinItem.add("9N066000000000011");//���o�Ǐ�
//		importKenshinItem.add("9N066160800000049");//�i�����j
//		importKenshinItem.add("9N071000000000049");//���̑��i�Ƒ��𓙁j
//		importKenshinItem.add("9N076000000000049");//���f�i���o���܂ށj
//		importKenshinItem.add("9N081000000000049");//�Œ��f
//		importKenshinItem.add("9N086000000000049");//�G�f�i�֐߉���܂ށj
//		importKenshinItem.add("9N091000000000001");//�������t�����e�X�g
//		importKenshinItem.add("9A755000000000001");//���k�������i���̑��j
//		importKenshinItem.add("9A752000000000001");//���k�������i�Q��ځj
//		importKenshinItem.add("9A751000000000001");//���k�������i�P��ځj
//		importKenshinItem.add("9A765000000000001");//�g���������i���̑��j
//		importKenshinItem.add("9A762000000000001");//�g���������i�Q��ځj
//		importKenshinItem.add("9A761000000000001");//�g���������i�P��ځj
//		importKenshinItem.add("9N121000000000001");//�S����
//		importKenshinItem.add("9N141000000000011");//�̌����ԁi�H��j
//		importKenshinItem.add("3F050000002327101");//���R���X�e���[��
//		importKenshinItem.add("3F050000002327201");//���R���X�e���[��
//		importKenshinItem.add("3F050000002399901");//���R���X�e���[��
//		importKenshinItem.add("3F015000002327101");//�������b�i�g���O���Z���h�j
//		importKenshinItem.add("3F015000002327201");//�������b�i�g���O���Z���h�j
//		importKenshinItem.add("3F015000002399901");//�������b�i�g���O���Z���h�j
//		importKenshinItem.add("3F070000002327101");//�g�c�k�R���X�e���[��
//		importKenshinItem.add("3F070000002327201");//�g�c�k�R���X�e���[��
//		importKenshinItem.add("3F070000002399901");//�g�c�k�R���X�e���[��
//		importKenshinItem.add("3F077000002327101");//�k�c�k�R���X�e���[��
//		importKenshinItem.add("3F077000002327201");//�k�c�k�R���X�e���[��
//		importKenshinItem.add("3F077000002399901");//�k�c�k�R���X�e���[��
//		importKenshinItem.add("3J010000002327101");//���r�����r��
//		importKenshinItem.add("3J010000002399901");//���r�����r��
//		importKenshinItem.add("3B035000002327201");//GOT�i�`�r�s�j
//		importKenshinItem.add("3B035000002399901");//GOT�i�`�r�s�j
//		importKenshinItem.add("3B045000002327201");//GPT�i�`�k�s�j
//		importKenshinItem.add("3B045000002399901");//GPT�i�`�k�s�j
//		importKenshinItem.add("3B090000002327101");//��-GT(��-GTP)
//		importKenshinItem.add("3B090000002399901");//��-GT(��-GTP)
//		importKenshinItem.add("3B070000002327101");//�`�k�o
//		importKenshinItem.add("3B070000002399901");//�`�k�o
//		importKenshinItem.add("3C015000002327101");//�����N���A�`�j��
//		importKenshinItem.add("3C015000002399901");//�����N���A�`�j��
//		importKenshinItem.add("3C020000002327101");//�����A�_
//		importKenshinItem.add("3C020000002399901");//�����A�_
//		importKenshinItem.add("3A010000002327101");//���`��
//		importKenshinItem.add("3A010000002399901");//���`��
//		importKenshinItem.add("3A015000002327101");//�A���u�~��
//		importKenshinItem.add("3A015000002399901");//�A���u�~��
//		importKenshinItem.add("3A016000002327102");//�`�^�f
//		importKenshinItem.add("5C095000002302301");//�����t�F���`��
//		importKenshinItem.add("5C095000002399901");//�����t�F���`��
//		importKenshinItem.add("3D010000001926101");//�󕠎�����
//		importKenshinItem.add("3D010000002227101");//�󕠎�����
//		importKenshinItem.add("3D010000001927201");//�󕠎�����
//		importKenshinItem.add("3D010000001999901");//�󕠎�����
//		importKenshinItem.add("3D010129901926101");//��������
//		importKenshinItem.add("3D010129902227101");//��������
//		importKenshinItem.add("3D010129901927201");//��������
//		importKenshinItem.add("3D010129901999901");//��������
//		importKenshinItem.add("3D045000001906202");//�g���`�P��
//		importKenshinItem.add("3D045000001920402");//�g���`�P��
//		importKenshinItem.add("3D045000001927102");//�g���`�P��
//		importKenshinItem.add("3D045000001999902");//�g���`�P��
//		importKenshinItem.add("1A020000000191111");//�A��
//		importKenshinItem.add("1A020000000190111");//�A��
//		importKenshinItem.add("1A010000000191111");//�A�`��
//		importKenshinItem.add("1A010000000190111");//�A�`��
//		importKenshinItem.add("1A100000000191111");//�A����
//		importKenshinItem.add("1A100000000190111");//�A����
//		importKenshinItem.add("1A105160700166211");//�A���ԁi�����̗L���j
//		importKenshinItem.add("1A105160800166249");//�A���ԁi�����j
//		importKenshinItem.add("1A030000000190301");//��d
//		importKenshinItem.add("1A030000000199901");//��d
//		importKenshinItem.add("2A040000001930102");//�w�}�g�N���b�g�l
//		importKenshinItem.add("2A030000001930101");//���F�f�ʁm�w���O���r���l�n
//		importKenshinItem.add("2A020000001930101");//�Ԍ�����
//		importKenshinItem.add("2A020161001930149");//�n�������i���{���R�j
//		importKenshinItem.add("2A060000001930101");//�l�b�u
//		importKenshinItem.add("2A070000001930101");//�l�b�g
//		importKenshinItem.add("2A080000001930101");//�l�b�g�b
//		importKenshinItem.add("2A010000001930101");//��������
//		importKenshinItem.add("2A050000001930101");//������
//		importKenshinItem.add("9A110160700000011");//�S�d�}�i�����̗L���j
//		importKenshinItem.add("9A110160800000049");//�S�d�}�i�����j
//		importKenshinItem.add("9A110161000000049");//�S�d�}�i���{���R�j
//		importKenshinItem.add("9N201000000000011");//�����G�b�N�X�������i����F���ڎB�e�j
//		importKenshinItem.add("9N206160700000011");//�����G�b�N�X�������i��ʁF���ڎB�e�j�i�����̗L���j
//		importKenshinItem.add("9N206160800000049");//�����G�b�N�X�������i��ʁF���ڎB�e�j�i�����j
//		importKenshinItem.add("9N211161100000049");//�����G�b�N�X�������i���ڎB�e�j�i�B�e�N�����j
//		importKenshinItem.add("9N211161200000049");//�����G�b�N�X�������i���ڎB�e�j�i�t�B�����ԍ��j
//		importKenshinItem.add("9N216000000000011");//�����G�b�N�X�������i����F�ԐڎB�e�j
//		importKenshinItem.add("9N221160700000011");//�����G�b�N�X�������i��ʁF�ԐڎB�e�j�i�����̗L���j
//		importKenshinItem.add("9N221160800000049");//�����G�b�N�X�������i��ʁF�ԐڎB�e�j�i�����j
//		importKenshinItem.add("9N226161100000049");//�����G�b�N�X�������i�ԐڎB�e�j�i�B�e�N�����j
//		importKenshinItem.add("9N226161200000049");//�����G�b�N�X�������i�ԐڎB�e�j�i�t�B�����ԍ��j
//		importKenshinItem.add("6A010160706170411");//�\ႌ��� �i�h�������@��ʍ׋ہj�i�����̗L���j
//		importKenshinItem.add("6A010160806170449");//�\ႌ��� �i�h�������@��ʍ׋ہj�i�����j
//		importKenshinItem.add("6A205000006171711");//�\ႌ����i�h�������@�R�_�ہj
//		importKenshinItem.add("6A205165606171711");//�\ႌ����i�K�t�L�[�����j
//		importKenshinItem.add("7A010000006143311");//�\ႍזE�f����
//		importKenshinItem.add("9N251000000000011");//�����b�s�����i����j
//		importKenshinItem.add("9N251160700000011");//�����b�s�����i�����̗L���j
//		importKenshinItem.add("9N251160800000049");//�����b�s�����i�����j
//		importKenshinItem.add("9N251161100000049");//�����b�s�����i�B�e�N�����j
//		importKenshinItem.add("9N251161200000049");//�����b�s�����i�t�B�����ԍ��j
//		importKenshinItem.add("9N256160700000011");//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�����̗L���j
//		importKenshinItem.add("9N256160800000049");//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�����j
//		importKenshinItem.add("9N256161100000049");//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�B�e�N�����j
//		importKenshinItem.add("9N256161200000049");//�㕔�����ǃG�b�N�X���i���ڎB�e�j�i�t�B�����ԍ��j
//		importKenshinItem.add("9N261160700000011");//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�����̗L���j
//		importKenshinItem.add("9N261160800000049");//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�����j
//		importKenshinItem.add("9N261161100000049");//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�B�e�N�����j
//		importKenshinItem.add("9N261161200000049");//�㕔�����ǃG�b�N�X���i�ԐڎB�e�j�i�t�B�����ԍ��j
//		importKenshinItem.add("9N266160700000011");//�㕔�����Ǔ����������i�����̗L���j
//		importKenshinItem.add("9N266160800000049");//�㕔�����Ǔ����������i�����j
//		importKenshinItem.add("3B339000002399811");//�y�v�V�m�Q��
//		importKenshinItem.add("9F130160700000011");//���������g�i�����̗L���j
//		importKenshinItem.add("9F130160800000049");//���������g�i�����j
//		importKenshinItem.add("9N271160700000011");//�w�l�Ȑf�@�i�����̗L���j
//		importKenshinItem.add("9N271160800000049");//�w�l�Ȑf�@�i�����j
//		importKenshinItem.add("9N276160700000011");//���[���G�f�i�����̗L���j
//		importKenshinItem.add("9N276160800000049");//���[���G�f�i�����j
//		importKenshinItem.add("9N281160700000011");//���[�摜�f�f�i�}�����O���t�B�[�j�i�����̗L���j
//		importKenshinItem.add("9N281160800000049");//���[�摜�f�f�i�}�����O���t�B�[�j�i�����j
//		importKenshinItem.add("9F140160700000011");//���[�����g�����i�����̗L���j
//		importKenshinItem.add("9F140160800000049");//���[�����g�����i�����j
//		importKenshinItem.add("9N291160700000011");//�q�{�z�����f�i�����̗L���j
//		importKenshinItem.add("9N291160800000049");//�q�{�z�����f�i�����j
//		importKenshinItem.add("9N296160700000011");//�q�{���f�i�����̗L���j
//		importKenshinItem.add("9N296160800000049");//�q�{���f(�����j
//		importKenshinItem.add("7A021165008543311");//�q�{�򕔍זE�f (�זE�f�w�l�ȍޗ��j�i���ꕪ�ށj
//		importKenshinItem.add("7A021165108543311");//�q�{�򕔍זE�f (�זE�f�w�l�ȍޗ��j�i�x�Z�X�_���ށj
//		importKenshinItem.add("7A022000008543311");//�q�{�̕��זE�f (�זE�f�w�l�ȍޗ��j
//		importKenshinItem.add("9Z771160700000011");//�������@�\�i2���ڈȏ�j�i�����̗L���j
//		importKenshinItem.add("9Z771160800000049");//�������@�\�i2���ڈȏ�j�i�����j
//		importKenshinItem.add("9Z770160700000011");//�������@�\�i1���ځj�i�����̗L���j
//		importKenshinItem.add("9Z770160800000049");//�������@�\�i1���ځj�i�����j
//		importKenshinItem.add("1B030000001599811");//�֐���
//		importKenshinItem.add("5D305000002399811");//�o�r�`�i�O���B���ٍR���j
//		importKenshinItem.add("9C310000000000001");//�x�@�\�����i�w�͔x���ʁj
//		importKenshinItem.add("9C320000000000001");//�x�@�\�����i�P�b�ʁj
//		importKenshinItem.add("9C330000000000002");//�x�@�\�����i�P�b���j
//		importKenshinItem.add("9C380000000000002");//�x�@�\�����i���u�b�j
//		importKenshinItem.add("9E160162100000001");//���́i�E�j
//		importKenshinItem.add("9E160162500000001");//���́i�E�F�����j
//		importKenshinItem.add("9E160162200000001");//���́i���j
//		importKenshinItem.add("9E160162600000001");//���́i���F�����j
//		importKenshinItem.add("9D100163100000011");//���́i�E�A1000Hz�j
//		importKenshinItem.add("9D100163200000011");//���́i�E�A4000Hz�j
//		importKenshinItem.add("9D100163500000011");//���́i���A1000Hz�j
//		importKenshinItem.add("9D100163600000011");//���́i���A4000Hz�j
//		importKenshinItem.add("9D100164000000011");//���́i�������@�j
//		importKenshinItem.add("9D100160900000049");//���́i���̑��̏����j
//		importKenshinItem.add("9E100166000000011");//��ꌟ���i�L�[�X���O�i�[���ށj
//		importKenshinItem.add("9E100166100000011");//��ꌟ���i�V�F�C�G���ށF�g�j
//		importKenshinItem.add("9E100166200000011");//��ꌟ���i�V�F�C�G���ށF�r�j
//		importKenshinItem.add("9E100166300000011");//��ꌟ���iSCOTT����)
//		importKenshinItem.add("9E100160900000049");//��ꌟ���i���̑��̏����j
//		importKenshinItem.add("9E100161000000049");//��ꌟ���i���{���R�j
//		importKenshinItem.add("9E105162100000001");//�ሳ�����i�E�j
//		importKenshinItem.add("9E105162200000001");//�ሳ�����i���j
//		importKenshinItem.add("5C070000002306201");//�b�q�o
//		importKenshinItem.add("5C070000002306301");//�b�q�o
//		importKenshinItem.add("5C070000002399901");//�b�q�o
//		importKenshinItem.add("5H010000001910111");//���t�^�i�`�a�n�j
//		importKenshinItem.add("5H010000001999911");//���t�^�i�`�a�n�j
//		importKenshinItem.add("5H020000001910111");//���t�^�i�q���j
//		importKenshinItem.add("5H020000001999911");//���t�^�i�q���j
//		importKenshinItem.add("5E071000002399811");//�~�Ŕ���
//		importKenshinItem.add("5F016141002399811");//�g�a���R��
//		importKenshinItem.add("5F360149502399811");//�g�b�u�R��
//		importKenshinItem.add("5F360149702399811");//�g�b�u�R�́i�͉��j
//		importKenshinItem.add("5F360150002399811");//�g�b�u�R������
//		importKenshinItem.add("5F360145002399811");//�g�b�u�j�_��������
//		importKenshinItem.add("9N401000000000011");//�b�^�̉��E�C���X���f�̔���
//		importKenshinItem.add("9N406000000000049");//���̑��̖@����ꌒ�N�f�f
//		importKenshinItem.add("9N411000000000049");//���̑��̖@�茟��
//		importKenshinItem.add("9N416000000000049");//���̑��̌���
//		importKenshinItem.add("9N501000000000011");//���^�{���b�N�V���h���[������
//		importKenshinItem.add("9N506000000000011");//�ی��w�����x��
//		importKenshinItem.add("9N511000000000049");//��t�̐f�f�i����j
//		importKenshinItem.add("9N516000000000049");//���N�f�f�����{������t�̎���
//		importKenshinItem.add("9N521000000000049");//��t�̈ӌ�
//		importKenshinItem.add("9N526000000000049");//�ӌ����q�ׂ���t�̎���
//		importKenshinItem.add("9N531000000000049");//���Ȉ�t�ɂ�錒�N�f�f
//		importKenshinItem.add("9N536000000000049");//���Ȉ�t�ɂ�錒�N�f�f�����{�������Ȉ�t�̎���
//		importKenshinItem.add("9N541000000000049");//���Ȉ�t�̈ӌ�
//		importKenshinItem.add("9N546000000000049");//�ӌ����q�ׂ����Ȉ�t�̎���
//		importKenshinItem.add("9N551000000000049");//���l
//		importKenshinItem.add("9N556000000000011");//�����@�\�]���̌��ʂP
//		importKenshinItem.add("9N561000000000011");//�����@�\�]���̌��ʂQ
//		importKenshinItem.add("9N566000000000049");//�����@�\�]���̌��ʂR
//		importKenshinItem.add("9N571000000000049");//��t�̐f�f�i����j�i�����@�\�]���j
//		importKenshinItem.add("9N576000000000049");//�f�f��������t�̎����i�����@�\�]���j
//		importKenshinItem.add("9N581161300000011");//��t�̐f�f�i�x���񌟐f�j�i�R�[�h�j
//		importKenshinItem.add("9N581161400000049");//��t�̐f�f�i�x���񌟐f�j�i���R�L�ځj
//		importKenshinItem.add("9N586000000000049");//�f�f��������t�̎����i�x���񌟐f�j
//		importKenshinItem.add("9N591161300000011");//��t�̐f�f�i�݂��񌟐f�j�i�R�[�h�j
//		importKenshinItem.add("9N591161400000049");//��t�̐f�f�i�݂��񌟐f�j�i���R�L�ځj
//		importKenshinItem.add("9N596000000000049");//�f�f��������t�̎����i�݂��񌟐f�j
//		importKenshinItem.add("9N601161300000011");//��t�̐f�f�i�����񌟐f�j�i�R�[�h�j
//		importKenshinItem.add("9N601161400000049");//��t�̐f�f�i�����񌟐f�j�i���R�L�ځj
//		importKenshinItem.add("9N606000000000049");//�f�f��������t�̎����i�����񌟐f�j
//		importKenshinItem.add("9N611161300000011");//��t�̐f�f�i�q�{���񌟐f�j�i�R�[�h�j
//		importKenshinItem.add("9N611161400000049");//��t�̐f�f�i�q�{���񌟐f�j�i���R�L�ځj
//		importKenshinItem.add("9N616000000000049");//�f�f��������t�̎����i�q�{���񌟐f�j
//		importKenshinItem.add("9N621161300000011");//��t�̐f�f�i�咰���񌟐f�j�i�R�[�h�j
//		importKenshinItem.add("9N621161400000049");//��t�̐f�f�i�咰���񌟐f�j�i���R�L�ځj
//		importKenshinItem.add("9N626000000000049");//�f�f��������t�̎���
//		importKenshinItem.add("9N631161300000011");//��t�̐f�f�i�O���B���񌟐f�j�i�R�[�h�j
//		importKenshinItem.add("9N631161400000049");//��t�̐f�f�i�O���B���񌟐f�j�i���R�L�ځj
//		importKenshinItem.add("9N636000000000049");//�f�f����t�̎����i�O���B���񌟐f�j
//		importKenshinItem.add("9N641000000000049");//��t�̐f�f�i���̑��j
//		importKenshinItem.add("9N646000000000049");//�f�f��������t�̎����i���̑��j
//		importKenshinItem.add("9N701000000000011");//����P�i�����j
//		importKenshinItem.add("9N701167000000049");//�i��܁j
//		importKenshinItem.add("9N701167100000049");//�i���򗝗R�j
//		importKenshinItem.add("9N706000000000011");//����Q�i�����j
//		importKenshinItem.add("9N706167000000049");//�i��܁j
//		importKenshinItem.add("9N706167100000049");//�i���򗝗R�j
//		importKenshinItem.add("9N711000000000011");//����R�i�����j
//		importKenshinItem.add("9N711167000000049");//�i��܁j
//		importKenshinItem.add("9N711167100000049");//�i���򗝗R�j
//		importKenshinItem.add("9N716000000000011");//�������P�i�]���ǁj
//		importKenshinItem.add("9N721000000000011");//�������Q�i�S���ǁj
//		importKenshinItem.add("9N726000000000011");//�������R�i�t�s�S�E�l�H���́j
//		importKenshinItem.add("9N731000000000011");//�n��
//		importKenshinItem.add("9N736000000000011");//�i��
//		importKenshinItem.add("9N741000000000011");//�Q�O�΂���̑̏d�ω�
//		importKenshinItem.add("9N746000000000011");//�R�O���ȏ�̉^���K��
//		importKenshinItem.add("9N751000000000011");//���s���͐g�̊���
//		importKenshinItem.add("9N756000000000011");//���s���x
//		importKenshinItem.add("9N761000000000011");//1�N�Ԃ̑̏d�ω�
//		importKenshinItem.add("9N766000000000011");//�H�ו�1�i���H�����j
//		importKenshinItem.add("9N771000000000011");//�H�ו��Q�i�A�Q�O�j
//		importKenshinItem.add("9N776000000000011");//�H�ו��R�i��H/�ԐH�j
//		importKenshinItem.add("9N781000000000011");//�H�K��
//		importKenshinItem.add("9N786000000000011");//����
//		importKenshinItem.add("9N791000000000011");//�����
//		importKenshinItem.add("9N796000000000011");//����
//		importKenshinItem.add("9N801000000000011");//�����K���̉��P
//		importKenshinItem.add("9N806000000000011");//�ی��w���̊�]
//		importKenshinItem.add("9N811000000000011");//�P�D�o�X��d�Ԃ�1�l�ŊO�o���Ă��܂���
//		importKenshinItem.add("9N816000000000011");//�Q�D���p�i�̔��������Ă��܂���
//		importKenshinItem.add("9N821000000000011");//�R�D�a�����̏o����������Ă��܂���
//		importKenshinItem.add("9N826000000000011");//�S�D�F�l�̉Ƃ�K�˂Ă��܂���
//		importKenshinItem.add("9N831000000000011");//�T�D�Ƒ���F�l�̑��k�ɂ̂��Ă��܂���
//		importKenshinItem.add("9N836000000000011");//�U�D�K�i���肷���ǂ�����炸�ɏ����Ă��܂���
//		importKenshinItem.add("9N841000000000011");//�V�D�֎q�ɍ�������Ԃ��牽�����܂炸�ɗ����オ���Ă��܂���
//		importKenshinItem.add("9N846000000000011");//�W�D15���ʑ����ĕ����Ă��܂���
//		importKenshinItem.add("9N851000000000011");//�X�D����1�N�Ԃɓ]�񂾂��Ƃ�����܂���
//		importKenshinItem.add("9N856000000000011");//�P�O�D�]�|�ɑ΂���s���͑傫���ł���
//		importKenshinItem.add("9N861000000000011");//�P�P�D6�����Ԃ�2�`3kg�ȏ�̑̏d����������܂�����
//		importKenshinItem.add("9N866000000000001");//�P�Q�D�g���@�@�@�@�@�����@�@�@�̏d�@�@�@�@�@�����@�i�a�l�h���@�@�@�@�j
//		importKenshinItem.add("9N871000000000011");//�P�R�D���N�O�ɔ�ׂČł����̂��H�ׂɂ����Ȃ�܂�����
//		importKenshinItem.add("9N876000000000011");//�P�S�D������`�����łނ��邱�Ƃ�����܂���
//		importKenshinItem.add("9N881000000000011");//�P�T�D���̊������C�ɂȂ�܂���
//		importKenshinItem.add("9N886000000000011");//�P�U�D�T�ɂP��ȏ�͊O�o���Ă��܂���
//		importKenshinItem.add("9N891000000000011");//�P�V�D��N�Ɣ�ׂĊO�o�̉񐔂������Ă��܂���
//		importKenshinItem.add("9N896000000000011");//�P�W�D����̐l����u�����������𕷂��v�Ȃǂ̕��Y�ꂪ����ƌ����܂���
//		importKenshinItem.add("9N901000000000011");//�P�X�D�����œd�b�ԍ��𒲂ׂāA�d�b�������邱�Ƃ����Ă��܂���
//		importKenshinItem.add("9N906000000000011");//�Q�O�D�����������������킩��Ȃ���������܂���
//		importKenshinItem.add("9N911000000000011");//�Q�P�D�i����2�T�ԁj�����̐����ɏ[�������Ȃ�
//		importKenshinItem.add("9N916000000000011");//�Q�Q�D�i����2�T�ԁj����܂Ŋy����ł��Ă������Ƃ��y���߂Ȃ��Ȃ���
//		importKenshinItem.add("9N921000000000011");//�Q�R�D�i����2�T�ԁj�ȑO�͊y�ɂł��Ă������Ƃ����ł͂��������Ɋ�������
//		importKenshinItem.add("9N926000000000011");//�Q�S�D�i����2�T�ԁj���������ɗ��l�Ԃ��Ǝv���Ȃ�
//		importKenshinItem.add("9N931000000000011");//�Q�T�D�i����2�T�ԁj�킯���Ȃ���ꂽ�悤�Ȋ���������
//
//		// add s.inoue 2010/08/06
//		// �ǉ����f-�V�K�ǉ�����
//		// edit s.inoue 2010/08/30
//		importKenshinItem.add("1A020106000190111");//���ׂP���Ԍ�A��
//		importKenshinItem.add("1A020106000191111");//���ׂP���Ԍ�A��
//		importKenshinItem.add("1A020112000190111");//���ׂQ���Ԍ�A��
//		importKenshinItem.add("1A020112000191111");//���ׂQ���Ԍ�A��
//		importKenshinItem.add("1A035000000190101");//�A���g
//		importKenshinItem.add("1A040000000190111");//�A�E���r���m�[�Q���萫
//		importKenshinItem.add("1A055000000190111");//�A�r�����r���萫
//		importKenshinItem.add("1A060000000190111");//�A�P�g���̒萫
//		importKenshinItem.add("1A065000000190111");//�A�A�~���[�[�萫
//		importKenshinItem.add("1A105000000166251");//�A���ԁ|�Ԍ���
//		importKenshinItem.add("3A010000002327101");//���`��
//		importKenshinItem.add("3A010000002399901");//���`��
//
//		importKenshinItem.add("1A105000000166253");//�A���ԁ|���זE
//		importKenshinItem.add("1A105000000166266");//�A���ԁ|�~��
//		importKenshinItem.add("1A105000000166294");//�A���ԁ|���̑�
//		importKenshinItem.add("1B010000001570111");//��������(�h��)(��)
//		importKenshinItem.add("1B025000001570111");//���傤��������(��)
//		importKenshinItem.add("1B040Z121015Z0111");//�Ɖu�֐�������(1��
//		importKenshinItem.add("1B040Z122015Z0111");//�Ɖu�֐�������(2��
//		importKenshinItem.add("2A090000001930101");//�D�_����
//		importKenshinItem.add("2A110000001963202");//�ԐԌ�����
//		importKenshinItem.add("2A160000001960351");//���t���|�D����(Neu
//		importKenshinItem.add("2A160000001960352");//���t���|�D��������
//		importKenshinItem.add("2A160000001960353");//���t���|�D�������t
//		importKenshinItem.add("2A160000001960354");//���t���|�D�_��(Eos
//		importKenshinItem.add("2A160000001960355");//���t���|�D���(B
//		importKenshinItem.add("2A160000001960356");//���t���|�P��(Mono)
//		importKenshinItem.add("2A160000001960357");//���t���|�����p��(L
//		importKenshinItem.add("2A160000001960377");//���t���|���̑�
//		importKenshinItem.add("2B010000001732001");//�o������
//		importKenshinItem.add("2B020000002231151");//�����������g�����{
//		importKenshinItem.add("2B020000002231157");//�����������g�����{
//		importKenshinItem.add("2B030000002231151");//�v���g�����r������
//		importKenshinItem.add("2B030000002231157");//�v���g�����r������
//		importKenshinItem.add("2Z010000001992001");//����1���Ԓl
//		importKenshinItem.add("3A015000000106101");//�A���A���u�~��
//		importKenshinItem.add("3A025000002329201");//TTT
//		importKenshinItem.add("3A030000002329201");//ZTT
//		importKenshinItem.add("3B010000002327101");//CK(CPK)
//		importKenshinItem.add("3B010000002327201");//CK(CPK)
//		importKenshinItem.add("3B010000002399801");//CK(CPK)
//		importKenshinItem.add("3B050000002327101");//LDH
//		importKenshinItem.add("3B050000002327201");//LDH
//		importKenshinItem.add("3B050000002399801");//LDH
//		importKenshinItem.add("3B110000002327101");//�R�����G�X�e���[�[
//		importKenshinItem.add("3B110000002327201");//�R�����G�X�e���[�[
//		importKenshinItem.add("3B110000002399801");//�R�����G�X�e���[�[
//		importKenshinItem.add("3B135000002327101");//LAP
//		importKenshinItem.add("3B135000002399801");//LAP
//		importKenshinItem.add("3B160000000127101");//�A�A�~���[�[���
//		importKenshinItem.add("3B160000002327101");//�����A�~���[�[
//		importKenshinItem.add("3B160000002399801");//�����A�~���[�[
//		importKenshinItem.add("3B180000002327101");//�������p�[�[
//		importKenshinItem.add("3B180000002327201");//�������p�[�[
//		importKenshinItem.add("3B180000002399801");//�������p�[�[
//		importKenshinItem.add("3B220000002327101");//���_���t�H�X�t�@�^
//		importKenshinItem.add("3B220000002327201");//���_���t�H�X�t�@�^
//		importKenshinItem.add("3B220000002388801");//���_���t�H�X�t�@�^
//		importKenshinItem.add("3B340000002399801");//�y�v�V�m�[�Q���P��
//		importKenshinItem.add("3B345000002399801");//�y�v�V�m�[�Q���Q��
//		importKenshinItem.add("3C025000002327101");//BUN(�A�f���f)
//		importKenshinItem.add("3C025000002327201");//BUN(�A�f���f)
//		importKenshinItem.add("3C025000002399801");//BUN(�A�f���f)
//		importKenshinItem.add("3D010100001926101");//���בO�����l
//		importKenshinItem.add("3D010100001927201");//���בO�����l
//		importKenshinItem.add("3D010100001999901");//���בO�����l
//		importKenshinItem.add("3D010100002227101");//���בO�����l
//		importKenshinItem.add("3D010106001926101");//����1���Ԍ㌌���l
//		importKenshinItem.add("3D010106001927201");//����1���Ԍ㌌���l
//		importKenshinItem.add("3D010106001999901");//����1���Ԍ㌌���l
//		importKenshinItem.add("3D010106002227101");//����1���Ԍ㌌���l
//		importKenshinItem.add("3D010112001926101");//����2���Ԍ㌌���l
//		importKenshinItem.add("3D010112001927201");//����2���Ԍ㌌���l
//		importKenshinItem.add("3D010112001999901");//����2���Ԍ㌌���l
//		importKenshinItem.add("3D010112002227101");//����2���Ԍ㌌���l
//		importKenshinItem.add("3D055000002320402");//�O���R�A���u�~��
//		importKenshinItem.add("3F035000002327101");//�V�����b�_
//		importKenshinItem.add("3F035000002399801");//�V�����b�_
//		importKenshinItem.add("3F065000002327101");//�V���^�R���X�e���[
//		importKenshinItem.add("3F065000002327201");//�V���^�R���X�e���[
//		importKenshinItem.add("3F065000002399801");//�V���^�R���X�e���[
//		importKenshinItem.add("3F110000002327101");//�_�`�_
//		importKenshinItem.add("3F110000002399801");//�_�`�_
//		importKenshinItem.add("3F130000002306101");//�x�[�^���|�`��
//		importKenshinItem.add("3F130000002329201");//�x�[�^���|�`��
//		importKenshinItem.add("3F130000002399801");//�x�[�^���|�`��
//		importKenshinItem.add("3F180000002399801");//�A�|�`��A1
//		importKenshinItem.add("3F185000002399801");//�A�|�`��A2
//		importKenshinItem.add("3F190000002399801");//�A�|�`��B
//		importKenshinItem.add("3H010000002326101");//�����i�g���E��
//		importKenshinItem.add("3H015000002326101");//�����J���E��
//		importKenshinItem.add("3H020000002326101");//�����N���[��
//		importKenshinItem.add("3H030000002327101");//�������J���V�E��
//		importKenshinItem.add("3H040000002327101");//�������@����
//		importKenshinItem.add("3H040000002327201");//�������@����
//		importKenshinItem.add("3I010000002327101");//�����S
//		importKenshinItem.add("3J015000002327101");//���ڃr�����r��
//		importKenshinItem.add("4A025000002299801");//ACTH���
//		importKenshinItem.add("4A055000002399801");//TSH���
//		importKenshinItem.add("4B010000002399801");//T3���
//		importKenshinItem.add("4B015000002399801");//FT3���
//		importKenshinItem.add("4B035000002399801");//FT4���
//		importKenshinItem.add("5D010000002399801");//CEA���
//		importKenshinItem.add("5D015000002302311");//AFP����
//		importKenshinItem.add("5D015000002399801");//AFP���
//		importKenshinItem.add("5D100000002399801");//CA125���
//		importKenshinItem.add("5D120000002399801");//CA15-3���
//		importKenshinItem.add("5D130000002399801");//CA19-9���
//		importKenshinItem.add("5E035000002306101");//ASO���
//		importKenshinItem.add("5E035000002315305");//ASO(��ߔ{��)
//		importKenshinItem.add("5E065000000102311");//�A���w���R�o�N�^�[
//		importKenshinItem.add("5E065000002302311");//�����w���R�o�N�^�[
//		importKenshinItem.add("5G160000002311611");//���E�}�g�C�h���q��
//		importKenshinItem.add("6Z100000009920311");//�w���R�o�N�^�[�s��
//		importKenshinItem.add("6Z200000007190111");//�w���R�o�N�^�[�s��
//		importKenshinItem.add("9A150160700000011");//�z���^�[�^�S�d�}��
//		importKenshinItem.add("9A150160800000049");//�z���^�[�^�S�d�}��
//		importKenshinItem.add("9A300160700000011");//�}�X�^�[2�i�K���׎�
//		importKenshinItem.add("9A300160800000049");//�}�X�^�[2�i�K���׎�
//		importKenshinItem.add("9A320160700000011");//�g���b�h�~�����אS
//		importKenshinItem.add("9A320160800000049");//�g���b�h�~�����אS
//		importKenshinItem.add("9A340160700000011");//�G���S���[�^�[����
//		importKenshinItem.add("9A340160800000049");//�G���S���[�^�[����
//		importKenshinItem.add("9A350160700000011");//�S�x�^�����׎���(��
//		importKenshinItem.add("9A350160800000049");//�S�x�^�����׎���(��
//		importKenshinItem.add("9I200160700000011");//��������������(����
//		importKenshinItem.add("9I200160800000049");//��������������(����
//		importKenshinItem.add("9Z5060000Z9125001");//�������(DPA�@)
//		importKenshinItem.add("9Z5060000Z9125002");//�������(DPA�@)��Y
//		importKenshinItem.add("9Z5060000Z9125049");//�������(DPA�@)
//		importKenshinItem.add("9Z5060000Z91250Z2");//�������(DPA�@)�Γ�
//		importKenshinItem.add("9Z5070000Z9225001");//�������(DXA/DEX�@
//		importKenshinItem.add("9Z5070000Z9225002");//�������(DXA/DEX�@
//		importKenshinItem.add("9Z5070000Z9225049");//�������(DXA/DEX�@
//		importKenshinItem.add("9Z5070000Z92250Z2");//�������(DXA/DEX�@
//		importKenshinItem.add("9Z5110000Z9399101");//�������(MD�@)
//		importKenshinItem.add("9Z5110000Z9399102");//�������(MD�@)��YA
//		importKenshinItem.add("9Z5110000Z9399149");//�������(MD�@)����
//		importKenshinItem.add("9Z5110000Z93991Z2");//�������(MD�@)�Γ�
//		importKenshinItem.add("9Z5120000Z9499101");//�������(CXD�@)
//		importKenshinItem.add("9Z5120000Z9499102");//�������(CXD�@)��Y
//		importKenshinItem.add("9Z5120000Z9499149");//�������(CXD�@)����
//		importKenshinItem.add("9Z5120000Z94991Z2");//�������(CXD�@)�Γ�
//		importKenshinItem.add("9Z5130000Z9599101");//�������(DIP�@)
//		importKenshinItem.add("9Z5130000Z9599102");//�������(DIP�@)��Y
//		importKenshinItem.add("9Z5130000Z9599149");//�������(DIP�@)����
//		importKenshinItem.add("9Z5130000Z95991Z2");//�������(DIP�@)�Γ�
//		importKenshinItem.add("9Z5210000Z9625001");//�������(QCT/pQCT�@
//		importKenshinItem.add("9Z5210000Z9625002");//�������(QCT/pQCT�@
//		importKenshinItem.add("9Z5210000Z9625049");//�������(QCT/pQCT�@
//		importKenshinItem.add("9Z5210000Z96250Z2");//�������(QCT/pQCT�@
//		importKenshinItem.add("9Z5260000Z9725001");//�������(US�@)
//		importKenshinItem.add("9Z5260000Z9725002");//�������(US�@)��YA
//		importKenshinItem.add("9Z5260000Z9725049");//�������(US�@)����
//		importKenshinItem.add("9Z5260000Z97250Z2");//�������(US�@)�Γ�
//		importKenshinItem.add("Z5D01000002305101");//�T�C�g�P���`��19�t
//		importKenshinItem.add("Z9A43Z010Z9725001");//���g�`�d���xbaPWV
//		importKenshinItem.add("Z9A43Z010Z9725049");//���g�`�d���xbaPWV(
//		importKenshinItem.add("Z9A43Z010Z97250ZA");//���g�`�d���xbaPWV��
//		importKenshinItem.add("Z9A43Z012Z97250ZI");//���g�`�d���x(�S����
//		importKenshinItem.add("Z9A75Z009Z9725002");//���֐߁E��r������
//		importKenshinItem.add("Z9F120000Z9725049");//�z�����G�R�[����
//		importKenshinItem.add("Z9N06000000000001");//�W���̏d
//		importKenshinItem.add("Z9N21000000000011");//����CT����(����)
//		importKenshinItem.add("Z9N21160700000011");//����CT����(�����̗L
//		importKenshinItem.add("Z9N21160800000049");//����CT����(����)
//		importKenshinItem.add("Z9N21161100000049");//����CT����(�B�e�N��
//		importKenshinItem.add("Z9N21161200000049");//����CT����(�t�B����
//		importKenshinItem.add("Z9N22000000000011");//����MRI����(����)
//		importKenshinItem.add("Z9N22160700000011");//����MRI����(������
//		importKenshinItem.add("Z9N22160800000049");//����MRI����(����)
//		importKenshinItem.add("Z9N22161100000049");//����MRI����(�B�e�N
//		importKenshinItem.add("Z9N26000000000011");//����CT����(����)
//		importKenshinItem.add("Z9N26160700000011");//����CT����(�����̗L
//		importKenshinItem.add("Z9N26160800000049");//����CT����(����)
//		importKenshinItem.add("Z9N26161100000049");//����CT����(�B�e�N��
//		importKenshinItem.add("Z9N26161200000049");//����CT����(�t�B����
//		importKenshinItem.add("Z9N29000000000011");//FDG-PET����(����)
//		importKenshinItem.add("Z9N29160700000011");//FDG-PET����(������
//		importKenshinItem.add("Z9N29160800000049");//FDG-PET����(����)
//		importKenshinItem.add("Z9N29161100000049");//FDG-PET����(�B�e�N
//		importKenshinItem.add("ZG001000000000011");//�ݕ�X�������E�w����
//		importKenshinItem.add("ZG002000000000011");//�ݓ����������E�w��
//		importKenshinItem.add("ZG003000000000011");//���������g�����E�w
//		importKenshinItem.add("ZG004000000000011");//�Ɖu�֐�����������
//		importKenshinItem.add("ZG005000000000011");//���������E�f�w����
//		importKenshinItem.add("ZG006000000000011");//���[�����E�w���敪
//		importKenshinItem.add("ZG007000000000011");//HBs�R�������E�w����
//		importKenshinItem.add("ZG008000000000011");//�����w���敪
//		importKenshinItem.add("ZG009000000000011");//���������E�w���敪
//		importKenshinItem.add("ZG010000000000011");//�̋@�\�����E���w��
//		importKenshinItem.add("ZG011000000000011");//���������E�w���敪
//		importKenshinItem.add("ZG012000000000011");//�A�_�����E�w���敪
//		importKenshinItem.add("ZG013000000000011");//�A��ʁE�t�@�\�w��
//		importKenshinItem.add("ZGG01000000000011");//���������w���敪
//		importKenshinItem.add("ZGGA1000000000049");//�w�������ӎ���
//		importKenshinItem.add("ZI200160700000011");//�咰����������(����
//		importKenshinItem.add("ZI200160800000049");//�咰����������(����
//		importKenshinItem.add("ZI201160700000011");//S�󌋒�����������(
//		importKenshinItem.add("ZI201160800000049");//S�󌋒�����������(
//		importKenshinItem.add("ZI210160700000011");//�������e����(������
//		importKenshinItem.add("ZI210160800000049");//�������e����(����)
//		importKenshinItem.add("ZI210161100000049");//�������e����(�B�e�N
//		importKenshinItem.add("ZI210161200000049");//�������e����(�t�B��
//		importKenshinItem.add("ZI220160700000011");//�����f(�����̗L��)
//		importKenshinItem.add("ZI220160800000049");//�����f(����)
//		importKenshinItem.add("ZN001000000000011");//�A�������鎞�ԑ�
//	}
//////hl7�O������/////////////////////////////////////////////
//	/*-------------------------------------����-----------------------------------------------*/
//
//}
//
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

