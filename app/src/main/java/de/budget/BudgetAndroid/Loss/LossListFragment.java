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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.budget.BudgetAndroid.AsyncTasks.GetItemsTask;
import de.budget.BudgetAndroid.AsyncTasks.OnTaskCompleted;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.R;

/**
 * Created by mark on 20/06/15.
 */
public class LossListFragment extends Fragment implements OnTaskCompleted {


    private LossArrayAdapter arrayAdapter;

    private OnFragmentInteractionListener mListener;

    private List<BasketTO> baskets;

    private ListView listView;
    private RelativeLayout loadingPanel;

    private BudgetAndroidApplication myApp;

    private Intent intent;  // From onCreateView to access in onTaskCompleted

    public static LossListFragment newInstance() {
        LossListFragment fragment = new LossListFragment();
        return fragment;
    }

    public LossListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_loss_list, container, false);

        myApp = (BudgetAndroidApplication) getActivity().getApplication();
        baskets = myApp.getBasket();

        arrayAdapter = new LossArrayAdapter (getActivity(), R.layout.listview_loss, baskets);

        loadingPanel = (RelativeLayout) rootView.findViewById(R.id.loadingPanel);
        listView = (ListView) rootView.findViewById(R.id.listView_loss);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                // Neuen Intent erzeugen der beim Klick auf die Vendor Show verweißt
                intent = new Intent(getActivity(), LossActivity.class);

                // Bundle anlegen
                Bundle bundle = new Bundle ();
                bundle.putInt("POSITION", position);
                intent.putExtras(bundle);

                BasketTO basket = (BasketTO) listView.getItemAtPosition(position);

                if (basket.getItems() == null) {

                    loadingPanel.setVisibility(View.VISIBLE);

                    basket.setOccupied(true);
                    GetItemsTask itemsTask = new GetItemsTask(myApp.getApplicationContext(), myApp, basket, new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(Object o) {
                            boolean success = (boolean) o;
                            if (success) {
                                loadingPanel.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                startActivity(intent);
                            } else {
                                loadingPanel.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                Toast.makeText(myApp, "Fehler! Items konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                    itemsTask.execute();

                } else {
                    startActivity(intent);
                }



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

    @Override
    public void onTaskCompleted(Object o) {
        boolean success = (boolean) o;
        if (success) {
            loadingPanel.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            startActivity(intent);
        } else {
            loadingPanel.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            Toast.makeText(myApp, "Fehler! Items konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
        }
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

    private ArrayList<HashMap<String, String>> mock(){
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

        return array;
    }

}
