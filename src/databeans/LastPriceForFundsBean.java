package databeans;


import org.genericdao.PrimaryKey;

@PrimaryKey("fund_id")
public class LastPriceForFundsBean  {
	private int fund_id;
	private String fund_name;
	private String fund_symbol;
	private double last_price;
	private String last_date;
	
	
	
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public String getFund_name() {
		return fund_name;
	}
	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}
	public String getFund_symbol() {
		return fund_symbol;
	}
	public void setFund_symbol(String fund_symbol) {
		this.fund_symbol = fund_symbol;
	}
	public double getLast_price() {
		return last_price;
	}
	public void setLast_price(double last_price) {
	   this.last_price = last_price;
	}

	
	  
	
	
	 
	 
	
	
	
	

}
