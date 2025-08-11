package logic;

import com.google.gson.*;

import java.io.*;

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
        String[][] stringGameField = null;
        //Instanz der Gson Klasse mit welcher auf die Inhalte der gameFile zugegriffen wird
        Gson gson = new GsonBuilder().create();
        try (FileReader fileReader = new FileReader(targetFile)){
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            if(isJsonValid(jsonObject)){
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
            }
        } catch(IOException e){
            throw new CustomException(CustomException.ERROR_FILE_READ_FAILED);
        }
        return(stringGameField);
    }

    /**
     * Methode welche prueft ob das uebergebene jsonObject bezueglich der json Struktur mit der erlaubten uebereinstimmt
     * Prueft nicht den Inhalt des Arrays. Nur ob das Array vorhanden ist und die Dimensionen
     * @param jsonObject das uebergebene jsonObject
     * @return ob das jsonObject valide ist
     */
    private static boolean isJsonValid(JsonObject jsonObject) throws CustomException {

        if(jsonObject.has("field")) { //Json Objekt hat ein Member namens field
            if(jsonObject.get("field").isJsonArray()){ //Member field ist vom Typ JsonArray
                JsonArray field = jsonObject.getAsJsonArray("field");
                //Hoehe des Spielfelds pruefen (Anzahl Zeilen)
                if(field.size() < Game.MIN_GAMEFIELD_SIZE_WITH_BORDER ||
                        field.size() > Game.MAX_GAMEFIELD_SIZE_WITH_BORDER){

                    throw new CustomException(CustomException.ERROR_INVALID_JSON_GAME_SIZE);
                }
                for(JsonElement currRowElement : field){ // jede Zeile durchlaufen
                    JsonArray currRow = currRowElement.getAsJsonArray();
                    //Breite des Spielfelds jeder Reihe pruefen (Anzahl Spalten)
                    if(currRow.size() < Game.MIN_GAMEFIELD_SIZE_WITH_BORDER ||
                            currRow.size() > Game.MAX_GAMEFIELD_SIZE_WITH_BORDER){

                        throw new CustomException(CustomException.ERROR_INVALID_JSON_GAME_SIZE);
                    }
                }
            } else { //kein JsonArray
                throw new CustomException(CustomException.ERROR_INVALID_JSON_WRONG_FIELD_TYPE);
            }
        } else { //kein Feld namens field
            throw new CustomException(CustomException.ERROR_INVALID_JSON_NO_FIELD);
        }
        return(true);
    }
}
