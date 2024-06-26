package com.android.example.planegame;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SpaceShip {
    private int x,y;
    private Bitmap player, phBit; //phBit
    private int screenW, screenH;
    public int hp = 3;

    public static final int GAME_PLAYER = R.mipmap.spaceshipup;
    public static final int PLAYER_HP = R.mipmap.hpb;


    public SpaceShip(Bitmap player, Bitmap phBit, int screenW, int screenH) {
        this.player = player;
        this.phBit = phBit;
        this.screenW = screenW;
        this.screenH = screenH;
        x = screenW/2;
        y = screenH/5*4;

    }


    public void draw(Canvas canvas, Paint paint){

        canvas.drawBitmap(player, x, y, paint);

        for(int i=0; i<hp;i++){
            canvas.drawBitmap(phBit, 350*i+phBit.getWidth(), screenH-phBit.getHeight(),paint);
        }

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
            int w2 = bullet.BitBullet.getWidth(); // 直接获取bullet的public属性
            int h2 = bullet.BitBullet.getHeight();
            if (x >= x2 && x >= x2 + w2) {
                return false;
            } else if (x <= x2 && x + player.getWidth() <= x2) {
                return false;
            } else if (y >= y2 && y >= y2 + h2) {
                return false;
            } else return y > y2 || y + player.getHeight() > y2;

    }
}

