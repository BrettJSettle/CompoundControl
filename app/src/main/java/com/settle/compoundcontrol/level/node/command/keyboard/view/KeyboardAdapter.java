package com.settle.compoundcontrol.level.node.command.keyboard.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.settle.compoundcontrol.level.node.command.keyboard.impl.Keyboard;
import com.settle.compoundcontrol.level.node.command.keyboard.impl.KeyboardTerm;

import java.util.HashSet;
import java.util.Set;

public class KeyboardAdapter extends ArrayAdapter<KeyboardTerm> {
    private KeyboardTerm.TermType type;
    private View.OnClickListener listener;
    private Set<KeyboardTerm.TermType> validTypes = new HashSet<>();

    public KeyboardAdapter(Context context, View.OnClickListener listener) {
        super(context, 0, new KeyboardTerm[0]);
        this.type = KeyboardTerm.TermType.COMMAND;
        Keyboard.initialize();
        this.listener = listener;
    }

    public void setType(KeyboardTerm.TermType type) {
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (type == null || !validTypes.contains(type)) {
            return 0;
        }
        return Keyboard.getNumOptions(type);
    }

    @Override
    public int getPosition(@Nullable KeyboardTerm item) {
        return -1;
    }

    @Nullable
    @Override
    public KeyboardTerm getItem(int position) {
        return Keyboard.getTermAt(type, position);
    }

    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        KeyboardTerm term = getItem(position);
        Button rowView;

        if (convertView == null) {
            rowView = new Button(getContext());
        } else {
            rowView = (Button) convertView;
        }
        if (term == null) {
            throw new RuntimeException("Term should not be null!");
        }
        rowView.setText(term.getText());
        rowView.setTag(term);

        rowView.setOnClickListener(listener);

        return rowView;
    }

    public void setValidTermTypes(Set<KeyboardTerm.TermType> types) {
        validTypes = types;
        notifyDataSetChanged();
    }
}
