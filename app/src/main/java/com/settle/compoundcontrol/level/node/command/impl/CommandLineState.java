package com.settle.compoundcontrol.level.node.command.impl;

import android.support.annotation.NonNull;

import com.settle.compoundcontrol.level.node.command.keyboard.impl.CommandTerm;
import com.settle.compoundcontrol.level.node.command.keyboard.impl.KeyboardTerm;
import com.settle.compoundcontrol.level.node.command.keyboard.impl.NumberTerm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandLineState implements Cloneable {
    private List<KeyboardTerm> terms;
    private boolean comment = false;
    private Character label;

    public CommandLineState() {
        terms = new ArrayList<>();
    }

    public void setLabel(Character label) {
        this.label = label;
    }

    public void backspace() {
        if (terms.size() == 0) {
            return;
        }
        KeyboardTerm lastTerm = terms.get(terms.size() - 1);
        terms.remove(lastTerm);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder command = new StringBuilder();
        if (comment) {
            command.append("#");
        }
        if (label != null) {
            command.append(label);
            command.append(":");
        }
        for (int i = 0; i < terms.size(); i++) {
            KeyboardTerm t = terms.get(i);
            command.append(t.getText());
            boolean isNum = t instanceof NumberTerm;
            boolean nextNum = terms.size() > i + 1 && terms.get(i + 1) instanceof NumberTerm;
            if (!(isNum && nextNum)) {
                command.append(" ");
            }
        }
        return command.toString();
    }

    public Set<KeyboardTerm.TermType> getValidTermTypes() {
        Set<KeyboardTerm.TermType> types = new HashSet<>();
        if (terms.isEmpty()) {
            types.add(KeyboardTerm.TermType.COMMAND);
            return types;
        }
        CommandTerm cmd = (CommandTerm) terms.get(0);

        List<KeyboardTerm> args = terms.subList(1, terms.size());
        types.addAll(cmd.getValidTermTypes(args));
        return types;
    }

    public void add(KeyboardTerm term) {
        terms.add(term);
    }

    public void clear() {
        comment = false;
        label = null;
        terms.clear();
    }

    public boolean isEmpty() {
        return terms.isEmpty() && label == null && !comment;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public CommandLineState clone() {
        CommandLineState state = new CommandLineState();
        for (KeyboardTerm term : terms) {
            state.add(term);
        }
        state.setLabel(label);
        state.setComment(comment);
        return state;
    }

    public boolean hasLabel() {
        return label != null;
    }
}
