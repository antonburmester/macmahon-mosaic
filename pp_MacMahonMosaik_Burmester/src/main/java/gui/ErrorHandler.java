package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.CustomException;

/**
 * Klasse welche die Error Texte enthaelt und diese in einem Fenster anzeigt
 *
 * @author Anton Burmester
 */
public class ErrorHandler {
    /**
     * Methode welche der CustomException eine Beschreibung zuordnet und diese in einem Fenster dem Nutzer anzeigt
     * @param exception die gewuenschte Fehlermeldung
     */
    public static void showError(CustomException exception) {
        String message = switch (exception.getErrorCode()) { //Text der Exception bekommen
            case CustomException.ERROR_WINDOW_OPEN ->
                    "Fehler " + exception.getErrorCode() + " : Fenster konnte nicht geladen/ geoeffnet werden!";
            case CustomException.ERROR_INVALID_FILE ->
                    "Fehler " + exception.getErrorCode() + " : Keine gültige Datei ausgewählt!";
            case CustomException.ERROR_INVALID_GAME_SIZE ->
                    "Fehler " + exception.getErrorCode() + " : Falsche Spielfeldgroeße! 2x2 - 6x6";
            case CustomException.NO_GAME_OPEN ->
                    "Fehler " + exception.getErrorCode() + " : Kein Spiel aktiv! Erstelle oder Lade ein Spiel.";
            default -> "Unbekannter Fehler: " + exception.getErrorCode();
        };
        //Mit dem Text ein Fehler Fenster aufrufen
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ErrorHandler.class.getResource("ErrorMessageScreen.fxml")); //eine FXML
            // Instanz laden
            Parent messageBildschirmInhalt = fxmlLoader.load(); //den Inhalt der FXML Instanz laden

            Stage errorMessageScreen = new Stage(); //neue Stage fuer den kleinen Screen
            errorMessageScreen.setTitle("Fehler Bildschirm");
            errorMessageScreen.setMinWidth(530);
            errorMessageScreen.setMinHeight(350);
            errorMessageScreen.setScene(new Scene(messageBildschirmInhalt)); //Inhalt der Stage setzen
            errorMessageScreen.initModality(Modality.WINDOW_MODAL); //kontext zum hintergrund
            ErrorMessageController controller = fxmlLoader.getController(); //controller der Spielerauswahl
            controller.initialize(message);
            errorMessageScreen.showAndWait(); //fenster anzeigen und warten bis es geschlossen wurde
        } catch (Exception e) {
            ErrorHandler.showError(new CustomException(CustomException.ERROR_WINDOW_OPEN));
        }
    }
}
