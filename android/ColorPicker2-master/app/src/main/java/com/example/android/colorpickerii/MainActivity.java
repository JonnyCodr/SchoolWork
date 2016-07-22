package com.example.android.colorpickerii;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class MainActivity extends Activity {

    public static final int REQUEST_CODE_COLOR_ONE = 1001;
    public static final int REQUEST_CODE_COLOR_TWO = 1002;
    public static final String SELECT_COLOR = "Select A Color";
    public static final int MAX_VAL = 255;
    public static final int NULL = 0;

//    private static int mAlpha = MAX_VAL;
    private static int mRed = MAX_VAL;
    private static int mGreen = MAX_VAL;
    private static int mBlue = MAX_VAL;

    private View mColorPreviewTwo;
    private View mColorPreviewOne;
    private Button mColorTwoButton;
    private Button mColorOneButton;
    private View mColorOneView;
    private View mColorTwoView;

    private SeekBar mMixerBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorPreviewTwo = findViewById(R.id.vColorTwoPreview);
        mColorPreviewOne = findViewById(R.id.vColorOnePreview);
        mColorTwoButton = (Button) findViewById(R.id.btnColorTwo);
        mColorOneButton = (Button) findViewById(R.id.btnColorOne);
        mColorOneView = findViewById(R.id.vColorOneForMixView);
        mColorTwoView = findViewById(R.id.vColorTwoForMixView);

        mMixerBar = (SeekBar)findViewById(R.id.sbMixerBar);


        mColorPreviewOne.setBackgroundColor(Color.argb(MAX_VAL, MAX_VAL, MAX_VAL, MAX_VAL));
        mColorPreviewTwo.setBackgroundColor(Color.argb(MAX_VAL, MAX_VAL, MAX_VAL, MAX_VAL));

        mColorTwoButton.setText(SELECT_COLOR);
        mColorTwoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*pass current aRGB values to the color picker?*/

                //use this to pass the second activity the value
                Intent intent = new Intent(MainActivity.this, ColorPickerActivity.class);

                //pass current aRGB values to the color picker here

                startActivityForResult(intent, REQUEST_CODE_COLOR_TWO);
            }
        });

        mColorOneButton.setText(SELECT_COLOR);
        mColorOneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*pass current aRGB values to the color picker?*/

                //use this to pass the second activity the value
                Intent intent = new Intent(MainActivity.this, ColorPickerActivity.class);

                startActivityForResult(intent, REQUEST_CODE_COLOR_ONE);
            }
        });

        mColorOneView.setBackgroundColor(Color.argb(MAX_VAL, MAX_VAL, MAX_VAL, MAX_VAL));
        mColorTwoView.setBackgroundColor(Color.argb(MAX_VAL, MAX_VAL, MAX_VAL, MAX_VAL));

        setupMixerBar();
    }

    private void setupMixerBar() {

        mMixerBar.setEnabled(false);
        mMixerBar.setProgress(125);

        mMixerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mColorTwoView.setBackgroundColor(Color.argb(progress, mRed, mGreen, mBlue));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                /* intentionally left blank */
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* intentionally left blank */
            }
        });
    }

    //this gets called when the activity that has been called, returns
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_COLOR_ONE:

                mColorOneButton.setText(""
                        + data.getIntExtra(ColorPickerActivity.VAL_ALPHA, NULL) + ", "
                        + data.getIntExtra(ColorPickerActivity.VAL_RED, NULL) + ", "
                        + data.getIntExtra(ColorPickerActivity.VAL_GREEN, NULL) + ", "
                        + data.getIntExtra(ColorPickerActivity.VAL_BLUE, NULL));

                mColorTwoButton.setText(SELECT_COLOR);

                mColorPreviewOne.setBackgroundColor(Color.argb(
                        data.getIntExtra(ColorPickerActivity.VAL_ALPHA, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_RED, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_GREEN, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_BLUE, NULL)));

                mColorPreviewTwo.setBackgroundColor(Color.argb(MAX_VAL, MAX_VAL, MAX_VAL, MAX_VAL));
                mColorTwoView.setBackgroundColor(Color.argb(MAX_VAL, MAX_VAL, MAX_VAL, MAX_VAL));

                mColorOneView.setBackgroundColor(Color.argb(
                        data.getIntExtra(ColorPickerActivity.VAL_ALPHA, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_RED, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_GREEN, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_BLUE, NULL)));

                mMixerBar.setProgress(NULL);
                mMixerBar.setEnabled(false);

                break;

            case REQUEST_CODE_COLOR_TWO:

//                mAlpha = data.getIntExtra(ColorPickerActivity.VAL_ALPHA, NULL);
                mRed = data.getIntExtra(ColorPickerActivity.VAL_RED, NULL);
                mGreen = data.getIntExtra(ColorPickerActivity.VAL_GREEN, NULL);
                mBlue = data.getIntExtra(ColorPickerActivity.VAL_BLUE, NULL);

                mColorTwoButton.setText(""
                        + data.getIntExtra(ColorPickerActivity.VAL_ALPHA, NULL) + ", "
                        + data.getIntExtra(ColorPickerActivity.VAL_RED, NULL) + ", "
                        + data.getIntExtra(ColorPickerActivity.VAL_GREEN, NULL) + ", "
                        + data.getIntExtra(ColorPickerActivity.VAL_BLUE, NULL));

                //this is where you would set the colorOne Preview
                mColorPreviewTwo.setBackgroundColor(Color.argb(
                        data.getIntExtra(ColorPickerActivity.VAL_ALPHA, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_RED, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_GREEN, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_BLUE, NULL)));

                //this is where you set the colorOne view for the mix.
                mColorTwoView.setBackgroundColor(Color.argb(
                        data.getIntExtra(ColorPickerActivity.VAL_ALPHA, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_RED, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_GREEN, NULL),
                        data.getIntExtra(ColorPickerActivity.VAL_BLUE, NULL)));

                mMixerBar.setProgress(MAX_VAL);
                mMixerBar.setEnabled(true);

                break;
        }
    }

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
        }else if (id == R.id.action_reset){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
