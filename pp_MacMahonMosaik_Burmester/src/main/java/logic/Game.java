package logic;

import gui.ErrorMessageHandler;

/**
 * Klasse welche das Spiel koodiniert
 * Diese Klasse ist die Schnittstelle zwischen GUI und Logik seitens des UserInterfaceController und der JavaFXGUI
 * Der UserInterfaceController speichert immer die aktuelle Instanz der Game Klasse und die JavaFXGUI nutzt
 * die Game Klasse nur zum setzen der Listener aber speichert nicht wie der UserInterfaceController die Instanz
 *
 * @author Anton Burmester
 */
public class Game {
    private final GUIConnector gui; //Nutzlast des GUIConnector mithilfe welches die Game Klasse mit der GUI (JavaFXGUI)
    // kommuniziert
    private final GameField gameField; //Nutzlast der Spielfeld Instanz
    private final Tiles tiles; //Nutzlast der Spielstein Instanz
    private boolean editorMode; //Nutzlast ob der EditorMode aktiv ist

    //Logic Konstanten
    public static int TILE_AMOUNT_NO_HOLE_NO_EMPTY = 24; //wieviele Spielsteine es gibt ohne Loch und nichts gelegt
    public static int TILE_AMOUNT_COMPLETE = 26; //wieviele Spielsteine es gibt mit Loch und nichts gelegt

    public static final int MIN_GAMEFIELD_SIZE_WITH_BORDER = 4;
    public static final int MAX_GAMEFIELD_SIZE_WITH_BORDER = 8;

    public static final int MIN_GAMEFIELD_SIZE_WITHOUT_BORDER = 2;
    public static final int MAX_GAMEFIELD_SIZE_WITHOUT_BORDER = 6;



    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt
     * (nur fuer Editor genutzt)
     * @param gui die GUI Instanz
     * @param heigth die Hoehe des Spielfelds (Breite inklusive Rand)
     * @param width die Breite des Spielfelds (Breite inklusive Rand)
     */
    public Game(GUIConnector gui, int heigth, int width){
        this.gui = gui;
        this.editorMode = true;
        this.gameField = new GameField(heigth, width, true);
        this.tiles = new Tiles();
    }

    /**
     * Konstruktor welcher das Standard Spiel erstellt nutzt hierfür den Konstruktor welcher ein Spiel auf Grundlage
     * eines StringArrays erstellt
     * (Defaultspiel)
     */
    public Game(GUIConnector gui){
        this(gui, new String[][] {{"NNNN", "NNGN", "NNGN", "NNNN"},
                                  {"NGNN", "NNNN", "NNNN", "NNNG"},
                                  {"NRNN", "NNNN", "NNNN", "NNNR"},
                                  {"NNNN", "YNNN", "YNNN", "NNNN"}});
    }

    /**
     * Konstruktor welcher ein Spiel auf Grundlage eines StringArrays erstellt
     * (zum laden eines bestehenden Spielfelds)
     * @param gui die GUI Instanz
     * @param inputGameField das uebergebene Spielfeld als String
     */
    public Game(GUIConnector gui, String[][] inputGameField){
        this.gui = gui;

        this.tiles = new Tiles();
        this.gameField = new GameField(inputGameField, this.tiles);
    }

    /**
     * Methode welche ein Spiel neustartet
     */
    public void restartGame(){
        if(!this.editorMode) {
            this.removeGameFieldTiles();
            this.updateTiles();
            this.setIsGameActive(true, true);
        } else {
            ErrorMessageHandler.showError(new CustomException(CustomException.ERROR_EDITOR_MODE_ON));
        }
    }

    /**
     * Methode welche das Spiel beendet
     */
    public void endGame(){
        this.setIsGameActive(false, false);
    }

    /**
     * Methode welche aus dem Menue durch die UserInterfaceController Klasse aufgerufen wird um zu pruefen, ob das
     * bestehende Feld im aktuellen Zustand geloest werden kann.
     * nutzt dafür die isGameFieldSolvable Methode der Game Klasse
     * TODO der kommentar ueberpruefen
     */
    public void checkSolvability(){
        boolean isGameFieldSolvable = this.isGameFieldSolvable();
        if(isGameFieldSolvable){
            ErrorMessageHandler.showError(new CustomException(CustomException.MESSAGE_GAMEFIELD_SOLVABLE));
        } else {
            ErrorMessageHandler.showError(new CustomException(CustomException.MESSAGE_GAMEFIELD_NOT_SOLVABLE));
        }
    }

