package jp.or.med.orca.jma_tokutei.common.convert;

import java.util.Calendar;

public class JYearAge {
	/**
	 * 生年月日から年齢を求める
	 * 
	 * @param birthDay
	 *            生年月日(YYYYMMDD)
	 * @return 年齢
	 */
	public static String getAge(String birthDay) {

		float birthDayValue = -1f;

		try {
			birthDayValue = new Float(birthDay);
		} catch (NumberFormatException e) {
		}

		if (birthDayValue < 0)
			return "";

		Calendar calendar = Calendar.getInstance();
		float currentDate = new Float(String.format("%d%02d%02d", calendar
				.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar
				.get(Calendar.DAY_OF_MONTH)));

		Integer ageValue = Math.round((currentDate - birthDayValue) / 10000);
		return ageValue.toString();
	}
}
