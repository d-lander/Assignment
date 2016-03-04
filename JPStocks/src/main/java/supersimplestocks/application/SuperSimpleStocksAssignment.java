package supersimplestocks.application;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import supersimplestocks.domain.stock.CommonStockImpl;
import supersimplestocks.domain.stock.PreferredStockImpl;
import supersimplestocks.domain.stock.Stock;
import supersimplestocks.domain.trade.Trade;
import supersimplestocks.domain.trade.TradeTransactionIndicator;
import supersimplestocks.repository.StockRepositoryImpl;
import supersimplestocks.service.StockService;
import supersimplestocks.service.StockServiceImpl;
import supersimplestocks.utils.DateUtils;


public class SuperSimpleStocksAssignment {

	private StockService stockService;

	public SuperSimpleStocksAssignment() {
		super();
		this.stockService = new StockServiceImpl(new StockRepositoryImpl());
	}

	public static void main(String[] args) {
		SuperSimpleStocksAssignment app = new SuperSimpleStocksAssignment();
		app.populateSampleData();
		app.process();
	}

	private void populateSampleData() {
		this.stockService.initialiseStock(Arrays.asList(new Stock[] {
				new CommonStockImpl("TEA", new BigDecimal("0.00"), new BigDecimal("100.00")),
				new CommonStockImpl("POP", new BigDecimal("8.00"), new BigDecimal("100.00")),
				new CommonStockImpl("ALE", new BigDecimal("23.00"), new BigDecimal("60.00")),
				new PreferredStockImpl("GIN", new BigDecimal("8.00"), new BigDecimal("2.00"), new BigDecimal("100.00")),
				new CommonStockImpl("JOE", new BigDecimal("13.00"), new BigDecimal("100.00")),
		}));
	}

	private void process() {
		System.out.println("Super Simple Stocks");
		calculateDividendYields();
		calculatePERatios();
		recordTrades();
		calculateStockPrices();
		calculateAllShareIndex();
		calculateDividendYieldsUsingTickerPriceFromTrades();
		calculatePERatiosUsingTickerPriceFromTrades();
	}

	private void calculateDividendYields() {
		System.out.println("\n\nCalculated Dividend Yields:\n");
		displayOutput("Dividend yield (where ticker price = 100) for TEA = ", this.stockService.calculateDividendYield("TEA", new BigDecimal("100.00")));
		displayOutput("Dividend yield (where ticker price = 100) for POP = ", this.stockService.calculateDividendYield("POP", new BigDecimal("100.00")));
		displayOutput("Dividend yield (where ticker price = 100) for ALE = ", this.stockService.calculateDividendYield("ALE", new BigDecimal("100.00")));
		displayOutput("Dividend yield (where ticker price = 100) for GIN = ", this.stockService.calculateDividendYield("GIN", new BigDecimal("100.00")));
		displayOutput("Dividend yield (where ticker price = 100) for JOE = ", this.stockService.calculateDividendYield("JOE", new BigDecimal("100.00")));
	}

	private void calculatePERatios() {
		System.out.println("\n\nCalculated P/E Ratios:\n");
		displayOutput("P/E Ratio (where ticker price = 100) for TEA = ", this.stockService.calculatePERatio("TEA", new BigDecimal("100.00")));
		displayOutput("P/E Ratio (where ticker price = 100) for POP = ", this.stockService.calculatePERatio("POP", new BigDecimal("100.00")));
		displayOutput("P/E Ratio (where ticker price = 100) for ALE = ", this.stockService.calculatePERatio("ALE", new BigDecimal("100.00")));
		displayOutput("P/E Ratio (where ticker price = 100) for GIN = ", this.stockService.calculatePERatio("GIN", new BigDecimal("100.00")));
		displayOutput("P/E Ratio (where ticker price = 100) for JOE = ", this.stockService.calculatePERatio("JOE", new BigDecimal("100.00")));
	}

