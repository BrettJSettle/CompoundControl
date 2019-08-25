package com.settle.compoundcontrol;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.SparseArray;

class NodeView extends ConstraintLayout {
    private SparseArray<BidirectionalPortView> ports = new SparseArray<>();

    public NodeView(Context context) {
        super(context);
        init();
    }

    public NodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        setBackgroundColor(Color.BLACK);
    }

    public void setPort(int direction, BidirectionalPortView port) {
        ports.put(direction, port);
    }

    public BidirectionalPortView getPort(int direction) {
        return ports.get(direction);
    }
}
