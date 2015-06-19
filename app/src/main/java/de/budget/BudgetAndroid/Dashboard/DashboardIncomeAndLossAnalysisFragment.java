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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.util.List;

import de.budget.BudgetAndroid.AsyncTasks.GetBasketsAmountForVendorsTask;
import de.budget.BudgetAndroid.AsyncTasks.GetIncomeAmountForCategoriesTask;
import de.budget.BudgetAndroid.AsyncTasks.OnTaskCompleted;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.ChartMethods;
import de.budget.BudgetService.dto.AmountTO;
import de.budget.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardIncomeAndLossAnalysisFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardIncomeAndLossAnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardIncomeAndLossAnalysisFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private HorizontalBarChart chart;
    private BudgetAndroidApplication myApp;
    private boolean refreshIncome = false, refreshLoss = false;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoryFragment.
     */
    public static DashboardIncomeAndLossAnalysisFragment newInstance() {
        DashboardIncomeAndLossAnalysisFragment fragment = new DashboardIncomeAndLossAnalysisFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardIncomeAndLossAnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_income_analysis, container, false);
        final RelativeLayout loadingPanel = (RelativeLayout) rootView.findViewById(R.id.loadingPanel);
        myApp = (BudgetAndroidApplication) getActivity().getApplication();

        // Draw Chart

        chart = (HorizontalBarChart) rootView.findViewById(R.id.chart_income);
        ChartMethods.setHorizontalBarChartFundamentals(chart);
        chart.animateY(2500);

        // Fetch Data for Charts
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            loadingPanel.setVisibility(View.VISIBLE);
            GetIncomeAmountForCategoriesTask taskIncome = new GetIncomeAmountForCategoriesTask(getActivity().getBaseContext(), myApp, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Object o) {
                    if(o == true){
                        loadingPanel.setVisibility(View.GONE);
                        refreshIncome = true;
                        refreshChart(chart);
                    }
                    else{
                        loadingPanel.setVisibility(View.GONE);
                        CharSequence text = "Bitte zuerst Einnahmen anlegen";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getBaseContext(), text, duration);
                        toast.show();
                    }
                }
            });
            taskIncome.execute();
            GetBasketsAmountForVendorsTask taskLoss = new GetBasketsAmountForVendorsTask(getActivity().getBaseContext(), myApp, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Object o) {
                    if(o == true){
                        loadingPanel.setVisibility(View.GONE);
                        refreshLoss = true;
                        refreshChart(chart);
                    }
                    else{
                        loadingPanel.setVisibility(View.GONE);
                        CharSequence text = "Bitte zuerst Ausgaben anlegen";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getBaseContext(), text, duration);
                        toast.show();
                    }
                }
            });
            taskLoss.execute();

            loadingPanel.setVisibility(View.VISIBLE);

        }
        else {
            CharSequence text = "Keine Netzwerkverbindung! :(";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getActivity().getBaseContext(), text, duration);
            toast.show();
        }

        return rootView;
    }

    public void refreshChart(HorizontalBarChart chart){
        if(refreshIncome && refreshLoss) {
            chart.setVisibility(View.VISIBLE);
            List<AmountTO> income = myApp.getIncomeCategoriesAmount();
            List<AmountTO> loss = myApp.getItemsCategoriesAmount();
            ChartMethods.setDataOfHorizontalBarChart(chart, income, loss, "Einnahmen pro Kategorie in €", "Ausgaben pro Kategorie in €");
            refreshIncome = false;
            refreshLoss = false;
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDashboardIncomeAndLossAnalysisFragmentInteraction(uri);
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
        public void onDashboardIncomeAndLossAnalysisFragmentInteraction(Uri uri);
    }

}
