package com.settle.compoundcontrol.level.state;

import com.settle.compoundcontrol.level.config.NodeState;
import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;
import com.settle.compoundcontrol.level.node.command.keyboard.impl.RegisterTerm;

public class CommandNodeGameState extends NodeGameState {
    public static final CommandNodeGameState EXAMPLE = new CommandNodeGameState();
    private int selectedLine = 0;
    private int acc = 0;
    private int bak = 0;
    private RegisterTerm last = null;
    private Mode mode = Mode.IDLE;
    private CommandLineState[] lines;

    public CommandNodeGameState(NodeState state) {
        this.lines = new CommandLineState[state.getLines()];
        for (int i = 0; i < lines.length; i++) {
            this.lines[i] = new CommandLineState();
        }
    }

    public CommandNodeGameState() {
        this.lines = new CommandLineState[NodeState.DEFAULT_NUM_LINES];
        for (int i = 0; i < lines.length; i++) {
            this.lines[i] = new CommandLineState();
        }
    }

    public Integer getSelectedLine() {
        return selectedLine;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public int getBak() {
        return bak;
    }

    public void setBak(int bak) {
        this.bak = bak;
    }

    public Mode getMode() {
        return mode;
    }

    public RegisterTerm getLast() {
        return last;
    }

    // Returns a copy of the line at index i.
    public CommandLineState getLine(int i) {
        return lines[i].clone();
    }

    // Overwrites the line at index i.
    public void setLine(int i, CommandLineState line) {
        lines[i] = line;
    }

    public int lineCount() {
        return lines.length;
    }

    public CommandLineState[] getLines() {
        CommandLineState[] lines_copy = new CommandLineState[lineCount()];
        for (int i = 0; i < lines_copy.length; i++) {
            lines_copy[i] = getLine(i);
        }
        return lines_copy;
    }

    public void setLines(CommandLineState[] new_lines) {
        for (int i = 0; i < new_lines.length; i++) {
            setLine(i, new_lines[i]);
        }
    }

    private void nextLine() {
        if (isEmpty()) {
            return;
        }
        selectedLine = (selectedLine + 1) % lines.length;
        while (lines[selectedLine].isEmpty()) {
            selectedLine = (selectedLine + 1) % lines.length;
        }
    }

    public boolean isEmpty() {
        for (CommandLineState line : lines) {
            if (!line.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void stop(boolean reset) {
        super.stop(reset);
        selectedLine = 0;
        acc = 0;
        bak = 0;
        last = null;
        mode = Mode.IDLE;
        if (reset) {
            for (CommandLineState line : lines) {
                line.clear();
            }
        }
    }

    private boolean runLine() {
        // Return true if the command completed.
        CommandLineState line = lines[selectedLine];

        return false;
    }

    @Override
    public void step() {
        if (isActive()) {
            if (runLine()) {
                nextLine();
            }
        }
        super.step();
    }

    public enum Mode {
        IDLE,
        RUN,
        WRITE,
        READ
    }
}
