package com.settle.compoundcontrol.level.node.command.keyboard.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;

import java.util.Locale;

public class CommandEditorLine extends android.support.v7.widget.AppCompatTextView {
    private CommandLineState state;
    private Integer maxLineLength;

    public CommandEditorLine(Context context) {
        this(context, null, 0);
    }

    public CommandEditorLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommandEditorLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        super.onFinishInflate();
        setGravity(Gravity.CENTER_VERTICAL);
        int color = Color.rgb((int) (255 * Math.random()),
                (int) (255 * Math.random()),
                (int) (255 * Math.random()));
        setBackgroundColor(color);
        state = new CommandLineState();
    }

    public void correctWidth() {
        if (maxLineLength == null) {
            throw new RuntimeException("Cannot correct width with no max line length.");
        }
        Paint paint = new Paint();
        Rect bounds = new Rect();
        int desiredWidth = this.getMeasuredWidth();

        paint.setTypeface(this.getTypeface());
        float textSize = 6;
        paint.setTextSize(textSize);
        String text = String.format(Locale.getDefault(), "%0" + this.maxLineLength + "d", 0);

        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.width() < desiredWidth) {
            textSize++;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 1);
    }

    public void setMaxLineLength(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        setFilters(filterArray);
        this.maxLineLength = length;
    }

    public void update() {
        setText(state.toString());
    }

    public CommandLineState getState() {
        return state;
    }

    public void setState(CommandLineState line) {
        this.state = line;
        setText(line.toString());
    }
}
