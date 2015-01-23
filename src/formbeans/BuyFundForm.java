package formbeans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;



public class BuyFundForm extends FormBean {
	private String fundname;
	private String amount;

	public String getFundname() {
		return fundname;
	}

	public void setFundname(String fundTicker) {
		this.fundname = trimAndConvert(fundTicker, "<>\"");
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount.trim();
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
		
		System.out.println("original : "+getFundAmountAsDouble());
		BigDecimal bd = new BigDecimal(getFundAmountAsDouble());
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		double buyAmt = bd.doubleValue();    
		System.out.println("rounded : "+buyAmt);
		
		if(getFundAmountAsDouble() > 100000000000.00){
			errors.add("Max. Amount allowed is $100000000000.00 !");
		}
		
		if(getFundAmountAsDouble() < 0.01){
			errors.add("Invalid Transaction ! Amount cannot be less than $0.01");
		}
		else if((getFundAmountAsDouble()!=buyAmt) && (getFundAmountAsDouble()-buyAmt) < 0.01){
			errors.add("Amount can only have upto 2 places of decimal !");
		}		
		
		return errors;
	}
	
	
	
	
}
