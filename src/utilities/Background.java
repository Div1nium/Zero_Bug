package utilities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scenes.GameScene;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public final class Background {

    /**
     * BackGround's ImageView property to be displayed.
     */
    private final ImageView backGroundImage;

    /**
     * GameScene in which the BackGround is displayed.
     * @see GameScene
     */
    private final GameScene gameScene;

    /**
     * X-axis coordinate.
     */
    private final double x;

    /**
     * Y-axis coordinate.
     */
    private final double y;


    /**
     * Creates a BackGround image from a file to be displayed at X and Y coordinates of the GameScene.
     * @param x X-axis coordinate.
     * @param y Y-axis coordinate.
     * @param fileName ImageView's file directory and name.
     * @param gameScene GameScene to be displayed.
     */
    public Background(double x, double y, String fileName, GameScene gameScene) {
        this.backGroundImage = new ImageView(new Image(fileName, gameScene.reader.getBackGroundWidth(), gameScene.reader.getBackGroundHeight(), false, false));
        this.backGroundImage.setX(x);
        this.backGroundImage.setY(y);
        this.gameScene = gameScene;
        this.x = x;
        this.y = y;
    }

    /**
     * Renders the BackGround's display based on the GameScene's camera reference.
     * @see Camera
     */
    public void render() {
        backGroundImage.setX(x - gameScene.getSceneCamera().getWorldX());
        backGroundImage.setY(y - gameScene.getSceneCamera().getWorldY());
    }

    /**
     * @return the imageView's property value.
     */
    public ImageView getImageView() {
        return backGroundImage;
    }

}
