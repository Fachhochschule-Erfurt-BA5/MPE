package com.pme.mpe.ui.block;

import android.graphics.Color;
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

    public BlockAdapter(List<CategoryBlock> blockList, List<Task> taskList, TaskViewModel taskViewModel, TasksPackageRepository tasksPackageRepository) {
        this.BlockList = blockList;
        this.taskList = taskList;
        this.taskViewModel = taskViewModel;
        this.tasksPackageRepository = tasksPackageRepository;
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
        blockViewHolder.timeBlock.setText(block.getStartTimeHour() + "h - " + block.getEndTimeHour() + "h");
        blockViewHolder.blockTitle.setTextColor(Color.parseColor(taskViewModel.getCategoryWithID(block.CB_CategoryId).getLetterColor()));
        blockViewHolder.timeBlock.setTextColor(Color.parseColor(taskViewModel.getCategoryWithID(block.CB_CategoryId).getLetterColor()));
        blockViewHolder.blockTasks.setTextColor(Color.parseColor(taskViewModel.getCategoryWithID(block.CB_CategoryId).getLetterColor()));
        blockViewHolder.blockNoTask.setTextColor(Color.parseColor(taskViewModel.getCategoryWithID(block.CB_CategoryId).getLetterColor()));
        blockViewHolder.blockCard.setCardBackgroundColor(Color.parseColor(taskViewModel.getCategoryWithID(block.CB_CategoryId).getColor()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(blockViewHolder.rvTaskItem.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setInitialPrefetchItemCount(assignTasks(block, taskList).size());
        blockViewHolder.blockTasks.setText(assignTasks(block, taskList).size()+" Tasks");
        TaskAdapter taskAdapter = new TaskAdapter(assignTasks(block, taskList), tasksPackageRepository, taskViewModel);
        if (assignTasks(block, taskList).size() == 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, blockViewHolder.itemView.getResources().getDisplayMetrics()));
            layoutParams.setMargins(0, 30, 0, 0);

            blockViewHolder.blockNoTask.setLayoutParams(layoutParams);
            blockViewHolder.blockNoTask.requestLayout();
        }

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
                blockViewHolder.blockCard.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 127, v.getResources().getDisplayMetrics());
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
        private TextView timeBlock;
        private TextView blockNoTask;
        private MaterialCardView blockCard;
        private TextView blockTasks;

        BlockViewHolder(View itemView) {
            super(itemView);
            blockTitle = itemView.findViewById(R.id.block_title_item);
            rvTaskItem = itemView.findViewById(R.id.rv_task_item);
            blockItem = itemView.findViewById(R.id.block_item_layout);
            timeBlock = itemView.findViewById(R.id.block_time_item);
            blockNoTask = itemView.findViewById(R.id.block_time_no_tasks);
            blockCard = itemView.findViewById(R.id.block_card);
            blockTasks = itemView.findViewById(R.id.block_tasks_item);
        }
    }

    public List<Task> assignTasks(CategoryBlock block, List<Task> taskList) {
        List<Task> selectedTasks = new ArrayList<>();
        for (int i = 0; i <= taskList.size() - 1; i++) {
            if (taskList.get(i).getT_categoryBlockId() == block.getCatBlockId()) {
                selectedTasks.add(taskList.get(i));
            }

        }
        return selectedTasks;
    }
}