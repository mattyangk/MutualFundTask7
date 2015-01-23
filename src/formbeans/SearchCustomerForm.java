package formbeans;

import org.mybeans.form.FormBean;

public class SearchCustomerForm extends FormBean{
	
	String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String nm) {
		customerName = trimAndConvert(nm, "<>\"");
	}
	
	
	
	
	

}
