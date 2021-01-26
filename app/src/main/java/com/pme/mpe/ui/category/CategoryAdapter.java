package com.pme.mpe.ui.category;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.card.MaterialCardView;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Category> categories;
    LayoutInflater inflater;

    public CategoryAdapter(Context mContext, List<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
        inflater = (LayoutInflater.from(mContext));
    }


    public int getItemCount() {
        if( this.categories != null && !this.categories.isEmpty() )
            return this.categories.size();
        else
            return 0;
    }

    @Override
    public int getCount() {
        if( this.categories != null && !this.categories.isEmpty() )
            return this.categories.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return this.categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categories.get(position).getCategoryId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_category_grid_element,parent,false);
            AppCompatTextView categoryName = (AppCompatTextView) convertView.findViewById(R.id.category_grid_name);
            categoryName.setText(categories.get(position).getCategoryName().toUpperCase());
            MaterialCardView categoryElement = (MaterialCardView) convertView.findViewById(R.id.category_grid_card);
            categoryElement.setCardBackgroundColor(Color.parseColor(categories.get(position).getColor()));
        }
        return convertView;
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }
}
