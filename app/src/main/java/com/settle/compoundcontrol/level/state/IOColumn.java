package com.settle.compoundcontrol.level.state;

import java.io.Serializable;

public class IOColumn implements Serializable {
    private int column;
    private int[] values;

    public IOColumn() {
    }

    public IOColumn(int column, int[] values) {
        this.column = column;
        this.values = values;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }
}
