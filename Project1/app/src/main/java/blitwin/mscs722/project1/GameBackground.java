package blitwin.mscs722.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GameBackground {
    private Bitmap space_bitmap;
    private Bitmap stars_bitmap;

    public GameBackground(Context context) {
        space_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.parallax_space_background);
        stars_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.parallax_space_stars);

    }
}
