package com.settle.compoundcontrol.level.node.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.settle.compoundcontrol.level.port.BidirectionalPortView;

public class NodeView extends ConstraintLayout {
    private SparseArray<BidirectionalPortView> ports = new SparseArray<>();

    public NodeView(Context context) {
        super(context);
        init();
    }

    public NodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setBackgroundColor(Color.BLACK);
    }

    public void read() {

    }

    public void write() {

    }

    public void setPort(int direction, BidirectionalPortView port) {
        ports.put(direction, port);
    }

    public BidirectionalPortView getPort(int direction) {
        return ports.get(direction);
    }
}
