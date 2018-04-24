package byog.Core.WorldGenerator;

import byog.TileEngine.Tileset;

import java.awt.Point;
import java.util.ArrayList;

public class Builder extends World { // UPDATE STRCTURE LIST WITH HALLS
    static ArrayList<Room> isolatedRooms;
    static ArrayList<Space> connectedStructures;

    public static void buildWorld() {
        makeRooms();
        makeHalls();
    } // End buildWorld method

    // MAKE A FUNCTION THAT TAKES IN BOUNDARIES EX NOT BETWEEN THESE NUMBERS

    public static void makeRooms() {
        int roomCount = RANDOM.nextInt(AREA / 200) + AREA / 500;
        connectedStructures = new ArrayList<Space>(roomCount);
        isolatedRooms = new ArrayList<Room>(roomCount);
        for (int i = 0; i < roomCount; i += 1) {
            isolatedRooms.add(new Room());
            buildRoom(isolatedRooms.get(i));
        } // End for loo
    } // End makeRooms method

    public static void makeHalls() {
        int firstIndex = RANDOM.nextInt(isolatedRooms.size());
        connectedStructures.add(isolatedRooms.get(firstIndex));
        isolatedRooms.remove(firstIndex);

        while (!isolatedRooms.isEmpty()) {
            int aIndex = RANDOM.nextInt(isolatedRooms.size());
            Room newConnected = isolatedRooms.get(aIndex);
            isolatedRooms.remove(aIndex);

            int bIndex = RANDOM.nextInt(connectedStructures.size());
            connect(newConnected, connectedStructures.get(bIndex));
            connectedStructures.add(newConnected);
        }

    }

    public static void connect(Space startSpace, Space endSpace) {
        pickSide(startSpace);
        pickSide(endSpace);


        Hall hallway = new Hall(startSpace, endSpace);
        for (int attempt = 0; attempt < 10; attempt++) {
            if (!checkWall(startSpace) || !checkWall(endSpace)) {
//                System.out.println(checkWall(endSpace));
                pickSide(endSpace);
                pickSide(startSpace);
            } else {
                break;
            }
        }
        hallway.joinSpaces();
        connectedStructures.add(hallway);
    } // End connectStructures method

    /*If start is a wall, return true else false; */
    public static boolean checkWall(Space first) {
        try {
            return world[first.walkway.x][first.walkway.y] != Tileset.NOTHING;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static void pickSide(Space structure) {
        int walkwayX = 0;
        int walkwayY = 0;
        int side;
        if (structure instanceof Room) {
            side = RANDOM.nextInt(4);
        } else {
            side = RANDOM.nextInt(2);
        }
//        System.out.println("structure: " + structure.pos);
        switch (side) {

            case 0: // Left
                walkwayX = structure.pos.x + 2;
                walkwayY = structure.pos.y + 2;
                break;
            case 1: // Right
                walkwayX = structure.pos.x + structure.xLength - 2;
                walkwayY = structure.pos.y + 3;
                break;
            case 2: // Top
                walkwayX = structure.pos.x + 2;
                walkwayY = structure.pos.y + structure.yLength - 2;
                break;
            case 3: // Bottom
                walkwayX = structure.pos.x + 2;
                walkwayY = structure.pos.y + 2;
                break;
            default:
                walkwayX = 0;
                walkwayY = 0;
                break;
        } // End switch statement picking hallway starting point
        structure.walkway = new Point(walkwayX, walkwayY);
    } // End pickRoomSide method

    public static void buildRoom(Space object) {
        for (int x = 0; x < object.xLength; x += 1) {
            for (int y = 0; y < object.yLength; y += 1) {
                int xPos = object.pos.x + x;
                int yPos = object.pos.y + y;
                if (x == 0 || x == object.xLength - 1 || y == 0 || y == object.yLength - 1) {
                    world[xPos][yPos] = Tileset.WALL;
                } else {
                    world[xPos][yPos] = Tileset.FLOOR;
                } // If edge, add wall. Else, add floor.
            } // End inner for loop
        } // End outer for loop
    } // End build method
} // End Builder class
