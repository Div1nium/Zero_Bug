package tiles;

import javafx.scene.image.Image;
import main.Main;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Door extends Tile {
    /**
     * Destination of the door. It can be a room or a level.
     * @see Main#actualLevel
     * @see Main#actualRoom
     */
    private final String direction;

    /**
     * Creates a tile object at X and Y coordinates with the provided collision property and an ImageView provided by the fileName image.
     * @param fileName directory and name for the imageview's image property.
     * @param x X-axis coordinate.
     * @param y Y-axis coordinate.
     * @param collision collision check property.
     * @param direction Room/Level direction this object teleports the Hero to.
     */
    public Door(String fileName, double x, double y, boolean collision, String direction) {
        super(fileName, x, y, collision);
        double requestedWidth = 150;
        double requestedHeight = 90;
        imageView.setImage(new Image(fileName, requestedWidth, requestedHeight, true, true));
        this.direction = direction;
    }

    /**
     * @return the direction's property value.
     */
    public String getDirection() {
        return direction;
    }


}
