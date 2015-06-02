package de.budget.BudgetService.Response;

/**
 * UserReturnCode Klasse die die ReturnCodeResponse erweitert um die SessionID
 * @author Moritz
 *
 */
public class UserLoginResponse extends ReturnCodeResponse {
	
	private static final long serialVersionUID = -3173158310918408228L;

	private int sessionId;
	
	public UserLoginResponse() {
		// TODO Auto-generated constructor stub
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

}
