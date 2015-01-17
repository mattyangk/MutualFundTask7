package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.EmployeeBean;
import formbeans.CreateEmployeeAccoutForm;

public class CreateEmployeeAccountAction extends Action {

	private FormBeanFactory<CreateEmployeeAccoutForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateEmployeeAccoutForm.class);

	EmployeeDAO employeeDAO;
	
	public CreateEmployeeAccountAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

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
				System.out.println("In create employee empty");
				return "createEmployeeAccount.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
				for (String error : errors) {
					System.out.println(error);
				}
				return "createEmployeeAccount.jsp";
			}	
			
			System.out.println("In create employee create bean");

			
			EmployeeBean employee = new EmployeeBean();
			employee.setFirstname(form.getFirstname());
			employee.setLastname(form.getLastname());
			employee.setUsername(form.getUsername());
			employee.setPassword(form.getPassword());
			
			System.out.println(form.getUsername());
			
			employeeDAO.createAutoIncrement(employee);
		

		} catch (FormBeanException e) {
			errors.add(e.getMessage());
		} catch (RollbackException e) {
			errors.add(e.getMessage());
		}

		return "manage.jsp";
		
	}

}
