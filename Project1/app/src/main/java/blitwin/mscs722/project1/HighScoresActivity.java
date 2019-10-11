package blitwin.mscs722.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HighScoresActivity extends AppCompatActivity {

    private TextView highScoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        SharedPreferences highscores = getSharedPreferences("highscores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = highscores.edit();
        int highScore = highscores.getInt("HIGH_SCORE", 0);
        int score = highscores.getInt("FINAL_SCORE", 0);

        highScoresList = findViewById(R.id.highScoresList);

        // save new high score
        if (score > highScore) {
            highScoresList.setText(Integer.toString(score));
            //SharedPreferences.Editor editor = highscores.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        } else {
            highScoresList.setText(Integer.toString(highScore));
        }
    }

    public void mainMenu(View view) {
        Intent menuIntent = new Intent(this, MainActivity.class);
        startActivity(menuIntent);
    }

    public void resetHighScores(View view) {
        SharedPreferences highscores = getSharedPreferences("highscores", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = highscores.edit();
        final Intent highScoresIntent = new Intent(this, HighScoresActivity.class);
        // show confirmation warning
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to reset high scores?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                editor.clear().apply();
                startActivity(highScoresIntent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
