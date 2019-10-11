package blitwin.mscs722.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private TextView scoreDisplay;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        // get score from GameActivity
        score = getIntent().getExtras().get("score").toString();
        scoreDisplay = (TextView) findViewById(R.id.gameOverScore);
        // set GameOverActivity's score display to the score passed in
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
