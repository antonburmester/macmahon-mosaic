package logic;

/**
 * Interface, welches die Logik nutzt, um der Oberflaeche (GUI) etwas mitzuteilen.
 *
 * @author Anton Burmester
 */
public interface GUIConnector {

    /**
     * Methode welche ein bestehendes Spiel spielbar macht oder nicht ueber die rechte Spielstein Auswahl
     * @param status ob die rechte GridPane (Auswahl) zugreifbar sein soll
     */
    void setDisableRightGridPane(boolean status);

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
     * Methode welche Spielsteine falls sie falsch gelegt wurden rot umrandet und dies wieder rueckgaengig machen kann
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @param mark ob sie markiert werden sollen
     * @param middleGridPane ob es sich um die mittlere GridPane handelt oder die rechte
     */
    void highlightTileNotMatching(int xIndex, int yIndex, boolean mark, boolean middleGridPane);
}