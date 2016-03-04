package supersimplestocks.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static int MINUS_ONE_HOUR = -1;

	private static int MINUS_FIFTEEN_MINUTES = -15;


	public static Date addDate(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static Date getDateOneHourOld() {
		return DateUtils.addDate(new Date(), Calendar.HOUR, MINUS_ONE_HOUR);
	}

	public static boolean isDateOlderThanFifteenMins(Date date) {
		boolean result = false;
		Date nowMinusFifteenMins = DateUtils.addDate(new Date(), Calendar.MINUTE, MINUS_FIFTEEN_MINUTES);
		int comp = date.compareTo(nowMinusFifteenMins);

		if (comp < 0) {
			result = true;
		}

		return result;
	}
}
