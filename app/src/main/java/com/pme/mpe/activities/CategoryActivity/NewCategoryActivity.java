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
import androidx.lifecycle.ViewModelProvider;

import com.pme.mpe.MainActivity;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.storage.dao.ColorSelectorDialog;
import com.pme.mpe.ui.category.CategoryFragment;

public class NewCategoryActivity extends AppCompatActivity {
    private AlertDialog colorPickDialog;
    private TextView categoryHex;
    private EditText categoryName;
    private ImageButton categoryColor;
    private NewCategoryActivityViewModel newCategoryActivityViewModel;


    private final View.OnClickListener saveCategoryClickListener = v -> {

        if (v.getId() == R.id.save_category) {
            Category newCategory = new Category(4, categoryName.getText().toString(), categoryHex.getText().toString());
            newCategoryActivityViewModel.saveCategory(newCategory);
        }

    };

    private final View.OnClickListener categoryColorClickListener = v -> {
        ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog() {
            @Override
            public void colorPicked(int red, int green, int blue, int textColor) {
                categoryColor.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(red,green,blue)));
                CharSequence hexColor_argb = Integer.toHexString(Color.rgb(red,green,blue));
                String hexColor = "#" + hexColor_argb.charAt(2) + hexColor_argb.charAt(3) + hexColor_argb.charAt(4) + hexColor_argb.charAt(5) + hexColor_argb.charAt(6) + hexColor_argb.charAt(7);
                categoryHex.setText(hexColor);
            }
        };
        showColorSelectorDialog(colorSelectorDialog);
    };

    public void showColorSelectorDialog(final ColorSelectorDialog colorSelectorDialog) {
        View layout = View.inflate(this, R.layout.fragment_select_color, null);
        final String format = "%1$03d";

        final CardView colorCard = (CardView) layout.findViewById(R.id.color_preview_card);
        final TextView colorTextView = (TextView) layout.findViewById(R.id.color_preview_text);
        final EditText hexEditText = (EditText) layout.findViewById(R.id.color_hex_edit);

        colorTextView.setTextColor(Color.rgb(0, 0, 0));
        hexEditText.setTextColor(Color.parseColor("#000000"));
        hexEditText.setText("#FFFFFF");
        SeekBar[] colorSeekbars = new SeekBar[3];

        final SeekBar seekBarRed = (SeekBar) layout.findViewById(R.id.color_seekBar_red);
        final TextView red_text = (TextView) layout.findViewById(R.id.color_red_text);
        seekBarRed.setProgress(Color.red(Color.parseColor("#FFFFFF")));
        red_text.setText(String.format(getResources().getConfiguration().locale, format, Color.red(Color.parseColor("#FFFFFF"))));
        colorSeekbars[0] = seekBarRed;

        final SeekBar seekBarGreen = (SeekBar) layout.findViewById(R.id.color_seekBar_green);
        final TextView green_text = (TextView) layout.findViewById(R.id.color_green_text);
        seekBarGreen.setProgress(Color.green(Color.parseColor("#FFFFFF")));
        green_text.setText(String.format(getResources().getConfiguration().locale, format, Color.green(Color.parseColor("#FFFFFF"))));
        colorSeekbars[1] = seekBarGreen;

        final SeekBar seekBarBlue = (SeekBar) layout.findViewById(R.id.color_seekBar_blue);
        final TextView blue_text = (TextView) layout.findViewById(R.id.color_blue_text);
        seekBarBlue.setProgress(Color.blue(Color.parseColor("#FFFFFF")));
        blue_text.setText(String.format(getResources().getConfiguration().locale, format, Color.blue(Color.parseColor("#FFFFFF"))));
        colorSeekbars[2] = seekBarBlue;

        colorCard.setCardBackgroundColor(Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress()));

        for (int i = 0; i < colorSeekbars.length; i++) {
            colorSeekbars[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    colorCard.setCardBackgroundColor(Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress()));
                    red_text.setText(String.format(format, seekBarRed.getProgress()));
                    green_text.setText(String.format(format, seekBarGreen.getProgress()));
                    blue_text.setText(String.format(format, seekBarBlue.getProgress()));
                    CharSequence hexColor_argb = Integer.toHexString(Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress()));
                    String hexColor = "#" + hexColor_argb.charAt(2) + hexColor_argb.charAt(3) + hexColor_argb.charAt(4) + hexColor_argb.charAt(5) + hexColor_argb.charAt(6) + hexColor_argb.charAt(7);
                    hexEditText.setText(hexColor);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        hexEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 7 && s.charAt(0) == '#') {
                    int color;
                    try {
                        color = Color.parseColor(String.valueOf(s));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    int red = Color.red(color);
                    int green = Color.green(color);
                    int blue = Color.blue(color);
                    colorCard.setCardBackgroundColor(Color.rgb(red, green, blue));
                    seekBarRed.setProgress(red);
                    red_text.setText(String.format(format, red));
                    seekBarGreen.setProgress(green);
                    green_text.setText(String.format(format, green));
                    seekBarBlue.setProgress(blue);
                    blue_text.setText(String.format(format, blue));
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme_Light_noKeyboard);
        builder.setView(layout)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        colorSelectorDialog.colorPicked(seekBarRed.getProgress(),
                                seekBarGreen.getProgress(), seekBarBlue.getProgress(),0);
                    }
                });
        colorPickDialog = builder.create();
        colorPickDialog.show();
        changeDialogButtonColor(colorPickDialog);
    }

    public AlertDialog changeDialogButtonColor(AlertDialog dialog) {

        Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positive != null) {
            positive.setTextColor(ContextCompat.getColor(this, R.color.primaryColor));
        }
        Button neutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        if (neutral != null) {
            neutral.setTextColor(ContextCompat.getColor(this, R.color.primaryColor));
        }
        Button negative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (negative != null) {
            negative.setTextColor(ContextCompat.getColor(this, R.color.primaryColor));
        }
        return dialog;
    }

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
