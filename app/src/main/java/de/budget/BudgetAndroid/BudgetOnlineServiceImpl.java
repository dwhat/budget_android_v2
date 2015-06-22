package de.budget.BudgetAndroid;

import android.util.Log;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetService.BudgetOnlineService;
import de.budget.BudgetService.BudgetOnlineServiceOld;
import de.budget.BudgetService.Exception.InvalidLoginException;
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
import de.budget.BudgetService.constants.BasketTOConstants;
import de.budget.BudgetService.dto.*;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * <p>
 *     Implementierung des Interfaces.
 *     Es wird mit Hilfe einer zentralen Methode ksoap Objecte an den Server geschickt.
 *     Die Objekte werden mit Hilfe der einzelnen Methoden erzeugt.
 *     Die Klasse hält den Namespace sowie die Url zum Webservice bereit.
 *
 *     Die Methoden bestehen aus einem Methodennamen, der auf dem Server ausgeführt werden soll,
 *     sowie eines SoapObjects zur Abhandlung des Response vom Server.
 *     die Methoden liefern entsprechende ResponseObjecte an den Aufrufer.
 *     Die ResponseObjecte erhalten den entsprehcenden Returncode des response des Servers.
 *
 *     Exceptions werden nicht geworfen, es wurde sich darauf geeinigt Returncodes zu verwenden.
 * </p>
 * @date 01.06.2015
 */
public class BudgetOnlineServiceImpl implements BudgetOnlineService {

    private static final String NAMESPACE = "http://onlinebudget.budget.de/";

    //private static final String URL = "http://10.0.2.2:8080/budget/BudgetOnlineServiceBean";
    private static final String URL = "http://85.214.64.59:8080/budget/BudgetOnlineServiceBean";

    private static final String TAG = BudgetOnlineServiceImpl.class.getName();

    private int tmp, rt;

    private String METHOD_NAME;
    private SoapObject response = null;
    private int returnCode;

    Calendar calendar = new GregorianCalendar();

    private BudgetAndroidApplication myApp;

    public BudgetOnlineServiceImpl (BudgetAndroidApplication myApp) {
        this.myApp = myApp;
    }

    /*#################      USER - SECTION     ##############*/

