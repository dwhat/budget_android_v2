package de.budget.BudgetService.dto;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 18.05.2015
 * @author Marco
 * Class for the date transfer of a category
 */
public class CategoryTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String notice;
	private boolean active;
	private boolean income; //True, if IncomeCategory; false, if LossesCategory
	private Timestamp createDate;
	private Timestamp lastChanged;
	private UserTO user;
	
	/**
	 * Default Constructor
	 * @author Marco
	 */
	public CategoryTO() {
		
	}
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param name
	 * @param notice
	 * @param active
	 * @param income
	 * @param createDate
	 * @param lastChanged
	 * @param user
	 */
	public CategoryTO(int id, String name, String notice, boolean active, boolean income, Timestamp createDate, Timestamp lastChanged, UserTO user) {
		super();
		this.id = id;
		this.name = name;
		this.notice = notice;
		this.active = active;
		this.income = income;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.user = user;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}
	/**
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the income
	 */
	public boolean isIncome() {
		return income;
	}
	/**
	 * @param income the income to set
	 */
	public void setIncome(boolean income) {
		this.income = income;
	}
	/**
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the lastChanged
	 */
	public Timestamp getLastChanged() {
		return lastChanged;
	}

	/**
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(Timestamp lastChanged) {
		this.lastChanged = lastChanged;
	}

	/**
	 * @return the user
	 */
	public UserTO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserTO user) {
		this.user = user;
	}

}
