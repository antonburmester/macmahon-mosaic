package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.CustomException;
import logic.Game;
import logic.GameField;
import logic.TileNames;

import java.io.File;
import java.util.Objects;


/**
 * Main class for the user interface.
 *
 * @author mjo, cei, Anton Burmester
 */
public class UserInterfaceController {

    private Game game; //Nutzlast der Game Instanz welche durch diese Klasse initialisiert wird

    private JavaFXGUI gui; //Nutzlast der GUI Instanz welche durch diese Klasse initialisiert wird

    //das Fenster insgesamt
    @FXML
    private BorderPane borderPane; //BorderPane welche den Hintergrund des Fensters darstellt und das Fenster in
    // 5 Bereiche einteilt (links(editorControls), rechts(rightGridPane), oben(Menue Bar (keine Nutzlast dieser Klasse),
    //  unten(nichts), mitte(Spielfeld)

    //Editor Bedienung (Links in der borderPane)
    @FXML
    private VBox editorControls; //Nutzlast der Flaeche auf welcher die Editor Controls liegen
    @FXML
    private Spinner<Integer> userHeightInput; //Nutzlast des Elements zur Auswahl der Spielfeldhoehe
    @FXML
    private Spinner<Integer> userWidthInput; //Nutzlast des Elements zur Auswahl der Spielfeldbreite

    //Spielfeld (mittig in der borderPane)
    @FXML
    private Pane centerPane; //Nutzlast der Flaeche auf welchem das mittlere Spielfeld liegt um die Groesse abfragen zu
    // koennen und diese zu skalieren, weil dies mit der GridPane direkt probleme verursacht
    @FXML
    private GridPane gridPane; //Nutzlast des mittleren Spielfelds

    //Spielsteinauswahl (rechts in der borderPane)
    @FXML
    private VBox rightVBox; //Nutzlast der rechten VBox in welcher die rightGridPane ist
    @FXML
    private GridPane rightGridPane; //Nutzlast der rechten Spielsteinauswahl

