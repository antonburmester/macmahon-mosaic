package logic;

import java.io.File;

/**
 * Interface, welches die Logik nutzt, um der Oberflaeche (GUI) etwas mitzuteilen.
 *
 * @author Anton Burmester
 */
public interface GUIConnector {

    /**
     * Methode welche die Spielfeld grossen Bedienung ein oder ausblendet
     */
    void displayEditorControls(boolean displayControls);

    /**
     * Methode welche ein bestehendes Spiel spielbar macht oder nicht ueber die rechte Spielstein Auswahl
     * @param status ob die rechte GridPane (Auswahl) zugreifbar sein soll
     */
    void setDisableTileSelection(boolean status);

    /**
     * Methode welche das mittlere Spielfeld deaktiviert oder aktiviert
     * @param status ob das mittlere Spielfeld GridPane zugreifbar sein soll
     */
    void setDisableGameField(boolean status);

    /**
     * Methode welche die GridPane fuellt
     * @param gameField das Spielfeld
     */
    void displayGameFieldTiles(GameField gameField);

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param tiles die verfuegbaren Spielsteine
     */
    void displaySelectionTiles(Tiles tiles);

    /**
     * Methode welche Spielsteine falls sie falsch gelegt wurden rot umrandet und dies wieder rueckgaengig machen kann
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @param mark ob sie markiert werden sollen
     * @param middleGridPane ob es sich um die mittlere GridPane handelt oder die rechte
     */
    void highlightTileNotMatching(int xIndex, int yIndex, boolean mark, boolean middleGridPane);

    /**
     * Methode welche einen Spielstein aus der rechten Spielsteinauswahl auf das Spielfeld legt
     * @param targetX die Breitenkoordinate
     * @param targetY die Hoehenkoordinate
     * @param tileIndex der Index des zu bewegenden Spielsteins
     */
    void moveTileSelectionToGameField(int targetX, int targetY, int tileIndex);

    /**
     * Methode welche einen Spielstein vom Spielfeld in die rechte Spielsteinauswahl bewegt
     * @param startX die Breitenkoordinate des Startfelds
     * @param startY die Hoehenkoordinate des Startfelds
     * @param targetX die Breitenkoordinate des Zielfelds
     * @param targetY die Hoehenkoordinate des Zielfelds
     */
    void moveTileGameFieldToGameField(int startX, int startY, int targetX, int targetY);

    /**
     * Methode welche einen Spielstein vom Spielfeld in die rechte Spielsteinauswahl bewegt
     * @param tileIndex der Index des zu bewegenden Spielsteins
     */
    void moveTileGameFieldToSelection(int tileIndex);

    /**
     * Methode welche einen Spielstein rotiert
     * @param tileIndex der Index des zu rotierenden Spielsteins
     * @param rotation die Rotation
     */
    void rotateTile(int tileIndex, int rotation);

    /**
     * Methode welche die Randfarbe setzt
     * @param targetX die Breitenkoordinate
     * @param targetY die Hoehenkoordinate
     * @param color die Farbe
     */
    void setBorderColor(int targetX, int targetY, TileNames color);

    /**
     * Graphisches Dateisystem des Betriebssystems zum erstellen einer neuen Datei oder selektieren von einer
     * @param selectFile ob eine Datei gesucht werden soll oder erstellt werden soll
     * @return die Datei samt Dateipfad
     */
    File openFileChooser(boolean selectFile);

    /**
     * Methode welche alle noetigen Grafik Methoden buendelt zum Anzeigen eines Spiels und aller noetigen Elemente
     * abgesehen von der platzierung der Spielsteine
     * @param width die Breite des Spielfelds
     * @param height die Hoehe des Spielfelds
     */
    void setupGUI(int width, int height);

    /**
     * Methode welche graphisch die Spielsteine der Spielsteinauswahl hinzufuegt und das graphische Spielfeld mit
     * Spielsteinen fuellt
     * @param tiles die Spielsteine mit welchen die Spielsteinauswahl gefuellt werden soll
     * @param gameField das Spielfeld welches als Refferenz dient wie das graphische Spielfeld gefuellt werden soll
     */
    void updateTiles(Tiles tiles, GameField gameField);
}