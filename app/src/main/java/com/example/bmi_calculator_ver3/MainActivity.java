package com.example.bmi_calculator_ver3;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.bmi_calculator_ver3.impl.CalcLogic_impl;
import com.example.bmi_calculator_ver3.impl.DisplayController_impl;

public class MainActivity extends AppCompatActivity {
    private TextView textViewBMI, textViewComment, mainTextBmiClassification ;
    private ScrollView scrollView;
    private EditText editTextHeight, editTextWeight;
    private CheckBox checkBox;
    private Button submitButton, clearButton, aboutAppButton, aboutBMIButton, classificationButton;
    private ImageButton imageButton;
    private DrawerLayout drawerLayoutMain, drawerLayoutTab;
    private LinearLayout textAboutApp, textAboutBMI, textClassification, infoTextContainer;

    private CalcLogic calcLogic;
    private DisplayController displayController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.caclculate_page);

        textViewBMI = findViewById(R.id.bmiText);
        textViewBMI.setText("0.00");
        textViewComment = findViewById(R.id.commentText);
        textViewComment.setText("");

        drawerLayoutMain = findViewById(R.id.main_drawer_layout);
        drawerLayoutTab = findViewById(R.id.tab_drawer_layout);

        editTextHeight = (EditText) findViewById(R.id.height);
        editTextWeight = (EditText) findViewById(R.id.weight);
        editTextHeight.setText("0cm");
        editTextWeight.setText("0kg");

        checkBox = findViewById(R.id.checkboxId);

        setUpSubmitButton(R.id.submit);
        setUpClearButton(R.id.clear);

        setUpMenuIcon();

        calcLogic = new CalcLogic_impl();
        displayController = new DisplayController_impl(calcLogic);

        cursorControl();
        setUpTextWatcher(editTextHeight,"cm");
        setUpTextWatcher(editTextWeight, "kg");

        scrollView = findViewById(R.id.mainScroll);

        // initialize buttons- and linear layouts id.
        aboutAppButton = findViewById(R.id.bAboutThisApp);
        aboutBMIButton = findViewById(R.id.bAboutBMI);
        classificationButton = findViewById(R.id.bAboutNutritionalStatus);

        textAboutApp = findViewById(R.id.textId_thisApp);
        textAboutBMI = findViewById(R.id.textId_bmi);
        textClassification = findViewById(R.id.textId_classification);

        mainTextBmiClassification = findViewById(R.id.id_mainTextClassification);

        infoTextContainer = findViewById(R.id.info_text_container);

        pushTextButton();

    }

    private void cursorControl() {
        // cursor moves on the weight textview after input finish on the height and push "enter".
        editTextHeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_DONE
                        || (keyEvent != null && keyEvent.getKeyCode()
                        == keyEvent.KEYCODE_ENTER && keyEvent.getAction()
                        == keyEvent.ACTION_DOWN)) {
                    // move cursor(focus) on the weight text view.
                    editTextWeight.requestFocus();
                    return true; // successfully execute
                }
                return false; // not execute
            }
        });


    }
    /**
     * method: set up the submit button function.
     * @ Param: int id => to search id from xml.
     *  this button sends number part of height and weight.
     *  inputController -> inputController_impl.
     *  buttonController -> buttonController_impl -> callPushSubmitButton -> pushSubmitButton -> bmiCalculate from BMICalculate_impl
     *      1. get number part of height and weight from inputController.
     *      2. calculate BMI via callPushSubmitButton from buttonController.
     *      3.a. set BMI result and comment on the textView(display).
     *      3.b. set comment for asian if you check the checkBox.
     *      4. heide the keyboard after push the submit-button.
     *      5. error message shows on a new message-window.
     * */
    private void setUpSubmitButton(int id) {
        submitButton = findViewById(id);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get both editText and convert to String.
                calcLogic.setHeight(editTextHeight.getText().toString());
                calcLogic.setWeight(editTextWeight.getText().toString());
                calcLogic.setIsA(checkBox.isChecked()); // check asian.
                calcLogic.callCalcBmi(); // calculate BMI.

                // set up error message on a new message window.
                String bmiResultText = displayController.callShowBMI();
                String commentText = displayController.callShowComment();
                String commentGoalText = displayController.callShowGoalWeight(); // show goal weight.

                if ("ERROR_VALUE".equals(bmiResultText)
                        || "ERROR_VALUE".equals(commentText)
                        || "ERROR_VALUE".equals(commentGoalText)) {
                    // show error dialog
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error")
                            .setMessage("failed height or weight." +
                                    "\nplease input your height and weight again.")
                            .setPositiveButton("OK", (dialog,whitch)-> {
                                // nothing to do what does after pushing ok button.
                            }).show();
                    textViewBMI.setText("0"); // initialise text
                    textViewComment.setText(""); // initialise text
                    // set text color.
                    textViewComment.setTextColor(ContextCompat.
                            getColor(MainActivity.this, android.R.color.black));
                } else {
                    textViewBMI.setText(bmiResultText); // get the BMI result.
                    textViewComment.setText(commentText + "\n" + commentGoalText); // get comment and the goal weight.

                    // set text color
                    SpannableStringBuilder ssb = new SpannableStringBuilder();
                    int commentTextColor;
                    if (commentText.contains("normal weight.")) {
                        commentTextColor = ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_dark);
                    } else if (commentText.contains("underweight.") || commentText.contains("pre-obesity.")) {
                        commentTextColor = ContextCompat.getColor(MainActivity.this, android.R.color.holo_orange_dark);
                    } else { // obesity class I, II, III
                        commentTextColor = ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_dark);
                    }
                    ssb.append(commentText, new ForegroundColorSpan(commentTextColor), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.append("\n");
                    // set to be color of commentGoalText black.
                    ssb.append(commentGoalText, new ForegroundColorSpan(ContextCompat.getColor(
                            MainActivity.this, android.R.color.black)), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textViewComment.setText(ssb);
                }

                // hide the keyboard
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }
    /**
     * method: set up the clear button function.
     * @ Param: int id => to search id from xml.
     *  execute "clearDisplay" method by wrap method from displayController_impl class via the interface class "displayController"
     *  displayController -> displayController_impl-> callClearDisplay(wrap method) -> clearDisplay.
     *      1. clear(initialize) inputted number part of height and weight.
     *      2. clear(initialize) BMI calculate result.
     *      3. clear(initialize) BMI comment.
     * **/
    private void setUpClearButton(int id) {
        clearButton = findViewById(id);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayController.callClearDisplay();
                editTextHeight.setText("");
                editTextWeight.setText("");
                textViewBMI.setText("0"); // initialise text
                textViewComment.setText(""); // initialise text
            }
        });
    }

    /**
     * method: TextWatcher: fix to show always a unit (cm and kg)
     * @ Param: final EditText editText
     * @ Param: final String unit
     *      - cm or kg
     * **/
    private void setUpTextWatcher(final EditText editText, final String unit) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdate = false;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing to do here
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isUpdate) {
                    return; // avoid infinite loop.
                }
                isUpdate = true;
                String text = editable.toString();
                // if already a unit(cm or kg) is there, nothing to do.
                if (text.endsWith(unit) && text.length() > text.length() && text.substring(0, text.length() - unit.length()).matches(".*\\d.*")) {
                    if (editText.getSelectionEnd() == text.length() - unit.length()) {
                        isUpdate = false;
                        return;
                    }
                }
                // puck up a number and remove units(cm and kg).
                String number = text.replaceAll("[^\\d.]", "");
                // remove 0 if user input any number.
                if (number.length() > 1 && number.startsWith("0") && !number.startsWith("0.")) {
                    number = number.substring(1);
                }
                // if no number is there, add a unit.
                if (!number.isEmpty()) {
                    String textWithUnit = number + unit;
                    editText.setText(textWithUnit);
                    editText.setSelection(number.length()); // move the cursor to just after the number
                } else if (text.isEmpty()) {
                    editText.setText("0" + unit); // initialize the number("0").
                    editText.setSelection(1); // move the cursor to just after initialized number("0").
                }
                isUpdate = false;
            }
        });

    }

    // set up menu icon on drawer
    private void setUpMenuIcon() {
        imageButton = findViewById(R.id.menuIcon);
        if ( imageButton != null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayoutMain.openDrawer(GravityCompat.END);
                    Toast.makeText(MainActivity.this, "drawer success", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void showAboutAppText() {
        if (scrollView != null) {
            scrollView.setVisibility(View.GONE);
        }
        if (infoTextContainer != null) {
            infoTextContainer.setVisibility(View.VISIBLE);
        }
        hideTextButton();
        if (textAboutApp != null) {
            textAboutApp.setVisibility(View.VISIBLE);
        }
    }

    private void showAboutBMIText() {
        if (scrollView != null) {
            scrollView.setVisibility(View.GONE);
        }
        if (infoTextContainer != null) {
            infoTextContainer.setVisibility(View.VISIBLE);
        }
        hideTextButton();
        if (textAboutBMI != null) {
            textAboutBMI.setVisibility(View.VISIBLE);
        }
    }

    private void showClassificationText() {
        if (scrollView != null) {
            scrollView.setVisibility(View.GONE);
        }
        if (infoTextContainer != null) {
            infoTextContainer.setVisibility(View.VISIBLE);
        }
        hideTextButton();
        if (textClassification != null) {
            textClassification.setVisibility(View.VISIBLE);
        }
    }

    private void pushTextButton() {
        aboutAppButton = findViewById(R.id.bAboutThisApp);
        aboutBMIButton = findViewById(R.id.bAboutBMI);
        classificationButton = findViewById(R.id.bAboutNutritionalStatus);

        if (aboutAppButton != null) {
            aboutAppButton.setOnClickListener(v -> {
                showAboutAppText();
                //hideTextButton();
                /*
                if (textAboutApp != null) {
                    textAboutApp.setVisibility(View.VISIBLE);
                }
                * */
                if (drawerLayoutMain != null) {
                    drawerLayoutMain.closeDrawer(GravityCompat.END);
                }
            });
        }

        if (aboutBMIButton != null) {
            aboutBMIButton.setOnClickListener(v -> {
                showAboutBMIText();
                //hideTextButton();
                /*
                if (textAboutBMI != null) {
                    textAboutBMI.setVisibility(View.VISIBLE);
                }*/
                if (drawerLayoutMain != null) {
                    drawerLayoutMain.closeDrawer(GravityCompat.END);
                }
            });
        }

        if (classificationButton != null) {
            classificationButton.setOnClickListener(v -> {
                showClassificationText();
                //hideTextButton();
                /*
                if (textClassification != null) {
                    textClassification.setVisibility(View.VISIBLE);
                }
                * */
                if (drawerLayoutMain != null) {
                    drawerLayoutMain.closeDrawer(GravityCompat.END);
                }
            });
        }
    }

    private void hideTextButton() {
        /*
        * if (scrollView != null) {
            scrollView.setVisibility(View.GONE);
        }
        if (infoTextContainer != null) {
            infoTextContainer.setVisibility(View.GONE);
        }
        * */
        if (textAboutApp != null) {
            textAboutApp.setVisibility(View.GONE);
        }
        if (textAboutBMI != null) {
            textAboutBMI.setVisibility(View.GONE);
        }
        if (textClassification != null) {
            textClassification.setVisibility(View.GONE);
        }

    }

    private void backFromTextPage() {
        if (scrollView != null) {
            scrollView.setVisibility(View.VISIBLE);
        }
        if (infoTextContainer != null) {
            infoTextContainer.setVisibility(View.GONE);
        }
        if (textAboutApp != null) {
            textAboutApp.setVisibility(View.GONE);
        }
        if (textAboutBMI != null) {
            textAboutBMI.setVisibility(View.GONE);
        }
        if (textClassification != null) {
            textClassification.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayoutMain != null && drawerLayoutMain.isDrawerOpen(GravityCompat.END)) {
            drawerLayoutMain.closeDrawer(GravityCompat.END);
        } else {
            if (infoTextContainer != null && infoTextContainer.getVisibility() == View.VISIBLE) {
                //
                backFromTextPage();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void changeFont() {
        String fullTextClassification = mainTextBmiClassification.getText().toString();
        SpannableStringBuilder ssbClassification = new SpannableStringBuilder(fullTextClassification);

        // set colort
        int colorBlue = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
        int colorOrange = ContextCompat.getColor(this, android.R.color.holo_orange_light);
        int colorRed = ContextCompat.getColor(this, android.R.color.holo_red_dark);

        String word = "";

    }

}