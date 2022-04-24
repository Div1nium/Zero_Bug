package utilities;

import entities.Hero;
import manager.TileManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import scenes.GameScene;
import scenes.Level;
import scenes.Room;
import tiles.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.signum;
import static java.lang.Math.abs;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class XmlReader {

    /**
     * List of all Platform objects.
     * @see Platform
     */
    private final ArrayList<Platform> platformElements = new ArrayList<>();

    /**
     * List of all Coin objects.
     * @see Coin
     */
    private final ArrayList<Coin> coinElements = new ArrayList<>();

    /**
     * List of all Door objects.
     * @see Door
     */
    private final ArrayList<Door> doorElements = new ArrayList<>();

    /**
     * List of all Spike objects.
     * @see Spike
     */
    private final ArrayList<Spike> spikeElements = new ArrayList<>();

    /**
     * List of all Npc objects.
     * @see Npc
     */
    private final ArrayList<Npc> npcElements = new ArrayList<>();

    /**
     * List of .xml files and directories for the creation af a Level's rooms.
     * Note : If this class is used for the creation of a Room object, this ArrayList will be empty.
     * @see Level
     */
    private final ArrayList<String> roomFiles = new ArrayList<>();

    /**
     * X-axis Hero's initial coordinate property.
     * @see Hero
     */
    private double startPosX;

    /**
     * Y-axis Hero's initial coordinate property.
     * @see Hero
     */
    private double startPosY;

    /**
     * Hero's movement speed property inside the Level/Room.
     * @see Hero
     */
    private int speed;

    /**
     * Hero's Y-axis gravity property inside the Level/Room.
     * @see Hero
     */
    private double gravity;

    /**
     * GameScene's rotation angle property.
     * @see GameScene
     */
    private int rotation;

    /**
     * Hero's keyOrder property.
     * @see Hero
     */
    private int keyOrder;

    /**
     * BackGround's image's directory and fileName.
     * @see Background
     */
    private String backGroundFileName;

    /**
     * BackGround's X-axis coordinate.
     * @see Background
     */
    private double backGroundX;

    /**
     * BackGround's Y-axis coordinate.
     * @see Background
     */
    private double backGroundY;

    /**
     * BackGround's width property value.
     * @see Background
     */
    private double backGroundWidth;

    /**
     * BackGround's height property value.
     * @see Background
     */
    private double backGroundHeight;

    /**
     * Platform's blink property value.
     * @see TileManager#render(long time)
     */
    private boolean blink;

    /**
     * Creates a XmlReader object that fills in this class' properties values based on the type of GameScene you try to create.
     * Those properties are used at the creation of each GameScene.
     * @param FILENAME Directory and name of the .xml file that contains the data.
     * @param type Type of GameScene you try to create.
     * @see Level
     * @see Room
     */
    public XmlReader(String FILENAME, String type) {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));

            // optional, but recommended
            doc.getDocumentElement().normalize();
            //System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            //System.out.println("------");
            System.out.println("Actual File :\t" + FILENAME);

            // get <staff>

            NodeList level = doc.getElementsByTagName(type);

            for (int temp = 0; temp < level.getLength(); temp++) {

                Node node = level.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    // get level's attribute
                    String id = element.getAttribute("id");

                    if (type.equals("level")) {
                        String files = doc.getElementsByTagName("roomFiles").item(0).getTextContent();
                        String[] roomFiles;
                        roomFiles = files.split(",");

                        this.roomFiles.addAll(Arrays.asList(roomFiles));
                    }

                    startPosX = Double.parseDouble(doc.getElementsByTagName("startPositionX").item(0).getTextContent());

                    startPosY = Double.parseDouble(doc.getElementsByTagName("startPositionY").item(0).getTextContent());

                    speed = parseInt(doc.getElementsByTagName("startVelocity").item(0).getTextContent());

                    gravity = parseInt(doc.getElementsByTagName("gravity").item(0).getTextContent());

                    keyOrder = parseInt(doc.getElementsByTagName("keyOrder").item(0).getTextContent());

                    rotation = parseInt(doc.getElementsByTagName("rotation").item(0).getTextContent());

                    backGroundFileName = doc.getElementsByTagName("image").item(0).getTextContent();
                    backGroundX = Double.parseDouble(doc.getElementsByTagName("imageX").item(0).getTextContent());
                    backGroundY = Double.parseDouble(doc.getElementsByTagName("imageY").item(0).getTextContent());

                    backGroundHeight = Double.parseDouble(doc.getElementsByTagName("imageH").item(0).getTextContent());
                    backGroundWidth = Double.parseDouble(doc.getElementsByTagName("imageW").item(0).getTextContent());


                    blink = Boolean.parseBoolean(doc.getElementsByTagName("blink").item(0).getTextContent());

                    // get text
                    //System.out.println("Current Element :" + node.getNodeName());
                    //System.out.println("Level Id : " + id);


                    NodeList platforms = doc.getElementsByTagName("platform");

                    for (int platformNumber = 0; platformNumber < platforms.getLength(); platformNumber++) {

                        Node platformAttributes = platforms.item(platformNumber);

                        if (platformAttributes.getNodeType() == Node.ELEMENT_NODE) {
                            Element attributes = (Element) platformAttributes;
                            // get tile's coordinates (x,y)
                            String coordinates = attributes.getElementsByTagName("coordinates").item(0).getTextContent();
                            // get text
                            String[] coordinate;
                            coordinate = coordinates.split(",");
                            //System.out.println("Current Element :" + platformAttributes.getNodeName());
                            //System.out.println("platform X: " + coordinate[0] + "\t platform Y: " + coordinate[1]);

                            String sprite = attributes.getElementsByTagName("sprite").item(0).getTextContent();
                            //System.out.println("Sprite: " + sprite);

                            boolean collision = Boolean.parseBoolean(attributes.getElementsByTagName("collision").item(0).getTextContent());
                            //System.out.println("Collision: " + collision);

                            int numberOfXTiles = parseInt(attributes.getElementsByTagName("numberX").item(0).getTextContent());
                            //System.out.println("Number of X Tiles : " + numberOfXTiles);

                            int numberOfYTiles = parseInt(attributes.getElementsByTagName("numberY").item(0).getTextContent());
                            //System.out.println("Number of Y Platforms : " + numberOfYTiles);
                            for (int i = 0; i < abs(numberOfXTiles); i++) {
                                for (int j = 0; j < abs(numberOfYTiles); j++) {
                                    platformElements.add(new Platform("/images/Tiles_256x256/" + sprite, parseInt(coordinate[0]) + signum(numberOfXTiles) * i * GameScene.tileSize, parseInt(coordinate[1]) + signum(numberOfYTiles) * j * GameScene.tileSize, collision));
                                }
                            }


                        }
                    }

                    NodeList coins = doc.getElementsByTagName("coin");

                    for (int coinNumber = 0; coinNumber < coins.getLength(); coinNumber++) {

                        Node coinAttributes = coins.item(coinNumber);

                        if (coinAttributes.getNodeType() == Node.ELEMENT_NODE) {
                            Element attributes = (Element) coinAttributes;
                            // get tile's coordinates (x,y)
                            String coordinates = attributes.getElementsByTagName("coordinates").item(0).getTextContent();
                            // get text
                            String[] coordinate;
                            coordinate = coordinates.split(",");
                            //System.out.println("Current Element :" + tileAttributes.getNodeName());
                            //System.out.println("platform X: " + coordinate[0] + "\t platform Y: " + coordinate[1]);

                            String sprite = attributes.getElementsByTagName("sprite").item(0).getTextContent();
                            //System.out.println("Sprite: " + sprite);

                            boolean collision = Boolean.parseBoolean(attributes.getElementsByTagName("collision").item(0).getTextContent());
                            //System.out.println("Collision: " + collision);

                            int numberOfXTiles = parseInt(attributes.getElementsByTagName("numberX").item(0).getTextContent());
                            //System.out.println("Number of X Tiles : " + numberOfXTiles);

                            int numberOfYTiles = parseInt(attributes.getElementsByTagName("numberY").item(0).getTextContent());
                            //System.out.println("Number of Y Platforms : " + numberOfYTiles);
                            for (int i = 0; i < abs(numberOfXTiles); i++) {
                                for (int j = 0; j < abs(numberOfYTiles); j++) {
                                    coinElements.add(new Coin("/images/Tiles_256x256/" + sprite, parseInt(coordinate[0]) + signum(numberOfXTiles) * i * GameScene.tileSize, parseInt(coordinate[1]) + signum(numberOfYTiles) * j * GameScene.tileSize, collision));
                                }
                            }


                        }
                    }

                    NodeList npc = doc.getElementsByTagName("npc");

                    for (int npcNumber = 0; npcNumber < npc.getLength(); npcNumber++) {

                        Node npcAttributes = npc.item(npcNumber);

                        if (npcAttributes.getNodeType() == Node.ELEMENT_NODE) {
                            Element attributes = (Element) npcAttributes;
                            // get tile's coordinates (x,y)
                            String coordinates = attributes.getElementsByTagName("coordinates").item(0).getTextContent();
                            // get text
                            String[] coordinate;
                            coordinate = coordinates.split(",");
                            //System.out.println("Current Element :" + tileAttributes.getNodeName());
                            //System.out.println("platform X: " + coordinate[0] + "\t platform Y: " + coordinate[1]);

                            String sprite = attributes.getElementsByTagName("sprite").item(0).getTextContent();
                            //System.out.println("Sprite: " + sprite);

                            boolean collision = Boolean.parseBoolean(attributes.getElementsByTagName("collision").item(0).getTextContent());
                            //System.out.println("Collision: " + collision);

                            int numberOfXTiles = parseInt(attributes.getElementsByTagName("numberX").item(0).getTextContent());
                            //System.out.println("Number of X Tiles : " + numberOfXTiles);

                            int numberOfYTiles = parseInt(attributes.getElementsByTagName("numberY").item(0).getTextContent());
                            //System.out.println("Number of Y Platforms : " + numberOfYTiles);
                            for (int i = 0; i < abs(numberOfXTiles); i++) {
                                for (int j = 0; j < abs(numberOfYTiles); j++) {
                                    npcElements.add(new Npc("/images/Tiles_256x256/" + sprite, parseInt(coordinate[0]) + signum(numberOfXTiles) * i * GameScene.tileSize, parseInt(coordinate[1]) + signum(numberOfYTiles) * j * GameScene.tileSize, collision));
                                }
                            }


                        }
                    }

                    NodeList doors = doc.getElementsByTagName("door");

                    for (int doorNumber = 0; doorNumber < doors.getLength(); doorNumber++) {

                        Node doorAttributes = doors.item(doorNumber);

                        if (doorAttributes.getNodeType() == Node.ELEMENT_NODE) {
                            Element attributes = (Element) doorAttributes;
                            // get tile's coordinates (x,y)
                            String coordinates = attributes.getElementsByTagName("coordinates").item(0).getTextContent();
                            // get text
                            String[] coordinate;
                            coordinate = coordinates.split(",");
                            //System.out.println("Current Element :" + tileAttributes.getNodeName());
                            //System.out.println("platform X: " + coordinate[0] + "\t platform Y: " + coordinate[1]);

                            String sprite = attributes.getElementsByTagName("sprite").item(0).getTextContent();
                            //System.out.println("Sprite: " + sprite);

                            boolean collision = Boolean.parseBoolean(attributes.getElementsByTagName("collision").item(0).getTextContent());
                            //System.out.println("Collision: " + collision);

                            int numberOfXTiles = parseInt(attributes.getElementsByTagName("numberX").item(0).getTextContent());

                            //System.out.println("Number of X Tiles : " + numberOfXTiles);
                            String direction = attributes.getElementsByTagName("direction").item(0).getTextContent();
                            int numberOfYTiles = parseInt(attributes.getElementsByTagName("numberY").item(0).getTextContent());
                            //System.out.println("Number of Y Platforms : " + numberOfYTiles);
                            for (int i = 0; i < abs(numberOfXTiles); i++) {
                                for (int j = 0; j < abs(numberOfYTiles); j++) {
                                    doorElements.add(new Door("/images/Tiles_256x256/" + sprite, parseInt(coordinate[0]) + signum(numberOfXTiles) * i * GameScene.tileSize, parseInt(coordinate[1]) + signum(numberOfYTiles) * j * GameScene.tileSize, collision, direction));
                                }
                            }


                        }
                        NodeList spikes = doc.getElementsByTagName("spike");

                        for (int spikeNumber = 0; spikeNumber < spikes.getLength(); spikeNumber++) {

                            Node spikeAttributes = spikes.item(spikeNumber);

                            if (spikeAttributes.getNodeType() == Node.ELEMENT_NODE) {
                                Element attributes = (Element) spikeAttributes;
                                // get tile's coordinates (x,y)
                                String coordinates = attributes.getElementsByTagName("coordinates").item(0).getTextContent();
                                // get text
                                String[] coordinate;
                                coordinate = coordinates.split(",");
                                //System.out.println("Current Element :" + tileAttributes.getNodeName());
                                //System.out.println("platform X: " + coordinate[0] + "\t platform Y: " + coordinate[1]);

                                String sprite = attributes.getElementsByTagName("sprite").item(0).getTextContent();
                                //System.out.println("Sprite: " + sprite);

                                boolean collision = Boolean.parseBoolean(attributes.getElementsByTagName("collision").item(0).getTextContent());
                                //System.out.println("Collision: " + collision);

                                int numberOfXTiles = parseInt(attributes.getElementsByTagName("numberX").item(0).getTextContent());

                                //System.out.println("Number of X Tiles : " + numberOfXTiles);

                                int numberOfYTiles = parseInt(attributes.getElementsByTagName("numberY").item(0).getTextContent());
                                //System.out.println("Number of Y Platforms : " + numberOfYTiles);
                                for (int i = 0; i < abs(numberOfXTiles); i++) {
                                    for (int j = 0; j < abs(numberOfYTiles); j++) {
                                        spikeElements.add(new Spike("/images/Tiles_256x256/" + sprite, parseInt(coordinate[0]) + signum(numberOfXTiles) * i * GameScene.tileSize, parseInt(coordinate[1]) + signum(numberOfYTiles) * j * GameScene.tileSize, collision));
                                    }
                                }


                            }

                        }
                    }

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return backGroundX's property value.
     */
    public double getBackGroundX() {
        return backGroundX;
    }

    /**
     * @return backGroundY's property value.
     */
    public double getBackGroundY() {
        return backGroundY;
    }

    /**
     * @return backGroundWidth's property value.
     */
    public double getBackGroundWidth() {
        return backGroundWidth;
    }

    /**
     * @return backGroundHeight's property value.
     */
    public double getBackGroundHeight() {
        return backGroundHeight;
    }

    /**
     * Returns the appropriate room .xml file based on its room number.
     * Note : If the room number doesn't exist a NoSuchFile string is returned.
     * @param roomNumber The room's number that refers to the file you try to get.
     * @return the .xml file that matches to the roomNumber parameter.
     */
    public String getRoomFile(String roomNumber) {
        switch (roomNumber) {
            case "Room1" -> {
                return roomFiles.get(0);
            }
            case "Room2" -> {
                return roomFiles.get(1);
            }
            case "Room3" -> {
                return roomFiles.get(2);
            }
            default -> {
                return "NoSuchFile";
            }
        }
    }

    /**
     * @return PlatformElements' property value.
     */
    public ArrayList<Platform> getPlatformElements() {
        return platformElements;
    }

    /**
     * @return CoinElements' property value.
     */
    public ArrayList<Coin> getCoinElements() {
        return coinElements;
    }

    /**
     * @return DoorElements' property value.
     */
    public ArrayList<Door> getDoorElements() {
        return doorElements;
    }

    /**
     * @return SpikeElements' property value.
     */
    public ArrayList<Spike> getSpikeElements() {
        return spikeElements;
    }

    /**
     * @return NpcElements' property value.
     */
    public ArrayList<Npc> getNpcElements() {
        return npcElements;
    }

    /**
     * @return startPositionX's property value.
     */
    public double getStartPosX() {
        return startPosX;
    }

    /**
     * @return startPositionY's property value.
     */
    public double getStartPosY() {
        return startPosY;
    }

    /**
     * @return speed's property value.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return gravity's property value.
     */
    public double getGravity() {
        return gravity;
    }

    /**
     * @return keyOrder's property value.
     */
    public int getKeyOrder() {
        return keyOrder;
    }

    /**
     * @return backGroundFileName's property value.
     */
    public String getBackGroundFileName() {
        return backGroundFileName;
    }

    /**
     * @return rotation's property value.
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * @return blink's property value.
     */
    public boolean getBlink() {
        return blink;
    }

}



