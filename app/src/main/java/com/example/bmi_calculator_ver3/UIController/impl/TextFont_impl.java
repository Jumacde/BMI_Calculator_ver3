package com.example.bmi_calculator_ver3.UIController.impl;

import com.example.bmi_calculator_ver3.UIController.TextFont;

public class TextFont_impl implements TextFont {
    String text;

    public TextFont_impl(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
