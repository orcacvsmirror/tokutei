package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.filechooser.FileFilter;
// add s.inoue 20081001
import javax.swing.ButtonGroup;
import javax.swing.JProgressBar;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JEraseSpace;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessageDialogFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportErrorResultFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportErrorResultFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportResultFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JOriginalFormatData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectKojinFrame;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectKojinFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JSelectKojinFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintDefine;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;

import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KihonKensaKoumoku;


import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
// edit s.inoue 2010/02/10
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameData;

/**
 * �O�����f���ʃf�[�^��荞�݂̃t���[���̃R���g���[��
 */
public class JImportDataFrameCtrl extends JImportDataFrame
{
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream Reader = null;
	private static Vector<Vector<String>> CSVItems = null;
	private TKensakekaTokutei kensakekaTokutei = new TKensakekaTokutei();
	private TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
	private TKensakekaTokuteiDao kensakekaTokuteiDao = null;
	private TKensakekaSonotaDao kensakekaSonotaDao = null;

	private String ResultMessage = "";
	private Long preuketukeId = 0L;
	private Integer prekensaDate = 0;
	private static final String FLAME_TITLE_MESSAGE = "���������E���ꐶ�N�����̎�f�҂����݂��܂��B";
	private String BMIFormat = "##.0";

	/* �t�H�[�J�X�ړ����� */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private ButtonGroup radioButtonGroup = new ButtonGroup();

