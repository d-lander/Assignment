package supersimplestocks.domain.stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import supersimplestocks.domain.trade.Trade;

public interface Stock {

	RoundingMode PRICE_ROUNDING_MODE = RoundingMode.HALF_EVEN;

	int PRICE_SCALE = 2;

	String getSymbol();

	BigDecimal getLastDividend();

	BigDecimal getParValue();

	BigDecimal calculateDividendYield(BigDecimal tickerPrice);

	BigDecimal calculatePERatio(BigDecimal tickerPrice);

	BigDecimal calculateStockPrice(List<Trade> trades);

}
