package de.budget.BudgetAndroid.Loss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import de.budget.R;

public class LossShow extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss_show);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("LOSS_NAME");
            EditText editText = (EditText) findViewById(R.id.loss_name);
            editText.setText(name);
        }
    }


}
