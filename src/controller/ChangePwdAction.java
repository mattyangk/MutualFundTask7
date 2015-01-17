package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.CustomerDAO;
import model.EmployeeDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import formbeans.ChangePwdForm;

public class ChangePwdAction {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;

	public ChangePwdAction(Model model) {
		customerDAO = model.getUserDAO();
		
	}

	public String getName() {
		return "changePwd.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up error list
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			request.setAttribute("currentUser", userDAO.currentUser());

			// Load the form parameters into a form bean
			ChangePwdForm form = formBeanFactory.create(request);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				request.setAttribute("currentUser", userDAO.currentUser());
				return "changePwd.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				request.setAttribute("currentUser", userDAO.currentUser());
				return "changePwd.jsp";
			}

			User user = (User) request.getSession().getAttribute("user");

			// Change the password
			userDAO.setPassword(user.getEmailAddress(), form.getNewPassword());

			request.setAttribute("message", "Password changed!");
			request.setAttribute("user", user);
			request.setAttribute("currentUser", userDAO.currentUser());
			return "success.jsp";
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "error.jsp";
		}
	}

}
