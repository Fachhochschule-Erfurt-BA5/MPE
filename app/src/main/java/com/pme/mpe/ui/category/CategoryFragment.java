package com.pme.mpe.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.pme.mpe.R;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivity;
import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.List;

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    private AppCompatTextView categoryAdd;
    private TasksPackageRepository tasksPackageRepository;
    private CategoryWithCatBlocksAndTasksRelation categoryWithCatBlocksAndTasksRelation;
    public List<Category> categories;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        tasksPackageRepository = new TasksPackageRepository(categoryViewModel.getApplication());
        categoryWithCatBlocksAndTasksRelation = new CategoryWithCatBlocksAndTasksRelation();
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        GridView gridView = (GridView) root.findViewById(R.id.category_grid_layout);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, tasksPackageRepository,categoryWithCatBlocksAndTasksRelation);
        gridView.setAdapter(categoryAdapter);

        categoryViewModel.getCategories().observe(this.requireActivity(),categoryAdapter::setCategories);


        categoryAdd = root.findViewById(R.id.category_grid_text);
        categoryAdd.setText(R.string.addCategory);
        LinearLayoutCompat addCategoryActivity = root.findViewById(R.id.category_grid_add);
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