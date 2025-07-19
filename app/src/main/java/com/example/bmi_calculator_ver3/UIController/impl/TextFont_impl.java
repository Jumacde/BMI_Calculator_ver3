package com.example.bmi_calculator_ver3.UIController.impl;

import android.widget.TextView;

import com.example.bmi_calculator_ver3.UIController.TextFont;

public class TextFont_impl implements TextFont {
    private TextView textObese;
    private TextFont font;

    public TextFont_impl() {

    }


    @Override
    public TextView getTextObese() {
        return textObese;
    }

    @Override
    public TextFont getFont() {
        return font;
    }

    @Override
    public void setTextObese(TextView textObese) {
        this.textObese = textObese;
    }

    @Override
    public void setFont(TextFont font) {
        this.font = font;
    }

    // wrap method
    @Override
    public void callChangeObeseFont() {
        changeObeseFont();
    }

    private void changeObeseFont() {

    }
}
