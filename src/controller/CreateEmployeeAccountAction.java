package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.EmployeeBean;
import formbeans.CreateEmployeeAccoutForm;

public class CreateEmployeeAccountAction extends Action {

	private FormBeanFactory<CreateEmployeeAccoutForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateEmployeeAccoutForm.class);

	EmployeeDAO employeeDAO;

	@Override
	public String getName() {
		return "createEmployeeAccount.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {

			CreateEmployeeAccoutForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "createEmployeeAccount.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
				return "createEmployeeAccount.jsp";
			}	
			
			EmployeeBean employee = new EmployeeBean();
			employee.setFirstname(form.getFirstname());
			employee.setLastname(form.getLastname());
			employee.setUsername(form.getUsername());
			employee.setPassword(form.getPassword());
			
			employeeDAO.createAutoIncrement(employee);
			
			HttpSession session = request.getSession();
			session.setAttribute("employee", employee);
			
			

		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}

		return "manage.do";
	}

}
