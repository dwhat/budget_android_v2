package de.budget.BudgetService;



//Java Datentypen Import
import java.util.List;

import de.budget.BudgetService.Response.AmountListResponse;
import de.budget.BudgetService.Response.AmountResponse;
import de.budget.BudgetService.Response.BasketListResponse;
import de.budget.BudgetService.Response.BasketResponse;
import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.BudgetService.Response.CategoryResponse;
import de.budget.BudgetService.Response.IncomeListResponse;
import de.budget.BudgetService.Response.IncomeResponse;
import de.budget.BudgetService.Response.ItemListResponse;
import de.budget.BudgetService.Response.ItemResponse;
import de.budget.BudgetService.Response.PaymentListResponse;
import de.budget.BudgetService.Response.PaymentResponse;
import de.budget.BudgetService.Response.ReturnCodeResponse;
import de.budget.BudgetService.Response.UserLoginResponse;
import de.budget.BudgetService.Response.UserResponse;
import de.budget.BudgetService.Response.VendorListResponse;
import de.budget.BudgetService.Response.VendorResponse;
import de.budget.BudgetService.dto.ItemTO;


/**
 * Webservice Interface BudgetOnlineService
 * @author Marco
 * @version 1
 */
public interface BudgetOnlineService2 {

		
	/*#################      USER - SECTION     ##############*/

    /**
     * Method to Register a new User
     * <p> Author: Marco </p>
     * @param username
     * @param password
     * @param email
     * @return UserLoginResponse
     *
     */
    public UserLoginResponse setUser(String username, String password, String email);


    /**
     * Method to login with Username and Password
     * <p> Author: Marco </p>
     * @param username
     * @param password
     * @return UserLoginResponse
     */
    public UserLoginResponse login(String username, String password);


    /**
     * Method to log out
     * <p> Author: Marco </p>
     * @param sessionID
     * @return ReturnCodeResponse
     */
    public ReturnCodeResponse logout(int sessionID);


    /**
     * Method to get a User by name
     * <p> Author: Marco </p>
     * @param sessionId
     * @param userName
     * @return UserResponse
     */
    public UserResponse getUserByName(int sessionId, String userName);

    /**
     * Method to delete the own user
     * <p> Author: Marco </p>
     * @param sessionId
     * @param username
     * @return ReturnCodeResponse
     */
    public ReturnCodeResponse deleteUser(int sessionId, String username);

		
	/*#################      VENDOR - SECTION     ##############*/

    /**
     * Method to get a special vendor
     * <p> Author: Marco </p>
     * @param sessionId
     * @return VendorListResponse Object
     */
    public VendorListResponse getVendors(int sessionId);

    /**
     * Method to get a Vendor with the SessionId and the vendorId
     * <p> Author: Marco </p>
     * @param sessionId
     * @param vendorId
     * @return VendorResponse Object
     */
    public VendorResponse getVendor(int sessionId, int vendorId);

    /**
     * Method to create a vendor
     * <p> Author: Marco </p>
     * @param sessionId
     * @param vendorId
     * @param name
     * @param logo
     * @param street
     * @param city
     * @param PLZ
     * @param houseNumber
     * @return VendorResponse
     */
    public VendorResponse createOrUpdateVendor(int sessionId, int vendorId, String name, String logo, String street, String city, int PLZ, int houseNumber);

    /**
     * Method to delete a vendor
     * <p> Author: Marco </p>
     * @param sessionId
     * @param vendorId
     * @return ReturnCodeResponse
     */
    public ReturnCodeResponse deleteVendor(int sessionId, int vendorId);

    /**
     * Methode um die Betr�ge pro Vendor zur�ck zugeben
     * <p> Author: Marco </p>
     * @param sessionId
     * @return AmountListResponse
     */
    public AmountListResponse getAmountForVendors(int sessionId);

	
	/*#################      PAYMENT - SECTION     ##############*/

    /**
     * Method to get all payments of a user
     * <p> Author: Marco </p>
     * @param sessionId
     * @return PaymentListResponse Object
     */
    public PaymentListResponse getPayments(int sessionId);

    /**
     * Method to get a payment with the SessionId and the paymentId
     * <p> Author: Marco </p>
     * @param sessionId
     * @param paymentId
     * @return PaymentResponse Object
     */
    public PaymentResponse getPayment(int sessionId, int paymentId);

    /**
     * Method to delete a payment
     * <p> Author: Marco </p>
     * @param sessionId
     * @param paymentId
     * @return ReturnCodeResponse Object
     */
    public ReturnCodeResponse deletePayment(int sessionId, int paymentId);


