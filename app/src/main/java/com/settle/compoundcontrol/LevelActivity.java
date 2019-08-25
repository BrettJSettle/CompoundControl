package com.settle.compoundcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.otaliastudios.zoom.ZoomLayout;


public class LevelActivity extends AppCompatActivity implements ControlListener {
    private static final int STEP_SLEEP_TIME = 1000;
    protected LevelState base_state;
    protected ZoomLayout pan_zoom_view;
    private ControlView buttons;
    private boolean running = false;
    private NodeTable grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        pan_zoom_view = findViewById(R.id.pan_zoom_view);
        buttons = findViewById(R.id.control_view);
        base_state = (LevelState) getIntent().getSerializableExtra("level");
        init();
    }

    private void init() {
        grid = new NodeTable(getBaseContext(), base_state);
        pan_zoom_view.addView(grid);
        buttons.setControlListener(this);
    }

    public void playPressed() {
        setRunning(!running);
        if (!running) {
            return;
        }
        LevelActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    step();
                    try {
                        Thread.sleep(STEP_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setRunning(boolean running) {
        this.running = running;
        buttons.setRunning(running);
    }

    public void step() {
        setRunning(false);
    }

    public void stopPressed() {
        setRunning(false);
    }

    public void stepPressed() {
        step();
    }


}