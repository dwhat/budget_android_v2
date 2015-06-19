package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.BudgetService.Response.VendorListResponse;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class GetVendorsTask extends AsyncTask<String, Integer, VendorListResponse>
{
    private OnTaskCompleted listener = null;
    private Context context;
    private static BudgetAndroidApplication myApp;

    public GetVendorsTask(Context context, BudgetAndroidApplication myApp, OnTaskCompleted listener)
    {
        this.context = context;
        this.myApp = myApp;
        this.listener = listener;
    }


    @Override
    protected VendorListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            VendorListResponse myVendors = myApp.getBudgetOnlineService().getVendors(myApp.getSession());
            Integer rt =  myVendors.getReturnCode();
            //Log.d("INFO", "Returncode: " + rt.toString());
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

    @Override
    protected void onPostExecute(VendorListResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200 || result.getReturnCode() == 404 ){

                myApp.setVendors(result.getVendorList());
                myApp.increaseInitialDataCounter();
                Log.d("INFO", "Händlerliste erfolgreich angelegt.");
                listener.onTaskCompleted(true);
            }
        }
        else
        {
            Log.d("INFO", "Händler konnten nicht geladen werden.");
            listener.onTaskCompleted(false);

        }
    }
}