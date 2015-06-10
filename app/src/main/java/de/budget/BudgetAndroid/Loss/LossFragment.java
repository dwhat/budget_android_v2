package de.budget.BudgetAndroid.Loss;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import de.budget.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LossFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LossFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LossFragment extends Fragment {
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
     * @return A new instance of fragment LossFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LossFragment newInstance(String param1, String param2) {
        LossFragment fragment = new LossFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LossFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_loss, container, false);

        // Author Mark
        // Createing Mock Objects
        // TODO: Holen der Vendors vom Server
        ArrayList <HashMap<String, String>> array = new ArrayList ();

        HashMap<String, String> firstItem = new HashMap();
        firstItem.put(Loss.NAME,"Erstes Item");
        firstItem.put(Loss.DATE, "30.06.1992");
        firstItem.put(Loss.NOTICE, "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam");
        firstItem.put(Loss.TOTAL, "1.00€");

        array.add(firstItem);

        // Starten des Array Adapters
        LossArrayAdapter ArrayAdapter = new LossArrayAdapter (getActivity(), R.layout.loss_listview, array);
        // Listview ermitteln
        listView = (ListView)rootView.findViewById(R.id.listView_loss);

        // ListView setzten mit entsprehcenden Objekten aus dem Adapter
        listView.setAdapter(ArrayAdapter);

        // OnClick Listener für Interaktion
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                // Neuen Intent erzeugen der beim Klick auf die Vendor Show verweißt
                Intent intent = new Intent(getActivity(), LossActivity.class);

                // Bundle anlegen
                Bundle bundle = new Bundle ();
                int itemPosition        = position;
                HashMap  item           = (HashMap) listView.getItemAtPosition(position);

                bundle.putString(Loss.NAME,     (String) item.get(Loss.NAME));
                bundle.putString(Loss.DATE,     (String) item.get(Loss.DATE));
                bundle.putString(Loss.NOTICE,   (String) item.get(Loss.NOTICE));
                bundle.putString(Loss.TOTAL,    (String) item.get(Loss.TOTAL));

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLossMainFragmentInteraction(uri);
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
        public void onLossMainFragmentInteraction(Uri uri);
    }

}
