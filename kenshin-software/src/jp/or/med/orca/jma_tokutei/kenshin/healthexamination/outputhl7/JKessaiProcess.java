package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JLong;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * ���Ϗ������s��
 */
public class JKessaiProcess {
	private static final String GROUP_CODE_GANTEI = "9E100161000000049";
	private static final String GROUP_CODE_SHINDENZU = "9A110161000000049";
	private static final String GROUP_CODE_HINKETSU = "2A020161001930149";
	private static Logger logger = Logger.getLogger(JKessaiProcess.class);
	// add s.inoue 2010/01/15
	private static final String TANKA_HANTEI_KIHON = "1";
	private static final String TANKA_HANTEI_DOC = "2";

	/**
	 * ���Ϗ���
	 * @param kessaiDatas �������s����M�҂̃��X�g
	 * @param tKikanNumber ���f���s�����@�ւ̔ԍ�
	 *
	 * Modified 2008/04/02 �ጎ
	 * ���F�������コ���邽�߁A�ύX�����R�����g���폜���A���t�@�N�^�����O���s�Ȃ����B
	 */
	public static void runProcess(Vector<JKessaiProcessData> kessaiDatas,String tKikanNumber) throws Exception
	{
		// edit s.inoue 2009/11/14
		// �ŏ��Ɍ��σf�[�^�e�[�u���A���σf�[�^�ڍ׃e�[�u������S�Ẵf�[�^���폜����
		// �ڍׂɒP�����c�邽�߁i�ڍ׌��f���ǉ����f�j�A�폜�𕜊�������I�I�I
		for (JKessaiProcessData kessaiProcessData : kessaiDatas) {
			StringBuilder sbKesaiWk = new StringBuilder();
			StringBuilder sbKesaiSyosaiWk = new StringBuilder();

			sbKesaiWk.append("DELETE FROM T_KESAI_WK");
			sbKesaiWk.append(" WHERE UKETUKE_ID = ");
			sbKesaiWk.append(JQueryConvert.queryConvert(kessaiProcessData.uketukeId));
			sbKesaiSyosaiWk.append("DELETE FROM T_KESAI_SYOUSAI_WK");
			sbKesaiSyosaiWk.append(" WHERE UKETUKE_ID = ");
			sbKesaiSyosaiWk.append(JQueryConvert.queryConvert(kessaiProcessData.uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(sbKesaiWk.toString());
			JApplication.kikanDatabase.sendNoResultQuery(sbKesaiSyosaiWk.toString());
		}

		for(int i = 0 ; i < kessaiDatas.size() ; i++)
		{
			// �l���Ƃ̃f�[�^
			// ���ꂪ��̍�ƒP�ʂƂȂ�
			JKessaiProcessData kessaiData = kessaiDatas.get(i);

			/* ��ی��ҏؓ��L�� */
			String hiHokenjyaKigo = kessaiData.hiHokenjyaKigo;
			if (hiHokenjyaKigo == null) {
				hiHokenjyaKigo = "";
			}

			/* ��ی��ҏؓ��ԍ����Ȃ��ꍇ�́A�G���[�Ƃ���B */
			if (kessaiData.hiHokenjyaNumber == null) {
				JErrorMessage.show("M4751",null);
				throw new Exception();
			}

			/* �ی��҂̏�� */
			Hashtable<String, String> hokenjyaInfo = getHokenjaInfo(kessaiData.hokenjyaNumber);

			/* �ϑ��敪 */
			String itakuKubun = hokenjyaInfo.get("ITAKU_KBN");

			/* ------------------------------------------------------------------------------------
			 * ���Ϗ��̌v�Z���s���B
			 * ------------------------------------------------------------------------------------
			 */

			/* ���̓f�[�^ */
			JKessaiDataInput inputData = new JKessaiDataInput();

			/* �l���e�[�u�����瑋�����S��ʂƋ��z�i�܂��͗��j���擾����B */
			Hashtable<String, String> kojinInfo = null;
			kojinInfo = JApplication.kikanDatabase.sendExecuteQuery(
					"SELECT * FROM T_KOJIN WHERE " +
					"HKNJANUM = " + JQueryConvert.queryConvert(kessaiData.hokenjyaNumber) + " AND " +
					"UKETUKE_ID = " + JQueryConvert.queryConvert(kessaiData.uketukeId)).get(0);

			/* ------------------------------------------
			 * ��{�I�Ȍ��f
			 * ------------------------------------------
			 */
			String inputTankaKihon = hokenjyaInfo.get("TANKA_KIHON");

			/* �������S�i��{�I�Ȍ��f�j */
			String inputMadoHutanKihon = kojinInfo.get("MADO_FUTAN_KIHON");
			inputData.setKihonMadoFutan(JLong.valueOf(inputMadoHutanKihon));
			/* �������S��ʁi��{�I�Ȍ��f�j */
			String inputMadoHutanKihonSyubetsu = kojinInfo.get("MADO_FUTAN_K_SYUBETU");
			inputData.setKihonMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanKihonSyubetsu));
			/* �P���i��{�I�Ȍ��f�j */
			inputData.setKihonTanka(JLong.valueOf(inputTankaKihon));

			/* �ی��ҕ��S����z�i��{�I�Ȍ��f�j */
			String hokenjyaHutanJyougenKihon = kojinInfo.get(
					"HOKENJYA_FUTAN_KIHON");
			if (hokenjyaHutanJyougenKihon != null &&
					! hokenjyaHutanJyougenKihon.isEmpty()) {

				long hokenjyaHutanJyougenKihonLong = JLong.valueOf(
						hokenjyaHutanJyougenKihon);
				inputData.setKihonHokenjyaJyougen(hokenjyaHutanJyougenKihonLong);
			}

			/*------------------------------------------
			 * �ڍׂȌ��f
			 * ------------------------------------------
			 */
			String inputTankaHinketu = null;
			String inputTankaShindenzu = null;
			String inputTankaGantei = null;

			/* �n������ */
			String inputHinketsuCode = hokenjyaInfo.get("HINKETU_CD");
			String executeTankaHinketsu = null;
			boolean existSyousaiHinketu = isExistSyousaiKensaKoumoku(
								kessaiData.hokenjyaNumber,
								kessaiData.uketukeId,
								kessaiData.KensaDate,
								GROUP_CODE_HINKETSU);
			if(existSyousaiHinketu)
			{
				executeTankaHinketsu = hokenjyaInfo.get("TANKA_HINKETU");
				inputTankaHinketu = hokenjyaInfo.get("TANKA_HINKETU");
			}else{
				executeTankaHinketsu = "000000";
			}

			/* �S�d�}���� */
			String inputShindenzuCode = hokenjyaInfo.get("SINDENZU_CD");
			String executeTankaShindenzu = null;
			boolean existSyousaiSindenzu = isExistSyousaiKensaKoumoku(
								kessaiData.hokenjyaNumber,
								kessaiData.uketukeId,
								kessaiData.KensaDate,
								GROUP_CODE_SHINDENZU);
			if(existSyousaiSindenzu)
			{
				executeTankaShindenzu = hokenjyaInfo.get("TANKA_SINDENZU");
				inputTankaShindenzu = hokenjyaInfo.get("TANKA_SINDENZU");
			}else{
				executeTankaShindenzu = "000000";
			}

			/* ��ꌟ�� */
			String inputGanteiCode = hokenjyaInfo.get("GANTEI_CD");
			String executeTankaGantei = null;
			boolean existSyousaiGantei = isExistSyousaiKensaKoumoku(
								kessaiData.hokenjyaNumber,
								kessaiData.uketukeId,
								kessaiData.KensaDate,
								GROUP_CODE_GANTEI);
			if(existSyousaiGantei)
			{
				executeTankaGantei = hokenjyaInfo.get("TANKA_GANTEI");
				inputTankaGantei = hokenjyaInfo.get("TANKA_GANTEI");
			}else{
				executeTankaGantei = "000000";
			}

			boolean existsSyousai = false;
			if (existSyousaiHinketu || existSyousaiSindenzu
					|| existSyousaiGantei) {
				existsSyousai = true;
			}

			inputData.setExistsSyousai(existsSyousai);

			/* �������S�i�ڍׂȌ��f�j */
			String inputMadoHutanSyousai = kojinInfo.get("MADO_FUTAN_SYOUSAI");
			inputData.setSyousaiMadoFutan(JLong.valueOf(inputMadoHutanSyousai));
			/* �������S��ʁi�ڍׂȌ��f�j */
			String inputMadoHutanSyosaiSyubetsu = kojinInfo.get("MADO_FUTAN_S_SYUBETU");
			inputData.setSyousaiMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanSyosaiSyubetsu));
			/* �P���i�ڍׂȌ��f�j */
			long executeSyousaiTanka = JInteger.valueOf(executeTankaHinketsu) +
								JInteger.valueOf(executeTankaShindenzu) +
								JInteger.valueOf(executeTankaGantei);
			inputData.setSyousaiTanka(executeSyousaiTanka);

