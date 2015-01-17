package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean  {
    private String customer_id;
    private String username;
    private String amount;
    
    public String getCustomer_id()   { return customer_id; }
    public String getUsername()  { return username; }
    public String getAmount()   { return amount; }
    
    public void setCustomer_id(String s) { customer_id = trimAndConvert(s,"<>\"");  }
    public void setUsername(String s) { username = trimAndConvert(s,"<>\"");  }
	public void setAmount(String s) {	amount = s.trim();    }

	public int getCustomerIdAsInt() {
		try {
			return Integer.parseInt(customer_id);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	
	public double getDepositAmountAsDouble() {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (customer_id == null || customer_id.trim().length() == 0) errors.add("Customer Id is required");
        if (username == null || username.trim().length() == 0) errors.add("Username is required");
        if (amount == null || amount.length() == 0) errors.add("Deposit Amount is required");

        try {
			Integer.parseInt(customer_id);
		} catch (NumberFormatException e) {
			errors.add("Id is not an integer");
		}
        
        try {
			Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			errors.add("Amount is not an double");
		}
        
        return errors;
    }

}
