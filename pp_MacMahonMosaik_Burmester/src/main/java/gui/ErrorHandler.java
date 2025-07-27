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
                    "Fehler " + exception.getErrorCode() + " : Keine gueltige Datei ausgewaehlt!";
            case CustomException.ERROR_FILE_READ_FAILED ->
                    "Fehler " + exception.getErrorCode() + " : Datei konnte nicht gelesen werden!";
            case CustomException.ERROR_INVALID_JSON_STRUCTURE ->
                    "Fehler " + exception.getErrorCode() + " : Der Inhalt der Datei entspricht nicht der notwendigen " +
                            "Json Struktur!";
            case CustomException.ERROR_INVALID_TILENAMES ->
                    "Fehler " + exception.getErrorCode() + " : Mindestens ein Feld der Datei entspricht nicht den " +
                            "Spielstein Namen!";
            case CustomException.ERROR_INVALID_TILENAMES_BORDER ->
                    "Fehler " + exception.getErrorCode() + " : Mindestens ein Stein auf dem Rand der kein Randstein " +
                            "ist!";
            case CustomException.ERROR_INVALID_TILENAMES_EDGE ->
                    "Fehler " + exception.getErrorCode() + " : Mindestens eine Ecke heisst nicht NNNN!";
            case CustomException.ERROR_MIDDLEGAMEFIELD_TILE_TOO_OFTEN ->
                    "Fehler " + exception.getErrorCode() + " : Mindestens ein mittlerer Spielstein liegt doppelt!";
            case CustomException.ERROR_MIDDLEGAMEFIELD_HOLE ->
                    "Fehler " + exception.getErrorCode() + " : Falsche Anzahl an Loechern im Spielfeld!";
            case CustomException.ERROR_INVALID_JSON_GAME_SIZE ->
                    "Fehler " + exception.getErrorCode() + " : Falsche JSON Spielfeldgroeße! 4x4 - 8x8 " +
                            "(inklusive Raendern)";
            case CustomException.ERROR_INVALID_JSON_WRONG_FIELD_TYPE ->
                    "Fehler " + exception.getErrorCode() + " : JSON field Attribut vorhanden aber nicht vom Typ Array!";
            case CustomException.ERROR_INVALID_JSON_NO_FIELD ->
                    "Fehler " + exception.getErrorCode() + " : JSON field Attribut nicht vorhanden!";
            case CustomException.ERROR_INVALID_GAME_SIZE ->
                    "Fehler " + exception.getErrorCode() + " : Falsche Spielfeldgroeße! 2x2 - 6x6";
            case CustomException.ERROR_NO_GAME_OPEN ->
                    "Fehler " + exception.getErrorCode() + " : Kein Spiel aktiv! Erstelle oder Lade ein Spiel.";
            case CustomException.ERROR_BORDER_NOT_SETTED ->
                    "Fehler " + exception.getErrorCode() + " : Mindestens ein Rand Feld ist nicht gefaerbt.";
            case CustomException.ERROR_EDITOR_MODE_ON ->
                    "Fehler " + exception.getErrorCode() + " : Editor muss ausgeblendet werden.";
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
