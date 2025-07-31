package logic;

import gui.ErrorHandler;

/**
 * Klasse welche das Spiel koodiniert
 * Diese Klasse ist die Schnittstelle zwischen GUI und Logik seitens des UserInterfaceController und der JavaFXGUI
 * Der UserInterfaceController speichert immer die aktuelle Instanz der Game Klasse und die JavaFXGUI nutzt
 * die Game Klasse nur zum setzen der Listener aber speichert nicht wie der UserInterfaceController die Instanz
 *
 * @author Anton Burmester
 */
public class Game {
    private final GUIConnector gui;
    private final GameField gameField;
    private final Tiles tiles;
    private final Tiles holeTiles;
    private boolean editorMode;


    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt
     */
    public Game(GUIConnector gui, int heigth, int width){
        this.gui = gui;
        this.editorMode = true;

        this.gameField = new GameField(heigth, width);
        this.tiles = new Tiles();
        int holesAmount = heigth * width - 24;
        this.holeTiles = new Tiles(Math.max(holesAmount, 0));
    }

    /**
     * Konstruktor welcher das Standard Spiel erstellt nutzt hierfür den Konstruktor welcher ein Spiel auf Grundlage
     * eines StringArrays erstellt
     */
    public Game(GUIConnector gui){
        this(gui, new String[][] {{"NNNN", "NNGN", "NNGN", "NNNN"},
                                  {"NGNN", "NNNN", "NNNN", "NNNG"},
                                  {"NRNN", "NNNN", "NNNN", "NNNR"},
                                  {"NNNN", "YNNN", "YNNN", "NNNN"}});
    }

    /**
     * Konstruktor welcher ein Spiel auf Grundlage eines StringArrays erstellt
     */
    public Game(GUIConnector gui, String[][] inputGameField){
        this.gui = gui;

        this.tiles = new Tiles();
        int holesAmount = (inputGameField.length - 2) * (inputGameField[0].length - 2) - 24;
        this.holeTiles = new Tiles(Math.max(holesAmount, 0));
        this.gameField = new GameField(inputGameField, this.tiles, this.holeTiles);
    }

    /**
     * Konstruktor welcher ein Spiel initialisiert auf Grundlage eines neuen Spielfelds, neuer Spielsteine und neuer
     * Lochsteine
     * @param gameField die Instanz des neuen Spielfelds
     * @param tiles die Instanz der neuen Spielsteine
     * @param holeTiles die Instanz der neuen Lochsteine
     */
    private Game(GameField gameField, Tiles tiles, Tiles holeTiles){
        this.gui = null;
        this.gameField = gameField;
        this.tiles = tiles;
        this.holeTiles = holeTiles;
    }

    /**
     * Methode welche die Game Instanz kopiert und erneuert
     * @return die neue Game Instanz
     */
    public Game cloneGame(){
        Tiles clonedTiles = this.tiles.cloneGameTiles();
        Tiles clonedHoleTiles = this.holeTiles.cloneGameTiles();
        GameField clonedGameField = this.gameField.cloneGameField(
                this.tiles, this.holeTiles, clonedTiles, clonedHoleTiles);
        return(new Game(clonedGameField, clonedTiles, clonedHoleTiles));
    }

    /**
     * Methode welche den Wahrheitswert ob ein Spiel aktiv ist setzt
     * @param isActive ob das Spiel aktiv ist
     */
    public void setIsGameActive(boolean isActive){
        if(isActive && this.gameField.isGameFieldBorderSetted()) { //Spiel soll aktiv werden und Rand ist gesetzt
            this.gui.setDisableRightGridPane(false);
        } else if (isActive){ //Spiel soll aktiv werden  aber Rand ist nicht komplett gesetzt
            this.gui.setDisableRightGridPane(true);
            ErrorHandler.showError(new CustomException(CustomException.ERROR_BORDER_NOT_SETTED));
        } else { //Spiel soll nicht aktiv werden
            this.gui.setDisableRightGridPane(true);
        }
    }

    /**
     * Methode welche die Spielstein Instanz zurueckgibt
     * @return die Instanz der Klasse aller Spielsteine
     */
    public Tiles getTiles(){
        return(this.tiles);
    }

    /**
     * Methdode welche die Spielfeld Instanz zurueckgibt
     * @return die Instanz der Klasse des Spielfelds
     */
    public GameField getGameField(){
        return(this.gameField);
    }

