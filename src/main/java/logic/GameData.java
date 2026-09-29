package logic;

import com.google.gson.*;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Klasse welche ein Spiel zum ist Zustand in einer json Datei speichert oder aus einer json Datei laedt
 *
 * @author  Anton Burmester
 */
public class GameData {

    /**
     * Methode welche das aktuelle Spiel als json Datei speichert
     * @param gameField das Spielfeld als String nach Form der Aufgabenstellung
     * @param targetFile die Datei in welche das json Objekt geschrieben werden soll
     */
    public static void saveGame(String[][] gameField, File targetFile) throws CustomException {
        //Instanz der Gson Klasse mit welcher die targetFile geschrieben wird
        Gson gson = new GsonBuilder().create();
        try (FileWriter writer = new FileWriter(targetFile)) { //laden der GameFile
            JsonObject jsonObject = new JsonObject();

            JsonArray jsonArray3DGameField = new JsonArray(); //das Array welches die Zeilenarrays beinhaltet, da GSON
            // nicht direkt 3 Dimensionale Arrays unterstuezt

            for(int y = 0; y < gameField.length; y++){ //jede Zeile
                JsonArray currRow = new JsonArray(); //leeres Array fuer neue Zeile
                for(int x = 0; x < gameField[y].length; x++){ //jede Spalte einer Zeile
                    currRow.add(gameField[y][x]); //Inhalte dem Array hinzufuegen
                }
                jsonArray3DGameField.add(currRow); //gesamtes Zeilenarray als Instanz dem jsonArray3DGameField geben
            }

            jsonObject.add("field", jsonArray3DGameField);

            gson.toJson(jsonObject, writer); //JSON in die Datei schreiben
        } catch (IOException exception) {
                throw new CustomException(CustomException.ERROR_INVALID_FILE);
        }
    }

    /**
     * Methode welche ein bestehendes Spiel aus einer json Datei laedt
     */
    public static String[][] loadGame(File targetFile) throws CustomException {
        if (targetFile == null || !targetFile.isFile()) {
            throw new CustomException(CustomException.ERROR_INVALID_FILE);
        }
        if (targetFile.length() == 0) {
            throw new CustomException(CustomException.ERROR_JSON_EMPTY);
        }

        String[][] stringGameField;
        //Instanz der Gson Klasse mit welcher auf die Inhalte der gameFile zugegriffen wird
        Gson gson = new GsonBuilder().create();
        try (FileReader fileReader = new FileReader(targetFile)){
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            GameData.isJsonValid(jsonObject); //nur wenn Json Data Valide ist

            JsonArray fieldArray = jsonObject.getAsJsonArray("field"); //das Json Spielfeld
            int xSize = fieldArray.get(0).getAsJsonArray().size();
            int ySize = fieldArray.size();
            stringGameField = new String[ySize][xSize]; //das neue Spielfeld
            for (int y = 0; y < ySize; y++) { //jede Zeile
                JsonArray currRow = fieldArray.get(y).getAsJsonArray(); //die aktuelle Array Zeile
                for (int x = 0; x < xSize; x++) { //jede SPalte
                    stringGameField[y][x] = currRow.get(x).getAsString();
                }
            }

        } catch (FileNotFoundException e) {
        throw new CustomException(CustomException.ERROR_INVALID_FILE); //Datei fehlt
    } catch (IOException e) {
        throw new CustomException(CustomException.ERROR_FILE_READ_FAILED); //Fehler beim einlesen
    }
        return(stringGameField);
    }

    /**
     * Methode welche prueft ob das uebergebene jsonObject bezueglich der json Struktur mit der erlaubten uebereinstimmt
     * Prueft nicht den Inhalt des Arrays. Nur ob das Array vorhanden ist und die Dimensionen
     * @param jsonObject das uebergebene jsonObject
     */
    static void isJsonValid(JsonObject jsonObject) throws CustomException {
        if(!jsonObject.isEmpty()) {
            if (jsonObject.has("field")) { //Json Objekt hat ein Member namens field
                if (jsonObject.get("field").isJsonArray()) { //Member field ist vom Typ JsonArray
                    JsonArray field = jsonObject.getAsJsonArray("field");
                    //Hoehe des Spielfelds pruefen (Anzahl Zeilen)
                    if (field.size() < Game.MIN_GAMEFIELD_SIZE_WITH_BORDER ||
                            field.size() > Game.MAX_GAMEFIELD_SIZE_WITH_BORDER) {

                        throw new CustomException(CustomException.ERROR_INVALID_JSON_GAME_SIZE);
                    }
                    for (JsonElement currRowElement : field) { // jede Zeile durchlaufen
                        JsonArray currRow = currRowElement.getAsJsonArray();
                        //Breite des Spielfelds jeder Reihe pruefen (Anzahl Spalten)
                        if (currRow.size() < Game.MIN_GAMEFIELD_SIZE_WITH_BORDER ||
                                currRow.size() > Game.MAX_GAMEFIELD_SIZE_WITH_BORDER) {

                            throw new CustomException(CustomException.ERROR_INVALID_JSON_GAME_SIZE);
                        }
                    }
                } else { //kein JsonArray
                    throw new CustomException(CustomException.ERROR_INVALID_JSON_WRONG_FIELD_TYPE);
                }
            } else { //kein Feld namens field
                throw new CustomException(CustomException.ERROR_INVALID_JSON_NO_OR_WRONG_NAMED_FIELD);
            }
        } else { //ist leer
            throw new CustomException(CustomException.ERROR_JSON_EMPTY);
        }
    }

    /**
     * Methode welche das Verzeichnis der gespeicherten Spielstaende abhaengig vom Betriebssystem zurueckgibt
     * @return der Pfad zu den gespeicherten Spielstaenden
     */
    public static File getSavedGamesDirectory(){
        File initialDirectory = null;
        String betriebssystemName = System.getProperty("os.name").toLowerCase();
        if (betriebssystemName.contains("win")) { //Windows

            try { //Pfad zum UserInterfaceController finden
                initialDirectory = new File(
                        gui.UserInterfaceController.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                initialDirectory = initialDirectory.getParentFile().getParentFile(); //zwei verzeichnisse zurueck,
                // da hier das src verzeichnis liegt
                initialDirectory = new File(initialDirectory, "src/main/resources/savedGames/");
            } catch (URISyntaxException ex) {
                //oops... ¯\_(ツ)_/¯
                //guess we won't be opening the dialog in the right directory
            }

        } else if(betriebssystemName.contains("mac")) { //Mac
            initialDirectory = new File("src/main/resources/savedGames/");
        }
        return(initialDirectory);
    }


}
