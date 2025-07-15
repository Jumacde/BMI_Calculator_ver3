package com.example.bmi_calculator_ver3;

public interface CalcLogic {
    // getter
    String getHeight();
    String getWeight();
    double getCalcHeight();
    double getCalcWeight();
    double getBmi();
    double getStandardWeight();
    double getGoalWeight();
    boolean getIsInput();
    boolean getIsA();

    // setter
    void setHeight(String height);
    void setWeight(String weight);
    void setCalcHeight(double calcHeight);
    void setCalcWeight(double calcWeight);
    void setBmi(double bmi);
    void setStandardWeight(double standardWeight);
    void setGoalWeight(double goalWeight);
    void setIsInput(boolean isInput);
    void setIsA(boolean isA);

    // wrap methods
    void callCalcBmi();
    double callCalcGoalWeight();

}