			/* �ی��ҕ��S����z�i�ڍׂȌ��f�j */
			String hokenjyaHutanJyougenSyousai = kojinInfo.get(
				"HOKENJYA_FUTAN_SYOUSAI");

			if (hokenjyaHutanJyougenSyousai != null &&
					! hokenjyaHutanJyougenSyousai.isEmpty()) {

				long hokenjyaHutanJyougenSyousaiLong = JLong.valueOf(
						hokenjyaHutanJyougenSyousai);
				inputData.setSyousaiHokenjyaJyougen(
						hokenjyaHutanJyougenSyousaiLong);
			}

			/*------------------------------------------
			 * �ǉ��̌��f
			 * ------------------------------------------
			 */
			/* �������S�i�ǉ��̌��f�j */
			String inputMadoHutanTsuika = kojinInfo.get("MADO_FUTAN_TSUIKA");
			inputData.setTsuikaMadoFutan(JLong.valueOf(inputMadoHutanTsuika));
			/* �������S��ʁi�ǉ��̌��f�j */
			String inputMadoHutanTsuikaSyubetu = kojinInfo.get("MADO_FUTAN_T_SYUBETU");
			inputData.setTsuikaMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanTsuikaSyubetu));

			/* �P���i�ǉ��̌��f�j */
			String[] params = {
					kessaiData.hokenjyaNumber,
					kessaiData.uketukeId,
					kessaiData.KensaDate
				};

			ArrayList<Hashtable<String,String>> items =
				JApplication.kikanDatabase.sendExecuteQuery(
					SELECT_TSUIKA_ITEM_DATA_SQL, params );

			Hashtable<String,String> item = null;
			if (items != null && items.size() > 0) {
				item = items.get(0);
			}
			else {
				/* ���b�Z�[�W���K�v�H */
				return;
			}

			long inputTankaTsuikaKenshin = JLong.valueOf(item.get("TANKA_KENSIN"));
			int tsuikaCount = JInteger.valueOf(item.get("TSUIKA_COUNT"));

			if (tsuikaCount > 0) {
				inputData.setExistsTsuika(true);
			}

			inputData.setTsuikaTanka(inputTankaTsuikaKenshin);

			/* �ی��ҕ��S�z����i�ǉ��̌��f�j */
			String hokenjyaHutanJyougenTsuika = kojinInfo.get(
				"HOKENJYA_FUTAN_TSUIKA");

			if (hokenjyaHutanJyougenTsuika != null &&
				! hokenjyaHutanJyougenTsuika.isEmpty()) {

				long hokenjyaHutanJyougenTsuikaLong = JLong.valueOf(
						hokenjyaHutanJyougenTsuika);
				inputData.setTsuikaHokenjyaJyougen(
						hokenjyaHutanJyougenTsuikaLong);
			}

			/*------------------------------------------
			 * �l�ԃh�b�N
			 * ------------------------------------------*/

			/* �������S�i�l�ԃh�b�N�j */
			String inputMadoHutanDock = kojinInfo.get("MADO_FUTAN_DOC");
			inputData.setDockMadoFutan(JLong.valueOf(inputMadoHutanDock));

			/* �������S��ʁi�l�ԃh�b�N�j */
			String inputMadoHutanDockSyubetu = kojinInfo.get("MADO_FUTAN_D_SYUBETU");

			inputData.setDockMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanDockSyubetu));

			// add ver2 s.inoue 2009/06/16
			/* �P���i�l�ԃh�b�N�j
			 * ���݁A�ی��ҏ��ɐl�ԃh�b�N�̒P����ݒ肷��ꏊ�������B
			 * �ЂƂ܂� 0 �~�Ƃ��Ă����B */
			String inputTankaDockKenshin = hokenjyaInfo.get("TANKA_NINGENDOC");
			inputData.setDockTanka(JLong.valueOf(inputTankaDockKenshin));

			// edit s.inoue 2010/01/15  �l�ԃh�b�N�Ή�
			// if (inputData.getDockTanka() > 0){
			//	inputData.setExistsDoc(true);
			// }
			if(hokenjyaInfo.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
				inputData.setExistsDoc(true);
			}

			/* �ی��ҕ��S�z����i�l�ԃh�b�N�j */
			String hokenjyaHutanJyougenDock = kojinInfo.get(
				"HOKENJYA_FUTAN_DOC");

			if (hokenjyaHutanJyougenDock != null &&
				! hokenjyaHutanJyougenDock.isEmpty()) {

				long hokenjyaHutanJyougenDockLong = JLong.valueOf(
						hokenjyaHutanJyougenDock);
				inputData.setDockHokenjyaJyougen(
						hokenjyaHutanJyougenDockLong);
			}

			/*------------------------------------------
			 * ���̑��̌��f�ɂ�镉�S�z
			 * ------------------------------------------
			 */
			String inputMadoHutanSonota = kojinInfo.get("MADO_FUTAN_SONOTA");
			inputData.setMadoFutanSonota(JLong.valueOf(inputMadoHutanSonota));

			/* ------------------------------------------
			 *  ���Ϗ������s�Ȃ��B
			 * ------------------------------------------
			 */
			/* ���Ϗ��̏o�͏�� */
			JKessaiDataOutput outputData = new JKessaiDataOutput();

			outputData = JKessai.Kessai(inputData, kessaiData);

			/* �P�����v */
			String outputTankaGoukei = String.valueOf(outputData.getTankaGoukei());

			/* JKessai�Ōv�Z������́A0���߂�����Ă��Ȃ��̂ŁA�K�v�ȂƂ���͖��߂�B */
			String outputMadoHutanKihon = Utility.FillZero(outputData.getMadoFutanKihon(), 6);
			String outputMadoHutanSyousai = Utility.FillZero(outputData.getMadoFutanSyousai(), 6);
			String outputMadoHutanTsuika = Utility.FillZero(outputData.getMadoFutanTsuika(), 6);
			String outputMadoHutanDoc = Utility.FillZero(outputData.getMadoFutanDoc(), 6);

			/* Added 2008/04/28 �ጎ ���̑��̌��f�ɂ�镉�S���z */
			/* --------------------------------------------------- */
			String outputMadoHutanSonota = String.valueOf(outputData.getMadoFutanSonota());

			/* �������S���v */
			String outputMadoHutanGoukei = String.valueOf(outputData.getMadoFutanGoukei());

			/* �������z */
			String outputSeikyuKingaku = String.valueOf(outputData.getSeikyuKingaku());

			/* �����̊m�F���s�� */
			if(
					outputTankaGoukei.length() > 9 ||
					outputMadoHutanGoukei.length() > 9 ||
					outputSeikyuKingaku.length() > 9
			)
			{
				JErrorMessage.show("M8401", null);
				continue;
			}

			// edit ver2 s.inoue 2009/08/20
			/* �V�K�y�эX�V */
			Hashtable<String, String> kesaiCnt = null;
			kesaiCnt = JApplication.kikanDatabase.sendExecuteQuery(
					" SELECT COUNT(UKETUKE_ID) UKETUKE_CNT FROM T_KESAI_WK "
					+ " WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber)
					+ " AND UKETUKE_ID = " + JQueryConvert.queryConvert(kessaiData.uketukeId)
					+ " AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kessaiData.KensaDate)).get(0);

			int kesaiCount = Integer.parseInt(kesaiCnt.get("UKETUKE_CNT"));

			// edit ver2 s.inoue 2009/08/21
			// timestamp�擾
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			if (kesaiCount > 0){

				StringBuffer bldUPD = new StringBuffer();

				bldUPD.append("UPDATE T_KESAI_WK SET ");
				bldUPD.append("TKIKAN_NO= " + JQueryConvert.queryConvertAppendComma(tKikanNumber));
				bldUPD.append("JISI_KBN= " + JQueryConvert.queryConvertAppendComma(kessaiData.jissiKubun));
//				bldUPD.append("KENSA_NENGAPI= " + JQueryConvert.queryConvertAppendComma(kessaiData.KensaDate));
				bldUPD.append("SEIKYU_KBN= " + JQueryConvert.queryConvertAppendComma(kessaiData.seikyuKubun));
				bldUPD.append("SYUBETU_CD= " + JQueryConvert.queryConvertAppendComma(kessaiData.syubetuCode));
				bldUPD.append("HKNJANUM= " + JQueryConvert.queryConvertAppendComma(kessaiData.hokenjyaNumber));
				bldUPD.append("HIHOKENJYASYO_KIGOU= " + JQueryConvert.queryConvertAppendComma(kessaiData.hiHokenjyaKigo));
				bldUPD.append("HIHOKENJYASYO_NO= " + JQueryConvert.queryConvertAppendComma(kessaiData.hiHokenjyaNumber));
				bldUPD.append("ITAKU_KBN= " + JQueryConvert.queryConvertAppendComma(itakuKubun));
				bldUPD.append("TANKA_KIHON= " + JQueryConvert.queryConvertAppendComma(inputTankaKihon));
				bldUPD.append("HINKETU_CD= " + JQueryConvert.queryConvertAppendComma(inputHinketsuCode));
				bldUPD.append("TANKA_HINKETU= " + JQueryConvert.queryConvertAppendComma(inputTankaHinketu));
				bldUPD.append("SINDENZU_CD= " + JQueryConvert.queryConvertAppendComma(inputShindenzuCode));
				bldUPD.append("TANKA_SINDENZU= " + JQueryConvert.queryConvertAppendComma(inputTankaShindenzu));
				bldUPD.append("GANTEI_CD= " + JQueryConvert.queryConvertAppendComma(inputGanteiCode));
				bldUPD.append("TANKA_GANTEI= " + JQueryConvert.queryConvertAppendComma(inputTankaGantei));
				bldUPD.append("MADO_FUTAN_K_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanKihonSyubetsu));
				bldUPD.append("MADO_FUTAN_KIHON= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanKihon));
//				bldUPD.append("MADO_FUTAN_KIHON_OUT= " + JQueryConvert.queryConvertAppendComma());
				bldUPD.append("MADO_FUTAN_S_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanSyosaiSyubetsu));
				bldUPD.append("MADO_FUTAN_SYOUSAI= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanSyousai));
//				bldUPD.append("MADO_FUTAN_SYOUSAI_OUT= " + JQueryConvert.queryConvertAppendComma());
				bldUPD.append("MADO_FUTAN_T_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuikaSyubetu));
				bldUPD.append("MADO_FUTAN_TSUIKA= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuika));
//				bldUPD.append("MADO_FUTAN_TSUIKA_OUT= " + JQueryConvert.queryConvertAppendComma());
				bldUPD.append("TANKA_GOUKEI= " + JQueryConvert.queryConvertAppendComma(outputTankaGoukei));
				bldUPD.append("MADO_FUTAN_GOUKEI= " + JQueryConvert.queryConvertAppendComma(outputMadoHutanGoukei));
				bldUPD.append("SEIKYU_KINGAKU= " + JQueryConvert.queryConvertAppendComma(outputSeikyuKingaku));
				bldUPD.append("SIHARAI_DAIKOU_BANGO= " + JQueryConvert.queryConvertAppendComma(kessaiData.daikouKikanNumber));
//				bldUPD.append("NENDO= " + JQueryConvert.queryConvertAppendComma());
//				bldUPD.append("UKETUKE_ID= " + JQueryConvert.queryConvertAppendComma(kessaiData.uketukeId));
				bldUPD.append("MADO_FUTAN_SONOTA= " + JQueryConvert.queryConvertAppendComma(outputMadoHutanSonota));
				bldUPD.append("HOKENJYA_FUTAN_KIHON= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenKihon));
				bldUPD.append("HOKENJYA_FUTAN_SYOUSAI= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenSyousai));
				bldUPD.append("HOKENJYA_FUTAN_TSUIKA= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenTsuika));
				bldUPD.append("HOKENJYA_FUTAN_DOC= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenDock));
				bldUPD.append("TANKA_NINGENDOC= " + JQueryConvert.queryConvertAppendComma(inputTankaDockKenshin));
				bldUPD.append("MADO_FUTAN_D_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanDockSyubetu));
				bldUPD.append("MADO_FUTAN_DOC= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanDock));
				bldUPD.append("MADO_FUTAN_DOC_OUT= " + JQueryConvert.queryConvertAppendComma(outputMadoHutanDoc));
				bldUPD.append("UPDATE_TIMESTAMP = "  + JQueryConvert.queryConvert(stringTimeStamp));
				// edit s.inoue 2009/10/13
				bldUPD.append(" WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber));
				bldUPD.append(" AND UKETUKE_ID= " + JQueryConvert.queryConvert(kessaiData.uketukeId));
				bldUPD.append(" AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kessaiData.KensaDate));

				String query = bldUPD.toString();
				JApplication.kikanDatabase.sendNoResultQuery(query);

				// edit ver2 s.inoue 2009/08/21
				/* �ǉ��ڍ׃e�[�u���ɒǉ����f���ڂ̏���ǉ�����B */
				insertKessaiSyousai(tKikanNumber, kessaiData,true);

			}else{

				StringBuffer bldINS = new StringBuffer();

				bldINS.append(INSERT_SQL_BASE);
				bldINS.append(" VALUES (");

				/* ���f�@�֔ԍ� */
				bldINS.append(JQueryConvert.queryConvertAppendComma(tKikanNumber));
				/* ���{�敪 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.jissiKubun));
				/* ���f�N���� */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.KensaDate));
				/* �����敪 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.seikyuKubun));
				/* ��ʃR�[�h */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.syubetuCode));
				/* �ی��Ҕԍ� */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.hokenjyaNumber));
				/* ��ی��ҏؓ��L�� */
				bldINS.append(JQueryConvert.queryConvertAppendBlankAndComma(hiHokenjyaKigo));
				/* ��ی��ҏؓ��ԍ� */
				bldINS.append(JQueryConvert.queryConvertAppendBlankAndComma(kessaiData.hiHokenjyaNumber));
				/* �ϑ��敪 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(itakuKubun));
				/* �P���i��{�I�Ȍ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaKihon));
				/* �n���R�[�h */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputHinketsuCode));
				/* �P���i�ڍׂȌ��f�A�n���j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaHinketu));
				/* �S�d�}�R�[�h */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputShindenzuCode));
				/* �P���i�ڍׂȌ��f�A�S�d�}�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaShindenzu));
				/* ���R�[�h */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputGanteiCode));
				/* �P���i�ڍׂȌ��f�A���j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaGantei));
				/* �������S��ʁi��{�I�Ȍ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanKihonSyubetsu));
				/* �������S�i��{�I�Ȍ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanKihon));
				/* �������S��ʁi�ڍׂȌ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanSyosaiSyubetsu));
				/* �������S�i�ڍׂȌ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanSyousai));
				/* �������S��ʁi�ǉ��̌��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuikaSyubetu));
				/* �������S�i�ǉ��̌��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuika));
				/* �P�����v */
				// edit s.inoue 2010/02/10
				if (hokenjyaInfo.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
					bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaDockKenshin));
				}else{
					bldINS.append(JQueryConvert.queryConvertAppendComma(outputTankaGoukei));
				}
				/* �������S���v */
				// edit s.inoue 2010/02/10
				if (hokenjyaInfo.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
					bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanDoc));
				}else{
					bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanGoukei));
				}

				/* �������z */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputSeikyuKingaku));

				/* �ϊ�����*/
				// del ver2 s.inoue 2009/04/02
				//buffer.append(JQueryConvert.queryConvertAppendComma(null));
				/* �������S�i��{�I�Ȍ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanKihon));
				/* �������S�i�ڍׂȌ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanSyousai));
				/* �������S�i�ǉ��̌��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanTsuika));
				/* �x����s�@�֔ԍ� */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.daikouKikanNumber));
				/* ��tID */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.uketukeId));
				/* ���̑��̌��f�ɂ�镉�S���z */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanSonota));
				/* �ی��ҕ��S�z��� */
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenKihon));
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenSyousai));
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenTsuika));
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenDock));

				/* �P���i�l�ԃh�b�N�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaDockKenshin));
				/* �������S��ʁi��{�I�Ȍ��f�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanDockSyubetu));

				/* �������S�i�l�ԃh�b�N�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanDock));
				/* �������S�i�l�ԃh�b�N�j */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanDoc));
				// edit ver2 s.inoue 2009/08/24
				bldINS.append(JQueryConvert.queryConvert(stringTimeStamp));
				bldINS.append(")");

				String query = bldINS.toString();
				JApplication.kikanDatabase.sendNoResultQuery(query);
			}

			// edit ver2 s.inoue 2009/08/21
			/* �ǉ��ڍ׃e�[�u���ɒǉ����f���ڂ̏���ǉ�����B */
			insertKessaiSyousai(tKikanNumber, kessaiData,false);
		}
	}

	// add ver2 s.inoue 2009/09/09
	/**
	 * ���ϊm�菈�� (work �� kessai)
	 * @param kessaiDatas �������s����M�҂̃��X�g
	 * @param tKikanNumber ���f���s�����@�ւ̔ԍ�
	 */
	public static void runWorkToKessaiProcess(Vector<JKessaiProcessData> kessaiDatas,String tKikanNumber) throws Exception
	{
		// work �� kessai�e�[�u��
		// 1.work�e�[�u������kessai�e�[�u���փf�[�^�R�s�[
		// 2.work�e�[�u������f�[�^�폜
		for(int i = 0 ; i < kessaiDatas.size() ; i++)
		{
			// �l���Ƃ̃f�[�^
			JKessaiProcessData kessaiData = kessaiDatas.get(i);
			// add s.inoue 2009/10/19
			// ���e�[�u���̍폜
			deleteKesai(kessaiData.uketukeId);
			// _WK �� T_KESAI
			registerWorkToKesai(kessaiData);
			// _WK �� T_KESAI_SYOUSAI
			registerWorkToKesaiSyousai(kessaiData);
			// del s.inoue 2009/10/18
			// _WK�e�[�u���̍폜
			// deleteKesai(kessaiData.uketukeId);

			// move s.inoue 2012/11/02
			// _WK�e�[�u���̍폜
			// deleteWork(kessaiData.uketukeId);
		}

	}

	// add ver2 s.inoue 2009/09/09
	/**
	 * WORK�e�[�u�����猈�σe�[�u���֓o�^���������{����B
	 */
	private static void registerWorkToKesai(JKessaiProcessData kessaiData){

		// �����e�[�u���o�^����(���f�[�^�́A�l�e�[�u�����擾)
		// timestamp�擾
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		StringBuilder workQuery = new StringBuilder();
		workQuery.append("INSERT INTO T_KESAI");
		workQuery.append(" (TKIKAN_NO, JISI_KBN, KENSA_NENGAPI, SEIKYU_KBN, SYUBETU_CD, HKNJANUM, HIHOKENJYASYO_KIGOU,");
		workQuery.append(" HIHOKENJYASYO_NO, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD,");
		workQuery.append(" TANKA_GANTEI, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_KIHON_OUT, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI,");
		workQuery.append(" MADO_FUTAN_SYOUSAI_OUT, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_TSUIKA_OUT, TANKA_GOUKEI, MADO_FUTAN_GOUKEI,");
		workQuery.append(" SEIKYU_KINGAKU, SIHARAI_DAIKOU_BANGO, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI,");
		workQuery.append(" HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC, TANKA_NINGENDOC, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, MADO_FUTAN_DOC_OUT,UPDATE_TIMESTAMP)");
		workQuery.append(" SELECT ");
		workQuery.append(" TKIKAN_NO, JISI_KBN, KENSA_NENGAPI, SEIKYU_KBN, SYUBETU_CD, HKNJANUM, HIHOKENJYASYO_KIGOU,");
		workQuery.append(" HIHOKENJYASYO_NO, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD,");
		workQuery.append(" TANKA_GANTEI, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_KIHON_OUT, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI,");
		workQuery.append(" MADO_FUTAN_SYOUSAI_OUT, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_TSUIKA_OUT, TANKA_GOUKEI, MADO_FUTAN_GOUKEI,");
		workQuery.append(" SEIKYU_KINGAKU, SIHARAI_DAIKOU_BANGO, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI,");
		workQuery.append(" HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC, TANKA_NINGENDOC, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, MADO_FUTAN_DOC_OUT,");
		workQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
		workQuery.append(" FROM T_KESAI_WK ");
		workQuery.append(" WHERE TKIKAN_NO = ");
		workQuery.append(JQueryConvert.queryConvert(JApplication.kikanNumber));
		workQuery.append(" AND UKETUKE_ID = ");
		workQuery.append(JQueryConvert.queryConvert(kessaiData.uketukeId));
		workQuery.append(" AND KENSA_NENGAPI = ");
		workQuery.append(JQueryConvert.queryConvert(kessaiData.KensaDate));

		try {
			JApplication.kikanDatabase.sendNoResultQuery(workQuery.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());

			try {
				JApplication.kikanDatabase.rollback();
			} catch (SQLException g) {
				logger.error(g.getMessage());
			}
		}
	}

	// add ver2 s.inoue 2009/09/09
	/**
	 * WORK�e�[�u�����猈�Ϗڍ׃e�[�u���֓o�^���������{����B
	 */
	private static void registerWorkToKesaiSyousai(JKessaiProcessData kessaiData){

		// �����e�[�u���o�^����(���f�[�^�́A�l�e�[�u�����擾)
		// timestamp�擾
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		StringBuilder workQuery = new StringBuilder();
		workQuery.append("INSERT INTO T_KESAI_SYOUSAI (");
		workQuery.append("TKIKAN_NO,KENSA_NENGAPI,HKNJANUM,HIHOKENJYASYO_KIGOU,HIHOKENJYASYO_NO,TUIKA_KENSIN_CD,TANKA_TUIKA_KENSIN,UKETUKE_ID,UPDATE_TIMESTAMP) ");
		workQuery.append(" SELECT ");
		workQuery.append("TKIKAN_NO,KENSA_NENGAPI,HKNJANUM,HIHOKENJYASYO_KIGOU,HIHOKENJYASYO_NO,TUIKA_KENSIN_CD,TANKA_TUIKA_KENSIN,UKETUKE_ID,");
		workQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
		workQuery.append(" FROM T_KESAI_SYOUSAI_WK WHERE UKETUKE_ID = ");
		workQuery.append( JQueryConvert.queryConvert(kessaiData.uketukeId));
		workQuery.append(" AND KENSA_NENGAPI = ");
		workQuery.append( JQueryConvert.queryConvert(kessaiData.KensaDate));
		// workQuery.append(" AND TUIKA_KENSIN_CD = ");
		// workQuery.append( JQueryConvert.queryConvert(kessaiData.));
		workQuery.append(" AND HKNJANUM = ");
		workQuery.append( JQueryConvert.queryConvert(kessaiData.hokenjyaNumber));

		try {
			JApplication.kikanDatabase.sendNoResultQuery(workQuery.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	// add s.inoue 2009/10/16
	/**
	 * �폜�������s��
	 */
	public static void deleteKesai(String uketukeId)
	{
		// T_KESAI�f�[�^�폜
		StringBuilder workKesaiQuery = new StringBuilder();
		try{

			workKesaiQuery.append("DELETE FROM T_KESAI");
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

			workSyousaiQuery.append("DELETE FROM T_KESAI_SYOUSAI");
			workSyousaiQuery.append(" WHERE UKETUKE_ID = ");
			workSyousaiQuery.append(JQueryConvert.queryConvert(uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(workSyousaiQuery.toString());

		}catch(SQLException err)
		{
			logger.error(err.getMessage());
		}
	}
	// add ver2 s.inoue 2009/09/09
	/**
	 * �폜�������s��
	 */
	public static void deleteWork(String uketukeId)
	{
		// T_KESAI�f�[�^�폜
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
	}

	private static String INSERT_SQL_BASE = null;
	static {
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT INTO ");
		buffer.append(" T_KESAI_WK ");

		/* ���f�@�֔ԍ� */
		buffer.append("(TKIKAN_NO,");
		/* ���{�敪 */
		buffer.append("JISI_KBN,");
		/* ���f�N���� */
		buffer.append("KENSA_NENGAPI,");
		/* �����敪 */
		buffer.append("SEIKYU_KBN,");
		/* ��ʃR�[�h */
		buffer.append("SYUBETU_CD,");
		/* �ی��Ҕԍ� */
		buffer.append("HKNJANUM,");
		/* ��ی��ҏؓ��L�� */
		buffer.append("HIHOKENJYASYO_KIGOU,");
		/* ��ی��ҏؓ��ԍ� */
		buffer.append("HIHOKENJYASYO_NO,");
		/* �ϑ��敪 */
		buffer.append("ITAKU_KBN,");
		/* �P���i��{�I�Ȍ��f�j */
		buffer.append("TANKA_KIHON,");
		/* �n���R�[�h */
		buffer.append("HINKETU_CD,");
		/* �P���i�ڍׂȌ��f�A�n���j */
		buffer.append("TANKA_HINKETU,");
		/* �S�d�}�R�[�h */
		buffer.append("SINDENZU_CD,");
		/* �P���i�ڍׂȌ��f�A�S�d�}�j */
		buffer.append("TANKA_SINDENZU,");
		/* ���R�[�h */
		buffer.append("GANTEI_CD,");
		/*�P���i�ڍׂȌ��f�A���j */
		buffer.append("TANKA_GANTEI,");

		/* �������S��ʁi��{�I�Ȍ��f�j */
		buffer.append("MADO_FUTAN_K_SYUBETU,");
		/* �������S�i��{�I�Ȍ��f�j */
		buffer.append("MADO_FUTAN_KIHON,");

		/* �������S��ʁi�ڍׂȌ��f�j */
		buffer.append("MADO_FUTAN_S_SYUBETU,");
		/* �������S�i�ڍׂȌ��f�j */
		buffer.append("MADO_FUTAN_SYOUSAI,");

		/* �������S��ʁi�ǉ��̌��f�j */
		buffer.append("MADO_FUTAN_T_SYUBETU,");
		// edit ver2 s.inoue 2009/06/18
		/* �������S�i�ǉ��̌��f�j */
		buffer.append("MADO_FUTAN_TSUIKA,");

		/* �P�����v */
		buffer.append("TANKA_GOUKEI,");
		/* �������S���v */
		buffer.append("MADO_FUTAN_GOUKEI,");
		/* �������z */
		buffer.append("SEIKYU_KINGAKU,");
		/*  �ϊ�����*/
		// del ver2 s.inoue 2009/04/02
		//buffer.append("HENKAN_NITIJI,");

		/* �������S�i��{�I�Ȍ��f�j */
		buffer.append("MADO_FUTAN_KIHON_OUT,");
		/* �������S�i�ڍׂȌ��f�j */
		buffer.append("MADO_FUTAN_SYOUSAI_OUT,");
		// edit ver2 s.inoue 2009/06/18
		/* �������S�i�ǉ��̌��f�j */
		buffer.append("MADO_FUTAN_TSUIKA_OUT,");

		/* �x����s�@�֔ԍ� */
		buffer.append("SIHARAI_DAIKOU_BANGO,");

		/* ��tID */
		buffer.append("UKETUKE_ID,");

//		/* ���̑��̌��f�ɂ�镉�S�z */
		buffer.append("MADO_FUTAN_SONOTA, ");

		/* �ی��ҕ��S�z��� */
		buffer.append("HOKENJYA_FUTAN_KIHON, ");
		buffer.append("HOKENJYA_FUTAN_SYOUSAI, ");
		buffer.append("HOKENJYA_FUTAN_TSUIKA, ");
		buffer.append("HOKENJYA_FUTAN_DOC,");
		/*�P���i�l�ԃh�b�N�j */
		buffer.append("TANKA_NINGENDOC,");

		/* �������S��ʁi�l�ԃh�b�N�j */
		buffer.append("MADO_FUTAN_D_SYUBETU,");
		/* �������S�i�l�ԃh�b�N�j */
		buffer.append("MADO_FUTAN_DOC,");
		/* �������S�i�l�ԃh�b�N�j */
		buffer.append("MADO_FUTAN_DOC_OUT,");
		// edit ver2 s.inoue 2009/08/24
		/* �X�V���t */
		buffer.append("UPDATE_TIMESTAMP");

		buffer.append(") ");

		INSERT_SQL_BASE = buffer.toString();
	}

	/**
	 * �ǉ��̌��f���ڂ̃f�[�^���擾���� SQL ���쐬����B
	 */
	private static final String SELECT_TSUIKA_ITEM_DATA_SQL = createTsuikaItemDataSql();

	private static String createTsuikaItemDataSql() {

		StringBuffer buffer = new StringBuffer();
		buffer.append(" select ");
		buffer.append("    sum(TANKA_KENSIN) as TANKA_KENSIN ");

		buffer.append("    , count(1) as TSUIKA_COUNT ");
		buffer.append(" from ");
		buffer.append("    T_KENSAKEKA_SONOTA a ");
		buffer.append(" left join T_KENSHINMASTER b ");
		buffer.append(" on ");
		buffer.append("    a.KOUMOKU_CD = b.KOUMOKU_CD ");

		// edit s.inoue 2009/12/03
		buffer.append(" left join T_HOKENJYA c on b.HKNJANUM = c.HKNJANUM ");
		buffer.append(" where b.HKNJANUM = ? ");
		buffer.append(" and a.UKETUKE_ID = ? ");
		buffer.append(" and a.KENSA_NENGAPI = ? ");
		buffer.append(" and a.KEKA_TI is not null ");
		buffer.append(" and a.KEKA_TI <> '' ");
		// edit s.inoue 2009/12/03
		buffer.append(" and c.TANKA_HANTEI <> '2' ");
		// add s.inoue 2010/01/15
		buffer.append(" and c.YUKOU_FLG = '1' ");

		buffer.append(" and ");
		buffer.append("   ( b.HISU_FLG = '3' ");
		buffer.append(" or ");
		buffer.append("   ( b.HISU_FLG = '2' ");
		buffer.append("     and");
		buffer.append("     b.TANKA_KENSIN is not null ");
		buffer.append("     ) )");
		String sql = buffer.toString();

		return sql;
	}

	// edit ver2 s.inoue 2009/08/21
	// �V�K,�X�V�p�ɍX�V
	/**
	 * ���Ϗڍ׃e�[�u���ɒǉ��̌��f���ڃf�[�^��ǉ�����B
	 */
	private static void insertKessaiSyousai(String tKikanNumber, JKessaiProcessData kojinData, boolean updateFlg) throws SQLException {

		String selectSql = "select * from T_KENSAKEKA_SONOTA a left join T_KENSHINMASTER b on " +
						"a.KOUMOKU_CD = b.KOUMOKU_CD where b.HKNJANUM = " + JQueryConvert.queryConvert(kojinData.hokenjyaNumber) +
						" and a.UKETUKE_ID = " + JQueryConvert.queryConvert(kojinData.uketukeId) +
						" and a.KENSA_NENGAPI = " + JQueryConvert.queryConvert(kojinData.KensaDate) +
						" and a.KEKA_TI is not null " +
						" and ( HISU_FLG = '3' or ( HISU_FLG = '2' and b.TANKA_KENSIN is not null ) )";

		// �ǉ��̌��f���ڂ����σf�[�^�ڍׂɏ�������
		ArrayList<Hashtable<String,String>> tsuikaItems = JApplication.kikanDatabase.sendExecuteQuery(selectSql);

// add ver2 s.inoue 2009/08/27
		// ���Ϗڍ׃e�[�u���Ƀf�[�^�����邩�ǂ���
//		Hashtable<String, String> HokenjyaInfo = null;
//		String sql = " SELECT * FROM T_HOKENJYA WHERE HKNJANUM = " +
//							JQueryConvert.queryConvert(hokenjyaNumber);
//
//		ArrayList<Hashtable<String, String>> queryResult = JApplication.kikanDatabase.sendExecuteQuery(sql);
//		if (queryResult == null || queryResult.size() == 0) {
//			JErrorMessage.show("M3140",null);
//			throw new Exception();
//		}
//		HokenjyaInfo = queryResult.get(0);

		// move s.inoue 2009/11/13
//		/* �V�K�y�эX�V */
//		Hashtable<String, String> kesaiCnt = null;
//		kesaiCnt = JApplication.kikanDatabase.sendExecuteQuery(
//				" SELECT COUNT(UKETUKE_ID) UKETUKE_CNT FROM T_KESAI_SYOUSAI_WK "
//				+ " WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber)
//				+ " AND UKETUKE_ID = " + JQueryConvert.queryConvert(kojinData.uketukeId)
//				+ " AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kojinData.KensaDate)
//				// edit s.inoue 2009/11/13
//				+ " AND TUIKA_KENSIN_CD = " + JQueryConvert.queryConvert(tsuikaItems.get(j).get("KOUMOKU_CD"))
//				).get(0);
//
//		int kesaiCount = Integer.parseInt(kesaiCnt.get("UKETUKE_CNT"));


		for(int j = 0 ; j < tsuikaItems.size() ; j++)
		{
			String kekati = tsuikaItems.get(j).get("KEKA_TI");
			if (kekati == null) {
				continue;
			}else if (kekati.isEmpty()) {
				continue;
			}

			String tankaKenshin = tsuikaItems.get(j).get("TANKA_KENSIN");
			if (tankaKenshin == null){
				tankaKenshin = "0";
			}else if (tankaKenshin.equals("")){
				tankaKenshin = "0";
			}

			// timestamp�擾
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			StringBuilder insertSql = new StringBuilder();

			// move s.inoue 2009/09/17
			/* �V�K�y�эX�V */
			Hashtable<String, String> kesaiCnt = null;
			kesaiCnt = JApplication.kikanDatabase.sendExecuteQuery(
					" SELECT COUNT(UKETUKE_ID) UKETUKE_CNT FROM T_KESAI_SYOUSAI_WK "
					+ " WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber)
					+ " AND UKETUKE_ID = " + JQueryConvert.queryConvert(kojinData.uketukeId)
					+ " AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kojinData.KensaDate)
					// edit s.inoue 2009/11/13
					+ " AND TUIKA_KENSIN_CD = " + JQueryConvert.queryConvert(tsuikaItems.get(j).get("KOUMOKU_CD"))
					).get(0);

			int kesaiCount = Integer.parseInt(kesaiCnt.get("UKETUKE_CNT"));

			// edit s.inoue 2009/09/17
			// updateflg��kesaiCount
			if (kesaiCount > 0){
				insertSql.append("UPDATE T_KESAI_SYOUSAI_WK SET ");
				insertSql.append("TKIKAN_NO = " + JQueryConvert.queryConvertAppendComma(tKikanNumber));
				insertSql.append("KENSA_NENGAPI = " + JQueryConvert.queryConvertAppendComma(kojinData.KensaDate));
				// edit ver2 s.inoue 2009/08/24
				// insertSql.append("HKNJANUM = " + JQueryConvert.queryConvertAppendComma(kojinData.hokenjyaNumber));
				insertSql.append("HIHOKENJYASYO_KIGOU = " + JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaKigo));
				insertSql.append("HIHOKENJYASYO_NO = " + JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaNumber));
				// edit ver2 s.inoue 2009/08/24
				// insertSql.append("TUIKA_KENSIN_CD = " + JQueryConvert.queryConvertAppendComma(tsuikaItems.get(j).get("KOUMOKU_CD")));
				insertSql.append("TANKA_TUIKA_KENSIN = " + JQueryConvert.queryConvertAppendComma(tankaKenshin));
				insertSql.append("UPDATE_TIMESTAMP = "+ JQueryConvert.queryConvert(stringTimeStamp));

				// edit ver2 s.inoue 2009/08/21
				// key���s���Ȉ׏C��
				insertSql.append(" WHERE UKETUKE_ID = ");
				insertSql.append( JQueryConvert.queryConvert(kojinData.uketukeId));
				insertSql.append(" AND KENSA_NENGAPI = ");
				insertSql.append( JQueryConvert.queryConvert(kojinData.KensaDate));
				insertSql.append(" AND TUIKA_KENSIN_CD = ");
				insertSql.append( JQueryConvert.queryConvert(tsuikaItems.get(j).get("KOUMOKU_CD")));
				insertSql.append(" AND HKNJANUM = ");
				insertSql.append( JQueryConvert.queryConvert(kojinData.hokenjyaNumber));

			}else{
				insertSql.append("INSERT INTO T_KESAI_SYOUSAI_WK (");
				insertSql.append("TKIKAN_NO,KENSA_NENGAPI,HKNJANUM,HIHOKENJYASYO_KIGOU,HIHOKENJYASYO_NO,TUIKA_KENSIN_CD,TANKA_TUIKA_KENSIN,UKETUKE_ID,UPDATE_TIMESTAMP ");
				insertSql.append(" )VALUES( ");
				insertSql.append( JQueryConvert.queryConvertAppendComma(tKikanNumber));
				insertSql.append( JQueryConvert.queryConvertAppendComma(kojinData.KensaDate));
				insertSql.append( JQueryConvert.queryConvertAppendComma(kojinData.hokenjyaNumber));
				insertSql.append( JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaKigo));
				insertSql.append( JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaNumber));
				insertSql.append( JQueryConvert.queryConvertAppendComma(tsuikaItems.get(j).get("KOUMOKU_CD")));
				insertSql.append( JQueryConvert.queryConvertAppendComma(tankaKenshin));
				insertSql.append( JQueryConvert.queryConvertAppendComma(kojinData.uketukeId));
				insertSql.append( JQueryConvert.queryConvert(stringTimeStamp));
				insertSql.append(" ) ");
			}

			JApplication.kikanDatabase.sendNoResultQuery(insertSql.toString());
		}
	}

	private static Hashtable<String, String> getHokenjaInfo(String hokenjyaNumber) throws SQLException, Exception {
		// ��Õی��ҏ��f�[�^�e�[�u������̍���
		Hashtable<String, String> HokenjyaInfo = null;

		// add s.inoue 2010/01/15
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT * FROM T_HOKENJYA WHERE HKNJANUM = ");
		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
		sb.append(" AND YUKOU_FLG = '1' ");

		ArrayList<Hashtable<String, String>> queryResult = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		if (queryResult == null || queryResult.size() == 0) {
			JErrorMessage.show("M3140",null);
			throw new Exception();
		}

		HokenjyaInfo = queryResult.get(0);
		return HokenjyaInfo;
	}

	/**
	 * �ڍ׌��f�̌������s���Ă��邩�`�F�b�N����
	 * @param hokenjyaNumber �ی��Ҕԍ�
	 * @param HiHokenjyaKigo ��ی��ҏؓ��L��
	 * @param HiHokenjyaNumber ��ی��ҏؓ��ԍ�
	 * @param kensaDate ���f����
	 * @param lensaGroup �����O���[�v
	 * @return true -> ���݂���
	 */
	public static boolean isExistSyousaiKensaKoumoku(
			String hokenjyaNumber,
			String uketukeId,
			String kensaDate,
			String lensaGroup)
	{
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append(" select sonota.KEKA_TI, master.KOUMOKU_CD, master.TANKA_KENSIN ");

			buffer.append(" from T_KENSAKEKA_SONOTA sonota ");

			buffer.append(" left join T_KENSHINMASTER master ");
			buffer.append(" on ");
			buffer.append(" sonota.KOUMOKU_CD = master.KOUMOKU_CD ");

			buffer.append(" where master.HKNJANUM = " + JQueryConvert.queryConvert(hokenjyaNumber));
			buffer.append(" and sonota.UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId));
			buffer.append(" and sonota.KENSA_NENGAPI = " + JQueryConvert.queryConvert(kensaDate));
			buffer.append(" and sonota.KEKA_TI is not null");
			buffer.append(" and HISU_FLG = " + JQueryConvert.queryConvert("2"));
			buffer.append(" and master.KENSA_GROUP = " + JQueryConvert.queryConvert(lensaGroup));

			String query = buffer.toString();

			ArrayList<Hashtable<String, String>> result = JApplication.kikanDatabase.sendExecuteQuery(query);
			for (Hashtable<String, String> item : result) {

				/* �P����ݒ肵�Ă���ꍇ�́A�ǉ����f���ڂƂ��Ĉ����B */
				String tanka = item.get("TANKA_KENSIN");
				if (tanka != null && ! tanka.isEmpty() ) {
					continue;
				}

				/* ���ʒl���擾���Anull ���󕶎��ȊO�Ȃ�A���͂����������̂Ƃ��ăJ�E���g����B */
				String kekati = item.get("KEKA_TI");
				if (kekati != null && ! kekati.isEmpty()) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}
}
