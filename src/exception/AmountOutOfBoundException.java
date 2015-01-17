package exception;

public class AmountOutOfBoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	double ownAmount;
	
	double trasactionAmount;
	
	public AmountOutOfBoundException(double ownAmount, double transactionAmount) {
		super();
		this.ownAmount = ownAmount;
		this.trasactionAmount = transactionAmount;	
	}
	
	@Override
	public String getMessage() {
		
		return "Owned amount is " + ownAmount + " and transaction amount is: "+ trasactionAmount + ".\n"+ super.getMessage();
	}
	

}
