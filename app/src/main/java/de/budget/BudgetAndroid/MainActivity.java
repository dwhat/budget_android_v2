package de.budget.BudgetAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.Categories.CategoryFragment;
import de.budget.BudgetAndroid.Categories.CategoryActivity;
import de.budget.BudgetAndroid.Income.IncomeFragment;
import de.budget.BudgetAndroid.Income.IncomeActivity;
import de.budget.BudgetAndroid.Loss.LossActivity;
import de.budget.BudgetAndroid.Loss.LossFragment;
import de.budget.BudgetAndroid.Vendors.VendorActivity;
import de.budget.BudgetAndroid.Vendors.VendorsFragment;
import de.budget.R;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, CategoryFragment.OnFragmentInteractionListener, VendorsFragment.OnFragmentInteractionListener,
                    IncomeFragment.OnFragmentInteractionListener, Logout.OnFragmentInteractionListener, LossFragment.OnFragmentInteractionListener,
                    DashboardFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /*
     * Speichert die geöffnete Fragment Instanz als Position im Navigationdarwer
     */
    private int savedNavigationPosition;
    public static final String FRAGMENT_NAVIGATION = "FRAGMENT_NAVIGATION_POSITION";

    // Erzeuge zwischenspeicher
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    // Preference Modus
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    // Preference File Bezeichnung
    private static final String PREFERENCE_NAVIGATION_STATE_FILE = "PREFERENCE_NAVIGATION_STATE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Navigation Fragment
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        // Get the Shared Preference
        sharedPreferences = getSharedPreferences(PREFERENCE_NAVIGATION_STATE_FILE, PREFERENCE_MODE_PRIVATE);
        // Set up preference editor
        sharedPreferencesEditor = sharedPreferences.edit();

        // Get Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)  {
            // Wechsel zum gewünschten Fragment
            int fragmentNavigation = bundle.getInt(FRAGMENT_NAVIGATION);

            Log.d("INFO", getFragmentByPosition(fragmentNavigation).toString());
            onNavigationDrawerItemSelected(fragmentNavigation);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // Speichere den Satatus des ausgewählten Items
        savedNavigationPosition = position;

        // update the main content by replacing fragments
        Fragment fragment = getFragmentByPosition(position);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("fragback").commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Wähle welches Bedienelement geklickt wurde
        switch (id) {
            case R.id.action_sync:
                synchronizeApplication();
                return true;

            case R.id.action_loss:

                changeActivity(LossActivity.class);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Speichere das ausgeäwhlte Fragment, sobald die Acitivty gewechselt wird
        sharedPreferencesEditor.putInt(FRAGMENT_NAVIGATION, savedNavigationPosition);
        sharedPreferencesEditor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // Hole das in onPause gespeicherte Fragment und lade es in die Activity
            sharedPreferences = getSharedPreferences(PREFERENCE_NAVIGATION_STATE_FILE, PREFERENCE_MODE_PRIVATE);
            onNavigationDrawerItemSelected(sharedPreferences.getInt(FRAGMENT_NAVIGATION, 0));
        }
    }

    @Override
    public void onCategoriesMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onVendorsMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onIncomeMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onLogoutFragmentInteraction(Uri uri){
    }
    @Override
    public void onLossMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onDashboardMainFragmentInteraction(Uri uri){
    }

    public void onSectionAttached(int number) {
        String[] stringArray = getResources().getStringArray(R.array.section_titles);
        if (number >= 1) {
            mTitle = stringArray[number - 1];
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

        // @Author Mark
        // entfernt den Schatten der Actionbar
        // //http://stackoverflow.com/questions/12246388/remove-shadow-below-actionbar
        actionBar.setElevation(0);

        // @Author Mark
        // entfernt den Titel
        // http://stackoverflow.com/questions/7655874/how-do-you-remove-the-title-text-from-the-android-actionbar
        actionBar.setDisplayShowTitleEnabled(false);

    }

    /*
     * Öffne Händler Maske zum anlegen
     */
    @Author(name="Mark")
    public void newVendor (View v){
        changeActivity(VendorActivity.class);
    }

    @Author(name="Mark")
    public void newCategory (View v){
        changeActivity(CategoryActivity.class);
    }

    @Author(name="Mark")
    public void newLoss (View v){
        changeActivity(LossActivity.class);
    }

    @Author(name="Mark")
    public void newIncome (View v){
        changeActivity(IncomeActivity.class);
    }

    /*
     * Toggle das Bottom Sheet Menu
     * Zeige das Menü an oder nicht
     * schiebe den FAB auf die Position des oberen Rands des Bottom Sheet Menüs
     */
    @Author(name="Mark")
    public void toggleBottomSheetMenu (View v) {

        // Hole die Menüliste
        LinearLayout bsm = (LinearLayout) findViewById(R.id.bottom_sheet_menu);

        // Hole den FAB
        ImageButton fab = (ImageButton) findViewById(R.id.fab);

        // Wähle ob Menü sichtbar oder nicht
        if (bsm.getVisibility() != View.VISIBLE){
            bsm.setVisibility(View.VISIBLE);
            fab.setImageDrawable(getDrawable(R.drawable.ic_clear_w));
            fab.bringToFront();
            ViewPropertyAnimator vpa = fab.animate();
            vpa.translationYBy(-535);
        } else {
            bsm.setVisibility(View.INVISIBLE);
            fab.setImageDrawable(getDrawable(R.drawable.ic_add_w));
            //TODO berechnen der translation evtl durch bsm.getHeight(), Padding und größe vom fab ermitteln und davon abziehen
            fab.animate().translationYBy(535);
        }

    }

    /*
     * Liefert das Fragment je nach Positionsnummer im Array
     */
    @Author(name="Mark")
    public Fragment getFragmentByPosition(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new DashboardFragment();
                break;
            case 1:
                fragment = new IncomeFragment();
                break;
            case 2:
                fragment = new LossFragment();
                break;
            case 3:
                fragment = new CategoryFragment();
                break;
            case 4:
                fragment = new VendorsFragment();
                break;
            case 5:
                fragment = new Logout();
                break;
            default:
                fragment = new DashboardFragment();
                break;
        }

        return fragment;

    }

    /*
     * Methode zum synchroniseren der Applikation, bei Fehlerfall, evtl für später bei mehrern Frontends
     *
     */
    @Author(name = "Mark")
    private void synchronizeApplication() {
        Toast.makeText(MainActivity.this, "Synchronisierung des Systems", Toast.LENGTH_SHORT).show();
    }

    /*
     * Methode zum ändern der Activity je nach eingegebener Klasse
     */
    @Author(name = "Mark")
    private void changeActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
