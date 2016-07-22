package com.example.android.colorpickerii;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class ColorPickerActivity extends Activity {

    public static final String VAL_ALPHA = "alpha value";
    public static final String VAL_RED = "red value";
    public static final String VAL_GREEN = "green value";
    public static final String VAL_BLUE = "blue value";
    static final int MIN_VAL = 0;
    static final int MAX_VAL = 255;

    int mAlpha = MAX_VAL, mRed, mGreen, mBlue;
    Color color1 = new Color();
    View colorView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        //this is where you would capture the values of the intent passed
        // from the main activity's color 1 and 2 values. using as an example:
        // int someValue = intent.getIntExtra("parameter name", 0);
        //where "parameter name is the same string used as the key from the
        //calling activity, and 0 is a default value.

        //use the new int someValue to set the colorPicker

        setupAlphaPicker();
        setupRedPicker();
        setupGreenPicker();
        setupBluePicker();
        setupMixItBtn();
        setupColorView();
    }

    private void setupMixItBtn() {
        Button mixIt = (Button)findViewById(R.id.btnMixIt);
        mixIt.setText("Mix It!");

        mixIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                //do the work
                intent.putExtra(VAL_ALPHA, getAlpha());
                intent.putExtra(VAL_RED, getRed());
                intent.putExtra(VAL_GREEN, getGreen());
                intent.putExtra(VAL_BLUE, getBlue());
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }

    private void setupColorView() {
        colorView = findViewById(R.id.vColorView);
        colorView.setBackgroundColor(Color.argb(getAlpha(),
                                                getRed(),
                                                getGreen(),
                                                getBlue()));
    }

    private void setupBluePicker() {
        NumberPicker bluePicker;
        bluePicker = (NumberPicker) findViewById(R.id.spBluePicker);
        bluePicker.setMinValue(MIN_VAL);
        bluePicker.setMaxValue(MAX_VAL);
        bluePicker.setValue(0);
//        bluePicker.setWrapSelectorWheel(true);

        bluePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setBlue(newVal);
                setupColorView();
            }
        });

    }

    private void setupGreenPicker() {
        NumberPicker greenPicker;
        greenPicker = (NumberPicker) findViewById(R.id.spGreenPicker);
        greenPicker.setMinValue(MIN_VAL);
        greenPicker.setMaxValue(MAX_VAL);
        greenPicker.setValue(0);
//        greenPicker.setWrapSelectorWheel(true);

        greenPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setGreen(newVal);
                setupColorView();
            }
        });
    }

    private void setupRedPicker() {
        NumberPicker redPicker;
        redPicker = (NumberPicker) findViewById(R.id.spRedPicker);
        redPicker.setMinValue(MIN_VAL);
        redPicker.setMaxValue(MAX_VAL);
        redPicker.setValue(0);
//        redPicker.setWrapSelectorWheel(true);

        redPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setRed(newVal);
                setupColorView();
            }
        });

    }

    private void setupAlphaPicker() {
        NumberPicker alphaPicker = (NumberPicker) findViewById(R.id.spAlphaPicker);
        alphaPicker.setMinValue(MIN_VAL);
        alphaPicker.setMaxValue(MAX_VAL);
        alphaPicker.setValue(255);
//        alphaPicker.setWrapSelectorWheel(true);

        alphaPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setAlpha(newVal);
                setupColorView();

            }
        });
    }

    private void setAlpha(int newVal) {
        this.mAlpha = newVal;
    }

    private void setRed(int newVal) {
        this.mRed = newVal;
    }

    private void setGreen(int newVal) {
        this.mGreen = newVal;
    }

    private void setBlue(int newVal) {
        this.mBlue = newVal;
    }

    public int getAlpha() { return this.mAlpha; }
    public int getRed() { return this.mRed; }
    public int getGreen() { return this.mGreen; }
    public int getBlue() { return this.mBlue; }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}