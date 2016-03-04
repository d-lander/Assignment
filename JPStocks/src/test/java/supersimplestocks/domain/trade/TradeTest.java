package supersimplestocks.domain.trade;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TradeTest {

	private Trade target;

	private Date timestamp;

	@Before
	public void setUp() throws Exception {
		if (this.timestamp == null) {
			this.timestamp = new Date();
		}

		this.target = new Trade("TEA", this.timestamp, 500, TradeTransactionIndicator.BUY, new BigDecimal("100"));
	}

	@Test
	public void testTrade() throws Exception {
		assertEquals("TEA", this.target.getSymbol());
	}

	@Test
	public void testGetSymbol() throws Exception {
		assertEquals("TEA", this.target.getSymbol());
	}

	@Test
	public void testGetTimeStamp() throws Exception {
		assertEquals(this.timestamp, this.target.getTimeStamp());
	}

	@Test
	public void testGetQuantity() throws Exception {
		assertEquals(500, this.target.getQuantity());
	}

	@Test
	public void testGetIndicator() throws Exception {
		assertEquals(TradeTransactionIndicator.BUY, this.target.getIndicator());
	}

	@Test
	public void testGetPrice() throws Exception {
		assertEquals(new BigDecimal("100"), this.target.getPrice());
	}

}
