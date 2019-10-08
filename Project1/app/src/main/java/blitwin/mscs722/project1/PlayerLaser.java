package blitwin.mscs722.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class PlayerLaser {

    private Bitmap bitmap;

    // coordinates
    private int xPos;
    private int yPos;

    private int minY = 0;

    private int speed = 50;

    private Rect collisionBox;

    private boolean isShooting;

    public PlayerLaser(Context context, int screenWidth, int screenHeight) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluelaser);
        xPos = -300;
        yPos = -300;

        isShooting = false;

        // rescale player
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() - 200, bitmap.getHeight() - 500, true);

        collisionBox = new Rect(xPos, yPos, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        // move the laser up until it reaches the top
        Log.d("LaserUpdateInside", Integer.toString(yPos));
        if (isShooting) {
            yPos -= speed;
            if (yPos < minY) {
                this.stopLaser();
            }
        }

        collisionBox.left = xPos;
        collisionBox.top = yPos;
        collisionBox.right = xPos + bitmap.getWidth();
        collisionBox.bottom = yPos + bitmap.getHeight();

    }

    public void stopLaser() {
        this.setXPos(-300);
        this.setYPos(-300);
        this.setShooting(false);
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
        Log.d("LaserX", Integer.toString(xPos));
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
        Log.d("LaserY", Integer.toString(yPos));
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Rect getCollisionBox() {
        return collisionBox;
    }

    public boolean isShooting() {
        return isShooting;
    }
}
