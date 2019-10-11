package blitwin.mscs722.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private TextView scoreDisplay;
    private String score;
    private TextView highScoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        SharedPreferences highscores = getSharedPreferences("highscores", Context.MODE_PRIVATE);
        scoreDisplay = findViewById(R.id.gameOverScore);
        // set GameOverActivity's score display to the score passed in
        score = Integer.toString(highscores.getInt("FINAL_SCORE", 0));
        scoreDisplay.setText(score);
    }

    public void launchGame(View view) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }

    public void viewHighScores(View view) {
        Intent highScoresIntent = new Intent(this, HighScoresActivity.class);
        startActivity(highScoresIntent);
    }

    public void mainMenu(View view) {
        Intent menuIntent = new Intent(this, MainActivity.class);
        startActivity(menuIntent);
    }
}
