package logic;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * fuer den Zwischenstand geforderte Tests
 */
public class pubTests {

    /**
     *
     * Passt das Mosaik-Teil in eine bestimmte Zelle im Feld?
     * test1
     *
     */

    /**
     * alle Nachbarfelder belegt
     */
    @Test
    public void test1AlleNachbarfelderBelegt(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "NNNN", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.layTile(2, 2, new Tile("YGRG"));
        assertTrue(status);
    }


    /**
     * am Rand und alle Nachbarfelder belegt
     */
    @Test
    public void test1AmRandUndAlleNachbarfelderBelegt(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "YGRG", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.layTile(1, 1, new Tile("GRYG"));
        assertTrue(status);
    }


    /**
     * in einer Ecke, die anderen Zellen noch nicht belegt
     */
    @Test
    public void test1InEinerEckeDieAnderenZellenNochNichtBelegt(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "NNNN", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.layTile(1, 1, new Tile("YYRR"));
        assertTrue(status);
    }

    /**
     * angrenzend an ein Loch
     */
    @Test
    public void test1AngrenzendAnEinLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "HHHH", "NNNR"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.layTile(2, 2, new Tile("YYRR"));
        assertTrue(status);
    }

}
