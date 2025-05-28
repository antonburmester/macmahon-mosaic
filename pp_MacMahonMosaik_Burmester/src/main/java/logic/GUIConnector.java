package logic;

/**
 * Interface, welches die Logik nutzt, um der Oberflaeche (GUI) etwas mitzuteilen.
 *
 * @author Anton Burmester
 */
public interface GUIConnector {

    /**
     * Methode welche die GridPane fuellt
     * @param gameField das Spielfeld
     */
    void displayGameFieldTiles(GameField gameField);

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param tiles die verfuegbaren Spielsteine
     */
    void displayNotUsedTiles(Tiles tiles);

    /**
     * Methode welche alle benoetigten Loecher Objekte in Form einer gefaerbten StackPane initialisiert und sie der
     * holeStackPanes Menge hinzufuegt
     */
    void loadHolesStackPanes();
}