import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Objects;


/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all user interface related
 * functionality such as printing menus and displaying the game board.
 */
public class FoxHoundUI {

    /** Number of main menu entries. */
    private static final int MENU_ENTRIES =4;
    /** Main menu display string. */
    private static final String MAIN_MENU =
        "\n1. Move\n2. Exit\n3. Save\n4. Load\n\nEnter 1 - 2 - 3 - 4:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;
    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 2;
    /** Menu entry to Save the program*/
    public static final int MENU_SAVE = 3;
    /** Menu entry to Load the program */
    public static final int MENU_LOAD = 4;

    /**
     * Public method used to display a board of n by n dimensions, taking into account the current position of the Hounds and the fox.
     * It will be displayed by print commands. Fox will be represented by 'F', Hounds with an 'H' and an empty space by '.'.
     * It should also display the letters of the columns and the numbers of the rows (starting at 0) at both sides of the Board.
     * In the case that the dimension (n) of the board is bigger than 10 the numbers from 1 to 9 (including) will have a 0 at the beginning (Ex:01).
     * It has 5 helper methods
     *
     * @param players  array with current position of the players.
     * @param dimension  number that describes the dimension of the board.
     * @throws NullPointerException if it receives a Null array of Coordinates (players).
     * @throws IllegalArgumentException if it receives a dimension that is not in range of the allowed values (between 4 and 26).
     */
    public static void displayBoard(String[] players, int dimension) {
        if (players == null){
            throw new NullPointerException("Not valid array of Coordinates");
        }
        if (dimension<FoxHoundUtils.MIN_DIM|| dimension>FoxHoundUtils.MAX_DIM){
            throw new IllegalArgumentException(FoxHoundUtils.ERROR_DIMENSION);
        }
        if (!FoxHoundUtils.isinBoardplayers(players,dimension)) {
            throw new IllegalArgumentException("Not valid");
        }
        String[][] coordinatesOfBoard = FoxHoundUtils.getCoordinatesOfBoard(dimension);
        String[] lettersOfBoard = FoxHoundUtils.getLettersOfBoard(dimension);

        printSpaces(dimension,2);
        printLetters(lettersOfBoard);
        if (dimension>=10){
            System.out.print(" ");
        }
        System.out.print(" \n");

        printSpaces(dimension,1);
        printPositions(dimension,players,coordinatesOfBoard);
        printSpaces(dimension,2);
        printLetters(lettersOfBoard);
        System.out.print("\n");
    }

    /**
     * Private Method that is used in the class as a Helper method of displayBoard.
     * It checks if there is a Hound  a given coordinate, , is a Helper Method of printBoard.
     *
     * @param coordinate,  coordinate that it is being checked.
     * @param positions, array fo strings that contains the positions (in coordinate form) of all the pieces.
     * @return it returns a boolean (result) that will be false if the Hound is not found and true if it is found.
     */
    private static boolean isHoundHere(String coordinate, String[] positions) {
        boolean result = false;
        for (int i = 0; i<positions.length-1; i++){
            if (coordinate.equals(positions[i])){
                result = true;
                break;
            }
        }
        return result;
    }
    /**
     * Private Method that is used in the class as a Helper method of displayBoard.
     *It checks if the fox is in a given coordinate, is a Helper Method of printBoard.
     *
     * @param coordinate, coordinate that it is being checked.
     * @param positions, array fo strings that contains the positions (in coordinate form) of all the pieces, in this case we care only about the fox.
     * @return boolean (result) that will be false if the Fox is not found and true if it is found.
     */
    private static boolean isFoxHere(String coordinate, String[] positions) {
        boolean result = false;
        if (coordinate.equals(positions[positions.length-1])){
            result = true;
        }
        return result;
    }

    /**
     * This method is a helper function that prints all the letters of the board from 'A' to the letter number n.
     *
     * @param lettersOfBoard array that contains all the letters of the board.
     */
    private static void printLetters(String[] lettersOfBoard){
        for (String s : lettersOfBoard) {
            System.out.print(s);
        }
        System.out.print(" ");

    }

