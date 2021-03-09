package com.pme.mpe.ui.block;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
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
import com.pme.mpe.activities.BlockCategoryActivity.EditBlockCategoryActivity;
import com.pme.mpe.activities.CategoryActivity.EditCategoryActivity;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivity;
import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.List;

public class BlockGridAdapter extends BaseAdapter {

    private final Context mContext;
    private List<CategoryBlock> blocks;
    private final TasksPackageRepository tasksPackageRepository;


    public BlockGridAdapter(Context mContext, List<CategoryBlock> blocks, TasksPackageRepository tasksPackageRepository, CategoryWithCatBlocksAndTasksRelation categoryWithCatBlocksAndTasksRelation) {
        this.mContext = mContext;
        this.blocks = blocks;
        this.tasksPackageRepository = tasksPackageRepository;
    }


    public int getItemCount() {
        if (this.blocks != null && !this.blocks.isEmpty())
            return this.blocks.size();
        else
            return 0;
    }

    @Override
    public int getCount() {
        if (this.blocks != null && !this.blocks.isEmpty())
            return this.blocks.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return this.blocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.blocks.get(position).getCatBlockId();
    }

    static class ViewHolder {
        AppCompatTextView blockName;
        MaterialCardView blockElement;
        AppCompatTextView tasksNumber;
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
            convertView = inflater.inflate(R.layout.item_block_category_grid_element, parent, false);

            viewHolder.blockName = (AppCompatTextView) convertView.findViewById(R.id.block_grid_name);
            viewHolder.blockElement = (MaterialCardView) convertView.findViewById(R.id.block_grid_card);
            viewHolder.tasksNumber = (AppCompatTextView) convertView.findViewById(R.id.block_grid_tasks);
            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.delete_btn);
            viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.edit_btn);
            viewHolder.contentLayout = (LinearLayoutCompat) convertView.findViewById(R.id.content_layout);
            viewHolder.updateLayout = (LinearLayout) convertView.findViewById(R.id.update_layout);

            viewHolder.blockElement.setOnLongClickListener(new View.OnLongClickListener() {
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
                    tasksPackageRepository.deleteCategoryBlock(blocks.get(position));
                }
            });

            viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editBlockIntent = new Intent(mContext.getApplicationContext(), EditBlockCategoryActivity.class);
                    editBlockIntent.putExtra("blockID",(int) blocks.get(position).getCatBlockId());
                    editBlockIntent.putExtra("blockName",blocks.get(position).getTitle());
                    mContext.startActivity(editBlockIntent);
                }
            });

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.blockName.setText(blocks.get(position).getTitle().toUpperCase());
        viewHolder.tasksNumber.setText(blocks.get(position).getAssignedTasks().size() + " Blocks");
        return convertView;
    }

    public void setBlocks(List<CategoryBlock> blocks) {
        this.blocks = blocks;
        notifyDataSetChanged();
    }

}
