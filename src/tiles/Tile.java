package tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import scenes.GameScene;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public abstract class Tile {

    /**
     * X-axis coordinate.
     */
    protected final double worldX;

    /**
     * Y-axis coordinate.
     */
    protected final double worldY;

    /**
     * Tile's ImageView to be displayed.
     * @see ImageView
     */
    protected ImageView imageView;

    /**
     * Tile's collision property.
     * <ul>
     * <li>True: Collisions are checked with this element.
     * <li>False: Collisions are not checked with this element.
     * </ul>
     */
    protected boolean collision;

    /**
     * Tile's Rectangle hit box used to perform collision interactions.
     * @see Rectangle
     */
    protected Rectangle hitBox;

    /**
     * Creates a Tile object at X and Y coordinates with the provided collision property and an ImageView provided by the fileName image.
     * @param fileName directory and name for the imageview's image property.
     * @param x X-axis coordinate.
     * @param y Y-axis coordinate.
     * @param collision collision check property.
     */
    public Tile(String fileName, double x, double y, boolean collision) {
        imageView = new ImageView(new Image(fileName, GameScene.tileSize, GameScene.tileSize, true, true));
        worldX = x;
        worldY = y;
        imageView.setX(worldX);
        imageView.setY(worldY);
        this.collision = collision;
        hitBox = new Rectangle(imageView.getX(), imageView.getY(), imageView.getImage().getWidth(), imageView.getImage().getHeight());
    }

    /**
     * @return the worldX's property value.
     */
    public double getWorldX() {
        return worldX;
    }

    /**
     * @return the worldX's property value.
     */
    public double getWorldY() {
        return worldY;
    }

    /**
     * @return the imageView's property value.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * @return the collision's property value.
     */
    public boolean getCollision() {
        return collision;
    }

    /**
     * Sets the collision's property value based on the collision parameter.
     * @param collision the value to set.
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    /**
     * @return the hitBox's property value.
     */
    public Rectangle getHitBox() {
        return hitBox;
    }


}
