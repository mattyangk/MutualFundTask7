package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.*;
import databeans.*;
import exception.AmountOutOfBoundException;
import formbeans.*;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;



public class TransitionDayAction extends Action {
	
	private FormBeanFactory<TransitionForm> formBeanFactory = FormBeanFactory
			.getInstance(TransitionForm.class);
	
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundpriceHistoryDAO;
	private CustomerDAO customerDAO;
	
	public TransitionDayAction(Model model) {
		fundDAO = model.getFundDAO();
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		fundpriceHistoryDAO = model.getFundPriceHistoryDAO();
		
	}
	
	
	public String getName(){
		
		return "TransitionDayAction.do";
	}
	
	public String perform(HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		
		request.setAttribute("errors", errors);
		
		
		try{
			
			TransitionForm form=formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			
		
			
			FundBean[] allFunds=fundDAO.getAllFunds();//distinct different funds;
			
			
			
			
			
			if(allFunds==null||allFunds.length==0)
			{
				System.out.println("No fund");
				errors.add("No fund has been created yet");
				return "e_Transition_day.jsp";//?
			}
			
			else {
				
				System.out.println("YES,has funds");
			}
			
			LastPriceForFundsBean[] funds=new LastPriceForFundsBean[allFunds.length];
			
			
			
			int existedCount=fundpriceHistoryDAO.getCount();//any record existed in fundpriceHistory table?
			
			if(existedCount==0)
				{
				  request.setAttribute("theLastDate", "Current");
				  System.out.println("No latestday");
				}
			   
			else
				{
				  request.setAttribute("theLastDate", "yes"); //should show the latest day,some function missed.
				  System.out.println("has date");
				}
			  
			
			
			double lastP=1.1;
			
			for(int i=0;i<funds.length;i++)
			{
				LastPriceForFundsBean onefund=new LastPriceForFundsBean();
				onefund.setFund_id(allFunds[i].getFund_id());
				onefund.setFund_name(allFunds[i].getName());
				onefund.setFund_symbol(allFunds[i].getSymbol());
				System.out.println(i);
	  			
		      // lastPrice=fundpriceHistoryDAO.findLatestPrice(allFunds[i].getFund_id());//need to do,based on fundID return that fund's lastPrice;
		       //if no existed, return -1
			    
			    onefund.setLast_price(lastP);
		        
		        funds[i]=onefund;
			}
			
			request.setAttribute("allFunds", funds);
			
			if(!form.isPresent())
			{
				return "e_Transition_day.jsp";
			}
			
			//form error check need to be done;
			
			
			
			
			String[]price=form.getPrice();    //start to get the value from the form
			String[]fund_id=form.getFund_id();
			
			
			//TODO
			//typed date check should be done,should be valid, and after the last end day.
			
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			Date newLateDate=dateFormat.parse(form.getTransitionDate());
			
			
			
			
			
			//update the historyPirce table first;
			for(int i=0;i<price.length;i++)
			{
				if(price[i]==null||price[i].length()==0)//TODO CHECK NULL
				{
					errors.add("Should type all price for all funds");
					
				}
				
				FundPriceHistoryBean one=new FundPriceHistoryBean();
				one.setFund_id(Integer.parseInt(fund_id[i]));
				one.setPrice(Double.parseDouble(price[i]));
				one.setPrice_date(newLateDate);
				fundpriceHistoryDAO.create(one);
				System.out.println("updating");
			}
			
			if(1!=0)
				return "e_Transition_day.jsp";
			
			
			//Then update pending transaction based on the queue.
			
			//
			TransactionBean [] business=transactionDAO.readPendingTransInOrder();//Succefully check all the pending transaction?
			
			for(int i=0;i<business.length;i++)
			{
			    CustomerBean theCustomer=customerDAO.read(business[i].getCustomer_id());
				
			    double cash=theCustomer.getCash(); //need to be done;
				
			    if(business[i].getTrasaction_type().equals("request"))
			    {
			    	double awayMoney=business[i].getAmount();
			    	
			    	customerDAO.updateCash(business[i].getCustomer_id(),cash-awayMoney);//need to be done;
			    	
			    	
			    	business[i].setTransaction_date(newLateDate);
			    	business[i].setIs_complete(true);
			    	
			    	transactionDAO.update(business[i]);
			    	
			    	
			    }
			    
			    else if(business[i].getTrasaction_type().equals("deposit"))
			    {
			    	double addMoney=business[i].getAmount();
			    	
			    	customerDAO.updateCash(business[i].getCustomer_id(),cash+addMoney);
			    	
			    	business[i].setTransaction_date(newLateDate);
			    	business[i].setIs_complete(true);
			    	
			    	transactionDAO.update(business[i]);
			    	
			    }
			    else if (business[i].getTrasaction_type().equals("buy")) {
					
					int customerID=business[i].getCustomer_id();
					int fundID=business[i].getFund_id();
					
					double newFundPrice=fundpriceHistoryDAO.findLatestPrice(business[i].getFund_id());//need to be done
					
					double newShares = business[i].getAmount()/newFundPrice;//be careful to the number;
					
					business[i].setExecute_date(newLateDate);
					
					business[i].setShares(newShares);
					
					business[i].setIs_complete(true);
					
					transactionDAO.update(business[i]);
				
					
					if(positionDAO.read(customerID,fundID)!=null)  //need to be done
					{
						PositionBean onePosition=positionDAO.read(customerID,fundID);
						double currentShares=onePosition.getShares();
						onePosition.setShares(currentShares+newShares);
						onePosition.setAvailable_shares(onePosition.getShares());
						positionDAO.update(onePosition);
						
					}else
					{
						PositionBean newPosition=new PositionBean();
						newPosition.setCustomer_id(customerID);
						newPosition.setFund_id(fundID);
						newPosition.setShares(newShares);
						newPosition.setAvailable_shares(newShares);
						  // insert new data into position table.
						positionDAO.create(newPosition);
					}
					
					// UPDATE CASH
					customerDAO.updateCash(business[i].getCustomer_id(),cash-business[i].getAmount());
					
					
			    } //buy fund is done.
			    else if(business[i].getTrasaction_type().equals("sell")) //sell fund begins.
			    {
			    	int customerID=business[i].getCustomer_id();
					int fundID=business[i].getFund_id();
					
					double newFundPrice=fundpriceHistoryDAO.findLatestPrice(business[i].getFund_id());//need to be done
					
					double amount=business[i].getShares() * newFundPrice;
					
					double newShares=business[i].getShares();
					
					business[i].setAmount(amount);
			    	
					business[i].setExecute_date(newLateDate);
					
					business[i].setIs_complete(true);
			    	
					transactionDAO.update(business[i]); //need to be done;
					
					
					
					if(positionDAO.read(customerID,fundID)!=null)  //need to be done
					{
						//TODO POSITIONDAO.READ ORDER
						PositionBean onePosition=positionDAO.read(customerID,fundID);
						double currentShares=onePosition.getShares();
						onePosition.setShares(currentShares-newShares);
						onePosition.setAvailable_shares(onePosition.getShares());  //needed new attribute.
						positionDAO.update(onePosition);
						
					}else
					{
						//TODO EXCEPTION DETAIL
						throw new RollbackException(
								"Some errors with funds");
						
					}
					
			    	
					customerDAO.updateCash(business[i].getCustomer_id(),cash+business[i].getAmount());
			    	
			    }
			}
			
			CustomerBean[] allCustomers=customerDAO.getAllcustomers();
			
			for(int i=0;i<allCustomers.length;i++)
			{
				CustomerBean oneCustomer=allCustomers[i];
				Double currentCash=oneCustomer.getCash();
				customerDAO.updateBalance(oneCustomer.getCustomer_id(),currentCash); //need to be done
			}
			// SET IS_COMPLETE IS_SUCCESS
			
			
			return "e_Transition_day.jsp" ; 
					
			 }catch(RollbackException e){
			   errors.add(e.getMessage());
			   return "e_Transition_day.jsp";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				  return "e_Transition_day.jsp";
			} catch (AmountOutOfBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				  return "e_Transition_day.jsp";
			}catch( FormBeanException e){
				e.printStackTrace();
				  return "e_Transition_day.jsp";
				
		    }
		     
		    finally{
				if (Transaction.isActive())
					Transaction.rollback();
		    
		    	  
		      }
		
	
		
	}
	
	
	

}
