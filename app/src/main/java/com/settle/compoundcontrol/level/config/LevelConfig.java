package com.settle.compoundcontrol.level.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class LevelConfig implements Serializable {
    protected int rows = 3, columns = 4;
    protected IOColumn[] input, output;
    protected NodeState[] nodes;

    protected LevelConfig() {

    }

    public LevelConfig(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public static LevelConfig load(InputStream fis) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(fis, LevelConfig.class);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public IOColumn[] getInput() {
        return input;
    }

    public IOColumn[] getOutput() {
        return output;
    }

    public NodeState[] getNodes() {
        return nodes;
    }

    public void setNodes(NodeState[] nodes) {
        this.nodes = nodes;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setOutput(IOColumn[] output) {
        this.output = output;
    }

    public void setInput(IOColumn[] input) {
        this.input = input;
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

    public void save(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path), this);
    }
}
