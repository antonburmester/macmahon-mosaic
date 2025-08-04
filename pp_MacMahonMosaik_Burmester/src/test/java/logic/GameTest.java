package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Klasse welche mithilfe von JUnit die Game Klasse testet
 * @author Anton Burmester
 */

public class GameTest {

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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertTrue(game.isGameFieldSolvable());
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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertTrue(game.isGameFieldSolvable());
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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertTrue(game.isGameFieldSolvable());
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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertTrue(game.isGameFieldSolvable());
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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertTrue(game.isGameFieldSolvable());
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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertTrue(game.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 7x6 Feld
     * True
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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertFalse(game.isGameFieldSolvable());
    }

    /**
     * ob die isGameFieldSolvable Methode funktioniert
     * 7x6 Feld
     * True
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

        Game game = new Game(new FakeGUI(), gameFieldInput);
        System.out.println(game.getGameField().toString());
        assertFalse(game.isGameFieldSolvable());
    }
}
