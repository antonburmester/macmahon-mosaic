package gui;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.*;

import java.util.Objects;

/**
 * Klasse durch welche die Logik veraenderungen der GUI durchfuehren kann.
 * Die JavaFXGUI wird vom UserInterfaceController erzeugt und als Parameter an die Logik uebergeben.
 *
 * @author Anton Burmester
 */
public class JavaFXGUI implements GUIConnector {

    private GridPane gridPane;

    private Pane centerPane;

    private BorderPane borderPane;

    private GridPane rightGridPane;

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
        for (Node node : gridPane.getChildren()) { //durchlaeuft jede Zelle und node ist die unterste Ebene des Inhalts
            if (node instanceof StackPane tilePane) { //StackPane, da in meinem Code die unterste Ebene eine StackPane ist //TODO ok da instanceof genutzt
                tilePane.setPrefSize(cellSize, cellSize); //Setzt die Größe der StackPane
                for (Node child : tilePane.getChildren()) { //durchlaeuft jede naechste Ebene der StackPane da dort
                    // das ImageView kommt
                    if (child instanceof ImageView imageView) {
                        //Bindung aufheben
                        imageView.fitWidthProperty().unbind();
                        imageView.fitHeightProperty().unbind();

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

        for (int x = 1; x < width - 1; x++) { //durchlaeuft jede Spalte
            for (int y = 1; y < height - 1; y++) { //durchlaeuft jede Zeile
                //StackPane für die Zelle da eine StackPane gefaerbt werden kann
                StackPane tilePane = new StackPane();
                tilePane.setPrefSize(90, 90); //Größe der StackPane
                tilePane.setStyle("-fx-border-color: black; -fx-border-width: 2;"); //rand fuer die StackPane

                //die naechste Ebene der StackPane ist das ImageView in welchem ein Bild angezeigt wird
                ImageView tileImageView = new ImageView();
                //Breite und Hoehe des Bildes an Breite und Hoehe der TilePane binden
                tileImageView.setFitHeight(90);
                tileImageView.setFitWidth(90);
                tileImageView.setPreserveRatio(false);
                // Entferne alte Bindings (falls welche existieren)
                /*
                tileImageView.fitWidthProperty().unbind();
                tileImageView.fitHeightProperty().unbind();
                tileImageView.fitWidthProperty().bind(tilePane.widthProperty());
                tileImageView.fitHeightProperty().bind(tilePane.heightProperty());

                 */

                //Drag Over Event: dass nur Bilder bewegt werden koennen und nicht z.B. kopiert
                tilePane.setOnDragOver(event -> {
                    if (event.getGestureSource() != tileImageView && event.getDragboard().hasImage()) { //ist Image
                        event.acceptTransferModes(TransferMode.MOVE); //wird bewegt und nicht z.B. kopiert
                    }
                    event.consume(); //markiert das Event als verarbeitet
                });

                //Drag Entered Event: wenn ein Bild ueber ein potentielles Ziel gezogen wird, wird dieses hervorgehoben
                tilePane.setOnDragEntered(event -> {
                    if (event.getGestureSource() != tileImageView && event.getDragboard().hasImage()) { //ist Image
                        tilePane.setStyle("-fx-background-color: pink;"); //setzt die Hintergrundfarbe der StackPane
                    }
                });

                //drag Exited Event wenn das Bild wieder aus dem moeglichen Ziel herausgezogen wird,
                // wird diese hervorhebung zurueckgesetzt
                tilePane.setOnDragExited(event -> {
                    tilePane.setStyle(""); //entfernt die Hintergrundfarbe und den Rand
                    tilePane.setStyle("-fx-border-color: black; -fx-border-width: 2;"); //setzt den Rand wieder
                });

                //Drop Event für das Spielfeld: wenn das Bild in einer Zelle tilePane abgelegt wird
                tilePane.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard(); //Inhalt was bewegt wird
                    if (db.hasImage()) { //wenn das zu verschiebende Objekt ein Bild ist
                        //Zielkoordinaten
                        Integer targetX = GridPane.getColumnIndex(tilePane);
                        Integer targetY = GridPane.getRowIndex(tilePane);

                        ImageView sourceImageView = (ImageView) event.getGestureSource(); //das zu verschiebende Bild

                        //überprüfen, ob das Tile aus der Auswahl (rightGridPane) oder vom Spielfeld (gridPane) kommt
                        if (rightGridPane.getChildren().contains(sourceImageView.getParent())) {
                            //Tile kommt aus der Auswahl
                            int tileIndex =
                                    getTileIndexFromRightGridPane(GridPane.getColumnIndex(sourceImageView.getParent()),
                                    GridPane.getRowIndex(sourceImageView.getParent()),
                                    rightGridPane.getRowCount()); //der Index nach TileNames Reihenfolge

                            //durch eine Game Klasse Methode wird die Bewegung von der Spielstein Auswahl in
                            // das Logik Spielfeld uebernommen
                            boolean placed = game.moveTileFromNotLaidTilesToGameField(tileIndex, targetX, targetY);
                            if (placed) {
                                System.out.println("Tile successfully moved from selection to game field at (" + targetX + ", " + targetY + ")");
                                //entfernt das Bild aus der Spielstein Auswahl
                                StackPane parentPane = (StackPane) sourceImageView.getParent();
                                ImageView imageView = (ImageView) parentPane.getChildren().getFirst();
                                imageView.setImage(null);//entfernt das Bild der Zelle
                                //setzt das Bild im Spielfeld
                                tileImageView.setImage(db.getImage());
                            }
                        } else if (gridPane.getChildren().contains(sourceImageView.getParent())) {
                            //Tile kommt vom Spielfeld
                            Integer startX = GridPane.getColumnIndex(sourceImageView.getParent());
                            Integer startY = GridPane.getRowIndex(sourceImageView.getParent());

                            if (startX != null && startY != null) {
                                //durch eine Game Klasse Methode wird die Bewegung innerhalb des Spielfelds in
                                // das Spielfeld der Game Instanz des Logik Package uebernommen
                                boolean moved = game.moveTileFromGamefieldToGameField(startX, startY, targetX, targetY);
                                if (moved) {
                                    System.out.println("Tile moved within the game field from (" + startX + ", " + startY + ") to (" + targetX + ", " + targetY + ")");
                                    //Spielstein aus alter Zelle loeschen
                                    StackPane parentPane = (StackPane) sourceImageView.getParent();
                                    ImageView imageView = (ImageView) parentPane.getChildren().getFirst();
                                    imageView.setImage(null);//entfernt das Bild der Zelle
                                    //setzt das Bild im Spielfeld
                                    tileImageView.setImage(db.getImage());
                                }
                            }
                        }

                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume(); //markiert das Event als verarbeitet
                });

                //Drag Detected Event für das Spielfeld: wenn ein Spielstein im Feld per Drag bewegt wird
                tileImageView.setOnDragDetected(event -> {
                    Dragboard db = tileImageView.startDragAndDrop(TransferMode.MOVE); //Dragboard um Spielstein zu ziehen
                    ClipboardContent content = new ClipboardContent(); //Clipboard Objekt um das Bild zu speichern
                    content.putImage(tileImageView.getImage());
                    db.setContent(content); //fuegt das Bild in das Dragboard zum verschieben
                    System.out.println("Dragging image: " + tileImageView.getImage());
                    event.consume(); //markiert das Event als verarbeitet
                });

                tilePane.getChildren().add(tileImageView); //ImageView der Stackpane hinzufuegen

                this.gridPane.add(tilePane, x, y); //StackPane welche das ImageView enthaelt dem GridPane in der
                // aktuellen Zelle hinzufuegen
            }
        }
        adjustGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
        displayBorder(gameField);
    }

    /**
     * Methode welche alle verfuegbaren Spielsteine rechts neben dem Spielfeld anzeigt
     * @param game Spiel Instanz aus welcher die Methoden kommen um die Bewegung eines Spielsteins der Logik mitzuteilen
     * @param tiles die verfuegbaren Spielsteine
     */
    public void displayNotUsedTiles(Game game, Tiles tiles) {
        this.rightGridPane.getChildren().clear(); //entfernt alle bestehenden Bilder

        //Abstand zwischen den Spalten und Reihen
        this.rightGridPane.setHgap(10);
        this.rightGridPane.setVgap(10);

        int col = 0, row = 0; //aktuelle Spalte und Reihe der Auswahl der Spielsteine

        for (Tile currTile : tiles.getTiles()) { //durchlaeuft jeden Spielstein
            String tileName = currTile.getTileString();
            if (currTile.isTileLayable()) { //da NNNN und HHHH Tile sind aber nicht in die Auswahl sollen
                //Index des aktuellen Spielsteins nach TileNames Reihenfolge
                int tileEnumIndex = TileNames.valueOf(tileName).ordinal();
                ImageView currImageView = this.imageViews[tileEnumIndex]; //das ImageView des aktuellen Tile Spielsteins

                StackPane tilePane = new StackPane(); //der Hintergrund des ImageViews da diese keine Hintergrundfarbe
                //haben koennen
                ImageView tileImageView = new ImageView(); //das ImageView welches das Bild des Spielsteins enthaelt
                tileImageView.setImage(currImageView.getImage()); //TODO tileImageView redundant da currImageView
                tileImageView.setFitWidth(80); //Feste Größe
                tileImageView.setFitHeight(80);

                tilePane.getChildren().add(tileImageView); //ImageView der Stackpane hinzufuegen

                //Drag Entered Event: wenn ein Bild ueber die Spielstein Auswahl gezogen wird, wird diese hervorgehoben
                rightGridPane.setOnDragEntered(event -> {
                    if (event.getGestureSource() != tileImageView && event.getDragboard().hasImage()) { //ist Image
                        rightGridPane.setStyle("-fx-background-color: pink;"); //setzt die Hintergrundfarbe der StackPane
                    }
                });

                //drag Exited Event wenn das Bild wieder aus der Spielstein Auswahl herausgezogen wird,
                // wird diese hervorhebung zurueckgesetzt
                rightGridPane.setOnDragExited(event -> {
                    rightGridPane.setStyle(""); //entfernt die Hintergrundfarbe
                });

                //Drag Detected Event für die Spielstein Auswahl: wenn ein Spielstein aus der Auswahl per Drag bewegt wird
                int finalCol = col;
                int finalRow = row;
                tileImageView.setOnDragDetected((MouseEvent event) -> {
                    Dragboard db = tileImageView.startDragAndDrop(TransferMode.ANY); //Dragboard um Spielstein zu ziehen
                    ClipboardContent content = new ClipboardContent(); //Clipboard Objekt um das Bild zu speichern
                    content.putImage(tileImageView.getImage());
                    db.setContent(content); //fuegt das Bild in das Dragboard zum verschieben
                    System.out.println("Dragging image: " + tileImageView.getImage());
                    System.out.println("Coordinates: " + finalCol + " " + finalRow);
                    System.out.println("TileNames Index of according Tile: " + getTileIndexFromRightGridPane(finalCol, finalRow, rightGridPane.getRowCount()));
                    event.consume(); //markiert das Event als verarbeitet
                });

                //Drag Over Event: dass nur Bilder bewegt werden koennen und nicht z.B. kopiert
                tilePane.setOnDragOver(event -> {
                    if (event.getGestureSource() != tileImageView && event.getDragboard().hasImage()) { //ist Image
                        event.acceptTransferModes(TransferMode.MOVE); //wird bewegt und nicht z.B. kopiert
                    }
                    event.consume(); //markiert das Event als verarbeitet
                });

                //Drop Event für das die Spielstein Auswahl: wenn das Bild in der rigthGridPane abgelegt wird
                this.rightGridPane.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard(); //Inhalt was bewegt wird
                    if (db.hasImage()) { //wenn das zu verschiebende Objekt ein Bild ist
                        ImageView sourceImageView = (ImageView) event.getGestureSource(); //das zu verschiebende Bild

                        if (gridPane.getChildren().contains(sourceImageView.getParent())) {
                            //Tile kommt vom Spielfeld zurück in die Auswahl und nicht von innerhalb der
                            // Spielstein Auswahl

                            //Ursprung des zu Verschiebenden Bildes (Tile) aus dem Spielfeld
                            Integer startX = GridPane.getColumnIndex(sourceImageView.getParent());
                            Integer startY = GridPane.getRowIndex(sourceImageView.getParent());

                            if (startX != null && startY != null) {
                                String TileName = game.getTileNameFromGameField(startX, startY); //der Spielsteinname
                                // anhand der Position im GameField der Game Instanz in dem Logik Package
                                int tileEnumPosition = TileNames.valueOf(TileName).ordinal(); //
                                int rightPaneCol = tileEnumPosition / 8; //die Spalte wo der Spielstein in der
                                // Spielstein Auswahl urspruenglich mal war
                                int rightPaneRow = tileEnumPosition % 8; //die Reihe wo der Spielstein in der
                                // Spielstein Auswahl urspruenglich mal war

                                //durch eine Game Klasse Methode wird die Bewegung vom Spielfeld in die Spielstein
                                // Auswahl in das Spielfeld der Game Instanz des Logik Package uebernommen
                                boolean returned = game.moveTileFromGamefieldToNotLaidTileSelection(startX, startY);
                                if (returned) {
                                    System.out.println("Tile moved back from game field to selection.");
                                    //Spielstein aus alter Zelle loeschen
                                    StackPane parentPane = (StackPane) sourceImageView.getParent();
                                    ImageView imageView = (ImageView) parentPane.getChildren().getFirst();
                                    imageView.setImage(null);//entfernt das Bild der Zelle

                                    //setzt den neuen Spielstein fuer die Spielstein Auswahl //TODO redundant glaube ich da imageView
                                    ImageView newTileImageView = new ImageView(db.getImage());
                                    newTileImageView.setFitWidth(80);
                                    newTileImageView.setFitHeight(80);

                                    //der Hintergrund des ImageViews da diese keine Hintergrundfarbe
                                    //haben koennen
                                    StackPane newTilePane = new StackPane();
                                    newTilePane.setPrefSize(80, 80);
                                    newTilePane.getChildren().add(newTileImageView); //das Bild der StackPane hinzufuegen

                                    //Drag Detected Event für die erneute Spielstein Auswahl: wenn ein Spielstein aus
                                    // der Auswahl per Drag bewegt wird
                                    newTileImageView.setOnDragDetected(e -> {
                                        // Dragboard um Spielstein zu ziehen
                                        Dragboard dragboard = newTileImageView.startDragAndDrop(TransferMode.MOVE);
                                        ClipboardContent content = new ClipboardContent(); //Clipboard Objekt um das
                                        // Bild zu speichern
                                        content.putImage(newTileImageView.getImage());
                                        dragboard.setContent(content); //fuegt das Bild in das Dragboard zum verschieben
                                        e.consume(); //markiert das Event als verarbeitet
                                    });

                                    // Füge das neue StackPane an die berechnete Position in rightGridPane hinzu
                                    rightGridPane.add(newTilePane, rightPaneCol, rightPaneRow);

                                }
                            }
                        }

                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume(); //markiert das Event als verarbeitet
                });
                this.rightGridPane.add(tilePane, col, row); //Zelle dem GridPane hinzufuegen

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
     * Methode welche den Index des aequivalenten Spielsteins nach TileNames Reihenfolge zurueckgibt
     * @param col die Spalte in welchem sich der Spielstein befindet
     * @param row die Reihe in welchem sich der Spielstein befindet
     * @param rowLength die Laenge der Reihen da in allen außer der ersten diese Laenge hinzugerechnet werden muss
     * @return der Index nach TileNames Reihenfolge
     */
    private int getTileIndexFromRightGridPane(int col, int row, int rowLength){
        return(col * rowLength + row);
    }

    /**
     * Methode welche die Raender anzeigt
     * //TODO Raender an Spielfeld binden
     * @param gameField das Spielfeld
     */
    public void displayBorder(GameField gameField){
        int width = gameField.getGameField().length;
        int height = gameField.getGameField()[0].length;

        for (int x = 0; x < width; x++) { //durchlaeuft jede Spalte
            for (int y = 0; y < height; y++) { //durchlaeuft jede Zeile
                //filtert die Ecken raus, da diese nicht als Rand angezeigt werden sollen
                if(!((x == 0 && y == 0) //linke obere Ecke
                        || (x == 0 && y == height - 1) //linke untere Ecke
                        || (x == width - 1 && y == 0) //rechte obere Ecke
                        || (x == width - 1 && y == height - 1))) //rechte untere Ecke
                if (y == 0 || y == height - 1) { //oberer und unterer Rand
                    Pane cell = new Pane();
                    cell.setStyle("-fx-background-color: purple;"); // Lila Hintergrund
                    this.gridPane.add(cell, x, y);
                } else if (x == 0 || x == width - 1) { //linker und rechter Rand
                    Pane cell = new Pane();
                    cell.setStyle("-fx-background-color: blue;"); // Lila Hintergrund
                    this.gridPane.add(cell, x, y);
                }
            }
        }
    }
}
