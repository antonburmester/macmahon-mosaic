package logic;

import com.google.gson.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die GameData Klasse testet
 * @author Anton Burmester
 */

public class GameDataTest {

    //da mit Dateien gearbeitet wird, erzeugt TempDir Temporaere Verzeichnisse welche pro Test neu sind
    @TempDir
    Path tempDir;

    //static saveGame(String[][], File) void

    /**
     * static saveGame(String[][], File) void
     * 1
     * ob ein bestehendes Spiel korrekt gespeichert wird
     */
    @Test
    void test_saveGame_1_Valid() throws Exception{
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Path outDirectory = tempDir.resolve("game.json"); //Verzeichnis in welches gespeichert werden soll

        //keine Fehlermeldung
        assertDoesNotThrow(() -> GameData.saveGame(gameField.translateToSpielstandsdatei(), outDirectory.toFile()));

        String json = Files.readString(outDirectory);
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        assertTrue(obj.has("field")); //Field Wert vorhanden

        JsonArray field = obj.getAsJsonArray("field");

        assertEquals(gameField.getGameFieldWidth(), field.get(0).getAsJsonArray().size()); //breite verlgeichen
        assertEquals(gameField.getGameFieldHeight(), field.getAsJsonArray().size()); //hoehe verlgiechen

        //ob alle Felder identisch sind
        boolean allFieldsSame = true;
        for(int y = 0; y < gameFieldInput.length; y++){
            for(int x = 0; x < gameFieldInput[y].length; x++){
                if(!Objects.equals(gameFieldInput[y][x], field.get(y).getAsJsonArray().get(x).getAsString())) //Input
                    // Feld und das Json Feld sind unterschiedlich
                    allFieldsSame = false;
            }
        }
        assertTrue(allFieldsSame);
    }

    /**
     * static saveGame(String[][], File) void
     * 2
     * ob ein bestehendes Spiel korrekt gespeichert wird
     */
    @Test
    void test_saveGame_2_Invalid() {
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        File outDirectory = tempDir.resolve("missingDir").resolve("game.json").toFile(); //Verzeichnis in
        // welches gespeichert werden soll exisitert nicht

        //Fehlermeldung
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.saveGame(gameField.translateToSpielstandsdatei(), outDirectory)
        );

