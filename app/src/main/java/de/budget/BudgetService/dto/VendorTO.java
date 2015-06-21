package de.budget.BudgetService.dto;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * Class for the DTO-Objects of a vendor
 * @author Marco
 *
 */
public class VendorTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private long createDate;
	private long lastChanged;
	private UserTO user;
	private String logo;
	private String street;
	private String city;
	private int PLZ;
	private int houseNumber;

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
	public VendorTO(int id, String name, long createDate, long lastChanged, UserTO user, String logo, String street, String city, int PLZ, int houseNumber) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.user = user;
		this.logo = logo;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.PLZ = PLZ;
	}

	public VendorTO(int id, String name, long createDate, long lastChanged, String street, String city, int PLZ, int houseNumber) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.PLZ = PLZ;
	}

	/**
	 * Constructor
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param name
	 */
	public VendorTO(int id, String name, String street, String city, int PLZ, int houseNumber) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.PLZ = PLZ;
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

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pLZ
	 */
	public int getPLZ() {
		return PLZ;
	}

	/**
	 * @param pLZ the pLZ to set
	 */
	public void setPLZ(int pLZ) {
		PLZ = pLZ;
	}

	/**
	 * @return the houseNumber
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
}