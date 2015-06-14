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

import java.util.List;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.BudgetService.dto.IncomeTO;
import de.budget.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IncomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeFragment newInstance(String param1, String param2) {
        IncomeFragment fragment = new IncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_income, container, false);


        // @Author Christopher
        // 14.06.2015
        // Abholen der Income
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getActivity().getApplication();
        List<IncomeTO> test = myApp.getIncome();
        String[] income = new String[test.size()];
        Log.d("INFO", "Size of Incomearray: " + test.size());
        for (int i=0; i< test.size(); i++){
            income[i] = test.get(i).getName();
        }
        // Starten des Array Adapters
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, income);

        // Listview ermitteln
        listView = (ListView)rootView.findViewById(R.id.listView_income);

        // ListView setzten mit entsprehcenden Objekten aus dem Adapter
        listView.setAdapter(ArrayAdapter);

        // OnClick Listener für Interaktion
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                // Neuen Intent erzeugen der beim Klick auf die Vendor Show verweißt
                Intent intent = new Intent(getActivity(), IncomeActivity.class);

                // Bundle anlegen
                Bundle bundle = new Bundle ();
                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Setzte im Bundle das Objekt
                bundle.putInt("INCOME_POSITION", position);

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
