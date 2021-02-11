package com.pme.mpe.ui.category;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.card.MaterialCardView;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Category> categories;
    private final TasksPackageRepository tasksPackageRepository;


    public CategoryAdapter(Context mContext, List<Category> categories, TasksPackageRepository tasksPackageRepository) {
        this.mContext = mContext;
        this.categories = categories;
        this.tasksPackageRepository = tasksPackageRepository;
    }


    public int getItemCount() {
        if (this.categories != null && !this.categories.isEmpty())
            return this.categories.size();
        else
            return 0;
    }

    @Override
    public int getCount() {
        if (this.categories != null && !this.categories.isEmpty())
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
        ImageButton deleteBtn;
        ImageButton editBtn;
        LinearLayout updateLayout;
        LinearLayoutCompat contentLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_category_grid_element, parent, false);

            viewHolder.categoryName = (AppCompatTextView) convertView.findViewById(R.id.category_grid_name);
            viewHolder.categoryElement = (MaterialCardView) convertView.findViewById(R.id.category_grid_card);
            viewHolder.categoryBlockNumber = (AppCompatTextView) convertView.findViewById(R.id.category_grid_block);
            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.delete_btn);
            viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.edit_btn);
            viewHolder.contentLayout = (LinearLayoutCompat) convertView.findViewById(R.id.content_layout);
            viewHolder.updateLayout = (LinearLayout) convertView.findViewById(R.id.update_layout);

            viewHolder.categoryElement.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewHolder.updateLayout.setAlpha(1);
                    viewHolder.updateLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, parent.getContext().getResources().getDisplayMetrics());
                    viewHolder.updateLayout.requestLayout();
                    viewHolder.contentLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 115, parent.getContext().getResources().getDisplayMetrics());
                    viewHolder.contentLayout.requestLayout();
                    return true;
                }
            });


            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasksPackageRepository.deleteCategory(categories.get(position));
                }
            });

            viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categories.get(position).setCategoryName("testUpdate");
                    ////////////////TODO: The Update Method was changed on the Repository////////////////
                    //tasksPackageRepository.updateCategory(categories.get(position));
                }
            });

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.categoryName.setText(categories.get(position).getCategoryName().toUpperCase());
        viewHolder.categoryElement.setCardBackgroundColor(Color.parseColor(categories.get(position).getColor()));
        viewHolder.categoryBlockNumber.setText(categories.get(position).getCategoryBlockList().size() + " Blocks");
        return convertView;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

}
