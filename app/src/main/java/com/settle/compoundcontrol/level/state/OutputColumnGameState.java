package com.settle.compoundcontrol.level.state;

import android.support.annotation.Nullable;

public class OutputColumnGameState extends ColumnGameState {
    private Integer[] actual;

    public OutputColumnGameState(String name, int[] values) {
        super(name, values);
        actual = new Integer[values.length];
    }

    public void stop(boolean clear) {
        super.stop(clear);
        actual = new Integer[values.length];
    }

    public boolean add(int answer) {
        actual[index] = answer;
        boolean correct = answer == values[index];
        index++;
        return correct;
    }

    @Override
    public void step() {
        if (isActive()) {
            // TODO: Wait for write.
        }
        super.step();
    }

    @Nullable
    public Integer getActual(int i) {
        if (i >= actual.length) {
            return null;
        }
        return actual[i];
    }
}
