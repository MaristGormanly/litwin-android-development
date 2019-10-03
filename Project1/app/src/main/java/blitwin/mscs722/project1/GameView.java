package blitwin.mscs722.project1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean isPlaying;

    private ImageView imageView = (ImageView)findViewById(R.id.imageView);

    private Thread gameThread = null;

    private PlayerShip player;
    private GameBackground gameBG;
    //private Bitmap gameBGBitmap = gameBG.getBitmap();

    private int screenHeight;
    private int screenWidth;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public GameView(Context context) {
        super(context);

        // initialize player
        player = new PlayerShip(context);

        // initialize background
        gameBG =  new GameBackground(context);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        surfaceHolder = getHolder();
        paint = new Paint();
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
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        player.update();
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
}
