
/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
public class FoxHoundUtils {

    // ATTENTION: Changing the following given constants can 
    // negatively affect the outcome of the auto grading!

    /**
     * Default dimension of the game board in case none is specified.
     */
    public static final int DEFAULT_DIM = 8;
    /**
     * Default length of the Array of players.
     */
    public static final int DEFAULT_LENGTH = 5;
    /**
     * Minimum possible dimension of the game board.
     */
    public static final int MIN_DIM = 4;
    /**
     * Maximum possible dimension of the game board.
     */
    public static final int MAX_DIM = 26;

    /**
     * Symbol to represent a hound figure.
     */
    public static final char HOUND_FIELD = 'H';
    /**
     * Symbol to represent the fox figure.
     */
    public static final char FOX_FIELD = 'F';
    /**
     * Error message that appears if the dimension (argument of several functions) is not in the valid range.
     */
    public static final String ERROR_DIMENSION = "Not valid dimension, must be between 4 and 26";
    /**
     * Error messafe that appears if there is a problem with a given coordinate;
     */
    public static final String ERROR_COORDINATES = "Not valid coordinates";
    /**
     * Error message that appears if the array of players is not valid
     */
    public static final String ERROR_PLAYERS = "Not valid array of Players";
    /**
     * Error message that appears if the given turn (char) is not valid
     */
    public static final String ERROR_CHAR = "Not valid turn char";
    /**
     * Error message that appears if a required object for a method is null.
     */
    public static final String ERROR_NULL = "Object must not be null";

    // HINT Write your own constants here to improve code readability ...
//---------------------------initialisePositions-----------------------------------------------------------------------------//
    /**
     * Public Method that is used to get The Initial position of the pieces in the board.
     *
     * This method will have other helper methods that will allow its functioning.
     *
     * @param dimension integer that describes the dimensions of a board n by n.
     * This is integer is used to determine the initial position and number (in the case of the hounds) of the Hounds and the Fox in the board.
     * @return an array of Strings that contains the initial coordinates of all the Hounds and the Fox.
     * @throws IllegalArgumentException if the Dimension given is not in the allowed range (between 4 and 26).
     */
    public static String[] initialisePositions(int dimension) {
        if (dimension<MIN_DIM || dimension> MAX_DIM){
            throw new IllegalArgumentException(ERROR_DIMENSION);
        }
        String[] lettersOfBoard = getLettersOfBoard(dimension);
        return getInitialPositions(dimension, lettersOfBoard);
    }

    /**
     * Public Method that is used in different classes to obtain all the letters of a Board of dimensions n by n.
     * It is also used as a Helper Method in the method inInitialisePositions.
     *
     * @param dimensionOfBoard Integer that describes the dimensions of a board n by n (Chess based).
     * @return an array that contains all the letters of a Board
     * @throws IllegalArgumentException if the dimension is not in the valid range (between 4 and 26).
     */

    public static String[] getLettersOfBoard(int dimensionOfBoard) {
        if (dimensionOfBoard<MIN_DIM || dimensionOfBoard> MAX_DIM) {
            throw new IllegalArgumentException(ERROR_DIMENSION);
        }
        String[] letters = new String[dimensionOfBoard];
        for (int i = 0; i < dimensionOfBoard; i++) {
            letters[i] = "" + ((char) (65 + i));
        }
        return letters;
    }

    /**
     * Private Method that is used in the class as a Helper  Method of inInitialisePositions.
     * It is used to generate an array of strings that contain all the initial positions of the Hounds and the Fox.
     * It uses a helper method (isBlack) to check if the assumed middle column is black or not.
     *
     * @param dimension Integer that describes the dimensions of a board n by n (Chess based).
     * @param lettersOfBoard Array of Strings that contain al the possible letters of the board.
     * @return an array of String which, contains all the coordinates of the initial position of the pieces.
     */
    private static String[] getInitialPositions(int dimension, String[] lettersOfBoard) {

        String[] initialPositions = new String[dimension / 2 + 1];//Number of Hounds +1 fox.
        int leap = 0;
        for (int Hounds = 0; Hounds < dimension / 2; Hounds++) {
            //Get the initial position of the Hounds (They are all in row 1 and the letter starts in B (ASCII Code 66) and then makes a jump of two letters each time from B to D...
            initialPositions[Hounds] = "" + ((char) (66 + leap)) + 1;
            leap += 2;
        }if (dimension%2 != 0){//If it is not even the board
            //Now we have to get the initial position of the fox that always will be in the last row
            if (isBlack(dimension)) {
                initialPositions[dimension / 2] = lettersOfBoard[dimension / 2] + dimension; //if the middle column is black just put it there (middle column is taken as dimension/2 for both even and odd dimensions)
            } else {
                initialPositions[dimension / 2] = lettersOfBoard[dimension / 2 + 1] + dimension; // if it is white move one column to the right
            }
            return initialPositions;//Needs further comment to explain.
        }else{//If it is even
            if (isBlack(dimension)) {
                initialPositions[dimension / 2] = lettersOfBoard[dimension / 2 ] + dimension; //if the middle column is black put it there
            }else{
                initialPositions[dimension/2] = lettersOfBoard[dimension/2-1] + dimension; //if the assumed middle column is white put it in the other middle column.
            }
        }
        return initialPositions;
    }

