package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import model.EmployeeDAO;
import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.EmployeeBean;
import databeans.CustomerBean;
import formbeans.CreateEmployeeAccoutForm;

public class CreateEmployeeAccountAction extends Action {

	private FormBeanFactory<CreateEmployeeAccoutForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateEmployeeAccoutForm.class);

	EmployeeDAO employeeDAO;
	CustomerDAO customerDAO;
	
	public CreateEmployeeAccountAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "createEmployeeAccount.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);
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
			
			String newName=form.getUsername();
			CustomerBean isExisted=customerDAO.getCustomerByUsername(newName);
			if(isExisted!=null)
			{
				errors.add("There already exists an Customer with the same username,please try another username");
				return "createEmployeeAccount.jsp";
			}
			
			EmployeeBean isExistedEmployee =employeeDAO.getEmployeeByUsername(newName);
			if(isExistedEmployee!=null)
			{
				errors.add("There already exists an employee with the same username,please try another username");
				return "createEmployeeAccount.jsp";
			}
			
			
			EmployeeBean employee = new EmployeeBean();
			employee.setFirstname(form.getFirstname());
			employee.setLastname(form.getLastname());
			employee.setUsername(form.getUsername());
			employee.setPassword(form.getPassword());
			
			System.out.println(form.getUsername());
			
			employeeDAO.createAutoIncrement(employee);
			successes.add("New employee account has been created!");
		

		} catch (FormBeanException e) {
			errors.add(e.getMessage());
		} catch (RollbackException e) {
			errors.add(e.getMessage());
		}

		return "manage.jsp";
		
	}

}
