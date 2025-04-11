package gui;

import javafx.scene.Node;
import javafx.scene.Parent;
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

        int columnsCount = gameField.getGameField().length;
        int rowsCount = gameField.getGameField()[0].length;
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
            if(node instanceof StackPane tilePane) { //StackPane, da in meinem Code die unterste Ebene eine StackPane ist //TODO ok da instanceof genutzt
                tilePane.setPrefSize(cellSize, cellSize); //Setzt die Größe der StackPane
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
    public void loadImages(){
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
     * Methode welche die Bilder der Mosaiksteine setzt
     * @param game Spiel Instanz aus welcher die Methoden kommen um die Bewegung eines Spielsteins der Logik mitzuteilen
     * @param gameField das Spielfeld
     */
    public void displayGameFieldTiles(Game game, GameField gameField) {
        this.gridPane.getChildren().clear(); //entfernt alle bestehenden Bilder

        int width = gameField.getGameField().length;
        int height = gameField.getGameField()[0].length;

        for(int x = 1; x < width - 1; x++) { //Start bei 1 und Ende bei Groeße - 1 da der Rand nicht beachtet wird
            for(int y = 1; y < height - 1; y++) { //Start bei 1 und Ende bei Groeße - 1 da der Rand nicht beachtet wird
                Tile currTile = gameField.getTile(x, y);

                //Hintergrund als StackPane da man diese faerben kann
                //die StackPane bleibt durchgehend an derselben Stelle der GridPane, somit muss nur einmalig ein
                // Event Listener gesetzt werden und die Bilder werden dann einfach immer von der einen StackPane auf
                // die andere StackPane beim verschieben gesetzt
                StackPane slotStackPane = new StackPane();
                slotStackPane.setPrefSize(90, 90);
                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

                if(currTile.getTile().equals(TileNames.NNNN)) {
                    slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
                } else if(currTile.getTile().equals(TileNames.HHHH)) {
                    slotStackPane.setStyle("-fx-background-color: gray; -fx-border-color: " +
                            "black; -fx-border-width: 2; -fx-opacity: 0.5;");
                } else {
                    int tileEnumIndex = TileNames.valueOf(currTile.getTileString()).ordinal();
                    ImageView tileImageView;
                    tileImageView = this.imageViews[tileEnumIndex];
                    tileImageView.setFitHeight(90);
                    tileImageView.setFitWidth(90);
                }

                //wenn ein Bild Hintergrund Konstrukt bewegt wird per Drag
                slotStackPane.setOnDragOver(event -> {
                    if(event.getGestureSource() != slotStackPane && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.ANY); //Bild bewegung registrieren TODO vorher um zwischen Border und GameField bewegen zu unterscheiden MOVE da Border COPY ist
                    }
                    event.consume();
                });

                //wenn ein Bild Hintergrund Konstrukt ueber den Slot gezogen wird
                slotStackPane.setOnDragEntered(event -> {
                    if(event.getGestureSource() != slotStackPane && event.getDragboard().hasString()) {
                        slotStackPane.setStyle("-fx-background-color: pink;"); //Hintergrund faerben
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
                    if(db.hasString()) {
                        String inputString = db.getString();
                        //try da zwischen dieser und der rechten GridPane Indexe als String ausgetauscht werden und
                        // aus dem Editor ein Tile namens HHHH
                        try{ //normales Tile (nicht HHHH)
                            int parsedString = Integer.parseInt(db.getString());
                            System.out.println("Normal Tile: " + parsedString);
                        } catch (NumberFormatException e){ //HHHH
                            System.out.println("HHHH: " + inputString);
                        }

                        int imageViewIndex = Integer.parseInt(db.getString()); //der Index des bewegten ImageViews
                        ImageView droppedImageView = this.imageViews[imageViewIndex]; //das bewegte ImageViews

                        int targetX = GridPane.getColumnIndex(slotStackPane); //der Breitenindex dieses Slots
                        int targetY = GridPane.getRowIndex(slotStackPane); //der Hoehenindex dieses Slots
                        System.out.println(targetX + " " + targetY);

                        //die GridPane aus welcher das Bild kommt
                        GridPane sourceGridPane = (GridPane) droppedImageView.getParent().getParent();
                        //der vorherige Slot (StackPane) auf welcher das Bild vorher lag
                        StackPane sourceSlotStackPane = (StackPane) droppedImageView.getParent();
                        if(sourceGridPane == this.gridPane){ //das Bild kommt aus dem mittleren Spielfeld
                            Integer startX = GridPane.getColumnIndex(sourceSlotStackPane);
                            Integer startY = GridPane.getRowIndex(sourceSlotStackPane);
                            boolean moved = game.moveTileFromGamefieldToGameField(startX, startY, targetX, targetY);
                            if(moved) {
                                System.out.println("Tile moved within the game field from (" + startX + ", " + startY + ") to (" + targetX + ", " + targetY + ")");
                                sourceSlotStackPane.getChildren().remove(droppedImageView); //Bild aus alten Slot entfernen
                                slotStackPane.getChildren().add(droppedImageView); //Bild in neuen Slot einfuegen
                            }
                        } else if(sourceGridPane == this.rightGridPane){ //das Bild kommt aus der rechten Auswahl
                            boolean moved = game.moveTileFromNotLaidTilesToGameField(targetX, targetY, imageViewIndex);
                            if(moved) {
                                System.out.println("Tile successfully moved from selection to game field at (" + targetX + ", " + targetY + ")");
                                //Bild aus alten Slot entfernen
                                sourceSlotStackPane.getChildren().remove(droppedImageView);
                                //Bild an die groeße des Hintergrunds (StackPane) anpassen
                                droppedImageView.setFitWidth(slotStackPane.getWidth());
                                droppedImageView.setFitHeight(slotStackPane.getHeight());
                                slotStackPane.getChildren().add(droppedImageView); //Bild in neuen Slot einfuegen
                            }
                        }
                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });

                slotStackPane.setOnDragDetected(event -> {
                    if(!slotStackPane.getChildren().isEmpty()) { //nicht leer also Bild
                        Dragboard db = slotStackPane.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        ImageView currImageView = (ImageView) slotStackPane.getChildren().getFirst();
                        int tileEnumIndex = this.getImageViewIndex(currImageView);
                        content.putString(Integer.toString(tileEnumIndex));
                        db.setContent(content);
                        System.out.println("Dragging image Index: " + tileEnumIndex);
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
     * @param tiles die verfuegbaren Spielsteine
     */
    public void displayNotUsedTiles(Game game, Tiles tiles) {
        this.rightGridPane.getChildren().clear(); //entfernt alle bestehenden Bilder

        for (Tile t : tiles.getTiles()) {
            if (t != null)
                System.out.println(t.getTileString());
            else
                System.out.println("Null");
        }

        //Abstand zwischen den Spalten und Reihen
        this.rightGridPane.setHgap(10);
        this.rightGridPane.setVgap(10);

        int col = 0, row = 0; //aktuelle Spalte und Reihe der Auswahl der Spielsteine

        for (Tile currTile : tiles.getTiles()) { //durchlaeuft jeden Spielstein

            //da NNNN und HHHH nicht legbar sind, sollen sie auch nicht in der Auswahl auftauchen
            if(!(currTile != null && (currTile.getTile() == TileNames.NNNN || currTile.getTile() == TileNames.HHHH))) {

                //Hintergrund als StackPane da man diese faerben kann
                //die StackPane bleibt durchgehend an derselben Stelle der GridPane, somit muss nur einmalig ein
                // Event Listener gesetzt werden und die Bilder werden dann einfach immer von der einen StackPane auf
                // die andere StackPane beim verschieben gesetzt
                StackPane slotStackPane = new StackPane();
                slotStackPane.setPrefSize(80, 80);
                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

                if (currTile != null) {
                    String tileName = currTile.getTileString();
                    if (currTile.isTileLayable()) { //wenn nicht NNNN und HHHH da diese kein Bild haben
                        int tileEnumIndex = TileNames.valueOf(tileName).ordinal();
                        ImageView imageView = this.imageViews[tileEnumIndex]; //das ImageView des aktuellen Tile Spielsteins
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(80);
                        slotStackPane.getChildren().add(imageView); //ImageView der Stackpane hinzufuegen
                    }
                }

                //Drag Entered Event: wenn ein Bild ueber die Spielstein Auswahl gezogen wird, wird diese hervorgehoben
                rightGridPane.setOnDragEntered(event -> {
                    if (((StackPane) event.getGestureSource()).getParent() != this.rightGridPane && event.getDragboard().hasString()) { //ist String
                        rightGridPane.setStyle("-fx-background-color: pink;"); //setzt die Hintergrundfarbe der StackPane
                    }
                });

                //drag Exited Event wenn das Bild wieder aus der Spielstein Auswahl herausgezogen wird,
                // wird diese hervorhebung zurueckgesetzt
                rightGridPane.setOnDragExited(event -> {
                    rightGridPane.setStyle(""); //entfernt die Hintergrundfarbe
                });

                //Drag Detected Event für die Spielstein Auswahl: wenn dieses StackPane ImageView Konstrukt wieder aus der
                // Auswahl per Drag bewegt wird
                int finalCol = col;
                int finalRow = row;
                slotStackPane.setOnDragDetected((MouseEvent event) -> {
                    if (!slotStackPane.getChildren().isEmpty()) { //nicht leer also Bild
                        Dragboard db = slotStackPane.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        ImageView currImageView = (ImageView) slotStackPane.getChildren().getFirst();
                        int tileEnumIndex = this.getImageViewIndex(currImageView);
                        content.putString(Integer.toString(tileEnumIndex));
                        db.setContent(content);
                        System.out.println("Dragging image Index: " + tileEnumIndex);
                    }
                    System.out.println("Coordinates: " + finalCol + " " + finalRow);
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

                //Drop Event für das die Spielstein Auswahl: wenn das Bild in der rigthGridPane abgelegt wird
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
                        boolean returned = game.moveTileFromGamefieldToNotLaidTileSelection(startX, startY);
                        if (returned) {
                            int xIndex = imageViewIndex / 8; //die Spalte wo der Spielstein in der
                            // Spielstein Auswahl urspruenglich mal war
                            int yIndex = imageViewIndex % 8; //die Reihe wo der Spielstein in der
                            // Spielstein Auswahl urspruenglich mal war

                            StackPane targetCellStackPane = this.getGridPaneCell(xIndex, yIndex, this.rightGridPane);
                            //der vorherige Slot (StackPane) auf welcher das Bild vorher lag
                            StackPane sourceSlotStackPane = (StackPane) droppedImageView.getParent();
                            sourceSlotStackPane.getChildren().remove(droppedImageView); //das Bild vom alten Slot losbinden
                            droppedImageView.setFitWidth(80);
                            droppedImageView.setFitHeight(80);
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
                row++; //nach jedem durchlauf in die naechste Zeile
                if (row == 8) { // Nach 8 Spalten neue Zeile beginnen
                    row = 0; // wieder in der obersten Reihe beginnen
                    col++;
                }
            }
        }
    }

    /**
     * Methode welche Anhand des imageViews Arrays welches alle ImageViews enthaehlt den Index des uebergebenen findet
     * @param imageView das uebergebene ImageView zu welchem der Index gesucht wird
     * @return der Index des uebergebenen Bildes oder -1 wenn es nicht gefunden wurde
     */
    private int getImageViewIndex(ImageView imageView){
        int result = -1;
        for(int i = 0; i < this.imageViews.length; i++){ //durchlaeuft jedes ImageView
            if(this.imageViews[i].equals(imageView)){ //wenn das aktuelle ImageView das uebergebene ist
                result = i; //den Index speichern
            }
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
        int width = gameField.getGameField().length;
        int height = gameField.getGameField()[0].length;

        for(int x = 0; x < width; x++) { //durchlaeuft jede Spalte
            for(int y = 0; y < height; y++) { //durchlaeuft jede Zeile
                //filtert die Ecken raus, da diese nicht als Rand angezeigt werden sollen
                //initiales Setzen des Randes (falls ein bestehendes Spiel geladen wurde)
                if(!gameField.isFieldEdge(x, y) && gameField.isFieldBorder(x, y)) { //Randstueck und kein Eckstueck

                    Pane cell = new Pane();
                    String initialCellStyle = switch (gameField.getTile(x, y).getTile()) { //Statement welches je nach Randstein den Rand faerbt
                        case TileNames.GGGG ->
                                "-fx-border-color: black; -fx-border-width: 2; -fx-background-color: green;"; //Gruen
                        case TileNames.YYYY ->
                                "-fx-border-color: black; -fx-border-width: 2; -fx-background-color: yellow;"; //Gelb
                        case TileNames.RRRR ->
                                "-fx-border-color: black; -fx-border-width: 2; -fx-background-color: red;"; //rot
                        default -> "-fx-border-color: black; -fx-border-width: 2;"; //leere Zelle mit Rand
                    };
                    cell.setStyle(initialCellStyle);
                    cell.setUserData(initialCellStyle);


                    //Drag Over Event: dass die Zellen kopiert werden
                    cell.setOnDragOver(event -> {
                        if(event.getGestureSource() != cell && event.getDragboard().hasString()) { //ist String
                            event.acceptTransferModes(TransferMode.COPY); //wird bewegt und nicht z.B. kopiert
                        }
                        event.consume(); //markiert das Event als verarbeitet
                    });

                    //Drag Entered Event: wenn ein Bild ueber ein potentielles Ziel gezogen wird, wird dieses hervorgehoben
                    cell.setOnDragEntered(event -> {
                        if(event.getGestureSource() != cell && event.getDragboard().hasString()) { //ist String
                            cell.setStyle("-fx-border-color: black; -fx-border-width: 2;" +
                                    "-fx-background-color: pink;"); //setzt die Hintergrundfarbe der StackPane
                        }
                    });

                    //drag Exited Event wenn das Bild wieder aus dem moeglichen Ziel herausgezogen wird,
                    // wird diese hervorhebung zurueckgesetzt
                    cell.setOnDragExited(event -> {
                        String originalStyle = (String) cell.getUserData(); //den letzten Style
                        cell.setStyle(originalStyle);
                    });

                    //Drop Event für das Spielfeld: wenn das Bild in einer Zelle tilePane abgelegt wird
                    cell.setOnDragDropped(event -> {
                        Dragboard db = event.getDragboard(); //Inhalt was bewegt wird
                        if(db.hasString()) { //wenn das zu farbkodierende Objekt ein String ist
                            //Zielkoordinaten
                            Integer targetX = GridPane.getColumnIndex(cell);
                            Integer targetY = GridPane.getRowIndex(cell);

                            //durch eine Game Klasse Methode wird die Bewegung von der Spielstein Auswahl in
                            // das Logik Spielfeld uebernommen
                            Tile newTile = new Tile(TileNames.valueOf(db.getString()));
                            boolean placed = game.colorBorder(targetX, targetY, newTile);
                            if(placed) {
                                System.out.println("Tile successfully moved from selection to game field at (" + targetX + ", " + targetY + ")");

                                //setzt die Farbe des RandElements im Spielfeld
                                String cellStyle = "-fx-border-color: black; -fx-border-width: 2;" +
                                        borderTileNameToColorNameString(newTile.getTile());
                                cell.setStyle(cellStyle);

                                //speichert den aktuellen Style damit dieser falls das Spielfeld durch setOnDragExited
                                // zurueckgesetzt wird dieses den letzten Style anzeigt und nicht einfach nichts
                                cell.setUserData(cellStyle);
                            }

                            event.setDropCompleted(true);
                        } else {
                            event.setDropCompleted(false);
                        }
                        event.consume(); //markiert das Event als verarbeitet
                    });

                    this.gridPane.add(cell, x, y);
                }
            }
        }
    }

    /**
     * Methode welche aus einem bestimmten TileNames Element sofern es einfarbig ist eine Farbe welche CSS kompatibel
     * ist zurueckgibt
     * @param input das TileNames Element
     * @return die CSS Kompatible Farbe
     */
    private String borderTileNameToColorNameString(TileNames input){
        String colorString;
        switch (input){
            case TileNames.GGGG:
                colorString = "-fx-background-color: green;";
                break;
            case TileNames.YYYY:
                colorString = "-fx-background-color: yellow;";
                break;
            case TileNames.RRRR:
                colorString = "-fx-background-color: red;";
                break;
            default:
                colorString = "";
                break;
        }
        return(colorString);
    }


    /**
     * Methode welche die Editor Elemente in der rechten GridPane Auswahl anzeigt
     * @param withHoles ob in das Spielfeld auch Loecher sollen (wenn nicht wird der Loch Spielstein nicht angezeigt)
     */
    public void fillRightGridPaneWithEditorPieces(boolean withHoles){
        this.rightGridPane.getChildren().clear(); //entfernt alle bestehenden Bilder

        //Abstand zwischen den Spalten und Reihen
        this.rightGridPane.setHgap(10);
        this.rightGridPane.setVgap(10);

        //rot, gruen und gelbe Auswahl fuer den Rand
        Label randLabel = new Label("Randsteine:"); //Schriftzug
        this.rightGridPane.add(randLabel, 0, 4, 3, 1); // über drei Spalten, eine Zeile


        StackPane cell = new StackPane();
        cell.setPrefSize(80, 80);
        cell.setStyle("-fx-background-color: green;"); //Gruen
        this.applyDragEventsForNode(cell, TileNames.GGGG, "-fx-background-color: green; -fx-border-width: 2;");
        this.rightGridPane.add(cell, 0, 5); //Zelle der GridPane hinzufuegen
        cell = new StackPane();
        cell.setPrefSize(80, 80);
        cell.setStyle("-fx-background-color: yellow;"); //Gelb
        this.applyDragEventsForNode(cell, TileNames.YYYY, "-fx-background-color: yellow; -fx-border-width: 2;");
        this.rightGridPane.add(cell, 1, 5); //Zelle der GridPane hinzufuegen
        cell = new StackPane();
        cell.setPrefSize(80, 80);
        cell.setStyle("-fx-background-color: red;"); //rot
        this.applyDragEventsForNode(cell, TileNames.RRRR, "-fx-background-color: red; -fx-border-width: 2;");
        this.rightGridPane.add(cell, 2, 5); //Zelle der GridPane hinzufuegen

        //Loch Spielstein fuer den Editor
        if(withHoles) {
            Label lochSteinLabel = new Label("Lochstein:"); //Schriftzug
            this.rightGridPane.add(lochSteinLabel, 0, 14, 3, 1); // über drei Spalten, eine Zeile
            cell = new StackPane();
            cell.setPrefSize(80, 80);
            cell.setStyle("-fx-background-color: gray;"); //rot
            this.applyDragEventsForNode(cell, TileNames.HHHH, "-fx-background-color: gray; -fx-border-width: 2;");
            this.rightGridPane.add(cell, 0, 15); //Zelle der GridPane hinzufuegen
        }
    }


    private void applyDragEventsForNode(Node node, TileNames nodeColor, String cssDragEnteredStyle) {

        // Drag starten, wenn man auf das Objekt klickt
        node.setOnDragDetected(event -> {
            Dragboard db = node.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(nodeColor.toString());
            db.setContent(content);
            event.consume();
        });

        /*
        node.setOnDragEntered(event -> {
            if(event.getGestureSource() != node && event.getDragboard().hasImage()) {
                node.setStyle(cssDragEnteredStyle);
            }
            event.consume();
        });

        node.setOnDragExited(event -> {
            node.setStyle("-fx-border-width: 2;"); // Ursprünglicher Stil wiederherstellen
            event.consume();
        });
         */

        node.setOnDragOver(event -> {
            if(event.getGestureSource() != node && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        node.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if(db.hasImage()) {
                ImageView imageView = new ImageView(db.getImage());
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);

                // Optional: remove existing children first if Pane
                if(node instanceof Pane pane) {
                    pane.getChildren().clear();
                    pane.getChildren().add(imageView);
                }

                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }


}
