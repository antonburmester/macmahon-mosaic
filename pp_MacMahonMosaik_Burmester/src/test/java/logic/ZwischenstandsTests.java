package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * fuer den Zwischenstand geforderte Tests
 */
public class ZwischenstandsTests {

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

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
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.isGameFieldTileMatching(1, 1, false);
        assertTrue(status);
    }

    /**
     * Fertig-Pruefung
     * test3
     */

    /**
     * fuer das geloeste Feld aus obigem Beispiel
     * ohne Loch
     */
    @Test
    public void test3_FuerDasGeloesteFeldAusObigemBeispiel_OhneLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "YGRG", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.checkIfGameFieldSolved(false);
        assertTrue(status);
    }

    /**
     * fuer das geloeste Feld aus obigem Beispiel
     * mit Loch
     */
    @Test
    public void test3_FuerDasGeloesteFeldAusObigemBeispiel_MitLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "HHHH", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.checkIfGameFieldSolved(false);
        assertTrue(status);
    }

    /**
     * fuer das geloeste Feld aus obigem Beispiel
     * ohne Loch mit NNNN (nicht gefordert)
     * um zu pruefen ob acceptN geht also das pruefen aller Spielsteine abgesehen von nicht gelegten Feldern
     */
    @Test
    public void test3_FuerDasGeloesteFeldAusObigemBeispiel_OhneLoch_mitNNNN(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "NNNN", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "YGRG", "YRRG", "NNNR"},
                                      {"NGNN", "HHHH", "RYGY", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.checkIfGameFieldSolved(true);
        assertTrue(status);
    }

    /**
     * fuer das noch nicht fertig geloestes Feld (exampleFieldNearlySolved)
     * ohne Loch
     */
    @Test
    public void test3_FuerDasNochNichtFertigGeloestesSpielFeld_OhneLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "NNNN", "YRRG", "NNNR"},
                                      {"NGNN", "HHHH", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.checkIfGameFieldSolved(false);
        assertFalse(status);
    }

    /**
     * fuer das noch nicht fertig geloestes Feld (exampleFieldNearlySolved)
     * mit Loch
     */
    @Test
    public void test3_FuerDasNochNichtFertigGeloestesSpielFeld_MitLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "NNNN", "YRRG", "NNNR"},
                                      {"NGNN", "HHHH", "RYGY", "HHHH", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.checkIfGameFieldSolved(false);
        assertFalse(status);
    }

    /**
     * fuer das vollstaendig belegte Feld aber mit einem falsch platzierten Mosaik-Teil
     * ohne Loch
     */
    @Test
    public void test3_FuerDasVollstaendigBelegteFeldAberMitEinemFalschPlatziertenMosaikTeil_OhneLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "GYGR", "YRRG", "NNNR"}, //in der mitte Muesste YGRG sein aber
                                      {"NGNN", "HHHH", "RYGY", "RGYY", "NNNG"}, //dort ist YGRG 90* rotiert
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.checkIfGameFieldSolved(false);
        assertFalse(status);
    }

    /**
     * fuer das vollstaendig belegte Feld aber mit einem falsch platzierten Mosaik-Teil
     * mit Loch
     */
    @Test
    public void test3_FuerDasVollstaendigBelegteFeldAberMitEinemFalschPlatziertenMosaikTeil_MitLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "HHHH", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "HHHH", "GYGR", "YRRG", "NNNR"}, //in der mitte Muesste YGRG sein aber
                                      {"NGNN", "HHHH", "RYGY", "HHHH", "NNNG"}, //dort ist YGRG 90* rotiert
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Tiles gameFieldTiles = new Tiles();
        GameField gameField = new GameField(gameFieldInput, gameFieldTiles);

        boolean status = gameField.checkIfGameFieldSolved(false);
        assertFalse(status);
    }

    /**
     * Loesbarkeitspruefung
     * test 4
     */

    /**
     * Fuer ein leeres Feld wie im obigen Beispiel
     * ohne Loch
     */
    @Test
    public void test4_FuerEinLeeresFeldWieImObigenBeispiel_OhneLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "NNNN", "NNNN", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Game game = new Game(new FakeGUI(), gameFieldInput);
        boolean status = game.isGameFieldSolvable();
        assertTrue(status);
    }

    /**
     * Fuer ein leeres Feld wie im obigen Beispiel
     * mit Loch
     */
    @Test
    public void test4_FuerEinLeeresFeldWieImObigenBeispiel_MitLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NRNN", "NNNN", "HHHH", "NNNN", "NNNR"},
                                      {"NGNN", "NNNN", "NNNN", "NNNN", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Game game = new Game(new FakeGUI(), gameFieldInput); //schlaegt Fehl da im Game Konstruktor berechnet wird
        // wieviele Lochsteine es gibt, in diesem Fall sollte es 0 geben aber es ist trotzdem einer drin.
        // Es wird somit versucht einen Lochstein zu platzieren welcher gar nicht existent ist
        boolean status = game.isGameFieldSolvable();
        assertTrue(status);
    }

    /**
     * fuer ein fast geloestes Feld wie im obigen Beispiel
     * ohne Loch
     */
    @Test
    public void test4_FuerEinFastGeloestesFeldWieImObigenBeispiel_OhneLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "NNNN", "YRRG", "NNNR"},
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Game game = new Game(new FakeGUI(), gameFieldInput);
        boolean status = game.isGameFieldSolvable();
        assertTrue(status);
    }

    /**
     * fuer ein fast geloestes Feld wie im obigen Beispiel
     * mit Loch
     */
    @Test
    public void test4_FuerEinFastGeloestesFeldWieImObigenBeispiel_MitLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"}, //GRYG GGRY YGGR RYGG    GRYR RGRY YRGR RYRG   GGYR RGGY YRGG GYRG
                                      {"NRNN", "YGRR", "NNNN", "HHHH", "NNNR"}, //YGRR RYGR RRYG GRRY
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"}, //RYYG GRYY YGRY YYGR    RYGY YRYG GYRY YGYR   RGYY YRGY YYRG GYYR
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Game game = new Game(new FakeGUI(), gameFieldInput); //schlaegt Fehl da im Game Konstruktor berechnet wird
        // wieviele Lochsteine es gibt, in diesem Fall sollte es 0 geben aber es ist trotzdem einer drin.
        // Es wird somit versucht einen Lochstein zu platzieren welcher gar nicht existent ist
        boolean status = game.isGameFieldSolvable();
        assertTrue(status);
    }

    /**
     * fuer ein teilweise belegtes Feld mit einem falsch gesetzten Mosaik-Teil
     * ohne Loch
     */
    @Test
    public void test4_FuerEinTeilweiseBelegtesFeldMitEinemFalschGesetztenMosaikTeil_OhneLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "NNNN", "GYRR", "NNNR"}, //bei GYRR sollte sein: YRRG
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Game game = new Game(new FakeGUI(), gameFieldInput);
        boolean status = game.isGameFieldSolvable();
        assertTrue(status);
    }

    /**
     * fuer ein teilweise belegtes Feld mit einem falsch gesetzten Mosaik-Teil
     * Mit Loch
     */
    @Test
    public void test4_FuerEinTeilweiseBelegtesFeldMitEinemFalschGesetztenMosaikTeil_MitLoch(){
        String[][] gameFieldInput =  {{"NNNN", "NNGN", "NNGN", "NNGN", "NNNN"},
                                      {"NGNN", "GRYG", "GRYR", "GGYR", "NNNG"},
                                      {"NRNN", "YGRR", "HHHH", "GYRR", "NNNR"}, //bei GYRR sollte sein: YRRG
                                      {"NGNN", "RYYG", "RYGY", "RGYY", "NNNG"},
                                      {"NNNN", "YNNN", "GNNN", "YNNN", "NNNN"}};
        Game game = new Game(new FakeGUI(), gameFieldInput); //schlaegt Fehl da im Game Konstruktor berechnet wird
        // wieviele Lochsteine es gibt, in diesem Fall sollte es 0 geben aber es ist trotzdem einer drin.
        // Es wird somit versucht einen Lochstein zu platzieren welcher gar nicht existent ist TODO fragen ob so gewollt
        boolean status = game.isGameFieldSolvable();
        assertTrue(status);
    }

}
