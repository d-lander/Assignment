package supersimplestocks.domain.stock;

import java.math.BigDecimal;

import supersimplestocks.exception.SuperSimpleStocksException;

public class PreferredStockImpl extends AbstractStock {

	private BigDecimal fixedDividend;

	public PreferredStockImpl(String symbol, BigDecimal lastDividend, BigDecimal parValue, BigDecimal fixedDividend) {
		super(symbol, lastDividend, parValue);
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal getFixedDividend() {
		return this.fixedDividend;
	}

	@Override
	public BigDecimal calculateDividendYield(BigDecimal tickerPrice) {
		BigDecimal result;

		if (tickerPrice.compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal fixedDivPar = calculateAnnualDividend();
			result = fixedDivPar.divide(tickerPrice, PRICE_SCALE, PRICE_ROUNDING_MODE);
		} else {
			throw new SuperSimpleStocksException("Error - cannot calculate dividend yield with a ticker price of zero.");
		}

		return result;
	}

	@Override
	public BigDecimal calculatePERatio(BigDecimal tickerPrice) {
		BigDecimal result = null;
		BigDecimal dividend = calculateAnnualDividend();

		if (dividend.compareTo(BigDecimal.ZERO) != 0) {
			result = tickerPrice.divide(dividend, PRICE_SCALE, PRICE_ROUNDING_MODE);
		}

		return result;
	}

	private BigDecimal calculateAnnualDividend() {
		BigDecimal fixedDiv = this.fixedDividend.divide(new BigDecimal("100.00"));
		return fixedDiv.multiply(this.parValue);
	}
}
