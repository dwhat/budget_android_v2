package de.budget.BudgetService.Exception;

/**
 * Exception, if an username is already existing
 * @date 18.05.2015
 * @author Marco
 *
 */
public class UsernameAlreadyExistsException extends BudgetOnlineException {
	
	private static final long serialVersionUID = 1L;
	private static final int CODE = 30;

	public UsernameAlreadyExistsException(String message) {
		super(CODE, message);
	}
}
