package ca.cmpt276.minionseeker;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

import ca.cmpt276.minionseeker.model.GameManager;

/*
* Class: OptionsMenu inherit AppCompatActivity
* Purpose: Provide functionality to the options activity*/
public class OptionsMenu extends AppCompatActivity {

    //Create a gameManager object
    GameManager gameManager;

    //onCreate()
    //The main purpose of this override is to create spinner dropdown menus and populate them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);

        // Changes action bar font color
        Objects.requireNonNull(getSupportActionBar())
                .setTitle(Html.fromHtml("<font color='#0C457A'>" + getString(R.string.app_name) + "</font>"));

        // Followed logic
        // of:https://code.tutsplus.com/tutorials/how-to-add-a-dropdown-menu-in-android-studio--cms-37860
        Spinner spinnerBoards = findViewById(R.id.spinner_boards);
        Spinner spinnerMinions = findViewById(R.id.spinner_minions);

        //Create dropdown menus
        ArrayAdapter<CharSequence> adapterBoards = ArrayAdapter.createFromResource(this, R.array.boards_sizes_text,
                android.R.layout.simple_spinner_item);
        adapterBoards.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBoards.setAdapter(adapterBoards);

        ArrayAdapter<CharSequence> adapterMinions = ArrayAdapter.createFromResource(this, R.array.number_minions_text,
                android.R.layout.simple_spinner_item);
        adapterMinions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinions.setAdapter(adapterMinions);

        gameManager = GameManager.getInstance();

        //Get BoardSize and numMinions from the gameManager instance taken from the options menu
        int boardSize = gameManager.getBoardSize();
        int numMinions = gameManager.getNumMinions();

        spinnerBoards.setSelection(boardSize);
        spinnerMinions.setSelection(numMinions);

        // create onclick listener for the spinner to save the selected value
        spinnerBoards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // call instance of GameManger class and size of the board to the selected value
                gameManager.setBoardSize(position, OptionsMenu.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // create onclick listener for the spinner to save the selected value
        spinnerMinions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // call instance of GameManger class and set the number of minions to th
                // selected value
                gameManager.setNumMinions(position, OptionsMenu.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // clearGames(View view)
    // Purpose: Clear the games and high scores
    public void clearGames(View view) {
        // create alert dialog to confirm the user wants to clear the games and high
        // scores
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Clear Games and High Scores");
        builder.setMessage("Are you sure you want to clear all games and high scores?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            gameManager = GameManager.getInstance();
            gameManager.setNumGames(0, OptionsMenu.this);
            gameManager.setScore1(0, OptionsMenu.this);
            gameManager.setScore2(0, OptionsMenu.this);
            gameManager.setScore3(0, OptionsMenu.this);
            // Create a toast to let the user know the games have been cleared
            Toast.makeText(OptionsMenu.this, "Games and High Scores Cleared", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}