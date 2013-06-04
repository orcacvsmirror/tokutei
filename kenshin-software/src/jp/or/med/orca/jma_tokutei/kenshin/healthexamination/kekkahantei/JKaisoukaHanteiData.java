package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei;

public class JKaisoukaHanteiData {
	private String Gender = null;
	private double Age = -1;
	private double Shintyou = -1;
	private double Taijyu = -1;
	private double NaizouShibou = -1;
	private double Hukui_Jissoku = -1;
	private double Hukui_JikoSokutei = -1;
	private double Hukui_JikoShinkoku = -1;
	private double BMI = -1;
	private double KuhukujiKetto = -1;
	private double HbA1c = -1;
	private double KettoHukuyaku = -1;
	private double ChuseiShibou = -1;
	private double HDLCholesterol = -1;
	private double ShishitsuHukuyaku = -1;
	private double ShushukuKetsuatsuNo1 = -1;
	private double ShushukuKetsuatsuNo2 = -1;
	private double ShushukuKetsuatsuOther = -1;
	private double KakutyoKetsuatsuNo1 = -1;
	private double KakutyoKetsuatsuNo2 = -1;
	private double KakutyoKetsuatsuOther = -1;
	private double KetsuatsuHukuyaku = -1;
	private double Kitsuen = -1;
	// add s.inoue 2013/01/29
	private String KensinjisiDate = "";

	// add ver2 s.inoue 2009/08/19
	private boolean ageTarget = false;

	/**
	 * @param KensinjisiDate
	 */
	public void setKensinJisiDate(String KensinjisiDate) {
		if( !KensinjisiDate.isEmpty() )
		{
			// add s.inoue 2013/03/06
			// this.�����������̂ŁA�G���[
			this.KensinjisiDate = KensinjisiDate;
		}
	}
	public String getKensinJisiDate() {
		return KensinjisiDate;
	}


	/**
	 * @param gender ����
	 */
	public void setGender(String gender) {
		if( !gender.isEmpty() )
		{
			Gender = gender;
		}
	}
	public String getGender() {
		return Gender;
	}

	/**
	 * @param age �N��
	 */
	public void setAge(double age){
		Age = age;
	}

	public double getAge() {
		return Age;
	}

	// add ver2 s.inoue 2009/08/19
	/**
	 * @param �۔�11180001 75�΂̒a�����܂ł̐���
	 */
	public void setTargetAge(boolean age75){
		this.ageTarget = age75;
	}

	public boolean getTargetAge() {
		return ageTarget;
	}

	/**
	 * @param shintyou �g��
	 */
	public void setShintyou(double shintyou) {
		Shintyou = shintyou;
	}
	public double getShintyou() {
		return Shintyou;
	}

	/**
	 * @param taijyu �̏d
	 */
	public void setTaijyu(double taijyu) {
		Taijyu = taijyu;
	}
	public double getTaijyu() {
		return Taijyu;
	}

	/**
	 * @param naizouShibou �������b�ʐ�
	 */
	public void setNaizouShibou(double naizouShibou) {
		NaizouShibou = naizouShibou;
	}
	public double getNaizouShibou() {
		return NaizouShibou;
	}

	/**
	 * @param hukui_Jissoku ���͎���
	 */
	public void setHukui_Jissoku(double hukui_Jissoku) {
		Hukui_Jissoku = hukui_Jissoku;
	}
	/**
	 * @param hukui_JikoSokutei ���͎��ȑ���
	 */
	public void setHukui_JikoSokutei(double hukui_JikoSokutei) {
		Hukui_JikoSokutei = hukui_JikoSokutei;
	}
	/**
	 * @param hukui_JikoShinkoku ���͎��Ȑ\��
	 */
	public void setHukui_JikoShinkoku(double hukui_JikoShinkoku) {
		Hukui_JikoShinkoku = hukui_JikoShinkoku;
	}
	public double getHukui() {
		// ���͎����A���͎��ȑ���A���͎��Ȑ\���̏��Ŏg�p����
		if(Hukui_Jissoku != -1)
		{
			return Hukui_Jissoku;
		}else{
			if(Hukui_JikoSokutei != -1)
			{
				return Hukui_JikoSokutei;
			}else{
				return Hukui_JikoShinkoku;
			}
		}
	}

	/**
	 * @param bmi BMI
	 */
	public void setBMI(double bmi) {
		BMI = bmi;
	}
	public double getBMI() {
		return BMI;
	}

	/**
	 * @param kuhukujiKetto �󕠎�����
	 */
	public void setKuhukujiKetto(double kuhukujiKetto) {
		KuhukujiKetto = kuhukujiKetto;
	}
	public double getKuhukujiKetto() {
		return KuhukujiKetto;
	}

	/**
	 * @param hbA1c HbA1c����
	 */
	public void setHbA1c(double hbA1c) {
		HbA1c = hbA1c;
	}
	public double getHbA1c() {
		return HbA1c;
	}

	/**
	 * @param kettoHukuyaku �����Ɋւ��Ă̕���
	 */
	public void setKettoHukuyaku(double kettoHukuyaku) {
		KettoHukuyaku = kettoHukuyaku;
	}
	public double getKettoHukuyaku() {
		return KettoHukuyaku;
	}

