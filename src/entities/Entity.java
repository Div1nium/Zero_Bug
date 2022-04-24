package entities;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import utilities.CollisionChecker;

import java.util.ArrayList;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public abstract class Entity {

    /**
     * X-axis coordinate.
     */
    protected double worldX;
    /**
     * Y-axis coordinate.
     */
    protected double worldY;
    /**
     * X-axis velocity.
     */
    protected double velocityX;
    /**
     * Y-axis velocity.
     */
    protected double velocityY;
    /**
     * Y-axis acceleration.
     */
    protected double accelY;
    /**
     * Entity's reference speed on both X and Y axes.
     */
    protected int speed;
    /**
     * Entity's collided sides. Possible attributes :
     * <ul>
     * <li> Left
     * <li> Right
     * <li> Up
     * <li> Down
     * </ul>
     */
    protected ArrayList<String> collisions;
    /**
     * Entity's ImageView to be displayed.
     */
    protected ImageView imageView;

    /**
     * Entity's Rectangle hit box used to perform collision interactions.
     */
    protected Rectangle hitBox;

    /**
     * @return Entity's worldX property value.
     */
    public double getX() {
        return worldX;
    }

    /**
     * @param worldX is assigned to Entity's world X-axis' coordinate.
     */
    public void setX(double worldX) {
        this.worldX = worldX;
    }

    /**
     * @return Entity's worldY property value.
     */
    public double getY() {
        return worldY;
    }

    /**
     * @param worldY is assigned to Entity's world Y-axis' coordinate.
     */
    public void setY(double worldY) {
        this.worldY = worldY;
    }

    /**
     * @return Entity's X-axis' velocity in pixels per second.
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * @return Entity's Y-axis' velocity in pixels per second.
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * @return Entity's ImageView property value.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * @return Entity's hitBox property value.
     */
    public Rectangle getHitBox() {
        return hitBox;
    }
}
