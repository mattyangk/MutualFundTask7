package databeans;

import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("fund_id,price_date")
public class FundPriceHistoryBean {
	private int fund_id;
	private Date price_date;
	private double price;
	
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
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
