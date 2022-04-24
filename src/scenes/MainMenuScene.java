/*
 * Created by : Alexandros Kossyfidis
 * Group : 2G1TD3TP5
 * Date : 26/11/2021
 */

package scenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.Main;

import java.util.Objects;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class MainMenuScene extends Scene {

    /**
     * Creates a MainMenuScene for a specific root Node.
     * @param parent Scene's root node.
     */

    public MainMenuScene(Group parent) {
        super(parent, GameScene.screenWidth, GameScene.screenHeight, Color.BLACK);
        ImageView imageView = new ImageView(new Image("./images/backGrounds/Menu.jpg"));
        parent.getChildren().add(imageView);

        ComboBox<String> difficulty = new ComboBox<>();
        difficulty.getItems().addAll("Level1", "Level2", "Level3");
        difficulty.setPromptText("Select Level");
        difficulty.setMinHeight(40);
        difficulty.setMinWidth(90);
        parent.getChildren().add(difficulty);

        Image image = new Image("./images/Tiles_32x32/Free/Menu/Buttons/Play.png");
        ImageView playBtnImageView = new ImageView(image);

        Button playBtn = new Button("Play", playBtnImageView);
        playBtn.setMinWidth(120);
        playBtn.setMinHeight(40);

        parent.getChildren().add(playBtn);
        playBtn.requestFocus();
        playBtn.setLayoutX((super.getWidth() - playBtn.getWidth()) / 2 - 50);
        playBtn.setLayoutY(420);

        difficulty.setLayoutX(playBtn.getLayoutX());
        difficulty.setLayoutY(150);

        playBtn.setOnKeyPressed((KeyEvent event) -> {
            //System.out.println(event.getCode());
            if (Objects.equals(event.getCode().toString(), "ENTER")) {
                Main.actualLevel = difficulty.toString();
            }
        });

        playBtn.setOnMouseClicked((MouseEvent event) -> {
            Main.actualLevel = "Level1";
        });
    }
}
