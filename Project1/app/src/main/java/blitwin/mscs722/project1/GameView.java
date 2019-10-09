package blitwin.mscs722.project1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

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

    private PlayerLaser[] lasers;

    private ArrayList<PlayerLaser> lasersInPlay = new ArrayList<>();

    private Paint scoreDisplay = new Paint();

   // private PlayerLaser laser;

    //private PlayerLaser playerLaserArray[] = new PlayerLaser[playerLaserLimit];

    // int playerLaserCount = 0;

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

        lasers = new PlayerLaser[player.getLaserLimit()];
        for (int i = 0; i < player.getLaserLimit(); i++) {
            lasers[i] = new PlayerLaser(context, screenWidth, screenHeight);
        }

        //laser = new PlayerLaser(context, screenWidth, screenHeight);

        explosion = new Explosion(context);

        // draw score
        scoreDisplay.setColor(Color.WHITE);
        scoreDisplay.setTextSize(70);
        scoreDisplay.setTypeface(Typeface.DEFAULT_BOLD);
        scoreDisplay.setAntiAlias(true);
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
            // draw lasers
//            PlayerLaser laser = new PlayerLaser(this.getContext(), screenWidth, screenHeight);
//            lasers.add(laser);
//            canvas.drawBitmap(lasers.get(0).getBitmap(), lasers.get(0).getXPos(), lasers.get(0).getYPos(), paint);
            for (int i = 0; i < player.getLaserLimit(); i++) {
                canvas.drawBitmap(lasers[i].getBitmap(), lasers[i].getXPos(), lasers[i].getYPos(), paint);
            }
            canvas.drawText("Score: " + player.getScore(), 20, 60, scoreDisplay);
            // unlock canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        player.update();

        Log.d("LaserSize", Integer.toString(lasersInPlay.size()));

        explosion.setXPos(-200);
        explosion.setYPos(-200);

        if (lasersInPlay.size() > 0) {
            for (int j = 0; j < lasersInPlay.size(); j++) {
                Log.d("LaserPosition", "Laser " + j + ": " + lasers[j].getXPos() +  "," + lasers[j].getYPos());
                lasers[j].update();
                if (!lasers[j].isShooting()) {
                    lasersInPlay.remove(lasers[j]);
                }
            }
        }

        for (int i = 0; i < enemyCount; i++) {
            enemies[i].update();

            // detect player collision
            if (Rect.intersects(player.getCollisionBox(), enemies[i].getCollisionBox())) {
                // draw explosion and remove enemy from game view
                explosion.setXPos(enemies[i].getXPos());
                explosion.setYPos(enemies[i].getYPos());
                enemies[i].setYPos(-200);
            }

            // detect player laser hit
            if (lasersInPlay.size() > 0) {
                int currentLaserCount = lasersInPlay.size();
                for (int j = 0; j < currentLaserCount; j++) {
                    if (Rect.intersects(lasers[j].getCollisionBox(), enemies[i].getCollisionBox())) {
                        // draw explosion and remove enemy from game view
                        explosion.setXPos(enemies[i].getXPos());
                        explosion.setYPos(enemies[i].getYPos());
                        enemies[i].setYPos(-200);
                        lasers[j].stopLaser();
                        player.setLaserCount(currentLaserCount - 1);
                        player.addPoints(5);
                    }
                }
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
        RectF leftSideScreen = new RectF(0, 0, screenWidth / 2, screenHeight);
        RectF rightSideScreen = new RectF((screenWidth / 2) + 1, 0, screenWidth, screenHeight);
        int touchXPos = (int) motionEvent.getX();
        int touchYPos = (int) motionEvent.getY();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;

            case MotionEvent.ACTION_DOWN:
                if (player.getCollisionBox().contains(touchXPos, touchYPos)) {
                    if (lasersInPlay.size() < player.getLaserLimit()) {
                        //player.setLaserCount(player.getLaserCount()+1);
                        //int currentLaserCount = lasersInPlay.size() + 1;
                        boolean laserShot = false;
                        for (int i = 0; i < lasers.length; i++) {
                            Log.d("LasersLength", Integer.toString(lasers.length));
                            Log.d("LaserShotPos", i + " " + lasers[i].getYPos());
                            if (lasers[i].getYPos() < 0 && !laserShot) {
                                player.shoot(lasers[i]);
                                lasersInPlay.add(lasers[i]);
                                laserShot = true;
                            }
                        }

                        Log.d("TouchShip", "Touched the ship at coordinates" + touchXPos + touchYPos);
                    }
                } else {
                    if (leftSideScreen.contains(touchXPos, touchYPos)) {
                        player.moveLeft();
                    } else if (rightSideScreen.contains(touchXPos, touchYPos)) {
                        player.moveRight();
                    }
                    player.setBoosting();
                }
                break;
        }
        return true;
    }
}
