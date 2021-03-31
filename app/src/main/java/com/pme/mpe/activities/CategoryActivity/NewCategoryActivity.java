package com.pme.mpe.activities.CategoryActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.mifmif.common.regex.Main;
import com.pme.mpe.MainActivity;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.util.ColorSelector;
import com.pme.mpe.storage.dao.ColorSelectorDialog;
import com.pme.mpe.ui.category.CategoryFragment;

public class NewCategoryActivity extends AppCompatActivity {
    private AlertDialog colorPickDialog;
    private TextView categoryHex;
    private EditText categoryName;
    private ImageButton categoryColor;
    private NewCategoryActivityViewModel newCategoryActivityViewModel;
    private ColorSelector colorSelector;
    String letterColor ="#000000";
    String cardColor = "#54aadb";



    private final View.OnClickListener saveCategoryClickListener = v -> {

        if (v.getId() == R.id.save_category) {
            Category newCategory = new Category(4, categoryName.getText().toString(), cardColor,letterColor);
            newCategoryActivityViewModel.saveCategory(newCategory);
            Intent blockIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(blockIntent);

        }

    };

    private final View.OnClickListener categoryColorClickListener = v -> {
        ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog() {
            @Override
            public void colorPicked(int red, int green, int blue, int textColor) {
                categoryColor.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(red,green,blue)));
                CharSequence hexColor_argb = Integer.toHexString(Color.rgb(red,green,blue));
                cardColor = "#" + hexColor_argb.charAt(2) + hexColor_argb.charAt(3) + hexColor_argb.charAt(4) + hexColor_argb.charAt(5) + hexColor_argb.charAt(6) + hexColor_argb.charAt(7);
                categoryHex.setText(cardColor);
                CharSequence hexColor_argb1 = Integer.toHexString(textColor);
                letterColor = "#" + hexColor_argb1.charAt(2) + hexColor_argb1.charAt(3) + hexColor_argb1.charAt(4) + hexColor_argb1.charAt(5) + hexColor_argb1.charAt(6) + hexColor_argb1.charAt(7);
            }
        };
        colorSelector = new ColorSelector();
       colorSelector.showColorSelectorDialog(this,colorSelectorDialog);
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        newCategoryActivityViewModel = new ViewModelProvider(this).get(NewCategoryActivityViewModel.class);
        categoryHex = findViewById(R.id.category_text_output);
        categoryName = findViewById(R.id.category_name_input);
        Button saveCategory = findViewById(R.id.save_category);
        categoryColor = findViewById(R.id.category_color_btn);



        saveCategory.setOnClickListener(this.saveCategoryClickListener);
        categoryColor.setOnClickListener(this.categoryColorClickListener);
    }
}
