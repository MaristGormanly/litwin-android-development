package blitwin.mscs722.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;


public class GameActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SensorEventListener {
    private SoundPool soundPool;
    private int cgs, st, ds;
    private SensorManager sensorManager;
    private Sensor sensor;
    int maxStreams = 10;
    float cgsSoundRate = 1;
    float dsSoundRate = 1;
    float stSoundRate = 1;
    float uploadedSoundRate = 1;
    float[] sampleRates = {cgsSoundRate, stSoundRate, dsSoundRate, uploadedSoundRate};
    int cgsLoop = 0;
    int dsLoop = 0;
    int stLoop = 0;
    int cgsStreamId;
    int dsStreamId;
    int stStreamId;
    int[] playbackSpinners = {R.id.cgsSpinner, R.id.dsSpinner, R.id.stSpinner, R.id.uploadedSoundSpinner};
    //int[] loopSpinners = {R.id.cgsLoop, R.id.dsLoop, R.id.stLoop, R.id.uploadedSoundLoop};
    MediaPlayer uploadedSoundPlayer = new MediaPlayer();
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageReference uploadedFileRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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

        // load the proximity sensor
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        uploadedFileRef = storageReference.child("audio/uploaded_file.mp3");

        // load the sound assets
        cgs = soundPool.load(this, R.raw.comegetsome, 1);
        st = soundPool.load(this, R.raw.stillthere, 1);
        ds = soundPool.load(this, R.raw.damnson, 1);

        uploadedSoundPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            uploadedSoundPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/blitbeatblender.appspot.com/o/audio%2Fuploaded_file.mp3?alt=media");
        } catch (IOException e) {
            e.printStackTrace();
        }
        uploadedSoundPlayer.prepareAsync();

        // Build the playback option spinners

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

        Spinner uploadedSpinner = findViewById(R.id.uploadedSoundSpinner);
        uploadedSpinner.setAdapter(adapter);
        uploadedSpinner.setSelection(1);
        uploadedSpinner.setOnItemSelectedListener(this);

        // Build the loop option spinners
