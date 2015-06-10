package de.budget.BudgetAndroid;

import android.util.Log;

import de.budget.BudgetService.BudgetOnlineService;
import de.budget.BudgetService.Exception.BudgetOnlineException;
import de.budget.BudgetService.Exception.InvalidLoginException;
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
import de.budget.BudgetService.dto.*;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @author christopher
 * @date 01.06.2015
 */
public class BudgetOnlineServiceImpl implements BudgetOnlineService{

    private static final String NAMESPACE = "http://onlinebudget.budget.de/";

    private static final String URL = "http://10.0.2.2:8080/budget/BudgetOnlineServiceBean";

    private static final String TAG = BudgetOnlineServiceImpl.class.getName();

    private int tmp, rt;

    @Override
    public UserLoginResponse setUser(String username, String password, String email) throws  Exception{
        UserLoginResponse result = new UserLoginResponse();
        String METHOD_NAME = "setUser";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, username, password, email);
            Log.d(TAG, response.toString());
            rt = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (rt == 200) {
                tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));
                result.setSessionId(tmp);
                return result;
            }
            else {
                tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
                result.setReturnCode(tmp);
                return result;
            }
        } catch (SoapFault e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UserLoginResponse login(String username, String password) throws InvalidLoginException{
        UserLoginResponse result = new UserLoginResponse();
        String METHOD_NAME = "login";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, username, password);
            Log.d(TAG, response.toString());
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
            Log.d(TAG, response.toString());
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
     * @author Marco
     * @date 19.05.2015
     * @param sessionId
     * @return VendorListResponse Object
     */
    public VendorListResponse getVendors(int sessionId) throws Exception{
        VendorListResponse result = new VendorListResponse();
        String METHOD_NAME = "getVendors";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
            Log.d(TAG, response.toString() + response.getPropertyCount());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                ArrayList<VendorTO> vendorList = new ArrayList<>();
                if (response.getPropertyCount() > 1) {
                    for (int idx = 1; idx < response.getPropertyCount(); idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        Log.d("INFO", "vendorList gefunden : " + ListObject.toString() + "Länge: " + ListObject.getPropertyCount());
                        String name = ListObject.getPrimitivePropertySafelyAsString("name");
                        String street = ListObject.getPrimitivePropertySafelyAsString("street");
                        String city = ListObject.getPrimitivePropertySafelyAsString("city");
                        int id = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("id"));
                        int hn = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("houseNumber"));
                        int plz = Integer.parseInt(ListObject.getPrimitivePropertySafelyAsString("PLZ"));
                        VendorTO tmp = new VendorTO();
                        tmp.setName(name);
                        tmp.setId(id);
                        tmp.setStreet(street);
                        tmp.setHouseNumber(hn);
                        tmp.setPLZ(plz);
                        tmp.setCity(city);
                        vendorList.add(tmp);

                    }
                }
                result.setVendorList(vendorList);
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
     * Method to get a Vendor with the SessionId and the vendorId
     * @author Marco
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
            Log.d(TAG, response.toString());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp != 0) {
                result.setReturnCode(tmp);
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
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param vendorId
     * @return
     */
    public ReturnCodeResponse deleteVendor(int sessionId, int vendorId){
        return null;
    }





	/*#################      PAYMENT - SECTION     ##############*/

    /**
     * @author Marco
     * @date 19.05.2015
     * @param sessionId
     * @return PaymentListResponse Object
     */
    public PaymentListResponse getPayments(int sessionId){
        return null;
    }

    /**
     * Method to get a payment with the SessionId and the paymentId
     * @author Marco
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
     * @author Marco
     * @param sessionId
     * @param paymentId
     * @return ReturnCodeResponse Object
     */
    public ReturnCodeResponse deletePayment(int sessionId, int paymentId){
        return null;
    }


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
    public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active){
        return null;
    }





	/*#################      CATEGORY - SECTION     ##############*/

    /**
     * @author Marco
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
            Log.d(TAG, response.toString() + response.getPropertyCount());

            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp == 200) {
                result.setReturnCode(tmp);
                ArrayList<CategoryTO> categoryList = new ArrayList<>();
                if (response.getPropertyCount() > 1) {
                    for (int idx = 1; idx < response.getPropertyCount(); idx++) {
                        SoapObject ListObject = (SoapObject) response.getProperty(idx);
                        Log.d("INFO", "categoryList gefunden : " + ListObject.toString() + "Länge: " + ListObject.getPropertyCount());
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
            else {
                throw new Exception("Create/Update category was not successful!");
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
            Log.d(TAG, response.toString());
            tmp = Integer.parseInt(response.getPrimitivePropertySafelyAsString("returnCode"));
            if (tmp != 0) {
                result.setReturnCode(tmp);
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
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public ReturnCodeResponse deleteCategory(int sessionId, int categoryId){
        return null;
    }





	/*#################      BASKET - SECTION     ##############*/

    /**
     * Gives a Response Object with all Baskets in a list
     * @author Marco
     * @date 19.05.2015
     * @param sessionId
     * @return BasketListResponse Object
     */
    public BasketListResponse getBaskets(int sessionId){
        return null;
    }

    /**
     * @author Marco
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
     * @param items   List with items to add to the basket
     * @return
     */
    public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, Timestamp purchaseDate, int paymentId, int vendorId, List<ItemTO> items){
        return null;
    }

    /**
     * Method to delete a basket
     * @author marco
     * @date 26.05.2015
     * @param sessionId
     * @param basketID
     * @return
     */
    public ReturnCodeResponse deleteBasket(int sessionId, int basketID){
        return null;
    }

    /**
     * Gibt die letzten Baskets als Liste zur�ck
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param numberOfBaskets Anzahl der letzten auszugebenen Baskets
     * @return BasketListResponse Object
     */
    public BasketListResponse getLastBaskets(int sessionId, int numberOfBaskets){
        return null;
    }

    /**
     * @author Marco
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
     * @author Marco
     * @param sessionId
     * @return
     */
    public BasketListResponse getBasketsOfActualMonth(int sessionId){
        return null;
    }


    /**
     * gets all baskets of a specific payment
     * @author Marco
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
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param incomeId    only necessary for update
     * @param name
     * @param quantity
     * @param amount
     * @param notice
     * @param period
     * @param launchDate
     * @param finishDate
     * @param categoryId
     * @return
     */
    public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name, double  quantity, double amount, String notice, int period, Timestamp launchDate, Timestamp finishDate, int categoryId){
        return null;
    }

    //public int updateIncome(Customer income,int incomeID);

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param itemId
     * @return
     */
    public IncomeResponse getIncome(int sessionId, int itemId){
        return null;
    }

    /**
     * Gibt die letzten Incomes als Liste zur�ck
     * @author Marco
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
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public IncomeListResponse getIncomesByCategory(int sessionId, int categoryId){
        return null;
    }


    /**
     * gets all income of the actual month
     * @author Marco
     * @param sessionId
     * @return
     */
    public IncomeListResponse getIncomesOfActualMonth(int sessionId){
        return null;
    }


    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param itemID
     * @return
     */
    public ReturnCodeResponse deleteIncome(int sessionId, int itemID){
        return null;
    }








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
     * @param period
     * @param launchDate
     * @param finishDate
     * @param basketId
     * @param categoryId
     * @return
     */
    public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double  quantity, double price, String notice, int period, Timestamp launchDate, Timestamp finishDate, int basketId, int categoryId){
        return null;
    }

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param lossId
     * @return
     */
    public ReturnCodeResponse deleteItem(int sessionId, int lossId){
        return null;
    }

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param itemId
     * @return
     */
    public ItemResponse getItemByBasket(int sessionId, int itemId, int basketId){
        return null;
    }

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param basketId
     * @return
     */
    public ItemListResponse getItemsByBasket(int sessionId, int basketId){
        return null;
    }


    /**
     * gets all Items of a specific category for losses
     * @author Marco
     * @date 29.05.2015
     * @param sessionId
     * @param categoryId
     * @return
     */
    public ItemListResponse getItemsByLossCategory(int sessionId, int categoryId){
        return null;
    }






	/*#################      XYZ - SECTION     ##############*/

    //public Map<Integer,Integer> getChart(int customerID);

    //public int getBalance(int customerID);

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param daysOfPeriod
     * @return
     */
    public AmountResponse getLossByPeriod(int sessionId, int daysOfPeriod){
        return null;
    }

    /**
     * @author Marco
     * @date 26.05.2015
     * @param sessionId
     * @param daysOfPeriod
     * @return
     */
    public AmountResponse getIncomeByPeriod(int sessionId, int daysOfPeriod){
        return null;
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

	    /* Assign the SoapObject request object to the envelop as the outbound message for the SOAP method call. */
        envelope.setOutputSoapObject(request);

	    /* Create a org.ksoap2.transport.HttpTransportSE object that represents a J2SE based HttpTransport layer. HttpTransportSE extends
	     * the org.ksoap2.transport.Transport class, which encapsulates the serialization and deserialization of SOAP messages.
	     */
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
            result = envelope.getResponse();

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
