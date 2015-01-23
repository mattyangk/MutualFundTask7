package formbeans;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
		
		System.out.println("original : "+getShareAsDouble());
		BigDecimal bdShares = new BigDecimal(getShareAsDouble());
		bdShares = bdShares.setScale(3, RoundingMode.HALF_UP);
		double roundedShares = bdShares.doubleValue();    
		System.out.println("rounded : "+roundedShares);
        
		if(getShareAsDouble() > 100000000000.00){
			errors.add("Max. Number allowed is 100000000000.000 !");
		}
		
        if(getShareAsDouble() < 0.001){
			errors.add("Invalid Transaction ! Shares cannot be less than 0.001");
		}
		else if((getShareAsDouble()!=roundedShares) && (getShareAsDouble()-roundedShares) < 0.001){
			errors.add("Shares can only have upto 3 places of decimal !");
		}
		
		
		return errors;
	}
}
