package com.settle.compoundcontrol.level.node.iocolumn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.config.IOColumn;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.state.NodeGameState;
import com.settle.compoundcontrol.level.state.OutputColumnGameState;

public class OutputColumnView extends NodeView {
    private static final IOColumn EXAMPLE = new IOColumn(4, new int[]{1, 2, 3});
    private TextView expectedView, actualView, titleView;

    public OutputColumnView(Context context) {
        super(context);
    }

    public OutputColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutputColumnView(Context context, OutputColumnGameState state) {
        super(context, state);
    }

    @Override
    public void init(NodeGameState state) {
        super.init(state);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.output_column_view, this);
        titleView = findViewById(R.id.column_title);
        expectedView = findViewById(R.id.expected_value);
        actualView = findViewById(R.id.actual_value);
    }

    private void setName(String name) {
        titleView.setText(name);
    }

    @Override
    public void update() {
        OutputColumnGameState os = (OutputColumnGameState) state;
        int expectedIndex = 0;
        setName(os.getName());
        if (state.isActive()) {
            expectedIndex = os.getIndex();
            Integer actual = os.getActual(expectedIndex);
            if (actual != null) {
                actualView.setText(String.valueOf(actual));
            }
        }
        expectedView.setText(String.valueOf(os.getAt(expectedIndex)));
    }
}