    /**
     * Methode welche einen Hinweis legt
     */
    public void layHint(){
        boolean laidHint = this.layHintTile();
        if(!laidHint) ErrorMessageHandler.showError(new CustomException(CustomException.
                MESSAGE_NO_HINT_GAMEFIELD_NOT_SOLVABLE));
    }

    /**
     * Methode welche den EditorMode aktiviert wenn deaktiviert und deaktiviert wenn aktiviert.
     * Zeigt dies auch visuell an
     */
    public void toggleEditorMode(){
        this.editorMode = !this.editorMode; //Editor Mode umschalten
        if(this.editorMode){ //wenn nun aktiviert
            this.removeGameFieldTiles();
            this.updateTiles();
            this.gui.setDisableTileSelection(true);
        }
        this.gui.displayEditorControls(this.editorMode); //Editor Elemente anzeigen oder nicht
    }

    /**
     * Methode welche den Wahrheitswert ob ein Spiel aktiv ist setzt
     * @param isActive ob das Spiel aktiv ist
     */
    public void setIsGameActive(boolean isActive, boolean setUpGame){
        if(isActive && this.gameField.isGameFieldBorderSetted()) { //Spiel soll aktiv werden und Rand ist gesetzt
            this.gui.setDisableTileSelection(false);
            this.gui.setDisableGameField(false);
        } else if (isActive){ //Spiel soll aktiv werden  aber Rand ist nicht komplett gesetzt
            this.gui.setDisableTileSelection(true);
            this.gui.setDisableGameField(false);
            ErrorMessageHandler.showError(new CustomException(CustomException.ERROR_BORDER_NOT_SETTED));
        } else { //Spiel soll nicht aktiv werden
            this.gui.setDisableTileSelection(true);
            //this.gui.setDisableMiddleGridPane(true);
        }
    }

    /**
     * Methode welche die Spielstein Instanz zurueckgibt
     * @return die Instanz der Klasse aller Spielsteine
     */
    private Tiles getTilesCopy(){
        return(this.tiles.cloneGameTiles());
    }

    /**
     * Methdode welche die Spielfeld Instanz zurueckgibt
     * @return die Instanz der Klasse des Spielfelds
     * //TODO REMOVE
     */
    public GameField getGameFieldCopy(){
        Tiles clonedtiles = this.tiles.cloneGameTiles();
        return(this.gameField.cloneGameField(clonedtiles));
    }

    /**
     * Methode welche einen String vom Spielfeld in der Aufgabenstellungsform zurueckgibt
     * @return das Spielfeld als String in Aufgabenstellungsform
     */
    public String[][] getGameFieldString(){
        return(this.gameField.translateToSpielstandsdatei());
    }

    /**
     * Methode welche die Spielsteine sichtbar macht und positioniert im Feld und der Auswahl.
     * positioniert die Spielsteine zwischen Spielfeld und Auswahl, setzt die Loecher im Spielfeld und faerbt den Rand
     */
    public void updateTiles(){
        //Bilder, Loecher und Faerbungen anzeigen
        this.gui.displayGameFieldTiles(this.getGameFieldCopy());
        this.gui.displaySelectionTiles(this.getTilesCopy());
        this.highlightTileIfWrongPlaced();
    }

    /**
     * Methode welche prueft ob das Spielfeld spielbar ist also ob der Rand voll mit Randstuecken gefuellt ist.
     * @return ob das Spielfeld spielbar ist
     */
    public boolean isGameFieldPlayable(){
        return(this.gameField.isGameFieldBorderSetted());
    }

    /**
     * Methode welche zurueckgibt ob das Feld der angegebenen Koordinaten zum mittleren Spielfeld gehoert
     * @param x Breitenkoordinate
     * @param y Hoehenkoordinate
     * @return ob das Feld zum mittleren Spielfeld gehoert (sonst Rand)
     */
    public boolean isFieldMiddleField(int x, int y){
        return(this.gameField.isFieldMiddleGamefield(x, y));
    }

