package com.settle.compoundcontrol.level.state;

import java.io.Serializable;

public class NodeState implements Serializable {
    private Type type = Type.COMMAND;
    private int row;
    private int column;

    public NodeState() {
    }

    public NodeState(int row, int column, Type type) {
        this.row = row;
        this.type = type;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type nodeType) {
        this.type = nodeType;
    }

    public enum Type {
        DISABLED, COMMAND, STACK
    }
}


