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
     *  <p>  Asynchroner Task um eine Category zu löschen.
     *
     *      Der Task nimmt einen Parameter eines zu löschenden Objects entgegen.
     *      Die Parameter werden über die BudgetAndroidApplication an das Interface übermittelt,
     *      welches die Versendung an den OnlineService übernimmt.
     *
     *      Die Antwort des Servers wird in einer Response gespeichert.
     *
     *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
     *      welche Interaktion durchgeführt werden soll.
     * </p>
    * @Author Christopher
    * @Date 16.06.2015
    */
public class DeleteCategoryTask extends AsyncTask<Integer, Integer, ReturnCodeResponse>
{
    private static BudgetAndroidApplication myApp;
    private static CategoryActivity activity;
    public static MainActivity nextActivity = new MainActivity();
    private Integer id;
    private Context context;

    public DeleteCategoryTask(Context context, BudgetAndroidApplication myApp, CategoryActivity pActivity)
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
            ReturnCodeResponse myResponse = myApp.getBudgetOnlineService().deleteCategory(myApp.getSession(), id);
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

                myApp.deleteCategory(id);
                Log.d("INFO", "Kategorie erfolgreich gelöscht.");
                //Toast anzeigen
                CharSequence text = "Kategorie gelöscht!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
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