package ca.cmpt276.minionseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

import ca.cmpt276.minionseeker.model.GameManager;

/*
*   Class scoreMenu, inherit appCompatActivity
*   Purpose: Provide functionality to the score menu activity
* */

public class ScoreMenu extends AppCompatActivity {

    // Override the onCreate function such that when the app starts
    // Assign according TextViews to display their respective purpose
    // ie, TextView with id scoreNum3 should display the score3 value loaded from the gameManagers singleton class.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_menu);

        //Create singleton class instance
        GameManager gameManager = GameManager.getInstance();

        // Changes action bar font color
        Objects.requireNonNull(getSupportActionBar())
                .setTitle(Html.fromHtml("<font color='#0C457A'>" + getString(R.string.app_name) + "</font>"));

        // Set the number of times the game is played
        TextView numPlays = findViewById(R.id.numPlays);
        int numGamesPlayed = gameManager.getNumGames();
        numPlays.setText(String.format(Locale.getDefault(), "%d", numGamesPlayed));

        TextView s1 = findViewById(R.id.scoreNum1);
        int score1 = gameManager.getScore1();
        s1.setText(String.format(Locale.getDefault(), "%d", score1));

        TextView s2 = findViewById(R.id.scoreNum2);
        int score2 = gameManager.getScore2();
        s2.setText(String.format(Locale.getDefault(), "%d", score2));

        TextView s3 = findViewById(R.id.scoreNum3);
        int score3 = gameManager.getScore3();
        s3.setText(String.format(Locale.getDefault(), "%d", score3));
    }
}