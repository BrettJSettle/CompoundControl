package com.settle.compoundcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class IOColumnView extends TableLayout {
    ArrayList<TextView> rows = new ArrayList<>();
    TextView titleView;

    public IOColumnView(Context context) {
        super(context);
        init(null);
    }

    public IOColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.iocolumn_view, this);
        titleView = findViewById(R.id.column_title);
        setBackgroundColor(Color.BLUE);
    }

    public void setData(String title, IOColumn data){
        titleView.setText(title);
        for (int val :data.getValues()){
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tv = new TextView(getContext());
            tv.setText(String.format(Locale.getDefault(), "%s", val));
            row.addView(tv, 0);
            addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            rows.add(tv);
        }
    }
}
