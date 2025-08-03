package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die GameField Klasse testet
 * @author Anton Burmester
 */

public class GameFieldTest {

    /**
     * ob das Feld in der richtigen Groeße initialisiert wird
     */
    @Test
    public void testSimpleConstructor(){
        GameField gameField = new GameField(2,6, true);
        //4 da 2+2 = 4 und 8 da 6+2 = 8 (fuer die Raender)
        assertTrue(gameField.getGameField().length == 4 && gameField.getGameField()[0].length == 8);
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
        Tiles gameFieldTiles = new Tiles();
        Tiles holeTiles = new Tiles(1);
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

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
        Tiles gameFieldTiles = new Tiles();
        Tiles holeTiles = new Tiles(1);
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

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
        Tiles gameFieldTiles = new Tiles();
        Tiles holeTiles = new Tiles(1);
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

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
        Tiles gameFieldTiles = new Tiles();
        Tiles holeTiles = new Tiles(0); //Loecher manuell gesetzt
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

        //Cloned GameField
        Tiles cloneGameFieldTiles = new Tiles();
        Tiles clonedHoleTiles = new Tiles(0);
        GameField clonedGameField = gameField.cloneGameField(gameFieldTiles, holeTiles, cloneGameFieldTiles,
                clonedHoleTiles);

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameWithRotation().equals(clonedGameField.getTile(x, y).getTileNameWithRotation()))
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
        Tiles gameFieldTiles = new Tiles();
        Tiles holeTiles = new Tiles(0); //Loecher manuell gesetzt
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

        //Cloned GameField
        Tiles cloneGameFieldTiles = new Tiles();
        Tiles clonedHoleTiles = new Tiles(0);
        GameField clonedGameField = gameField.cloneGameField(gameFieldTiles, holeTiles, cloneGameFieldTiles,
                clonedHoleTiles);

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameWithRotation().equals(clonedGameField.getTile(x, y).getTileNameWithRotation()))
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
        Tiles gameFieldTiles = new Tiles();
        Tiles holeTiles = new Tiles(1); //Loecher manuell gesetzt
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

        //Cloned GameField
        Tiles cloneGameFieldTiles = new Tiles();
        Tiles clonedHoleTiles = new Tiles(1);
        GameField clonedGameField = gameField.cloneGameField(gameFieldTiles, holeTiles, cloneGameFieldTiles,
                clonedHoleTiles);

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameWithRotation().equals(clonedGameField.getTile(x, y).getTileNameWithRotation()))
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
        Tiles holeTiles = new Tiles(6); //Loecher manuell gesetzt
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

        //Cloned GameField
        Tiles cloneGameFieldTiles = new Tiles();
        Tiles clonedHoleTiles = new Tiles(6);
        GameField clonedGameField = gameField.cloneGameField(gameFieldTiles, holeTiles, cloneGameFieldTiles,
                clonedHoleTiles);

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameWithRotation().equals(clonedGameField.getTile(x, y).getTileNameWithRotation()))
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
        Tiles holeTiles = new Tiles(12); //Loecher manuell gesetzt
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles, holeTiles);

        //Cloned GameField
        Tiles cloneGameFieldTiles = new Tiles();
        Tiles clonedHoleTiles = new Tiles(12);
        GameField clonedGameField = gameField.cloneGameField(gameFieldTiles, holeTiles, cloneGameFieldTiles,
                clonedHoleTiles);

        //compare those two
        boolean status = true;
        for(int y = 0; y < gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < gameField.getGameFieldWidth(); x++){
                if(!gameField.getTile(x, y).getTileNameWithRotation().equals(clonedGameField.getTile(x, y).getTileNameWithRotation()))
                    status = false;
            }
        }

        assertTrue(status);
    }
}
