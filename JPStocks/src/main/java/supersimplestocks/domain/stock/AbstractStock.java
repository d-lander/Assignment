package supersimplestocks.domain.stock;

import java.math.BigDecimal;
import java.util.List;

import supersimplestocks.domain.trade.Trade;

public abstract class AbstractStock implements Stock {

	protected String symbol;

	protected BigDecimal lastDividend;

	protected BigDecimal parValue;

	protected AbstractStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
		super();
		this.symbol = symbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
	}

	@Override
	public String getSymbol() {
		return this.symbol;
	}

	@Override
	public BigDecimal getLastDividend() {
		return this.lastDividend;
	}

	@Override
	public BigDecimal getParValue() {
		return this.parValue;
	}

	@Override
	public BigDecimal calculateStockPrice(List<Trade> trades) {
		BigDecimal stockPrice = null;

		if (trades != null) {
			BigDecimal sumOfPriceQuantity = new BigDecimal("0.00");
			BigDecimal sumOfQunatity = new BigDecimal("0.00");

			for (Trade trade : trades) {
				BigDecimal quantity = new BigDecimal(trade.getQuantity());
				sumOfPriceQuantity = sumOfPriceQuantity.add(trade.getPrice().multiply(quantity));
				sumOfQunatity = sumOfQunatity.add(quantity);
			}

			if (sumOfQunatity.compareTo(BigDecimal.ZERO) != 0) {
				stockPrice = sumOfPriceQuantity.divide(sumOfQunatity, Stock.PRICE_SCALE, Stock.PRICE_ROUNDING_MODE);
			}
		}

		return stockPrice;
	}
}
