package tiles;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import scenes.GameScene;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Npc extends Tile {

    /**
     * Current step on the dialog.
     */
    private int step = 0;
    /**
     * Show property of the dialog.
     * @True the dialog is shown when there is an Interaction with the Npc.
     * @False the dialog is not shown when there is an Interaction with the Npc.
     */
    private boolean doShow = true;

    /**
     * Creates a Npc object at X and Y coordinates with the provided collision property and an ImageView provided by the fileName image.
     * @param fileName directory and name for the imageview's image property.
     * @param x X-axis coordinate.
     * @param y Y-axis coordinate.
     * @param collision collision check property.
     */
    public Npc(String fileName, double x, double y, boolean collision) {
        super(fileName, x, y, collision);
    }

    /**
     * Displays a dialog on the scene when an interaction with the Npc occurs.
     * @param scene The scene to display the dialog.
     */
    public void displayDialog(GameScene scene) {
        if (doShow) {
            doShow = false;
            GridPane root = new GridPane();
            root.setStyle("-fx-background-image: url('./images/Tiles_32x32/Free/Background/Blue.png')");

            root.setPadding(new Insets(20));
            root.setHgap(15);
            root.setVgap(15);
            root.setAlignment(Pos.CENTER);

            root.setLayoutX(scene.getHero().getImageView().getX() + 100);
            root.setLayoutY(scene.getHero().getImageView().getY() - 100);

            Label labelTitle = new Label("I need to have the missing line of code .\nTo repair the program !");
            root.add(labelTitle, 0, 0, 2, 1);
            GridPane.setHalignment(labelTitle, HPos.CENTER);

            Label labelQuestion = new Label();
            TextField answer = new TextField();

            Button nextButton = new Button("Next");
            Button closeButton = new Button("Close");

            ((Group) scene.getRoot()).getChildren().add(root);

            GridPane.setHalignment(labelQuestion, HPos.CENTER);
            root.add(labelQuestion, 0, 1, 2, 1);

            GridPane.setHalignment(nextButton, HPos.RIGHT);
            root.add(nextButton, 1, 3);

            GridPane.setHalignment(closeButton, HPos.LEFT);
            root.add(closeButton, 0, 3);

            nextButton.setOnAction(actionEvent -> nextButtonAction(root, labelTitle, labelQuestion, answer, nextButton));
            closeButton.setOnAction(actionEvent -> closeButtonAction(scene, root));
        }
    }

    /**
     * Performs the adequate action when the NextButton of the dialog is clicked and updates its content on the screen.
     */
    private void nextButtonAction(GridPane root, Label title, Label question, TextField answer, Button nextButton) {
        switch (step) {
            case 0 -> {
                title.setText("You have to find it in the rooms.");
                question.setText("Do you have the line ?");
                GridPane.setHalignment(answer, HPos.CENTER);
                root.add(answer, 0, 2, 2, 1);

                step++;
            }
            case 1 -> {
                if (answer.getText().equals("virus.kill(true);")) {
                    root.getChildren().remove(nextButton);
                    root.getChildren().remove(question);
                    root.getChildren().remove(answer);
                    title.setTextAlignment(TextAlignment.CENTER);
                    title.setText("Oh it seems like we have someone as smart as me.\nYou probably have an Ensea Engineering Degree.\nYou earned a reward. Please take this key.");
                    step++;
                } else {
                    root.getChildren().remove(nextButton);
                    root.getChildren().remove(question);
                    root.getChildren().remove(answer);
                    title.setText("This isn't the line take a better look next time.");
                }
            }
        }
    }

    /**
     * Removes the dialog from the screen when the CloseButton is clicked.
     * If the dialog was at its final step, the latter will no longer be available, otherwise it is reset.
     */
    private void closeButtonAction(GameScene scene, GridPane root) {
        if (step < 2) {
            doShow = true;
            step = 0;
            ((Group) scene.getRoot()).getChildren().remove(root);
        } else ((Group) scene.getRoot()).getChildren().remove(root);
    }
}
