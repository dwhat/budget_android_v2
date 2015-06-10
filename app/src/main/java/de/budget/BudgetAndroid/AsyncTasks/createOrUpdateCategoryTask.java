package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Categories.CategoryActivity;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.CategoryResponse;

/**
 * Created by christopher on 10.06.15.
 */
public class createOrUpdateCategoryTask extends AsyncTask<String, Boolean, CategoryResponse>
{
        private Context context;
        private static CategoryActivity activity;

        public createOrUpdateCategoryTask(Context context, CategoryActivity pActivity)
        {
            this.context = context;
            this.activity = pActivity;
        }

        @Override
        protected CategoryResponse doInBackground(String... params){
            if(params.length != 5)
                return null;
            String incomeOrLossString = params[0];;
            String categoryName = params[1];
            String categoryNotice = params[2];
            String categoryColour = params[3];
            String categoryId = params[4];

            Boolean incomeOrLoss, categoryActive = true;

            if ("Einnahme".equals(incomeOrLossString)) {
                incomeOrLoss = true;
            }
            else{
                incomeOrLoss = false;
            }

            BudgetAndroidApplication myApp = (BudgetAndroidApplication) activity.getApplication();
            try {
                CategoryResponse myCategory = myApp.getBudgetOnlineService().createOrUpdateCategory(myApp.getSession(), Integer.parseInt(categoryId), incomeOrLoss, categoryActive, categoryName, categoryNotice, categoryColour);
                Integer rt =  myCategory.getReturnCode();
                Log.d("INFO", "Returncode: " + rt.toString());
                return myCategory;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {
            //wird in diesem Beispiel nicht verwendet
        }

        protected void onPostExecute(CategoryResponse result)
        {
            int duration = Toast.LENGTH_SHORT;
            if(result != null)
            {
                //erfolgreich eingeloggt
                if (result.getReturnCode() == 200){

                    Log.d("INFO", "Kategorie erfolgreich angelegt.");
                    //Toast anzeigen
                    CharSequence text = "Kategorie gespeichert!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    //Nächste Activity anzeigen
                    Intent intent = new Intent(context, MainActivity.class);
                    activity.startActivity(intent);
                }
            }
            else
            {
                //Toast anzeigen
                CharSequence text = "Kategorie konnte nicht gespeichert werden.";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
}
