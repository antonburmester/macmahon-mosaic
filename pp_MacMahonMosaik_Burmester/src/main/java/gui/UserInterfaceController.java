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
    private Spinner<Integer> userHeightInput;
    @FXML
    private Spinner<Integer> userWidthInput;

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
        //BorderPane.setMargin(rightGridPane, new Insets(0, 0, 0, 10)); // Abstand links vom right-Bereich (10px) TODO abstand zwischen mittleren Spielfeld und rechtem Spielfeld
        this.userHeightInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 6, 2));
        this.userWidthInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 6, 2));
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
        //if(!this.userWidthInput.getText().isEmpty() && !this.userHeightInput.getText().isEmpty()) {
            int heigth = this.userHeightInput.getValue();
            int width = this.userWidthInput.getValue();
            if (heigth >= 2 && width >= 2 && heigth <= 6 && width <= 6) {
                this.game = new Game(this.gui, heigth, width);
            } else {
                ErrorHandler.showError(new CustomException(CustomException.ERROR_INVALID_GAME_SIZE));
            }
        //}
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um zu pruefen, ob das bestehende Feld im aktuellen Zustand geloest
     * werden kann
     */
    public void checkSolvability(){

    }
}