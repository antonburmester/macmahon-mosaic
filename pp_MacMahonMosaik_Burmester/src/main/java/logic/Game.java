package logic;

/**
 * Klasse welche das Spiel koodiniert
 * //TODO
 *
 * @author Anton Burmester
 */
public class Game {
    private final GUIConnector gui;
    GameField gameField;
    GameTiles gameTiles;
    HoleTiles holeTiles;
    boolean editorMode;



    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt
     */
    public Game(GUIConnector gui, int heigth, int width){
        this.gui = gui;
        this.editorMode = false;

        this.gameField = new GameField(heigth, width);
        this.gameTiles = new GameTiles();
        int holesAmount = heigth * width - 24;
        System.out.println("holes Amount: " + Math.max(holesAmount, 0));
        this.holeTiles = new HoleTiles(Math.max(holesAmount, 0));

        this.gui.updateGridPaneFormat(this.gameField);
        this.gui.displayGameFieldTiles(this, this.gameField);
        this.gui.displayNotUsedTiles(this, this.gameTiles);
        this.gui.displayBorder(this, this.gameField);
    }


    /**
     * Methode welche umschaltet ob der Editor Mode aktiv ist oder nicht
     */
    public void setEditorMode(boolean isEditorMode){
        System.out.println("EditorMode: " + isEditorMode);
        if(!isEditorMode){ //kein EditorMode
            //this.gui.displayGameFieldTiles(this, this.gameField); TODO hiermit werden die gameTiles nach neuem laden entfernt
            this.gui.displayNotUsedTiles(this, this.gameTiles);
            this.gui.displayBorder(this, this.gameField);
            this.editorMode = false;
        } else { //Editor Mode
            this.gui.fillRightGridPaneWithEditorPieces(this, true); //TODO withHoles Wert an Groeße Binden
            //this.gui.fillRightGridPaneWithEditorPieces(this.gameField.getGameField().length - 2
            //        * this.gameField.getGameField()[0].length - 2 > 24);
            this.editorMode = true;
        }
    }

    /**
     * Methode welche den Enum Namen des Spielsteins welcher im Spielfeld an einer Position liegt zurueckgibt
     * @param x die Spalte
     * @param y die Reihe
     * @return den Namen des Spielsteins
     */
    public String getTileNameFromGameField(int x, int y){
        return(this.gameField.getTile(x,y).getTileString());
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
        Tile tile = isGameTile ? this.gameTiles.getTile(tileIndex) : this.holeTiles.getTile(tileIndex);
        System.out.println("TILELLL: " + tile.getTileString() + " Tile Index: " + tileIndex);
        boolean status = true;
        if (this.gameField.isGameFieldFieldFree(x, y)) {
            if (isGameTile) { //Spielstein aus den nicht gelegten Spielsteinen loeschen
                //this.gameTiles.removeTile(tile);
                //this.gameTiles.removeTile(tileIndex);
                this.gameTiles.setTileLaidStatus(tile, true);
            } else { //Loch aus den nicht gelegten Loechern loeschen
                //this.holeTiles.removeTile(tileIndex);
                this.holeTiles.setTileLaidStatus(tile, true);
            }
            this.gameField.layTile(x, y, tile); //Spielstein auf das Spielfeld legen
            System.out.println(this.gameField.toString());
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
            if(this.gameField.isGameFieldFieldFree(xTarget, yTarget)){
                this.gameField.layTile(xTarget, yTarget, tile); //Spielstein auf die neue Position des Spielfelds legen
                this.gameField.resetTile(xStart, yStart); //Spielstein von der alten Position
                // des Spielfelds loeschen
                System.out.println(this.gameField.toString());
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
                //this.gameTiles.addTile(tile); //Spielstein wieder der Spielsteinauswahl hinzufuegen
                this.gameTiles.setTileLaidStatus(tile, false);
            } else { //wenn es sich um einen Spielstein handelt
                //this.holeTiles.addTile(tile); //Spielstein wieder der Lochsteinauswahl hinzufuegen
                this.holeTiles.setTileLaidStatus(tile, false);
            }
            System.out.println(this.gameField.toString());
            //System.out.println(this.gameTiles.toString());
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
        boolean status = true;
        if(tile != null && tile.isTileBorderCompatible()){
            this.gameField.colorBorder(x, y, tile); //Spielstein von der alten Position
            // des Spielfelds loeschen
            System.out.println(this.gameField.toString());
        } else {
            status = false;
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



    public void setOnDragEntered(int x, int y, boolean middleGridPane){
        if(middleGridPane){
            if(this.gameField.isGameFieldFieldFree(x, y)){

            }
        }
    }
}
