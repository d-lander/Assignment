package supersimplestocks.domain.stock;

import java.math.BigDecimal;

import supersimplestocks.exception.SuperSimpleStocksException;

public class CommonStockImpl extends AbstractStock {

	public CommonStockImpl(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
		super(symbol, lastDividend, parValue);
	}

	@Override
	public BigDecimal calculateDividendYield(BigDecimal tickerPrice) {
		BigDecimal result;

		if (tickerPrice.compareTo(BigDecimal.ZERO) != 0) {
			result = this.lastDividend.divide(tickerPrice, PRICE_SCALE, PRICE_ROUNDING_MODE);
		} else {
			throw new SuperSimpleStocksException("Error - cannot calculate dividend yield with a ticker price of zero.");
		}

		return result;
	}

	@Override
	public BigDecimal calculatePERatio(BigDecimal tickerPrice) {
		BigDecimal result = null;

		if (this.getLastDividend().compareTo(BigDecimal.ZERO) != 0) {
			result = tickerPrice.divide(this.getLastDividend(), PRICE_SCALE, PRICE_ROUNDING_MODE);
		}

		return result;
	}
}
