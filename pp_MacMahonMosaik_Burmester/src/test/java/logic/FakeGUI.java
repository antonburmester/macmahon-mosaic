package logic;

/**
 * FakeGUI damit die Game Klasse auch in den Tests genutzt werden kann ohne Probleme
 * @author Anton Burmester
 */
public class FakeGUI implements GUIConnector{
    boolean isEditorMode = false; //Nutzlast ob EditorMode aktiv ist, da wenn es eine GUI gibt in den Tests, man nicht
    // darauf zugreifen kann, ob der EditorMode aktiv ist oder nicht

    /**
     * Methode welche die Spielfeld grossen Bedienung ein oder ausblendet
     */
    @Override
    public void displayEditorControls(boolean displayControls){
        isEditorMode = displayControls;
    }

    /**
     * Methode welche zurueckgibt, ob der EditorMode gerade aktiv ist oder nicht
     * @return ob der Editormode aktiv ist
     */
    @Override
    public boolean isEditorMode(){
        return(isEditorMode);
    }

    /**
     * Methode welche ein bestehendes Spiel spielbar macht oder nicht ueber die rechte Spielstein Auswahl
     *
     * @param status ob die rechte GridPane (Auswahl) zugreifbar sein soll
     */
    @Override
    public void setDisableTileSelection(boolean status) {

    }

    /**
     * Methode welche das mittlere Spielfeld deaktiviert oder aktiviert
     * @param status ob das mittlere Spielfeld GridPane zugreifbar sein soll
     */
    @Override
    public void setDisableGameField(boolean status){

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
    public void displaySelectionTiles(Tiles tiles) {

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

    /**
     * Methode welche einen Spielstein aus der rechten Spielsteinauswahl auf das Spielfeld legt
     *
     * @param targetX   die Breitenkoordinate
     * @param targetY   die Hoehenkoordinate
     * @param tileIndex der Index des zu bewegenden Spielsteins
     */
    @Override
    public void moveTileSelectionToGameField(int targetX, int targetY, int tileIndex) {

    }

    /**
     * Methode welche einen Spielstein vom Spielfeld in die rechte Spielsteinauswahl bewegt
     * @param startX die Breitenkoordinate des Startfelds
     * @param startY die Hoehenkoordinate des Startfelds
     * @param targetX die Breitenkoordinate des Zielfelds
     * @param targetY die Hoehenkoordinate des Zielfelds
     */
    @Override
    public void moveTileGameFieldToGameField(int startX, int startY, int targetX, int targetY){

    }

    /**
     * Methode welche einen Spielstein vom Spielfeld in die rechte Spielsteinauswahl bewegt
     *
     * @param tileIndex der Index des zu bewegenden Spielsteins
     */
    @Override
    public void moveTileGameFieldToSelection(int tileIndex) {

    }

    /**
     * Methode welche einen Spielstein rotiert
     *
     * @param tileIndex der Index des zu rotierenden Spielsteins
     * @param rotation  die Rotation
     */
    @Override
    public void rotateTile(int tileIndex, int rotation) {

    }

    /**
     * Methode welche die Randfarbe setzt
     * @param targetX die Breitenkoordinate
     * @param targetY die Hoehenkoordinate
     * @param color die Farbe
     */
    @Override
    public void setBorderColor(int targetX, int targetY, TileNames color){

    }
}
