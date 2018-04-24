package byog.Core.WorldGenerator;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import static byog.Core.WorldGenerator.World.playLetter;
import static byog.Core.WorldGenerator.World.world;

public class Player {
    TERenderer renderer;
    Random rand;
    LinkedList<Character> commands;
    String seed;
    Point pos;
    public Player(TERenderer r, String seed){
//        this.seed = seed;
//        commands = new LinkedList<Character>();
        this.renderer = r;
        acrossConstructors(seed);
        renderer.renderFrame(world);
//        this.rand = World.RANDOM;
//        Point structurePos = Builder.connectedStructures.get(0).pos;
//        this.pos = new Point(structurePos.x + 1, structurePos.y + 1);
//        world[pos.x][pos.y] = Tileset.PLAYER;
//        System.out.println(pos);
    }
    public Player(String seed) {
        acrossConstructors(seed);
    }
    private void acrossConstructors(String seed){
        this.seed = seed;
        commands = new LinkedList<Character>();
        this.rand = World.RANDOM;
        Point structurePos = Builder.connectedStructures.get(0).pos;
        this.pos = new Point(structurePos.x + 1, structurePos.y + 1);
        world[pos.x][pos.y] = Tileset.PLAYER;
    }
    public void play(char c){
        playWithChar(c);
        //Listen to the keyboard, and call playWithChar.
        renderer.renderFrame(world);
    }
    public void playWithChar(char c){
        switch (c){
            case 'a':
                commands.add('a');
                commandA();
                break;
            case 's':
                commands.add('s');
                commandS();
                break;
            case 'd':
                commands.add('d');
                commandD();
                break;
            case 'w':
                commands.add('w');
                commandW();
                break;
            case 'q':
                commandQ();
                break;
            default:
                //do nothing.
        }
        renderer.renderFrame(world);
//      TODO: remove the above line.
    }

    /*Save the inputs to a file name game.txt*/
    private void commandQ(){
        String inputFeed = "n" + seed + "s";
        while (!commands.isEmpty()){
            inputFeed += commands.removeFirst();
        }
        saveGame(inputFeed);

    }

    /*Save the input to a file named game.txt
    * @source
    * https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
    * */
    public static void saveGame(String input) {
        //TODO: write out a .txt file with the given instructions?
        //Write the string they gave us in? or the keyboard pressed
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("game.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.print(input);
        writer.close();
    }

    /*Move the player up. If there is a wall, or locked door
    * can't move. If it is a locked door, then print, unlock door first.*/
    private void commandW(){
        int mobility = canMove(pos.x, pos.y + 1);
        if (mobility == 1) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            world[pos.x][pos.y + 1] = Tileset.PLAYER;
            pos.y++;
        } else if(mobility == 0) {
            System.out.print("oh oh... There's a locked door!!!");
            //TODO make this more formal, and display it.
        } else {
            System.out.println("oh oh... That is a wall.");
            //TODO make this more formal, and display it.
        }
    }

    /*Move the player right. If there is a wall, or locked door
     * can't move. If it is a locked door, then print, unlock door first.*/
    private void commandD(){
        //move the player to the right??
        int mobility = canMove(pos.x + 1, pos.y);
        if (mobility == 1) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            world[pos.x + 1][pos.y] = Tileset.PLAYER;
            pos.x++;
        } else if(mobility == 0) {
            System.out.print("oh oh... There's a locked door!!!");
            //TODO make this more formal, and display it.
        } else {
            System.out.println("oh oh... That is a wall.");
            //TODO make this more formal, and display it.
        }
    }

    /*Move the player down. If there is a wall, or locked door
     * can't move. If it is a locked door, then print, unlock door first.*/
    private void commandS(){
        //move the player to the down??
        int mobility = canMove(pos.x, pos.y - 1);
        if (mobility == 1) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            world[pos.x][pos.y - 1] = Tileset.PLAYER;
            pos.y--;
        } else if(mobility == 0) {
            System.out.print("oh oh... There's a locked door!!!");
            //TODO make this more formal, and display it.
        } else {
            System.out.println("oh oh... That is a wall.");
            //TODO make this more formal, and display it.
        }
    }

    /*Move the player left. If there is a wall, or locked door
     * can't move. If it is a locked door, then print, unlock door first.*/
    private void commandA(){
        //move the player to the left??
        int mobility = canMove(pos.x - 1, pos.y);
        if (mobility == 1) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            world[pos.x - 1][pos.y] = Tileset.PLAYER;
            pos.x--;
        } else if(mobility == 0) {
            System.out.print("oh oh... There's a locked door!!!");
            //TODO make this more formal, and display it.
        } else {
            System.out.println("oh oh... That is a wall.");
            //TODO make this more formal, and display it.
        }
    }

    /*Return 1 if the player can move to new point
    * else return 0 if the player can't move because of a locked door
    * else return -1 if the player can't move because of a wall. */
    private int canMove(int x, int y){
        if (world[x][y] == Tileset.WALL){
            return -1;
        } else if(world[x][y] == Tileset.LOCKED_DOOR) {
            return 0;
        }
        return 1;
    }
}
