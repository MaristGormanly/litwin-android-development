package blitwin.mscs722.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SoundPool soundPool;
    private int cgs, st, ds;
    int maxStreams = 3;
    float cgsSoundRate = 1;
    float dsSoundRate = 1;
    float stSoundRate = 1;

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

        Spinner spinner = findViewById(R.id.cgsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.playback_choices, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.stSpinner);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(1);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3 = findViewById(R.id.dsSpinner);
        spinner3.setAdapter(adapter);
        spinner3.setSelection(1);
        spinner3.setOnItemSelectedListener(this);

    }

    public void playSound(View view) {
        // Play different sound depending on which button is pressed
        switch (view.getId()) {
            case R.id.come_get_some:
                soundPool.play(cgs, 1, 1, 0, 0, cgsSoundRate);
                break;
            case R.id.damn_son:
                soundPool.play(ds, 1, 1, 0, 0, dsSoundRate);
                break;
            case R.id.still_there:
                soundPool.play(st, 1, 1, 0, 0, stSoundRate);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        float soundRate = 1;
        Log.d("position", Integer.toString(position));
        switch (position) {
            case 0:
                soundRate = (float) 0.5;
                break;
            case 1:
                soundRate = 1;
                break;
            case 2:
                soundRate = 2;
        }
        switch (parent.getId()) {
            case R.id.cgsSpinner:
                cgsSoundRate = soundRate;
                break;
            case R.id.stSpinner:
                stSoundRate =  soundRate;
                break;
            case R.id.dsSpinner:
                dsSoundRate = soundRate;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
