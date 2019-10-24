package com.settle.compoundcontrol.level.node.iocolumn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.state.IOColumn;

public class IOColumnView extends NodeView {
    private static final IOColumn SAMPLE_DATA = new IOColumn(4, new int[]{1, 2, 3});
    private IOColumn data;
    private TextView valueView, titleView;
    private int position = 0;

    public IOColumnView(Context context) {
        super(context);
        init("SAMPLE", SAMPLE_DATA);
    }

    public IOColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init("SAMPLE", SAMPLE_DATA);
    }

    public IOColumnView(Context context, String name, IOColumn column) {
        super(context);
        init(name, column);
    }

    private void init(String name, IOColumn column) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.iocolumn_view, this);
        titleView = findViewById(R.id.column_title);
        valueView = findViewById(R.id.column_value);
        setName(name);
        setData(column);
    }

    private void setName(String name) {
        titleView.setText(name);
    }

    private void setData(IOColumn data) {
        this.data = data;
        position = 0;
        updateValue();
    }

    private void updateValue() {
        int num = data.getValues()[position];
        valueView.setText(String.valueOf(num));
    }

    public void step() {
        position += 1;
        updateValue();
    }
}
