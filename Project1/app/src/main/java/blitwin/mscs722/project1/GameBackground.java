package blitwin.mscs722.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class GameBackground {
    private Bitmap space_bitmap;
    private Bitmap stars_bitmap;
    private Bitmap combined_bitmap;

    public GameBackground(Context context) {
        space_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.parallax_space_background);
        stars_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.parallax_space_stars);

        combined_bitmap = Bitmap.createBitmap(space_bitmap.getWidth(), space_bitmap.getHeight(), space_bitmap.getConfig());
        Canvas canvas = new Canvas(combined_bitmap);
        canvas.drawBitmap(space_bitmap, new Matrix(), null);
        canvas.drawBitmap(stars_bitmap, 0, 0, null);

    }

    public Bitmap getBitmap() {
        return combined_bitmap;
    }
}
