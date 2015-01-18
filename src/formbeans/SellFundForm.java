package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;



public class SellFundForm extends FormBean {
	private String share;
	private String fundname;
	
	public String getFundname() {
		return fundname;
	}

	public void setFundname(String fundname) {
		this.fundname = fundname;
	}


	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = trimAndConvert(share, "<>\"");
	}
	
	public double getShareAsDouble() {
		try {
			return Double.parseDouble(share);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (share == null || share.length() == 0) {
			errors.add("Number of shares is required");
		}
		if(fundname==null||fundname.length()==0){
			errors.add("fundname of fund is required");
		}
		return errors;
	}
}
