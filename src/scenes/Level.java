package scenes;

import javafx.scene.Group;
import utilities.XmlReader;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Level extends GameScene {

    /**
     * Type of GameScene to load. This property is used by the XmlReader class.
     * @see XmlReader#XmlReader(String fileName, String type)
     */
    private static final String type = "level";
    /**
     * Level's 1st Room property.
     * @see Room
     */
    public Room Room1;
    /**
     * Level's 2nd Room property.
     * @see Room
     */
    public Room Room2;
    /**
     * Level's 3rd Room property.
     * @see Room
     */
    public Room Room3;

    /**
     * Creates a Level from a specific file name.
     * @param actualLevel the .xml file to read.
     */
    public Level(String actualLevel) {
        super(new Group(), screenWidth, screenHeight, actualLevel, type);
    }

    /**
     * This method uses the XmlReader to get the file directories for the three Rooms of each level.
     * If another parameter than <i>Room1</i>, <i>Room2</i> or <i>Room3</i> is provided, an IllegalStateException will be thrown.
     * @param roomNumber The Room's number you try to access.
     * @return the Room property value based on the roomNumber parameter.
     */
    public Room accessRoom(String roomNumber) {
        Room room;
        switch (roomNumber) {
            case "Room1" -> {
                if (Room1 == null) {
                    Room1 = new Room(reader.getRoomFile("Room1"));
                }
                room = Room1;
            }
            case "Room2" -> {
                if (Room2 == null) {
                    Room2 = new Room(reader.getRoomFile("Room2"));
                }
                room = Room2;
            }
            case "Room3" -> {
                if (Room3 == null) {
                    Room3 = new Room(reader.getRoomFile("Room3"));
                }
                room = Room3;
            }


            default -> throw new IllegalStateException("Unexpected value: " + roomNumber);
        }
        return room;
    }
}
