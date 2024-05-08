package com.android.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
    public Bitmap BitBullet;
    public int x,y;
    private  int bulletTpye;
    private int screenH;
    //private int screenW;

    public int getY(){
        return y;
    }

    public static final int BULLET_PLAYER = R.mipmap.bullet1;
    public static final int BULLET_ENEMY = R.mipmap.bullet2;

    public Bullet(Bitmap bu, int x, int y, int bulletTpye, int screenH) {
        this.BitBullet = bu;
        this.x = x;
        this.y = y;
        this.bulletTpye = bulletTpye;
        this.screenH = screenH;
        //this.screenW = screenW;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(BitBullet, x, y, paint);

    }

    public void logic(){
        switch (bulletTpye) {
            case GameProperty.TPYE_PLAYER:
                y = y - 55; //bullet speed
                break;

            case GameProperty.TPYE_ENEMY:
                y = y + 25;
                break;
        }
    }
}
