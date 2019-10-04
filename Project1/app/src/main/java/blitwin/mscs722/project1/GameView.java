package blitwin.mscs722.project1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean isPlaying;

    private ImageView imageView = (ImageView)findViewById(R.id.imageView);

    private Thread gameThread = null;

    private PlayerShip player;
    private GameBackground gameBG;

    private int screenHeight;
    private int screenWidth;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private EnemyShip[] enemies;

    private Explosion explosion;

    private int enemyCount = 3;

    public GameView(Context context) {
        super(context);

        // get the display screen width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        // initialize player
        player = new PlayerShip(context, screenWidth , screenHeight);

        // initialize background
        gameBG =  new GameBackground(context);

        surfaceHolder = getHolder();
        paint = new Paint();

        enemies = new EnemyShip[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new EnemyShip(context, screenWidth, screenHeight);
        }

        explosion = new Explosion(context);
    }

    @Override
    public void run() {
        while (isPlaying) {
            // update frame
            update();

            // draw frame
            draw();

            // control the framerate
            control();
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            // draw background
            Bitmap resized_background = Bitmap.createScaledBitmap(gameBG.getBitmap(), screenWidth, screenHeight, true);
            canvas.drawBitmap(resized_background, 0, 0, null);
            // draw player bitmap
            canvas.drawBitmap(player.getBitmap(), player.getXPos(), player.getYPos(), paint);
            // draw enemies
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getXPos(), enemies[i].getYPos(), paint);
            }
            // draw explosion
            canvas.drawBitmap(explosion.getBitmap(), explosion.getXPos(), explosion.getYPos(), paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        player.update();

        explosion.setXPos(-200);
        explosion.setYPos(-200);

        // enemies update based on player speed
        for (int i = 0; i < enemyCount; i++) {
            enemies[i].update();

            // detect player collision
            if (Rect.intersects(player.getCollisionBox(), enemies[i].getCollisionBox())) {
                // draw explosion and remove enemy from game view
                explosion.setXPos(enemies[i].getXPos());
                explosion.setYPos(enemies[i].getYPos());
                enemies[i].setY(-200);
            }
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        // pause the game and stop the game thread
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {

        }
    }

    public void resume() {
        // resume game and start game thread
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        RectF leftSideScreen = new RectF(0, screenHeight / 4, screenWidth / 2, screenHeight);
        int touchXPos = (int) motionEvent.getX();
        int touchYPos = (int) motionEvent.getY();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;

            case MotionEvent.ACTION_DOWN:
                if (leftSideScreen.contains(touchXPos, touchYPos)) {
                    player.moveLeft();
                } else {
                    player.moveRight();
                }
                player.setBoosting();
                break;
        }
        return true;
    }
}
