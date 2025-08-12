package logic;

import java.io.File;

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

    //Logic Konstanten
    public static int TILE_AMOUNT_NO_HOLE_NO_EMPTY = 24; //wieviele Spielsteine es gibt ohne Loch und nichts gelegt
    public static int TILE_AMOUNT_COMPLETE = 26; //wieviele Spielsteine es gibt mit Loch und nichts gelegt

    public static final int MIN_GAMEFIELD_SIZE_WITH_BORDER = 4;
    public static final int MAX_GAMEFIELD_SIZE_WITH_BORDER = 8;

    public static final int MIN_GAMEFIELD_SIZE_WITHOUT_BORDER = 2;
    public static final int MAX_GAMEFIELD_SIZE_WITHOUT_BORDER = 6;

    public static final int MAX_FREE_TILES_SOLVABLE_CHECK = 18; //wieviele Spielsteine maximal im Feld frei sein dürfen,
    // beim laden eines neuen Spielfelds geprueft wird ob dieses Spielbar ist

    private static final String[][] DEFAULT_GAME =  {{"NNNN", "NNGN", "NNGN", "NNNN"},
                                                     {"NGNN", "NNNN", "NNNN", "NNNG"},
                                                     {"NRNN", "NNNN", "NNNN", "NNNR"},
                                                     {"NNNN", "YNNN", "YNNN", "NNNN"}}; //Das Standard Spielfeld


    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt
     * (nur fuer Editor genutzt)
     * @param gui die GUI Instanz
     * @param heigth die Hoehe des Spielfelds (Breite inklusive Rand)
     * @param width die Breite des Spielfelds (Breite inklusive Rand)
     */
    public Game(GUIConnector gui, int heigth, int width){
        this.gui = gui;
        this.gameField = new GameField(heigth, width, true, true);
    }

    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt mit Rand des uebergebenen Spielfelds
     * (nur fuer Editor genutzt)
     * @param gui die GUI Instanz
     * @param heigth die Hoehe des Spielfelds (Breite inklusive Rand)
     * @param width die Breite des Spielfelds (Breite inklusive Rand)
     * @param oldGameField das alte Spielfeld, da dieses als Refferenz zum Rand einfaerben dient des neuen Spielfelds
     */
    public Game(GUIConnector gui, int heigth, int width, GameField oldGameField){
        this(gui, heigth, width);
        this.gameField.setBorderFromGameField(oldGameField);
    }

    /**
     * Konstruktor welcher das Standard Spiel erstellt nutzt hierfür den Konstruktor welcher ein Spiel auf Grundlage
     * eines StringArrays erstellt
     * (Defaultspiel)
     */
    public Game(GUIConnector gui) {
        this(gui, Game.DEFAULT_GAME);
    }

    /**
     * Konstruktor welcher ein Spiel auf Grundlage eines StringArrays erstellt
     * (zum laden eines bestehenden Spielfelds)
     * @param gui die GUI Instanz mithilfe welcher die Game klasse mit dem graphischen Spielfeld interagiert
     * @param inputGameField das uebergebene Spielfeld als String
     */
    Game(GUIConnector gui, String[][] inputGameField) {
        this.gui = gui;

        GameField gameField;

        try{
            GameField.inputStringGameFieldValid(inputGameField); //ob Spielfeld Input valide ist

            gameField = new GameField(inputGameField);
        } catch (CustomException e){
            this.gui.showCustomException(e);
            gameField = new GameField(Game.DEFAULT_GAME);
        }

        this.gameField = gameField;
    }

    /**
     * Konstruktor welcher auf Grundlage eines Dateipfads das dazugehoerige Spielfeld laedt
     * @param gui die GUI Instanz mithilfe welcher die Game klasse mit dem graphischen Spielfeld interagiert
     * @param fileWithPath der Dateipfad
     */
    public Game(GUIConnector gui, File fileWithPath) {
        this.gui = gui;

        GameField gameField;

        try { //versuchen das Spielfeld aus der Datei zu laden
            String[][] inputStringGameField = GameData.loadGame(fileWithPath);
            gameField = new GameField(inputStringGameField);
        } catch (CustomException e) { //Spielfeld konnte nicht aus der Datei geladen werden
            this.gui.showCustomException(e);
            gameField = new GameField(Game.DEFAULT_GAME);
        }

        this.gameField = gameField;
    }

    /**
     * Methode welche je nachdem ob ein spielfeld loesbar ist oder nicht den Editor Mode aktiviert oder nicht
     * und Spielsteine entfernt oder nicht.
     * Gibt dem Spieler auch Rückmeldung
     * @param initialGameField ob der Aufruf fuer das initiale Spielfeld ist damit keine Ausgabe kommt, da das Spiel
     *                         garantiert gueltig ist
     *
     */
    public void initializeGameState(boolean initialGameField){
        if(this.gameField.isGameFieldBorderSetted()) { //ob Spielfeld von dem Rand her komplett Spielbar ist

            if(this.gameField.getLayableFieldsAmount(false) > Game.MAX_FREE_TILES_SOLVABLE_CHECK){ //mehr
                // freie Felder als Anzahl die maximal ueberprueft werden soll -> nicht loesbarkeit pruefen

                this.setGameFlow(true, true);
                if (isEditorMode()) this.toggleEditorMode(false); //editor Mode deaktivieren, da Spielfeld
                // spielbar ist und er vorher aktiv war

                this.setGameFlow(false, false); //Spielfeld aktiviert, Auswahl aktiviert

                this.gui.showCustomException(new CustomException(
                        CustomException.MESSAGE_MORE_THAN_18_FREE_FIELDS_SOLVABLE_NOT_CHECKED));

            } else { //weniger als die maximale Anzahl welche ueberprueft wird -> loesbarkeit ueberpruefen

                if(this.gameField.checkIfPlainGameFieldSolvable()) { //Spielfeld ist vom Rand loesbar

                    this.setGameFlow(true, true);
                    if (isEditorMode()) this.toggleEditorMode(false); //editor Mode deaktivieren, da Spielfeld
                    // spielbar ist und er vorher aktiv war

                    this.setGameFlow(false, false); //Spielfeld aktiviert,
                    // Auswahl deaktiviert

                    if(!initialGameField) //da initialer Aufruf und Spiel garantiert gueltig dies nicht ausgeben
                        this.gui.showCustomException(new CustomException(CustomException.MESSAGE_GAMEFIELD_SOLVABLE));

                } else { //Spielfeld ist vom Rand nicht loesbar

                    this.setGameFlow(false, true);
                    if (!isEditorMode()) {
                        this.toggleEditorMode(false); //editor Mode aktivieren, da Spielfeld nicht spielbar ist
                    } else { //nochmal entfernen, da Spiel geladen wird und editor schon aktiv ist
                        // (Spielsteine falls da sollen weg)
                        this.removeGameFieldTiles(false);
                    }

                    this.setGameFlow(false, true); //Spielfeld aktiviert,
                    // Auswahl deaktiviert

                    this.gui.showCustomException(new CustomException(CustomException.MESSAGE_GAMEFIELD_NOT_SOLVABLE));

                }
            }
        } else { //nicht spielbar da Rand nicht komplett gesetzt deshalb EditorMode aktivieren

            this.setGameFlow(false, true);
            if(!isEditorMode()) {
                this.toggleEditorMode(false); //editor Mode aktivieren, da Spielfeld nicht spielbar ist
            } else { //nochmal entfernen, da Spiel geladen wird und editor schon aktiv ist
                // (Spielsteine falls da sollen weg)
                this.removeGameFieldTiles(false);
            }

            this.setGameFlow(false, true); //Spielfeld aktiviert, Auswahl deaktiviert

            this.gui.showCustomException(new CustomException(CustomException.ERROR_BORDER_NOT_SETTED));

        }
    }

    /**
     * Methode welche ein Spiel neustartet
     */
    public void restartGame(){
        if(!this.isEditorMode()) {
            this.removeGameFieldTiles(true);
            this.updateTiles();
            this.setGameFlow(false, false); //Spielfeld und Spielsteinauswahl aktivieren
        } else {
            this.gui.showCustomException(new CustomException(CustomException.ERROR_EDITOR_MODE_ON));
        }
    }

    /**
     * Methode welche das Spiel beendet
     */
    public void endGame(){
        this.setGameFlow(false, false);
    }

    /**
     * Methode welche aus dem Menue durch die UserInterfaceController Klasse aufgerufen wird um zu pruefen, ob das
     * bestehende Feld im aktuellen Zustand geloest werden kann.
     * nutzt dafür die isGameFieldSolvable Methode der Game Klasse
     * @param checkPlainField ob das leere Feld also nur der Rand geprueft werden soll oder aber das ganze Feld
     */
    public void checkSolvability(boolean checkPlainField){
        GameField checkedGameField = checkPlainField ? this.gameField.cloneGameField() : this.gameField;

        if(this.gameField.getLayableFieldsAmount(false) <= Game.MAX_FREE_TILES_SOLVABLE_CHECK) {

            boolean isGameFieldSolvable = checkedGameField.isGameFieldSolvable();
            if (isGameFieldSolvable) {
                this.gui.showCustomException(new CustomException(CustomException.MESSAGE_GAMEFIELD_SOLVABLE));
            } else {
                this.gui.showCustomException(new CustomException(CustomException.MESSAGE_GAMEFIELD_NOT_SOLVABLE));
            }

        } else {
            this.gui.showCustomException(new CustomException(
                    CustomException.MESSAGE_MORE_THAN_18_FREE_FIELDS_SOLVABLE_NOT_CHECKED));
        }
    }

    /**
     * Methode welche einen Hinweis legt
     */
    public void layHint(){
        if(this.gameField.getLayableFieldsAmount(false) <= Game.MAX_FREE_TILES_SOLVABLE_CHECK) {
            boolean laidHint = this.layHintTile();
            if (!laidHint) this.gui.showCustomException(new CustomException(CustomException.
                    MESSAGE_NO_HINT_GAMEFIELD_NOT_SOLVABLE));
        } else {
            this.gui.showCustomException(new CustomException(
                    CustomException.MESSAGE_MORE_THAN_18_FREE_FIELDS_SOLVABLE_NOT_CHECKED));
        }
    }

    /**
     * Methode welche den EditorMode aktiviert wenn deaktiviert und deaktiviert wenn aktiviert.
     * Zeigt dies auch visuell an
     * @param initial ob Initial bei einem neuen Spielfeld aufgerufen
     */
    public void toggleEditorMode(boolean initial){
        boolean toggledEditorMode = !this.isEditorMode();
        this.gui.displayEditorControls(toggledEditorMode); //Editor Elemente anzeigen wenn nicht angezeigt und andersrum
        if(toggledEditorMode){ //wenn nun aktiviert
            if(initial)
                this.removeGameFieldTiles(true); //alle Steine die nicht Rand sind vom Spielfeld
            // entfernen
            this.updateTiles();
            this.setGameFlow(false, true); //Spielfeldauswahl deaktivieren
        }
    }

    /**
     * Methode welche den das Spielfeld und die Spielsteinauswahl unabhaengig voneinander aktiviert und deaktiviert
     * @param disableGameField ob das Spielfeld deaktiviert werden soll
     * @param disableSelection ob die Spielsteinauswahl deaktiviert werden soll
     */
    public void setGameFlow(boolean disableGameField, boolean disableSelection){
            this.gui.setDisableTileSelection(disableSelection);
            this.gui.setDisableGameField(disableGameField);
    }

    /**
     * Methode welche die Spielstein Instanz zurueckgibt
     * @return die Instanz der Klasse aller Spielsteine
     */
    private Tiles getTilesCopy(){
        return(this.gameField.getTiles().cloneTiles());
    }

    /**
     * Methdode welche die Spielfeld Instanz zurueckgibt
     * @return die Instanz der Klasse des Spielfelds
     */
    public GameField getGameFieldCopy(){
        return(this.gameField.cloneGameField());
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
     * Methode welche einen Spielstein von der Auswahl der Spielsteine auf das Spielfeld legt
     * der gelegte Spielstein wird dann aus der Auswahl geloescht
     * @param tileIndex Index des zu legenden Spielsteins (orientiert sich an der TileNames Reihenfolge)
     * @param x in welcher Spalte des Spielfeldes der Spielstein gelegt werden soll
     * @param y in welcher Reihe des Spielfeldes der Spielstein gelegt werden soll
     * @return ob der Spielstein von der Auswahl auf das Spielfeld gelegt werden konnte
     */
    public boolean moveTileFromNotLaidTilesToGameField(int x, int y, int tileIndex){
        //wenn isGameTile dann wird der in den Spielsteinen gesucht und wenn nicht dann in den Lochsteinen
        Tile tile = gameField.getTiles().getTileByTileNamesIndex(tileIndex);
        boolean status = true;
        //Feld ist frei und es handelt sich um das mittlere Spielfeld
        if (this.gameField.isFieldFree(x, y) && this.gameField.isFieldMiddleGamefield(x, y)) {
            this.gameField.layTile(x, y, tile); //Spielstein auf das Spielfeld legen

            this.gui.moveTileSelectionToGameField(x, y, tileIndex); //Zug visuell anzeigen

            this.highlightTileIfWrongPlaced();

            this.checkAndHandleWin(); //pruefen und handhaben ob das Spiel durch einen Sieg beendet wurde
        } else {
            status = false;
        }
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
        if(tile != null && (tile.isNormalGameTile() || (this.isEditorMode() && tile.isHoleTile()))){
            if(this.gameField.isFieldMiddleGamefield(xTarget, yTarget) &&
                    this.gameField.isFieldFree(xTarget, yTarget)){
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
        if(tile != null && (tile.isNormalGameTile() || (this.isEditorMode() && tile.isHoleTile()))){
            this.gameField.resetTile(x, y); //Spielstein von der alten Position

            this.gui.moveTileGameFieldToSelection(tile.getTileName().ordinal()); //das Graphische Bewegen des Spielsteins
            // in die Spielsteinauswahl

            this.highlightTileIfWrongPlaced();
        } else {
            status = false;
        }
        return(status);
    }

    /**
     * Methode welche die Randfarbe aendert
     * schaltet bei jedem Aufruf zur jeweils naechsten Farbe
     * @param x die Spalte des Randes
     * @param y die Reihe des Randes
     */
    public void  toggleBorderColor(int x, int y){
        if(this.gameField.isFieldBorder(x, y)){
            Tile currTile = this.gameField.getTile(x, y); //die aktuelle Farbe
            Tile newBorderTile; //die naechste Farbe
            //wenn es sich aktuell um nicht um ein Randstueck handelt (noch nichts gelegt) oder die letzte Randfarbe
            // liegt

            if(!currTile.isTileBorderLayable() || currTile.getTileName().equals(TileNames.YYYY)) {
                newBorderTile = new Tile(TileNames.RRRR); //wieder auf Rot schalten (erste Farbe)
            } else { //bei allen anderen Randfarben
                newBorderTile = new Tile(TileNames.values()[currTile.getTileName().ordinal() + 1]); //die naechste Randfarbe
            }

            this.gameField.layTile(x, y, newBorderTile);
            this.gui.setBorderColor(x, y, newBorderTile.getTileName()); //die Farbaenderung visuell sichtbar machen

        }
    }

    /**
     * rotiert einen Spielstein
     * @param tileIndex der Index nach TileNames Reihenfolge des zu rotierenden Spielsteins
     */
    public void rotateGameTile(int tileIndex){
        Tile tile = gameField.getTiles().getTileByTileNamesIndex(tileIndex); //Stein in der Spielsteinauswahl suchen
        if(tile == null){ //Stein nicht in der Spielsteinauswahl -> liegt auf dem Spielfeld
            tile = this.gameField.getTileByTileNamesIndexFromGameField(tileIndex); //Stein im Spielfeld suchen
        }
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
            this.gui.showCustomException(new CustomException(CustomException.MESSAGE_WIN));
            this.setGameFlow(false, false);
        }
    }

    /**
     * Getter welcher zurueckgibt ob der EditorMode aktiv ist
     * @return ob der EditorMode aktiv ist
     */
    public boolean isEditorMode(){
        return(this.gui.isEditorMode());
    }

    /**
     * Methode welche einen weiteren Spielstein legt.
     * @return ob das Spielfeld loesbar ist und somit ein naechster Spielstein gelegt werden konnte
     */
    private boolean layHintTile(){
        GameField solvedGameFieldCopy = this.gameField.solveGameFieldAsCopy(); //das geloeste Spielfeld oder
        // null falls nicht
        if(solvedGameFieldCopy != null) { //Spielfeld wurde geloest
            Position pos = new Position(0, 0);
            this.gameField.goToNextFreeMiddleField(this.gameField, pos); //das naechste freie Feld finden im
            // Original Spielfeld
            Tile hintTileCopy = solvedGameFieldCopy.getTile(pos.getX(), pos.getY()); //den Kopie Spielsteinim geloesten
            // Spielfeld an der Stelle wo im Original Spielfeld das erste Feld noch nicht geloest wurde

            Tile hintTileOriginal = gameField.getTiles().getTileByNameWithRotation(
                    hintTileCopy.getTileNameStringWithRotation()); //der Kopie Spielstein als Original Spielstein

            this.moveTileFromNotLaidTilesToGameField(pos.getX(), pos.getY(),
                    hintTileOriginal.getTileName().ordinal()); //Spielstein legen

            //Graphisch den gefundenen Spielstein anzeigen
            this.gui.moveTileSelectionToGameField(pos.getX(), pos.getY(), hintTileOriginal.getTileName().ordinal());
            this.gui.rotateTile(hintTileOriginal.getTileName().ordinal(),
                    Rotation.rotationToDegrees(hintTileOriginal.getRotation()));
            return(true);
        } else { //Spielfeld wurde nicht geloest
            //Fehler, das das Spielfeld nicht loesbar ist
            return(false);
        }
    }

    /**
     * Methode welche ein bestehendes Spiel neustartet.
     * (entfernt alle Spielsteine aus dem Spielfeld, laesst aber den Rand und die Lochsteine)
     * @param highlightWrongPlaced ob die Hervorhebung der Spielsteine falls falsch gelegt oder vorher falsch und nun
     *                             richtig aktualisiert werden soll
     */
    public void removeGameFieldTiles(boolean highlightWrongPlaced){
        this.gameField.removeLayableMiddleGameFieldTiles();
        if(highlightWrongPlaced)
            this.highlightTileIfWrongPlaced(); //falls es falsche Felder gab die Markierung wieder wegnehmen
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
