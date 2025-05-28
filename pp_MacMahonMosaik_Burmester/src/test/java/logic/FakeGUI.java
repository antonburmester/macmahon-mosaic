package logic;

/**
 * FakeGUI damit die Game Klasse auch in den Tests genutzt werden kann ohne Probleme
 * @author Anton Burmester
 */
public class FakeGUI implements GUIConnector{

    /**
     * Methode welche die GridPane fuellt
     * @param gameField das Spielfeld
     */
    @Override
    public void displayGameFieldTiles(GameField gameField) {

    }

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param tiles die verfuegbaren Spielsteine
     */
    @Override
    public void displayNotUsedTiles(Tiles tiles) {

    }

    /**
     * Methode welche alle benoetigten Loecher Objekte in Form einer gefaerbten StackPane initialisiert und sie der
     * holeStackPanes Menge hinzufuegt
     */
    @Override
    public void loadHolesStackPanes() {

    }
}
