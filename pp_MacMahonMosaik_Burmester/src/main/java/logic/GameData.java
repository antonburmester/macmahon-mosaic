package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        //Instanz der Gson Klasse mit welcher auf die Inhalte der gameFile zugegriffen wird
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson = new GsonBuilder().create();
        try (FileWriter writer = new FileWriter(targetFile)) { //laden der GameFile
            JsonObject jsonObject = new JsonObject();

            JsonArray jsonArray3DGameField = new JsonArray(); //das Array welches die Zeilenarrays beinhaltet, da GSON
            // nicht direkt 3 Dimensionale Arrays unterstuezt

            for(int y = 0; y < gameField.length; y++){ //jede Zeile
                JsonArray currRow = new JsonArray(); //leeres Array fuer neue Zeile
                for(int x = 0; x < gameField[y].length; x++){ //jede Spalte einer Zeile
                    currRow.add(gameField[x][y]); //Inhalte dem Array hinzufuegen
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
    public static void loadGame(){

    }
}
