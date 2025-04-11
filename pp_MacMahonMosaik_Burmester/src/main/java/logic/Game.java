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
    Tiles tiles;
    boolean editorMode;



    /**
     * Konstruktor welcher ein neues leeres Spiel erzeugt
     */
    public Game(GUIConnector gui, int heigth, int width){
        this.gameField = new GameField(heigth, width);
        this.tiles = new Tiles();
        this.gui = gui;
        this.editorMode = false;

        this.gui.updateGridPaneFormat(this.gameField);
        this.gui.displayGameFieldTiles(this, this.gameField);
        this.gui.displayNotUsedTiles(this, this.tiles);
        this.gui.displayBorder(this, this.gameField);
    }


    /**
     * Methode welche umschaltet ob der Editor Mode aktiv ist oder nicht
     */
    public void setEditorMode(boolean isEditorMode){
        System.out.println("EditorMode: " + isEditorMode);
        if(!isEditorMode){ //kein EditorMode
            //this.gui.displayGameFieldTiles(this, this.gameField); TODO hiermit werden die tiles nach neuem laden entfernt
            this.gui.displayNotUsedTiles(this, this.tiles);
            this.gui.displayBorder(this, this.gameField);
        } else { //Editor Mode
            this.gui.fillRightGridPaneWithEditorPieces(true); //TODO withHoles Wert an Groeße Binden
            //this.gui.fillRightGridPaneWithEditorPieces(this.gameField.getGameField().length - 2
            //        * this.gameField.getGameField()[0].length - 2 > 24);
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
     * @return ob der Spielstein von der Auswahl auf das Spielfeld gelegt werden konnte
     */
    public boolean moveTileFromNotLaidTilesToGameField(int x, int y, int tileIndex){
        Tile tile  = this.tiles.getTile(tileIndex);
        boolean status = true;
        if(tile != null){
            if(this.gameField.isFieldFree(x, y)){
                this.tiles.removeTile(tile); //Spielstein aus den nicht gelegten loeschen
                this.gameField.layTile(x, y, tile); //Spielstein auf das Spielfeld legen
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
        if(tile != null && tile.isTileLayable()){
            if(this.gameField.isFieldFree(xTarget, yTarget)){
                this.gameField.layTile(xTarget, yTarget, tile); //Spielstein auf die neue Position des Spielfelds legen
                this.gameField.resetTile(xStart, yStart); //Spielstein von der alten Position ////Spielstein von der alten Position
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
     * @return ob der Spielstein erfolgreich zurueckgelegt werden konnte
     */
    public boolean moveTileFromGamefieldToNotLaidTileSelection(int x, int y){
        Tile tile = this.gameField.getTile(x, y);
        boolean status = true;
        if(tile != null && tile.isTileLayable()){
            this.gameField.resetTile(x, y); //Spielstein von der alten Position
            // des Spielfelds loeschen
            this.tiles.addTile(tile); //Spielstein wieder der Spielsteinauswahl hinzufuegen
            System.out.println(this.gameField.toString());
            //System.out.println(this.tiles.toString());
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
}
