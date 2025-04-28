package logic;

/**
 * Klasse welche Spielsteine ohne NNNN und HHHH als Array enthaelt
 * Die Reihenfolge orientiert sich an der Reihenfolge des TileNames Enum
 *
 * @author Anton Burmester
 */

public class Tiles {
    private final Tile[] tiles;

    /**
     * Konstruktor welcher diese Klasse mit allen Spielsteinen fuellt
     */
    public Tiles(){
        this.tiles = new Tile[TileNames.values().length - 2];
        for (int i = 0; i < TileNames.values().length - 2; i++) { //durchlaeuft alle Mosaiksteine bis auf HHHH und NNNN
            this.tiles[i] = new Tile(TileNames.values()[i]);
        }
    }

    /**
     * Konstruktor welcher diese Klasse mit bestehenden Spielsteinen laedt
     */
    public Tiles(Tile[] tiles){
        this.tiles = tiles;
    }

    /**
     * Konstruktor welcher diese Klasse mit Lochsteinen fuelt
     * @param holesCount die Anzahl der Lochsteine
     */
    public Tiles(int holesCount) {
        this.tiles = new Tile[holesCount];
        for(int i = 0; i < holesCount; i++){
            this.tiles[i] = new Tile(TileNames.HHHH);
        }
    }

    /**
     * Methode welche diese Instanz klont als DeepCopy
     * @return eine DeepCopy dieser Instanz
     */
    public Tiles cloneGameTiles(){
        Tile[] clonedTiles = new Tile[this.tiles.length];
        for(int i = 0; i < this.tiles.length; i++){ //jeder Spielstein
            clonedTiles[i] = this.tiles[i].cloneTile(); //Spielstein kopieren und nicht die refferenz
        }
        return(new Tiles(clonedTiles));
    }

    /**
     * Public getter um die Private Nutzlast des Arrays der Mosaiksteine zu bekommen
     * @return alle Mosaiksteine die in der Menge sind
     */
    public Tile[] getTiles(){
        return(this.tiles);
    }

    /**
     * Methode welche den Spielstein an der gewuenschten Stelle zurueckgibt
     * Ist kein Spielstein vorhanden an der Stelle wird null zurueckgegeben
     * @param index der Index es Spielsteins
     * @return die Instanz des Spielsteins oder null falls dieser nicht vorhanden ist weil er geloescht wurde
     */
    public Tile getTile(int index){
        return(this.tiles[index]);
    }

    /**
     * Methode welche den Index eines uebergebenen Spielsteins zurueckgibt
     * @param tile der uebergebene Spielstein
     * @return der Index des Spielsteins
     */
    public int getTileIndex(Tile tile){
        int index = -1;
        for(int i = 0; i < this.tiles.length; i++){
            if(this.getTile(i).equals(tile)){
                index = i;
            }
        }
        return(index);
    }

    /**
     * Methode welche den Status ob ein Stein im Spielfeld gesetzt ist setzt
     * @param tile der Spielstein
     * @param status ob der Spielstein gelegt wird oder nicht
     */
    public void setTileLaidStatus(Tile tile, boolean status){
        tile.setIsLaid(status);
    }

    /**
     * Methode welche prueft ob alle Loecher gelegt wurden
     * @return ob alle Loecher gelegt wurden
     */
    public boolean allTilesUsed(){
        boolean status = true;
        for(int i = 0; i < this.tiles.length; i++){
            if(!this.getTile(i).getIsLaid()){
                status = false;
            }
        }
        return(status);
    }

    /**
     * Methode welche die richtige Klasse aus den Spielsteinen sucht
     * Der Name muss uebereinstimmen und der Spielstein darf noch nicht gelegt worden sein
     * @param tileName der Name des Spielsteins inklusive Rotationen
     * @return die Instanz welche zum uebergebenen Namen passt und die Rotationen werden gespeichert
     */
    public Tile getTileByNameWithRotation(String tileName){
        Tile resultTile = null;
        for(int i = 0; i < this.tiles.length; i++){ //jeden Spielstein durchlaufen
            Tile currTile = this.getTile(i); //der aktuelle Spielstein
            if(!currTile.getIsLaid()){ //Spielstein noch nicht gelegt
                //koennte auch ersetzt werden durch isGameFieldTile
                int rotations = currTile.isHoleTile() || currTile.isPlaceHolderTile() ? 90 : 360;
                for(int rotation = 0; rotation < rotations; rotation += 90){ //jede Rotation durchlaufen
                    if(currTile.getTileNameWithRotation().equals(tileName)){ //wenn der Spielstein mit Rotation
                        // dem uebergebenen Namen gleicht
                        resultTile = currTile;
                    } else { //wenn der Spielstein nicht dem Namen gleicht
                        currTile.rotateTile(); //den Spielstein rotieren
                    }
                }
                if(resultTile == null) { //wenn der aktuelle Spielstein in allen Rotationen nicht passt
                    currTile.resetTileRotation(); //die Rotation zuruecksetzen
                }
            }
        }
        return(resultTile);
    }

    /**
     * gibt alle Mosaiksteine aus die in der Menge vorhanden sind
     * @return alle Mosaiksteine als String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Tiles: ").append("\n");
        for(Tile tile : this.tiles){
            sb.append(tile == null ? "null" : tile.getTileString()).append("\n");
        }
        return(sb.toString());
    }
}
