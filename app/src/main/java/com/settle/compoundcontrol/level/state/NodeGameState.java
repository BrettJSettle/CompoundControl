package com.settle.compoundcontrol.level.state;

import com.settle.compoundcontrol.level.config.NodeState;

public abstract class NodeGameState extends AbstractSteppable {

    public static NodeGameState fromNodeState(NodeState state) {
        switch (state.getType()) {
            case COMMAND:
                return new CommandNodeGameState(state);
            case DISABLED:
            case STACK:
            default:
                return new CommandNodeGameState();
        }
    }
}
