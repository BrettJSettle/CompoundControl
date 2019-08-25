package com.settle.compoundcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TableLayout;
import android.widget.TableRow;

@SuppressLint("ViewConstructor")
public class NodeGrid extends TableLayout {
    LevelState state;
    SparseArray<IOColumnView> inputs = new SparseArray<>(), outputs = new SparseArray<>();
    NodeView[][] nodeGrid;

    public NodeGrid(Context context, LevelState state) {
        super(context);
        init(state);
    }

    public NodeGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        LevelState s = new LevelState(2, 3);
        s.setInput(new IOColumn[]{
                new IOColumn(0, new int[]{1, 2, 3, 4, 5})
        });
        s.setOutput(new IOColumn[]{
                new IOColumn(2, new int[]{1, 2, 3, 4, 5})
        });
        init(s);
    }

    public TableRow addRow() {
        TableRow row = new TableRow(getContext());
        addView(row, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private void init(LevelState state) {
        this.state = state;
        TableRow row1 = addRow();
        TableRow row2 = addRow();
        TableRow row3 = addRow();
        TableRow row4 = addRow();
    }
}
