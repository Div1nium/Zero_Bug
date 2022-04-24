package entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import scenes.GameScene;
import tiles.Platform;
import utilities.AnimatedSprite;
import utilities.Camera;
import utilities.CollisionChecker;
import utilities.KeyHandler;
import utilities.XmlReader;

import java.util.ArrayList;
import java.util.Objects;

import static javafx.scene.paint.Color.YELLOW;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Hero extends AnimatedThing {

    /**
     * Hero's displayed ImageView's X and Y axis scaling.
     */
    private static final double scale = 1;
    /**
     * Hero's ImageView's default source file.
     */
    private static final String defaultFileName = "/images/Tiles_32x32/Virtual Guy/Idle.png";
    /**
     * Number of sprites used for the animation.
     */
    private static final int COLUMNS = 10;
    /**
     * Actual index referring to the sprite sheet's displayed image.
     */
    private static final int COUNT = 0;
    /**
     * Hero's collected coin number.
     */
    private static int score;
    /**
     * GameScene in which the Hero belongs.
     */
    private final GameScene gameScene;
    /**
     * Inputs manager for actions to perform.
     */
    private final KeyHandler keyHandler;
    /**
     * Hero's interaction possibility with other elements of the scene.
     */
    private boolean interact = false;
    /**
     * Hero's default Y-axis acceleration value.
     */
    private double gravity = 600;
    /**
     * Hero's actual state (FLOOR or JUMP).
     */
    private State actualState;
    /**
     * Field to display Hero's collected coins number.
     */
    private Text text;
    /**
     * Coin ImageView used for the Hero's GUI.
     */
    private ImageView coin;
    /**
     * Key ImageView used for the Hero's GUI.
     */
    private ImageView key;

    /**
     * Creates the Hero to be displayed in the GameScene. The Hero performs different actions
     * based on the inputs registered by the KeyHandler.
     *
     * @param gameScene the gameScene where the Hero is displayed.
     * @param keyHandler the input handler for actions to be performed.
     * @see GameScene
     * @see KeyHandler
     */
    public Hero(GameScene gameScene, KeyHandler keyHandler) {
        super(defaultFileName, COLUMNS, COUNT, scale);
        this.gameScene = gameScene;
        this.keyHandler = keyHandler;
        loadHeroValues();
        loadHeroInterface();
        hitBox = new Rectangle(imageView.getX(), imageView.getY(), GameScene.tileSize, GameScene.tileSize);
        accelY = gravity;
        collisions = new ArrayList<>();
        actualState = State.JUMP;
    }

    /**
     * @return the Hero's interact property.
     * <ul>
     * <li>True: The hero is allowed to interact.
     * <li>False: The hero is not allowed to interact.
     * </ul>
     */
    public boolean isInteracting() {
        return interact;
    }

    /**
     * Modify the Hero's possibility to interact with other elements.
     * @param interact value to apply to Hero's interact parameter.
     */
    public void setInteract(boolean interact) {
        this.interact = interact;
    }

    /**
     * @return the reference of the collected number of coins field.
     */
    public Text getText() {
        return text;
    }

    /**
     * Adds an amount of coins to the number of collected coins.
     * @param amount coins to be added to the score.
     */
    public void addScore(int amount) {
        score = score + amount;
    }

    /**
     * @return the number of collected coins.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the coin ImageView used by the GUI.
     */
    public ImageView getCoin() {
        return coin;
    }

    /**
     * @return the key ImageView used by the GUI.
     */
    public ImageView getKey() {
        return key;
    }

    /**
     * Loads the Hero's GUI elements in memory.
     * List of elements loaded :
     * <ul>
     * <li>Coin (Image displayed that refers to the actual number of coins)
     * <li>Key (Image displayed that refers to the actual number of coins)
     * <li>Score (Hero's number of collected coins)
     * </ul>
     */
    public void loadHeroInterface() {
        coin = new ImageView(new Image("/images/Tiles_256x256/Coin.gif", 20, 20, true, true));
        key = new ImageView(new Image("/images/Tiles_256x256/pixel-key.png", 40, 40, true, true));
        score = 0;
        text = new Text();
        coin.setX(960);
        coin.setY(35);
        key.setX(960);
        key.setY(85);
        text.setX(980);
        text.setY(50);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        text.setFill(YELLOW);
        text.setText("x" + score);
    }

    /**
     * Assigns the main physics values to the Hero on the creation of this object.
     * Tha values to be used are extracted form the XmlReader object.
     * List of values loaded :
     * <ul>
     * <li>WorldX (Hero's starting X-axis coordinate)
     * <li>WorldY (Hero's starting Y-axis coordinate)
     * <li>Speed (Hero's movement speed on both X and Y axis)
     * <li>Gravity (Hero's gravity applied on Y axis)
     * </ul>
     * @see XmlReader
     */
    public void loadHeroValues() {
        gravity = gameScene.reader.getGravity();
        speed = gameScene.reader.getSpeed();
        worldX = gameScene.reader.getStartPosX();
        worldY = gameScene.reader.getStartPosY();
    }

    /**
     * This method is called by the GameScene object to perform the Hero's updates.
     * This method is sensitive to inputs registered in the KeyHandler to apply movement speed on both X and Y axes and to perform interactions
     * with the other elements of the scene. Also, it checks if collisions were occurred since last call in order to allow the Hero's movement.
     * @param time the elapsed time since update's last call.
     * @see CollisionChecker
     * @see KeyHandler
     * @see GameScene
     */

    public void update(double time) {
        text.setText("x" + score);

        resetCollisions();

        if (!keyHandler.keysPressed.contains(KeyCode.E)) interact = true;

        if (keyHandler.keysPressed.contains(KeyCode.E)) {
            gameScene.getCollisionChecker().borrowDoor(this);
            gameScene.getCollisionChecker().isTalking(this);
        }

        gameScene.getCollisionChecker().collectCoin(this);


        if ((!keyHandler.keysPressed.contains(KeyCode.D) && !keyHandler.keysPressed.contains(KeyCode.Q))
                || (keyHandler.keysPressed.contains(KeyCode.D) && keyHandler.keysPressed.contains(KeyCode.Q))) {
            velocityX = 0;
            if (actualState.equals(State.JUMP)) {
                if (velocityY > 0) sprite.setAttitude("fall");
                else sprite.setAttitude("jump");
            } else sprite.setAttitude("idle");
        }
        if ((keyHandler.keysPressed.contains(KeyCode.Q) && !keyHandler.keysPressed.contains(KeyCode.D))) {
            velocityX = -speed;
            if (actualState.equals(State.JUMP)) {
                if (velocityY > 0) sprite.setAttitude("fall_left");
                else sprite.setAttitude("jump_left");
            } else sprite.setAttitude("run_left");
        }
        if ((keyHandler.keysPressed.contains(KeyCode.D) && !keyHandler.keysPressed.contains(KeyCode.Q))) {
            velocityX = speed;
            if (actualState.equals(State.JUMP)) {
                if (velocityY > 0) sprite.setAttitude("fall_right");
                else sprite.setAttitude("jump_right");
            } else sprite.setAttitude("run_right");
        }

        if ((keyHandler.keysPressed.contains(KeyCode.Z) || keyHandler.keysPressed.contains(KeyCode.UP))) {
            jump();
        }
        //Compute Y movement value
        if (!actualState.equals(State.FLOOR)) {
            if (velocityY >= speed) velocityY = speed;
            else velocityY += 1.9 * accelY * time;
        }

        gameScene.getCollisionChecker().checkCollision(this);
        gameScene.getCollisionChecker().isDead(this);

        if (collisions.isEmpty()) actualState = State.JUMP;
        else {
            if (collisions.contains("Up")) {
                velocityY = 0;
            }
            if (collisions.contains("Down")) {
                actualState = State.FLOOR;
                velocityY = 0;
            }

            if (collisions.contains("Right") || collisions.contains("Left")) {
                velocityX = 0;
            }
        }

        worldX += velocityX * time;
        worldY += velocityY * time;
    }

    /**
     * Renders the Hero's display based on the Camera parameter.
     * @param camera the camera to use as a display reference.
     * @see Camera
     */
    public void render(Camera camera) {
        imageView.setX(worldX - camera.getWorldX());
        imageView.setY(worldY - camera.getWorldY());

        hitBox.setX(imageView.getX());
        hitBox.setY(imageView.getY());
    }

    /**
     * Is used to teleport the hero at a point defined by the X and Y parameters.
     * The camera is also updated to display the Hero in the middle of the screen.
     * @param x X-axis coordinate.
     * @param y Y-axis coordinate.
     * @see Camera
     */
    public void setSpawnCoordinates(double x, double y) {
        worldX = x;
        worldY = y;
        render(gameScene.getSceneCamera());
        gameScene.getSceneCamera().recenterOnHero();
    }

    /**
     * This method makes the Hero jump if he is allowed to.
     * Note that to perform this action, the Hero has to be on a platform (in other words, his state must be FLOOR).
     * Each time this action is performed, the Hero's attitude changes to "Jump" (which updates his imageView) and a sound is played.
     * @see AnimatedSprite
     */

    public void jump() {
        if (actualState.equals(State.FLOOR)) {
            actualState = State.JUMP;
            sprite.setAttitude("jump");
            velocityY = -450;
            playSound("music/jump.wav");
        }
    }

    /**
     * Is called every time a collision is detected between the Hero's hit box and a platform object.
     * We save the collided sides to perform the appropriate action.
     * @param side the side on which the hero collides with a platform object.
     * @see Platform
     * @see CollisionChecker
     */
    public void setCollision(String side) {
        if (!collisions.contains(side)) collisions.add(side);
    }

    /**
     * Is used at the beginning of the update method to clear the list of the collided sides.
     */
    public void resetCollisions() {
        collisions.clear();
    }

    /**
     * Is used to play a sound saved in the resources' directory that matches with the filename parameter.
     * If none of the files match, a NullPointerException is thrown.
     * @param filename sound's file name to be played.
     * @see AudioClip
     */
    public void playSound(String filename) {
        String url = Objects.requireNonNull(getClass().getResource("/" + filename)).toString();
        AudioClip clip = new AudioClip(url);
        clip.play();
    }

    /**
     * Returns the Hero's ActualState in the GameScene.
     * This provides information on the actions that can be performed by the Hero.
     * @return the Hero's actualState property value.
     * @see State
     */
    public State getState() {
        return actualState;
    }

    /**
     * Definition of two possible states for the Hero.
     * <ul>
     * <li>FLOOR: The Hero is laid on a platform.
     * <li>JUMP: The Hero is in the airs.
     * </ul>
     */
    public enum State {JUMP, FLOOR}
}
