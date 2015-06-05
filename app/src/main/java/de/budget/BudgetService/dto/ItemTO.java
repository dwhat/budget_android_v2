package de.budget.BudgetService.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 18.05.2015
 * @author Marco
 * Class for the date transfer of an Item
 */
public class ItemTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;	
	private double quantity;	
	private double price;	
	private String notice;		
	private int period;	
	private Timestamp createDate;	
	private Timestamp launchDate;	
	private Timestamp finishDate;
	private Timestamp lastChanged;
	private BasketTO basket;
	private CategoryTO category;

	/**
	 * Deafault Constructor
	 * @author Marco
	 * @date 19.05.2015
	 */
	public ItemTO() {
		
	}
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param period
	 * @param createDate
	 * @param launchDate
	 * @param finishDate
	 * @param lastChanged
	 * @param basket
	 * @param category
	 */
	public ItemTO(int id, String name, double quantity, double price, String notice, int period, Timestamp createDate, Timestamp launchDate, Timestamp finishDate, Timestamp lastChanged, BasketTO basket, CategoryTO category) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.notice = notice;
		this.period = period;
		this.createDate = createDate;
		this.finishDate = finishDate;
		this.launchDate = launchDate;
		this.lastChanged = lastChanged;
		this.category = category;
		this.basket = basket;
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
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}


	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
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
	 * @return the launchDate
	 */
	public Timestamp getLaunchDate() {
		return launchDate;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param launchDate the launchDate to set
	 */
	public void setLaunchDate(Timestamp launchDate) {
		this.launchDate = launchDate;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the finishDate
	 */
	public Timestamp getFinishDate() {
		return finishDate;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param finishDate the finishDate to set
	 */
	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
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
	 * @return the basket
	 */
	public BasketTO getBasket() {
		return basket;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param basket the basket to set
	 */
	public void setBasket(BasketTO basket) {
		this.basket = basket;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the category
	 */
	public CategoryTO getCategory() {
		return category;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param category the category to set
	 */
	public void setCategory(CategoryTO category) {
		this.category = category;
	}
}
