package utilities;

import entities.Hero;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;
import main.Main;
import scenes.GameScene;
import tiles.*;

import java.util.ArrayList;
import java.util.Objects;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class CollisionChecker {

    /**
     * GameScene property where the CollisionChecker belongs to.
     * @see GameScene
     */
    private final GameScene gameScene;

    /**
     * List of platforms in collision with the Hero.
     */
    private final ArrayList<Platform> collisionPlatforms = new ArrayList<>();

    /**
     * Creates a CollisionChecker object in the specified GameScene.
     * @param gameScene The GameScene this object is attached to.
     */
    public CollisionChecker(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Checks for collisions between the Hero and the platform objects of the GameScene.
     * Note : the algorithm used is all but perfect, feel free to modify/update it for a better one.
     * @param hero Hero object to check the collisions with.
     */
    public void checkCollision(Hero hero) {
        double heroLeftX = hero.getHitBox().getX();
        double heroRightX = hero.getHitBox().getX() + hero.getHitBox().getWidth();
        double heroTopY = hero.getHitBox().getY();
        double heroBottomY = hero.getHitBox().getY() + hero.getHitBox().getHeight();

        double offset = 6;
        double width = hero.getImageView().getImage().getWidth();
        double height = hero.getImageView().getImage().getHeight();
        double yLimit = height - offset;
        double xLimit = width - offset;

        collisionPlatforms.clear();
        ArrayList<Object> vectors = new ArrayList<>();

        for (Platform platform : gameScene.getTileManager().getPlatforms()) {
            if (platform.getHitBox().getBoundsInParent().intersects(hero.getHitBox().getBoundsInParent())) {
                collisionPlatforms.add(platform);
            }
        }


        if (collisionPlatforms.size() > 0) {
            for (Platform touchedPlatform : collisionPlatforms) {
                if (touchedPlatform.getCollision()) {
                    Point2D vector;
                    switch (hero.getState()) {
                        case JUMP -> {
                            vector = new Point2D((float) (heroLeftX - (touchedPlatform.getHitBox().getX())),
                                    (float) (heroTopY - touchedPlatform.getHitBox().getY()));
                            vectors.add(vector);
                            if (vector.getY() > -height && vector.getY() < -yLimit && Math.abs(Math.abs(vector.getX()) - Math.abs(vector.getY())) > offset && hero.getVelocityY() >= 0) {
                                hero.setCollision("Down");
                            } else if (vector.getY() > yLimit - 4 && vector.getY() < height && Math.abs(Math.abs(vector.getX()) - Math.abs(vector.getY())) > 10 && hero.getVelocityY() < 0)
                                hero.setCollision("Up");
                            else if (vector.getX() > -width && vector.getX() < -xLimit && vector.getY() > -yLimit && hero.getVelocityX() > 0) {
                                hero.setCollision("Right");
                            } else if (vector.getX() < width && vector.getX() > xLimit && vector.getY() > -yLimit && hero.getVelocityX() < 0) {
                                hero.setCollision("Left");
                            }
                        }
                        case FLOOR -> {
                            vector = new Point2D((float) (heroLeftX - (touchedPlatform.getHitBox().getX())),
                                    (float) (heroTopY - touchedPlatform.getHitBox().getY()));
                            vectors.add(vector);
                            if (vector.getY() > -height && vector.getY() < -yLimit && hero.getVelocityY() >= 0) {
                                hero.setCollision("Down");
                                hero.setY(touchedPlatform.getWorldY() - hero.getHitBox().getHeight() + 1);
                            } else if (vector.getX() > -width && vector.getX() < -xLimit && vector.getY() > -yLimit && hero.getVelocityX() > 0) {
                                hero.setCollision("Right");
                                hero.setX(touchedPlatform.getWorldX() - hero.getHitBox().getWidth() + 1);
                            } else if (vector.getX() < width && vector.getX() > xLimit && vector.getY() > -yLimit && hero.getVelocityX() < 0) {
                                hero.setCollision("Left");
                                hero.setX(touchedPlatform.getWorldX() + hero.getHitBox().getWidth() - 1);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks for interactions between the Hero and the coin objects of the GameScene.
     * Every time a coin is collected, it is added to the Hero's inventory and a sound is played.
     * @param hero Hero object to check the interactions with.
     * @see Hero#addScore(int amount)
     */
    public void collectCoin(Hero hero) {
        for (Coin coin : gameScene.getTileManager().getCoins()) {

            if (coin.getImageView().getBoundsInParent().intersects(hero.getHitBox().getBoundsInParent()) && coin.getCollision()) {
                coin.setCollision(false);
                coin.getImageView().setVisible(false);
                gameScene.getHero().addScore(1);
                System.out.println(gameScene.getHero().getScore());
                playSound("music/Coin.wav");

            }
        }
    }

    /**
     * Checks for collisions between the Hero and the spike objects of the GameScene.
     * If the Hero dies, a sound is played, and he automatically respawns at the beginning of the actual Room/Level.
     * @param hero Hero object to check the collisions with.
     * @see Hero#setSpawnCoordinates(double, double)
     */
    public void isDead(Hero hero) {
        for (Spike spike : gameScene.getTileManager().getSpikes())
            if (hero.getHitBox().getBoundsInParent().intersects(spike.getHitBox().getBoundsInParent())) {
                System.out.println("My Hero Is Dead");
                playSound("./music/Death2.wav");
                hero.setSpawnCoordinates(gameScene.reader.getStartPosX(), gameScene.reader.getStartPosY());
            }
    }

    /**
     * Checks for interactions between the Hero and the Npc objects of the GameScene.
     * If it occurs, a dialog starts with the Npc.
     * @param hero Hero object to check the collisions with.
     * @see Npc#displayDialog(GameScene)
     */
    public void isTalking(Hero hero) {
        for (Npc npc : gameScene.getTileManager().getNpc())
            if (hero.getHitBox().getBoundsInParent().intersects(npc.getHitBox().getBoundsInParent())) {
                hero.setInteract(false);
                npc.displayDialog(gameScene);
            }
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
     * Checks for interactions between the Hero and the Door objects of the GameScene.
     * If it occurs, the Hero is teleported to the direction defined at the door's direction property value.
     * @param hero Hero object to check the collisions with.
     * @see Door#getDirection()
     */
    public void borrowDoor(Hero hero) {
        if (hero.isInteracting()) {
            for (Door door : gameScene.getTileManager().getDoors())
                if (hero.getHitBox().getBoundsInParent().intersects(door.getHitBox().getBoundsInParent()) && hero.getState().equals(Hero.State.FLOOR)) {
                    hero.setInteract(false);
                    String direction = door.getDirection();
                    if (direction.contains("Room"))
                        Main.actualRoom = direction;
                    else if (direction.contains("Level")) {
                        Main.actualLevel = direction;
                        Main.actualRoom = "default";
                    } else Main.actualRoom = "default";
                }
        }
    }
}

