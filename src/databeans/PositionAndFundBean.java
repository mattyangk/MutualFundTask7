package databeans;

public class PositionAndFundBean {
	private int fund_id;
	private String name;
	private String symbol;
	private int customer_id;
	private double shares;
	private double available_shares;
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public double getShares() {
		return shares;
	}
	public void setShares(double shares) {
		this.shares = shares;
	}
	public double getAvailable_shares() {
		return available_shares;
	}
	public void setAvailable_shares(double available_shares) {
		this.available_shares = available_shares;
	}
	
	

}
