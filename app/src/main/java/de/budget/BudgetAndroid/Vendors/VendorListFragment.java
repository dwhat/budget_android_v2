package de.budget.BudgetAndroid.Vendors;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.VendorTO;
import de.budget.R;


/**
 * @Author Christopher
 * @date 17.06.2015
 */
public class VendorListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
     * @return A new instance of fragment VendorsFragment.
     */
    public static VendorListFragment newInstance(String param1, String param2) {
        VendorListFragment fragment = new VendorListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public VendorListFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_vendor_list, container, false);

        // @Author Christopher
        // 09.06.2015
        // Abholen der Categorien
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getActivity().getApplication();
        List<VendorTO> result = myApp.getVendors();
        String[] vendors = new String[result.size()];
        Log.d("INFO", "Size of Categoryarray: " + result.size());
        for (int i=0; i< result.size(); i++){
            vendors[i] = result.get(i).getName();
        }

        // Starten des Array Adapters
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, vendors);

        // Listview ermitteln
        listView = (ListView)rootView.findViewById(R.id.listView_vendor);

        // ListView setzten mit entsprehcenden Objekten aus dem Adapter
        listView.setAdapter(ArrayAdapter);

        // OnClick Listener für Interaktion
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                // Neuen Intent erzeugen der beim Klick auf die Vendor Show verweißt
                Intent intent = new Intent(getActivity(), VendorActivity.class);

                // Bundle anlegen
                Bundle bundle = new Bundle ();
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Setzte im Bundle das Objekt
                bundle.putInt("VENDOR_POSITION", position);

                // Übergebe das Objekt an den Intent
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onVendorsMainFragmentInteraction(uri);
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
        public void onVendorsMainFragmentInteraction(Uri uri);
    }

}
