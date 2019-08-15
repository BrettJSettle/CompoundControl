package com.settle.compoundcontrol;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class LevelInfoTile extends FrameLayout {
    private LevelInfo level;

    public LevelInfoTile(Context context, LevelInfo level){
        super(context);
        this.level = level;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.level_info_tile, this);

    }

    public LevelInfo getLevel(){
        return level;
    }

    public void setSelected(boolean selected){
        setBackgroundColor(selected ? Color.YELLOW : Color.TRANSPARENT);
    }
}