    /**
     * Methode welche einen String vom Spielfeld in der Aufgabenstellungsform zurueckgibt
     * @return das Spielfeld als String in Aufgabenstellungsform
     */
    public String[][] getGameFieldString(){
        return(this.gameField.translateToSpielstandsdatei());
    }

    /**
     * Methode welche setzt ob der Editor Mode aktiv ist oder nicht und wenn ja die Spielstein Auswahl deaktiviert
     * @param status ob der eingeschaltet werden soll oder aus
     */
    public void setIsEditorMode(boolean status){
        this.editorMode = status;
        if(status){
            this.removeGameFieldTiles();
            this.updateTiles();
            this.gui.setDisableRightGridPane(true);
        }
    }

    /**
     * Methode welche die Spielsteine sichtbar macht und positioniert im Feld und der Auswahl.
     * positioniert die Spielsteine zwischen Spielfeld und Auswahl, setzt die Loecher im Spielfeld und faerbt den Rand
     */
    public void updateTiles(){
        //Bilder, Loecher und Faerbungen anzeigen
        this.gui.displayGameFieldTiles(this.getGameField());
        this.gui.displayNotUsedTiles(this.getTiles());
    }

    /**
     * Methode welche prueft ob das Spielfeld spielbar ist also ob alle Loecher falls vorhanden gelegt wurden und
     * der Rand voll mit Randstuecken gefuellt ist
     * @return ob das Spielfeld spielbar ist
     */
    public boolean isGameFieldPlayable(){
        return(this.holeTiles.allTilesUsed() && this.gameField.isGameFieldBorderSetted());
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
     * @param isGameTile ob es sich bei dem Stein um einen Spielstein oder ein Lochstein handelt
     * @return ob der Spielstein von der Auswahl auf das Spielfeld gelegt werden konnte
     */
    public boolean moveTileFromNotLaidTilesToGameField(int x, int y, int tileIndex, boolean isGameTile){
        //wenn isGameTile dann wird der in den Spielsteinen gesucht und wenn nicht dann in den Lochsteinen
        Tile tile = isGameTile ? this.tiles.getTile(tileIndex) : this.holeTiles.getTile(tileIndex);
        boolean status = true;
        //Feld ist frei und es handelt sich um das mittlere Spielfeld
        if (this.gameField.isFieldFieldFree(x, y) && this.gameField.isFieldMiddleGamefield(x, y)) {
            if (isGameTile) { //Spielstein aus den nicht gelegten Spielsteinen loeschen
                this.tiles.setTileLaidStatus(tile, true); //TODO glaube ich irrelevant
            } else { //Loch aus den nicht gelegten Loechern loeschen
                this.holeTiles.setTileLaidStatus(tile, true); //TODO glaube ich irrelevant
            }
            this.gameField.layTile(x, y, tile); //Spielstein auf das Spielfeld legen
        } else {
            status = false;
        }
        System.out.println(this.gameField.toString());
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
            if(this.gameField.isFieldFieldFree(xTarget, yTarget)){
                this.gameField.layTile(xTarget, yTarget, tile); //Spielstein auf die neue Position des Spielfelds legen
                this.gameField.resetTile(xStart, yStart); //Spielstein von der alten Position
                // des Spielfelds loeschen
            } else {
                status = false;
            }
        } else {
            status = false;
        }
        System.out.println(this.gameField.toString());
        return(status);
    }

    /**
     * Methode welche  einen Spielstein vom Spielfeld wieder in die Auswahl hinzufuegt
     * @param x die Spalte des Spielsteins
     * @param y die Reihe des Spielsteins
     * @param isGameTile ob es sich bei dem Stein um einen Spielstein oder ein Lochstein handelt
     * @return ob der Spielstein erfolgreich zurueckgelegt werden konnte
     */
    public boolean moveTileFromGamefieldToNotLaidTileSelection(int x, int y, boolean isGameTile){
        Tile tile = this.gameField.getTile(x, y);
        boolean status = true;
        //ob der Spielstein gefunden wurde und entweder ein normaler Stein ist oder der EditorMode aktiv und Loch Stein
        if(tile != null && (tile.isNormalGameTile() || (this.editorMode && tile.isHoleTile()))){
            this.gameField.resetTile(x, y); //Spielstein von der alten Position
            // des Spielfelds loeschen
            if(isGameTile) { //wenn es sich um einen Spielstein handelt
                //Spielstein wieder der Spielsteinauswahl hinzufuegen
                this.tiles.setTileLaidStatus(tile, false);
            } else { //wenn es sich um einen Lochstein handelt
                //Spielstein wieder der Lochsteinauswahl hinzufuegen
                this.holeTiles.setTileLaidStatus(tile, false);
            }
        } else {
            status = false;
        }
        System.out.println(this.gameField.toString());
        return(status);
    }

