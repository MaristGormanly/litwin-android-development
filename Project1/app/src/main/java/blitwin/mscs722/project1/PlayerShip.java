package blitwin.mscs722.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PlayerShip {
    private Bitmap bitmap;

    // player coordinates
    private int x;
    private int y;

    // player ship speed
    private int speed = 0;

    public PlayerShip(Context context) {
        x = 75;
        y = 50;
        speed = 1;

        // get bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship);
    }

    public void update(){
        // updating y coordinate
        y++;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

}


