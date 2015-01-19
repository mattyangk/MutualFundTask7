package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResearchFundForm extends FormBean {
	private String fundname;
	
	public String getFundname() {
		return fundname;
	}

	public void setFundname(String fundname) {
		this.fundname = trimAndConvert(fundname, "<>\"");
	}


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (fundname == null || fundname.length() == 0) {
			errors.add("fundname is required");
		}
		
		return errors;
	}
}