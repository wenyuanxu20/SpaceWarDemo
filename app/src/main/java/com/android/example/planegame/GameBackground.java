package com.android.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameBackground {
    public Bitmap background01, background02;
    public int screenW, screenH;
    public Rect rect01, rect02;
    public static final int GAME_BACKGROUND = R.mipmap.background;
    public static final int HEALTH_BAR = R.mipmap.hp;
    public static final int GAME_OVER = R.mipmap.gameover;
    public static final int GAME_PAUSE = R.mipmap.pause;


    public GameBackground(Bitmap background, int screenW, int screenH) {
        this.background01 = background;
        this.background02 = background;
        this.screenW = screenW;
        this.screenH = screenH;
        rect01 = new Rect(0,0,screenW,screenH);
        rect02 = new Rect(0, screenH - 110, screenW, screenH*2-110);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(background01, null, rect01, paint);
        canvas.drawBitmap(background02, null, rect02, paint);
    }

    // background logic:可以滚动
    public void logic() {
        //屏幕滚动就是背景在上移
        rect01.top-=10;
        rect01.bottom-=10;
        rect02.top-=10;
        rect02.bottom-=10;
        //循环
        if(rect01.bottom<=-20){
            rect01.top = screenH - 110;
            rect01.bottom = screenH*2 - 110;

        }
        if(rect02.bottom<=-20){
            rect02.top = screenH - 110;
            rect02.bottom = screenH*2 - 110;

        }
    }
}
