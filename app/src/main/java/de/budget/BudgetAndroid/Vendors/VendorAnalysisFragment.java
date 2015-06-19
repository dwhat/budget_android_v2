package de.budget.BudgetAndroid.Vendors;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import de.budget.BudgetAndroid.AsyncTasks.GetBasketsAmountForVendorsTask;
import de.budget.BudgetAndroid.AsyncTasks.OnTaskCompleted;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.ChartMethods;

import java.util.ArrayList;
import java.util.List;

import de.budget.BudgetService.dto.AmountTO;
import de.budget.BudgetService.dto.VendorTO;
import de.budget.R;


/**
 * @Author Christopher
 * @date 17.06.2015
 */
public class VendorAnalysisFragment extends Fragment implements OnTaskCompleted {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private BudgetAndroidApplication myApp;
    private HorizontalBarChart chart;

    private ListView listView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoryFragment.
     */
    public static VendorAnalysisFragment newInstance() {
        VendorAnalysisFragment fragment = new VendorAnalysisFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public VendorAnalysisFragment() {
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
        View rootView =  inflater.inflate(R.layout.fragment_vendor_analysis, container, false);
        final RelativeLayout loadingPanel = (RelativeLayout) rootView.findViewById(R.id.loadingPanel);

        myApp = (BudgetAndroidApplication) getActivity().getApplication();

        // Draw Chart

        chart = (HorizontalBarChart) rootView.findViewById(R.id.chart_vendor);
        ChartMethods.setHorizontalBarChartFundamentals(chart);
        chart.animateY(2500);

        // Fetch Data for Charts
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            loadingPanel.setVisibility(View.VISIBLE);
            GetBasketsAmountForVendorsTask task = new GetBasketsAmountForVendorsTask(getActivity().getBaseContext(), myApp, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Object o) {
                    boolean success = (boolean) o;
                    if(success == true){
                        loadingPanel.setVisibility(View.GONE);
                        chart.setVisibility(View.VISIBLE);
                        refreshChart(chart);
                    }
                    else{
                        loadingPanel.setVisibility(View.GONE);
                        CharSequence text = "Bitte zuerst Ausgabe anlegen";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getBaseContext(), text, duration);
                        toast.show();
                    }
                }
            });
            task.execute();
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
        List<AmountTO> vendors = myApp.getVendorsAmount();
        ChartMethods.setDataOfHorizontalBarChart(chart, vendors, "Händler in €");

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onVendorAnalysisFragmentInteraction(uri);
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
        public void onVendorAnalysisFragmentInteraction(Uri uri);
    }

    @Override
    public void onTaskCompleted(Object o) {
    }

}