    @Override
    public UserLoginResponse setUser(String username, String password, String email) {

        UserLoginResponse result = new UserLoginResponse();
        SoapObject response = null;
        String METHOD_NAME = "setUser";
        int returnCode = 0;
        int sessionId;

        try {

            response = executeSoapAction(METHOD_NAME, username, password, email);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            if (returnCode == 200) {
                sessionId = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));
                result.setSessionId(sessionId);
            }


        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
            return result;
        }

        return result;
    }

    @Override
    public UserLoginResponse login(String username, String password) {

        UserLoginResponse result = new UserLoginResponse();
        SoapObject response = null;
        String METHOD_NAME = "login";
        int returnCode = 0;
        int sessionId;

        try {
            response = executeSoapAction(METHOD_NAME, username, password);

            if (response == null) return null;

            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            sessionId = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));

            if (sessionId != 0) {
                result.setSessionId(sessionId);
            }

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;

    }

    @Override
    public ReturnCodeResponse logout(int sessionID) {

        ReturnCodeResponse result = new ReturnCodeResponse();
        SoapObject response = null;
        String METHOD_NAME = "logout";
        int returnCode = 0;

        try {
            response = executeSoapAction(METHOD_NAME, sessionID);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    @Override
    public UserResponse getUserByName(int sessionId, String userName) {
        return null;
    }

    @Override
    public ReturnCodeResponse deleteUser(int sessionId, String username) {
        return null;
    }



	/*#################      VENDOR - SECTION     ##############*/

    /**
     * @author Christopher
     * @date 19.05.2015
     * @param sessionId
     * @return VendorListResponse Object
     */
    public VendorListResponse getVendors(int sessionId) {

        VendorListResponse result = new VendorListResponse();
        SoapObject response = null;
        String METHOD_NAME = "getVendors";
        int returnCode = 0;

        ArrayList<VendorTO> vendorList = new ArrayList<>();


        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    for (int idx = 1; idx < propertyCount; idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        vendorList.add(parseVendorResponse(ListObject));
                    }
                }
            }

            result.setVendorList(vendorList);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    private VendorTO parseVendorResponse (SoapObject ListObject) {

        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
        String name = ListObject.getPrimitivePropertySafelyAsString("name");
        String street = ListObject.getPrimitivePropertySafelyAsString("street");
        String city = ListObject.getPrimitivePropertySafelyAsString("city");
        int plz = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("PLZ"));
        int hn = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("houseNumber"));


        return new VendorTO(id, name, street, city, plz, hn);

    }

    /**
     * Method to get a Vendor with the SessionId and the vendorId
     * @author Christopher
     * @date 18.05.2015
     * @param sessionId
     * @param vendorId
     * @return VendorResponse Object
     */
    public VendorResponse getVendor(int sessionId, int vendorId){
        return null;
    }

    /**
     * method to create a vendor
     * @author Christopher
     * @date 09.06.2015
     * @param sessionId
     * @param vendorId only necessary for update
     * @param name
     * @param logo (base64 String)
     * @return
     */
    public VendorResponse createOrUpdateVendor(int sessionId, int vendorId, String name, String logo, String street, String city, int plz, int houseNumber) {

        VendorResponse result = new VendorResponse();
        SoapObject response = null;
        String METHOD_NAME = "createOrUpdateVendor";
        int returnCode = 0;

        try {
            response = executeSoapAction(METHOD_NAME, sessionId, vendorId, name, logo, street, city, plz, houseNumber);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            SoapObject vendorResponse = (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(vendorResponse.getPrimitivePropertySafelyAsString("id"));

            if (returnCode == 200) {
                Long createDate = calendar.getTimeInMillis();
                Long lastChanged = calendar.getTimeInMillis();
                result.setVendorTo(new VendorTO(id, name, createDate, lastChanged, street, city, plz, houseNumber));
            }

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Method to delete a vendor
     * @author Christopher
     * @date 16.06.2015
     * @param sessionId
     * @param vendorId
     * @return
     */
    public ReturnCodeResponse deleteVendor(int sessionId, int vendorId) {
        return deleteObject(sessionId, vendorId, "deleteVendor");
    }



	/*#################      PAYMENT - SECTION     ##############*/

    /**
     * @author Christopher
     * @date 19.05.2015
     * @param sessionId
     * @return PaymentListResponse Object
     */
    public PaymentListResponse getPayments(int sessionId) {
        PaymentListResponse result = new PaymentListResponse();
        SoapObject response = null;
        String METHOD_NAME = "getPayments";
        int returnCode = 0;

        ArrayList<PaymentTO> paymentList = new ArrayList<>();

        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    for (int idx = 1; idx < propertyCount; idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        paymentList.add(parsePayment(ListObject));
                    }
                }
            }

            result.setPaymentList(paymentList);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public PaymentTO parsePayment (SoapObject ListObject) {
        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
        String name = ListObject.getPrimitivePropertySafelyAsString("name");
        boolean active = Boolean.parseBoolean(ListObject.getPrimitivePropertySafelyAsString("active"));
        String bic = ListObject.getPrimitivePropertySafelyAsString("bic");
        String number = ListObject.getPrimitivePropertySafelyAsString("number");

        return new PaymentTO(id, name, number, bic, active);
    }

    /**
     * Method to get a payment with the SessionId and the paymentId
     * @author Christopher
     * @date 18.05.2015
     * @param sessionId
     * @param paymentId
     * @return PaymentResponse Object
     */
    public PaymentResponse getPayment(int sessionId, int paymentId){
        return null;
    }

    /**
     * Method to delete a payment
     * @author Christopher
     * @param sessionId
     * @param paymentId
     * @return ReturnCodeResponse Object
     */
    public ReturnCodeResponse deletePayment(int sessionId, int paymentId){
        return null;
    }


    /**
     * method to create or update a payment
     * @author Christopher
     * @author Moritz
     * @param sessionId
     * @param paymentId
     * @param name
     * @param number
     * @param bic
     * @param active
     * @return PaymentResponse
     */
    public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active) {

        PaymentResponse result = new PaymentResponse();
        SoapObject response = null;
        String METHOD_NAME = "createOrUpdatePayment";
        int returnCode = 0;

        try {
            response = executeSoapAction(METHOD_NAME, sessionId, paymentId, name, number, bic, active);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            SoapObject vendorResponse = (SoapObject) response.getProperty(1);

            int id = Integer.parseInt(vendorResponse.getPrimitivePropertySafelyAsString("id"));

            if (returnCode == 200) {
                result.setPaymentTo(new PaymentTO(id, name, number, bic, active));
            }

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }





	/*#################      CATEGORY - SECTION     ##############*/

    /**
     * @author Christopher
     * @param sessionId
     * @param categoryId
     * @return
     */
    public CategoryResponse getCategory(int sessionId, int categoryId){
        return null;
    }

    /**
     * @author Christopher
     * @date 09.06.2015
     * @param sessionId
     * @return CategoryListResponse Object
     */
    public CategoryListResponse getCategorys(int sessionId) {
        CategoryListResponse result = new CategoryListResponse();
        SoapObject response = null;
        String METHOD_NAME = "getCategorys";
        int returnCode = 0;

        ArrayList<CategoryTO> categoryList = new ArrayList<>();

        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    for (int idx = 1; idx < propertyCount; idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        categoryList.add(parseCategoryResponse(ListObject));
                    }
                }
            }

            result.setCategoryList(categoryList);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    private CategoryTO parseCategoryResponse (SoapObject ListObject) {

        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
        String name = ListObject.getPrimitivePropertySafelyAsString("name");
        String notice = ListObject.getPrimitivePropertySafelyAsString("notice");
        String activeString = ListObject.getPrimitivePropertySafelyAsString("active");
        boolean active = "true".equals(activeString) ? true : false;
        boolean income = Boolean.parseBoolean(ListObject.getPrimitivePropertySafelyAsString("income"));
        String color = ListObject.getPrimitivePropertySafelyAsString("colour");

        Long createDate = calendar.getTimeInMillis();
        Long lastChanged = calendar.getTimeInMillis();

        return new CategoryTO(id, name, notice, active, income, createDate, lastChanged, null, color);
    }

    /**
     * @author Christopher
     * @date 09.06.2015
     * @param sessionId
     * @param categoryId only necessary for update
     * @param income
     * @param active
     * @param name
     * @param notice
     * @return
     */
    public CategoryResponse createOrUpdateCategory(int sessionId, int categoryId, boolean income, boolean active, String name, String notice, String color){
        CategoryResponse result = new CategoryResponse();
        SoapObject response = null;
        String METHOD_NAME = "createOrUpdateCategory";
        int returnCode = 0;

        try {
            response = executeSoapAction(METHOD_NAME, sessionId, categoryId, income, active, name, notice, color);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            SoapObject categoryResponse = (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(categoryResponse.getPrimitivePropertySafelyAsString("id"));

            if (returnCode == 200) {
                Long createDate = calendar.getTimeInMillis();
                Long lastChanged = calendar.getTimeInMillis();
                result.setCategoryTo(new CategoryTO(id, name, notice, active, income, createDate, lastChanged, color));
            }

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * @author Christopher
     * @date 16.06.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public ReturnCodeResponse deleteCategory(int sessionId, int categoryId) {
        return deleteObject(sessionId, sessionId, "deleteCategory");
    }

    /**
     * Method to get all Categories of a use where income is true
     * @author Christopher
     * @date 19.06.2015
     * @param sessionId
     * @return
     */
    public CategoryListResponse getCategorysOfIncome(int sessionId){return null;}

    /**
     * Method to get all Categories of a use where income is false
     * @author Christopher
     * @date 19.06.2015
     * @param sessionId
     * @return
     */
    public CategoryListResponse getCategorysOfLoss(int sessionId){return null;}






	/*#################      BASKET - SECTION     ##############*/


    /**
     * Gives a Response Object with all Baskets in a list
     * @author Mark
     * @date 17.06.2015
     * @param sessionId
     * @return BasketListResponse Object
     */
    @Author(name="Mark")
    public BasketListResponse getBaskets(int sessionId) {

        BasketListResponse result = new BasketListResponse();
        String METHOD_NAME = "getBaskets";
        SoapObject response = null;

        // Create new List for Basket Obejcts
        ArrayList<BasketTO> basketList = new ArrayList<>();

        try {
            // Get Response from SOAP Object specified Method Name
            response = executeSoapAction(METHOD_NAME, sessionId);
            // Get Return Code from SOAP Object
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            // Set Returncode for successs
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    for (int idx = 1; idx < propertyCount; idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        Log.d("BASKET RESPONSE", ListObject.toString());
                        basketList.add(parseBasket(ListObject));
                    }
                }
            }

            result.setBasketList(basketList);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;

    }

    public BasketTO parseBasket (SoapObject ListObject) {
        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString(BasketTOConstants.ID));
        String name = ListObject.getPrimitivePropertySafelyAsString(BasketTOConstants.NAME);
        String notice = ListObject.getPrimitivePropertySafelyAsString(BasketTOConstants.NOTICE);
        double amount = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString(BasketTOConstants.AMOUNT));
        long createDate = Long.parseLong(ListObject.getPrimitivePropertySafelyAsString(BasketTOConstants.CREATE_DATE));
        long purchaseDate = Long.parseLong(ListObject.getPrimitivePropertySafelyAsString(BasketTOConstants.PURCHASE_DATE));
        long lastChanged = Long.parseLong(ListObject.getPrimitivePropertySafelyAsString(BasketTOConstants.LAST_CHANGED));

        SoapObject payment = (SoapObject) ListObject.getProperty(BasketTOConstants.PAYMENT);
        int paymentId = Integer.parseInt(payment.getPrimitivePropertyAsString("id"));

        SoapObject vendor = (SoapObject) ListObject.getProperty(BasketTOConstants.VENDOR);
        int vendorId = Integer.parseInt(payment.getPrimitivePropertyAsString("id"));

        VendorTO vendorTO = myApp.getVendorById(vendorId);
        PaymentTO paymentTO = myApp.getPaymentById(paymentId);

        return new BasketTO(id, name, notice, amount, createDate, purchaseDate, lastChanged, null, vendorTO, paymentTO, null);
    }

    /**
     * @author Christopher
     * @date 18.05.2015
     * @param sessionId
     * @param basketID
     * @return Basket Object
     */
    public BasketResponse getBasket(int sessionId, int basketID){
        return null;
    }

    /**
     * Method to create a basket
     * <p> Author: Marco </p>
     *
     * @param sessionId
     * @param basketId
     * @param name
     * @param notice
     * @param amount
     * @param purchaseDate
     * @param paymentId
     * @param vendorId
     * @param items        List with itemTO Objects to add to the basket
     * @return BasketResponse
     */
    @Override
    public BasketResponse createOrUpdateBasketList(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId, List<ItemTO> items) {
        return null;
    }

    /**
     * Method to create a basket
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param basketId
     * @param name
     * @param notice
     * @param amount
     * @param purchaseDate
     * @param paymentId
     * @param vendorId
     * @return
     */
    public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId) {

        BasketResponse result = new BasketResponse();
        SoapObject response = null;
        String METHOD_NAME = "createOrUpdateBasket";
        int returnCode = 0;

        try {

            response = executeSoapAction(METHOD_NAME, sessionId, basketId, name, notice, amount, purchaseDate, paymentId, vendorId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            SoapObject basketResponse = (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(basketResponse.getPrimitivePropertySafelyAsString("id"));

            if (returnCode == 200) {

                Long createDate = calendar.getTimeInMillis();
                Long lastChanged = calendar.getTimeInMillis();
                result.setBasketTo(new BasketTO(id, name, notice, amount, createDate, purchaseDate, lastChanged, null, myApp.getVendorById(vendorId), myApp.getPaymentById(paymentId)));
            }
        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }


    /**
     * Method to delete a basket
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param basketId
     * @return
     */
    public ReturnCodeResponse deleteBasket(int sessionId, int basketId) {
        return deleteObject(sessionId, basketId, "deleteBasket");
    }

    /**
     * Gibt die letzten Baskets als Liste zur�ck
     * <p> Author: Marco </p>
     *
     * @param sessionId
     * @param startPosition startPosition of the incomes sorted by date
     * @param endPosition   endPosition of the incomes sorted by date
     * @return BasketListResponse Object
     */
    @Override
    public BasketListResponse getLastBaskets(int sessionId, int startPosition, int endPosition) {
        return null;
    }


    /**
     * Method to delete a basket
     * @author Mark
     * @date 20.06.2015
     * @param sessionId
     * @param objectId
     * @param method
     * @return
     */
    public ReturnCodeResponse deleteObject (int sessionId, int objectId, String method) {

        ReturnCodeResponse result = new ReturnCodeResponse();
        SoapObject response = null;
        String METHOD_NAME = method;
        int returnCode = 0;

        try {
            response = executeSoapAction(METHOD_NAME, sessionId, objectId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Gibt die letzten Baskets als Liste zur�ck
     * @author Christopher
     * @date 29.05.2015
     * @param sessionId
     * @param numberOfBaskets Anzahl der letzten auszugebenen Baskets
     * @return BasketListResponse Object
     */
    public BasketListResponse getLastBaskets(int sessionId, int numberOfBaskets){
        return null;
    }

    /**
     * @author Christopher
     * @date 29.05.2015
     * @param sessionId
     * @param vendorId
     * @return a list with all baskets of a vendor
     */
    public BasketListResponse getBasketsByVendor(int sessionId, int vendorId){
        return null;
    }

    /**
     * gets all baskets of the actual month
     * @author Christopher
     * @param sessionId
     * @return
     */
    public AmountResponse getBasketsOfActualMonth(int sessionId){
        return null;
    }


    /**
     * gets all baskets of a specific payment
     * @author Christopher
     * @date 29.05.2015
     * @param sessionId
     * @param paymentId
     * @return
     */
    public BasketListResponse getBasketsByPayment(int sessionId, int paymentId){
        return null;
    }

    

	/*#################      INCOME - SECTION     ##############*/

    /**
     * @author Christopher
     * @date 14.06.2015
     * @param sessionId
     * @param incomeId    only necessary for update
     * @param name
     * @param quantity
     * @param amount
     * @param notice
     * @param categoryId
     * @return
     */
    public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name, double  quantity, double amount, String notice, long receiptDate, int categoryId) {
        IncomeResponse result = new IncomeResponse();
        SoapObject response = null;
        String METHOD_NAME = "createOrUpdateIncome";
        int returnCode = 0;
        
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, incomeId, name, quantity, amount, notice, receiptDate, categoryId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            
            SoapObject incomeResponse= (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(incomeResponse.getPrimitivePropertySafelyAsString("id"));

            if (returnCode == 200) {
                result.setIncomeTo(new IncomeTO(id, name, quantity, amount, notice, receiptDate, null, myApp.getCategory(categoryId)));
            }
            
        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }


    /**
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param itemId
     * @return
     */
    public IncomeResponse getIncome(int sessionId, int itemId){
        return null;
    }

    /**
     * Method to get all incomes of a user
     * @author Christopher
     * @date 09.06.2015
     * @param sessionId
     * @return
     */
    public IncomeListResponse getIncomes(int sessionId) {

        IncomeListResponse result = new IncomeListResponse();
        SoapObject response = null;
        String METHOD_NAME = "getIncomes";
        int returnCode = 0;

        ArrayList<IncomeTO> incomeList = new ArrayList<>();

        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    for (int idx = 1; idx < propertyCount; idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        incomeList.add(parseIncome(ListObject));
                    }
                }
            }

            result.setIncomeList(incomeList);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;

    }

    /**
     * Gibt die letzten Incomes als Liste zur�ck
     * <p> Author: Marco </p>
     *
     * @param sessionId
     * @param startPosition startPosition of the incomes sorted by date
     * @param endPosition   endPosition of the incomes sorted by date
     * @return IncomeListResponse
     */
    @Override
    public IncomeListResponse getLastIncomes(int sessionId, int startPosition, int endPosition) {
        return null;
    }

    private IncomeTO parseIncome (SoapObject ListObject) {
        String name = ListObject.getPrimitivePropertySafelyAsString("name");
        String notice = ListObject.getPrimitivePropertySafelyAsString("notice");
        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
        Double amount = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString("amount"));
        Double quantity = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString("quantity"));
        Long receiptDate = Long.parseLong(ListObject.getPrimitivePropertySafelyAsString("receiptDate"));

        // Kategorie holen
        SoapObject CategoryObject = (SoapObject) ListObject.getProperty("category");
        int categoryId = Integer.parseInt(CategoryObject.getPrimitivePropertyAsString("id"));

        return new IncomeTO(id, name, quantity, amount, notice, receiptDate, null, myApp.getCategory(categoryId));
    }

    /**
     * Gibt die letzten Incomes als Liste zur�ck
     * @author Christopher
     * @date 29.05.2015
     * @param sessionId
     * @param numberOfIncome
     * @return IncomeListResponse
     */
    public IncomeListResponse getLastIncomes(int sessionId, int numberOfIncome){
        return null;
    }

    /**
     * gets all Incomes of a specific category for incomes
     * @author Christopher
     * @date 29.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public IncomeListResponse getIncomesByCategory(int sessionId, int categoryId){
        return null;
    }

    /**
     * Method to get the Amount of all income, which are assigned to a special category
     * <p> Author: Marco </p>
     *
     * @param sessionId
     * @return AmountListResponse
     */
    @Override
    public AmountListResponse getIncomesAmountForCategories(int sessionId) {
        return getObjectAmountForCategories(sessionId, "getIncomesAmountForCategories");
    }


    /**
     * Method to get the Amount of all income, which are assigned to a special category
     * @author Marco
     * @date 18.06.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public AmountResponse getIncomeAmountByCategory(int sessionId, int categoryId){return null;};

    /**
     * gets all income of the actual month
     * @author Christopher
     * @param sessionId
     * @return
     */
    public AmountResponse getIncomesOfActualMonth(int sessionId){
        return null;
    }


    /**
     * @author Christopher
     * @date 16.06.2015
     * @param sessionId
     * @param incomeId
     * @return
     */
    public ReturnCodeResponse deleteIncome(int sessionId, int incomeId) {
        return deleteObject(sessionId, incomeId, "deleteIncome");

    }



	/*#################      ITEM - SECTION     ##############*/

    /**
     * @author Christopher
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
    public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double  quantity, double price, String notice, long receiptDate, int basketId, int categoryId) {
        ItemResponse result = new ItemResponse();
        SoapObject response = null;
        String METHOD_NAME = "createOrUpdateItem";
        int returnCode = 0;

        try {
            response = executeSoapAction(METHOD_NAME, sessionId, itemId, name,  quantity, price, notice, receiptDate, basketId, categoryId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            SoapObject itemResponse = (SoapObject) response.getProperty(1);

            int id = Integer.parseInt(itemResponse.getPrimitivePropertySafelyAsString("id"));

            if (returnCode == 200) {
                result.setItemTo(new ItemTO(name, quantity, price, notice, receiptDate, basketId, categoryId));
            }

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param lossId
     * @return
     */
    public ReturnCodeResponse deleteItem(int sessionId, int lossId){
        return null;
    }

    /**
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param itemId
     * @return
     */
    public ItemResponse getItemByBasket(int sessionId, int itemId, int basketId){
        return null;
    }

    /**
     * @author Mark
     * @date 19.06.2015
     * @param sessionId
     * @param basketId
     * @return
     */
    public ItemListResponse getItemsByBasket(int sessionId, int basketId) {

        ItemListResponse result = new ItemListResponse();
        SoapObject response = null;
        String METHOD_NAME = ItemTO.GET_ITEMS_BY_BASKET;
        int returnCode = 0;

        // Create new List for Basket Obejcts
        ArrayList<ItemTO> itemList = new ArrayList<>();

        try {

            // Get Response from SOAP Object specified Method Name
            response = executeSoapAction(METHOD_NAME, sessionId, basketId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    for (int idx = 1; idx < propertyCount; idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        itemList.add(parseItem(ListObject, basketId));
                    }
                }
            }

            result.setItemList(itemList);

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    private ItemTO parseItem (SoapObject ListObject, int basketId) {
        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString(ItemTO.ID));
        String name = ListObject.getPrimitivePropertySafelyAsString(ItemTO.NAME);
        double quantity = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString(ItemTO.QUANTITY));
        double price = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString(ItemTO.PRICE));
        String notice = ListObject.getPrimitivePropertySafelyAsString(ItemTO.NOTICE);
        long receiptDate = Long.parseLong(ListObject.getPrimitivePropertySafelyAsString(ItemTO.RECEIPTDATE));
        int categoryId = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString(ItemTO.CATEGORY));

        return new ItemTO(id, name, quantity, price, notice, receiptDate, basketId, categoryId);

    }


    /**
     * gets all Items of a specific category for losses
     * @author Christopher
     * @date 29.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public ItemListResponse getItemsByLossCategory(int sessionId, int categoryId){
        return null;
    }


	/*#################      XYZ - SECTION     ##############*/

    /**
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param daysOfPeriod
     * @return
     */
    public AmountResponse getLossByPeriod(int sessionId, int daysOfPeriod) {
        return getObjectByPeriod(sessionId, daysOfPeriod, "getLossByPeriod");
    }

    public AmountResponse getObjectByPeriod(int sessionId, int daysOfPeriod, String method)  {

        AmountResponse result = new AmountResponse();
        SoapObject response = null;
        String METHOD_NAME = method;
        int returnCode = 0;

        try {
            response = executeSoapAction(METHOD_NAME, sessionId, daysOfPeriod);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    Double amount = Double.parseDouble(response.getPrimitivePropertySafelyAsString("value"));
                    result.setValue(amount);
                }
            }

        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param daysOfPeriod
     * @return
     */
    public AmountResponse getIncomeByPeriod(int sessionId, int daysOfPeriod)  {
        return getObjectByPeriod(sessionId, daysOfPeriod, "getIncomeByPeriod");

    }


    /**
     * @author Christopher
     * @date 19.05.2015
     * @param sessionId
     * @return
     */
    public AmountListResponse getItemsAmountForCategories(int sessionId) {
        return getObjectAmountForCategories(sessionId, "getItemsAmountForCategories");
    }

    public AmountListResponse getObjectAmountForCategories(int sessionId, String method)  {
        return getAmount(sessionId, method);

    }


    /**
     * @author Mark
     * @date 19.06.2015
     * @param sessionId
     * @return
     */
    public AmountListResponse getAmountForVendors(int sessionId) {
        return getAmount(sessionId, "getAmountForVendors");

    }

    /**
     * @author Mark
     * @date 19.06.2015
     * @param sessionId
     * @param method
     * @return
     */
    public AmountListResponse getAmount(int sessionId, String method)  {

        // Create ResultSet
        AmountListResponse result  = new AmountListResponse();
        // Create a List to store response items
        ArrayList<AmountTO> list = new ArrayList<>();
        // Get Method Name to invoke on Server
        String METHOD_NAME = method;
        // Save Soap response - Server method return
        SoapObject response = null;

        try {

            response = executeSoapAction(METHOD_NAME, sessionId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);
            int propertyCount = response.getPropertyCount();

            if (returnCode == 200) {
                if (propertyCount > 1) {
                    for (int idx = 1; idx < propertyCount; idx++) {

                        SoapObject ListObject = (SoapObject) response.getProperty(idx);

                        // Read from the SoapObject
                        String name     = ListObject.getPrimitivePropertySafelyAsString("name");
                        Double value    = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString("value"));

                        AmountTO amountTO = new AmountTO(name, value);
                        list.add(amountTO);
                    }
                }

            } else  Log.d("INFO", METHOD_NAME + " keine Werte vorhanden");

            result.setAmountList(list);


        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }



    /**
     * Diese Methode delegiert einen Methodenaufruf an den hinterlegten WebService.
     * @param methodName
     * @return
     */
    private SoapObject executeSoapAction(String methodName, Object... args) throws SoapFault {

        Object result = null;


	    /* Create a org.ksoap2.serialization.SoapObject object to build a SOAP request. Specify the namespace of the SOAP object and method
	     * name to be invoked in the SoapObject constructor.
	     */
        SoapObject request = new SoapObject(NAMESPACE, methodName);

	    /* The array of arguments is copied into properties of the SOAP request using the addProperty method. */
        for (int i=0; i<args.length; i++) {
            request.addProperty("arg" + i, args[i]);
        }

	    /* Next create a SOAP envelop. Use the SoapSerializationEnvelope class, which extends the SoapEnvelop class, with support for SOAP
	     * Serialization format, which represents the structure of a SOAP serialized message. The main advantage of SOAP serialization is portability.
	     * The constant SoapEnvelope.VER11 indicates SOAP Version 1.1, which is default for a JAX-WS webservice endpoint under JBoss.
	     */
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        // @author Christopher
        // @date 16.06.2015
        envelope.implicitTypes = true;
        envelope.encodingStyle = SoapSerializationEnvelope.XSD;

	    /* Assign the SoapObject request object to the envelop as the outbound message for the SOAP method call. */
        envelope.setOutputSoapObject(request);


	    /* Create a org.ksoap2.transport.HttpTransportSE object that represents a J2SE based HttpTransport layer. HttpTransportSE extends
	     * the org.ksoap2.transport.Transport class, which encapsulates the serialization and deserialization of SOAP messages.
	     */

        // marshaling !
        MarshalDouble md = new MarshalDouble();
        md.register(envelope);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
	        /* Make the soap call using the SOAP_ACTION and the soap envelop. */
            List<HeaderProperty> reqHeaders = null;

            @SuppressWarnings({"unused", "unchecked"})
            //List<HeaderProperty> respHeaders = androidHttpTransport.call(NAMESPACE + methodName, envelope, reqHeaders);
                    //fuehrt zu CXF-Fehler! neue Version ohne SOAP-Action funktioniert:
                    List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);

	        /* Get the web service response using the getResponse method of the SoapSerializationEnvelope object.
	         * The result has to be cast to SoapPrimitive, the class used to encapsulate primitive types, or to SoapObject.
	         */
            result = (SoapObject) envelope.getResponse();

            //Log.d("Result", result.toString());

            if (result instanceof SoapFault) {
                throw (SoapFault) result;
            }
        }
        catch (SoapFault e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return (SoapObject) result;
    }
}
