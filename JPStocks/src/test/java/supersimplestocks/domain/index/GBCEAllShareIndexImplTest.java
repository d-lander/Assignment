package supersimplestocks.domain.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GBCEAllShareIndexImplTest {

	private GBCEAllShareIndexImpl target;

	@Test
	public void testCalculateAllShareIndexValue() throws Exception {
		List<BigDecimal> allStockPrices = new ArrayList<BigDecimal>();
		allStockPrices.add(new BigDecimal("101.67"));
		allStockPrices.add(new BigDecimal("95.00"));
		allStockPrices.add(new BigDecimal("74.71"));

		this.target = new GBCEAllShareIndexImpl(allStockPrices);

		assertEquals(new BigDecimal("89.69"), this.target.calculateAllShareIndexValue());
	}

	@Test
	public void testCalculateAllShareIndexValueWithNoStockPrices() throws Exception {
		List<BigDecimal> allStockPrices = new ArrayList<BigDecimal>();

		this.target = new GBCEAllShareIndexImpl(allStockPrices);

		assertNull(this.target.calculateAllShareIndexValue());
	}
}
