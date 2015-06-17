package de.budget.BudgetService;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.Response.*;
import de.budget.BudgetService.Exception.*;

//Java Datentypen Import
import java.sql.Timestamp;
import java.util.List;


//Response-Imports
import de.budget.BudgetService.dto.*;


/*
 * Interface BudgetOnlineService
 * @author Marco
 * @date 08.05.2015
 * @version Beta 1
 */
public interface BudgetOnlineService {


	/*#################      USER - SECTION     ##############*/

    /**
     * Method to Register a new User
     * @param username
     * @param password
     * @param email
     * @return UserLoginResponse
     * @date 18.05.2015
     * @author Marco
     */
    public UserLoginResponse setUser(String username, String password, String email) throws Exception;


    /**
     * Method to login with Username and Password
     * @param username
     * @param password
     * @return UserLoginResponse
     * @author Marco
     * @date 08.05.2015
     */
    public UserLoginResponse login(String username, String password) throws InvalidLoginException;


    /**
     * Method to log out
     * @throws NoSessionException
     * @author Marco
     * @date 08.05.2015
     */
    public ReturnCodeResponse logout(int sessionID) throws Exception;


    /**
     * Method to get a User by name
     * @author Marco
     * @param sessionId
     * @param userName
     * @return
     */
    public UserResponse getUserByName(int sessionId, String userName);


    //public int createOrUpdateUser(User user);

    /**
     * @author Marco
     * @param sessionId
     * @param username
     * @return
     */
    public ReturnCodeResponse deleteUser(int sessionId, String username);





	/*#################      VENDOR - SECTION     ##############*/

    /**
     * @author Marco
     * @date 19.05.2015
     * @param sessionId
     * @return VendorListResponse Object
     */
    public VendorListResponse getVendors(int sessionId) throws Exception;

    /**
     * Method to get a Vendor with the SessionId and the vendorId
     * @author Marco
     * @date 18.05.2015
     * @param sessionId
     * @param vendorId
     * @return VendorResponse Object
     */
    public VendorResponse getVendor(int sessionId, int vendorId);

    /**
     * method to create a vendor
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param vendorId only necessary for update
     * @param name
     * @param logo (base64 String)
     * @return
     */
    public VendorResponse createOrUpdateVendor(int sessionId, int vendorId, String name, String logo, String street, String city, int PLZ, int houseNumber) throws Exception;

    /**
     * Method to delete a vendor
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param vendorId
     * @return
     */
    public ReturnCodeResponse deleteVendor(int sessionId, int vendorId);





	/*#################      PAYMENT - SECTION     ##############*/

    /**
     * @author Marco
     * @date 19.05.2015
     * @param sessionId
     * @return PaymentListResponse Object
     */
    public PaymentListResponse getPayments(int sessionId) throws Exception;

    /**
     * Method to get a payment with the SessionId and the paymentId
     * @author Marco
     * @date 18.05.2015
     * @param sessionId
     * @param paymentId
     * @return PaymentResponse Object
     */
    public PaymentResponse getPayment(int sessionId, int paymentId);

    /**
     * Method to delete a payment
     * @author Marco
     * @param sessionId
     * @param paymentId
     * @return ReturnCodeResponse Object
     */
    public ReturnCodeResponse deletePayment(int sessionId, int paymentId);


