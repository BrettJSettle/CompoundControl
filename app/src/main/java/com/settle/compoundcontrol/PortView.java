package com.settle.compoundcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import java.util.Locale;


public class PortView extends AppCompatTextView {
    enum Direction {
        LEFT, UP, RIGHT, DOWN
    }
    private Direction direction;
    private Integer value = null;
    private RotateDrawable arrow;

    public PortView(Context context) {
        super(context);
        init(null, 0);
    }

    public PortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PortView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setValue(Integer value){
        this.value = value;
        update();
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PortView, defStyle, 0);

        String dir_str = a.getString(R.styleable.PortView_direction);
        if (dir_str == null){
            dir_str = "0";
        }
        int index = Integer.valueOf(dir_str);
        direction = Direction.values()[index];
        System.out.println(String.format(Locale.US, "%d %s", index, direction.toString()));
        a.recycle();

        update();
    }

    public void update(){
        RotateDrawable left = null, top = null, right = null, bottom = null;
        int id = value == null ? R.drawable.arrow_empty : R.drawable.arrow_occupied;
        Drawable arrow_empty = getContext().getDrawable(id);

        // direction is the way the arrow is facing.
        // drawable name is the side of the text that the arrow is on.
        switch (direction) {
            case UP:
                right = new RotateDrawable();
                right.setDrawable(arrow_empty);
                right.setFromDegrees(0);
                right.setToDegrees(-90);
                setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                setMinimumWidth(200);
                arrow = right;
                break;
            case DOWN:
                left = new RotateDrawable();
                left.setDrawable(arrow_empty);
                left.setFromDegrees(0);
                left.setToDegrees(90);
                setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                setMinimumWidth(200);
                arrow = left;
                break;
            case LEFT:
                top = new RotateDrawable();
                top.setDrawable(arrow_empty);
                top.setFromDegrees(0);
                top.setToDegrees(180);
                setMinimumHeight(200);
                arrow = top;
                break;
            case RIGHT:
                bottom = new RotateDrawable();
                bottom.setDrawable(arrow_empty);
                setGravity(Gravity.BOTTOM);
                setMinimumHeight(200);
                arrow = bottom;
                break;
        }

        arrow.setBounds(0, 0, 64, 64);
        arrow.setLevel(10000);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);

//        setText(value == null ? "" : String.format(Locale.US, "%d", value));
    }

    public Integer getValue() {
        return value;
    }
}
