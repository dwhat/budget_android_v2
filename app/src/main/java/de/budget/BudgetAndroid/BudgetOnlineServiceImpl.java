package de.budget.BudgetAndroid;

import android.util.Log;

import de.budget.BudgetService.BudgetOnlineService;
import de.budget.BudgetService.Exception.InvalidLoginException;
import de.budget.BudgetService.Response.ReturnCodeResponse;
import de.budget.BudgetService.Response.UserLoginResponse;
import de.budget.BudgetService.Response.UserResponse;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


/**
 * Created by christopher on 02.06.15.
 */
public class BudgetOnlineServiceImpl implements BudgetOnlineService{

    private static final String NAMESPACE = "http://budget.de/";

    private static final String URL = "http://10.0.2.2:8080/budget/BudgetOnlineServiceBean";

    private static final String TAG = BudgetOnlineServiceImpl.class.getName();

    private int sessionId;

    @Override
    public UserLoginResponse setUser(String username, String password, String email) {
        return null;
    }

    @Override
    public UserLoginResponse login(String username, String password) throws InvalidLoginException{
        UserLoginResponse result = new UserLoginResponse();
        String METHOD_NAME = "login";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, username, password);
            Log.d(TAG, response.toString());
            sessionId = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));
            if (sessionId != 0) {
                result.setSessionId(sessionId);
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
    public ReturnCodeResponse logout(int sessionID) {
        return null;
    }

    @Override
    public UserResponse getUserByName(int sessionId, String userName) {
        return null;
    }

    @Override
    public ReturnCodeResponse deleteUser(int sessionId, String username) {
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
