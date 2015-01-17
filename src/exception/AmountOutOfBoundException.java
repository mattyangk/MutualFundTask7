package exception;

public class AmountOutOfBoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	double ownAmount;
	
	double trasactionAmount;
	
	String transactionType;
	
	public AmountOutOfBoundException(double ownAmount, double transactionAmount, String transactionType) {
		super();
		this.ownAmount = ownAmount;
		this.trasactionAmount = transactionAmount;	
		this.transactionType = transactionType;
	}
	
	@Override
	public String getMessage() {
		
		return "The transaction is: " + transactionType + ".\nOwned amount is " + ownAmount + " and transaction amount is: "+ trasactionAmount + ".\n"+ super.getMessage();
	}
	

}
