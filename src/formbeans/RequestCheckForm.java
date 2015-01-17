package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean  {

    private String amount;
    

    public String getAmount()   { return amount; }
    

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

        if (amount == null || amount.length() == 0) errors.add("Deposit Amount is required");
        
        try {
			Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			errors.add("Amount is not an double");
		}
        
        return errors;
    }

}
