package com.android.example.planegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;
import java.util.Vector;

public class SpaceGameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder sfh;
    private Resources res;
    private static Thread thread;
    private Canvas canvas;
    private Paint paint;
    private GameMenu gameMenu; // 游戏menu界面
    private boolean startFlag = false; // 游戏开始按钮的按压状态
    private Bitmap start, startPress;
    public static int gameState = GameProperty.GAME_START;
    public boolean playing;
    //游戏项目标签
    private boolean flag = false;

    // gameing
    public GameBackground gameBg;
    public Bitmap lose;
    private player player;


    private Vector<Enemy> vcEnemy; //敌机容器

    int sumEnemy = 12; //敌机总数量
    int enemySunadd = 3; // 每次敌机增加数量

    int countTime = 0; //计时器

    private Vector<Bullet> buPlayer; //玩家子弹容器
    private Vector<Bullet> buEnemy; //敌机子弹容器


    public SpaceGameView(Context context) {

        super(context);
        sfh = getHolder();
        res = getResources();
        sfh.addCallback(this);
        setFocusable(true);
    }

    // 触屏设置
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int tempX = (int) event.getX();
        int tempY = (int) event.getY();
        switch (gameState) {
            case GameProperty.GAME_START://游戏开始界面
                if (tempX > getWidth() / 2 - start.getWidth() / 2 && tempX < getWidth() / 2 + start.getWidth() / 2) {
                    if (tempY > getHeight() / 2 - start.getHeight() / 2 && tempY < getHeight() / 2 + start.getHeight() / 2) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                            startFlag = true; //如果手指是按下或者移动
                        } else if (event.getAction() == MotionEvent.ACTION_UP) { //startFlag == true &&
                            if (startFlag == true) {
                                startFlag = false;
                                //Enter Game State
                                gameState = GameProperty.GAME_ING;
                            }

                        }

                    }
                }
                break;
            case GameProperty.GAME_ING:
                //gameBg.draw(canvas,paint);

                // 设置飞机只能移动
                if (length(player.getX() - tempX, player.getY() - tempY)) {
                    player.setX(tempX);
                    player.setY(tempY);
                }

                break;
            case GameProperty.GAME_LOSE:
                break;
            case GameProperty.GAME_WIN:
                break;
        }
        return true;
    }

    //判断手指触点与飞机的距离
    private boolean length(int x, int y) {
        int sum = x * x + y * y;
        if (sum < 60000) {
            return true;
        }
        return false;

    }


    // 项目创建的时候调用该方法
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        initGame();
        thread = new Thread(this);
        flag = true;
        thread.start();

    }

    private void initGame() { // 初始化游戏
        // 初始化game menu
        Bitmap gm = BitmapFactory.decodeResource(res, GameProperty.GAME_MENU);
        gameMenu = new GameMenu(gm, getWidth(), getHeight());
        start = BitmapFactory.decodeResource(res, GameProperty.GAME_START_BTN); // 引用start按钮，初始化时创建按钮
        startPress = BitmapFactory.decodeResource(res, GameProperty.GAME_START_PRESS); // 按钮的点击状态

        // 初始化background
        Bitmap Bg = BitmapFactory.decodeResource(res, GameProperty.GAME_BACKGROUND);
        gameBg = new GameBackground(Bg, getWidth(), getHeight());

        // 初始化player
        Bitmap pl = BitmapFactory.decodeResource(res, GameProperty.GAME_PLAYER);
        Bitmap phBit = BitmapFactory.decodeResource(res, GameProperty.GAME_PLAYER);
        player = new player(pl, getWidth(), getHeight());

        // 初始化enemy
        Bitmap en = BitmapFactory.decodeResource(res, GameProperty.GAME_ENEMY);
        vcEnemy = new Vector<>();
        for (int i = 0; i < 3; i++) {
            Bitmap enemy = BitmapFactory.decodeResource(res, GameProperty.GAME_ENEMY);
            int y = -100;
            Random random = new Random();
            int x = random.nextInt(getWidth() - 200);
            vcEnemy.add(new Enemy(enemy, x, y));
        }

        // 初始化子弹
        buPlayer = new Vector<>();
        buEnemy = new Vector<>();

        // 失败界面
        lose = BitmapFactory.decodeResource(res, GameProperty.GAME_OVER);


    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    @Override
    public void run() {
        while (flag) {
            myDraw();
            logic();
        }

    }


    private void logic() {
        switch (gameState) {
            case GameProperty.GAME_ING:
                gameBg.logic();

                // 生成敌机的逻辑
                for (int i = 0; i < vcEnemy.size(); i++) {
                    Enemy enemy = vcEnemy.elementAt(i);
                    enemy.logic();
                }

                // 添加敌机
                countTime++;
                if (countTime % 40 == 0) {
                    if (enemySunadd < sumEnemy) {
                        enemySunadd++;
                        Bitmap en = BitmapFactory.decodeResource(res, GameProperty.GAME_ENEMY);

                        int y = -100;
                        Random random = new Random();
                        int x = random.nextInt(getWidth() - 200);
                        vcEnemy.add(new Enemy(en, x, y));
                    }
                }
                // 添加子弹
                if (countTime % 20 == 0) {
                    Bitmap bullet_player = BitmapFactory.decodeResource(res, Bullet.BULLET_PLAYER);
                    buPlayer.add(new Bullet(bullet_player, player.getX(), player.getY(), GameProperty.TPYE_PLAYER, getHeight()));

                }

                if (countTime % 40 == 0) {
                    // enemy bullet
                    Bitmap bullet_enemy = BitmapFactory.decodeResource(res, GameProperty.BULLET_ENEMY);
                    for (int i = 0; i < vcEnemy.size(); i++) {
                        Enemy enemy = vcEnemy.elementAt(i);
                        buEnemy.add(new Bullet(bullet_enemy, enemy.x, enemy.y, GameProperty.TPYE_ENEMY, getHeight()));
                    }

                }

                for (int i = 0; i < buPlayer.size(); i++) {
                    Bullet bullet = buPlayer.elementAt(i);
                    bullet.logic();

                    if (bullet.getY() > getHeight()) { //子弹超出屏幕范围就销毁
                        buPlayer.removeElementAt(i);
                    }
                }

                for (int i = 0; i < buEnemy.size(); i++) {
                    Bullet bullet = buEnemy.elementAt(i);
                    bullet.logic();

                    if (bullet.getY() > getHeight()) {
                        buEnemy.removeElementAt(i);
                    }
                }

                // 碰撞检测
                for (int i = 0; i < buEnemy.size(); i++) {
                    Bullet bullet = buEnemy.elementAt(i);
                    boolean collisionWith = player.isCollisionWith(bullet);
                    if (collisionWith) {
                        buEnemy.removeElementAt(i); //碰撞后销毁bullet
                        player.hp--; //玩家血量减一
                    }
                }

                for (int i = 0; i < buPlayer.size(); i++) {
                    Bullet bullet = buPlayer.elementAt(i);
                    for (int j = 0; j < vcEnemy.size(); j++) {
                        Enemy enemy = vcEnemy.elementAt(j);
                        boolean collisionWith = enemy.isCollisionWith(bullet);
                        if (collisionWith) {
                            buPlayer.removeElementAt(i); //碰撞后销毁bullet
                            enemy.hp--; //敌机血量减一

                        }
                    }
                }

                // 碰撞移除
                // dispose enemy
                for (int i = 0; i < vcEnemy.size(); i++) {
                    Enemy enemy = vcEnemy.elementAt(i);
                    if (enemy.hp <= 0) {
                        vcEnemy.removeElementAt(i); //敌机死亡后销毁敌机
                    }
                }

                // 玩家死亡
                if (player.hp <= 0) {
                    gameState = GameProperty.GAME_LOSE;
                }
        }
    }
    private void myDraw() {
            try {
                canvas = sfh.lockCanvas();
                switch (gameState) {

                    case GameProperty.GAME_START://游戏开始界面
                        gameMenu.draw(canvas, paint); //绘制游戏menu界面
                        if (startFlag) {
                            canvas.drawBitmap(startPress, getWidth() / 2 - startPress.getWidth() / 2, getHeight() / 2 - startPress.getHeight() / 2, paint);
                        } else {
                            canvas.drawBitmap(start, getWidth() / 2 - start.getWidth() / 2, getHeight() / 2 - start.getHeight() / 2, paint);
                        }
                        break;

                    case GameProperty.GAME_ING:
                        // draw 游戏ing的背景
                        gameBg.draw(canvas, paint);
                        //
                        player.draw(canvas, paint);
                        //
                        for (int i = 0; i < vcEnemy.size(); i++) {
                            Enemy enemy = vcEnemy.elementAt(i);
                            enemy.draw(canvas, paint);
                        }
                        // draw bullet
                        for (int i = 0; i < buPlayer.size(); i++) {
                            Bullet bullet = buPlayer.elementAt(i);
                            bullet.draw(canvas, paint);
                        }

                        for (int i = 0; i < buEnemy.size(); i++) {
                            Bullet bullet = buEnemy.elementAt(i);
                            bullet.draw(canvas, paint);
                        }

                        break;

                    case GameProperty.GAME_LOSE:
                        // draw 游戏失败的界面

                        Rect rect = new Rect(0, 0, getWidth(), getHeight());
                        canvas.drawBitmap(lose, null, rect, paint);
                        break;

                    case GameProperty.GAME_WIN:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sfh.unlockCanvasAndPost(canvas);
            }
        }

    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }
}


