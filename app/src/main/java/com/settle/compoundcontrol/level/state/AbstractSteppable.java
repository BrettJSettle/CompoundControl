package com.settle.compoundcontrol.level.state;

public abstract class AbstractSteppable {
    protected boolean active = false;
    protected int step = 0;

    public boolean isActive() {
        return active;
    }

    // Return true if this step is the activation step.
    public void step() {
        if (active) {
            step++;
        }
        active = true;
    }

    public void stop(boolean reset) {
        active = false;
        step = 0;
    }
}
