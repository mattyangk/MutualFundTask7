package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;



public class BuyFundForm extends FormBean {
	private String fundname;
	private String amount;

	public String getFundName() {
		return fundname;
	}

	public void setFundName(String fundTicker) {
		this.fundname = trimAndConvert(fundTicker, "<>\"");
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = trimAndConvert(amount, "<>\"");
	}
	
	
	public double getFundAmountAsDouble() {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (fundname == null || fundname.length() == 0) {
			errors.add("Fundname is required");
		}
		if (amount == null || amount.length() == 0) {
			errors.add("Amount is required");
		} else if (!amount.matches("-?\\d+(\\.\\d+)?")) {
			errors.add("Invalid amount");
		} 
		return errors;
	}
	
	
	
	
}
