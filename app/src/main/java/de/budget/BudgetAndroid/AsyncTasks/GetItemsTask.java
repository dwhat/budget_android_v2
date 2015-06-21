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

        private OnTaskCompleted listener = null;

        private Context context;
        private static BudgetAndroidApplication myApp;
        private BasketTO basket;

        public GetItemsTask(Context context, BudgetAndroidApplication myApp, BasketTO basket, OnTaskCompleted listener)
        {
            this.context = context;
            this.myApp = myApp;
            this.basket = basket;
            this.listener = listener;

        }


        @Override
        protected ItemListResponse doInBackground(String... params){
            if(params.length != 0)
                return null;
            try {
                ItemListResponse item = myApp.getBudgetOnlineService().getItemsByBasket(myApp.getSession(), basket.getId());
                Integer returnCode =  item.getReturnCode();
                return item;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {

        }

        @Override
        protected void onPostExecute(ItemListResponse result)
        {
            if(result != null) {
                if (result.getReturnCode() == 200){
                    myApp.setItems(result.getItemList());
                    basket.setOccupied(false);
                    listener.onTaskCompleted(true);
                }
            } else {
                Log.d("INFO", "Items konnten nicht geladen werden.");
                listener.onTaskCompleted(false);
            }
        }

}
