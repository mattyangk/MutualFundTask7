package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateCustomerAccoutForm extends FormBean{
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
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = trimAndConvert(username, "<>\"");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = trimAndConvert(password, "<>\"");
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = trimAndConvert(firstname, "<>\"");
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = trimAndConvert(lastname, "<>\"");
	}
    public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getAddr_line1() {
		return addr_line1;
	}
	public void setAddr_line1(String addr_line1) {
		this.addr_line1 = trimAndConvert(addr_line1, "<>\"");
	}
	public String getAddr_line2() {
		return addr_line2;
	}
	public void setAddr_line2(String addr_line2) {
		this.addr_line2 = trimAndConvert(addr_line2, "<>\"");
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = trimAndConvert(city, "<>\"");
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = trimAndConvert(state, "<>\"");
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip =trimAndConvert(zip, "<>\"");
	}

	public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (username == null || username.trim().length() == 0) errors.add("Username is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
        
        

        return errors;
    }
	
	
}
