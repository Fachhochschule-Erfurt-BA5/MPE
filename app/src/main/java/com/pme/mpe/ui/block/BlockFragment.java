package com.pme.mpe.ui.block;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pme.mpe.R;

import java.util.List;

public class BlockFragment extends Fragment {

    private BlockViewModel blockViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blockViewModel = new ViewModelProvider(this).get(BlockViewModel.class);
        View root = inflater.inflate(R.layout.fragment_block, container, false);
        final TextView blockText = root.findViewById(R.id.block_name);
        final TextView blockText2 = root.findViewById(R.id.block_name2);
        final TextView blockText3 = root.findViewById(R.id.block_name3);
        final TextView blockText4 = root.findViewById(R.id.add_category);
        final TextView taskText = root.findViewById(R.id.block_task_number);
        final TextView taskText2 = root.findViewById(R.id.block_task_number2);
        final TextView taskText3 = root.findViewById(R.id.block_task_number3);
        blockViewModel.getBlockCategory().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                blockText.setText(strings.get(0));
                blockText2.setText(strings.get(1));
                blockText3.setText(strings.get(2));
                blockText4.setText(strings.get(3));

            }
        });

        blockViewModel.getNTask().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                taskText.setText(strings.get(0));
                taskText2.setText(strings.get(1));
                taskText3.setText(strings.get(2));
            }
        });
        return root;
    }
}