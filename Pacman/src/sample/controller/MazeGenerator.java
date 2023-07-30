package sample.controller;

import java.util.*;

public class MazeGenerator {
    public static String[][] mazeMap;
    public static int mazeWidth;
    public static int mazeLength;

    public static String[][] generateMaze() {
        mazeLength = 12;
        mazeWidth = 12;
        int mazeNumber = 1;
        for (int i = 0; i < mazeNumber; i++) {
            mazeMap = new String[(2 * mazeLength) + 1][(2 * mazeWidth) + 1];
            initializeMazeMap();
            mazeMap[1][1] = "*";
            mazeGenerator(1, 1);
        }
        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[i].length; j++) {
                if (mazeMap[i][j].equals("*"))
                    mazeMap[i][j] = "0";
            }
        }
        makeMazeStandard();
        return mazeMap;
    }

    private static void makeMazeStandard() {
        Random random = new Random();
        int deleteCounter = 0;
        while (deleteCounter < 5 * mazeLength + 10) {
            int i = random.nextInt((2 * mazeLength) + 1);
            int j = random.nextInt((2 * mazeLength) + 1);
            if (mazeMap[i][j].equals("1")) {
                mazeMap[i][j] = "0";
                ++deleteCounter;
            }
        }

        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[i].length; j++) {
                if (i == 0)
                    mazeMap[i][j] = "0";
                if (j == 0)
                    mazeMap[i][j] = "0";
                if (i == mazeMap.length - 1)
                    mazeMap[i][j] = "0";
                if (j == mazeMap.length - 1)
                    mazeMap[i][j] = "0";

            }
        }
        mazeMap[10][7] = "0";
        mazeMap[10][8] = "0";
        mazeMap[10][9] = "0";
        mazeMap[10][10] = "0";
    }

    private static void mazeGenerator(int x, int y) {
        while (checkEmptyNeighbor(x, y)) {
            int[] neighbor = chooseRandomNeighbor(x, y);
            mazeMap[neighbor[0]][neighbor[1]] = "*";
            mazeMap[(neighbor[0] + x) / 2][(y + neighbor[1]) / 2] = "0";

            mazeGenerator(neighbor[0], neighbor[1]);
        }
    }

    private static int[] chooseRandomNeighbor(int x, int y) {
        Random random = new Random();

        while (true) {
            int randomMove = Math.abs(random.nextInt() % 4);
            if (randomMove == 0) {
                if (checkForOutOfBond(x + 2, y) &&
                        (mazeMap[x + 2][y].equals("-")))
                    return new int[]{x + 2, y};

            } else if (randomMove == 1) {
                if (checkForOutOfBond(x - 2, y) &&
                        (mazeMap[x - 2][y].equals("-")))
                    return new int[]{x - 2, y};

            } else if (randomMove == 2) {
                if (checkForOutOfBond(x, y + 2) &&
                        (mazeMap[x][y + 2].equals("-")))
                    return new int[]{x, y + 2};

            } else {
                if (checkForOutOfBond(x, y - 2) &&
                        (mazeMap[x][y - 2].equals("-")))
                    return new int[]{x, y - 2};
            }
        }
    }

    private static boolean checkEmptyNeighbor(int x, int y) {

        if (checkForOutOfBond(x + 2, y)) {
            if (mazeMap[x + 2][y].equals("-"))
                return true;
        }

        if (checkForOutOfBond(x - 2, y)) {
            if (mazeMap[x - 2][y].equals("-"))
                return true;
        }

        if (checkForOutOfBond(x, y + 2)) {
            if (mazeMap[x][y + 2].equals("-"))
                return true;
        }

        if (checkForOutOfBond(x, y - 2)) {
            if (mazeMap[x][y - 2].equals("-"))
                return true;
        }
        return false;
    }

    private static boolean checkForOutOfBond(int x, int y) {
        return (x >= 0) &&
                (y >= 0) &&
                (x <= (2 * mazeLength) - 1) &&
                (y <= (2 * mazeWidth) - 1);
    }

    private static void initializeMazeMap() {
        for (int i = 0; i < mazeMap.length; i++) {
            if (i == 0) {
                for (int j = 0; j < mazeMap[i].length; j++) {
                    if (j == 1)
                        mazeMap[i][j] = "1";
                    else
                        mazeMap[i][j] = "1";
                }
            } else if (i == mazeMap.length - 1) {
                for (int j = 0; j < mazeMap[i].length; j++) {
                    if (j == mazeMap[i].length - 2)
                        mazeMap[i][j] = "1";
                    else
                        mazeMap[i][j] = "1";
                }
            } else if (i % 2 == 1) {
                for (int j = 0; j < mazeMap[i].length; j++) {
                    if ((j == 0) || (j == mazeMap[i].length - 1))
                        mazeMap[i][j] = "1";
                    else if (j % 2 == 0)
                        mazeMap[i][j] = "1";
                    else
                        mazeMap[i][j] = "-";
                }
            } else {
                Arrays.fill(mazeMap[i], "1");
            }
        }
    }
}

