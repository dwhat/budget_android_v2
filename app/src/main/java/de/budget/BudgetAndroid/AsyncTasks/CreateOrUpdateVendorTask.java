package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.Vendors.VendorActivity;
import de.budget.BudgetService.Response.VendorResponse;

    /**
     *  <p>  Asynchroner Task um ein Vendor anzulegen oder zu ändern.
     *
     *      Der Task nimmt die Parameter eines neuen oder zu ändernden Basket Objects entgegen.
     *      Die Parameter werden über die BudgetAndroidApplication an das Interface übermittelt,
     *      welches die Versendung an den OnlineService übernimmt.
     *
     *      Die Antwort des Servers wird in einer Response gespeichert.
     *
     *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
     *      welche Interaktion durchgeführt werden soll.
     * </p>
    * @Author Christopher
    * @Date 09.06.2015
    */
public class CreateOrUpdateVendorTask extends AsyncTask<String, Integer, VendorResponse>
{
    private Context context;
    private static VendorActivity activity;
    public static MainActivity nextActivity = new MainActivity();
    private static BudgetAndroidApplication myApp;

    public CreateOrUpdateVendorTask(Context context, BudgetAndroidApplication myApp, VendorActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }

    @Override
    protected VendorResponse doInBackground(String... params){
        if(params.length != 6)
            return null;
        String vendorName = params[0];
        String vendorStreet = params[1];
        String vendorNr = params[2];
        String vendorPlz = params[3];
        String vendorCity = params[4];
        String vendorId = params[5];

        try {
            VendorResponse myVendor = myApp.getBudgetOnlineService().createOrUpdateVendor(myApp.getSession(), Integer.parseInt(vendorId), vendorName, "",  vendorStreet, vendorCity, Integer.parseInt(vendorPlz), Integer.parseInt(vendorNr) );
            Integer rt =  myVendor.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return myVendor;
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

                Log.d("INFO", "Händler erfolgreich angelegt.");
                // Update der alten Liste
                myApp.checkVendorsList(result.getVendorTo());
                //Toast anzeigen
                CharSequence text = "Händler gespeichert!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
            }
            else if (result.getReturnCode() == 407){
                CharSequence text = "Bitte zuerst Einnahmen/Ausgaben des Händlers löschen!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Händler konnte nicht gespeichert werden.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}