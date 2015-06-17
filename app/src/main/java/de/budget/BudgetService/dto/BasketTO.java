package de.budget.BudgetService.dto;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @date 18.05.2015
 * @author Marco
 * Class for the date transfer of a basket
 */
public class BasketTO implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String notice;
	private double amount;
	private long createDate;
	private long purchaseDate;
	private long lastChanged;
//	private Timestamp createDate;
//	private Timestamp purchaseDate;
//	private Timestamp lastChanged;
	private UserTO user;
	private VendorTO vendor;
	private PaymentTO payment;
	private List<ItemTO> items;
	
	/**
	 * Default Constructor
	 * @author Marco
	 */
	public BasketTO() {
		
	}
	
//	/**
//	 * @author Marco
//	 * @date 19.05.2015
//	 * @param id
//	 * @param notice
//	 * @param amount
//	 * @param createDate
//	 * @param purchaseDate
//	 * @param lastChanged
//	 * @param user
//	 * @param vendor
//	 * @param payment
//	 * @param items
//	 */
//	public BasketTO(int id, String notice, double amount, Timestamp createDate, Timestamp purchaseDate, Timestamp lastChanged, UserTO user, VendorTO vendor, PaymentTO payment, List<ItemTO> items) {
//		super();
//		this.id = id;
//		this.notice = notice;
//		this.amount = amount;
//		this.createDate = createDate;
//		this.purchaseDate =purchaseDate;
//		this.lastChanged = lastChanged;
//		this.user = user;
//		this.vendor = vendor;
//		this.payment = payment;
//		this.items = items;
//	}

	/**
	 * @author Mark
	 * @date 17.06.2015
	 * @param id
	 * @param name
	 * @param notice
	 * @param amount
	 * @param createDate
	 * @param purchaseDate
	 * @param lastChanged
	 * @param user
	 * @param vendor
	 * @param payment
	 * @param items
	 */
	public BasketTO(int id, String name, String notice, double amount, long createDate, long purchaseDate, long lastChanged, UserTO user, VendorTO vendor, PaymentTO payment, List<ItemTO> items) {
		super();
		this.id = id;
		this.name = name;
		this.notice = notice;
		this.amount = amount;
		this.createDate = createDate;
		this.purchaseDate =purchaseDate;
		this.lastChanged = lastChanged;
		this.user = user;
		this.vendor = vendor;
		this.payment = payment;
		this.items = items;
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
		return this.name;
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
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

//	/**
//	 * @return the createDate
//	 */
//	public Timestamp getCreateDate() {
//		return createDate;
//	}
//
//	/**
//	 * @param createDate the createDate to set
//	 */
//	public void setCreateDate(Timestamp createDate) {
//		this.createDate = createDate;
//	}
//
//	/**
//	 * @return the purchaseDate
//	 */
//	public Timestamp getPurchaseDate() {
//		return purchaseDate;
//	}
//
//	/**
//	 * @param purchaseDate the purchaseDate to set
//	 */
//	public void setPurchaseDate(Timestamp purchaseDate) {
//		this.purchaseDate = purchaseDate;
//	}
//
//	/**
//	 * @return the lastChanged
//	 */
//	public Timestamp getLastChanged() {
//		return lastChanged;
//	}
//
//	/**
//	 * @param lastChanged the lastChanged to set
//	 */
//	public void setLastChanged(Timestamp lastChanged) {
//		this.lastChanged = lastChanged;
//	}

	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the purchaseDate
	 */
	public long getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the lastChanged
	 */
	public long getLastChanged() {
		return lastChanged;
	}

	/**
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(long lastChanged) {
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

	/**
	 * @return the vendor
	 */
	public VendorTO getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(VendorTO vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the payment
	 */
	public PaymentTO getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(PaymentTO payment) {
		this.payment = payment;
	}

	/**
	 * @return the items
	 */
	public List<ItemTO> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<ItemTO> items) {
		this.items = items;
	}

	/**
	 * @param item add to the items
	 */
	public void setItem(ItemTO item) { this.items.add(item); }
}