//        Spinner loopSpinner = findViewById(R.id.cgsLoop);
//        ArrayAdapter<CharSequence> loopAdapter = ArrayAdapter.createFromResource(this,
//                R.array.loop_choices, android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        loopSpinner.setAdapter(loopAdapter);
//        loopSpinner.setSelection(1);
//        loopSpinner.setOnItemSelectedListener(this);
//
//        Spinner loopSpinner2 = findViewById(R.id.stLoop);
//        loopSpinner2.setAdapter(loopAdapter);
//        loopSpinner2.setSelection(1);
//        loopSpinner2.setOnItemSelectedListener(this);
//
//        Spinner loopSpinner3 = findViewById(R.id.dsLoop);
//        loopSpinner3.setAdapter(loopAdapter);
//        loopSpinner3.setSelection(1);
//        loopSpinner3.setOnItemSelectedListener(this);
//
//        Spinner uploadedLoop = findViewById(R.id.uploadedSoundLoop);
//        uploadedLoop.setAdapter(loopAdapter);
//        uploadedLoop.setSelection(1);
//        uploadedLoop.setOnItemSelectedListener(this);

    }

    public void playSound(View view) {
        // Play different sound depending on which button is pressed
        final CheckBox cgsloopCheckBox = findViewById(R.id.cgsloopcheckBox);
        final CheckBox stloopCheckBox = findViewById(R.id.stloopcheckBox);
        final CheckBox dsloopCheckBox = findViewById(R.id.dsloopcheckBox);
        final CheckBox uploadedloopCheckBox = findViewById(R.id.uploadedloopcheckBox);
        switch (view.getId()) {
            case R.id.come_get_some:
                cgsStreamId = soundPool.play(cgs, 1, 1, 0, cgsloopCheckBox.isChecked() ? -1 : 0, sampleRates[0]);
                break;
            case R.id.still_there:
                stStreamId = soundPool.play(st, 1, 1, 0, stloopCheckBox.isChecked() ? -1 : 0, sampleRates[1]);
                break;
            case R.id.damn_son:
                dsStreamId = soundPool.play(ds, 1, 1, 0, dsloopCheckBox.isChecked() ? -1 : 0, sampleRates[2]);
                break;
            case R.id.uploadedSound:
                uploadedSoundPlayer.setPlaybackParams(uploadedSoundPlayer.getPlaybackParams().setSpeed(sampleRates[3]));
                uploadedSoundPlayer.setLooping(uploadedloopCheckBox.isChecked());
                uploadedSoundPlayer.start();
        }
    }

    public void stopAll(View view) {
        soundPool.autoPause();
        if(uploadedSoundPlayer.isPlaying()) {
            uploadedSoundPlayer.pause();
            uploadedSoundPlayer.seekTo(0);
        }
    }

    public void stopSound(View view) {
        switch (view.getId()) {
            case R.id.stopCGS:
                soundPool.pause(cgsStreamId);
                break;
            case R.id.stopDS:
                soundPool.pause(dsStreamId);
                break;
            case R.id.stopST:
                soundPool.pause(stStreamId);
                break;
            case R.id.stopUploaded:
                uploadedSoundPlayer.pause();
                uploadedSoundPlayer.seekTo(0);
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
        // check if use proximity sensor is checked
        final CheckBox proximityCheckBox = findViewById(R.id.proximitycheckBox);
        boolean playbackSelected = false;
        //boolean loopSelected = false;
        for (int i : playbackSpinners) {
            if (i == parent.getId()) {
                playbackSelected = true;
                break;
            }
        }
//        for (int i : loopSpinners) {
//            if (i == parent.getId()) {
//                loopSelected = true;
//                break;
//            }
//        }
        float soundRate = 1;
        int loop = 0;
        // depending on which spinner group was selected
        if (playbackSelected) {
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
        } /*else if (loopSelected) {
            switch (position) {
                case 0:
                    loop = -1;
                    break;
                case 1:
                    loop = 0;
            }
        }*/

        // change parameter of selected sound
        switch (parent.getId()) {
            case R.id.cgsSpinner:
                // only change these if proximity sensor is not being used
                if (!proximityCheckBox.isChecked()) {
                    sampleRates[0] = soundRate;
                }
                break;
            case R.id.stSpinner:
                if (!proximityCheckBox.isChecked()) {
                    sampleRates[1] = soundRate;
                }
                break;
            case R.id.dsSpinner:
                if (!proximityCheckBox.isChecked()) {
                    sampleRates[2] = soundRate;
                }
                break;
            case R.id.uploadedSoundSpinner:
                if (!proximityCheckBox.isChecked()) {
                    sampleRates[3] = soundRate;
                }
                break;
            /*case R.id.cgsLoop:
                cgsLoop = loop;
                break;
            case R.id.stLoop:
                stLoop =  loop;
                break;
            case R.id.dsLoop:
                dsLoop = loop;
                break;
            case R.id.uploadedSoundLoop:
                uploadedSoundPlayer.setLooping(loop == -1);*/
        }
    }

    public void menu(View view) {
        Intent menuIntent = new Intent(this, MainActivity.class);
        startActivity(menuIntent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        float newSampleRate = 0;
        final CheckBox proximityCheckBox = findViewById(R.id.proximitycheckBox);
        // only use sensor if checkbox is active
        if (proximityCheckBox.isChecked()) {
            // change sample rates depending on distance value
            if (distance < 2) {
                newSampleRate = (float) 0.5;
            } else if (distance < 5) {
                newSampleRate = 1;
            } else {
                newSampleRate = 2;
            }
            // update all sample rates
            for (int i = 0; i < sampleRates.length; i++) {
                sampleRates[i] = newSampleRate;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
