package de.budget.BudgetService.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VendorTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id; 
	private String name;
	private Timestamp createDate;
	private Timestamp lastChanged;
	private UserTO user;
	private String logo;

	/**
	 * Deafault Constructor
	 * @author Marco
	 * @date 19.05.2015
	 */
	public VendorTO() {
		
	}
	
	/**
	 * Constructor
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param name
	 * @param createDate
	 * @param lastChanged
	 * @param user
	 * @param logo
	 */
	public VendorTO(int id, String name, Timestamp createDate, Timestamp lastChanged, UserTO user, String logo) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.user = user;
		this.logo = logo;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the lastChanged
	 */
	public Timestamp getLastChanged() {
		return lastChanged;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(Timestamp lastChanged) {
		this.lastChanged = lastChanged;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the user
	 */
	public UserTO getUser() {
		return user;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param user the user to set
	 */
	public void setUser(UserTO user) {
		this.user = user;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
}
