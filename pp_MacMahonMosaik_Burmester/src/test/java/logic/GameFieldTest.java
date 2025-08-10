package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die GameField Klasse testet
 * @author Anton Burmester
 */

public class GameFieldTest {

    //GameField(int, int, boolean)

    /**
     * GameField(int, int, boolean)
     * 1
     * Masse, Raender, Ecken, inneres Spielfeld und Tiles Instanz testen
     */
    @Test
    void Test_Constructor1_1_5x6() {
        int height = 3, width = 4;
        GameField gameField = new GameField(height, width, false);

        //passt die groesse des erzeugten Spielfeld
        assertEquals(height + 2, gameField.getGameFieldHeight(), "Error Height");
        assertEquals(width + 2, gameField.getGameFieldWidth(), "Error Width");

        int gameFieldHeigth = gameField.getGameFieldHeight();
        int gameFieldWidth = gameField.getGameFieldWidth();

        //sind die Raender (ohne Ecken) RRRR
        for (int x = 1; x < gameFieldWidth - 1; x++) { //x = 1 und x < gameFieldWidth -1 um die Ecken zu ueberspringen
            assertEquals(TileNames.RRRR, gameField.getTile(x, 0).getTileName(), "Error upper Border");
            assertEquals(TileNames.RRRR, gameField.getTile(x, gameFieldHeigth - 1).getTileName(),
                    "Error lower Border");
        }
        for (int y = 1; y < gameFieldHeigth - 1; y++) { //x = 1 und x < gameFieldHeigth -1 um die Ecken zu ueberspringen
            assertEquals(TileNames.RRRR, gameField.getTile(0, y).getTileName(), "Error left Border");
            assertEquals(TileNames.RRRR, gameField.getTile(gameFieldWidth - 1, y).getTileName(),
                    "Error right Border");
        }

        //sind die Ecken NNNN
        assertEquals(TileNames.NNNN, gameField.getTile(0, 0).getTileName(), "Error left upper edge");
        assertEquals(TileNames.NNNN, gameField.getTile(gameFieldWidth - 1, 0).getTileName(),
                "Error right upper edge");
        assertEquals(TileNames.NNNN, gameField.getTile(0, gameFieldHeigth - 1).getTileName(),
                "Error left lower edge");
        assertEquals(TileNames.NNNN, gameField.getTile(gameFieldWidth - 1, gameFieldHeigth - 1).getTileName());

        //sind die inneren Felder NNNN
        for (int y = 1; y < gameFieldHeigth - 1; y++) {
            for (int x = 1; x < gameFieldWidth - 1; x++) {
                assertEquals(gameField.getTile(x, y).getTileName(), TileNames.NNNN);
            }
        }

        //Tiles wurden erstellt und enthalten alle Tile die es enthalten soll. Tiles Konstruktor in Tiles getestet
        assertEquals(Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY, gameField.getTiles().getTileCount());
    }


    //getGameFieldWidth()int
        //keine Tests, da einfacher Getter


    //getGameFieldHeight() int
        //keine Tests, da einfacher Getter


    //getTiles() Tiles
        //keine Tests, da einfacher Getter


    //getGameField() Tile[][]
        //keine Tests, da einfacher Getter


    //getTile(int, int) Tile
        //keine Tests, da einfacher Getter


    //isInputStringGameFieldValid(String[][]) boolean

    /**
     * isInputStringGameFieldValid(String[][]) boolean
     * 1
     * ob ein valider String Spielfeld Input valide mit der GameField Klasse ist
     */
    @Test
    public void test_isInputStringGameFieldValid_1_Valid(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        assertTrue(GameField.isInputStringGameFieldValid(gameFieldInput));
    }

    /**
     * isInputStringGameFieldValid(String[][]) boolean
     * 2
     * ob ein nicht valider String (1,1 nicht existierender Spielstein) Spielfeld Input nicht valide
     */
    @Test
    public void test_isInputStringGameFieldValid_2_TileNotExisting(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "AWID", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        assertFalse(GameField.isInputStringGameFieldValid(gameFieldInput));
    }

