package com.settle.compoundcontrol;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.settle.compoundcontrol.level.config.LevelConfig;
import com.settle.compoundcontrol.level_select.impl.LevelAdapter;
import com.settle.compoundcontrol.level_select.impl.LevelInfo;
import com.settle.compoundcontrol.level_select.view.LevelInfoTile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LevelInfoTile selected_tile;
    private Button startButton;
    private TextView titleView;
    private TextView infoView;
    private LevelInfo[] levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleView = findViewById(R.id.levelSelectTitleText);
        infoView = findViewById(R.id.levelSelectInfoText);

        loadLevels();

        GridView gv = findViewById(R.id.levelSelectViewGrid);
        gv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        gv.setDrawSelectorOnTop(true);

        startButton = findViewById(R.id.levelSelectStartButton);
        startButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                LevelInfo info = selected_tile.getLevel();
                startLevel(info);
            }
        });

        LevelAdapter adapter = new LevelAdapter(this, levels);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectLevel((LevelInfoTile) view);
            }
        });

        // Use for quick testing
//        startLevel(levels[1]);
    }

    private void startLevel(LevelInfo info){
        Intent intent = new Intent(MainActivity.this, LevelActivity.class);
        AssetManager manager = getAssets();
        try {
            InputStream is = manager.open(info.getTitle() + ".json");
            LevelConfig state = LevelConfig.load(is);
            intent.putExtra("level", state);
            startActivity(intent);
            return;
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        Toast toast = Toast.makeText(getBaseContext(), "Failed to load " + info.getTitle(), Toast.LENGTH_LONG);
        toast.show();
    }

    private void loadLevels(){
        ArrayList<LevelInfo> levels = new ArrayList<>();
        AssetManager assetManager = getAssets();

            try {
                InputStream is = assetManager.open("levels.json");
                JsonReader reader = new JsonReader(new InputStreamReader(is));
                reader.setLenient(true);
                reader.beginArray();
                while (reader.hasNext()) {
                    LevelInfo item = parseLevel(reader);
                    levels.add(item);
                }
                reader.endArray();
                reader.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        this.levels = new LevelInfo[levels.size()];
        levels.toArray(this.levels);
    }

    private void selectLevel(LevelInfoTile tile){
        if (selected_tile != null){
            selected_tile.setSelected(false);
        }
        selected_tile = tile;
        selected_tile.setSelected(true);
        startButton.setEnabled(true);
        LevelInfo info = selected_tile.getLevel();
        titleView.setText(info.getTitle());
        infoView.setText(info.getDescription());
    }

    private LevelInfo parseLevel(JsonReader reader) throws IOException {
        reader.beginObject();
        String level_name = "", description = "";

        while(reader.hasNext()){
            String name = reader.nextName();
            switch(name) {
                case "name":
                    level_name = reader.nextString();
                    break;
                case "description":
                    description = reader.nextString();
                    break;
                default:
                    String val = reader.nextString();
                    throw new RuntimeException("UNKNOWN : " + name + "->" + val);
            }
        }
        reader.endObject();
        return new LevelInfo(level_name, description);
    }
}
