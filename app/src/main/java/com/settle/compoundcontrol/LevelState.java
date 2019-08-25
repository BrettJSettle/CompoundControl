package com.settle.compoundcontrol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

class IOColumn implements Serializable{
    private int column;
    private int[] values;

    public IOColumn(){}

    IOColumn(int column, int[] values) {
        this.column = column;
        this.values = values;
    }

    int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }
}

class NodeState implements Serializable{
    enum Type {
        DISABLED, COMMAND, STACK
    }

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

    public void setRow(int row) {
        this.row = row;
    }

    int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    int getColumn() {
        return column;
    }

    public void setType(Type nodeType) {
        this.type = nodeType;
    }

    Type getType() {
        return type;
    }
}


public class LevelState implements Serializable {
    private int rows = 3, columns = 4;
    private IOColumn[] input, output;
    private NodeState[] nodes;

    public LevelState() {
    }

    LevelState(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
    static LevelState load(InputStream fis) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(fis, LevelState.class);
    }

    public void save(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path), this);
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    int getRows() {
        return rows;
    }

    int getColumns() {
        return columns;
    }

    private void setColumns(int columns) {
        this.columns = columns;
    }

    NodeState[] getNodes() {
        return nodes;
    }
    public void setNodes(NodeState[] nodes) {
        this.nodes = nodes;
    }

    IOColumn[] getInput() {
        return input;
    }

    void setInput(IOColumn[] input) {
        this.input = input;
    }

    IOColumn[] getOutput() {
        return output;
    }

    void setOutput(IOColumn[] output) {
        this.output = output;
    }

    public void print() {
        ObjectMapper mapper = new ObjectMapper();
        try {

            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            String value = mapper.writeValueAsString(this);
            System.out.println("----------------");
            System.out.println(value);
            System.out.println("----------------");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
