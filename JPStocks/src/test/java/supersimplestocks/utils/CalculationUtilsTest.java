package supersimplestocks.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import supersimplestocks.exception.SuperSimpleStocksException;

public class CalculationUtilsTest {

	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;;

	private static final int SCALE = 2;

	@Test
	public void testNthRoot() throws Exception {
		assertEquals(new BigDecimal("3.00"), CalculationUtils.nthRoot(2, new BigDecimal("9.00"), SCALE, ROUNDING_MODE));
		assertEquals(new BigDecimal("3.00"), CalculationUtils.nthRoot(3, new BigDecimal("27.00"), SCALE, ROUNDING_MODE));
		assertEquals(new BigDecimal("1.77"), CalculationUtils.nthRoot(7, new BigDecimal("54"), SCALE, ROUNDING_MODE));
		assertEquals(new BigDecimal("98.28"), CalculationUtils.nthRoot(2, new BigDecimal("9658.65"), SCALE, ROUNDING_MODE));
	}

	@Test
	public void testNthRootWithZeroBase() throws Exception {
		assertEquals(new BigDecimal("0.00"), CalculationUtils.nthRoot(2, new BigDecimal("0.00"), SCALE, ROUNDING_MODE));
	}

	@Test(expected = SuperSimpleStocksException.class)
	public void testNthRootWithNegativeBase() throws Exception {
		CalculationUtils.nthRoot(2, new BigDecimal("-1.00"), SCALE, ROUNDING_MODE);
	}
}
