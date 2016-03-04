package supersimplestocks.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import supersimplestocks.domain.index.GBCEAllShareIndexImpl;
import supersimplestocks.domain.index.ShareIndex;
import supersimplestocks.domain.stock.Stock;
import supersimplestocks.domain.trade.Trade;
import supersimplestocks.domain.trade.TradeTransactionIndicator;
import supersimplestocks.repository.StockRepository;
import supersimplestocks.repository.StockRepositoryImpl;

public class StockServiceImpl implements StockService {

	StockRepository stockRepository;

	public StockServiceImpl(StockRepository stockRepository) {
		super();
		this.stockRepository = new StockRepositoryImpl();
	}

	@Override
	public void initialiseStock(List<Stock> stocks) {
		this.stockRepository.initialiseStock(stocks);
	}

	@Override
	public BigDecimal calculateDividendYield(String stockSymbol, BigDecimal tickerPrice) {
		return this.stockRepository.getStock(stockSymbol).calculateDividendYield(tickerPrice);
	}

	@Override
	public BigDecimal calculatePERatio(String stockSymbol, BigDecimal tickerPrice) {
		return this.stockRepository.getStock(stockSymbol).calculatePERatio(tickerPrice);
	}

	@Override
	public void recordTrade(Trade trade) {
		this.stockRepository.recordTrade(trade);
	}

	@Override
	public BigDecimal calculateStockPrice(String stockSymbol) {
		Stock stock = this.stockRepository.getStock(stockSymbol);
		return stock.calculateStockPrice(this.stockRepository.getTradesInPastFifteenMins(stockSymbol));
	}

	/**
	 * Assumption: the All Share Index is calculated from stock prices based on trades recorded in the
	 * last 15 minutes.
	 */
	@Override
	public BigDecimal calculateAllShareIndex() {
		ShareIndex shareIndex = new GBCEAllShareIndexImpl(getAllStockPrices(getAllStock()));
		return shareIndex.calculateAllShareIndexValue();
	}

	@Override
	public Map <String, Stock> getAllStock() {
		return this.stockRepository.getAllStock();
	}

	@Override
	public Map <String, List<Trade>> getAllTrades() {
		return this.stockRepository.getAllTrades();
	}

	/**
	 * Assumption: the ticker price is price from the last recorded sale - i.e. the last trade with
	 * a BUY indicator. Par value is used if no sale is recorded.
	 */
	@Override
	public BigDecimal getTickerPrice(String stockSymbol) {
		BigDecimal result = null;
		Trade trade = this.stockRepository.getLatestTrade(stockSymbol, TradeTransactionIndicator.BUY);

		if (trade != null) {
			result = trade.getPrice();
		} else {
			result = this.stockRepository.getStock(stockSymbol).getParValue();
		}

		return result;
	}

	private List<BigDecimal> getAllStockPrices(Map<String, Stock> allStocks) {
		List<BigDecimal> allStockPrices = new ArrayList<BigDecimal>();

		for (String stockSymbol : allStocks.keySet()) {
			BigDecimal stockPrice = calculateStockPrice(stockSymbol);

			if (stockPrice != null) {
				allStockPrices.add(stockPrice);
			}
		}

		return allStockPrices;
	}
}
