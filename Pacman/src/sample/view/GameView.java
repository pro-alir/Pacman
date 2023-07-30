package sample.view;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.util.Duration;
import sample.controller.GameController;
import sample.controller.MazeGenerator;
import sample.model.DIRECTIONS;
import sample.model.Ghost;

import java.lang.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


public class GameView {
    private Stage stage;
    private Scene scene;
    private AnchorPane anchorPane;
    private String[][] maze = MazeGenerator.generateMaze();

    private GameController gameController;

    private boolean isUpKeyPressed = false;
    private boolean isDownKeyPressed = false;
    private boolean isRightKeyPressed = false;
    private boolean isLeftKeyPressed = false;
    private boolean isStartKeyPressed = false;

    private int angle = 0;
    @FXML
    private ImageView pacman;

    private Ghost colorfulGhost;
    private Ghost redGhost;
    private Ghost greenGhost;
    private Ghost yellowGhost;

    private ArrayList<Ghost> ghosts;
    private ArrayList<ImageView> bombs;

    private Timeline timeline;

    private final int LENGTH_OF_MAZE = 25;
    private final int LENGTH_OF_EACH_CELL = 20;

    private boolean isPacManAteBomb = false;

    private int extraRandomMove = 0;

    public GameView() throws FileNotFoundException {

        anchorPane = new AnchorPane();

        initializePacman();
        ghosts = new ArrayList<>();
        createGhosts();
        bombs = new ArrayList<>();
        initializeBombs();

        stage = new Stage();
        scene = new Scene(anchorPane, LENGTH_OF_MAZE * LENGTH_OF_EACH_CELL,
                LENGTH_OF_MAZE * LENGTH_OF_EACH_CELL, Color.BLACK);

        stage.setScene(scene);
        anchorPane.getChildren().add(pacman);

        showMaze();

        gameController = GameController.getInstance();

        generateKeyListeners();
        gameLoop();
    }

    public Stage getStage() {
        return stage;
    }


    private void initializePacman() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src\\sample\\view\\pictures\\pacmanPicture.gif"));
        pacman = new ImageView(image);

        pacman.setFitHeight(LENGTH_OF_EACH_CELL);
        pacman.setFitWidth(LENGTH_OF_EACH_CELL);

