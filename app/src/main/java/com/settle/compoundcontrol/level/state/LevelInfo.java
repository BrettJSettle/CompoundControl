package com.settle.compoundcontrol.level.state;

public class LevelInfo {

    private int num;
    private String title;
    private String description;

    public LevelInfo(int num, String title, String description) {
        this.num = num;
        this.title = title;
        this.description = description;
    }

    public int getLevelNum() {
        return num;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