        assertEquals(CustomException.ERROR_INVALID_FILE, customException.getErrorOrMessageCode());
    }


    //static loadGame(File) String[][]

    /**
     * static loadGame(File) String[][]
     * 1
     * ob ein .json Spielfeld korrekt eingelesen wird
     */
    @Test
    void test_loadGame_1_GameFileExists(){
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "8x7_BorderInvalid_3TilesLaid.json");

        String[][] loadedGameAsString = {{"NNNN","NNGN","NNNN","NNNN","NNNN","NNNN","NNNN"},
                                         {"NGNN","HHHH","RRRR","HHHH","HHHH","HHHH","NNNN"},
                                         {"NNNN","HHHH","NNNN","NNNN","NNNN","NNNN","NNNN"},
                                         {"NNNN","NNNN","NNNN","HHHH","YRRR","NNNN","NNNN"},
                                         {"NNNN","NNNN","NNNN","NNNN","NNNN","NNNN","NNNN"},
                                         {"NRNN","NNNN","NNNN","NNNN","NNNN","NNNN","NNNN"},
                                         {"NRNN","NNNN","NNNN","NNNN","NNNN","GYRG","NNNG"},
                                         {"NNNN","NNNN","NNNN","NNNN","NNNN","RNNN","NNNN"}};

        //keine Fehlermeldung
        String[][] loadedGameAsStringFromFile = assertDoesNotThrow(() ->  GameData.loadGame(savedGame));

        assertEquals(loadedGameAsString[0].length, loadedGameAsStringFromFile[0].length); //breite verlgeichen
        assertEquals(loadedGameAsString.length, loadedGameAsStringFromFile.length); //hoehe verlgiechen

        //ob alle Felder identisch sind
        boolean allFieldsSame = true;
        for(int y = 0; y < loadedGameAsString.length; y++){
            for(int x = 0; x < loadedGameAsString[y].length; x++){
                if (!Objects.equals(loadedGameAsString[y][x], loadedGameAsStringFromFile[y][x])) //Wie es sein sollte
                    // Feld und wie es eingelesen wurde ist unterschiedlich
                {
                    allFieldsSame = false;
                    break;
                }
            }
        }
        assertTrue(allFieldsSame);
    }

    /**
     * static loadGame(File) String[][]
     * 2
     * ob ein .json Spielfeld korrekt eingelesen wird
     */
    @Test
    void test_loadGame_2_GameFileEmpty() {
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "invalid_empty.json"); //existiert nicht


        //Fehlermeldung
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.loadGame(savedGame)
        );

        assertEquals(CustomException.ERROR_JSON_EMPTY, customException.getErrorOrMessageCode());
    }

    /**
     * static loadGame(File) String[][]
     * 3
     * ob ein .json Spielfeld korrekt eingelesen wird
     */
    @Test
    void test_loadGame_3_GameFileNotExisting() {
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "6x7.json"); //existiert nicht


        //Fehlermeldung
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.loadGame(savedGame)
        );

        assertEquals(CustomException.ERROR_INVALID_FILE, customException.getErrorOrMessageCode());
    }


    //static isJsonValid(JsonObject) void

    /**
     * static isJsonValid(JsonObject) void
     * 1
     * ob die Methode korrekt json auf validitaet testet
     */
    @Test
    void test_isJsonValid_1_valid() throws Exception{
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "valid.json");

        String json = Files.readString(savedGame.toPath());
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        assertDoesNotThrow(() ->  GameData.isJsonValid(jsonObject));
    }

    /**
     * static isJsonValid(JsonObject) void
     * 2
     * ob die Methode korrekt json auf validitaet testet
     */
    @Test
    void test_isJsonValid_2_tooBigWidth() throws Exception{
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "invalid_tooBigWidth.json");

        String json = Files.readString(savedGame.toPath());
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //Fehlercode
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.isJsonValid(jsonObject)
        );

        assertEquals(CustomException.ERROR_INVALID_JSON_GAME_SIZE, customException.getErrorOrMessageCode());
    }

    /**
     * static isJsonValid(JsonObject) void
     * 3
     * ob die Methode korrekt json auf validitaet testet
     */
    @Test
    void test_isJsonValid_3_tooBigHeight() throws Exception{
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "invalid_tooBigHeight.json");

        String json = Files.readString(savedGame.toPath());
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //Fehlercode
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.isJsonValid(jsonObject)
        );

        assertEquals(CustomException.ERROR_INVALID_JSON_GAME_SIZE, customException.getErrorOrMessageCode());
    }

    /**
     * static isJsonValid(JsonObject) void
     * 4
     * ob die Methode korrekt json auf validitaet testet
     */
    @Test
    void test_isJsonValid_4_tooSmallWidth() throws Exception{
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "invalid_tooSmallWidth.json");

        String json = Files.readString(savedGame.toPath());
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //Fehlercode
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.isJsonValid(jsonObject)
        );

        assertEquals(CustomException.ERROR_INVALID_JSON_GAME_SIZE, customException.getErrorOrMessageCode());
    }

    /**
     * static isJsonValid(JsonObject) void
     * 5
     * ob die Methode korrekt json auf validitaet testet
     */
    @Test
    void test_isJsonValid_5_tooSmallHeight() throws Exception{
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "invalid_tooSmallHeight.json");

        String json = Files.readString(savedGame.toPath());
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //Fehlercode
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.isJsonValid(jsonObject)
        );

        assertEquals(CustomException.ERROR_INVALID_JSON_GAME_SIZE, customException.getErrorOrMessageCode());
    }

    /**
     * static isJsonValid(JsonObject) void
     * 6
     * ob die Methode korrekt json auf validitaet testet
     */
    @Test
    void test_isJsonValid_6_noJsonArray() throws Exception{
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "invalid_noJsonArray.json");

        String json = Files.readString(savedGame.toPath());
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //Fehlercode
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.isJsonValid(jsonObject)
        );

        assertEquals(CustomException.ERROR_INVALID_JSON_WRONG_FIELD_TYPE, customException.getErrorOrMessageCode());
    }

    /**
     * static isJsonValid(JsonObject) void
     * 7
     * ob die Methode korrekt json auf validitaet testet
     */
    @Test
    void test_isJsonValid_7_wrongProperty() throws Exception{
        File savedGamesDirectory = GameData.getSavedGamesDirectory();
        File savedGame = new File(savedGamesDirectory, "invalid_wrongProperty.json");

        String json = Files.readString(savedGame.toPath());
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //Fehlercode
        CustomException customException = assertThrows(
                CustomException.class,
                () -> GameData.isJsonValid(jsonObject)
        );

        assertEquals(CustomException.ERROR_INVALID_JSON_NO_OR_WRONG_NAMED_FIELD,
                customException.getErrorOrMessageCode());
    }

    //static getSavedGamesPath() File
        //nicht testen da getter
}