package logic;

/**
 * FakeGUI damit die Game Klasse auch in den Tests genutzt werden kann ohne Probleme
 * @author Anton Burmester
 */
public class FakeGUI implements GUIConnector{


    /**
     * Methode die Listener auf eine StackPane setzten und diese in die Zellen der GridPane einfuegen
     *
     * @param game      Spiel Instanz aus welcher die Methoden kommen um die Bewegung eines Spielsteins der Logik mitzuteilen
     * @param gameField das Spielfeld
     */
    @Override
    public void addListenerToMiddleGamefField(Game game, GameField gameField) {

    }

    /**
     * Methode welche die GridPane fuellt
     *
     * @param game      die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param gameField das Spielfeld
     */
    @Override
    public void displayGameFieldTiles(Game game, GameField gameField) {

    }

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     *
     * @param game      die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param tiles die verfuegbaren Spielsteine
     */
    @Override
    public void displayNotUsedTiles(Game game, Tiles tiles) {

    }

    /**
     * Methode welche den Rand des Spielfelds anzeigt
     *
     * @param game      die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param gameField das Spielfeld
     */
    @Override
    public void displayBorder(Game game, GameField gameField) {

    }

    /**
     * Methode welche die Editor Elemente in der rechten GridPane Auswahl anzeigt
     *
     * @param game      die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param withHoles ob in das Spielfeld auch Loecher sollen (wenn nicht wird der Loch Spielstein nicht angezeigt)
     */
    @Override
    public void fillRightGridPaneWithEditorPieces(Game game, boolean withHoles) {

    }

    /**
     * Methode welche alle benoetigten Loecher Objekte in Form einer gefaerbten StackPane initialisiert und sie der
     * holeStackPanes Menge hinzufuegt
     */
    @Override
    public void loadHolesStackPanes() {

    }
}
