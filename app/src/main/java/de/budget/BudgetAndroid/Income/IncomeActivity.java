package de.budget.BudgetAndroid.Income;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.AsyncTasks.createOrUpdateCategoryTask;
import de.budget.BudgetAndroid.AsyncTasks.createOrUpdateIncomeTask;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.BudgetService.dto.IncomeTO;
import de.budget.R;

public class IncomeActivity extends ActionBarActivity {

    private static long receiptDate;
    private String[] categoryNames;
    private int[] categoryIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        final Calendar c = Calendar.getInstance();
        String incomeReceiptDate = c.get(Calendar.DAY_OF_MONTH)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.YEAR);
        try{
            SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            Date d = f.parse(incomeReceiptDate);
            receiptDate = d.getTime();
        }catch (ParseException e){
            Log.d("ERROR", e.getMessage());
        }

        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
        List<CategoryTO> categoryTOs = myApp.getCategories();
        categoryNames = new String[categoryTOs.size()];
        categoryIds = new int[categoryTOs.size()];
        Log.d("INFO", "Size of Categoryarray: "+categoryTOs.size());
        for (int i=0; i< categoryTOs.size(); i++){
            categoryNames[i] = categoryTOs.get(i).getName();
            categoryIds[i] = categoryTOs.get(i).getId();
        }
        Spinner txtCategorySpinner = (Spinner) findViewById(R.id.income_category);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtCategorySpinner.setAdapter(spinnerArrayAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            // Schreibe Objekt in das Layout
            EditText txtIncomeName = (EditText) findViewById(R.id.income_name);
            EditText txtIncomeNotice = (EditText) findViewById(R.id.income_notice);
            TextView txtIncomeId = (TextView) findViewById(R.id.label_income_id);
            EditText txtIncomeAmount = (EditText) findViewById(R.id.income_amount);
            EditText txtIncomeQuantity = (EditText) findViewById(R.id.income_quantity);
            Spinner txtIncomeCategory = (Spinner) findViewById(R.id.income_category);
            //TODO
           // DatePicker txtReceiptDate = (DatePicker) findViewById(R.id.income_receiptDate);

            // @author Christopher
            int incomePos = bundle.getInt("INCOME_POSITION");
            List<IncomeTO> tmp = myApp.getIncome();
            IncomeTO income = tmp.get(incomePos);
            txtIncomeName.setText(income.getName());
            txtIncomeNotice.setText(income.getNotice());
            txtIncomeAmount.setText(String.valueOf(income.getAmount()));
            txtIncomeQuantity.setText(String.valueOf(income.getQuantity()));
            txtIncomeId.setText(Integer.toString(income.getId()));

            for(int i=0; i< categoryIds.length; i++) {
                if(income.getCategory().getId() == categoryIds[i]) {
                    txtCategorySpinner.setSelection(i);
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_income_new, menu);
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
    @Author(name="Christopher")
    public void save(View v) {
        Toast.makeText(this, "Speichern", Toast.LENGTH_SHORT).show();

        EditText txtIncomeName = (EditText) findViewById(R.id.income_name);
        EditText txtIncomeNotice = (EditText) findViewById(R.id.income_notice);
        TextView txtIncomeId = (TextView) findViewById(R.id.label_income_id);
        EditText txtIncomeAmount = (EditText) findViewById(R.id.income_amount);
        EditText txtIncomeQuantity = (EditText) findViewById(R.id.income_quantity);
        Spinner txtIncomeCategory = (Spinner) findViewById(R.id.income_category);

        String incomeName = txtIncomeName.getText().toString();
        String incomeNotice = txtIncomeNotice.getText().toString();
        String incomeAmount = txtIncomeAmount.getText().toString();
        String incomeQuantity = txtIncomeQuantity.getText().toString();
        Log.d("INFO", incomeQuantity);
        String incomeId = txtIncomeId.getText().toString();
        String incomeCategoryString = txtIncomeCategory.getSelectedItem().toString();
        String incomeCategory = new String();
        for(int i=0; i<categoryNames.length; i++) {
            if (categoryNames[i].equals(incomeCategoryString)) {
                incomeCategory = String.valueOf(categoryIds[i]);
            }
        }
        if(!"".equals(incomeName) && !"".equals(incomeAmount) && !"".equals(incomeQuantity))
        {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
                createOrUpdateIncomeTask task = new createOrUpdateIncomeTask(this,myApp, this);
                task.execute(incomeId, incomeName,  incomeQuantity, incomeAmount, incomeNotice, String.valueOf(receiptDate), incomeCategory);
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

    public void change(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra(MainActivity.FRAGMENT_NAVIGATION,1);
        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * @Author Christopher
     * @date 14.06.2015
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            String date= day+"-"+month+"-"+year;
            try{
            Date d = f.parse(date);
            receiptDate = d.getTime();
                Log.d("INFO", "LONG DATE: " +receiptDate);
            }
            catch (ParseException e){
                Log.d("ERROR", e.getMessage());
            }
        }
    }


}
