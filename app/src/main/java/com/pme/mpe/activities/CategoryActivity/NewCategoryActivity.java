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

import com.pme.mpe.R;

public class NewCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        ImageButton categoryColor = findViewById(R.id.category_color_btn);
        EditText categoryName = findViewById(R.id.category_name_input);
        Button categorySave = findViewById(R.id.save_category);
    }
}
