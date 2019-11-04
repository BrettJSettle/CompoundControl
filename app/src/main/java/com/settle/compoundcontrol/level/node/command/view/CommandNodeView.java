package com.settle.compoundcontrol.level.node.command.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level.node.view.NodeView;
import com.settle.compoundcontrol.level.state.CommandNodeGameState;
import com.settle.compoundcontrol.level.state.NodeGameState;

public class CommandNodeView extends NodeView implements View.OnClickListener {
    private TextView textArea;
    private TextView accView, bakView, modeView, lastView, idleView;
    private Rect highlightRect;
    private Paint highlightPaint;
    private Integer highlightedLine = 0;

    public CommandNodeView(Context context) {
        super(context);
    }

    public CommandNodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommandNodeView(Context context, CommandNodeGameState state) {
        super(context, state);
    }

    @Override
    public void init(NodeGameState state) {
        super.init(state);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View nodeView = inflater.inflate(R.layout.execution_node_view, this, false);
        addView(nodeView, new ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textArea = nodeView.findViewById(R.id.command_editor);
        accView = nodeView.findViewById(R.id.accText);
        bakView = nodeView.findViewById(R.id.bakText);
        modeView = nodeView.findViewById(R.id.modeText);
        lastView = nodeView.findViewById(R.id.lastText);
        idleView = nodeView.findViewById(R.id.idleText);

        highlightRect = new Rect(0, 0, getWidth(), textArea.getLineHeight());
        highlightPaint = new Paint();
        highlightPaint.setColor(Color.parseColor("#77CCCCCC"));
        highlightPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onClick(View view) {
        if (!(view instanceof TextView)) {
            return;
        }
        view.setBackgroundColor(Color.RED);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (highlightedLine != null) {
            highlightRect.set(0, 0, textArea.getWidth(), textArea.getLineHeight());
            highlightRect.offsetTo(6, 6 + textArea.getLineHeight() * highlightedLine);
            canvas.drawRect(highlightRect, highlightPaint);
        }
    }

    @Override
    public void update() {
        // Update the views with values from the state.
        CommandNodeGameState gs = (CommandNodeGameState) state;
        String acc = String.valueOf(gs.getAcc());
        accView.setText(acc);
        String bak = String.valueOf(gs.getBak());
        bakView.setText(bak);
        String last = String.valueOf(gs.getLast());
        lastView.setText(last);
        String mode = String.valueOf(gs.getMode());
        modeView.setText(mode);
        String idle = String.valueOf(0);
        idleView.setText(idle);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < gs.lineCount(); i++) {
            builder.append(gs.getLine(i).toString());
            builder.append("\n");
        }
        textArea.setText(builder.toString());

        if (state.isActive()) {
            highlightedLine = gs.getSelectedLine();
        } else {
            highlightedLine = null;
        }
        invalidate();
    }

}
