package com.settle.compoundcontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.otaliastudios.zoom.ZoomLayout;
import com.settle.compoundcontrol.level.config.LevelConfig;
import com.settle.compoundcontrol.level.event.ControlListener;
import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;
import com.settle.compoundcontrol.level.node.command.keyboard.event.NodeEditorEventListener;
import com.settle.compoundcontrol.level.node.command.view.CommandNodeView;
import com.settle.compoundcontrol.level.node.editor.NodeEditor;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.state.CommandNodeGameState;
import com.settle.compoundcontrol.level.state.GameState;
import com.settle.compoundcontrol.level.view.ControlView;
import com.settle.compoundcontrol.level.view.NodeTable;


public class LevelActivity extends AppCompatActivity implements ControlListener, NodeEditorEventListener {

    protected ZoomLayout pan_zoom_view;
    private ControlView control;
    private NodeView selectedNode;
    private NodeTable grid;
    private GameState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        pan_zoom_view = findViewById(R.id.pan_zoom_view);
        control = findViewById(R.id.control_view);
        LevelConfig level = (LevelConfig) getIntent().getSerializableExtra("level");
        init(level);
    }

    private void init(LevelConfig level) {
        this.state = new GameState(level);
        grid = new NodeTable(getBaseContext(), state);
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
        control.setControlListener(this);
        grid.update();
    }

    public void playPressed() {
        // control.setRunning(true);
        // TODO: start AsyncTask running step() every STEP_SLEEP_TIME
    }

    public void stopPressed() {
        control.setRunning(false);
        boolean clear = false;
        if (!state.isActive()) {
            // TODO: ADD CONFIRM DIALOG.
            clear = true;
        }
        state.stop(clear);
        grid.update();
    }

    public void stepPressed() {
        control.setRunning(false);
        state.step();
        grid.update();
    }

    public void focusOnNode(NodeView view) {
        if (state.isActive()) {
            return;
        }
        if (selectedNode != null) {
            return;
        }

        NodeEditor editor = (NodeEditor) getSupportFragmentManager().findFragmentById(R.id.node_editor_fragment);
        if (editor == null) {
            editor = new NodeEditor();
        }

        editor.setNodeEditorEventListener(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.level_frame, editor);


        if (view instanceof CommandNodeView) {
            CommandNodeGameState gs = (CommandNodeGameState) view.getState();
            Bundle bundle = new Bundle();
            bundle.putSerializable("states", gs.getLines());
            editor.setArguments(bundle);
//        }else if (view instanceof InputColumnView){

        } else {
            return;
        }
        selectedNode = view;
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
    public void saveLines(CommandLineState[] lines) {
        CommandNodeGameState gs = (CommandNodeGameState) selectedNode.getState();
        gs.setLines(lines);
        grid.update();
    }

    @Override
    public void closeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        selectedNode = null;
    }
}