    /**
     * Private Method that is used in the class as a Helper method of getInitalPositions
     * This method is used to check that the assumed middle column of the last row (the one in the position dimension/2) is black or white.
     *
     * @param dimension Integer that describes the dimension of a board n by n (Chess based).
     * @return boolean that is true if the square at the last row and assumed middle column is black and that returns false if it is white.
     */
    private static boolean isBlack(int dimension) {//This method checks if the point the position middle column, last row is a black position or a white position
        boolean black;
        if (dimension % 2 == 0) {//if the dimensions of the board are even then the last row starts with a black square
            black = true;
        }
        else {//if the dimensions of the board are even then the last row starts with a white square
            black = false;
        }
        for (int b = 1; b <= dimension / 2; b++) {
            if (black) {
                black = false;
            }else {
                black = true;
            }
        }
        return black;
    }

    /**
     * Public Method that is used to get all the coordinates of a Chess based of dimension n by n, it will get all the coordinates as Strings, with format Column(letter)Row(Number).
     * This method is used also as a Helper function in methods like isInBoard.
     *
     * @param dimensionOfBoard Integer that describes the dimension of a board n by n (Chess based).
     * @return an array of arrays of strings that contain all the possible coordinates of the board (each array contains an arrays that contains all the coordinates of a row, and that is repeated with all the rows).
     * @throws IllegalArgumentException if the dimension is not in the range of allowed values (between 4 and 26).
     */
    //---------------------------------------------getCoordinates------------------------------//
    public static String[][] getCoordinatesOfBoard(int dimensionOfBoard) {
        if (dimensionOfBoard<MIN_DIM || dimensionOfBoard> MAX_DIM) {
            throw new IllegalArgumentException(ERROR_DIMENSION);
        }
        int[] numbersOfBoard = getNumbersOfBoard(dimensionOfBoard);
        String[] lettersOfBoard = getLettersOfBoard(dimensionOfBoard);
        String[][] coordinates = new String[dimensionOfBoard][dimensionOfBoard];
        for (int i = 0; i < dimensionOfBoard; i++) {
            for (int j = 0; j < dimensionOfBoard; j++) {
                coordinates[i][j] = lettersOfBoard[j] + numbersOfBoard[i];
            }
        }
        return coordinates;
    }

    /**
     * Private Method that is used in the class as a Helper Method of getCoordinates.
     * It is used to get all the numbers of the a Board of dimensions n by n the numbers go through 1 to n, so it is important to take into account that the numbers start with one and not in zero.
     *
     * @param dimensionOfBoard integer that describes the dimensions of a board n by n (Chess based).
     * @return an array of Integers that contain the numbers of the board.
     */
    private static int[] getNumbersOfBoard(int dimensionOfBoard) {
        int[] numbersOfBoard = new int[dimensionOfBoard];
        for (int i = 0; i < dimensionOfBoard; i++) {
            numbersOfBoard[i] = i + 1;
        }
        return numbersOfBoard;
    }
//---------------------------------------------isValidMove---------------------------------//
    /**
     * Public Method that is used to check if a given movement (origin, and destination) is valid, taking into account the dimension of the board, the rules of the game and the current position of all the pieces.
     * For a Move to be valid it must pass certain conditions, such as being in the board. Also the origin position must correspond with the figure that it is being moved, the destination must be free and the movement should be diagonal.
     * This method is used in other classes. And requires several helper methods to allow its functioning.
     *
     * @param dimension Integer thar describes the dimension of a board n by n (Chess based).
     * @param players Array of String that contains all the information regarding the current position of all the pieces (Hounds and Fox).
     * @param figure Character that can be'F' if it is the turn of the fox or 'H' if it is the turn of the Hounds.
     * @param origin String (in Coordinate form) that contains the information of the original position of the piece that is going to be moved.
     * @param destination String (in Coordinate form) that contains the information of the final position of the piece that is going to be moved.
     * @return boolean that is true if the move has passed all the tests and false if it has failed one or more tests.
     * @throws NullPointerException if the Array of players is Null.
     * @throws NullPointerException if the given String of Origin is Null.
     * @throws NullPointerException if the given String of Destination is Null.
     * @throws IllegalArgumentException if the array of players does not have the correct length.
     * @throws IllegalArgumentException if the figure does not match with Hounds or Fox (the character figure is not equal to either 'F' or 'H').
     * @throws IllegalArgumentException if the destination or the origin are not in board.
     */

