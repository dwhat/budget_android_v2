package de.budget.BudgetAndroid.Categories;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.ChartMethods;

import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import de.budget.BudgetService.dto.CategoryTO;
import de.budget.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryAnalysisFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryAnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryAnalysisFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BudgetAndroidApplication myApp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoryFragment.
     */
    public static CategoryAnalysisFragment newInstance() {
        CategoryAnalysisFragment fragment = new CategoryAnalysisFragment();
        return fragment;
    }

    public CategoryAnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (BudgetAndroidApplication) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_category_analysis, container, false);
        List<CategoryTO> categories = myApp.getCategories();
        int incomeCount =0, lossCount = 0;
        for (int i=0; i< categories.size(); i++){
            if(categories.get(i).isIncome()){
                incomeCount++;
            }
            else {
                lossCount++;
            }
        }

        PieChart chart = (PieChart) rootView.findViewById(R.id.chart_category);
        chart = ChartMethods.setPieChartFundamentals(chart, String.valueOf(categories.size()) + "\n Kategorien");
        chart.setUsePercentValues(true);
        chart = ChartMethods.setDataPercent(chart, "Einnahmen", "Ausgaben", incomeCount, lossCount);
        chart.animateXY(1500, 1500);

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCategoryAnalysisFragmentInteraction(uri);
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
        public void onCategoryAnalysisFragmentInteraction(Uri uri);
    }

}
