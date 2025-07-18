package com.example.bmi_calculator_ver3.impl;

import com.example.bmi_calculator_ver3.ButtonMessage;

public class ButtonMessage_impl implements ButtonMessage {
    private String message;

    public ButtonMessage_impl() {

    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    // wrap method
    @Override
    public void callShowButtonMessage() {

    }

    private void showButtonMessage() {

    }

}
