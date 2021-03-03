package com.pme.mpe.model.util;

import android.content.Context;
import android.content.DialogInterface;
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

import com.pme.mpe.R;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivityViewModel;
import com.pme.mpe.storage.dao.ColorSelectorDialog;

public class ColorSelector {
    private AlertDialog colorPickDialog;


    public void showColorSelectorDialog(Context context,final ColorSelectorDialog colorSelectorDialog) {
        View layout = View.inflate(context, R.layout.fragment_select_color, null);
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
        red_text.setText(String.format(context.getResources().getConfiguration().locale, format, Color.red(Color.parseColor("#FFFFFF"))));
        colorSeekbars[0] = seekBarRed;

        final SeekBar seekBarGreen = (SeekBar) layout.findViewById(R.id.color_seekBar_green);
        final TextView green_text = (TextView) layout.findViewById(R.id.color_green_text);
        seekBarGreen.setProgress(Color.green(Color.parseColor("#FFFFFF")));
        green_text.setText(String.format(context.getResources().getConfiguration().locale, format, Color.green(Color.parseColor("#FFFFFF"))));
        colorSeekbars[1] = seekBarGreen;

        final SeekBar seekBarBlue = (SeekBar) layout.findViewById(R.id.color_seekBar_blue);
        final TextView blue_text = (TextView) layout.findViewById(R.id.color_blue_text);
        seekBarBlue.setProgress(Color.blue(Color.parseColor("#FFFFFF")));
        blue_text.setText(String.format(context.getResources().getConfiguration().locale, format, Color.blue(Color.parseColor("#FFFFFF"))));
        colorSeekbars[2] = seekBarBlue;


        colorCard.setCardBackgroundColor(Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress()));

        final SeekBar seekBarGrey = (SeekBar) layout.findViewById(R.id.color_seekBar_grey);
        final TextView grey_text = (TextView) layout.findViewById(R.id.color_grey_text);
        seekBarGrey.setProgress(0);
        grey_text.setText(String.format(format, 0));

        int color;
        color = Color.parseColor("#000000");
        grey_text.setText(String.format(format, 255));
        colorTextView.setTextColor(color);
        colorTextView.setText("Preview");

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

        seekBarGrey.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int color;
                if (progress > 255) {
                    color = Color.argb(seekBarGrey.getProgress() - 256, 255, 255, 255);
                    grey_text.setText(String.format(format, seekBarGrey.getProgress() - 256));
                } else {
                    color = Color.argb(255 - seekBarGrey.getProgress(), 0, 0, 0);
                    grey_text.setText(String.format(format, 255 - seekBarGrey.getProgress()));
                }
                colorTextView.setTextColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme_Light_noKeyboard);
        builder.setView(layout)
                .setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int textcolor;
                        if (seekBarGrey.getProgress() > 255) {
                            textcolor = Color.argb(seekBarGrey.getProgress(), 255, 255, 255);
                        } else {
                            textcolor = Color.argb(255 - seekBarGrey.getProgress(), 0, 0, 0);
                        }
                        colorSelectorDialog.colorPicked(seekBarRed.getProgress(),
                                seekBarGreen.getProgress(), seekBarBlue.getProgress(),textcolor);
                    }
                });
        colorPickDialog = builder.create();
        colorPickDialog.show();
        changeDialogButtonColor(context,colorPickDialog);
    }

    public AlertDialog changeDialogButtonColor(Context context,AlertDialog dialog) {

        Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positive != null) {
            positive.setTextColor(ContextCompat.getColor(context, R.color.primaryColor));
        }
        Button neutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        if (neutral != null) {
            neutral.setTextColor(ContextCompat.getColor(context, R.color.primaryColor));
        }
        Button negative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (negative != null) {
            negative.setTextColor(ContextCompat.getColor(context, R.color.primaryColor));
        }
        return dialog;
    }


}
