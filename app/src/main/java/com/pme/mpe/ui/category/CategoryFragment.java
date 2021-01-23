package com.pme.mpe.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.card.MaterialCardView;
import com.pme.mpe.R;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivity;
import com.pme.mpe.model.tasks.Category;

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;

    public Category categories[];


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        GridView gridView = (GridView) root.findViewById(R.id.category_grid_layout);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories);

        MaterialCardView addCategoryActivity = root.findViewById(R.id.category_grid_add);
        addCategoryActivity.setOnClickListener(this.addButtonClickListener);

        return root;
    }

    private final View.OnClickListener addButtonClickListener = v -> {

        if( v.getId() == R.id.category_grid_add)
        {
            Intent newCategoryIntent = new Intent(getContext(), NewCategoryActivity.class);
            startActivity(newCategoryIntent);
        }
    };
}