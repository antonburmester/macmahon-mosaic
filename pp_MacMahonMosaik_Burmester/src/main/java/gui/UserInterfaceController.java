package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.CustomException;
import logic.Game;

/**
 * Main class for the user interface.
 *
 * @author mjo, cei, Anton Burmester
 */
public class UserInterfaceController {

    private Game game;

    private JavaFXGUI gui;

    @FXML
    private VBox editorControls;
    @FXML
    private TextField userHeightInput;
    @FXML
    private TextField userWidthInput;

    @FXML
    private GridPane gridPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Pane centerPane;

    /**
     * Initialisierung des Programms
     */
    @FXML
    public void initialize() {
        this.gui = new JavaFXGUI(this.borderPane, this.centerPane, this.gridPane);
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein neues Spiel zu starten
     */
    public void restartGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein bestehendes Spiel zu laden
     */
    public void loadGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird das aktuelle Spiel zu speichern
     */
    public void saveGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein bestehendes Spiel zu beenden
     */
    public void endGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um den Editor Mode einzuschalten
     */
    public void toggleEditorMode(){
        gridPane.setGridLinesVisible(true);
        boolean isEditorMode = !editorControls.isManaged(); //wenn sichtbar dann unsichtbar und umgekehrt (toggle)
        editorControls.setVisible(isEditorMode); //macht die Spielfeldeingaben (Breite,Hoehe,Button) sichtbar/unsichtbar
        editorControls.setManaged(isEditorMode); // Entfernt den Platz, wenn unsichtbar und nimmt ihn ein wenn sichtbar
    }

    /**
     * Methode welche die Breite und Hoehe durch die Nutzereingaben einließt
     */
    public void applyEditorChanges(){
        int heigth = Integer.parseInt(this.userHeightInput.getText());
        int width = Integer.parseInt(this.userWidthInput.getText());
        if(heigth >= 2 && width >= 2 && heigth <= 6 && width <= 6) {
            this.game = new Game(this.gui, Integer.parseInt(this.userHeightInput.getText()),
                    Integer.parseInt(this.userWidthInput.getText()));
        } else {
            ErrorHandler.showError(new CustomException(CustomException.ERROR_INVALID_GAME_SIZE));
        }
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um zu prüfen, ob das bestehende Feld im aktuellen Zustand geloest
     * werden kann
     */
    public void checkSolvability(){

    }
}