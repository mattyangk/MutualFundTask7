package databeans;

import java.util.Date;

public class FundPriceDetailBean {
	private int fund_id;
	private String name;
	private String symbol;
	private Date price_date;
	private double price;
	
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
	public Date getPrice_date() {
		return price_date;
	}
	public void setPrice_date(Date price_date) {
		this.price_date = price_date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
