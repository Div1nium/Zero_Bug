package entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scenes.GameScene;
import utilities.AnimatedSprite;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public abstract class AnimatedThing extends Entity {

    /**
     * An object that allows you to display multiple sprite animation.
     */
    protected AnimatedSprite sprite;

    /**
     * Create an AnimatedThing object from a horizontal spriteSheet with a COLUMN number of sprites.
     * @param fileName filename of AnimatedThing's spriteSheet.
     * @param COLUMNS number of horizontal sprites.
     * @param COUNT index of the actual sprite displayed.
     * @param scale sets the dimensions' scale of the sprite.
     * @see AnimatedSprite
     */

    public AnimatedThing(String fileName, int COLUMNS, int COUNT, double scale) {
        imageView = new ImageView(new Image(fileName, GameScene.tileSize, GameScene.tileSize, true, true));
        sprite = new AnimatedSprite(imageView, 0.06, COLUMNS, COUNT, scale);
    }

    /**
     * @return the sprite's property value.
     */
    public AnimatedSprite getSprite() {
        return sprite;
    }

    @Override
    public String toString() {
        return "entities.AnimatedThing{" +
                "imageView.worldX= " + imageView.getX() +
                "imageView.worldY= " + imageView.getY() +
                '}';
    }
}
