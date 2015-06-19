package de.budget.BudgetAndroid.Categories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.budget.BudgetAndroid.AsyncTasks.DeleteCategoryTask;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.R;
import de.budget.BudgetAndroid.AsyncTasks.CreateOrUpdateCategoryTask;

public class CategoryActivity extends ActionBarActivity {

    private BudgetAndroidApplication myApp;
    private boolean newCategory = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        myApp = (BudgetAndroidApplication) getApplication();

        Bundle bundle = getIntent().getExtras();
        // Prüfe ob ein Objekt übergeben worden ist
        if (bundle != null) {
            newCategory = false;
            // Schreibe Objekt in das Layout
            EditText txtCategoryName = (EditText) findViewById(R.id.category_name);
            EditText txtCategoryNotice = (EditText) findViewById(R.id.category_notice);
            TextView txtCategoryId = (TextView) findViewById(R.id.label_category_id);
            RadioButton rbIncome =(RadioButton)findViewById(R.id.rb_category_income);
            RadioButton rbLoss =(RadioButton)findViewById(R.id.rb_category_loss);

            // @author Christopher
            int categoryPos = bundle.getInt("CATEGORY_POSITION");
            List<CategoryTO> tmp = myApp.getCategories();
            CategoryTO category = tmp.get(categoryPos);
            txtCategoryName.setText(category.getName());
            txtCategoryNotice.setText(category.getNotice());
            if(category.isIncome()){
                rbIncome.toggle();
            }
            else{
                rbLoss.toggle();
            }
            txtCategoryId.setText(Integer.toString(category.getId()));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        if(newCategory) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
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
        else if (id == R.id.action_delete) {
            delete(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Methode zum speichern des Objekts
     * @author Christopher
     * @date 08.06.2015
     */
    public void save(View v) {
        Toast.makeText(this, "Speichern", Toast.LENGTH_SHORT).show();

        EditText txtCategoryName = (EditText) findViewById(R.id.category_name);
        EditText txtCategoryNotice = (EditText) findViewById(R.id.category_notice);
        RadioGroup rgIncomeOrLoss =(RadioGroup)findViewById(R.id.category_type);
        RadioButton radioButton = (RadioButton) this.findViewById(rgIncomeOrLoss.getCheckedRadioButtonId());
        TextView txtCategoryId = (TextView) findViewById(R.id.label_category_id);

        String categoryName = txtCategoryName.getText().toString();
        String incomeOrLoss = radioButton.getText().toString();
        String categoryNotice = txtCategoryNotice.getText().toString();
        String categoryColor = "gelb";
        String categoryId = txtCategoryId.getText().toString();

        if(!"".equals(categoryName) && !"".equals(incomeOrLoss))
        {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                CreateOrUpdateCategoryTask task = new CreateOrUpdateCategoryTask(this,myApp, this);
                task.execute(incomeOrLoss, categoryName, categoryNotice, categoryColor, categoryId);
            }
            else {
                CharSequence text = "Keine Netzwerkverbindung! :(";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
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
     * @Author Christopher
     * @Date 17.06.2015
    * Methode zum Löschen des Objekts
     */
    public void delete(View v) {
        Toast.makeText(this, "Löschen", Toast.LENGTH_SHORT).show();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            TextView txtCategoryId = (TextView) findViewById(R.id.label_category_id);
            DeleteCategoryTask task = new DeleteCategoryTask(this, myApp, this);
            task.execute(Integer.parseInt(txtCategoryId.getText().toString()));
        }
        else {
            CharSequence text = "Keine Netzwerkverbindung! :(";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

}
