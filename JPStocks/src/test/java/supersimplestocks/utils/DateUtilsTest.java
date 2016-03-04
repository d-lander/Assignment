package supersimplestocks.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testAddDate() throws Exception {
		Date date = new Date();
		Date result = DateUtils.addDate(date, Calendar.MINUTE, -1);
		assertEquals(60000, date.getTime() - result.getTime());
	}

	@Test
	public void testGetDateOneHourOld() throws Exception {
		Date date = new Date();
		Date result = DateUtils.addDate(date, Calendar.HOUR, -1);
		assertEquals(3600000, date.getTime() - result.getTime());
	}

	@Test
	public void testIsDateOlder() throws Exception {
		Date date = new Date();
		assertFalse(DateUtils.isDateOlderThanFifteenMins(new Date()));

		date = DateUtils.addDate(new Date(), Calendar.MINUTE, -15);
		assertFalse(DateUtils.isDateOlderThanFifteenMins(date));

		date = DateUtils.addDate(date, Calendar.SECOND, -1);
		assertTrue(DateUtils.isDateOlderThanFifteenMins(date));
	}
}
