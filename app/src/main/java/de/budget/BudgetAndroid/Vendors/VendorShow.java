package de.budget.BudgetAndroid.Vendors;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import de.budget.R;

public class VendorShow extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_show);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("VENDOR_NAME");
            EditText editText = (EditText) findViewById(R.id.vendor_name);
            editText.setText(name);
        }
    }

}
