package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePwdForm extends FormBean {

	private String newPassword;
	private String rePassword;

	public String getrePassword() {
		return rePassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setrePassword(String s) {
		rePassword = trimAndConvert(s, "<>\"");
	}

	public void setNewPassword(String s) {
		newPassword = trimAndConvert(s, "<>\"");
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (newPassword == null || newPassword.length() == 0) {
			errors.add("New Password is required");
		}

		if (rePassword == null || rePassword.length() == 0) {
			errors.add("Please confirm the new password");
		}

		

		if (!newPassword.equals(rePassword)) {
			errors.add("Passwords do not match");
		}

		if (errors.size() > 0) {
			return errors;
		}
		return errors;
	}

}
