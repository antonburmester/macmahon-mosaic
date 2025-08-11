package logic;

import org.junit.jupiter.api.Test;

public class GameDataTest {

    //static saveGame(String[][], File) void

    /**
     * static saveGame(String[][], File) void
     * 1
     * ob ein bestehendes Spiel korrekt gespeichert wird
     */
    @Test
    void test_saveGame_1_(){
        String[][] gameFieldInput =  {{"NNNN", "GGYY", "NNGN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "YGRY", "NNNN", "GGYY", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NGNN", "YYRY", "HHHH", "NNNN", "RRRR", "NNNN", "NNNG"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "YNNN", "YNNN", "NNNN"}};
        GameField gameField = new GameField(gameFieldInput);
    }
}