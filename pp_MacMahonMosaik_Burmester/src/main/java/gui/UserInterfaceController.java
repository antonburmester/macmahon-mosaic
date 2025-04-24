package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

    @FXML
    private GridPane rightGridPane;

    /**
     * Initialisierung des Programms
     */
    @FXML
    public void initialize() {
        System.out.println(this.game == null);
        this.gui = new JavaFXGUI(this.borderPane, this.centerPane, this.gridPane, this.rightGridPane);
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
        if(this.game != null)
            this.game.setEditorMode(isEditorMode);
    }

    /**
     * Methode welche die Breite und Hoehe durch die Nutzereingaben einließt
     */
    public void applyEditorChanges(){
        //TODO sicherstellen dass beide eingabefelder eine Eingabe haben
        if(!this.userWidthInput.getText().isEmpty() && !this.userHeightInput.getText().isEmpty()) {
            int heigth = Integer.parseInt(this.userHeightInput.getText());
            int width = Integer.parseInt(this.userWidthInput.getText());
            if (heigth >= 2 && width >= 2 && heigth <= 6 && width <= 6) {
                //this.gui = new JavaFXGUI(this.borderPane, this.centerPane, this.gridPane, this.rightGridPane);
                this.game = new Game(this.gui, Integer.parseInt(this.userHeightInput.getText()),
                        Integer.parseInt(this.userWidthInput.getText()));
            } else {
                ErrorHandler.showError(new CustomException(CustomException.ERROR_INVALID_GAME_SIZE));
            }
        }
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um zu prüfen, ob das bestehende Feld im aktuellen Zustand geloest
     * werden kann
     */
    public void checkSolvability(){

    }
}