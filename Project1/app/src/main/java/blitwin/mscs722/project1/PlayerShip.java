package blitwin.mscs722.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerShip {

    private Bitmap bitmap;

    // player coordinates
    private int xPos;
    private int yPos;

    private boolean boosting;

    // player ship speed
    private int speed;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 30;

    private Rect collisionBox;

    private int laserCount = 0;

    private int laserLimit = 1;

    // max and min X values of screen that player can travel in
    private int maxX;
    private int minX;

    // ship will either move left or right
    private String moveDirection;

    public PlayerShip(Context context, int screenWidth, int screenHeight) {
        xPos = screenWidth / 2;
        yPos = screenHeight - (screenHeight / 4);
        speed = 1;

        // get bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship);

        // rescale player
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() - 50, bitmap.getHeight() - 100, true);

        maxX = screenWidth - bitmap.getWidth();
        minX = 0;

        boosting = false;

        collisionBox = new Rect(xPos, yPos, bitmap.getWidth(), bitmap.getHeight());
    }

    //setting boosting true
    public void setBoosting() {
        boosting = true;
    }

    //setting boosting false
    public void stopBoosting() {
        boosting = false;
    }

    public void setLaserCount(int laserCount) {
        this.laserCount = laserCount;
    }

    public void setLaserLimit(int laserLimit) {
        this.laserLimit = laserLimit;
    }

    public void moveLeft() {
        moveDirection = "left";
    }
    
    public void moveRight() {
        moveDirection = "right";
    }

    public void update(){
        if (boosting) {
            speed += 5;
        } else {
            speed -= 2;
        }
        // keep ship from going too fast
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        // keep the ship slowly drifting with no input
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        // moving the ship left
        if (moveDirection == "left") {
            xPos -= speed;
        }

        // moving the ship right
        if (moveDirection == "right") {
            xPos += speed;
        }

        // but controlling it also so that it won't go off the screen
        if (xPos < minX) {
            xPos = minX;
        }
        if (xPos > maxX) {
            xPos = maxX;
        }

        collisionBox.left = xPos;
        collisionBox.top = yPos;
        collisionBox.right = xPos + bitmap.getWidth();
        collisionBox.bottom = yPos + bitmap.getHeight();
    }

    public Rect getCollisionBox() {
        return collisionBox;
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

    public int getLaserCount() {
        return laserCount;
    }

    public int getLaserLimit() {
        return laserLimit;
    }

    public void shoot(PlayerLaser laser) {
        laser.setXPos(this.getXPos());
        laser.setYPos(this.getYPos());
        laser.setShooting(true);
    }

}


