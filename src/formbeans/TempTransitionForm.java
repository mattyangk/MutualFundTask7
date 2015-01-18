package formbeans;

import org.mybeans.form.FormBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempTransitionForm  extends FormBean {
	 private String transitionDate;
	 
	public String getTransitionDate() {
		return transitionDate;
	}
	public void setTransitionDate(String transitionDate) {
		this.transitionDate = transitionDate;
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
		if(!isValidDate(transitionDate)){
			errors.add("Not a Valid Format for TransitionDate ");
		}
		return errors;
	}
	
	public boolean isValidDate(String s) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			sf.parse(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

}