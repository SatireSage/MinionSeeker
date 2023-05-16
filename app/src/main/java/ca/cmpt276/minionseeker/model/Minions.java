package ca.cmpt276.minionseeker.model;

import java.util.ArrayList;

/*
*  Class: Minions
*  Purpose: Generate Minions and Cells for a GameManager singleton instance, do checking and scanning for cells on the game board
* */

public class Minions {
    // array of minions
    private int[][] minions;
    // create an ArrayList of arrays
    private ArrayList<int[]> minionLocations;
    // number of rows
    private final int numRows;
    // number of columns
    private final int numColumns;
    // number of minions
    private final int numTotalMinions;
    // number of minions found
    private int numMinionsFound;

    //Minions()
    //Purpose: Call the game manager to get the number of rows and columns, generateMinions based on the settings obtained
    public Minions() {
        GameManager gameManager = GameManager.getInstance();
        numRows = gameManager.getBoardRows();
        numColumns = gameManager.getBoardCols();
        // create the array of minions
        minions = new int[numRows][numColumns];
        // set all values in the array to 0
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                minions[i][j] = 0;
            }
        }
        // get the total number of minions
        numTotalMinions = gameManager.getTotalMinions();
        // set the number of minions found to 0
        numMinionsFound = 0;
        // call generateMinions to generate the minions
        minionLocations = new ArrayList<>();
        generateMinions();
    }

    //minionCheck()
    //Purpose: Check if a cell has a minion inside of it
    public boolean minionCheck(int row, int column) {
        boolean isMinion = false;
        // check if arraylist is empty
        if (minionLocations != null) {
            if (minionLocations.size() == 0) {
                isMinion = false;
            } else {
                // check if the row and column are in the array
                for (int[] minionLocation : minionLocations) {
                    if (minionLocation[0] == row && minionLocation[1] == column) {
                        isMinion = true;
                        break;
                    }
                }
            }
        }
        return isMinion;
    }

    //generateMinions()
    //Purpose: Generate minions randomly across the board
    private void generateMinions() {
        // generate random minion rows and column values for the number of minions
        for (int i = 0; i < numTotalMinions; i++) {
            int randomRow = (int) (Math.random() * numRows);
            int randomColumn = (int) (Math.random() * numColumns);
            // if the random row and column values are not already occupied by a minion
            if (!minionCheck(randomRow, randomColumn)) {
                // create a new minionCoordinates array of size 2
                int[] minionCoordinates = new int[2];
                // set the first value to the random row
                minionCoordinates[0] = randomRow;
                // set the second value to the random column
                minionCoordinates[1] = randomColumn;
                // add the array to the arraylist
                minionLocations.add(minionCoordinates);
            } else {
                // try again if the random row and column values are already occupied by a
                // minion
                i--;
            }
        }
    }

    //getNumMinionsFound()
    //Purpose: Accessor for the number of minions found
    public int getNumMinionsFound() {
        return numMinionsFound;
    }

    //incrementNumMinionsFound()
    //Purpose: Increment the number of minions founds for the score display
    public void incrementNumMinionsFound() {
        this.numMinionsFound = this.numMinionsFound + 1;
    }

    //removeMinion(int row, int col)
    //Purpose: If a minion is found, remove it at its location,
    public void removeMinion(int row, int col) {
        // remove the minion from the arraylist at the given row and column
        for (int i = 0; i < minionLocations.size(); i++) {
            if (minionLocations.get(i)[0] == row && minionLocations.get(i)[1] == col) {
                minionLocations.remove(i);
                break;
            }
        }
    }

    //scanCell(int row, int col)
    //Purpose: Scan the cell if a minion is found
    public void scanCell(int row, int col) {
        int numMinions = 0;
        // check if there are any minions in the row or column of the clicked cell
        for (int[] minionLocation : minionLocations) {
            if (minionLocation[0] == row || minionLocation[1] == col) {
                numMinions++;
            }
        }
        // set the value of the clicked in the minions array
        minions[row][col] = numMinions;
    }

    //getMinions()
    //Purpose: Accessor the minions
    public int[][] getMinions() {
        return minions;
    }
}
