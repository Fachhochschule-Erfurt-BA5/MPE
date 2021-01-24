package com.pme.mpe.activities.CategoryActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;

public class NewCategoryActivity extends AppCompatActivity {
    private EditText categoryName;
    private ImageButton categoryColor;
    private NewCategoryActivityViewModel newCategoryActivityViewModel;


    private final View.OnClickListener saveCategoryClickListener = v -> {

        if( v.getId() == R.id.save_category) {
            Category newCategory = new Category(1, categoryName.getText().toString(),"test");
            newCategoryActivityViewModel.saveCategory(newCategory);
        }
    };


        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        Button saveCategory = findViewById(R.id.save_category);
        saveCategory.setOnClickListener(this.saveCategoryClickListener);
    }
}
