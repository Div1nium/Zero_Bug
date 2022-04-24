package utilities;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scenes.GameScene;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class AnimatedSprite {

    /**
     * Number of horizontal sprites.
     */
    private final int columns;

    /**
     * AnimatedSprite's imageView property.
     * @see ImageView
     */
    private final ImageView imageView;

    /**
     * AnimatedSprite's scaling property on both X and Y axes.
     */
    private final double scale;

    /**
     * Index of the actual sprite shown.
     */
    private int index;

    /**
     * Animated Object's attitude property.
     */
    private String attitude = "idle";

    /**
     * Create the sprite Animation for an AnimatedThing's imageView
     * @param imageView imageView of the selected sprite from entities.AnimatedThing's spriteSheet
     * @param columns   number of sprites
     * @param index     index of the actual sprite
     * @param duration  requested elapsedTime between two sprite changes
     */

    public AnimatedSprite(ImageView imageView, double duration, int columns, int index, double scale) {
        this.index = index;
        this.columns = columns;
        this.imageView = imageView;
        this.scale = scale;

        LongValue lastNanoTime = new LongValue(System.nanoTime());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double elapsedTime = (l - lastNanoTime.value) / 1000000000.0;
                if (elapsedTime >= duration) {
                    lastNanoTime.value = l;
                    animatedRender();
                }
            }
        };
        timer.start();

    }

    /**
     * Select render type depending on the actual attitude
     */
    public void animatedRender() {
        switch (attitude) {
            case "run_right" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Run.png", GameScene.tileSize * 12, GameScene.tileSize, true, true));
                if (index < columns) {
                    imageView.setViewport(new Rectangle2D(index * GameScene.tileSize, 0, GameScene.tileSize, GameScene.tileSize));
                    imageView.setScaleX(scale);
                    imageView.setScaleY(scale);
                    index++;
                } else index = 0;
            }

            case "run_left" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Run.png", GameScene.tileSize * 12, GameScene.tileSize, true, true));
                if (index < columns) {
                    imageView.setViewport(new Rectangle2D(index * GameScene.tileSize, 0, GameScene.tileSize, GameScene.tileSize));
                    imageView.setScaleX(-scale);
                    imageView.setScaleY(scale);
                    index++;
                } else index = 0;
            }

            case "jump" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Jump.png", GameScene.tileSize, GameScene.tileSize, true, true));
                imageView.setViewport(new Rectangle2D(0, 0, GameScene.tileSize, GameScene.tileSize));
            }

            case "fall" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Fall.png", GameScene.tileSize, GameScene.tileSize, true, true));
                imageView.setViewport(new Rectangle2D(0, 0, GameScene.tileSize, GameScene.tileSize));
            }

            case "jump_left" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Jump.png", GameScene.tileSize, GameScene.tileSize, true, true));
                imageView.setViewport(new Rectangle2D(0, 0, GameScene.tileSize, GameScene.tileSize));
                imageView.setScaleX(-scale);
                imageView.setScaleY(scale);
            }

            case "jump_right" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Jump.png", GameScene.tileSize, GameScene.tileSize, true, true));
                imageView.setViewport(new Rectangle2D(0, 0, GameScene.tileSize, GameScene.tileSize));
                imageView.setScaleX(scale);
                imageView.setScaleY(scale);
            }

            case "fall_left" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Fall.png", GameScene.tileSize, GameScene.tileSize, true, true));
                imageView.setViewport(new Rectangle2D(0, 0, GameScene.tileSize, GameScene.tileSize));
                imageView.setScaleX(-scale);
                imageView.setScaleY(scale);
            }

            case "fall_right" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Fall.png", GameScene.tileSize, GameScene.tileSize, true, true));
                imageView.setViewport(new Rectangle2D(0, 0, GameScene.tileSize, GameScene.tileSize));
                imageView.setScaleX(scale);
                imageView.setScaleY(scale);
            }

            case "dead" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Hit.png", GameScene.tileSize * 7, GameScene.tileSize, true, true));
                if (index < 7) {
                    imageView.setViewport(new Rectangle2D(index * GameScene.tileSize, 0, GameScene.tileSize, GameScene.tileSize));
                    index++;
                } else {
                    index = 0;
                    attitude = "idle";
                }
            }

            case "idle" -> {
                imageView.setImage(new Image("/images/Tiles_32x32/Virtual Guy/Idle.png", GameScene.tileSize * 11, GameScene.tileSize, true, true));
                if (index < columns) {
                    imageView.setViewport(new Rectangle2D(index * GameScene.tileSize, 0, GameScene.tileSize, GameScene.tileSize));
                    if (imageView.getScaleX() > 0) imageView.setScaleX(scale);
                    else imageView.setScaleX(-scale);
                    imageView.setScaleY(scale);
                    index++;
                } else index = 0;
            }
        }
    }

    /**
     * Sets the attitude's property value.
     * @param attitude attitude value to overwrite to the actual one.
     */
    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }
}
