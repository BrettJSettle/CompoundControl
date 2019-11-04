package com.settle.compoundcontrol.level.state;

import android.util.SparseArray;

import com.settle.compoundcontrol.level.config.IOColumn;
import com.settle.compoundcontrol.level.config.LevelConfig;
import com.settle.compoundcontrol.level.config.NodeState;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameState extends AbstractSteppable {

    private NodeGameState[][] nodeStates;
    private SparseArray<InputColumnGameState> inputs;
    private SparseArray<OutputColumnGameState> outputs;

    public GameState(LevelConfig config) {
        nodeStates = new NodeGameState[config.getRows()][config.getColumns()];
        for (NodeState node : config.getNodes()) {
            NodeGameState node_gs = NodeGameState.fromNodeState(node);
            nodeStates[node.getRow()][node.getColumn()] = node_gs;
        }
        for (int row = 0; row < config.getRows(); row++) {
            for (int column = 0; column < config.getColumns(); column++) {
                if (nodeStates[row][column] == null) {
                    nodeStates[row][column] = new CommandNodeGameState();
                }
            }
        }
        inputs = new SparseArray<>();
        int i = 0;
        for (IOColumn input : config.getInput()) {
            InputColumnGameState column = new InputColumnGameState("IN " + i, input.getValues());
            inputs.put(input.getColumn(), column);
            i++;
        }
        outputs = new SparseArray<>();
        i = 0;
        for (IOColumn output : config.getOutput()) {
            OutputColumnGameState column = new OutputColumnGameState("OUT " + i, output.getValues());
            outputs.put(output.getColumn(), column);
            i++;
        }
    }

    public int getRows() {
        return nodeStates.length;
    }

    public int getColumns() {
        return nodeStates[0].length;
    }

    private Set<AbstractSteppable> getAllStates() {
        Set<AbstractSteppable> states = new HashSet<>();
        for (int i = 0; i < inputs.size(); i++) {
            int key = inputs.keyAt(i);
            states.add(inputs.get(key));
        }
        for (int i = 0; i < outputs.size(); i++) {
            int key = outputs.keyAt(i);
            states.add(outputs.get(key));
        }
        for (NodeGameState[] row : nodeStates) {
            Collections.addAll(states, row);
        }
        return states;
    }

    public void stop(boolean clear) {
        super.stop(clear);
        for (AbstractSteppable steppable : getAllStates()) {
            steppable.stop(clear);
        }
    }

    public void step() {
        // Step for all nodes and io columns.
        for (AbstractSteppable steppable : getAllStates()) {
            steppable.step();
        }
        if (isActive()) {
            step++;
        }
        active = true;
    }

    public NodeGameState getNode(int row, int col) {
        return nodeStates[row][col];
    }

    public InputColumnGameState getInput(int col) {
        return inputs.get(col);
    }

    public OutputColumnGameState getOutput(int col) {
        return outputs.get(col);
    }

}
