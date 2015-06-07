package de.budget.BudgetAndroid.Loss;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import de.budget.R;

/**
 * Created by mark on 06/06/15.
 */
public class LossDialog extends DialogFragment {

    private EditText mEditText;

    public LossDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_loss_dialog, container);
        mEditText = (EditText) view.findViewById(R.id.loss_name);
        getDialog().setTitle(getString(R.string.title_activity_loss));

        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        return view;
    }

}
