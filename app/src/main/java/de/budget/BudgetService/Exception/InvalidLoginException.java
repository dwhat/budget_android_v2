package de.budget.BudgetService.Exception;

public class InvalidLoginException extends BudgetOnlineException {
	
	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 20;

	public InvalidLoginException(String message) {
		super(CODE, message);
	}


}
