package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.math.BigDecimal;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.BasketListResponse;
import de.budget.BudgetService.Response.IncomeListResponse;
import de.budget.BudgetService.dto.BasketTO;

/**
 * <p>  Asynchroner Task um eine Liste Basket zu erhalten.
 *
 *      Der Task nimmt keine Parameter entgegen. Der Task schickt die aktuelle SessionID zur Ermittlung
 *      der aktiven Baskets zum Interface.
 *
 *      Die Antwort des Servers wird in einer Response gespeichert.
 *
 *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
 *      welche Interaktion durchgeführt werden soll.
 *  </p>
 * Created by mark on 17/06/15.
 * @Author Mark
 */
public class GetBasketTask extends AsyncTask<String, Integer, BasketListResponse>{

        private OnTaskCompleted listener = null;
        private Context context;
        private static BudgetAndroidApplication myApp;

        public GetBasketTask(Context context, BudgetAndroidApplication myApp, OnTaskCompleted listener)
        {
            this.context = context;
            this.myApp = myApp;
            this.listener = listener;
        }


        @Override
        protected BasketListResponse doInBackground(String... params){
            if(params.length != 0)
                return null;

            try {

                BasketListResponse basket = myApp.getBudgetOnlineService().getBaskets(myApp.getSession());
                Integer returnCode =  basket.getReturnCode();
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

        @Override
        protected void onPostExecute(BasketListResponse result)
        {
            if(result != null)
            {
                if (result.getReturnCode() == 200 || result.getReturnCode() == 404 ){

                    myApp.setBasket(result.getBasketList());
                    Log.d("INFO", "Liste der Ausgaben erfolgreich angelegt.");
                    myApp.increaseInitialDataCounter();
                    BigDecimal loss = new BigDecimal(0);
                    for (int i= 0; i < myApp.getBasket().size(); i++){
                        loss = loss.add(BigDecimal.valueOf(myApp.getBasket().get(i).getAmount()));
                    }
                    myApp.setLossLastPeriod(loss.doubleValue());
                    listener.onTaskCompleted(true);

                }
            }
            else
            {
                Log.d("INFO", "Ausgaben konnten nicht geladen werden.");
                listener.onTaskCompleted(false);

            }
        }

    private void getItemsByBasketTask () {
        for(BasketTO basket : myApp.getBasket()) {
            if (basket.getItems() == null) {
                if (!basket.isOccupied()){
                    basket.setOccupied(true);
                    GetItemsTask itemsTask = new GetItemsTask(myApp.getApplicationContext(), myApp, basket, new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(Object o) {
                        }
                    });
                    itemsTask.execute();
                }
            }
        }
        Log.d("INFO", "Liste der Items erfolgreich angelegt.");
    }


}
