package sample.controller;

import javafx.scene.image.ImageView;
import sample.model.DIRECTIONS;

import java.util.Random;

public class GameController {
    private static GameController gameController = null;

    private GameController() {
    }

    public static GameController getInstance() {
        if (gameController == null)
            gameController = new GameController();
        return gameController;
    }

    public DIRECTIONS getRandomDirection() {
        Random random = new Random();
        int randomDirection = random.nextInt(4);
        DIRECTIONS direction = null;
        if (randomDirection % 4 == 0) {
            direction = DIRECTIONS.UP;
        } else if (randomDirection % 4 == 1) {
            direction = DIRECTIONS.DOWN;
        } else if (randomDirection % 4 == 2) {
            direction = DIRECTIONS.RIGHT;
        } else {
            direction = DIRECTIONS.LEFT;
        }
        return direction;
    }
}
