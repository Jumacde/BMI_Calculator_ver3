package com.example.bmi_calculator_ver3.UIController;

import android.widget.TextView;

public interface TextFont {
    // getter
    TextView getTextObese();
    TextFont getFont();

    // setter
    void setTextObese(TextView textObese);
    void setFont(TextFont font);

    // wrap method
    void callChangeObeseFont();
}
