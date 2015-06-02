package de.budget.BudgetService.Exception;

public class NoSessionException extends BudgetOnlineException{
	
	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 10;

	public NoSessionException(String message) {
		super(CODE, message);
	}
}
