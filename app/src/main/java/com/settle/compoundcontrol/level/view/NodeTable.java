package com.settle.compoundcontrol.level.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.settle.compoundcontrol.level.node.command.view.CommandNodeView;
import com.settle.compoundcontrol.level.node.iocolumn.view.IOColumnView;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.port.BidirectionalPortView;
import com.settle.compoundcontrol.level.state.IOColumn;
import com.settle.compoundcontrol.level.state.LevelState;
import com.settle.compoundcontrol.level.state.NodeState;

import java.util.HashSet;
import java.util.Set;


public class NodeTable extends TableLayout {
    private static final TableRow.LayoutParams ROW_PARAMS = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private static final TableRow.LayoutParams VIEW_PARAMS = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

    private HashSet<NodeView> nodeViews;
    protected NodeView[][] nodeGrid;
    protected LevelState state;

    public NodeTable(Context context, LevelState state) {
        super(context);
        init(state);
    }

    public NodeTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        LevelState state = new LevelState(2, 3);
        state.setInput(new IOColumn[]{
                new IOColumn(0, new int[]{1, 2, 3, 4, 5})
        });
        state.setOutput(new IOColumn[]{
                new IOColumn(1, new int[]{1, 2, 3, 4, 5})
        });
        init(state);
    }

    protected void init(LevelState state) {
        this.state = state;
        nodeViews = new HashSet<>();
        setPadding(1000, 1000, 1000, 1000);

        // Make node grid row by row
        makeNodeViews();
        makeRows();

        // Make Input row
        addIORow(state.getInput(), true);
        // Make output row
        addIORow(state.getOutput(), false);
    }

    public Set<NodeView> getNodeViews() {
        return nodeViews;
    }

    private void makeNodeViews() {
    /*
        Populate the 2D matrix of NodeView objects and add them to the layout. Also create ports
        between neighboring nodes and constrain them to their neighbors.
     */
        nodeGrid = new NodeView[state.getRows()][state.getColumns()];
        makeSpecialNodeViews(state.getNodes());
        for (int r = 0; r < state.getRows(); r++) {
            for (int c = 0; c < state.getColumns(); c++) {
                if (nodeGrid[r][c] == null) {
                    nodeGrid[r][c] = makeNodeView(null);
                }
                // Add ports to top and left nodes
                if (r > 0) {
                    makePort(nodeGrid[r - 1][c], nodeGrid[r][c], LinearLayout.VERTICAL);
                }
                if (c > 0) {
                    makePort(nodeGrid[r][c - 1], nodeGrid[r][c], LinearLayout.HORIZONTAL);
                }
            }
        }
    }

    private BidirectionalPortView makePort(NodeView a, NodeView b, int port_directions) {
        return new BidirectionalPortView(getContext(), a, b, port_directions);
    }

    private void makeSpecialNodeViews(NodeState[] nodes) {
        if (nodes == null) {
            return;
        }
        for (NodeState nodeState : nodes) {
            int row = nodeState.getRow();
            int column = nodeState.getColumn();
            nodeGrid[row][column] = makeNodeView(nodeState);
        }
    }

    private NodeView makeNodeView(NodeState nodeState) {
        NodeView view;
        if (nodeState == null) {
            view = new CommandNodeView(getContext());
        } else {
            switch (nodeState.getType()) {
                case STACK:
                    // TODO
                case DISABLED:
                    // TODO
                case COMMAND:
                default:
                    view = new CommandNodeView(getContext());
            }
        }
        int color = Color.rgb((int) (255 * Math.random()),
                (int) (255 * Math.random()),
                (int) (255 * Math.random()));
        view.setBackgroundColor(color);
        nodeViews.add(view);
        return view;
    }

    protected void makeRows() {
        for (int r = 0; r < state.getRows(); r++) {
            if (r > 0) {
                addPortRow(r);
            }
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(ROW_PARAMS);
            for (NodeView node : nodeGrid[r]) {
                row.addView(node, VIEW_PARAMS);
                BidirectionalPortView right = node.getPort(ConstraintSet.RIGHT);
                if (right != null) {
                    row.addView(right, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
                }
            }
            addView(row, ROW_PARAMS);
        }
    }

    protected void addPortRow(int row_below) {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(ROW_PARAMS);
        for (int i = 0; i < state.getColumns(); i++) {
            BidirectionalPortView port = nodeGrid[row_below][i].getPort(ConstraintSet.TOP);
            TableRow.LayoutParams p = new TableRow.LayoutParams(2 * i);
            row.addView(port, p);
        }
        addView(row, ROW_PARAMS);
    }

    private void addIORow(IOColumn[] columns, boolean input) {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(ROW_PARAMS);
        TableRow port_row = new TableRow(getContext());
        port_row.setLayoutParams(ROW_PARAMS);
        String prefix = input ? "IN " : "OUT ";
        View[] views = new View[state.getColumns()];
        for (int i = 0; i < columns.length; i++) {
            IOColumnView view = new IOColumnView(getContext(), prefix + i, columns[i]);
            views[columns[i].getColumn()] = view;
            nodeViews.add(view);
        }


        int node_row = input ? 0 : state.getRows() - 1;
        for (int i = 0; i < state.getColumns(); i++) {
            View ioc, port;
            if (views[i] == null) {
                ioc = new Space(getContext());
                port = new Space(getContext());
            } else {
                ioc = views[i];
                port = makePort((IOColumnView) ioc, nodeGrid[node_row][i], LinearLayout.VERTICAL);
            }
            port_row.addView(port, VIEW_PARAMS);
            row.addView(ioc, VIEW_PARAMS);
            if (i < state.getColumns() - 1) {
                Space spacer = new Space(getContext());
                row.addView(spacer, VIEW_PARAMS);
                Space spacer2 = new Space(getContext());
                port_row.addView(spacer2, VIEW_PARAMS);
            }

        }
        if (input) {
            addView(row, 0, ROW_PARAMS);
            addView(port_row, 1, ROW_PARAMS);
        } else {
            addView(port_row, ROW_PARAMS);
            addView(row, ROW_PARAMS);
        }
    }
}
