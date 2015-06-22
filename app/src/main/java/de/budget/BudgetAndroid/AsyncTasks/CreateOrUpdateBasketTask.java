package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Income.IncomeActivity;
import de.budget.BudgetAndroid.Loss.LossActivity;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.BasketResponse;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.BudgetService.dto.ItemTO;

/**
 * Created by mark on 18/06/15.
 * <p>  Asynchroner Task um ein Basket anzulegen oder zu ändern.
 *
 *      Der Task nimmt die Parameter eines neuen oder zu ändernden Objects entgegen.
 *      Die Parameter werden über die BudgetAndroidApplication an das Interface übermittelt,
 *      welches die Versendung an den OnlineService übernimmt.
 *
 *      Die Antwort des Servers wird in einer BasketResponse gespeichert.
 *       *
 *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
 *      welche Interaktion durchgeführt werden soll.
 * </p>
 * @author Mark
 */
public class CreateOrUpdateBasketTask extends AsyncTask<Object, Integer, BasketResponse> {

    private Context context;
    public static MainActivity nextActivity = new MainActivity();
    private static BudgetAndroidApplication myApp;

    private List<ItemTO> items;

    public CreateOrUpdateBasketTask(Context context, BudgetAndroidApplication myApp)
    {
        this.context = context;
        this.myApp = myApp;
    }

    @Override
    protected BasketResponse doInBackground(Object... params){

        int length = params.length;
        if(length != 8) return null;

        int basketId        = (int)             params[0];
        String name         = (String)          params[1];
        String notice       = (String)          params[2];
        Double amount       = (Double)          params[3];
        Long date           = (Long)            params[4];
        int paymentId       = (int)             params[5];
        int vendorId        = (int)             params[6];
        this.items          = (List<ItemTO>)    params[7];


        Log.d("INFO", date + " : " + basketId + ". " + name + " " + notice + " " + amount + "€ " + paymentId + " " + vendorId);


        try {
            //     public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId);
            BasketResponse basket = myApp.getBudgetOnlineService().createOrUpdateBasket(myApp.getSession(), basketId, name, notice, amount, date, paymentId, vendorId);
            Log.d("INFO", basket.toString());
            Integer rt =  basket.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return basket;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

        protected void onProgessUpdate(Integer... values)
        {
            //wird in diesem Beispiel nicht verwendet
        }

        protected void onPostExecute(BasketResponse result)
        {
            int duration = Toast.LENGTH_SHORT;
            if(result != null)
            {
                //erfolgreich eingeloggt
                if (result.getReturnCode() == 200){

                    Log.d("INFO", "Ausgabe erfolgreich angelegt.");

                    BasketTO basket = result.getBasketTo();

                    // Update der alten Liste
                    myApp.checkBasketList(basket);

                    for(ItemTO item : items) {
                        CreateOrUpdateItemTask task = new CreateOrUpdateItemTask(context ,myApp);
                        if (item.getId()==0) task.execute(0, item.getName(), item.getQuantity(), item.getPrice(), item.getNotice(), item.getReceiptDate(), item.getCategory(), basket.getId());
                        else task.execute(item.getId(), item.getName(), item.getQuantity(), item.getPrice(), item.getNotice(), item.getReceiptDate(), item.getCategory(), basket.getId());
                    }

                    Toast.makeText(context, "Ausgabe gespeichert", Toast.LENGTH_SHORT).show();

                    //Nächste Activity anzeigen
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(context, "Ausgabe konnte nicht gespeichert werden.", Toast.LENGTH_SHORT).show();
            }
        }

}
