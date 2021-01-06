package com.pme.mpe.ui.category;

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

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        final TextView textView = root.findViewById(R.id.category_grid_name);
        final TextView textView1 = root.findViewById(R.id.category_grid_name2);
        final TextView textView2 = root.findViewById(R.id.category_grid_name3);
        final TextView textView3 = root.findViewById(R.id.category_grid_name4);
        final TextView textView4 = root.findViewById(R.id.category_grid_name5);
        final TextView textView5 = root.findViewById(R.id.category_grid_text);
        final TextView nBlock = root.findViewById(R.id.category_grid_block);
        final TextView nBlock1 = root.findViewById(R.id.category_grid_block2);
        final TextView nBlock2 = root.findViewById(R.id.category_grid_block3);
        final TextView nBlock3 = root.findViewById(R.id.category_grid_block4);
        final TextView nBlock4 = root.findViewById(R.id.category_grid_block5);
        categoryViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                textView.setText(strings.get(0));
                textView1.setText(strings.get(1));
                textView2.setText(strings.get(2));
                textView3.setText(strings.get(3));
                textView4.setText(strings.get(4));
                textView5.setText(strings.get(5));
            }
        });

        categoryViewModel.getBlock().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                nBlock.setText(strings.get(0));
                nBlock1.setText(strings.get(1));
                nBlock2.setText(strings.get(2));
                nBlock3.setText(strings.get(3));
                nBlock4.setText(strings.get(4));
            }
        });

        return root;
    }
}