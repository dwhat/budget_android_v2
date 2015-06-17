package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.Vendors.VendorActivity;
import de.budget.BudgetService.Response.ReturnCodeResponse;

/*
    * @Author Christopher
    * @Date 16.06.2015
    */
public class DeleteVendorTask extends AsyncTask<Integer, Integer, ReturnCodeResponse>
{
    private static BudgetAndroidApplication myApp;
    private static VendorActivity activity;
    public static MainActivity nextActivity = new MainActivity();
    private Integer id;
    private Context context;

    public DeleteVendorTask(Context context, BudgetAndroidApplication myApp, VendorActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }


    @Override
    protected ReturnCodeResponse doInBackground(Integer... params){
        if(params.length != 1)
            return null;
        id = params[0];
        try {
            ReturnCodeResponse myResponse = myApp.getBudgetOnlineService().deleteVendor(myApp.getSession(), id);
            Integer rt =  myResponse.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return myResponse;
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
    protected void onPostExecute(ReturnCodeResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            if (result.getReturnCode() == 200){

                myApp.deleteVendor(id);
                Log.d("INFO", "Händler erfolgreich gelöscht.");
                //Toast anzeigen
                CharSequence text = "Händler gelöscht!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
            }
            else if (result.getReturnCode() == 407){
                CharSequence text = "Bitte zuerst Einnahmen/Ausgaben der Kategorie löschen!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else
        {
            Log.d("INFO", "Händler konnte nicht gelöscht werden.");
            CharSequence text = "Händler konnte nicht gelöscht werden!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}