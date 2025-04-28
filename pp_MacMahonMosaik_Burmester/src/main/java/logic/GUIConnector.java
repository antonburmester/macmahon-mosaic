package logic;

/**
 * Interface, welches die Logik nutzt, um der Oberflaeche (GUI) etwas mitzuteilen.
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
     * @param game die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param gameField das Spielfeld
     */
    void displayGameFieldTiles(Game game, GameField gameField);

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param game die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param tiles die verfuegbaren Spielsteine
     */
    void displayNotUsedTiles(Game game, Tiles tiles);

    /**
     * Methode welche den Rand des Spielfelds anzeigt
     * @param game die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param gameField das Spielfeld
     */
    void displayBorder(Game game, GameField gameField);

    /**
     * Methode welche die Editor Elemente in der rechten GridPane Auswahl anzeigt
     * @param game die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param withHoles ob in das Spielfeld auch Loecher sollen (wenn nicht wird der Loch Spielstein nicht angezeigt)
     */
    void fillRightGridPaneWithEditorPieces(Game game, boolean withHoles);

    /**
     * Methode welche alle benoetigten Loecher Objekte in Form einer gefaerbten StackPane initialisiert und sie der
     * holeStackPanes Menge hinzufuegt
     */
    void loadHolesStackPanes();
}