    /**
     * method to create or update a payment
     * <p> Author: Marco </p>
     * <p> Author: Moritz </p>
     * @param sessionId
     * @param paymentId
     * @param name
     * @param number
     * @param bic
     * @param active
     * @return PaymentResponse
     */
    public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active);

	/*#################      CATEGORY - SECTION     ##############*/

    /**
     * Method to get a special category of a user
     * <p> Author: Marco </p>
     * @param sessionId
     * @param categoryId
     * @return CategoryResponse
     */
    public CategoryResponse getCategory(int sessionId, int categoryId);

    /**
     * Method to get all categories of a user
     * <p> Author: Marco </p>
     * @param sessionId
     * @return CategoryListResponse Object
     */
    public CategoryListResponse getCategorys(int sessionId);

    /**
     * Method to get all Categories of a use where income is true
     * <p> Author: Marco </p>
     * @param sessionId
     * @return CategoryListResponse
     */
    public CategoryListResponse getCategorysOfIncome(int sessionId);

    /**
     * Method to get all Categories of a use where income is false
     * <p> Author: Marco </p>
     * @param sessionId
     * @return CategoryListResponse
     */
    public CategoryListResponse getCategorysOfLoss(int sessionId);

    /**
     * Method to create or update d category
     * <p> Author: Marco </p>
     * @param sessionId
     * @param categoryId
     * @param income true if income, false if loss
     * @param active
     * @param name
     * @param notice
     * @param colour
     * @return CategoryResponse
     */
    public CategoryResponse createOrUpdateCategory(int sessionId, int categoryId, boolean income, boolean active, String name, String notice, String colour);

    /**
     * Method to delete a category
     * <p> Author: Marco </p>
     * @param sessionId
     * @param categoryId
     * @return ReturnCodeResponse
     */
    public ReturnCodeResponse deleteCategory(int sessionId, int categoryId);
	
	/*#################      BASKET - SECTION     ##############*/

    /**
     * Gives a Response Object with all Baskets in a list
     * <p> Author: Marco </p>
     * @param sessionId
     * @return BasketListResponse Object
     */
    public BasketListResponse getBaskets(int sessionId);

    /**
     * Method to delete a basket
     * <p> Author: Marco </p>
     * @param sessionId
     * @param basketId
     * @return BasketResponse Object
     */
    public BasketResponse getBasket(int sessionId, int basketId);

    /**
     * Method to create a basket
     * <p> Author: Marco </p>
     * @param sessionId
     * @param basketId
     * @param name
     * @param notice
     * @param amount
     * @param purchaseDate
     * @param paymentId
     * @param vendorId
     * @param items   List with itemTO Objects to add to the basket
     * @return BasketResponse
     */
    public BasketResponse createOrUpdateBasketList(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId, List<ItemTO> items);

    /**
     * Method to create a basket
     * <p> Author: Marco </p>
     * @param sessionId
     * @param basketId
     * @param name
     * @param notice
     * @param amount
     * @param purchaseDate
     * @param paymentId
     * @param vendorId
     * @return BasketResponse
     */
    public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId);

    /**
     * Method to delete a basket
     * <p> Author: Marco </p>
     * @param sessionId
     * @param basketId
     * @return ReturnCodeResponse
     */
    public ReturnCodeResponse deleteBasket(int sessionId, int basketId);

    /**
     * Gibt die letzten Baskets als Liste zur�ck
     * <p> Author: Marco </p>
     * @param sessionId
     * @param startPosition  startPosition of the incomes sorted by date
     * @param endPosition  endPosition of the incomes sorted by date
     * @return BasketListResponse Object
     */
    public BasketListResponse getLastBaskets(int sessionId, int startPosition, int endPosition);

    /**
     * Method to find all baskets of a vendor
     * <p> Author: Marco </p>
     * @param sessionId
     * @param vendorId
     * @return BasketListResponse - a list with all baskets of a vendor
     */
    public BasketListResponse getBasketsByVendor(int sessionId, int vendorId);

    /**
     * gets the sum of all basketsamounts of the actual month
     * <p> Author: Marco </p>
     * @param sessionId
     * @return AmountResponse
     */
    public AmountResponse getBasketsOfActualMonth(int sessionId);


    /**
     * gets all baskets of a specific payment
     * <p> Author: Marco </p>
     * @param sessionId
     * @param paymentId
     * @return BasketListResponse - a list with all baskets of a payment
     */
    public BasketListResponse getBasketsByPayment(int sessionId, int paymentId);

	/*#################      INCOME - SECTION     ##############*/

    /**
     * Method to create or update an income
     * <p> Author: Marco </p>
     * @param sessionId
     * @param incomeId
     * @param name
     * @param quantity
     * @param amount
     * @param notice
     * @param receiptDate
     * @param categoryId
     * @return IncomeResponse
     */
    public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name, double  quantity, double amount, String notice, long receiptDate, int categoryId);

    /**
     * Method to get a special income
     * <p> Author: Marco </p>
     * @param sessionId
     * @param itemId
     * @return Incomeresponse
     */
    public IncomeResponse getIncome(int sessionId, int itemId);

    /**
     * Method to get all incomes of a user
     * <p> Author: Marco </p>
     * @param sessionId
     * @return IncomeListResponse
     */
    public IncomeListResponse getIncomes(int sessionId);

    /**
     * Gibt die letzten Incomes als Liste zur�ck
     * <p> Author: Marco </p>
     * @param sessionId
     * @param startPosition  startPosition of the incomes sorted by date
     * @param endPosition  endPosition of the incomes sorted by date
     * @return IncomeListResponse
     */
    public IncomeListResponse getLastIncomes(int sessionId, int startPosition, int endPosition);

    /**
     * Method to get all Incomes of a specific category for incomes
     * <p> Author: Marco </p>
     * @param sessionId
     * @param categoryId
     * @return IncomeListResponse
     */
    public IncomeListResponse getIncomesByCategory(int sessionId, int categoryId);

    /**
     * Method to get the Amount of all income, which are assigned to a special category
     * <p> Author: Marco </p>
     * @param sessionId
     * @return AmountListResponse
     */
    public AmountListResponse getIncomesAmountForCategories(int sessionId);

    /**
     * gets the sum of all income of the actual month
     * <p> Author: Marco </p>
     * @param sessionId
     * @return AmountResponse
     */
    public AmountResponse getIncomesOfActualMonth(int sessionId);


    /**
     * Method to delete an income
     * <p> Author: Marco </p>
     * @param sessionId
     * @param incomeId
     * @return ReturnCodeResponse
     */
    public ReturnCodeResponse deleteIncome(int sessionId, int incomeId);
	
	/*#################      ITEM - SECTION     ##############*/

    /**
     * Method to create or update an item
     * <p> Author: Marco </p>
     * @param sessionId
     * @param itemId
     * @param name
     * @param quantity
     * @param price
     * @param notice
     * @param receiptDate
     * @param basketId
     * @param categoryId
     * @return ItemResponse
     */
    public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double  quantity, double price, String notice, long receiptDate, int basketId, int categoryId);

    /**
     * Method to delete an item
     * <p> Author: Marco </p>
     * @param sessionId
     * @param lossId
     * @return ReturnCodeResponse
     */
    public ReturnCodeResponse deleteItem(int sessionId, int lossId);

    /**
     * Method to get a special item out of the basket
     * <p> Author: Marco </p>
     * @param sessionId
     * @param itemId
     * @param basketId
     * @return ItemResponse
     */
    public ItemResponse getItemByBasket(int sessionId, int itemId, int basketId);

    /**
     * Method to get all items of a basket
     * <p> Author: Marco </p>
     * @param sessionId
     * @param basketId
     * @return ItemListResponse
     */
    public ItemListResponse getItemsByBasket(int sessionId, int basketId);


    /**
     * Method to ge all Items of a specific category for losses
     * <p> Author: Marco </p>
     * @param sessionId
     * @param categoryId
     * @return ItemListResponse
     */
    public ItemListResponse getItemsByLossCategory(int sessionId, int categoryId);

    /**
     * Method to get the amount of all items
     * <p> Author: Marco </p>
     * @param sessionId
     * @return AmountListResponse
     */
    public AmountListResponse getItemsAmountForCategories(int sessionId);

	
	/*#################      XYZ - SECTION     ##############*/

    /**
     * Method to get the amount of all investments of a period
     * <p> Author: Marco </p>
     * @param sessionId
     * @param daysOfPeriod
     * @return AmountResponse
     */
    public AmountResponse getLossByPeriod(int sessionId, int daysOfPeriod);

    /**
     * Method to get the amount of all income of a period
     * <p> Author: Marco </p>
     * @param sessionId
     * @param daysOfPeriod
     * @return AmountResponse
     */
    public AmountResponse getIncomeByPeriod(int sessionId, int daysOfPeriod);



}

