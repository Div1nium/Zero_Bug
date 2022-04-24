package manager;

import javafx.scene.Group;
import scenes.GameScene;
import tiles.*;
import utilities.Camera;
import utilities.XmlReader;

import java.util.ArrayList;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class TileManager {
    /**
     * GameScene in which the TileManager belongs.
     */
    private final GameScene gameScene;

    /**
     * List of all the Tile objects loaded in the GameScene by the XmlReader.
     * @see Tile
     * @see GameScene
     * @see XmlReader
     */
    private final ArrayList<Tile> tiles = new ArrayList<>();
    /**
     * List of all the Platform objects loaded in the GameScene by the XmlReader.
     * @see Platform
     * @see GameScene
     * @see XmlReader
     */
    private ArrayList<Platform> platforms;
    /**
     * List of all the Coin objects loaded in the GameScene by the XmlReader.
     * @see Coin
     * @see GameScene
     * @see XmlReader
     */
    private ArrayList<Coin> coins;
    /**
     * List of all the Door objects loaded in the GameScene by the XmlReader.
     * @see Door
     * @see GameScene
     * @see XmlReader
     */
    private ArrayList<Door> doors;
    /**
     * List of all the Spike objects loaded in the GameScene by the XmlReader.
     * @see Spike
     * @see GameScene
     * @see XmlReader
     */
    private ArrayList<Spike> spikes;
    /**
     * List of all the Npc objects loaded in the GameScene by the XmlReader.
     * @see Npc
     * @see GameScene
     * @see XmlReader
     */
    private ArrayList<Npc> npc;

    /**
     * Creates a TileManager that loads and adds the tiles in the current GameScene.
     * Also, the TileManager runs all the updates of the Tiles present in each Level/Room.
     * @param gameScene the GameScene this object belongs to and where the Tile objects are displayed.
     * @see #loadLevel()
     * @see #addTilesOnScene()
     * @see #render(long time)
     */
    public TileManager(GameScene gameScene) {
        this.gameScene = gameScene;
        loadLevel();
        addTilesOnScene();
    }

    /**
     * Loads all the Tile objects from the XmlReader.
     * @see XmlReader
     */
    private void loadLevel() {
        platforms = gameScene.reader.getPlatformElements();
        coins = gameScene.reader.getCoinElements();
        doors = gameScene.reader.getDoorElements();
        spikes = gameScene.reader.getSpikeElements();
        npc = gameScene.reader.getNpcElements();

        tiles.addAll(npc);
        tiles.addAll(coins);
        tiles.addAll(doors);
        tiles.addAll(spikes);
        tiles.addAll(platforms);
    }

    /**
     * Adds all the Tile objects' ImageView (referenced in the tiles ArrayList) in the GameScene.
     * @see GameScene
     */
    private void addTilesOnScene() {
        for(Tile tile : tiles)
            ((Group) gameScene.getRoot()).getChildren().add(tile.getImageView());
    }

    /**
     * Renders the Tile's display based on the GameScene's camera reference.
     * If the Platforms are set up to blink, this method will set them visible/invisible periodically based on the time parameter.
     * @param time the camera to use as a display reference.
     * @see GameScene
     * @see Camera
     * @see Platform
     */
    public void render(long time) {
        for (Tile tile : tiles) {
            double x = tile.getWorldX();
            double y = tile.getWorldY();

            tile.getImageView().setX(x - gameScene.getSceneCamera().getWorldX());
            tile.getImageView().setY(y - gameScene.getSceneCamera().getWorldY());

            tile.getHitBox().setX(tile.getImageView().getX());
            tile.getHitBox().setY(tile.getImageView().getY());
        }

        for (Platform platform : platforms) {
            platform.getImageView().setVisible((time / 1000000) % 1000 <= 250 || !gameScene.reader.getBlink());
        }
    }

    /**
     * @return the list on Platform objects.
     */
    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    /**
     * @return the list on Coin objects.
     */
    public ArrayList<Coin> getCoins() {
        return coins;
    }

    /**
     * @return the list on Door objects.
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /**
     * @return the list on Spike objects.
     */
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    /**
     * @return the list on Npc objects.
     */
    public ArrayList<Npc> getNpc() {
        return npc;
    }


}