    public static boolean isValidMove(int dimension, String[] players, char figure, String origin, String destination){
        boolean isFigureInOrigin;
        boolean isDiagonalMove;
        boolean result = true;
        for (int a = 0; a <players.length;a++) {
            if (!isInBoard(players[a], dimension)) {
                result = false;
                break;
            }
        }
        if (!result){
            throw new IllegalArgumentException("error");
        }
        if (dimension<MIN_DIM || dimension>MAX_DIM){
            throw new IllegalArgumentException(ERROR_DIMENSION);
        }

        if (origin == null){
            throw new NullPointerException(ERROR_NULL);
        }
        if (destination == null){
            throw new NullPointerException(ERROR_NULL);
        }
        if (players == null){
            throw new NullPointerException(ERROR_NULL);
        }
        if (players.length != dimension/2 +1){
            throw new IllegalArgumentException(ERROR_PLAYERS);
        }

        if (!isInBoard(destination,dimension)||!isInBoard(origin,dimension)){
            throw new IllegalArgumentException(ERROR_COORDINATES);
        }

        if (figure == 'F'){
            isFigureInOrigin = isFoxInOrigin(players,origin);
            isDiagonalMove = isFoxDiagonalMove( origin,destination);
        }else if (figure == 'H'){
            isFigureInOrigin = isHoundInOrigin(players,origin);
            isDiagonalMove =isHoundDiagonalMove(origin,destination);
        }else{
            throw new IllegalArgumentException(ERROR_CHAR);
        }
        return isDestinationFree(players,destination) && isDiagonalMove &&isFigureInOrigin;
    }

    /**
     * Public Method that is used to check if a given position is in a board of n by n.
     * This method is used also as a helper method in isValidMove.
     * It is important to note that this method should only be used with the actual coordinates of the board, that can be obtained using the method of FoxHoundUtils.getCoordinates given a dimension.
     * This method should not be used alone, it should be used as a complement of other methods, as that will assure its functioning.
     *
     * @param coordinate String that contains all the information of a coordinate in a Chess based board (letter + number). This coordinate should be given in a good Coordinate form, if not it Will always return false.
     * @param dimension Integer thar describes the dimension of a board n by n (Chess based).
     * @return boolean that is true if the given coordinate is in the board described by the given dimension or false if it is not in the board.
     * @throws NullPointerException if the given coordinate is null.
     * @throws IllegalArgumentException if the number of rows and columns of the coordinate is not equal to the dimensions.
     * @throws IllegalArgumentException if the given dimension is not valid.
     */
    public static boolean isInBoard (String coordinate, int dimension){
       String[][] coordinates =getCoordinatesOfBoard(dimension);
        if (coordinate == null){
            throw new NullPointerException(ERROR_NULL);
        }
        if (coordinates[0].length!= coordinates.length|| coordinates.length != dimension){
            throw new IllegalArgumentException(ERROR_COORDINATES);
        }
        if (dimension<MIN_DIM || dimension> MAX_DIM) {
            throw new IllegalArgumentException(ERROR_DIMENSION);
        }
        boolean result = false;
        found:
        for (int i = 0 ; i<dimension;i++){

            for(int j = 0 ;j<dimension;j++){

                if (coordinates[i][j].equals(coordinate)){
                    result =  true;
                    break found;
                }
            }
        }
        return result;
    }

