package logic;

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
    private GameField gameField;
    private Tiles tiles;
    private Tiles holeTiles;
    private boolean editorMode;


    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt
     */
    public Game(GUIConnector gui, int heigth, int width){
        this.gui = gui;
        this.editorMode = false;

        this.gameField = new GameField(heigth, width);
        this.tiles = new Tiles();
        int holesAmount = heigth * width - 24;
        this.holeTiles = new Tiles(Math.max(holesAmount, 0));

        this.gui.updateGridPaneFormat(this.gameField);
        this.gui.displayGameFieldTiles(this, this.gameField);
        this.gui.displayNotUsedTiles(this, this.tiles);
        this.gui.displayBorder(this, this.gameField);
    }

    /**
     * Konstruktor welcher ein Spiel auf Grundlage eines StringArrays erstellt
     * TODO implementieren
     */
    public Game(GUIConnector gui, String[][] inputGameField){
        this.gui = gui;
        this.editorMode = false;

        this.tiles = new Tiles();
        //this.gameField = new GameField(inputGameField, this.tiles); //TODO
        int holesAmount = (inputGameField.length - 2) * (inputGameField[0].length - 2) - 24;
        this.holeTiles = new Tiles(Math.max(holesAmount, 0));

        this.gui.updateGridPaneFormat(this.gameField);
        this.gui.displayGameFieldTiles(this, this.gameField);
        this.gui.displayNotUsedTiles(this, this.tiles);
        this.gui.displayBorder(this, this.gameField);
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
     * Methode welche umschaltet ob der Editor Mode aktiv ist oder nicht
     * //TODO richtig implementieren
     */
    public void setEditorMode(boolean isEditorMode){
        if(!isEditorMode){ //kein EditorMode
            //this.gui.displayGameFieldTiles(this, this.gameField);
            this.gui.displayNotUsedTiles(this, this.tiles);
            this.gui.displayBorder(this, this.gameField);
            this.editorMode = false;
        } else { //Editor Mode
            this.gui.fillRightGridPaneWithEditorPieces(this, true);
            //this.gui.fillRightGridPaneWithEditorPieces(this.gameField.getGameField().length - 2
            //        * this.gameField.getGameField()[0].length - 2 > 24);
            this.editorMode = true;
        }
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
        if (this.gameField.isFieldFieldFree(x, y)) {
            if (isGameTile) { //Spielstein aus den nicht gelegten Spielsteinen loeschen
                this.tiles.setTileLaidStatus(tile, true); //TODO glaube ich irrelevant
            } else { //Loch aus den nicht gelegten Loechern loeschen
                this.holeTiles.setTileLaidStatus(tile, true); //TODO glaube ich irrelevant
            }
            this.gameField.layTile(x, y, tile); //Spielstein auf das Spielfeld legen
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
                tile.resetTileRotation(); //Rotation in der Logik zuruecksetzen
            } else { //wenn es sich um einen Lochstein handelt
                //Spielstein wieder der Lochsteinauswahl hinzufuegen
                this.holeTiles.setTileLaidStatus(tile, false);
            }
        } else {
            status = false;
        }
        return(status);
    }

    /**
     * Methode welche den Rand des Spielfelds einfarbt
     * @param x die Spalte des Spielsteins
     * @param y die Reihe des Spielsteins
     * @return ob der Spielstein erfolgreich zurueckgelegt werden konnte
     */
    public boolean colorBorder(int x, int y, Tile tile){
        boolean status = false;
        if(tile != null){
            if(this.gameField.layTile(x, y, tile)) {
                status = true;
            }
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
     * Methode welche prueft ob das Spielfeld im aktuellen Zustand loesbar ist
     * @return ob das Spielfeld loesbar ist
     */
    public boolean isGameFieldSolvable(){
        Tiles clonedTiles = this.tiles.cloneGameTiles();
        Tiles clonedHoleTiles = this.holeTiles.cloneGameTiles();
        GameField clonedGameField = this.gameField.cloneGameField(
                this.tiles, this.holeTiles, clonedTiles, clonedHoleTiles);
        return(this.isGameFieldSolvableRecoursive(clonedGameField, clonedTiles));
    }

    /**
     * Rekursive Methode welche prueft ob das Spielfeld im aktuellen Zustand loesbar ist
     * Hierfuer wird gesucht bis ein neuer Stein gefunden wurde der auf ein leeres Feld passt
     * Wenn an einen Punkt gekommen wird wo es keine weiteren Steine mehr gibt wird durch Backtracking zurueckgegangen
     * und eine andere Kombination versucht
     * Das geht solange bis das Spielfeld loesbar ist oder jede Kombination versucht wurde
     * @param clonedGameField eine Kopie des aktuellen Spielfelds
     * @param clonedTiles eine Kopie der Spielsteine
     * @return ob eine passende Kombination gefunden wurde
     */
    private boolean isGameFieldSolvableRecoursive(GameField clonedGameField, Tiles clonedTiles){
        if(clonedGameField.checkIfGameFieldSolved(false)) return(true);

        //jedes Feld durchlaufen
        for(int y = 0; y < clonedGameField.getGameField().length; y++){ //Hoehenindex
            for(int x = 0; x < clonedGameField.getGameField()[y].length; x++){ //Breitenindex
                Tile currGameFieldTile = clonedGameField.getTile(x, y); //das aktuelle Feld
                if(currGameFieldTile.isPlaceHolderTile()){ //wenn es ein leeres Feld ist
                    for(Tile currTile : clonedTiles.getTiles()){ //alle Spielsteine durchlaufen
                        if(!currTile.getIsLaid()){ //wenn der aktuelle Spielstein noch nicht gelegt wurde
                            for(int rotation = 0; rotation < 270; rotation += 90){ //alle Rotationen durchlaufen

                            }
                        }
                    }
                }
            }
        }

        //return(this.isGameFieldSolvableRecoursive(clonedGameField, clonedTiles));
        return true; //TODO
    }
}
