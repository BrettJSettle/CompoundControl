package com.settle.compoundcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class CommandNodeView extends NodeView {
    FrameLayout nodeLayout;

    public CommandNodeView(Context context) {
        super(context);
        init(null, 0);
    }

    public CommandNodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CommandNodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.node_view, this);

        nodeLayout = findViewById(R.id.node_frame);
        CommandNodeView commandNode = new CommandNodeView(getContext());
        nodeLayout.addView(commandNode);
    }
}
