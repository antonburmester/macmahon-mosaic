package logic;

/**
 * FakeGUI damit die Game Klasse auch in den Tests genutzt werden kann ohne Probleme
 * @author Anton Burmester
 */
public class FakeGUI implements GUIConnector{
    /**
     * Methode welche Form der GridPane anzeigt
     *
     * @param gameField das Spielfeld
     */
    @Override
    public void updateGridPaneFormat(GameField gameField) {

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
     * @param gameTiles die verfuegbaren Spielsteine
     */
    @Override
    public void displayNotUsedTiles(Game game, GameTiles gameTiles) {

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
