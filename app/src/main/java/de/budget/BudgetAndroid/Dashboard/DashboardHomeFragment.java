package de.budget.BudgetAndroid.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.ChartMethods;
import de.budget.BudgetService.Response.AmountResponse;
import de.budget.R;

/**
 * @Author Christopher
 * @date 18.06.2015
 */
public class DashboardHomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    static private BudgetAndroidApplication myApp;
    static private BigDecimal income, loss, delta;
    static private PieChart chart;
    static private boolean incomeFinished = false, lossFinished = false;
    static private GetIncomeByPeriodTask taskIncome;
    static private GetLossByPeriodTask taskLoss;

    public DashboardHomeFragment() {
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
        final View rootView =  inflater.inflate(R.layout.fragment_dashboard_home, container, false);
        myApp = (BudgetAndroidApplication) getActivity().getApplication();

        chart = (PieChart) rootView.findViewById(R.id.chart_home);
        final TextView text_period = (TextView) rootView.findViewById(R.id.text_period);

        SeekBar period = (SeekBar) rootView.findViewById(R.id.bar_period);

        refreshChart(true);

        period.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 30;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                text_period.setText("Einnahmen/Ausgaben der letzten " + progressChanged + " Tage");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()){
                    taskIncome = new GetIncomeByPeriodTask();
                    taskIncome.execute(String.valueOf(String.valueOf(progressChanged)));
                    taskLoss = new GetLossByPeriodTask();
                    taskLoss.execute(String.valueOf(String.valueOf(progressChanged)));
                }
                else {
                    CharSequence text = "Keine Netzwerkverbindung! :(";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity().getBaseContext(), text, duration);
                    toast.show();
                }
            }
        });

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

    public static void refreshChart(boolean startUp){
        if((incomeFinished && lossFinished) || startUp) {
            income = new BigDecimal(myApp.getIncomeLastPeriod());
            loss = new BigDecimal(myApp.getLossLastPeriod());
            delta = income.subtract(loss);
            DecimalFormat df = new DecimalFormat(".00");
            chart = ChartMethods.setChartFundamentals(chart, "Differenz \n" + df.format(delta) + "€");
            chart = ChartMethods.setDataNormal(chart, "Einnahmen", "Ausgaben", income.round(new MathContext(3, RoundingMode.HALF_UP)).intValueExact(), loss.round(new MathContext(3, RoundingMode.HALF_UP)).intValueExact());
            chart.animateXY(1500, 1500);
            lossFinished = false;
            incomeFinished = false;
        }

    }


    public class GetIncomeByPeriodTask extends AsyncTask<String, Integer, AmountResponse>
    {

        public GetIncomeByPeriodTask()
        {

        }


        @Override
        protected AmountResponse doInBackground(String... params){
            if(params.length != 1)
                return null;
            Integer period = Integer.parseInt(params[0]);

            try {
                AmountResponse response = myApp.getBudgetOnlineService().getIncomeByPeriod(myApp.getSession(), period);
                Integer rt =  response.getReturnCode();
                //Log.d("INFO", "Response" + rt.toString());
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {
            //wird in diesem Beispiel nicht verwendet
        }

        @Override
        protected void onPostExecute(AmountResponse result)
        {
            if(result != null) {
                if (result.getReturnCode() == 200) {
                    myApp.setIncomeLastPeriod(result.getValue());
                    incomeFinished = true;
                    refreshChart(false);
                    Log.d("INFO", "Einnahme für Periode erfolgreich angelegt.");
                }
            }
            else
            {
                Log.d("INFO", "Einnahmen konnten nicht geladen werden.");

            }
        }
    }

    public class GetLossByPeriodTask extends AsyncTask<String, Integer, AmountResponse>
    {

        public GetLossByPeriodTask()
        {

        }


        @Override
        protected AmountResponse doInBackground(String... params){
            if(params.length != 1)
                return null;
            Integer period = Integer.parseInt(params[0]);

            try {
                AmountResponse response = myApp.getBudgetOnlineService().getLossByPeriod(myApp.getSession(), period);
                Integer rt =  response.getReturnCode();
                //Log.d("INFO", "Response" + rt.toString());
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {
            //wird in diesem Beispiel nicht verwendet
        }

        @Override
        protected void onPostExecute(AmountResponse result)
        {
            if(result != null)
            {
                if (result.getReturnCode() == 200){
                    myApp.setLossLastPeriod(result.getValue());
                    lossFinished = true;
                    refreshChart(false);
                    Log.d("INFO", "Ausgaben für Periode erfolgreich angelegt.");
                }
            }
            else
            {
                Log.d("INFO", "Ausgaben konnten nicht geladen werden.");

            }
        }
    }


}
