package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.EmployeeBean;
import formbeans.ChangePwdForm;

public class ChangePwdAction extends Action{
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private EmployeeDAO employeeDAO;

	public ChangePwdAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "changePwd.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up error list
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);

		try {
			HttpSession session = request.getSession();
			EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
			
			if (employee == null) {
				errors.add("session expired");
				return "index.do";
			}

			// Load the form parameters into a form bean
			ChangePwdForm form = formBeanFactory.create(request);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "changePwd.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "changePwd.jsp";
			}
			
			String firstPwd = form.getNewPassword();
			String secondPwd = form.getrePassword();
			
			if (!firstPwd.equals(secondPwd)) {
				errors.add("Two passwords are not the same. Please enter again");
				return "changePwd.jsp";
			}
			
			employee.setPassword(firstPwd);			
			employeeDAO.update(employee);
			
			successes.add("Password has been changed!");
			
			
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "changePwd.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "changePwd.jsp";
		}
		
		return "manage.jsp";
	}

}