    /**
     * Methode welche einen Spielstein von der Auswahl der Spielsteine auf das Spielfeld legt
     * der gelegte Spielstein wird dann aus der Auswahl geloescht
     * @param tileIndex Index des zu legenden Spielsteins (orientiert sich an der TileNames Reihenfolge)
     * @param x in welcher Spalte des Spielfeldes der Spielstein gelegt werden soll
     * @param y in welcher Reihe des Spielfeldes der Spielstein gelegt werden soll
     * @return ob der Spielstein von der Auswahl auf das Spielfeld gelegt werden konnte
     */
    public boolean moveTileFromNotLaidTilesToGameField(int x, int y, int tileIndex){
        //wenn isGameTile dann wird der in den Spielsteinen gesucht und wenn nicht dann in den Lochsteinen
        Tile tile = this.tiles.getTile(tileIndex);
        boolean status = true;
        //Feld ist frei und es handelt sich um das mittlere Spielfeld
        if (this.gameField.isFieldFieldFree(x, y) && this.gameField.isFieldMiddleGamefield(x, y)) {
            this.gameField.layTile(x, y, tile); //Spielstein auf das Spielfeld legen
            this.gui.moveTileSelectionToGameField(x, y, tileIndex); //Zug visuell anzeigen
            this.highlightTileIfWrongPlaced();
            this.checkAndHandleWin(); //pruefen und handhaben ob das Spiel durch einen Sieg beendet wurde
        } else {
            status = false;
        }
        System.out.println(this.gameField);
        return(status);
    }

    /**
     * Methode welche einen Spielfeld innerhalb des Spielfelds bewegt
     * @param xStart Spalte des umzulegenden Spielsteins
     * @param yStart Reihe des umzulegenden Spielsteins
     * @param xTarget Spalte des Ziels des umzulegenden Spielsteins
     * @param yTarget Reihe des Ziels des umzulegenden Spielsteins
     * @return ob der Spielstein erfolgreich umgelegt werden konnte
     */
    public boolean moveTileFromGamefieldToGameField(int xStart, int yStart, int xTarget, int yTarget){
        Tile tile = this.gameField.getTile(xStart, yStart);
        boolean status = true;
        //ob der Spielstein gefunden wurde und entweder ein normaler Stein ist oder der EditorMode aktiv und Loch Stein
        if(tile != null && (tile.isNormalGameTile() || (this.editorMode && tile.isHoleTile()))){
            if(this.gameField.isFieldMiddleGamefield(xTarget, yTarget) &&
                    this.gameField.isFieldFieldFree(xTarget, yTarget)){
                this.gameField.layTile(xTarget, yTarget, tile); //Spielstein auf die neue Position des Spielfelds legen
                this.gameField.resetTile(xStart, yStart); //Spielstein von der alten Position
                // des Spielfelds loeschen
                this.gui.moveTileGameFieldToGameField(xStart, yStart, xTarget, yTarget); //den Spielstein oder Lochstein
                // Graphisch verschieben
                this.highlightTileIfWrongPlaced();
            } else {
                status = false;
            }
        } else {
            status = false;
        }
        System.out.println(this.gameField);
        return(status);
    }

    /**
     * Methode welche  einen Spielstein vom Spielfeld wieder in die Auswahl hinzufuegt
     * @param x die Spalte des Spielsteins
     * @param y die Reihe des Spielsteins
     * @return ob der Spielstein erfolgreich zurueckgelegt werden konnte
     */
    public boolean moveTileFromGamefieldToNotLaidTileSelection(int x, int y){
        Tile tile = this.gameField.getTile(x, y);
        boolean status = true;
        //ob der Spielstein gefunden wurde und entweder ein normaler Stein ist oder der EditorMode aktiv und Loch Stein
        if(tile != null && (tile.isNormalGameTile() || (this.editorMode && tile.isHoleTile()))){
            this.gameField.resetTile(x, y); //Spielstein von der alten Position
            this.gui.moveTileGameFieldToSelection(tile.getTile().ordinal()); //das Graphische Bewegen des Spielsteins
            // in die Spielsteinauswahl
            this.highlightTileIfWrongPlaced();
        } else {
            status = false;
        }
        System.out.println(this.gameField);
        return(status);
    }

