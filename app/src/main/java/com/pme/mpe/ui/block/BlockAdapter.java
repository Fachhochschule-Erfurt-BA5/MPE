package com.pme.mpe.ui.block;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.ArrayList;
import java.util.List;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<CategoryBlock> BlockList;
    private List<Task> taskList;
    private TaskViewModel taskViewModel;
    private TasksPackageRepository tasksPackageRepository;

    public BlockAdapter(List<CategoryBlock> blockList, List<Task> taskList, TaskViewModel taskViewModel) {
        this.BlockList = blockList;
        this.taskList = taskList;
        this.taskViewModel = taskViewModel;
    }

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_block_item, viewGroup, false);

        return new BlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder blockViewHolder, int i) {
        CategoryBlock block = BlockList.get(i);
        blockViewHolder.blockTitle.setText(block.getTitle());
        LinearLayoutManager layoutManager = new LinearLayoutManager(blockViewHolder.rvTaskItem.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setInitialPrefetchItemCount(assignTasks(block,taskList).size());
        TaskAdapter taskAdapter = new TaskAdapter(assignTasks(block,taskList), tasksPackageRepository, taskViewModel);

        blockViewHolder.rvTaskItem.setLayoutManager(layoutManager);
        blockViewHolder.rvTaskItem.setAdapter(taskAdapter);
        blockViewHolder.rvTaskItem.setRecycledViewPool(viewPool);

        blockViewHolder.blockCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                blockViewHolder.blockCard.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                blockViewHolder.blockCard.requestLayout();
                        return true;
            }
        });

        blockViewHolder.blockCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockViewHolder.blockCard.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 185, v.getResources().getDisplayMetrics());
                blockViewHolder.blockCard.requestLayout();
            }
        });
    }

    @Override
    public int getItemCount() {
        return BlockList.size();
    }

    static class BlockViewHolder extends RecyclerView.ViewHolder {
        private TextView blockTitle;
        private RecyclerView rvTaskItem;
        private LinearLayout blockItem;
        private TextView timeTask;
        private MaterialCardView blockCard;

        BlockViewHolder(View itemView) {
            super(itemView);
            blockTitle = itemView.findViewById(R.id.block_title_item);
            rvTaskItem = itemView.findViewById(R.id.rv_task_item);
            blockItem = itemView.findViewById(R.id.block_item_layout);
            timeTask = itemView.findViewById(R.id.block_time_item);
            blockCard = itemView.findViewById(R.id.block_card);
        }
    }
    public List<Task> assignTasks(CategoryBlock block, List<Task> taskList){
        List<Task> selectedTasks = new ArrayList<>();
        for(int i = 0; i<= taskList.size()-1; i++){
            if (taskList.get(i).getT_categoryBlockId()==block.getCatBlockId()){
                selectedTasks.add(taskList.get(i));
            }

        }
        return selectedTasks;
    }
}