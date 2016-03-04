package supersimplestocks.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import supersimplestocks.exception.SuperSimpleStocksException;

public class CalculationUtils {

	/**
	 * Calculate the nth root.
	 *
	 * Would use: (base)^(1/exponent) but the BigDecimal pow method only accepts an int parameter.
	 */
	public static BigDecimal nthRoot(final int exponent, final BigDecimal base, int scale, RoundingMode roundingMode) {

		if (base.compareTo(BigDecimal.ZERO) < 0) {
			throw new SuperSimpleStocksException("Error - nth root can only be calculated for positive numbers by this method");
		}

		if (base.compareTo(BigDecimal.ZERO) == 0) {
			return base;
		}

		BigDecimal result = base.divide(new BigDecimal(exponent), scale, roundingMode);
		BigDecimal movePointLeftScale = BigDecimal.valueOf(0.1).movePointLeft(scale);
		BigDecimal previousResult = base;

		while (result.subtract(previousResult).abs().compareTo(movePointLeftScale) > 0) {
			previousResult = result;
			result = BigDecimal.valueOf(exponent - 1.0)
					.multiply(result)
					.add(base.divide(result.pow(exponent - 1), scale, roundingMode))
					.divide(new BigDecimal(exponent), scale, roundingMode);
		}

		return result;
	}
}
