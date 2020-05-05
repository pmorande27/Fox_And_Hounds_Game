
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
public class FoxHoundIO {
    /**
     * Error message that appears if the path is null
     */
    public static final String ERROR_PATH = "Null Path";
    /**
     * Public Method that is used to save the game in a previously-created text file (.txt)
     * It uses 3 helper methods that allow its functioning.
     * @param turn Character that contains the information regarding the current turn of the game it should only take two values 'F' or 'H'.
     *
     * @param players Array of Strings that contain all the information regarding the current position of all the pieces in the board.
     * @param path variable of type Path (will use method path.get) that describes where the file is located in the system.
     * @return boolean that is true if no exception has been caught and false if it is the case that the players array is null, the path does not exist or the turn character does not match with either 'F' or 'H'.
     * @throws NullPointerException if the array of players is null.
     * @throws IllegalArgumentException if the given character does not match with neither 'F' or 'H'.
     * @throws NullPointerException if the given path is null.
     * @throws IllegalArgumentException if the length of the array of players does not match with the correct one.
     */
    public static boolean saveGame( String[] players,char turn, Path path) {
        boolean success = true;
        if (turn != FoxHoundUtils.FOX_FIELD&& turn != FoxHoundUtils.HOUND_FIELD){
           throw new IllegalArgumentException(FoxHoundUtils.ERROR_CHAR);
        }
        if (players == null){
            throw new NullPointerException(FoxHoundUtils.ERROR_NULL);
        }
        if(players.length != FoxHoundUtils.DEFAULT_LENGTH){
            throw new IllegalArgumentException(FoxHoundUtils.ERROR_PLAYERS);
        }
        if (!FoxHoundUtils.isinBoardplayers(players,8)) {
            throw new IllegalArgumentException("Not valid");
        }
        if (path == null){
            throw new NullPointerException(FoxHoundUtils.ERROR_NULL);
        }
        if (path.toFile().exists()){
            return false;
        }
        String content = format(turn,players);
        try {
            PrintWriter writer = new PrintWriter(path.toFile());
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            success = false;
        }
        return success;
    }

    /**
     * Private Method that is used in the class as a Helper method of saveGame.
     * It is used to create a single String, given the turn and the array of players.
     * It should separate each component with a space and the first element of the new String is the turn, followed by the content in the array of players.
     *
     * @param turn Character that contains the information of the next turn of the game.
     * @param players Array that contains all the information regarding the current position of all the pieces.
     * @return string that is equal to the addition of turn and all the elements of players.
     */
    private static String format(char turn, String[] players){
        StringBuilder result;
        result = new StringBuilder(turn + " ");
        for (int i = 0 ;i<players.length;i++){
            if (i == players.length-1){//This if-else block helps to control if we need to add a space or not. In the case of the last element we don't want to add that space.
                result.append(players[i]);
            }else{
                result.append(players[i]).append(" ");
            }

        }
        return result.toString();
    }

    /**
     * Public Method that is used to Load a Game.
     * It will only be able to load the game if the dimension of the current gme that is being played and the game that is being load are the same.
     * This method uses some imported elements to be able to read a file.
     * the block try-catch is used to check if everything went fine when searching for the file.
     * It uses 3 helper methods that allow its functioning.
     *
     * @param players Array of Strings that contains the information of all the current positions of the players and that is going to be over-written.
     * @param path Variable of type Path that contains the information of where the file that is going to be loaded is.
     * @return character, it corresponds to the next turn in the game, in the case that something has gone wrong, It will return the char '#'.
     * @throws NullPointerException if the given path is null.
     * @throws NullPointerException if the given array of players is null
     * @throws IllegalArgumentException if the Array of players do not match with the correct length.
     */
    public static char loadGame(String[] players, Path path){
        if (path == null){
            throw new NullPointerException(ERROR_PATH);
        }
        boolean success = true;
        char result = '#';
        if (players == null){
            throw new NullPointerException(FoxHoundUtils.ERROR_PLAYERS);
        }
        if (players.length != FoxHoundUtils.DEFAULT_LENGTH){
            throw new IllegalArgumentException(FoxHoundUtils.ERROR_PLAYERS);
        }
        if (!FoxHoundUtils.isinBoardplayers(players,8)) {
            throw new IllegalArgumentException("Not valid");
        }

        if(!Files.exists(path)){
            result = '#';
        }
        if (success ){
            File file = new File(path.toString());
            try {
                BufferedReader entry = new BufferedReader(new FileReader(file));
                String reading = entry.readLine();
                if (entry.readLine() != null){
                    success = false;
                }
                String[] playersAndTurn = getArray(reading);
                String[] newPlayers = new String[playersAndTurn.length-1];
                getNewPlayers(newPlayers,playersAndTurn);
                if(newPlayers.length != players.length){
                    success = false;
                }
                if (!playersAndTurn[0].equals("F")&&!playersAndTurn[0].equals("H")){
                    success = false;
                }if (!checkCoordinates(playersAndTurn)){
                    success = false;
                }
                if (success){
                    result = playersAndTurn[0].charAt(0);
                    System.arraycopy(newPlayers, 0, players, 0, players.length);
                }
            } catch (IOException e) {
               return result;
            }
        }
        return result;
    }

    /**
     * Private Method that is used in the class as a Helper method of loadGame.
     * It is used to get from a String an Array of Strings, using the space as Separator.
     *
     * @param reading String received from the file.
     * @return Array of Strings that contains the information of the Next Turn and the new position of the players.
     */
    private static String[] getArray(String reading){
        return reading.split(" ");
    }

    /**
     * Private Method that is used in the class as a Helper method of loadGame.
     * It is used to check if all the coordinates that have been received from the file are actually board-like coordinates of a Board of a given dimension.
     *
     * @param playersAndTurn Array that contains the Next-turn character and the information of the position of all the pieces (the first position is the one that corresponds with the turn character.
     * @return boolean that will be true if all the coordinates in the file are actually board coordinates and false otherwise.
     */
    private static boolean checkCoordinates(String[] playersAndTurn){
        boolean result = true;
        for(int i = 1; i<playersAndTurn.length;i++){
            if (!FoxHoundUtils.isInBoard(playersAndTurn[i], 8)){
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Private Method that is used in the class as a Helper method of loadGame
     * It is used to create an Array of the positions of the players given an array that contains that positions but also the next-turn character (as a string).
     *
     * @param newPlayers New Array that is going to copy the positions of the old array
     * @param playersAndChar Old array that contains the information.
     */
    private static void getNewPlayers(String[] newPlayers,String[]playersAndChar){
        if (playersAndChar.length - 1 >= 0)
            System.arraycopy(playersAndChar, 1, newPlayers, 0, playersAndChar.length - 1);
    }
}
