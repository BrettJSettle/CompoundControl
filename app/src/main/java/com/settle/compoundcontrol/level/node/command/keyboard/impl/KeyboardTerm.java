package com.settle.compoundcontrol.level.node.command.keyboard.impl;

import java.util.ArrayList;
import java.util.List;

public class KeyboardTerm {
    protected String text;
    protected TermType type;

    protected KeyboardTerm(TermType type, String text) {
        this.text = text;
        this.type = type;
        Keyboard.addTerm(type, this);
    }

    protected List<TermType> argToTermTypes(ArgType e) {
        ArrayList<TermType> types = new ArrayList<>();
        switch (e) {
            case SRC:
                types.add(TermType.NUMBER);
            case DEST:
                types.add(TermType.REGISTER);
                break;
            case LBL:
                types.add(TermType.LABEL);
                break;
        }
        return types;
    }

    public String getText() {
        return text;
    }

    public TermType getType() {
        return type;
    }

    public enum ArgType {
        SRC, DEST, LBL
    }

    public enum TermType {
        COMMAND, REGISTER, NUMBER, LABEL
    }

}
