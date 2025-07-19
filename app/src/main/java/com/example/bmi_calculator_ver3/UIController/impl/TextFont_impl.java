package com.example.bmi_calculator_ver3.UIController.impl;

import android.widget.TextView;

import com.example.bmi_calculator_ver3.UIController.TextFont;

public class TextFont_impl implements TextFont {
    private TextView textUnderWeight, textDesirable, textObese, textOverWeight;
    private TextFont font;

    public TextFont_impl() {

    }


    @Override
    public TextView getTextUnderWeight() {
        return textUnderWeight;
    }

    @Override
    public TextView getTextDesirable() {
        return textDesirable;
    }

    @Override
    public TextView getTextObese() {
        return textObese;
    }

    @Override
    public TextView getTextOverWeight() {
        return textOverWeight;
    }

    @Override
    public TextFont getFont() {
        return font;
    }

    @Override
    public void setTextUnderWeight(TextView textUnderWeight) {
        this.textUnderWeight = textUnderWeight;
    }

    @Override
    public void setTextDesirable(TextView textDesirable) {
        this.textDesirable = textDesirable;
    }

    @Override
    public void setTextObese(TextView textObese) {
        this.textObese = textObese;
    }

    @Override
    public void setTextOverWeight(TextView textOverWeight) {
        this.textOverWeight = textOverWeight;
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
