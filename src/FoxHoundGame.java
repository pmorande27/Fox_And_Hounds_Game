
import java.util.Scanner;

/** 
 * The Main class of the fox hound program.
 * 
 * It contains the main game loop where main menu interactions
 * are processed and handler functions are called.
  */
public class FoxHoundGame {

    /** 
     * This scanner can be used by the program to read from
     * the standard input. 
     * 
     * Every scanner should be closed after its use, however, if you do
     * that for StdIn, it will close the underlying input stream as well
     * which makes it difficult to read from StdIn again later during the
     * program.
     * 
     * Therefore, it is advisable to create only one Scanner for StdIn 
     * over the course of a program and only close it when the program
     * exits. Additionally, it reduces complexity.
     */
    private static final String ERROR_MESSAGE_SAVING = "ERROR: Saving file failed.";
    private static final String ERROR_MESSAGE_LOADING = "ERROR: Loading file failed.";
    private static final Scanner STDIN_SCAN = new Scanner(System.in);
    private static final String HOUND_WINS = "Hound Wins";
    private static final String FOX_WINS = "Fox Wins";
    private static final String INVALID_MOVE = "ERROR: Invalid Move";

    /**
     * Swap between fox and hounds to determine the next
     * figure to move.
     * 
     * @param currentTurn last figure to be moved
     * @return next figure to be moved
     */
    private static char swapPlayers(char currentTurn) {
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    /**
     * The main loop of the game. Interactions with the main
     * menu are interpreted and executed here.
     * 
     * @param dim the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     */
    private static void gameLoop(int dim, String[] players) {
        // start each game with the Fox
        char turn = FoxHoundUtils.FOX_FIELD;
        boolean exit = false;
        while(!exit) {
            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);
            // handle menu choice
            switch (choice) {
                case FoxHoundUI.MENU_LOAD:
                    try{
                        char newTurn = FoxHoundIO.loadGame(players,FoxHoundUI.fileQuery(STDIN_SCAN));
                        if
                        (newTurn =='#'){
                            System.err.println(ERROR_MESSAGE_LOADING);
                        }else{
                            turn = newTurn;
                            FoxHoundUI.displayBoard(players, dim);
                        }
                    }
                    catch (IllegalArgumentException | NullPointerException e){
                        System.err.println(ERROR_MESSAGE_LOADING);
                    }
                    break;

                case FoxHoundUI.MENU_SAVE:
                    try {
                        if (!FoxHoundIO.saveGame(players,turn,FoxHoundUI.fileQuery(STDIN_SCAN))){
                            System.err.println(ERROR_MESSAGE_SAVING);
                        }
                    }
                    catch (NullPointerException | IllegalArgumentException e){
                        System.err.println(ERROR_MESSAGE_SAVING);
                    }
                    break;

                case FoxHoundUI.MENU_MOVE:
                    String[] OriginAndDestiny = FoxHoundUI.positionQuery(dim, STDIN_SCAN);
                    String origin = OriginAndDestiny[0];
                    String destination = OriginAndDestiny[1];
                    FoxHoundGame.updatePlayer(dim, players, origin, destination, turn);
                    FoxHoundUI.displayBoard(players, dim);
                    if (FoxHoundUtils.isHoundWin(players,dim)){
                        System.out.println(HOUND_WINS);
                        exit = true;
                    }
                    if (FoxHoundUtils.isFoxWin(players[players.length-1])){
                        System.out.println(FOX_WINS);
                        exit = true;
                    }
                    turn = swapPlayers(turn);
                    break;
                case FoxHoundUI.MENU_EXIT:
                    exit = true;
                    break;
                default:
                    System.err.println("ERROR: invalid menu choice: " + choice);
            }
        }
    }
    private  static void updatePlayer(int dim, String[] player, String origin, String destination, char turn) {
        while (true) {
            if (FoxHoundUtils.isValidMove(dim, player, turn, origin, destination)) {
                for (int i = 0; i < player.length; i++) {
                    if (player[i].equals(origin)) {
                        player[i] = destination;
                    }
                }
                break;
            } else {
                System.err.println(INVALID_MOVE);
                String[] OriginAndDestiny = FoxHoundUI.positionQuery(dim, STDIN_SCAN);
                origin = OriginAndDestiny[0];
                destination = OriginAndDestiny[1];
            }
        }
    }
    /**
     * Entry method for the Fox and Hound game. 
     * 
     * The dimensions of the game board can be passed in as
     * optional command line argument.
     * 
     * If no argument is passed, a default dimension of 
     * {@value FoxHoundUtils#DEFAULT_DIM} is used. 
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be
     * board dimensions.
     */
    public static void main(String[] args)  {
        System.out.println("Enter Dimension of the board");
        int dimension = STDIN_SCAN.nextInt();
        String[] players = FoxHoundUtils.initialisePositions(dimension);
        FoxHoundUI.displayBoard(players, dimension);
        gameLoop(dimension, players);

        // Close the scanner reading the standard input stream       
        STDIN_SCAN.close();
    }
}
