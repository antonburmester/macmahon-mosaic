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
    public void test1_AlleNachbarfelderBelegt(){
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
    public void test1_AmRandUndAlleNachbarfelderBelegt(){
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
    public void test1_InEinerEckeDieAnderenZellenNochNichtBelegt(){
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
    public void test1_AngrenzendAnEinLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                                      {"NRNN", "HHHH", "NNNN", "HHHH", "NNNR"},
                                      {"NGNN", "NNNN", "HHHH", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.layTile(2, 2, new Tile("YYRR"));
        assertTrue(status);
    }

    /**
     * Passt das Teil in einer Zelle zu den Nachbarn?
     * test2
     */

    /**
     * alle Nachbarfelder belegt
     */
    @Test
    public void test2_AlleNachbarfelderBelegt(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "YGRG", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.isGameFieldTileMatching(2, 2, false);
        assertTrue(status);
    }

    /**
     * am Rand und alle Nachbarfelder belegt
     */
    @Test
    public void test2_AmRandUndAlleNachbarfelderBelegt(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "YGRG", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.isGameFieldTileMatching(1, 1, false);
        assertTrue(status);
    }

    /**
     * in einer Ecke, die anderen Zellen noch nicht belegt
     */
    @Test
    public void test2_InEinerEckeDieAnderenZellenNochNichtBelegt(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GGRG", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "NNNN", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.isGameFieldTileMatching(1, 1, true);
        assertTrue(status);
    }

    /**
     * in einer Ecke, die anderen Zellen noch nicht belegt
     */
    @Test
    public void test2_InEinerEckeDieAnderenZellenNochNichtBelegt_NNichtAkzeptiert(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                {"NGNN", "GGRG", "NNNN", "NNNN", "NNNG"},
                {"NRNN", "NNNN", "NNNN", "NNNN", "NNNR"},
                {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.isGameFieldTileMatching(1, 1, false);
        assertFalse(status);
    }

    /**
     * angrenzend an ein Loch
     */
    @Test
    public void test2_AngrenzendAnEinLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "HHHH", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "YGRG", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
        boolean status = gameField.isGameFieldTileMatching(1, 1, false);
        assertTrue(status);
    }


}
