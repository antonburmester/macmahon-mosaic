package gui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import logic.*;

/**
 * Klasse durch welche die Logik veraenderungen der GUI durchfuehren kann.
 * Die JavaFXGUI wird vom UserInterfaceController erzeugt und als Parameter an die Logik uebergeben.
 *
 * @author Anton Burmester
 */
public class JavaFXGUI implements GUIConnector {

    private final GridPane gridPane;

    private final GridPane rightGridPane;

    private final VBox editorControls; //Nutzlast der Flaeche der Spielfeld groessen Bedienung

    private final ImageView[] imageViews;

    private final StackPane[] holeStackPanes;

    //die Kennungen der verschiedenen Drag and Drop Objekte
    static final String ID_PIECE = "0";
    static final String ID_HOLE = "1";
    static final String ID_BORDER = "2";
    static final String ID_Overlay_RED = "3";

    static final int BORDER_SIZE_GRAPHICAL = 2;
    static final double NOT_LAID_TILE_SIZE = 80;

    static final int MAX_HOLES_AMOUNT = 12;

    //die HEX Farbkennungen des Randes damit die Randfarben den Spielsteinen gleichen
    static final String COLOR_HEX_CODE_GREEN = "#007F0E;";
    static final String COLOR_HEX_CODE_YELLOW = "#FFD800;";
    static final String COLOR_HEX_CODE_RED = "#B60000;";

    /**
     * Konstruktor welcher diese Klasse initialisiert
     * @param gridPane das mittlere Spielfeld
     * @param rightGridPane die rechte GridPane in welcher die noch nicht gelegten Spielsteine sind
     * @param editorControls Flaeche der Spielfeld groessen Bedienung
     * @param imageViews die Bilder mit Listenern initialisiert in der UserInterfaceController Klasse
     * @param holeStackPanes die Loecher Stackpanes mit Listenern initialisiert in der UserInterfaceController Klasse
     */
    public JavaFXGUI(GridPane gridPane, GridPane rightGridPane,
                     VBox editorControls, ImageView[] imageViews, StackPane[] holeStackPanes){
        this.gridPane = gridPane;
        this.rightGridPane = rightGridPane;
        this.editorControls = editorControls;

        this.imageViews = imageViews;
        this.holeStackPanes = holeStackPanes;
    }

    /**
     * Methode welche ein bestehendes Spiel spielbar macht oder nicht ueber die rechte Spielstein Auswahl
     * @param status ob die rechte GridPane (Auswahl) zugreifbar sein soll
     */
    public void setDisableTileSelection(boolean status){
        this.rightGridPane.setDisable(status);
        this.rightGridPane.setOpacity(!status ? 1 : 0.7);
    }

    /**
     * Methode welche das mittlere Spielfeld deaktiviert oder aktiviert
     * @param status ob das mittlere Spielfeld GridPane zugreifbar sein soll
     */
    public void setDisableGameField(boolean status){
        this.gridPane.setDisable(status);
        this.gridPane.setOpacity(!status ? 1 : 0.7);
    }

    /**
     * Methode welche die Spielfeld grossen Bedienung ein oder ausblendet
     */
    public void displayEditorControls(boolean displayControls){
        editorControls.setVisible(displayControls); //macht die Spielfeldeingaben (Breite,Hoehe,Button)
        // sichtbar/unsichtbar
        editorControls.setManaged(displayControls); //entfernt den Platz wenn unsichtbar und nimmt ihn ein wenn sichtbar
    }