    /**
     * isInputStringGameFieldValid(String[][]) boolean
     * 3
     * ob ein nicht valider String (0,1 falscher Stein auf dem Rand) Spielfeld Input nicht valide
     */
    @Test
    public void test_isInputStringGameFieldValid_3_BorderNotCompatible(){
        String[][] gameFieldInput =  {{"NNNN", "GGYY", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        assertFalse(GameField.isInputStringGameFieldValid(gameFieldInput));
    }

    /**
     * isInputStringGameFieldValid(String[][]) boolean
     * 4
     * ob ein nicht valider String (0,0 falscher Stein auf der Ecke) Spielfeld Input nicht valide mit der GameField
     * Klasse ist
     */
    @Test
    public void test_isInputStringGameFieldValid_4_EdgeNotCompatible(){
        String[][] gameFieldInput =  {{"NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        assertFalse(GameField.isInputStringGameFieldValid(gameFieldInput));
    }

    /**
     * isInputStringGameFieldValid(String[][]) boolean
     * 5
     * ob ein nicht valider String (1,3 und 1,4 identischer Stein im mittleren Spielfeld)
     * Spielfeld Input nicht valide
     */
    @Test
    public void test_isInputStringGameFieldValid_5_DuplicateTileInvalid(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        assertFalse(GameField.isInputStringGameFieldValid(gameFieldInput));
    }

    /**
     * isInputStringGameFieldValid(String[][]) boolean
     * 6
     * ob ein nicht valider String (25 mittlere Felder braeuchte 1 Loch) Spielfeld Input nicht valide
     */
    @Test
    public void test_isInputStringGameFieldValid_6_MissingHoleTile(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        assertFalse(GameField.isInputStringGameFieldValid(gameFieldInput));
    }

    /**
     * isInputStringGameFieldValid(String[][]) boolean
     * 6
     * ob ein nicht valider String (25 mittlere Felder braeuchte 1 Loch sind aber 2) Spielfeld Input nicht valide
     */
    @Test
    public void test_isInputStringGameFieldValid_6_TooMuchHoleTile(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "HHHH", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        assertFalse(GameField.isInputStringGameFieldValid(gameFieldInput));
    }


    //isFieldFree(int, int) boolean

    /**
     * isFieldFree(int, int) boolean
     * 1
     * ob ein Spielfeld Rand Feld leer ist
     */
    @Test
    public void test_isFieldFree_1_BorderFree(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldFree(1, 0));
    }

    /**
     * isFieldFree(int, int) boolean
     * 2
     * ob ein mittleres NNNN Spielfeld Feld frei ist
     */
    @Test
    public void test_isFieldFree_2_MiddleFieldFreeNNNN(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldFree(1, 1));
    }

    /**
     * isFieldFree(int, int) boolean
     * 3
     * ob ein mittleres HHHH Spielfeld Feld frei ist
     */
    @Test
    public void test_isFieldFree_3_MiddleFieldNotFreeHHHH(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertFalse(gameField.isFieldFree(1, 2));
    }

    /**
     * isFieldFree(int, int) boolean
     * 4
     * ob ein mittleres belegtes Spielfeld Feld frei ist
     */
    @Test
    public void test_isFieldFree_4_MiddleFieldNotFreeGameTile(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertFalse(gameField.isFieldFree(3, 2));
    }


    //isFieldGameField(int, int) boolean

    /**
     * isFieldGameField(int, int) boolean
     * 1
     * ob ein bestimmtes Feld im Spielfeld liegt
     * valide
     */
    @Test
    public void test_isFieldGameField_1_MiddleField(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldGamefield(0,0));
    }

    /**
     * isFieldGameField(int, int) boolean
     * 2
     * ob ein bestimmtes Feld im Spielfeld liegt
     * invalide: x Index zu klein
     */
    @Test
    public void test_isFieldGameField_2_WidthIndexTooSmall(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldGamefield(0,0));
        assertFalse(gameField.isFieldGamefield(-1,0));
    }

