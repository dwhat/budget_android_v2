package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.BasketListResponse;
import de.budget.BudgetService.Response.IncomeListResponse;
import de.budget.BudgetService.dto.BasketTO;

/**
 * Created by mark on 17/06/15.
 */
public class GetBasketTask extends AsyncTask<String, Integer, BasketListResponse>{

        private Context context;
        private static BudgetAndroidApplication myApp;

        public GetBasketTask(Context context, BudgetAndroidApplication myApp)
        {
            this.context = context;
            this.myApp = myApp;
        }


        @Override
        protected BasketListResponse doInBackground(String... params){
            if(params.length != 0)
                return null;

            try {

                BasketListResponse basket = myApp.getBudgetOnlineService().getBaskets(myApp.getSession(), myApp);
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
                if (result.getReturnCode() == 200){

                    myApp.setBasket(result.getBasketList());
                    Log.d("INFO", "Liste der Ausgaben erfolgreich angelegt.");
                    myApp.increaseInitialDataCounter();

                }
            }
            else
            {
                Log.d("INFO", "Ausgaben konnten nicht geladen werden.");

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