    public boolean isValidMove(int xStart, int yStart, int xTarget, int yTarget){
        boolean status = false;

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
            //wenn es sich aktuell um nicht um ein Randstueck handelt (noch nichts gelegt) oder die letzte Randfarbe liegt
            if(!currTile.isTileBorderLayable() || currTile.getTile().equals(TileNames.YYYY)) {
                newBorderTile = new Tile(TileNames.RRRR); //wieder auf Rot schalten (erste Farbe)
            } else { //bei allen anderen Randfarben
                newBorderTile = new Tile(TileNames.values()[currTile.getTile().ordinal() + 1]); //die naechste Randfarbe
            }
            this.gameField.layTile(x, y, newBorderTile);
            System.out.println(this.gameField.toString());
            status = true;
        }
        return(status);
    }

    /**
     * Getter welcher zurueckgibt ob der EditorMode aktiv ist
     * @return ob der EditorMode aktiv ist
     */
    public boolean isEditorMode(){
        return(this.editorMode);
    }


    /**
     * rotiert einen Spielstein
     * @param tileIndex der Index des zu rotierenden Spielsteins
     */
    public void rotateGameTile(int tileIndex){
        this.tiles.getTile(tileIndex).rotateTile();
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
        System.out.println(this.gameField.toString());
    }

    /**
     * Methode welche prueft ob das Spielfeld im aktuellen Zustand loesbar ist
     * @return ob das Spielfeld loesbar ist
     * TODO implement
     */
    public boolean isGameFieldSolvable(){
        /*
        Tiles clonedTiles = this.tiles.cloneGameTiles();
        Tiles clonedHoleTiles = this.holeTiles.cloneGameTiles();
        GameField clonedGameField = this.gameField.cloneGameField(
                this.tiles, this.holeTiles, clonedTiles, clonedHoleTiles);
        return(this.isGameFieldSolvableRecoursive(clonedGameField, clonedTiles));
         */
        /*
         * Lösungsansatz:
         * Ein Array welches das Spielfeld groß ist
         * auf jedes noch nicht gelegtes Feld alle moeglichen Spielsteine legen
         * nun alles durchlaufen und pruefen (alle Kombinationen) bis eine gefunden wurde
         * oder Rekoursiv mit Backtracking
         */
        return(this.isGameFieldSolvable(this.gameField, this.tiles, this.holeTiles));
    }

    /**
     * Methode welche prueft ob das Spielfeld im aktuellen Zustand loesbar ist
     * Hierfuer wird gesucht bis ein neuer Stein gefunden wurde der auf ein leeres Feld passt
     * Wenn an einen Punkt gekommen wird wo es keine weiteren Steine mehr gibt wird durch Backtracking zurueckgegangen
     * und eine andere Kombination versucht
     * Das geht solange bis das Spielfeld loesbar ist oder jede Kombination versucht wurde
     * @param gameField das Spielfeld
     * @param tiles die Spielsteine
     * @param holeTiles die Lochsteine
     * @return ob eine passende Kombination gefunden wurde
     * TODO IMPLEMENT
     */

    private boolean isGameFieldSolvable(GameField gameField, Tiles tiles, Tiles holeTiles){
        if(gameField.checkIfGameFieldSolved(false)) return(true);

        Tiles clonedTiles = tiles.cloneGameTiles();
        Tiles clonedHoleTiles = holeTiles.cloneGameTiles();
        GameField clonedGameField = gameField.cloneGameField(
                tiles, holeTiles, clonedTiles, clonedHoleTiles);

        System.out.println("GameField Cloned: " + clonedGameField.toString());

        GameField newGameField = new GameField(gameField.getGameFieldHeight(), gameField.getGameFieldWidth());

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
                System.out.println("Naechstes Feld (kein Backtracking): " + pos.toString());
            } else { //es gab einen Schritt zurueck Backtracking
                System.out.println("Letzter Schritt war Backtracking zu der Position: " + pos.toString());
                nextFieldThere = false;
            }

            if(nextFieldThere || lastTileBeforeGoingBack != null){ //es gibt ein naechstes Spielfeld
                Tile nextMatchingTile = this.findNextMatchingTile(clonedGameField, clonedTiles, pos,
                        lastTileBeforeGoingBack);
                if(nextMatchingTile != null){ //es gibt einen passenden Spielstein
                    clonedGameField.resetTile(pos.getX(), pos.getY());
                    clonedGameField.layTile(pos.getX(), pos.getY(), nextMatchingTile); //Spielstein legen
                    //nextMatchingTile.setIsLaid(true);
                    lastTileBeforeGoingBack = null; //da naechster Spielstein gefunden wurde
                    System.out.println("Es gibt einen passenden Spielstein fuer position: " + pos + " Spielstein: " + nextMatchingTile.getTileNameWithRotation());
                } else { //es gibt keinen passenden Spielstein
                    boolean previousFieldThere = this.goToPreviousFreeMiddleField(gameField, pos); //das vorherige freie Feld suchen
                    if(previousFieldThere) { //es gibt ein vorheriges freies Feld, deshalb dieses zurueckgehen
                        lastTileBeforeGoingBack = clonedGameField.getTile(pos.getX(), pos.getY()); //fuer Backtracking
                        clonedGameField.resetTile(pos.getX(), pos.getY()); //Spielfeld zuruecksetzen
                        //lastTileBeforeGoingBack.setIsLaid(false);
                        System.out.println("Backtracking: Es gibt einen vorherigen Spielstein Position: " + pos);
                    } else { //es gibt kein weiteres vorheriges Spielfeld
                        searchActive = false; //Spielfeld nicht loesbar
                        System.out.println("Backtracking: Es gibt keinen vorherigen Spielstein Position: " + pos);
                    }
                }
            } else { //kein weiteres Feld gefunden
                searchActive = false; //Spielfeld sollte geloest sein
                System.out.println("Es gibt kein naechstes Spielfeld! Schleife beendet");
            }
        }

        System.out.println("Ist solved: " + clonedGameField.checkIfGameFieldSolved(false));
        System.out.println(clonedGameField.toString()); //TODO remove

        return(clonedGameField.checkIfGameFieldSolved(false)); //ob das Spielfeld nach allen Kombinationen
        // geloest wurde
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
    private Tile findNextMatchingTile(GameField inputGameField, Tiles inputTiles, Position pos, Tile lastTileBeforeGoingBack){
        //Tile tile = inputGameField.getTile(pos.getX(), pos.getY()); //null ist initialer Zustand und kodiert, das kein passender Spielstein gefunden wurde
        //System.out.println("Tile: " + tile.toString() + "X Pos: " + pos.getX() + "Y Pos: " + pos.getY());
        Tile tile = lastTileBeforeGoingBack;
        int startIndex = tile != null  && !tile.getTile().equals(TileNames.NNNN) ?
                inputTiles.getTileIndex(tile) : 0; //der Index zum Start
        // oder 0 falls es ein Aufruf fuer ein leeres Feld ist
        int startRotation = tile != null && !tile.getTile().equals(TileNames.NNNN) ?
                tile.getRotation() + 90 : 0; //die Rotation des übergebenen
        // Spielsteins zum Start oder 0 falls es ein Aufruf fuer ein leeres Feld ist
        // +90, da dies die einmalige Drehung eines Spielsteins ist und somit kein Spielstein mehrmals getestet wird

        //if(inputTiles.getTiles().length - 1 == startIndex && startRotation == 270) return(null); //es wurden schon
        // alle Spielsteine und Rotationen geprueft

        for(int tileIndex = startIndex; tileIndex < inputTiles.getTiles().length; tileIndex++){ //jeden moeglichen
            // Spielstein vom startIndex bis zum ende
            tile = inputTiles.getTile(tileIndex);
            if(!tile.getIsLaid()) { //nur nicht gelegte Spielsteine nutzen‚

                for (int rotation = startRotation; rotation < 360; rotation += 90) { //jede Rotation von der start Rotation an
                    tile.rotateTile(); //den Spielstein rotieren
                    inputGameField.layTile(pos.getX(), pos.getY(), tile);

                    if (inputGameField.isGameFieldTileMatching(pos.getX(), pos.getY(), true)) { //pruefen ob der neue Spielstein passt
                        inputGameField.resetTile(pos.getX(), pos.getY());

                        return (tile);
                    }
                    inputGameField.resetTile(pos.getX(), pos.getY());
                }
                tile.resetTileRotation();
            }
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
