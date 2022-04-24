package scenes;

import javafx.scene.Group;
import utilities.XmlReader;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Room extends GameScene {

    /**
     * Type of GameScene to load. This property is used by the XmlReader class.
     * @see XmlReader#XmlReader(String fileName, String type)
     */
    private static final String type = "room";

    /**
     * Creates a Room from a specific file name.
     * @param roomName the .xml file to read.
     */
    public Room(String roomName) {
        super(new Group(), screenWidth, screenHeight, roomName, type);
    }
}
