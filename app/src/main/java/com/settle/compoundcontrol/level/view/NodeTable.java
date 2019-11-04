package com.settle.compoundcontrol.level.view;

import android.content.Context;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.settle.compoundcontrol.level.config.LevelConfig;
import com.settle.compoundcontrol.level.node.command.view.CommandNodeView;
import com.settle.compoundcontrol.level.node.iocolumn.view.InputColumnView;
import com.settle.compoundcontrol.level.node.iocolumn.view.OutputColumnView;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.port.BidirectionalPortView;
import com.settle.compoundcontrol.level.state.CommandNodeGameState;
import com.settle.compoundcontrol.level.state.GameState;
import com.settle.compoundcontrol.level.state.InputColumnGameState;
import com.settle.compoundcontrol.level.state.NodeGameState;
import com.settle.compoundcontrol.level.state.OutputColumnGameState;

import java.util.HashSet;
import java.util.Set;


public class NodeTable extends TableLayout {
    private static final TableRow.LayoutParams ROW_PARAMS = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private static final TableRow.LayoutParams VIEW_PARAMS = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

    private HashSet<NodeView> nodeViews;
    protected NodeView[][] nodeGrid;

    public NodeTable(Context context, GameState state) {
        super(context);
        init(state);
    }

    public NodeTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        LevelConfig config = new LevelConfig(2, 3);
//        state.setInput(new IOColumn[]{
//                new IOColumn(0, new int[]{1, 2, 3, 4, 5})
//        });
//        state.setOutput(new IOColumn[]{
//                new IOColumn(1, new int[]{1, 2, 3, 4, 5})
//        });
        GameState state = new GameState(config);
        init(state);
    }

    protected void init(GameState state) {
        nodeViews = new HashSet<>();
        setPadding(1000, 1000, 1000, 1000);

        // Make node grid row by row
        makeNodeViews(state);
        makeRows(state);

        // Make Input row
        addInputRow(state);

        // Make output row
        addOutputRow(state);
    }

    public Set<NodeView> getNodeViews() {
        return nodeViews;
    }

    private void makeNodeViews(GameState state) {
    /*
        Populate the 2D matrix of NodeView objects and add them to the layout. Also create ports
        between neighboring nodes and constrain them to their neighbors.
     */
        nodeGrid = new NodeView[state.getRows()][state.getColumns()];
        for (int r = 0; r < state.getRows(); r++) {
            for (int c = 0; c < state.getColumns(); c++) {
                NodeGameState gs = state.getNode(r, c);
                nodeGrid[r][c] = makeNodeView(gs);
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

    private NodeView makeNodeView(NodeGameState state) {
        NodeView view;
        if (state == null) {
            state = new CommandNodeGameState();
        }
        if (state instanceof CommandNodeGameState) {
            view = new CommandNodeView(getContext(), (CommandNodeGameState) state);
        } else {
            throw new RuntimeException("Unrecognized node state type.");
        }
        nodeViews.add(view);
        return view;
    }

    protected void makeRows(GameState state) {
        for (int r = 0; r < state.getRows(); r++) {
            if (r > 0) {
                addPortRow(state, r);
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

    protected void addPortRow(GameState state, int row_below) {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(ROW_PARAMS);
        for (int i = 0; i < state.getColumns(); i++) {
            BidirectionalPortView port = nodeGrid[row_below][i].getPort(ConstraintSet.TOP);
            TableRow.LayoutParams p = new TableRow.LayoutParams(2 * i);
            row.addView(port, p);
        }
        addView(row, ROW_PARAMS);
    }

    private void addInputRow(GameState state) {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(ROW_PARAMS);
        TableRow port_row = new TableRow(getContext());
        port_row.setLayoutParams(ROW_PARAMS);
        View[] views = new View[state.getColumns()];
        for (int i = 0; i < state.getColumns(); i++) {
            InputColumnGameState input = state.getInput(i);
            if (input == null) {
                continue;
            }
            InputColumnView view = new InputColumnView(getContext(), input);
            views[i] = view;
            nodeViews.add(view);
        }


        int node_row = 0;
        for (int i = 0; i < state.getColumns(); i++) {
            View ioc, port;
            if (views[i] == null) {
                ioc = new Space(getContext());
                port = new Space(getContext());
            } else {
                ioc = views[i];
                port = makePort((InputColumnView) ioc, nodeGrid[node_row][i], LinearLayout.VERTICAL);
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
        addView(row, 0, ROW_PARAMS);
        addView(port_row, 1, ROW_PARAMS);
    }

    private void addOutputRow(GameState state) {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(ROW_PARAMS);
        TableRow port_row = new TableRow(getContext());
        port_row.setLayoutParams(ROW_PARAMS);
        View[] views = new View[state.getColumns()];
        for (int i = 0; i < state.getColumns(); i++) {
            OutputColumnGameState output = state.getOutput(i);
            if (output == null) {
                continue;
            }
            OutputColumnView view = new OutputColumnView(getContext(), output);
            views[i] = view;
            nodeViews.add(view);
        }


        int node_row = state.getRows() - 1;
        for (int i = 0; i < state.getColumns(); i++) {
            View ioc, port;
            if (views[i] == null) {
                ioc = new Space(getContext());
                port = new Space(getContext());
            } else {
                ioc = views[i];
                port = makePort((OutputColumnView) ioc, nodeGrid[node_row][i], LinearLayout.VERTICAL);
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
        addView(port_row, ROW_PARAMS);
        addView(row, ROW_PARAMS);
    }

    public void update() {
        // Updates nodes and IOColumnViews.
        for (NodeView view : nodeViews) {
            view.update();
        }
        invalidate();
    }
}
