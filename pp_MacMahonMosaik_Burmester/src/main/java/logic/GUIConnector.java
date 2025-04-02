package logic;

/**
 * Interface, welches die Logik nutzt, um der Oberfläche (GUI) etwas mitzuteilen.
 *
 * @author Anton Burmester
 */
public interface GUIConnector {
    /**
     * Methode welche Form der GridPane anzeigt
     * @param gameField das Spielfeld
     */
    void updateGridPaneFormat(GameField gameField);

    /**
     * Methode welche die GridPane fuellt
     * @param gameField das Spielfeld
     */
    void displayGameFieldTiles(Game game, GameField gameField);

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param tiles die verfuegbaren Spielsteine
     */
    void displayNotUsedTiles(Game game, Tiles tiles);
}