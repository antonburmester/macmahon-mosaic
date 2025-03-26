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
}
