package com.settle.compoundcontrol.level.node.iocolumn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.config.IOColumn;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.state.InputColumnGameState;
import com.settle.compoundcontrol.level.state.NodeGameState;

public class InputColumnView extends NodeView {
    private static final IOColumn SAMPLE_DATA = new IOColumn(4, new int[]{1, 2, 3});
    private TextView valueView, titleView;

    public InputColumnView(Context context) {
        super(context);
    }

    public InputColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputColumnView(Context context, InputColumnGameState is) {
        super(context, is);
    }

    @Override
    public void init(NodeGameState state) {
        super.init(state);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.input_column_view, this);
        titleView = findViewById(R.id.column_title);
        valueView = findViewById(R.id.column_value);
    }

    private void setName(String name) {
        titleView.setText(name);
    }

    @Override
    public void update() {
        InputColumnGameState is = (InputColumnGameState) state;
        setName(is.getName());
        int num = 0;
        if (state.isActive()) {
            num = is.getIndex();
        }
        valueView.setText(String.valueOf(is.getAt(num)));
    }
}