    /**
     * Methode welche die Randfarbe aendert
     * schaltet bei jedem Aufruf zur jeweils naechsten Farbe
     * @param x die Spalte des Randes
     * @param y die Reihe des Randes
     * @return ob der gewuenschte Ort richtig gefaerbt werden konnte
     */
    public boolean toggleBorderColor(int x, int y){
        boolean status = false;
        if(this.gameField.isFieldBorder(x, y)){
            Tile currTile = this.gameField.getTile(x, y); //die aktuelle Farbe
            Tile newBorderTile; //die naechste Farbe
            //wenn es sich aktuell um nicht um ein Randstueck handelt (noch nichts gelegt) oder die letzte Randfarbe
            // liegt

            if(!currTile.isTileBorderLayable() || currTile.getTile().equals(TileNames.YYYY)) {
                newBorderTile = new Tile(TileNames.RRRR); //wieder auf Rot schalten (erste Farbe)
            } else { //bei allen anderen Randfarben
                newBorderTile = new Tile(TileNames.values()[currTile.getTile().ordinal() + 1]); //die naechste Randfarbe
            }

            this.gameField.layTile(x, y, newBorderTile);
            this.gui.setBorderColor(x, y, newBorderTile.getTile()); //die Farbaenderung visuell sichtbar machen

            System.out.println(this.gameField);
            status = true;
        }
        return(status);
    }

    /**
     * rotiert einen Spielstein
     * @param tileIndex der Index des zu rotierenden Spielsteins
     */
    public void rotateGameTile(int tileIndex){
        Tile tile = this.tiles.getTile(tileIndex);
        tile.rotateTile();
        this.gui.rotateTile(tileIndex, Rotation.rotationToDegrees(tile.getRotation())); //die Rotation graphisch
        // anzeigen
        this.highlightTileIfWrongPlaced();
        this.checkAndHandleWin(); //pruefen und handhaben ob das Spiel durch einen Sieg beendet wurde
    }

    /**
     * Methode welche prueft ob das Spiel geloest wurde und falls ja das Spiel beendet und dem Spieler die Meldung
     * ausgibt, dass das Spiel erfolgreich geloest wurde
     */
    private void checkAndHandleWin(){
        boolean win = this.gameField.checkIfGameFieldSolved(false);
        if (win) {
            ErrorMessageHandler.showError(new CustomException(CustomException.MESSAGE_WIN));
            this.setIsGameActive(false, false);
        }
    }

    /**
     * Getter welcher zurueckgibt ob der EditorMode aktiv ist
     * @return ob der EditorMode aktiv ist
     */
    public boolean isEditorMode(){
        return(this.editorMode);
    }

