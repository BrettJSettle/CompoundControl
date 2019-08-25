package com.settle.compoundcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class CommandEditorView extends LinearLayout {
    private static final int DEFAULT_LINES = 10;
    private static final int MAX_LINE_LENGTH = 20;
    private TextView[] lines;

    public CommandEditorView(Context context) {
        super(context);
        init(DEFAULT_LINES);
    }

    public CommandEditorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommandEditorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CommandEditorView, defStyle, 0);

        int lines = a.getInteger(R.styleable.CommandEditorView_lines, DEFAULT_LINES);
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
        for (TextView tv : lines) {
            tv.setHeight(size);
//            tv.setTextSize(size/3);
            correctWidth(tv);
        }
    }

    private void init(int lines) {
        setOrientation(VERTICAL);
        this.lines = new TextView[lines];
        if (isInEditMode()) {
            return;
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(MAX_LINE_LENGTH);
        for (int i = 0; i < lines; i++) {
            TextView tv = new TextView(getContext());
            tv.setFilters(filterArray);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            int color = Color.rgb((int) (255 * Math.random()),
                    (int) (255 * Math.random()),
                    (int) (255 * Math.random()));
            tv.setBackgroundColor(color);
            addView(tv, i, params);
            this.lines[i] = tv;
        }
    }

    public void correctWidth(TextView textView) {
        Paint paint = new Paint();
        Rect bounds = new Rect();
        int desiredWidth = textView.getMeasuredWidth();

        paint.setTypeface(textView.getTypeface());
        float textSize = 6;
        paint.setTextSize(textSize);
        String text = String.format(Locale.getDefault(), "%0" + MAX_LINE_LENGTH + "d", 0);

        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.width() < desiredWidth) {
            textSize++;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 1);
    }

    public void setText(int line, String text) {
        lines[line].setText(text);
    }

}
