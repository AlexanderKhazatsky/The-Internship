package byog.Core;

import byog.Core.WorldGenerator.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    TERenderer ter = new TERenderer();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        //TODO: Create a list of all the inputed commands, so if they say save world, we know what they did.
        //TODO: while player didn't press q, do the typical commands.
        //TODO: if player pressed q, continue, and then system.exit(0);
        //TODO: call playKeyboard(char typed) instead of playLetter.
        //TODO: Create a variable count and if count = 0, and the char is l then load, otherwise don't load.

        World w = new World();
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        /*KeyCodes: 32 = space (talking)
        * 65 = A
        * 87 = W
        * 83 = S
        * 68 = D
        * 48 = 0
        * 49 = 1
        * 50 = 2
        * 51 = 3
        * ...
        * 57 = 9
        * 76 = l
        *  :q
        * */
        int count = 0;
        boolean loaded = false;
        while (true) {
            if (StdDraw.isKeyPressed(76)){
                if(!loaded) {
                    finalWorldFrame = load(w, false, ter);
                    loaded = true;
                }
                System.out.println("l pressed");
            }
            if (StdDraw.isKeyPressed(32)){

                System.out.println("space pressed");
            }
            if (StdDraw.isKeyPressed(87)){
                System.out.println("w pressed");

                w.playKeyboard('w');
            }
            if (StdDraw.isKeyPressed(65)){
                System.out.println("a pressed");

                w.playKeyboard('a');
            }
            if (StdDraw.isKeyPressed(83)){
                System.out.println("s pressed");

                w.playKeyboard('s');
            }
            if (StdDraw.isKeyPressed(68)){
                System.out.println("d pressed");

                w.playKeyboard('d');
            }
            if (StdDraw.isKeyPressed(81)){
                System.out.println("q pressed");
                w.playKeyboard('q');
                break;
            }
            StdDraw.pause(100);
//            if (StdDraw.isMousePressed()) {
//                try {
//                    Integer y = ((int) StdDraw.mouseY());
//                    Integer x = ((int) StdDraw.mouseX());
//
//                    TETile f = finalWorldFrame[x][y];
//                    StdDraw.text(0, 0, f.description());
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    //do nothing
//                }
//            }
        }

        System.out.println("Finished");

    }
    private static void delay(){
        StdDraw.pause(1000);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        //TODO: if the player types in "l", then we load game and continue.
        //TODO: else we assume its a new game, if there is no valid string, then return null.
        String numStr, rest;
        TETile[][] finalWorldFrame;
        World w = new World();
        if (input.toLowerCase().indexOf('n') != -1) {
            int s = input.toLowerCase().indexOf('s');
            rest = input.substring(s+1);
            Long seed = getSeed(input);
            finalWorldFrame = w.launchString(seed, WIDTH, HEIGHT, true);
        } else if(input.toLowerCase().indexOf('l') != -1) {
            finalWorldFrame = load(w, false, ter);
            rest = input.substring(input.toLowerCase().indexOf('l') + 1);
        } else {
            numStr = input;
            rest = "";
            long seed = Long.parseLong(numStr);
            finalWorldFrame = w.launchString(seed, WIDTH, HEIGHT, true);
        }
        if (rest.indexOf(":q") != -1) {
            for (char c : rest.toCharArray()) {
                w.playLetter(c);
            }
        }else {
            for (int i = 0; i < rest.toCharArray().length - 2; i++){
                w.playKeyboard(rest.toCharArray()[i]);
            }
        }
        printWorld(finalWorldFrame);
        return finalWorldFrame;
    }

    private static void printWorld(TETile[][] world){
        for(TETile[] row : world){
            for (TETile c : row){
                System.out.print(c.description() + "\t");
            }
            System.out.println();
        }
    }
    /*Returns the seed.*/
    private static Long getSeed(String seedString) {
        if (seedString.toLowerCase().indexOf('n') != -1) {
            int s = seedString.toLowerCase().indexOf('s');
            if (s == -1) {
                System.out.println("There's is an error.");
                System.exit(0);
            }
            String numStr = seedString.substring(1, s);
            long seed = Long.parseLong(numStr);
            return seed;
        }
        return null;
    }

    /*Returns the given world. if initializePlayer is true, it means we are playing
    * with a string, else we are actually rendering the board. */
    //TODO: what happens if there is no file loaded.... do we return null?
    protected static TETile[][] load(World w, boolean initializePlayer, TERenderer ter){
        String line = "";
        String temp;
        try {
            FileReader fileReader = new FileReader("game.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((temp = bufferedReader.readLine()) != null) {
                line += temp;
            }
        }catch(FileNotFoundException e){
            //TODO: Display, file not found... try again.
        }catch(IOException e){
            //TODO: Display, file maybe corrupted.
        }
        int s = line.toLowerCase().indexOf('s');
        String sequence = line.toLowerCase().substring(s);
        Long seed = getSeed(line);
        TETile[][] returnTile;
        if (initializePlayer) {
            returnTile = w.launchString(seed, WIDTH, HEIGHT, true);
        }else {
            returnTile = w.launchBoard(seed, WIDTH, HEIGHT, ter);
        }
        w.load(sequence);
        return returnTile;
    }
}

