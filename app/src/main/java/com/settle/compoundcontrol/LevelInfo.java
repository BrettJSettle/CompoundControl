package com.settle.compoundcontrol;

class LevelInfo {

    private int num;
    private String title;
    private String description;

    LevelInfo(int num, String title, String description){
        this.num = num;
        this.title = title;
        this.description = description;
    }

    int getLevelNum(){
        return num;
    }

    String getTitle(){
        return title;
    }

    String getDescription(){
        return description;
    }
}
