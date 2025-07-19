package com.example.bmi_calculator_ver3.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.bmi_calculator_ver3.CalcLogic;
import com.example.bmi_calculator_ver3.DisplayController;
import com.example.bmi_calculator_ver3.R;

public class DisplayController_impl implements DisplayController {
    private String bmiDisplay;
    private String commentDisplay;

    private final CalcLogic calcLogic;

    public DisplayController_impl(CalcLogic calcLogic) {
        this.calcLogic = calcLogic;
        clearDisplay();
    }

    @Override
    public String getBmiDisplay() {
        return bmiDisplay;
    }

    @Override
    public String getCommentDisplay() {
        return commentDisplay;
    }

    @Override
    public void setBmiDisplay(String bmiDisplay) {
        this.bmiDisplay = bmiDisplay;
    }

    @Override
    public void setCommentDisplay(String commentDisplay) {
        this.commentDisplay = commentDisplay;
    }

    @Override
    public void callClearDisplay() {
        clearDisplay();
    }

    @Override
    public String callShowBMI() {
        return showBMI();
    }

    @Override
    public String callShowComment() {
        return showComment();
    }

    @Override
    public String callShowGoalWeight() {
        return showGoalWeight();
    }

    @Override
    public String callFormatNumber(double num) {
        return formatNumber(num);
    }

    private void clearDisplay() {
        this.calcLogic.setHeight("");
        this.calcLogic.setCalcHeight(0);
        this.calcLogic.setWeight("");
        this.calcLogic.setCalcWeight(0);
        this.calcLogic.setBmi(0);
        this.calcLogic.setIsInput(false);
        this.bmiDisplay = "0";
        this.commentDisplay = "0";
    }

    private String showBMI() {
        double bmi = calcLogic.getBmi();
        String bmiFormat = formatNumber(bmi);
        String bmiResult;
        if (Double.isInfinite(bmi) || Double.isNaN(bmi)) {
            return bmiResult = "ERROR_VALUE";
        } else {
            return bmiResult = bmiFormat;
        }
    }

    private String showComment() {
        double bmi = calcLogic.getBmi();
        double goalWeight = calcLogic.getGoalWeight();
        String comment;
        boolean isAsian = calcLogic.getIsA();
        //bmiCalculate.setIsAsian(true);

        if (Double.isInfinite(bmi) || Double.isNaN(bmi)) {
            return "ERROR_VALUE";
        }

        if (isAsian) {
            if (bmi < 17.5) {
                comment = "underweight.";
            } else if (bmi >= 17.5 && bmi < 23) {
                comment = "normal weight.";
            } else if (bmi >= 23 && bmi < 28) {
                comment = "pre-obesity.";
            } else if (bmi >= 27.5 && bmi < 30){ // bmi >= 28
                comment = "obesity class I";
            } else if (bmi >= 30 && bmi < 35) {
                comment = "obesity class II";
            } else {
                comment = "obesity class III";
            }
        } else {
            if (bmi < 18.5) {
                comment = "underweight.";
            } else if (bmi >= 18.5 && bmi < 25) {
                comment = "normal weight.";
            } else if (bmi >= 25 && bmi < 30) {
                comment = "pre-obesity.";
            } else if (bmi >= 30 && bmi < 34.9){
                comment = "obesity class I";
            } else if (bmi >= 35 && bmi < 39.9) {
                comment = "obesity class II";
            } else {
                comment = "obesity class III";
            }
        }
        return comment;
    }

    private String showGoalWeight() {
        double bmi = calcLogic.getBmi();
        calcLogic.callCalcGoalWeight();

        double calcWeight = calcLogic.getCalcWeight();
        double standardWeight =calcLogic.getStandardWeight();
        double goalWeight = calcLogic.getGoalWeight();
        double dietWeightGain = standardWeight - calcWeight;
        double dietWeightLoss = calcWeight - standardWeight;
        String commentGoal;
        boolean isAsian = calcLogic.getIsA();
        //bmiCalculate.setIsAsian(true);

        if (Double.isInfinite(bmi) || Double.isNaN(bmi)) {
            return "ERROR_VALUE";
        }

        if (isAsian) {
            if (bmi < 17.5) {
                commentGoal = "Goal weight gain +" + formatNumber(Math.abs(dietWeightGain)) + "kg";
            } else if (bmi > 23){
                commentGoal = "Goal weight loss -" + formatNumber(Math.abs(dietWeightLoss)) + "kg";
            } else {
                commentGoal = "";
            }
        } else {
            if (bmi < 18.5) {
                commentGoal = "Goal weight gain +" + formatNumber(Math.abs(dietWeightGain)) + "kg";
            } else if (bmi > 25){
                commentGoal = "Goal weight loss -" + formatNumber(Math.abs(dietWeightLoss)) + "kg";
            } else {
                commentGoal = "";
            }
        }
        return commentGoal;
    }

    /**
     * - method: float number format to a integer
     * @ param: double num
     * - set each number
     * **/
    @SuppressLint("DefaultLocale")
    private String formatNumber(double num) {
        //display up to two decimal places
        return String.format("%.2f", num);
    }

}
