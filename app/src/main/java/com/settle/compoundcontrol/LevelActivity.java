package com.settle.compoundcontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.otaliastudios.zoom.ZoomLayout;
import com.settle.compoundcontrol.level.event.ControlListener;
import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;
import com.settle.compoundcontrol.level.node.command.keyboard.event.NodeEditorEventListener;
import com.settle.compoundcontrol.level.node.command.view.CommandNodeView;
import com.settle.compoundcontrol.level.node.editor.NodeEditor;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.state.LevelState;
import com.settle.compoundcontrol.level.view.ControlView;
import com.settle.compoundcontrol.level.view.NodeTable;


public class LevelActivity extends AppCompatActivity implements ControlListener, NodeEditorEventListener {
    private static final int STEP_SLEEP_TIME = 1000;
    protected FrameLayout frame;
    protected LevelState base_state;
    protected ZoomLayout pan_zoom_view;
    private ControlView buttons;
    private boolean running = false;
    private NodeView selectedNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        frame = findViewById(R.id.level_frame);
        pan_zoom_view = findViewById(R.id.pan_zoom_view);
        buttons = findViewById(R.id.control_view);
        base_state = (LevelState) getIntent().getSerializableExtra("level");
        init();
    }

    private void init() {
        NodeTable grid = new NodeTable(getBaseContext(), base_state);
        for (NodeView view : grid.getNodeViews()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view instanceof NodeView) {
                        focusOnNode((NodeView) view);
                    }
                }
            });
        }
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
                    stepPressed();
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

    public void stopPressed() {
        setRunning(false);
    }

    public void stepPressed() {
        setRunning(false);
    }

    public void focusOnNode(NodeView view) {
        if (selectedNode != null) {
            return;
        }
        selectedNode = view;
        NodeEditor editor = (NodeEditor) getSupportFragmentManager().findFragmentById(R.id.node_editor_fragment);
        if (editor == null) {
            System.out.println("null editor");
            editor = new NodeEditor();
        }

        editor.setNodeEditorEventListener(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.level_frame, editor);


        if (view instanceof CommandNodeView) {
            CommandNodeView node = (CommandNodeView) view;
            Bundle bundle = new Bundle();
            bundle.putSerializable("states", node.getStates());
            editor.setArguments(bundle);
        } else {
            System.out.println("No info view for " + view);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NodeEditor) {
            ((NodeEditor) fragment).setNodeEditorEventListener(this);
        }
    }

    @Override
    public void saveStates(CommandLineState[] states) {
        ((CommandNodeView) selectedNode).setStates(states);
    }

    @Override
    public void closeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        selectedNode = null;
    }
}