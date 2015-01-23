package formbeans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean  {

    private String amount;

    public String getAmount()   { return amount; }
	
    public void setAmount(String s) {	amount = s.trim();    }
	
	public double getRequestAmountAsDouble() {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (amount == null || amount.length() == 0) errors.add("Deposit Amount is required");
        
        try {
			Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			errors.add("Amount is not an double");
		}
        
        System.out.println("original : "+getRequestAmountAsDouble());
		BigDecimal bd = new BigDecimal(getRequestAmountAsDouble());
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		double requestAmt = bd.doubleValue();    
		System.out.println("rounded : "+requestAmt);
        
		if(getRequestAmountAsDouble() > 100000000000.00){
			errors.add("Max. Amount allowed is $100000000000.00 !");
		}
		
		if(getRequestAmountAsDouble() < 0.01){
			errors.add("Invalid Transaction ! Amount cannot be less than $0.01");
		}
		else if((getRequestAmountAsDouble()!=requestAmt) && (getRequestAmountAsDouble()-requestAmt) < 0.01){
			errors.add("Amount can only have upto 2 places of decimal !");
		}
        
        return errors;
    }

}
