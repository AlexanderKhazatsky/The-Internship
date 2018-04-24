package byog.Core.WorldGenerator;

import byog.TileEngine.Tileset;

import java.awt.Point;


public class Room extends Space {
    public Room() {

        int newXPos;
        int newYPos;
        xLength = RANDOM.nextInt(WIDTH / 10) + AREA / 500;
        yLength = RANDOM.nextInt(HEIGHT / 10) + AREA / 500;
        do {
            newXPos = RANDOM.nextInt(WIDTH - xLength);
            newYPos = RANDOM.nextInt(HEIGHT - yLength);
            pos = new Point(newXPos, newYPos);

        } while (!canFit(this));
    } // End Room constructor

    public boolean canFit(Space object) {
        for (int y = -3; y < object.yLength + 3; y++) {
            int yPos = object.pos.y + y;
            if (y > 0 && y < object.yLength - 1) {
                try {
                    boolean cond1 = world[0][yPos] != Tileset.NOTHING;
                    boolean cond2 = world[object.xLength][yPos] != Tileset.NOTHING;
                    if (cond1 && cond2) {
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    return false;
                }
            } else {
                for (int x = -3; x < object.xLength + 3; x++) {
                    int xPos = object.pos.x + x;
                    try {
                        if (world[xPos][yPos] != Tileset.NOTHING) {
                            return false;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    } // End canFit
} // End Room class
