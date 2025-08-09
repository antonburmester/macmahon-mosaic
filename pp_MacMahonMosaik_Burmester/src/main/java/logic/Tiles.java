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
    Tiles(){
        this.tiles = new ArrayList<>();
        for (int i = 0; i < Game.TILE_AMOUNT_NO_HOLE_NO_EMPTY; i++) { //durchlaeuft alle Mosaiksteine bis auf HHHH und
            // NNNN
            this.tiles.add(new Tile(TileNames.values()[i]));
        }
    }

    /**
     * Konstruktor welcher diese Klasse mit bestehenden Spielsteinen laedt
     */
    Tiles(ArrayList<Tile> tiles){
        this.tiles = tiles;
    }

    /**
     * Methode welche einen bestimmten Spielstein dem Array hinzufuegt
     * @param addedTile der hinzuzufuegende Spielstein
     */
    void addTile(Tile addedTile){
        this.tiles.add(addedTile);
    }

    /**
     * Methode welche einen bestimmten Spielstein aus dem Array entfernt
     * @param removedTile der zu entfernende Spielstein
     */
    void removeTile(Tile removedTile){
        this.tiles.remove(removedTile);
    }

    /**
     * Methode welche diese Instanz klont als DeepCopy
     * @return eine DeepCopy dieser Instanz
     */
    Tiles cloneGameTiles(){
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
    Tile getTileByTileNamesIndex(int index){
        TileNames searchedTileName = TileNames.values()[index];
        for(Tile currTile : this.tiles){ //jeder Spielstein
            if(currTile.getTile().equals(searchedTileName)) return(currTile); //wenn TileName des Spielsteins dem
            // TileName am index gleicht
        }
        return(null);
    }

    /**
     * Methode welche die richtige Klasse aus den Spielsteinen sucht
     * Der Name muss uebereinstimmen und der Spielstein darf noch nicht gelegt worden sein
     * @param tileName der Name des Spielsteins inklusive Rotationen
     * @return die Instanz welche zum uebergebenen Namen passt und die Rotationen werden gespeichert oder null
     */
    Tile getTileByNameWithRotation(String tileName){
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
