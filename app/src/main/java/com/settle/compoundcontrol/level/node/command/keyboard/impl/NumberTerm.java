package com.settle.compoundcontrol.level.node.command.keyboard.impl;

public class NumberTerm extends KeyboardTerm {
    public NumberTerm(int number) {
        super(TermType.NUMBER, String.valueOf(number));
    }

    public NumberTerm(String number) {
        super(TermType.NUMBER, number);
    }
}
