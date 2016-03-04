package supersimplestocks.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SuperSimpleStocksExceptionTest {

	private SuperSimpleStocksException target;

	@Test
	public void testSuperSimpleStocksException() throws Exception {
		this.target = new SuperSimpleStocksException("test message");

		assertEquals("test message", this.target.getMessage());
	}

}
