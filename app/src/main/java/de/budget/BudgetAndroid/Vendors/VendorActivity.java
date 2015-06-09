package de.budget.BudgetAndroid.Vendors;

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
import android.widget.Toast;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.CategoryResponse;
import de.budget.BudgetService.Response.VendorResponse;
import de.budget.R;

public class VendorActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("VENDOR_NAME");
            EditText editText = (EditText) findViewById(R.id.vendor_name);
            editText.setText(name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vendor_new, menu);
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
        //TODO Die eingengeben Werte an den Server schicken
        Toast.makeText(this, "Speichern", Toast.LENGTH_SHORT).show();

        EditText txtVendorName = (EditText) findViewById(R.id.vendor_name);
        EditText txtVendorAddress = (EditText) findViewById(R.id.vendor_address);

        String vendorName = txtVendorName.getText().toString();
        String vendorAddress = txtVendorAddress.getText().toString();


        if(!"".equals(vendorName) && !"".equals(vendorAddress))
        {
            createOrUpdateVendorTask task = new createOrUpdateVendorTask(this);
            //Proxy asynchron aufrufen
            task.execute(vendorName, vendorAddress);
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
    private class createOrUpdateVendorTask extends AsyncTask<String, Integer, VendorResponse>
    {
        private Context context;

        public createOrUpdateVendorTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected VendorResponse doInBackground(String... params){
            if(params.length != 2)
                return null;
            String vendorName = params[0];;
            String vendorLogo = params[1];

            BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
            try {
                VendorResponse myVendor = myApp.getBudgetOnlineService().createOrUpdateVendor(myApp.getSession(), 0, vendorName, vendorLogo);
                Integer rt =  myVendor.getReturnCode();
                Log.d("INFO", "Returncode: " + rt.toString());
                return myVendor;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {
            //wird in diesem Beispiel nicht verwendet
        }

        protected void onPostExecute(VendorResponse result)
        {
            int duration = Toast.LENGTH_SHORT;
            if(result != null)
            {
                //erfolgreich eingeloggt
                if (result.getReturnCode() == 200){

                    Log.d("INFO", "Händler erfolgreich angelegt.");
                    //Toast anzeigen
                    CharSequence text = "Händler gespeichert!";
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
                CharSequence text = "Händler konnte nicht gespeichert werden.";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