	/**
	 * @param chuseiShibou �������b
	 */
	public void setChuseiShibou(double chuseiShibou) {
		ChuseiShibou = chuseiShibou;
	}
	public double getChuseiShibou() {
		return ChuseiShibou;
	}

	/**
	 * @param cholesterol HDL�R���X�e���[��
	 */
	public void setHDLCholesterol(double cholesterol) {
		HDLCholesterol = cholesterol;
	}
	public double getHDLCholesterol() {
		return HDLCholesterol;
	}

	/**
	 * @param shishitsuHukuyaku �����Ɋւ��Ă̕���
	 */
	public void setShishitsuHukuyaku(double shishitsuHukuyaku) {
		ShishitsuHukuyaku = shishitsuHukuyaku;
	}
	public double getShishitsuHukuyaku() {
		return ShishitsuHukuyaku;
	}

	/**
	 * ���k������
	 */
	public double getShushukuKetsuatsu() {
		// ���k�������A�g���������y�A�Ƃ��Ď�舵���B
		// eidt s.inoue 2012/09/25
		// �y�A�ɂ���Ɣ���ɉe�����o��
		// if(ShushukuKetsuatsuOther != -1 && KakutyoKetsuatsuOther != -1)
		if(ShushukuKetsuatsuOther != -1)
		{
			return ShushukuKetsuatsuOther;
		}else if(ShushukuKetsuatsuNo2 != -1)
		// }else if(ShushukuKetsuatsuNo2 != -1 && KakutyoKetsuatsuNo2 != -1)
		{
			return ShushukuKetsuatsuNo2;
		}else if(ShushukuKetsuatsuNo1 != -1)
		// }else if(ShushukuKetsuatsuNo1 != -1 && KakutyoKetsuatsuNo1 != -1)
		{
			return ShushukuKetsuatsuNo1;
		}

		return -1;
	}

	/**
	 * @param shushukuKetsuatsuNo1 ���k�������i1��ځj
	 */
	public void setShushukuKetsuatsuNo1(double shushukuKetsuatsuNo1) {
		ShushukuKetsuatsuNo1 = shushukuKetsuatsuNo1;
	}

	/**
	 * @param shushukuKetsuatsuNo2 ���k�������i2��ځj
	 */
	public void setShushukuKetsuatsuNo2(double shushukuKetsuatsuNo2) {
		ShushukuKetsuatsuNo2 = shushukuKetsuatsuNo2;
	}

	/**
	 * @param shushukuKetsuatsuOther ���k�������i���̑��j
	 */
	public void setShushukuKetsuatsuOther(double shushukuKetsuatsuOther) {
		ShushukuKetsuatsuOther = shushukuKetsuatsuOther;
	}

	/**
	 * �g������
	 */
	public double getKakutyoKetsuatsu() {
		// ���k�������A�g���������y�A�Ƃ��Ď�舵���B
		// eidt s.inoue 2012/09/25
		// �y�A�ɂ���Ɣ���ɉe�����o��
		if(KakutyoKetsuatsuOther != -1)
		// if(KakutyoKetsuatsuOther != -1 && ShushukuKetsuatsuOther != -1)
		{
			return KakutyoKetsuatsuOther;
		}else if(KakutyoKetsuatsuNo2 != -1)
		// }else if(KakutyoKetsuatsuNo2 != -1 && ShushukuKetsuatsuNo2 != -1)
		{
			return KakutyoKetsuatsuNo2;
		}else if(KakutyoKetsuatsuNo1 != -1)
		// }else if(KakutyoKetsuatsuNo1 != -1 && ShushukuKetsuatsuNo1 != -1)
		{
			return KakutyoKetsuatsuNo1;
		}

		return -1;
	}

	/**
	 * @param kakutyoKetsuatsuNo1 �g�������i1��ځj
	 */
	public void setKakutyoKetsuatsuNo1(double kakutyoKetsuatsuNo1) {
		KakutyoKetsuatsuNo1 = kakutyoKetsuatsuNo1;
	}

	/**
	 * @param kakutyoKetsuatsuNo2 �g�������i2��ځj
	 */
	public void setKakutyoKetsuatsuNo2(double kakutyoKetsuatsuNo2) {
		KakutyoKetsuatsuNo2 = kakutyoKetsuatsuNo2;
	}

	/**
	 * @param kakutyoKetsuatsuOther �g�������i���̑��j
	 */
	public void setKakutyoKetsuatsuOther(double kakutyoKetsuatsuOther) {
		KakutyoKetsuatsuOther = kakutyoKetsuatsuOther;
	}
	/**
	 * @param ketsuatsuHukuyaku �����Ɋւ��Ă̕���
	 */
	public void setKetsuatsuHukuyaku(double ketsuatsuHukuyaku) {
		KetsuatsuHukuyaku = ketsuatsuHukuyaku;
	}
	public double getKetsuatsuHukuyaku() {
		return KetsuatsuHukuyaku;
	}

	/**
	 * @param kitsuen �i����
	 */
	public void setKitsuen(double kitsuen) {
		Kitsuen = kitsuen;
	}
	public double getKitsuen() {
		return Kitsuen;
	}
}
