package com.settle.compoundcontrol.level.node.command.keyboard.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandTerm extends KeyboardTerm {

    private ArgType[] args;

    public CommandTerm(String text, ArgType... args) {
        super(TermType.COMMAND, text);
        this.args = args;
    }

    public Set<? extends TermType> getValidTermTypes(List<KeyboardTerm> params) {
        Set<TermType> types = new HashSet<>();
        // No arguments accepted.
        if (args.length == 0) {
            return types;
        }

        if (params.isEmpty()) {
            // Get term types for first argument.
            types.addAll(argToTermTypes(args[0]));
            return types;
        }
        ArrayList<TermType> terms = new ArrayList<>();
        boolean lastWasNumber = false;
        for (KeyboardTerm term : params) {
            if (term instanceof NumberTerm) {
                if (!lastWasNumber) {
                    lastWasNumber = true;
                    terms.add(term.getType());
                }
                continue;
            } else {
                lastWasNumber = false;
            }
            terms.add(term.getType());
        }

        // Enable numbers if the last term was a number
        if (!terms.isEmpty() && terms.get(terms.size() - 1) == TermType.NUMBER) {
            if (getEndNumberLength(params) < 3) {
                types.add(TermType.NUMBER);
            }
        }

        System.out.println("PARAMS");
        for (KeyboardTerm param : params) {
            System.out.println(param);
        }

        System.out.println("TYPES");
        for (TermType term : terms) {
            System.out.println(term);
        }

        System.out.println("ARGS");
        for (ArgType arg : args) {
            System.out.println(arg);
        }

        if (terms.size() < args.length) {
            types.addAll(argToTermTypes(args[terms.size()]));
        }

        return types;
    }

    private int getEndNumberLength(List<KeyboardTerm> params) {
        int length = 0;
        for (int i = params.size() - 1; i >= 0; i--) {
            if (params.get(i) instanceof NumberTerm) {
                length++;
            } else {
                break;
            }
        }
        return length;
    }
}
