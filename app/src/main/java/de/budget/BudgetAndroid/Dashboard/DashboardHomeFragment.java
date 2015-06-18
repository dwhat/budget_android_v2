package de.budget.BudgetAndroid.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import de.budget.BudgetAndroid.AsyncTasks.GetIncomeByPeriodTask;
import de.budget.BudgetAndroid.AsyncTasks.LoginTask;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.ChartMethods;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.R;

/**
 * @Author Christopher
 * @date 18.06.2015
 */
public class DashboardHomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BudgetAndroidApplication myApp;

    public DashboardHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getActivity().getApplication();
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            GetIncomeByPeriodTask task = new GetIncomeByPeriodTask(myApp);
            task.execute(String.valueOf(myApp.getSession()), String.valueOf(30));
        }
        else {
            CharSequence text = "Keine Netzwerkverbindung! :(";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getActivity().getBaseContext(), text, duration);
            toast.show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_dashboard_home, container, false);
        PieChart chart = (PieChart) rootView.findViewById(R.id.chart_home);

        double income = 0.0;
        double loss = 0.0;

        Double delta = income - loss;
        chart = ChartMethods.configureChart(chart, String.valueOf(delta));
        chart = ChartMethods.setData(chart, "Einnahmen", "Ausgaben", (int) Math.round(income), (int) Math.round(loss));
        chart.animateXY(1500, 1500);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDashboardHomeFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onDashboardHomeFragmentInteraction(Uri uri);
    }

}
