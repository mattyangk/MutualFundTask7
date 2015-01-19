package controller;

<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.FundBean;
import formbeans.CreateFundForm;

public class CreateFundAction extends Action {
	
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateFundForm.class);	
	
	FundDAO funDAO;
	
	public CreateFundAction(Model model) {
		funDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return "createFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "createFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "createFund.jsp";
			}
			
			FundBean fund = new FundBean();
			fund.setName(form.getFundname());
			fund.setSymbol(form.getSymbol());
			
			funDAO.createAutoIncrement(fund);
			
			
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createFund.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "createFund.do";
		}
		
		
		return "manage.jsp";
	}
=======
import org.mybeans.form.FormBeanFactory;

import formbeans.CreateFundForm;

public class CreateFundAction {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateFundForm.class);
	
	private FundDAO fundDao;
	
	
	
	
>>>>>>> Stashed changes

}
