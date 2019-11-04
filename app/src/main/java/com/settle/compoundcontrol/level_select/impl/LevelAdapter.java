package com.settle.compoundcontrol.level_select.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.settle.compoundcontrol.R;
import com.settle.compoundcontrol.level_select.view.LevelInfoTile;

public class LevelAdapter extends BaseAdapter {

    private LevelInfo[] levels;
    private Context context;

    public LevelAdapter(Context context, LevelInfo[] levels) {
        super();
        this.context = context;
        this.levels = levels;
    }

    @Override
    public int getCount() {
        return levels.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            // get layout from mobile.xml
            convertView = new LevelInfoTile(context, levels[i]);//inflater.inflate(R.layout.level_info_tile, null);

        }

        TextView text = convertView
                .findViewById(R.id.levelTileText);

        text.setText(String.valueOf(i));

        return convertView;
    }
}
