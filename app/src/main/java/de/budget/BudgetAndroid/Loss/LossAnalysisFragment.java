package de.budget.BudgetAndroid.Loss;

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

import de.budget.BudgetAndroid.AsyncTasks.GetIncomeAmountForCategoriesTask;
import de.budget.BudgetAndroid.AsyncTasks.GetLossAmountForCategoriesTask;
import de.budget.BudgetAndroid.AsyncTasks.OnTaskCompleted;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Dashboard.ChartMethods;
import de.budget.BudgetAndroid.common.NetworkCommon;
import de.budget.BudgetAndroid.common.ToastCommon;
import de.budget.BudgetService.dto.AmountTO;
import de.budget.R;

/**
 * Created by mark on 20/06/15.
 */
public class LossAnalysisFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private HorizontalBarChart chart;
    private BudgetAndroidApplication myApp;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoryFragment.
     */
    public static LossAnalysisFragment newInstance() {
        LossAnalysisFragment fragment = new LossAnalysisFragment();
        return fragment;
    }

    public LossAnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_loss_analysis, container, false);
        final RelativeLayout loadingPanel = (RelativeLayout) rootView.findViewById(R.id.loadingPanel);
        myApp = (BudgetAndroidApplication) getActivity().getApplication();

        // Draw Chart
        chart = (HorizontalBarChart) rootView.findViewById(R.id.chart_loss);
        ChartMethods.setHorizontalBarChartFundamentals(chart);
        chart.animateY(2500);

        // Fetch Data for Charts
        if (NetworkCommon.getStatus(getActivity())) {
            loadingPanel.setVisibility(View.VISIBLE);
            GetLossAmountForCategoriesTask task = new GetLossAmountForCategoriesTask(getActivity().getBaseContext(), myApp, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Object o) {
                    boolean success = (boolean) o;
                    if (success) {
                        loadingPanel.setVisibility(View.GONE);
                        chart.setVisibility(View.VISIBLE);
                        refreshChart(chart);
                    } else {
                        loadingPanel.setVisibility(View.GONE);
                        CharSequence text = "Bitte zuerst Ausgaben anlegen";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getBaseContext(), text, duration);
                        toast.show();
                    }
                }
            });
            task.execute();
            loadingPanel.setVisibility(View.VISIBLE);

        } else {
            ToastCommon.NetworkMissing(getActivity());
        }

        return rootView;
    }

    public void refreshChart(HorizontalBarChart chart) {
        List<AmountTO> loss = myApp.getLossCategoriesAmount();
        ChartMethods.setDataOfHorizontalBarChart(chart, loss, "Ausgaben pro Kategorie in €");

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLossAnalysisFragmentInteraction(uri);
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
        public void onLossAnalysisFragmentInteraction(Uri uri);
    }
}