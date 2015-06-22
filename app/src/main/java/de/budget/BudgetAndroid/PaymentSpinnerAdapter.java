package de.budget.BudgetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.budget.BudgetService.dto.PaymentTO;
import de.budget.R;

/**
 * <p>
 *     Eine Erweiterung des BaseAdapters zur Darstellung von Payment Objecten innerhalb eines Spinners
 *
 * </p>
 * Created by mark on 17/06/15.
 *@Author Mark
 */
public class PaymentSpinnerAdapter extends BaseAdapter {

    private final Context context;
    private List<PaymentTO> values;

    public PaymentSpinnerAdapter(Context context, int resource, List<PaymentTO> values) {
        this.context    = context;
        this.values     = values;
    }

    @Override
    public int getCount() {
        return this.values.size();
    }

    @Override
    public PaymentTO getItem(int position) {
        return this.values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.values.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =  inflater.inflate(R.layout.spinner_payment, parent, false);

        TextView paymentName = (TextView) convertView.findViewById(R.id.payment_name);
        paymentName.setText(getItem(position).getName());

        return convertView;
    }
}
