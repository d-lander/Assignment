package supersimplestocks.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import supersimplestocks.domain.stock.Stock;
import supersimplestocks.domain.trade.Trade;
import supersimplestocks.domain.trade.TradeTransactionIndicator;
import supersimplestocks.exception.SuperSimpleStocksException;
import supersimplestocks.utils.DateUtils;

public class StockRepositoryImpl implements StockRepository {

	private Map <String, Stock> stockMap = new HashMap<String, Stock>();

	private Map <String, List<Trade>> tradesMap = new HashMap<String, List<Trade>>();

	public StockRepositoryImpl() {
		super();
	}

	@Override
	public void initialiseStock(List<Stock> stocks) {
		for (Stock stock : stocks) {
			String stockSymbol = stock.getSymbol();
			this.stockMap.put(stockSymbol, stock);
			this.tradesMap.put(stockSymbol, new ArrayList<Trade>());
		}
	}

	@Override
	public Map <String, Stock> getAllStock() {
		return this.stockMap;
	}

	@Override
	public Map <String, List<Trade>> getAllTrades() {
		return this.tradesMap;
	}

	@Override
	public Stock getStock(String symbol) {
		return this.stockMap.get(symbol);
	}

	@Override
	public List<Trade> getTradesInPastFifteenMins(String symbol) {
		List<Trade> result = new ArrayList<Trade>();

		for (Trade trade : this.tradesMap.get(symbol)) {
			if (!DateUtils.isDateOlderThanFifteenMins(trade.getTimeStamp())) {
				result.add(trade);
			}
		}

		return result;
	}

	@Override
	public Trade getLatestTrade(String stockSymbol, TradeTransactionIndicator indicator) {
		Trade result = null;
		List<Trade> stockTrades = this.tradesMap.get(stockSymbol);

		for (int i = stockTrades.size() - 1; i >= 0; i--) {
			Trade trd = stockTrades.get(i);

			if (trd.getIndicator() == indicator) {
				result = trd;
				break;
			}
		}

		return result;
	}

	@Override
	public void recordTrade(Trade trade) {
		String stockSymbol = trade.getSymbol();
		List<Trade> stockTrades = this.tradesMap.get(stockSymbol);

		if (stockTrades != null) {
			stockTrades.add(trade);
		} else {
			throw new SuperSimpleStocksException("This trade cannot be recorded as the stock is not known for symbol = " + stockSymbol);
		}
	}
}
