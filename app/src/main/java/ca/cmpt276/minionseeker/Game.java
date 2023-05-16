package ca.cmpt276.minionseeker;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import ca.cmpt276.minionseeker.model.GameManager;
import ca.cmpt276.minionseeker.model.Minions;

/*
    Class: Game inherit AppCompatActivity
    Purpose: Provide functionality to activity_game.xml, and use GameManager instance to properly run MinionSeeker and save values to SharedPreferences accordingly
 */
public class Game extends AppCompatActivity {

    //Declarations for board dimensions
    private int numColumns = 0;
    private int numRows = 0;

    //List of buttons
    ArrayList<int[]> buttonsClicked;
    Button[][] minionsButtons;

    //GameManager instance
    GameManager gameManager;

    //Minions object
    Minions minions;

    //Declarations of number of various game attributes
    int numGamesPlayed;
    int numTotalMinions;
    int numScans;

    //Object declaration for TextView elements defined in activity_game.xml respectively
    TextView scansNum, minionsFound;

    //MediaPlayer object to play mp3 sound
    MediaPlayer scanner, minionFound;

    //Alert dialog object to open when the game is won
    Dialog roundFinished;

    //Boolean for checking if the user has clicked
    boolean clicked;

    // Override the onCreate function
    // Purpose: Initialize the board and populate textView items with respective criteria.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        roundFinished = new Dialog(this);

        // Changes action bar font color
        Objects.requireNonNull(getSupportActionBar())
                .setTitle(Html.fromHtml("<font color='#0C457A'>" + getString(R.string.app_name) + "</font>"));

        TextView totalNum = findViewById(R.id.totalNum);

        scansNum = findViewById(R.id.scansNum);
        minionsFound = findViewById(R.id.foundNum);
        // set the number of columns and rows to the values from the settings saved in
        // the shared preferences

        gameManager = GameManager.getInstance();
        // call the minions class
        minions = new Minions();

        numRows = gameManager.getBoardRows();
        numColumns = gameManager.getBoardCols();

        minionsButtons = new Button[numRows][numColumns];
        populateMinions();

        numTotalMinions = gameManager.getTotalMinions();
        totalNum.setText(String.format(Locale.getDefault(), "%d", numTotalMinions));

        numScans = 0;
        scansNum.setText(String.format(Locale.getDefault(), "%d", numScans));

        minionsFound.setText(String.format(Locale.getDefault(), "%d", minions.getNumMinionsFound()));

        // initialize the arraylist
        buttonsClicked = new ArrayList<>();

