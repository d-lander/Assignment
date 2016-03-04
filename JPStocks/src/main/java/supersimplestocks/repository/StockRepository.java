package supersimplestocks.repository;

import java.util.List;
import java.util.Map;

import supersimplestocks.domain.stock.Stock;
import supersimplestocks.domain.trade.Trade;
import supersimplestocks.domain.trade.TradeTransactionIndicator;

public interface StockRepository {

	void initialiseStock(List<Stock> stocks);

	Map<String, Stock> getAllStock();

	Map<String, List<Trade>> getAllTrades();

	List<Trade> getTradesInPastFifteenMins(String symbol);

	Trade getLatestTrade(String symbol, TradeTransactionIndicator indicator);

	Stock getStock(String symbol);

	void recordTrade(Trade trade);
}
