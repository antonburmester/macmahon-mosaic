package logic;

/**
 * FakeGUI damit die Game Klasse auch in den Tests genutzt werden kann ohne Probleme
 * @author Anton Burmester
 */
public class FakeGUI implements GUIConnector{

    /**
     * Methode welche ein bestehendes Spiel spielbar macht oder nicht ueber die rechte Spielstein Auswahl
     *
     * @param status ob die rechte GridPane (Auswahl) zugreifbar sein soll
     */
    @Override
    public void setDisableRightGridPane(boolean status) {

    }

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
     * Methode welche Spielsteine falls sie falsch gelegt wurden rot umrandet und dies wieder rueckgaengig machen kann
     *
     * @param xIndex         Breitenindex
     * @param yIndex         Hoehenindex
     * @param mark           ob sie markiert werden sollen
     * @param middleGridPane ob es sich um die mittlere GridPane handelt oder die rechte
     */
    @Override
    public void highlightTileNotMatching(int xIndex, int yIndex, boolean mark, boolean middleGridPane) {

    }
}
