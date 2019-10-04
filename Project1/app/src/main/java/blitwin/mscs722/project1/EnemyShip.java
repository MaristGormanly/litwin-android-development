package blitwin.mscs722.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;

public class EnemyShip {

    private Bitmap bitmap;

    // enemy position
    private int xPos;
    private int yPos;

    // enemy speed
    private int speed;

    // keep the enemy inside the screen
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    public EnemyShip(Context context, int screenWidth, int screenHeight) {
        //getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ufo);

        // rescale enemies
        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth / 5, screenHeight / 10, true);


        //initializing min and max coordinates
        maxX = screenWidth;
        maxY = screenHeight;
        minX = 0;
        minY = 0;

        //generating a random coordinate to add enemy
        Random generator = new Random();
        speed = generator.nextInt(6) + 15;
        yPos = 0;
        xPos = generator.nextInt(maxX) - bitmap.getWidth();
        if (xPos < minX) {
            xPos = 20;
        }



    }

    public void update() {
        // increase y coordinate so that enemy will move top to botttom
        yPos += speed;
        // if the enemy reaches the bottom
        if (yPos > maxY - bitmap.getHeight()) {
            // adding the enemy again to the top
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            yPos = 0;
            xPos = generator.nextInt(maxX) - bitmap.getWidth();
            if (xPos < minX) {
                xPos = 20;
            }
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getSpeed() {
        return speed;
    }
}