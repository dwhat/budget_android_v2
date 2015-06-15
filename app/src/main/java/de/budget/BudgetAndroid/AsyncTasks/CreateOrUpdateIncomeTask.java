package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Income.IncomeActivity;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.Vendors.VendorActivity;
import de.budget.BudgetService.Response.IncomeResponse;
import de.budget.BudgetService.Response.VendorResponse;

/*
    * @Author Christopher
    * @Date 14.06.2015
    */
public class CreateOrUpdateIncomeTask extends AsyncTask<String, Integer, IncomeResponse>
{
    private Context context;
    private static IncomeActivity activity;
    public static MainActivity nextActivity = new MainActivity();
    private static BudgetAndroidApplication myApp;

    public CreateOrUpdateIncomeTask(Context context, BudgetAndroidApplication myApp, IncomeActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }

    @Override
    protected IncomeResponse doInBackground(String... params){
        if(params.length != 7)
            return null;
        String incomeId = params[0];
        String name = params[1];
        String quantity = params[2];
        String amount = params[3];
        String notice = params[4];
        String receiptDate = params[5];
        String categoryId = params[6];

        try {
            IncomeResponse myIncome = myApp.getBudgetOnlineService().createOrUpdateIncome(myApp.getSession(), Integer.parseInt(incomeId), name, Double.parseDouble(quantity),  Double.parseDouble(amount), notice, Long.parseLong(receiptDate), Integer.parseInt(categoryId),myApp);
            Integer rt =  myIncome.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return myIncome;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values)
    {
        //wird in diesem Beispiel nicht verwendet
    }

    protected void onPostExecute(IncomeResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200){

                Log.d("INFO", "Einnahme erfolgreich angelegt.");
                // Update der alten Liste
                myApp.checkIncomeList(result.getIncomeTo());
                //Toast anzeigen
                CharSequence text = "Einnahme gespeichert!";
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
            CharSequence text = "Einnahme konnte nicht gespeichert werden.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}