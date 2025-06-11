package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import logic.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Klasse durch welche die Logik veraenderungen der GUI durchfuehren kann.
 * Die JavaFXGUI wird vom UserInterfaceController erzeugt und als Parameter an die Logik uebergeben.
 *
 * @author Anton Burmester
 */
public class JavaFXGUI implements GUIConnector {

    private final GridPane gridPane;

    private final Pane centerPane;

    private final BorderPane borderPane;

    private final GridPane rightGridPane;

    private ImageView[] imageViews;

    private StackPane[] holeStackPanes;

    //die Kennungen der verschiedenen Drag and Drop Objekte
    public static final String ID_PIECE = "0";
    public static final String ID_HOLE = "1";
    public static final String ID_BORDER = "2";

    public static final int BORDER_SIZE = 2;
    public static final double NOT_LAID_TILE_SIZE = 80;

    //die HEX Farbkennungen des Randes damit die Randfarben den Spielsteinen gleichen
    public static final String COLOR_HEX_CODE_GREEN = "#007F0E;";
    public static final String COLOR_HEX_CODE_YELLOW = "#FFD800;";
    public static final String COLOR_HEX_CODE_RED = "#B60000;";

    /**
     * Konstruktor welcher diese Klasse initialisiert
     * @param borderPane der Gesamte Hintergrund
     * @param centerPane der hintergrund der GridPane in der Mitte
     * @param gridPane die FXML Instanz
     * @param rightGridPane die rechte GridPane in welcher die noch nicht gelegten Spielsteine sind
     * @param imageViews die Bilder mit Listenern initialisiert in der UserInterfaceController Klasse
     * @param holeStackPanes die Loecher Stackpanes mit Listenern initialisiert in der UserInterfaceController Klasse
     */
    public JavaFXGUI(BorderPane borderPane, Pane centerPane, GridPane gridPane, GridPane rightGridPane,
                     ImageView[] imageViews, StackPane[] holeStackPanes){
        this.borderPane = borderPane;
        this.centerPane = centerPane;
        this.gridPane = gridPane;
        this.rightGridPane = rightGridPane;

        this.imageViews = imageViews;
        this.holeStackPanes = holeStackPanes;
    }

    /**
     * Methode welche das mittlere Spielfeld (GridPane) mit den Spielsteinen und Lochsteinen fuellt und den Rand faerbt
     * @param gameField das Spielfeld
     */
    public void displayGameFieldTiles(GameField gameField) {
        for (int y = 0; y < gameField.getGameField().length; y++) { //jedes Feld bezueglich Hoehe
            for (int x = 0; x < gameField.getGameField()[y].length; x++) { //jedes Feld bezueglich Breite
                Tile currTile = gameField.getTile(x, y);
                StackPane slotStackPane = this.getGridPaneCell(x, y, this.gridPane); //der Slot des jeweiligen Feldes

                if(gameField.isFieldMiddleGamefield(x, y)) { //mittleres Spielfeld ohne Rand
                    if (currTile.getTile().equals(TileNames.HHHH)) { //ein Loch
                        for (StackPane currStackPane : this.holeStackPanes) { //durchlaeuft jede holeStackPane
                            if (currStackPane.getParent() == null) { //wenn holeStackPane nirgendwo gelegt wurde
                                slotStackPane.getChildren().add(currStackPane); //die holeStackPane dem slot hinzufuegen
                            }
                        }
                    } else if(!currTile.getTile().equals(TileNames.NNNN)) { //ein Bild da es kein Loch und kein NNNN ist
                        int tileEnumIndex = TileNames.valueOf(currTile.getTileString()).ordinal();//der Index des Bilds
                        ImageView tileImageView = this.imageViews[tileEnumIndex]; //das Bild als ImageView
                        tileImageView.setRotate(currTile.getRotation()); //Bild rotieren falls rotiert
                        slotStackPane.getChildren().add(tileImageView); //Bild dem Hintergrund hinzufuegen
                    }
                } else { //Rand
                    //initiales Setzen des Randes (falls ein bestehendes Spiel geladen wurde)
                    if(!gameField.isFieldEdge(x, y)) { //kein Eckstueck
                        //switch Statement welches je nach Randstein den Rand faerbt
                        String initialCellStyle = switch (currTile.getTile()) {
                            case TileNames.GGGG ->
                                    "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE +
                                            "; -fx-background-color: " + COLOR_HEX_CODE_GREEN + ";"; //Gruen
                            case TileNames.YYYY ->
                                    "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE +
                                            "; -fx-background-color: " + COLOR_HEX_CODE_YELLOW + ";"; //Gelb
                            case TileNames.RRRR ->
                                    "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE +
                                            "; -fx-background-color: " + COLOR_HEX_CODE_RED + ";"; //rot
                            //leere Zelle am Rand
                            default -> "-fx-border-color: black; -fx-border-width: " + + JavaFXGUI.BORDER_SIZE + ";";
                        };
                        slotStackPane.setStyle(initialCellStyle);
                    }
                }
            }
        }
    }

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param tiles die verfuegbaren Spielsteine
     */
    public void displayNotUsedTiles(Tiles tiles) {
        //Abstand zwischen den Spalten und Reihen
        this.rightGridPane.setHgap(10);
        this.rightGridPane.setVgap(10);

        int col = 0, row = 0; //aktuelle Spalte und Reihe der Auswahl der Spielsteine

        for (Tile currTile : tiles.getTiles()) { //durchlaeuft jeden Spielstein

            //da NNNN und HHHH nicht legbar sind, sollen sie auch nicht in der Auswahl auftauchen
            if(!(!currTile.getIsLaid() &&
                    (currTile.getTile() == TileNames.NNNN || currTile.getTile() == TileNames.HHHH))) {

                if (!currTile.getIsLaid()) {
                    String tileName = currTile.getTileString();
                    if (currTile.isNormalGameTile()) { //wenn nicht NNNN und HHHH da diese kein Bild haben
                        int tileEnumIndex = TileNames.valueOf(tileName).ordinal();
                        ImageView imageView = this.imageViews[tileEnumIndex]; //das ImageView des aktuellen Spielsteins
                        imageView.setFitWidth(JavaFXGUI.NOT_LAID_TILE_SIZE);
                        imageView.setFitHeight(JavaFXGUI.NOT_LAID_TILE_SIZE);
                        imageView.setRotate(currTile.getRotation());
                        //slotStackPane.getChildren().add(imageView); //ImageView der Stackpane hinzufuegen
                        //Bild dem Slot der GridPane hinzufuegen
                        if(imageView.getParent() != null){
                            StackPane slotStackPane = (StackPane) imageView.getParent();
                            slotStackPane.getChildren().remove(imageView);
                        }
                        this.getGridPaneCell(col, row, this.rightGridPane).getChildren().add(imageView);
                    }
                }
                //Verwaltung fuer Reihen und Spalten
                col++; //nach jedem durchlauf in die naechste Zeile
                if (col == 3) { // Nach 3 Spalten neue Zeile beginnen
                    col = 0; // wieder in der obersten Reihe beginnen
                    row++;
                }
            }
        }
    }

