package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("customer_id")
public class CustomerBean {

	private int customer_id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String addr_line1;
	private String addr_line2;
	private String city;
	private String state;
	private String zip;
	private double cash;
	private double balance;
	
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddr_line1() {
		return addr_line1;
	}
	public void setAddr_line1(String addr_line1) {
		this.addr_line1 = addr_line1;
	}
	public String getAddr_line2() {
		return addr_line2;
	}
	public void setAddr_line2(String addr_line2) {
		this.addr_line2 = addr_line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	

}
