// TicTacToe by Anvit Nidadavolu

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {

    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!

    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public static void main(String[] args) {


        //this array list will hold strings of each turn of the game
        ArrayList<char[][]> gameHistory = new ArrayList<>();
        //runs a loop that asks the user what they want to do
        //loop ends when the user enters Q

        Scanner sc = new Scanner(System.in);
        String choice;
        String[] names = new String[2];

        do {
            System.out.println("Welcome to game of Tic Tac Toe, choose one of the following options from below:");
            System.out.println();

            System.out.println("1. Single player\n2. Two player\nD. Display last match\nQ. Quit");
            choice = sc.nextLine();

            if (choice.equals("1")) {

                System.out.println("Enter player 1 name: ");
                names[0] = sc.nextLine();
                names[1] = "Computer";

                gameHistory = runOnePlayerGame(names);
            } else if (choice.equals("2")) {
                System.out.println("Enter player 1 name: ");
                names[0] = sc.nextLine();

                System.out.println("Enter player 2 name: ");
                names[1] = sc.nextLine();

                gameHistory = runTwoPlayerGame(names);
            } else if (choice.equals("D")) {
                if (!(gameHistory.isEmpty())) {
                    runGameHistory(names, gameHistory);
                } else {
                    System.out.println("No match found.");
                    System.out.println();
                    System.out.println("Welcome to game of Tic Tac Toe, choose one of the following options from below: ");
                    System.out.println();

                }
            } else if (choice.equals("Q")) {
                System.out.println("Thanks for playing. Hope you had fun!");
            } else {
                System.out.println("Invalid input");
            }


        }
        while (!(choice.equals("Q")));

    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.
    private static String displayGameFromState(char[][] state) {


        String result = ""; // String that'll end up being returned


        //for loops that go through the state array
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                result += " " + state[x][y] + " "; //adds the chars to the string with spaces on each side
                if (y != 2) //adds a vertical line if at the correct spot
                {
                    result += "|";
                }
            }
            if (x != 2) //adds newlines and a line if at the correct spot
            {
                result += "\n";
                result += "----------";
                result += "\n";
            }
        }
        return result;

        // Returns the state of a game that has just started.
        // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
        // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    private static char[][] getInitialGameState() {
        return new char[][]{
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }

    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runTwoPlayerGame(String[] names) {

        //chooses who goes first
        System.out.println("Tossing a coin to decide who goes first!!! ");
        int active = (int) (Math.random() * 2);
        System.out.println(names[active] + " goes first");

        //Arraylist to return later
        ArrayList<char[][]> result = new ArrayList<>();

        //variables for the symbol of the player and the current game state
        char curSym;
        char[][] curState = getInitialGameState();

        //loop until win or draw
        do {

            //prints out the game board and adds it to the array which will eventually be returned later
            System.out.println(displayGameFromState(curState));
            System.out.println(names[active] + "'s turn:");

            //assigns player symbols
            if (active == 0) {
                curSym = playerOneSymbol;
            } else {
                curSym = playerTwoSymbol;
            }

            result.add(curState); //Update the results
            // Both lines below determine next move
            curState = runPlayerMove(names[active], curSym, curState);
            active = (active + 1) % 2;
        }
        while (!checkWin(curState) && !checkDraw(curState));

        result.add(curState);
        System.out.println(displayGameFromState(curState)); //prints and adds to the array the last game state


        if (checkWin(curState)) //prints if win or draw
        {
            System.out.println(names[Math.abs(active)] + " wins!");
        } else {
            System.out.println("It's a draw!");
        }

        return result;
    }

    // Given the player names (where player two is "Computer"),
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames) {

        ArrayList<char[][]> result = new ArrayList<>();//Arraylist to return later
        char[][] state = getInitialGameState();
        char curSym;
        int active = (int) (Math.random() * 2); //chooses who goes first

        System.out.println("Tossing a coin to decide who goes first!!!");
        System.out.println(playerNames[active] + " goes first.");

        //loop until win or draw
        do {

            System.out.println(displayGameFromState(state));//sets current state, prints out current state,
            // and adds string of current state to array that will be returned

            System.out.println(playerNames[active] + "'s turn: ");

            if (active == 0) {
                curSym = playerOneSymbol;
            } else {
                curSym = playerTwoSymbol;
            }
            result.add(state);

            if (active == 0) //if player one (human) is up, they do their turn
            {
                state = runPlayerMove(playerNames[active], curSym, state); //runs and adds move to game
            } else //if the robot is up then it checks the difficulty and runs the corresponding bot move
            {
                state = getCPUMove(state);
            }
            active = (active + 1) % 2;
        } while (!checkWin(state) && !checkDraw(state));

        //the rest of the code below in this method does the same as in runTwoPlayerGame
        System.out.println(displayGameFromState(state));
        result.add(state);

        if (checkWin(state)) {
            System.out.println(playerNames[Math.abs(active - 1)] + " wins!");
        } else {
            System.out.println("Draw");
        }

        return result;
    }

    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) { // Repeatedly prompts player for
        // move in current state and makes the move

        int[] move;
        do {
            move = getInBoundsPlayerMove(playerName);

            if (!checkValidMove(move, currentState)) {
                System.out.println("That space is already taken. Try again.");
            }
        } while (!checkValidMove(move, currentState));

        return makeMove(move, playerSymbol, currentState);
    }

    // Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove(String playerName) {

        Scanner sc = new Scanner(System.in);

        int[] result = new int[2];

        do {
            System.out.print(playerName + " enter row: ");
            result[0] = sc.nextInt();

            System.out.print(playerName + " enter column: ");
            result[1] = sc.nextInt();

            if (!(result[0] >= 0 && result[0] <= 2 && result[1] >= 0 && result[1] <= 2)) {
                System.out.println("That row or column is out of bounds. Try again.");
            }

        } while (!(result[0] >= 0 && result[0] <= 2 && result[1] >= 0 && result[1] <= 2));

        return result;
    }

    // Given a [row, col] move, return true if a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {

        if (state[move[0]][move[1]] == emptySpaceSymbol) {
            return true;
        }

        return false;
    }

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {

        char[][] stateCopy = new char[3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (move[0] == x && move[1] == y) {
                    stateCopy[x][y] = symbol;
                } else {
                    stateCopy[x][y] = currentState[x][y];
                }
            }
        }
        return stateCopy;
    }

    // Given a state, return true if some player has won in that state
    private static boolean checkWin(char[][] state) {

        // Horizontals

        if (state[0][0] == state[0][1] && state[0][1] == state[0][2] && state[0][0] != emptySpaceSymbol) {
            return true;
        } else if (state[1][0] == state[1][1] && state[1][1] == state[1][2] && state[1][0] != emptySpaceSymbol) {
            return true;
        } else if (state[2][0] == state[2][1] && state[2][1] == state[2][2] && state[2][0] != emptySpaceSymbol) {
            return true;
        }

        // Verticals

        else if (state[0][0] == state[1][0] && state[1][0] == state[2][0] && state[0][0] != emptySpaceSymbol) {
            return true;
        } else if (state[0][1] == state[1][1] && state[1][1] == state[2][1] && state[0][1] != emptySpaceSymbol) {
            return true;
        } else if (state[0][2] == state[1][2] && state[1][2] == state[2][2] && state[0][2] != emptySpaceSymbol) {
            return true;
        }

        // Diagonals

        else if (state[0][0] == state[1][1] && state[1][1] == state[2][2] && state[0][0] != emptySpaceSymbol) {
            return true;
        } else if (state[0][2] == state[1][1] && state[1][1] == state[2][0] && state[0][2] != emptySpaceSymbol) {
            return true;
        } else {
            return false;
        }
    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.
    private static boolean checkDraw(char[][] state) {

        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                if (state[x][y] == emptySpaceSymbol) {
                    return false;
                }
            }
        }

        return true;
    }

    // Given a game state, return a new game state with move from the AI
    // It follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getCPUMove(char[][] gameState) {

        char[][] test = Arrays.copyOf(gameState, gameState.length);

        char[][] test2 = Arrays.copyOf(test, test.length);

        int[] move = new int[2];

        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                move[0] = x;
                move[1] = y;

                if (checkValidMove(move, gameState)) {
                    test[x][y] = playerTwoSymbol;
                    if (checkWin(test)) {
                        return test;
                    }
                    test[x][y] = emptySpaceSymbol;
                }
            }
        }

        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                move[0] = x;
                move[1] = y;

                if (checkValidMove(move, gameState)) {
                    test2[x][y] = playerOneSymbol;
                    if (checkWin(test2)) {
                        test[x][y] = playerTwoSymbol;
                        return test;
                    }
                    test[x][y] = emptySpaceSymbol;
                    test2[x][y] = emptySpaceSymbol;
                }
            }
        }

        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                move[0] = x;
                move[1] = y;

                if (checkValidMove(move, gameState)) {
                    test[x][y] = playerTwoSymbol;
                    if (x == 1 && y == 1) {
                        return test;
                    }
                    test[x][y] = emptySpaceSymbol;
                }
            }
        }

        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                move[0] = x;
                move[1] = y;

                if (checkValidMove(move, gameState)) {
                    test[x][y] = playerTwoSymbol;
                    if ((x == 0 && y == 0) || (x == 2 && y == 2) || (x == 2 && y == 0) || (x == 0 && y == 2)) {
                        return test;
                    }
                    test[x][y] = emptySpaceSymbol;
                }
            }
        }

        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                move[0] = x;
                move[1] = y;
                if (checkValidMove(move, gameState)) {
                    test[x][y] = playerTwoSymbol;
                    return test;
                }
            }
        }

        return test;
    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board

    private static ArrayList<int[]> getValidMoves(char[][] gameState) {

        ArrayList<int[]> free = new ArrayList<>();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (gameState[x][y] == emptySpaceSymbol) {
                    free.add(new int[]{x, y});
                }
            }
        }
        return free;
    }

    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {


        int active = 0;

        for (char[] h : gameHistory.get(1)) {
            for (char r : h) {
                if (r == playerOneSymbol) {
                    active = 0;
                } else if (r == playerTwoSymbol) {
                    active = 1;
                }
            }
        }

        System.out.println(playerNames[0] + " (" + playerOneSymbol + ") vs. "
                + playerNames[1] + " (" + playerTwoSymbol + ")");

        for (int x = 0; x < gameHistory.size(); x++) {
            if (x == 0) {
                System.out.println(displayGameFromState(gameHistory.get(x)));
            } else {
                System.out.println(playerNames[active]);
                System.out.println(displayGameFromState(gameHistory.get(x)));
                active = (active + 1) % 2;
            }
        }

        System.out.println();

        if (checkWin(gameHistory.get(gameHistory.size() - 1))) {
            System.out.println(playerNames[active] + " wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}

