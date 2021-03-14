package com.pme.mpe.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pme.mpe.ui.block.BlockAdapter;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvBlock = root.findViewById(R.id.recycler_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        BlockAdapter itemAdapter = new BlockAdapter(buildBlockList());
        rvBlock.setAdapter(itemAdapter);
        rvBlock.setLayoutManager(layoutManager);

        return root;
    }
    private List<CategoryBlock> buildBlockList() {
        List<CategoryBlock> BlockList = new ArrayList<>();
      //  for (int i=1; i<6; i++) {
        //    CategoryBlock block = new CategoryBlock("Block "+i, buildTaskList());
          //  BlockList.add(block);
        //}
        return BlockList;
    }

    private List<Task> buildTaskList() {
        List<Task> taskList = new ArrayList<>();
        for (int i=1; i<5; i++) {
            Task Task = new Task("Task "+i);
            taskList.add(Task);
        }
        return taskList;
    }
}