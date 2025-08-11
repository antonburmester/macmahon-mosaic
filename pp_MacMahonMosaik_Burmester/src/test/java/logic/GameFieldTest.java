package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die GameField Klasse testet
 * @author Anton Burmester
 */

public class GameFieldTest {

    //GameField(int, int)

    /**
     * GameField(int, int)
     * 1
     * ob der Konstruktor von den Massen her funktioniert.
     * Der Rest wird in den folgenden Tests geprueft
     */
    @Test
    void test_Constructor1_1_5x6(){
        int height = 3, width = 4;
        GameField gameField = new GameField(height, width);

        assertEquals(Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY, gameField.getTiles().getTileCount());
        assertEquals(height, gameField.getGameFieldHeight() -2);
        assertEquals(width, gameField.getGameFieldWidth() -2);
    }


    //GameField(int, int, boolean)

    /**
     * GameField(int, int, boolean)
     * 1
     * ob der Konstruktor von den Massen her funktioniert.
     * Der Rest wird in den folgenden Tests geprueft
     */
    @Test
    void test_Constructor2_1_5x6() {
        int height = 3, width = 4;
        GameField gameField = new GameField(height, width, false);

        assertEquals(Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY, gameField.getTiles().getTileCount());
        assertEquals(height, gameField.getGameFieldHeight() -2);
        assertEquals(width, gameField.getGameFieldWidth() -2);
    }


    //GameField(int, int, boolean)

