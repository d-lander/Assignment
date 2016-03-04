package supersimplestocks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import supersimplestocks.domain.stock.CommonStockImpl;
import supersimplestocks.domain.stock.PreferredStockImpl;
import supersimplestocks.domain.stock.Stock;
import supersimplestocks.domain.trade.Trade;
import supersimplestocks.domain.trade.TradeTransactionIndicator;
import supersimplestocks.exception.SuperSimpleStocksException;
import supersimplestocks.repository.StockRepositoryImpl;
import supersimplestocks.utils.DateUtils;

public class StockServiceImplTest {

	private StockServiceImpl target;

	@Before
	public void setUp() throws Exception {
		this.target = new StockServiceImpl(new StockRepositoryImpl());
		this.target.initialiseStock(getStockData());
	}

	@Test
	public void testStockServiceImpl() throws Exception {
		this.target = new StockServiceImpl(new StockRepositoryImpl());
		assertEquals(0, this.target.getAllStock().size());
	}

	@Test
	public void testInitialiseStock() throws Exception {
		assertEquals(5, this.target.getAllStock().size());
	}

	@Test
	public void testCalculateDividendYield() throws Exception {
		assertEquals(new BigDecimal("0.00"), this.target.calculateDividendYield("TEA", new BigDecimal("100")));
	}

	@Test(expected = SuperSimpleStocksException.class)
	public void testCalculateDividendYieldWithZeroTickerPrice() throws Exception {
		assertEquals(new BigDecimal("0.000"), this.target.calculateDividendYield("TEA", new BigDecimal("0.00")));
	}

	@Test
	public void testCalculatePERatio() throws Exception {
		assertEquals(new BigDecimal("12.50"), this.target.calculatePERatio("POP", new BigDecimal("100")));
	}

	@Test
	public void testCalculatePERatioWithZeroDividend() throws Exception {
		assertEquals(null, this.target.calculatePERatio("TEA", new BigDecimal("100")));
	}

	@Test
	public void testRecordTrade() throws Exception {
		Date timestampBuy = new Date();
		Trade trade = new Trade("TEA", timestampBuy, 500, TradeTransactionIndicator.BUY, new BigDecimal("100"));
		this.target.recordTrade(trade);

		List<Trade> trades = this.target.getAllTrades().get("TEA");
		assertEquals(1, trades.size());

		Trade trade1 = trades.get(0);
		assertEquals("TEA", trade1.getSymbol());
		assertEquals(timestampBuy, trade1.getTimeStamp());
		assertEquals(500, trade1.getQuantity());
		assertEquals(TradeTransactionIndicator.BUY, trade1.getIndicator());
		assertEquals(new BigDecimal("100"), trade1.getPrice());
	}

	@Test
	public void testGetAllStock() throws Exception {
		assertEquals(5, this.target.getAllStock().size());
	}

	@Test
	public void testCalculateStockPrice() throws Exception {
		this.target.recordTrade(new Trade("TEA", DateUtils.getDateOneHourOld(), 5000, TradeTransactionIndicator.BUY, new BigDecimal("100.00")));
		this.target.recordTrade(new Trade("TEA", new Date(), 1100, TradeTransactionIndicator.BUY, new BigDecimal("110.00")));
		this.target.recordTrade(new Trade("TEA", new Date(), 900, TradeTransactionIndicator.SELL, new BigDecimal("105.00")));

		assertEquals(new BigDecimal("107.75"), this.target.calculateStockPrice("TEA"));
	}

	@Test
	public void testCalculateStockPriceWhereNoTradesInLastFifteenMins() throws Exception {
		this.target.recordTrade(new Trade("TEA", DateUtils.getDateOneHourOld(), 5000, TradeTransactionIndicator.BUY, new BigDecimal("100.00")));
		this.target.recordTrade(new Trade("TEA", DateUtils.getDateOneHourOld(), 1100, TradeTransactionIndicator.BUY, new BigDecimal("110.00")));

		assertNull(this.target.calculateStockPrice("TEA"));
	}

	@Test
	public void testCalculateAllShareIndex() {
		this.target.recordTrade(new Trade("TEA", DateUtils.getDateOneHourOld(), 500, TradeTransactionIndicator.BUY, new BigDecimal("100.00")));
		this.target.recordTrade(new Trade("TEA", new Date(), 600, TradeTransactionIndicator.BUY, new BigDecimal("105.00")));
		this.target.recordTrade(new Trade("TEA", new Date(), 300, TradeTransactionIndicator.SELL, new BigDecimal("95.00")));

		this.target.recordTrade(new Trade("POP", DateUtils.getDateOneHourOld(), 200, TradeTransactionIndicator.BUY, new BigDecimal("100.00")));
		this.target.recordTrade(new Trade("POP", new Date(), 100, TradeTransactionIndicator.BUY, new BigDecimal("110.00")));
		this.target.recordTrade(new Trade("POP", new Date(), 300, TradeTransactionIndicator.SELL, new BigDecimal("90.00")));

		assertEquals(new BigDecimal("98.28"), this.target.calculateAllShareIndex());
	}

	@Test
	public void testGetTickerPrice() throws Exception {
		this.target.recordTrade(new Trade("JOE", new Date(), 1000, TradeTransactionIndicator.BUY, new BigDecimal("250.00")));
		this.target.recordTrade(new Trade("JOE", new Date(), 1100, TradeTransactionIndicator.BUY, new BigDecimal("270.00")));
		this.target.recordTrade(new Trade("JOE", new Date(), 900, TradeTransactionIndicator.SELL, new BigDecimal("260.00")));

		assertEquals(new BigDecimal("270.00"), this.target.getTickerPrice("JOE"));
	}

	@Test
	public void testGetTickerPriceWhenNoTrades() throws Exception {
		assertEquals(new BigDecimal("100.00"), this.target.getTickerPrice("TEA"));
	}

	private List<Stock> getStockData() {
		return Arrays.asList(new Stock[] {
				new CommonStockImpl("TEA", new BigDecimal("0.00"), new BigDecimal("100.00")),
				new CommonStockImpl("POP", new BigDecimal("8.00"), new BigDecimal("100.00")),
				new CommonStockImpl("ALE", new BigDecimal("23.00"), new BigDecimal("60.00")),
				new PreferredStockImpl("GIN", new BigDecimal("8.00"), new BigDecimal("2.00"), new BigDecimal("100.00")),
				new CommonStockImpl("JOE", new BigDecimal("8.00"), new BigDecimal("100.00")),
		});
	}
}
