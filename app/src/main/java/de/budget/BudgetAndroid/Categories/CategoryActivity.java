package de.budget.BudgetAndroid.Categories;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.CategoryResponse;
import de.budget.BudgetService.Response.UserLoginResponse;
import de.budget.R;

public class CategoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        Bundle bundle = getIntent().getExtras();
        // Prüfe ob ein Objekt übergeben worden ist
        if (bundle != null) {

            // Schreibe Objekt in das Layout
            String name = bundle.getString("CATEGORY_NAME");
            EditText editText = (EditText) findViewById(R.id.category_name);
            editText.setText(name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            save(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Methode zum speichern des Objekts
     */
    @Author(name="Mark")
    public void save(View v) {
        Toast.makeText(this, "Speichern", Toast.LENGTH_SHORT).show();

        // Start Async Task
        // @author Christopher
        // @date 08.06.2015
        EditText txtCategoryName = (EditText) findViewById(R.id.category_name);
        RadioGroup rgIncomeOrLoss =(RadioGroup)findViewById(R.id.category_group);
        RadioButton radioButton = (RadioButton) this.findViewById(rgIncomeOrLoss.getCheckedRadioButtonId());
        // TODO Spinner?!

        String categoryName = txtCategoryName.getText().toString();
        String incomeOrLoss = radioButton.getText().toString();

        if(!"".equals(categoryName) && !"".equals(incomeOrLoss))
        {
            createOrUpdateCategoryTask task = new createOrUpdateCategoryTask(this);
            //Proxy asynchron aufrufen
            task.execute(categoryName, incomeOrLoss);
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Bitte alle Felder ausfüllen!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

    /*
     * @author Christopher
     * @date 08.06.2015
     */
    private class createOrUpdateCategoryTask extends AsyncTask<String, Boolean, CategoryResponse>
    {
        private Context context;

        public createOrUpdateCategoryTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected CategoryResponse doInBackground(String... params){
            if(params.length != 3)
                return null;
            String incomeOrLossString = params[0];
            String categoryActiveString = params[1];
            String categoryName = params[2];
            String categoryNotice = params[3];
            String categoryColour = params[4];

            Boolean incomeOrLoss, categoryActive;

            if ("income".equals(incomeOrLossString)) {
                incomeOrLoss = true;
            }
            else{
                incomeOrLoss = false;
            }
            if ("active".equals(categoryActiveString)){
                categoryActive = true;
            }
            else{
                categoryActive = false;
            }

            BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
            try {
                CategoryResponse myCategory = myApp.getBudgetOnlineService().createOrUpdateCategory(myApp.getSession(), 0, incomeOrLoss, categoryActive, categoryName, categoryNotice, categoryColour);
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
                    startActivity(intent);
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
}
