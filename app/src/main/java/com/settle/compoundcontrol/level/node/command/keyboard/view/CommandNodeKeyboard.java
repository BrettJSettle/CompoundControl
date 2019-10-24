package com.settle.compoundcontrol.level.node.command.keyboard.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;
import com.settle.compoundcontrol.level.node.command.keyboard.impl.Keyboard;
import com.settle.compoundcontrol.level.node.command.keyboard.impl.KeyboardTerm;
import com.settle.compoundcontrol.level.node.command.view.CommandEditorView;
import com.settle.compoundcontrol.level.node.editor.NodeEditor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandNodeKeyboard extends LinearLayout implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    TabLayout tabLayout;
    HashMap<KeyboardTerm.TermType, Tab> tabs;
    GridView page;
    KeyboardAdapter adapter;
    ToggleButton commentToggle, labelToggle;
    Button backspaceButton, clearButton, cancelButton, saveButton;

    CommandEditorView textView;
    NodeEditor fragment;

    public CommandNodeKeyboard(Context context) {
        super(context);
        init(null, 0);
    }

    public CommandNodeKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CommandNodeKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.command_node_keyboard, this);

        tabLayout = findViewById(R.id.keyboard_tabs);
        tabLayout.addOnTabSelectedListener(this);

        page = findViewById(R.id.keyboard_page);
        page.setNumColumns(4);
        adapter = new KeyboardAdapter(getContext(), this);
        page.setAdapter(adapter);
        TextView emptyView = new TextView(getContext());
        emptyView.setText("No valid input here. Delete something, or select a different line/tab.");
        page.setEmptyView(emptyView);

        commentToggle = findViewById(R.id.comment_toggle);
        labelToggle = findViewById(R.id.label_toggle);
        backspaceButton = findViewById(R.id.backspace_button);
        clearButton = findViewById(R.id.clear_button);
        cancelButton = findViewById(R.id.cancel_button);
        saveButton = findViewById(R.id.save_button);

        // Buttons that interact with the node text.
        commentToggle.setOnClickListener(this);
        labelToggle.setOnClickListener(this);
        backspaceButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        // Buttons that interact with the fragment.
        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        makeTabs();
    }

    private void makeTabs() {
        tabs = new HashMap<>();
        for (KeyboardTerm.TermType type : Keyboard.getTabs()) {
            Tab tab = tabLayout.newTab();
            tab.setText(type.toString());
            tab.setTag(type);
            tabs.put(type, tab);
            tabLayout.addTab(tab);
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setInlineLabel(true);
    }

    @Override
    public void onClick(View view) {
        // Buttons dealing with the fragment.
        if (view == cancelButton) {
            fragment.closeFragment(false);
            return;
        } else if (view == saveButton) {
            fragment.closeFragment(true);
            return;
        }

        // Buttons dealing with the current line of the TextView.
        CommandEditorLine line = textView.getSelectedLine();
        if (line == null) {
            return;
        }
        CommandLineState state = line.getState();
        if (view == commentToggle) {
            state.setComment(commentToggle.isChecked());
        } else if (view == labelToggle) {
            int num = (int) line.getTag();
            char l = (char) (65 + num);
            Character label = labelToggle.isChecked() ? l : null;
            state.setLabel(label);
        } else if (view == backspaceButton) {
            state.backspace();
        } else if (view == clearButton) {
            state.clear();
        } else {
            // One of the keyboard keys.
            Button key = (Button) view;
            KeyboardTerm term = (KeyboardTerm) key.getTag();
            state.add(term);
        }
        updateKeyboard(state.getValidTermTypes());
        line.update();
    }

    private void updateKeyboard(Set<KeyboardTerm.TermType> terms) {
        for (Map.Entry<KeyboardTerm.TermType, Tab> t : tabs.entrySet()) {
            boolean valid = terms.contains(t.getKey());
            Tab tab = t.getValue();
            View view = tab.view;
            view.setClickable(valid);
            view.setAlpha(valid ? 1f : .4f);
        }
        // Switch selection to first valid tab.
        Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
        if (tab != null && !((View) tab.view).isClickable()) {
            for (KeyboardTerm.TermType type : KeyboardTerm.TermType.values()) {
                if (terms.contains(type)) {
                    Tab t = tabs.get(type);
                    if (t != null) {
                        t.select();
                        break;
                    }
                }
            }
        }
        adapter.setValidTermTypes(terms);
    }

    @Override
    public void onTabSelected(Tab tab) {
        KeyboardTerm.TermType term = (KeyboardTerm.TermType) tab.getTag();
        adapter.setType(term);
    }

    @Override
    public void onTabUnselected(Tab tab) {

    }

    @Override
    public void onTabReselected(Tab tab) {

    }

    public void setComment(boolean isComment) {
        commentToggle.setChecked(isComment);
    }

    public void setHasLabel(boolean hasLabel) {
        labelToggle.setChecked(hasLabel);
    }

    public void setTextView(CommandEditorView textView) {
        this.textView = textView;
    }

    public void setFragment(NodeEditor fragment) {
        this.fragment = fragment;
    }

    public void load(CommandLineState state) {
        setComment(state.isComment());
        setHasLabel(state.hasLabel());
        updateKeyboard(state.getValidTermTypes());
    }
}
