package com.settle.compoundcontrol;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;

import com.otaliastudios.zoom.ZoomLayout;


public class LevelActivity extends AppCompatActivity {
    protected LevelState base_state;
    protected ZoomLayout panZoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        panZoomView = findViewById(R.id.pan_zoom_view);

        base_state = (LevelState) getIntent().getSerializableExtra("level");
        init();
    }

    protected void initNodeGrid(){
        NodeGrid grid = new NodeGrid(getBaseContext());
        grid.makeGrid(base_state);
        panZoomView.addView(grid);
    }

    private void init(){
        initNodeGrid();

        // activate control buttons
    }
}