    /**
     * Initialisierung des Programms
     */
    @FXML
    public void initialize() {
        //Abstaende fuer die Spielfeldgroessen Auswahl (editorControls)
        BorderPane.setMargin(this.editorControls, new Insets(10, 0, 10, 10)); //links Abstand zum
        // Fensterrand; oben + unten Abstand zum Fensterrand sowie rechts Abstand zum mittleren Spielfeld (centerPane
        // und GridPane) = 0, da dieser im Spielfeld gehandhabt ist, da das Spielfeld permanent sichtbar ist und die
        // EditorControls nicht

        //Abstaende fuer das mittlere Spielfeld (gridPane) welches auf der centerPane liegt
        BorderPane.setMargin(this.centerPane, new Insets(10, 10, 10, 10)); //links Abstand zum
        // Fensterrand sowie der Groessenverstellung; oben und unten Abstand zum Fensterrand; rechts Abstand zur
        // Spielsteinauswahl

        //Abstaende fuer die rechte Spielsteinauswahl (rightGridPane)
        //BorderPane.setMargin(this.rightGridPane, new Insets(10, 10, 10, 0)); //links Abstand zum
        // Spielfeld = 0 da dieser schon in im Spielfeld (centerPane) gehandhabt wird; oben zum Menue; unten sowie
        // rechts Abstand zum Fensterrand

        rightVBox.setPadding(new Insets(10, 10, 10, 0));



        //Spielfeld Groessenauswahl Bedienung initialisieren (Bereich 2 - 6; Start 2)
        this.userHeightInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Game.MIN_GAMEFIELD_SIZE_WITHOUT_BORDER, Game.MAX_GAMEFIELD_SIZE_WITHOUT_BORDER,
                Game.MIN_GAMEFIELD_SIZE_WITHOUT_BORDER));

        this.userWidthInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Game.MIN_GAMEFIELD_SIZE_WITHOUT_BORDER, Game.MAX_GAMEFIELD_SIZE_WITHOUT_BORDER,
                Game.MIN_GAMEFIELD_SIZE_WITHOUT_BORDER));

        this.gui = new JavaFXGUI(this.borderPane, this.centerPane, this.gridPane, this.rightGridPane,
                this.editorControls, this.loadImages(), this.loadHolesStackPanes());

        // ChangeListener hinzufuegen, damit sich die GridPane durch die Pane an die
        // Groeßenveraenderung der BorderPane anpasst
        this.centerPane.widthProperty().addListener((obs, oldVal, newVal) ->
                this.adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));
        this.centerPane.heightProperty().addListener((obs, oldVal, newVal) ->
                this.adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight()));
        this.game = new Game(this.gui); //erstaufruf welcher das beispielspiel initialisiert
        Platform.runLater(() -> { //setupGUI Methode erst nachdem alles im Layout gesetzt wurde aufrufen
            this.setupGUI(this.game.getGameFieldCopy().getGameField()[0].length,
                this.game.getGameFieldCopy().getGameField().length);
            this.game.setIsGameActive(true, true); //TODO move to Game class
        });
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein neues Spiel zu starten
     */
    public void restartGame(){
        this.game.restartGame();
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein bestehendes Spiel zu laden
     */
    public void loadGame(){
        try {
            File file = openFileChooser(true);
            if (file != null) {
                String[][] field = logic.GameData.loadGame(file);
                this.game = new Game(this.gui, field);
                if(editorControls.isManaged()){ //alle Spielsteine aus dem Spielfeld entfernen wenn Editormode
                    this.game.removeGameFieldTiles();
                }
                this.setupGUI(this.game.getGameFieldCopy().getGameField()[0].length,
                        this.game.getGameFieldCopy().getGameField().length);
                this.game.setIsGameActive(true, true); //TODO move to game class
            }
        } catch (CustomException e){
            ErrorMessageHandler.showError(e);
        }
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird das aktuelle Spiel zu speichern
     */
    public void saveGame(){
        try{
            File file = openFileChooser(false);
            if(file != null) {
                logic.GameData.saveGame(this.game.getGameFieldString(), file);
                this.game.setIsGameActive(false, false); //TODO move to Game class
            }
        } catch (CustomException e) {
            ErrorMessageHandler.showError(e);
        }
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um ein bestehendes Spiel zu beenden
     */
    public void endGame(){
        this.game.endGame();
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um den Editor Mode einzuschalten wenn vorher an oder auszuschalten
     * wenn vorher aus
     */
    public void toggleEditorMode(){
        this.game.toggleEditorMode();
    }

    /**
     * Methode welche die Breite und Hoehe durch die Nutzereingaben einließt
     */
    public void applyEditorChanges(){
        int height = this.userHeightInput.getValue();
        int width = this.userWidthInput.getValue();
        if (height >= Game.MIN_GAMEFIELD_SIZE_WITHOUT_BORDER && width >= Game.MIN_GAMEFIELD_SIZE_WITHOUT_BORDER &&
                height <= Game.MAX_GAMEFIELD_SIZE_WITHOUT_BORDER && width <= Game.MAX_GAMEFIELD_SIZE_WITHOUT_BORDER) {

            this.game = new Game(this.gui, height, width);
            this.setupGUI(width + 2, height + 2);
        } else {
            ErrorMessageHandler.showError(new CustomException(CustomException.ERROR_INVALID_GAME_SIZE));
        }
    }

    /**
     * Methode welche alle noetigen Grafik Methoden buendelt zum Anzeigen eines Spiels und aller noetigen Elemente
     * @param width die Breite des Spielfelds
     * @param height die Hoehe des Spielfelds
     */
    private void setupGUI(int width, int height){
        this.addSlotsAndListenerEmptyFields(false, 3, 8); //rechte GP
        this.updateGridPaneFormat(height, width);
        this.addSlotsAndListenerEmptyFields(true, width, height); //mittlere GP
        this.gameFieldGridPaneHandleEdges();
        centerPane.applyCss(); //centerPane css setzen bevor die Methode weiterlaeuft
        centerPane.layout(); //centerPane Layout setzen bevor die Methode weiterlaeuft
        this.adjustMiddleGridPaneSize(this.gridPane, this.centerPane.getWidth(), this.centerPane.getHeight());
        this.gridPane.layout(); //centerPane Layout setzen bevor die Methode weiterlaeuft
        this.game.updateTiles();
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um zu pruefen, ob das bestehende Feld im aktuellen Zustand geloest
     * werden kann
     */
    public void checkSolvability(){
        this.game.checkSolvability();
    }

    /**
     * Methode welche aus dem Menue aufgerufen wird um einen Tipp (richtiger Spielstein an der richtigen Stelle)
     * zu legen
     */
    public void layHint(){
        this.game.layHint();
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
     * Methode welche die GridPanes (Spielfeld oder Spielsteinauswahl) mit Hintergrund Slots (StackPane) fuellt
     * und ihnen ueber die dazugehoerigen Methdoen Listener gibt.
     * Die mittlere GridPane (Spielfeld): den mittleren Teil mit addDropListenerMiddleGridPane Listenern und den
     * aeußeren randteil mit den addSlotListenerBorderGridPane Listenern.
     * Die rechte GridPane (Spielsteinauswahl): alles mit den addDropListenerRightGridPane Listenern.
     * @param middleGridPane ob die mittlere GridPane oder die rechte
     * @param xSize die x groeße des Spielfelds
     * @param ySize die y groeße des Spielfelds
     */
    public void addSlotsAndListenerEmptyFields(boolean middleGridPane, int xSize, int ySize){
        for(int y = 0; y < ySize; y++) { //Hoehe durchlaufen
            for (int x = 0; x < xSize; x++) { //Breite durchlaufen
                StackPane slotStackPane =
                        this.gui.getGridPaneCell(x, y, middleGridPane ? this.gridPane : this.rightGridPane);

                if(slotStackPane == null) { //slot existiert noch nicht
                    slotStackPane = new StackPane(); //neuer Slot welcher die Listener bekommt
                    if (middleGridPane) { //mittlere GridPane (Spielfeld)

                        if (!GameField.isFieldEdge(x, y, xSize, ySize)) { //kein Eckstueck
                            slotStackPane.setStyle("-fx-background-color: transparent; -fx-border-color: black;" +
                                    "-fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL + ";");
                        }

                        if (!GameField.isFieldBorder(x, y, xSize, ySize)) { //Feld gehoert zum mittleren Spielfeld
                            this.addDropListenerMiddleGridPane(slotStackPane); //Listener dem Slot anfuegen
                            slotStackPane.setUserData(JavaFXGUI.ID_PIECE); //damit man weiß was der Slot darstellt
                        } else { //Feld gehoert zum Rand
                            this.addSlotListenerBorderGridPane(slotStackPane); //Listener dem Slot anfuegen
                            slotStackPane.setUserData(JavaFXGUI.ID_BORDER);
                        }
                        this.gridPane.add(slotStackPane, x, y); //der mittleren GridPane den Slot hinzuefuegen

                    } else { //rechte GridPane (Spielsteinauswahl)
                        this.addDropListenerRightGridPane(slotStackPane); //Listener dem Slot anfuegen
                        slotStackPane.setUserData(JavaFXGUI.ID_PIECE);
                        this.rightGridPane.add(slotStackPane, x, y); //der rechten GridPane den Slot hinzuefuegen
                    }

                } else { //Slot existiert schon (nur fuer Spielfeld, da nur dieses veraendert werden kann)
                    if (middleGridPane) {

                        if(slotStackPane.getUserData().equals(JavaFXGUI.ID_BORDER) &&
                                !GameField.isFieldBorder(x, y, xSize, ySize)){ //slot existiert schon und ist Rand aber
                            // nun nach den neuen massen (vergroesserung) kein Rand mehr sondern mittleres Spielfeld

                            this.removeListener(slotStackPane); //auch wenn einfach Listener ueberschrieben werden diese
                            // sauber null setzen (deaktivieren und loeschen)
                            this.addDropListenerMiddleGridPane(slotStackPane); //Spielfeld Listener dem Slot anfuegen
                            slotStackPane.setUserData(JavaFXGUI.ID_PIECE);

                        } else if(slotStackPane.getUserData().equals(JavaFXGUI.ID_PIECE) &&
                                GameField.isFieldBorder(x, y, xSize, ySize)){ //slot exisitiert schon und ist Spielfeld
                            // aber nun nach den neuen massen (verkleinerung) kein Spielfeld mehr sondern Rand

                            this.removeListener(slotStackPane); //auch wenn einfach Listener ueberschrieben werden diese
                            // sauber null setzen (deaktivieren und loeschen)
                            this.addSlotListenerBorderGridPane(slotStackPane); //Listener dem Slot anfuegen
                            slotStackPane.setUserData(JavaFXGUI.ID_BORDER);

                        }
                    }

                }
            }
        }
    }

    /**
     * Methode welche alle in diesem Programm genutzen Listener einer Node auf null setzt also zuruecksetzt
     * @param node das Objekt von welchem die Listener zurueckgesetzt werden
     */
    private void removeListener(Node node){
        node.setOnDragOver(null);
        node.setOnDragDropped(null);
        node.setOnDragEntered(null);
        node.setOnDragExited(null);
        node.setOnMouseClicked(null);
    }

    /**
     * Methode welche die Listener fuer die Rand Slots (StackPane) fuer den Farbwechsel setzt
     * @param inputStackPane der Slot welcher die Listener bekommen soll
     */
    private void addSlotListenerBorderGridPane(StackPane inputStackPane){
        inputStackPane.setOnMouseClicked(event -> {
            if(this.game.isEditorMode()) {
                this.game.toggleBorderColor(GridPane.getColumnIndex(inputStackPane),
                        GridPane.getRowIndex(inputStackPane));
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
                    // und somit frei fuer neues ist

                    //Drop wird nur akzeptiert wenn es sich um ein Spielstein handelt im nicht Editormode oder ein Loch
                    // oder Randstein im EditorMode
                    if((!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)) || //Spielstein
                            (game.isEditorMode() && (dragboard.getString().startsWith(JavaFXGUI.ID_HOLE) || //Lochstein
                                    // und EditorMode
                                    dragboard.getString().startsWith(JavaFXGUI.ID_BORDER)))){ //Randstein und EditorMode

                        //Hintergrund faerben und Rand setzen
                        inputStackPane.setStyle("-fx-background-color: pink; " +
                                "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL + ";");
                    }
                }
            }
        });

        //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber den Slot war und wieder weggezogen wird
        inputStackPane.setOnDragExited(event -> {
            //Hintergrund Farbe entfernen und Rand setzen
            inputStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL +
                    ";");
        });

        //wenn ein Objekt vom Typ StackPane (Loch oder Spielstein) ueber diesen Slot gezogen wird
        inputStackPane.setOnDragOver(event -> {
            Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
            if (event.getGestureSource() != inputStackPane && dragboard.hasString()) { //nicht Bewegung im selben Feld
                if(inputStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also leer ist und keine Kinder hat

                    //Drop wird nur akzeptiert wenn es sich um ein Spielstein handelt im nicht Editormode oder ein Loch
                    // oder Randstein im EditorMode
                    if((!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)) || //Spielstein
                            (game.isEditorMode() && (dragboard.getString().startsWith(JavaFXGUI.ID_HOLE) || //Lochstein
                                    // und EditorMode
                                    dragboard.getString().startsWith(JavaFXGUI.ID_BORDER)))){ //Randstein und EditorMode
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

                Object droppedObject = event.getGestureSource(); //das gedroppte Objekt (Loch als StackPane oder
                // Spielstein als ImageView)
                Node droppedObjectNode = (Node) droppedObject; //das gedroppte Objekt als Node
                StackPane droppedObjectParent = (StackPane) droppedObjectNode.getParent(); //die StackPane (Slot) auf
                // welcher das gedroppte Objekt liegt
                GridPane droppedObjectSourceGridPane = (GridPane) droppedObjectParent.getParent(); //die
                // GridPane aus welcher das Objekt kommt
                GridPane droppedObjectTargetGridPane = (GridPane) inputStackPane.getParent(); //die GridPane in welche
                // das gedroppte Element soll

                //woher das gedroppte Objekt kommt (Spielfeld oder Spielsteinauswahl)
                //droppedObjectParent, da das droppedObjet auf diesem liegt und
                // getColumnIndex einen child der ersten Ebene braucht
                int sourceX = GridPane.getColumnIndex(droppedObjectParent);
                int sourceY = GridPane.getRowIndex(droppedObjectParent);
                //wohin das Objekt soll (x und y)
                int targetX = GridPane.getColumnIndex(inputStackPane);
                int targetY = GridPane.getRowIndex(inputStackPane);

                if(droppedObjectSourceGridPane == this.gridPane){ //Objekt kommt vom Spielfeld
                    if(droppedObjectTargetGridPane == this.gridPane){ //Objekt soll in das Spielfeld
                        //Methode der Game Klasse fuehrt Zug aus und aktualisiert falls noetig Grafik GUI
                        if(this.game.moveTileFromGamefieldToGameField(sourceX, sourceY, targetX, targetY)){
                            event.setDropCompleted(true);
                        }
                    }
                } else { //Objekt kommt aus der rechten Spielstein Auswahl
                    if(droppedObjectTargetGridPane == this.gridPane){ //Objekt soll in das Spielfeld
                        //Methode der Game Klasse fuehrt Zug aus und aktualisiert falls noetig GUI
                        if(this.game.moveTileFromNotLaidTilesToGameField(
                                targetX, targetY, this.gui.getTileIndex(droppedObjectNode))){
                            event.setDropCompleted(true);
                        }
                    }
                }
                event.setDropCompleted(false);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }

    /**
     * Methode welche einem Slot (StackPane) einen Listener gibt
     * Nur fuer die rechte GridPane
     * @param inputStackPane der Slot welcher die Listener bekommen soll
     */
    private void addDropListenerRightGridPane(StackPane inputStackPane){
        // rechte GridPane braucht bezueglich ihrer Platzhalter eine groeße, da sonst sollte eine
        // ganze reihe frei sein, diese sich in ihrer Hoehe und oder Breite zusammenzieht
        inputStackPane.setPrefSize(JavaFXGUI.NOT_LAID_TILE_SIZE, JavaFXGUI.NOT_LAID_TILE_SIZE);
        inputStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL + ";");

        //wenn ein Objekt vom Typ StackPane Spielstein ueber den Slot gezogen wird
        inputStackPane.setOnDragEntered(event -> {
            Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
            if (event.getGestureSource() != inputStackPane && dragboard.hasString()) {
                if(inputStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also keine Kinder hat

                    //Drop wird nur akzeptiert wenn es sich um ein Bild handelt im nicht EditorMode
                    if(!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)){
                        //hintergrund faerben und Rand setzen
                        inputStackPane.setStyle("-fx-background-color: pink; " +
                                "-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL + ";");
                    }
                }
            }
        });

        //wenn ein Objekt vom Typ StackPane (Spielstein) ueber dem Slot war und wieder weggezogen wird
        inputStackPane.setOnDragExited(event -> {
            //Hintergrund entfernen und Rand setzen
            inputStackPane.setStyle("-fx-border-color: black; -fx-border-width: " + JavaFXGUI.BORDER_SIZE_GRAPHICAL +
                    ";");
        });

        //wenn ein Objekt vom Typ StackPane (Spielstein) ueber diesen Slot gezogen wird
        inputStackPane.setOnDragOver(event -> {
            Dragboard dragboard = event.getDragboard(); //der Inhalt der verschoben wird
            if (event.getGestureSource() != inputStackPane && dragboard.hasString()) {
                if(inputStackPane.getChildren().isEmpty()) { //wenn das Feld NNNN ist also keine Kinder hat

                    //Drag wird nur akzeptiert wenn es sich um ein Bild handelt im nicht EditorMode
                    if (!game.isEditorMode() && dragboard.getString().startsWith(JavaFXGUI.ID_PIECE)){
                        event.acceptTransferModes(TransferMode.MOVE); //Bild bewegung registrieren
                    }
                }
            }
            event.consume();
        });

        //wenn ein Objekt vom Typ StackPane (Spielstein) auf den Slot gedropped wird
        inputStackPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {

                Object droppedObject = event.getGestureSource(); //das gedroppte Objekt (Spielstein als ImageView)
                ImageView droppedImageView = (ImageView) droppedObject; //das gedroppte Objekt als Node
                StackPane droppedImageViewParent = (StackPane) droppedImageView.getParent(); //die StackPane auf
                // welcher das gedroppte Objekt liegt
                GridPane droppedImageViewSourceGridPane = (GridPane) droppedImageViewParent.getParent(); //die
                // GridPane aus welcher das Objekt kommt (Spielfeld oder Spielsteinauswahl)
                GridPane droppedImageViewTargetGridPane = (GridPane) inputStackPane.getParent(); //woher das Objekt
                // kommt (Spielfeld GridPane oder Spielsteinauswahl GridPane)

                // getColumnIndex einen child der ersten Ebene braucht
                int sourceX = GridPane.getColumnIndex(droppedImageViewParent);
                int sourceY = GridPane.getRowIndex(droppedImageViewParent);

                if(droppedImageViewSourceGridPane == this.gridPane){ //Objekt kommt vom Spielfeld
                    if(droppedImageViewTargetGridPane == this.rightGridPane) { //Objekt soll zurueck in die Auswahl
                        //Methode der Game Klasse fuehrt Zug aus und aktualisiert falls noetig GUI
                        if(this.game.moveTileFromGamefieldToNotLaidTileSelection(sourceX, sourceY))
                            event.setDropCompleted(true);
                    }
                }
                event.setDropCompleted(false);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
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
                StackPane slotStackPane = this.gui.getGridPaneCell(x, y, this.gridPane);
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
    private void adjustMiddleGridPaneSize(GridPane gridPane, double width, double height) {
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
     * Methode welche alle Bilder am Anfang des Spiels laedt ohne diese anzuzeigen
     * Die Bilder werden in dieser Klasse in einem Eindimensionalem Array
     * in der Reihenfolge des TileNames Enums gespeichert
     */
    private ImageView[] loadImages(){
        ImageView[] imageViews = new ImageView[Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY];
        // NNNN und HHHH nicht geladen werden da sie kein Bild haben
        String imagePath;
        for(int i = 0; i < imageViews.length; i++){ //-2 weil HHHH und NNNN nicht als Bild vorhanden sind
            imagePath = "/tiles/" + TileNames.values()[i] + ".png"; //der relative Pfad zu dem Bild
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))); //laedt das Bild
            ImageView currIndexImage = new ImageView(image); //ImageView da es Attribute wie z.B. Groeße speichert

            final int imageIndex = i;

            //Listener (Drag und Rotation) hinzufuegen
            //Drag des Images
            currIndexImage.setOnDragDetected(event -> {
                if (!this.game.isEditorMode()) { //kein EditorMode
                    Dragboard db = currIndexImage.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    String contentPayload = JavaFXGUI.ID_PIECE + imageIndex;
                    content.putString(contentPayload);
                    db.setContent(content);
                }
                event.consume();
            });

            //Rotation des Images wenn Rechtsklick
            currIndexImage.setOnMouseClicked(event -> {
                if(!this.game.isEditorMode()) {
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        game.rotateGameTile(imageIndex); //Rotation
                    }
                }
            });

            imageViews[i] = currIndexImage;
        }
        return(imageViews);
    }

    /**
     * Methode welche alle benoetigten Loecher Objekte in Form einer gefaerbten StackPane initialisiert und sie dem
     * holeStackPanes Array hinzufuegt
     */
    public StackPane[] loadHolesStackPanes(){
        //Anzahl der benoetigten Loecher da fuer jede Zelle die es im Spielfeld mehr gibt als Bilder ein Loch sein muss
        // -2 da Rand nicht beachtet
        StackPane[] holeStackPanes = new StackPane[JavaFXGUI.MAX_HOLES_AMOUNT];
        for(int i = 0; i < holeStackPanes.length; i++){ //soviele Loecher wie noetig
            StackPane holeStackPane = new StackPane();
            holeStackPane.setStyle("-fx-background-color: gray;");

            final int imageIndex = i;

            //Drag Listener hinzufuegen
            holeStackPane.setOnDragDetected(event -> {
                if (this.game.isEditorMode()) { //EditorMode
                    Dragboard db = holeStackPane.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    String contentPayload = JavaFXGUI.ID_HOLE + imageIndex;
                    content.putString(contentPayload);
                    db.setContent(content);
                }
                event.consume();
            });

            holeStackPanes[i] = holeStackPane; //diese Loecher dem Array der benoetigten Loecher hinuzfuegen
        }
        return(holeStackPanes);
    }
}