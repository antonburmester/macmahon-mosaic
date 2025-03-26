package logic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasse welche alle Spielsteine als Menge enthaelt
 *
 * @author Anton Burmester
 */

public class Tiles {
    private Set<TileNames> tiles;

    /**
     * Konstruktor welche eine Menge mit allen Spielsteinen erstellt
     */
    public Tiles(){
        this.tiles = new HashSet<>();
        for(int i = 0; i < TileNames.values().length; i++) { //durchlaeuft alle Mosaiksteine
            this.tiles.add(TileNames.values()[i]); // fuegt alle Mosaiksteine der Menge hinzu
        }
    }

    /**
     * Public getter um die Private Nutzlast der Menge der Mosaiksteine zu bekommen
     * @return alle Mosaiksteine die in der Menge sind
     */
    public Set<TileNames> getTiles(){
        return(this.tiles);
    }

    /**
     * fuegt den zu loeschenden Mosaikstein zur Menge hinzu
     * @param tile der hinzufuegende Mosaikstein
     */
    public void addTile(TileNames tile){
        this.tiles.add(tile);
    }

    /**
     * loescht ein Mosaikstein aus der Menge
     * @param tile der zu loeschende Mosaikstein
     */
    public void removeTile(TileNames tile){
        this.tiles.remove(tile);
    }

    /**
     * gibt alle Mosaiksteine aus die in der Menge vorhanden sind
     * @return alle Mosaiksteine als String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Tiles: ").append("\n");
        for(TileNames tile : this.tiles){
            sb.append(tile.toString()).append("\n");
        }
        return(sb.toString());
    }
}