        pacman.setLayoutX((double) LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1) / 2);
        pacman.setLayoutY(0);
    }

    public void initializeBombs() throws FileNotFoundException {
        int bombCounter = 0;
        while (bombCounter < 7) {
            Image image = new Image(new FileInputStream("src\\sample\\view\\pictures\\bomb5.gif"));
            ImageView imageView = new ImageView(image);

            Random random = new Random();
            int bombX = random.nextInt(LENGTH_OF_MAZE - 1);
            int bombY = random.nextInt(LENGTH_OF_MAZE - 1);
            if (maze[bombX][bombY].equals("0")) {
                imageView.setLayoutX(bombX * LENGTH_OF_EACH_CELL);
                imageView.setLayoutY(bombY * LENGTH_OF_EACH_CELL);
                imageView.setFitHeight(LENGTH_OF_EACH_CELL);
                imageView.setFitWidth(LENGTH_OF_EACH_CELL);
                anchorPane.getChildren().add(imageView);
                bombs.add(imageView);
                ++bombCounter;

            }
        }
    }

    public void createGhosts() throws FileNotFoundException {

        Image image = new Image(new FileInputStream("src\\sample\\view\\pictures\\colorful.gif"));
        colorfulGhost = new Ghost(image);
        addGhostToMaze(colorfulGhost, 0, LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1));
        colorfulGhost.setDirection(DIRECTIONS.LEFT);

        image = new Image(new FileInputStream("src\\sample\\view\\pictures\\yellow.gif"));
        yellowGhost = new Ghost(image);
        addGhostToMaze(yellowGhost, 0, 0);
        yellowGhost.setDirection(DIRECTIONS.RIGHT);

        image = new Image(new FileInputStream("src\\sample\\view\\pictures\\red.gif"));
        redGhost = new Ghost(image);
        addGhostToMaze(redGhost, LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1), 0);
        redGhost.setDirection(DIRECTIONS.DOWN);

        image = new Image(new FileInputStream("src\\sample\\view\\pictures\\green.gif"));
        greenGhost = new Ghost(image);
        addGhostToMaze(greenGhost, LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1),
                LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1));
        greenGhost.setDirection(DIRECTIONS.UP);
    }

    private void addGhostToMaze(Ghost ghost, int x, int y) {
        ghost.getImageView().setLayoutX(x);
        ghost.getImageView().setLayoutY(y);

        ghost.getImageView().setFitHeight(LENGTH_OF_EACH_CELL);
        ghost.getImageView().setFitWidth(LENGTH_OF_EACH_CELL);

        anchorPane.getChildren().add(ghost.getImageView());
        ghosts.add(ghost);
    }


    public void showMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].equals("1")) {
                    Rectangle rectangle = new Rectangle(LENGTH_OF_EACH_CELL, LENGTH_OF_EACH_CELL);
                    rectangle.setFill(Color.SKYBLUE);
                    rectangle.setStroke(Color.GREY);
                    rectangle.setX(i * LENGTH_OF_EACH_CELL);
                    rectangle.setY(j * LENGTH_OF_EACH_CELL);
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setWidth(2);
                    dropShadow.setHeight(2);
                    dropShadow.setRadius(10);
                    rectangle.setEffect(dropShadow);
                    anchorPane.getChildren().add(rectangle);
                }
            }
        }
    }

    public void generateKeyListeners() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.UP) {
                    isUpKeyPressed = true;
                    isStartKeyPressed = true;
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = true;
                    isStartKeyPressed = true;

                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                    isStartKeyPressed = true;

                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                    isStartKeyPressed = true;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.UP) {
                    isUpKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                }
            }
        });
    }

    public void gameLoop() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            if (isStartKeyPressed) {
                if (isPacManAteBomb) {
                    try {
                        changeFaceOfGhostsToRunning();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (collidePacmanAndGhosts()) {
                    isStartKeyPressed = false;
                    repositElements();
                }
                if (isStartKeyPressed) {
                    movePacMan();
                    moveGhost();
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void changeFaceOfGhostsToRunning() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src\\sample\\view\\pictures\\runnerGhost.gif"));
        ImageView imageView = new ImageView(image);
        for (Ghost ghost : ghosts) {
            ghost.setImageView(imageView);
        }
    }

    public void repositElements() {
        repositElement(pacman, LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1) / 2, 0);
        repositElement(colorfulGhost.getImageView(), 0, LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1));
        repositElement(yellowGhost.getImageView(), 0, 0);
        repositElement(redGhost.getImageView(), LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1), 0);
        repositElement(greenGhost.getImageView(), LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1),
                LENGTH_OF_EACH_CELL * (LENGTH_OF_MAZE - 1));
    }

    public void repositElement(Node node, int x, int y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
    }


    public void movePacMan() {
        if (isUpKeyPressed) {
            angle = 270;
            pacman.setRotate(angle);
            if ((((int) pacman.getLayoutY() / LENGTH_OF_EACH_CELL) != 0)
                    && !maze[(int) pacman.getLayoutX() / LENGTH_OF_EACH_CELL]
                    [((int) pacman.getLayoutY() / LENGTH_OF_EACH_CELL) - 1].equals("1")) {
                pacman.setLayoutY(pacman.getLayoutY() - LENGTH_OF_EACH_CELL);
            }
        }
        if (isDownKeyPressed) {
            angle = 90;
            pacman.setRotate(angle);
            if ((((int) pacman.getLayoutY() / LENGTH_OF_EACH_CELL) != LENGTH_OF_MAZE - 1)
                    && !maze[(int) pacman.getLayoutX() / LENGTH_OF_EACH_CELL]
                    [((int) pacman.getLayoutY() / LENGTH_OF_EACH_CELL) + 1].equals("1")) {
                pacman.setLayoutY(pacman.getLayoutY() + LENGTH_OF_EACH_CELL);
            }
        }
        if (isRightKeyPressed) {
            angle = 0;
            pacman.setRotate(angle);
            if ((((int) pacman.getLayoutX() / LENGTH_OF_EACH_CELL) != LENGTH_OF_MAZE - 1)
                    && !maze[((int) pacman.getLayoutX() / LENGTH_OF_EACH_CELL) + 1]
                    [((int) pacman.getLayoutY() / LENGTH_OF_EACH_CELL)].equals("1")) {
                pacman.setLayoutX(pacman.getLayoutX() + LENGTH_OF_EACH_CELL);
            }
        }
        if (isLeftKeyPressed) {
            angle = 180;
            pacman.setRotate(angle);
            if ((((int) pacman.getLayoutX() / LENGTH_OF_EACH_CELL) != 0)
                    && (!maze[((int) pacman.getLayoutX() / LENGTH_OF_EACH_CELL) - 1]
                    [((int) pacman.getLayoutY() / LENGTH_OF_EACH_CELL)].equals("1"))) {
                pacman.setLayoutX(pacman.getLayoutX() - LENGTH_OF_EACH_CELL);
            }
        }
    }

    public void moveGhost() {
        for (Ghost ghost : ghosts) {
            if (extraRandomMove == 4) {
                ghost.setDirection(gameController.getRandomDirection());
                if (checkForFreeWay(ghost.getImageView(), ghost.getDirection())) {
                    move(ghost.getImageView(), ghost.getDirection());
                    extraRandomMove = 0;
                }
            } else {
                while (!checkForFreeWay(ghost.getImageView(), ghost.getDirection())) {
                    ghost.setDirection(gameController.getRandomDirection());
                }
                move(ghost.getImageView(), ghost.getDirection());
            }
            ++extraRandomMove;
        }
    }

    public void move(Node node, DIRECTIONS direction) {
        if (direction == DIRECTIONS.UP)
            node.setLayoutY(node.getLayoutY() - LENGTH_OF_EACH_CELL);
        else if (direction == DIRECTIONS.DOWN)
            node.setLayoutY(node.getLayoutY() + LENGTH_OF_EACH_CELL);
        else if (direction == DIRECTIONS.RIGHT)
            node.setLayoutX(node.getLayoutX() + LENGTH_OF_EACH_CELL);
        else if (direction == DIRECTIONS.LEFT)
            node.setLayoutX(node.getLayoutX() - LENGTH_OF_EACH_CELL);
    }

    public boolean checkForFreeWay(Node node, DIRECTIONS direction) {
        if (direction == DIRECTIONS.UP) {
            return (((int) node.getLayoutY() / LENGTH_OF_EACH_CELL) > 0) &&
                    !maze[(int) node.getLayoutX() / LENGTH_OF_EACH_CELL]
                            [((int) node.getLayoutY() / LENGTH_OF_EACH_CELL) - 1].equals("1");
        } else if (direction == DIRECTIONS.DOWN) {
            return (((int) node.getLayoutY() / LENGTH_OF_EACH_CELL) < LENGTH_OF_MAZE - 1) &&
                    !maze[(int) node.getLayoutX() / LENGTH_OF_EACH_CELL]
                            [((int) node.getLayoutY() / LENGTH_OF_EACH_CELL) + 1].equals("1");
        } else if (direction == DIRECTIONS.RIGHT) {
            return (((int) node.getLayoutX() / LENGTH_OF_EACH_CELL) < LENGTH_OF_MAZE - 1) &&
                    !maze[((int) node.getLayoutX() / LENGTH_OF_EACH_CELL) + 1]
                            [((int) node.getLayoutY() / LENGTH_OF_EACH_CELL)].equals("1");
        } else if (direction == DIRECTIONS.LEFT) {
            return (((int) node.getLayoutX() / LENGTH_OF_EACH_CELL) > 0) &&
                    (!maze[((int) node.getLayoutX() / LENGTH_OF_EACH_CELL) - 1]
                            [((int) node.getLayoutY() / LENGTH_OF_EACH_CELL)].equals("1"));
        }
        return false;
    }

    public boolean collidePacmanAndGhosts() {
        for (Ghost ghost : ghosts) {
            ImageView ghostView = ghost.getImageView();
            if (ghost == yellowGhost) {
                System.out.println(Math.abs(pacman.getLayoutX() - ghostView.getLayoutX()));
                System.out.println(Math.abs(pacman.getLayoutY() - ghostView.getLayoutY()));
            }
            if ((Math.abs(pacman.getLayoutX() - ghostView.getLayoutX()) < ((double) LENGTH_OF_EACH_CELL)) &&
                    (Math.abs(pacman.getLayoutY() - ghostView.getLayoutY()) < ((double) LENGTH_OF_EACH_CELL))) {
                System.out.println("yep");
                return true;
            }
        }
        return false;
    }
}





















