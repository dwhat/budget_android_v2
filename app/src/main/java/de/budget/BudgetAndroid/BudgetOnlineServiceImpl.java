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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * @author christopher
 * @date 01.06.2015
 */
public class BudgetOnlineServiceImpl implements BudgetOnlineServiceOld {

    private static final String NAMESPACE = "http://onlinebudget.budget.de/";

    //private static final String URL = "http://10.0.2.2:8080/budget/BudgetOnlineServiceBean";
    private static final String URL = "http://85.214.64.59:8080/budget/BudgetOnlineServiceBean";

    private static final String TAG = BudgetOnlineServiceImpl.class.getName();

    private int tmp, rt;

    private int returnCode;

    @Override
    public UserLoginResponse setUser(String username, String password, String email) {

        UserLoginResponse result = new UserLoginResponse();
        int returnCode = 0;
        String METHOD_NAME = "setUser";
        SoapObject response = null;
        int sessionId;

        try {
            response = executeSoapAction(METHOD_NAME, username, password, email);
            //Log.d(TAG, response.toString());
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            if (returnCode == 200) {
                sessionId = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));
                result.setSessionId(sessionId);
            }


        } catch (SoapFault e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    @Override
    public UserLoginResponse login(String username, String password) throws InvalidLoginException{
        UserLoginResponse result = new UserLoginResponse();
        String METHOD_NAME = "login";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, username, password);
            //Log.d(TAG, response.toString());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));
            if (tmp != 0) {
                result.setSessionId(tmp);
                return result;
            }
            else {
                throw new InvalidLoginException("Login not successful!");
            }
        } catch (SoapFault e) {
            throw new InvalidLoginException(e.getMessage());
        }
    }

    @Override
    public ReturnCodeResponse logout(int sessionID) throws Exception{
        ReturnCodeResponse result = new ReturnCodeResponse();
        String METHOD_NAME = "logout";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionID);
            //Log.d(TAG, response.toString());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp != 0) {
                result.setReturnCode(tmp);
                return result;
            }
            else {
                throw new Exception("Logout not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
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
    public VendorListResponse getVendors(int sessionId) throws Exception{

        VendorListResponse result = new VendorListResponse();
        ArrayList<VendorTO> vendorList = new ArrayList<>();

        String METHOD_NAME = "getVendors";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            result.setReturnCode(returnCode);

            if (returnCode == 200) {
                if (response.getPropertyCount() > 1) {
                    for (int idx = 1; idx < response.getPropertyCount(); idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);

                        vendorList.add(parseVendorResponse(ListObject));
                    }
                }
            } else {
                throw new Exception(METHOD_NAME + " was not successful!");
            }

            result.setVendorList(vendorList);
            return result;

        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
    }

    private VendorTO parseVendorResponse (SoapObject ListObject) {

        VendorTO vendorTO = new VendorTO();

        String name = ListObject.getPrimitivePropertySafelyAsString("name");
        String street = ListObject.getPrimitivePropertySafelyAsString("street");
        String city = ListObject.getPrimitivePropertySafelyAsString("city");
        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
        int hn = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("houseNumber"));
        int plz = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("PLZ"));


        vendorTO.setName(name);
        vendorTO.setId(id);
        vendorTO.setStreet(street);
        vendorTO.setHouseNumber(hn);
        vendorTO.setPLZ(plz);
        vendorTO.setCity(city);

        return vendorTO;

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
    public VendorResponse createOrUpdateVendor(int sessionId, int vendorId, String name, String logo, String street, String city, int PLZ, int houseNumber) throws Exception{
        VendorResponse result = new VendorResponse();
        String METHOD_NAME = "createOrUpdateVendor";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, vendorId, name, logo, street, city, PLZ, houseNumber);
            //Log.d(TAG, "Response: " + response.toString());
            //Log.d(TAG, "Response: " + response.getPropertyCount());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            SoapObject test= (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(test.getPrimitivePropertySafelyAsString("id"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                VendorTO vendor = new VendorTO();
                vendor.setName(name);
                vendor.setId(id);
                vendor.setStreet(street);
                vendor.setHouseNumber(houseNumber);
                vendor.setPLZ(PLZ);
                vendor.setCity(city);
                result.setVendorTo(vendor);
                return result;
            }
            else {
                throw new Exception("Create/Update vendor was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method to delete a vendor
     * @author Christopher
     * @date 16.06.2015
     * @param sessionId
     * @param vendorId
     * @return
     */
    public ReturnCodeResponse deleteVendor(int sessionId, int vendorId) throws Exception{
        return deleteObject(sessionId, vendorId, "deleteVendor");
    }



	/*#################      PAYMENT - SECTION     ##############*/

    /**
     * @author Christopher
     * @date 19.05.2015
     * @param sessionId
     * @return PaymentListResponse Object
     */
    public PaymentListResponse getPayments(int sessionId) throws Exception{
        PaymentListResponse result = new PaymentListResponse();
        String METHOD_NAME = "getPayments";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            //Log.d(TAG, response.toString() + response.getPropertyCount());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                ArrayList<PaymentTO> paymentList = new ArrayList<>();
                if (response.getPropertyCount() > 1) {
                    for (int idx = 1; idx < response.getPropertyCount(); idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        //Log.d("INFO", "paymentList gefunden : " + ListObject.toString() + "Länge: " + ListObject.getPropertyCount());
                        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
                        String name = ListObject.getPrimitivePropertySafelyAsString("name");
                        boolean active = Boolean.parseBoolean(ListObject.getPrimitivePropertySafelyAsString("active"));
                        String bic = ListObject.getPrimitivePropertySafelyAsString("bic");
                        String number = ListObject.getPrimitivePropertySafelyAsString("number");
                        PaymentTO tmp = new PaymentTO();
                        tmp.setName(name);
                        tmp.setId(id);
                        tmp.setActive(active);
                        tmp.setBic(bic);
                        tmp.setNumber(number);
                        paymentList.add(tmp);
                    }
                }
                result.setPaymentList(paymentList);
                return result;
            }
            else if(tmp == 404){
                result.setReturnCode(tmp);
                result.setPaymentList(new ArrayList<PaymentTO>());
                return result;
            }
            else {
                throw new Exception("getPayments was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
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
    public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active) throws Exception{
        PaymentResponse result = new PaymentResponse();
        String METHOD_NAME = "createOrUpdatePayment";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, paymentId, name, number, bic, active);
            //Log.d(TAG, response.toString());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            SoapObject test= (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(test.getPrimitivePropertySafelyAsString("id"));
            if (tmp != 0) {
                result.setReturnCode(tmp);
                PaymentTO payment = new PaymentTO();
                payment.setName(name);
                payment.setId(id);
                payment.setNumber(number);
                payment.setBic(bic);
                payment.setActive(active);
                result.setPaymentTo(payment);
                return result;
            }
            else {
                throw new Exception("Create/Update payment was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
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
    public CategoryListResponse getCategorys(int sessionId) throws Exception{
        CategoryListResponse result = new CategoryListResponse();
        String METHOD_NAME = "getCategorys";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            //Log.d(TAG, response.toString() + response.getPropertyCount());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                ArrayList<CategoryTO> categoryList = new ArrayList<>();
                if (response.getPropertyCount() > 1) {
                    for (int idx = 1; idx < response.getPropertyCount(); idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        //Log.d("INFO", "categoryList gefunden : " + ListObject.toString() + "Länge: " + ListObject.getPropertyCount());
                        String name = ListObject.getPrimitivePropertySafelyAsString("name");
                        String activeString = ListObject.getPrimitivePropertySafelyAsString("active");
                        boolean active = false;
                        if("true".equals(activeString)){
                            active = true;
                        }
                        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
                        boolean income = Boolean.parseBoolean(ListObject.getPrimitivePropertySafelyAsString("income"));
                        String notice = ListObject.getPrimitivePropertySafelyAsString("notice");
                        String colour = ListObject.getPrimitivePropertySafelyAsString("colour");
                        CategoryTO tmp = new CategoryTO();
                        tmp.setName(name);
                        tmp.setActive(active);
                        tmp.setId(id);
                        tmp.setNotice(notice);
                        tmp.setColour(colour);
                        tmp.setIncome(income);
                        categoryList.add(tmp);

                    }
                }
                result.setCategoryList(categoryList);
                return result;
            }
            else if(tmp == 404){
                result.setReturnCode(tmp);
                result.setCategoryList(new ArrayList<CategoryTO>());
                return result;
            }
            else {
                throw new Exception("getCategorys was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }

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
    public CategoryResponse createOrUpdateCategory(int sessionId, int categoryId, boolean income, boolean active, String name, String notice, String colour)throws Exception{
        CategoryResponse result = new CategoryResponse();
        String METHOD_NAME = "createOrUpdateCategory";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, categoryId, income, active, name, notice, colour);
            //Log.d(TAG, response.toString());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            SoapObject test= (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(test.getPrimitivePropertySafelyAsString("id"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                CategoryTO category = new CategoryTO();
                category.setName(name);
                category.setId(id);
                category.setIncome(income);
                category.setActive(active);
                category.setNotice(notice);
                category.setColour(colour);
                result.setCategoryTo(category);
                return result;
            }
            else {
                throw new Exception("Create/Update category was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * @author Christopher
     * @date 16.06.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public ReturnCodeResponse deleteCategory(int sessionId, int categoryId) throws Exception{
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
    public BasketListResponse getBaskets(int sessionId, BudgetAndroidApplication myApp) throws Exception{

        BasketListResponse result = new BasketListResponse();
        String METHOD_NAME = BasketTOConstants.GET_BASKETS;
        SoapObject response = null;

        try {

            // Get Response from SOAP Object specified Method Name
            response = executeSoapAction(METHOD_NAME, sessionId);


            //Log.d(TAG, "getBaskets: " + response.toString() +
            //        "/n Count: " + response.getPropertyCount());


            // Get Return Code from SOAP Object
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));


            Log.d("INO", String.valueOf(returnCode));
            if (returnCode == 200) {

                // Create new List for Basket Obejcts
                ArrayList<BasketTO> basketList = new ArrayList<>();

                // Set Returncode for successs
                result.setReturnCode(returnCode);

                // Response has Propertys
                if (response.getPropertyCount() > 1) {


                    for (int i = 1; i < response.getPropertyCount(); i++) {

                        // Iterate throu SoapObject response by Property ID
                        SoapObject ListObject = (SoapObject) response.getProperty(i);

                        //Log.d("INFO", "basketList gefunden : " + ListObject.toString() +
                        //        " Länge: " + ListObject.getPropertyCount());

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


                        // int id, String notice, double amount, Timestamp createDate, Timestamp purchaseDate, Timestamp lastChanged, UserTO user, VendorTO vendor, PaymentTO payment, List<ItemTO> items
                        BasketTO basket = new BasketTO(id, name, notice, amount, createDate, purchaseDate, lastChanged, null, vendorTO, paymentTO, null);

//                        basket.setId(id);
//                        basket.setNotice(notice);
//                        basket.setAmount(amount);
//                        basket.setCreateDate(createDate);
//                        basket.setPurchaseDate(purchaseDate);
//                        basket.setLastChanged(lastChanged);
//                        basket.setUser(null);
//                        basket.setVendor(null);
//                        basket.setPayment(null);
//                        basket.setItems(null);

                        basketList.add(basket);

                    }
                }

                result.setBasketList(basketList);

                return result;
            }
            else if(returnCode == 404){
                result.setReturnCode(returnCode);
                result.setBasketList(new ArrayList<BasketTO>());
                return result;
            }
            else {
                throw new Exception("Get Basket was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }

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
     * @param myApp
     * @return
     */
    public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId, BudgetAndroidApplication myApp) throws Exception{

        BasketResponse result = new BasketResponse();

        String METHOD_NAME = "createOrUpdateBasket";

        SoapObject response = null;

        try {

            response = executeSoapAction(METHOD_NAME, sessionId, basketId, name, notice, amount, purchaseDate, paymentId, vendorId);

            Log.d(TAG, response.toString());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));

            SoapObject test= (SoapObject) response.getProperty(1);

            int id = Integer.parseInt(test.getPrimitivePropertySafelyAsString("id"));

            if (tmp == 200) {
                result.setReturnCode(tmp);
                Calendar c = new GregorianCalendar();
                Long createDate = c.getTimeInMillis();
                Long lastChanged = c.getTimeInMillis();
                BasketTO basket = new BasketTO(id, name, notice, amount, createDate, purchaseDate, lastChanged, null, myApp.getVendorById(vendorId), myApp.getPaymentById(paymentId));
                result.setBasketTo(basket);
                return result;
            }
            else {
                throw new Exception("Create/Update category was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
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
     * @param myApp
     * @return
     */
    public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId, List<ItemTO> items, BudgetAndroidApplication myApp) throws Exception{

        BasketResponse result = new BasketResponse();

        String METHOD_NAME = "createOrUpdateBasket";

        SoapObject response = null;

        try {

            response = executeSoapAction(METHOD_NAME, sessionId, basketId, name, notice, amount, purchaseDate, paymentId, vendorId, items);
            Log.d(TAG, response.toString());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));

            SoapObject test= (SoapObject) response.getProperty(1);

            int id = Integer.parseInt(test.getPrimitivePropertySafelyAsString("id"));

            if (tmp == 200) {
                result.setReturnCode(tmp);
                Calendar c = new GregorianCalendar();
                Long createDate = c.getTimeInMillis();
                Long lastChanged = c.getTimeInMillis();
                BasketTO basket = new BasketTO(id, name, notice, amount, createDate, purchaseDate, lastChanged, null, myApp.getVendorById(vendorId), myApp.getPaymentById(paymentId), items);
                return result;
            }
            else {
                throw new Exception("Create/Update category was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Method to delete a basket
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param basketId
     * @return
     */
    public ReturnCodeResponse deleteBasket(int sessionId, int basketId) throws Exception{
        return deleteObject(sessionId, basketId, "deleteBasket");
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
    public ReturnCodeResponse deleteObject (int sessionId, int objectId, String method) throws Exception{

        ReturnCodeResponse result = new ReturnCodeResponse();
        String METHOD_NAME = method;
        SoapObject response = null;

        try {
            response = executeSoapAction(METHOD_NAME, sessionId, objectId);
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));

            if (returnCode == 200) {
                result.setReturnCode(returnCode);
                return result;
            }
            else {
                throw new Exception(METHOD_NAME + " was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }

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
    public BasketListResponse getBasketsOfActualMonth(int sessionId){
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
    public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name, double  quantity, double amount, String notice, long receiptDate, int categoryId, BudgetAndroidApplication myApp) throws Exception{
        IncomeResponse result = new IncomeResponse();;
        String METHOD_NAME = "createOrUpdateIncome";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, incomeId, name, quantity, amount, notice, receiptDate, categoryId);
            //Log.d(TAG, response.toString());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            SoapObject test= (SoapObject) response.getProperty(1);
            int id = Integer.parseInt(test.getPrimitivePropertySafelyAsString("id"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                IncomeTO income = new IncomeTO();
                income.setName(name);
                income.setId(id);
                income.setQuantity(quantity);
                income.setAmount(amount);
                income.setNotice(notice);
                income.setReceiptDate(receiptDate);
                income.setCategory(myApp.getCategory(categoryId));
                result.setIncomeTo(income);
                return result;
            }
            else {
                throw new Exception("Create/Update Income was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
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
    public IncomeListResponse getIncomes(int sessionId, BudgetAndroidApplication myApp) throws Exception{
        IncomeListResponse result = new IncomeListResponse();
        //MainActivity activity = new MainActivity();
        String METHOD_NAME = "getIncomes";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            //Log.d(TAG, response.toString() + response.getPropertyCount());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                ArrayList<IncomeTO> incomeList = new ArrayList<>();
                if (response.getPropertyCount() > 1) {
                    for (int idx = 1; idx < response.getPropertyCount(); idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        //Log.d("INFO", "incomeList gefunden : " + ListObject.toString() + "Länge: " + ListObject.getPropertyCount());
                        String name = ListObject.getPrimitivePropertySafelyAsString("name");
                        String notice = ListObject.getPrimitivePropertySafelyAsString("notice");
                        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
                        Double amount = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString("amount"));
                        Double quantity = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString("quantity"));
                        Long receiptDate = Long.parseLong(ListObject.getPrimitivePropertySafelyAsString("receiptDate"));

                        // Kategorie holen
                        SoapObject CategoryObject = (SoapObject) ListObject.getProperty("category");
                        int categoryId = Integer.parseInt(CategoryObject.getPrimitivePropertyAsString("id"));

                        IncomeTO tmp = new IncomeTO();
                        tmp.setName(name);
                        tmp.setCategory(myApp.getCategory(categoryId));
                        tmp.setId(id);
                        tmp.setNotice(notice);
                        tmp.setAmount(amount);
                        tmp.setQuantity(quantity);
                        tmp.setReceiptDate(receiptDate);
                        incomeList.add(tmp);

                    }
                }
                result.setIncomeList(incomeList);
                return result;
            }
            else if(tmp == 404){
                result.setReturnCode(tmp);
                result.setIncomeList(new ArrayList<IncomeTO>());
                return result;
            }
            else {
                throw new Exception("Get Incomes was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }

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
    public IncomeListResponse getIncomesOfActualMonth(int sessionId){
        return null;
    }


    /**
     * @author Christopher
     * @date 16.06.2015
     * @param sessionId
     * @param incomeId
     * @return
     */
    public ReturnCodeResponse deleteIncome(int sessionId, int incomeId) throws Exception{
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
    public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double  quantity, double price, String notice, long receiptDate, int basketId, int categoryId) throws Exception{
        ItemResponse result = new ItemResponse();;
        String METHOD_NAME = "createOrUpdateItem";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, itemId, name,  quantity, price, notice, receiptDate, basketId, categoryId);

            Log.d(TAG, response.toString());

            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));

            SoapObject test= (SoapObject) response.getProperty(1);

            int id = Integer.parseInt(test.getPrimitivePropertySafelyAsString("id"));
            if (returnCode == 200) {
                result.setReturnCode(returnCode);
                ItemTO item = new ItemTO(name, quantity, price, notice, receiptDate, basketId, categoryId);
                result.setItemTo(item);
                return result;
            }
            else {
                throw new Exception("Create/Update Income was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
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
    public ItemListResponse getItemsByBasket(int sessionId, int basketId,  BudgetAndroidApplication myApp) throws Exception{

        ItemListResponse result = new ItemListResponse();
        String METHOD_NAME = ItemTO.GET_ITEMS_BY_BASKET;
        SoapObject response = null;


        try {

            // Get Response from SOAP Object specified Method Name
            response = executeSoapAction(METHOD_NAME, sessionId, basketId);


            //Log.d(TAG, ItemTOConstant.GET_ITEMS_BY_BASKET + ": " + response.toString() +
            //        "/n Count: " + response.getPropertyCount());


            // Get Return Code from SOAP Object
            returnCode = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));


            if (returnCode == 200) {

                // Create new List for Basket Obejcts
                ArrayList<ItemTO> itemList = new ArrayList<>();

                // Set Returncode for successs
                result.setReturnCode(returnCode);

                // Response has Propertys
                if (response.getPropertyCount() > 1) {


                    for (int i = 1; i < response.getPropertyCount(); i++) {

                        SoapObject ListObject = (SoapObject) response.getProperty(i);

                        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString(ItemTO.ID));
                        String name = ListObject.getPrimitivePropertySafelyAsString(ItemTO.NAME);
                        double quantity = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString(ItemTO.QUANTITY));
                        double price = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString(ItemTO.PRICE));
                        String notice = ListObject.getPrimitivePropertySafelyAsString(ItemTO.NOTICE);
                        long receiptDate = Long.parseLong(ListObject.getPrimitivePropertySafelyAsString(ItemTO.RECEIPTDATE));
                        int categoryId = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString(ItemTO.CATEGORY));


                        ItemTO item = new ItemTO(id, name, quantity, price, notice, receiptDate, basketId, categoryId);

                        itemList.add(item);

                    }
                }

                result.setItemList(itemList);

                return result;
            }
            else {
                throw new Exception("Create/Update item was not successful!");
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
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

    public AmountResponse getItemsAmountByLossCategory(int sessionId, int categoryId) throws Exception{
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
    public AmountResponse getLossByPeriod(int sessionId, int daysOfPeriod) throws Exception{
        return getObjectByPeriod(sessionId, daysOfPeriod, "getLossByPeriod");
    }

    public AmountResponse getObjectByPeriod(int sessionId, int daysOfPeriod, String method) throws Exception {
        AmountResponse result = new AmountResponse();
        String METHOD_NAME = method;
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId, daysOfPeriod);
            //Log.d(TAG, response.toString() + response.getPropertyCount());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                if (response.getPropertyCount() > 1) {
                    Double amount = Double.parseDouble(response.getPrimitivePropertySafelyAsString("value"));
                    result.setValue(amount);
                }
                return result;
            }
            else {
                result.setReturnCode(tmp);
                Log.d("INFO", METHOD_NAME + " keine vorhanden");
                return result;
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * @author Christopher
     * @date 26.05.2015
     * @param sessionId
     * @param daysOfPeriod
     * @return
     */
    public AmountResponse getIncomeByPeriod(int sessionId, int daysOfPeriod) throws Exception {
        return getObjectByPeriod(sessionId, daysOfPeriod, "getIncomeByPeriod");

    }

    /**
     * @author Christopher
     * @date 19.05.2015
     * @param sessionId
     * @return
     */
    public AmountListResponse getItemsAmountForCategories(int sessionId) throws Exception{
        return getObjectAmountForCategories(sessionId, "getItemsAmountForCategories");
    }

    public AmountListResponse getObjectAmountForCategories(int sessionId, String method) throws Exception {

        return getAmount(sessionId, method);

        /*
        AmountListResponse result = new AmountListResponse();
        ArrayList<AmountTO> list = new ArrayList<>();
        String METHOD_NAME = method;
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
                        AmountTO amountTO = new AmountTO();

                        String name = ListObject.getPrimitivePropertySafelyAsString("name");
                        Double value = Double.parseDouble(ListObject.getPrimitivePropertySafelyAsString("value"));

                        amountTO.setName(name);
                        amountTO.setValue(value);

                        list.add(amountTO);
                    }
                }
                result.setAmountList(list);

            } else Log.d("INFO", METHOD_NAME + " keine vorhanden");

            return result;

        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }*/
    }

    /**
     * @author Christopher
     * @date 19.05.2015
     * @param sessionId
     * @return
     */
    public AmountListResponse getIncomeAmountForCategories(int sessionId) throws Exception{
        return getObjectAmountForCategories(sessionId, "getIncomesAmountForCategories");
    }


    /**
     * @author Mark
     * @date 19.06.2015
     * @param sessionId
     * @return
     */
    public AmountListResponse getBasketsAmountForVendors(int sessionId) throws Exception{
        return getAmount(sessionId, "getBasketsAmountForVendors");
    }

    /**
     * @author Mark
     * @date 19.06.2015
     * @param sessionId
     * @param method
     * @return
     */
    public AmountListResponse getAmount(int sessionId, String method) throws Exception {

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

            // Read the response and get ReturnCode


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

                    result.setAmountList(list);
                }


            } else  Log.d("INFO", METHOD_NAME + " keine Werte vorhanden");

            return result;

        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }

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
