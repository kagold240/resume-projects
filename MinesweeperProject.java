/**
 * The purpose of this class is to create a console-controlled Minesweeper game for
 the user to play
 * CSE 174
 * Section A
 *
 */
//create initializeGrid method
import java.util.Random;
import java.util.Scanner;
public class MinesweeperProject
{
    //create initializeGrid method
    public static String[][] initializeGrid(int rows, int cols)
    {
        String[][] grid = new String[rows][cols];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = "\uD83D\uDFEB";
            }
        }
        return grid;
    }
    //create placeMines method
    public static void placeMines(String[][] grid, int rows, int cols)
    {
//create new instance of class random
        Random rand = new Random();
//initialize int variables to hold random values that will be generated for row, column
        int bombRow;
        int bombCol;
//initialize boolean variable to hold random value--this value will then be used to determine which type of bomb is placed
        boolean randVal;
//initialize a mineCounter to keep track of the # of mines placed
        int mineCounter = 0;
//begin while loop that iterates until 20 bombs have been placed
        while (mineCounter < rows*2)
        {
            bombRow = rand.nextInt(rows);
            bombCol = rand.nextInt(cols);
            if(bombRow+bombCol>1)
            {
                randVal = rand.nextBoolean();
                if (randVal)
                {
//ensure that the bomb is not re-placed in a spot which has already been assigned a bomb
//doing so guarantees that there will be exactly twenty bombs on the grid--no less (which could occur if a bomb was placed in the same space twice
                    if(grid[bombRow][bombCol].equals("\uD83D\uDFEB"))
                    {
                        grid[bombRow][bombCol] = "\uD83D\uDEA8";
                        mineCounter++;
                    }
                }
                else
                {
                    if(grid[bombRow][bombCol].equals("\uD83D\uDFEB"))
                    {
                        grid[bombRow][bombCol] = "\uD83D\uDCA3";
                        mineCounter++;
                    }
                }
            }
        }
    }
    //create printGrid method
    public static void printGrid(String[][] grid, int rows, int cols)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
    //create detectNearbyMines method
    public static void detectNearbyMines(String[][] grid, int row, int col)
    {
        int undetectableMinesCounter = 0;
        int detectableMinesCounter = 0;
        if(row+1<10) {
            if (grid[row + 1][col].equals("\uD83D\uDEA8"))
            {
                undetectableMinesCounter++;
            }
            if(grid[row+1][col].equals("\uD83D\uDCA3"))
            {
                detectableMinesCounter++;
                grid[row+1][col] = "\u2705";
            }
        }
        if(col+1<10)
        {
            if (grid[row][col + 1].equals("\uD83D\uDEA8"))
            {
                undetectableMinesCounter++;
            }
            if(grid[row][col+1].equals("\uD83D\uDCA3"))
            {
                detectableMinesCounter++;
                grid[row][col+1] = "\u2705";
            }
        }
        if(row-1>=0) {
            if (grid[row - 1][col].equals("\uD83D\uDEA8"))
            {
                undetectableMinesCounter++;
            }
            if(grid[row-1][col].equals("\uD83D\uDCA3"))
            {
                detectableMinesCounter++;
                grid[row-1][col] = "\u2705";
            }
        }
        if(col-1>=0) {
            if (grid[row][col - 1].equals("\uD83D\uDEA8"))
            {
                undetectableMinesCounter++;
            }
            if(grid[row][col-1].equals("\uD83D\uDCA3"))
            {
                detectableMinesCounter++;
                grid[row][col-1] = "\u2705";
            }
        }
        System.out.println(detectableMinesCounter + " regular mine(s) were detected and swept nearby, but there's still " + undetectableMinesCounter + " undetectable mine(s) nearby!");
    }
    public static void main(String[] args)
    {
//Call initializeGrid
        String[][] grid = initializeGrid(10, 10);
//Call placeMines
        placeMines(grid, 10, 10);
//Call printGrid
        printGrid(grid, 10, 10);
//Start game loop
        Scanner keyboard = new Scanner(System.in);
        int stepsCounter = 0;
        String move;
        int rowPointer = 0;
        int colPointer = 0;
        while(stepsCounter < 15)
        {
            System.out.print("W/A/S/D to move, Q to quit: ");
            move = keyboard.next();
//move the pointer according to the player input and ensure they are not moving outside the bounds of the grid
//should not be able to move left if colPointer is already at 0
//should not be able to move right if colPointer is already at 9
//should not be able to move up if rowPointer is already at 0
//should not be able to move down if rowPointer is already at 9
//so these conditions must be accounted for in the if statements
//move rowPointer up if player pressed w
            if(move.equalsIgnoreCase("w") && rowPointer > 0)
            {
                rowPointer--;
                stepsCounter++;
            }
//move rowPointer down if player pressed s
            else if(move.equalsIgnoreCase("s") && rowPointer < 9)
            {
                rowPointer++;
                stepsCounter++;
            }
//move colPointer right if player pressed d
            else if(move.equalsIgnoreCase("d") && colPointer < 9)
            {
                colPointer++;
                stepsCounter++;
            }
//move colPointer left if player pressed a
            else if(move.equalsIgnoreCase("a") && colPointer > 0)
            {
                colPointer--;
                stepsCounter++;
            }
//if player has pressed q break the game loop
            else if(move.equalsIgnoreCase("q"))
            {
                break;
            }
//if user entered something besides w/a/s/d or q, display an error message and re-prompt them
            else
            {
                System.out.println("Invalid input. You've attempted to move out of bounds or entered an invalid character. Please try again.");
                continue;
            }
//if player has hit an undetectable mine, break the loop
            if(grid[rowPointer][colPointer].equals("\uD83D\uDEA8"))
            {
                grid[rowPointer][colPointer] = "\uD83D\uDD25";
                break;
            }
//detect mines and increment stepCounter
            detectNearbyMines(grid, rowPointer, colPointer);
        }
//display losing message/winning message/exit message
        if(stepsCounter==15)
        {
            System.out.println("Congratulations! You have won the game!");
            printGrid(grid, 10, 10);
        }
        else if(grid[rowPointer][colPointer].equals("\uD83D\uDD25"))
        {
            System.out.println("Game over! You hit a mine");
            printGrid(grid, 10, 10);
        }
        else
        {
            System.out.println("You have exited the game. Game over.");
        }
//close Scanner
        keyboard.close();
    }
}