    /**
     * isFieldGameField(int, int) boolean
     * 3
     * ob ein bestimmtes Feld im Spielfeld liegt
     * invalide: y Index zu klein
     */
    @Test
    public void test_isFieldGameField_3_HeightIndexTooSmall(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldGamefield(0,0));
        assertFalse(gameField.isFieldGamefield(0,-1));
    }

    /**
     * isFieldGameField(int, int) boolean
     * 4
     * ob ein bestimmtes Feld im Spielfeld liegt
     * invalide: x Index zu gross
     */
    @Test
    public void test_isFieldGameField_4_WidthIndexTooBig(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldGamefield(6,6));
        assertFalse(gameField.isFieldGamefield(7,6));
    }

    /**
     * isFieldGameField(int, int) boolean
     * 5
     * ob ein bestimmtes Feld im Spielfeld liegt
     * invalide: y Index zu gross
     */
    @Test
    public void test_isFieldGameField_5_HeightIndexTooBig(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldGamefield(6,6));
        assertFalse(gameField.isFieldGamefield(6,7));
    }


    //isFieldMiddleGamefield(int, int) boolean

    /**
     * isFieldMiddleGamefield(int, int) boolean
     * 1
     * ob ein bestimmtes Feld im mittleren Teil des Spielfelds liegt
     * valide
     */
    @Test
    public void test_isFieldMiddleGamefield_1_MiddleField(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldMiddleGamefield(1,1));
    }

    /**
     * isFieldMiddleGamefield(int, int) boolean
     * 2
     * ob ein bestimmtes Feld im mittleren Teil des Spielfelds liegt
     * invalide: x Index zu klein
     */
    @Test
    public void test_isFieldMiddleGameField_2_WidthIndexTooSmall(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldMiddleGamefield(1,1));
        assertFalse(gameField.isFieldMiddleGamefield(0,1));
    }

    /**
     * isFieldMiddleGamefield(int, int) boolean
     * 3
     * ob ein bestimmtes Feld im mittleren Teil des Spielfelds liegt
     * invalide: y Index zu klein
     */
    @Test
    public void test_isFieldMiddleGameField_3_HeightIndexTooSmall(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldMiddleGamefield(1,1));
        assertFalse(gameField.isFieldMiddleGamefield(1,0));
    }

    /**
     * isFieldMiddleGamefield(int, int) boolean
     * 4
     * ob ein bestimmtes Feld im mittleren Teil des Spielfelds liegt
     * invalide: x Index zu gross
     */
    @Test
    public void test_isFieldMiddleGameField_4_WidthIndexTooBig(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldMiddleGamefield(5,5));
        assertFalse(gameField.isFieldMiddleGamefield(6,5));
    }

    /**
     * isFieldMiddleGamefield(int, int) boolean
     * 5
     * ob ein bestimmtes Feld im mittleren Teil des Spielfelds liegt
     * invalide: y Index zu gross
     */
    @Test
    public void test_isFieldMiddleGameField_5_HeightIndexTooBig(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isFieldMiddleGamefield(5,5));
        assertFalse(gameField.isFieldMiddleGamefield(5,6));
    }


    //static isFieldBorder(int, int, int, int) boolean

    /**
     * static isFieldBorder(int, int, int, int) boolean
     * 1
     * ob ein bestimmtes Feld im Randbereich liegt (Ecke und Rand)
     * valide
     */
    @Test
    public void test_isFieldBorder_1_Valid(){
        assertTrue(GameField.isFieldBorder(0, 0, 6, 6)); //Ecke
        assertTrue(GameField.isFieldBorder(1, 0, 6, 6)); //Rand
    }

    /**
     * static isFieldBorder(int, int, int, int) boolean
     * 2
     * ob ein bestimmtes Feld im Randbereich liegt (Ecke und Rand)
     * invalide
     */
    @Test
    public void test_isFieldBorder_2_Invalid(){
        assertFalse(GameField.isFieldBorder(-1, 0, 6, 6)); //x zu klein
        assertFalse(GameField.isFieldBorder(0, -1, 6, 6)); //y zu klein
        assertFalse(GameField.isFieldBorder(7, 6, 6, 6)); //x zu gross
        assertFalse(GameField.isFieldBorder(6, 7, 6, 6)); //y zu gross
    }


    //isFieldBorder(int, int) boolean
        //muss nicht mehr getestet werden, da diese Methode auf die static isFieldBorder zugreift welche getestet wird


    //static isFieldEdge(int, int) boolean

    /**
     * static isFieldEdge(int, int, int, int) boolean
     * 1
     * ob ein bestimmtes Feld im Eckbereich liegt (Ecke und Rand)
     * valide
     */
    @Test
    public void test_isFieldEdge_1_Valid(){
        assertTrue(GameField.isFieldEdge(0, 0, 6, 6)); //Ecke oben links
        assertTrue(GameField.isFieldEdge(5, 0, 6, 6)); //Ecke oben rechts
        assertTrue(GameField.isFieldEdge(0, 5, 6, 6)); //Ecke unten links
        assertTrue(GameField.isFieldEdge(5, 5, 6, 6)); //Ecke unten rechts
    }

    /**
     * static isFieldEdge(int, int, int, int) boolean
     * 2
     * ob ein bestimmtes Feld im Randbereich liegt (Ecke und Rand)
     * invalide
     */
    @Test
    public void test_isFieldEdge_2_Invalid(){
        assertFalse(GameField.isFieldEdge(1, 0, 6, 6)); //Ecke oben links; x zu gross
        assertFalse(GameField.isFieldEdge(4, 0, 6, 6)); //Ecke oben rechts; x zu klein
        assertFalse(GameField.isFieldEdge(0, 4, 6, 6)); //Ecke unten links; y zu klein
        assertFalse(GameField.isFieldEdge(5, 6, 6, 6)); //Ecke unten rechts; y zu gross
    }


    //isFieldEdge(int, int) boolean
        //muss nicht mehr getestet werden, da diese Methode auf die static isFieldEdge zugreift welche getestet wird


    //isGameFieldBorderSetted() boolean

    /**
     * isGameFieldBorderSetted() boolean
     * 1
     * ob der gesamte Rand eingefaerbt wurde (nicht NNNN) abgesehen der Ecken
     * valide
     */
    @Test
    public void test_isGameFieldBorderSetted_1_Valid(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.isGameFieldBorderSetted());
    }

    /**
     * isGameFieldBorderSetted() boolean
     * 2
     * ob der gesamte Rand eingefaerbt wurde (nicht NNNN) abgesehen der Ecken
     * invalide (1,0)
     */
    @Test
    public void test_isGameFieldBorderSetted_2_InvalidNNNN(){
        String[][] gameFieldInput =  {{"NNNN", "NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertFalse(gameField.isGameFieldBorderSetted());
    }


    //static isTwoFieldsOfTileNotMatching(char, char, boolean) boolean

    /**
     * static isTwoFieldsOfTileNotMatching(char, char, boolean) boolean
     * 1
     * ob zwei Buchstaben nicht aneinander passen
     * Valide
     */
    @Test
    public void test_isTwoFieldsOfTileNotMatching_1_Valid(){
        assertFalse(GameField.isTwoFieldsOfTileNotMatching('Y', 'Y', false)); //beide identisch
        assertFalse(GameField.isTwoFieldsOfTileNotMatching('Y', 'Y', true)); //beide identisch
        assertFalse(GameField.isTwoFieldsOfTileNotMatching('N', 'Y', true)); //einer n acceptN
        assertFalse(GameField.isTwoFieldsOfTileNotMatching('Y', 'N', true)); //einer n acceptN
        assertFalse(GameField.isTwoFieldsOfTileNotMatching('N', 'N', true)); //beide n acceptN
    }

    /**
     * static isTwoFieldsOfTileNotMatching(char, char, boolean) boolean
     * 2
     * ob zwei Buchstaben nicht aneinander passen
     * Valide
     */
    @Test
    public void test_isTwoFieldsOfTileNotMatching_2_Invalid(){
        assertTrue(GameField.isTwoFieldsOfTileNotMatching('Y', 'G', false)); //beide unterschiedlich
        assertTrue(GameField.isTwoFieldsOfTileNotMatching('N', 'Y', false)); //einer n
        assertTrue(GameField.isTwoFieldsOfTileNotMatching('Y', 'N', false)); //einer n
    }


    //isGameFieldTileMatching(int, int, boolean) boolean

    /**
     * isGameFieldTileMatching(int, int, boolean) boolean
     * 1
     * ob der Spielstein eines Felds zu allen umliegenden passt
     * mittleres Spielfeld grenzt an verschiedene andere Spielsteine
     */
    @Test
    public void test_isGameFieldTileMatching_1_MiddleGameField(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GGYG", "RGRG", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "YYYY", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "GGGG", "NNNN", "NNNN", "HHHH", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        //passt nur oben nicht
        gameField.resetTile(2, 2);
        gameField.layTile(2, 2, gameField.getTiles().getTileByNameWithRotation("GYGY"));
        assertFalse(gameField.isGameFieldTileMatching(2,2, false), "should not match upwards");
        assertFalse(gameField.isGameFieldTileMatching(2,2, true), "should not match upwards");

        //passt nur rechts nicht
        gameField.resetTile(2, 2);
        gameField.layTile(2, 2, gameField.getTiles().getTileByNameWithRotation("RRGY"));
        assertFalse(gameField.isGameFieldTileMatching(2,2, false), "should not match right");
        assertFalse(gameField.isGameFieldTileMatching(2,2, true), "should not match right");

        //passt nur unten nicht
        gameField.resetTile(2, 2);
        gameField.layTile(2, 2, gameField.getTiles().getTileByNameWithRotation("RYRY"));
        assertFalse(gameField.isGameFieldTileMatching(2,2, false), "should not match downwards");
        assertFalse(gameField.isGameFieldTileMatching(2,2, true), "should not match downwards");

        //passt nur links nicht
        gameField.resetTile(2, 2);
        gameField.layTile(2, 2, gameField.getTiles().getTileByNameWithRotation("RYGG"));
        assertFalse(gameField.isGameFieldTileMatching(2,2, false), "should not match left");
        assertFalse(gameField.isGameFieldTileMatching(2,2, true), "should not match left");

        //passt und passt nicht NNNN
        gameField.resetTile(2, 2);
        gameField.layTile(2, 2, new Tile(TileNames.NNNN));
        assertFalse(gameField.isGameFieldTileMatching(2,2, false), "should not match NNNN");
        assertTrue(gameField.isGameFieldTileMatching(2,2, true), "should match NNNN");

        //passt Lochstein
        gameField.resetTile(2, 2);
        gameField.layTile(2, 2, new Tile(TileNames.HHHH));
        assertTrue(gameField.isGameFieldTileMatching(2,2, false), "should match HHHH");
        assertTrue(gameField.isGameFieldTileMatching(2,2, true), "should match HHHH");

        //passt Spielstein
        gameField.resetTile(2, 2);
        gameField.layTile(2, 2, gameField.getTiles().getTileByNameWithRotation("RYGY"));
        assertTrue(gameField.isGameFieldTileMatching(2,2, false), "should match Tile");
        assertTrue(gameField.isGameFieldTileMatching(2,2, true), "should match Tile");
    }


    //checkIfGameFieldSolved(boolean) boolean

    /**
     * checkIfGameFieldSolved(boolean) boolean
     * 1
     * ob das Spielfeld geloest wurde
     */
    @Test
    public void test_checkIfGameFieldSolved_1_Valid_NNNNTrue() {
        String[][] gameFieldInput = {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                     {"NGNN", "GGYG", "RGRG", "NNNN", "NNNN", "NNNN", "NNNG"},
                                     {"NRNN", "YYYY", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                     {"NGNN", "NNNN", "GGGG", "NNNN", "NNNN", "HHHH", "NNNG"},
                                     {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                     {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                     {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
    }

    /**
     * ob das Feld in der richtigen Groeße initialisiert wird
     */
    @Test
    public void testSimpleConstructor(){
        GameField gameField = new GameField(2,6, true);
        //4 da 2+2 = 4 und 8 da 6+2 = 8 (fuer die Raender)
        assertTrue(gameField.getGameFieldHeight() == 4 && gameField.getGameFieldWidth() == 8);
    }

    /**
     * layTile()
     * ob ein mit einem Spielstein belegtes Feld belegt werden kann
     */
    @Test
    public void test_layTile_FeldBelegt_Loch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "HHHH", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        boolean status = gameField.layTile(2, 2, new Tile("YYRR"));
        assertFalse(status);
    }

    /**
     * layTile()
     * ob ein mit einem Spielstein belegtes Feld belegt werden kann
     */
    @Test
    public void test_layTile_FeldBelegt_Spielstein(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YYYY", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        boolean status = gameField.layTile(2, 2, new Tile("YYRR"));
        assertFalse(status);
    }

    /**
     * layTile()
     * ob ein leeres Feld belegt werden kann
     */
    @Test
    public void test_layTile_FeldFrei(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        boolean status = gameField.layTile(2, 2, new Tile("YYRR"));
        assertTrue(status);
    }

    /**
     * cloneGameField()
     * ob das Spielfeld korrekt kopiert wird
     * 5x5 GameField also 3x3 innere Felder
     * 0 Loecher
     */
    @Test
    public void testCloneGameField_5x5(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        //Original GameField
        GameField gameField = new GameField(gameFieldInput);

        //Cloned GameField
        GameField clonedGameField = gameField.cloneGameField();

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameStringWithRotation().
                        equals(clonedGameField.getTile(x, y).getTileNameStringWithRotation()))
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField()
     * ob das Spielfeld korrekt kopiert wird
     * 5x6 GameField also 3x4 innere Felder
     * 0 Loecher
     */
    @Test
    public void testCloneGameField_5x6(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        //Original GameField
        GameField gameField = new GameField(gameFieldInput);

        //Cloned GameField
        GameField clonedGameField = gameField.cloneGameField();

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameStringWithRotation().
                        equals(clonedGameField.getTile(x, y).getTileNameStringWithRotation()))
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField()
     * ob das Spielfeld korrekt kopiert wird
     * 7x7 GameField also 5x5 innere Felder
     * 5x5-24 = 25-24 = 1 Loch
     */
    @Test
    public void testCloneGameField_7x7_OneHole(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        //Original GameField
        GameField gameField = new GameField(gameFieldInput);

        //Cloned GameField
        GameField clonedGameField = gameField.cloneGameField();

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameStringWithRotation().
                        equals(clonedGameField.getTile(x, y).getTileNameStringWithRotation()))
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField()
     * ob das Spielfeld korrekt kopiert wird
     * 8x7 GameField also 6x5 innere Felder
     * 6x5-24 = 30-24 = 6 Loecher
     */
    @Test
    public void testCloneGameField_8x7_SixHoles(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GGGR", "NNNN", "HHHH", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "HHHH", "HHHH", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "HHHH", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        //Original GameField
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput);

        //Cloned GameField
        GameField clonedGameField = gameField.cloneGameField();

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameStringWithRotation().
                        equals(clonedGameField.getTile(x, y).getTileNameStringWithRotation()))
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField()
     * ob das Spielfeld korrekt kopiert wird
     * 8x8 GameField also 6x6 innere Felder
     * 6x6-24 = 36-24 = 12 Loecher
     */
    @Test
    public void testCloneGameField_8x8_TwelveHoles(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNRN", "NNGN", "NNRN", "NNNN"},
                                      {"NGNN", "GGGR", "NNNN", "HHHH", "NNNN", "NNNN", "HHHH", "NNNG"},
                                      {"NRNN", "HHHH", "YGYY", "YGRY", "NNNN", "GGYY", "YGGG", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "HHHH", "HHHH", "HHHH", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "HHHH", "RRRR", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "HHHH", "NNNN", "NNNN", "NNNN", "HHHH", "HHHH", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        //Original GameField
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput);

        //Cloned GameField
        GameField clonedGameField = gameField.cloneGameField();

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameStringWithRotation().
                        equals(clonedGameField.getTile(x, y).getTileNameStringWithRotation()))
                    status = false;
            }
        }

        assertTrue(status);
    }


    //isGameFieldSolvable() boolean

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 5x5 Feld
     * False
     */
    @Test
    public void testGameFieldSolvable5x5TrueOnlyYellow(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 5x5 Feld
     * True
     */
    @Test
    public void testGameFieldSolvable5x5True(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                {"NRNN", "NNNN", "NNNN", "NNNN", "NNNR"},
                {"NGNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 5x5 Feld
     * True
     */
    @Test
    public void testGameFieldSolvable5x5True2() {
        String[][] gameFieldInput = {{"NNNN", "NNYN", "NNYN", "NNGN", "NNNN"},
                {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 8x8 Feld
     * True
     */
    @Test
    public void testGameFieldSolvable8x8True() {
        String[][] gameFieldInput = {
                {"NNNN", "YYYY", "YYYY", "GGGG", "GGGG", "RRRR", "RRRR", "NNNN"},
                {"YYYY", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "RRRR"},
                {"YYYY", "NNNN", "HHHH", "HHHH", "HHHH", "HHHH", "NNNN", "RRRR"},
                {"GGGG", "NNNN", "HHHH", "NNNN", "NNNN", "HHHH", "NNNN", "GGGG"},
                {"GGGG", "NNNN", "HHHH", "NNNN", "NNNN", "HHHH", "NNNN", "GGGG"},
                {"RRRR", "NNNN", "HHHH", "HHHH", "HHHH", "HHHH", "NNNN", "YYYY"},
                {"RRRR", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "YYYY"},
                {"NNNN", "RRRR", "RRRR", "GGGG", "GGGG", "YYYY", "YYYY", "NNNN"}
        };

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 8x8 Feld
     * True
     */
    @Test
    public void testGameFieldSolvable8x8_Second_True() {
        String[][] gameFieldInput = {
                {"NNNN", "GGGG", "GGGG", "RRRR", "RRRR", "RRRR", "RRRR", "NNNN"},
                {"GGGG", "HHHH", "NNNN", "NNNN", "HHHH", "NNNN", "HHHH", "RRRR"},
                {"RRRR", "HHHH", "NNNN", "HHHH", "HHHH", "NNNN", "HHHH", "RRRR"},
                {"RRRR", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "GGGG"},
                {"GGGG", "NNNN", "HHHH", "NNNN", "HHHH", "NNNN", "NNNN", "GGGG"},
                {"RRRR", "NNNN", "NNNN", "HHHH", "NNNN", "HHHH", "NNNN", "RRRR"},
                {"GGGG", "HHHH", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "GGGG"},
                {"NNNN", "GGGG", "GGGG", "RRRR", "GGGG", "GGGG", "RRRR", "NNNN"}
        };

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 8x8 Feld
     * True
     */
    @Test
    public void testGameFieldSolvable8x8SomeAlreadySolvedTrue() {
        String[][] gameFieldInput = {
                {"NNNN", "GGGG", "GGGG", "RRRR", "RRRR", "RRRR", "RRRR", "NNNN"},
                {"GGGG", "HHHH", "GRGR", "RRRR", "HHHH", "NNNN", "HHHH", "RRRR"},
                {"RRRR", "HHHH", "NNNN", "HHHH", "HHHH", "GGYR", "HHHH", "RRRR"},
                {"RRRR", "GGGR", "NNNN", "YYYY", "NNNN", "NNNN", "NNNN", "GGGG"},
                {"GGGG", "NNNN", "HHHH", "NNNN", "HHHH", "NNNN", "GGGY", "GGGG"},
                {"RRRR", "NNNN", "NNNN", "HHHH", "NNNN", "HHHH", "NNNN", "RRRR"},
                {"GGGG", "HHHH", "NNNN", "NNNN", "YRGY", "NNNN", "RGRY", "GGGG"},
                {"NNNN", "GGGG", "GGGG", "RRRR", "GGGG", "GGGG", "RRRR", "NNNN"}
        };

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 7x6 Feld
     * False
     */
    @Test
    public void testGameFieldSolvable7x6AlreadyLaidFalse() {
        String[][] gameFieldInput = {
                {"NNNN", "GGGG", "GGGG", "RRRR", "RRRR", "RRRR", "NNNN"},
                {"RRRR", "GGYY", "NNNN", "NNNN", "NNNN", "NNNN", "GGGG"},
                {"GGGG", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "GGGG"},
                {"RRRR", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "RRRR"},
                {"GGGG", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "GGGG"},
                {"NNNN", "GGGG", "GGGG", "RRRR", "GGGG", "GGGG", "NNNN"}
        };

        GameField gameField = new GameField(gameFieldInput);
        assertFalse(gameField.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 7x6 Feld
     * False
     */
    @Test
    public void testGameFieldSolvable7x6AlreadyLaidFalse2() {
        String[][] gameFieldInput = {
                {"NNNN", "RRRR", "RRRR", "RRRR", "RRRR", "RRRR", "NNNN"},
                {"RRRR", "GGRR", "RGYG", "NNNN", "NNNN", "NNNN", "RRRR"},
                {"RRRR", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "RRRR"},
                {"RRRR", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "RRRR"},
                {"RRRR", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "RRRR"},
                {"NNNN", "RRRR", "RRRR", "RRRR", "RRRR", "RRRR", "NNNN"}
        };

        GameField gameField = new GameField(gameFieldInput);
        assertFalse(gameField.isGameFieldSolvable());
    }
}