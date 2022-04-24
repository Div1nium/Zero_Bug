package tiles;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Spike extends Tile {

    /**
     * Creates a Spike object at X and Y coordinates with the provided collision property and an ImageView provided by the fileName image.
     * @param fileName directory and name for the imageview's image property.
     * @param x X-axis coordinate.
     * @param y Y-axis coordinate.
     * @param collision collision check property.
     */
    public Spike(String fileName, double x, double y, boolean collision) {
        super(fileName, x, y, collision);
        hitBox.setHeight(hitBox.getHeight() / 2);
        hitBox.setWidth(56);
        hitBox.setX(x + (64 - 56) / 2.0);
        hitBox.setY(y + 32);
    }
}