    /**
     * GameField(String[][])
     * 1
     * ob der Konstruktor von den Massen her funktioniert.
     * Der Rest wird in den folgenden Tests geprueft
     */
    @Test
    void test_Constructor3_1_5x6() {
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertEquals(Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY, gameField.getTiles().getTileCount());
        assertEquals(7, gameField.getGameFieldHeight());
        assertEquals(7, gameField.getGameFieldWidth());
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
    void test_isInputStringGameFieldValid_1_Valid(){
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
    void test_isInputStringGameFieldValid_2_TileNotExisting(){
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
    void test_isInputStringGameFieldValid_3_BorderNotCompatible(){
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
    void test_isInputStringGameFieldValid_4_EdgeNotCompatible(){
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
    void test_isInputStringGameFieldValid_5_DuplicateTileInvalid(){
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
    void test_isInputStringGameFieldValid_6_MissingHoleTile(){
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
    void test_isInputStringGameFieldValid_6_TooMuchHoleTile(){
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
    void test_isFieldFree_1_BorderFree(){
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
    void test_isFieldFree_2_MiddleFieldFreeNNNN(){
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
    void test_isFieldFree_3_MiddleFieldNotFreeHHHH(){
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
    void test_isFieldFree_4_MiddleFieldNotFreeGameTile(){
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
    void test_isFieldGameField_1_MiddleField(){
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
    void test_isFieldGameField_2_WidthIndexTooSmall(){
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
    void test_isFieldGameField_3_HeightIndexTooSmall(){
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
    void test_isFieldGameField_4_WidthIndexTooBig(){
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
    void test_isFieldGameField_5_HeightIndexTooBig(){
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
    void test_isFieldMiddleGamefield_1_MiddleField(){
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
    void test_isFieldMiddleGameField_2_WidthIndexTooSmall(){
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
    void test_isFieldMiddleGameField_3_HeightIndexTooSmall(){
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
    void test_isFieldMiddleGameField_4_WidthIndexTooBig(){
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
    void test_isFieldMiddleGameField_5_HeightIndexTooBig(){
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
    void test_isFieldBorder_1_Valid(){
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
    void test_isFieldBorder_2_Invalid(){
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
    @Test void test_isFieldEdge_1_Valid(){
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
    void test_isFieldEdge_2_Invalid(){
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
    void test_isGameFieldBorderSetted_1_Valid(){
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
    void test_isGameFieldBorderSetted_2_InvalidNNNN(){
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
    void test_isTwoFieldsOfTileNotMatching_1_Valid(){
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
    void test_isTwoFieldsOfTileNotMatching_2_Invalid(){
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
    void test_isGameFieldTileMatching_1_MiddleGameField(){
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
     * True
     */
    @Test
    void test_checkIfGameFieldSolved_1_CompletelySolved() {
        String[][] gameFieldInput = {{"NNNN","NNYN","NNYN","NNGN","NNGN","NNRN","NNRN","NNNN"},
                                     {"NYNN","YYYY","YRYY","GRGR","GRRR","RRRR","RRYR","NNNR"},
                                     {"NYNN","YGYY","HHHH","HHHH","HHHH","HHHH","YRYR","NNNR"},
                                     {"NGNN","YGYG","HHHH","GGGG","GGRG","HHHH","YGGG","NNNG"},
                                     {"NGNN","YRRG","HHHH","GGYR","RYGG","HHHH","GGRR","NNNG"},
                                     {"NRNN","RYYR","HHHH","HHHH","HHHH","HHHH","RYRG","NNNY"},
                                     {"NRNN","YGRR","YYRG","RYGY","YGGY","RGYG","RYYG","NNNY"},
                                     {"NNNN","RNNN","RNNN","GNNN","GNNN","YNNN","YNNN","NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertTrue(gameField.checkIfGameFieldSolved(false));
        assertTrue(gameField.checkIfGameFieldSolved(true));
    }

    /**
     * checkIfGameFieldSolved(boolean) boolean
     * 2
     * ob das Spielfeld geloest wurde
     */
    @Test
    void test_checkIfGameFieldSolved_2_MissingTileSolvable() {
        String[][] gameFieldInput = {{"NNNN","NNYN","NNYN","NNGN","NNGN","NNRN","NNRN","NNNN"},
                                     {"NYNN","YYYY","YRYY","GRGR","GRRR","RRRR","RRYR","NNNR"},
                                     {"NYNN","YGYY","HHHH","HHHH","HHHH","HHHH","YRYR","NNNR"},
                                     {"NGNN","YGYG","HHHH","GGGG","GGRG","HHHH","YGGG","NNNG"},
                                     {"NGNN","YRRG","HHHH","GGYR","NNNN","HHHH","GGRR","NNNG"},
                                     {"NRNN","RYYR","HHHH","HHHH","HHHH","HHHH","RYRG","NNNY"},
                                     {"NRNN","YGRR","YYRG","RYGY","YGGY","RGYG","RYYG","NNNY"},
                                     {"NNNN","RNNN","RNNN","GNNN","GNNN","YNNN","YNNN","NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertFalse(gameField.checkIfGameFieldSolved(false));
        assertTrue(gameField.checkIfGameFieldSolved(true));
    }

    /**
     * checkIfGameFieldSolved(boolean) boolean
     * 3
     * ob das Spielfeld geloest wurde
     */
    @Test
    void test_checkIfGameFieldSolved_3_MultipleMissingTileSolvable() {
        String[][] gameFieldInput = {{"NNNN","NNYN","NNYN","NNGN","NNGN","NNRN","NNRN","NNNN"},
                                     {"NYNN","YYYY","YRYY","GRGR","GRRR","RRRR","RRYR","NNNR"},
                                     {"NYNN","YGYY","HHHH","HHHH","HHHH","HHHH","YRYR","NNNR"},
                                     {"NGNN","NNNN","HHHH","NNNN","NNNN","HHHH","YGGG","NNNG"},
                                     {"NGNN","YRRG","HHHH","NNNN","NNNN","HHHH","GGRR","NNNG"},
                                     {"NRNN","RYYR","HHHH","HHHH","HHHH","HHHH","RYRG","NNNY"},
                                     {"NRNN","YGRR","YYRG","RYGY","YGGY","RGYG","RYYG","NNNY"},
                                     {"NNNN","RNNN","RNNN","GNNN","GNNN","YNNN","YNNN","NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertFalse(gameField.checkIfGameFieldSolved(false));
        assertTrue(gameField.checkIfGameFieldSolved(true));
    }

    /**
     * checkIfGameFieldSolved(boolean) boolean
     * 4
     * ob das Spielfeld geloest wurde
     */
    @Test
    void test_checkIfGameFieldSolved_4_MissingTileNotSolvable() {
        String[][] gameFieldInput = {{"NNNN","NNYN","NNYN","NNGN","NNGN","NNRN","NNRN","NNNN"},
                                     {"NYNN","YYYY","YRYY","GRGR","GRRR","RRRR","RRYR","NNNR"},
                                     {"NYNN","YGYY","HHHH","HHHH","HHHH","HHHH","YRYR","NNNR"},
                                     {"NGNN","NNNN","HHHH","NNNN","NNNN","HHHH","YGGG","NNNG"},
                                     {"NGNN","YRRG","HHHH","NNNN","NNNN","HHHH","GGRR","NNNG"},
                                     {"NRNN","RYYR","HHHH","HHHH","HHHH","HHHH","RYRG","NNNY"},
                                     {"NRNN","YGRR","YYRG","RYGY","YGGY","RGYG","RYYG","NNNY"},
                                     {"NNNN","RNNN","RNNN","GNNN","GNNN","YNNN","YNNN","NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        assertFalse(gameField.checkIfGameFieldSolved(false));
        assertTrue(gameField.checkIfGameFieldSolved(true));
    }


    //setBorderFromGameField(GameField) void

    /**
     * setBorderFromGameField(GameField) void
     * 1
     * ob der Spielfeldrand vom alten Spielfeld korrekt im neuen Spielfeld gesetzt wird
     */
    @Test
    void test_setBorderFromGameField_1_bothSameSize(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNRN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "RNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput); //3x3 Spielfeld

        GameField newGameFieldWithOldBorder = new GameField(3, 3, true); //3x3 Spielfeld
        newGameFieldWithOldBorder.setBorderFromGameField(gameField);

        boolean status = true;
        //ueberpruefen, ob Rand des alten Spielfelds und des neuen welches den alten uebernommen hat identisch ist
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(gameField.isFieldBorder(x, y)){
                    if(!gameField.getTile(x, y).getTileName().equals(
                            newGameFieldWithOldBorder.getTile(x, y).getTileName())){ //Rand vom neuen und alten
                        // Spielfeld ist unterschiedlich
                        status = false;
                    }
                }
            }
        }
        assertTrue(status);
    }

    /**
     * setBorderFromGameField(GameField) void
     * 2
     * ob der Spielfeldrand vom alten Spielfeld korrekt im neuen Spielfeld gesetzt wird
     */
    @Test
    void test_setBorderFromGameField_2_newBigger(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNRN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "RNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput); //3x3 Spielfeld

        GameField newGameField = new GameField(4, 4, true); //4x4 Spielfeld

        newGameField.setBorderFromGameField(gameField); //den Rand des alten Spielfelds im neuen Spielfeld setzen

        //Explizit jedes Randfeld schauen, da sonst im Test zum ueberpruefen derselbe code der ueberprueften Methode
        // genutzt werden muesste

        //oberer Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(0,0).getTileName(), "Error upper left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(1,0).getTileName(), "Error upper border");
        assertEquals(TileNames.YYYY, newGameField.getTile(2,0).getTileName(), "Error upper border");
        assertEquals(TileNames.RRRR, newGameField.getTile(3,0).getTileName(), "Error upper border");
        assertEquals(TileNames.RRRR, newGameField.getTile(4,0).getTileName(), "Error upper border");//
        // dieses RRRR kommt nicht aus dem alten Spielfeld sondern wurde beim erzeugen des Spielfelds gesetzt
        assertEquals(TileNames.NNNN, newGameField.getTile(5,0).getTileName(), "Error upper right edge");

        //linker Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(0,0).getTileName(), "Error upper left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(0,1).getTileName(), "Error left border");
        assertEquals(TileNames.YYYY, newGameField.getTile(0,2).getTileName(), "Error left border");
        assertEquals(TileNames.GGGG, newGameField.getTile(0,3).getTileName(), "Error left border");
        assertEquals(TileNames.RRRR, newGameField.getTile(0,4).getTileName(), "Error left border");//
        // dieses RRRR kommt nicht aus dem alten Spielfeld sondern wurde beim erzeugen des Spielfelds gesetzt
        assertEquals(TileNames.NNNN, newGameField.getTile(0,5).getTileName(), "Error lower left edge");

        //unterer Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(0,5).getTileName(), "Error lower left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(1,5).getTileName(), "Error lower border");
        assertEquals(TileNames.RRRR, newGameField.getTile(2,5).getTileName(), "Error lower border");
        assertEquals(TileNames.YYYY, newGameField.getTile(3,5).getTileName(), "Error lower border");
        assertEquals(TileNames.RRRR, newGameField.getTile(4,5).getTileName(), "Error lower border");//
        // dieses RRRR kommt nicht aus dem alten Spielfeld sondern wurde beim erzeugen des Spielfelds gesetzt
        assertEquals(TileNames.NNNN, newGameField.getTile(5,5).getTileName(), "Error lower right edge");

        //rechter Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(5,0).getTileName(), "Error upper left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(5,1).getTileName(), "Error left border");
        assertEquals(TileNames.YYYY, newGameField.getTile(5,2).getTileName(), "Error left border");
        assertEquals(TileNames.GGGG, newGameField.getTile(5,3).getTileName(), "Error left border");
        assertEquals(TileNames.RRRR, newGameField.getTile(5,4).getTileName(), "Error left border");//
        // dieses RRRR kommt nicht aus dem alten Spielfeld sondern wurde beim erzeugen des Spielfelds gesetzt
        assertEquals(TileNames.NNNN, newGameField.getTile(5,5).getTileName(), "Error lower left edge");
    }

    /**
     * setBorderFromGameField(GameField) void
     * 3
     * ob der Spielfeldrand vom alten Spielfeld korrekt im neuen Spielfeld gesetzt wird
     */
    @Test
    void test_setBorderFromGameField_3_newSmaller(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNRN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "RNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput); //3x3 Spielfeld

        GameField newGameField = new GameField(2, 2, true); //2x2 Spielfeld

        newGameField.setBorderFromGameField(gameField); //den Rand des alten Spielfelds im neuen Spielfeld setzen

        //Explizit jedes Randfeld schauen, da sonst im Test zum ueberpruefen derselbe code der ueberprueften Methode
        // genutzt werden muesste

        //oberer Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(0,0).getTileName(), "Error upper left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(1,0).getTileName(), "Error upper border");
        assertEquals(TileNames.YYYY, newGameField.getTile(2,0).getTileName(), "Error upper border");
        assertEquals(TileNames.NNNN, newGameField.getTile(3,0).getTileName(), "Error upper right edge");

        //linker Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(0,0).getTileName(), "Error upper left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(0,1).getTileName(), "Error left border");
        assertEquals(TileNames.YYYY, newGameField.getTile(0,2).getTileName(), "Error left border");
        assertEquals(TileNames.NNNN, newGameField.getTile(0,3).getTileName(), "Error lower left edge");

        //unterer Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(0,3).getTileName(), "Error lower left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(1,3).getTileName(), "Error lower border");
        assertEquals(TileNames.RRRR, newGameField.getTile(2,3).getTileName(), "Error lower border");
        assertEquals(TileNames.NNNN, newGameField.getTile(3,3).getTileName(), "Error lower right edge");

        //rechter Rand
        assertEquals(TileNames.NNNN, newGameField.getTile(3,0).getTileName(), "Error upper left edge");
        assertEquals(TileNames.YYYY, newGameField.getTile(3,1).getTileName(), "Error left border");
        assertEquals(TileNames.YYYY, newGameField.getTile(3,2).getTileName(), "Error left border");
        assertEquals(TileNames.NNNN, newGameField.getTile(3,3).getTileName(), "Error lower left edge");
    }


    //placeGameFieldEmptyBorderRed(boolean) void
        //wird schon beim erzeugen eines Spielfelds getestet unabhaengig vom Konstruktor

    /**
     * placeGameFieldEmptyBorderRed(boolean) void
     * 1
     * ob das Spielfeld korrekt mit leeren Spielsteinen und falls true der Rand ohne die Ecken mit Roten Randsteinen
     * gefuellt wird
     */
    @Test
    void test_placeGameFieldEmptyBorderRed_1_EverythingNNNN(){
        GameField gameField = new GameField(5, 5);

        boolean allTilesNull = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(gameField.getTile(x, y) != null) allTilesNull = false;
            }
        }
        assertTrue(allTilesNull);

        gameField.placeGameFieldEmptyBorderRed(false);

        boolean allTilesNotNullButNNNN = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                Tile currTile = gameField.getTile(x, y);
                if(currTile == null || !currTile.getTileName().equals(TileNames.NNNN)) allTilesNotNullButNNNN = false;
            }
        }
        assertTrue(allTilesNotNullButNNNN);
    }

    /**
     * placeGameFieldEmptyBorderRed(boolean) void
     * 2
     * ob das Spielfeld korrekt mit leeren Spielsteinen und falls true der Rand ohne die Ecken mit Roten Randsteinen
     * gefuellt wird
     */
    @Test
    void test_placeGameFieldEmptyBorderRed_2_GameFieldNNNNBorderRRRR(){
        GameField gameField = new GameField(5, 5);

        boolean allTilesNull = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(gameField.getTile(x, y) != null)
                    allTilesNull = false;
            }
        }
        assertTrue(allTilesNull);

        gameField.placeGameFieldEmptyBorderRed(true); //alle Felder NNNN setzen und Rand RRRR

        boolean allTilesNNNNBorderRRRR = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                Tile currTile = gameField.getTile(x, y);
                if(currTile != null) { //aktueller Spielstein ist nicht null
                    if (gameField.isFieldEdge(x, y)) { //aktuelle Position ist Ecke
                        if (!currTile.getTileName().equals(TileNames.NNNN)) //auf der Ecke liegt nicht NNNN
                            allTilesNNNNBorderRRRR = false;
                    } else if (gameField.isFieldBorder(x, y)) { //aktuelle Position ist Rand und keine Ecke
                        if (!currTile.getTileName().equals(TileNames.RRRR)) { //auf dem Rand liegt
                            allTilesNNNNBorderRRRR = false;
                        }
                    } else { //aktuelle Position ist mittleres Spielfeld
                        if (!currTile.getTileName().equals(TileNames.NNNN)) //im mittleren Spielfeld liegt nicht NNNN
                            allTilesNNNNBorderRRRR = false;
                    }
                } else { //aktueller Spielstein ist null
                    allTilesNNNNBorderRRRR = false;
                }
            }
        }
        assertTrue(allTilesNNNNBorderRRRR);
    }


    //String[][] calcNeededHoles(int, int) int

    /**
     * String[][] calcNeededHoles(int, int) int
     * 1
     * ob die richtige Anzahl an benoetigten Lochsteinen errechnet wird
     */
    @Test
    void test_calcNeededHoles_1_4x4(){
        int neededHoles = GameField.calcNeededHoles(6, 6);

        assertEquals(0, neededHoles);
    }

    /**
     * String[][] calcNeededHoles(int, int) int
     * 2
     * ob die richtige Anzahl an benoetigten Lochsteinen errechnet wird
     */
    @Test
    void test_calcNeededHoles_2_5x5(){
        int neededHoles = GameField.calcNeededHoles(7, 7);

        assertEquals(1, neededHoles);
    }

    /**
     * c calcNeededHoles(int, int) int
     * 3
     * ob die richtige Anzahl an benoetigten Lochsteinen errechnet wird
     */
    @Test
    void test_calcNeededHoles_3_6x5(){
        int neededHoles = GameField.calcNeededHoles(8, 7);

        assertEquals(6, neededHoles);
    }

    /**
     * String[][] calcNeededHoles(int, int) int
     * 3
     * ob die richtige Anzahl an benoetigten Lochsteinen errechnet wird
     */
    @Test
    void test_calcNeededHoles_3_6x6(){
        int neededHoles = GameField.calcNeededHoles(8, 8);

        assertEquals(12, neededHoles);
    }


    //placeGameFieldHoles() void

    /**
     * placeGameFieldHoles() void
     * 1
     * ob die richtige Anzahl an Lochsteinen an den richtigen Positionen liegt
     */
    @Test
    void test_placeGameFieldHoles_1_4x4(){
        GameField gameField = new GameField(4, 4);
        gameField.placeGameFieldEmptyBorderRed(true); //Rand und Platzhalter setzen
        assertNotEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //noch keine Lochsteine

        gameField.placeGameFieldHoles(); //Lochsteine automatisch setzen (keine da Spielfeld zu klein)

        assertNotEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //hier sollte keiner sein
    }

    /**
     * placeGameFieldHoles() void
     * 2
     * ob die richtige Anzahl an Lochsteinen an den richtigen Positionen liegt
     */
    @Test
    void test_placeGameFieldHoles_2_5x5(){
        GameField gameField = new GameField(5, 5);
        gameField.placeGameFieldEmptyBorderRed(true); //Rand und Platzhalter setzen
        assertNotEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //noch keine Lochsteine

        gameField.placeGameFieldHoles(); //Lochsteine automatisch setzen (einer)

        assertEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //Lochstein ueberpruefen

        assertNotEquals(TileNames.HHHH, gameField.getTile(2, 1).getTileName()); //hier sollte keiner sein
    }

    /**
     * placeGameFieldHoles() void
     * 2
     * ob die richtige Anzahl an Lochsteinen an den richtigen Positionen liegt
     */
    @Test
    void test_placeGameFieldHoles_2_6x5(){
        GameField gameField = new GameField(6, 5);
        gameField.placeGameFieldEmptyBorderRed(true); //Rand und Platzhalter setzen
        assertNotEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //noch keine Lochsteine

        gameField.placeGameFieldHoles(); //Lochsteine automatisch setzen (einer)

        //Reihe 1
        assertEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(2, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(3, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(4, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(5, 1).getTileName()); //Lochstein ueberpruefen

        //Reihe 2
        assertEquals(TileNames.HHHH, gameField.getTile(1, 2).getTileName()); //Lochstein ueberpruefen

        assertNotEquals(TileNames.HHHH, gameField.getTile(3, 2).getTileName()); //hier sollte keiner sein
    }

    /**
     * placeGameFieldHoles() void
     * 2
     * ob die richtige Anzahl an Lochsteinen an den richtigen Positionen liegt
     */
    @Test
    void test_placeGameFieldHoles_3_6x6(){
        GameField gameField = new GameField(6, 6);
        gameField.placeGameFieldEmptyBorderRed(true); //Rand und Platzhalter setzen
        assertNotEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //noch keine Lochsteine

        gameField.placeGameFieldHoles(); //Lochsteine automatisch setzen (einer)

        //Reihe 1
        assertEquals(TileNames.HHHH, gameField.getTile(1, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(2, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(3, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(4, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(5, 1).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(6, 1).getTileName()); //Lochstein ueberpruefen

        //Reihe 2
        assertEquals(TileNames.HHHH, gameField.getTile(1, 2).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(2, 2).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(3, 2).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(4, 2).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(5, 2).getTileName()); //Lochstein ueberpruefen
        assertEquals(TileNames.HHHH, gameField.getTile(6, 2).getTileName()); //Lochstein ueberpruefen

        //Reihe 3
        assertNotEquals(TileNames.HHHH, gameField.getTile(1, 3).getTileName()); //hier sollte keiner mehr sein
    }


    //static translateFromSpielstandsdatei(String[][]) String[][]

    /**
     * static translateFromSpielstandsdatei(String[][]) String[][]
     * 1
     * ob die String Eingabe welche am Rand statt (YYYY, GGGG, RRRR) (Tilenames mit nur einem Buchstaben an der
     * richtigen Position) richtig uebersetzt wird in YYYY, GGGG, RRRR ...
     */
    @Test
    void test_translateFromSpielstandsdatei_1(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "RNNN", "YNNN", "YNNN", "NNNN"}};

        String[][] translatedGameField = GameField.translateFromSpielstandsdatei(gameFieldInput);

        assertEquals(translatedGameField[0][0], "NNNN"); //linke Ecke
        assertEquals(translatedGameField[0][1], "YYYY"); //oberen Rand
        assertEquals(translatedGameField[1][0], "YYYY"); //linker Rand
        assertEquals(translatedGameField[1][4], "YYYY"); //rechter Rand
        assertEquals(translatedGameField[4][1], "RRRR"); //unterer Rand

        assertEquals(translatedGameField[2][3], "RYYY"); //Spielstein bleibt richtig
    }


    //translateToSpielstandsdatei() String[][]

    /**
     * translateToSpielstandsdatei() String[][]
     * 1
     * ob der aktuelle Spielstand im richtigen geforderten Format zurueckgegeben wird (Randbeschriftung weicht von
     * meiner ab)
     */
    @Test
    void test_translateToSpielstandsdatei_1(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        String[][] gameFieldOutput = gameField.translateToSpielstandsdatei();

        assertArrayEquals(gameFieldInput, gameFieldOutput);
    }


    //layTile(int, int, Tile) boolean

    /**
     * layTile(int, int, Tile) boolean
     * 1
     * ob ein Spielstein korrekt gelegt werden kann
     */
    @Test
    void test_layTile_1_MiddleGameFieldFree(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);
        Tile shouldBeLaidTile = gameField.getTileByTileNamesIndexFromGameField(TileNames.GGRR.ordinal());
        boolean status = gameField.layTile(1,1, shouldBeLaidTile);

        assertTrue(status);
        assertEquals(shouldBeLaidTile, gameField.getTile(1, 1)); //Feld im Spielfeld passt
        assertFalse(gameField.getTiles().containsTile(shouldBeLaidTile)); //da gelegt Spielstein aus Auswahl geloescht
    }

    /**
     * layTile(int, int, Tile) boolean
     * 2
     * ob ein Spielstein korrekt gelegt werden kann
     */
    @Test
    void test_layTile_2_MiddleGameFieldNotFree(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        Tile beforelayTile = gameField.getTile(3,2);

        Tile shouldBeLaidTile = gameField.getTiles().getTileByTileNamesIndex(TileNames.GGRR.ordinal());
        boolean status = gameField.layTile(3,2, shouldBeLaidTile); //nicht legbar da Feld nicht frei

        assertFalse(status);
        assertEquals(beforelayTile, gameField.getTile(3, 2));
        assertTrue(gameField.getTiles().containsTile(shouldBeLaidTile)); //da nicht gelegt Spielstein aus Auswahl nicht
        // geloescht
    }

    /**
     * layTile(int, int, Tile) boolean
     * 3
     * ob ein Spielstein korrekt gelegt werden kann
     */
    @Test
    void test_layTile_3_BorderCompatible(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        Tile shouldBeLaidTile = new Tile(TileNames.GGGG);
        boolean status = gameField.layTile(1,0, shouldBeLaidTile); //nicht legbar da Feld nicht frei

        assertTrue(status);
        assertEquals(shouldBeLaidTile, gameField.getTile(1, 0));
        //nicht pruefen ob Spielstein auf Tiles geloscht, da die Randsteine nicht aus Tiles kommen und unwichtige
        // Refferenzen haben
    }

    /**
     * layTile(int, int, Tile) boolean
     * 4
     * ob ein Spielstein korrekt gelegt werden kann
     */
    @Test
    void test_layTile_4_NotBorderCompatible(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        Tile beforelayTile = gameField.getTile(1,0);

        Tile shouldBeLaidTile = new Tile(TileNames.RGYG);
        boolean status = gameField.layTile(1,0, shouldBeLaidTile); //nicht legbar da Feld nicht frei

        assertFalse(status);
        assertEquals(beforelayTile, gameField.getTile(1, 0));
    }


    //resetTile(int, int) void

    /**
     * resetTile(int, int) void
     * 1
     * ob ein Feld korrekt zurueckgesetzt wurde
     */
    @Test
    void test_resetTile_1_AlreadyResetted(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        Tile beforelayTile = gameField.getTile(1,1);

        gameField.resetTile(1,1); //resetted nichts da dort nichts liegt nur NNNN

        assertEquals(beforelayTile, gameField.getTile(1, 1));
    }

    /**
     * resetTile(int, int) void
     * 3
     * ob ein Spielstein korrekt gelegt werden kann
     */
    @Test
    void test_resetTile_2_CanBeResetted(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        Tile beforelayTile = gameField.getTile(3,2);

        assertFalse(gameField.getTiles().containsTile(beforelayTile)); //Spielstein nicht hierdrin da auf Spielfeld

        gameField.resetTile(3,2); //resetted nichts da dort nichts liegt nur NNNN

        assertNotEquals(beforelayTile, gameField.getTile(3, 2)); //alter Spielstein liegt dort nichtmehr
        assertEquals(TileNames.NNNN, gameField.getTile(3, 2).getTileName()); //resetted steht dort NNNN
        assertTrue(gameField.getTiles().containsTile(beforelayTile)); //Spielstein hier drin da nicht mehr auf Spielfeld
    }

    /**
     * resetTile(int, int) void
     * 3
     * ob ein Spielstein korrekt gelegt werden kann
     */
    @Test
    void test_resetTile_3_BorderNotResettable(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        Tile beforelayTile = gameField.getTile(0,1);

        gameField.resetTile(0,1); //resetted Rand zu nichts gelegt

        assertNotEquals(beforelayTile, gameField.getTile(0, 1)); //alter Spielstein liegt nicht da Rand nicht
        // resetted wird
        assertEquals(TileNames.NNNN, gameField.getTile(0,1).getTileName()); //wurde zu NNNN resetted
    }


    //cloneGameField() GameField

    /**
     * cloneGameField() GameField
     * 1
     * ob das Spielfeld korrekt kopiert wird (Spielsteine Nutzlasten identisch aber Refferenzen unterschiedlich
     */
    @Test
    void test_cloneGameField_1_3x3(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        //original GameField
        GameField gameField = new GameField(gameFieldInput);

        //geklontes GameField
        GameField clonedGameField = gameField.cloneGameField();

        //beide Felder vergleichen
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                //Fehler wenn
                if(!gameField.getTile(x, y).getTileName().
                        equals(clonedGameField.getTile(x, y).getTileName()) || //Name nicht identisch

                        !gameField.getTile(x, y).getRotation().
                                equals(clonedGameField.getTile(x, y).getRotation()) || //Rotation nicht identisch

                        gameField.getTile(x, y).
                                equals(clonedGameField.getTile(x, y))) //Refferenz identisch
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField() GameField
     * 2
     * ob das Spielfeld korrekt kopiert wird (Spielsteine Nutzlasten identisch aber Refferenzen unterschiedlich
     */
    @Test
    void test_cloneGameField_2_3x4(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        //original GameField
        GameField gameField = new GameField(gameFieldInput);

        //geklontes GameField
        GameField clonedGameField = gameField.cloneGameField();

        //Felder vergleichen
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                //Fehler wenn
                if(!gameField.getTile(x, y).getTileName().
                        equals(clonedGameField.getTile(x, y).getTileName()) || //Name nicht identisch

                        !gameField.getTile(x, y).getRotation().
                                equals(clonedGameField.getTile(x, y).getRotation()) || //Rotation nicht identisch

                        gameField.getTile(x, y).
                                equals(clonedGameField.getTile(x, y))) //Refferenz identisch
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField() GameField
     * 3
     * ob das Spielfeld korrekt kopiert wird (Spielsteine Nutzlasten identisch aber Refferenzen unterschiedlich
     */
    @Test
    void test_CloneGameField_3_5x5OneHole(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        //original GameField
        GameField gameField = new GameField(gameFieldInput);

        //geklontes GameField
        GameField clonedGameField = gameField.cloneGameField();

        //Felder vergleichen
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                //Fehler wenn
                if(!gameField.getTile(x, y).getTileName().
                        equals(clonedGameField.getTile(x, y).getTileName()) || //Name nicht identisch

                        !gameField.getTile(x, y).getRotation().
                                equals(clonedGameField.getTile(x, y).getRotation()) || //Rotation nicht identisch

                        gameField.getTile(x, y).
                                equals(clonedGameField.getTile(x, y))) //Refferenz identisch
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField() GameField
     * 4
     * ob das Spielfeld korrekt kopiert wird (Spielsteine Nutzlasten identisch aber Refferenzen unterschiedlich
     */
    @Test
    void test_cloneGameField_4_6x5SixHoles(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GGGR", "NNNN", "HHHH", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "YGYY", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "HHHH", "HHHH", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "HHHH", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        //original GameField
        GameField gameField = new GameField(gameFieldInput);

        //geklontes GameField
        GameField clonedGameField = gameField.cloneGameField();

        //Felder vergleichen
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                //Fehler wenn
                if(!gameField.getTile(x, y).getTileName().
                        equals(clonedGameField.getTile(x, y).getTileName()) || //Name nicht identisch

                        !gameField.getTile(x, y).getRotation().
                                equals(clonedGameField.getTile(x, y).getRotation()) || //Rotation nicht identisch

                        gameField.getTile(x, y).
                                equals(clonedGameField.getTile(x, y))) //Refferenz identisch
                    status = false;
            }
        }

        assertTrue(status);
    }

    /**
     * cloneGameField() GameField
     * 5
     * ob das Spielfeld korrekt kopiert wird (Spielsteine Nutzlasten identisch aber Refferenzen unterschiedlich
     */
    @Test
    void test_cloneGameField_5_6x6TwelveHoles(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNRN", "NNGN", "NNRN", "NNNN"},
                                      {"NGNN", "GGGR", "NNNN", "HHHH", "NNNN", "NNNN", "HHHH", "NNNG"},
                                      {"NRNN", "HHHH", "YGYY", "YGRY", "NNNN", "GGYY", "YGGG", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "HHHH", "HHHH", "HHHH", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "HHHH", "RRRR", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "HHHH", "NNNN", "NNNN", "NNNN", "HHHH", "HHHH", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        //original GameField
        GameField gameField = new GameField(gameFieldInput);

        //geklontes GameField
        GameField clonedGameField = gameField.cloneGameField();

        //Felder vergleichen
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileName().
                        equals(clonedGameField.getTile(x, y).getTileName()) || //Name nicht identisch

                        !gameField.getTile(x, y).getRotation().
                                equals(clonedGameField.getTile(x, y).getRotation()) || //Rotation nicht identisch

                        gameField.getTile(x, y).
                                equals(clonedGameField.getTile(x, y))) //Refferenz identisch
                    status = false;
            }
        }

        assertTrue(status);
    }


    //solveGameFieldAsCopy() GameField
        //getestet durch isGameFieldSolvable da es fuer ein Spielfeld mehrere Loesungen gibt


    //resetAllNotLaidTileRotation() void

    /**
     * resetAllNotLaidTileRotation() void
     * 1
     * ob alle nicht gelegten Spielsteine richtig bezueglich ihrer Rotation zurueckgesetzt werden
     */
    @Test
    void test_resetAllNotLaidTileRotation_1(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "YRYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);

        Rotation laidTileRotation = gameField.getTile(3, 2).getRotation();

        //Rotation von zwei nicht gelegten Spielsteinen aendern
        Tile rotatedNotLaidTile1 = gameField.getTiles().getTileByTileNamesIndex(TileNames.RYGR.ordinal());
        rotatedNotLaidTile1.rotateTile();
        Tile rotatedNotLaidTile2 = gameField.getTiles().getTileByTileNamesIndex(TileNames.GYYY.ordinal());
        rotatedNotLaidTile2.rotateTile();
        rotatedNotLaidTile2.rotateTile();

        gameField.resetAllNotLaidTileRotation(gameField.getTiles());

        boolean status = true;
        for(Tile currTile: gameField.getTiles().getTiles()){
            if (currTile.getRotation() != Rotation.R0) {
                status = false;
                break;
            }
        }
        assertTrue(status); //nicht gelegte Spielsteine sind alle R0
        assertEquals(laidTileRotation, gameField.getTile(3, 2).getRotation()); //Spielsteine auf dem Spielfeld
        // wurden nicht geandert
    }


    //goToNextFreeMiddleField(GameField, Position) boolean

    /**
     * goToNextFreeMiddleField(GameField, Position) boolean
     * 1
     * ob die richtige naechste freie Position gefunden wird, falls es eine gibt
     */
    @Test
    void test_goToNextFreeMiddleField_1_00to11(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(0,0); //von hier aus ist bei 1,1 das naechste freie Feld da dort kein Rand
        // mehr ist

        boolean nextPosition = gameField.goToNextFreeMiddleField(gameField, pos);

        assertEquals(1, pos.getX());
        assertEquals(1, pos.getY());
        assertTrue(nextPosition);
    }

    /**
     * goToNextFreeMiddleField(GameField, Position) boolean
     * 2
     * ob die richtige naechste freie Position gefunden wird, falls es eine gibt
     */
    @Test
    void test_goToNextFreeMiddleField_2_11to21(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //direkt hierneben ein naechstes freies mittleres Feld

        boolean nextPosition = gameField.goToNextFreeMiddleField(gameField, pos);

        assertEquals(2, pos.getX());
        assertEquals(1, pos.getY());
        assertTrue(nextPosition);
    }

    /**
     * goToNextFreeMiddleField(GameField, Position) boolean
     * 3
     * ob die richtige naechste freie Position gefunden wird, falls es eine gibt
     */
    @Test
    void test_goToNextFreeMiddleField_3_11to22(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "GGGG", "RRRR", "NNNY"},
                                      {"NYNN", "YYYY", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //von hier erst bei 2,2 wieder ein freies Feld

        boolean nextPosition = gameField.goToNextFreeMiddleField(gameField, pos);

        assertEquals(2, pos.getX());
        assertEquals(2, pos.getY());
        assertTrue(nextPosition);
    }

    /**
     * goToNextFreeMiddleField(GameField, Position) boolean
     * 4
     * ob die richtige naechste freie Position gefunden wird, falls es eine gibt
     */
    @Test
    void test_goToNextFreeMiddleField_4_NoNextField(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(3,3); //von hier keine freien mittleren Felder mehr

        boolean nextPosition = gameField.goToNextFreeMiddleField(gameField, pos);

        assertEquals(3, pos.getX());
        assertEquals(3, pos.getY());
        assertFalse(nextPosition);
    }


    //goToPreviousFreeMiddleField(GameField, Position) boolean

    /**
     * goToPreviousFreeMiddleField(GameField, Position) boolean
     * 1
     * ob die richtige naechste freie Position gefunden wird, falls es eine gibt
     */
    @Test
    void test_goToPreviousFreeMiddleField_1_44to33(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(4,4); //Feld ausserhalb mittlerem Spielfeld,
        // von hier ist (3,3) das naheste valide Feld

        boolean nextPosition = gameField.goToPreviousFreeMiddleField(gameField, pos);

        assertEquals(3, pos.getX());
        assertEquals(3, pos.getY());
        assertTrue(nextPosition);
    }

    /**
     * goToPreviousFreeMiddleField(GameField, Position) boolean
     * 2
     * ob die richtige naechste freie Position gefunden wird, falls es eine gibt
     */
    @Test
    void test_goToPreviousFreeMiddleField_2_33to23(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(3,3); //von hier das naheste vorherige freie mittlere Feld ist bei (2,3)

        boolean nextPosition = gameField.goToPreviousFreeMiddleField(gameField, pos);

        assertEquals(2, pos.getX());
        assertEquals(3, pos.getY());
        assertTrue(nextPosition);
    }

    @Test
    void test_goToPreviousFreeMiddleField_3_23to22(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "RRRR", "NNNY"},
                                      {"NYNN", "YYYY", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "GGGG", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(2,3); //von hier das naheste vorherige freie mittlere Feld ist bei (2,2)

        boolean nextPosition = gameField.goToPreviousFreeMiddleField(gameField, pos);

        assertEquals(2, pos.getX());
        assertEquals(2, pos.getY());
        assertTrue(nextPosition);
    }

    /**
     * goToPreviousFreeMiddleField(GameField, Position) boolean
     * 4
     * ob die richtige naechste freie Position gefunden wird, falls es eine gibt
     */
    @Test
    void test_goToPreviousFreeMiddleField_4_NoPreviousField(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //von hier aus kein vorheriges mittleres Feld

        boolean nextPosition = gameField.goToPreviousFreeMiddleField(gameField, pos);

        assertEquals(1, pos.getX());
        assertEquals(1, pos.getY());
        assertFalse(nextPosition);
    }


    //findNextMatchingTile(GameField, Position, Tile) Tile

    /**
     * findNextMatchingTile(GameField, Position, Tile) Tile
     * 1
     * ob der korrekte naechste Spielstein gefunden wird
     */
    @Test
    void test_findNextMatchingTile_1_AllAvailableNoStartTile(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //von hier aus kein vorheriges mittleres Feld

        Tile startTile = null; //da null wird jeder Spielstein durchprobiert
        Tile nextMatchingTile = gameField.findNextMatchingTile(gameField, pos, startTile);

        assertEquals("YYYY", nextMatchingTile.getTileNameStringWithRotation()); //der am schnellsten passende
    }

    /**
     * findNextMatchingTile(GameField, Position, Tile) Tile
     * 2
     * ob der korrekte naechste Spielstein gefunden wird
     */
    @Test
    void test_findNextMatchingTile_2_StartTile(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //von hier aus kein vorheriges mittleres Feld

        Tile startTile = gameField.getTiles().getTileByTileNamesIndex(TileNames.RYGR.ordinal()); //mit allen Rotationen
        // passt dieser Spielstein nicht
        Tile nextMatchingTile = gameField.findNextMatchingTile(gameField, pos, startTile);

        assertEquals("YRGY", nextMatchingTile.getTileNameStringWithRotation()); //der am schnellsten passende
    }

    /**
     * findNextMatchingTile(GameField, Position, Tile) Tile
     * 3
     * ob der korrekte naechste Spielstein gefunden wird
     */
    @Test
    void test_findNextMatchingTile_3_StartTileGetsRotated(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //von hier aus kein vorheriges mittleres Feld

        Tile startTile = gameField.getTiles().getTileByTileNamesIndex(TileNames.RYYY.ordinal()); //muss einmal rotiert
        // werden, damit dieser passt
        Tile nextMatchingTile = gameField.findNextMatchingTile(gameField, pos, startTile);

        assertEquals("YRYY", nextMatchingTile.getTileNameStringWithRotation()); //der am schnellsten passende;
        // ohne Rotationen: RYYY
    }

    /**
     * findNextMatchingTile(GameField, Position, Tile) Tile
     * 4
     * ob der korrekte naechste Spielstein gefunden wird
     */
    @Test
    void test_findNextMatchingTile_4_StartTileR3GetsSkipped(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //von hier aus kein vorheriges mittleres Feld

        Tile startTile = gameField.getTiles().getTileByNameWithRotation("YYYR"); //mit Rotationen:
        // RYYY YRYY YYRY YYYR
        //zwei Rotationen von dem aktuellen Zustand wuerde der Spielstein wieder passen aber da ab diesem angefangen
        // wird, wird dieser uebersprungen da die Rotation R3 nicht passt

        Tile nextMatchingTile = gameField.findNextMatchingTile(gameField, pos, startTile);

        assertEquals("YGYY", nextMatchingTile.getTileNameStringWithRotation()); //der am schnellsten passende;
        // ohne Rotationen GYYY
    }

    /**
     * findNextMatchingTile(GameField, Position, Tile) Tile
     * 4
     * ob der korrekte naechste Spielstein gefunden wird
     */
    @Test
    void test_findNextMatchingTile_5_NothingMatchingFound(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Position pos = new Position(1,1); //von hier aus kein vorheriges mittleres Feld

        Tile startTile = gameField.getTiles().getTileByNameWithRotation("RYGY"); //mit Rotationen:
        // RYYY YRYY YYRY YYYR
        //zwei Rotationen von dem aktuellen Zustand wuerde der Spielstein wieder passen aber da ab diesem angefangen
        // wird, wird dieser uebersprungen da die Rotation R3 nicht passt

        Tile nextMatchingTile = gameField.findNextMatchingTile(gameField, pos, startTile);

        assertNull(nextMatchingTile); //kein passender Spielstein gefunden
    }


    //getTileByTileNamesIndexFromGameField(int) Tile

    /**
     * getTileByTileNamesIndexFromGameField(int) Tile
     * 1
     * ob der richtige Spielstein auf dem mittleren Spielfeld gefunden wird
     */
    @Test
    void test_getTileByTileNamesIndexFromGameField_1_OnGamefield() {
        String[][] gameFieldInput = {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Tile laidTile = gameField.getTiles().getTileByNameWithRotation("RRYR");
        gameField.layTile(2, 2, laidTile);

        Tile searchedTile = gameField.getTileByTileNamesIndexFromGameField(laidTile.getTileIndex());

        assertEquals(laidTile, searchedTile);
    }

    /**
     * getTileByTileNamesIndexFromGameField(int) Tile
     * 2
     * ob der richtige Spielstein auf dem mittleren Spielfeld gefunden wird
     */
    @Test
    void test_getTileByTileNamesIndexFromGameField_2_NotOnGamefield() {
        String[][] gameFieldInput = {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        Tile laidTile = gameField.getTiles().getTileByNameWithRotation("RRYR");
        //gameField.layTile(2, 2, laidTile); nicht gelegt daher nicht auf Spielfeld

        Tile searchedTile = gameField.getTileByTileNamesIndexFromGameField(laidTile.getTileIndex());

        assertNull(searchedTile);
    }


    //checkIfPlainGameFieldSolvable() boolean

    /**
     * checkIfPlainGameFieldSolvable() boolean
     * 1
     * ob das leere Spielfeld von den Raendern her geloest werden kann
     */
    @Test
    void test_checkIfPlainGameFieldSolvable_1_NotSolvableWithNotPlain() {
        String[][] gameFieldInput = {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                     {"NYNN", "GGGG", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);

        //im nicht Plain Zustand nicht Loesbar, durch Feld (1,1)

        assertFalse(gameField.isGameFieldSolvable());
        assertTrue(gameField.checkIfPlainGameFieldSolvable());
    }


    //isGameFieldSolvable() boolean

    /**
     * isGameFieldSolvable() boolean
     * 1
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_1_5x5TrueOnlyYellow(){
        String[][] gameFieldInput =  {{"NNNN", "NNYN", "NNYN", "NNYN", "NNNN"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "RYYY", "NNNY"},
                                      {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * isGameFieldSolvable() boolean
     * 2
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_2_5x5True(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "NNNN", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                      {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * isGameFieldSolvable() boolean
     * 3
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_3_5x5True() {
        String[][] gameFieldInput = {{"NNNN", "NNYN", "NNYN", "NNGN", "NNNN"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NYNN", "NNNN", "NNNN", "NNNN", "NNNY"},
                                     {"NNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};

        GameField gameField = new GameField(gameFieldInput);
        assertTrue(gameField.isGameFieldSolvable());
    }

    /**
     * isGameFieldSolvable() boolean
     * 4
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_4_8x8True() {
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
     * isGameFieldSolvable() boolean
     * 5
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_5_8x8True() {
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
     * isGameFieldSolvable() boolean
     * 6
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_6_8x8SomeAlreadySolvedTrue() {
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
     * isGameFieldSolvable() boolean
     * 7
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_7_7x6AlreadyLaidFalse() {
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
     * isGameFieldSolvable() boolean
     * 8
     * ob die Methode korrekt ausgibt ob ein Spielfeld im aktuellen Zustand loesbar ist
     */
    @Test
    void test_isGameFieldSolvable_8_7x6AlreadyLaidFalse2() {
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


    //removeLayableMiddleGameFieldTiles() void
    @Test
    void test_removeLayableMiddleGameFieldTiles_1_NoHoles() {
        String[][] gameFieldInput = {
                {"NNNN","GGGG","GGGG","RRRR","RRRR","RRRR","NNNN"},
                {"RRRR","GGYY","NNNN","NNNN","NNNN","NNNN","GGGG"},
                {"GGGG","NNNN","NNNN","NNNN","NNNN","NNNN","GGGG"},
                {"RRRR","NNNN","NNNN","NNNN","NNNN","NNNN","RRRR"},
                {"GGGG","NNNN","NNNN","NNNN","NNNN","NNNN","GGGG"},
                {"NNNN","GGGG","GGGG","RRRR","GGGG","GGGG","NNNN"}
        };
        GameField gameField = new GameField(gameFieldInput);
        gameField.removeLayableMiddleGameFieldTiles();

        String[][] gameFieldEmptyInput = {
                {"NNNN","GGGG","GGGG","RRRR","RRRR","RRRR","NNNN"},
                {"RRRR","NNNN","NNNN","NNNN","NNNN","NNNN","GGGG"},
                {"GGGG","NNNN","NNNN","NNNN","NNNN","NNNN","GGGG"},
                {"RRRR","NNNN","NNNN","NNNN","NNNN","NNNN","RRRR"},
                {"GGGG","NNNN","NNNN","NNNN","NNNN","NNNN","GGGG"},
                {"NNNN","GGGG","GGGG","RRRR","GGGG","GGGG","NNNN"}
        };

        GameField emptyInputtedGameField = new GameField(gameFieldEmptyInput);

        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameStringWithRotation().equals(
                        emptyInputtedGameField.getTile(x, y).getTileNameStringWithRotation()))
                    status = false;
            }
        }
        assertTrue(status);
    }
}