    /**
     * Methode welche Anhand des imageViews Arrays welches alle ImageViews enthaehlt den Index des uebergebenen findet
     * sofern es sich beim Objekt um ein ImageView handelt
     * dasselbe auch mit den Loechern
     * nichts angegeben bekommt einfach immer den Index 25 da dieser dem Index von NNNN in TileNames entspricht
     * @param input das uebergebene Objekt zu welchem der Index gesucht wird
     * @return der Index des uebergebenen Objekts
     */
    int getTileIndex(Node input){
        int result = -1;
        if(input instanceof ImageView) { //wenn es sich beim uebergebenen Objekt um ein Bild handelt
            for (int i = 0; i < this.imageViews.length; i++) { //durchlaeuft jedes ImageView
                if (this.imageViews[i].equals(input)) { //wenn das aktuelle ImageView das uebergebene ist
                    result = i; //den Index speichern
                }
            }
        } else if(input instanceof StackPane) { //wenn es sich beim uebergebenen Objekt um ein Loch handelt
            for (int i = 0; i < this.holeStackPanes.length; i++) { //durchlaeuft jedes ImageView
                if (this.holeStackPanes[i].equals(input)) { //wenn das aktuelle ImageView das uebergebene ist
                    result = i; //den Index speichern
                }
            }
        } else {
            result = 25; //nicht angegeben
        }
        return(result);
    }

    /**
     * Methode welche die Instanz des Inhalts (StackPane) an einer Stelle in einer GridPane zurueckgibt
     * @param xIndex der Breitenindex
     * @param yIndex der Hoehenindex
     * @param gridPane die GridPane in welcher gesucht wird
     * @return die Instanz des StackPaneInhalts an der bestimmten Stelle
     */
    StackPane getGridPaneCell(int xIndex, int yIndex, GridPane gridPane){
        StackPane resultCell = null;
        for(Node currCellNode: gridPane.getChildren()){
            //da 0 als null zurueckgegeben wird, muss man es zu 0 aendern
            int xCoordinate = GridPane.getColumnIndex(currCellNode) != null ? GridPane.getColumnIndex(currCellNode) : 0;
            //da 0 als null zurueckgegeben wird, muss man es zu 0 aendern
            int yCoordinate = GridPane.getRowIndex(currCellNode) != null ? GridPane.getRowIndex(currCellNode) : 0;
            if(xCoordinate == xIndex && yCoordinate == yIndex){
                resultCell = (StackPane) currCellNode;
            }
        }
        return(resultCell);
    }

    /**
     * Methode welche alle Objekte die auf einem Slot Liegen entfernen
     * @param gridPane die GridPane welche gelehrt werden soll
     */
    void removeAllPiecesAndColouringsButLeaveSlots(GridPane gridPane){
        int width = gridPane.getColumnCount();
        int height = gridPane.getRowCount();
        for (int y = 0; y < height; y++) { //Start bei 1 und Ende bei Groeße - 1
            // da der Rand nicht beachtet wird
            for (int x = 0; x < width; x++) { //Start bei 1 und Ende bei Groeße - 1

                StackPane slotStackPane = this.getGridPaneCell(x, y, gridPane);
                if(!GameField.isFieldEdge(x, y, width, height)) {
                    slotStackPane.setStyle("-fx-background-color: transparent; -fx-border-color: black;" +
                            "-fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";"); //den Stil zuruecksetzen
                }
                ArrayList<Node> stackPaneChildren = new ArrayList<>(slotStackPane.getChildren()); //kopieren, weil man
                // ueber dieselbe Liste itterirt und objekte aus dieser loescht
                for(Node child: stackPaneChildren){
                    slotStackPane.getChildren().remove(child);
                }

            }
        }
    }
}