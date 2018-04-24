package byog.Core.WorldGenerator;

import byog.TileEngine.Tileset;
import java.awt.Point;

public class Hall extends Space {
    Point direction;
    Space startSpace;
    Space endSpace;

    public Hall(Space start, Space end) {
        startSpace = start;
        endSpace = end;
        pos = new Point(startSpace.walkway.x, startSpace.walkway.y);

    } // End Room constructor

    public boolean finishedX() {
//        System.out.println("end: " + endSpace.walkway);
//        System.out.println("current: " + this.pos);
        return endSpace.walkway.x + direction.x == this.pos.x;
    }

    public boolean finishedY() {
//        System.out.println("endY: " + endSpace.walkway);
//        System.out.println("currentY: " + this.pos);
        return endSpace.walkway.y + direction.y == this.pos.y;
    }

    public void joinSpaces() {
//        System.out.println(endSpace.walkway);
        direction = new Point();
        xLength = Math.abs(endSpace.walkway.x - this.pos.x);
        yLength = Math.abs(endSpace.walkway.y - this.pos.y);

        if (endSpace.walkway.x - this.pos.x > 0) {
            direction.x = 1;
        } else if (endSpace.walkway.x - this.pos.x == 0) {
            direction.x = 0;
        } else {
            direction.x = -1;
        }
        if (endSpace.walkway.y - this.pos.y > 0) {
            direction.y = 1;
        } else if (endSpace.walkway.y - this.pos.y == 0) {
            direction.y = 0;
        } else {
            direction.y = -1;
        }

        while (!finishedX() && !outOfBounds(this.pos)) {
            world[this.pos.x][this.pos.y] = Tileset.FLOOR;
            this.pos.x += direction.x;
        }

        while (!finishedY() && !outOfBounds(this.pos)) {
//            System.out.println(world);
//            System.out.println(this.pos);
            world[this.pos.x][this.pos.y] = Tileset.FLOOR;
            this.pos.y += direction.y;
        }

        finishedWall();
    }

    public void finishedWall() {
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                if (world[row][col] == Tileset.FLOOR) {
//                    checkEdge();
                    checkSurrounding(row, col);
                }
            }

        }
    }

    public void checkSurrounding(int row, int col) {
        try {
            if (world[row + 1][col] == Tileset.NOTHING) {
                world[row + 1][col] = Tileset.WALL;
            }
            if (world[row + 1][col + 1] == Tileset.NOTHING) {
                world[row + 1][col + 1] = Tileset.WALL;
            }
            if (world[row][col + 1] == Tileset.NOTHING) {
                world[row][col + 1] = Tileset.WALL;
            }
            if (world[row - 1][col] == Tileset.NOTHING) {
                world[row - 1][col] = Tileset.WALL;
            }
            if (world[row - 1][col - 1] == Tileset.NOTHING) {
                world[row - 1][col - 1] = Tileset.WALL;
            }
            if (world[row][col - 1] == Tileset.NOTHING) {
                world[row][col - 1] = Tileset.WALL;
            }
            if (world[row - 1][col + 1] == Tileset.NOTHING) {
                world[row - 1][col + 1] = Tileset.WALL;
            }
            if (world[row + 1][col - 1] == Tileset.NOTHING) {
                world[row + 1][col - 1] = Tileset.WALL;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.print(e);
        }
    }

    //    public void checkEdge
    /*Returns true if it is a connection, else false */
    public boolean isConnection(Point location) {
        return world[location.x][location.y] == Tileset.WALL && doesConnect(location);
    } // End lengthToConnect method

    public boolean outOfBounds(Point location) {
        boolean cond1 = (location.x + direction.x) < 0;
        boolean cond2 = (location.y + direction.y) < 0;
        boolean cond3 = (location.x + direction.x) >= WIDTH;
        boolean cond4 = (location.y + direction.y) >= HEIGHT;
        return cond1 || cond2 || cond3 || cond4;
    } // End outOfBounds method

    public boolean doesConnect(Point location) {
        try {
            return world[location.x + direction.x][location.y + direction.y] == Tileset.FLOOR;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;

        }
    } // End doesConnect method

    public void safeWall() {

        Point sideA = new Point(this.pos.x + direction.y, this.pos.y + direction.x);
        Point sideB = new Point(this.pos.x - direction.y, this.pos.y - direction.x);
        if (outOfBounds(sideA)) {
            world[this.pos.x][this.pos.y] = Tileset.WALL;
            return;
        }
        if (outOfBounds(sideB)) {
            world[this.pos.x][this.pos.y] = Tileset.WALL;
            return;
        }
        if (world[sideA.x][sideA.y] != Tileset.FLOOR) {
            world[sideA.x][sideA.y] = Tileset.WALL;
        }
        if (world[sideB.x][sideB.y] != Tileset.FLOOR) {
            world[sideB.x][sideB.y] = Tileset.WALL;
        }
    }
} // End Room class
