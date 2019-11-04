package com.settle.compoundcontrol.level.state;

import android.support.annotation.Nullable;

public abstract class ColumnGameState extends NodeGameState {
    protected int index = 0;
    protected String name;
    protected int[] values;

    public ColumnGameState(String name, int[] values) {
        this.name = name;
        this.values = values;
    }

    @Override
    public void stop(boolean clear) {
        super.stop(clear);
        index = 0;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public Integer getAt(int i) {
        if (i >= values.length) {
            return null;
        }
        return values[i];
    }

    public int getIndex() {
        return index;
    }
}
