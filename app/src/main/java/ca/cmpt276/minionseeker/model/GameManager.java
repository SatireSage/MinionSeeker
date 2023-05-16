package ca.cmpt276.minionseeker.model;

import android.content.Context;
import android.content.SharedPreferences;

/*
    Class: GameManager
    Purpose: Create GameManager objects that hold game objects utilizing a singleton class which will only allow a single instance as intended.
 */
public class GameManager {

    // Arrange singleton class for GameManager
    private static GameManager instance = null;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // // Credit to the Logic below for spinner selection.
    // //
    // of:https://stackoverflow.com/questions/13680219/save-value-of-spinner-selected-item
    // SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs",
    // mContext.MODE_PRIVATE);
    // int boardSize = myPrefs.getInt("boardSize", 0);
    // int numMinions = myPrefs.getInt("numMinions", 0);

    //Variable declarations
    private int numGames;
    private int boardSize;
    private int boardRows, boardCols;
    private int numMinions;
    private int score1, score2, score3;

    //Accessor for the number of games
    public int getNumGames() {
        return numGames;
    }

    //Mutator for the number of games
    //Implementation: Use sharedPreferences to load the number of games from the options
    public void setNumGames(int numGames, Context mContext) {
        SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("gamesPlayed", numGames);
        editor.apply();
        this.numGames = numGames;
    }

    //Accessor for the board size
    public int getBoardSize() {
        return boardSize;
    }

    //Mutator for the board size
    //Implementation: Use sharedPreferences to load the size from the options
    public void setBoardSize(int boardSizeOption, Context mContext) {
        SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("boardSize", boardSizeOption);
        editor.apply();
        setBoardSizeValues(boardSizeOption);
        this.boardSize = boardSizeOption;
    }

    //Accessor for the number of rows in the board
    public int getBoardRows() {
        return boardRows;
    }

    //Mutator for the number of rows in the board
    public void setBoardRows(int boardRows) {
        this.boardRows = boardRows;
    }

    //Accessor for the number of columns in the board
    public int getBoardCols() {
        return boardCols;
    }

    //Mutator for the number of columns in the board
    public void setBoardCols(int boardCols) {
        this.boardCols = boardCols;
    }

    //Mutator for the number of columns in the board
    //Implementation: Based on the boardSize option selected from the options menu, set the size of the columns and rows according the size
    public void setBoardSizeValues(int boardSizeOption) {
        if (boardSizeOption == 0) {
            setBoardCols(6);
            setBoardRows(4);
        } else if (boardSizeOption == 1) {
            setBoardCols(10);
            setBoardRows(5);
        } else if (boardSizeOption == 2) {
            setBoardCols(15);
            setBoardRows(6);
        }
    }

    //Accessor for the number of minions
    public int getNumMinions() {
        return numMinions;
    }

    //Accessor for the number of the total of minions
    //Implementation: Based on the numMinions selected by the user in the options menu, set the numMinions var and return it.
    public int getTotalMinions() {
        int numTotalMinions = 0;
        if (numMinions == 0) {
            numTotalMinions = 6;
        } else if (numMinions == 1) {
            numTotalMinions = 10;
        } else if (numMinions == 2) {
            numTotalMinions = 15;
        } else if (numMinions == 3) {
            numTotalMinions = 20;
        }
        return numTotalMinions;
    }


    //Mutators to set the number of minions
    public void setNumMinions(int numMinionsOption, Context mContext) {
        // Set the text totalNum to the number of minions
        this.numMinions = numMinionsOption;
        saveNumMinions(mContext);
    }

    //saveNumMinions will save the number of minions using SharedPreferences for cache loading.
    public void saveNumMinions(Context mContext) {
        SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("numMinions", getNumMinions());
        editor.apply();
    }

    //Accessor for score1
    public int getScore1() {
        return score1;
    }

    //Mutator for score1
    public void setScore1(int score1, Context mContext) {
        SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("size1", 0);
        editor.apply();
        this.score1 = score1;
    }

    //Accessor for score2
    public int getScore2() {
        return score2;
    }

    //Mutator for score2
    public void setScore2(int score2, Context mContext) {
        SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("size2", 0);
        editor.apply();
        this.score2 = score2;
    }

    //Accessor for score3
    public int getScore3() {
        return score3;
    }

    //Mutator for score3
    public void setScore3(int score3, Context mContext) {
        SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("size3", 0);
        editor.apply();
        this.score3 = score3;
    }

    // Load the saved values from the options using a SharedPreferences object
    public void getSavedValues(Context mContext) {
        SharedPreferences myPrefs = mContext.getSharedPreferences("myPrefs", mContext.MODE_PRIVATE);
        int boardSize = myPrefs.getInt("boardSize", 0);
        int numMinions = myPrefs.getInt("numMinions", 0);
        int gamesPlayed = myPrefs.getInt("gamesPlayed", 0);
        int size1 = myPrefs.getInt("size1", 0);
        int size2 = myPrefs.getInt("size2", 0);
        int size3 = myPrefs.getInt("size3", 0);
        // set the values
        setBoardSize(boardSize, mContext);
        setNumMinions(numMinions, mContext);
        setNumGames(gamesPlayed, mContext);
        setScore1(size1, mContext);
        setScore2(size2, mContext);
        setScore3(size3, mContext);
    }

    //Function to set individual high scores for the specific board sizes
    public void setScore(int score, Context mContext) {
        if (boardSize == 0) {
            if (score1 == 0 || score < score1) {
                setScore1(score, mContext);
            }
        } else if (boardSize == 1) {
            if (score2 == 0 || score < score2) {
                setScore2(score, mContext);
            }
        } else if (boardSize == 2) {
            if (score3 == 0 || score < score3) {
                setScore3(score, mContext);
            }
        }
    }

}
