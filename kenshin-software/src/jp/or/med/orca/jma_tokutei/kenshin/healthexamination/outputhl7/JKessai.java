package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.math.BigDecimal;

/**
 * ���Ϗ����v�Z����
 */
public class JKessai {
	/**
	 * ���Ϗ��̏���
	 * @param input ���σf�[�^�̓���
	 * @return
	 */
	public static JKessaiDataOutput Kessai(JKessaiDataInput input,
			JKessaiProcessData processData )
	{
		JKessaiDataOutput outputData = new JKessaiDataOutput();

		if(input.Check() == false)
		{
			throw new RuntimeException();
		}

		long tsuikaTanka = input.getTsuikaTanka();
		outputData.setTsuikaTankaGoukei(tsuikaTanka);

		long kihonTanka = input.getKihonTanka();
		long syousaiTanka = input.getSyousaiTanka();
		long tankaGoukei = 	kihonTanka + syousaiTanka +	tsuikaTanka;
		long docTanka = input.getDockTanka();
		// add ver2 s.inoue 2009/07/09
		outputData.setDocTankaGoukei(docTanka);

		/* ���̑��̌��f�ɂ�镉�S���z */
		long madoFutanSonota = input.getMadoFutanSonota();
		long sonota = madoFutanSonota;
		long[] madoFutanKihon = getModifiedMadoFutan(
				kihonTanka,
				input.getKihonMadoFutan(),
				input.getKihonMadoFutanSyubetu(),
				input.getKihonHokenjyaJyougen(),
				sonota
			);

		if (madoFutanKihon[0] >= 0) {
			sonota = 0;
		}
		else {
			sonota = - madoFutanKihon[0];
			madoFutanKihon[0] = 0;
		}

		long[] madoFutanSyousai = {0, 0};
		if (input.isExistsSyousai()) {
			madoFutanSyousai = getModifiedMadoFutan(
					syousaiTanka,
					input.getSyousaiMadoFutan(),
					input.getSyousaiMadoFutanSyubetu(),
					input.getSyousaiHokenjyaJyougen(),
					sonota
				);

			if (madoFutanSyousai[0] >= 0) {
				sonota = 0;
			}
			else {
				sonota = - madoFutanSyousai[0];
				madoFutanSyousai[0] = 0;
			}
		}

		long madoFutanTsuika[] = {0, 0};
		if (input.isExistsTsuika()) {
			madoFutanTsuika = getModifiedMadoFutan(
					tsuikaTanka,
					input.getTsuikaMadoFutan(),
					input.getTsuikaMadoFutanSyubetu(),
					input.getTsuikaHokenjyaJyougen(),
					sonota
				);

			if (madoFutanTsuika[0] < 0) {
				madoFutanTsuika[0] = 0;
			}
		}

		long madoFutanGoukei = madoFutanKihon[0] + madoFutanSyousai[0] + madoFutanTsuika[0];
		long seikyuKingaku = tankaGoukei - madoFutanGoukei - madoFutanSonota;
		if (seikyuKingaku <= 0) {
			seikyuKingaku = 0;

			madoFutanGoukei = tankaGoukei - madoFutanSonota;
			if (madoFutanGoukei < 0) {
				madoFutanGoukei = 0;
			}
		}

		// add ver2 s.inoue 2009/07/09
		/* �l�ԃh�b�N�v�Z */
		long madoFutanDoc[] = {0, 0};
		if (input.isExistsDoc()) {
			madoFutanDoc = getModifiedMadoFutanNingenDoc(
					docTanka,
					input.getDocMadoFutan(),
					input.getDockMadoFutanSyubetu(),
					input.getDockHokenjyaJyougen(),
					sonota
				);

			if (madoFutanDoc[0] < 0) {
				madoFutanDoc[0] = 0;
			}
		}

		long madoFutanDocGoukei = madoFutanDoc[0];
		long seikyuKingakuDoc = docTanka - madoFutanDocGoukei - madoFutanSonota;
		if (seikyuKingakuDoc <= 0) {
			seikyuKingakuDoc = 0;

			madoFutanDocGoukei = docTanka - madoFutanSonota;
			if (madoFutanDocGoukei < 0) {
				madoFutanDocGoukei = 0;
			}
		}

		outputData.setMadoFutanDoc(madoFutanDoc[0]);

		outputData.setMadoFutanSonota(madoFutanSonota);
		outputData.setTankaGoukei(tankaGoukei);
		outputData.setMadoFutanKihon(madoFutanKihon[0]);
		outputData.setMadoFutanSyousai(madoFutanSyousai[0]);
		outputData.setMadoFutanTsuika(madoFutanTsuika[0]);

		// add ver2 s.inoue 2009/07/09
		if (input.isExistsDoc()){
			// edit ver2 s.inoue 2009/07/21
			outputData.setMadoFutanGoukei(madoFutanDocGoukei);
			outputData.setSeikyuKingaku(seikyuKingakuDoc);
		}else{
			// edit ver2 s.inoue 2009/07/21
			outputData.setMadoFutanGoukei(madoFutanGoukei);
			outputData.setSeikyuKingaku(seikyuKingaku);
		}

		processData.outputFutanSyubetsuKihon = (int)madoFutanKihon[1];
		processData.outputFutanSyubetsuSyousai = (int)madoFutanSyousai[1];
		processData.outputFutanSyubetsuTsuika = (int)madoFutanTsuika[1];

		processData.outputFutanSyubetsuDoc = (int)madoFutanDoc[1];

		return outputData;
	}

