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
 *  * <p>  Asynchroner Task um ein Basket zu löschen.
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
 * Created by mark on 19/06/15.
 * @author Mark
 */
public class DeleteBasketTask extends AsyncTask<Integer, Integer, ReturnCodeResponse>  {

    private static BudgetAndroidApplication myApp;
    public static MainActivity nextActivity = new MainActivity();
    private Context context;
    private int id;

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

                //Toast anzeigen
                CharSequence text = "Ausgabe gelöscht!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        }
        else
        {
            Log.d("INFO", "Ausgabe konnte nicht gelöscht werden.");
            CharSequence text = "Ausgabe konnte nicht gelöscht werden!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
