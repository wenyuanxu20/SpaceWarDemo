package com.android.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy {
    public Bitmap en;
    public int x, y;
    public int hp = 1;
    public boolean up = false, down = true;

    public Enemy(Bitmap en, int x, int y) {
        this.en = en;
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas, Paint paint) {

        canvas.drawBitmap(en, x, y, paint);
    }

    public void logic() {
        if (y >= 400) {
            down = false;
        }


        if (y <= 100) {
            down = true;
        }

        if (down) {
            y += 5;
        } else {
            y -= 5;
        }
    }

    public boolean isCollisionWith(Bullet bullet){
        int x2 = bullet.x;
        int y2 = bullet.y;
        int w2 = bullet.bu.getWidth(); // 直接获取bullet的public属性
        int h2 = bullet.bu.getHeight();
        if (x >= x2 && x >= x2 + w2) {
            return false;
        } else if (x <= x2 && x + en.getWidth() <= x2) {
            return false;
        } else if (y >= y2 && y >= y2 + h2) {
            return false;
        } else if (y <= y2 && y + en.getHeight() <= y2) {
            return false;
        }
        return true;

    }
}

