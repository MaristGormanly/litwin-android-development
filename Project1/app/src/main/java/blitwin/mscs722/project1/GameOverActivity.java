package blitwin.mscs722.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    public void launchGame(View view) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }
}
