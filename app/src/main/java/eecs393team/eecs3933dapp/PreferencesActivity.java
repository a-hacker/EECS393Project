package eecs393team.eecs3933dapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PreferencesActivity extends Activity {
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private SeekBar alphaSeekBar;
    private ToggleButton axesToggleButton;
    private ToggleButton gridsToggleButton;
    protected TextView objectColorView;

    protected void applyColor() {
        int color = redSeekBar.getProgress() * 255 / redSeekBar.getMax() << 16;
        color |= greenSeekBar.getProgress() * 255 / greenSeekBar.getMax() << 8;
        color |= blueSeekBar.getProgress() * 255 / blueSeekBar.getMax();
        color |= alphaSeekBar.getProgress() * 255 / alphaSeekBar.getMax() << 24;
        objectColorView.setBackgroundColor(color);
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);
        alphaSeekBar = (SeekBar) findViewById(R.id.alphaSeekBar);
        axesToggleButton = (ToggleButton) findViewById(R.id.axesToggleButton);
        gridsToggleButton = (ToggleButton) findViewById(R.id.gridToggleButton);

        objectColorView = (TextView) findViewById(R.id.objectColorView);
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                applyColor();
                return false;
            }
        };

        redSeekBar.setOnTouchListener(onTouchListener);
        greenSeekBar.setOnTouchListener(onTouchListener);
        blueSeekBar.setOnTouchListener(onTouchListener);
        alphaSeekBar.setOnTouchListener(onTouchListener);

        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STLRenderer.red = 0.75f;
                STLRenderer.green = 0.75f;
                STLRenderer.blue = 0.75f;
                STLRenderer.alpha = 0.5f;
                STLRenderer.displayAxes = true;
                STLRenderer.displayGrids = true;

                redSeekBar.setProgress((int) (redSeekBar.getMax() * STLRenderer.red));
                greenSeekBar.setProgress((int) (greenSeekBar.getMax() * STLRenderer.green));
                blueSeekBar.setProgress((int) (blueSeekBar.getMax() * STLRenderer.blue));
                alphaSeekBar.setProgress((int) (alphaSeekBar.getMax() * STLRenderer.alpha));
                axesToggleButton.setChecked(STLRenderer.displayAxes);
                gridsToggleButton.setChecked(STLRenderer.displayGrids);

                applyColor();
            }
        });

        Button closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    };

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences colorConfig = getSharedPreferences("colors", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = colorConfig.edit();

        STLRenderer.red = (float) redSeekBar.getProgress() / (float) redSeekBar.getMax();
        STLRenderer.green = (float) greenSeekBar.getProgress() / (float) greenSeekBar.getMax();
        STLRenderer.blue = (float) blueSeekBar.getProgress() / (float) blueSeekBar.getMax();
        STLRenderer.alpha = (float) alphaSeekBar.getProgress() / (float) alphaSeekBar.getMax();
        STLRenderer.displayAxes = axesToggleButton.isChecked();
        STLRenderer.displayGrids = gridsToggleButton.isChecked();

        editor.putFloat("red", STLRenderer.red);
        editor.putFloat("green", STLRenderer.green);
        editor.putFloat("blue", STLRenderer.blue);
        editor.putFloat("alpha", STLRenderer.alpha);
        editor.putBoolean("displayAxes", STLRenderer.displayAxes);
        editor.putBoolean("displayGrids", STLRenderer.displayGrids);

        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences colorConfig = getSharedPreferences("colors", Activity.MODE_PRIVATE);

        float red = colorConfig.getFloat("red", 0.75f);
        float green = colorConfig.getFloat("green", 0.75f);
        float blue = colorConfig.getFloat("blue", 0.75f);
        float alpha = colorConfig.getFloat("alpha", 0.5f);
        boolean displayAxes = colorConfig.getBoolean("displayAxes", true);
        boolean displayGrids = colorConfig.getBoolean("displayGrids", true);

        redSeekBar.setProgress((int) (redSeekBar.getMax() * red));
        greenSeekBar.setProgress((int) (greenSeekBar.getMax() * green));
        blueSeekBar.setProgress((int) (blueSeekBar.getMax() * blue));
        alphaSeekBar.setProgress((int) (alphaSeekBar.getMax() * alpha));
        axesToggleButton.setChecked(displayAxes);
        gridsToggleButton.setChecked(displayGrids);

        applyColor();
    }
}
