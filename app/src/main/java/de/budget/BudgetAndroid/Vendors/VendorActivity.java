package de.budget.BudgetAndroid.Vendors;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.budget.BudgetAndroid.AsyncTasks.CreateOrUpdateIncomeTask;
import de.budget.BudgetAndroid.AsyncTasks.DeleteIncomeTask;
import de.budget.BudgetAndroid.AsyncTasks.DeleteVendorTask;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.VendorTO;
import de.budget.R;
import de.budget.BudgetAndroid.AsyncTasks.CreateOrUpdateVendorTask;

public class VendorActivity extends ActionBarActivity {

    private boolean newVendor = true;
    private BudgetAndroidApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        myApp = (BudgetAndroidApplication) getApplication();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newVendor = false;
            // @author Christopher
            // @date 10.06.2015
            EditText txtVendorName = (EditText) findViewById(R.id.vendor_name);
            EditText txtVendorStreet = (EditText) findViewById(R.id.vendor_street);
            EditText txtVendorNr = (EditText) findViewById(R.id.vendor_nr);
            EditText txtVendorCity = (EditText) findViewById(R.id.vendor_city);
            EditText txtVendorPlz = (EditText) findViewById(R.id.vendor_plz);
            TextView txtVendorId = (TextView) findViewById(R.id.label_vendor_id);

            int vendorPos = bundle.getInt("VENDOR_POSITION");
            myApp = (BudgetAndroidApplication) getApplication();
            List<VendorTO> tmp = myApp.getVendors();
            VendorTO vendor = tmp.get(vendorPos);
            txtVendorName.setText(vendor.getName());
            txtVendorStreet.setText(vendor.getStreet());
            txtVendorNr.setText(Integer.toString(vendor.getHouseNumber()));
            txtVendorCity.setText(vendor.getCity());
            txtVendorPlz.setText(Integer.toString(vendor.getPLZ()));
            txtVendorId.setText(Integer.toString(vendor.getId()));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vendor_new, menu);
        if(newVendor) {
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
     * @date 09.06.2015
     */
    public void save(View v) {
        Toast.makeText(this, "Speichern", Toast.LENGTH_SHORT).show();

        EditText txtVendorName = (EditText) findViewById(R.id.vendor_name);
        EditText txtVendorStreet = (EditText) findViewById(R.id.vendor_street);
        EditText txtVendorNr = (EditText) findViewById(R.id.vendor_nr);
        EditText txtVendorCity = (EditText) findViewById(R.id.vendor_city);
        EditText txtVendorPlz = (EditText) findViewById(R.id.vendor_plz);
        TextView txtVendorId = (TextView) findViewById(R.id.label_vendor_id);

        String vendorName = txtVendorName.getText().toString();
        String vendorStreet = txtVendorStreet.getText().toString();
        String vendorNr = txtVendorNr.getText().toString();
        String vendorPlz = txtVendorPlz.getText().toString();
        String vendorCity = txtVendorCity.getText().toString();
        String vendorId = txtVendorId.getText().toString();


        if(!"".equals(vendorName) && !"".equals(vendorStreet) && !"".equals(vendorPlz) && !"".equals(vendorCity))
        {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                CreateOrUpdateVendorTask task = new CreateOrUpdateVendorTask(this, myApp,this);
                task.execute(vendorName, vendorStreet, vendorNr, vendorPlz, vendorCity , vendorId);
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
            TextView txtVendorId = (TextView) findViewById(R.id.label_vendor_id);
            DeleteVendorTask task = new DeleteVendorTask(this, myApp, this);
            task.execute(Integer.parseInt(txtVendorId.getText().toString()));
        }
        else {
            CharSequence text = "Keine Netzwerkverbindung! :(";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

}