	private void recordTrades() {
		Date dateOlderThanFifteenMins = DateUtils.getDateOneHourOld();

		this.stockService.recordTrade(new Trade("TEA", dateOlderThanFifteenMins, 500, TradeTransactionIndicator.BUY, new BigDecimal("100.00")));
		this.stockService.recordTrade(new Trade("TEA", new Date(), 600, TradeTransactionIndicator.BUY, new BigDecimal("105.00")));
		this.stockService.recordTrade(new Trade("TEA", new Date(), 300, TradeTransactionIndicator.SELL, new BigDecimal("95.00")));

		System.out.println("\n\nTrades for TEA:\n");
		displayTrades("TEA");

		this.stockService.recordTrade(new Trade("POP", dateOlderThanFifteenMins, 200, TradeTransactionIndicator.BUY, new BigDecimal("100.00")));
		this.stockService.recordTrade(new Trade("POP", new Date(), 100, TradeTransactionIndicator.BUY, new BigDecimal("110.00")));
		this.stockService.recordTrade(new Trade("POP", new Date(), 300, TradeTransactionIndicator.SELL, new BigDecimal("90.00")));

		System.out.println("\n\nTrades for POP:\n");
		displayTrades("POP");

		this.stockService.recordTrade(new Trade("ALE", dateOlderThanFifteenMins, 700, TradeTransactionIndicator.BUY, new BigDecimal("60.00")));
		this.stockService.recordTrade(new Trade("ALE", new Date(), 800, TradeTransactionIndicator.BUY, new BigDecimal("80.00")));
		this.stockService.recordTrade(new Trade("ALE", new Date(), 900, TradeTransactionIndicator.SELL, new BigDecimal("70.00")));

		System.out.println("\n\nTrades for ALE:\n");
		displayTrades("ALE");

		this.stockService.recordTrade(new Trade("GIN", dateOlderThanFifteenMins, 200, TradeTransactionIndicator.BUY, new BigDecimal("110.00")));
		this.stockService.recordTrade(new Trade("GIN", new Date(), 100, TradeTransactionIndicator.BUY, new BigDecimal("105.00")));
		this.stockService.recordTrade(new Trade("GIN", new Date(), 300, TradeTransactionIndicator.SELL, new BigDecimal("95.00")));

		System.out.println("\n\nTrades for GIN:\n");
		displayTrades("GIN");

		this.stockService.recordTrade(new Trade("JOE", dateOlderThanFifteenMins, 1000, TradeTransactionIndicator.BUY, new BigDecimal("250.00")));
		this.stockService.recordTrade(new Trade("JOE", new Date(), 1100, TradeTransactionIndicator.BUY, new BigDecimal("270.00")));
		this.stockService.recordTrade(new Trade("JOE", new Date(), 900, TradeTransactionIndicator.SELL, new BigDecimal("260.00")));

		System.out.println("\n\nTrades for JOE:\n");
		displayTrades("JOE");
	}

	private void calculateStockPrices() {
		System.out.println("\n\nCalculated Stock Prices, (based on trades recorded in the past 15 minutes) :\n");
		displayOutput("Stock Price for TEA = ", this.stockService.calculateStockPrice("TEA"));
		displayOutput("Stock Price for POP = ", this.stockService.calculateStockPrice("POP"));
		displayOutput("Stock Price for ALE = ", this.stockService.calculateStockPrice("ALE"));
		displayOutput("Stock Price for GIN = ", this.stockService.calculateStockPrice("GIN"));
		displayOutput("Stock Price for JOE = ", this.stockService.calculateStockPrice("JOE"));
	}

	private void calculateAllShareIndex() {
		System.out.println("\n\nCalculated GBCE All Share Price Index:\n");
		displayOutput("GBCE All Share Price Index = ", this.stockService.calculateAllShareIndex());
	}

	private void calculateDividendYieldsUsingTickerPriceFromTrades() {
		System.out.println("\n\nCalculated Dividend Yields using the Ticker Price from recorded trades:\n");

		BigDecimal tickerPrice = this.stockService.getTickerPrice("TEA");
		displayOutput("Dividend yield (where ticker price = " + tickerPrice+ ") for TEA = ", this.stockService.calculateDividendYield("TEA", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("POP");
		displayOutput("Dividend yield (where ticker price = " + tickerPrice+ ") for POP = ", this.stockService.calculateDividendYield("POP", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("ALE");
		displayOutput("Dividend yield (where ticker price = " + tickerPrice+ ") for ALE = ", this.stockService.calculateDividendYield("ALE", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("GIN");
		displayOutput("Dividend yield (where ticker price = " + tickerPrice+ ") for GIN = ", this.stockService.calculateDividendYield("GIN", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("JOE");
		displayOutput("Dividend yield (where ticker price = " + tickerPrice+ ") for JOE = ", this.stockService.calculateDividendYield("JOE", tickerPrice));
	}

	private void calculatePERatiosUsingTickerPriceFromTrades() {
		System.out.println("\n\nCalculated P/E Ratios using the Ticker Price from recorded trades:\n");

		BigDecimal tickerPrice = this.stockService.getTickerPrice("TEA");
		displayOutput("P/E Ratio (where ticker price = " + tickerPrice+ ") for TEA = ",	this.stockService.calculatePERatio("TEA", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("POP");
		displayOutput("P/E Ratio (where ticker price = " + tickerPrice+ ") for POP = ",	this.stockService.calculatePERatio("POP", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("ALE");
		displayOutput("P/E Ratio (where ticker price = " + tickerPrice+ ") for ALE = ",	this.stockService.calculatePERatio("ALE", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("GIN");
		displayOutput("P/E Ratio (where ticker price = " + tickerPrice+ ") for GIN = ",	this.stockService.calculatePERatio("GIN", tickerPrice));

		tickerPrice = this.stockService.getTickerPrice("JOE");
		displayOutput("P/E Ratio (where ticker price = " + tickerPrice+ ") for JOE = ",	this.stockService.calculatePERatio("JOE", tickerPrice));

	}

	private void displayOutput(String prefix, Object expression) {
		String output = prefix;

		if (expression != null) {
			output += expression;
		} else {
			output += "Unavailable";
		}

		System.out.println(output);
	}

	private void displayTrades(String symbol) {
		for (Trade trade : this.stockService.getAllTrades().get(symbol)) {
			String output = "Symbol=" + trade.getSymbol() +
					" TimeStamp=" + trade.getTimeStamp() +
					" Quantity=" + trade.getQuantity() +
					" Indicator=" + trade.getIndicator() +
					" Price=" + trade.getPrice();
			System.out.println(output);
		}
	}
}
