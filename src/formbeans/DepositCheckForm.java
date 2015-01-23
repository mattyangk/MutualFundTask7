package formbeans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean  {
  
    private String username;
    private String amount;
    
    public String getUsername()  { return username; }
    public String getAmount()   { return amount; }
    
    public void setUsername(String s) { username = trimAndConvert(s,"<>\"");  }
	public void setAmount(String s) {	amount = s.trim();    }

	
	
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

      
        if (username == null || username.trim().length() == 0) errors.add("Username is required");
        if (amount == null || amount.length() == 0) errors.add("Deposit Amount is required");

        try {
			Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			errors.add("Invalid Amount");
		}
        
        System.out.println("original : "+getDepositAmountAsDouble());
		BigDecimal bd = new BigDecimal(getDepositAmountAsDouble());
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		double depositAmt = bd.doubleValue();    
		System.out.println("rounded : "+depositAmt);
		
		if(getDepositAmountAsDouble() > 100000000000.00){
			errors.add("Max. Amount allowed is $100000000000.00 !");
		}
		
		if(getDepositAmountAsDouble() < 0.01){
			errors.add("Invalid Transaction ! Amount cannot be less than $0.01");
		}
		else if((getDepositAmountAsDouble()!=depositAmt) && (getDepositAmountAsDouble()-depositAmt) < 0.01){
			errors.add("Amount can only have upto 2 places of decimal !");
		}
        
        return errors;
    }

}
