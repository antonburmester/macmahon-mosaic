package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Klasse welche dem Spieler ein Fenster anzeigen soll welches dem Nutzer einen Text anzeigt
 * zustaendig fuer ErrorMessageScreen
 *
 * @author Anton Burmester
 */
public class ErrorMessageController {

    @FXML
    private Label txtLabel;

    @FXML
    private Button okBtn;

    /**
     * Konstruktor welcher Fenster mit Fehlertext initialisiert
     *
     * @param errMessageText Fehlertext
     */
    @FXML
    public void initialize(String errMessageText) {
        this.txtLabel.setText(errMessageText);
    }

    /**
     * wenn der yes Button geclickt wird
     */
    @FXML
    private void onYesClicked() {
        this.closeWindow();
    }

    /**
     * schließt das Fenster
     */
    private void closeWindow() {
        Stage stage = (Stage) okBtn.getScene().getWindow(); // Hole die aktuelle Stage
        stage.close(); // Schließe die Stage
    }
}
