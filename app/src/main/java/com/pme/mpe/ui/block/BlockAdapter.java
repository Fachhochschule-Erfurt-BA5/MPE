package com.pme.mpe.ui.block;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pme.mpe.R;
import com.pme.mpe.activities.TaskActivity.NewTaskActivityViewModel;
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

    public BlockAdapter(List<CategoryBlock> blockList,List<Task> taskList) {
        this.BlockList = blockList;
        this.taskList = taskList;
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
        TaskAdapter taskAdapter = new TaskAdapter(assignTasks(block,taskList));

        blockViewHolder.rvTaskItem.setLayoutManager(layoutManager);
        blockViewHolder.rvTaskItem.setAdapter(taskAdapter);
        blockViewHolder.rvTaskItem.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return BlockList.size();
    }

    static class BlockViewHolder extends RecyclerView.ViewHolder {
        private TextView blockTitle;
        private RecyclerView rvTaskItem;

        BlockViewHolder(View itemView) {
            super(itemView);
            blockTitle = itemView.findViewById(R.id.block_title_item);
            rvTaskItem = itemView.findViewById(R.id.rv_task_item);
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