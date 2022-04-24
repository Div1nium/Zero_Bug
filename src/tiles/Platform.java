package tiles;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Platform extends Tile {

    /**
     * Creates a Platform object at X and Y coordinates with the provided collision property and an ImageView provided by the fileName image.
     * @param fileName directory and name for the imageview's image property.
     * @param x X-axis coordinate.
     * @param y Y-axis coordinate.
     * @param collision collision check property.
     */
    public Platform(String fileName, double x, double y, boolean collision) {
        super(fileName, x, y, collision);
    }
}
