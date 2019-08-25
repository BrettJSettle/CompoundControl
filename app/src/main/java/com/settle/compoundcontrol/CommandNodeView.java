package com.settle.compoundcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

public class CommandNodeView extends NodeView {
    CommandEditorView editor;
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
        inflater.inflate(R.layout.command_node_view, this);
        editor = findViewById(R.id.command_editor);
    }

    public void setText(int line, String text) {
        editor.setText(line, text);
    }
}