    /**
     *Private method that is used as a helper method of PrintBoard.
     * It prints all the spaces needed in each case, so that all numbers and letters fit in their respective positions.
     *
     * @param dimension  number that describes the dimension of the board.
     * @param form as different type of spaces are needed, this number allows to, with just one method, print them when it is needed.
     */
    private static void printSpaces(int dimension, int form) {
        if (form == 1) {
            if (dimension >= 10) {
                System.out.print("\n");
            } else {
                System.out.print("\n");
            }
        } else {
            if (dimension >= 10) {
                System.out.print("   ");
            } else {
                System.out.print("  ");
            }
        }
    }
    /**
     * Private method that is used as a helper method of PrintBoard.
     * It prints the characters that represent the empty space(.), fox(F) and Hound(H).
     * It is important to note that the numbers of the rows should start at 1 and not at zero (Chess-based board).
     *
     * @param dimension number that describes the dimension of the board.
     * @param players array which contains the current position of all the pieces.
     * @param coordinates array that contains all the possible coordinates of the board.
     */
    private static void printPositions(int dimension,String[] players,String[][] coordinates){
        for (int s = 0; s < dimension; s++) {
            if (dimension>=10 && s<9){
                System.out.print("0" + (s + 1) + " ");
            }
            else{
                System.out.print( (s + 1) + " ");
            }
            for (int j = 0; j < dimension; j++) {
                if(isFoxHere(coordinates[s][j],players)){
                    System.out.print("F");
                }
                else if (isHoundHere(coordinates[s][j], players)){
                    System.out.print("H");
                }
                else {
                    System.out.print(".");
                }
            }
            if (dimension>=10 && s<9){
                System.out.print(" "+"0" + (s + 1)+"\n");
            }
            else{
                System.out.print(" "+ (s + 1)+"\n") ;
            }
        }
        System.out.print("\n");
    }
    /**
     * Public Method that displays a fancy form of the board (is the one that is being used currently), it has some Helper functions that allows its functioning.
     * This method does the basically the same as printBoard, However it prints the board in a more fancy way (there is a line of separation between each row of positions and the separation is a line formed of characters that create a cell).
     *
     * @param dimension number that describes the dimension of a board n by n.
     * @param players array that contains the current position of all the pieces (Fox and Hounds) in coordinate form.
     * @throws NullPointerException if the the array of players is null.
     * @throws IllegalArgumentException if the dimension is not in the range of accepted values (between 4 and 26)
     */
    public static void displayBoardFancy (int dimension, String[] players){
        if (players == null){
            throw new NullPointerException(FoxHoundUtils.ERROR_NULL);
        }
        if (players.length != dimension/2 +1){
            throw new IllegalArgumentException(FoxHoundUtils.ERROR_PLAYERS);
        }
        if (dimension<FoxHoundUtils.MIN_DIM || dimension >FoxHoundUtils.MAX_DIM){
            throw new IllegalArgumentException(FoxHoundUtils.ERROR_DIMENSION);
        }
        String[][] coordinates = FoxHoundUtils.getCoordinatesOfBoard(dimension);
        printSpaces2(dimension,2);
        printLetters2(dimension);
        for(int i = 0;i<dimension;i++){
            printSpaces2(dimension,1);
            printBars(dimension);
            printBoard(dimension,players,coordinates,i);
        }
        printSpaces2(dimension,1);
        printBars(dimension);
        printSpaces2(dimension,1);
        printLetters2(dimension);
    }

    /**
     * Private Method that is used in the class as a Helper method of displayBoardFancy.
     * This method helps to print all the letters of the board, going from 'A' to the letter n of the alphabet, the method adds also the spaces required between letters so that each of them is in its required position.
     *
     * @param dimension integer that describes the dimension of a board n by n.
     */
    private static void printLetters2(int dimension) {
        String[] lettersOfBoard=FoxHoundUtils.getLettersOfBoard(dimension);
        for (int i = 0; i < dimension; i++) {
            System.out.print("  " + lettersOfBoard[i] + " ");
        }
        System.out.print("\n");
    }

    /**
     * Private Method that is used in the class as a Helper method of displayBoardFancy.
     * This Method is used to print some spaces that are needed when printing the board, it is used to avoid code-repeating.
     *
     * @param dimension integer that describes the dimension of a board n by n.
     * @param format as different type of spaces are needed, this number allows to, with just one method, print them when it is needed.
     */

    private static void printSpaces2(int dimension,int format){
        if (format == 1){
            if (dimension>=10){
                System.out.print(" ");
            }
            System.out.print("  ");
        }else if (format == 2){
            if (dimension>=10){
                System.out.print(" ");
            }
            System.out.print("  ");
        }
    }

