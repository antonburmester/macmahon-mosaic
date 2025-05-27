package gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import logic.CustomException;
import logic.Game;
import logic.GameField;
import logic.TileNames;


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
        this.gui = new JavaFXGUI(this.borderPane, this.centerPane, this.gridPane, this.rightGridPane);
        //this.game = new Game(this.gui); //erstaufruf welcher das beispielspiel initialisiert TODO wieder anmachen
        //this.setupGUI(this.game.getGameField().getGameField()[0].length,
                //this.game.getGameField().getGameField().length); TODO wieder anmachen
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
            int height = this.userHeightInput.getValue();
            int width = this.userWidthInput.getValue();
            if (height >= 2 && width >= 2 && height <= 6 && width <= 6) {
                this.game = new Game(this.gui, height, width);
                this.setupGUI(width, height);
            } else {
                ErrorHandler.showError(new CustomException(CustomException.ERROR_INVALID_GAME_SIZE));
            }
        //}
    }

    /**
     * Methode welche alle noetigen Grafik Methoden buendelt zum Anzeigen eines Spiels und aller noetigen Elemente
     * @param width die Breite des Spielfelds
     * @param height die Hoehe des Spielfelds
     */
    private void setupGUI(int width, int height){
        this.addAllSlotsWithDropListenerGridPane(this.rightGridPane, 3, 8);
        this.updateGridPaneFormat(height + 2, width + 2);
        this.addAllSlotsWithDropListenerGridPane(true, width + 2, height + 2);

        this.gui.displayGameFieldTiles(this.game, this.game.getGameField());
        this.gui.displayNotUsedTiles(this.game, this.game.getTiles());
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
     * Methode welche die GridPanes mit Hintergrund Slots (StackPane) fuellt und ihnen ueber die dazugehoerigen Methdoen
     * Listener gibt
     * @param middleGridPane ob es sich bei der gewuenschten GridPane um die mittlere handelt
     * @param xSize die x groeße des Spielfelds
     * @param ySize die y groeße des Spielfelds
     */
    public void addAllSlotsWithDropListenerGridPane(boolean middleGridPane, int xSize, int ySize){
        for(int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                StackPane slotStackPane = new StackPane(); //Slot welcher die Listener bekommt
                if(!GameField.isFieldEdge(x, y, xSize, ySize)) { //kein Eckstueck
                    slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";");
                }
                if(this.game.isFieldMiddleField(x, y)){ //Feld gehoert zum mittleren Spielfeld
                    this.addDropListenerMiddleGridPane(slotStackPane); //dem Slot die Listener hinzufuegen
                } else { //Feld gehoert zum Rand
                    this.addSlotListenerBorderGridPane(slotStackPane); //dem Slot die Listener hinzufuegen
                }
                this.gridPane.add(slotStackPane, x, y); //der GridPane den Slot hinzuefuegen
            }
        }
    }

    /**
     * Methode welche die Listener fuer die Rand Slots (StackPane) fuer den Farbwechsel setzt
     * @param inputStackPane der Slot welcher die Listener bekommen soll
     */
    private void addSlotListenerBorderGridPane(StackPane inputStackPane){
        inputStackPane.setOnMouseClicked(event -> {
            if(this.game.isEditorMode()) {
                int targetX = GridPane.getColumnIndex(inputStackPane);
                int targetY = GridPane.getRowIndex(inputStackPane);
                this.game.toggleBorderColor(targetX, targetY);
                String styleResultString;
                switch(this.game.getGameField().getTile(targetX, targetY).getTile()){
                    case TileNames.GGGG ->
                            styleResultString = "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE +
                                    "; -fx-background-color: " + JavaFXGUI.COLOR_HEX_CODE_GREEN + ";"; //Gruen
                    case TileNames.YYYY ->
                            styleResultString = "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE +
                                    "; -fx-background-color: " + JavaFXGUI.COLOR_HEX_CODE_YELLOW + ";"; //Gelb
                    case TileNames.RRRR ->
                            styleResultString = "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE +
                                    "; -fx-background-color: " + JavaFXGUI.COLOR_HEX_CODE_RED + ";"; //rot
                    //leere Zelle am Rand
                    default -> styleResultString = "-fx-border-color: black; -fx-border-width: " +
                            JavaFXGUI.BORDER_SIZE + ";";
                }
                inputStackPane.setStyle(styleResultString);
            }
        });
    }

    /**
     * Methode welche den Slots (StackPane) die Listener gibt
     * Nur fuer die mittleren Slots und nicht fuer den Rand
     * @param inputStackPane der Slot welcher die Listener bekommen soll
     */
    private void addDropListenerMiddleGridPane(StackPane inputStackPane){
        //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber den Slot gezogen wird
        inputStackPane.setOnDragEntered(event -> {
            Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
            if (event.getGestureSource() != inputStackPane && dragboard.hasString()) {
                if(inputStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also leer ist und keine Kinder hat

                    //Drop wird nur akzeptiert wenn es sich um ein Spielstein handelt im nicht Editormode oder ein Loch
                    // oder Randstein im EditorMode
                    if((!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)) ||
                            (game.isEditorMode() && (dragboard.getString().startsWith(JavaFXGUI.ID_HOLE) ||
                                    dragboard.getString().startsWith(JavaFXGUI.ID_BORDER)))){
                        //hintergrund faerben und Rand setzen
                        inputStackPane.setStyle("-fx-background-color: pink; " +
                                "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";");
                    }
                }
            }
        });

        //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber den Slot war und wieder weggezogen wird
        inputStackPane.setOnDragExited(event -> {
            //Hintergrund entfernen und Rand setzen
            inputStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";");
        });

        //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber diesen Slot gezogen wird
        inputStackPane.setOnDragOver(event -> {
            Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
            if (event.getGestureSource() != inputStackPane && dragboard.hasString()) {
                if(inputStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also leer ist und keine Kinder hat

                    //Drop wird nur akzeptiert wenn es sich um ein Spielstein handelt im nicht Editormode oder ein Loch
                    // oder Randstein im EditorMode
                    if ((!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)) ||
                            (game.isEditorMode() && (dragboard.getString().startsWith(JavaFXGUI.ID_HOLE) ||
                                    dragboard.getString().startsWith(JavaFXGUI.ID_BORDER)))) {
                        event.acceptTransferModes(TransferMode.MOVE); //Bild bewegung registrieren
                    }
                }
            }
            event.consume();
        });

        //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) auf den Slot gedropped wird
        inputStackPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                String inputString = db.getString();
                Object droppedObject = event.getGestureSource(); //das gedroppte Objekt (Loch als StackPane oder
                // Spielstein als ImageView)
                Node droppedObjectNode = (Node) droppedObject; //das gedroppte Objekt als Node
                StackPane droppedObjectParent = (StackPane) droppedObjectNode.getParent(); //die StackPane auf
                // welcher das gedroppte Objekt liegt
                GridPane droppedObjectSourceGridPane = (GridPane) droppedObjectParent.getParent(); //die
                // GridPane aus welcher das Objekt kommt
                GridPane droppedObjectTargetGridPane = (GridPane) inputStackPane.getParent();
                boolean dropSuccess = false;
                //woher das gedroppte Objekt kommt (x und y)
                //droppedObjectParent, da das droppedObjet auf diesem liegt und
                // getColumnIndex einen child der ersten Ebene braucht
                int sourceX = GridPane.getColumnIndex(droppedObjectParent);
                int sourceY = GridPane.getRowIndex(droppedObjectParent);
                //wohin das Objekt soll (x und y)
                int targetX = GridPane.getColumnIndex(inputStackPane);
                int targetY = GridPane.getRowIndex(inputStackPane);
                boolean isGameTile = droppedObjectNode instanceof ImageView; //wenn ImageView dann Spielstein
                // sonst Loch
                if(droppedObjectSourceGridPane == this.gridPane){ //Objekt kommt vom Spielfeld
                    if(droppedObjectTargetGridPane == this.gridPane){ //Objekt soll in das Spielfeld
                        dropSuccess =
                                this.game.moveTileFromGamefieldToGameField(sourceX, sourceY, targetX, targetY);
                    }
                } else { //Objekt kommt aus der rechten Spielstein Auswahl
                    if(droppedObjectTargetGridPane == this.gridPane){ //Objekt soll in das Spielfeld
                        dropSuccess = this.game.moveTileFromNotLaidTilesToGameField(
                                targetX, targetY, this.gui.getTileIndex(droppedObjectNode), isGameTile);
                    }
                }

                if(dropSuccess) {
                    droppedObjectParent.getChildren().remove(droppedObject); //das Objekt vom vorherigen slot loesen
                    //Groeße des gedroppten StackPane oder ImageView Elements anpassen
                    double slotWidth = inputStackPane.getWidth() - JavaFXGUI.BORDER_SIZE * 2; //*2 da Rand links
                    // und rechts
                    double slotHeight = inputStackPane.getHeight() - JavaFXGUI.BORDER_SIZE * 2;//*2 da Rand oben
                    // und unten
                    if (droppedObjectNode instanceof StackPane stackPane) { //Loch StackPane
                        stackPane.setPrefSize(slotWidth, slotHeight);
                    } else if (droppedObjectNode instanceof ImageView imageView) { //Spielstein Bild
                        imageView.setFitWidth(slotWidth);
                        imageView.setFitHeight(slotHeight);
                    }
                    inputStackPane.getChildren().add(droppedObjectNode); //das Objekt an den neuen Platz binden

                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
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
    private void addAllSlotsWithDropListenerGridPane(GridPane gridPane, int xSize, int ySize){
        for(int y = 0; y < ySize; y++){
            for(int x = 0; x < xSize; x++){
                StackPane slotStackPane = new StackPane();
                if(gridPane == this.rightGridPane){ //die rechte GridPane
                    // rechte GridPane braucht bezueglich ihrer Platzhalter eine groeße, da sonst sollte eine
                    // ganze reihe frei sein, diese sich in ihrer Hoehe und oder Breite zusammenzieht
                    slotStackPane.setPrefSize(JavaFXGUI.NOT_LAID_TILE_SIZE, JavaFXGUI.NOT_LAID_TILE_SIZE);
                }
                slotStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE + ";");

                //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber den Slot gezogen wird
                slotStackPane.setOnDragEntered(event -> {
                    Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
                    if (event.getGestureSource() != slotStackPane && dragboard.hasString()) {
                        if(slotStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also keine Kinder hat
                            //nur hervorheben wenn es sich um einen Spielstein handelt oder ein Loch im EditorMode

                            //Drag wird nur akzeptiert wenn es sich um ein Bild handelt oder ein Loch im EditorMode
                            if((!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)) ||
                                    (game.isEditorMode() && (dragboard.getString().startsWith(JavaFXGUI.ID_HOLE) ||
                                            dragboard.getString().startsWith(JavaFXGUI.ID_BORDER)))){
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
                                    (game.isEditorMode() && (dragboard.getString().startsWith(JavaFXGUI.ID_HOLE) ||
                                            dragboard.getString().startsWith(JavaFXGUI.ID_BORDER)))) {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE); //Bild bewegung registrieren
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
                        Object droppedObject = event.getGestureSource(); //das gedroppte Objekt (Loch als StackPane oder
                        // Spielstein als ImageView)
                        Node droppedObjectNode = (Node) droppedObject; //das gedroppte Objekt als Node
                        StackPane droppedObjectParent = (StackPane) droppedObjectNode.getParent(); //die StackPane auf
                        // welcher das gedroppte Objekt liegt
                        GridPane droppedObjectSourceGridPane = (GridPane) droppedObjectParent.getParent(); //die
                        // GridPane aus welcher das Objekt kommt
                        GridPane droppedObjectTargetGridPane = (GridPane) slotStackPane.getParent();
                        boolean dropSuccess = false;
                        //woher das gedroppte Objekt kommt (x und y)
                        //droppedObjectParent, da das droppedObjet auf diesem liegt und
                        // getColumnIndex einen child der ersten Ebene braucht
                        int sourceX = GridPane.getColumnIndex(droppedObjectParent);
                        int sourceY = GridPane.getRowIndex(droppedObjectParent);
                        //wohin das Objekt soll (x und y)
                        int targetX = GridPane.getColumnIndex(slotStackPane);
                        int targetY = GridPane.getRowIndex(slotStackPane);
                        boolean isGameTile = droppedObjectNode instanceof ImageView; //wenn ImageView dann Spielstein
                        // sonst Loch
                        if(droppedObjectSourceGridPane == this.gridPane){ //Objekt kommt vom Spielfeld
                            if(droppedObjectTargetGridPane == this.gridPane){ //Objekt soll in das Spielfeld
                                dropSuccess =
                                        this.game.moveTileFromGamefieldToGameField(sourceX, sourceY, targetX, targetY);
                            } else { //Objekt soll zurueck in die Auswahl
                                dropSuccess =
                                        this.game.moveTileFromGamefieldToNotLaidTileSelection(
                                                sourceX, sourceY, isGameTile);
                            }
                        } else { //Objekt kommt aus der rechten Spielstein Auswahl
                            if(droppedObjectTargetGridPane == this.gridPane){ //Objekt soll in das Spielfeld
                                if(this.game.isEditorMode()){ //Rand
                                    int tileIndex = Integer.parseInt(inputString.substring(1));
                                    dropSuccess = this.game.colorBorder(
                                            targetX, targetY, tileIndex);
                                } else { //Spielstein
                                    dropSuccess = this.game.moveTileFromNotLaidTilesToGameField(
                                            targetX, targetY, this.gui.getTileIndex(droppedObjectNode), isGameTile);
                                }
                            }
                        }

                        if(dropSuccess) {
                            droppedObjectParent.getChildren().remove(droppedObject); //das Objekt vom vorherigen slot loesen
                            //Groeße des gedroppten StackPane oder ImageView Elements anpassen
                            double slotWidth = slotStackPane.getWidth() - JavaFXGUI.BORDER_SIZE * 2; //*2 da Rand links
                            // und rechts
                            double slotHeight = slotStackPane.getHeight() - JavaFXGUI.BORDER_SIZE * 2;//*2 da Rand oben
                            // und unten
                            if (droppedObjectNode instanceof StackPane stackPane) {
                                stackPane.setPrefSize(slotWidth, slotHeight);
                            } else if (droppedObjectNode instanceof ImageView imageView) {
                                imageView.setFitWidth(slotWidth);
                                imageView.setFitHeight(slotHeight);
                            }
                            slotStackPane.getChildren().add(droppedObjectNode); //das Objekt an den neuen Platz binden

                            event.setDropCompleted(true);
                        } else {
                            event.setDropCompleted(false);
                        }
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
                            : middleColWidthSizePercentage); //Ternaerer Operator: wenn linkeste oder rechteste Reihe
                    // dann eine schmale Zelle in Bezug auf die Breite sonst fuer die mittleren dicke Zellen
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
                            : middleRowWidthSizePercentage);//Ternaerer Operator: wenn oberste oder unterste Reihe dann
                    // eine schmale Zelle in Bezug auf die Hoehe sonst fuer die mittleren dicke Zellen
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
        final double colCount = middleCols + 0.25d * 2; //0,5 da eine Randspalte 0,25 einer normalen ist und 0,5 da zwei
        final double rowCount = middleRows + 0.25d * 2; //0,5 da eine Randreihe 0,25 einer normalen ist und 0,5 da zwei

        double cellSize = Math.floor(Math.min(width / colCount, height / rowCount)); //die Zellengroeße abgerundet

        gridPane.setPrefSize(cellSize * colCount, cellSize * rowCount); //setzt diese Zellengroeße fuer GridPane
        double cellSizeWithoutBorder = cellSize - JavaFXGUI.BORDER_SIZE * 2; //abzueglich der Randgroeße * 2,
        // da jede Zelle einen Rand hat

        //aktualisierung der Bildgroeßen und Abstaende
        for(Node node : gridPane.getChildren()) { //durchlaeuft jede Zelle und node ist die unterste Ebene des Inhalts
            // also das StackPane

            if(node instanceof StackPane tilePane) { //StackPane, da die unterste Ebene eine StackPane ist
                tilePane.setPrefSize(cellSize, cellSize); //Setzt die Groeße der StackPane
                for(Node child : tilePane.getChildren()) { //durchlaeuft jede naechste Ebene der StackPane da dort
                    // das ImageView kommt
                    if(child instanceof ImageView imageView) {
                        //Groeße updaten

                        imageView.setFitWidth(cellSizeWithoutBorder);
                        imageView.setFitHeight(cellSizeWithoutBorder);
                    }
                }
            }
        }
    }
}