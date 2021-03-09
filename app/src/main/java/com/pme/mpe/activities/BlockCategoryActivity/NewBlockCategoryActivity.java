package com.pme.mpe.activities.BlockCategoryActivity;

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
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivityViewModel;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.util.ColorSelector;
import com.pme.mpe.storage.dao.ColorSelectorDialog;
import com.pme.mpe.ui.block.BlockFragment;
import com.pme.mpe.ui.category.CategoryFragment;

public class NewBlockCategoryActivity extends AppCompatActivity {

    private EditText blockName;
    private TextView blockDate;
    private TextView blockStart;
    private TextView blockFinish;
    private Button blockSave;
    private NewBlockActivityViewModel newBlockActivityViewModel;


    private final View.OnClickListener saveBlockClickListener = v -> {

        if (v.getId() == R.id.block_save_btn) {
            //CategoryBlock newCategoryBlock = new CategoryBlock();
            //newBlockActivityViewModel.saveBlock(newCategoryBlock);
            //Intent blockIntent = new Intent(getApplicationContext(), BlockFragment.class);
            //startActivity(blockIntent);
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_block_category);
        newBlockActivityViewModel = new ViewModelProvider(this).get(NewBlockActivityViewModel.class);
        blockName = findViewById(R.id.block_name_input);
        blockDate = findViewById(R.id.block_date_select);
        blockStart = findViewById(R.id.block_start_select);
        blockFinish = findViewById(R.id.block_finish_select);
        blockSave = findViewById(R.id.block_save_btn);


        blockSave.setOnClickListener(this.saveBlockClickListener);
    }
}
