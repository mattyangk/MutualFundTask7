package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;



public class SellFundForm extends FormBean {
	private String share;

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = trimAndConvert(share, "<>\"");
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (share == null || share.length() == 0) {
			errors.add("Number of shares is required");
		}
		return errors;
	}
}
