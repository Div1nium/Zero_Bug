package utilities;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class KeyHandler implements EventHandler<KeyEvent> {
    /**
     * ArrayList that contains the pressed keys' KeyCodes.
     * @see KeyCode
     */
    public ArrayList<KeyCode> keysPressed = new ArrayList<>();

    /**
     * Performs the adequate action based on the input key events.If a key is pressed, its KeyCode is added to keysPressed ArrayList otherwise it is removed.
     * Note : Each key is only added once to the keysPressed ArrayList.
     * @param keyEvent occurred key event.
     * @see KeyEvent
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED) && !keysPressed.contains(keyEvent.getCode()))
            keysPressed.add(keyEvent.getCode());
        else if (keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED)) keysPressed.remove(keyEvent.getCode());
    }
}
