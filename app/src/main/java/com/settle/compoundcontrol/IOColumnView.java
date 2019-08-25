package com.settle.compoundcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class IOColumnView extends NodeView {
    private static final IOColumn SAMPLE_DATA = new IOColumn(4, new int[]{1, 2, 3});
    LinearLayout table;
    ScrollView scroll_view;
    ArrayList<TextView> rows = new ArrayList<>();
    TextView titleView;

    public IOColumnView(Context context) {
        super(context);
        init("SAMPLE", SAMPLE_DATA);
    }

    public IOColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init("SAMPLE", SAMPLE_DATA);
    }

    public IOColumnView(Context context, String name, IOColumn column) {
        super(context);
        init(name, column);
    }

    private void init(String name, IOColumn column) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.iocolumn_view, this);
        titleView = findViewById(R.id.column_title);
        table = findViewById(R.id.io_column_table);
        scroll_view = findViewById(R.id.iocolumn_scroll_view);
        setName(name);
        setData(column);
    }

    private void setName(String name) {
        titleView.setText(name);
    }

    public void setData(IOColumn data) {
        table.removeAllViews();
        for (int val : data.getValues()) {
            TextView tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setText(String.format(Locale.getDefault(), "%s", val));
            table.addView(tv);
            rows.add(tv);
        }
    }
}
