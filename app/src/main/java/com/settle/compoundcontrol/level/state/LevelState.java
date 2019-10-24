package com.settle.compoundcontrol.level.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class LevelState implements Serializable {
    private int rows = 3, columns = 4;
    private IOColumn[] input, output;
    private NodeState[] nodes;

    public LevelState() {
    }

    public LevelState(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public static LevelState load(InputStream fis) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(fis, LevelState.class);
    }

    public void save(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path), this);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public NodeState[] getNodes() {
        return nodes;
    }
    public void setNodes(NodeState[] nodes) {
        this.nodes = nodes;
    }

    public IOColumn[] getInput() {
        return input;
    }

    public void setInput(IOColumn[] input) {
        this.input = input;
    }

    public IOColumn[] getOutput() {
        return output;
    }

    public void setOutput(IOColumn[] output) {
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
