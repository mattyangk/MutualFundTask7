package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import formbeans.ResetPwdForm;

public class ResetPwdAction extends Action{
	private FormBeanFactory<ResetPwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ResetPwdForm.class);

	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;

	public ResetPwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
		employeeDAO= model.getEmployeeDAO();
	}

	public String getName() {
		return "resetPwd.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up error list
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			HttpSession session = request.getSession();
			EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
			
			if (employee == null) {
				errors.add("session expired");
				return "index.do";
			}

			// Load the form parameters into a form bean
			ResetPwdForm form = formBeanFactory.create(request);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "resetPwd.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "resetPwd.jsp";
			}
			String userName=form.getUsername();
			String firstPwd = form.getNewPassword();
			String secondPwd = form.getrePassword();
			
			if (!firstPwd.equals(secondPwd)) {
				errors.add("Two passwords are not the same. Please enter again");
				return "resetPwd.jsp";
			}
			
			CustomerBean customer=customerDAO.getCustomerByUsername(userName);
			
			customer.setPassword(firstPwd);			
			customerDAO.update(customer);

			return "manage.jsp";
			
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "resetPwd.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "resetPwd.jsp";
		}
	}

}

