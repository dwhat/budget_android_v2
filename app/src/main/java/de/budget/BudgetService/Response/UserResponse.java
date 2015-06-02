package de.budget.BudgetService.Response;


import de.budget.BudgetService.dto.UserTO;

public class UserResponse extends ReturnCodeResponse {


	private static final long serialVersionUID = 1L;

	private UserTO userTo;
	
	/**
	 * @author MArco
	 * @date 18.05.2015
	 * Default Constructor
	 */
	public UserResponse() {
		
	}
	
	public void setUserTo(UserTO userTo) {
		this.userTo = userTo;
	}
	
	public UserTO getUserTo () {
		return this.userTo;
	}
}
