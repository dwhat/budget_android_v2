package de.budget.BudgetAndroid.Categories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.BudgetService.Response.CategoryResponse;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
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
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryFragment() {
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
        View rootView =  inflater.inflate(R.layout.fragment_category, container, false);

        // @Author Christopher
        // 09.06.2015
        // Abholen der Categorien
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getActivity().getApplication();
        List<CategoryTO> test = myApp.getCategories();
        String[] categories = new String[test.size()];
        for (int i=0; i< test.size(); i++){
            categories[i] = test.get(i).getName();
        }

        //String[] categories = new String[] { "Haushalt","Sport","Sonstiges", "Luxus"};

        // Starten des Array Adapters
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);

        // Listview ermitteln
        listView = (ListView)rootView.findViewById(R.id.listView_category);

        // ListView setzten mit entsprehcenden Objekten aus dem Adapter
        listView.setAdapter(ArrayAdapter);

        // OnClick Listener für Interaktion
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                // Neuen Intent erzeugen der beim Klick auf die Vendor Show verweißt
                Intent intent = new Intent(getActivity(), CategoryActivity.class);

                // Bundle anlegen
                Bundle bundle = new Bundle ();
                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Setzte im Bundle das Objekt
                bundle.putString("CATEGORY_NAME", itemValue);

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
            mListener.onCategoriesMainFragmentInteraction(uri);
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
        public void onCategoriesMainFragmentInteraction(Uri uri);
    }

}