    /**
     * Private Method that is used in the class a sa Helper method of displayBoardFancy.
     * This particular Method is used to print the bars ("|===") that, in the fancy board separate all the components of the board.
     *
     * @param dimension integer that describes the dimension of a board n by n.
     */
    private static void printBars(int dimension){
        for (int a= 0;a<dimension;a++ ){
            System.out.print("|===");
        }
        System.out.println("|");
    }
    private static void printBoard (int dimension,String[] players, String[][] coordinates, int positionNumber){
        if (!FoxHoundUtils.isinBoardplayers(players,dimension)){
            throw new IllegalArgumentException("Not valid");
        }
        if (dimension>=10&& positionNumber<9){
            System.out.print("0"+(positionNumber+1)+ " |");
        }
        else {
            System.out.print((positionNumber+1)+ " |");
        }
        for(int k=0;k<dimension;k++){
            String foxOrHoundOrEmpty;
            if (isFoxHere(coordinates[positionNumber][k],players)){
                foxOrHoundOrEmpty = "F";
            }
            else if (isHoundHere(coordinates[positionNumber][k],players)){
                foxOrHoundOrEmpty = "H";
            }else{
                foxOrHoundOrEmpty =" ";
            }
            System.out.print(" "+ foxOrHoundOrEmpty+ " |");
        }
        if (dimension>=10&& positionNumber<9){
            System.out.println(" 0"+(positionNumber+1));
        }
        else {
            System.out.println(" "+ (positionNumber+1));
        }
    }

    /**
     * Public method that asks the user for valid coordinates to the user.
     * In case that the User does not give a valid position this function will return an error message and it will make a recursive call to itself so that is asks the coordinates again.
     *
     * @param dimension number that describes the dimension of the board.
     * @param scan  a Scanner object to read user input from.
     * @return it returns an array of Strings that contains the original position and the destination of the move.
     * @throws NullPointerException if the given Scanner is null.
     * @throws IllegalArgumentException if the dimension is not in the range between 4 and 26.
     */
    public static String[] positionQuery (int dimension, Scanner scan){

        Objects.requireNonNull(scan, "Given Scanner must not be null");

        if (dimension>FoxHoundUtils.MAX_DIM|| dimension<FoxHoundUtils.MIN_DIM){
            throw new IllegalArgumentException(FoxHoundUtils.ERROR_DIMENSION);
        }
        String[][] coordinates = FoxHoundUtils.getCoordinatesOfBoard(dimension);
        String[] pairOfPositions = new String[2];
        while (true){
            System.out.print("Provide origin and destination coordinates.\n" +"Enter two positions between "+ coordinates[0][0] + "-"+ coordinates[dimension-1][dimension-1]+":\n");
            pairOfPositions[0] = scan.next();
            pairOfPositions[1] = scan.next();
            if (!FoxHoundUtils.isInBoard(pairOfPositions[0],dimension)||(!FoxHoundUtils.isInBoard(pairOfPositions[1],dimension))) {
                System.err.print("ERROR: Please enter valid coordinate pair separated by space.");
                System.out.print("\n");
            }
            else{
                break;
            }
        }
        return  pairOfPositions;
    }

    /**
     * Print the main menu and query the user for an entry selection.
     * 
     * @param figureToMove the figure type that has the next move.
     * @param stdin a Scanner object to read user input from.
     * @return a number representing the menu entry selected by the user.
     * @throws IllegalArgumentException if the given figure type is invalid.
     * @throws NullPointerException if the given Scanner is null.
     */
    public static int mainMenuQuery(char figureToMove, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        if (figureToMove != FoxHoundUtils.FOX_FIELD 
         && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }
        String nextFigure = 
            figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";
        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }
            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }
            stdin.nextLine(); // throw away the rest of the line
        }
        return input;
    }
    public static Path fileQuery(Scanner stdn){
        System.out.println("Enter file path:");
        Objects.requireNonNull(stdn,FoxHoundUtils.ERROR_NULL);
        String reading = stdn.nextLine();
        while (true){
        try{
            return Paths.get(reading);
        }
        catch (InvalidPathException e){
            System.err.println(FoxHoundIO.ERROR_PATH);

            }
        }
    }
}







