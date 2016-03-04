Assignment - Super Simple Stocks

This assignment has been written to compile against Java SE 1.7

To keep things simple, the assignment code was has been written using the Standard Edition JDK. 
The only dependent jar that has been used is JUnit, for running the unit tests.

As the constraints specified no GUI, I've assumed that no user interaction is required. If I had
provided a user interface, it would be a simple character based one and I would have used the 
java.util.Scanner to accept user input.

The sample data and sample recorded trades are provided as hard coded values in the 
SuperSimpleStocksAssignment class. This class has a main method that will do the following:

	Calculate the Dividend Yields using a ticker price of 100.
	
	Calculate the P/E Ratios using a ticker price of 100.
	
	Record some sample Trades for each stock.
	
	Calculate the Stock Prices.
	
	Calculate the All Share Index.
	
	Calculate the Dividend Yields using Ticker Prices derived from the trades.
	
	Calculate the P/E Ratios using Ticker Prices derived from the trades.
	

I have also made the following assumptions:

The All Share Index is calculated from stock prices based on trades recorded in the
last 15 minutes.

The ticker price is price from the last recorded sale - i.e. the last trade with a 
BUY indicator. Par value is used if no sale is recorded.