    /**
     * method to create or update a payment
     * @author Marco
     * @author Moritz
     * @param sessionId
     * @param paymentId
     * @param name
     * @param number
     * @param bic
     * @param active
     * @return PaymentResponse
     */
    public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active) throws Exception;





	/*#################      CATEGORY - SECTION     ##############*/

    /**
     * @author Marco
     * @param sessionId
     * @param categoryId
     * @return
     */
    public CategoryResponse getCategory(int sessionId, int categoryId);

    /**
     * @author Marco
     * @date 19.05.2015
     * @param sessionId
     * @return CategoryListResponse Object
     */
    public CategoryListResponse getCategorys(int sessionId) throws Exception;

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param categoryId only necessary for update
     * @param income
     * @param active
     * @param name
     * @param notice
     * @return
     */
    public CategoryResponse createOrUpdateCategory(int sessionId, int categoryId, boolean income, boolean active, String name, String notice, String colour) throws Exception;

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public ReturnCodeResponse deleteCategory(int sessionId, int categoryId);





	/*#################      BASKET - SECTION     ##############*/

    /**
     * Gives a Response Object with all Baskets in a list
     * @author Marco
     * @date 19.05.2015
     * @param sessionId
     * @return BasketListResponse Object
     */
    public BasketListResponse getBaskets(int sessionId) throws Exception;

    /**
     * @author Marco
     * @date 18.05.2015
     * @param sessionId
     * @param basketID
     * @return Basket Object
     */
    public BasketResponse getBasket(int sessionId, int basketID);

    /**
     * Method to create a basket
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param basketId
     * @param name
     * @param notice
     * @param amount
     * @param purchaseDate
     * @param paymentId
     * @param vendorId
     * @param items   List with itemTO Objects to add to the basket
     * @return
     */
    public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId, List<ItemTO> items);

    /**
     * Method to delete a basket
     * @author marco
     * @date 26.05.2015
     * @param sessionId
     * @param basketID
     * @return
     */
    public ReturnCodeResponse deleteBasket(int sessionId, int basketID);

    /**
     * Gibt die letzten Baskets als Liste zur�ck
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param numberOfBaskets Anzahl der letzten auszugebenen Baskets
     * @return BasketListResponse Object
     */
    public BasketListResponse getLastBaskets(int sessionId, int numberOfBaskets);

    /**
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param vendorId
     * @return a list with all baskets of a vendor
     */
    public BasketListResponse getBasketsByVendor(int sessionId, int vendorId);

    /**
     * gets all baskets of the actual month
     * @author Marco
     * @param sessionId
     * @return
     */
    public BasketListResponse getBasketsOfActualMonth(int sessionId);


    /**
     * gets all baskets of a specific payment
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param paymentId
     * @return
     */
    public BasketListResponse getBasketsByPayment(int sessionId, int paymentId);








	/*#################      INCOME - SECTION     ##############*/

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param incomeId    only necessary for update
     * @param name
     * @param quantity
     * @param amount
     * @param notice
     * @param categoryId
     * @return
     */
    public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name, double  quantity, double amount, String notice, long receiptDate, int categoryId, BudgetAndroidApplication myApp) throws Exception;


    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param itemId
     * @return
     */
    public IncomeResponse getIncome(int sessionId, int itemId);

    /**
     * Method to get all incomes of a user
     * @author Marco
     * @date 09.06.2015
     * @param sessionId
     * @return
     */
    public IncomeListResponse getIncomes(int sessionId, BudgetAndroidApplication myApp) throws Exception;

    /**
     * Gibt die letzten Incomes als Liste zur�ck
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param numberOfIncome
     * @return IncomeListResponse
     */
    public IncomeListResponse getLastIncomes(int sessionId, int numberOfIncome);

    /**
     * gets all Incomes of a specific category for incomes
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public IncomeListResponse getIncomesByCategory(int sessionId, int categoryId);


    /**
     * gets all income of the actual month
     * @author Marco
     * @param sessionId
     * @return
     */
    public IncomeListResponse getIncomesOfActualMonth(int sessionId);


    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param itemID
     * @return
     */
    public ReturnCodeResponse deleteIncome(int sessionId, int itemID);



	/*#################      ITEM - SECTION     ##############*/

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param itemId
     * @param name
     * @param quantity
     * @param price
     * @param notice
     * @param basketId
     * @param categoryId
     * @return
     */
    public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double  quantity, double price, String notice, long receiptDate, int basketId, int categoryId);

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param lossId
     * @return
     */
    public ReturnCodeResponse deleteItem(int sessionId, int lossId);

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param itemId
     * @return
     */
    public ItemResponse getItemByBasket(int sessionId, int itemId, int basketId);

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param basketId
     * @return
     */
    public ItemListResponse getItemsByBasket(int sessionId, int basketId);


    /**
     * gets all Items of a specific category for losses
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public ItemListResponse getItemsByLossCategory(int sessionId, int categoryId);


	/*#################      XYZ - SECTION     ##############*/

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param daysOfPeriod
     * @return
     */
    public AmountResponse getLossByPeriod(int sessionId, int daysOfPeriod);

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param daysOfPeriod
     * @return
     */
    public AmountResponse getIncomeByPeriod(int sessionId, int daysOfPeriod);



}
