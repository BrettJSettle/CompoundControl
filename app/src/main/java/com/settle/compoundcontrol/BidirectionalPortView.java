package com.settle.compoundcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

public class BidirectionalPortView extends LinearLayout {
    PortView first, second;
    SparseArray<NodeView> nodes = new SparseArray<>();

    public BidirectionalPortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BidirectionalPortView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BidirectionalPortView, defStyle, 0);
        String directions_str = a.getString(R.styleable.BidirectionalPortView_directions);
        int directions = (directions_str != null && directions_str.equals("0")) ? VERTICAL : HORIZONTAL;
        a.recycle();
        init(directions);
    }

    public BidirectionalPortView(Context context, NodeView a, NodeView b, int directions) {
        super(context);
        init(directions);
        if (directions == LinearLayout.HORIZONTAL) {
            nodes.put(ConstraintSet.LEFT, a);
            a.setPort(ConstraintSet.RIGHT, this);
            nodes.put(ConstraintSet.RIGHT, b);
            b.setPort(ConstraintSet.LEFT, this);
        } else {
            nodes.put(ConstraintSet.TOP, a);
            a.setPort(ConstraintSet.BOTTOM, this);
            nodes.put(ConstraintSet.BOTTOM, b);
            b.setPort(ConstraintSet.TOP, this);
        }
    }

    public static int reverseDirection(int direction) {
        switch (direction) {
            case ConstraintSet.TOP:
                return ConstraintSet.BOTTOM;
            case ConstraintSet.RIGHT:
                return ConstraintSet.LEFT;
            case ConstraintSet.BOTTOM:
                return ConstraintSet.TOP;
            case ConstraintSet.LEFT:
                return ConstraintSet.RIGHT;
        }
        return 0;
    }

    void init(int port_dirs) {
        PortView.PortDirection d1 = port_dirs == HORIZONTAL ? PortView.PortDirection.RIGHT : PortView.PortDirection.UP;
        PortView.PortDirection d2 = port_dirs == HORIZONTAL ? PortView.PortDirection.LEFT : PortView.PortDirection.DOWN;

        first = new PortView(getContext(), d1);
        first.setBackgroundColor(Color.RED);
        first.setId(View.generateViewId());

        second = new PortView(getContext(), d2);
        second.setBackgroundColor(Color.BLUE);
        second.setId(View.generateViewId());

        addView(first);
        addView(second);

        // Layout orientation is the perpendicular of the port directions
        int layout_orientation = (port_dirs == VERTICAL) ? HORIZONTAL : VERTICAL;
        setOrientation(layout_orientation);
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
        if (getOrientation() == LinearLayout.VERTICAL) {
            first.setDirection(PortView.PortDirection.RIGHT);
            second.setDirection(PortView.PortDirection.LEFT);
        } else {
            first.setDirection(PortView.PortDirection.UP);
            second.setDirection(PortView.PortDirection.DOWN);
        }
    }

    public NodeView getNode(int direction) {
        return nodes.get(direction);
    }
}
