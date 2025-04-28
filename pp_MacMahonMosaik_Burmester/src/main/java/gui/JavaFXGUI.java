package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import logic.*;

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
    private final String pieceID = "0";
    private final String holeID = "1";
    private final String borderID = "2";

    /**
     * Konstruktor welcher diese Klasse initialisiert
     * @param borderPane der Gesamte Hintergrund
     * @param centerPane der hintergrund der GridPane in der Mitte
     * @param gridPane die FXML Instanz
     * @param rightGridPane die rechte GridPane in welcher die noch nicht gelegten Spielsteine sind
     */
    public JavaFXGUI(BorderPane borderPane, Pane centerPane, GridPane gridPane, GridPane rightGridPane){
        this.borderPane = borderPane;
        this.centerPane = centerPane;
        this.gridPane = gridPane;
        this.rightGridPane = rightGridPane;

        this.loadImages();
    }

    /**
     * Methode welche dieForm der GridPane anzeigt
     * @param gameField das Spielfeld und seine darauf liegenden Spielsteine
     */
    public void updateGridPaneFormat(GameField gameField){
        this.gridPane.getChildren().clear(); //entfernt alte Zellen
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();

        int rowsCount = gameField.getGameField().length;
        int columnsCount = gameField.getGameField()[0].length;
        gridPane.setMinSize(0, 0); //minimalgroeße der GridPane

        double middleColWidthSizePercentage = 100 / (columnsCount - 1.5d); //Breite der mittleren Felder
        double borderColWidthSizePercentage = middleColWidthSizePercentage / 4; //Breite der Rand Spalten Felder
        double middleRowWidthSizePercentage = 100 / (rowsCount - 1.5d); //Hoehe der mittleren Felder
        double borderRowWidthSizePercentage = middleRowWidthSizePercentage / 4; //Breite der Rand Zeilen Felder

        // ChangeListener hinzufuegen, damit sich die GridPane durch die Pane an die
        // Groeßenveraenderung der BorderPane anpasst
        centerPane.widthProperty().addListener((obs, oldVal, newVal) ->
                adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));
        centerPane.heightProperty().addListener((obs, oldVal, newVal) ->
                adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));

        //neue Spalten
        for(int i = 0; i < columnsCount; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.ALWAYS);
            colConstraints.setPercentWidth((i == 0 || i == columnsCount - 1) ? borderColWidthSizePercentage
                    : middleColWidthSizePercentage); //Ternaerer Operator: wenn linkeste oder rechteste Reihe dann eine
            // schmale Zelle in Bezug auf die Breite sonst fuer die mittleren dicke Zellen
            gridPane.getColumnConstraints().add(colConstraints);
        }

        //neue Zeilen
        for(int i = 0; i < rowsCount; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setPercentHeight((i == 0 || i == rowsCount - 1) ? borderRowWidthSizePercentage
                    : middleRowWidthSizePercentage);//Ternaerer Operator: wenn oberste oder unterste Reihe dann eine
            // schmale Zelle in Bezug auf die Hoehe sonst fuer die mittleren dicke Zellen
            gridPane.getRowConstraints().add(rowConstraints);
        }

        adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
        this.loadHolesStackPanes();
    }


    /**
     * Methode welche die mittlere Spielflaeche sowie die Bilder in den Zellen dieser an die Groeße anpasst
     * @param gridPane Spielflaeche
     * @param width Breite des mittleren Flaeche auf welcher die GridPane liegt
     * @param height Hoehe des mittleren Flaeche auf welcher die GridPane liegt
     */
    private void adjustGridPaneSize(GridPane gridPane, double width, double height) {
        final int middleCols = gridPane.getColumnCount() - 2; //-2 da 2 Columns Rand sind
        final int middleRows = gridPane.getRowCount() - 2; //-2 da 2 Rows Rand sind
        //die aeußeren beiden Spalten/Reihen zaehlen zusammen nur als halbe Spalte/Reihe
        final double colCount = middleCols + 0.5d; //0,5 da eine Randspalte 0,25 einer normalen ist und 0,5 da zwei
        final double rowCount = middleRows + 0.5d; //0,5 da eine Randreihe 0,25 einer normalen ist und 0,5 da zwei

        double cellSize = Math.min(width / colCount, height / rowCount); //die Zellengroeße
        gridPane.setPrefSize(cellSize * colCount, cellSize * rowCount); //setzt diese Zellengroeße fuer GridPane

        //aktualisierung der Bildgroeßen und Abstaende
        for(Node node : gridPane.getChildren()) { //durchlaeuft jede Zelle und node ist die unterste Ebene des Inhalts
            // also das StackPane
            if(node instanceof StackPane tilePane) { //StackPane, da die unterste Ebene eine StackPane ist
                tilePane.setPrefSize(cellSize, cellSize); //Setzt die Groeße der StackPane
                for(Node child : tilePane.getChildren()) { //durchlaeuft jede naechste Ebene der StackPane da dort
                    // das ImageView kommt
                    if(child instanceof ImageView imageView) {
                        //Groeße updaten
                        imageView.setFitWidth(cellSize);
                        imageView.setFitHeight(cellSize);
                    }
                }
            }
        }
    }

    /**
     * Methode welche alle Bilder am Anfang des Spiels laedt ohne diese anzuzeigen
     * Die Bilder werden in dieser Klasse in einem Eindimensionalem Array
     * in der Reihenfolge des TileNames Enums gespeichert
     */
    private void loadImages(){
        ImageView[] imageViews = new ImageView[TileNames.values().length - 2]; //Laenge -2 da die TileNames
        // NNNN und HHHH nicht geladen werden da sie kein Bild haben
        String imagePath;
        for(int i = 0; i < imageViews.length; i++){ //-2 weil HHHH und NNNN nicht als Bild vorhanden sind
                imagePath = "/tiles/" + TileNames.values()[i] + ".png"; //der relative Pfad zu dem Bild
                Image image =
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))); //laedt das Bild
                ImageView currIndexImage = new ImageView(image); //ImageView da es Attribute wie z.B. Groeße speichert
                imageViews[i] = currIndexImage;
        }
        this.imageViews = imageViews;
    }

    /**
     * Methode welche alle benoetigten Loecher Objekte in Form einer gefaerbten StackPane initialisiert und sie dem
     * holeStackPanes Array hinzufuegt
     */
    public void loadHolesStackPanes(){
        //Anzahl der benoetigten Loecher da fuer jede Zelle die es im Spielfeld mehr gibt als Bilder ein Loch sein muss
        // -2 da Rand nicht beachtet
        int holesAmount = (this.gridPane.getColumnCount() - 2) * (this.gridPane.getRowCount() - 2) - 24;
        StackPane[] holeStackPanes = new StackPane[Math.max(holesAmount, 0)];
        if(holesAmount > 0){ //wenn es Loecher gibt
            for(int i = 0; i < holesAmount; i++){ //soviele Loecher wie noetig
                StackPane holeStackPane = new StackPane();
                holeStackPane.setStyle("-fx-background-color: gray; -fx-border-color: " +
                        "black; -fx-border-width: 2;");
                holeStackPanes[i] = holeStackPane; //diese Loecher dem Array der benoetigten Loecher hinuzfuegen
            }
        }
        this.holeStackPanes = holeStackPanes;
    }

    /**
     * Methode welche die Bilder der Mosaiksteine setzt
     * @param game Spiel Instanz aus welcher die Methoden kommen um die Bewegung eines Spielsteins der Logik mitzuteilen
     * @param gameField das Spielfeld
     */
    public void displayGameFieldTiles(Game game, GameField gameField) {
        this.gridPane.getChildren().clear(); //entfernt alle bestehenden Bilder

        for(int y = 1; y < gameField.getGameField().length - 1; y++) { //Start bei 1 und Ende bei Groeße - 1
            // da der Rand nicht beachtet wird
            for(int x = 1; x < gameField.getGameField()[y].length - 1; x++) { //Start bei 1 und Ende bei Groeße - 1
                // da der Rand nicht beachtet wird
                Tile currTile = gameField.getTile(x, y);

                //Hintergrund als StackPane da man diese faerben kann
                //die StackPane bleibt durchgehend an derselben Stelle der GridPane, somit muss nur einmalig ein
                // Event Listener gesetzt werden und die Bilder werden dann einfach immer von der einen StackPane auf
                // die andere StackPane beim verschieben gesetzt
                StackPane slotStackPane = new StackPane();
                slotStackPane.setPrefSize(90, 90);
                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

                if(currTile.getTile().equals(TileNames.NNNN)) { //ein leeres Feld
                    slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
                } else if(currTile.getTile().equals(TileNames.HHHH)) { //ein Loch
                    //die aktuelle holeStackPane
                    for (StackPane currStackPane : this.holeStackPanes) { //durchlaeuft jede holeStackPane
                        if (currStackPane.getParent() == null) { //wenn holeStackPane nirgendwo gelegt wurde
                            slotStackPane.getChildren().add(currStackPane); //das Loch dem Hintergrund hinzufuegen
                        }
                    }
                } else { //ein Bild
                    int tileEnumIndex = TileNames.valueOf(currTile.getTileString()).ordinal();//der Index des Bilds
                    ImageView tileImageView;
                    tileImageView = this.imageViews[tileEnumIndex]; //das Bild als ImageView
                    tileImageView.setFitHeight(90);
                    tileImageView.setFitWidth(90);
                    slotStackPane.getChildren().add(tileImageView);
                }

                //Rotation des Spielsteins wenn Rechtsklick
                slotStackPane.setOnMouseClicked(event -> {
                    if(event.getButton().equals(MouseButton.SECONDARY)){
                        Node holeOrImage = slotStackPane.getChildren().getFirst();
                        if(holeOrImage instanceof ImageView imageView) {
                            imageView.setRotate(imageView.getRotate() + 90); //Bild graphisch rotieren
                            game.rotateGameTile(this.getTileIndex(imageView)); //Rotation in der Logik
                        }
                    }
                });

                //wenn ein Bild Hintergrund Konstrukt bewegt wird per Drag
                slotStackPane.setOnDragOver(event -> {
                    Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
                    if (event.getGestureSource() != slotStackPane && dragboard.hasString()) {
                        if(slotStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also keine Kinder hat
                            //nur hervorheben wenn es sich um einen Spielstein handelt oder ein Loch im EditorMode

                            //Drag wird nur akzeptiert wenn es sich um ein Bild handelt oder ein Loch im EditorMode
                            if ((!game.isEditorMode() && dragboard.getString().startsWith(pieceID)) ||
                                    (game.isEditorMode() && dragboard.getString().startsWith(holeID))) {
                                event.acceptTransferModes(TransferMode.MOVE); //Bild bewegung registrieren
                            }
                        }
                    }
                    event.consume();
                });

                //wenn ein Bild Hintergrund Konstrukt ueber den Slot gezogen wird
                slotStackPane.setOnDragEntered(event -> {
                    Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
                    if (event.getGestureSource() != slotStackPane && dragboard.hasString()) {
                        if(slotStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also keine Kinder hat
                            //nur hervorheben wenn es sich um einen Spielstein handelt oder ein Loch im EditorMode

                            //Drag wird nur akzeptiert wenn es sich um ein Bild handelt oder ein Loch im EditorMode
                            if((!game.isEditorMode() && dragboard.getString().startsWith(pieceID)) ||
                                    (game.isEditorMode() && dragboard.getString().startsWith(holeID))){
                                slotStackPane.setStyle("-fx-background-color: pink; " +
                                        "-fx-border-color: black; -fx-border-width: 2;"); //Hintergrund faerben
                            }
                        }
                    }
                });

                //wenn ein Bild Hintergrund Konstrukt ueber den Slot war und wieder weggezogen wird
                slotStackPane.setOnDragExited(event -> {
                    slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;"); //Hintergrund entfernen und
                    // Rand wiederherstellen
                });

                //wenn ein Bild Hintergrund Konstrukt auf den Slot gedropped wird
                slotStackPane.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        String inputString = db.getString();

                        //substring 1 da die 0te Position der Typ ist
                        String pieceOrHoleStringIndex = db.getString().substring(1);

                        int targetX = GridPane.getColumnIndex(slotStackPane); //der Breitenindex dieses Slots
                        int targetY = GridPane.getRowIndex(slotStackPane); //der Hoehenindex dieses Slots

                        if(inputString.startsWith(pieceID)) { //wenn ein nicht Loch ueber ein Feld gedropped wurde
                            //der Index des bewegten ImageViews
                            int imageViewIndex = Integer.parseInt(pieceOrHoleStringIndex);
                            ImageView droppedImageView = this.imageViews[imageViewIndex]; //das bewegte ImageViews

                            //die GridPane aus welcher das Bild kommt
                            GridPane sourceGridPane = (GridPane) droppedImageView.getParent().getParent();
                            //der vorherige Slot (StackPane) auf welcher das Bild vorher lag
                            StackPane sourceSlotStackPane = (StackPane) droppedImageView.getParent();
                            if (sourceGridPane == this.gridPane) { //das Bild kommt aus dem mittleren Spielfeld
                                Integer startX = GridPane.getColumnIndex(sourceSlotStackPane);
                                Integer startY = GridPane.getRowIndex(sourceSlotStackPane);
                                boolean moved = game.moveTileFromGamefieldToGameField(startX, startY, targetX, targetY);
                                if (moved) {
                                    //Bild aus alten Slot entfernen
                                    sourceSlotStackPane.getChildren().remove(droppedImageView);
                                    //Bild in neuen Slot einfuegen
                                    slotStackPane.getChildren().add(droppedImageView);
                                }
                            } else if (sourceGridPane == this.rightGridPane) { //das Bild kommt aus der rechten Auswahl
                                boolean moved = game.moveTileFromNotLaidTilesToGameField(targetX, targetY,
                                        imageViewIndex, true);
                                if (moved) {
                                    //Bild aus alten Slot entfernen
                                    sourceSlotStackPane.getChildren().remove(droppedImageView);
                                    //Bild an die groeße des Hintergrunds (StackPane) anpassen
                                    droppedImageView.setFitWidth(slotStackPane.getWidth());
                                    droppedImageView.setFitHeight(slotStackPane.getHeight());
                                    //Bild in neuen Slot einfuegen
                                    slotStackPane.getChildren().add(droppedImageView);
                                }
                            }
                        } else if(inputString.startsWith(holeID)){ //wenn ein Loch ueber ein Feld gedropped wurde
                            int holeIndex = Integer.parseInt(pieceOrHoleStringIndex);
                            StackPane holeStackPane = this.holeStackPanes[holeIndex];
                            //die GridPane aus welcher das Bild kommt
                            GridPane sourceGridPane = (GridPane) holeStackPane.getParent().getParent();
                            //der vorherige Slot (StackPane) auf welcher das Bild vorher lag
                            StackPane sourceSlotStackPane = (StackPane) holeStackPane.getParent();
                            boolean moved;
                            if(sourceGridPane.equals(this.rightGridPane)) { //Loch kommt aus der rechten GridPane
                                moved = game.moveTileFromNotLaidTilesToGameField
                                        (targetX, targetY, holeIndex, false);
                            } else { //Loch wird innerhalb des mittleren Spielfelds verschoben
                                Integer startX = GridPane.getColumnIndex(sourceSlotStackPane);
                                Integer startY = GridPane.getRowIndex(sourceSlotStackPane);
                                moved = game.moveTileFromGamefieldToGameField(startX, startY, targetX, targetY);
                            }
                            if (moved) {
                                sourceSlotStackPane.getChildren().remove(holeStackPane);
                                slotStackPane.getChildren().add(holeStackPane);
                            }
                        }
                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });

                slotStackPane.setOnDragDetected(event -> {
                    if (!slotStackPane.getChildren().isEmpty()) { //nicht leer also Bild
                        Dragboard db = slotStackPane.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        Node currNode = slotStackPane.getChildren().getFirst();
                        int tileEnumIndex = this.getTileIndex(currNode);
                        String contentPayload = (currNode instanceof ImageView ? pieceID : holeID) + tileEnumIndex;
                        content.putString(contentPayload);
                        db.setContent(content);
                    }
                    event.consume();
                });

                this.gridPane.add(slotStackPane, x, y);
            }
        }
        adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
    }


    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param game Spiel Instanz aus welcher die Methoden kommen um die Bewegung eines Spielsteins der Logik mitzuteilen
     * @param gameTiles die verfuegbaren Spielsteine
     */
    public void displayNotUsedTiles(Game game, GameTiles gameTiles) {
        this.rightGridPane.getChildren().clear(); //entfernt alle bestehenden Bilder

        //Abstand zwischen den Spalten und Reihen
        this.rightGridPane.setHgap(10);
        this.rightGridPane.setVgap(10);

        int col = 0, row = 0; //aktuelle Spalte und Reihe der Auswahl der Spielsteine

        for (Tile currTile : gameTiles.getTiles()) { //durchlaeuft jeden Spielstein

            //da NNNN und HHHH nicht legbar sind, sollen sie auch nicht in der Auswahl auftauchen
            if(!(!currTile.getIsLaid() &&
                    (currTile.getTile() == TileNames.NNNN || currTile.getTile() == TileNames.HHHH))) {

                //Hintergrund als StackPane da man diese faerben kann
                //die StackPane bleibt durchgehend an derselben Stelle der GridPane, somit muss nur einmalig ein
                // Event Listener gesetzt werden und die Bilder werden dann einfach immer von der einen StackPane auf
                // die andere StackPane beim verschieben gesetzt
                StackPane slotStackPane = new StackPane();
                slotStackPane.setPrefSize(80, 80);
                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

                if (!currTile.getIsLaid()) {
                    String tileName = currTile.getTileString();
                    if (currTile.isNormalGameTile()) { //wenn nicht NNNN und HHHH da diese kein Bild haben
                        int tileEnumIndex = TileNames.valueOf(tileName).ordinal();
                        ImageView imageView = this.imageViews[tileEnumIndex]; //das ImageView des aktuellen Spielsteins
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(80);
                        slotStackPane.getChildren().add(imageView); //ImageView der Stackpane hinzufuegen
                    }
                }

                //Drag Entered Event: wenn ein Bild ueber die Spielstein Auswahl gezogen wird, wird diese hervorgehoben
                rightGridPane.setOnDragEntered(event -> {
                    if (((StackPane) event.getGestureSource()).getParent() !=
                            this.rightGridPane && event.getDragboard().hasString()) { //ist String
                        rightGridPane.setStyle("-fx-background-color: pink;"); //setzt Hintergrundfarbe der StackPane
                    }
                });

                //drag Exited Event wenn das Bild wieder aus der Spielstein Auswahl herausgezogen wird,
                // wird diese hervorhebung zurueckgesetzt
                rightGridPane.setOnDragExited(event -> {
                    rightGridPane.setStyle(""); //entfernt die Hintergrundfarbe
                });

                //Drag Detected Event fuer die Spielstein Auswahl: wenn dieses StackPane ImageView Konstrukt wieder aus
                // der Auswahl per Drag bewegt wird
                slotStackPane.setOnDragDetected((MouseEvent event) -> {
                    if (!slotStackPane.getChildren().isEmpty()) { //nicht leer also Bild
                        Dragboard db = slotStackPane.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        ImageView currImageView = (ImageView) slotStackPane.getChildren().getFirst();
                        int tileEnumIndex = this.getTileIndex(currImageView);
                        content.putString(pieceID + tileEnumIndex);
                        db.setContent(content);
                    }
                    event.consume(); //markiert das Event als verarbeitet
                });

                //Drag Over Event: registiert, dass ein StackPane ImageView Konstrukt ueber dieses Slot gezogen wurde
                slotStackPane.setOnDragOver(event -> {
                    if (((StackPane) event.getGestureSource()).getParent() !=
                            this.rightGridPane && event.getDragboard().hasString()) { //enthaelt einen String
                        event.acceptTransferModes(TransferMode.MOVE); //wird bewegt und nicht z.B. kopiert
                    }
                    event.consume(); //markiert das Event als verarbeitet
                });

                //Drop Event fuer das die Spielstein Auswahl: wenn das Bild in der rigthGridPane abgelegt wird
                this.rightGridPane.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        int imageViewIndex = Integer.parseInt(db.getString());
                        ImageView droppedImageView = this.imageViews[imageViewIndex];

                        //Ursprung des zu Verschiebenden Bildes (Tile) aus dem Spielfeld
                        Integer startX = GridPane.getColumnIndex(droppedImageView.getParent());
                        Integer startY = GridPane.getRowIndex(droppedImageView.getParent());

                        //durch eine Game Klasse Methode wird die Bewegung vom Spielfeld in die Spielstein
                        // Auswahl in das Spielfeld der Game Instanz des Logik Package uebernommen
                        boolean returned = game.moveTileFromGamefieldToNotLaidTileSelection(startX, startY,
                                true);
                        if (returned) {
                            int xIndex = imageViewIndex % 3; //die Spalte wo der Spielstein in der
                            // Spielstein Auswahl urspruenglich mal war
                            int yIndex = imageViewIndex / 3; //die Reihe wo der Spielstein in der
                            // Spielstein Auswahl urspruenglich mal war

                            StackPane targetCellStackPane = this.getGridPaneCell(xIndex, yIndex, this.rightGridPane);
                            //der vorherige Slot (StackPane) auf welcher das Bild vorher lag
                            StackPane sourceSlotStackPane = (StackPane) droppedImageView.getParent();

                            //das Bild vom alten Slot losbinden
                            sourceSlotStackPane.getChildren().remove(droppedImageView);
                            droppedImageView.setFitWidth(80);
                            droppedImageView.setFitHeight(80);
                            droppedImageView.setRotate(0); //Rotation Graphisch zuruecksetzen
                            targetCellStackPane.getChildren().add(droppedImageView); //das Bild an den Slot binden

                            event.setDropCompleted(true);
                        } else {
                            event.setDropCompleted(false);
                        }
                    }

                    event.consume(); //markiert das Event als verarbeitet
                });

                this.rightGridPane.add(slotStackPane, col, row); //Zelle dem GridPane hinzufuegen
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
    private int getTileIndex(Node input){
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
     * @return die Instanz des Inhalts an der bestimmten Stelle
     */
    private StackPane getGridPaneCell(int xIndex, int yIndex, GridPane gridPane){
        StackPane resultCell = null;
        for(Node currCellNode: gridPane.getChildren()){
            int xCoordinate = GridPane.getColumnIndex(currCellNode);
            int yCoordinate = GridPane.getRowIndex(currCellNode);
            if(xCoordinate == xIndex && yCoordinate == yIndex){
                resultCell = (StackPane) currCellNode;
            }
        }
        return(resultCell);
    }

    /**
     * Methode welche den Rand des Spielfelds anzeigt
     * @param game die Instanz des Spiels damit Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param gameField das Spielfeld
     */
    public void displayBorder(Game game, GameField gameField){

        for(int y = 0; y < gameField.getGameField().length; y++) { //durchlaeuft jede Zeile
            for(int x = 0; x < gameField.getGameField()[y].length; x++) { //durchlaeuft jede Spalte
                //filtert die Ecken raus, da diese nicht als Rand angezeigt werden sollen
                //initiales Setzen des Randes (falls ein bestehendes Spiel geladen wurde)
                if(!gameField.isFieldEdge(x, y) && gameField.isFieldBorder(x, y)) { //Randstueck und kein Eckstueck

                    Pane slotStackPane = new Pane();
                    //switch Statement welches je nach Randstein den Rand faerbt
                    String initialCellStyle = switch (gameField.getTile(x, y).getTile()) {
                        case TileNames.GGGG ->
                                "-fx-border-color: black; -fx-border-width: 2; -fx-background-color: green;"; //Gruen
                        case TileNames.YYYY ->
                                "-fx-border-color: black; -fx-border-width: 2; -fx-background-color: yellow;"; //Gelb
                        case TileNames.RRRR ->
                                "-fx-border-color: black; -fx-border-width: 2; -fx-background-color: red;"; //rot
                        default -> "-fx-border-color: black; -fx-border-width: 2;"; //leere Zelle mit Rand
                    };
                    slotStackPane.setStyle(initialCellStyle);
                    slotStackPane.setUserData(initialCellStyle);


                    //Drag Over Event: den Drag des Randsteins ueber den Rand registrieren
                    slotStackPane.setOnDragOver(event -> {
                        Dragboard db = event.getDragboard(); //der Inhalt der verschoben wird
                        if(event.getGestureSource() != slotStackPane && db.hasString()) { //ist String
                            if(db.getString().startsWith(borderID)) { //ist Randstueck
                                event.acceptTransferModes(TransferMode.COPY); //wird kopiert
                            }
                        }
                        event.consume(); //markiert das Event als verarbeitet
                    });

                    //Drag Entered Event: wenn ein Randstein ueber ein potentielles Randstueck gezogen wird,
                    // dieses hervorgehoben
                    slotStackPane.setOnDragEntered(event -> {
                        Dragboard db = event.getDragboard(); //der Inhalt der verschoben wird
                        if(event.getGestureSource() != slotStackPane && db.hasString()) { //ist String
                            if(db.getString().startsWith(borderID)) { //ist Randstueck
                                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;" +
                                        "-fx-background-color: pink;"); //setzt die Hintergrundfarbe der StackPane
                            }
                        }
                    });

                    //drag Exited Event wenn das Bild wieder aus dem moeglichen Ziel herausgezogen wird,
                    // wird diese hervorhebung zurueckgesetzt auf den vorherigen Style
                    slotStackPane.setOnDragExited(event -> {
                        String originalStyle = (String) slotStackPane.getUserData(); //den letzten Style
                        slotStackPane.setStyle(originalStyle); //den letzten Style setzen
                    });

                    //Drop Event fuer das Spielfeld: wenn das Bild in einer Zelle tilePane abgelegt wird
                    slotStackPane.setOnDragDropped(event -> {
                        Dragboard db = event.getDragboard(); //Inhalt was bewegt wird
                        if(db.hasString()) { //wenn das zu farbkodierende Objekt ein String ist
                            if(db.getString().startsWith(borderID)) { //Drop Teil ist Randstueck
                                String inputString = db.getString().substring(1); //Substring 1 da
                                // die erste Zahl ein Identifier fuer Rand ist
                                //Zielkoordinaten
                                Integer targetX = GridPane.getColumnIndex(slotStackPane);
                                Integer targetY = GridPane.getRowIndex(slotStackPane);

                                //durch eine Game Klasse Methode wird die Bewegung von der Spielstein Auswahl in
                                // das Logik Spielfeld uebernommen
                                Tile newTile = new Tile(TileNames.values()[Integer.parseInt(inputString)]);
                                boolean placed = game.colorBorder(targetX, targetY, newTile);
                                if (placed) {
                                    //setzt die Farbe des RandElements im Spielfeld
                                    String cellStyle = "-fx-border-color: black; -fx-border-width: 2;" +
                                            borderTileNameToColorNameString(newTile.getTile());
                                    slotStackPane.setStyle(cellStyle);

                                    //speichert den aktuellen Style damit dieser wenn das Spielfeld durch
                                    // setOnDragExited zurueckgesetzt wird dieses den letzten Style anzeigt
                                    // und nicht einfach nichts da der Rand ja nur gefaerbt wird
                                    // z.B: koennte der Rand vorher Rot oder Gelb gewesen sein
                                    slotStackPane.setUserData(cellStyle);
                                }
                            }
                            event.setDropCompleted(true);
                        } else {
                            event.setDropCompleted(false);
                        }
                        event.consume(); //markiert das Event als verarbeitet
                    });

                    this.gridPane.add(slotStackPane, x, y); //den Hintergrund (wird gefaerbt) des Randes
                    // in das mittlere Spielfeld hinzufuegen
                }
            }
        }
    }

    /**
     * Methode welche aus einem bestimmten TileNames Element sofern es einfarbig ist eine Farbe welche css kompatibel
     * ist zurueckgibt
     * @param input das TileNames Element
     * @return die css Kompatible Farbe
     */
    private String borderTileNameToColorNameString(TileNames input){
        return(switch (input) {
            case TileNames.GGGG -> "-fx-background-color: green;";
            case TileNames.YYYY -> "-fx-background-color: yellow;";
            case TileNames.RRRR -> "-fx-background-color: red;";
            default -> "";
        });
    }


    /**
     * Methode welche die Editor Elemente in der rechten GridPane Auswahl anzeigt
     * @param game die Instanz des Spiels damit Loch Aenderungen am Spielfeld auch in der Logik angepasst werden
     * @param withHoles ob in das Spielfeld auch Loecher sollen (wenn nicht wird der Loch Spielstein nicht angezeigt)
     */
    public void fillRightGridPaneWithEditorPieces(Game game, boolean withHoles){
        this.rightGridPane.getChildren().clear(); //entfernt alle bestehenden Bilder

        //Abstand zwischen den Spalten und Reihen
        this.rightGridPane.setHgap(10);
        this.rightGridPane.setVgap(10);

        //rot, gruen und gelbe Auswahl fuer den Rand
        Label randLabel = new Label("Randsteine:"); //Schriftzug
        this.rightGridPane.add(randLabel, 0, 4, 3, 1); // ueber drei Spalten, eine Zeile

        StackPane cell = new StackPane();
        cell.setPrefSize(80, 80);
        cell.setStyle("-fx-background-color: green;"); //Gruen
        this.applyDragEventsForBorder(cell, borderID + TileNames.valueOf(TileNames.GGGG.toString()).ordinal());
        this.rightGridPane.add(cell, 0, 5); //Zelle der GridPane hinzufuegen
        cell = new StackPane();
        cell.setPrefSize(80, 80);
        cell.setStyle("-fx-background-color: yellow;"); //Gelb
        this.applyDragEventsForBorder(cell, borderID + TileNames.valueOf(TileNames.YYYY.toString()).ordinal());
        this.rightGridPane.add(cell, 1, 5); //Zelle der GridPane hinzufuegen
        cell = new StackPane();
        cell.setPrefSize(80, 80);
        cell.setStyle("-fx-background-color: red;"); //rot
        this.applyDragEventsForBorder(cell, borderID + TileNames.valueOf(TileNames.RRRR.toString()).ordinal());
        this.rightGridPane.add(cell, 2, 5); //Zelle der GridPane hinzufuegen

        //Loch Spielstein fuer den Editor
        if(this.holeStackPanes.length > 0) {
            Label lochSteinLabel = new Label("Lochstein:"); //Schriftzug
            this.rightGridPane.add(lochSteinLabel, 0, 14, 3, 1); // ueber drei Spalten, eine Zeile
            int row = 15, col = 0;
            for (StackPane holeStackPane : this.holeStackPanes) { //jedes Loch hinzufuegen
                StackPane slotStackPane = new StackPane();
                slotStackPane.setPrefSize(80, 80);
                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

                if (holeStackPane.getParent() == null || //noch nie gelegt
                        (holeStackPane.getParent() != null && //gelegt und nicht mehr auf dem Spielfeld
                                holeStackPane.getParent().getParent() == null)) {
                    cell = holeStackPane; //das aktuelle Loch
                    cell.setPrefSize(80, 80);
                    cell.setStyle("-fx-background-color: gray;"); //graues Loch

                    slotStackPane.getChildren().add(cell); //dem Hintergrund das Loch hinzufuegen
                }

                this.applyDragDropEventsForHoleSelection(slotStackPane, game); //Drag and Drop hinzufuegen
                this.rightGridPane.add(slotStackPane, col, row); //Zelle der GridPane hinzufuegen

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
     * Methode welche die Drag and Drop Event Handler fuer die Loecher setzt
     * @param node das Objekt welches die Handler bekommt abgesehen von der rightGridPane
     * @param game die Instanz des Spiels damit Graphische veraenderungen dem Spiel mitgeteilt werden koennen
     */
    private void applyDragDropEventsForHoleSelection(Node node, Game game) {
        //DragDetected Event: Drag starten, wenn man auf das Objekt klickt
        node.setOnDragDetected(event -> {
            Dragboard db = node.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            StackPane slotStackPane = (StackPane) node; //der Hintergrund gezogenen Objekts
            //der Index des Lochsteins
            content.putString(holeID + this.getTileIndex(slotStackPane.getChildren().getFirst()));
            db.setContent(content);
            event.consume();
        });

        //DragEntered Event: wenn ein potentieller Lochstein ueber ein moegliches Lochstein ziel gezogen wird
        this.rightGridPane.setOnDragEntered(event -> {
            if (((StackPane) event.getGestureSource()).getParent() !=
                    this.rightGridPane && event.getDragboard().hasString()) { //enthaelt einen String und kommt nicht
                // aus der rechten GridPane
                rightGridPane.setStyle("-fx-background-color: pink; -fx-border-width: 2;");
            }
            event.consume();
        });

        //DragExited Event: wenn der potentielle Lochstein wieder sein Ziel verlaesst
        this.rightGridPane.setOnDragExited(event -> {
            this.rightGridPane.setStyle("-fx-border-width: 2;"); // Urspruenglichen Stil wiederherstellen
            event.consume();
        });

        //Drag Over Event: registiert, dass ein StackPane ImageView Konstrukt ueber dieses Slot gezogen wurde
        this.rightGridPane.setOnDragOver(event -> {
            if (((StackPane) event.getGestureSource()).getParent() !=
                    this.rightGridPane && event.getDragboard().hasString()) { //enthaelt einen String
                event.acceptTransferModes(TransferMode.MOVE); //wird bewegt und nicht z.B. kopiert
            }
            event.consume(); //markiert das Event als verarbeitet
        });

        //DragDropped Event: wenn der Lochstein ueber ein potentielles Ziel abgelegt wird
        this.rightGridPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if(db.hasString()) {
                String inputString = db.getString();
                if(inputString.startsWith(holeID)) {
                    String holeIndexString = db.getString().substring(1); //substring 1 da 0 der Identifier
                    // ist ob es sich um ein Loch handelt
                    int holeIndex = Integer.parseInt(holeIndexString);
                    StackPane holeStackPane = this.holeStackPanes[holeIndex]; //das StackPane Loch am Index im Arrays

                    int xIndex = holeIndex % 3; //die Spalte wo der Spielstein in der
                    // Spielstein Auswahl urspruenglich mal war
                    int yIndex = holeIndex / 3; //die Reihe wo der Spielstein in der
                    // Spielstein Auswahl urspruenglich mal war

                    //+ 15 weil ab Reihe 15 erst die Lochsteine anfangen in der Editorauswahl
                    StackPane targetCellStackPane = this.getGridPaneCell(xIndex, yIndex + 15, this.rightGridPane);

                    //der vorherige Slot (StackPane) auf welcher das Bild vorher lag
                    StackPane sourceSlotStackPane = (StackPane) holeStackPane.getParent();

                    //Ursprung des zu Verschiebenden Bildes (Tile) aus dem Spielfeld
                    Integer startX = GridPane.getColumnIndex(sourceSlotStackPane);
                    Integer startY = GridPane.getRowIndex(sourceSlotStackPane);
                    boolean moved = game.moveTileFromGamefieldToNotLaidTileSelection(startX, startY, false);
                    if (moved) {
                        sourceSlotStackPane.getChildren().remove(holeStackPane);
                        targetCellStackPane.getChildren().add(holeStackPane);
                    }

                    event.setDropCompleted(true);
                }
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }

    /**
     * Methode welche den Drag Event Handler fuer Randsteine setzt
     * @param node das Hintergrundstueck welches den Handler bekommt
     * @param nodeString die Kennung des Randstuecks
     */
    private void applyDragEventsForBorder(Node node, String nodeString) {
        //Drag starten, wenn man auf das Objekt klickt
        node.setOnDragDetected(event -> {
            Dragboard db = node.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(nodeString); //die Kennung des Randstuecks
            db.setContent(content);
            event.consume();
        });
    }
}
