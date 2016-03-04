package supersimplestocks.repository;

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
import supersimplestocks.utils.DateUtils;

public class StockRepositoryImplTest {

	private StockRepositoryImpl target;

	@Before
	public void setUp() throws Exception {
		this.target = new StockRepositoryImpl();
		this.target.initialiseStock(getStockData());
	}

	@Test
	public void testStockRepositoryImpl() throws Exception {
		this.target = new StockRepositoryImpl();
		assertEquals(0, this.target.getAllStock().size());
		assertEquals(0, this.target.getAllTrades().size());
	}

	@Test
	public void testInitialiseStock() throws Exception {
		assertEquals(5, this.target.getAllStock().size());
		assertEquals(5, this.target.getAllTrades().size());
	}

	@Test
	public void testGetAllStock() throws Exception {
		assertEquals(5, this.target.getAllStock().size());
	}

	@Test
	public void testGetAllTrades() throws Exception {
		assertEquals(5, this.target.getAllTrades().size());
	}

	@Test
	public void testGetStock() throws Exception {
		Stock stock = this.target.getStock("TEA");
		assertEquals(new BigDecimal(0), stock.getLastDividend());
		assertEquals(new BigDecimal(100), stock.getParValue());
	}

	@Test
	public void testRecordTrade() throws Exception {
		Date timestampBuy = new Date();
		Trade trade = new Trade("TEA", timestampBuy, 500, TradeTransactionIndicator.BUY, new BigDecimal("100"));
		this.target.recordTrade(trade);

		Date timestampSell = new Date();
		trade = new Trade("TEA", timestampSell, 750, TradeTransactionIndicator.SELL, new BigDecimal("101"));
		this.target.recordTrade(trade);

		List<Trade> trades = this.target.getAllTrades().get("TEA");
		assertEquals(2, trades.size());

		Trade trade1 = trades.get(0);
		assertEquals("TEA", trade1.getSymbol());
		assertEquals(timestampBuy, trade1.getTimeStamp());
		assertEquals(500, trade1.getQuantity());
		assertEquals(TradeTransactionIndicator.BUY, trade1.getIndicator());
		assertEquals(new BigDecimal("100"), trade1.getPrice());

		Trade trade2 = trades.get(1);
		assertEquals("TEA", trade2.getSymbol());
		assertEquals(timestampSell, trade2.getTimeStamp());
		assertEquals(750, trade2.getQuantity());
		assertEquals(TradeTransactionIndicator.SELL, trade2.getIndicator());
		assertEquals(new BigDecimal("101"), trade2.getPrice());
	}

	@Test(expected = SuperSimpleStocksException.class)
	public void testRecordTradeStockNotFound() throws Exception {

		Trade trade = new Trade("XXX", new Date(), 500, TradeTransactionIndicator.BUY, new BigDecimal("100"));
		this.target.recordTrade(trade);
	}

	private List<Stock> getStockData() {
		return Arrays.asList(new Stock[] {
				new CommonStockImpl("TEA", new BigDecimal(0), new BigDecimal(100)),
				new CommonStockImpl("POP", new BigDecimal(8), new BigDecimal(100)),
				new CommonStockImpl("ALE", new BigDecimal(23), new BigDecimal(60)),
				new PreferredStockImpl("GIN", new BigDecimal(8), new BigDecimal(2), new BigDecimal(100)),
				new CommonStockImpl("JOE", new BigDecimal(8), new BigDecimal(100)),
		});
	}

	@Test
	public void testGetTradesInPastFifteenMins() throws Exception {
		this.target.recordTrade(new Trade("JOE", DateUtils.getDateOneHourOld(), 1000, TradeTransactionIndicator.BUY, new BigDecimal("250.00")));
		this.target.recordTrade(new Trade("JOE", new Date(), 1100, TradeTransactionIndicator.BUY, new BigDecimal("270.00")));
		this.target.recordTrade(new Trade("JOE", new Date(), 900, TradeTransactionIndicator.SELL, new BigDecimal("260.00")));

		assertEquals(2, this.target.getTradesInPastFifteenMins("JOE").size());
	}

	@Test
	public void testGetLatestTrade() throws Exception {
		this.target.recordTrade(new Trade("JOE", new Date(), 1000, TradeTransactionIndicator.BUY, new BigDecimal("250.00")));
		this.target.recordTrade(new Trade("JOE", new Date(), 1100, TradeTransactionIndicator.BUY, new BigDecimal("270.00")));
		this.target.recordTrade(new Trade("JOE", new Date(), 900, TradeTransactionIndicator.SELL, new BigDecimal("260.00")));

		Trade result = this.target.getLatestTrade("JOE", TradeTransactionIndicator.BUY);
		assertEquals(TradeTransactionIndicator.BUY, result.getIndicator());
		assertEquals(1100, result.getQuantity());

		result = this.target.getLatestTrade("JOE", TradeTransactionIndicator.SELL);
		assertEquals(TradeTransactionIndicator.SELL, result.getIndicator());
		assertEquals(900, result.getQuantity());
	}

	@Test
	public void testGetLatestTradeWhereNoTradesAvailable() throws Exception {
		assertNull(this.target.getLatestTrade("JOE", TradeTransactionIndicator.BUY));
	}
}
