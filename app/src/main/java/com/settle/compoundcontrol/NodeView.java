package com.settle.compoundcontrol;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.HashMap;

public class NodeView extends ConstraintLayout {
    HashMap<PortView.Direction, PortView> ports = new HashMap<>();
    public NodeView(Context context) {
        super(context);
        init(null, 0);
    }

    public NodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void setPortValue(PortView.Direction direction, Integer value){
        PortView view = ports.get(direction);
        if (view != null) {
            view.setValue(value);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.node_view, this);

        ports.put(PortView.Direction.LEFT, (PortView) findViewById(R.id.left_port));
        ports.put(PortView.Direction.UP, (PortView) findViewById(R.id.up_port));
        ports.put(PortView.Direction.RIGHT, (PortView) findViewById(R.id.right_port));
        ports.put(PortView.Direction.DOWN, (PortView) findViewById(R.id.down_port));
    }
}
