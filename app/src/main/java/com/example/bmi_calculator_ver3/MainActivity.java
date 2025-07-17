package com.example.bmi_calculator_ver3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.bmi_calculator_ver3.impl.CalcLogic_impl;
import com.example.bmi_calculator_ver3.impl.DisplayController_impl;

public class MainActivity extends AppCompatActivity {
    private TextView textViewBMI, textViewComment;
    private EditText editTextHeight, editTextWeight;
    private CheckBox checkBox;
    private Button button;
    private ImageButton imageButton;
    private DrawerLayout drawerLayout;
    private LinearLayout textAboutApp, textBMI, textClassification;

    private CalcLogic calcLogic;
    private DisplayController displayController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.caclculate_page);

        calcLogic = new CalcLogic_impl();
        displayController = new DisplayController_impl(calcLogic);

    }
}