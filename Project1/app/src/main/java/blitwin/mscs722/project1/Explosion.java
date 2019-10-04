package blitwin.mscs722.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {

    private Bitmap bitmap;

    // coordinates
    private int xPos;
    private int yPos;

    public Explosion(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.single_explosion);
        xPos = -200;
        yPos = -200;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
}
