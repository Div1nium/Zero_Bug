package scenes;

import entities.Hero;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import manager.TileManager;
import utilities.KeyHandler;
import utilities.*;

import java.util.Objects;

import static java.lang.Integer.parseInt;
import static javafx.scene.paint.Color.GRAY;
import static javafx.scene.paint.Color.WHITE;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class GameScene extends Scene {

    /**
     * Original Tile's size value.
     */
    private static final int originalTileSize = 64;
    /**
     * Tile's size scaling value.
     */
    private static final double scale = 1;
    /**
     * Tile's size value after scaling.
     */
    public static final int tileSize = (int) (originalTileSize * scale);

    /**
     * Maximum number of scaled tiles to show horizontally on the screen.
     */
    private static final int maxScreenCol = 16;
    /**
     * Maximum number of scaled tiles to show vertically on the screen.
     */
    private static final int maxScreenRow = 12;

    /**
     * Screen's width dimension.
     */
    public static final int screenWidth = tileSize * maxScreenCol;       // 1024
    /**
     * Screen's height dimension.
     */
    public static final int screenHeight = tileSize * maxScreenRow;      // 768
    /**
     * GameScene's background image.
     * @see Background
     */
    private final Background backgroundImage;
    /**
     * Inputs manager for actions to perform.
     * @see utilities.KeyHandler
     */
    private final KeyHandler keyHandler = new KeyHandler();
    /**
     * GameScene's Hero object.
     * @see Hero
     */
    private final Hero hero;
    /**
     * GameScene's camera reference for display.
     * @see Camera
     */
    private final Camera camera;
    /**
     * GameScene's TileManager.
     * @see TileManager
     */
    private final TileManager tileManager;
    /**
     * GameScene's CollisionChecker for collision management.
     * @see CollisionChecker
     */
    private final CollisionChecker collisionChecker;
    /**
     * GameScene's XmlReader for loading parameters based on the type of scene.
     * @see XmlReader
     */
    public XmlReader reader;

    /**
     * Creates a GameScene for a specific root Node with a specific size by loading parameters from the XmlReader based on the type of scene we create.
     * @param parent GameScene's root Node.
     * @param WIDTH window's width.
     * @param HEIGHT window's height.
     * @param fileName xml file's name.
     * @param type specifies the type of scene you want to create (!! Case Sensitive). Accepted values: level, room.
     */
    public GameScene(Group parent, int WIDTH, int HEIGHT, String fileName, String type) {
        super(parent, WIDTH, HEIGHT);
        this.setFill(GRAY);
        reader = new XmlReader("./data/" + fileName + ".xml", type);
        backgroundImage = new Background(reader.getBackGroundX(), reader.getBackGroundY(), reader.getBackGroundFileName(), this);
        parent.getChildren().add(backgroundImage.getImageView());

        setOnKeyPressed(keyHandler);
        setOnKeyReleased(keyHandler);

        hero = new Hero(this, keyHandler);
        camera = new Camera(GameScene.screenWidth, GameScene.screenHeight, hero);


        tileManager = new TileManager(this);
        collisionChecker = new CollisionChecker(this);

        //creating the rotation transformation
        Rotate rotate = new Rotate();
        //Setting the angle for the rotation
        rotate.setAngle(reader.getRotation());
        parent.getTransforms().addAll(rotate);

        parent.getChildren().add(hero.getImageView());
        parent.getChildren().add(hero.getText());
        parent.getChildren().add(hero.getCoin());
        parent.getChildren().add(hero.getKey());

        run();
    }

    /**
     * Lunches a timer which calls the update and render methods. This is the main loop of the game.
     */
    public void run() {
        LongValue lastNanoTime = new LongValue(System.nanoTime());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                double elapsedTime = (time - lastNanoTime.value) / 1000000000.0;

                if (elapsedTime > 0.016) {
                    lastNanoTime.value = time;
                    hero.update(elapsedTime);
                    camera.update(elapsedTime);
                    render(time);
                }
            }
        };
        timer.start();
    }

    /**
     * Renders every element's display periodically based on the time parameter.
     * @param time elapsed time since last call.
     * @see Hero#render(Camera camera)
     * @see Background#render()
     * @see TileManager#render(long time)
     */
    public void render(long time) {
        hero.render(camera);
        backgroundImage.render();
        tileManager.render(time);
    }

    /**
     * This method is only used for debugging purposes. It will add textFields to the screen to monitor some interesting values.
     * @param parent GameScene's root Node.
     */
    private void addTextFieldsOnScreen(Group parent) {
        Text text = new Text();
        Text vector = new Text();
        Text keysPressed = new Text();
        Text coordinates = new Text();
        Text fps = new Text();

        Button spawn = new Button("Respawn");
        TextField x = new TextField();
        TextField y = new TextField();

        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        text.setFill(WHITE);
        coordinates.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        coordinates.setFill(WHITE);
        vector.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        vector.setFill(WHITE);
        keysPressed.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        keysPressed.setFill(WHITE);
        fps.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        fps.setFill(WHITE);

        spawn.setOnMouseClicked(mouseEvent -> hero.setSpawnCoordinates(parseInt(x.getText()), parseInt(y.getText())));

        x.setLayoutX(740);
        x.setLayoutY(20);
        y.setLayoutX(740);
        y.setLayoutY(60);
        spawn.setLayoutX(740);
        spawn.setLayoutY(100);
        text.setX(20);
        text.setY(20);
        vector.setX(20);
        vector.setY(60);
        keysPressed.setX(20);
        keysPressed.setY(40);
        coordinates.setX(400);
        coordinates.setY(40);
        fps.setX(400);
        fps.setY(20);


        parent.getChildren().add(text);

        parent.getChildren().add(vector);
        parent.getChildren().add(keysPressed);
        parent.getChildren().add(coordinates);
        parent.getChildren().add(fps);


    }

    /**
     * @return the value of the property camera.
     */
    public Camera getSceneCamera() {
        return camera;
    }

    /**
     * @return the value of the property hero.
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Is used to indefinitely play a sound saved in the resources' directory that matches with the filename parameter.
     * If none of the files match, a NullPointerException is thrown.
     * @param filename sound's file name to be played.
     * @see AudioClip
     */
    public void playSoundInf(String filename) {
        String url = Objects.requireNonNull(getClass().getResource("/" + filename)).toString();
        AudioClip clip = new AudioClip(url);
        clip.setCycleCount(AudioClip.INDEFINITE);
        clip.play();
    }

    /**
     * @return the value of the property tileManager.
     */
    public TileManager getTileManager() {
        return tileManager;
    }

    /**
     * @return the value of the property collisionChecker.
     */
    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }
}