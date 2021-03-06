package de.budget.BudgetAndroid.Vendors;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.budget.R;


/**
 * <p>
 * <p> Ein Oberfragment des Vendors, initialisiert die Tabs und startet entsprehcende Subfragments</p>
 * </p>
 * @Author Christopher
 * @date 17.06.2015
 */
public class VendorFragment extends Fragment{
    private FragmentTabHost mTabHost;

    private OnFragmentInteractionListener mListener;

    /**
     * @Author Christopher
     * @date 17.06.2015
     */
    public static VendorFragment newInstance() {
        VendorFragment fragment = new VendorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public VendorFragment() {
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
        //return inflater.inflate(R.layout.fragment_dashboard, container, false);

        View rootView = inflater.inflate(R.layout.fragment_vendor,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("Übersicht").setIndicator("Übersicht"),
                VendorListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Analyse").setIndicator("Analyse"),
                VendorAnalysisFragment.class, null);

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onVendorFragmentInteraction(uri);
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
        public void onVendorFragmentInteraction(Uri uri);
    }


    public void clickFAB (View v ){

    }
}
