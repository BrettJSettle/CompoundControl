package com.settle.compoundcontrol.level.node.command.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;
import com.settle.compoundcontrol.level.node.command.keyboard.view.CommandEditorLine;

import org.jetbrains.annotations.Nullable;

public class CommandEditorView extends LinearLayout {
    private CommandEditorLine[] lines;
    private int selectedLine = -1;

    public CommandEditorView(Context context) {
        super(context);
        init(getResources().getInteger(R.integer.max_lines));
    }

    public CommandEditorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommandEditorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CommandEditorView, defStyle, 0);

        int lines = a.getInteger(R.styleable.CommandEditorView_lines, getResources().getInteger(R.integer.max_lines));
        init(lines);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isInEditMode()) {
            return;
        }
        int size = getMeasuredHeight() / lines.length;
        for (CommandEditorLine tv : lines) {
            tv.setHeight(size);
            tv.correctWidth();
        }
    }

    private void init(int lines) {
        setOrientation(VERTICAL);
        this.lines = new CommandEditorLine[lines];
        if (isInEditMode()) {
            return;
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        for (int i = 0; i < lines; i++) {
            CommandEditorLine tv = new CommandEditorLine(getContext());
            tv.setMaxLineLength(getResources().getInteger(R.integer.max_characters));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTag(i);
            addView(tv, i, params);
            this.lines[i] = tv;
        }
    }

    public void highlightLine(int i) {
        if (selectedLine >= 0) {
            lines[selectedLine].setBackgroundColor(getResources().getColor(R.color.colorBackground));
        }
        selectedLine = i;
        if (selectedLine >= 0) {
            lines[selectedLine].setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Nullable
    public CommandEditorLine getSelectedLine() {
        if (selectedLine < 0) {
            return null;
        }
        return lines[selectedLine];
    }

    public void setLineClickHandler(OnClickListener listener) {
        for (CommandEditorLine line : lines) {
            line.setOnClickListener(listener);
        }
    }

    public CommandLineState[] getStates() {
        CommandLineState[] states = new CommandLineState[lines.length];
        for (int i = 0; i < lines.length; i++) {
            states[i] = lines[i].getState();
        }
        return states;
    }

    public void setState(int i, CommandLineState line) {
        lines[i].setState(line);
    }

    @NonNull
    public CommandLineState getState(int index) {
        return lines[index].getState();
    }
}
