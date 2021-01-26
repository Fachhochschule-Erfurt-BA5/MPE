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

import java.util.HashMap;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Category> categories;

    public CategoryAdapter(Context mContext, List<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
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

    static class ViewHolder {
        AppCompatTextView categoryName;
        MaterialCardView categoryElement;
        AppCompatTextView categoryBlockNumber;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_category_grid_element,parent,false);

            viewHolder.categoryName = (AppCompatTextView) convertView.findViewById(R.id.category_grid_name);
            viewHolder.categoryElement = (MaterialCardView) convertView.findViewById(R.id.category_grid_card);
            viewHolder.categoryBlockNumber = (AppCompatTextView) convertView.findViewById(R.id.category_grid_block);


            convertView.setTag(viewHolder);



        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.categoryName.setText(categories.get(position).getCategoryName().toUpperCase());
        viewHolder.categoryElement.setCardBackgroundColor(Color.parseColor(categories.get(position).getColor()));
        viewHolder.categoryBlockNumber.setText(categories.get(position).getCategoryBlockList().size()+" Blocks");
        return convertView;
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }
}
