package de.budget.BudgetAndroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Toast;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.Categories.CategoriesMain;
import de.budget.BudgetAndroid.Income.IncomeMain;
import de.budget.BudgetAndroid.Loss.LossMain;
import de.budget.BudgetAndroid.Loss.LossNew;
import de.budget.BudgetAndroid.Vendors.VendorsMain;
import de.budget.R;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, CategoriesMain.OnFragmentInteractionListener, VendorsMain.OnFragmentInteractionListener,
                    IncomeMain.OnFragmentInteractionListener, Logout.OnFragmentInteractionListener, LossMain.OnFragmentInteractionListener,
                    DashboardMain.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new DashboardMain();
                break;
            case 1:
                fragment = new IncomeMain();
                break;
            case 2:
                fragment = new LossMain();
                break;
            case 3:
                fragment = new VendorsMain();
                break;
            case 4:
                fragment = new CategoriesMain();
                break;
            case 5:
                fragment = new Logout();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_dashboard);
                break;
            case 1:
                mTitle = getString(R.string.title_incomes);
                break;
            case 2:
                mTitle = getString(R.string.title_losses);
                break;
            case 3:
                mTitle = getString(R.string.title_vendors);
                break;
            case 4:
                mTitle = getString(R.string.title_categories);
                break;
            case 5:
                mTitle = getString(R.string.title_logout);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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

                changeActivity(LossNew.class);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Methode zum synchroniseren der Applikation, bei Fehlerfall, evtl für später bei mehrern Frontends
      *
     */
    @Author("Mark")
    private void synchronizeApplication() {
        Toast.makeText(MainActivity.this, "Synchronisierung des Systems", Toast.LENGTH_SHORT).show();
    }

    /*
     * Methode zum ändern der Activity je nach eingegebener Klasse
     */
    @Author("Mark")
    private void changeActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
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

    public void toast(View view) {
        Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
    }


}
