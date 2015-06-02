package de.budget.BudgetService.dto;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @date 19.05.2015
 * @author Marco
 * Class for the date transfer of a payment
 */
public class PaymentTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String number;
	private String bic;
	private boolean active;
	private Timestamp createDate;
	private Timestamp lastChanged;
	private UserTO user;
	
	/**
	 * Default Constructor
	 * @author Marco
	 * @date 19.05.2015
	 */
	public PaymentTO() {
		
	}
	
	/**
	 * Constructor for class PaymentTO
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param name
	 * @param number
	 * @param bic
	 * @param active
	 * @param createDate
	 * @param lastChanged
	 * @param user
	 */
	public PaymentTO(int id, String name, String number, String bic, boolean active, Timestamp createDate, Timestamp lastChanged, UserTO user) {
		this.id = id;
		this.name = name;
		this.bic = bic; 
		this.active = active;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.user = user;
		
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the bic
	 */
	public String getBic() {
		return bic;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param bic the bic to set
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the lastChanged
	 */
	public Timestamp getLastChanged() {
		return lastChanged;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(Timestamp lastChanged) {
		this.lastChanged = lastChanged;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @return the userTO Object of the User
	 */
	public UserTO getUser() {
		return user;
	}

	/**
	 * @date 19.05.2015
	 * @author Marco
	 * @param user the user to set
	 */
	public void setUser(UserTO user) {
		this.user = user;
	}
	
}
