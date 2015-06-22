package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.PreferencesActivity;
import de.budget.BudgetAndroid.Vendors.VendorActivity;
import de.budget.BudgetService.Response.PaymentResponse;
import de.budget.BudgetService.Response.VendorResponse;

    /**
     *  * <p>  Asynchroner Task um ein Payment anzulegen oder zu ändern.
     *
     *      Der Task nimmt die Parameter eines neuen oder zu ändernden Basket Objects entgegen.
     *      Die Parameter werden über die BudgetAndroidApplication an das Interface übermittelt,
     *      welches die Versendung an den OnlineService übernimmt.
     *
     *      Die Antwort des Servers wird in einer Response gespeichert.
     *       *
     *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
     *      welche Interaktion durchgeführt werden soll.
     * </p>
    * @Author Christopher
    * @Date 11.06.2015
    */
public class CreateOrUpdatePaymentTask extends AsyncTask<String, Integer, PaymentResponse>
{
    private Context context;
    private static PreferencesActivity activity;
    public static MainActivity nextActivity = new MainActivity();
    private static BudgetAndroidApplication myApp;

    public CreateOrUpdatePaymentTask(Context context, BudgetAndroidApplication myApp, PreferencesActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }

    @Override
    protected PaymentResponse doInBackground(String... params){
        if(params.length != 5)
            return null;
        String paymentName = params[0];
        String paymentNumber = params[1];
        String paymentBic = params[2];
        String paymentActiveString = params[3];
        String paymentId = params[4];

        boolean paymentActive;

        if ("true".equals(paymentActiveString)) {
            paymentActive = true;
        }
        else{
            paymentActive = false;
        }

        try {
            PaymentResponse response = myApp.getBudgetOnlineService().createOrUpdatePayment(myApp.getSession(), Integer.parseInt(paymentId), paymentName, paymentNumber, paymentBic, paymentActive);
            Integer rt =  response.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values)
    {
        //wird in diesem Beispiel nicht verwendet
    }

    protected void onPostExecute(VendorResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200){

                Log.d("INFO", "Zahlungsart erfolgreich angelegt.");
                // Update der alten Liste
                myApp.checkVendorsList(result.getVendorTo());
                //Toast anzeigen
                CharSequence text = "Zahlungsart gespeichert!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
            }
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Zahlungsart konnte nicht gespeichert werden.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}