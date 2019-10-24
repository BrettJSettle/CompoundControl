package com.settle.compoundcontrol.level.node.command.keyboard.impl;

import java.util.ArrayList;
import java.util.HashMap;

public class Keyboard {

    public static final Keyboard INSTANCE = new Keyboard();
    private HashMap<KeyboardTerm.TermType, ArrayList<KeyboardTerm>> termMap = new HashMap<>();

    public static void initialize() {
        for (KeyboardTerm.TermType term : KeyboardTerm.TermType.values()) {
            INSTANCE.termMap.put(term, new ArrayList<KeyboardTerm>());
        }
        new CommandTerm("NOP");
        new CommandTerm("SWP");
        new CommandTerm("SAV");
        new CommandTerm("NEG");
        new CommandTerm("ADD", KeyboardTerm.ArgType.SRC);
        new CommandTerm("SUB", KeyboardTerm.ArgType.SRC);
        new CommandTerm("JMP", KeyboardTerm.ArgType.LBL);
        new CommandTerm("JEZ", KeyboardTerm.ArgType.LBL);
        new CommandTerm("JNZ", KeyboardTerm.ArgType.LBL);
        new CommandTerm("JGZ", KeyboardTerm.ArgType.LBL);
        new CommandTerm("JLZ", KeyboardTerm.ArgType.LBL);
        new CommandTerm("JRO", KeyboardTerm.ArgType.SRC);
        new CommandTerm("MOV", KeyboardTerm.ArgType.SRC, KeyboardTerm.ArgType.DEST);

        // Registers
        new RegisterTerm("ACC");
        new RegisterTerm("NIL");
        new RegisterTerm("LEFT");
        new RegisterTerm("RIGHT");
        new RegisterTerm("UP");
        new RegisterTerm("DOWN");
        new RegisterTerm("ANY");
        new RegisterTerm("LAST");

        // Numbers
        new NumberTerm(0);
        new NumberTerm(1);
        new NumberTerm(2);
        new NumberTerm(3);
        new NumberTerm(4);
        new NumberTerm(5);
        new NumberTerm(6);
        new NumberTerm(7);
        new NumberTerm(8);
        new NumberTerm(9);
    }

    public static KeyboardTerm.TermType[] getTabs() {
        return KeyboardTerm.TermType.values();
    }

    public static int getNumOptions(KeyboardTerm.TermType type) {
        return INSTANCE.termMap.get(type).size();
    }

    public static KeyboardTerm getTermAt(KeyboardTerm.TermType type, int i) {
        return INSTANCE.termMap.get(type).get(i);
    }

    public static void addTerm(KeyboardTerm.TermType type, KeyboardTerm term) {
        INSTANCE.termMap.get(type).add(term);
    }

}
