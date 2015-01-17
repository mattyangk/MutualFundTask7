package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.TransactionDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.TransactionBean;
import formbeans.RequestCheckForm;

public class RequestCheckAction extends Action{

	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckForm.class);
	
	TransactionDAO transactionDAO;
	
	public RequestCheckAction(Model model) {
		transactionDAO = model.getTransactionDAO();
	}
	
	@Override
	public String getName() {
		return "requestCheck.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			RequestCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "requestCheck.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
				return "requestCheck.jsp";
			}
			
			TransactionBean transaction = new TransactionBean();
			
			
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "manage.jsp";
	}

}