    /**
     * Methode welche das mittlere Spielfeld (GridPane) mit den Spielsteinen und Lochsteinen fuellt und den Rand faerbt
     * @param gameField das Spielfeld
     */
    public void displayGameFieldTiles(GameField gameField) {
        this.removeAllPiecesAndColouringsButLeaveSlots(this.gridPane); //die Slots der GridPane von

        // ImageViews (Spielsteine) und StackPanes (Loecher) bereinigen
        for (int y = 0; y < gameField.getGameFieldHeight(); y++) { //jedes Feld bezueglich Hoehe
            for (int x = 0; x < gameField.getGameFieldWidth(); x++) { //jedes Feld bezueglich Breite
                Tile currTile = gameField.getTile(x, y);
                StackPane slotStackPane = this.getGridPaneCell(x, y, this.gridPane); //der Slot des jeweiligen Feldes

                //Groeße die die StackPane (Loch) oder des ImageView Element (Bild) bekommen soll
                double slotWidth = slotStackPane.getWidth() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2; //*2 da Rand links
                // und rechts
                double slotHeight = slotStackPane.getHeight() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2;//*2 da Rand oben
                // und unten

                if(gameField.isFieldMiddleGamefield(x, y)) { //mittleres Spielfeld ohne Rand
                    if (currTile.getTileName().equals(TileNames.HHHH)) { //ein Loch
                        // Zuerst explizit alle holeStackPanes vom Parent trennen
                        for (StackPane currStackPane : this.holeStackPanes) { //durchlaeuft jede holeStackPane
                            if (currStackPane.getParent() == null) { //wenn holeStackPane nirgendwo gelegt wurde
                                currStackPane.setPrefSize(slotWidth, slotHeight); //StackPane an Feld groesse anpassen
                                slotStackPane.getChildren().add(currStackPane); //die holeStackPane dem slot hinzufuegen
                                break; //schleife beenden, da Spielstein gefunden wurde
                            }
                        }
                    } else if(!currTile.getTileName().equals(TileNames.NNNN)) { //ein Bild da es kein Loch und kein
                        // NNNN ist
                        int tileEnumIndex = TileNames.valueOf(currTile.getTileNameString()).ordinal();//der Index des
                        // Bilds
                        ImageView imageView = this.imageViews[tileEnumIndex]; //das Bild als ImageView
                        //Bild von seinem vorherigen Ort (Parent) loesen falls es gebunden ist
                        StackPane parent = (StackPane) imageView.getParent();
                        if(parent != null) parent.getChildren().remove(imageView);
                        imageView.setRotate(Rotation.rotationToDegrees(currTile.getRotation())); //Bild rotieren bis
                        // richtige Rotation erreicht wurde
                        //groesse des Bildes anpassen
                        imageView.setFitWidth(slotWidth);
                        imageView.setFitHeight(slotHeight);
                        slotStackPane.getChildren().add(imageView); //Bild dem Hintergrund hinzufuegen
                    }
                } else { //Rand
                    //initiales Setzen des Randes (falls ein bestehendes Spiel geladen wurde)
                    if(!gameField.isFieldEdge(x, y)) { //kein Eckstueck
                        //switch Statement welches je nach Randstein den Rand faerbt
                        String initialCellStyle = switch (currTile.getTileName()) {
                            case TileNames.GGGG ->
                                    "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL +
                                            "; -fx-background-color: " + COLOR_HEX_CODE_GREEN + ";"; //Gruen
                            case TileNames.YYYY ->
                                    "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL +
                                            "; -fx-background-color: " + COLOR_HEX_CODE_YELLOW + ";"; //Gelb
                            case TileNames.RRRR ->
                                    "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL +
                                            "; -fx-background-color: " + COLOR_HEX_CODE_RED + ";"; //rot
                            //leere Zelle am Rand
                            default -> "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL
                                    + ";";
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
    public void displaySelectionTiles(Tiles tiles) {
        this.removeAllPiecesAndColouringsButLeaveSlots(this.rightGridPane); //Slots der rechten GridPane von ImageViews
        // (Spielsteine) bereinigen
        //Abstand zwischen den Spalten und Reihen
        this.rightGridPane.setHgap(10);
        this.rightGridPane.setVgap(10);

        int col, row; //aktuelle Spalte und Reihe der Auswahl der Spielsteine

        for(int i = 0; i < tiles.getTileCount(); i++){ //durchlaeuft jeden Spielstein der Tiles Klasse
            Tile currTile = tiles.getTileByArrayIndex(i);

            //da NNNN und HHHH nicht legbar sind, sollen sie auch nicht in der Auswahl auftauchen
            if(!(currTile.getTileName() == TileNames.NNNN || currTile.getTileName() == TileNames.HHHH)) {

                //einheitliche Reihenfolge durch TileNames
                col = currTile.getTileName().ordinal() % 3; //Modulo 3 da es 3 Spalten gibt und so die richtige erkannt wird
                row = currTile.getTileName().ordinal() / 3; //DIV 3 da in jede Spalte 3 Steine passen und mann so in die
                // richtige Spalte kommt.

                String tileName = currTile.getTileNameString();
                if (currTile.isNormalGameTile()) { //wenn nicht NNNN und HHHH da diese kein Bild haben
                    int tileEnumIndex = TileNames.valueOf(tileName).ordinal();
                    ImageView imageView = this.imageViews[tileEnumIndex]; //das ImageView des aktuellen Spielsteins
                    imageView.setFitWidth(JavaFXGUI.NOT_LAID_TILE_SIZE);
                    imageView.setFitHeight(JavaFXGUI.NOT_LAID_TILE_SIZE);
                    imageView.setRotate(Rotation.rotationToDegrees(currTile.getRotation())); //Bild rotieren bis
                    // richtige Rotation erreicht wurde
                    //Bild vom bisherigen Slot loesen falls es schonmal lag
                    if(imageView.getParent() != null){
                        StackPane slotStackPane = (StackPane) imageView.getParent();
                        slotStackPane.getChildren().remove(imageView);
                    }
                    StackPane slotStackPane = this.getGridPaneCell(col, row, this.rightGridPane); //Slot des Feldes
                    slotStackPane.getChildren().add(imageView);
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
    private void removeAllPiecesAndColouringsButLeaveSlots(GridPane gridPane) {
        if (gridPane == this.gridPane) { //nur bei der mittleren GridPane die loecher loesen
            // Zuerst explizit alle holeStackPanes vom Parent trennen
            for (StackPane hole : this.holeStackPanes) { //alle Loecher durchlaufen
                Parent parent = hole.getParent(); //die Eltern also der Ort wo das Loch dran gebunden ist
                if (parent instanceof StackPane stackPane) { //falls Loch lag
                    stackPane.getChildren().remove(hole); //Loch entfernen vom vorherigen Ort
                }
            }
        }

        int width = gridPane.getColumnCount();
        int height = gridPane.getRowCount();
        for (int y = 0; y < height; y++) { //Start bei 1 und Ende bei Groeße - 1
            // da der Rand nicht beachtet wird
            for (int x = 0; x < width; x++) { //Start bei 1 und Ende bei Groeße - 1

                StackPane slotStackPane = this.getGridPaneCell(x, y, gridPane);
                if (!GameField.isFieldEdge(x, y, width, height)) {
                    slotStackPane.setStyle("-fx-background-color: transparent; -fx-border-color: black;" +
                            "-fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL + ";"); //den Stil zuruecksetzen
                }
                slotStackPane.getChildren().clear(); //alles entfernen (Loecher und Bilder)
            }
        }
    }

    /**
     * Methode welche Spielsteine falls sie falsch gelegt wurden rot umrandet und dies wieder rueckgaengig machen kann
     * @param xIndex Breitenindex
     * @param yIndex Hoehenindex
     * @param mark ob sie markiert werden sollen
     * @param middleGridPane ob es sich um die mittlere GridPane handelt oder die rechte
     */
    public void highlightTileNotMatching(int xIndex, int yIndex, boolean mark, boolean middleGridPane){
        GridPane gridPane = middleGridPane ? this.gridPane : this.rightGridPane;
        StackPane gridPaneCell = this.getGridPaneCell(xIndex, yIndex, gridPane);

        if (mark) { //Spielfeld soll hervorgehoben werden
            boolean isOverlayAlreadyThere = gridPaneCell.getChildren().stream()
                    .anyMatch(n -> JavaFXGUI.ID_Overlay_RED.equals(n.getUserData())); //ob es schon ein Overlay gibt
            if (!isOverlayAlreadyThere) { //es gibt noch kein Overlay
                Pane overlay = new Pane();
                overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); //Overlay Farbe: schwarz, Deckkraft: 50%
                overlay.setUserData(JavaFXGUI.ID_Overlay_RED);
                overlay.prefWidthProperty().bind(gridPaneCell.widthProperty());
                overlay.prefHeightProperty().bind(gridPaneCell.heightProperty());
                overlay.setMouseTransparent(true);
                gridPaneCell.getChildren().add(overlay);
            }
        } else {
            //das Overlay entfernen wenn das Feld keine Farbliche Grenzprobleme hat
            gridPaneCell.getChildren().removeIf(n -> JavaFXGUI.ID_Overlay_RED.equals(n.getUserData()));
        }
    }

    /**
     * Methode welche einen Spielstein aus der rechten Spielsteinauswahl auf das Spielfeld legt
     * @param targetX die Breitenkoordinate
     * @param targetY die Hoehenkoordinate
     * @param tileIndex der Index des zu bewegenden Spielsteins
     */
    public void moveTileSelectionToGameField(int targetX, int targetY, int tileIndex){
        ImageView movedTile = this.imageViews[tileIndex]; //der zu verschiebende Spielstein

        //den Spielstein aus der Spielsteinauswahl loeschen
        StackPane movedTileSource = (StackPane) movedTile.getParent();
        movedTileSource.getChildren().remove(movedTile);

        //den Spielstein in das Spielfeld an der gewuenschten Position einfuegen
        StackPane movedTileTarget = this.getGridPaneCell(targetX, targetY, this.gridPane);

        //Groeße des gedroppten StackPane oder ImageView Elements anpassen
        double slotWidth = movedTileTarget.getWidth() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2; //*2 da Rand links
        // und rechts
        double slotHeight = movedTileTarget.getHeight() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2;//*2 da Rand oben
        // und unten
        movedTile.setFitWidth(slotWidth);
        movedTile.setFitHeight(slotHeight);

        movedTileTarget.getChildren().add(movedTile);
    }

    /**
     * Methode welche einen Spielstein vom Spielfeld in ein anderes Feld bewegt
     * @param startX die Breitenkoordinate des Startfelds
     * @param startY die Hoehenkoordinate des Startfelds
     * @param targetX die Breitenkoordinate des Zielfelds
     * @param targetY die Hoehenkoordinate des Zielfelds
     */
    public void moveTileGameFieldToGameField(int startX, int startY, int targetX, int targetY){
        Node movedTile = this.getGridPaneCell(startX, startY, this.gridPane).getChildren().getFirst(); //der Spielstein
        // oder der Lochstein welcher bewegt werden soll

        //den Spielstein aus der Spielsteinauswahl loeschen
        StackPane movedTileSource = (StackPane) movedTile.getParent();
        movedTileSource.getChildren().remove(movedTile);

        //den Spielstein in das Spielfeld an der gewuenschten Position einfuegen
        StackPane movedTileTarget = this.getGridPaneCell(targetX, targetY, this.gridPane);

        //Groeße des gedroppten StackPane oder ImageView Elements anpassen
        double slotWidth = movedTileTarget.getWidth() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2; //*2 da Rand links
        // und rechts
        double slotHeight = movedTileTarget.getHeight() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2;//*2 da Rand oben
        // und unten
        if (movedTile instanceof StackPane stackPane) { //Loch StackPane
            stackPane.setPrefSize(slotWidth, slotHeight);
        } else {
            ImageView imageView = (ImageView) movedTile;//Spielstein Bild
            imageView.setFitWidth(slotWidth);
            imageView.setFitHeight(slotHeight);
        }

        movedTileTarget.getChildren().add(movedTile);
    }

    /**
     * Methode welche einen Spielstein vom Spielfeld in die rechte Spielsteinauswahl bewegt
     * @param tileIndex der Index des zu bewegenden Spielsteins
     */
    public void moveTileGameFieldToSelection(int tileIndex){
        ImageView movedTile = this.imageViews[tileIndex]; //der zu verschiebende Spielstein

        //den Spielstein aus der Spielsteinauswahl loeschen
        StackPane movedTileSource = (StackPane) movedTile.getParent();
        movedTileSource.getChildren().remove(movedTile);

        //einheitliche Reihenfolge durch TileNames
        int col = tileIndex % 3; //Modulo 3 da es 3 Spalten gibt und so die richtige erkannt wird
        int row = tileIndex / 3; //DIV 3 da in jede Spalte 3 Steine passen und mann so in die
        // richtige Spalte kommt.

        //den Spielstein in die Spielsteinauswahl an der richtigen Position einfuegen (bestimmt durch TileNames Order)
        StackPane movedTileTarget = this.getGridPaneCell(col, row, this.rightGridPane);

        //Groeße des gedroppten StackPane oder ImageView Elements anpassen
        if(movedTileTarget != null) {
            double slotWidth = movedTileTarget.getWidth() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2; //*2 da Rand links
            // und rechts
            double slotHeight = movedTileTarget.getHeight() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2;//*2 da Rand oben
            // und unten
            movedTile.setFitWidth(slotWidth);
            movedTile.setFitHeight(slotHeight);

            movedTile.setRotate(0); //Rotation zuruecksetzen
            movedTileTarget.getChildren().add(movedTile); //Spielstein zuruecklegen
        }
    }

    /**
     * Methode welche einen Spielstein rotiert
     * @param tileIndex der Index des zu rotierenden Spielsteins
     * @param rotation die Rotation
     */
    public void rotateTile(int tileIndex, int rotation){
        ImageView movedTile = this.imageViews[tileIndex]; //der zu verschiebende Spielstein
        movedTile.setRotate(rotation);
    }

    /**
     * Methode welche die Randfarbe setzt
     * @param targetX die Breitenkoordinate
     * @param targetY die Hoehenkoordinate
     * @param color die Farbe
     */
    public void setBorderColor(int targetX, int targetY, TileNames color){
        Pane targetPane = this.getGridPaneCell(targetX, targetY, this.gridPane);

        String stringColor;
        switch(color){
            case TileNames.GGGG ->
                    stringColor = JavaFXGUI.COLOR_HEX_CODE_GREEN; //Gruen
            case TileNames.YYYY ->
                    stringColor = JavaFXGUI.COLOR_HEX_CODE_YELLOW; //Gelb
            case TileNames.RRRR ->
                    stringColor = JavaFXGUI.COLOR_HEX_CODE_RED; //rot
            //leere Zelle am Rand
            default -> stringColor = "transparent";
        }

        targetPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL +
                "; -fx-background-color: " + stringColor + ";");
    }

    /**
     * Methode welche Meldungen Graphisch anzeigt
     * @param customException die Meldung welche ausgegeben werden soll
     */
    public void showCustomException(CustomException customException){
        ErrorMessageHandler.showError(customException);
    }

    /**
     * Methode welche zurueckgibt, ob der EditorMode gerade aktiv ist oder nicht
     * @return ob der Editormode aktiv ist
     */
    public boolean isEditorMode(){
        return(this.editorControls.isManaged());
    }

}