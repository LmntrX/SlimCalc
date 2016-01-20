package com.lmntrx.slimcalc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnAdd, btnSub, btnMultiply, btnDivide, btnA, btnB, btnC, btnEquals, btnDot, btnClear;
    TextView Display, display_op;
    String operator = "", lastPress = "", op = "";
    double a = 0;
    boolean corrected = false, fromMemory = false, memoryCleared = false, longClickA = false, longClickB = false, longClickBack = false, negativeInitiated, isVibrationOn, isThemeDark;
    Context con;
    MenuItem about;
    Vibrator mVibrator;
    SettingsActivity settings;
    Toast toast;
    static String realResult = null;
    NumberFormat numberFormat = new DecimalFormat("#.########");

    SharedPreferences sp;

    boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = new SettingsActivity();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        isVibrationOn = sp.getBoolean("VIBRATE_CHECKBOX", true);
        isThemeDark = sp.getBoolean("THEME_CHECKBOX", true);
        if (isThemeDark) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.light_theme);
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btnA = (Button) findViewById(R.id.storeA);
        btnB = (Button) findViewById(R.id.storeB);
        btnC = (Button) findViewById(R.id.storeC);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnEquals = (Button) findViewById(R.id.btnEqual);
        Display = (TextView) findViewById(R.id.display);
        display_op = (TextView) findViewById(R.id.displayOpr);
        con = this;
        about = (MenuItem) findViewById(R.id.action_about);


        if (savedInstanceState != null) {

            Display.setText(savedInstanceState.getString("PREVIOUS_VALUE"));
            display_op.setText(savedInstanceState.getString("OPERATOR"));
            a = savedInstanceState.getDouble("PREVIOUS_RESULT");
            A = savedInstanceState.getDouble("MEMORY_A");
            B = savedInstanceState.getDouble("MEMORY_B");


        }

        View.OnLongClickListener bt_Click = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                A = 0;
                B = 0;
                memoryCleared = true;
                longClickBack = true;
                vibrate(200);
                toast.setText("Memory Cleared");
                toast.show();
                return false;
            }
        };
        btnClear.setOnLongClickListener(bt_Click);
        View.OnLongClickListener A_LongClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                A = 0;
                longClickA = true;
                vibrate(200);
                toast.setText("A Cleared");
                toast.show();
                return false;
            }
        };
        btnA.setOnLongClickListener(A_LongClick);
        View.OnLongClickListener B_LongClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                B = 0;
                longClickB = true;
                vibrate(200);
                toast.setText("B Cleared");
                toast.show();
                return false;
            }
        };
        btnB.setOnLongClickListener(B_LongClick);
    }


    public void readAndDisplay(View v) {
        vibrate(25);
        if (lastPress.equals("=")) {
            clearAll();
            lastPress = "";
        }
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        switch (v.getId()) {
            case R.id.btn0:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "0");
                break;
            case R.id.btn1:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "1");
                break;
            case R.id.btn2:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "2");
                break;
            case R.id.btn3:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "3");
                break;
            case R.id.btn4:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "4");
                break;
            case R.id.btn5:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "5");
                break;
            case R.id.btn6:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "6");
                break;
            case R.id.btn7:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "7");
                break;
            case R.id.btn8:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "8");
                break;
            case R.id.btn9:
                if (lastPress.equals("+") || lastPress.equals("*") || lastPress.equals("/") || lastPress.equals("A") || lastPress.equals("B")) {
                    clearAll();
                    lastPress = "";
                } else if (lastPress.equals("-")) {
                    if (negativeInitiated) {
                        lastPress = "";
                    } else {
                        clearAll();
                        lastPress = "";
                    }

                }
                Display.setText(Display.getText() + "9");
                break;
        }
    }

    public void add(View v) {
        op = "+";
        String disp = Display.getText().toString();
        if (disp.contains("x10^")) {
            disp = disp.replace("x10^", "E");
        }
        if ((Display.getText() + "").equals("") || (Display.getText() + "").equals(".") || disp.equals("∞")) {
            ;
        } else if (lastPress == "+" || lastPress == "-" || lastPress == "*" || lastPress == "/") {
            switch (op) {
                case "+":
                    lastPress = "+";
                    operator = "+";
                    break;
                case "-":
                    lastPress = "-";
                    operator = "-";
                    break;
                case "*":
                    lastPress = "*";
                    operator = "*";
                    break;
                case "/":
                    lastPress = "/";
                    operator = "/";
                    break;
            }
            vibrate(25);
        } else {
            if (operator.equals("+")) {
                a += Double.parseDouble(disp);
                operator = "+";
                lastPress = "+";

            } else if (operator.equals("-")) {
                a -= Double.parseDouble(disp);
                operator = "+";
                lastPress = "+";

            } else if (operator.equals("*")) {
                a *= Double.parseDouble(disp);
                operator = "+";
                lastPress = "+";

            } else if (operator.equals("/")) {
                a /= Double.parseDouble(disp);
                operator = "+";
                lastPress = "+";

            } else {
                a = Double.parseDouble(disp);
                operator = "+";
                lastPress = "+";

            }
            vibrate(25);
        }


        display_op.setText("+");

    }

    public void sub(View v) {
        if (negative(v)) {
            lastPress = "-";
        } else {
            op = "-";
            String disp = Display.getText().toString();
            if (disp.contains("x10^")) {
                disp = disp.replace("x10^", "E");
            }
            if ((Display.getText() + "").equals("") || (Display.getText() + "").equals(".") || disp.equals("∞")) {
                ;
            } else if (lastPress == "+" || lastPress == "-" || lastPress == "*" || lastPress == "/") {
                switch (op) {
                    case "+":
                        lastPress = "+";
                        operator = "+";
                        break;
                    case "-":
                        lastPress = "-";
                        operator = "-";
                        break;
                    case "*":
                        lastPress = "*";
                        operator = "*";
                        break;
                    case "/":
                        lastPress = "/";
                        operator = "/";
                        break;
                }
                vibrate(25);
            } else {
                if (operator.equals("+")) {
                    a += Double.parseDouble(disp);
                    operator = "-";
                    lastPress = "-";

                } else if (operator.equals("-")) {
                    a -= Double.parseDouble(disp);
                    operator = "-";
                    lastPress = "-";

                } else if (operator.equals("*")) {
                    a *= Double.parseDouble(disp);
                    operator = "-";
                    lastPress = "-";

                } else if (operator.equals("/")) {
                    a /= Double.parseDouble(disp);
                    operator = "-";
                    lastPress = "-";

                } else {
                    a = Double.parseDouble(disp);
                    operator = "-";
                    lastPress = "-";

                }
                vibrate(25);
            }

            display_op.setText("-");
        }

    }

    public boolean negative(View v) {
        if (((Display.getText() + "").equals("") && a == 0) || ((Display.getText() + "").equals(".") && a == 0)) {
            Display.setText("-");
            negativeInitiated = true;
            vibrate(25);
            return true;
        } else {
            negativeInitiated = false;
            return false;
        }
    }

    public void multiply(View v) {
        op = "*";
        String disp = Display.getText().toString();
        if (disp.contains("x10^")) {
            disp = disp.replace("x10^", "E");
        }
        if ((Display.getText() + "").equals("") || (Display.getText() + "").equals(".") || disp.equals("∞")) {
            ;
        } else if (lastPress == "+" || lastPress == "-" || lastPress == "*" || lastPress == "/") {
            switch (op) {
                case "+":
                    lastPress = "+";
                    operator = "+";
                    break;
                case "-":
                    lastPress = "-";
                    operator = "-";
                    break;
                case "*":
                    lastPress = "*";
                    operator = "*";
                    break;
                case "/":
                    lastPress = "/";
                    operator = "/";
                    break;
            }
            vibrate(25);
        } else {
            if (operator.equals("+")) {
                a += Double.parseDouble(disp);
                operator = "*";
                lastPress = "*";

            } else if (operator.equals("-")) {
                a -= Double.parseDouble(disp);
                operator = "*";

                lastPress = "*";
            } else if (operator.equals("*")) {
                a *= Double.parseDouble(disp);
                operator = "*";
                lastPress = "*";

            } else if (operator.equals("/")) {
                a /= Double.parseDouble(disp);
                operator = "*";
                lastPress = "*";

            } else {
                a = Double.parseDouble(disp);
                operator = "*";
                lastPress = "*";

            }
            vibrate(25);
        }

        display_op.setText("x");
    }

    public void divide(View v) {
        op = "/";
        String disp = Display.getText().toString();
        if (disp.contains("x10^")) {
            disp = disp.replace("x10^", "E");
        }
        if ((Display.getText() + "").equals("") || (Display.getText() + "").equals(".") || disp.equals("∞")) {
            ;
        } else if (lastPress == "+" || lastPress == "-" || lastPress == "*" || lastPress == "/") {
            switch (op) {
                case "+":
                    lastPress = "+";
                    operator = "+";
                    break;
                case "-":
                    lastPress = "-";
                    operator = "-";
                    break;
                case "*":
                    lastPress = "*";
                    operator = "*";
                    break;
                case "/":
                    lastPress = "/";
                    operator = "/";
                    break;
            }
            vibrate(25);
        } else {
            if (operator.equals("+")) {
                a += Double.parseDouble(disp);
                operator = "/";
                lastPress = "/";
            } else if (operator.equals("-")) {
                a -= Double.parseDouble(disp);
                operator = "/";
                lastPress = "/";
            } else if (operator.equals("*")) {
                a *= Double.parseDouble(disp);
                operator = "/";
                lastPress = "/";

            } else if (operator.equals("/")) {
                a /= Double.parseDouble(disp);
                operator = "/";
                lastPress = "/";

            } else {
                a = Double.parseDouble(disp);
                operator = "/";
                lastPress = "/";

            }
            vibrate(25);
        }


        display_op.setText("÷");
    }

    public void equal(View v) {
        switch (operator) {
            case "+": {
                try {
                    a += Double.parseDouble(Display.getText() + "");
                    Display.setText(numberFormat.format(a));

                } catch (Exception e) {
                }
                break;
            }
            case "-": {
                try {
                    a -= Double.parseDouble(Display.getText() + "");
                    Display.setText(numberFormat.format(a));
                    //Display.setText(numberFormat.format(a - Double.parseDouble(Display.getText() + "")) + "");
                } catch (Exception e) {
                }
                break;
            }
            case "*": {
                try {
                    a *= Double.parseDouble(Display.getText() + "");
                    Display.setText(numberFormat.format(a));
                    //Display.setText(numberFormat.format(a * Double.parseDouble(Display.getText() + "")) + "");
                } catch (Exception e) {
                }
                break;

            }
            case "/": {
                try {
                    a /= Double.parseDouble(Display.getText() + "");
                    Display.setText(numberFormat.format(a));
                    //Display.setText(numberFormat.format(a / Double.parseDouble(Display.getText() + "")) + "");
                } catch (Exception e) {
                }
                break;
            }
        }
        realResult = a + "";
        correctExponent();
        vibrate(25);
        a = 0;
        lastPress = "=";
        operator = "";
        display_op.setText("=");
    }

    private void correctExponent() {
        String result = Display.getText().toString();

        int length;
        double number;
        if (result.contains("E")) {

            length = result.indexOf('E');

            if (length > 13) {
                number = Double.parseDouble(result.substring(0, 12));
                Display.setText(number + "x10^" + result.substring(result.indexOf("E") + 1));
            } else {
                Display.setText(result.replace("E", "x10^"));
            }


        } else if (result.contains(".") && result.length() < Double.SIZE) {


            if (result.length() > 10)
                result = round(result, 4) + "";

            try {
                if (Integer.parseInt(result.substring(result.indexOf(".") + 1)) == 0) {
                    Display.setText(result.substring(0, result.indexOf(".")));

                }

            } catch (NumberFormatException nfe) {
                Display.setText(result);
                correctExponent();
            }


        } else if (result.contains(".")) {

            try {
                result = round(result, 8) + "";
                Display.setText(result);
                correctExponent();
            } catch (NumberFormatException nfe) {
                Log.e("Number Format Wrong", nfe.getMessage());
            }

        } else {
            if (realResult != null) {

                if (realResult.length() > 10) {
                    Display.setText(realResult.charAt(0) + realResult.substring(1, 6) + "x10^" + realResult.substring(realResult.indexOf("E") + 1));

                } else if (realResult.length() <= 10 && result.length() <= 10) {

                } else {
                    Display.setText("∞");
                    a = Double.POSITIVE_INFINITY;
                    realResult = "∞";
                }
            } else {
                Display.setText(result);
                if (!corrected) {
                    corrected = true;
                    correctExponent();
                }
            }
        }

    }

    public BigDecimal round2(String value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd;
    }

    public double round(String value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        try {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        } catch (NumberFormatException e) {
            clearAll();
            return 0;
        }
    }

    public void decimal(View v) {
        if (lastPress.equals("=")) {
            clearAll();
            lastPress = "";
        }
        Display.append(".");
        vibrate(25);
    }

    public void clearAll() {
        Display.setText("");
    }

    public void back(View v) {
        if (!longClickBack) {
            String h = Display.getText() + "", k = "";
            int l = h.length();
            int i = 0, j = 0;
            while (i < (l - 1)) {
                k += h.charAt(i);
                i++;
            }
            Display.setText(k);
            k = "";
            vibrate(25);
        } else
            ;
        longClickBack = false;
    }

    boolean A_On = false, B_On = false;
    double A = 0, B = 0;

    public void storeA(View v) {

        if (sp.getBoolean("FIRST_START",true)) {
            alertHelp();
        }
        if (!longClickA) {
            memoryCleared = A == 0 && B == 0;
            if (memoryCleared || A == 0) {
                if (!Display.getText().toString().isEmpty() && !Display.getText().toString().equals("∞")) {

                    String disp = Display.getText().toString();
                    if (disp.contains("x10^")) {
                        disp = disp.replace("x10^", "E");
                    }
                    A = Double.parseDouble(disp);
                    vibrate(100);
                    toast.setText("Value stored in A, Long press A to clear value");
                    toast.show();
                    lastPress = "A";
                }
            } else {
                vibrate(25);
                Display.setText(A + "");
                A_On = false;
                correctExponent();
            }
        }

        longClickA = false;

    }

    public void storeB(View v) {
        if (sp.getBoolean("FIRST_START",true)) {
            alertHelp();
        }
        if (longClickB == false) {
            if (A == 0 && B == 0)
                memoryCleared = true;
            else if (A != 0 || B != 0)
                memoryCleared = false;
            if (memoryCleared || B == 0) {
                if (!Display.getText().toString().isEmpty() && !Display.getText().toString().equals("∞")) {

                    String disp = Display.getText().toString();
                    if (disp.contains("x10^")) {
                        disp = disp.replace("x10^", "E");
                    }
                    B = Double.parseDouble(disp);
                    memoryCleared = false;
                    vibrate(100);
                    toast.setText("Value stored in B, Long press B to clear value");
                    toast.show();
                    lastPress = "B";
                }
            } else {
                vibrate(25);
                Display.setText(B + "");
                B_On = false;
                correctExponent();
            }
        }

        longClickB = false;

    }

    private void alertHelp() {

        View checkBoxView = View.inflate(this, R.layout.dialog_check_box, null);
        CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Save to shared preferences
                if (isChecked) {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("FIRST_START", false);
                    edit.apply();
                } else {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("FIRST_START", true);
                    edit.apply();
                }
            }
        });
        checkBox.setText("Got it");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hint");
        builder.setMessage("Tap on A or B to store the displayed value in memory. Long press to clear individual memory or long press on ← to clear both")
                .setView(checkBoxView)
                .setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }

    public void clear(View v) {
        clearAll();
        vibrate(50);
        a = 0;
        realResult = null;
        display_op.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent intent = new Intent(con, About.class);
            startActivity(intent);
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(con, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        isVibrationOn = sp.getBoolean("VIBRATE_CHECKBOX", true);
        isThemeDark = sp.getBoolean("THEME_CHECKBOX", true);
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void vibrate(long l) {

        if (isVibrationOn) {
            mVibrator.vibrate(l);
        } else if ((isVibrationOn + "").equals("")) {
            mVibrator.vibrate(l);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putString("OPERATOR", display_op.getText().toString());
        outState.putString("PREVIOUS_VALUE", Display.getText().toString());
        outState.putDouble("PREVIOUS_RESULT", a);
        outState.putDouble("MEMORY_A", A);
        outState.putDouble("MEMORY_B", B);


        super.onSaveInstanceState(outState, outPersistentState);


    }
}