	// logger�� object�����B
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JImportDataFrameCtrl.class);

	JImportDataFrameData validateDate = new JImportDataFrameData();

	public JImportDataFrameCtrl()
	{
		// add s.inoue 2009/10/14
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
// del s.inoue 2010/04/02
//		this.focusTraversalPolicy.setDefaultComponent(jRadio_SelectDokuji);
//		this.focusTraversalPolicy.addComponent(jRadio_SelectDokuji);
//		this.focusTraversalPolicy.addComponent(jRadio_SelectNitii);
		this.focusTraversalPolicy.setDefaultComponent(jButton_OpenFile);
		this.focusTraversalPolicy.addComponent(jButton_OpenFile);
		this.focusTraversalPolicy.addComponent(jButton_Import);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		//���f�Z���^�[���̃R���{�{�b�N�X�̏����ݒ�
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem;
		String Query = new String(	"SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME " +
									"FROM T_KENSACENTER_MASTER " +
									"ORDER BY KENSA_CENTER_CD ASC");
		try{
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		}catch(SQLException e){
			e.printStackTrace();
			JErrorMessage.show("M3320",this);
		}

		for(int i = 0;i < Result.size();i++ )
		{
			ResultItem = Result.get(i);
			jComboBox_KensaCenterName.addItem(ResultItem.get("CENTER_NAME"));
		}
		// del s.inoue 2010/04/02
//		radioButtonGroup.add(jRadio_SelectDokuji);
//		radioButtonGroup.add(jRadio_SelectNitii);
//		jRadio_SelectDokuji.setSelected(true);
	}

	public boolean validateAsImportPushed(JImportDataFrameData data)
	{
		if(validateDate.getFilePath().equals(""))
		{
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3301",this);
			return false;
		}
		validateDate.setValidateAsDataSet();
		return true;
	}

	/* Add 2008/07/23 ��� */
	/* --------------------------------------------------- */
	public boolean validateImportJusinSeiriNo(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		// �G���[�̏ꍇ�A��ʂ��Z�b�g
		if (!data.jusinSeiriNo.toString().equals("")){
			// ���l�`�F�b�N
			//if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
			//	data.errCase=JApplication.ERR_KIND_NUMBER;
			//	errFlg=true;
			//}else

			// �K�{�`�F�b�N
			if (data.jusinSeiriNo.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// ���p�`�F�b�N
			if (JValidate.isAllHankaku(data.jusinSeiriNo.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// �����`�F�b�N
			if (JValidate.checkMaxLimit(data.jusinSeiriNo.toString(),11) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}
		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_JUSIN_SEIRI_NO;

		return errFlg;
	}

	public boolean validateImportJisiKikanNo(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try {
			// �K�{�`�F�b�N
			if (data.jisiKikanNo.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// ���p�`�F�b�N
			if (JValidate.isAllHankaku(data.jisiKikanNo.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// �����`�F�b�N
			if (JValidate.checkMaxLimit(data.jisiKikanNo.toString(),10) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}else
			// ��v�`�F�b�N
			if (!JApplication.kikanNumber.equals(data.jisiKikanNo.toString())){
				data.errCase=JApplication.ERR_KIND_NOT_EXIST;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_JISI_KIKAN_NO;

		return errFlg;
	}

	public boolean validateImportJisiDate(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			String temp = JCalendarConvert.JCtoAD(data.jishiDate);
			// ���p�`�F�b�N
			if (JValidate.isAllHankaku(data.jishiDate.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// ���{���Ó����`�F�b�N
			if( temp == null )
			{
				data.errCase=JApplication.ERR_KIND_DATE;
				errFlg = true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_JISI_DATE;

		return errFlg;
	}

	public boolean validateImportkanaShimei(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			// �K�{�`�F�b�N
			if (data.kanaShimei.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else

			// ���p�`�F�b�N
			if ( !JValidate.isAllHankaku(data.kanaShimei,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else

			// �����`�F�b�N
			if (JValidate.checkMaxLimit(data.kanaShimei,17) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SHIMEI;

		return errFlg;
	}

	// add s.inoue 2010/02/03
	public boolean validateImportNitiiFormatkanaShimei(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			// �K�{�`�F�b�N
			if (data.kanaShimei.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else

			// ���p�`�F�b�N
			if ( !JValidate.isAllZenkaku(data.kanaShimei)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else

			// �����`�F�b�N
			if (JValidate.checkMaxLimit(data.kanaShimei,40) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SHIMEI;

		return errFlg;
	}

	public boolean validateImportseiNenGapi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		String temp ="";

		try{
			// ���N�������t�Ó����`�F�b�N
			if (data.seiNenGapi.toString().equals("") &&
					data.jusinSeiriNo.toString().equals("")){
					// keyNotExist
					data.errCase=JApplication.ERR_KIND_DATE;
					errFlg = true;
			// edit ver2 s.inoue 2009/06/24
			}else if(!data.seiNenGapi.toString().equals("")){

				temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
				if( temp == null ){
					data.errCase=JApplication.ERR_KIND_DATE;
					errFlg = true;
				}

				// �ĕϊ�����(�a�����)
				if (!JValidate.checkByte(data.seiNenGapi,8) &&
					!JValidate.isNumber(data.seiNenGapi.toString())){

					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
					if( temp == null ){
						data.errCase=JApplication.ERR_KIND_DATE;
						errFlg = true;
					}
				}

				// ���l�`�F�b�N
				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
					data.errCase=JApplication.ERR_KIND_NUMBER;
					errFlg=true;
				}
				// ���p�`�F�b�N
				if ( !JValidate.isAllHankaku(data.seiNenGapi,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEINENGAPI;
		return errFlg;
	}

	// add s.inoue 2010/02/03
	public boolean validateImportNitiiFormatseiNenGapi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		String temp ="";

		try{
			// ���N�������t�Ó����`�F�b�N
			// �K�{�`�F�b�N
			if (data.seiNenGapi.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			// edit s.inoue 2010/02/03
			}else{
				temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
				if( temp == null ){
					data.errCase=JApplication.ERR_KIND_DATE;
					errFlg = true;
				}

				// �ĕϊ�����(�a�����)
				if (!JValidate.checkByte(data.seiNenGapi,8) &&
					!JValidate.isNumber(data.seiNenGapi.toString())){

					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
					if( temp == null ){
						data.errCase=JApplication.ERR_KIND_DATE;
						errFlg = true;
					}
				}

				// ���l�`�F�b�N
				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
					data.errCase=JApplication.ERR_KIND_NUMBER;
					errFlg=true;
				}
				// ���p�`�F�b�N
				if ( !JValidate.isAllHankaku(data.seiNenGapi,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEINENGAPI;
		return errFlg;
	}

	public boolean validateImportSex(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// ���ʃ`�F�b�N
		try{
			if (!data.seiBetu.toString().equals("")){
				// ���p�`�F�b�N
				if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}else if( !JValidate.validateSexFlag(data.seiBetu) )
				{
					// ���ʑÓ���
					data.errCase=JApplication.ERR_KIND_SEX;
					errFlg = true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEIBETU;
		return errFlg;
	}

	// add s.inoue 2010/02/03
	public boolean validateImportNitiiFormatSex(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// ���ʃ`�F�b�N
		try{
			// ���N�������t�Ó����`�F�b�N
			// �K�{�`�F�b�N
			if (data.seiBetu.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;

			// ���p�`�F�b�N
			}else if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else if( !JValidate.validateSexFlag(data.seiBetu) )
			{
				// ���ʑÓ���
				data.errCase=JApplication.ERR_KIND_SEX;
				errFlg = true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEIBETU;
		return errFlg;
	}

	public boolean validateImportNyuBi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// ���r/�n���`�F�b�N
		try{
			if (!data.nyuBi.toString().equals("")){
				// �����`�F�b�N
				if (JValidate.checkMaxLimit(data.nyuBi,10) == false){
					data.errCase=JApplication.ERR_KIND_DIGIT;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_YOUKETU;
		return errFlg;
	}

	public boolean validateImportShokuZenGo(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// �H�O/�H��`�F�b�N
		try{
			if (!data.shokuZenGo.toString().equals("")){
				// �����`�F�b�N
				if (JValidate.checkMaxLimit(data.shokuZenGo,10) == false){
					data.errCase=JApplication.ERR_KIND_DIGIT;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SHOKUZENGO;
		return errFlg;
	}


	/*** �ڍ׏�� ***/
	public boolean validateKensaKoumokuCode(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// �������ڃR�[�h�`�F�b�N
		try{
			// �K�{�`�F�b�N
			if (data.koumokuCd.equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// ���p�`�F�b�N
			if ( !JValidate.isAllHankaku(data.koumokuCd,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// �����`�F�b�N 17���݂̂ɕύX s.inoue 20081114
			//if (JValidate.checkMaxLimit(data.koumokuCd,17) == false){
			if (JValidate.checkByte(data.koumokuCd,17) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.KENSA_FIELD_KOUMOKU_CD;
		return errFlg;
	}

	public boolean validateImportJisiKbn(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		// ���{�敪�`�F�b�N
		try{
			String temp = JValidate.validateJisiKubun(data.jisiKbn);

			// add s.inoue 20081118(�󕠎�����,�g���`�P��)
			//boolean kensaJisi = checkTokuteiKensaJisi(data);

			// �K�{�`�F�b�N
			if (data.jisiKbn.toString().equals("")){
					data.errCase=JApplication.ERR_KIND_EMPTY;
					errFlg=true;
			}
			// ���p�`�F�b�N
			else if ( !JValidate.isAllHankaku(data.jisiKbn,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			// �l�`�F�b�N
			}
			else if( temp == null )
			{
				data.errCase=JApplication.ERR_KIND_NOT_EXIST;
				errFlg = true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.KENSA_FIELD_JISI_KBN;
		return errFlg;
	}

	private boolean checkTokuteiKensaJisi(JImportErrorResultFrameData data)
	{
		boolean hantei = false;
		// �󕠎�����,�g���`�P���̏ꍇ
		if (KihonKensaKoumoku.KUFUKU_CODES.contains(data.koumokuCd) ||
				KihonKensaKoumoku.HBA1C_CODES.contains(data.koumokuCd)){
			// ���ʒl����
			if (data.kekkaTi.equals("")){
				hantei=true;
			}
		}

		return hantei;
	}
//	 del 20080918 s.inoue
//	public boolean validateImportHLKbn(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// �ُ�l�敪�`�F�b�N
//		try{
//			if (!data.ijyoKbn.toString().equals("")){
//				// �l�`�F�b�N
//				String temp = JValidate.validateHLKubun(data.ijyoKbn);
//
//				// ���p�`�F�b�N
//				if ( !JValidate.isAllHankaku(data.ijyoKbn,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}
//				else if( temp == null )
//				{
//					data.errCase=JApplication.ERR_KIND_NOT_EXIST;
//					errFlg = true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.KENSA_FIELD_IJYO_KBN;
//
//		return errFlg;
//	}

// del 20080918 s.inoue
//	public boolean validateImportKekkaTiKeitai(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// ���ʒl�`�ԃ`�F�b�N
//		try{
//			if (!data.kekkaTiKeitai.toString().equals("")){
//				String temp = JValidate.validateKekkaTiKeitai(data.kekkaTiKeitai);
//
//				// ���p�`�F�b�N
//				if ( !JValidate.isAllHankaku(data.kekkaTiKeitai,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}
//				else if( temp == null )
//				{
//					data.errCase=JApplication.ERR_KIND_NOT_EXIST;
//					errFlg = true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.KENSA_FIELD_KEKATI_KEITAI;
//
//		return errFlg;
//	}

	public boolean validateImportKekkaTi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			// ���ʒl�`�F�b�N
			// �K�{�`�F�b�N
// del s.inoue 20080916
//			if (data.kekkaTi.equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else

			// edit s.inoue 20080916
			// ���{�敪��1�̎�
			if (data.jisiKbn.equals("1"))
			{
				// ���p�`�F�b�N
				if ( !JValidate.isAllHankaku(data.kekkaTi,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}else
				// �����`�F�b�N
				if (JValidate.checkMaxLimit(data.kekkaTi,14) == false){
					data.errCase=JApplication.ERR_KIND_DIGIT;
					errFlg=true;
				}

				// add 20080916 s.inoue
				// ���ʒl�Ȃ� �����{
				if (data.kekkaTi.equals("")){
					data.jisiKbn = "0";
				}
			}

			// add 20080916 s.inoue
			// ���ʒl���� �����{���͑���s�\
			else if( !data.kekkaTi.equals("") &&
					(data.jisiKbn.equals("0") ||
					(data.jisiKbn.equals("2"))))
			{
				data.errCase=JApplication.ERR_KIND_DATA;
				errFlg = true;
			}

		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.KENSA_FIELD_KEKATI;

		return errFlg;
	}

	// CSV�t�@�C��format�`�F�b�N
	// flg|true:���� false:����
	private boolean inputCsvFileCheck(String FilePath,boolean flg){
		boolean retCheck = false;

		// 1.txt�`�����ǂ���
		if (!FilePath.endsWith(".txt") &&
				!FilePath.endsWith(".TXT") &&
					!FilePath.endsWith(".csv") &&
						!FilePath.endsWith(".CSV"))
			retCheck = true;

		Vector<String> insertRow = new Vector<String>();

		// �������ʃf�[�^�捞����
		if (flg){
			for (int i = 0; i < CSVItems.size(); i++) {
				insertRow =CSVItems.get(i);
				int csvCnt = insertRow.size() - 1;

				// 2.������� ��13�ȏ゠�邩
				if (csvCnt <= 13)
					retCheck = true;

				// 3.������� - �������ڏ��8�Ŋ���؂��
				csvCnt = csvCnt - JApplication.CSV_ZOKUSEI_COUNT;
				int csvKensa = csvCnt % 8;
				if (csvKensa != 0)
					retCheck = true;
			}
		}
		return retCheck;
	}

	/*--------------------------------------nitii-----------------------------------------------
	/**
	 * ����t�H�[�}�b�g�f�[�^�̎�荞��
	 * @param
	 */
	public void nitiImport(
			JImportErrorResultFrameData Data,
			ArrayList<Hashtable<String, String>> kojinData,
			ArrayList<Hashtable<String, String>> selectKojinData,
			Vector<JImportErrorResultFrameData> ErrorResultData)
	{

		// �o�^�����ϐ�
		int intregistCnt = 0;
		boolean retblnALL = false;
		ResultMessage="";

		// �������ʃf�[�^�捞����
		for (int i = 0; i < CSVItems.size(); i++) {

			Vector<String> insertRow = new Vector<String>();

			insertRow =CSVItems.get(i);

			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "��");

			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JUSIN_SEIRI_NO));
			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SHIMEI));
			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SEIBETU));
			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISIKIKAN_NO));

			// ����������
			Data.uketukeNo =null;
			kojinData = null;
			preuketukeId = 0L;
			prekensaDate =0;

			// ���ʎ捞�f�[�^(�L�[���)�`�F�b�N����
			if (checkKensaKekkaKeyNitiiFormatData(Data,ErrorResultData,i,false)){
				i++;continue;
			}

			// �l���ʃf�[�^:(�����ԍ�,���f���P�ʂŎ擾)
			try {
				if (!Data.jusinSeiriNo.toString().equals(""))
					kojinData = getKojinData(Data);

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
					logger.error("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
					break;
				}

			// �Y������f�[�^���Ȃ��ꍇ�A�l(����,���N����,����)�Ō���
			if (kojinData == null ||
					kojinData.size() == 0){
				try{

					selectKojinData = getSimeiData(Data);

					if (selectKojinData == null ||
							selectKojinData.size() == 0){
						break;
					}

					// �Y������l�����������ꍇ�A���X�g��\������
					Hashtable<String, String>selectedKojinData
						= searchKojinData(selectKojinData,Data,false);

					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);

					kojinData = getSelectKojinData(Data);
					if (kojinData == null ||
							kojinData.size() == 0){
						break;
					}

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
					logger.error("[����t�H�[�}�b�g]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
					break;
				}
			}

			// add s.inoue 2010/02/10
			// �z�񂩂�\���̂֕ϊ��i Arrays.asList�j
			HashMap<String, String> csvKoumokuCD = this.registerKoumokuCd(validateDate);

			// ���ʎ捞CSV�t�@�C����DB�̒l�̔�r���s���B
			// ���̑��e�[�u���̍��ڃR�[�h�P�ʂŏ������s���B
			for (int j = 0; j < kojinData.size(); j++) {

					Hashtable<String,String> DatabaseItem = kojinData.get(j);

					// del s.inoue 2010/02/17
					// if (Data.uketukeNo == null){
					Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					//}

					// ��tID���r����i��v�F�o�^�ΏہA�s��v�F�G���[�Ώہj
					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);

// del s.inoue 2010/02/17 1���݂̂̑Ώۂɂ��폜
					// �f�[�^�x�[�X�ɓo�^(T_KENSAKEKKA_TOKUTEI)
					// �L�[���(:��tID,�������{��)���ς������o�^�������s��
//					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
//							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
						try{

							// ���ʎ捞�f�[�^(�������)�`�F�b�N����
							if (checkKensaKekkaData(Data,ErrorResultData,i)){
								j++;continue;
							}

							// ���Ɍ��f���ʃf�[�^�����݂����ꍇ�A�x�����b�Z�[�W��\������B
							// ���f���{��,�����J�i�� ���ʓo�^����ƃf�[�^�����݂���ׁA�X�V�����̂�
							// edit s.inoue 2010/02/10
							String strCsvKoumokuOrder = (String) csvKoumokuCD.get(koumokuCdDB);
							if (strCsvKoumokuOrder == null){
								j++;continue;
							}
							System.out.println("csv:" + strCsvKoumokuOrder);
							// ���ڃR�[�h��CSV���Ԃ�n���Ď擾
							// edit s.inoue 2010/02/17
							// Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
							Data.koumokuCd = koumokuCdDB;
							Data.kekkaTi = Reader.rmQuart(insertRow.get(Integer.parseInt(strCsvKoumokuOrder)));

							// ���ڃR�[�h�`�F�b�N
							if (checkKoumokuCD(Data,ErrorResultData,i)){
								j++;continue;
							}

							boolean existsKensaKekkaData = existsKensaKekkaData(Data);

							if (existsKensaKekkaData) {

								if (!retblnALL){
									// M3328=�m�F,[%s]�ɂ́A���łɌ��ʃf�[�^�����݂��܂��B�o�^�����ꍇ�A
									// �ȑO�ɓ��͂������ʃf�[�^�͏����Ă��܂��܂��B�o�^���Ă�낵���ł����H
									String[] messageParams = {"��t�ԍ�:"+ Data.uketukeNo + "/" + "���f���{��:" + Data.jishiDate + "/" + "����:"+ Data.kanaShimei};
									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
									// cancel��
									if (retValue == RETURN_VALUE.CANCEL) {
										try {
										JApplication.kikanDatabase.rollback();
										}catch (SQLException err) {}
										return;
									}else if (retValue == RETURN_VALUE.NO){
										preuketukeId=Long.valueOf(uketukeIdDB);
										prekensaDate=Integer.valueOf(kensaDateDB);
										j++;continue;
									// add s.inoue 2010/02/17
									}else if (retValue == RETURN_VALUE.YES){
										updatekensakekaSonota(Data,insertRow);
									}else if (retValue == RETURN_VALUE.ALL){
										retblnALL = true;
									}
								}
							}else{
								updatekensakekaSonota(Data,insertRow);
							}
							intregistCnt++;

							// add s.inoue 2010/03/16
							// BMI�����v�Z roop�Ō�ɏ���
							String height = "";
							String weight = "";
							if(Data.koumokuCd.equals("9N001000000000001")){
								height = Data.kekkaTi;
							}else if (Data.koumokuCd.equals("9N006000000000001")){
								weight = Data.kekkaTi;
							}
							if (j == kojinData.size()-1){
								String bmi = calcBMI(height, weight);
								if (bmi.equals(""))break;

								String kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
								Data.koumokuCd = "9N011000000000001"; //BMI
								Data.kekkaTi = kensaKekka;
								updatekensakekaSonota(Data,insertRow);
							}

						}catch(Exception ex){
							try {
								JApplication.kikanDatabase.rollback();
							} catch (SQLException err) {}
							ResultMessage += GetErrorMessage("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B",Data.kanaShimei);

							logger.error("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B");
							break;
						}
					}

// del s.inoue 2010/02/03
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
// del s.inoue 2010/02/17
//			}
		}
	}

	// sonota�e�[�u���X�V
	private void updatekensakekaSonota(JImportErrorResultFrameData Data,Vector<String> insertRow){
		// Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN ));
		Data.jisiKbn ="1"; // �t�B�[���h�Ȃ��Œ�
		// Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI));
		try {
			kensakekaSonotaregister(Data);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// add s.inoue 2010/02/10
	// ���ڃR�[�h�o�^
	private HashMap<String, String> registerKoumokuCd(JImportDataFrameData data){

		HashMap<String, String> hm = new HashMap<String, String>();
		List arrKoumokuCD = new ArrayList<String>();
		List arrKoumokuOrder = new ArrayList<String>();

		arrKoumokuCD = Arrays.asList(data.koumokuCD_Situmonhyo);
		arrKoumokuOrder = Arrays.asList(data.koumokuCD_Situmonhyo_Order);

		for (int i = 0; i < arrKoumokuCD.size(); i++) {
			hm.put((String)arrKoumokuCD.get(i), (String)arrKoumokuOrder.get(i));
		}

		return hm;
	}

	// add s.inoue 2010/01/26
	/**
	 * ��荞�ݑO����
	 * @param FilePath �ǂݍ��ރt�@�C���̃p�X
	 */
	private void nitiImportCheck(String FilePath)
	{
		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		ArrayList<Hashtable<String, String>> kojinData=null;
		ArrayList<Hashtable<String, String>> selectKojinData=null;

		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();

		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();

		// CSV�Ǎ�����
		Reader = new JCSVReaderStream();

		try {

			Reader.openCSV(FilePath,JApplication.CSV_CHARSET);

			CSVItems = Reader.readAllTable();

			if (inputCsvFileCheck(FilePath,false)){
				JErrorMessage.show("M3327",this);
				return;
			}

		} catch (Exception e) {
			logger.error("[����t�H�[�}�b�g�捞����]CSV�Ǎ������Ɏ��s���܂����B");
			JErrorMessage.show("M3329",this);
			return;
		}

		// �@��(����,���̑�)�ڑ�����
		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();

			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
					connection,
					kensakekaTokutei);

			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
					connection,
					kensakekaSonota);

			JApplication.kikanDatabase.Transaction();

		} catch (Exception e) {
			logger.error("[�������ʎ捞����]�@��FDB�ւ̐ڑ������Ɏ��s���܂����B");
			JErrorMessage.show("M3326",this);
			return;
		}

		// �捞��
		nitiImport(Data,kojinData,selectKojinData,ErrorResultData);

		// add s.inoue 2010/02/17
		try {
			JApplication.kikanDatabase.Commit();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	/*--------------------------------------nitii-----------------------------------------------*/

	/*-------------------------------------orgine-----------------------------------------------*/
	/**
	 * �f�[�^�̎�荞��
	 * @param FilePath �ǂݍ��ރt�@�C���̃p�X
	 */
	public void kekkaImport(String FilePath)
	{

		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		ArrayList<Hashtable<String, String>> kojinData=null;
		ArrayList<Hashtable<String, String>> selectKojinData=null;

		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();

		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();
		// �o�^�����ϐ�
		int intregistCnt = 0;
		boolean retblnALL = false;
		ResultMessage="";

		// CSV�Ǎ�����
		Reader = new JCSVReaderStream();

		try {

			Reader.openCSV(FilePath,JApplication.CSV_CHARSET);

			CSVItems = Reader.readAllTable();

			// add s.inoue 20081001
			if (inputCsvFileCheck(FilePath,true)){
				JErrorMessage.show("M3327",this);
				return;
			}

		} catch (Exception e) {
			logger.error("[�������ʎ捞����]CSV�Ǎ������Ɏ��s���܂����B");
			JErrorMessage.show("M3326",this);
			return;
		}

		// �@��(����,���̑�)�ڑ�����
		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();

			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
					connection,
					kensakekaTokutei);

			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
					connection,
					kensakekaSonota);

			JApplication.kikanDatabase.Transaction();

		} catch (Exception e) {
			logger.error("[�������ʎ捞����]�@��FDB�ւ̐ڑ������Ɏ��s���܂����B");
			JErrorMessage.show("M3326",this);
			return;
		}

		// �������ʃf�[�^�捞����
		for (int i = 0; i < CSVItems.size(); i++) {

			Vector<String> insertRow = new Vector<String>();

			insertRow =CSVItems.get(i);

			// add s.inoue 20081001
			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "��");

			// �������擾 CSV�f�[�^�����[�J���ϐ��ɃZ�b�g(�u"�v��������������)
			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JUSIN_SEIRI_NO));
			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISI_DATE));
			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHIMEI));
			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEINENGAPI));
			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEIBETU));
			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISIKIKAN_NO));
			Data.nyuBi =Reader.rmQuart((insertRow.get(JApplication.CSV_ZOKUSEI_NYUBI_YOUKETU)));
			Data.shokuZenGo =Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHOKUZEN_SHOKUGO));

			// ����������
			Data.uketukeNo =null;
			kojinData = null;
			preuketukeId = 0L;
			prekensaDate =0;

			// ���ʎ捞�f�[�^(�L�[���)�`�F�b�N����
			if (checkKensaKekkaKeyData(Data,ErrorResultData,i,false)){
				i++;continue;
			}

			// �l���ʃf�[�^:(�����ԍ�,���f���P�ʂŎ擾)
			try {
				if (!Data.jusinSeiriNo.toString().equals(""))
					kojinData = getKojinData(Data);

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
					logger.error("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
					break;
				}

			// �Y������f�[�^���Ȃ��ꍇ�A�l(����,���N����,����)�Ō���
			if (kojinData == null ||
					kojinData.size() == 0){
				try{

					selectKojinData = getSimeiData(Data);

					if (selectKojinData == null ||
							selectKojinData.size() == 0){
						break;
					}

					// �Y������l�����������ꍇ�A���X�g��\������
					// edit s.inoue 2010/02/03
					Hashtable<String, String>selectedKojinData
						= searchKojinData(selectKojinData,Data,true);

					// del s.inoue 20081003
					//if (selectedKojinData == null) {
					//	Data.errCase = JApplication.ERR_KIND_NOT_EXIST;
					//	setImportErrDigit(ErrorResultData,Data,i);
					//	ResultMessage += GetErrorMessage("[�������ʎ捞����]�Y���҂����܂���ł����B�����𒆒f���܂��B","�������ʃf�[�^:" + Data.kanaShimei);
					//	break;
					//}
					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);

					kojinData = getSelectKojinData(Data);
					if (kojinData == null ||
							kojinData.size() == 0){
						break;
					}

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B",Data.kanaShimei);
					logger.error("[�������ʎ捞����]�l���f�[�^���擾���鎞�ɃG���[���������܂����B");
					break;
				}
			}

			// ���ʎ捞CSV�t�@�C����DB�̒l�̔�r���s���B
			// ���̑��e�[�u���̍��ڃR�[�h�P�ʂŏ������s���B
			// add s.inoue 2010/04/21
			String height = "";
			String weight = "";
			for (int j = 0; j < kojinData.size(); j++) {

					Hashtable<String,String> DatabaseItem = kojinData.get(j);

					if (Data.uketukeNo == null){
						Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					}
					// ��tID���r����i��v�F�o�^�ΏہA�s��v�F�G���[�Ώہj
					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);

					// �f�[�^�x�[�X�ɓo�^(T_KENSAKEKKA_TOKUTEI)
					// �L�[���(:��tID,�������{��)���ς������o�^�������s��
					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
						try{

							// ���ʎ捞�f�[�^(�������)�`�F�b�N����
							if (checkKensaKekkaData(Data,ErrorResultData,i)){
								j++;continue;
							}

							// ���Ɍ��f���ʃf�[�^�����݂����ꍇ�A�x�����b�Z�[�W��\������B
							// ���f���{��,�����J�i�� ���ʓo�^����ƃf�[�^�����݂���ׁA�X�V�����̂�
							Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
							// ���ڃR�[�h�`�F�b�N
							if (checkKoumokuCD(Data,ErrorResultData,i)){
								j++;continue;
							}

							boolean existsKensaKekkaData = existsKensaKekkaData(Data);

							if (existsKensaKekkaData) {

								if (!retblnALL){
									// edit ver2 s.inoue 2009/05/22
									// M3328=�m�F,[%s]�ɂ́A���łɌ��ʃf�[�^�����݂��܂��B�o�^�����ꍇ�A
									// �ȑO�ɓ��͂������ʃf�[�^�͏����Ă��܂��܂��B�o�^���Ă�낵���ł����H
									String[] messageParams = {"��t�ԍ�:"+ Data.uketukeNo + "/" + "���f���{��:" + Data.jishiDate + "/" + "����:"+ Data.kanaShimei};
									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
									// cancel��
									if (retValue == RETURN_VALUE.CANCEL) {
										try {
										JApplication.kikanDatabase.rollback();
										}catch (SQLException err) {}
										return;
									}else if (retValue == RETURN_VALUE.NO){
										preuketukeId=Long.valueOf(uketukeIdDB);
										prekensaDate=Integer.valueOf(kensaDateDB);
										j++;continue;
									}else if (retValue == RETURN_VALUE.ALL){
										retblnALL = true;
									}
								}
							}
							intregistCnt++;

							kensakekaTokuteiregister(Data);
							preuketukeId=Long.valueOf(uketukeIdDB);
							prekensaDate=Integer.valueOf(kensaDateDB);

						}catch(Exception ex){
							try {
								JApplication.kikanDatabase.rollback();
							} catch (SQLException err) {}
							ResultMessage += GetErrorMessage("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B",Data.kanaShimei);

							logger.error("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B");
							break;
						}
					}

					// �L�[��񂪈�v�����猟�����ʓo�^���Ă��萳��
					// �u���̑�:1�`22�̍���(���I�ɕω�����)�v�̓o�^
					Integer kensaKoumokuLoop = JApplication.CSV_KENSA_KOUMOKU_LOOP;
					int cntKoumoku =0;

					while (cntKoumoku < JApplication.CSV_KENSA_LOOP * JApplication.CSV_KENSA_KOUMOKU_LOOP) {

						if (insertRow.size() <= JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku )
						{
							cntKoumoku += kensaKoumokuLoop;break;
						}
						Data.koumokuCd = Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku));

						if (Data.koumokuCd.equals("")){
							checkKoumokuCD(Data,ErrorResultData,i);
							cntKoumoku += kensaKoumokuLoop;continue;
						}

						try {
							Long resultCnt = kensakekaSonotaDao.selectByCountPrimaryKey(
									Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
							if (resultCnt < 0) {
								checkKoumokuCD(Data,ErrorResultData,i);
								cntKoumoku += kensaKoumokuLoop;
								continue;
							}

						} catch (Exception e) {
							logger.error("[�������ʎ捞����]�������ʃf�[�^���̑��f�[�^�擾���A�G���[�ƂȂ�܂����B");
							break;
						}

						if(JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(JEraseSpace.EraceSpace(uketukeIdDB)) &&
							JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(JEraseSpace.EraceSpace(kensaDateDB)) &&
							 JEraseSpace.EraceSpace(Data.koumokuCd.toString()).equals(JEraseSpace.EraceSpace(koumokuCdDB)))
						{
							Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN + cntKoumoku));
							Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI + cntKoumoku));
							// del 20080918 s.inoue
							//Data.ijyoKbn=Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_IJYO_KBN + cntKoumoku));
							//Data.kekkaTiKeitai =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI_KEITAI + cntKoumoku));

							// ���ʎ捞�f�[�^(�������ڏ��)
							if (checkKensaKekkaDataDetail(Data,ErrorResultData,i)){
								cntKoumoku += kensaKoumokuLoop;break;
							}

							// ���f���ʂ��̑��o�^����
							try{
								kensakekaSonotaregister(Data);

								// add s.inoue 2010/04/21
								// BMI�����v�Z roop�Ō�ɏ���
								if(Data.koumokuCd.equals("9N001000000000001")){
									height = Data.kekkaTi;
								}else if (Data.koumokuCd.equals("9N006000000000001")){
									weight = Data.kekkaTi;
								}
							}catch(Exception ex){
								try {
									JApplication.kikanDatabase.rollback();

								} catch (SQLException err) {
								}
								logger.error("[�������ʎ捞����]�������ڏ��o�^���A�G���[�ƂȂ�܂����B");
								ResultMessage += GetErrorMessage("[�������ʎ捞����]�������ʃf�[�^����e�[�u���ɓo�^���A�G���[���������܂����B",Data.kanaShimei);
								break;
							}
						}

						cntKoumoku += kensaKoumokuLoop;
					}
			}

			// add s.inoue 2010/04/21
			String bmi = calcBMI(height, weight);
			if (bmi.equals(""))break;

			String kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
			Data.koumokuCd = "9N011000000000001"; //BMI
			Data.kekkaTi = kensaKekka;
			updatekensakekaSonota(Data,insertRow);

		}

		try {
			// commit
			JApplication.kikanDatabase.Commit();

		} catch (SQLException err) {
			logger.error("[�������ʎ捞����]�������ڏ��o�^���A�G���[�ƂȂ�܂����B" + JApplication.LINE_SEPARATOR + err.getMessage());
		}

		// �A���}�b�`�G���[�ꗗ�\��
		if(ErrorResultData.size() >= 1){
			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
					this,new JImportErrorResultFrameCtrl(ErrorResultData,this));

		}else if(intregistCnt == 0){
			JErrorMessage.show("M3325",this);

		// �������f�̃G���[���b�Z�[�W��\��
		}else if(ResultMessage.equals("")){
			JErrorMessage.show("M3324",this);

		// �������f�̃G���[���b�Z�[�W��\��
		}else{
			try
			{
				BufferedWriter file = new BufferedWriter(new FileWriter("Error.txt"));
				file.write(ResultMessage);
				file.close();

			}catch(Exception err){
				logger.error("[�������ʎ捞����]�������ڏ��o�^���A�G���[�ƂȂ�܂����B" + JApplication.LINE_SEPARATOR + err.getMessage());
				JErrorMessage.show("M3323",this);
			}
			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
					this,new JImportResultFrameCtrl(ResultMessage,this));
		}

		// add s.inoue 20081001
		this.m_guiStatus.setText("");

	}
	/*-------------------------------------orgine-----------------------------------------------*/

	/**
	 * �����ԍ��Ō����ł��Ȃ��ꍇ�̍i���ݏ���
	 * convertFlg:���Ȏ����𔼊p���S�p true,�S�p�����p false
	 */
	private Hashtable<String,String> searchKojinData(
			ArrayList<Hashtable<String,String>> KojinDatabaseData,
			JImportErrorResultFrameData Data,
			boolean convertFlg){

		// �Y������l������
		Hashtable<String,String> TargetItem = null;

		Vector<JSelectKojinFrameData> SameKojinData = new Vector<JSelectKojinFrameData>();

		for(int j = 0 ; j < KojinDatabaseData.size() ; j++)
		{
			Hashtable<String,String> DatabaseItem = KojinDatabaseData.get(j);

			// edit s.inoue 2010/02/03
			//���p�J�^�J�i�A�󔒂�����������ԂŔ�r
			String Name = "";
			if (convertFlg){
				Name = JZenkakuKatakanaToHankakuKatakana.Convert(DatabaseItem.get("KANANAME"));
			}else{
				Name = DatabaseItem.get("KANANAME");
			}

			if(JEraseSpace.EraceSpace(Name).equals(JEraseSpace.EraceSpace(Data.kanaShimei)))
			{
				JSelectKojinFrameData DataSame = new JSelectKojinFrameData();

				DataSame.jusinSeiNo = DatabaseItem.get("JYUSHIN_SEIRI_NO");
				DataSame.name = DatabaseItem.get("NAME");
				DataSame.kanaShimei = DatabaseItem.get("KANANAME");
				DataSame.seiNenGapi = DatabaseItem.get("BIRTHDAY");
				DataSame.seiBetu = DatabaseItem.get("SEX");
				// edit ver2 s.inoue 2009/08/19
				DataSame.jyusho= DatabaseItem.get("HOME_ADRS");
				DataSame.hihokenshaKigo = DatabaseItem.get("HIHOKENJYASYO_KIGOU");
				DataSame.hihokenshaNo = DatabaseItem.get("HIHOKENJYASYO_NO");
				DataSame.DatabaseItem = DatabaseItem;

				SameKojinData.add(DataSame);
				TargetItem = DatabaseItem;
			}
		}

		//���̒i�K��null�������Ă�����Y������l�����Ȃ��̂ŁA�G���[���o���Ȃǂ̏������s���B
		//if(TargetItem == null)
		//{
		//	ResultMessage += GetErrorMessage("�Y�������f�҂����܂���B",shimeiKana);
		//}

		//���������E���ꐶ�N�����̎�f�҂����������ꍇ�̏���
		if(SameKojinData.size() > 1)
		{
			//ResultMessage += GetErrorMessage("���������E���ꐶ�N�����̎�f�҂����܂��B�I���_�C�A���O���o���܂��B",shimeiKana);

//			JSelectKojinFrameData SelectedData =  new JSelectKojinFrameData();
//			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JSelectKojinFrameCtrl(SameKojinData,this));
//			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this);
//			SelectedData =SelectKojinFrame.GetSelectedData();

			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this,FLAME_TITLE_MESSAGE);
			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();

			if(SelectedData == null)
			{
				ResultMessage += GetErrorMessage("��f�҂��I������܂���ł����B�������X�L�b�v���܂��B",Data.kanaShimei);
				TargetItem=null;
			}else{
				TargetItem = SelectedData.DatabaseItem;
			}
		}
		return TargetItem;
	}
	/* --------------------------------------------------- */

	/* ADD 2008/07/23 ��� */
	/* --------------------------------------------------- */
	/**
	 * �G���[��ʐݒ菈��
	 */
	private void setImportErrDigit(
			//Hashtable<String,String> DatabaseItem,
			Vector<JImportErrorResultFrameData> ErrorResultData,
			JImportErrorResultFrameData Data,
			Integer i){

		// add ver2 s.inoue 2009/07/27
		// DB����
		// Data.rowNo =i + 1;
		// ErrorResultData.add(Data);

		JImportErrorResultFrameData DataKeys = new JImportErrorResultFrameData();

		DataKeys.errCase = Data.errCase;
		DataKeys.errField = Data.errField;
		DataKeys.jusinSeiriNo =Data.jusinSeiriNo;
		DataKeys.jishiDate = Data.jishiDate;
		DataKeys.kanaShimei = Data.kanaShimei;
		DataKeys.seiNenGapi = Data.seiNenGapi;
		DataKeys.seiBetu = Data.seiBetu;
		DataKeys.jisiKikanNo = Data.jisiKikanNo;
		DataKeys.nyuBi = Data.nyuBi;
		DataKeys.shokuZenGo = Data.shokuZenGo;

		DataKeys.koumokuCd = Data.koumokuCd;
		DataKeys.jisiKbn = Data.jisiKbn;
		DataKeys.kekkaTi = Data.kekkaTi;
		DataKeys.rowNo =i + 1;

		// DB����
		//Data.rowNo =i + 1;
		ErrorResultData.add(DataKeys);
	}

	/**
	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{)
	 */
	private boolean checkKensaKekkaKeyData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i,
				boolean flg)
	{
		boolean checkKensaKekkaFlg=false;

		if (!Data.jusinSeiriNo.toString().equals("")){
			// ��f�������ԍ�
			if (validateImportJusinSeiriNo(Data))
			{
				setImportErrDigit(ErrorResultData,Data,i);
				checkKensaKekkaFlg =true;

			}
		}
		// �J�i������
		if (validateImportkanaShimei(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// ���N����
		if (validateImportseiNenGapi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// ����
		if (validateImportSex(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// ����/�n��
		if (validateImportNyuBi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// �H�O/�H��
		if (validateImportShokuZenGo(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// ���f���{�N������
		if (validateImportJisiDate(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;
	}

	/**
	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{�A������t�H�[�}�b�g�p�K�{)
	 */
	private boolean checkKensaKekkaKeyNitiiFormatData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i,
				boolean flg)
	{
		boolean checkKensaKekkaFlg=false;

		if (!Data.jusinSeiriNo.toString().equals("")){
			// ��f�������ԍ�
			if (validateImportJusinSeiriNo(Data))
			{
				setImportErrDigit(ErrorResultData,Data,i);
				checkKensaKekkaFlg =true;

			}
		}
		// �J�i������
		if (validateImportNitiiFormatkanaShimei(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// ���N������
		if (validateImportNitiiFormatseiNenGapi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// ���ʁ�
		if (validateImportNitiiFormatSex(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// ���f���{�N������
		if (validateImportJisiDate(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;

	}

	/**
	 * �������ʃf�[�^�`�F�b�N����(�������)��format����
	 */
	private boolean checkKensaKekkaData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i) {

		boolean checkKensaKekkaFlg=false;

		// ���f���{�@�֔ԍ�
		if (validateImportJisiKikanNo(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;
	}

	/**
	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{)
	 */
	private boolean checkKoumokuCD(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i)
	{
		boolean checkKensaKekkaFlg=false;
		// ���ڃR�[�h��
		if (validateKensaKoumokuCode(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}
		return checkKensaKekkaFlg;
	}


	/**
	 * �������ʃf�[�^�`�F�b�N����(�������ڏ�񁝕K�{)
	 */
	private boolean checkKensaKekkaDataDetail(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i)
	{
		boolean checkKensaKekkaFlg=false;

		// ���{�敪��
		if (validateImportJisiKbn(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}

// del 20080918 s.inoue
//		// �ُ�l�敪
//		if (validateImportHLKbn(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
//		// ���ʒl�`��
//		if (validateImportKekkaTiKeitai(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
		// �������ʁ�
		if (validateImportKekkaTi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}
		return checkKensaKekkaFlg;
	}
	/* --------------------------------------------------- */

	/* EDIT 2008/07/23 ��� */
	/* --------------------------------------------------- */
	/**
	 * �������ʓ���o�^
	 * @throws SQLException
	 */
	private void kensakekaTokuteiregister(JImportErrorResultFrameData Data)
		throws SQLException
	{

		kensakekaTokutei = new TKensakekaTokutei();
		// ����
		kensakekaTokutei.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
		kensakekaTokutei.setKENSA_NENGAPI(Integer.valueOf(Data.jishiDate));
		kensakekaTokutei.setNYUBI_YOUKETU(Data.nyuBi);
		kensakekaTokutei.setSYOKUZEN_SYOKUGO(Data.shokuZenGo);
		// pending s.inoue
		//if (mode == INSERT_MODE) {
		//	kensakekaTokutei.setHIHOKENJYASYO_KIGOU("");
		//	kensakekaTokutei.setHIHOKENJYASYO_NO("");
		//	kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumber));
		//	kensakekaTokutei.setHANTEI_NENGAPI(null);
		//	kensakekaTokutei.setTUTI_NENGAPI(null);
		//	kensakekaTokutei.setKENSA_CENTER_CD("");
			// ���̂���UPDATE���s�����߈ꎞ�I�Ƀf�[�^������
		//	kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(1));
		//	kensakekaTokutei.setSEIKYU_KBN(new Integer(1));
		//	kensakekaTokutei.setKOMENTO("");
		//	kensakekaTokutei.setNENDO(null);
		//	kensakekaTokuteiDao.insert(kensakekaTokutei);

		//}else if (mode == UPDATE_MODE) {
		// s.inoue 20080924
		kensakekaTokuteiDao.update(kensakekaTokutei,false);
		//}

		/*String query = "UPDATE T_KENSAKEKA_TOKUTEI SET "
			+ "NYUBI_YOUKETU = "
			+ JQueryConvert.queryConvertAppendComma(this.nyubiYoketu)

			+ "SYOKUZEN_SYOKUGO = "
			+ JQueryConvert.queryConvert(this.shokuzenShokugo)

			//+ "KENSA_CENTER_CD = "
				//+ JQueryConvert.queryConvertAppendComma("")
			//+ "K_P_NO = "
				//+ JQueryConvert.queryConvertAppendComma("")
			//+ "KOMENTO = "
				//+ JQueryConvert.queryConvertAppendComma("")
			//+ "SEIKYU_KBN = "
				//+ JQueryConvert.queryConvert("")
			+ " WHERE "
			+ "UKETUKE_ID = "
				+ JQueryConvert.queryConvert(this.uketukeId.toString())
			+ " AND "
			+ "	KENSA_NENGAPI = "
				+ JQueryConvert.queryConvert(this.kensaDate.toString());
			JApplication.kikanDatabase.sendNoResultQuery(query);*/
	}
	/* --------------------------------------------------- */

	/* EDIT 2008/07/23 ��� */
	/* --------------------------------------------------- */
	/**
	 * �������ʓo�^
	 * @throws SQLException
	 */
	private void kensakekaSonotaregister(JImportErrorResultFrameData Data)
		throws SQLException
	{
			// move4. �������ʓo�^����
			// ad s.inoue �f�[�^�x�[�X�ɓo�^(T_KENSAKEKKA_SONOTA)
//			2008/3/19 ���R �C��
//			T_KOJIN,T_KENSAKEKA_TOKUTEI��UKETUKE_ID,NENDO�ǉ��Ή�
//			-------------------------------------------------------------------------------------------------------
			TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
			// ed s.inoue
			// Map<String, String> recMap = KojinDatabaseData.get(i);
			// ed s.inoue kensakekaSonota.setUKETUKE_ID(new Long(recMap.get("UKETUKE_ID")));
			// kensakekaSonota.setHIHOKENJYASYO_KIGOU(TargetItem.get("HIHOKENJYASYO_KIGOU"));
			// kensakekaSonota.setHIHOKENJYASYO_NO(TargetItem.get("HIHOKENJYASYO_NO"));
			// kensakekaSonota.setKENSA_NENGAPI(new Integer(KensaDate));
			// kensakekaSonota.setKOUMOKU_CD(KoumokuCode);
			// kensakekaSonota.setKEKA_TI(Body.Kekka);

			kensakekaSonota.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
			kensakekaSonota.setKENSA_NENGAPI(Integer.valueOf(Data.jishiDate));
			kensakekaSonota.setKOUMOKU_CD(Data.koumokuCd);
			// notnull�̃t�B�[���h�l
			kensakekaSonota.setHIHOKENJYASYO_KIGOU("");
			kensakekaSonota.setHIHOKENJYASYO_NO("");

			kensakekaSonota.setJISI_KBN(Integer.valueOf(Data.jisiKbn));
			// del 20080918 s.inoue
			//kensakekaSonota.setH_L(Integer.valueOf(JValidate.validateHLKubun(Data.ijyoKbn)));
			//kensakekaSonota.setKEKA_TI_KEITAI(Integer.valueOf(JValidate.validateKekkaTiKeitai(Data.kekkaTiKeitai)));

			kensakekaSonota.setKEKA_TI(Data.kekkaTi);

			// pending s.inoue
			// if (mode == INSERT_MODE) {
			//	kensakekaSonotaDao.insert(kensakekaSonota);
			// }else if (mode == UPDATE_MODE) {
			kensakekaSonotaDao.update(kensakekaSonota);
			// }

			// del s.inoue
			//try {
			//	TKensakekaSonotaDao kensakekaSonotaDao =
			//	(TKensakekaSonotaDao)DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), kensakekaSonota);
			//	kensakekaSonotaDao.insert(kensakekaSonota);
			//} catch (Exception e) {
			//	e.printStackTrace();
			//	ed s.inoue ImportItem��shimeiKana
			//	ResultMessage += GetErrorMessage("�f�[�^�x�[�X�ւ̓o�^�Ɏ��s���܂����B����̃f�[�^�����݂���\��������܂��B",shimeiKana);
			//	 del s.inoue
			//	 continue;
			//}
//		 -------------------------------------------------------------------------------------------------------
//						 del s.inoue }

	}
	/* --------------------------------------------------- */

	/**
	 * �������ʗL������
	 */
	private boolean existsKensaKekkaData(JImportErrorResultFrameData Data) {

		TKensakekaSonota resultList = null;

		try {
			resultList = kensakekaSonotaDao.selectByPrimaryKey(
					Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean exsistsKensaKekkaData = false;
		if (resultList != null) {

			if (resultList.getKEKA_TI() !=null)
			{
				if (!resultList.getKEKA_TI().equals("")){
					exsistsKensaKekkaData = true;
				}
			}
		}

		return exsistsKensaKekkaData;
	}

	/**
	 * �l�f�[�^�擾(����:�����A�N��A����)
	 */
	private static ArrayList<Hashtable<String, String>>
		getSimeiData(JImportErrorResultFrameData Data) throws Exception{

		ArrayList<Hashtable<String, String>> kenshinData = null;

		try{

			String[] params = new String[] { JCalendarConvert.JCtoAD(Data.seiNenGapi),
					Data.seiBetu,
					};

			kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
				getSimeiDataSQL(), params);

			} catch (Exception err) {
				throw err;
			}
		return kenshinData;
	}

	/* Edit 2008/07/23 ��� */
	/* --------------------------------------------------- */
	/**
	 * �l�f�[�^�擾
	 */
	private static ArrayList<Hashtable<String, String>>
		getKojinData(JImportErrorResultFrameData Data) throws Exception{

		ArrayList<Hashtable<String, String>> kenshinData = null;
		JConnection kikanDatabase = null;

		try{

				// omschange s.inoue
				//String[] params = new String[] { Data.uketukeNo,
				String[] params = new String[] {
						Data.jusinSeiriNo.toString(),
						Data.jishiDate,
						Data.jishiDate,
						};

				kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
					getKensaDataSQL(false), params);
				if (kenshinData == null ||
						kenshinData.size() <= 0) {
					return null;
				}
			} catch (Exception err) {
				throw err;
			}
		return kenshinData;
	}
	/* --------------------------------------------------- */

	/**
	 * �l�f�[�^�擾(��t�ԍ�)
	 */
	private static ArrayList<Hashtable<String, String>>
		getSelectKojinData(JImportErrorResultFrameData Data) throws Exception{

		ArrayList<Hashtable<String, String>> kenshinData = null;

		try{
				String[] params = new String[] {
						Data.uketukeNo,
						Data.jishiDate,
						Data.jishiDate,
						};

				kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
					getKensaDataSQL(true), params);
				if (kenshinData == null ||
						kenshinData.size() <= 0) {
					return null;
				}
			} catch (Exception err) {
				throw err;
			}
		return kenshinData;
	}

	// add s.inoue 2010/03/16
	/**
	 * BMI�l���v�Z����
	 */
	public String calcBMI(String height, String weight) {
		// �ǂ��炩����l�̏ꍇ�͋�l��Ԃ�
		if (height.isEmpty() || weight.isEmpty()) {
			return "";
		}

		// �O���Z�G���[��h��
		if (Double.valueOf(height) == 0) {
			return "";
		}

		BigDecimal bmi = new BigDecimal(
				String
						.valueOf((Double.valueOf(weight) / ((Double
								.valueOf(height) / 100) * (Double
								.valueOf(height) / 100)))));
		return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

	/**
	 * �������ʂ��̑��擾(����)
	 */
	private static String getFromSQL()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(" T_KENSAKEKA_TOKUTEI AS TOKUTEI");
		buffer.append(" LEFT JOIN T_KENSAKEKA_SONOTA AS SONOTA ");
		buffer.append(" ON");
		buffer.append(" (");
		buffer.append("  TOKUTEI.UKETUKE_ID = SONOTA.UKETUKE_ID");
		buffer.append(" )");
		buffer.append(" LEFT JOIN T_KOJIN AS KOJIN ");
		buffer.append(" ON ");
		buffer.append(" ( ");
		buffer.append(" SONOTA.UKETUKE_ID = KOJIN.UKETUKE_ID ");
		buffer.append(" ) ");
		// add s.inoue 20081002
//		buffer.append(" LEFT JOIN T_KENSHINMASTER AS MASTER ");
//		buffer.append(" ON ");
//		buffer.append(" ( ");
//		buffer.append(" KOJIN.HKNJANUM = MASTER.HKNJANUM ");
//		buffer.append(" ) ");
		return buffer.toString();
	}

	/**
	 * �������ʂ��̑��擾
	 * flgKeys true:��t�ԍ�, false:�����ԍ�
	 */
	private static String getKensaDataSQL(boolean flgKeys)
	{
		// add 20081002 s.inoue SONOTA��MASTER.KOUMOKU_CD�֕ύX
		StringBuffer buffer = new StringBuffer();
		//buffer.append(" SELECT DISTINCT KOJIN.UKETUKE_ID,TOKUTEI.KENSA_NENGAPI,MASTER.KOUMOKU_CD ");
		buffer.append(" SELECT KOJIN.UKETUKE_ID,TOKUTEI.KENSA_NENGAPI,SONOTA.KOUMOKU_CD ");
		buffer.append(" FROM ");
		buffer.append(getFromSQL());

		buffer.append(" WHERE");

		if (flgKeys){
			buffer.append(" TOKUTEI.UKETUKE_ID = ? ");
		}else{
			buffer.append(" KOJIN.JYUSHIN_SEIRI_NO = ? ");
		}

		buffer.append(" AND");
		buffer.append(" TOKUTEI.KENSA_NENGAPI = ? ");
		buffer.append(" AND");
		buffer.append(" SONOTA.KENSA_NENGAPI = ? ");
		String sql = buffer.toString();
		return sql;
	}

	/**
	 * �������ʂ��̑��擾(������)
	 */
	private static String getSimeiDataSQL()
	{

		StringBuffer buffer = new StringBuffer();
		// edit ver2 s.inoue 2009/08/19
		buffer.append("SELECT ");
		buffer.append(" PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME,");
		buffer.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL,");
		buffer.append(" KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU,");
		buffer.append(" MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO,");
		buffer.append(" UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");

		buffer.append(" FROM T_KOJIN ");
		buffer.append(" WHERE ");
		buffer.append(" BIRTHDAY = ? ");
		buffer.append(" AND SEX = ? ");

		return buffer.toString();

	}

	/**
	 * ���s�R�[�h
	 */
	private String LineSeparator = System.getProperty("line.separator");

	/**
	 * �G���[���b�Z�[�W�̌`���Ń��b�Z�[�W���擾����B
	 * @param Message ���b�Z�[�W
	 * @param Data �ǂݍ��ݒ��̃f�[�^
	 * @return �t�H�[�}�b�g�ς݂̃e�L�X�g
	 */
	private String GetErrorMessage(String Message,JOriginalFormatData Data)
	{
		return "(" + Data.Name + ")" + Message + LineSeparator;
	}

	/**
	 * �G���[���b�Z�[�W�̌`���Ń��b�Z�[�W���擾����B
	 * @param Message ���b�Z�[�W
	 * @param Name ���O
	 * @return �t�H�[�}�b�g�ς݂̃e�L�X�g
	 */
	private String GetErrorMessage(String Message,String Name)
	{
		return "(" + Name + ")" + Message + LineSeparator;
	}

	/**
	 * �C���|�[�g�{�^��
	 */
	public void pushedImportButton(ActionEvent e)
	{
		if( validateDate.setFilePath(jTextField_FileName.getText()))
		{
			if( validateAsImportPushed(validateDate) )
			{
				if( validateDate.isValidateAsDataSet )
				{
// edit s.inoue 2010/04/02
//					if(jRadio_SelectDokuji.isSelected()){
						// ���ʎ�荞��
						kekkaImport(validateDate.getFilePath());
// edit s.inoue 2010/04/02
//					}else{
//						// ����t�H�[�}�b�g
//						nitiImportCheck(validateDate.getFilePath());
//					}
				}
			}
		}
	}

	/**
	 * �I���{�^��
	 */
	public void pushedEndButton(ActionEvent e)
	{
		dispose();
	}

	class KnsaFileFilter extends FileFilter
	{
		public boolean accept(File f)
		{
			if(f.getName().equals("kakka.txt"))
			{
				return true;
			}else{
				return false;
			}
		}

		public String getDescription() { return ""; }
	}

	/**
	 * �Q�ƃ{�^��
	 */
	public void pushedOpenFileButton(ActionEvent e)
	{
		FileDialog fd = new FileDialog(this,"�������ʃf�[�^���w�肵�Ă�������",FileDialog.LOAD);
		fd.setVisible(true);

		if(fd.getDirectory() != null && fd.getFile() != null)
		{
			jTextField_FileName.setText(fd.getDirectory() + fd.getFile());
		}
	}

// dsel s.inoue 2010/01/25
//	public void changedDokujiState(ItemEvent e)
//	{
//		if( ItemEvent.SELECTED == e.getStateChange() )
//		{
//			validateDate.setImportFormat(jRadioButton_Dokuji.getText());
//		}
//	}
//
//	public void changedKouseiState(ItemEvent e)
//	{
//		if( ItemEvent.SELECTED == e.getStateChange() )
//		{
//			validateDate.setImportFormat(jRadioButton_Kousei.getText());
//		}
//	}

	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}

		if( e.getSource() == jButton_Import )
		{
			logger.info(jButton_Import.getText());
			pushedImportButton( e );
		}

		if( e.getSource() == jButton_OpenFile )
		{
			logger.info(jButton_OpenFile.getText());
			pushedOpenFileButton( e );
		}
	}

	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Import.getText());
			pushedImportButton(null);break;
		}
	}

// del s.inoue 2010/01/26
//	public void itemStateChanged(ItemEvent e)
//	{
//		if( e.getSource() == jRadioButton_Dokuji)
//		{
//			changedDokujiState(e);
//		}
//
//		if( e.getSource() == jRadioButton_Kousei)
//		{
//			changedKouseiState(e);
//		}
//	}
}  //  @jve:decl-index=0:visual-constraint="11,15"


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

