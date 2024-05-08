package com.android.example.planegame;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class player {
    private int x,y;
    private Bitmap player; //phBit
    private int screenW, screenH;
    public int hp = 3;


    public player(Bitmap player,  int screenW, int screenH) { //Bitmap phBit,
        this.player = player;
        //this.phBit = phBit;
        this.screenW = screenW;
        this.screenH = screenH;
        x = screenW/2;
        y = screenH/5*4;

    }


    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(player, x, y, paint);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCollisionWith(Bullet bullet){
            int x2 = bullet.x;
            int y2 = bullet.y;
            int w2 = bullet.bu.getWidth(); // 直接获取bullet的public属性
            int h2 = bullet.bu.getHeight();
            if (x >= x2 && x >= x2 + w2) {
                return false;
            } else if (x <= x2 && x + player.getWidth() <= x2) {
                return false;
            } else if (y >= y2 && y >= y2 + h2) {
                return false;
            } else if (y <= y2 && y + player.getHeight() <= y2) {
                return false;
            }
            return true;

    }
}

