package com.settle.compoundcontrol.level.node.editor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.node.command.impl.CommandLineState;
import com.settle.compoundcontrol.level.node.command.keyboard.event.NodeEditorEventListener;
import com.settle.compoundcontrol.level.node.command.keyboard.view.CommandNodeKeyboard;
import com.settle.compoundcontrol.level.node.command.view.CommandEditorView;


public class NodeEditor extends Fragment implements View.OnClickListener {

    NodeEditorEventListener callback;
    private CommandEditorView textView;
    private CommandNodeKeyboard keyboard;

    public void setNodeEditorEventListener(NodeEditorEventListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.node_editor_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textView = view.findViewById(R.id.editor_text);
        keyboard = view.findViewById(R.id.editor_keyboard);
        textView.setLineClickHandler(this);
        keyboard.setFragment(this);
        keyboard.setTextView(textView);
    }

    public void closeFragment(boolean save) {
        if (save) {
            callback.saveLines(textView.getStates());
        }
        callback.closeFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        CommandLineState[] states = (CommandLineState[]) bundle.get("states");
        if (states != null) {
            setStates(states);
        }
    }

    public void selectLine(int index) {
        textView.highlightLine(index);
        CommandLineState state = textView.getState(index);
        keyboard.load(state);
    }

    @Override
    public void onClick(View view) {
        int index = (int) view.getTag();
        selectLine(index);
    }

    public void setStates(CommandLineState[] states) {
        int i = 0;
        for (CommandLineState state : states) {
            if (state == null) {
                state = new CommandLineState();
            }
            textView.setState(i++, state);
        }
        selectLine(0);
    }
}
