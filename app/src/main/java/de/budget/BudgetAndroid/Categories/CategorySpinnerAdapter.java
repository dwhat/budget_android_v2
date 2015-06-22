package de.budget.BudgetAndroid.Categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.R;

/**
 * <p>
 *     Eine Erweiterung des BaseAdapters zur Darstellung von Category Objecten innerhalb eines Spinners
 *
 * </p>
 * Created by mark on 17/06/15.
 *@Author Mark
 */
@Author(name="Mark")
public class CategorySpinnerAdapter extends BaseAdapter {

    private final Context context;
    private List<CategoryTO> values;

    public CategorySpinnerAdapter(Context context, int resource, List<CategoryTO> values) {

        this.context    = context;
        this.values     = values;
    }

    @Override
    public int getCount() {
        return this.values.size();
    }

    @Override
    public CategoryTO getItem(int position) {
        return this.values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.values.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =  inflater.inflate(R.layout.spinner_category, parent, false);

        TextView categoryName       = (TextView) convertView.findViewById(R.id.category_name);
        TextView categoryNotice     = (TextView) convertView.findViewById(R.id.category_notice);

        categoryName        .setText(getItem(position).getName());
        categoryNotice      .setText(getItem(position).getNotice());
        return convertView;
    }
}
