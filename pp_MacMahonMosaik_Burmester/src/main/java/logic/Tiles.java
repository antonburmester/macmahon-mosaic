package logic;

import java.util.ArrayList;

/**
 * Klasse welche Spielsteine ohne NNNN und HHHH als Array enthaelt
 * Die Reihenfolge orientiert sich an der Reihenfolge des TileNames Enum
 *
 * @author Anton Burmester
 */

public class Tiles {
    private final ArrayList<Tile> tiles; //Nutzlast der Tile Instanzen in einem Array

    /**
     * Konstruktor welcher diese Klasse mit allen Spielsteinen fuellt
     */
    public Tiles(){
        this.tiles = new ArrayList<>();
        for (int i = 0; i < Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY; i++) { //durchlaeuft alle Mosaiksteine bis auf HHHH und
            // NNNN
            this.tiles.add(new Tile(TileNames.values()[i]));
        }
    }

    /**
     * Konstruktor welcher diese Klasse mit bestehenden Spielsteinen laedt
     */
    public Tiles(ArrayList<Tile> tiles){
        this.tiles = tiles;
    }

    /**
     * Konstruktor welcher diese Klasse mit Lochsteinen fuelt
     * @param holesCount die Anzahl der Lochsteine
     */
    public Tiles(int holesCount) {
        this.tiles = new ArrayList<>();
        for(int i = 0; i < holesCount; i++){
            this.tiles.add(new Tile(TileNames.HHHH));
        }
    }

    /**
     * Methode welche einen bestimmten Spielstein aus dem Array entfernt
     * @param removedTile der zu entfernende Spielstein
     * @return ob der Spielstein aus der Liste entfernt werden konnte oder gar nicht in der Liste war
     */
    public boolean removeTile(Tile removedTile){
        return(this.tiles.remove(removedTile));
    }

    /**
     * Methode welche einen bestimmten Spielstein dem Array hinzufuegt
     * @param addedTile der hinzuzufuegende Spielstein
     * @return ob der Spielstein der Liste hinzugefuegt werden konnte oder schon in der Liste war
     */
    public boolean addTile(Tile addedTile){
        return(this.tiles.add(addedTile));
    }

    /**
     * Methode welche einen bestimmten Spielstein an den Anfang des Tiles Array einfuegt
     * @param prependedTile der einzufugende Spielstein
     * @return ob der Spielstein hinzugefuegt wurde oder schon vorhanden war
     */
    public boolean prependTile(Tile prependedTile){
        if(!this.tiles.contains(prependedTile)) {
            this.tiles.addFirst(prependedTile);
            return(true);
        }
        return(false);
    }

    /**
     * Methode welche diese Instanz klont als DeepCopy
     * @return eine DeepCopy dieser Instanz
     */
    public Tiles cloneGameTiles(){
        ArrayList<Tile> clonedTiles = new ArrayList<>();
        for(Tile currTile: this.tiles){ //jeder Spielstein
            clonedTiles.add(currTile.cloneTile()); //Spielstein kopieren und nicht die refferenz
        }
        return(new Tiles(clonedTiles));
    }

    /**
     * Public getter um die Private Nutzlast des Arrays der Mosaiksteine zu bekommen
     * @return alle Mosaiksteine die in der Menge sind
     */
    public ArrayList<Tile> getTiles(){
        return(this.tiles);
    }

    /**
     * Methode welche den Spielstein an der gewuenschten Stelle zurueckgibt. Reihenfolge der TileNames
     * Ist kein Spielstein vorhanden an der Stelle wird null zurueckgegeben
     * @param index der Index es Spielsteins
     * @return die Instanz des Spielsteins oder null falls dieser nicht vorhanden ist weil er geloescht wurde
     */
    public Tile getTileByTileNamesIndex(int index){
        TileNames searchedTileName = TileNames.values()[index];
        for(Tile currTile : this.tiles){ //jeder Spielstein
            if(currTile.getTile().equals(searchedTileName)) return(currTile); //wenn TileName des Spielsteins dem
            // TileName am index gleicht
        }
        return(null);
    }

    /**
     * Methode welche den Spielstein an der gewuenschten Stelle zurueckgibt. Reihenfolge der TileNames
     * Ist kein Spielstein vorhanden an der Stelle wird null zurueckgegeben
     * @param index der Index es Spielsteins
     * @return die Instanz des Spielsteins oder null falls dieser nicht vorhanden ist weil er geloescht wurde
     */
    public Tile getTileByArrayIndex(int index){
        return(this.tiles.get(index));
    }

    /**
     * Methode welche den anhand des uebergebenen Spielsteins seine Position/ Index im Array sucht
     * @param tile der Spielstein
     * @return die Position im Array oder -1 falls nicht gefunden
     */
    public int getTileIndexInTiles(Tile tile){
        for(int i = 0; i < this.tiles.size(); i++){ //jeden Spielstein durchlaufen
            if(this.tiles.get(i).equals(tile)) return(i); //wenn aktueller Spielstein dem uebergebenen gleicht Index
            // zurueckgeben
        }
        return(-1);
    }

    /**
     * Methode welche einen Spielstein an eine bestimmte Position im Array einfuegt
     * @param index die Position im Array
     * @param tile der einzufuegende Spielstein
     * @return ob der Spielstein schon im Array war oder eingefuegt werden konnte
     */
    public boolean insertTile(int index, Tile tile){
        if(!this.tiles.contains(tile)){
            this.tiles.add(index, tile);
            return(true);
        }
        return(false);
    }

    /**
     * Methode welche die richtige Klasse aus den Spielsteinen sucht
     * Der Name muss uebereinstimmen und der Spielstein darf noch nicht gelegt worden sein
     * @param tileName der Name des Spielsteins inklusive Rotationen
     * @return die Instanz welche zum uebergebenen Namen passt und die Rotationen werden gespeichert oder null
     */
    public Tile getTileByNameWithRotation(String tileName){
        for(Tile currTile : this.tiles){ //jeden Spielstein durchlaufen
            Rotation originalRotation = currTile.getRotation(); //um die Rotation nach den versuchen zurueckzusetzen
            for(Rotation currRotation : Rotation.values()){ //jede Rotation durchlaufen
                if(currTile.getTileNameWithRotation().equals(tileName)){ //wenn der Spielstein mit Rotation
                    // dem uebergebenen Namen gleicht
                    return(currTile);
                } else { //wenn der Spielstein nicht dem Namen gleicht
                    currTile.rotateTile(); //den Spielstein rotieren
                }
                if(currTile.isHoleTile() || currTile.isPlaceHolderTile()) break; //Rotationen abbrechen da diese bei
                // Lochstein und Platzhalter keine Auswirkungen haben
            }
            currTile.setTileRotation(originalRotation);
        }
        return(null);
    }

    /**
     * Methode welche prueft, ob ein Spielstein in den verfuegbaren Spielsteinen vorkommt
     * @param tile der pruefende Stein
     * @return ob dieser vorkommt oder nicht
     */
    boolean containsTile(Tile tile) {
        return(this.tiles.contains(tile));
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
