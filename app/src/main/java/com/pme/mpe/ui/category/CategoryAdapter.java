package com.pme.mpe.ui.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pme.mpe.model.tasks.Category;

public class CategoryAdapter extends BaseAdapter {

    private final Context mContext;
    private final Category[] categories;

    public CategoryAdapter(Context mContext, Category[] categories) {
        this.mContext = mContext;
        this.categories = categories;
    }


    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
