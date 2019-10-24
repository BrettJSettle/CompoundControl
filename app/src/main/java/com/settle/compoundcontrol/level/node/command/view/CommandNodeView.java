package com.settle.compoundcontrol.level.node.command.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;
import com.settle.compoundcontrol.level.node.view.NodeView;

public class CommandNodeView extends NodeView implements View.OnClickListener {
    private TextView textArea;
    private CommandLineState[] states;

    public CommandNodeView(Context context) {
        super(context);
    }

    public CommandNodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        super.init();
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View nodeView = inflater.inflate(R.layout.execution_node_view, this, false);
        addView(nodeView, new ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textArea = nodeView.findViewById(R.id.command_editor);
        int lines = getResources().getInteger(R.integer.max_lines);
        states = new CommandLineState[lines];
        for (int i = 0; i < lines; i++) {
            states[i] = new CommandLineState();
        }
    }

    @Override
    public void onClick(View view) {
        if (!(view instanceof TextView)) {
            return;
        }
        view.setBackgroundColor(Color.RED);
    }

    public CommandLineState[] getStates() {
        CommandLineState[] states_copy = new CommandLineState[states.length];
        for (int i = 0; i < states.length; i++) {
            states_copy[i] = states[i].clone();
        }
        return states_copy;
    }

    public void setStates(CommandLineState[] states) {
        this.states = states;
        StringBuilder builder = new StringBuilder();
        for (CommandLineState line : states) {
            builder.append(line.toString());
            builder.append("\n");
        }
        textArea.setText(builder.toString());
    }
}
