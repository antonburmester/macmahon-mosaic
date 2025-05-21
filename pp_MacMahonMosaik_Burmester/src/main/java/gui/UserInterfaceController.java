package gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import logic.CustomException;
import logic.Game;


/**
 * Main class for the user interface.
 *
 * @author mjo, cei, Anton Burmester
 */
public class UserInterfaceController {

    private Game game;

    private JavaFXGUI gui;

    @FXML
    private VBox editorControls;
    @FXML
    private Spinner<Integer> userHeightInput;
    @FXML
    private Spinner<Integer> userWidthInput;

    @FXML
    private GridPane gridPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Pane centerPane;

    @FXML
    private GridPane rightGridPane;

    /**
     * Initialisierung des Programms
     */
    @FXML
    public void initialize() {
        //luecke zwischen der Mitte und der rechten GridPane
        BorderPane.setMargin(rightGridPane, new Insets(0, 0, 0, 10));
        this.userHeightInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 6, 2));
        this.userWidthInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 6, 2));
        this.setDropListenerGridPane(this.rightGridPane, 3, 8);
        this.gui = new JavaFXGUI(this.borderPane, this.centerPane, this.gridPane, this.rightGridPane);
        //this.game = new Game(this.gui); //erstaufruf welcher das beispielspiel initialisiert TODO wieder anmachen
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein neues Spiel zu starten
     */
    public void restartGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein bestehendes Spiel zu laden
     */
    public void loadGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird das aktuelle Spiel zu speichern
     */
    public void saveGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein bestehendes Spiel zu beenden
     */
    public void endGame(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um den Editor Mode einzuschalten
     */
    public void toggleEditorMode(){
        gridPane.setGridLinesVisible(true);
        boolean isEditorMode = !editorControls.isManaged(); //wenn sichtbar dann unsichtbar und umgekehrt (toggle)
        editorControls.setVisible(isEditorMode); //macht die Spielfeldeingaben (Breite,Hoehe,Button) sichtbar/unsichtbar
        editorControls.setManaged(isEditorMode); // Entfernt den Platz, wenn unsichtbar und nimmt ihn ein wenn sichtbar
        if(this.game != null)
            this.game.setEditorMode(isEditorMode);
    }

    /**
     * Methode welche die Breite und Hoehe durch die Nutzereingaben einließt
     */
    public void applyEditorChanges(){
        //if(!this.userWidthInput.getText().isEmpty() && !this.userHeightInput.getText().isEmpty()) {
            int heigth = this.userHeightInput.getValue();
            int width = this.userWidthInput.getValue();
            if (heigth >= 2 && width >= 2 && heigth <= 6 && width <= 6) {
                this.updateGridPaneFormat(heigth + 2, width + 2);
                this.setDropListenerGridPane(this.gridPane, width + 2, heigth + 2);
                this.game = new Game(this.gui, heigth, width);
            } else {
                ErrorHandler.showError(new CustomException(CustomException.ERROR_INVALID_GAME_SIZE));
            }
        //}
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um zu pruefen, ob das bestehende Feld im aktuellen Zustand geloest
     * werden kann
     */
    public void checkSolvability(){

    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um einen Tipp (richtiger Spielstein an der richtigen Stelle)
     * zu legen
     */
    public void layHint(){

    }

    /**
     * Methode welche die GridPanes je nachdem welche (mittlere oder rechte) GridPane uebergeben wird die Drop Listener
     * StackPane Konstrukte setzt
     * Die Drop Listener werden auf eine StackPane gesetzt welche in dieser Methode pro Feld neu initialisiert wird
     * Mit diesen StackPanes wird dann das GridPane gefuellt
     * @param gridPane die jeweilige GridPane welche die Listener bekommt
     * @param xSize die Hoehe der GridPane
     * @param ySize die Breite der GridPane
     */
    private void setDropListenerGridPane(GridPane gridPane, int xSize, int ySize){
        for(int y = 0; y < ySize; y++){
            for(int x = 0; x < xSize; x++){
                StackPane slotStackPane = new StackPane();
                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";");

                //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber den Slot gezogen wird
                slotStackPane.setOnDragEntered(event -> {
                    Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
                    if (event.getGestureSource() != slotStackPane && dragboard.hasString()) {
                        if(slotStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also keine Kinder hat
                            //nur hervorheben wenn es sich um einen Spielstein handelt oder ein Loch im EditorMode

                            //Drag wird nur akzeptiert wenn es sich um ein Bild handelt oder ein Loch im EditorMode
                            if((!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)) ||
                                    (game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_HOLE))){
                                //hintergrund faerben und Rand setzen
                                slotStackPane.setStyle("-fx-background-color: pink; " +
                                        "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";");
                            }
                        }
                    }
                });

                //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber den Slot war und wieder weggezogen wird
                slotStackPane.setOnDragExited(event -> {
                    //Hintergrund entfernen und Rand setzen
                    slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";");
                });

                //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber diesen Slot gezogen wird
                slotStackPane.setOnDragOver(event -> {
                    Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
                    if (event.getGestureSource() != slotStackPane && dragboard.hasString()) {
                        if(slotStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also keine Kinder hat
                            //nur hervorheben wenn es sich um einen Spielstein handelt oder ein Loch im EditorMode

                            //Drag wird nur akzeptiert wenn es sich um ein Bild handelt oder ein Loch im EditorMode
                            if ((!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)) ||
                                    (game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_HOLE))) {
                                event.acceptTransferModes(TransferMode.MOVE); //Bild bewegung registrieren
                            }
                        }
                    }
                    event.consume();
                });

                //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) auf den Slot gedropped wird
                slotStackPane.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        String inputString = db.getString();
                        Object droppedObject = event.getGestureSource(); //das verschobene Objekt
                        Node droppedObjectNode = (Node) droppedObject; //Loch (StackPane) oder Spielstein (ImageView)
                        StackPane droppedObjectParent = (StackPane) droppedObjectNode.getParent(); //die StackPane auf
                        // welcher das gedroppte Objekt liegt
                        droppedObjectParent.getChildren().remove(droppedObject); //das Objekt vom vorherigen slot loesen
                        //Groeße des gedroppten StackPane oder ImageView Elements anpassen
                        double slotWidth = slotStackPane.getWidth() - JavaFXGUI.BORDER_SIZE * 2; //*2 da Rand links
                        // und rechts
                        double slotHeight = slotStackPane.getHeight() - JavaFXGUI.BORDER_SIZE * 2;//*2 da Rand oben
                        // und unten
                        if(droppedObjectNode instanceof StackPane stackPane){
                            stackPane.setPrefSize(slotWidth, slotHeight);
                        } else if(droppedObjectNode instanceof ImageView imageView){
                            imageView.setFitWidth(slotWidth);
                            imageView.setFitHeight(slotHeight);
                        }
                        slotStackPane.getChildren().add(droppedObjectNode); //das Objekt an den neuen Platz binden

                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });

                gridPane.add(slotStackPane, x, y);
                System.out.println("added StackPane at x: " + x + " y: " + y);
            }
        }
    }

    /**
     * Methode welche die Form der GridPane anzeigt
     * @param rows die neue hoehe des Spielfelds
     * @param columns die neue Breite des Spielfelds
     */
    public void updateGridPaneFormat(int rows, int columns){
        //Die Differenz der bestehenden GridSize Breite zur neuen
        int widthGrowLoss = columns - gridPane.getColumnCount();
        //Die Differenz der bestehenden GridSize Hoehe zur neuen
        int heigthGrowLoss = rows - gridPane.getRowCount();

        gridPane.setMinSize(0, 0); //minimalgroeße der GridPane

        double middleColWidthSizePercentage = 100 / (columns - 1.5d); //Breite der mittleren Felder
        double borderColWidthSizePercentage = middleColWidthSizePercentage / 4; //Breite der Rand Spalten Felder
        double middleRowWidthSizePercentage = 100 / (rows - 1.5d); //Hoehe der mittleren Felder
        double borderRowWidthSizePercentage = middleRowWidthSizePercentage / 4; //Breite der Rand Zeilen Felder

        // ChangeListener hinzufuegen, damit sich die GridPane durch die Pane an die
        // Groeßenveraenderung der BorderPane anpasst TODO Listener nur einmal setzen
        centerPane.widthProperty().addListener((obs, oldVal, newVal) ->
                adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));
        centerPane.heightProperty().addListener((obs, oldVal, newVal) ->
                adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));

        if(widthGrowLoss > 0){ //GirdPane soll groeßer bezueglich Breite werden (Spalten)

            int existingCols = gridPane.getColumnCount();
            for(int i = 0; i < columns; i++) { //von den bestehenden bis zur neuen Breite
                ColumnConstraints colConstraints;
                if(i >= existingCols){ //wenn das Spaltenobjekt noch nicht existiert
                    colConstraints = new ColumnConstraints();
                    colConstraints.setHgrow(Priority.ALWAYS);
                    gridPane.getColumnConstraints().add(i, colConstraints); //neue Constraints den Constraints
                    // hinzufuegen
                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt
                    colConstraints = gridPane.getColumnConstraints().get(i);
                }
                colConstraints.setPercentWidth((i == 0 || i == columns - 1) ? borderColWidthSizePercentage
                        : middleColWidthSizePercentage); //Ternaerer Operator: wenn linkeste oder rechteste Reihe dann
                // eine schmale Zelle in Bezug auf die Breite sonst fuer die mittleren dicke Zellen
            }
        } else if(widthGrowLoss < 0){ //GirdPane soll kleiner bezueglich Breite werden (Spalten)

            int existingCols = gridPane.getColumnCount();
            for(int i = existingCols - 1; i >= 0; i--) { //von den bestehenden bis zur neuen Breite
                if(i >= columns){ //wenn die zu loeschenden Spalten erreicht wurden
                    gridPane.getColumnConstraints().remove(i); //bestehende Constraints aus den Constraints loeschen
                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt
                    ColumnConstraints colConstraints;
                    colConstraints = gridPane.getColumnConstraints().get(i);
                    colConstraints.setPercentWidth((i == 0 || i == columns - 1) ? borderColWidthSizePercentage
                            : middleColWidthSizePercentage); //Ternaerer Operator: wenn linkeste oder rechteste Reihe dann
                    // eine schmale Zelle in Bezug auf die Breite sonst fuer die mittleren dicke Zellen
                }
            }
        }

        if(heigthGrowLoss > 0){ //GirdPane soll groeßer bezueglich Hoehe werden (mehr Zeilen)

            int existingRows = this.gridPane.getRowCount();
            for (int i = 0; i < rows; i++) {
                RowConstraints rowConstraints;
                if(i >= existingRows) { //wenn das Reihenobjekt noch nicht existiert
                    rowConstraints = new RowConstraints();
                    rowConstraints.setVgrow(Priority.ALWAYS);
                    gridPane.getRowConstraints().add(i, rowConstraints); //neue Constraints den Constraints
                    // hinzufuegen
                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt
                    rowConstraints = gridPane.getRowConstraints().get(i);
                }
                rowConstraints.setPercentHeight((i == 0 || i == rows - 1) ? borderRowWidthSizePercentage
                        : middleRowWidthSizePercentage);//Ternaerer Operator: wenn oberste oder unterste Reihe dann eine
                // schmale Zelle in Bezug auf die Hoehe sonst fuer die mittleren dicke Zellen
            }
        } else if(heigthGrowLoss < 0){ //GirdPane soll kleiner bezueglich Hoehe werden (weniger Zeilen)

            int existingRows = gridPane.getRowCount();
            for(int i = existingRows - 1; i >= 0; i--) { //von den bestehenden bis zur neuen Hoehe
                if(i >= rows){ //wenn die zu loeschenden Reihen erreicht wurden
                    gridPane.getRowConstraints().remove(i); //bestehende Constraints aus den Constraints loeschen
                } else { //wenn es schon existiert soll es nicht neu erstellt werden sondern aus den Constraints geholt
                    RowConstraints rowConstraints;
                    rowConstraints = gridPane.getRowConstraints().get(i);
                    rowConstraints.setPercentHeight((i == 0 || i == rows - 1) ? borderRowWidthSizePercentage
                            : middleRowWidthSizePercentage);//Ternaerer Operator: wenn oberste oder unterste Reihe dann eine
                    // schmale Zelle in Bezug auf die Hoehe sonst fuer die mittleren dicke Zellen
                }
            }
        }
        adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
    }


    /**
     * Methode welche die mittlere Spielflaeche sowie die Bilder in den Zellen dieser an die Groeße anpasst
     * @param gridPane Spielflaeche
     * @param width Breite des mittleren Flaeche auf welcher die GridPane liegt
     * @param height Hoehe des mittleren Flaeche auf welcher die GridPane liegt
     */
    private void adjustMiddleGridPaneSize(GridPane gridPane, double width, double height) {
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
}