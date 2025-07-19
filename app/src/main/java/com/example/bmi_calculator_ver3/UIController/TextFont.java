package com.example.bmi_calculator_ver3.UIController;

import android.widget.TextView;

public interface TextFont {
    // getter
    TextView getTextUnderWeight();
    TextView getTextDesirable();
    TextView getTextObese();
    TextView getTextOverWeight();
    TextFont getFont();

    // setter
    void setTextUnderWeight(TextView textUnderWeight);
    void setTextDesirable(TextView textDesirable);
    void setTextObese(TextView textObese);
    void setTextOverWeight(TextView textOverWeight);
    void setFont(TextFont font);

    // wrap method
    void callChangeObeseFont();
}
