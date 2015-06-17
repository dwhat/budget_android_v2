package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.Response.BasketListResponse;
import de.budget.BudgetService.Response.IncomeListResponse;

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
                Log.d("INFO", "Returncode: " + returnCode.toString());
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
                    myApp.increaseInitialDataCounter();
                    Log.d("INFO", "Liste der Ausgaben erfolgreich angelegt.");
                }
            }
            else
            {
                Log.d("INFO", "Ausgaben konnten nicht geladen werden.");

            }
        }

}
