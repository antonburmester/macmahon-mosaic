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
        GameField gameField = new GameField(2,6);
        //4 da 2+2 = 4 und 8 da 6+2 = 8 (fuer die Raender)
        assertTrue(gameField.getGameField().length == 4 && gameField.getGameField()[0].length == 8);
    }

    /**
     * testet ob ein mit Loch belegtes Feld ueberlegt werden kann
     */
    @Test
    public void testFeldSchonBelegtLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                {"NRNN", "HHHH", "HHHH", "YGRY", "NNNR"},
                {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.layTile(2, 2, new Tile("YYRR"));
        assertFalse(status);
    }

    /**
     * testet ob ein mit einem Spielstein belegtes Feld ueberlegt werden kann
     */
    @Test
    public void testFeldSchonBelegtSpielstein(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                {"NRNN", "HHHH", "YGRY", "YGRY", "NNNR"},
                {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.layTile(2, 2, new Tile("YYRR"));
        assertFalse(status);
    }
}