	/**
	 * ���S�z���Z�o����
	 * @param tankaParam �P��
	 * @param inputHutanParam ���S�̒l
	 * @param inputCodeParam ���S�̃R�[�h
	 * @return �R�[�h���画�ʂ������S�z
	 */
	public static long[] getModifiedMadoFutan(
			long tanka,
			long inputHutan,
			int inputCode,
			long jyougen,
			long sonota
		){

		long tankaParam = tanka < 0 ? 0 : tanka;
		long inputHutanParam = inputHutan < 0 ? 0 : inputHutan;
		int inputCodeParam = inputCode < 1 ? 1 : inputCode;
		long jyougenParam = jyougen < 0 ? 0 : jyougen;
		long sonotaParam = sonota < 0 ? 0 : sonota;

		/* ���S�z */
		long hutan = 0;
		long syubetu = inputCodeParam;

		/* ���S�Ȃ��i�{�ی��ҕ��S����z�j */
		if(inputCodeParam == 0 || inputCodeParam == 1 ){
			/* 1.���S�Ȃ� */
			if(jyougenParam == 0){
				/* �������S���z = 0 */
				hutan = 0;
			}
			/* 4.�ی��ҕ��S����z */
			else {
				syubetu = 4;

				/* A �P�������̑� */
				if (tankaParam > sonotaParam) {
					/* a �P���|���̑����ی��ҕ��S����z�̂Ƃ� */
					if ((tankaParam - sonotaParam) > jyougenParam) {
						/* �������S���z = �P�� - �ی��ҕ��S����z */
						hutan = ( tankaParam - sonotaParam ) - jyougenParam;
					}
					/* b �P���|���̑������ی��ҕ��S����z�̂Ƃ� */
					else {
						/* �������S���z���O */
						hutan = 0;
					}
				}
				/* B �P���������̑� */
				else {
					/* �������S���z���O */
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* ��z���S�i�{�ی��ҕ��S����z�j */
		else if(inputCodeParam == 2){
			/* �ی��ҕ��S����z�� 0 -> ��z���S�̂� */
			/* 2.��z���S */

			if (jyougenParam == 0){

				/* A �P�������̑� */
				if (tankaParam > sonotaParam) {
					/* a �P�� - ���̑�����z���S���z */
					if ((tankaParam - sonotaParam) > inputHutanParam) {
						hutan = inputHutanParam;
					}
					/* b �P�� - ���̑�������z���S���z */
					else {
						hutan = tankaParam - sonotaParam;
					}
				}
				/* B �P���������̑� */
				else {
					/* �������S���z���O */
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 5.��z���S�{�ی��ҕ��S����z */
			else {

				syubetu = 2;

				/* A �P�������̑� */
				if (tankaParam > sonotaParam) {
					/* a �P���|���̑�����z���S���z�̂Ƃ� */
					if ((tankaParam - sonotaParam) > inputHutanParam) {
						/* �A�j�P���|���̑��|��z���S���z���ی��ҕ��S����z�̂Ƃ� */
						if (tankaParam - sonotaParam - inputHutanParam > jyougenParam) {
							hutan = tankaParam - sonotaParam - jyougenParam;
							syubetu = 4;
						}
						/* �C�j�P���|���̑��|��z���S���z�����ی��ҕ��S����z�̂Ƃ� */
						else {
							hutan = inputHutanParam;
						}
					}
					/* b �P���|���̑�������z���S���z�̂Ƃ� */
					else {
						hutan = tankaParam - sonotaParam;
					}
				}
				/* B �P���������̑� */
				else {
					/* �������S���z���O */
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* �藦���S�i+ �ی��ҕ��S����z�j */
		else if(inputCodeParam == 3){

			/* ���S�������z�ɕϊ����� */
			double dHutan = (double)inputHutanParam;
			double wariai = dHutan / 100000.0;
			double dTanka = (double)tankaParam;
			double madoHutanDouble = (tankaParam - sonotaParam) * wariai;
			BigDecimal bd = new BigDecimal(String.valueOf(madoHutanDouble));
			long hutanGaku = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();


			/* �ی��ҕ��S����z�� 0 -> �藦���S�̂� */
			/* 3.�藦���S */
			if (jyougenParam == 0){
				/* �������S���z���P���w�藦 */
				/* A �P�������̑� */
				if (tankaParam > sonotaParam) {
					/* �������S���z���o�P���|���̌��f�ɂ�镉�S���z�p�w�藦 */
					hutan = hutanGaku;
				}
				/* B �P���������̑� */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 6.�藦���S�{�ی��ҕ��S����z */
			else {
				syubetu = 3;

				/* A �P�������̑� */
				if (tankaParam > sonotaParam) {
					/* a �P���|���̑��|�i�P���w�藦�j���ی��ҕ��S����z�̂Ƃ� */
					if ((tankaParam - sonotaParam) - hutanGaku > jyougenParam) {
						hutan = tankaParam - sonotaParam - jyougenParam;
						syubetu = 4;
					}
					/* b �P���|���̑��|�i�P���w�藦�j�����ی��ҕ��S����z�̂Ƃ� */
					else {
						hutan = hutanGaku;
					}
				}
				/* B �P���������̑� */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
		}

		long[] ret = { 0, 0 };

		ret[0] = hutan;
		ret[1] = syubetu;

		return ret;
	}

	// add ver2 s.inoue 2009/07/08
	/**
	 * ���S�z�i�l�ԃh�b�N�j���Z�o����
	 * @param tankaParam �P��
	 * @param inputHutanParam ���S�̒l
	 * @param inputCodeParam ���S�̃R�[�h
	 * @return �R�[�h���画�ʂ������S�z
	 */
	public static long[] getModifiedMadoFutanNingenDoc(
			long tankaDoc,
			long inputHutanDoc,
			int inputCodeDoc,
			long jyougenDoc,
			long sonota
		){

		long tankaParam = tankaDoc < 0 ? 0 : tankaDoc;
		long inputHutanParam = inputHutanDoc < 0 ? 0 : inputHutanDoc;
		int inputCodeParam = inputCodeDoc < 1 ? 1 : inputCodeDoc;
		long jyougenParam = jyougenDoc < 0 ? 0 : jyougenDoc;
		long sonotaParam = sonota < 0 ? 0 : sonota;

		/* ���S�z */
		long hutan = 0;
		long syubetu = inputCodeParam;

		/* ���S�Ȃ��i�{�ی��ҕ��S����z�j */
		if(inputCodeParam == 0 || inputCodeParam == 1 ){
			/* 1.���S�Ȃ� */
			if(jyougenParam == 0){
				/* �������S���z = 0 */
				hutan = 0;
			}
			/* 4.�ی��ҕ��S����z */
			else {
				syubetu = 4;

				/* �l�ԃh�b�N�̂Ƃ� */
				/* a �P���|���̑����ی��ҕ��S����z�̂Ƃ� */
				if ((tankaParam - sonotaParam) > jyougenParam) {
					/* �������S���z = �P�� - �ی��ҕ��S����z */
					hutan = ( tankaParam - sonotaParam ) - jyougenParam;
				}
				/* b �P���|���̑������ی��ҕ��S����z�̂Ƃ� */
				else {
					/* �������S���z���O */
					hutan = 0;
				}
			}
		}
		/* ��z���S�i�{�ی��ҕ��S����z�j */
		else if(inputCodeParam == 2){
			/* �ی��ҕ��S����z�� 0 -> ��z���S�̂� */
			/* 2.��z���S */

			if (jyougenParam == 0){
				/* a �P�� - ���̑�����z���S���z */
				if ((tankaParam - sonotaParam) > inputHutanParam) {
					hutan = inputHutanParam;
				}
				/* b �P�� - ���̑�������z���S���z */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 5.��z���S�{�ی��ҕ��S����z */
			else {

				syubetu = 2;

				/* a �P���|���̑�����z���S���z�̂Ƃ� */
				if ((tankaParam - sonotaParam) > inputHutanParam) {
					/* �A�j�P���|���̑��|��z���S���z���ی��ҕ��S����z�̂Ƃ� */
					if (tankaParam - sonotaParam - inputHutanParam > jyougenParam) {
						hutan = tankaParam - sonotaParam - jyougenParam;
						syubetu = 4;
					}
					/* �C�j�P���|���̑��|��z���S���z�����ی��ҕ��S����z�̂Ƃ� */
					else {
						hutan = inputHutanParam;
					}
				}
				/* b �P���|���̑�������z���S���z�̂Ƃ� */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* �藦���S�i+ �ی��ҕ��S����z�j */
		else if(inputCodeParam == 3){

			/* ���S�������z�ɕϊ����� */
			double dHutan = (double)inputHutanParam;
			double wariai = dHutan / 100000.0;
			double dTanka = (double)tankaParam;
			double madoHutanDouble = (tankaParam - sonotaParam) * wariai;
			BigDecimal bd = new BigDecimal(String.valueOf(madoHutanDouble));
			long hutanGaku = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();


			/* �ی��ҕ��S����z�� 0 -> �藦���S�̂� */
			/* 3.�藦���S */
			if (jyougenParam == 0){
				/* �������S���z���o�P���|���̌��f�ɂ�镉�S���z�p�w�藦 */
				hutan = hutanGaku;
			}
			/* 6.�藦���S�{�ی��ҕ��S����z */
			else {
				syubetu = 3;

				/* a �P���|���̑��|�i�P���w�藦�j���ی��ҕ��S����z�̂Ƃ� */
				if ((tankaParam - sonotaParam) - hutanGaku > jyougenParam) {
					hutan = tankaParam - sonotaParam - jyougenParam;
					syubetu = 4;
				}
				/* b �P���|���̑��|�i�P���w�藦�j�����ی��ҕ��S����z�̂Ƃ� */
				else {
					hutan = hutanGaku;
				}
			}
		}

		long[] ret = { 0, 0 };

		ret[0] = hutan;
		ret[1] = syubetu;

		return ret;
	}


}
