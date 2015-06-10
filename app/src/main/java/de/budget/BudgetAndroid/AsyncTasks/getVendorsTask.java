package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.BudgetService.Response.VendorListResponse;

/*
    * @author Christopher
    * @date 09.06.2015
    */
public class getVendorsTask extends AsyncTask<String, Integer, VendorListResponse>
{
    private Context context;
    private static MainActivity activity;
    private static BudgetAndroidApplication myApp;

    public getVendorsTask(Context context, BudgetAndroidApplication myApp, MainActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }


    @Override
    protected VendorListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            VendorListResponse myVendors = myApp.getBudgetOnlineService().getVendors(myApp.getSession());
            Integer rt =  myVendors.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return myVendors;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values)
    {
        //wird in diesem Beispiel nicht verwendet
    }

    protected void onPostExecute(VendorListResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200){

                myApp.setVendors(result.getVendorList());
                Log.d("INFO", "Händlerliste erfolgreich angelegt.");
            }
        }
        else
        {
            Log.d("INFO", "Händler konnten nicht geladen werden.");

        }
    }
}