package logic;

/**
 * Klasse welche Spielsteine ohne NNNN und HHHH als Array enthaelt
 * Die Reihenfolge orientiert sich an der Reihenfolge des TileNames Enum
 *
 * @author Anton Burmester
 */

public class GameTiles {
    private final Tile[] tiles;

    /**
     * Konstruktor welcher ein Array mit allen Spielsteinen erstellt
     */
    public GameTiles(){
        this.tiles = new Tile[TileNames.values().length];
        for (int i = 0; i < TileNames.values().length - 2; i++) { //durchlaeuft alle Mosaiksteine bis auf HHHH und NNNN
            this.tiles[i] = new Tile(TileNames.values()[i]);
        }
    }

    /**
     * Konstruktor welcher diese Klasse mit bestehenden Spielsteinen laedt
     */
    public GameTiles(Tile[] tiles){
        this.tiles = tiles;
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
     * fuegt den zu hinzuzufuegenden Mosaikstein dem Array an der richtigen Stelle hinzu
     * @param tile der hinzufuegende Mosaikstein
     */
    public void addTile(Tile tile){
        this.tiles[TileNames.valueOf(tile.getTileString()).ordinal()] = tile;
    }

    /**
     * loescht ein Mosaikstein aus dem Array
     * in wirklichkeit wird die Instanz an der Stelle nur null gesetzt
     * @param tile der zu loeschende Mosaikstein
     */
    public void removeTile(Tile tile){
        this.tiles[TileNames.valueOf(tile.getTileString()).ordinal()] = null;
    }

    /**
     * gibt alle Mosaiksteine aus die in der Menge vorhanden sind
     * @return alle Mosaiksteine als String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("GameTiles: ").append("\n");
        for(Tile tile : this.tiles){
            sb.append(tile == null ? "null" : tile.getTileString()).append("\n");
        }
        return(sb.toString());
    }
}
