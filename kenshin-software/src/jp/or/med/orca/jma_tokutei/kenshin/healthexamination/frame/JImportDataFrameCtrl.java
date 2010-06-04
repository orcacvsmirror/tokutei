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
 * 外部健診結果データ取り込みのフレームのコントロール
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
	private static final String FLAME_TITLE_MESSAGE = "同姓同名・同一生年月日の受診者が存在します。";
	private String BMIFormat = "##.0";

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private ButtonGroup radioButtonGroup = new ButtonGroup();

	// loggerの objectを作る。
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

		//健診センター名称コンボボックスの初期設定
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

	/* Add 2008/07/23 井上 */
	/* --------------------------------------------------- */
	public boolean validateImportJusinSeiriNo(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		// エラーの場合、種別をセット
		if (!data.jusinSeiriNo.toString().equals("")){
			// 数値チェック
			//if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
			//	data.errCase=JApplication.ERR_KIND_NUMBER;
			//	errFlg=true;
			//}else

			// 必須チェック
			if (data.jusinSeiriNo.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// 半角チェック
			if (JValidate.isAllHankaku(data.jusinSeiriNo.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 桁数チェック
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
			// 必須チェック
			if (data.jisiKikanNo.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// 半角チェック
			if (JValidate.isAllHankaku(data.jisiKikanNo.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 桁数チェック
			if (JValidate.checkMaxLimit(data.jisiKikanNo.toString(),10) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}else
			// 一致チェック
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
			// 半角チェック
			if (JValidate.isAllHankaku(data.jishiDate.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 実施日妥当性チェック
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
			// 必須チェック
			if (data.kanaShimei.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else

			// 半角チェック
			if ( !JValidate.isAllHankaku(data.kanaShimei,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else

			// 桁数チェック
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
			// 必須チェック
			if (data.kanaShimei.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else

			// 半角チェック
			if ( !JValidate.isAllZenkaku(data.kanaShimei)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else

			// 桁数チェック
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
			// 生年月日日付妥当性チェック
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

				// 再変換処理(和暦→西暦)
				if (!JValidate.checkByte(data.seiNenGapi,8) &&
					!JValidate.isNumber(data.seiNenGapi.toString())){

					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
					if( temp == null ){
						data.errCase=JApplication.ERR_KIND_DATE;
						errFlg = true;
					}
				}

				// 数値チェック
				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
					data.errCase=JApplication.ERR_KIND_NUMBER;
					errFlg=true;
				}
				// 半角チェック
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
			// 生年月日日付妥当性チェック
			// 必須チェック
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

				// 再変換処理(和暦→西暦)
				if (!JValidate.checkByte(data.seiNenGapi,8) &&
					!JValidate.isNumber(data.seiNenGapi.toString())){

					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
					if( temp == null ){
						data.errCase=JApplication.ERR_KIND_DATE;
						errFlg = true;
					}
				}

				// 数値チェック
				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
					data.errCase=JApplication.ERR_KIND_NUMBER;
					errFlg=true;
				}
				// 半角チェック
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
		// 性別チェック
		try{
			if (!data.seiBetu.toString().equals("")){
				// 半角チェック
				if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}else if( !JValidate.validateSexFlag(data.seiBetu) )
				{
					// 性別妥当性
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
		// 性別チェック
		try{
			// 生年月日日付妥当性チェック
			// 必須チェック
			if (data.seiBetu.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;

			// 半角チェック
			}else if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else if( !JValidate.validateSexFlag(data.seiBetu) )
			{
				// 性別妥当性
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
		// 乳ビ/溶血チェック
		try{
			if (!data.nyuBi.toString().equals("")){
				// 桁数チェック
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
		// 食前/食後チェック
		try{
			if (!data.shokuZenGo.toString().equals("")){
				// 桁数チェック
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


	/*** 詳細情報 ***/
	public boolean validateKensaKoumokuCode(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// 検査項目コードチェック
		try{
			// 必須チェック
			if (data.koumokuCd.equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// 半角チェック
			if ( !JValidate.isAllHankaku(data.koumokuCd,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 桁数チェック 17桁のみに変更 s.inoue 20081114
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

		// 実施区分チェック
		try{
			String temp = JValidate.validateJisiKubun(data.jisiKbn);

			// add s.inoue 20081118(空腹時血糖,ＨｂＡ１ｃ)
			//boolean kensaJisi = checkTokuteiKensaJisi(data);

			// 必須チェック
			if (data.jisiKbn.toString().equals("")){
					data.errCase=JApplication.ERR_KIND_EMPTY;
					errFlg=true;
			}
			// 半角チェック
			else if ( !JValidate.isAllHankaku(data.jisiKbn,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			// 値チェック
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
		// 空腹時血糖,ＨｂＡ１ｃの場合
		if (KihonKensaKoumoku.KUFUKU_CODES.contains(data.koumokuCd) ||
				KihonKensaKoumoku.HBA1C_CODES.contains(data.koumokuCd)){
			// 結果値無し
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
//		// 異常値区分チェック
//		try{
//			if (!data.ijyoKbn.toString().equals("")){
//				// 値チェック
//				String temp = JValidate.validateHLKubun(data.ijyoKbn);
//
//				// 半角チェック
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
//		// 結果値形態チェック
//		try{
//			if (!data.kekkaTiKeitai.toString().equals("")){
//				String temp = JValidate.validateKekkaTiKeitai(data.kekkaTiKeitai);
//
//				// 半角チェック
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
			// 結果値チェック
			// 必須チェック
// del s.inoue 20080916
//			if (data.kekkaTi.equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else

			// edit s.inoue 20080916
			// 実施区分が1の時
			if (data.jisiKbn.equals("1"))
			{
				// 半角チェック
				if ( !JValidate.isAllHankaku(data.kekkaTi,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}else
				// 桁数チェック
				if (JValidate.checkMaxLimit(data.kekkaTi,14) == false){
					data.errCase=JApplication.ERR_KIND_DIGIT;
					errFlg=true;
				}

				// add 20080916 s.inoue
				// 結果値なし 未実施
				if (data.kekkaTi.equals("")){
					data.jisiKbn = "0";
				}
			}

			// add 20080916 s.inoue
			// 結果値あり 未実施又は測定不能
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

	// CSVファイルformatチェック
	// flg|true:結果 false:日医
	private boolean inputCsvFileCheck(String FilePath,boolean flg){
		boolean retCheck = false;

		// 1.txt形式かどうか
		if (!FilePath.endsWith(".txt") &&
				!FilePath.endsWith(".TXT") &&
					!FilePath.endsWith(".csv") &&
						!FilePath.endsWith(".CSV"))
			retCheck = true;

		Vector<String> insertRow = new Vector<String>();

		// 検査結果データ取込処理
		if (flg){
			for (int i = 0; i < CSVItems.size(); i++) {
				insertRow =CSVItems.get(i);
				int csvCnt = insertRow.size() - 1;

				// 2.属性情報 が13個以上あるか
				if (csvCnt <= 13)
					retCheck = true;

				// 3.属性情報 - 検査項目情報が8で割り切れる
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
	 * 日医フォーマットデータの取り込み
	 * @param
	 */
	public void nitiImport(
			JImportErrorResultFrameData Data,
			ArrayList<Hashtable<String, String>> kojinData,
			ArrayList<Hashtable<String, String>> selectKojinData,
			Vector<JImportErrorResultFrameData> ErrorResultData)
	{

		// 登録件数変数
		int intregistCnt = 0;
		boolean retblnALL = false;
		ResultMessage="";

		// 検査結果データ取込処理
		for (int i = 0; i < CSVItems.size(); i++) {

			Vector<String> insertRow = new Vector<String>();

			insertRow =CSVItems.get(i);

			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "件");

			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JUSIN_SEIRI_NO));
			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SHIMEI));
			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SEIBETU));
			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISIKIKAN_NO));

			// 初期化処理
			Data.uketukeNo =null;
			kojinData = null;
			preuketukeId = 0L;
			prekensaDate =0;

			// 結果取込データ(キー情報)チェック処理
			if (checkKensaKekkaKeyNitiiFormatData(Data,ErrorResultData,i,false)){
				i++;continue;
			}

			// 個人結果データ:(整理番号,健診日単位で取得)
			try {
				if (!Data.jusinSeiriNo.toString().equals(""))
					kojinData = getKojinData(Data);

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。");
					break;
				}

			// 該当するデータがない場合、人(氏名,生年月日,性別)で検索
			if (kojinData == null ||
					kojinData.size() == 0){
				try{

					selectKojinData = getSimeiData(Data);

					if (selectKojinData == null ||
							selectKojinData.size() == 0){
						break;
					}

					// 該当する人が複数いた場合、リストを表示する
					Hashtable<String, String>selectedKojinData
						= searchKojinData(selectKojinData,Data,false);

					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);

					kojinData = getSelectKojinData(Data);
					if (kojinData == null ||
							kojinData.size() == 0){
						break;
					}

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。");
					break;
				}
			}

			// add s.inoue 2010/02/10
			// 配列から構造体へ変換（ Arrays.asList）
			HashMap<String, String> csvKoumokuCD = this.registerKoumokuCd(validateDate);

			// 結果取込CSVファイルとDBの値の比較を行う。
			// その他テーブルの項目コード単位で処理を行う。
			for (int j = 0; j < kojinData.size(); j++) {

					Hashtable<String,String> DatabaseItem = kojinData.get(j);

					// del s.inoue 2010/02/17
					// if (Data.uketukeNo == null){
					Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					//}

					// 受付IDを比較する（一致：登録対象、不一致：エラー対象）
					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);

// del s.inoue 2010/02/17 1件のみの対象により削除
					// データベースに登録(T_KENSAKEKKA_TOKUTEI)
					// キー情報(:受付ID,検査実施日)が変わったら登録処理を行う
//					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
//							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
						try{

							// 結果取込データ(属性情報)チェック処理
							if (checkKensaKekkaData(Data,ErrorResultData,i)){
								j++;continue;
							}

							// 既に健診結果データが存在した場合、警告メッセージを表示する。
							// 健診実施日,氏名カナ毎 結果登録するとデータが存在する為、更新処理のみ
							// edit s.inoue 2010/02/10
							String strCsvKoumokuOrder = (String) csvKoumokuCD.get(koumokuCdDB);
							if (strCsvKoumokuOrder == null){
								j++;continue;
							}
							System.out.println("csv:" + strCsvKoumokuOrder);
							// 項目コードはCSV順番を渡して取得
							// edit s.inoue 2010/02/17
							// Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
							Data.koumokuCd = koumokuCdDB;
							Data.kekkaTi = Reader.rmQuart(insertRow.get(Integer.parseInt(strCsvKoumokuOrder)));

							// 項目コードチェック
							if (checkKoumokuCD(Data,ErrorResultData,i)){
								j++;continue;
							}

							boolean existsKensaKekkaData = existsKensaKekkaData(Data);

							if (existsKensaKekkaData) {

								if (!retblnALL){
									// M3328=確認,[%s]には、すでに結果データが存在します。登録した場合、
									// 以前に入力した結果データは消えてしまいます。登録してよろしいですか？
									String[] messageParams = {"受付番号:"+ Data.uketukeNo + "/" + "健診実施日:" + Data.jishiDate + "/" + "氏名:"+ Data.kanaShimei};
									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
									// cancel時
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
							// BMI自動計算 roop最後に処理
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
							ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);

							logger.error("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。");
							break;
						}
					}

// del s.inoue 2010/02/03
//					// キー情報が一致したら検査結果登録してあり正常
//					// 「その他:1～22の項目(動的に変化する)」の登録
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
//							logger.error("[検査結果取込処理]検査結果データその他データ取得時、エラーとなりました。");
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
//							// 結果取込データ(検査項目情報)
//							if (checkKensaKekkaDataDetail(Data,ErrorResultData,i)){
//								cntKoumoku += kensaKoumokuLoop;break;
//							}
//
//							// 健診結果その他登録処理
//							try{
//								kensakekaSonotaregister(Data);
//
//							}catch(Exception ex){
//								try {
//									JApplication.kikanDatabase.rollback();
//
//								} catch (SQLException err) {
//								}
//								logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。");
//								ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);
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

	// sonotaテーブル更新
	private void updatekensakekaSonota(JImportErrorResultFrameData Data,Vector<String> insertRow){
		// Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN ));
		Data.jisiKbn ="1"; // フィールドなし固定
		// Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI));
		try {
			kensakekaSonotaregister(Data);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// add s.inoue 2010/02/10
	// 項目コード登録
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
	 * 取り込み前処理
	 * @param FilePath 読み込むファイルのパス
	 */
	private void nitiImportCheck(String FilePath)
	{
		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		ArrayList<Hashtable<String, String>> kojinData=null;
		ArrayList<Hashtable<String, String>> selectKojinData=null;

		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();

		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();

		// CSV読込処理
		Reader = new JCSVReaderStream();

		try {

			Reader.openCSV(FilePath,JApplication.CSV_CHARSET);

			CSVItems = Reader.readAllTable();

			if (inputCsvFileCheck(FilePath,false)){
				JErrorMessage.show("M3327",this);
				return;
			}

		} catch (Exception e) {
			logger.error("[日医フォーマット取込処理]CSV読込処理に失敗しました。");
			JErrorMessage.show("M3329",this);
			return;
		}

		// 機関(特定,その他)接続生成
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
			logger.error("[検査結果取込処理]機関FDBへの接続処理に失敗しました。");
			JErrorMessage.show("M3326",this);
			return;
		}

		// 取込み
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
	 * データの取り込み
	 * @param FilePath 読み込むファイルのパス
	 */
	public void kekkaImport(String FilePath)
	{

		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		ArrayList<Hashtable<String, String>> kojinData=null;
		ArrayList<Hashtable<String, String>> selectKojinData=null;

		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();

		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();
		// 登録件数変数
		int intregistCnt = 0;
		boolean retblnALL = false;
		ResultMessage="";

		// CSV読込処理
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
			logger.error("[検査結果取込処理]CSV読込処理に失敗しました。");
			JErrorMessage.show("M3326",this);
			return;
		}

		// 機関(特定,その他)接続生成
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
			logger.error("[検査結果取込処理]機関FDBへの接続処理に失敗しました。");
			JErrorMessage.show("M3326",this);
			return;
		}

		// 検査結果データ取込処理
		for (int i = 0; i < CSVItems.size(); i++) {

			Vector<String> insertRow = new Vector<String>();

			insertRow =CSVItems.get(i);

			// add s.inoue 20081001
			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "件");

			// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JUSIN_SEIRI_NO));
			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISI_DATE));
			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHIMEI));
			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEINENGAPI));
			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEIBETU));
			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISIKIKAN_NO));
			Data.nyuBi =Reader.rmQuart((insertRow.get(JApplication.CSV_ZOKUSEI_NYUBI_YOUKETU)));
			Data.shokuZenGo =Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHOKUZEN_SHOKUGO));

			// 初期化処理
			Data.uketukeNo =null;
			kojinData = null;
			preuketukeId = 0L;
			prekensaDate =0;

			// 結果取込データ(キー情報)チェック処理
			if (checkKensaKekkaKeyData(Data,ErrorResultData,i,false)){
				i++;continue;
			}

			// 個人結果データ:(整理番号,健診日単位で取得)
			try {
				if (!Data.jusinSeiriNo.toString().equals(""))
					kojinData = getKojinData(Data);

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。");
					break;
				}

			// 該当するデータがない場合、人(氏名,生年月日,性別)で検索
			if (kojinData == null ||
					kojinData.size() == 0){
				try{

					selectKojinData = getSimeiData(Data);

					if (selectKojinData == null ||
							selectKojinData.size() == 0){
						break;
					}

					// 該当する人が複数いた場合、リストを表示する
					// edit s.inoue 2010/02/03
					Hashtable<String, String>selectedKojinData
						= searchKojinData(selectKojinData,Data,true);

					// del s.inoue 20081003
					//if (selectedKojinData == null) {
					//	Data.errCase = JApplication.ERR_KIND_NOT_EXIST;
					//	setImportErrDigit(ErrorResultData,Data,i);
					//	ResultMessage += GetErrorMessage("[検査結果取込処理]該当者がいませんでした。処理を中断します。","検査結果データ:" + Data.kanaShimei);
					//	break;
					//}
					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);

					kojinData = getSelectKojinData(Data);
					if (kojinData == null ||
							kojinData.size() == 0){
						break;
					}

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。");
					break;
				}
			}

			// 結果取込CSVファイルとDBの値の比較を行う。
			// その他テーブルの項目コード単位で処理を行う。
			// add s.inoue 2010/04/21
			String height = "";
			String weight = "";
			for (int j = 0; j < kojinData.size(); j++) {

					Hashtable<String,String> DatabaseItem = kojinData.get(j);

					if (Data.uketukeNo == null){
						Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					}
					// 受付IDを比較する（一致：登録対象、不一致：エラー対象）
					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);

					// データベースに登録(T_KENSAKEKKA_TOKUTEI)
					// キー情報(:受付ID,検査実施日)が変わったら登録処理を行う
					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
						try{

							// 結果取込データ(属性情報)チェック処理
							if (checkKensaKekkaData(Data,ErrorResultData,i)){
								j++;continue;
							}

							// 既に健診結果データが存在した場合、警告メッセージを表示する。
							// 健診実施日,氏名カナ毎 結果登録するとデータが存在する為、更新処理のみ
							Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
							// 項目コードチェック
							if (checkKoumokuCD(Data,ErrorResultData,i)){
								j++;continue;
							}

							boolean existsKensaKekkaData = existsKensaKekkaData(Data);

							if (existsKensaKekkaData) {

								if (!retblnALL){
									// edit ver2 s.inoue 2009/05/22
									// M3328=確認,[%s]には、すでに結果データが存在します。登録した場合、
									// 以前に入力した結果データは消えてしまいます。登録してよろしいですか？
									String[] messageParams = {"受付番号:"+ Data.uketukeNo + "/" + "健診実施日:" + Data.jishiDate + "/" + "氏名:"+ Data.kanaShimei};
									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
									// cancel時
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
							ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);

							logger.error("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。");
							break;
						}
					}

					// キー情報が一致したら検査結果登録してあり正常
					// 「その他:1～22の項目(動的に変化する)」の登録
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
							logger.error("[検査結果取込処理]検査結果データその他データ取得時、エラーとなりました。");
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

							// 結果取込データ(検査項目情報)
							if (checkKensaKekkaDataDetail(Data,ErrorResultData,i)){
								cntKoumoku += kensaKoumokuLoop;break;
							}

							// 健診結果その他登録処理
							try{
								kensakekaSonotaregister(Data);

								// add s.inoue 2010/04/21
								// BMI自動計算 roop最後に処理
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
								logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。");
								ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);
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
			logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。" + JApplication.LINE_SEPARATOR + err.getMessage());
		}

		// アンマッチエラー一覧表示
		if(ErrorResultData.size() >= 1){
			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
					this,new JImportErrorResultFrameCtrl(ErrorResultData,this));

		}else if(intregistCnt == 0){
			JErrorMessage.show("M3325",this);

		// 処理中断のエラーメッセージを表示
		}else if(ResultMessage.equals("")){
			JErrorMessage.show("M3324",this);

		// 処理中断のエラーメッセージを表示
		}else{
			try
			{
				BufferedWriter file = new BufferedWriter(new FileWriter("Error.txt"));
				file.write(ResultMessage);
				file.close();

			}catch(Exception err){
				logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。" + JApplication.LINE_SEPARATOR + err.getMessage());
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
	 * 整理番号で検索できない場合の絞込み処理
	 * convertFlg:かな氏名を半角→全角 true,全角→半角 false
	 */
	private Hashtable<String,String> searchKojinData(
			ArrayList<Hashtable<String,String>> KojinDatabaseData,
			JImportErrorResultFrameData Data,
			boolean convertFlg){

		// 該当する人を検索
		Hashtable<String,String> TargetItem = null;

		Vector<JSelectKojinFrameData> SameKojinData = new Vector<JSelectKojinFrameData>();

		for(int j = 0 ; j < KojinDatabaseData.size() ; j++)
		{
			Hashtable<String,String> DatabaseItem = KojinDatabaseData.get(j);

			// edit s.inoue 2010/02/03
			//半角カタカナ、空白を除去した状態で比較
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

		//この段階でnullが入っていたら該当する人がいないので、エラーを出すなどの処理を行う。
		//if(TargetItem == null)
		//{
		//	ResultMessage += GetErrorMessage("該当する受診者がいません。",shimeiKana);
		//}

		//同姓同名・同一生年月日の受診者が複数いた場合の処理
		if(SameKojinData.size() > 1)
		{
			//ResultMessage += GetErrorMessage("同姓同名・同一生年月日の受診者がいます。選択ダイアログを出します。",shimeiKana);

//			JSelectKojinFrameData SelectedData =  new JSelectKojinFrameData();
//			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JSelectKojinFrameCtrl(SameKojinData,this));
//			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this);
//			SelectedData =SelectKojinFrame.GetSelectedData();

			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this,FLAME_TITLE_MESSAGE);
			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();

			if(SelectedData == null)
			{
				ResultMessage += GetErrorMessage("受診者が選択されませんでした。処理をスキップします。",Data.kanaShimei);
				TargetItem=null;
			}else{
				TargetItem = SelectedData.DatabaseItem;
			}
		}
		return TargetItem;
	}
	/* --------------------------------------------------- */

	/* ADD 2008/07/23 井上 */
	/* --------------------------------------------------- */
	/**
	 * エラー種別設定処理
	 */
	private void setImportErrDigit(
			//Hashtable<String,String> DatabaseItem,
			Vector<JImportErrorResultFrameData> ErrorResultData,
			JImportErrorResultFrameData Data,
			Integer i){

		// add ver2 s.inoue 2009/07/27
		// DB項目
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

		// DB項目
		//Data.rowNo =i + 1;
		ErrorResultData.add(DataKeys);
	}

	/**
	 * 検査結果データチェック処理(検査項目情報◎必須)
	 */
	private boolean checkKensaKekkaKeyData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i,
				boolean flg)
	{
		boolean checkKensaKekkaFlg=false;

		if (!Data.jusinSeiriNo.toString().equals("")){
			// 受診券整理番号
			if (validateImportJusinSeiriNo(Data))
			{
				setImportErrDigit(ErrorResultData,Data,i);
				checkKensaKekkaFlg =true;

			}
		}
		// カナ氏名◎
		if (validateImportkanaShimei(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 生年月日
		if (validateImportseiNenGapi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 性別
		if (validateImportSex(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 乳び/溶血
		if (validateImportNyuBi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 食前/食後
		if (validateImportShokuZenGo(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 健診実施年月日◎
		if (validateImportJisiDate(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;
	}

	/**
	 * 検査結果データチェック処理(検査項目情報◎必須、●日医フォーマット用必須)
	 */
	private boolean checkKensaKekkaKeyNitiiFormatData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i,
				boolean flg)
	{
		boolean checkKensaKekkaFlg=false;

		if (!Data.jusinSeiriNo.toString().equals("")){
			// 受診券整理番号
			if (validateImportJusinSeiriNo(Data))
			{
				setImportErrDigit(ErrorResultData,Data,i);
				checkKensaKekkaFlg =true;

			}
		}
		// カナ氏名●
		if (validateImportNitiiFormatkanaShimei(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 生年月日●
		if (validateImportNitiiFormatseiNenGapi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 性別●
		if (validateImportNitiiFormatSex(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 健診実施年月日◎
		if (validateImportJisiDate(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;

	}

	/**
	 * 検査結果データチェック処理(属性情報)★format共通
	 */
	private boolean checkKensaKekkaData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i) {

		boolean checkKensaKekkaFlg=false;

		// 健診実施機関番号
		if (validateImportJisiKikanNo(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;
	}

	/**
	 * 検査結果データチェック処理(検査項目情報◎必須)
	 */
	private boolean checkKoumokuCD(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i)
	{
		boolean checkKensaKekkaFlg=false;
		// 項目コード◎
		if (validateKensaKoumokuCode(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}
		return checkKensaKekkaFlg;
	}


	/**
	 * 検査結果データチェック処理(検査項目情報◎必須)
	 */
	private boolean checkKensaKekkaDataDetail(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i)
	{
		boolean checkKensaKekkaFlg=false;

		// 実施区分◎
		if (validateImportJisiKbn(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}

// del 20080918 s.inoue
//		// 異常値区分
//		if (validateImportHLKbn(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
//		// 結果値形態
//		if (validateImportKekkaTiKeitai(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
		// 検査結果◎
		if (validateImportKekkaTi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}
		return checkKensaKekkaFlg;
	}
	/* --------------------------------------------------- */

	/* EDIT 2008/07/23 井上 */
	/* --------------------------------------------------- */
	/**
	 * 検査結果特定登録
	 * @throws SQLException
	 */
	private void kensakekaTokuteiregister(JImportErrorResultFrameData Data)
		throws SQLException
	{

		kensakekaTokutei = new TKensakekaTokutei();
		// 共通
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
			// このあとUPDATEを行うため一時的にデータを入れる
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

	/* EDIT 2008/07/23 井上 */
	/* --------------------------------------------------- */
	/**
	 * 検査結果登録
	 * @throws SQLException
	 */
	private void kensakekaSonotaregister(JImportErrorResultFrameData Data)
		throws SQLException
	{
			// move4. 検査結果登録処理
			// ad s.inoue データベースに登録(T_KENSAKEKKA_SONOTA)
//			2008/3/19 西山 修正
//			T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
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
			// notnullのフィールド値
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
			//	ed s.inoue ImportItem→shimeiKana
			//	ResultMessage += GetErrorMessage("データベースへの登録に失敗しました。同一のデータが存在する可能性があります。",shimeiKana);
			//	 del s.inoue
			//	 continue;
			//}
//		 -------------------------------------------------------------------------------------------------------
//						 del s.inoue }

	}
	/* --------------------------------------------------- */

	/**
	 * 検査結果有無判定
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
	 * 個人データ取得(条件:氏名、年齢、性別)
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

	/* Edit 2008/07/23 井上 */
	/* --------------------------------------------------- */
	/**
	 * 個人データ取得
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
	 * 個人データ取得(受付番号)
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
	 * BMI値を計算する
	 */
	public String calcBMI(String height, String weight) {
		// どちらかが空値の場合は空値を返す
		if (height.isEmpty() || weight.isEmpty()) {
			return "";
		}

		// ０除算エラーを防ぐ
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
	 * 検査結果その他取得(共通)
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
	 * 検査結果その他取得
	 * flgKeys true:受付番号, false:整理番号
	 */
	private static String getKensaDataSQL(boolean flgKeys)
	{
		// add 20081002 s.inoue SONOTA⇒MASTER.KOUMOKU_CDへ変更
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
	 * 検査結果その他取得(条件別)
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
	 * 改行コード
	 */
	private String LineSeparator = System.getProperty("line.separator");

	/**
	 * エラーメッセージの形式でメッセージを取得する。
	 * @param Message メッセージ
	 * @param Data 読み込み中のデータ
	 * @return フォーマット済みのテキスト
	 */
	private String GetErrorMessage(String Message,JOriginalFormatData Data)
	{
		return "(" + Data.Name + ")" + Message + LineSeparator;
	}

	/**
	 * エラーメッセージの形式でメッセージを取得する。
	 * @param Message メッセージ
	 * @param Name 名前
	 * @return フォーマット済みのテキスト
	 */
	private String GetErrorMessage(String Message,String Name)
	{
		return "(" + Name + ")" + Message + LineSeparator;
	}

	/**
	 * インポートボタン
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
						// 結果取り込み
						kekkaImport(validateDate.getFilePath());
// edit s.inoue 2010/04/02
//					}else{
//						// 日医フォーマット
//						nitiImportCheck(validateDate.getFilePath());
//					}
				}
			}
		}
	}

	/**
	 * 終了ボタン
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
	 * 参照ボタン
	 */
	public void pushedOpenFileButton(ActionEvent e)
	{
		FileDialog fd = new FileDialog(this,"検査結果データを指定してください",FileDialog.LOAD);
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

