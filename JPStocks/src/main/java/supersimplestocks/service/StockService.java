package supersimplestocks.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import supersimplestocks.domain.stock.Stock;
import supersimplestocks.domain.trade.Trade;

public interface StockService {

	BigDecimal calculateDividendYield(String stockSymbol, BigDecimal tickerPrice);

	BigDecimal calculatePERatio(String stockSymbol, BigDecimal tickerPrice);

	void recordTrade(Trade trade);

	BigDecimal calculateStockPrice(String stockSymbol);

	BigDecimal calculateAllShareIndex();

	BigDecimal getTickerPrice(String stockSymbol);

	void initialiseStock(List<Stock> stocks);

	Map<String, Stock> getAllStock();

	Map<String, List<Trade>> getAllTrades();
}
