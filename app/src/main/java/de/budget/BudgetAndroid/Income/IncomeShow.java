package de.budget.BudgetAndroid.Income;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import de.budget.R;

public class IncomeShow extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_show);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("INCOME_NAME");
            EditText editText = (EditText) findViewById(R.id.income_name);
            editText.setText(name);
        }
    }


}
