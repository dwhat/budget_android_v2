package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Categories.CategoryActivity;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.ReturnCodeResponse;

/**
 * Created by mark on 19/06/15.
 */
public class DeleteBasketTask extends AsyncTask<Integer, Integer, ReturnCodeResponse>  {

    private static BudgetAndroidApplication myApp;
    public static MainActivity nextActivity = new MainActivity();
    private Integer id;
    private Context context;

    public DeleteBasketTask(Context context, BudgetAndroidApplication myApp)
    {
        this.context = context;
        this.myApp = myApp;
    }


    @Override
    protected ReturnCodeResponse doInBackground(Integer... params){
        if(params.length != 1)
            return null;
        id = params[0];
        try {
            ReturnCodeResponse myResponse = myApp.getBudgetOnlineService().deleteBasket(myApp.getSession(), id);
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

                myApp.deleteBasket(id);
                Log.d("INFO", "Kategorie erfolgreich gelöscht.");
                //Toast anzeigen
                CharSequence text = "Kategorie gelöscht!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        }
        else
        {
            Log.d("INFO", "Kategorie konnte nicht gelöscht werden.");
            CharSequence text = "Kategorie konnte nicht gelöscht werden!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}