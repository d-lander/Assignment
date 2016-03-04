package supersimplestocks.domain.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import supersimplestocks.exception.SuperSimpleStocksException;

public class PreferredStockImplTest {

	private PreferredStockImpl target;

	@Before
	public void setUp() throws Exception {
		this.target = new PreferredStockImpl("GIN", new BigDecimal("8.00"), new BigDecimal("100.00"), new BigDecimal("2.00"));
	}

	@Test
	public void testPreferredStockImpl() throws Exception {
		assertEquals("GIN", this.target.getSymbol());
		assertEquals(new BigDecimal("8.00"), this.target.getLastDividend());
		assertEquals(new BigDecimal("100.00"), this.target.getParValue());
		assertEquals(new BigDecimal("2.00"), this.target.getFixedDividend());
	}

	@Test
	public void testCalculateDividendYield() throws Exception {
		assertEquals(new BigDecimal("0.02"), this.target.calculateDividendYield(new BigDecimal("100.00")));
	}

	@Test(expected = SuperSimpleStocksException.class)
	public void testCalculateDividendYieldWithZeroTickerPrice() throws Exception {
		this.target.calculateDividendYield(new BigDecimal("0.00"));
	}

	@Test
	public void testCalculatePERatio() throws Exception {
		assertEquals(new BigDecimal("50.00"), this.target.calculatePERatio(new BigDecimal("100.00")));
	}

	@Test
	public void testCalculatePERatioWithZeroDividend() throws Exception {
		this.target = new PreferredStockImpl("GIN", new BigDecimal("8.00"), new BigDecimal("100.00"), new BigDecimal("0.00"));
		assertNull(this.target.calculatePERatio(new BigDecimal("100.00")));
	}
}
