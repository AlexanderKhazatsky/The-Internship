package byog.Core.WorldGenerator;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class World {
    protected static int WIDTH = 50;
    protected static int HEIGHT = 50;
    protected static int AREA = WIDTH * HEIGHT;
    protected static Random RANDOM;
    protected static TETile[][] world;
    private static Player p;
    public static TETile[][] launchBoard(Long seed, int w, int h, TERenderer r){
        launchString(seed, w, h, false);
        r.initialize(WIDTH, HEIGHT);
        r.renderFrame(world);
        p = new Player(r, seed.toString());
        return world;
    }
    public static TETile[][] launchString(Long seed, int w, int h, boolean initializePlayer) {
        if (seed != null) {
            RANDOM = new Random(seed);
        } else {
            RANDOM = new Random();
        }
        WIDTH = w;
        HEIGHT = h;
        AREA = WIDTH * HEIGHT;
        world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            } // End inner for loop
        } // End outer for loop

        Builder.buildWorld();
        if (initializePlayer) {
            p = new Player(seed.toString());
        }
        return world;
    } // End main method

    /*initializes the world at the end. */
    public static void load(String sequence){
        for (char c : sequence.toLowerCase().toCharArray()){
            p.playWithChar(c);
        }
    }
    /*This will not render anything... */
    public static void playLetter(char c){
        //TODO: You are given the character, now we need to do the corresponding action.
//        System.out.println(String.format("Character %c has been pressed", c));
        p.playWithChar(c);
    }
    public void playKeyboard(char typed){
        p.play(typed);
        delay();
//        StdDraw.pause(100);
    }
    private static void delay(){
        try{
            TimeUnit.MILLISECONDS.sleep(100);
        }catch(InterruptedException e){
            //do nothing
        }
    }

} // End World class

