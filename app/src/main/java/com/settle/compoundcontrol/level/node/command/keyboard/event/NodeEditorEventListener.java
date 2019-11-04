package com.settle.compoundcontrol.level.node.command.keyboard.event;

import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;

public interface NodeEditorEventListener {
    public void saveLines(CommandLineState[] lines);

    public void closeFragment();
}
