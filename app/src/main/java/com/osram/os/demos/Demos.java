package com.osram.os.demos;

/**
 * Created by 34945 on 2/6/2018.
 */

public class Demos {
    private final int name;
    private final int imageResource;
    private boolean isFavorite = false;

    public Demos(int name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public int getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }
    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

}