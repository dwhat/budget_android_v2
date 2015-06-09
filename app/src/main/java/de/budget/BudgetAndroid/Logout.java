package de.budget.BudgetAndroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import de.budget.BudgetService.Response.ReturnCodeResponse;

import de.budget.R;


/**
 * @author christopher
 * @date 04.06.2015
 */
public class Logout extends Fragment {
    private Context context;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Logout.
     */
    public static Logout newInstance() {
        Logout fragment = new Logout();
        Bundle args = new Bundle();
        return fragment;
    }

    public Logout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogoutTask logoutTask = new LogoutTask(context);
        //Proxy asynchron aufrufen
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getActivity().getApplication();
        int sessionId = myApp.getSession();
        logoutTask.execute(sessionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLogoutFragmentInteraction(uri);
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
        public void onLogoutFragmentInteraction(Uri uri);
    }

    private class LogoutTask extends AsyncTask<Integer, Integer, ReturnCodeResponse>
    {
        private Context context;

        //Dem Konstruktor der Klasse wird der aktuelle Kontext der Activity übergeben
        //damit auf die UI-Elemente zugegriffen werden kann und Intents gestartet werden können, usw.
        public LogoutTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected ReturnCodeResponse doInBackground(Integer... params){
            if(params.length != 1)
                return null;
            int sessionId = params[0];
            BudgetAndroidApplication myApp = (BudgetAndroidApplication) getActivity().getApplication();
            try {
                ReturnCodeResponse myUser = myApp.getBudgetOnlineService().logout(sessionId);
                Integer rt = myUser.getReturnCode();
                Log.d("INFO", rt.toString());
                return myUser;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {
            //wird in diesem Beispiel nicht verwendet
        }

        protected void onPostExecute(ReturnCodeResponse result)
        {
            int duration = Toast.LENGTH_SHORT;
            if(result != null)
            {
                //erfolgreich eingeloggt
                if (result.getReturnCode() == 200){

                    BudgetAndroidApplication myApp = (BudgetAndroidApplication) getActivity().getApplication();
                    myApp.setSession(0);
                    Intent intent = new Intent(getActivity().getBaseContext(), Login.class);
                    startActivity(intent);
                }
            }
            else
            {

            }
        }
    }

}
