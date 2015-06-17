package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.Response.BasketListResponse;
import de.budget.BudgetService.Response.ItemListResponse;
import de.budget.BudgetService.dto.BasketTO;

/**
 * Created by mark on 17/06/15.
 */
public class GetItemsTask extends AsyncTask<String, Integer, ItemListResponse> {

        private Context context;
        private static BudgetAndroidApplication myApp;
        private int basketId;

        public GetItemsTask(Context context, BudgetAndroidApplication myApp, int basketId)
        {
            this.context = context;
            this.myApp = myApp;
            this.basketId = basketId;

        }


        @Override
        protected ItemListResponse doInBackground(String... params){
            if(params.length != 0)
                return null;

            try {

                    ItemListResponse item = myApp.getBudgetOnlineService().getItemsByBasket(myApp.getSession(), basketId, myApp);

                    Integer returnCode =  item.getReturnCode();
                    Log.d("INFO", "Returncode: " + returnCode.toString());
                    return item;


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
        protected void onPostExecute(ItemListResponse result)
        {
            if(result != null)
            {
                if (result.getReturnCode() == 200){

                    myApp.setItems(result.getItemList());
                    myApp.increaseInitialDataCounter();
                    Log.d("INFO", "Liste der Items erfolgreich angelegt.");
                }
            }
            else
            {
                Log.d("INFO", "Items konnten nicht geladen werden.");

            }
        }

}
