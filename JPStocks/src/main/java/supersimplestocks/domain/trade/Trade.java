package supersimplestocks.domain.trade;

import java.math.BigDecimal;
import java.util.Date;

public class Trade {

	private String symbol;

	private Date timeStamp;

	private int quantity;

	private TradeTransactionIndicator indicator;

	private BigDecimal price;

	public Trade(String symbol, Date timeStamp, int quantity, TradeTransactionIndicator indicator, BigDecimal price) {
		super();
		this.symbol = symbol;
		this.timeStamp = timeStamp;
		this.quantity = quantity;
		this.indicator = indicator;
		this.price = price;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public TradeTransactionIndicator getIndicator() {
		return this.indicator;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

}
