package supersimplestocks.domain.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import supersimplestocks.domain.trade.Trade;
import supersimplestocks.domain.trade.TradeTransactionIndicator;
import supersimplestocks.exception.SuperSimpleStocksException;

public class CommonStockImplTest {

	private CommonStockImpl target;

	@Before
	public void setUp() throws Exception {
		this.target = new CommonStockImpl("JOE", new BigDecimal("13.00"), new BigDecimal("250.00"));
	}

	@Test
	public void testCommonStockImpl() throws Exception {
		assertEquals("JOE", this.target.getSymbol());
		assertEquals(new BigDecimal("13.00"), this.target.getLastDividend());
		assertEquals(new BigDecimal("250.00"), this.target.getParValue());
	}

	@Test
	public void testCalculateDividendYield() throws Exception {
		assertEquals(new BigDecimal("0.13"), this.target.calculateDividendYield(new BigDecimal("100.00")));
	}

	@Test(expected = SuperSimpleStocksException.class)
	public void testCalculateDividendYieldWithZeroTickerPrice() throws Exception {
		this.target.calculateDividendYield(new BigDecimal("0.00"));
	}

	@Test
	public void testCalculatePERatio() throws Exception {
		assertEquals(new BigDecimal("7.69"), this.target.calculatePERatio(new BigDecimal("100.00")));
	}

	@Test
	public void testCalculatePERatioWithZeroDividend() throws Exception {
		this.target = new CommonStockImpl("JOE", new BigDecimal("0.00"), new BigDecimal("250.00"));
		assertNull(this.target.calculatePERatio(new BigDecimal("100.00")));
	}

	@Test
	public void testCalculateStockPrice() throws Exception {

		List<Trade> trades = Arrays.asList(new Trade[] {
				new Trade("TEA", new Date(), 1100, TradeTransactionIndicator.BUY, new BigDecimal("110.00")),
				new Trade("TEA", new Date(), 900, TradeTransactionIndicator.SELL, new BigDecimal("105.00"))
		});

		assertEquals(new BigDecimal("107.75"), this.target.calculateStockPrice(trades));
	}

	@Test
	public void testCalculateStockPriceWhereNoTrades() throws Exception {
		assertNull(this.target.calculateStockPrice(null));
		assertNull(this.target.calculateStockPrice(new ArrayList<Trade>()));
	}
}
