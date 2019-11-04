package com.settle.compoundcontrol.level.node.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.settle.compoundcontrol.level.port.BidirectionalPortView;
import com.settle.compoundcontrol.level.state.CommandNodeGameState;
import com.settle.compoundcontrol.level.state.NodeGameState;

public abstract class NodeView extends ConstraintLayout {
    protected SparseArray<BidirectionalPortView> ports = new SparseArray<>();
    protected NodeGameState state;

    public NodeView(Context context) {
        super(context);
        init(CommandNodeGameState.EXAMPLE);
    }

    public NodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(CommandNodeGameState.EXAMPLE);
    }

    public NodeView(Context context, NodeGameState state) {
        super(context);
        init(state);
    }

    // Update subviews with values from the state.
    public abstract void update();

    public void init(NodeGameState state) {
        this.state = state;
        setBackgroundColor(Color.BLACK);
    }

    public NodeGameState getState() {
        return state;
    }

    public void setPort(int direction, BidirectionalPortView port) {
        ports.put(direction, port);
    }

    public BidirectionalPortView getPort(int direction) {
        return ports.get(direction);
    }
}
