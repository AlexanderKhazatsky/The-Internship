package byog.Core;
import byog.Core.WorldGenerator.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.*;

public class KeyBoardListener extends Frame implements KeyListener {
    World w;
    TERenderer ter;
    TETile [][] frame;
    boolean done = false;
    public KeyBoardListener(World w1, TERenderer ter) {
        this.w = w1;
        this.ter = ter;
        JFrame frame = new JFrame("Square Move Practice");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(800, 120);
        frame.addKeyListener(this);
        ImageIcon ii = new ImageIcon("images/keyboard.jpg");
        JLabel label = new JLabel(ii);
        JScrollPane jsp = new JScrollPane(label);
        frame.getContentPane().add(jsp);
    }

    public TETile[][] getFrame(){
        return frame;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        System.out.print("test");
        String seed = "";
        boolean collecting = true;
        Character nextKey;
        do {
            nextKey = key.getKeyChar();
            nextKey = Character.toLowerCase(nextKey);

            if (collecting) {
                if (nextKey == 'l') {
                    try {
                        frame = Game.load(w, false, ter);
                    } catch (Exception e) {
                        //Nothing
                    }

                    collecting = false;
                } else if (nextKey == 's') {
                    collecting = false;
                    long longSeed = Long.parseLong(seed);
                    w.launchBoard(longSeed, Game.WIDTH, Game.HEIGHT, this.ter);
                } else if (nextKey != 'n') {
                    seed += nextKey;
                }

            } else {
                w.playKeyboard(nextKey);
            }
        } while (nextKey != 'q');
        w.playKeyboard(nextKey);
        done = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.paramString());
    }
}
