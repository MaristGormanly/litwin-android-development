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

import java.util.ArrayList;
import java.util.Collections;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        SharedPreferences highscores = getSharedPreferences("highscores", Context.MODE_PRIVATE);

        String highscoresString = highscores.getString("HIGHSCORES_LIST", "");
        ArrayList<Integer> highScoresIntegerList = new ArrayList<>();
        String string[] = highscoresString.split(",");
        for (String s: string) {
            highScoresIntegerList.add(Integer.parseInt(s));
        }
        Collections.sort(highScoresIntegerList, Collections.reverseOrder());
        TextView score1 = findViewById(R.id.score1);
        score1.setText(getString(R.string.score1) +  "   " + highScoresIntegerList.get(0).toString());
        TextView score2 = findViewById(R.id.score2);
        score2.setText(getString(R.string.score2) + "   " + highScoresIntegerList.get(1).toString());
        TextView score3 = findViewById(R.id.score3);
        score3.setText(getString(R.string.score3) + "   " + highScoresIntegerList.get(2).toString());
        TextView score4 = findViewById(R.id.score4);
        score4.setText(getString(R.string.score4) + "   " + highScoresIntegerList.get(3).toString());
        TextView score5 = findViewById(R.id.score5);
        score5.setText(getString(R.string.score5) + "   " + highScoresIntegerList.get(4).toString());
        TextView score6 = findViewById(R.id.score6);
        score6.setText(getString(R.string.score6) + "   " + highScoresIntegerList.get(5).toString());
        TextView score7 = findViewById(R.id.score7);
        score7.setText(getString(R.string.score7) + "   " + highScoresIntegerList.get(6).toString());
        TextView score8 = findViewById(R.id.score8);
        score8.setText(getString(R.string.score8) + "   " + highScoresIntegerList.get(7).toString());
        TextView score9 = findViewById(R.id.score9);
        score9.setText(getString(R.string.score9) + "   " + highScoresIntegerList.get(8).toString());
        TextView score10 = findViewById(R.id.score10);
        score10.setText(getString(R.string.score10) + "   " + highScoresIntegerList.get(9).toString());
    }

    public void mainMenu(View view) {
        Intent menuIntent = new Intent(this, MainActivity.class);
        startActivity(menuIntent);
    }

    public void resetHighScores(View view) {
        final SharedPreferences highscores = getSharedPreferences("highscores", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = highscores.edit();
        final Intent highScoresIntent = new Intent(this, HighScoresActivity.class);
        // show confirmation warning
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to reset high scores?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Clear high scores close the dialog
                editor.clear().apply();
                int[] highscoresList = new int[10];
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < highscoresList.length; i++) {
                    str.append(highscoresList[i]).append(",");
                }
                highscores.edit().putString("HIGHSCORES_LIST", str.toString()).apply();
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
