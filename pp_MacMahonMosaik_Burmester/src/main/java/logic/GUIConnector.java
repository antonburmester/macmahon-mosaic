package logic;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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
     * Methode welche das mittlere Spielfeld deaktiviert oder aktiviert
     * @param status ob das mittlere Spielfeld GridPane zugreifbar sein soll
     */
    void setDisableMiddleGridPane(boolean status);

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

    /**
     * Methode welche einen Spielstein aus der rechten Spielsteinauswahl auf das Spielfeld legt
     * @param targetX die Breitenkoordinate
     * @param targetY die Hoehenkoordinate
     * @param tileIndex der Index des zu bewegenden Spielsteins
     */
    void moveTileSelectionToGameField(int targetX, int targetY, int tileIndex);

    /**
     * Methode welche einen Spielstein vom Spielfeld in die rechte Spielsteinauswahl bewegt
     * @param targetX die Breitenkoordinate
     * @param targetY die Hoehenkoordinate
     * @param tileIndex der Index des zu bewegenden Spielsteins
     * @param isHoleTile ob der zu bewegenden Spielstein ein normaler Spielstein ist oder einen Lochstein
     */
    void moveTileGameFieldToGameField(int targetX, int targetY, int tileIndex, boolean isHoleTile);

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
}