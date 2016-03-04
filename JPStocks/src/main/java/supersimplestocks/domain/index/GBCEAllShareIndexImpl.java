package supersimplestocks.domain.index;

import java.math.BigDecimal;
import java.util.List;

import supersimplestocks.domain.stock.Stock;
import supersimplestocks.utils.CalculationUtils;

public class GBCEAllShareIndexImpl implements ShareIndex {

	private List<BigDecimal> allStockPrices;

	public GBCEAllShareIndexImpl(List<BigDecimal> allStockPrices) {
		super();
		this.allStockPrices = allStockPrices;
	}

	@Override
	public BigDecimal calculateAllShareIndexValue() {
		BigDecimal allShareIndex = null;

		if (!this.allStockPrices.isEmpty()) {
			BigDecimal allSharePriceProduct = getProductOfStockPrices(this.allStockPrices);
			allShareIndex = CalculationUtils.nthRoot(this.allStockPrices.size(), allSharePriceProduct,
					Stock.PRICE_SCALE, Stock.PRICE_ROUNDING_MODE);
		}

		return allShareIndex;
	}

	private BigDecimal getProductOfStockPrices(List<BigDecimal> allStockPrices) {
		BigDecimal allSharePriceProduct = null;

		for (BigDecimal price : allStockPrices) {
			if (allSharePriceProduct == null) {
				allSharePriceProduct = price;
			} else {
				allSharePriceProduct = allSharePriceProduct.multiply(price);
			}
		}

		return allSharePriceProduct;
	}
}