    /**
     * Private Method that is used in the class as a Helper method of IsValidMove.
     * This method is only called upon if the figure matches with 'H', it checks that for a given coordinate (origin) there is one match in the array of players (not taking into account the last position of the array as it contains information regarding the Fox and not the Hounds).
     *
     * @param players Array of Strings that contains all the information regarding the position of the pieces.
     * @param origin String that represents the position that it is going to be checked (the original position of the Hound in the move).
     * @return boolean that will be true if there is one match between the origin and one of the elements of players and false if there in no match (not taking into account the last element of the array).
     */
    private static boolean isHoundInOrigin(String[] players, String origin){
        boolean result = false;
        for (int i = 0 ; i<players.length -1 ;i++){
            if (origin.equals(players[i])){
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Private Method that is used in the class as a Helper method of IsValidMove.
     * This method is only called upon if the figure matches with 'F', it checks that for a given coordinate (origin) if it matches the last element of the array of player (that contains the information of the current position of the Fox).
     * This method only cares about the information of the last element in the array, as it is the one that contains the information regarding the current position of the array. All the other elements only have information about the positions of the Hounds,
     *
     * @param players Array of Strings that contains all the information regarding the position of the pieces.
     * @param origin String that represents the position that it is going to be checked (the original position of the Fox in the move).
     * @return boolean that will be true if there is a match between the origin and the last element of players and false if there in no match.
     */
    private static boolean isFoxInOrigin(String[] players,String origin){
        return origin.equals(players[players.length-1]);
    }

    /**
     * Private Method that is used in the class as a Helper method of isValidMove.
     * This method is used to check if a given destination (coordinate) is free, which means that there in other figure in it already, either Hound or Fox.
     * This function tries to check if there is at least one figure in the given coordinate, we don't take into account the origin position because it is assumed that the destination and origin are not equal, this condition is checked in other method.
     *
     * @param players Array of Strings that contains all the information regarding the position of the pieces.
     * @param destination String that represents te coordinate that is going to be checked.
     * @return boolean that will be true if the destination is not occupied by any other piece and false if it is occupied.
     */
    private static boolean isDestinationFree (String[] players, String destination){
        boolean result = true;
        for (String player : players) {
          if (player.equals(destination)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Private Method that is used in the class as a Helper method of isValidMove.
     * This method is only called upon if the figure matches with 'F'.
     * This method checks if the actual movement of the Fox (given a a pair of coordinates origin and destination) is valid according to the rules of the game.
     * The Fox should be able to move in diagonal backwards but also forward.
     * This method also checks if the origin and destination are the same, it is one of its side effects, so there is no need to implement a method to check that.
     *
     * @param origin String that describes the initial position of the piece that is being moved.
     * @param destination String that describes the final position of the piece that is going to be moved.
     * @return boolean that returns true if the move is actually a diagonal (backwards or forward) and false if not.
     */
    private static boolean isFoxDiagonalMove(String origin,String destination) {
        int numberOrigin = getNumberOfString(origin);
        char letterOrigin = origin.charAt(0);
        int numberDestination = getNumberOfString(destination);
        char letterDestination = destination.charAt(0);
        boolean move = false;
        int sign1 = 1;
        int sign2 = 1;
        for (int i = 0; i<4;i++){
            if  (((numberOrigin + sign1) == numberDestination) && letterOrigin == (char) (letterDestination -sign2)) {
                move = true;
                break;
            }
            if (i == 1){
                sign1 = -1;
            }
            sign2 = -sign2;
        }
        return move;
    }
        /**
         * Private Method that is used in the class as a Helper method of isValidMove.
         * This method is only called upon if the figure matches with 'H'.
         * This method checks if the actual movement of the Fox (given a a pair of coordinates origin and destination) is valid according to the rules of the game.
         * The Hound should be able to move only forward in a diagonal.
         *
         * @param origin String that describes the initial position of the piece that is being moved.
         * @param destination String that describes the final position of the piece that is going to be moved.
         * @return boolean that returns true if the move is actually a diagonal (forward) and false if not.
         */
    private static boolean isHoundDiagonalMove(String origin,String destination){
        int numberOrigin = getNumberOfString(origin);
        char letterOrigin = origin.charAt(0);
        int numberDestination =getNumberOfString(destination);
        char letterDestination = destination.charAt(0);
        boolean move = false;
        int sign = 1;

        for (int i = 0; i<2;i++) {
            if (((numberOrigin + 1) == numberDestination) && letterOrigin == (char) (letterDestination + sign)) {
                move = true;
            }
            sign = -sign;
        }
        return move;
    }
    /**
     * Private method that is used as a Helper method of isHoundDiagonalMove and other functions.
     * This method is used to get the number of a Coordinate (That has letter + number) and is given as a String.
     * The number of a Coordinate corresponds with the row of the Board.
     *
     * @param coordinate String that contains all the information of a position in a Chess-based board.
     * @return integer that is equal to the row of the Coordinate
     */
    private static int getNumberOfString(String coordinate){
        String letter = ""+ coordinate.charAt(0);
        String[] parts = coordinate.split(letter);
        return Integer.parseInt(parts[1]);
    }
//----------------------------------------------isHoundWin and isFoxWin--------------------------------------------------
    /**
     * Public Method that is used to check if the Hounds have won the game.
     * This happens, according to the rules of the game, if the Fox has no possible moves.
     * By definition the fox has 4 possible moves, if all of them are invalid moves (if it is in a corner or all the positions are occupied by Hounds).
     *
     * @param players Array of Strings that contains all the information regarding the position of the pieces.
     * @param dimension  Integer thar describes the dimension of a board n by n (Chess based).
     * @throws NullPointerException if the Array of positions is null.
     * @throws IllegalArgumentException if the dimension is not in the range of the accepted dimensions (between 4 and 26).
     * @throws IllegalArgumentException if the length of the Array of players is not correct.
     * @return boolean that returns true if the Hounds have won the game and false if it is not the case.
     */
    public static boolean isHoundWin(String[] players,int dimension) {
        int sign1 = 1; //Variables used to produce all the possible moves of the Fox.
        int sign2 = 1;
        boolean result = true;
        if (players == null) {
            throw new NullPointerException(ERROR_NULL);
        }
        for (int a = 0; a <players.length;a++){
            if (!isInBoard(players[a],dimension)){
                 result = false;
                 break;
            }
        }
        if (players.length!= dimension/2 +1){
            throw new IllegalArgumentException(ERROR_PLAYERS);
        }
        if (!result){
            throw new IllegalArgumentException("Not valid");
        }

        if (dimension < MIN_DIM || dimension > MAX_DIM) {
            throw new IllegalArgumentException(ERROR_DIMENSION);
        }
        String positionOfFox = players[players.length - 1];
        char letter = positionOfFox.charAt(0);
        int numberOfString = getNumberOfString(positionOfFox);
        boolean win = true;
        String[] possibleMoves = new String[4];
        for (int i = 0; i < 4; i++) {
            possibleMoves[i] = ((char) (letter + sign1)) + "" + (numberOfString + sign2);
            if (i == 1) {
                sign1 = -1;
            }
            sign2 = -sign2;
        }
        for (int j = 0; j < 4; j++) {
            if ((isInBoard(possibleMoves[j], dimension))) { //First we have to check if the possible move is in the board. And then we have to check if that move is Valid
                if (isValidMove(dimension, players, 'F', positionOfFox, possibleMoves[j])) {
                    win = false;
                    break;
                }
            }
        }
        return win;
    }

    /**
     * Public Method that is used to check if the Fox has won the game.
     * This happens, according to the rules of the game, if the Fox has reached the row 1, as stated in the rules of the game.
     * Therefore the function has to check if the number in the coordinate is equal to 1. for that it Will use the method getNumberOfString.
     *
     * @param foxPosition String that describes the current position of the Fox in a Chess-based board of dimensions n by n.
     * @return boolean that returns true if the Fox has won the game and false if it is not the case.
     * @throws NullPointerException if the given String is Null, in this case if the coordinate is Null.
     */
    public static boolean isFoxWin(String foxPosition){
        if (foxPosition == null){
            throw new NullPointerException(ERROR_NULL);
        }
        if(!format(foxPosition)){
            throw new IllegalArgumentException("error");
        }
        int row = getNumberOfString(foxPosition);
        return (row ==1);
    }
    public static boolean isinBoardplayers(String[] players, int dimension){
        boolean result = true;
        for (int a = 0; a <players.length;a++) {
            if (!isInBoard(players[a], dimension)) {
                result = false;
                break;
            }
        }
        return result;
    }
    private static boolean format(String coordinate){
       int a=  getNumberOfString(coordinate);
       if (a>26){
           return false;
       }
       return true;
    }
}