package de.budget.BudgetAndroid.Income;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.IncomeTO;
import de.budget.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IncomeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IncomeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private List<IncomeTO> incomes;
    private IncomeArrayAdapter arrayAdapter;
    private BudgetAndroidApplication myApp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeListFragment newInstance(String param1, String param2) {
        IncomeListFragment fragment = new IncomeListFragment();
        return fragment;
    }

    public IncomeListFragment() {
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
        View rootView =  inflater.inflate(R.layout.fragment_income_list, container, false);

        myApp = (BudgetAndroidApplication) getActivity().getApplication();

        incomes = myApp.getIncome();

        arrayAdapter = new IncomeArrayAdapter(getActivity(), R.layout.listview_income, incomes);

        // Listview ermitteln
        listView = (ListView)rootView.findViewById(R.id.listView_income);

        // ListView setzten mit entsprehcenden Objekten aus dem Adapter
        listView.setAdapter(arrayAdapter);

        // OnClick Listener für Interaktion
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                // Neuen Intent erzeugen der beim Klick auf die Vendor Show verweißt
                Intent intent = new Intent(getActivity(), IncomeActivity.class);

                // Bundle anlegen
                Bundle bundle       = new Bundle ();
                int itemPosition    = position;

                // Setzte im Bundle das Objekt
                bundle.putInt("POSITION", position);

                // Übergebe das Objekt an den Intent
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onIncomeMainFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        public void onIncomeMainFragmentInteraction(Uri uri);
    }

}
