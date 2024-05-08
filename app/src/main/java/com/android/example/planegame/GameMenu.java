package com.android.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameMenu {
    private Bitmap gameMenu;

    private int screenW, screenH;
    private Rect rect;
//    private Bitmap start, startPress;


    public GameMenu(Bitmap gameMenu, int screenW, int screenH) {
        this.gameMenu = gameMenu;
        this.screenW = screenW;
        this.screenH = screenH;
        rect = new Rect(0,0, screenW, screenH);

    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(gameMenu, null, rect, paint);
    }
}
