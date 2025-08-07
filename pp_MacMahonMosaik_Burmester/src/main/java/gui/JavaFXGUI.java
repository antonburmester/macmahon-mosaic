package gui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.*;

import java.io.File;

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
     * @param borderPane der Gesamte Hintergrund des Fensters in welchem alle Elemente sind
     * @param centerPane der hintergrund der GridPane (Spielfeld) in der Mitte
     * @param gridPane das mittlere Spielfeld
     * @param rightGridPane die rechte GridPane in welcher die noch nicht gelegten Spielsteine sind
     * @param editorControls Flaeche der Spielfeld groessen Bedienung
     * @param imageViews die Bilder mit Listenern initialisiert in der UserInterfaceController Klasse
     * @param holeStackPanes die Loecher Stackpanes mit Listenern initialisiert in der UserInterfaceController Klasse
     */
    public JavaFXGUI(BorderPane borderPane, Pane centerPane, GridPane gridPane, GridPane rightGridPane,
                     VBox editorControls, ImageView[] imageViews, StackPane[] holeStackPanes){
        this.borderPane = borderPane;
        this.centerPane = centerPane;
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
        System.out.println();
        // ImageViews (Spielsteine) und StackPanes (Loecher) bereinigen
        for (int y = 0; y < gameField.getGameField().length; y++) { //jedes Feld bezueglich Hoehe
            for (int x = 0; x < gameField.getGameField()[y].length; x++) { //jedes Feld bezueglich Breite
                Tile currTile = gameField.getTile(x, y);
                StackPane slotStackPane = this.getGridPaneCell(x, y, this.gridPane); //der Slot des jeweiligen Feldes

                //Groeße die die StackPane (Loch) oder des ImageView Element (Bild) bekommen soll
                double slotWidth = slotStackPane.getWidth() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2; //*2 da Rand links
                // und rechts
                double slotHeight = slotStackPane.getHeight() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2;//*2 da Rand oben
                // und unten

                if(gameField.isFieldMiddleGamefield(x, y)) { //mittleres Spielfeld ohne Rand
                    if (currTile.getTile().equals(TileNames.HHHH)) { //ein Loch
                        // Zuerst explizit alle holeStackPanes vom Parent trennen
                        for (StackPane currStackPane : this.holeStackPanes) { //durchlaeuft jede holeStackPane
                            if (currStackPane.getParent() == null) { //wenn holeStackPane nirgendwo gelegt wurde
                                currStackPane.setPrefSize(slotWidth, slotHeight); //StackPane an Feld groesse anpassen
                                slotStackPane.getChildren().add(currStackPane); //die holeStackPane dem slot hinzufuegen
                                break; //schleife beenden, da Spielstein gefunden wurde
                            }
                        }
                    } else if(!currTile.getTile().equals(TileNames.NNNN)) { //ein Bild da es kein Loch und kein NNNN ist
                        int tileEnumIndex = TileNames.valueOf(currTile.getTileString()).ordinal();//der Index des Bilds
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
                        String initialCellStyle = switch (currTile.getTile()) {
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
                            default -> "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL + ";";
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

        int col = 0, row = 0; //aktuelle Spalte und Reihe der Auswahl der Spielsteine

        for (Tile currTile : tiles.getTiles()) { //durchlaeuft jeden Spielstein

            //da NNNN und HHHH nicht legbar sind, sollen sie auch nicht in der Auswahl auftauchen
            if(!(currTile.getTile() == TileNames.NNNN || currTile.getTile() == TileNames.HHHH)) {

                String tileName = currTile.getTileString();
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

        //den Spielstein in das Spielfeld an der gewuenschten Position einfuegen
        StackPane movedTileTarget = this.getGridPaneNextAvailabeField(this.rightGridPane);

        //Groeße des gedroppten StackPane oder ImageView Elements anpassen
        if(movedTileTarget != null) {
            double slotWidth = movedTileTarget.getWidth() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2; //*2 da Rand links
            // und rechts
            double slotHeight = movedTileTarget.getHeight() - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2;//*2 da Rand oben
            // und unten
            movedTile.setFitWidth(slotWidth);
            movedTile.setFitHeight(slotHeight);

            movedTileTarget.getChildren().add(movedTile);
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
     * Methode welche das naechste freie Feld einer GridPane ausgibt.
     * Im Aufbau hier bedeutet frei, das die StackPane (Hintergrund) eines Feldes keine children hat
     * @param gridPane die GridPane in welcher gesucht werden soll
     * @return das Feld oder null falls es keins mehr gibt
     */
    private StackPane getGridPaneNextAvailabeField(GridPane gridPane){
        for(int y = 0; y < gridPane.getRowCount(); y++){ //jede Hoehenkoordinate durchlaufen
            for(int x = 0; x < gridPane.getColumnCount(); x++){ //jede Breitenkoordinate durchlaufen
                StackPane currFieldSlot = this.getGridPaneCell(x, y, gridPane); //der aktuelle Slot (Background)
                if(currFieldSlot.getChildren().isEmpty()) return(currFieldSlot); //wenn Slot leer diesen zurueckgeben
            }
        }
        return(null);
    }

    /**
     * Graphisches Dateisystem des Betriebssystems zum erstellen einer neuen Datei oder selektieren von einer
     * @param selectFile ob eine Datei gesucht werden soll oder erstellt werden soll
     * @return die Datei samt Dateipfad
     */
    public File openFileChooser(boolean selectFile) {
        FileChooser fileChooser = new FileChooser();

        //Startverzeichnis je nach Betriebssystem setzen. Getestet auf Windows, deshalb koennte man bei den anderen
        // Betriebssystemen im Standardverzeichniss landen und nicht im gewuenschten
        File initialDirectory = null;
        String betriebssystemName = System.getProperty("os.name").toLowerCase();
        if (betriebssystemName.contains("win")) { //Windows
            initialDirectory = new File("pp_MacMahonMosaik_Burmester/src/main/resources/savedGames/");
        } else if(betriebssystemName.contains("mac")) { //Mac
            initialDirectory = new File("src/main/resources/savedGames/");
        }

        if (initialDirectory != null && initialDirectory.exists() && initialDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        //Filter für Dateityp .json
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        //Stage aus dem Event holen da hierrueber ein Fenster goeffnet wird:
        Stage stage = (Stage) this.centerPane.getScene().getWindow();

        //Dateiauswahl Fenster oeffnen
        File selectedFile;
        if (selectFile) {
            selectedFile = fileChooser.showOpenDialog(stage); //vorhandene Datei waehlen
        } else {
            selectedFile = fileChooser.showSaveDialog(stage); //neue Datei erstellen
        }
        return(selectedFile);
    }

    /**
     * Methode welche die Ecken der mittleren GridPane unsichtbar und nicht klickbar macht
     * Sollte das Spielfeld vergroeßert werden, werden die vorherigen Ecken wieder sichtbar und klickbar gemacht und
     * die neuen Ecken unsichtbar und nicht klickbar.
     */
    private void gameFieldGridPaneHandleEdges(){
        int xSize = this.gridPane.getColumnCount();
        int ySize = this.gridPane.getRowCount();
        for(int y = 0; y < ySize; y++) { //Hoehe durchlaufen
            for (int x = 0; x < xSize; x++) { //Breite durchlaufen
                StackPane slotStackPane = this.getGridPaneCell(x, y, this.gridPane);
                if(GameField.isFieldEdge(x, y, xSize, ySize)) { //Ecke
                    //Slot nicht sichtbar und nicht klickbar machen
                    slotStackPane.setVisible(false);
                    slotStackPane.setMouseTransparent(true);
                } else { //keine Ecke (genutzt falls das Spielfeld vergroeßert wird, da alte Ecken wieder normal werden)
                    //Slot sichtbar und klickbar machen
                    slotStackPane.setVisible(true);
                    slotStackPane.setMouseTransparent(false);
                }
            }
        }
    }

    /**
     * Methode welche die Form der GridPane anzeigt und aktualisiert falls Spielfeld vergroessert oder verkleinert wird
     * ohne bestehende Zeilen und Spalten falls es vergroessert wird zu loeschen.
     * @param newRowAmount die neue hoehe des Spielfelds
     * @param newColumsAmount die neue Breite des Spielfelds
     */
    public void updateGridPaneFormat(int newRowAmount, int newColumsAmount){
        int existingCols = this.gridPane.getColumnCount();
        int existingRows = this.gridPane.getRowCount();

        //die Differenz der bestehenden GridSize Breite zur neuen
        int widthGrowLoss = newColumsAmount - existingCols;
        //die Differenz der bestehenden GridSize Hoehe zur neuen
        int heigthGrowLoss = newRowAmount - existingRows;

        gridPane.setMinSize(0, 0); //minimalgroeße der GridPane

        double middleColWidthSizePercentage = 100 / (newColumsAmount - 1.5d); //Breite der mittleren Felder
        double borderColWidthSizePercentage = middleColWidthSizePercentage / 4; //Breite der Rand Spalten Felder
        double middleRowWidthSizePercentage = 100 / (newRowAmount - 1.5d); //Hoehe der mittleren Felder
        double borderRowWidthSizePercentage = middleRowWidthSizePercentage / 4; //Breite der Rand Zeilen Felder

        if(widthGrowLoss > 0){ //GirdPane soll groeßer bezueglich Breite werden (Spalten)

            for(int i = 0; i < newColumsAmount; i++) { //von den bestehenden bis zur neuen Breite
                ColumnConstraints colConstraints;
                if(i >= existingCols){ //wenn das Spaltenobjekt noch nicht existiert

                    colConstraints = new ColumnConstraints();
                    colConstraints.setHgrow(Priority.ALWAYS);
                    gridPane.getColumnConstraints().add(i, colConstraints); //neue Constraint den Constraints
                    // hinzufuegen

                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt
                    // werden, da es an die neue groesse angepasst werden muss
                    colConstraints = gridPane.getColumnConstraints().get(i);
                }

                colConstraints.setPercentWidth((i == 0 || i == newColumsAmount - 1) ? borderColWidthSizePercentage
                        : middleColWidthSizePercentage); //Ternaerer Operator: wenn linkeste oder rechteste Reihe dann
                // eine schmale Zelle in Bezug auf die Breite sonst fuer die mittleren eine dicke Zellen
            }
        } else if(widthGrowLoss < 0){ //GirdPane soll kleiner bezueglich Breite werden (Spalten)

            for(int i = existingCols - 1; i >= 0; i--) { //von den bestehenden bis zur neuen Breite
                if(i >= newColumsAmount){ //wenn die zu loeschenden Spalten erreicht wurden
                    gridPane.getColumnConstraints().remove(i); //bestehende Constraints aus den Constraints loeschen
                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt
                    // werden, da es an die neue groesse angepasst werden muss

                    ColumnConstraints colConstraints;
                    colConstraints = gridPane.getColumnConstraints().get(i);

                    colConstraints.setPercentWidth((i == 0 || i == newColumsAmount - 1) ? borderColWidthSizePercentage
                            : middleColWidthSizePercentage); //Ternaerer Operator: wenn linkeste oder rechteste Reihe
                    // dann eine schmale Zelle in Bezug auf die Breite sonst fuer die mittleren dicke Zellen
                }
            }
        }

        if(heigthGrowLoss > 0){ //GirdPane soll groeßer bezueglich Hoehe werden (mehr Zeilen)

            for (int i = 0; i < newRowAmount; i++) {
                RowConstraints rowConstraints;
                if(i >= existingRows) { //wenn das Reihenobjekt noch nicht existiert

                    rowConstraints = new RowConstraints();
                    rowConstraints.setVgrow(Priority.ALWAYS);
                    gridPane.getRowConstraints().add(i, rowConstraints); //neue Constraints den Constraints
                    // hinzufuegen

                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt
                    // werden, da es an die neue groesse angepasst werden muss
                    rowConstraints = gridPane.getRowConstraints().get(i);
                }

                rowConstraints.setPercentHeight((i == 0 || i == newRowAmount - 1) ? borderRowWidthSizePercentage
                        : middleRowWidthSizePercentage);//Ternaerer Operator: wenn oberste oder unterste Reihe dann eine
                // schmale Zelle in Bezug auf die Hoehe sonst fuer die mittleren dicke Zellen
            }
        } else if(heigthGrowLoss < 0){ //GirdPane soll kleiner bezueglich Hoehe werden (weniger Zeilen)

            for(int i = existingRows - 1; i >= 0; i--) { //von den bestehenden bis zur neuen Hoehe
                if(i >= newRowAmount){ //wenn die zu loeschenden Reihen erreicht wurden
                    gridPane.getRowConstraints().remove(i); //bestehende Constraints aus den Constraints loeschen
                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt

                    RowConstraints rowConstraints;
                    rowConstraints = gridPane.getRowConstraints().get(i);

                    rowConstraints.setPercentHeight((i == 0 || i == newRowAmount - 1) ? borderRowWidthSizePercentage
                            : middleRowWidthSizePercentage);//Ternaerer Operator: wenn oberste oder unterste Reihe dann
                    // eine schmale Zelle in Bezug auf die Hoehe sonst fuer die mittleren dicke Zellen
                }
            }
        }

        //Lambda Ausdruck welcher alle Elemente der GridPane entfernt welche außerhalb ihrer Groeße liegen
        // (bei verkleinerungen der GridPane)
        gridPane.getChildren().removeIf(node -> { //alle Elemente der GridPane sollen entfernt werden sofern return true
            Integer col = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);
            col = (col == null) ? 0 : col; //da getColumnIndex statt 0 null nutzt muss null mit 0 ersetzt werden
            row = (row == null) ? 0 : row; //da getRowIndex statt 0 null nutzt muss null mit 0 ersetzt werden
            return col >= newColumsAmount || row >= newRowAmount; //ist True wenn ein Element groeßer als die
            // neue Breite oder neue Hoehe ist
        });

        //Gesamtgroesse des neuen Spielfelds anpassen
        adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
    }

    /**
     * Methode welche die mittlere Spielflaeche sowie die Bilder in den Zellen dieser an die Groeße des Spielfelds
     * anpasst
     * @param gridPane Spielfeld
     * @param width Breite des mittleren Flaeche auf welcher die GridPane liegt
     * @param height Hoehe des mittleren Flaeche auf welcher die GridPane liegt
     */
    void adjustMiddleGridPaneSize(GridPane gridPane, double width, double height) {
        final int middleCols = gridPane.getColumnCount() - 2; //-2 da 2 Columns Rand sind
        final int middleRows = gridPane.getRowCount() - 2; //-2 da 2 Rows Rand sind
        //die aeußeren beiden Spalten/Reihen zaehlen zusammen nur als halbe Spalte/Reihe

        final double colCount = middleCols + 0.25d * 2; //0,5 da eine Randspalte 0,25 einer normalen ist und 0,5 da zwei
        final double rowCount = middleRows + 0.25d * 2; //0,5 da eine Randreihe 0,25 einer normalen ist und 0,5 da zwei

        double cellSize = Math.floor(Math.min(width / colCount, height / rowCount)); //die Zellengroeße abgerundet

        gridPane.setPrefSize(cellSize * colCount, cellSize * rowCount); //setzt diese Zellengroeße fuer GridPane
        double cellSizeWithoutBorder = cellSize - JavaFXGUI.BORDER_SIZE_GRAPHICAL * 2; //abzueglich der Randgroeße * 2,
        // da jede Zelle einen Rand hat

        //aktualisierung der Bildgroeßen und Abstaende
        for(Node node : gridPane.getChildren()) { //durchlaeuft jede Zelle und node ist die unterste Ebene des Inhalts
            // also die StackPane

            if(node instanceof StackPane tilePane) { //StackPane, da die unterste Ebene eine StackPane ist
                tilePane.setPrefSize(cellSize, cellSize); //Setzt die Groeße der StackPane
                for(Node child : tilePane.getChildren()) { //durchlaeuft jede naechste Ebene der StackPane da dort
                    // das ImageView kommt
                    if(child instanceof ImageView imageView) {
                        //Groeße updaten

                        //Breite und Hoehe anpassen
                        imageView.setFitWidth(cellSizeWithoutBorder);
                        imageView.setFitHeight(cellSizeWithoutBorder);
                    }
                }
            }
        }
    }

    /**
     * Methode welche alle noetigen Grafik Methoden buendelt zum Anzeigen eines Spiels und aller noetigen Elemente
     * abgesehen von der platzierung der Spielsteine
     * @param width die Breite des Spielfelds
     * @param height die Hoehe des Spielfelds
     */
    public void setupGUI(int width, int height){
        this.gameFieldGridPaneHandleEdges();
        centerPane.applyCss(); //centerPane css setzen bevor die Methode weiterlaeuft
        centerPane.layout(); //centerPane Layout setzen bevor die Methode weiterlaeuft
        this.adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
        this.gridPane.layout(); //centerPane Layout setzen bevor die Methode weiterlaeuft
    }

    /**
     * Methode welche graphisch die Spielsteine der Spielsteinauswahl hinzufuegt und das graphische Spielfeld mit
     * Spielsteinen fuellt
     * @param tiles die Spielsteine mit welchen die Spielsteinauswahl gefuellt werden soll
     * @param gameField das Spielfeld welches als Refferenz dient wie das graphische Spielfeld gefuellt werden soll
     */
    public void updateTiles(Tiles tiles, GameField gameField) {
        this.displayGameFieldTiles(gameField);
        this.displaySelectionTiles(tiles);
    }

}