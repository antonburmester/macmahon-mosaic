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
public class ErrorMessageHandler {
    /**
     * Methode welche der CustomException eine Beschreibung zuordnet und diese in einem Fenster dem Nutzer anzeigt
     * @param exception die gewuenschte Fehlermeldung
     */
    public static void showError(CustomException exception) {
        String message = switch (exception.getErrorOrMessageCode()) { //Text der Exception bekommen
            case CustomException.MESSAGE_WIN -> "Mitteilung: " + exception.getErrorOrMessageCode() +
                    " Das Spiel wurde erfolgreich geloest und ist somit beendet!";
            case CustomException.MESSAGE_GAMEFIELD_SOLVABLE -> "Mitteilung: " + exception.getErrorOrMessageCode() +
                    " Das Spielfeld ist loesbar!";
            case CustomException.MESSAGE_GAMEFIELD_NOT_SOLVABLE -> "Mitteilung: " + exception.getErrorOrMessageCode() +
                    " Das Spielfeld ist nicht loesbar!";
            case CustomException.MESSAGE_NO_HINT_GAMEFIELD_NOT_SOLVABLE -> "Mitteilung: " +
                    exception.getErrorOrMessageCode() +
                    " Kann keinen naechsten Spielstein als Hilfe legen, da Spielfeld nicht loesbar!";
            case CustomException.MESSAGE_MORE_THAN_18_FREE_FIELDS_SOLVABLE_NOT_CHECKED -> "Mitteilung: " +
                    exception.getErrorOrMessageCode() +
                    " Da mehr als 18 Felder noch frei sind, wird die Loesbarkeit nicht geprueft!";

            case CustomException.ERROR_WINDOW_OPEN ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Fenster konnte nicht geladen/ geoeffnet werden!"
                    ;
            case CustomException.ERROR_INVALID_FILE ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Keine gueltige Datei ausgewaehlt!";
            case CustomException.ERROR_FILE_READ_FAILED ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Datei konnte nicht gelesen werden!";
            case CustomException.ERROR_INVALID_JSON_STRUCTURE ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Der Inhalt der Datei entspricht nicht der " +
                            "notwendigen Json Struktur!";
            case CustomException.ERROR_JSON_EMPTY ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Der Inhalt der .json Datei ist leer";
            case CustomException.ERROR_INVALID_TILENAMES ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Mindestens ein Feld der Datei entspricht " +
                            "nicht den Spielstein Namen!";
            case CustomException.ERROR_INVALID_TILENAMES_BORDER ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Mindestens ein Stein auf dem Rand der kein " +
                            "Randstein ist!";
            case CustomException.ERROR_INVALID_TILENAMES_EDGE ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Mindestens eine Ecke heisst nicht NNNN!";
            case CustomException.ERROR_MIDDLEGAMEFIELD_TILE_TOO_OFTEN ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Mindestens ein mittlerer Spielstein liegt " +
                            "doppelt!";
            case CustomException.ERROR_MIDDLEGAMEFIELD_HOLE ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Falsche Anzahl an Loechern im Spielfeld!";
            case CustomException.ERROR_INVALID_JSON_GAME_SIZE ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Falsche JSON Spielfeldgroeße! 4x4 - 8x8 " +
                            "(inklusive Raendern)";
            case CustomException.ERROR_INVALID_JSON_WRONG_FIELD_TYPE ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : JSON field Attribut vorhanden aber nicht vom " +
                            "Typ Array!";
            case CustomException.ERROR_INVALID_JSON_NO_OR_WRONG_NAMED_FIELD ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : JSON field Attribut nicht vorhanden!";
            case CustomException.ERROR_INVALID_GAME_SIZE ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Falsche Spielfeldgroeße! 2x2 - 6x6";
            case CustomException.ERROR_NO_GAME_OPEN ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Kein Spiel aktiv! Erstelle oder Lade ein Spiel."
                    ;
            case CustomException.ERROR_BORDER_NOT_SETTED ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Mindestens ein Rand Feld ist nicht gefaerbt.";
            case CustomException.ERROR_EDITOR_MODE_ON ->
                    "Fehler " + exception.getErrorOrMessageCode() + " : Editor muss ausgeblendet werden.";

            default -> "Unbekannte Fehler/ Meldung: " + exception.getErrorOrMessageCode();
        };
        //Mit dem Text ein Fehler Fenster aufrufen
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ErrorMessageHandler.class.getResource(
                    "ErrorMessageScreen.fxml")); //eine FXML
            // Instanz laden
            Parent messageBildschirmInhalt = fxmlLoader.load(); //den Inhalt der FXML Instanz laden

            Stage errorMessageScreen = new Stage(); //neue Stage fuer den kleinen Screen
            errorMessageScreen.setTitle("Mitteilung Bildschirm");
            errorMessageScreen.setMinWidth(530);
            errorMessageScreen.setMinHeight(350);
            errorMessageScreen.setScene(new Scene(messageBildschirmInhalt)); //Inhalt der Stage setzen
            errorMessageScreen.initModality(Modality.WINDOW_MODAL); //kontext zum hintergrund
            ErrorMessageController controller = fxmlLoader.getController(); //controller der Spielerauswahl
            controller.initialize(message);
            errorMessageScreen.showAndWait(); //fenster anzeigen und warten bis es geschlossen wurde
        } catch (Exception e) {
            ErrorMessageHandler.showError(new CustomException(CustomException.ERROR_WINDOW_OPEN));
        }
    }
}