        clicked = false;
    }

    // populateMinions()
    // Purpose: Populate the the table with minions
    private void populateMinions() {
        TableLayout table = findViewById(R.id.tableForMinions);
        for (int row = 0; row < numRows; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            table.addView(tableRow);
            for (int col = 0; col < numColumns; col++) {
                final int FINAL_ROW = row;
                final int FINAL_COL = col;
                Button minion = new Button(this);
                minion.setBackgroundResource(R.drawable.minion_button);
                minion.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                // create onClickListener for each minion
                minion.setOnClickListener(v -> minionClick(FINAL_ROW, FINAL_COL));
                minionsButtons[row][col] = minion;
                tableRow.addView(minion);
            }
        }
    }

    //minionClick(int FINAL_ROW, int FINAL_COL)
    //Purpose: If a cell is clicked, respectively change the board dimensions, play media, and end the game based on whether it was or was not a minion/the last minion
    private void minionClick(int FINAL_ROW, int FINAL_COL) {
        // reset the cells positions due to rotation back to 0 on the board
        resetAll();
        // call the minion check method
        boolean isMinion = minions.minionCheck(FINAL_ROW, FINAL_COL);
        // if the minion is found create a toast message
        if (isMinion) {
            // followed: https://www.youtube.com/watch?v=0SJAK_pKUoE
            // play the minion found sound using media player
            minionFound = MediaPlayer.create(this, R.raw.found_sound);
            // if scanner is playing, stop it
            if (scanner != null && scanner.isPlaying()) {
                scanner.stop();
            }
            minionFound.start();
            // remove the minion from minions class
            minions.removeMinion(FINAL_ROW, FINAL_COL);
            // increment the number of minions found
            minions.incrementNumMinionsFound();
            minionsFound.setText(String.format(Locale.getDefault(), "%d", minions.getNumMinionsFound()));
            // set the button to show the minion
            int newHeight = minionsButtons[FINAL_ROW][FINAL_COL].getHeight();
            int newWidth = minionsButtons[FINAL_ROW][FINAL_COL].getWidth();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.minion_found);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            minionsButtons[FINAL_ROW][FINAL_COL].setBackground(new BitmapDrawable(resource, scaledBitmap));
            // if all the minions are found, create an alert dialog
            if (minions.getNumMinionsFound() == numTotalMinions) {
                // play winner sound
                MediaPlayer winner = MediaPlayer.create(this, R.raw.minions_celebrate);
                winner.seekTo(12000);
                if (scanner != null && scanner.isPlaying()) {
                    scanner.stop();
                }
                if (minionFound != null && minionFound.isPlaying()) {
                    minionFound.stop();
                }
                winner.start();
                new Handler().postDelayed(winner::stop, 1500);
                // setup an alert dialog
                roundFinished.setContentView(R.layout.round_finished);
                roundFinished.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                roundFinished.setCancelable(false);
                Button finish = roundFinished.findViewById(R.id.finishButton);
                finish.setOnClickListener(v -> {
                    storeScore();
                    winner.stop();
                    roundFinished.dismiss();
                    finish();
                });
                roundFinished.show();
            }
        } else {
            // increment the number of scans
            numScans++;
            // update number of scans
            scansNum.setText(String.format(Locale.getDefault(), "%d", numScans));
            // display value
            updateCell(FINAL_ROW, FINAL_COL);
            // create a new minionCoordinates array of size 2
            int[] buttonCoordinates = new int[2];
            // set the first value to the random row
            buttonCoordinates[0] = FINAL_ROW;
            // set the second value to the random column
            buttonCoordinates[1] = FINAL_COL;
            // add the array to the arraylist
            buttonsClicked.add(buttonCoordinates);
            // play scanner sound if not a minion
            scanner = MediaPlayer.create(this, R.raw.scanner_sound);
            scanner.seekTo(1000);
            scanner.start();
            // stop the sound after 2 seconds
            new Handler().postDelayed(scanner::stop, 2000);
        }
        // rotate the buttons in the same row and column
        rotateRow(FINAL_ROW);
        rotateCol(FINAL_COL);
        updateBoard();
        // check if number of scans is 1 or 1 minion found
        if (!clicked) {
            // increment the number of games played and
            int numGames = gameManager.getNumGames();
            numGames++;
            // save the number of games played in shared preferences
            gameManager.setNumGames(numGames, this);
            clicked = true;
        }
    }

    //rotateRow(int row)
    //Purpose: Rotate board animation
    private void rotateRow(int row) {
        // fade the from the left to the right one by one with a delay between each one
        for (int col = 0; col < numColumns; col++) {
            Button minion = minionsButtons[row][col];
            minion.animate().rotationBy(360).setDuration(500);
            minion.setAlpha(0f);
            minion.animate().alpha(1f).setDuration(1500);
        }
    }

    //rotateCol(int col)
    //Purpose: Rotate column animation
    private void rotateCol(int col) {
        for (int row = 0; row < numRows; row++) {
            Button minion = minionsButtons[row][col];
            minion.animate().rotationBy(360).setDuration(500);
            minion.setAlpha(0f);
            minion.animate().alpha(1f).setDuration(1500);
        }
    }

    //resetAll()
    //Purpose: Reset the position of all the buttons to zero
    private void resetAll() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                Button minion = minionsButtons[row][col];
                minion.setRotation(0);
            }
        }
    }

    //updateCell(int row, int col)
    //Purpose: Update the cell after scanning it
    private void updateCell(int row, int col) {
        minions.scanCell(row, col);
        int[][] minions_array = minions.getMinions();
        Button minion = minionsButtons[row][col];
        minion.setText(String.format(Locale.getDefault(), "%d", minions_array[row][col]));
    }

    //updateBoard()
    //Purpose:Update the text of the buttons that have been clicked
    private void updateBoard() {
        for (int i = 0; i < buttonsClicked.size(); i++) {
            int[] buttonCoordinates = buttonsClicked.get(i);
            int row = buttonCoordinates[0];
            int col = buttonCoordinates[1];
            updateCell(row, col);
        }
    }

    //storeScore()
    //Purpose: Obtain the score the game ie, the number of scans.
    private void storeScore() {
        gameManager.setScore(numScans, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (scanner != null && scanner.isPlaying()) {
            scanner.stop();
        }
        if (minionFound != null && minionFound.isPlaying()) {
            minionFound.stop();
        }
    }

}