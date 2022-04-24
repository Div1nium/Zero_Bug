package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import scenes.Level;
import scenes.MainMenuScene;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Main extends Application {

    /**
     * Refers to the actual level of the game. This parameter is used for the Level management in the game.
     */
    public static String actualLevel = "MainMenu";
    /**
     * Refers to the actual room inside each level. This parameter is used for the Room management in the game.
     * Note that when this parameter has a default value, we consider that we are on the main level scene.
     * Otherwise, we consider that we entered inside a room of the level.
     */
    public static String actualRoom = "default";

    /**
     * MainMenu's scene reference.
     * @see MainMenuScene
     */
    private MainMenuScene mainMenu;
    /**
     * Level1's scene property.
     * @see Level
     */
    private Level Level1;
    /**
     * Level2's scene property.
     * @see Level
     */
    private Level Level2;

    public static void main(String[] args) {
        launch();
    }

    /**
     * Updates the Scene to be shown on the Screen by modifying the Stage's scene value based on the actualLevel and actualRoom parameters.
     * @see #actualLevel
     * @see #actualRoom
     * @param stage that refers to your screen.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Platformer 2D");
        stage.setResizable(false);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (actualLevel) {
                    case "Level1" -> {
                        if (Level1 == null) {
                            Level1 = new Level(actualLevel);
                            Level1.playSoundInf("music/Sound.wav");
                            mainMenu = null;
                        }
                        switch (actualRoom) {
                            case "Room1" -> stage.setScene(Level1.accessRoom("Room1"));
                            case "Room2" -> stage.setScene(Level1.accessRoom("Room2"));
                            case "Room3" -> stage.setScene(Level1.accessRoom("Room3"));

                            default -> {
                                if (stage.getScene() != Level1)
                                    stage.setScene(Level1);


                            }
                        }
                    }
                    case "Level2" -> {
                        if (Level2 == null) {
                            Level2 = new Level(actualLevel);
                            Level2.playSoundInf("music/Sound.wav");
                            Level1 = null;
                        }
                        switch (actualRoom) {
                            case "Room1" -> stage.setScene(Level2.accessRoom("Room1"));
                            case "Room2" -> stage.setScene(Level2.accessRoom("Room2"));
                            case "Room3" -> stage.setScene(Level2.accessRoom("Room3"));
                            default -> stage.setScene(Level2);
                        }
                    }
                    default -> {
                        if (mainMenu == null) {
                            mainMenu = new MainMenuScene(new Group());
                            stage.setScene(mainMenu);
                        }
                    }
                }
            }
        };
        timer.start();

        stage.show();
    }

}
