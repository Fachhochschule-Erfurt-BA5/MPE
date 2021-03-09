package com.pme.mpe.ui.block;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pme.mpe.R;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.List;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<CategoryBlock> BlockList;

    public BlockAdapter(List<CategoryBlock> BlockList) {
        this.BlockList = BlockList;
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

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                blockViewHolder.rvTaskItem.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(block.getAssignedTasks().size());

        // Create sub item view adapter //// Appliocation application?
        //TaskAdapter taskAdapter = new TaskAdapter(block.getAssignedTasks(), TasksPackageRepository.getRepository(Application ));

        blockViewHolder.rvTaskItem.setLayoutManager(layoutManager);
        //blockViewHolder.rvTaskItem.setAdapter(taskAdapter);  warum ist taskAdapter nicht vom Typ adapter?
        blockViewHolder.rvTaskItem.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return BlockList.size();
    }

    class BlockViewHolder extends RecyclerView.ViewHolder {
        private TextView blockTitle;
        private RecyclerView rvTaskItem;

        BlockViewHolder(View itemView) {
            super(itemView);
            blockTitle = itemView.findViewById(R.id.block_title_item);
            rvTaskItem = itemView.findViewById(R.id.rv_task_item);
        }
    }
}