    /**
     * Methode welche ein bestehendes Spiel neustartet.
     * (entfernt alle Spielsteine aus dem Spielfeld, laesst aber den Rand und die Lochsteine)
     */
    public void removeGameFieldTiles(){
        this.gameField.getGameField();
        for(int y = 0; y < this.gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < this.gameField.getGameFieldWidth(); x++){
                if(!this.gameField.isFieldBorder(x, y)){ //kein Randstueck
                    Tile currTile = this.gameField.getTile(x,y);
                    if(currTile.isNormalGameTile()){ //kein Loch und nicht leer
                        this.gameField.resetTile(x, y);
                        this.tiles.setTileLaidStatus(currTile, false);
                    }
                }
            }
        }
        this.highlightTileIfWrongPlaced(); //falls es falsche Felder gab die Markierung wieder wegnehmen
    }

    /**
     * Methode welche prueft ob das Spielfeld im aktuellen Zustand loesbar ist
     * Diese Methode greift auf die solveGameFieldAsCopy Methode zurueck
     * @return ob eine passende Kombination gefunden wurde und somit das Spielfeld geloest wurde
     */
    boolean isGameFieldSolvable(){
        GameField solvedGameFieldCopy = this.solveGameFieldAsCopy();
        return(solvedGameFieldCopy != null && solvedGameFieldCopy.checkIfGameFieldSolved(false));
    }

    /**
     * Methode welche einen weiteren Spielstein legt.
     * @return ob das Spielfeld loesbar ist und somit ein naechster Spielstein gelegt werden konnte
     */
    private boolean layHintTile(){
        GameField solvedGameFieldCopy = this.solveGameFieldAsCopy(); //das geloeste Spielfeld oder null falls nicht
        if(solvedGameFieldCopy != null) { //Spielfeld wurde geloest
            Position pos = new Position(0, 0);
            this.goToNextFreeMiddleField(this.gameField, pos); //das naechste freie Feld finden im Original Spielfeld
            Tile hintTileCopy = solvedGameFieldCopy.getTile(pos.getX(), pos.getY()); //den Kopie Spielsteinim geloesten
            // Spielfeld an der Stelle wo im Original Spielfeld das erste Feld noch nicht geloest wurde

            Tile hintTileOriginal = this.tiles.getTileByNameWithRotation(hintTileCopy.getTileNameWithRotation()); //der
            // Kopie Spielstein als Original Spielstein

            this.moveTileFromNotLaidTilesToGameField(pos.getX(), pos.getY(),
                    hintTileOriginal.getTile().ordinal()); //Spielstein legen

            //Graphisch den gefundenen Spielstein anzeigen
            this.gui.moveTileSelectionToGameField(pos.getX(), pos.getY(), hintTileOriginal.getTile().ordinal());
            this.gui.rotateTile(hintTileOriginal.getTile().ordinal(),
                    Rotation.rotationToDegrees(hintTileOriginal.getRotation()));
            return(true);
        } else { //Spielfeld wurde nicht geloest
            //Fehler, das das Spielfeld nicht loesbar ist
            return(false);
        }
    }

    /**
     * Methode welche versucht das Spielfeld zu loesen.
     * Hierfuer wird gesucht bis ein neuer Stein gefunden wurde, der auf ein leeres Feld passt.
     * Wenn an einen Punkt gekommen wird wo es keine weiteren Steine mehr gibt wird durch Backtracking zurueckgegangen
     * und eine andere Kombination versucht
     * Das geht solange bis das Spielfeld loesbar ist oder jede Kombination versucht wurde
     * @return null falls das Spielfeld nicht geloest wurde, sonst die Kopie des Spielfelds welche geloest ist
     */
    private GameField solveGameFieldAsCopy(){
        GameField gameField = this.gameField;

        Tiles clonedTiles = this.tiles.cloneGameTiles();
        GameField clonedGameField = gameField.cloneGameField(clonedTiles);

        if(gameField.checkIfGameFieldSolved(false)) return(clonedGameField); //Spielfeld schon geloest

        if(!gameField.checkIfGameFieldSolved(true)) return(null); //nicht loesbar bezugelich der schon liegenden
        // Spielsteine bei Aufruf dieser Methode

        Position pos = new Position(0, 0); //die aktuelle Position in einer Klasse, damit diese als Refferenz
        // uebergeben werden kann

        clonedTiles.resetAllNotLaidTileRotation(); //initial die Rotation aller nicht gelegten Spielsteine zuruecksetzen
        // damit jede Rotation versucht wird und keine uebersprungen wird,
        // da findNextMatchingTile bei der aktuellen Rotation des Spielsteins beginnt

        Tile lastTileBeforeGoingBack = null; //speichert immer wenn einen Schritt zurueckgegangen wurde den Spielstein
        // damit beim naechsten Durchlauf dieser nicht nochmal versucht wird (Backtracking)

        boolean searchActive = true;
        while(searchActive){ //solange die Suche noch anläuft und kein Spielstein gefunden wurde
            boolean nextFieldThere;
            if(lastTileBeforeGoingBack == null) { //es gab keine Schritt zurueck (Backtracking)
                nextFieldThere = this.goToNextFreeMiddleField(gameField, pos); //das naechste freie Feld suchen
            } else { //es gab einen Schritt zurueck Backtracking
                nextFieldThere = false;
            }

            if(nextFieldThere || lastTileBeforeGoingBack != null){ //es gibt ein naechstes Spielfeld
                Tile nextMatchingTile = this.findNextMatchingTile(clonedGameField, clonedTiles, pos,
                        lastTileBeforeGoingBack);
                if(nextMatchingTile != null){ //es gibt einen passenden Spielstein
                    clonedGameField.resetTile(pos.getX(), pos.getY());
                    clonedGameField.layTile(pos.getX(), pos.getY(), nextMatchingTile); //Spielstein legen
                    lastTileBeforeGoingBack = null; //da naechster Spielstein gefunden wurde
                } else { //es gibt keinen passenden Spielstein
                    boolean previousFieldThere = this.goToPreviousFreeMiddleField(gameField, pos); //das vorherige
                    // freie Feld suchen
                    if(previousFieldThere) { //es gibt ein vorheriges freies Feld, deshalb dieses zurueckgehen
                        lastTileBeforeGoingBack = clonedGameField.getTile(pos.getX(), pos.getY()); //fuer Backtracking
                        clonedGameField.resetTile(pos.getX(), pos.getY()); //Spielfeld zuruecksetzen
                    } else { //es gibt kein weiteres vorheriges Spielfeld
                        searchActive = false; //Spielfeld nicht loesbar
                    }
                }
            } else { //kein weiteres Feld gefunden
                searchActive = false; //Spielfeld sollte geloest sein
            }
        }

        System.out.println("Solved as far GameField: \n" + clonedGameField);

        return(clonedGameField.checkIfGameFieldSolved(false) ? clonedGameField : null); //wenn das Spielfeld
        // geloest wurde das geloeste Spielfeld, sonst null
    }


    /**
     * Methode welche das naechste freie mittlere Spielfeld sucht
     * @param inputGameField das Spielfeld
     * @param pos die aktuelle Position als Refferenz, damit die x und y Werte auch wieder aus dieser Methode rauskommen
     * @return ob es ein naechstes Feld gibt
     */
    private boolean goToNextFreeMiddleField(GameField inputGameField, Position pos){
        int xStart; //initial x auf den Wert der aktuellen Position setzen
        int yStart;
        if(pos.getX() < inputGameField.getGameFieldWidth() - 2){ //- 2 damit man 1. Spalte vorgehen kann
            // (letzte Spalte Spielfeld, Rand)
            xStart = pos.getX() + 1; //1. Spalte vor damit nicht dasselbe Feld zurueckgegeben wird
            yStart = pos.getY() == 0 ? 1 : pos.getY();
        } else if(pos.getY() < inputGameField.getGameFieldHeight() - 2){ //- 2 damit man 1. Zeile vorgehen kann
            // (letztes Spielfeld, darunter Rand)
            xStart = 1;
            yStart = pos.getY() + 1; //1. Zeile zurueck damit nicht dasselbe Feld zurueckgegeben wird
        } else { //man kann kein Feld mehr vorgehen
            return(false);
        }
        for(int y = yStart; y < inputGameField.getGameFieldHeight() - 1; y++){
            for(int x = xStart; x < inputGameField.getGameFieldWidth() - 1; x++){
                if((inputGameField.isFieldFieldFree(x, y) || inputGameField.getTile(x, y).isPlaceHolderTile())
                        && inputGameField.isFieldMiddleGamefield(x, y)){
                    pos.setX(x);
                    pos.setY(y);
                    return(true);
                }
            }
            xStart = 1; //1 da 0 der linke Rand ist
        }
        return(false); //kein Feld gefunden
    }

    /**
     * Methode welche das naechste vorherige freie mittlere Spielfeld sucht
     * @param inputGameField das Spielfeld
     * @param pos die aktuelle Position als Refferenz, damit die x und y Werte auch wieder aus dieser Methode rauskommen
     * @return ob es ein naechstes Feld gibt
     */
    private boolean goToPreviousFreeMiddleField(GameField inputGameField, Position pos){
        int xStart;
        int yStart;
        if(pos.getX() >= 2){ //>= 2 damit man 1. Spalte zurueckgehen kann (Rand, erstes Spielfeld)
            xStart = pos.getX() - 1; //1. Spalte zurueck damit nicht dasselbe Feld zurueckgegeben wird
            //damit nicht die unterste Randspalte
            yStart = pos.getY() == inputGameField.getGameFieldHeight() - 1 ? pos.getY() - 1: pos.getY();
        } else if(pos.getY() >= 2){ //>= 2 damit man 1. Zeile zurueckgehen kann (Rand, darunter erstes Spielfeld)
            xStart = inputGameField.getGameFieldWidth() - 2; //die auesserte Spalte von inneren Spielfeld
            yStart = pos.getY() - 1; //1. Zeile zurueck damit nicht dasselbe Feld zurueckgegeben wird
        } else { //man kann kein Feld mehr zurueckgehen
            return(false);
        }

        for(int y = yStart; y >= 1; y--){
            for(int x = xStart; x >= 1; x--){
                if((inputGameField.isFieldFieldFree(x, y) || inputGameField.getTile(x, y).isPlaceHolderTile()) &&
                        inputGameField.isFieldMiddleGamefield(x, y)){
                    pos.setX(x);
                    pos.setY(y);
                    return(true);
                }
            }
            xStart = inputGameField.getGameFieldWidth() - 2; //wieder ganze Zeile, -2 da sonst Rand und 0 Indexiert
        }
        return(false); //es wurde kein freies Feld gefunden
    }

    /**
     * Methode welche fuer ein Spielfeld an einer bestimmten Position das passende Spielteil sucht.
     * @param inputGameField das Spielfeld
     * @param inputTiles die Spielstein Klasse
     * @param pos die aktuelle Position als Refferenz
     * @param lastTileBeforeGoingBack der Spielstein des Felds von dem es nicht weiter geht und deshalb Backtracking
     *  genutzt wird, der Stein wird uebergeben, damit diese Methode weiß von wo sie nach neuen Spielsteinen suchen
     *  muss; null falls es kein Aufruf nach Backtracking war sondern ein ganz normaler
     * @return der gefundene Spielstein oder null falls keiner gefunden wurde
     */
    private Tile findNextMatchingTile(GameField inputGameField, Tiles inputTiles, Position pos,
                                      Tile lastTileBeforeGoingBack){
        Tile tile = lastTileBeforeGoingBack;
        int startIndex = tile != null  && !tile.getTile().equals(TileNames.NNNN) ?
                inputTiles.getTileIndex(tile) : 0; //der Index zum Start des aktuell dort liegenden Spielsteins
        // oder 0 falls es ein Aufruf fuer ein leeres Feld ist

        Rotation startRotation = tile != null && !tile.getTile().equals(TileNames.NNNN) ?
                tile.getRotation() : Rotation.R0; //die Rotation des übergebenen Spielsteins zum Start oder R0
        // falls es ein Aufruf fuer ein leeres Feld ist

        boolean firstIteration = true;

        for(int tileIndex = startIndex; tileIndex < inputTiles.getTiles().length; tileIndex++){ //jeden moeglichen
            // Spielstein vom startIndex bis zum ende
            tile = inputTiles.getTile(tileIndex);
            if(!tile.getIsLaid()) { //nur nicht gelegte Spielsteine nutzen‚

                if(startRotation != Rotation.R3) { //nur Rotationen durchlaufen sofern der Spielstein nicht schon die
                    // letze Rotation aufweist bei Aufruf dieser Methode
                    for (Rotation currRotation : Rotation.values()) {

                        //nur Rotation versuchen, wenn es sich um eine neue noch nicht probierte handelt; nur relevant
                        // in der ersten Iteration, da diese bereits mit einem Index ungleich 0 starten koennte
                        if (startRotation.ordinal() < currRotation.ordinal() //erster Durchlauf muss neue Rotation sein
                                || tile.getTile().equals(TileNames.NNNN) //oder egal falls mit NNNN gestartet wird, da
                                // hier garantiert eine neue Kombination versucht wird
                                || !firstIteration //oder nicht erster durchlauf wodurch es auch garantiert eine neue
                            // Kombination ist
                                ) {

                            tile.rotateTile(); //den Spielstein rotieren
                            inputGameField.layTile(pos.getX(), pos.getY(), tile);

                            if (inputGameField.isGameFieldTileMatching(pos.getX(), pos.getY(), true)) { //
                                // pruefen ob der neue Spielstein passt
                                inputGameField.resetTile(pos.getX(), pos.getY());
                                return (tile);
                            }
                            inputGameField.resetTile(pos.getX(), pos.getY());
                        }
                    }
                }
                startRotation = Rotation.R0; //StartRotation wieder auf 0 Anfang setzen,
                // da jetzt nicht mehr der uebergebene Spielstein getestet wird
                tile.resetTileRotation();
            }
            firstIteration = false;
        }
        return(null);
    }

    /**
     * Methode welche alle Spielfeld Felder durchlaeuft und prueft, ob es welche gibt, die nicht Farblich passen.
     * Alle die nicht farblich passen, werden hervorgehoben
     */
    public void highlightTileIfWrongPlaced(){
        for(int y = 0; y < this.gameField.getGameFieldHeight(); y++){
            for(int x = 0; x < this.gameField.getGameFieldWidth(); x++){
                boolean isTileMatching = this.gameField.isGameFieldTileMatching(x, y, true);
                this.gui.highlightTileNotMatching(x, y, !isTileMatching, true);
            }
        }
    }
}
