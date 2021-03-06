package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.PaymentListResponse;
import de.budget.BudgetService.Response.VendorListResponse;

    /**
     *  <p>  Asynchroner Task um eine Liste Payments zu erhalten.
     *
     *      Der Task nimmt keine Parameter entgegen. Der Task schickt die aktuelle SessionID zur Ermittlung
     *      der Payments zum Interface.
     *
     *      Die Antwort des Servers wird in einer Response gespeichert.
     *
     *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
     *      welche Interaktion durchgeführt werden soll.
     *  </p>
    * @Author Christopher
    * @Date 11.06.2015
    */
public class GetPaymentsTask extends AsyncTask<String, Integer, PaymentListResponse>
{
    private OnTaskCompleted listener = null;
    private Context context;
    private static SyncActivity activity;
    private static BudgetAndroidApplication myApp;

    public GetPaymentsTask(Context context, BudgetAndroidApplication myApp, OnTaskCompleted listener)
    {
        this.context = context;
        this.myApp = myApp;
        this.listener = listener;
    }


    @Override
    protected PaymentListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            PaymentListResponse response = myApp.getBudgetOnlineService().getPayments(myApp.getSession());
            Integer rt =  response.getReturnCode();
            //Log.d("INFO", "Returncode: " + rt.toString());
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

    @Override
    protected void onPostExecute(PaymentListResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200 || result.getReturnCode() == 404 ){

                myApp.setPayments(result.getPaymentList());
                myApp.increaseInitialDataCounter();
                Log.d("INFO", "Zahlungsartenliste erfolgreich angelegt.");
                listener.onTaskCompleted(true);
            }
        }
        else
        {
            Log.d("INFO", "Zahlungsarten konnten nicht geladen werden.");
            listener.onTaskCompleted(false);

        }
    }
}