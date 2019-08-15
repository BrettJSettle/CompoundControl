package com.settle.compoundcontrol;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.otaliastudios.zoom.ZoomLayout;

import java.util.HashMap;

public class NodeGrid extends TableLayout {
    protected NodeView[][] grid;

    public NodeGrid(Context context) {
        super(context);
        init(null);
    }

    public NodeGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

    }

    private NodeView makeNodeView(int row, int column, NodeState nodeState) {
        NodeView view = new NodeView(getContext());
        return view;
    }

    protected void makeGrid(LevelState state) {
        setBackgroundColor(Color.YELLOW);
        TableRow input_row = new TableRow(getContext());
        input_row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        for (IOColumn input : state.getInput()){
            IOColumnView view = makeInput(input);
            input_row.addView(view, input.getColumn());
        }
        addView(input_row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        int rows = state.getRows(), columns = state.getColumns();
        grid = new NodeView[rows][columns];
        if (state.getNodes() != null) {
            for (NodeState nodeState : state.getNodes()) {
                int row = nodeState.getRow();
                int column = nodeState.getColumn();
                grid[row][column] = makeNodeView(row, column, nodeState);
            }
        }
        for (int r = 0; r < rows; r++) {
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int c = 0; c < columns; c++) {
                if (grid[r][c] == null) {
                    grid[r][c] = makeNodeView(r, c, null);
                }
                row.addView(grid[r][c], c);
            }
            addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

        TableRow output_row = new TableRow(getContext());
        output_row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        for (IOColumn output : state.getOutput()){
            IOColumnView view = makeOutput(output);
            output_row.addView(view, output.getColumn());
        }
        addView(output_row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }

    private IOColumnView makeInput(IOColumn input){
        IOColumnView view = new IOColumnView(getContext());
        view.setData("IN", input);
        return view;
    }
    private IOColumnView makeOutput(IOColumn input){
        IOColumnView view = new IOColumnView(getContext());
        view.setData("OUT", input);
        return view;
    }

    private void arrangeNode(ConstraintSet set, int row, int column){
        NodeView nv = grid[row][column];
        NodeView left = grid[row][column - 1];
        NodeView up = grid[row - 1][column];

        set.connect(nv.getId(), ConstraintSet.LEFT, left.getId(), ConstraintSet.RIGHT);
        set.connect(nv.getId(), ConstraintSet.TOP, up.getId(), ConstraintSet.BOTTOM);
    }
}
