package blitwin.mscs722.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int cgs, st, ds;
//    Button ds;
//    Button cgs;
//    Button st;
    int maxStreams = 3;
    float soundRate = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize SoundPool based on Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }

        cgs = soundPool.load(this, R.raw.comegetsome, 1);
        st = soundPool.load(this, R.raw.stillthere, 1);
        ds = soundPool.load(this, R.raw.damnson, 1);

    }

    public void playSound(View view) {
        // Play different sound depending on which button is pressed
        switch (view.getId()) {
            case R.id.come_get_some:
                soundPool.play(cgs, 1, 1, 0, 0, soundRate);
                break;
            case R.id.damn_son:
                soundPool.play(ds, 1, 1, 0, 0, soundRate);
                break;
            case R.id.still_there:
                soundPool.play(st, 1, 1, 0, 0, soundRate);
                break;
        }
    }

    public void speed2X(View view) {
        soundRate = 2;
    }

    public void speed1X(View view) {
        soundRate = 1;
    }

    public void speed05X(View view) {
        soundRate = (float) 0.5;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }


}
