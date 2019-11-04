package com.settle.compoundcontrol.level.state;

public class InputColumnGameState extends ColumnGameState {
    public InputColumnGameState(String name, int[] values) {
        super(name, values);
    }

    @Override
    public void step() {
        // TODO: try to write to node.
        if (isActive()) {
            index++;
        }
        super.step();
    }
}
