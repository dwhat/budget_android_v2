package de.budget.BudgetService.dto;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * @date 26.05.2015
 * @author Marco
 *
 */
public class UserTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private String username;
	private String password;
	private String email;
	private Timestamp createDate;
	private Timestamp lastChanged;
	private List<BasketTO> basketList;
	private List<CategoryTO> categoryList;
	private List<PaymentTO> paymentList;
	private List<VendorTO> vendorList;
	

	/**
	 * Default Constructor
	 * @date 26.05.2015
	 * @author Marco
	 */
	public UserTO() {
		
	}
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param username
	 * @param password
	 * @param email
	 * @param createDate
	 * @param lastChanged
	 * @param baskets
	 * @param categories
	 * @param payments
	 * @param vendors
	 */
	public UserTO(String username, String password, String email, Timestamp createDate, Timestamp lastChanged, List<BasketTO> baskets, List<CategoryTO> categories, List<PaymentTO> payments, List<VendorTO> vendors) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.basketList = baskets;
		this.vendorList = vendors;
		this.paymentList = payments;
		this.categoryList = categories;
		
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the basketList
	 */
	public List<BasketTO> getBasketList() {
		return basketList;
	}

	/**
	 * @param basketList the basketList to set
	 */
	public void setBasketList(List<BasketTO> basketList) {
		this.basketList = basketList;
	}

	/**
	 * @return the categoryList
	 */
	public List<CategoryTO> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<CategoryTO> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return the paymentList
	 */
	public List<PaymentTO> getPaymentList() {
		return paymentList;
	}

	/**
	 * @param paymentList the paymentList to set
	 */
	public void setPaymentList(List<PaymentTO> paymentList) {
		this.paymentList = paymentList;
	}

	/**
	 * @return the vendorList
	 */
	public List<VendorTO> getVendorList() {
		return vendorList;
	}

	/**
	 * @param vendorList the vendorList to set
	 */
	public void setVendorList(List<VendorTO> vendorList) {
		this.vendorList = vendorList;
	}
	
	
}
