package formbeans;

import org.mybeans.form.FormBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransitionForm  extends FormBean {
	 private String transitionDate;
	 private String[] fund_id;
	 private String[] price;
	 
	public String getTransitionDate() {
		return transitionDate;
	}
	public void setTransitionDate(String transitionDate) {
		this.transitionDate = transitionDate;
	}
	public String[] getFund_id() {
		return fund_id;
	}
	public void setFund_id(String[] fund_id) {
		this.fund_id = fund_id;
	}
	public String[] getPrice() {
		return price;
	}
	public void setPrice(String[] price) {
		this.price = price;
	}
	 
	public Date getTransitionDateAsDate(){
		List<String> errors = new ArrayList<String>();
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = parser.parse(transitionDate);
		} catch (ParseException e) {
			errors.add("Not a Valid Format for TransitionDate ");
		}
		return date;
	}
	
	public List<String> getValidationErrors() {
		
		List<String> errors = new ArrayList<String>();
		if(!isValidDate(transitionDate))
			errors.add("Not a Valid Format for TransitionDate ");
		
		for(int i=0;i<price.length;i++)
		{
			String s=price[i];
			if(s==null||s.length()==0)
				errors.add("Please type new prices for all funds");
			
		}
		
		return errors;
	}
	
	
	
	
	public boolean isValidDate(String s) {
		try {
			java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat(
					"yyyy-mm-dd");
			sf.parse(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	 
	
	
	
	
	
	

}
