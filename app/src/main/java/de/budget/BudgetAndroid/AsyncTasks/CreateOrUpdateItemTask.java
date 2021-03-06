package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.BasketResponse;
import de.budget.BudgetService.Response.ItemResponse;
import de.budget.BudgetService.dto.ItemTO;

/**
 *  <p>  Asynchroner Task um ein Basket anzulegen oder zu ändern.
 *
 *      Der Task nimmt die Parameter eines neuen oder zu ändernden Basket Objects entgegen.
 *      Die Parameter werden über die BudgetAndroidApplication an das Interface übermittelt,
 *      welches die Versendung an den OnlineService übernimmt.
 *
 *      Die Antwort des Servers wird in einer Response gespeichert.
 *       *
 *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
 *      welche Interaktion durchgeführt werden soll.
 * </p>
 * Created by mark on 19/06/15.
 * @author Mark
 *
 */
public class CreateOrUpdateItemTask extends AsyncTask<Object, Integer, ItemResponse>{


        private Context context;
        private static BudgetAndroidApplication myApp;

        public CreateOrUpdateItemTask(Context context, BudgetAndroidApplication myApp)
        {
            this.context = context;
            this.myApp = myApp;
        }

        @Override
        protected ItemResponse doInBackground(Object... params){

            int length = params.length;
            if(length != 8) return null;

            int id           = (int)    params[0];
            String name      = (String) params[1];
            Double quantity  = (Double) params[2];
            Double price     = (Double) params[3];
            String notice    = (String) params[4];
            Long receiptDate = (Long)   params[5];
            int category     = (int)    params[6];
            int basket       = (int)    params[7];

           //  Log.d("INFO", receiptDate + " : " + name + " " + quantity + " x " + price + "€ " + notice + " " + category + " " + basket);

            try {
                ItemResponse item = myApp.getBudgetOnlineService().createOrUpdateItem(myApp.getSession(), id, name, quantity, price, notice, receiptDate, basket, category);
                // Log.d("INFO", item.toString());
                Integer rt =  item.getReturnCode();
                // Log.d("INFO", "Returncode: " + rt.toString());
                return item;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {
        }

        protected void onPostExecute(ItemResponse result)
        {
            if(result != null)
            {
                if (result.getReturnCode() == 200){

                    // Log.d("Info", result.getItemTo().toString());

                    ItemTO item = result.getItemTo();
                    myApp.getBasketById(item.getBasket()).setItem(item);

                }
            }
            else
            {
                Toast.makeText(context, "Item konnte nicht gespeichert werden.", Toast.LENGTH_SHORT).show();
            }
        }


}
